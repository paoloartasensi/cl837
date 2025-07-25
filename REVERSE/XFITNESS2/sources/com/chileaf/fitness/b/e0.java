package com.chileaf.fitness.b;

import android.view.View;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.ViewDataBinding;
import androidx.drawerlayout.widget.DrawerLayout;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.navigation.NavigationView;

/* compiled from: ActivityMainBinding */
public abstract class e0 extends ViewDataBinding {
    public final NavigationView A;
    public final Toolbar B;
    public final DrawerLayout z;

    protected e0(Object obj, View view, int i2, AppBarLayout appBarLayout, DrawerLayout drawerLayout, NavigationView navigationView, Toolbar toolbar) {
        super(obj, view, i2);
        this.z = drawerLayout;
        this.A = navigationView;
        this.B = toolbar;
    }
}
