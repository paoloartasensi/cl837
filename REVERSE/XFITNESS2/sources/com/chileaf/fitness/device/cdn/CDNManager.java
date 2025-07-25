package com.chileaf.fitness.device.cdn;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import com.android.chileaf.bluetooth.connect.data.Data;
import com.android.chileaf.bluetooth.connect.e1;
import com.android.chileaf.bluetooth.connect.f1.d;
import com.android.chileaf.bluetooth.connect.f1.j;
import com.android.chileaf.bluetooth.connect.g0;
import com.android.chileaf.fitness.FitnessManager;
import com.android.chileaf.fitness.common.b.b;
import com.android.chileaf.fitness.common.csc.CyclingSpeedAndCadenceMeasurementDataCallback;
import java.util.UUID;

public class CDNManager extends FitnessManager<c> {
    /* access modifiers changed from: private */
    public static final UUID V = UUID.fromString("00001816-0000-1000-8000-00805f9b34fb");
    /* access modifiers changed from: private */
    public static final UUID W = UUID.fromString("00002A5B-0000-1000-8000-00805f9b34fb");
    private static CDNManager X = null;
    /* access modifiers changed from: private */
    public final SharedPreferences S;
    /* access modifiers changed from: private */
    public BluetoothGattCharacteristic T;
    /* access modifiers changed from: private */
    public final CyclingSpeedAndCadenceMeasurementDataCallback U = new CyclingSpeedAndCadenceMeasurementDataCallback() {
        public float a() {
            return (float) Integer.parseInt(CDNManager.this.S.getString("settings_wheel_size", String.valueOf(2340)));
        }

        public void b(BluetoothDevice bluetoothDevice, Data data) {
            CDNManager cDNManager = CDNManager.this;
            cDNManager.a(5, "Invalid CSC Measurement data received: " + data);
        }

        public void a(BluetoothDevice bluetoothDevice, float f2, float f3, float f4) {
            ((c) CDNManager.this.c).a(bluetoothDevice, f2, f3, f4);
        }

        public void a(BluetoothDevice bluetoothDevice, float f2, float f3) {
            ((c) CDNManager.this.c).a(bluetoothDevice, f2, f3);
        }

        public void a(BluetoothDevice bluetoothDevice, Data data) {
            CDNManager cDNManager = CDNManager.this;
            cDNManager.a(4, b.a(data) + " received");
            super.a(bluetoothDevice, data);
        }
    };

    private final class a extends FitnessManager<c>.defpackage.a {
        private a() {
            super();
        }

        public /* synthetic */ void a(BluetoothDevice bluetoothDevice) {
            CDNManager.this.a(4, "Rx notifications enabled");
        }

        public boolean c(BluetoothGatt bluetoothGatt) {
            super.c(bluetoothGatt);
            BluetoothGattService service = bluetoothGatt.getService(CDNManager.V);
            if (service != null) {
                BluetoothGattCharacteristic unused = CDNManager.this.T = service.getCharacteristic(CDNManager.W);
            }
            return CDNManager.this.T != null;
        }

        /* access modifiers changed from: protected */
        public void e() {
            super.e();
            CDNManager cDNManager = CDNManager.this;
            cDNManager.c(cDNManager.T).a((com.android.chileaf.bluetooth.connect.f1.b) CDNManager.this.U);
            CDNManager cDNManager2 = CDNManager.this;
            e1 c = cDNManager2.a(cDNManager2.T);
            c.a((j) new a(this));
            c.a((d) new b(this));
            c.a();
        }

        /* access modifiers changed from: protected */
        public void h() {
            super.h();
            BluetoothGattCharacteristic unused = CDNManager.this.T = null;
        }

        public /* synthetic */ void a(BluetoothDevice bluetoothDevice, int i2) {
            CDNManager.this.a(5, "Rx characteristic not found");
        }
    }

    private CDNManager(Context context) {
        super(context);
        this.S = PreferenceManager.getDefaultSharedPreferences(context);
    }

    /* access modifiers changed from: protected */
    public g0<c>.b d() {
        return new a();
    }

    public static synchronized CDNManager a(Context context) {
        CDNManager cDNManager;
        synchronized (CDNManager.class) {
            if (X == null) {
                X = new CDNManager(context.getApplicationContext());
            }
            cDNManager = X;
        }
        return cDNManager;
    }
}
