package no.nordicsemi.android.ble.observer;

import android.bluetooth.BluetoothDevice;
import androidx.annotation.NonNull;

public interface BondingObserver {
   void onBondingRequired(@NonNull BluetoothDevice var1);

   void onBonded(@NonNull BluetoothDevice var1);

   void onBondingFailed(@NonNull BluetoothDevice var1);
}
