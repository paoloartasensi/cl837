package androidx.fragment.app;

import android.app.Activity;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.R$id;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.ViewModelStoreOwner;

/* compiled from: FragmentStateManager */
class n {
    private final i a;
    private final Fragment b;
    private int c = -1;

    /* compiled from: FragmentStateManager */
    static /* synthetic */ class a {
        static final /* synthetic */ int[] a;

        /* JADX WARNING: Can't wrap try/catch for region: R(6:0|1|2|3|4|(3:5|6|8)) */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0012 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x001d */
        static {
            /*
                androidx.lifecycle.Lifecycle$State[] r0 = androidx.lifecycle.Lifecycle.State.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                a = r0
                androidx.lifecycle.Lifecycle$State r1 = androidx.lifecycle.Lifecycle.State.RESUMED     // Catch:{ NoSuchFieldError -> 0x0012 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0012 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0012 }
            L_0x0012:
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x001d }
                androidx.lifecycle.Lifecycle$State r1 = androidx.lifecycle.Lifecycle.State.STARTED     // Catch:{ NoSuchFieldError -> 0x001d }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001d }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001d }
            L_0x001d:
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x0028 }
                androidx.lifecycle.Lifecycle$State r1 = androidx.lifecycle.Lifecycle.State.CREATED     // Catch:{ NoSuchFieldError -> 0x0028 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0028 }
                r2 = 3
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0028 }
            L_0x0028:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.fragment.app.n.a.<clinit>():void");
        }
    }

    n(i iVar, Fragment fragment) {
        this.a = iVar;
        this.b = fragment;
    }

    private Bundle n() {
        Bundle bundle = new Bundle();
        this.b.j(bundle);
        this.a.d(this.b, bundle, false);
        if (bundle.isEmpty()) {
            bundle = null;
        }
        if (this.b.K != null) {
            k();
        }
        if (this.b.f586g != null) {
            if (bundle == null) {
                bundle = new Bundle();
            }
            bundle.putSparseParcelableArray("android:view_state", this.b.f586g);
        }
        if (!this.b.M) {
            if (bundle == null) {
                bundle = new Bundle();
            }
            bundle.putBoolean("android:user_visible_hint", this.b.M);
        }
        return bundle;
    }

    /* access modifiers changed from: package-private */
    public void a(int i2) {
        this.c = i2;
    }

    /* access modifiers changed from: package-private */
    public int b() {
        int i2 = this.c;
        Fragment fragment = this.b;
        if (fragment.q) {
            if (fragment.r) {
                i2 = Math.max(i2, 1);
            } else if (i2 < 2) {
                i2 = Math.min(i2, fragment.e);
            } else {
                i2 = Math.min(i2, 1);
            }
        }
        if (!this.b.o) {
            i2 = Math.min(i2, 1);
        }
        Fragment fragment2 = this.b;
        if (fragment2.p) {
            if (fragment2.L()) {
                i2 = Math.min(i2, 1);
            } else {
                i2 = Math.min(i2, -1);
            }
        }
        Fragment fragment3 = this.b;
        if (fragment3.L && fragment3.e < 3) {
            i2 = Math.min(i2, 2);
        }
        int i3 = a.a[this.b.T.ordinal()];
        if (i3 == 1) {
            return i2;
        }
        if (i3 == 2) {
            return Math.min(i2, 3);
        }
        if (i3 != 3) {
            return Math.min(i2, -1);
        }
        return Math.min(i2, 1);
    }

    /* access modifiers changed from: package-private */
    public void c() {
        if (j.d(3)) {
            Log.d("FragmentManager", "moveto CREATED: " + this.b);
        }
        Fragment fragment = this.b;
        if (!fragment.S) {
            this.a.c(fragment, fragment.f585f, false);
            Fragment fragment2 = this.b;
            fragment2.h(fragment2.f585f);
            i iVar = this.a;
            Fragment fragment3 = this.b;
            iVar.b(fragment3, fragment3.f585f, false);
            return;
        }
        fragment.k(fragment.f585f);
        this.b.e = 1;
    }

    /* access modifiers changed from: package-private */
    public void d() {
        Fragment fragment = this.b;
        if (fragment.q && fragment.r && !fragment.t) {
            if (j.d(3)) {
                Log.d("FragmentManager", "moveto CREATE_VIEW: " + this.b);
            }
            Fragment fragment2 = this.b;
            fragment2.b(fragment2.i(fragment2.f585f), (ViewGroup) null, this.b.f585f);
            View view = this.b.K;
            if (view != null) {
                view.setSaveFromParentEnabled(false);
                Fragment fragment3 = this.b;
                fragment3.K.setTag(R$id.fragment_container_view_tag, fragment3);
                Fragment fragment4 = this.b;
                if (fragment4.C) {
                    fragment4.K.setVisibility(8);
                }
                Fragment fragment5 = this.b;
                fragment5.a(fragment5.K, fragment5.f585f);
                i iVar = this.a;
                Fragment fragment6 = this.b;
                iVar.a(fragment6, fragment6.K, fragment6.f585f, false);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public Fragment e() {
        return this.b;
    }

    /* access modifiers changed from: package-private */
    public void f() {
        if (j.d(3)) {
            Log.d("FragmentManager", "movefrom RESUMED: " + this.b);
        }
        this.b.e0();
        this.a.c(this.b, false);
    }

    /* access modifiers changed from: package-private */
    public void g() {
        if (j.d(3)) {
            Log.d("FragmentManager", "moveto RESTORE_VIEW_STATE: " + this.b);
        }
        Fragment fragment = this.b;
        if (fragment.K != null) {
            fragment.l(fragment.f585f);
        }
        this.b.f585f = null;
    }

    /* access modifiers changed from: package-private */
    public void h() {
        if (j.d(3)) {
            Log.d("FragmentManager", "moveto RESUMED: " + this.b);
        }
        this.b.g0();
        this.a.d(this.b, false);
        Fragment fragment = this.b;
        fragment.f585f = null;
        fragment.f586g = null;
    }

    /* access modifiers changed from: package-private */
    public Fragment.SavedState i() {
        Bundle n;
        if (this.b.e <= -1 || (n = n()) == null) {
            return null;
        }
        return new Fragment.SavedState(n);
    }

    /* access modifiers changed from: package-private */
    public FragmentState j() {
        FragmentState fragmentState = new FragmentState(this.b);
        if (this.b.e <= -1 || fragmentState.q != null) {
            fragmentState.q = this.b.f585f;
        } else {
            Bundle n = n();
            fragmentState.q = n;
            if (this.b.l != null) {
                if (n == null) {
                    fragmentState.q = new Bundle();
                }
                fragmentState.q.putString("android:target_state", this.b.l);
                int i2 = this.b.m;
                if (i2 != 0) {
                    fragmentState.q.putInt("android:target_req_state", i2);
                }
            }
        }
        return fragmentState;
    }

    /* access modifiers changed from: package-private */
    public void k() {
        if (this.b.K != null) {
            SparseArray<Parcelable> sparseArray = new SparseArray<>();
            this.b.K.saveHierarchyState(sparseArray);
            if (sparseArray.size() > 0) {
                this.b.f586g = sparseArray;
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void l() {
        if (j.d(3)) {
            Log.d("FragmentManager", "moveto STARTED: " + this.b);
        }
        this.b.h0();
        this.a.e(this.b, false);
    }

    /* access modifiers changed from: package-private */
    public void m() {
        if (j.d(3)) {
            Log.d("FragmentManager", "movefrom STARTED: " + this.b);
        }
        this.b.i0();
        this.a.f(this.b, false);
    }

    /* access modifiers changed from: package-private */
    public void a(ClassLoader classLoader) {
        Bundle bundle = this.b.f585f;
        if (bundle != null) {
            bundle.setClassLoader(classLoader);
            Fragment fragment = this.b;
            fragment.f586g = fragment.f585f.getSparseParcelableArray("android:view_state");
            Fragment fragment2 = this.b;
            fragment2.l = fragment2.f585f.getString("android:target_state");
            Fragment fragment3 = this.b;
            if (fragment3.l != null) {
                fragment3.m = fragment3.f585f.getInt("android:target_req_state", 0);
            }
            Fragment fragment4 = this.b;
            Boolean bool = fragment4.f587h;
            if (bool != null) {
                fragment4.M = bool.booleanValue();
                this.b.f587h = null;
            } else {
                fragment4.M = fragment4.f585f.getBoolean("android:user_visible_hint", true);
            }
            Fragment fragment5 = this.b;
            if (!fragment5.M) {
                fragment5.L = true;
            }
        }
    }

    n(i iVar, ClassLoader classLoader, f fVar, FragmentState fragmentState) {
        this.a = iVar;
        this.b = fVar.a(classLoader, fragmentState.e);
        Bundle bundle = fragmentState.n;
        if (bundle != null) {
            bundle.setClassLoader(classLoader);
        }
        this.b.m(fragmentState.n);
        Fragment fragment = this.b;
        fragment.f588i = fragmentState.f602f;
        fragment.q = fragmentState.f603g;
        fragment.s = true;
        fragment.z = fragmentState.f604h;
        fragment.A = fragmentState.f605i;
        fragment.B = fragmentState.f606j;
        fragment.E = fragmentState.k;
        fragment.p = fragmentState.l;
        fragment.D = fragmentState.m;
        fragment.C = fragmentState.o;
        fragment.T = Lifecycle.State.values()[fragmentState.p];
        Bundle bundle2 = fragmentState.q;
        if (bundle2 != null) {
            this.b.f585f = bundle2;
        } else {
            this.b.f585f = new Bundle();
        }
        if (j.d(2)) {
            Log.v("FragmentManager", "Instantiated fragment " + this.b);
        }
    }

    /* access modifiers changed from: package-private */
    public void a(g<?> gVar, j jVar, Fragment fragment) {
        Fragment fragment2 = this.b;
        fragment2.w = gVar;
        fragment2.y = fragment;
        fragment2.v = jVar;
        this.a.b(fragment2, gVar.e(), false);
        this.b.Z();
        Fragment fragment3 = this.b;
        Fragment fragment4 = fragment3.y;
        if (fragment4 == null) {
            gVar.a(fragment3);
        } else {
            fragment4.a(fragment3);
        }
        this.a.a(this.b, gVar.e(), false);
    }

    /* JADX WARNING: type inference failed for: r5v15, types: [android.view.View] */
    /* access modifiers changed from: package-private */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void a(androidx.fragment.app.d r5) {
        /*
            r4 = this;
            androidx.fragment.app.Fragment r0 = r4.b
            boolean r0 = r0.q
            if (r0 == 0) goto L_0x0007
            return
        L_0x0007:
            r0 = 3
            boolean r0 = androidx.fragment.app.j.d((int) r0)
            if (r0 == 0) goto L_0x0026
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r1 = "moveto CREATE_VIEW: "
            r0.append(r1)
            androidx.fragment.app.Fragment r1 = r4.b
            r0.append(r1)
            java.lang.String r0 = r0.toString()
            java.lang.String r1 = "FragmentManager"
            android.util.Log.d(r1, r0)
        L_0x0026:
            r0 = 0
            androidx.fragment.app.Fragment r1 = r4.b
            android.view.ViewGroup r2 = r1.J
            if (r2 == 0) goto L_0x0030
            r0 = r2
            goto L_0x00a5
        L_0x0030:
            int r1 = r1.A
            if (r1 == 0) goto L_0x00a5
            r0 = -1
            if (r1 == r0) goto L_0x0087
            android.view.View r5 = r5.a(r1)
            r0 = r5
            android.view.ViewGroup r0 = (android.view.ViewGroup) r0
            if (r0 != 0) goto L_0x00a5
            androidx.fragment.app.Fragment r5 = r4.b
            boolean r1 = r5.s
            if (r1 == 0) goto L_0x0047
            goto L_0x00a5
        L_0x0047:
            android.content.res.Resources r5 = r5.y()     // Catch:{ NotFoundException -> 0x0054 }
            androidx.fragment.app.Fragment r0 = r4.b     // Catch:{ NotFoundException -> 0x0054 }
            int r0 = r0.A     // Catch:{ NotFoundException -> 0x0054 }
            java.lang.String r5 = r5.getResourceName(r0)     // Catch:{ NotFoundException -> 0x0054 }
            goto L_0x0056
        L_0x0054:
            java.lang.String r5 = "unknown"
        L_0x0056:
            java.lang.IllegalArgumentException r0 = new java.lang.IllegalArgumentException
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "No view found for id 0x"
            r1.append(r2)
            androidx.fragment.app.Fragment r2 = r4.b
            int r2 = r2.A
            java.lang.String r2 = java.lang.Integer.toHexString(r2)
            r1.append(r2)
            java.lang.String r2 = " ("
            r1.append(r2)
            r1.append(r5)
            java.lang.String r5 = ") for fragment "
            r1.append(r5)
            androidx.fragment.app.Fragment r5 = r4.b
            r1.append(r5)
            java.lang.String r5 = r1.toString()
            r0.<init>(r5)
            throw r0
        L_0x0087:
            java.lang.IllegalArgumentException r5 = new java.lang.IllegalArgumentException
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r1 = "Cannot create fragment "
            r0.append(r1)
            androidx.fragment.app.Fragment r1 = r4.b
            r0.append(r1)
            java.lang.String r1 = " for a container view with no id"
            r0.append(r1)
            java.lang.String r0 = r0.toString()
            r5.<init>(r0)
            throw r5
        L_0x00a5:
            androidx.fragment.app.Fragment r5 = r4.b
            r5.J = r0
            android.os.Bundle r1 = r5.f585f
            android.view.LayoutInflater r1 = r5.i((android.os.Bundle) r1)
            androidx.fragment.app.Fragment r2 = r4.b
            android.os.Bundle r2 = r2.f585f
            r5.b((android.view.LayoutInflater) r1, (android.view.ViewGroup) r0, (android.os.Bundle) r2)
            androidx.fragment.app.Fragment r5 = r4.b
            android.view.View r5 = r5.K
            if (r5 == 0) goto L_0x010d
            r1 = 0
            r5.setSaveFromParentEnabled(r1)
            androidx.fragment.app.Fragment r5 = r4.b
            android.view.View r2 = r5.K
            int r3 = androidx.fragment.R$id.fragment_container_view_tag
            r2.setTag(r3, r5)
            if (r0 == 0) goto L_0x00d2
            androidx.fragment.app.Fragment r5 = r4.b
            android.view.View r5 = r5.K
            r0.addView(r5)
        L_0x00d2:
            androidx.fragment.app.Fragment r5 = r4.b
            boolean r0 = r5.C
            if (r0 == 0) goto L_0x00df
            android.view.View r5 = r5.K
            r0 = 8
            r5.setVisibility(r0)
        L_0x00df:
            androidx.fragment.app.Fragment r5 = r4.b
            android.view.View r5 = r5.K
            androidx.core.h.v.I(r5)
            androidx.fragment.app.Fragment r5 = r4.b
            android.view.View r0 = r5.K
            android.os.Bundle r2 = r5.f585f
            r5.a((android.view.View) r0, (android.os.Bundle) r2)
            androidx.fragment.app.i r5 = r4.a
            androidx.fragment.app.Fragment r0 = r4.b
            android.view.View r2 = r0.K
            android.os.Bundle r3 = r0.f585f
            r5.a(r0, r2, r3, r1)
            androidx.fragment.app.Fragment r5 = r4.b
            android.view.View r0 = r5.K
            int r0 = r0.getVisibility()
            if (r0 != 0) goto L_0x010b
            androidx.fragment.app.Fragment r0 = r4.b
            android.view.ViewGroup r0 = r0.J
            if (r0 == 0) goto L_0x010b
            r1 = 1
        L_0x010b:
            r5.O = r1
        L_0x010d:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.fragment.app.n.a(androidx.fragment.app.d):void");
    }

    n(i iVar, Fragment fragment, FragmentState fragmentState) {
        this.a = iVar;
        this.b = fragment;
        fragment.f586g = null;
        fragment.u = 0;
        fragment.r = false;
        fragment.o = false;
        Fragment fragment2 = fragment.k;
        fragment.l = fragment2 != null ? fragment2.f588i : null;
        Fragment fragment3 = this.b;
        fragment3.k = null;
        Bundle bundle = fragmentState.q;
        if (bundle != null) {
            fragment3.f585f = bundle;
        } else {
            fragment3.f585f = new Bundle();
        }
    }

    /* access modifiers changed from: package-private */
    public void a() {
        if (j.d(3)) {
            Log.d("FragmentManager", "moveto ACTIVITY_CREATED: " + this.b);
        }
        Fragment fragment = this.b;
        fragment.g(fragment.f585f);
        i iVar = this.a;
        Fragment fragment2 = this.b;
        iVar.a(fragment2, fragment2.f585f, false);
    }

    /* access modifiers changed from: package-private */
    public void a(g<?> gVar, l lVar) {
        if (j.d(3)) {
            Log.d("FragmentManager", "movefrom CREATED: " + this.b);
        }
        Fragment fragment = this.b;
        boolean z = true;
        boolean z2 = fragment.p && !fragment.L();
        if (z2 || lVar.f(this.b)) {
            if (gVar instanceof ViewModelStoreOwner) {
                z = lVar.c();
            } else if (gVar.e() instanceof Activity) {
                z = true ^ ((Activity) gVar.e()).isChangingConfigurations();
            }
            if (z2 || z) {
                lVar.b(this.b);
            }
            this.b.a0();
            this.a.a(this.b, false);
            return;
        }
        this.b.e = 0;
    }

    /* access modifiers changed from: package-private */
    public void a(l lVar) {
        if (j.d(3)) {
            Log.d("FragmentManager", "movefrom ATTACHED: " + this.b);
        }
        this.b.c0();
        boolean z = false;
        this.a.b(this.b, false);
        Fragment fragment = this.b;
        fragment.e = -1;
        fragment.w = null;
        fragment.y = null;
        fragment.v = null;
        if (fragment.p && !fragment.L()) {
            z = true;
        }
        if (z || lVar.f(this.b)) {
            if (j.d(3)) {
                Log.d("FragmentManager", "initState called for fragment: " + this.b);
            }
            this.b.G();
        }
    }
}
