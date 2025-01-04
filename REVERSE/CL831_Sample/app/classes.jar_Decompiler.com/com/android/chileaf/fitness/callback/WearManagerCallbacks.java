package com.android.chileaf.fitness.callback;

import android.bluetooth.BluetoothDevice;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.android.chileaf.fitness.FitnessManagerCallbacks;
import com.android.chileaf.fitness.common.heart.BodySensorLocationCallback;
import com.android.chileaf.fitness.common.heart.HeartRateMeasurementCallback;
import java.util.List;

public interface WearManagerCallbacks extends FitnessManagerCallbacks, BodySensorLocationCallback, HeartRateMeasurementCallback, BodySportCallback, BluetoothStatusCallback {
   default void onBodySensorLocationReceived(@NonNull final BluetoothDevice device, final int sensorLocation) {
   }

   default void onHeartRateMeasurementReceived(@NonNull final BluetoothDevice device, final int heartRate, @Nullable final Boolean contactDetected, @Nullable final Integer energyExpanded, @Nullable final List<Integer> rrIntervals) {
   }

   default void onSportReceived(@NonNull final BluetoothDevice device, final int step, final int distance, final int calorie) {
   }

   default void onBluetoothStatusReceived(@NonNull final BluetoothDevice device, boolean enabled) {
   }
}
