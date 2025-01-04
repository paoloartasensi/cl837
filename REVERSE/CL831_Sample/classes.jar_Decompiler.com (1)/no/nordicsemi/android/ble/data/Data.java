package no.nordicsemi.android.ble.data;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class Data implements Parcelable {
   private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();
   public static final int FORMAT_UINT8 = 17;
   /** @deprecated */
   @Deprecated
   public static final int FORMAT_UINT16 = 18;
   public static final int FORMAT_UINT16_LE = 18;
   public static final int FORMAT_UINT16_BE = 274;
   /** @deprecated */
   @Deprecated
   public static final int FORMAT_UINT24 = 19;
   public static final int FORMAT_UINT24_LE = 19;
   public static final int FORMAT_UINT24_BE = 275;
   /** @deprecated */
   @Deprecated
   public static final int FORMAT_UINT32 = 20;
   public static final int FORMAT_UINT32_LE = 20;
   public static final int FORMAT_UINT32_BE = 276;
   public static final int FORMAT_SINT8 = 33;
   /** @deprecated */
   @Deprecated
   public static final int FORMAT_SINT16 = 34;
   public static final int FORMAT_SINT16_LE = 34;
   public static final int FORMAT_SINT16_BE = 290;
   /** @deprecated */
   @Deprecated
   public static final int FORMAT_SINT24 = 35;
   public static final int FORMAT_SINT24_LE = 35;
   public static final int FORMAT_SINT24_BE = 291;
   /** @deprecated */
   @Deprecated
   public static final int FORMAT_SINT32 = 36;
   public static final int FORMAT_SINT32_LE = 36;
   public static final int FORMAT_SINT32_BE = 292;
   public static final int FORMAT_SFLOAT = 50;
   public static final int FORMAT_FLOAT = 52;
   protected byte[] mValue;
   public static final Creator<Data> CREATOR = new Creator<Data>() {
      public Data createFromParcel(Parcel in) {
         return new Data(in);
      }

      public Data[] newArray(int size) {
         return new Data[size];
      }
   };

   public Data() {
      this.mValue = null;
   }

   public Data(@Nullable byte[] value) {
      this.mValue = value;
   }

   public static Data from(@NonNull String value) {
      return new Data(value.getBytes());
   }

   public static Data from(@NonNull BluetoothGattCharacteristic characteristic) {
      return new Data(characteristic.getValue());
   }

   public static Data from(@NonNull BluetoothGattDescriptor descriptor) {
      return new Data(descriptor.getValue());
   }

   public static Data opCode(byte opCode) {
      return new Data(new byte[]{opCode});
   }

   public static Data opCode(byte opCode, byte parameter) {
      return new Data(new byte[]{opCode, parameter});
   }

   @Nullable
   public byte[] getValue() {
      return this.mValue;
   }

   @Nullable
   public String getStringValue(@IntRange(from = 0L) int offset) {
      if (this.mValue != null && offset <= this.mValue.length) {
         byte[] strBytes = new byte[this.mValue.length - offset];

         for(int i = 0; i != this.mValue.length - offset; ++i) {
            strBytes[i] = this.mValue[offset + i];
         }

         return new String(strBytes);
      } else {
         return null;
      }
   }

   public int size() {
      return this.mValue != null ? this.mValue.length : 0;
   }

   @NonNull
   public String toString() {
      if (this.size() == 0) {
         return "";
      } else {
         char[] out = new char[this.mValue.length * 3 - 1];

         for(int j = 0; j < this.mValue.length; ++j) {
            int v = this.mValue[j] & 255;
            out[j * 3] = HEX_ARRAY[v >>> 4];
            out[j * 3 + 1] = HEX_ARRAY[v & 15];
            if (j != this.mValue.length - 1) {
               out[j * 3 + 2] = '-';
            }
         }

         return "(0x) " + new String(out);
      }
   }

   @Nullable
   public Byte getByte(@IntRange(from = 0L) int offset) {
      return offset + 1 > this.size() ? null : this.mValue[offset];
   }

   @Nullable
   public Integer getIntValue(int formatType, @IntRange(from = 0L) int offset) {
      if (offset + getTypeLen(formatType) > this.size()) {
         return null;
      } else {
         switch(formatType) {
         case 17:
            return unsignedByteToInt(this.mValue[offset]);
         case 18:
            return unsignedBytesToInt(this.mValue[offset], this.mValue[offset + 1]);
         case 19:
            return unsignedBytesToInt(this.mValue[offset], this.mValue[offset + 1], this.mValue[offset + 2], (byte)0);
         case 20:
            return unsignedBytesToInt(this.mValue[offset], this.mValue[offset + 1], this.mValue[offset + 2], this.mValue[offset + 3]);
         case 33:
            return unsignedToSigned(unsignedByteToInt(this.mValue[offset]), 8);
         case 34:
            return unsignedToSigned(unsignedBytesToInt(this.mValue[offset], this.mValue[offset + 1]), 16);
         case 35:
            return unsignedToSigned(unsignedBytesToInt(this.mValue[offset], this.mValue[offset + 1], this.mValue[offset + 2], (byte)0), 24);
         case 36:
            return unsignedToSigned(unsignedBytesToInt(this.mValue[offset], this.mValue[offset + 1], this.mValue[offset + 2], this.mValue[offset + 3]), 32);
         case 274:
            return unsignedBytesToInt(this.mValue[offset + 1], this.mValue[offset]);
         case 275:
            return unsignedBytesToInt(this.mValue[offset + 2], this.mValue[offset + 1], this.mValue[offset], (byte)0);
         case 276:
            return unsignedBytesToInt(this.mValue[offset + 3], this.mValue[offset + 2], this.mValue[offset + 1], this.mValue[offset]);
         case 290:
            return unsignedToSigned(unsignedBytesToInt(this.mValue[offset + 1], this.mValue[offset]), 16);
         case 291:
            return unsignedToSigned(unsignedBytesToInt((byte)0, this.mValue[offset + 2], this.mValue[offset + 1], this.mValue[offset]), 24);
         case 292:
            return unsignedToSigned(unsignedBytesToInt(this.mValue[offset + 3], this.mValue[offset + 2], this.mValue[offset + 1], this.mValue[offset]), 32);
         default:
            return null;
         }
      }
   }

   @Nullable
   public Long getLongValue(int formatType, @IntRange(from = 0L) int offset) {
      if (offset + getTypeLen(formatType) > this.size()) {
         return null;
      } else {
         switch(formatType) {
         case 20:
            return unsignedBytesToLong(this.mValue[offset], this.mValue[offset + 1], this.mValue[offset + 2], this.mValue[offset + 3]);
         case 36:
            return unsignedToSigned(unsignedBytesToLong(this.mValue[offset], this.mValue[offset + 1], this.mValue[offset + 2], this.mValue[offset + 3]), 32);
         case 276:
            return unsignedBytesToLong(this.mValue[offset + 3], this.mValue[offset + 2], this.mValue[offset + 1], this.mValue[offset]);
         case 292:
            return unsignedToSigned(unsignedBytesToLong(this.mValue[offset + 3], this.mValue[offset + 2], this.mValue[offset + 1], this.mValue[offset]), 32);
         default:
            return null;
         }
      }
   }

   @Nullable
   public Float getFloatValue(int formatType, @IntRange(from = 0L) int offset) {
      if (offset + getTypeLen(formatType) > this.size()) {
         return null;
      } else {
         switch(formatType) {
         case 50:
            if (this.mValue[offset + 1] == 7 && this.mValue[offset] == -2) {
               return Float.POSITIVE_INFINITY;
            } else if (this.mValue[offset + 1] == 7 && this.mValue[offset] == -1 || this.mValue[offset + 1] == 8 && this.mValue[offset] == 0 || this.mValue[offset + 1] == 8 && this.mValue[offset] == 1) {
               return Float.NaN;
            } else {
               if (this.mValue[offset + 1] == 8 && this.mValue[offset] == 2) {
                  return Float.NEGATIVE_INFINITY;
               }

               return bytesToFloat(this.mValue[offset], this.mValue[offset + 1]);
            }
         case 52:
            if (this.mValue[offset + 3] == 0) {
               if (this.mValue[offset + 2] == 127 && this.mValue[offset + 1] == -1) {
                  if (this.mValue[offset] == -2) {
                     return Float.POSITIVE_INFINITY;
                  }

                  if (this.mValue[offset] == -1) {
                     return Float.NaN;
                  }
               } else if (this.mValue[offset + 2] == -128 && this.mValue[offset + 1] == 0) {
                  if (this.mValue[offset] == 0 || this.mValue[offset] == 1) {
                     return Float.NaN;
                  }

                  if (this.mValue[offset] == 2) {
                     return Float.NEGATIVE_INFINITY;
                  }
               }
            }

            return bytesToFloat(this.mValue[offset], this.mValue[offset + 1], this.mValue[offset + 2], this.mValue[offset + 3]);
         default:
            return null;
         }
      }
   }

   public static int getTypeLen(int formatType) {
      return formatType & 15;
   }

   private static int unsignedByteToInt(byte b) {
      return b & 255;
   }

   private static long unsignedByteToLong(byte b) {
      return (long)b & 255L;
   }

   private static int unsignedBytesToInt(byte b0, byte b1) {
      return unsignedByteToInt(b0) + (unsignedByteToInt(b1) << 8);
   }

   private static int unsignedBytesToInt(byte b0, byte b1, byte b2, byte b3) {
      return unsignedByteToInt(b0) + (unsignedByteToInt(b1) << 8) + (unsignedByteToInt(b2) << 16) + (unsignedByteToInt(b3) << 24);
   }

   private static long unsignedBytesToLong(byte b0, byte b1, byte b2, byte b3) {
      return unsignedByteToLong(b0) + (unsignedByteToLong(b1) << 8) + (unsignedByteToLong(b2) << 16) + (unsignedByteToLong(b3) << 24);
   }

   private static float bytesToFloat(byte b0, byte b1) {
      int mantissa = unsignedToSigned(unsignedByteToInt(b0) + ((unsignedByteToInt(b1) & 15) << 8), 12);
      int exponent = unsignedToSigned(unsignedByteToInt(b1) >> 4, 4);
      return (float)((double)mantissa * Math.pow(10.0D, (double)exponent));
   }

   private static float bytesToFloat(byte b0, byte b1, byte b2, byte b3) {
      int mantissa = unsignedToSigned(unsignedByteToInt(b0) + (unsignedByteToInt(b1) << 8) + (unsignedByteToInt(b2) << 16), 24);
      return (float)((double)mantissa * Math.pow(10.0D, (double)b3));
   }

   private static int unsignedToSigned(int unsigned, int size) {
      if ((unsigned & 1 << size - 1) != 0) {
         unsigned = -1 * ((1 << size - 1) - (unsigned & (1 << size - 1) - 1));
      }

      return unsigned;
   }

   private static long unsignedToSigned(long unsigned, int size) {
      if ((unsigned & 1L << size - 1) != 0L) {
         unsigned = -1L * ((1L << size - 1) - (unsigned & (1L << size - 1) - 1L));
      }

      return unsigned;
   }

   protected Data(Parcel in) {
      this.mValue = in.createByteArray();
   }

   public void writeToParcel(Parcel dest, int flags) {
      dest.writeByteArray(this.mValue);
   }

   public int describeContents() {
      return 0;
   }

   @Retention(RetentionPolicy.SOURCE)
   public @interface FloatFormat {
   }

   @Retention(RetentionPolicy.SOURCE)
   @SuppressLint({"UniqueConstants"})
   public @interface LongFormat {
   }

   @Retention(RetentionPolicy.SOURCE)
   @SuppressLint({"UniqueConstants"})
   public @interface IntFormat {
   }

   @Retention(RetentionPolicy.SOURCE)
   @SuppressLint({"UniqueConstants"})
   public @interface ValueFormat {
   }
}
