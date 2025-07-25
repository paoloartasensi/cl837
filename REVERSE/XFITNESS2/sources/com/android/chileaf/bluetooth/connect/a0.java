package com.android.chileaf.bluetooth.connect;

import android.bluetooth.BluetoothDevice;
import com.android.chileaf.bluetooth.connect.data.Data;
import com.android.chileaf.bluetooth.connect.f1.b;

/* compiled from: lambda */
public final /* synthetic */ class a0 implements Runnable {
    private final /* synthetic */ b e;

    /* renamed from: f  reason: collision with root package name */
    private final /* synthetic */ BluetoothDevice f1026f;

    /* renamed from: g  reason: collision with root package name */
    private final /* synthetic */ Data f1027g;

    public /* synthetic */ a0(b bVar, BluetoothDevice bluetoothDevice, Data data) {
        this.e = bVar;
        this.f1026f = bluetoothDevice;
        this.f1027g = data;
    }

    public final void run() {
        this.e.a(this.f1026f, this.f1027g);
    }
}
