package com.android.chileaf.bluetooth.connect;

import android.bluetooth.BluetoothDevice;

/* compiled from: lambda */
public final /* synthetic */ class n implements Runnable {
    private final /* synthetic */ r0 e;

    /* renamed from: f  reason: collision with root package name */
    private final /* synthetic */ BluetoothDevice f1061f;

    /* renamed from: g  reason: collision with root package name */
    private final /* synthetic */ int f1062g;

    /* renamed from: h  reason: collision with root package name */
    private final /* synthetic */ int f1063h;

    public /* synthetic */ n(r0 r0Var, BluetoothDevice bluetoothDevice, int i2, int i3) {
        this.e = r0Var;
        this.f1061f = bluetoothDevice;
        this.f1062g = i2;
        this.f1063h = i3;
    }

    public final void run() {
        this.e.a(this.f1061f, this.f1062g, this.f1063h);
    }
}
