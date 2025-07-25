package com.android.chileaf.bluetooth.connect;

import android.bluetooth.BluetoothDevice;

/* compiled from: lambda */
public final /* synthetic */ class m implements Runnable {
    private final /* synthetic */ r0 e;

    /* renamed from: f  reason: collision with root package name */
    private final /* synthetic */ BluetoothDevice f1060f;

    public /* synthetic */ m(r0 r0Var, BluetoothDevice bluetoothDevice) {
        this.e = r0Var;
        this.f1060f = bluetoothDevice;
    }

    public final void run() {
        this.e.f(this.f1060f);
    }
}
