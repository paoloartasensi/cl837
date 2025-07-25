package com.android.chileaf.bluetooth.connect;

import android.bluetooth.BluetoothDevice;

/* compiled from: lambda */
public final /* synthetic */ class g implements Runnable {
    private final /* synthetic */ j0 e;

    /* renamed from: f  reason: collision with root package name */
    private final /* synthetic */ o0 f1045f;

    /* renamed from: g  reason: collision with root package name */
    private final /* synthetic */ BluetoothDevice f1046g;

    public /* synthetic */ g(j0 j0Var, o0 o0Var, BluetoothDevice bluetoothDevice) {
        this.e = j0Var;
        this.f1045f = o0Var;
        this.f1046g = bluetoothDevice;
    }

    public final void run() {
        this.e.a(this.f1045f, this.f1046g);
    }
}
