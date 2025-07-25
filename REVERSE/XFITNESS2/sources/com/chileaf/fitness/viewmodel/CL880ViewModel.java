package com.chileaf.fitness.viewmodel;

import android.app.Application;
import android.bluetooth.BluetoothDevice;
import com.android.chileaf.bluetooth.connect.h0;
import com.android.chileaf.bluetooth.connect.i0;
import com.android.chileaf.fitness.v;
import com.android.chileaf.fitness.w;
import com.chileaf.fitness.R$string;
import com.chileaf.fitness.base.BaseViewModel;
import com.chileaf.fitness.device.wear.cl880.CL880Manager;
import com.chileaf.fitness.device.wear.cl880.callback.e;
import com.chileaf.fitness.device.wear.cl880.callback.f;
import com.chileaf.fitness.device.wear.cl880.g;
import com.chileaf.fitness.device.wear.cl880.h;
import com.chileaf.fitness.device.wear.cl880.model.SleepHistory;
import com.chileaf.fitness.device.wear.cl880.model.SportDayHistory;
import com.chileaf.fitness.device.wear.cl880.model.SportHistory;
import com.chileaf.fitness.model.DiscoveredDevice;
import java.util.List;
import kotlin.d;
import kotlin.jvm.internal.i;

/* compiled from: CL880ViewModel.kt */
public final class CL880ViewModel extends BaseViewModel implements h {

    /* renamed from: f  reason: collision with root package name */
    private com.chileaf.fitness.model.a.a<String> f1279f = new com.chileaf.fitness.model.a.a<>();

    /* renamed from: g  reason: collision with root package name */
    private com.chileaf.fitness.model.a.a<String> f1280g = new com.chileaf.fitness.model.a.a<>();

    /* renamed from: h  reason: collision with root package name */
    private com.chileaf.fitness.model.a.a<String> f1281h = new com.chileaf.fitness.model.a.a<>();

    /* renamed from: i  reason: collision with root package name */
    private com.chileaf.fitness.model.a.a<String> f1282i = new com.chileaf.fitness.model.a.a<>();

    /* renamed from: j  reason: collision with root package name */
    private com.chileaf.fitness.model.a.a<Integer> f1283j = new com.chileaf.fitness.model.a.a<>();
    private com.chileaf.fitness.model.a.a<String> k = new com.chileaf.fitness.model.a.a<>();
    private com.chileaf.fitness.model.a.a<Integer> l = new com.chileaf.fitness.model.a.a<>();
    private BluetoothDevice m;
    private final d n;

    /* compiled from: CL880ViewModel.kt */
    static final class a implements e {
        public static final a e = new a();

        a() {
        }

        public final void d(BluetoothDevice bluetoothDevice, List<SportHistory> list) {
            i.b(bluetoothDevice, "<anonymous parameter 0>");
            com.chileaf.fitness.device.wear.cl880.external.c l = com.chileaf.fitness.device.wear.cl880.external.c.l();
            i.a((Object) l, "SpecManager.getInstance()");
            l.d(list);
        }
    }

    /* compiled from: CL880ViewModel.kt */
    static final class b implements com.chileaf.fitness.device.wear.cl880.callback.d {
        public static final b e = new b();

        b() {
        }

        public final void h(BluetoothDevice bluetoothDevice, List<SleepHistory> list) {
            i.b(bluetoothDevice, "<anonymous parameter 0>");
            com.chileaf.fitness.device.wear.cl880.external.c l = com.chileaf.fitness.device.wear.cl880.external.c.l();
            i.a((Object) l, "SpecManager.getInstance()");
            l.b(list);
        }
    }

    /* compiled from: CL880ViewModel.kt */
    static final class c implements f {
        public static final c e = new c();

        c() {
        }

        public final void f(BluetoothDevice bluetoothDevice, List<SportDayHistory> list) {
            i.b(bluetoothDevice, "<anonymous parameter 0>");
            com.chileaf.fitness.device.wear.cl880.external.c l = com.chileaf.fitness.device.wear.cl880.external.c.l();
            i.a((Object) l, "SpecManager.getInstance()");
            l.c(list);
        }
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public CL880ViewModel(Application application) {
        super(application);
        i.b(application, "application");
        this.n = g.a(new CL880ViewModel$mManager$2(application));
        j();
        l().a(this);
        l().b(false);
        l().a((e) a.e);
        l().a((com.chileaf.fitness.device.wear.cl880.callback.d) b.e);
        l().a((f) c.e);
    }

    private final void b(float f2) {
        this.f1282i.a(com.chileaf.fitness.config.a.a(R$string.distance_value, Float.valueOf(f2)));
    }

    private final void j() {
        a(0);
        a(0.0f);
        b(0.0f);
        b(0);
        this.f1283j.a(0);
    }

    private final void k() {
        com.chileaf.fitness.device.wear.cl880.external.c l2 = com.chileaf.fitness.device.wear.cl880.external.c.l();
        i.a((Object) l2, "SpecManager.getInstance()");
        List<SleepHistory> f2 = l2.f();
        if (f2 == null || f2.isEmpty()) {
            l().A();
        }
    }

    private final CL880Manager l() {
        return (CL880Manager) this.n.getValue();
    }

    private final void m() {
        BluetoothDevice bluetoothDevice = this.m;
        if (bluetoothDevice != null) {
            com.chileaf.fitness.device.wear.cl880.external.c.l().j();
            l().a(bluetoothDevice, true);
        }
    }

    public /* synthetic */ void a(BluetoothDevice bluetoothDevice, int i2) {
        g.a(this, bluetoothDevice, i2);
    }

