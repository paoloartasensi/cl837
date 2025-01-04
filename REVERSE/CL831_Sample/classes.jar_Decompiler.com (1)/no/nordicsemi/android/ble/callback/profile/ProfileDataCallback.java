package no.nordicsemi.android.ble.callback.profile;

import android.bluetooth.BluetoothDevice;
import androidx.annotation.NonNull;
import no.nordicsemi.android.ble.callback.DataReceivedCallback;
import no.nordicsemi.android.ble.data.Data;

public interface ProfileDataCallback extends DataReceivedCallback {
   default void onInvalidDataReceived(@NonNull BluetoothDevice device, @NonNull Data data) {
   }
}
