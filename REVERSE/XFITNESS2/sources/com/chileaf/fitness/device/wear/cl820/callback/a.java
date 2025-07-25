package com.chileaf.fitness.device.wear.cl820.callback;

import android.bluetooth.BluetoothDevice;
import com.android.chileaf.fitness.model.HistoryOfHeartRate;
import java.util.List;

/* compiled from: HistoryOfHRDataCallback */
public interface a {
    void c(BluetoothDevice bluetoothDevice, List<HistoryOfHeartRate> list);
}
