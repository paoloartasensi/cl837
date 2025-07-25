package com.android.chileaf.fitness.callback;

import android.bluetooth.BluetoothDevice;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;

public interface Sensor3DFrequencyCallback {
   void onSensor3DFrequencyReceived(@NonNull final BluetoothDevice device, @IntRange(from = 0L,to = 4L) final int frequency);
}
