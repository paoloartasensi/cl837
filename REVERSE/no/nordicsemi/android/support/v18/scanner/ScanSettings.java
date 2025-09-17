/*     */ package no.nordicsemi.android.support.v18.scanner;
/*     */ 
/*     */ import android.os.Parcel;
/*     */ import android.os.Parcelable;
/*     */ import androidx.annotation.NonNull;
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
/*     */ public final class ScanSettings
/*     */   implements Parcelable
/*     */ {
/*     */   public static final long MATCH_LOST_DEVICE_TIMEOUT_DEFAULT = 10000L;
/*     */   public static final long MATCH_LOST_TASK_INTERVAL_DEFAULT = 10000L;
/*     */   public static final int SCAN_MODE_OPPORTUNISTIC = -1;
/*     */   public static final int SCAN_MODE_LOW_POWER = 0;
/*     */   public static final int SCAN_MODE_BALANCED = 1;
/*     */   public static final int SCAN_MODE_LOW_LATENCY = 2;
/*     */   public static final int CALLBACK_TYPE_ALL_MATCHES = 1;
/*     */   public static final int CALLBACK_TYPE_FIRST_MATCH = 2;
/*     */   public static final int CALLBACK_TYPE_MATCH_LOST = 4;
/*     */   public static final int MATCH_NUM_ONE_ADVERTISEMENT = 1;
/*     */   public static final int MATCH_NUM_FEW_ADVERTISEMENT = 2;
/*     */   public static final int MATCH_NUM_MAX_ADVERTISEMENT = 3;
/*     */   public static final int MATCH_MODE_AGGRESSIVE = 1;
/*     */   public static final int MATCH_MODE_STICKY = 2;
/*     */   public static final int PHY_LE_ALL_SUPPORTED = 255;
/*     */   private final long powerSaveScanInterval;
/*     */   private final long powerSaveRestInterval;
/*     */   private final int scanMode;
/*     */   private final int callbackType;
/*     */   private final long reportDelayMillis;
/*     */   private final int matchMode;
/*     */   private final int numOfMatchesPerFilter;
/*     */   private final boolean useHardwareFilteringIfSupported;
/*     */   private final boolean useHardwareBatchingIfSupported;
/*     */   private boolean useHardwareCallbackTypesIfSupported;
/*     */   private final long matchLostDeviceTimeout;
/*     */   private final long matchLostTaskInterval;
/*     */   private final boolean legacy;
/*     */   private final int phy;
/*     */   
/*     */   public int getScanMode() {
/* 183 */     return this.scanMode;
/*     */   }
/*     */   
/*     */   public int getCallbackType() {
/* 187 */     return this.callbackType;
/*     */   }
/*     */   
/*     */   public int getMatchMode() {
/* 191 */     return this.matchMode;
/*     */   }
/*     */   
/*     */   public int getNumOfMatches() {
/* 195 */     return this.numOfMatchesPerFilter;
/*     */   }
/*     */   
/*     */   public boolean getUseHardwareFilteringIfSupported() {
/* 199 */     return this.useHardwareFilteringIfSupported;
/*     */   }
/*     */   
/*     */   public boolean getUseHardwareBatchingIfSupported() {
/* 203 */     return this.useHardwareBatchingIfSupported;
/*     */   }
/*     */   
/*     */   public boolean getUseHardwareCallbackTypesIfSupported() {
/* 207 */     return this.useHardwareCallbackTypesIfSupported;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void disableUseHardwareCallbackTypes() {
/* 217 */     this.useHardwareCallbackTypesIfSupported = false;
/*     */   }
/*     */   
/*     */   public long getMatchLostDeviceTimeout() {
/* 221 */     return this.matchLostDeviceTimeout;
/*     */   }
/*     */   
/*     */   public long getMatchLostTaskInterval() {
/* 225 */     return this.matchLostTaskInterval;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getLegacy() {
/* 234 */     return this.legacy;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getPhy() {
/* 241 */     return this.phy;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getReportDelayMillis() {
/* 248 */     return this.reportDelayMillis;
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
/*     */   private ScanSettings(int scanMode, int callbackType, long reportDelayMillis, int matchMode, int numOfMatchesPerFilter, boolean legacy, int phy, boolean hardwareFiltering, boolean hardwareBatching, boolean hardwareCallbackTypes, long matchTimeout, long taskInterval, long powerSaveScanInterval, long powerSaveRestInterval) {
/* 260 */     this.scanMode = scanMode;
/* 261 */     this.callbackType = callbackType;
/* 262 */     this.reportDelayMillis = reportDelayMillis;
/* 263 */     this.numOfMatchesPerFilter = numOfMatchesPerFilter;
/* 264 */     this.matchMode = matchMode;
/* 265 */     this.legacy = legacy;
/* 266 */     this.phy = phy;
/* 267 */     this.useHardwareFilteringIfSupported = hardwareFiltering;
/* 268 */     this.useHardwareBatchingIfSupported = hardwareBatching;
/* 269 */     this.useHardwareCallbackTypesIfSupported = hardwareCallbackTypes;
/* 270 */     this.matchLostDeviceTimeout = matchTimeout * 1000000L;
/* 271 */     this.matchLostTaskInterval = taskInterval;
/* 272 */     this.powerSaveScanInterval = powerSaveScanInterval;
/* 273 */     this.powerSaveRestInterval = powerSaveRestInterval;
/*     */   }
/*     */   
/*     */   private ScanSettings(Parcel in) {
/* 277 */     this.scanMode = in.readInt();
/* 278 */     this.callbackType = in.readInt();
/* 279 */     this.reportDelayMillis = in.readLong();
/* 280 */     this.matchMode = in.readInt();
/* 281 */     this.numOfMatchesPerFilter = in.readInt();
/* 282 */     this.legacy = (in.readInt() != 0);
/* 283 */     this.phy = in.readInt();
/* 284 */     this.useHardwareFilteringIfSupported = (in.readInt() == 1);
/* 285 */     this.useHardwareBatchingIfSupported = (in.readInt() == 1);
/* 286 */     this.matchLostDeviceTimeout = in.readLong();
/* 287 */     this.matchLostTaskInterval = in.readLong();
/* 288 */     this.powerSaveScanInterval = in.readLong();
/* 289 */     this.powerSaveRestInterval = in.readLong();
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeToParcel(Parcel dest, int flags) {
/* 294 */     dest.writeInt(this.scanMode);
/* 295 */     dest.writeInt(this.callbackType);
/* 296 */     dest.writeLong(this.reportDelayMillis);
/* 297 */     dest.writeInt(this.matchMode);
/* 298 */     dest.writeInt(this.numOfMatchesPerFilter);
/* 299 */     dest.writeInt(this.legacy ? 1 : 0);
/* 300 */     dest.writeInt(this.phy);
/* 301 */     dest.writeInt(this.useHardwareFilteringIfSupported ? 1 : 0);
/* 302 */     dest.writeInt(this.useHardwareBatchingIfSupported ? 1 : 0);
/* 303 */     dest.writeLong(this.matchLostDeviceTimeout);
/* 304 */     dest.writeLong(this.matchLostTaskInterval);
/* 305 */     dest.writeLong(this.powerSaveScanInterval);
/* 306 */     dest.writeLong(this.powerSaveRestInterval);
/*     */   }
/*     */ 
/*     */   
/*     */   public int describeContents() {
/* 311 */     return 0;
/*     */   }
/*     */   
/* 314 */   public static final Parcelable.Creator<ScanSettings> CREATOR = new Parcelable.Creator<ScanSettings>()
/*     */     {
/*     */       public ScanSettings[] newArray(int size) {
/* 317 */         return new ScanSettings[size];
/*     */       }
/*     */ 
/*     */       
/*     */       public ScanSettings createFromParcel(Parcel in) {
/* 322 */         return new ScanSettings(in);
/*     */       }
/*     */     };
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasPowerSaveMode() {
/* 330 */     return (this.powerSaveRestInterval > 0L && this.powerSaveScanInterval > 0L);
/*     */   }
/*     */   
/*     */   public long getPowerSaveRest() {
/* 334 */     return this.powerSaveRestInterval;
/*     */   }
/*     */   
/*     */   public long getPowerSaveScan() {
/* 338 */     return this.powerSaveScanInterval;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final class Builder
/*     */   {
/* 346 */     private int scanMode = 0;
/* 347 */     private int callbackType = 1;
/* 348 */     private long reportDelayMillis = 0L;
/* 349 */     private int matchMode = 1;
/* 350 */     private int numOfMatchesPerFilter = 3;
/*     */     private boolean legacy = true;
/* 352 */     private int phy = 255;
/*     */     private boolean useHardwareFilteringIfSupported = true;
/*     */     private boolean useHardwareBatchingIfSupported = true;
/*     */     private boolean useHardwareCallbackTypesIfSupported = true;
/* 356 */     private long matchLostDeviceTimeout = 10000L;
/* 357 */     private long matchLostTaskInterval = 10000L;
/* 358 */     private long powerSaveRestInterval = 0L;
/* 359 */     private long powerSaveScanInterval = 0L;
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
/*     */     @NonNull
/*     */     public Builder setScanMode(int scanMode) {
/* 380 */       if (scanMode < -1 || scanMode > 2) {
/* 381 */         throw new IllegalArgumentException("invalid scan mode " + scanMode);
/*     */       }
/* 383 */       this.scanMode = scanMode;
/* 384 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @NonNull
/*     */     public Builder setCallbackType(int callbackType) {
/* 395 */       if (!isValidCallbackType(callbackType)) {
/* 396 */         throw new IllegalArgumentException("invalid callback type - " + callbackType);
/*     */       }
/* 398 */       this.callbackType = callbackType;
/* 399 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     private boolean isValidCallbackType(int callbackType) {
/* 404 */       if (callbackType == 1 || callbackType == 2 || callbackType == 4)
/*     */       {
/*     */         
/* 407 */         return true;
/*     */       }
/* 409 */       return (callbackType == 6);
/*     */     }
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
/*     */     @NonNull
/*     */     public Builder setReportDelay(long reportDelayMillis) {
/* 427 */       if (reportDelayMillis < 0L) {
/* 428 */         throw new IllegalArgumentException("reportDelay must be > 0");
/*     */       }
/* 430 */       this.reportDelayMillis = reportDelayMillis;
/* 431 */       return this;
/*     */     }
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
/*     */     @NonNull
/*     */     public Builder setNumOfMatches(int numOfMatches) {
/* 445 */       if (numOfMatches < 1 || numOfMatches > 3)
/*     */       {
/* 447 */         throw new IllegalArgumentException("invalid numOfMatches " + numOfMatches);
/*     */       }
/* 449 */       this.numOfMatchesPerFilter = numOfMatches;
/* 450 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @NonNull
/*     */     public Builder setMatchMode(int matchMode) {
/* 463 */       if (matchMode < 1 || matchMode > 2)
/*     */       {
/* 465 */         throw new IllegalArgumentException("invalid matchMode " + matchMode);
/*     */       }
/* 467 */       this.matchMode = matchMode;
/* 468 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @NonNull
/*     */     public Builder setLegacy(boolean legacy) {
/* 481 */       this.legacy = legacy;
/* 482 */       return this;
/*     */     }
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
/*     */     @NonNull
/*     */     public Builder setPhy(int phy) {
/* 501 */       this.phy = phy;
/* 502 */       return this;
/*     */     }
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
/*     */     @NonNull
/*     */     public Builder setUseHardwareFilteringIfSupported(boolean use) {
/* 518 */       this.useHardwareFilteringIfSupported = use;
/* 519 */       return this;
/*     */     }
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
/*     */     @NonNull
/*     */     public Builder setUseHardwareBatchingIfSupported(boolean use) {
/* 536 */       this.useHardwareBatchingIfSupported = use;
/* 537 */       return this;
/*     */     }
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
/*     */     @NonNull
/*     */     public Builder setUseHardwareCallbackTypesIfSupported(boolean use) {
/* 555 */       this.useHardwareCallbackTypesIfSupported = use;
/* 556 */       return this;
/*     */     }
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
/*     */     @NonNull
/*     */     public Builder setMatchOptions(long deviceTimeoutMillis, long taskIntervalMillis) {
/* 573 */       if (deviceTimeoutMillis <= 0L || taskIntervalMillis <= 0L) {
/* 574 */         throw new IllegalArgumentException("maxDeviceAgeMillis and taskIntervalMillis must be > 0");
/*     */       }
/* 576 */       this.matchLostDeviceTimeout = deviceTimeoutMillis;
/* 577 */       this.matchLostTaskInterval = taskIntervalMillis;
/* 578 */       return this;
/*     */     }
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
/*     */     @NonNull
/*     */     public Builder setPowerSave(long scanInterval, long restInterval) {
/* 593 */       if (scanInterval <= 0L || restInterval <= 0L) {
/* 594 */         throw new IllegalArgumentException("scanInterval and restInterval must be > 0");
/*     */       }
/* 596 */       this.powerSaveScanInterval = scanInterval;
/* 597 */       this.powerSaveRestInterval = restInterval;
/* 598 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @NonNull
/*     */     public ScanSettings build() {
/* 606 */       if (this.powerSaveRestInterval == 0L && this.powerSaveScanInterval == 0L) {
/* 607 */         updatePowerSaveSettings();
/*     */       }
/* 609 */       return new ScanSettings(this.scanMode, this.callbackType, this.reportDelayMillis, this.matchMode, this.numOfMatchesPerFilter, this.legacy, this.phy, this.useHardwareFilteringIfSupported, this.useHardwareBatchingIfSupported, this.useHardwareCallbackTypesIfSupported, this.matchLostDeviceTimeout, this.matchLostTaskInterval, this.powerSaveScanInterval, this.powerSaveRestInterval);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private void updatePowerSaveSettings() {
/* 620 */       switch (this.scanMode) {
/*     */         
/*     */         case 2:
/* 623 */           this.powerSaveScanInterval = 0L;
/* 624 */           this.powerSaveRestInterval = 0L;
/*     */           return;
/*     */         
/*     */         case 1:
/* 628 */           this.powerSaveScanInterval = 2000L;
/* 629 */           this.powerSaveRestInterval = 3000L;
/*     */           return;
/*     */       } 
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
/* 642 */       this.powerSaveScanInterval = 500L;
/* 643 */       this.powerSaveRestInterval = 4500L;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Admin\Downloads\jd-gui-windows-1.6.6\classes.jar!\no\nordicsemi\android\support\v18\scanner\ScanSettings.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */