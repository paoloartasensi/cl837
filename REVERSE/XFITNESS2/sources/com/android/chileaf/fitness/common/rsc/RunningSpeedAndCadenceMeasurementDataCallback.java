package com.android.chileaf.fitness.common.rsc;

import android.bluetooth.BluetoothDevice;
import com.android.chileaf.bluetooth.connect.callback.profile.ProfileReadResponse;
import com.android.chileaf.bluetooth.connect.data.Data;

public abstract class RunningSpeedAndCadenceMeasurementDataCallback extends ProfileReadResponse implements a {
    public void a(BluetoothDevice bluetoothDevice, Data data) {
        Integer num;
        Data data2 = data;
        super.a(bluetoothDevice, data);
        int i2 = 4;
        if (data.b() < 4) {
            b(bluetoothDevice, data);
            return;
        }
        int i3 = 0;
        int intValue = data2.a(17, 0).intValue();
        boolean z = (intValue & 1) != 0;
        boolean z2 = (intValue & 2) != 0;
        boolean z3 = (intValue & 4) != 0;
        float intValue2 = ((float) data2.a(18, 1).intValue()) / 256.0f;
        int intValue3 = data2.a(17, 3).intValue();
        int b = data.b();
        int i4 = (z ? 2 : 0) + 4;
        if (z2) {
            i3 = 4;
        }
        if (b < i4 + i3) {
            b(bluetoothDevice, data);
            return;
        }
        if (z) {
            num = data2.a(18, 4);
            i2 = 6;
        } else {
            num = null;
        }
        a(bluetoothDevice, z3, intValue2, intValue3, num, z2 ? data2.b(20, i2) : null);
    }
}
