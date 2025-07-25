package aicare.net.cn.iweightlibrary.wby;

import aicare.net.cn.iweightlibrary.entity.AlgorithmInfo;
import aicare.net.cn.iweightlibrary.entity.BleInfo;
import aicare.net.cn.iweightlibrary.entity.BodyFatData;
import aicare.net.cn.iweightlibrary.entity.DecimalInfo;
import aicare.net.cn.iweightlibrary.entity.User;
import aicare.net.cn.iweightlibrary.entity.WeightData;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.SparseArray;
import com.jeremyliao.liveeventbus.BuildConfig;
import f.a.a.a.b.c;
import f.a.a.a.b.d;
import f.a.a.a.b.e;
import java.io.PrintStream;
import java.lang.ref.WeakReference;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/* compiled from: WBYManager */
public class a implements aicare.net.cn.iweightlibrary.bleprofile.a<b> {
    /* access modifiers changed from: private */
    public static final UUID u = UUID.fromString("0000ffb0-0000-1000-8000-00805f9b34fb");
    /* access modifiers changed from: private */
    public static final UUID v = UUID.fromString("0000ffb2-0000-1000-8000-00805f9b34fb");
    /* access modifiers changed from: private */
    public static final UUID w = UUID.fromString("0000ffb1-0000-1000-8000-00805f9b34fb");
    private static final UUID x = UUID.fromString("00002902-0000-1000-8000-00805f9b34fb");
    private static a y = null;
    /* access modifiers changed from: private */
    public b a;
    /* access modifiers changed from: private */
    public BluetoothGatt b;
    private Context c;
    private b d;
    private BleInfo e;

    /* renamed from: f  reason: collision with root package name */
    private BluetoothDevice f7f;

    /* renamed from: g  reason: collision with root package name */
    private DecimalInfo f8g;

    /* renamed from: h  reason: collision with root package name */
    private DecimalInfo f9h;

    /* renamed from: i  reason: collision with root package name */
    private User f10i;
    /* access modifiers changed from: private */

    /* renamed from: j  reason: collision with root package name */
    public BluetoothGattCharacteristic f11j;
    /* access modifiers changed from: private */
    public BluetoothGattCharacteristic k;
    /* access modifiers changed from: private */
    public List<byte[]> l = new ArrayList();
    /* access modifiers changed from: private */
    public int m = 0;
    /* access modifiers changed from: private */
    public byte[] n;
    private byte[] o;
    /* access modifiers changed from: private */
    public byte[] p;
    private final BluetoothGattCallback q = new C0000a();
    private String r;
    private byte[] s;
    private byte[] t;

    /* renamed from: aicare.net.cn.iweightlibrary.wby.a$a  reason: collision with other inner class name */
    /* compiled from: WBYManager */
    class C0000a extends BluetoothGattCallback {
        C0000a() {
        }

        public void onCharacteristicChanged(BluetoothGatt bluetoothGatt, BluetoothGattCharacteristic bluetoothGattCharacteristic) {
            byte[] value = bluetoothGattCharacteristic.getValue();
            c.c("WBYManager", "onCharacteristicChanged: " + d.a(value));
            b a2 = a.this.a;
            a2.a("Changed:" + d.a(value));
            if (bluetoothGattCharacteristic.getUuid().equals(a.v)) {
                a.this.a(value);
            }
        }

        public void onCharacteristicWrite(BluetoothGatt bluetoothGatt, BluetoothGattCharacteristic bluetoothGattCharacteristic, int i2) {
            if (i2 != 0) {
                c.b("WBYManager", "onCharacteristicWrite error: +  (" + i2 + ")");
            } else if (bluetoothGattCharacteristic.getUuid().equals(a.w)) {
                byte[] value = bluetoothGattCharacteristic.getValue();
                c.c("WBYManager", "onCharacteristicWrite: " + d.a(value));
                b a2 = a.this.a;
                a2.a("Write:" + d.a(value));
                if (a.this.l.size() != 0) {
                    PrintStream printStream = System.out;
                    printStream.println("index = " + a.this.m);
                    if (a.this.m < a.this.l.size() - 1) {
                        if (Arrays.equals(value, (byte[]) a.this.l.get(a.this.m))) {
                            a aVar = a.this;
                            aVar.b((byte[]) aVar.l.get(a.d(a.this)));
                        }
                    } else if (Arrays.equals(value, (byte[]) a.this.l.get(a.this.m))) {
                        a.this.a((byte) 2, (byte) 0);
                        int unused = a.this.m = 0;
                    }
                }
                if (Arrays.equals(value, a.this.n)) {
                    a.this.t();
                }
                if (Arrays.equals(value, a.this.p)) {
                    a.this.a((byte) -4, (byte) 0);
                }
            }
        }

        public void onConnectionStateChange(BluetoothGatt bluetoothGatt, int i2, int i3) {
            if (i2 != 0) {
                c.b("WBYManager", "onConnectionStateChange error: (" + i2 + ")");
                a.this.a.a("Error on connection state change", i2);
            } else if (i3 == 2) {
                a.this.a.b();
                if (a.this.b != null) {
                    a.this.b.discoverServices();
                }
            } else if (i3 == 0) {
                c.a("WBYManager", "Device disconnected");
                a.this.a.d();
            }
        }

        public void onDescriptorWrite(BluetoothGatt bluetoothGatt, BluetoothGattDescriptor bluetoothGattDescriptor, int i2) {
            if (i2 == 0) {
                c.c("WBYManager", "onDescriptorWrite");
                a.this.a((byte) -9, (byte) 0);
                return;
            }
            c.b("WBYManager", "onDescriptorWrite error: +  (" + i2 + ")");
            a.this.a.a("Error on writing descriptor", i2);
        }

        public void onServicesDiscovered(BluetoothGatt bluetoothGatt, int i2) {
            if (i2 == 0) {
                c.c("WBYManager", "onServicesDiscovered Success");
                c.c("WBYManager", "onServicesDiscovered status = " + i2);
                List<BluetoothGattService> services = bluetoothGatt.getServices();
                c.c("WBYManager", "onServicesDiscovered services = " + services.size());
                if (services.size() == 0) {
                    a.this.disconnect();
                }
                for (BluetoothGattService next : services) {
                    c.b("WBYManager", next.getUuid().toString());
                    for (BluetoothGattCharacteristic next2 : next.getCharacteristics()) {
                        c.b("WBYManager", next2.getUuid().toString() + "; permission: " + d.a(next2.getPermissions()) + "; property: " + d.a(next2.getProperties()));
                    }
                }
                BluetoothGattService service = bluetoothGatt.getService(a.u);
                if (services.contains(bluetoothGatt.getService(a.u))) {
                    BluetoothGattCharacteristic unused = a.this.f11j = service.getCharacteristic(a.w);
                    BluetoothGattCharacteristic unused2 = a.this.k = service.getCharacteristic(a.v);
                    if (a.this.n()) {
                        a.this.a.a();
                        a.this.l();
                        return;
                    }
                    return;
                }
                return;
            }
            c.b("WBYManager", "onServicesDiscovered error: (" + i2 + ")");
            a.this.a.a("Error on discovering services", i2);
        }
    }

    /* compiled from: WBYManager */
    private static class b extends Handler {
        private WeakReference<a> a;
        private int b = 0;

        public b(a aVar) {
            this.a = new WeakReference<>(aVar);
        }

        public void handleMessage(Message message) {
            super.handleMessage(message);
            a aVar = (a) this.a.get();
            if (aVar != null) {
                int i2 = message.what;
                if (i2 == 0) {
                    aVar.e();
                    aVar.a(false);
                    aVar.d();
                    aVar.o();
                } else if (i2 == 1) {
                    c.b("WBYManager", "getDecimalCount = " + this.b);
                    if (this.b < 3) {
                        aVar.c();
                        this.b++;
                        return;
                    }
                    this.b = 0;
                    aVar.d();
                    aVar.o();
                } else if (i2 == 2) {
                    aVar.a(false);
                    aVar.d();
                    aVar.o();
                }
            }
        }
    }

    static /* synthetic */ int d(a aVar) {
        int i2 = aVar.m + 1;
        aVar.m = i2;
        return i2;
    }

    /* access modifiers changed from: private */
    public void l() {
        c.c("WBYManager", "enableAicareIndication");
        this.b.setCharacteristicNotification(this.k, true);
        BluetoothGattDescriptor descriptor = this.k.getDescriptor(x);
        descriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
        this.b.writeDescriptor(descriptor);
        c.c("WBYManager", "enableAicareIndication sync.......................");
    }

