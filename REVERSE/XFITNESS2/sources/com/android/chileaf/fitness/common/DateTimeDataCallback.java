package com.android.chileaf.fitness.common;

import android.bluetooth.BluetoothDevice;
import com.android.chileaf.bluetooth.connect.callback.profile.ProfileReadResponse;
import com.android.chileaf.bluetooth.connect.data.Data;
import java.util.Calendar;

public abstract class DateTimeDataCallback extends ProfileReadResponse implements a {
    public void a(BluetoothDevice bluetoothDevice, Data data) {
        super.a(bluetoothDevice, data);
        Calendar a = a(data, 0);
        if (a == null) {
            b(bluetoothDevice, data);
        } else {
            a(bluetoothDevice, a);
        }
    }

    public static Calendar a(Data data, int i2) {
        if (data.b() < i2 + 7) {
            return null;
        }
        Calendar instance = Calendar.getInstance();
        int intValue = data.a(18, i2).intValue();
        int intValue2 = data.a(17, i2 + 2).intValue();
        int intValue3 = data.a(17, i2 + 3).intValue();
        if (intValue > 0) {
            instance.set(1, intValue);
        } else {
            instance.clear(1);
        }
        if (intValue2 > 0) {
            instance.set(2, intValue2 - 1);
        } else {
            instance.clear(2);
        }
        if (intValue3 > 0) {
            instance.set(5, intValue3);
        } else {
            instance.clear(5);
        }
        instance.set(11, data.a(17, i2 + 4).intValue());
        instance.set(12, data.a(17, i2 + 5).intValue());
        instance.set(13, data.a(17, i2 + 6).intValue());
        instance.set(14, 0);
        return instance;
    }
}
