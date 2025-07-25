package com.android.chileaf.bluetooth.connect;

import android.bluetooth.BluetoothDevice;
import com.android.chileaf.bluetooth.connect.data.Data;
import com.android.chileaf.bluetooth.connect.data.a;
import com.android.chileaf.bluetooth.connect.data.d;
import com.android.chileaf.bluetooth.connect.f1.b;
import com.android.chileaf.bluetooth.connect.f1.h;

/* compiled from: WaitForValueChangedRequest */
public final class d1 extends f0<b> {
    private h u;
    private com.android.chileaf.bluetooth.connect.data.b v;
    private d w;
    private a x;
    private int y;

    /* access modifiers changed from: package-private */
    public boolean l() {
        return this.y > 0;
    }

    /* access modifiers changed from: package-private */
    public d1 a(u0 u0Var) {
        super.a(u0Var);
        return this;
    }

    /* access modifiers changed from: package-private */
    public boolean a(byte[] bArr) {
        a aVar = this.x;
        return aVar == null || aVar.a(bArr);
    }

    /* access modifiers changed from: package-private */
    public void a(BluetoothDevice bluetoothDevice, byte[] bArr) {
        b bVar = (b) this.r;
        if (bVar != null) {
            if (this.v == null) {
                this.b.post(new a0(bVar, bluetoothDevice, new Data(bArr)));
                return;
            }
            this.b.post(new b0(this, bluetoothDevice, bArr, this.y));
            if (this.w == null) {
                this.w = new d();
            }
            com.android.chileaf.bluetooth.connect.data.b bVar2 = this.v;
            d dVar = this.w;
            int i2 = this.y;
            this.y = i2 + 1;
            if (bVar2.a(dVar, bArr, i2)) {
                this.b.post(new c0(bVar, bluetoothDevice, this.w.a()));
                this.w = null;
                this.y = 0;
            }
        }
    }

    public /* synthetic */ void a(BluetoothDevice bluetoothDevice, byte[] bArr, int i2) {
        h hVar = this.u;
        if (hVar != null) {
            hVar.a(bluetoothDevice, bArr, i2);
        }
    }
}
