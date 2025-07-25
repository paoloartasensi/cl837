package com.android.chileaf.bluetooth.connect;

import android.bluetooth.BluetoothDevice;

/* compiled from: lambda */
public final /* synthetic */ class t implements Runnable {
    private final /* synthetic */ Request e;

    /* renamed from: f  reason: collision with root package name */
    private final /* synthetic */ BluetoothDevice f1081f;

    public /* synthetic */ t(Request request, BluetoothDevice bluetoothDevice) {
        this.e = request;
        this.f1081f = bluetoothDevice;
    }

    public final void run() {
        this.e.a(this.f1081f);
    }
}
