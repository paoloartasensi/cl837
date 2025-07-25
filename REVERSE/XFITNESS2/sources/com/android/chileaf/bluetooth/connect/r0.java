package com.android.chileaf.bluetooth.connect;

import android.bluetooth.BluetoothDevice;
import com.android.chileaf.bluetooth.connect.f1.g;

/* compiled from: PhyRequest */
public final class r0 extends y0<g> {
    private final int q;
    private final int r;
    private final int s;

    /* access modifiers changed from: package-private */
    public void b(BluetoothDevice bluetoothDevice, int i2, int i3) {
        this.b.post(new n(this, bluetoothDevice, i2, i3));
    }

    public /* synthetic */ void f(BluetoothDevice bluetoothDevice) {
        T t = this.p;
        if (t != null) {
            ((g) t).a(bluetoothDevice, 1, 1);
        }
    }

    /* access modifiers changed from: package-private */
    public void g(BluetoothDevice bluetoothDevice) {
        this.b.post(new m(this, bluetoothDevice));
    }

    /* access modifiers changed from: package-private */
    public int i() {
        return this.s;
    }

    /* access modifiers changed from: package-private */
    public int j() {
        return this.r;
    }

    /* access modifiers changed from: package-private */
    public int k() {
        return this.q;
    }

    /* access modifiers changed from: package-private */
    public r0 a(u0 u0Var) {
        super.a(u0Var);
        return this;
    }

    public /* synthetic */ void a(BluetoothDevice bluetoothDevice, int i2, int i3) {
        T t = this.p;
        if (t != null) {
            ((g) t).a(bluetoothDevice, i2, i3);
        }
    }
}
