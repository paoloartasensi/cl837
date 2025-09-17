package com.android.chileaf.fitness.callback;

import android.bluetooth.BluetoothDevice;
import androidx.annotation.NonNull;

public interface TemperatureCallback {
  void onTemperatureReceived(@NonNull BluetoothDevice paramBluetoothDevice, float paramFloat1, float paramFloat2, float paramFloat3);
}


/* Location:              C:\Users\Admin\Downloads\jd-gui-windows-1.6.6\classes.jar!\com\android\chileaf\fitness\callback\TemperatureCallback.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */