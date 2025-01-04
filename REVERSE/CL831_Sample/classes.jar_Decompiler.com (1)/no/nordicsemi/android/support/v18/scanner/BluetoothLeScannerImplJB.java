package no.nordicsemi.android.support.v18.scanner;

import android.app.PendingIntent;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothAdapter.LeScanCallback;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.SystemClock;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

class BluetoothLeScannerImplJB extends BluetoothLeScannerCompat {
   @NonNull
   private final ScanCallbackWrapperSet<BluetoothLeScannerCompat.ScanCallbackWrapper> wrappers = new ScanCallbackWrapperSet();
   @Nullable
   private HandlerThread handlerThread;
   @Nullable
   private Handler powerSaveHandler;
   private long powerSaveRestInterval;
   private long powerSaveScanInterval;
   private final Runnable powerSaveSleepTask = new Runnable() {
      public void run() {
         BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
         if (adapter != null && BluetoothLeScannerImplJB.this.powerSaveRestInterval > 0L && BluetoothLeScannerImplJB.this.powerSaveScanInterval > 0L) {
            adapter.stopLeScan(BluetoothLeScannerImplJB.this.scanCallback);
            BluetoothLeScannerImplJB.this.powerSaveHandler.postDelayed(BluetoothLeScannerImplJB.this.powerSaveScanTask, BluetoothLeScannerImplJB.this.powerSaveRestInterval);
         }

      }
   };
   private final Runnable powerSaveScanTask = new Runnable() {
      public void run() {
         BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
         if (adapter != null && BluetoothLeScannerImplJB.this.powerSaveRestInterval > 0L && BluetoothLeScannerImplJB.this.powerSaveScanInterval > 0L) {
            adapter.startLeScan(BluetoothLeScannerImplJB.this.scanCallback);
            BluetoothLeScannerImplJB.this.powerSaveHandler.postDelayed(BluetoothLeScannerImplJB.this.powerSaveSleepTask, BluetoothLeScannerImplJB.this.powerSaveScanInterval);
         }

      }
   };
   private final LeScanCallback scanCallback = (device, rssi, scanRecord) -> {
      ScanResult scanResult = new ScanResult(device, ScanRecord.parseFromBytes(scanRecord), rssi, SystemClock.elapsedRealtimeNanos());
      synchronized(this.wrappers) {
         Collection<BluetoothLeScannerCompat.ScanCallbackWrapper> scanCallbackWrappers = this.wrappers.values();
         Iterator var7 = scanCallbackWrappers.iterator();

         while(var7.hasNext()) {
            BluetoothLeScannerCompat.ScanCallbackWrapper wrapper = (BluetoothLeScannerCompat.ScanCallbackWrapper)var7.next();
            wrapper.handler.post(() -> {
               wrapper.handleScanResult(1, scanResult);
            });
         }

      }
   };

   void startScanInternal(@NonNull List<ScanFilter> filters, @NonNull ScanSettings settings, @NonNull ScanCallback callback, @NonNull Handler handler) {
      BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
      boolean shouldStart;
      synchronized(this.wrappers) {
         if (this.wrappers.contains(callback)) {
            throw new IllegalArgumentException("scanner already started with given scanCallback");
         }

         UserScanCallbackWrapper callbackWrapper = new UserScanCallbackWrapper(callback);
         BluetoothLeScannerCompat.ScanCallbackWrapper wrapper = new BluetoothLeScannerCompat.ScanCallbackWrapper(false, false, filters, settings, callbackWrapper, handler);
         shouldStart = this.wrappers.isEmpty();
         this.wrappers.add(wrapper);
      }

      if (this.handlerThread == null) {
         this.handlerThread = new HandlerThread(BluetoothLeScannerImplJB.class.getName());
         this.handlerThread.start();
         this.powerSaveHandler = new Handler(this.handlerThread.getLooper());
      }

      this.setPowerSaveSettings();
      if (shouldStart) {
         adapter.startLeScan(this.scanCallback);
      }

   }

   void stopScanInternal(@NonNull ScanCallback callback) {
      BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
      boolean shouldStop;
      BluetoothLeScannerCompat.ScanCallbackWrapper wrapper;
      synchronized(this.wrappers) {
         wrapper = this.wrappers.remove(callback);
         shouldStop = this.wrappers.isEmpty();
      }

      if (wrapper != null) {
         wrapper.close();
         this.setPowerSaveSettings();
         if (shouldStop) {
            adapter.stopLeScan(this.scanCallback);
            if (this.powerSaveHandler != null) {
               this.powerSaveHandler.removeCallbacksAndMessages((Object)null);
            }

            if (this.handlerThread != null) {
               this.handlerThread.quitSafely();
               this.handlerThread = null;
            }
         }

      }
   }

   void startScanInternal(@NonNull List<ScanFilter> filters, @NonNull ScanSettings settings, @NonNull Context context, @NonNull PendingIntent callbackIntent, int requestCode) {
      Intent service = new Intent(context, ScannerService.class);
      service.putParcelableArrayListExtra("no.nordicsemi.android.support.v18.EXTRA_FILTERS", new ArrayList(filters));
      service.putExtra("no.nordicsemi.android.support.v18.EXTRA_SETTINGS", settings);
      service.putExtra("no.nordicsemi.android.support.v18.EXTRA_PENDING_INTENT", callbackIntent);
      service.putExtra("no.nordicsemi.android.support.v18.REQUEST_CODE", requestCode);
      service.putExtra("no.nordicsemi.android.support.v18.EXTRA_START", true);
      context.startService(service);
   }

   void stopScanInternal(@NonNull Context context, @NonNull PendingIntent callbackIntent, int requestCode) {
      Intent service = new Intent(context, ScannerService.class);
      service.putExtra("no.nordicsemi.android.support.v18.EXTRA_PENDING_INTENT", callbackIntent);
      service.putExtra("no.nordicsemi.android.support.v18.REQUEST_CODE", requestCode);
      service.putExtra("no.nordicsemi.android.support.v18.EXTRA_START", false);
      context.startService(service);
   }

   public void flushPendingScanResults(@NonNull ScanCallback callback) {
      if (callback == null) {
         throw new IllegalArgumentException("callback cannot be null!");
      } else {
         BluetoothLeScannerCompat.ScanCallbackWrapper wrapper;
         synchronized(this.wrappers) {
            wrapper = this.wrappers.get(callback);
         }

         if (wrapper == null) {
            throw new IllegalArgumentException("callback not registered!");
         } else {
            wrapper.flushPendingScanResults();
         }
      }
   }

   private void setPowerSaveSettings() {
      long minRest = Long.MAX_VALUE;
      long minScan = Long.MAX_VALUE;
      synchronized(this.wrappers) {
         Iterator var6 = this.wrappers.values().iterator();

         while(var6.hasNext()) {
            BluetoothLeScannerCompat.ScanCallbackWrapper wrapper = (BluetoothLeScannerCompat.ScanCallbackWrapper)var6.next();
            ScanSettings settings = wrapper.scanSettings;
            if (settings.hasPowerSaveMode()) {
               if (minRest > settings.getPowerSaveRest()) {
                  minRest = settings.getPowerSaveRest();
               }

               if (minScan > settings.getPowerSaveScan()) {
                  minScan = settings.getPowerSaveScan();
               }
            }
         }
      }

      if (minRest < Long.MAX_VALUE && minScan < Long.MAX_VALUE) {
         this.powerSaveRestInterval = minRest;
         this.powerSaveScanInterval = minScan;
         if (this.powerSaveHandler != null) {
            this.powerSaveHandler.removeCallbacks(this.powerSaveScanTask);
            this.powerSaveHandler.removeCallbacks(this.powerSaveSleepTask);
            this.powerSaveHandler.postDelayed(this.powerSaveSleepTask, this.powerSaveScanInterval);
         }
      } else {
         this.powerSaveRestInterval = this.powerSaveScanInterval = 0L;
         if (this.powerSaveHandler != null) {
            this.powerSaveHandler.removeCallbacks(this.powerSaveScanTask);
            this.powerSaveHandler.removeCallbacks(this.powerSaveSleepTask);
         }
      }

   }
}
