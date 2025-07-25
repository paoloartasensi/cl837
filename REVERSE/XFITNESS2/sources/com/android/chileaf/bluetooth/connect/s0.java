package com.android.chileaf.bluetooth.connect;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGattCharacteristic;
import com.android.chileaf.bluetooth.connect.Request;
import com.android.chileaf.bluetooth.connect.data.Data;
import com.android.chileaf.bluetooth.connect.data.a;
import com.android.chileaf.bluetooth.connect.data.d;
import com.android.chileaf.bluetooth.connect.f1.b;
import com.android.chileaf.bluetooth.connect.f1.h;
import com.android.chileaf.bluetooth.connect.f1.j;

/* compiled from: ReadRequest */
public final class s0 extends y0<b> {
    private h q;
    private com.android.chileaf.bluetooth.connect.data.b r;
    private d s;
    private a t;
    private int u = 0;

    s0(Request.Type type) {
        super(type);
    }

    /* access modifiers changed from: package-private */
    public void b(BluetoothDevice bluetoothDevice, byte[] bArr) {
        b bVar = (b) this.p;
        if (bVar != null) {
            if (this.r == null) {
                this.b.post(new o(bVar, bluetoothDevice, new Data(bArr)));
                return;
            }
            this.b.post(new q(this, bluetoothDevice, bArr));
            if (this.s == null) {
                this.s = new d();
            }
            com.android.chileaf.bluetooth.connect.data.b bVar2 = this.r;
            d dVar = this.s;
            int i2 = this.u;
            this.u = i2 + 1;
            if (bVar2.a(dVar, bArr, i2)) {
                this.b.post(new p(bVar, bluetoothDevice, this.s.a()));
                this.s = null;
                this.u = 0;
            }
        }
    }

    /* access modifiers changed from: package-private */
    public boolean i() {
        return this.u > 0;
    }

    /* access modifiers changed from: package-private */
    public s0 a(u0 u0Var) {
        super.a(u0Var);
        return this;
    }

    s0(Request.Type type, BluetoothGattCharacteristic bluetoothGattCharacteristic) {
        super(type, bluetoothGattCharacteristic);
    }

    public s0 a(j jVar) {
        super.a(jVar);
        return this;
    }

    public s0 a(com.android.chileaf.bluetooth.connect.f1.d dVar) {
        super.a(dVar);
        return this;
    }

    public s0 a(b bVar) {
        super.a(bVar);
        return this;
    }

    /* access modifiers changed from: package-private */
    public boolean a(byte[] bArr) {
        a aVar = this.t;
        return aVar == null || aVar.a(bArr);
    }

    public /* synthetic */ void a(BluetoothDevice bluetoothDevice, byte[] bArr) {
        h hVar = this.q;
        if (hVar != null) {
            hVar.a(bluetoothDevice, bArr, this.u);
        }
    }
}
