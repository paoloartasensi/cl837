package no.nordicsemi.android.ble.callback;

import android.bluetooth.BluetoothDevice;
import androidx.annotation.NonNull;
import no.nordicsemi.android.ble.data.Data;

@FunctionalInterface
public interface DataReceivedCallback {
  void onDataReceived(@NonNull BluetoothDevice paramBluetoothDevice, @NonNull Data paramData);
}


/* Location:              C:\Users\Admin\Downloads\jd-gui-windows-1.6.6\classes.jar!\no\nordicsemi\android\ble\callback\DataReceivedCallback.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */