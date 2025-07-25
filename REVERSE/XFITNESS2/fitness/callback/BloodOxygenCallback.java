package com.android.chileaf.fitness.callback;

import android.bluetooth.BluetoothDevice;
import androidx.annotation.NonNull;

public interface BloodOxygenCallback {
   void onBloodOxygenReceived(@NonNull final BluetoothDevice device, int bSwitch, String value, int gesture, int piValue, int Onwrist);
}
