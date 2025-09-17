package no.nordicsemi.android.ble.callback.profile;

import android.bluetooth.BluetoothDevice;
import androidx.annotation.NonNull;
import no.nordicsemi.android.ble.callback.DataReceivedCallback;
import no.nordicsemi.android.ble.data.Data;

public interface ProfileDataCallback extends DataReceivedCallback {
  default void onInvalidDataReceived(@NonNull BluetoothDevice device, @NonNull Data data) {}
}


/* Location:              C:\Users\Admin\Downloads\jd-gui-windows-1.6.6\classes.jar!\no\nordicsemi\android\ble\callback\profile\ProfileDataCallback.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */