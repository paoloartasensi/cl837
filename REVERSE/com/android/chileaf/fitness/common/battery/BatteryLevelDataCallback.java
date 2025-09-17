/*    */ package com.android.chileaf.fitness.common.battery;
/*    */ 
/*    */ import android.bluetooth.BluetoothDevice;
/*    */ import android.os.Parcel;
/*    */ import androidx.annotation.NonNull;
/*    */ import no.nordicsemi.android.ble.callback.profile.ProfileReadResponse;
/*    */ import no.nordicsemi.android.ble.data.Data;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class BatteryLevelDataCallback
/*    */   extends ProfileReadResponse
/*    */   implements BatteryLevelCallback
/*    */ {
/*    */   public BatteryLevelDataCallback() {}
/*    */   
/*    */   protected BatteryLevelDataCallback(Parcel in) {
/* 26 */     super(in);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void onDataReceived(@NonNull BluetoothDevice device, @NonNull Data data) {
/* 32 */     super.onDataReceived(device, data);
/*    */     
/* 34 */     if (data.size() == 1) {
/* 35 */       int batteryLevel = data.getIntValue(17, 0).intValue();
/* 36 */       if (batteryLevel >= 0 && batteryLevel <= 100) {
/* 37 */         onBatteryLevelChanged(device, batteryLevel);
/*    */         
/*    */         return;
/*    */       } 
/*    */     } 
/* 42 */     onInvalidDataReceived(device, data);
/*    */   }
/*    */ }


/* Location:              C:\Users\Admin\Downloads\jd-gui-windows-1.6.6\classes.jar!\com\android\chileaf\fitness\common\battery\BatteryLevelDataCallback.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */