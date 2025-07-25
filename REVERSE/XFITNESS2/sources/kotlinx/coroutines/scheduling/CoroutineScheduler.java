package kotlinx.coroutines.scheduling;

import android.support.v4.media.session.PlaybackStateCompat;
import java.io.Closeable;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import java.util.concurrent.locks.LockSupport;
import kotlin.jvm.internal.i;
import kotlin.l;
import kotlinx.coroutines.e2;
import kotlinx.coroutines.f2;
import kotlinx.coroutines.internal.t;
import kotlinx.coroutines.j0;
import kotlinx.coroutines.k0;

/* compiled from: CoroutineScheduler.kt */
public final class CoroutineScheduler implements Executor, Closeable {
    private static final AtomicLongFieldUpdater m = AtomicLongFieldUpdater.newUpdater(CoroutineScheduler.class, "parkedWorkersStack");
    static final AtomicLongFieldUpdater n = AtomicLongFieldUpdater.newUpdater(CoroutineScheduler.class, "controlState");
    private static final AtomicIntegerFieldUpdater o = AtomicIntegerFieldUpdater.newUpdater(CoroutineScheduler.class, "_isTerminated");
    /* access modifiers changed from: private */
    public static final int p;
    /* access modifiers changed from: private */
    public static final int q;
    /* access modifiers changed from: private */
    public static final int r = ((int) TimeUnit.SECONDS.toNanos(1));
    /* access modifiers changed from: private */
    public static final int s = ((int) f.b(f.a(k.a / ((long) 4), 10), (long) r));
    /* access modifiers changed from: private */
    public static final t t = new t("NOT_IN_STACK");
    private volatile int _isTerminated;
    volatile long controlState;
    /* access modifiers changed from: private */
    public final d e;
    /* access modifiers changed from: private */

    /* renamed from: f  reason: collision with root package name */
    public final Semaphore f1820f;
    /* access modifiers changed from: private */

    /* renamed from: g  reason: collision with root package name */
    public final b[] f1821g;
    /* access modifiers changed from: private */

    /* renamed from: h  reason: collision with root package name */
    public final Random f1822h;
    /* access modifiers changed from: private */

    /* renamed from: i  reason: collision with root package name */
    public final int f1823i;

    /* renamed from: j  reason: collision with root package name */
    private final int f1824j;
    /* access modifiers changed from: private */
    public final long k;
    /* access modifiers changed from: private */
    public final String l;
    private volatile long parkedWorkersStack;

    /* compiled from: CoroutineScheduler.kt */
    public enum WorkerState {
        CPU_ACQUIRED,
        BLOCKING,
        PARKING,
        RETIRING,
        TERMINATED
    }

    /* compiled from: CoroutineScheduler.kt */
    public static final class a {
        private a() {
        }

        public /* synthetic */ a(f fVar) {
            this();
        }
    }

    /* compiled from: CoroutineScheduler.kt */
    public final class b extends Thread {
        private static final AtomicIntegerFieldUpdater l = AtomicIntegerFieldUpdater.newUpdater(b.class, "terminationState");
        private final m e;

        /* renamed from: f  reason: collision with root package name */
        private long f1825f;

        /* renamed from: g  reason: collision with root package name */
        private long f1826g;

        /* renamed from: h  reason: collision with root package name */
        private int f1827h;

        /* renamed from: i  reason: collision with root package name */
        private int f1828i;
        private volatile int indexInArray;

        /* renamed from: j  reason: collision with root package name */
        private int f1829j;
        private volatile Object nextParkedWorker;
        private volatile int spins;
        private volatile WorkerState state;
        private volatile int terminationState;

        private b() {
            setDaemon(true);
            this.e = new m();
            this.state = WorkerState.RETIRING;
            this.terminationState = 0;
            this.nextParkedWorker = CoroutineScheduler.t;
            this.f1827h = CoroutineScheduler.s;
            this.f1828i = CoroutineScheduler.this.f1822h.nextInt();
        }

        private final boolean k() {
            h a = CoroutineScheduler.this.e.a(TaskMode.PROBABLY_BLOCKING);
            if (a == null) {
                return true;
            }
            this.e.a(a, CoroutineScheduler.this.e);
            return false;
        }

        private final void l() {
            a(WorkerState.PARKING);
            if (k()) {
                this.terminationState = 0;
                if (this.f1825f == 0) {
                    this.f1825f = System.nanoTime() + CoroutineScheduler.this.k;
                }
                if (a(CoroutineScheduler.this.k) && System.nanoTime() - this.f1825f >= 0) {
                    this.f1825f = 0;
                    p();
                }
            }
        }

        private final void m() {
            int i2 = this.spins;
            if (i2 <= CoroutineScheduler.q) {
                this.spins = i2 + 1;
                if (i2 >= CoroutineScheduler.p) {
                    Thread.yield();
                    return;
                }
                return;
            }
            if (this.f1827h < CoroutineScheduler.r) {
                this.f1827h = f.b((this.f1827h * 3) >>> 1, CoroutineScheduler.r);
            }
            a(WorkerState.PARKING);
            a((long) this.f1827h);
        }

        private final h n() {
            h hVar;
            h a;
            boolean z = a(CoroutineScheduler.this.f1823i * 2) == 0;
            if (z && (a = CoroutineScheduler.this.e.a(TaskMode.NON_BLOCKING)) != null) {
                return a;
            }
            h b = this.e.b();
            if (b != null) {
                return b;
            }
            if (z || (hVar = (h) CoroutineScheduler.this.e.c()) == null) {
                return o();
            }
            return hVar;
        }

        private final h o() {
            int c = CoroutineScheduler.this.q();
            if (c < 2) {
                return null;
            }
            int i2 = this.f1829j;
            if (i2 == 0) {
                i2 = a(c);
            }
            int i3 = 1;
            int i4 = i2 + 1;
            if (i4 <= c) {
                i3 = i4;
            }
            this.f1829j = i3;
            b bVar = CoroutineScheduler.this.f1821g[i3];
            if (bVar == null || bVar == this || !this.e.a(bVar.e, CoroutineScheduler.this.e)) {
                return null;
            }
            return this.e.b();
        }

        private final void p() {
            synchronized (CoroutineScheduler.this.f1821g) {
                if (!CoroutineScheduler.this.r()) {
                    if (CoroutineScheduler.this.q() > CoroutineScheduler.this.f1823i) {
                        if (k()) {
                            if (l.compareAndSet(this, 0, 1)) {
                                int i2 = this.indexInArray;
                                b(0);
                                CoroutineScheduler.this.a(this, i2, 0);
                                int andDecrement = (int) (CoroutineScheduler.n.getAndDecrement(CoroutineScheduler.this) & 2097151);
                                if (andDecrement != i2) {
                                    b bVar = CoroutineScheduler.this.f1821g[andDecrement];
                                    if (bVar != null) {
                                        CoroutineScheduler.this.f1821g[i2] = bVar;
                                        bVar.b(i2);
                                        CoroutineScheduler.this.a(bVar, andDecrement, i2);
                                    } else {
                                        i.a();
                                        throw null;
                                    }
                                }
                                CoroutineScheduler.this.f1821g[andDecrement] = null;
                                l lVar = l.a;
                                this.state = WorkerState.TERMINATED;
                            }
                        }
                    }
                }
            }
        }

        public final void a(Object obj) {
            this.nextParkedWorker = obj;
        }

        public final int b() {
            return this.indexInArray;
        }

        public final m c() {
            return this.e;
        }

        public final Object d() {
            return this.nextParkedWorker;
        }

        public final CoroutineScheduler e() {
            return CoroutineScheduler.this;
        }

        public final void f() {
            this.f1827h = CoroutineScheduler.s;
            this.spins = 0;
        }

        public final boolean g() {
            return this.state == WorkerState.BLOCKING;
        }

        public final WorkerState getState() {
            return this.state;
        }

        public final boolean h() {
            return this.state == WorkerState.PARKING;
        }

        public final boolean i() {
            if (this.state == WorkerState.CPU_ACQUIRED) {
                return true;
            }
            if (!CoroutineScheduler.this.f1820f.tryAcquire()) {
                return false;
            }
            this.state = WorkerState.CPU_ACQUIRED;
            return true;
        }

        public final boolean j() {
            int i2 = this.terminationState;
            if (i2 == 1 || i2 == -1) {
                return false;
            }
            if (i2 == 0) {
                return l.compareAndSet(this, 0, -1);
            }
            throw new IllegalStateException(("Invalid terminationState = " + i2).toString());
        }

        public void run() {
            boolean z = false;
            while (!CoroutineScheduler.this.r() && this.state != WorkerState.TERMINATED) {
                h a = a();
                if (a == null) {
                    if (this.state == WorkerState.CPU_ACQUIRED) {
                        m();
                    } else {
                        l();
                    }
                    z = true;
                } else {
                    TaskMode a2 = a.a();
                    if (z) {
                        b(a2);
                        z = false;
                    }
                    a(a2, a.e);
                    CoroutineScheduler.this.a(a);
                    a(a2);
                }
            }
            a(WorkerState.TERMINATED);
        }

        public final boolean a(WorkerState workerState) {
            i.b(workerState, "newState");
            WorkerState workerState2 = this.state;
            boolean z = workerState2 == WorkerState.CPU_ACQUIRED;
            if (z) {
                CoroutineScheduler.this.f1820f.release();
            }
            if (workerState2 != workerState) {
                this.state = workerState;
            }
            return z;
        }

        public final void b(int i2) {
            StringBuilder sb = new StringBuilder();
            sb.append(CoroutineScheduler.this.l);
            sb.append("-worker-");
            sb.append(i2 == 0 ? "TERMINATED" : String.valueOf(i2));
            setName(sb.toString());
            this.indexInArray = i2;
        }

        private final void b(TaskMode taskMode) {
            this.f1825f = 0;
            this.f1829j = 0;
            if (this.state == WorkerState.PARKING) {
                if (j0.a()) {
                    if (!(taskMode == TaskMode.PROBABLY_BLOCKING)) {
                        throw new AssertionError();
                    }
                }
                this.state = WorkerState.BLOCKING;
                this.f1827h = CoroutineScheduler.s;
            }
            this.spins = 0;
        }

        private final void a(TaskMode taskMode, long j2) {
            if (taskMode != TaskMode.NON_BLOCKING) {
                CoroutineScheduler.n.addAndGet(CoroutineScheduler.this, PlaybackStateCompat.ACTION_SET_SHUFFLE_MODE);
                if (a(WorkerState.BLOCKING)) {
                    CoroutineScheduler.this.t();
                }
            } else if (CoroutineScheduler.this.f1820f.availablePermits() != 0) {
                long a = k.f1842f.a();
                long j3 = k.a;
                if (a - j2 >= j3 && a - this.f1826g >= j3 * ((long) 5)) {
                    this.f1826g = a;
                    CoroutineScheduler.this.t();
                }
            }
        }

        public b(CoroutineScheduler coroutineScheduler, int i2) {
            this();
            b(i2);
        }

        private final void a(TaskMode taskMode) {
            if (taskMode != TaskMode.NON_BLOCKING) {
                CoroutineScheduler.n.addAndGet(CoroutineScheduler.this, -2097152);
                WorkerState workerState = this.state;
                if (workerState != WorkerState.TERMINATED) {
                    if (j0.a()) {
                        if (!(workerState == WorkerState.BLOCKING)) {
                            throw new AssertionError();
                        }
                    }
                    this.state = WorkerState.RETIRING;
                }
            }
        }

        public final int a(int i2) {
            int i3 = this.f1828i;
            int i4 = i3 ^ (i3 << 13);
            this.f1828i = i4;
            int i5 = i4 ^ (i4 >> 17);
            this.f1828i = i5;
            int i6 = i5 ^ (i5 << 5);
            this.f1828i = i6;
            int i7 = i2 - 1;
            if ((i7 & i2) == 0) {
                return i6 & i7;
            }
            return (i6 & Integer.MAX_VALUE) % i2;
        }

        private final boolean a(long j2) {
            CoroutineScheduler.this.b(this);
            if (!k()) {
                return false;
            }
            LockSupport.parkNanos(j2);
            return true;
        }

        public final h a() {
            if (i()) {
                return n();
            }
            h b = this.e.b();
            return b != null ? b : CoroutineScheduler.this.e.a(TaskMode.PROBABLY_BLOCKING);
        }
    }

    static {
        new a((f) null);
        int a2 = w.a("kotlinx.coroutines.scheduler.spins", 1000, 1, 0, 8, (Object) null);
        p = a2;
        q = a2 + w.a("kotlinx.coroutines.scheduler.yields", 0, 0, 0, 8, (Object) null);
    }

