/*     */ package no.nordicsemi.android.support.v18.scanner;
/*     */ 
/*     */ import android.annotation.TargetApi;
/*     */ import android.app.PendingIntent;
/*     */ import android.bluetooth.BluetoothAdapter;
/*     */ import android.bluetooth.le.BluetoothLeScanner;
/*     */ import android.bluetooth.le.ScanCallback;
/*     */ import android.bluetooth.le.ScanFilter;
/*     */ import android.bluetooth.le.ScanResult;
/*     */ import android.bluetooth.le.ScanSettings;
/*     */ import android.content.Context;
/*     */ import android.content.Intent;
/*     */ import android.os.Handler;
/*     */ import android.os.Parcelable;
/*     */ import android.os.SystemClock;
/*     */ import androidx.annotation.NonNull;
/*     */ import java.util.ArrayList;
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
/*     */ @TargetApi(21)
/*     */ class BluetoothLeScannerImplLollipop
/*     */   extends BluetoothLeScannerCompat
/*     */ {
/*     */   @NonNull
/*  48 */   private final ScanCallbackWrapperSet<ScanCallbackWrapperLollipop> wrappers = new ScanCallbackWrapperSet<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void startScanInternal(@NonNull List<ScanFilter> filters, @NonNull ScanSettings settings, @NonNull ScanCallback callback, @NonNull Handler handler) {
/*     */     ScanCallbackWrapperLollipop wrapper;
/*  57 */     BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
/*  58 */     BluetoothLeScanner scanner = adapter.getBluetoothLeScanner();
/*  59 */     if (scanner == null) {
/*  60 */       throw new IllegalStateException("BT le scanner not available");
/*     */     }
/*  62 */     boolean offloadedBatchingSupported = adapter.isOffloadedScanBatchingSupported();
/*  63 */     boolean offloadedFilteringSupported = adapter.isOffloadedFilteringSupported();
/*     */ 
/*     */ 
/*     */     
/*  67 */     synchronized (this.wrappers) {
/*  68 */       if (this.wrappers.contains(callback)) {
/*  69 */         throw new IllegalArgumentException("scanner already started with given callback");
/*     */       }
/*  71 */       UserScanCallbackWrapper callbackWrapper = new UserScanCallbackWrapper(callback);
/*  72 */       wrapper = new ScanCallbackWrapperLollipop(offloadedBatchingSupported, offloadedFilteringSupported, filters, settings, callbackWrapper, handler);
/*     */       
/*  74 */       this.wrappers.add(wrapper);
/*     */     } 
/*     */     
/*  77 */     ScanSettings nativeScanSettings = toNativeScanSettings(adapter, settings, false);
/*  78 */     List<ScanFilter> nativeScanFilters = null;
/*  79 */     if (!filters.isEmpty() && offloadedFilteringSupported && settings.getUseHardwareFilteringIfSupported()) {
/*  80 */       nativeScanFilters = toNativeScanFilters(filters);
/*     */     }
/*  82 */     scanner.startScan(nativeScanFilters, nativeScanSettings, wrapper.nativeCallback);
/*     */   }
/*     */ 
/*     */   
/*     */   void stopScanInternal(@NonNull ScanCallback callback) {
/*     */     ScanCallbackWrapperLollipop wrapper;
/*  88 */     synchronized (this.wrappers) {
/*  89 */       wrapper = this.wrappers.remove(callback);
/*     */     } 
/*  91 */     if (wrapper == null) {
/*     */       return;
/*     */     }
/*  94 */     wrapper.close();
/*     */     
/*  96 */     BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
/*  97 */     if (adapter != null) {
/*  98 */       BluetoothLeScanner scanner = adapter.getBluetoothLeScanner();
/*  99 */       if (scanner != null) {
/* 100 */         scanner.stopScan(wrapper.nativeCallback);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void startScanInternal(@NonNull List<ScanFilter> filters, @NonNull ScanSettings settings, @NonNull Context context, @NonNull PendingIntent callbackIntent, int requestCode) {
/* 110 */     BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
/* 111 */     BluetoothLeScanner scanner = adapter.getBluetoothLeScanner();
/* 112 */     if (scanner == null) {
/* 113 */       throw new IllegalStateException("BT le scanner not available");
/*     */     }
/* 115 */     Intent service = new Intent(context, ScannerService.class);
/* 116 */     service.putParcelableArrayListExtra("no.nordicsemi.android.support.v18.EXTRA_FILTERS", new ArrayList<>(filters));
/* 117 */     service.putExtra("no.nordicsemi.android.support.v18.EXTRA_SETTINGS", settings);
/* 118 */     service.putExtra("no.nordicsemi.android.support.v18.EXTRA_PENDING_INTENT", (Parcelable)callbackIntent);
/* 119 */     service.putExtra("no.nordicsemi.android.support.v18.REQUEST_CODE", requestCode);
/* 120 */     service.putExtra("no.nordicsemi.android.support.v18.EXTRA_START", true);
/* 121 */     context.startService(service);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void stopScanInternal(@NonNull Context context, @NonNull PendingIntent callbackIntent, int requestCode) {
/* 128 */     Intent service = new Intent(context, ScannerService.class);
/* 129 */     service.putExtra("no.nordicsemi.android.support.v18.EXTRA_PENDING_INTENT", (Parcelable)callbackIntent);
/* 130 */     service.putExtra("no.nordicsemi.android.support.v18.REQUEST_CODE", requestCode);
/* 131 */     service.putExtra("no.nordicsemi.android.support.v18.EXTRA_START", false);
/* 132 */     context.startService(service);
/*     */   }
/*     */   
/*     */   public void flushPendingScanResults(@NonNull ScanCallback callback) {
/*     */     ScanCallbackWrapperLollipop wrapper;
/* 137 */     BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
/*     */     
/* 139 */     if (callback == null) {
/* 140 */       throw new IllegalArgumentException("callback cannot be null!");
/*     */     }
/*     */ 
/*     */     
/* 144 */     synchronized (this.wrappers) {
/* 145 */       wrapper = this.wrappers.get(callback);
/*     */     } 
/*     */     
/* 148 */     if (wrapper == null) {
/* 149 */       throw new IllegalArgumentException("callback not registered!");
/*     */     }
/*     */     
/* 152 */     ScanSettings settings = wrapper.scanSettings;
/* 153 */     if (adapter.isOffloadedScanBatchingSupported() && settings.getUseHardwareBatchingIfSupported()) {
/* 154 */       BluetoothLeScanner scanner = adapter.getBluetoothLeScanner();
/* 155 */       if (scanner == null)
/*     */         return; 
/* 157 */       scanner.flushPendingScanResults(wrapper.nativeCallback);
/*     */     } else {
/* 159 */       wrapper.flushPendingScanResults();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @NonNull
/*     */   ScanSettings toNativeScanSettings(@NonNull BluetoothAdapter adapter, @NonNull ScanSettings settings, boolean exactCopy) {
/* 167 */     ScanSettings.Builder builder = new ScanSettings.Builder();
/*     */ 
/*     */     
/* 170 */     if (exactCopy || (adapter.isOffloadedScanBatchingSupported() && settings.getUseHardwareBatchingIfSupported())) {
/* 171 */       builder.setReportDelay(settings.getReportDelayMillis());
/*     */     }
/* 173 */     if (settings.getScanMode() != -1) {
/* 174 */       builder.setScanMode(settings.getScanMode());
/*     */     }
/*     */     else {
/*     */       
/* 178 */       builder.setScanMode(0);
/*     */     } 
/*     */     
/* 181 */     settings.disableUseHardwareCallbackTypes();
/*     */     
/* 183 */     return builder.build();
/*     */   }
/*     */   
/*     */   @NonNull
/*     */   ArrayList<ScanFilter> toNativeScanFilters(@NonNull List<ScanFilter> filters) {
/* 188 */     ArrayList<ScanFilter> nativeScanFilters = new ArrayList<>();
/* 189 */     for (ScanFilter filter : filters)
/* 190 */       nativeScanFilters.add(toNativeScanFilter(filter)); 
/* 191 */     return nativeScanFilters;
/*     */   }
/*     */   
/*     */   @NonNull
/*     */   ScanFilter toNativeScanFilter(@NonNull ScanFilter filter) {
/* 196 */     ScanFilter.Builder builder = new ScanFilter.Builder();
/* 197 */     builder.setServiceUuid(filter.getServiceUuid(), filter.getServiceUuidMask())
/* 198 */       .setManufacturerData(filter.getManufacturerId(), filter.getManufacturerData(), filter.getManufacturerDataMask());
/*     */     
/* 200 */     if (filter.getDeviceAddress() != null) {
/* 201 */       builder.setDeviceAddress(filter.getDeviceAddress());
/*     */     }
/* 203 */     if (filter.getDeviceName() != null) {
/* 204 */       builder.setDeviceName(filter.getDeviceName());
/*     */     }
/* 206 */     if (filter.getServiceDataUuid() != null) {
/* 207 */       builder.setServiceData(filter.getServiceDataUuid(), filter.getServiceData(), filter.getServiceDataMask());
/*     */     }
/* 209 */     return builder.build();
/*     */   }
/*     */ 
/*     */   
/*     */   @NonNull
/*     */   ScanResult fromNativeScanResult(@NonNull ScanResult nativeScanResult) {
/* 215 */     byte[] data = (nativeScanResult.getScanRecord() != null) ? nativeScanResult.getScanRecord().getBytes() : null;
/* 216 */     return new ScanResult(nativeScanResult.getDevice(), ScanRecord.parseFromBytes(data), nativeScanResult
/* 217 */         .getRssi(), nativeScanResult.getTimestampNanos());
/*     */   }
/*     */   
/*     */   @NonNull
/*     */   ArrayList<ScanResult> fromNativeScanResults(@NonNull List<ScanResult> nativeScanResults) {
/* 222 */     ArrayList<ScanResult> results = new ArrayList<>();
/* 223 */     for (ScanResult nativeScanResult : nativeScanResults) {
/* 224 */       ScanResult result = fromNativeScanResult(nativeScanResult);
/* 225 */       results.add(result);
/*     */     } 
/* 227 */     return results;
/*     */   }
/*     */ 
/*     */   
/*     */   static class ScanCallbackWrapperLollipop
/*     */     extends BluetoothLeScannerCompat.ScanCallbackWrapper
/*     */   {
/*     */     @NonNull
/*     */     private final ScanCallback nativeCallback;
/*     */     
/*     */     private ScanCallbackWrapperLollipop(boolean offloadedBatchingSupported, boolean offloadedFilteringSupported, @NonNull List<ScanFilter> filters, @NonNull ScanSettings settings, @NonNull ScanCallback callback, @NonNull Handler handler) {
/* 238 */       super(offloadedBatchingSupported, offloadedFilteringSupported, filters, settings, callback, handler);
/*     */ 
/*     */ 
/*     */       
/* 242 */       this.nativeCallback = new ScanCallback()
/*     */         {
/*     */           private long lastBatchTimestamp;
/*     */ 
/*     */           
/*     */           public void onScanResult(int callbackType, ScanResult nativeScanResult) {
/* 248 */             BluetoothLeScannerImplLollipop.ScanCallbackWrapperLollipop.this.handler.post(() -> {
/*     */                   BluetoothLeScannerImplLollipop scannerImpl = (BluetoothLeScannerImplLollipop)BluetoothLeScannerCompat.getScanner();
/*     */                   ScanResult result = scannerImpl.fromNativeScanResult(nativeScanResult);
/*     */                   BluetoothLeScannerImplLollipop.ScanCallbackWrapperLollipop.this.handleScanResult(callbackType, result);
/*     */                 });
/*     */           }
/*     */ 
/*     */ 
/*     */           
/*     */           public void onBatchScanResults(List<ScanResult> nativeScanResults) {
/* 258 */             BluetoothLeScannerImplLollipop.ScanCallbackWrapperLollipop.this.handler.post(() -> {
/*     */                   long now = SystemClock.elapsedRealtime();
/*     */                   if (this.lastBatchTimestamp > now - BluetoothLeScannerImplLollipop.ScanCallbackWrapperLollipop.this.scanSettings.getReportDelayMillis() + 5L) {
/*     */                     return;
/*     */                   }
/*     */                   this.lastBatchTimestamp = now;
/*     */                   BluetoothLeScannerImplLollipop scannerImpl = (BluetoothLeScannerImplLollipop)BluetoothLeScannerCompat.getScanner();
/*     */                   List<ScanResult> results = scannerImpl.fromNativeScanResults(nativeScanResults);
/*     */                   BluetoothLeScannerImplLollipop.ScanCallbackWrapperLollipop.this.handleScanResults(results);
/*     */                 });
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public void onScanFailed(int errorCode) {
/* 276 */             BluetoothLeScannerImplLollipop.ScanCallbackWrapperLollipop.this.handler.post(() -> {
/*     */                   if (BluetoothLeScannerImplLollipop.ScanCallbackWrapperLollipop.this.scanSettings.getUseHardwareCallbackTypesIfSupported() && BluetoothLeScannerImplLollipop.ScanCallbackWrapperLollipop.this.scanSettings.getCallbackType() != 1) {
/*     */                     BluetoothLeScannerImplLollipop.ScanCallbackWrapperLollipop.this.scanSettings.disableUseHardwareCallbackTypes();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */                     
/*     */                     BluetoothLeScannerCompat scanner = BluetoothLeScannerCompat.getScanner();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */                     
/*     */                     try {
/*     */                       scanner.stopScan(BluetoothLeScannerImplLollipop.ScanCallbackWrapperLollipop.this.scanCallback);
/* 292 */                     } catch (Exception exception) {}
/*     */ 
/*     */                     
/*     */                     try {
/*     */                       scanner.startScanInternal(BluetoothLeScannerImplLollipop.ScanCallbackWrapperLollipop.this.filters, BluetoothLeScannerImplLollipop.ScanCallbackWrapperLollipop.this.scanSettings, BluetoothLeScannerImplLollipop.ScanCallbackWrapperLollipop.this.scanCallback, BluetoothLeScannerImplLollipop.ScanCallbackWrapperLollipop.this.handler);
/* 297 */                     } catch (Exception exception) {}
/*     */                     return;
/*     */                   } 
/*     */                   BluetoothLeScannerImplLollipop.ScanCallbackWrapperLollipop.this.handleScanError(errorCode);
/*     */                 });
/*     */           }
/*     */         };
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Admin\Downloads\jd-gui-windows-1.6.6\classes.jar!\no\nordicsemi\android\support\v18\scanner\BluetoothLeScannerImplLollipop.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */