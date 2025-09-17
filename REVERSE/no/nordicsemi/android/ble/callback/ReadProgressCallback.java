package no.nordicsemi.android.ble.callback;

import android.bluetooth.BluetoothDevice;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

@FunctionalInterface
public interface ReadProgressCallback {
  void onPacketReceived(@NonNull BluetoothDevice paramBluetoothDevice, @Nullable byte[] paramArrayOfbyte, @IntRange(from = 0L) int paramInt);
}


/* Location:              C:\Users\Admin\Downloads\jd-gui-windows-1.6.6\classes.jar!\no\nordicsemi\android\ble\callback\ReadProgressCallback.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */