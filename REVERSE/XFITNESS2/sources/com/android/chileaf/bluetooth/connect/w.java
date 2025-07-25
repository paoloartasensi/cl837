package com.android.chileaf.bluetooth.connect;

import android.bluetooth.BluetoothDevice;

/* compiled from: lambda */
public final /* synthetic */ class w implements Runnable {
    private final /* synthetic */ a1 e;

    /* renamed from: f  reason: collision with root package name */
    private final /* synthetic */ BluetoothDevice f1085f;

    public /* synthetic */ w(a1 a1Var, BluetoothDevice bluetoothDevice) {
        this.e = a1Var;
        this.f1085f = bluetoothDevice;
    }

    public final void run() {
        this.e.f(this.f1085f);
    }
}
