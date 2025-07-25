package okhttp3;

import java.io.Closeable;
import okhttp3.internal.connection.d;
import okhttp3.y;

/* compiled from: Response */
public final class h0 implements Closeable {
    final f0 e;

    /* renamed from: f  reason: collision with root package name */
    final Protocol f1880f;

    /* renamed from: g  reason: collision with root package name */
    final int f1881g;

    /* renamed from: h  reason: collision with root package name */
    final String f1882h;

    /* renamed from: i  reason: collision with root package name */
    final x f1883i;

    /* renamed from: j  reason: collision with root package name */
    final y f1884j;
    final i0 k;
    final h0 l;
    final h0 m;
    final h0 n;
    final long o;
    final long p;
    final d q;
    private volatile i r;

    /* compiled from: Response */
    public static class a {
        f0 a;
        Protocol b;
        int c;
        String d;
        x e;

        /* renamed from: f  reason: collision with root package name */
        y.a f1885f;

        /* renamed from: g  reason: collision with root package name */
        i0 f1886g;

        /* renamed from: h  reason: collision with root package name */
        h0 f1887h;

        /* renamed from: i  reason: collision with root package name */
        h0 f1888i;

        /* renamed from: j  reason: collision with root package name */
        h0 f1889j;
        long k;
        long l;
        d m;

        public a() {
            this.c = -1;
            this.f1885f = new y.a();
        }

        private void d(h0 h0Var) {
            if (h0Var.k != null) {
                throw new IllegalArgumentException("priorResponse.body != null");
            }
        }

        public a a(f0 f0Var) {
            this.a = f0Var;
            return this;
        }

        public a b(String str, String str2) {
            this.f1885f.c(str, str2);
            return this;
        }

        public a c(h0 h0Var) {
            if (h0Var != null) {
                d(h0Var);
            }
            this.f1889j = h0Var;
            return this;
        }

        public a a(Protocol protocol) {
            this.b = protocol;
            return this;
        }

        public a b(h0 h0Var) {
            if (h0Var != null) {
                a("networkResponse", h0Var);
            }
            this.f1887h = h0Var;
            return this;
        }

        public a a(int i2) {
            this.c = i2;
            return this;
        }

        a(h0 h0Var) {
            this.c = -1;
            this.a = h0Var.e;
            this.b = h0Var.f1880f;
            this.c = h0Var.f1881g;
            this.d = h0Var.f1882h;
            this.e = h0Var.f1883i;
            this.f1885f = h0Var.f1884j.a();
            this.f1886g = h0Var.k;
            this.f1887h = h0Var.l;
            this.f1888i = h0Var.m;
            this.f1889j = h0Var.n;
            this.k = h0Var.o;
            this.l = h0Var.p;
            this.m = h0Var.q;
        }

        public a a(String str) {
            this.d = str;
            return this;
        }

        public a b(long j2) {
            this.k = j2;
            return this;
        }

        public a a(x xVar) {
            this.e = xVar;
            return this;
        }

        public a a(String str, String str2) {
            this.f1885f.a(str, str2);
            return this;
        }

        public a a(y yVar) {
            this.f1885f = yVar.a();
            return this;
        }

        public a a(i0 i0Var) {
            this.f1886g = i0Var;
            return this;
        }

        public a a(h0 h0Var) {
            if (h0Var != null) {
                a("cacheResponse", h0Var);
            }
            this.f1888i = h0Var;
            return this;
        }

        private void a(String str, h0 h0Var) {
            if (h0Var.k != null) {
                throw new IllegalArgumentException(str + ".body != null");
            } else if (h0Var.l != null) {
                throw new IllegalArgumentException(str + ".networkResponse != null");
            } else if (h0Var.m != null) {
                throw new IllegalArgumentException(str + ".cacheResponse != null");
            } else if (h0Var.n != null) {
                throw new IllegalArgumentException(str + ".priorResponse != null");
            }
        }

        public a a(long j2) {
            this.l = j2;
            return this;
        }

        /* access modifiers changed from: package-private */
        public void a(d dVar) {
            this.m = dVar;
        }

        public h0 a() {
            if (this.a == null) {
                throw new IllegalStateException("request == null");
            } else if (this.b == null) {
                throw new IllegalStateException("protocol == null");
            } else if (this.c < 0) {
                throw new IllegalStateException("code < 0: " + this.c);
            } else if (this.d != null) {
                return new h0(this);
            } else {
                throw new IllegalStateException("message == null");
            }
        }
    }

    h0(a aVar) {
        this.e = aVar.a;
        this.f1880f = aVar.b;
        this.f1881g = aVar.c;
        this.f1882h = aVar.d;
        this.f1883i = aVar.e;
        this.f1884j = aVar.f1885f.a();
        this.k = aVar.f1886g;
        this.l = aVar.f1887h;
        this.m = aVar.f1888i;
        this.n = aVar.f1889j;
        this.o = aVar.k;
        this.p = aVar.l;
        this.q = aVar.m;
    }

    public String a(String str, String str2) {
        String a2 = this.f1884j.a(str);
        return a2 != null ? a2 : str2;
    }

    public String b(String str) {
        return a(str, (String) null);
    }

    public i c() {
        i iVar = this.r;
        if (iVar != null) {
            return iVar;
        }
        i a2 = i.a(this.f1884j);
        this.r = a2;
        return a2;
    }

    public void close() {
        i0 i0Var = this.k;
        if (i0Var != null) {
            i0Var.close();
            return;
        }
        throw new IllegalStateException("response is not eligible for a body and must not be closed");
    }

    public int j() {
        return this.f1881g;
    }

    public x m() {
        return this.f1883i;
    }

    public y n() {
        return this.f1884j;
    }

    public boolean o() {
        int i2 = this.f1881g;
        return i2 >= 200 && i2 < 300;
    }

    public String p() {
        return this.f1882h;
    }

    public a q() {
        return new a(this);
    }

    public h0 r() {
        return this.n;
    }

    public long s() {
        return this.p;
    }

    public f0 t() {
        return this.e;
    }

    public String toString() {
        return "Response{protocol=" + this.f1880f + ", code=" + this.f1881g + ", message=" + this.f1882h + ", url=" + this.e.g() + '}';
    }

    public long u() {
        return this.o;
    }

    public i0 a() {
        return this.k;
    }
}
