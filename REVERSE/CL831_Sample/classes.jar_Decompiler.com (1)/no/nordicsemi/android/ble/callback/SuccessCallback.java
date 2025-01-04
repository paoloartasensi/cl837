package no.nordicsemi.android.ble.callback;

import android.bluetooth.BluetoothDevice;
import androidx.annotation.NonNull;

@FunctionalInterface
public interface SuccessCallback {
   void onRequestCompleted(@NonNull BluetoothDevice var1);
}
