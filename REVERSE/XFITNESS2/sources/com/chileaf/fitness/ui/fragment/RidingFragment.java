package com.chileaf.fitness.ui.fragment;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.databinding.g;
import androidx.fragment.app.j;
import androidx.viewpager.widget.ViewPager;
import com.chileaf.fitness.R$layout;
import com.chileaf.fitness.b.w1;
import com.chileaf.fitness.b.y0;
import com.chileaf.fitness.base.BaseViewModel;
import com.chileaf.fitness.base.a;
import com.chileaf.fitness.ui.b.b;
import com.google.android.material.tabs.TabLayout;
import java.util.HashMap;
import kotlin.d;
import kotlin.jvm.internal.i;

/* compiled from: RidingFragment.kt */
public final class RidingFragment extends a<y0> implements TabLayout.d {
    private final d f0 = g.a(RidingFragment$mTitles$2.INSTANCE);
    private final d g0 = g.a(RidingFragment$mImages$2.INSTANCE);
    private final d h0 = g.a(RidingFragment$mDescriptions$2.INSTANCE);
    private final d i0 = g.a(new RidingFragment$mFragments$2(this));
    private HashMap j0;

    /* access modifiers changed from: private */
    public final Integer[] s0() {
        return (Integer[]) this.h0.getValue();
    }

    private final RidingProductFragment[] t0() {
        return (RidingProductFragment[]) this.i0.getValue();
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
        TabLayout tabLayout = ((y0) p0()).z;
        i.a((Object) tabLayout, "mBinding.tabRiding");
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
        RidingProductFragment[] t0 = t0();
        j j2 = j();
        i.a((Object) j2, "childFragmentManager");
        b bVar = new b(v0, t0, j2);
        y0 y0Var = (y0) p0();
        ViewPager viewPager = y0Var.A;
        i.a((Object) viewPager, "vpRiding");
        viewPager.setCurrentItem(0);
        ViewPager viewPager2 = y0Var.A;
        i.a((Object) viewPager2, "vpRiding");
        viewPager2.setAdapter(bVar);
        ViewPager viewPager3 = y0Var.A;
        i.a((Object) viewPager3, "vpRiding");
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
        return R$layout.fragment_riding;
    }

    public void b(TabLayout.g gVar) {
        i.b(gVar, "tab");
        ((y0) p0()).A.a(gVar.c(), false);
    }
}
