package okhttp3.internal.connection;

import java.io.IOException;
import java.net.Socket;
import java.util.List;
import okhttp3.a0;
import okhttp3.d0;
import okhttp3.internal.connection.i;
import okhttp3.j;
import okhttp3.j0;
import okhttp3.k0.h.c;
import okhttp3.n;
import okhttp3.v;

/* compiled from: ExchangeFinder */
final class e {
    private final j a;
    private final okhttp3.e b;
    private final g c;
    private final j d;
    private final v e;

    /* renamed from: f  reason: collision with root package name */
    private i.a f1915f;

    /* renamed from: g  reason: collision with root package name */
    private final i f1916g;

    /* renamed from: h  reason: collision with root package name */
    private f f1917h;

    /* renamed from: i  reason: collision with root package name */
    private boolean f1918i;

    /* renamed from: j  reason: collision with root package name */
    private j0 f1919j;

    e(j jVar, g gVar, okhttp3.e eVar, j jVar2, v vVar) {
        this.a = jVar;
        this.c = gVar;
        this.b = eVar;
        this.d = jVar2;
        this.e = vVar;
        this.f1916g = new i(eVar, gVar.e, jVar2, vVar);
    }

    private boolean e() {
        f fVar = this.a.f1933i;
        return fVar != null && fVar.l == 0 && okhttp3.k0.e.a(fVar.e().a().k(), this.b.k());
    }

