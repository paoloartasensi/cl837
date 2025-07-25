package com.android.chileaf.fitness.common.battery;

import android.bluetooth.BluetoothDevice;
import com.android.chileaf.bluetooth.connect.callback.profile.ProfileReadResponse;
import com.android.chileaf.bluetooth.connect.data.Data;

public abstract class BatteryLevelDataCallback extends ProfileReadResponse implements a {
    public void a(BluetoothDevice bluetoothDevice, Data data) {
        int intValue;
        super.a(bluetoothDevice, data);
        if (data.b() != 1 || (intValue = data.a(17, 0).intValue()) < 0 || intValue > 100) {
            b(bluetoothDevice, data);
        } else {
            b(bluetoothDevice, intValue);
        }
    }
}
