package okhttp3.internal.connection;

import java.io.IOException;
import java.net.ProtocolException;
import okhttp3.f0;
import okhttp3.h0;
import okhttp3.i0;
import okhttp3.j;
import okhttp3.k0.h.c;
import okhttp3.k0.h.h;
import okhttp3.v;
import okio.f;
import okio.g;
import okio.k;
import okio.q;
import okio.r;

/* compiled from: Exchange */
public final class d {
    final j a;
    final j b;
    final v c;
    final e d;
    final c e;

    /* renamed from: f  reason: collision with root package name */
    private boolean f1904f;

    /* compiled from: Exchange */
    final class b extends g {

        /* renamed from: f  reason: collision with root package name */
        private final long f1910f;

        /* renamed from: g  reason: collision with root package name */
        private long f1911g;

        /* renamed from: h  reason: collision with root package name */
        private boolean f1912h;

        /* renamed from: i  reason: collision with root package name */
        private boolean f1913i;

        b(r rVar, long j2) {
            super(rVar);
            this.f1910f = j2;
            if (j2 == 0) {
                a((IOException) null);
            }
        }

        /* access modifiers changed from: package-private */
        public IOException a(IOException iOException) {
            if (this.f1912h) {
                return iOException;
            }
            this.f1912h = true;
            return d.this.a(this.f1911g, true, false, iOException);
        }

        public long b(okio.c cVar, long j2) {
            if (!this.f1913i) {
                try {
                    long b = a().b(cVar, j2);
                    if (b == -1) {
                        a((IOException) null);
                        return -1;
                    }
                    long j3 = this.f1911g + b;
                    if (this.f1910f != -1) {
                        if (j3 > this.f1910f) {
                            throw new ProtocolException("expected " + this.f1910f + " bytes but received " + j3);
                        }
                    }
                    this.f1911g = j3;
                    if (j3 == this.f1910f) {
                        a((IOException) null);
                    }
                    return b;
                } catch (IOException e) {
                    throw a(e);
                }
            } else {
                throw new IllegalStateException("closed");
            }
        }

        public void close() {
            if (!this.f1913i) {
                this.f1913i = true;
                try {
                    super.close();
                    a((IOException) null);
                } catch (IOException e) {
                    throw a(e);
                }
            }
        }
    }

    public d(j jVar, j jVar2, v vVar, e eVar, c cVar) {
        this.a = jVar;
        this.b = jVar2;
        this.c = vVar;
        this.d = eVar;
        this.e = cVar;
    }

    public void a(f0 f0Var) {
        try {
            this.c.d(this.b);
            this.e.a(f0Var);
            this.c.a(this.b, f0Var);
        } catch (IOException e2) {
            this.c.b(this.b, e2);
            a(e2);
            throw e2;
        }
    }

    public f b() {
        return this.e.c();
    }

    public void c() {
        this.e.cancel();
        this.a.a(this, true, true, (IOException) null);
    }

    public void d() {
        try {
            this.e.a();
        } catch (IOException e2) {
            this.c.b(this.b, e2);
            a(e2);
            throw e2;
        }
    }

    public void e() {
        try {
            this.e.b();
        } catch (IOException e2) {
            this.c.b(this.b, e2);
            a(e2);
            throw e2;
        }
    }

    public boolean f() {
        return this.f1904f;
    }

    public void g() {
        this.e.c().d();
    }

    public void h() {
        this.a.a(this, true, false, (IOException) null);
    }

    public void i() {
        this.c.f(this.b);
    }

    public void b(h0 h0Var) {
        this.c.a(this.b, h0Var);
    }

    /* compiled from: Exchange */
    private final class a extends f {

        /* renamed from: f  reason: collision with root package name */
        private boolean f1905f;

        /* renamed from: g  reason: collision with root package name */
        private long f1906g;

        /* renamed from: h  reason: collision with root package name */
        private long f1907h;

        /* renamed from: i  reason: collision with root package name */
        private boolean f1908i;

        a(q qVar, long j2) {
            super(qVar);
            this.f1906g = j2;
        }

        public void a(okio.c cVar, long j2) {
            if (!this.f1908i) {
                long j3 = this.f1906g;
                if (j3 == -1 || this.f1907h + j2 <= j3) {
                    try {
                        super.a(cVar, j2);
                        this.f1907h += j2;
                    } catch (IOException e) {
                        throw a(e);
                    }
                } else {
                    throw new ProtocolException("expected " + this.f1906g + " bytes but received " + (this.f1907h + j2));
                }
            } else {
                throw new IllegalStateException("closed");
            }
        }

        public void close() {
            if (!this.f1908i) {
                this.f1908i = true;
                long j2 = this.f1906g;
                if (j2 == -1 || this.f1907h == j2) {
                    try {
                        super.close();
                        a((IOException) null);
                    } catch (IOException e) {
                        throw a(e);
                    }
                } else {
                    throw new ProtocolException("unexpected end of stream");
                }
            }
        }

        public void flush() {
            try {
                super.flush();
            } catch (IOException e) {
                throw a(e);
            }
        }

        private IOException a(IOException iOException) {
            if (this.f1905f) {
                return iOException;
            }
            this.f1905f = true;
            return d.this.a(this.f1907h, false, true, iOException);
        }
    }

    public q a(f0 f0Var, boolean z) {
        this.f1904f = z;
        long a2 = f0Var.a().a();
        this.c.c(this.b);
        return new a(this.e.a(f0Var, a2), a2);
    }

    public h0.a a(boolean z) {
        try {
            h0.a a2 = this.e.a(z);
            if (a2 != null) {
                okhttp3.k0.c.a.a(a2, this);
            }
            return a2;
        } catch (IOException e2) {
            this.c.c(this.b, e2);
            a(e2);
            throw e2;
        }
    }

    public i0 a(h0 h0Var) {
        try {
            this.c.e(this.b);
            String b2 = h0Var.b("Content-Type");
            long a2 = this.e.a(h0Var);
            return new h(b2, a2, k.a((r) new b(this.e.b(h0Var), a2)));
        } catch (IOException e2) {
            this.c.c(this.b, e2);
            a(e2);
            throw e2;
        }
    }

    public void a() {
        this.e.cancel();
    }

    /* access modifiers changed from: package-private */
    public void a(IOException iOException) {
        this.d.d();
        this.e.c().a(iOException);
    }

    /* access modifiers changed from: package-private */
    public IOException a(long j2, boolean z, boolean z2, IOException iOException) {
        if (iOException != null) {
            a(iOException);
        }
        if (z2) {
            if (iOException != null) {
                this.c.b(this.b, iOException);
            } else {
                this.c.a(this.b, j2);
            }
        }
        if (z) {
            if (iOException != null) {
                this.c.c(this.b, iOException);
            } else {
                this.c.b(this.b, j2);
            }
        }
        return this.a.a(this, z2, z, iOException);
    }
}
