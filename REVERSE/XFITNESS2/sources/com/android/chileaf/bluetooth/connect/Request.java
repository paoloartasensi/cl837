package com.android.chileaf.bluetooth.connect;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.os.ConditionVariable;
import android.os.Handler;
import com.android.chileaf.bluetooth.connect.f1.a;
import com.android.chileaf.bluetooth.connect.f1.d;
import com.android.chileaf.bluetooth.connect.f1.e;
import com.android.chileaf.bluetooth.connect.f1.j;

public abstract class Request {
    protected u0 a;
    protected Handler b;
    final Type c;
    final BluetoothGattCharacteristic d;
    final BluetoothGattDescriptor e;

    /* renamed from: f  reason: collision with root package name */
    a f1021f;

    /* renamed from: g  reason: collision with root package name */
    j f1022g;

    /* renamed from: h  reason: collision with root package name */
    d f1023h;

    /* renamed from: i  reason: collision with root package name */
    e f1024i;

    /* renamed from: j  reason: collision with root package name */
    a f1025j;
    j k;
    d l;
    boolean m;
    boolean n;
    boolean o;

    enum Type {
        SET,
        CONNECT,
        DISCONNECT,
        CREATE_BOND,
        REMOVE_BOND,
        WRITE,
        NOTIFY,
        INDICATE,
        READ,
        WRITE_DESCRIPTOR,
        READ_DESCRIPTOR,
        BEGIN_RELIABLE_WRITE,
        EXECUTE_RELIABLE_WRITE,
        ABORT_RELIABLE_WRITE,
        ENABLE_NOTIFICATIONS,
        ENABLE_INDICATIONS,
        DISABLE_NOTIFICATIONS,
        DISABLE_INDICATIONS,
        WAIT_FOR_NOTIFICATION,
        WAIT_FOR_INDICATION,
        WAIT_FOR_READ,
        WAIT_FOR_WRITE,
        WAIT_FOR_CONDITION,
        SET_VALUE,
        SET_DESCRIPTOR_VALUE,
        READ_BATTERY_LEVEL,
        ENABLE_BATTERY_LEVEL_NOTIFICATIONS,
        DISABLE_BATTERY_LEVEL_NOTIFICATIONS,
        ENABLE_SERVICE_CHANGED_INDICATIONS,
        REQUEST_MTU,
        REQUEST_CONNECTION_PRIORITY,
        SET_PREFERRED_PHY,
        READ_PHY,
        READ_RSSI,
        REFRESH_CACHE,
        SLEEP
    }

    Request(Type type) {
        this.c = type;
        this.d = null;
        this.e = null;
        new ConditionVariable(true);
    }

    @Deprecated
    public static s0 b(BluetoothGattCharacteristic bluetoothGattCharacteristic) {
        return new s0(Type.READ, bluetoothGattCharacteristic);
    }

    static p0 d() {
        return new p0(Type.DISCONNECT);
    }

    static n0 e(BluetoothDevice bluetoothDevice) {
        return new n0(Type.CONNECT, bluetoothDevice);
    }

    static e1 f() {
        return new e1(Type.ENABLE_SERVICE_CHANGED_INDICATIONS);
    }

    @Deprecated
    public static s0 g() {
        return new s0(Type.READ_BATTERY_LEVEL);
    }

    @Deprecated
    public static t0 h() {
        return new t0(Type.READ_RSSI);
    }

    /* access modifiers changed from: package-private */
    public Request a(u0 u0Var) {
        this.a = u0Var;
        if (this.b == null) {
            this.b = u0Var.a();
        }
        return this;
    }

    /* access modifiers changed from: package-private */
    public void c(BluetoothDevice bluetoothDevice) {
        if (!this.n) {
            this.n = true;
            this.b.post(new t(this, bluetoothDevice));
        }
    }

    @Deprecated
    public static e1 e() {
        return new e1(Type.ENABLE_BATTERY_LEVEL_NOTIFICATIONS);
    }

    public /* synthetic */ void b(BluetoothDevice bluetoothDevice) {
        j jVar = this.f1022g;
        if (jVar != null) {
            jVar.a(bluetoothDevice);
        }
        j jVar2 = this.k;
        if (jVar2 != null) {
            jVar2.a(bluetoothDevice);
        }
    }

    /* access modifiers changed from: package-private */
    public void d(BluetoothDevice bluetoothDevice) {
        if (!this.o) {
            this.o = true;
            this.b.post(new v(this, bluetoothDevice));
        }
    }

    @Deprecated
    public static e1 a(BluetoothGattCharacteristic bluetoothGattCharacteristic, byte[] bArr) {
        return new e1(Type.WRITE, bluetoothGattCharacteristic, bArr, 0, bArr != null ? bArr.length : 0, bluetoothGattCharacteristic != null ? bluetoothGattCharacteristic.getWriteType() : 2);
    }

    /* access modifiers changed from: package-private */
    public void c() {
        if (!this.o) {
            this.o = true;
            this.b.post(new s(this));
        }
    }

    Request(Type type, BluetoothGattCharacteristic bluetoothGattCharacteristic) {
        this.c = type;
        this.d = bluetoothGattCharacteristic;
        this.e = null;
        new ConditionVariable(true);
    }

    @Deprecated
    public static e1 a(BluetoothGattCharacteristic bluetoothGattCharacteristic) {
        return new e1(Type.ENABLE_NOTIFICATIONS, bluetoothGattCharacteristic);
    }

    /* access modifiers changed from: package-private */
    public void b(BluetoothDevice bluetoothDevice, int i2) {
        if (!this.o) {
            this.o = true;
            this.b.post(new u(this, bluetoothDevice, i2));
        }
    }

    @Deprecated
    public static o0 a(int i2) {
        return new o0(Type.REQUEST_CONNECTION_PRIORITY, i2);
    }

    public Request a(j jVar) {
        this.f1022g = jVar;
        return this;
    }

    public Request a(d dVar) {
        this.f1023h = dVar;
        return this;
    }

    public /* synthetic */ void b() {
        e eVar = this.f1024i;
        if (eVar != null) {
            eVar.a();
        }
    }

    public Request a(a aVar) {
        this.f1021f = aVar;
        return this;
    }

    public void a() {
        this.a.a(this);
    }

    public /* synthetic */ void a(BluetoothDevice bluetoothDevice) {
        a aVar = this.f1021f;
        if (aVar != null) {
            aVar.a(bluetoothDevice);
        }
        a aVar2 = this.f1025j;
        if (aVar2 != null) {
            aVar2.a(bluetoothDevice);
        }
    }

    public /* synthetic */ void a(BluetoothDevice bluetoothDevice, int i2) {
        d dVar = this.f1023h;
        if (dVar != null) {
            dVar.a(bluetoothDevice, i2);
        }
        d dVar2 = this.l;
        if (dVar2 != null) {
            dVar2.a(bluetoothDevice, i2);
        }
    }
}
