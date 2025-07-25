package com.android.chileaf.fitness;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import com.android.chileaf.bluetooth.connect.data.Data;
import com.android.chileaf.bluetooth.connect.e1;
import com.android.chileaf.bluetooth.connect.f1.b;
import com.android.chileaf.bluetooth.connect.f1.c;
import com.android.chileaf.bluetooth.connect.f1.d;
import com.android.chileaf.bluetooth.connect.f1.i;
import com.android.chileaf.bluetooth.connect.f1.j;
import com.android.chileaf.bluetooth.connect.g0;
import com.android.chileaf.bluetooth.connect.s0;
import com.android.chileaf.bluetooth.connect.t0;
import com.android.chileaf.fitness.common.battery.BatteryLevelDataCallback;
import com.android.chileaf.fitness.w;
import com.android.chileaf.util.HexUtil;
import java.util.UUID;

public abstract class FitnessManager<T extends w> extends g0<T> {
    protected static final UUID F = UUID.fromString("AAE28F00-71B5-42A1-8C3C-F9CF6AC969D0");
    protected static final UUID G = UUID.fromString("AAE28F01-71B5-42A1-8C3C-F9CF6AC969D0");
    protected static final UUID H = UUID.fromString("AAE28F02-71B5-42A1-8C3C-F9CF6AC969D0");
    /* access modifiers changed from: private */
    public static final UUID I = UUID.fromString("0000180F-0000-1000-8000-00805f9b34fb");
    /* access modifiers changed from: private */
    public static final UUID J = UUID.fromString("00002A19-0000-1000-8000-00805f9b34fb");
    /* access modifiers changed from: private */
    public static final UUID K = UUID.fromString("0000180A-0000-1000-8000-00805f9b34fb");
    /* access modifiers changed from: private */
    public static final UUID L = UUID.fromString("00002A23-0000-1000-8000-00805f9b34fb");
    /* access modifiers changed from: private */
    public static final UUID M = UUID.fromString("00002A24-0000-1000-8000-00805f9b34fb");
    /* access modifiers changed from: private */
    public static final UUID N = UUID.fromString("00002A25-0000-1000-8000-00805f9b34fb");
    /* access modifiers changed from: private */
    public static final UUID O = UUID.fromString("00002A26-0000-1000-8000-00805f9b34fb");
    /* access modifiers changed from: private */
    public static final UUID P = UUID.fromString("00002A27-0000-1000-8000-00805f9b34fb");
    /* access modifiers changed from: private */
    public static final UUID Q = UUID.fromString("00002A28-0000-1000-8000-00805f9b34fb");
    /* access modifiers changed from: private */
    public static final UUID R = UUID.fromString("00002A29-0000-1000-8000-00805f9b34fb");
    private final b A = new o(this);
    private final b B = new c(this);
    private final b C = new n(this);
    private final b D = new u(this);
    private final b E = new BatteryLevelDataCallback() {
        public void b(BluetoothDevice bluetoothDevice, int i2) {
            FitnessManager fitnessManager = FitnessManager.this;
            fitnessManager.a(4, "Battery Level received: " + i2 + "%");
            ((w) FitnessManager.this.c).b(bluetoothDevice, i2);
            Integer unused = FitnessManager.this.v = Integer.valueOf(i2);
            if (FitnessManager.this.n()) {
                t0 i3 = FitnessManager.this.g();
                i3.a(FitnessManager.this.w);
                i3.a();
            }
        }

        public void b(BluetoothDevice bluetoothDevice, Data data) {
            FitnessManager fitnessManager = FitnessManager.this;
            fitnessManager.a(5, "Invalid Battery Level data received: " + data);
        }
    };

    /* renamed from: j  reason: collision with root package name */
    protected boolean f1136j = false;
    protected BluetoothGattCharacteristic k;
    protected BluetoothGattCharacteristic l;
    /* access modifiers changed from: private */
    public BluetoothGattCharacteristic m;
    /* access modifiers changed from: private */
    public BluetoothGattCharacteristic n;
    /* access modifiers changed from: private */
    public BluetoothGattCharacteristic o;
    /* access modifiers changed from: private */
    public BluetoothGattCharacteristic p;
    /* access modifiers changed from: private */
    public BluetoothGattCharacteristic q;
    /* access modifiers changed from: private */
    public BluetoothGattCharacteristic r;
    /* access modifiers changed from: private */
    public BluetoothGattCharacteristic s;
    /* access modifiers changed from: private */
    public BluetoothGattCharacteristic t;
    private boolean u;
    /* access modifiers changed from: private */
    public Integer v;
    /* access modifiers changed from: private */
    public final i w = new e(this);
    private final b x = new q(this);
    private final b y = new p(this);
    private final b z = new m(this);

    protected abstract class a extends g0<T>.b {
        protected a() {
            super(FitnessManager.this);
        }

        /* access modifiers changed from: protected */
        public boolean b(BluetoothGatt bluetoothGatt) {
            BluetoothGattService service = bluetoothGatt.getService(FitnessManager.I);
            if (service != null) {
                BluetoothGattCharacteristic unused = FitnessManager.this.m = service.getCharacteristic(FitnessManager.J);
            }
            boolean z = FitnessManager.this.m != null;
            BluetoothGattService service2 = bluetoothGatt.getService(FitnessManager.K);
            if (service2 != null) {
                BluetoothGattCharacteristic unused2 = FitnessManager.this.n = service2.getCharacteristic(FitnessManager.L);
                BluetoothGattCharacteristic unused3 = FitnessManager.this.o = service2.getCharacteristic(FitnessManager.M);
                BluetoothGattCharacteristic unused4 = FitnessManager.this.p = service2.getCharacteristic(FitnessManager.N);
                BluetoothGattCharacteristic unused5 = FitnessManager.this.q = service2.getCharacteristic(FitnessManager.O);
                BluetoothGattCharacteristic unused6 = FitnessManager.this.r = service2.getCharacteristic(FitnessManager.P);
                BluetoothGattCharacteristic unused7 = FitnessManager.this.s = service2.getCharacteristic(FitnessManager.Q);
                BluetoothGattCharacteristic unused8 = FitnessManager.this.t = service2.getCharacteristic(FitnessManager.R);
            }
            boolean z2 = (FitnessManager.this.n == null || FitnessManager.this.o == null || FitnessManager.this.p == null || FitnessManager.this.q == null || FitnessManager.this.r == null || FitnessManager.this.s == null || FitnessManager.this.t == null) ? false : true;
            if (!z || !z2) {
                return false;
            }
            return true;
        }

        /* access modifiers changed from: protected */
        public boolean c(BluetoothGatt bluetoothGatt) {
            boolean z;
            boolean z2;
            BluetoothGattService service = bluetoothGatt.getService(FitnessManager.this.m());
            if (service != null) {
                FitnessManager fitnessManager = FitnessManager.this;
                fitnessManager.k = service.getCharacteristic(fitnessManager.k());
                FitnessManager fitnessManager2 = FitnessManager.this;
                fitnessManager2.l = service.getCharacteristic(fitnessManager2.l());
                for (BluetoothGattCharacteristic next : service.getCharacteristics()) {
                    if (!(next == null || next.getUuid() == null || !next.getUuid().toString().equalsIgnoreCase("AAE21541-71B5-42A1-8C3C-F9CF6AC969D0"))) {
                        FitnessManager.this.f1136j = true;
                    }
                }
            }
            BluetoothGattCharacteristic bluetoothGattCharacteristic = FitnessManager.this.l;
            if (bluetoothGattCharacteristic != null) {
                int properties = bluetoothGattCharacteristic.getProperties();
                z2 = (properties & 8) > 0;
                z = (properties & 4) > 0;
                if (z2) {
                    FitnessManager.this.l.setWriteType(2);
                    FitnessManager.this.a(3, "TXCharacteristic notifications WRITE_TYPE_DEFAULT");
                }
            } else {
                z = false;
                z2 = false;
            }
            FitnessManager fitnessManager3 = FitnessManager.this;
            if (fitnessManager3.k == null || fitnessManager3.l == null || (!z && !z2)) {
                return false;
            }
            return true;
        }

        /* access modifiers changed from: protected */
        public void e() {
            if (Build.VERSION.SDK_INT >= 21) {
                FitnessManager.this.a(1).a();
            }
            FitnessManager.this.p();
            FitnessManager.this.o();
            FitnessManager.this.j();
        }

        /* access modifiers changed from: protected */
        public void h() {
            BluetoothGattCharacteristic unused = FitnessManager.this.m = null;
            FitnessManager fitnessManager = FitnessManager.this;
            fitnessManager.k = null;
            fitnessManager.l = null;
            Integer unused2 = fitnessManager.v = null;
            FitnessManager.this.f1136j = false;
        }
    }

    static {
        System.loadLibrary("fitness");
    }

    public FitnessManager(Context context) {
        super(context);
    }

    /* access modifiers changed from: protected */
    public void a(String str, boolean z2) {
    }

    /* access modifiers changed from: protected */
    public int[] a(long j2) {
        return new int[]{(int) (j2 >> 24), (int) (j2 >> 16), (int) (j2 >> 8), (int) j2};
    }

    /* access modifiers changed from: protected */
    public native byte checkSum(byte[] bArr);

    /* access modifiers changed from: protected */
    public native float fetchBeat(int i2);

    /* access modifiers changed from: protected */
    public native byte[] fetchPassCode();

    /* access modifiers changed from: protected */
    public UUID l() {
        return H;
    }

    /* access modifiers changed from: protected */
    public UUID m() {
        return F;
    }

    public boolean n() {
        return true;
    }

    public void o() {
        if (e()) {
            s0 b = b(this.m);
            b.a(this.E);
            b.a((d) new a(this));
            b.a();
        }
    }

    public void p() {
        if (e()) {
            s0 b = b(this.n);
            b.a(this.x);
            b.a((d) new j(this));
            b.a();
            s0 b2 = b(this.o);
            b2.a(this.y);
            b2.a((d) new h(this));
            b2.a();
            s0 b3 = b(this.p);
            b3.a(this.z);
            b3.a((d) new r(this));
            b3.a();
            s0 b4 = b(this.q);
            b4.a(this.A);
            b4.a((d) new f(this));
            b4.a();
            s0 b5 = b(this.r);
            b5.a(this.B);
            b5.a((d) new b(this));
            b5.a();
            s0 b6 = b(this.s);
            b6.a(this.C);
            b6.a((d) new k(this));
            b6.a();
            s0 b7 = b(this.t);
            b7.a(this.D);
            b7.a((d) new g(this));
            b7.a();
        }
    }

    public /* synthetic */ void i(BluetoothDevice bluetoothDevice, int i2) {
        a(5, "Profile vendor characteristic not found");
    }

    public /* synthetic */ void j(BluetoothDevice bluetoothDevice, int i2) {
        a(5, "Profile system characteristic not found");
    }

    /* access modifiers changed from: protected */
    public UUID k() {
        return G;
    }

    public /* synthetic */ void l(BluetoothDevice bluetoothDevice, int i2) {
        a(5, "Tx writeCharacteristic failure");
    }

    public void b(boolean z2) {
        this.u = z2;
    }

    public /* synthetic */ void c(BluetoothDevice bluetoothDevice, int i2) {
        ((w) this.c).c(bluetoothDevice, i2);
    }

    public /* synthetic */ void d(BluetoothDevice bluetoothDevice, Data data) {
        if (data.b() > 0 && data.a() != null) {
            String a2 = HexUtil.a(data.a());
            if (!TextUtils.isEmpty(a2)) {
                a(4, "Firmware Version: " + a2);
                ((w) this.c).b(bluetoothDevice, a2);
            }
        }
    }

    public /* synthetic */ void e(BluetoothDevice bluetoothDevice, Data data) {
        if (data.b() > 0 && data.a() != null) {
            String a2 = HexUtil.a(data.a());
            if (!TextUtils.isEmpty(a2)) {
                a(4, "Hardware Version: " + a2);
                ((w) this.c).d(bluetoothDevice, a2);
            }
        }
    }

    public /* synthetic */ void f(BluetoothDevice bluetoothDevice, Data data) {
        if (data.b() > 0 && data.a() != null) {
            String a2 = HexUtil.a(data.a());
            if (!TextUtils.isEmpty(a2)) {
                a(4, "Software Version: " + a2);
                ((w) this.c).g(bluetoothDevice, a2);
            }
        }
    }

    public /* synthetic */ void g(BluetoothDevice bluetoothDevice, Data data) {
        if (data.b() > 0 && data.a() != null) {
            String a2 = HexUtil.a(data.a());
            if (!TextUtils.isEmpty(a2)) {
                a(4, "Vendor Name: " + a2);
                ((w) this.c).f(bluetoothDevice, a2);
            }
        }
    }

    public /* synthetic */ void h(BluetoothDevice bluetoothDevice, int i2) {
        a(5, "Profile software characteristic not found");
    }

    public void j() {
        if (e()) {
            c(this.m).a(this.E);
            e1 a2 = a(this.m);
            a2.a((j) new d(this));
            a2.a((d) new i(this));
            a2.a();
        }
    }

    public /* synthetic */ void k(BluetoothDevice bluetoothDevice, int i2) {
        a(5, "Profile model characteristic not found");
    }

    public /* synthetic */ void b(BluetoothDevice bluetoothDevice, Data data) {
        if (data.b() > 0 && data.a() != null) {
            String a2 = HexUtil.a(data.a());
            if (!TextUtils.isEmpty(a2)) {
                a(4, "Model Name: " + a2);
                a(a2, this.f1136j);
                ((w) this.c).c(bluetoothDevice, a2);
            }
        }
    }

    public /* synthetic */ void c(BluetoothDevice bluetoothDevice, Data data) {
        if (data.b() > 0 && data.a() != null) {
            String a2 = HexUtil.a(data.a());
            if (!TextUtils.isEmpty(a2)) {
                a(4, "Serial Number: " + a2);
                ((w) this.c).a(bluetoothDevice, a2);
            }
        }
    }

    public /* synthetic */ void h(BluetoothDevice bluetoothDevice, Data data) {
        a(2, "send:" + com.android.chileaf.bluetooth.connect.h1.a.a(data.a()));
    }

    public void a(int i2, String str) {
        if (this.u) {
            Log.println(i2, getClass().getSimpleName(), str);
        }
    }

    public void a(int i2, String str, Object... objArr) {
        a(i2, String.format(str, objArr));
    }

    public /* synthetic */ void d(BluetoothDevice bluetoothDevice, int i2) {
        a(5, "Battery Level characteristic not found");
    }

    public /* synthetic */ void e(BluetoothDevice bluetoothDevice, int i2) {
        a(5, "Profile serial characteristic not found");
    }

    public /* synthetic */ void f(BluetoothDevice bluetoothDevice, int i2) {
        a(5, "Profile firmware characteristic not found");
    }

    public /* synthetic */ void g(BluetoothDevice bluetoothDevice, int i2) {
        a(5, "Profile hardware characteristic not found");
    }

    public /* synthetic */ void a(BluetoothDevice bluetoothDevice, Data data) {
        if (data.b() > 0 && data.a() != null) {
            String a2 = HexUtil.a(data.a());
            if (!TextUtils.isEmpty(a2)) {
                a(4, "System Id: " + a2);
                ((w) this.c).e(bluetoothDevice, a2);
            }
        }
    }

    public /* synthetic */ void d(BluetoothDevice bluetoothDevice) {
        a(3, "Battery Level notifications enabled");
    }

    public /* synthetic */ void e(BluetoothDevice bluetoothDevice) {
        a(3, "Tx writeCharacteristic success");
    }

    public /* synthetic */ void b(BluetoothDevice bluetoothDevice, int i2) {
        a(5, "Battery Level characteristic not found");
    }

    /* access modifiers changed from: protected */
    public void a(byte[] bArr) {
        BluetoothGattCharacteristic bluetoothGattCharacteristic;
        if (e() && (bluetoothGattCharacteristic = this.l) != null) {
            e1 a2 = a(bluetoothGattCharacteristic, bArr);
            a2.k();
            a2.a((c) new s(this));
            a2.a((j) new l(this));
            a2.a((d) new t(this));
            a2.a();
        }
    }
}
