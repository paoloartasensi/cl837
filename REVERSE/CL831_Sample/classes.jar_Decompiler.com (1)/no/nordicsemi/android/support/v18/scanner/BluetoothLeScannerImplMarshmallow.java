package no.nordicsemi.android.support.v18.scanner;

import android.annotation.TargetApi;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.le.ScanSettings.Builder;
import androidx.annotation.NonNull;

@TargetApi(23)
class BluetoothLeScannerImplMarshmallow extends BluetoothLeScannerImplLollipop {
   @NonNull
   android.bluetooth.le.ScanSettings toNativeScanSettings(@NonNull BluetoothAdapter adapter, @NonNull ScanSettings settings, boolean exactCopy) {
      Builder builder = new Builder();
      if (exactCopy || adapter.isOffloadedScanBatchingSupported() && settings.getUseHardwareBatchingIfSupported()) {
         builder.setReportDelay(settings.getReportDelayMillis());
      }

      if (exactCopy || settings.getUseHardwareCallbackTypesIfSupported()) {
         builder.setCallbackType(settings.getCallbackType()).setMatchMode(settings.getMatchMode()).setNumOfMatches(settings.getNumOfMatches());
      }

      builder.setScanMode(settings.getScanMode());
      return builder.build();
   }
}
