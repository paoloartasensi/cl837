package com.android.chileaf.fitness.callback;

import android.bluetooth.BluetoothDevice;
import androidx.annotation.NonNull;

public interface Sensor6DRawDataCallback {
   void onSensor6DRawDataReceived(@NonNull final BluetoothDevice device, final long stamp, final int sequence, final int gyroscopeX, final int gyroscopeY, final int gyroscopeZ, final int accelerometerX, final int accelerometerY, final int accelerometerZ);
}
