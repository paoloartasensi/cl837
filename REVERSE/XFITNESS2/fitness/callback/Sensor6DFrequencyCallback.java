package com.android.chileaf.fitness.callback;

import android.bluetooth.BluetoothDevice;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;

public interface Sensor6DFrequencyCallback {
   void onSensor6DFrequencyReceived(@NonNull final BluetoothDevice device, @IntRange(from = 0L,to = 3L) final int frequency);
}
