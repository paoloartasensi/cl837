package no.nordicsemi.android.ble.callback;

import android.bluetooth.BluetoothDevice;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;

@FunctionalInterface
public interface ConnectionParametersUpdatedCallback {
  void onConnectionUpdated(@NonNull BluetoothDevice paramBluetoothDevice, @IntRange(from = 6L, to = 3200L) int paramInt1, @IntRange(from = 0L, to = 499L) int paramInt2, @IntRange(from = 10L, to = 3200L) int paramInt3);
}


/* Location:              C:\Users\Admin\Downloads\jd-gui-windows-1.6.6\classes.jar!\no\nordicsemi\android\ble\callback\ConnectionParametersUpdatedCallback.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */