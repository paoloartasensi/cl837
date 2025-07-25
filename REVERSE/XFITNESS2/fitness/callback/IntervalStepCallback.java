package com.android.chileaf.fitness.callback;

import android.bluetooth.BluetoothDevice;
import androidx.annotation.NonNull;
import com.android.chileaf.model.IntervalStep;
import java.util.List;

public interface IntervalStepCallback {
   void onIntervalStepReceived(@NonNull final BluetoothDevice device, List<IntervalStep> steps);
}
