package com.chileaf.fitness.viewmodel;

import android.app.Application;
import android.bluetooth.BluetoothDevice;
import com.chileaf.fitness.R$string;
import com.chileaf.fitness.base.BaseViewModel;
import com.chileaf.fitness.model.DiscoveredDevice;
import com.chileaf.fitness.model.a.a;
import kotlin.jvm.internal.i;

/* compiled from: BoxingViewModel.kt */
public final class BoxingViewModel extends BaseViewModel {

    /* renamed from: f  reason: collision with root package name */
    private a<String> f1251f = new a<>();

    /* renamed from: g  reason: collision with root package name */
    private a<String> f1252g = new a<>();

    /* renamed from: h  reason: collision with root package name */
    private BluetoothDevice f1253h;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public BoxingViewModel(Application application) {
        super(application);
        i.b(application, "application");
        e();
    }

    private final void e() {
        a(0);
    }

    private final void f() {
        BluetoothDevice bluetoothDevice = this.f1253h;
    }

    public final void a(DiscoveredDevice discoveredDevice) {
        i.b(discoveredDevice, "device");
        if (this.f1253h == null) {
            this.f1253h = discoveredDevice.getDevice();
            f();
        }
    }

    public final a<String> c() {
        return this.f1251f;
    }

    public final a<String> d() {
        return this.f1252g;
    }

    private final void a(int i2) {
        this.f1251f.a(com.chileaf.fitness.config.a.a(R$string.battery_level, Integer.valueOf(i2)));
    }
}
