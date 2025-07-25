package com.chileaf.fitness.viewmodel;

import android.app.Application;
import android.bluetooth.BluetoothDevice;
import android.preference.PreferenceManager;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.OnLifecycleEvent;
import com.android.chileaf.bluetooth.connect.h0;
import com.android.chileaf.bluetooth.connect.i0;
import com.android.chileaf.bluetooth.connect.n0;
import com.android.chileaf.fitness.v;
import com.android.chileaf.fitness.w;
import com.chileaf.fitness.R$string;
import com.chileaf.fitness.base.BaseViewModel;
import com.chileaf.fitness.device.cdn.CDNManager;
import com.chileaf.fitness.device.cdn.c;
import com.chileaf.fitness.model.DiscoveredDevice;
import com.chileaf.fitness.model.a.a;
import java.util.Arrays;
import java.util.Locale;
import kotlin.d;
import kotlin.jvm.internal.i;
import kotlin.jvm.internal.m;

/* compiled from: CDNViewModel.kt */
public final class CDNViewModel extends BaseViewModel implements c {

    /* renamed from: f  reason: collision with root package name */
    private a<String> f1254f = new a<>();

    /* renamed from: g  reason: collision with root package name */
    private a<String> f1255g = new a<>();

    /* renamed from: h  reason: collision with root package name */
    private a<String> f1256h = new a<>();

    /* renamed from: i  reason: collision with root package name */
    private a<String> f1257i = new a<>();

    /* renamed from: j  reason: collision with root package name */
    private a<String> f1258j = new a<>();
    private a<String> k = new a<>();
    private a<String> l = new a<>();
    private a<String> m = new a<>();
    private a<String> n = new a<>();
    private a<String> o = new a<>();
    private BluetoothDevice p;
    private final d q;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public CDNViewModel(Application application) {
        super(application);
        i.b(application, "application");
        this.q = g.a(new CDNViewModel$mManager$2(application));
        m();
        n().a(this);
        n().b(false);
    }

    private final void m() {
        a(0);
        this.f1255g.a(com.chileaf.fitness.config.a.a(R$string.not_available_value));
        this.f1257i.a(com.chileaf.fitness.config.a.a(R$string.not_available_value));
        this.f1258j.a(com.chileaf.fitness.config.a.a(R$string.not_available_value));
        this.l.a(com.chileaf.fitness.config.a.a(R$string.not_available_value));
        this.n.a(com.chileaf.fitness.config.a.a(R$string.not_available_value));
    }

    private final CDNManager n() {
        return (CDNManager) this.q.getValue();
    }

    private final void o() {
        BluetoothDevice bluetoothDevice = this.p;
        if (bluetoothDevice != null) {
            n0 a = n().a(bluetoothDevice);
            a.a(true);
            a.a();
        }
    }

    public /* synthetic */ float a() {
        return com.android.chileaf.fitness.common.csc.a.a(this);
    }

    public /* synthetic */ void a(BluetoothDevice bluetoothDevice, String str) {
        v.d(this, bluetoothDevice, str);
    }

    public /* synthetic */ void a(BluetoothDevice bluetoothDevice, String str, int i2) {
        h0.a(this, bluetoothDevice, str, i2);
    }

    public final void a(DiscoveredDevice discoveredDevice) {
        i.b(discoveredDevice, "device");
        if (this.p == null) {
            this.p = discoveredDevice.getDevice();
            o();
        }
    }

    public /* synthetic */ void b(BluetoothDevice bluetoothDevice) {
        h0.f(this, bluetoothDevice);
    }

    public void b(BluetoothDevice bluetoothDevice, int i2) {
        i.b(bluetoothDevice, "device");
        a(i2);
    }

    public /* synthetic */ void b(BluetoothDevice bluetoothDevice, String str) {
        v.a((w) this, bluetoothDevice, str);
    }

    public /* synthetic */ void b(BluetoothDevice bluetoothDevice, boolean z) {
        h0.a((i0) this, bluetoothDevice, z);
    }

    public final a<String> c() {
        return this.f1254f;
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
        return this.f1257i;
    }

    @Deprecated
    public /* synthetic */ void d(BluetoothDevice bluetoothDevice, int i2) {
        h0.a((i0) this, bluetoothDevice, i2);
    }

    public /* synthetic */ void d(BluetoothDevice bluetoothDevice, String str) {
        v.b(this, bluetoothDevice, str);
    }

    public final a<String> e() {
        return this.f1258j;
    }

    public /* synthetic */ void e(BluetoothDevice bluetoothDevice) {
        h0.d(this, bluetoothDevice);
    }

    public /* synthetic */ void e(BluetoothDevice bluetoothDevice, String str) {
        v.f(this, bluetoothDevice, str);
    }

    public final a<String> f() {
        return this.k;
    }

    public /* synthetic */ void f(BluetoothDevice bluetoothDevice) {
        h0.a(this, bluetoothDevice);
    }

