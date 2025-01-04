package no.nordicsemi.android.ble.callback;

import android.bluetooth.BluetoothDevice;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

@FunctionalInterface
public interface ReadProgressCallback {
   void onPacketReceived(@NonNull BluetoothDevice var1, @Nullable byte[] var2, @IntRange(from = 0L) int var3);
}
