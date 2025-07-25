package com.android.chileaf.fitness.common.heart;

import android.bluetooth.BluetoothDevice;
import com.android.chileaf.bluetooth.connect.callback.profile.ProfileReadResponse;
import com.android.chileaf.bluetooth.connect.data.Data;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class HeartRateMeasurementDataCallback extends ProfileReadResponse implements b {
    public void a(BluetoothDevice bluetoothDevice, Data data) {
        Integer num;
        Data data2 = data;
        super.a(bluetoothDevice, data);
        if (data.b() < 2) {
            b(bluetoothDevice, data);
            return;
        }
        int i2 = 17;
        int intValue = data2.a(17, 0).intValue();
        if ((intValue & 1) != 0) {
            i2 = 18;
        }
        int i3 = (intValue & 6) >> 1;
        boolean z = i3 == 2 || i3 == 3;
        boolean z2 = i3 == 3;
        boolean z3 = (intValue & 8) != 0;
        boolean z4 = (intValue & 16) != 0;
        int i4 = i2 & 15;
        if (data.b() < i4 + 1 + (z3 ? 2 : 0) + (z4 ? 2 : 0)) {
            b(bluetoothDevice, data);
            return;
        }
        List list = null;
        Boolean valueOf = z ? Boolean.valueOf(z2) : null;
        int intValue2 = data2.a(i2, 1).intValue();
        int i5 = 1 + i4;
        if (z3) {
            Integer a = data2.a(18, i5);
            i5 += 2;
            num = a;
        } else {
            num = null;
        }
        if (z4) {
            int b = (data.b() - i5) / 2;
            ArrayList arrayList = new ArrayList(b);
            for (int i6 = 0; i6 < b; i6++) {
                arrayList.add(data2.a(18, i5));
                i5 += 2;
            }
            list = Collections.unmodifiableList(arrayList);
        }
        a(bluetoothDevice, intValue2, valueOf, num, list);
    }
}
