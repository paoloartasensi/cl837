package com.android.chileaf.fitness.callback;

import android.bluetooth.BluetoothDevice;
import androidx.annotation.NonNull;

public interface AccelerometerCallback {
   void onAccelerometerReceived(@NonNull final BluetoothDevice device, int x, int y, int z);
}
