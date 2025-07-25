package com.android.chileaf.fitness.callback;

import android.bluetooth.BluetoothDevice;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;

public interface HistoryOfSingleRecordCallback {
   void onHistorySingleRecordReceived(@NonNull final BluetoothDevice device, final long stamp, @IntRange(from = 0L) final long step, @IntRange(from = 0L) final long distance, @IntRange(from = 0L) final long calorie);
}
