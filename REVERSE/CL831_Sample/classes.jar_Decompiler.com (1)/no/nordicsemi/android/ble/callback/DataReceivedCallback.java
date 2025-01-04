package no.nordicsemi.android.ble.callback;

import android.bluetooth.BluetoothDevice;
import androidx.annotation.NonNull;
import no.nordicsemi.android.ble.data.Data;

@FunctionalInterface
public interface DataReceivedCallback {
   void onDataReceived(@NonNull BluetoothDevice var1, @NonNull Data var2);
}
