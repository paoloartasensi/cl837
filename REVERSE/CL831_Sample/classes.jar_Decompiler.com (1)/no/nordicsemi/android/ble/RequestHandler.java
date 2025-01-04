package no.nordicsemi.android.ble;

import android.bluetooth.BluetoothDevice;
import androidx.annotation.NonNull;

abstract class RequestHandler implements CallbackHandler {
   abstract void enqueue(@NonNull Request var1);

   abstract void cancelQueue();

   abstract void cancelCurrent();

   abstract void onRequestTimeout(@NonNull BluetoothDevice var1, @NonNull TimeoutableRequest var2);
}
