package com.android.chileaf.fitness.callback;

import android.bluetooth.BluetoothDevice;
import androidx.annotation.NonNull;

public interface BluetoothStatusCallback {
   void onBluetoothStatusReceived(@NonNull final BluetoothDevice device, boolean enabled);
}
