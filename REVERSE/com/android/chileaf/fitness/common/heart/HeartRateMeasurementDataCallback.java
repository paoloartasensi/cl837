/*    */ package com.android.chileaf.fitness.common.heart;
/*    */ 
/*    */ import android.bluetooth.BluetoothDevice;
/*    */ import android.os.Parcel;
/*    */ import androidx.annotation.NonNull;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collections;
/*    */ import java.util.List;
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
/*    */ public abstract class HeartRateMeasurementDataCallback
/*    */   extends ProfileReadResponse
/*    */   implements HeartRateMeasurementCallback
/*    */ {
/*    */   public HeartRateMeasurementDataCallback() {}
/*    */   
/*    */   protected HeartRateMeasurementDataCallback(Parcel in) {
/* 30 */     super(in);
/*    */   }
/*    */ 
/*    */   
/*    */   public void onDataReceived(@NonNull BluetoothDevice device, @NonNull Data data) {
/* 35 */     super.onDataReceived(device, data);
/*    */     
/* 37 */     if (data.size() < 2) {
/* 38 */       onInvalidDataReceived(device, data);
/*    */       
/*    */       return;
/*    */     } 
/*    */     
/* 43 */     int offset = 0;
/* 44 */     int flags = data.getIntValue(17, offset).intValue();
/* 45 */     int hearRateType = ((flags & 0x1) == 0) ? 17 : 18;
/* 46 */     int sensorContactStatus = (flags & 0x6) >> 1;
/* 47 */     boolean sensorContactSupported = (sensorContactStatus == 2 || sensorContactStatus == 3);
/* 48 */     boolean sensorContactDetected = (sensorContactStatus == 3);
/* 49 */     boolean energyExpandedPresent = ((flags & 0x8) != 0);
/* 50 */     boolean rrIntervalsPresent = ((flags & 0x10) != 0);
/* 51 */     offset++;
/*    */ 
/*    */     
/* 54 */     if (data.size() < 1 + (hearRateType & 0xF) + (
/* 55 */       energyExpandedPresent ? 2 : 0) + (
/* 56 */       rrIntervalsPresent ? 2 : 0)) {
/* 57 */       onInvalidDataReceived(device, data);
/*    */       
/*    */       return;
/*    */     } 
/*    */     
/* 62 */     Boolean sensorContact = sensorContactSupported ? Boolean.valueOf(sensorContactDetected) : null;
/*    */     
/* 64 */     int heartRate = data.getIntValue(hearRateType, offset).intValue();
/* 65 */     offset += hearRateType & 0xF;
/*    */     
/* 67 */     Integer energyExpanded = null;
/* 68 */     if (energyExpandedPresent) {
/* 69 */       energyExpanded = data.getIntValue(18, offset);
/* 70 */       offset += 2;
/*    */     } 
/*    */     
/* 73 */     List<Integer> rrIntervals = null;
/* 74 */     if (rrIntervalsPresent) {
/* 75 */       int count = (data.size() - offset) / 2;
/* 76 */       List<Integer> intervals = new ArrayList<>(count);
/* 77 */       for (int i = 0; i < count; i++) {
/* 78 */         intervals.add(data.getIntValue(18, offset));
/* 79 */         offset += 2;
/*    */       } 
/* 81 */       rrIntervals = Collections.unmodifiableList(intervals);
/*    */     } 
/*    */     
/* 84 */     onHeartRateMeasurementReceived(device, heartRate, sensorContact, energyExpanded, rrIntervals);
/*    */   }
/*    */ }


/* Location:              C:\Users\Admin\Downloads\jd-gui-windows-1.6.6\classes.jar!\com\android\chileaf\fitness\common\heart\HeartRateMeasurementDataCallback.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */