/*     */ package no.nordicsemi.android.support.v18.scanner;
/*     */ 
/*     */ import android.app.PendingIntent;
/*     */ import android.content.Context;
/*     */ import android.os.Build;
/*     */ import android.os.Handler;
/*     */ import android.os.Looper;
/*     */ import android.os.SystemClock;
/*     */ import androidx.annotation.NonNull;
/*     */ import androidx.annotation.Nullable;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class BluetoothLeScannerCompat
/*     */ {
/*     */   public static final String EXTRA_LIST_SCAN_RESULT = "android.bluetooth.le.extra.LIST_SCAN_RESULT";
/*     */   public static final String EXTRA_ERROR_CODE = "android.bluetooth.le.extra.ERROR_CODE";
/*     */   public static final String EXTRA_CALLBACK_TYPE = "android.bluetooth.le.extra.CALLBACK_TYPE";
/*     */   private static BluetoothLeScannerCompat instance;
/*     */   
/*     */   @NonNull
/*     */   public static synchronized BluetoothLeScannerCompat getScanner() {
/*  98 */     if (instance != null)
/*  99 */       return instance; 
/* 100 */     if (Build.VERSION.SDK_INT >= 26)
/* 101 */       return instance = new BluetoothLeScannerImplOreo(); 
/* 102 */     if (Build.VERSION.SDK_INT >= 23)
/* 103 */       return instance = new BluetoothLeScannerImplMarshmallow(); 
/* 104 */     if (Build.VERSION.SDK_INT >= 21)
/* 105 */       return instance = new BluetoothLeScannerImplLollipop(); 
/* 106 */     return instance = new BluetoothLeScannerImplJB();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void startScan(@NonNull ScanCallback callback) {
/* 134 */     if (callback == null) {
/* 135 */       throw new IllegalArgumentException("callback is null");
/*     */     }
/* 137 */     Handler handler = new Handler(Looper.getMainLooper());
/* 138 */     startScanInternal(Collections.emptyList(), (new ScanSettings.Builder()).build(), callback, handler);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void startScan(@Nullable List<ScanFilter> filters, @Nullable ScanSettings settings, @NonNull ScanCallback callback) {
/* 169 */     if (callback == null) {
/* 170 */       throw new IllegalArgumentException("callback is null");
/*     */     }
/* 172 */     Handler handler = new Handler(Looper.getMainLooper());
/* 173 */     startScanInternal((filters != null) ? filters : Collections.<ScanFilter>emptyList(), 
/* 174 */         (settings != null) ? settings : (new ScanSettings.Builder()).build(), callback, handler);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void startScan(@Nullable List<ScanFilter> filters, @Nullable ScanSettings settings, @NonNull ScanCallback callback, @Nullable Handler handler) {
/* 207 */     if (callback == null) {
/* 208 */       throw new IllegalArgumentException("callback is null");
/*     */     }
/* 210 */     startScanInternal((filters != null) ? filters : Collections.<ScanFilter>emptyList(), 
/* 211 */         (settings != null) ? settings : (new ScanSettings.Builder()).build(), callback, 
/* 212 */         (handler != null) ? handler : new Handler(Looper.getMainLooper()));
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
/*     */   public final void stopScan(@NonNull ScanCallback callback) {
/* 229 */     if (callback == null) {
/* 230 */       throw new IllegalArgumentException("callback is null");
/*     */     }
/* 232 */     stopScanInternal(callback);
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   abstract void startScanInternal(@NonNull List<ScanFilter> paramList, @NonNull ScanSettings paramScanSettings, @NonNull ScanCallback paramScanCallback, @NonNull Handler paramHandler);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   abstract void stopScanInternal(@NonNull ScanCallback paramScanCallback);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void startScan(@Nullable List<ScanFilter> filters, @Nullable ScanSettings settings, @NonNull Context context, @NonNull PendingIntent callbackIntent, int requestCode) {
/* 305 */     if (callbackIntent == null) {
/* 306 */       throw new IllegalArgumentException("callbackIntent is null");
/*     */     }
/*     */     
/* 309 */     if (context == null) {
/* 310 */       throw new IllegalArgumentException("context is null");
/*     */     }
/* 312 */     startScanInternal((filters != null) ? filters : Collections.<ScanFilter>emptyList(), 
/* 313 */         (settings != null) ? settings : (new ScanSettings.Builder()).build(), context, callbackIntent, requestCode);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void startScan(@Nullable List<ScanFilter> filters, @Nullable ScanSettings settings, @NonNull Context context, @NonNull PendingIntent callbackIntent) {
/* 364 */     startScan(filters, settings, context, callbackIntent, 0);
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
/*     */   
/*     */   public final void stopScan(@NonNull Context context, @NonNull PendingIntent callbackIntent, int requestCode) {
/* 387 */     if (callbackIntent == null) {
/* 388 */       throw new IllegalArgumentException("callbackIntent is null");
/*     */     }
/*     */     
/* 391 */     if (context == null) {
/* 392 */       throw new IllegalArgumentException("context is null");
/*     */     }
/* 394 */     stopScanInternal(context, callbackIntent, requestCode);
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
/*     */   public final void stopScan(@NonNull Context context, @NonNull PendingIntent callbackIntent) {
/* 415 */     stopScanInternal(context, callbackIntent, 0);
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
/*     */   abstract void startScanInternal(@NonNull List<ScanFilter> paramList, @NonNull ScanSettings paramScanSettings, @NonNull Context paramContext, @NonNull PendingIntent paramPendingIntent, int paramInt);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   abstract void stopScanInternal(@NonNull Context paramContext, @NonNull PendingIntent paramPendingIntent, int paramInt);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract void flushPendingScanResults(@NonNull ScanCallback paramScanCallback);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static class ScanCallbackWrapper
/*     */   {
/*     */     @NonNull
/* 465 */     private final Object LOCK = new Object();
/*     */ 
/*     */     
/*     */     private final boolean emulateFiltering;
/*     */     
/*     */     private final boolean emulateBatching;
/*     */     
/*     */     private final boolean emulateFoundOrLostCallbackType;
/*     */     
/*     */     private boolean scanningStopped;
/*     */     
/*     */     @NonNull
/* 477 */     private final List<ScanResult> scanResults = new ArrayList<>(); @NonNull final List<ScanFilter> filters; @NonNull final ScanSettings scanSettings; @NonNull final ScanCallback scanCallback; @NonNull
/*     */     final Handler handler; @NonNull
/* 479 */     private final Set<String> devicesInBatch = new HashSet<>();
/*     */     
/*     */     @NonNull
/* 482 */     private final Map<String, ScanResult> devicesInRange = new HashMap<>();
/*     */     
/*     */     @NonNull
/* 485 */     private final Runnable matchLostNotifierTask = new Runnable()
/*     */       {
/*     */         public void run()
/*     */         {
/* 489 */           long now = SystemClock.elapsedRealtimeNanos();
/*     */           
/* 491 */           synchronized (BluetoothLeScannerCompat.ScanCallbackWrapper.this.LOCK) {
/* 492 */             Iterator<ScanResult> iterator = BluetoothLeScannerCompat.ScanCallbackWrapper.this.devicesInRange.values().iterator();
/* 493 */             while (iterator.hasNext()) {
/* 494 */               ScanResult result = iterator.next();
/* 495 */               if (result.getTimestampNanos() < now - BluetoothLeScannerCompat.ScanCallbackWrapper.this.scanSettings.getMatchLostDeviceTimeout()) {
/* 496 */                 iterator.remove();
/* 497 */                 BluetoothLeScannerCompat.ScanCallbackWrapper.this.handler.post(() -> BluetoothLeScannerCompat.ScanCallbackWrapper.this.scanCallback.onScanResult(4, result));
/*     */               } 
/*     */             } 
/*     */             
/* 501 */             if (!BluetoothLeScannerCompat.ScanCallbackWrapper.this.devicesInRange.isEmpty()) {
/* 502 */               BluetoothLeScannerCompat.ScanCallbackWrapper.this.handler.postDelayed(this, BluetoothLeScannerCompat.ScanCallbackWrapper.this.scanSettings.getMatchLostTaskInterval());
/*     */             }
/*     */           } 
/*     */         }
/*     */       };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     ScanCallbackWrapper(boolean offloadedBatchingSupported, boolean offloadedFilteringSupported, @NonNull List<ScanFilter> filters, @NonNull ScanSettings settings, @NonNull ScanCallback callback, @NonNull final Handler handler) {
/* 514 */       this.filters = Collections.unmodifiableList(filters);
/* 515 */       this.scanSettings = settings;
/* 516 */       this.scanCallback = callback;
/* 517 */       this.handler = handler;
/* 518 */       this.scanningStopped = false;
/*     */ 
/*     */       
/* 521 */       boolean callbackTypesSupported = (Build.VERSION.SDK_INT >= 23);
/* 522 */       this
/* 523 */         .emulateFoundOrLostCallbackType = (settings.getCallbackType() != 1 && (!callbackTypesSupported || !settings.getUseHardwareCallbackTypesIfSupported()));
/*     */ 
/*     */       
/* 526 */       this.emulateFiltering = (!filters.isEmpty() && (!offloadedFilteringSupported || !settings.getUseHardwareFilteringIfSupported()));
/*     */ 
/*     */       
/* 529 */       long delay = settings.getReportDelayMillis();
/* 530 */       this.emulateBatching = (delay > 0L && (!offloadedBatchingSupported || !settings.getUseHardwareBatchingIfSupported()));
/* 531 */       if (this.emulateBatching) {
/* 532 */         Runnable flushPendingScanResultsTask = new Runnable()
/*     */           {
/*     */             public void run() {
/* 535 */               if (!BluetoothLeScannerCompat.ScanCallbackWrapper.this.scanningStopped) {
/* 536 */                 BluetoothLeScannerCompat.ScanCallbackWrapper.this.flushPendingScanResults();
/* 537 */                 handler.postDelayed(this, BluetoothLeScannerCompat.ScanCallbackWrapper.this.scanSettings.getReportDelayMillis());
/*     */               } 
/*     */             }
/*     */           };
/* 541 */         handler.postDelayed(flushPendingScanResultsTask, delay);
/*     */       } 
/*     */     }
/*     */     
/*     */     void close() {
/* 546 */       this.scanningStopped = true;
/* 547 */       this.handler.removeCallbacksAndMessages(null);
/* 548 */       synchronized (this.LOCK) {
/* 549 */         this.devicesInRange.clear();
/* 550 */         this.devicesInBatch.clear();
/* 551 */         this.scanResults.clear();
/*     */       } 
/*     */     }
/*     */     
/*     */     void flushPendingScanResults() {
/* 556 */       if (this.emulateBatching && !this.scanningStopped) {
/* 557 */         synchronized (this.LOCK) {
/* 558 */           this.scanCallback.onBatchScanResults(new ArrayList<>(this.scanResults));
/* 559 */           this.scanResults.clear();
/* 560 */           this.devicesInBatch.clear();
/*     */         } 
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     void handleScanResult(int callbackType, @NonNull ScanResult scanResult) {
/* 567 */       if (this.scanningStopped || (!this.filters.isEmpty() && !matches(scanResult))) {
/*     */         return;
/*     */       }
/* 570 */       String deviceAddress = scanResult.getDevice().getAddress();
/*     */ 
/*     */       
/* 573 */       if (this.emulateFoundOrLostCallbackType) {
/*     */         ScanResult previousResult;
/*     */         boolean firstResult;
/* 576 */         synchronized (this.devicesInRange) {
/*     */           
/* 578 */           firstResult = this.devicesInRange.isEmpty();
/*     */           
/* 580 */           previousResult = this.devicesInRange.put(deviceAddress, scanResult);
/*     */         } 
/*     */         
/* 583 */         if (previousResult == null && (
/* 584 */           this.scanSettings.getCallbackType() & 0x2) > 0) {
/* 585 */           this.scanCallback.onScanResult(2, scanResult);
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 591 */         if (firstResult && (
/* 592 */           this.scanSettings.getCallbackType() & 0x4) > 0) {
/* 593 */           this.handler.removeCallbacks(this.matchLostNotifierTask);
/* 594 */           this.handler.postDelayed(this.matchLostNotifierTask, this.scanSettings.getMatchLostTaskInterval());
/*     */         
/*     */         }
/*     */       
/*     */       }
/*     */       else {
/*     */         
/* 601 */         if (this.emulateBatching) {
/* 602 */           synchronized (this.LOCK) {
/* 603 */             if (!this.devicesInBatch.contains(deviceAddress)) {
/* 604 */               this.scanResults.add(scanResult);
/* 605 */               this.devicesInBatch.add(deviceAddress);
/*     */             } 
/*     */           } 
/*     */           
/*     */           return;
/*     */         } 
/* 611 */         this.scanCallback.onScanResult(callbackType, scanResult);
/*     */       } 
/*     */     }
/*     */     
/*     */     void handleScanResults(@NonNull List<ScanResult> results) {
/* 616 */       if (this.scanningStopped) {
/*     */         return;
/*     */       }
/* 619 */       List<ScanResult> filteredResults = results;
/*     */       
/* 621 */       if (this.emulateFiltering) {
/* 622 */         filteredResults = new ArrayList<>();
/* 623 */         for (ScanResult result : results) {
/* 624 */           if (matches(result))
/* 625 */             filteredResults.add(result); 
/*     */         } 
/*     */       } 
/* 628 */       this.scanCallback.onBatchScanResults(filteredResults);
/*     */     }
/*     */     
/*     */     void handleScanError(int errorCode) {
/* 632 */       this.scanCallback.onScanFailed(errorCode);
/*     */     }
/*     */     
/*     */     private boolean matches(@NonNull ScanResult result) {
/* 636 */       for (ScanFilter filter : this.filters) {
/* 637 */         if (filter.matches(result))
/* 638 */           return true; 
/*     */       } 
/* 640 */       return false;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Admin\Downloads\jd-gui-windows-1.6.6\classes.jar!\no\nordicsemi\android\support\v18\scanner\BluetoothLeScannerCompat.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */