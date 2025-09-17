/*    */ package com.android.chileaf.fitness.common.heart;
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
/*    */ 
/*    */ public abstract class BodySensorLocationDataCallback
/*    */   extends ProfileReadResponse
/*    */   implements BodySensorLocationCallback
/*    */ {
/*    */   public BodySensorLocationDataCallback() {}
/*    */   
/*    */   protected BodySensorLocationDataCallback(Parcel in) {
/* 27 */     super(in);
/*    */   }
/*    */ 
/*    */   
/*    */   public void onDataReceived(@NonNull BluetoothDevice device, @NonNull Data data) {
/* 32 */     super.onDataReceived(device, data);
/*    */     
/* 34 */     if (data.size() < 1) {
/* 35 */       onInvalidDataReceived(device, data);
/*    */       
/*    */       return;
/*    */     } 
/* 39 */     int sensorLocation = data.getIntValue(17, 0).intValue();
/* 40 */     onBodySensorLocationReceived(device, sensorLocation);
/*    */   }
/*    */ }


/* Location:              C:\Users\Admin\Downloads\jd-gui-windows-1.6.6\classes.jar!\com\android\chileaf\fitness\common\heart\BodySensorLocationDataCallback.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */