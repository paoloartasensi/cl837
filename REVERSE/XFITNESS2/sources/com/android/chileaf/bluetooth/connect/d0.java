package com.android.chileaf.bluetooth.connect;

import android.bluetooth.BluetoothDevice;

/* compiled from: lambda */
public final /* synthetic */ class d0 implements Runnable {
    private final /* synthetic */ e1 e;

    /* renamed from: f  reason: collision with root package name */
    private final /* synthetic */ BluetoothDevice f1038f;

    /* renamed from: g  reason: collision with root package name */
    private final /* synthetic */ byte[] f1039g;

    public /* synthetic */ d0(e1 e1Var, BluetoothDevice bluetoothDevice, byte[] bArr) {
        this.e = e1Var;
        this.f1038f = bluetoothDevice;
        this.f1039g = bArr;
    }

    public final void run() {
        this.e.a(this.f1038f, this.f1039g);
    }
}
