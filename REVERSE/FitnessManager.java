package com.android.chileaf.fitness;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.content.Context;
import android.os.Build.VERSION;
import android.text.TextUtils;
import androidx.annotation.NonNull;
import com.android.chileaf.fitness.common.battery.BatteryLevelDataCallback;
import com.android.chileaf.util.HexUtil;
import com.android.chileaf.util.LogUtil;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import no.nordicsemi.android.ble.BleManager;
import no.nordicsemi.android.ble.LegacyBleManager;
import no.nordicsemi.android.ble.callback.DataReceivedCallback;
import no.nordicsemi.android.ble.callback.RssiCallback;
import no.nordicsemi.android.ble.data.Data;
import no.nordicsemi.android.ble.utils.ParserUtils;

public abstract class FitnessManager<T extends FitnessManagerCallbacks> extends LegacyBleManager<T> {
   protected static final UUID SERVICE_UUID = UUID.fromString("AAE28F00-71B5-42A1-8C3C-F9CF6AC969D0");
   protected static final UUID RX_CHAR_UUID = UUID.fromString("AAE28F01-71B5-42A1-8C3C-F9CF6AC969D0");
   protected static final UUID TX_CHAR_UUID = UUID.fromString("AAE28F02-71B5-42A1-8C3C-F9CF6AC969D0");
   protected static final String SPEC_CHAR_UUID = "AAE21541-71B5-42A1-8C3C-F9CF6AC969D0";
   protected static final String CUSTOM_CHAR_UUID = "AAE21542-71B5-42A1-8C3C-F9CF6AC969D0";
   private static final UUID BATTERY_SERVICE_UUID = UUID.fromString("0000180F-0000-1000-8000-00805f9b34fb");
   private static final UUID BATTERY_LEVEL_CHARACTERISTIC_UUID = UUID.fromString("00002A19-0000-1000-8000-00805f9b34fb");
   private static final UUID PROFILE_SERVICE_UUID = UUID.fromString("0000180A-0000-1000-8000-00805f9b34fb");
   private static final UUID PROFILE_SYSTEM_CHARACTERISTIC_UUID = UUID.fromString("00002A23-0000-1000-8000-00805f9b34fb");
   private static final UUID PROFILE_MODEL_CHARACTERISTIC_UUID = UUID.fromString("00002A24-0000-1000-8000-00805f9b34fb");
   private static final UUID PROFILE_SERIAL_CHARACTERISTIC_UUID = UUID.fromString("00002A25-0000-1000-8000-00805f9b34fb");
   private static final UUID PROFILE_FIRMWARE_CHARACTERISTIC_UUID = UUID.fromString("00002A26-0000-1000-8000-00805f9b34fb");
   private static final UUID PROFILE_HARDWARE_CHARACTERISTIC_UUID = UUID.fromString("00002A27-0000-1000-8000-00805f9b34fb");
   private static final UUID PROFILE_SOFTWARE_CHARACTERISTIC_UUID = UUID.fromString("00002A28-0000-1000-8000-00805f9b34fb");
   private static final UUID PROFILE_VENDOR_CHARACTERISTIC_UUID = UUID.fromString("00002A29-0000-1000-8000-00805f9b34fb");
   protected boolean isContainCL833 = false;
   protected BluetoothGattCharacteristic mRXCharacteristic;
   protected BluetoothGattCharacteristic mTXCharacteristic;
   protected BluetoothGattCharacteristic mCustomRxCharacteristic;
   private BluetoothGattCharacteristic mBatteryLevelCharacteristic;
   private BluetoothGattCharacteristic mProfileSystemCharacteristic;
   private BluetoothGattCharacteristic mProfileModelCharacteristic;
   private BluetoothGattCharacteristic mProfileSerialCharacteristic;
   private BluetoothGattCharacteristic mProfileFirmwareCharacteristic;
   private BluetoothGattCharacteristic mProfileHardwareCharacteristic;
   private BluetoothGattCharacteristic mProfileSoftwareCharacteristic;
   private BluetoothGattCharacteristic mProfileVendorCharacteristic;
   private Integer mRssi;
   private Integer mBatteryLevel;
   private String mSystemId;
   private String mModelName;
   private String mSerialNumber;
   private String mFirmwareVersion;
   private String mHardwareVersion;
   private String mSoftwareVersion;
   private String mVendorName;
   private final RssiCallback mRssiCallback = (device, rssi) -> {
      ((FitnessManagerCallbacks)this.mCallbacks).onRssiRead(device, rssi);
      this.mRssi = rssi;
   };
   private final DataReceivedCallback mSystemCallBack = (device, data) -> {
      if (data.size() > 0 && data.getValue() != null) {
         String systemId = HexUtil.byteArrayToString(data.getValue());
         if (!TextUtils.isEmpty(systemId)) {
            this.log(4, "System Id: " + systemId);
            ((FitnessManagerCallbacks)this.mCallbacks).onSystemId(device, systemId);
            this.mSystemId = systemId;
         }
      }

   };
   private final DataReceivedCallback mModelCallBack = (device, data) -> {
      if (data.size() > 0 && data.getValue() != null) {
         String modelName = HexUtil.byteArrayToString(data.getValue());
         if (!TextUtils.isEmpty(modelName)) {
            this.log(4, "Model Name: " + modelName);
            this.checkModel(modelName, this.isContainCL833);
            ((FitnessManagerCallbacks)this.mCallbacks).onModelName(device, modelName);
            this.mModelName = modelName;
         }
      }

   };
   private final DataReceivedCallback mSerialNumberCallBack = (device, data) -> {
      if (data.size() > 0 && data.getValue() != null) {
         String serialNumber = HexUtil.byteArrayToString(data.getValue());
         if (!TextUtils.isEmpty(serialNumber)) {
            this.log(4, "Serial Number: " + serialNumber);
            ((FitnessManagerCallbacks)this.mCallbacks).onSerialNumber(device, serialNumber);
            this.mSerialNumber = serialNumber;
         }
      }

   };
   private final DataReceivedCallback mFirmwareCallBack = (device, data) -> {
      if (data.size() > 0 && data.getValue() != null) {
         String firmware = HexUtil.byteArrayToString(data.getValue());
         if (!TextUtils.isEmpty(firmware)) {
            this.log(4, "Firmware Version: " + firmware);
            ((FitnessManagerCallbacks)this.mCallbacks).onFirmwareVersion(device, firmware);
            this.mFirmwareVersion = firmware;
         }
      }

   };
   private final DataReceivedCallback mHardwareCallBack = (device, data) -> {
      if (data.size() > 0 && data.getValue() != null) {
         String value = HexUtil.byteArrayToString(data.getValue());
         if (!TextUtils.isEmpty(value)) {
            this.log(4, "Hardware Version: " + value);
            ((FitnessManagerCallbacks)this.mCallbacks).onHardwareVersion(device, value);
            this.mHardwareVersion = value;
         }
      }

   };
   private final DataReceivedCallback mSoftwareCallBack = (device, data) -> {
      if (data.size() > 0 && data.getValue() != null) {
         String value = HexUtil.byteArrayToString(data.getValue());
         if (!TextUtils.isEmpty(value)) {
            this.log(4, "Software Version: " + value);
            ((FitnessManagerCallbacks)this.mCallbacks).onSoftwareVersion(device, value);
            this.mSoftwareVersion = value;
         }
      }

   };
   private final DataReceivedCallback mVendorCallBack = (device, data) -> {
      if (data.size() > 0 && data.getValue() != null) {
         String value = HexUtil.byteArrayToString(data.getValue());
         if (!TextUtils.isEmpty(value)) {
            this.log(4, "Vendor Name: " + value);
            ((FitnessManagerCallbacks)this.mCallbacks).onVendorName(device, value);
            this.mVendorName = value;
         }
      }

   };
   private final DataReceivedCallback mBatteryLevelDataCallback = new BatteryLevelDataCallback() {
      public void onBatteryLevelChanged(@NonNull final BluetoothDevice device, final int batteryLevel) {
         FitnessManager.this.log(4, "Battery Level received: " + batteryLevel + "%");
         ((FitnessManagerCallbacks)FitnessManager.this.mCallbacks).onBatteryLevelChanged(device, batteryLevel);
         FitnessManager.this.mBatteryLevel = batteryLevel;
         if (FitnessManager.this.isReadRssi()) {
            FitnessManager.this.readRssi().with(FitnessManager.this.mRssiCallback).enqueue();
         }

      }

      public void onInvalidDataReceived(@NonNull final BluetoothDevice device, @NonNull final Data data) {
         FitnessManager.this.log(5, "Invalid Battery Level data received: " + data);
      }
   };

