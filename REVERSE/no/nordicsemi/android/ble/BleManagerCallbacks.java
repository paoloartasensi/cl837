/*     */ package no.nordicsemi.android.ble;
/*     */ 
/*     */ import android.bluetooth.BluetoothDevice;
/*     */ import androidx.annotation.IntRange;
/*     */ import androidx.annotation.NonNull;
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
/*     */ @Deprecated
/*     */ public interface BleManagerCallbacks
/*     */ {
/*     */   @Deprecated
/*     */   void onDeviceConnecting(@NonNull BluetoothDevice paramBluetoothDevice);
/*     */   
/*     */   @Deprecated
/*     */   void onDeviceConnected(@NonNull BluetoothDevice paramBluetoothDevice);
/*     */   
/*     */   @Deprecated
/*     */   void onDeviceDisconnecting(@NonNull BluetoothDevice paramBluetoothDevice);
/*     */   
/*     */   @Deprecated
/*     */   void onDeviceDisconnected(@NonNull BluetoothDevice paramBluetoothDevice);
/*     */   
/*     */   @Deprecated
/*     */   void onLinkLossOccurred(@NonNull BluetoothDevice paramBluetoothDevice);
/*     */   
/*     */   @Deprecated
/*     */   void onServicesDiscovered(@NonNull BluetoothDevice paramBluetoothDevice, boolean paramBoolean);
/*     */   
/*     */   @Deprecated
/*     */   void onDeviceReady(@NonNull BluetoothDevice paramBluetoothDevice);
/*     */   
/*     */   @Deprecated
/*     */   default boolean shouldEnableBatteryLevelNotifications(@NonNull BluetoothDevice device) {
/* 163 */     return false;
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   default void onBatteryValueReceived(@NonNull BluetoothDevice device, @IntRange(from = 0L, to = 100L) int value) {}
/*     */   
/*     */   @Deprecated
/*     */   void onBondingRequired(@NonNull BluetoothDevice paramBluetoothDevice);
/*     */   
/*     */   @Deprecated
/*     */   void onBonded(@NonNull BluetoothDevice paramBluetoothDevice);
/*     */   
/*     */   @Deprecated
/*     */   void onBondingFailed(@NonNull BluetoothDevice paramBluetoothDevice);
/*     */   
/*     */   @Deprecated
/*     */   void onError(@NonNull BluetoothDevice paramBluetoothDevice, @NonNull String paramString, int paramInt);
/*     */   
/*     */   @Deprecated
/*     */   void onDeviceNotSupported(@NonNull BluetoothDevice paramBluetoothDevice);
/*     */ }


/* Location:              C:\Users\Admin\Downloads\jd-gui-windows-1.6.6\classes.jar!\no\nordicsemi\android\ble\BleManagerCallbacks.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */