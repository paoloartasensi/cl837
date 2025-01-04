package com.android.chileaf.fitness.callback;

import android.bluetooth.BluetoothDevice;
import androidx.annotation.NonNull;
import com.android.chileaf.model.HistoryOfRecord;
import java.util.List;

public interface HistoryOfRRRecordCallback {
   void onHistoryOfRRRecordReceived(@NonNull final BluetoothDevice device, List<HistoryOfRecord> records);
}