   public FitnessManager(@NonNull final Context context) {
      super(context);
   }

   public void setManagerCallbacks(@NonNull final T callbacks) {
      super.setGattCallbacks(callbacks);
      this.mCallbacks = callbacks;
   }

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

   public void setDebug(boolean debug) {
      LogUtil.setDebug(debug);
   }

   public void log(final int priority, @NonNull final String message) {
      LogUtil.log(6, priority, message);
   }

   protected byte checkSum(final byte[] data) {
      int result = 0;
      byte[] var3 = data;
      int var4 = data.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         byte item = var3[var5];
         result += item;
      }

      result = -result;
      result ^= 58;
      return (byte)(result & 255);
   }

   public Integer getRssi() {
      return this.mRssi;
   }

   public Integer getBatteryLevel() {
      return this.mBatteryLevel;
   }

   public String getSystemId() {
      return this.mSystemId;
   }

   public String getModelName() {
      return this.mModelName;
   }

   public String getSerialNumber() {
      return this.mSerialNumber;
   }

   public String getFirmwareVersion() {
      return this.mFirmwareVersion;
   }

   public String getHardwareVersion() {
      return this.mHardwareVersion;
   }

   public String getSoftwareVersion() {
      return this.mSoftwareVersion;
   }

   public String getVendorName() {
      return this.mVendorName;
   }

   public boolean isReadRssi() {
      return true;
   }

   public abstract void checkModel(String modelName, boolean isCL833);

   public void readProfileCharacteristic() {
      if (this.isConnected()) {
         this.readCharacteristic(this.mProfileSystemCharacteristic).with(this.mSystemCallBack).fail((device, status) -> {
            this.log(5, "Profile system characteristic not found");
         }).enqueue();
         this.readCharacteristic(this.mProfileModelCharacteristic).with(this.mModelCallBack).fail((device, status) -> {
            this.log(5, "Profile model characteristic not found");
         }).enqueue();
         this.readCharacteristic(this.mProfileSerialCharacteristic).with(this.mSerialNumberCallBack).fail((device, status) -> {
            this.log(5, "Profile serial characteristic not found");
         }).enqueue();
         this.readCharacteristic(this.mProfileFirmwareCharacteristic).with(this.mFirmwareCallBack).fail((device, status) -> {
            this.log(5, "Profile firmware characteristic not found");
         }).enqueue();
         this.readCharacteristic(this.mProfileHardwareCharacteristic).with(this.mHardwareCallBack).fail((device, status) -> {
            this.log(5, "Profile hardware characteristic not found");
         }).enqueue();
         this.readCharacteristic(this.mProfileSoftwareCharacteristic).with(this.mSoftwareCallBack).fail((device, status) -> {
            this.log(5, "Profile software characteristic not found");
         }).enqueue();
         this.readCharacteristic(this.mProfileVendorCharacteristic).with(this.mVendorCallBack).fail((device, status) -> {
            this.log(5, "Profile vendor characteristic not found");
         }).enqueue();
      }

   }

   public void readBatteryLevelCharacteristic() {
      if (this.isConnected()) {
         this.readCharacteristic(this.mBatteryLevelCharacteristic).with(this.mBatteryLevelDataCallback).fail((device, status) -> {
            this.log(5, "Battery Level characteristic not found");
         }).enqueue();
      }

   }

   public void enableBatteryLevelCharacteristicNotifications() {
      if (this.isConnected()) {
         this.setNotificationCallback(this.mBatteryLevelCharacteristic).with(this.mBatteryLevelDataCallback);
         this.enableNotifications(this.mBatteryLevelCharacteristic).done((device) -> {
            this.log(3, "Battery Level notifications enabled");
         }).fail((device, status) -> {
            this.log(5, "Battery Level characteristic not found");
         }).enqueue();
      }

   }

   public void disableBatteryLevelCharacteristicNotifications() {
      if (this.isConnected()) {
         this.disableNotifications(this.mBatteryLevelCharacteristic).done((device) -> {
            this.log(3, "Battery Level notifications disabled");
         }).enqueue();
      }

   }

   public void connectDevice(BluetoothDevice device) {
      this.connect(device).useAutoConnect(false).enqueue();
   }

   public void disconnectDevice() {
      this.disconnect().enqueue();
   }

   protected void writeTxCharacteristic(final byte[] command) {
      if (this.isConnected() && this.mTXCharacteristic != null) {
         this.writeCharacteristic(this.mTXCharacteristic, command).with((device, data) -> {
            this.log(2, "Send:" + ParserUtils.parse(data.getValue()));
         }).done((device) -> {
            this.log(3, "Tx writeCharacteristic success");
         }).fail((device, status) -> {
            this.log(5, "Tx writeCharacteristic failure");
         }).enqueue();
      }

   }

   protected void sendCommand(final byte[] bytes) {
      this.sendCommand(bytes, false);
   }

   protected void sendCommand(final byte[] bytes, boolean isCheckSum) {
      byte[] command;
      if (isCheckSum) {
         byte check = this.checkSum(bytes);
         command = HexUtil.append(bytes, check);
      } else {
         command = bytes;
      }

      this.writeTxCharacteristic(command);
   }

   protected abstract class FitnessManagerGattCallback extends BleManager.BleManagerGattCallback {
      protected void initialize() {
         if (VERSION.SDK_INT >= 21) {
            FitnessManager.this.requestConnectionPriority(1).enqueue();
         }

         FitnessManager.this.readProfileCharacteristic();
         FitnessManager.this.readBatteryLevelCharacteristic();
         FitnessManager.this.enableBatteryLevelCharacteristicNotifications();
      }

      public boolean isRequiredServiceSupported(@NonNull final BluetoothGatt gatt) {
         BluetoothGattService service = gatt.getService(FitnessManager.SERVICE_UUID);
         if (service != null) {
            FitnessManager.this.mRXCharacteristic = service.getCharacteristic(FitnessManager.RX_CHAR_UUID);
            FitnessManager.this.mTXCharacteristic = service.getCharacteristic(FitnessManager.TX_CHAR_UUID);
            List<BluetoothGattCharacteristic> characteristics = service.getCharacteristics();
            Iterator var4 = characteristics.iterator();

            while(var4.hasNext()) {
               BluetoothGattCharacteristic characteristic = (BluetoothGattCharacteristic)var4.next();
               if (characteristic != null && characteristic.getUuid() != null && characteristic.getUuid().toString().equalsIgnoreCase("AAE21541-71B5-42A1-8C3C-F9CF6AC969D0")) {
                  FitnessManager.this.mCustomRxCharacteristic = service.getCharacteristic(UUID.fromString("AAE21542-71B5-42A1-8C3C-F9CF6AC969D0"));
                  FitnessManager.this.isContainCL833 = true;
               }
            }
         }

         boolean writeRequest = false;
         boolean writeCommand = false;
         if (FitnessManager.this.mTXCharacteristic != null) {
            int txProperties = FitnessManager.this.mTXCharacteristic.getProperties();
            writeRequest = (txProperties & 8) > 0;
            writeCommand = (txProperties & 4) > 0;
            if (writeRequest) {
               FitnessManager.this.mTXCharacteristic.setWriteType(2);
               FitnessManager.this.log(3, "TXCharacteristic notifications WRITE_TYPE_DEFAULT");
            }
         }

         return FitnessManager.this.mRXCharacteristic != null && FitnessManager.this.mTXCharacteristic != null && (writeCommand || writeRequest);
      }

      protected boolean isOptionalServiceSupported(@NonNull final BluetoothGatt gatt) {
         BluetoothGattService batteryService = gatt.getService(FitnessManager.BATTERY_SERVICE_UUID);
         if (batteryService != null) {
            FitnessManager.this.mBatteryLevelCharacteristic = batteryService.getCharacteristic(FitnessManager.BATTERY_LEVEL_CHARACTERISTIC_UUID);
         }

         boolean isBatteryService = FitnessManager.this.mBatteryLevelCharacteristic != null;
         BluetoothGattService profileService = gatt.getService(FitnessManager.PROFILE_SERVICE_UUID);
         if (profileService != null) {
            FitnessManager.this.mProfileSystemCharacteristic = profileService.getCharacteristic(FitnessManager.PROFILE_SYSTEM_CHARACTERISTIC_UUID);
            FitnessManager.this.mProfileModelCharacteristic = profileService.getCharacteristic(FitnessManager.PROFILE_MODEL_CHARACTERISTIC_UUID);
            FitnessManager.this.mProfileSerialCharacteristic = profileService.getCharacteristic(FitnessManager.PROFILE_SERIAL_CHARACTERISTIC_UUID);
            FitnessManager.this.mProfileFirmwareCharacteristic = profileService.getCharacteristic(FitnessManager.PROFILE_FIRMWARE_CHARACTERISTIC_UUID);
            FitnessManager.this.mProfileHardwareCharacteristic = profileService.getCharacteristic(FitnessManager.PROFILE_HARDWARE_CHARACTERISTIC_UUID);
            FitnessManager.this.mProfileSoftwareCharacteristic = profileService.getCharacteristic(FitnessManager.PROFILE_SOFTWARE_CHARACTERISTIC_UUID);
            FitnessManager.this.mProfileVendorCharacteristic = profileService.getCharacteristic(FitnessManager.PROFILE_VENDOR_CHARACTERISTIC_UUID);
         }

         boolean isProfileService = FitnessManager.this.mProfileSystemCharacteristic != null && FitnessManager.this.mProfileModelCharacteristic != null && FitnessManager.this.mProfileSerialCharacteristic != null && FitnessManager.this.mProfileFirmwareCharacteristic != null && FitnessManager.this.mProfileHardwareCharacteristic != null && FitnessManager.this.mProfileSoftwareCharacteristic != null && FitnessManager.this.mProfileVendorCharacteristic != null;
         return isBatteryService && isProfileService;
      }

      protected void onDeviceDisconnected() {
         FitnessManager.this.mBatteryLevelCharacteristic = null;
         FitnessManager.this.mCustomRxCharacteristic = null;
         FitnessManager.this.mRXCharacteristic = null;
         FitnessManager.this.mTXCharacteristic = null;
         FitnessManager.this.mBatteryLevel = null;
         FitnessManager.this.isContainCL833 = false;
      }

      protected void onServicesInvalidated() {
         FitnessManager.this.mBatteryLevelCharacteristic = null;
         FitnessManager.this.mCustomRxCharacteristic = null;
         FitnessManager.this.mRXCharacteristic = null;
         FitnessManager.this.mTXCharacteristic = null;
         FitnessManager.this.mBatteryLevel = null;
         FitnessManager.this.isContainCL833 = false;
      }
   }
}
