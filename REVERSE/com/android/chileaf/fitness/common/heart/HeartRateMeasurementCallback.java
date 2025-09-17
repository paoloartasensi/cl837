package com.android.chileaf.fitness.common.heart;

import android.bluetooth.BluetoothDevice;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.List;

public interface HeartRateMeasurementCallback {
  void onHeartRateMeasurementReceived(@NonNull BluetoothDevice paramBluetoothDevice, @IntRange(from = 0L) int paramInt, @Nullable Boolean paramBoolean, @Nullable @IntRange(from = 0L) Integer paramInteger, @Nullable List<Integer> paramList);
}


/* Location:              C:\Users\Admin\Downloads\jd-gui-windows-1.6.6\classes.jar!\com\android\chileaf\fitness\common\heart\HeartRateMeasurementCallback.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */