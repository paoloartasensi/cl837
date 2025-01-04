package no.nordicsemi.android.ble.callback;

import android.bluetooth.BluetoothDevice;
import androidx.annotation.NonNull;

@FunctionalInterface
public interface AfterCallback {
   void onRequestFinished(@NonNull BluetoothDevice var1);
}
