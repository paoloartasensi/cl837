package com.chileaf.fitness.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.chileaf.fitness.R$drawable;
import com.chileaf.fitness.R$layout;
import com.chileaf.fitness.R$string;
import com.chileaf.fitness.b.q0;
import com.chileaf.fitness.base.BaseActivity;
import com.chileaf.fitness.base.BaseViewModel;
import com.chileaf.fitness.ui.activity.DevicesActivity;
import java.util.HashMap;
import kotlin.jvm.internal.i;

/* compiled from: BoxingFragment.kt */
public final class a extends com.chileaf.fitness.base.a<q0> {
    private HashMap f0;

    /* renamed from: com.chileaf.fitness.ui.fragment.a$a  reason: collision with other inner class name */
    /* compiled from: BoxingFragment.kt */
    public static final class C0071a {
        private C0071a() {
        }

        public /* synthetic */ C0071a(f fVar) {
            this();
        }
    }

    /* compiled from: BoxingFragment.kt */
    static final class b implements View.OnClickListener {
        final /* synthetic */ a e;

        b(a aVar) {
            this.e = aVar;
        }

        public final void onClick(View view) {
            Intent intent = new Intent(this.e.o0(), DevicesActivity.class);
            intent.putExtra("extra_name", "Sampling");
            BaseActivity.a(this.e.o0(), intent, false, 2, (Object) null);
        }
    }

    static {
        new C0071a((f) null);
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
        q0 q0Var = (q0) p0();
        q0Var.A.setImageResource(R$drawable.ic_boxing_box);
        TextView textView = q0Var.B;
        i.a((Object) textView, "tvDescription");
        textView.setText(a((int) R$string.white_space) + a((int) R$string.boxing_box));
        q0Var.z.setOnClickListener(new b(this));
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
        return R$layout.fragment_boxing;
    }
}
