package com.android.chileaf.bluetooth.connect;

import android.bluetooth.BluetoothGatt;
import com.android.chileaf.bluetooth.connect.j0;

/* compiled from: lambda */
public final /* synthetic */ class f implements Runnable {
    private final /* synthetic */ j0.c e;

    /* renamed from: f  reason: collision with root package name */
    private final /* synthetic */ int f1043f;

    /* renamed from: g  reason: collision with root package name */
    private final /* synthetic */ BluetoothGatt f1044g;

    public /* synthetic */ f(j0.c cVar, int i2, BluetoothGatt bluetoothGatt) {
        this.e = cVar;
        this.f1043f = i2;
        this.f1044g = bluetoothGatt;
    }

    public final void run() {
        this.e.a(this.f1043f, this.f1044g);
    }
}
