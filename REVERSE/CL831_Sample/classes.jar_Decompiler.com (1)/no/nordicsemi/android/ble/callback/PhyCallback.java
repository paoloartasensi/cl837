package no.nordicsemi.android.ble.callback;

import android.bluetooth.BluetoothDevice;
import androidx.annotation.NonNull;

@FunctionalInterface
public interface PhyCallback {
   int PHY_LE_1M = 1;
   int PHY_LE_2M = 2;
   int PHY_LE_CODED = 3;

   void onPhyChanged(@NonNull BluetoothDevice var1, int var2, int var3);
}
