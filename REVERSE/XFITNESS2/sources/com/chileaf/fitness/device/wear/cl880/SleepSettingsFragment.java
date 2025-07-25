package com.chileaf.fitness.device.wear.cl880;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.chileaf.fitness.R$layout;
import com.chileaf.fitness.b.e1;
import com.chileaf.fitness.base.BaseViewModel;
import com.chileaf.fitness.device.wear.cl880.model.SleepConfig;
import com.chileaf.fitness.ui.c.g;
import java.util.HashMap;
import kotlin.d;
import kotlin.jvm.internal.i;

/* compiled from: SleepSettingsFragment.kt */
public final class SleepSettingsFragment extends com.chileaf.fitness.base.a<e1> {
    private final d f0 = g.a(SleepSettingsFragment$mManager$2.INSTANCE);
    private final d g0 = g.a(new SleepSettingsFragment$mSingleTime$2(this));
    private HashMap h0;

    /* compiled from: SleepSettingsFragment.kt */
    static final class a implements View.OnClickListener {
        final /* synthetic */ e1 e;

        /* renamed from: f  reason: collision with root package name */
        final /* synthetic */ SleepSettingsFragment f1209f;

        /* renamed from: g  reason: collision with root package name */
        final /* synthetic */ SleepConfig f1210g;

        /* renamed from: com.chileaf.fitness.device.wear.cl880.SleepSettingsFragment$a$a  reason: collision with other inner class name */
        /* compiled from: SleepSettingsFragment.kt */
        static final class C0064a implements g.c {
            final /* synthetic */ a a;

            C0064a(a aVar) {
                this.a = aVar;
            }

            public final void a(int i2, int i3, String str) {
                a aVar = this.a;
                SleepConfig sleepConfig = aVar.f1210g;
                sleepConfig.startHH = i2;
                sleepConfig.startMM = i3;
                SleepSettingsFragment sleepSettingsFragment = aVar.f1209f;
                TextView textView = aVar.e.D;
                i.a((Object) textView, "tvSleep");
                SleepConfig sleepConfig2 = this.a.f1210g;
                sleepSettingsFragment.a(textView, sleepConfig2.startHH, sleepConfig2.startMM);
                a aVar2 = this.a;
                SleepSettingsFragment sleepSettingsFragment2 = aVar2.f1209f;
                SleepConfig sleepConfig3 = aVar2.f1210g;
                i.a((Object) sleepConfig3, "sleepConfig");
                sleepSettingsFragment2.a(sleepConfig3);
            }
        }

        a(e1 e1Var, SleepSettingsFragment sleepSettingsFragment, SleepConfig sleepConfig) {
            this.e = e1Var;
            this.f1209f = sleepSettingsFragment;
            this.f1210g = sleepConfig;
        }

        public final void onClick(View view) {
            g.b b = this.f1209f.t0();
            TextView textView = this.e.D;
            i.a((Object) textView, "tvSleep");
            b.a(textView.getText().toString());
            b.a((g.c) new C0064a(this));
            b.a().show();
        }
    }

    /* compiled from: SleepSettingsFragment.kt */
    static final class b implements View.OnClickListener {
        final /* synthetic */ e1 e;

        /* renamed from: f  reason: collision with root package name */
        final /* synthetic */ SleepSettingsFragment f1211f;

        /* renamed from: g  reason: collision with root package name */
        final /* synthetic */ SleepConfig f1212g;

        /* compiled from: SleepSettingsFragment.kt */
        static final class a implements g.c {
            final /* synthetic */ b a;

            a(b bVar) {
                this.a = bVar;
            }

            public final void a(int i2, int i3, String str) {
                b bVar = this.a;
                SleepConfig sleepConfig = bVar.f1212g;
                sleepConfig.workWakeHH = i2;
                sleepConfig.workWakeMM = i3;
                SleepSettingsFragment sleepSettingsFragment = bVar.f1211f;
                TextView textView = bVar.e.E;
                i.a((Object) textView, "tvWork");
                SleepConfig sleepConfig2 = this.a.f1212g;
                sleepSettingsFragment.a(textView, sleepConfig2.workWakeHH, sleepConfig2.workWakeMM);
                b bVar2 = this.a;
                SleepSettingsFragment sleepSettingsFragment2 = bVar2.f1211f;
                SleepConfig sleepConfig3 = bVar2.f1212g;
                i.a((Object) sleepConfig3, "sleepConfig");
                sleepSettingsFragment2.a(sleepConfig3);
            }
        }

        b(e1 e1Var, SleepSettingsFragment sleepSettingsFragment, SleepConfig sleepConfig) {
            this.e = e1Var;
            this.f1211f = sleepSettingsFragment;
            this.f1212g = sleepConfig;
        }

        public final void onClick(View view) {
            g.b b = this.f1211f.t0();
            TextView textView = this.e.E;
            i.a((Object) textView, "tvWork");
            b.a(textView.getText().toString());
            b.a((g.c) new a(this));
            b.a().show();
        }
    }

    /* compiled from: SleepSettingsFragment.kt */
    static final class c implements View.OnClickListener {
        final /* synthetic */ e1 e;

        /* renamed from: f  reason: collision with root package name */
        final /* synthetic */ SleepSettingsFragment f1213f;

        /* renamed from: g  reason: collision with root package name */
        final /* synthetic */ SleepConfig f1214g;

        /* compiled from: SleepSettingsFragment.kt */
        static final class a implements g.c {
            final /* synthetic */ c a;

            a(c cVar) {
                this.a = cVar;
            }

            public final void a(int i2, int i3, String str) {
                c cVar = this.a;
                SleepConfig sleepConfig = cVar.f1214g;
                sleepConfig.restWakeHH = i2;
                sleepConfig.restWakeMM = i3;
                SleepSettingsFragment sleepSettingsFragment = cVar.f1213f;
                TextView textView = cVar.e.C;
                i.a((Object) textView, "tvRest");
                SleepConfig sleepConfig2 = this.a.f1214g;
                sleepSettingsFragment.a(textView, sleepConfig2.restWakeHH, sleepConfig2.restWakeMM);
                c cVar2 = this.a;
                SleepSettingsFragment sleepSettingsFragment2 = cVar2.f1213f;
                SleepConfig sleepConfig3 = cVar2.f1214g;
                i.a((Object) sleepConfig3, "sleepConfig");
                sleepSettingsFragment2.a(sleepConfig3);
            }
        }

        c(e1 e1Var, SleepSettingsFragment sleepSettingsFragment, SleepConfig sleepConfig) {
            this.e = e1Var;
            this.f1213f = sleepSettingsFragment;
            this.f1214g = sleepConfig;
        }

        public final void onClick(View view) {
            g.b b = this.f1213f.t0();
            TextView textView = this.e.C;
            i.a((Object) textView, "tvRest");
            b.a(textView.getText().toString());
            b.a((g.c) new a(this));
            b.a().show();
        }
    }

    private final CL880Manager s0() {
        return (CL880Manager) this.f0.getValue();
    }

    /* access modifiers changed from: private */
    public final g.b t0() {
        return (g.b) this.g0.getValue();
    }

    public /* synthetic */ void T() {
        super.T();
        n0();
    }

    public View e(int i2) {
        if (this.h0 == null) {
            this.h0 = new HashMap();
        }
        View view = (View) this.h0.get(Integer.valueOf(i2));
        if (view != null) {
            return view;
        }
        View F = F();
        if (F == null) {
            return null;
        }
        View findViewById = F.findViewById(i2);
        this.h0.put(Integer.valueOf(i2), findViewById);
        return findViewById;
    }

    /* access modifiers changed from: protected */
    public void n(Bundle bundle) {
        com.chileaf.fitness.device.wear.cl880.external.c l = com.chileaf.fitness.device.wear.cl880.external.c.l();
        i.a((Object) l, "SpecManager.getInstance()");
        SleepConfig e = l.e();
        e1 e1Var = (e1) p0();
        TextView textView = e1Var.D;
        i.a((Object) textView, "tvSleep");
        a(textView, e.startHH, e.startMM);
        e1Var.A.setOnClickListener(new a(e1Var, this, e));
        TextView textView2 = e1Var.E;
        i.a((Object) textView2, "tvWork");
        a(textView2, e.workWakeHH, e.workWakeMM);
        e1Var.B.setOnClickListener(new b(e1Var, this, e));
        TextView textView3 = e1Var.C;
        i.a((Object) textView3, "tvRest");
        a(textView3, e.restWakeHH, e.restWakeMM);
        e1Var.z.setOnClickListener(new c(e1Var, this, e));
    }

    public void n0() {
        HashMap hashMap = this.h0;
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
        return R$layout.fragment_sleep_setting;
    }

    /* access modifiers changed from: private */
    public final void a(TextView textView, int i2, int i3) {
        textView.setText(f(i2) + ':' + f(i3));
    }

    /* access modifiers changed from: private */
    public final void a(SleepConfig sleepConfig) {
        s0().a(sleepConfig);
        com.chileaf.fitness.device.wear.cl880.external.c l = com.chileaf.fitness.device.wear.cl880.external.c.l();
        i.a((Object) l, "SpecManager.getInstance()");
        l.a(sleepConfig);
    }
}
