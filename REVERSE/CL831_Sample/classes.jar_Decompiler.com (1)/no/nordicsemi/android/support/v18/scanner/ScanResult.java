package no.nordicsemi.android.support.v18.scanner;

import android.bluetooth.BluetoothDevice;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public final class ScanResult implements Parcelable {
   public static final int DATA_COMPLETE = 0;
   public static final int DATA_TRUNCATED = 2;
   public static final int PHY_UNUSED = 0;
   public static final int SID_NOT_PRESENT = 255;
   public static final int TX_POWER_NOT_PRESENT = 127;
   public static final int PERIODIC_INTERVAL_NOT_PRESENT = 0;
   static final int ET_LEGACY_MASK = 16;
   static final int ET_CONNECTABLE_MASK = 1;
   @NonNull
   private final BluetoothDevice device;
   @Nullable
   private ScanRecord scanRecord;
   private final int rssi;
   private final long timestampNanos;
   private final int eventType;
   private final int primaryPhy;
   private final int secondaryPhy;
   private final int advertisingSid;
   private final int txPower;
   private final int periodicAdvertisingInterval;
   public static final Creator<ScanResult> CREATOR = new Creator<ScanResult>() {
      public ScanResult createFromParcel(Parcel source) {
         return new ScanResult(source);
      }

      public ScanResult[] newArray(int size) {
         return new ScanResult[size];
      }
   };

   /** @deprecated */
   public ScanResult(@NonNull BluetoothDevice device, @Nullable ScanRecord scanRecord, int rssi, long timestampNanos) {
      this.device = device;
      this.scanRecord = scanRecord;
      this.rssi = rssi;
      this.timestampNanos = timestampNanos;
      this.eventType = 17;
      this.primaryPhy = 1;
      this.secondaryPhy = 0;
      this.advertisingSid = 255;
      this.txPower = 127;
      this.periodicAdvertisingInterval = 0;
   }

   public ScanResult(@NonNull BluetoothDevice device, int eventType, int primaryPhy, int secondaryPhy, int advertisingSid, int txPower, int rssi, int periodicAdvertisingInterval, @Nullable ScanRecord scanRecord, long timestampNanos) {
      this.device = device;
      this.eventType = eventType;
      this.primaryPhy = primaryPhy;
      this.secondaryPhy = secondaryPhy;
      this.advertisingSid = advertisingSid;
      this.txPower = txPower;
      this.rssi = rssi;
      this.periodicAdvertisingInterval = periodicAdvertisingInterval;
      this.scanRecord = scanRecord;
      this.timestampNanos = timestampNanos;
   }

   private ScanResult(Parcel in) {
      this.device = (BluetoothDevice)BluetoothDevice.CREATOR.createFromParcel(in);
      if (in.readInt() == 1) {
         this.scanRecord = ScanRecord.parseFromBytes(in.createByteArray());
      }

      this.rssi = in.readInt();
      this.timestampNanos = in.readLong();
      this.eventType = in.readInt();
      this.primaryPhy = in.readInt();
      this.secondaryPhy = in.readInt();
      this.advertisingSid = in.readInt();
      this.txPower = in.readInt();
      this.periodicAdvertisingInterval = in.readInt();
   }

   public void writeToParcel(Parcel dest, int flags) {
      this.device.writeToParcel(dest, flags);
      if (this.scanRecord != null) {
         dest.writeInt(1);
         dest.writeByteArray(this.scanRecord.getBytes());
      } else {
         dest.writeInt(0);
      }

      dest.writeInt(this.rssi);
      dest.writeLong(this.timestampNanos);
      dest.writeInt(this.eventType);
      dest.writeInt(this.primaryPhy);
      dest.writeInt(this.secondaryPhy);
      dest.writeInt(this.advertisingSid);
      dest.writeInt(this.txPower);
      dest.writeInt(this.periodicAdvertisingInterval);
   }

   public int describeContents() {
      return 0;
   }

   @NonNull
   public BluetoothDevice getDevice() {
      return this.device;
   }

   @Nullable
   public ScanRecord getScanRecord() {
      return this.scanRecord;
   }

   public int getRssi() {
      return this.rssi;
   }

   public long getTimestampNanos() {
      return this.timestampNanos;
   }

   public boolean isLegacy() {
      return (this.eventType & 16) != 0;
   }

   public boolean isConnectable() {
      return (this.eventType & 1) != 0;
   }

   public int getDataStatus() {
      return this.eventType >> 5 & 3;
   }

   public int getPrimaryPhy() {
      return this.primaryPhy;
   }

   public int getSecondaryPhy() {
      return this.secondaryPhy;
   }

   public int getAdvertisingSid() {
      return this.advertisingSid;
   }

   public int getTxPower() {
      return this.txPower;
   }

   public int getPeriodicAdvertisingInterval() {
      return this.periodicAdvertisingInterval;
   }

   public int hashCode() {
      return Objects.hash(this.device, this.rssi, this.scanRecord, this.timestampNanos, this.eventType, this.primaryPhy, this.secondaryPhy, this.advertisingSid, this.txPower, this.periodicAdvertisingInterval);
   }

   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      } else if (obj != null && this.getClass() == obj.getClass()) {
         ScanResult other = (ScanResult)obj;
         return Objects.equals(this.device, other.device) && this.rssi == other.rssi && Objects.equals(this.scanRecord, other.scanRecord) && this.timestampNanos == other.timestampNanos && this.eventType == other.eventType && this.primaryPhy == other.primaryPhy && this.secondaryPhy == other.secondaryPhy && this.advertisingSid == other.advertisingSid && this.txPower == other.txPower && this.periodicAdvertisingInterval == other.periodicAdvertisingInterval;
      } else {
         return false;
      }
   }

   public String toString() {
      return "ScanResult{device=" + this.device + ", scanRecord=" + Objects.toString(this.scanRecord) + ", rssi=" + this.rssi + ", timestampNanos=" + this.timestampNanos + ", eventType=" + this.eventType + ", primaryPhy=" + this.primaryPhy + ", secondaryPhy=" + this.secondaryPhy + ", advertisingSid=" + this.advertisingSid + ", txPower=" + this.txPower + ", periodicAdvertisingInterval=" + this.periodicAdvertisingInterval + '}';
   }

   // $FF: synthetic method
   ScanResult(Parcel x0, Object x1) {
      this(x0);
   }
}
