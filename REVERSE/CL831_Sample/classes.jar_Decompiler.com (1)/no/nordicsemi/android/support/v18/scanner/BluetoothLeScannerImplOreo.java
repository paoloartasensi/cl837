package no.nordicsemi.android.support.v18.scanner;

import android.annotation.TargetApi;
import android.app.PendingIntent;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanSettings.Builder;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Build.VERSION;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

@TargetApi(26)
class BluetoothLeScannerImplOreo extends BluetoothLeScannerImplMarshmallow {
   @NonNull
   private final HashMap<PendingIntent, BluetoothLeScannerImplOreo.PendingIntentExecutorWrapper> wrappers = new HashMap();

   @Nullable
   BluetoothLeScannerImplOreo.PendingIntentExecutorWrapper getWrapper(@NonNull PendingIntent callbackIntent) {
      synchronized(this.wrappers) {
         if (this.wrappers.containsKey(callbackIntent)) {
            BluetoothLeScannerImplOreo.PendingIntentExecutorWrapper wrapper = (BluetoothLeScannerImplOreo.PendingIntentExecutorWrapper)this.wrappers.get(callbackIntent);
            if (wrapper == null) {
               throw new IllegalStateException("Scanning has been stopped");
            } else {
               return wrapper;
            }
         } else {
            return null;
         }
      }
   }

   void addWrapper(@NonNull PendingIntent callbackIntent, @NonNull BluetoothLeScannerImplOreo.PendingIntentExecutorWrapper wrapper) {
      synchronized(this.wrappers) {
         this.wrappers.put(callbackIntent, wrapper);
      }
   }

   void startScanInternal(@Nullable List<ScanFilter> filters, @Nullable ScanSettings settings, @NonNull Context context, @NonNull PendingIntent callbackIntent, int requestCode) {
      BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
      BluetoothLeScanner scanner = adapter.getBluetoothLeScanner();
      if (scanner == null) {
         throw new IllegalStateException("BT le scanner not available");
      } else {
         ScanSettings nonNullSettings = settings != null ? settings : (new ScanSettings.Builder()).build();
         List<ScanFilter> nonNullFilters = filters != null ? filters : Collections.emptyList();
         android.bluetooth.le.ScanSettings nativeSettings = this.toNativeScanSettings(adapter, nonNullSettings, false);
         List<android.bluetooth.le.ScanFilter> nativeFilters = null;
         if (filters != null && adapter.isOffloadedFilteringSupported() && nonNullSettings.getUseHardwareFilteringIfSupported()) {
            nativeFilters = this.toNativeScanFilters(filters);
         }

         synchronized(this.wrappers) {
            this.wrappers.remove(callbackIntent);
         }

         PendingIntent pendingIntent = this.createStartingPendingIntent(nonNullFilters, nonNullSettings, context, callbackIntent, requestCode);
         scanner.startScan(nativeFilters, nativeSettings, pendingIntent);
      }
   }

   void stopScanInternal(@NonNull Context context, @NonNull PendingIntent callbackIntent, int requestCode) {
      BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
      BluetoothLeScanner scanner = adapter.getBluetoothLeScanner();
      if (scanner == null) {
         throw new IllegalStateException("BT le scanner not available");
      } else {
         PendingIntent pendingIntent = this.createStoppingPendingIntent(context, requestCode);
         scanner.stopScan(pendingIntent);
         synchronized(this.wrappers) {
            this.wrappers.put(callbackIntent, (Object)null);
         }
      }
   }

   @NonNull
   private PendingIntent createStartingPendingIntent(@NonNull List<ScanFilter> filters, @NonNull ScanSettings settings, @NonNull Context context, @NonNull PendingIntent callbackIntent, int requestCode) {
      Intent intent = new Intent(context, PendingIntentReceiver.class);
      intent.setAction("no.nordicsemi.android.support.v18.ACTION_FOUND");
      BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
      intent.putExtra("no.nordicsemi.android.support.v18.EXTRA_PENDING_INTENT", callbackIntent);
      intent.putParcelableArrayListExtra("no.nordicsemi.android.support.v18.EXTRA_FILTERS", this.toNativeScanFilters(filters));
      intent.putExtra("no.nordicsemi.android.support.v18.EXTRA_SETTINGS", this.toNativeScanSettings(adapter, settings, true));
      intent.putExtra("no.nordicsemi.android.support.v18.EXTRA_USE_HARDWARE_BATCHING", settings.getUseHardwareBatchingIfSupported());
      intent.putExtra("no.nordicsemi.android.support.v18.EXTRA_USE_HARDWARE_FILTERING", settings.getUseHardwareFilteringIfSupported());
      intent.putExtra("no.nordicsemi.android.support.v18.EXTRA_USE_HARDWARE_CALLBACK_TYPES", settings.getUseHardwareCallbackTypesIfSupported());
      intent.putExtra("no.nordicsemi.android.support.v18.EXTRA_MATCH_MODE", settings.getMatchMode());
      intent.putExtra("no.nordicsemi.android.support.v18.EXTRA_NUM_OF_MATCHES", settings.getNumOfMatches());
      int flags = 134217728;
      if (VERSION.SDK_INT >= 31) {
         flags |= 33554432;
      }

      return PendingIntent.getBroadcast(context, requestCode, intent, flags);
   }

   @NonNull
   private PendingIntent createStoppingPendingIntent(@NonNull Context context, int requestCode) {
      Intent intent = new Intent(context, PendingIntentReceiver.class);
      intent.setAction("no.nordicsemi.android.support.v18.ACTION_FOUND");
      int flags = 134217728;
      if (VERSION.SDK_INT >= 23) {
         flags |= 67108864;
      }

      return PendingIntent.getBroadcast(context, requestCode, intent, flags);
   }

   @NonNull
   android.bluetooth.le.ScanSettings toNativeScanSettings(@NonNull BluetoothAdapter adapter, @NonNull ScanSettings settings, boolean exactCopy) {
      Builder builder = new Builder();
      if (exactCopy || adapter.isOffloadedScanBatchingSupported() && settings.getUseHardwareBatchingIfSupported()) {
         builder.setReportDelay(settings.getReportDelayMillis());
      }

      if (exactCopy || settings.getUseHardwareCallbackTypesIfSupported()) {
         builder.setCallbackType(settings.getCallbackType()).setMatchMode(settings.getMatchMode()).setNumOfMatches(settings.getNumOfMatches());
      }

      builder.setScanMode(settings.getScanMode()).setLegacy(settings.getLegacy()).setPhy(settings.getPhy());
      return builder.build();
   }

   @NonNull
   ScanSettings fromNativeScanSettings(@NonNull android.bluetooth.le.ScanSettings settings, boolean useHardwareBatchingIfSupported, boolean useHardwareFilteringIfSupported, boolean useHardwareCallbackTypesIfSupported, long matchLostDeviceTimeout, long matchLostTaskInterval, int matchMode, int numOfMatches) {
      ScanSettings.Builder builder = (new ScanSettings.Builder()).setLegacy(settings.getLegacy()).setPhy(settings.getPhy()).setCallbackType(settings.getCallbackType()).setScanMode(settings.getScanMode()).setReportDelay(settings.getReportDelayMillis()).setUseHardwareBatchingIfSupported(useHardwareBatchingIfSupported).setUseHardwareFilteringIfSupported(useHardwareFilteringIfSupported).setUseHardwareCallbackTypesIfSupported(useHardwareCallbackTypesIfSupported).setMatchOptions(matchLostDeviceTimeout, matchLostTaskInterval).setMatchMode(matchMode).setNumOfMatches(numOfMatches);
      return builder.build();
   }

   @NonNull
   ArrayList<ScanFilter> fromNativeScanFilters(@NonNull List<android.bluetooth.le.ScanFilter> filters) {
      ArrayList<ScanFilter> nativeScanFilters = new ArrayList();
      Iterator var3 = filters.iterator();

      while(var3.hasNext()) {
         android.bluetooth.le.ScanFilter filter = (android.bluetooth.le.ScanFilter)var3.next();
         nativeScanFilters.add(this.fromNativeScanFilter(filter));
      }

      return nativeScanFilters;
   }

   @NonNull
   ScanFilter fromNativeScanFilter(@NonNull android.bluetooth.le.ScanFilter filter) {
      ScanFilter.Builder builder = new ScanFilter.Builder();
      builder.setDeviceAddress(filter.getDeviceAddress()).setDeviceName(filter.getDeviceName()).setServiceUuid(filter.getServiceUuid(), filter.getServiceUuidMask()).setManufacturerData(filter.getManufacturerId(), filter.getManufacturerData(), filter.getManufacturerDataMask());
      if (filter.getServiceDataUuid() != null) {
         builder.setServiceData(filter.getServiceDataUuid(), filter.getServiceData(), filter.getServiceDataMask());
      }

      return builder.build();
   }

   @NonNull
   ScanResult fromNativeScanResult(@NonNull android.bluetooth.le.ScanResult result) {
      int eventType = result.getDataStatus() << 5 | (result.isLegacy() ? 16 : 0) | (result.isConnectable() ? 1 : 0);
      byte[] data = result.getScanRecord() != null ? result.getScanRecord().getBytes() : null;
      return new ScanResult(result.getDevice(), eventType, result.getPrimaryPhy(), result.getSecondaryPhy(), result.getAdvertisingSid(), result.getTxPower(), result.getRssi(), result.getPeriodicAdvertisingInterval(), ScanRecord.parseFromBytes(data), result.getTimestampNanos());
   }

   static class PendingIntentExecutorWrapper extends BluetoothLeScannerCompat.ScanCallbackWrapper {
      @NonNull
      final PendingIntentExecutor executor;

      PendingIntentExecutorWrapper(boolean offloadedBatchingSupported, boolean offloadedFilteringSupported, @NonNull List<ScanFilter> filters, @NonNull ScanSettings settings, @NonNull PendingIntentExecutor executor) {
         super(offloadedBatchingSupported, offloadedFilteringSupported, filters, settings, executor, new Handler());
         this.executor = executor;
      }
   }
}
