package com.chileaf.fitness.device.wear.cl830.heart;

import android.bluetooth.BluetoothDevice;
import com.android.chileaf.bluetooth.connect.callback.profile.ProfileReadResponse;
import com.android.chileaf.bluetooth.connect.data.Data;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class HeartRateBeatMeasurementDataCallback extends ProfileReadResponse implements a {

    /* renamed from: h  reason: collision with root package name */
    private float f1175h;

    /* renamed from: i  reason: collision with root package name */
    private boolean f1176i;

    /* access modifiers changed from: protected */
    public abstract float a(int i2);

    public void a(BluetoothDevice bluetoothDevice, Data data) {
        Integer num;
        int i2;
        List list;
        Data data2 = data;
        super.a(bluetoothDevice, data);
        if (data.b() < 2) {
            b(bluetoothDevice, data);
            return;
        }
        int i3 = 17;
        int intValue = data2.a(17, 0).intValue();
        if ((intValue & 1) != 0) {
            i3 = 18;
        }
        int i4 = (intValue & 6) >> 1;
        boolean z = i4 == 2 || i4 == 3;
        boolean z2 = i4 == 3;
        boolean z3 = (intValue & 8) != 0;
        boolean z4 = (intValue & 16) != 0;
        int i5 = i3 & 15;
        if (data.b() < i5 + 1 + (z3 ? 2 : 0) + (z4 ? 2 : 0)) {
            b(bluetoothDevice, data);
            return;
        }
        Boolean valueOf = z ? Boolean.valueOf(z2) : null;
        int intValue2 = data2.a(i3, 1).intValue();
        int i6 = 1 + i5;
        if (this.f1176i) {
            this.f1175h += a(intValue2);
        }
        if (z3) {
            int i7 = i6 + 2;
            num = data2.a(18, i6);
            i2 = i7;
        } else {
            i2 = i6;
            num = null;
        }
        if (z4) {
            int b = (data.b() - i2) / 2;
            ArrayList arrayList = new ArrayList(b);
            for (int i8 = 0; i8 < b; i8++) {
                arrayList.add(data2.a(18, i2));
                i2 += 2;
            }
            list = Collections.unmodifiableList(arrayList);
        } else {
            list = null;
        }
        a(bluetoothDevice, intValue2, Math.round(this.f1175h), valueOf, num, list);
    }
}
