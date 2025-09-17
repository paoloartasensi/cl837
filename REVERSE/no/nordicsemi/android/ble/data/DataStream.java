/*    */ package no.nordicsemi.android.ble.data;
/*    */ 
/*    */ import androidx.annotation.IntRange;
/*    */ import androidx.annotation.NonNull;
/*    */ import androidx.annotation.Nullable;
/*    */ import java.io.ByteArrayOutputStream;
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
/*    */ public class DataStream
/*    */ {
/* 36 */   private final ByteArrayOutputStream buffer = new ByteArrayOutputStream();
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean write(@Nullable byte[] data) {
/* 41 */     if (data == null) {
/* 42 */       return false;
/*    */     }
/* 44 */     return write(data, 0, data.length);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean write(@Nullable byte[] data, @IntRange(from = 0L) int offset, @IntRange(from = 0L) int length) {
/* 49 */     if (data == null || data.length < offset) {
/* 50 */       return false;
/*    */     }
/* 52 */     int len = Math.min(data.length - offset, length);
/* 53 */     this.buffer.write(data, offset, len);
/* 54 */     return true;
/*    */   }
/*    */   
/*    */   public boolean write(@Nullable Data data) {
/* 58 */     return (data != null && write(data.getValue()));
/*    */   }
/*    */   
/*    */   @IntRange(from = 0L)
/*    */   public int size() {
/* 63 */     return this.buffer.size();
/*    */   }
/*    */   
/*    */   @NonNull
/*    */   public byte[] toByteArray() {
/* 68 */     return this.buffer.toByteArray();
/*    */   }
/*    */   
/*    */   @NonNull
/*    */   public Data toData() {
/* 73 */     return new Data(this.buffer.toByteArray());
/*    */   }
/*    */ }


/* Location:              C:\Users\Admin\Downloads\jd-gui-windows-1.6.6\classes.jar!\no\nordicsemi\android\ble\data\DataStream.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */