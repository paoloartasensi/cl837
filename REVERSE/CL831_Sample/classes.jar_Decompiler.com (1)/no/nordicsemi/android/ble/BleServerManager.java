package no.nordicsemi.android.ble;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattServer;
import android.bluetooth.BluetoothGattServerCallback;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.StringRes;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.UUID;
import no.nordicsemi.android.ble.data.Data;
import no.nordicsemi.android.ble.observer.ServerObserver;
import no.nordicsemi.android.ble.utils.ILogger;

public abstract class BleServerManager implements ILogger {
   private static final UUID CHARACTERISTIC_EXTENDED_PROPERTIES_DESCRIPTOR_UUID = UUID.fromString("00002900-0000-1000-8000-00805f9b34fb");
   private static final UUID CLIENT_USER_DESCRIPTION_DESCRIPTOR_UUID = UUID.fromString("00002901-0000-1000-8000-00805f9b34fb");
   private static final UUID CLIENT_CHARACTERISTIC_CONFIG_DESCRIPTOR_UUID = UUID.fromString("00002902-0000-1000-8000-00805f9b34fb");
   private BluetoothGattServer server;
   private final List<BleManager> managers = new ArrayList();
   private final Context context;
   private ServerObserver serverObserver;
   private Queue<BluetoothGattService> serverServices;
   private List<BluetoothGattCharacteristic> sharedCharacteristics;
   private List<BluetoothGattDescriptor> sharedDescriptors;
   private final BluetoothGattServerCallback gattServerCallback = new BluetoothGattServerCallback() {
      @SuppressLint({"MissingPermission"})
      public void onServiceAdded(int status, @NonNull BluetoothGattService service) {
         if (status == 0) {
            try {
               BluetoothGattService nextService = (BluetoothGattService)BleServerManager.this.serverServices.remove();
               BleServerManager.this.server.addService(nextService);
            } catch (Exception var4) {
               BleServerManager.this.log(4, "[Server] All services added successfully");
               if (BleServerManager.this.serverObserver != null) {
                  BleServerManager.this.serverObserver.onServerReady();
               }

               BleServerManager.this.serverServices = null;
            }
         } else {
            BleServerManager.this.log(6, "[Server] Adding service failed with error " + status);
         }

      }

      public void onConnectionStateChange(@NonNull BluetoothDevice device, int status, int newState) {
         if (status == 0 && newState == 2) {
            BleServerManager.this.log(4, "[Server] " + device.getAddress() + " is now connected");
            if (BleServerManager.this.serverObserver != null) {
               BleServerManager.this.serverObserver.onDeviceConnectedToServer(device);
            }
         } else {
            if (status == 0) {
               BleServerManager.this.log(4, "[Server] " + device.getAddress() + " is disconnected");
            } else {
               BleServerManager.this.log(5, "[Server] " + device.getAddress() + " has disconnected connected with status: " + status);
            }

            if (BleServerManager.this.serverObserver != null) {
               BleServerManager.this.serverObserver.onDeviceDisconnectedFromServer(device);
            }
         }

      }

      public void onCharacteristicReadRequest(@NonNull BluetoothDevice device, int requestId, int offset, @NonNull BluetoothGattCharacteristic characteristic) {
         BleManagerHandler handler = BleServerManager.this.getRequestHandler(device);
         if (handler != null) {
            handler.onCharacteristicReadRequest(BleServerManager.this.server, device, requestId, offset, characteristic);
         }

      }

      public void onCharacteristicWriteRequest(@NonNull BluetoothDevice device, int requestId, @NonNull BluetoothGattCharacteristic characteristic, boolean preparedWrite, boolean responseNeeded, int offset, @NonNull byte[] value) {
         BleManagerHandler handler = BleServerManager.this.getRequestHandler(device);
         if (handler != null) {
            handler.onCharacteristicWriteRequest(BleServerManager.this.server, device, requestId, characteristic, preparedWrite, responseNeeded, offset, value);
         }

      }

      public void onDescriptorReadRequest(@NonNull BluetoothDevice device, int requestId, int offset, @NonNull BluetoothGattDescriptor descriptor) {
         BleManagerHandler handler = BleServerManager.this.getRequestHandler(device);
         if (handler != null) {
            handler.onDescriptorReadRequest(BleServerManager.this.server, device, requestId, offset, descriptor);
         }

      }

      public void onDescriptorWriteRequest(@NonNull BluetoothDevice device, int requestId, @NonNull BluetoothGattDescriptor descriptor, boolean preparedWrite, boolean responseNeeded, int offset, @NonNull byte[] value) {
         BleManagerHandler handler = BleServerManager.this.getRequestHandler(device);
         if (handler != null) {
            handler.onDescriptorWriteRequest(BleServerManager.this.server, device, requestId, descriptor, preparedWrite, responseNeeded, offset, value);
         }

      }

      public void onExecuteWrite(@NonNull BluetoothDevice device, int requestId, boolean execute) {
         BleManagerHandler handler = BleServerManager.this.getRequestHandler(device);
         if (handler != null) {
            handler.onExecuteWrite(BleServerManager.this.server, device, requestId, execute);
         }

      }

      @RequiresApi(
         api = 21
      )
      public void onNotificationSent(@NonNull BluetoothDevice device, int status) {
         BleManagerHandler handler = BleServerManager.this.getRequestHandler(device);
         if (handler != null) {
            handler.onNotificationSent(BleServerManager.this.server, device, status);
         }

      }

      @RequiresApi(
         api = 22
      )
      public void onMtuChanged(@NonNull BluetoothDevice device, int mtu) {
         BleManagerHandler handler = BleServerManager.this.getRequestHandler(device);
         if (handler != null) {
            handler.onMtuChanged(BleServerManager.this.server, device, mtu);
         }

      }
   };

   public BleServerManager(@NonNull Context context) {
      this.context = context;
   }

   @SuppressLint({"MissingPermission"})
   public final boolean open() {
      if (this.server != null) {
         return true;
      } else {
         this.serverServices = new LinkedList(this.initializeServer());
         BluetoothManager bm = (BluetoothManager)this.context.getSystemService("bluetooth");
         if (bm != null) {
            this.server = bm.openGattServer(this.context, this.gattServerCallback);
         }

         if (this.server != null) {
            this.log(4, "[Server] Server started successfully");

            try {
               BluetoothGattService service = (BluetoothGattService)this.serverServices.remove();
               this.server.addService(service);
            } catch (NoSuchElementException var3) {
               if (this.serverObserver != null) {
                  this.serverObserver.onServerReady();
               }
            } catch (Exception var4) {
               this.close();
               return false;
            }

            return true;
         } else {
            this.log(5, "GATT server initialization failed");
            this.serverServices = null;
            return false;
         }
      }
   }

   @SuppressLint({"MissingPermission"})
   public final void close() {
      if (this.server != null) {
         this.server.close();
         this.server = null;
      }

      this.serverServices = null;
      Iterator var1 = this.managers.iterator();

      while(var1.hasNext()) {
         BleManager manager = (BleManager)var1.next();
         manager.closeServer();
         manager.close();
      }

      this.managers.clear();
   }

   public final void setServerObserver(@Nullable ServerObserver observer) {
      this.serverObserver = observer;
   }

   @Nullable
   final BluetoothGattServer getServer() {
      return this.server;
   }

   final void addManager(@NonNull BleManager manager) {
      if (!this.managers.contains(manager)) {
         this.managers.add(manager);
      }

   }

   final void removeManager(@NonNull BleManager manager) {
      this.managers.remove(manager);
   }

   final boolean isShared(@NonNull BluetoothGattCharacteristic characteristic) {
      return this.sharedCharacteristics != null && this.sharedCharacteristics.contains(characteristic);
   }

   final boolean isShared(@NonNull BluetoothGattDescriptor descriptor) {
      return this.sharedDescriptors != null && this.sharedDescriptors.contains(descriptor);
   }

   @Nullable
   private BleManagerHandler getRequestHandler(@NonNull BluetoothDevice device) {
      Iterator var2 = this.managers.iterator();

      BleManager manager;
      do {
         if (!var2.hasNext()) {
            return null;
         }

         manager = (BleManager)var2.next();
      } while(!device.equals(manager.getBluetoothDevice()));

      return manager.requestHandler;
   }

