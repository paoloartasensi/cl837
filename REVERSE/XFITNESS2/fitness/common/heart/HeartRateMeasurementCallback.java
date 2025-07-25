package com.android.chileaf.fitness.common.heart;

import android.bluetooth.BluetoothDevice;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.List;

public interface HeartRateMeasurementCallback {
   void onHeartRateMeasurementReceived(@NonNull final BluetoothDevice device, @IntRange(from = 0L) final int heartRate, @Nullable final Boolean contactDetected, @Nullable @IntRange(from = 0L) final Integer energyExpanded, @Nullable final List<Integer> rrIntervals);
}
