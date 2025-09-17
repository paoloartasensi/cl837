package no.nordicsemi.android.support.v18.scanner;

import androidx.annotation.NonNull;
import java.util.List;

public abstract class ScanCallback {
  public static final int SCAN_FAILED_ALREADY_STARTED = 1;
  
  public static final int SCAN_FAILED_APPLICATION_REGISTRATION_FAILED = 2;
  
  public static final int SCAN_FAILED_INTERNAL_ERROR = 3;
  
  public static final int SCAN_FAILED_FEATURE_UNSUPPORTED = 4;
  
  public static final int SCAN_FAILED_OUT_OF_HARDWARE_RESOURCES = 5;
  
  public static final int SCAN_FAILED_SCANNING_TOO_FREQUENTLY = 6;
  
  static final int NO_ERROR = 0;
  
  public void onScanResult(int callbackType, @NonNull ScanResult result) {}
  
  public void onBatchScanResults(@NonNull List<ScanResult> results) {}
  
  public void onScanFailed(int errorCode) {}
}


/* Location:              C:\Users\Admin\Downloads\jd-gui-windows-1.6.6\classes.jar!\no\nordicsemi\android\support\v18\scanner\ScanCallback.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */