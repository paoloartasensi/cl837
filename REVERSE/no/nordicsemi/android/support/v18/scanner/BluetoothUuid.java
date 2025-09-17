/*    */ package no.nordicsemi.android.support.v18.scanner;
/*    */ 
/*    */ import android.os.ParcelUuid;
/*    */ import java.nio.ByteBuffer;
/*    */ import java.nio.ByteOrder;
/*    */ import java.util.UUID;
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
/*    */ final class BluetoothUuid
/*    */ {
/* 31 */   private static final ParcelUuid BASE_UUID = ParcelUuid.fromString("00000000-0000-1000-8000-00805F9B34FB");
/*    */ 
/*    */ 
/*    */   
/*    */   static final int UUID_BYTES_16_BIT = 2;
/*    */ 
/*    */ 
/*    */   
/*    */   static final int UUID_BYTES_32_BIT = 4;
/*    */ 
/*    */ 
/*    */   
/*    */   static final int UUID_BYTES_128_BIT = 16;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   static ParcelUuid parseUuidFrom(byte[] uuidBytes) {
/*    */     long shortUuid;
/* 50 */     if (uuidBytes == null) {
/* 51 */       throw new IllegalArgumentException("uuidBytes cannot be null");
/*    */     }
/* 53 */     int length = uuidBytes.length;
/* 54 */     if (length != 2 && length != 4 && length != 16)
/*    */     {
/* 56 */       throw new IllegalArgumentException("uuidBytes length invalid - " + length);
/*    */     }
/*    */ 
/*    */     
/* 60 */     if (length == 16) {
/* 61 */       ByteBuffer buf = ByteBuffer.wrap(uuidBytes).order(ByteOrder.LITTLE_ENDIAN);
/* 62 */       long l1 = buf.getLong(8);
/* 63 */       long l2 = buf.getLong(0);
/* 64 */       return new ParcelUuid(new UUID(l1, l2));
/*    */     } 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 70 */     if (length == 2) {
/* 71 */       shortUuid = (uuidBytes[0] & 0xFF);
/* 72 */       shortUuid += ((uuidBytes[1] & 0xFF) << 8);
/*    */     } else {
/* 74 */       shortUuid = (uuidBytes[0] & 0xFF);
/* 75 */       shortUuid += ((uuidBytes[1] & 0xFF) << 8);
/* 76 */       shortUuid += ((uuidBytes[2] & 0xFF) << 16);
/* 77 */       shortUuid += ((uuidBytes[3] & 0xFF) << 24);
/*    */     } 
/* 79 */     long msb = BASE_UUID.getUuid().getMostSignificantBits() + (shortUuid << 32L);
/* 80 */     long lsb = BASE_UUID.getUuid().getLeastSignificantBits();
/* 81 */     return new ParcelUuid(new UUID(msb, lsb));
/*    */   }
/*    */ }


/* Location:              C:\Users\Admin\Downloads\jd-gui-windows-1.6.6\classes.jar!\no\nordicsemi\android\support\v18\scanner\BluetoothUuid.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */