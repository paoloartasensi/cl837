package com.android.chileaf.fitness.callback;

import android.bluetooth.BluetoothDevice;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;

public interface HeartRateStatusCallback {
   void onHeartRateStatusReceived(@NonNull final BluetoothDevice device, @IntRange(from = 0L) final int min, @IntRange(from = 0L) final int max, @IntRange(from = 0L) final int goal);
}
