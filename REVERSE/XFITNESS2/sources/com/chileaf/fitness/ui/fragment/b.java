package com.chileaf.fitness.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.chileaf.fitness.R$drawable;
import com.chileaf.fitness.R$layout;
import com.chileaf.fitness.R$string;
import com.chileaf.fitness.b.m1;
import com.chileaf.fitness.base.BaseActivity;
import com.chileaf.fitness.base.BaseViewModel;
import com.chileaf.fitness.ui.activity.DevicesActivity;
import java.util.HashMap;
import kotlin.jvm.internal.i;

/* compiled from: WeighFragment.kt */
public final class b extends com.chileaf.fitness.base.a<m1> {
    private HashMap f0;

    /* compiled from: WeighFragment.kt */
    public static final class a {
        private a() {
        }

        public /* synthetic */ a(f fVar) {
            this();
        }
    }

    /* renamed from: com.chileaf.fitness.ui.fragment.b$b  reason: collision with other inner class name */
    /* compiled from: WeighFragment.kt */
    static final class C0072b implements View.OnClickListener {
        final /* synthetic */ b e;

        C0072b(b bVar) {
            this.e = bVar;
        }

        public final void onClick(View view) {
            Intent intent = new Intent(this.e.o0(), DevicesActivity.class);
            intent.putExtra("extra_name", "SWAN");
            BaseActivity.a(this.e.o0(), intent, false, 2, (Object) null);
        }
    }

    static {
        new a((f) null);
    }

    public /* synthetic */ void T() {
        super.T();
        n0();
    }

    public View e(int i2) {
        if (this.f0 == null) {
            this.f0 = new HashMap();
        }
        View view = (View) this.f0.get(Integer.valueOf(i2));
        if (view != null) {
            return view;
        }
        View F = F();
        if (F == null) {
            return null;
        }
        View findViewById = F.findViewById(i2);
        this.f0.put(Integer.valueOf(i2), findViewById);
        return findViewById;
    }

    /* access modifiers changed from: protected */
    public void n(Bundle bundle) {
        m1 m1Var = (m1) p0();
        m1Var.A.setImageResource(R$drawable.ic_weigh_said);
        TextView textView = m1Var.B;
        i.a((Object) textView, "tvDescription");
        textView.setText(a((int) R$string.white_space) + a((int) R$string.weigh_said));
        m1Var.z.setOnClickListener(new C0072b(this));
    }

    public void n0() {
        HashMap hashMap = this.f0;
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
        return R$layout.fragment_weigh;
    }
}
