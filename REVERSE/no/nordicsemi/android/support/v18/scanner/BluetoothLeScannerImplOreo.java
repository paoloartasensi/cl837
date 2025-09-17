/*     */ package no.nordicsemi.android.support.v18.scanner;
/*     */ 
/*     */ import android.annotation.TargetApi;
/*     */ import android.app.PendingIntent;
/*     */ import android.bluetooth.BluetoothAdapter;
/*     */ import android.bluetooth.le.BluetoothLeScanner;
/*     */ import android.bluetooth.le.ScanFilter;
/*     */ import android.bluetooth.le.ScanResult;
/*     */ import android.bluetooth.le.ScanSettings;
/*     */ import android.content.Context;
/*     */ import android.content.Intent;
/*     */ import android.os.Build;
/*     */ import android.os.Handler;
/*     */ import android.os.Parcelable;
/*     */ import androidx.annotation.NonNull;
/*     */ import androidx.annotation.Nullable;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @TargetApi(26)
/*     */ class BluetoothLeScannerImplOreo
/*     */   extends BluetoothLeScannerImplMarshmallow
/*     */ {
/*     */   @NonNull
/*  50 */   private final HashMap<PendingIntent, PendingIntentExecutorWrapper> wrappers = new HashMap<>();
/*     */ 
/*     */ 
/*     */ 
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
/*     */   PendingIntentExecutorWrapper getWrapper(@NonNull PendingIntent callbackIntent) {
/*  65 */     synchronized (this.wrappers) {
/*  66 */       if (this.wrappers.containsKey(callbackIntent)) {
/*  67 */         PendingIntentExecutorWrapper wrapper = this.wrappers.get(callbackIntent);
/*  68 */         if (wrapper == null)
/*  69 */           throw new IllegalStateException("Scanning has been stopped"); 
/*  70 */         return wrapper;
/*     */       } 
/*  72 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   void addWrapper(@NonNull PendingIntent callbackIntent, @NonNull PendingIntentExecutorWrapper wrapper) {
/*  78 */     synchronized (this.wrappers) {
/*  79 */       this.wrappers.put(callbackIntent, wrapper);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void startScanInternal(@Nullable List<ScanFilter> filters, @Nullable ScanSettings settings, @NonNull Context context, @NonNull PendingIntent callbackIntent, int requestCode) {
/*  89 */     BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
/*  90 */     BluetoothLeScanner scanner = adapter.getBluetoothLeScanner();
/*  91 */     if (scanner == null) {
/*  92 */       throw new IllegalStateException("BT le scanner not available");
/*     */     }
/*  94 */     ScanSettings nonNullSettings = (settings != null) ? settings : (new ScanSettings.Builder()).build();
/*  95 */     List<ScanFilter> nonNullFilters = (filters != null) ? filters : Collections.<ScanFilter>emptyList();
/*     */     
/*  97 */     ScanSettings nativeSettings = toNativeScanSettings(adapter, nonNullSettings, false);
/*  98 */     List<ScanFilter> nativeFilters = null;
/*  99 */     if (filters != null && adapter.isOffloadedFilteringSupported() && nonNullSettings.getUseHardwareFilteringIfSupported()) {
/* 100 */       nativeFilters = toNativeScanFilters(filters);
/*     */     }
/* 102 */     synchronized (this.wrappers) {
/*     */ 
/*     */       
/* 105 */       this.wrappers.remove(callbackIntent);
/*     */     } 
/*     */     
/* 108 */     PendingIntent pendingIntent = createStartingPendingIntent(nonNullFilters, nonNullSettings, context, callbackIntent, requestCode);
/*     */     
/* 110 */     scanner.startScan(nativeFilters, nativeSettings, pendingIntent);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   void stopScanInternal(@NonNull Context context, @NonNull PendingIntent callbackIntent, int requestCode) {
/* 116 */     BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
/* 117 */     BluetoothLeScanner scanner = adapter.getBluetoothLeScanner();
/* 118 */     if (scanner == null) {
/* 119 */       throw new IllegalStateException("BT le scanner not available");
/*     */     }
/* 121 */     PendingIntent pendingIntent = createStoppingPendingIntent(context, requestCode);
/* 122 */     scanner.stopScan(pendingIntent);
/*     */     
/* 124 */     synchronized (this.wrappers) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 130 */       this.wrappers.put(callbackIntent, null);
/*     */     } 
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
/*     */   @NonNull
/*     */   private PendingIntent createStartingPendingIntent(@NonNull List<ScanFilter> filters, @NonNull ScanSettings settings, @NonNull Context context, @NonNull PendingIntent callbackIntent, int requestCode) {
/* 150 */     Intent intent = new Intent(context, PendingIntentReceiver.class);
/* 151 */     intent.setAction("no.nordicsemi.android.support.v18.ACTION_FOUND");
/*     */     
/* 153 */     BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
/*     */     
/* 155 */     intent.putExtra("no.nordicsemi.android.support.v18.EXTRA_PENDING_INTENT", (Parcelable)callbackIntent);
/*     */ 
/*     */ 
/*     */     
/* 159 */     intent.putParcelableArrayListExtra("no.nordicsemi.android.support.v18.EXTRA_FILTERS", toNativeScanFilters(filters));
/* 160 */     intent.putExtra("no.nordicsemi.android.support.v18.EXTRA_SETTINGS", (Parcelable)toNativeScanSettings(adapter, settings, true));
/* 161 */     intent.putExtra("no.nordicsemi.android.support.v18.EXTRA_USE_HARDWARE_BATCHING", settings.getUseHardwareBatchingIfSupported());
/* 162 */     intent.putExtra("no.nordicsemi.android.support.v18.EXTRA_USE_HARDWARE_FILTERING", settings.getUseHardwareFilteringIfSupported());
/* 163 */     intent.putExtra("no.nordicsemi.android.support.v18.EXTRA_USE_HARDWARE_CALLBACK_TYPES", settings.getUseHardwareCallbackTypesIfSupported());
/* 164 */     intent.putExtra("no.nordicsemi.android.support.v18.EXTRA_MATCH_MODE", settings.getMatchMode());
/* 165 */     intent.putExtra("no.nordicsemi.android.support.v18.EXTRA_NUM_OF_MATCHES", settings.getNumOfMatches());
/*     */     
/* 167 */     int flags = 134217728;
/*     */     
/* 169 */     if (Build.VERSION.SDK_INT >= 31)
/* 170 */       flags |= 0x2000000; 
/* 171 */     return PendingIntent.getBroadcast(context, requestCode, intent, flags);
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
/*     */   @NonNull
/*     */   private PendingIntent createStoppingPendingIntent(@NonNull Context context, int requestCode) {
/* 186 */     Intent intent = new Intent(context, PendingIntentReceiver.class);
/* 187 */     intent.setAction("no.nordicsemi.android.support.v18.ACTION_FOUND");
/*     */     
/* 189 */     int flags = 134217728;
/*     */ 
/*     */     
/* 192 */     if (Build.VERSION.SDK_INT >= 23)
/* 193 */       flags |= 0x4000000; 
/* 194 */     return PendingIntent.getBroadcast(context, requestCode, intent, flags);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NonNull
/*     */   ScanSettings toNativeScanSettings(@NonNull BluetoothAdapter adapter, @NonNull ScanSettings settings, boolean exactCopy) {
/* 202 */     ScanSettings.Builder builder = new ScanSettings.Builder();
/*     */ 
/*     */     
/* 205 */     if (exactCopy || (adapter.isOffloadedScanBatchingSupported() && settings.getUseHardwareBatchingIfSupported())) {
/* 206 */       builder.setReportDelay(settings.getReportDelayMillis());
/*     */     }
/* 208 */     if (exactCopy || settings.getUseHardwareCallbackTypesIfSupported()) {
/* 209 */       builder.setCallbackType(settings.getCallbackType())
/* 210 */         .setMatchMode(settings.getMatchMode())
/* 211 */         .setNumOfMatches(settings.getNumOfMatches());
/*     */     }
/* 213 */     builder.setScanMode(settings.getScanMode())
/* 214 */       .setLegacy(settings.getLegacy())
/* 215 */       .setPhy(settings.getPhy());
/*     */     
/* 217 */     return builder.build();
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
/*     */   @NonNull
/*     */   ScanSettings fromNativeScanSettings(@NonNull ScanSettings settings, boolean useHardwareBatchingIfSupported, boolean useHardwareFilteringIfSupported, boolean useHardwareCallbackTypesIfSupported, long matchLostDeviceTimeout, long matchLostTaskInterval, int matchMode, int numOfMatches) {
/* 240 */     ScanSettings.Builder builder = (new ScanSettings.Builder()).setLegacy(settings.getLegacy()).setPhy(settings.getPhy()).setCallbackType(settings.getCallbackType()).setScanMode(settings.getScanMode()).setReportDelay(settings.getReportDelayMillis()).setUseHardwareBatchingIfSupported(useHardwareBatchingIfSupported).setUseHardwareFilteringIfSupported(useHardwareFilteringIfSupported).setUseHardwareCallbackTypesIfSupported(useHardwareCallbackTypesIfSupported).setMatchOptions(matchLostDeviceTimeout, matchLostTaskInterval).setMatchMode(matchMode).setNumOfMatches(numOfMatches);
/*     */     
/* 242 */     return builder.build();
/*     */   }
/*     */   
/*     */   @NonNull
/*     */   ArrayList<ScanFilter> fromNativeScanFilters(@NonNull List<ScanFilter> filters) {
/* 247 */     ArrayList<ScanFilter> nativeScanFilters = new ArrayList<>();
/* 248 */     for (ScanFilter filter : filters)
/* 249 */       nativeScanFilters.add(fromNativeScanFilter(filter)); 
/* 250 */     return nativeScanFilters;
/*     */   }
/*     */ 
/*     */   
/*     */   @NonNull
/*     */   ScanFilter fromNativeScanFilter(@NonNull ScanFilter filter) {
/* 256 */     ScanFilter.Builder builder = new ScanFilter.Builder();
/* 257 */     builder.setDeviceAddress(filter.getDeviceAddress())
/* 258 */       .setDeviceName(filter.getDeviceName())
/* 259 */       .setServiceUuid(filter.getServiceUuid(), filter.getServiceUuidMask())
/* 260 */       .setManufacturerData(filter.getManufacturerId(), filter.getManufacturerData(), filter.getManufacturerDataMask());
/*     */     
/* 262 */     if (filter.getServiceDataUuid() != null) {
/* 263 */       builder.setServiceData(filter.getServiceDataUuid(), filter.getServiceData(), filter.getServiceDataMask());
/*     */     }
/* 265 */     return builder.build();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NonNull
/*     */   ScanResult fromNativeScanResult(@NonNull ScanResult result) {
/* 274 */     int eventType = result.getDataStatus() << 5 | (result.isLegacy() ? 16 : 0) | (result.isConnectable() ? 1 : 0);
/*     */     
/* 276 */     byte[] data = (result.getScanRecord() != null) ? result.getScanRecord().getBytes() : null;
/*     */     
/* 278 */     return new ScanResult(result.getDevice(), eventType, result.getPrimaryPhy(), result
/* 279 */         .getSecondaryPhy(), result.getAdvertisingSid(), result
/* 280 */         .getTxPower(), result.getRssi(), result
/* 281 */         .getPeriodicAdvertisingInterval(), 
/* 282 */         ScanRecord.parseFromBytes(data), result.getTimestampNanos());
/*     */   }
/*     */ 
/*     */   
/*     */   static class PendingIntentExecutorWrapper
/*     */     extends BluetoothLeScannerCompat.ScanCallbackWrapper
/*     */   {
/*     */     @NonNull
/*     */     final PendingIntentExecutor executor;
/*     */     
/*     */     PendingIntentExecutorWrapper(boolean offloadedBatchingSupported, boolean offloadedFilteringSupported, @NonNull List<ScanFilter> filters, @NonNull ScanSettings settings, @NonNull PendingIntentExecutor executor) {
/* 293 */       super(offloadedBatchingSupported, offloadedFilteringSupported, filters, settings, executor, new Handler());
/*     */       
/* 295 */       this.executor = executor;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Admin\Downloads\jd-gui-windows-1.6.6\classes.jar!\no\nordicsemi\android\support\v18\scanner\BluetoothLeScannerImplOreo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */