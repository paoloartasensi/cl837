package com.android.chileaf.bluetooth.connect;

import android.bluetooth.BluetoothDevice;

/* compiled from: lambda */
public final /* synthetic */ class x implements Runnable {
    private final /* synthetic */ c1 e;

    /* renamed from: f  reason: collision with root package name */
    private final /* synthetic */ BluetoothDevice f1086f;

    /* renamed from: g  reason: collision with root package name */
    private final /* synthetic */ byte[] f1087g;

    public /* synthetic */ x(c1 c1Var, BluetoothDevice bluetoothDevice, byte[] bArr) {
        this.e = c1Var;
        this.f1086f = bluetoothDevice;
        this.f1087g = bArr;
    }

    public final void run() {
        this.e.a(this.f1086f, this.f1087g);
    }
}
