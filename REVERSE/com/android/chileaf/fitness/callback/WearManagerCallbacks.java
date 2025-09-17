package com.android.chileaf.fitness.callback;

import android.bluetooth.BluetoothDevice;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.android.chileaf.fitness.FitnessManagerCallbacks;
import com.android.chileaf.fitness.common.heart.BodySensorLocationCallback;
import com.android.chileaf.fitness.common.heart.HeartRateMeasurementCallback;
import java.util.List;

public interface WearManagerCallbacks extends FitnessManagerCallbacks, BodySensorLocationCallback, HeartRateMeasurementCallback, BodySportCallback, BluetoothStatusCallback {
  default void onBodySensorLocationReceived(@NonNull BluetoothDevice device, int sensorLocation) {}
  
  default void onHeartRateMeasurementReceived(@NonNull BluetoothDevice device, int heartRate, @Nullable Boolean contactDetected, @Nullable Integer energyExpanded, @Nullable List<Integer> rrIntervals) {}
  
  default void onSportReceived(@NonNull BluetoothDevice device, int step, int distance, int calorie) {}
  
  default void onBluetoothStatusReceived(@NonNull BluetoothDevice device, boolean enabled) {}
}


/* Location:              C:\Users\Admin\Downloads\jd-gui-windows-1.6.6\classes.jar!\com\android\chileaf\fitness\callback\WearManagerCallbacks.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */