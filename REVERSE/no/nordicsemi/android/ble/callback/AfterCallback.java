package no.nordicsemi.android.ble.callback;

import android.bluetooth.BluetoothDevice;
import androidx.annotation.NonNull;

@FunctionalInterface
public interface AfterCallback {
  void onRequestFinished(@NonNull BluetoothDevice paramBluetoothDevice);
}


/* Location:              C:\Users\Admin\Downloads\jd-gui-windows-1.6.6\classes.jar!\no\nordicsemi\android\ble\callback\AfterCallback.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */