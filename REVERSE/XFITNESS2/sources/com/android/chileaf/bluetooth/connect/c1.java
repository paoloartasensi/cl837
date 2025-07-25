package com.android.chileaf.bluetooth.connect;

import android.bluetooth.BluetoothDevice;
import android.os.Handler;
import com.android.chileaf.bluetooth.connect.data.Data;
import com.android.chileaf.bluetooth.connect.data.a;
import com.android.chileaf.bluetooth.connect.data.d;
import com.android.chileaf.bluetooth.connect.f1.b;
import com.android.chileaf.bluetooth.connect.f1.h;

/* compiled from: ValueChangedCallback */
public class c1 {
    private h a;
    private b b;
    private com.android.chileaf.bluetooth.connect.data.b c;
    private d d;
    private a e;

    /* renamed from: f  reason: collision with root package name */
    private final Handler f1035f;

    /* renamed from: g  reason: collision with root package name */
    private int f1036g = 0;

    c1(Handler handler) {
        this.f1035f = handler;
    }

    public c1 a(b bVar) {
        this.b = bVar;
        return this;
    }

    /* access modifiers changed from: package-private */
    public void b(BluetoothDevice bluetoothDevice, byte[] bArr) {
        b bVar = this.b;
        if (bVar != null) {
            if (this.c == null) {
                this.f1035f.post(new z(bVar, bluetoothDevice, new Data(bArr)));
                return;
            }
            this.f1035f.post(new x(this, bluetoothDevice, bArr));
            if (this.d == null) {
                this.d = new d();
            }
            com.android.chileaf.bluetooth.connect.data.b bVar2 = this.c;
            d dVar = this.d;
            int i2 = this.f1036g;
            this.f1036g = i2 + 1;
            if (bVar2.a(dVar, bArr, i2)) {
                this.f1035f.post(new y(bVar, bluetoothDevice, this.d.a()));
                this.d = null;
                this.f1036g = 0;
            }
        }
    }

    /* access modifiers changed from: package-private */
    public c1 a() {
        this.b = null;
        this.c = null;
        this.a = null;
        this.d = null;
        return this;
    }

    /* access modifiers changed from: package-private */
    public boolean a(byte[] bArr) {
        a aVar = this.e;
        return aVar == null || aVar.a(bArr);
    }

    public /* synthetic */ void a(BluetoothDevice bluetoothDevice, byte[] bArr) {
        h hVar = this.a;
        if (hVar != null) {
            hVar.a(bluetoothDevice, bArr, this.f1036g);
        }
    }
}
