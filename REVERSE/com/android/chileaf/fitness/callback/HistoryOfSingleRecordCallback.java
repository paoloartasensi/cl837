package com.android.chileaf.fitness.callback;

import android.bluetooth.BluetoothDevice;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;

public interface HistoryOfSingleRecordCallback {
  void onHistorySingleRecordReceived(@NonNull BluetoothDevice paramBluetoothDevice, long paramLong1, @IntRange(from = 0L) long paramLong2, @IntRange(from = 0L) long paramLong3, @IntRange(from = 0L) long paramLong4);
}


/* Location:              C:\Users\Admin\Downloads\jd-gui-windows-1.6.6\classes.jar!\com\android\chileaf\fitness\callback\HistoryOfSingleRecordCallback.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */