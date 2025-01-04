package no.nordicsemi.android.ble.callback;

import android.bluetooth.BluetoothDevice;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;

@FunctionalInterface
public interface ConnectionParametersUpdatedCallback {
   void onConnectionUpdated(@NonNull BluetoothDevice var1, @IntRange(from = 6L,to = 3200L) int var2, @IntRange(from = 0L,to = 499L) int var3, @IntRange(from = 10L,to = 3200L) int var4);
}
