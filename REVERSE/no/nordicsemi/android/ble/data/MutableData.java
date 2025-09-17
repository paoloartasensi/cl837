/*     */ package no.nordicsemi.android.ble.data;
/*     */ 
/*     */ import android.bluetooth.BluetoothGattCharacteristic;
/*     */ import android.bluetooth.BluetoothGattDescriptor;
/*     */ import androidx.annotation.IntRange;
/*     */ import androidx.annotation.NonNull;
/*     */ import androidx.annotation.Nullable;
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
/*     */ public class MutableData
/*     */   extends Data
/*     */ {
/*     */   private static final int SFLOAT_POSITIVE_INFINITY = 2046;
/*     */   private static final int SFLOAT_NAN = 2047;
/*     */   private static final int SFLOAT_NEGATIVE_INFINITY = 2050;
/*     */   private static final int SFLOAT_MANTISSA_MAX = 2045;
/*     */   private static final int SFLOAT_EXPONENT_MAX = 7;
/*     */   private static final int SFLOAT_EXPONENT_MIN = -8;
/*     */   private static final float SFLOAT_MAX = 2.04500009E10F;
/*     */   private static final float SFLOAT_MIN = -2.04500009E10F;
/*     */   private static final int SFLOAT_PRECISION = 10000;
/*     */   private static final int FLOAT_POSITIVE_INFINITY = 8388606;
/*     */   private static final int FLOAT_NAN = 8388607;
/*     */   private static final int FLOAT_NEGATIVE_INFINITY = 8388610;
/*     */   private static final int FLOAT_MANTISSA_MAX = 8388605;
/*     */   private static final int FLOAT_EXPONENT_MAX = 127;
/*     */   private static final int FLOAT_EXPONENT_MIN = -128;
/*     */   private static final int FLOAT_PRECISION = 10000000;
/*     */   
/*     */   public MutableData() {}
/*     */   
/*     */   public MutableData(@Nullable byte[] data) {
/*  65 */     super(data);
/*     */   }
/*     */   
/*     */   public static MutableData from(@NonNull BluetoothGattCharacteristic characteristic) {
/*  69 */     return new MutableData(characteristic.getValue());
/*     */   }
/*     */   
/*     */   public static MutableData from(@NonNull BluetoothGattDescriptor descriptor) {
/*  73 */     return new MutableData(descriptor.getValue());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean setValue(@Nullable byte[] value) {
/*  84 */     this.mValue = value;
/*  85 */     return true;
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
/*     */   public boolean setByte(int value, @IntRange(from = 0L) int offset) {
/*  97 */     int len = offset + 1;
/*  98 */     if (this.mValue == null) this.mValue = new byte[len]; 
/*  99 */     if (len > this.mValue.length) return false; 
/* 100 */     this.mValue[offset] = (byte)value;
/* 101 */     return true;
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
/*     */   public boolean setValue(int value, int formatType, @IntRange(from = 0L) int offset) {
/* 114 */     int len = offset + getTypeLen(formatType);
/* 115 */     if (this.mValue == null) this.mValue = new byte[len]; 
/* 116 */     if (len > this.mValue.length) return false;
/*     */     
/* 118 */     switch (formatType) {
/*     */       case 33:
/* 120 */         value = intToSignedBits(value, 8);
/*     */       
/*     */       case 17:
/* 123 */         this.mValue[offset] = (byte)(value & 0xFF);
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
/* 183 */         return true;case 34: value = intToSignedBits(value, 16);case 18: this.mValue[offset++] = (byte)(value & 0xFF); this.mValue[offset] = (byte)(value >> 8 & 0xFF); return true;case 290: value = intToSignedBits(value, 16);case 274: this.mValue[offset++] = (byte)(value >> 8 & 0xFF); this.mValue[offset] = (byte)(value & 0xFF); return true;case 35: value = intToSignedBits(value, 24);case 19: this.mValue[offset++] = (byte)(value & 0xFF); this.mValue[offset++] = (byte)(value >> 8 & 0xFF); this.mValue[offset] = (byte)(value >> 16 & 0xFF); return true;case 291: value = intToSignedBits(value, 24);case 275: this.mValue[offset++] = (byte)(value >> 16 & 0xFF); this.mValue[offset++] = (byte)(value >> 8 & 0xFF); this.mValue[offset] = (byte)(value & 0xFF); return true;case 36: value = intToSignedBits(value, 32);case 20: this.mValue[offset++] = (byte)(value & 0xFF); this.mValue[offset++] = (byte)(value >> 8 & 0xFF); this.mValue[offset++] = (byte)(value >> 16 & 0xFF); this.mValue[offset] = (byte)(value >> 24 & 0xFF); return true;case 292: value = intToSignedBits(value, 32);case 276: this.mValue[offset++] = (byte)(value >> 24 & 0xFF); this.mValue[offset++] = (byte)(value >> 16 & 0xFF); this.mValue[offset++] = (byte)(value >> 8 & 0xFF); this.mValue[offset] = (byte)(value & 0xFF); return true;
/*     */     } 
/*     */     return false;
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
/*     */   public boolean setValue(int mantissa, int exponent, int formatType, @IntRange(from = 0L) int offset) {
/* 198 */     int len = offset + getTypeLen(formatType);
/* 199 */     if (this.mValue == null) this.mValue = new byte[len]; 
/* 200 */     if (len > this.mValue.length) return false;
/*     */     
/* 202 */     switch (formatType) {
/*     */       case 50:
/* 204 */         mantissa = intToSignedBits(mantissa, 12);
/* 205 */         exponent = intToSignedBits(exponent, 4);
/* 206 */         this.mValue[offset++] = (byte)(mantissa & 0xFF);
/* 207 */         this.mValue[offset] = (byte)(mantissa >> 8 & 0xF);
/* 208 */         this.mValue[offset] = (byte)(this.mValue[offset] + (byte)((exponent & 0xF) << 4));
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
/* 224 */         return true;case 52: mantissa = intToSignedBits(mantissa, 24); exponent = intToSignedBits(exponent, 8); this.mValue[offset++] = (byte)(mantissa & 0xFF); this.mValue[offset++] = (byte)(mantissa >> 8 & 0xFF); this.mValue[offset++] = (byte)(mantissa >> 16 & 0xFF); this.mValue[offset] = (byte)(this.mValue[offset] + (byte)(exponent & 0xFF)); return true;
/*     */     } 
/*     */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean setValue(long value, int formatType, @IntRange(from = 0L) int offset) {
/* 237 */     int len = offset + getTypeLen(formatType);
/* 238 */     if (this.mValue == null) this.mValue = new byte[len]; 
/* 239 */     if (len > this.mValue.length) return false;
/*     */     
/* 241 */     switch (formatType) {
/*     */       case 36:
/* 243 */         value = longToSignedBits(value, 32);
/*     */       
/*     */       case 20:
/* 246 */         this.mValue[offset++] = (byte)(int)(value & 0xFFL);
/* 247 */         this.mValue[offset++] = (byte)(int)(value >> 8L & 0xFFL);
/* 248 */         this.mValue[offset++] = (byte)(int)(value >> 16L & 0xFFL);
/* 249 */         this.mValue[offset] = (byte)(int)(value >> 24L & 0xFFL);
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
/* 265 */         return true;case 292: value = longToSignedBits(value, 32);case 276: this.mValue[offset++] = (byte)(int)(value >> 24L & 0xFFL); this.mValue[offset++] = (byte)(int)(value >> 16L & 0xFFL); this.mValue[offset++] = (byte)(int)(value >> 8L & 0xFFL); this.mValue[offset] = (byte)(int)(value & 0xFFL); return true;
/*     */     } 
/*     */     return false;
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
/*     */   public boolean setValue(float value, int formatType, @IntRange(from = 0L) int offset) {
/* 279 */     int sfloatAsInt, floatAsInt, len = offset + getTypeLen(formatType);
/* 280 */     if (this.mValue == null) this.mValue = new byte[len]; 
/* 281 */     if (len > this.mValue.length) return false;
/*     */     
/* 283 */     switch (formatType) {
/*     */       case 50:
/* 285 */         sfloatAsInt = sfloatToInt(value);
/* 286 */         this.mValue[offset++] = (byte)(sfloatAsInt & 0xFF);
/* 287 */         this.mValue[offset] = (byte)(sfloatAsInt >> 8 & 0xFF);
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
/* 302 */         return true;case 52: floatAsInt = floatToInt(value); this.mValue[offset++] = (byte)(floatAsInt & 0xFF); this.mValue[offset++] = (byte)(floatAsInt >> 8 & 0xFF); this.mValue[offset++] = (byte)(floatAsInt >> 16 & 0xFF); this.mValue[offset] = (byte)(this.mValue[offset] + (byte)(floatAsInt >> 24 & 0xFF)); return true;
/*     */     } 
/*     */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int sfloatToInt(float value) {
/* 313 */     if (Float.isNaN(value))
/* 314 */       return 2047; 
/* 315 */     if (value > 2.04500009E10F)
/* 316 */       return 2046; 
/* 317 */     if (value < -2.04500009E10F) {
/* 318 */       return 2050;
/*     */     }
/*     */     
/* 321 */     int sign = (value >= 0.0F) ? 1 : -1;
/* 322 */     float mantissa = Math.abs(value);
/* 323 */     int exponent = 0;
/*     */ 
/*     */     
/* 326 */     while (mantissa > 2045.0F) {
/* 327 */       mantissa /= 10.0F;
/* 328 */       exponent++;
/* 329 */       if (exponent > 7) {
/*     */         
/* 331 */         if (sign > 0) {
/* 332 */           return 2046;
/*     */         }
/* 334 */         return 2050;
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 340 */     while (mantissa < 1.0F) {
/* 341 */       mantissa *= 10.0F;
/* 342 */       exponent--;
/* 343 */       if (exponent < -8)
/*     */       {
/* 345 */         return 0;
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 350 */     double smantissa = Math.round(mantissa * 10000.0F);
/* 351 */     double rmantissa = (Math.round(mantissa) * 10000);
/* 352 */     double mdiff = Math.abs(smantissa - rmantissa);
/* 353 */     while (mdiff > 0.5D && exponent > -8 && mantissa * 10.0F <= 2045.0F) {
/*     */       
/* 355 */       mantissa *= 10.0F;
/* 356 */       exponent--;
/* 357 */       smantissa = Math.round(mantissa * 10000.0F);
/* 358 */       rmantissa = (Math.round(mantissa) * 10000);
/* 359 */       mdiff = Math.abs(smantissa - rmantissa);
/*     */     } 
/*     */     
/* 362 */     int int_mantissa = Math.round(sign * mantissa);
/* 363 */     return (exponent & 0xF) << 12 | int_mantissa & 0xFFF;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int floatToInt(float value) {
/* 374 */     if (Float.isNaN(value))
/* 375 */       return 8388607; 
/* 376 */     if (value == Float.POSITIVE_INFINITY)
/* 377 */       return 8388606; 
/* 378 */     if (value == Float.NEGATIVE_INFINITY) {
/* 379 */       return 8388610;
/*     */     }
/*     */     
/* 382 */     int sign = (value >= 0.0F) ? 1 : -1;
/* 383 */     float mantissa = Math.abs(value);
/* 384 */     int exponent = 0;
/*     */ 
/*     */     
/* 387 */     while (mantissa > 8388605.0F) {
/* 388 */       mantissa /= 10.0F;
/* 389 */       exponent++;
/* 390 */       if (exponent > 127) {
/*     */         
/* 392 */         if (sign > 0) {
/* 393 */           return 8388606;
/*     */         }
/* 395 */         return 8388610;
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 401 */     while (mantissa < 1.0F) {
/* 402 */       mantissa *= 10.0F;
/* 403 */       exponent--;
/* 404 */       if (exponent < -128)
/*     */       {
/* 406 */         return 0;
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 411 */     double smantissa = Math.round(mantissa * 1.0E7F);
/* 412 */     double rmantissa = (Math.round(mantissa) * 10000000);
/* 413 */     double mdiff = Math.abs(smantissa - rmantissa);
/* 414 */     while (mdiff > 0.5D && exponent > -128 && mantissa * 10.0F <= 8388605.0F) {
/*     */       
/* 416 */       mantissa *= 10.0F;
/* 417 */       exponent--;
/* 418 */       smantissa = Math.round(mantissa * 1.0E7F);
/* 419 */       rmantissa = (Math.round(mantissa) * 10000000);
/* 420 */       mdiff = Math.abs(smantissa - rmantissa);
/*     */     } 
/*     */     
/* 423 */     int int_mantissa = Math.round(sign * mantissa);
/* 424 */     return exponent << 24 | int_mantissa & 0xFFFFFF;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int intToSignedBits(int i, int size) {
/* 431 */     if (i < 0) {
/* 432 */       i = (1 << size - 1) + (i & (1 << size - 1) - 1);
/*     */     }
/* 434 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static long longToSignedBits(long i, int size) {
/* 441 */     if (i < 0L) {
/* 442 */       i = (1L << size - 1) + (i & (1L << size - 1) - 1L);
/*     */     }
/* 444 */     return i;
/*     */   }
/*     */ }


/* Location:              C:\Users\Admin\Downloads\jd-gui-windows-1.6.6\classes.jar!\no\nordicsemi\android\ble\data\MutableData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */