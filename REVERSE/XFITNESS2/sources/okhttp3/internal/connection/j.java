package okhttp3.internal.connection;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.lang.ref.WeakReference;
import java.net.Socket;
import java.util.concurrent.TimeUnit;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSocketFactory;
import okhttp3.a0;
import okhttp3.d0;
import okhttp3.f0;
import okhttp3.k0.c;
import okhttp3.k0.e;
import okhttp3.k0.j.f;
import okhttp3.l;
import okhttp3.n;
import okhttp3.v;
import okhttp3.z;

/* compiled from: Transmitter */
public final class j {
    private final d0 a;
    private final g b;
    private final okhttp3.j c;
    private final v d;
    private final okio.a e = new a();

    /* renamed from: f  reason: collision with root package name */
    private Object f1930f;

    /* renamed from: g  reason: collision with root package name */
    private f0 f1931g;

    /* renamed from: h  reason: collision with root package name */
    private e f1932h;

    /* renamed from: i  reason: collision with root package name */
    public f f1933i;

    /* renamed from: j  reason: collision with root package name */
    private d f1934j;
    private boolean k;
    private boolean l;
    private boolean m;
    private boolean n;
    private boolean o;

    /* compiled from: Transmitter */
    class a extends okio.a {
        a() {
        }

        /* access modifiers changed from: protected */
        public void i() {
            j.this.c();
        }
    }

    /* compiled from: Transmitter */
    static final class b extends WeakReference<j> {
        final Object a;

        b(j jVar, Object obj) {
            super(jVar);
            this.a = obj;
        }
    }

    public j(d0 d0Var, okhttp3.j jVar) {
        this.a = d0Var;
        this.b = c.a.a(d0Var.g());
        this.c = jVar;
        this.d = d0Var.l().a(jVar);
        this.e.a((long) d0Var.d(), TimeUnit.MILLISECONDS);
    }

    private IOException b(IOException iOException) {
        if (this.n || !this.e.h()) {
            return iOException;
        }
        InterruptedIOException interruptedIOException = new InterruptedIOException("timeout");
        if (iOException != null) {
            interruptedIOException.initCause(iOException);
        }
        return interruptedIOException;
    }

    public void a() {
        this.f1930f = f.c().a("response.body().close()");
        this.d.b(this.c);
    }

    public void c() {
        d dVar;
        f fVar;
        synchronized (this.b) {
            this.m = true;
            dVar = this.f1934j;
            if (this.f1932h == null || this.f1932h.a() == null) {
                fVar = this.f1933i;
            } else {
                fVar = this.f1932h.a();
            }
        }
        if (dVar != null) {
            dVar.a();
        } else if (fVar != null) {
            fVar.a();
        }
    }

    public void d() {
        synchronized (this.b) {
            if (!this.o) {
                this.f1934j = null;
            } else {
                throw new IllegalStateException();
            }
        }
    }

    public boolean e() {
        boolean z;
        synchronized (this.b) {
            z = this.f1934j != null;
        }
        return z;
    }

    public boolean f() {
        boolean z;
        synchronized (this.b) {
            z = this.m;
        }
        return z;
    }

    /* access modifiers changed from: package-private */
    public Socket g() {
        int i2 = 0;
        int size = this.f1933i.p.size();
        while (true) {
            if (i2 >= size) {
                i2 = -1;
                break;
            } else if (this.f1933i.p.get(i2).get() == this) {
                break;
            } else {
                i2++;
            }
        }
        if (i2 != -1) {
            f fVar = this.f1933i;
            fVar.p.remove(i2);
            this.f1933i = null;
            if (!fVar.p.isEmpty()) {
                return null;
            }
            fVar.q = System.nanoTime();
            if (this.b.a(fVar)) {
                return fVar.f();
            }
            return null;
        }
        throw new IllegalStateException();
    }

    public void h() {
        if (!this.n) {
            this.n = true;
            this.e.h();
            return;
        }
        throw new IllegalStateException();
    }

    public void i() {
        this.e.g();
    }

    public void a(f0 f0Var) {
        f0 f0Var2 = this.f1931g;
        if (f0Var2 != null) {
            if (e.a(f0Var2.g(), f0Var.g()) && this.f1932h.b()) {
                return;
            }
            if (this.f1934j != null) {
                throw new IllegalStateException();
            } else if (this.f1932h != null) {
                a((IOException) null, true);
                this.f1932h = null;
            }
        }
        this.f1931g = f0Var;
        this.f1932h = new e(this, this.b, a(f0Var.g()), this.c, this.d);
    }

    public boolean b() {
        return this.f1932h.c() && this.f1932h.b();
    }

    private okhttp3.e a(z zVar) {
        l lVar;
        HostnameVerifier hostnameVerifier;
        SSLSocketFactory sSLSocketFactory;
        if (zVar.h()) {
            SSLSocketFactory A = this.a.A();
            hostnameVerifier = this.a.o();
            sSLSocketFactory = A;
            lVar = this.a.e();
        } else {
            sSLSocketFactory = null;
            hostnameVerifier = null;
            lVar = null;
        }
        return new okhttp3.e(zVar.g(), zVar.k(), this.a.k(), this.a.z(), sSLSocketFactory, hostnameVerifier, lVar, this.a.v(), this.a.u(), this.a.t(), this.a.h(), this.a.w());
    }

    /* access modifiers changed from: package-private */
    public d a(a0.a aVar, boolean z) {
        synchronized (this.b) {
            if (this.o) {
                throw new IllegalStateException("released");
            } else if (this.f1934j != null) {
                throw new IllegalStateException("cannot make a new request because the previous response is still open: please call response.close()");
            }
        }
        d dVar = new d(this, this.c, this.d, this.f1932h, this.f1932h.a(this.a, aVar, z));
        synchronized (this.b) {
            this.f1934j = dVar;
            this.k = false;
            this.l = false;
        }
        return dVar;
    }

    /* access modifiers changed from: package-private */
    public void a(f fVar) {
        if (this.f1933i == null) {
            this.f1933i = fVar;
            fVar.p.add(new b(this, this.f1930f));
            return;
        }
        throw new IllegalStateException();
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x0038, code lost:
        if (r1 == false) goto L_?;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:?, code lost:
        return a(r6, false);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:?, code lost:
        return r6;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.io.IOException a(okhttp3.internal.connection.d r3, boolean r4, boolean r5, java.io.IOException r6) {
        /*
            r2 = this;
            okhttp3.internal.connection.g r0 = r2.b
            monitor-enter(r0)
            okhttp3.internal.connection.d r1 = r2.f1934j     // Catch:{ all -> 0x003f }
            if (r3 == r1) goto L_0x0009
            monitor-exit(r0)     // Catch:{ all -> 0x003f }
            return r6
        L_0x0009:
            r3 = 0
            r1 = 1
            if (r4 == 0) goto L_0x0013
            boolean r4 = r2.k     // Catch:{ all -> 0x003f }
            r4 = r4 ^ r1
            r2.k = r1     // Catch:{ all -> 0x003f }
            goto L_0x0014
        L_0x0013:
            r4 = 0
        L_0x0014:
            if (r5 == 0) goto L_0x001d
            boolean r5 = r2.l     // Catch:{ all -> 0x003f }
            if (r5 != 0) goto L_0x001b
            r4 = 1
        L_0x001b:
            r2.l = r1     // Catch:{ all -> 0x003f }
        L_0x001d:
            boolean r5 = r2.k     // Catch:{ all -> 0x003f }
            if (r5 == 0) goto L_0x0036
            boolean r5 = r2.l     // Catch:{ all -> 0x003f }
            if (r5 == 0) goto L_0x0036
            if (r4 == 0) goto L_0x0036
            okhttp3.internal.connection.d r4 = r2.f1934j     // Catch:{ all -> 0x003f }
            okhttp3.internal.connection.f r4 = r4.b()     // Catch:{ all -> 0x003f }
            int r5 = r4.m     // Catch:{ all -> 0x003f }
            int r5 = r5 + r1
            r4.m = r5     // Catch:{ all -> 0x003f }
            r4 = 0
            r2.f1934j = r4     // Catch:{ all -> 0x003f }
            goto L_0x0037
        L_0x0036:
            r1 = 0
        L_0x0037:
            monitor-exit(r0)     // Catch:{ all -> 0x003f }
            if (r1 == 0) goto L_0x003e
            java.io.IOException r6 = r2.a((java.io.IOException) r6, (boolean) r3)
        L_0x003e:
            return r6
        L_0x003f:
            r3 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x003f }
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: okhttp3.internal.connection.j.a(okhttp3.internal.connection.d, boolean, boolean, java.io.IOException):java.io.IOException");
    }

    public IOException a(IOException iOException) {
        synchronized (this.b) {
            this.o = true;
        }
        return a(iOException, false);
    }

    private IOException a(IOException iOException, boolean z) {
        f fVar;
        Socket g2;
        boolean z2;
        boolean z3;
        synchronized (this.b) {
            if (z) {
                if (this.f1934j != null) {
                    throw new IllegalStateException("cannot release connection while it is in use");
                }
            }
            fVar = this.f1933i;
            g2 = (this.f1933i == null || this.f1934j != null || (!z && !this.o)) ? null : g();
            if (this.f1933i != null) {
                fVar = null;
            }
            z2 = true;
            z3 = this.o && this.f1934j == null;
        }
        e.a(g2);
        if (fVar != null) {
            this.d.b(this.c, (n) fVar);
        }
        if (z3) {
            if (iOException == null) {
                z2 = false;
            }
            iOException = b(iOException);
            if (z2) {
                this.d.a(this.c, iOException);
            } else {
                this.d.a(this.c);
            }
        }
        return iOException;
    }
}
