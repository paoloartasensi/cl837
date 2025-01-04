package no.nordicsemi.android.support.v18.scanner;

import android.os.ParcelUuid;
import android.util.Log;
import android.util.SparseArray;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class ScanRecord {
   private static final String TAG = "ScanRecord";
   private static final int DATA_TYPE_FLAGS = 1;
   private static final int DATA_TYPE_SERVICE_UUIDS_16_BIT_PARTIAL = 2;
   private static final int DATA_TYPE_SERVICE_UUIDS_16_BIT_COMPLETE = 3;
   private static final int DATA_TYPE_SERVICE_UUIDS_32_BIT_PARTIAL = 4;
   private static final int DATA_TYPE_SERVICE_UUIDS_32_BIT_COMPLETE = 5;
   private static final int DATA_TYPE_SERVICE_UUIDS_128_BIT_PARTIAL = 6;
   private static final int DATA_TYPE_SERVICE_UUIDS_128_BIT_COMPLETE = 7;
   private static final int DATA_TYPE_LOCAL_NAME_SHORT = 8;
   private static final int DATA_TYPE_LOCAL_NAME_COMPLETE = 9;
   private static final int DATA_TYPE_TX_POWER_LEVEL = 10;
   private static final int DATA_TYPE_SERVICE_DATA_16_BIT = 22;
   private static final int DATA_TYPE_SERVICE_DATA_32_BIT = 32;
   private static final int DATA_TYPE_SERVICE_DATA_128_BIT = 33;
   private static final int DATA_TYPE_MANUFACTURER_SPECIFIC_DATA = 255;
   private final int advertiseFlags;
   @Nullable
   private final List<ParcelUuid> serviceUuids;
   @Nullable
   private final SparseArray<byte[]> manufacturerSpecificData;
   @Nullable
   private final Map<ParcelUuid, byte[]> serviceData;
   private final int txPowerLevel;
   private final String deviceName;
   private final byte[] bytes;

   public int getAdvertiseFlags() {
      return this.advertiseFlags;
   }

   @Nullable
   public List<ParcelUuid> getServiceUuids() {
      return this.serviceUuids;
   }

   @Nullable
   public SparseArray<byte[]> getManufacturerSpecificData() {
      return this.manufacturerSpecificData;
   }

   @Nullable
   public byte[] getManufacturerSpecificData(int manufacturerId) {
      return this.manufacturerSpecificData == null ? null : (byte[])this.manufacturerSpecificData.get(manufacturerId);
   }

   @Nullable
   public Map<ParcelUuid, byte[]> getServiceData() {
      return this.serviceData;
   }

   @Nullable
   public byte[] getServiceData(@NonNull ParcelUuid serviceDataUuid) {
      return serviceDataUuid != null && this.serviceData != null ? (byte[])this.serviceData.get(serviceDataUuid) : null;
   }

   public int getTxPowerLevel() {
      return this.txPowerLevel;
   }

   @Nullable
   public String getDeviceName() {
      return this.deviceName;
   }

   @Nullable
   public byte[] getBytes() {
      return this.bytes;
   }

   private ScanRecord(@Nullable List<ParcelUuid> serviceUuids, @Nullable SparseArray<byte[]> manufacturerData, @Nullable Map<ParcelUuid, byte[]> serviceData, int advertiseFlags, int txPowerLevel, String localName, byte[] bytes) {
      this.serviceUuids = serviceUuids;
      this.manufacturerSpecificData = manufacturerData;
      this.serviceData = serviceData;
      this.deviceName = localName;
      this.advertiseFlags = advertiseFlags;
      this.txPowerLevel = txPowerLevel;
      this.bytes = bytes;
   }

   @Nullable
   static ScanRecord parseFromBytes(@Nullable byte[] scanRecord) {
      if (scanRecord == null) {
         return null;
      } else {
         int currentPos = 0;
         int advertiseFlag = -1;
         int txPowerLevel = Integer.MIN_VALUE;
         String localName = null;
         List<ParcelUuid> serviceUuids = null;
         SparseArray<byte[]> manufacturerData = null;
         HashMap serviceData = null;

         try {
            int dataLength;
            for(; currentPos < scanRecord.length; currentPos += dataLength) {
               int length = scanRecord[currentPos++] & 255;
               if (length == 0) {
                  break;
               }

               dataLength = length - 1;
               int fieldType = scanRecord[currentPos++] & 255;
               switch(fieldType) {
               case 1:
                  advertiseFlag = scanRecord[currentPos] & 255;
                  break;
               case 2:
               case 3:
                  if (serviceUuids == null) {
                     serviceUuids = new ArrayList();
                  }

                  parseServiceUuid(scanRecord, currentPos, dataLength, 2, serviceUuids);
                  break;
               case 4:
               case 5:
                  if (serviceUuids == null) {
                     serviceUuids = new ArrayList();
                  }

                  parseServiceUuid(scanRecord, currentPos, dataLength, 4, serviceUuids);
                  break;
               case 6:
               case 7:
                  if (serviceUuids == null) {
                     serviceUuids = new ArrayList();
                  }

                  parseServiceUuid(scanRecord, currentPos, dataLength, 16, serviceUuids);
                  break;
               case 8:
               case 9:
                  localName = new String(extractBytes(scanRecord, currentPos, dataLength));
                  break;
               case 10:
                  txPowerLevel = scanRecord[currentPos];
                  break;
               case 22:
               case 32:
               case 33:
                  int serviceUuidLength = 2;
                  if (fieldType == 32) {
                     serviceUuidLength = 4;
                  } else if (fieldType == 33) {
                     serviceUuidLength = 16;
                  }

                  byte[] serviceDataUuidBytes = extractBytes(scanRecord, currentPos, serviceUuidLength);
                  ParcelUuid serviceDataUuid = BluetoothUuid.parseUuidFrom(serviceDataUuidBytes);
                  byte[] serviceDataArray = extractBytes(scanRecord, currentPos + serviceUuidLength, dataLength - serviceUuidLength);
                  if (serviceData == null) {
                     serviceData = new HashMap();
                  }

                  serviceData.put(serviceDataUuid, serviceDataArray);
                  break;
               case 255:
                  int manufacturerId = ((scanRecord[currentPos + 1] & 255) << 8) + (scanRecord[currentPos] & 255);
                  byte[] manufacturerDataBytes = extractBytes(scanRecord, currentPos + 2, dataLength - 2);
                  if (manufacturerData == null) {
                     manufacturerData = new SparseArray();
                  }

                  manufacturerData.put(manufacturerId, manufacturerDataBytes);
               }
            }

            return new ScanRecord(serviceUuids, manufacturerData, serviceData, advertiseFlag, txPowerLevel, localName, scanRecord);
         } catch (Exception var17) {
            Log.e("ScanRecord", "unable to parse scan record: " + Arrays.toString(scanRecord));
            return new ScanRecord((List)null, (SparseArray)null, (Map)null, -1, Integer.MIN_VALUE, (String)null, scanRecord);
         }
      }
   }

   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      } else if (obj != null && this.getClass() == obj.getClass()) {
         ScanRecord other = (ScanRecord)obj;
         return Arrays.equals(this.bytes, other.bytes);
      } else {
         return false;
      }
   }

   public String toString() {
      return "ScanRecord [advertiseFlags=" + this.advertiseFlags + ", serviceUuids=" + this.serviceUuids + ", manufacturerSpecificData=" + BluetoothLeUtils.toString(this.manufacturerSpecificData) + ", serviceData=" + BluetoothLeUtils.toString(this.serviceData) + ", txPowerLevel=" + this.txPowerLevel + ", deviceName=" + this.deviceName + "]";
   }

   private static int parseServiceUuid(@NonNull byte[] scanRecord, int currentPos, int dataLength, int uuidLength, @NonNull List<ParcelUuid> serviceUuids) {
      while(dataLength > 0) {
         byte[] uuidBytes = extractBytes(scanRecord, currentPos, uuidLength);
         serviceUuids.add(BluetoothUuid.parseUuidFrom(uuidBytes));
         dataLength -= uuidLength;
         currentPos += uuidLength;
      }

      return currentPos;
   }

   private static byte[] extractBytes(@NonNull byte[] scanRecord, int start, int length) {
      byte[] bytes = new byte[length];
      System.arraycopy(scanRecord, start, bytes, 0, length);
      return bytes;
   }
}
