package com.chileaf.fitness.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.SeekBar;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelLazy;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.g;
import androidx.recyclerview.widget.u;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.lifecycle.LifecycleExtKt;
import com.chileaf.fitness.R$color;
import com.chileaf.fitness.R$layout;
import com.chileaf.fitness.R$string;
import com.chileaf.fitness.b.y;
import com.chileaf.fitness.base.BaseActivity;
import com.chileaf.fitness.model.DiscoveredDevice;
import com.chileaf.fitness.ui.b.a;
import com.chileaf.fitness.viewmodel.DevicesViewModel;
import java.util.HashMap;
import kotlin.TypeCastException;
import kotlin.jvm.b.l;
import kotlin.jvm.internal.i;
import kotlin.jvm.internal.k;

/* compiled from: DevicesActivity.kt */
public final class DevicesActivity extends BaseActivity<y> implements a.c {
    private final kotlin.d E = g.a(new DevicesActivity$mName$2(this));
    private final kotlin.d F = new ViewModelLazy(k.a(DevicesViewModel.class), new DevicesActivity$$special$$inlined$viewModels$2(this), new DevicesActivity$$special$$inlined$viewModels$1(this));
    private HashMap G;

    /* compiled from: DevicesActivity.kt */
    public static final class WrapLinearLayoutManager extends LinearLayoutManager {
        public WrapLinearLayoutManager(Context context, AttributeSet attributeSet, int i2, int i3) {
            super(context, attributeSet, i2, i3);
        }

        public void e(RecyclerView.v vVar, RecyclerView.z zVar) {
            try {
                super.e(vVar, zVar);
            } catch (IndexOutOfBoundsException e) {
                e.printStackTrace();
            }
        }

        /* JADX INFO: this call moved to the top of the method (can break code semantics) */
        public /* synthetic */ WrapLinearLayoutManager(Context context, AttributeSet attributeSet, int i2, int i3, int i4, f fVar) {
            this(context, (i4 & 2) != 0 ? null : attributeSet, (i4 & 4) != 0 ? 0 : i2, (i4 & 8) != 0 ? 0 : i3);
        }
    }

    /* compiled from: DevicesActivity.kt */
    public static final class a {
        private a() {
        }

        public /* synthetic */ a(f fVar) {
            this();
        }
    }

    /* compiled from: DevicesActivity.kt */
    static final class b implements View.OnClickListener {
        final /* synthetic */ DevicesActivity e;

        b(DevicesActivity devicesActivity) {
            this.e = devicesActivity;
        }

        public final void onClick(View view) {
            com.chileaf.fitness.d.b.b.c(this.e);
        }
    }

    /* compiled from: DevicesActivity.kt */
    static final class c implements View.OnClickListener {
        final /* synthetic */ DevicesActivity e;

        c(DevicesActivity devicesActivity) {
            this.e = devicesActivity;
        }

        public final void onClick(View view) {
            this.e.t();
        }
    }

    /* compiled from: DevicesActivity.kt */
    public static final class d implements SeekBar.OnSeekBarChangeListener {
        final /* synthetic */ y a;
        final /* synthetic */ DevicesActivity b;

        d(y yVar, DevicesActivity devicesActivity) {
            this.a = yVar;
            this.b = devicesActivity;
        }

        public void onProgressChanged(SeekBar seekBar, int i2, boolean z) {
            int i3 = -20 - i2;
            AppCompatTextView appCompatTextView = this.a.G;
            i.a((Object) appCompatTextView, "tvSignal");
            appCompatTextView.setText(String.valueOf(i3));
            this.b.r().a(i3);
        }

        public void onStartTrackingTouch(SeekBar seekBar) {
        }

        public void onStopTrackingTouch(SeekBar seekBar) {
        }
    }

    /* compiled from: DevicesActivity.kt */
    static final class e<T> implements Observer<com.chileaf.fitness.model.a.b> {
        final /* synthetic */ DevicesActivity a;

        e(DevicesActivity devicesActivity) {
            this.a = devicesActivity;
        }

        /* renamed from: a */
        public final void onChanged(com.chileaf.fitness.model.a.b bVar) {
            DevicesActivity devicesActivity = this.a;
            i.a((Object) bVar, "it");
            devicesActivity.a(bVar);
        }
    }

    static {
        new a((f) null);
    }

    public static final /* synthetic */ y a(DevicesActivity devicesActivity) {
        return (y) devicesActivity.m();
    }

    private final String q() {
        return (String) this.E.getValue();
    }

    /* access modifiers changed from: private */
    public final DevicesViewModel r() {
        return (DevicesViewModel) this.F.getValue();
    }

    /* access modifiers changed from: private */
    public final void s() {
        MaterialDialog materialDialog = new MaterialDialog(this, (com.afollestad.materialdialogs.a) null, 2, (f) null);
        MaterialDialog.a(materialDialog, Float.valueOf(8.0f), (Integer) null, 2, (Object) null);
        MaterialDialog.a(materialDialog, (Integer) null, getString(R$string.permission_required), 1, (Object) null);
        MaterialDialog.a(materialDialog, (Integer) null, getString(R$string.permission_location_info), (l) null, 5, (Object) null);
        MaterialDialog.c(materialDialog, (Integer) null, getString(R$string.confirm), new DevicesActivity$onPermissionSettings$$inlined$show$lambda$1(this), 1, (Object) null);
        MaterialDialog.b(materialDialog, (Integer) null, getString(R$string.cancel), (l) null, 5, (Object) null);
        LifecycleExtKt.a(materialDialog, this);
        materialDialog.show();
    }

