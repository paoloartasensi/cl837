package no.nordicsemi.android.ble.callback;

import android.bluetooth.BluetoothDevice;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;

@FunctionalInterface
public interface RssiCallback {
  void onRssiRead(@NonNull BluetoothDevice paramBluetoothDevice, @IntRange(from = -128L, to = 20L) int paramInt);
}


/* Location:              C:\Users\Admin\Downloads\jd-gui-windows-1.6.6\classes.jar!\no\nordicsemi\android\ble\callback\RssiCallback.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */