/*    */ package com.android.chileaf.fitness.common.parser;
/*    */ 
/*    */ import no.nordicsemi.android.ble.data.Data;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BodySensorLocationParser
/*    */ {
/*    */   public static String parse(Data data) {
/* 10 */     int value = data.getIntValue(17, 0).intValue();
/* 11 */     switch (value) { case 6:
/* 12 */         return "Foot";
/* 13 */       case 5: return "Ear Lobe";
/* 14 */       case 4: return "Hand";
/* 15 */       case 3: return "Finger";
/* 16 */       case 2: return "Wrist";
/* 17 */       case 1: return "Chest"; }
/*    */     
/* 19 */     return "Other";
/*    */   }
/*    */ }


/* Location:              C:\Users\Admin\Downloads\jd-gui-windows-1.6.6\classes.jar!\com\android\chileaf\fitness\common\parser\BodySensorLocationParser.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */