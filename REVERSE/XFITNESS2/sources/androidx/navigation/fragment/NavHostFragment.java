package androidx.navigation.fragment;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.p;
import androidx.lifecycle.LifecycleOwner;
import androidx.navigation.NavController;
import androidx.navigation.fragment.a;
import androidx.navigation.j;
import androidx.navigation.m;
import androidx.navigation.q;
import androidx.navigation.r;

public class NavHostFragment extends Fragment {
    private m b0;
    private Boolean c0 = null;
    private int d0;
    private boolean e0;

    public static NavController b(Fragment fragment) {
        for (Fragment fragment2 = fragment; fragment2 != null; fragment2 = fragment2.v()) {
            if (fragment2 instanceof NavHostFragment) {
                return ((NavHostFragment) fragment2).o0();
            }
            Fragment u = fragment2.w().u();
            if (u instanceof NavHostFragment) {
                return ((NavHostFragment) u).o0();
            }
        }
        View F = fragment.F();
        if (F != null) {
            return q.a(F);
        }
        throw new IllegalStateException("Fragment " + fragment + " does not have a NavController set");
    }

    private int p0() {
        int r = r();
        if (r == 0 || r == -1) {
            return R$id.nav_host_fragment_container;
        }
        return r;
    }

    public void a(Context context) {
        super.a(context);
        if (this.e0) {
            p b = w().b();
            b.d(this);
            b.a();
        }
    }

    public void c(Bundle bundle) {
        Bundle bundle2;
        super.c(bundle);
        m mVar = new m(k0());
        this.b0 = mVar;
        mVar.a((LifecycleOwner) this);
        this.b0.a(j0().a());
        m mVar2 = this.b0;
        Boolean bool = this.c0;
        int i2 = 0;
        mVar2.a(bool != null && bool.booleanValue());
        Bundle bundle3 = null;
        this.c0 = null;
        this.b0.a(getViewModelStore());
        a((NavController) this.b0);
        if (bundle != null) {
            bundle2 = bundle.getBundle("android-support-nav:fragment:navControllerState");
            if (bundle.getBoolean("android-support-nav:fragment:defaultHost", false)) {
                this.e0 = true;
                p b = w().b();
                b.d(this);
                b.a();
            }
            this.d0 = bundle.getInt("android-support-nav:fragment:graphId");
        } else {
            bundle2 = null;
        }
        if (bundle2 != null) {
            this.b0.a(bundle2);
        }
        int i3 = this.d0;
        if (i3 != 0) {
            this.b0.b(i3);
            return;
        }
        Bundle i4 = i();
        if (i4 != null) {
            i2 = i4.getInt("android-support-nav:fragment:graphId");
        }
        if (i4 != null) {
            bundle3 = i4.getBundle("android-support-nav:fragment:startDestinationArgs");
        }
        if (i2 != 0) {
            this.b0.a(i2, bundle3);
        }
    }

    public void d(boolean z) {
        m mVar = this.b0;
        if (mVar != null) {
            mVar.a(z);
        } else {
            this.c0 = Boolean.valueOf(z);
        }
    }

    public void e(Bundle bundle) {
        super.e(bundle);
        Bundle h2 = this.b0.h();
        if (h2 != null) {
            bundle.putBundle("android-support-nav:fragment:navControllerState", h2);
        }
        if (this.e0) {
            bundle.putBoolean("android-support-nav:fragment:defaultHost", true);
        }
        int i2 = this.d0;
        if (i2 != 0) {
            bundle.putInt("android-support-nav:fragment:graphId", i2);
        }
    }

    /* access modifiers changed from: protected */
    @Deprecated
    public r<? extends a.C0040a> n0() {
        return new a(k0(), j(), p0());
    }

    public final NavController o0() {
        m mVar = this.b0;
        if (mVar != null) {
            return mVar;
        }
        throw new IllegalStateException("NavController is not available before onCreate()");
    }

    /* access modifiers changed from: protected */
    public void a(NavController navController) {
        navController.e().a((r<? extends j>) new DialogFragmentNavigator(k0(), j()));
        navController.e().a((r<? extends j>) n0());
    }

    public View a(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        FragmentContainerView fragmentContainerView = new FragmentContainerView(layoutInflater.getContext());
        fragmentContainerView.setId(p0());
        return fragmentContainerView;
    }

    public void a(View view, Bundle bundle) {
        super.a(view, bundle);
        if (view instanceof ViewGroup) {
            q.a(view, (NavController) this.b0);
            if (view.getParent() != null) {
                View view2 = (View) view.getParent();
                if (view2.getId() == r()) {
                    q.a(view2, (NavController) this.b0);
                    return;
                }
                return;
            }
            return;
        }
        throw new IllegalStateException("created host view " + view + " is not a ViewGroup");
    }

    public void a(Context context, AttributeSet attributeSet, Bundle bundle) {
        super.a(context, attributeSet, bundle);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.NavHost);
        int resourceId = obtainStyledAttributes.getResourceId(R$styleable.NavHost_navGraph, 0);
        if (resourceId != 0) {
            this.d0 = resourceId;
        }
        obtainStyledAttributes.recycle();
        TypedArray obtainStyledAttributes2 = context.obtainStyledAttributes(attributeSet, R$styleable.NavHostFragment);
        if (obtainStyledAttributes2.getBoolean(R$styleable.NavHostFragment_defaultNavHost, false)) {
            this.e0 = true;
        }
        obtainStyledAttributes2.recycle();
    }
}
