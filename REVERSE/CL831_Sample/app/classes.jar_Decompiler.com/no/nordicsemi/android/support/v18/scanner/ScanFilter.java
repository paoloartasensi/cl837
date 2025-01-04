package no.nordicsemi.android.support.v18.scanner;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.os.Parcel;
import android.os.ParcelUuid;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

public final class ScanFilter implements Parcelable {
   @Nullable
   private final String deviceName;
   @Nullable
   private final String deviceAddress;
   @Nullable
   private final ParcelUuid serviceUuid;
   @Nullable
   private final ParcelUuid serviceUuidMask;
   @Nullable
   private final ParcelUuid serviceDataUuid;
   @Nullable
   private final byte[] serviceData;
   @Nullable
   private final byte[] serviceDataMask;
   private final int manufacturerId;
   @Nullable
   private final byte[] manufacturerData;
   @Nullable
   private final byte[] manufacturerDataMask;
   private static final ScanFilter EMPTY = (new ScanFilter.Builder()).build();
   public static final Creator<ScanFilter> CREATOR = new Creator<ScanFilter>() {
      public ScanFilter[] newArray(int size) {
         return new ScanFilter[size];
      }

      public ScanFilter createFromParcel(Parcel in) {
         ScanFilter.Builder builder = new ScanFilter.Builder();
         if (in.readInt() == 1) {
            builder.setDeviceName(in.readString());
         }

         if (in.readInt() == 1) {
            builder.setDeviceAddress(in.readString());
         }

         ParcelUuid serviceDataUuid;
         if (in.readInt() == 1) {
            serviceDataUuid = (ParcelUuid)in.readParcelable(ParcelUuid.class.getClassLoader());
            builder.setServiceUuid(serviceDataUuid);
            if (in.readInt() == 1) {
               ParcelUuid uuidMask = (ParcelUuid)in.readParcelable(ParcelUuid.class.getClassLoader());
               builder.setServiceUuid(serviceDataUuid, uuidMask);
            }
         }

         byte[] manufacturerData;
         int manufacturerDataMaskLength;
         byte[] manufacturerDataMask;
         int manufacturerDataLength;
         if (in.readInt() == 1) {
            serviceDataUuid = (ParcelUuid)in.readParcelable(ParcelUuid.class.getClassLoader());
            if (in.readInt() == 1) {
               manufacturerDataLength = in.readInt();
               manufacturerData = new byte[manufacturerDataLength];
               in.readByteArray(manufacturerData);
               if (in.readInt() == 0) {
                  builder.setServiceData(serviceDataUuid, manufacturerData);
               } else {
                  manufacturerDataMaskLength = in.readInt();
                  manufacturerDataMask = new byte[manufacturerDataMaskLength];
                  in.readByteArray(manufacturerDataMask);
                  builder.setServiceData(serviceDataUuid, manufacturerData, manufacturerDataMask);
               }
            }
         }

         int manufacturerId = in.readInt();
         if (in.readInt() == 1) {
            manufacturerDataLength = in.readInt();
            manufacturerData = new byte[manufacturerDataLength];
            in.readByteArray(manufacturerData);
            if (in.readInt() == 0) {
               builder.setManufacturerData(manufacturerId, manufacturerData);
            } else {
               manufacturerDataMaskLength = in.readInt();
               manufacturerDataMask = new byte[manufacturerDataMaskLength];
               in.readByteArray(manufacturerDataMask);
               builder.setManufacturerData(manufacturerId, manufacturerData, manufacturerDataMask);
            }
         }

         return builder.build();
      }
   };

   private ScanFilter(@Nullable String name, @Nullable String deviceAddress, @Nullable ParcelUuid uuid, @Nullable ParcelUuid uuidMask, @Nullable ParcelUuid serviceDataUuid, @Nullable byte[] serviceData, @Nullable byte[] serviceDataMask, int manufacturerId, @Nullable byte[] manufacturerData, @Nullable byte[] manufacturerDataMask) {
      this.deviceName = name;
      this.serviceUuid = uuid;
      this.serviceUuidMask = uuidMask;
      this.deviceAddress = deviceAddress;
      this.serviceDataUuid = serviceDataUuid;
      this.serviceData = serviceData;
      this.serviceDataMask = serviceDataMask;
      this.manufacturerId = manufacturerId;
      this.manufacturerData = manufacturerData;
      this.manufacturerDataMask = manufacturerDataMask;
   }

   public int describeContents() {
      return 0;
   }

   public void writeToParcel(Parcel dest, int flags) {
      dest.writeInt(this.deviceName == null ? 0 : 1);
      if (this.deviceName != null) {
         dest.writeString(this.deviceName);
      }

      dest.writeInt(this.deviceAddress == null ? 0 : 1);
      if (this.deviceAddress != null) {
         dest.writeString(this.deviceAddress);
      }

      dest.writeInt(this.serviceUuid == null ? 0 : 1);
      if (this.serviceUuid != null) {
         dest.writeParcelable(this.serviceUuid, flags);
         dest.writeInt(this.serviceUuidMask == null ? 0 : 1);
         if (this.serviceUuidMask != null) {
            dest.writeParcelable(this.serviceUuidMask, flags);
         }
      }

      dest.writeInt(this.serviceDataUuid == null ? 0 : 1);
      if (this.serviceDataUuid != null) {
         dest.writeParcelable(this.serviceDataUuid, flags);
         dest.writeInt(this.serviceData == null ? 0 : 1);
         if (this.serviceData != null) {
            dest.writeInt(this.serviceData.length);
            dest.writeByteArray(this.serviceData);
            dest.writeInt(this.serviceDataMask == null ? 0 : 1);
            if (this.serviceDataMask != null) {
               dest.writeInt(this.serviceDataMask.length);
               dest.writeByteArray(this.serviceDataMask);
            }
         }
      }

      dest.writeInt(this.manufacturerId);
      dest.writeInt(this.manufacturerData == null ? 0 : 1);
      if (this.manufacturerData != null) {
         dest.writeInt(this.manufacturerData.length);
         dest.writeByteArray(this.manufacturerData);
         dest.writeInt(this.manufacturerDataMask == null ? 0 : 1);
         if (this.manufacturerDataMask != null) {
            dest.writeInt(this.manufacturerDataMask.length);
            dest.writeByteArray(this.manufacturerDataMask);
         }
      }

   }

   @Nullable
   public String getDeviceName() {
      return this.deviceName;
   }

   @Nullable
   public ParcelUuid getServiceUuid() {
      return this.serviceUuid;
   }

   @Nullable
   public ParcelUuid getServiceUuidMask() {
      return this.serviceUuidMask;
   }

   @Nullable
   public String getDeviceAddress() {
      return this.deviceAddress;
   }

   @Nullable
   public byte[] getServiceData() {
      return this.serviceData;
   }

   @Nullable
   public byte[] getServiceDataMask() {
      return this.serviceDataMask;
   }

   @Nullable
   public ParcelUuid getServiceDataUuid() {
      return this.serviceDataUuid;
   }

   public int getManufacturerId() {
      return this.manufacturerId;
   }

   @Nullable
   public byte[] getManufacturerData() {
      return this.manufacturerData;
   }

   @Nullable
   public byte[] getManufacturerDataMask() {
      return this.manufacturerDataMask;
   }

   public boolean matches(@Nullable ScanResult scanResult) {
      if (scanResult == null) {
         return false;
      } else {
         BluetoothDevice device = scanResult.getDevice();
         if (this.deviceAddress != null && !this.deviceAddress.equals(device.getAddress())) {
            return false;
         } else {
            ScanRecord scanRecord = scanResult.getScanRecord();
            if (scanRecord != null || this.deviceName == null && this.serviceUuid == null && this.manufacturerData == null && this.serviceData == null) {
               if (this.deviceName != null && !this.deviceName.equals(scanRecord.getDeviceName())) {
                  return false;
               } else if (this.serviceUuid != null && !matchesServiceUuids(this.serviceUuid, this.serviceUuidMask, scanRecord.getServiceUuids())) {
                  return false;
               } else if (this.serviceDataUuid != null && scanRecord != null && !this.matchesPartialData(this.serviceData, this.serviceDataMask, scanRecord.getServiceData(this.serviceDataUuid))) {
                  return false;
               } else {
                  return this.manufacturerId < 0 || scanRecord == null || this.matchesPartialData(this.manufacturerData, this.manufacturerDataMask, scanRecord.getManufacturerSpecificData(this.manufacturerId));
               }
            } else {
               return false;
            }
         }
      }
   }

