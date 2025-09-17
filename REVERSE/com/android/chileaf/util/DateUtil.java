/*    */ package com.android.chileaf.util;
/*    */ 
/*    */ import java.util.Calendar;
/*    */ 
/*    */ public class DateUtil
/*    */ {
/*    */   public static long getZoneUTC() {
/*  8 */     Calendar calendar = Calendar.getInstance();
/*  9 */     int zoneOffset = calendar.get(15);
/* 10 */     int dstOffset = calendar.get(16);
/* 11 */     calendar.add(14, zoneOffset + dstOffset);
/* 12 */     return calendar.getTimeInMillis() / 1000L;
/*    */   }
/*    */   
/*    */   public static long restoreZoneUTC(long stamp) {
/* 16 */     Calendar calendar = Calendar.getInstance();
/* 17 */     calendar.setTimeInMillis(stamp * 1000L);
/* 18 */     int zoneOffset = calendar.get(15);
/* 19 */     int dstOffset = calendar.get(16);
/* 20 */     calendar.add(14, -(zoneOffset + dstOffset));
/* 21 */     return calendar.getTimeInMillis();
/*    */   }
/*    */   
/*    */   public static long restoreZoneUTCTimeInMillis(long stamp) {
/* 25 */     Calendar calendar = Calendar.getInstance();
/* 26 */     calendar.setTimeInMillis(stamp);
/* 27 */     int zoneOffset = calendar.get(15);
/* 28 */     int dstOffset = calendar.get(16);
/* 29 */     calendar.add(14, -(zoneOffset + dstOffset));
/* 30 */     return calendar.getTimeInMillis();
/*    */   }
/*    */ }


/* Location:              C:\Users\Admin\Downloads\jd-gui-windows-1.6.6\classes.jar!\com\android\chilea\\util\DateUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */