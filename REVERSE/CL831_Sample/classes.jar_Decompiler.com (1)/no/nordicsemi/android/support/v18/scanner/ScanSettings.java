package no.nordicsemi.android.support.v18.scanner;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import androidx.annotation.NonNull;

public final class ScanSettings implements Parcelable {
   public static final long MATCH_LOST_DEVICE_TIMEOUT_DEFAULT = 10000L;
   public static final long MATCH_LOST_TASK_INTERVAL_DEFAULT = 10000L;
   public static final int SCAN_MODE_OPPORTUNISTIC = -1;
   public static final int SCAN_MODE_LOW_POWER = 0;
   public static final int SCAN_MODE_BALANCED = 1;
   public static final int SCAN_MODE_LOW_LATENCY = 2;
   public static final int CALLBACK_TYPE_ALL_MATCHES = 1;
   public static final int CALLBACK_TYPE_FIRST_MATCH = 2;
   public static final int CALLBACK_TYPE_MATCH_LOST = 4;
   public static final int MATCH_NUM_ONE_ADVERTISEMENT = 1;
   public static final int MATCH_NUM_FEW_ADVERTISEMENT = 2;
   public static final int MATCH_NUM_MAX_ADVERTISEMENT = 3;
   public static final int MATCH_MODE_AGGRESSIVE = 1;
   public static final int MATCH_MODE_STICKY = 2;
   public static final int PHY_LE_ALL_SUPPORTED = 255;
   private final long powerSaveScanInterval;
   private final long powerSaveRestInterval;
   private final int scanMode;
   private final int callbackType;
   private final long reportDelayMillis;
   private final int matchMode;
   private final int numOfMatchesPerFilter;
   private final boolean useHardwareFilteringIfSupported;
   private final boolean useHardwareBatchingIfSupported;
   private boolean useHardwareCallbackTypesIfSupported;
   private final long matchLostDeviceTimeout;
   private final long matchLostTaskInterval;
   private final boolean legacy;
   private final int phy;
   public static final Creator<ScanSettings> CREATOR = new Creator<ScanSettings>() {
      public ScanSettings[] newArray(int size) {
         return new ScanSettings[size];
      }

      public ScanSettings createFromParcel(Parcel in) {
         return new ScanSettings(in);
      }
   };

   public int getScanMode() {
      return this.scanMode;
   }

   public int getCallbackType() {
      return this.callbackType;
   }

   public int getMatchMode() {
      return this.matchMode;
   }

   public int getNumOfMatches() {
      return this.numOfMatchesPerFilter;
   }

   public boolean getUseHardwareFilteringIfSupported() {
      return this.useHardwareFilteringIfSupported;
   }

   public boolean getUseHardwareBatchingIfSupported() {
      return this.useHardwareBatchingIfSupported;
   }

   public boolean getUseHardwareCallbackTypesIfSupported() {
      return this.useHardwareCallbackTypesIfSupported;
   }

   void disableUseHardwareCallbackTypes() {
      this.useHardwareCallbackTypesIfSupported = false;
   }

   public long getMatchLostDeviceTimeout() {
      return this.matchLostDeviceTimeout;
   }

   public long getMatchLostTaskInterval() {
      return this.matchLostTaskInterval;
   }

   public boolean getLegacy() {
      return this.legacy;
   }

   public int getPhy() {
      return this.phy;
   }

   public long getReportDelayMillis() {
      return this.reportDelayMillis;
   }

   private ScanSettings(int scanMode, int callbackType, long reportDelayMillis, int matchMode, int numOfMatchesPerFilter, boolean legacy, int phy, boolean hardwareFiltering, boolean hardwareBatching, boolean hardwareCallbackTypes, long matchTimeout, long taskInterval, long powerSaveScanInterval, long powerSaveRestInterval) {
      this.scanMode = scanMode;
      this.callbackType = callbackType;
      this.reportDelayMillis = reportDelayMillis;
      this.numOfMatchesPerFilter = numOfMatchesPerFilter;
      this.matchMode = matchMode;
      this.legacy = legacy;
      this.phy = phy;
      this.useHardwareFilteringIfSupported = hardwareFiltering;
      this.useHardwareBatchingIfSupported = hardwareBatching;
      this.useHardwareCallbackTypesIfSupported = hardwareCallbackTypes;
      this.matchLostDeviceTimeout = matchTimeout * 1000000L;
      this.matchLostTaskInterval = taskInterval;
      this.powerSaveScanInterval = powerSaveScanInterval;
      this.powerSaveRestInterval = powerSaveRestInterval;
   }

   private ScanSettings(Parcel in) {
      this.scanMode = in.readInt();
      this.callbackType = in.readInt();
      this.reportDelayMillis = in.readLong();
      this.matchMode = in.readInt();
      this.numOfMatchesPerFilter = in.readInt();
      this.legacy = in.readInt() != 0;
      this.phy = in.readInt();
      this.useHardwareFilteringIfSupported = in.readInt() == 1;
      this.useHardwareBatchingIfSupported = in.readInt() == 1;
      this.matchLostDeviceTimeout = in.readLong();
      this.matchLostTaskInterval = in.readLong();
      this.powerSaveScanInterval = in.readLong();
      this.powerSaveRestInterval = in.readLong();
   }

   public void writeToParcel(Parcel dest, int flags) {
      dest.writeInt(this.scanMode);
      dest.writeInt(this.callbackType);
      dest.writeLong(this.reportDelayMillis);
      dest.writeInt(this.matchMode);
      dest.writeInt(this.numOfMatchesPerFilter);
      dest.writeInt(this.legacy ? 1 : 0);
      dest.writeInt(this.phy);
      dest.writeInt(this.useHardwareFilteringIfSupported ? 1 : 0);
      dest.writeInt(this.useHardwareBatchingIfSupported ? 1 : 0);
      dest.writeLong(this.matchLostDeviceTimeout);
      dest.writeLong(this.matchLostTaskInterval);
      dest.writeLong(this.powerSaveScanInterval);
      dest.writeLong(this.powerSaveRestInterval);
   }

   public int describeContents() {
      return 0;
   }

   public boolean hasPowerSaveMode() {
      return this.powerSaveRestInterval > 0L && this.powerSaveScanInterval > 0L;
   }

   public long getPowerSaveRest() {
      return this.powerSaveRestInterval;
   }

   public long getPowerSaveScan() {
      return this.powerSaveScanInterval;
   }

   // $FF: synthetic method
   ScanSettings(Parcel x0, Object x1) {
      this(x0);
   }

   // $FF: synthetic method
   ScanSettings(int x0, int x1, long x2, int x3, int x4, boolean x5, int x6, boolean x7, boolean x8, boolean x9, long x10, long x11, long x12, long x13, Object x14) {
      this(x0, x1, x2, x3, x4, x5, x6, x7, x8, x9, x10, x11, x12, x13);
   }

   public static final class Builder {
      private int scanMode = 0;
      private int callbackType = 1;
      private long reportDelayMillis = 0L;
      private int matchMode = 1;
      private int numOfMatchesPerFilter = 3;
      private boolean legacy = true;
      private int phy = 255;
      private boolean useHardwareFilteringIfSupported = true;
      private boolean useHardwareBatchingIfSupported = true;
      private boolean useHardwareCallbackTypesIfSupported = true;
      private long matchLostDeviceTimeout = 10000L;
      private long matchLostTaskInterval = 10000L;
      private long powerSaveRestInterval = 0L;
      private long powerSaveScanInterval = 0L;

      @NonNull
      public ScanSettings.Builder setScanMode(int scanMode) {
         if (scanMode >= -1 && scanMode <= 2) {
            this.scanMode = scanMode;
            return this;
         } else {
            throw new IllegalArgumentException("invalid scan mode " + scanMode);
         }
      }

      @NonNull
      public ScanSettings.Builder setCallbackType(int callbackType) {
         if (!this.isValidCallbackType(callbackType)) {
            throw new IllegalArgumentException("invalid callback type - " + callbackType);
         } else {
            this.callbackType = callbackType;
            return this;
         }
      }

      private boolean isValidCallbackType(int callbackType) {
         if (callbackType != 1 && callbackType != 2 && callbackType != 4) {
            return callbackType == 6;
         } else {
            return true;
         }
      }

      @NonNull
      public ScanSettings.Builder setReportDelay(long reportDelayMillis) {
         if (reportDelayMillis < 0L) {
            throw new IllegalArgumentException("reportDelay must be > 0");
         } else {
            this.reportDelayMillis = reportDelayMillis;
            return this;
         }
      }

      @NonNull
      public ScanSettings.Builder setNumOfMatches(int numOfMatches) {
         if (numOfMatches >= 1 && numOfMatches <= 3) {
            this.numOfMatchesPerFilter = numOfMatches;
            return this;
         } else {
            throw new IllegalArgumentException("invalid numOfMatches " + numOfMatches);
         }
      }

      @NonNull
      public ScanSettings.Builder setMatchMode(int matchMode) {
         if (matchMode >= 1 && matchMode <= 2) {
            this.matchMode = matchMode;
            return this;
         } else {
            throw new IllegalArgumentException("invalid matchMode " + matchMode);
         }
      }

      @NonNull
      public ScanSettings.Builder setLegacy(boolean legacy) {
         this.legacy = legacy;
         return this;
      }

      @NonNull
      public ScanSettings.Builder setPhy(int phy) {
         this.phy = phy;
         return this;
      }

      @NonNull
      public ScanSettings.Builder setUseHardwareFilteringIfSupported(boolean use) {
         this.useHardwareFilteringIfSupported = use;
         return this;
      }

      @NonNull
      public ScanSettings.Builder setUseHardwareBatchingIfSupported(boolean use) {
         this.useHardwareBatchingIfSupported = use;
         return this;
      }

      @NonNull
      public ScanSettings.Builder setUseHardwareCallbackTypesIfSupported(boolean use) {
         this.useHardwareCallbackTypesIfSupported = use;
         return this;
      }

      @NonNull
      public ScanSettings.Builder setMatchOptions(long deviceTimeoutMillis, long taskIntervalMillis) {
         if (deviceTimeoutMillis > 0L && taskIntervalMillis > 0L) {
            this.matchLostDeviceTimeout = deviceTimeoutMillis;
            this.matchLostTaskInterval = taskIntervalMillis;
            return this;
         } else {
            throw new IllegalArgumentException("maxDeviceAgeMillis and taskIntervalMillis must be > 0");
         }
      }

      @NonNull
      public ScanSettings.Builder setPowerSave(long scanInterval, long restInterval) {
         if (scanInterval > 0L && restInterval > 0L) {
            this.powerSaveScanInterval = scanInterval;
            this.powerSaveRestInterval = restInterval;
            return this;
         } else {
            throw new IllegalArgumentException("scanInterval and restInterval must be > 0");
         }
      }

      @NonNull
      public ScanSettings build() {
         if (this.powerSaveRestInterval == 0L && this.powerSaveScanInterval == 0L) {
            this.updatePowerSaveSettings();
         }

         return new ScanSettings(this.scanMode, this.callbackType, this.reportDelayMillis, this.matchMode, this.numOfMatchesPerFilter, this.legacy, this.phy, this.useHardwareFilteringIfSupported, this.useHardwareBatchingIfSupported, this.useHardwareCallbackTypesIfSupported, this.matchLostDeviceTimeout, this.matchLostTaskInterval, this.powerSaveScanInterval, this.powerSaveRestInterval);
      }

      private void updatePowerSaveSettings() {
         switch(this.scanMode) {
         case -1:
         case 0:
         default:
            this.powerSaveScanInterval = 500L;
            this.powerSaveRestInterval = 4500L;
            break;
         case 1:
            this.powerSaveScanInterval = 2000L;
            this.powerSaveRestInterval = 3000L;
            break;
         case 2:
            this.powerSaveScanInterval = 0L;
            this.powerSaveRestInterval = 0L;
         }

      }
   }
}
