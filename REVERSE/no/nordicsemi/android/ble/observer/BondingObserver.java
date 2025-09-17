package no.nordicsemi.android.ble.observer;

import android.bluetooth.BluetoothDevice;
import androidx.annotation.NonNull;

public interface BondingObserver {
  void onBondingRequired(@NonNull BluetoothDevice paramBluetoothDevice);
  
  void onBonded(@NonNull BluetoothDevice paramBluetoothDevice);
  
  void onBondingFailed(@NonNull BluetoothDevice paramBluetoothDevice);
}


/* Location:              C:\Users\Admin\Downloads\jd-gui-windows-1.6.6\classes.jar!\no\nordicsemi\android\ble\observer\BondingObserver.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */