package com.chileaf.fitness.device.wear.cl831;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.content.Context;
import com.android.chileaf.bluetooth.connect.data.Data;
import com.android.chileaf.bluetooth.connect.e1;
import com.android.chileaf.bluetooth.connect.f1.j;
import com.android.chileaf.bluetooth.connect.g0;
import com.android.chileaf.bluetooth.connect.n0;
import com.android.chileaf.bluetooth.connect.s0;
import com.android.chileaf.fitness.FitnessManager;
import com.android.chileaf.fitness.common.heart.BodySensorLocationDataCallback;
import com.android.chileaf.fitness.common.heart.HeartRateMeasurementDataCallback;
import com.android.chileaf.fitness.common.heart.b;
import com.android.chileaf.fitness.model.HistoryOfHeartRate;
import com.android.chileaf.fitness.model.HistoryOfRecord;
import com.android.chileaf.fitness.x.c;
import com.android.chileaf.util.HexUtil;
import com.chileaf.fitness.device.wear.cl831.callback.CL831ReceivedDataCallback;
import com.chileaf.fitness.device.wear.cl831.callback.d;
import com.chileaf.fitness.device.wear.cl831.model.HistoryOfSport;
import com.chileaf.fitness.device.wear.cl831.model.IntervalStep;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CL831Manager extends FitnessManager<e> {
    /* access modifiers changed from: private */
    public static final UUID g0 = UUID.fromString("0000180D-0000-1000-8000-00805f9b34fb");
    /* access modifiers changed from: private */
    public static final UUID h0 = UUID.fromString("00002A38-0000-1000-8000-00805f9b34fb");
    /* access modifiers changed from: private */
    public static final UUID i0 = UUID.fromString("00002A37-0000-1000-8000-00805f9b34fb");
    private static CL831Manager j0 = null;
    /* access modifiers changed from: private */
    public BluetoothGattCharacteristic S;
    /* access modifiers changed from: private */
    public BluetoothGattCharacteristic T;
    /* access modifiers changed from: private */
    public List<c> U;
    /* access modifiers changed from: private */
    public List<com.android.chileaf.fitness.common.sport.a> V;
    /* access modifiers changed from: private */
    public List<b> W;
    /* access modifiers changed from: private */
    public List<com.chileaf.fitness.device.wear.cl831.callback.b> X;
    /* access modifiers changed from: private */
    public List<com.android.chileaf.fitness.x.b> Y;
    /* access modifiers changed from: private */
    public List<com.android.chileaf.fitness.x.a> Z;
    /* access modifiers changed from: private */
    public List<com.chileaf.fitness.device.wear.cl831.callback.c> a0;
    /* access modifiers changed from: private */
    public List<d> b0;
    /* access modifiers changed from: private */
    public com.chileaf.fitness.device.wear.cl831.callback.a c0;
    /* access modifiers changed from: private */
    public final BodySensorLocationDataCallback d0 = new BodySensorLocationDataCallback() {
        public void a(BluetoothDevice bluetoothDevice, int i2) {
            ((e) CL831Manager.this.c).a(bluetoothDevice, i2);
        }

        public void a(BluetoothDevice bluetoothDevice, Data data) {
            CL831Manager.this.a(3, String.format("%s received", new Object[]{com.android.chileaf.fitness.common.b.a.a(data)}));
            super.a(bluetoothDevice, data);
        }
    };
    /* access modifiers changed from: private */
    public final HeartRateMeasurementDataCallback e0 = new HeartRateMeasurementDataCallback() {
        public void a(BluetoothDevice bluetoothDevice, int i2, Boolean bool, Integer num, List<Integer> list) {
            ((e) CL831Manager.this.c).a(bluetoothDevice, i2, bool, num, list);
            if (CL831Manager.this.W != null && !CL831Manager.this.W.isEmpty()) {
                for (b a : CL831Manager.this.W) {
                    a.a(bluetoothDevice, i2, bool, num, list);
                }
            }
        }

        public void a(BluetoothDevice bluetoothDevice, Data data) {
            CL831Manager.this.a(3, "%s received", com.android.chileaf.fitness.common.b.c.a(data));
            super.a(bluetoothDevice, data);
        }
    };
    /* access modifiers changed from: private */
    public final CL831ReceivedDataCallback f0 = new CL831ReceivedDataCallback() {
        public void a(BluetoothDevice bluetoothDevice, int i2, int i3, int i4) {
            ((e) CL831Manager.this.c).a(bluetoothDevice, i2, i3, i4);
            if (CL831Manager.this.V != null && !CL831Manager.this.V.isEmpty()) {
                for (com.android.chileaf.fitness.common.sport.a a : CL831Manager.this.V) {
                    a.a(bluetoothDevice, i2, i3, i4);
                }
            }
        }

        public void b(BluetoothDevice bluetoothDevice, List<HistoryOfSport> list) {
            if (CL831Manager.this.X != null && !CL831Manager.this.X.isEmpty()) {
                for (com.chileaf.fitness.device.wear.cl831.callback.b b : CL831Manager.this.X) {
                    b.b(bluetoothDevice, list);
                }
            }
        }

        public void c(BluetoothDevice bluetoothDevice, List<HistoryOfHeartRate> list) {
            if (CL831Manager.this.Z != null && !CL831Manager.this.Z.isEmpty()) {
                for (com.android.chileaf.fitness.x.a c : CL831Manager.this.Z) {
                    c.c(bluetoothDevice, list);
                }
            }
        }

        public void e(BluetoothDevice bluetoothDevice, List<IntervalStep> list) {
            if (CL831Manager.this.a0 != null && !CL831Manager.this.a0.isEmpty()) {
                for (com.chileaf.fitness.device.wear.cl831.callback.c e : CL831Manager.this.a0) {
                    e.e(bluetoothDevice, list);
                }
            }
        }

        public void i(BluetoothDevice bluetoothDevice, List<HistoryOfRecord> list) {
            if (CL831Manager.this.b0 != null && !CL831Manager.this.b0.isEmpty()) {
                for (d i2 : CL831Manager.this.b0) {
                    i2.i(bluetoothDevice, list);
                }
            }
        }

        public void a(BluetoothDevice bluetoothDevice, boolean z) {
            ((e) CL831Manager.this.c).a(bluetoothDevice, z);
            if (CL831Manager.this.c0 != null) {
                CL831Manager.this.c0.a(bluetoothDevice, z);
            }
        }

        public void a(BluetoothDevice bluetoothDevice, int i2, int i3, int i4, int i5, long j2) {
            if (CL831Manager.this.U != null && !CL831Manager.this.U.isEmpty()) {
                for (c a : CL831Manager.this.U) {
                    a.a(bluetoothDevice, i2, i3, i4, i5, j2);
                }
            }
        }

        public void a(BluetoothDevice bluetoothDevice, List<HistoryOfRecord> list) {
            if (CL831Manager.this.Y != null && !CL831Manager.this.Y.isEmpty()) {
                for (com.android.chileaf.fitness.x.b a : CL831Manager.this.Y) {
                    a.a(bluetoothDevice, list);
                }
            }
        }
    };

    private final class a extends FitnessManager<e>.defpackage.a {
        private a() {
            super();
        }

        public /* synthetic */ void a(BluetoothDevice bluetoothDevice, int i2) {
            CL831Manager.this.a(5, "Body Sensor Location characteristic not found");
        }

        public /* synthetic */ void b(BluetoothDevice bluetoothDevice, int i2) {
            CL831Manager.this.a(5, "Rx characteristic not found");
        }

        public boolean c(BluetoothGatt bluetoothGatt) {
            super.c(bluetoothGatt);
            BluetoothGattService service = bluetoothGatt.getService(CL831Manager.g0);
            if (service != null) {
                BluetoothGattCharacteristic unused = CL831Manager.this.T = service.getCharacteristic(CL831Manager.i0);
            }
            return CL831Manager.this.T != null;
        }

        /* access modifiers changed from: protected */
        public void e() {
            super.e();
            CL831Manager cL831Manager = CL831Manager.this;
            s0 b = cL831Manager.b(cL831Manager.S);
            b.a((com.android.chileaf.bluetooth.connect.f1.b) CL831Manager.this.d0);
            b.a((com.android.chileaf.bluetooth.connect.f1.d) new a(this));
            b.a();
            CL831Manager cL831Manager2 = CL831Manager.this;
            cL831Manager2.c(cL831Manager2.T).a((com.android.chileaf.bluetooth.connect.f1.b) CL831Manager.this.e0);
            CL831Manager cL831Manager3 = CL831Manager.this;
            cL831Manager3.a(cL831Manager3.T).a();
            CL831Manager cL831Manager4 = CL831Manager.this;
            cL831Manager4.c(cL831Manager4.k).a((com.android.chileaf.bluetooth.connect.f1.b) CL831Manager.this.f0);
            CL831Manager cL831Manager5 = CL831Manager.this;
            e1 g2 = cL831Manager5.a(cL831Manager5.k);
            g2.a((j) new c(this));
            g2.a((com.android.chileaf.bluetooth.connect.f1.d) new b(this));
            g2.a();
        }

        /* access modifiers changed from: protected */
        public void h() {
            super.h();
            BluetoothGattCharacteristic unused = CL831Manager.this.S = null;
            BluetoothGattCharacteristic unused2 = CL831Manager.this.T = null;
        }

        /* access modifiers changed from: protected */
        public void i() {
            super.i();
            CL831Manager.this.E();
        }

        public /* synthetic */ void a(BluetoothDevice bluetoothDevice) {
            CL831Manager.this.a(3, "Rx notifications enabled");
        }

        /* access modifiers changed from: protected */
        public boolean b(BluetoothGatt bluetoothGatt) {
            super.b(bluetoothGatt);
            BluetoothGattService service = bluetoothGatt.getService(CL831Manager.g0);
            if (service != null) {
                BluetoothGattCharacteristic unused = CL831Manager.this.S = service.getCharacteristic(CL831Manager.h0);
            }
            return CL831Manager.this.S != null;
        }
    }

    private CL831Manager(Context context) {
        super(context);
    }

    public void A() {
        this.f0.a(4);
        a((byte) 33, 0);
    }

    public void B() {
        this.f0.a(2);
        a((byte) 22, 0);
    }

    public void C() {
        this.f0.a(8);
        a((byte) 64, 0);
    }

    public void D() {
        this.f0.a(16);
        a((byte) 66, 0);
    }

    public void E() {
        c(com.android.chileaf.util.a.a());
    }

    public static synchronized CL831Manager a(Context context) {
        CL831Manager cL831Manager;
        synchronized (CL831Manager.class) {
            if (j0 == null) {
                j0 = new CL831Manager(context.getApplicationContext());
            }
            cL831Manager = j0;
        }
        return cL831Manager;
    }

    public void b(long j2) {
        this.f0.a(6);
        a((byte) 34, HexUtil.a(1, a(j2)));
    }

    public void c(long j2) {
        a((byte) 8, a(j2));
    }

    /* access modifiers changed from: protected */
    public g0<e>.b d() {
        return new a();
    }

    public void a(String str, boolean z) {
        this.f0.a(str.equalsIgnoreCase("CL831") && z);
        a(6, String.format("checkModel modelName:%s isCL833:%s", new Object[]{str, Boolean.valueOf(z)}));
    }

    public void a(com.chileaf.fitness.device.wear.cl831.callback.b bVar) {
        if (this.X == null) {
            this.X = new ArrayList();
        }
        this.X.add(bVar);
    }

    public void a(com.android.chileaf.fitness.x.b bVar) {
        if (this.Y == null) {
            this.Y = new ArrayList();
        }
        this.Y.add(bVar);
    }

    public void a(com.android.chileaf.fitness.x.a aVar) {
        if (this.Z == null) {
            this.Z = new ArrayList();
        }
        this.Z.add(aVar);
    }

    public void a(com.chileaf.fitness.device.wear.cl831.callback.c cVar) {
        if (this.a0 == null) {
            this.a0 = new ArrayList();
        }
        this.a0.add(cVar);
    }

    public void a(d dVar) {
        if (this.b0 == null) {
            this.b0 = new ArrayList();
        }
        this.b0.add(dVar);
    }

    public void a(BluetoothDevice bluetoothDevice, boolean z) {
        n0 a2 = a(bluetoothDevice);
        a2.a(z);
        a2.a();
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
