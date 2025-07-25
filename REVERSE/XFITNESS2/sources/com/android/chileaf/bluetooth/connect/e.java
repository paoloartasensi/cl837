package com.android.chileaf.bluetooth.connect;

import android.bluetooth.BluetoothGatt;
import com.android.chileaf.bluetooth.connect.j0;

/* compiled from: lambda */
public final /* synthetic */ class e implements Runnable {
    private final /* synthetic */ j0.c e;

    /* renamed from: f  reason: collision with root package name */
    private final /* synthetic */ BluetoothGatt f1041f;

    public /* synthetic */ e(j0.c cVar, BluetoothGatt bluetoothGatt) {
        this.e = cVar;
        this.f1041f = bluetoothGatt;
    }

    public final void run() {
        this.e.a(this.f1041f);
    }
}
