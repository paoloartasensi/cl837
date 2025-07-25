package com.android.chileaf.bluetooth.connect;

import android.bluetooth.BluetoothDevice;
import com.android.chileaf.bluetooth.connect.f1.f;

/* compiled from: MtuRequest */
public final class q0 extends y0<f> {
    private final int q;

    public /* synthetic */ void c(BluetoothDevice bluetoothDevice, int i2) {
        T t = this.p;
        if (t != null) {
            ((f) t).a(bluetoothDevice, i2);
        }
    }

    /* access modifiers changed from: package-private */
    public void d(BluetoothDevice bluetoothDevice, int i2) {
        this.b.post(new l(this, bluetoothDevice, i2));
    }

    /* access modifiers changed from: package-private */
    public int i() {
        return this.q;
    }

    /* access modifiers changed from: package-private */
    public q0 a(u0 u0Var) {
        super.a(u0Var);
        return this;
    }
}
