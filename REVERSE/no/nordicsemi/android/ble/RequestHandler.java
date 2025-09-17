package no.nordicsemi.android.ble;

import android.bluetooth.BluetoothDevice;
import androidx.annotation.NonNull;

abstract class RequestHandler implements CallbackHandler {
  abstract void enqueue(@NonNull Request paramRequest);
  
  abstract void cancelQueue();
  
  abstract void cancelCurrent();
  
  abstract void onRequestTimeout(@NonNull BluetoothDevice paramBluetoothDevice, @NonNull TimeoutableRequest paramTimeoutableRequest);
}


/* Location:              C:\Users\Admin\Downloads\jd-gui-windows-1.6.6\classes.jar!\no\nordicsemi\android\ble\RequestHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */