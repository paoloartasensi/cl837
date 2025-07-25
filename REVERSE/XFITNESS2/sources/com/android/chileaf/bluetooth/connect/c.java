package com.android.chileaf.bluetooth.connect;

import android.bluetooth.BluetoothDevice;

/* compiled from: lambda */
public final /* synthetic */ class c implements Runnable {
    private final /* synthetic */ j0 e;

    /* renamed from: f  reason: collision with root package name */
    private final /* synthetic */ Request f1031f;

    /* renamed from: g  reason: collision with root package name */
    private final /* synthetic */ BluetoothDevice f1032g;

    public /* synthetic */ c(j0 j0Var, Request request, BluetoothDevice bluetoothDevice) {
        this.e = j0Var;
        this.f1031f = request;
        this.f1032g = bluetoothDevice;
    }

    public final void run() {
        this.e.a(this.f1031f, this.f1032g);
    }
}
