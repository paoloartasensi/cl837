package com.chileaf.fitness.device.wear.cl820.heart;

import android.bluetooth.BluetoothDevice;
import com.android.chileaf.bluetooth.connect.callback.profile.ProfileReadResponse;
import com.android.chileaf.bluetooth.connect.data.Data;
import com.android.chileaf.fitness.common.heart.b;
import com.android.chileaf.fitness.common.heart.c;
import j.a.a;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class HeartRateVariabilityMeasurementDataCallback extends ProfileReadResponse implements b, c {

    /* renamed from: h  reason: collision with root package name */
    private int f1169h = 0;

    /* renamed from: i  reason: collision with root package name */
    private boolean f1170i = false;

    /* renamed from: j  reason: collision with root package name */
    private List<Integer> f1171j = new ArrayList();
    private List<Integer> k = new ArrayList();

    public void a(BluetoothDevice bluetoothDevice, Data data) {
        Integer num;
        int size;
        HeartRateVariabilityMeasurementDataCallback heartRateVariabilityMeasurementDataCallback;
        HeartRateVariabilityMeasurementDataCallback heartRateVariabilityMeasurementDataCallback2 = this;
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
        int i5 = i4 + 1;
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
                if (!heartRateVariabilityMeasurementDataCallback2.f1170i) {
                    heartRateVariabilityMeasurementDataCallback2.f1171j.add(data2.a(18, i5));
                }
                heartRateVariabilityMeasurementDataCallback2.k.add(data2.a(18, i5));
                arrayList.add(data2.a(18, i5));
                i5 += 2;
            }
            List unmodifiableList = Collections.unmodifiableList(arrayList);
            heartRateVariabilityMeasurementDataCallback2.f1169h++;
            list = unmodifiableList;
        }
        a(bluetoothDevice, intValue2, valueOf, num, list);
        if (heartRateVariabilityMeasurementDataCallback2.f1171j.size() > 10 && !heartRateVariabilityMeasurementDataCallback2.f1171j.isEmpty()) {
            int i7 = 0;
            for (Integer intValue3 : heartRateVariabilityMeasurementDataCallback2.f1171j) {
                double intValue4 = (double) intValue3.intValue();
                double d = (double) i7;
                Double.isNaN(d);
                Double.isNaN(intValue4);
                i7 = (int) (d + intValue4);
            }
            int size2 = heartRateVariabilityMeasurementDataCallback2.f1171j.size();
            float f2 = (float) (i7 / size2);
            for (int i8 = 0; i8 < size2; i8++) {
                double intValue5 = (double) (((float) heartRateVariabilityMeasurementDataCallback2.k.get(i8).intValue()) - f2);
                double abs = Math.abs(intValue5);
                double d2 = (double) f2;
                Double.isNaN(d2);
                if (abs <= d2 * 0.3d) {
                    Math.pow(intValue5, 2.0d);
                }
            }
        }
        if (heartRateVariabilityMeasurementDataCallback2.f1169h < 10 || heartRateVariabilityMeasurementDataCallback2.k.isEmpty()) {
            HeartRateVariabilityMeasurementDataCallback heartRateVariabilityMeasurementDataCallback3 = heartRateVariabilityMeasurementDataCallback2;
            return;
        }
        int i9 = 0;
        for (Integer intValue6 : heartRateVariabilityMeasurementDataCallback2.k) {
            double intValue7 = (double) intValue6.intValue();
            double d3 = (double) i9;
            Double.isNaN(d3);
            Double.isNaN(intValue7);
            i9 = (int) (d3 + intValue7);
        }
        double d4 = 0.0d;
        int size3 = heartRateVariabilityMeasurementDataCallback2.k.size();
        float f3 = (float) (i9 / size3);
        int i10 = 0;
        while (i10 < size3) {
            double intValue8 = (double) (((float) heartRateVariabilityMeasurementDataCallback2.k.get(i10).intValue()) - f3);
            double abs2 = Math.abs(intValue8);
            double d5 = d4;
            double d6 = (double) f3;
            Double.isNaN(d6);
            if (abs2 <= d6 * 0.3d) {
                d4 = d5 + Math.pow(intValue8, 2.0d);
                heartRateVariabilityMeasurementDataCallback = this;
            } else {
                heartRateVariabilityMeasurementDataCallback = this;
                heartRateVariabilityMeasurementDataCallback.k.remove(i10);
                size3--;
                d4 = d5;
            }
            i10++;
            heartRateVariabilityMeasurementDataCallback2 = heartRateVariabilityMeasurementDataCallback;
        }
        HeartRateVariabilityMeasurementDataCallback heartRateVariabilityMeasurementDataCallback4 = heartRateVariabilityMeasurementDataCallback2;
        double d7 = d4;
        double d8 = (double) size3;
        Double.isNaN(d8);
        double d9 = d7 / d8;
        heartRateVariabilityMeasurementDataCallback4.a(bluetoothDevice, intValue2, (float) Math.sqrt(d9));
        a.b("mHeartRates:%s number:%s sum:%s average:%s total:%s sqrt:%s", heartRateVariabilityMeasurementDataCallback4.k.toString(), Integer.valueOf(size3), Integer.valueOf(i9), Float.valueOf(f3), Double.valueOf(d7), Double.valueOf(d9));
        if (!heartRateVariabilityMeasurementDataCallback4.k.isEmpty() && (size = heartRateVariabilityMeasurementDataCallback4.k.size()) > 10) {
            heartRateVariabilityMeasurementDataCallback4.k = heartRateVariabilityMeasurementDataCallback4.k.subList(size - 10, size);
        }
        heartRateVariabilityMeasurementDataCallback4.f1170i = false;
        heartRateVariabilityMeasurementDataCallback4.f1169h--;
    }
}