    public /* synthetic */ void f(BluetoothDevice bluetoothDevice, String str) {
        v.g(this, bluetoothDevice, str);
    }

    public final a<String> g() {
        return this.o;
    }

    public /* synthetic */ void g(BluetoothDevice bluetoothDevice) {
        h0.b(this, bluetoothDevice);
    }

    public /* synthetic */ void g(BluetoothDevice bluetoothDevice, String str) {
        v.e(this, bluetoothDevice, str);
    }

    public final a<String> h() {
        return this.n;
    }

    public final a<String> i() {
        return this.f1255g;
    }

    public /* synthetic */ void i(BluetoothDevice bluetoothDevice) {
        h0.c(this, bluetoothDevice);
    }

    public final a<String> j() {
        return this.f1256h;
    }

    @Deprecated
    public /* synthetic */ boolean j(BluetoothDevice bluetoothDevice) {
        return h0.h(this, bluetoothDevice);
    }

    public final a<String> k() {
        return this.l;
    }

    public /* synthetic */ void k(BluetoothDevice bluetoothDevice) {
        h0.g(this, bluetoothDevice);
    }

    public final a<String> l() {
        return this.m;
    }

    /* access modifiers changed from: protected */
    public void onCleared() {
        super.onCleared();
        if (n().e()) {
            n().a().a();
            this.p = null;
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public final void onResume() {
        String string = PreferenceManager.getDefaultSharedPreferences(b()).getString("settings_csc_unit", String.valueOf(1));
        Integer valueOf = string != null ? Integer.valueOf(Integer.parseInt(string)) : null;
        if (valueOf != null && valueOf.intValue() == 0) {
            this.f1256h.a(com.chileaf.fitness.config.a.a(R$string.csc_speed_unit_m_s));
            this.k.a(com.chileaf.fitness.config.a.a(R$string.csc_distance_unit_m));
            this.m.a(com.chileaf.fitness.config.a.a(R$string.csc_total_distance_unit_km));
        } else if (valueOf != null && valueOf.intValue() == 1) {
            this.f1256h.a(com.chileaf.fitness.config.a.a(R$string.csc_speed_unit_km_h));
            this.k.a(com.chileaf.fitness.config.a.a(R$string.csc_distance_unit_m));
            this.m.a(com.chileaf.fitness.config.a.a(R$string.csc_total_distance_unit_km));
        } else if (valueOf != null && valueOf.intValue() == 2) {
            this.f1256h.a(com.chileaf.fitness.config.a.a(R$string.csc_speed_unit_mph));
            this.k.a(com.chileaf.fitness.config.a.a(R$string.csc_distance_unit_yd));
            this.m.a(com.chileaf.fitness.config.a.a(R$string.csc_total_distance_unit_mile));
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

    private final void a(int i2) {
        this.f1254f.a(com.chileaf.fitness.config.a.a(R$string.battery_level, Integer.valueOf(i2)));
    }

    public void a(BluetoothDevice bluetoothDevice, float f2, float f3, float f4) {
        i.b(bluetoothDevice, "device");
        a(f4, f3, f2);
    }

    public void a(BluetoothDevice bluetoothDevice, float f2, float f3) {
        i.b(bluetoothDevice, "device");
        a<String> aVar = this.f1257i;
        m mVar = m.a;
        Locale locale = Locale.US;
        i.a((Object) locale, "Locale.US");
        String format = String.format(locale, "%.1f", Arrays.copyOf(new Object[]{Float.valueOf(f2)}, 1));
        i.a((Object) format, "java.lang.String.format(locale, format, *args)");
        aVar.a(format);
        a<String> aVar2 = this.n;
        m mVar2 = m.a;
        Locale locale2 = Locale.US;
        i.a((Object) locale2, "Locale.US");
        String format2 = String.format(locale2, "%.1f", Arrays.copyOf(new Object[]{Float.valueOf(f3)}, 1));
        i.a((Object) format2, "java.lang.String.format(locale, format, *args)");
        aVar2.a(format2);
    }

    private final void a(float f2, float f3, float f4) {
        String string = PreferenceManager.getDefaultSharedPreferences(b()).getString("settings_csc_unit", String.valueOf(1));
        Integer valueOf = string != null ? Integer.valueOf(Integer.parseInt(string)) : null;
        if (valueOf != null && valueOf.intValue() == 1) {
            f2 *= 3.6f;
            if (f3 < ((float) 1000)) {
                a<String> aVar = this.f1258j;
                m mVar = m.a;
                Locale locale = Locale.US;
                i.a((Object) locale, "Locale.US");
                String format = String.format(locale, "%.0f", Arrays.copyOf(new Object[]{Float.valueOf(f3)}, 1));
                i.a((Object) format, "java.lang.String.format(locale, format, *args)");
                aVar.a(format);
                this.k.a(com.chileaf.fitness.config.a.a(R$string.csc_distance_unit_m));
            } else {
                a<String> aVar2 = this.f1258j;
                m mVar2 = m.a;
                Locale locale2 = Locale.US;
                i.a((Object) locale2, "Locale.US");
                String format2 = String.format(locale2, "%.2f", Arrays.copyOf(new Object[]{Float.valueOf(f3 / 1000.0f)}, 1));
                i.a((Object) format2, "java.lang.String.format(locale, format, *args)");
                aVar2.a(format2);
                this.k.a(com.chileaf.fitness.config.a.a(R$string.csc_distance_unit_km));
            }
            a<String> aVar3 = this.l;
            m mVar3 = m.a;
            Locale locale3 = Locale.US;
            i.a((Object) locale3, "Locale.US");
            String format3 = String.format(locale3, "%.2f", Arrays.copyOf(new Object[]{Float.valueOf(f4 / 1000.0f)}, 1));
            i.a((Object) format3, "java.lang.String.format(locale, format, *args)");
            aVar3.a(format3);
        } else if (valueOf != null && valueOf.intValue() == 0) {
            if (f3 < ((float) 1000)) {
                a<String> aVar4 = this.f1258j;
                m mVar4 = m.a;
                Locale locale4 = Locale.US;
                i.a((Object) locale4, "Locale.US");
                String format4 = String.format(locale4, "%.0f", Arrays.copyOf(new Object[]{Float.valueOf(f3)}, 1));
                i.a((Object) format4, "java.lang.String.format(locale, format, *args)");
                aVar4.a(format4);
                this.k.a(com.chileaf.fitness.config.a.a(R$string.csc_distance_unit_m));
            } else {
                a<String> aVar5 = this.f1258j;
                m mVar5 = m.a;
                Locale locale5 = Locale.US;
                i.a((Object) locale5, "Locale.US");
                String format5 = String.format(locale5, "%.2f", Arrays.copyOf(new Object[]{Float.valueOf(f3 / 1000.0f)}, 1));
                i.a((Object) format5, "java.lang.String.format(locale, format, *args)");
                aVar5.a(format5);
                this.k.a(com.chileaf.fitness.config.a.a(R$string.csc_distance_unit_km));
            }
            a<String> aVar6 = this.l;
            m mVar6 = m.a;
            Locale locale6 = Locale.US;
            i.a((Object) locale6, "Locale.US");
            String format6 = String.format(locale6, "%.2f", Arrays.copyOf(new Object[]{Float.valueOf(f4 / 1000.0f)}, 1));
            i.a((Object) format6, "java.lang.String.format(locale, format, *args)");
            aVar6.a(format6);
        } else if (valueOf != null && valueOf.intValue() == 2) {
            f2 *= 2.2369f;
            if (f3 < ((float) 1760)) {
                a<String> aVar7 = this.f1258j;
                m mVar7 = m.a;
                Locale locale7 = Locale.US;
                i.a((Object) locale7, "Locale.US");
                String format7 = String.format(locale7, "%.0f", Arrays.copyOf(new Object[]{Float.valueOf(f3)}, 1));
                i.a((Object) format7, "java.lang.String.format(locale, format, *args)");
                aVar7.a(format7);
                this.k.a(com.chileaf.fitness.config.a.a(R$string.csc_distance_unit_yd));
            } else {
                a<String> aVar8 = this.f1258j;
                m mVar8 = m.a;
                Locale locale8 = Locale.US;
                i.a((Object) locale8, "Locale.US");
                String format8 = String.format(locale8, "%.2f", Arrays.copyOf(new Object[]{Float.valueOf(f3 / 1760.0f)}, 1));
                i.a((Object) format8, "java.lang.String.format(locale, format, *args)");
                aVar8.a(format8);
                this.k.a(com.chileaf.fitness.config.a.a(R$string.csc_distance_unit_mile));
            }
            a<String> aVar9 = this.l;
            m mVar9 = m.a;
            Locale locale9 = Locale.US;
            i.a((Object) locale9, "Locale.US");
            String format9 = String.format(locale9, "%.2f", Arrays.copyOf(new Object[]{Float.valueOf(f4 / 1609.31f)}, 1));
            i.a((Object) format9, "java.lang.String.format(locale, format, *args)");
            aVar9.a(format9);
        }
        a<String> aVar10 = this.f1255g;
        m mVar10 = m.a;
        Locale locale10 = Locale.US;
        i.a((Object) locale10, "Locale.US");
        String format10 = String.format(locale10, "%.1f", Arrays.copyOf(new Object[]{Float.valueOf(f2)}, 1));
        i.a((Object) format10, "java.lang.String.format(locale, format, *args)");
        aVar10.a(format10);
    }

    public void a(BluetoothDevice bluetoothDevice) {
        i.b(bluetoothDevice, "device");
        a(com.chileaf.fitness.config.a.a(R$string.state_disconnected));
    }

    private final void a(String str) {
        this.o.a(str);
    }
}