    public static synchronized a m() {
        a aVar;
        synchronized (a.class) {
            if (y == null) {
                y = new a();
            }
            aVar = y;
        }
        return aVar;
    }

    /* access modifiers changed from: private */
    public boolean n() {
        return this.f11j != null;
    }

    /* access modifiers changed from: private */
    public void o() {
        b bVar = this.a;
        if (bVar != null) {
            bVar.c();
            this.a.a(this.f8g);
        }
    }

    private void p() {
        i();
        this.d.sendEmptyMessageDelayed(2, 500);
    }

    private void q() {
        j();
        this.d.sendEmptyMessageDelayed(1, 500);
    }

    private void r() {
        k();
        this.d.sendEmptyMessageDelayed(0, 500);
    }

    private void s() {
        c.b("WBYManager", "syncUserId");
        b(this.n);
    }

    /* access modifiers changed from: private */
    public void t() {
        c.b("WBYManager", "syncUserInfo");
        b(this.o);
    }

    public void disconnect() {
        c.a("WBYManager", "disconnect");
        BluetoothGatt bluetoothGatt = this.b;
        if (bluetoothGatt != null) {
            bluetoothGatt.disconnect();
        }
    }

    private void i() {
        b bVar = this.d;
        if (bVar != null) {
            bVar.removeMessages(2);
        }
    }

    private void j() {
        b bVar = this.d;
        if (bVar != null) {
            bVar.removeMessages(1);
        }
    }

    private void k() {
        b bVar = this.d;
        if (bVar != null) {
            bVar.removeMessages(0);
        }
    }

    public void c() {
        c.a("WBYManager", "getDecimalInfo");
        b(f.a.a.a.b.a.a((byte) 4));
        q();
    }

    public void d() {
        this.f8g = this.f9h;
    }

    public void e() {
        b bVar = this.a;
        if (bVar != null) {
            bVar.a(0, BuildConfig.FLAVOR);
        }
    }

    /* access modifiers changed from: private */
    public void b(byte[] bArr) {
        if (n()) {
            this.f11j.setValue(bArr);
            this.f11j.setWriteType(1);
            if (this.b.writeCharacteristic(this.f11j)) {
                c.b("WBYManager", "writeValue: bytes = " + d.a(bArr));
            }
        }
    }

    public void a(b bVar) {
        this.a = bVar;
    }

    public void a(Context context, BluetoothDevice bluetoothDevice) {
        c.c("WBYManager", "connect");
        a();
        this.f7f = bluetoothDevice;
        BleInfo bleInfo = new BleInfo();
        this.e = bleInfo;
        bleInfo.setAddress(bluetoothDevice.getAddress());
        this.e.setName(bluetoothDevice.getName());
        this.b = bluetoothDevice.connectGatt(context, false, this.q);
        this.c = context;
        this.d = new b(this);
        this.f9h = new DecimalInfo(1, 1, 1, 1, 1, 1);
        this.f8g = null;
    }

    public void b() {
        byte[] a2 = f.a.a.a.b.a.a();
        this.t = a2;
        this.s = f.a.a.a.b.a.b(Arrays.copyOfRange(a2, 2, a2.length), false);
        b(this.t);
        p();
    }

    public void a() {
        this.c = null;
        this.f10i = null;
        this.e = null;
        this.f8g = null;
        k();
        i();
        j();
        BluetoothGatt bluetoothGatt = this.b;
        if (bluetoothGatt != null) {
            a(bluetoothGatt);
            this.b.close();
            this.b = null;
            this.f11j = null;
            this.k = null;
        }
    }

    public void a(byte b2, byte b3) {
        if (b2 == -9) {
            c.a("WBYManager", "getVersion");
            r();
        }
        b(f.a.a.a.b.a.a(b2, (User) null, b3));
    }

    public void a(User user) {
        this.f10i = user;
        this.n = f.a.a.a.b.a.a((byte) -6, user, (byte) 0);
        this.o = f.a.a.a.b.a.a((byte) -5, user, (byte) 0);
        s();
    }

