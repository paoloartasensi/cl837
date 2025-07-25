package com.android.chileaf.fitness.callback;

import android.bluetooth.BluetoothDevice;
import androidx.annotation.NonNull;
import com.android.chileaf.model.HistoryOfRecord;
import java.util.List;

public interface SingleTapRecordCallback {
   void onSingleTapRecordReceived(@NonNull final BluetoothDevice device, List<HistoryOfRecord> records);
}
