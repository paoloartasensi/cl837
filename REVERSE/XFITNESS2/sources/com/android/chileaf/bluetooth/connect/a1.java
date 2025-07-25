package com.android.chileaf.bluetooth.connect;

import android.bluetooth.BluetoothDevice;
import com.android.chileaf.bluetooth.connect.Request;

/* compiled from: TimeoutableRequest */
public abstract class a1 extends Request {
    private Runnable p;
    protected long q;

    a1(Request.Type type) {
        super(type);
    }

    /* access modifiers changed from: package-private */
    public void b(BluetoothDevice bluetoothDevice, int i2) {
        if (!this.o) {
            this.b.removeCallbacks(this.p);
            this.p = null;
        }
        super.b(bluetoothDevice, i2);
    }

    /* access modifiers changed from: package-private */
    public void c(BluetoothDevice bluetoothDevice) {
        if (this.q > 0) {
            w wVar = new w(this, bluetoothDevice);
            this.p = wVar;
            this.b.postDelayed(wVar, this.q);
        }
        super.c(bluetoothDevice);
    }

    /* access modifiers changed from: package-private */
    public void d(BluetoothDevice bluetoothDevice) {
        if (!this.o) {
            this.b.removeCallbacks(this.p);
            this.p = null;
        }
        super.d(bluetoothDevice);
    }

    public /* synthetic */ void f(BluetoothDevice bluetoothDevice) {
        this.p = null;
        if (!this.o) {
            b(bluetoothDevice, -5);
            this.a.a(this);
        }
    }

    /* access modifiers changed from: package-private */
    public a1 a(u0 u0Var) {
        super.a(u0Var);
        return this;
    }

    public final void a() {
        super.a();
    }

    /* access modifiers changed from: package-private */
    public void c() {
        if (!this.o) {
            this.b.removeCallbacks(this.p);
            this.p = null;
        }
        super.c();
    }
}
