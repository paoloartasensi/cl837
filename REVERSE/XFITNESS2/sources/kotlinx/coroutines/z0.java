package kotlinx.coroutines;

import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import kotlin.TypeCastException;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.internal.i;
import kotlin.l;
import kotlinx.coroutines.internal.k;
import kotlinx.coroutines.internal.x;
import kotlinx.coroutines.internal.y;

/* compiled from: EventLoop.common.kt */
public abstract class z0 extends a1 implements o0 {

    /* renamed from: h  reason: collision with root package name */
    private static final AtomicReferenceFieldUpdater f1846h;

    /* renamed from: i  reason: collision with root package name */
    private static final AtomicReferenceFieldUpdater f1847i;
    private volatile Object _delayed = null;
    private volatile Object _queue = null;
    /* access modifiers changed from: private */
    public volatile boolean isCompleted;

    /* compiled from: EventLoop.common.kt */
    private final class a extends b {

        /* renamed from: h  reason: collision with root package name */
        private final h<l> f1848h;

        /* renamed from: i  reason: collision with root package name */
        final /* synthetic */ z0 f1849i;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public a(z0 z0Var, long j2, h<? super l> hVar) {
            super(j2);
            i.b(hVar, "cont");
            this.f1849i = z0Var;
            this.f1848h = hVar;
        }

        public void run() {
            this.f1848h.a(this.f1849i, l.a);
        }

        public String toString() {
            return super.toString() + this.f1848h.toString();
        }
    }

    /* compiled from: EventLoop.common.kt */
    public static final class c extends x<b> {
        public long b;

        public c(long j2) {
            this.b = j2;
        }
    }

    static {
        Class<Object> cls = Object.class;
        Class<z0> cls2 = z0.class;
        f1846h = AtomicReferenceFieldUpdater.newUpdater(cls2, cls, "_queue");
        f1847i = AtomicReferenceFieldUpdater.newUpdater(cls2, cls, "_delayed");
    }

    private final int c(long j2, b bVar) {
        if (this.isCompleted) {
            return 1;
        }
        c cVar = (c) this._delayed;
        if (cVar == null) {
            f1847i.compareAndSet(this, (Object) null, new c(j2));
            Object obj = this._delayed;
            if (obj != null) {
                cVar = (c) obj;
            } else {
                i.a();
                throw null;
            }
        }
        return bVar.a(j2, cVar, this);
    }

    private final void x() {
        if (!j0.a() || this.isCompleted) {
            while (true) {
                Object obj = this._queue;
                if (obj == null) {
                    if (f1846h.compareAndSet(this, (Object) null, c1.b)) {
                        return;
                    }
                } else if (obj instanceof k) {
                    ((k) obj).a();
                    return;
                } else if (obj != c1.b) {
                    k kVar = new k(8, true);
                    if (obj != null) {
                        kVar.a((Runnable) obj);
                        if (f1846h.compareAndSet(this, obj, kVar)) {
                            return;
                        }
                    } else {
                        throw new TypeCastException("null cannot be cast to non-null type kotlinx.coroutines.Runnable /* = java.lang.Runnable */");
                    }
                } else {
                    return;
                }
            }
        } else {
            throw new AssertionError();
        }
    }

    private final Runnable y() {
        while (true) {
            Object obj = this._queue;
            if (obj == null) {
                return null;
            }
            if (obj instanceof k) {
                if (obj != null) {
                    k kVar = (k) obj;
                    Object e = kVar.e();
                    if (e != k.f1801g) {
                        return (Runnable) e;
                    }
                    f1846h.compareAndSet(this, obj, kVar.d());
                } else {
                    throw new TypeCastException("null cannot be cast to non-null type kotlinx.coroutines.Queue<kotlinx.coroutines.Runnable /* = java.lang.Runnable */> /* = kotlinx.coroutines.internal.LockFreeTaskQueueCore<kotlinx.coroutines.Runnable /* = java.lang.Runnable */> */");
                }
            } else if (obj == c1.b) {
                return null;
            } else {
                if (f1846h.compareAndSet(this, obj, (Object) null)) {
                    if (obj != null) {
                        return (Runnable) obj;
                    }
                    throw new TypeCastException("null cannot be cast to non-null type kotlinx.coroutines.Runnable /* = java.lang.Runnable */");
                }
            }
        }
    }

    private final void z() {
        b bVar;
        e2 a2 = f2.a();
        long e = a2 != null ? a2.e() : System.nanoTime();
        while (true) {
            c cVar = (c) this._delayed;
            if (cVar != null && (bVar = (b) cVar.e()) != null) {
                a(e, bVar);
            } else {
                return;
            }
        }
    }

