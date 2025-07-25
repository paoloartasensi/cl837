package okhttp3;

import java.net.Proxy;
import java.net.ProxySelector;
import java.security.GeneralSecurityException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.net.SocketFactory;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import okhttp3.h0;
import okhttp3.internal.connection.g;
import okhttp3.j;
import okhttp3.k0.e;
import okhttp3.k0.g.d;
import okhttp3.k0.j.f;
import okhttp3.k0.l.c;
import okhttp3.v;
import okhttp3.y;

/* compiled from: OkHttpClient */
public class d0 implements Cloneable, j.a {
    static final List<Protocol> G = e.a((T[]) new Protocol[]{Protocol.HTTP_2, Protocol.HTTP_1_1});
    static final List<p> H = e.a((T[]) new p[]{p.f2069g, p.f2070h});
    final boolean A;
    final int B;
    final int C;
    final int D;
    final int E;
    final int F;
    final s e;

    /* renamed from: f  reason: collision with root package name */
    final Proxy f1857f;

    /* renamed from: g  reason: collision with root package name */
    final List<Protocol> f1858g;

    /* renamed from: h  reason: collision with root package name */
    final List<p> f1859h;

    /* renamed from: i  reason: collision with root package name */
    final List<a0> f1860i;

    /* renamed from: j  reason: collision with root package name */
    final List<a0> f1861j;
    final v.b k;
    final ProxySelector l;
    final r m;
    final h n;
    final d o;
    final SocketFactory p;
    final SSLSocketFactory q;
    final c r;
    final HostnameVerifier s;
    final l t;
    final g u;
    final g v;
    final o w;
    final u x;
    final boolean y;
    final boolean z;

    /* compiled from: OkHttpClient */
    class a extends okhttp3.k0.c {
        a() {
        }

        public void a(y.a aVar, String str) {
            aVar.a(str);
        }

        public void a(y.a aVar, String str, String str2) {
            aVar.b(str, str2);
        }

        public g a(o oVar) {
            return oVar.a;
        }

        public boolean a(e eVar, e eVar2) {
            return eVar.a(eVar2);
        }

        public int a(h0.a aVar) {
            return aVar.c;
        }

        public void a(p pVar, SSLSocket sSLSocket, boolean z) {
            pVar.a(sSLSocket, z);
        }

        public void a(h0.a aVar, okhttp3.internal.connection.d dVar) {
            aVar.a(dVar);
        }

        public okhttp3.internal.connection.d a(h0 h0Var) {
            return h0Var.q;
        }
    }

    static {
        okhttp3.k0.c.a = new a();
    }

    public d0() {
        this(new b());
    }

    private static SSLSocketFactory a(X509TrustManager x509TrustManager) {
        try {
            SSLContext a2 = f.c().a();
            a2.init((KeyManager[]) null, new TrustManager[]{x509TrustManager}, (SecureRandom) null);
            return a2.getSocketFactory();
        } catch (GeneralSecurityException e2) {
            AssertionError assertionError = new AssertionError("No System TLS");
            assertionError.initCause(e2);
            throw assertionError;
        }
    }

    public SSLSocketFactory A() {
        return this.q;
    }

    public int B() {
        return this.E;
    }

    public g b() {
        return this.v;
    }

    public int d() {
        return this.B;
    }

    public l e() {
        return this.t;
    }

    public int f() {
        return this.C;
    }

    public o g() {
        return this.w;
    }

    public List<p> h() {
        return this.f1859h;
    }

    public r i() {
        return this.m;
    }

    public s j() {
        return this.e;
    }

    public u k() {
        return this.x;
    }

    public v.b l() {
        return this.k;
    }

    public boolean m() {
        return this.z;
    }

    public boolean n() {
        return this.y;
    }

    public HostnameVerifier o() {
        return this.s;
    }

    public List<a0> p() {
        return this.f1860i;
    }

    /* access modifiers changed from: package-private */
    public d q() {
        h hVar = this.n;
        return hVar != null ? hVar.e : this.o;
    }

    public List<a0> r() {
        return this.f1861j;
    }

    public int s() {
        return this.F;
    }

    public List<Protocol> t() {
        return this.f1858g;
    }

    public Proxy u() {
        return this.f1857f;
    }

    public g v() {
        return this.u;
    }

    public ProxySelector w() {
        return this.l;
    }

    public int x() {
        return this.D;
    }

    public boolean y() {
        return this.A;
    }

    public SocketFactory z() {
        return this.p;
    }

    /* compiled from: OkHttpClient */
    public static final class b {
        int A;
        int B;
        s a = new s();
        Proxy b;
        List<Protocol> c = d0.G;
        List<p> d = d0.H;
        final List<a0> e = new ArrayList();

        /* renamed from: f  reason: collision with root package name */
        final List<a0> f1862f = new ArrayList();

        /* renamed from: g  reason: collision with root package name */
        v.b f1863g = v.a(v.a);

        /* renamed from: h  reason: collision with root package name */
        ProxySelector f1864h;

        /* renamed from: i  reason: collision with root package name */
        r f1865i;

        /* renamed from: j  reason: collision with root package name */
        h f1866j;
        d k;
        SocketFactory l;
        SSLSocketFactory m;
        c n;
        HostnameVerifier o;
        l p;
        g q;
        g r;
        o s;
        u t;
        boolean u;
        boolean v;
        boolean w;
        int x;
        int y;
        int z;

        public b() {
            ProxySelector proxySelector = ProxySelector.getDefault();
            this.f1864h = proxySelector;
            if (proxySelector == null) {
                this.f1864h = new okhttp3.k0.k.a();
            }
            this.f1865i = r.a;
            this.l = SocketFactory.getDefault();
            this.o = okhttp3.k0.l.d.a;
            this.p = l.c;
            g gVar = g.a;
            this.q = gVar;
            this.r = gVar;
            this.s = new o();
            this.t = u.a;
            this.u = true;
            this.v = true;
            this.w = true;
            this.x = 0;
            this.y = 10000;
            this.z = 10000;
            this.A = 10000;
            this.B = 0;
        }

        public b a(a0 a0Var) {
            if (a0Var != null) {
                this.e.add(a0Var);
                return this;
            }
            throw new IllegalArgumentException("interceptor == null");
        }

        public d0 a() {
            return new d0(this);
        }
    }

    d0(b bVar) {
        boolean z2;
        this.e = bVar.a;
        this.f1857f = bVar.b;
        this.f1858g = bVar.c;
        this.f1859h = bVar.d;
        this.f1860i = e.a(bVar.e);
        this.f1861j = e.a(bVar.f1862f);
        this.k = bVar.f1863g;
        this.l = bVar.f1864h;
        this.m = bVar.f1865i;
        this.n = bVar.f1866j;
        this.o = bVar.k;
        this.p = bVar.l;
        Iterator<p> it = this.f1859h.iterator();
        loop0:
        while (true) {
            z2 = false;
            while (true) {
                if (!it.hasNext()) {
                    break loop0;
                }
                p next = it.next();
                if (z2 || next.b()) {
                    z2 = true;
                }
            }
        }
        if (bVar.m != null || !z2) {
            this.q = bVar.m;
            this.r = bVar.n;
        } else {
            X509TrustManager a2 = e.a();
            this.q = a(a2);
            this.r = c.a(a2);
        }
        if (this.q != null) {
            f.c().a(this.q);
        }
        this.s = bVar.o;
        this.t = bVar.p.a(this.r);
        this.u = bVar.q;
        this.v = bVar.r;
        this.w = bVar.s;
        this.x = bVar.t;
        this.y = bVar.u;
        this.z = bVar.v;
        this.A = bVar.w;
        this.B = bVar.x;
        this.C = bVar.y;
        this.D = bVar.z;
        this.E = bVar.A;
        this.F = bVar.B;
        if (this.f1860i.contains((Object) null)) {
            throw new IllegalStateException("Null interceptor: " + this.f1860i);
        } else if (this.f1861j.contains((Object) null)) {
            throw new IllegalStateException("Null network interceptor: " + this.f1861j);
        }
    }

    public j a(f0 f0Var) {
        return e0.a(this, f0Var, false);
    }
}
