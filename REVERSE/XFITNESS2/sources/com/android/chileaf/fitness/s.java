package com.android.chileaf.fitness;

import android.bluetooth.BluetoothDevice;
import com.android.chileaf.bluetooth.connect.data.Data;
import com.android.chileaf.bluetooth.connect.f1.c;

/* compiled from: lambda */
public final /* synthetic */ class s implements c {
    private final /* synthetic */ FitnessManager e;

    public /* synthetic */ s(FitnessManager fitnessManager) {
        this.e = fitnessManager;
    }

    public final void a(BluetoothDevice bluetoothDevice, Data data) {
        this.e.h(bluetoothDevice, data);
    }
}
