package com.android.chileaf.fitness.common;

import androidx.annotation.NonNull;
import java.util.List;
import no.nordicsemi.android.support.v18.scanner.ScanResult;

public interface FilterScanCallback {
   void onFilterScanResults(@NonNull final List<ScanResult> results);

   default void onScanResult(final int callbackType, @NonNull final ScanResult result) {
   }

   default void onBatchScanResults(@NonNull final List<ScanResult> results) {
   }

   default void onScanFailed(final int errorCode) {
   }
}
