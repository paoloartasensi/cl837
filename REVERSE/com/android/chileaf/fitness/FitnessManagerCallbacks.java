/*     */ package com.android.chileaf.fitness;
/*     */ 
/*     */ import android.bluetooth.BluetoothDevice;
/*     */ import androidx.annotation.IntRange;
/*     */ import androidx.annotation.NonNull;
/*     */ import com.android.chileaf.fitness.common.battery.BatteryLevelCallback;
/*     */ import com.android.chileaf.fitness.common.profile.ProfileCallback;
/*     */ import no.nordicsemi.android.ble.BleManagerCallbacks;
/*     */ import no.nordicsemi.android.ble.callback.RssiCallback;
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
/*     */ public interface FitnessManagerCallbacks
/*     */   extends BleManagerCallbacks, RssiCallback, BatteryLevelCallback, ProfileCallback
/*     */ {
/*     */   default void onDeviceConnecting(@NonNull BluetoothDevice device) {}
/*     */   
/*     */   void onDeviceConnected(@NonNull BluetoothDevice paramBluetoothDevice);
/*     */   
/*     */   default void onDeviceDisconnecting(@NonNull BluetoothDevice device) {}
/*     */   
/*     */   void onDeviceDisconnected(@NonNull BluetoothDevice paramBluetoothDevice);
/*     */   
/*     */   default void onLinkLossOccurred(@NonNull BluetoothDevice device) {}
/*     */   
/*     */   default void onServicesDiscovered(@NonNull BluetoothDevice device, boolean optionalServicesFound) {}
/*     */   
/*     */   default void onDeviceReady(@NonNull BluetoothDevice device) {}
/*     */   
/*     */   @Deprecated
/*     */   default boolean shouldEnableBatteryLevelNotifications(@NonNull BluetoothDevice device) {
/* 133 */     return false;
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   default void onBatteryValueReceived(@NonNull BluetoothDevice device, @IntRange(from = 0L, to = 100L) int value) {}
/*     */   
/*     */   default void onBondingRequired(@NonNull BluetoothDevice device) {}
/*     */   
/*     */   default void onBonded(@NonNull BluetoothDevice device) {}
/*     */   
/*     */   default void onBondingFailed(@NonNull BluetoothDevice device) {}
/*     */   
/*     */   default void onError(@NonNull BluetoothDevice device, @NonNull String message, int errorCode) {}
/*     */   
/*     */   default void onDeviceNotSupported(@NonNull BluetoothDevice device) {}
/*     */   
/*     */   default void onRssiRead(@NonNull BluetoothDevice device, int rssi) {}
/*     */   
/*     */   default void onBatteryLevelChanged(@NonNull BluetoothDevice device, int batteryLevel) {}
/*     */   
/*     */   default void onSystemId(@NonNull BluetoothDevice device, String systemId) {}
/*     */   
/*     */   default void onModelName(@NonNull BluetoothDevice device, String modelName) {}
/*     */   
/*     */   default void onSerialNumber(@NonNull BluetoothDevice device, String serialNumber) {}
/*     */   
/*     */   default void onFirmwareVersion(@NonNull BluetoothDevice device, String firmware) {}
/*     */   
/*     */   default void onHardwareVersion(@NonNull BluetoothDevice device, String hardware) {}
/*     */   
/*     */   default void onSoftwareVersion(@NonNull BluetoothDevice device, String software) {}
/*     */   
/*     */   default void onVendorName(@NonNull BluetoothDevice device, String vendorName) {}
/*     */ }


/* Location:              C:\Users\Admin\Downloads\jd-gui-windows-1.6.6\classes.jar!\com\android\chileaf\fitness\FitnessManagerCallbacks.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */