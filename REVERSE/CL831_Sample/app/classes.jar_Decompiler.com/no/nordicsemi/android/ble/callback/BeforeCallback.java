package no.nordicsemi.android.ble.callback;

import android.bluetooth.BluetoothDevice;
import androidx.annotation.NonNull;

@FunctionalInterface
public interface BeforeCallback {
   void onRequestStarted(@NonNull BluetoothDevice var1);
}
