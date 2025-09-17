/*    */ package com.android.chileaf.fitness.common.parser;
/*    */ 
/*    */ import no.nordicsemi.android.ble.data.Data;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BloodPressureMeasurementParser
/*    */ {
/*    */   public static String parse(Data data) {
/* 10 */     StringBuilder builder = new StringBuilder();
/*    */ 
/*    */     
/* 13 */     int offset = 0;
/* 14 */     int flags = data.getIntValue(17, offset++).intValue();
/*    */     
/* 16 */     int unitType = flags & 0x1;
/* 17 */     boolean timestampPresent = ((flags & 0x2) > 0);
/* 18 */     boolean pulseRatePresent = ((flags & 0x4) > 0);
/* 19 */     boolean userIdPresent = ((flags & 0x8) > 0);
/* 20 */     boolean statusPresent = ((flags & 0x10) > 0);
/*    */ 
/*    */     
/* 23 */     float systolic = data.getFloatValue(50, offset).floatValue();
/* 24 */     float diastolic = data.getFloatValue(50, offset + 2).floatValue();
/* 25 */     float meanArterialPressure = data.getFloatValue(50, offset + 4).floatValue();
/* 26 */     String unit = (unitType == 0) ? " mmHg" : " kPa";
/* 27 */     offset += 6;
/* 28 */     builder.append("Systolic: ").append(systolic).append(unit);
/* 29 */     builder.append(" Diastolic: ").append(diastolic).append(unit);
/* 30 */     builder.append(" Mean AP: ").append(meanArterialPressure).append(unit);
/*    */ 
/*    */     
/* 33 */     if (timestampPresent) {
/* 34 */       builder.append(" Timestamp: ").append(DateTimeParser.parse(data, offset));
/* 35 */       offset += 7;
/*    */     } 
/*    */ 
/*    */     
/* 39 */     if (pulseRatePresent) {
/* 40 */       float pulseRate = data.getFloatValue(50, offset).floatValue();
/* 41 */       offset += 2;
/* 42 */       builder.append(" Pulse: ").append(pulseRate).append(" bpm");
/*    */     } 
/*    */     
/* 45 */     if (userIdPresent) {
/* 46 */       int userId = data.getIntValue(17, offset).intValue();
/* 47 */       offset++;
/* 48 */       builder.append(" User ID: ").append(userId);
/*    */     } 
/*    */     
/* 51 */     if (statusPresent) {
/* 52 */       int status = data.getIntValue(18, offset).intValue();
/*    */       
/* 54 */       if ((status & 0x1) > 0)
/* 55 */         builder.append(" Body movement detected"); 
/* 56 */       if ((status & 0x2) > 0)
/* 57 */         builder.append(" Cuff too lose"); 
/* 58 */       if ((status & 0x4) > 0)
/* 59 */         builder.append(" Irregular pulse detected"); 
/* 60 */       if ((status & 0x18) == 8)
/* 61 */         builder.append(" Pulse rate exceeds upper limit"); 
/* 62 */       if ((status & 0x18) == 16)
/* 63 */         builder.append(" Pulse rate is less than lower limit"); 
/* 64 */       if ((status & 0x18) == 24)
/* 65 */         builder.append(" Pulse rate range: Reserved for future use "); 
/* 66 */       if ((status & 0x20) > 0) {
/* 67 */         builder.append(" Improper measurement position");
/*    */       }
/*    */     } 
/* 70 */     return builder.toString();
/*    */   }
/*    */ }


/* Location:              C:\Users\Admin\Downloads\jd-gui-windows-1.6.6\classes.jar!\com\android\chileaf\fitness\common\parser\BloodPressureMeasurementParser.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */