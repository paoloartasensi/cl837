package androidx.fragment.app;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Looper;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import androidx.activity.OnBackPressedDispatcher;
import androidx.fragment.R$id;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.c;
import androidx.fragment.app.q;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.ViewModelStore;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/* compiled from: FragmentManager */
public abstract class j {
    private static boolean F = false;
    private ArrayList<Boolean> A;
    private ArrayList<Fragment> B;
    private ArrayList<C0034j> C;
    private l D;
    private Runnable E = new d();
    private final ArrayList<h> a = new ArrayList<>();
    private boolean b;
    private final o c = new o();
    ArrayList<a> d;
    private ArrayList<Fragment> e;

    /* renamed from: f  reason: collision with root package name */
    private final h f619f = new h(this);

    /* renamed from: g  reason: collision with root package name */
    private OnBackPressedDispatcher f620g;

    /* renamed from: h  reason: collision with root package name */
    private final androidx.activity.b f621h = new a(false);

    /* renamed from: i  reason: collision with root package name */
    private final AtomicInteger f622i = new AtomicInteger();

    /* renamed from: j  reason: collision with root package name */
    private ArrayList<g> f623j;
    private ConcurrentHashMap<Fragment, HashSet<androidx.core.d.a>> k = new ConcurrentHashMap<>();
    private final q.g l = new b();
    private final i m = new i(this);
    int n = -1;
    g<?> o;
    d p;
    private Fragment q;
    Fragment r;
    private f s = null;
    private f t = new c();
    private boolean u;
    private boolean v;
    private boolean w;
    private boolean x;
    private boolean y;
    private ArrayList<a> z;

    /* compiled from: FragmentManager */
    class a extends androidx.activity.b {
        a(boolean z) {
            super(z);
        }

        public void a() {
            j.this.v();
        }
    }

    /* compiled from: FragmentManager */
    class b implements q.g {
        b() {
        }

        public void a(Fragment fragment, androidx.core.d.a aVar) {
            if (!aVar.b()) {
                j.this.b(fragment, aVar);
            }
        }

        public void b(Fragment fragment, androidx.core.d.a aVar) {
            j.this.a(fragment, aVar);
        }
    }

    /* compiled from: FragmentManager */
    class c extends f {
        c() {
        }

        public Fragment a(ClassLoader classLoader, String str) {
            g<?> gVar = j.this.o;
            return gVar.a(gVar.e(), str, (Bundle) null);
        }
    }

    /* compiled from: FragmentManager */
    class d implements Runnable {
        d() {
        }

        public void run() {
            j.this.c(true);
        }
    }

    /* compiled from: FragmentManager */
    class e extends AnimatorListenerAdapter {
        final /* synthetic */ ViewGroup a;
        final /* synthetic */ View b;
        final /* synthetic */ Fragment c;

        e(j jVar, ViewGroup viewGroup, View view, Fragment fragment) {
            this.a = viewGroup;
            this.b = view;
            this.c = fragment;
        }

        public void onAnimationEnd(Animator animator) {
            this.a.endViewTransition(this.b);
            animator.removeListener(this);
            Fragment fragment = this.c;
            View view = fragment.K;
            if (view != null && fragment.C) {
                view.setVisibility(8);
            }
        }
    }

    /* compiled from: FragmentManager */
    public static abstract class f {
        public void a(j jVar, Fragment fragment) {
        }

        public void a(j jVar, Fragment fragment, Context context) {
        }

        public void a(j jVar, Fragment fragment, Bundle bundle) {
        }

        public void a(j jVar, Fragment fragment, View view, Bundle bundle) {
        }

        public void b(j jVar, Fragment fragment) {
        }

        public void b(j jVar, Fragment fragment, Context context) {
        }

        public void b(j jVar, Fragment fragment, Bundle bundle) {
        }

        public void c(j jVar, Fragment fragment) {
        }

        public void c(j jVar, Fragment fragment, Bundle bundle) {
        }

        public void d(j jVar, Fragment fragment) {
        }

        public void d(j jVar, Fragment fragment, Bundle bundle) {
        }

        public void e(j jVar, Fragment fragment) {
        }

        public void f(j jVar, Fragment fragment) {
        }

        public void g(j jVar, Fragment fragment) {
        }
    }

    /* compiled from: FragmentManager */
    public interface g {
        void a();
    }

    /* compiled from: FragmentManager */
    interface h {
        boolean a(ArrayList<a> arrayList, ArrayList<Boolean> arrayList2);
    }

    /* compiled from: FragmentManager */
    private class i implements h {
        final String a;
        final int b;
        final int c;

        i(String str, int i2, int i3) {
            this.a = str;
            this.b = i2;
            this.c = i3;
        }

        public boolean a(ArrayList<a> arrayList, ArrayList<Boolean> arrayList2) {
            Fragment fragment = j.this.r;
            if (fragment != null && this.b < 0 && this.a == null && fragment.j().A()) {
                return false;
            }
            return j.this.a(arrayList, arrayList2, this.a, this.b, this.c);
        }
    }

    /* renamed from: androidx.fragment.app.j$j  reason: collision with other inner class name */
    /* compiled from: FragmentManager */
    static class C0034j implements Fragment.e {
        final boolean a;
        final a b;
        private int c;

        C0034j(a aVar, boolean z) {
            this.a = z;
            this.b = aVar;
        }

        public void a() {
            int i2 = this.c - 1;
            this.c = i2;
            if (i2 == 0) {
                this.b.r.C();
            }
        }

        public void b() {
            this.c++;
        }

        /* access modifiers changed from: package-private */
        public void c() {
            a aVar = this.b;
            aVar.r.a(aVar, this.a, false, false);
        }

        /* access modifiers changed from: package-private */
        public void d() {
            boolean z = this.c > 0;
            for (Fragment next : this.b.r.q()) {
                next.setOnStartEnterTransitionListener((Fragment.e) null);
                if (z && next.M()) {
                    next.m0();
                }
            }
            a aVar = this.b;
            aVar.r.a(aVar, this.a, !z, true);
        }

        public boolean e() {
            return this.c == 0;
        }
    }

    private void D() {
        if (x()) {
            throw new IllegalStateException("Can not perform this action after onSaveInstanceState");
        }
    }

    private void E() {
        this.b = false;
        this.A.clear();
        this.z.clear();
    }

    private void F() {
        if (this.y) {
            this.y = false;
            J();
        }
    }

    private void G() {
        if (!this.k.isEmpty()) {
            for (Fragment next : this.k.keySet()) {
                q(next);
                a(next, next.D());
            }
        }
    }

    private void H() {
        if (this.C != null) {
            while (!this.C.isEmpty()) {
                this.C.remove(0).d();
            }
        }
    }

    private void I() {
        if (this.f623j != null) {
            for (int i2 = 0; i2 < this.f623j.size(); i2++) {
                this.f623j.get(i2).a();
            }
        }
    }

    private void J() {
        for (Fragment next : this.c.b()) {
            if (next != null) {
                k(next);
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x001a, code lost:
        if (o() <= 0) goto L_0x0025;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0022, code lost:
        if (g(r3.q) == false) goto L_0x0025;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0025, code lost:
        r2 = false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0026, code lost:
        r0.a(r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0029, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0014, code lost:
        r0 = r3.f621h;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void K() {
        /*
            r3 = this;
            java.util.ArrayList<androidx.fragment.app.j$h> r0 = r3.a
            monitor-enter(r0)
            java.util.ArrayList<androidx.fragment.app.j$h> r1 = r3.a     // Catch:{ all -> 0x002a }
            boolean r1 = r1.isEmpty()     // Catch:{ all -> 0x002a }
            r2 = 1
            if (r1 != 0) goto L_0x0013
            androidx.activity.b r1 = r3.f621h     // Catch:{ all -> 0x002a }
            r1.a((boolean) r2)     // Catch:{ all -> 0x002a }
            monitor-exit(r0)     // Catch:{ all -> 0x002a }
            return
        L_0x0013:
            monitor-exit(r0)     // Catch:{ all -> 0x002a }
            androidx.activity.b r0 = r3.f621h
            int r1 = r3.o()
            if (r1 <= 0) goto L_0x0025
            androidx.fragment.app.Fragment r1 = r3.q
            boolean r1 = r3.g(r1)
            if (r1 == 0) goto L_0x0025
            goto L_0x0026
        L_0x0025:
            r2 = 0
        L_0x0026:
            r0.a((boolean) r2)
            return
        L_0x002a:
            r1 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x002a }
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.fragment.app.j.K():void");
    }

    private void a(RuntimeException runtimeException) {
        Log.e("FragmentManager", runtimeException.getMessage());
        Log.e("FragmentManager", "Activity state:");
        PrintWriter printWriter = new PrintWriter(new androidx.core.g.b("FragmentManager"));
        g<?> gVar = this.o;
        if (gVar != null) {
            try {
                gVar.a("  ", (FileDescriptor) null, printWriter, new String[0]);
            } catch (Exception e2) {
                Log.e("FragmentManager", "Failed dumping state", e2);
            }
        } else {
            try {
                a("  ", (FileDescriptor) null, printWriter, new String[0]);
            } catch (Exception e3) {
                Log.e("FragmentManager", "Failed dumping state", e3);
            }
        }
        throw runtimeException;
    }

    static boolean d(int i2) {
        return F || Log.isLoggable("FragmentManager", i2);
    }

    static int e(int i2) {
        if (i2 == 4097) {
            return 8194;
        }
        if (i2 != 4099) {
            return i2 != 8194 ? 0 : 4097;
        }
        return 4099;
    }

    private void r(Fragment fragment) {
        Animator animator;
        if (fragment.K != null) {
            c.d a2 = c.a(this.o.e(), this.p, fragment, !fragment.C);
            if (a2 == null || (animator = a2.b) == null) {
                if (a2 != null) {
                    fragment.K.startAnimation(a2.a);
                    a2.a.start();
                }
                fragment.K.setVisibility((!fragment.C || fragment.K()) ? 0 : 8);
                if (fragment.K()) {
                    fragment.g(false);
                }
            } else {
                animator.setTarget(fragment.K);
                if (!fragment.C) {
                    fragment.K.setVisibility(0);
                } else if (fragment.K()) {
                    fragment.g(false);
                } else {
                    ViewGroup viewGroup = fragment.J;
                    View view = fragment.K;
                    viewGroup.startViewTransition(view);
                    a2.b.addListener(new e(this, viewGroup, view, fragment));
                }
                a2.b.start();
            }
        }
        if (fragment.o && w(fragment)) {
            this.u = true;
        }
        fragment.P = false;
        fragment.a(fragment.C);
    }

    private void s(Fragment fragment) {
        fragment.b0();
        this.m.g(fragment, false);
        fragment.J = null;
        fragment.K = null;
        fragment.V = null;
        fragment.W.setValue(null);
        fragment.r = false;
    }

    private l u(Fragment fragment) {
        return this.D.c(fragment);
    }

    public boolean A() {
        return a((String) null, -1, 0);
    }

    /* access modifiers changed from: package-private */
    public Parcelable B() {
        int size;
        H();
        G();
        c(true);
        this.v = true;
        ArrayList<FragmentState> e2 = this.c.e();
        BackStackState[] backStackStateArr = null;
        if (e2.isEmpty()) {
            if (d(2)) {
                Log.v("FragmentManager", "saveAllState: no fragments!");
            }
            return null;
        }
        ArrayList<String> f2 = this.c.f();
        ArrayList<a> arrayList = this.d;
        if (arrayList != null && (size = arrayList.size()) > 0) {
            backStackStateArr = new BackStackState[size];
            for (int i2 = 0; i2 < size; i2++) {
                backStackStateArr[i2] = new BackStackState(this.d.get(i2));
                if (d(2)) {
                    Log.v("FragmentManager", "saveAllState: adding back stack #" + i2 + ": " + this.d.get(i2));
                }
            }
        }
        FragmentManagerState fragmentManagerState = new FragmentManagerState();
        fragmentManagerState.e = e2;
        fragmentManagerState.f598f = f2;
        fragmentManagerState.f599g = backStackStateArr;
        fragmentManagerState.f600h = this.f622i.get();
        Fragment fragment = this.r;
        if (fragment != null) {
            fragmentManagerState.f601i = fragment.f588i;
        }
        return fragmentManagerState;
    }

    /* access modifiers changed from: package-private */
    public void C() {
        synchronized (this.a) {
            boolean z2 = false;
            boolean z3 = this.C != null && !this.C.isEmpty();
            if (this.a.size() == 1) {
                z2 = true;
            }
            if (z3 || z2) {
                this.o.f().removeCallbacks(this.E);
                this.o.f().post(this.E);
                K();
            }
        }
    }

    public void addOnBackStackChangedListener(g gVar) {
        if (this.f623j == null) {
            this.f623j = new ArrayList<>();
        }
        this.f623j.add(gVar);
    }

    public p b() {
        return new a(this);
    }

    /* access modifiers changed from: package-private */
    public void c(Fragment fragment) {
        if (d(2)) {
            Log.v("FragmentManager", "attach: " + fragment);
        }
        if (fragment.D) {
            fragment.D = false;
            if (!fragment.o) {
                this.c.a(fragment);
                if (d(2)) {
                    Log.v("FragmentManager", "add from attach: " + fragment);
                }
                if (w(fragment)) {
                    this.u = true;
                }
            }
        }
    }

    /* access modifiers changed from: package-private */
    public ViewModelStore e(Fragment fragment) {
        return this.D.d(fragment);
    }

    /* access modifiers changed from: package-private */
    public void f(Fragment fragment) {
        if (d(2)) {
            Log.v("FragmentManager", "hide: " + fragment);
        }
        if (!fragment.C) {
            fragment.C = true;
            fragment.P = true ^ fragment.P;
            x(fragment);
        }
    }

    /* access modifiers changed from: package-private */
    public boolean g(Fragment fragment) {
        if (fragment == null) {
            return true;
        }
        j jVar = fragment.v;
        if (!fragment.equals(jVar.u()) || !g(jVar.q)) {
            return false;
        }
        return true;
    }

    /* access modifiers changed from: package-private */
    public void h(Fragment fragment) {
        if (!this.c.a(fragment.f588i)) {
            n nVar = new n(this.m, fragment);
            nVar.a(this.o.e().getClassLoader());
            this.c.a(nVar);
            if (fragment.F) {
                if (fragment.E) {
                    b(fragment);
                } else {
                    m(fragment);
                }
                fragment.F = false;
            }
            nVar.a(this.n);
            if (d(2)) {
                Log.v("FragmentManager", "Added fragment to active set " + fragment);
            }
        }
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0049, code lost:
        r0 = r0.K;
        r1 = r4.J;
        r0 = r1.indexOfChild(r0);
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void i(androidx.fragment.app.Fragment r4) {
        /*
            r3 = this;
            androidx.fragment.app.o r0 = r3.c
            java.lang.String r1 = r4.f588i
            boolean r0 = r0.a((java.lang.String) r1)
            if (r0 != 0) goto L_0x003a
            r0 = 3
            boolean r0 = d((int) r0)
            if (r0 == 0) goto L_0x0039
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r1 = "Ignoring moving "
            r0.append(r1)
            r0.append(r4)
            java.lang.String r4 = " to state "
            r0.append(r4)
            int r4 = r3.n
            r0.append(r4)
            java.lang.String r4 = "since it is not added to "
            r0.append(r4)
            r0.append(r3)
            java.lang.String r4 = r0.toString()
            java.lang.String r0 = "FragmentManager"
            android.util.Log.d(r0, r4)
        L_0x0039:
            return
        L_0x003a:
            r3.j(r4)
            android.view.View r0 = r4.K
            if (r0 == 0) goto L_0x009f
            androidx.fragment.app.o r0 = r3.c
            androidx.fragment.app.Fragment r0 = r0.b((androidx.fragment.app.Fragment) r4)
            if (r0 == 0) goto L_0x0061
            android.view.View r0 = r0.K
            android.view.ViewGroup r1 = r4.J
            int r0 = r1.indexOfChild(r0)
            android.view.View r2 = r4.K
            int r2 = r1.indexOfChild(r2)
            if (r2 >= r0) goto L_0x0061
            r1.removeViewAt(r2)
            android.view.View r2 = r4.K
            r1.addView(r2, r0)
        L_0x0061:
            boolean r0 = r4.O
            if (r0 == 0) goto L_0x009f
            android.view.ViewGroup r0 = r4.J
            if (r0 == 0) goto L_0x009f
            float r0 = r4.Q
            r1 = 0
            int r2 = (r0 > r1 ? 1 : (r0 == r1 ? 0 : -1))
            if (r2 <= 0) goto L_0x0075
            android.view.View r2 = r4.K
            r2.setAlpha(r0)
        L_0x0075:
            r4.Q = r1
            r0 = 0
            r4.O = r0
            androidx.fragment.app.g<?> r0 = r3.o
            android.content.Context r0 = r0.e()
            androidx.fragment.app.d r1 = r3.p
            r2 = 1
            androidx.fragment.app.c$d r0 = androidx.fragment.app.c.a(r0, r1, r4, r2)
            if (r0 == 0) goto L_0x009f
            android.view.animation.Animation r1 = r0.a
            if (r1 == 0) goto L_0x0093
            android.view.View r0 = r4.K
            r0.startAnimation(r1)
            goto L_0x009f
        L_0x0093:
            android.animation.Animator r1 = r0.b
            android.view.View r2 = r4.K
            r1.setTarget(r2)
            android.animation.Animator r0 = r0.b
            r0.start()
        L_0x009f:
            boolean r0 = r4.P
            if (r0 == 0) goto L_0x00a6
            r3.r(r4)
        L_0x00a6:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.fragment.app.j.i(androidx.fragment.app.Fragment):void");
    }

    /* access modifiers changed from: package-private */
    public void j(Fragment fragment) {
        a(fragment, this.n);
    }

    /* access modifiers changed from: package-private */
    public void k(Fragment fragment) {
        if (!fragment.L) {
            return;
        }
        if (this.b) {
            this.y = true;
            return;
        }
        fragment.L = false;
        a(fragment, this.n);
    }

    /* access modifiers changed from: package-private */
    public void l(Fragment fragment) {
        if (d(2)) {
            Log.v("FragmentManager", "remove: " + fragment + " nesting=" + fragment.u);
        }
        boolean z2 = !fragment.L();
        if (!fragment.D || z2) {
            this.c.c(fragment);
            if (w(fragment)) {
                this.u = true;
            }
            fragment.p = true;
            x(fragment);
        }
    }

    /* access modifiers changed from: package-private */
    public void m(Fragment fragment) {
        if (x()) {
            if (d(2)) {
                Log.v("FragmentManager", "Ignoring removeRetainedFragment as the state is already saved");
            }
        } else if (this.D.e(fragment) && d(2)) {
            Log.v("FragmentManager", "Updating retained Fragments: Removed " + fragment);
        }
    }

    public boolean n() {
        boolean c2 = c(true);
        H();
        return c2;
    }

    public int o() {
        ArrayList<a> arrayList = this.d;
        if (arrayList != null) {
            return arrayList.size();
        }
        return 0;
    }

    /* access modifiers changed from: package-private */
    public void p(Fragment fragment) {
        if (d(2)) {
            Log.v("FragmentManager", "show: " + fragment);
        }
        if (fragment.C) {
            fragment.C = false;
            fragment.P = !fragment.P;
        }
    }

    public List<Fragment> q() {
        return this.c.c();
    }

    public void removeOnBackStackChangedListener(g gVar) {
        ArrayList<g> arrayList = this.f623j;
        if (arrayList != null) {
            arrayList.remove(gVar);
        }
    }

    /* access modifiers changed from: package-private */
    public Fragment t() {
        return this.q;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(128);
        sb.append("FragmentManager{");
        sb.append(Integer.toHexString(System.identityHashCode(this)));
        sb.append(" in ");
        Fragment fragment = this.q;
        if (fragment != null) {
            sb.append(fragment.getClass().getSimpleName());
            sb.append("{");
            sb.append(Integer.toHexString(System.identityHashCode(this.q)));
            sb.append("}");
        } else {
            g<?> gVar = this.o;
            if (gVar != null) {
                sb.append(gVar.getClass().getSimpleName());
                sb.append("{");
                sb.append(Integer.toHexString(System.identityHashCode(this.o)));
                sb.append("}");
            } else {
                sb.append("null");
            }
        }
        sb.append("}}");
        return sb.toString();
    }

    /* access modifiers changed from: package-private */
    public void v() {
        c(true);
        if (this.f621h.b()) {
            A();
        } else {
            this.f620g.a();
        }
    }

    public boolean w() {
        return this.x;
    }

    public boolean x() {
        return this.v || this.w;
    }

    /* access modifiers changed from: package-private */
    public void y() {
        if (this.o != null) {
            this.v = false;
            this.w = false;
            for (Fragment next : this.c.c()) {
                if (next != null) {
                    next.Q();
                }
            }
        }
    }

    public void z() {
        a((h) new i((String) null, -1, 0), false);
    }

    private void q(Fragment fragment) {
        HashSet hashSet = this.k.get(fragment);
        if (hashSet != null) {
            Iterator it = hashSet.iterator();
            while (it.hasNext()) {
                ((androidx.core.d.a) it.next()).a();
            }
            hashSet.clear();
            s(fragment);
            this.k.remove(fragment);
        }
    }

    private void t(Fragment fragment) {
        if (fragment != null && fragment.equals(a(fragment.f588i))) {
            fragment.f0();
        }
    }

    private boolean w(Fragment fragment) {
        return (fragment.G && fragment.H) || fragment.x.c();
    }

    private void x(Fragment fragment) {
        ViewGroup v2 = v(fragment);
        if (v2 != null) {
            if (v2.getTag(R$id.visible_removing_fragment_view_tag) == null) {
                v2.setTag(R$id.visible_removing_fragment_view_tag, fragment);
            }
            ((Fragment) v2.getTag(R$id.visible_removing_fragment_view_tag)).b(fragment.t());
        }
    }

    /* access modifiers changed from: package-private */
    public void b(Fragment fragment, androidx.core.d.a aVar) {
        HashSet hashSet = this.k.get(fragment);
        if (hashSet != null && hashSet.remove(aVar) && hashSet.isEmpty()) {
            this.k.remove(fragment);
            if (fragment.e < 3) {
                s(fragment);
                a(fragment, fragment.D());
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void d(Fragment fragment) {
        if (d(2)) {
            Log.v("FragmentManager", "detach: " + fragment);
        }
        if (!fragment.D) {
            fragment.D = true;
            if (fragment.o) {
                if (d(2)) {
                    Log.v("FragmentManager", "remove from detach: " + fragment);
                }
                this.c.c(fragment);
                if (w(fragment)) {
                    this.u = true;
                }
                x(fragment);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void e() {
        this.v = false;
        this.w = false;
        c(1);
    }

    /* access modifiers changed from: package-private */
    public void j() {
        K();
        t(this.r);
    }

    /* access modifiers changed from: package-private */
    public void o(Fragment fragment) {
        if (fragment == null || (fragment.equals(a(fragment.f588i)) && (fragment.w == null || fragment.v == this))) {
            Fragment fragment2 = this.r;
            this.r = fragment;
            t(fragment2);
            t(this.r);
            return;
        }
        throw new IllegalArgumentException("Fragment " + fragment + " is not an active fragment of FragmentManager " + this);
    }

    public Fragment u() {
        return this.r;
    }

    public Fragment.SavedState n(Fragment fragment) {
        n e2 = this.c.e(fragment.f588i);
        if (e2 != null && e2.e().equals(fragment)) {
            return e2.i();
        }
        a((RuntimeException) new IllegalStateException("Fragment " + fragment + " is not currently in the FragmentManager"));
        throw null;
    }

    private ViewGroup v(Fragment fragment) {
        if (fragment.A > 0 && this.p.c()) {
            View a2 = this.p.a(fragment.A);
            if (a2 instanceof ViewGroup) {
                return (ViewGroup) a2;
            }
        }
        return null;
    }

    /* access modifiers changed from: package-private */
    public void g() {
        c(1);
    }

    public f p() {
        f fVar = this.s;
        if (fVar != null) {
            return fVar;
        }
        Fragment fragment = this.q;
        if (fragment != null) {
            return fragment.v.p();
        }
        return this.t;
    }

    /* access modifiers changed from: package-private */
    public void f() {
        this.x = true;
        c(true);
        G();
        c(-1);
        this.o = null;
        this.p = null;
        this.q = null;
        if (this.f620g != null) {
            this.f621h.c();
            this.f620g = null;
        }
    }

    /* access modifiers changed from: package-private */
    public void k() {
        this.v = false;
        this.w = false;
        c(4);
    }

    /* access modifiers changed from: package-private */
    public void m() {
        this.w = true;
        c(2);
    }

    /* access modifiers changed from: package-private */
    public void b(Fragment fragment) {
        if (x()) {
            if (d(2)) {
                Log.v("FragmentManager", "Ignoring addRetainedFragment as the state is already saved");
            }
        } else if (this.D.a(fragment) && d(2)) {
            Log.v("FragmentManager", "Updating retained Fragments: Added " + fragment);
        }
    }

    /* access modifiers changed from: package-private */
    public i s() {
        return this.m;
    }

    /* access modifiers changed from: package-private */
    public Fragment c(String str) {
        return this.c.d(str);
    }

    /* JADX INFO: finally extract failed */
    /* access modifiers changed from: package-private */
    public boolean c(boolean z2) {
        d(z2);
        boolean z3 = false;
        while (b(this.z, this.A)) {
            this.b = true;
            try {
                c(this.z, this.A);
                E();
                z3 = true;
            } catch (Throwable th) {
                E();
                throw th;
            }
        }
        K();
        F();
        this.c.a();
        return z3;
    }

    /* access modifiers changed from: package-private */
    public void l() {
        this.v = false;
        this.w = false;
        c(3);
    }

    private void d(boolean z2) {
        if (this.b) {
            throw new IllegalStateException("FragmentManager is already executing transactions");
        } else if (this.o == null) {
            if (this.x) {
                throw new IllegalStateException("FragmentManager has been destroyed");
            }
            throw new IllegalStateException("FragmentManager has not been attached to a host.");
        } else if (Looper.myLooper() == this.o.f().getLooper()) {
            if (!z2) {
                D();
            }
            if (this.z == null) {
                this.z = new ArrayList<>();
                this.A = new ArrayList<>();
            }
            this.b = true;
            try {
                a((ArrayList<a>) null, (ArrayList<Boolean>) null);
            } finally {
                this.b = false;
            }
        } else {
            throw new IllegalStateException("Must be called from main thread of fragment host");
        }
    }

    public void a(String str, int i2) {
        a((h) new i(str, -1, i2), false);
    }

    public void a(int i2, int i3) {
        if (i2 >= 0) {
            a((h) new i((String) null, i2, i3), false);
            return;
        }
        throw new IllegalArgumentException("Bad id: " + i2);
    }

    /* access modifiers changed from: package-private */
    public void h() {
        for (Fragment next : this.c.c()) {
            if (next != null) {
                next.d0();
            }
        }
    }

    private boolean a(String str, int i2, int i3) {
        c(false);
        d(true);
        Fragment fragment = this.r;
        if (fragment != null && i2 < 0 && str == null && fragment.j().A()) {
            return true;
        }
        boolean a2 = a(this.z, this.A, str, i2, i3);
        if (a2) {
            this.b = true;
            try {
                c(this.z, this.A);
            } finally {
                E();
            }
        }
        K();
        F();
        this.c.a();
        return a2;
    }

    /* access modifiers changed from: package-private */
    public boolean b(int i2) {
        return this.n >= i2;
    }

    public Fragment b(String str) {
        return this.c.c(str);
    }

    /* access modifiers changed from: package-private */
    public void b(h hVar, boolean z2) {
        if (!z2 || (this.o != null && !this.x)) {
            d(z2);
            if (hVar.a(this.z, this.A)) {
                this.b = true;
                try {
                    c(this.z, this.A);
                } finally {
                    E();
                }
            }
            K();
            F();
            this.c.a();
        }
    }

    private void c(ArrayList<a> arrayList, ArrayList<Boolean> arrayList2) {
        if (!arrayList.isEmpty()) {
            if (arrayList.size() == arrayList2.size()) {
                a(arrayList, arrayList2);
                int size = arrayList.size();
                int i2 = 0;
                int i3 = 0;
                while (i2 < size) {
                    if (!arrayList.get(i2).p) {
                        if (i3 != i2) {
                            b(arrayList, arrayList2, i3, i2);
                        }
                        i3 = i2 + 1;
                        if (arrayList2.get(i2).booleanValue()) {
                            while (i3 < size && arrayList2.get(i3).booleanValue() && !arrayList.get(i3).p) {
                                i3++;
                            }
                        }
                        b(arrayList, arrayList2, i2, i3);
                        i2 = i3 - 1;
                    }
                    i2++;
                }
                if (i3 != size) {
                    b(arrayList, arrayList2, i3, size);
                    return;
                }
                return;
            }
            throw new IllegalStateException("Internal error with the back stack records");
        }
    }

    private void b(ArrayList<a> arrayList, ArrayList<Boolean> arrayList2, int i2, int i3) {
        int i4;
        ArrayList<a> arrayList3 = arrayList;
        ArrayList<Boolean> arrayList4 = arrayList2;
        int i5 = i2;
        int i6 = i3;
        boolean z2 = arrayList3.get(i5).p;
        ArrayList<Fragment> arrayList5 = this.B;
        if (arrayList5 == null) {
            this.B = new ArrayList<>();
        } else {
            arrayList5.clear();
        }
        this.B.addAll(this.c.c());
        Fragment u2 = u();
        boolean z3 = false;
        for (int i7 = i5; i7 < i6; i7++) {
            a aVar = arrayList3.get(i7);
            if (!arrayList4.get(i7).booleanValue()) {
                u2 = aVar.a(this.B, u2);
            } else {
                u2 = aVar.b(this.B, u2);
            }
            z3 = z3 || aVar.f628g;
        }
        this.B.clear();
        if (!z2) {
            q.a(this, arrayList, arrayList2, i2, i3, false, this.l);
        }
        a(arrayList, arrayList2, i2, i3);
        if (z2) {
            g.a.b bVar = new g.a.b();
            a((g.a.b<Fragment>) bVar);
            int a2 = a(arrayList, arrayList2, i2, i3, (g.a.b<Fragment>) bVar);
            b((g.a.b<Fragment>) bVar);
            i4 = a2;
        } else {
            i4 = i6;
        }
        if (i4 != i5 && z2) {
            q.a(this, arrayList, arrayList2, i2, i4, true, this.l);
            a(this.n, true);
        }
        while (i5 < i6) {
            a aVar2 = arrayList3.get(i5);
            if (arrayList4.get(i5).booleanValue() && aVar2.t >= 0) {
                aVar2.t = -1;
            }
            aVar2.j();
            i5++;
        }
        if (z3) {
            I();
        }
    }

    /* access modifiers changed from: package-private */
    public LayoutInflater.Factory2 r() {
        return this.f619f;
    }

    /* access modifiers changed from: package-private */
    public void a(Fragment fragment, androidx.core.d.a aVar) {
        if (this.k.get(fragment) == null) {
            this.k.put(fragment, new HashSet());
        }
        this.k.get(fragment).add(aVar);
    }

    /* access modifiers changed from: package-private */
    public void d() {
        this.v = false;
        this.w = false;
        c(2);
    }

    /* access modifiers changed from: package-private */
    public void i() {
        c(3);
    }

    public void a(Bundle bundle, String str, Fragment fragment) {
        if (fragment.v == this) {
            bundle.putString(str, fragment.f588i);
            return;
        }
        a((RuntimeException) new IllegalStateException("Fragment " + fragment + " is not currently in the FragmentManager"));
        throw null;
    }

    /* JADX INFO: finally extract failed */
    private void c(int i2) {
        try {
            this.b = true;
            this.c.a(i2);
            a(i2, false);
            this.b = false;
            c(true);
        } catch (Throwable th) {
            this.b = false;
            throw th;
        }
    }

    public Fragment a(Bundle bundle, String str) {
        String string = bundle.getString(str);
        if (string == null) {
            return null;
        }
        Fragment a2 = a(string);
        if (a2 != null) {
            return a2;
        }
        a((RuntimeException) new IllegalStateException("Fragment no longer exists for key " + str + ": unique id " + string));
        throw null;
    }

    static Fragment a(View view) {
        Object tag = view.getTag(R$id.fragment_container_view_tag);
        if (tag instanceof Fragment) {
            return (Fragment) tag;
        }
        return null;
    }

    /* access modifiers changed from: package-private */
    public boolean c() {
        boolean z2 = false;
        for (Fragment next : this.c.b()) {
            if (next != null) {
                z2 = w(next);
                continue;
            }
            if (z2) {
                return true;
            }
        }
        return false;
    }

    public void a(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        int size;
        int size2;
        String str2 = str + "    ";
        this.c.a(str, fileDescriptor, printWriter, strArr);
        ArrayList<Fragment> arrayList = this.e;
        if (arrayList != null && (size2 = arrayList.size()) > 0) {
            printWriter.print(str);
            printWriter.println("Fragments Created Menus:");
            for (int i2 = 0; i2 < size2; i2++) {
                printWriter.print(str);
                printWriter.print("  #");
                printWriter.print(i2);
                printWriter.print(": ");
                printWriter.println(this.e.get(i2).toString());
            }
        }
        ArrayList<a> arrayList2 = this.d;
        if (arrayList2 != null && (size = arrayList2.size()) > 0) {
            printWriter.print(str);
            printWriter.println("Back Stack:");
            for (int i3 = 0; i3 < size; i3++) {
                a aVar = this.d.get(i3);
                printWriter.print(str);
                printWriter.print("  #");
                printWriter.print(i3);
                printWriter.print(": ");
                printWriter.println(aVar.toString());
                aVar.a(str2, printWriter);
            }
        }
        printWriter.print(str);
        printWriter.println("Back Stack Index: " + this.f622i.get());
        synchronized (this.a) {
            int size3 = this.a.size();
            if (size3 > 0) {
                printWriter.print(str);
                printWriter.println("Pending Actions:");
                for (int i4 = 0; i4 < size3; i4++) {
                    printWriter.print(str);
                    printWriter.print("  #");
                    printWriter.print(i4);
                    printWriter.print(": ");
                    printWriter.println(this.a.get(i4));
                }
            }
        }
        printWriter.print(str);
        printWriter.println("FragmentManager misc state:");
        printWriter.print(str);
        printWriter.print("  mHost=");
        printWriter.println(this.o);
        printWriter.print(str);
        printWriter.print("  mContainer=");
        printWriter.println(this.p);
        if (this.q != null) {
            printWriter.print(str);
            printWriter.print("  mParent=");
            printWriter.println(this.q);
        }
        printWriter.print(str);
        printWriter.print("  mCurState=");
        printWriter.print(this.n);
        printWriter.print(" mStateSaved=");
        printWriter.print(this.v);
        printWriter.print(" mStopped=");
        printWriter.print(this.w);
        printWriter.print(" mDestroyed=");
        printWriter.println(this.x);
        if (this.u) {
            printWriter.print(str);
            printWriter.print("  mNeedMenuInvalidate=");
            printWriter.println(this.u);
        }
    }

    private void b(g.a.b<Fragment> bVar) {
        int size = bVar.size();
        for (int i2 = 0; i2 < size; i2++) {
            Fragment c2 = bVar.c(i2);
            if (!c2.o) {
                View l0 = c2.l0();
                c2.Q = l0.getAlpha();
                l0.setAlpha(0.0f);
            }
        }
    }

    private boolean b(ArrayList<a> arrayList, ArrayList<Boolean> arrayList2) {
        synchronized (this.a) {
            if (this.a.isEmpty()) {
                return false;
            }
            int size = this.a.size();
            boolean z2 = false;
            for (int i2 = 0; i2 < size; i2++) {
                z2 |= this.a.get(i2).a(arrayList, arrayList2);
            }
            this.a.clear();
            this.o.f().removeCallbacks(this.E);
            return z2;
        }
    }

    /* access modifiers changed from: package-private */
    public void b(boolean z2) {
        for (Fragment next : this.c.c()) {
            if (next != null) {
                next.f(z2);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public boolean b(Menu menu) {
        boolean z2 = false;
        if (this.n < 1) {
            return false;
        }
        for (Fragment next : this.c.c()) {
            if (next != null && next.d(menu)) {
                z2 = true;
            }
        }
        return z2;
    }

    /* access modifiers changed from: package-private */
    public boolean b(MenuItem menuItem) {
        if (this.n < 1) {
            return false;
        }
        for (Fragment next : this.c.c()) {
            if (next != null && next.d(menuItem)) {
                return true;
            }
        }
        return false;
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x003e, code lost:
        if (r2 != 3) goto L_0x01f5;
     */
    /* JADX WARNING: Removed duplicated region for block: B:120:0x01ef  */
    /* JADX WARNING: Removed duplicated region for block: B:42:0x00e6  */
    /* JADX WARNING: Removed duplicated region for block: B:44:0x00eb  */
    /* JADX WARNING: Removed duplicated region for block: B:46:0x00f8  */
    /* JADX WARNING: Removed duplicated region for block: B:48:0x00fd  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void a(androidx.fragment.app.Fragment r13, int r14) {
        /*
            r12 = this;
            androidx.fragment.app.o r0 = r12.c
            java.lang.String r1 = r13.f588i
            androidx.fragment.app.n r0 = r0.e(r1)
            r1 = 1
            if (r0 != 0) goto L_0x0015
            androidx.fragment.app.n r0 = new androidx.fragment.app.n
            androidx.fragment.app.i r2 = r12.m
            r0.<init>(r2, r13)
            r0.a((int) r1)
        L_0x0015:
            int r2 = r0.b()
            int r14 = java.lang.Math.min(r14, r2)
            int r2 = r13.e
            r3 = 0
            java.lang.String r4 = "FragmentManager"
            r5 = -1
            r6 = 2
            r7 = 3
            if (r2 > r14) goto L_0x0102
            if (r2 >= r14) goto L_0x0034
            java.util.concurrent.ConcurrentHashMap<androidx.fragment.app.Fragment, java.util.HashSet<androidx.core.d.a>> r2 = r12.k
            boolean r2 = r2.isEmpty()
            if (r2 != 0) goto L_0x0034
            r12.q(r13)
        L_0x0034:
            int r2 = r13.e
            if (r2 == r5) goto L_0x0042
            if (r2 == 0) goto L_0x00df
            if (r2 == r1) goto L_0x00e4
            if (r2 == r6) goto L_0x00f6
            if (r2 == r7) goto L_0x00fb
            goto L_0x01f5
        L_0x0042:
            if (r14 <= r5) goto L_0x00df
            boolean r2 = d((int) r7)
            if (r2 == 0) goto L_0x005e
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r8 = "moveto ATTACHED: "
            r2.append(r8)
            r2.append(r13)
            java.lang.String r2 = r2.toString()
            android.util.Log.d(r4, r2)
        L_0x005e:
            androidx.fragment.app.Fragment r2 = r13.k
            java.lang.String r8 = " that does not belong to this FragmentManager!"
            java.lang.String r9 = " declared target fragment "
            java.lang.String r10 = "Fragment "
            if (r2 == 0) goto L_0x00a6
            java.lang.String r11 = r2.f588i
            androidx.fragment.app.Fragment r11 = r12.a((java.lang.String) r11)
            boolean r2 = r2.equals(r11)
            if (r2 == 0) goto L_0x0086
            androidx.fragment.app.Fragment r2 = r13.k
            int r11 = r2.e
            if (r11 >= r1) goto L_0x007d
            r12.a((androidx.fragment.app.Fragment) r2, (int) r1)
        L_0x007d:
            androidx.fragment.app.Fragment r2 = r13.k
            java.lang.String r2 = r2.f588i
            r13.l = r2
            r13.k = r3
            goto L_0x00a6
        L_0x0086:
            java.lang.IllegalStateException r14 = new java.lang.IllegalStateException
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            r0.append(r10)
            r0.append(r13)
            r0.append(r9)
            androidx.fragment.app.Fragment r13 = r13.k
            r0.append(r13)
            r0.append(r8)
            java.lang.String r13 = r0.toString()
            r14.<init>(r13)
            throw r14
        L_0x00a6:
            java.lang.String r2 = r13.l
            if (r2 == 0) goto L_0x00d8
            androidx.fragment.app.Fragment r2 = r12.a((java.lang.String) r2)
            if (r2 == 0) goto L_0x00b8
            int r3 = r2.e
            if (r3 >= r1) goto L_0x00d8
            r12.a((androidx.fragment.app.Fragment) r2, (int) r1)
            goto L_0x00d8
        L_0x00b8:
            java.lang.IllegalStateException r14 = new java.lang.IllegalStateException
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            r0.append(r10)
            r0.append(r13)
            r0.append(r9)
            java.lang.String r13 = r13.l
            r0.append(r13)
            r0.append(r8)
            java.lang.String r13 = r0.toString()
            r14.<init>(r13)
            throw r14
        L_0x00d8:
            androidx.fragment.app.g<?> r2 = r12.o
            androidx.fragment.app.Fragment r3 = r12.q
            r0.a(r2, r12, r3)
        L_0x00df:
            if (r14 <= 0) goto L_0x00e4
            r0.c()
        L_0x00e4:
            if (r14 <= r5) goto L_0x00e9
            r0.d()
        L_0x00e9:
            if (r14 <= r1) goto L_0x00f6
            androidx.fragment.app.d r1 = r12.p
            r0.a((androidx.fragment.app.d) r1)
            r0.a()
            r0.g()
        L_0x00f6:
            if (r14 <= r6) goto L_0x00fb
            r0.l()
        L_0x00fb:
            if (r14 <= r7) goto L_0x01f5
            r0.h()
            goto L_0x01f5
        L_0x0102:
            if (r2 <= r14) goto L_0x01f5
            if (r2 == 0) goto L_0x01ec
            r8 = 0
            if (r2 == r1) goto L_0x01ab
            if (r2 == r6) goto L_0x011c
            if (r2 == r7) goto L_0x0117
            r9 = 4
            if (r2 == r9) goto L_0x0112
            goto L_0x01f5
        L_0x0112:
            if (r14 >= r9) goto L_0x0117
            r0.f()
        L_0x0117:
            if (r14 >= r7) goto L_0x011c
            r0.m()
        L_0x011c:
            if (r14 >= r6) goto L_0x01ab
            boolean r2 = d((int) r7)
            if (r2 == 0) goto L_0x0138
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r6 = "movefrom ACTIVITY_CREATED: "
            r2.append(r6)
            r2.append(r13)
            java.lang.String r2 = r2.toString()
            android.util.Log.d(r4, r2)
        L_0x0138:
            android.view.View r2 = r13.K
            if (r2 == 0) goto L_0x014b
            androidx.fragment.app.g<?> r2 = r12.o
            boolean r2 = r2.b(r13)
            if (r2 == 0) goto L_0x014b
            android.util.SparseArray<android.os.Parcelable> r2 = r13.f586g
            if (r2 != 0) goto L_0x014b
            r0.k()
        L_0x014b:
            android.view.View r2 = r13.K
            if (r2 == 0) goto L_0x019c
            android.view.ViewGroup r6 = r13.J
            if (r6 == 0) goto L_0x019c
            r6.endViewTransition(r2)
            android.view.View r2 = r13.K
            r2.clearAnimation()
            boolean r2 = r13.O()
            if (r2 != 0) goto L_0x019c
            int r2 = r12.n
            r6 = 0
            if (r2 <= r5) goto L_0x0184
            boolean r2 = r12.x
            if (r2 != 0) goto L_0x0184
            android.view.View r2 = r13.K
            int r2 = r2.getVisibility()
            if (r2 != 0) goto L_0x0184
            float r2 = r13.Q
            int r2 = (r2 > r6 ? 1 : (r2 == r6 ? 0 : -1))
            if (r2 < 0) goto L_0x0184
            androidx.fragment.app.g<?> r2 = r12.o
            android.content.Context r2 = r2.e()
            androidx.fragment.app.d r3 = r12.p
            androidx.fragment.app.c$d r3 = androidx.fragment.app.c.a(r2, r3, r13, r8)
        L_0x0184:
            r13.Q = r6
            android.view.ViewGroup r2 = r13.J
            android.view.View r5 = r13.K
            if (r3 == 0) goto L_0x0194
            r13.d((int) r14)
            androidx.fragment.app.q$g r6 = r12.l
            androidx.fragment.app.c.a(r13, r3, r6)
        L_0x0194:
            r2.removeView(r5)
            android.view.ViewGroup r3 = r13.J
            if (r2 == r3) goto L_0x019c
            return
        L_0x019c:
            java.util.concurrent.ConcurrentHashMap<androidx.fragment.app.Fragment, java.util.HashSet<androidx.core.d.a>> r2 = r12.k
            java.lang.Object r2 = r2.get(r13)
            if (r2 != 0) goto L_0x01a8
            r12.s(r13)
            goto L_0x01ab
        L_0x01a8:
            r13.d((int) r14)
        L_0x01ab:
            if (r14 >= r1) goto L_0x01ec
            boolean r2 = r13.p
            if (r2 == 0) goto L_0x01b8
            boolean r2 = r13.L()
            if (r2 != 0) goto L_0x01b8
            r8 = 1
        L_0x01b8:
            if (r8 != 0) goto L_0x01d6
            androidx.fragment.app.l r2 = r12.D
            boolean r2 = r2.f(r13)
            if (r2 == 0) goto L_0x01c3
            goto L_0x01d6
        L_0x01c3:
            java.lang.String r2 = r13.l
            if (r2 == 0) goto L_0x01d9
            androidx.fragment.app.Fragment r2 = r12.a((java.lang.String) r2)
            if (r2 == 0) goto L_0x01d9
            boolean r3 = r2.z()
            if (r3 == 0) goto L_0x01d9
            r13.k = r2
            goto L_0x01d9
        L_0x01d6:
            r12.a((androidx.fragment.app.n) r0)
        L_0x01d9:
            java.util.concurrent.ConcurrentHashMap<androidx.fragment.app.Fragment, java.util.HashSet<androidx.core.d.a>> r2 = r12.k
            java.lang.Object r2 = r2.get(r13)
            if (r2 == 0) goto L_0x01e5
            r13.d((int) r14)
            goto L_0x01ed
        L_0x01e5:
            androidx.fragment.app.g<?> r1 = r12.o
            androidx.fragment.app.l r2 = r12.D
            r0.a(r1, r2)
        L_0x01ec:
            r1 = r14
        L_0x01ed:
            if (r1 >= 0) goto L_0x01f4
            androidx.fragment.app.l r14 = r12.D
            r0.a((androidx.fragment.app.l) r14)
        L_0x01f4:
            r14 = r1
        L_0x01f5:
            int r0 = r13.e
            if (r0 == r14) goto L_0x0227
            boolean r0 = d((int) r7)
            if (r0 == 0) goto L_0x0225
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r1 = "moveToState: Fragment state for "
            r0.append(r1)
            r0.append(r13)
            java.lang.String r1 = " not updated inline; expected state "
            r0.append(r1)
            r0.append(r14)
            java.lang.String r1 = " found "
            r0.append(r1)
            int r1 = r13.e
            r0.append(r1)
            java.lang.String r0 = r0.toString()
            android.util.Log.d(r4, r0)
        L_0x0225:
            r13.e = r14
        L_0x0227:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.fragment.app.j.a(androidx.fragment.app.Fragment, int):void");
    }

    /* access modifiers changed from: package-private */
    public void a(Fragment fragment, boolean z2) {
        ViewGroup v2 = v(fragment);
        if (v2 != null && (v2 instanceof FragmentContainerView)) {
            ((FragmentContainerView) v2).setDrawDisappearingViewsLast(!z2);
        }
    }

    /* access modifiers changed from: package-private */
    public void a(int i2, boolean z2) {
        g<?> gVar;
        if (this.o == null && i2 != -1) {
            throw new IllegalStateException("No activity");
        } else if (z2 || i2 != this.n) {
            this.n = i2;
            for (Fragment i3 : this.c.c()) {
                i(i3);
            }
            for (Fragment next : this.c.b()) {
                if (next != null && !next.O) {
                    i(next);
                }
            }
            J();
            if (this.u && (gVar = this.o) != null && this.n == 4) {
                gVar.i();
                this.u = false;
            }
        }
    }

    private void a(n nVar) {
        Fragment e2 = nVar.e();
        if (this.c.a(e2.f588i)) {
            if (d(2)) {
                Log.v("FragmentManager", "Removed fragment from active set " + e2);
            }
            this.c.b(nVar);
            m(e2);
        }
    }

    /* access modifiers changed from: package-private */
    public void a(Fragment fragment) {
        if (d(2)) {
            Log.v("FragmentManager", "add: " + fragment);
        }
        h(fragment);
        if (!fragment.D) {
            this.c.a(fragment);
            fragment.p = false;
            if (fragment.K == null) {
                fragment.P = false;
            }
            if (w(fragment)) {
                this.u = true;
            }
        }
    }

    public Fragment a(int i2) {
        return this.c.b(i2);
    }

    /* access modifiers changed from: package-private */
    public Fragment a(String str) {
        return this.c.b(str);
    }

    /* access modifiers changed from: package-private */
    public void a(h hVar, boolean z2) {
        if (!z2) {
            if (this.o != null) {
                D();
            } else if (this.x) {
                throw new IllegalStateException("FragmentManager has been destroyed");
            } else {
                throw new IllegalStateException("FragmentManager has not been attached to a host.");
            }
        }
        synchronized (this.a) {
            if (this.o != null) {
                this.a.add(hVar);
                C();
            } else if (!z2) {
                throw new IllegalStateException("Activity has been destroyed");
            }
        }
    }

    /* access modifiers changed from: package-private */
    public int a() {
        return this.f622i.getAndIncrement();
    }

    private void a(ArrayList<a> arrayList, ArrayList<Boolean> arrayList2) {
        int indexOf;
        int indexOf2;
        ArrayList<C0034j> arrayList3 = this.C;
        int size = arrayList3 == null ? 0 : arrayList3.size();
        int i2 = 0;
        while (i2 < size) {
            C0034j jVar = this.C.get(i2);
            if (arrayList != null && !jVar.a && (indexOf2 = arrayList.indexOf(jVar.b)) != -1 && arrayList2 != null && arrayList2.get(indexOf2).booleanValue()) {
                this.C.remove(i2);
                i2--;
                size--;
                jVar.c();
            } else if (jVar.e() || (arrayList != null && jVar.b.a(arrayList, 0, arrayList.size()))) {
                this.C.remove(i2);
                i2--;
                size--;
                if (arrayList == null || jVar.a || (indexOf = arrayList.indexOf(jVar.b)) == -1 || arrayList2 == null || !arrayList2.get(indexOf).booleanValue()) {
                    jVar.d();
                } else {
                    jVar.c();
                }
            }
            i2++;
        }
    }

    private int a(ArrayList<a> arrayList, ArrayList<Boolean> arrayList2, int i2, int i3, g.a.b<Fragment> bVar) {
        int i4 = i3;
        for (int i5 = i3 - 1; i5 >= i2; i5--) {
            a aVar = arrayList.get(i5);
            boolean booleanValue = arrayList2.get(i5).booleanValue();
            if (aVar.i() && !aVar.a(arrayList, i5 + 1, i3)) {
                if (this.C == null) {
                    this.C = new ArrayList<>();
                }
                C0034j jVar = new C0034j(aVar, booleanValue);
                this.C.add(jVar);
                aVar.setOnStartPostponedListener(jVar);
                if (booleanValue) {
                    aVar.g();
                } else {
                    aVar.c(false);
                }
                i4--;
                if (i5 != i4) {
                    arrayList.remove(i5);
                    arrayList.add(i4, aVar);
                }
                a(bVar);
            }
        }
        return i4;
    }

    /* access modifiers changed from: package-private */
    public void a(a aVar, boolean z2, boolean z3, boolean z4) {
        if (z2) {
            aVar.c(z4);
        } else {
            aVar.g();
        }
        ArrayList arrayList = new ArrayList(1);
        ArrayList arrayList2 = new ArrayList(1);
        arrayList.add(aVar);
        arrayList2.add(Boolean.valueOf(z2));
        if (z3) {
            q.a(this, arrayList, arrayList2, 0, 1, true, this.l);
        }
        if (z4) {
            a(this.n, true);
        }
        for (Fragment next : this.c.b()) {
            if (next != null && next.K != null && next.O && aVar.b(next.A)) {
                float f2 = next.Q;
                if (f2 > 0.0f) {
                    next.K.setAlpha(f2);
                }
                if (z4) {
                    next.Q = 0.0f;
                } else {
                    next.Q = -1.0f;
                    next.O = false;
                }
            }
        }
    }

    private static void a(ArrayList<a> arrayList, ArrayList<Boolean> arrayList2, int i2, int i3) {
        while (i2 < i3) {
            a aVar = arrayList.get(i2);
            boolean z2 = true;
            if (arrayList2.get(i2).booleanValue()) {
                aVar.a(-1);
                if (i2 != i3 - 1) {
                    z2 = false;
                }
                aVar.c(z2);
            } else {
                aVar.a(1);
                aVar.g();
            }
            i2++;
        }
    }

    private void a(g.a.b<Fragment> bVar) {
        int i2 = this.n;
        if (i2 >= 1) {
            int min = Math.min(i2, 3);
            for (Fragment next : this.c.c()) {
                if (next.e < min) {
                    a(next, min);
                    if (next.K != null && !next.C && next.O) {
                        bVar.add(next);
                    }
                }
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void a(a aVar) {
        if (this.d == null) {
            this.d = new ArrayList<>();
        }
        this.d.add(aVar);
    }

    /* access modifiers changed from: package-private */
    public boolean a(ArrayList<a> arrayList, ArrayList<Boolean> arrayList2, String str, int i2, int i3) {
        int i4;
        ArrayList<a> arrayList3 = this.d;
        if (arrayList3 == null) {
            return false;
        }
        if (str == null && i2 < 0 && (i3 & 1) == 0) {
            int size = arrayList3.size() - 1;
            if (size < 0) {
                return false;
            }
            arrayList.add(this.d.remove(size));
            arrayList2.add(true);
        } else {
            if (str != null || i2 >= 0) {
                int size2 = this.d.size() - 1;
                while (size2 >= 0) {
                    a aVar = this.d.get(size2);
                    if ((str != null && str.equals(aVar.h())) || (i2 >= 0 && i2 == aVar.t)) {
                        break;
                    }
                    size2--;
                }
                if (size2 < 0) {
                    return false;
                }
                if ((i3 & 1) != 0) {
                    while (true) {
                        size2--;
                        if (size2 < 0) {
                            break;
                        }
                        a aVar2 = this.d.get(size2);
                        if ((str == null || !str.equals(aVar2.h())) && (i2 < 0 || i2 != aVar2.t)) {
                            break;
                        }
                    }
                }
                i4 = size2;
            } else {
                i4 = -1;
            }
            if (i4 == this.d.size() - 1) {
                return false;
            }
            for (int size3 = this.d.size() - 1; size3 > i4; size3--) {
                arrayList.add(this.d.remove(size3));
                arrayList2.add(true);
            }
        }
        return true;
    }

    /* access modifiers changed from: package-private */
    public void a(Parcelable parcelable) {
        n nVar;
        if (parcelable != null) {
            FragmentManagerState fragmentManagerState = (FragmentManagerState) parcelable;
            if (fragmentManagerState.e != null) {
                this.c.d();
                Iterator<FragmentState> it = fragmentManagerState.e.iterator();
                while (it.hasNext()) {
                    FragmentState next = it.next();
                    if (next != null) {
                        Fragment a2 = this.D.a(next.f602f);
                        if (a2 != null) {
                            if (d(2)) {
                                Log.v("FragmentManager", "restoreSaveState: re-attaching retained " + a2);
                            }
                            nVar = new n(this.m, a2, next);
                        } else {
                            nVar = new n(this.m, this.o.e().getClassLoader(), p(), next);
                        }
                        Fragment e2 = nVar.e();
                        e2.v = this;
                        if (d(2)) {
                            Log.v("FragmentManager", "restoreSaveState: active (" + e2.f588i + "): " + e2);
                        }
                        nVar.a(this.o.e().getClassLoader());
                        this.c.a(nVar);
                        nVar.a(this.n);
                    }
                }
                for (Fragment next2 : this.D.b()) {
                    if (!this.c.a(next2.f588i)) {
                        if (d(2)) {
                            Log.v("FragmentManager", "Discarding retained Fragment " + next2 + " that was not found in the set of active Fragments " + fragmentManagerState.e);
                        }
                        a(next2, 1);
                        next2.p = true;
                        a(next2, -1);
                    }
                }
                this.c.a((List<String>) fragmentManagerState.f598f);
                if (fragmentManagerState.f599g != null) {
                    this.d = new ArrayList<>(fragmentManagerState.f599g.length);
                    int i2 = 0;
                    while (true) {
                        BackStackState[] backStackStateArr = fragmentManagerState.f599g;
                        if (i2 >= backStackStateArr.length) {
                            break;
                        }
                        a a3 = backStackStateArr[i2].a(this);
                        if (d(2)) {
                            Log.v("FragmentManager", "restoreAllState: back stack #" + i2 + " (index " + a3.t + "): " + a3);
                            PrintWriter printWriter = new PrintWriter(new androidx.core.g.b("FragmentManager"));
                            a3.a("  ", printWriter, false);
                            printWriter.close();
                        }
                        this.d.add(a3);
                        i2++;
                    }
                } else {
                    this.d = null;
                }
                this.f622i.set(fragmentManagerState.f600h);
                String str = fragmentManagerState.f601i;
                if (str != null) {
                    Fragment a4 = a(str);
                    this.r = a4;
                    t(a4);
                }
            }
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v6, resolved type: androidx.activity.c} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v7, resolved type: androidx.fragment.app.Fragment} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v8, resolved type: androidx.fragment.app.Fragment} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v9, resolved type: androidx.fragment.app.Fragment} */
    /* access modifiers changed from: package-private */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void a(androidx.fragment.app.g<?> r3, androidx.fragment.app.d r4, androidx.fragment.app.Fragment r5) {
        /*
            r2 = this;
            androidx.fragment.app.g<?> r0 = r2.o
            if (r0 != 0) goto L_0x004b
            r2.o = r3
            r2.p = r4
            r2.q = r5
            if (r5 == 0) goto L_0x000f
            r2.K()
        L_0x000f:
            boolean r4 = r3 instanceof androidx.activity.c
            if (r4 == 0) goto L_0x0026
            r4 = r3
            androidx.activity.c r4 = (androidx.activity.c) r4
            androidx.activity.OnBackPressedDispatcher r0 = r4.a()
            r2.f620g = r0
            if (r5 == 0) goto L_0x001f
            r4 = r5
        L_0x001f:
            androidx.activity.OnBackPressedDispatcher r0 = r2.f620g
            androidx.activity.b r1 = r2.f621h
            r0.a(r4, r1)
        L_0x0026:
            if (r5 == 0) goto L_0x0031
            androidx.fragment.app.j r3 = r5.v
            androidx.fragment.app.l r3 = r3.u(r5)
            r2.D = r3
            goto L_0x004a
        L_0x0031:
            boolean r4 = r3 instanceof androidx.lifecycle.ViewModelStoreOwner
            if (r4 == 0) goto L_0x0042
            androidx.lifecycle.ViewModelStoreOwner r3 = (androidx.lifecycle.ViewModelStoreOwner) r3
            androidx.lifecycle.ViewModelStore r3 = r3.getViewModelStore()
            androidx.fragment.app.l r3 = androidx.fragment.app.l.a((androidx.lifecycle.ViewModelStore) r3)
            r2.D = r3
            goto L_0x004a
        L_0x0042:
            androidx.fragment.app.l r3 = new androidx.fragment.app.l
            r4 = 0
            r3.<init>(r4)
            r2.D = r3
        L_0x004a:
            return
        L_0x004b:
            java.lang.IllegalStateException r3 = new java.lang.IllegalStateException
            java.lang.String r4 = "Already attached"
            r3.<init>(r4)
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.fragment.app.j.a(androidx.fragment.app.g, androidx.fragment.app.d, androidx.fragment.app.Fragment):void");
    }

    /* access modifiers changed from: package-private */
    public void a(boolean z2) {
        for (Fragment next : this.c.c()) {
            if (next != null) {
                next.e(z2);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void a(Configuration configuration) {
        for (Fragment next : this.c.c()) {
            if (next != null) {
                next.a(configuration);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public boolean a(Menu menu, MenuInflater menuInflater) {
        if (this.n < 1) {
            return false;
        }
        ArrayList<Fragment> arrayList = null;
        boolean z2 = false;
        for (Fragment next : this.c.c()) {
            if (next != null && next.b(menu, menuInflater)) {
                if (arrayList == null) {
                    arrayList = new ArrayList<>();
                }
                arrayList.add(next);
                z2 = true;
            }
        }
        if (this.e != null) {
            for (int i2 = 0; i2 < this.e.size(); i2++) {
                Fragment fragment = this.e.get(i2);
                if (arrayList == null || !arrayList.contains(fragment)) {
                    fragment.S();
                }
            }
        }
        this.e = arrayList;
        return z2;
    }

    /* access modifiers changed from: package-private */
    public boolean a(MenuItem menuItem) {
        if (this.n < 1) {
            return false;
        }
        for (Fragment next : this.c.c()) {
            if (next != null && next.c(menuItem)) {
                return true;
            }
        }
        return false;
    }

    /* access modifiers changed from: package-private */
    public void a(Menu menu) {
        if (this.n >= 1) {
            for (Fragment next : this.c.c()) {
                if (next != null) {
                    next.c(menu);
                }
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void a(Fragment fragment, Lifecycle.State state) {
        if (!fragment.equals(a(fragment.f588i)) || !(fragment.w == null || fragment.v == this)) {
            throw new IllegalArgumentException("Fragment " + fragment + " is not an active fragment of FragmentManager " + this);
        }
        fragment.T = state;
    }

    public void a(f fVar, boolean z2) {
        this.m.a(fVar, z2);
    }

    public void a(f fVar) {
        this.m.a(fVar);
    }
}
