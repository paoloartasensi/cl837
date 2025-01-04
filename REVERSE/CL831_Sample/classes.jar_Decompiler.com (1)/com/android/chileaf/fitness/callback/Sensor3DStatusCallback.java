package com.android.chileaf.fitness.callback;

import android.bluetooth.BluetoothDevice;
import androidx.annotation.NonNull;

public interface Sensor3DStatusCallback {
   void onSensor3DStatusReceived(@NonNull final BluetoothDevice device, boolean enabled);
}
