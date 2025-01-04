package no.nordicsemi.android.support.v18.scanner;

import android.app.PendingIntent;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.os.Build.VERSION;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public abstract class BluetoothLeScannerCompat {
   public static final String EXTRA_LIST_SCAN_RESULT = "android.bluetooth.le.extra.LIST_SCAN_RESULT";
   public static final String EXTRA_ERROR_CODE = "android.bluetooth.le.extra.ERROR_CODE";
   public static final String EXTRA_CALLBACK_TYPE = "android.bluetooth.le.extra.CALLBACK_TYPE";
   private static BluetoothLeScannerCompat instance;

   @NonNull
   public static synchronized BluetoothLeScannerCompat getScanner() {
      if (instance != null) {
         return instance;
      } else if (VERSION.SDK_INT >= 26) {
         return instance = new BluetoothLeScannerImplOreo();
      } else if (VERSION.SDK_INT >= 23) {
         return instance = new BluetoothLeScannerImplMarshmallow();
      } else {
         return VERSION.SDK_INT >= 21 ? (instance = new BluetoothLeScannerImplLollipop()) : (instance = new BluetoothLeScannerImplJB());
      }
   }

   BluetoothLeScannerCompat() {
   }

   public final void startScan(@NonNull ScanCallback callback) {
      if (callback == null) {
         throw new IllegalArgumentException("callback is null");
      } else {
         Handler handler = new Handler(Looper.getMainLooper());
         this.startScanInternal(Collections.emptyList(), (new ScanSettings.Builder()).build(), callback, handler);
      }
   }

   public final void startScan(@Nullable List<ScanFilter> filters, @Nullable ScanSettings settings, @NonNull ScanCallback callback) {
      if (callback == null) {
         throw new IllegalArgumentException("callback is null");
      } else {
         Handler handler = new Handler(Looper.getMainLooper());
         this.startScanInternal(filters != null ? filters : Collections.emptyList(), settings != null ? settings : (new ScanSettings.Builder()).build(), callback, handler);
      }
   }

   public final void startScan(@Nullable List<ScanFilter> filters, @Nullable ScanSettings settings, @NonNull ScanCallback callback, @Nullable Handler handler) {
      if (callback == null) {
         throw new IllegalArgumentException("callback is null");
      } else {
         this.startScanInternal(filters != null ? filters : Collections.emptyList(), settings != null ? settings : (new ScanSettings.Builder()).build(), callback, handler != null ? handler : new Handler(Looper.getMainLooper()));
      }
   }

   public final void stopScan(@NonNull ScanCallback callback) {
      if (callback == null) {
         throw new IllegalArgumentException("callback is null");
      } else {
         this.stopScanInternal(callback);
      }
   }

   abstract void startScanInternal(@NonNull List<ScanFilter> var1, @NonNull ScanSettings var2, @NonNull ScanCallback var3, @NonNull Handler var4);

   abstract void stopScanInternal(@NonNull ScanCallback var1);

   public final void startScan(@Nullable List<ScanFilter> filters, @Nullable ScanSettings settings, @NonNull Context context, @NonNull PendingIntent callbackIntent, int requestCode) {
      if (callbackIntent == null) {
         throw new IllegalArgumentException("callbackIntent is null");
      } else if (context == null) {
         throw new IllegalArgumentException("context is null");
      } else {
         this.startScanInternal(filters != null ? filters : Collections.emptyList(), settings != null ? settings : (new ScanSettings.Builder()).build(), context, callbackIntent, requestCode);
      }
   }

   public final void startScan(@Nullable List<ScanFilter> filters, @Nullable ScanSettings settings, @NonNull Context context, @NonNull PendingIntent callbackIntent) {
      this.startScan(filters, settings, context, callbackIntent, 0);
   }

   public final void stopScan(@NonNull Context context, @NonNull PendingIntent callbackIntent, int requestCode) {
      if (callbackIntent == null) {
         throw new IllegalArgumentException("callbackIntent is null");
      } else if (context == null) {
         throw new IllegalArgumentException("context is null");
      } else {
         this.stopScanInternal(context, callbackIntent, requestCode);
      }
   }

   public final void stopScan(@NonNull Context context, @NonNull PendingIntent callbackIntent) {
      this.stopScanInternal(context, callbackIntent, 0);
   }

   abstract void startScanInternal(@NonNull List<ScanFilter> var1, @NonNull ScanSettings var2, @NonNull Context var3, @NonNull PendingIntent var4, int var5);

   abstract void stopScanInternal(@NonNull Context var1, @NonNull PendingIntent var2, int var3);

   public abstract void flushPendingScanResults(@NonNull ScanCallback var1);

   static class ScanCallbackWrapper {
      @NonNull
      private final Object LOCK = new Object();
      private final boolean emulateFiltering;
      private final boolean emulateBatching;
      private final boolean emulateFoundOrLostCallbackType;
      private boolean scanningStopped;
      @NonNull
      final List<ScanFilter> filters;
      @NonNull
      final ScanSettings scanSettings;
      @NonNull
      final ScanCallback scanCallback;
      @NonNull
      final Handler handler;
      @NonNull
      private final List<ScanResult> scanResults = new ArrayList();
      @NonNull
      private final Set<String> devicesInBatch = new HashSet();
      @NonNull
      private final Map<String, ScanResult> devicesInRange = new HashMap();
      @NonNull
      private final Runnable matchLostNotifierTask = new Runnable() {
         public void run() {
            long now = SystemClock.elapsedRealtimeNanos();
            synchronized(ScanCallbackWrapper.this.LOCK) {
               Iterator iterator = ScanCallbackWrapper.this.devicesInRange.values().iterator();

               while(iterator.hasNext()) {
                  ScanResult result = (ScanResult)iterator.next();
                  if (result.getTimestampNanos() < now - ScanCallbackWrapper.this.scanSettings.getMatchLostDeviceTimeout()) {
                     iterator.remove();
                     ScanCallbackWrapper.this.handler.post(() -> {
                        ScanCallbackWrapper.this.scanCallback.onScanResult(4, result);
                     });
                  }
               }

               if (!ScanCallbackWrapper.this.devicesInRange.isEmpty()) {
                  ScanCallbackWrapper.this.handler.postDelayed(this, ScanCallbackWrapper.this.scanSettings.getMatchLostTaskInterval());
               }

            }
         }
      };

      ScanCallbackWrapper(boolean offloadedBatchingSupported, boolean offloadedFilteringSupported, @NonNull List<ScanFilter> filters, @NonNull ScanSettings settings, @NonNull ScanCallback callback, @NonNull final Handler handler) {
         this.filters = Collections.unmodifiableList(filters);
         this.scanSettings = settings;
         this.scanCallback = callback;
         this.handler = handler;
         this.scanningStopped = false;
         boolean callbackTypesSupported = VERSION.SDK_INT >= 23;
         this.emulateFoundOrLostCallbackType = settings.getCallbackType() != 1 && (!callbackTypesSupported || !settings.getUseHardwareCallbackTypesIfSupported());
         this.emulateFiltering = !filters.isEmpty() && (!offloadedFilteringSupported || !settings.getUseHardwareFilteringIfSupported());
         long delay = settings.getReportDelayMillis();
         this.emulateBatching = delay > 0L && (!offloadedBatchingSupported || !settings.getUseHardwareBatchingIfSupported());
         if (this.emulateBatching) {
            Runnable flushPendingScanResultsTask = new Runnable() {
               public void run() {
                  if (!ScanCallbackWrapper.this.scanningStopped) {
                     ScanCallbackWrapper.this.flushPendingScanResults();
                     handler.postDelayed(this, ScanCallbackWrapper.this.scanSettings.getReportDelayMillis());
                  }

               }
            };
            handler.postDelayed(flushPendingScanResultsTask, delay);
         }

      }

      void close() {
         this.scanningStopped = true;
         this.handler.removeCallbacksAndMessages((Object)null);
         synchronized(this.LOCK) {
            this.devicesInRange.clear();
            this.devicesInBatch.clear();
            this.scanResults.clear();
         }
      }

      void flushPendingScanResults() {
         if (this.emulateBatching && !this.scanningStopped) {
            synchronized(this.LOCK) {
               this.scanCallback.onBatchScanResults(new ArrayList(this.scanResults));
               this.scanResults.clear();
               this.devicesInBatch.clear();
            }
         }

      }

      void handleScanResult(int callbackType, @NonNull ScanResult scanResult) {
         if (!this.scanningStopped && (this.filters.isEmpty() || this.matches(scanResult))) {
            String deviceAddress = scanResult.getDevice().getAddress();
            if (this.emulateFoundOrLostCallbackType) {
               ScanResult previousResult;
               boolean firstResult;
               synchronized(this.devicesInRange) {
                  firstResult = this.devicesInRange.isEmpty();
                  previousResult = (ScanResult)this.devicesInRange.put(deviceAddress, scanResult);
               }

               if (previousResult == null && (this.scanSettings.getCallbackType() & 2) > 0) {
                  this.scanCallback.onScanResult(2, scanResult);
               }

               if (firstResult && (this.scanSettings.getCallbackType() & 4) > 0) {
                  this.handler.removeCallbacks(this.matchLostNotifierTask);
                  this.handler.postDelayed(this.matchLostNotifierTask, this.scanSettings.getMatchLostTaskInterval());
               }
            } else {
               if (this.emulateBatching) {
                  synchronized(this.LOCK) {
                     if (!this.devicesInBatch.contains(deviceAddress)) {
                        this.scanResults.add(scanResult);
                        this.devicesInBatch.add(deviceAddress);
                     }

                     return;
                  }
               }

               this.scanCallback.onScanResult(callbackType, scanResult);
            }

         }
      }

      void handleScanResults(@NonNull List<ScanResult> results) {
         if (!this.scanningStopped) {
            List<ScanResult> filteredResults = results;
            if (this.emulateFiltering) {
               filteredResults = new ArrayList();
               Iterator var3 = results.iterator();

               while(var3.hasNext()) {
                  ScanResult result = (ScanResult)var3.next();
                  if (this.matches(result)) {
                     ((List)filteredResults).add(result);
                  }
               }
            }

            this.scanCallback.onBatchScanResults((List)filteredResults);
         }
      }

      void handleScanError(int errorCode) {
         this.scanCallback.onScanFailed(errorCode);
      }

      private boolean matches(@NonNull ScanResult result) {
         Iterator var2 = this.filters.iterator();

         ScanFilter filter;
         do {
            if (!var2.hasNext()) {
               return false;
            }

            filter = (ScanFilter)var2.next();
         } while(!filter.matches(result));

         return true;
      }
   }
}
