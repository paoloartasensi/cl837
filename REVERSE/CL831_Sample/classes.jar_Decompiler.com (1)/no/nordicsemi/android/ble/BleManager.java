package no.nordicsemi.android.ble;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattServer;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Looper;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.RequiresPermission;
import androidx.annotation.RestrictTo;
import androidx.annotation.StringRes;
import androidx.annotation.RestrictTo.Scope;
import java.util.UUID;
import no.nordicsemi.android.ble.callback.ConnectionParametersUpdatedCallback;
import no.nordicsemi.android.ble.data.Data;
import no.nordicsemi.android.ble.data.DataProvider;
import no.nordicsemi.android.ble.observer.BondingObserver;
import no.nordicsemi.android.ble.observer.ConnectionObserver;
import no.nordicsemi.android.ble.utils.ILogger;
import no.nordicsemi.android.ble.utils.ParserUtils;

@SuppressLint({"MissingPermission"})
public abstract class BleManager implements ILogger {
   static final UUID CLIENT_CHARACTERISTIC_CONFIG_DESCRIPTOR_UUID = UUID.fromString("00002902-0000-1000-8000-00805f9b34fb");
   static final UUID BATTERY_SERVICE = UUID.fromString("0000180F-0000-1000-8000-00805f9b34fb");
   static final UUID BATTERY_LEVEL_CHARACTERISTIC = UUID.fromString("00002A19-0000-1000-8000-00805f9b34fb");
   static final UUID GENERIC_ATTRIBUTE_SERVICE = UUID.fromString("00001801-0000-1000-8000-00805f9b34fb");
   static final UUID SERVICE_CHANGED_CHARACTERISTIC = UUID.fromString("00002A05-0000-1000-8000-00805f9b34fb");
   public static final int PAIRING_VARIANT_PIN = 0;
   public static final int PAIRING_VARIANT_PASSKEY = 1;
   public static final int PAIRING_VARIANT_PASSKEY_CONFIRMATION = 2;
   public static final int PAIRING_VARIANT_CONSENT = 3;
   public static final int PAIRING_VARIANT_DISPLAY_PASSKEY = 4;
   public static final int PAIRING_VARIANT_DISPLAY_PIN = 5;
   public static final int PAIRING_VARIANT_OOB_CONSENT = 6;
   private final Context context;
   private BleServerManager serverManager;
   @NonNull
   final BleManager.BleManagerGattCallback requestHandler;
   /** @deprecated */
   @Deprecated
   protected BleManagerCallbacks callbacks;
   @Nullable
   BondingObserver bondingObserver;
   @Nullable
   ConnectionObserver connectionObserver;
   private final BroadcastReceiver mPairingRequestBroadcastReceiver;

   public BleManager(@NonNull Context context) {
      this(context, new Handler(Looper.getMainLooper()));
   }

   public BleManager(@NonNull Context context, @NonNull Handler handler) {
      this.mPairingRequestBroadcastReceiver = new BroadcastReceiver() {
         public void onReceive(Context context, Intent intent) {
            BluetoothDevice device = (BluetoothDevice)intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE");
            BluetoothDevice bluetoothDevice = BleManager.this.requestHandler.getBluetoothDevice();
            if (bluetoothDevice != null && device != null && device.getAddress().equals(bluetoothDevice.getAddress())) {
               int variant = intent.getIntExtra("android.bluetooth.device.extra.PAIRING_VARIANT", 0);
               int key = intent.getIntExtra("android.bluetooth.device.extra.PAIRING_KEY", -1);
               BleManager.this.log(3, "[Broadcast] Action received: android.bluetooth.device.action.PAIRING_REQUEST, pairing variant: " + ParserUtils.pairingVariantToString(variant) + " (" + variant + "); key: " + key);
               BleManager.this.onPairingRequestReceived(device, variant, key);
            }
         }
      };
      this.context = context;
      this.requestHandler = this.getGattCallback();
      this.requestHandler.init(this, handler);
      context.registerReceiver(this.mPairingRequestBroadcastReceiver, new IntentFilter("android.bluetooth.device.action.PAIRING_REQUEST"));
   }

   protected void initialize() {
      this.requestHandler.initialize();
   }

   protected boolean isRequiredServiceSupported(@NonNull BluetoothGatt gatt) {
      return this.requestHandler.isRequiredServiceSupported(gatt);
   }

   protected boolean isOptionalServiceSupported(@NonNull BluetoothGatt gatt) {
      return this.requestHandler.isOptionalServiceSupported(gatt);
   }

   protected void onServerReady(@NonNull BluetoothGattServer server) {
      this.requestHandler.onServerReady(server);
   }

   protected void onServicesInvalidated() {
      this.requestHandler.onServicesInvalidated();
   }

   protected void onDeviceReady() {
      this.requestHandler.onDeviceReady();
   }

   protected void onManagerReady() {
      this.requestHandler.onManagerReady();
   }

   public void close() {
      try {
         this.context.unregisterReceiver(this.mPairingRequestBroadcastReceiver);
      } catch (Exception var2) {
      }

      if (this.serverManager != null) {
         this.serverManager.removeManager(this);
      }

      this.requestHandler.close();
   }

   protected void runOnCallbackThread(@NonNull Runnable runnable) {
      this.requestHandler.post(runnable);
   }

   /** @deprecated */
   @Deprecated
   public void setGattCallbacks(@NonNull BleManagerCallbacks callbacks) {
      this.callbacks = callbacks;
   }

   public final void setConnectionObserver(@Nullable ConnectionObserver callback) {
      this.connectionObserver = callback;
   }

   @RestrictTo({Scope.LIBRARY_GROUP})
   @Nullable
   public final ConnectionObserver getConnectionObserver() {
      return this.connectionObserver;
   }

   public final void setBondingObserver(@Nullable BondingObserver callback) {
      this.bondingObserver = callback;
   }

   @RestrictTo({Scope.LIBRARY_GROUP})
   @Nullable
   public final BondingObserver getBondingObserver() {
      return this.bondingObserver;
   }

   public final void useServer(@NonNull BleServerManager server) {
      if (this.serverManager != null) {
         this.serverManager.removeManager(this);
      }

      this.serverManager = server;
      server.addManager(this);
      this.requestHandler.useServer(server);
   }

   final void closeServer() {
      this.serverManager = null;
      this.requestHandler.useServer((BleServerManager)null);
   }

   protected void onPairingRequestReceived(@NonNull BluetoothDevice device, int variant, int key) {
   }

   /** @deprecated */
   @Deprecated
   @NonNull
   protected BleManager.BleManagerGattCallback getGattCallback() {
      return new BleManager.BleManagerGattCallback() {
         protected boolean isRequiredServiceSupported(@NonNull BluetoothGatt gatt) {
            return false;
         }

         protected void onServicesInvalidated() {
         }
      };
   }

   @NonNull
   protected final Context getContext() {
      return this.context;
   }

   @Nullable
   public BluetoothDevice getBluetoothDevice() {
      return this.requestHandler.getBluetoothDevice();
   }

   public final boolean isConnected() {
      return this.requestHandler.isConnected();
   }

   public final boolean isReady() {
      return this.requestHandler.isReady();
   }

   protected final boolean isBonded() {
      BluetoothDevice bluetoothDevice = this.requestHandler.getBluetoothDevice();
      return bluetoothDevice != null && bluetoothDevice.getBondState() == 12;
   }

   public final int getConnectionState() {
      return this.requestHandler.getConnectionState();
   }

