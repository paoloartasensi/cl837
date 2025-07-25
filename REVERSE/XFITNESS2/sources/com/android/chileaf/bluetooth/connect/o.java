package com.android.chileaf.bluetooth.connect;

import android.bluetooth.BluetoothDevice;
import com.android.chileaf.bluetooth.connect.data.Data;
import com.android.chileaf.bluetooth.connect.f1.b;

/* compiled from: lambda */
public final /* synthetic */ class o implements Runnable {
    private final /* synthetic */ b e;

    /* renamed from: f  reason: collision with root package name */
    private final /* synthetic */ BluetoothDevice f1064f;

    /* renamed from: g  reason: collision with root package name */
    private final /* synthetic */ Data f1065g;

    public /* synthetic */ o(b bVar, BluetoothDevice bluetoothDevice, Data data) {
        this.e = bVar;
        this.f1064f = bluetoothDevice;
        this.f1065g = data;
    }

    public final void run() {
        this.e.a(this.f1064f, this.f1065g);
    }
}
