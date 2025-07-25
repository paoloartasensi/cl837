package kotlinx.coroutines;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CancellationException;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import kotlin.TypeCastException;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.b.p;
import kotlin.jvm.internal.i;
import kotlin.l;
import kotlinx.coroutines.internal.d;
import kotlinx.coroutines.internal.h;
import kotlinx.coroutines.internal.i;
import kotlinx.coroutines.internal.o;
import kotlinx.coroutines.internal.s;
import kotlinx.coroutines.k1;

/* compiled from: JobSupport.kt */
public class q1 implements k1, o, x1 {
    private static final AtomicReferenceFieldUpdater e = AtomicReferenceFieldUpdater.newUpdater(q1.class, Object.class, "_state");
    private volatile Object _state;
    public volatile m parentHandle;

    /* compiled from: JobSupport.kt */
    private static final class a extends p1<k1> {

        /* renamed from: i  reason: collision with root package name */
        private final q1 f1816i;

        /* renamed from: j  reason: collision with root package name */
        private final b f1817j;
        private final n k;
        private final Object l;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public a(q1 q1Var, b bVar, n nVar, Object obj) {
            super(nVar.f1809i);
            i.b(q1Var, "parent");
            i.b(bVar, "state");
            i.b(nVar, "child");
            this.f1816i = q1Var;
            this.f1817j = bVar;
            this.k = nVar;
            this.l = obj;
        }

        public void b(Throwable th) {
            this.f1816i.a(this.f1817j, this.k, this.l);
        }

        public /* bridge */ /* synthetic */ Object invoke(Object obj) {
            b((Throwable) obj);
            return l.a;
        }

        public String toString() {
            return "ChildCompletion[" + this.k + ", " + this.l + ']';
        }
    }

    /* compiled from: JobSupport.kt */
    private static final class b implements f1 {
        private volatile Object _exceptionsHolder;
        private final u1 e;
        public volatile boolean isCompleting;
        public volatile Throwable rootCause;

        public b(u1 u1Var, boolean z, Throwable th) {
            i.b(u1Var, "list");
            this.e = u1Var;
            this.isCompleting = z;
            this.rootCause = th;
        }

        private final ArrayList<Throwable> d() {
            return new ArrayList<>(4);
        }

        public final boolean a() {
            return this.rootCause != null;
        }

        public u1 b() {
            return this.e;
        }

        public final boolean c() {
            return this._exceptionsHolder == r1.a;
        }

        public boolean isActive() {
            return this.rootCause == null;
        }

        public String toString() {
            return "Finishing[cancelling=" + a() + ", completing=" + this.isCompleting + ", rootCause=" + this.rootCause + ", exceptions=" + this._exceptionsHolder + ", list=" + b() + ']';
        }

        public final void a(Throwable th) {
            i.b(th, "exception");
            Throwable th2 = this.rootCause;
            if (th2 == null) {
                this.rootCause = th;
            } else if (th != th2) {
                Object obj = this._exceptionsHolder;
                if (obj == null) {
                    this._exceptionsHolder = th;
                } else if (obj instanceof Throwable) {
                    if (th != obj) {
                        ArrayList<Throwable> d = d();
                        d.add(obj);
                        d.add(th);
                        this._exceptionsHolder = d;
                    }
                } else if (obj instanceof ArrayList) {
                    ((ArrayList) obj).add(th);
                } else {
                    throw new IllegalStateException(("State is " + obj).toString());
                }
            }
        }

        public final List<Throwable> b(Throwable th) {
            ArrayList<Throwable> arrayList;
            Object obj = this._exceptionsHolder;
            if (obj == null) {
                arrayList = d();
            } else if (obj instanceof Throwable) {
                ArrayList<Throwable> d = d();
                d.add(obj);
                arrayList = d;
            } else if (obj instanceof ArrayList) {
                arrayList = (ArrayList) obj;
            } else {
                throw new IllegalStateException(("State is " + obj).toString());
            }
            Throwable th2 = this.rootCause;
            if (th2 != null) {
                arrayList.add(0, th2);
            }
            if (th != null && (!i.a((Object) th, (Object) th2))) {
                arrayList.add(th);
            }
            this._exceptionsHolder = r1.a;
            return arrayList;
        }
    }

    /* compiled from: LockFreeLinkedList.kt */
    public static final class c extends i.a {
        final /* synthetic */ q1 d;
        final /* synthetic */ Object e;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public c(kotlinx.coroutines.internal.i iVar, kotlinx.coroutines.internal.i iVar2, q1 q1Var, Object obj) {
            super(iVar2);
            this.d = q1Var;
            this.e = obj;
        }

        /* renamed from: a */
        public Object b(kotlinx.coroutines.internal.i iVar) {
            kotlin.jvm.internal.i.b(iVar, "affected");
            if (this.d.e() == this.e) {
                return null;
            }
            return h.a();
        }
    }

    public q1(boolean z) {
        this._state = z ? r1.c : r1.b;
    }

    private final boolean b(f1 f1Var, Object obj, int i2) {
        if (j0.a()) {
            if (!((f1Var instanceof x0) || (f1Var instanceof p1))) {
                throw new AssertionError();
            }
        }
        if (j0.a() && !(!(obj instanceof u))) {
            throw new AssertionError();
        } else if (!e.compareAndSet(this, f1Var, r1.a(obj))) {
            return false;
        } else {
            f((Throwable) null);
            d(obj);
            a(f1Var, obj, i2);
            return true;
        }
    }

    private final boolean g(Throwable th) {
        if (g()) {
            return true;
        }
        boolean z = th instanceof CancellationException;
        m mVar = this.parentHandle;
        if (mVar == null || mVar == v1.e) {
            return z;
        }
        if (mVar.a(th) || z) {
            return true;
        }
        return false;
    }

    private final int i(Object obj) {
        if (obj instanceof x0) {
            if (((x0) obj).isActive()) {
                return 0;
            }
            if (!e.compareAndSet(this, obj, r1.c)) {
                return -1;
            }
            i();
            return 1;
        } else if (!(obj instanceof e1)) {
            return 0;
        } else {
            if (!e.compareAndSet(this, obj, ((e1) obj).b())) {
                return -1;
            }
            i();
            return 1;
        }
    }

    private final JobCancellationException k() {
        return new JobCancellationException("Job was cancelled", (Throwable) null, this);
    }

    /* access modifiers changed from: protected */
    public void a(Object obj, int i2) {
    }

    public boolean b() {
        return true;
    }

    public final CancellationException c() {
        Object e2 = e();
        if (e2 instanceof b) {
            Throwable th = ((b) e2).rootCause;
            if (th != null) {
                CancellationException a2 = a(th, k0.a((Object) this) + " is cancelling");
                if (a2 != null) {
                    return a2;
                }
            }
            throw new IllegalStateException(("Job is still new or active: " + this).toString());
        } else if (e2 instanceof f1) {
            throw new IllegalStateException(("Job is still new or active: " + this).toString());
        } else if (e2 instanceof u) {
            return a(this, ((u) e2).a, (String) null, 1, (Object) null);
        } else {
            return new JobCancellationException(k0.a((Object) this) + " has completed normally", (Throwable) null, this);
        }
    }

    /* access modifiers changed from: protected */
    public void d(Object obj) {
    }

    public boolean d() {
        return false;
    }

    /* access modifiers changed from: protected */
    public boolean d(Throwable th) {
        kotlin.jvm.internal.i.b(th, "exception");
        return false;
    }

    public void e(Throwable th) {
        kotlin.jvm.internal.i.b(th, "exception");
        throw th;
    }

    /* access modifiers changed from: protected */
    public void f(Throwable th) {
    }

    public final boolean f() {
        return !(e() instanceof f1);
    }

    public <R> R fold(R r, p<? super R, ? super CoroutineContext.a, ? extends R> pVar) {
        kotlin.jvm.internal.i.b(pVar, "operation");
        return k1.a.a(this, r, pVar);
    }

    /* access modifiers changed from: protected */
    public boolean g() {
        return false;
    }

    public <E extends CoroutineContext.a> E get(CoroutineContext.b<E> bVar) {
        kotlin.jvm.internal.i.b(bVar, "key");
        return k1.a.a((k1) this, bVar);
    }

    public final CoroutineContext.b<?> getKey() {
        return k1.d;
    }

    public String h() {
        return k0.a((Object) this);
    }

    public void i() {
    }

    public boolean isActive() {
        Object e2 = e();
        return (e2 instanceof f1) && ((f1) e2).isActive();
    }

    public final boolean isCancelled() {
        Object e2 = e();
        return (e2 instanceof u) || ((e2 instanceof b) && ((b) e2).a());
    }

    public final String j() {
        return h() + '{' + j(e()) + '}';
    }

    public CoroutineContext minusKey(CoroutineContext.b<?> bVar) {
        kotlin.jvm.internal.i.b(bVar, "key");
        return k1.a.b(this, bVar);
    }

    public CoroutineContext plus(CoroutineContext coroutineContext) {
        kotlin.jvm.internal.i.b(coroutineContext, "context");
        return k1.a.a((k1) this, coroutineContext);
    }

    public final boolean start() {
        int i2;
        do {
            i2 = i(e());
            if (i2 == 0) {
                return false;
            }
        } while (i2 != 1);
        return true;
    }

    public String toString() {
        return j() + '@' + k0.b(this);
    }

    private final Throwable f(Object obj) {
        if (obj != null ? obj instanceof Throwable : true) {
            return obj != null ? (Throwable) obj : k();
        }
        if (obj != null) {
            return ((x1) obj).a();
        }
        throw new TypeCastException("null cannot be cast to non-null type kotlinx.coroutines.ParentJob");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:24:0x003b, code lost:
        if (r0 == null) goto L_0x0046;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x003d, code lost:
        a(((kotlinx.coroutines.q1.b) r2).b(), r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x0046, code lost:
        return true;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final boolean h(java.lang.Object r8) {
        /*
            r7 = this;
            r0 = 0
            r1 = r0
        L_0x0002:
            java.lang.Object r2 = r7.e()
            boolean r3 = r2 instanceof kotlinx.coroutines.q1.b
            r4 = 0
            r5 = 1
            if (r3 == 0) goto L_0x004a
            monitor-enter(r2)
            r3 = r2
            kotlinx.coroutines.q1$b r3 = (kotlinx.coroutines.q1.b) r3     // Catch:{ all -> 0x0047 }
            boolean r3 = r3.c()     // Catch:{ all -> 0x0047 }
            if (r3 == 0) goto L_0x0018
            monitor-exit(r2)
            return r4
        L_0x0018:
            r3 = r2
            kotlinx.coroutines.q1$b r3 = (kotlinx.coroutines.q1.b) r3     // Catch:{ all -> 0x0047 }
            boolean r3 = r3.a()     // Catch:{ all -> 0x0047 }
            if (r8 != 0) goto L_0x0023
            if (r3 != 0) goto L_0x0030
        L_0x0023:
            if (r1 == 0) goto L_0x0026
            goto L_0x002a
        L_0x0026:
            java.lang.Throwable r1 = r7.f((java.lang.Object) r8)     // Catch:{ all -> 0x0047 }
        L_0x002a:
            r8 = r2
            kotlinx.coroutines.q1$b r8 = (kotlinx.coroutines.q1.b) r8     // Catch:{ all -> 0x0047 }
            r8.a(r1)     // Catch:{ all -> 0x0047 }
        L_0x0030:
            r8 = r2
            kotlinx.coroutines.q1$b r8 = (kotlinx.coroutines.q1.b) r8     // Catch:{ all -> 0x0047 }
            java.lang.Throwable r8 = r8.rootCause     // Catch:{ all -> 0x0047 }
            r1 = r3 ^ 1
            if (r1 == 0) goto L_0x003a
            r0 = r8
        L_0x003a:
            monitor-exit(r2)
            if (r0 == 0) goto L_0x0046
            kotlinx.coroutines.q1$b r2 = (kotlinx.coroutines.q1.b) r2
            kotlinx.coroutines.u1 r8 = r2.b()
            r7.a((kotlinx.coroutines.u1) r8, (java.lang.Throwable) r0)
        L_0x0046:
            return r5
        L_0x0047:
            r8 = move-exception
            monitor-exit(r2)
            throw r8
        L_0x004a:
            boolean r3 = r2 instanceof kotlinx.coroutines.f1
            if (r3 == 0) goto L_0x00a1
            if (r1 == 0) goto L_0x0051
            goto L_0x0055
        L_0x0051:
            java.lang.Throwable r1 = r7.f((java.lang.Object) r8)
        L_0x0055:
            r3 = r2
            kotlinx.coroutines.f1 r3 = (kotlinx.coroutines.f1) r3
            boolean r6 = r3.isActive()
            if (r6 == 0) goto L_0x0065
            boolean r2 = r7.a((kotlinx.coroutines.f1) r3, (java.lang.Throwable) r1)
            if (r2 == 0) goto L_0x0002
            return r5
        L_0x0065:
            kotlinx.coroutines.u r3 = new kotlinx.coroutines.u
            r6 = 2
            r3.<init>(r1, r4, r6, r0)
            int r3 = r7.a((java.lang.Object) r2, (java.lang.Object) r3, (int) r4)
            if (r3 == 0) goto L_0x0086
            if (r3 == r5) goto L_0x0085
            if (r3 == r6) goto L_0x0085
            r2 = 3
            if (r3 != r2) goto L_0x0079
            goto L_0x0002
        L_0x0079:
            java.lang.IllegalStateException r8 = new java.lang.IllegalStateException
            java.lang.String r0 = "unexpected result"
            java.lang.String r0 = r0.toString()
            r8.<init>(r0)
            throw r8
        L_0x0085:
            return r5
        L_0x0086:
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            r8.<init>()
            java.lang.String r0 = "Cannot happen in "
            r8.append(r0)
            r8.append(r2)
            java.lang.String r8 = r8.toString()
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r8 = r8.toString()
            r0.<init>(r8)
            throw r0
        L_0x00a1:
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.q1.h(java.lang.Object):boolean");
    }

    private final String j(Object obj) {
        if (obj instanceof b) {
            b bVar = (b) obj;
            if (bVar.a()) {
                return "Cancelling";
            }
            if (bVar.isCompleting) {
                return "Completing";
            }
            return "Active";
        } else if (!(obj instanceof f1)) {
            return obj instanceof u ? "Cancelled" : "Completed";
        } else {
            if (((f1) obj).isActive()) {
                return "Active";
            }
            return "New";
        }
    }

    public final void a(k1 k1Var) {
        if (j0.a()) {
            if (!(this.parentHandle == null)) {
                throw new AssertionError();
            }
        }
        if (k1Var == null) {
            this.parentHandle = v1.e;
            return;
        }
        k1Var.start();
        m a2 = k1Var.a((o) this);
        this.parentHandle = a2;
        if (f()) {
            a2.a();
            this.parentHandle = v1.e;
        }
    }

    public final Object e() {
        while (true) {
            Object obj = this._state;
            if (!(obj instanceof o)) {
                return obj;
            }
            ((o) obj).a(this);
        }
    }

    private final boolean e(Object obj) {
        int a2;
        do {
            Object e2 = e();
            if (!(e2 instanceof f1) || (((e2 instanceof b) && ((b) e2).isCompleting) || (a2 = a(e2, (Object) new u(f(obj), false, 2, (f) null), 0)) == 0)) {
                return false;
            }
            if (a2 == 1 || a2 == 2) {
                return true;
            }
        } while (a2 == 3);
        throw new IllegalStateException("unexpected result".toString());
    }

    private final Throwable g(Object obj) {
        if (!(obj instanceof u)) {
            obj = null;
        }
        u uVar = (u) obj;
        if (uVar != null) {
            return uVar.a;
        }
        return null;
    }

    private final void b(p1<?> p1Var) {
        p1Var.a(new u1());
        e.compareAndSet(this, p1Var, p1Var.d());
    }

    public boolean c(Throwable th) {
        kotlin.jvm.internal.i.b(th, "cause");
        if (th instanceof CancellationException) {
            return true;
        }
        if (!b((Object) th) || !b()) {
            return false;
        }
        return true;
    }

    private final boolean a(b bVar, Object obj, int i2) {
        boolean a2;
        Throwable a3;
        boolean z = false;
        if (!(e() == bVar)) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        } else if (!(!bVar.c())) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        } else if (bVar.isCompleting) {
            u uVar = (u) (!(obj instanceof u) ? null : obj);
            Throwable th = uVar != null ? uVar.a : null;
            synchronized (bVar) {
                a2 = bVar.a();
                List<Throwable> b2 = bVar.b(th);
                a3 = a(bVar, (List<? extends Throwable>) b2);
                if (a3 != null) {
                    a(a3, (List<? extends Throwable>) b2);
                }
            }
            if (!(a3 == null || a3 == th)) {
                obj = new u(a3, false, 2, (f) null);
            }
            if (a3 != null) {
                if (g(a3) || d(a3)) {
                    z = true;
                }
                if (z) {
                    if (obj != null) {
                        ((u) obj).b();
                    } else {
                        throw new TypeCastException("null cannot be cast to non-null type kotlinx.coroutines.CompletedExceptionally");
                    }
                }
            }
            if (!a2) {
                f(a3);
            }
            d(obj);
            if (e.compareAndSet(this, bVar, r1.a(obj))) {
                a((f1) bVar, obj, i2);
                return true;
            }
            throw new IllegalArgumentException(("Unexpected state: " + this._state + ", expected: " + bVar + ", update: " + obj).toString());
        } else {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:38:0x0054, code lost:
        if (r3 == null) goto L_0x0059;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:0x0056, code lost:
        a(r0, r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:0x0059, code lost:
        r8 = a(r8);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x005d, code lost:
        if (r8 == null) goto L_0x0067;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:43:0x0063, code lost:
        if (b(r2, r8, r9) == false) goto L_0x0067;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:44:0x0065, code lost:
        return 2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:46:0x006b, code lost:
        if (a(r2, r9, r10) == false) goto L_0x006e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:47:0x006d, code lost:
        return 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:48:0x006e, code lost:
        return 3;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final int c(kotlinx.coroutines.f1 r8, java.lang.Object r9, int r10) {
        /*
            r7 = this;
            kotlinx.coroutines.u1 r0 = r7.b((kotlinx.coroutines.f1) r8)
            r1 = 3
            if (r0 == 0) goto L_0x007e
            boolean r2 = r8 instanceof kotlinx.coroutines.q1.b
            r3 = 0
            if (r2 != 0) goto L_0x000e
            r2 = r3
            goto L_0x000f
        L_0x000e:
            r2 = r8
        L_0x000f:
            kotlinx.coroutines.q1$b r2 = (kotlinx.coroutines.q1.b) r2
            r4 = 0
            if (r2 == 0) goto L_0x0015
            goto L_0x001a
        L_0x0015:
            kotlinx.coroutines.q1$b r2 = new kotlinx.coroutines.q1$b
            r2.<init>(r0, r4, r3)
        L_0x001a:
            monitor-enter(r2)
            boolean r5 = r2.isCompleting     // Catch:{ all -> 0x007b }
            if (r5 == 0) goto L_0x0021
            monitor-exit(r2)
            return r4
        L_0x0021:
            r4 = 1
            r2.isCompleting = r4     // Catch:{ all -> 0x007b }
            if (r2 == r8) goto L_0x0030
            java.util.concurrent.atomic.AtomicReferenceFieldUpdater r5 = e     // Catch:{ all -> 0x007b }
            boolean r5 = r5.compareAndSet(r7, r8, r2)     // Catch:{ all -> 0x007b }
            if (r5 != 0) goto L_0x0030
            monitor-exit(r2)
            return r1
        L_0x0030:
            boolean r5 = r2.c()     // Catch:{ all -> 0x007b }
            r5 = r5 ^ r4
            if (r5 == 0) goto L_0x006f
            boolean r5 = r2.a()     // Catch:{ all -> 0x007b }
            boolean r6 = r9 instanceof kotlinx.coroutines.u     // Catch:{ all -> 0x007b }
            if (r6 != 0) goto L_0x0041
            r6 = r3
            goto L_0x0042
        L_0x0041:
            r6 = r9
        L_0x0042:
            kotlinx.coroutines.u r6 = (kotlinx.coroutines.u) r6     // Catch:{ all -> 0x007b }
            if (r6 == 0) goto L_0x004b
            java.lang.Throwable r6 = r6.a     // Catch:{ all -> 0x007b }
            r2.a(r6)     // Catch:{ all -> 0x007b }
        L_0x004b:
            java.lang.Throwable r6 = r2.rootCause     // Catch:{ all -> 0x007b }
            r5 = r5 ^ r4
            if (r5 == 0) goto L_0x0051
            r3 = r6
        L_0x0051:
            kotlin.l r5 = kotlin.l.a     // Catch:{ all -> 0x007b }
            monitor-exit(r2)
            if (r3 == 0) goto L_0x0059
            r7.a((kotlinx.coroutines.u1) r0, (java.lang.Throwable) r3)
        L_0x0059:
            kotlinx.coroutines.n r8 = r7.a((kotlinx.coroutines.f1) r8)
            if (r8 == 0) goto L_0x0067
            boolean r8 = r7.b((kotlinx.coroutines.q1.b) r2, (kotlinx.coroutines.n) r8, (java.lang.Object) r9)
            if (r8 == 0) goto L_0x0067
            r8 = 2
            return r8
        L_0x0067:
            boolean r8 = r7.a((kotlinx.coroutines.q1.b) r2, (java.lang.Object) r9, (int) r10)
            if (r8 == 0) goto L_0x006e
            return r4
        L_0x006e:
            return r1
        L_0x006f:
            java.lang.String r8 = "Failed requirement."
            java.lang.IllegalArgumentException r9 = new java.lang.IllegalArgumentException     // Catch:{ all -> 0x007b }
            java.lang.String r8 = r8.toString()     // Catch:{ all -> 0x007b }
            r9.<init>(r8)     // Catch:{ all -> 0x007b }
            throw r9     // Catch:{ all -> 0x007b }
        L_0x007b:
            r8 = move-exception
            monitor-exit(r2)
            throw r8
        L_0x007e:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.q1.c(kotlinx.coroutines.f1, java.lang.Object, int):int");
    }

    public boolean b(Throwable th) {
        return b((Object) th) && b();
    }

    public final boolean b(Object obj) {
        if (!d() || !e(obj)) {
            return h(obj);
        }
        return true;
    }

    private final u1 b(f1 f1Var) {
        u1 b2 = f1Var.b();
        if (b2 != null) {
            return b2;
        }
        if (f1Var instanceof x0) {
            return new u1();
        }
        if (f1Var instanceof p1) {
            b((p1<?>) (p1) f1Var);
            return null;
        }
        throw new IllegalStateException(("State should have list: " + f1Var).toString());
    }

    private final boolean b(b bVar, n nVar, Object obj) {
        while (k1.a.a(nVar.f1809i, false, false, new a(this, bVar, nVar, obj), 1, (Object) null) == v1.e) {
            nVar = a((kotlinx.coroutines.internal.i) nVar);
            if (nVar == null) {
                return false;
            }
        }
        return true;
    }

    private final void b(u1 u1Var, Throwable th) {
        Object c2 = u1Var.c();
        if (c2 != null) {
            CompletionHandlerException completionHandlerException = null;
            for (kotlinx.coroutines.internal.i iVar = (kotlinx.coroutines.internal.i) c2; !kotlin.jvm.internal.i.a((Object) iVar, (Object) u1Var); iVar = iVar.d()) {
                if (iVar instanceof p1) {
                    p1 p1Var = (p1) iVar;
                    try {
                        p1Var.b(th);
                    } catch (Throwable th2) {
                        if (completionHandlerException != null) {
                            b.a(completionHandlerException, th2);
                            if (completionHandlerException != null) {
                            }
                        }
                        completionHandlerException = new CompletionHandlerException("Exception in completion handler " + p1Var + " for " + this, th2);
                        l lVar = l.a;
                    }
                }
            }
            if (completionHandlerException != null) {
                e((Throwable) completionHandlerException);
                return;
            }
            return;
        }
        throw new TypeCastException("null cannot be cast to non-null type kotlinx.coroutines.internal.Node /* = kotlinx.coroutines.internal.LockFreeLinkedListNode */");
    }

    public final boolean c(Object obj) {
        int a2;
        do {
            boolean z = false;
            a2 = a(e(), obj, 0);
            if (a2 != 0) {
                z = true;
                if (!(a2 == 1 || a2 == 2)) {
                }
            }
            return z;
        } while (a2 == 3);
        throw new IllegalStateException("unexpected result".toString());
    }

    private final Throwable a(b bVar, List<? extends Throwable> list) {
        T t = null;
        if (!list.isEmpty()) {
            Iterator<T> it = list.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                T next = it.next();
                if (!(((Throwable) next) instanceof CancellationException)) {
                    t = next;
                    break;
                }
            }
            Throwable th = (Throwable) t;
            return th != null ? th : (Throwable) list.get(0);
        } else if (bVar.a()) {
            return k();
        } else {
            return null;
        }
    }

    public final boolean b(Object obj, int i2) {
        int a2;
        do {
            a2 = a(e(), obj, i2);
            if (a2 == 0) {
                throw new IllegalStateException("Job " + this + " is already complete or completing, " + "but is being completed with " + obj, g(obj));
            } else if (a2 == 1) {
                return true;
            } else {
                if (a2 == 2) {
                    return false;
                }
            }
        } while (a2 == 3);
        throw new IllegalStateException("unexpected result".toString());
    }

    private final void a(Throwable th, List<? extends Throwable> list) {
        if (list.size() > 1) {
            Set a2 = d.a(list.size());
            Throwable b2 = s.b(th);
            for (Throwable b3 : list) {
                Throwable b4 = s.b(b3);
                if (b4 != th && b4 != b2 && !(b4 instanceof CancellationException) && a2.add(b4)) {
                    b.a(th, b4);
                }
            }
        }
    }

    private final void a(f1 f1Var, Object obj, int i2) {
        m mVar = this.parentHandle;
        if (mVar != null) {
            mVar.a();
            this.parentHandle = v1.e;
        }
        Throwable th = null;
        u uVar = (u) (!(obj instanceof u) ? null : obj);
        if (uVar != null) {
            th = uVar.a;
        }
        if (f1Var instanceof p1) {
            try {
                ((p1) f1Var).b(th);
            } catch (Throwable th2) {
                e((Throwable) new CompletionHandlerException("Exception in completion handler " + f1Var + " for " + this, th2));
            }
        } else {
            u1 b2 = f1Var.b();
            if (b2 != null) {
                b(b2, th);
            }
        }
        a(obj, i2);
    }

    private final void a(u1 u1Var, Throwable th) {
        f(th);
        Object c2 = u1Var.c();
        if (c2 != null) {
            CompletionHandlerException completionHandlerException = null;
            for (kotlinx.coroutines.internal.i iVar = (kotlinx.coroutines.internal.i) c2; !kotlin.jvm.internal.i.a((Object) iVar, (Object) u1Var); iVar = iVar.d()) {
                if (iVar instanceof l1) {
                    p1 p1Var = (p1) iVar;
                    try {
                        p1Var.b(th);
                    } catch (Throwable th2) {
                        if (completionHandlerException != null) {
                            b.a(completionHandlerException, th2);
                            if (completionHandlerException != null) {
                            }
                        }
                        completionHandlerException = new CompletionHandlerException("Exception in completion handler " + p1Var + " for " + this, th2);
                        l lVar = l.a;
                    }
                }
            }
            if (completionHandlerException != null) {
                e((Throwable) completionHandlerException);
            }
            g(th);
            return;
        }
        throw new TypeCastException("null cannot be cast to non-null type kotlinx.coroutines.internal.Node /* = kotlinx.coroutines.internal.LockFreeLinkedListNode */");
    }

    public static /* synthetic */ CancellationException a(q1 q1Var, Throwable th, String str, int i2, Object obj) {
        if (obj == null) {
            if ((i2 & 1) != 0) {
                str = null;
            }
            return q1Var.a(th, str);
        }
        throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: toCancellationException");
    }

    /* access modifiers changed from: protected */
    public final CancellationException a(Throwable th, String str) {
        kotlin.jvm.internal.i.b(th, "$this$toCancellationException");
        CancellationException cancellationException = (CancellationException) (!(th instanceof CancellationException) ? null : th);
        if (cancellationException == null) {
            if (str == null) {
                str = k0.a((Object) th) + " was cancelled";
            }
            cancellationException = new JobCancellationException(str, th, this);
        }
        return cancellationException;
    }

    public final v0 a(kotlin.jvm.b.l<? super Throwable, l> lVar) {
        kotlin.jvm.internal.i.b(lVar, "handler");
        return a(false, true, lVar);
    }

    private final p1<?> a(kotlin.jvm.b.l<? super Throwable, l> lVar, boolean z) {
        boolean z2 = true;
        l1 l1Var = null;
        if (z) {
            if (lVar instanceof l1) {
                l1Var = lVar;
            }
            l1 l1Var2 = l1Var;
            if (l1Var2 != null) {
                if (l1Var2.f1812h != this) {
                    z2 = false;
                }
                if (!z2) {
                    throw new IllegalArgumentException("Failed requirement.".toString());
                } else if (l1Var2 != null) {
                    return l1Var2;
                }
            }
            return new i1(this, lVar);
        }
        if (lVar instanceof p1) {
            l1Var = lVar;
        }
        p1<?> p1Var = l1Var;
        if (p1Var != null) {
            if (p1Var.f1812h != this || (p1Var instanceof l1)) {
                z2 = false;
            }
            if (!z2) {
                throw new IllegalArgumentException("Failed requirement.".toString());
            } else if (p1Var != null) {
                return p1Var;
            }
        }
        return new j1(this, lVar);
    }

    /* JADX WARNING: type inference failed for: r1v2, types: [kotlinx.coroutines.e1] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final void a(kotlinx.coroutines.x0 r3) {
        /*
            r2 = this;
            kotlinx.coroutines.u1 r0 = new kotlinx.coroutines.u1
            r0.<init>()
            boolean r1 = r3.isActive()
            if (r1 == 0) goto L_0x000c
            goto L_0x0012
        L_0x000c:
            kotlinx.coroutines.e1 r1 = new kotlinx.coroutines.e1
            r1.<init>(r0)
            r0 = r1
        L_0x0012:
            java.util.concurrent.atomic.AtomicReferenceFieldUpdater r1 = e
            r1.compareAndSet(r2, r3, r0)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.q1.a(kotlinx.coroutines.x0):void");
    }

    public void a(CancellationException cancellationException) {
        b((Throwable) cancellationException);
    }

    public final void a(x1 x1Var) {
        kotlin.jvm.internal.i.b(x1Var, "parentJob");
        b((Object) x1Var);
    }

    public CancellationException a() {
        Throwable th;
        Object e2 = e();
        CancellationException cancellationException = null;
        if (e2 instanceof b) {
            th = ((b) e2).rootCause;
        } else if (e2 instanceof u) {
            th = ((u) e2).a;
        } else if (!(e2 instanceof f1)) {
            th = null;
        } else {
            throw new IllegalStateException(("Cannot be cancelling child in this state: " + e2).toString());
        }
        if (th instanceof CancellationException) {
            cancellationException = th;
        }
        CancellationException cancellationException2 = cancellationException;
        if (cancellationException2 != null) {
            return cancellationException2;
        }
        return new JobCancellationException("Parent job is " + j(e2), th, this);
    }

    private final boolean a(f1 f1Var, Throwable th) {
        if (j0.a() && !(!(f1Var instanceof b))) {
            throw new AssertionError();
        } else if (!j0.a() || f1Var.isActive()) {
            u1 b2 = b(f1Var);
            if (b2 == null) {
                return false;
            }
            if (!e.compareAndSet(this, f1Var, new b(b2, false, th))) {
                return false;
            }
            a(b2, th);
            return true;
        } else {
            throw new AssertionError();
        }
    }

    private final int a(Object obj, Object obj2, int i2) {
        if (!(obj instanceof f1)) {
            return 0;
        }
        if (((obj instanceof x0) || (obj instanceof p1)) && !(obj instanceof n) && !(obj2 instanceof u)) {
            return !b((f1) obj, obj2, i2) ? 3 : 1;
        }
        return c((f1) obj, obj2, i2);
    }

    private final n a(f1 f1Var) {
        n nVar = (n) (!(f1Var instanceof n) ? null : f1Var);
        if (nVar != null) {
            return nVar;
        }
        u1 b2 = f1Var.b();
        if (b2 != null) {
            return a((kotlinx.coroutines.internal.i) b2);
        }
        return null;
    }

    /* access modifiers changed from: private */
    public final void a(b bVar, n nVar, Object obj) {
        if (e() == bVar) {
            n a2 = a((kotlinx.coroutines.internal.i) nVar);
            if ((a2 == null || !b(bVar, a2, obj)) && a(bVar, obj, 0)) {
            }
            return;
        }
        throw new IllegalArgumentException("Failed requirement.".toString());
    }

    private final n a(kotlinx.coroutines.internal.i iVar) {
        while (iVar.h()) {
            iVar = iVar.f();
        }
        while (true) {
            iVar = iVar.d();
            if (!iVar.h()) {
                if (iVar instanceof n) {
                    return (n) iVar;
                }
                if (iVar instanceof u1) {
                    return null;
                }
            }
        }
    }

    public final m a(o oVar) {
        kotlin.jvm.internal.i.b(oVar, "child");
        v0 a2 = k1.a.a(this, true, false, new n(this, oVar), 2, (Object) null);
        if (a2 != null) {
            return (m) a2;
        }
        throw new TypeCastException("null cannot be cast to non-null type kotlinx.coroutines.ChildHandle");
    }

    public final v0 a(boolean z, boolean z2, kotlin.jvm.b.l<? super Throwable, l> lVar) {
        Throwable th;
        kotlin.jvm.internal.i.b(lVar, "handler");
        Throwable th2 = null;
        p1<?> p1Var = null;
        while (true) {
            Object e2 = e();
            if (e2 instanceof x0) {
                x0 x0Var = (x0) e2;
                if (x0Var.isActive()) {
                    if (p1Var == null) {
                        p1Var = a(lVar, z);
                    }
                    if (e.compareAndSet(this, e2, p1Var)) {
                        return p1Var;
                    }
                } else {
                    a(x0Var);
                }
            } else if (e2 instanceof f1) {
                u1 b2 = ((f1) e2).b();
                if (b2 != null) {
                    v0 v0Var = v1.e;
                    if (!z || !(e2 instanceof b)) {
                        th = null;
                    } else {
                        synchronized (e2) {
                            th = ((b) e2).rootCause;
                            if (th == null || ((lVar instanceof n) && !((b) e2).isCompleting)) {
                                if (p1Var == null) {
                                    p1Var = a(lVar, z);
                                }
                                if (a(e2, b2, p1Var)) {
                                    if (th == null) {
                                        return p1Var;
                                    }
                                    v0Var = p1Var;
                                }
                            }
                            l lVar2 = l.a;
                        }
                    }
                    if (th != null) {
                        if (z2) {
                            lVar.invoke(th);
                        }
                        return v0Var;
                    }
                    if (p1Var == null) {
                        p1Var = a(lVar, z);
                    }
                    if (a(e2, b2, p1Var)) {
                        return p1Var;
                    }
                } else if (e2 != null) {
                    b((p1<?>) (p1) e2);
                } else {
                    throw new TypeCastException("null cannot be cast to non-null type kotlinx.coroutines.JobNode<*>");
                }
            } else {
                if (z2) {
                    if (!(e2 instanceof u)) {
                        e2 = null;
                    }
                    u uVar = (u) e2;
                    if (uVar != null) {
                        th2 = uVar.a;
                    }
                    lVar.invoke(th2);
                }
                return v1.e;
            }
        }
    }

    private final boolean a(Object obj, u1 u1Var, p1<?> p1Var) {
        int a2;
        c cVar = new c(p1Var, p1Var, this, obj);
        do {
            Object e2 = u1Var.e();
            if (e2 != null) {
                a2 = ((kotlinx.coroutines.internal.i) e2).a(p1Var, u1Var, cVar);
                if (a2 == 1) {
                    return true;
                }
            } else {
                throw new TypeCastException("null cannot be cast to non-null type kotlinx.coroutines.internal.Node /* = kotlinx.coroutines.internal.LockFreeLinkedListNode */");
            }
        } while (a2 != 2);
        return false;
    }

    /* JADX WARNING: Removed duplicated region for block: B:14:0x001d A[SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:3:0x000d A[ADDED_TO_REGION] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void a(kotlinx.coroutines.p1<?> r4) {
        /*
            r3 = this;
            java.lang.String r0 = "node"
            kotlin.jvm.internal.i.b(r4, r0)
        L_0x0005:
            java.lang.Object r0 = r3.e()
            boolean r1 = r0 instanceof kotlinx.coroutines.p1
            if (r1 == 0) goto L_0x001d
            if (r0 == r4) goto L_0x0010
            return
        L_0x0010:
            java.util.concurrent.atomic.AtomicReferenceFieldUpdater r1 = e
            kotlinx.coroutines.x0 r2 = kotlinx.coroutines.r1.c
            boolean r0 = r1.compareAndSet(r3, r0, r2)
            if (r0 == 0) goto L_0x0005
            return
        L_0x001d:
            boolean r1 = r0 instanceof kotlinx.coroutines.f1
            if (r1 == 0) goto L_0x002c
            kotlinx.coroutines.f1 r0 = (kotlinx.coroutines.f1) r0
            kotlinx.coroutines.u1 r0 = r0.b()
            if (r0 == 0) goto L_0x002c
            r4.i()
        L_0x002c:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.q1.a(kotlinx.coroutines.p1):void");
    }
}
