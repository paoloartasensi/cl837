package com.android.chileaf.bluetooth.connect;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattServer;
import android.bluetooth.BluetoothGattService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import com.android.chileaf.bluetooth.connect.Request;
import com.android.chileaf.bluetooth.connect.data.Data;
import java.lang.reflect.Method;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.LinkedBlockingDeque;

/* compiled from: BleManagerHandler */
abstract class j0 extends u0 {
    /* access modifiers changed from: private */
    public final HashMap<Object, c1> A = new HashMap<>();
    /* access modifiers changed from: private */
    @Deprecated
    public c1 B;
    /* access modifiers changed from: private */
    public f0 C;
    private final BroadcastReceiver D = new a();
    private final BroadcastReceiver E = new b();
    private final BluetoothGattCallback F = new c();
    private final Object a = new Object();
    /* access modifiers changed from: private */
    public BluetoothDevice b;
    /* access modifiers changed from: private */
    public BluetoothGatt c;
    /* access modifiers changed from: private */
    public g0 d;
    /* access modifiers changed from: private */
    public k0 e;
    /* access modifiers changed from: private */

    /* renamed from: f  reason: collision with root package name */
    public Handler f1053f;
    /* access modifiers changed from: private */

    /* renamed from: g  reason: collision with root package name */
    public final Deque<Request> f1054g = new LinkedBlockingDeque();
    /* access modifiers changed from: private */

    /* renamed from: h  reason: collision with root package name */
    public Deque<Request> f1055h;
    /* access modifiers changed from: private */

    /* renamed from: i  reason: collision with root package name */
    public boolean f1056i;
    /* access modifiers changed from: private */

    /* renamed from: j  reason: collision with root package name */
    public boolean f1057j;
    /* access modifiers changed from: private */
    public boolean k;
    /* access modifiers changed from: private */
    public long l;
    /* access modifiers changed from: private */
    public int m = 0;
    /* access modifiers changed from: private */
    public boolean n;
    /* access modifiers changed from: private */
    public boolean o;
    /* access modifiers changed from: private */
    public boolean p;
    /* access modifiers changed from: private */
    public boolean q;
    /* access modifiers changed from: private */
    public boolean r;
    /* access modifiers changed from: private */
    public int s;
    /* access modifiers changed from: private */
    public boolean t;
    /* access modifiers changed from: private */
    public int u = 23;
    /* access modifiers changed from: private */
    public Map<BluetoothGattCharacteristic, byte[]> v;
    /* access modifiers changed from: private */
    public Map<BluetoothGattDescriptor, byte[]> w;
    /* access modifiers changed from: private */
    public n0 x;
    /* access modifiers changed from: private */
    public Request y;
    /* access modifiers changed from: private */
    public v0 z;

    /* compiled from: BleManagerHandler */
    class a extends BroadcastReceiver {
        a() {
        }

        private String a(int i2) {
            switch (i2) {
                case 10:
                    return "OFF";
                case 11:
                    return "TURNING ON";
                case 12:
                    return "ON";
                case 13:
                    return "TURNING OFF";
                default:
                    return "UNKNOWN (" + i2 + ")";
            }
        }

        public void onReceive(Context context, Intent intent) {
            int intExtra = intent.getIntExtra("android.bluetooth.adapter.extra.STATE", 10);
            int intExtra2 = intent.getIntExtra("android.bluetooth.adapter.extra.PREVIOUS_STATE", 10);
            j0.this.a(3, "[Broadcast] Action received: android.bluetooth.adapter.action.STATE_CHANGED, state changed to " + a(intExtra));
            if (intExtra != 10 && intExtra != 13) {
                return;
            }
            if (intExtra2 == 13 || intExtra2 == 10) {
                j0.this.b();
                return;
            }
            boolean unused = j0.this.p = true;
            j0.this.f1054g.clear();
            Deque unused2 = j0.this.f1055h = null;
            BluetoothDevice u = j0.this.b;
            if (u != null) {
                if (!(j0.this.y == null || j0.this.y.c == Request.Type.DISCONNECT)) {
                    j0.this.y.b(u, -100);
                    Request unused3 = j0.this.y = null;
                }
                if (j0.this.C != null) {
                    j0.this.C.b(u, -100);
                    f0 unused4 = j0.this.C = null;
                }
                if (j0.this.x != null) {
                    j0.this.x.b(u, -100);
                    n0 unused5 = j0.this.x = null;
                }
            }
            boolean unused6 = j0.this.q = true;
            boolean unused7 = j0.this.p = false;
            if (u != null) {
                j0.this.a(u);
            }
        }
    }

    /* compiled from: BleManagerHandler */
    class b extends BroadcastReceiver {
        b() {
        }

        public /* synthetic */ void a() {
            j0.this.a(2, "Discovering services...");
            j0.this.a(3, "gatt.discoverServices()");
            j0.this.c.discoverServices();
        }

        public void onReceive(Context context, Intent intent) {
            BluetoothDevice bluetoothDevice = (BluetoothDevice) intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE");
            int intExtra = intent.getIntExtra("android.bluetooth.device.extra.BOND_STATE", -1);
            int intExtra2 = intent.getIntExtra("android.bluetooth.device.extra.PREVIOUS_BOND_STATE", -1);
            if (j0.this.b != null && bluetoothDevice != null && bluetoothDevice.getAddress().equals(j0.this.b.getAddress())) {
                j0 j0Var = j0.this;
                j0Var.a(3, "[Broadcast] Action received: android.bluetooth.device.action.BOND_STATE_CHANGED, bond state changed to: " + com.android.chileaf.bluetooth.connect.h1.a.a(intExtra) + " (" + intExtra + ")");
                switch (intExtra) {
                    case 10:
                        if (intExtra2 != 11) {
                            if (intExtra2 == 12 && j0.this.y != null && j0.this.y.c == Request.Type.REMOVE_BOND) {
                                j0.this.a(4, "Bond information removed");
                                j0.this.y.d(bluetoothDevice);
                                Request unused = j0.this.y = null;
                                break;
                            }
                        } else {
                            j0.this.d.c.g(bluetoothDevice);
                            j0.this.a(5, "Bonding failed");
                            if (j0.this.y != null) {
                                j0.this.y.b(bluetoothDevice, -4);
                                Request unused2 = j0.this.y = null;
                                break;
                            }
                        }
                        break;
                    case 11:
                        j0.this.d.c.i(bluetoothDevice);
                        return;
                    case 12:
                        j0.this.a(4, "Device bonded");
                        j0.this.d.c.f(bluetoothDevice);
                        if (j0.this.y != null && j0.this.y.c == Request.Type.CREATE_BOND) {
                            j0.this.y.d(bluetoothDevice);
                            Request unused3 = j0.this.y = null;
                            break;
                        } else if (j0.this.f1057j || j0.this.k) {
                            if (Build.VERSION.SDK_INT < 26 && j0.this.y != null) {
                                j0 j0Var2 = j0.this;
                                j0Var2.b(j0Var2.y);
                                break;
                            } else {
                                return;
                            }
                        } else {
                            boolean unused4 = j0.this.k = true;
                            j0.this.f1053f.post(new d(this));
                            return;
                        }
                        break;
                }
                j0.this.b(true);
            }
        }
    }

