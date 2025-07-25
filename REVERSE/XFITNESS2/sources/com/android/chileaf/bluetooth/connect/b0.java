package com.android.chileaf.bluetooth.connect;

import android.bluetooth.BluetoothDevice;

/* compiled from: lambda */
public final /* synthetic */ class b0 implements Runnable {
    private final /* synthetic */ d1 e;

    /* renamed from: f  reason: collision with root package name */
    private final /* synthetic */ BluetoothDevice f1028f;

    /* renamed from: g  reason: collision with root package name */
    private final /* synthetic */ byte[] f1029g;

    /* renamed from: h  reason: collision with root package name */
    private final /* synthetic */ int f1030h;

    public /* synthetic */ b0(d1 d1Var, BluetoothDevice bluetoothDevice, byte[] bArr, int i2) {
        this.e = d1Var;
        this.f1028f = bluetoothDevice;
        this.f1029g = bArr;
        this.f1030h = i2;
    }

    public final void run() {
        this.e.a(this.f1028f, this.f1029g, this.f1030h);
    }
}
