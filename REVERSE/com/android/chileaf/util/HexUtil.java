/*     */ package com.android.chileaf.util;
/*     */ 
/*     */ import java.nio.charset.Charset;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class HexUtil
/*     */ {
/*  11 */   private static final char[] DIGITS_LOWER = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  17 */   private static final char[] DIGITS_UPPER = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static char[] encodeHex(byte[] data) {
/*  27 */     return encodeHex(data, true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static char[] encodeHex(byte[] data, boolean toLowerCase) {
/*  38 */     return encodeHex(data, toLowerCase ? DIGITS_LOWER : DIGITS_UPPER);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static char[] encodeHex(byte[] data, char[] toDigits) {
/*  49 */     if (data == null)
/*  50 */       return new char[0]; 
/*  51 */     int l = data.length;
/*  52 */     char[] out = new char[l << 1];
/*  53 */     for (int i = 0, j = 0; i < l; i++) {
/*  54 */       out[j++] = toDigits[(0xF0 & data[i]) >>> 4];
/*  55 */       out[j++] = toDigits[0xF & data[i]];
/*     */     } 
/*  57 */     return out;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String encodeHexStr(byte[] data) {
/*  67 */     return encodeHexStr(data, true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String encodeHexStr(byte[] data, boolean toLowerCase) {
/*  78 */     return encodeHexStr(data, toLowerCase ? DIGITS_LOWER : DIGITS_UPPER);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static String encodeHexStr(byte[] data, char[] toDigits) {
/*  89 */     return new String(encodeHex(data, toDigits));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static byte[] decodeHex(char[] data) {
/* 100 */     int len = data.length;
/* 101 */     if ((len & 0x1) != 0) {
/* 102 */       throw new RuntimeException("Odd number of characters.");
/*     */     }
/* 104 */     byte[] out = new byte[len >> 1];
/*     */     
/* 106 */     for (int i = 0, j = 0; j < len; i++) {
/* 107 */       int f = toDigit(data[j], j) << 4;
/* 108 */       j++;
/* 109 */       f |= toDigit(data[j], j);
/* 110 */       j++;
/* 111 */       out[i] = (byte)(f & 0xFF);
/*     */     } 
/* 113 */     return out;
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
/*     */   public static int toDigit(char ch, int index) {
/* 125 */     int digit = Character.digit(ch, 16);
/* 126 */     if (digit == -1) {
/* 127 */       throw new RuntimeException("Illegal hexadecimal character " + ch + " at index " + index);
/*     */     }
/* 129 */     return digit;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static byte[] hexStringToBytes(String hexString) {
/* 140 */     if (hexString == null || hexString.equals("")) {
/* 141 */       return new byte[0];
/*     */     }
/* 143 */     hexString = hexString.toUpperCase();
/* 144 */     int length = hexString.length() / 2;
/* 145 */     char[] hexChars = hexString.toCharArray();
/* 146 */     byte[] d = new byte[length];
/* 147 */     for (int i = 0; i < length; i++) {
/* 148 */       int pos = i * 2;
/* 149 */       d[i] = (byte)(charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
/*     */     } 
/* 151 */     return d;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static byte[] fromHexString(String hexString) {
/* 161 */     if (null == hexString || "".equals(hexString.trim())) {
/* 162 */       return new byte[0];
/*     */     }
/* 164 */     byte[] bytes = new byte[hexString.length() / 2];
/*     */     
/* 166 */     for (int i = 0; i < hexString.length() / 2; i++) {
/* 167 */       String hex = hexString.substring(i * 2, i * 2 + 2);
/* 168 */       bytes[i] = (byte)Integer.parseInt(hex, 16);
/*     */     } 
/* 170 */     return bytes;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static byte charToByte(char c) {
/* 180 */     return (byte)"0123456789ABCDEF".indexOf(c);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String byteArrayToString(byte[] data) {
/* 190 */     StringBuilder sb = new StringBuilder();
/* 191 */     for (byte datum : data) {
/* 192 */       char temp = (char)datum;
/* 193 */       if (temp != '\000') {
/* 194 */         sb.append(temp);
/*     */       }
/*     */     } 
/* 197 */     return sb.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String splitToHexString(byte[] data) {
/* 207 */     StringBuilder sb = new StringBuilder();
/* 208 */     for (int i = 0; i < data.length; i++) {
/* 209 */       byte datum = data[i];
/* 210 */       String hex = Integer.toHexString(datum & 0xFF);
/* 211 */       if (hex.length() < 2) {
/* 212 */         sb.append(0);
/*     */       }
/* 214 */       if (datum == 44) {
/* 215 */         char ch = (char)datum;
/* 216 */         sb.append(ch);
/*     */       } else {
/* 218 */         sb.append(hex);
/*     */       } 
/*     */     } 
/* 221 */     return sb.toString();
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
/*     */   public static String getAsciiString(byte[] data, int offset, int length) {
/* 233 */     return new String(data, offset, length, Charset.forName("US-ASCII"));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getAsciiString(byte[] data) {
/* 243 */     return getAsciiString(data, 0, data.length);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static byte[] stringToBytes(String data) {
/* 253 */     byte[] array = new byte[data.length()];
/* 254 */     for (int i = 0; i < data.length(); i++) {
/* 255 */       array[i] = (byte)data.charAt(i);
/*     */     }
/* 257 */     return array;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String bytes2HexString(byte[] bytes) {
/* 264 */     StringBuffer sb = new StringBuffer();
/* 265 */     for (int i = 0; i < bytes.length; i++) {
/* 266 */       String hex = Integer.toHexString(bytes[i] & 0xFF);
/* 267 */       if (hex.length() < 2) {
/* 268 */         sb.append(0);
/*     */       }
/* 270 */       sb.append(hex);
/*     */     } 
/* 272 */     return sb.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String hex2String(String hex) {
/* 282 */     StringBuilder sb = new StringBuilder();
/* 283 */     StringBuilder temp = new StringBuilder();
/* 284 */     for (int i = 0; i < hex.length() - 1; i += 2) {
/* 285 */       String output = hex.substring(i, i + 2);
/* 286 */       int str = Integer.parseInt(output, 16);
/* 287 */       sb.append((char)str);
/* 288 */       temp.append(str);
/*     */     } 
/* 290 */     return sb.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static byte[] compose(int... bytes) {
/* 300 */     byte[] dest = new byte[bytes.length];
/* 301 */     for (int i = 0; i < bytes.length; i++) {
/* 302 */       dest[i] = (byte)(bytes[i] & 0xFF);
/*     */     }
/* 304 */     return dest;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static byte[] compose(byte... bytes) {
/* 314 */     byte[] dest = new byte[bytes.length];
/* 315 */     System.arraycopy(bytes, 0, dest, 0, bytes.length);
/* 316 */     return dest;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static byte[] subByte(byte[] source, int length) {
/* 327 */     byte[] dest = new byte[length];
/* 328 */     System.arraycopy(source, 0, dest, 0, dest.length);
/* 329 */     return dest;
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
/*     */   public static byte[] subByte(byte[] source, int start, int end) {
/* 341 */     byte[] dest = new byte[end - start];
/* 342 */     System.arraycopy(source, start, dest, 0, dest.length);
/* 343 */     return dest;
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
/*     */   public static byte[] subByteByLength(byte[] source, int start, int length) {
/* 355 */     byte[] dest = new byte[length];
/* 356 */     System.arraycopy(source, start, dest, 0, dest.length);
/* 357 */     return dest;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int[] append(int source, int[] dest) {
/* 368 */     int[] result = new int[1 + dest.length];
/* 369 */     result[0] = source;
/* 370 */     System.arraycopy(dest, 0, result, 1, dest.length);
/* 371 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static byte[] append(byte source, byte[] dest) {
/* 382 */     byte[] temp = { source };
/* 383 */     return append(temp, dest);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static byte[] append(byte[] source, byte dest) {
/* 394 */     byte[] result = new byte[source.length + 1];
/* 395 */     byte[] temp = { dest };
/* 396 */     System.arraycopy(source, 0, result, 0, source.length);
/* 397 */     System.arraycopy(temp, 0, result, result.length - 1, 1);
/* 398 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static byte[] append(byte[] source, byte[] dest) {
/* 409 */     byte[] result = new byte[source.length + dest.length];
/* 410 */     System.arraycopy(source, 0, result, 0, source.length);
/* 411 */     int offset = result.length - dest.length;
/* 412 */     System.arraycopy(dest, 0, result, offset, dest.length);
/* 413 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static byte[] append(byte[]... bytes) {
/* 423 */     byte[] dest = new byte[0];
/* 424 */     for (byte[] outs : bytes) {
/* 425 */       dest = append(dest, outs);
/*     */     }
/* 427 */     return dest;
/*     */   }
/*     */ }


/* Location:              C:\Users\Admin\Downloads\jd-gui-windows-1.6.6\classes.jar!\com\android\chilea\\util\HexUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */