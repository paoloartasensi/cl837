package no.nordicsemi.android.ble.callback;

import android.bluetooth.BluetoothDevice;
import androidx.annotation.NonNull;

@FunctionalInterface
public interface FailCallback {
  public static final int REASON_DEVICE_DISCONNECTED = -1;
  
  public static final int REASON_DEVICE_NOT_SUPPORTED = -2;
  
  public static final int REASON_NULL_ATTRIBUTE = -3;
  
  public static final int REASON_REQUEST_FAILED = -4;
  
  public static final int REASON_TIMEOUT = -5;
  
  public static final int REASON_VALIDATION = -6;
  
  public static final int REASON_CANCELLED = -7;
  
  public static final int REASON_BLUETOOTH_DISABLED = -100;
  
  void onRequestFailed(@NonNull BluetoothDevice paramBluetoothDevice, int paramInt);
}


/* Location:              C:\Users\Admin\Downloads\jd-gui-windows-1.6.6\classes.jar!\no\nordicsemi\android\ble\callback\FailCallback.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */