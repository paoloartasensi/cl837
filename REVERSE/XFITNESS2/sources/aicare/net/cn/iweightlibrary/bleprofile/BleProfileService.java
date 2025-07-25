package aicare.net.cn.iweightlibrary.bleprofile;

import android.app.Service;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;

public abstract class BleProfileService extends Service implements b {
    private b e;
    /* access modifiers changed from: private */

    /* renamed from: f  reason: collision with root package name */
    public a<b> f1f;
    /* access modifiers changed from: private */

    /* renamed from: g  reason: collision with root package name */
    public boolean f2g;
    /* access modifiers changed from: private */

    /* renamed from: h  reason: collision with root package name */
    public String f3h;

    private static class b extends Handler {
        private BleProfileService a;
        private c b;
        private String c;

        public void handleMessage(Message message) {
            super.handleMessage(message);
            if (message.what == 0) {
                this.a.c(this.c, 5);
                this.b.a();
            }
        }

        private b(BleProfileService bleProfileService) {
            this.a = bleProfileService;
            c e = bleProfileService.e();
            this.b = e;
            this.c = e.b();
        }
    }

    public class c extends Binder {
        public c() {
        }

        public final void a() {
            f.a.a.a.b.c.b("BleProfileService", "disconnect mConnected = " + BleProfileService.this.f2g);
            if (!BleProfileService.this.f2g) {
                BleProfileService.this.d();
            } else {
                BleProfileService.this.f1f.disconnect();
            }
        }

        public String b() {
            return BleProfileService.this.f3h;
        }

        public boolean c() {
            return BleProfileService.this.f2g;
        }
    }

    private void h() {
        this.e.removeMessages(0);
    }

    private void i() {
        h();
        this.e.sendEmptyMessageDelayed(0, 10000);
    }

    public void d() {
        f.a.a.a.b.c.b("BleProfileService", "onDeviceDisconnected!");
        c(this.f3h, 0);
        this.f2g = false;
        this.f3h = null;
        stopSelf();
    }

    /* access modifiers changed from: protected */
    public c e() {
        return new c();
    }

    /* access modifiers changed from: protected */
    public abstract a f();

    /* access modifiers changed from: protected */
    public void g() {
    }

    public IBinder onBind(Intent intent) {
        return e();
    }

    public void onCreate() {
        super.onCreate();
        a<b> f2 = f();
        this.f1f = f2;
        f2.a(this);
        this.e = new b();
    }

    public void onDestroy() {
        super.onDestroy();
        f.a.a.a.b.c.b("BleProfileService", "Service onDestroy!");
        this.f1f.a();
        this.f1f = null;
        this.f3h = null;
        this.f2g = false;
        h();
    }

    public int onStartCommand(Intent intent, int i2, int i3) {
        f.a.a.a.b.c.b("BleProfileService", "BleProfileService onStartCommand!");
        if (intent == null || !intent.hasExtra("aicare.net.cn.fatscale.extra.DEVICE_ADDRESS")) {
            throw new UnsupportedOperationException("No device address at EXTRA_DEVICE_ADDRESS key");
        }
        String stringExtra = intent.getStringExtra("aicare.net.cn.fatscale.extra.DEVICE_ADDRESS");
        this.f3h = stringExtra;
        c(stringExtra, 4);
        i();
        BluetoothDevice remoteDevice = ((BluetoothManager) getSystemService("bluetooth")).getAdapter().getRemoteDevice(this.f3h);
        remoteDevice.getName();
        g();
        f.a.a.a.b.c.b("BleProfileService", "mConnected = " + this.f2g);
        if (!this.f2g) {
            this.f1f.a(getApplicationContext(), remoteDevice);
            return 2;
        }
        h();
        return 2;
    }

    public boolean onUnbind(Intent intent) {
        return true;
    }

    public void b() {
        f.a.a.a.b.c.b("BleProfileService", "onDeviceConnected!");
        h();
        this.f2g = true;
        c(this.f3h, 1);
    }

    public void c() {
        f.a.a.a.b.c.b("BleProfileService", "onIndicationSuccess!");
        c(this.f3h, 3);
    }

    public void a() {
        f.a.a.a.b.c.b("BleProfileService", "onServicesDiscovered!");
        c(this.f3h, 2);
    }

    /* access modifiers changed from: private */
    public void c(String str, int i2) {
        Intent intent = new Intent("aicare.net.cn.fatscale.action.CONNECT_STATE_CHANGED");
        intent.putExtra("aicare.net.cn.fatscale.extra.DEVICE_ADDRESS", str);
        intent.putExtra("aicare.net.cn.fatscale.extra.CONNECT_STATE", i2);
        intent.setPackage(getPackageName());
        sendBroadcast(intent);
    }

    public void a(String str, int i2) {
        f.a.a.a.b.c.b("BleProfileService", "onError message = " + str + ", errorcode = " + i2);
        b(str, i2);
        d();
    }

    private void b(String str, int i2) {
        Intent intent = new Intent("aicare.net.cn.fatscale.action.CONNECT_ERROR");
        intent.setPackage(getPackageName());
        intent.putExtra("aicare.net.cn.fatscale.extra.ERROR_MSG", str);
        intent.putExtra("aicare.net.cn.fatscale.extra.ERROR_CODE", i2);
        sendBroadcast(intent);
    }
}
