package no.nordicsemi.android.ble.callback;

import android.bluetooth.BluetoothDevice;
import androidx.annotation.NonNull;

@FunctionalInterface
public interface FailCallback {
   int REASON_DEVICE_DISCONNECTED = -1;
   int REASON_DEVICE_NOT_SUPPORTED = -2;
   int REASON_NULL_ATTRIBUTE = -3;
   int REASON_REQUEST_FAILED = -4;
   int REASON_TIMEOUT = -5;
   int REASON_VALIDATION = -6;
   int REASON_CANCELLED = -7;
   int REASON_BLUETOOTH_DISABLED = -100;

   void onRequestFailed(@NonNull BluetoothDevice var1, int var2);
}
