package com.android.chileaf.fitness.common;

import android.bluetooth.BluetoothDevice;
import androidx.annotation.NonNull;
import java.util.Calendar;

public interface DateTimeCallback {
   void onDateTimeReceived(@NonNull final BluetoothDevice device, @NonNull final Calendar calendar);
}
