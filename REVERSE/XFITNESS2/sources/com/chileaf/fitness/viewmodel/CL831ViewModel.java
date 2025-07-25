package com.chileaf.fitness.viewmodel;

import android.app.Application;
import android.bluetooth.BluetoothDevice;
import com.android.chileaf.bluetooth.connect.h0;
import com.android.chileaf.bluetooth.connect.i0;
import com.android.chileaf.fitness.v;
import com.android.chileaf.fitness.w;
import com.chileaf.fitness.R$string;
import com.chileaf.fitness.base.BaseViewModel;
import com.chileaf.fitness.device.wear.cl831.CL831Manager;
import com.chileaf.fitness.device.wear.cl831.e;
import com.chileaf.fitness.model.DiscoveredDevice;
import com.chileaf.fitness.model.a.a;
import java.util.List;
import kotlin.d;
import kotlin.jvm.internal.i;

/* compiled from: CL831ViewModel.kt */
public final class CL831ViewModel extends BaseViewModel implements e {

    /* renamed from: f  reason: collision with root package name */
    private a<String> f1274f = new a<>();

    /* renamed from: g  reason: collision with root package name */
    private a<String> f1275g = new a<>();

    /* renamed from: h  reason: collision with root package name */
    private a<String> f1276h = new a<>();

    /* renamed from: i  reason: collision with root package name */
    private a<String> f1277i = new a<>();

    /* renamed from: j  reason: collision with root package name */
    private a<Integer> f1278j = new a<>();
    private a<String> k = new a<>();
    private a<Integer> l = new a<>();
    private BluetoothDevice m;
    private final d n;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public CL831ViewModel(Application application) {
        super(application);
        i.b(application, "application");
        this.n = g.a(new CL831ViewModel$mManager$2(application));
        j();
        k().a(this);
        k().b(false);
    }

    private final void b(float f2) {
        this.f1277i.a(com.chileaf.fitness.config.a.a(R$string.distance_value, Float.valueOf(f2)));
    }

    private final void j() {
        a(0);
        a(0.0f);
        b(0.0f);
        b(0);
        this.f1278j.a(0);
    }

    private final CL831Manager k() {
        return (CL831Manager) this.n.getValue();
    }

    private final void l() {
        BluetoothDevice bluetoothDevice = this.m;
        if (bluetoothDevice != null) {
            k().a(bluetoothDevice, true);
        }
    }

    public /* synthetic */ void a(BluetoothDevice bluetoothDevice, int i2) {
        com.chileaf.fitness.device.wear.cl831.d.a((e) this, bluetoothDevice, i2);
    }

    public /* synthetic */ void a(BluetoothDevice bluetoothDevice, String str) {
        v.d(this, bluetoothDevice, str);
    }

    public /* synthetic */ void a(BluetoothDevice bluetoothDevice, String str, int i2) {
        h0.a(this, bluetoothDevice, str, i2);
    }

    public /* synthetic */ void a(BluetoothDevice bluetoothDevice, boolean z) {
        com.chileaf.fitness.device.wear.cl831.d.a((e) this, bluetoothDevice, z);
    }

    public final void a(DiscoveredDevice discoveredDevice) {
        i.b(discoveredDevice, "device");
        if (this.m == null) {
            this.m = discoveredDevice.getDevice();
            l();
        }
    }

    public /* synthetic */ void b(BluetoothDevice bluetoothDevice) {
        h0.f(this, bluetoothDevice);
    }

    public /* synthetic */ void b(BluetoothDevice bluetoothDevice, String str) {
        v.a((w) this, bluetoothDevice, str);
    }

    public /* synthetic */ void b(BluetoothDevice bluetoothDevice, boolean z) {
        h0.a((i0) this, bluetoothDevice, z);
    }

    public final a<String> c() {
        return this.f1274f;
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

    public final a<String> d() {
        return this.f1275g;
    }

    @Deprecated
    public /* synthetic */ void d(BluetoothDevice bluetoothDevice, int i2) {
        h0.a((i0) this, bluetoothDevice, i2);
    }

    public /* synthetic */ void d(BluetoothDevice bluetoothDevice, String str) {
        v.b(this, bluetoothDevice, str);
    }

    public final a<String> e() {
        return this.f1277i;
    }

    public /* synthetic */ void e(BluetoothDevice bluetoothDevice) {
        h0.d(this, bluetoothDevice);
    }

    public /* synthetic */ void e(BluetoothDevice bluetoothDevice, String str) {
        v.f(this, bluetoothDevice, str);
    }

    public final a<String> f() {
        return this.f1276h;
    }

    public /* synthetic */ void f(BluetoothDevice bluetoothDevice) {
        h0.a(this, bluetoothDevice);
    }

    public /* synthetic */ void f(BluetoothDevice bluetoothDevice, String str) {
        v.g(this, bluetoothDevice, str);
    }

    public final a<String> g() {
        return this.k;
    }

    public /* synthetic */ void g(BluetoothDevice bluetoothDevice) {
        h0.b(this, bluetoothDevice);
    }

    public /* synthetic */ void g(BluetoothDevice bluetoothDevice, String str) {
        v.e(this, bluetoothDevice, str);
    }

    public final a<Integer> h() {
        return this.l;
    }

    public final a<Integer> i() {
        return this.f1278j;
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
        if (k().e()) {
            k().a().a();
            this.m = null;
        }
    }

    private final void b(int i2) {
        this.f1276h.a(com.chileaf.fitness.config.a.a(R$string.heart_rate_value, Integer.valueOf(i2)));
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
        this.f1274f.a(com.chileaf.fitness.config.a.a(R$string.battery_level, Integer.valueOf(i2)));
    }

    private final void a(float f2) {
        this.f1275g.a(com.chileaf.fitness.config.a.a(R$string.calorie_value, Float.valueOf(f2)));
    }

    public void a(BluetoothDevice bluetoothDevice, int i2, int i3, int i4) {
        i.b(bluetoothDevice, "device");
        a(((float) i4) / 10.0f);
        b(((float) i3) / 100.0f);
        this.f1278j.a(Integer.valueOf(i2));
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
