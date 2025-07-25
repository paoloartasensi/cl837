package com.chileaf.fitness.ui.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.widget.AppCompatTextView;
import com.chileaf.fitness.R$layout;
import com.chileaf.fitness.R$string;
import com.chileaf.fitness.b.o0;
import com.chileaf.fitness.base.BaseActivity;
import com.chileaf.fitness.base.BaseViewModel;
import com.chileaf.fitness.ui.activity.WebResultActivity;
import java.util.HashMap;
import kotlin.jvm.internal.i;

@SuppressLint({"SetTextI18n"})
/* compiled from: AboutFragment.kt */
public final class AboutFragment extends com.chileaf.fitness.base.a<o0> {
    private HashMap f0;

    /* compiled from: AboutFragment.kt */
    public static final class a {
        private a() {
        }

        public /* synthetic */ a(f fVar) {
            this();
        }
    }

    /* compiled from: AboutFragment.kt */
    static final class b implements View.OnClickListener {
        final /* synthetic */ AboutFragment e;

        b(AboutFragment aboutFragment) {
            this.e = aboutFragment;
        }

        public final void onClick(View view) {
            WebResultActivity.a aVar = WebResultActivity.J;
            BaseActivity a = this.e.o0();
            String a2 = this.e.a((int) R$string.about_chileaf);
            i.a((Object) a2, "getString(R.string.about_chileaf)");
            aVar.a(a, a2, "https://www.chileaf.com/");
        }
    }

    /* compiled from: AboutFragment.kt */
    static final class c implements View.OnClickListener {
        final /* synthetic */ AboutFragment e;

        c(AboutFragment aboutFragment) {
            this.e = aboutFragment;
        }

        public final void onClick(View view) {
            WebResultActivity.a aVar = WebResultActivity.J;
            BaseActivity a = this.e.o0();
            String a2 = this.e.a((int) R$string.latest_product);
            i.a((Object) a2, "getString(R.string.latest_product)");
            aVar.a(a, a2, "https://www.chileaf.com/page1000007");
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
        o0 o0Var = (o0) p0();
        AppCompatTextView appCompatTextView = o0Var.B;
        i.a((Object) appCompatTextView, "tvVersion");
        appCompatTextView.setText(a(2131755053) + ' ' + com.chileaf.fitness.d.a.a.a(o0()));
        o0Var.z.setOnClickListener(new b(this));
        o0Var.A.setOnClickListener(new c(this));
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
        return R$layout.fragment_about;
    }
}
