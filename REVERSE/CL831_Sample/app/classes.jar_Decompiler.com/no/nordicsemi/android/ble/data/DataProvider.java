package no.nordicsemi.android.ble.data;

import android.bluetooth.BluetoothDevice;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public interface DataProvider {
   @Nullable
   byte[] getData(@NonNull BluetoothDevice var1);
}
