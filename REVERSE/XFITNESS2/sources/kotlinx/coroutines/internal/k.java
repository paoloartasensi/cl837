package kotlinx.coroutines.internal;

import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceArray;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import kotlinx.coroutines.j0;

/* compiled from: LockFreeTaskQueue.kt */
public final class k<E> {
    private static final AtomicReferenceFieldUpdater e;

    /* renamed from: f  reason: collision with root package name */
    public static final /* synthetic */ AtomicLongFieldUpdater f1800f;

    /* renamed from: g  reason: collision with root package name */
    public static final t f1801g = new t("REMOVE_FROZEN");

    /* renamed from: h  reason: collision with root package name */
    public static final a f1802h = new a((f) null);
    private volatile Object _next = null;
    public volatile /* synthetic */ long _state$internal = 0;
    /* access modifiers changed from: private */
    public final int a;
    public /* synthetic */ AtomicReferenceArray b = new AtomicReferenceArray(this.c);
    private final int c;
    /* access modifiers changed from: private */
    public final boolean d;

    /* compiled from: LockFreeTaskQueue.kt */
    public static final class a {
        private a() {
        }

        public final int a(long j2) {
            return (j2 & 2305843009213693952L) != 0 ? 2 : 1;
        }

        public final long a(long j2, int i2) {
            return a(j2, 1073741823) | (((long) i2) << 0);
        }

        public final long a(long j2, long j3) {
            return j2 & (j3 ^ -1);
        }

        public final long b(long j2, int i2) {
            return a(j2, 1152921503533105152L) | (((long) i2) << 30);
        }

        public /* synthetic */ a(f fVar) {
            this();
        }
    }

    /* compiled from: LockFreeTaskQueue.kt */
    public static final class b {
        public final int a;

        public b(int i2) {
            this.a = i2;
        }
    }

    static {
        Class<k> cls = k.class;
        e = AtomicReferenceFieldUpdater.newUpdater(cls, Object.class, "_next");
        f1800f = AtomicLongFieldUpdater.newUpdater(cls, "_state$internal");
    }

    public k(int i2, boolean z) {
        this.c = i2;
        this.d = z;
        boolean z2 = true;
        this.a = i2 - 1;
        if (this.a <= 1073741823) {
            if (!((this.c & this.a) != 0 ? false : z2)) {
                throw new IllegalStateException("Check failed.".toString());
            }
            return;
        }
        throw new IllegalStateException("Check failed.".toString());
    }

    private final long f() {
        long j2;
        long j3;
        do {
            j2 = this._state$internal;
            if ((j2 & 1152921504606846976L) != 0) {
                return j2;
            }
            j3 = j2 | 1152921504606846976L;
        } while (!f1800f.compareAndSet(this, j2, j3));
        return j3;
    }

    public final boolean c() {
        long j2 = this._state$internal;
        return ((int) ((1073741823 & j2) >> 0)) == ((int) ((j2 & 1152921503533105152L) >> 30));
    }

    public final k<E> d() {
        return b(f());
    }

    public final Object e() {
        Object obj;
        while (true) {
            long j2 = this._state$internal;
            if ((1152921504606846976L & j2) == 0) {
                int i2 = (int) ((1073741823 & j2) >> 0);
                if ((this.a & ((int) ((1152921503533105152L & j2) >> 30))) != (this.a & i2)) {
                    obj = this.b.get(this.a & i2);
                    if (obj != null) {
                        if (!(obj instanceof b)) {
                            int i3 = (i2 + 1) & 1073741823;
                            if (!f1800f.compareAndSet(this, j2, f1802h.a(j2, i3))) {
                                if (this.d) {
                                    k kVar = this;
                                    do {
                                        kVar = kVar.a(i2, i3);
                                    } while (kVar != null);
                                    break;
                                }
                            } else {
                                this.b.set(this.a & i2, (Object) null);
                                break;
                            }
                        } else {
                            return null;
                        }
                    } else if (this.d) {
                        return null;
                    }
                } else {
                    return null;
                }
            } else {
                return f1801g;
            }
        }
        return obj;
    }

    public final int b() {
        long j2 = this._state$internal;
        return 1073741823 & (((int) ((j2 & 1152921503533105152L) >> 30)) - ((int) ((1073741823 & j2) >> 0)));
    }

    private final k<E> a(int i2, E e2) {
        Object obj = this.b.get(this.a & i2);
        if (!(obj instanceof b) || ((b) obj).a != i2) {
            return null;
        }
        this.b.set(i2 & this.a, e2);
        return this;
    }

    private final k<E> b(long j2) {
        while (true) {
            k<E> kVar = (k) this._next;
            if (kVar != null) {
                return kVar;
            }
            e.compareAndSet(this, (Object) null, a(j2));
        }
    }

    private final k<E> a(long j2) {
        k<E> kVar = new k<>(this.c * 2, this.d);
        int i2 = (int) ((1073741823 & j2) >> 0);
        int i3 = (int) ((1152921503533105152L & j2) >> 30);
        while (true) {
            int i4 = this.a;
            if ((i2 & i4) != (i3 & i4)) {
                Object obj = this.b.get(i4 & i2);
                if (obj == null) {
                    obj = new b(i2);
                }
                kVar.b.set(kVar.a & i2, obj);
                i2++;
            } else {
                kVar._state$internal = f1802h.a(j2, 1152921504606846976L);
                return kVar;
            }
        }
    }

    public final boolean a() {
        long j2;
        do {
            j2 = this._state$internal;
            if ((j2 & 2305843009213693952L) != 0) {
                return true;
            }
            if ((1152921504606846976L & j2) != 0) {
                return false;
            }
        } while (!f1800f.compareAndSet(this, j2, j2 | 2305843009213693952L));
        return true;
    }

    /* JADX WARNING: Removed duplicated region for block: B:20:0x006c A[LOOP:1: B:20:0x006c->B:23:0x007e, LOOP_START, PHI: r1 
      PHI: (r1v7 kotlinx.coroutines.internal.k) = (r1v6 kotlinx.coroutines.internal.k), (r1v9 kotlinx.coroutines.internal.k) binds: [B:19:0x0064, B:23:0x007e] A[DONT_GENERATE, DONT_INLINE]] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final int a(E r13) {
        /*
            r12 = this;
            java.lang.String r0 = "element"
            kotlin.jvm.internal.i.b(r13, r0)
        L_0x0005:
            long r3 = r12._state$internal
            r0 = 3458764513820540928(0x3000000000000000, double:1.727233711018889E-77)
            long r0 = r0 & r3
            r7 = 0
            int r2 = (r0 > r7 ? 1 : (r0 == r7 ? 0 : -1))
            if (r2 == 0) goto L_0x0017
            kotlinx.coroutines.internal.k$a r13 = f1802h
            int r13 = r13.a(r3)
            return r13
        L_0x0017:
            r0 = 1073741823(0x3fffffff, double:5.304989472E-315)
            long r0 = r0 & r3
            r9 = 0
            long r0 = r0 >> r9
            int r1 = (int) r0
            r5 = 1152921503533105152(0xfffffffc0000000, double:1.2882296003504729E-231)
            long r5 = r5 & r3
            r0 = 30
            long r5 = r5 >> r0
            int r0 = (int) r5
            int r10 = r12.a
            int r2 = r0 + 2
            r2 = r2 & r10
            r5 = r1 & r10
            r6 = 1
            if (r2 != r5) goto L_0x0033
            return r6
        L_0x0033:
            boolean r2 = r12.d
            r5 = 1073741823(0x3fffffff, float:1.9999999)
            if (r2 != 0) goto L_0x0051
            java.util.concurrent.atomic.AtomicReferenceArray r2 = r12.b
            r11 = r0 & r10
            java.lang.Object r2 = r2.get(r11)
            if (r2 == 0) goto L_0x0051
            int r2 = r12.c
            r3 = 1024(0x400, float:1.435E-42)
            if (r2 < r3) goto L_0x0050
            int r0 = r0 - r1
            r0 = r0 & r5
            int r1 = r2 >> 1
            if (r0 <= r1) goto L_0x0005
        L_0x0050:
            return r6
        L_0x0051:
            int r1 = r0 + 1
            r1 = r1 & r5
            java.util.concurrent.atomic.AtomicLongFieldUpdater r2 = f1800f
            kotlinx.coroutines.internal.k$a r5 = f1802h
            long r5 = r5.b(r3, r1)
            r1 = r2
            r2 = r12
            boolean r1 = r1.compareAndSet(r2, r3, r5)
            if (r1 == 0) goto L_0x0005
            java.util.concurrent.atomic.AtomicReferenceArray r1 = r12.b
            r2 = r0 & r10
            r1.set(r2, r13)
            r1 = r12
        L_0x006c:
            long r2 = r1._state$internal
            r4 = 1152921504606846976(0x1000000000000000, double:1.2882297539194267E-231)
            long r2 = r2 & r4
            int r4 = (r2 > r7 ? 1 : (r2 == r7 ? 0 : -1))
            if (r4 != 0) goto L_0x0076
            goto L_0x0081
        L_0x0076:
            kotlinx.coroutines.internal.k r1 = r1.d()
            kotlinx.coroutines.internal.k r1 = r1.a((int) r0, r13)
            if (r1 == 0) goto L_0x0081
            goto L_0x006c
        L_0x0081:
            return r9
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.internal.k.a(java.lang.Object):int");
    }

    /* access modifiers changed from: private */
    public final k<E> a(int i2, int i3) {
        long j2;
        int i4;
        do {
            j2 = this._state$internal;
            boolean z = false;
            i4 = (int) ((1073741823 & j2) >> 0);
            if (j0.a()) {
                if (i4 == i2) {
                    z = true;
                }
                if (!z) {
                    throw new AssertionError();
                }
            }
            if ((1152921504606846976L & j2) != 0) {
                return d();
            }
        } while (!f1800f.compareAndSet(this, j2, f1802h.a(j2, i3)));
        this.b.set(this.a & i4, (Object) null);
        return null;
    }
}
