/*      */ package no.nordicsemi.android.ble;
/*      */ 
/*      */ import android.annotation.SuppressLint;
/*      */ import android.annotation.TargetApi;
/*      */ import android.bluetooth.BluetoothAdapter;
/*      */ import android.bluetooth.BluetoothDevice;
/*      */ import android.bluetooth.BluetoothGatt;
/*      */ import android.bluetooth.BluetoothGattCallback;
/*      */ import android.bluetooth.BluetoothGattCharacteristic;
/*      */ import android.bluetooth.BluetoothGattDescriptor;
/*      */ import android.bluetooth.BluetoothGattServer;
/*      */ import android.bluetooth.BluetoothGattService;
/*      */ import android.content.BroadcastReceiver;
/*      */ import android.content.Context;
/*      */ import android.content.Intent;
/*      */ import android.content.IntentFilter;
/*      */ import android.os.Build;
/*      */ import android.os.Handler;
/*      */ import android.os.SystemClock;
/*      */ import android.util.Log;
/*      */ import android.util.Pair;
/*      */ import androidx.annotation.IntRange;
/*      */ import androidx.annotation.Keep;
/*      */ import androidx.annotation.NonNull;
/*      */ import androidx.annotation.Nullable;
/*      */ import androidx.annotation.RequiresApi;
/*      */ import androidx.annotation.RequiresPermission;
/*      */ import java.lang.reflect.Method;
/*      */ import java.security.InvalidParameterException;
/*      */ import java.util.Deque;
/*      */ import java.util.HashMap;
/*      */ import java.util.LinkedList;
/*      */ import java.util.Map;
/*      */ import java.util.concurrent.LinkedBlockingDeque;
/*      */ import no.nordicsemi.android.ble.callback.ConnectionParametersUpdatedCallback;
/*      */ import no.nordicsemi.android.ble.callback.DataReceivedCallback;
/*      */ import no.nordicsemi.android.ble.data.Data;
/*      */ import no.nordicsemi.android.ble.data.DataProvider;
/*      */ import no.nordicsemi.android.ble.error.GattError;
/*      */ import no.nordicsemi.android.ble.observer.BondingObserver;
/*      */ import no.nordicsemi.android.ble.observer.ConnectionObserver;
/*      */ import no.nordicsemi.android.ble.utils.ParserUtils;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ @SuppressLint({"MissingPermission"})
/*      */ abstract class BleManagerHandler
/*      */   extends RequestHandler
/*      */ {
/*      */   private static final String TAG = "BleManager";
/*      */   private static final String ERROR_CONNECTION_STATE_CHANGE = "Error on connection state change";
/*      */   private static final String ERROR_DISCOVERY_SERVICE = "Error on discovering services";
/*      */   private static final String ERROR_AUTH_ERROR_WHILE_BONDED = "Phone has lost bonding information";
/*      */   private static final String ERROR_READ_CHARACTERISTIC = "Error on reading characteristic";
/*      */   private static final String ERROR_WRITE_CHARACTERISTIC = "Error on writing characteristic";
/*      */   private static final String ERROR_READ_DESCRIPTOR = "Error on reading descriptor";
/*      */   private static final String ERROR_WRITE_DESCRIPTOR = "Error on writing descriptor";
/*      */   private static final String ERROR_MTU_REQUEST = "Error on mtu request";
/*      */   private static final String ERROR_CONNECTION_PRIORITY_REQUEST = "Error on connection priority request";
/*      */   private static final String ERROR_READ_RSSI = "Error on RSSI read";
/*      */   private static final String ERROR_READ_PHY = "Error on PHY read";
/*      */   private static final String ERROR_PHY_UPDATE = "Error on PHY update";
/*      */   private static final String ERROR_RELIABLE_WRITE = "Error on Execute Reliable Write";
/*      */   private static final String ERROR_NOTIFY = "Error on sending notification/indication";
/*   81 */   private final Object LOCK = new Object();
/*      */   
/*      */   private BluetoothDevice bluetoothDevice;
/*      */   private BluetoothGatt bluetoothGatt;
/*      */   private BleManager manager;
/*      */   private BleServerManager serverManager;
/*      */   private Handler handler;
/*   88 */   private final Deque<Request> taskQueue = new LinkedBlockingDeque<>();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Deque<Request> initQueue;
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean initialization;
/*      */ 
/*      */ 
/*      */   
/*      */   private static final long CONNECTION_TIMEOUT_THRESHOLD = 20000L;
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean servicesDiscovered;
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean deviceNotSupported;
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean serviceDiscoveryRequested;
/*      */ 
/*      */ 
/*      */   
/*      */   private long connectionTime;
/*      */ 
/*      */ 
/*      */   
/*  121 */   private int connectionCount = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean connected;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean ready;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean operationInProgress;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean userDisconnected;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean initialConnection;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  156 */   private int connectionState = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean connectionPriorityOperationInProgress = false;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean reliableWriteInProgress;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  174 */   private int mtu = 23;
/*      */ 
/*      */   
/*      */   private int interval;
/*      */   
/*      */   private int latency;
/*      */   
/*      */   private int timeout;
/*      */   
/*      */   @Deprecated
/*      */   @IntRange(from = -1L, to = 100L)
/*  185 */   private int batteryValue = -1;
/*      */ 
/*      */ 
/*      */   
/*      */   private Map<BluetoothGattCharacteristic, byte[]> characteristicValues;
/*      */ 
/*      */ 
/*      */   
/*      */   private Map<BluetoothGattDescriptor, byte[]> descriptorValues;
/*      */ 
/*      */ 
/*      */   
/*      */   private Deque<Pair<Object, byte[]>> preparedValues;
/*      */ 
/*      */ 
/*      */   
/*      */   private int prepareError;
/*      */ 
/*      */ 
/*      */   
/*      */   private ConnectRequest connectRequest;
/*      */ 
/*      */ 
/*      */   
/*      */   private Request request;
/*      */ 
/*      */ 
/*      */   
/*      */   private RequestQueue requestQueue;
/*      */ 
/*      */ 
/*      */   
/*      */   @NonNull
/*  218 */   private final HashMap<Object, ValueChangedCallback> valueChangedCallbacks = new HashMap<>();
/*      */ 
/*      */ 
/*      */   
/*      */   @NonNull
/*  223 */   private final HashMap<Object, DataProvider> dataProviders = new HashMap<>();
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   private ConnectionParametersUpdatedCallback connectionParametersUpdatedCallback;
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   @Nullable
/*      */   private ValueChangedCallback batteryLevelNotificationCallback;
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   private AwaitingRequest<?> awaitingRequest;
/*      */ 
/*      */ 
/*      */   
/*  243 */   private final BroadcastReceiver bluetoothStateBroadcastReceiver = new BroadcastReceiver()
/*      */     {
/*      */       public void onReceive(Context context, Intent intent) {
/*  246 */         int state = intent.getIntExtra("android.bluetooth.adapter.extra.STATE", 10);
/*  247 */         int previousState = intent.getIntExtra("android.bluetooth.adapter.extra.PREVIOUS_STATE", 10);
/*      */         
/*  249 */         BleManagerHandler.this.log(3, () -> "[Broadcast] Action received: android.bluetooth.adapter.action.STATE_CHANGED, state changed to " + state2String(state));
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  254 */         switch (state) {
/*      */           case 10:
/*      */           case 13:
/*  257 */             if (previousState != 13 && previousState != 10) {
/*      */ 
/*      */               
/*  260 */               BleManagerHandler.this.operationInProgress = true;
/*  261 */               BleManagerHandler.this.taskQueue.clear();
/*  262 */               BleManagerHandler.this.initQueue = null;
/*      */               
/*  264 */               boolean wasConnected = BleManagerHandler.this.connected;
/*  265 */               BleManagerHandler.this.connected = false;
/*  266 */               BleManagerHandler.this.ready = false;
/*  267 */               BleManagerHandler.this.connectionState = 0;
/*      */               
/*  269 */               BluetoothDevice device = BleManagerHandler.this.bluetoothDevice;
/*  270 */               if (device != null) {
/*      */                 
/*  272 */                 if (BleManagerHandler.this.request != null && BleManagerHandler.this.request.type != Request.Type.DISCONNECT) {
/*  273 */                   BleManagerHandler.this.request.notifyFail(device, -100);
/*  274 */                   BleManagerHandler.this.request = null;
/*      */                 } 
/*  276 */                 if (BleManagerHandler.this.awaitingRequest != null) {
/*  277 */                   BleManagerHandler.this.awaitingRequest.notifyFail(device, -100);
/*  278 */                   BleManagerHandler.this.awaitingRequest = null;
/*      */                 } 
/*  280 */                 if (BleManagerHandler.this.connectRequest != null) {
/*  281 */                   BleManagerHandler.this.connectRequest.notifyFail(device, -100);
/*  282 */                   BleManagerHandler.this.connectRequest = null;
/*      */                 } 
/*      */               } 
/*      */ 
/*      */               
/*  287 */               BleManagerHandler.this.userDisconnected = true;
/*      */ 
/*      */ 
/*      */               
/*  291 */               BleManagerHandler.this.operationInProgress = false;
/*      */               
/*  293 */               if (device != null) {
/*  294 */                 BleManagerHandler.this.connected = wasConnected;
/*  295 */                 BleManagerHandler.this.notifyDeviceDisconnected(device, 1);
/*      */               } 
/*      */               
/*      */               break;
/*      */             } 
/*  300 */             BleManagerHandler.this.close();
/*      */             break;
/*      */         } 
/*      */       }
/*      */ 
/*      */       
/*      */       private String state2String(int state) {
/*  307 */         switch (state) {
/*      */           case 11:
/*  309 */             return "TURNING ON";
/*      */           case 12:
/*  311 */             return "ON";
/*      */           case 13:
/*  313 */             return "TURNING OFF";
/*      */           case 10:
/*  315 */             return "OFF";
/*      */         } 
/*  317 */         return "UNKNOWN (" + state + ")";
/*      */       }
/*      */     };
/*      */ 
/*      */   
/*  322 */   private final BroadcastReceiver mBondingBroadcastReceiver = new BroadcastReceiver()
/*      */     {
/*      */       public void onReceive(Context context, Intent intent) {
/*  325 */         BluetoothDevice device = (BluetoothDevice)intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE");
/*  326 */         int bondState = intent.getIntExtra("android.bluetooth.device.extra.BOND_STATE", -1);
/*  327 */         int previousBondState = intent.getIntExtra("android.bluetooth.device.extra.PREVIOUS_BOND_STATE", -1);
/*      */ 
/*      */         
/*  330 */         if (BleManagerHandler.this.bluetoothDevice == null || device == null || 
/*  331 */           !device.getAddress().equals(BleManagerHandler.this.bluetoothDevice.getAddress())) {
/*      */           return;
/*      */         }
/*      */         
/*  335 */         BleManagerHandler.this.log(3, () -> "[Broadcast] Action received: android.bluetooth.device.action.BOND_STATE_CHANGED, bond state changed to: " + ParserUtils.bondStateToString(bondState) + " (" + bondState + ")");
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  340 */         switch (bondState) {
/*      */           case 10:
/*  342 */             if (previousBondState == 11) {
/*  343 */               BleManagerHandler.this.postCallback(c -> c.onBondingFailed(device));
/*  344 */               BleManagerHandler.this.postBondingStateChange(o -> o.onBondingFailed(device));
/*  345 */               BleManagerHandler.this.log(5, () -> "Bonding failed");
/*  346 */               if (BleManagerHandler.this.request != null && BleManagerHandler.this.request.type == Request.Type.CREATE_BOND) {
/*  347 */                 BleManagerHandler.this.request.notifyFail(device, -4);
/*  348 */                 BleManagerHandler.this.request = null;
/*      */               } 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/*  354 */               if (!BleManagerHandler.this.servicesDiscovered && !BleManagerHandler.this.serviceDiscoveryRequested) {
/*  355 */                 BleManagerHandler.this.post(() -> {
/*      */                       BluetoothGatt bluetoothGatt = BleManagerHandler.this.bluetoothGatt; if (!BleManagerHandler.this.servicesDiscovered && !BleManagerHandler.this.serviceDiscoveryRequested && bluetoothGatt != null) {
/*      */                         BleManagerHandler.this.serviceDiscoveryRequested = true; BleManagerHandler.this.log(2, ());
/*      */                         BleManagerHandler.this.log(3, ());
/*      */                         bluetoothGatt.discoverServices();
/*      */                       } 
/*      */                     });
/*      */                 return;
/*      */               } 
/*      */               break;
/*      */             } 
/*  366 */             if (previousBondState == 12) {
/*      */               
/*  368 */               BleManagerHandler.this.userDisconnected = true;
/*      */               
/*  370 */               if (BleManagerHandler.this.request != null && BleManagerHandler.this.request.type == Request.Type.REMOVE_BOND) {
/*      */                 
/*  372 */                 BleManagerHandler.this.log(4, () -> "Bond information removed");
/*  373 */                 BleManagerHandler.this.request.notifySuccess(device);
/*  374 */                 BleManagerHandler.this.request = null;
/*      */               } 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/*  380 */               if (!BleManagerHandler.this.isConnected()) {
/*  381 */                 BleManagerHandler.this.close();
/*      */               }
/*      */             } 
/*      */             break;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           case 11:
/*  392 */             BleManagerHandler.this.postCallback(c -> c.onBondingRequired(device));
/*  393 */             BleManagerHandler.this.postBondingStateChange(o -> o.onBondingRequired(device));
/*      */             return;
/*      */           case 12:
/*  396 */             BleManagerHandler.this.log(4, () -> "Device bonded");
/*  397 */             BleManagerHandler.this.postCallback(c -> c.onBonded(device));
/*  398 */             BleManagerHandler.this.postBondingStateChange(o -> o.onBonded(device));
/*  399 */             if (BleManagerHandler.this.request != null && BleManagerHandler.this.request.type == Request.Type.CREATE_BOND) {
/*  400 */               BleManagerHandler.this.request.notifySuccess(device);
/*  401 */               BleManagerHandler.this.request = null;
/*      */               
/*      */               break;
/*      */             } 
/*      */             
/*  406 */             if (!BleManagerHandler.this.servicesDiscovered && !BleManagerHandler.this.serviceDiscoveryRequested) {
/*  407 */               BleManagerHandler.this.post(() -> {
/*      */                     BluetoothGatt bluetoothGatt = BleManagerHandler.this.bluetoothGatt;
/*      */ 
/*      */ 
/*      */                     
/*      */                     if (!BleManagerHandler.this.servicesDiscovered && !BleManagerHandler.this.serviceDiscoveryRequested && bluetoothGatt != null) {
/*      */                       BleManagerHandler.this.serviceDiscoveryRequested = true;
/*      */ 
/*      */ 
/*      */                       
/*      */                       BleManagerHandler.this.log(2, ());
/*      */ 
/*      */ 
/*      */                       
/*      */                       BleManagerHandler.this.log(3, ());
/*      */ 
/*      */                       
/*      */                       bluetoothGatt.discoverServices();
/*      */                     } 
/*      */                   });
/*      */ 
/*      */               
/*      */               return;
/*      */             } 
/*      */ 
/*      */             
/*  433 */             if (Build.VERSION.SDK_INT < 26 && 
/*  434 */               BleManagerHandler.this.request != null) {
/*      */               
/*  436 */               BleManagerHandler.this.enqueueFirst(BleManagerHandler.this.request);
/*      */               break;
/*      */             } 
/*      */             return;
/*      */         } 
/*      */ 
/*      */         
/*  443 */         BleManagerHandler.this.nextRequest(true);
/*      */       }
/*      */     };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void init(@NonNull BleManager manager, @NonNull Handler handler) {
/*  453 */     this.manager = manager;
/*  454 */     this.handler = handler;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void useServer(@Nullable BleServerManager server) {
/*  463 */     this.serverManager = server;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void attachClientConnection(BluetoothDevice clientDevice) {
/*  472 */     if (this.bluetoothDevice != null) {
/*  473 */       log(6, () -> "attachClientConnection called on existing connection, call ignored");
/*      */     } else {
/*  475 */       this.bluetoothDevice = clientDevice;
/*      */ 
/*      */       
/*  478 */       initializeServerAttributes();
/*      */       
/*  480 */       this.manager.initialize();
/*      */     } 
/*      */   }
/*      */   
/*      */   private void initializeServerAttributes() {
/*  485 */     if (this.serverManager != null) {
/*  486 */       BluetoothGattServer server = this.serverManager.getServer();
/*  487 */       if (server != null) {
/*  488 */         for (BluetoothGattService service : server.getServices()) {
/*  489 */           for (BluetoothGattCharacteristic characteristic : service.getCharacteristics()) {
/*  490 */             if (!this.serverManager.isShared(characteristic)) {
/*  491 */               if (this.characteristicValues == null)
/*  492 */                 this.characteristicValues = (Map)new HashMap<>(); 
/*  493 */               this.characteristicValues.put(characteristic, characteristic.getValue());
/*      */             } 
/*  495 */             for (BluetoothGattDescriptor descriptor : characteristic.getDescriptors()) {
/*  496 */               if (!this.serverManager.isShared(descriptor)) {
/*  497 */                 if (this.descriptorValues == null)
/*  498 */                   this.descriptorValues = (Map)new HashMap<>(); 
/*  499 */                 this.descriptorValues.put(descriptor, descriptor.getValue());
/*      */               } 
/*      */             } 
/*      */           } 
/*      */         } 
/*  504 */         this.manager.onServerReady(server);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void close() {
/*      */     try {
/*  514 */       Context context = this.manager.getContext();
/*  515 */       context.unregisterReceiver(this.bluetoothStateBroadcastReceiver);
/*  516 */       context.unregisterReceiver(this.mBondingBroadcastReceiver);
/*  517 */     } catch (Exception exception) {}
/*      */ 
/*      */     
/*  520 */     synchronized (this.LOCK) {
/*  521 */       if (this.bluetoothGatt != null) {
/*  522 */         if (this.manager.shouldClearCacheWhenDisconnected()) {
/*  523 */           if (internalRefreshDeviceCache()) {
/*  524 */             log(4, () -> "Cache refreshed");
/*      */           } else {
/*  526 */             log(5, () -> "Refreshing failed");
/*      */           } 
/*      */         }
/*  529 */         log(3, () -> "gatt.close()");
/*      */         try {
/*  531 */           this.bluetoothGatt.close();
/*  532 */         } catch (Throwable throwable) {}
/*      */ 
/*      */         
/*  535 */         this.bluetoothGatt = null;
/*      */       } 
/*  537 */       this.reliableWriteInProgress = false;
/*  538 */       this.initialConnection = false;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  543 */       this.taskQueue.clear();
/*  544 */       this.initQueue = null;
/*  545 */       this.initialization = false;
/*  546 */       this.bluetoothDevice = null;
/*  547 */       this.connected = false;
/*      */     } 
/*      */   }
/*      */   
/*      */   public BluetoothDevice getBluetoothDevice() {
/*  552 */     return this.bluetoothDevice;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public final byte[] getCharacteristicValue(@NonNull BluetoothGattCharacteristic serverCharacteristic) {
/*  564 */     if (this.characteristicValues != null && this.characteristicValues.containsKey(serverCharacteristic))
/*  565 */       return this.characteristicValues.get(serverCharacteristic); 
/*  566 */     return serverCharacteristic.getValue();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public final byte[] getDescriptorValue(@NonNull BluetoothGattDescriptor serverDescriptor) {
/*  578 */     if (this.descriptorValues != null && this.descriptorValues.containsKey(serverDescriptor))
/*  579 */       return this.descriptorValues.get(serverDescriptor); 
/*  580 */     return serverDescriptor.getValue();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean internalConnect(@NonNull BluetoothDevice device, @Nullable ConnectRequest connectRequest) {
/*  587 */     boolean bluetoothEnabled = BluetoothAdapter.getDefaultAdapter().isEnabled();
/*  588 */     if (this.connected || !bluetoothEnabled) {
/*  589 */       BluetoothDevice currentDevice = this.bluetoothDevice;
/*  590 */       if (bluetoothEnabled && currentDevice != null && currentDevice.equals(device)) {
/*  591 */         if (this.connectRequest != null) {
/*  592 */           this.connectRequest.notifySuccess(device);
/*      */         
/*      */         }
/*      */       
/*      */       }
/*  597 */       else if (this.connectRequest != null) {
/*  598 */         this.connectRequest.notifyFail(device, 
/*  599 */             bluetoothEnabled ? 
/*  600 */             -4 : 
/*  601 */             -100);
/*      */       } 
/*      */       
/*  604 */       this.connectRequest = null;
/*  605 */       nextRequest(true);
/*  606 */       return true;
/*      */     } 
/*      */     
/*  609 */     Context context = this.manager.getContext();
/*  610 */     synchronized (this.LOCK) {
/*  611 */       if (this.bluetoothGatt != null) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  621 */         if (!this.initialConnection) {
/*  622 */           log(3, () -> "gatt.close()");
/*      */           try {
/*  624 */             this.bluetoothGatt.close();
/*  625 */           } catch (Throwable throwable) {}
/*      */ 
/*      */           
/*  628 */           this.bluetoothGatt = null;
/*      */           try {
/*  630 */             log(3, () -> "wait(200)");
/*  631 */             Thread.sleep(200L);
/*  632 */           } catch (InterruptedException interruptedException) {}
/*      */ 
/*      */         
/*      */         }
/*      */         else {
/*      */ 
/*      */           
/*  639 */           this.initialConnection = false;
/*  640 */           this.connectionTime = 0L;
/*  641 */           this.connectionState = 1;
/*  642 */           log(2, () -> "Connecting...");
/*  643 */           postCallback(c -> c.onDeviceConnecting(device));
/*  644 */           postConnectionStateChange(o -> o.onDeviceConnecting(device));
/*  645 */           log(3, () -> "gatt.connect()");
/*  646 */           this.bluetoothGatt.connect();
/*  647 */           return true;
/*      */         }
/*      */       
/*  650 */       } else if (connectRequest != null) {
/*      */         
/*  652 */         context.registerReceiver(this.bluetoothStateBroadcastReceiver, new IntentFilter("android.bluetooth.adapter.action.STATE_CHANGED"));
/*      */         
/*  654 */         context.registerReceiver(this.mBondingBroadcastReceiver, new IntentFilter("android.bluetooth.device.action.BOND_STATE_CHANGED"));
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  662 */     if (connectRequest == null)
/*  663 */       return false; 
/*  664 */     boolean shouldAutoConnect = connectRequest.shouldAutoConnect();
/*      */     
/*  666 */     this.userDisconnected = !shouldAutoConnect;
/*      */ 
/*      */ 
/*      */     
/*  670 */     if (shouldAutoConnect) {
/*  671 */       this.initialConnection = true;
/*      */     }
/*  673 */     this.bluetoothDevice = device;
/*  674 */     log(2, () -> connectRequest.isFirstAttempt() ? "Connecting..." : "Retrying...");
/*  675 */     this.connectionState = 1;
/*  676 */     postCallback(c -> c.onDeviceConnecting(device));
/*  677 */     postConnectionStateChange(o -> o.onDeviceConnecting(device));
/*  678 */     this.connectionTime = SystemClock.elapsedRealtime();
/*  679 */     if (Build.VERSION.SDK_INT > 26) {
/*      */       
/*  681 */       int preferredPhy = connectRequest.getPreferredPhy();
/*  682 */       log(3, () -> "gatt = device.connectGatt(autoConnect = false, TRANSPORT_LE, " + ParserUtils.phyMaskToString(preferredPhy) + ")");
/*      */ 
/*      */ 
/*      */       
/*  686 */       this.bluetoothGatt = device.connectGatt(context, false, this.gattCallback, 2, preferredPhy, this.handler);
/*      */     }
/*  688 */     else if (Build.VERSION.SDK_INT == 26) {
/*      */       
/*  690 */       int preferredPhy = connectRequest.getPreferredPhy();
/*  691 */       log(3, () -> "gatt = device.connectGatt(autoConnect = false, TRANSPORT_LE, " + ParserUtils.phyMaskToString(preferredPhy) + ")");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  697 */       this.bluetoothGatt = device.connectGatt(context, false, this.gattCallback, 2, preferredPhy);
/*      */     }
/*  699 */     else if (Build.VERSION.SDK_INT >= 23) {
/*  700 */       log(3, () -> "gatt = device.connectGatt(autoConnect = false, TRANSPORT_LE)");
/*  701 */       this.bluetoothGatt = device.connectGatt(context, false, this.gattCallback, 2);
/*      */     } else {
/*      */       
/*  704 */       log(3, () -> "gatt = device.connectGatt(autoConnect = false)");
/*  705 */       this.bluetoothGatt = device.connectGatt(context, false, this.gattCallback);
/*      */     } 
/*  707 */     return true;
/*      */   }
/*      */   
/*      */   private boolean internalDisconnect(int reason) {
/*  711 */     this.userDisconnected = true;
/*  712 */     this.initialConnection = false;
/*  713 */     this.ready = false;
/*      */     
/*  715 */     BluetoothGatt gatt = this.bluetoothGatt;
/*  716 */     if (gatt != null) {
/*  717 */       boolean wasConnected = this.connected;
/*  718 */       this.connectionState = 3;
/*  719 */       log(2, () -> wasConnected ? "Disconnecting..." : "Cancelling connection...");
/*  720 */       BluetoothDevice device = gatt.getDevice();
/*  721 */       if (wasConnected) {
/*  722 */         postCallback(c -> c.onDeviceDisconnecting(device));
/*  723 */         postConnectionStateChange(o -> o.onDeviceDisconnecting(device));
/*      */       } 
/*  725 */       log(3, () -> "gatt.disconnect()");
/*  726 */       gatt.disconnect();
/*  727 */       if (wasConnected) {
/*  728 */         return true;
/*      */       }
/*      */ 
/*      */       
/*  732 */       this.connectionState = 0;
/*  733 */       log(4, () -> "Disconnected");
/*  734 */       close();
/*  735 */       postCallback(c -> c.onDeviceDisconnected(device));
/*  736 */       postConnectionStateChange(o -> o.onDeviceDisconnected(device, reason));
/*      */     } 
/*      */ 
/*      */     
/*  740 */     Request r = this.request;
/*  741 */     if (r != null && r.type == Request.Type.DISCONNECT)
/*  742 */       if (this.bluetoothDevice != null || gatt != null) {
/*  743 */         r.notifySuccess((this.bluetoothDevice != null) ? this.bluetoothDevice : gatt.getDevice());
/*      */       } else {
/*  745 */         r.notifyInvalidRequest();
/*      */       }  
/*  747 */     nextRequest(true);
/*  748 */     return true;
/*      */   }
/*      */   
/*      */   @RequiresPermission("android.permission.BLUETOOTH_ADMIN")
/*      */   private boolean internalCreateBond(boolean ensure) {
/*  753 */     BluetoothDevice device = this.bluetoothDevice;
/*  754 */     if (device == null) {
/*  755 */       return false;
/*      */     }
/*  757 */     if (ensure) {
/*  758 */       log(2, () -> "Ensuring bonding...");
/*      */     } else {
/*  760 */       log(2, () -> "Starting bonding...");
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  778 */     if (!ensure && device.getBondState() == 12) {
/*  779 */       log(5, () -> "Bond information present on client, skipping bonding");
/*  780 */       this.request.notifySuccess(device);
/*  781 */       nextRequest(true);
/*  782 */       return true;
/*      */     } 
/*  784 */     boolean result = createBond(device);
/*  785 */     if (ensure && !result) {
/*      */ 
/*      */       
/*  788 */       Request bond = Request.createBond().setRequestHandler(this);
/*      */       
/*  790 */       bond.successCallback = this.request.successCallback;
/*  791 */       bond.invalidRequestCallback = this.request.invalidRequestCallback;
/*  792 */       bond.failCallback = this.request.failCallback;
/*  793 */       bond.internalSuccessCallback = this.request.internalSuccessCallback;
/*  794 */       bond.internalFailCallback = this.request.internalFailCallback;
/*  795 */       this.request.successCallback = null;
/*  796 */       this.request.invalidRequestCallback = null;
/*  797 */       this.request.failCallback = null;
/*  798 */       this.request.internalSuccessCallback = null;
/*  799 */       this.request.internalFailCallback = null;
/*  800 */       enqueueFirst(bond);
/*      */       
/*  802 */       enqueueFirst(Request.removeBond().setRequestHandler(this));
/*  803 */       nextRequest(true);
/*  804 */       return true;
/*      */     } 
/*  806 */     return result;
/*      */   }
/*      */   
/*      */   @RequiresPermission("android.permission.BLUETOOTH_ADMIN")
/*      */   private boolean createBond(@NonNull BluetoothDevice device) {
/*  811 */     if (Build.VERSION.SDK_INT >= 19) {
/*  812 */       log(3, () -> "device.createBond()");
/*  813 */       return device.createBond();
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  820 */       Method createBond = device.getClass().getMethod("createBond", new Class[0]);
/*  821 */       log(3, () -> "device.createBond() (hidden)");
/*  822 */       return (createBond.invoke(device, new Object[0]) == Boolean.TRUE);
/*  823 */     } catch (Exception e) {
/*  824 */       Log.w("BleManager", "An exception occurred while creating bond", e);
/*  825 */       return false;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   @RequiresPermission("android.permission.BLUETOOTH_ADMIN")
/*      */   private boolean internalRemoveBond() {
/*  832 */     BluetoothDevice device = this.bluetoothDevice;
/*  833 */     if (device == null) {
/*  834 */       return false;
/*      */     }
/*  836 */     log(2, () -> "Removing bond information...");
/*      */     
/*  838 */     if (device.getBondState() == 10) {
/*  839 */       log(5, () -> "Device is not bonded");
/*  840 */       this.request.notifySuccess(device);
/*  841 */       nextRequest(true);
/*  842 */       return true;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  851 */       Method removeBond = device.getClass().getMethod("removeBond", new Class[0]);
/*  852 */       log(3, () -> "device.removeBond() (hidden)");
/*      */       
/*  854 */       this.userDisconnected = true;
/*  855 */       return (removeBond.invoke(device, new Object[0]) == Boolean.TRUE);
/*  856 */     } catch (Exception e) {
/*  857 */       Log.w("BleManager", "An exception occurred while removing bond", e);
/*      */       
/*  859 */       return false;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean ensureServiceChangedEnabled() {
/*  872 */     BluetoothGatt gatt = this.bluetoothGatt;
/*  873 */     if (gatt == null || !this.connected) {
/*  874 */       return false;
/*      */     }
/*      */     
/*  877 */     BluetoothDevice device = gatt.getDevice();
/*  878 */     if (device.getBondState() != 12) {
/*  879 */       return false;
/*      */     }
/*  881 */     BluetoothGattService gaService = gatt.getService(BleManager.GENERIC_ATTRIBUTE_SERVICE);
/*  882 */     if (gaService == null) {
/*  883 */       return false;
/*      */     }
/*      */     
/*  886 */     BluetoothGattCharacteristic scCharacteristic = gaService.getCharacteristic(BleManager.SERVICE_CHANGED_CHARACTERISTIC);
/*  887 */     if (scCharacteristic == null) {
/*  888 */       return false;
/*      */     }
/*  890 */     log(4, () -> "Service Changed characteristic found on a bonded device");
/*  891 */     return internalEnableIndications(scCharacteristic);
/*      */   }
/*      */   
/*      */   private boolean internalEnableNotifications(@Nullable BluetoothGattCharacteristic characteristic) {
/*  895 */     BluetoothGatt gatt = this.bluetoothGatt;
/*  896 */     if (gatt == null || characteristic == null || !this.connected) {
/*  897 */       return false;
/*      */     }
/*  899 */     BluetoothGattDescriptor descriptor = getCccd(characteristic, 16);
/*  900 */     if (descriptor != null) {
/*  901 */       log(3, () -> "gatt.setCharacteristicNotification(" + characteristic.getUuid() + ", true)");
/*  902 */       gatt.setCharacteristicNotification(characteristic, true);
/*      */       
/*  904 */       log(2, () -> "Enabling notifications for " + characteristic.getUuid());
/*  905 */       if (Build.VERSION.SDK_INT >= 33) {
/*  906 */         log(3, () -> "gatt.writeDescriptor(00002902-0000-1000-8000-00805f9b34fb, value=0x01-00)");
/*      */         
/*  908 */         return (gatt.writeDescriptor(descriptor, BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE) == 0);
/*      */       } 
/*  910 */       log(3, () -> "descriptor.setValue(0x01-00)");
/*  911 */       descriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
/*  912 */       log(3, () -> "gatt.writeDescriptor(00002902-0000-1000-8000-00805f9b34fb)");
/*  913 */       if (Build.VERSION.SDK_INT >= 24) {
/*  914 */         return gatt.writeDescriptor(descriptor);
/*      */       }
/*  916 */       return internalWriteDescriptorWorkaround(descriptor);
/*      */     } 
/*      */ 
/*      */     
/*  920 */     return false;
/*      */   }
/*      */   
/*      */   private boolean internalDisableNotifications(@Nullable BluetoothGattCharacteristic characteristic) {
/*  924 */     BluetoothGatt gatt = this.bluetoothGatt;
/*  925 */     if (gatt == null || characteristic == null || !this.connected) {
/*  926 */       return false;
/*      */     }
/*  928 */     BluetoothGattDescriptor descriptor = getCccd(characteristic, 48);
/*      */     
/*  930 */     if (descriptor != null) {
/*  931 */       log(3, () -> "gatt.setCharacteristicNotification(" + characteristic.getUuid() + ", false)");
/*  932 */       gatt.setCharacteristicNotification(characteristic, false);
/*      */       
/*  934 */       log(2, () -> "Disabling notifications and indications for " + characteristic.getUuid());
/*  935 */       if (Build.VERSION.SDK_INT >= 33) {
/*  936 */         log(3, () -> "gatt.writeDescriptor(00002902-0000-1000-8000-00805f9b34fb, value=0x00-00)");
/*      */         
/*  938 */         return (gatt.writeDescriptor(descriptor, BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE) == 0);
/*      */       } 
/*  940 */       log(3, () -> "descriptor.setValue(0x00-00)");
/*  941 */       descriptor.setValue(BluetoothGattDescriptor.DISABLE_NOTIFICATION_VALUE);
/*  942 */       log(3, () -> "gatt.writeDescriptor(00002902-0000-1000-8000-00805f9b34fb)");
/*  943 */       if (Build.VERSION.SDK_INT >= 24) {
/*  944 */         return gatt.writeDescriptor(descriptor);
/*      */       }
/*  946 */       return internalWriteDescriptorWorkaround(descriptor);
/*      */     } 
/*      */ 
/*      */     
/*  950 */     return false;
/*      */   }
/*      */   
/*      */   private boolean internalEnableIndications(@Nullable BluetoothGattCharacteristic characteristic) {
/*  954 */     BluetoothGatt gatt = this.bluetoothGatt;
/*  955 */     if (gatt == null || characteristic == null || !this.connected) {
/*  956 */       return false;
/*      */     }
/*  958 */     BluetoothGattDescriptor descriptor = getCccd(characteristic, 32);
/*  959 */     if (descriptor != null) {
/*  960 */       log(3, () -> "gatt.setCharacteristicNotification(" + characteristic.getUuid() + ", true)");
/*  961 */       gatt.setCharacteristicNotification(characteristic, true);
/*      */       
/*  963 */       log(2, () -> "Enabling indications for " + characteristic.getUuid());
/*  964 */       if (Build.VERSION.SDK_INT >= 33) {
/*  965 */         log(3, () -> "gatt.writeDescriptor(00002902-0000-1000-8000-00805f9b34fb, value=0x02-00)");
/*      */         
/*  967 */         return (gatt.writeDescriptor(descriptor, BluetoothGattDescriptor.ENABLE_INDICATION_VALUE) == 0);
/*      */       } 
/*  969 */       log(3, () -> "descriptor.setValue(0x02-00)");
/*  970 */       descriptor.setValue(BluetoothGattDescriptor.ENABLE_INDICATION_VALUE);
/*  971 */       log(3, () -> "gatt.writeDescriptor(00002902-0000-1000-8000-00805f9b34fb)");
/*  972 */       if (Build.VERSION.SDK_INT >= 24) {
/*  973 */         return gatt.writeDescriptor(descriptor);
/*      */       }
/*  975 */       return internalWriteDescriptorWorkaround(descriptor);
/*      */     } 
/*      */ 
/*      */     
/*  979 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   private boolean internalDisableIndications(@Nullable BluetoothGattCharacteristic characteristic) {
/*  984 */     return internalDisableNotifications(characteristic);
/*      */   }
/*      */ 
/*      */   
/*      */   private boolean internalSendNotification(@Nullable BluetoothGattCharacteristic serverCharacteristic, boolean confirm, @Nullable byte[] data) {
/*  989 */     if (this.serverManager == null || this.serverManager.getServer() == null || serverCharacteristic == null)
/*  990 */       return false; 
/*  991 */     int requiredProperty = confirm ? 32 : 16;
/*  992 */     if ((serverCharacteristic.getProperties() & requiredProperty) == 0)
/*  993 */       return false; 
/*  994 */     BluetoothGattDescriptor cccd = serverCharacteristic.getDescriptor(BleManager.CLIENT_CHARACTERISTIC_CONFIG_DESCRIPTOR_UUID);
/*  995 */     if (cccd == null) {
/*  996 */       return false;
/*      */     }
/*  998 */     byte[] value = (this.descriptorValues != null && this.descriptorValues.containsKey(cccd)) ? this.descriptorValues.get(cccd) : cccd.getValue();
/*  999 */     if (value != null && value.length == 2 && value[0] != 0) {
/* 1000 */       log(2, () -> "[Server] Sending " + (confirm ? "indication" : "notification") + " to " + serverCharacteristic.getUuid());
/*      */       
/* 1002 */       if (Build.VERSION.SDK_INT >= 33) {
/* 1003 */         log(3, () -> "[Server] gattServer.notifyCharacteristicChanged(" + serverCharacteristic.getUuid() + ", confirm=" + confirm + ", value=" + ParserUtils.parseDebug(data) + ")");
/*      */ 
/*      */         
/* 1006 */         return (this.serverManager.getServer().notifyCharacteristicChanged(this.bluetoothDevice, serverCharacteristic, confirm, data) == 0);
/*      */       } 
/* 1008 */       log(3, () -> "[Server] characteristic.setValue(" + ParserUtils.parseDebug(data) + ")");
/* 1009 */       serverCharacteristic.setValue(data);
/* 1010 */       log(3, () -> "[Server] gattServer.notifyCharacteristicChanged(" + serverCharacteristic.getUuid() + ", confirm=" + confirm + ")");
/* 1011 */       boolean result = this.serverManager.getServer().notifyCharacteristicChanged(this.bluetoothDevice, serverCharacteristic, confirm);
/*      */ 
/*      */       
/* 1014 */       if (result && Build.VERSION.SDK_INT < 21) {
/* 1015 */         post(() -> {
/*      */               notifyNotificationSent(this.bluetoothDevice);
/*      */               
/*      */               nextRequest(true);
/*      */             });
/*      */       }
/* 1021 */       return result;
/*      */     } 
/*      */     
/* 1024 */     nextRequest(true);
/* 1025 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static BluetoothGattDescriptor getCccd(@Nullable BluetoothGattCharacteristic characteristic, int requiredProperty) {
/* 1040 */     if (characteristic == null) {
/* 1041 */       return null;
/*      */     }
/*      */     
/* 1044 */     int properties = characteristic.getProperties();
/* 1045 */     if ((properties & requiredProperty) == 0) {
/* 1046 */       return null;
/*      */     }
/* 1048 */     return characteristic.getDescriptor(BleManager.CLIENT_CHARACTERISTIC_CONFIG_DESCRIPTOR_UUID);
/*      */   }
/*      */   
/*      */   private boolean internalReadCharacteristic(@Nullable BluetoothGattCharacteristic characteristic) {
/* 1052 */     BluetoothGatt gatt = this.bluetoothGatt;
/* 1053 */     if (gatt == null || characteristic == null || !this.connected) {
/* 1054 */       return false;
/*      */     }
/*      */     
/* 1057 */     int properties = characteristic.getProperties();
/* 1058 */     if ((properties & 0x2) == 0) {
/* 1059 */       return false;
/*      */     }
/* 1061 */     log(2, () -> "Reading characteristic " + characteristic.getUuid());
/* 1062 */     log(3, () -> "gatt.readCharacteristic(" + characteristic.getUuid() + ")");
/* 1063 */     return gatt.readCharacteristic(characteristic);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean internalWriteCharacteristic(@Nullable BluetoothGattCharacteristic characteristic, @Nullable byte[] data, int writeType) {
/* 1071 */     BluetoothGatt gatt = this.bluetoothGatt;
/* 1072 */     if (gatt == null || characteristic == null || !this.connected) {
/* 1073 */       return false;
/*      */     }
/*      */     
/* 1076 */     int properties = characteristic.getProperties();
/* 1077 */     if ((properties & 0xC) == 0)
/*      */     {
/* 1079 */       return false;
/*      */     }
/* 1081 */     if (Build.VERSION.SDK_INT >= 33) {
/* 1082 */       log(2, () -> "Writing characteristic " + characteristic.getUuid() + " (" + ParserUtils.writeTypeToString(writeType) + ")");
/*      */ 
/*      */       
/* 1085 */       log(3, () -> "gatt.writeCharacteristic(" + characteristic.getUuid() + ", value=" + ParserUtils.parseDebug(data) + ", " + ParserUtils.writeTypeToString(writeType) + ")");
/*      */ 
/*      */       
/* 1088 */       return (gatt.writeCharacteristic(characteristic, data, writeType) == 0);
/*      */     } 
/* 1090 */     log(2, () -> "Writing characteristic " + characteristic.getUuid() + " (" + ParserUtils.writeTypeToString(writeType) + ")");
/*      */ 
/*      */     
/* 1093 */     log(3, () -> "characteristic.setValue(" + ParserUtils.parseDebug(data) + ")");
/* 1094 */     characteristic.setValue(data);
/* 1095 */     log(3, () -> "characteristic.setWriteType(" + ParserUtils.writeTypeToString(writeType) + ")");
/* 1096 */     characteristic.setWriteType(writeType);
/* 1097 */     log(3, () -> "gatt.writeCharacteristic(" + characteristic.getUuid() + ")");
/* 1098 */     return gatt.writeCharacteristic(characteristic);
/*      */   }
/*      */ 
/*      */   
/*      */   private boolean internalReadDescriptor(@Nullable BluetoothGattDescriptor descriptor) {
/* 1103 */     BluetoothGatt gatt = this.bluetoothGatt;
/* 1104 */     if (gatt == null || descriptor == null || !this.connected) {
/* 1105 */       return false;
/*      */     }
/* 1107 */     log(2, () -> "Reading descriptor " + descriptor.getUuid());
/* 1108 */     log(3, () -> "gatt.readDescriptor(" + descriptor.getUuid() + ")");
/* 1109 */     return gatt.readDescriptor(descriptor);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean internalWriteDescriptor(@Nullable BluetoothGattDescriptor descriptor, @Nullable byte[] data) {
/* 1116 */     BluetoothGatt gatt = this.bluetoothGatt;
/* 1117 */     if (gatt == null || descriptor == null || !this.connected) {
/* 1118 */       return false;
/*      */     }
/* 1120 */     log(2, () -> "Writing descriptor " + descriptor.getUuid());
/* 1121 */     if (Build.VERSION.SDK_INT >= 33) {
/* 1122 */       log(3, () -> "gatt.writeDescriptor(" + descriptor.getUuid() + ", value=" + ParserUtils.parseDebug(data) + ")");
/*      */       
/* 1124 */       return (gatt.writeDescriptor(descriptor, data) == 0);
/*      */     } 
/* 1126 */     log(3, () -> "descriptor.setValue(" + descriptor.getUuid() + ")");
/* 1127 */     descriptor.setValue(data);
/* 1128 */     log(3, () -> "gatt.writeDescriptor(" + descriptor.getUuid() + ")");
/* 1129 */     if (Build.VERSION.SDK_INT >= 24) {
/* 1130 */       return internalWriteDescriptorWorkaround(descriptor);
/*      */     }
/* 1132 */     return gatt.writeDescriptor(descriptor);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean internalWriteDescriptorWorkaround(@Nullable BluetoothGattDescriptor descriptor) {
/* 1148 */     BluetoothGatt gatt = this.bluetoothGatt;
/* 1149 */     if (gatt == null || descriptor == null || !this.connected) {
/* 1150 */       return false;
/*      */     }
/* 1152 */     BluetoothGattCharacteristic parentCharacteristic = descriptor.getCharacteristic();
/* 1153 */     int originalWriteType = parentCharacteristic.getWriteType();
/* 1154 */     parentCharacteristic.setWriteType(2);
/* 1155 */     boolean result = gatt.writeDescriptor(descriptor);
/* 1156 */     parentCharacteristic.setWriteType(originalWriteType);
/* 1157 */     return result;
/*      */   }
/*      */   
/*      */   private boolean internalBeginReliableWrite() {
/* 1161 */     BluetoothGatt gatt = this.bluetoothGatt;
/* 1162 */     if (gatt == null || !this.connected) {
/* 1163 */       return false;
/*      */     }
/*      */     
/* 1166 */     if (this.reliableWriteInProgress) {
/* 1167 */       return true;
/*      */     }
/* 1169 */     log(2, () -> "Beginning reliable write...");
/* 1170 */     log(3, () -> "gatt.beginReliableWrite()");
/* 1171 */     return this.reliableWriteInProgress = gatt.beginReliableWrite();
/*      */   }
/*      */   
/*      */   private boolean internalExecuteReliableWrite() {
/* 1175 */     BluetoothGatt gatt = this.bluetoothGatt;
/* 1176 */     if (gatt == null || !this.connected) {
/* 1177 */       return false;
/*      */     }
/* 1179 */     if (!this.reliableWriteInProgress) {
/* 1180 */       return false;
/*      */     }
/* 1182 */     log(2, () -> "Executing reliable write...");
/* 1183 */     log(3, () -> "gatt.executeReliableWrite()");
/* 1184 */     return gatt.executeReliableWrite();
/*      */   }
/*      */   
/*      */   private boolean internalAbortReliableWrite() {
/* 1188 */     BluetoothGatt gatt = this.bluetoothGatt;
/* 1189 */     if (gatt == null || !this.connected) {
/* 1190 */       return false;
/*      */     }
/* 1192 */     if (!this.reliableWriteInProgress) {
/* 1193 */       return false;
/*      */     }
/* 1195 */     log(2, () -> "Aborting reliable write...");
/* 1196 */     if (Build.VERSION.SDK_INT >= 19) {
/* 1197 */       log(3, () -> "gatt.abortReliableWrite()");
/* 1198 */       gatt.abortReliableWrite();
/*      */     } else {
/* 1200 */       log(3, () -> "gatt.abortReliableWrite(device)");
/* 1201 */       gatt.abortReliableWrite(gatt.getDevice());
/*      */     } 
/* 1203 */     return true;
/*      */   }
/*      */   
/*      */   @Deprecated
/*      */   private boolean internalReadBatteryLevel() {
/* 1208 */     BluetoothGatt gatt = this.bluetoothGatt;
/* 1209 */     if (gatt == null || !this.connected) {
/* 1210 */       return false;
/*      */     }
/* 1212 */     BluetoothGattService batteryService = gatt.getService(BleManager.BATTERY_SERVICE);
/* 1213 */     if (batteryService == null) {
/* 1214 */       return false;
/*      */     }
/*      */     
/* 1217 */     BluetoothGattCharacteristic batteryLevelCharacteristic = batteryService.getCharacteristic(BleManager.BATTERY_LEVEL_CHARACTERISTIC);
/* 1218 */     return internalReadCharacteristic(batteryLevelCharacteristic);
/*      */   }
/*      */   
/*      */   @Deprecated
/*      */   private boolean internalSetBatteryNotifications(boolean enable) {
/* 1223 */     BluetoothGatt gatt = this.bluetoothGatt;
/* 1224 */     if (gatt == null || !this.connected) {
/* 1225 */       return false;
/*      */     }
/* 1227 */     BluetoothGattService batteryService = gatt.getService(BleManager.BATTERY_SERVICE);
/* 1228 */     if (batteryService == null) {
/* 1229 */       return false;
/*      */     }
/*      */     
/* 1232 */     BluetoothGattCharacteristic batteryLevelCharacteristic = batteryService.getCharacteristic(BleManager.BATTERY_LEVEL_CHARACTERISTIC);
/* 1233 */     if (enable) {
/* 1234 */       return internalEnableNotifications(batteryLevelCharacteristic);
/*      */     }
/* 1236 */     return internalDisableNotifications(batteryLevelCharacteristic);
/*      */   }
/*      */   
/*      */   @RequiresApi(api = 21)
/*      */   private boolean internalRequestMtu(@IntRange(from = 23L, to = 517L) int mtu) {
/* 1241 */     BluetoothGatt gatt = this.bluetoothGatt;
/* 1242 */     if (gatt == null || !this.connected) {
/* 1243 */       return false;
/*      */     }
/* 1245 */     log(2, () -> "Requesting new MTU...");
/* 1246 */     log(3, () -> "gatt.requestMtu(" + mtu + ")");
/* 1247 */     return gatt.requestMtu(mtu);
/*      */   }
/*      */   
/*      */   @RequiresApi(api = 21)
/*      */   private boolean internalRequestConnectionPriority(int priority) {
/* 1252 */     BluetoothGatt gatt = this.bluetoothGatt;
/* 1253 */     if (gatt == null || !this.connected) {
/* 1254 */       return false;
/*      */     }
/*      */     
/* 1257 */     int supervisionTimeout = (Build.VERSION.SDK_INT >= 26) ? 5 : 20;
/* 1258 */     log(2, () -> {
/*      */           switch (priority) {
/*      */             case 1:
/*      */               text = (Build.VERSION.SDK_INT >= 23) ? ("HIGH (11.25–15ms, 0, " + supervisionTimeout + "s)") : ("HIGH (7.5–10ms, 0, " + supervisionTimeout + "s)");
/*      */               return "Requesting connection priority: " + text + "...";
/*      */ 
/*      */ 
/*      */             
/*      */             case 2:
/*      */               text = "LOW POWER (100–125ms, 2, " + supervisionTimeout + "s)";
/*      */               return "Requesting connection priority: " + text + "...";
/*      */           } 
/*      */ 
/*      */ 
/*      */           
/*      */           String text = "BALANCED (30–50ms, 0, " + supervisionTimeout + "s)";
/*      */           return "Requesting connection priority: " + text + "...";
/*      */         });
/* 1276 */     log(3, () -> {
/*      */           switch (priority) {
/*      */             case 1:
/*      */               text = "HIGH";
/*      */               return "gatt.requestConnectionPriority(" + text + ")";
/*      */ 
/*      */             
/*      */             case 2:
/*      */               text = "LOW POWER";
/*      */               return "gatt.requestConnectionPriority(" + text + ")";
/*      */           } 
/*      */ 
/*      */           
/*      */           String text = "BALANCED";
/*      */           return "gatt.requestConnectionPriority(" + text + ")";
/*      */         });
/* 1292 */     return gatt.requestConnectionPriority(priority);
/*      */   }
/*      */ 
/*      */   
/*      */   @RequiresApi(api = 26)
/*      */   private boolean internalSetPreferredPhy(int txPhy, int rxPhy, int phyOptions) {
/* 1298 */     BluetoothGatt gatt = this.bluetoothGatt;
/* 1299 */     if (gatt == null || !this.connected) {
/* 1300 */       return false;
/*      */     }
/* 1302 */     log(2, () -> "Requesting preferred PHYs...");
/* 1303 */     log(3, () -> "gatt.setPreferredPhy(" + ParserUtils.phyMaskToString(txPhy) + ", " + ParserUtils.phyMaskToString(rxPhy) + ", coding option = " + ParserUtils.phyCodedOptionToString(phyOptions) + ")");
/*      */ 
/*      */ 
/*      */     
/* 1307 */     gatt.setPreferredPhy(txPhy, rxPhy, phyOptions);
/* 1308 */     return true;
/*      */   }
/*      */   
/*      */   @RequiresApi(api = 26)
/*      */   private boolean internalReadPhy() {
/* 1313 */     BluetoothGatt gatt = this.bluetoothGatt;
/* 1314 */     if (gatt == null || !this.connected) {
/* 1315 */       return false;
/*      */     }
/* 1317 */     log(2, () -> "Reading PHY...");
/* 1318 */     log(3, () -> "gatt.readPhy()");
/* 1319 */     gatt.readPhy();
/* 1320 */     return true;
/*      */   }
/*      */   
/*      */   private boolean internalReadRssi() {
/* 1324 */     BluetoothGatt gatt = this.bluetoothGatt;
/* 1325 */     if (gatt == null || !this.connected) {
/* 1326 */       return false;
/*      */     }
/* 1328 */     log(2, () -> "Reading remote RSSI...");
/* 1329 */     log(3, () -> "gatt.readRemoteRssi()");
/* 1330 */     return gatt.readRemoteRssi();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NonNull
/*      */   ValueChangedCallback getValueChangedCallback(@Nullable Object attribute) {
/* 1342 */     ValueChangedCallback callback = this.valueChangedCallbacks.get(attribute);
/* 1343 */     if (callback == null) {
/* 1344 */       callback = new ValueChangedCallback(this);
/* 1345 */       if (attribute != null) {
/* 1346 */         this.valueChangedCallbacks.put(attribute, callback);
/*      */       }
/* 1348 */     } else if (this.bluetoothDevice != null) {
/* 1349 */       callback.notifyClosed();
/*      */     } 
/*      */     
/* 1352 */     return callback;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void removeValueChangedCallback(@Nullable Object attribute) {
/* 1361 */     ValueChangedCallback callback = this.valueChangedCallbacks.remove(attribute);
/* 1362 */     if (callback != null) {
/* 1363 */       callback.notifyClosed();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void setCharacteristicValue(@Nullable BluetoothGattCharacteristic serverCharacteristic, @Nullable DataProvider dataProvider) {
/* 1374 */     if (serverCharacteristic == null)
/*      */       return; 
/* 1376 */     if (dataProvider == null) {
/* 1377 */       this.dataProviders.remove(serverCharacteristic);
/*      */     } else {
/* 1379 */       this.dataProviders.put(serverCharacteristic, dataProvider);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void setDescriptorValue(@Nullable BluetoothGattDescriptor serverDescriptor, @Nullable DataProvider dataProvider) {
/* 1390 */     if (serverDescriptor == null)
/*      */       return; 
/* 1392 */     if (dataProvider == null) {
/* 1393 */       this.dataProviders.remove(serverDescriptor);
/*      */     } else {
/* 1395 */       this.dataProviders.put(serverDescriptor, dataProvider);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @RequiresApi(api = 26)
/*      */   void setConnectionParametersListener(@Nullable ConnectionParametersUpdatedCallback callback) {
/* 1405 */     this.connectionParametersUpdatedCallback = callback;
/*      */ 
/*      */     
/* 1408 */     if (callback != null && this.bluetoothDevice != null && this.interval > 0) {
/* 1409 */       callback.onConnectionUpdated(this.bluetoothDevice, this.interval, this.latency, this.timeout);
/*      */     }
/*      */   }
/*      */   
/*      */   @Deprecated
/*      */   DataReceivedCallback getBatteryLevelCallback() {
/* 1415 */     return (device, data) -> {
/*      */         if (data.size() == 1) {
/*      */           int batteryLevel = data.getIntValue(17, 0).intValue();
/*      */           log(4, ());
/*      */           this.batteryValue = batteryLevel;
/*      */           onBatteryValueReceived(this.bluetoothGatt, batteryLevel);
/*      */           postCallback(());
/*      */         } 
/*      */       };
/*      */   }
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   void setBatteryLevelNotificationCallback() {
/* 1429 */     if (this.batteryLevelNotificationCallback == null) {
/* 1430 */       this
/* 1431 */         .batteryLevelNotificationCallback = (new ValueChangedCallback(this)).with((device, data) -> {
/*      */             if (data.size() == 1) {
/*      */               int batteryLevel = data.getIntValue(17, 0).intValue();
/*      */               this.batteryValue = batteryLevel;
/*      */               onBatteryValueReceived(this.bluetoothGatt, batteryLevel);
/*      */               postCallback(());
/*      */             } 
/*      */           });
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean internalRefreshDeviceCache() {
/* 1448 */     BluetoothGatt gatt = this.bluetoothGatt;
/* 1449 */     if (gatt == null) {
/* 1450 */       return false;
/*      */     }
/* 1452 */     log(2, () -> "Refreshing device cache...");
/* 1453 */     log(3, () -> "gatt.refresh() (hidden)");
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/* 1459 */       Method refresh = gatt.getClass().getMethod("refresh", new Class[0]);
/* 1460 */       return (refresh.invoke(gatt, new Object[0]) == Boolean.TRUE);
/* 1461 */     } catch (Exception e) {
/* 1462 */       Log.w("BleManager", "An exception occurred while refreshing device", e);
/* 1463 */       log(5, () -> "gatt.refresh() method not found");
/*      */       
/* 1465 */       return false;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void enqueueFirst(@NonNull Request request) {
/* 1480 */     RequestQueue rq = this.requestQueue;
/* 1481 */     if (rq == null) {
/* 1482 */       Deque<Request> queue = (this.initialization && this.initQueue != null) ? this.initQueue : this.taskQueue;
/* 1483 */       queue.addFirst(request);
/*      */     } else {
/* 1485 */       rq.addFirst(request);
/*      */     } 
/* 1487 */     request.enqueued = true;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1493 */     this.operationInProgress = false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   final void enqueue(@NonNull Request request) {
/* 1499 */     if (!request.enqueued) {
/* 1500 */       Deque<Request> queue = (this.initialization && this.initQueue != null) ? this.initQueue : this.taskQueue;
/* 1501 */       queue.add(request);
/* 1502 */       request.enqueued = true;
/*      */     } 
/* 1504 */     nextRequest(false);
/*      */   }
/*      */ 
/*      */   
/*      */   final void cancelQueue() {
/* 1509 */     this.taskQueue.clear();
/* 1510 */     this.initQueue = null;
/* 1511 */     this.initialization = false;
/*      */     
/* 1513 */     BluetoothDevice device = this.bluetoothDevice;
/* 1514 */     if (device == null) {
/*      */       return;
/*      */     }
/* 1517 */     if (this.operationInProgress) {
/* 1518 */       cancelCurrent();
/*      */     }
/*      */     
/* 1521 */     if (this.connectRequest != null) {
/* 1522 */       this.connectRequest.notifyFail(device, -7);
/* 1523 */       this.connectRequest = null;
/* 1524 */       internalDisconnect(5);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   final void cancelCurrent() {
/* 1530 */     BluetoothDevice device = this.bluetoothDevice;
/* 1531 */     if (device == null) {
/*      */       return;
/*      */     }
/* 1534 */     log(5, () -> "Request cancelled");
/* 1535 */     if (this.request instanceof TimeoutableRequest) {
/* 1536 */       this.request.notifyFail(device, -7);
/*      */     }
/* 1538 */     if (this.awaitingRequest != null) {
/* 1539 */       this.awaitingRequest.notifyFail(device, -7);
/* 1540 */       this.awaitingRequest = null;
/*      */     } 
/* 1542 */     if (this.requestQueue instanceof ReliableWriteRequest) {
/*      */ 
/*      */ 
/*      */       
/* 1546 */       this.requestQueue.cancelQueue();
/* 1547 */     } else if (this.requestQueue != null) {
/* 1548 */       this.requestQueue.notifyFail(device, -7);
/* 1549 */       this.requestQueue = null;
/*      */     } 
/* 1551 */     nextRequest((this.request == null || this.request.finished));
/*      */   }
/*      */ 
/*      */   
/*      */   final void onRequestTimeout(@NonNull BluetoothDevice device, @NonNull TimeoutableRequest tr) {
/* 1556 */     if (tr instanceof SleepRequest) {
/* 1557 */       tr.notifySuccess(device);
/*      */     } else {
/* 1559 */       log(5, () -> "Request timed out");
/*      */     } 
/* 1561 */     if (this.request instanceof TimeoutableRequest) {
/* 1562 */       this.request.notifyFail(device, -5);
/*      */     }
/* 1564 */     if (this.awaitingRequest != null) {
/* 1565 */       this.awaitingRequest.notifyFail(device, -5);
/* 1566 */       this.awaitingRequest = null;
/*      */     } 
/* 1568 */     tr.notifyFail(device, -5);
/* 1569 */     if (tr.type == Request.Type.CONNECT) {
/* 1570 */       this.connectRequest = null;
/* 1571 */       internalDisconnect(10);
/*      */       
/*      */       return;
/*      */     } 
/* 1575 */     if (tr.type == Request.Type.DISCONNECT) {
/* 1576 */       close();
/*      */       return;
/*      */     } 
/* 1579 */     nextRequest((this.request == null || this.request.finished));
/*      */   }
/*      */ 
/*      */   
/*      */   public void post(@NonNull Runnable r) {
/* 1584 */     this.handler.post(r);
/*      */   }
/*      */ 
/*      */   
/*      */   public void postDelayed(@NonNull Runnable r, long delayMillis) {
/* 1589 */     this.handler.postDelayed(r, delayMillis);
/*      */   }
/*      */ 
/*      */   
/*      */   public void removeCallbacks(@NonNull Runnable r) {
/* 1594 */     this.handler.removeCallbacks(r);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   private void postCallback(@NonNull CallbackRunnable r) {
/* 1605 */     BleManagerCallbacks callbacks = this.manager.callbacks;
/* 1606 */     if (callbacks != null) {
/* 1607 */       post(() -> r.run(callbacks));
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void postBondingStateChange(@NonNull BondingObserverRunnable r) {
/* 1616 */     BondingObserver observer = this.manager.bondingObserver;
/* 1617 */     if (observer != null) {
/* 1618 */       post(() -> r.run(observer));
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void postConnectionStateChange(@NonNull ConnectionObserverRunnable r) {
/* 1627 */     ConnectionObserver observer = this.manager.connectionObserver;
/* 1628 */     if (observer != null) {
/* 1629 */       post(() -> r.run(observer));
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   final int getConnectionState() {
/* 1644 */     return this.connectionState;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   final boolean isConnected() {
/* 1652 */     return this.connected;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   final int getBatteryValue() {
/* 1661 */     return this.batteryValue;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   final boolean isReady() {
/* 1669 */     return this.ready;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   final boolean isReliableWriteInProgress() {
/* 1677 */     return this.reliableWriteInProgress;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   final int getMtu() {
/* 1684 */     return this.mtu;
/*      */   }
/*      */   
/*      */   final void overrideMtu(@IntRange(from = 23L, to = 517L) int mtu) {
/* 1688 */     if (Build.VERSION.SDK_INT >= 21) {
/* 1689 */       this.mtu = mtu;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   protected boolean isOptionalServiceSupported(@NonNull BluetoothGatt gatt) {
/* 1713 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   protected Deque<Request> initGatt(@NonNull BluetoothGatt gatt) {
/* 1732 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   protected void initialize() {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   protected void onServerReady(@NonNull BluetoothGattServer server) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   protected void onDeviceReady() {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   protected void onManagerReady() {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   protected void onDeviceDisconnected() {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void notifyDeviceDisconnected(@NonNull BluetoothDevice device, int status) {
/* 1816 */     boolean wasConnected = this.connected;
/* 1817 */     this.connected = false;
/* 1818 */     this.ready = false;
/* 1819 */     this.servicesDiscovered = false;
/* 1820 */     this.serviceDiscoveryRequested = false;
/* 1821 */     this.deviceNotSupported = false;
/* 1822 */     this.mtu = 23;
/* 1823 */     this.interval = this.latency = this.timeout = 0;
/* 1824 */     this.connectionState = 0;
/* 1825 */     checkCondition();
/* 1826 */     if (!wasConnected) {
/* 1827 */       log(5, () -> "Connection attempt timed out");
/* 1828 */       close();
/* 1829 */       postCallback(c -> c.onDeviceDisconnected(device));
/* 1830 */       postConnectionStateChange(o -> o.onDeviceFailedToConnect(device, status));
/*      */     }
/* 1832 */     else if (this.userDisconnected) {
/* 1833 */       log(4, () -> "Disconnected");
/*      */ 
/*      */ 
/*      */       
/* 1837 */       Request request = this.request;
/* 1838 */       if (request == null || request.type != Request.Type.REMOVE_BOND)
/* 1839 */         close(); 
/* 1840 */       postCallback(c -> c.onDeviceDisconnected(device));
/* 1841 */       postConnectionStateChange(o -> o.onDeviceDisconnected(device, status));
/* 1842 */       if (request != null && request.type == Request.Type.DISCONNECT) {
/* 1843 */         request.notifySuccess(device);
/* 1844 */         this.request = null;
/*      */       } 
/*      */     } else {
/* 1847 */       log(5, () -> "Connection lost");
/* 1848 */       postCallback(c -> c.onLinkLossOccurred(device));
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1853 */       int reason = (status == 2) ? 2 : 3;
/* 1854 */       postConnectionStateChange(o -> o.onDeviceDisconnected(device, reason));
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1859 */     for (ValueChangedCallback callback : this.valueChangedCallbacks.values()) {
/* 1860 */       callback.notifyClosed();
/*      */     }
/* 1862 */     this.valueChangedCallbacks.clear();
/* 1863 */     this.dataProviders.clear();
/* 1864 */     this.batteryLevelNotificationCallback = null;
/* 1865 */     this.batteryValue = -1;
/* 1866 */     this.manager.onServicesInvalidated();
/* 1867 */     onDeviceDisconnected();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   protected void onCharacteristicRead(@NonNull BluetoothGatt gatt, @NonNull BluetoothGattCharacteristic characteristic) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   protected void onCharacteristicWrite(@NonNull BluetoothGatt gatt, @NonNull BluetoothGattCharacteristic characteristic) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   protected void onDescriptorRead(@NonNull BluetoothGatt gatt, @NonNull BluetoothGattDescriptor descriptor) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   protected void onDescriptorWrite(@NonNull BluetoothGatt gatt, @NonNull BluetoothGattDescriptor descriptor) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   protected void onBatteryValueReceived(@NonNull BluetoothGatt gatt, @IntRange(from = 0L, to = 100L) int value) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   protected void onCharacteristicNotified(@NonNull BluetoothGatt gatt, @NonNull BluetoothGattCharacteristic characteristic) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   protected void onCharacteristicIndicated(@NonNull BluetoothGatt gatt, @NonNull BluetoothGattCharacteristic characteristic) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   protected void onMtuChanged(@NonNull BluetoothGatt gatt, @IntRange(from = 23L, to = 517L) int mtu) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   @TargetApi(26)
/*      */   protected void onConnectionUpdated(@NonNull BluetoothGatt gatt, @IntRange(from = 6L, to = 3200L) int interval, @IntRange(from = 0L, to = 499L) int latency, @IntRange(from = 10L, to = 3200L) int timeout) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void onError(BluetoothDevice device, String message, int errorCode) {
/* 2012 */     log(6, () -> "Error (0x" + Integer.toHexString(errorCode) + "): " + GattError.parse(errorCode));
/* 2013 */     postCallback(c -> c.onError(device, message, errorCode));
/*      */   }
/*      */   
/* 2016 */   private final BluetoothGattCallback gattCallback = new BluetoothGattCallback()
/*      */     {
/*      */       
/*      */       public void onConnectionStateChange(@NonNull BluetoothGatt gatt, int status, int newState)
/*      */       {
/* 2021 */         BleManagerHandler.this.log(3, () -> "[Callback] Connection state changed with status: " + status + " and new state: " + newState + " (" + ParserUtils.stateToString(newState) + ")");
/*      */ 
/*      */ 
/*      */         
/* 2025 */         if (status == 0 && newState == 2) {
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 2030 */           if (BleManagerHandler.this.bluetoothDevice == null) {
/* 2031 */             Log.e("BleManager", "Device received notification after disconnection.");
/* 2032 */             BleManagerHandler.this.log(3, () -> "gatt.close()");
/*      */             try {
/* 2034 */               gatt.close();
/* 2035 */             } catch (Throwable throwable) {}
/*      */ 
/*      */             
/*      */             return;
/*      */           } 
/*      */ 
/*      */           
/* 2042 */           BleManagerHandler.this.log(4, () -> "Connected to " + gatt.getDevice().getAddress());
/* 2043 */           BleManagerHandler.this.connected = true;
/* 2044 */           BleManagerHandler.this.connectionTime = 0L;
/* 2045 */           BleManagerHandler.this.connectionState = 2;
/* 2046 */           BleManagerHandler.this.postCallback(c -> c.onDeviceConnected(gatt.getDevice()));
/* 2047 */           BleManagerHandler.this.postConnectionStateChange(o -> o.onDeviceConnected(gatt.getDevice()));
/*      */           
/* 2049 */           if (!BleManagerHandler.this.serviceDiscoveryRequested) {
/* 2050 */             boolean bonded = (gatt.getDevice().getBondState() == 12);
/* 2051 */             int delay = BleManagerHandler.this.manager.getServiceDiscoveryDelay(bonded);
/* 2052 */             if (delay > 0) {
/* 2053 */               BleManagerHandler.this.log(3, () -> "wait(" + delay + ")");
/*      */             }
/* 2055 */             int connectionCount = ++BleManagerHandler.this.connectionCount;
/* 2056 */             BleManagerHandler.this.postDelayed(() -> { if (connectionCount != BleManagerHandler.this.connectionCount) return;  if (BleManagerHandler.this.connected && !BleManagerHandler.this.servicesDiscovered && !BleManagerHandler.this.serviceDiscoveryRequested && gatt.getDevice().getBondState() != 11) { BleManagerHandler.this.serviceDiscoveryRequested = true; BleManagerHandler.this.log(2, ()); BleManagerHandler.this.log(3, ()); gatt.discoverServices(); }  }delay);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         }
/*      */         else {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 2075 */           if (newState == 0) {
/* 2076 */             long now = SystemClock.elapsedRealtime();
/* 2077 */             boolean canTimeout = (BleManagerHandler.this.connectionTime > 0L);
/* 2078 */             boolean timeout = (canTimeout && now > BleManagerHandler.this.connectionTime + 20000L);
/*      */             
/* 2080 */             if (status != 0) {
/* 2081 */               BleManagerHandler.this.log(5, () -> "Error: (0x" + Integer.toHexString(status) + "): " + GattError.parseConnectionError(status));
/*      */             }
/*      */ 
/*      */ 
/*      */             
/* 2086 */             if (status != 0 && canTimeout && !timeout && BleManagerHandler.this
/* 2087 */               .connectRequest != null && BleManagerHandler.this.connectRequest.canRetry()) {
/* 2088 */               int delay = BleManagerHandler.this.connectRequest.getRetryDelay();
/* 2089 */               if (delay > 0)
/* 2090 */                 BleManagerHandler.this.log(3, () -> "wait(" + delay + ")"); 
/* 2091 */               BleManagerHandler.this.postDelayed(() -> BleManagerHandler.this.internalConnect(gatt.getDevice(), BleManagerHandler.this.connectRequest), delay);
/*      */               
/*      */               return;
/*      */             } 
/* 2095 */             if (BleManagerHandler.this.connectRequest != null && BleManagerHandler.this.connectRequest.shouldAutoConnect() && BleManagerHandler.this.initialConnection && gatt
/* 2096 */               .getDevice().getBondState() == 12) {
/* 2097 */               BleManagerHandler.this.log(3, () -> "autoConnect = false called failed; retrying with autoConnect = true");
/* 2098 */               BleManagerHandler.this.post(() -> BleManagerHandler.this.internalConnect(gatt.getDevice(), BleManagerHandler.this.connectRequest));
/*      */               
/*      */               return;
/*      */             } 
/* 2102 */             BleManagerHandler.this.operationInProgress = true;
/* 2103 */             BleManagerHandler.this.taskQueue.clear();
/* 2104 */             BleManagerHandler.this.initQueue = null;
/* 2105 */             BleManagerHandler.this.ready = false;
/*      */ 
/*      */             
/* 2108 */             boolean wasConnected = BleManagerHandler.this.connected;
/* 2109 */             boolean notSupported = BleManagerHandler.this.deviceNotSupported;
/*      */             
/* 2111 */             BleManagerHandler.this.notifyDeviceDisconnected(gatt.getDevice(), 
/* 2112 */                 timeout ? 
/* 2113 */                 10 : (
/* 2114 */                 notSupported ? 
/* 2115 */                 4 : 
/* 2116 */                 BleManagerHandler.this.mapDisconnectStatusToReason(status)));
/*      */ 
/*      */             
/* 2119 */             if (BleManagerHandler.this.request != null && 
/* 2120 */               BleManagerHandler.this.request.type != Request.Type.DISCONNECT && BleManagerHandler.this.request.type != Request.Type.REMOVE_BOND) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/* 2126 */               BleManagerHandler.this.request.notifyFail(gatt.getDevice(), 
/* 2127 */                   (status == 0) ? 
/* 2128 */                   -1 : status);
/* 2129 */               BleManagerHandler.this.request = null;
/*      */             } 
/*      */             
/* 2132 */             if (BleManagerHandler.this.awaitingRequest != null) {
/* 2133 */               BleManagerHandler.this.awaitingRequest.notifyFail(gatt.getDevice(), -1);
/* 2134 */               BleManagerHandler.this.awaitingRequest = null;
/*      */             } 
/* 2136 */             if (BleManagerHandler.this.connectRequest != null) {
/*      */               int reason;
/* 2138 */               if (notSupported) {
/* 2139 */                 reason = -2;
/* 2140 */               } else if (status == 0) {
/* 2141 */                 reason = -1;
/* 2142 */               } else if (status == 133 && timeout) {
/* 2143 */                 reason = -5;
/*      */               } else {
/* 2145 */                 reason = status;
/* 2146 */               }  BleManagerHandler.this.connectRequest.notifyFail(gatt.getDevice(), reason);
/* 2147 */               BleManagerHandler.this.connectRequest = null;
/*      */             } 
/*      */ 
/*      */             
/* 2151 */             BleManagerHandler.this.operationInProgress = false;
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 2156 */             if (wasConnected && BleManagerHandler.this.initialConnection) {
/* 2157 */               BleManagerHandler.this.internalConnect(gatt.getDevice(), null);
/*      */             } else {
/* 2159 */               BleManagerHandler.this.initialConnection = false;
/* 2160 */               BleManagerHandler.this.nextRequest(false);
/*      */             } 
/*      */             
/* 2163 */             if (wasConnected || status == 0) {
/*      */               return;
/*      */             }
/* 2166 */           } else if (status != 0) {
/* 2167 */             BleManagerHandler.this.log(6, () -> "Error (0x" + Integer.toHexString(status) + "): " + GattError.parseConnectionError(status));
/*      */           } 
/*      */ 
/*      */           
/* 2171 */           BleManagerHandler.this.postCallback(c -> c.onError(gatt.getDevice(), "Error on connection state change", status));
/*      */         } 
/*      */       }
/*      */ 
/*      */       
/*      */       public void onServicesDiscovered(@NonNull BluetoothGatt gatt, int status) {
/* 2177 */         if (!BleManagerHandler.this.serviceDiscoveryRequested)
/*      */           return; 
/* 2179 */         BleManagerHandler.this.serviceDiscoveryRequested = false;
/* 2180 */         if (status == 0) {
/* 2181 */           BleManagerHandler.this.log(4, () -> "Services discovered");
/* 2182 */           BleManagerHandler.this.servicesDiscovered = true;
/* 2183 */           if (BleManagerHandler.this.manager.isRequiredServiceSupported(gatt)) {
/* 2184 */             BleManagerHandler.this.log(2, () -> "Primary service found");
/* 2185 */             BleManagerHandler.this.deviceNotSupported = false;
/* 2186 */             boolean optionalServicesFound = BleManagerHandler.this.manager.isOptionalServiceSupported(gatt);
/* 2187 */             if (optionalServicesFound) {
/* 2188 */               BleManagerHandler.this.log(2, () -> "Secondary service found");
/*      */             }
/*      */             
/* 2191 */             BleManagerHandler.this.postCallback(c -> c.onServicesDiscovered(gatt.getDevice(), optionalServicesFound));
/*      */ 
/*      */             
/* 2194 */             BleManagerHandler.this.initializeServerAttributes();
/*      */ 
/*      */ 
/*      */             
/* 2198 */             BleManagerHandler.this.operationInProgress = true;
/* 2199 */             BleManagerHandler.this.initialization = true;
/* 2200 */             BleManagerHandler.this.initQueue = BleManagerHandler.this.initGatt(gatt);
/*      */             
/* 2202 */             boolean deprecatedApiUsed = (BleManagerHandler.this.initQueue != null);
/* 2203 */             if (deprecatedApiUsed) {
/* 2204 */               for (Request request : BleManagerHandler.this.initQueue) {
/* 2205 */                 request.setRequestHandler(BleManagerHandler.this);
/* 2206 */                 request.enqueued = true;
/*      */               } 
/*      */             }
/*      */             
/* 2210 */             if (BleManagerHandler.this.initQueue == null) {
/* 2211 */               BleManagerHandler.this.initQueue = new LinkedBlockingDeque();
/*      */             }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 2222 */             if (Build.VERSION.SDK_INT < 23 || Build.VERSION.SDK_INT == 26 || Build.VERSION.SDK_INT == 27 || Build.VERSION.SDK_INT == 28) {
/*      */ 
/*      */ 
/*      */               
/* 2226 */               BleManagerHandler.this.enqueueFirst(Request.newEnableServiceChangedIndicationsRequest()
/* 2227 */                   .setRequestHandler(BleManagerHandler.this));
/*      */               
/* 2229 */               BleManagerHandler.this.operationInProgress = true;
/*      */             } 
/*      */ 
/*      */             
/* 2233 */             if (deprecatedApiUsed) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/* 2240 */               BleManagerHandler.this.manager.readBatteryLevel();
/*      */ 
/*      */               
/* 2243 */               if (BleManagerHandler.this.manager.callbacks != null && 
/* 2244 */                 BleManagerHandler.this.manager.callbacks.shouldEnableBatteryLevelNotifications(gatt.getDevice())) {
/* 2245 */                 BleManagerHandler.this.manager.enableBatteryLevelNotifications();
/*      */               }
/*      */             } 
/*      */             
/* 2249 */             BleManagerHandler.this.manager.initialize();
/* 2250 */             BleManagerHandler.this.initialization = false;
/* 2251 */             BleManagerHandler.this.nextRequest(true);
/*      */           } else {
/* 2253 */             BleManagerHandler.this.log(5, () -> "Device is not supported");
/* 2254 */             BleManagerHandler.this.deviceNotSupported = true;
/* 2255 */             BleManagerHandler.this.postCallback(c -> c.onDeviceNotSupported(gatt.getDevice()));
/* 2256 */             BleManagerHandler.this.internalDisconnect(4);
/*      */           } 
/*      */         } else {
/* 2259 */           Log.e("BleManager", "onServicesDiscovered error " + status);
/* 2260 */           BleManagerHandler.this.onError(gatt.getDevice(), "Error on discovering services", status);
/* 2261 */           if (BleManagerHandler.this.connectRequest != null) {
/* 2262 */             BleManagerHandler.this.connectRequest.notifyFail(gatt.getDevice(), -4);
/* 2263 */             BleManagerHandler.this.connectRequest = null;
/*      */           } 
/* 2265 */           BleManagerHandler.this.internalDisconnect(-1);
/*      */         } 
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       @Keep
/*      */       public void onServiceChanged(@NonNull BluetoothGatt gatt) {
/* 2279 */         BleManagerHandler.this.log(4, () -> "Service changed, invalidating services");
/*      */ 
/*      */         
/* 2282 */         BleManagerHandler.this.operationInProgress = true;
/*      */         
/* 2284 */         BleManagerHandler.this.manager.onServicesInvalidated();
/* 2285 */         BleManagerHandler.this.onDeviceDisconnected();
/*      */         
/* 2287 */         BleManagerHandler.this.taskQueue.clear();
/* 2288 */         BleManagerHandler.this.initQueue = null;
/*      */         
/* 2290 */         BleManagerHandler.this.serviceDiscoveryRequested = true;
/* 2291 */         BleManagerHandler.this.servicesDiscovered = false;
/* 2292 */         BleManagerHandler.this.log(2, () -> "Discovering Services...");
/* 2293 */         BleManagerHandler.this.log(3, () -> "gatt.discoverServices()");
/* 2294 */         gatt.discoverServices();
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
/* 2301 */         onCharacteristicRead(gatt, characteristic, characteristic.getValue(), status);
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public void onCharacteristicRead(@NonNull BluetoothGatt gatt, @NonNull BluetoothGattCharacteristic characteristic, @NonNull byte[] data, int status) {
/* 2308 */         if (status == 0)
/* 2309 */         { BleManagerHandler.this.log(4, () -> "Read Response received from " + characteristic.getUuid() + ", value: " + ParserUtils.parse(data));
/*      */ 
/*      */ 
/*      */           
/* 2313 */           BleManagerHandler.this.onCharacteristicRead(gatt, characteristic);
/* 2314 */           if (BleManagerHandler.this.request instanceof ReadRequest) {
/* 2315 */             ReadRequest rr = (ReadRequest)BleManagerHandler.this.request;
/* 2316 */             boolean matches = rr.matches(data);
/* 2317 */             if (matches) {
/* 2318 */               rr.notifyValueChanged(gatt.getDevice(), data);
/*      */             }
/* 2320 */             if (!matches || rr.hasMore()) {
/* 2321 */               BleManagerHandler.this.enqueueFirst(rr);
/*      */             } else {
/* 2323 */               rr.notifySuccess(gatt.getDevice());
/*      */             } 
/*      */           }  }
/* 2326 */         else { if (status == 5 || status == 8 || status == 137) {
/*      */ 
/*      */             
/* 2329 */             BleManagerHandler.this.log(5, () -> "Authentication required (" + status + ")");
/* 2330 */             if (gatt.getDevice().getBondState() != 10) {
/*      */               
/* 2332 */               Log.w("BleManager", "Phone has lost bonding information");
/* 2333 */               BleManagerHandler.this.postCallback(c -> c.onError(gatt.getDevice(), "Phone has lost bonding information", status));
/*      */             } 
/*      */             
/*      */             return;
/*      */           } 
/* 2338 */           Log.e("BleManager", "onCharacteristicRead error " + status);
/* 2339 */           if (BleManagerHandler.this.request instanceof ReadRequest) {
/* 2340 */             BleManagerHandler.this.request.notifyFail(gatt.getDevice(), status);
/*      */           }
/* 2342 */           BleManagerHandler.this.awaitingRequest = null;
/* 2343 */           BleManagerHandler.this.onError(gatt.getDevice(), "Error on reading characteristic", status); }
/*      */         
/* 2345 */         BleManagerHandler.this.checkCondition();
/* 2346 */         BleManagerHandler.this.nextRequest(true);
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
/* 2353 */         if (status == 0)
/*      */         
/*      */         { 
/* 2356 */           BleManagerHandler.this.log(4, () -> "Data written to " + characteristic.getUuid());
/*      */           
/* 2358 */           BleManagerHandler.this.onCharacteristicWrite(gatt, characteristic);
/* 2359 */           if (BleManagerHandler.this.request instanceof WriteRequest) {
/* 2360 */             WriteRequest wr = (WriteRequest)BleManagerHandler.this.request;
/*      */ 
/*      */ 
/*      */             
/* 2364 */             boolean valid = wr.notifyPacketSent(gatt.getDevice(), characteristic.getValue());
/* 2365 */             if (!valid && BleManagerHandler.this.requestQueue instanceof ReliableWriteRequest) {
/* 2366 */               wr.notifyFail(gatt.getDevice(), -6);
/* 2367 */               BleManagerHandler.this.requestQueue.cancelQueue();
/* 2368 */             } else if (wr.hasMore()) {
/* 2369 */               BleManagerHandler.this.enqueueFirst(wr);
/*      */             } else {
/* 2371 */               wr.notifySuccess(gatt.getDevice());
/*      */             } 
/*      */           }  }
/* 2374 */         else { if (status == 5 || status == 8 || status == 137) {
/*      */ 
/*      */             
/* 2377 */             BleManagerHandler.this.log(5, () -> "Authentication required (" + status + ")");
/* 2378 */             if (gatt.getDevice().getBondState() != 10) {
/*      */               
/* 2380 */               Log.w("BleManager", "Phone has lost bonding information");
/* 2381 */               BleManagerHandler.this.postCallback(c -> c.onError(gatt.getDevice(), "Phone has lost bonding information", status));
/*      */             } 
/*      */             
/*      */             return;
/*      */           } 
/* 2386 */           Log.e("BleManager", "onCharacteristicWrite error " + status);
/* 2387 */           if (BleManagerHandler.this.request instanceof WriteRequest) {
/* 2388 */             BleManagerHandler.this.request.notifyFail(gatt.getDevice(), status);
/*      */             
/* 2390 */             if (BleManagerHandler.this.requestQueue instanceof ReliableWriteRequest)
/* 2391 */               BleManagerHandler.this.requestQueue.cancelQueue(); 
/*      */           } 
/* 2393 */           BleManagerHandler.this.awaitingRequest = null;
/* 2394 */           BleManagerHandler.this.onError(gatt.getDevice(), "Error on writing characteristic", status); }
/*      */         
/* 2396 */         BleManagerHandler.this.checkCondition();
/* 2397 */         BleManagerHandler.this.nextRequest(true);
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*      */       public void onReliableWriteCompleted(@NonNull BluetoothGatt gatt, int status) {
/* 2403 */         boolean execute = (BleManagerHandler.this.request.type == Request.Type.EXECUTE_RELIABLE_WRITE);
/* 2404 */         BleManagerHandler.this.reliableWriteInProgress = false;
/* 2405 */         if (status == 0) {
/* 2406 */           if (execute) {
/* 2407 */             BleManagerHandler.this.log(4, () -> "Reliable Write executed");
/* 2408 */             BleManagerHandler.this.request.notifySuccess(gatt.getDevice());
/*      */           } else {
/* 2410 */             BleManagerHandler.this.log(5, () -> "Reliable Write aborted");
/* 2411 */             BleManagerHandler.this.request.notifySuccess(gatt.getDevice());
/* 2412 */             BleManagerHandler.this.requestQueue.notifyFail(gatt.getDevice(), -4);
/*      */           } 
/*      */         } else {
/* 2415 */           Log.e("BleManager", "onReliableWriteCompleted execute " + execute + ", error " + status);
/* 2416 */           BleManagerHandler.this.request.notifyFail(gatt.getDevice(), status);
/* 2417 */           BleManagerHandler.this.onError(gatt.getDevice(), "Error on Execute Reliable Write", status);
/*      */         } 
/* 2419 */         BleManagerHandler.this.checkCondition();
/* 2420 */         BleManagerHandler.this.nextRequest(true);
/*      */       }
/*      */ 
/*      */       
/*      */       public void onDescriptorRead(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
/* 2425 */         byte[] data = descriptor.getValue();
/*      */         
/* 2427 */         if (status == 0)
/* 2428 */         { BleManagerHandler.this.log(4, () -> "Read Response received from descr. " + descriptor.getUuid() + ", value: " + ParserUtils.parse(data));
/*      */ 
/*      */           
/* 2431 */           BleManagerHandler.this.onDescriptorRead(gatt, descriptor);
/* 2432 */           if (BleManagerHandler.this.request instanceof ReadRequest) {
/* 2433 */             ReadRequest request = (ReadRequest)BleManagerHandler.this.request;
/* 2434 */             request.notifyValueChanged(gatt.getDevice(), data);
/* 2435 */             if (request.hasMore()) {
/* 2436 */               BleManagerHandler.this.enqueueFirst(request);
/*      */             } else {
/* 2438 */               request.notifySuccess(gatt.getDevice());
/*      */             } 
/*      */           }  }
/* 2441 */         else { if (status == 5 || status == 8 || status == 137) {
/*      */ 
/*      */             
/* 2444 */             BleManagerHandler.this.log(5, () -> "Authentication required (" + status + ")");
/* 2445 */             if (gatt.getDevice().getBondState() != 10) {
/*      */               
/* 2447 */               Log.w("BleManager", "Phone has lost bonding information");
/* 2448 */               BleManagerHandler.this.postCallback(c -> c.onError(gatt.getDevice(), "Phone has lost bonding information", status));
/*      */             } 
/*      */             
/*      */             return;
/*      */           } 
/* 2453 */           Log.e("BleManager", "onDescriptorRead error " + status);
/* 2454 */           if (BleManagerHandler.this.request instanceof ReadRequest) {
/* 2455 */             BleManagerHandler.this.request.notifyFail(gatt.getDevice(), status);
/*      */           }
/* 2457 */           BleManagerHandler.this.awaitingRequest = null;
/* 2458 */           BleManagerHandler.this.onError(gatt.getDevice(), "Error on reading descriptor", status); }
/*      */         
/* 2460 */         BleManagerHandler.this.checkCondition();
/* 2461 */         BleManagerHandler.this.nextRequest(true);
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public void onDescriptorWrite(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
/* 2468 */         byte[] data = descriptor.getValue();
/*      */         
/* 2470 */         if (status == 0)
/* 2471 */         { BleManagerHandler.this.log(4, () -> "Data written to descr. " + descriptor.getUuid());
/*      */           
/* 2473 */           if (BleManagerHandler.this.isServiceChangedCCCD(descriptor)) {
/* 2474 */             BleManagerHandler.this.log(4, () -> "Service Changed notifications enabled");
/* 2475 */           } else if (BleManagerHandler.this.isCCCD(descriptor)) {
/* 2476 */             if (data != null && data.length == 2 && data[1] == 0) {
/* 2477 */               switch (data[0]) {
/*      */                 case 0:
/* 2479 */                   BleManagerHandler.this.log(4, () -> "Notifications and indications disabled");
/*      */                   break;
/*      */                 case 1:
/* 2482 */                   BleManagerHandler.this.log(4, () -> "Notifications enabled");
/*      */                   break;
/*      */                 case 2:
/* 2485 */                   BleManagerHandler.this.log(4, () -> "Indications enabled");
/*      */                   break;
/*      */               } 
/* 2488 */               BleManagerHandler.this.onDescriptorWrite(gatt, descriptor);
/*      */             } 
/*      */           } else {
/* 2491 */             BleManagerHandler.this.onDescriptorWrite(gatt, descriptor);
/*      */           } 
/* 2493 */           if (BleManagerHandler.this.request instanceof WriteRequest) {
/* 2494 */             WriteRequest wr = (WriteRequest)BleManagerHandler.this.request;
/* 2495 */             boolean valid = wr.notifyPacketSent(gatt.getDevice(), data);
/* 2496 */             if (!valid && BleManagerHandler.this.requestQueue instanceof ReliableWriteRequest) {
/* 2497 */               wr.notifyFail(gatt.getDevice(), -6);
/* 2498 */               BleManagerHandler.this.requestQueue.cancelQueue();
/* 2499 */             } else if (wr.hasMore()) {
/* 2500 */               BleManagerHandler.this.enqueueFirst(wr);
/*      */             } else {
/* 2502 */               wr.notifySuccess(gatt.getDevice());
/*      */             } 
/*      */           }  }
/* 2505 */         else { if (status == 5 || status == 8 || status == 137) {
/*      */ 
/*      */             
/* 2508 */             BleManagerHandler.this.log(5, () -> "Authentication required (" + status + ")");
/* 2509 */             if (gatt.getDevice().getBondState() != 10) {
/*      */               
/* 2511 */               Log.w("BleManager", "Phone has lost bonding information");
/* 2512 */               BleManagerHandler.this.postCallback(c -> c.onError(gatt.getDevice(), "Phone has lost bonding information", status));
/*      */             } 
/*      */             
/*      */             return;
/*      */           } 
/* 2517 */           Log.e("BleManager", "onDescriptorWrite error " + status);
/* 2518 */           if (BleManagerHandler.this.request instanceof WriteRequest) {
/* 2519 */             BleManagerHandler.this.request.notifyFail(gatt.getDevice(), status);
/*      */             
/* 2521 */             if (BleManagerHandler.this.requestQueue instanceof ReliableWriteRequest)
/* 2522 */               BleManagerHandler.this.requestQueue.cancelQueue(); 
/*      */           } 
/* 2524 */           BleManagerHandler.this.awaitingRequest = null;
/* 2525 */           BleManagerHandler.this.onError(gatt.getDevice(), "Error on writing descriptor", status); }
/*      */         
/* 2527 */         BleManagerHandler.this.checkCondition();
/* 2528 */         BleManagerHandler.this.nextRequest(true);
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
/* 2535 */         onCharacteristicChanged(gatt, characteristic, characteristic.getValue());
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public void onCharacteristicChanged(@NonNull BluetoothGatt gatt, @NonNull BluetoothGattCharacteristic characteristic, @NonNull byte[] data) {
/* 2543 */         if (BleManagerHandler.this.isServiceChangedCharacteristic(characteristic)) {
/*      */ 
/*      */           
/* 2546 */           if (Build.VERSION.SDK_INT <= 30) {
/* 2547 */             BleManagerHandler.this.log(4, () -> "Service Changed indication received");
/*      */ 
/*      */ 
/*      */             
/* 2551 */             BleManagerHandler.this.operationInProgress = true;
/*      */             
/* 2553 */             BleManagerHandler.this.manager.onServicesInvalidated();
/* 2554 */             BleManagerHandler.this.onDeviceDisconnected();
/*      */             
/* 2556 */             BleManagerHandler.this.taskQueue.clear();
/* 2557 */             BleManagerHandler.this.initQueue = null;
/* 2558 */             BleManagerHandler.this.serviceDiscoveryRequested = true;
/* 2559 */             BleManagerHandler.this.log(2, () -> "Discovering Services...");
/* 2560 */             BleManagerHandler.this.log(3, () -> "gatt.discoverServices()");
/* 2561 */             gatt.discoverServices();
/*      */           } 
/*      */           
/*      */           return;
/*      */         } 
/*      */         
/* 2567 */         BluetoothGattDescriptor cccd = characteristic.getDescriptor(BleManager.CLIENT_CHARACTERISTIC_CONFIG_DESCRIPTOR_UUID);
/*      */         
/* 2569 */         boolean notifications = (cccd == null || cccd.getValue() == null || (cccd.getValue()).length != 2 || cccd.getValue()[0] == 1);
/*      */         
/* 2571 */         if (notifications) {
/* 2572 */           BleManagerHandler.this.log(4, () -> "Notification received from " + characteristic.getUuid() + ", value: " + ParserUtils.parse(data));
/*      */           
/* 2574 */           BleManagerHandler.this.onCharacteristicNotified(gatt, characteristic);
/*      */         } else {
/* 2576 */           BleManagerHandler.this.log(4, () -> "Indication received from " + characteristic.getUuid() + ", value: " + ParserUtils.parse(data));
/*      */           
/* 2578 */           BleManagerHandler.this.onCharacteristicIndicated(gatt, characteristic);
/*      */         } 
/* 2580 */         if (BleManagerHandler.this.batteryLevelNotificationCallback != null && BleManagerHandler.this.isBatteryLevelCharacteristic(characteristic)) {
/* 2581 */           BleManagerHandler.this.batteryLevelNotificationCallback.notifyValueChanged(gatt.getDevice(), data);
/*      */         }
/*      */         
/* 2584 */         ValueChangedCallback request = (ValueChangedCallback)BleManagerHandler.this.valueChangedCallbacks.get(characteristic);
/* 2585 */         if (request != null && request.matches(data)) {
/* 2586 */           request.notifyValueChanged(gatt.getDevice(), data);
/*      */         }
/*      */         
/* 2589 */         if (BleManagerHandler.this.awaitingRequest instanceof WaitForValueChangedRequest && 
/*      */           
/* 2591 */           BleManagerHandler.this.awaitingRequest.characteristic == characteristic && 
/*      */ 
/*      */           
/* 2594 */           !BleManagerHandler.this.awaitingRequest.isTriggerPending()) {
/* 2595 */           WaitForValueChangedRequest valueChangedRequest = (WaitForValueChangedRequest)BleManagerHandler.this.awaitingRequest;
/* 2596 */           if (valueChangedRequest.matches(data)) {
/*      */             
/* 2598 */             valueChangedRequest.notifyValueChanged(gatt.getDevice(), data);
/*      */ 
/*      */             
/* 2601 */             if (valueChangedRequest.isComplete()) {
/* 2602 */               BleManagerHandler.this.log(4, () -> "Wait for value changed complete");
/*      */               
/* 2604 */               valueChangedRequest.notifySuccess(gatt.getDevice());
/*      */ 
/*      */ 
/*      */               
/* 2608 */               BleManagerHandler.this.awaitingRequest = null;
/* 2609 */               if (valueChangedRequest.isTriggerCompleteOrNull()) {
/* 2610 */                 BleManagerHandler.this.nextRequest(true);
/*      */               }
/*      */             } 
/*      */           } 
/*      */         } 
/* 2615 */         if (BleManagerHandler.this.checkCondition()) {
/* 2616 */           BleManagerHandler.this.nextRequest(true);
/*      */         }
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       @RequiresApi(api = 21)
/*      */       public void onMtuChanged(@NonNull BluetoothGatt gatt, @IntRange(from = 23L, to = 517L) int mtu, int status) {
/* 2625 */         if (status == 0) {
/* 2626 */           BleManagerHandler.this.log(4, () -> "MTU changed to: " + mtu);
/* 2627 */           BleManagerHandler.this.mtu = mtu;
/* 2628 */           BleManagerHandler.this.onMtuChanged(gatt, mtu);
/* 2629 */           if (BleManagerHandler.this.request instanceof MtuRequest) {
/* 2630 */             ((MtuRequest)BleManagerHandler.this.request).notifyMtuChanged(gatt.getDevice(), mtu);
/* 2631 */             BleManagerHandler.this.request.notifySuccess(gatt.getDevice());
/*      */           } 
/*      */         } else {
/* 2634 */           Log.e("BleManager", "onMtuChanged error: " + status + ", mtu: " + mtu);
/* 2635 */           if (BleManagerHandler.this.request instanceof MtuRequest) {
/* 2636 */             BleManagerHandler.this.request.notifyFail(gatt.getDevice(), status);
/* 2637 */             BleManagerHandler.this.awaitingRequest = null;
/*      */           } 
/* 2639 */           BleManagerHandler.this.onError(gatt.getDevice(), "Error on mtu request", status);
/*      */         } 
/* 2641 */         BleManagerHandler.this.checkCondition();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2647 */         if (BleManagerHandler.this.servicesDiscovered) {
/* 2648 */           BleManagerHandler.this.nextRequest(true);
/*      */         }
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       @RequiresApi(api = 26)
/*      */       @Keep
/*      */       public void onConnectionUpdated(@NonNull BluetoothGatt gatt, @IntRange(from = 6L, to = 3200L) int interval, @IntRange(from = 0L, to = 499L) int latency, @IntRange(from = 10L, to = 3200L) int timeout, int status) {
/* 2673 */         if (status == 0) {
/* 2674 */           BleManagerHandler.this.log(4, () -> "Connection parameters updated (interval: " + (interval * 1.25D) + "ms, latency: " + latency + ", timeout: " + (timeout * 10) + "ms)");
/*      */ 
/*      */ 
/*      */           
/* 2678 */           BleManagerHandler.this.interval = interval;
/* 2679 */           BleManagerHandler.this.latency = latency;
/* 2680 */           BleManagerHandler.this.timeout = timeout;
/*      */           
/* 2682 */           BleManagerHandler.this.onConnectionUpdated(gatt, interval, latency, timeout);
/* 2683 */           ConnectionParametersUpdatedCallback cpuc = BleManagerHandler.this.connectionParametersUpdatedCallback;
/* 2684 */           if (cpuc != null) {
/* 2685 */             cpuc.onConnectionUpdated(gatt.getDevice(), interval, latency, timeout);
/*      */           }
/*      */           
/* 2688 */           if (BleManagerHandler.this.request instanceof ConnectionPriorityRequest) {
/* 2689 */             ((ConnectionPriorityRequest)BleManagerHandler.this.request)
/* 2690 */               .notifyConnectionPriorityChanged(gatt.getDevice(), interval, latency, timeout);
/* 2691 */             BleManagerHandler.this.request.notifySuccess(gatt.getDevice());
/*      */           } 
/* 2693 */         } else if (status == 59) {
/* 2694 */           Log.e("BleManager", "onConnectionUpdated received status: Unacceptable connection interval, interval: " + interval + ", latency: " + latency + ", timeout: " + timeout);
/*      */           
/* 2696 */           BleManagerHandler.this.log(5, () -> "Connection parameters update failed with status: UNACCEPT CONN INTERVAL (0x3b) (interval: " + (interval * 1.25D) + "ms, latency: " + latency + ", timeout: " + (timeout * 10) + "ms)");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 2702 */           if (BleManagerHandler.this.request instanceof ConnectionPriorityRequest) {
/* 2703 */             BleManagerHandler.this.request.notifyFail(gatt.getDevice(), status);
/* 2704 */             BleManagerHandler.this.awaitingRequest = null;
/*      */           } 
/*      */         } else {
/* 2707 */           Log.e("BleManager", "onConnectionUpdated received status: " + status + ", interval: " + interval + ", latency: " + latency + ", timeout: " + timeout);
/*      */           
/* 2709 */           BleManagerHandler.this.log(5, () -> "Connection parameters update failed with status " + status + " (interval: " + (interval * 1.25D) + "ms, latency: " + latency + ", timeout: " + (timeout * 10) + "ms)");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 2715 */           if (BleManagerHandler.this.request instanceof ConnectionPriorityRequest) {
/* 2716 */             BleManagerHandler.this.request.notifyFail(gatt.getDevice(), status);
/* 2717 */             BleManagerHandler.this.awaitingRequest = null;
/*      */           } 
/* 2719 */           BleManagerHandler.this.postCallback(c -> c.onError(gatt.getDevice(), "Error on connection priority request", status));
/*      */         } 
/* 2721 */         if (BleManagerHandler.this.connectionPriorityOperationInProgress) {
/* 2722 */           BleManagerHandler.this.connectionPriorityOperationInProgress = false;
/* 2723 */           BleManagerHandler.this.checkCondition();
/* 2724 */           BleManagerHandler.this.nextRequest(true);
/*      */         } 
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       @RequiresApi(api = 26)
/*      */       public void onPhyUpdate(@NonNull BluetoothGatt gatt, int txPhy, int rxPhy, int status) {
/* 2733 */         if (status == 0) {
/* 2734 */           BleManagerHandler.this.log(4, () -> "PHY updated (TX: " + ParserUtils.phyToString(txPhy) + ", RX: " + ParserUtils.phyToString(rxPhy) + ")");
/*      */ 
/*      */           
/* 2737 */           if (BleManagerHandler.this.request instanceof PhyRequest) {
/* 2738 */             ((PhyRequest)BleManagerHandler.this.request).notifyPhyChanged(gatt.getDevice(), txPhy, rxPhy);
/* 2739 */             BleManagerHandler.this.request.notifySuccess(gatt.getDevice());
/*      */           } 
/*      */         } else {
/* 2742 */           BleManagerHandler.this.log(5, () -> "PHY updated failed with status " + status);
/* 2743 */           if (BleManagerHandler.this.request instanceof PhyRequest) {
/* 2744 */             BleManagerHandler.this.request.notifyFail(gatt.getDevice(), status);
/* 2745 */             BleManagerHandler.this.awaitingRequest = null;
/*      */           } 
/* 2747 */           BleManagerHandler.this.postCallback(c -> c.onError(gatt.getDevice(), "Error on PHY update", status));
/*      */         } 
/*      */ 
/*      */         
/* 2751 */         if (BleManagerHandler.this.checkCondition() || BleManagerHandler.this.request instanceof PhyRequest) {
/* 2752 */           BleManagerHandler.this.nextRequest(true);
/*      */         }
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       @RequiresApi(api = 26)
/*      */       public void onPhyRead(@NonNull BluetoothGatt gatt, int txPhy, int rxPhy, int status) {
/* 2761 */         if (status == 0) {
/* 2762 */           BleManagerHandler.this.log(4, () -> "PHY read (TX: " + ParserUtils.phyToString(txPhy) + ", RX: " + ParserUtils.phyToString(rxPhy) + ")");
/*      */ 
/*      */           
/* 2765 */           if (BleManagerHandler.this.request instanceof PhyRequest) {
/* 2766 */             ((PhyRequest)BleManagerHandler.this.request).notifyPhyChanged(gatt.getDevice(), txPhy, rxPhy);
/* 2767 */             BleManagerHandler.this.request.notifySuccess(gatt.getDevice());
/*      */           } 
/*      */         } else {
/* 2770 */           BleManagerHandler.this.log(5, () -> "PHY read failed with status " + status);
/* 2771 */           if (BleManagerHandler.this.request instanceof PhyRequest) {
/* 2772 */             BleManagerHandler.this.request.notifyFail(gatt.getDevice(), status);
/*      */           }
/* 2774 */           BleManagerHandler.this.awaitingRequest = null;
/* 2775 */           BleManagerHandler.this.postCallback(c -> c.onError(gatt.getDevice(), "Error on PHY read", status));
/*      */         } 
/* 2777 */         BleManagerHandler.this.checkCondition();
/* 2778 */         BleManagerHandler.this.nextRequest(true);
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public void onReadRemoteRssi(@NonNull BluetoothGatt gatt, @IntRange(from = -128L, to = 20L) int rssi, int status) {
/* 2785 */         if (status == 0) {
/* 2786 */           BleManagerHandler.this.log(4, () -> "Remote RSSI received: " + rssi + " dBm");
/* 2787 */           if (BleManagerHandler.this.request instanceof ReadRssiRequest) {
/* 2788 */             ((ReadRssiRequest)BleManagerHandler.this.request).notifyRssiRead(gatt.getDevice(), rssi);
/* 2789 */             BleManagerHandler.this.request.notifySuccess(gatt.getDevice());
/*      */           } 
/*      */         } else {
/* 2792 */           BleManagerHandler.this.log(5, () -> "Reading remote RSSI failed with status " + status);
/* 2793 */           if (BleManagerHandler.this.request instanceof ReadRssiRequest) {
/* 2794 */             BleManagerHandler.this.request.notifyFail(gatt.getDevice(), status);
/*      */           }
/* 2796 */           BleManagerHandler.this.awaitingRequest = null;
/* 2797 */           BleManagerHandler.this.postCallback(c -> c.onError(gatt.getDevice(), "Error on RSSI read", status));
/*      */         } 
/* 2799 */         BleManagerHandler.this.checkCondition();
/* 2800 */         BleManagerHandler.this.nextRequest(true);
/*      */       }
/*      */     };
/*      */   
/*      */   private int mapDisconnectStatusToReason(int status) {
/* 2805 */     switch (status) {
/*      */       case 0:
/* 2807 */         return 0;
/*      */       case 22:
/* 2809 */         return 1;
/*      */       case 19:
/* 2811 */         return 2;
/*      */       case 8:
/* 2813 */         return 10;
/*      */     } 
/* 2815 */     return -1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   final void onCharacteristicReadRequest(@NonNull BluetoothGattServer server, @NonNull BluetoothDevice device, int requestId, int offset, @NonNull BluetoothGattCharacteristic characteristic) {
/* 2823 */     log(3, () -> "[Server callback] Read request for characteristic " + characteristic.getUuid() + " (requestId=" + requestId + ", offset: " + offset + ")");
/*      */     
/* 2825 */     if (offset == 0) {
/* 2826 */       log(4, () -> "[Server] READ request for characteristic " + characteristic.getUuid() + " received");
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2835 */     DataProvider dataProvider = this.dataProviders.get(characteristic);
/* 2836 */     byte[] data = (offset == 0 && dataProvider != null) ? dataProvider.getData(device) : null;
/* 2837 */     if (data != null) {
/*      */ 
/*      */       
/* 2840 */       assign(characteristic, data);
/*      */     
/*      */     }
/*      */     else {
/*      */ 
/*      */       
/* 2846 */       data = (this.characteristicValues == null || !this.characteristicValues.containsKey(characteristic)) ? characteristic.getValue() : this.characteristicValues.get(characteristic);
/*      */     } 
/*      */     
/* 2849 */     WaitForReadRequest waitForReadRequest = null;
/*      */     
/* 2851 */     if (this.awaitingRequest instanceof WaitForReadRequest && this.awaitingRequest.characteristic == characteristic && 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2856 */       !this.awaitingRequest.isTriggerPending()) {
/* 2857 */       waitForReadRequest = (WaitForReadRequest)this.awaitingRequest;
/*      */ 
/*      */       
/* 2860 */       waitForReadRequest.setDataIfNull(data);
/* 2861 */       data = waitForReadRequest.getData(this.mtu);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2867 */     if (data != null && data.length > this.mtu - 1) {
/* 2868 */       data = Bytes.copy(data, offset, this.mtu - 1);
/*      */     }
/*      */     
/* 2871 */     sendResponse(server, device, 0, requestId, offset, data);
/*      */     
/* 2873 */     if (waitForReadRequest != null) {
/* 2874 */       waitForReadRequest.notifyPacketRead(device, data);
/*      */ 
/*      */       
/* 2877 */       if (!waitForReadRequest.hasMore() && (data == null || data.length < this.mtu - 1)) {
/* 2878 */         log(4, () -> "Wait for read complete");
/* 2879 */         waitForReadRequest.notifySuccess(device);
/* 2880 */         this.awaitingRequest = null;
/* 2881 */         nextRequest(true);
/*      */       } 
/* 2883 */     } else if (checkCondition()) {
/* 2884 */       nextRequest(true);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   final void onCharacteristicWriteRequest(@NonNull BluetoothGattServer server, @NonNull BluetoothDevice device, int requestId, @NonNull BluetoothGattCharacteristic characteristic, boolean preparedWrite, boolean responseNeeded, int offset, @NonNull byte[] value) {
/* 2893 */     log(3, () -> "[Server callback] Write " + (responseNeeded ? "request" : "command") + " to characteristic " + characteristic.getUuid() + " (requestId=" + requestId + ", prepareWrite=" + preparedWrite + ", responseNeeded=" + responseNeeded + ", offset: " + offset + ", value=" + ParserUtils.parseDebug(value) + ")");
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2898 */     if (offset == 0) {
/* 2899 */       log(4, () -> {
/*      */             String type = responseNeeded ? "WRITE REQUEST" : "WRITE COMMAND";
/*      */             
/*      */             String option = preparedWrite ? "Prepare " : "";
/*      */             
/*      */             return "[Server] " + option + type + " for characteristic " + characteristic.getUuid() + " received, value: " + ParserUtils.parse(value);
/*      */           });
/*      */     }
/* 2907 */     if (responseNeeded) {
/* 2908 */       sendResponse(server, device, 0, requestId, offset, value);
/*      */     }
/*      */ 
/*      */     
/* 2912 */     if (preparedWrite) {
/* 2913 */       if (this.preparedValues == null) {
/* 2914 */         this.preparedValues = new LinkedList<>();
/*      */       }
/* 2916 */       if (offset == 0)
/*      */       {
/* 2918 */         this.preparedValues.offer(new Pair(characteristic, value));
/*      */       }
/*      */       else
/*      */       {
/* 2922 */         Pair<Object, byte[]> last = this.preparedValues.peekLast();
/* 2923 */         if (last != null && characteristic.equals(last.first)) {
/* 2924 */           this.preparedValues.pollLast();
/* 2925 */           this.preparedValues.offer(new Pair(characteristic, Bytes.concat((byte[])last.second, value, offset)));
/*      */         } else {
/* 2927 */           this.prepareError = 7;
/*      */         }
/*      */       
/*      */       }
/*      */     
/* 2932 */     } else if (assignAndNotify(device, characteristic, value) || checkCondition()) {
/* 2933 */       nextRequest(true);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   final void onDescriptorReadRequest(@NonNull BluetoothGattServer server, @NonNull BluetoothDevice device, int requestId, int offset, @NonNull BluetoothGattDescriptor descriptor) {
/* 2941 */     log(3, () -> "[Server callback] Read request for descriptor " + descriptor.getUuid() + " (requestId=" + requestId + ", offset: " + offset + ")");
/*      */ 
/*      */     
/* 2944 */     if (offset == 0) {
/* 2945 */       log(4, () -> "[Server] READ request for descriptor " + descriptor.getUuid() + " received");
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2954 */     DataProvider dataProvider = this.dataProviders.get(descriptor);
/* 2955 */     byte[] data = (offset == 0 && dataProvider != null) ? dataProvider.getData(device) : null;
/* 2956 */     if (data != null) {
/*      */ 
/*      */       
/* 2959 */       assign(descriptor, data);
/*      */     
/*      */     }
/*      */     else {
/*      */ 
/*      */       
/* 2965 */       data = (this.descriptorValues == null || !this.descriptorValues.containsKey(descriptor)) ? descriptor.getValue() : this.descriptorValues.get(descriptor);
/*      */     } 
/*      */     
/* 2968 */     WaitForReadRequest waitForReadRequest = null;
/*      */     
/* 2970 */     if (this.awaitingRequest instanceof WaitForReadRequest && this.awaitingRequest.descriptor == descriptor && 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2975 */       !this.awaitingRequest.isTriggerPending()) {
/* 2976 */       waitForReadRequest = (WaitForReadRequest)this.awaitingRequest;
/*      */ 
/*      */       
/* 2979 */       waitForReadRequest.setDataIfNull(data);
/* 2980 */       data = waitForReadRequest.getData(this.mtu);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2986 */     if (data != null && data.length > this.mtu - 1) {
/* 2987 */       data = Bytes.copy(data, offset, this.mtu - 1);
/*      */     }
/*      */     
/* 2990 */     sendResponse(server, device, 0, requestId, offset, data);
/*      */     
/* 2992 */     if (waitForReadRequest != null) {
/* 2993 */       waitForReadRequest.notifyPacketRead(device, data);
/*      */ 
/*      */       
/* 2996 */       if (!waitForReadRequest.hasMore() && (data == null || data.length < this.mtu - 1)) {
/* 2997 */         waitForReadRequest.notifySuccess(device);
/* 2998 */         this.awaitingRequest = null;
/* 2999 */         nextRequest(true);
/*      */       } 
/* 3001 */     } else if (checkCondition()) {
/* 3002 */       nextRequest(true);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   final void onDescriptorWriteRequest(@NonNull BluetoothGattServer server, @NonNull BluetoothDevice device, int requestId, @NonNull BluetoothGattDescriptor descriptor, boolean preparedWrite, boolean responseNeeded, int offset, @NonNull byte[] value) {
/* 3011 */     log(3, () -> "[Server callback] Write " + (responseNeeded ? "request" : "command") + " to descriptor " + descriptor.getUuid() + " (requestId=" + requestId + ", prepareWrite=" + preparedWrite + ", responseNeeded=" + responseNeeded + ", offset: " + offset + ", value=" + ParserUtils.parseDebug(value) + ")");
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3016 */     if (offset == 0) {
/* 3017 */       log(4, () -> {
/*      */             String type = responseNeeded ? "WRITE REQUEST" : "WRITE COMMAND";
/*      */             
/*      */             String option = preparedWrite ? "Prepare " : "";
/*      */             
/*      */             return "[Server] " + option + type + " request for descriptor " + descriptor.getUuid() + " received, value: " + ParserUtils.parse(value);
/*      */           });
/*      */     }
/* 3025 */     if (responseNeeded) {
/* 3026 */       sendResponse(server, device, 0, requestId, offset, value);
/*      */     }
/*      */ 
/*      */     
/* 3030 */     if (preparedWrite) {
/* 3031 */       if (this.preparedValues == null) {
/* 3032 */         this.preparedValues = new LinkedList<>();
/*      */       }
/* 3034 */       if (offset == 0)
/*      */       {
/* 3036 */         this.preparedValues.offer(new Pair(descriptor, value));
/*      */       }
/*      */       else
/*      */       {
/* 3040 */         Pair<Object, byte[]> last = this.preparedValues.peekLast();
/* 3041 */         if (last != null && descriptor.equals(last.first)) {
/* 3042 */           this.preparedValues.pollLast();
/* 3043 */           this.preparedValues.offer(new Pair(descriptor, Bytes.concat((byte[])last.second, value, offset)));
/*      */         } else {
/* 3045 */           this.prepareError = 7;
/*      */         }
/*      */       
/*      */       }
/*      */     
/* 3050 */     } else if (assignAndNotify(device, descriptor, value) || checkCondition()) {
/* 3051 */       nextRequest(true);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   final void onExecuteWrite(@NonNull BluetoothGattServer server, @NonNull BluetoothDevice device, int requestId, boolean execute) {
/* 3059 */     log(3, () -> "[Server callback] Execute write request (requestId=" + requestId + ", execute=" + execute + ")");
/*      */     
/* 3061 */     if (execute) {
/* 3062 */       Deque<Pair<Object, byte[]>> values = this.preparedValues;
/* 3063 */       log(4, () -> "[Server] Execute write request received");
/* 3064 */       this.preparedValues = null;
/* 3065 */       if (this.prepareError != 0) {
/* 3066 */         sendResponse(server, device, this.prepareError, requestId, 0, null);
/* 3067 */         this.prepareError = 0;
/*      */         return;
/*      */       } 
/* 3070 */       sendResponse(server, device, 0, requestId, 0, null);
/*      */       
/* 3072 */       if (values == null || values.isEmpty()) {
/*      */         return;
/*      */       }
/* 3075 */       boolean startNextRequest = false;
/* 3076 */       for (Pair<Object, byte[]> value : values) {
/* 3077 */         if (value.first instanceof BluetoothGattCharacteristic) {
/* 3078 */           BluetoothGattCharacteristic characteristic = (BluetoothGattCharacteristic)value.first;
/* 3079 */           startNextRequest = (assignAndNotify(device, characteristic, (byte[])value.second) || startNextRequest); continue;
/* 3080 */         }  if (value.first instanceof BluetoothGattDescriptor) {
/* 3081 */           BluetoothGattDescriptor descriptor = (BluetoothGattDescriptor)value.first;
/* 3082 */           startNextRequest = (assignAndNotify(device, descriptor, (byte[])value.second) || startNextRequest);
/*      */         } 
/*      */       } 
/* 3085 */       if (checkCondition() || startNextRequest) {
/* 3086 */         nextRequest(true);
/*      */       }
/*      */     } else {
/* 3089 */       log(4, () -> "[Server] Cancel write request received");
/* 3090 */       this.preparedValues = null;
/* 3091 */       sendResponse(server, device, 0, requestId, 0, null);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   final void onNotificationSent(@NonNull BluetoothGattServer server, @NonNull BluetoothDevice device, int status) {
/* 3097 */     log(3, () -> "[Server callback] Notification sent (status=" + status + ")");
/* 3098 */     if (status == 0) {
/* 3099 */       notifyNotificationSent(device);
/*      */     } else {
/* 3101 */       Log.e("BleManager", "onNotificationSent error " + status);
/* 3102 */       if (this.request instanceof WriteRequest) {
/* 3103 */         this.request.notifyFail(device, status);
/*      */       }
/* 3105 */       this.awaitingRequest = null;
/* 3106 */       onError(device, "Error on sending notification/indication", status);
/*      */     } 
/* 3108 */     checkCondition();
/* 3109 */     nextRequest(true);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   @RequiresApi(api = 22)
/*      */   final void onMtuChanged(@NonNull BluetoothGattServer server, @NonNull BluetoothDevice device, int mtu) {
/* 3116 */     log(4, () -> "[Server] MTU changed to: " + mtu);
/* 3117 */     this.mtu = mtu;
/* 3118 */     checkCondition();
/* 3119 */     nextRequest(false);
/*      */   }
/*      */   
/*      */   private void notifyNotificationSent(@NonNull BluetoothDevice device) {
/* 3123 */     if (this.request instanceof WriteRequest) {
/* 3124 */       WriteRequest wr = (WriteRequest)this.request;
/* 3125 */       switch (wr.type) {
/*      */         case NOTIFY:
/* 3127 */           log(4, () -> "[Server] Notification sent");
/*      */           break;
/*      */         case INDICATE:
/* 3130 */           log(4, () -> "[Server] Indication sent");
/*      */           break;
/*      */       } 
/*      */       
/* 3134 */       wr.notifyPacketSent(device, wr.characteristic.getValue());
/* 3135 */       if (wr.hasMore()) {
/* 3136 */         enqueueFirst(wr);
/*      */       } else {
/* 3138 */         wr.notifySuccess(device);
/*      */       } 
/*      */     } 
/*      */   } @Deprecated private static interface CallbackRunnable {
/*      */     void run(@NonNull BleManagerCallbacks param1BleManagerCallbacks); } private static interface BondingObserverRunnable {
/*      */     void run(@NonNull BondingObserver param1BondingObserver); } private static interface ConnectionObserverRunnable {
/*      */     void run(@NonNull ConnectionObserver param1ConnectionObserver); } @FunctionalInterface
/* 3145 */   private static interface Loggable { String log(); } private void assign(@NonNull BluetoothGattCharacteristic characteristic, @NonNull byte[] value) { boolean isShared = (this.characteristicValues == null || !this.characteristicValues.containsKey(characteristic));
/* 3146 */     if (isShared) {
/* 3147 */       characteristic.setValue(value);
/*      */     } else {
/* 3149 */       this.characteristicValues.put(characteristic, value);
/*      */     }  }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean assignAndNotify(@NonNull BluetoothDevice device, @NonNull BluetoothGattCharacteristic characteristic, @NonNull byte[] value) {
/* 3156 */     assign(characteristic, value);
/*      */     
/*      */     ValueChangedCallback callback;
/* 3159 */     if ((callback = this.valueChangedCallbacks.get(characteristic)) != null) {
/* 3160 */       callback.notifyValueChanged(device, value);
/*      */     }
/*      */ 
/*      */     
/* 3164 */     if (this.awaitingRequest instanceof WaitForValueChangedRequest && this.awaitingRequest.characteristic == characteristic && 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 3169 */       !this.awaitingRequest.isTriggerPending()) {
/* 3170 */       WaitForValueChangedRequest waitForWrite = (WaitForValueChangedRequest)this.awaitingRequest;
/* 3171 */       if (waitForWrite.matches(value)) {
/*      */         
/* 3173 */         waitForWrite.notifyValueChanged(device, value);
/*      */ 
/*      */         
/* 3176 */         if (waitForWrite.isComplete()) {
/*      */           
/* 3178 */           waitForWrite.notifySuccess(device);
/*      */ 
/*      */ 
/*      */           
/* 3182 */           this.awaitingRequest = null;
/* 3183 */           return waitForWrite.isTriggerCompleteOrNull();
/*      */         } 
/*      */       } 
/*      */     } 
/* 3187 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   private void assign(@NonNull BluetoothGattDescriptor descriptor, @NonNull byte[] value) {
/* 3192 */     boolean isShared = (this.descriptorValues == null || !this.descriptorValues.containsKey(descriptor));
/* 3193 */     if (isShared) {
/* 3194 */       descriptor.setValue(value);
/*      */     } else {
/* 3196 */       this.descriptorValues.put(descriptor, value);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean assignAndNotify(@NonNull BluetoothDevice device, @NonNull BluetoothGattDescriptor descriptor, @NonNull byte[] value) {
/* 3203 */     assign(descriptor, value);
/*      */     
/*      */     ValueChangedCallback callback;
/* 3206 */     if ((callback = this.valueChangedCallbacks.get(descriptor)) != null) {
/* 3207 */       callback.notifyValueChanged(device, value);
/*      */     }
/*      */ 
/*      */     
/* 3211 */     if (this.awaitingRequest instanceof WaitForValueChangedRequest && this.awaitingRequest.descriptor == descriptor && 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 3216 */       !this.awaitingRequest.isTriggerPending()) {
/* 3217 */       WaitForValueChangedRequest waitForWrite = (WaitForValueChangedRequest)this.awaitingRequest;
/* 3218 */       if (waitForWrite.matches(value)) {
/*      */         
/* 3220 */         waitForWrite.notifyValueChanged(device, value);
/*      */ 
/*      */         
/* 3223 */         if (waitForWrite.isComplete()) {
/*      */           
/* 3225 */           waitForWrite.notifySuccess(device);
/*      */ 
/*      */ 
/*      */           
/* 3229 */           this.awaitingRequest = null;
/* 3230 */           return waitForWrite.isTriggerCompleteOrNull();
/*      */         } 
/*      */       } 
/*      */     } 
/* 3234 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void sendResponse(@NonNull BluetoothGattServer server, @NonNull BluetoothDevice device, int status, int requestId, int offset, @Nullable byte[] response) {
/*      */     String msg;
/* 3242 */     switch (status) { case 0:
/* 3243 */         msg = "GATT_SUCCESS"; break;
/* 3244 */       case 6: msg = "GATT_REQUEST_NOT_SUPPORTED"; break;
/* 3245 */       case 7: msg = "GATT_INVALID_OFFSET"; break;
/* 3246 */       default: throw new InvalidParameterException(); }
/*      */     
/* 3248 */     log(3, () -> "server.sendResponse(" + msg + ", offset=" + offset + ", value=" + ParserUtils.parseDebug(response) + ")");
/*      */ 
/*      */     
/* 3251 */     server.sendResponse(device, requestId, status, offset, response);
/* 3252 */     log(2, () -> "[Server] Response sent");
/*      */   }
/*      */   
/*      */   private boolean checkCondition() {
/* 3256 */     if (this.awaitingRequest instanceof ConditionalWaitRequest) {
/* 3257 */       ConditionalWaitRequest<?> cwr = (ConditionalWaitRequest)this.awaitingRequest;
/* 3258 */       if (cwr.isFulfilled()) {
/* 3259 */         log(4, () -> "Condition fulfilled");
/* 3260 */         cwr.notifySuccess(this.bluetoothDevice);
/* 3261 */         this.awaitingRequest = null;
/* 3262 */         return true;
/*      */       } 
/*      */     } 
/* 3265 */     return false; } @SuppressLint({"MissingPermission"}) private synchronized void nextRequest(boolean force) { ConnectRequest cr;
/*      */     WriteRequest wr;
/*      */     SetValueRequest svr;
/*      */     MtuRequest mr;
/*      */     ConnectionPriorityRequest cpr;
/*      */     PhyRequest pr;
/*      */     Request r;
/*      */     SleepRequest sr;
/*      */     byte[] data;
/* 3274 */     if (force && this.operationInProgress) {
/* 3275 */       this.operationInProgress = (this.awaitingRequest != null);
/*      */     }
/*      */     
/* 3278 */     if (this.operationInProgress) {
/*      */       return;
/*      */     }
/* 3281 */     BluetoothDevice bluetoothDevice = this.bluetoothDevice;
/*      */ 
/*      */     
/* 3284 */     Request request = null;
/*      */     
/*      */     try {
/* 3287 */       if (this.requestQueue != null) {
/* 3288 */         if (this.requestQueue.hasMore()) {
/*      */           
/* 3290 */           request = this.requestQueue.getNext().setRequestHandler(this);
/*      */         } else {
/* 3292 */           if (this.requestQueue instanceof ReliableWriteRequest) {
/* 3293 */             ReliableWriteRequest rwr = (ReliableWriteRequest)this.requestQueue;
/* 3294 */             if (rwr.isCancelled()) {
/* 3295 */               this.requestQueue.notifyFail(bluetoothDevice, -7);
/*      */             }
/*      */           } 
/*      */           
/* 3299 */           this.requestQueue.notifySuccess(bluetoothDevice);
/* 3300 */           this.requestQueue = null;
/*      */         } 
/*      */       }
/*      */       
/* 3304 */       if (request == null) {
/* 3305 */         request = (this.initQueue != null) ? this.initQueue.poll() : null;
/*      */       }
/* 3307 */     } catch (Exception e) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 3312 */       request = null;
/*      */     } 
/*      */ 
/*      */     
/* 3316 */     if (request == null) {
/* 3317 */       if (this.initQueue != null) {
/* 3318 */         this.initQueue = null;
/*      */ 
/*      */ 
/*      */         
/* 3322 */         this.operationInProgress = true;
/* 3323 */         this.ready = true;
/* 3324 */         this.manager.onDeviceReady();
/* 3325 */         if (bluetoothDevice != null) {
/* 3326 */           postCallback(c -> c.onDeviceReady(bluetoothDevice));
/* 3327 */           postConnectionStateChange(o -> o.onDeviceReady(bluetoothDevice));
/*      */         } 
/* 3329 */         if (this.connectRequest != null) {
/* 3330 */           this.connectRequest.notifySuccess(this.connectRequest.getDevice());
/* 3331 */           this.connectRequest = null;
/*      */         } 
/*      */       } 
/*      */       
/*      */       try {
/* 3336 */         request = this.taskQueue.remove();
/* 3337 */       } catch (Exception e) {
/*      */         
/* 3339 */         this.operationInProgress = false;
/* 3340 */         this.request = null;
/* 3341 */         this.manager.onManagerReady();
/*      */         
/*      */         return;
/*      */       } 
/*      */     } 
/*      */     
/* 3347 */     if (request.finished) {
/* 3348 */       nextRequest(false);
/*      */       
/*      */       return;
/*      */     } 
/* 3352 */     boolean result = false;
/* 3353 */     this.operationInProgress = true;
/* 3354 */     this.request = request;
/*      */     
/* 3356 */     if (request instanceof AwaitingRequest) {
/* 3357 */       AwaitingRequest<?> awaitingRequest = (AwaitingRequest)request;
/*      */ 
/*      */ 
/*      */       
/* 3361 */       int requiredProperty = 0;
/* 3362 */       switch (request.type) {
/*      */         case WAIT_FOR_NOTIFICATION:
/* 3364 */           requiredProperty = 16;
/*      */           break;
/*      */         case WAIT_FOR_INDICATION:
/* 3367 */           requiredProperty = 32;
/*      */           break;
/*      */         case WAIT_FOR_READ:
/* 3370 */           requiredProperty = 2;
/*      */           break;
/*      */         case WAIT_FOR_WRITE:
/* 3373 */           requiredProperty = 76;
/*      */           break;
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 3380 */       result = (this.connected && bluetoothDevice != null && (awaitingRequest.characteristic == null || (awaitingRequest.characteristic.getProperties() & requiredProperty) != 0));
/* 3381 */       if (result) {
/* 3382 */         if (awaitingRequest instanceof ConditionalWaitRequest) {
/* 3383 */           ConditionalWaitRequest<?> cwr = (ConditionalWaitRequest)awaitingRequest;
/* 3384 */           log(2, () -> "Waiting for fulfillment of condition...");
/* 3385 */           if (cwr.isFulfilled()) {
/* 3386 */             cwr.notifyStarted(bluetoothDevice);
/* 3387 */             log(4, () -> "Condition fulfilled");
/* 3388 */             cwr.notifySuccess(bluetoothDevice);
/* 3389 */             nextRequest(true);
/*      */             return;
/*      */           } 
/*      */         } 
/* 3393 */         if (awaitingRequest instanceof WaitForReadRequest) {
/* 3394 */           log(2, () -> "Waiting for read request...");
/*      */         }
/* 3396 */         if (awaitingRequest instanceof WaitForValueChangedRequest) {
/* 3397 */           log(2, () -> "Waiting for value change...");
/*      */         }
/* 3399 */         this.awaitingRequest = awaitingRequest;
/*      */         
/* 3401 */         if (awaitingRequest.getTrigger() != null) {
/*      */           
/* 3403 */           awaitingRequest.notifyStarted(bluetoothDevice);
/*      */ 
/*      */ 
/*      */           
/* 3407 */           this.request = request = awaitingRequest.getTrigger();
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 3412 */     if (request.type == Request.Type.CONNECT) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 3417 */       ConnectRequest connectRequest = (ConnectRequest)request;
/* 3418 */       connectRequest.notifyStarted(connectRequest.getDevice());
/*      */     }
/* 3420 */     else if (bluetoothDevice != null) {
/* 3421 */       request.notifyStarted(bluetoothDevice);
/*      */     } else {
/*      */       
/* 3424 */       request.notifyInvalidRequest();
/*      */       
/* 3426 */       this.awaitingRequest = null;
/* 3427 */       nextRequest(true);
/*      */       
/*      */       return;
/*      */     } 
/*      */     
/* 3432 */     switch (request.type) {
/*      */       
/*      */       case CONNECT:
/* 3435 */         cr = (ConnectRequest)request;
/* 3436 */         this.connectRequest = cr;
/* 3437 */         this.request = null;
/* 3438 */         result = internalConnect(cr.getDevice(), cr);
/*      */         break;
/*      */       
/*      */       case DISCONNECT:
/* 3442 */         result = internalDisconnect(0);
/*      */         break;
/*      */       
/*      */       case ENSURE_BOND:
/* 3446 */         result = internalCreateBond(true);
/*      */         break;
/*      */       
/*      */       case CREATE_BOND:
/* 3450 */         result = internalCreateBond(false);
/*      */         break;
/*      */       
/*      */       case REMOVE_BOND:
/* 3454 */         result = internalRemoveBond();
/*      */         break;
/*      */ 
/*      */       
/*      */       case SET:
/* 3459 */         this.requestQueue = (RequestQueue)request;
/* 3460 */         nextRequest(true);
/*      */         return;
/*      */       
/*      */       case READ:
/* 3464 */         result = internalReadCharacteristic(request.characteristic);
/*      */         break;
/*      */ 
/*      */       
/*      */       case WRITE:
/* 3469 */         wr = (WriteRequest)request;
/* 3470 */         result = internalWriteCharacteristic(wr.characteristic, wr.getData(this.mtu), wr.getWriteType());
/*      */         break;
/*      */       
/*      */       case READ_DESCRIPTOR:
/* 3474 */         result = internalReadDescriptor(request.descriptor);
/*      */         break;
/*      */ 
/*      */       
/*      */       case WRITE_DESCRIPTOR:
/* 3479 */         wr = (WriteRequest)request;
/* 3480 */         result = internalWriteDescriptor(wr.descriptor, wr.getData(this.mtu));
/*      */         break;
/*      */ 
/*      */       
/*      */       case NOTIFY:
/*      */       case INDICATE:
/* 3486 */         wr = (WriteRequest)request;
/* 3487 */         data = wr.getData(this.mtu);
/* 3488 */         if (wr.characteristic != null) {
/* 3489 */           wr.characteristic.setValue(data);
/* 3490 */           if (this.characteristicValues != null && this.characteristicValues.containsKey(wr.characteristic))
/* 3491 */             this.characteristicValues.put(wr.characteristic, data); 
/*      */         } 
/* 3493 */         result = internalSendNotification(wr.characteristic, (request.type == Request.Type.INDICATE), data);
/*      */         break;
/*      */ 
/*      */       
/*      */       case SET_VALUE:
/* 3498 */         svr = (SetValueRequest)request;
/* 3499 */         if (svr.characteristic != null) {
/* 3500 */           if (this.characteristicValues != null && this.characteristicValues.containsKey(svr.characteristic)) {
/* 3501 */             this.characteristicValues.put(svr.characteristic, svr.getData(this.mtu));
/*      */           } else {
/* 3503 */             svr.characteristic.setValue(svr.getData(this.mtu));
/* 3504 */           }  result = true;
/* 3505 */           svr.notifySuccess(bluetoothDevice);
/* 3506 */           nextRequest(true);
/*      */         } 
/*      */         break;
/*      */ 
/*      */       
/*      */       case SET_DESCRIPTOR_VALUE:
/* 3512 */         svr = (SetValueRequest)request;
/* 3513 */         if (svr.descriptor != null) {
/* 3514 */           if (this.descriptorValues != null && this.descriptorValues.containsKey(svr.descriptor)) {
/* 3515 */             this.descriptorValues.put(svr.descriptor, svr.getData(this.mtu));
/*      */           } else {
/* 3517 */             svr.descriptor.setValue(svr.getData(this.mtu));
/* 3518 */           }  result = true;
/* 3519 */           svr.notifySuccess(bluetoothDevice);
/* 3520 */           nextRequest(true);
/*      */         } 
/*      */         break;
/*      */       
/*      */       case BEGIN_RELIABLE_WRITE:
/* 3525 */         result = internalBeginReliableWrite();
/*      */ 
/*      */         
/* 3528 */         if (result) {
/* 3529 */           this.request.notifySuccess(bluetoothDevice);
/* 3530 */           nextRequest(true);
/*      */           return;
/*      */         } 
/*      */         break;
/*      */       
/*      */       case EXECUTE_RELIABLE_WRITE:
/* 3536 */         result = internalExecuteReliableWrite();
/*      */         break;
/*      */       
/*      */       case ABORT_RELIABLE_WRITE:
/* 3540 */         result = internalAbortReliableWrite();
/*      */         break;
/*      */       
/*      */       case ENABLE_NOTIFICATIONS:
/* 3544 */         result = internalEnableNotifications(request.characteristic);
/*      */         break;
/*      */       
/*      */       case ENABLE_INDICATIONS:
/* 3548 */         result = internalEnableIndications(request.characteristic);
/*      */         break;
/*      */       
/*      */       case DISABLE_NOTIFICATIONS:
/* 3552 */         result = internalDisableNotifications(request.characteristic);
/*      */         break;
/*      */       
/*      */       case DISABLE_INDICATIONS:
/* 3556 */         result = internalDisableIndications(request.characteristic);
/*      */         break;
/*      */       
/*      */       case READ_BATTERY_LEVEL:
/* 3560 */         result = internalReadBatteryLevel();
/*      */         break;
/*      */       
/*      */       case ENABLE_BATTERY_LEVEL_NOTIFICATIONS:
/* 3564 */         result = internalSetBatteryNotifications(true);
/*      */         break;
/*      */       
/*      */       case DISABLE_BATTERY_LEVEL_NOTIFICATIONS:
/* 3568 */         result = internalSetBatteryNotifications(false);
/*      */         break;
/*      */       
/*      */       case ENABLE_SERVICE_CHANGED_INDICATIONS:
/* 3572 */         result = ensureServiceChangedEnabled();
/*      */         break;
/*      */ 
/*      */       
/*      */       case REQUEST_MTU:
/* 3577 */         mr = (MtuRequest)request;
/* 3578 */         if (this.mtu != mr.getRequiredMtu() && Build.VERSION.SDK_INT >= 21) {
/*      */           
/* 3580 */           result = internalRequestMtu(mr.getRequiredMtu()); break;
/*      */         } 
/* 3582 */         result = this.connected;
/* 3583 */         if (result) {
/* 3584 */           mr.notifyMtuChanged(bluetoothDevice, this.mtu);
/* 3585 */           mr.notifySuccess(bluetoothDevice);
/* 3586 */           nextRequest(true);
/*      */           return;
/*      */         } 
/*      */         break;
/*      */ 
/*      */ 
/*      */       
/*      */       case REQUEST_CONNECTION_PRIORITY:
/* 3594 */         cpr = (ConnectionPriorityRequest)request;
/* 3595 */         this.connectionPriorityOperationInProgress = (Build.VERSION.SDK_INT >= 26);
/* 3596 */         if (Build.VERSION.SDK_INT >= 21) {
/* 3597 */           result = internalRequestConnectionPriority(cpr.getRequiredPriority());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 3605 */           if (result) {
/* 3606 */             postDelayed(() -> { if (cpr.notifySuccess(bluetoothDevice)) { this.connectionPriorityOperationInProgress = false; nextRequest(true); }  }200L);
/*      */ 
/*      */             
/*      */             break;
/*      */           } 
/*      */ 
/*      */           
/* 3613 */           this.connectionPriorityOperationInProgress = false;
/*      */         } 
/*      */         break;
/*      */ 
/*      */ 
/*      */       
/*      */       case SET_PREFERRED_PHY:
/* 3620 */         pr = (PhyRequest)request;
/* 3621 */         if (Build.VERSION.SDK_INT >= 26) {
/* 3622 */           result = internalSetPreferredPhy(pr
/* 3623 */               .getPreferredTxPhy(), pr
/* 3624 */               .getPreferredRxPhy(), pr
/* 3625 */               .getPreferredPhyOptions());
/*      */           
/* 3627 */           if (Build.VERSION.SDK_INT == 33)
/*      */           {
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 3633 */             this.handler.postDelayed(() -> { if (!pr.finished) { log(5, ()); internalReadPhy(); }  }1000L);
/*      */           }
/*      */ 
/*      */           
/*      */           break;
/*      */         } 
/*      */ 
/*      */         
/* 3641 */         result = this.connected;
/* 3642 */         if (result) {
/* 3643 */           pr.notifyLegacyPhy(bluetoothDevice);
/* 3644 */           pr.notifySuccess(bluetoothDevice);
/* 3645 */           nextRequest(true);
/*      */           return;
/*      */         } 
/*      */         break;
/*      */ 
/*      */ 
/*      */       
/*      */       case READ_PHY:
/* 3653 */         pr = (PhyRequest)request;
/* 3654 */         if (Build.VERSION.SDK_INT >= 26) {
/* 3655 */           result = internalReadPhy(); break;
/*      */         } 
/* 3657 */         result = this.connected;
/* 3658 */         if (result) {
/* 3659 */           pr.notifyLegacyPhy(bluetoothDevice);
/* 3660 */           pr.notifySuccess(bluetoothDevice);
/* 3661 */           nextRequest(true);
/*      */           return;
/*      */         } 
/*      */         break;
/*      */ 
/*      */       
/*      */       case READ_RSSI:
/* 3668 */         r = request;
/* 3669 */         result = internalReadRssi();
/* 3670 */         if (result) {
/* 3671 */           postDelayed(() -> { if (this.request == r) { r.notifyFail(bluetoothDevice, -5); nextRequest(true); }  }1000L);
/*      */         }
/*      */         break;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       case REFRESH_CACHE:
/* 3683 */         r = request;
/* 3684 */         result = internalRefreshDeviceCache();
/* 3685 */         if (result) {
/* 3686 */           postDelayed(() -> { log(4, ()); r.notifySuccess(bluetoothDevice); this.request = null; if (this.awaitingRequest != null) { this.awaitingRequest.notifyFail(bluetoothDevice, -3); this.awaitingRequest = null; }  this.taskQueue.clear(); this.initQueue = null; BluetoothGatt bluetoothGatt = this.bluetoothGatt; if (this.connected && bluetoothGatt != null) { this.manager.onServicesInvalidated(); onDeviceDisconnected(); this.serviceDiscoveryRequested = true; this.servicesDiscovered = false; log(2, ()); log(3, ()); bluetoothGatt.discoverServices(); }  }200L);
/*      */         }
/*      */         break;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       case SLEEP:
/* 3714 */         sr = (SleepRequest)request;
/* 3715 */         log(3, () -> "sleep(" + sr.timeout + ")");
/* 3716 */         result = true;
/*      */         break;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3729 */     if (!result && bluetoothDevice != null) {
/* 3730 */       request.notifyFail(bluetoothDevice, 
/* 3731 */           this.connected ? 
/* 3732 */           -3 : (
/* 3733 */           BluetoothAdapter.getDefaultAdapter().isEnabled() ? 
/* 3734 */           -1 : 
/* 3735 */           -100));
/* 3736 */       this.awaitingRequest = null;
/* 3737 */       this.connectionPriorityOperationInProgress = false;
/* 3738 */       nextRequest(true);
/*      */     }  }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean isServiceChangedCCCD(@Nullable BluetoothGattDescriptor descriptor) {
/* 3751 */     return (descriptor != null && BleManager.SERVICE_CHANGED_CHARACTERISTIC
/* 3752 */       .equals(descriptor.getCharacteristic().getUuid()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean isServiceChangedCharacteristic(@Nullable BluetoothGattCharacteristic characteristic) {
/* 3762 */     return (characteristic != null && BleManager.SERVICE_CHANGED_CHARACTERISTIC
/* 3763 */       .equals(characteristic.getUuid()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   private boolean isBatteryLevelCharacteristic(@Nullable BluetoothGattCharacteristic characteristic) {
/* 3774 */     return (characteristic != null && BleManager.BATTERY_LEVEL_CHARACTERISTIC
/* 3775 */       .equals(characteristic.getUuid()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean isCCCD(@Nullable BluetoothGattDescriptor descriptor) {
/* 3785 */     return (descriptor != null && BleManager.CLIENT_CHARACTERISTIC_CONFIG_DESCRIPTOR_UUID
/* 3786 */       .equals(descriptor.getUuid()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void log(int priority, @NonNull Loggable message) {
/* 3802 */     if (priority >= this.manager.getMinLogPriority())
/* 3803 */       this.manager.log(priority, message.log()); 
/*      */   }
/*      */   
/*      */   protected abstract boolean isRequiredServiceSupported(@NonNull BluetoothGatt paramBluetoothGatt);
/*      */   
/*      */   @Deprecated
/*      */   protected abstract void onServicesInvalidated();
/*      */ }


/* Location:              C:\Users\Admin\Downloads\jd-gui-windows-1.6.6\classes.jar!\no\nordicsemi\android\ble\BleManagerHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */