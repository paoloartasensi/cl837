package androidx.navigation;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import androidx.activity.OnBackPressedDispatcher;
import androidx.core.app.m;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelStore;
import androidx.navigation.j;
import androidx.navigation.o;
import androidx.navigation.r;
import com.jeremyliao.liveeventbus.BuildConfig;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

public class NavController {
    private final Context a;
    private Activity b;
    private n c;
    k d;
    private Bundle e;

    /* renamed from: f  reason: collision with root package name */
    private Parcelable[] f722f;

    /* renamed from: g  reason: collision with root package name */
    private boolean f723g;

    /* renamed from: h  reason: collision with root package name */
    final Deque<f> f724h = new ArrayDeque();

    /* renamed from: i  reason: collision with root package name */
    private LifecycleOwner f725i;

    /* renamed from: j  reason: collision with root package name */
    private g f726j;
    private final s k = new s();
    private final CopyOnWriteArrayList<b> l = new CopyOnWriteArrayList<>();
    private final LifecycleObserver m = new LifecycleEventObserver() {
        public void onStateChanged(LifecycleOwner lifecycleOwner, Lifecycle.Event event) {
            NavController navController = NavController.this;
            if (navController.d != null) {
                for (f a : navController.f724h) {
                    a.a(event);
                }
            }
        }
    };
    private final androidx.activity.b n = new a(false);
    private boolean o = true;

    class a extends androidx.activity.b {
        a(boolean z) {
            super(z);
        }

        public void a() {
            NavController.this.g();
        }
    }

    public interface b {
        void a(NavController navController, j jVar, Bundle bundle);
    }

    public NavController(Context context) {
        this.a = context;
        while (true) {
            if (!(context instanceof ContextWrapper)) {
                break;
            } else if (context instanceof Activity) {
                this.b = (Activity) context;
                break;
            } else {
                context = ((ContextWrapper) context).getBaseContext();
            }
        }
        s sVar = this.k;
        sVar.a((r<? extends j>) new l(sVar));
        this.k.a((r<? extends j>) new b(this.a));
    }

    /* JADX WARNING: Removed duplicated region for block: B:0:0x0000 A[LOOP:0: B:0:0x0000->B:5:0x002d, LOOP_START, MTH_ENTER_BLOCK] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean i() {
        /*
            r10 = this;
        L_0x0000:
            java.util.Deque<androidx.navigation.f> r0 = r10.f724h
            boolean r0 = r0.isEmpty()
            r1 = 1
            if (r0 != 0) goto L_0x0030
            java.util.Deque<androidx.navigation.f> r0 = r10.f724h
            java.lang.Object r0 = r0.peekLast()
            androidx.navigation.f r0 = (androidx.navigation.f) r0
            androidx.navigation.j r0 = r0.d()
            boolean r0 = r0 instanceof androidx.navigation.k
            if (r0 == 0) goto L_0x0030
            java.util.Deque<androidx.navigation.f> r0 = r10.f724h
            java.lang.Object r0 = r0.peekLast()
            androidx.navigation.f r0 = (androidx.navigation.f) r0
            androidx.navigation.j r0 = r0.d()
            int r0 = r0.d()
            boolean r0 = r10.b(r0, r1)
            if (r0 == 0) goto L_0x0030
            goto L_0x0000
        L_0x0030:
            java.util.Deque<androidx.navigation.f> r0 = r10.f724h
            boolean r0 = r0.isEmpty()
            if (r0 != 0) goto L_0x010c
            java.util.Deque<androidx.navigation.f> r0 = r10.f724h
            java.lang.Object r0 = r0.peekLast()
            androidx.navigation.f r0 = (androidx.navigation.f) r0
            androidx.navigation.j r0 = r0.d()
            r2 = 0
            boolean r3 = r0 instanceof androidx.navigation.c
            if (r3 == 0) goto L_0x0068
            java.util.Deque<androidx.navigation.f> r3 = r10.f724h
            java.util.Iterator r3 = r3.descendingIterator()
        L_0x004f:
            boolean r4 = r3.hasNext()
            if (r4 == 0) goto L_0x0068
            java.lang.Object r4 = r3.next()
            androidx.navigation.f r4 = (androidx.navigation.f) r4
            androidx.navigation.j r4 = r4.d()
            boolean r5 = r4 instanceof androidx.navigation.k
            if (r5 != 0) goto L_0x004f
            boolean r5 = r4 instanceof androidx.navigation.c
            if (r5 != 0) goto L_0x004f
            r2 = r4
        L_0x0068:
            java.util.HashMap r3 = new java.util.HashMap
            r3.<init>()
            java.util.Deque<androidx.navigation.f> r4 = r10.f724h
            java.util.Iterator r4 = r4.descendingIterator()
        L_0x0073:
            boolean r5 = r4.hasNext()
            if (r5 == 0) goto L_0x00c7
            java.lang.Object r5 = r4.next()
            androidx.navigation.f r5 = (androidx.navigation.f) r5
            androidx.lifecycle.Lifecycle$State r6 = r5.e()
            androidx.navigation.j r7 = r5.d()
            if (r0 == 0) goto L_0x009f
            int r8 = r7.d()
            int r9 = r0.d()
            if (r8 != r9) goto L_0x009f
            androidx.lifecycle.Lifecycle$State r7 = androidx.lifecycle.Lifecycle.State.RESUMED
            if (r6 == r7) goto L_0x009a
            r3.put(r5, r7)
        L_0x009a:
            androidx.navigation.k r0 = r0.g()
            goto L_0x0073
        L_0x009f:
            if (r2 == 0) goto L_0x00c1
            int r7 = r7.d()
            int r8 = r2.d()
            if (r7 != r8) goto L_0x00c1
            androidx.lifecycle.Lifecycle$State r7 = androidx.lifecycle.Lifecycle.State.RESUMED
            if (r6 != r7) goto L_0x00b5
            androidx.lifecycle.Lifecycle$State r6 = androidx.lifecycle.Lifecycle.State.STARTED
            r5.a((androidx.lifecycle.Lifecycle.State) r6)
            goto L_0x00bc
        L_0x00b5:
            androidx.lifecycle.Lifecycle$State r7 = androidx.lifecycle.Lifecycle.State.STARTED
            if (r6 == r7) goto L_0x00bc
            r3.put(r5, r7)
        L_0x00bc:
            androidx.navigation.k r2 = r2.g()
            goto L_0x0073
        L_0x00c1:
            androidx.lifecycle.Lifecycle$State r6 = androidx.lifecycle.Lifecycle.State.CREATED
            r5.a((androidx.lifecycle.Lifecycle.State) r6)
            goto L_0x0073
        L_0x00c7:
            java.util.Deque<androidx.navigation.f> r0 = r10.f724h
            java.util.Iterator r0 = r0.iterator()
        L_0x00cd:
            boolean r2 = r0.hasNext()
            if (r2 == 0) goto L_0x00e5
            java.lang.Object r2 = r0.next()
            androidx.navigation.f r2 = (androidx.navigation.f) r2
            java.lang.Object r4 = r3.get(r2)
            androidx.lifecycle.Lifecycle$State r4 = (androidx.lifecycle.Lifecycle.State) r4
            if (r4 == 0) goto L_0x00cd
            r2.a((androidx.lifecycle.Lifecycle.State) r4)
            goto L_0x00cd
        L_0x00e5:
            java.util.Deque<androidx.navigation.f> r0 = r10.f724h
            java.lang.Object r0 = r0.peekLast()
            androidx.navigation.f r0 = (androidx.navigation.f) r0
            java.util.concurrent.CopyOnWriteArrayList<androidx.navigation.NavController$b> r2 = r10.l
            java.util.Iterator r2 = r2.iterator()
        L_0x00f3:
            boolean r3 = r2.hasNext()
            if (r3 == 0) goto L_0x010b
            java.lang.Object r3 = r2.next()
            androidx.navigation.NavController$b r3 = (androidx.navigation.NavController.b) r3
            androidx.navigation.j r4 = r0.d()
            android.os.Bundle r5 = r0.c()
            r3.a(r10, r4, r5)
            goto L_0x00f3
        L_0x010b:
            return r1
        L_0x010c:
            r0 = 0
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.navigation.NavController.i():boolean");
    }

    private int j() {
        int i2 = 0;
        for (f d2 : this.f724h) {
            if (!(d2.d() instanceof k)) {
                i2++;
            }
        }
        return i2;
    }

    private void k() {
        androidx.activity.b bVar = this.n;
        boolean z = true;
        if (!this.o || j() <= 1) {
            z = false;
        }
        bVar.a(z);
    }

    /* access modifiers changed from: package-private */
    public Context a() {
        return this.a;
    }

    public void addOnDestinationChangedListener(b bVar) {
        if (!this.f724h.isEmpty()) {
            f peekLast = this.f724h.peekLast();
            bVar.a(this, peekLast.d(), peekLast.c());
        }
        this.l.add(bVar);
    }

    /* access modifiers changed from: package-private */
    public boolean b(int i2, boolean z) {
        boolean z2;
        boolean z3 = false;
        if (this.f724h.isEmpty()) {
            return false;
        }
        ArrayList arrayList = new ArrayList();
        Iterator<f> descendingIterator = this.f724h.descendingIterator();
        while (true) {
            if (!descendingIterator.hasNext()) {
                z2 = false;
                break;
            }
            j d2 = descendingIterator.next().d();
            r a2 = this.k.a(d2.f());
            if (z || d2.d() != i2) {
                arrayList.add(a2);
            }
            if (d2.d() == i2) {
                z2 = true;
                break;
            }
        }
        if (!z2) {
            String a3 = j.a(this.a, i2);
            Log.i("NavController", "Ignoring popBackStack to destination " + a3 + " as it was not found on the current back stack");
            return false;
        }
        Iterator it = arrayList.iterator();
        while (it.hasNext() && ((r) it.next()).c()) {
            f removeLast = this.f724h.removeLast();
            removeLast.a(Lifecycle.State.DESTROYED);
            g gVar = this.f726j;
            if (gVar != null) {
                gVar.a(removeLast.f731j);
            }
            z3 = true;
        }
        k();
        return z3;
    }

    public k c() {
        k kVar = this.d;
        if (kVar != null) {
            return kVar;
        }
        throw new IllegalStateException("You must call setGraph() before calling getGraph()");
    }

    public n d() {
        if (this.c == null) {
            this.c = new n(this.a, this.k);
        }
        return this.c;
    }

    public s e() {
        return this.k;
    }

    public boolean f() {
        if (j() != 1) {
            return g();
        }
        j b2 = b();
        int d2 = b2.d();
        for (k g2 = b2.g(); g2 != null; g2 = g2.g()) {
            if (g2.j() != d2) {
                i iVar = new i(this);
                iVar.a(g2.d());
                iVar.a().b();
                Activity activity = this.b;
                if (activity != null) {
                    activity.finish();
                }
                return true;
            }
            d2 = g2.d();
        }
        return false;
    }

    public boolean g() {
        if (this.f724h.isEmpty()) {
            return false;
        }
        return a(b().d(), true);
    }

    public Bundle h() {
        Bundle bundle;
        ArrayList arrayList = new ArrayList();
        Bundle bundle2 = new Bundle();
        for (Map.Entry next : this.k.a().entrySet()) {
            String str = (String) next.getKey();
            Bundle b2 = ((r) next.getValue()).b();
            if (b2 != null) {
                arrayList.add(str);
                bundle2.putBundle(str, b2);
            }
        }
        if (!arrayList.isEmpty()) {
            bundle = new Bundle();
            bundle2.putStringArrayList("android-support-nav:controller:navigatorState:names", arrayList);
            bundle.putBundle("android-support-nav:controller:navigatorState", bundle2);
        } else {
            bundle = null;
        }
        if (!this.f724h.isEmpty()) {
            if (bundle == null) {
                bundle = new Bundle();
            }
            Parcelable[] parcelableArr = new Parcelable[this.f724h.size()];
            int i2 = 0;
            for (f navBackStackEntryState : this.f724h) {
                parcelableArr[i2] = new NavBackStackEntryState(navBackStackEntryState);
                i2++;
            }
            bundle.putParcelableArray("android-support-nav:controller:backStack", parcelableArr);
        }
        if (this.f723g) {
            if (bundle == null) {
                bundle = new Bundle();
            }
            bundle.putBoolean("android-support-nav:controller:deepLinkHandled", this.f723g);
        }
        return bundle;
    }

    public void removeOnDestinationChangedListener(b bVar) {
        this.l.remove(bVar);
    }

    public boolean a(int i2, boolean z) {
        return b(i2, z) && i();
    }

    public void a(int i2, Bundle bundle) {
        a(d().a(i2), bundle);
    }

    public void a(k kVar, Bundle bundle) {
        k kVar2 = this.d;
        if (kVar2 != null) {
            b(kVar2.d(), true);
        }
        this.d = kVar;
        b(bundle);
    }

    public boolean a(Intent intent) {
        k kVar;
        j.a a2;
        if (intent == null) {
            return false;
        }
        Bundle extras = intent.getExtras();
        int[] intArray = extras != null ? extras.getIntArray("android-support-nav:controller:deepLinkIds") : null;
        Bundle bundle = new Bundle();
        Bundle bundle2 = extras != null ? extras.getBundle("android-support-nav:controller:deepLinkExtras") : null;
        if (bundle2 != null) {
            bundle.putAll(bundle2);
        }
        if (!((intArray != null && intArray.length != 0) || intent.getData() == null || (a2 = this.d.a(intent.getData())) == null)) {
            intArray = a2.a().a();
            bundle.putAll(a2.b());
        }
        if (intArray == null || intArray.length == 0) {
            return false;
        }
        String a3 = a(intArray);
        if (a3 != null) {
            Log.i("NavController", "Could not find destination " + a3 + " in the navigation graph, ignoring the deep link from " + intent);
            return false;
        }
        bundle.putParcelable("android-support-nav:controller:deepLinkIntent", intent);
        int flags = intent.getFlags();
        int i2 = 268435456 & flags;
        if (i2 != 0 && (flags & 32768) == 0) {
            intent.addFlags(32768);
            m a4 = m.a(this.a);
            a4.b(intent);
            a4.b();
            Activity activity = this.b;
            if (activity != null) {
                activity.finish();
                this.b.overridePendingTransition(0, 0);
            }
            return true;
        } else if (i2 != 0) {
            if (!this.f724h.isEmpty()) {
                b(this.d.d(), true);
            }
            int i3 = 0;
            while (i3 < intArray.length) {
                int i4 = i3 + 1;
                int i5 = intArray[i3];
                j a5 = a(i5);
                if (a5 != null) {
                    o.a aVar = new o.a();
                    aVar.a(0);
                    aVar.b(0);
                    a(a5, bundle, aVar.a(), (r.a) null);
                    i3 = i4;
                } else {
                    throw new IllegalStateException("unknown destination during deep link: " + j.a(this.a, i5));
                }
            }
            return true;
        } else {
            k kVar2 = this.d;
            int i6 = 0;
            while (i6 < intArray.length) {
                int i7 = intArray[i6];
                j c2 = i6 == 0 ? this.d : kVar2.c(i7);
                if (c2 != null) {
                    if (i6 != intArray.length - 1) {
                        while (true) {
                            kVar = (k) c2;
                            if (!(kVar.c(kVar.j()) instanceof k)) {
                                break;
                            }
                            c2 = kVar.c(kVar.j());
                        }
                        kVar2 = kVar;
                    } else {
                        Bundle a6 = c2.a(bundle);
                        o.a aVar2 = new o.a();
                        aVar2.a(this.d.d(), true);
                        aVar2.a(0);
                        aVar2.b(0);
                        a(c2, a6, aVar2.a(), (r.a) null);
                    }
                    i6++;
                } else {
                    throw new IllegalStateException("unknown destination during deep link: " + j.a(this.a, i7));
                }
            }
            this.f723g = true;
            return true;
        }
    }

    public void b(int i2) {
        a(i2, (Bundle) null);
    }

    private void b(Bundle bundle) {
        Activity activity;
        ArrayList<String> stringArrayList;
        Bundle bundle2 = this.e;
        if (!(bundle2 == null || (stringArrayList = bundle2.getStringArrayList("android-support-nav:controller:navigatorState:names")) == null)) {
            Iterator<String> it = stringArrayList.iterator();
            while (it.hasNext()) {
                String next = it.next();
                r a2 = this.k.a(next);
                Bundle bundle3 = this.e.getBundle(next);
                if (bundle3 != null) {
                    a2.a(bundle3);
                }
            }
        }
        Parcelable[] parcelableArr = this.f722f;
        boolean z = false;
        if (parcelableArr != null) {
            int length = parcelableArr.length;
            int i2 = 0;
            while (i2 < length) {
                NavBackStackEntryState navBackStackEntryState = (NavBackStackEntryState) parcelableArr[i2];
                j a3 = a(navBackStackEntryState.b());
                if (a3 != null) {
                    Bundle a4 = navBackStackEntryState.a();
                    if (a4 != null) {
                        a4.setClassLoader(this.a.getClassLoader());
                    }
                    this.f724h.add(new f(this.a, a3, a4, this.f725i, this.f726j, navBackStackEntryState.d(), navBackStackEntryState.c()));
                    i2++;
                } else {
                    throw new IllegalStateException("unknown destination during restore: " + this.a.getResources().getResourceName(navBackStackEntryState.b()));
                }
            }
            k();
            this.f722f = null;
        }
        if (this.d != null && this.f724h.isEmpty()) {
            if (!this.f723g && (activity = this.b) != null && a(activity.getIntent())) {
                z = true;
            }
            if (!z) {
                a((j) this.d, bundle, (o) null, (r.a) null);
            }
        }
    }

    public j b() {
        if (this.f724h.isEmpty()) {
            return null;
        }
        return this.f724h.getLast().d();
    }

    private String a(int[] iArr) {
        k kVar;
        k kVar2 = this.d;
        int i2 = 0;
        while (true) {
            j jVar = null;
            if (i2 >= iArr.length) {
                return null;
            }
            int i3 = iArr[i2];
            if (i2 != 0) {
                jVar = kVar2.a(i3, false);
            } else if (this.d.d() == i3) {
                jVar = this.d;
            }
            if (jVar == null) {
                return j.a(this.a, i3);
            }
            if (i2 != iArr.length - 1) {
                while (true) {
                    kVar = (k) jVar;
                    if (!(kVar.c(kVar.j()) instanceof k)) {
                        break;
                    }
                    jVar = kVar.c(kVar.j());
                }
                kVar2 = kVar;
            }
            i2++;
        }
    }

    /* access modifiers changed from: package-private */
    public j a(int i2) {
        j jVar;
        k kVar;
        k kVar2 = this.d;
        if (kVar2 == null) {
            return null;
        }
        if (kVar2.d() == i2) {
            return this.d;
        }
        if (this.f724h.isEmpty()) {
            jVar = this.d;
        } else {
            jVar = this.f724h.getLast().d();
        }
        if (jVar instanceof k) {
            kVar = (k) jVar;
        } else {
            kVar = jVar.g();
        }
        return kVar.c(i2);
    }

    public void a(int i2, Bundle bundle, o oVar) {
        a(i2, bundle, oVar, (r.a) null);
    }

    public void a(int i2, Bundle bundle, o oVar, r.a aVar) {
        j jVar;
        int i3;
        String str;
        if (this.f724h.isEmpty()) {
            jVar = this.d;
        } else {
            jVar = this.f724h.getLast().d();
        }
        if (jVar != null) {
            d a2 = jVar.a(i2);
            Bundle bundle2 = null;
            if (a2 != null) {
                if (oVar == null) {
                    oVar = a2.c();
                }
                i3 = a2.b();
                Bundle a3 = a2.a();
                if (a3 != null) {
                    bundle2 = new Bundle();
                    bundle2.putAll(a3);
                }
            } else {
                i3 = i2;
            }
            if (bundle != null) {
                if (bundle2 == null) {
                    bundle2 = new Bundle();
                }
                bundle2.putAll(bundle);
            }
            if (i3 == 0 && oVar != null && oVar.e() != -1) {
                a(oVar.e(), oVar.f());
            } else if (i3 != 0) {
                j a4 = a(i3);
                if (a4 == null) {
                    String a5 = j.a(this.a, i3);
                    StringBuilder sb = new StringBuilder();
                    sb.append("navigation destination ");
                    sb.append(a5);
                    if (a2 != null) {
                        str = " referenced from action " + j.a(this.a, i2);
                    } else {
                        str = BuildConfig.FLAVOR;
                    }
                    sb.append(str);
                    sb.append(" is unknown to this NavController");
                    throw new IllegalArgumentException(sb.toString());
                }
                a(a4, bundle2, oVar, aVar);
            } else {
                throw new IllegalArgumentException("Destination id == 0 can only be used in conjunction with a valid navOptions.popUpTo");
            }
        } else {
            throw new IllegalStateException("no current navigation node");
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:9:0x002f A[LOOP:0: B:9:0x002f->B:14:0x005c, LOOP_START] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void a(androidx.navigation.j r9, android.os.Bundle r10, androidx.navigation.o r11, androidx.navigation.r.a r12) {
        /*
            r8 = this;
            if (r11 == 0) goto L_0x0016
            int r0 = r11.e()
            r1 = -1
            if (r0 == r1) goto L_0x0016
            int r0 = r11.e()
            boolean r1 = r11.f()
            boolean r0 = r8.b(r0, r1)
            goto L_0x0017
        L_0x0016:
            r0 = 0
        L_0x0017:
            androidx.navigation.s r1 = r8.k
            java.lang.String r2 = r9.f()
            androidx.navigation.r r1 = r1.a((java.lang.String) r2)
            android.os.Bundle r10 = r9.a((android.os.Bundle) r10)
            androidx.navigation.j r9 = r1.a(r9, r10, r11, r12)
            if (r9 == 0) goto L_0x00c0
            boolean r11 = r9 instanceof androidx.navigation.c
            if (r11 != 0) goto L_0x005f
        L_0x002f:
            java.util.Deque<androidx.navigation.f> r11 = r8.f724h
            boolean r11 = r11.isEmpty()
            if (r11 != 0) goto L_0x005f
            java.util.Deque<androidx.navigation.f> r11 = r8.f724h
            java.lang.Object r11 = r11.peekLast()
            androidx.navigation.f r11 = (androidx.navigation.f) r11
            androidx.navigation.j r11 = r11.d()
            boolean r11 = r11 instanceof androidx.navigation.c
            if (r11 == 0) goto L_0x005f
            java.util.Deque<androidx.navigation.f> r11 = r8.f724h
            java.lang.Object r11 = r11.peekLast()
            androidx.navigation.f r11 = (androidx.navigation.f) r11
            androidx.navigation.j r11 = r11.d()
            int r11 = r11.d()
            r12 = 1
            boolean r11 = r8.b(r11, r12)
            if (r11 == 0) goto L_0x005f
            goto L_0x002f
        L_0x005f:
            java.util.Deque<androidx.navigation.f> r11 = r8.f724h
            boolean r11 = r11.isEmpty()
            if (r11 == 0) goto L_0x007b
            androidx.navigation.f r11 = new androidx.navigation.f
            android.content.Context r3 = r8.a
            androidx.navigation.k r4 = r8.d
            androidx.lifecycle.LifecycleOwner r6 = r8.f725i
            androidx.navigation.g r7 = r8.f726j
            r2 = r11
            r5 = r10
            r2.<init>(r3, r4, r5, r6, r7)
            java.util.Deque<androidx.navigation.f> r12 = r8.f724h
            r12.add(r11)
        L_0x007b:
            java.util.ArrayDeque r11 = new java.util.ArrayDeque
            r11.<init>()
            r12 = r9
        L_0x0081:
            if (r12 == 0) goto L_0x00a5
            int r1 = r12.d()
            androidx.navigation.j r1 = r8.a((int) r1)
            if (r1 != 0) goto L_0x00a5
            androidx.navigation.k r12 = r12.g()
            if (r12 == 0) goto L_0x0081
            androidx.navigation.f r1 = new androidx.navigation.f
            android.content.Context r3 = r8.a
            androidx.lifecycle.LifecycleOwner r6 = r8.f725i
            androidx.navigation.g r7 = r8.f726j
            r2 = r1
            r4 = r12
            r5 = r10
            r2.<init>(r3, r4, r5, r6, r7)
            r11.addFirst(r1)
            goto L_0x0081
        L_0x00a5:
            java.util.Deque<androidx.navigation.f> r12 = r8.f724h
            r12.addAll(r11)
            androidx.navigation.f r11 = new androidx.navigation.f
            android.content.Context r3 = r8.a
            android.os.Bundle r5 = r9.a((android.os.Bundle) r10)
            androidx.lifecycle.LifecycleOwner r6 = r8.f725i
            androidx.navigation.g r7 = r8.f726j
            r2 = r11
            r4 = r9
            r2.<init>(r3, r4, r5, r6, r7)
            java.util.Deque<androidx.navigation.f> r10 = r8.f724h
            r10.add(r11)
        L_0x00c0:
            r8.k()
            if (r0 != 0) goto L_0x00c7
            if (r9 == 0) goto L_0x00ca
        L_0x00c7:
            r8.i()
        L_0x00ca:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.navigation.NavController.a(androidx.navigation.j, android.os.Bundle, androidx.navigation.o, androidx.navigation.r$a):void");
    }

    public void a(Bundle bundle) {
        if (bundle != null) {
            bundle.setClassLoader(this.a.getClassLoader());
            this.e = bundle.getBundle("android-support-nav:controller:navigatorState");
            this.f722f = bundle.getParcelableArray("android-support-nav:controller:backStack");
            this.f723g = bundle.getBoolean("android-support-nav:controller:deepLinkHandled");
        }
    }

    /* access modifiers changed from: package-private */
    public void a(LifecycleOwner lifecycleOwner) {
        this.f725i = lifecycleOwner;
        lifecycleOwner.getLifecycle().addObserver(this.m);
    }

    /* access modifiers changed from: package-private */
    public void a(OnBackPressedDispatcher onBackPressedDispatcher) {
        if (this.f725i != null) {
            this.n.c();
            onBackPressedDispatcher.a(this.f725i, this.n);
            return;
        }
        throw new IllegalStateException("You must call setLifecycleOwner() before calling setOnBackPressedDispatcher()");
    }

    /* access modifiers changed from: package-private */
    public void a(boolean z) {
        this.o = z;
        k();
    }

    /* access modifiers changed from: package-private */
    public void a(ViewModelStore viewModelStore) {
        if (this.f724h.isEmpty()) {
            this.f726j = g.a(viewModelStore);
            return;
        }
        throw new IllegalStateException("ViewModelStore should be set before setGraph call");
    }
}
