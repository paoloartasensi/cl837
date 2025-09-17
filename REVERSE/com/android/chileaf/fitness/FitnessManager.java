/*     */ package com.android.chileaf.fitness;
/*     */ 
/*     */ import android.bluetooth.BluetoothDevice;
/*     */ import android.bluetooth.BluetoothGatt;
/*     */ import android.bluetooth.BluetoothGattCharacteristic;
/*     */ import android.bluetooth.BluetoothGattService;
/*     */ import android.content.Context;
/*     */ import android.os.Build;
/*     */ import android.text.TextUtils;
/*     */ import androidx.annotation.NonNull;
/*     */ import com.android.chileaf.fitness.common.battery.BatteryLevelDataCallback;
/*     */ import com.android.chileaf.util.HexUtil;
/*     */ import com.android.chileaf.util.LogUtil;
/*     */ import java.util.List;
/*     */ import java.util.UUID;
/*     */ import no.nordicsemi.android.ble.BleManager;
/*     */ import no.nordicsemi.android.ble.BleManagerCallbacks;
/*     */ import no.nordicsemi.android.ble.ConnectionPriorityRequest;
/*     */ import no.nordicsemi.android.ble.LegacyBleManager;
/*     */ import no.nordicsemi.android.ble.ReadRssiRequest;
/*     */ import no.nordicsemi.android.ble.callback.DataReceivedCallback;
/*     */ import no.nordicsemi.android.ble.callback.RssiCallback;
/*     */ import no.nordicsemi.android.ble.data.Data;
/*     */ import no.nordicsemi.android.ble.utils.ParserUtils;
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class FitnessManager<T extends FitnessManagerCallbacks>
/*     */   extends LegacyBleManager<T>
/*     */ {
/*  31 */   protected static final UUID SERVICE_UUID = UUID.fromString("AAE28F00-71B5-42A1-8C3C-F9CF6AC969D0");
/*  32 */   protected static final UUID RX_CHAR_UUID = UUID.fromString("AAE28F01-71B5-42A1-8C3C-F9CF6AC969D0");
/*  33 */   protected static final UUID TX_CHAR_UUID = UUID.fromString("AAE28F02-71B5-42A1-8C3C-F9CF6AC969D0");
/*     */   
/*     */   protected static final String SPEC_CHAR_UUID = "AAE21541-71B5-42A1-8C3C-F9CF6AC969D0";
/*     */   
/*     */   protected static final String CUSTOM_CHAR_UUID = "AAE21542-71B5-42A1-8C3C-F9CF6AC969D0";
/*  38 */   private static final UUID BATTERY_SERVICE_UUID = UUID.fromString("0000180F-0000-1000-8000-00805f9b34fb");
/*  39 */   private static final UUID BATTERY_LEVEL_CHARACTERISTIC_UUID = UUID.fromString("00002A19-0000-1000-8000-00805f9b34fb");
/*     */   
/*  41 */   private static final UUID PROFILE_SERVICE_UUID = UUID.fromString("0000180A-0000-1000-8000-00805f9b34fb");
/*  42 */   private static final UUID PROFILE_SYSTEM_CHARACTERISTIC_UUID = UUID.fromString("00002A23-0000-1000-8000-00805f9b34fb");
/*  43 */   private static final UUID PROFILE_MODEL_CHARACTERISTIC_UUID = UUID.fromString("00002A24-0000-1000-8000-00805f9b34fb");
/*  44 */   private static final UUID PROFILE_SERIAL_CHARACTERISTIC_UUID = UUID.fromString("00002A25-0000-1000-8000-00805f9b34fb");
/*  45 */   private static final UUID PROFILE_FIRMWARE_CHARACTERISTIC_UUID = UUID.fromString("00002A26-0000-1000-8000-00805f9b34fb");
/*  46 */   private static final UUID PROFILE_HARDWARE_CHARACTERISTIC_UUID = UUID.fromString("00002A27-0000-1000-8000-00805f9b34fb");
/*  47 */   private static final UUID PROFILE_SOFTWARE_CHARACTERISTIC_UUID = UUID.fromString("00002A28-0000-1000-8000-00805f9b34fb");
/*  48 */   private static final UUID PROFILE_VENDOR_CHARACTERISTIC_UUID = UUID.fromString("00002A29-0000-1000-8000-00805f9b34fb"); protected boolean isContainCL833 = false; protected BluetoothGattCharacteristic mRXCharacteristic; protected BluetoothGattCharacteristic mTXCharacteristic; protected BluetoothGattCharacteristic mCustomRxCharacteristic; private BluetoothGattCharacteristic mBatteryLevelCharacteristic; private BluetoothGattCharacteristic mProfileSystemCharacteristic;
/*     */   private BluetoothGattCharacteristic mProfileModelCharacteristic;
/*     */   private BluetoothGattCharacteristic mProfileSerialCharacteristic;
/*     */   private BluetoothGattCharacteristic mProfileFirmwareCharacteristic;
/*     */   private BluetoothGattCharacteristic mProfileHardwareCharacteristic;
/*     */   private BluetoothGattCharacteristic mProfileSoftwareCharacteristic;
/*     */   private BluetoothGattCharacteristic mProfileVendorCharacteristic;
/*     */   private Integer mRssi;
/*     */   private Integer mBatteryLevel;
/*     */   private String mSystemId;
/*     */   private String mModelName;
/*     */   private String mSerialNumber;
/*     */   private String mFirmwareVersion;
/*     */   private String mHardwareVersion;
/*     */   private String mSoftwareVersion;
/*     */   private String mVendorName;
/*     */   private final RssiCallback mRssiCallback;
/*     */   private final DataReceivedCallback mSystemCallBack;
/*     */   private final DataReceivedCallback mModelCallBack;
/*     */   private final DataReceivedCallback mSerialNumberCallBack;
/*     */   private final DataReceivedCallback mFirmwareCallBack;
/*     */   private final DataReceivedCallback mHardwareCallBack;
/*     */   private final DataReceivedCallback mSoftwareCallBack;
/*     */   private final DataReceivedCallback mVendorCallBack;
/*     */   private final DataReceivedCallback mBatteryLevelDataCallback;
/*     */   
/*  74 */   public FitnessManager(@NonNull Context context) { super(context);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 169 */     this.mRssiCallback = ((device, rssi) -> {
/*     */         ((FitnessManagerCallbacks)this.mCallbacks).onRssiRead(device, rssi);
/*     */         
/*     */         this.mRssi = Integer.valueOf(rssi);
/*     */       });
/* 174 */     this.mSystemCallBack = ((device, data) -> {
/*     */         if (data.size() > 0 && data.getValue() != null) {
/*     */           String systemId = HexUtil.byteArrayToString(data.getValue());
/*     */           
/*     */           if (!TextUtils.isEmpty(systemId)) {
/*     */             log(4, "System Id: " + systemId);
/*     */             ((FitnessManagerCallbacks)this.mCallbacks).onSystemId(device, systemId);
/*     */             this.mSystemId = systemId;
/*     */           } 
/*     */         } 
/*     */       });
/* 185 */     this.mModelCallBack = ((device, data) -> {
/*     */         if (data.size() > 0 && data.getValue() != null) {
/*     */           String modelName = HexUtil.byteArrayToString(data.getValue());
/*     */           
/*     */           if (!TextUtils.isEmpty(modelName)) {
/*     */             log(4, "Model Name: " + modelName);
/*     */             checkModel(modelName, this.isContainCL833);
/*     */             ((FitnessManagerCallbacks)this.mCallbacks).onModelName(device, modelName);
/*     */             this.mModelName = modelName;
/*     */           } 
/*     */         } 
/*     */       });
/* 197 */     this.mSerialNumberCallBack = ((device, data) -> {
/*     */         if (data.size() > 0 && data.getValue() != null) {
/*     */           String serialNumber = HexUtil.byteArrayToString(data.getValue());
/*     */           
/*     */           if (!TextUtils.isEmpty(serialNumber)) {
/*     */             log(4, "Serial Number: " + serialNumber);
/*     */             ((FitnessManagerCallbacks)this.mCallbacks).onSerialNumber(device, serialNumber);
/*     */             this.mSerialNumber = serialNumber;
/*     */           } 
/*     */         } 
/*     */       });
/* 208 */     this.mFirmwareCallBack = ((device, data) -> {
/*     */         if (data.size() > 0 && data.getValue() != null) {
/*     */           String firmware = HexUtil.byteArrayToString(data.getValue());
/*     */           
/*     */           if (!TextUtils.isEmpty(firmware)) {
/*     */             log(4, "Firmware Version: " + firmware);
/*     */             ((FitnessManagerCallbacks)this.mCallbacks).onFirmwareVersion(device, firmware);
/*     */             this.mFirmwareVersion = firmware;
/*     */           } 
/*     */         } 
/*     */       });
/* 219 */     this.mHardwareCallBack = ((device, data) -> {
/*     */         if (data.size() > 0 && data.getValue() != null) {
/*     */           String value = HexUtil.byteArrayToString(data.getValue());
/*     */           
/*     */           if (!TextUtils.isEmpty(value)) {
/*     */             log(4, "Hardware Version: " + value);
/*     */             ((FitnessManagerCallbacks)this.mCallbacks).onHardwareVersion(device, value);
/*     */             this.mHardwareVersion = value;
/*     */           } 
/*     */         } 
/*     */       });
/* 230 */     this.mSoftwareCallBack = ((device, data) -> {
/*     */         if (data.size() > 0 && data.getValue() != null) {
/*     */           String value = HexUtil.byteArrayToString(data.getValue());
/*     */           
/*     */           if (!TextUtils.isEmpty(value)) {
/*     */             log(4, "Software Version: " + value);
/*     */             ((FitnessManagerCallbacks)this.mCallbacks).onSoftwareVersion(device, value);
/*     */             this.mSoftwareVersion = value;
/*     */           } 
/*     */         } 
/*     */       });
/* 241 */     this.mVendorCallBack = ((device, data) -> {
/*     */         if (data.size() > 0 && data.getValue() != null) {
/*     */           String value = HexUtil.byteArrayToString(data.getValue());
/*     */           
/*     */           if (!TextUtils.isEmpty(value)) {
/*     */             log(4, "Vendor Name: " + value);
/*     */             ((FitnessManagerCallbacks)this.mCallbacks).onVendorName(device, value);
/*     */             this.mVendorName = value;
/*     */           } 
/*     */         } 
/*     */       });
/* 252 */     this.mBatteryLevelDataCallback = (DataReceivedCallback)new BatteryLevelDataCallback()
/*     */       {
/*     */         public void onBatteryLevelChanged(@NonNull BluetoothDevice device, int batteryLevel)
/*     */         {
/* 256 */           FitnessManager.this.log(4, "Battery Level received: " + batteryLevel + "%");
/* 257 */           ((FitnessManagerCallbacks)FitnessManager.this.mCallbacks).onBatteryLevelChanged(device, batteryLevel);
/* 258 */           FitnessManager.this.mBatteryLevel = Integer.valueOf(batteryLevel);
/* 259 */           if (FitnessManager.this.isReadRssi()) {
/* 260 */             FitnessManager.this.readRssi().with(FitnessManager.this.mRssiCallback).enqueue();
/*     */           }
/*     */         }
/*     */ 
/*     */         
/*     */         public void onInvalidDataReceived(@NonNull BluetoothDevice device, @NonNull Data data) {
/* 266 */           FitnessManager.this.log(5, "Invalid Battery Level data received: " + data); } }; }
/*     */   public void setManagerCallbacks(@NonNull T callbacks) { setGattCallbacks((BleManagerCallbacks)callbacks); this.mCallbacks = (BleManagerCallbacks)callbacks; }
/*     */   @NonNull protected BleManager.BleManagerGattCallback getGattCallback() { return new BleManager.BleManagerGattCallback() {
/*     */         protected boolean isRequiredServiceSupported(@NonNull BluetoothGatt gatt) { return false; }
/*     */         protected void onServicesInvalidated() {}
/* 271 */       }; } public void setDebug(boolean debug) { LogUtil.setDebug(debug); } public void log(int priority, @NonNull String message) { LogUtil.log(6, priority, message, new Object[0]); } protected byte checkSum(byte[] data) { int result = 0; for (byte item : data) result += item;  result = -result; result ^= 0x3A; return (byte)(result & 0xFF); } public Integer getRssi() { return this.mRssi; } public Integer getBatteryLevel() { return this.mBatteryLevel; } public void readProfileCharacteristic() { if (isConnected())
/* 272 */     { readCharacteristic(this.mProfileSystemCharacteristic)
/* 273 */         .with(this.mSystemCallBack)
/* 274 */         .fail((device, status) -> log(5, "Profile system characteristic not found"))
/* 275 */         .enqueue();
/* 276 */       readCharacteristic(this.mProfileModelCharacteristic)
/* 277 */         .with(this.mModelCallBack)
/* 278 */         .fail((device, status) -> log(5, "Profile model characteristic not found"))
/* 279 */         .enqueue();
/* 280 */       readCharacteristic(this.mProfileSerialCharacteristic)
/* 281 */         .with(this.mSerialNumberCallBack)
/* 282 */         .fail((device, status) -> log(5, "Profile serial characteristic not found"))
/* 283 */         .enqueue();
/* 284 */       readCharacteristic(this.mProfileFirmwareCharacteristic)
/* 285 */         .with(this.mFirmwareCallBack)
/* 286 */         .fail((device, status) -> log(5, "Profile firmware characteristic not found"))
/* 287 */         .enqueue();
/* 288 */       readCharacteristic(this.mProfileHardwareCharacteristic)
/* 289 */         .with(this.mHardwareCallBack)
/* 290 */         .fail((device, status) -> log(5, "Profile hardware characteristic not found"))
/* 291 */         .enqueue();
/* 292 */       readCharacteristic(this.mProfileSoftwareCharacteristic)
/* 293 */         .with(this.mSoftwareCallBack)
/* 294 */         .fail((device, status) -> log(5, "Profile software characteristic not found"))
/* 295 */         .enqueue();
/* 296 */       readCharacteristic(this.mProfileVendorCharacteristic)
/* 297 */         .with(this.mVendorCallBack)
/* 298 */         .fail((device, status) -> log(5, "Profile vendor characteristic not found"))
/* 299 */         .enqueue(); }  }
/*     */   public String getSystemId() { return this.mSystemId; }
/*     */   public String getModelName() { return this.mModelName; }
/*     */   public String getSerialNumber() { return this.mSerialNumber; }
/*     */   public String getFirmwareVersion() { return this.mFirmwareVersion; }
/* 304 */   public String getHardwareVersion() { return this.mHardwareVersion; } public String getSoftwareVersion() { return this.mSoftwareVersion; } public String getVendorName() { return this.mVendorName; } public boolean isReadRssi() { return true; } public void readBatteryLevelCharacteristic() { if (isConnected()) {
/* 305 */       readCharacteristic(this.mBatteryLevelCharacteristic)
/* 306 */         .with(this.mBatteryLevelDataCallback)
/* 307 */         .fail((device, status) -> log(5, "Battery Level characteristic not found"))
/* 308 */         .enqueue();
/*     */     } }
/*     */ 
/*     */   
/*     */   public void enableBatteryLevelCharacteristicNotifications() {
/* 313 */     if (isConnected()) {
/*     */       
/* 315 */       setNotificationCallback(this.mBatteryLevelCharacteristic)
/* 316 */         .with(this.mBatteryLevelDataCallback);
/* 317 */       enableNotifications(this.mBatteryLevelCharacteristic)
/* 318 */         .done(device -> log(3, "Battery Level notifications enabled"))
/* 319 */         .fail((device, status) -> log(5, "Battery Level characteristic not found"))
/* 320 */         .enqueue();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void disableBatteryLevelCharacteristicNotifications() {
/* 328 */     if (isConnected()) {
/* 329 */       disableNotifications(this.mBatteryLevelCharacteristic)
/* 330 */         .done(device -> log(3, "Battery Level notifications disabled"))
/* 331 */         .enqueue();
/*     */     }
/*     */   }
/*     */   
/*     */   protected abstract class FitnessManagerGattCallback
/*     */     extends BleManager.BleManagerGattCallback
/*     */   {
/*     */     protected void initialize() {
/* 339 */       if (Build.VERSION.SDK_INT >= 21) {
/* 340 */         FitnessManager.this.requestConnectionPriority(1).enqueue();
/*     */       }
/* 342 */       FitnessManager.this.readProfileCharacteristic();
/* 343 */       FitnessManager.this.readBatteryLevelCharacteristic();
/* 344 */       FitnessManager.this.enableBatteryLevelCharacteristicNotifications();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isRequiredServiceSupported(@NonNull BluetoothGatt gatt) {
/* 349 */       BluetoothGattService service = gatt.getService(FitnessManager.SERVICE_UUID);
/* 350 */       if (service != null) {
/* 351 */         FitnessManager.this.mRXCharacteristic = service.getCharacteristic(FitnessManager.RX_CHAR_UUID);
/* 352 */         FitnessManager.this.mTXCharacteristic = service.getCharacteristic(FitnessManager.TX_CHAR_UUID);
/* 353 */         List<BluetoothGattCharacteristic> characteristics = service.getCharacteristics();
/* 354 */         for (BluetoothGattCharacteristic characteristic : characteristics) {
/* 355 */           if (characteristic != null && characteristic.getUuid() != null && characteristic.getUuid().toString().equalsIgnoreCase("AAE21541-71B5-42A1-8C3C-F9CF6AC969D0")) {
/* 356 */             FitnessManager.this.mCustomRxCharacteristic = service.getCharacteristic(UUID.fromString("AAE21542-71B5-42A1-8C3C-F9CF6AC969D0"));
/* 357 */             FitnessManager.this.isContainCL833 = true;
/*     */           } 
/*     */         } 
/*     */       } 
/* 361 */       boolean writeRequest = false;
/* 362 */       boolean writeCommand = false;
/* 363 */       if (FitnessManager.this.mTXCharacteristic != null) {
/* 364 */         int txProperties = FitnessManager.this.mTXCharacteristic.getProperties();
/* 365 */         writeRequest = ((txProperties & 0x8) > 0);
/* 366 */         writeCommand = ((txProperties & 0x4) > 0);
/* 367 */         if (writeRequest) {
/* 368 */           FitnessManager.this.mTXCharacteristic.setWriteType(2);
/* 369 */           FitnessManager.this.log(3, "TXCharacteristic notifications WRITE_TYPE_DEFAULT");
/*     */         } 
/*     */       } 
/* 372 */       return (FitnessManager.this.mRXCharacteristic != null && FitnessManager.this.mTXCharacteristic != null && (writeCommand || writeRequest));
/*     */     }
/*     */ 
/*     */     
/*     */     protected boolean isOptionalServiceSupported(@NonNull BluetoothGatt gatt) {
/* 377 */       BluetoothGattService batteryService = gatt.getService(FitnessManager.BATTERY_SERVICE_UUID);
/* 378 */       if (batteryService != null) {
/* 379 */         FitnessManager.this.mBatteryLevelCharacteristic = batteryService.getCharacteristic(FitnessManager.BATTERY_LEVEL_CHARACTERISTIC_UUID);
/*     */       }
/* 381 */       boolean isBatteryService = (FitnessManager.this.mBatteryLevelCharacteristic != null);
/* 382 */       BluetoothGattService profileService = gatt.getService(FitnessManager.PROFILE_SERVICE_UUID);
/* 383 */       if (profileService != null) {
/* 384 */         FitnessManager.this.mProfileSystemCharacteristic = profileService.getCharacteristic(FitnessManager.PROFILE_SYSTEM_CHARACTERISTIC_UUID);
/* 385 */         FitnessManager.this.mProfileModelCharacteristic = profileService.getCharacteristic(FitnessManager.PROFILE_MODEL_CHARACTERISTIC_UUID);
/* 386 */         FitnessManager.this.mProfileSerialCharacteristic = profileService.getCharacteristic(FitnessManager.PROFILE_SERIAL_CHARACTERISTIC_UUID);
/* 387 */         FitnessManager.this.mProfileFirmwareCharacteristic = profileService.getCharacteristic(FitnessManager.PROFILE_FIRMWARE_CHARACTERISTIC_UUID);
/* 388 */         FitnessManager.this.mProfileHardwareCharacteristic = profileService.getCharacteristic(FitnessManager.PROFILE_HARDWARE_CHARACTERISTIC_UUID);
/* 389 */         FitnessManager.this.mProfileSoftwareCharacteristic = profileService.getCharacteristic(FitnessManager.PROFILE_SOFTWARE_CHARACTERISTIC_UUID);
/* 390 */         FitnessManager.this.mProfileVendorCharacteristic = profileService.getCharacteristic(FitnessManager.PROFILE_VENDOR_CHARACTERISTIC_UUID);
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 395 */       boolean isProfileService = (FitnessManager.this.mProfileSystemCharacteristic != null && FitnessManager.this.mProfileModelCharacteristic != null && FitnessManager.this.mProfileSerialCharacteristic != null && FitnessManager.this.mProfileFirmwareCharacteristic != null && FitnessManager.this.mProfileHardwareCharacteristic != null && FitnessManager.this.mProfileSoftwareCharacteristic != null && FitnessManager.this.mProfileVendorCharacteristic != null);
/* 396 */       return (isBatteryService && isProfileService);
/*     */     }
/*     */ 
/*     */     
/*     */     protected void onDeviceDisconnected() {
/* 401 */       FitnessManager.this.mBatteryLevelCharacteristic = null;
/* 402 */       FitnessManager.this.mCustomRxCharacteristic = null;
/* 403 */       FitnessManager.this.mRXCharacteristic = null;
/* 404 */       FitnessManager.this.mTXCharacteristic = null;
/* 405 */       FitnessManager.this.mBatteryLevel = null;
/* 406 */       FitnessManager.this.isContainCL833 = false;
/*     */     }
/*     */ 
/*     */     
/*     */     protected void onServicesInvalidated() {
/* 411 */       FitnessManager.this.mBatteryLevelCharacteristic = null;
/* 412 */       FitnessManager.this.mCustomRxCharacteristic = null;
/* 413 */       FitnessManager.this.mRXCharacteristic = null;
/* 414 */       FitnessManager.this.mTXCharacteristic = null;
/* 415 */       FitnessManager.this.mBatteryLevel = null;
/* 416 */       FitnessManager.this.isContainCL833 = false;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void connectDevice(BluetoothDevice device) {
/* 426 */     connect(device).useAutoConnect(false).enqueue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void disconnectDevice() {
/* 437 */     disconnect().enqueue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void writeTxCharacteristic(byte[] command) {
/* 446 */     if (isConnected() && this.mTXCharacteristic != null) {
/* 447 */       writeCharacteristic(this.mTXCharacteristic, command)
/* 448 */         .with((device, data) -> log(2, "Send:" + ParserUtils.parse(data.getValue())))
/* 449 */         .done(device -> log(3, "Tx writeCharacteristic success"))
/* 450 */         .fail((device, status) -> log(5, "Tx writeCharacteristic failure"))
/* 451 */         .enqueue();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void sendCommand(byte[] bytes) {
/* 461 */     sendCommand(bytes, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void sendCommand(byte[] bytes, boolean isCheckSum) {
/*     */     byte[] command;
/* 471 */     if (isCheckSum) {
/* 472 */       byte check = checkSum(bytes);
/* 473 */       command = HexUtil.append(bytes, check);
/*     */     } else {
/* 475 */       command = bytes;
/*     */     } 
/* 477 */     writeTxCharacteristic(command);
/*     */   }
/*     */   
/*     */   public abstract void checkModel(String paramString, boolean paramBoolean);
/*     */ }


/* Location:              C:\Users\Admin\Downloads\jd-gui-windows-1.6.6\classes.jar!\com\android\chileaf\fitness\FitnessManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */