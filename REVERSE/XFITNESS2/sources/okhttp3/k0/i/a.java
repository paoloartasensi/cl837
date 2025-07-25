package okhttp3.k0.i;

import android.support.v4.media.session.PlaybackStateCompat;
import java.io.EOFException;
import java.io.IOException;
import java.net.ProtocolException;
import java.util.concurrent.TimeUnit;
import okhttp3.d0;
import okhttp3.f0;
import okhttp3.h0;
import okhttp3.k0.h.i;
import okhttp3.k0.h.k;
import okhttp3.y;
import okhttp3.z;
import okio.h;
import okio.q;
import okio.r;
import okio.s;

/* compiled from: Http1ExchangeCodec */
public final class a implements okhttp3.k0.h.c {
    /* access modifiers changed from: private */
    public final d0 a;
    /* access modifiers changed from: private */
    public final okhttp3.internal.connection.f b;
    /* access modifiers changed from: private */
    public final okio.e c;
    /* access modifiers changed from: private */
    public final okio.d d;
    /* access modifiers changed from: private */
    public int e = 0;

    /* renamed from: f  reason: collision with root package name */
    private long f2044f = PlaybackStateCompat.ACTION_SET_REPEAT_MODE;
    /* access modifiers changed from: private */

    /* renamed from: g  reason: collision with root package name */
    public y f2045g;

    /* compiled from: Http1ExchangeCodec */
    private abstract class b implements r {
        protected final h e;

        /* renamed from: f  reason: collision with root package name */
        protected boolean f2046f;

        private b() {
            this.e = new h(a.this.c.d());
        }

        /* access modifiers changed from: package-private */
        public final void a() {
            if (a.this.e != 6) {
                if (a.this.e == 5) {
                    a.this.a(this.e);
                    int unused = a.this.e = 6;
                    return;
                }
                throw new IllegalStateException("state: " + a.this.e);
            }
        }

        public long b(okio.c cVar, long j2) {
            try {
                return a.this.c.b(cVar, j2);
            } catch (IOException e2) {
                a.this.b.d();
                a();
                throw e2;
            }
        }

        public s d() {
            return this.e;
        }
    }

    /* compiled from: Http1ExchangeCodec */
    private final class c implements q {
        private final h e = new h(a.this.d.d());

        /* renamed from: f  reason: collision with root package name */
        private boolean f2048f;

        c() {
        }

        public void a(okio.c cVar, long j2) {
            if (this.f2048f) {
                throw new IllegalStateException("closed");
            } else if (j2 != 0) {
                a.this.d.a(j2);
                a.this.d.a("\r\n");
                a.this.d.a(cVar, j2);
                a.this.d.a("\r\n");
            }
        }

        public synchronized void close() {
            if (!this.f2048f) {
                this.f2048f = true;
                a.this.d.a("0\r\n\r\n");
                a.this.a(this.e);
                int unused = a.this.e = 3;
            }
        }

        public s d() {
            return this.e;
        }

        public synchronized void flush() {
            if (!this.f2048f) {
                a.this.d.flush();
            }
        }
    }

    /* compiled from: Http1ExchangeCodec */
    private class d extends b {

        /* renamed from: h  reason: collision with root package name */
        private final z f2050h;

        /* renamed from: i  reason: collision with root package name */
        private long f2051i = -1;

        /* renamed from: j  reason: collision with root package name */
        private boolean f2052j = true;

        d(z zVar) {
            super();
            this.f2050h = zVar;
        }

        private void c() {
            if (this.f2051i != -1) {
                a.this.c.g();
            }
            try {
                this.f2051i = a.this.c.k();
                String trim = a.this.c.g().trim();
                if (this.f2051i < 0 || (!trim.isEmpty() && !trim.startsWith(";"))) {
                    throw new ProtocolException("expected chunk size and optional extensions but was \"" + this.f2051i + trim + "\"");
                } else if (this.f2051i == 0) {
                    this.f2052j = false;
                    a aVar = a.this;
                    y unused = aVar.f2045g = aVar.h();
                    okhttp3.k0.h.e.a(a.this.a.i(), this.f2050h, a.this.f2045g);
                    a();
                }
            } catch (NumberFormatException e) {
                throw new ProtocolException(e.getMessage());
            }
        }

