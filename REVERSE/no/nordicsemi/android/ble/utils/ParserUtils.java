/*     */ package no.nordicsemi.android.ble.utils;
/*     */ 
/*     */ import android.bluetooth.BluetoothGattCharacteristic;
/*     */ import android.bluetooth.BluetoothGattDescriptor;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ParserUtils
/*     */ {
/*  51 */   protected static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();
/*     */   
/*     */   public static String parse(@NonNull BluetoothGattCharacteristic characteristic) {
/*  54 */     return parse(characteristic.getValue());
/*     */   }
/*     */   
/*     */   public static String parse(@NonNull BluetoothGattDescriptor descriptor) {
/*  58 */     return parse(descriptor.getValue());
/*     */   }
/*     */   
/*     */   public static String parse(@Nullable byte[] data) {
/*  62 */     if (data == null || data.length == 0) {
/*  63 */       return "";
/*     */     }
/*  65 */     char[] out = new char[data.length * 3 - 1];
/*  66 */     for (int j = 0; j < data.length; j++) {
/*  67 */       int v = data[j] & 0xFF;
/*  68 */       out[j * 3] = HEX_ARRAY[v >>> 4];
/*  69 */       out[j * 3 + 1] = HEX_ARRAY[v & 0xF];
/*  70 */       if (j != data.length - 1)
/*  71 */         out[j * 3 + 2] = '-'; 
/*     */     } 
/*  73 */     return "(0x) " + new String(out);
/*     */   }
/*     */   
/*     */   public static String parseDebug(@Nullable byte[] data) {
/*  77 */     if (data == null || data.length == 0) {
/*  78 */       return "null";
/*     */     }
/*  80 */     char[] out = new char[data.length * 2];
/*  81 */     for (int j = 0; j < data.length; j++) {
/*  82 */       int v = data[j] & 0xFF;
/*  83 */       out[j * 2] = HEX_ARRAY[v >>> 4];
/*  84 */       out[j * 2 + 1] = HEX_ARRAY[v & 0xF];
/*     */     } 
/*  86 */     return "0x" + new String(out);
/*     */   }
/*     */   
/*     */   @NonNull
/*     */   public static String pairingVariantToString(int variant) {
/*  91 */     switch (variant) {
/*     */       case 0:
/*  93 */         return "PAIRING_VARIANT_PIN";
/*     */       case 1:
/*  95 */         return "PAIRING_VARIANT_PASSKEY";
/*     */       case 2:
/*  97 */         return "PAIRING_VARIANT_PASSKEY_CONFIRMATION";
/*     */       case 3:
/*  99 */         return "PAIRING_VARIANT_CONSENT";
/*     */       case 4:
/* 101 */         return "PAIRING_VARIANT_DISPLAY_PASSKEY";
/*     */       case 5:
/* 103 */         return "PAIRING_VARIANT_DISPLAY_PIN";
/*     */       case 6:
/* 105 */         return "PAIRING_VARIANT_OOB_CONSENT";
/*     */     } 
/* 107 */     return "UNKNOWN (" + variant + ")";
/*     */   }
/*     */ 
/*     */   
/*     */   @NonNull
/*     */   public static String bondStateToString(int state) {
/* 113 */     switch (state) {
/*     */       case 10:
/* 115 */         return "BOND_NONE";
/*     */       case 11:
/* 117 */         return "BOND_BONDING";
/*     */       case 12:
/* 119 */         return "BOND_BONDED";
/*     */     } 
/* 121 */     return "UNKNOWN (" + state + ")";
/*     */   }
/*     */ 
/*     */   
/*     */   @NonNull
/*     */   public static String writeTypeToString(int type) {
/* 127 */     switch (type) {
/*     */       case 2:
/* 129 */         return "WRITE REQUEST";
/*     */       case 1:
/* 131 */         return "WRITE COMMAND";
/*     */       case 4:
/* 133 */         return "WRITE SIGNED";
/*     */     } 
/* 135 */     return "UNKNOWN (" + type + ")";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NonNull
/*     */   public static String stateToString(int state) {
/* 147 */     switch (state) {
/*     */       case 2:
/* 149 */         return "CONNECTED";
/*     */       case 1:
/* 151 */         return "CONNECTING";
/*     */       case 3:
/* 153 */         return "DISCONNECTING";
/*     */       case 0:
/* 155 */         return "DISCONNECTED";
/*     */     } 
/* 157 */     return "UNKNOWN (" + state + ")";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NonNull
/*     */   public static String phyToString(int phy) {
/* 169 */     switch (phy) {
/*     */       case 1:
/* 171 */         return "LE 1M";
/*     */       case 2:
/* 173 */         return "LE 2M";
/*     */       case 3:
/* 175 */         return "LE Coded";
/*     */     } 
/* 177 */     return "UNKNOWN (" + phy + ")";
/*     */   }
/*     */ 
/*     */   
/*     */   @NonNull
/*     */   public static String phyMaskToString(int mask) {
/* 183 */     switch (mask) {
/*     */       case 1:
/* 185 */         return "LE 1M";
/*     */       case 2:
/* 187 */         return "LE 2M";
/*     */       case 4:
/* 189 */         return "LE Coded";
/*     */       case 3:
/* 191 */         return "LE 1M or LE 2M";
/*     */       case 5:
/* 193 */         return "LE 1M or LE Coded";
/*     */       case 6:
/* 195 */         return "LE 2M or LE Coded";
/*     */       
/*     */       case 7:
/* 198 */         return "LE 1M, LE 2M or LE Coded";
/*     */     } 
/* 200 */     return "UNKNOWN (" + mask + ")";
/*     */   }
/*     */ 
/*     */   
/*     */   @NonNull
/*     */   public static String phyCodedOptionToString(int option) {
/* 206 */     switch (option) {
/*     */       case 0:
/* 208 */         return "No preferred";
/*     */       case 1:
/* 210 */         return "S2";
/*     */       case 2:
/* 212 */         return "S8";
/*     */     } 
/* 214 */     return "UNKNOWN (" + option + ")";
/*     */   }
/*     */ }


/* Location:              C:\Users\Admin\Downloads\jd-gui-windows-1.6.6\classes.jar!\no\nordicsemi\android\bl\\utils\ParserUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */