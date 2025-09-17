package no.nordicsemi.android.ble.observer;

import android.bluetooth.BluetoothDevice;
import androidx.annotation.NonNull;

public interface ServerObserver {
  void onServerReady();
  
  void onDeviceConnectedToServer(@NonNull BluetoothDevice paramBluetoothDevice);
  
  void onDeviceDisconnectedFromServer(@NonNull BluetoothDevice paramBluetoothDevice);
}


/* Location:              C:\Users\Admin\Downloads\jd-gui-windows-1.6.6\classes.jar!\no\nordicsemi\android\ble\observer\ServerObserver.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */