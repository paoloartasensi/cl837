/*    */ package com.android.chileaf.fitness.common.parser;
/*    */ 
/*    */ import no.nordicsemi.android.ble.data.Data;
/*    */ 
/*    */ 
/*    */ public class CSCMeasurementParser
/*    */ {
/*    */   private static final byte WHEEL_REV_DATA_PRESENT = 1;
/*    */   private static final byte CRANK_REV_DATA_PRESENT = 2;
/*    */   
/*    */   public static String parse(Data data) {
/* 12 */     int offset = 0;
/* 13 */     int flags = data.getByte(offset).byteValue();
/* 14 */     offset++;
/*    */     
/* 16 */     boolean wheelRevPresent = ((flags & 0x1) > 0);
/* 17 */     boolean crankRevPreset = ((flags & 0x2) > 0);
/*    */     
/* 19 */     int wheelRevolutions = 0;
/* 20 */     int lastWheelEventTime = 0;
/* 21 */     if (wheelRevPresent) {
/* 22 */       wheelRevolutions = data.getIntValue(20, offset).intValue();
/* 23 */       offset += 4;
/*    */       
/* 25 */       lastWheelEventTime = data.getIntValue(18, offset).intValue();
/* 26 */       offset += 2;
/*    */     } 
/*    */     
/* 29 */     int crankRevolutions = 0;
/* 30 */     int lastCrankEventTime = 0;
/* 31 */     if (crankRevPreset) {
/* 32 */       crankRevolutions = data.getIntValue(18, offset).intValue();
/* 33 */       offset += 2;
/*    */       
/* 35 */       lastCrankEventTime = data.getIntValue(18, offset).intValue();
/*    */     } 
/*    */ 
/*    */     
/* 39 */     StringBuilder builder = new StringBuilder();
/* 40 */     if (wheelRevPresent) {
/* 41 */       builder.append("Wheel rev: ").append(wheelRevolutions).append(",");
/* 42 */       builder.append("Last wheel event time: ").append(lastWheelEventTime).append(",");
/*    */     } 
/* 44 */     if (crankRevPreset) {
/* 45 */       builder.append("Crank rev: ").append(crankRevolutions).append(",");
/* 46 */       builder.append("Last crank event time: ").append(lastCrankEventTime).append(",");
/*    */     } 
/* 48 */     if (!wheelRevPresent && !crankRevPreset) {
/* 49 */       builder.append("No wheel or crank data");
/*    */     }
/* 51 */     builder.setLength(builder.length() - 2);
/* 52 */     return builder.toString();
/*    */   }
/*    */ }


/* Location:              C:\Users\Admin\Downloads\jd-gui-windows-1.6.6\classes.jar!\com\android\chileaf\fitness\common\parser\CSCMeasurementParser.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */