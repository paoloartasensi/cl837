package com.chileaf.fitness.device.wear.cl830;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.content.Context;
import com.android.chileaf.bluetooth.connect.data.Data;
import com.android.chileaf.bluetooth.connect.e1;
import com.android.chileaf.bluetooth.connect.f1.b;
import com.android.chileaf.bluetooth.connect.f1.d;
import com.android.chileaf.bluetooth.connect.f1.j;
import com.android.chileaf.bluetooth.connect.g0;
import com.android.chileaf.bluetooth.connect.s0;
import com.android.chileaf.fitness.FitnessManager;
import com.android.chileaf.fitness.common.b.c;
import com.android.chileaf.fitness.common.heart.BodySensorLocationDataCallback;
import com.android.chileaf.fitness.common.sport.BodySportDataCallback;
import com.chileaf.fitness.device.wear.cl830.heart.HeartRateBeatMeasurementDataCallback;
import java.util.List;
import java.util.UUID;

public class CL830Manager extends FitnessManager<h> {
    /* access modifiers changed from: private */
    public static final UUID Y = UUID.fromString("0000180D-0000-1000-8000-00805f9b34fb");
    /* access modifiers changed from: private */
    public static final UUID Z = UUID.fromString("00002A38-0000-1000-8000-00805f9b34fb");
    /* access modifiers changed from: private */
    public static final UUID a0 = UUID.fromString("00002A37-0000-1000-8000-00805f9b34fb");
    private static CL830Manager b0 = null;
    private int S = 0;
    /* access modifiers changed from: private */
    public BluetoothGattCharacteristic T;
    /* access modifiers changed from: private */
    public BluetoothGattCharacteristic U;
    /* access modifiers changed from: private */
    public final BodySensorLocationDataCallback V = new BodySensorLocationDataCallback() {
        public void a(BluetoothDevice bluetoothDevice, Data data) {
            CL830Manager cL830Manager = CL830Manager.this;
            cL830Manager.a(4, com.android.chileaf.fitness.common.b.a.a(data) + " received");
            super.a(bluetoothDevice, data);
        }

        public void a(BluetoothDevice bluetoothDevice, int i2) {
            ((h) CL830Manager.this.c).a(bluetoothDevice, i2);
        }
    };
    /* access modifiers changed from: private */
    public final HeartRateBeatMeasurementDataCallback W = new HeartRateBeatMeasurementDataCallback() {
        public void a(BluetoothDevice bluetoothDevice, Data data) {
            CL830Manager cL830Manager = CL830Manager.this;
            cL830Manager.a(4, c.a(data) + " received");
            super.a(bluetoothDevice, data);
        }

        public void a(BluetoothDevice bluetoothDevice, int i2, int i3, Boolean bool, Integer num, List<Integer> list) {
            ((h) CL830Manager.this.c).a(bluetoothDevice, i2, i3, bool, num, list);
        }

        /* access modifiers changed from: protected */
        public float a(int i2) {
            return CL830Manager.this.fetchBeat(i2);
        }
    };
    /* access modifiers changed from: private */
    public final BodySportDataCallback X = new BodySportDataCallback() {
        public void a(BluetoothDevice bluetoothDevice, Data data) {
            CL830Manager cL830Manager = CL830Manager.this;
            cL830Manager.a(6, "receive:" + com.android.chileaf.bluetooth.connect.h1.a.a(data.a()));
            super.a(bluetoothDevice, data);
        }

        public void a(BluetoothDevice bluetoothDevice, int i2, int i3, int i4) {
            ((h) CL830Manager.this.c).a(bluetoothDevice, i2, i3, i4);
        }
    };

    private final class a extends FitnessManager<h>.defpackage.a {
        private a() {
            super();
        }

        public /* synthetic */ void a(BluetoothDevice bluetoothDevice) {
            CL830Manager.this.a(4, "Body Sensor Location readCharacteristic success");
        }

        public /* synthetic */ void b(BluetoothDevice bluetoothDevice) {
            CL830Manager.this.a(4, "Heart Rate notifications enabled");
        }

        public /* synthetic */ void c(BluetoothDevice bluetoothDevice) {
            CL830Manager.this.a(4, "Rx notifications enabled");
        }

        /* access modifiers changed from: protected */
        public void e() {
            super.e();
            CL830Manager cL830Manager = CL830Manager.this;
            s0 f2 = cL830Manager.b(cL830Manager.U);
            f2.a((b) CL830Manager.this.V);
            f2.a((j) new f(this));
            f2.a((d) new a(this));
            f2.a();
            CL830Manager cL830Manager2 = CL830Manager.this;
            cL830Manager2.c(cL830Manager2.T).a((b) CL830Manager.this.W);
            CL830Manager cL830Manager3 = CL830Manager.this;
            e1 b = cL830Manager3.a(cL830Manager3.T);
            b.a((j) new b(this));
            b.a((d) new d(this));
            b.a();
            CL830Manager cL830Manager4 = CL830Manager.this;
            cL830Manager4.c(cL830Manager4.k).a((b) CL830Manager.this.X);
            CL830Manager cL830Manager5 = CL830Manager.this;
            e1 d = cL830Manager5.a(cL830Manager5.k);
            d.a((j) new c(this));
            d.a((d) new e(this));
            d.a();
        }

        /* access modifiers changed from: protected */
        public void h() {
            super.h();
            BluetoothGattCharacteristic unused = CL830Manager.this.U = null;
            BluetoothGattCharacteristic unused2 = CL830Manager.this.T = null;
        }

        /* access modifiers changed from: protected */
        public void i() {
            super.i();
            CL830Manager.this.C();
        }

        public /* synthetic */ void a(BluetoothDevice bluetoothDevice, int i2) {
            CL830Manager.this.a(5, "Body Sensor Location characteristic not found");
        }

        public /* synthetic */ void b(BluetoothDevice bluetoothDevice, int i2) {
            CL830Manager.this.a(5, "Heart Rate characteristic not found");
        }

        public /* synthetic */ void c(BluetoothDevice bluetoothDevice, int i2) {
            CL830Manager.this.a(5, "Rx characteristic not found");
        }

        /* access modifiers changed from: protected */
        public boolean b(BluetoothGatt bluetoothGatt) {
            super.b(bluetoothGatt);
            BluetoothGattService service = bluetoothGatt.getService(CL830Manager.Y);
            if (service != null) {
                BluetoothGattCharacteristic unused = CL830Manager.this.U = service.getCharacteristic(CL830Manager.Z);
            }
            return CL830Manager.this.U != null;
        }

        /* access modifiers changed from: protected */
        public boolean c(BluetoothGatt bluetoothGatt) {
            super.c(bluetoothGatt);
            BluetoothGattService service = bluetoothGatt.getService(CL830Manager.Y);
            if (service != null) {
                BluetoothGattCharacteristic unused = CL830Manager.this.T = service.getCharacteristic(CL830Manager.a0);
            }
            return CL830Manager.this.T != null;
        }
    }

    private CL830Manager(Context context) {
        super(context);
    }

    /* access modifiers changed from: private */
    public void C() {
        a(fetchPassCode());
    }

    public void b(int i2) {
        a(1, 0, new byte[]{15, 1, 1, (byte) i2});
    }

    public void c(boolean z) {
        a(1, 0, new byte[]{4, 1, 1, z ? (byte) 1 : 0});
    }

    /* access modifiers changed from: protected */
    public g0<h>.b d() {
        return new a();
    }

    public static synchronized CL830Manager a(Context context) {
        CL830Manager cL830Manager;
        synchronized (CL830Manager.class) {
            if (b0 == null) {
                b0 = new CL830Manager(context.getApplicationContext());
            }
            cL830Manager = b0;
        }
        return cL830Manager;
    }

    private void a(int i2, int i3, byte[] bArr) {
        int i4 = this.S;
        int length = bArr.length + 8;
        byte[] bArr2 = new byte[length];
        bArr2[0] = -1;
        bArr2[1] = (byte) (i3 & 255);
        bArr2[2] = (byte) length;
        bArr2[3] = (byte) ((i4 >> 8) & 255);
        bArr2[4] = (byte) (i4 & 255);
        bArr2[5] = (byte) (i2 & 255);
        bArr2[6] = 16;
        System.arraycopy(bArr, 0, bArr2, 7, bArr.length);
        bArr2[length - 1] = checkSum(bArr2);
        a(bArr2);
    }
}
