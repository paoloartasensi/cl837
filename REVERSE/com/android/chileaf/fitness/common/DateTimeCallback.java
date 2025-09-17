package com.android.chileaf.fitness.common;

import android.bluetooth.BluetoothDevice;
import androidx.annotation.NonNull;
import java.util.Calendar;

public interface DateTimeCallback {
  void onDateTimeReceived(@NonNull BluetoothDevice paramBluetoothDevice, @NonNull Calendar paramCalendar);
}


/* Location:              C:\Users\Admin\Downloads\jd-gui-windows-1.6.6\classes.jar!\com\android\chileaf\fitness\common\DateTimeCallback.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */