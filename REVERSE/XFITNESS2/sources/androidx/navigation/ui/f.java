package androidx.navigation.ui;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.j;
import androidx.navigation.k;
import androidx.navigation.o;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.navigation.NavigationView;
import java.lang.ref.WeakReference;
import java.util.Set;

/* compiled from: NavigationUI */
public final class f {

    /* compiled from: NavigationUI */
    static class a implements NavigationView.b {
        final /* synthetic */ NavController a;
        final /* synthetic */ NavigationView b;

        a(NavController navController, NavigationView navigationView) {
            this.a = navController;
            this.b = navigationView;
        }

        public boolean a(MenuItem menuItem) {
            boolean a2 = f.a(menuItem, this.a);
            if (a2) {
                ViewParent parent = this.b.getParent();
                if (parent instanceof DrawerLayout) {
                    ((DrawerLayout) parent).a((View) this.b);
                } else {
                    BottomSheetBehavior a3 = f.a((View) this.b);
                    if (a3 != null) {
                        a3.c(5);
                    }
                }
            }
            return a2;
        }
    }

    /* compiled from: NavigationUI */
    static class b implements NavController.b {
        final /* synthetic */ WeakReference a;
        final /* synthetic */ NavController b;

        b(WeakReference weakReference, NavController navController) {
            this.a = weakReference;
            this.b = navController;
        }

        public void a(NavController navController, j jVar, Bundle bundle) {
            NavigationView navigationView = (NavigationView) this.a.get();
            if (navigationView == null) {
                this.b.removeOnDestinationChangedListener(this);
                return;
            }
            Menu menu = navigationView.getMenu();
            int size = menu.size();
            for (int i2 = 0; i2 < size; i2++) {
                MenuItem item = menu.getItem(i2);
                item.setChecked(f.a(jVar, item.getItemId()));
            }
        }
    }

    public static boolean a(MenuItem menuItem, NavController navController) {
        o.a aVar = new o.a();
        aVar.a(true);
        aVar.a(R$anim.nav_default_enter_anim);
        aVar.b(R$anim.nav_default_exit_anim);
        aVar.c(R$anim.nav_default_pop_enter_anim);
        aVar.d(R$anim.nav_default_pop_exit_anim);
        if ((menuItem.getOrder() & 196608) == 0) {
            aVar.a(a(navController.c()).d(), false);
        }
        try {
            navController.a(menuItem.getItemId(), (Bundle) null, aVar.a());
            return true;
        } catch (IllegalArgumentException unused) {
            return false;
        }
    }

    public static boolean a(NavController navController, d dVar) {
        DrawerLayout a2 = dVar.a();
        j b2 = navController.b();
        Set<Integer> c = dVar.c();
        if (a2 != null && b2 != null && a(b2, c)) {
            a2.f(8388611);
            return true;
        } else if (navController.f()) {
            return true;
        } else {
            if (dVar.b() != null) {
                return dVar.b().a();
            }
            return false;
        }
    }

    public static void a(AppCompatActivity appCompatActivity, NavController navController, d dVar) {
        navController.addOnDestinationChangedListener(new b(appCompatActivity, dVar));
    }

    public static void a(NavigationView navigationView, NavController navController) {
        navigationView.setNavigationItemSelectedListener(new a(navController, navigationView));
        navController.addOnDestinationChangedListener(new b(new WeakReference(navigationView), navController));
    }

    static BottomSheetBehavior a(View view) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (!(layoutParams instanceof CoordinatorLayout.f)) {
            ViewParent parent = view.getParent();
            if (parent instanceof View) {
                return a((View) parent);
            }
            return null;
        }
        CoordinatorLayout.c d = ((CoordinatorLayout.f) layoutParams).d();
        if (!(d instanceof BottomSheetBehavior)) {
            return null;
        }
        return (BottomSheetBehavior) d;
    }

    static boolean a(j jVar, int i2) {
        while (jVar.d() != i2 && jVar.g() != null) {
            jVar = jVar.g();
        }
        return jVar.d() == i2;
    }

    static boolean a(j jVar, Set<Integer> set) {
        while (!set.contains(Integer.valueOf(jVar.d()))) {
            jVar = jVar.g();
            if (jVar == null) {
                return false;
            }
        }
        return true;
    }

    static j a(k kVar) {
        while (true) {
            boolean z = r1 instanceof k;
            j jVar = kVar;
            if (!z) {
                return jVar;
            }
            k kVar2 = (k) jVar;
            jVar = kVar2.c(kVar2.j());
        }
    }
}
