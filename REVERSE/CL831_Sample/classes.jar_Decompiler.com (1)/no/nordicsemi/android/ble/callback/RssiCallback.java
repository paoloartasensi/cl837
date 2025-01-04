package no.nordicsemi.android.ble.callback;

import android.bluetooth.BluetoothDevice;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;

@FunctionalInterface
public interface RssiCallback {
   void onRssiRead(@NonNull BluetoothDevice var1, @IntRange(from = -128L,to = 20L) int var2);
}
