package com.chileaf.fitness.b;

import android.view.View;
import androidx.databinding.ViewDataBinding;
import androidx.viewpager.widget.ViewPager;
import com.google.android.material.tabs.TabLayout;

/* compiled from: FragmentWearBinding */
public abstract class i1 extends ViewDataBinding {
    public final ViewPager A;
    public final TabLayout z;

    protected i1(Object obj, View view, int i2, TabLayout tabLayout, ViewPager viewPager) {
        super(obj, view, i2);
        this.z = tabLayout;
        this.A = viewPager;
    }
}
