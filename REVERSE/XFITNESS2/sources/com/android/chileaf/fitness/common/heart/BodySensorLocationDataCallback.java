package com.android.chileaf.fitness.common.heart;

import android.bluetooth.BluetoothDevice;
import com.android.chileaf.bluetooth.connect.callback.profile.ProfileReadResponse;
import com.android.chileaf.bluetooth.connect.data.Data;

public abstract class BodySensorLocationDataCallback extends ProfileReadResponse implements a {
    public void a(BluetoothDevice bluetoothDevice, Data data) {
        super.a(bluetoothDevice, data);
        if (data.b() < 1) {
            b(bluetoothDevice, data);
        } else {
            a(bluetoothDevice, data.a(17, 0).intValue());
        }
    }
}
