package com.chileaf.fitness.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.chileaf.fitness.R$layout;
import com.chileaf.fitness.R$string;
import com.chileaf.fitness.b.a1;
import com.chileaf.fitness.base.BaseActivity;
import com.chileaf.fitness.base.BaseViewModel;
import com.chileaf.fitness.model.Component;
import com.chileaf.fitness.ui.activity.DevicesActivity;
import java.util.HashMap;
import kotlin.d;
import kotlin.jvm.internal.i;

/* compiled from: RidingProductFragment.kt */
public final class RidingProductFragment extends com.chileaf.fitness.base.a<a1> {
    public static final a h0 = new a((f) null);
    private final d f0 = g.a(new RidingProductFragment$mComponent$2(this));
    private HashMap g0;

    /* compiled from: RidingProductFragment.kt */
    public static final class a {
        private a() {
        }

        public final RidingProductFragment a(Component component) {
            i.b(component, "component");
            RidingProductFragment ridingProductFragment = new RidingProductFragment();
            Bundle bundle = new Bundle();
            bundle.putParcelable("riding_component", component);
            ridingProductFragment.m(bundle);
            return ridingProductFragment;
        }

        public /* synthetic */ a(f fVar) {
            this();
        }
    }

    /* compiled from: RidingProductFragment.kt */
    static final class b implements View.OnClickListener {
        final /* synthetic */ Component e;

        /* renamed from: f  reason: collision with root package name */
        final /* synthetic */ RidingProductFragment f1249f;

        b(Component component, RidingProductFragment ridingProductFragment) {
            this.e = component;
            this.f1249f = ridingProductFragment;
        }

        public final void onClick(View view) {
            Intent intent = new Intent(this.f1249f.o0(), DevicesActivity.class);
            intent.putExtra("extra_name", this.e.c());
            BaseActivity.a(this.f1249f.o0(), intent, false, 2, (Object) null);
        }
    }

    private final Component s0() {
        return (Component) this.f0.getValue();
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
        Component s0 = s0();
        if (s0 != null) {
            a1 a1Var = (a1) p0();
            a1Var.A.setImageResource(s0.b());
            TextView textView = a1Var.B;
            i.a((Object) textView, "tvDescription");
            textView.setText(a((int) R$string.white_space) + a(s0.a()));
            a1Var.z.setOnClickListener(new b(s0, this));
        }
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
        return R$layout.fragment_riding_product;
    }
}
