package com.android.chileaf.fitness.common.sport;

import android.bluetooth.BluetoothDevice;
import com.android.chileaf.bluetooth.connect.callback.profile.ProfileReadResponse;
import com.android.chileaf.bluetooth.connect.data.Data;

public abstract class BodySportDataCallback extends ProfileReadResponse implements a {
    private int a(byte b) {
        return b & 255;
    }

    public void a(BluetoothDevice bluetoothDevice, Data data) {
        super.a(bluetoothDevice, data);
        if (data.b() < 2) {
            b(bluetoothDevice, data);
            return;
        }
        byte[] a = data.a();
        try {
            if (a.length == 20 && a(a[5]) == 1 && a(a[7]) == 1) {
                a(bluetoothDevice, a(a[10], a[11], a[12]), a(a[13], a[14], a[15]), a(a[16], a[17], a[18]));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private int a(byte b, byte b2, byte b3) {
        return (a(b) << 16) + (a(b2) << 8) + a(b3);
    }
}
