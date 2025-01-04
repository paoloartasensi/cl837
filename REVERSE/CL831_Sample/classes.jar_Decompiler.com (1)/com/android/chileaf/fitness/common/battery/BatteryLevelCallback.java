package com.android.chileaf.fitness.common.battery;

import android.bluetooth.BluetoothDevice;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;

public interface BatteryLevelCallback {
   void onBatteryLevelChanged(@NonNull final BluetoothDevice device, @IntRange(from = 0L,to = 100L) final int batteryLevel);
}