        public long b(okio.c cVar, long j2) {
            if (j2 < 0) {
                throw new IllegalArgumentException("byteCount < 0: " + j2);
            } else if (this.f2046f) {
                throw new IllegalStateException("closed");
            } else if (!this.f2052j) {
                return -1;
            } else {
                long j3 = this.f2051i;
                if (j3 == 0 || j3 == -1) {
                    c();
                    if (!this.f2052j) {
                        return -1;
                    }
                }
                long b = super.b(cVar, Math.min(j2, this.f2051i));
                if (b != -1) {
                    this.f2051i -= b;
                    return b;
                }
                a.this.b.d();
                ProtocolException protocolException = new ProtocolException("unexpected end of stream");
                a();
                throw protocolException;
            }
        }

        public void close() {
            if (!this.f2046f) {
                if (this.f2052j && !okhttp3.k0.e.a((r) this, 100, TimeUnit.MILLISECONDS)) {
                    a.this.b.d();
                    a();
                }
                this.f2046f = true;
            }
        }
    }

    /* compiled from: Http1ExchangeCodec */
    private class e extends b {

        /* renamed from: h  reason: collision with root package name */
        private long f2053h;

        e(long j2) {
            super();
            this.f2053h = j2;
            if (j2 == 0) {
                a();
            }
        }

        public long b(okio.c cVar, long j2) {
            if (j2 < 0) {
                throw new IllegalArgumentException("byteCount < 0: " + j2);
            } else if (!this.f2046f) {
                long j3 = this.f2053h;
                if (j3 == 0) {
                    return -1;
                }
                long b = super.b(cVar, Math.min(j3, j2));
                if (b != -1) {
                    long j4 = this.f2053h - b;
                    this.f2053h = j4;
                    if (j4 == 0) {
                        a();
                    }
                    return b;
                }
                a.this.b.d();
                ProtocolException protocolException = new ProtocolException("unexpected end of stream");
                a();
                throw protocolException;
            } else {
                throw new IllegalStateException("closed");
            }
        }

        public void close() {
            if (!this.f2046f) {
                if (this.f2053h != 0 && !okhttp3.k0.e.a((r) this, 100, TimeUnit.MILLISECONDS)) {
                    a.this.b.d();
                    a();
                }
                this.f2046f = true;
            }
        }
    }

    /* compiled from: Http1ExchangeCodec */
    private final class f implements q {
        private final h e;

        /* renamed from: f  reason: collision with root package name */
        private boolean f2055f;

        private f() {
            this.e = new h(a.this.d.d());
        }

        public void a(okio.c cVar, long j2) {
            if (!this.f2055f) {
                okhttp3.k0.e.a(cVar.r(), 0, j2);
                a.this.d.a(cVar, j2);
                return;
            }
            throw new IllegalStateException("closed");
        }

        public void close() {
            if (!this.f2055f) {
                this.f2055f = true;
                a.this.a(this.e);
                int unused = a.this.e = 3;
            }
        }

        public s d() {
            return this.e;
        }

        public void flush() {
            if (!this.f2055f) {
                a.this.d.flush();
            }
        }
    }

    /* compiled from: Http1ExchangeCodec */
    private class g extends b {

        /* renamed from: h  reason: collision with root package name */
        private boolean f2057h;

        private g(a aVar) {
            super();
        }

        public long b(okio.c cVar, long j2) {
            if (j2 < 0) {
                throw new IllegalArgumentException("byteCount < 0: " + j2);
            } else if (this.f2046f) {
                throw new IllegalStateException("closed");
            } else if (this.f2057h) {
                return -1;
            } else {
                long b = super.b(cVar, j2);
                if (b != -1) {
                    return b;
                }
                this.f2057h = true;
                a();
                return -1;
            }
        }

        public void close() {
            if (!this.f2046f) {
                if (!this.f2057h) {
                    a();
                }
                this.f2046f = true;
            }
        }
    }

    public a(d0 d0Var, okhttp3.internal.connection.f fVar, okio.e eVar, okio.d dVar) {
        this.a = d0Var;
        this.b = fVar;
        this.c = eVar;
        this.d = dVar;
    }

    /* access modifiers changed from: private */
    public y h() {
        y.a aVar = new y.a();
        while (true) {
            String g2 = g();
            if (g2.length() == 0) {
                return aVar.a();
            }
            okhttp3.k0.c.a.a(aVar, g2);
        }
    }

    public void cancel() {
        okhttp3.internal.connection.f fVar = this.b;
        if (fVar != null) {
            fVar.a();
        }
    }

    private q d() {
        if (this.e == 1) {
            this.e = 2;
            return new c();
        }
        throw new IllegalStateException("state: " + this.e);
    }

    private q e() {
        if (this.e == 1) {
            this.e = 2;
            return new f();
        }
        throw new IllegalStateException("state: " + this.e);
    }

    private r f() {
        if (this.e == 4) {
            this.e = 5;
            this.b.d();
            return new g();
        }
        throw new IllegalStateException("state: " + this.e);
    }

    private String g() {
        String c2 = this.c.c(this.f2044f);
        this.f2044f -= (long) c2.length();
        return c2;
    }

    public r b(h0 h0Var) {
        if (!okhttp3.k0.h.e.b(h0Var)) {
            return a(0);
        }
        if ("chunked".equalsIgnoreCase(h0Var.b("Transfer-Encoding"))) {
            return a(h0Var.t().g());
        }
        long a2 = okhttp3.k0.h.e.a(h0Var);
        if (a2 != -1) {
            return a(a2);
        }
        return f();
    }

    public okhttp3.internal.connection.f c() {
        return this.b;
    }

    public void c(h0 h0Var) {
        long a2 = okhttp3.k0.h.e.a(h0Var);
        if (a2 != -1) {
            r a3 = a(a2);
            okhttp3.k0.e.b(a3, Integer.MAX_VALUE, TimeUnit.MILLISECONDS);
            a3.close();
        }
    }

    public q a(f0 f0Var, long j2) {
        if (f0Var.a() != null && f0Var.a().c()) {
            throw new ProtocolException("Duplex connections are not supported for HTTP/1");
        } else if ("chunked".equalsIgnoreCase(f0Var.a("Transfer-Encoding"))) {
            return d();
        } else {
            if (j2 != -1) {
                return e();
            }
            throw new IllegalStateException("Cannot stream a request body without chunked encoding or a known content length!");
        }
    }

    public void b() {
        this.d.flush();
    }

    public void a(f0 f0Var) {
        a(f0Var.c(), i.a(f0Var, this.b.e().b().type()));
    }

    public long a(h0 h0Var) {
        if (!okhttp3.k0.h.e.b(h0Var)) {
            return 0;
        }
        if ("chunked".equalsIgnoreCase(h0Var.b("Transfer-Encoding"))) {
            return -1;
        }
        return okhttp3.k0.h.e.a(h0Var);
    }

    public void a() {
        this.d.flush();
    }

    public void a(y yVar, String str) {
        if (this.e == 0) {
            this.d.a(str).a("\r\n");
            int b2 = yVar.b();
            for (int i2 = 0; i2 < b2; i2++) {
                this.d.a(yVar.a(i2)).a(": ").a(yVar.b(i2)).a("\r\n");
            }
            this.d.a("\r\n");
            this.e = 1;
            return;
        }
        throw new IllegalStateException("state: " + this.e);
    }

    public h0.a a(boolean z) {
        int i2 = this.e;
        if (i2 == 1 || i2 == 3) {
            try {
                k a2 = k.a(g());
                h0.a aVar = new h0.a();
                aVar.a(a2.a);
                aVar.a(a2.b);
                aVar.a(a2.c);
                aVar.a(h());
                if (z && a2.b == 100) {
                    return null;
                }
                if (a2.b == 100) {
                    this.e = 3;
                    return aVar;
                }
                this.e = 4;
                return aVar;
            } catch (EOFException e2) {
                okhttp3.internal.connection.f fVar = this.b;
                String m = fVar != null ? fVar.e().a().k().m() : "unknown";
                throw new IOException("unexpected end of stream on " + m, e2);
            }
        } else {
            throw new IllegalStateException("state: " + this.e);
        }
    }

    private r a(long j2) {
        if (this.e == 4) {
            this.e = 5;
            return new e(j2);
        }
        throw new IllegalStateException("state: " + this.e);
    }

    private r a(z zVar) {
        if (this.e == 4) {
            this.e = 5;
            return new d(zVar);
        }
        throw new IllegalStateException("state: " + this.e);
    }

    /* access modifiers changed from: private */
    public void a(h hVar) {
        s g2 = hVar.g();
        hVar.a(s.d);
        g2.a();
        g2.b();
    }
}
