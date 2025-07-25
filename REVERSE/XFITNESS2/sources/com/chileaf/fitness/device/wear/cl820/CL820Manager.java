package com.chileaf.fitness.device.wear.cl820;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.content.Context;
import com.android.chileaf.bluetooth.connect.data.Data;
import com.android.chileaf.bluetooth.connect.e1;
import com.android.chileaf.bluetooth.connect.f1.j;
import com.android.chileaf.bluetooth.connect.g0;
import com.android.chileaf.bluetooth.connect.s0;
import com.android.chileaf.fitness.FitnessManager;
import com.android.chileaf.fitness.common.heart.BodySensorLocationDataCallback;
import com.android.chileaf.fitness.model.HistoryOfHeartRate;
import com.android.chileaf.fitness.model.HistoryOfRecord;
import com.android.chileaf.util.HexUtil;
import com.chileaf.fitness.device.wear.cl820.callback.CL820ReceivedDataCallback;
import com.chileaf.fitness.device.wear.cl820.callback.b;
import com.chileaf.fitness.device.wear.cl820.callback.c;
import com.chileaf.fitness.device.wear.cl820.callback.d;
import com.chileaf.fitness.device.wear.cl820.callback.e;
import com.chileaf.fitness.device.wear.cl820.callback.f;
import com.chileaf.fitness.device.wear.cl820.heart.HeartRateVariabilityMeasurementDataCallback;
import com.chileaf.fitness.device.wear.cl820.model.HistoryOfRespiratoryRate;
import com.chileaf.fitness.device.wear.cl820.model.HistoryOfSleep;
import com.chileaf.fitness.device.wear.cl820.model.HistoryOfSport;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CL820Manager extends FitnessManager<h> {
    /* access modifiers changed from: private */
    public static final UUID e0 = UUID.fromString("0000180D-0000-1000-8000-00805f9b34fb");
    /* access modifiers changed from: private */
    public static final UUID f0 = UUID.fromString("00002A38-0000-1000-8000-00805f9b34fb");
    /* access modifiers changed from: private */
    public static final UUID g0 = UUID.fromString("00002A37-0000-1000-8000-00805f9b34fb");
    private static CL820Manager h0 = null;
    /* access modifiers changed from: private */
    public BluetoothGattCharacteristic S;
    /* access modifiers changed from: private */
    public BluetoothGattCharacteristic T;
    /* access modifiers changed from: private */
    public List<f> U;
    /* access modifiers changed from: private */
    public List<b> V;
    /* access modifiers changed from: private */
    public List<com.chileaf.fitness.device.wear.cl820.callback.a> W;
    /* access modifiers changed from: private */
    public List<d> X;
    /* access modifiers changed from: private */
    public List<c> Y;
    /* access modifiers changed from: private */
    public List<e> Z;
    /* access modifiers changed from: private */
    public com.android.chileaf.fitness.common.heart.c a0;
    /* access modifiers changed from: private */
    public final BodySensorLocationDataCallback b0 = new BodySensorLocationDataCallback() {
        public void a(BluetoothDevice bluetoothDevice, int i2) {
            ((h) CL820Manager.this.c).a(bluetoothDevice, i2);
        }

        public void a(BluetoothDevice bluetoothDevice, Data data) {
            CL820Manager.this.a(3, String.format("%s received", new Object[]{com.android.chileaf.fitness.common.b.a.a(data)}));
            super.a(bluetoothDevice, data);
        }
    };
    /* access modifiers changed from: private */
    public final HeartRateVariabilityMeasurementDataCallback c0 = new HeartRateVariabilityMeasurementDataCallback() {
        public void a(BluetoothDevice bluetoothDevice, int i2, float f2) {
            ((h) CL820Manager.this.c).a(bluetoothDevice, i2, f2);
            if (CL820Manager.this.a0 != null) {
                CL820Manager.this.a0.a(bluetoothDevice, i2, f2);
            }
        }

        public void a(BluetoothDevice bluetoothDevice, int i2, Boolean bool, Integer num, List<Integer> list) {
            ((h) CL820Manager.this.c).a(bluetoothDevice, i2, bool, num, list);
        }

        public void a(BluetoothDevice bluetoothDevice, Data data) {
            CL820Manager.this.a(3, String.format("%s received", new Object[]{com.android.chileaf.fitness.common.b.c.a(data)}));
            super.a(bluetoothDevice, data);
        }
    };
    /* access modifiers changed from: private */
    public final CL820ReceivedDataCallback d0 = new CL820ReceivedDataCallback() {
        public void a(BluetoothDevice bluetoothDevice, List<HistoryOfRecord> list) {
            if (CL820Manager.this.V != null && !CL820Manager.this.V.isEmpty()) {
                for (b a : CL820Manager.this.V) {
                    a.a(bluetoothDevice, list);
                }
            }
        }

        public void b(BluetoothDevice bluetoothDevice, List<HistoryOfSport> list) {
            if (CL820Manager.this.U != null && !CL820Manager.this.U.isEmpty()) {
                for (f b : CL820Manager.this.U) {
                    b.b(bluetoothDevice, list);
                }
            }
        }

        public void c(BluetoothDevice bluetoothDevice, List<HistoryOfHeartRate> list) {
            if (CL820Manager.this.W != null && !CL820Manager.this.W.isEmpty()) {
                for (com.chileaf.fitness.device.wear.cl820.callback.a c : CL820Manager.this.W) {
                    c.c(bluetoothDevice, list);
                }
            }
        }

        public void g(BluetoothDevice bluetoothDevice, List<HistoryOfRespiratoryRate> list) {
            if (CL820Manager.this.Y != null && !CL820Manager.this.Y.isEmpty()) {
                for (c g2 : CL820Manager.this.Y) {
                    g2.g(bluetoothDevice, list);
                }
            }
        }

        public void j(BluetoothDevice bluetoothDevice, List<HistoryOfSleep> list) {
            if (CL820Manager.this.Z != null && !CL820Manager.this.Z.isEmpty()) {
                for (e j2 : CL820Manager.this.Z) {
                    j2.j(bluetoothDevice, list);
                }
            }
        }

        public void k(BluetoothDevice bluetoothDevice, List<HistoryOfRecord> list) {
            if (CL820Manager.this.X != null && !CL820Manager.this.X.isEmpty()) {
                for (d k : CL820Manager.this.X) {
                    k.k(bluetoothDevice, list);
                }
            }
        }

        public void a(BluetoothDevice bluetoothDevice, int i2, int i3, int i4) {
            ((h) CL820Manager.this.c).a(bluetoothDevice, i2, i3, i4);
        }

        public void a(BluetoothDevice bluetoothDevice, Data data) {
            CL820Manager.this.a(6, String.format("onDataReceived:%s", new Object[]{com.android.chileaf.bluetooth.connect.h1.a.a(data.a())}));
            super.a(bluetoothDevice, data);
        }
    };

    private final class a extends FitnessManager<h>.defpackage.a {
        private a() {
            super();
        }

        public /* synthetic */ void a(BluetoothDevice bluetoothDevice) {
            CL820Manager.this.a(4, "Body Sensor Location readCharacteristic success");
        }

        public /* synthetic */ void b(BluetoothDevice bluetoothDevice) {
            CL820Manager.this.a(4, "Heart Rate notifications enabled");
        }

        public /* synthetic */ void c(BluetoothDevice bluetoothDevice) {
            CL820Manager.this.a(4, "Rx notifications enabled");
        }

        /* access modifiers changed from: protected */
        public void e() {
            super.e();
            CL820Manager cL820Manager = CL820Manager.this;
            s0 b = cL820Manager.b(cL820Manager.S);
            b.a((com.android.chileaf.bluetooth.connect.f1.b) CL820Manager.this.b0);
            b.a((j) new c(this));
            b.a((com.android.chileaf.bluetooth.connect.f1.d) new e(this));
            b.a();
            CL820Manager cL820Manager2 = CL820Manager.this;
            cL820Manager2.c(cL820Manager2.T).a((com.android.chileaf.bluetooth.connect.f1.b) CL820Manager.this.c0);
            CL820Manager cL820Manager3 = CL820Manager.this;
            e1 e = cL820Manager3.a(cL820Manager3.T);
            e.a((j) new d(this));
            e.a((com.android.chileaf.bluetooth.connect.f1.d) new f(this));
            e.a();
            CL820Manager cL820Manager4 = CL820Manager.this;
            cL820Manager4.c(cL820Manager4.k).a((com.android.chileaf.bluetooth.connect.f1.b) CL820Manager.this.d0);
            CL820Manager cL820Manager5 = CL820Manager.this;
            e1 g2 = cL820Manager5.a(cL820Manager5.k);
            g2.a((j) new a(this));
            g2.a((com.android.chileaf.bluetooth.connect.f1.d) new b(this));
            g2.a();
        }

        /* access modifiers changed from: protected */
        public void h() {
            super.h();
            BluetoothGattCharacteristic unused = CL820Manager.this.S = null;
            BluetoothGattCharacteristic unused2 = CL820Manager.this.T = null;
        }

        /* access modifiers changed from: protected */
        public void i() {
            super.i();
            CL820Manager.this.E();
        }

        public /* synthetic */ void a(BluetoothDevice bluetoothDevice, int i2) {
            CL820Manager.this.a(5, "Body Sensor Location characteristic not found");
        }

        public /* synthetic */ void b(BluetoothDevice bluetoothDevice, int i2) {
            CL820Manager.this.a(5, "Heart Rate characteristic not found");
        }

        public /* synthetic */ void c(BluetoothDevice bluetoothDevice, int i2) {
            CL820Manager.this.a(5, "Rx characteristic not found");
        }

        /* access modifiers changed from: protected */
        public boolean b(BluetoothGatt bluetoothGatt) {
            super.b(bluetoothGatt);
            BluetoothGattService service = bluetoothGatt.getService(CL820Manager.e0);
            if (service != null) {
                BluetoothGattCharacteristic unused = CL820Manager.this.S = service.getCharacteristic(CL820Manager.f0);
            }
            return CL820Manager.this.S != null;
        }

        public boolean c(BluetoothGatt bluetoothGatt) {
            super.c(bluetoothGatt);
            BluetoothGattService service = bluetoothGatt.getService(CL820Manager.e0);
            if (service != null) {
                BluetoothGattCharacteristic unused = CL820Manager.this.T = service.getCharacteristic(CL820Manager.g0);
            }
            return CL820Manager.this.T != null;
        }
    }

    private CL820Manager(Context context) {
        super(context);
    }

    public void A() {
        a((byte) 33);
    }

    public void B() {
        a((byte) 36);
    }

    public void C() {
        a((byte) 49);
    }

    public void D() {
        a((byte) 22);
    }

    public void E() {
        d(com.android.chileaf.util.a.a());
    }

    public static synchronized CL820Manager a(Context context) {
        CL820Manager cL820Manager;
        synchronized (CL820Manager.class) {
            if (h0 == null) {
                h0 = new CL820Manager(context.getApplicationContext());
            }
            cL820Manager = h0;
        }
        return cL820Manager;
    }

    public void b(long j2) {
        a((byte) 34, HexUtil.a(1, a(j2)));
    }

    public void c(long j2) {
        a((byte) 37, HexUtil.a(1, a(j2)));
    }

    /* access modifiers changed from: protected */
    public g0<h>.b d() {
        return new a();
    }

    public void d(long j2) {
        a((byte) 8, a(j2));
    }

    public void a(f fVar) {
        if (this.U == null) {
            this.U = new ArrayList();
        }
        this.U.add(fVar);
    }

    public void a(b bVar) {
        if (this.V == null) {
            this.V = new ArrayList();
        }
        this.V.add(bVar);
    }

    public void a(com.chileaf.fitness.device.wear.cl820.callback.a aVar) {
        if (this.W == null) {
            this.W = new ArrayList();
        }
        this.W.add(aVar);
    }

    public void a(d dVar) {
        if (this.X == null) {
            this.X = new ArrayList();
        }
        this.X.add(dVar);
    }

    public void a(c cVar) {
        if (this.Y == null) {
            this.Y = new ArrayList();
        }
        this.Y.add(cVar);
    }

    public void a(e eVar) {
        if (this.Z == null) {
            this.Z = new ArrayList();
        }
        this.Z.add(eVar);
    }

    private void a(byte b) {
        a(b, 0);
    }

    private void a(byte b, int... iArr) {
        byte[] bArr;
        if (iArr != null) {
            bArr = HexUtil.a(HexUtil.a(255, iArr.length + 4, b), HexUtil.a(iArr));
        } else {
            bArr = HexUtil.a(255, 4, b);
        }
        a(HexUtil.a(bArr, checkSum(bArr)));
    }
}
