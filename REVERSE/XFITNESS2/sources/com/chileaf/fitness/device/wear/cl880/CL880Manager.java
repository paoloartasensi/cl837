package com.chileaf.fitness.device.wear.cl880;

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
import com.android.chileaf.fitness.common.b.c;
import com.android.chileaf.fitness.common.heart.BodySensorLocationDataCallback;
import com.android.chileaf.fitness.common.heart.HeartRateMeasurementDataCallback;
import com.android.chileaf.util.HexUtil;
import com.chileaf.fitness.device.wear.cl880.callback.CL880ReceivedDataCallback;
import com.chileaf.fitness.device.wear.cl880.callback.d;
import com.chileaf.fitness.device.wear.cl880.callback.e;
import com.chileaf.fitness.device.wear.cl880.callback.f;
import com.chileaf.fitness.device.wear.cl880.model.AlarmConfig;
import com.chileaf.fitness.device.wear.cl880.model.DrinkConfig;
import com.chileaf.fitness.device.wear.cl880.model.InactivityConfig;
import com.chileaf.fitness.device.wear.cl880.model.MessageConfig;
import com.chileaf.fitness.device.wear.cl880.model.SleepConfig;
import com.chileaf.fitness.device.wear.cl880.model.SleepHistory;
import com.chileaf.fitness.device.wear.cl880.model.SportDayHistory;
import com.chileaf.fitness.device.wear.cl880.model.SportHistory;
import com.jeremyliao.liveeventbus.BuildConfig;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

public class CL880Manager extends FitnessManager<h> {
    private static final UUID b0 = UUID.fromString("0000FD01-0000-1000-8000-00805F9B34FB");
    private static final UUID c0 = UUID.fromString("00008F01-0000-1000-8000-00805F9B34FB");
    private static final UUID d0 = UUID.fromString("00008F02-0000-1000-8000-00805F9B34FB");
    /* access modifiers changed from: private */
    public static final UUID e0 = UUID.fromString("0000180D-0000-1000-8000-00805f9b34fb");
    /* access modifiers changed from: private */
    public static final UUID f0 = UUID.fromString("00002A38-0000-1000-8000-00805f9b34fb");
    /* access modifiers changed from: private */
    public static final UUID g0 = UUID.fromString("00002A37-0000-1000-8000-00805f9b34fb");
    private static CL880Manager h0 = null;
    /* access modifiers changed from: private */
    public int S = 0;
    /* access modifiers changed from: private */
    public BluetoothGattCharacteristic T;
    /* access modifiers changed from: private */
    public BluetoothGattCharacteristic U;
    /* access modifiers changed from: private */
    public List<e> V;
    /* access modifiers changed from: private */
    public List<d> W;
    /* access modifiers changed from: private */
    public List<f> X;
    /* access modifiers changed from: private */
    public final BodySensorLocationDataCallback Y = new BodySensorLocationDataCallback() {
        public void a(BluetoothDevice bluetoothDevice, int i2) {
            ((h) CL880Manager.this.c).a(bluetoothDevice, i2);
        }

        public void a(BluetoothDevice bluetoothDevice, Data data) {
            CL880Manager.this.a(3, "%s received", com.android.chileaf.fitness.common.b.a.a(data));
            super.a(bluetoothDevice, data);
        }
    };
    /* access modifiers changed from: private */
    public final HeartRateMeasurementDataCallback Z = new HeartRateMeasurementDataCallback() {
        public void a(BluetoothDevice bluetoothDevice, int i2, Boolean bool, Integer num, List<Integer> list) {
            ((h) CL880Manager.this.c).a(bluetoothDevice, i2, bool, num, list);
        }

        public void a(BluetoothDevice bluetoothDevice, Data data) {
            CL880Manager.this.a(3, "%s received", c.a(data));
            super.a(bluetoothDevice, data);
        }
    };
    /* access modifiers changed from: private */
    public final CL880ReceivedDataCallback a0 = new CL880ReceivedDataCallback() {
        public void a(BluetoothDevice bluetoothDevice, byte[] bArr, int i2) {
            CL880Manager.this.a(3, "onPlayMusicReceived action:%s", Integer.valueOf(i2));
            CL880Manager.this.c(bArr);
        }

        public void b(BluetoothDevice bluetoothDevice, byte[] bArr, int i2) {
            CL880Manager.this.a(3, "onTakePhotoReceived count:%s", Integer.valueOf(i2));
            CL880Manager.this.c(bArr);
        }

        public void d(BluetoothDevice bluetoothDevice, List<SportHistory> list) {
            if (CL880Manager.this.V != null && !CL880Manager.this.V.isEmpty()) {
                for (e d : CL880Manager.this.V) {
                    d.d(bluetoothDevice, list);
                }
            }
        }

        public void f(BluetoothDevice bluetoothDevice, List<SportDayHistory> list) {
            if (CL880Manager.this.X != null && !CL880Manager.this.X.isEmpty()) {
                for (f f2 : CL880Manager.this.X) {
                    f2.f(bluetoothDevice, list);
                }
            }
        }

        public void h(BluetoothDevice bluetoothDevice, List<SleepHistory> list) {
            if (CL880Manager.this.W != null && !CL880Manager.this.W.isEmpty()) {
                for (d h2 : CL880Manager.this.W) {
                    h2.h(bluetoothDevice, list);
                }
            }
        }

        public void a(BluetoothDevice bluetoothDevice, byte[] bArr, boolean z) {
            CL880Manager.this.a(3, "onResponseReceived initial:%s ", Boolean.valueOf(z));
            CL880Manager.this.c(bArr);
            if (z) {
                CL880Manager.this.E();
            }
        }

        public void b(BluetoothDevice bluetoothDevice, byte[] bArr, boolean z) {
            CL880Manager.this.a(3, "onRingtoneReceived open:%s", Boolean.valueOf(z));
            CL880Manager.this.c(bArr);
        }

        public void a(BluetoothDevice bluetoothDevice, int i2, int i3, int i4) {
            ((h) CL880Manager.this.c).a(bluetoothDevice, i2, i3, i4);
        }
    };

    static /* synthetic */ class a {
        static final /* synthetic */ int[] a;

        /* JADX WARNING: Can't wrap try/catch for region: R(18:0|1|2|3|4|5|6|7|8|9|10|11|12|13|14|15|16|(3:17|18|20)) */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:11:0x003e */
        /* JADX WARNING: Missing exception handler attribute for start block: B:13:0x0049 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:15:0x0054 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:17:0x0060 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0012 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x001d */
        /* JADX WARNING: Missing exception handler attribute for start block: B:7:0x0028 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:9:0x0033 */
        static {
            /*
                com.chileaf.fitness.device.wear.cl880.external.NotificationType[] r0 = com.chileaf.fitness.device.wear.cl880.external.NotificationType.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                a = r0
                com.chileaf.fitness.device.wear.cl880.external.NotificationType r1 = com.chileaf.fitness.device.wear.cl880.external.NotificationType.MISSEDCALL     // Catch:{ NoSuchFieldError -> 0x0012 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0012 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0012 }
            L_0x0012:
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x001d }
                com.chileaf.fitness.device.wear.cl880.external.NotificationType r1 = com.chileaf.fitness.device.wear.cl880.external.NotificationType.EMAIL     // Catch:{ NoSuchFieldError -> 0x001d }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001d }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001d }
            L_0x001d:
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x0028 }
                com.chileaf.fitness.device.wear.cl880.external.NotificationType r1 = com.chileaf.fitness.device.wear.cl880.external.NotificationType.SMS     // Catch:{ NoSuchFieldError -> 0x0028 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0028 }
                r2 = 3
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0028 }
            L_0x0028:
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x0033 }
                com.chileaf.fitness.device.wear.cl880.external.NotificationType r1 = com.chileaf.fitness.device.wear.cl880.external.NotificationType.WECHAT     // Catch:{ NoSuchFieldError -> 0x0033 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0033 }
                r2 = 4
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0033 }
            L_0x0033:
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x003e }
                com.chileaf.fitness.device.wear.cl880.external.NotificationType r1 = com.chileaf.fitness.device.wear.cl880.external.NotificationType.QQ     // Catch:{ NoSuchFieldError -> 0x003e }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x003e }
                r2 = 5
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x003e }
            L_0x003e:
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x0049 }
                com.chileaf.fitness.device.wear.cl880.external.NotificationType r1 = com.chileaf.fitness.device.wear.cl880.external.NotificationType.SKYPE     // Catch:{ NoSuchFieldError -> 0x0049 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0049 }
                r2 = 6
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0049 }
            L_0x0049:
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x0054 }
                com.chileaf.fitness.device.wear.cl880.external.NotificationType r1 = com.chileaf.fitness.device.wear.cl880.external.NotificationType.WHATSAPP     // Catch:{ NoSuchFieldError -> 0x0054 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0054 }
                r2 = 7
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0054 }
            L_0x0054:
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x0060 }
                com.chileaf.fitness.device.wear.cl880.external.NotificationType r1 = com.chileaf.fitness.device.wear.cl880.external.NotificationType.FACEBOOK     // Catch:{ NoSuchFieldError -> 0x0060 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0060 }
                r2 = 8
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0060 }
            L_0x0060:
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x006c }
                com.chileaf.fitness.device.wear.cl880.external.NotificationType r1 = com.chileaf.fitness.device.wear.cl880.external.NotificationType.OTHER     // Catch:{ NoSuchFieldError -> 0x006c }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x006c }
                r2 = 9
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x006c }
            L_0x006c:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.chileaf.fitness.device.wear.cl880.CL880Manager.a.<clinit>():void");
        }
    }

    private final class b extends FitnessManager<h>.defpackage.a {
        private b() {
            super();
        }

        public /* synthetic */ void a(BluetoothDevice bluetoothDevice, int i2) {
            CL880Manager.this.a(5, "Body Sensor Location characteristic not found");
        }

        public /* synthetic */ void b(BluetoothDevice bluetoothDevice, int i2) {
            CL880Manager.this.a(5, "Rx characteristic not found");
        }

        public boolean c(BluetoothGatt bluetoothGatt) {
            super.c(bluetoothGatt);
            BluetoothGattService service = bluetoothGatt.getService(CL880Manager.e0);
            if (service != null) {
                BluetoothGattCharacteristic unused = CL880Manager.this.U = service.getCharacteristic(CL880Manager.g0);
            }
            return CL880Manager.this.U != null;
        }

        /* access modifiers changed from: protected */
        public void e() {
            super.e();
            CL880Manager cL880Manager = CL880Manager.this;
            s0 b = cL880Manager.b(cL880Manager.T);
            b.a((com.android.chileaf.bluetooth.connect.f1.b) CL880Manager.this.Y);
            b.a((com.android.chileaf.bluetooth.connect.f1.d) new c(this));
            b.a();
            CL880Manager cL880Manager2 = CL880Manager.this;
            cL880Manager2.c(cL880Manager2.U).a((com.android.chileaf.bluetooth.connect.f1.b) CL880Manager.this.Z);
            CL880Manager cL880Manager3 = CL880Manager.this;
            cL880Manager3.a(cL880Manager3.U).a();
            CL880Manager cL880Manager4 = CL880Manager.this;
            cL880Manager4.c(cL880Manager4.k).a((com.android.chileaf.bluetooth.connect.f1.b) CL880Manager.this.a0);
            CL880Manager cL880Manager5 = CL880Manager.this;
            e1 g2 = cL880Manager5.a(cL880Manager5.k);
            g2.a((j) new b(this));
            g2.a((com.android.chileaf.bluetooth.connect.f1.d) new d(this));
            g2.a();
        }

        /* access modifiers changed from: protected */
        public void h() {
            super.h();
            CL880Manager.this.a0.b();
            BluetoothGattCharacteristic unused = CL880Manager.this.T = null;
            BluetoothGattCharacteristic unused2 = CL880Manager.this.U = null;
            int unused3 = CL880Manager.this.S = 0;
        }

        public /* synthetic */ void a(BluetoothDevice bluetoothDevice) {
            CL880Manager.this.a(3, "Rx notifications enabled");
        }

        /* access modifiers changed from: protected */
        public boolean b(BluetoothGatt bluetoothGatt) {
            super.b(bluetoothGatt);
            BluetoothGattService service = bluetoothGatt.getService(CL880Manager.e0);
            if (service != null) {
                BluetoothGattCharacteristic unused = CL880Manager.this.T = service.getCharacteristic(CL880Manager.f0);
            }
            return CL880Manager.this.T != null;
        }
    }

    private CL880Manager(Context context) {
        super(context);
        com.chileaf.fitness.device.wear.cl880.external.c.l().a(context);
        com.chileaf.fitness.device.wear.cl880.external.c.l().a(this);
    }

    private String D() {
        int i2 = 1;
        Object[] objArr = new Object[1];
        int i3 = this.S;
        if (i3 != 0) {
            i2 = i3;
        }
        objArr[0] = Integer.valueOf(i2);
        return String.format("%04x", objArr);
    }

    /* access modifiers changed from: private */
    public void E() {
        com.chileaf.fitness.device.wear.cl880.external.c l = com.chileaf.fitness.device.wear.cl880.external.c.l();
        byte[] a2 = HexUtil.a(com.android.chileaf.util.a.a());
        String a3 = a(1, 4, 1, d(l.i()));
        String a4 = a(8, 1, HexUtil.b(a2));
        String a5 = a(10, 1, b(l.e()));
        a(2, 1, a(l.a()));
        a(6, "sleep:%s", a5);
        a(a3 + a4 + "0A 0110040101010801040109011600080009000A280201010105 010D035C8AC5BC53556B2163D0919205 010D035C8AC5BC53556B2163D0919203 0101030701090A130E010C1117003203 0101030601090A2F16011734173B32");
    }

    public void A() {
        b(0);
    }

    public /* synthetic */ void i(BluetoothDevice bluetoothDevice, Data data) {
        a(6, "Tx writeTxCharacteristic:" + com.android.chileaf.bluetooth.connect.h1.a.a(data.a()));
    }

    /* access modifiers changed from: protected */
    public UUID k() {
        return c0;
    }

    /* access modifiers changed from: protected */
    public UUID l() {
        return d0;
    }

    /* access modifiers changed from: protected */
    public UUID m() {
        return b0;
    }

    /* access modifiers changed from: private */
    public void c(byte[] bArr) {
        a(HexUtil.a(HexUtil.b("FF2006"), bArr), true);
    }

    private String e(boolean z) {
        return String.valueOf(z ? 1 : 0);
    }

    public void b(boolean z) {
        super.b(z);
    }

    /* access modifiers changed from: protected */
    public g0<h>.defpackage.b d() {
        return new b();
    }

    public /* synthetic */ void f(BluetoothDevice bluetoothDevice) {
        this.S++;
        a(3, "Tx writeCharacteristic success  index:" + this.S);
    }

    public /* synthetic */ void m(BluetoothDevice bluetoothDevice, int i2) {
        a(5, "Tx writeCharacteristic failure");
    }

    private String b(MessageConfig messageConfig) {
        String str = e(messageConfig.facebook) + e(messageConfig.whatsApp) + e(messageConfig.skype) + e(messageConfig.qq) + e(messageConfig.wechat) + e(messageConfig.sms) + e(messageConfig.email) + e(messageConfig.missedCall);
        return HexUtil.a(str) + d(messageConfig.other);
    }

    private String d(boolean z) {
        return String.format("%02x", new Object[]{Integer.valueOf(z ? 1 : 0)});
    }

    public static synchronized CL880Manager a(Context context) {
        CL880Manager cL880Manager;
        synchronized (CL880Manager.class) {
            if (h0 == null) {
                h0 = new CL880Manager(context.getApplicationContext());
            }
            cL880Manager = h0;
        }
        return cL880Manager;
    }

    public void c(boolean z) {
        b(1, 14, 1, c(z ? 1 : 0));
    }

    private String c(String str) {
        return str.replace(" ", BuildConfig.FLAVOR);
    }

    private String c(int i2) {
        return String.format("%02x", new Object[]{Integer.valueOf(i2)});
    }

    public void a(e eVar) {
        if (this.V == null) {
            this.V = new ArrayList();
        }
        this.V.add(eVar);
    }

    public void a(d dVar) {
        if (this.W == null) {
            this.W = new ArrayList();
        }
        this.W.add(dVar);
    }

    private void b(String str) {
        b(1, 3, 1, str);
    }

    private String b(InactivityConfig inactivityConfig) {
        return c(inactivityConfig.amStartHH) + c(inactivityConfig.amStartMM) + c(inactivityConfig.amEndHH) + c(inactivityConfig.amEndMM) + c(inactivityConfig.pmStartHH) + c(inactivityConfig.pmStartMM) + c(inactivityConfig.pmEndHH) + c(inactivityConfig.pmEndMM) + c(inactivityConfig.interval);
    }

    public void a(f fVar) {
        if (this.X == null) {
            this.X = new ArrayList();
        }
        this.X.add(fVar);
    }

    public void a(BluetoothDevice bluetoothDevice, boolean z) {
        n0 a2 = a(bluetoothDevice);
        a2.a(z);
        a2.a();
    }

    /* access modifiers changed from: protected */
    public void a(byte[] bArr) {
        BluetoothGattCharacteristic bluetoothGattCharacteristic;
        if (e() && (bluetoothGattCharacteristic = this.l) != null) {
            e1 a2 = a(bluetoothGattCharacteristic, bArr);
            a2.k();
            a2.a((com.android.chileaf.bluetooth.connect.f1.c) new f(this));
            a2.a((j) new a(this));
            a2.a((com.android.chileaf.bluetooth.connect.f1.d) new e(this));
            a2.a();
        }
    }

    private String b(DrinkConfig drinkConfig) {
        return c(drinkConfig.amStartHH) + c(drinkConfig.amStartMM) + c(drinkConfig.amEndHH) + c(drinkConfig.amEndMM) + c(drinkConfig.pmStartHH) + c(drinkConfig.pmStartMM) + c(drinkConfig.pmEndHH) + c(drinkConfig.pmEndMM) + c(drinkConfig.interval);
    }

    private void a(String str) {
        b(HexUtil.b(c(str)));
    }

    /* access modifiers changed from: protected */
    public void a(byte[] bArr, boolean z) {
        if (z) {
            bArr = HexUtil.a(bArr, checkSum(bArr));
        }
        a(bArr);
    }

    public void a(MessageConfig messageConfig) {
        com.chileaf.fitness.device.wear.cl880.external.c.l().a(messageConfig);
        a(3, "setMessageConfig:%s", messageConfig.toString());
        b(1, 1, 1, b(messageConfig));
    }

    private String b(SleepConfig sleepConfig) {
        return d(sleepConfig.autoWake) + c(sleepConfig.startHH) + c(sleepConfig.startMM) + c(sleepConfig.workWakeHH) + c(sleepConfig.workWakeMM) + c(sleepConfig.restWakeHH) + c(sleepConfig.restWakeMM) + c(sleepConfig.actionTime) + c(sleepConfig.autoWakeTime);
    }

    private String a(List<AlarmConfig> list) {
        if (list == null || list.isEmpty()) {
            return c(0);
        }
        StringBuilder sb = new StringBuilder();
        for (AlarmConfig alarmConfig : list) {
            sb.insert(0, e(alarmConfig.enable));
        }
        return HexUtil.a(sb.reverse().toString());
    }

    private void b(int i2) {
        b(4, i2, 2, 0);
    }

    public void a(InactivityConfig inactivityConfig) {
        com.chileaf.fitness.device.wear.cl880.external.c l = com.chileaf.fitness.device.wear.cl880.external.c.l();
        l.a(inactivityConfig);
        a(3, "setInactivityConfig:%s", inactivityConfig.toString());
        b(1, 6, 1, b(inactivityConfig));
        b(HexUtil.a(e(inactivityConfig.enable) + e(l.b().enable)));
    }

    private void b(int i2, int i3, int i4, byte... bArr) {
        a(a(i2, i3, i4, bArr));
    }

    private void b(int i2, int i3, int i4, String str) {
        a(a(i2, i3, i4, str));
    }

    private void b(byte... bArr) {
        try {
            if (bArr.length > 0) {
                int length = bArr.length;
                if (length <= 14) {
                    a(true, 0, length + 6, bArr);
                    return;
                }
                int i2 = 128;
                int i3 = length - 14;
                int i4 = i3 % 16 != 0 ? (i3 / 16) + 1 : i3 / 16;
                a(true, 128, 20, HexUtil.a(bArr, 14));
                byte[] a2 = HexUtil.a(bArr, 14, bArr.length);
                for (int i5 = 0; i5 < i4; i5++) {
                    a(4, "result:%s length:%d index:%d total:%d", com.android.chileaf.bluetooth.connect.h1.a.a(a2), Integer.valueOf(a2.length), Integer.valueOf(i5), Integer.valueOf(i4));
                    if (a2.length > 16) {
                        i2++;
                        a(false, i2, 20, HexUtil.a(a2, 16));
                        a2 = HexUtil.a(a2, 16, a2.length);
                    } else {
                        a(false, 192 + i4, a2.length + 4, a2);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void a(DrinkConfig drinkConfig) {
        com.chileaf.fitness.device.wear.cl880.external.c l = com.chileaf.fitness.device.wear.cl880.external.c.l();
        l.a(drinkConfig);
        a(3, "setDrinkConfig:%s", drinkConfig.toString());
        b(1, 7, 1, b(drinkConfig));
        b(HexUtil.a(e(l.c().enable) + e(drinkConfig.enable)));
    }

    public void a(SleepConfig sleepConfig) {
        com.chileaf.fitness.device.wear.cl880.external.c.l().a(sleepConfig);
        a(3, "setSleepConfig:%s", sleepConfig.toString());
        b(1, 10, 1, b(sleepConfig));
    }

    public void a(com.chileaf.fitness.device.wear.cl880.external.b bVar) {
        try {
            a(3, "onCallNotification:%s", bVar.toString());
            b(2, 1, 1, c(bVar.c) + HexUtil.b(HexUtil.a(bVar.a, false)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void a(com.chileaf.fitness.device.wear.cl880.external.a aVar) {
        boolean z;
        String str;
        try {
            a(2, "setNotificationConfig:%s", aVar.toString());
            MessageConfig d = com.chileaf.fitness.device.wear.cl880.external.c.l().d();
            switch (a.a[aVar.f1222f.ordinal()]) {
                case 1:
                    z = d.missedCall;
                    break;
                case 2:
                    z = d.email;
                    break;
                case 3:
                    z = d.sms;
                    break;
                case 4:
                    z = d.wechat;
                    break;
                case 5:
                    z = d.qq;
                    break;
                case 6:
                    z = d.skype;
                    break;
                case 7:
                    z = d.whatsApp;
                    break;
                case 8:
                    z = d.facebook;
                    break;
                case 9:
                    z = d.other;
                    break;
                default:
                    z = false;
                    break;
            }
            if (z) {
                Calendar instance = Calendar.getInstance();
                instance.setTimeInMillis(aVar.b);
                if (aVar.c != null) {
                    str = aVar.c + ":" + aVar.d;
                } else {
                    str = aVar.d;
                }
                if (str.length() > 60) {
                    str = str.substring(59) + "...";
                }
                b(2, 2, 1, c(1) + c(aVar.f1222f.key) + c(instance.get(11)) + c(instance.get(12)) + HexUtil.b(HexUtil.a(str, false)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String a(int i2, int i3, String str) {
        String c = c(str);
        byte[] a2 = HexUtil.a(i2, i3, c.length() / 2);
        return HexUtil.b(a2) + c;
    }

    private String a(int i2, int i3, int i4, byte... bArr) {
        return HexUtil.b(HexUtil.a(HexUtil.a(i2, 16, i3, i4, bArr.length), bArr));
    }

    private String a(int i2, int i3, int i4, String str) {
        String c = c(str);
        byte[] a2 = HexUtil.a(i2, 16, i3, i4, c.length() / 2);
        return HexUtil.b(a2) + c;
    }

    private synchronized void a(boolean z, int i2, int i3, byte[] bArr) {
        byte[] a2 = HexUtil.a(255, i2, i3);
        if (z) {
            a2 = HexUtil.a(a2, HexUtil.b(D()));
        }
        byte[] a3 = HexUtil.a(a2, bArr);
        a(a3, true);
        a(3, "checkedPackageSend: %s result length:%d", com.android.chileaf.bluetooth.connect.h1.a.a(a3), Integer.valueOf(a3.length));
    }
}
