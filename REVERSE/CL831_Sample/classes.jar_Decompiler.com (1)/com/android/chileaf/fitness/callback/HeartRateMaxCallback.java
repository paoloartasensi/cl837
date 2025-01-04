package com.android.chileaf.fitness.callback;

import android.bluetooth.BluetoothDevice;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;

public interface HeartRateMaxCallback {
   void onHeartRateMaxReceived(@NonNull final BluetoothDevice device, @IntRange(from = 0L) final int max);
}