    /* access modifiers changed from: private */
    public void a(byte[] bArr) {
        if (bArr[0] == -83 && bArr[1] == 1) {
            boolean a2 = f.a.a.a.b.a.a(bArr, this.s);
            i();
            if (TextUtils.isEmpty(this.r) || "20180118".compareTo(this.r.split("_")[0]) > 0) {
                this.f8g = this.f9h;
                o();
            } else {
                c();
            }
            a(a2);
            this.a.a(this.t, bArr, this.s, a2);
            this.t = null;
            this.s = null;
        }
        SparseArray<Object> h2 = f.a.a.a.b.a.h(bArr);
        if (h2 != null && h2.size() != 0) {
            if (h2.indexOfKey(0) >= 0) {
                if (this.f8g != null) {
                    WeightData weightData = (WeightData) h2.get(0);
                    weightData.setDecimalInfo(this.f8g);
                    this.a.a(weightData);
                }
            } else if (h2.indexOfKey(1) >= 0) {
                if (((Integer) h2.get(1)).intValue() == 22) {
                    disconnect();
                }
                this.a.b(((Integer) h2.get(1)).intValue());
            } else if (h2.indexOfKey(2) >= 0) {
                k();
                String valueOf = String.valueOf(h2.get(2));
                this.r = valueOf;
                this.e.setVersion(valueOf);
                if (!f.a.a.a.b.a.a(this.f7f.getAddress()) || !f.a.a.a.b.a.a(this.r.split("_")[0], this.r.split("_")[1])) {
                    b();
                } else {
                    a(true);
                    this.f8g = this.f9h;
                    o();
                }
                this.a.a(0, this.r);
            } else if (h2.indexOfKey(3) >= 0) {
                this.a.a(3, String.valueOf(h2.get(3)));
            } else if (h2.indexOfKey(4) >= 0) {
                this.a.a(1, String.valueOf(h2.get(4)));
            } else if (h2.indexOfKey(5) >= 0) {
                this.a.a(2, String.valueOf(h2.get(5)));
            } else if (h2.indexOfKey(6) >= 0) {
                this.a.a(4, String.valueOf(h2.get(6)));
            } else if (h2.indexOfKey(7) >= 0) {
                if (this.f8g != null) {
                    BodyFatData bodyFatData = (BodyFatData) h2.get(7);
                    bodyFatData.setDecimalInfo(this.f8g);
                    this.a.a(true, bodyFatData);
                }
            } else if (h2.indexOfKey(8) >= 0) {
                if (this.f8g != null) {
                    BodyFatData bodyFatData2 = (BodyFatData) h2.get(8);
                    bodyFatData2.setDecimalInfo(this.f8g);
                    User user = this.f10i;
                    if (user != null) {
                        bodyFatData2.setNumber(user.getId());
                        bodyFatData2.setHeight(this.f10i.getHeight());
                        bodyFatData2.setSex(this.f10i.getSex());
                        bodyFatData2.setAge(this.f10i.getAge());
                    }
                    this.a.a(false, bodyFatData2);
                }
            } else if (h2.indexOfKey(9) >= 0) {
                this.a.a(((Integer) h2.get(9)).intValue());
            } else if (h2.indexOfKey(10) >= 0) {
                j();
                this.f8g = (DecimalInfo) h2.get(10);
                o();
            } else if (h2.indexOfKey(11) >= 0 && this.f8g != null) {
                AlgorithmInfo algorithmInfo = (AlgorithmInfo) h2.get(11);
                algorithmInfo.setDecimalInfo(this.f8g);
                this.a.a(algorithmInfo);
            }
        }
    }

    /* access modifiers changed from: private */
    public void a(boolean z) {
        if (!z) {
            disconnect();
        }
        BleInfo bleInfo = this.e;
        if (bleInfo != null) {
            bleInfo.setIsCheck(z ? 1 : 0);
            if (f.a.a.a.b.b.b(this.c)) {
                new e(f.a.a.a.b.b.a(this.c, this.e)).execute(new Void[0]);
            }
        }
    }

    private void a(BluetoothGatt bluetoothGatt) {
        try {
            c.b("WBYManager", "refresh device cache");
            Method method = bluetoothGatt.getClass().getMethod("refresh", (Class[]) null);
            if (method != null && !((Boolean) method.invoke(bluetoothGatt, (Object[]) null)).booleanValue()) {
                c.b("WBYManager", "refresh failed");
            }
        } catch (Exception unused) {
            c.b("WBYManager", "An exception occurred while refreshing device cache");
        }
    }
}
