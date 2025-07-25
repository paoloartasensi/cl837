package com.android.chileaf.fitness.common.csc;

import android.bluetooth.BluetoothDevice;
import com.android.chileaf.bluetooth.connect.callback.profile.ProfileReadResponse;
import com.android.chileaf.bluetooth.connect.data.Data;

public abstract class CyclingSpeedAndCadenceMeasurementDataCallback extends ProfileReadResponse implements b {

    /* renamed from: h  reason: collision with root package name */
    private long f1138h = -1;

    /* renamed from: i  reason: collision with root package name */
    private long f1139i = -1;

    /* renamed from: j  reason: collision with root package name */
    private int f1140j = -1;
    private int k = -1;
    private int l = -1;
    private float m = -1.0f;

    public /* synthetic */ float a() {
        return a.a(this);
    }

    public void a(BluetoothDevice bluetoothDevice, Data data) {
        super.a(bluetoothDevice, data);
        int i2 = 1;
        if (data.b() < 1) {
            b(bluetoothDevice, data);
            return;
        }
        int i3 = 0;
        byte byteValue = data.a(0).byteValue();
        boolean z = (byteValue & 1) != 0;
        boolean z2 = (byteValue & 2) != 0;
        int b = data.b();
        int i4 = (z ? 6 : 0) + 1;
        if (z2) {
            i3 = 4;
        }
        if (b < i4 + i3) {
            b(bluetoothDevice, data);
            return;
        }
        if (z) {
            long intValue = ((long) data.a(20, 1).intValue()) & 4294967295L;
            int intValue2 = data.a(18, 5).intValue();
            if (this.f1138h < 0) {
                this.f1138h = intValue;
            }
            a(bluetoothDevice, intValue, intValue2);
            i2 = 7;
        }
        if (z2) {
            a(bluetoothDevice, data.a(18, i2).intValue(), data.a(18, i2 + 2).intValue());
        }
    }

    public void a(BluetoothDevice bluetoothDevice, long j2, int i2) {
        if (this.f1140j != i2) {
            if (this.f1139i >= 0) {
                float a = a();
                int i3 = this.f1140j;
                float f2 = (i2 < i3 ? (float) ((65535 + i2) - i3) : (float) (i2 - i3)) / 1024.0f;
                long j3 = this.f1139i;
                this.m = (((float) (j2 - j3)) * 60.0f) / f2;
                a(bluetoothDevice, (((float) j2) * a) / 1000.0f, (((float) (j2 - this.f1138h)) * a) / 1000.0f, ((((float) (j2 - j3)) * a) / 1000.0f) / f2);
            }
            this.f1139i = j2;
            this.f1140j = i2;
        }
    }

    public void a(BluetoothDevice bluetoothDevice, int i2, int i3) {
        int i4 = this.l;
        if (i4 != i3) {
            if (this.k >= 0) {
                float f2 = (((float) (i2 - this.k)) * 60.0f) / ((i3 < i4 ? (float) ((65535 + i3) - i4) : (float) (i3 - i4)) / 1024.0f);
                float f3 = 0.0f;
                if (f2 > 0.0f) {
                    float f4 = this.m;
                    if (f4 >= 0.0f) {
                        f3 = f4 / f2;
                    }
                    a(bluetoothDevice, f2, f3);
                }
            }
            this.k = i2;
            this.l = i3;
        }
    }
}