    /* access modifiers changed from: private */
    public final void t() {
        if (!com.chileaf.fitness.d.b.b.a(this)) {
            MaterialDialog materialDialog = new MaterialDialog(this, (com.afollestad.materialdialogs.a) null, 2, (f) null);
            MaterialDialog.a(materialDialog, Float.valueOf(8.0f), (Integer) null, 2, (Object) null);
            MaterialDialog materialDialog2 = materialDialog;
            MaterialDialog.a(materialDialog2, (Integer) null, getString(R$string.permission_location_enable), (l) null, 5, (Object) null);
            MaterialDialog.c(materialDialog2, (Integer) null, getString(R$string.confirm), new DevicesActivity$onRequestLocationPermission$$inlined$show$lambda$1(this), 1, (Object) null);
            LifecycleExtKt.a(materialDialog, this);
            materialDialog.show();
        } else if (Build.VERSION.SDK_INT < 29) {
            com.chileaf.fitness.config.permission.a.a(this, new String[]{"android.permission.ACCESS_FINE_LOCATION", "android.permission.ACCESS_COARSE_LOCATION"}, new DevicesActivity$onRequestLocationPermission$1(this));
        } else {
            com.chileaf.fitness.config.permission.a.a(this, new String[]{"android.permission.ACCESS_FINE_LOCATION", "android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_BACKGROUND_LOCATION"}, new DevicesActivity$onRequestLocationPermission$2(this));
        }
    }

    public View d(int i2) {
        if (this.G == null) {
            this.G = new HashMap();
        }
        View view = (View) this.G.get(Integer.valueOf(i2));
        if (view != null) {
            return view;
        }
        View findViewById = findViewById(i2);
        this.G.put(Integer.valueOf(i2), findViewById);
        return findViewById;
    }

    public void onRequestPermissionsResult(int i2, String[] strArr, int[] iArr) {
        i.b(strArr, "permissions");
        i.b(iArr, "grantResults");
        super.onRequestPermissionsResult(i2, strArr, iArr);
        if (i2 == 3) {
            r().j();
        }
    }

    /* access modifiers changed from: protected */
    public void onStart() {
        super.onStart();
        r().clear();
    }

    /* access modifiers changed from: protected */
    public void onStop() {
        super.onStop();
        r().l();
    }

    /* access modifiers changed from: protected */
    public int p() {
        return R$layout.activity_devices;
    }

    /* access modifiers changed from: protected */
    public DevicesViewModel o() {
        return r();
    }

    /* access modifiers changed from: protected */
    public void a(View view) {
        i.b(view, "root");
        ((y) m()).F.setColorSchemeColors(androidx.core.content.a.a((Context) this, (int) R$color.colorPrimary));
        RecyclerView recyclerView = ((y) m()).D;
        recyclerView.a((RecyclerView.n) new g(this, 1));
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new WrapLinearLayoutManager(this, (AttributeSet) null, 0, 0, 14, (f) null));
        recyclerView.setItemAnimator(new androidx.recyclerview.widget.e());
        RecyclerView.l itemAnimator = recyclerView.getItemAnimator();
        if (itemAnimator != null) {
            ((u) itemAnimator).a(false);
            return;
        }
        throw new TypeCastException("null cannot be cast to non-null type androidx.recyclerview.widget.SimpleItemAnimator");
    }

    /* access modifiers changed from: protected */
    public void a(Bundle bundle) {
        String string = getString(R$string.scan_device);
        i.a((Object) string, "getString(R.string.scan_device)");
        a(string);
        y yVar = (y) m();
        yVar.z.z.setOnClickListener(new b(this));
        yVar.B.z.setOnClickListener(new c(this));
        yVar.F.setOnRefreshListener(new DevicesActivity$initData$$inlined$with$lambda$3(this));
        yVar.E.setOnSeekBarChangeListener(new d(yVar, this));
        DevicesViewModel r = r();
        r.d().a(q());
        r.c().observe(this, new e(this));
        com.chileaf.fitness.ui.b.a aVar = new com.chileaf.fitness.ui.b.a(this, r().d());
        aVar.setOnItemClickListener(this);
        RecyclerView recyclerView = yVar.D;
        i.a((Object) recyclerView, "rvDevices");
        recyclerView.setAdapter(aVar);
        yVar.a(r());
    }

    public void a(DiscoveredDevice discoveredDevice) {
        i.b(discoveredDevice, "device");
        if (discoveredDevice.getName() != null) {
            String name = discoveredDevice.getName();
            i.a((Object) name, "device.name");
            Class<?> b2 = com.chileaf.fitness.config.c.b(name);
            if (b2 != null) {
                Intent intent = new Intent(this, b2);
                intent.putExtra("extra_device", discoveredDevice);
                BaseActivity.a((BaseActivity) this, intent, false, 2, (Object) null);
                return;
            }
            BaseActivity.a((BaseActivity) this, (CharSequence) getString(R$string.device_can_not_connect), 0, 2, (Object) null);
            return;
        }
        BaseActivity.a((BaseActivity) this, (CharSequence) getString(R$string.device_can_not_connect), 0, 2, (Object) null);
    }

    /* access modifiers changed from: private */
    public final void a(com.chileaf.fitness.model.a.b bVar) {
        if (!com.chileaf.fitness.d.b.b.b(this) || !com.chileaf.fitness.d.b.b.a(this)) {
            r().h().a(false);
            r().e().a(true);
            r().i().a(false);
            r().f().a(false);
            r().g().a(false);
            return;
        }
        r().h().a(true);
        if (com.chileaf.fitness.d.b.b.a()) {
            r().f().a(Boolean.valueOf(bVar.d()));
            r().g().a(Boolean.valueOf(!bVar.d()));
            r().e().a(true);
            r().i().a(true);
            r().k();
            return;
        }
        r().e().a(false);
        r().i().a(false);
        r().f().a(false);
        r().g().a(false);
        r().clear();
    }
}