   public int getMinLogPriority() {
      return 4;
   }

   public void log(int priority, @NonNull String message) {
   }

   public void log(int priority, @StringRes int messageRes, @Nullable Object... params) {
      String message = this.context.getString(messageRes, params);
      this.log(priority, message);
   }

   @NonNull
   protected abstract List<BluetoothGattService> initializeServer();

   @NonNull
   protected final BluetoothGattService service(@NonNull UUID uuid, BluetoothGattCharacteristic... characteristics) {
      BluetoothGattService service = new BluetoothGattService(uuid, 0);
      BluetoothGattCharacteristic[] var4 = characteristics;
      int var5 = characteristics.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         BluetoothGattCharacteristic characteristic = var4[var6];
         service.addCharacteristic(characteristic);
      }

      return service;
   }

   @NonNull
   protected final BluetoothGattCharacteristic characteristic(@NonNull UUID uuid, int properties, int permissions, @Nullable byte[] initialValue, BluetoothGattDescriptor... descriptors) {
      boolean writableAuxiliaries = false;
      boolean cccdFound = false;
      boolean cepdFound = false;
      BluetoothGattDescriptor cepd = null;
      BluetoothGattDescriptor[] var10 = descriptors;
      int var11 = descriptors.length;

      for(int var12 = 0; var12 < var11; ++var12) {
         BluetoothGattDescriptor descriptor = var10[var12];
         if (CLIENT_CHARACTERISTIC_CONFIG_DESCRIPTOR_UUID.equals(descriptor.getUuid())) {
            cccdFound = true;
         } else if (CLIENT_USER_DESCRIPTION_DESCRIPTOR_UUID.equals(descriptor.getUuid()) && 0 != (descriptor.getPermissions() & 112)) {
            writableAuxiliaries = true;
         } else if (CHARACTERISTIC_EXTENDED_PROPERTIES_DESCRIPTOR_UUID.equals(descriptor.getUuid())) {
            cepd = descriptor;
            cepdFound = true;
         }
      }

      if (writableAuxiliaries) {
         if (cepd == null) {
            cepd = new BluetoothGattDescriptor(CHARACTERISTIC_EXTENDED_PROPERTIES_DESCRIPTOR_UUID, 1);
            cepd.setValue(new byte[]{2, 0});
         } else if (cepd.getValue() != null && cepd.getValue().length == 2) {
            byte[] var10000 = cepd.getValue();
            var10000[0] = (byte)(var10000[0] | 2);
         } else {
            cepd.setValue(new byte[]{2, 0});
         }
      }

      boolean cccdRequired = (properties & 48) != 0;
      boolean reliableWrite = cepd != null && cepd.getValue() != null && cepd.getValue().length == 2 && (cepd.getValue()[0] & 1) != 0;
      if (writableAuxiliaries || reliableWrite) {
         properties |= 128;
      }

      if ((properties & 128) != 0 && cepd == null) {
         cepd = new BluetoothGattDescriptor(CHARACTERISTIC_EXTENDED_PROPERTIES_DESCRIPTOR_UUID, 1);
         cepd.setValue(new byte[]{0, 0});
      }

      BluetoothGattCharacteristic characteristic = new BluetoothGattCharacteristic(uuid, properties, permissions);
      if (cccdRequired && !cccdFound) {
         characteristic.addDescriptor(this.cccd());
      }

      BluetoothGattDescriptor[] var20 = descriptors;
      int var14 = descriptors.length;

      for(int var15 = 0; var15 < var14; ++var15) {
         BluetoothGattDescriptor descriptor = var20[var15];
         characteristic.addDescriptor(descriptor);
      }

      if (cepd != null && !cepdFound) {
         characteristic.addDescriptor(cepd);
      }

      characteristic.setValue(initialValue);
      return characteristic;
   }

   @NonNull
   protected final BluetoothGattCharacteristic characteristic(@NonNull UUID uuid, int properties, int permissions, @Nullable Data initialValue, BluetoothGattDescriptor... descriptors) {
      return this.characteristic(uuid, properties, permissions, initialValue != null ? initialValue.getValue() : null, descriptors);
   }

   @NonNull
   protected final BluetoothGattCharacteristic characteristic(@NonNull UUID uuid, int properties, int permissions, BluetoothGattDescriptor... descriptors) {
      return this.characteristic(uuid, properties, permissions, (byte[])null, descriptors);
   }

   @NonNull
   protected final BluetoothGattCharacteristic sharedCharacteristic(@NonNull UUID uuid, int properties, int permissions, @Nullable byte[] initialValue, BluetoothGattDescriptor... descriptors) {
      BluetoothGattCharacteristic characteristic = this.characteristic(uuid, properties, permissions, initialValue, descriptors);
      if (this.sharedCharacteristics == null) {
         this.sharedCharacteristics = new ArrayList();
      }

      this.sharedCharacteristics.add(characteristic);
      return characteristic;
   }

   @NonNull
   protected final BluetoothGattCharacteristic sharedCharacteristic(@NonNull UUID uuid, int properties, int permissions, @Nullable Data initialValue, BluetoothGattDescriptor... descriptors) {
      return this.sharedCharacteristic(uuid, properties, permissions, initialValue != null ? initialValue.getValue() : null, descriptors);
   }

   @NonNull
   protected final BluetoothGattCharacteristic sharedCharacteristic(@NonNull UUID uuid, int properties, int permissions, BluetoothGattDescriptor... descriptors) {
      return this.sharedCharacteristic(uuid, properties, permissions, (byte[])null, descriptors);
   }

   @NonNull
   protected final BluetoothGattDescriptor descriptor(@NonNull UUID uuid, int permissions, @Nullable byte[] initialValue) {
      BluetoothGattDescriptor descriptor = new BluetoothGattDescriptor(uuid, permissions);
      descriptor.setValue(initialValue);
      return descriptor;
   }

   @NonNull
   protected final BluetoothGattDescriptor descriptor(@NonNull UUID uuid, int permissions, @Nullable Data initialValue) {
      return this.descriptor(uuid, permissions, initialValue != null ? initialValue.getValue() : null);
   }

   @NonNull
   protected final BluetoothGattDescriptor sharedDescriptor(@NonNull UUID uuid, int permissions, @Nullable byte[] initialValue) {
      BluetoothGattDescriptor descriptor = this.descriptor(uuid, permissions, initialValue);
      if (this.sharedDescriptors == null) {
         this.sharedDescriptors = new ArrayList();
      }

      this.sharedDescriptors.add(descriptor);
      return descriptor;
   }

   @NonNull
   protected final BluetoothGattDescriptor sharedDescriptor(@NonNull UUID uuid, int permissions, @Nullable Data initialValue) {
      return this.sharedDescriptor(uuid, permissions, initialValue != null ? initialValue.getValue() : null);
   }

   @NonNull
   protected final BluetoothGattDescriptor cccd() {
      return this.descriptor(CLIENT_CHARACTERISTIC_CONFIG_DESCRIPTOR_UUID, 17, (byte[])(new byte[]{0, 0}));
   }

   @NonNull
   protected final BluetoothGattDescriptor sharedCccd() {
      return this.sharedDescriptor(CLIENT_CHARACTERISTIC_CONFIG_DESCRIPTOR_UUID, 17, (byte[])(new byte[]{0, 0}));
   }

   @NonNull
   protected final BluetoothGattDescriptor reliableWrite() {
      return this.sharedDescriptor(CHARACTERISTIC_EXTENDED_PROPERTIES_DESCRIPTOR_UUID, 1, (byte[])(new byte[]{1, 0}));
   }

   @NonNull
   protected final BluetoothGattDescriptor description(@Nullable String description, boolean writableAuxiliaries) {
      BluetoothGattDescriptor cud = this.descriptor(CLIENT_USER_DESCRIPTION_DESCRIPTOR_UUID, 1 | (writableAuxiliaries ? 16 : 0), description != null ? description.getBytes() : null);
      if (!writableAuxiliaries) {
         if (this.sharedDescriptors == null) {
            this.sharedDescriptors = new ArrayList();
         }

         this.sharedDescriptors.add(cud);
      }

      return cud;
   }
}
