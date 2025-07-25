package com.chileaf.fitness.device.wear.cl831;

import android.bluetooth.BluetoothDevice;
import com.android.chileaf.fitness.common.heart.a;
import com.android.chileaf.fitness.common.heart.b;
import com.android.chileaf.fitness.w;
import java.util.List;

/* compiled from: CL831ManagerCallbacks */
public interface e extends w, a, b, com.android.chileaf.fitness.common.sport.a, com.chileaf.fitness.device.wear.cl831.callback.a {
    void a(BluetoothDevice bluetoothDevice, int i2);

    void a(BluetoothDevice bluetoothDevice, int i2, int i3, int i4);

    void a(BluetoothDevice bluetoothDevice, int i2, Boolean bool, Integer num, List<Integer> list);

    void a(BluetoothDevice bluetoothDevice, boolean z);
}
