/*    */ package com.android.chileaf.fitness.common;
/*    */ 
/*    */ import android.bluetooth.BluetoothDevice;
/*    */ import android.os.Parcel;
/*    */ import androidx.annotation.NonNull;
/*    */ import androidx.annotation.Nullable;
/*    */ import java.util.Calendar;
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
/*    */ public abstract class DateTimeDataCallback
/*    */   extends ProfileReadResponse
/*    */   implements DateTimeCallback
/*    */ {
/*    */   public DateTimeDataCallback() {}
/*    */   
/*    */   protected DateTimeDataCallback(Parcel in) {
/* 29 */     super(in);
/*    */   }
/*    */ 
/*    */   
/*    */   public void onDataReceived(@NonNull BluetoothDevice device, @NonNull Data data) {
/* 34 */     super.onDataReceived(device, data);
/*    */     
/* 36 */     Calendar calendar = readDateTime(data, 0);
/* 37 */     if (calendar == null) {
/* 38 */       onInvalidDataReceived(device, data);
/*    */       return;
/*    */     } 
/* 41 */     onDateTimeReceived(device, calendar);
/*    */   }
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
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public static Calendar readDateTime(@NonNull Data data, int offset) {
/* 59 */     if (data.size() < offset + 7) {
/* 60 */       return null;
/*    */     }
/* 62 */     Calendar calendar = Calendar.getInstance();
/* 63 */     int year = data.getIntValue(18, offset).intValue();
/* 64 */     int month = data.getIntValue(17, offset + 2).intValue();
/* 65 */     int day = data.getIntValue(17, offset + 3).intValue();
/* 66 */     if (year > 0) {
/* 67 */       calendar.set(1, year);
/*    */     } else {
/* 69 */       calendar.clear(1);
/* 70 */     }  if (month > 0) {
/* 71 */       calendar.set(2, month - 1);
/*    */     } else {
/* 73 */       calendar.clear(2);
/* 74 */     }  if (day > 0) {
/* 75 */       calendar.set(5, day);
/*    */     } else {
/* 77 */       calendar.clear(5);
/* 78 */     }  calendar.set(11, data.getIntValue(17, offset + 4).intValue());
/* 79 */     calendar.set(12, data.getIntValue(17, offset + 5).intValue());
/* 80 */     calendar.set(13, data.getIntValue(17, offset + 6).intValue());
/* 81 */     calendar.set(14, 0);
/* 82 */     return calendar;
/*    */   }
/*    */ }


/* Location:              C:\Users\Admin\Downloads\jd-gui-windows-1.6.6\classes.jar!\com\android\chileaf\fitness\common\DateTimeDataCallback.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */