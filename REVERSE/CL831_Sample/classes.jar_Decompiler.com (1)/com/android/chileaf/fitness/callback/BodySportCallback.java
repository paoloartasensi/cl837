package com.android.chileaf.fitness.callback;

import android.bluetooth.BluetoothDevice;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;

public interface BodySportCallback {
   void onSportReceived(@NonNull final BluetoothDevice device, @IntRange(from = 0L) final int step, @IntRange(from = 0L) final int distance, @IntRange(from = 0L) final int calorie);
}
