package no.nordicsemi.android.support.v18.scanner;

import android.annotation.TargetApi;
import android.app.PendingIntent;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanSettings.Builder;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.SystemClock;
import androidx.annotation.NonNull;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@TargetApi(21)
class BluetoothLeScannerImplLollipop extends BluetoothLeScannerCompat {
   @NonNull
   private final ScanCallbackWrapperSet<BluetoothLeScannerImplLollipop.ScanCallbackWrapperLollipop> wrappers = new ScanCallbackWrapperSet();

   void startScanInternal(@NonNull List<ScanFilter> filters, @NonNull ScanSettings settings, @NonNull ScanCallback callback, @NonNull Handler handler) {
      BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
      BluetoothLeScanner scanner = adapter.getBluetoothLeScanner();
      if (scanner == null) {
         throw new IllegalStateException("BT le scanner not available");
      } else {
         boolean offloadedBatchingSupported = adapter.isOffloadedScanBatchingSupported();
         boolean offloadedFilteringSupported = adapter.isOffloadedFilteringSupported();
         BluetoothLeScannerImplLollipop.ScanCallbackWrapperLollipop wrapper;
         synchronized(this.wrappers) {
            if (this.wrappers.contains(callback)) {
               throw new IllegalArgumentException("scanner already started with given callback");
            }

            UserScanCallbackWrapper callbackWrapper = new UserScanCallbackWrapper(callback);
            wrapper = new BluetoothLeScannerImplLollipop.ScanCallbackWrapperLollipop(offloadedBatchingSupported, offloadedFilteringSupported, filters, settings, callbackWrapper, handler);
            this.wrappers.add(wrapper);
         }

         android.bluetooth.le.ScanSettings nativeScanSettings = this.toNativeScanSettings(adapter, settings, false);
         List<android.bluetooth.le.ScanFilter> nativeScanFilters = null;
         if (!filters.isEmpty() && offloadedFilteringSupported && settings.getUseHardwareFilteringIfSupported()) {
            nativeScanFilters = this.toNativeScanFilters(filters);
         }

         scanner.startScan(nativeScanFilters, nativeScanSettings, wrapper.nativeCallback);
      }
   }

   void stopScanInternal(@NonNull ScanCallback callback) {
      BluetoothLeScannerImplLollipop.ScanCallbackWrapperLollipop wrapper;
      synchronized(this.wrappers) {
         wrapper = (BluetoothLeScannerImplLollipop.ScanCallbackWrapperLollipop)this.wrappers.remove(callback);
      }

      if (wrapper != null) {
         wrapper.close();
         BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
         if (adapter != null) {
            BluetoothLeScanner scanner = adapter.getBluetoothLeScanner();
            if (scanner != null) {
               scanner.stopScan(wrapper.nativeCallback);
            }
         }

      }
   }

   void startScanInternal(@NonNull List<ScanFilter> filters, @NonNull ScanSettings settings, @NonNull Context context, @NonNull PendingIntent callbackIntent, int requestCode) {
      BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
      BluetoothLeScanner scanner = adapter.getBluetoothLeScanner();
      if (scanner == null) {
         throw new IllegalStateException("BT le scanner not available");
      } else {
         Intent service = new Intent(context, ScannerService.class);
         service.putParcelableArrayListExtra("no.nordicsemi.android.support.v18.EXTRA_FILTERS", new ArrayList(filters));
         service.putExtra("no.nordicsemi.android.support.v18.EXTRA_SETTINGS", settings);
         service.putExtra("no.nordicsemi.android.support.v18.EXTRA_PENDING_INTENT", callbackIntent);
         service.putExtra("no.nordicsemi.android.support.v18.REQUEST_CODE", requestCode);
         service.putExtra("no.nordicsemi.android.support.v18.EXTRA_START", true);
         context.startService(service);
      }
   }

   void stopScanInternal(@NonNull Context context, @NonNull PendingIntent callbackIntent, int requestCode) {
      Intent service = new Intent(context, ScannerService.class);
      service.putExtra("no.nordicsemi.android.support.v18.EXTRA_PENDING_INTENT", callbackIntent);
      service.putExtra("no.nordicsemi.android.support.v18.REQUEST_CODE", requestCode);
      service.putExtra("no.nordicsemi.android.support.v18.EXTRA_START", false);
      context.startService(service);
   }

   public void flushPendingScanResults(@NonNull ScanCallback callback) {
      BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
      if (callback == null) {
         throw new IllegalArgumentException("callback cannot be null!");
      } else {
         BluetoothLeScannerImplLollipop.ScanCallbackWrapperLollipop wrapper;
         synchronized(this.wrappers) {
            wrapper = (BluetoothLeScannerImplLollipop.ScanCallbackWrapperLollipop)this.wrappers.get(callback);
         }

         if (wrapper == null) {
            throw new IllegalArgumentException("callback not registered!");
         } else {
            ScanSettings settings = wrapper.scanSettings;
            if (adapter.isOffloadedScanBatchingSupported() && settings.getUseHardwareBatchingIfSupported()) {
               BluetoothLeScanner scanner = adapter.getBluetoothLeScanner();
               if (scanner == null) {
                  return;
               }

               scanner.flushPendingScanResults(wrapper.nativeCallback);
            } else {
               wrapper.flushPendingScanResults();
            }

         }
      }
   }

   @NonNull
   android.bluetooth.le.ScanSettings toNativeScanSettings(@NonNull BluetoothAdapter adapter, @NonNull ScanSettings settings, boolean exactCopy) {
      Builder builder = new Builder();
      if (exactCopy || adapter.isOffloadedScanBatchingSupported() && settings.getUseHardwareBatchingIfSupported()) {
         builder.setReportDelay(settings.getReportDelayMillis());
      }

      if (settings.getScanMode() != -1) {
         builder.setScanMode(settings.getScanMode());
      } else {
         builder.setScanMode(0);
      }

      settings.disableUseHardwareCallbackTypes();
      return builder.build();
   }

   @NonNull
   ArrayList<android.bluetooth.le.ScanFilter> toNativeScanFilters(@NonNull List<ScanFilter> filters) {
      ArrayList<android.bluetooth.le.ScanFilter> nativeScanFilters = new ArrayList();
      Iterator var3 = filters.iterator();

      while(var3.hasNext()) {
         ScanFilter filter = (ScanFilter)var3.next();
         nativeScanFilters.add(this.toNativeScanFilter(filter));
      }

      return nativeScanFilters;
   }

   @NonNull
   android.bluetooth.le.ScanFilter toNativeScanFilter(@NonNull ScanFilter filter) {
      android.bluetooth.le.ScanFilter.Builder builder = new android.bluetooth.le.ScanFilter.Builder();
      builder.setServiceUuid(filter.getServiceUuid(), filter.getServiceUuidMask()).setManufacturerData(filter.getManufacturerId(), filter.getManufacturerData(), filter.getManufacturerDataMask());
      if (filter.getDeviceAddress() != null) {
         builder.setDeviceAddress(filter.getDeviceAddress());
      }

      if (filter.getDeviceName() != null) {
         builder.setDeviceName(filter.getDeviceName());
      }

      if (filter.getServiceDataUuid() != null) {
         builder.setServiceData(filter.getServiceDataUuid(), filter.getServiceData(), filter.getServiceDataMask());
      }

      return builder.build();
   }

   @NonNull
   ScanResult fromNativeScanResult(@NonNull android.bluetooth.le.ScanResult nativeScanResult) {
      byte[] data = nativeScanResult.getScanRecord() != null ? nativeScanResult.getScanRecord().getBytes() : null;
      return new ScanResult(nativeScanResult.getDevice(), ScanRecord.parseFromBytes(data), nativeScanResult.getRssi(), nativeScanResult.getTimestampNanos());
   }

   @NonNull
   ArrayList<ScanResult> fromNativeScanResults(@NonNull List<android.bluetooth.le.ScanResult> nativeScanResults) {
      ArrayList<ScanResult> results = new ArrayList();
      Iterator var3 = nativeScanResults.iterator();

      while(var3.hasNext()) {
         android.bluetooth.le.ScanResult nativeScanResult = (android.bluetooth.le.ScanResult)var3.next();
         ScanResult result = this.fromNativeScanResult(nativeScanResult);
         results.add(result);
      }

      return results;
   }

   static class ScanCallbackWrapperLollipop extends BluetoothLeScannerCompat.ScanCallbackWrapper {
      @NonNull
      private final android.bluetooth.le.ScanCallback nativeCallback;

      private ScanCallbackWrapperLollipop(boolean offloadedBatchingSupported, boolean offloadedFilteringSupported, @NonNull List<ScanFilter> filters, @NonNull ScanSettings settings, @NonNull ScanCallback callback, @NonNull Handler handler) {
         super(offloadedBatchingSupported, offloadedFilteringSupported, filters, settings, callback, handler);
         this.nativeCallback = new android.bluetooth.le.ScanCallback() {
            private long lastBatchTimestamp;

            public void onScanResult(int callbackType, android.bluetooth.le.ScanResult nativeScanResult) {
               ScanCallbackWrapperLollipop.this.handler.post(() -> {
                  BluetoothLeScannerImplLollipop scannerImpl = (BluetoothLeScannerImplLollipop)BluetoothLeScannerCompat.getScanner();
                  ScanResult result = scannerImpl.fromNativeScanResult(nativeScanResult);
                  ScanCallbackWrapperLollipop.this.handleScanResult(callbackType, result);
               });
            }

            public void onBatchScanResults(List<android.bluetooth.le.ScanResult> nativeScanResults) {
               ScanCallbackWrapperLollipop.this.handler.post(() -> {
                  long now = SystemClock.elapsedRealtime();
                  if (this.lastBatchTimestamp <= now - ScanCallbackWrapperLollipop.this.scanSettings.getReportDelayMillis() + 5L) {
                     this.lastBatchTimestamp = now;
                     BluetoothLeScannerImplLollipop scannerImpl = (BluetoothLeScannerImplLollipop)BluetoothLeScannerCompat.getScanner();
                     List<ScanResult> results = scannerImpl.fromNativeScanResults(nativeScanResults);
                     ScanCallbackWrapperLollipop.this.handleScanResults(results);
                  }
               });
            }

            public void onScanFailed(int errorCode) {
               ScanCallbackWrapperLollipop.this.handler.post(() -> {
                  if (ScanCallbackWrapperLollipop.this.scanSettings.getUseHardwareCallbackTypesIfSupported() && ScanCallbackWrapperLollipop.this.scanSettings.getCallbackType() != 1) {
                     ScanCallbackWrapperLollipop.this.scanSettings.disableUseHardwareCallbackTypes();
                     BluetoothLeScannerCompat scanner = BluetoothLeScannerCompat.getScanner();

                     try {
                        scanner.stopScan(ScanCallbackWrapperLollipop.this.scanCallback);
                     } catch (Exception var5) {
                     }

                     try {
                        scanner.startScanInternal(ScanCallbackWrapperLollipop.this.filters, ScanCallbackWrapperLollipop.this.scanSettings, ScanCallbackWrapperLollipop.this.scanCallback, ScanCallbackWrapperLollipop.this.handler);
                     } catch (Exception var4) {
                     }

                  } else {
                     ScanCallbackWrapperLollipop.this.handleScanError(errorCode);
                  }
               });
            }
         };
      }

      // $FF: synthetic method
      ScanCallbackWrapperLollipop(boolean x0, boolean x1, List x2, ScanSettings x3, ScanCallback x4, Handler x5, Object x6) {
         this(x0, x1, x2, x3, x4, x5);
      }
   }
}
