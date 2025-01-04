package no.nordicsemi.android.ble.observer;

import android.bluetooth.BluetoothDevice;
import androidx.annotation.NonNull;

public interface ServerObserver {
   void onServerReady();

   void onDeviceConnectedToServer(@NonNull BluetoothDevice var1);

   void onDeviceDisconnectedFromServer(@NonNull BluetoothDevice var1);
}
