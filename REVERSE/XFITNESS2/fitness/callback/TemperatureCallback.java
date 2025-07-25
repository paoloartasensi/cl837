package com.android.chileaf.fitness.callback;

import android.bluetooth.BluetoothDevice;
import androidx.annotation.NonNull;

public interface TemperatureCallback {
   void onTemperatureReceived(@NonNull final BluetoothDevice device, float environment, float wrist, float body);
}
