/*    */ package no.nordicsemi.android.ble;
/*    */ 
/*    */ import androidx.annotation.IntRange;
/*    */ import androidx.annotation.Nullable;
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
/*    */ final class Bytes
/*    */ {
/*    */   static byte[] copy(@Nullable byte[] value, @IntRange(from = 0L) int offset, @IntRange(from = 0L) int length) {
/* 18 */     if (value == null || offset > value.length)
/* 19 */       return null; 
/* 20 */     int maxLength = Math.min(value.length - offset, length);
/* 21 */     byte[] copy = new byte[maxLength];
/* 22 */     System.arraycopy(value, offset, copy, 0, maxLength);
/* 23 */     return copy;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   static byte[] concat(@Nullable byte[] left, @Nullable byte[] right, @IntRange(from = 0L) int offset) {
/* 34 */     int length = offset + ((right != null) ? right.length : 0);
/* 35 */     byte[] result = new byte[length];
/* 36 */     if (left != null)
/* 37 */       System.arraycopy(left, 0, result, 0, left.length); 
/* 38 */     if (right != null)
/* 39 */       System.arraycopy(right, 0, result, offset, right.length); 
/* 40 */     return result;
/*    */   }
/*    */ }


/* Location:              C:\Users\Admin\Downloads\jd-gui-windows-1.6.6\classes.jar!\no\nordicsemi\android\ble\Bytes.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */