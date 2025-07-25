package com.chileaf.fitness.device.wear.cl820.callback;

import android.bluetooth.BluetoothDevice;
import com.chileaf.fitness.device.wear.cl820.model.HistoryOfRespiratoryRate;
import java.util.List;

/* compiled from: HistoryOfRRDataCallback */
public interface c {
    void g(BluetoothDevice bluetoothDevice, List<HistoryOfRespiratoryRate> list);
}
