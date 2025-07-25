package retrofit2;

import java.io.IOException;
import okhttp3.b0;
import okhttp3.f0;
import okhttp3.h0;
import okhttp3.i0;
import okhttp3.j;
import okio.e;
import okio.g;
import okio.k;
import okio.r;

/* compiled from: OkHttpCall */
final class l<T> implements d<T> {
    private final q e;

    /* renamed from: f  reason: collision with root package name */
    private final Object[] f2120f;

    /* renamed from: g  reason: collision with root package name */
    private final j.a f2121g;

    /* renamed from: h  reason: collision with root package name */
    private final h<i0, T> f2122h;

    /* renamed from: i  reason: collision with root package name */
    private volatile boolean f2123i;

    /* renamed from: j  reason: collision with root package name */
    private j f2124j;
    private Throwable k;
    private boolean l;

    /* compiled from: OkHttpCall */
    static final class b extends i0 {

        /* renamed from: f  reason: collision with root package name */
        private final i0 f2125f;

        /* renamed from: g  reason: collision with root package name */
        private final e f2126g;

        /* renamed from: h  reason: collision with root package name */
        IOException f2127h;

        /* compiled from: OkHttpCall */
        class a extends g {
            a(r rVar) {
                super(rVar);
            }

            public long b(okio.c cVar, long j2) {
                try {
                    return super.b(cVar, j2);
                } catch (IOException e) {
                    b.this.f2127h = e;
                    throw e;
                }
            }
        }

        b(i0 i0Var) {
            this.f2125f = i0Var;
            this.f2126g = k.a((r) new a(i0Var.m()));
        }

        public long c() {
            return this.f2125f.c();
        }

        public void close() {
            this.f2125f.close();
        }

        public b0 j() {
            return this.f2125f.j();
        }

        public e m() {
            return this.f2126g;
        }

        /* access modifiers changed from: package-private */
        public void n() {
            IOException iOException = this.f2127h;
            if (iOException != null) {
                throw iOException;
            }
        }
    }

    /* compiled from: OkHttpCall */
    static final class c extends i0 {

        /* renamed from: f  reason: collision with root package name */
        private final b0 f2129f;

        /* renamed from: g  reason: collision with root package name */
        private final long f2130g;

        c(b0 b0Var, long j2) {
            this.f2129f = b0Var;
            this.f2130g = j2;
        }

        public long c() {
            return this.f2130g;
        }

        public b0 j() {
            return this.f2129f;
        }

        public e m() {
            throw new IllegalStateException("Cannot read raw response body of a converted body.");
        }
    }

    l(q qVar, Object[] objArr, j.a aVar, h<i0, T> hVar) {
        this.e = qVar;
        this.f2120f = objArr;
        this.f2121g = aVar;
        this.f2122h = hVar;
    }

    private j b() {
        j a2 = this.f2121g.a(this.e.a(this.f2120f));
        if (a2 != null) {
            return a2;
        }
        throw new NullPointerException("Call.Factory returned null.");
    }

    private j d() {
        j jVar = this.f2124j;
        if (jVar != null) {
            return jVar;
        }
        Throwable th = this.k;
        if (th == null) {
            try {
                j b2 = b();
                this.f2124j = b2;
                return b2;
            } catch (IOException | Error | RuntimeException e2) {
                w.a(e2);
                this.k = e2;
                throw e2;
            }
        } else if (th instanceof IOException) {
            throw ((IOException) th);
        } else if (th instanceof RuntimeException) {
            throw ((RuntimeException) th);
        } else {
            throw ((Error) th);
        }
    }

    public synchronized f0 a() {
        try {
        } catch (IOException e2) {
            throw new RuntimeException("Unable to create request.", e2);
        }
        return d().a();
    }

    public boolean c() {
        boolean z = true;
        if (this.f2123i) {
            return true;
        }
        synchronized (this) {
            if (this.f2124j == null || !this.f2124j.c()) {
                z = false;
            }
        }
        return z;
    }

    public void cancel() {
        j jVar;
        this.f2123i = true;
        synchronized (this) {
            jVar = this.f2124j;
        }
        if (jVar != null) {
            jVar.cancel();
        }
    }

    public void a(f<T> fVar) {
        j jVar;
        Throwable th;
        d.a(fVar, "callback == null");
        synchronized (this) {
            if (!this.l) {
                this.l = true;
                jVar = this.f2124j;
                th = this.k;
                if (jVar == null && th == null) {
                    try {
                        j b2 = b();
                        this.f2124j = b2;
                        jVar = b2;
                    } catch (Throwable th2) {
                        th = th2;
                        w.a(th);
                        this.k = th;
                    }
                }
            } else {
                throw new IllegalStateException("Already executed.");
            }
        }
        if (th != null) {
            fVar.a((d<T>) this, th);
            return;
        }
        if (this.f2123i) {
            jVar.cancel();
        }
        jVar.a(new a(fVar));
    }

    public l<T> clone() {
        return new l<>(this.e, this.f2120f, this.f2121g, this.f2122h);
    }

    /* compiled from: OkHttpCall */
    class a implements okhttp3.k {
        final /* synthetic */ f a;

        a(f fVar) {
            this.a = fVar;
        }

        public void a(j jVar, h0 h0Var) {
            try {
                try {
                    this.a.a(l.this, l.this.a(h0Var));
                } catch (Throwable th) {
                    w.a(th);
                    th.printStackTrace();
                }
            } catch (Throwable th2) {
                w.a(th2);
                a(th2);
            }
        }

        public void a(j jVar, IOException iOException) {
            a(iOException);
        }

        private void a(Throwable th) {
            try {
                this.a.a(l.this, th);
            } catch (Throwable th2) {
                w.a(th2);
                th2.printStackTrace();
            }
        }
    }

    /* access modifiers changed from: package-private */
    public r<T> a(h0 h0Var) {
        i0 a2 = h0Var.a();
        h0.a q = h0Var.q();
        q.a((i0) new c(a2.j(), a2.c()));
        h0 a3 = q.a();
        int j2 = a3.j();
        if (j2 < 200 || j2 >= 300) {
            try {
                return r.a(w.a(a2), a3);
            } finally {
                a2.close();
            }
        } else if (j2 == 204 || j2 == 205) {
            a2.close();
            return r.a(null, a3);
        } else {
            b bVar = new b(a2);
            try {
                return r.a(this.f2122h.a(bVar), a3);
            } catch (RuntimeException e2) {
                bVar.n();
                throw e2;
            }
        }
    }
}
