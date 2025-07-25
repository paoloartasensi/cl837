package com.android.chileaf.bluetooth.connect;

import android.bluetooth.BluetoothDevice;
import com.android.chileaf.bluetooth.connect.Request;
import com.android.chileaf.bluetooth.connect.f1.i;

/* compiled from: ReadRssiRequest */
public final class t0 extends y0<i> {
    t0(Request.Type type) {
        super(type);
    }

    public /* synthetic */ void c(BluetoothDevice bluetoothDevice, int i2) {
        T t = this.p;
        if (t != null) {
            ((i) t).c(bluetoothDevice, i2);
        }
    }

    /* access modifiers changed from: package-private */
    public void d(BluetoothDevice bluetoothDevice, int i2) {
        this.b.post(new r(this, bluetoothDevice, i2));
    }

    /* access modifiers changed from: package-private */
    public t0 a(u0 u0Var) {
        super.a(u0Var);
        return this;
    }

    public t0 a(i iVar) {
        super.a(iVar);
        return this;
    }
}
