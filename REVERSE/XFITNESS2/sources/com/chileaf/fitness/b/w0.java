package com.chileaf.fitness.b;

import android.view.View;
import androidx.databinding.ViewDataBinding;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.tabs.TabLayout;

/* compiled from: FragmentMainBinding */
public abstract class w0 extends ViewDataBinding {
    public final ViewPager2 A;
    public final TabLayout z;

    protected w0(Object obj, View view, int i2, TabLayout tabLayout, ViewPager2 viewPager2) {
        super(obj, view, i2);
        this.z = tabLayout;
        this.A = viewPager2;
    }
}
