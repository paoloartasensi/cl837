/*    */ package com.android.chileaf.fitness.common.parser;
/*    */ 
/*    */ import com.android.chileaf.fitness.common.DateTimeDataCallback;
/*    */ import java.util.Calendar;
/*    */ import java.util.Locale;
/*    */ import no.nordicsemi.android.ble.data.Data;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DateTimeParser
/*    */ {
/*    */   public static String parse(Data data) {
/* 18 */     return parse(data, 0);
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
/*    */   static String parse(Data data, int offset) {
/* 30 */     Calendar calendar = DateTimeDataCallback.readDateTime(data, offset);
/* 31 */     return String.format(Locale.US, "%1$te %1$tb %1$tY, %1$tH:%1$tM:%1$tS", new Object[] { calendar });
/*    */   }
/*    */ }


/* Location:              C:\Users\Admin\Downloads\jd-gui-windows-1.6.6\classes.jar!\com\android\chileaf\fitness\common\parser\DateTimeParser.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */