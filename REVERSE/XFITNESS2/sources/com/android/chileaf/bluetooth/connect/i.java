package com.android.chileaf.bluetooth.connect;

import android.bluetooth.BluetoothDevice;

/* compiled from: lambda */
public final /* synthetic */ class i implements Runnable {
    private final /* synthetic */ j0 e;

    /* renamed from: f  reason: collision with root package name */
    private final /* synthetic */ z0 f1051f;

    /* renamed from: g  reason: collision with root package name */
    private final /* synthetic */ BluetoothDevice f1052g;

    public /* synthetic */ i(j0 j0Var, z0 z0Var, BluetoothDevice bluetoothDevice) {
        this.e = j0Var;
        this.f1051f = z0Var;
        this.f1052g = bluetoothDevice;
    }

    public final void run() {
        this.e.a(this.f1051f, this.f1052g);
    }
}
