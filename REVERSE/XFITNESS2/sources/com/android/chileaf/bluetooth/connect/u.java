package com.android.chileaf.bluetooth.connect;

import android.bluetooth.BluetoothDevice;

/* compiled from: lambda */
public final /* synthetic */ class u implements Runnable {
    private final /* synthetic */ Request e;

    /* renamed from: f  reason: collision with root package name */
    private final /* synthetic */ BluetoothDevice f1082f;

    /* renamed from: g  reason: collision with root package name */
    private final /* synthetic */ int f1083g;

    public /* synthetic */ u(Request request, BluetoothDevice bluetoothDevice, int i2) {
        this.e = request;
        this.f1082f = bluetoothDevice;
        this.f1083g = i2;
    }

    public final void run() {
        this.e.a(this.f1082f, this.f1083g);
    }
}
