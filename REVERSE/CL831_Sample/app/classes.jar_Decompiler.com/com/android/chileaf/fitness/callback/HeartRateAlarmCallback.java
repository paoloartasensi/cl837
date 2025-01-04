package com.android.chileaf.fitness.callback;

import android.bluetooth.BluetoothDevice;
import androidx.annotation.NonNull;

public interface HeartRateAlarmCallback {
   void onHeartRateAlarmReceived(@NonNull final BluetoothDevice device, long stamp, boolean enabled);
}