    public c a(d0 d0Var, a0.a aVar, boolean z) {
        try {
            return a(aVar.c(), aVar.d(), aVar.b(), d0Var.s(), d0Var.y(), z).a(d0Var, aVar);
        } catch (RouteException e2) {
            d();
            throw e2;
        } catch (IOException e3) {
            d();
            throw new RouteException(e3);
        }
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0033, code lost:
        return r2;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean b() {
        /*
            r3 = this;
            okhttp3.internal.connection.g r0 = r3.c
            monitor-enter(r0)
            okhttp3.j0 r1 = r3.f1919j     // Catch:{ all -> 0x0034 }
            r2 = 1
            if (r1 == 0) goto L_0x000a
            monitor-exit(r0)     // Catch:{ all -> 0x0034 }
            return r2
        L_0x000a:
            boolean r1 = r3.e()     // Catch:{ all -> 0x0034 }
            if (r1 == 0) goto L_0x001c
            okhttp3.internal.connection.j r1 = r3.a     // Catch:{ all -> 0x0034 }
            okhttp3.internal.connection.f r1 = r1.f1933i     // Catch:{ all -> 0x0034 }
            okhttp3.j0 r1 = r1.e()     // Catch:{ all -> 0x0034 }
            r3.f1919j = r1     // Catch:{ all -> 0x0034 }
            monitor-exit(r0)     // Catch:{ all -> 0x0034 }
            return r2
        L_0x001c:
            okhttp3.internal.connection.i$a r1 = r3.f1915f     // Catch:{ all -> 0x0034 }
            if (r1 == 0) goto L_0x0028
            okhttp3.internal.connection.i$a r1 = r3.f1915f     // Catch:{ all -> 0x0034 }
            boolean r1 = r1.b()     // Catch:{ all -> 0x0034 }
            if (r1 != 0) goto L_0x0032
        L_0x0028:
            okhttp3.internal.connection.i r1 = r3.f1916g     // Catch:{ all -> 0x0034 }
            boolean r1 = r1.a()     // Catch:{ all -> 0x0034 }
            if (r1 == 0) goto L_0x0031
            goto L_0x0032
        L_0x0031:
            r2 = 0
        L_0x0032:
            monitor-exit(r0)     // Catch:{ all -> 0x0034 }
            return r2
        L_0x0034:
            r1 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x0034 }
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: okhttp3.internal.connection.e.b():boolean");
    }

    /* access modifiers changed from: package-private */
    public boolean c() {
        boolean z;
        synchronized (this.c) {
            z = this.f1918i;
        }
        return z;
    }

    /* access modifiers changed from: package-private */
    public void d() {
        synchronized (this.c) {
            this.f1918i = true;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0018, code lost:
        if (r0.a(r9) != false) goto L_0x001e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x001e, code lost:
        return r0;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private okhttp3.internal.connection.f a(int r4, int r5, int r6, int r7, boolean r8, boolean r9) {
        /*
            r3 = this;
        L_0x0000:
            okhttp3.internal.connection.f r0 = r3.a(r4, r5, r6, r7, r8)
            okhttp3.internal.connection.g r1 = r3.c
            monitor-enter(r1)
            int r2 = r0.m     // Catch:{ all -> 0x001f }
            if (r2 != 0) goto L_0x0013
            boolean r2 = r0.c()     // Catch:{ all -> 0x001f }
            if (r2 != 0) goto L_0x0013
            monitor-exit(r1)     // Catch:{ all -> 0x001f }
            return r0
        L_0x0013:
            monitor-exit(r1)     // Catch:{ all -> 0x001f }
            boolean r1 = r0.a((boolean) r9)
            if (r1 != 0) goto L_0x001e
            r0.d()
            goto L_0x0000
        L_0x001e:
            return r0
        L_0x001f:
            r4 = move-exception
            monitor-exit(r1)     // Catch:{ all -> 0x001f }
            goto L_0x0023
        L_0x0022:
            throw r4
        L_0x0023:
            goto L_0x0022
        */
        throw new UnsupportedOperationException("Method not decompiled: okhttp3.internal.connection.e.a(int, int, int, int, boolean, boolean):okhttp3.internal.connection.f");
    }

    private f a(int i2, int i3, int i4, int i5, boolean z) {
        Socket socket;
        Socket g2;
        f fVar;
        f fVar2;
        boolean z2;
        j0 j0Var;
        boolean z3;
        List<j0> list;
        i.a aVar;
        synchronized (this.c) {
            if (!this.a.f()) {
                this.f1918i = false;
                f fVar3 = this.a.f1933i;
                socket = null;
                g2 = (this.a.f1933i == null || !this.a.f1933i.k) ? null : this.a.g();
                if (this.a.f1933i != null) {
                    fVar2 = this.a.f1933i;
                    fVar = null;
                } else {
                    fVar = fVar3;
                    fVar2 = null;
                }
                if (fVar2 == null) {
                    if (this.c.a(this.b, this.a, (List<j0>) null, false)) {
                        fVar2 = this.a.f1933i;
                        j0Var = null;
                        z2 = true;
                    } else {
                        if (this.f1919j != null) {
                            j0Var = this.f1919j;
                            this.f1919j = null;
                        } else if (e()) {
                            j0Var = this.a.f1933i.e();
                        }
                        z2 = false;
                    }
                }
                j0Var = null;
                z2 = false;
            } else {
                throw new IOException("Canceled");
            }
        }
        okhttp3.k0.e.a(g2);
        if (fVar != null) {
            this.e.b(this.d, (n) fVar);
        }
        if (z2) {
            this.e.a(this.d, (n) fVar2);
        }
        if (fVar2 != null) {
            return fVar2;
        }
        if (j0Var != null || ((aVar = this.f1915f) != null && aVar.b())) {
            z3 = false;
        } else {
            this.f1915f = this.f1916g.b();
            z3 = true;
        }
        synchronized (this.c) {
            if (!this.a.f()) {
                if (z3) {
                    list = this.f1915f.a();
                    if (this.c.a(this.b, this.a, list, false)) {
                        fVar2 = this.a.f1933i;
                        z2 = true;
                    }
                } else {
                    list = null;
                }
                if (!z2) {
                    if (j0Var == null) {
                        j0Var = this.f1915f.c();
                    }
                    fVar2 = new f(this.c, j0Var);
                    this.f1917h = fVar2;
                }
            } else {
                throw new IOException("Canceled");
            }
        }
        if (z2) {
            this.e.a(this.d, (n) fVar2);
            return fVar2;
        }
        fVar2.a(i2, i3, i4, i5, z, this.d, this.e);
        this.c.e.a(fVar2.e());
        synchronized (this.c) {
            this.f1917h = null;
            if (this.c.a(this.b, this.a, list, true)) {
                fVar2.k = true;
                socket = fVar2.f();
                fVar2 = this.a.f1933i;
                this.f1919j = j0Var;
            } else {
                this.c.b(fVar2);
                this.a.a(fVar2);
            }
        }
        okhttp3.k0.e.a(socket);
        this.e.a(this.d, (n) fVar2);
        return fVar2;
    }

    /* access modifiers changed from: package-private */
    public f a() {
        return this.f1917h;
    }
}
