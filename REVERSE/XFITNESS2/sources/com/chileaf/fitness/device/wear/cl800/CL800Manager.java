package com.chileaf.fitness.device.wear.cl800;

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
import com.android.chileaf.fitness.common.heart.HeartRateMeasurementDataCallback;
import com.android.chileaf.fitness.common.sport.BodySportDataCallback;
import java.util.List;
import java.util.UUID;

public class CL800Manager extends FitnessManager<h> {
    /* access modifiers changed from: private */
    public static final UUID X = UUID.fromString("0000180D-0000-1000-8000-00805f9b34fb");
    /* access modifiers changed from: private */
    public static final UUID Y = UUID.fromString("00002A38-0000-1000-8000-00805f9b34fb");
    /* access modifiers changed from: private */
    public static final UUID Z = UUID.fromString("00002A37-0000-1000-8000-00805f9b34fb");
    private static CL800Manager a0 = null;
    /* access modifiers changed from: private */
    public BluetoothGattCharacteristic S;
    /* access modifiers changed from: private */
    public BluetoothGattCharacteristic T;
    /* access modifiers changed from: private */
    public final BodySensorLocationDataCallback U = new BodySensorLocationDataCallback() {
        public void a(BluetoothDevice bluetoothDevice, Data data) {
            CL800Manager cL800Manager = CL800Manager.this;
            cL800Manager.a(4, com.android.chileaf.fitness.common.b.a.a(data) + " received");
            super.a(bluetoothDevice, data);
        }

        public void a(BluetoothDevice bluetoothDevice, int i2) {
            ((h) CL800Manager.this.c).a(bluetoothDevice, i2);
        }
    };
    /* access modifiers changed from: private */
    public final HeartRateMeasurementDataCallback V = new HeartRateMeasurementDataCallback() {
        public void a(BluetoothDevice bluetoothDevice, Data data) {
            CL800Manager cL800Manager = CL800Manager.this;
            cL800Manager.a(4, c.a(data) + " received");
            super.a(bluetoothDevice, data);
        }

        public void a(BluetoothDevice bluetoothDevice, int i2, Boolean bool, Integer num, List<Integer> list) {
            ((h) CL800Manager.this.c).a(bluetoothDevice, i2, bool, num, list);
        }
    };
    /* access modifiers changed from: private */
    public final BodySportDataCallback W = new BodySportDataCallback() {
        public void a(BluetoothDevice bluetoothDevice, Data data) {
            CL800Manager cL800Manager = CL800Manager.this;
            cL800Manager.a(4, "receive:" + com.android.chileaf.bluetooth.connect.h1.a.a(data.a()));
            super.a(bluetoothDevice, data);
        }

        public void a(BluetoothDevice bluetoothDevice, int i2, int i3, int i4) {
            ((h) CL800Manager.this.c).a(bluetoothDevice, i2, i3, i4);
        }
    };

    private final class a extends FitnessManager<h>.defpackage.a {
        private a() {
            super();
        }

        public /* synthetic */ void a(BluetoothDevice bluetoothDevice) {
            CL800Manager.this.a(4, "Body Sensor Location readCharacteristic success");
        }

        public /* synthetic */ void b(BluetoothDevice bluetoothDevice) {
            CL800Manager.this.a(4, "Heart Rate notifications enabled");
        }

        public /* synthetic */ void c(BluetoothDevice bluetoothDevice) {
            CL800Manager.this.a(4, "Rx notifications enabled");
        }

        /* access modifiers changed from: protected */
        public void e() {
            super.e();
            CL800Manager cL800Manager = CL800Manager.this;
            s0 e = cL800Manager.b(cL800Manager.T);
            e.a((b) CL800Manager.this.U);
            e.a((j) new b(this));
            e.a((d) new d(this));
            e.a();
            CL800Manager cL800Manager2 = CL800Manager.this;
            cL800Manager2.c(cL800Manager2.S).a((b) CL800Manager.this.V);
            CL800Manager cL800Manager3 = CL800Manager.this;
            e1 a = cL800Manager3.a(cL800Manager3.S);
            a.a((j) new a(this));
            a.a((d) new e(this));
            a.a();
            CL800Manager cL800Manager4 = CL800Manager.this;
            cL800Manager4.c(cL800Manager4.k).a((b) CL800Manager.this.W);
            CL800Manager cL800Manager5 = CL800Manager.this;
            e1 c = cL800Manager5.a(cL800Manager5.k);
            c.a((j) new f(this));
            c.a((d) new c(this));
            c.a();
        }

        /* access modifiers changed from: protected */
        public void h() {
            super.h();
            BluetoothGattCharacteristic unused = CL800Manager.this.T = null;
            BluetoothGattCharacteristic unused2 = CL800Manager.this.S = null;
        }

        public /* synthetic */ void a(BluetoothDevice bluetoothDevice, int i2) {
            CL800Manager.this.a(5, "Body Sensor Location characteristic not found");
        }

        public /* synthetic */ void b(BluetoothDevice bluetoothDevice, int i2) {
            CL800Manager.this.a(5, "Heart Rate characteristic not found");
        }

        public /* synthetic */ void c(BluetoothDevice bluetoothDevice, int i2) {
            CL800Manager.this.a(5, "Rx characteristic not found");
        }

        /* access modifiers changed from: protected */
        public boolean b(BluetoothGatt bluetoothGatt) {
            super.b(bluetoothGatt);
            BluetoothGattService service = bluetoothGatt.getService(CL800Manager.X);
            if (service != null) {
                BluetoothGattCharacteristic unused = CL800Manager.this.T = service.getCharacteristic(CL800Manager.Y);
            }
            return CL800Manager.this.T != null;
        }

        /* access modifiers changed from: protected */
        public boolean c(BluetoothGatt bluetoothGatt) {
            super.c(bluetoothGatt);
            BluetoothGattService service = bluetoothGatt.getService(CL800Manager.X);
            if (service != null) {
                BluetoothGattCharacteristic unused = CL800Manager.this.S = service.getCharacteristic(CL800Manager.Z);
            }
            return CL800Manager.this.S != null;
        }
    }

    private CL800Manager(Context context) {
        super(context);
    }

    public static synchronized CL800Manager a(Context context) {
        CL800Manager cL800Manager;
        synchronized (CL800Manager.class) {
            if (a0 == null) {
                a0 = new CL800Manager(context.getApplicationContext());
            }
            cL800Manager = a0;
        }
        return cL800Manager;
    }

    /* access modifiers changed from: protected */
    public g0<h>.b d() {
        return new a();
    }
}
