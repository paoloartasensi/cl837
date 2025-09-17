package com.android.chileaf.fitness.common;

import androidx.annotation.NonNull;
import java.util.List;
import no.nordicsemi.android.support.v18.scanner.ScanResult;

public interface FilterScanCallback {
  void onFilterScanResults(@NonNull List<ScanResult> paramList);
  
  default void onScanResult(int callbackType, @NonNull ScanResult result) {}
  
  default void onBatchScanResults(@NonNull List<ScanResult> results) {}
  
  default void onScanFailed(int errorCode) {}
}


/* Location:              C:\Users\Admin\Downloads\jd-gui-windows-1.6.6\classes.jar!\com\android\chileaf\fitness\common\FilterScanCallback.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */