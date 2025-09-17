package com.android.chileaf.fitness.callback;

import android.bluetooth.BluetoothDevice;
import androidx.annotation.NonNull;
import com.android.chileaf.model.IntervalStep;
import java.util.List;

public interface IntervalStepCallback {
  void onIntervalStepReceived(@NonNull BluetoothDevice paramBluetoothDevice, List<IntervalStep> paramList);
}


/* Location:              C:\Users\Admin\Downloads\jd-gui-windows-1.6.6\classes.jar!\com\android\chileaf\fitness\callback\IntervalStepCallback.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */