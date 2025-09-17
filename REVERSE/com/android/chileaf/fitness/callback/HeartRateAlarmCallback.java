package com.android.chileaf.fitness.callback;

import android.bluetooth.BluetoothDevice;
import androidx.annotation.NonNull;

public interface HeartRateAlarmCallback {
  void onHeartRateAlarmReceived(@NonNull BluetoothDevice paramBluetoothDevice, long paramLong, boolean paramBoolean);
}


/* Location:              C:\Users\Admin\Downloads\jd-gui-windows-1.6.6\classes.jar!\com\android\chileaf\fitness\callback\HeartRateAlarmCallback.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */