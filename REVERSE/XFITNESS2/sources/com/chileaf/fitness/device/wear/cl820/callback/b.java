package com.chileaf.fitness.device.wear.cl820.callback;

import android.bluetooth.BluetoothDevice;
import com.android.chileaf.fitness.model.HistoryOfRecord;
import java.util.List;

/* compiled from: HistoryOfHRRecordCallback */
public interface b {
    void a(BluetoothDevice bluetoothDevice, List<HistoryOfRecord> list);
}