    public CoroutineScheduler(int i2, int i3, long j2, String str) {
        i.b(str, "schedulerName");
        this.f1823i = i2;
        this.f1824j = i3;
        this.k = j2;
        this.l = str;
        if (i2 >= 1) {
            if (this.f1824j >= this.f1823i) {
                if (this.f1824j <= 2097150) {
                    if (this.k > 0) {
                        this.e = new d();
                        this.f1820f = new Semaphore(this.f1823i, false);
                        this.parkedWorkersStack = 0;
                        this.f1821g = new b[(this.f1824j + 1)];
                        this.controlState = 0;
                        this.f1822h = new Random();
                        this._isTerminated = 0;
                        return;
                    }
                    throw new IllegalArgumentException(("Idle worker keep alive time " + this.k + " must be positive").toString());
                }
                throw new IllegalArgumentException(("Max pool size " + this.f1824j + " should not exceed maximal supported number of threads 2097150").toString());
            }
            throw new IllegalArgumentException(("Max pool size " + this.f1824j + " should be greater than or equals to core pool size " + this.f1823i).toString());
        }
        throw new IllegalArgumentException(("Core pool size " + this.f1823i + " should be at least 1").toString());
    }

    /* JADX WARNING: Code restructure failed: missing block: B:42:0x007c, code lost:
        return 0;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final int o() {
        /*
            r10 = this;
            kotlinx.coroutines.scheduling.CoroutineScheduler$b[] r0 = r10.f1821g
            monitor-enter(r0)
            boolean r1 = r10.r()     // Catch:{ all -> 0x007d }
            if (r1 == 0) goto L_0x000c
            r1 = -1
            monitor-exit(r0)
            return r1
        L_0x000c:
            long r1 = r10.controlState     // Catch:{ all -> 0x007d }
            r3 = 2097151(0x1fffff, double:1.0361303E-317)
            long r5 = r1 & r3
            int r6 = (int) r5     // Catch:{ all -> 0x007d }
            r7 = 4398044413952(0x3ffffe00000, double:2.1729226538177E-311)
            long r1 = r1 & r7
            r5 = 21
            long r1 = r1 >> r5
            int r2 = (int) r1     // Catch:{ all -> 0x007d }
            int r1 = r6 - r2
            int r2 = r10.f1823i     // Catch:{ all -> 0x007d }
            r5 = 0
            if (r1 < r2) goto L_0x0027
            monitor-exit(r0)
            return r5
        L_0x0027:
            int r2 = r10.f1824j     // Catch:{ all -> 0x007d }
            if (r6 >= r2) goto L_0x007b
            java.util.concurrent.Semaphore r2 = r10.f1820f     // Catch:{ all -> 0x007d }
            int r2 = r2.availablePermits()     // Catch:{ all -> 0x007d }
            if (r2 != 0) goto L_0x0034
            goto L_0x007b
        L_0x0034:
            long r6 = r10.controlState     // Catch:{ all -> 0x007d }
            long r6 = r6 & r3
            int r2 = (int) r6     // Catch:{ all -> 0x007d }
            r6 = 1
            int r2 = r2 + r6
            if (r2 <= 0) goto L_0x0044
            kotlinx.coroutines.scheduling.CoroutineScheduler$b[] r7 = r10.f1821g     // Catch:{ all -> 0x007d }
            r7 = r7[r2]     // Catch:{ all -> 0x007d }
            if (r7 != 0) goto L_0x0044
            r7 = 1
            goto L_0x0045
        L_0x0044:
            r7 = 0
        L_0x0045:
            if (r7 == 0) goto L_0x006f
            kotlinx.coroutines.scheduling.CoroutineScheduler$b r7 = new kotlinx.coroutines.scheduling.CoroutineScheduler$b     // Catch:{ all -> 0x007d }
            r7.<init>(r10, r2)     // Catch:{ all -> 0x007d }
            r7.start()     // Catch:{ all -> 0x007d }
            java.util.concurrent.atomic.AtomicLongFieldUpdater r8 = n     // Catch:{ all -> 0x007d }
            long r8 = r8.incrementAndGet(r10)     // Catch:{ all -> 0x007d }
            long r3 = r3 & r8
            int r4 = (int) r3     // Catch:{ all -> 0x007d }
            if (r2 != r4) goto L_0x005a
            r5 = 1
        L_0x005a:
            if (r5 == 0) goto L_0x0063
            kotlinx.coroutines.scheduling.CoroutineScheduler$b[] r3 = r10.f1821g     // Catch:{ all -> 0x007d }
            r3[r2] = r7     // Catch:{ all -> 0x007d }
            int r1 = r1 + r6
            monitor-exit(r0)
            return r1
        L_0x0063:
            java.lang.String r1 = "Failed requirement."
            java.lang.IllegalArgumentException r2 = new java.lang.IllegalArgumentException     // Catch:{ all -> 0x007d }
            java.lang.String r1 = r1.toString()     // Catch:{ all -> 0x007d }
            r2.<init>(r1)     // Catch:{ all -> 0x007d }
            throw r2     // Catch:{ all -> 0x007d }
        L_0x006f:
            java.lang.String r1 = "Failed requirement."
            java.lang.IllegalArgumentException r2 = new java.lang.IllegalArgumentException     // Catch:{ all -> 0x007d }
            java.lang.String r1 = r1.toString()     // Catch:{ all -> 0x007d }
            r2.<init>(r1)     // Catch:{ all -> 0x007d }
            throw r2     // Catch:{ all -> 0x007d }
        L_0x007b:
            monitor-exit(r0)
            return r5
        L_0x007d:
            r1 = move-exception
            monitor-exit(r0)
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.scheduling.CoroutineScheduler.o():int");
    }

    private final b p() {
        Thread currentThread = Thread.currentThread();
        if (!(currentThread instanceof b)) {
            currentThread = null;
        }
        b bVar = (b) currentThread;
        if (bVar == null || !i.a((Object) bVar.e(), (Object) this)) {
            return null;
        }
        return bVar;
    }

    /* access modifiers changed from: private */
    public final int q() {
        return (int) (this.controlState & 2097151);
    }

    /* access modifiers changed from: private */
    public final boolean r() {
        return this._isTerminated != 0;
    }

    private final b s() {
        while (true) {
            long j2 = this.parkedWorkersStack;
            b bVar = this.f1821g[(int) (2097151 & j2)];
            if (bVar == null) {
                return null;
            }
            long j3 = (PlaybackStateCompat.ACTION_SET_SHUFFLE_MODE + j2) & -2097152;
            int a2 = a(bVar);
            if (a2 >= 0) {
                if (m.compareAndSet(this, j2, ((long) a2) | j3)) {
                    bVar.a((Object) t);
                    return bVar;
                }
            }
        }
    }

    /* access modifiers changed from: private */
    public final void t() {
        if (this.f1820f.availablePermits() == 0) {
            u();
        } else if (!u()) {
            long j2 = this.controlState;
            if (((int) (2097151 & j2)) - ((int) ((j2 & 4398044413952L) >> 21)) < this.f1823i) {
                int o2 = o();
                if (o2 == 1 && this.f1823i > 1) {
                    o();
                }
                if (o2 > 0) {
                    return;
                }
            }
            u();
        }
    }

    private final boolean u() {
        while (true) {
            b s2 = s();
            if (s2 == null) {
                return false;
            }
            s2.f();
            boolean h2 = s2.h();
            LockSupport.unpark(s2);
            if (h2 && s2.j()) {
                return true;
            }
        }
    }

    public void close() {
        h(10000);
    }

    public void execute(Runnable runnable) {
        i.b(runnable, "command");
        a(this, runnable, (i) null, false, 6, (Object) null);
    }

    public String toString() {
        ArrayList arrayList = new ArrayList();
        int i2 = 0;
        int i3 = 0;
        int i4 = 0;
        int i5 = 0;
        int i6 = 0;
        for (b bVar : this.f1821g) {
            if (bVar != null) {
                int c = bVar.c().c();
                int i7 = a.a[bVar.getState().ordinal()];
                if (i7 == 1) {
                    i4++;
                } else if (i7 == 2) {
                    i3++;
                    arrayList.add(String.valueOf(c) + "b");
                } else if (i7 == 3) {
                    i2++;
                    arrayList.add(String.valueOf(c) + "c");
                } else if (i7 == 4) {
                    i5++;
                    if (c > 0) {
                        arrayList.add(String.valueOf(c) + "r");
                    }
                } else if (i7 == 5) {
                    i6++;
                }
            }
        }
        long j2 = this.controlState;
        return this.l + '@' + k0.b(this) + '[' + "Pool Size {" + "core = " + this.f1823i + ", " + "max = " + this.f1824j + "}, " + "Worker States {" + "CPU = " + i2 + ", " + "blocking = " + i3 + ", " + "parked = " + i4 + ", " + "retired = " + i5 + ", " + "terminated = " + i6 + "}, " + "running workers queues = " + arrayList + ", " + "global queue size = " + this.e.b() + ", " + "Control State Workers {" + "created = " + ((int) (2097151 & j2)) + ", " + "blocking = " + ((int) ((j2 & 4398044413952L) >> 21)) + '}' + "]";
    }

