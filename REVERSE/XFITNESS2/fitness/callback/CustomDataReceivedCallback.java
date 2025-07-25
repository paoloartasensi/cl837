package com.android.chileaf.fitness.callback;

import android.bluetooth.BluetoothDevice;
import androidx.annotation.NonNull;

public interface CustomDataReceivedCallback {
   void onDataReceived(@NonNull final BluetoothDevice device, final byte[] data);
}
