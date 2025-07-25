package androidx.fragment.app;

import android.graphics.Rect;
import android.os.Build;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import androidx.core.app.l;
import androidx.core.h.s;
import androidx.core.h.v;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/* compiled from: FragmentTransition */
class q {
    private static final int[] a = {0, 3, 0, 1, 5, 4, 7, 6, 9, 8, 10};
    private static final s b = (Build.VERSION.SDK_INT >= 21 ? new r() : null);
    private static final s c = a();

    /* compiled from: FragmentTransition */
    static class a implements Runnable {
        final /* synthetic */ g e;

        /* renamed from: f  reason: collision with root package name */
        final /* synthetic */ Fragment f635f;

        /* renamed from: g  reason: collision with root package name */
        final /* synthetic */ androidx.core.d.a f636g;

        a(g gVar, Fragment fragment, androidx.core.d.a aVar) {
            this.e = gVar;
            this.f635f = fragment;
            this.f636g = aVar;
        }

        public void run() {
            this.e.a(this.f635f, this.f636g);
        }
    }

    /* compiled from: FragmentTransition */
    static class b implements Runnable {
        final /* synthetic */ ArrayList e;

        b(ArrayList arrayList) {
            this.e = arrayList;
        }

        public void run() {
            q.a((ArrayList<View>) this.e, 4);
        }
    }

    /* compiled from: FragmentTransition */
    static class c implements Runnable {
        final /* synthetic */ g e;

        /* renamed from: f  reason: collision with root package name */
        final /* synthetic */ Fragment f637f;

        /* renamed from: g  reason: collision with root package name */
        final /* synthetic */ androidx.core.d.a f638g;

        c(g gVar, Fragment fragment, androidx.core.d.a aVar) {
            this.e = gVar;
            this.f637f = fragment;
            this.f638g = aVar;
        }

        public void run() {
            this.e.a(this.f637f, this.f638g);
        }
    }

    /* compiled from: FragmentTransition */
    static class d implements Runnable {
        final /* synthetic */ Object e;

        /* renamed from: f  reason: collision with root package name */
        final /* synthetic */ s f639f;

        /* renamed from: g  reason: collision with root package name */
        final /* synthetic */ View f640g;

        /* renamed from: h  reason: collision with root package name */
        final /* synthetic */ Fragment f641h;

        /* renamed from: i  reason: collision with root package name */
        final /* synthetic */ ArrayList f642i;

        /* renamed from: j  reason: collision with root package name */
        final /* synthetic */ ArrayList f643j;
        final /* synthetic */ ArrayList k;
        final /* synthetic */ Object l;

        d(Object obj, s sVar, View view, Fragment fragment, ArrayList arrayList, ArrayList arrayList2, ArrayList arrayList3, Object obj2) {
            this.e = obj;
            this.f639f = sVar;
            this.f640g = view;
            this.f641h = fragment;
            this.f642i = arrayList;
            this.f643j = arrayList2;
            this.k = arrayList3;
            this.l = obj2;
        }

        public void run() {
            Object obj = this.e;
            if (obj != null) {
                this.f639f.b(obj, this.f640g);
                this.f643j.addAll(q.a(this.f639f, this.e, this.f641h, (ArrayList<View>) this.f642i, this.f640g));
            }
            if (this.k != null) {
                if (this.l != null) {
                    ArrayList arrayList = new ArrayList();
                    arrayList.add(this.f640g);
                    this.f639f.a(this.l, (ArrayList<View>) this.k, (ArrayList<View>) arrayList);
                }
                this.k.clear();
                this.k.add(this.f640g);
            }
        }
    }

    /* compiled from: FragmentTransition */
    static class e implements Runnable {
        final /* synthetic */ Fragment e;

        /* renamed from: f  reason: collision with root package name */
        final /* synthetic */ Fragment f644f;

        /* renamed from: g  reason: collision with root package name */
        final /* synthetic */ boolean f645g;

        /* renamed from: h  reason: collision with root package name */
        final /* synthetic */ g.a.a f646h;

        /* renamed from: i  reason: collision with root package name */
        final /* synthetic */ View f647i;

        /* renamed from: j  reason: collision with root package name */
        final /* synthetic */ s f648j;
        final /* synthetic */ Rect k;

        e(Fragment fragment, Fragment fragment2, boolean z, g.a.a aVar, View view, s sVar, Rect rect) {
            this.e = fragment;
            this.f644f = fragment2;
            this.f645g = z;
            this.f646h = aVar;
            this.f647i = view;
            this.f648j = sVar;
            this.k = rect;
        }

        public void run() {
            q.a(this.e, this.f644f, this.f645g, (g.a.a<String, View>) this.f646h, false);
            View view = this.f647i;
            if (view != null) {
                this.f648j.a(view, this.k);
            }
        }
    }

    /* compiled from: FragmentTransition */
    static class f implements Runnable {
        final /* synthetic */ s e;

        /* renamed from: f  reason: collision with root package name */
        final /* synthetic */ g.a.a f649f;

        /* renamed from: g  reason: collision with root package name */
        final /* synthetic */ Object f650g;

        /* renamed from: h  reason: collision with root package name */
        final /* synthetic */ h f651h;

        /* renamed from: i  reason: collision with root package name */
        final /* synthetic */ ArrayList f652i;

        /* renamed from: j  reason: collision with root package name */
        final /* synthetic */ View f653j;
        final /* synthetic */ Fragment k;
        final /* synthetic */ Fragment l;
        final /* synthetic */ boolean m;
        final /* synthetic */ ArrayList n;
        final /* synthetic */ Object o;
        final /* synthetic */ Rect p;

        f(s sVar, g.a.a aVar, Object obj, h hVar, ArrayList arrayList, View view, Fragment fragment, Fragment fragment2, boolean z, ArrayList arrayList2, Object obj2, Rect rect) {
            this.e = sVar;
            this.f649f = aVar;
            this.f650g = obj;
            this.f651h = hVar;
            this.f652i = arrayList;
            this.f653j = view;
            this.k = fragment;
            this.l = fragment2;
            this.m = z;
            this.n = arrayList2;
            this.o = obj2;
            this.p = rect;
        }

        public void run() {
            g.a.a<String, View> a = q.a(this.e, (g.a.a<String, String>) this.f649f, this.f650g, this.f651h);
            if (a != null) {
                this.f652i.addAll(a.values());
                this.f652i.add(this.f653j);
            }
            q.a(this.k, this.l, this.m, a, false);
            Object obj = this.f650g;
            if (obj != null) {
                this.e.b(obj, (ArrayList<View>) this.n, (ArrayList<View>) this.f652i);
                View a2 = q.a(a, this.f651h, this.o, this.m);
                if (a2 != null) {
                    this.e.a(a2, this.p);
                }
            }
        }
    }

    /* compiled from: FragmentTransition */
    interface g {
        void a(Fragment fragment, androidx.core.d.a aVar);

        void b(Fragment fragment, androidx.core.d.a aVar);
    }

    /* compiled from: FragmentTransition */
    static class h {
        public Fragment a;
        public boolean b;
        public a c;
        public Fragment d;
        public boolean e;

        /* renamed from: f  reason: collision with root package name */
        public a f654f;

        h() {
        }
    }

    private static s a() {
        try {
            return (s) Class.forName("androidx.transition.e").getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
        } catch (Exception unused) {
            return null;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:7:0x0020, code lost:
        r12 = r4.a;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static void b(androidx.fragment.app.j r17, int r18, androidx.fragment.app.q.h r19, android.view.View r20, g.a.a<java.lang.String, java.lang.String> r21, androidx.fragment.app.q.g r22) {
        /*
            r0 = r17
            r4 = r19
            r9 = r20
            r10 = r22
            androidx.fragment.app.d r1 = r0.p
            boolean r1 = r1.c()
            if (r1 == 0) goto L_0x001b
            androidx.fragment.app.d r0 = r0.p
            r1 = r18
            android.view.View r0 = r0.a(r1)
            android.view.ViewGroup r0 = (android.view.ViewGroup) r0
            goto L_0x001c
        L_0x001b:
            r0 = 0
        L_0x001c:
            r11 = r0
            if (r11 != 0) goto L_0x0020
            return
        L_0x0020:
            androidx.fragment.app.Fragment r12 = r4.a
            androidx.fragment.app.Fragment r13 = r4.d
            androidx.fragment.app.s r14 = a((androidx.fragment.app.Fragment) r13, (androidx.fragment.app.Fragment) r12)
            if (r14 != 0) goto L_0x002b
            return
        L_0x002b:
            boolean r15 = r4.b
            boolean r0 = r4.e
            java.util.ArrayList r8 = new java.util.ArrayList
            r8.<init>()
            java.util.ArrayList r7 = new java.util.ArrayList
            r7.<init>()
            java.lang.Object r6 = a((androidx.fragment.app.s) r14, (androidx.fragment.app.Fragment) r12, (boolean) r15)
            java.lang.Object r5 = b((androidx.fragment.app.s) r14, (androidx.fragment.app.Fragment) r13, (boolean) r0)
            r0 = r14
            r1 = r11
            r2 = r20
            r3 = r21
            r4 = r19
            r17 = r5
            r5 = r7
            r18 = r6
            r6 = r8
            r16 = r11
            r11 = r7
            r7 = r18
            r10 = r8
            r8 = r17
            java.lang.Object r8 = b(r0, r1, r2, r3, r4, r5, r6, r7, r8)
            r6 = r18
            if (r6 != 0) goto L_0x0066
            if (r8 != 0) goto L_0x0066
            r7 = r17
            if (r7 != 0) goto L_0x0068
            return
        L_0x0066:
            r7 = r17
        L_0x0068:
            java.util.ArrayList r5 = a((androidx.fragment.app.s) r14, (java.lang.Object) r7, (androidx.fragment.app.Fragment) r13, (java.util.ArrayList<android.view.View>) r11, (android.view.View) r9)
            java.util.ArrayList r9 = a((androidx.fragment.app.s) r14, (java.lang.Object) r6, (androidx.fragment.app.Fragment) r12, (java.util.ArrayList<android.view.View>) r10, (android.view.View) r9)
            r0 = 4
            a((java.util.ArrayList<android.view.View>) r9, (int) r0)
            r0 = r14
            r1 = r6
            r2 = r7
            r3 = r8
            r4 = r12
            r12 = r5
            r5 = r15
            java.lang.Object r15 = a((androidx.fragment.app.s) r0, (java.lang.Object) r1, (java.lang.Object) r2, (java.lang.Object) r3, (androidx.fragment.app.Fragment) r4, (boolean) r5)
            if (r13 == 0) goto L_0x00a1
            if (r12 == 0) goto L_0x00a1
            int r0 = r12.size()
            if (r0 > 0) goto L_0x008f
            int r0 = r11.size()
            if (r0 <= 0) goto L_0x00a1
        L_0x008f:
            androidx.core.d.a r0 = new androidx.core.d.a
            r0.<init>()
            r1 = r22
            r1.b(r13, r0)
            androidx.fragment.app.q$a r2 = new androidx.fragment.app.q$a
            r2.<init>(r1, r13, r0)
            r14.a(r13, r15, r0, r2)
        L_0x00a1:
            if (r15 == 0) goto L_0x00cb
            a((androidx.fragment.app.s) r14, (java.lang.Object) r7, (androidx.fragment.app.Fragment) r13, (java.util.ArrayList<android.view.View>) r12)
            java.util.ArrayList r13 = r14.a((java.util.ArrayList<android.view.View>) r10)
            r0 = r14
            r1 = r15
            r2 = r6
            r3 = r9
            r4 = r7
            r5 = r12
            r6 = r8
            r7 = r10
            r0.a(r1, r2, r3, r4, r5, r6, r7)
            r0 = r16
            r14.a((android.view.ViewGroup) r0, (java.lang.Object) r15)
            r1 = r14
            r2 = r0
            r3 = r11
            r4 = r10
            r5 = r13
            r6 = r21
            r1.a(r2, r3, r4, r5, r6)
            r0 = 0
            a((java.util.ArrayList<android.view.View>) r9, (int) r0)
            r14.b((java.lang.Object) r8, (java.util.ArrayList<android.view.View>) r11, (java.util.ArrayList<android.view.View>) r10)
        L_0x00cb:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.fragment.app.q.b(androidx.fragment.app.j, int, androidx.fragment.app.q$h, android.view.View, g.a.a, androidx.fragment.app.q$g):void");
    }

    static void a(j jVar, ArrayList<a> arrayList, ArrayList<Boolean> arrayList2, int i2, int i3, boolean z, g gVar) {
        j jVar2 = jVar;
        ArrayList<a> arrayList3 = arrayList;
        ArrayList<Boolean> arrayList4 = arrayList2;
        int i4 = i3;
        boolean z2 = z;
        if (jVar2.n >= 1) {
            SparseArray sparseArray = new SparseArray();
            for (int i5 = i2; i5 < i4; i5++) {
                a aVar = arrayList3.get(i5);
                if (arrayList4.get(i5).booleanValue()) {
                    b(aVar, (SparseArray<h>) sparseArray, z2);
                } else {
                    a(aVar, (SparseArray<h>) sparseArray, z2);
                }
            }
            if (sparseArray.size() != 0) {
                View view = new View(jVar2.o.e());
                int size = sparseArray.size();
                for (int i6 = 0; i6 < size; i6++) {
                    int keyAt = sparseArray.keyAt(i6);
                    g.a.a<String, String> a2 = a(keyAt, arrayList3, arrayList4, i2, i4);
                    h hVar = (h) sparseArray.valueAt(i6);
                    if (z2) {
                        b(jVar, keyAt, hVar, view, a2, gVar);
                    } else {
                        a(jVar, keyAt, hVar, view, a2, gVar);
                    }
                }
            }
        }
    }

    private static g.a.a<String, String> a(int i2, ArrayList<a> arrayList, ArrayList<Boolean> arrayList2, int i3, int i4) {
        ArrayList<String> arrayList3;
        ArrayList<String> arrayList4;
        g.a.a<String, String> aVar = new g.a.a<>();
        for (int i5 = i4 - 1; i5 >= i3; i5--) {
            a aVar2 = arrayList.get(i5);
            if (aVar2.b(i2)) {
                boolean booleanValue = arrayList2.get(i5).booleanValue();
                ArrayList<String> arrayList5 = aVar2.n;
                if (arrayList5 != null) {
                    int size = arrayList5.size();
                    if (booleanValue) {
                        arrayList3 = aVar2.n;
                        arrayList4 = aVar2.o;
                    } else {
                        ArrayList<String> arrayList6 = aVar2.n;
                        arrayList3 = aVar2.o;
                        arrayList4 = arrayList6;
                    }
                    for (int i6 = 0; i6 < size; i6++) {
                        String str = arrayList4.get(i6);
                        String str2 = arrayList3.get(i6);
                        String remove = aVar.remove(str2);
                        if (remove != null) {
                            aVar.put(str, remove);
                        } else {
                            aVar.put(str, str2);
                        }
                    }
                }
            }
        }
        return aVar;
    }

    private static Object b(s sVar, Fragment fragment, boolean z) {
        Object obj;
        if (fragment == null) {
            return null;
        }
        if (z) {
            obj = fragment.A();
        } else {
            obj = fragment.n();
        }
        return sVar.b(obj);
    }

    private static Object b(s sVar, ViewGroup viewGroup, View view, g.a.a<String, String> aVar, h hVar, ArrayList<View> arrayList, ArrayList<View> arrayList2, Object obj, Object obj2) {
        Object obj3;
        Object obj4;
        Rect rect;
        View view2;
        s sVar2 = sVar;
        View view3 = view;
        g.a.a<String, String> aVar2 = aVar;
        h hVar2 = hVar;
        ArrayList<View> arrayList3 = arrayList;
        ArrayList<View> arrayList4 = arrayList2;
        Object obj5 = obj;
        Fragment fragment = hVar2.a;
        Fragment fragment2 = hVar2.d;
        if (fragment != null) {
            fragment.l0().setVisibility(0);
        }
        if (fragment == null || fragment2 == null) {
            return null;
        }
        boolean z = hVar2.b;
        if (aVar.isEmpty()) {
            obj3 = null;
        } else {
            obj3 = a(sVar, fragment, fragment2, z);
        }
        g.a.a<String, View> b2 = b(sVar, aVar2, obj3, hVar2);
        g.a.a<String, View> a2 = a(sVar, aVar2, obj3, hVar2);
        if (aVar.isEmpty()) {
            if (b2 != null) {
                b2.clear();
            }
            if (a2 != null) {
                a2.clear();
            }
            obj4 = null;
        } else {
            a(arrayList3, b2, (Collection<String>) aVar.keySet());
            a(arrayList4, a2, aVar.values());
            obj4 = obj3;
        }
        if (obj5 == null && obj2 == null && obj4 == null) {
            return null;
        }
        a(fragment, fragment2, z, b2, true);
        if (obj4 != null) {
            arrayList4.add(view3);
            sVar.b(obj4, view3, arrayList3);
            a(sVar, obj4, obj2, b2, hVar2.e, hVar2.f654f);
            Rect rect2 = new Rect();
            View a3 = a(a2, hVar2, obj5, z);
            if (a3 != null) {
                sVar.a(obj5, rect2);
            }
            rect = rect2;
            view2 = a3;
        } else {
            view2 = null;
            rect = null;
        }
        s.a(viewGroup, new e(fragment, fragment2, z, a2, view2, sVar, rect));
        return obj4;
    }

    private static void a(s sVar, Object obj, Fragment fragment, ArrayList<View> arrayList) {
        if (fragment != null && obj != null && fragment.o && fragment.C && fragment.P) {
            fragment.g(true);
            sVar.a(obj, fragment.F(), arrayList);
            s.a(fragment.J, new b(arrayList));
        }
    }

    private static void a(j jVar, int i2, h hVar, View view, g.a.a<String, String> aVar, g gVar) {
        Fragment fragment;
        Fragment fragment2;
        s a2;
        Object obj;
        j jVar2 = jVar;
        h hVar2 = hVar;
        View view2 = view;
        g.a.a<String, String> aVar2 = aVar;
        g gVar2 = gVar;
        ViewGroup viewGroup = jVar2.p.c() ? (ViewGroup) jVar2.p.a(i2) : null;
        if (viewGroup != null && (a2 = a(fragment2, fragment)) != null) {
            boolean z = hVar2.b;
            boolean z2 = hVar2.e;
            Object a3 = a(a2, (fragment = hVar2.a), z);
            Object b2 = b(a2, (fragment2 = hVar2.d), z2);
            ArrayList arrayList = new ArrayList();
            ArrayList arrayList2 = new ArrayList();
            ArrayList arrayList3 = arrayList;
            Object obj2 = b2;
            Object obj3 = a3;
            s sVar = a2;
            Fragment fragment3 = fragment2;
            Object a4 = a(a2, viewGroup, view, aVar, hVar, (ArrayList<View>) arrayList3, (ArrayList<View>) arrayList2, obj3, obj2);
            Object obj4 = obj3;
            if (obj4 == null && a4 == null) {
                obj = obj2;
                if (obj == null) {
                    return;
                }
            } else {
                obj = obj2;
            }
            ArrayList arrayList4 = arrayList3;
            ArrayList<View> a5 = a(sVar, obj, fragment3, (ArrayList<View>) arrayList4, view2);
            Object obj5 = (a5 == null || a5.isEmpty()) ? null : obj;
            sVar.a(obj4, view2);
            Object a6 = a(sVar, obj4, obj5, a4, fragment, hVar2.b);
            if (!(fragment3 == null || a5 == null || (a5.size() <= 0 && arrayList4.size() <= 0))) {
                androidx.core.d.a aVar3 = new androidx.core.d.a();
                gVar2.b(fragment3, aVar3);
                sVar.a(fragment3, a6, aVar3, new c(gVar2, fragment3, aVar3));
            }
            if (a6 != null) {
                ArrayList arrayList5 = new ArrayList();
                s sVar2 = sVar;
                sVar2.a(a6, obj4, arrayList5, obj5, a5, a4, arrayList2);
                a(sVar2, viewGroup, fragment, view, (ArrayList<View>) arrayList2, obj4, (ArrayList<View>) arrayList5, obj5, a5);
                ArrayList arrayList6 = arrayList2;
                g.a.a<String, String> aVar4 = aVar;
                sVar.a((View) viewGroup, (ArrayList<View>) arrayList6, (Map<String, String>) aVar4);
                sVar.a(viewGroup, a6);
                sVar.a(viewGroup, (ArrayList<View>) arrayList6, (Map<String, String>) aVar4);
            }
        }
    }

    private static g.a.a<String, View> b(s sVar, g.a.a<String, String> aVar, Object obj, h hVar) {
        l lVar;
        ArrayList<String> arrayList;
        if (aVar.isEmpty() || obj == null) {
            aVar.clear();
            return null;
        }
        Fragment fragment = hVar.d;
        g.a.a<String, View> aVar2 = new g.a.a<>();
        sVar.a((Map<String, View>) aVar2, fragment.l0());
        a aVar3 = hVar.f654f;
        if (hVar.e) {
            lVar = fragment.m();
            arrayList = aVar3.o;
        } else {
            lVar = fragment.o();
            arrayList = aVar3.n;
        }
        if (arrayList != null) {
            aVar2.a(arrayList);
        }
        if (lVar != null) {
            lVar.a(arrayList, aVar2);
            for (int size = arrayList.size() - 1; size >= 0; size--) {
                String str = arrayList.get(size);
                View view = aVar2.get(str);
                if (view == null) {
                    aVar.remove(str);
                } else if (!str.equals(v.v(view))) {
                    aVar.put(v.v(view), aVar.remove(str));
                }
            }
        } else {
            aVar.a(aVar2.keySet());
        }
        return aVar2;
    }

    private static void a(s sVar, ViewGroup viewGroup, Fragment fragment, View view, ArrayList<View> arrayList, Object obj, ArrayList<View> arrayList2, Object obj2, ArrayList<View> arrayList3) {
        ViewGroup viewGroup2 = viewGroup;
        s.a(viewGroup, new d(obj, sVar, view, fragment, arrayList, arrayList2, arrayList3, obj2));
    }

    private static s a(Fragment fragment, Fragment fragment2) {
        ArrayList arrayList = new ArrayList();
        if (fragment != null) {
            Object n = fragment.n();
            if (n != null) {
                arrayList.add(n);
            }
            Object A = fragment.A();
            if (A != null) {
                arrayList.add(A);
            }
            Object C = fragment.C();
            if (C != null) {
                arrayList.add(C);
            }
        }
        if (fragment2 != null) {
            Object l = fragment2.l();
            if (l != null) {
                arrayList.add(l);
            }
            Object x = fragment2.x();
            if (x != null) {
                arrayList.add(x);
            }
            Object B = fragment2.B();
            if (B != null) {
                arrayList.add(B);
            }
        }
        if (arrayList.isEmpty()) {
            return null;
        }
        s sVar = b;
        if (sVar != null && a(sVar, (List<Object>) arrayList)) {
            return b;
        }
        s sVar2 = c;
        if (sVar2 != null && a(sVar2, (List<Object>) arrayList)) {
            return c;
        }
        if (b == null && c == null) {
            return null;
        }
        throw new IllegalArgumentException("Invalid Transition types");
    }

    public static void b(a aVar, SparseArray<h> sparseArray, boolean z) {
        if (aVar.r.p.c()) {
            for (int size = aVar.a.size() - 1; size >= 0; size--) {
                a(aVar, aVar.a.get(size), sparseArray, true, z);
            }
        }
    }

    static boolean b() {
        return (b == null && c == null) ? false : true;
    }

    private static boolean a(s sVar, List<Object> list) {
        int size = list.size();
        for (int i2 = 0; i2 < size; i2++) {
            if (!sVar.a(list.get(i2))) {
                return false;
            }
        }
        return true;
    }

    private static Object a(s sVar, Fragment fragment, Fragment fragment2, boolean z) {
        Object obj;
        if (fragment == null || fragment2 == null) {
            return null;
        }
        if (z) {
            obj = fragment2.C();
        } else {
            obj = fragment.B();
        }
        return sVar.c(sVar.b(obj));
    }

    private static Object a(s sVar, Fragment fragment, boolean z) {
        Object obj;
        if (fragment == null) {
            return null;
        }
        if (z) {
            obj = fragment.x();
        } else {
            obj = fragment.l();
        }
        return sVar.b(obj);
    }

    private static void a(ArrayList<View> arrayList, g.a.a<String, View> aVar, Collection<String> collection) {
        for (int size = aVar.size() - 1; size >= 0; size--) {
            View d2 = aVar.d(size);
            if (collection.contains(v.v(d2))) {
                arrayList.add(d2);
            }
        }
    }

    private static Object a(s sVar, ViewGroup viewGroup, View view, g.a.a<String, String> aVar, h hVar, ArrayList<View> arrayList, ArrayList<View> arrayList2, Object obj, Object obj2) {
        g.a.a<String, String> aVar2;
        Object obj3;
        Object obj4;
        Rect rect;
        s sVar2 = sVar;
        h hVar2 = hVar;
        ArrayList<View> arrayList3 = arrayList;
        Object obj5 = obj;
        Fragment fragment = hVar2.a;
        Fragment fragment2 = hVar2.d;
        if (fragment == null || fragment2 == null) {
            return null;
        }
        boolean z = hVar2.b;
        if (aVar.isEmpty()) {
            aVar2 = aVar;
            obj3 = null;
        } else {
            obj3 = a(sVar2, fragment, fragment2, z);
            aVar2 = aVar;
        }
        g.a.a<String, View> b2 = b(sVar2, aVar2, obj3, hVar2);
        if (aVar.isEmpty()) {
            obj4 = null;
        } else {
            arrayList3.addAll(b2.values());
            obj4 = obj3;
        }
        if (obj5 == null && obj2 == null && obj4 == null) {
            return null;
        }
        a(fragment, fragment2, z, b2, true);
        if (obj4 != null) {
            rect = new Rect();
            sVar2.b(obj4, view, arrayList3);
            a(sVar, obj4, obj2, b2, hVar2.e, hVar2.f654f);
            if (obj5 != null) {
                sVar2.a(obj5, rect);
            }
        } else {
            rect = null;
        }
        f fVar = r0;
        f fVar2 = new f(sVar, aVar, obj4, hVar, arrayList2, view, fragment, fragment2, z, arrayList, obj, rect);
        s.a(viewGroup, fVar);
        return obj4;
    }

    static g.a.a<String, View> a(s sVar, g.a.a<String, String> aVar, Object obj, h hVar) {
        l lVar;
        ArrayList<String> arrayList;
        String a2;
        Fragment fragment = hVar.a;
        View F = fragment.F();
        if (aVar.isEmpty() || obj == null || F == null) {
            aVar.clear();
            return null;
        }
        g.a.a<String, View> aVar2 = new g.a.a<>();
        sVar.a((Map<String, View>) aVar2, F);
        a aVar3 = hVar.c;
        if (hVar.b) {
            lVar = fragment.o();
            arrayList = aVar3.n;
        } else {
            lVar = fragment.m();
            arrayList = aVar3.o;
        }
        if (arrayList != null) {
            aVar2.a(arrayList);
            aVar2.a(aVar.values());
        }
        if (lVar != null) {
            lVar.a(arrayList, aVar2);
            for (int size = arrayList.size() - 1; size >= 0; size--) {
                String str = arrayList.get(size);
                View view = aVar2.get(str);
                if (view == null) {
                    String a3 = a(aVar, str);
                    if (a3 != null) {
                        aVar.remove(a3);
                    }
                } else if (!str.equals(v.v(view)) && (a2 = a(aVar, str)) != null) {
                    aVar.put(a2, v.v(view));
                }
            }
        } else {
            a(aVar, aVar2);
        }
        return aVar2;
    }

    private static String a(g.a.a<String, String> aVar, String str) {
        int size = aVar.size();
        for (int i2 = 0; i2 < size; i2++) {
            if (str.equals(aVar.d(i2))) {
                return aVar.b(i2);
            }
        }
        return null;
    }

    static View a(g.a.a<String, View> aVar, h hVar, Object obj, boolean z) {
        ArrayList<String> arrayList;
        String str;
        a aVar2 = hVar.c;
        if (obj == null || aVar == null || (arrayList = aVar2.n) == null || arrayList.isEmpty()) {
            return null;
        }
        if (z) {
            str = aVar2.n.get(0);
        } else {
            str = aVar2.o.get(0);
        }
        return aVar.get(str);
    }

    private static void a(s sVar, Object obj, Object obj2, g.a.a<String, View> aVar, boolean z, a aVar2) {
        String str;
        ArrayList<String> arrayList = aVar2.n;
        if (arrayList != null && !arrayList.isEmpty()) {
            if (z) {
                str = aVar2.o.get(0);
            } else {
                str = aVar2.n.get(0);
            }
            View view = aVar.get(str);
            sVar.c(obj, view);
            if (obj2 != null) {
                sVar.c(obj2, view);
            }
        }
    }

    private static void a(g.a.a<String, String> aVar, g.a.a<String, View> aVar2) {
        for (int size = aVar.size() - 1; size >= 0; size--) {
            if (!aVar2.containsKey(aVar.d(size))) {
                aVar.c(size);
            }
        }
    }

    static void a(Fragment fragment, Fragment fragment2, boolean z, g.a.a<String, View> aVar, boolean z2) {
        l lVar;
        int i2;
        if (z) {
            lVar = fragment2.m();
        } else {
            lVar = fragment.m();
        }
        if (lVar != null) {
            ArrayList arrayList = new ArrayList();
            ArrayList arrayList2 = new ArrayList();
            if (aVar == null) {
                i2 = 0;
            } else {
                i2 = aVar.size();
            }
            for (int i3 = 0; i3 < i2; i3++) {
                arrayList2.add(aVar.b(i3));
                arrayList.add(aVar.d(i3));
            }
            if (z2) {
                lVar.b(arrayList2, arrayList, (List<View>) null);
            } else {
                lVar.a(arrayList2, arrayList, (List<View>) null);
            }
        }
    }

    static ArrayList<View> a(s sVar, Object obj, Fragment fragment, ArrayList<View> arrayList, View view) {
        if (obj == null) {
            return null;
        }
        ArrayList<View> arrayList2 = new ArrayList<>();
        View F = fragment.F();
        if (F != null) {
            sVar.a(arrayList2, F);
        }
        if (arrayList != null) {
            arrayList2.removeAll(arrayList);
        }
        if (arrayList2.isEmpty()) {
            return arrayList2;
        }
        arrayList2.add(view);
        sVar.a(obj, arrayList2);
        return arrayList2;
    }

    static void a(ArrayList<View> arrayList, int i2) {
        if (arrayList != null) {
            for (int size = arrayList.size() - 1; size >= 0; size--) {
                arrayList.get(size).setVisibility(i2);
            }
        }
    }

    private static Object a(s sVar, Object obj, Object obj2, Object obj3, Fragment fragment, boolean z) {
        boolean z2;
        if (obj == null || obj2 == null || fragment == null) {
            z2 = true;
        } else {
            z2 = z ? fragment.f() : fragment.e();
        }
        if (z2) {
            return sVar.b(obj2, obj, obj3);
        }
        return sVar.a(obj2, obj, obj3);
    }

    public static void a(a aVar, SparseArray<h> sparseArray, boolean z) {
        int size = aVar.a.size();
        for (int i2 = 0; i2 < size; i2++) {
            a(aVar, aVar.a.get(i2), sparseArray, false, z);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:29:0x0039, code lost:
        if (r0.o != false) goto L_0x008c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:51:0x006e, code lost:
        r9 = true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:63:0x008a, code lost:
        if (r0.C == false) goto L_0x008c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:64:0x008c, code lost:
        r9 = true;
     */
    /* JADX WARNING: Removed duplicated region for block: B:69:0x009a  */
    /* JADX WARNING: Removed duplicated region for block: B:90:0x00d9 A[ADDED_TO_REGION] */
    /* JADX WARNING: Removed duplicated region for block: B:96:? A[ADDED_TO_REGION, RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static void a(androidx.fragment.app.a r8, androidx.fragment.app.p.a r9, android.util.SparseArray<androidx.fragment.app.q.h> r10, boolean r11, boolean r12) {
        /*
            androidx.fragment.app.Fragment r0 = r9.b
            if (r0 != 0) goto L_0x0005
            return
        L_0x0005:
            int r1 = r0.A
            if (r1 != 0) goto L_0x000a
            return
        L_0x000a:
            if (r11 == 0) goto L_0x0013
            int[] r2 = a
            int r9 = r9.a
            r9 = r2[r9]
            goto L_0x0015
        L_0x0013:
            int r9 = r9.a
        L_0x0015:
            r2 = 0
            r3 = 1
            if (r9 == r3) goto L_0x007f
            r4 = 3
            if (r9 == r4) goto L_0x0057
            r4 = 4
            if (r9 == r4) goto L_0x003f
            r4 = 5
            if (r9 == r4) goto L_0x002d
            r4 = 6
            if (r9 == r4) goto L_0x0057
            r4 = 7
            if (r9 == r4) goto L_0x007f
            r9 = 0
        L_0x0029:
            r4 = 0
            r5 = 0
            goto L_0x0092
        L_0x002d:
            if (r12 == 0) goto L_0x003c
            boolean r9 = r0.P
            if (r9 == 0) goto L_0x008e
            boolean r9 = r0.C
            if (r9 != 0) goto L_0x008e
            boolean r9 = r0.o
            if (r9 == 0) goto L_0x008e
            goto L_0x008c
        L_0x003c:
            boolean r9 = r0.C
            goto L_0x008f
        L_0x003f:
            if (r12 == 0) goto L_0x004e
            boolean r9 = r0.P
            if (r9 == 0) goto L_0x0070
            boolean r9 = r0.o
            if (r9 == 0) goto L_0x0070
            boolean r9 = r0.C
            if (r9 == 0) goto L_0x0070
        L_0x004d:
            goto L_0x006e
        L_0x004e:
            boolean r9 = r0.o
            if (r9 == 0) goto L_0x0070
            boolean r9 = r0.C
            if (r9 != 0) goto L_0x0070
            goto L_0x004d
        L_0x0057:
            if (r12 == 0) goto L_0x0072
            boolean r9 = r0.o
            if (r9 != 0) goto L_0x0070
            android.view.View r9 = r0.K
            if (r9 == 0) goto L_0x0070
            int r9 = r9.getVisibility()
            if (r9 != 0) goto L_0x0070
            float r9 = r0.Q
            r4 = 0
            int r9 = (r9 > r4 ? 1 : (r9 == r4 ? 0 : -1))
            if (r9 < 0) goto L_0x0070
        L_0x006e:
            r9 = 1
            goto L_0x007b
        L_0x0070:
            r9 = 0
            goto L_0x007b
        L_0x0072:
            boolean r9 = r0.o
            if (r9 == 0) goto L_0x0070
            boolean r9 = r0.C
            if (r9 != 0) goto L_0x0070
            goto L_0x006e
        L_0x007b:
            r5 = r9
            r9 = 0
            r4 = 1
            goto L_0x0092
        L_0x007f:
            if (r12 == 0) goto L_0x0084
            boolean r9 = r0.O
            goto L_0x008f
        L_0x0084:
            boolean r9 = r0.o
            if (r9 != 0) goto L_0x008e
            boolean r9 = r0.C
            if (r9 != 0) goto L_0x008e
        L_0x008c:
            r9 = 1
            goto L_0x008f
        L_0x008e:
            r9 = 0
        L_0x008f:
            r2 = r9
            r9 = 1
            goto L_0x0029
        L_0x0092:
            java.lang.Object r6 = r10.get(r1)
            androidx.fragment.app.q$h r6 = (androidx.fragment.app.q.h) r6
            if (r2 == 0) goto L_0x00a4
            androidx.fragment.app.q$h r6 = a((androidx.fragment.app.q.h) r6, (android.util.SparseArray<androidx.fragment.app.q.h>) r10, (int) r1)
            r6.a = r0
            r6.b = r11
            r6.c = r8
        L_0x00a4:
            r2 = 0
            if (r12 != 0) goto L_0x00c5
            if (r9 == 0) goto L_0x00c5
            if (r6 == 0) goto L_0x00b1
            androidx.fragment.app.Fragment r9 = r6.d
            if (r9 != r0) goto L_0x00b1
            r6.d = r2
        L_0x00b1:
            androidx.fragment.app.j r9 = r8.r
            int r7 = r0.e
            if (r7 >= r3) goto L_0x00c5
            int r7 = r9.n
            if (r7 < r3) goto L_0x00c5
            boolean r7 = r8.p
            if (r7 != 0) goto L_0x00c5
            r9.h(r0)
            r9.a((androidx.fragment.app.Fragment) r0, (int) r3)
        L_0x00c5:
            if (r5 == 0) goto L_0x00d7
            if (r6 == 0) goto L_0x00cd
            androidx.fragment.app.Fragment r9 = r6.d
            if (r9 != 0) goto L_0x00d7
        L_0x00cd:
            androidx.fragment.app.q$h r6 = a((androidx.fragment.app.q.h) r6, (android.util.SparseArray<androidx.fragment.app.q.h>) r10, (int) r1)
            r6.d = r0
            r6.e = r11
            r6.f654f = r8
        L_0x00d7:
            if (r12 != 0) goto L_0x00e3
            if (r4 == 0) goto L_0x00e3
            if (r6 == 0) goto L_0x00e3
            androidx.fragment.app.Fragment r8 = r6.a
            if (r8 != r0) goto L_0x00e3
            r6.a = r2
        L_0x00e3:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.fragment.app.q.a(androidx.fragment.app.a, androidx.fragment.app.p$a, android.util.SparseArray, boolean, boolean):void");
    }

    private static h a(h hVar, SparseArray<h> sparseArray, int i2) {
        if (hVar != null) {
            return hVar;
        }
        h hVar2 = new h();
        sparseArray.put(i2, hVar2);
        return hVar2;
    }
}
