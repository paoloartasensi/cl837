package com.android.chileaf.bluetooth.connect;

import android.bluetooth.BluetoothDevice;

/* compiled from: lambda */
public final /* synthetic */ class q implements Runnable {
    private final /* synthetic */ s0 e;

    /* renamed from: f  reason: collision with root package name */
    private final /* synthetic */ BluetoothDevice f1068f;

    /* renamed from: g  reason: collision with root package name */
    private final /* synthetic */ byte[] f1069g;

    public /* synthetic */ q(s0 s0Var, BluetoothDevice bluetoothDevice, byte[] bArr) {
        this.e = s0Var;
        this.f1068f = bluetoothDevice;
        this.f1069g = bArr;
    }

    public final void run() {
        this.e.a(this.f1068f, this.f1069g);
    }
}
