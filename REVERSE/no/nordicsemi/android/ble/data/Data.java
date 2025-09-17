/*     */ package no.nordicsemi.android.ble.data;
/*     */ 
/*     */ import android.annotation.SuppressLint;
/*     */ import android.bluetooth.BluetoothGattCharacteristic;
/*     */ import android.bluetooth.BluetoothGattDescriptor;
/*     */ import android.os.Parcel;
/*     */ import android.os.Parcelable;
/*     */ import androidx.annotation.IntRange;
/*     */ import androidx.annotation.NonNull;
/*     */ import androidx.annotation.Nullable;
/*     */ import java.lang.annotation.Retention;
/*     */ import java.lang.annotation.RetentionPolicy;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Data
/*     */   implements Parcelable
/*     */ {
/* 118 */   private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();
/*     */ 
/*     */   
/*     */   public static final int FORMAT_UINT8 = 17;
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public static final int FORMAT_UINT16 = 18;
/*     */ 
/*     */   
/*     */   public static final int FORMAT_UINT16_LE = 18;
/*     */ 
/*     */   
/*     */   public static final int FORMAT_UINT16_BE = 274;
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public static final int FORMAT_UINT24 = 19;
/*     */ 
/*     */   
/*     */   public static final int FORMAT_UINT24_LE = 19;
/*     */ 
/*     */   
/*     */   public static final int FORMAT_UINT24_BE = 275;
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public static final int FORMAT_UINT32 = 20;
/*     */ 
/*     */   
/*     */   public static final int FORMAT_UINT32_LE = 20;
/*     */ 
/*     */   
/*     */   public static final int FORMAT_UINT32_BE = 276;
/*     */ 
/*     */   
/*     */   public static final int FORMAT_SINT8 = 33;
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public static final int FORMAT_SINT16 = 34;
/*     */ 
/*     */   
/*     */   public static final int FORMAT_SINT16_LE = 34;
/*     */ 
/*     */   
/*     */   public static final int FORMAT_SINT16_BE = 290;
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public static final int FORMAT_SINT24 = 35;
/*     */ 
/*     */   
/*     */   public static final int FORMAT_SINT24_LE = 35;
/*     */ 
/*     */   
/*     */   public static final int FORMAT_SINT24_BE = 291;
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public static final int FORMAT_SINT32 = 36;
/*     */ 
/*     */   
/*     */   public static final int FORMAT_SINT32_LE = 36;
/*     */ 
/*     */   
/*     */   public static final int FORMAT_SINT32_BE = 292;
/*     */ 
/*     */   
/*     */   public static final int FORMAT_SFLOAT = 50;
/*     */ 
/*     */   
/*     */   public static final int FORMAT_FLOAT = 52;
/*     */ 
/*     */   
/*     */   protected byte[] mValue;
/*     */ 
/*     */   
/*     */   public Data() {
/* 197 */     this.mValue = null;
/*     */   }
/*     */   
/*     */   public Data(@Nullable byte[] value) {
/* 201 */     this.mValue = value;
/*     */   }
/*     */   
/*     */   public static Data from(@NonNull String value) {
/* 205 */     return new Data(value.getBytes());
/*     */   }
/*     */   
/*     */   public static Data from(@NonNull BluetoothGattCharacteristic characteristic) {
/* 209 */     return new Data(characteristic.getValue());
/*     */   }
/*     */   
/*     */   public static Data from(@NonNull BluetoothGattDescriptor descriptor) {
/* 213 */     return new Data(descriptor.getValue());
/*     */   }
/*     */   
/*     */   public static Data opCode(byte opCode) {
/* 217 */     return new Data(new byte[] { opCode });
/*     */   }
/*     */   
/*     */   public static Data opCode(byte opCode, byte parameter) {
/* 221 */     return new Data(new byte[] { opCode, parameter });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public byte[] getValue() {
/* 231 */     return this.mValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public String getStringValue(@IntRange(from = 0L) int offset) {
/* 243 */     if (this.mValue == null || offset > this.mValue.length)
/* 244 */       return null; 
/* 245 */     byte[] strBytes = new byte[this.mValue.length - offset];
/* 246 */     for (int i = 0; i != this.mValue.length - offset; i++)
/* 247 */       strBytes[i] = this.mValue[offset + i]; 
/* 248 */     return new String(strBytes);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int size() {
/* 257 */     return (this.mValue != null) ? this.mValue.length : 0;
/*     */   }
/*     */ 
/*     */   
/*     */   @NonNull
/*     */   public String toString() {
/* 263 */     if (size() == 0) {
/* 264 */       return "";
/*     */     }
/* 266 */     char[] out = new char[this.mValue.length * 3 - 1];
/* 267 */     for (int j = 0; j < this.mValue.length; j++) {
/* 268 */       int v = this.mValue[j] & 0xFF;
/* 269 */       out[j * 3] = HEX_ARRAY[v >>> 4];
/* 270 */       out[j * 3 + 1] = HEX_ARRAY[v & 0xF];
/* 271 */       if (j != this.mValue.length - 1)
/* 272 */         out[j * 3 + 2] = '-'; 
/*     */     } 
/* 274 */     return "(0x) " + new String(out);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Byte getByte(@IntRange(from = 0L) int offset) {
/* 285 */     if (offset + 1 > size()) return null;
/*     */     
/* 287 */     return Byte.valueOf(this.mValue[offset]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Integer getIntValue(int formatType, @IntRange(from = 0L) int offset) {
/* 306 */     if (offset + getTypeLen(formatType) > size()) return null;
/*     */     
/* 308 */     switch (formatType) {
/*     */       case 17:
/* 310 */         return Integer.valueOf(unsignedByteToInt(this.mValue[offset]));
/*     */       
/*     */       case 18:
/* 313 */         return Integer.valueOf(unsignedBytesToInt(this.mValue[offset], this.mValue[offset + 1]));
/*     */       case 274:
/* 315 */         return Integer.valueOf(unsignedBytesToInt(this.mValue[offset + 1], this.mValue[offset]));
/*     */       
/*     */       case 19:
/* 318 */         return Integer.valueOf(unsignedBytesToInt(this.mValue[offset], this.mValue[offset + 1], this.mValue[offset + 2], (byte)0));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       case 275:
/* 325 */         return Integer.valueOf(unsignedBytesToInt(this.mValue[offset + 2], this.mValue[offset + 1], this.mValue[offset], (byte)0));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       case 20:
/* 333 */         return Integer.valueOf(unsignedBytesToInt(this.mValue[offset], this.mValue[offset + 1], this.mValue[offset + 2], this.mValue[offset + 3]));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       case 276:
/* 340 */         return Integer.valueOf(unsignedBytesToInt(this.mValue[offset + 3], this.mValue[offset + 2], this.mValue[offset + 1], this.mValue[offset]));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       case 33:
/* 348 */         return Integer.valueOf(unsignedToSigned(unsignedByteToInt(this.mValue[offset]), 8));
/*     */       
/*     */       case 34:
/* 351 */         return Integer.valueOf(unsignedToSigned(unsignedBytesToInt(this.mValue[offset], this.mValue[offset + 1]), 16));
/*     */       
/*     */       case 290:
/* 354 */         return Integer.valueOf(unsignedToSigned(unsignedBytesToInt(this.mValue[offset + 1], this.mValue[offset]), 16));
/*     */ 
/*     */       
/*     */       case 35:
/* 358 */         return Integer.valueOf(unsignedToSigned(unsignedBytesToInt(this.mValue[offset], this.mValue[offset + 1], this.mValue[offset + 2], (byte)0), 24));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       case 291:
/* 365 */         return Integer.valueOf(unsignedToSigned(unsignedBytesToInt((byte)0, this.mValue[offset + 2], this.mValue[offset + 1], this.mValue[offset]), 24));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       case 36:
/* 373 */         return Integer.valueOf(unsignedToSigned(unsignedBytesToInt(this.mValue[offset], this.mValue[offset + 1], this.mValue[offset + 2], this.mValue[offset + 3]), 32));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       case 292:
/* 380 */         return Integer.valueOf(unsignedToSigned(unsignedBytesToInt(this.mValue[offset + 3], this.mValue[offset + 2], this.mValue[offset + 1], this.mValue[offset]), 32));
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 388 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Long getLongValue(int formatType, @IntRange(from = 0L) int offset) {
/* 407 */     if (offset + getTypeLen(formatType) > size()) return null;
/*     */     
/* 409 */     switch (formatType) {
/*     */       case 20:
/* 411 */         return Long.valueOf(unsignedBytesToLong(this.mValue[offset], this.mValue[offset + 1], this.mValue[offset + 2], this.mValue[offset + 3]));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       case 276:
/* 418 */         return Long.valueOf(unsignedBytesToLong(this.mValue[offset + 3], this.mValue[offset + 2], this.mValue[offset + 1], this.mValue[offset]));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       case 36:
/* 426 */         return Long.valueOf(unsignedToSigned(unsignedBytesToLong(this.mValue[offset], this.mValue[offset + 1], this.mValue[offset + 2], this.mValue[offset + 3]), 32));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       case 292:
/* 433 */         return Long.valueOf(unsignedToSigned(unsignedBytesToLong(this.mValue[offset + 3], this.mValue[offset + 2], this.mValue[offset + 1], this.mValue[offset]), 32));
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 441 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Float getFloatValue(int formatType, @IntRange(from = 0L) int offset) {
/* 454 */     if (offset + getTypeLen(formatType) > size()) return null;
/*     */     
/* 456 */     switch (formatType) {
/*     */       case 50:
/* 458 */         if (this.mValue[offset + 1] == 7 && this.mValue[offset] == -2)
/* 459 */           return Float.valueOf(Float.POSITIVE_INFINITY); 
/* 460 */         if ((this.mValue[offset + 1] == 7 && this.mValue[offset] == -1) || (this.mValue[offset + 1] == 8 && this.mValue[offset] == 0) || (this.mValue[offset + 1] == 8 && this.mValue[offset] == 1))
/*     */         {
/*     */           
/* 463 */           return Float.valueOf(Float.NaN); } 
/* 464 */         if (this.mValue[offset + 1] == 8 && this.mValue[offset] == 2) {
/* 465 */           return Float.valueOf(Float.NEGATIVE_INFINITY);
/*     */         }
/* 467 */         return Float.valueOf(bytesToFloat(this.mValue[offset], this.mValue[offset + 1]));
/*     */       
/*     */       case 52:
/* 470 */         if (this.mValue[offset + 3] == 0) {
/* 471 */           if (this.mValue[offset + 2] == Byte.MAX_VALUE && this.mValue[offset + 1] == -1) {
/* 472 */             if (this.mValue[offset] == -2)
/* 473 */               return Float.valueOf(Float.POSITIVE_INFINITY); 
/* 474 */             if (this.mValue[offset] == -1)
/* 475 */               return Float.valueOf(Float.NaN); 
/* 476 */           } else if (this.mValue[offset + 2] == Byte.MIN_VALUE && this.mValue[offset + 1] == 0) {
/* 477 */             if (this.mValue[offset] == 0 || this.mValue[offset] == 1)
/* 478 */               return Float.valueOf(Float.NaN); 
/* 479 */             if (this.mValue[offset] == 2) {
/* 480 */               return Float.valueOf(Float.NEGATIVE_INFINITY);
/*     */             }
/*     */           } 
/*     */         }
/* 484 */         return Float.valueOf(bytesToFloat(this.mValue[offset], this.mValue[offset + 1], this.mValue[offset + 2], this.mValue[offset + 3]));
/*     */     } 
/*     */ 
/*     */     
/* 488 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getTypeLen(int formatType) {
/* 495 */     return formatType & 0xF;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int unsignedByteToInt(byte b) {
/* 502 */     return b & 0xFF;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static long unsignedByteToLong(byte b) {
/* 509 */     return b & 0xFFL;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int unsignedBytesToInt(byte b0, byte b1) {
/* 516 */     return unsignedByteToInt(b0) + (unsignedByteToInt(b1) << 8);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int unsignedBytesToInt(byte b0, byte b1, byte b2, byte b3) {
/* 523 */     return unsignedByteToInt(b0) + (unsignedByteToInt(b1) << 8) + (
/* 524 */       unsignedByteToInt(b2) << 16) + (unsignedByteToInt(b3) << 24);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static long unsignedBytesToLong(byte b0, byte b1, byte b2, byte b3) {
/* 531 */     return unsignedByteToLong(b0) + (unsignedByteToLong(b1) << 8L) + (
/* 532 */       unsignedByteToLong(b2) << 16L) + (unsignedByteToLong(b3) << 24L);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static float bytesToFloat(byte b0, byte b1) {
/* 539 */     int mantissa = unsignedToSigned(unsignedByteToInt(b0) + ((
/* 540 */         unsignedByteToInt(b1) & 0xF) << 8), 12);
/* 541 */     int exponent = unsignedToSigned(unsignedByteToInt(b1) >> 4, 4);
/* 542 */     return (float)(mantissa * Math.pow(10.0D, exponent));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static float bytesToFloat(byte b0, byte b1, byte b2, byte b3) {
/* 549 */     int mantissa = unsignedToSigned(unsignedByteToInt(b0) + (
/* 550 */         unsignedByteToInt(b1) << 8) + (
/* 551 */         unsignedByteToInt(b2) << 16), 24);
/* 552 */     return (float)(mantissa * Math.pow(10.0D, b3));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int unsignedToSigned(int unsigned, int size) {
/* 560 */     if ((unsigned & 1 << size - 1) != 0) {
/* 561 */       unsigned = -1 * ((1 << size - 1) - (unsigned & (1 << size - 1) - 1));
/*     */     }
/* 563 */     return unsigned;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static long unsignedToSigned(long unsigned, int size) {
/* 572 */     if ((unsigned & 1L << size - 1) != 0L) {
/* 573 */       unsigned = -1L * ((1L << size - 1) - (unsigned & (1L << size - 1) - 1L));
/*     */     }
/* 575 */     return unsigned;
/*     */   }
/*     */ 
/*     */   
/*     */   protected Data(Parcel in) {
/* 580 */     this.mValue = in.createByteArray();
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeToParcel(Parcel dest, int flags) {
/* 585 */     dest.writeByteArray(this.mValue);
/*     */   }
/*     */ 
/*     */   
/*     */   public int describeContents() {
/* 590 */     return 0;
/*     */   }
/*     */   
/* 593 */   public static final Parcelable.Creator<Data> CREATOR = new Parcelable.Creator<Data>()
/*     */     {
/*     */       public Data createFromParcel(Parcel in) {
/* 596 */         return new Data(in);
/*     */       }
/*     */ 
/*     */       
/*     */       public Data[] newArray(int size) {
/* 601 */         return new Data[size];
/*     */       }
/*     */     };
/*     */   
/*     */   @Retention(RetentionPolicy.SOURCE)
/*     */   public static @interface FloatFormat {}
/*     */   
/*     */   @Retention(RetentionPolicy.SOURCE)
/*     */   @SuppressLint({"UniqueConstants"})
/*     */   public static @interface LongFormat {}
/*     */   
/*     */   @Retention(RetentionPolicy.SOURCE)
/*     */   @SuppressLint({"UniqueConstants"})
/*     */   public static @interface IntFormat {}
/*     */   
/*     */   @Retention(RetentionPolicy.SOURCE)
/*     */   @SuppressLint({"UniqueConstants"})
/*     */   public static @interface ValueFormat {}
/*     */ }


/* Location:              C:\Users\Admin\Downloads\jd-gui-windows-1.6.6\classes.jar!\no\nordicsemi\android\ble\data\Data.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */