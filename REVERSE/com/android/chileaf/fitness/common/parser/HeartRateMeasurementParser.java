/*    */ package com.android.chileaf.fitness.common.parser;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import java.util.Locale;
/*    */ import no.nordicsemi.android.ble.data.Data;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class HeartRateMeasurementParser
/*    */ {
/*    */   private static final byte HEART_RATE_VALUE_FORMAT = 1;
/*    */   private static final byte SENSOR_CONTACT_STATUS = 6;
/*    */   private static final byte ENERGY_EXPANDED_STATUS = 8;
/*    */   private static final byte RR_INTERVAL = 16;
/*    */   
/*    */   public static String parse(Data data) {
/* 18 */     int offset = 0;
/* 19 */     int flags = data.getIntValue(17, offset++).intValue();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 25 */     boolean value16bit = ((flags & 0x1) > 0);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 33 */     int sensorContactStatus = (flags & 0x6) >> 1;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 39 */     boolean energyExpandedStatus = ((flags & 0x8) > 0);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 45 */     boolean rrIntervalStatus = ((flags & 0x10) > 0);
/*    */ 
/*    */     
/* 48 */     int heartRateValue = data.getIntValue(value16bit ? 18 : 17, offset++).intValue();
/* 49 */     if (value16bit) {
/* 50 */       offset++;
/*    */     }
/*    */     
/* 53 */     int energyExpanded = -1;
/* 54 */     if (energyExpandedStatus)
/* 55 */       energyExpanded = data.getIntValue(18, offset).intValue(); 
/* 56 */     offset += 2;
/*    */ 
/*    */     
/* 59 */     List<Float> rrIntervals = new ArrayList<>();
/* 60 */     if (rrIntervalStatus) {
/* 61 */       for (int o = offset; o < (data.getValue()).length; o += 2) {
/* 62 */         int units = data.getIntValue(18, o).intValue();
/* 63 */         rrIntervals.add(Float.valueOf(units * 1000.0F / 1024.0F));
/*    */       } 
/*    */     }
/*    */     
/* 67 */     StringBuilder builder = new StringBuilder();
/* 68 */     builder.append("Heart Rate Measurement: ").append(heartRateValue).append(" bpm");
/* 69 */     switch (sensorContactStatus) {
/*    */       case 0:
/*    */       case 1:
/* 72 */         builder.append(",Sensor Contact Not Supported");
/*    */         break;
/*    */       case 2:
/* 75 */         builder.append(",Contact is NOT Detected");
/*    */         break;
/*    */       case 3:
/* 78 */         builder.append(",Contact is Detected");
/*    */         break;
/*    */     } 
/* 81 */     if (energyExpandedStatus)
/* 82 */       builder.append(",Energy Expanded: ").append(energyExpanded).append(" kJ"); 
/* 83 */     if (rrIntervalStatus) {
/* 84 */       builder.append(",RR Interval: ");
/* 85 */       for (Float interval : rrIntervals) {
/* 86 */         builder.append(String.format(Locale.US, "%.02f ms, ", new Object[] { interval }));
/* 87 */       }  builder.setLength(builder.length() - 2);
/*    */     } 
/* 89 */     return builder.toString();
/*    */   }
/*    */ }


/* Location:              C:\Users\Admin\Downloads\jd-gui-windows-1.6.6\classes.jar!\com\android\chileaf\fitness\common\parser\HeartRateMeasurementParser.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */