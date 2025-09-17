package com.android.chileaf.fitness.callback;

import android.bluetooth.BluetoothDevice;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;

public interface Sensor6DFrequencyCallback {
  void onSensor6DFrequencyReceived(@NonNull BluetoothDevice paramBluetoothDevice, @IntRange(from = 0L, to = 3L) int paramInt);
}


/* Location:              C:\Users\Admin\Downloads\jd-gui-windows-1.6.6\classes.jar!\com\android\chileaf\fitness\callback\Sensor6DFrequencyCallback.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */