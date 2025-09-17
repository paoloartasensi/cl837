/*    */ package no.nordicsemi.android.ble.data;
/*    */ 
/*    */ import androidx.annotation.IntRange;
/*    */ import androidx.annotation.NonNull;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class DefaultMtuSplitter
/*    */   implements DataSplitter
/*    */ {
/*    */   @Nullable
/*    */   public byte[] chunk(@NonNull byte[] message, @IntRange(from = 0L) int index, @IntRange(from = 20L) int maxLength) {
/* 39 */     int offset = index * maxLength;
/* 40 */     int length = Math.min(maxLength, message.length - offset);
/*    */     
/* 42 */     if (length <= 0) {
/* 43 */       return null;
/*    */     }
/* 45 */     byte[] data = new byte[length];
/* 46 */     System.arraycopy(message, offset, data, 0, length);
/* 47 */     return data;
/*    */   }
/*    */ }


/* Location:              C:\Users\Admin\Downloads\jd-gui-windows-1.6.6\classes.jar!\no\nordicsemi\android\ble\data\DefaultMtuSplitter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */