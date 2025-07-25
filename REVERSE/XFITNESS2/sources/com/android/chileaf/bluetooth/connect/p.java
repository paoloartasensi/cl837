package com.android.chileaf.bluetooth.connect;

import android.bluetooth.BluetoothDevice;
import com.android.chileaf.bluetooth.connect.data.Data;
import com.android.chileaf.bluetooth.connect.f1.b;

/* compiled from: lambda */
public final /* synthetic */ class p implements Runnable {
    private final /* synthetic */ b e;

    /* renamed from: f  reason: collision with root package name */
    private final /* synthetic */ BluetoothDevice f1066f;

    /* renamed from: g  reason: collision with root package name */
    private final /* synthetic */ Data f1067g;

    public /* synthetic */ p(b bVar, BluetoothDevice bluetoothDevice, Data data) {
        this.e = bVar;
        this.f1066f = bluetoothDevice;
        this.f1067g = data;
    }

    public final void run() {
        this.e.a(this.f1066f, this.f1067g);
    }
}
