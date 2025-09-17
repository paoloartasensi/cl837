/*     */ package no.nordicsemi.android.support.v18.scanner;
/*     */ 
/*     */ import android.app.PendingIntent;
/*     */ import android.bluetooth.BluetoothAdapter;
/*     */ import android.bluetooth.le.ScanFilter;
/*     */ import android.bluetooth.le.ScanResult;
/*     */ import android.bluetooth.le.ScanSettings;
/*     */ import android.content.BroadcastReceiver;
/*     */ import android.content.Context;
/*     */ import android.content.Intent;
/*     */ import androidx.annotation.RequiresApi;
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
/*     */ public class PendingIntentReceiver
/*     */   extends BroadcastReceiver
/*     */ {
/*     */   static final String ACTION = "no.nordicsemi.android.support.v18.ACTION_FOUND";
/*     */   static final String EXTRA_PENDING_INTENT = "no.nordicsemi.android.support.v18.EXTRA_PENDING_INTENT";
/*     */   static final String EXTRA_FILTERS = "no.nordicsemi.android.support.v18.EXTRA_FILTERS";
/*     */   static final String EXTRA_SETTINGS = "no.nordicsemi.android.support.v18.EXTRA_SETTINGS";
/*     */   static final String EXTRA_USE_HARDWARE_BATCHING = "no.nordicsemi.android.support.v18.EXTRA_USE_HARDWARE_BATCHING";
/*     */   static final String EXTRA_USE_HARDWARE_FILTERING = "no.nordicsemi.android.support.v18.EXTRA_USE_HARDWARE_FILTERING";
/*     */   static final String EXTRA_USE_HARDWARE_CALLBACK_TYPES = "no.nordicsemi.android.support.v18.EXTRA_USE_HARDWARE_CALLBACK_TYPES";
/*     */   static final String EXTRA_MATCH_LOST_TIMEOUT = "no.nordicsemi.android.support.v18.EXTRA_MATCH_LOST_TIMEOUT";
/*     */   static final String EXTRA_MATCH_LOST_INTERVAL = "no.nordicsemi.android.support.v18.EXTRA_MATCH_LOST_INTERVAL";
/*     */   static final String EXTRA_MATCH_MODE = "no.nordicsemi.android.support.v18.EXTRA_MATCH_MODE";
/*     */   static final String EXTRA_NUM_OF_MATCHES = "no.nordicsemi.android.support.v18.EXTRA_NUM_OF_MATCHES";
/*     */   
/*     */   @RequiresApi(api = 26)
/*     */   public void onReceive(Context context, Intent intent) {
/*     */     BluetoothLeScannerImplOreo.PendingIntentExecutorWrapper wrapper;
/*  42 */     if (context == null || intent == null) {
/*     */       return;
/*     */     }
/*     */     
/*  46 */     PendingIntent callbackIntent = (PendingIntent)intent.getParcelableExtra("no.nordicsemi.android.support.v18.EXTRA_PENDING_INTENT");
/*  47 */     if (callbackIntent == null) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*  53 */     ArrayList<ScanFilter> nativeScanFilters = intent.getParcelableArrayListExtra("no.nordicsemi.android.support.v18.EXTRA_FILTERS");
/*  54 */     ScanSettings nativeScanSettings = (ScanSettings)intent.getParcelableExtra("no.nordicsemi.android.support.v18.EXTRA_SETTINGS");
/*  55 */     if (nativeScanFilters == null || nativeScanSettings == null) {
/*     */       return;
/*     */     }
/*     */     
/*  59 */     boolean useHardwareBatchingIfSupported = intent.getBooleanExtra("no.nordicsemi.android.support.v18.EXTRA_USE_HARDWARE_BATCHING", true);
/*  60 */     boolean useHardwareFilteringIfSupported = intent.getBooleanExtra("no.nordicsemi.android.support.v18.EXTRA_USE_HARDWARE_FILTERING", true);
/*  61 */     boolean useHardwareCallbackTypesIfSupported = intent.getBooleanExtra("no.nordicsemi.android.support.v18.EXTRA_USE_HARDWARE_CALLBACK_TYPES", true);
/*  62 */     long matchLostDeviceTimeout = intent.getLongExtra("no.nordicsemi.android.support.v18.EXTRA_MATCH_LOST_TIMEOUT", 10000L);
/*  63 */     long matchLostTaskInterval = intent.getLongExtra("no.nordicsemi.android.support.v18.EXTRA_MATCH_LOST_INTERVAL", 10000L);
/*  64 */     int matchMode = intent.getIntExtra("no.nordicsemi.android.support.v18.EXTRA_MATCH_MODE", 1);
/*  65 */     int numOfMatches = intent.getIntExtra("no.nordicsemi.android.support.v18.EXTRA_NUM_OF_MATCHES", 3);
/*     */ 
/*     */     
/*  68 */     BluetoothLeScannerCompat scanner = BluetoothLeScannerCompat.getScanner();
/*  69 */     BluetoothLeScannerImplOreo scannerImpl = (BluetoothLeScannerImplOreo)scanner;
/*  70 */     ArrayList<ScanFilter> filters = scannerImpl.fromNativeScanFilters(nativeScanFilters);
/*  71 */     ScanSettings settings = scannerImpl.fromNativeScanSettings(nativeScanSettings, useHardwareBatchingIfSupported, useHardwareFilteringIfSupported, useHardwareCallbackTypesIfSupported, matchLostDeviceTimeout, matchLostTaskInterval, matchMode, numOfMatches);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  79 */     BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
/*  80 */     boolean offloadedBatchingSupported = adapter.isOffloadedScanBatchingSupported();
/*  81 */     boolean offloadedFilteringSupported = adapter.isOffloadedFilteringSupported();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  90 */     synchronized (scanner) {
/*     */       try {
/*  92 */         wrapper = scannerImpl.getWrapper(callbackIntent);
/*  93 */       } catch (IllegalStateException e) {
/*     */         return;
/*     */       } 
/*     */       
/*  97 */       if (wrapper == null) {
/*     */ 
/*     */         
/* 100 */         PendingIntentExecutor executor = new PendingIntentExecutor(callbackIntent, settings);
/* 101 */         wrapper = new BluetoothLeScannerImplOreo.PendingIntentExecutorWrapper(offloadedBatchingSupported, offloadedFilteringSupported, filters, settings, executor);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 107 */         scannerImpl.addWrapper(callbackIntent, wrapper);
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 113 */     wrapper.executor.setTemporaryContext(context);
/*     */ 
/*     */ 
/*     */     
/* 117 */     List<ScanResult> nativeScanResults = intent.getParcelableArrayListExtra("android.bluetooth.le.extra.LIST_SCAN_RESULT");
/* 118 */     if (nativeScanResults != null) {
/* 119 */       ArrayList<ScanResult> results = scannerImpl.fromNativeScanResults(nativeScanResults);
/*     */       
/* 121 */       if (settings.getReportDelayMillis() > 0L) {
/* 122 */         wrapper.handleScanResults(results);
/* 123 */       } else if (!results.isEmpty()) {
/* 124 */         int callbackType = intent.getIntExtra("android.bluetooth.le.extra.CALLBACK_TYPE", 1);
/*     */         
/* 126 */         wrapper.handleScanResult(callbackType, results.get(0));
/*     */       } 
/*     */     } else {
/* 129 */       int errorCode = intent.getIntExtra("android.bluetooth.le.extra.ERROR_CODE", 0);
/* 130 */       if (errorCode != 0) {
/* 131 */         wrapper.handleScanError(errorCode);
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 137 */     wrapper.executor.setTemporaryContext(null);
/*     */   }
/*     */ }


/* Location:              C:\Users\Admin\Downloads\jd-gui-windows-1.6.6\classes.jar!\no\nordicsemi\android\support\v18\scanner\PendingIntentReceiver.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */