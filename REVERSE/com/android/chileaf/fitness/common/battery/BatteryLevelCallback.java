package com.android.chileaf.fitness.common.battery;

import android.bluetooth.BluetoothDevice;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;

public interface BatteryLevelCallback {
  void onBatteryLevelChanged(@NonNull BluetoothDevice paramBluetoothDevice, @IntRange(from = 0L, to = 100L) int paramInt);
}


/* Location:              C:\Users\Admin\Downloads\jd-gui-windows-1.6.6\classes.jar!\com\android\chileaf\fitness\common\battery\BatteryLevelCallback.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */