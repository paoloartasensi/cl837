package no.nordicsemi.android.ble;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattServer;
import android.bluetooth.BluetoothGattService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.SystemClock;
import android.os.Build.VERSION;
import android.util.Log;
import android.util.Pair;
import androidx.annotation.IntRange;
import androidx.annotation.Keep;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.RequiresPermission;
import java.lang.reflect.Method;
import java.security.InvalidParameterException;
import java.util.Deque;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.LinkedBlockingDeque;
import no.nordicsemi.android.ble.callback.ConnectionParametersUpdatedCallback;
import no.nordicsemi.android.ble.callback.DataReceivedCallback;
import no.nordicsemi.android.ble.data.DataProvider;
import no.nordicsemi.android.ble.error.GattError;
import no.nordicsemi.android.ble.observer.BondingObserver;
import no.nordicsemi.android.ble.observer.ConnectionObserver;
import no.nordicsemi.android.ble.utils.ParserUtils;

@SuppressLint({"MissingPermission"})
abstract class BleManagerHandler extends RequestHandler {
   private static final String TAG = "BleManager";
   private static final String ERROR_CONNECTION_STATE_CHANGE = "Error on connection state change";
   private static final String ERROR_DISCOVERY_SERVICE = "Error on discovering services";
   private static final String ERROR_AUTH_ERROR_WHILE_BONDED = "Phone has lost bonding information";
   private static final String ERROR_READ_CHARACTERISTIC = "Error on reading characteristic";
   private static final String ERROR_WRITE_CHARACTERISTIC = "Error on writing characteristic";
   private static final String ERROR_READ_DESCRIPTOR = "Error on reading descriptor";
   private static final String ERROR_WRITE_DESCRIPTOR = "Error on writing descriptor";
   private static final String ERROR_MTU_REQUEST = "Error on mtu request";
   private static final String ERROR_CONNECTION_PRIORITY_REQUEST = "Error on connection priority request";
   private static final String ERROR_READ_RSSI = "Error on RSSI read";
   private static final String ERROR_READ_PHY = "Error on PHY read";
   private static final String ERROR_PHY_UPDATE = "Error on PHY update";
   private static final String ERROR_RELIABLE_WRITE = "Error on Execute Reliable Write";
   private static final String ERROR_NOTIFY = "Error on sending notification/indication";
   private final Object LOCK = new Object();
   private BluetoothDevice bluetoothDevice;
   private BluetoothGatt bluetoothGatt;
   private BleManager manager;
   private BleServerManager serverManager;
   private Handler handler;
   private final Deque<Request> taskQueue = new LinkedBlockingDeque();
   private Deque<Request> initQueue;
   private boolean initialization;
   private static final long CONNECTION_TIMEOUT_THRESHOLD = 20000L;
   private boolean servicesDiscovered;
   private boolean deviceNotSupported;
   private boolean serviceDiscoveryRequested;
   private long connectionTime;
   private int connectionCount = 0;
   private boolean connected;
   private boolean ready;
   private boolean operationInProgress;
   private boolean userDisconnected;
   private boolean initialConnection;
   private int connectionState = 0;
   private boolean connectionPriorityOperationInProgress = false;
   private boolean reliableWriteInProgress;
   private int mtu = 23;
   private int interval;
   private int latency;
   private int timeout;
   /** @deprecated */
   @Deprecated
   @IntRange(
      from = -1L,
      to = 100L
   )
   private int batteryValue = -1;
   private Map<BluetoothGattCharacteristic, byte[]> characteristicValues;
   private Map<BluetoothGattDescriptor, byte[]> descriptorValues;
   private Deque<Pair<Object, byte[]>> preparedValues;
   private int prepareError;
   private ConnectRequest connectRequest;
   private Request request;
   private RequestQueue requestQueue;
   @NonNull
   private final HashMap<Object, ValueChangedCallback> valueChangedCallbacks = new HashMap();
   @NonNull
   private final HashMap<Object, DataProvider> dataProviders = new HashMap();
   @Nullable
   private ConnectionParametersUpdatedCallback connectionParametersUpdatedCallback;
   /** @deprecated */
   @Deprecated
   @Nullable
   private ValueChangedCallback batteryLevelNotificationCallback;
   @Nullable
   private AwaitingRequest<?> awaitingRequest;
   private final BroadcastReceiver bluetoothStateBroadcastReceiver = new BroadcastReceiver() {
      public void onReceive(Context context, Intent intent) {
         int state = intent.getIntExtra("android.bluetooth.adapter.extra.STATE", 10);
         int previousState = intent.getIntExtra("android.bluetooth.adapter.extra.PREVIOUS_STATE", 10);
         BleManagerHandler.this.log(3, () -> {
            return "[Broadcast] Action received: android.bluetooth.adapter.action.STATE_CHANGED, state changed to " + this.state2String(state);
         });
         switch(state) {
         case 10:
         case 13:
            if (previousState != 13 && previousState != 10) {
               BleManagerHandler.this.operationInProgress = true;
               BleManagerHandler.this.taskQueue.clear();
               BleManagerHandler.this.initQueue = null;
               boolean wasConnected = BleManagerHandler.this.connected;
               BleManagerHandler.this.connected = false;
               BleManagerHandler.this.ready = false;
               BleManagerHandler.this.connectionState = 0;
               BluetoothDevice device = BleManagerHandler.this.bluetoothDevice;
               if (device != null) {
                  if (BleManagerHandler.this.request != null && BleManagerHandler.this.request.type != Request.Type.DISCONNECT) {
                     BleManagerHandler.this.request.notifyFail(device, -100);
                     BleManagerHandler.this.request = null;
                  }

                  if (BleManagerHandler.this.awaitingRequest != null) {
                     BleManagerHandler.this.awaitingRequest.notifyFail(device, -100);
                     BleManagerHandler.this.awaitingRequest = null;
                  }

                  if (BleManagerHandler.this.connectRequest != null) {
                     BleManagerHandler.this.connectRequest.notifyFail(device, -100);
                     BleManagerHandler.this.connectRequest = null;
                  }
               }

               BleManagerHandler.this.userDisconnected = true;
               BleManagerHandler.this.operationInProgress = false;
               if (device != null) {
                  BleManagerHandler.this.connected = wasConnected;
                  BleManagerHandler.this.notifyDeviceDisconnected(device, 1);
               }
            } else {
               BleManagerHandler.this.close();
            }
         default:
         }
      }

      private String state2String(int state) {
         switch(state) {
         case 10:
            return "OFF";
         case 11:
            return "TURNING ON";
         case 12:
            return "ON";
         case 13:
            return "TURNING OFF";
         default:
            return "UNKNOWN (" + state + ")";
         }
      }
   };
   private final BroadcastReceiver mBondingBroadcastReceiver = new BroadcastReceiver() {
      public void onReceive(Context context, Intent intent) {
         BluetoothDevice device = (BluetoothDevice)intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE");
         int bondState = intent.getIntExtra("android.bluetooth.device.extra.BOND_STATE", -1);
         int previousBondState = intent.getIntExtra("android.bluetooth.device.extra.PREVIOUS_BOND_STATE", -1);
         if (BleManagerHandler.this.bluetoothDevice != null && device != null && device.getAddress().equals(BleManagerHandler.this.bluetoothDevice.getAddress())) {
            BleManagerHandler.this.log(3, () -> {
               return "[Broadcast] Action received: android.bluetooth.device.action.BOND_STATE_CHANGED, bond state changed to: " + ParserUtils.bondStateToString(bondState) + " (" + bondState + ")";
            });
            switch(bondState) {
            case 10:
               if (previousBondState == 11) {
                  BleManagerHandler.this.postCallback((c) -> {
                     c.onBondingFailed(device);
                  });
                  BleManagerHandler.this.postBondingStateChange((o) -> {
                     o.onBondingFailed(device);
                  });
                  BleManagerHandler.this.log(5, () -> {
                     return "Bonding failed";
                  });
                  if (BleManagerHandler.this.request != null && BleManagerHandler.this.request.type == Request.Type.CREATE_BOND) {
                     BleManagerHandler.this.request.notifyFail(device, -4);
                     BleManagerHandler.this.request = null;
                  }

                  if (!BleManagerHandler.this.servicesDiscovered && !BleManagerHandler.this.serviceDiscoveryRequested) {
                     BleManagerHandler.this.post(() -> {
                        BluetoothGatt bluetoothGatt = BleManagerHandler.this.bluetoothGatt;
                        if (!BleManagerHandler.this.servicesDiscovered && !BleManagerHandler.this.serviceDiscoveryRequested && bluetoothGatt != null) {
                           BleManagerHandler.this.serviceDiscoveryRequested = true;
                           BleManagerHandler.this.log(2, () -> {
                              return "Discovering services...";
                           });
                           BleManagerHandler.this.log(3, () -> {
                              return "gatt.discoverServices()";
                           });
                           bluetoothGatt.discoverServices();
                        }

                     });
                     return;
                  }
               } else if (previousBondState == 12) {
                  BleManagerHandler.this.userDisconnected = true;
                  if (BleManagerHandler.this.request != null && BleManagerHandler.this.request.type == Request.Type.REMOVE_BOND) {
                     BleManagerHandler.this.log(4, () -> {
                        return "Bond information removed";
                     });
                     BleManagerHandler.this.request.notifySuccess(device);
                     BleManagerHandler.this.request = null;
                  }

                  if (!BleManagerHandler.this.isConnected()) {
                     BleManagerHandler.this.close();
                  }
               }
               break;
            case 11:
               BleManagerHandler.this.postCallback((c) -> {
                  c.onBondingRequired(device);
               });
               BleManagerHandler.this.postBondingStateChange((o) -> {
                  o.onBondingRequired(device);
               });
               return;
            case 12:
               BleManagerHandler.this.log(4, () -> {
                  return "Device bonded";
               });
               BleManagerHandler.this.postCallback((c) -> {
                  c.onBonded(device);
               });
               BleManagerHandler.this.postBondingStateChange((o) -> {
                  o.onBonded(device);
               });
               if (BleManagerHandler.this.request != null && BleManagerHandler.this.request.type == Request.Type.CREATE_BOND) {
                  BleManagerHandler.this.request.notifySuccess(device);
                  BleManagerHandler.this.request = null;
               } else {
                  if (!BleManagerHandler.this.servicesDiscovered && !BleManagerHandler.this.serviceDiscoveryRequested) {
                     BleManagerHandler.this.post(() -> {
                        BluetoothGatt bluetoothGatt = BleManagerHandler.this.bluetoothGatt;
                        if (!BleManagerHandler.this.servicesDiscovered && !BleManagerHandler.this.serviceDiscoveryRequested && bluetoothGatt != null) {
                           BleManagerHandler.this.serviceDiscoveryRequested = true;
                           BleManagerHandler.this.log(2, () -> {
                              return "Discovering services...";
                           });
                           BleManagerHandler.this.log(3, () -> {
                              return "gatt.discoverServices()";
                           });
                           bluetoothGatt.discoverServices();
                        }

                     });
                     return;
                  }

                  if (VERSION.SDK_INT >= 26 || BleManagerHandler.this.request == null) {
                     return;
                  }

                  BleManagerHandler.this.enqueueFirst(BleManagerHandler.this.request);
               }
            }

            BleManagerHandler.this.nextRequest(true);
         }
      }
   };
   private final BluetoothGattCallback gattCallback = new BluetoothGattCallback() {
      public void onConnectionStateChange(@NonNull BluetoothGatt gatt, int status, int newState) {
         BleManagerHandler.this.log(3, () -> {
            return "[Callback] Connection state changed with status: " + status + " and new state: " + newState + " (" + ParserUtils.stateToString(newState) + ")";
         });
         if (status == 0 && newState == 2) {
            if (BleManagerHandler.this.bluetoothDevice == null) {
               Log.e("BleManager", "Device received notification after disconnection.");
               BleManagerHandler.this.log(3, () -> {
                  return "gatt.close()";
               });

               try {
                  gatt.close();
               } catch (Throwable var11) {
               }

               return;
            }

            BleManagerHandler.this.log(4, () -> {
               return "Connected to " + gatt.getDevice().getAddress();
            });
            BleManagerHandler.this.connected = true;
            BleManagerHandler.this.connectionTime = 0L;
            BleManagerHandler.this.connectionState = 2;
            BleManagerHandler.this.postCallback((c) -> {
               c.onDeviceConnected(gatt.getDevice());
            });
            BleManagerHandler.this.postConnectionStateChange((o) -> {
               o.onDeviceConnected(gatt.getDevice());
            });
            if (!BleManagerHandler.this.serviceDiscoveryRequested) {
               boolean bonded = gatt.getDevice().getBondState() == 12;
               int delayx = BleManagerHandler.this.manager.getServiceDiscoveryDelay(bonded);
               if (delayx > 0) {
                  BleManagerHandler.this.log(3, () -> {
                     return "wait(" + delayx + ")";
                  });
               }

               int connectionCount = ++BleManagerHandler.this.connectionCount;
               BleManagerHandler.this.postDelayed(() -> {
                  if (connectionCount == BleManagerHandler.this.connectionCount) {
                     if (BleManagerHandler.this.connected && !BleManagerHandler.this.servicesDiscovered && !BleManagerHandler.this.serviceDiscoveryRequested && gatt.getDevice().getBondState() != 11) {
                        BleManagerHandler.this.serviceDiscoveryRequested = true;
                        BleManagerHandler.this.log(2, () -> {
                           return "Discovering services...";
                        });
                        BleManagerHandler.this.log(3, () -> {
                           return "gatt.discoverServices()";
                        });
                        gatt.discoverServices();
                     }

                  }
               }, (long)delayx);
            }
         } else {
            if (newState == 0) {
               long now = SystemClock.elapsedRealtime();
               boolean canTimeout = BleManagerHandler.this.connectionTime > 0L;
               boolean timeout = canTimeout && now > BleManagerHandler.this.connectionTime + 20000L;
               if (status != 0) {
                  BleManagerHandler.this.log(5, () -> {
                     return "Error: (0x" + Integer.toHexString(status) + "): " + GattError.parseConnectionError(status);
                  });
               }

               if (status != 0 && canTimeout && !timeout && BleManagerHandler.this.connectRequest != null && BleManagerHandler.this.connectRequest.canRetry()) {
                  int delay = BleManagerHandler.this.connectRequest.getRetryDelay();
                  if (delay > 0) {
                     BleManagerHandler.this.log(3, () -> {
                        return "wait(" + delay + ")";
                     });
                  }

                  BleManagerHandler.this.postDelayed(() -> {
                     BleManagerHandler.this.internalConnect(gatt.getDevice(), BleManagerHandler.this.connectRequest);
                  }, (long)delay);
                  return;
               }

               if (BleManagerHandler.this.connectRequest != null && BleManagerHandler.this.connectRequest.shouldAutoConnect() && BleManagerHandler.this.initialConnection && gatt.getDevice().getBondState() == 12) {
                  BleManagerHandler.this.log(3, () -> {
                     return "autoConnect = false called failed; retrying with autoConnect = true";
                  });
                  BleManagerHandler.this.post(() -> {
                     BleManagerHandler.this.internalConnect(gatt.getDevice(), BleManagerHandler.this.connectRequest);
                  });
                  return;
               }

               BleManagerHandler.this.operationInProgress = true;
               BleManagerHandler.this.taskQueue.clear();
               BleManagerHandler.this.initQueue = null;
               BleManagerHandler.this.ready = false;
               boolean wasConnected = BleManagerHandler.this.connected;
               boolean notSupported = BleManagerHandler.this.deviceNotSupported;
               BleManagerHandler.this.notifyDeviceDisconnected(gatt.getDevice(), timeout ? 10 : (notSupported ? 4 : BleManagerHandler.this.mapDisconnectStatusToReason(status)));
               if (BleManagerHandler.this.request != null && BleManagerHandler.this.request.type != Request.Type.DISCONNECT && BleManagerHandler.this.request.type != Request.Type.REMOVE_BOND) {
                  BleManagerHandler.this.request.notifyFail(gatt.getDevice(), status == 0 ? -1 : status);
                  BleManagerHandler.this.request = null;
               }

               if (BleManagerHandler.this.awaitingRequest != null) {
                  BleManagerHandler.this.awaitingRequest.notifyFail(gatt.getDevice(), -1);
                  BleManagerHandler.this.awaitingRequest = null;
               }

               if (BleManagerHandler.this.connectRequest != null) {
                  int reason;
                  if (notSupported) {
                     reason = -2;
                  } else if (status == 0) {
                     reason = -1;
                  } else if (status == 133 && timeout) {
                     reason = -5;
                  } else {
                     reason = status;
                  }

                  BleManagerHandler.this.connectRequest.notifyFail(gatt.getDevice(), reason);
                  BleManagerHandler.this.connectRequest = null;
               }

               BleManagerHandler.this.operationInProgress = false;
               if (wasConnected && BleManagerHandler.this.initialConnection) {
                  BleManagerHandler.this.internalConnect(gatt.getDevice(), (ConnectRequest)null);
               } else {
                  BleManagerHandler.this.initialConnection = false;
                  BleManagerHandler.this.nextRequest(false);
               }

               if (wasConnected || status == 0) {
                  return;
               }
            } else if (status != 0) {
               BleManagerHandler.this.log(6, () -> {
                  return "Error (0x" + Integer.toHexString(status) + "): " + GattError.parseConnectionError(status);
               });
            }

            BleManagerHandler.this.postCallback((c) -> {
               c.onError(gatt.getDevice(), "Error on connection state change", status);
            });
         }

      }

      public void onServicesDiscovered(@NonNull BluetoothGatt gatt, int status) {
         if (BleManagerHandler.this.serviceDiscoveryRequested) {
            BleManagerHandler.this.serviceDiscoveryRequested = false;
            if (status == 0) {
               BleManagerHandler.this.log(4, () -> {
                  return "Services discovered";
               });
               BleManagerHandler.this.servicesDiscovered = true;
               if (BleManagerHandler.this.manager.isRequiredServiceSupported(gatt)) {
                  BleManagerHandler.this.log(2, () -> {
                     return "Primary service found";
                  });
                  BleManagerHandler.this.deviceNotSupported = false;
                  boolean optionalServicesFound = BleManagerHandler.this.manager.isOptionalServiceSupported(gatt);
                  if (optionalServicesFound) {
                     BleManagerHandler.this.log(2, () -> {
                        return "Secondary service found";
                     });
                  }

                  BleManagerHandler.this.postCallback((c) -> {
                     c.onServicesDiscovered(gatt.getDevice(), optionalServicesFound);
                  });
                  BleManagerHandler.this.initializeServerAttributes();
                  BleManagerHandler.this.operationInProgress = true;
                  BleManagerHandler.this.initialization = true;
                  BleManagerHandler.this.initQueue = BleManagerHandler.this.initGatt(gatt);
                  boolean deprecatedApiUsed = BleManagerHandler.this.initQueue != null;
                  Request request;
                  if (deprecatedApiUsed) {
                     for(Iterator var5 = BleManagerHandler.this.initQueue.iterator(); var5.hasNext(); request.enqueued = true) {
                        request = (Request)var5.next();
                        request.setRequestHandler(BleManagerHandler.this);
                     }
                  }

                  if (BleManagerHandler.this.initQueue == null) {
                     BleManagerHandler.this.initQueue = new LinkedBlockingDeque();
                  }

                  if (VERSION.SDK_INT < 23 || VERSION.SDK_INT == 26 || VERSION.SDK_INT == 27 || VERSION.SDK_INT == 28) {
                     BleManagerHandler.this.enqueueFirst(Request.newEnableServiceChangedIndicationsRequest().setRequestHandler(BleManagerHandler.this));
                     BleManagerHandler.this.operationInProgress = true;
                  }

                  if (deprecatedApiUsed) {
                     BleManagerHandler.this.manager.readBatteryLevel();
                     if (BleManagerHandler.this.manager.callbacks != null && BleManagerHandler.this.manager.callbacks.shouldEnableBatteryLevelNotifications(gatt.getDevice())) {
                        BleManagerHandler.this.manager.enableBatteryLevelNotifications();
                     }
                  }

                  BleManagerHandler.this.manager.initialize();
                  BleManagerHandler.this.initialization = false;
                  BleManagerHandler.this.nextRequest(true);
               } else {
                  BleManagerHandler.this.log(5, () -> {
                     return "Device is not supported";
                  });
                  BleManagerHandler.this.deviceNotSupported = true;
                  BleManagerHandler.this.postCallback((c) -> {
                     c.onDeviceNotSupported(gatt.getDevice());
                  });
                  BleManagerHandler.this.internalDisconnect(4);
               }
            } else {
               Log.e("BleManager", "onServicesDiscovered error " + status);
               BleManagerHandler.this.onError(gatt.getDevice(), "Error on discovering services", status);
               if (BleManagerHandler.this.connectRequest != null) {
                  BleManagerHandler.this.connectRequest.notifyFail(gatt.getDevice(), -4);
                  BleManagerHandler.this.connectRequest = null;
               }

               BleManagerHandler.this.internalDisconnect(-1);
            }

         }
      }

      @Keep
      public void onServiceChanged(@NonNull BluetoothGatt gatt) {
         BleManagerHandler.this.log(4, () -> {
            return "Service changed, invalidating services";
         });
         BleManagerHandler.this.operationInProgress = true;
         BleManagerHandler.this.manager.onServicesInvalidated();
         BleManagerHandler.this.onDeviceDisconnected();
         BleManagerHandler.this.taskQueue.clear();
         BleManagerHandler.this.initQueue = null;
         BleManagerHandler.this.serviceDiscoveryRequested = true;
         BleManagerHandler.this.servicesDiscovered = false;
         BleManagerHandler.this.log(2, () -> {
            return "Discovering Services...";
         });
         BleManagerHandler.this.log(3, () -> {
            return "gatt.discoverServices()";
         });
         gatt.discoverServices();
      }

      public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
         this.onCharacteristicRead(gatt, characteristic, characteristic.getValue(), status);
      }

