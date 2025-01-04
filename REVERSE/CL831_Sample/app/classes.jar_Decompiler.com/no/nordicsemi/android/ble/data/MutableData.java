package no.nordicsemi.android.ble.data;

import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class MutableData extends Data {
   private static final int SFLOAT_POSITIVE_INFINITY = 2046;
   private static final int SFLOAT_NAN = 2047;
   private static final int SFLOAT_NEGATIVE_INFINITY = 2050;
   private static final int SFLOAT_MANTISSA_MAX = 2045;
   private static final int SFLOAT_EXPONENT_MAX = 7;
   private static final int SFLOAT_EXPONENT_MIN = -8;
   private static final float SFLOAT_MAX = 2.04500009E10F;
   private static final float SFLOAT_MIN = -2.04500009E10F;
   private static final int SFLOAT_PRECISION = 10000;
   private static final int FLOAT_POSITIVE_INFINITY = 8388606;
   private static final int FLOAT_NAN = 8388607;
   private static final int FLOAT_NEGATIVE_INFINITY = 8388610;
   private static final int FLOAT_MANTISSA_MAX = 8388605;
   private static final int FLOAT_EXPONENT_MAX = 127;
   private static final int FLOAT_EXPONENT_MIN = -128;
   private static final int FLOAT_PRECISION = 10000000;

   public MutableData() {
   }

   public MutableData(@Nullable byte[] data) {
      super(data);
   }

   public static MutableData from(@NonNull BluetoothGattCharacteristic characteristic) {
      return new MutableData(characteristic.getValue());
   }

   public static MutableData from(@NonNull BluetoothGattDescriptor descriptor) {
      return new MutableData(descriptor.getValue());
   }

   public boolean setValue(@Nullable byte[] value) {
      this.mValue = value;
      return true;
   }

   public boolean setByte(int value, @IntRange(from = 0L) int offset) {
      int len = offset + 1;
      if (this.mValue == null) {
         this.mValue = new byte[len];
      }

      if (len > this.mValue.length) {
         return false;
      } else {
         this.mValue[offset] = (byte)value;
         return true;
      }
   }

   public boolean setValue(int value, int formatType, @IntRange(from = 0L) int offset) {
      int len = offset + getTypeLen(formatType);
      if (this.mValue == null) {
         this.mValue = new byte[len];
      }

      if (len > this.mValue.length) {
         return false;
      } else {
         switch(formatType) {
         case 33:
            value = intToSignedBits(value, 8);
         case 17:
            this.mValue[offset] = (byte)(value & 255);
            break;
         case 34:
            value = intToSignedBits(value, 16);
         case 18:
            this.mValue[offset++] = (byte)(value & 255);
            this.mValue[offset] = (byte)(value >> 8 & 255);
            break;
         case 35:
            value = intToSignedBits(value, 24);
         case 19:
            this.mValue[offset++] = (byte)(value & 255);
            this.mValue[offset++] = (byte)(value >> 8 & 255);
            this.mValue[offset] = (byte)(value >> 16 & 255);
            break;
         case 36:
            value = intToSignedBits(value, 32);
         case 20:
            this.mValue[offset++] = (byte)(value & 255);
            this.mValue[offset++] = (byte)(value >> 8 & 255);
            this.mValue[offset++] = (byte)(value >> 16 & 255);
            this.mValue[offset] = (byte)(value >> 24 & 255);
            break;
         case 290:
            value = intToSignedBits(value, 16);
         case 274:
            this.mValue[offset++] = (byte)(value >> 8 & 255);
            this.mValue[offset] = (byte)(value & 255);
            break;
         case 291:
            value = intToSignedBits(value, 24);
         case 275:
            this.mValue[offset++] = (byte)(value >> 16 & 255);
            this.mValue[offset++] = (byte)(value >> 8 & 255);
            this.mValue[offset] = (byte)(value & 255);
            break;
         case 292:
            value = intToSignedBits(value, 32);
         case 276:
            this.mValue[offset++] = (byte)(value >> 24 & 255);
            this.mValue[offset++] = (byte)(value >> 16 & 255);
            this.mValue[offset++] = (byte)(value >> 8 & 255);
            this.mValue[offset] = (byte)(value & 255);
            break;
         default:
            return false;
         }

         return true;
      }
   }

   public boolean setValue(int mantissa, int exponent, int formatType, @IntRange(from = 0L) int offset) {
      int len = offset + getTypeLen(formatType);
      if (this.mValue == null) {
         this.mValue = new byte[len];
      }

      if (len > this.mValue.length) {
         return false;
      } else {
         byte[] var10000;
         switch(formatType) {
         case 50:
            mantissa = intToSignedBits(mantissa, 12);
            exponent = intToSignedBits(exponent, 4);
            this.mValue[offset++] = (byte)(mantissa & 255);
            this.mValue[offset] = (byte)(mantissa >> 8 & 15);
            var10000 = this.mValue;
            var10000[offset] += (byte)((exponent & 15) << 4);
            break;
         case 52:
            mantissa = intToSignedBits(mantissa, 24);
            exponent = intToSignedBits(exponent, 8);
            this.mValue[offset++] = (byte)(mantissa & 255);
            this.mValue[offset++] = (byte)(mantissa >> 8 & 255);
            this.mValue[offset++] = (byte)(mantissa >> 16 & 255);
            var10000 = this.mValue;
            var10000[offset] += (byte)(exponent & 255);
            break;
         default:
            return false;
         }

         return true;
      }
   }

   public boolean setValue(long value, int formatType, @IntRange(from = 0L) int offset) {
      int len = offset + getTypeLen(formatType);
      if (this.mValue == null) {
         this.mValue = new byte[len];
      }

      if (len > this.mValue.length) {
         return false;
      } else {
         switch(formatType) {
         case 36:
            value = longToSignedBits(value, 32);
         case 20:
            this.mValue[offset++] = (byte)((int)(value & 255L));
            this.mValue[offset++] = (byte)((int)(value >> 8 & 255L));
            this.mValue[offset++] = (byte)((int)(value >> 16 & 255L));
            this.mValue[offset] = (byte)((int)(value >> 24 & 255L));
            break;
         case 292:
            value = longToSignedBits(value, 32);
         case 276:
            this.mValue[offset++] = (byte)((int)(value >> 24 & 255L));
            this.mValue[offset++] = (byte)((int)(value >> 16 & 255L));
            this.mValue[offset++] = (byte)((int)(value >> 8 & 255L));
            this.mValue[offset] = (byte)((int)(value & 255L));
            break;
         default:
            return false;
         }

         return true;
      }
   }

   public boolean setValue(float value, int formatType, @IntRange(from = 0L) int offset) {
      int len = offset + getTypeLen(formatType);
      if (this.mValue == null) {
         this.mValue = new byte[len];
      }

      if (len > this.mValue.length) {
         return false;
      } else {
         switch(formatType) {
         case 50:
            int sfloatAsInt = sfloatToInt(value);
            this.mValue[offset++] = (byte)(sfloatAsInt & 255);
            this.mValue[offset] = (byte)(sfloatAsInt >> 8 & 255);
            break;
         case 52:
            int floatAsInt = floatToInt(value);
            this.mValue[offset++] = (byte)(floatAsInt & 255);
            this.mValue[offset++] = (byte)(floatAsInt >> 8 & 255);
            this.mValue[offset++] = (byte)(floatAsInt >> 16 & 255);
            byte[] var10000 = this.mValue;
            var10000[offset] += (byte)(floatAsInt >> 24 & 255);
            break;
         default:
            return false;
         }

         return true;
      }
   }

   private static int sfloatToInt(float value) {
      if (Float.isNaN(value)) {
         return 2047;
      } else if (value > 2.04500009E10F) {
         return 2046;
      } else if (value < -2.04500009E10F) {
         return 2050;
      } else {
         int sign = value >= 0.0F ? 1 : -1;
         float mantissa = Math.abs(value);
         int exponent = 0;

         while(mantissa > 2045.0F) {
            mantissa /= 10.0F;
            ++exponent;
            if (exponent > 7) {
               if (sign > 0) {
                  return 2046;
               }

               return 2050;
            }
         }

         while(mantissa < 1.0F) {
            mantissa *= 10.0F;
            --exponent;
            if (exponent < -8) {
               return 0;
            }
         }

         double smantissa = (double)Math.round(mantissa * 10000.0F);
         double rmantissa = (double)(Math.round(mantissa) * 10000);

         for(double mdiff = Math.abs(smantissa - rmantissa); mdiff > 0.5D && exponent > -8 && mantissa * 10.0F <= 2045.0F; mdiff = Math.abs(smantissa - rmantissa)) {
            mantissa *= 10.0F;
            --exponent;
            smantissa = (double)Math.round(mantissa * 10000.0F);
            rmantissa = (double)(Math.round(mantissa) * 10000);
         }

         int int_mantissa = Math.round((float)sign * mantissa);
         return (exponent & 15) << 12 | int_mantissa & 4095;
      }
   }

   private static int floatToInt(float value) {
      if (Float.isNaN(value)) {
         return 8388607;
      } else if (value == Float.POSITIVE_INFINITY) {
         return 8388606;
      } else if (value == Float.NEGATIVE_INFINITY) {
         return 8388610;
      } else {
         int sign = value >= 0.0F ? 1 : -1;
         float mantissa = Math.abs(value);
         int exponent = 0;

         while(mantissa > 8388605.0F) {
            mantissa /= 10.0F;
            ++exponent;
            if (exponent > 127) {
               if (sign > 0) {
                  return 8388606;
               }

               return 8388610;
            }
         }

         while(mantissa < 1.0F) {
            mantissa *= 10.0F;
            --exponent;
            if (exponent < -128) {
               return 0;
            }
         }

         double smantissa = (double)Math.round(mantissa * 1.0E7F);
         double rmantissa = (double)(Math.round(mantissa) * 10000000);

         for(double mdiff = Math.abs(smantissa - rmantissa); mdiff > 0.5D && exponent > -128 && mantissa * 10.0F <= 8388605.0F; mdiff = Math.abs(smantissa - rmantissa)) {
            mantissa *= 10.0F;
            --exponent;
            smantissa = (double)Math.round(mantissa * 1.0E7F);
            rmantissa = (double)(Math.round(mantissa) * 10000000);
         }

         int int_mantissa = Math.round((float)sign * mantissa);
         return exponent << 24 | int_mantissa & 16777215;
      }
   }

   private static int intToSignedBits(int i, int size) {
      if (i < 0) {
         i = (1 << size - 1) + (i & (1 << size - 1) - 1);
      }

      return i;
   }

   private static long longToSignedBits(long i, int size) {
      if (i < 0L) {
         i = (1L << size - 1) + (i & (1L << size - 1) - 1L);
      }

      return i;
   }
}
