package com.chileaf.fitness.device.wear.cl820;

import android.bluetooth.BluetoothDevice;
import com.android.chileaf.fitness.common.heart.a;
import com.android.chileaf.fitness.common.heart.b;
import com.android.chileaf.fitness.common.heart.c;
import com.android.chileaf.fitness.w;

/* compiled from: CL820ManagerCallbacks */
public interface h extends w, a, b, c, com.android.chileaf.fitness.common.sport.a {
    void a(BluetoothDevice bluetoothDevice, int i2);

    void a(BluetoothDevice bluetoothDevice, int i2, float f2);
}
