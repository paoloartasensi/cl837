package com.chileaf.fitness.device.wear.cl880;

import android.bluetooth.BluetoothDevice;
import com.android.chileaf.bluetooth.connect.data.Data;
import com.android.chileaf.bluetooth.connect.f1.c;

/* compiled from: lambda */
public final /* synthetic */ class f implements c {
    private final /* synthetic */ CL880Manager e;

    public /* synthetic */ f(CL880Manager cL880Manager) {
        this.e = cL880Manager;
    }

    public final void a(BluetoothDevice bluetoothDevice, Data data) {
        this.e.i(bluetoothDevice, data);
    }
}