   private static boolean matchesServiceUuids(@Nullable ParcelUuid uuid, @Nullable ParcelUuid parcelUuidMask, @Nullable List<ParcelUuid> uuids) {
      if (uuid == null) {
         return true;
      } else if (uuids == null) {
         return false;
      } else {
         Iterator var3 = uuids.iterator();

         ParcelUuid parcelUuid;
         UUID uuidMask;
         do {
            if (!var3.hasNext()) {
               return false;
            }

            parcelUuid = (ParcelUuid)var3.next();
            uuidMask = parcelUuidMask == null ? null : parcelUuidMask.getUuid();
         } while(!matchesServiceUuid(uuid.getUuid(), uuidMask, parcelUuid.getUuid()));

         return true;
      }
   }

   private static boolean matchesServiceUuid(@NonNull UUID uuid, @Nullable UUID mask, @NonNull UUID data) {
      if (mask == null) {
         return uuid.equals(data);
      } else if ((uuid.getLeastSignificantBits() & mask.getLeastSignificantBits()) != (data.getLeastSignificantBits() & mask.getLeastSignificantBits())) {
         return false;
      } else {
         return (uuid.getMostSignificantBits() & mask.getMostSignificantBits()) == (data.getMostSignificantBits() & mask.getMostSignificantBits());
      }
   }

   private boolean matchesPartialData(@Nullable byte[] data, @Nullable byte[] dataMask, @Nullable byte[] parsedData) {
      if (data == null) {
         return parsedData != null;
      } else if (parsedData != null && parsedData.length >= data.length) {
         int i;
         if (dataMask == null) {
            for(i = 0; i < data.length; ++i) {
               if (parsedData[i] != data[i]) {
                  return false;
               }
            }

            return true;
         } else {
            for(i = 0; i < data.length; ++i) {
               if ((dataMask[i] & parsedData[i]) != (dataMask[i] & data[i])) {
                  return false;
               }
            }

            return true;
         }
      } else {
         return false;
      }
   }

   public String toString() {
      return "BluetoothLeScanFilter [deviceName=" + this.deviceName + ", deviceAddress=" + this.deviceAddress + ", mUuid=" + this.serviceUuid + ", uuidMask=" + this.serviceUuidMask + ", serviceDataUuid=" + Objects.toString(this.serviceDataUuid) + ", serviceData=" + Arrays.toString(this.serviceData) + ", serviceDataMask=" + Arrays.toString(this.serviceDataMask) + ", manufacturerId=" + this.manufacturerId + ", manufacturerData=" + Arrays.toString(this.manufacturerData) + ", manufacturerDataMask=" + Arrays.toString(this.manufacturerDataMask) + "]";
   }

   public int hashCode() {
      return Objects.hash(this.deviceName, this.deviceAddress, this.manufacturerId, Arrays.hashCode(this.manufacturerData), Arrays.hashCode(this.manufacturerDataMask), this.serviceDataUuid, Arrays.hashCode(this.serviceData), Arrays.hashCode(this.serviceDataMask), this.serviceUuid, this.serviceUuidMask);
   }

   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      } else if (obj != null && this.getClass() == obj.getClass()) {
         ScanFilter other = (ScanFilter)obj;
         return Objects.equals(this.deviceName, other.deviceName) && Objects.equals(this.deviceAddress, other.deviceAddress) && this.manufacturerId == other.manufacturerId && Objects.deepEquals(this.manufacturerData, other.manufacturerData) && Objects.deepEquals(this.manufacturerDataMask, other.manufacturerDataMask) && Objects.equals(this.serviceDataUuid, other.serviceDataUuid) && Objects.deepEquals(this.serviceData, other.serviceData) && Objects.deepEquals(this.serviceDataMask, other.serviceDataMask) && Objects.equals(this.serviceUuid, other.serviceUuid) && Objects.equals(this.serviceUuidMask, other.serviceUuidMask);
      } else {
         return false;
      }
   }

   boolean isAllFieldsEmpty() {
      return EMPTY.equals(this);
   }

   // $FF: synthetic method
   ScanFilter(String x0, String x1, ParcelUuid x2, ParcelUuid x3, ParcelUuid x4, byte[] x5, byte[] x6, int x7, byte[] x8, byte[] x9, Object x10) {
      this(x0, x1, x2, x3, x4, x5, x6, x7, x8, x9);
   }

   public static final class Builder {
      private String deviceName;
      private String deviceAddress;
      private ParcelUuid serviceUuid;
      private ParcelUuid uuidMask;
      private ParcelUuid serviceDataUuid;
      private byte[] serviceData;
      private byte[] serviceDataMask;
      private int manufacturerId = -1;
      private byte[] manufacturerData;
      private byte[] manufacturerDataMask;

      public ScanFilter.Builder setDeviceName(@Nullable String deviceName) {
         this.deviceName = deviceName;
         return this;
      }

      public ScanFilter.Builder setDeviceAddress(@Nullable String deviceAddress) {
         if (deviceAddress != null && !BluetoothAdapter.checkBluetoothAddress(deviceAddress)) {
            throw new IllegalArgumentException("invalid device address " + deviceAddress);
         } else {
            this.deviceAddress = deviceAddress;
            return this;
         }
      }

      public ScanFilter.Builder setServiceUuid(@Nullable ParcelUuid serviceUuid) {
         this.serviceUuid = serviceUuid;
         this.uuidMask = null;
         return this;
      }

      public ScanFilter.Builder setServiceUuid(@Nullable ParcelUuid serviceUuid, @Nullable ParcelUuid uuidMask) {
         if (uuidMask != null && serviceUuid == null) {
            throw new IllegalArgumentException("uuid is null while uuidMask is not null!");
         } else {
            this.serviceUuid = serviceUuid;
            this.uuidMask = uuidMask;
            return this;
         }
      }

      public ScanFilter.Builder setServiceData(@NonNull ParcelUuid serviceDataUuid, @Nullable byte[] serviceData) {
         if (serviceDataUuid == null) {
            throw new IllegalArgumentException("serviceDataUuid is null!");
         } else {
            this.serviceDataUuid = serviceDataUuid;
            this.serviceData = serviceData;
            this.serviceDataMask = null;
            return this;
         }
      }

      public ScanFilter.Builder setServiceData(@NonNull ParcelUuid serviceDataUuid, @Nullable byte[] serviceData, @Nullable byte[] serviceDataMask) {
         if (serviceDataUuid == null) {
            throw new IllegalArgumentException("serviceDataUuid is null");
         } else {
            if (serviceDataMask != null) {
               if (serviceData == null) {
                  throw new IllegalArgumentException("serviceData is null while serviceDataMask is not null");
               }

               if (serviceData.length != serviceDataMask.length) {
                  throw new IllegalArgumentException("size mismatch for service data and service data mask");
               }
            }

            this.serviceDataUuid = serviceDataUuid;
            this.serviceData = serviceData;
            this.serviceDataMask = serviceDataMask;
            return this;
         }
      }

      public ScanFilter.Builder setManufacturerData(int manufacturerId, @Nullable byte[] manufacturerData) {
         if (manufacturerData != null && manufacturerId < 0) {
            throw new IllegalArgumentException("invalid manufacture id");
         } else {
            this.manufacturerId = manufacturerId;
            this.manufacturerData = manufacturerData;
            this.manufacturerDataMask = null;
            return this;
         }
      }

      public ScanFilter.Builder setManufacturerData(int manufacturerId, @Nullable byte[] manufacturerData, @Nullable byte[] manufacturerDataMask) {
         if (manufacturerData != null && manufacturerId < 0) {
            throw new IllegalArgumentException("invalid manufacture id");
         } else {
            if (manufacturerDataMask != null) {
               if (manufacturerData == null) {
                  throw new IllegalArgumentException("manufacturerData is null while manufacturerDataMask is not null");
               }

               if (manufacturerData.length != manufacturerDataMask.length) {
                  throw new IllegalArgumentException("size mismatch for manufacturerData and manufacturerDataMask");
               }
            }

            this.manufacturerId = manufacturerId;
            this.manufacturerData = manufacturerData;
            this.manufacturerDataMask = manufacturerDataMask;
            return this;
         }
      }

      public ScanFilter build() {
         return new ScanFilter(this.deviceName, this.deviceAddress, this.serviceUuid, this.uuidMask, this.serviceDataUuid, this.serviceData, this.serviceDataMask, this.manufacturerId, this.manufacturerData, this.manufacturerDataMask);
      }
   }
}
