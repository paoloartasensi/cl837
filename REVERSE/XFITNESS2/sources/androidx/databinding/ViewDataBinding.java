package androidx.databinding;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.SparseIntArray;
import android.view.Choreographer;
import android.view.View;
import android.view.ViewGroup;
import androidx.databinding.c;
import androidx.databinding.j;
import androidx.databinding.k;
import androidx.databinding.l;
import androidx.databinding.library.R$id;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.OnLifecycleEvent;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

public abstract class ViewDataBinding extends a {
    static int t = 0;
    private static final int u = 8;
    private static final boolean v;
    private static final i w = new d();
    /* access modifiers changed from: private */
    public static final ReferenceQueue<ViewDataBinding> x = new ReferenceQueue<>();
    /* access modifiers changed from: private */
    public static final View.OnAttachStateChangeListener y;
    /* access modifiers changed from: private */

    /* renamed from: f  reason: collision with root package name */
    public final Runnable f558f;
    /* access modifiers changed from: private */

    /* renamed from: g  reason: collision with root package name */
    public boolean f559g;
    /* access modifiers changed from: private */

    /* renamed from: h  reason: collision with root package name */
    public boolean f560h;

    /* renamed from: i  reason: collision with root package name */
    private n[] f561i;
    /* access modifiers changed from: private */

    /* renamed from: j  reason: collision with root package name */
    public final View f562j;
    private c<m, ViewDataBinding, Void> k;
    private boolean l;
    private Choreographer m;
    private final Choreographer.FrameCallback n;
    private Handler o;
    private ViewDataBinding p;
    private LifecycleOwner q;
    private OnStartListener r;
    private boolean s;

    static class OnStartListener implements LifecycleObserver {
        final WeakReference<ViewDataBinding> e;

        /* synthetic */ OnStartListener(ViewDataBinding viewDataBinding, a aVar) {
            this(viewDataBinding);
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_START)
        public void onStart() {
            ViewDataBinding viewDataBinding = (ViewDataBinding) this.e.get();
            if (viewDataBinding != null) {
                viewDataBinding.b();
            }
        }

        private OnStartListener(ViewDataBinding viewDataBinding) {
            this.e = new WeakReference<>(viewDataBinding);
        }
    }

    static class a implements i {
        a() {
        }

        public n a(ViewDataBinding viewDataBinding, int i2) {
            return new p(viewDataBinding, i2).a();
        }
    }

    static class b implements i {
        b() {
        }

        public n a(ViewDataBinding viewDataBinding, int i2) {
            return new m(viewDataBinding, i2).a();
        }
    }

    static class c implements i {
        c() {
        }

        public n a(ViewDataBinding viewDataBinding, int i2) {
            return new o(viewDataBinding, i2).a();
        }
    }

    static class d implements i {
        d() {
        }

        public n a(ViewDataBinding viewDataBinding, int i2) {
            return new k(viewDataBinding, i2).a();
        }
    }

    static class e extends c.a<m, ViewDataBinding, Void> {
        e() {
        }

        public void a(m mVar, ViewDataBinding viewDataBinding, int i2, Void voidR) {
            if (i2 != 1) {
                if (i2 == 2) {
                    mVar.b(viewDataBinding);
                } else if (i2 == 3) {
                    mVar.a(viewDataBinding);
                }
            } else if (!mVar.c(viewDataBinding)) {
                boolean unused = viewDataBinding.f560h = true;
            }
        }
    }

    static class f implements View.OnAttachStateChangeListener {
        f() {
        }

        @TargetApi(19)
        public void onViewAttachedToWindow(View view) {
            ViewDataBinding.b(view).f558f.run();
            view.removeOnAttachStateChangeListener(this);
        }

        public void onViewDetachedFromWindow(View view) {
        }
    }

    class g implements Runnable {
        g() {
        }

        public void run() {
            synchronized (this) {
                boolean unused = ViewDataBinding.this.f559g = false;
            }
            ViewDataBinding.l();
            if (Build.VERSION.SDK_INT < 19 || ViewDataBinding.this.f562j.isAttachedToWindow()) {
                ViewDataBinding.this.b();
                return;
            }
            ViewDataBinding.this.f562j.removeOnAttachStateChangeListener(ViewDataBinding.y);
            ViewDataBinding.this.f562j.addOnAttachStateChangeListener(ViewDataBinding.y);
        }
    }

    class h implements Choreographer.FrameCallback {
        h() {
        }

        public void doFrame(long j2) {
            ViewDataBinding.this.f558f.run();
        }
    }

    private interface i {
        n a(ViewDataBinding viewDataBinding, int i2);
    }

    protected static class j {
        public final String[][] a;
        public final int[][] b;
        public final int[][] c;

        public j(int i2) {
            this.a = new String[i2][];
            this.b = new int[i2][];
            this.c = new int[i2][];
        }

        public void a(int i2, String[] strArr, int[] iArr, int[] iArr2) {
            this.a[i2] = strArr;
            this.b[i2] = iArr;
            this.c[i2] = iArr2;
        }
    }

    private static class k implements Observer, l<LiveData<?>> {
        final n<LiveData<?>> a;
        LifecycleOwner b;

        public k(ViewDataBinding viewDataBinding, int i2) {
            this.a = new n<>(viewDataBinding, i2, this);
        }

        public void onChanged(Object obj) {
            ViewDataBinding a2 = this.a.a();
            if (a2 != null) {
                n<LiveData<?>> nVar = this.a;
                a2.b(nVar.b, (Object) nVar.b(), 0);
            }
        }

        public void a(LifecycleOwner lifecycleOwner) {
            LiveData b2 = this.a.b();
            if (b2 != null) {
                if (this.b != null) {
                    b2.removeObserver(this);
                }
                if (lifecycleOwner != null) {
                    b2.observe(lifecycleOwner, this);
                }
            }
            this.b = lifecycleOwner;
        }

        public void b(LiveData<?> liveData) {
            liveData.removeObserver(this);
        }

        public n<LiveData<?>> a() {
            return this.a;
        }

        public void a(LiveData<?> liveData) {
            LifecycleOwner lifecycleOwner = this.b;
            if (lifecycleOwner != null) {
                liveData.observe(lifecycleOwner, this);
            }
        }
    }

    private interface l<T> {
        void a(LifecycleOwner lifecycleOwner);

        void a(T t);

        void b(T t);
    }

    private static class m extends k.a implements l<k> {
        final n<k> a;

        public m(ViewDataBinding viewDataBinding, int i2) {
            this.a = new n<>(viewDataBinding, i2, this);
        }

        public void a(LifecycleOwner lifecycleOwner) {
        }

        public n<k> a() {
            return this.a;
        }

        public void b(k kVar) {
            kVar.a(this);
        }

        public void a(k kVar) {
            kVar.b(this);
        }
    }

    private static class n<T> extends WeakReference<ViewDataBinding> {
        private final l<T> a;
        protected final int b;
        private T c;

        public n(ViewDataBinding viewDataBinding, int i2, l<T> lVar) {
            super(viewDataBinding, ViewDataBinding.x);
            this.b = i2;
            this.a = lVar;
        }

        public void a(LifecycleOwner lifecycleOwner) {
            this.a.a(lifecycleOwner);
        }

        public T b() {
            return this.c;
        }

        public boolean c() {
            boolean z;
            T t = this.c;
            if (t != null) {
                this.a.b(t);
                z = true;
            } else {
                z = false;
            }
            this.c = null;
            return z;
        }

        public void a(T t) {
            c();
            this.c = t;
            if (t != null) {
                this.a.a(t);
            }
        }

        /* access modifiers changed from: protected */
        public ViewDataBinding a() {
            ViewDataBinding viewDataBinding = (ViewDataBinding) get();
            if (viewDataBinding == null) {
                c();
            }
            return viewDataBinding;
        }
    }

    private static class o extends l.a implements l<l> {
        final n<l> a;

        public o(ViewDataBinding viewDataBinding, int i2) {
            this.a = new n<>(viewDataBinding, i2, this);
        }

        public void a(LifecycleOwner lifecycleOwner) {
        }

        public n<l> a() {
            return this.a;
        }

        public void b(l lVar) {
            lVar.b(this);
        }

        public void a(l lVar) {
            lVar.a(this);
        }
    }

    private static class p extends j.a implements l<j> {
        final n<j> a;

        public p(ViewDataBinding viewDataBinding, int i2) {
            this.a = new n<>(viewDataBinding, i2, this);
        }

        public void a(LifecycleOwner lifecycleOwner) {
        }

        public n<j> a() {
            return this.a;
        }

        public void b(j jVar) {
            jVar.removeOnPropertyChangedCallback(this);
        }

        public void a(j jVar) {
            jVar.addOnPropertyChangedCallback(this);
        }

        public void a(j jVar, int i2) {
            ViewDataBinding a2 = this.a.a();
            if (a2 != null && this.a.b() == jVar) {
                a2.b(this.a.b, (Object) jVar, i2);
            }
        }
    }

    static {
        int i2 = Build.VERSION.SDK_INT;
        t = i2;
        v = i2 >= 16;
        new a();
        new b();
        new c();
        new e();
        if (Build.VERSION.SDK_INT < 19) {
            y = null;
        } else {
            y = new f();
        }
    }

    protected ViewDataBinding(f fVar, View view, int i2) {
        this.f558f = new g();
        this.f559g = false;
        this.f560h = false;
        this.f561i = new n[i2];
        this.f562j = view;
        if (Looper.myLooper() == null) {
            throw new IllegalStateException("DataBinding must be created in view's UI Thread");
        } else if (v) {
            this.m = Choreographer.getInstance();
            this.n = new h();
        } else {
            this.n = null;
            this.o = new Handler(Looper.myLooper());
        }
    }

    protected static void d(ViewDataBinding viewDataBinding) {
        viewDataBinding.k();
    }

    private void k() {
        if (this.l) {
            f();
        } else if (d()) {
            this.l = true;
            this.f560h = false;
            c<m, ViewDataBinding, Void> cVar = this.k;
            if (cVar != null) {
                cVar.a(this, 1, null);
                if (this.f560h) {
                    this.k.a(this, 2, null);
                }
            }
            if (!this.f560h) {
                a();
                c<m, ViewDataBinding, Void> cVar2 = this.k;
                if (cVar2 != null) {
                    cVar2.a(this, 3, null);
                }
            }
            this.l = false;
        }
    }

    /* access modifiers changed from: private */
    public static void l() {
        while (true) {
            Reference<? extends ViewDataBinding> poll = x.poll();
            if (poll == null) {
                return;
            }
            if (poll instanceof n) {
                ((n) poll).c();
            }
        }
    }

    /* access modifiers changed from: protected */
    public abstract void a();

    /* access modifiers changed from: protected */
    public abstract boolean a(int i2, Object obj, int i3);

    public abstract boolean d();

    public abstract void e();

    /* access modifiers changed from: protected */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x002a, code lost:
        if (v == false) goto L_0x0034;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x002c, code lost:
        r2.m.postFrameCallback(r2.n);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0034, code lost:
        r2.o.post(r2.f558f);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:?, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void f() {
        /*
            r2 = this;
            androidx.databinding.ViewDataBinding r0 = r2.p
            if (r0 == 0) goto L_0x0008
            r0.f()
            goto L_0x003b
        L_0x0008:
            androidx.lifecycle.LifecycleOwner r0 = r2.q
            if (r0 == 0) goto L_0x001d
            androidx.lifecycle.Lifecycle r0 = r0.getLifecycle()
            androidx.lifecycle.Lifecycle$State r0 = r0.getCurrentState()
            androidx.lifecycle.Lifecycle$State r1 = androidx.lifecycle.Lifecycle.State.STARTED
            boolean r0 = r0.isAtLeast(r1)
            if (r0 != 0) goto L_0x001d
            return
        L_0x001d:
            monitor-enter(r2)
            boolean r0 = r2.f559g     // Catch:{ all -> 0x003c }
            if (r0 == 0) goto L_0x0024
            monitor-exit(r2)     // Catch:{ all -> 0x003c }
            return
        L_0x0024:
            r0 = 1
            r2.f559g = r0     // Catch:{ all -> 0x003c }
            monitor-exit(r2)     // Catch:{ all -> 0x003c }
            boolean r0 = v
            if (r0 == 0) goto L_0x0034
            android.view.Choreographer r0 = r2.m
            android.view.Choreographer$FrameCallback r1 = r2.n
            r0.postFrameCallback(r1)
            goto L_0x003b
        L_0x0034:
            android.os.Handler r0 = r2.o
            java.lang.Runnable r1 = r2.f558f
            r0.post(r1)
        L_0x003b:
            return
        L_0x003c:
            r0 = move-exception
            monitor-exit(r2)     // Catch:{ all -> 0x003c }
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.databinding.ViewDataBinding.f():void");
    }

    public void g() {
        for (n nVar : this.f561i) {
            if (nVar != null) {
                nVar.c();
            }
        }
    }

    public View c() {
        return this.f562j;
    }

    private static f a(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof f) {
            return (f) obj;
        }
        throw new IllegalArgumentException("The provided bindingComponent parameter must be an instance of DataBindingComponent. See  https://issuetracker.google.com/issues/116541301 for details of why this parameter is not defined as DataBindingComponent");
    }

    public void b() {
        ViewDataBinding viewDataBinding = this.p;
        if (viewDataBinding == null) {
            k();
        } else {
            viewDataBinding.b();
        }
    }

    static ViewDataBinding b(View view) {
        if (view != null) {
            return (ViewDataBinding) view.getTag(R$id.dataBinding);
        }
        return null;
    }

    /* access modifiers changed from: protected */
    public void a(View view) {
        view.setTag(R$id.dataBinding, this);
    }

    /* access modifiers changed from: private */
    public void b(int i2, Object obj, int i3) {
        if (!this.s && a(i2, obj, i3)) {
            f();
        }
    }

    public void a(LifecycleOwner lifecycleOwner) {
        LifecycleOwner lifecycleOwner2 = this.q;
        if (lifecycleOwner2 != lifecycleOwner) {
            if (lifecycleOwner2 != null) {
                lifecycleOwner2.getLifecycle().removeObserver(this.r);
            }
            this.q = lifecycleOwner;
            if (lifecycleOwner != null) {
                if (this.r == null) {
                    this.r = new OnStartListener(this, (a) null);
                }
                lifecycleOwner.getLifecycle().addObserver(this.r);
            }
            for (n nVar : this.f561i) {
                if (nVar != null) {
                    nVar.a(lifecycleOwner);
                }
            }
        }
    }

    private boolean b(int i2, Object obj, i iVar) {
        if (obj == null) {
            return a(i2);
        }
        n nVar = this.f561i[i2];
        if (nVar == null) {
            a(i2, obj, iVar);
            return true;
        } else if (nVar.b() == obj) {
            return false;
        } else {
            a(i2);
            a(i2, obj, iVar);
            return true;
        }
    }

    protected ViewDataBinding(Object obj, View view, int i2) {
        this(a(obj), view, i2);
    }

    /* access modifiers changed from: protected */
    public boolean a(int i2) {
        n nVar = this.f561i[i2];
        if (nVar != null) {
            return nVar.c();
        }
        return false;
    }

    private static int b(String str, int i2) {
        int length = str.length();
        int i3 = 0;
        while (i2 < length) {
            i3 = (i3 * 10) + (str.charAt(i2) - '0');
            i2++;
        }
        return i3;
    }

    /* access modifiers changed from: protected */
    public boolean a(int i2, LiveData<?> liveData) {
        this.s = true;
        try {
            return b(i2, (Object) liveData, w);
        } finally {
            this.s = false;
        }
    }

    /* access modifiers changed from: protected */
    public void a(int i2, Object obj, i iVar) {
        if (obj != null) {
            n nVar = this.f561i[i2];
            if (nVar == null) {
                nVar = iVar.a(this, i2);
                this.f561i[i2] = nVar;
                LifecycleOwner lifecycleOwner = this.q;
                if (lifecycleOwner != null) {
                    nVar.a(lifecycleOwner);
                }
            }
            nVar.a(obj);
        }
    }

    protected static Object[] a(f fVar, View view, int i2, j jVar, SparseIntArray sparseIntArray) {
        Object[] objArr = new Object[i2];
        a(fVar, view, objArr, jVar, sparseIntArray, true);
        return objArr;
    }

    protected static int a(Integer num) {
        if (num == null) {
            return 0;
        }
        return num.intValue();
    }

    protected static boolean a(Boolean bool) {
        if (bool == null) {
            return false;
        }
        return bool.booleanValue();
    }

    /* access modifiers changed from: protected */
    public void a(ViewDataBinding viewDataBinding) {
        if (viewDataBinding != null) {
            viewDataBinding.p = this;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:69:0x00fe  */
    /* JADX WARNING: Removed duplicated region for block: B:73:0x010b A[SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static void a(androidx.databinding.f r16, android.view.View r17, java.lang.Object[] r18, androidx.databinding.ViewDataBinding.j r19, android.util.SparseIntArray r20, boolean r21) {
        /*
            r6 = r16
            r0 = r17
            r7 = r19
            r8 = r20
            androidx.databinding.ViewDataBinding r1 = b((android.view.View) r17)
            if (r1 == 0) goto L_0x000f
            return
        L_0x000f:
            java.lang.Object r1 = r17.getTag()
            boolean r2 = r1 instanceof java.lang.String
            if (r2 == 0) goto L_0x001a
            java.lang.String r1 = (java.lang.String) r1
            goto L_0x001b
        L_0x001a:
            r1 = 0
        L_0x001b:
            java.lang.String r9 = "layout"
            r2 = -1
            r11 = 1
            if (r21 == 0) goto L_0x004b
            if (r1 == 0) goto L_0x004b
            boolean r3 = r1.startsWith(r9)
            if (r3 == 0) goto L_0x004b
            r3 = 95
            int r3 = r1.lastIndexOf(r3)
            if (r3 <= 0) goto L_0x0047
            int r3 = r3 + r11
            boolean r4 = a((java.lang.String) r1, (int) r3)
            if (r4 == 0) goto L_0x0047
            int r1 = b((java.lang.String) r1, (int) r3)
            r3 = r18[r1]
            if (r3 != 0) goto L_0x0042
            r18[r1] = r0
        L_0x0042:
            if (r7 != 0) goto L_0x0045
            r1 = -1
        L_0x0045:
            r3 = 1
            goto L_0x0049
        L_0x0047:
            r1 = -1
            r3 = 0
        L_0x0049:
            r12 = r1
            goto L_0x0069
        L_0x004b:
            if (r1 == 0) goto L_0x0067
            java.lang.String r3 = "binding_"
            boolean r3 = r1.startsWith(r3)
            if (r3 == 0) goto L_0x0067
            int r3 = u
            int r1 = b((java.lang.String) r1, (int) r3)
            r3 = r18[r1]
            if (r3 != 0) goto L_0x0061
            r18[r1] = r0
        L_0x0061:
            if (r7 != 0) goto L_0x0064
            r1 = -1
        L_0x0064:
            r12 = r1
            r3 = 1
            goto L_0x0069
        L_0x0067:
            r3 = 0
            r12 = -1
        L_0x0069:
            if (r3 != 0) goto L_0x007f
            int r1 = r17.getId()
            if (r1 <= 0) goto L_0x007f
            if (r8 == 0) goto L_0x007f
            int r1 = r8.get(r1, r2)
            if (r1 < 0) goto L_0x007f
            r2 = r18[r1]
            if (r2 != 0) goto L_0x007f
            r18[r1] = r0
        L_0x007f:
            boolean r1 = r0 instanceof android.view.ViewGroup
            if (r1 == 0) goto L_0x0113
            r13 = r0
            android.view.ViewGroup r13 = (android.view.ViewGroup) r13
            int r14 = r13.getChildCount()
            r0 = 0
            r1 = 0
        L_0x008c:
            if (r0 >= r14) goto L_0x0113
            android.view.View r2 = r13.getChildAt(r0)
            if (r12 < 0) goto L_0x00f9
            java.lang.Object r3 = r2.getTag()
            boolean r3 = r3 instanceof java.lang.String
            if (r3 == 0) goto L_0x00f9
            java.lang.Object r3 = r2.getTag()
            java.lang.String r3 = (java.lang.String) r3
            java.lang.String r4 = "_0"
            boolean r4 = r3.endsWith(r4)
            if (r4 == 0) goto L_0x00f9
            boolean r4 = r3.startsWith(r9)
            if (r4 == 0) goto L_0x00f9
            r4 = 47
            int r4 = r3.indexOf(r4)
            if (r4 <= 0) goto L_0x00f9
            int r3 = a((java.lang.String) r3, (int) r1, (androidx.databinding.ViewDataBinding.j) r7, (int) r12)
            if (r3 < 0) goto L_0x00f9
            int r1 = r3 + 1
            int[][] r4 = r7.b
            r4 = r4[r12]
            r4 = r4[r3]
            int[][] r5 = r7.c
            r5 = r5[r12]
            r3 = r5[r3]
            int r5 = a((android.view.ViewGroup) r13, (int) r0)
            if (r5 != r0) goto L_0x00dc
            androidx.databinding.ViewDataBinding r3 = androidx.databinding.g.a((androidx.databinding.f) r6, (android.view.View) r2, (int) r3)
            r18[r4] = r3
        L_0x00d8:
            r10 = r0
            r11 = r1
            r0 = 1
            goto L_0x00fc
        L_0x00dc:
            int r5 = r5 - r0
            int r5 = r5 + r11
            android.view.View[] r15 = new android.view.View[r5]
            r10 = 0
        L_0x00e1:
            if (r10 >= r5) goto L_0x00ef
            int r11 = r0 + r10
            android.view.View r11 = r13.getChildAt(r11)
            r15[r10] = r11
            int r10 = r10 + 1
            r11 = 1
            goto L_0x00e1
        L_0x00ef:
            androidx.databinding.ViewDataBinding r3 = androidx.databinding.g.a((androidx.databinding.f) r6, (android.view.View[]) r15, (int) r3)
            r18[r4] = r3
            int r5 = r5 + -1
            int r0 = r0 + r5
            goto L_0x00d8
        L_0x00f9:
            r10 = r0
            r11 = r1
            r0 = 0
        L_0x00fc:
            if (r0 != 0) goto L_0x010b
            r5 = 0
            r0 = r16
            r1 = r2
            r2 = r18
            r3 = r19
            r4 = r20
            a(r0, r1, r2, r3, r4, r5)
        L_0x010b:
            r0 = 1
            int r1 = r10 + 1
            r0 = r1
            r1 = r11
            r11 = 1
            goto L_0x008c
        L_0x0113:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.databinding.ViewDataBinding.a(androidx.databinding.f, android.view.View, java.lang.Object[], androidx.databinding.ViewDataBinding$j, android.util.SparseIntArray, boolean):void");
    }

    private static int a(String str, int i2, j jVar, int i3) {
        CharSequence subSequence = str.subSequence(str.indexOf(47) + 1, str.length() - 2);
        String[] strArr = jVar.a[i3];
        int length = strArr.length;
        while (i2 < length) {
            if (TextUtils.equals(subSequence, strArr[i2])) {
                return i2;
            }
            i2++;
        }
        return -1;
    }

    private static int a(ViewGroup viewGroup, int i2) {
        String str = (String) viewGroup.getChildAt(i2).getTag();
        String substring = str.substring(0, str.length() - 1);
        int length = substring.length();
        int childCount = viewGroup.getChildCount();
        for (int i3 = i2 + 1; i3 < childCount; i3++) {
            View childAt = viewGroup.getChildAt(i3);
            String str2 = childAt.getTag() instanceof String ? (String) childAt.getTag() : null;
            if (str2 != null && str2.startsWith(substring)) {
                if (str2.length() == str.length() && str2.charAt(str2.length() - 1) == '0') {
                    return i2;
                }
                if (a(str2, length)) {
                    i2 = i3;
                }
            }
        }
        return i2;
    }

    private static boolean a(String str, int i2) {
        int length = str.length();
        if (length == i2) {
            return false;
        }
        while (i2 < length) {
            if (!Character.isDigit(str.charAt(i2))) {
                return false;
            }
            i2++;
        }
        return true;
    }
}
