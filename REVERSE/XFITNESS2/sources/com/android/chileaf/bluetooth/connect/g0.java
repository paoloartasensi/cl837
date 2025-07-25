package com.android.chileaf.bluetooth.connect;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGattCharacteristic;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Looper;
import com.android.chileaf.bluetooth.connect.f1.j;
import com.android.chileaf.bluetooth.connect.i0;
import java.util.UUID;

/* compiled from: BleManager */
public abstract class g0<E extends i0> {
    static final UUID e = UUID.fromString("00002902-0000-1000-8000-00805f9b34fb");

    /* renamed from: f  reason: collision with root package name */
    static final UUID f1047f = UUID.fromString("0000180F-0000-1000-8000-00805f9b34fb");

    /* renamed from: g  reason: collision with root package name */
    static final UUID f1048g = UUID.fromString("00002A19-0000-1000-8000-00805f9b34fb");

    /* renamed from: h  reason: collision with root package name */
    static final UUID f1049h = UUID.fromString("00001801-0000-1000-8000-00805f9b34fb");

    /* renamed from: i  reason: collision with root package name */
    static final UUID f1050i = UUID.fromString("00002A05-0000-1000-8000-00805f9b34fb");
    private final Context a;
    final b b;
    protected E c;
    private final BroadcastReceiver d;

    /* compiled from: BleManager */
    class a extends BroadcastReceiver {
        a() {
        }

        public void onReceive(Context context, Intent intent) {
            BluetoothDevice bluetoothDevice = (BluetoothDevice) intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE");
            BluetoothDevice d = g0.this.b.d();
            if (d != null && bluetoothDevice != null && bluetoothDevice.getAddress().equals(d.getAddress())) {
                int intExtra = intent.getIntExtra("android.bluetooth.device.extra.PAIRING_VARIANT", 0);
                g0 g0Var = g0.this;
                g0Var.a(3, "[Broadcast] Action received: android.bluetooth.device.action.PAIRING_REQUEST, pairing variant: " + com.android.chileaf.bluetooth.connect.h1.a.b(intExtra) + " (" + intExtra + ")");
                g0.this.a(bluetoothDevice, intExtra);
            }
        }
    }

    /* compiled from: BleManager */
    protected abstract class b extends j0 {
        protected b(g0 g0Var) {
        }

        /* access modifiers changed from: protected */
        public /* bridge */ /* synthetic */ void i() {
            super.i();
        }
    }

    public g0(Context context) {
        this(context, new Handler(Looper.getMainLooper()));
    }

    /* access modifiers changed from: protected */
    public int a(boolean z) {
        return z ? 1600 : 300;
    }

    public abstract void a(int i2, String str);

    /* access modifiers changed from: protected */
    public void a(BluetoothDevice bluetoothDevice, int i2) {
    }

    public final void a(E e2) {
        this.c = e2;
    }

    /* access modifiers changed from: protected */
    public s0 b(BluetoothGattCharacteristic bluetoothGattCharacteristic) {
        s0 b2 = Request.b(bluetoothGattCharacteristic);
        b2.a((u0) this.b);
        return b2;
    }

    /* access modifiers changed from: protected */
    public final Context c() {
        return this.a;
    }

    /* access modifiers changed from: protected */
    public abstract g0<E>.defpackage.b d();

    public final boolean e() {
        return this.b.f();
    }

    /* access modifiers changed from: protected */
    @Deprecated
    public void f() {
        s0 g2 = Request.g();
        g2.a((u0) this.b);
        g2.a(this.b.c());
        g2.a();
    }

    /* access modifiers changed from: protected */
    public t0 g() {
        t0 h2 = Request.h();
        h2.a((u0) this.b);
        return h2;
    }

    /* access modifiers changed from: protected */
    @Deprecated
    public boolean h() {
        return false;
    }

    /* access modifiers changed from: protected */
    public boolean i() {
        return false;
    }

    public g0(Context context, Handler handler) {
        this.d = new a();
        this.a = context;
        g0<E>.defpackage.b d2 = d();
        this.b = d2;
        d2.a(this, handler);
        context.registerReceiver(this.d, new IntentFilter("android.bluetooth.device.action.PAIRING_REQUEST"));
    }

    public final n0 a(BluetoothDevice bluetoothDevice) {
        if (this.c == null) {
            throw new NullPointerException("Set mCallbacks using setManagerCallbacks(E mCallbacks) before connecting");
        } else if (bluetoothDevice != null) {
            n0 e2 = Request.e(bluetoothDevice);
            e2.a(h());
            e2.a((u0) this.b);
            return e2;
        } else {
            throw new NullPointerException("Bluetooth device not specified");
        }
    }

    /* access modifiers changed from: protected */
    public c1 c(BluetoothGattCharacteristic bluetoothGattCharacteristic) {
        return this.b.a((Object) bluetoothGattCharacteristic);
    }

    /* access modifiers changed from: protected */
    @Deprecated
    public void b() {
        e1 e2 = Request.e();
        e2.a((u0) this.b);
        e2.a((com.android.chileaf.bluetooth.connect.f1.a) new a(this));
        e2.a((j) new b(this));
        e2.a();
    }

    public /* synthetic */ void c(BluetoothDevice bluetoothDevice) {
        a(4, "Battery Level notifications enabled");
    }

    public final p0 a() {
        p0 d2 = Request.d();
        d2.a((u0) this.b);
        return d2;
    }

    public /* synthetic */ void b(BluetoothDevice bluetoothDevice) {
        this.b.k();
    }

    /* access modifiers changed from: protected */
    public e1 a(BluetoothGattCharacteristic bluetoothGattCharacteristic) {
        e1 a2 = Request.a(bluetoothGattCharacteristic);
        a2.a((u0) this.b);
        return a2;
    }

    /* access modifiers changed from: protected */
    public e1 a(BluetoothGattCharacteristic bluetoothGattCharacteristic, byte[] bArr) {
        e1 a2 = Request.a(bluetoothGattCharacteristic, bArr);
        a2.a((u0) this.b);
        return a2;
    }

    /* access modifiers changed from: protected */
    public o0 a(int i2) {
        o0 a2 = Request.a(i2);
        a2.a((u0) this.b);
        return a2;
    }
}
