package com.android.chileaf.bluetooth.connect;

import android.bluetooth.BluetoothDevice;
import com.android.chileaf.bluetooth.connect.Request;

/* compiled from: ConnectRequest */
public class n0 extends a1 {
    private BluetoothDevice r;
    private int s;
    private int t = 0;
    private int u = 0;
    private int v = 0;
    private boolean w = false;

    n0(Request.Type type, BluetoothDevice bluetoothDevice) {
        super(type);
        this.r = bluetoothDevice;
        this.s = 1;
    }

    /* access modifiers changed from: package-private */
    public boolean i() {
        int i2 = this.u;
        if (i2 <= 0) {
            return false;
        }
        this.u = i2 - 1;
        return true;
    }

    public BluetoothDevice j() {
        return this.r;
    }

    /* access modifiers changed from: package-private */
    public int k() {
        return this.s;
    }

    /* access modifiers changed from: package-private */
    public int l() {
        return this.v;
    }

    /* access modifiers changed from: package-private */
    public boolean m() {
        int i2 = this.t;
        this.t = i2 + 1;
        return i2 == 0;
    }

    /* access modifiers changed from: package-private */
    public boolean n() {
        return this.w;
    }

    /* access modifiers changed from: package-private */
    public n0 a(u0 u0Var) {
        super.a(u0Var);
        return this;
    }

    public n0 a(boolean z) {
        this.w = z;
        return this;
    }
}
