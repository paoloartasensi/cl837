package no.nordicsemi.android.ble.callback;

import android.bluetooth.BluetoothDevice;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;

@FunctionalInterface
public interface MtuCallback {
  void onMtuChanged(@NonNull BluetoothDevice paramBluetoothDevice, @IntRange(from = 23L, to = 517L) int paramInt);
}


/* Location:              C:\Users\Admin\Downloads\jd-gui-windows-1.6.6\classes.jar!\no\nordicsemi\android\ble\callback\MtuCallback.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */