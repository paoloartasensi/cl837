package com.android.chileaf.bluetooth.connect;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGattCharacteristic;
import com.android.chileaf.bluetooth.connect.Request;
import com.android.chileaf.bluetooth.connect.data.Data;
import com.android.chileaf.bluetooth.connect.data.e;
import com.android.chileaf.bluetooth.connect.f1.a;
import com.android.chileaf.bluetooth.connect.f1.c;
import com.android.chileaf.bluetooth.connect.f1.d;
import com.android.chileaf.bluetooth.connect.f1.j;
import com.android.chileaf.bluetooth.connect.f1.k;
import java.util.Arrays;

/* compiled from: WriteRequest */
public final class e1 extends y0<c> {
    private static final com.android.chileaf.bluetooth.connect.data.c y = new e();
    private k q;
    private com.android.chileaf.bluetooth.connect.data.c r;
    private final byte[] s;
    private final int t;
    private byte[] u;
    private byte[] v;
    private int w;
    private boolean x;

    e1(Request.Type type) {
        this(type, (BluetoothGattCharacteristic) null);
    }

    /* access modifiers changed from: package-private */
    public byte[] b(int i2) {
        if (this.r == null || this.s == null) {
            this.x = true;
            byte[] bArr = this.s;
            this.u = bArr;
            return bArr;
        }
        int i3 = this.t != 4 ? i2 - 3 : i2 - 12;
        byte[] bArr2 = this.v;
        if (bArr2 == null) {
            bArr2 = this.r.a(this.s, this.w, i3);
        }
        if (bArr2 != null) {
            this.v = this.r.a(this.s, this.w + 1, i3);
        }
        if (this.v == null) {
            this.x = true;
        }
        this.u = bArr2;
        return bArr2;
    }

    public /* synthetic */ void f(BluetoothDevice bluetoothDevice) {
        T t2 = this.p;
        if (t2 != null) {
            ((c) t2).a(bluetoothDevice, new Data(this.s));
        }
    }

    /* access modifiers changed from: package-private */
    public int i() {
        return this.t;
    }

    /* access modifiers changed from: package-private */
    public boolean j() {
        return !this.x;
    }

    public e1 k() {
        this.r = y;
        this.q = null;
        return this;
    }

    e1(Request.Type type, BluetoothGattCharacteristic bluetoothGattCharacteristic) {
        super(type, bluetoothGattCharacteristic);
        this.w = 0;
        this.x = false;
        this.s = null;
        this.t = 0;
        this.x = true;
    }

    /* access modifiers changed from: package-private */
    public e1 a(u0 u0Var) {
        super.a(u0Var);
        return this;
    }

    public e1 a(j jVar) {
        super.a(jVar);
        return this;
    }

    public e1 a(d dVar) {
        super.a(dVar);
        return this;
    }

    public e1 a(a aVar) {
        super.a(aVar);
        return this;
    }

    public e1 a(c cVar) {
        super.a(cVar);
        return this;
    }

    public /* synthetic */ void a(BluetoothDevice bluetoothDevice, byte[] bArr) {
        k kVar = this.q;
        if (kVar != null) {
            kVar.a(bluetoothDevice, bArr, this.w);
        }
    }

    e1(Request.Type type, BluetoothGattCharacteristic bluetoothGattCharacteristic, byte[] bArr, int i2, int i3, int i4) {
        super(type, bluetoothGattCharacteristic);
        this.w = 0;
        this.x = false;
        this.s = l0.a(bArr, i2, i3);
        this.t = i4;
    }

    /* access modifiers changed from: package-private */
    public boolean b(BluetoothDevice bluetoothDevice, byte[] bArr) {
        this.b.post(new d0(this, bluetoothDevice, bArr));
        this.w++;
        if (this.x) {
            this.b.post(new e0(this, bluetoothDevice));
        }
        return Arrays.equals(bArr, this.u);
    }
}
