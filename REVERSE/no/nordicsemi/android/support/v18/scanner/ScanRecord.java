/*     */ package no.nordicsemi.android.support.v18.scanner;
/*     */ 
/*     */ import android.os.ParcelUuid;
/*     */ import android.util.Log;
/*     */ import android.util.SparseArray;
/*     */ import androidx.annotation.NonNull;
/*     */ import androidx.annotation.Nullable;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
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
/*     */ public final class ScanRecord
/*     */ {
/*     */   private static final String TAG = "ScanRecord";
/*     */   private static final int DATA_TYPE_FLAGS = 1;
/*     */   private static final int DATA_TYPE_SERVICE_UUIDS_16_BIT_PARTIAL = 2;
/*     */   private static final int DATA_TYPE_SERVICE_UUIDS_16_BIT_COMPLETE = 3;
/*     */   private static final int DATA_TYPE_SERVICE_UUIDS_32_BIT_PARTIAL = 4;
/*     */   private static final int DATA_TYPE_SERVICE_UUIDS_32_BIT_COMPLETE = 5;
/*     */   private static final int DATA_TYPE_SERVICE_UUIDS_128_BIT_PARTIAL = 6;
/*     */   private static final int DATA_TYPE_SERVICE_UUIDS_128_BIT_COMPLETE = 7;
/*     */   private static final int DATA_TYPE_LOCAL_NAME_SHORT = 8;
/*     */   private static final int DATA_TYPE_LOCAL_NAME_COMPLETE = 9;
/*     */   private static final int DATA_TYPE_TX_POWER_LEVEL = 10;
/*     */   private static final int DATA_TYPE_SERVICE_DATA_16_BIT = 22;
/*     */   private static final int DATA_TYPE_SERVICE_DATA_32_BIT = 32;
/*     */   private static final int DATA_TYPE_SERVICE_DATA_128_BIT = 33;
/*     */   private static final int DATA_TYPE_MANUFACTURER_SPECIFIC_DATA = 255;
/*     */   private final int advertiseFlags;
/*     */   @Nullable
/*     */   private final List<ParcelUuid> serviceUuids;
/*     */   @Nullable
/*     */   private final SparseArray<byte[]> manufacturerSpecificData;
/*     */   @Nullable
/*     */   private final Map<ParcelUuid, byte[]> serviceData;
/*     */   private final int txPowerLevel;
/*     */   private final String deviceName;
/*     */   private final byte[] bytes;
/*     */   
/*     */   public int getAdvertiseFlags() {
/*  87 */     return this.advertiseFlags;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public List<ParcelUuid> getServiceUuids() {
/*  96 */     return this.serviceUuids;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public SparseArray<byte[]> getManufacturerSpecificData() {
/* 105 */     return this.manufacturerSpecificData;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public byte[] getManufacturerSpecificData(int manufacturerId) {
/* 114 */     if (this.manufacturerSpecificData == null) {
/* 115 */       return null;
/*     */     }
/* 117 */     return (byte[])this.manufacturerSpecificData.get(manufacturerId);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Map<ParcelUuid, byte[]> getServiceData() {
/* 125 */     return this.serviceData;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public byte[] getServiceData(@NonNull ParcelUuid serviceDataUuid) {
/* 135 */     if (serviceDataUuid == null || this.serviceData == null) {
/* 136 */       return null;
/*     */     }
/* 138 */     return this.serviceData.get(serviceDataUuid);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getTxPowerLevel() {
/* 149 */     return this.txPowerLevel;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public String getDeviceName() {
/* 157 */     return this.deviceName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public byte[] getBytes() {
/* 165 */     return this.bytes;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private ScanRecord(@Nullable List<ParcelUuid> serviceUuids, @Nullable SparseArray<byte[]> manufacturerData, @Nullable Map<ParcelUuid, byte[]> serviceData, int advertiseFlags, int txPowerLevel, String localName, byte[] bytes) {
/* 173 */     this.serviceUuids = serviceUuids;
/* 174 */     this.manufacturerSpecificData = manufacturerData;
/* 175 */     this.serviceData = serviceData;
/* 176 */     this.deviceName = localName;
/* 177 */     this.advertiseFlags = advertiseFlags;
/* 178 */     this.txPowerLevel = txPowerLevel;
/* 179 */     this.bytes = bytes;
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
/*     */   @Nullable
/*     */   static ScanRecord parseFromBytes(@Nullable byte[] scanRecord) {
/* 194 */     if (scanRecord == null) {
/* 195 */       return null;
/*     */     }
/*     */     
/* 198 */     int currentPos = 0;
/* 199 */     int advertiseFlag = -1;
/* 200 */     int txPowerLevel = Integer.MIN_VALUE;
/* 201 */     String localName = null;
/* 202 */     List<ParcelUuid> serviceUuids = null;
/* 203 */     SparseArray<byte[]> manufacturerData = null;
/* 204 */     Map<ParcelUuid, byte[]> serviceData = null;
/*     */     
/*     */     try {
/* 207 */       while (currentPos < scanRecord.length) {
/*     */         int serviceUuidLength; byte[] serviceDataUuidBytes; ParcelUuid serviceDataUuid; byte[] serviceDataArray; int manufacturerId; byte[] manufacturerDataBytes;
/* 209 */         int length = scanRecord[currentPos++] & 0xFF;
/* 210 */         if (length == 0) {
/*     */           break;
/*     */         }
/*     */         
/* 214 */         int dataLength = length - 1;
/*     */         
/* 216 */         int fieldType = scanRecord[currentPos++] & 0xFF;
/* 217 */         switch (fieldType) {
/*     */           case 1:
/* 219 */             advertiseFlag = scanRecord[currentPos] & 0xFF;
/*     */             break;
/*     */           case 2:
/*     */           case 3:
/* 223 */             if (serviceUuids == null)
/* 224 */               serviceUuids = new ArrayList<>(); 
/* 225 */             parseServiceUuid(scanRecord, currentPos, dataLength, 2, serviceUuids);
/*     */             break;
/*     */           
/*     */           case 4:
/*     */           case 5:
/* 230 */             if (serviceUuids == null)
/* 231 */               serviceUuids = new ArrayList<>(); 
/* 232 */             parseServiceUuid(scanRecord, currentPos, dataLength, 4, serviceUuids);
/*     */             break;
/*     */           
/*     */           case 6:
/*     */           case 7:
/* 237 */             if (serviceUuids == null)
/* 238 */               serviceUuids = new ArrayList<>(); 
/* 239 */             parseServiceUuid(scanRecord, currentPos, dataLength, 16, serviceUuids);
/*     */             break;
/*     */ 
/*     */           
/*     */           case 8:
/*     */           case 9:
/* 245 */             localName = new String(extractBytes(scanRecord, currentPos, dataLength));
/*     */             break;
/*     */           case 10:
/* 248 */             txPowerLevel = scanRecord[currentPos];
/*     */             break;
/*     */           case 22:
/*     */           case 32:
/*     */           case 33:
/* 253 */             serviceUuidLength = 2;
/* 254 */             if (fieldType == 32) {
/* 255 */               serviceUuidLength = 4;
/* 256 */             } else if (fieldType == 33) {
/* 257 */               serviceUuidLength = 16;
/*     */             } 
/*     */             
/* 260 */             serviceDataUuidBytes = extractBytes(scanRecord, currentPos, serviceUuidLength);
/*     */             
/* 262 */             serviceDataUuid = BluetoothUuid.parseUuidFrom(serviceDataUuidBytes);
/*     */             
/* 264 */             serviceDataArray = extractBytes(scanRecord, currentPos + serviceUuidLength, dataLength - serviceUuidLength);
/*     */             
/* 266 */             if (serviceData == null)
/* 267 */               serviceData = (Map)new HashMap<>(); 
/* 268 */             serviceData.put(serviceDataUuid, serviceDataArray);
/*     */             break;
/*     */ 
/*     */           
/*     */           case 255:
/* 273 */             manufacturerId = ((scanRecord[currentPos + 1] & 0xFF) << 8) + (scanRecord[currentPos] & 0xFF);
/*     */             
/* 275 */             manufacturerDataBytes = extractBytes(scanRecord, currentPos + 2, dataLength - 2);
/*     */             
/* 277 */             if (manufacturerData == null)
/* 278 */               manufacturerData = new SparseArray(); 
/* 279 */             manufacturerData.put(manufacturerId, manufacturerDataBytes);
/*     */             break;
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/* 285 */         currentPos += dataLength;
/*     */       } 
/*     */       
/* 288 */       return new ScanRecord(serviceUuids, manufacturerData, serviceData, advertiseFlag, txPowerLevel, localName, scanRecord);
/*     */     }
/* 290 */     catch (Exception e) {
/* 291 */       Log.e("ScanRecord", "unable to parse scan record: " + Arrays.toString(scanRecord));
/*     */ 
/*     */       
/* 294 */       return new ScanRecord(null, null, null, -1, -2147483648, null, scanRecord);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 301 */     if (this == obj) {
/* 302 */       return true;
/*     */     }
/* 304 */     if (obj == null || getClass() != obj.getClass()) {
/* 305 */       return false;
/*     */     }
/* 307 */     ScanRecord other = (ScanRecord)obj;
/* 308 */     return Arrays.equals(this.bytes, other.bytes);
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 313 */     return "ScanRecord [advertiseFlags=" + this.advertiseFlags + ", serviceUuids=" + this.serviceUuids + ", manufacturerSpecificData=" + 
/* 314 */       BluetoothLeUtils.toString(this.manufacturerSpecificData) + ", serviceData=" + 
/* 315 */       BluetoothLeUtils.<ParcelUuid>toString(this.serviceData) + ", txPowerLevel=" + this.txPowerLevel + ", deviceName=" + this.deviceName + "]";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int parseServiceUuid(@NonNull byte[] scanRecord, int currentPos, int dataLength, int uuidLength, @NonNull List<ParcelUuid> serviceUuids) {
/* 325 */     while (dataLength > 0) {
/* 326 */       byte[] uuidBytes = extractBytes(scanRecord, currentPos, uuidLength);
/*     */       
/* 328 */       serviceUuids.add(BluetoothUuid.parseUuidFrom(uuidBytes));
/* 329 */       dataLength -= uuidLength;
/* 330 */       currentPos += uuidLength;
/*     */     } 
/* 332 */     return currentPos;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static byte[] extractBytes(@NonNull byte[] scanRecord, int start, int length) {
/* 338 */     byte[] bytes = new byte[length];
/* 339 */     System.arraycopy(scanRecord, start, bytes, 0, length);
/* 340 */     return bytes;
/*     */   }
/*     */ }


/* Location:              C:\Users\Admin\Downloads\jd-gui-windows-1.6.6\classes.jar!\no\nordicsemi\android\support\v18\scanner\ScanRecord.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */