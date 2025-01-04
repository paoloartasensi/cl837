package no.nordicsemi.android.support.v18.scanner;

import android.app.PendingIntent;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import androidx.annotation.RequiresApi;
import java.util.ArrayList;
import java.util.List;

public class PendingIntentReceiver extends BroadcastReceiver {
   static final String ACTION = "no.nordicsemi.android.support.v18.ACTION_FOUND";
   static final String EXTRA_PENDING_INTENT = "no.nordicsemi.android.support.v18.EXTRA_PENDING_INTENT";
   static final String EXTRA_FILTERS = "no.nordicsemi.android.support.v18.EXTRA_FILTERS";
   static final String EXTRA_SETTINGS = "no.nordicsemi.android.support.v18.EXTRA_SETTINGS";
   static final String EXTRA_USE_HARDWARE_BATCHING = "no.nordicsemi.android.support.v18.EXTRA_USE_HARDWARE_BATCHING";
   static final String EXTRA_USE_HARDWARE_FILTERING = "no.nordicsemi.android.support.v18.EXTRA_USE_HARDWARE_FILTERING";
   static final String EXTRA_USE_HARDWARE_CALLBACK_TYPES = "no.nordicsemi.android.support.v18.EXTRA_USE_HARDWARE_CALLBACK_TYPES";
   static final String EXTRA_MATCH_LOST_TIMEOUT = "no.nordicsemi.android.support.v18.EXTRA_MATCH_LOST_TIMEOUT";
   static final String EXTRA_MATCH_LOST_INTERVAL = "no.nordicsemi.android.support.v18.EXTRA_MATCH_LOST_INTERVAL";
   static final String EXTRA_MATCH_MODE = "no.nordicsemi.android.support.v18.EXTRA_MATCH_MODE";
   static final String EXTRA_NUM_OF_MATCHES = "no.nordicsemi.android.support.v18.EXTRA_NUM_OF_MATCHES";

   @RequiresApi(
      api = 26
   )
   public void onReceive(Context context, Intent intent) {
      if (context != null && intent != null) {
         PendingIntent callbackIntent = (PendingIntent)intent.getParcelableExtra("no.nordicsemi.android.support.v18.EXTRA_PENDING_INTENT");
         if (callbackIntent != null) {
            ArrayList<android.bluetooth.le.ScanFilter> nativeScanFilters = intent.getParcelableArrayListExtra("no.nordicsemi.android.support.v18.EXTRA_FILTERS");
            android.bluetooth.le.ScanSettings nativeScanSettings = (android.bluetooth.le.ScanSettings)intent.getParcelableExtra("no.nordicsemi.android.support.v18.EXTRA_SETTINGS");
            if (nativeScanFilters != null && nativeScanSettings != null) {
               boolean useHardwareBatchingIfSupported = intent.getBooleanExtra("no.nordicsemi.android.support.v18.EXTRA_USE_HARDWARE_BATCHING", true);
               boolean useHardwareFilteringIfSupported = intent.getBooleanExtra("no.nordicsemi.android.support.v18.EXTRA_USE_HARDWARE_FILTERING", true);
               boolean useHardwareCallbackTypesIfSupported = intent.getBooleanExtra("no.nordicsemi.android.support.v18.EXTRA_USE_HARDWARE_CALLBACK_TYPES", true);
               long matchLostDeviceTimeout = intent.getLongExtra("no.nordicsemi.android.support.v18.EXTRA_MATCH_LOST_TIMEOUT", 10000L);
               long matchLostTaskInterval = intent.getLongExtra("no.nordicsemi.android.support.v18.EXTRA_MATCH_LOST_INTERVAL", 10000L);
               int matchMode = intent.getIntExtra("no.nordicsemi.android.support.v18.EXTRA_MATCH_MODE", 1);
               int numOfMatches = intent.getIntExtra("no.nordicsemi.android.support.v18.EXTRA_NUM_OF_MATCHES", 3);
               BluetoothLeScannerCompat scanner = BluetoothLeScannerCompat.getScanner();
               BluetoothLeScannerImplOreo scannerImpl = (BluetoothLeScannerImplOreo)scanner;
               ArrayList<ScanFilter> filters = scannerImpl.fromNativeScanFilters(nativeScanFilters);
               ScanSettings settings = scannerImpl.fromNativeScanSettings(nativeScanSettings, useHardwareBatchingIfSupported, useHardwareFilteringIfSupported, useHardwareCallbackTypesIfSupported, matchLostDeviceTimeout, matchLostTaskInterval, matchMode, numOfMatches);
               BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
               boolean offloadedBatchingSupported = adapter.isOffloadedScanBatchingSupported();
               boolean offloadedFilteringSupported = adapter.isOffloadedFilteringSupported();
               BluetoothLeScannerImplOreo.PendingIntentExecutorWrapper wrapper;
               synchronized(scanner) {
                  try {
                     wrapper = scannerImpl.getWrapper(callbackIntent);
                  } catch (IllegalStateException var26) {
                     return;
                  }

                  if (wrapper == null) {
                     PendingIntentExecutor executor = new PendingIntentExecutor(callbackIntent, settings);
                     wrapper = new BluetoothLeScannerImplOreo.PendingIntentExecutorWrapper(offloadedBatchingSupported, offloadedFilteringSupported, filters, settings, executor);
                     scannerImpl.addWrapper(callbackIntent, wrapper);
                  }
               }

               wrapper.executor.setTemporaryContext(context);
               List<android.bluetooth.le.ScanResult> nativeScanResults = intent.getParcelableArrayListExtra("android.bluetooth.le.extra.LIST_SCAN_RESULT");
               if (nativeScanResults != null) {
                  ArrayList<ScanResult> results = scannerImpl.fromNativeScanResults(nativeScanResults);
                  if (settings.getReportDelayMillis() > 0L) {
                     wrapper.handleScanResults(results);
                  } else if (!results.isEmpty()) {
                     int callbackType = intent.getIntExtra("android.bluetooth.le.extra.CALLBACK_TYPE", 1);
                     wrapper.handleScanResult(callbackType, (ScanResult)results.get(0));
                  }
               } else {
                  int errorCode = intent.getIntExtra("android.bluetooth.le.extra.ERROR_CODE", 0);
                  if (errorCode != 0) {
                     wrapper.handleScanError(errorCode);
                  }
               }

               wrapper.executor.setTemporaryContext((Context)null);
            }
         }
      }
   }
}
