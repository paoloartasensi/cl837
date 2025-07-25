package com.android.chileaf.bluetooth.connect;

import android.bluetooth.BluetoothDevice;

/* compiled from: lambda */
public final /* synthetic */ class e0 implements Runnable {
    private final /* synthetic */ e1 e;

    /* renamed from: f  reason: collision with root package name */
    private final /* synthetic */ BluetoothDevice f1042f;

    public /* synthetic */ e0(e1 e1Var, BluetoothDevice bluetoothDevice) {
        this.e = e1Var;
        this.f1042f = bluetoothDevice;
    }

    public final void run() {
        this.e.f(this.f1042f);
    }
}