    public final void b(long j2, b bVar) {
        i.b(bVar, "delayedTask");
        int c2 = c(j2, bVar);
        if (c2 != 0) {
            if (c2 == 1) {
                a(j2, bVar);
            } else if (c2 != 2) {
                throw new IllegalStateException("unexpected result".toString());
            }
        } else if (a(bVar)) {
            t();
        }
    }

    public final void dispatch(CoroutineContext coroutineContext, Runnable runnable) {
        i.b(coroutineContext, "context");
        i.b(runnable, "block");
        a(runnable);
    }

    /* access modifiers changed from: protected */
    public long n() {
        b bVar;
        if (super.n() == 0) {
            return 0;
        }
        Object obj = this._queue;
        if (obj != null) {
            if (obj instanceof k) {
                if (!((k) obj).c()) {
                    return 0;
                }
            } else if (obj == c1.b) {
                return Long.MAX_VALUE;
            } else {
                return 0;
            }
        }
        c cVar = (c) this._delayed;
        if (cVar == null || (bVar = (b) cVar.d()) == null) {
            return Long.MAX_VALUE;
        }
        long j2 = bVar.f1851g;
        e2 a2 = f2.a();
        return f.a(j2 - (a2 != null ? a2.e() : System.nanoTime()), 0);
    }

    /* access modifiers changed from: protected */
    public void r() {
        d2.b.b();
        this.isCompleted = true;
        x();
        do {
        } while (v() <= 0);
        z();
    }

    /* access modifiers changed from: protected */
    public boolean u() {
        if (!p()) {
            return false;
        }
        c cVar = (c) this._delayed;
        if (cVar != null && !cVar.c()) {
            return false;
        }
        Object obj = this._queue;
        if (obj != null) {
            if (obj instanceof k) {
                return ((k) obj).c();
            }
            if (obj == c1.b) {
                return true;
            }
            return false;
        }
        return true;
    }

    /* JADX WARNING: Removed duplicated region for block: B:32:0x0055  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public long v() {
        /*
            r7 = this;
            boolean r0 = r7.q()
            if (r0 == 0) goto L_0x000b
            long r0 = r7.n()
            return r0
        L_0x000b:
            java.lang.Object r0 = r7._delayed
            kotlinx.coroutines.z0$c r0 = (kotlinx.coroutines.z0.c) r0
            if (r0 == 0) goto L_0x004f
            boolean r1 = r0.c()
            if (r1 != 0) goto L_0x004f
            kotlinx.coroutines.e2 r1 = kotlinx.coroutines.f2.a()
            if (r1 == 0) goto L_0x0022
            long r1 = r1.e()
            goto L_0x0026
        L_0x0022:
            long r1 = java.lang.System.nanoTime()
        L_0x0026:
            monitor-enter(r0)
            kotlinx.coroutines.internal.y r3 = r0.a()     // Catch:{ all -> 0x004c }
            r4 = 0
            if (r3 == 0) goto L_0x0046
            kotlinx.coroutines.z0$b r3 = (kotlinx.coroutines.z0.b) r3     // Catch:{ all -> 0x004c }
            boolean r5 = r3.a((long) r1)     // Catch:{ all -> 0x004c }
            r6 = 0
            if (r5 == 0) goto L_0x003c
            boolean r3 = r7.b(r3)     // Catch:{ all -> 0x004c }
            goto L_0x003d
        L_0x003c:
            r3 = 0
        L_0x003d:
            if (r3 == 0) goto L_0x0044
            kotlinx.coroutines.internal.y r3 = r0.a((int) r6)     // Catch:{ all -> 0x004c }
            r4 = r3
        L_0x0044:
            monitor-exit(r0)
            goto L_0x0047
        L_0x0046:
            monitor-exit(r0)
        L_0x0047:
            kotlinx.coroutines.z0$b r4 = (kotlinx.coroutines.z0.b) r4
            if (r4 == 0) goto L_0x004f
            goto L_0x0026
        L_0x004c:
            r1 = move-exception
            monitor-exit(r0)
            throw r1
        L_0x004f:
            java.lang.Runnable r0 = r7.y()
            if (r0 == 0) goto L_0x0058
            r0.run()
        L_0x0058:
            long r0 = r7.n()
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.z0.v():long");
    }

    /* access modifiers changed from: protected */
    public final void w() {
        this._queue = null;
        this._delayed = null;
    }

    public void a(long j2, h<? super l> hVar) {
        i.b(hVar, "continuation");
        long a2 = c1.a(j2);
        if (a2 < 4611686018427387903L) {
            e2 a3 = f2.a();
            long e = a3 != null ? a3.e() : System.nanoTime();
            a aVar = new a(this, a2 + e, hVar);
            j.a(hVar, aVar);
            b(e, aVar);
        }
    }

    /* compiled from: EventLoop.common.kt */
    public static abstract class b implements Runnable, Comparable<b>, v0, y {
        private Object e;

        /* renamed from: f  reason: collision with root package name */
        private int f1850f = -1;

        /* renamed from: g  reason: collision with root package name */
        public long f1851g;

        public b(long j2) {
            this.f1851g = j2;
        }

        public void a(x<?> xVar) {
            if (this.e != c1.a) {
                this.e = xVar;
                return;
            }
            throw new IllegalArgumentException("Failed requirement.".toString());
        }

        public x<?> b() {
            Object obj = this.e;
            if (!(obj instanceof x)) {
                obj = null;
            }
            return (x) obj;
        }

        public int c() {
            return this.f1850f;
        }

        public String toString() {
            return "Delayed[nanos=" + this.f1851g + ']';
        }

        public void a(int i2) {
            this.f1850f = i2;
        }

        /* renamed from: a */
        public int compareTo(b bVar) {
            i.b(bVar, "other");
            long j2 = this.f1851g - bVar.f1851g;
            if (j2 > 0) {
                return 1;
            }
            return j2 < 0 ? -1 : 0;
        }

        public final boolean a(long j2) {
            return j2 - this.f1851g >= 0;
        }

        public final synchronized int a(long j2, c cVar, z0 z0Var) {
            i.b(cVar, "delayed");
            i.b(z0Var, "eventLoop");
            if (this.e == c1.a) {
                return 2;
            }
            synchronized (cVar) {
                b bVar = (b) cVar.a();
                if (z0Var.isCompleted) {
                    return 1;
                }
                if (bVar == null) {
                    cVar.b = j2;
                } else {
                    long j3 = bVar.f1851g;
                    if (j3 - j2 < 0) {
                        j2 = j3;
                    }
                    if (j2 - cVar.b > 0) {
                        cVar.b = j2;
                    }
                }
                if (this.f1851g - cVar.b < 0) {
                    this.f1851g = cVar.b;
                }
                cVar.a(this);
                return 0;
            }
        }

        public final synchronized void a() {
            Object obj = this.e;
            if (obj != c1.a) {
                if (!(obj instanceof c)) {
                    obj = null;
                }
                c cVar = (c) obj;
                if (cVar != null) {
                    cVar.b(this);
                }
                this.e = c1.a;
            }
        }
    }

    private final boolean b(Runnable runnable) {
        while (true) {
            Object obj = this._queue;
            if (this.isCompleted) {
                return false;
            }
            if (obj == null) {
                if (f1846h.compareAndSet(this, (Object) null, runnable)) {
                    return true;
                }
            } else if (obj instanceof k) {
                if (obj != null) {
                    k kVar = (k) obj;
                    int a2 = kVar.a(runnable);
                    if (a2 == 0) {
                        return true;
                    }
                    if (a2 == 1) {
                        f1846h.compareAndSet(this, obj, kVar.d());
                    } else if (a2 == 2) {
                        return false;
                    }
                } else {
                    throw new TypeCastException("null cannot be cast to non-null type kotlinx.coroutines.Queue<kotlinx.coroutines.Runnable /* = java.lang.Runnable */> /* = kotlinx.coroutines.internal.LockFreeTaskQueueCore<kotlinx.coroutines.Runnable /* = java.lang.Runnable */> */");
                }
            } else if (obj == c1.b) {
                return false;
            } else {
                k kVar2 = new k(8, true);
                if (obj != null) {
                    kVar2.a((Runnable) obj);
                    kVar2.a(runnable);
                    if (f1846h.compareAndSet(this, obj, kVar2)) {
                        return true;
                    }
                } else {
                    throw new TypeCastException("null cannot be cast to non-null type kotlinx.coroutines.Runnable /* = java.lang.Runnable */");
                }
            }
        }
    }

    public final void a(Runnable runnable) {
        i.b(runnable, "task");
        if (b(runnable)) {
            t();
        } else {
            l0.k.a(runnable);
        }
    }

    private final boolean a(b bVar) {
        c cVar = (c) this._delayed;
        return (cVar != null ? (b) cVar.d() : null) == bVar;
    }
}
