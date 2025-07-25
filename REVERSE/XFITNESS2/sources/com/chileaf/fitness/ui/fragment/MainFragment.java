package com.chileaf.fitness.ui.fragment;

import android.os.Bundle;
import android.view.View;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;
import com.chileaf.fitness.R$layout;
import com.chileaf.fitness.b.w0;
import com.chileaf.fitness.base.BaseViewModel;
import com.chileaf.fitness.widget.c;
import com.google.android.material.tabs.TabLayout;
import java.util.HashMap;
import kotlin.d;
import kotlin.jvm.internal.i;

/* compiled from: MainFragment.kt */
public final class MainFragment extends com.chileaf.fitness.base.a<w0> {
    private final d f0 = g.a(new MainFragment$mTitles$2(this));
    private final d g0 = g.a(MainFragment$mFragments$2.INSTANCE);
    private HashMap h0;

    /* compiled from: MainFragment.kt */
    static final class a implements c.a {
        final /* synthetic */ MainFragment a;

        a(MainFragment mainFragment, b bVar) {
            this.a = mainFragment;
        }

        public final void a(TabLayout.g gVar, int i2) {
            i.b(gVar, "tab");
            gVar.b((CharSequence) this.a.t0()[i2]);
        }
    }

    /* compiled from: MainFragment.kt */
    public static final class b extends FragmentStateAdapter {

        /* renamed from: i  reason: collision with root package name */
        final /* synthetic */ MainFragment f1248i;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        b(MainFragment mainFragment, Fragment fragment) {
            super(fragment);
            this.f1248i = mainFragment;
        }

        public int getItemCount() {
            return this.f1248i.s0().length;
        }

        public com.chileaf.fitness.base.a<? extends ViewDataBinding> a(int i2) {
            return this.f1248i.s0()[i2];
        }
    }

    /* access modifiers changed from: private */
    public final com.chileaf.fitness.base.a<? extends ViewDataBinding>[] s0() {
        return (com.chileaf.fitness.base.a[]) this.g0.getValue();
    }

    /* access modifiers changed from: private */
    public final String[] t0() {
        return (String[]) this.f0.getValue();
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
        b bVar = new b(this, this);
        w0 w0Var = (w0) p0();
        ViewPager2 viewPager2 = w0Var.A;
        i.a((Object) viewPager2, "vpMain");
        viewPager2.setCurrentItem(0);
        ViewPager2 viewPager22 = w0Var.A;
        i.a((Object) viewPager22, "vpMain");
        viewPager22.setAdapter(bVar);
        ViewPager2 viewPager23 = w0Var.A;
        i.a((Object) viewPager23, "vpMain");
        viewPager23.setOffscreenPageLimit(t0().length);
        new c(w0Var.z, w0Var.A, new a(this, bVar)).a();
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
        return R$layout.fragment_main;
    }
}
