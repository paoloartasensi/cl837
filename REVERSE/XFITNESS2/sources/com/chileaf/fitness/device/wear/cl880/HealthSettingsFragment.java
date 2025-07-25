package com.chileaf.fitness.device.wear.cl880;

import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;
import com.chileaf.fitness.R$layout;
import com.chileaf.fitness.R$string;
import com.chileaf.fitness.b.u0;
import com.chileaf.fitness.base.BaseActivity;
import com.chileaf.fitness.base.BaseViewModel;
import com.chileaf.fitness.device.wear.cl880.model.DrinkConfig;
import com.chileaf.fitness.device.wear.cl880.model.InactivityConfig;
import com.chileaf.fitness.ui.c.d;
import com.chileaf.fitness.ui.c.e;
import com.chileaf.fitness.widget.SwitchButton;
import java.util.HashMap;

/* compiled from: HealthSettingsFragment.kt */
public final class HealthSettingsFragment extends com.chileaf.fitness.base.a<u0> {
    private final kotlin.d f0 = g.a(HealthSettingsFragment$mManager$2.INSTANCE);
    private final kotlin.d g0 = g.a(new HealthSettingsFragment$mDoubleTime$2(this));
    private final kotlin.d h0 = g.a(new HealthSettingsFragment$mInterval$2(this));
    private HashMap i0;

    /* compiled from: HealthSettingsFragment.kt */
    static final class a implements CompoundButton.OnCheckedChangeListener {
        final /* synthetic */ HealthSettingsFragment a;
        final /* synthetic */ DrinkConfig b;

        a(HealthSettingsFragment healthSettingsFragment, DrinkConfig drinkConfig, InactivityConfig inactivityConfig) {
            this.a = healthSettingsFragment;
            this.b = drinkConfig;
        }

        public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
            DrinkConfig drinkConfig = this.b;
            drinkConfig.enable = z;
            HealthSettingsFragment healthSettingsFragment = this.a;
            kotlin.jvm.internal.i.a((Object) drinkConfig, "drink");
            healthSettingsFragment.a(drinkConfig);
        }
    }

    /* compiled from: HealthSettingsFragment.kt */
    static final class b implements View.OnClickListener {
        final /* synthetic */ u0 e;

        /* renamed from: f  reason: collision with root package name */
        final /* synthetic */ HealthSettingsFragment f1197f;

        /* renamed from: g  reason: collision with root package name */
        final /* synthetic */ DrinkConfig f1198g;

        /* compiled from: HealthSettingsFragment.kt */
        static final class a implements d.c {
            final /* synthetic */ b a;

            a(b bVar) {
                this.a = bVar;
            }

            public final void a(int i2, int i3, int i4, int i5, String str) {
                b bVar = this.a;
                DrinkConfig drinkConfig = bVar.f1198g;
                drinkConfig.amStartHH = i2;
                drinkConfig.amStartMM = i3;
                drinkConfig.amEndHH = i4;
                drinkConfig.amEndMM = i5;
                HealthSettingsFragment healthSettingsFragment = bVar.f1197f;
                TextView textView = bVar.e.K;
                kotlin.jvm.internal.i.a((Object) textView, "tvDrinkAm");
                DrinkConfig drinkConfig2 = this.a.f1198g;
                healthSettingsFragment.a(textView, drinkConfig2.amStartHH, drinkConfig2.amStartMM, drinkConfig2.amEndHH, drinkConfig2.amEndMM);
                b bVar2 = this.a;
                HealthSettingsFragment healthSettingsFragment2 = bVar2.f1197f;
                DrinkConfig drinkConfig3 = bVar2.f1198g;
                kotlin.jvm.internal.i.a((Object) drinkConfig3, "drink");
                healthSettingsFragment2.a(drinkConfig3);
            }
        }

        b(u0 u0Var, HealthSettingsFragment healthSettingsFragment, DrinkConfig drinkConfig, InactivityConfig inactivityConfig) {
            this.e = u0Var;
            this.f1197f = healthSettingsFragment;
            this.f1198g = drinkConfig;
        }

        public final void onClick(View view) {
            d.b b = this.f1197f.s0();
            TextView textView = this.e.K;
            kotlin.jvm.internal.i.a((Object) textView, "tvDrinkAm");
            b.a(textView.getText().toString());
            b.a((d.c) new a(this));
            b.a().show();
        }
    }

    /* compiled from: HealthSettingsFragment.kt */
    static final class c implements View.OnClickListener {
        final /* synthetic */ u0 e;

        /* renamed from: f  reason: collision with root package name */
        final /* synthetic */ HealthSettingsFragment f1199f;

        /* renamed from: g  reason: collision with root package name */
        final /* synthetic */ DrinkConfig f1200g;

        /* compiled from: HealthSettingsFragment.kt */
        static final class a implements d.c {
            final /* synthetic */ c a;

            a(c cVar) {
                this.a = cVar;
            }

            public final void a(int i2, int i3, int i4, int i5, String str) {
                c cVar = this.a;
                DrinkConfig drinkConfig = cVar.f1200g;
                drinkConfig.pmStartHH = i2;
                drinkConfig.pmStartMM = i3;
                drinkConfig.pmEndHH = i4;
                drinkConfig.pmEndMM = i5;
                HealthSettingsFragment healthSettingsFragment = cVar.f1199f;
                TextView textView = cVar.e.M;
                kotlin.jvm.internal.i.a((Object) textView, "tvDrinkPm");
                DrinkConfig drinkConfig2 = this.a.f1200g;
                healthSettingsFragment.a(textView, drinkConfig2.pmStartHH, drinkConfig2.pmStartMM, drinkConfig2.pmEndHH, drinkConfig2.pmEndMM);
                c cVar2 = this.a;
                HealthSettingsFragment healthSettingsFragment2 = cVar2.f1199f;
                DrinkConfig drinkConfig3 = cVar2.f1200g;
                kotlin.jvm.internal.i.a((Object) drinkConfig3, "drink");
                healthSettingsFragment2.a(drinkConfig3);
            }
        }

        c(u0 u0Var, HealthSettingsFragment healthSettingsFragment, DrinkConfig drinkConfig, InactivityConfig inactivityConfig) {
            this.e = u0Var;
            this.f1199f = healthSettingsFragment;
            this.f1200g = drinkConfig;
        }

        public final void onClick(View view) {
            d.b b = this.f1199f.s0();
            TextView textView = this.e.M;
            kotlin.jvm.internal.i.a((Object) textView, "tvDrinkPm");
            b.a(textView.getText().toString());
            b.a((d.c) new a(this));
            b.a().show();
        }
    }

    /* compiled from: HealthSettingsFragment.kt */
    static final class d implements View.OnClickListener {
        final /* synthetic */ u0 e;

        /* renamed from: f  reason: collision with root package name */
        final /* synthetic */ HealthSettingsFragment f1201f;

        /* renamed from: g  reason: collision with root package name */
        final /* synthetic */ DrinkConfig f1202g;

        /* compiled from: HealthSettingsFragment.kt */
        static final class a implements e.c {
            final /* synthetic */ d a;

            a(d dVar) {
                this.a = dVar;
            }

            public final void a(String str) {
                DrinkConfig drinkConfig = this.a.f1202g;
                kotlin.jvm.internal.i.a((Object) str, "range");
                drinkConfig.interval = Integer.parseInt(str);
                d dVar = this.a;
                HealthSettingsFragment healthSettingsFragment = dVar.f1201f;
                TextView textView = dVar.e.L;
                kotlin.jvm.internal.i.a((Object) textView, "tvDrinkInterval");
                healthSettingsFragment.a(textView, this.a.f1202g.interval);
                d dVar2 = this.a;
                HealthSettingsFragment healthSettingsFragment2 = dVar2.f1201f;
                DrinkConfig drinkConfig2 = dVar2.f1202g;
                kotlin.jvm.internal.i.a((Object) drinkConfig2, "drink");
                healthSettingsFragment2.a(drinkConfig2);
            }
        }

        d(u0 u0Var, HealthSettingsFragment healthSettingsFragment, DrinkConfig drinkConfig, InactivityConfig inactivityConfig) {
            this.e = u0Var;
            this.f1201f = healthSettingsFragment;
            this.f1202g = drinkConfig;
        }

        public final void onClick(View view) {
            e.b c = this.f1201f.t0();
            TextView textView = this.e.L;
            kotlin.jvm.internal.i.a((Object) textView, "tvDrinkInterval");
            c.a(textView.getText().toString());
            c.a((e.c) new a(this));
            c.a().show();
        }
    }

    /* compiled from: HealthSettingsFragment.kt */
    static final class e implements CompoundButton.OnCheckedChangeListener {
        final /* synthetic */ HealthSettingsFragment a;
        final /* synthetic */ InactivityConfig b;

        e(HealthSettingsFragment healthSettingsFragment, DrinkConfig drinkConfig, InactivityConfig inactivityConfig) {
            this.a = healthSettingsFragment;
            this.b = inactivityConfig;
        }

        public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
            InactivityConfig inactivityConfig = this.b;
            inactivityConfig.enable = z;
            HealthSettingsFragment healthSettingsFragment = this.a;
            kotlin.jvm.internal.i.a((Object) inactivityConfig, "inactivity");
            healthSettingsFragment.a(inactivityConfig);
        }
    }

    /* compiled from: HealthSettingsFragment.kt */
    static final class f implements View.OnClickListener {
        final /* synthetic */ u0 e;

        /* renamed from: f  reason: collision with root package name */
        final /* synthetic */ HealthSettingsFragment f1203f;

        /* renamed from: g  reason: collision with root package name */
        final /* synthetic */ InactivityConfig f1204g;

        /* compiled from: HealthSettingsFragment.kt */
        static final class a implements d.c {
            final /* synthetic */ f a;

            a(f fVar) {
                this.a = fVar;
            }

            public final void a(int i2, int i3, int i4, int i5, String str) {
                f fVar = this.a;
                InactivityConfig inactivityConfig = fVar.f1204g;
                inactivityConfig.amStartHH = i2;
                inactivityConfig.amStartMM = i3;
                inactivityConfig.amEndHH = i4;
                inactivityConfig.amEndMM = i5;
                HealthSettingsFragment healthSettingsFragment = fVar.f1203f;
                TextView textView = fVar.e.N;
                kotlin.jvm.internal.i.a((Object) textView, "tvInactivityAm");
                InactivityConfig inactivityConfig2 = this.a.f1204g;
                healthSettingsFragment.a(textView, inactivityConfig2.amStartHH, inactivityConfig2.amStartMM, inactivityConfig2.amEndHH, inactivityConfig2.amEndMM);
                f fVar2 = this.a;
                HealthSettingsFragment healthSettingsFragment2 = fVar2.f1203f;
                InactivityConfig inactivityConfig3 = fVar2.f1204g;
                kotlin.jvm.internal.i.a((Object) inactivityConfig3, "inactivity");
                healthSettingsFragment2.a(inactivityConfig3);
            }
        }

        f(u0 u0Var, HealthSettingsFragment healthSettingsFragment, DrinkConfig drinkConfig, InactivityConfig inactivityConfig) {
            this.e = u0Var;
            this.f1203f = healthSettingsFragment;
            this.f1204g = inactivityConfig;
        }

        public final void onClick(View view) {
            d.b b = this.f1203f.s0();
            TextView textView = this.e.N;
            kotlin.jvm.internal.i.a((Object) textView, "tvInactivityAm");
            b.a(textView.getText().toString());
            b.a((d.c) new a(this));
            b.a().show();
        }
    }

    /* compiled from: HealthSettingsFragment.kt */
    static final class g implements View.OnClickListener {
        final /* synthetic */ u0 e;

        /* renamed from: f  reason: collision with root package name */
        final /* synthetic */ HealthSettingsFragment f1205f;

        /* renamed from: g  reason: collision with root package name */
        final /* synthetic */ InactivityConfig f1206g;

        /* compiled from: HealthSettingsFragment.kt */
        static final class a implements d.c {
            final /* synthetic */ g a;

            a(g gVar) {
                this.a = gVar;
            }

            public final void a(int i2, int i3, int i4, int i5, String str) {
                g gVar = this.a;
                InactivityConfig inactivityConfig = gVar.f1206g;
                inactivityConfig.pmStartHH = i2;
                inactivityConfig.pmStartMM = i3;
                inactivityConfig.pmEndHH = i4;
                inactivityConfig.pmEndMM = i5;
                HealthSettingsFragment healthSettingsFragment = gVar.f1205f;
                TextView textView = gVar.e.P;
                kotlin.jvm.internal.i.a((Object) textView, "tvInactivityPm");
                InactivityConfig inactivityConfig2 = this.a.f1206g;
                healthSettingsFragment.a(textView, inactivityConfig2.pmStartHH, inactivityConfig2.pmStartMM, inactivityConfig2.pmEndHH, inactivityConfig2.pmEndMM);
                g gVar2 = this.a;
                HealthSettingsFragment healthSettingsFragment2 = gVar2.f1205f;
                InactivityConfig inactivityConfig3 = gVar2.f1206g;
                kotlin.jvm.internal.i.a((Object) inactivityConfig3, "inactivity");
                healthSettingsFragment2.a(inactivityConfig3);
            }
        }

        g(u0 u0Var, HealthSettingsFragment healthSettingsFragment, DrinkConfig drinkConfig, InactivityConfig inactivityConfig) {
            this.e = u0Var;
            this.f1205f = healthSettingsFragment;
            this.f1206g = inactivityConfig;
        }

        public final void onClick(View view) {
            d.b b = this.f1205f.s0();
            TextView textView = this.e.P;
            kotlin.jvm.internal.i.a((Object) textView, "tvInactivityPm");
            b.a(textView.getText().toString());
            b.a((d.c) new a(this));
            b.a().show();
        }
    }

    /* compiled from: HealthSettingsFragment.kt */
    static final class h implements View.OnClickListener {
        final /* synthetic */ u0 e;

        /* renamed from: f  reason: collision with root package name */
        final /* synthetic */ HealthSettingsFragment f1207f;

        /* renamed from: g  reason: collision with root package name */
        final /* synthetic */ InactivityConfig f1208g;

        /* compiled from: HealthSettingsFragment.kt */
        static final class a implements e.c {
            final /* synthetic */ h a;

            a(h hVar) {
                this.a = hVar;
            }

            public final void a(String str) {
                InactivityConfig inactivityConfig = this.a.f1208g;
                kotlin.jvm.internal.i.a((Object) str, "range");
                inactivityConfig.interval = Integer.parseInt(str);
                h hVar = this.a;
                HealthSettingsFragment healthSettingsFragment = hVar.f1207f;
                TextView textView = hVar.e.O;
                kotlin.jvm.internal.i.a((Object) textView, "tvInactivityInterval");
                healthSettingsFragment.a(textView, this.a.f1208g.interval);
                h hVar2 = this.a;
                HealthSettingsFragment healthSettingsFragment2 = hVar2.f1207f;
                InactivityConfig inactivityConfig2 = hVar2.f1208g;
                kotlin.jvm.internal.i.a((Object) inactivityConfig2, "inactivity");
                healthSettingsFragment2.a(inactivityConfig2);
            }
        }

        h(u0 u0Var, HealthSettingsFragment healthSettingsFragment, DrinkConfig drinkConfig, InactivityConfig inactivityConfig) {
            this.e = u0Var;
            this.f1207f = healthSettingsFragment;
            this.f1208g = inactivityConfig;
        }

        public final void onClick(View view) {
            e.b c = this.f1207f.t0();
            TextView textView = this.e.O;
            kotlin.jvm.internal.i.a((Object) textView, "tvInactivityInterval");
            c.a(textView.getText().toString());
            c.a((e.c) new a(this));
            c.a().show();
        }
    }

    /* compiled from: HealthSettingsFragment.kt */
    static final class i implements View.OnClickListener {
        final /* synthetic */ HealthSettingsFragment e;

        i(HealthSettingsFragment healthSettingsFragment, DrinkConfig drinkConfig, InactivityConfig inactivityConfig) {
            this.e = healthSettingsFragment;
        }

        public final void onClick(View view) {
            BaseActivity.a(this.e.o0(), AlarmSettingActivity.class, false, 2, (Object) null);
        }
    }

    /* compiled from: HealthSettingsFragment.kt */
    static final class j implements View.OnClickListener {
        final /* synthetic */ u0 e;

        j(u0 u0Var) {
            this.e = u0Var;
        }

        public final void onClick(View view) {
            this.e.I.toggle();
        }
    }

    /* compiled from: HealthSettingsFragment.kt */
    static final class k implements View.OnClickListener {
        final /* synthetic */ u0 e;

        k(u0 u0Var) {
            this.e = u0Var;
        }

        public final void onClick(View view) {
            this.e.J.toggle();
        }
    }

    /* access modifiers changed from: private */
    public final d.b s0() {
        return (d.b) this.g0.getValue();
    }

    /* access modifiers changed from: private */
    public final e.b t0() {
        return (e.b) this.h0.getValue();
    }

    private final CL880Manager u0() {
        return (CL880Manager) this.f0.getValue();
    }

    public /* synthetic */ void T() {
        super.T();
        n0();
    }

    public View e(int i2) {
        if (this.i0 == null) {
            this.i0 = new HashMap();
        }
        View view = (View) this.i0.get(Integer.valueOf(i2));
        if (view != null) {
            return view;
        }
        View F = F();
        if (F == null) {
            return null;
        }
        View findViewById = F.findViewById(i2);
        this.i0.put(Integer.valueOf(i2), findViewById);
        return findViewById;
    }

    /* access modifiers changed from: protected */
    public void n(Bundle bundle) {
        com.chileaf.fitness.device.wear.cl880.external.c l = com.chileaf.fitness.device.wear.cl880.external.c.l();
        kotlin.jvm.internal.i.a((Object) l, "SpecManager.getInstance()");
        DrinkConfig b2 = l.b();
        com.chileaf.fitness.device.wear.cl880.external.c l2 = com.chileaf.fitness.device.wear.cl880.external.c.l();
        kotlin.jvm.internal.i.a((Object) l2, "SpecManager.getInstance()");
        InactivityConfig c2 = l2.c();
        u0 u0Var = (u0) p0();
        SwitchButton switchButton = u0Var.I;
        kotlin.jvm.internal.i.a((Object) switchButton, "swDrink");
        switchButton.setChecked(b2.enable);
        u0Var.z.setOnClickListener(new j(u0Var));
        u0Var.I.setOnCheckedChangeListener(new a(this, b2, c2));
        TextView textView = u0Var.K;
        kotlin.jvm.internal.i.a((Object) textView, "tvDrinkAm");
        a(textView, b2.amStartHH, b2.amStartMM, b2.amEndHH, b2.amEndMM);
        u0Var.A.setOnClickListener(new b(u0Var, this, b2, c2));
        TextView textView2 = u0Var.M;
        kotlin.jvm.internal.i.a((Object) textView2, "tvDrinkPm");
        a(textView2, b2.pmStartHH, b2.pmStartMM, b2.pmEndHH, b2.pmEndMM);
        u0Var.C.setOnClickListener(new c(u0Var, this, b2, c2));
        TextView textView3 = u0Var.L;
        kotlin.jvm.internal.i.a((Object) textView3, "tvDrinkInterval");
        a(textView3, b2.interval);
        u0Var.B.setOnClickListener(new d(u0Var, this, b2, c2));
        SwitchButton switchButton2 = u0Var.J;
        kotlin.jvm.internal.i.a((Object) switchButton2, "swInactivity");
        switchButton2.setChecked(c2.enable);
        u0Var.D.setOnClickListener(new k(u0Var));
        u0Var.J.setOnCheckedChangeListener(new e(this, b2, c2));
        TextView textView4 = u0Var.N;
        kotlin.jvm.internal.i.a((Object) textView4, "tvInactivityAm");
        a(textView4, c2.amStartHH, c2.amStartMM, c2.amEndHH, c2.amEndMM);
        u0Var.E.setOnClickListener(new f(u0Var, this, b2, c2));
        TextView textView5 = u0Var.P;
        kotlin.jvm.internal.i.a((Object) textView5, "tvInactivityPm");
        a(textView5, c2.pmStartHH, c2.pmStartMM, c2.pmEndHH, c2.pmEndMM);
        u0Var.G.setOnClickListener(new g(u0Var, this, b2, c2));
        TextView textView6 = u0Var.O;
        kotlin.jvm.internal.i.a((Object) textView6, "tvInactivityInterval");
        a(textView6, c2.interval);
        u0Var.F.setOnClickListener(new h(u0Var, this, b2, c2));
        u0Var.H.setOnClickListener(new i(this, b2, c2));
    }

    public void n0() {
        HashMap hashMap = this.i0;
        if (hashMap != null) {
            hashMap.clear();
        }
    }

    /* access modifiers changed from: protected */
    public BaseViewModel q0() {
        return null;
    }

    /* access modifiers changed from: protected */
    public int r0() {
        return R$layout.fragment_health_setting;
    }

    /* access modifiers changed from: private */
    public final void a(TextView textView, int i2, int i3, int i4, int i5) {
        textView.setText(f(i2) + ':' + f(i3) + '-' + f(i4) + ':' + f(i5));
    }

    /* access modifiers changed from: private */
    public final void a(TextView textView, int i2) {
        textView.setText(i2 + a((int) R$string.interval_unit));
    }

    /* access modifiers changed from: private */
    public final void a(DrinkConfig drinkConfig) {
        u0().a(drinkConfig);
        com.chileaf.fitness.device.wear.cl880.external.c l = com.chileaf.fitness.device.wear.cl880.external.c.l();
        kotlin.jvm.internal.i.a((Object) l, "SpecManager.getInstance()");
        l.a(drinkConfig);
    }

    /* access modifiers changed from: private */
    public final void a(InactivityConfig inactivityConfig) {
        u0().a(inactivityConfig);
        com.chileaf.fitness.device.wear.cl880.external.c l = com.chileaf.fitness.device.wear.cl880.external.c.l();
        kotlin.jvm.internal.i.a((Object) l, "SpecManager.getInstance()");
        l.a(inactivityConfig);
    }
}
