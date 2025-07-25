package com.chileaf.fitness.device.wear.cl880;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;
import com.chileaf.fitness.R$layout;
import com.chileaf.fitness.R$string;
import com.chileaf.fitness.b.g1;
import com.chileaf.fitness.base.BaseViewModel;
import com.chileaf.fitness.ui.c.h;
import com.chileaf.fitness.widget.SwitchButton;
import java.util.HashMap;
import kotlin.jvm.internal.i;

/* compiled from: TrainSettingsFragment.kt */
public final class TrainSettingsFragment extends com.chileaf.fitness.base.a<g1> {
    private final kotlin.d f0 = g.a(TrainSettingsFragment$mPreference$2.INSTANCE);
    private HashMap g0;

    /* compiled from: TrainSettingsFragment.kt */
    public static final class a {
        private a() {
        }

        public /* synthetic */ a(f fVar) {
            this();
        }
    }

    /* compiled from: TrainSettingsFragment.kt */
    static final class b implements View.OnClickListener {
        final /* synthetic */ g1 e;

        /* renamed from: f  reason: collision with root package name */
        final /* synthetic */ TrainSettingsFragment f1215f;

        /* compiled from: TrainSettingsFragment.kt */
        static final class a implements h.c {
            final /* synthetic */ b a;

            a(b bVar) {
                this.a = bVar;
            }

            public final void a(String str) {
                b bVar = this.a;
                TrainSettingsFragment trainSettingsFragment = bVar.f1215f;
                TextView textView = bVar.e.C;
                i.a((Object) textView, "tvWarning");
                trainSettingsFragment.a(textView, str);
                this.a.f1215f.s0().edit().putString("setting_warning", str).apply();
            }
        }

        b(g1 g1Var, TrainSettingsFragment trainSettingsFragment) {
            this.e = g1Var;
            this.f1215f = trainSettingsFragment;
        }

        public final void onClick(View view) {
            h.b a2 = h.a((Activity) this.f1215f.o0());
            TextView textView = this.e.C;
            i.a((Object) textView, "tvWarning");
            a2.a(textView.getText().toString());
            a2.a((h.c) new a(this));
            a2.a().show();
        }
    }

    /* compiled from: TrainSettingsFragment.kt */
    static final class c implements CompoundButton.OnCheckedChangeListener {
        final /* synthetic */ TrainSettingsFragment a;

        c(TrainSettingsFragment trainSettingsFragment) {
            this.a = trainSettingsFragment;
        }

        public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
            this.a.s0().edit().putBoolean("setting_sound", z).apply();
        }
    }

    /* compiled from: TrainSettingsFragment.kt */
    static final class d implements View.OnClickListener {
        final /* synthetic */ g1 e;

        d(g1 g1Var) {
            this.e = g1Var;
        }

        public final void onClick(View view) {
            this.e.B.toggle();
        }
    }

    static {
        new a((f) null);
    }

    /* access modifiers changed from: private */
    public final SharedPreferences s0() {
        return (SharedPreferences) this.f0.getValue();
    }

    public /* synthetic */ void T() {
        super.T();
        n0();
    }

    public View e(int i2) {
        if (this.g0 == null) {
            this.g0 = new HashMap();
        }
        View view = (View) this.g0.get(Integer.valueOf(i2));
        if (view != null) {
            return view;
        }
        View F = F();
        if (F == null) {
            return null;
        }
        View findViewById = F.findViewById(i2);
        this.g0.put(Integer.valueOf(i2), findViewById);
        return findViewById;
    }

    /* access modifiers changed from: protected */
    public void n(Bundle bundle) {
        g1 g1Var = (g1) p0();
        TextView textView = g1Var.C;
        i.a((Object) textView, "tvWarning");
        a(textView, s0().getString("setting_warning", "60"));
        g1Var.A.setOnClickListener(new b(g1Var, this));
        SwitchButton switchButton = g1Var.B;
        i.a((Object) switchButton, "swSound");
        switchButton.setChecked(s0().getBoolean("setting_sound", false));
        g1Var.B.setOnCheckedChangeListener(new c(this));
        g1Var.z.setOnClickListener(new d(g1Var));
    }

    public void n0() {
        HashMap hashMap = this.g0;
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
        return R$layout.fragment_train_setting;
    }

    /* access modifiers changed from: private */
    public final void a(TextView textView, String str) {
        textView.setText(i.a(str, (Object) a((int) R$string.heart_rate_unit)));
    }
}
