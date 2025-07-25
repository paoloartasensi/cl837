package okhttp3.internal.connection;

import java.io.IOException;
import java.lang.ref.Reference;
import java.net.Proxy;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import okhttp3.internal.connection.j;
import okhttp3.j0;
import okhttp3.k0.e;
import okhttp3.k0.j.f;

/* compiled from: RealConnectionPool */
public final class g {

    /* renamed from: g  reason: collision with root package name */
    private static final Executor f1925g = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60, TimeUnit.SECONDS, new SynchronousQueue(), e.a("OkHttp ConnectionPool", true));
    private final int a;
    private final long b;
    private final Runnable c = new a(this);
    private final Deque<f> d = new ArrayDeque();
    final h e = new h();

    /* renamed from: f  reason: collision with root package name */
    boolean f1926f;

    public g(int i2, long j2, TimeUnit timeUnit) {
        this.a = i2;
        this.b = timeUnit.toNanos(j2);
        if (j2 <= 0) {
            throw new IllegalArgumentException("keepAliveDuration <= 0: " + j2);
        }
    }

    /* JADX WARNING: Exception block dominator not found, dom blocks: [] */
    /* JADX WARNING: Missing exception handler attribute for start block: B:11:0x0025 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public /* synthetic */ void a() {
        /*
            r6 = this;
        L_0x0000:
            long r0 = java.lang.System.nanoTime()
            long r0 = r6.a((long) r0)
            r2 = -1
            int r4 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r4 != 0) goto L_0x000f
            return
        L_0x000f:
            r2 = 0
            int r4 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r4 <= 0) goto L_0x0000
            r2 = 1000000(0xf4240, double:4.940656E-318)
            long r4 = r0 / r2
            long r2 = r2 * r4
            long r0 = r0 - r2
            monitor-enter(r6)
            int r1 = (int) r0
            r6.wait(r4, r1)     // Catch:{ InterruptedException -> 0x0025 }
            goto L_0x0025
        L_0x0023:
            r0 = move-exception
            goto L_0x0027
        L_0x0025:
            monitor-exit(r6)     // Catch:{ all -> 0x0023 }
            goto L_0x0000
        L_0x0027:
            monitor-exit(r6)     // Catch:{ all -> 0x0023 }
            goto L_0x002a
        L_0x0029:
            throw r0
        L_0x002a:
            goto L_0x0029
        */
        throw new UnsupportedOperationException("Method not decompiled: okhttp3.internal.connection.g.a():void");
    }

    /* access modifiers changed from: package-private */
    public void b(f fVar) {
        if (!this.f1926f) {
            this.f1926f = true;
            f1925g.execute(this.c);
        }
        this.d.add(fVar);
    }

    /* access modifiers changed from: package-private */
    public boolean a(okhttp3.e eVar, j jVar, List<j0> list, boolean z) {
        for (f next : this.d) {
            if ((!z || next.c()) && next.a(eVar, list)) {
                jVar.a(next);
                return true;
            }
        }
        return false;
    }

    /* access modifiers changed from: package-private */
    public boolean a(f fVar) {
        if (fVar.k || this.a == 0) {
            this.d.remove(fVar);
            return true;
        }
        notifyAll();
        return false;
    }

    /* access modifiers changed from: package-private */
    public long a(long j2) {
        synchronized (this) {
            f fVar = null;
            long j3 = Long.MIN_VALUE;
            int i2 = 0;
            int i3 = 0;
            for (f next : this.d) {
                if (a(next, j2) > 0) {
                    i3++;
                } else {
                    i2++;
                    long j4 = j2 - next.q;
                    if (j4 > j3) {
                        fVar = next;
                        j3 = j4;
                    }
                }
            }
            if (j3 < this.b) {
                if (i2 <= this.a) {
                    if (i2 > 0) {
                        long j5 = this.b - j3;
                        return j5;
                    } else if (i3 > 0) {
                        long j6 = this.b;
                        return j6;
                    } else {
                        this.f1926f = false;
                        return -1;
                    }
                }
            }
            this.d.remove(fVar);
            e.a(fVar.f());
            return 0;
        }
    }

    private int a(f fVar, long j2) {
        List<Reference<j>> list = fVar.p;
        int i2 = 0;
        while (i2 < list.size()) {
            Reference reference = list.get(i2);
            if (reference.get() != null) {
                i2++;
            } else {
                f.c().a("A connection to " + fVar.e().a().k() + " was leaked. Did you forget to close a response body?", ((j.b) reference).a);
                list.remove(i2);
                fVar.k = true;
                if (list.isEmpty()) {
                    fVar.q = j2 - this.b;
                    return 0;
                }
            }
        }
        return list.size();
    }

    public void a(j0 j0Var, IOException iOException) {
        if (j0Var.b().type() != Proxy.Type.DIRECT) {
            okhttp3.e a2 = j0Var.a();
            a2.h().connectFailed(a2.k().o(), j0Var.b().address(), iOException);
        }
        this.e.b(j0Var);
    }
}