    public /* synthetic */ void a(BluetoothDevice bluetoothDevice, String str) {
        v.d(this, bluetoothDevice, str);
    }

    public /* synthetic */ void a(BluetoothDevice bluetoothDevice, String str, int i2) {
        h0.a(this, bluetoothDevice, str, i2);
    }

    public final void a(boolean z) {
        l().c(z);
    }

    public void b(BluetoothDevice bluetoothDevice) {
        i.b(bluetoothDevice, "device");
    }

    public /* synthetic */ void b(BluetoothDevice bluetoothDevice, String str) {
        v.a((w) this, bluetoothDevice, str);
    }

    public /* synthetic */ void b(BluetoothDevice bluetoothDevice, boolean z) {
        h0.a((i0) this, bluetoothDevice, z);
    }

    public final com.chileaf.fitness.model.a.a<String> c() {
        return this.f1279f;
    }

    public /* synthetic */ void c(BluetoothDevice bluetoothDevice) {
        h0.e(this, bluetoothDevice);
    }

    public /* synthetic */ void c(BluetoothDevice bluetoothDevice, int i2) {
        v.a((w) this, bluetoothDevice, i2);
    }

    public /* synthetic */ void c(BluetoothDevice bluetoothDevice, String str) {
        v.c(this, bluetoothDevice, str);
    }

    public final com.chileaf.fitness.model.a.a<String> d() {
        return this.f1280g;
    }

    @Deprecated
    public /* synthetic */ void d(BluetoothDevice bluetoothDevice, int i2) {
        h0.a((i0) this, bluetoothDevice, i2);
    }

    public /* synthetic */ void d(BluetoothDevice bluetoothDevice, String str) {
        v.b(this, bluetoothDevice, str);
    }

    public final com.chileaf.fitness.model.a.a<String> e() {
        return this.f1282i;
    }

    public /* synthetic */ void e(BluetoothDevice bluetoothDevice) {
        h0.d(this, bluetoothDevice);
    }

    public /* synthetic */ void e(BluetoothDevice bluetoothDevice, String str) {
        v.f(this, bluetoothDevice, str);
    }

    public final com.chileaf.fitness.model.a.a<String> f() {
        return this.f1281h;
    }

    public /* synthetic */ void f(BluetoothDevice bluetoothDevice) {
        h0.a(this, bluetoothDevice);
    }

    public /* synthetic */ void f(BluetoothDevice bluetoothDevice, String str) {
        v.g(this, bluetoothDevice, str);
    }

    public final com.chileaf.fitness.model.a.a<String> g() {
        return this.k;
    }

    public /* synthetic */ void g(BluetoothDevice bluetoothDevice) {
        h0.b(this, bluetoothDevice);
    }

    public /* synthetic */ void g(BluetoothDevice bluetoothDevice, String str) {
        v.e(this, bluetoothDevice, str);
    }

    public final com.chileaf.fitness.model.a.a<Integer> h() {
        return this.l;
    }

    public final com.chileaf.fitness.model.a.a<Integer> i() {
        return this.f1283j;
    }

    public /* synthetic */ void i(BluetoothDevice bluetoothDevice) {
        h0.c(this, bluetoothDevice);
    }

    @Deprecated
    public /* synthetic */ boolean j(BluetoothDevice bluetoothDevice) {
        return h0.h(this, bluetoothDevice);
    }

    public /* synthetic */ void k(BluetoothDevice bluetoothDevice) {
        h0.g(this, bluetoothDevice);
    }

    /* access modifiers changed from: protected */
    public void onCleared() {
        super.onCleared();
        if (l().e()) {
            l().a().a();
            this.m = null;
        }
    }

    private final void b(int i2) {
        this.f1281h.a(com.chileaf.fitness.config.a.a(R$string.heart_rate_value, Integer.valueOf(i2)));
    }

    public final void a(DiscoveredDevice discoveredDevice) {
        i.b(discoveredDevice, "device");
        if (this.m == null) {
            this.m = discoveredDevice.getDevice();
            m();
        }
    }

    public void d(BluetoothDevice bluetoothDevice) {
        i.b(bluetoothDevice, "device");
        a(com.chileaf.fitness.config.a.a(R$string.state_connecting));
    }

    public void h(BluetoothDevice bluetoothDevice) {
        i.b(bluetoothDevice, "device");
        a(com.chileaf.fitness.config.a.a(R$string.state_connected));
    }

    public void b(BluetoothDevice bluetoothDevice, int i2) {
        i.b(bluetoothDevice, "device");
        a(i2);
    }

    private final void a(int i2) {
        this.f1279f.a(com.chileaf.fitness.config.a.a(R$string.battery_level, Integer.valueOf(i2)));
    }

    private final void a(float f2) {
        this.f1280g.a(com.chileaf.fitness.config.a.a(R$string.calorie_value, Float.valueOf(f2)));
    }

    public void a(BluetoothDevice bluetoothDevice, int i2, int i3, int i4) {
        i.b(bluetoothDevice, "device");
        a(((float) i4) / 10.0f);
        b(((float) i3) / 100.0f);
        this.f1283j.a(Integer.valueOf(i2));
        k();
    }

    public void a(BluetoothDevice bluetoothDevice, int i2, Boolean bool, Integer num, List<Integer> list) {
        i.b(bluetoothDevice, "device");
        b(i2);
        this.l.a(Integer.valueOf(i2));
    }

    public void a(BluetoothDevice bluetoothDevice) {
        i.b(bluetoothDevice, "device");
        a(com.chileaf.fitness.config.a.a(R$string.state_disconnected));
    }

    private final void a(String str) {
        this.k.a(str);
    }
}
