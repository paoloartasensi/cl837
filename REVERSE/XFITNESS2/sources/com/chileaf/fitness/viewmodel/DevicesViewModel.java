package com.chileaf.fitness.viewmodel;

import android.app.Application;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.IntentFilter;
import android.os.SystemClock;
import com.android.chileaf.bluetooth.scanner.ScanFilter;
import com.android.chileaf.bluetooth.scanner.ScanResult;
import com.android.chileaf.bluetooth.scanner.ScanSettings;
import com.android.chileaf.bluetooth.scanner.j;
import com.android.chileaf.bluetooth.scanner.k;
import com.chileaf.fitness.base.BaseViewModel;
import com.chileaf.fitness.model.a.b;
import com.chileaf.fitness.model.a.c;
import java.util.ArrayList;
import java.util.List;
import kotlin.jvm.internal.i;

/* compiled from: DevicesViewModel.kt */
public final class DevicesViewModel extends BaseViewModel {

    /* renamed from: f  reason: collision with root package name */
    private com.chileaf.fitness.model.a.a<Boolean> f1284f = new com.chileaf.fitness.model.a.a<>();

    /* renamed from: g  reason: collision with root package name */
    private com.chileaf.fitness.model.a.a<Boolean> f1285g = new com.chileaf.fitness.model.a.a<>();

    /* renamed from: h  reason: collision with root package name */
    private com.chileaf.fitness.model.a.a<Boolean> f1286h = new com.chileaf.fitness.model.a.a<>();

    /* renamed from: i  reason: collision with root package name */
    private com.chileaf.fitness.model.a.a<Boolean> f1287i = new com.chileaf.fitness.model.a.a<>();

    /* renamed from: j  reason: collision with root package name */
    private com.chileaf.fitness.model.a.a<Boolean> f1288j = new com.chileaf.fitness.model.a.a<>();
    private final c k = new c(false, true);
    private final b l;
    private final a m;
    private final DevicesViewModel$mLocationChangedReceiver$1 n;
    private final DevicesViewModel$mBluetoothStateReceiver$1 o;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public DevicesViewModel(Application application) {
        super(application);
        i.b(application, "application");
        this.l = new b(com.chileaf.fitness.d.b.b.a(), com.chileaf.fitness.d.b.b.a(application));
        this.m = new a(this);
        this.n = new DevicesViewModel$mLocationChangedReceiver$1(this);
        DevicesViewModel$mBluetoothStateReceiver$1 devicesViewModel$mBluetoothStateReceiver$1 = new DevicesViewModel$mBluetoothStateReceiver$1(this);
        this.o = devicesViewModel$mBluetoothStateReceiver$1;
        application.registerReceiver(devicesViewModel$mBluetoothStateReceiver$1, new IntentFilter("android.bluetooth.adapter.action.STATE_CHANGED"));
        if (com.chileaf.fitness.d.b.b.b()) {
            application.registerReceiver(this.n, new IntentFilter("android.location.MODE_CHANGED"));
        }
    }

    public final void a(int i2) {
        this.k.a(i2);
        clear();
    }

    public final b c() {
        return this.l;
    }

    public final void clear() {
        this.k.b();
        this.l.c();
    }

    public final c d() {
        return this.k;
    }

    public final com.chileaf.fitness.model.a.a<Boolean> e() {
        return this.f1285g;
    }

    public final com.chileaf.fitness.model.a.a<Boolean> f() {
        return this.f1287i;
    }

    public final com.chileaf.fitness.model.a.a<Boolean> g() {
        return this.f1288j;
    }

    public final com.chileaf.fitness.model.a.a<Boolean> h() {
        return this.f1284f;
    }

    public final com.chileaf.fitness.model.a.a<Boolean> i() {
        return this.f1286h;
    }

    public final void j() {
        this.l.h();
    }

    public final void k() {
        if (!this.l.f() && this.l.e()) {
            ScanSettings.b bVar = new ScanSettings.b();
            bVar.e(2);
            bVar.b(false);
            bVar.a(1000);
            bVar.a(false);
            ScanSettings a2 = bVar.a();
            i.a((Object) a2, "ScanSettings.Builder()\n …\n                .build()");
            com.android.chileaf.bluetooth.scanner.a a3 = com.android.chileaf.bluetooth.scanner.a.a();
            i.a((Object) a3, "BluetoothLeScannerCompat.getScanner()");
            a3.a((List<ScanFilter>) null, a2, this.m);
            this.l.i();
        }
    }

    public final void l() {
        if (this.l.f() && this.l.e()) {
            com.android.chileaf.bluetooth.scanner.a a2 = com.android.chileaf.bluetooth.scanner.a.a();
            i.a((Object) a2, "BluetoothLeScannerCompat.getScanner()");
            a2.a(this.m);
            this.l.j();
        }
    }

    /* access modifiers changed from: protected */
    public void onCleared() {
        super.onCleared();
        b().unregisterReceiver(this.o);
        if (com.chileaf.fitness.d.b.b.b()) {
            b().unregisterReceiver(this.n);
        }
    }

    /* compiled from: DevicesViewModel.kt */
    public static final class a extends j {
        final /* synthetic */ DevicesViewModel a;

        a(DevicesViewModel devicesViewModel) {
            this.a = devicesViewModel;
        }

        public void a(List<ScanResult> list) {
            i.b(list, "results");
            boolean z = false;
            for (T t : r.b(list, a())) {
                BluetoothDevice a2 = t.a();
                i.a((Object) a2, "result.device");
                j.a.a.b("onBatchScanResults: %s %s", a2.getName(), t.toString());
                if (this.a.d().a((ScanResult) t)) {
                    z = true;
                }
            }
            if (z) {
                this.a.d().a();
                this.a.c().g();
            }
        }

        public void a(int i2) {
            this.a.c().j();
        }

        private final List<ScanResult> a() {
            ArrayList arrayList = new ArrayList();
            BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
            if (defaultAdapter != null) {
                for (BluetoothDevice next : defaultAdapter.getBondedDevices()) {
                    i.a((Object) next, "bluetoothDevice");
                    if (next.getType() == 2 || next.getType() == 3) {
                        j.a.a.b("bondedDevices: %s - %s", next.getName(), next.getAddress());
                        arrayList.add(new ScanResult(next, (k) null, -100, SystemClock.elapsedRealtimeNanos()));
                    }
                }
            }
            return arrayList;
        }
    }
}
