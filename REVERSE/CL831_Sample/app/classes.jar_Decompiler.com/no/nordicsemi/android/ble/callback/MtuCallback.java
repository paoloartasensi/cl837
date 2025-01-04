package no.nordicsemi.android.ble.callback;

import android.bluetooth.BluetoothDevice;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;

@FunctionalInterface
public interface MtuCallback {
   void onMtuChanged(@NonNull BluetoothDevice var1, @IntRange(from = 23L,to = 517L) int var2);
}
