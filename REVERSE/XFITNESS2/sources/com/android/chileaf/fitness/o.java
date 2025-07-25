package com.android.chileaf.fitness;

import android.bluetooth.BluetoothDevice;
import com.android.chileaf.bluetooth.connect.data.Data;
import com.android.chileaf.bluetooth.connect.f1.b;

/* compiled from: lambda */
public final /* synthetic */ class o implements b {
    private final /* synthetic */ FitnessManager e;

    public /* synthetic */ o(FitnessManager fitnessManager) {
        this.e = fitnessManager;
    }

    public final void a(BluetoothDevice bluetoothDevice, Data data) {
        this.e.d(bluetoothDevice, data);
    }
}