    /* compiled from: BleManagerHandler */
    static /* synthetic */ class d {
        static final /* synthetic */ int[] a;

        /* JADX WARNING: Can't wrap try/catch for region: R(70:0|1|2|3|4|5|6|7|8|9|10|11|12|13|14|15|16|17|18|19|20|21|22|23|24|25|26|27|28|29|30|31|32|33|34|35|36|37|38|39|40|41|42|43|44|45|46|47|48|49|50|51|52|53|54|55|56|57|58|59|60|61|62|63|64|65|66|67|68|(3:69|70|72)) */
        /* JADX WARNING: Can't wrap try/catch for region: R(72:0|1|2|3|4|5|6|7|8|9|10|11|12|13|14|15|16|17|18|19|20|21|22|23|24|25|26|27|28|29|30|31|32|33|34|35|36|37|38|39|40|41|42|43|44|45|46|47|48|49|50|51|52|53|54|55|56|57|58|59|60|61|62|63|64|65|66|67|68|69|70|72) */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:11:0x003e */
        /* JADX WARNING: Missing exception handler attribute for start block: B:13:0x0049 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:15:0x0054 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:17:0x0060 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:19:0x006c */
        /* JADX WARNING: Missing exception handler attribute for start block: B:21:0x0078 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:23:0x0084 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:25:0x0090 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:27:0x009c */
        /* JADX WARNING: Missing exception handler attribute for start block: B:29:0x00a8 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:31:0x00b4 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:33:0x00c0 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:35:0x00cc */
        /* JADX WARNING: Missing exception handler attribute for start block: B:37:0x00d8 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:39:0x00e4 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0012 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:41:0x00f0 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:43:0x00fc */
        /* JADX WARNING: Missing exception handler attribute for start block: B:45:0x0108 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:47:0x0114 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:49:0x0120 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:51:0x012c */
        /* JADX WARNING: Missing exception handler attribute for start block: B:53:0x0138 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:55:0x0144 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:57:0x0150 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:59:0x015c */
        /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x001d */
        /* JADX WARNING: Missing exception handler attribute for start block: B:61:0x0168 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:63:0x0174 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:65:0x0180 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:67:0x018c */
        /* JADX WARNING: Missing exception handler attribute for start block: B:69:0x0198 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:7:0x0028 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:9:0x0033 */
        static {
            /*
                com.android.chileaf.bluetooth.connect.Request$Type[] r0 = com.android.chileaf.bluetooth.connect.Request.Type.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                a = r0
                com.android.chileaf.bluetooth.connect.Request$Type r1 = com.android.chileaf.bluetooth.connect.Request.Type.NOTIFY     // Catch:{ NoSuchFieldError -> 0x0012 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0012 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0012 }
            L_0x0012:
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x001d }
                com.android.chileaf.bluetooth.connect.Request$Type r1 = com.android.chileaf.bluetooth.connect.Request.Type.INDICATE     // Catch:{ NoSuchFieldError -> 0x001d }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001d }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001d }
            L_0x001d:
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x0028 }
                com.android.chileaf.bluetooth.connect.Request$Type r1 = com.android.chileaf.bluetooth.connect.Request.Type.WAIT_FOR_NOTIFICATION     // Catch:{ NoSuchFieldError -> 0x0028 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0028 }
                r2 = 3
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0028 }
            L_0x0028:
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x0033 }
                com.android.chileaf.bluetooth.connect.Request$Type r1 = com.android.chileaf.bluetooth.connect.Request.Type.WAIT_FOR_INDICATION     // Catch:{ NoSuchFieldError -> 0x0033 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0033 }
                r2 = 4
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0033 }
            L_0x0033:
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x003e }
                com.android.chileaf.bluetooth.connect.Request$Type r1 = com.android.chileaf.bluetooth.connect.Request.Type.WAIT_FOR_READ     // Catch:{ NoSuchFieldError -> 0x003e }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x003e }
                r2 = 5
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x003e }
            L_0x003e:
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x0049 }
                com.android.chileaf.bluetooth.connect.Request$Type r1 = com.android.chileaf.bluetooth.connect.Request.Type.WAIT_FOR_WRITE     // Catch:{ NoSuchFieldError -> 0x0049 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0049 }
                r2 = 6
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0049 }
            L_0x0049:
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x0054 }
                com.android.chileaf.bluetooth.connect.Request$Type r1 = com.android.chileaf.bluetooth.connect.Request.Type.CONNECT     // Catch:{ NoSuchFieldError -> 0x0054 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0054 }
                r2 = 7
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0054 }
            L_0x0054:
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x0060 }
                com.android.chileaf.bluetooth.connect.Request$Type r1 = com.android.chileaf.bluetooth.connect.Request.Type.DISCONNECT     // Catch:{ NoSuchFieldError -> 0x0060 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0060 }
                r2 = 8
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0060 }
            L_0x0060:
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x006c }
                com.android.chileaf.bluetooth.connect.Request$Type r1 = com.android.chileaf.bluetooth.connect.Request.Type.CREATE_BOND     // Catch:{ NoSuchFieldError -> 0x006c }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x006c }
                r2 = 9
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x006c }
            L_0x006c:
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x0078 }
                com.android.chileaf.bluetooth.connect.Request$Type r1 = com.android.chileaf.bluetooth.connect.Request.Type.REMOVE_BOND     // Catch:{ NoSuchFieldError -> 0x0078 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0078 }
                r2 = 10
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0078 }
            L_0x0078:
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x0084 }
                com.android.chileaf.bluetooth.connect.Request$Type r1 = com.android.chileaf.bluetooth.connect.Request.Type.SET     // Catch:{ NoSuchFieldError -> 0x0084 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0084 }
                r2 = 11
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0084 }
            L_0x0084:
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x0090 }
                com.android.chileaf.bluetooth.connect.Request$Type r1 = com.android.chileaf.bluetooth.connect.Request.Type.READ     // Catch:{ NoSuchFieldError -> 0x0090 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0090 }
                r2 = 12
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0090 }
            L_0x0090:
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x009c }
                com.android.chileaf.bluetooth.connect.Request$Type r1 = com.android.chileaf.bluetooth.connect.Request.Type.WRITE     // Catch:{ NoSuchFieldError -> 0x009c }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x009c }
                r2 = 13
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x009c }
            L_0x009c:
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x00a8 }
                com.android.chileaf.bluetooth.connect.Request$Type r1 = com.android.chileaf.bluetooth.connect.Request.Type.READ_DESCRIPTOR     // Catch:{ NoSuchFieldError -> 0x00a8 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x00a8 }
                r2 = 14
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x00a8 }
            L_0x00a8:
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x00b4 }
                com.android.chileaf.bluetooth.connect.Request$Type r1 = com.android.chileaf.bluetooth.connect.Request.Type.WRITE_DESCRIPTOR     // Catch:{ NoSuchFieldError -> 0x00b4 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x00b4 }
                r2 = 15
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x00b4 }
            L_0x00b4:
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x00c0 }
                com.android.chileaf.bluetooth.connect.Request$Type r1 = com.android.chileaf.bluetooth.connect.Request.Type.SET_VALUE     // Catch:{ NoSuchFieldError -> 0x00c0 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x00c0 }
                r2 = 16
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x00c0 }
            L_0x00c0:
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x00cc }
                com.android.chileaf.bluetooth.connect.Request$Type r1 = com.android.chileaf.bluetooth.connect.Request.Type.SET_DESCRIPTOR_VALUE     // Catch:{ NoSuchFieldError -> 0x00cc }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x00cc }
                r2 = 17
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x00cc }
            L_0x00cc:
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x00d8 }
                com.android.chileaf.bluetooth.connect.Request$Type r1 = com.android.chileaf.bluetooth.connect.Request.Type.BEGIN_RELIABLE_WRITE     // Catch:{ NoSuchFieldError -> 0x00d8 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x00d8 }
                r2 = 18
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x00d8 }
            L_0x00d8:
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x00e4 }
                com.android.chileaf.bluetooth.connect.Request$Type r1 = com.android.chileaf.bluetooth.connect.Request.Type.EXECUTE_RELIABLE_WRITE     // Catch:{ NoSuchFieldError -> 0x00e4 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x00e4 }
                r2 = 19
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x00e4 }
            L_0x00e4:
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x00f0 }
                com.android.chileaf.bluetooth.connect.Request$Type r1 = com.android.chileaf.bluetooth.connect.Request.Type.ABORT_RELIABLE_WRITE     // Catch:{ NoSuchFieldError -> 0x00f0 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x00f0 }
                r2 = 20
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x00f0 }
            L_0x00f0:
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x00fc }
                com.android.chileaf.bluetooth.connect.Request$Type r1 = com.android.chileaf.bluetooth.connect.Request.Type.ENABLE_NOTIFICATIONS     // Catch:{ NoSuchFieldError -> 0x00fc }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x00fc }
                r2 = 21
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x00fc }
            L_0x00fc:
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x0108 }
                com.android.chileaf.bluetooth.connect.Request$Type r1 = com.android.chileaf.bluetooth.connect.Request.Type.ENABLE_INDICATIONS     // Catch:{ NoSuchFieldError -> 0x0108 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0108 }
                r2 = 22
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0108 }
            L_0x0108:
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x0114 }
                com.android.chileaf.bluetooth.connect.Request$Type r1 = com.android.chileaf.bluetooth.connect.Request.Type.DISABLE_NOTIFICATIONS     // Catch:{ NoSuchFieldError -> 0x0114 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0114 }
                r2 = 23
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0114 }
            L_0x0114:
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x0120 }
                com.android.chileaf.bluetooth.connect.Request$Type r1 = com.android.chileaf.bluetooth.connect.Request.Type.DISABLE_INDICATIONS     // Catch:{ NoSuchFieldError -> 0x0120 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0120 }
                r2 = 24
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0120 }
            L_0x0120:
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x012c }
                com.android.chileaf.bluetooth.connect.Request$Type r1 = com.android.chileaf.bluetooth.connect.Request.Type.READ_BATTERY_LEVEL     // Catch:{ NoSuchFieldError -> 0x012c }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x012c }
                r2 = 25
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x012c }
            L_0x012c:
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x0138 }
                com.android.chileaf.bluetooth.connect.Request$Type r1 = com.android.chileaf.bluetooth.connect.Request.Type.ENABLE_BATTERY_LEVEL_NOTIFICATIONS     // Catch:{ NoSuchFieldError -> 0x0138 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0138 }
                r2 = 26
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0138 }
            L_0x0138:
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x0144 }
                com.android.chileaf.bluetooth.connect.Request$Type r1 = com.android.chileaf.bluetooth.connect.Request.Type.DISABLE_BATTERY_LEVEL_NOTIFICATIONS     // Catch:{ NoSuchFieldError -> 0x0144 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0144 }
                r2 = 27
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0144 }
            L_0x0144:
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x0150 }
                com.android.chileaf.bluetooth.connect.Request$Type r1 = com.android.chileaf.bluetooth.connect.Request.Type.ENABLE_SERVICE_CHANGED_INDICATIONS     // Catch:{ NoSuchFieldError -> 0x0150 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0150 }
                r2 = 28
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0150 }
            L_0x0150:
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x015c }
                com.android.chileaf.bluetooth.connect.Request$Type r1 = com.android.chileaf.bluetooth.connect.Request.Type.REQUEST_MTU     // Catch:{ NoSuchFieldError -> 0x015c }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x015c }
                r2 = 29
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x015c }
            L_0x015c:
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x0168 }
                com.android.chileaf.bluetooth.connect.Request$Type r1 = com.android.chileaf.bluetooth.connect.Request.Type.REQUEST_CONNECTION_PRIORITY     // Catch:{ NoSuchFieldError -> 0x0168 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0168 }
                r2 = 30
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0168 }
            L_0x0168:
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x0174 }
                com.android.chileaf.bluetooth.connect.Request$Type r1 = com.android.chileaf.bluetooth.connect.Request.Type.SET_PREFERRED_PHY     // Catch:{ NoSuchFieldError -> 0x0174 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0174 }
                r2 = 31
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0174 }
            L_0x0174:
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x0180 }
                com.android.chileaf.bluetooth.connect.Request$Type r1 = com.android.chileaf.bluetooth.connect.Request.Type.READ_PHY     // Catch:{ NoSuchFieldError -> 0x0180 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0180 }
                r2 = 32
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0180 }
            L_0x0180:
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x018c }
                com.android.chileaf.bluetooth.connect.Request$Type r1 = com.android.chileaf.bluetooth.connect.Request.Type.READ_RSSI     // Catch:{ NoSuchFieldError -> 0x018c }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x018c }
                r2 = 33
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x018c }
            L_0x018c:
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x0198 }
                com.android.chileaf.bluetooth.connect.Request$Type r1 = com.android.chileaf.bluetooth.connect.Request.Type.REFRESH_CACHE     // Catch:{ NoSuchFieldError -> 0x0198 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0198 }
                r2 = 34
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0198 }
            L_0x0198:
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x01a4 }
                com.android.chileaf.bluetooth.connect.Request$Type r1 = com.android.chileaf.bluetooth.connect.Request.Type.SLEEP     // Catch:{ NoSuchFieldError -> 0x01a4 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x01a4 }
                r2 = 35
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x01a4 }
            L_0x01a4:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.chileaf.bluetooth.connect.j0.d.<clinit>():void");
        }
    }

    j0() {
    }

    static /* synthetic */ int i(j0 j0Var) {
        int i2 = j0Var.m + 1;
        j0Var.m = i2;
        return i2;
    }

    /* access modifiers changed from: protected */
    @Deprecated
    public Deque<Request> a(BluetoothGatt bluetoothGatt) {
        return null;
    }

    /* access modifiers changed from: protected */
    @Deprecated
    public void a(BluetoothGatt bluetoothGatt, int i2) {
    }

    /* access modifiers changed from: protected */
    @Deprecated
    public void a(BluetoothGatt bluetoothGatt, BluetoothGattCharacteristic bluetoothGattCharacteristic) {
    }

    /* access modifiers changed from: protected */
    @Deprecated
    public void a(BluetoothGatt bluetoothGatt, BluetoothGattDescriptor bluetoothGattDescriptor) {
    }

    /* access modifiers changed from: protected */
    public void a(BluetoothGattServer bluetoothGattServer) {
    }

    /* access modifiers changed from: protected */
    @Deprecated
    public void b(BluetoothGatt bluetoothGatt, int i2) {
    }

    /* access modifiers changed from: protected */
    @Deprecated
    public void b(BluetoothGatt bluetoothGatt, BluetoothGattCharacteristic bluetoothGattCharacteristic) {
    }

    /* access modifiers changed from: protected */
    @Deprecated
    public void b(BluetoothGatt bluetoothGatt, BluetoothGattDescriptor bluetoothGattDescriptor) {
    }

    /* access modifiers changed from: protected */
    public abstract boolean b(BluetoothGatt bluetoothGatt);

    /* access modifiers changed from: protected */
    @Deprecated
    public void c(BluetoothGatt bluetoothGatt, BluetoothGattCharacteristic bluetoothGattCharacteristic) {
    }

    /* access modifiers changed from: protected */
    public abstract boolean c(BluetoothGatt bluetoothGatt);

    /* access modifiers changed from: protected */
    @Deprecated
    public void d(BluetoothGatt bluetoothGatt, BluetoothGattCharacteristic bluetoothGattCharacteristic) {
    }

    /* access modifiers changed from: protected */
    public abstract void e();

    /* access modifiers changed from: protected */
    public abstract void h();

    /* access modifiers changed from: protected */
    public void i() {
    }

    /* access modifiers changed from: protected */
    public void j() {
    }

    /* access modifiers changed from: private */
    public boolean l() {
        f0 f0Var = this.C;
        if (!(f0Var instanceof m0)) {
            return false;
        }
        m0 m0Var = (m0) f0Var;
        if (!m0Var.l()) {
            return false;
        }
        m0Var.d(this.b);
        this.C = null;
        return true;
    }

    private boolean m() {
        BluetoothGattService service;
        BluetoothGattCharacteristic characteristic;
        BluetoothGatt bluetoothGatt = this.c;
        if (bluetoothGatt == null || !this.n || bluetoothGatt.getDevice().getBondState() != 12 || (service = bluetoothGatt.getService(g0.f1049h)) == null || (characteristic = service.getCharacteristic(g0.f1050i)) == null) {
            return false;
        }
        a(4, "Service Changed characteristic found on a bonded device");
        return c(characteristic);
    }

    private boolean n() {
        BluetoothGatt bluetoothGatt = this.c;
        if (bluetoothGatt == null || !this.n || !this.t) {
            return false;
        }
        a(2, "Aborting reliable write...");
        if (Build.VERSION.SDK_INT >= 19) {
            a(3, "gatt.abortReliableWrite()");
            bluetoothGatt.abortReliableWrite();
            return true;
        }
        a(3, "gatt.abortReliableWrite(device)");
        bluetoothGatt.abortReliableWrite(bluetoothGatt.getDevice());
        return true;
    }

    private boolean o() {
        BluetoothGatt bluetoothGatt = this.c;
        if (bluetoothGatt == null || !this.n) {
            return false;
        }
        if (this.t) {
            return true;
        }
        a(2, "Beginning reliable write...");
        a(3, "gatt.beginReliableWrite()");
        boolean beginReliableWrite = bluetoothGatt.beginReliableWrite();
        this.t = beginReliableWrite;
        return beginReliableWrite;
    }

    private boolean p() {
        BluetoothDevice bluetoothDevice = this.b;
        if (bluetoothDevice == null) {
            return false;
        }
        a(2, "Starting pairing...");
        if (bluetoothDevice.getBondState() == 12) {
            a(5, "Device already bonded");
            this.y.d(bluetoothDevice);
            b(true);
            return true;
        } else if (Build.VERSION.SDK_INT >= 19) {
            a(3, "device.createBond()");
            return bluetoothDevice.createBond();
        } else {
            try {
                Method method = bluetoothDevice.getClass().getMethod("createBond", new Class[0]);
                a(3, "device.createBond() (hidden)");
                return ((Boolean) method.invoke(bluetoothDevice, new Object[0])).booleanValue();
            } catch (Exception e2) {
                Log.w("BleManager", "An exception occurred while creating bond", e2);
                return false;
            }
        }
    }

    /* access modifiers changed from: private */
    public boolean q() {
        this.q = true;
        this.r = false;
        if (this.c != null) {
            a(2, this.n ? "Disconnecting..." : "Cancelling connection...");
            this.d.c.e(this.c.getDevice());
            boolean z2 = this.n;
            a(3, "gatt.disconnect()");
            this.c.disconnect();
            if (z2) {
                return true;
            }
            a(4, "Disconnected");
            this.d.c.a(this.c.getDevice());
        }
        Request request = this.y;
        if (request != null && request.c == Request.Type.DISCONNECT) {
            BluetoothDevice bluetoothDevice = this.b;
            if (bluetoothDevice != null) {
                request.d(bluetoothDevice);
            } else {
                request.c();
            }
        }
        b(true);
        return true;
    }

    private boolean r() {
        BluetoothGatt bluetoothGatt = this.c;
        if (bluetoothGatt == null || !this.n || !this.t) {
            return false;
        }
        a(2, "Executing reliable write...");
        a(3, "gatt.executeReliableWrite()");
        return bluetoothGatt.executeReliableWrite();
    }

    @Deprecated
    private boolean s() {
        BluetoothGattService service;
        BluetoothGatt bluetoothGatt = this.c;
        if (bluetoothGatt == null || !this.n || (service = bluetoothGatt.getService(g0.f1047f)) == null) {
            return false;
        }
        return e(service.getCharacteristic(g0.f1048g));
    }

    private boolean t() {
        BluetoothGatt bluetoothGatt = this.c;
        if (bluetoothGatt == null || !this.n) {
            return false;
        }
        a(2, "Reading PHY...");
        a(3, "gatt.readPhy()");
        bluetoothGatt.readPhy();
        return true;
    }

    private boolean u() {
        BluetoothGatt bluetoothGatt = this.c;
        if (bluetoothGatt == null || !this.n) {
            return false;
        }
        a(2, "Reading remote RSSI...");
        a(3, "gatt.readRemoteRssi()");
        return bluetoothGatt.readRemoteRssi();
    }

    private boolean v() {
        BluetoothGatt bluetoothGatt = this.c;
        if (bluetoothGatt == null) {
            return false;
        }
        a(2, "Refreshing device cache...");
        a(3, "gatt.refresh() (hidden)");
        try {
            return ((Boolean) bluetoothGatt.getClass().getMethod("refresh", new Class[0]).invoke(bluetoothGatt, new Object[0])).booleanValue();
        } catch (Exception e2) {
            Log.w("BleManager", "An exception occurred while refreshing device", e2);
            a(5, "gatt.refresh() method not found");
            return false;
        }
    }

    private boolean w() {
        BluetoothDevice bluetoothDevice = this.b;
        if (bluetoothDevice == null) {
            return false;
        }
        a(2, "Removing bond information...");
        if (bluetoothDevice.getBondState() == 10) {
            a(5, "Device is not bonded");
            this.y.d(bluetoothDevice);
            b(true);
            return true;
        }
        try {
            Method method = bluetoothDevice.getClass().getMethod("removeBond", new Class[0]);
            a(3, "device.removeBond() (hidden)");
            return ((Boolean) method.invoke(bluetoothDevice, new Object[0])).booleanValue();
        } catch (Exception e2) {
            Log.w("BleManager", "An exception occurred while removing bond", e2);
            return false;
        }
    }

    /* access modifiers changed from: package-private */
    @Deprecated
    public void k() {
        if (this.B == null) {
            c1 c1Var = new c1(this.f1053f);
            c1Var.a((com.android.chileaf.bluetooth.connect.f1.b) new k(this));
            this.B = c1Var;
        }
    }

    private boolean c(BluetoothGattCharacteristic bluetoothGattCharacteristic) {
        BluetoothGattDescriptor a2;
        BluetoothGatt bluetoothGatt = this.c;
        if (bluetoothGatt == null || bluetoothGattCharacteristic == null || !this.n || (a2 = a(bluetoothGattCharacteristic, 32)) == null) {
            return false;
        }
        a(3, "gatt.setCharacteristicNotification(" + bluetoothGattCharacteristic.getUuid() + ", true)");
        bluetoothGatt.setCharacteristicNotification(bluetoothGattCharacteristic, true);
        a2.setValue(BluetoothGattDescriptor.ENABLE_INDICATION_VALUE);
        a(2, "Enabling indications for " + bluetoothGattCharacteristic.getUuid());
        a(3, "gatt.writeDescriptor(" + g0.e + ", value=0x02-00)");
        return c(a2);
    }

    private boolean e(BluetoothGattCharacteristic bluetoothGattCharacteristic) {
        BluetoothGatt bluetoothGatt = this.c;
        if (bluetoothGatt == null || bluetoothGattCharacteristic == null || !this.n || (bluetoothGattCharacteristic.getProperties() & 2) == 0) {
            return false;
        }
        a(2, "Reading characteristic " + bluetoothGattCharacteristic.getUuid());
        a(3, "gatt.readCharacteristic(" + bluetoothGattCharacteristic.getUuid() + ")");
        return bluetoothGatt.readCharacteristic(bluetoothGattCharacteristic);
    }

    private boolean f(BluetoothGattCharacteristic bluetoothGattCharacteristic) {
        BluetoothGatt bluetoothGatt = this.c;
        if (bluetoothGatt == null || bluetoothGattCharacteristic == null || !this.n || (bluetoothGattCharacteristic.getProperties() & 12) == 0) {
            return false;
        }
        a(2, "Writing characteristic " + bluetoothGattCharacteristic.getUuid() + " (" + com.android.chileaf.bluetooth.connect.h1.a.g(bluetoothGattCharacteristic.getWriteType()) + ")");
        StringBuilder sb = new StringBuilder();
        sb.append("gatt.writeCharacteristic(");
        sb.append(bluetoothGattCharacteristic.getUuid());
        sb.append(")");
        a(3, sb.toString());
        return bluetoothGatt.writeCharacteristic(bluetoothGattCharacteristic);
    }

    /* access modifiers changed from: private */
    public boolean h(BluetoothGattCharacteristic bluetoothGattCharacteristic) {
        return bluetoothGattCharacteristic != null && g0.f1050i.equals(bluetoothGattCharacteristic.getUuid());
    }

    public BluetoothDevice d() {
        return this.b;
    }

    public /* synthetic */ void g() {
        b(this.b);
        b(true);
    }

    private boolean d(BluetoothGattCharacteristic bluetoothGattCharacteristic) {
        BluetoothGattDescriptor a2;
        BluetoothGatt bluetoothGatt = this.c;
        if (bluetoothGatt == null || bluetoothGattCharacteristic == null || !this.n || (a2 = a(bluetoothGattCharacteristic, 16)) == null) {
            return false;
        }
        a(3, "gatt.setCharacteristicNotification(" + bluetoothGattCharacteristic.getUuid() + ", true)");
        bluetoothGatt.setCharacteristicNotification(bluetoothGattCharacteristic, true);
        a2.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
        a(2, "Enabling notifications for " + bluetoothGattCharacteristic.getUuid());
        a(3, "gatt.writeDescriptor(" + g0.e + ", value=0x01-00)");
        return c(a2);
    }

    /* access modifiers changed from: private */
    @Deprecated
    public boolean g(BluetoothGattCharacteristic bluetoothGattCharacteristic) {
        return bluetoothGattCharacteristic != null && g0.f1048g.equals(bluetoothGattCharacteristic.getUuid());
    }

    /* compiled from: BleManagerHandler */
    class c extends BluetoothGattCallback {
        c() {
        }

        public /* synthetic */ void a(int i2, BluetoothGatt bluetoothGatt) {
            if (i2 == j0.this.m && j0.this.n && bluetoothGatt.getDevice().getBondState() != 11) {
                boolean unused = j0.this.k = true;
                j0.this.a(2, "Discovering services...");
                j0.this.a(3, "gatt.discoverServices()");
                bluetoothGatt.discoverServices();
            }
        }

        public void onCharacteristicChanged(BluetoothGatt bluetoothGatt, BluetoothGattCharacteristic bluetoothGattCharacteristic) {
            byte[] value = bluetoothGattCharacteristic.getValue();
            if (j0.this.h(bluetoothGattCharacteristic)) {
                boolean unused = j0.this.p = true;
                j0.this.f1054g.clear();
                Deque unused2 = j0.this.f1055h = null;
                j0.this.a(4, "Service Changed indication received");
                j0.this.a(2, "Discovering Services...");
                j0.this.a(3, "gatt.discoverServices()");
                bluetoothGatt.discoverServices();
                return;
            }
            BluetoothGattDescriptor descriptor = bluetoothGattCharacteristic.getDescriptor(g0.e);
            boolean z = false;
            if (descriptor == null || descriptor.getValue() == null || descriptor.getValue().length != 2 || descriptor.getValue()[0] == 1) {
                z = true;
            }
            String a2 = com.android.chileaf.bluetooth.connect.h1.a.a(value);
            if (z) {
                j0 j0Var = j0.this;
                j0Var.a(4, "Notification received from " + bluetoothGattCharacteristic.getUuid() + ", value: " + a2);
                j0.this.b(bluetoothGatt, bluetoothGattCharacteristic);
            } else {
                j0 j0Var2 = j0.this;
                j0Var2.a(4, "Indication received from " + bluetoothGattCharacteristic.getUuid() + ", value: " + a2);
                j0.this.a(bluetoothGatt, bluetoothGattCharacteristic);
            }
            if (j0.this.B != null && j0.this.g(bluetoothGattCharacteristic)) {
                j0.this.B.b(bluetoothGatt.getDevice(), value);
            }
            c1 c1Var = (c1) j0.this.A.get(bluetoothGattCharacteristic);
            if (c1Var != null && c1Var.a(value)) {
                c1Var.b(bluetoothGatt.getDevice(), value);
            }
            if ((j0.this.C instanceof d1) && j0.this.C.d == bluetoothGattCharacteristic && !j0.this.C.k()) {
                d1 d1Var = (d1) j0.this.C;
                if (d1Var.a(value)) {
                    d1Var.a(bluetoothGatt.getDevice(), value);
                    if (!d1Var.l()) {
                        d1Var.d(bluetoothGatt.getDevice());
                        f0 unused3 = j0.this.C = null;
                        if (d1Var.j()) {
                            j0.this.b(true);
                        }
                    }
                }
            }
            if (j0.this.l()) {
                j0.this.b(true);
            }
        }

        public void onCharacteristicRead(BluetoothGatt bluetoothGatt, BluetoothGattCharacteristic bluetoothGattCharacteristic, int i2) {
            byte[] value = bluetoothGattCharacteristic.getValue();
            if (i2 == 0) {
                j0 j0Var = j0.this;
                j0Var.a(4, "Read Response received from " + bluetoothGattCharacteristic.getUuid() + ", value: " + com.android.chileaf.bluetooth.connect.h1.a.a(value));
                j0.this.c(bluetoothGatt, bluetoothGattCharacteristic);
                if (j0.this.y instanceof s0) {
                    s0 s0Var = (s0) j0.this.y;
                    boolean a2 = s0Var.a(value);
                    if (a2) {
                        s0Var.b(bluetoothGatt.getDevice(), value);
                    }
                    if (!a2 || s0Var.i()) {
                        j0.this.b((Request) s0Var);
                    } else {
                        s0Var.d(bluetoothGatt.getDevice());
                    }
                }
            } else if (i2 == 5 || i2 == 8 || i2 == 137) {
                j0 j0Var2 = j0.this;
                j0Var2.a(5, "Authentication required (" + i2 + ")");
                if (bluetoothGatt.getDevice().getBondState() != 10) {
                    Log.w("BleManager", "Phone has lost bonding information");
                    j0.this.d.c.a(bluetoothGatt.getDevice(), "Phone has lost bonding information", i2);
                    return;
                }
                return;
            } else {
                Log.e("BleManager", "onCharacteristicRead error " + i2);
                if (j0.this.y instanceof s0) {
                    j0.this.y.b(bluetoothGatt.getDevice(), i2);
                }
                f0 unused = j0.this.C = null;
                j0.this.a(bluetoothGatt.getDevice(), "Error on reading characteristic", i2);
            }
            boolean unused2 = j0.this.l();
            j0.this.b(true);
        }

        public void onCharacteristicWrite(BluetoothGatt bluetoothGatt, BluetoothGattCharacteristic bluetoothGattCharacteristic, int i2) {
            byte[] value = bluetoothGattCharacteristic.getValue();
            if (i2 == 0) {
                j0 j0Var = j0.this;
                j0Var.a(4, "Data written to " + bluetoothGattCharacteristic.getUuid() + ", value: " + com.android.chileaf.bluetooth.connect.h1.a.a(value));
                j0.this.d(bluetoothGatt, bluetoothGattCharacteristic);
                if (j0.this.y instanceof e1) {
                    e1 e1Var = (e1) j0.this.y;
                    if (!e1Var.b(bluetoothGatt.getDevice(), value)) {
                        v0 unused = j0.this.z;
                    }
                    if (e1Var.j()) {
                        j0.this.b((Request) e1Var);
                    } else {
                        e1Var.d(bluetoothGatt.getDevice());
                    }
                }
            } else if (i2 == 5 || i2 == 8 || i2 == 137) {
                j0 j0Var2 = j0.this;
                j0Var2.a(5, "Authentication required (" + i2 + ")");
                if (bluetoothGatt.getDevice().getBondState() != 10) {
                    Log.w("BleManager", "Phone has lost bonding information");
                    j0.this.d.c.a(bluetoothGatt.getDevice(), "Phone has lost bonding information", i2);
                    return;
                }
                return;
            } else {
                Log.e("BleManager", "onCharacteristicWrite error " + i2);
                if (j0.this.y instanceof e1) {
                    j0.this.y.b(bluetoothGatt.getDevice(), i2);
                    v0 unused2 = j0.this.z;
                }
                f0 unused3 = j0.this.C = null;
                j0.this.a(bluetoothGatt.getDevice(), "Error on writing characteristic", i2);
            }
            boolean unused4 = j0.this.l();
            j0.this.b(true);
        }

        public final void onConnectionStateChange(BluetoothGatt bluetoothGatt, int i2, int i3) {
            j0 j0Var = j0.this;
            j0Var.a(3, "[Callback] Connection state changed with status: " + i2 + " and new state: " + i3 + " (" + com.android.chileaf.bluetooth.connect.h1.a.f(i3) + ")");
            boolean z = true;
            if (i2 != 0 || i3 != 2) {
                if (i3 == 0) {
                    long elapsedRealtime = SystemClock.elapsedRealtime();
                    boolean z2 = j0.this.l > 0;
                    boolean z3 = z2 && elapsedRealtime > j0.this.l + 20000;
                    if (i2 != 0) {
                        j0 j0Var2 = j0.this;
                        j0Var2.a(5, "Error: (0x" + Integer.toHexString(i2) + "): " + com.android.chileaf.bluetooth.connect.g1.a.b(i2));
                    }
                    if (i2 == 0 || !z2 || z3 || j0.this.x == null || !j0.this.x.i()) {
                        boolean unused = j0.this.p = true;
                        j0.this.f1054g.clear();
                        Deque unused2 = j0.this.f1055h = null;
                        boolean unused3 = j0.this.o = false;
                        boolean f2 = j0.this.n;
                        j0.this.a(bluetoothGatt.getDevice());
                        int i4 = -1;
                        if (!(j0.this.y == null || j0.this.y.c == Request.Type.DISCONNECT || j0.this.y.c == Request.Type.REMOVE_BOND)) {
                            j0.this.y.b(bluetoothGatt.getDevice(), i2 == 0 ? -1 : i2);
                            Request unused4 = j0.this.y = null;
                        }
                        if (j0.this.C != null) {
                            j0.this.C.b(bluetoothGatt.getDevice(), -1);
                            f0 unused5 = j0.this.C = null;
                        }
                        if (j0.this.x != null) {
                            if (j0.this.f1057j) {
                                i4 = -2;
                            } else if (i2 != 0) {
                                i4 = (i2 != 133 || !z3) ? i2 : -5;
                            }
                            j0.this.x.b(bluetoothGatt.getDevice(), i4);
                            n0 unused6 = j0.this.x = null;
                        }
                        boolean unused7 = j0.this.p = false;
                        if (!f2 || !j0.this.r) {
                            boolean unused8 = j0.this.r = false;
                            j0.this.b(false);
                        } else {
                            boolean unused9 = j0.this.a(bluetoothGatt.getDevice(), (n0) null);
                        }
                        if (f2 || i2 == 0) {
                            return;
                        }
                    } else {
                        int l = j0.this.x.l();
                        if (l > 0) {
                            j0 j0Var3 = j0.this;
                            j0Var3.a(3, "wait(" + l + ")");
                        }
                        j0.this.f1053f.postDelayed(new e(this, bluetoothGatt), (long) l);
                        return;
                    }
                } else if (i2 != 0) {
                    j0 j0Var4 = j0.this;
                    j0Var4.a(6, "Error (0x" + Integer.toHexString(i2) + "): " + com.android.chileaf.bluetooth.connect.g1.a.b(i2));
                }
                j0.this.d.c.a(bluetoothGatt.getDevice(), "Error on connection state change", i2);
            } else if (j0.this.b == null) {
                Log.e("BleManager", "Device received notification after disconnection.");
                j0.this.a(3, "gatt.close()");
                try {
                    bluetoothGatt.close();
                } catch (Throwable unused10) {
                }
            } else {
                j0 j0Var5 = j0.this;
                j0Var5.a(4, "Connected to " + bluetoothGatt.getDevice().getAddress());
                boolean unused11 = j0.this.n = true;
                long unused12 = j0.this.l = 0;
                int unused13 = j0.this.s = 2;
                j0.this.d.c.h(bluetoothGatt.getDevice());
                if (!j0.this.k) {
                    if (bluetoothGatt.getDevice().getBondState() != 12) {
                        z = false;
                    }
                    int a2 = j0.this.d.a(z);
                    if (a2 > 0) {
                        j0 j0Var6 = j0.this;
                        j0Var6.a(3, "wait(" + a2 + ")");
                    }
                    j0.this.f1053f.postDelayed(new f(this, j0.i(j0.this), bluetoothGatt), (long) a2);
                }
            }
        }

        public void onDescriptorRead(BluetoothGatt bluetoothGatt, BluetoothGattDescriptor bluetoothGattDescriptor, int i2) {
            byte[] value = bluetoothGattDescriptor.getValue();
            if (i2 == 0) {
                j0 j0Var = j0.this;
                j0Var.a(4, "Read Response received from descr. " + bluetoothGattDescriptor.getUuid() + ", value: " + com.android.chileaf.bluetooth.connect.h1.a.a(value));
                j0.this.a(bluetoothGatt, bluetoothGattDescriptor);
                if (j0.this.y instanceof s0) {
                    s0 s0Var = (s0) j0.this.y;
                    s0Var.b(bluetoothGatt.getDevice(), value);
                    if (s0Var.i()) {
                        j0.this.b((Request) s0Var);
                    } else {
                        s0Var.d(bluetoothGatt.getDevice());
                    }
                }
            } else if (i2 == 5 || i2 == 8 || i2 == 137) {
                j0 j0Var2 = j0.this;
                j0Var2.a(5, "Authentication required (" + i2 + ")");
                if (bluetoothGatt.getDevice().getBondState() != 10) {
                    Log.w("BleManager", "Phone has lost bonding information");
                    j0.this.d.c.a(bluetoothGatt.getDevice(), "Phone has lost bonding information", i2);
                    return;
                }
                return;
            } else {
                Log.e("BleManager", "onDescriptorRead error " + i2);
                if (j0.this.y instanceof s0) {
                    j0.this.y.b(bluetoothGatt.getDevice(), i2);
                }
                f0 unused = j0.this.C = null;
                j0.this.a(bluetoothGatt.getDevice(), "Error on reading descriptor", i2);
            }
            boolean unused2 = j0.this.l();
            j0.this.b(true);
        }

        public void onDescriptorWrite(BluetoothGatt bluetoothGatt, BluetoothGattDescriptor bluetoothGattDescriptor, int i2) {
            byte[] value = bluetoothGattDescriptor.getValue();
            if (i2 == 0) {
                j0 j0Var = j0.this;
                j0Var.a(4, "Data written to descr. " + bluetoothGattDescriptor.getUuid() + ", value: " + com.android.chileaf.bluetooth.connect.h1.a.a(value));
                if (j0.this.e(bluetoothGattDescriptor)) {
                    j0.this.a(4, "Service Changed notifications enabled");
                } else if (!j0.this.d(bluetoothGattDescriptor)) {
                    j0.this.b(bluetoothGatt, bluetoothGattDescriptor);
                } else if (value != null && value.length == 2 && value[1] == 0) {
                    byte b = value[0];
                    if (b == 0) {
                        j0.this.a(4, "Notifications and indications disabled");
                    } else if (b == 1) {
                        j0.this.a(4, "Notifications enabled");
                    } else if (b == 2) {
                        j0.this.a(4, "Indications enabled");
                    }
                    j0.this.b(bluetoothGatt, bluetoothGattDescriptor);
                }
                if (j0.this.y instanceof e1) {
                    e1 e1Var = (e1) j0.this.y;
                    if (!e1Var.b(bluetoothGatt.getDevice(), value)) {
                        v0 unused = j0.this.z;
                    }
                    if (e1Var.j()) {
                        j0.this.b((Request) e1Var);
                    } else {
                        e1Var.d(bluetoothGatt.getDevice());
                    }
                }
            } else if (i2 == 5 || i2 == 8 || i2 == 137) {
                j0 j0Var2 = j0.this;
                j0Var2.a(5, "Authentication required (" + i2 + ")");
                if (bluetoothGatt.getDevice().getBondState() != 10) {
                    Log.w("BleManager", "Phone has lost bonding information");
                    j0.this.d.c.a(bluetoothGatt.getDevice(), "Phone has lost bonding information", i2);
                    return;
                }
                return;
            } else {
                Log.e("BleManager", "onDescriptorWrite error " + i2);
                if (j0.this.y instanceof e1) {
                    j0.this.y.b(bluetoothGatt.getDevice(), i2);
                    v0 unused2 = j0.this.z;
                }
                f0 unused3 = j0.this.C = null;
                j0.this.a(bluetoothGatt.getDevice(), "Error on writing descriptor", i2);
            }
            boolean unused4 = j0.this.l();
            j0.this.b(true);
        }

        public final void onMtuChanged(BluetoothGatt bluetoothGatt, int i2, int i3) {
            if (i3 == 0) {
                j0 j0Var = j0.this;
                j0Var.a(4, "MTU changed to: " + i2);
                int unused = j0.this.u = i2;
                j0.this.b(bluetoothGatt, i2);
                if (j0.this.y instanceof q0) {
                    ((q0) j0.this.y).d(bluetoothGatt.getDevice(), i2);
                    j0.this.y.d(bluetoothGatt.getDevice());
                }
            } else {
                Log.e("BleManager", "onMtuChanged error: " + i3 + ", mtu: " + i2);
                if (j0.this.y instanceof q0) {
                    j0.this.y.b(bluetoothGatt.getDevice(), i3);
                    f0 unused2 = j0.this.C = null;
                }
                j0.this.a(bluetoothGatt.getDevice(), "Error on mtu request", i3);
            }
            boolean unused3 = j0.this.l();
            j0.this.b(true);
        }

        public final void onPhyRead(BluetoothGatt bluetoothGatt, int i2, int i3, int i4) {
            if (i4 == 0) {
                j0 j0Var = j0.this;
                j0Var.a(4, "PHY read (TX: " + com.android.chileaf.bluetooth.connect.h1.a.e(i2) + ", RX: " + com.android.chileaf.bluetooth.connect.h1.a.e(i3) + ")");
                if (j0.this.y instanceof r0) {
                    ((r0) j0.this.y).b(bluetoothGatt.getDevice(), i2, i3);
                    j0.this.y.d(bluetoothGatt.getDevice());
                }
            } else {
                j0 j0Var2 = j0.this;
                j0Var2.a(5, "PHY read failed with status " + i4);
                if (j0.this.y instanceof r0) {
                    j0.this.y.b(bluetoothGatt.getDevice(), i4);
                }
                f0 unused = j0.this.C = null;
                j0.this.d.c.a(bluetoothGatt.getDevice(), "Error on PHY read", i4);
            }
            boolean unused2 = j0.this.l();
            j0.this.b(true);
        }

        public final void onPhyUpdate(BluetoothGatt bluetoothGatt, int i2, int i3, int i4) {
            if (i4 == 0) {
                j0 j0Var = j0.this;
                j0Var.a(4, "PHY updated (TX: " + com.android.chileaf.bluetooth.connect.h1.a.e(i2) + ", RX: " + com.android.chileaf.bluetooth.connect.h1.a.e(i3) + ")");
                if (j0.this.y instanceof r0) {
                    ((r0) j0.this.y).b(bluetoothGatt.getDevice(), i2, i3);
                    j0.this.y.d(bluetoothGatt.getDevice());
                }
            } else {
                j0 j0Var2 = j0.this;
                j0Var2.a(5, "PHY updated failed with status " + i4);
                if (j0.this.y instanceof r0) {
                    j0.this.y.b(bluetoothGatt.getDevice(), i4);
                    f0 unused = j0.this.C = null;
                }
                j0.this.d.c.a(bluetoothGatt.getDevice(), "Error on PHY update", i4);
            }
            if (j0.this.l() || (j0.this.y instanceof r0)) {
                j0.this.b(true);
            }
        }

        public final void onReadRemoteRssi(BluetoothGatt bluetoothGatt, int i2, int i3) {
            if (i3 == 0) {
                j0 j0Var = j0.this;
                j0Var.a(4, "Remote RSSI received: " + i2 + " dBm");
                if (j0.this.y instanceof t0) {
                    ((t0) j0.this.y).d(bluetoothGatt.getDevice(), i2);
                    j0.this.y.d(bluetoothGatt.getDevice());
                }
            } else {
                j0 j0Var2 = j0.this;
                j0Var2.a(5, "Reading remote RSSI failed with status " + i3);
                if (j0.this.y instanceof t0) {
                    j0.this.y.b(bluetoothGatt.getDevice(), i3);
                }
                f0 unused = j0.this.C = null;
                j0.this.d.c.a(bluetoothGatt.getDevice(), "Error on RSSI read", i3);
            }
            boolean unused2 = j0.this.l();
            j0.this.b(true);
        }

        public final void onReliableWriteCompleted(BluetoothGatt bluetoothGatt, int i2) {
            boolean z = j0.this.y.c == Request.Type.EXECUTE_RELIABLE_WRITE;
            boolean unused = j0.this.t = false;
            if (i2 != 0) {
                Log.e("BleManager", "onReliableWriteCompleted execute " + z + ", error " + i2);
                j0.this.y.b(bluetoothGatt.getDevice(), i2);
                j0.this.a(bluetoothGatt.getDevice(), "Error on Execute Reliable Write", i2);
            } else if (z) {
                j0.this.a(4, "Reliable Write executed");
                j0.this.y.d(bluetoothGatt.getDevice());
            } else {
                j0.this.a(5, "Reliable Write aborted");
                j0.this.y.d(bluetoothGatt.getDevice());
                j0.this.z.b(bluetoothGatt.getDevice(), -4);
            }
            boolean unused2 = j0.this.l();
            j0.this.b(true);
        }

        public final void onServicesDiscovered(BluetoothGatt bluetoothGatt, int i2) {
            BluetoothGattServer a2;
            boolean unused = j0.this.k = false;
            if (i2 == 0) {
                j0.this.a(4, "Services discovered");
                boolean unused2 = j0.this.f1057j = true;
                if (j0.this.c(bluetoothGatt)) {
                    j0.this.a(2, "Primary service found");
                    boolean b = j0.this.b(bluetoothGatt);
                    if (b) {
                        j0.this.a(2, "Secondary service found");
                    }
                    j0.this.d.c.b(bluetoothGatt.getDevice(), b);
                    if (!(j0.this.e == null || (a2 = j0.this.e.a()) == null)) {
                        for (BluetoothGattService characteristics : a2.getServices()) {
                            for (BluetoothGattCharacteristic next : characteristics.getCharacteristics()) {
                                if (!j0.this.e.a(next)) {
                                    if (j0.this.v == null) {
                                        Map unused3 = j0.this.v = new HashMap();
                                    }
                                    j0.this.v.put(next, next.getValue());
                                }
                                for (BluetoothGattDescriptor next2 : next.getDescriptors()) {
                                    if (!j0.this.e.a(next2)) {
                                        if (j0.this.w == null) {
                                            Map unused4 = j0.this.w = new HashMap();
                                        }
                                        j0.this.w.put(next2, next2.getValue());
                                    }
                                }
                            }
                        }
                        j0.this.a(a2);
                    }
                    boolean unused5 = j0.this.f1056i = true;
                    boolean unused6 = j0.this.p = true;
                    j0 j0Var = j0.this;
                    Deque unused7 = j0Var.f1055h = j0Var.a(bluetoothGatt);
                    boolean z = j0.this.f1055h != null;
                    if (z) {
                        for (Request request : j0.this.f1055h) {
                            request.m = true;
                        }
                    }
                    if (j0.this.f1055h == null) {
                        Deque unused8 = j0.this.f1055h = new LinkedBlockingDeque();
                    }
                    int i3 = Build.VERSION.SDK_INT;
                    if (i3 < 23 || i3 == 26 || i3 == 27 || i3 == 28) {
                        j0 j0Var2 = j0.this;
                        e1 f2 = Request.f();
                        f2.a((u0) j0.this);
                        j0Var2.b((Request) f2);
                    }
                    if (z) {
                        j0.this.d.f();
                        if (j0.this.d.c.j(bluetoothGatt.getDevice())) {
                            j0.this.d.b();
                        }
                    }
                    j0.this.e();
                    boolean unused9 = j0.this.f1056i = false;
                    j0.this.b(true);
                    return;
                }
                j0.this.a(5, "Device is not supported");
                j0.this.d.c.c(bluetoothGatt.getDevice());
                boolean unused10 = j0.this.q();
                return;
            }
            Log.e("BleManager", "onServicesDiscovered error " + i2);
            j0.this.a(bluetoothGatt.getDevice(), "Error on discovering services", i2);
            if (j0.this.x != null) {
                j0.this.x.b(bluetoothGatt.getDevice(), -4);
                n0 unused11 = j0.this.x = null;
            }
            boolean unused12 = j0.this.q();
        }

        public /* synthetic */ void a(BluetoothGatt bluetoothGatt) {
            boolean unused = j0.this.a(bluetoothGatt.getDevice(), j0.this.x);
        }
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Can't wrap try/catch for region: R(7:8|(2:10|(1:12)(1:13))|14|15|16|17|18) */
    /* JADX WARNING: Missing exception handler attribute for start block: B:17:0x003e */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void b() {
        /*
            r4 = this;
            com.android.chileaf.bluetooth.connect.g0 r0 = r4.d     // Catch:{ Exception -> 0x0010 }
            android.content.Context r0 = r0.c()     // Catch:{ Exception -> 0x0010 }
            android.content.BroadcastReceiver r1 = r4.D     // Catch:{ Exception -> 0x0010 }
            r0.unregisterReceiver(r1)     // Catch:{ Exception -> 0x0010 }
            android.content.BroadcastReceiver r1 = r4.E     // Catch:{ Exception -> 0x0010 }
            r0.unregisterReceiver(r1)     // Catch:{ Exception -> 0x0010 }
        L_0x0010:
            java.lang.Object r0 = r4.a
            monitor-enter(r0)
            android.bluetooth.BluetoothGatt r1 = r4.c     // Catch:{ all -> 0x0055 }
            r2 = 0
            if (r1 == 0) goto L_0x0040
            com.android.chileaf.bluetooth.connect.g0 r1 = r4.d     // Catch:{ all -> 0x0055 }
            boolean r1 = r1.i()     // Catch:{ all -> 0x0055 }
            if (r1 == 0) goto L_0x0033
            boolean r1 = r4.v()     // Catch:{ all -> 0x0055 }
            if (r1 == 0) goto L_0x002d
            r1 = 4
            java.lang.String r3 = "Cache refreshed"
            r4.a((int) r1, (java.lang.String) r3)     // Catch:{ all -> 0x0055 }
            goto L_0x0033
        L_0x002d:
            r1 = 5
            java.lang.String r3 = "Refreshing failed"
            r4.a((int) r1, (java.lang.String) r3)     // Catch:{ all -> 0x0055 }
        L_0x0033:
            r1 = 3
            java.lang.String r3 = "gatt.close()"
            r4.a((int) r1, (java.lang.String) r3)     // Catch:{ all -> 0x0055 }
            android.bluetooth.BluetoothGatt r1 = r4.c     // Catch:{ all -> 0x003e }
            r1.close()     // Catch:{ all -> 0x003e }
        L_0x003e:
            r4.c = r2     // Catch:{ all -> 0x0055 }
        L_0x0040:
            r1 = 0
            r4.t = r1     // Catch:{ all -> 0x0055 }
            r4.r = r1     // Catch:{ all -> 0x0055 }
            java.util.HashMap<java.lang.Object, com.android.chileaf.bluetooth.connect.c1> r1 = r4.A     // Catch:{ all -> 0x0055 }
            r1.clear()     // Catch:{ all -> 0x0055 }
            java.util.Deque<com.android.chileaf.bluetooth.connect.Request> r1 = r4.f1054g     // Catch:{ all -> 0x0055 }
            r1.clear()     // Catch:{ all -> 0x0055 }
            r4.f1055h = r2     // Catch:{ all -> 0x0055 }
            r4.b = r2     // Catch:{ all -> 0x0055 }
            monitor-exit(r0)     // Catch:{ all -> 0x0055 }
            return
        L_0x0055:
            r1 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x0055 }
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.chileaf.bluetooth.connect.j0.b():void");
    }

    /* access modifiers changed from: private */
    public boolean e(BluetoothGattDescriptor bluetoothGattDescriptor) {
        return bluetoothGattDescriptor != null && g0.f1050i.equals(bluetoothGattDescriptor.getCharacteristic().getUuid());
    }

    /* access modifiers changed from: package-private */
    public final boolean f() {
        return this.n;
    }

    private boolean c(BluetoothGattDescriptor bluetoothGattDescriptor) {
        BluetoothGatt bluetoothGatt = this.c;
        if (bluetoothGatt == null || bluetoothGattDescriptor == null || !this.n) {
            return false;
        }
        BluetoothGattCharacteristic characteristic = bluetoothGattDescriptor.getCharacteristic();
        int writeType = characteristic.getWriteType();
        characteristic.setWriteType(2);
        boolean writeDescriptor = bluetoothGatt.writeDescriptor(bluetoothGattDescriptor);
        characteristic.setWriteType(writeType);
        return writeDescriptor;
    }

    /* access modifiers changed from: private */
    public boolean d(BluetoothGattDescriptor bluetoothGattDescriptor) {
        return bluetoothGattDescriptor != null && g0.e.equals(bluetoothGattDescriptor.getUuid());
    }

    /* access modifiers changed from: package-private */
    public void a(g0 g0Var, Handler handler) {
        this.d = g0Var;
        this.f1053f = handler;
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Can't wrap try/catch for region: R(8:10|11|12|13|14|15|16|17) */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x0074, code lost:
        if (r12 == null) goto L_0x0076;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x0076, code lost:
        return false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x0077, code lost:
        r0 = r12.n();
        r10.q = !r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x007f, code lost:
        if (r0 != false) goto L_0x0081;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x0081, code lost:
        r10.r = true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x0083, code lost:
        r10.b = r11;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x0089, code lost:
        if (r12.m() != false) goto L_0x008b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x008b, code lost:
        r0 = "Connecting...";
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x008e, code lost:
        r0 = "Retrying...";
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x0090, code lost:
        a(2, r0);
        r10.d.c.d(r11);
        r10.l = android.os.SystemClock.elapsedRealtime();
        r0 = android.os.Build.VERSION.SDK_INT;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x00a4, code lost:
        if (r0 >= 26) goto L_0x00a6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x00a6, code lost:
        r9 = r12.k();
        a(3, "gatt = device.connectGatt(autoConnect = false, TRANSPORT_LE, " + com.android.chileaf.bluetooth.connect.h1.a.d(r9) + ")");
        r10.c = r11.connectGatt(r5, false, r10.F, 2, r9);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:0x00d5, code lost:
        if (r0 >= 23) goto L_0x00d7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:0x00d7, code lost:
        a(3, "gatt = device.connectGatt(autoConnect = false, TRANSPORT_LE)");
        r10.c = r11.connectGatt(r5, false, r10.F, 2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:0x00e5, code lost:
        a(3, "gatt = device.connectGatt(autoConnect = false)");
        r10.c = r11.connectGatt(r5, false, r10.F);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x00f2, code lost:
        return true;
     */
    /* JADX WARNING: Missing exception handler attribute for start block: B:13:0x0030 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean a(android.bluetooth.BluetoothDevice r11, com.android.chileaf.bluetooth.connect.n0 r12) {
        /*
            r10 = this;
            android.bluetooth.BluetoothAdapter r0 = android.bluetooth.BluetoothAdapter.getDefaultAdapter()
            boolean r0 = r0.isEnabled()
            boolean r1 = r10.n
            r2 = 0
            r3 = 1
            if (r1 != 0) goto L_0x00f6
            if (r0 != 0) goto L_0x0012
            goto L_0x00f6
        L_0x0012:
            com.android.chileaf.bluetooth.connect.g0 r0 = r10.d
            android.content.Context r5 = r0.c()
            java.lang.Object r1 = r10.a
            monitor-enter(r1)
            android.bluetooth.BluetoothGatt r0 = r10.c     // Catch:{ all -> 0x00f3 }
            r4 = 2
            r6 = 0
            r7 = 3
            if (r0 == 0) goto L_0x005b
            boolean r0 = r10.r     // Catch:{ all -> 0x00f3 }
            if (r0 != 0) goto L_0x003d
            java.lang.String r0 = "gatt.close()"
            r10.a((int) r7, (java.lang.String) r0)     // Catch:{ all -> 0x00f3 }
            android.bluetooth.BluetoothGatt r0 = r10.c     // Catch:{ all -> 0x0030 }
            r0.close()     // Catch:{ all -> 0x0030 }
        L_0x0030:
            r10.c = r2     // Catch:{ all -> 0x00f3 }
            java.lang.String r0 = "wait(200)"
            r10.a((int) r7, (java.lang.String) r0)     // Catch:{ InterruptedException -> 0x0073 }
            r8 = 200(0xc8, double:9.9E-322)
            java.lang.Thread.sleep(r8)     // Catch:{ InterruptedException -> 0x0073 }
            goto L_0x0073
        L_0x003d:
            r10.r = r6     // Catch:{ all -> 0x00f3 }
            r5 = 0
            r10.l = r5     // Catch:{ all -> 0x00f3 }
            java.lang.String r12 = "Connecting..."
            r10.a((int) r4, (java.lang.String) r12)     // Catch:{ all -> 0x00f3 }
            com.android.chileaf.bluetooth.connect.g0 r12 = r10.d     // Catch:{ all -> 0x00f3 }
            E r12 = r12.c     // Catch:{ all -> 0x00f3 }
            r12.d(r11)     // Catch:{ all -> 0x00f3 }
            java.lang.String r11 = "gatt.connect()"
            r10.a((int) r7, (java.lang.String) r11)     // Catch:{ all -> 0x00f3 }
            android.bluetooth.BluetoothGatt r11 = r10.c     // Catch:{ all -> 0x00f3 }
            r11.connect()     // Catch:{ all -> 0x00f3 }
            monitor-exit(r1)     // Catch:{ all -> 0x00f3 }
            return r3
        L_0x005b:
            android.content.BroadcastReceiver r0 = r10.D     // Catch:{ all -> 0x00f3 }
            android.content.IntentFilter r2 = new android.content.IntentFilter     // Catch:{ all -> 0x00f3 }
            java.lang.String r8 = "android.bluetooth.adapter.action.STATE_CHANGED"
            r2.<init>(r8)     // Catch:{ all -> 0x00f3 }
            r5.registerReceiver(r0, r2)     // Catch:{ all -> 0x00f3 }
            android.content.BroadcastReceiver r0 = r10.E     // Catch:{ all -> 0x00f3 }
            android.content.IntentFilter r2 = new android.content.IntentFilter     // Catch:{ all -> 0x00f3 }
            java.lang.String r8 = "android.bluetooth.device.action.BOND_STATE_CHANGED"
            r2.<init>(r8)     // Catch:{ all -> 0x00f3 }
            r5.registerReceiver(r0, r2)     // Catch:{ all -> 0x00f3 }
        L_0x0073:
            monitor-exit(r1)     // Catch:{ all -> 0x00f3 }
            if (r12 != 0) goto L_0x0077
            return r6
        L_0x0077:
            boolean r0 = r12.n()
            r1 = r0 ^ 1
            r10.q = r1
            if (r0 == 0) goto L_0x0083
            r10.r = r3
        L_0x0083:
            r10.b = r11
            boolean r0 = r12.m()
            if (r0 == 0) goto L_0x008e
            java.lang.String r0 = "Connecting..."
            goto L_0x0090
        L_0x008e:
            java.lang.String r0 = "Retrying..."
        L_0x0090:
            r10.a((int) r4, (java.lang.String) r0)
            com.android.chileaf.bluetooth.connect.g0 r0 = r10.d
            E r0 = r0.c
            r0.d(r11)
            long r0 = android.os.SystemClock.elapsedRealtime()
            r10.l = r0
            int r0 = android.os.Build.VERSION.SDK_INT
            r1 = 26
            if (r0 < r1) goto L_0x00d3
            int r9 = r12.k()
            java.lang.StringBuilder r12 = new java.lang.StringBuilder
            r12.<init>()
            java.lang.String r0 = "gatt = device.connectGatt(autoConnect = false, TRANSPORT_LE, "
            r12.append(r0)
            java.lang.String r0 = com.android.chileaf.bluetooth.connect.h1.a.d(r9)
            r12.append(r0)
            java.lang.String r0 = ")"
            r12.append(r0)
            java.lang.String r12 = r12.toString()
            r10.a((int) r7, (java.lang.String) r12)
            r6 = 0
            android.bluetooth.BluetoothGattCallback r7 = r10.F
            r8 = 2
            r4 = r11
            android.bluetooth.BluetoothGatt r11 = r4.connectGatt(r5, r6, r7, r8, r9)
            r10.c = r11
            goto L_0x00f2
        L_0x00d3:
            r12 = 23
            if (r0 < r12) goto L_0x00e5
            java.lang.String r12 = "gatt = device.connectGatt(autoConnect = false, TRANSPORT_LE)"
            r10.a((int) r7, (java.lang.String) r12)
            android.bluetooth.BluetoothGattCallback r12 = r10.F
            android.bluetooth.BluetoothGatt r11 = r11.connectGatt(r5, r6, r12, r4)
            r10.c = r11
            goto L_0x00f2
        L_0x00e5:
            java.lang.String r12 = "gatt = device.connectGatt(autoConnect = false)"
            r10.a((int) r7, (java.lang.String) r12)
            android.bluetooth.BluetoothGattCallback r12 = r10.F
            android.bluetooth.BluetoothGatt r11 = r11.connectGatt(r5, r6, r12)
            r10.c = r11
        L_0x00f2:
            return r3
        L_0x00f3:
            r11 = move-exception
            monitor-exit(r1)     // Catch:{ all -> 0x00f3 }
            throw r11
        L_0x00f6:
            android.bluetooth.BluetoothDevice r12 = r10.b
            if (r0 == 0) goto L_0x0108
            if (r12 == 0) goto L_0x0108
            boolean r12 = r12.equals(r11)
            if (r12 == 0) goto L_0x0108
            com.android.chileaf.bluetooth.connect.n0 r12 = r10.x
            r12.d(r11)
            goto L_0x0115
        L_0x0108:
            com.android.chileaf.bluetooth.connect.n0 r12 = r10.x
            if (r12 == 0) goto L_0x0115
            if (r0 == 0) goto L_0x0110
            r0 = -4
            goto L_0x0112
        L_0x0110:
            r0 = -100
        L_0x0112:
            r12.b(r11, r0)
        L_0x0115:
            r10.x = r2
            r10.b((boolean) r3)
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.chileaf.bluetooth.connect.j0.a(android.bluetooth.BluetoothDevice, com.android.chileaf.bluetooth.connect.n0):boolean");
    }

    /* access modifiers changed from: package-private */
    @Deprecated
    public com.android.chileaf.bluetooth.connect.f1.b c() {
        return new j(this);
    }

    private boolean b(BluetoothGattCharacteristic bluetoothGattCharacteristic) {
        BluetoothGattDescriptor a2;
        BluetoothGatt bluetoothGatt = this.c;
        if (bluetoothGatt == null || bluetoothGattCharacteristic == null || !this.n || (a2 = a(bluetoothGattCharacteristic, 16)) == null) {
            return false;
        }
        a(3, "gatt.setCharacteristicNotification(" + bluetoothGattCharacteristic.getUuid() + ", false)");
        bluetoothGatt.setCharacteristicNotification(bluetoothGattCharacteristic, false);
        a2.setValue(BluetoothGattDescriptor.DISABLE_NOTIFICATION_VALUE);
        a(2, "Disabling notifications and indications for " + bluetoothGattCharacteristic.getUuid());
        a(3, "gatt.writeDescriptor(" + g0.e + ", value=0x00-00)");
        return c(a2);
    }

    private boolean b(BluetoothGattDescriptor bluetoothGattDescriptor) {
        if (this.c == null || bluetoothGattDescriptor == null || !this.n) {
            return false;
        }
        a(2, "Writing descriptor " + bluetoothGattDescriptor.getUuid());
        a(3, "gatt.writeDescriptor(" + bluetoothGattDescriptor.getUuid() + ")");
        return c(bluetoothGattDescriptor);
    }

    private boolean b(int i2) {
        BluetoothGatt bluetoothGatt = this.c;
        if (bluetoothGatt == null || !this.n) {
            return false;
        }
        a(2, "Requesting new MTU...");
        a(3, "gatt.requestMtu(" + i2 + ")");
        return bluetoothGatt.requestMtu(i2);
    }

    public /* synthetic */ void b(BluetoothDevice bluetoothDevice, Data data) {
        if (data.b() == 1) {
            int intValue = data.a(17, 0).intValue();
            a(this.c, intValue);
            this.d.c.d(bluetoothDevice, intValue);
        }
    }

    /* access modifiers changed from: package-private */
    public final void b(Request request) {
        (this.f1056i ? this.f1055h : this.f1054g).addFirst(request);
        request.m = true;
    }

    private void b(BluetoothDevice bluetoothDevice) {
        Request request = this.y;
        if (request instanceof e1) {
            e1 e1Var = (e1) request;
            int i2 = d.a[e1Var.c.ordinal()];
            if (i2 == 1) {
                a(4, "[Server] Notification sent");
            } else if (i2 == 2) {
                a(4, "[Server] Indication sent");
            }
            e1Var.b(bluetoothDevice, e1Var.d.getValue());
            if (e1Var.j()) {
                b((Request) e1Var);
            } else {
                e1Var.d(bluetoothDevice);
            }
        }
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Can't wrap try/catch for region: R(9:29|30|(2:32|(1:34))|35|36|37|38|39|40) */
    /* JADX WARNING: Code restructure failed: missing block: B:210:0x03a2, code lost:
        return;
     */
    /* JADX WARNING: Missing exception handler attribute for start block: B:37:0x007a */
    /* JADX WARNING: Removed duplicated region for block: B:191:0x0354  */
    /* JADX WARNING: Removed duplicated region for block: B:198:0x037a  */
    /* JADX WARNING: Removed duplicated region for block: B:201:0x0381  */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x0038 A[Catch:{ Exception -> 0x0045 }] */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x0048 A[SYNTHETIC, Splitter:B:29:0x0048] */
    /* JADX WARNING: Removed duplicated region for block: B:44:0x008d  */
    /* JADX WARNING: Removed duplicated region for block: B:79:0x00fb  */
    /* JADX WARNING: Removed duplicated region for block: B:82:0x0102  */
    /* JADX WARNING: Removed duplicated region for block: B:83:0x010d  */
    /* JADX WARNING: Unknown top exception splitter block from list: {B:41:0x0083=Splitter:B:41:0x0083, B:75:0x00e7=Splitter:B:75:0x00e7, B:37:0x007a=Splitter:B:37:0x007a} */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void b(boolean r10) {
        /*
            r9 = this;
            monitor-enter(r9)
            r0 = 0
            r1 = 1
            if (r10 == 0) goto L_0x000e
            com.android.chileaf.bluetooth.connect.f0 r10 = r9.C     // Catch:{ all -> 0x03ad }
            if (r10 == 0) goto L_0x000b
            r10 = 1
            goto L_0x000c
        L_0x000b:
            r10 = 0
        L_0x000c:
            r9.p = r10     // Catch:{ all -> 0x03ad }
        L_0x000e:
            boolean r10 = r9.p     // Catch:{ all -> 0x03ad }
            if (r10 == 0) goto L_0x0014
            monitor-exit(r9)
            return
        L_0x0014:
            r10 = 0
            com.android.chileaf.bluetooth.connect.v0 r2 = r9.z     // Catch:{ Exception -> 0x0045 }
            if (r2 == 0) goto L_0x0035
            com.android.chileaf.bluetooth.connect.v0 r2 = r9.z     // Catch:{ Exception -> 0x0045 }
            boolean r2 = r2.j()     // Catch:{ Exception -> 0x0045 }
            if (r2 == 0) goto L_0x002c
            com.android.chileaf.bluetooth.connect.v0 r2 = r9.z     // Catch:{ Exception -> 0x0045 }
            com.android.chileaf.bluetooth.connect.Request r2 = r2.i()     // Catch:{ Exception -> 0x0045 }
            com.android.chileaf.bluetooth.connect.Request r2 = r2.a((com.android.chileaf.bluetooth.connect.u0) r9)     // Catch:{ Exception -> 0x0045 }
            goto L_0x0036
        L_0x002c:
            com.android.chileaf.bluetooth.connect.v0 r2 = r9.z     // Catch:{ Exception -> 0x0045 }
            android.bluetooth.BluetoothDevice r3 = r9.b     // Catch:{ Exception -> 0x0045 }
            r2.d(r3)     // Catch:{ Exception -> 0x0045 }
            r9.z = r10     // Catch:{ Exception -> 0x0045 }
        L_0x0035:
            r2 = r10
        L_0x0036:
            if (r2 != 0) goto L_0x0046
            java.util.Deque<com.android.chileaf.bluetooth.connect.Request> r2 = r9.f1055h     // Catch:{ Exception -> 0x0045 }
            if (r2 == 0) goto L_0x0045
            java.util.Deque<com.android.chileaf.bluetooth.connect.Request> r2 = r9.f1055h     // Catch:{ Exception -> 0x0045 }
            java.lang.Object r2 = r2.poll()     // Catch:{ Exception -> 0x0045 }
            com.android.chileaf.bluetooth.connect.Request r2 = (com.android.chileaf.bluetooth.connect.Request) r2     // Catch:{ Exception -> 0x0045 }
            goto L_0x0046
        L_0x0045:
            r2 = r10
        L_0x0046:
            if (r2 != 0) goto L_0x0083
            java.util.Deque<com.android.chileaf.bluetooth.connect.Request> r2 = r9.f1055h     // Catch:{ all -> 0x03ad }
            if (r2 == 0) goto L_0x0071
            r9.f1055h = r10     // Catch:{ all -> 0x03ad }
            r9.p = r1     // Catch:{ all -> 0x03ad }
            r9.i()     // Catch:{ all -> 0x03ad }
            com.android.chileaf.bluetooth.connect.g0 r2 = r9.d     // Catch:{ all -> 0x03ad }
            E r2 = r2.c     // Catch:{ all -> 0x03ad }
            android.bluetooth.BluetoothGatt r3 = r9.c     // Catch:{ all -> 0x03ad }
            android.bluetooth.BluetoothDevice r3 = r3.getDevice()     // Catch:{ all -> 0x03ad }
            r2.b(r3)     // Catch:{ all -> 0x03ad }
            com.android.chileaf.bluetooth.connect.n0 r2 = r9.x     // Catch:{ all -> 0x03ad }
            if (r2 == 0) goto L_0x0071
            com.android.chileaf.bluetooth.connect.n0 r2 = r9.x     // Catch:{ all -> 0x03ad }
            com.android.chileaf.bluetooth.connect.n0 r3 = r9.x     // Catch:{ all -> 0x03ad }
            android.bluetooth.BluetoothDevice r3 = r3.j()     // Catch:{ all -> 0x03ad }
            r2.d(r3)     // Catch:{ all -> 0x03ad }
            r9.x = r10     // Catch:{ all -> 0x03ad }
        L_0x0071:
            java.util.Deque<com.android.chileaf.bluetooth.connect.Request> r2 = r9.f1054g     // Catch:{ Exception -> 0x007a }
            java.lang.Object r2 = r2.remove()     // Catch:{ Exception -> 0x007a }
            com.android.chileaf.bluetooth.connect.Request r2 = (com.android.chileaf.bluetooth.connect.Request) r2     // Catch:{ Exception -> 0x007a }
            goto L_0x0083
        L_0x007a:
            r9.p = r0     // Catch:{ all -> 0x03ad }
            r9.y = r10     // Catch:{ all -> 0x03ad }
            r9.j()     // Catch:{ all -> 0x03ad }
            monitor-exit(r9)
            return
        L_0x0083:
            r9.p = r1     // Catch:{ all -> 0x03ad }
            r9.y = r2     // Catch:{ all -> 0x03ad }
            boolean r3 = r2 instanceof com.android.chileaf.bluetooth.connect.f0     // Catch:{ all -> 0x03ad }
            r4 = 2
            r5 = 3
            if (r3 == 0) goto L_0x00fb
            r3 = r2
            com.android.chileaf.bluetooth.connect.f0 r3 = (com.android.chileaf.bluetooth.connect.f0) r3     // Catch:{ all -> 0x03ad }
            int[] r6 = com.android.chileaf.bluetooth.connect.j0.d.a     // Catch:{ all -> 0x03ad }
            com.android.chileaf.bluetooth.connect.Request$Type r7 = r2.c     // Catch:{ all -> 0x03ad }
            int r7 = r7.ordinal()     // Catch:{ all -> 0x03ad }
            r6 = r6[r7]     // Catch:{ all -> 0x03ad }
            if (r6 == r5) goto L_0x00af
            r7 = 4
            if (r6 == r7) goto L_0x00ac
            r7 = 5
            if (r6 == r7) goto L_0x00aa
            r7 = 6
            if (r6 == r7) goto L_0x00a7
            r6 = 0
            goto L_0x00b1
        L_0x00a7:
            r6 = 76
            goto L_0x00b1
        L_0x00aa:
            r6 = 2
            goto L_0x00b1
        L_0x00ac:
            r6 = 32
            goto L_0x00b1
        L_0x00af:
            r6 = 16
        L_0x00b1:
            boolean r7 = r9.n     // Catch:{ all -> 0x03ad }
            if (r7 == 0) goto L_0x00c8
            android.bluetooth.BluetoothDevice r7 = r9.b     // Catch:{ all -> 0x03ad }
            if (r7 == 0) goto L_0x00c8
            android.bluetooth.BluetoothGattCharacteristic r7 = r3.d     // Catch:{ all -> 0x03ad }
            if (r7 == 0) goto L_0x00c6
            android.bluetooth.BluetoothGattCharacteristic r7 = r3.d     // Catch:{ all -> 0x03ad }
            int r7 = r7.getProperties()     // Catch:{ all -> 0x03ad }
            r6 = r6 & r7
            if (r6 == 0) goto L_0x00c8
        L_0x00c6:
            r6 = 1
            goto L_0x00c9
        L_0x00c8:
            r6 = 0
        L_0x00c9:
            if (r6 == 0) goto L_0x00fc
            boolean r7 = r3 instanceof com.android.chileaf.bluetooth.connect.m0     // Catch:{ all -> 0x03ad }
            if (r7 == 0) goto L_0x00e7
            r7 = r3
            com.android.chileaf.bluetooth.connect.m0 r7 = (com.android.chileaf.bluetooth.connect.m0) r7     // Catch:{ all -> 0x03ad }
            boolean r8 = r7.l()     // Catch:{ all -> 0x03ad }
            if (r8 == 0) goto L_0x00e7
            android.bluetooth.BluetoothDevice r10 = r9.b     // Catch:{ all -> 0x03ad }
            r7.c(r10)     // Catch:{ all -> 0x03ad }
            android.bluetooth.BluetoothDevice r10 = r9.b     // Catch:{ all -> 0x03ad }
            r7.d(r10)     // Catch:{ all -> 0x03ad }
            r9.b((boolean) r1)     // Catch:{ all -> 0x03ad }
            monitor-exit(r9)
            return
        L_0x00e7:
            r9.C = r3     // Catch:{ all -> 0x03ad }
            com.android.chileaf.bluetooth.connect.Request r7 = r3.i()     // Catch:{ all -> 0x03ad }
            if (r7 == 0) goto L_0x00fc
            android.bluetooth.BluetoothDevice r2 = r9.b     // Catch:{ all -> 0x03ad }
            r3.c(r2)     // Catch:{ all -> 0x03ad }
            com.android.chileaf.bluetooth.connect.Request r2 = r3.i()     // Catch:{ all -> 0x03ad }
            r9.y = r2     // Catch:{ all -> 0x03ad }
            goto L_0x00fc
        L_0x00fb:
            r6 = 0
        L_0x00fc:
            com.android.chileaf.bluetooth.connect.Request$Type r3 = r2.c     // Catch:{ all -> 0x03ad }
            com.android.chileaf.bluetooth.connect.Request$Type r7 = com.android.chileaf.bluetooth.connect.Request.Type.CONNECT     // Catch:{ all -> 0x03ad }
            if (r3 != r7) goto L_0x010d
            r3 = r2
            com.android.chileaf.bluetooth.connect.n0 r3 = (com.android.chileaf.bluetooth.connect.n0) r3     // Catch:{ all -> 0x03ad }
            android.bluetooth.BluetoothDevice r7 = r3.j()     // Catch:{ all -> 0x03ad }
            r3.c(r7)     // Catch:{ all -> 0x03ad }
            goto L_0x0116
        L_0x010d:
            android.bluetooth.BluetoothDevice r3 = r9.b     // Catch:{ all -> 0x03ad }
            if (r3 == 0) goto L_0x03a3
            android.bluetooth.BluetoothDevice r3 = r9.b     // Catch:{ all -> 0x03ad }
            r2.c(r3)     // Catch:{ all -> 0x03ad }
        L_0x0116:
            int[] r3 = com.android.chileaf.bluetooth.connect.j0.d.a     // Catch:{ all -> 0x03ad }
            com.android.chileaf.bluetooth.connect.Request$Type r7 = r2.c     // Catch:{ all -> 0x03ad }
            int r7 = r7.ordinal()     // Catch:{ all -> 0x03ad }
            r3 = r3[r7]     // Catch:{ all -> 0x03ad }
            if (r3 == r1) goto L_0x034d
            if (r3 == r4) goto L_0x034d
            r4 = 21
            r7 = 26
            switch(r3) {
                case 7: goto L_0x033e;
                case 8: goto L_0x0339;
                case 9: goto L_0x0334;
                case 10: goto L_0x032f;
                case 11: goto L_0x0326;
                case 12: goto L_0x031f;
                case 13: goto L_0x0303;
                case 14: goto L_0x02fb;
                case 15: goto L_0x02e5;
                case 16: goto L_0x02ad;
                case 17: goto L_0x0277;
                case 18: goto L_0x0265;
                case 19: goto L_0x025f;
                case 20: goto L_0x0259;
                case 21: goto L_0x0251;
                case 22: goto L_0x0249;
                case 23: goto L_0x0241;
                case 24: goto L_0x0239;
                case 25: goto L_0x0233;
                case 26: goto L_0x022d;
                case 27: goto L_0x0227;
                case 28: goto L_0x0221;
                case 29: goto L_0x01f4;
                case 30: goto L_0x01c6;
                case 31: goto L_0x019b;
                case 32: goto L_0x017c;
                case 33: goto L_0x0176;
                case 34: goto L_0x0160;
                case 35: goto L_0x012d;
                default: goto L_0x012b;
            }     // Catch:{ all -> 0x03ad }
        L_0x012b:
            goto L_0x037f
        L_0x012d:
            android.bluetooth.BluetoothDevice r0 = r9.b     // Catch:{ all -> 0x03ad }
            if (r0 == 0) goto L_0x037f
            com.android.chileaf.bluetooth.connect.z0 r2 = (com.android.chileaf.bluetooth.connect.z0) r2     // Catch:{ all -> 0x03ad }
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x03ad }
            r3.<init>()     // Catch:{ all -> 0x03ad }
            java.lang.String r4 = "sleep("
            r3.append(r4)     // Catch:{ all -> 0x03ad }
            long r6 = r2.i()     // Catch:{ all -> 0x03ad }
            r3.append(r6)     // Catch:{ all -> 0x03ad }
            java.lang.String r4 = ")"
            r3.append(r4)     // Catch:{ all -> 0x03ad }
            java.lang.String r3 = r3.toString()     // Catch:{ all -> 0x03ad }
            r9.a((int) r5, (java.lang.String) r3)     // Catch:{ all -> 0x03ad }
            android.os.Handler r3 = r9.f1053f     // Catch:{ all -> 0x03ad }
            com.android.chileaf.bluetooth.connect.i r4 = new com.android.chileaf.bluetooth.connect.i     // Catch:{ all -> 0x03ad }
            r4.<init>(r9, r2, r0)     // Catch:{ all -> 0x03ad }
            long r5 = r2.i()     // Catch:{ all -> 0x03ad }
            r3.postDelayed(r4, r5)     // Catch:{ all -> 0x03ad }
            goto L_0x02e2
        L_0x0160:
            boolean r6 = r9.v()     // Catch:{ all -> 0x03ad }
            if (r6 == 0) goto L_0x037f
            android.bluetooth.BluetoothDevice r0 = r9.b     // Catch:{ all -> 0x03ad }
            android.os.Handler r3 = r9.f1053f     // Catch:{ all -> 0x03ad }
            com.android.chileaf.bluetooth.connect.c r4 = new com.android.chileaf.bluetooth.connect.c     // Catch:{ all -> 0x03ad }
            r4.<init>(r9, r2, r0)     // Catch:{ all -> 0x03ad }
            r7 = 200(0xc8, double:9.9E-322)
            r3.postDelayed(r4, r7)     // Catch:{ all -> 0x03ad }
            goto L_0x037f
        L_0x0176:
            boolean r6 = r9.u()     // Catch:{ all -> 0x03ad }
            goto L_0x037f
        L_0x017c:
            com.android.chileaf.bluetooth.connect.r0 r2 = (com.android.chileaf.bluetooth.connect.r0) r2     // Catch:{ all -> 0x03ad }
            int r0 = android.os.Build.VERSION.SDK_INT     // Catch:{ all -> 0x03ad }
            if (r0 < r7) goto L_0x0188
            boolean r6 = r9.t()     // Catch:{ all -> 0x03ad }
            goto L_0x037f
        L_0x0188:
            boolean r6 = r9.n     // Catch:{ all -> 0x03ad }
            if (r6 == 0) goto L_0x037f
            android.bluetooth.BluetoothDevice r10 = r9.b     // Catch:{ all -> 0x03ad }
            r2.g(r10)     // Catch:{ all -> 0x03ad }
            android.bluetooth.BluetoothDevice r10 = r9.b     // Catch:{ all -> 0x03ad }
            r2.d(r10)     // Catch:{ all -> 0x03ad }
            r9.b((boolean) r1)     // Catch:{ all -> 0x03ad }
            monitor-exit(r9)
            return
        L_0x019b:
            com.android.chileaf.bluetooth.connect.r0 r2 = (com.android.chileaf.bluetooth.connect.r0) r2     // Catch:{ all -> 0x03ad }
            int r0 = android.os.Build.VERSION.SDK_INT     // Catch:{ all -> 0x03ad }
            if (r0 < r7) goto L_0x01b3
            int r0 = r2.k()     // Catch:{ all -> 0x03ad }
            int r3 = r2.j()     // Catch:{ all -> 0x03ad }
            int r2 = r2.i()     // Catch:{ all -> 0x03ad }
            boolean r6 = r9.a((int) r0, (int) r3, (int) r2)     // Catch:{ all -> 0x03ad }
            goto L_0x037f
        L_0x01b3:
            boolean r6 = r9.n     // Catch:{ all -> 0x03ad }
            if (r6 == 0) goto L_0x037f
            android.bluetooth.BluetoothDevice r10 = r9.b     // Catch:{ all -> 0x03ad }
            r2.g(r10)     // Catch:{ all -> 0x03ad }
            android.bluetooth.BluetoothDevice r10 = r9.b     // Catch:{ all -> 0x03ad }
            r2.d(r10)     // Catch:{ all -> 0x03ad }
            r9.b((boolean) r1)     // Catch:{ all -> 0x03ad }
            monitor-exit(r9)
            return
        L_0x01c6:
            com.android.chileaf.bluetooth.connect.o0 r2 = (com.android.chileaf.bluetooth.connect.o0) r2     // Catch:{ all -> 0x03ad }
            int r0 = android.os.Build.VERSION.SDK_INT     // Catch:{ all -> 0x03ad }
            if (r0 < r7) goto L_0x01d6
            int r0 = r2.i()     // Catch:{ all -> 0x03ad }
            boolean r6 = r9.a((int) r0)     // Catch:{ all -> 0x03ad }
            goto L_0x037f
        L_0x01d6:
            int r0 = android.os.Build.VERSION.SDK_INT     // Catch:{ all -> 0x03ad }
            if (r0 < r4) goto L_0x037f
            int r0 = r2.i()     // Catch:{ all -> 0x03ad }
            boolean r6 = r9.a((int) r0)     // Catch:{ all -> 0x03ad }
            if (r6 == 0) goto L_0x037f
            android.bluetooth.BluetoothDevice r0 = r9.b     // Catch:{ all -> 0x03ad }
            android.os.Handler r3 = r9.f1053f     // Catch:{ all -> 0x03ad }
            com.android.chileaf.bluetooth.connect.g r4 = new com.android.chileaf.bluetooth.connect.g     // Catch:{ all -> 0x03ad }
            r4.<init>(r9, r2, r0)     // Catch:{ all -> 0x03ad }
            r7 = 100
            r3.postDelayed(r4, r7)     // Catch:{ all -> 0x03ad }
            goto L_0x037f
        L_0x01f4:
            com.android.chileaf.bluetooth.connect.q0 r2 = (com.android.chileaf.bluetooth.connect.q0) r2     // Catch:{ all -> 0x03ad }
            int r0 = r9.u     // Catch:{ all -> 0x03ad }
            int r3 = r2.i()     // Catch:{ all -> 0x03ad }
            if (r0 == r3) goto L_0x020c
            int r0 = android.os.Build.VERSION.SDK_INT     // Catch:{ all -> 0x03ad }
            if (r0 < r4) goto L_0x020c
            int r0 = r2.i()     // Catch:{ all -> 0x03ad }
            boolean r6 = r9.b((int) r0)     // Catch:{ all -> 0x03ad }
            goto L_0x037f
        L_0x020c:
            boolean r6 = r9.n     // Catch:{ all -> 0x03ad }
            if (r6 == 0) goto L_0x037f
            android.bluetooth.BluetoothDevice r10 = r9.b     // Catch:{ all -> 0x03ad }
            int r0 = r9.u     // Catch:{ all -> 0x03ad }
            r2.d(r10, r0)     // Catch:{ all -> 0x03ad }
            android.bluetooth.BluetoothDevice r10 = r9.b     // Catch:{ all -> 0x03ad }
            r2.d(r10)     // Catch:{ all -> 0x03ad }
            r9.b((boolean) r1)     // Catch:{ all -> 0x03ad }
            monitor-exit(r9)
            return
        L_0x0221:
            boolean r6 = r9.m()     // Catch:{ all -> 0x03ad }
            goto L_0x037f
        L_0x0227:
            boolean r6 = r9.a((boolean) r0)     // Catch:{ all -> 0x03ad }
            goto L_0x037f
        L_0x022d:
            boolean r6 = r9.a((boolean) r1)     // Catch:{ all -> 0x03ad }
            goto L_0x037f
        L_0x0233:
            boolean r6 = r9.s()     // Catch:{ all -> 0x03ad }
            goto L_0x037f
        L_0x0239:
            android.bluetooth.BluetoothGattCharacteristic r0 = r2.d     // Catch:{ all -> 0x03ad }
            boolean r6 = r9.a((android.bluetooth.BluetoothGattCharacteristic) r0)     // Catch:{ all -> 0x03ad }
            goto L_0x037f
        L_0x0241:
            android.bluetooth.BluetoothGattCharacteristic r0 = r2.d     // Catch:{ all -> 0x03ad }
            boolean r6 = r9.b((android.bluetooth.BluetoothGattCharacteristic) r0)     // Catch:{ all -> 0x03ad }
            goto L_0x037f
        L_0x0249:
            android.bluetooth.BluetoothGattCharacteristic r0 = r2.d     // Catch:{ all -> 0x03ad }
            boolean r6 = r9.c((android.bluetooth.BluetoothGattCharacteristic) r0)     // Catch:{ all -> 0x03ad }
            goto L_0x037f
        L_0x0251:
            android.bluetooth.BluetoothGattCharacteristic r0 = r2.d     // Catch:{ all -> 0x03ad }
            boolean r6 = r9.d((android.bluetooth.BluetoothGattCharacteristic) r0)     // Catch:{ all -> 0x03ad }
            goto L_0x037f
        L_0x0259:
            boolean r6 = r9.n()     // Catch:{ all -> 0x03ad }
            goto L_0x037f
        L_0x025f:
            boolean r6 = r9.r()     // Catch:{ all -> 0x03ad }
            goto L_0x037f
        L_0x0265:
            boolean r6 = r9.o()     // Catch:{ all -> 0x03ad }
            if (r6 == 0) goto L_0x037f
            com.android.chileaf.bluetooth.connect.Request r10 = r9.y     // Catch:{ all -> 0x03ad }
            android.bluetooth.BluetoothDevice r0 = r9.b     // Catch:{ all -> 0x03ad }
            r10.d(r0)     // Catch:{ all -> 0x03ad }
            r9.b((boolean) r1)     // Catch:{ all -> 0x03ad }
            monitor-exit(r9)
            return
        L_0x0277:
            com.android.chileaf.bluetooth.connect.w0 r2 = (com.android.chileaf.bluetooth.connect.w0) r2     // Catch:{ all -> 0x03ad }
            android.bluetooth.BluetoothGattDescriptor r0 = r2.e     // Catch:{ all -> 0x03ad }
            if (r0 == 0) goto L_0x037f
            java.util.Map<android.bluetooth.BluetoothGattDescriptor, byte[]> r0 = r9.w     // Catch:{ all -> 0x03ad }
            if (r0 == 0) goto L_0x0299
            java.util.Map<android.bluetooth.BluetoothGattDescriptor, byte[]> r0 = r9.w     // Catch:{ all -> 0x03ad }
            android.bluetooth.BluetoothGattDescriptor r3 = r2.e     // Catch:{ all -> 0x03ad }
            boolean r0 = r0.containsKey(r3)     // Catch:{ all -> 0x03ad }
            if (r0 == 0) goto L_0x0299
            java.util.Map<android.bluetooth.BluetoothGattDescriptor, byte[]> r0 = r9.w     // Catch:{ all -> 0x03ad }
            android.bluetooth.BluetoothGattDescriptor r3 = r2.e     // Catch:{ all -> 0x03ad }
            int r4 = r9.u     // Catch:{ all -> 0x03ad }
            byte[] r4 = r2.b(r4)     // Catch:{ all -> 0x03ad }
            r0.put(r3, r4)     // Catch:{ all -> 0x03ad }
            goto L_0x02a4
        L_0x0299:
            android.bluetooth.BluetoothGattDescriptor r0 = r2.e     // Catch:{ all -> 0x03ad }
            int r3 = r9.u     // Catch:{ all -> 0x03ad }
            byte[] r3 = r2.b(r3)     // Catch:{ all -> 0x03ad }
            r0.setValue(r3)     // Catch:{ all -> 0x03ad }
        L_0x02a4:
            android.bluetooth.BluetoothDevice r0 = r9.b     // Catch:{ all -> 0x03ad }
            r2.d(r0)     // Catch:{ all -> 0x03ad }
            r9.b((boolean) r1)     // Catch:{ all -> 0x03ad }
            goto L_0x02e2
        L_0x02ad:
            com.android.chileaf.bluetooth.connect.w0 r2 = (com.android.chileaf.bluetooth.connect.w0) r2     // Catch:{ all -> 0x03ad }
            android.bluetooth.BluetoothGattCharacteristic r0 = r2.d     // Catch:{ all -> 0x03ad }
            if (r0 == 0) goto L_0x037f
            java.util.Map<android.bluetooth.BluetoothGattCharacteristic, byte[]> r0 = r9.v     // Catch:{ all -> 0x03ad }
            if (r0 == 0) goto L_0x02cf
            java.util.Map<android.bluetooth.BluetoothGattCharacteristic, byte[]> r0 = r9.v     // Catch:{ all -> 0x03ad }
            android.bluetooth.BluetoothGattCharacteristic r3 = r2.d     // Catch:{ all -> 0x03ad }
            boolean r0 = r0.containsKey(r3)     // Catch:{ all -> 0x03ad }
            if (r0 == 0) goto L_0x02cf
            java.util.Map<android.bluetooth.BluetoothGattCharacteristic, byte[]> r0 = r9.v     // Catch:{ all -> 0x03ad }
            android.bluetooth.BluetoothGattCharacteristic r3 = r2.d     // Catch:{ all -> 0x03ad }
            int r4 = r9.u     // Catch:{ all -> 0x03ad }
            byte[] r4 = r2.b(r4)     // Catch:{ all -> 0x03ad }
            r0.put(r3, r4)     // Catch:{ all -> 0x03ad }
            goto L_0x02da
        L_0x02cf:
            android.bluetooth.BluetoothGattCharacteristic r0 = r2.d     // Catch:{ all -> 0x03ad }
            int r3 = r9.u     // Catch:{ all -> 0x03ad }
            byte[] r3 = r2.b(r3)     // Catch:{ all -> 0x03ad }
            r0.setValue(r3)     // Catch:{ all -> 0x03ad }
        L_0x02da:
            android.bluetooth.BluetoothDevice r0 = r9.b     // Catch:{ all -> 0x03ad }
            r2.d(r0)     // Catch:{ all -> 0x03ad }
            r9.b((boolean) r1)     // Catch:{ all -> 0x03ad }
        L_0x02e2:
            r6 = 1
            goto L_0x037f
        L_0x02e5:
            r0 = r2
            com.android.chileaf.bluetooth.connect.e1 r0 = (com.android.chileaf.bluetooth.connect.e1) r0     // Catch:{ all -> 0x03ad }
            android.bluetooth.BluetoothGattDescriptor r2 = r2.e     // Catch:{ all -> 0x03ad }
            if (r2 == 0) goto L_0x02f5
            int r3 = r9.u     // Catch:{ all -> 0x03ad }
            byte[] r0 = r0.b(r3)     // Catch:{ all -> 0x03ad }
            r2.setValue(r0)     // Catch:{ all -> 0x03ad }
        L_0x02f5:
            boolean r6 = r9.b((android.bluetooth.BluetoothGattDescriptor) r2)     // Catch:{ all -> 0x03ad }
            goto L_0x037f
        L_0x02fb:
            android.bluetooth.BluetoothGattDescriptor r0 = r2.e     // Catch:{ all -> 0x03ad }
            boolean r6 = r9.a((android.bluetooth.BluetoothGattDescriptor) r0)     // Catch:{ all -> 0x03ad }
            goto L_0x037f
        L_0x0303:
            r0 = r2
            com.android.chileaf.bluetooth.connect.e1 r0 = (com.android.chileaf.bluetooth.connect.e1) r0     // Catch:{ all -> 0x03ad }
            android.bluetooth.BluetoothGattCharacteristic r2 = r2.d     // Catch:{ all -> 0x03ad }
            if (r2 == 0) goto L_0x031a
            int r3 = r9.u     // Catch:{ all -> 0x03ad }
            byte[] r3 = r0.b(r3)     // Catch:{ all -> 0x03ad }
            r2.setValue(r3)     // Catch:{ all -> 0x03ad }
            int r0 = r0.i()     // Catch:{ all -> 0x03ad }
            r2.setWriteType(r0)     // Catch:{ all -> 0x03ad }
        L_0x031a:
            boolean r6 = r9.f((android.bluetooth.BluetoothGattCharacteristic) r2)     // Catch:{ all -> 0x03ad }
            goto L_0x037f
        L_0x031f:
            android.bluetooth.BluetoothGattCharacteristic r0 = r2.d     // Catch:{ all -> 0x03ad }
            boolean r6 = r9.e((android.bluetooth.BluetoothGattCharacteristic) r0)     // Catch:{ all -> 0x03ad }
            goto L_0x037f
        L_0x0326:
            com.android.chileaf.bluetooth.connect.v0 r2 = (com.android.chileaf.bluetooth.connect.v0) r2     // Catch:{ all -> 0x03ad }
            r9.z = r2     // Catch:{ all -> 0x03ad }
            r9.b((boolean) r1)     // Catch:{ all -> 0x03ad }
            monitor-exit(r9)
            return
        L_0x032f:
            boolean r6 = r9.w()     // Catch:{ all -> 0x03ad }
            goto L_0x037f
        L_0x0334:
            boolean r6 = r9.p()     // Catch:{ all -> 0x03ad }
            goto L_0x037f
        L_0x0339:
            boolean r6 = r9.q()     // Catch:{ all -> 0x03ad }
            goto L_0x037f
        L_0x033e:
            com.android.chileaf.bluetooth.connect.n0 r2 = (com.android.chileaf.bluetooth.connect.n0) r2     // Catch:{ all -> 0x03ad }
            r9.x = r2     // Catch:{ all -> 0x03ad }
            r9.y = r10     // Catch:{ all -> 0x03ad }
            android.bluetooth.BluetoothDevice r0 = r2.j()     // Catch:{ all -> 0x03ad }
            boolean r6 = r9.a((android.bluetooth.BluetoothDevice) r0, (com.android.chileaf.bluetooth.connect.n0) r2)     // Catch:{ all -> 0x03ad }
            goto L_0x037f
        L_0x034d:
            r3 = r2
            com.android.chileaf.bluetooth.connect.e1 r3 = (com.android.chileaf.bluetooth.connect.e1) r3     // Catch:{ all -> 0x03ad }
            android.bluetooth.BluetoothGattCharacteristic r4 = r2.d     // Catch:{ all -> 0x03ad }
            if (r4 == 0) goto L_0x0372
            int r5 = r9.u     // Catch:{ all -> 0x03ad }
            byte[] r3 = r3.b(r5)     // Catch:{ all -> 0x03ad }
            r4.setValue(r3)     // Catch:{ all -> 0x03ad }
            java.util.Map<android.bluetooth.BluetoothGattCharacteristic, byte[]> r3 = r9.v     // Catch:{ all -> 0x03ad }
            if (r3 == 0) goto L_0x0372
            java.util.Map<android.bluetooth.BluetoothGattCharacteristic, byte[]> r3 = r9.v     // Catch:{ all -> 0x03ad }
            boolean r3 = r3.containsKey(r4)     // Catch:{ all -> 0x03ad }
            if (r3 == 0) goto L_0x0372
            java.util.Map<android.bluetooth.BluetoothGattCharacteristic, byte[]> r3 = r9.v     // Catch:{ all -> 0x03ad }
            byte[] r5 = r4.getValue()     // Catch:{ all -> 0x03ad }
            r3.put(r4, r5)     // Catch:{ all -> 0x03ad }
        L_0x0372:
            android.bluetooth.BluetoothGattCharacteristic r3 = r2.d     // Catch:{ all -> 0x03ad }
            com.android.chileaf.bluetooth.connect.Request$Type r2 = r2.c     // Catch:{ all -> 0x03ad }
            com.android.chileaf.bluetooth.connect.Request$Type r4 = com.android.chileaf.bluetooth.connect.Request.Type.INDICATE     // Catch:{ all -> 0x03ad }
            if (r2 != r4) goto L_0x037b
            r0 = 1
        L_0x037b:
            boolean r6 = r9.a((android.bluetooth.BluetoothGattCharacteristic) r3, (boolean) r0)     // Catch:{ all -> 0x03ad }
        L_0x037f:
            if (r6 != 0) goto L_0x03a1
            com.android.chileaf.bluetooth.connect.Request r0 = r9.y     // Catch:{ all -> 0x03ad }
            android.bluetooth.BluetoothDevice r2 = r9.b     // Catch:{ all -> 0x03ad }
            boolean r3 = r9.n     // Catch:{ all -> 0x03ad }
            if (r3 == 0) goto L_0x038b
            r3 = -3
            goto L_0x0399
        L_0x038b:
            android.bluetooth.BluetoothAdapter r3 = android.bluetooth.BluetoothAdapter.getDefaultAdapter()     // Catch:{ all -> 0x03ad }
            boolean r3 = r3.isEnabled()     // Catch:{ all -> 0x03ad }
            if (r3 == 0) goto L_0x0397
            r3 = -1
            goto L_0x0399
        L_0x0397:
            r3 = -100
        L_0x0399:
            r0.b(r2, r3)     // Catch:{ all -> 0x03ad }
            r9.C = r10     // Catch:{ all -> 0x03ad }
            r9.b((boolean) r1)     // Catch:{ all -> 0x03ad }
        L_0x03a1:
            monitor-exit(r9)
            return
        L_0x03a3:
            r2.c()     // Catch:{ all -> 0x03ad }
            r9.C = r10     // Catch:{ all -> 0x03ad }
            r9.b((boolean) r1)     // Catch:{ all -> 0x03ad }
            monitor-exit(r9)
            return
        L_0x03ad:
            r10 = move-exception
            monitor-exit(r9)
            throw r10
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.chileaf.bluetooth.connect.j0.b(boolean):void");
    }

    private boolean a(BluetoothGattCharacteristic bluetoothGattCharacteristic) {
        return b(bluetoothGattCharacteristic);
    }

    private boolean a(BluetoothGattCharacteristic bluetoothGattCharacteristic, boolean z2) {
        BluetoothGattDescriptor descriptor;
        k0 k0Var = this.e;
        if (k0Var == null || k0Var.a() == null || bluetoothGattCharacteristic == null) {
            return false;
        }
        if (((z2 ? 32 : 16) & bluetoothGattCharacteristic.getProperties()) == 0 || (descriptor = bluetoothGattCharacteristic.getDescriptor(g0.e)) == null) {
            return false;
        }
        byte[] value = this.w.containsKey(descriptor) ? this.w.get(descriptor) : descriptor.getValue();
        if (value == null || value.length != 2 || value[0] == 0) {
            b(true);
            return true;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("[Server] Sending ");
        sb.append(z2 ? "indication" : "notification");
        sb.append(" to ");
        sb.append(bluetoothGattCharacteristic.getUuid());
        a(2, sb.toString());
        a(3, "server.notifyCharacteristicChanged(device, " + bluetoothGattCharacteristic.getUuid() + ", " + z2 + ")");
        boolean notifyCharacteristicChanged = this.e.a().notifyCharacteristicChanged(this.b, bluetoothGattCharacteristic, z2);
        if (notifyCharacteristicChanged && Build.VERSION.SDK_INT < 21) {
            this.f1053f.post(new h(this));
        }
        return notifyCharacteristicChanged;
    }

    private static BluetoothGattDescriptor a(BluetoothGattCharacteristic bluetoothGattCharacteristic, int i2) {
        if (bluetoothGattCharacteristic == null || (i2 & bluetoothGattCharacteristic.getProperties()) == 0) {
            return null;
        }
        return bluetoothGattCharacteristic.getDescriptor(g0.e);
    }

    private boolean a(BluetoothGattDescriptor bluetoothGattDescriptor) {
        BluetoothGatt bluetoothGatt = this.c;
        if (bluetoothGatt == null || bluetoothGattDescriptor == null || !this.n) {
            return false;
        }
        a(2, "Reading descriptor " + bluetoothGattDescriptor.getUuid());
        a(3, "gatt.readDescriptor(" + bluetoothGattDescriptor.getUuid() + ")");
        return bluetoothGatt.readDescriptor(bluetoothGattDescriptor);
    }

    @Deprecated
    private boolean a(boolean z2) {
        BluetoothGattService service;
        BluetoothGatt bluetoothGatt = this.c;
        if (bluetoothGatt == null || !this.n || (service = bluetoothGatt.getService(g0.f1047f)) == null) {
            return false;
        }
        BluetoothGattCharacteristic characteristic = service.getCharacteristic(g0.f1048g);
        if (z2) {
            return d(characteristic);
        }
        return b(characteristic);
    }

    private boolean a(int i2) {
        String str;
        String str2;
        BluetoothGatt bluetoothGatt = this.c;
        if (bluetoothGatt == null || !this.n) {
            return false;
        }
        if (i2 == 1) {
            str2 = Build.VERSION.SDK_INT >= 23 ? "HIGH (11.25–15ms, 0, 20s)" : "HIGH (7.5–10ms, 0, 20s)";
            str = "HIGH";
        } else if (i2 != 2) {
            str2 = "BALANCED (30–50ms, 0, 20s)";
            str = "BALANCED";
        } else {
            str2 = "LOW POWER (100–125ms, 2, 20s)";
            str = "LOW POWER";
        }
        a(2, "Requesting connection priority: " + str2 + "...");
        a(3, "gatt.requestConnectionPriority(" + str + ")");
        return bluetoothGatt.requestConnectionPriority(i2);
    }

    private boolean a(int i2, int i3, int i4) {
        BluetoothGatt bluetoothGatt = this.c;
        if (bluetoothGatt == null || !this.n) {
            return false;
        }
        a(2, "Requesting preferred PHYs...");
        a(3, "gatt.setPreferredPhy(" + com.android.chileaf.bluetooth.connect.h1.a.d(i2) + ", " + com.android.chileaf.bluetooth.connect.h1.a.d(i3) + ", coding option = " + com.android.chileaf.bluetooth.connect.h1.a.c(i4) + ")");
        bluetoothGatt.setPreferredPhy(i2, i3, i4);
        return true;
    }

    /* access modifiers changed from: package-private */
    public c1 a(Object obj) {
        c1 c1Var = this.A.get(obj);
        if (c1Var == null) {
            c1Var = new c1(this.f1053f);
            if (obj != null) {
                this.A.put(obj, c1Var);
            }
        }
        c1Var.a();
        return c1Var;
    }

    public /* synthetic */ void a(BluetoothDevice bluetoothDevice, Data data) {
        if (data.b() == 1) {
            int intValue = data.a(17, 0).intValue();
            a(4, "Battery Level received: " + intValue + "%");
            a(this.c, intValue);
            this.d.c.d(bluetoothDevice, intValue);
        }
    }

    /* access modifiers changed from: package-private */
    public final void a(Request request) {
        (this.f1056i ? this.f1055h : this.f1054g).add(request);
        request.m = true;
        b(false);
    }

    /* access modifiers changed from: package-private */
    public final void a(a1 a1Var) {
        this.y = null;
        this.C = null;
        Request.Type type = a1Var.c;
        if (type == Request.Type.CONNECT) {
            this.x = null;
            q();
        } else if (type == Request.Type.DISCONNECT) {
            b();
        } else {
            b(true);
        }
    }

    /* access modifiers changed from: package-private */
    public final Handler a() {
        return this.f1053f;
    }

    /* access modifiers changed from: private */
    public void a(BluetoothDevice bluetoothDevice) {
        boolean z2 = this.n;
        this.n = false;
        this.f1057j = false;
        this.k = false;
        this.f1056i = false;
        l();
        if (!z2) {
            a(5, "Connection attempt timed out");
            b();
            this.d.c.a(bluetoothDevice);
        } else if (this.q) {
            a(4, "Disconnected");
            b();
            this.d.c.a(bluetoothDevice);
            Request request = this.y;
            if (request != null && request.c == Request.Type.DISCONNECT) {
                request.d(bluetoothDevice);
            }
        } else {
            a(5, "Connection lost");
            this.d.c.k(bluetoothDevice);
        }
        h();
    }

    /* access modifiers changed from: private */
    public void a(BluetoothDevice bluetoothDevice, String str, int i2) {
        a(6, "Error (0x" + Integer.toHexString(i2) + "): " + com.android.chileaf.bluetooth.connect.g1.a.a(i2));
        this.d.c.a(bluetoothDevice, str, i2);
    }

    public /* synthetic */ void a(o0 o0Var, BluetoothDevice bluetoothDevice) {
        o0Var.d(bluetoothDevice);
        b(true);
    }

    public /* synthetic */ void a(Request request, BluetoothDevice bluetoothDevice) {
        a(4, "Cache refreshed");
        request.d(bluetoothDevice);
        this.y = null;
        f0 f0Var = this.C;
        if (f0Var != null) {
            f0Var.b(bluetoothDevice, -3);
            this.C = null;
        }
        this.f1054g.clear();
        this.f1055h = null;
        if (this.n) {
            h();
            a(2, "Discovering Services...");
            a(3, "gatt.discoverServices()");
            this.c.discoverServices();
        }
    }

    public /* synthetic */ void a(z0 z0Var, BluetoothDevice bluetoothDevice) {
        z0Var.d(bluetoothDevice);
        b(true);
    }

    /* access modifiers changed from: private */
    public void a(int i2, String str) {
        this.d.a(i2, str);
    }
}
