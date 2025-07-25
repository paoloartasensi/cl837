package com.chileaf.fitness.ui.fragment;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.databinding.g;
import androidx.fragment.app.j;
import androidx.viewpager.widget.ViewPager;
import com.chileaf.fitness.R$layout;
import com.chileaf.fitness.b.i1;
import com.chileaf.fitness.b.w1;
import com.chileaf.fitness.base.BaseViewModel;
import com.chileaf.fitness.base.a;
import com.chileaf.fitness.ui.b.b;
import com.google.android.material.tabs.TabLayout;
import java.util.HashMap;
import kotlin.d;
import kotlin.jvm.internal.i;

/* compiled from: WearFragment.kt */
public final class WearFragment extends a<i1> implements TabLayout.d {
    private final d f0 = g.a(WearFragment$mTitles$2.INSTANCE);
    private final d g0 = g.a(WearFragment$mImages$2.INSTANCE);
    private final d h0 = g.a(WearFragment$mDescriptions$2.INSTANCE);
    private final d i0 = g.a(new WearFragment$mFragments$2(this));
    private HashMap j0;

    /* access modifiers changed from: private */
    public final Integer[] s0() {
        return (Integer[]) this.h0.getValue();
    }

    private final WearProductFragment[] t0() {
        return (WearProductFragment[]) this.i0.getValue();
    }

    /* access modifiers changed from: private */
    public final Integer[] u0() {
        return (Integer[]) this.g0.getValue();
    }

    /* access modifiers changed from: private */
    public final String[] v0() {
        return (String[]) this.f0.getValue();
    }

    public /* synthetic */ void T() {
        super.T();
        n0();
    }

    public void a(TabLayout.g gVar) {
        i.b(gVar, "tab");
    }

    public void c(TabLayout.g gVar) {
        i.b(gVar, "tab");
    }

    public View e(int i2) {
        if (this.j0 == null) {
            this.j0 = new HashMap();
        }
        View view = (View) this.j0.get(Integer.valueOf(i2));
        if (view != null) {
            return view;
        }
        View F = F();
        if (F == null) {
            return null;
        }
        View findViewById = F.findViewById(i2);
        this.j0.put(Integer.valueOf(i2), findViewById);
        return findViewById;
    }

    /* access modifiers changed from: protected */
    public void n(Bundle bundle) {
        TabLayout tabLayout = ((i1) p0()).z;
        i.a((Object) tabLayout, "mBinding.tabWear");
        for (String text : v0()) {
            w1 w1Var = (w1) g.a(s(), (int) R$layout.layout_tab_text, (ViewGroup) null, false);
            TextView textView = w1Var.z;
            i.a((Object) textView, "binding.tabTitle");
            textView.setText(text);
            TabLayout.g b = tabLayout.b();
            i.a((Object) b, "tabNavigator.newTab()");
            i.a((Object) w1Var, "binding");
            b.a(w1Var.c());
            tabLayout.a(b);
        }
        String[] v0 = v0();
        WearProductFragment[] t0 = t0();
        j j2 = j();
        i.a((Object) j2, "childFragmentManager");
        b bVar = new b(v0, t0, j2);
        i1 i1Var = (i1) p0();
        ViewPager viewPager = i1Var.A;
        i.a((Object) viewPager, "vpWear");
        viewPager.setCurrentItem(0);
        ViewPager viewPager2 = i1Var.A;
        i.a((Object) viewPager2, "vpWear");
        viewPager2.setAdapter(bVar);
        ViewPager viewPager3 = i1Var.A;
        i.a((Object) viewPager3, "vpWear");
        viewPager3.setOffscreenPageLimit(t0().length);
        tabLayout.addOnTabSelectedListener(this);
    }

    public void n0() {
        HashMap hashMap = this.j0;
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
        return R$layout.fragment_wear;
    }

    public void b(TabLayout.g gVar) {
        i.b(gVar, "tab");
        ((i1) p0()).A.a(gVar.c(), false);
    }
}
