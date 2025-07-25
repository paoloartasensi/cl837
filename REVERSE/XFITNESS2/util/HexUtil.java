package com.android.chileaf.util;

import java.nio.charset.Charset;

public class HexUtil {
   private static final char[] DIGITS_LOWER = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
   private static final char[] DIGITS_UPPER = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

   public static char[] encodeHex(final byte[] data) {
      return encodeHex(data, true);
   }

   public static char[] encodeHex(final byte[] data, final boolean toLowerCase) {
      return encodeHex(data, toLowerCase ? DIGITS_LOWER : DIGITS_UPPER);
   }

   protected static char[] encodeHex(final byte[] data, final char[] toDigits) {
      if (data == null) {
         return new char[0];
      } else {
         int l = data.length;
         char[] out = new char[l << 1];
         int i = 0;

         for(int var5 = 0; i < l; ++i) {
            out[var5++] = toDigits[(240 & data[i]) >>> 4];
            out[var5++] = toDigits[15 & data[i]];
         }

         return out;
      }
   }

   public static String encodeHexStr(final byte[] data) {
      return encodeHexStr(data, true);
   }

   public static String encodeHexStr(final byte[] data, final boolean toLowerCase) {
      return encodeHexStr(data, toLowerCase ? DIGITS_LOWER : DIGITS_UPPER);
   }

   protected static String encodeHexStr(final byte[] data, final char[] toDigits) {
      return new String(encodeHex(data, toDigits));
   }

   public static byte[] decodeHex(char[] data) {
      int len = data.length;
      if ((len & 1) != 0) {
         throw new RuntimeException("Odd number of characters.");
      } else {
         byte[] out = new byte[len >> 1];
         int i = 0;

         for(int j = 0; j < len; ++i) {
            int f = toDigit(data[j], j) << 4;
            ++j;
            f |= toDigit(data[j], j);
            ++j;
            out[i] = (byte)(f & 255);
         }

         return out;
      }
   }

   public static int toDigit(char ch, int index) {
      int digit = Character.digit(ch, 16);
      if (digit == -1) {
         throw new RuntimeException("Illegal hexadecimal character " + ch + " at index " + index);
      } else {
         return digit;
      }
   }

   public static byte[] hexStringToBytes(String hexString) {
      if (hexString != null && !hexString.equals("")) {
         hexString = hexString.toUpperCase();
         int length = hexString.length() / 2;
         char[] hexChars = hexString.toCharArray();
         byte[] d = new byte[length];

         for(int i = 0; i < length; ++i) {
            int pos = i * 2;
            d[i] = (byte)(charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
         }

         return d;
      } else {
         return new byte[0];
      }
   }

   public static byte[] fromHexString(final String hexString) {
      if (null != hexString && !"".equals(hexString.trim())) {
         byte[] bytes = new byte[hexString.length() / 2];

         for(int i = 0; i < hexString.length() / 2; ++i) {
            String hex = hexString.substring(i * 2, i * 2 + 2);
            bytes[i] = (byte)Integer.parseInt(hex, 16);
         }

         return bytes;
      } else {
         return new byte[0];
      }
   }

   public static byte charToByte(char c) {
      return (byte)"0123456789ABCDEF".indexOf(c);
   }

   public static String byteArrayToString(final byte[] data) {
      StringBuilder sb = new StringBuilder();
      byte[] var2 = data;
      int var3 = data.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         byte datum = var2[var4];
         char temp = (char)datum;
         if (temp != 0) {
            sb.append(temp);
         }
      }

      return sb.toString();
   }

   public static String splitToHexString(final byte[] data) {
      StringBuilder sb = new StringBuilder();

      for(int i = 0; i < data.length; ++i) {
         byte datum = data[i];
         String hex = Integer.toHexString(datum & 255);
         if (hex.length() < 2) {
            sb.append(0);
         }

         if (datum == 44) {
            char ch = (char)datum;
            sb.append(ch);
         } else {
            sb.append(hex);
         }
      }

      return sb.toString();
   }

   public static String getAsciiString(final byte[] data, final int offset, final int length) {
      return new String(data, offset, length, Charset.forName("US-ASCII"));
   }

   public static String getAsciiString(final byte[] data) {
      return getAsciiString(data, 0, data.length);
   }

   public static byte[] stringToBytes(final String data) {
      byte[] array = new byte[data.length()];

      for(int i = 0; i < data.length(); ++i) {
         array[i] = (byte)data.charAt(i);
      }

      return array;
   }

   public static String bytes2HexString(final byte[] bytes) {
      StringBuffer sb = new StringBuffer();

      for(int i = 0; i < bytes.length; ++i) {
         String hex = Integer.toHexString(bytes[i] & 255);
         if (hex.length() < 2) {
            sb.append(0);
         }

         sb.append(hex);
      }

      return sb.toString();
   }

   public static String hex2String(final String hex) {
      StringBuilder sb = new StringBuilder();
      StringBuilder temp = new StringBuilder();

      for(int i = 0; i < hex.length() - 1; i += 2) {
         String output = hex.substring(i, i + 2);
         int str = Integer.parseInt(output, 16);
         sb.append((char)str);
         temp.append(str);
      }

      return sb.toString();
   }

   public static byte[] compose(final int... bytes) {
      byte[] dest = new byte[bytes.length];

      for(int i = 0; i < bytes.length; ++i) {
         dest[i] = (byte)(bytes[i] & 255);
      }

      return dest;
   }

   public static byte[] compose(final byte... bytes) {
      byte[] dest = new byte[bytes.length];
      System.arraycopy(bytes, 0, dest, 0, bytes.length);
      return dest;
   }

   public static byte[] subByte(final byte[] source, final int length) {
      byte[] dest = new byte[length];
      System.arraycopy(source, 0, dest, 0, dest.length);
      return dest;
   }

   public static byte[] subByte(final byte[] source, final int start, final int end) {
      byte[] dest = new byte[end - start];
      System.arraycopy(source, start, dest, 0, dest.length);
      return dest;
   }

   public static byte[] subByteByLength(final byte[] source, final int start, final int length) {
      byte[] dest = new byte[length];
      System.arraycopy(source, start, dest, 0, dest.length);
      return dest;
   }

   public static int[] append(final int source, final int[] dest) {
      int[] result = new int[1 + dest.length];
      result[0] = source;
      System.arraycopy(dest, 0, result, 1, dest.length);
      return result;
   }

   public static byte[] append(final byte source, final byte[] dest) {
      byte[] temp = new byte[]{source};
      return append(temp, dest);
   }

   public static byte[] append(final byte[] source, final byte dest) {
      byte[] result = new byte[source.length + 1];
      byte[] temp = new byte[]{dest};
      System.arraycopy(source, 0, result, 0, source.length);
      System.arraycopy(temp, 0, result, result.length - 1, 1);
      return result;
   }

   public static byte[] append(final byte[] source, final byte[] dest) {
      byte[] result = new byte[source.length + dest.length];
      System.arraycopy(source, 0, result, 0, source.length);
      int offset = result.length - dest.length;
      System.arraycopy(dest, 0, result, offset, dest.length);
      return result;
   }

   public static byte[] append(final byte[]... bytes) {
      byte[] dest = new byte[0];
      byte[][] var2 = bytes;
      int var3 = bytes.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         byte[] outs = var2[var4];
         dest = append(dest, outs);
      }

      return dest;
   }
}