      public void onCharacteristicRead(@NonNull BluetoothGatt gatt, @NonNull BluetoothGattCharacteristic characteristic, @NonNull byte[] data, int status) {
         if (status == 0) {
            BleManagerHandler.this.log(4, () -> {
               return "Read Response received from " + characteristic.getUuid() + ", value: " + ParserUtils.parse(data);
            });
            BleManagerHandler.this.onCharacteristicRead(gatt, characteristic);
            if (BleManagerHandler.this.request instanceof ReadRequest) {
               ReadRequest rr = (ReadRequest)BleManagerHandler.this.request;
               boolean matches = rr.matches(data);
               if (matches) {
                  rr.notifyValueChanged(gatt.getDevice(), data);
               }

               if (matches && !rr.hasMore()) {
                  rr.notifySuccess(gatt.getDevice());
               } else {
                  BleManagerHandler.this.enqueueFirst(rr);
               }
            }
         } else {
            if (status == 5 || status == 8 || status == 137) {
               BleManagerHandler.this.log(5, () -> {
                  return "Authentication required (" + status + ")";
               });
               if (gatt.getDevice().getBondState() != 10) {
                  Log.w("BleManager", "Phone has lost bonding information");
                  BleManagerHandler.this.postCallback((c) -> {
                     c.onError(gatt.getDevice(), "Phone has lost bonding information", status);
                  });
               }

               return;
            }

            Log.e("BleManager", "onCharacteristicRead error " + status);
            if (BleManagerHandler.this.request instanceof ReadRequest) {
               BleManagerHandler.this.request.notifyFail(gatt.getDevice(), status);
            }

            BleManagerHandler.this.awaitingRequest = null;
            BleManagerHandler.this.onError(gatt.getDevice(), "Error on reading characteristic", status);
         }

         BleManagerHandler.this.checkCondition();
         BleManagerHandler.this.nextRequest(true);
      }

      public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
         if (status == 0) {
            BleManagerHandler.this.log(4, () -> {
               return "Data written to " + characteristic.getUuid();
            });
            BleManagerHandler.this.onCharacteristicWrite(gatt, characteristic);
            if (BleManagerHandler.this.request instanceof WriteRequest) {
               WriteRequest wr = (WriteRequest)BleManagerHandler.this.request;
               boolean valid = wr.notifyPacketSent(gatt.getDevice(), characteristic.getValue());
               if (!valid && BleManagerHandler.this.requestQueue instanceof ReliableWriteRequest) {
                  wr.notifyFail(gatt.getDevice(), -6);
                  BleManagerHandler.this.requestQueue.cancelQueue();
               } else if (wr.hasMore()) {
                  BleManagerHandler.this.enqueueFirst(wr);
               } else {
                  wr.notifySuccess(gatt.getDevice());
               }
            }
         } else {
            if (status == 5 || status == 8 || status == 137) {
               BleManagerHandler.this.log(5, () -> {
                  return "Authentication required (" + status + ")";
               });
               if (gatt.getDevice().getBondState() != 10) {
                  Log.w("BleManager", "Phone has lost bonding information");
                  BleManagerHandler.this.postCallback((c) -> {
                     c.onError(gatt.getDevice(), "Phone has lost bonding information", status);
                  });
               }

               return;
            }

            Log.e("BleManager", "onCharacteristicWrite error " + status);
            if (BleManagerHandler.this.request instanceof WriteRequest) {
               BleManagerHandler.this.request.notifyFail(gatt.getDevice(), status);
               if (BleManagerHandler.this.requestQueue instanceof ReliableWriteRequest) {
                  BleManagerHandler.this.requestQueue.cancelQueue();
               }
            }

            BleManagerHandler.this.awaitingRequest = null;
            BleManagerHandler.this.onError(gatt.getDevice(), "Error on writing characteristic", status);
         }

         BleManagerHandler.this.checkCondition();
         BleManagerHandler.this.nextRequest(true);
      }

      public void onReliableWriteCompleted(@NonNull BluetoothGatt gatt, int status) {
         boolean execute = BleManagerHandler.this.request.type == Request.Type.EXECUTE_RELIABLE_WRITE;
         BleManagerHandler.this.reliableWriteInProgress = false;
         if (status == 0) {
            if (execute) {
               BleManagerHandler.this.log(4, () -> {
                  return "Reliable Write executed";
               });
               BleManagerHandler.this.request.notifySuccess(gatt.getDevice());
            } else {
               BleManagerHandler.this.log(5, () -> {
                  return "Reliable Write aborted";
               });
               BleManagerHandler.this.request.notifySuccess(gatt.getDevice());
               BleManagerHandler.this.requestQueue.notifyFail(gatt.getDevice(), -4);
            }
         } else {
            Log.e("BleManager", "onReliableWriteCompleted execute " + execute + ", error " + status);
            BleManagerHandler.this.request.notifyFail(gatt.getDevice(), status);
            BleManagerHandler.this.onError(gatt.getDevice(), "Error on Execute Reliable Write", status);
         }

         BleManagerHandler.this.checkCondition();
         BleManagerHandler.this.nextRequest(true);
      }

      public void onDescriptorRead(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
         byte[] data = descriptor.getValue();
         if (status == 0) {
            BleManagerHandler.this.log(4, () -> {
               return "Read Response received from descr. " + descriptor.getUuid() + ", value: " + ParserUtils.parse(data);
            });
            BleManagerHandler.this.onDescriptorRead(gatt, descriptor);
            if (BleManagerHandler.this.request instanceof ReadRequest) {
               ReadRequest request = (ReadRequest)BleManagerHandler.this.request;
               request.notifyValueChanged(gatt.getDevice(), data);
               if (request.hasMore()) {
                  BleManagerHandler.this.enqueueFirst(request);
               } else {
                  request.notifySuccess(gatt.getDevice());
               }
            }
         } else {
            if (status == 5 || status == 8 || status == 137) {
               BleManagerHandler.this.log(5, () -> {
                  return "Authentication required (" + status + ")";
               });
               if (gatt.getDevice().getBondState() != 10) {
                  Log.w("BleManager", "Phone has lost bonding information");
                  BleManagerHandler.this.postCallback((c) -> {
                     c.onError(gatt.getDevice(), "Phone has lost bonding information", status);
                  });
               }

               return;
            }

            Log.e("BleManager", "onDescriptorRead error " + status);
            if (BleManagerHandler.this.request instanceof ReadRequest) {
               BleManagerHandler.this.request.notifyFail(gatt.getDevice(), status);
            }

            BleManagerHandler.this.awaitingRequest = null;
            BleManagerHandler.this.onError(gatt.getDevice(), "Error on reading descriptor", status);
         }

         BleManagerHandler.this.checkCondition();
         BleManagerHandler.this.nextRequest(true);
      }

      public void onDescriptorWrite(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
         byte[] data = descriptor.getValue();
         if (status == 0) {
            BleManagerHandler.this.log(4, () -> {
               return "Data written to descr. " + descriptor.getUuid();
            });
            if (BleManagerHandler.this.isServiceChangedCCCD(descriptor)) {
               BleManagerHandler.this.log(4, () -> {
                  return "Service Changed notifications enabled";
               });
            } else if (BleManagerHandler.this.isCCCD(descriptor)) {
               if (data != null && data.length == 2 && data[1] == 0) {
                  switch(data[0]) {
                  case 0:
                     BleManagerHandler.this.log(4, () -> {
                        return "Notifications and indications disabled";
                     });
                     break;
                  case 1:
                     BleManagerHandler.this.log(4, () -> {
                        return "Notifications enabled";
                     });
                     break;
                  case 2:
                     BleManagerHandler.this.log(4, () -> {
                        return "Indications enabled";
                     });
                  }

                  BleManagerHandler.this.onDescriptorWrite(gatt, descriptor);
               }
            } else {
               BleManagerHandler.this.onDescriptorWrite(gatt, descriptor);
            }

            if (BleManagerHandler.this.request instanceof WriteRequest) {
               WriteRequest wr = (WriteRequest)BleManagerHandler.this.request;
               boolean valid = wr.notifyPacketSent(gatt.getDevice(), data);
               if (!valid && BleManagerHandler.this.requestQueue instanceof ReliableWriteRequest) {
                  wr.notifyFail(gatt.getDevice(), -6);
                  BleManagerHandler.this.requestQueue.cancelQueue();
               } else if (wr.hasMore()) {
                  BleManagerHandler.this.enqueueFirst(wr);
               } else {
                  wr.notifySuccess(gatt.getDevice());
               }
            }
         } else {
            if (status == 5 || status == 8 || status == 137) {
               BleManagerHandler.this.log(5, () -> {
                  return "Authentication required (" + status + ")";
               });
               if (gatt.getDevice().getBondState() != 10) {
                  Log.w("BleManager", "Phone has lost bonding information");
                  BleManagerHandler.this.postCallback((c) -> {
                     c.onError(gatt.getDevice(), "Phone has lost bonding information", status);
                  });
               }

               return;
            }

            Log.e("BleManager", "onDescriptorWrite error " + status);
            if (BleManagerHandler.this.request instanceof WriteRequest) {
               BleManagerHandler.this.request.notifyFail(gatt.getDevice(), status);
               if (BleManagerHandler.this.requestQueue instanceof ReliableWriteRequest) {
                  BleManagerHandler.this.requestQueue.cancelQueue();
               }
            }

            BleManagerHandler.this.awaitingRequest = null;
            BleManagerHandler.this.onError(gatt.getDevice(), "Error on writing descriptor", status);
         }

         BleManagerHandler.this.checkCondition();
         BleManagerHandler.this.nextRequest(true);
      }

      public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
         this.onCharacteristicChanged(gatt, characteristic, characteristic.getValue());
      }

      public void onCharacteristicChanged(@NonNull BluetoothGatt gatt, @NonNull BluetoothGattCharacteristic characteristic, @NonNull byte[] data) {
         if (BleManagerHandler.this.isServiceChangedCharacteristic(characteristic)) {
            if (VERSION.SDK_INT <= 30) {
               BleManagerHandler.this.log(4, () -> {
                  return "Service Changed indication received";
               });
               BleManagerHandler.this.operationInProgress = true;
               BleManagerHandler.this.manager.onServicesInvalidated();
               BleManagerHandler.this.onDeviceDisconnected();
               BleManagerHandler.this.taskQueue.clear();
               BleManagerHandler.this.initQueue = null;
               BleManagerHandler.this.serviceDiscoveryRequested = true;
               BleManagerHandler.this.log(2, () -> {
                  return "Discovering Services...";
               });
               BleManagerHandler.this.log(3, () -> {
                  return "gatt.discoverServices()";
               });
               gatt.discoverServices();
            }

         } else {
            BluetoothGattDescriptor cccd = characteristic.getDescriptor(BleManager.CLIENT_CHARACTERISTIC_CONFIG_DESCRIPTOR_UUID);
            boolean notifications = cccd == null || cccd.getValue() == null || cccd.getValue().length != 2 || cccd.getValue()[0] == 1;
            if (notifications) {
               BleManagerHandler.this.log(4, () -> {
                  return "Notification received from " + characteristic.getUuid() + ", value: " + ParserUtils.parse(data);
               });
               BleManagerHandler.this.onCharacteristicNotified(gatt, characteristic);
            } else {
               BleManagerHandler.this.log(4, () -> {
                  return "Indication received from " + characteristic.getUuid() + ", value: " + ParserUtils.parse(data);
               });
               BleManagerHandler.this.onCharacteristicIndicated(gatt, characteristic);
            }

            if (BleManagerHandler.this.batteryLevelNotificationCallback != null && BleManagerHandler.this.isBatteryLevelCharacteristic(characteristic)) {
               BleManagerHandler.this.batteryLevelNotificationCallback.notifyValueChanged(gatt.getDevice(), data);
            }

            ValueChangedCallback request = (ValueChangedCallback)BleManagerHandler.this.valueChangedCallbacks.get(characteristic);
            if (request != null && request.matches(data)) {
               request.notifyValueChanged(gatt.getDevice(), data);
            }

            if (BleManagerHandler.this.awaitingRequest instanceof WaitForValueChangedRequest && BleManagerHandler.this.awaitingRequest.characteristic == characteristic && !BleManagerHandler.this.awaitingRequest.isTriggerPending()) {
               WaitForValueChangedRequest valueChangedRequest = (WaitForValueChangedRequest)BleManagerHandler.this.awaitingRequest;
               if (valueChangedRequest.matches(data)) {
                  valueChangedRequest.notifyValueChanged(gatt.getDevice(), data);
                  if (valueChangedRequest.isComplete()) {
                     BleManagerHandler.this.log(4, () -> {
                        return "Wait for value changed complete";
                     });
                     valueChangedRequest.notifySuccess(gatt.getDevice());
                     BleManagerHandler.this.awaitingRequest = null;
                     if (valueChangedRequest.isTriggerCompleteOrNull()) {
                        BleManagerHandler.this.nextRequest(true);
                     }
                  }
               }
            }

            if (BleManagerHandler.this.checkCondition()) {
               BleManagerHandler.this.nextRequest(true);
            }

         }
      }

      @RequiresApi(
         api = 21
      )
      public void onMtuChanged(@NonNull BluetoothGatt gatt, @IntRange(from = 23L,to = 517L) int mtu, int status) {
         if (status == 0) {
            BleManagerHandler.this.log(4, () -> {
               return "MTU changed to: " + mtu;
            });
            BleManagerHandler.this.mtu = mtu;
            BleManagerHandler.this.onMtuChanged(gatt, mtu);
            if (BleManagerHandler.this.request instanceof MtuRequest) {
               ((MtuRequest)BleManagerHandler.this.request).notifyMtuChanged(gatt.getDevice(), mtu);
               BleManagerHandler.this.request.notifySuccess(gatt.getDevice());
            }
         } else {
            Log.e("BleManager", "onMtuChanged error: " + status + ", mtu: " + mtu);
            if (BleManagerHandler.this.request instanceof MtuRequest) {
               BleManagerHandler.this.request.notifyFail(gatt.getDevice(), status);
               BleManagerHandler.this.awaitingRequest = null;
            }

            BleManagerHandler.this.onError(gatt.getDevice(), "Error on mtu request", status);
         }

         BleManagerHandler.this.checkCondition();
         if (BleManagerHandler.this.servicesDiscovered) {
            BleManagerHandler.this.nextRequest(true);
         }

      }

      @RequiresApi(
         api = 26
      )
      @Keep
      public void onConnectionUpdated(@NonNull BluetoothGatt gatt, @IntRange(from = 6L,to = 3200L) int interval, @IntRange(from = 0L,to = 499L) int latency, @IntRange(from = 10L,to = 3200L) int timeout, int status) {
         if (status == 0) {
            BleManagerHandler.this.log(4, () -> {
               return "Connection parameters updated (interval: " + (double)interval * 1.25D + "ms, latency: " + latency + ", timeout: " + timeout * 10 + "ms)";
            });
            BleManagerHandler.this.interval = interval;
            BleManagerHandler.this.latency = latency;
            BleManagerHandler.this.timeout = timeout;
            BleManagerHandler.this.onConnectionUpdated(gatt, interval, latency, timeout);
            ConnectionParametersUpdatedCallback cpuc = BleManagerHandler.this.connectionParametersUpdatedCallback;
            if (cpuc != null) {
               cpuc.onConnectionUpdated(gatt.getDevice(), interval, latency, timeout);
            }

            if (BleManagerHandler.this.request instanceof ConnectionPriorityRequest) {
               ((ConnectionPriorityRequest)BleManagerHandler.this.request).notifyConnectionPriorityChanged(gatt.getDevice(), interval, latency, timeout);
               BleManagerHandler.this.request.notifySuccess(gatt.getDevice());
            }
         } else if (status == 59) {
            Log.e("BleManager", "onConnectionUpdated received status: Unacceptable connection interval, interval: " + interval + ", latency: " + latency + ", timeout: " + timeout);
            BleManagerHandler.this.log(5, () -> {
               return "Connection parameters update failed with status: UNACCEPT CONN INTERVAL (0x3b) (interval: " + (double)interval * 1.25D + "ms, latency: " + latency + ", timeout: " + timeout * 10 + "ms)";
            });
            if (BleManagerHandler.this.request instanceof ConnectionPriorityRequest) {
               BleManagerHandler.this.request.notifyFail(gatt.getDevice(), status);
               BleManagerHandler.this.awaitingRequest = null;
            }
         } else {
            Log.e("BleManager", "onConnectionUpdated received status: " + status + ", interval: " + interval + ", latency: " + latency + ", timeout: " + timeout);
            BleManagerHandler.this.log(5, () -> {
               return "Connection parameters update failed with status " + status + " (interval: " + (double)interval * 1.25D + "ms, latency: " + latency + ", timeout: " + timeout * 10 + "ms)";
            });
            if (BleManagerHandler.this.request instanceof ConnectionPriorityRequest) {
               BleManagerHandler.this.request.notifyFail(gatt.getDevice(), status);
               BleManagerHandler.this.awaitingRequest = null;
            }

            BleManagerHandler.this.postCallback((c) -> {
               c.onError(gatt.getDevice(), "Error on connection priority request", status);
            });
         }

         if (BleManagerHandler.this.connectionPriorityOperationInProgress) {
            BleManagerHandler.this.connectionPriorityOperationInProgress = false;
            BleManagerHandler.this.checkCondition();
            BleManagerHandler.this.nextRequest(true);
         }

      }

      @RequiresApi(
         api = 26
      )
      public void onPhyUpdate(@NonNull BluetoothGatt gatt, int txPhy, int rxPhy, int status) {
         if (status == 0) {
            BleManagerHandler.this.log(4, () -> {
               return "PHY updated (TX: " + ParserUtils.phyToString(txPhy) + ", RX: " + ParserUtils.phyToString(rxPhy) + ")";
            });
            if (BleManagerHandler.this.request instanceof PhyRequest) {
               ((PhyRequest)BleManagerHandler.this.request).notifyPhyChanged(gatt.getDevice(), txPhy, rxPhy);
               BleManagerHandler.this.request.notifySuccess(gatt.getDevice());
            }
         } else {
            BleManagerHandler.this.log(5, () -> {
               return "PHY updated failed with status " + status;
            });
            if (BleManagerHandler.this.request instanceof PhyRequest) {
               BleManagerHandler.this.request.notifyFail(gatt.getDevice(), status);
               BleManagerHandler.this.awaitingRequest = null;
            }

            BleManagerHandler.this.postCallback((c) -> {
               c.onError(gatt.getDevice(), "Error on PHY update", status);
            });
         }

         if (BleManagerHandler.this.checkCondition() || BleManagerHandler.this.request instanceof PhyRequest) {
            BleManagerHandler.this.nextRequest(true);
         }

      }

      @RequiresApi(
         api = 26
      )
      public void onPhyRead(@NonNull BluetoothGatt gatt, int txPhy, int rxPhy, int status) {
         if (status == 0) {
            BleManagerHandler.this.log(4, () -> {
               return "PHY read (TX: " + ParserUtils.phyToString(txPhy) + ", RX: " + ParserUtils.phyToString(rxPhy) + ")";
            });
            if (BleManagerHandler.this.request instanceof PhyRequest) {
               ((PhyRequest)BleManagerHandler.this.request).notifyPhyChanged(gatt.getDevice(), txPhy, rxPhy);
               BleManagerHandler.this.request.notifySuccess(gatt.getDevice());
            }
         } else {
            BleManagerHandler.this.log(5, () -> {
               return "PHY read failed with status " + status;
            });
            if (BleManagerHandler.this.request instanceof PhyRequest) {
               BleManagerHandler.this.request.notifyFail(gatt.getDevice(), status);
            }

            BleManagerHandler.this.awaitingRequest = null;
            BleManagerHandler.this.postCallback((c) -> {
               c.onError(gatt.getDevice(), "Error on PHY read", status);
            });
         }

         BleManagerHandler.this.checkCondition();
         BleManagerHandler.this.nextRequest(true);
      }

      public void onReadRemoteRssi(@NonNull BluetoothGatt gatt, @IntRange(from = -128L,to = 20L) int rssi, int status) {
         if (status == 0) {
            BleManagerHandler.this.log(4, () -> {
               return "Remote RSSI received: " + rssi + " dBm";
            });
            if (BleManagerHandler.this.request instanceof ReadRssiRequest) {
               ((ReadRssiRequest)BleManagerHandler.this.request).notifyRssiRead(gatt.getDevice(), rssi);
               BleManagerHandler.this.request.notifySuccess(gatt.getDevice());
            }
         } else {
            BleManagerHandler.this.log(5, () -> {
               return "Reading remote RSSI failed with status " + status;
            });
            if (BleManagerHandler.this.request instanceof ReadRssiRequest) {
               BleManagerHandler.this.request.notifyFail(gatt.getDevice(), status);
            }

            BleManagerHandler.this.awaitingRequest = null;
            BleManagerHandler.this.postCallback((c) -> {
               c.onError(gatt.getDevice(), "Error on RSSI read", status);
            });
         }

         BleManagerHandler.this.checkCondition();
         BleManagerHandler.this.nextRequest(true);
      }
   };

   void init(@NonNull BleManager manager, @NonNull Handler handler) {
      this.manager = manager;
      this.handler = handler;
   }

   void useServer(@Nullable BleServerManager server) {
      this.serverManager = server;
   }

   void attachClientConnection(BluetoothDevice clientDevice) {
      if (this.bluetoothDevice != null) {
         this.log(6, () -> {
            return "attachClientConnection called on existing connection, call ignored";
         });
      } else {
         this.bluetoothDevice = clientDevice;
         this.initializeServerAttributes();
         this.manager.initialize();
      }

   }

   private void initializeServerAttributes() {
      if (this.serverManager != null) {
         BluetoothGattServer server = this.serverManager.getServer();
         if (server != null) {
            Iterator var2 = server.getServices().iterator();

            while(var2.hasNext()) {
               BluetoothGattService service = (BluetoothGattService)var2.next();
               Iterator var4 = service.getCharacteristics().iterator();

               while(var4.hasNext()) {
                  BluetoothGattCharacteristic characteristic = (BluetoothGattCharacteristic)var4.next();
                  if (!this.serverManager.isShared(characteristic)) {
                     if (this.characteristicValues == null) {
                        this.characteristicValues = new HashMap();
                     }

                     this.characteristicValues.put(characteristic, characteristic.getValue());
                  }

                  Iterator var6 = characteristic.getDescriptors().iterator();

                  while(var6.hasNext()) {
                     BluetoothGattDescriptor descriptor = (BluetoothGattDescriptor)var6.next();
                     if (!this.serverManager.isShared(descriptor)) {
                        if (this.descriptorValues == null) {
                           this.descriptorValues = new HashMap();
                        }

                        this.descriptorValues.put(descriptor, descriptor.getValue());
                     }
                  }
               }
            }

            this.manager.onServerReady(server);
         }
      }

   }

   void close() {
      try {
         Context context = this.manager.getContext();
         context.unregisterReceiver(this.bluetoothStateBroadcastReceiver);
         context.unregisterReceiver(this.mBondingBroadcastReceiver);
      } catch (Exception var6) {
      }

      synchronized(this.LOCK) {
         if (this.bluetoothGatt != null) {
            if (this.manager.shouldClearCacheWhenDisconnected()) {
               if (this.internalRefreshDeviceCache()) {
                  this.log(4, () -> {
                     return "Cache refreshed";
                  });
               } else {
                  this.log(5, () -> {
                     return "Refreshing failed";
                  });
               }
            }

            this.log(3, () -> {
               return "gatt.close()";
            });

            try {
               this.bluetoothGatt.close();
            } catch (Throwable var4) {
            }

            this.bluetoothGatt = null;
         }

         this.reliableWriteInProgress = false;
         this.initialConnection = false;
         this.taskQueue.clear();
         this.initQueue = null;
         this.initialization = false;
         this.bluetoothDevice = null;
         this.connected = false;
      }
   }

   public BluetoothDevice getBluetoothDevice() {
      return this.bluetoothDevice;
   }

   @Nullable
   public final byte[] getCharacteristicValue(@NonNull BluetoothGattCharacteristic serverCharacteristic) {
      return this.characteristicValues != null && this.characteristicValues.containsKey(serverCharacteristic) ? (byte[])this.characteristicValues.get(serverCharacteristic) : serverCharacteristic.getValue();
   }

   @Nullable
   public final byte[] getDescriptorValue(@NonNull BluetoothGattDescriptor serverDescriptor) {
      return this.descriptorValues != null && this.descriptorValues.containsKey(serverDescriptor) ? (byte[])this.descriptorValues.get(serverDescriptor) : serverDescriptor.getValue();
   }

   private boolean internalConnect(@NonNull BluetoothDevice device, @Nullable ConnectRequest connectRequest) {
      boolean bluetoothEnabled = BluetoothAdapter.getDefaultAdapter().isEnabled();
      if (!this.connected && bluetoothEnabled) {
         Context context = this.manager.getContext();
         synchronized(this.LOCK) {
            if (this.bluetoothGatt != null) {
               if (this.initialConnection) {
                  this.initialConnection = false;
                  this.connectionTime = 0L;
                  this.connectionState = 1;
                  this.log(2, () -> {
                     return "Connecting...";
                  });
                  this.postCallback((c) -> {
                     c.onDeviceConnecting(device);
                  });
                  this.postConnectionStateChange((o) -> {
                     o.onDeviceConnecting(device);
                  });
                  this.log(3, () -> {
                     return "gatt.connect()";
                  });
                  this.bluetoothGatt.connect();
                  return true;
               }

               this.log(3, () -> {
                  return "gatt.close()";
               });

               try {
                  this.bluetoothGatt.close();
               } catch (Throwable var9) {
               }

               this.bluetoothGatt = null;

               try {
                  this.log(3, () -> {
                     return "wait(200)";
                  });
                  Thread.sleep(200L);
               } catch (InterruptedException var8) {
               }
            } else if (connectRequest != null) {
               context.registerReceiver(this.bluetoothStateBroadcastReceiver, new IntentFilter("android.bluetooth.adapter.action.STATE_CHANGED"));
               context.registerReceiver(this.mBondingBroadcastReceiver, new IntentFilter("android.bluetooth.device.action.BOND_STATE_CHANGED"));
            }
         }

         if (connectRequest == null) {
            return false;
         } else {
            boolean shouldAutoConnect = connectRequest.shouldAutoConnect();
            this.userDisconnected = !shouldAutoConnect;
            if (shouldAutoConnect) {
               this.initialConnection = true;
            }

            this.bluetoothDevice = device;
            this.log(2, () -> {
               return connectRequest.isFirstAttempt() ? "Connecting..." : "Retrying...";
            });
            this.connectionState = 1;
            this.postCallback((c) -> {
               c.onDeviceConnecting(device);
            });
            this.postConnectionStateChange((o) -> {
               o.onDeviceConnecting(device);
            });
            this.connectionTime = SystemClock.elapsedRealtime();
            int preferredPhy;
            if (VERSION.SDK_INT > 26) {
               preferredPhy = connectRequest.getPreferredPhy();
               this.log(3, () -> {
                  return "gatt = device.connectGatt(autoConnect = false, TRANSPORT_LE, " + ParserUtils.phyMaskToString(preferredPhy) + ")";
               });
               this.bluetoothGatt = device.connectGatt(context, false, this.gattCallback, 2, preferredPhy, this.handler);
            } else if (VERSION.SDK_INT == 26) {
               preferredPhy = connectRequest.getPreferredPhy();
               this.log(3, () -> {
                  return "gatt = device.connectGatt(autoConnect = false, TRANSPORT_LE, " + ParserUtils.phyMaskToString(preferredPhy) + ")";
               });
               this.bluetoothGatt = device.connectGatt(context, false, this.gattCallback, 2, preferredPhy);
            } else if (VERSION.SDK_INT >= 23) {
               this.log(3, () -> {
                  return "gatt = device.connectGatt(autoConnect = false, TRANSPORT_LE)";
               });
               this.bluetoothGatt = device.connectGatt(context, false, this.gattCallback, 2);
            } else {
               this.log(3, () -> {
                  return "gatt = device.connectGatt(autoConnect = false)";
               });
               this.bluetoothGatt = device.connectGatt(context, false, this.gattCallback);
            }

            return true;
         }
      } else {
         BluetoothDevice currentDevice = this.bluetoothDevice;
         if (bluetoothEnabled && currentDevice != null && currentDevice.equals(device)) {
            if (this.connectRequest != null) {
               this.connectRequest.notifySuccess(device);
            }
         } else if (this.connectRequest != null) {
            this.connectRequest.notifyFail(device, bluetoothEnabled ? -4 : -100);
         }

         this.connectRequest = null;
         this.nextRequest(true);
         return true;
      }
   }

   private boolean internalDisconnect(int reason) {
      this.userDisconnected = true;
      this.initialConnection = false;
      this.ready = false;
      BluetoothGatt gatt = this.bluetoothGatt;
      if (gatt != null) {
         boolean wasConnected = this.connected;
         this.connectionState = 3;
         this.log(2, () -> {
            return wasConnected ? "Disconnecting..." : "Cancelling connection...";
         });
         BluetoothDevice device = gatt.getDevice();
         if (wasConnected) {
            this.postCallback((c) -> {
               c.onDeviceDisconnecting(device);
            });
            this.postConnectionStateChange((o) -> {
               o.onDeviceDisconnecting(device);
            });
         }

         this.log(3, () -> {
            return "gatt.disconnect()";
         });
         gatt.disconnect();
         if (wasConnected) {
            return true;
         }

         this.connectionState = 0;
         this.log(4, () -> {
            return "Disconnected";
         });
         this.close();
         this.postCallback((c) -> {
            c.onDeviceDisconnected(device);
         });
         this.postConnectionStateChange((o) -> {
            o.onDeviceDisconnected(device, reason);
         });
      }

      Request r = this.request;
      if (r != null && r.type == Request.Type.DISCONNECT) {
         if (this.bluetoothDevice == null && gatt == null) {
            r.notifyInvalidRequest();
         } else {
            r.notifySuccess(this.bluetoothDevice != null ? this.bluetoothDevice : gatt.getDevice());
         }
      }

      this.nextRequest(true);
      return true;
   }

   @RequiresPermission("android.permission.BLUETOOTH_ADMIN")
   private boolean internalCreateBond(boolean ensure) {
      BluetoothDevice device = this.bluetoothDevice;
      if (device == null) {
         return false;
      } else {
         if (ensure) {
            this.log(2, () -> {
               return "Ensuring bonding...";
            });
         } else {
            this.log(2, () -> {
               return "Starting bonding...";
            });
         }

         if (!ensure && device.getBondState() == 12) {
            this.log(5, () -> {
               return "Bond information present on client, skipping bonding";
            });
            this.request.notifySuccess(device);
            this.nextRequest(true);
            return true;
         } else {
            boolean result = this.createBond(device);
            if (ensure && !result) {
               Request bond = Request.createBond().setRequestHandler(this);
               bond.successCallback = this.request.successCallback;
               bond.invalidRequestCallback = this.request.invalidRequestCallback;
               bond.failCallback = this.request.failCallback;
               bond.internalSuccessCallback = this.request.internalSuccessCallback;
               bond.internalFailCallback = this.request.internalFailCallback;
               this.request.successCallback = null;
               this.request.invalidRequestCallback = null;
               this.request.failCallback = null;
               this.request.internalSuccessCallback = null;
               this.request.internalFailCallback = null;
               this.enqueueFirst(bond);
               this.enqueueFirst(Request.removeBond().setRequestHandler(this));
               this.nextRequest(true);
               return true;
            } else {
               return result;
            }
         }
      }
   }

   @RequiresPermission("android.permission.BLUETOOTH_ADMIN")
   private boolean createBond(@NonNull BluetoothDevice device) {
      if (VERSION.SDK_INT >= 19) {
         this.log(3, () -> {
            return "device.createBond()";
         });
         return device.createBond();
      } else {
         try {
            Method createBond = device.getClass().getMethod("createBond");
            this.log(3, () -> {
               return "device.createBond() (hidden)";
            });
            return createBond.invoke(device) == Boolean.TRUE;
         } catch (Exception var3) {
            Log.w("BleManager", "An exception occurred while creating bond", var3);
            return false;
         }
      }
   }

   @RequiresPermission("android.permission.BLUETOOTH_ADMIN")
   private boolean internalRemoveBond() {
      BluetoothDevice device = this.bluetoothDevice;
      if (device == null) {
         return false;
      } else {
         this.log(2, () -> {
            return "Removing bond information...";
         });
         if (device.getBondState() == 10) {
            this.log(5, () -> {
               return "Device is not bonded";
            });
            this.request.notifySuccess(device);
            this.nextRequest(true);
            return true;
         } else {
            try {
               Method removeBond = device.getClass().getMethod("removeBond");
               this.log(3, () -> {
                  return "device.removeBond() (hidden)";
               });
               this.userDisconnected = true;
               return removeBond.invoke(device) == Boolean.TRUE;
            } catch (Exception var3) {
               Log.w("BleManager", "An exception occurred while removing bond", var3);
               return false;
            }
         }
      }
   }

   private boolean ensureServiceChangedEnabled() {
      BluetoothGatt gatt = this.bluetoothGatt;
      if (gatt != null && this.connected) {
         BluetoothDevice device = gatt.getDevice();
         if (device.getBondState() != 12) {
            return false;
         } else {
            BluetoothGattService gaService = gatt.getService(BleManager.GENERIC_ATTRIBUTE_SERVICE);
            if (gaService == null) {
               return false;
            } else {
               BluetoothGattCharacteristic scCharacteristic = gaService.getCharacteristic(BleManager.SERVICE_CHANGED_CHARACTERISTIC);
               if (scCharacteristic == null) {
                  return false;
               } else {
                  this.log(4, () -> {
                     return "Service Changed characteristic found on a bonded device";
                  });
                  return this.internalEnableIndications(scCharacteristic);
               }
            }
         }
      } else {
         return false;
      }
   }

   private boolean internalEnableNotifications(@Nullable BluetoothGattCharacteristic characteristic) {
      BluetoothGatt gatt = this.bluetoothGatt;
      if (gatt != null && characteristic != null && this.connected) {
         BluetoothGattDescriptor descriptor = getCccd(characteristic, 16);
         if (descriptor != null) {
            this.log(3, () -> {
               return "gatt.setCharacteristicNotification(" + characteristic.getUuid() + ", true)";
            });
            gatt.setCharacteristicNotification(characteristic, true);
            this.log(2, () -> {
               return "Enabling notifications for " + characteristic.getUuid();
            });
            if (VERSION.SDK_INT >= 33) {
               this.log(3, () -> {
                  return "gatt.writeDescriptor(00002902-0000-1000-8000-00805f9b34fb, value=0x01-00)";
               });
               return gatt.writeDescriptor(descriptor, BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE) == 0;
            } else {
               this.log(3, () -> {
                  return "descriptor.setValue(0x01-00)";
               });
               descriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
               this.log(3, () -> {
                  return "gatt.writeDescriptor(00002902-0000-1000-8000-00805f9b34fb)";
               });
               return VERSION.SDK_INT >= 24 ? gatt.writeDescriptor(descriptor) : this.internalWriteDescriptorWorkaround(descriptor);
            }
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   private boolean internalDisableNotifications(@Nullable BluetoothGattCharacteristic characteristic) {
      BluetoothGatt gatt = this.bluetoothGatt;
      if (gatt != null && characteristic != null && this.connected) {
         BluetoothGattDescriptor descriptor = getCccd(characteristic, 48);
         if (descriptor != null) {
            this.log(3, () -> {
               return "gatt.setCharacteristicNotification(" + characteristic.getUuid() + ", false)";
            });
            gatt.setCharacteristicNotification(characteristic, false);
            this.log(2, () -> {
               return "Disabling notifications and indications for " + characteristic.getUuid();
            });
            if (VERSION.SDK_INT >= 33) {
               this.log(3, () -> {
                  return "gatt.writeDescriptor(00002902-0000-1000-8000-00805f9b34fb, value=0x00-00)";
               });
               return gatt.writeDescriptor(descriptor, BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE) == 0;
            } else {
               this.log(3, () -> {
                  return "descriptor.setValue(0x00-00)";
               });
               descriptor.setValue(BluetoothGattDescriptor.DISABLE_NOTIFICATION_VALUE);
               this.log(3, () -> {
                  return "gatt.writeDescriptor(00002902-0000-1000-8000-00805f9b34fb)";
               });
               return VERSION.SDK_INT >= 24 ? gatt.writeDescriptor(descriptor) : this.internalWriteDescriptorWorkaround(descriptor);
            }
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   private boolean internalEnableIndications(@Nullable BluetoothGattCharacteristic characteristic) {
      BluetoothGatt gatt = this.bluetoothGatt;
      if (gatt != null && characteristic != null && this.connected) {
         BluetoothGattDescriptor descriptor = getCccd(characteristic, 32);
         if (descriptor != null) {
            this.log(3, () -> {
               return "gatt.setCharacteristicNotification(" + characteristic.getUuid() + ", true)";
            });
            gatt.setCharacteristicNotification(characteristic, true);
            this.log(2, () -> {
               return "Enabling indications for " + characteristic.getUuid();
            });
            if (VERSION.SDK_INT >= 33) {
               this.log(3, () -> {
                  return "gatt.writeDescriptor(00002902-0000-1000-8000-00805f9b34fb, value=0x02-00)";
               });
               return gatt.writeDescriptor(descriptor, BluetoothGattDescriptor.ENABLE_INDICATION_VALUE) == 0;
            } else {
               this.log(3, () -> {
                  return "descriptor.setValue(0x02-00)";
               });
               descriptor.setValue(BluetoothGattDescriptor.ENABLE_INDICATION_VALUE);
               this.log(3, () -> {
                  return "gatt.writeDescriptor(00002902-0000-1000-8000-00805f9b34fb)";
               });
               return VERSION.SDK_INT >= 24 ? gatt.writeDescriptor(descriptor) : this.internalWriteDescriptorWorkaround(descriptor);
            }
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   private boolean internalDisableIndications(@Nullable BluetoothGattCharacteristic characteristic) {
      return this.internalDisableNotifications(characteristic);
   }

   private boolean internalSendNotification(@Nullable BluetoothGattCharacteristic serverCharacteristic, boolean confirm, @Nullable byte[] data) {
      if (this.serverManager != null && this.serverManager.getServer() != null && serverCharacteristic != null) {
         int requiredProperty = confirm ? 32 : 16;
         if ((serverCharacteristic.getProperties() & requiredProperty) == 0) {
            return false;
         } else {
            BluetoothGattDescriptor cccd = serverCharacteristic.getDescriptor(BleManager.CLIENT_CHARACTERISTIC_CONFIG_DESCRIPTOR_UUID);
            if (cccd == null) {
               return false;
            } else {
               byte[] value = this.descriptorValues != null && this.descriptorValues.containsKey(cccd) ? (byte[])this.descriptorValues.get(cccd) : cccd.getValue();
               if (value != null && value.length == 2 && value[0] != 0) {
                  this.log(2, () -> {
                     return "[Server] Sending " + (confirm ? "indication" : "notification") + " to " + serverCharacteristic.getUuid();
                  });
                  if (VERSION.SDK_INT >= 33) {
                     this.log(3, () -> {
                        return "[Server] gattServer.notifyCharacteristicChanged(" + serverCharacteristic.getUuid() + ", confirm=" + confirm + ", value=" + ParserUtils.parseDebug(data) + ")";
                     });
                     return this.serverManager.getServer().notifyCharacteristicChanged(this.bluetoothDevice, serverCharacteristic, confirm, data) == 0;
                  } else {
                     this.log(3, () -> {
                        return "[Server] characteristic.setValue(" + ParserUtils.parseDebug(data) + ")";
                     });
                     serverCharacteristic.setValue(data);
                     this.log(3, () -> {
                        return "[Server] gattServer.notifyCharacteristicChanged(" + serverCharacteristic.getUuid() + ", confirm=" + confirm + ")";
                     });
                     boolean result = this.serverManager.getServer().notifyCharacteristicChanged(this.bluetoothDevice, serverCharacteristic, confirm);
                     if (result && VERSION.SDK_INT < 21) {
                        this.post(() -> {
                           this.notifyNotificationSent(this.bluetoothDevice);
                           this.nextRequest(true);
                        });
                     }

                     return result;
                  }
               } else {
                  this.nextRequest(true);
                  return true;
               }
            }
         }
      } else {
         return false;
      }
   }

   private static BluetoothGattDescriptor getCccd(@Nullable BluetoothGattCharacteristic characteristic, int requiredProperty) {
      if (characteristic == null) {
         return null;
      } else {
         int properties = characteristic.getProperties();
         return (properties & requiredProperty) == 0 ? null : characteristic.getDescriptor(BleManager.CLIENT_CHARACTERISTIC_CONFIG_DESCRIPTOR_UUID);
      }
   }

   private boolean internalReadCharacteristic(@Nullable BluetoothGattCharacteristic characteristic) {
      BluetoothGatt gatt = this.bluetoothGatt;
      if (gatt != null && characteristic != null && this.connected) {
         int properties = characteristic.getProperties();
         if ((properties & 2) == 0) {
            return false;
         } else {
            this.log(2, () -> {
               return "Reading characteristic " + characteristic.getUuid();
            });
            this.log(3, () -> {
               return "gatt.readCharacteristic(" + characteristic.getUuid() + ")";
            });
            return gatt.readCharacteristic(characteristic);
         }
      } else {
         return false;
      }
   }

   private boolean internalWriteCharacteristic(@Nullable BluetoothGattCharacteristic characteristic, @Nullable byte[] data, int writeType) {
      BluetoothGatt gatt = this.bluetoothGatt;
      if (gatt != null && characteristic != null && this.connected) {
         int properties = characteristic.getProperties();
         if ((properties & 12) == 0) {
            return false;
         } else if (VERSION.SDK_INT >= 33) {
            this.log(2, () -> {
               return "Writing characteristic " + characteristic.getUuid() + " (" + ParserUtils.writeTypeToString(writeType) + ")";
            });
            this.log(3, () -> {
               return "gatt.writeCharacteristic(" + characteristic.getUuid() + ", value=" + ParserUtils.parseDebug(data) + ", " + ParserUtils.writeTypeToString(writeType) + ")";
            });
            return gatt.writeCharacteristic(characteristic, data, writeType) == 0;
         } else {
            this.log(2, () -> {
               return "Writing characteristic " + characteristic.getUuid() + " (" + ParserUtils.writeTypeToString(writeType) + ")";
            });
            this.log(3, () -> {
               return "characteristic.setValue(" + ParserUtils.parseDebug(data) + ")";
            });
            characteristic.setValue(data);
            this.log(3, () -> {
               return "characteristic.setWriteType(" + ParserUtils.writeTypeToString(writeType) + ")";
            });
            characteristic.setWriteType(writeType);
            this.log(3, () -> {
               return "gatt.writeCharacteristic(" + characteristic.getUuid() + ")";
            });
            return gatt.writeCharacteristic(characteristic);
         }
      } else {
         return false;
      }
   }

   private boolean internalReadDescriptor(@Nullable BluetoothGattDescriptor descriptor) {
      BluetoothGatt gatt = this.bluetoothGatt;
      if (gatt != null && descriptor != null && this.connected) {
         this.log(2, () -> {
            return "Reading descriptor " + descriptor.getUuid();
         });
         this.log(3, () -> {
            return "gatt.readDescriptor(" + descriptor.getUuid() + ")";
         });
         return gatt.readDescriptor(descriptor);
      } else {
         return false;
      }
   }

   private boolean internalWriteDescriptor(@Nullable BluetoothGattDescriptor descriptor, @Nullable byte[] data) {
      BluetoothGatt gatt = this.bluetoothGatt;
      if (gatt != null && descriptor != null && this.connected) {
         this.log(2, () -> {
            return "Writing descriptor " + descriptor.getUuid();
         });
         if (VERSION.SDK_INT >= 33) {
            this.log(3, () -> {
               return "gatt.writeDescriptor(" + descriptor.getUuid() + ", value=" + ParserUtils.parseDebug(data) + ")";
            });
            return gatt.writeDescriptor(descriptor, data) == 0;
         } else {
            this.log(3, () -> {
               return "descriptor.setValue(" + descriptor.getUuid() + ")";
            });
            descriptor.setValue(data);
            this.log(3, () -> {
               return "gatt.writeDescriptor(" + descriptor.getUuid() + ")";
            });
            return VERSION.SDK_INT >= 24 ? this.internalWriteDescriptorWorkaround(descriptor) : gatt.writeDescriptor(descriptor);
         }
      } else {
         return false;
      }
   }

   private boolean internalWriteDescriptorWorkaround(@Nullable BluetoothGattDescriptor descriptor) {
      BluetoothGatt gatt = this.bluetoothGatt;
      if (gatt != null && descriptor != null && this.connected) {
         BluetoothGattCharacteristic parentCharacteristic = descriptor.getCharacteristic();
         int originalWriteType = parentCharacteristic.getWriteType();
         parentCharacteristic.setWriteType(2);
         boolean result = gatt.writeDescriptor(descriptor);
         parentCharacteristic.setWriteType(originalWriteType);
         return result;
      } else {
         return false;
      }
   }

   private boolean internalBeginReliableWrite() {
      BluetoothGatt gatt = this.bluetoothGatt;
      if (gatt != null && this.connected) {
         if (this.reliableWriteInProgress) {
            return true;
         } else {
            this.log(2, () -> {
               return "Beginning reliable write...";
            });
            this.log(3, () -> {
               return "gatt.beginReliableWrite()";
            });
            return this.reliableWriteInProgress = gatt.beginReliableWrite();
         }
      } else {
         return false;
      }
   }

   private boolean internalExecuteReliableWrite() {
      BluetoothGatt gatt = this.bluetoothGatt;
      if (gatt != null && this.connected) {
         if (!this.reliableWriteInProgress) {
            return false;
         } else {
            this.log(2, () -> {
               return "Executing reliable write...";
            });
            this.log(3, () -> {
               return "gatt.executeReliableWrite()";
            });
            return gatt.executeReliableWrite();
         }
      } else {
         return false;
      }
   }

   private boolean internalAbortReliableWrite() {
      BluetoothGatt gatt = this.bluetoothGatt;
      if (gatt != null && this.connected) {
         if (!this.reliableWriteInProgress) {
            return false;
         } else {
            this.log(2, () -> {
               return "Aborting reliable write...";
            });
            if (VERSION.SDK_INT >= 19) {
               this.log(3, () -> {
                  return "gatt.abortReliableWrite()";
               });
               gatt.abortReliableWrite();
            } else {
               this.log(3, () -> {
                  return "gatt.abortReliableWrite(device)";
               });
               gatt.abortReliableWrite(gatt.getDevice());
            }

            return true;
         }
      } else {
         return false;
      }
   }

   /** @deprecated */
   @Deprecated
   private boolean internalReadBatteryLevel() {
      BluetoothGatt gatt = this.bluetoothGatt;
      if (gatt != null && this.connected) {
         BluetoothGattService batteryService = gatt.getService(BleManager.BATTERY_SERVICE);
         if (batteryService == null) {
            return false;
         } else {
            BluetoothGattCharacteristic batteryLevelCharacteristic = batteryService.getCharacteristic(BleManager.BATTERY_LEVEL_CHARACTERISTIC);
            return this.internalReadCharacteristic(batteryLevelCharacteristic);
         }
      } else {
         return false;
      }
   }

   /** @deprecated */
   @Deprecated
   private boolean internalSetBatteryNotifications(boolean enable) {
      BluetoothGatt gatt = this.bluetoothGatt;
      if (gatt != null && this.connected) {
         BluetoothGattService batteryService = gatt.getService(BleManager.BATTERY_SERVICE);
         if (batteryService == null) {
            return false;
         } else {
            BluetoothGattCharacteristic batteryLevelCharacteristic = batteryService.getCharacteristic(BleManager.BATTERY_LEVEL_CHARACTERISTIC);
            return enable ? this.internalEnableNotifications(batteryLevelCharacteristic) : this.internalDisableNotifications(batteryLevelCharacteristic);
         }
      } else {
         return false;
      }
   }

   @RequiresApi(
      api = 21
   )
   private boolean internalRequestMtu(@IntRange(from = 23L,to = 517L) int mtu) {
      BluetoothGatt gatt = this.bluetoothGatt;
      if (gatt != null && this.connected) {
         this.log(2, () -> {
            return "Requesting new MTU...";
         });
         this.log(3, () -> {
            return "gatt.requestMtu(" + mtu + ")";
         });
         return gatt.requestMtu(mtu);
      } else {
         return false;
      }
   }

   @RequiresApi(
      api = 21
   )
   private boolean internalRequestConnectionPriority(int priority) {
      BluetoothGatt gatt = this.bluetoothGatt;
      if (gatt != null && this.connected) {
         int supervisionTimeout = VERSION.SDK_INT >= 26 ? 5 : 20;
         this.log(2, () -> {
            String text;
            switch(priority) {
            case 0:
            default:
               text = "BALANCED (30–50ms, 0, " + supervisionTimeout + "s)";
               break;
            case 1:
               text = VERSION.SDK_INT >= 23 ? "HIGH (11.25–15ms, 0, " + supervisionTimeout + "s)" : "HIGH (7.5–10ms, 0, " + supervisionTimeout + "s)";
               break;
            case 2:
               text = "LOW POWER (100–125ms, 2, " + supervisionTimeout + "s)";
            }

            return "Requesting connection priority: " + text + "...";
         });
         this.log(3, () -> {
            String text;
            switch(priority) {
            case 0:
            default:
               text = "BALANCED";
               break;
            case 1:
               text = "HIGH";
               break;
            case 2:
               text = "LOW POWER";
            }

            return "gatt.requestConnectionPriority(" + text + ")";
         });
         return gatt.requestConnectionPriority(priority);
      } else {
         return false;
      }
   }

   @RequiresApi(
      api = 26
   )
   private boolean internalSetPreferredPhy(int txPhy, int rxPhy, int phyOptions) {
      BluetoothGatt gatt = this.bluetoothGatt;
      if (gatt != null && this.connected) {
         this.log(2, () -> {
            return "Requesting preferred PHYs...";
         });
         this.log(3, () -> {
            return "gatt.setPreferredPhy(" + ParserUtils.phyMaskToString(txPhy) + ", " + ParserUtils.phyMaskToString(rxPhy) + ", coding option = " + ParserUtils.phyCodedOptionToString(phyOptions) + ")";
         });
         gatt.setPreferredPhy(txPhy, rxPhy, phyOptions);
         return true;
      } else {
         return false;
      }
   }

   @RequiresApi(
      api = 26
   )
   private boolean internalReadPhy() {
      BluetoothGatt gatt = this.bluetoothGatt;
      if (gatt != null && this.connected) {
         this.log(2, () -> {
            return "Reading PHY...";
         });
         this.log(3, () -> {
            return "gatt.readPhy()";
         });
         gatt.readPhy();
         return true;
      } else {
         return false;
      }
   }

   private boolean internalReadRssi() {
      BluetoothGatt gatt = this.bluetoothGatt;
      if (gatt != null && this.connected) {
         this.log(2, () -> {
            return "Reading remote RSSI...";
         });
         this.log(3, () -> {
            return "gatt.readRemoteRssi()";
         });
         return gatt.readRemoteRssi();
      } else {
         return false;
      }
   }

   @NonNull
   ValueChangedCallback getValueChangedCallback(@Nullable Object attribute) {
      ValueChangedCallback callback = (ValueChangedCallback)this.valueChangedCallbacks.get(attribute);
      if (callback == null) {
         callback = new ValueChangedCallback(this);
         if (attribute != null) {
            this.valueChangedCallbacks.put(attribute, callback);
         }
      } else if (this.bluetoothDevice != null) {
         callback.notifyClosed();
      }

      return callback;
   }

   void removeValueChangedCallback(@Nullable Object attribute) {
      ValueChangedCallback callback = (ValueChangedCallback)this.valueChangedCallbacks.remove(attribute);
      if (callback != null) {
         callback.notifyClosed();
      }

   }

   void setCharacteristicValue(@Nullable BluetoothGattCharacteristic serverCharacteristic, @Nullable DataProvider dataProvider) {
      if (serverCharacteristic != null) {
         if (dataProvider == null) {
            this.dataProviders.remove(serverCharacteristic);
         } else {
            this.dataProviders.put(serverCharacteristic, dataProvider);
         }

      }
   }

   void setDescriptorValue(@Nullable BluetoothGattDescriptor serverDescriptor, @Nullable DataProvider dataProvider) {
      if (serverDescriptor != null) {
         if (dataProvider == null) {
            this.dataProviders.remove(serverDescriptor);
         } else {
            this.dataProviders.put(serverDescriptor, dataProvider);
         }

      }
   }

   @RequiresApi(
      api = 26
   )
   void setConnectionParametersListener(@Nullable ConnectionParametersUpdatedCallback callback) {
      this.connectionParametersUpdatedCallback = callback;
      if (callback != null && this.bluetoothDevice != null && this.interval > 0) {
         callback.onConnectionUpdated(this.bluetoothDevice, this.interval, this.latency, this.timeout);
      }

   }

   /** @deprecated */
   @Deprecated
   DataReceivedCallback getBatteryLevelCallback() {
      return (device, data) -> {
         if (data.size() == 1) {
            int batteryLevel = data.getIntValue(17, 0);
            this.log(4, () -> {
               return "Battery Level received: " + batteryLevel + "%";
            });
            this.batteryValue = batteryLevel;
            this.onBatteryValueReceived(this.bluetoothGatt, batteryLevel);
            this.postCallback((c) -> {
               c.onBatteryValueReceived(device, batteryLevel);
            });
         }

      };
   }

   /** @deprecated */
   @Deprecated
   void setBatteryLevelNotificationCallback() {
      if (this.batteryLevelNotificationCallback == null) {
         this.batteryLevelNotificationCallback = (new ValueChangedCallback(this)).with((device, data) -> {
            if (data.size() == 1) {
               int batteryLevel = data.getIntValue(17, 0);
               this.batteryValue = batteryLevel;
               this.onBatteryValueReceived(this.bluetoothGatt, batteryLevel);
               this.postCallback((c) -> {
                  c.onBatteryValueReceived(device, batteryLevel);
               });
            }

         });
      }

   }

   private boolean internalRefreshDeviceCache() {
      BluetoothGatt gatt = this.bluetoothGatt;
      if (gatt == null) {
         return false;
      } else {
         this.log(2, () -> {
            return "Refreshing device cache...";
         });
         this.log(3, () -> {
            return "gatt.refresh() (hidden)";
         });

         try {
            Method refresh = gatt.getClass().getMethod("refresh");
            return refresh.invoke(gatt) == Boolean.TRUE;
         } catch (Exception var3) {
            Log.w("BleManager", "An exception occurred while refreshing device", var3);
            this.log(5, () -> {
               return "gatt.refresh() method not found";
            });
            return false;
         }
      }
   }

   private void enqueueFirst(@NonNull Request request) {
      RequestQueue rq = this.requestQueue;
      if (rq == null) {
         Deque<Request> queue = this.initialization && this.initQueue != null ? this.initQueue : this.taskQueue;
         queue.addFirst(request);
      } else {
         rq.addFirst(request);
      }

      request.enqueued = true;
      this.operationInProgress = false;
   }

   final void enqueue(@NonNull Request request) {
      if (!request.enqueued) {
         Deque<Request> queue = this.initialization && this.initQueue != null ? this.initQueue : this.taskQueue;
         queue.add(request);
         request.enqueued = true;
      }

      this.nextRequest(false);
   }

   final void cancelQueue() {
      this.taskQueue.clear();
      this.initQueue = null;
      this.initialization = false;
      BluetoothDevice device = this.bluetoothDevice;
      if (device != null) {
         if (this.operationInProgress) {
            this.cancelCurrent();
         }

         if (this.connectRequest != null) {
            this.connectRequest.notifyFail(device, -7);
            this.connectRequest = null;
            this.internalDisconnect(5);
         }

      }
   }

   final void cancelCurrent() {
      BluetoothDevice device = this.bluetoothDevice;
      if (device != null) {
         this.log(5, () -> {
            return "Request cancelled";
         });
         if (this.request instanceof TimeoutableRequest) {
            this.request.notifyFail(device, -7);
         }

         if (this.awaitingRequest != null) {
            this.awaitingRequest.notifyFail(device, -7);
            this.awaitingRequest = null;
         }

         if (this.requestQueue instanceof ReliableWriteRequest) {
            this.requestQueue.cancelQueue();
         } else if (this.requestQueue != null) {
            this.requestQueue.notifyFail(device, -7);
            this.requestQueue = null;
         }

         this.nextRequest(this.request == null || this.request.finished);
      }
   }

   final void onRequestTimeout(@NonNull BluetoothDevice device, @NonNull TimeoutableRequest tr) {
      if (tr instanceof SleepRequest) {
         tr.notifySuccess(device);
      } else {
         this.log(5, () -> {
            return "Request timed out";
         });
      }

      if (this.request instanceof TimeoutableRequest) {
         this.request.notifyFail(device, -5);
      }

      if (this.awaitingRequest != null) {
         this.awaitingRequest.notifyFail(device, -5);
         this.awaitingRequest = null;
      }

      tr.notifyFail(device, -5);
      if (tr.type == Request.Type.CONNECT) {
         this.connectRequest = null;
         this.internalDisconnect(10);
      } else if (tr.type == Request.Type.DISCONNECT) {
         this.close();
      } else {
         this.nextRequest(this.request == null || this.request.finished);
      }
   }

   public void post(@NonNull Runnable r) {
      this.handler.post(r);
   }

   public void postDelayed(@NonNull Runnable r, long delayMillis) {
      this.handler.postDelayed(r, delayMillis);
   }

   public void removeCallbacks(@NonNull Runnable r) {
      this.handler.removeCallbacks(r);
   }

   /** @deprecated */
   @Deprecated
   private void postCallback(@NonNull BleManagerHandler.CallbackRunnable r) {
      BleManagerCallbacks callbacks = this.manager.callbacks;
      if (callbacks != null) {
         this.post(() -> {
            r.run(callbacks);
         });
      }

   }

   private void postBondingStateChange(@NonNull BleManagerHandler.BondingObserverRunnable r) {
      BondingObserver observer = this.manager.bondingObserver;
      if (observer != null) {
         this.post(() -> {
            r.run(observer);
         });
      }

   }

   private void postConnectionStateChange(@NonNull BleManagerHandler.ConnectionObserverRunnable r) {
      ConnectionObserver observer = this.manager.connectionObserver;
      if (observer != null) {
         this.post(() -> {
            r.run(observer);
         });
      }

   }

   final int getConnectionState() {
      return this.connectionState;
   }

   final boolean isConnected() {
      return this.connected;
   }

   /** @deprecated */
   @Deprecated
   final int getBatteryValue() {
      return this.batteryValue;
   }

   final boolean isReady() {
      return this.ready;
   }

   final boolean isReliableWriteInProgress() {
      return this.reliableWriteInProgress;
   }

   final int getMtu() {
      return this.mtu;
   }

   final void overrideMtu(@IntRange(from = 23L,to = 517L) int mtu) {
      if (VERSION.SDK_INT >= 21) {
         this.mtu = mtu;
      }

   }

   /** @deprecated */
   protected abstract boolean isRequiredServiceSupported(@NonNull BluetoothGatt var1);

   /** @deprecated */
   @Deprecated
   protected boolean isOptionalServiceSupported(@NonNull BluetoothGatt gatt) {
      return false;
   }

   /** @deprecated */
   @Deprecated
   protected Deque<Request> initGatt(@NonNull BluetoothGatt gatt) {
      return null;
   }

   /** @deprecated */
   @Deprecated
   protected void initialize() {
   }

   /** @deprecated */
   @Deprecated
   protected void onServerReady(@NonNull BluetoothGattServer server) {
   }

   /** @deprecated */
   @Deprecated
   protected void onDeviceReady() {
   }

   /** @deprecated */
   @Deprecated
   protected void onManagerReady() {
   }

   /** @deprecated */
   @Deprecated
   protected void onDeviceDisconnected() {
   }

   /** @deprecated */
   @Deprecated
   protected abstract void onServicesInvalidated();

   private void notifyDeviceDisconnected(@NonNull BluetoothDevice device, int status) {
      boolean wasConnected = this.connected;
      this.connected = false;
      this.ready = false;
      this.servicesDiscovered = false;
      this.serviceDiscoveryRequested = false;
      this.deviceNotSupported = false;
      this.mtu = 23;
      this.interval = this.latency = this.timeout = 0;
      this.connectionState = 0;
      this.checkCondition();
      if (!wasConnected) {
         this.log(5, () -> {
            return "Connection attempt timed out";
         });
         this.close();
         this.postCallback((c) -> {
            c.onDeviceDisconnected(device);
         });
         this.postConnectionStateChange((o) -> {
            o.onDeviceFailedToConnect(device, status);
         });
      } else if (this.userDisconnected) {
         this.log(4, () -> {
            return "Disconnected";
         });
         Request request = this.request;
         if (request == null || request.type != Request.Type.REMOVE_BOND) {
            this.close();
         }

         this.postCallback((c) -> {
            c.onDeviceDisconnected(device);
         });
         this.postConnectionStateChange((o) -> {
            o.onDeviceDisconnected(device, status);
         });
         if (request != null && request.type == Request.Type.DISCONNECT) {
            request.notifySuccess(device);
            this.request = null;
         }
      } else {
         this.log(5, () -> {
            return "Connection lost";
         });
         this.postCallback((c) -> {
            c.onLinkLossOccurred(device);
         });
         int reason = status == 2 ? 2 : 3;
         this.postConnectionStateChange((o) -> {
            o.onDeviceDisconnected(device, reason);
         });
      }

      Iterator var7 = this.valueChangedCallbacks.values().iterator();

      while(var7.hasNext()) {
         ValueChangedCallback callback = (ValueChangedCallback)var7.next();
         callback.notifyClosed();
      }

      this.valueChangedCallbacks.clear();
      this.dataProviders.clear();
      this.batteryLevelNotificationCallback = null;
      this.batteryValue = -1;
      this.manager.onServicesInvalidated();
      this.onDeviceDisconnected();
   }

   /** @deprecated */
   @Deprecated
   protected void onCharacteristicRead(@NonNull BluetoothGatt gatt, @NonNull BluetoothGattCharacteristic characteristic) {
   }

   /** @deprecated */
   @Deprecated
   protected void onCharacteristicWrite(@NonNull BluetoothGatt gatt, @NonNull BluetoothGattCharacteristic characteristic) {
   }

   /** @deprecated */
   @Deprecated
   protected void onDescriptorRead(@NonNull BluetoothGatt gatt, @NonNull BluetoothGattDescriptor descriptor) {
   }

   /** @deprecated */
   @Deprecated
   protected void onDescriptorWrite(@NonNull BluetoothGatt gatt, @NonNull BluetoothGattDescriptor descriptor) {
   }

   /** @deprecated */
   @Deprecated
   protected void onBatteryValueReceived(@NonNull BluetoothGatt gatt, @IntRange(from = 0L,to = 100L) int value) {
   }

   /** @deprecated */
   @Deprecated
   protected void onCharacteristicNotified(@NonNull BluetoothGatt gatt, @NonNull BluetoothGattCharacteristic characteristic) {
   }

   /** @deprecated */
   @Deprecated
   protected void onCharacteristicIndicated(@NonNull BluetoothGatt gatt, @NonNull BluetoothGattCharacteristic characteristic) {
   }

   /** @deprecated */
   @Deprecated
   protected void onMtuChanged(@NonNull BluetoothGatt gatt, @IntRange(from = 23L,to = 517L) int mtu) {
   }

   /** @deprecated */
   @Deprecated
   @TargetApi(26)
   protected void onConnectionUpdated(@NonNull BluetoothGatt gatt, @IntRange(from = 6L,to = 3200L) int interval, @IntRange(from = 0L,to = 499L) int latency, @IntRange(from = 10L,to = 3200L) int timeout) {
   }

   private void onError(BluetoothDevice device, String message, int errorCode) {
      this.log(6, () -> {
         return "Error (0x" + Integer.toHexString(errorCode) + "): " + GattError.parse(errorCode);
      });
      this.postCallback((c) -> {
         c.onError(device, message, errorCode);
      });
   }

   private int mapDisconnectStatusToReason(int status) {
      switch(status) {
      case 0:
         return 0;
      case 8:
         return 10;
      case 19:
         return 2;
      case 22:
         return 1;
      default:
         return -1;
      }
   }

   final void onCharacteristicReadRequest(@NonNull BluetoothGattServer server, @NonNull BluetoothDevice device, int requestId, int offset, @NonNull BluetoothGattCharacteristic characteristic) {
      this.log(3, () -> {
         return "[Server callback] Read request for characteristic " + characteristic.getUuid() + " (requestId=" + requestId + ", offset: " + offset + ")";
      });
      if (offset == 0) {
         this.log(4, () -> {
            return "[Server] READ request for characteristic " + characteristic.getUuid() + " received";
         });
      }

      DataProvider dataProvider = (DataProvider)this.dataProviders.get(characteristic);
      byte[] data = offset == 0 && dataProvider != null ? dataProvider.getData(device) : null;
      if (data != null) {
         this.assign(characteristic, data);
      } else {
         data = this.characteristicValues != null && this.characteristicValues.containsKey(characteristic) ? (byte[])this.characteristicValues.get(characteristic) : characteristic.getValue();
      }

      WaitForReadRequest waitForReadRequest = null;
      if (this.awaitingRequest instanceof WaitForReadRequest && this.awaitingRequest.characteristic == characteristic && !this.awaitingRequest.isTriggerPending()) {
         waitForReadRequest = (WaitForReadRequest)this.awaitingRequest;
         waitForReadRequest.setDataIfNull(data);
         data = waitForReadRequest.getData(this.mtu);
      }

      if (data != null && data.length > this.mtu - 1) {
         data = Bytes.copy(data, offset, this.mtu - 1);
      }

      this.sendResponse(server, device, 0, requestId, offset, data);
      if (waitForReadRequest != null) {
         waitForReadRequest.notifyPacketRead(device, data);
         if (!waitForReadRequest.hasMore() && (data == null || data.length < this.mtu - 1)) {
            this.log(4, () -> {
               return "Wait for read complete";
            });
            waitForReadRequest.notifySuccess(device);
            this.awaitingRequest = null;
            this.nextRequest(true);
         }
      } else if (this.checkCondition()) {
         this.nextRequest(true);
      }

   }

   final void onCharacteristicWriteRequest(@NonNull BluetoothGattServer server, @NonNull BluetoothDevice device, int requestId, @NonNull BluetoothGattCharacteristic characteristic, boolean preparedWrite, boolean responseNeeded, int offset, @NonNull byte[] value) {
      this.log(3, () -> {
         return "[Server callback] Write " + (responseNeeded ? "request" : "command") + " to characteristic " + characteristic.getUuid() + " (requestId=" + requestId + ", prepareWrite=" + preparedWrite + ", responseNeeded=" + responseNeeded + ", offset: " + offset + ", value=" + ParserUtils.parseDebug(value) + ")";
      });
      if (offset == 0) {
         this.log(4, () -> {
            String type = responseNeeded ? "WRITE REQUEST" : "WRITE COMMAND";
            String option = preparedWrite ? "Prepare " : "";
            return "[Server] " + option + type + " for characteristic " + characteristic.getUuid() + " received, value: " + ParserUtils.parse(value);
         });
      }

      if (responseNeeded) {
         this.sendResponse(server, device, 0, requestId, offset, value);
      }

      if (preparedWrite) {
         if (this.preparedValues == null) {
            this.preparedValues = new LinkedList();
         }

         if (offset == 0) {
            this.preparedValues.offer(new Pair(characteristic, value));
         } else {
            Pair<Object, byte[]> last = (Pair)this.preparedValues.peekLast();
            if (last != null && characteristic.equals(last.first)) {
               this.preparedValues.pollLast();
               this.preparedValues.offer(new Pair(characteristic, Bytes.concat((byte[])last.second, value, offset)));
            } else {
               this.prepareError = 7;
            }
         }
      } else if (this.assignAndNotify(device, characteristic, value) || this.checkCondition()) {
         this.nextRequest(true);
      }

   }

   final void onDescriptorReadRequest(@NonNull BluetoothGattServer server, @NonNull BluetoothDevice device, int requestId, int offset, @NonNull BluetoothGattDescriptor descriptor) {
      this.log(3, () -> {
         return "[Server callback] Read request for descriptor " + descriptor.getUuid() + " (requestId=" + requestId + ", offset: " + offset + ")";
      });
      if (offset == 0) {
         this.log(4, () -> {
            return "[Server] READ request for descriptor " + descriptor.getUuid() + " received";
         });
      }

      DataProvider dataProvider = (DataProvider)this.dataProviders.get(descriptor);
      byte[] data = offset == 0 && dataProvider != null ? dataProvider.getData(device) : null;
      if (data != null) {
         this.assign(descriptor, data);
      } else {
         data = this.descriptorValues != null && this.descriptorValues.containsKey(descriptor) ? (byte[])this.descriptorValues.get(descriptor) : descriptor.getValue();
      }

      WaitForReadRequest waitForReadRequest = null;
      if (this.awaitingRequest instanceof WaitForReadRequest && this.awaitingRequest.descriptor == descriptor && !this.awaitingRequest.isTriggerPending()) {
         waitForReadRequest = (WaitForReadRequest)this.awaitingRequest;
         waitForReadRequest.setDataIfNull(data);
         data = waitForReadRequest.getData(this.mtu);
      }

      if (data != null && data.length > this.mtu - 1) {
         data = Bytes.copy(data, offset, this.mtu - 1);
      }

      this.sendResponse(server, device, 0, requestId, offset, data);
      if (waitForReadRequest != null) {
         waitForReadRequest.notifyPacketRead(device, data);
         if (!waitForReadRequest.hasMore() && (data == null || data.length < this.mtu - 1)) {
            waitForReadRequest.notifySuccess(device);
            this.awaitingRequest = null;
            this.nextRequest(true);
         }
      } else if (this.checkCondition()) {
         this.nextRequest(true);
      }

   }

   final void onDescriptorWriteRequest(@NonNull BluetoothGattServer server, @NonNull BluetoothDevice device, int requestId, @NonNull BluetoothGattDescriptor descriptor, boolean preparedWrite, boolean responseNeeded, int offset, @NonNull byte[] value) {
      this.log(3, () -> {
         return "[Server callback] Write " + (responseNeeded ? "request" : "command") + " to descriptor " + descriptor.getUuid() + " (requestId=" + requestId + ", prepareWrite=" + preparedWrite + ", responseNeeded=" + responseNeeded + ", offset: " + offset + ", value=" + ParserUtils.parseDebug(value) + ")";
      });
      if (offset == 0) {
         this.log(4, () -> {
            String type = responseNeeded ? "WRITE REQUEST" : "WRITE COMMAND";
            String option = preparedWrite ? "Prepare " : "";
            return "[Server] " + option + type + " request for descriptor " + descriptor.getUuid() + " received, value: " + ParserUtils.parse(value);
         });
      }

      if (responseNeeded) {
         this.sendResponse(server, device, 0, requestId, offset, value);
      }

      if (preparedWrite) {
         if (this.preparedValues == null) {
            this.preparedValues = new LinkedList();
         }

         if (offset == 0) {
            this.preparedValues.offer(new Pair(descriptor, value));
         } else {
            Pair<Object, byte[]> last = (Pair)this.preparedValues.peekLast();
            if (last != null && descriptor.equals(last.first)) {
               this.preparedValues.pollLast();
               this.preparedValues.offer(new Pair(descriptor, Bytes.concat((byte[])last.second, value, offset)));
            } else {
               this.prepareError = 7;
            }
         }
      } else if (this.assignAndNotify(device, descriptor, value) || this.checkCondition()) {
         this.nextRequest(true);
      }

   }

   final void onExecuteWrite(@NonNull BluetoothGattServer server, @NonNull BluetoothDevice device, int requestId, boolean execute) {
      this.log(3, () -> {
         return "[Server callback] Execute write request (requestId=" + requestId + ", execute=" + execute + ")";
      });
      if (execute) {
         Deque<Pair<Object, byte[]>> values = this.preparedValues;
         this.log(4, () -> {
            return "[Server] Execute write request received";
         });
         this.preparedValues = null;
         if (this.prepareError != 0) {
            this.sendResponse(server, device, this.prepareError, requestId, 0, (byte[])null);
            this.prepareError = 0;
            return;
         }

         this.sendResponse(server, device, 0, requestId, 0, (byte[])null);
         if (values == null || values.isEmpty()) {
            return;
         }

         boolean startNextRequest = false;
         Iterator var7 = values.iterator();

         while(true) {
            while(var7.hasNext()) {
               Pair<Object, byte[]> value = (Pair)var7.next();
               if (value.first instanceof BluetoothGattCharacteristic) {
                  BluetoothGattCharacteristic characteristic = (BluetoothGattCharacteristic)value.first;
                  startNextRequest = this.assignAndNotify(device, characteristic, (byte[])value.second) || startNextRequest;
               } else if (value.first instanceof BluetoothGattDescriptor) {
                  BluetoothGattDescriptor descriptor = (BluetoothGattDescriptor)value.first;
                  startNextRequest = this.assignAndNotify(device, descriptor, (byte[])value.second) || startNextRequest;
               }
            }

            if (this.checkCondition() || startNextRequest) {
               this.nextRequest(true);
            }
            break;
         }
      } else {
         this.log(4, () -> {
            return "[Server] Cancel write request received";
         });
         this.preparedValues = null;
         this.sendResponse(server, device, 0, requestId, 0, (byte[])null);
      }

   }

   final void onNotificationSent(@NonNull BluetoothGattServer server, @NonNull BluetoothDevice device, int status) {
      this.log(3, () -> {
         return "[Server callback] Notification sent (status=" + status + ")";
      });
      if (status == 0) {
         this.notifyNotificationSent(device);
      } else {
         Log.e("BleManager", "onNotificationSent error " + status);
         if (this.request instanceof WriteRequest) {
            this.request.notifyFail(device, status);
         }

         this.awaitingRequest = null;
         this.onError(device, "Error on sending notification/indication", status);
      }

      this.checkCondition();
      this.nextRequest(true);
   }

   @RequiresApi(
      api = 22
   )
   final void onMtuChanged(@NonNull BluetoothGattServer server, @NonNull BluetoothDevice device, int mtu) {
      this.log(4, () -> {
         return "[Server] MTU changed to: " + mtu;
      });
      this.mtu = mtu;
      this.checkCondition();
      this.nextRequest(false);
   }

   private void notifyNotificationSent(@NonNull BluetoothDevice device) {
      if (this.request instanceof WriteRequest) {
         WriteRequest wr = (WriteRequest)this.request;
         switch(wr.type) {
         case NOTIFY:
            this.log(4, () -> {
               return "[Server] Notification sent";
            });
            break;
         case INDICATE:
            this.log(4, () -> {
               return "[Server] Indication sent";
            });
         }

         wr.notifyPacketSent(device, wr.characteristic.getValue());
         if (wr.hasMore()) {
            this.enqueueFirst(wr);
         } else {
            wr.notifySuccess(device);
         }
      }

   }

   private void assign(@NonNull BluetoothGattCharacteristic characteristic, @NonNull byte[] value) {
      boolean isShared = this.characteristicValues == null || !this.characteristicValues.containsKey(characteristic);
      if (isShared) {
         characteristic.setValue(value);
      } else {
         this.characteristicValues.put(characteristic, value);
      }

   }

   private boolean assignAndNotify(@NonNull BluetoothDevice device, @NonNull BluetoothGattCharacteristic characteristic, @NonNull byte[] value) {
      this.assign(characteristic, value);
      ValueChangedCallback callback;
      if ((callback = (ValueChangedCallback)this.valueChangedCallbacks.get(characteristic)) != null) {
         callback.notifyValueChanged(device, value);
      }

      if (this.awaitingRequest instanceof WaitForValueChangedRequest && this.awaitingRequest.characteristic == characteristic && !this.awaitingRequest.isTriggerPending()) {
         WaitForValueChangedRequest waitForWrite = (WaitForValueChangedRequest)this.awaitingRequest;
         if (waitForWrite.matches(value)) {
            waitForWrite.notifyValueChanged(device, value);
            if (waitForWrite.isComplete()) {
               waitForWrite.notifySuccess(device);
               this.awaitingRequest = null;
               return waitForWrite.isTriggerCompleteOrNull();
            }
         }
      }

      return false;
   }

   private void assign(@NonNull BluetoothGattDescriptor descriptor, @NonNull byte[] value) {
      boolean isShared = this.descriptorValues == null || !this.descriptorValues.containsKey(descriptor);
      if (isShared) {
         descriptor.setValue(value);
      } else {
         this.descriptorValues.put(descriptor, value);
      }

   }

   private boolean assignAndNotify(@NonNull BluetoothDevice device, @NonNull BluetoothGattDescriptor descriptor, @NonNull byte[] value) {
      this.assign(descriptor, value);
      ValueChangedCallback callback;
      if ((callback = (ValueChangedCallback)this.valueChangedCallbacks.get(descriptor)) != null) {
         callback.notifyValueChanged(device, value);
      }

      if (this.awaitingRequest instanceof WaitForValueChangedRequest && this.awaitingRequest.descriptor == descriptor && !this.awaitingRequest.isTriggerPending()) {
         WaitForValueChangedRequest waitForWrite = (WaitForValueChangedRequest)this.awaitingRequest;
         if (waitForWrite.matches(value)) {
            waitForWrite.notifyValueChanged(device, value);
            if (waitForWrite.isComplete()) {
               waitForWrite.notifySuccess(device);
               this.awaitingRequest = null;
               return waitForWrite.isTriggerCompleteOrNull();
            }
         }
      }

      return false;
   }

   private void sendResponse(@NonNull BluetoothGattServer server, @NonNull BluetoothDevice device, int status, int requestId, int offset, @Nullable byte[] response) {
      String msg;
      switch(status) {
      case 0:
         msg = "GATT_SUCCESS";
         break;
      case 6:
         msg = "GATT_REQUEST_NOT_SUPPORTED";
         break;
      case 7:
         msg = "GATT_INVALID_OFFSET";
         break;
      default:
         throw new InvalidParameterException();
      }

      this.log(3, () -> {
         return "server.sendResponse(" + msg + ", offset=" + offset + ", value=" + ParserUtils.parseDebug(response) + ")";
      });
      server.sendResponse(device, requestId, status, offset, response);
      this.log(2, () -> {
         return "[Server] Response sent";
      });
   }

   private boolean checkCondition() {
      if (this.awaitingRequest instanceof ConditionalWaitRequest) {
         ConditionalWaitRequest<?> cwr = (ConditionalWaitRequest)this.awaitingRequest;
         if (cwr.isFulfilled()) {
            this.log(4, () -> {
               return "Condition fulfilled";
            });
            cwr.notifySuccess(this.bluetoothDevice);
            this.awaitingRequest = null;
            return true;
         }
      }

      return false;
   }

   @SuppressLint({"MissingPermission"})
   private synchronized void nextRequest(boolean force) {
      if (force && this.operationInProgress) {
         this.operationInProgress = this.awaitingRequest != null;
      }

      if (!this.operationInProgress) {
         BluetoothDevice bluetoothDevice = this.bluetoothDevice;
         Request request = null;

         try {
            if (this.requestQueue != null) {
               if (this.requestQueue.hasMore()) {
                  request = this.requestQueue.getNext().setRequestHandler(this);
               } else {
                  if (this.requestQueue instanceof ReliableWriteRequest) {
                     ReliableWriteRequest rwr = (ReliableWriteRequest)this.requestQueue;
                     if (rwr.isCancelled()) {
                        this.requestQueue.notifyFail(bluetoothDevice, -7);
                     }
                  }

                  this.requestQueue.notifySuccess(bluetoothDevice);
                  this.requestQueue = null;
               }
            }

            if (request == null) {
               request = this.initQueue != null ? (Request)this.initQueue.poll() : null;
            }
         } catch (Exception var9) {
            request = null;
         }

         if (request == null) {
            if (this.initQueue != null) {
               this.initQueue = null;
               this.operationInProgress = true;
               this.ready = true;
               this.manager.onDeviceReady();
               if (bluetoothDevice != null) {
                  this.postCallback((c) -> {
                     c.onDeviceReady(bluetoothDevice);
                  });
                  this.postConnectionStateChange((o) -> {
                     o.onDeviceReady(bluetoothDevice);
                  });
               }

               if (this.connectRequest != null) {
                  this.connectRequest.notifySuccess(this.connectRequest.getDevice());
                  this.connectRequest = null;
               }
            }

            try {
               request = (Request)this.taskQueue.remove();
            } catch (Exception var8) {
               this.operationInProgress = false;
               this.request = null;
               this.manager.onManagerReady();
               return;
            }
         }

         if (request.finished) {
            this.nextRequest(false);
         } else {
            boolean result = false;
            this.operationInProgress = true;
            this.request = request;
            if (request instanceof AwaitingRequest) {
               AwaitingRequest<?> r = (AwaitingRequest)request;
               int requiredProperty = 0;
               switch(request.type) {
               case WAIT_FOR_NOTIFICATION:
                  requiredProperty = 16;
                  break;
               case WAIT_FOR_INDICATION:
                  requiredProperty = 32;
                  break;
               case WAIT_FOR_READ:
                  requiredProperty = 2;
                  break;
               case WAIT_FOR_WRITE:
                  requiredProperty = 76;
               }

               result = this.connected && bluetoothDevice != null && (r.characteristic == null || (r.characteristic.getProperties() & requiredProperty) != 0);
               if (result) {
                  if (r instanceof ConditionalWaitRequest) {
                     ConditionalWaitRequest<?> cwr = (ConditionalWaitRequest)r;
                     this.log(2, () -> {
                        return "Waiting for fulfillment of condition...";
                     });
                     if (cwr.isFulfilled()) {
                        cwr.notifyStarted(bluetoothDevice);
                        this.log(4, () -> {
                           return "Condition fulfilled";
                        });
                        cwr.notifySuccess(bluetoothDevice);
                        this.nextRequest(true);
                        return;
                     }
                  }

                  if (r instanceof WaitForReadRequest) {
                     this.log(2, () -> {
                        return "Waiting for read request...";
                     });
                  }

                  if (r instanceof WaitForValueChangedRequest) {
                     this.log(2, () -> {
                        return "Waiting for value change...";
                     });
                  }

                  this.awaitingRequest = r;
                  if (r.getTrigger() != null) {
                     r.notifyStarted(bluetoothDevice);
                     this.request = request = r.getTrigger();
                  }
               }
            }

            ConnectRequest cr;
            if (request.type == Request.Type.CONNECT) {
               cr = (ConnectRequest)request;
               cr.notifyStarted(cr.getDevice());
            } else {
               if (bluetoothDevice == null) {
                  request.notifyInvalidRequest();
                  this.awaitingRequest = null;
                  this.nextRequest(true);
                  return;
               }

               request.notifyStarted(bluetoothDevice);
            }

            PhyRequest pr;
            SetValueRequest svr;
            WriteRequest wr;
            switch(request.type) {
            case NOTIFY:
            case INDICATE:
               wr = (WriteRequest)request;
               byte[] data = wr.getData(this.mtu);
               if (wr.characteristic != null) {
                  wr.characteristic.setValue(data);
                  if (this.characteristicValues != null && this.characteristicValues.containsKey(wr.characteristic)) {
                     this.characteristicValues.put(wr.characteristic, data);
                  }
               }

               result = this.internalSendNotification(wr.characteristic, request.type == Request.Type.INDICATE, data);
            case WAIT_FOR_NOTIFICATION:
            case WAIT_FOR_INDICATION:
            case WAIT_FOR_READ:
            case WAIT_FOR_WRITE:
            default:
               break;
            case CONNECT:
               cr = (ConnectRequest)request;
               this.connectRequest = cr;
               this.request = null;
               result = this.internalConnect(cr.getDevice(), cr);
               break;
            case DISCONNECT:
               result = this.internalDisconnect(0);
               break;
            case ENSURE_BOND:
               result = this.internalCreateBond(true);
               break;
            case CREATE_BOND:
               result = this.internalCreateBond(false);
               break;
            case REMOVE_BOND:
               result = this.internalRemoveBond();
               break;
            case SET:
               this.requestQueue = (RequestQueue)request;
               this.nextRequest(true);
               return;
            case READ:
               result = this.internalReadCharacteristic(request.characteristic);
               break;
            case WRITE:
               wr = (WriteRequest)request;
               result = this.internalWriteCharacteristic(wr.characteristic, wr.getData(this.mtu), wr.getWriteType());
               break;
            case READ_DESCRIPTOR:
               result = this.internalReadDescriptor(request.descriptor);
               break;
            case WRITE_DESCRIPTOR:
               wr = (WriteRequest)request;
               result = this.internalWriteDescriptor(wr.descriptor, wr.getData(this.mtu));
               break;
            case SET_VALUE:
               svr = (SetValueRequest)request;
               if (svr.characteristic != null) {
                  if (this.characteristicValues != null && this.characteristicValues.containsKey(svr.characteristic)) {
                     this.characteristicValues.put(svr.characteristic, svr.getData(this.mtu));
                  } else {
                     svr.characteristic.setValue(svr.getData(this.mtu));
                  }

                  result = true;
                  svr.notifySuccess(bluetoothDevice);
                  this.nextRequest(true);
               }
               break;
            case SET_DESCRIPTOR_VALUE:
               svr = (SetValueRequest)request;
               if (svr.descriptor != null) {
                  if (this.descriptorValues != null && this.descriptorValues.containsKey(svr.descriptor)) {
                     this.descriptorValues.put(svr.descriptor, svr.getData(this.mtu));
                  } else {
                     svr.descriptor.setValue(svr.getData(this.mtu));
                  }

                  result = true;
                  svr.notifySuccess(bluetoothDevice);
                  this.nextRequest(true);
               }
               break;
            case BEGIN_RELIABLE_WRITE:
               result = this.internalBeginReliableWrite();
               if (result) {
                  this.request.notifySuccess(bluetoothDevice);
                  this.nextRequest(true);
                  return;
               }
               break;
            case EXECUTE_RELIABLE_WRITE:
               result = this.internalExecuteReliableWrite();
               break;
            case ABORT_RELIABLE_WRITE:
               result = this.internalAbortReliableWrite();
               break;
            case ENABLE_NOTIFICATIONS:
               result = this.internalEnableNotifications(request.characteristic);
               break;
            case ENABLE_INDICATIONS:
               result = this.internalEnableIndications(request.characteristic);
               break;
            case DISABLE_NOTIFICATIONS:
               result = this.internalDisableNotifications(request.characteristic);
               break;
            case DISABLE_INDICATIONS:
               result = this.internalDisableIndications(request.characteristic);
               break;
            case READ_BATTERY_LEVEL:
               result = this.internalReadBatteryLevel();
               break;
            case ENABLE_BATTERY_LEVEL_NOTIFICATIONS:
               result = this.internalSetBatteryNotifications(true);
               break;
            case DISABLE_BATTERY_LEVEL_NOTIFICATIONS:
               result = this.internalSetBatteryNotifications(false);
               break;
            case ENABLE_SERVICE_CHANGED_INDICATIONS:
               result = this.ensureServiceChangedEnabled();
               break;
            case REQUEST_MTU:
               MtuRequest mr = (MtuRequest)request;
               if (this.mtu != mr.getRequiredMtu() && VERSION.SDK_INT >= 21) {
                  result = this.internalRequestMtu(mr.getRequiredMtu());
               } else {
                  result = this.connected;
                  if (result) {
                     mr.notifyMtuChanged(bluetoothDevice, this.mtu);
                     mr.notifySuccess(bluetoothDevice);
                     this.nextRequest(true);
                     return;
                  }
               }
               break;
            case REQUEST_CONNECTION_PRIORITY:
               ConnectionPriorityRequest cpr = (ConnectionPriorityRequest)request;
               this.connectionPriorityOperationInProgress = VERSION.SDK_INT >= 26;
               if (VERSION.SDK_INT >= 21) {
                  result = this.internalRequestConnectionPriority(cpr.getRequiredPriority());
                  if (result) {
                     this.postDelayed(() -> {
                        if (cpr.notifySuccess(bluetoothDevice)) {
                           this.connectionPriorityOperationInProgress = false;
                           this.nextRequest(true);
                        }

                     }, 200L);
                  } else {
                     this.connectionPriorityOperationInProgress = false;
                  }
               }
               break;
            case SET_PREFERRED_PHY:
               pr = (PhyRequest)request;
               if (VERSION.SDK_INT >= 26) {
                  result = this.internalSetPreferredPhy(pr.getPreferredTxPhy(), pr.getPreferredRxPhy(), pr.getPreferredPhyOptions());
                  if (VERSION.SDK_INT == 33) {
                     this.handler.postDelayed(() -> {
                        if (!pr.finished) {
                           this.log(5, () -> {
                              return "Callback not received in 1000 ms";
                           });
                           this.internalReadPhy();
                        }

                     }, 1000L);
                  }
               } else {
                  result = this.connected;
                  if (result) {
                     pr.notifyLegacyPhy(bluetoothDevice);
                     pr.notifySuccess(bluetoothDevice);
                     this.nextRequest(true);
                     return;
                  }
               }
               break;
            case READ_PHY:
               pr = (PhyRequest)request;
               if (VERSION.SDK_INT >= 26) {
                  result = this.internalReadPhy();
               } else {
                  result = this.connected;
                  if (result) {
                     pr.notifyLegacyPhy(bluetoothDevice);
                     pr.notifySuccess(bluetoothDevice);
                     this.nextRequest(true);
                     return;
                  }
               }
               break;
            case READ_RSSI:
               result = this.internalReadRssi();
               if (result) {
                  this.postDelayed(() -> {
                     if (this.request == request) {
                        request.notifyFail(bluetoothDevice, -5);
                        this.nextRequest(true);
                     }

                  }, 1000L);
               }
               break;
            case REFRESH_CACHE:
               result = this.internalRefreshDeviceCache();
               if (result) {
                  this.postDelayed(() -> {
                     this.log(4, () -> {
                        return "Cache refreshed";
                     });
                     request.notifySuccess(bluetoothDevice);
                     this.request = null;
                     if (this.awaitingRequest != null) {
                        this.awaitingRequest.notifyFail(bluetoothDevice, -3);
                        this.awaitingRequest = null;
                     }

                     this.taskQueue.clear();
                     this.initQueue = null;
                     BluetoothGatt bluetoothGatt = this.bluetoothGatt;
                     if (this.connected && bluetoothGatt != null) {
                        this.manager.onServicesInvalidated();
                        this.onDeviceDisconnected();
                        this.serviceDiscoveryRequested = true;
                        this.servicesDiscovered = false;
                        this.log(2, () -> {
                           return "Discovering Services...";
                        });
                        this.log(3, () -> {
                           return "gatt.discoverServices()";
                        });
                        bluetoothGatt.discoverServices();
                     }

                  }, 200L);
               }
               break;
            case SLEEP:
               SleepRequest sr = (SleepRequest)request;
               this.log(3, () -> {
                  return "sleep(" + sr.timeout + ")";
               });
               result = true;
            }

            if (!result && bluetoothDevice != null) {
               request.notifyFail(bluetoothDevice, this.connected ? -3 : (BluetoothAdapter.getDefaultAdapter().isEnabled() ? -1 : -100));
               this.awaitingRequest = null;
               this.connectionPriorityOperationInProgress = false;
               this.nextRequest(true);
            }

         }
      }
   }

   private boolean isServiceChangedCCCD(@Nullable BluetoothGattDescriptor descriptor) {
      return descriptor != null && BleManager.SERVICE_CHANGED_CHARACTERISTIC.equals(descriptor.getCharacteristic().getUuid());
   }

   private boolean isServiceChangedCharacteristic(@Nullable BluetoothGattCharacteristic characteristic) {
      return characteristic != null && BleManager.SERVICE_CHANGED_CHARACTERISTIC.equals(characteristic.getUuid());
   }

   /** @deprecated */
   @Deprecated
   private boolean isBatteryLevelCharacteristic(@Nullable BluetoothGattCharacteristic characteristic) {
      return characteristic != null && BleManager.BATTERY_LEVEL_CHARACTERISTIC.equals(characteristic.getUuid());
   }

   private boolean isCCCD(@Nullable BluetoothGattDescriptor descriptor) {
      return descriptor != null && BleManager.CLIENT_CHARACTERISTIC_CONFIG_DESCRIPTOR_UUID.equals(descriptor.getUuid());
   }

   private void log(int priority, @NonNull BleManagerHandler.Loggable message) {
      if (priority >= this.manager.getMinLogPriority()) {
         this.manager.log(priority, message.log());
      }

   }

   @FunctionalInterface
   private interface Loggable {
      String log();
   }

   private interface ConnectionObserverRunnable {
      void run(@NonNull ConnectionObserver var1);
   }

   private interface BondingObserverRunnable {
      void run(@NonNull BondingObserver var1);
   }

   /** @deprecated */
   @Deprecated
   private interface CallbackRunnable {
      void run(@NonNull BleManagerCallbacks var1);
   }
}
