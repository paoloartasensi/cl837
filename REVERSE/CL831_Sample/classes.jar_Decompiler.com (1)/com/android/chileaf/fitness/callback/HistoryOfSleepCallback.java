package com.android.chileaf.fitness.callback;

import android.bluetooth.BluetoothDevice;
import androidx.annotation.NonNull;
import com.android.chileaf.model.HistorySleep;
import java.util.List;

public interface HistoryOfSleepCallback {
   void onHistoryOfSleepReceived(@NonNull final BluetoothDevice device, List<HistorySleep> sleeps);
}
