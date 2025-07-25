package com.android.chileaf.bluetooth.connect;

import android.bluetooth.BluetoothDevice;

/* compiled from: lambda */
public final /* synthetic */ class r implements Runnable {
    private final /* synthetic */ t0 e;

    /* renamed from: f  reason: collision with root package name */
    private final /* synthetic */ BluetoothDevice f1070f;

    /* renamed from: g  reason: collision with root package name */
    private final /* synthetic */ int f1071g;

    public /* synthetic */ r(t0 t0Var, BluetoothDevice bluetoothDevice, int i2) {
        this.e = t0Var;
        this.f1070f = bluetoothDevice;
        this.f1071g = i2;
    }

    public final void run() {
        this.e.c(this.f1070f, this.f1071g);
    }
}
