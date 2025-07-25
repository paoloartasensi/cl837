package com.chileaf.fitness.b;

import android.view.View;
import androidx.databinding.ViewDataBinding;
import androidx.viewpager.widget.ViewPager;
import com.google.android.material.tabs.TabLayout;

/* compiled from: FragmentRidingBinding */
public abstract class y0 extends ViewDataBinding {
    public final ViewPager A;
    public final TabLayout z;

    protected y0(Object obj, View view, int i2, TabLayout tabLayout, ViewPager viewPager) {
        super(obj, view, i2);
        this.z = tabLayout;
        this.A = viewPager;
    }
}