    /* access modifiers changed from: private */
    public final void b(b bVar) {
        long j2;
        long j3;
        int b2;
        if (bVar.d() == t) {
            do {
                j2 = this.parkedWorkersStack;
                int i2 = (int) (2097151 & j2);
                j3 = (PlaybackStateCompat.ACTION_SET_SHUFFLE_MODE + j2) & -2097152;
                b2 = bVar.b();
                if (j0.a()) {
                    if (!(b2 != 0)) {
                        throw new AssertionError();
                    }
                }
                bVar.a((Object) this.f1821g[i2]);
            } while (!m.compareAndSet(this, j2, ((long) b2) | j3));
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:34:0x006a, code lost:
        if (r9 != null) goto L_0x0075;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void h(long r9) {
        /*
            r8 = this;
            java.util.concurrent.atomic.AtomicIntegerFieldUpdater r0 = o
            r1 = 0
            r2 = 1
            boolean r0 = r0.compareAndSet(r8, r1, r2)
            if (r0 != 0) goto L_0x000b
            return
        L_0x000b:
            kotlinx.coroutines.scheduling.CoroutineScheduler$b r0 = r8.p()
            kotlinx.coroutines.scheduling.CoroutineScheduler$b[] r3 = r8.f1821g
            monitor-enter(r3)
            long r4 = r8.controlState     // Catch:{ all -> 0x00a3 }
            r6 = 2097151(0x1fffff, double:1.0361303E-317)
            long r4 = r4 & r6
            int r5 = (int) r4
            monitor-exit(r3)
            if (r2 > r5) goto L_0x005f
            r3 = 1
        L_0x001d:
            kotlinx.coroutines.scheduling.CoroutineScheduler$b[] r4 = r8.f1821g
            r4 = r4[r3]
            if (r4 == 0) goto L_0x005a
            if (r4 == r0) goto L_0x0055
        L_0x0025:
            boolean r6 = r4.isAlive()
            if (r6 == 0) goto L_0x0032
            java.util.concurrent.locks.LockSupport.unpark(r4)
            r4.join(r9)
            goto L_0x0025
        L_0x0032:
            kotlinx.coroutines.scheduling.CoroutineScheduler$WorkerState r6 = r4.getState()
            boolean r7 = kotlinx.coroutines.j0.a()
            if (r7 == 0) goto L_0x004c
            kotlinx.coroutines.scheduling.CoroutineScheduler$WorkerState r7 = kotlinx.coroutines.scheduling.CoroutineScheduler.WorkerState.TERMINATED
            if (r6 != r7) goto L_0x0042
            r6 = 1
            goto L_0x0043
        L_0x0042:
            r6 = 0
        L_0x0043:
            if (r6 == 0) goto L_0x0046
            goto L_0x004c
        L_0x0046:
            java.lang.AssertionError r9 = new java.lang.AssertionError
            r9.<init>()
            throw r9
        L_0x004c:
            kotlinx.coroutines.scheduling.m r4 = r4.c()
            kotlinx.coroutines.scheduling.d r6 = r8.e
            r4.a((kotlinx.coroutines.scheduling.d) r6)
        L_0x0055:
            if (r3 == r5) goto L_0x005f
            int r3 = r3 + 1
            goto L_0x001d
        L_0x005a:
            kotlin.jvm.internal.i.a()
            r9 = 0
            throw r9
        L_0x005f:
            kotlinx.coroutines.scheduling.d r9 = r8.e
            r9.a()
        L_0x0064:
            if (r0 == 0) goto L_0x006d
            kotlinx.coroutines.scheduling.h r9 = r0.a()
            if (r9 == 0) goto L_0x006d
            goto L_0x0075
        L_0x006d:
            kotlinx.coroutines.scheduling.d r9 = r8.e
            java.lang.Object r9 = r9.c()
            kotlinx.coroutines.scheduling.h r9 = (kotlinx.coroutines.scheduling.h) r9
        L_0x0075:
            if (r9 == 0) goto L_0x007b
            r8.a((kotlinx.coroutines.scheduling.h) r9)
            goto L_0x0064
        L_0x007b:
            if (r0 == 0) goto L_0x0082
            kotlinx.coroutines.scheduling.CoroutineScheduler$WorkerState r9 = kotlinx.coroutines.scheduling.CoroutineScheduler.WorkerState.TERMINATED
            r0.a((kotlinx.coroutines.scheduling.CoroutineScheduler.WorkerState) r9)
        L_0x0082:
            boolean r9 = kotlinx.coroutines.j0.a()
            if (r9 == 0) goto L_0x009c
            java.util.concurrent.Semaphore r9 = r8.f1820f
            int r9 = r9.availablePermits()
            int r10 = r8.f1823i
            if (r9 != r10) goto L_0x0093
            r1 = 1
        L_0x0093:
            if (r1 == 0) goto L_0x0096
            goto L_0x009c
        L_0x0096:
            java.lang.AssertionError r9 = new java.lang.AssertionError
            r9.<init>()
            throw r9
        L_0x009c:
            r9 = 0
            r8.parkedWorkersStack = r9
            r8.controlState = r9
            return
        L_0x00a3:
            r9 = move-exception
            monitor-exit(r3)
            goto L_0x00a7
        L_0x00a6:
            throw r9
        L_0x00a7:
            goto L_0x00a6
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.scheduling.CoroutineScheduler.h(long):void");
    }

    private final int a(b bVar) {
        Object d = bVar.d();
        while (d != t) {
            if (d == null) {
                return 0;
            }
            b bVar2 = (b) d;
            int b2 = bVar2.b();
            if (b2 != 0) {
                return b2;
            }
            d = bVar2.d();
        }
        return -1;
    }

    public static /* synthetic */ void a(CoroutineScheduler coroutineScheduler, Runnable runnable, i iVar, boolean z, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            iVar = g.f1839f;
        }
        if ((i2 & 4) != 0) {
            z = false;
        }
        coroutineScheduler.a(runnable, iVar, z);
    }

    public final void a(Runnable runnable, i iVar, boolean z) {
        i.b(runnable, "block");
        i.b(iVar, "taskContext");
        e2 a2 = f2.a();
        if (a2 != null) {
            a2.d();
        }
        h a3 = a(runnable, iVar);
        int a4 = a(a3, z);
        if (a4 == -1) {
            return;
        }
        if (a4 != 1) {
            t();
        } else if (this.e.a(a3)) {
            t();
        } else {
            throw new RejectedExecutionException(this.l + " was terminated");
        }
    }

    public final h a(Runnable runnable, i iVar) {
        i.b(runnable, "block");
        i.b(iVar, "taskContext");
        long a2 = k.f1842f.a();
        if (!(runnable instanceof h)) {
            return new j(runnable, a2, iVar);
        }
        h hVar = (h) runnable;
        hVar.e = a2;
        hVar.f1840f = iVar;
        return hVar;
    }

    private final int a(h hVar, boolean z) {
        boolean z2;
        b p2 = p();
        if (p2 == null || p2.getState() == WorkerState.TERMINATED) {
            return 1;
        }
        int i2 = -1;
        if (hVar.a() == TaskMode.NON_BLOCKING) {
            if (p2.g()) {
                i2 = 0;
            } else if (!p2.i()) {
                return 1;
            }
        }
        if (z) {
            z2 = p2.c().b(hVar, this.e);
        } else {
            z2 = p2.c().a(hVar, this.e);
        }
        if (!z2 || p2.c().a() > k.b) {
            return 0;
        }
        return i2;
    }

    /* access modifiers changed from: private */
    public final void a(h hVar) {
        e2 a2;
        try {
            hVar.run();
            a2 = f2.a();
            if (a2 == null) {
                return;
            }
        } catch (Throwable th) {
            e2 a3 = f2.a();
            if (a3 != null) {
                a3.c();
            }
            throw th;
        }
        a2.c();
    }

    /* access modifiers changed from: private */
    public final void a(b bVar, int i2, int i3) {
        while (true) {
            long j2 = this.parkedWorkersStack;
            int i4 = (int) (2097151 & j2);
            long j3 = (PlaybackStateCompat.ACTION_SET_SHUFFLE_MODE + j2) & -2097152;
            if (i4 == i2) {
                i4 = i3 == 0 ? a(bVar) : i3;
            }
            if (i4 >= 0) {
                if (m.compareAndSet(this, j2, j3 | ((long) i4))) {
                    return;
                }
            }
        }
    }
}
