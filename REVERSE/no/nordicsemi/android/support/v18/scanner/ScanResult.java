/*     */ package no.nordicsemi.android.support.v18.scanner;
/*     */ 
/*     */ import android.bluetooth.BluetoothDevice;
/*     */ import android.os.Parcel;
/*     */ import android.os.Parcelable;
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
/*     */ public final class ScanResult
/*     */   implements Parcelable
/*     */ {
/*     */   public static final int DATA_COMPLETE = 0;
/*     */   public static final int DATA_TRUNCATED = 2;
/*     */   public static final int PHY_UNUSED = 0;
/*     */   public static final int SID_NOT_PRESENT = 255;
/*     */   public static final int TX_POWER_NOT_PRESENT = 127;
/*     */   public static final int PERIODIC_INTERVAL_NOT_PRESENT = 0;
/*     */   static final int ET_LEGACY_MASK = 16;
/*     */   static final int ET_CONNECTABLE_MASK = 1;
/*     */   @NonNull
/*     */   private final BluetoothDevice device;
/*     */   @Nullable
/*     */   private ScanRecord scanRecord;
/*     */   private final int rssi;
/*     */   private final long timestampNanos;
/*     */   private final int eventType;
/*     */   private final int primaryPhy;
/*     */   private final int secondaryPhy;
/*     */   private final int advertisingSid;
/*     */   private final int txPower;
/*     */   private final int periodicAdvertisingInterval;
/*     */   
/*     */   public ScanResult(@NonNull BluetoothDevice device, @Nullable ScanRecord scanRecord, int rssi, long timestampNanos) {
/* 113 */     this.device = device;
/* 114 */     this.scanRecord = scanRecord;
/* 115 */     this.rssi = rssi;
/* 116 */     this.timestampNanos = timestampNanos;
/* 117 */     this.eventType = 17;
/* 118 */     this.primaryPhy = 1;
/* 119 */     this.secondaryPhy = 0;
/* 120 */     this.advertisingSid = 255;
/* 121 */     this.txPower = 127;
/* 122 */     this.periodicAdvertisingInterval = 0;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ScanResult(@NonNull BluetoothDevice device, int eventType, int primaryPhy, int secondaryPhy, int advertisingSid, int txPower, int rssi, int periodicAdvertisingInterval, @Nullable ScanRecord scanRecord, long timestampNanos) {
/* 144 */     this.device = device;
/* 145 */     this.eventType = eventType;
/* 146 */     this.primaryPhy = primaryPhy;
/* 147 */     this.secondaryPhy = secondaryPhy;
/* 148 */     this.advertisingSid = advertisingSid;
/* 149 */     this.txPower = txPower;
/* 150 */     this.rssi = rssi;
/* 151 */     this.periodicAdvertisingInterval = periodicAdvertisingInterval;
/* 152 */     this.scanRecord = scanRecord;
/* 153 */     this.timestampNanos = timestampNanos;
/*     */   }
/*     */   
/*     */   private ScanResult(Parcel in) {
/* 157 */     this.device = (BluetoothDevice)BluetoothDevice.CREATOR.createFromParcel(in);
/* 158 */     if (in.readInt() == 1) {
/* 159 */       this.scanRecord = ScanRecord.parseFromBytes(in.createByteArray());
/*     */     }
/* 161 */     this.rssi = in.readInt();
/* 162 */     this.timestampNanos = in.readLong();
/* 163 */     this.eventType = in.readInt();
/* 164 */     this.primaryPhy = in.readInt();
/* 165 */     this.secondaryPhy = in.readInt();
/* 166 */     this.advertisingSid = in.readInt();
/* 167 */     this.txPower = in.readInt();
/* 168 */     this.periodicAdvertisingInterval = in.readInt();
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeToParcel(Parcel dest, int flags) {
/* 173 */     this.device.writeToParcel(dest, flags);
/* 174 */     if (this.scanRecord != null) {
/* 175 */       dest.writeInt(1);
/* 176 */       dest.writeByteArray(this.scanRecord.getBytes());
/*     */     } else {
/* 178 */       dest.writeInt(0);
/*     */     } 
/* 180 */     dest.writeInt(this.rssi);
/* 181 */     dest.writeLong(this.timestampNanos);
/* 182 */     dest.writeInt(this.eventType);
/* 183 */     dest.writeInt(this.primaryPhy);
/* 184 */     dest.writeInt(this.secondaryPhy);
/* 185 */     dest.writeInt(this.advertisingSid);
/* 186 */     dest.writeInt(this.txPower);
/* 187 */     dest.writeInt(this.periodicAdvertisingInterval);
/*     */   }
/*     */ 
/*     */   
/*     */   public int describeContents() {
/* 192 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NonNull
/*     */   public BluetoothDevice getDevice() {
/* 200 */     return this.device;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public ScanRecord getScanRecord() {
/* 208 */     return this.scanRecord;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRssi() {
/* 215 */     return this.rssi;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getTimestampNanos() {
/* 222 */     return this.timestampNanos;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isLegacy() {
/* 231 */     return ((this.eventType & 0x10) != 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isConnectable() {
/* 238 */     return ((this.eventType & 0x1) != 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getDataStatus() {
/* 248 */     return this.eventType >> 5 & 0x3;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getPrimaryPhy() {
/* 257 */     return this.primaryPhy;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSecondaryPhy() {
/* 267 */     return this.secondaryPhy;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getAdvertisingSid() {
/* 274 */     return this.advertisingSid;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getTxPower() {
/* 281 */     return this.txPower;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getPeriodicAdvertisingInterval() {
/* 290 */     return this.periodicAdvertisingInterval;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 295 */     return Objects.hash(new Object[] { this.device, Integer.valueOf(this.rssi), this.scanRecord, Long.valueOf(this.timestampNanos), 
/* 296 */           Integer.valueOf(this.eventType), Integer.valueOf(this.primaryPhy), Integer.valueOf(this.secondaryPhy), 
/* 297 */           Integer.valueOf(this.advertisingSid), Integer.valueOf(this.txPower), 
/* 298 */           Integer.valueOf(this.periodicAdvertisingInterval) });
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 303 */     if (this == obj) {
/* 304 */       return true;
/*     */     }
/* 306 */     if (obj == null || getClass() != obj.getClass()) {
/* 307 */       return false;
/*     */     }
/* 309 */     ScanResult other = (ScanResult)obj;
/* 310 */     return (Objects.equals(this.device, other.device) && this.rssi == other.rssi && 
/* 311 */       Objects.equals(this.scanRecord, other.scanRecord) && this.timestampNanos == other.timestampNanos && this.eventType == other.eventType && this.primaryPhy == other.primaryPhy && this.secondaryPhy == other.secondaryPhy && this.advertisingSid == other.advertisingSid && this.txPower == other.txPower && this.periodicAdvertisingInterval == other.periodicAdvertisingInterval);
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
/*     */   public String toString() {
/* 323 */     return "ScanResult{device=" + this.device + ", scanRecord=" + 
/* 324 */       Objects.toString(this.scanRecord) + ", rssi=" + this.rssi + ", timestampNanos=" + this.timestampNanos + ", eventType=" + this.eventType + ", primaryPhy=" + this.primaryPhy + ", secondaryPhy=" + this.secondaryPhy + ", advertisingSid=" + this.advertisingSid + ", txPower=" + this.txPower + ", periodicAdvertisingInterval=" + this.periodicAdvertisingInterval + '}';
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 331 */   public static final Parcelable.Creator<ScanResult> CREATOR = new Parcelable.Creator<ScanResult>()
/*     */     {
/*     */       public ScanResult createFromParcel(Parcel source) {
/* 334 */         return new ScanResult(source);
/*     */       }
/*     */ 
/*     */       
/*     */       public ScanResult[] newArray(int size) {
/* 339 */         return new ScanResult[size];
/*     */       }
/*     */     };
/*     */ }


/* Location:              C:\Users\Admin\Downloads\jd-gui-windows-1.6.6\classes.jar!\no\nordicsemi\android\support\v18\scanner\ScanResult.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */