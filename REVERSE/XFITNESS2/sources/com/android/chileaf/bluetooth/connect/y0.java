package com.android.chileaf.bluetooth.connect;

import android.bluetooth.BluetoothGattCharacteristic;
import com.android.chileaf.bluetooth.connect.Request;

/* compiled from: SimpleValueRequest */
public abstract class y0<T> extends x0 {
    T p;

    y0(Request.Type type) {
        super(type);
    }

    public y0<T> a(T t) {
        this.p = t;
        return this;
    }

    y0(Request.Type type, BluetoothGattCharacteristic bluetoothGattCharacteristic) {
        super(type, bluetoothGattCharacteristic);
    }
}
