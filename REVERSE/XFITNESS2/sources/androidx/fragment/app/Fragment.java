package androidx.fragment.app;

import android.animation.Animator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ComponentCallbacks;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import androidx.core.app.l;
import androidx.core.h.f;
import androidx.lifecycle.HasDefaultViewModelProviderFactory;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateViewModelFactory;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.savedstate.SavedStateRegistry;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.util.UUID;

public class Fragment implements ComponentCallbacks, View.OnCreateContextMenuListener, LifecycleOwner, ViewModelStoreOwner, HasDefaultViewModelProviderFactory, androidx.savedstate.b {
    static final Object a0 = new Object();
    int A;
    String B;
    boolean C;
    boolean D;
    boolean E;
    boolean F;
    boolean G;
    boolean H;
    private boolean I;
    ViewGroup J;
    View K;
    boolean L;
    boolean M;
    d N;
    boolean O;
    boolean P;
    float Q;
    LayoutInflater R;
    boolean S;
    Lifecycle.State T;
    LifecycleRegistry U;
    t V;
    MutableLiveData<LifecycleOwner> W;
    private ViewModelProvider.Factory X;
    androidx.savedstate.a Y;
    private int Z;
    int e;

    /* renamed from: f  reason: collision with root package name */
    Bundle f585f;

    /* renamed from: g  reason: collision with root package name */
    SparseArray<Parcelable> f586g;

    /* renamed from: h  reason: collision with root package name */
    Boolean f587h;

    /* renamed from: i  reason: collision with root package name */
    String f588i;

    /* renamed from: j  reason: collision with root package name */
    Bundle f589j;
    Fragment k;
    String l;
    int m;
    private Boolean n;
    boolean o;
    boolean p;
    boolean q;
    boolean r;
    boolean s;
    boolean t;
    int u;
    j v;
    g<?> w;
    j x;
    Fragment y;
    int z;

    public static class InstantiationException extends RuntimeException {
        public InstantiationException(String str, Exception exc) {
            super(str, exc);
        }
    }

    class a implements Runnable {
        a() {
        }

        public void run() {
            Fragment.this.m0();
        }
    }

    class b implements Runnable {
        b() {
        }

        public void run() {
            Fragment.this.c();
        }
    }

    class c extends d {
        c() {
        }

        public View a(int i2) {
            View view = Fragment.this.K;
            if (view != null) {
                return view.findViewById(i2);
            }
            throw new IllegalStateException("Fragment " + this + " does not have a view");
        }

        public boolean c() {
            return Fragment.this.K != null;
        }
    }

    static class d {
        View a;
        Animator b;
        int c;
        int d;
        int e;

        /* renamed from: f  reason: collision with root package name */
        Object f590f = null;

        /* renamed from: g  reason: collision with root package name */
        Object f591g;

        /* renamed from: h  reason: collision with root package name */
        Object f592h;

        /* renamed from: i  reason: collision with root package name */
        Object f593i;

        /* renamed from: j  reason: collision with root package name */
        Object f594j;
        Object k;
        Boolean l;
        Boolean m;
        l n;
        l o;
        boolean p;
        e q;
        boolean r;

        d() {
            Object obj = Fragment.a0;
            this.f591g = obj;
            this.f592h = null;
            this.f593i = obj;
            this.f594j = null;
            this.k = obj;
            this.n = null;
            this.o = null;
        }
    }

    interface e {
        void a();

        void b();
    }

    public Fragment() {
        this.e = -1;
        this.f588i = UUID.randomUUID().toString();
        this.l = null;
        this.n = null;
        this.x = new k();
        this.H = true;
        this.M = true;
        new a();
        this.T = Lifecycle.State.RESUMED;
        this.W = new MutableLiveData<>();
        o0();
    }

    @Deprecated
    public static Fragment a(Context context, String str, Bundle bundle) {
        try {
            Fragment fragment = (Fragment) f.d(context.getClassLoader(), str).getConstructor(new Class[0]).newInstance(new Object[0]);
            if (bundle != null) {
                bundle.setClassLoader(fragment.getClass().getClassLoader());
                fragment.m(bundle);
            }
            return fragment;
        } catch (InstantiationException e2) {
            throw new InstantiationException("Unable to instantiate fragment " + str + ": make sure class name exists, is public, and has an empty constructor that is public", e2);
        } catch (IllegalAccessException e3) {
            throw new InstantiationException("Unable to instantiate fragment " + str + ": make sure class name exists, is public, and has an empty constructor that is public", e3);
        } catch (NoSuchMethodException e4) {
            throw new InstantiationException("Unable to instantiate fragment " + str + ": could not find Fragment constructor", e4);
        } catch (InvocationTargetException e5) {
            throw new InstantiationException("Unable to instantiate fragment " + str + ": calling Fragment constructor caused an exception", e5);
        }
    }

    private d n0() {
        if (this.N == null) {
            this.N = new d();
        }
        return this.N;
    }

    private void o0() {
        this.U = new LifecycleRegistry(this);
        this.Y = androidx.savedstate.a.a((androidx.savedstate.b) this);
        if (Build.VERSION.SDK_INT >= 19) {
            this.U.addObserver(new LifecycleEventObserver() {
                public void onStateChanged(LifecycleOwner lifecycleOwner, Lifecycle.Event event) {
                    View view;
                    if (event == Lifecycle.Event.ON_STOP && (view = Fragment.this.K) != null) {
                        view.cancelPendingInputEvents();
                    }
                }
            });
        }
    }

    public Object A() {
        d dVar = this.N;
        if (dVar == null) {
            return null;
        }
        Object obj = dVar.f591g;
        return obj == a0 ? l() : obj;
    }

    public Object B() {
        d dVar = this.N;
        if (dVar == null) {
            return null;
        }
        return dVar.f594j;
    }

    public Object C() {
        d dVar = this.N;
        if (dVar == null) {
            return null;
        }
        Object obj = dVar.k;
        return obj == a0 ? B() : obj;
    }

    /* access modifiers changed from: package-private */
    public int D() {
        d dVar = this.N;
        if (dVar == null) {
            return 0;
        }
        return dVar.c;
    }

    public final Fragment E() {
        String str;
        Fragment fragment = this.k;
        if (fragment != null) {
            return fragment;
        }
        j jVar = this.v;
        if (jVar == null || (str = this.l) == null) {
            return null;
        }
        return jVar.a(str);
    }

    public View F() {
        return this.K;
    }

    /* access modifiers changed from: package-private */
    public void G() {
        o0();
        this.f588i = UUID.randomUUID().toString();
        this.o = false;
        this.p = false;
        this.q = false;
        this.r = false;
        this.s = false;
        this.u = 0;
        this.v = null;
        this.x = new k();
        this.w = null;
        this.z = 0;
        this.A = 0;
        this.B = null;
        this.C = false;
        this.D = false;
    }

    public final boolean H() {
        return this.w != null && this.o;
    }

    public final boolean I() {
        return this.D;
    }

    public final boolean J() {
        return this.C;
    }

    /* access modifiers changed from: package-private */
    public boolean K() {
        d dVar = this.N;
        if (dVar == null) {
            return false;
        }
        return dVar.r;
    }

    /* access modifiers changed from: package-private */
    public final boolean L() {
        return this.u > 0;
    }

    /* access modifiers changed from: package-private */
    public boolean M() {
        d dVar = this.N;
        if (dVar == null) {
            return false;
        }
        return dVar.p;
    }

    public final boolean N() {
        return this.p;
    }

    /* access modifiers changed from: package-private */
    public final boolean O() {
        Fragment v2 = v();
        return v2 != null && (v2.N() || v2.O());
    }

    public final boolean P() {
        j jVar = this.v;
        if (jVar == null) {
            return false;
        }
        return jVar.x();
    }

    /* access modifiers changed from: package-private */
    public void Q() {
        this.x.y();
    }

    public void R() {
        this.I = true;
    }

    public void S() {
    }

    public void T() {
        this.I = true;
    }

    public void U() {
        this.I = true;
    }

    public void V() {
        this.I = true;
    }

    public void W() {
        this.I = true;
    }

    public void X() {
        this.I = true;
    }

    public void Y() {
        this.I = true;
    }

    /* access modifiers changed from: package-private */
    public void Z() {
        this.x.a(this.w, (d) new c(), this);
        this.e = 0;
        this.I = false;
        a(this.w.e());
        if (!this.I) {
            throw new SuperNotCalledException("Fragment " + this + " did not call through to super.onAttach()");
        }
    }

    public Animation a(int i2, boolean z2, int i3) {
        return null;
    }

    public void a(int i2, int i3, Intent intent) {
    }

    public void a(int i2, String[] strArr, int[] iArr) {
    }

    public void a(Menu menu) {
    }

    public void a(Menu menu, MenuInflater menuInflater) {
    }

    public void a(View view, Bundle bundle) {
    }

    public void a(Fragment fragment) {
    }

    public void a(boolean z2) {
    }

    public boolean a(MenuItem menuItem) {
        return false;
    }

    /* access modifiers changed from: package-private */
    public void a0() {
        this.x.f();
        this.U.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY);
        this.e = 0;
        this.I = false;
        this.S = false;
        R();
        if (!this.I) {
            throw new SuperNotCalledException("Fragment " + this + " did not call through to super.onDestroy()");
        }
    }

    public Animator b(int i2, boolean z2, int i3) {
        return null;
    }

    public final SavedStateRegistry b() {
        return this.Y.a();
    }

    public void b(Menu menu) {
    }

    public void b(boolean z2) {
    }

    public boolean b(MenuItem menuItem) {
        return false;
    }

    /* access modifiers changed from: package-private */
    public void b0() {
        this.x.g();
        if (this.K != null) {
            this.V.a(Lifecycle.Event.ON_DESTROY);
        }
        this.e = 1;
        this.I = false;
        T();
        if (this.I) {
            androidx.loader.a.a.a(this).a();
            this.t = false;
            return;
        }
        throw new SuperNotCalledException("Fragment " + this + " did not call through to super.onDestroyView()");
    }

    public void c(Bundle bundle) {
        this.I = true;
        k(bundle);
        if (!this.x.b(1)) {
            this.x.e();
        }
    }

    public void c(boolean z2) {
    }

    /* access modifiers changed from: package-private */
    public void c0() {
        this.e = -1;
        this.I = false;
        U();
        this.R = null;
        if (!this.I) {
            throw new SuperNotCalledException("Fragment " + this + " did not call through to super.onDetach()");
        } else if (!this.x.w()) {
            this.x.f();
            this.x = new k();
        }
    }

    public final FragmentActivity d() {
        g<?> gVar = this.w;
        if (gVar == null) {
            return null;
        }
        return (FragmentActivity) gVar.d();
    }

    public void d(boolean z2) {
    }

    /* access modifiers changed from: package-private */
    public void d0() {
        onLowMemory();
        this.x.h();
    }

    public void e(Bundle bundle) {
    }

    public boolean e() {
        Boolean bool;
        d dVar = this.N;
        if (dVar == null || (bool = dVar.m) == null) {
            return true;
        }
        return bool.booleanValue();
    }

    /* access modifiers changed from: package-private */
    public void e0() {
        this.x.i();
        if (this.K != null) {
            this.V.a(Lifecycle.Event.ON_PAUSE);
        }
        this.U.handleLifecycleEvent(Lifecycle.Event.ON_PAUSE);
        this.e = 3;
        this.I = false;
        V();
        if (!this.I) {
            throw new SuperNotCalledException("Fragment " + this + " did not call through to super.onPause()");
        }
    }

    public final boolean equals(Object obj) {
        return super.equals(obj);
    }

    public void f(Bundle bundle) {
        this.I = true;
    }

    /* access modifiers changed from: package-private */
    public void f0() {
        boolean g2 = this.v.g(this);
        Boolean bool = this.n;
        if (bool == null || bool.booleanValue() != g2) {
            this.n = Boolean.valueOf(g2);
            d(g2);
            this.x.j();
        }
    }

    /* access modifiers changed from: package-private */
    public void g(Bundle bundle) {
        this.x.y();
        this.e = 2;
        this.I = false;
        b(bundle);
        if (this.I) {
            this.x.d();
            return;
        }
        throw new SuperNotCalledException("Fragment " + this + " did not call through to super.onActivityCreated()");
    }

    /* access modifiers changed from: package-private */
    public void g0() {
        this.x.y();
        this.x.c(true);
        this.e = 4;
        this.I = false;
        W();
        if (this.I) {
            this.U.handleLifecycleEvent(Lifecycle.Event.ON_RESUME);
            if (this.K != null) {
                this.V.a(Lifecycle.Event.ON_RESUME);
            }
            this.x.k();
            return;
        }
        throw new SuperNotCalledException("Fragment " + this + " did not call through to super.onResume()");
    }

    public ViewModelProvider.Factory getDefaultViewModelProviderFactory() {
        if (this.v != null) {
            if (this.X == null) {
                this.X = new SavedStateViewModelFactory(j0().getApplication(), this, i());
            }
            return this.X;
        }
        throw new IllegalStateException("Can't access ViewModels from detached fragment");
    }

    public Lifecycle getLifecycle() {
        return this.U;
    }

    public ViewModelStore getViewModelStore() {
        j jVar = this.v;
        if (jVar != null) {
            return jVar.e(this);
        }
        throw new IllegalStateException("Can't access ViewModels from detached fragment");
    }

    public void h(boolean z2) {
        if (this.H != z2) {
            this.H = z2;
            if (this.G && H() && !J()) {
                this.w.i();
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void h0() {
        this.x.y();
        this.x.c(true);
        this.e = 3;
        this.I = false;
        X();
        if (this.I) {
            this.U.handleLifecycleEvent(Lifecycle.Event.ON_START);
            if (this.K != null) {
                this.V.a(Lifecycle.Event.ON_START);
            }
            this.x.l();
            return;
        }
        throw new SuperNotCalledException("Fragment " + this + " did not call through to super.onStart()");
    }

    public final int hashCode() {
        return super.hashCode();
    }

    public final Bundle i() {
        return this.f589j;
    }

    /* access modifiers changed from: package-private */
    public void i0() {
        this.x.m();
        if (this.K != null) {
            this.V.a(Lifecycle.Event.ON_STOP);
        }
        this.U.handleLifecycleEvent(Lifecycle.Event.ON_STOP);
        this.e = 2;
        this.I = false;
        Y();
        if (!this.I) {
            throw new SuperNotCalledException("Fragment " + this + " did not call through to super.onStop()");
        }
    }

    public final j j() {
        if (this.w != null) {
            return this.x;
        }
        throw new IllegalStateException("Fragment " + this + " has not been attached yet.");
    }

    public final FragmentActivity j0() {
        FragmentActivity d2 = d();
        if (d2 != null) {
            return d2;
        }
        throw new IllegalStateException("Fragment " + this + " not attached to an activity.");
    }

    public Context k() {
        g<?> gVar = this.w;
        if (gVar == null) {
            return null;
        }
        return gVar.e();
    }

    public final Context k0() {
        Context k2 = k();
        if (k2 != null) {
            return k2;
        }
        throw new IllegalStateException("Fragment " + this + " not attached to a context.");
    }

    /* access modifiers changed from: package-private */
    public final void l(Bundle bundle) {
        SparseArray<Parcelable> sparseArray = this.f586g;
        if (sparseArray != null) {
            this.K.restoreHierarchyState(sparseArray);
            this.f586g = null;
        }
        this.I = false;
        f(bundle);
        if (!this.I) {
            throw new SuperNotCalledException("Fragment " + this + " did not call through to super.onViewStateRestored()");
        } else if (this.K != null) {
            this.V.a(Lifecycle.Event.ON_CREATE);
        }
    }

    public final View l0() {
        View F2 = F();
        if (F2 != null) {
            return F2;
        }
        throw new IllegalStateException("Fragment " + this + " did not return a View from onCreateView() or this was called before onCreateView().");
    }

    public void m(Bundle bundle) {
        if (this.v == null || !P()) {
            this.f589j = bundle;
            return;
        }
        throw new IllegalStateException("Fragment already added and state has been saved");
    }

    public void m0() {
        j jVar = this.v;
        if (jVar == null || jVar.o == null) {
            n0().p = false;
        } else if (Looper.myLooper() != this.v.o.f().getLooper()) {
            this.v.o.f().postAtFrontOfQueue(new b());
        } else {
            c();
        }
    }

    public Object n() {
        d dVar = this.N;
        if (dVar == null) {
            return null;
        }
        return dVar.f592h;
    }

    /* access modifiers changed from: package-private */
    public l o() {
        d dVar = this.N;
        if (dVar == null) {
            return null;
        }
        return dVar.o;
    }

    public void onConfigurationChanged(Configuration configuration) {
        this.I = true;
    }

    public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
        j0().onCreateContextMenu(contextMenu, view, contextMenuInfo);
    }

    public void onLowMemory() {
        this.I = true;
    }

    @Deprecated
    public final j p() {
        return this.v;
    }

    public final Object q() {
        g<?> gVar = this.w;
        if (gVar == null) {
            return null;
        }
        return gVar.g();
    }

    public final int r() {
        return this.z;
    }

    public final LayoutInflater s() {
        LayoutInflater layoutInflater = this.R;
        return layoutInflater == null ? i((Bundle) null) : layoutInflater;
    }

    /* access modifiers changed from: package-private */
    public void setOnStartEnterTransitionListener(e eVar) {
        n0();
        e eVar2 = this.N.q;
        if (eVar != eVar2) {
            if (eVar == null || eVar2 == null) {
                d dVar = this.N;
                if (dVar.p) {
                    dVar.q = eVar;
                }
                if (eVar != null) {
                    eVar.b();
                    return;
                }
                return;
            }
            throw new IllegalStateException("Trying to set a replacement startPostponedEnterTransition on " + this);
        }
    }

    /* access modifiers changed from: package-private */
    public int t() {
        d dVar = this.N;
        if (dVar == null) {
            return 0;
        }
        return dVar.d;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(128);
        sb.append(getClass().getSimpleName());
        sb.append("{");
        sb.append(Integer.toHexString(System.identityHashCode(this)));
        sb.append("}");
        sb.append(" (");
        sb.append(this.f588i);
        sb.append(")");
        if (this.z != 0) {
            sb.append(" id=0x");
            sb.append(Integer.toHexString(this.z));
        }
        if (this.B != null) {
            sb.append(" ");
            sb.append(this.B);
        }
        sb.append('}');
        return sb.toString();
    }

    /* access modifiers changed from: package-private */
    public int u() {
        d dVar = this.N;
        if (dVar == null) {
            return 0;
        }
        return dVar.e;
    }

    public final Fragment v() {
        return this.y;
    }

    public final j w() {
        j jVar = this.v;
        if (jVar != null) {
            return jVar;
        }
        throw new IllegalStateException("Fragment " + this + " not associated with a fragment manager.");
    }

    public Object x() {
        d dVar = this.N;
        if (dVar == null) {
            return null;
        }
        Object obj = dVar.f593i;
        return obj == a0 ? n() : obj;
    }

    public final Resources y() {
        return k0().getResources();
    }

    public final boolean z() {
        return this.E;
    }

    @SuppressLint({"BanParcelableUsage"})
    public static class SavedState implements Parcelable {
        public static final Parcelable.Creator<SavedState> CREATOR = new a();
        final Bundle e;

        static class a implements Parcelable.ClassLoaderCreator<SavedState> {
            a() {
            }

            public SavedState[] newArray(int i2) {
                return new SavedState[i2];
            }

            public SavedState createFromParcel(Parcel parcel) {
                return new SavedState(parcel, (ClassLoader) null);
            }

            public SavedState createFromParcel(Parcel parcel, ClassLoader classLoader) {
                return new SavedState(parcel, classLoader);
            }
        }

        SavedState(Bundle bundle) {
            this.e = bundle;
        }

        public int describeContents() {
            return 0;
        }

        public void writeToParcel(Parcel parcel, int i2) {
            parcel.writeBundle(this.e);
        }

        SavedState(Parcel parcel, ClassLoader classLoader) {
            Bundle readBundle = parcel.readBundle();
            this.e = readBundle;
            if (classLoader != null && readBundle != null) {
                readBundle.setClassLoader(classLoader);
            }
        }
    }

    public boolean b(String str) {
        g<?> gVar = this.w;
        if (gVar != null) {
            return gVar.a(str);
        }
        return false;
    }

    public LayoutInflater d(Bundle bundle) {
        return a(bundle);
    }

    public boolean f() {
        Boolean bool;
        d dVar = this.N;
        if (dVar == null || (bool = dVar.l) == null) {
            return true;
        }
        return bool.booleanValue();
    }

    public void i(boolean z2) {
        this.E = z2;
        j jVar = this.v;
        if (jVar == null) {
            this.F = true;
        } else if (z2) {
            jVar.b(this);
        } else {
            jVar.m(this);
        }
    }

    /* access modifiers changed from: package-private */
    public void k(Bundle bundle) {
        Parcelable parcelable;
        if (bundle != null && (parcelable = bundle.getParcelable("android:support:fragments")) != null) {
            this.x.a(parcelable);
            this.x.e();
        }
    }

    /* access modifiers changed from: package-private */
    public boolean d(Menu menu) {
        boolean z2 = false;
        if (this.C) {
            return false;
        }
        if (this.G && this.H) {
            z2 = true;
            b(menu);
        }
        return z2 | this.x.b(menu);
    }

    /* access modifiers changed from: package-private */
    public void e(boolean z2) {
        b(z2);
        this.x.a(z2);
    }

    public void b(Bundle bundle) {
        this.I = true;
    }

    /* access modifiers changed from: package-private */
    public void f(boolean z2) {
        c(z2);
        this.x.b(z2);
    }

    @Deprecated
    public void j(boolean z2) {
        if (!this.M && z2 && this.e < 3 && this.v != null && H() && this.S) {
            this.v.k(this);
        }
        this.M = z2;
        this.L = this.e < 3 && !z2;
        if (this.f585f != null) {
            this.f587h = Boolean.valueOf(z2);
        }
    }

    /* access modifiers changed from: package-private */
    public l m() {
        d dVar = this.N;
        if (dVar == null) {
            return null;
        }
        return dVar.n;
    }

    /* access modifiers changed from: package-private */
    public void b(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.x.y();
        this.t = true;
        this.V = new t();
        View a2 = a(layoutInflater, viewGroup, bundle);
        this.K = a2;
        if (a2 != null) {
            this.V.c();
            this.W.setValue(this.V);
        } else if (!this.V.d()) {
            this.V = null;
        } else {
            throw new IllegalStateException("Called getViewLifecycleOwner() but onCreateView() returned null");
        }
    }

    /* access modifiers changed from: package-private */
    public void c() {
        d dVar = this.N;
        e eVar = null;
        if (dVar != null) {
            dVar.p = false;
            e eVar2 = dVar.q;
            dVar.q = null;
            eVar = eVar2;
        }
        if (eVar != null) {
            eVar.a();
        }
    }

    /* access modifiers changed from: package-private */
    public void h(Bundle bundle) {
        this.x.y();
        this.e = 1;
        this.I = false;
        this.Y.a(bundle);
        c(bundle);
        this.S = true;
        if (this.I) {
            this.U.handleLifecycleEvent(Lifecycle.Event.ON_CREATE);
            return;
        }
        throw new SuperNotCalledException("Fragment " + this + " did not call through to super.onCreate()");
    }

    /* access modifiers changed from: package-private */
    public boolean d(MenuItem menuItem) {
        if (this.C) {
            return false;
        }
        if ((!this.G || !this.H || !b(menuItem)) && !this.x.b(menuItem)) {
            return false;
        }
        return true;
    }

    /* access modifiers changed from: package-private */
    public LayoutInflater i(Bundle bundle) {
        LayoutInflater d2 = d(bundle);
        this.R = d2;
        return d2;
    }

    /* access modifiers changed from: package-private */
    public View g() {
        d dVar = this.N;
        if (dVar == null) {
            return null;
        }
        return dVar.a;
    }

    public void a(SavedState savedState) {
        Bundle bundle;
        if (this.v == null) {
            if (savedState == null || (bundle = savedState.e) == null) {
                bundle = null;
            }
            this.f585f = bundle;
            return;
        }
        throw new IllegalStateException("Fragment already added");
    }

    /* access modifiers changed from: package-private */
    public boolean c(MenuItem menuItem) {
        if (this.C) {
            return false;
        }
        if (!a(menuItem) && !this.x.a(menuItem)) {
            return false;
        }
        return true;
    }

    /* access modifiers changed from: package-private */
    public void g(boolean z2) {
        n0().r = z2;
    }

    public Object l() {
        d dVar = this.N;
        if (dVar == null) {
            return null;
        }
        return dVar.f590f;
    }

    /* access modifiers changed from: package-private */
    public void d(int i2) {
        n0().c = i2;
    }

    /* access modifiers changed from: package-private */
    public void j(Bundle bundle) {
        e(bundle);
        this.Y.b(bundle);
        Parcelable B2 = this.x.B();
        if (B2 != null) {
            bundle.putParcelable("android:support:fragments", B2);
        }
    }

    public Fragment(int i2) {
        this();
        this.Z = i2;
    }

    public void a(Fragment fragment, int i2) {
        j jVar = this.v;
        j jVar2 = fragment != null ? fragment.v : null;
        if (jVar == null || jVar2 == null || jVar == jVar2) {
            Fragment fragment2 = fragment;
            while (fragment2 != null) {
                if (fragment2 != this) {
                    fragment2 = fragment2.E();
                } else {
                    throw new IllegalArgumentException("Setting " + fragment + " as the target of " + this + " would create a target cycle");
                }
            }
            if (fragment == null) {
                this.l = null;
                this.k = null;
            } else if (this.v == null || fragment.v == null) {
                this.l = null;
                this.k = fragment;
            } else {
                this.l = fragment.f588i;
                this.k = null;
            }
            this.m = i2;
            return;
        }
        throw new IllegalArgumentException("Fragment " + fragment + " must share the same FragmentManager to be set as a target fragment");
    }

    /* access modifiers changed from: package-private */
    public void c(Menu menu) {
        if (!this.C) {
            if (this.G && this.H) {
                a(menu);
            }
            this.x.a(menu);
        }
    }

    /* access modifiers changed from: package-private */
    public boolean b(Menu menu, MenuInflater menuInflater) {
        boolean z2 = false;
        if (this.C) {
            return false;
        }
        if (this.G && this.H) {
            z2 = true;
            a(menu, menuInflater);
        }
        return z2 | this.x.a(menu, menuInflater);
    }

    /* access modifiers changed from: package-private */
    public Animator h() {
        d dVar = this.N;
        if (dVar == null) {
            return null;
        }
        return dVar.b;
    }

    /* access modifiers changed from: package-private */
    public void c(int i2) {
        if (this.N != null || i2 != 0) {
            n0();
            this.N.e = i2;
        }
    }

    /* access modifiers changed from: package-private */
    public void b(int i2) {
        if (this.N != null || i2 != 0) {
            n0().d = i2;
        }
    }

    public final String a(int i2) {
        return y().getString(i2);
    }

    public final String a(int i2, Object... objArr) {
        return y().getString(i2, objArr);
    }

    public void a(@SuppressLint({"UnknownNullness"}) Intent intent) {
        a(intent, (Bundle) null);
    }

    public void a(@SuppressLint({"UnknownNullness"}) Intent intent, Bundle bundle) {
        g<?> gVar = this.w;
        if (gVar != null) {
            gVar.a(this, intent, -1, bundle);
            return;
        }
        throw new IllegalStateException("Fragment " + this + " not attached to Activity");
    }

    public final void a(String[] strArr, int i2) {
        g<?> gVar = this.w;
        if (gVar != null) {
            gVar.a(this, strArr, i2);
            return;
        }
        throw new IllegalStateException("Fragment " + this + " not attached to Activity");
    }

    @Deprecated
    public LayoutInflater a(Bundle bundle) {
        g<?> gVar = this.w;
        if (gVar != null) {
            LayoutInflater h2 = gVar.h();
            f.b(h2, this.x.r());
            return h2;
        }
        throw new IllegalStateException("onGetLayoutInflater() cannot be executed until the Fragment is attached to the FragmentManager.");
    }

    public void a(Context context, AttributeSet attributeSet, Bundle bundle) {
        this.I = true;
        g<?> gVar = this.w;
        Activity d2 = gVar == null ? null : gVar.d();
        if (d2 != null) {
            this.I = false;
            a(d2, attributeSet, bundle);
        }
    }

    @Deprecated
    public void a(Activity activity, AttributeSet attributeSet, Bundle bundle) {
        this.I = true;
    }

    public void a(Context context) {
        this.I = true;
        g<?> gVar = this.w;
        Activity d2 = gVar == null ? null : gVar.d();
        if (d2 != null) {
            this.I = false;
            a(d2);
        }
    }

    @Deprecated
    public void a(Activity activity) {
        this.I = true;
    }

    public View a(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        int i2 = this.Z;
        if (i2 != 0) {
            return layoutInflater.inflate(i2, viewGroup, false);
        }
        return null;
    }

    public void a(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        printWriter.print(str);
        printWriter.print("mFragmentId=#");
        printWriter.print(Integer.toHexString(this.z));
        printWriter.print(" mContainerId=#");
        printWriter.print(Integer.toHexString(this.A));
        printWriter.print(" mTag=");
        printWriter.println(this.B);
        printWriter.print(str);
        printWriter.print("mState=");
        printWriter.print(this.e);
        printWriter.print(" mWho=");
        printWriter.print(this.f588i);
        printWriter.print(" mBackStackNesting=");
        printWriter.println(this.u);
        printWriter.print(str);
        printWriter.print("mAdded=");
        printWriter.print(this.o);
        printWriter.print(" mRemoving=");
        printWriter.print(this.p);
        printWriter.print(" mFromLayout=");
        printWriter.print(this.q);
        printWriter.print(" mInLayout=");
        printWriter.println(this.r);
        printWriter.print(str);
        printWriter.print("mHidden=");
        printWriter.print(this.C);
        printWriter.print(" mDetached=");
        printWriter.print(this.D);
        printWriter.print(" mMenuVisible=");
        printWriter.print(this.H);
        printWriter.print(" mHasMenu=");
        printWriter.println(this.G);
        printWriter.print(str);
        printWriter.print("mRetainInstance=");
        printWriter.print(this.E);
        printWriter.print(" mUserVisibleHint=");
        printWriter.println(this.M);
        if (this.v != null) {
            printWriter.print(str);
            printWriter.print("mFragmentManager=");
            printWriter.println(this.v);
        }
        if (this.w != null) {
            printWriter.print(str);
            printWriter.print("mHost=");
            printWriter.println(this.w);
        }
        if (this.y != null) {
            printWriter.print(str);
            printWriter.print("mParentFragment=");
            printWriter.println(this.y);
        }
        if (this.f589j != null) {
            printWriter.print(str);
            printWriter.print("mArguments=");
            printWriter.println(this.f589j);
        }
        if (this.f585f != null) {
            printWriter.print(str);
            printWriter.print("mSavedFragmentState=");
            printWriter.println(this.f585f);
        }
        if (this.f586g != null) {
            printWriter.print(str);
            printWriter.print("mSavedViewState=");
            printWriter.println(this.f586g);
        }
        Fragment E2 = E();
        if (E2 != null) {
            printWriter.print(str);
            printWriter.print("mTarget=");
            printWriter.print(E2);
            printWriter.print(" mTargetRequestCode=");
            printWriter.println(this.m);
        }
        if (t() != 0) {
            printWriter.print(str);
            printWriter.print("mNextAnim=");
            printWriter.println(t());
        }
        if (this.J != null) {
            printWriter.print(str);
            printWriter.print("mContainer=");
            printWriter.println(this.J);
        }
        if (this.K != null) {
            printWriter.print(str);
            printWriter.print("mView=");
            printWriter.println(this.K);
        }
        if (g() != null) {
            printWriter.print(str);
            printWriter.print("mAnimatingAway=");
            printWriter.println(g());
            printWriter.print(str);
            printWriter.print("mStateAfterAnimating=");
            printWriter.println(D());
        }
        if (k() != null) {
            androidx.loader.a.a.a(this).a(str, fileDescriptor, printWriter, strArr);
        }
        printWriter.print(str);
        printWriter.println("Child " + this.x + ":");
        j jVar = this.x;
        jVar.a(str + "  ", fileDescriptor, printWriter, strArr);
    }

    /* access modifiers changed from: package-private */
    public Fragment a(String str) {
        if (str.equals(this.f588i)) {
            return this;
        }
        return this.x.c(str);
    }

    /* access modifiers changed from: package-private */
    public void a(Configuration configuration) {
        onConfigurationChanged(configuration);
        this.x.a(configuration);
    }

    /* access modifiers changed from: package-private */
    public void a(View view) {
        n0().a = view;
    }

    /* access modifiers changed from: package-private */
    public void a(Animator animator) {
        n0().b = animator;
    }
}