   /** @deprecated */
   @Deprecated
   @IntRange(
      from = -1L,
      to = 100L
   )
   public final int getBatteryValue() {
      return this.requestHandler.getBatteryValue();
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

   /** @deprecated */
   @Deprecated
   protected boolean shouldAutoConnect() {
      return false;
   }

   protected boolean shouldClearCacheWhenDisconnected() {
      return false;
   }

   @IntRange(
      from = 0L
   )
   protected int getServiceDiscoveryDelay(boolean bonded) {
      return bonded ? 1600 : 300;
   }

   @NonNull
   public final ConnectRequest connect(@NonNull BluetoothDevice device) {
      return Request.connect(device).useAutoConnect(this.shouldAutoConnect()).setRequestHandler(this.requestHandler);
   }

   /** @deprecated */
   @Deprecated
   @NonNull
   public final ConnectRequest connect(@NonNull BluetoothDevice device, int phy) {
      return Request.connect(device).usePreferredPhy(phy).useAutoConnect(this.shouldAutoConnect()).setRequestHandler(this.requestHandler);
   }

   @NonNull
   public final DisconnectRequest disconnect() {
      return Request.disconnect().setRequestHandler(this.requestHandler);
   }

   public void attachClientConnection(BluetoothDevice client) {
      this.requestHandler.attachClientConnection(client);
   }

   /** @deprecated */
   @Deprecated
   @NonNull
   @RequiresPermission("android.permission.BLUETOOTH_ADMIN")
   protected Request createBond() {
      return this.createBondInsecure();
   }

   @NonNull
   @RequiresPermission("android.permission.BLUETOOTH_ADMIN")
   protected Request createBondInsecure() {
      return Request.createBond().setRequestHandler(this.requestHandler);
   }

   @RequiresPermission("android.permission.BLUETOOTH_ADMIN")
   protected Request ensureBond() {
      return Request.ensureBond().setRequestHandler(this.requestHandler);
   }

   @NonNull
   @RequiresPermission("android.permission.BLUETOOTH_ADMIN")
   protected Request removeBond() {
      return Request.removeBond().setRequestHandler(this.requestHandler);
   }

   @NonNull
   protected ValueChangedCallback setNotificationCallback(@Nullable BluetoothGattCharacteristic characteristic) {
      return this.requestHandler.getValueChangedCallback(characteristic);
   }

   @NonNull
   protected ValueChangedCallback setIndicationCallback(@Nullable BluetoothGattCharacteristic characteristic) {
      return this.setNotificationCallback(characteristic);
   }

   @NonNull
   protected ValueChangedCallback setWriteCallback(@Nullable BluetoothGattCharacteristic serverCharacteristic) {
      return this.requestHandler.getValueChangedCallback(serverCharacteristic);
   }

   @NonNull
   protected ValueChangedCallback setWriteCallback(@Nullable BluetoothGattDescriptor serverDescriptor) {
      return this.requestHandler.getValueChangedCallback(serverDescriptor);
   }

   protected void removeNotificationCallback(@Nullable BluetoothGattCharacteristic characteristic) {
      this.requestHandler.removeValueChangedCallback(characteristic);
   }

   protected void removeIndicationCallback(@Nullable BluetoothGattCharacteristic characteristic) {
      this.removeNotificationCallback(characteristic);
   }

   protected void removeWriteCallback(@Nullable BluetoothGattCharacteristic serverCharacteristic) {
      this.requestHandler.removeValueChangedCallback(serverCharacteristic);
   }

   protected void removeWriteCallback(@Nullable BluetoothGattDescriptor serverDescriptor) {
      this.requestHandler.removeValueChangedCallback(serverDescriptor);
   }

   @NonNull
   protected WaitForValueChangedRequest waitForNotification(@Nullable BluetoothGattCharacteristic characteristic) {
      return Request.newWaitForNotificationRequest(characteristic).setRequestHandler(this.requestHandler);
   }

   @NonNull
   protected WaitForValueChangedRequest waitForIndication(@Nullable BluetoothGattCharacteristic characteristic) {
      return Request.newWaitForIndicationRequest(characteristic).setRequestHandler(this.requestHandler);
   }

   @NonNull
   protected WaitForValueChangedRequest waitForWrite(@Nullable BluetoothGattCharacteristic serverCharacteristic) {
      return Request.newWaitForWriteRequest(serverCharacteristic).setRequestHandler(this.requestHandler);
   }

   @NonNull
   protected WaitForValueChangedRequest waitForWrite(@Nullable BluetoothGattDescriptor serverDescriptor) {
      return Request.newWaitForWriteRequest(serverDescriptor).setRequestHandler(this.requestHandler);
   }

   @NonNull
   protected ConditionalWaitRequest<Void> waitIf(@NonNull ConditionalWaitRequest.Condition<Void> condition) {
      return Request.newConditionalWaitRequest(condition, (Object)null).setRequestHandler(this.requestHandler);
   }

   @NonNull
   protected <T> ConditionalWaitRequest<T> waitIf(@Nullable T parameter, @NonNull ConditionalWaitRequest.Condition<T> condition) {
      return Request.newConditionalWaitRequest(condition, parameter).setRequestHandler(this.requestHandler);
   }

   @NonNull
   protected ConditionalWaitRequest<Void> waitUntil(@NonNull ConditionalWaitRequest.Condition<Void> condition) {
      return this.waitIf(condition).negate();
   }

   @NonNull
   protected <T> ConditionalWaitRequest<T> waitUntil(@Nullable T parameter, @NonNull ConditionalWaitRequest.Condition<T> condition) {
      return this.waitIf(parameter, condition).negate();
   }

   @NonNull
   protected ConditionalWaitRequest<BluetoothGattCharacteristic> waitUntilNotificationsEnabled(@Nullable BluetoothGattCharacteristic serverCharacteristic) {
      return this.waitUntil(serverCharacteristic, (characteristic) -> {
         if (characteristic == null) {
            return false;
         } else {
            BluetoothGattDescriptor cccd = characteristic.getDescriptor(CLIENT_CHARACTERISTIC_CONFIG_DESCRIPTOR_UUID);
            if (cccd == null) {
               return false;
            } else {
               byte[] value = this.requestHandler.getDescriptorValue(cccd);
               return value != null && value.length == 2 && (value[0] & 1) == 1;
            }
         }
      });
   }

   @NonNull
   protected ConditionalWaitRequest<BluetoothGattCharacteristic> waitUntilIndicationsEnabled(@Nullable BluetoothGattCharacteristic serverCharacteristic) {
      return this.waitUntil(serverCharacteristic, (characteristic) -> {
         if (characteristic == null) {
            return false;
         } else {
            BluetoothGattDescriptor cccd = characteristic.getDescriptor(CLIENT_CHARACTERISTIC_CONFIG_DESCRIPTOR_UUID);
            if (cccd == null) {
               return false;
            } else {
               byte[] value = this.requestHandler.getDescriptorValue(cccd);
               return value != null && value.length == 2 && (value[0] & 2) == 2;
            }
         }
      });
   }

   @NonNull
   protected WaitForReadRequest waitForRead(@Nullable BluetoothGattCharacteristic serverCharacteristic) {
      return Request.newWaitForReadRequest(serverCharacteristic).setRequestHandler(this.requestHandler);
   }

   @NonNull
   protected WaitForReadRequest waitForRead(@Nullable BluetoothGattCharacteristic serverCharacteristic, @Nullable byte[] data) {
      return Request.newWaitForReadRequest(serverCharacteristic, data).setRequestHandler(this.requestHandler);
   }

   @NonNull
   protected WaitForReadRequest waitForRead(@Nullable BluetoothGattCharacteristic serverCharacteristic, @Nullable byte[] data, int offset, int length) {
      return Request.newWaitForReadRequest(serverCharacteristic, data, offset, length).setRequestHandler(this.requestHandler);
   }

   @NonNull
   protected WaitForReadRequest waitForRead(@Nullable BluetoothGattDescriptor serverDescriptor) {
      return Request.newWaitForReadRequest(serverDescriptor).setRequestHandler(this.requestHandler);
   }

   @NonNull
   protected WaitForReadRequest waitForRead(@Nullable BluetoothGattDescriptor serverDescriptor, @Nullable byte[] data) {
      return Request.newWaitForReadRequest(serverDescriptor, data).setRequestHandler(this.requestHandler);
   }

   @NonNull
   protected WaitForReadRequest waitForRead(@Nullable BluetoothGattDescriptor serverDescriptor, @Nullable byte[] data, int offset, int length) {
      return Request.newWaitForReadRequest(serverDescriptor, data, offset, length).setRequestHandler(this.requestHandler);
   }

   protected void setCharacteristicValue(@Nullable BluetoothGattCharacteristic serverCharacteristic, @Nullable DataProvider provider) {
      this.requestHandler.setCharacteristicValue(serverCharacteristic, provider);
   }

   @NonNull
   protected SetValueRequest setCharacteristicValue(@Nullable BluetoothGattCharacteristic serverCharacteristic, @Nullable Data data) {
      return Request.newSetValueRequest(serverCharacteristic, data != null ? data.getValue() : null).setRequestHandler(this.requestHandler);
   }

   @NonNull
   protected SetValueRequest setCharacteristicValue(@Nullable BluetoothGattCharacteristic serverCharacteristic, @Nullable byte[] data) {
      return Request.newSetValueRequest(serverCharacteristic, data).setRequestHandler(this.requestHandler);
   }

   @NonNull
   protected SetValueRequest setCharacteristicValue(@Nullable BluetoothGattCharacteristic serverCharacteristic, @Nullable byte[] data, int offset, int length) {
      return Request.newSetValueRequest(serverCharacteristic, data, offset, length).setRequestHandler(this.requestHandler);
   }

   protected void setDescriptorValue(@Nullable BluetoothGattDescriptor serverDescriptor, @Nullable DataProvider provider) {
      this.requestHandler.setDescriptorValue(serverDescriptor, provider);
   }

   @NonNull
   protected SetValueRequest setDescriptorValue(@Nullable BluetoothGattDescriptor serverDescriptor, @Nullable Data data) {
      return Request.newSetValueRequest(serverDescriptor, data != null ? data.getValue() : null).setRequestHandler(this.requestHandler);
   }

   @NonNull
   protected SetValueRequest setDescriptorValue(@Nullable BluetoothGattDescriptor serverDescriptor, @Nullable byte[] data) {
      return Request.newSetValueRequest(serverDescriptor, data).setRequestHandler(this.requestHandler);
   }

   @NonNull
   protected SetValueRequest setDescriptorValue(@Nullable BluetoothGattDescriptor serverDescriptor, @Nullable byte[] data, int offset, int length) {
      return Request.newSetValueRequest(serverDescriptor, data, offset, length).setRequestHandler(this.requestHandler);
   }

   @NonNull
   protected WriteRequest enableNotifications(@Nullable BluetoothGattCharacteristic characteristic) {
      return Request.newEnableNotificationsRequest(characteristic).setRequestHandler(this.requestHandler);
   }

   @NonNull
   protected WriteRequest disableNotifications(@Nullable BluetoothGattCharacteristic characteristic) {
      return Request.newDisableNotificationsRequest(characteristic).setRequestHandler(this.requestHandler);
   }

   @NonNull
   protected WriteRequest enableIndications(@Nullable BluetoothGattCharacteristic characteristic) {
      return Request.newEnableIndicationsRequest(characteristic).setRequestHandler(this.requestHandler);
   }

   @NonNull
   protected WriteRequest disableIndications(@Nullable BluetoothGattCharacteristic characteristic) {
      return Request.newDisableIndicationsRequest(characteristic).setRequestHandler(this.requestHandler);
   }

   @NonNull
   protected ReadRequest readCharacteristic(@Nullable BluetoothGattCharacteristic characteristic) {
      return Request.newReadRequest(characteristic).setRequestHandler(this.requestHandler);
   }

   @NonNull
   protected WriteRequest writeCharacteristic(@Nullable BluetoothGattCharacteristic characteristic, @Nullable Data data, int writeType) {
      return Request.newWriteRequest(characteristic, data != null ? data.getValue() : null, writeType).setRequestHandler(this.requestHandler);
   }

   @NonNull
   protected WriteRequest writeCharacteristic(@Nullable BluetoothGattCharacteristic characteristic, @Nullable byte[] data, int writeType) {
      return Request.newWriteRequest(characteristic, data, writeType).setRequestHandler(this.requestHandler);
   }

   @NonNull
   protected WriteRequest writeCharacteristic(@Nullable BluetoothGattCharacteristic characteristic, @Nullable byte[] data, int offset, int length, int writeType) {
      return Request.newWriteRequest(characteristic, data, offset, length, writeType).setRequestHandler(this.requestHandler);
   }

   /** @deprecated */
   @Deprecated
   @NonNull
   protected WriteRequest writeCharacteristic(@Nullable BluetoothGattCharacteristic characteristic, @Nullable Data data) {
      return Request.newWriteRequest(characteristic, data != null ? data.getValue() : null).setRequestHandler(this.requestHandler);
   }

   /** @deprecated */
   @Deprecated
   @NonNull
   protected WriteRequest writeCharacteristic(@Nullable BluetoothGattCharacteristic characteristic, @Nullable byte[] data) {
      return Request.newWriteRequest(characteristic, data).setRequestHandler(this.requestHandler);
   }

   /** @deprecated */
   @Deprecated
   @NonNull
   protected WriteRequest writeCharacteristic(@Nullable BluetoothGattCharacteristic characteristic, @Nullable byte[] data, int offset, int length) {
      return Request.newWriteRequest(characteristic, data, offset, length).setRequestHandler(this.requestHandler);
   }

   @NonNull
   protected ReadRequest readDescriptor(@Nullable BluetoothGattDescriptor descriptor) {
      return Request.newReadRequest(descriptor).setRequestHandler(this.requestHandler);
   }

   @NonNull
   protected WriteRequest writeDescriptor(@Nullable BluetoothGattDescriptor descriptor, @Nullable Data data) {
      return Request.newWriteRequest(descriptor, data != null ? data.getValue() : null).setRequestHandler(this.requestHandler);
   }

   @NonNull
   protected WriteRequest writeDescriptor(@Nullable BluetoothGattDescriptor descriptor, @Nullable byte[] data) {
      return Request.newWriteRequest(descriptor, data).setRequestHandler(this.requestHandler);
   }

   @NonNull
   protected WriteRequest writeDescriptor(@Nullable BluetoothGattDescriptor descriptor, @Nullable byte[] data, int offset, int length) {
      return Request.newWriteRequest(descriptor, data, offset, length).setRequestHandler(this.requestHandler);
   }

   @NonNull
   protected WriteRequest sendNotification(@Nullable BluetoothGattCharacteristic serverCharacteristic, @Nullable Data data) {
      return Request.newNotificationRequest(serverCharacteristic, data != null ? data.getValue() : null).setRequestHandler(this.requestHandler);
   }

   @NonNull
   protected WriteRequest sendNotification(@Nullable BluetoothGattCharacteristic serverCharacteristic, @Nullable byte[] data) {
      return Request.newNotificationRequest(serverCharacteristic, data).setRequestHandler(this.requestHandler);
   }

   @NonNull
   protected WriteRequest sendNotification(@Nullable BluetoothGattCharacteristic serverCharacteristic, @Nullable byte[] data, int offset, int length) {
      return Request.newNotificationRequest(serverCharacteristic, data, offset, length).setRequestHandler(this.requestHandler);
   }

   @NonNull
   protected WriteRequest sendIndication(@Nullable BluetoothGattCharacteristic serverCharacteristic, @Nullable Data data) {
      return Request.newIndicationRequest(serverCharacteristic, data != null ? data.getValue() : null).setRequestHandler(this.requestHandler);
   }

   @NonNull
   protected WriteRequest sendIndication(@Nullable BluetoothGattCharacteristic serverCharacteristic, @Nullable byte[] data) {
      return Request.newIndicationRequest(serverCharacteristic, data).setRequestHandler(this.requestHandler);
   }

   @NonNull
   protected WriteRequest sendIndication(@Nullable BluetoothGattCharacteristic serverCharacteristic, @Nullable byte[] data, int offset, int length) {
      return Request.newIndicationRequest(serverCharacteristic, data, offset, length).setRequestHandler(this.requestHandler);
   }

   @NonNull
   protected RequestQueue beginAtomicRequestQueue() {
      return (new RequestQueue()).setRequestHandler(this.requestHandler);
   }

   @NonNull
   protected ReliableWriteRequest beginReliableWrite() {
      return Request.newReliableWriteRequest().setRequestHandler(this.requestHandler);
   }

   protected final boolean isReliableWriteInProgress() {
      return this.requestHandler.isReliableWriteInProgress();
   }

   /** @deprecated */
   @Deprecated
   protected void readBatteryLevel() {
      Request.newReadBatteryLevelRequest().setRequestHandler(this.requestHandler).with(this.requestHandler.getBatteryLevelCallback()).enqueue();
   }

   /** @deprecated */
   @Deprecated
   protected void enableBatteryLevelNotifications() {
      Request.newEnableBatteryLevelNotificationsRequest().setRequestHandler(this.requestHandler).before((device) -> {
         this.requestHandler.setBatteryLevelNotificationCallback();
      }).done((device) -> {
         this.log(4, "Battery Level notifications enabled");
      }).enqueue();
   }

   /** @deprecated */
   @Deprecated
   protected void disableBatteryLevelNotifications() {
      Request.newDisableBatteryLevelNotificationsRequest().setRequestHandler(this.requestHandler).done((device) -> {
         this.log(4, "Battery Level notifications disabled");
      }).enqueue();
   }

   protected MtuRequest requestMtu(@IntRange(from = 23L,to = 517L) int mtu) {
      return Request.newMtuRequest(mtu).setRequestHandler(this.requestHandler);
   }

   @IntRange(
      from = 23L,
      to = 517L
   )
   protected int getMtu() {
      return this.requestHandler.getMtu();
   }

   protected void overrideMtu(@IntRange(from = 23L,to = 517L) int mtu) {
      this.requestHandler.overrideMtu(mtu);
   }

   @RequiresApi(
      api = 21
   )
   protected ConnectionPriorityRequest requestConnectionPriority(int priority) {
      return Request.newConnectionPriorityRequest(priority).setRequestHandler(this.requestHandler);
   }

   @RequiresApi(
      api = 26
   )
   protected void setConnectionParametersListener(@Nullable ConnectionParametersUpdatedCallback callback) {
      this.requestHandler.setConnectionParametersListener(callback);
   }

   protected PhyRequest setPreferredPhy(int txPhy, int rxPhy, int phyOptions) {
      return Request.newSetPreferredPhyRequest(txPhy, rxPhy, phyOptions).setRequestHandler(this.requestHandler);
   }

   protected PhyRequest readPhy() {
      return Request.newReadPhyRequest().setRequestHandler(this.requestHandler);
   }

   protected ReadRssiRequest readRssi() {
      return Request.newReadRssiRequest().setRequestHandler(this.requestHandler);
   }

   protected Request refreshDeviceCache() {
      return Request.newRefreshCacheRequest().setRequestHandler(this.requestHandler);
   }

   protected SleepRequest sleep(@IntRange(from = 0L) long delay) {
      return Request.newSleepRequest(delay).setRequestHandler(this.requestHandler);
   }

   /** @deprecated */
   @Deprecated
   protected final void enqueue(@NonNull Request request) {
      this.requestHandler.enqueue(request);
   }

   protected final void cancelQueue() {
      this.requestHandler.cancelQueue();
   }

   protected abstract static class BleManagerGattCallback extends BleManagerHandler {
   }
}
