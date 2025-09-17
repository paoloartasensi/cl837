package no.nordicsemi.android.ble.data;

import android.bluetooth.BluetoothDevice;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public interface DataProvider {
  @Nullable
  byte[] getData(@NonNull BluetoothDevice paramBluetoothDevice);
}


/* Location:              C:\Users\Admin\Downloads\jd-gui-windows-1.6.6\classes.jar!\no\nordicsemi\android\ble\data\DataProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */