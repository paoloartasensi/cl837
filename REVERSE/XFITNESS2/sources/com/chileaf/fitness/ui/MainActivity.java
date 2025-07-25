package com.chileaf.fitness.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.a;
import androidx.navigation.ui.c;
import androidx.navigation.ui.d;
import androidx.navigation.ui.e;
import androidx.navigation.ui.g;
import com.chileaf.fitness.R$id;
import com.chileaf.fitness.R$layout;
import com.chileaf.fitness.b.e0;
import com.chileaf.fitness.base.BaseActivity;
import com.chileaf.fitness.base.BaseViewModel;
import com.google.android.material.navigation.NavigationView;
import java.util.HashMap;
import java.util.Set;
import kotlin.jvm.internal.i;

/* compiled from: MainActivity.kt */
public final class MainActivity extends BaseActivity<e0> {
    private DrawerLayout E;
    private NavController F;
    private d G;
    private HashMap H;

    private final void q() {
        Intent intent = new Intent("android.intent.action.MAIN");
        intent.setFlags(67108864);
        intent.addCategory("android.intent.category.HOME");
        BaseActivity.a((BaseActivity) this, intent, false, 2, (Object) null);
    }

    /* access modifiers changed from: protected */
    public void a(Bundle bundle) {
        DrawerLayout drawerLayout = ((e0) m()).z;
        i.a((Object) drawerLayout, "mBinding.drawerLayout");
        this.E = drawerLayout;
        this.F = a.a(this, R$id.nav_host_fragment);
        Set a = b0.a((T[]) new Integer[]{Integer.valueOf(R$id.nav_home), Integer.valueOf(R$id.nav_devices), Integer.valueOf(R$id.nav_setting), Integer.valueOf(R$id.nav_about)});
        DrawerLayout drawerLayout2 = this.E;
        if (drawerLayout2 != null) {
            MainActivity$initData$$inlined$AppBarConfiguration$1 mainActivity$initData$$inlined$AppBarConfiguration$1 = MainActivity$initData$$inlined$AppBarConfiguration$1.INSTANCE;
            d.b bVar = new d.b(a);
            bVar.a(drawerLayout2);
            bVar.a((d.c) new a(mainActivity$initData$$inlined$AppBarConfiguration$1));
            d a2 = bVar.a();
            i.a((Object) a2, "AppBarConfiguration.Buil…eUpListener)\n    .build()");
            this.G = a2;
            a(((e0) m()).B);
            NavController navController = this.F;
            if (navController != null) {
                d dVar = this.G;
                if (dVar != null) {
                    c.a(this, navController, dVar);
                    NavigationView navigationView = ((e0) m()).A;
                    i.a((Object) navigationView, "mBinding.navMain");
                    NavController navController2 = this.F;
                    if (navController2 != null) {
                        g.a(navigationView, navController2);
                    } else {
                        i.d("mNavController");
                        throw null;
                    }
                } else {
                    i.d("mAppBarConfiguration");
                    throw null;
                }
            } else {
                i.d("mNavController");
                throw null;
            }
        } else {
            i.d("mDrawerLayout");
            throw null;
        }
    }

    public View d(int i2) {
        if (this.H == null) {
            this.H = new HashMap();
        }
        View view = (View) this.H.get(Integer.valueOf(i2));
        if (view != null) {
            return view;
        }
        View findViewById = findViewById(i2);
        this.H.put(Integer.valueOf(i2), findViewById);
        return findViewById;
    }

    public boolean l() {
        NavController navController = this.F;
        if (navController != null) {
            d dVar = this.G;
            if (dVar != null) {
                return e.a(navController, dVar) || super.l();
            }
            i.d("mAppBarConfiguration");
            throw null;
        }
        i.d("mNavController");
        throw null;
    }

    /* access modifiers changed from: protected */
    public BaseViewModel o() {
        return null;
    }

    public void onBackPressed() {
        DrawerLayout drawerLayout = this.E;
        if (drawerLayout == null) {
            i.d("mDrawerLayout");
            throw null;
        } else if (drawerLayout.e(8388611)) {
            DrawerLayout drawerLayout2 = this.E;
            if (drawerLayout2 != null) {
                drawerLayout2.a(8388611);
            } else {
                i.d("mDrawerLayout");
                throw null;
            }
        } else {
            q();
        }
    }

    public boolean onKeyDown(int i2, KeyEvent keyEvent) {
        if (i2 != 4) {
            return super.onKeyDown(i2, keyEvent);
        }
        q();
        return true;
    }

    /* access modifiers changed from: protected */
    public int p() {
        return R$layout.activity_main;
    }
}
