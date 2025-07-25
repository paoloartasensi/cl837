package com.android.chileaf.fitness;

import android.bluetooth.BluetoothDevice;
import com.android.chileaf.bluetooth.connect.f1.i;
import com.android.chileaf.bluetooth.connect.i0;
import com.android.chileaf.fitness.common.battery.a;

/* compiled from: FitnessManagerCallbacks */
public interface w extends i0, i, a {
    void a(BluetoothDevice bluetoothDevice, String str);

    void b(BluetoothDevice bluetoothDevice, int i2);

    void b(BluetoothDevice bluetoothDevice, String str);

    void c(BluetoothDevice bluetoothDevice, int i2);

    void c(BluetoothDevice bluetoothDevice, String str);

    void d(BluetoothDevice bluetoothDevice, String str);

    void e(BluetoothDevice bluetoothDevice, String str);

    void f(BluetoothDevice bluetoothDevice, String str);

    void g(BluetoothDevice bluetoothDevice, String str);
}
