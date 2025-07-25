package com.android.chileaf.fitness.callback;

import android.bluetooth.BluetoothDevice;
import androidx.annotation.NonNull;
import com.android.chileaf.model.HistoryOfSport;
import java.util.List;

public interface HistoryOfSportCallback {
   void onHistoryOfSportReceived(@NonNull final BluetoothDevice device, List<HistoryOfSport> sports);
}
