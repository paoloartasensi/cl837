package com.android.chileaf.bluetooth.connect;

import android.bluetooth.BluetoothDevice;

/* compiled from: lambda */
public final /* synthetic */ class l implements Runnable {
    private final /* synthetic */ q0 e;

    /* renamed from: f  reason: collision with root package name */
    private final /* synthetic */ BluetoothDevice f1058f;

    /* renamed from: g  reason: collision with root package name */
    private final /* synthetic */ int f1059g;

    public /* synthetic */ l(q0 q0Var, BluetoothDevice bluetoothDevice, int i2) {
        this.e = q0Var;
        this.f1058f = bluetoothDevice;
        this.f1059g = i2;
    }

    public final void run() {
        this.e.c(this.f1058f, this.f1059g);
    }
}
