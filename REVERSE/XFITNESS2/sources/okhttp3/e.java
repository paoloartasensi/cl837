package okhttp3;

import java.net.Proxy;
import java.net.ProxySelector;
import java.util.List;
import javax.net.SocketFactory;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSocketFactory;
import okhttp3.z;

/* compiled from: Address */
public final class e {
    final z a;
    final u b;
    final SocketFactory c;
    final g d;
    final List<Protocol> e;

    /* renamed from: f  reason: collision with root package name */
    final List<p> f1867f;

    /* renamed from: g  reason: collision with root package name */
    final ProxySelector f1868g;

    /* renamed from: h  reason: collision with root package name */
    final Proxy f1869h;

    /* renamed from: i  reason: collision with root package name */
    final SSLSocketFactory f1870i;

    /* renamed from: j  reason: collision with root package name */
    final HostnameVerifier f1871j;
    final l k;

    public e(String str, int i2, u uVar, SocketFactory socketFactory, SSLSocketFactory sSLSocketFactory, HostnameVerifier hostnameVerifier, l lVar, g gVar, Proxy proxy, List<Protocol> list, List<p> list2, ProxySelector proxySelector) {
        z.a aVar = new z.a();
        aVar.d(sSLSocketFactory != null ? "https" : "http");
        aVar.b(str);
        aVar.a(i2);
        this.a = aVar.a();
        if (uVar != null) {
            this.b = uVar;
            if (socketFactory != null) {
                this.c = socketFactory;
                if (gVar != null) {
                    this.d = gVar;
                    if (list != null) {
                        this.e = okhttp3.k0.e.a(list);
                        if (list2 != null) {
                            this.f1867f = okhttp3.k0.e.a(list2);
                            if (proxySelector != null) {
                                this.f1868g = proxySelector;
                                this.f1869h = proxy;
                                this.f1870i = sSLSocketFactory;
                                this.f1871j = hostnameVerifier;
                                this.k = lVar;
                                return;
                            }
                            throw new NullPointerException("proxySelector == null");
                        }
                        throw new NullPointerException("connectionSpecs == null");
                    }
                    throw new NullPointerException("protocols == null");
                }
                throw new NullPointerException("proxyAuthenticator == null");
            }
            throw new NullPointerException("socketFactory == null");
        }
        throw new NullPointerException("dns == null");
    }

    public l a() {
        return this.k;
    }

    public List<p> b() {
        return this.f1867f;
    }

    public u c() {
        return this.b;
    }

    public HostnameVerifier d() {
        return this.f1871j;
    }

    public List<Protocol> e() {
        return this.e;
    }

    public boolean equals(Object obj) {
        if (obj instanceof e) {
            e eVar = (e) obj;
            return this.a.equals(eVar.a) && a(eVar);
        }
    }

    public Proxy f() {
        return this.f1869h;
    }

    public g g() {
        return this.d;
    }

    public ProxySelector h() {
        return this.f1868g;
    }

    public int hashCode() {
        return ((((((((((((((((((527 + this.a.hashCode()) * 31) + this.b.hashCode()) * 31) + this.d.hashCode()) * 31) + this.e.hashCode()) * 31) + this.f1867f.hashCode()) * 31) + this.f1868g.hashCode()) * 31) + b.a(this.f1869h)) * 31) + b.a(this.f1870i)) * 31) + b.a(this.f1871j)) * 31) + b.a(this.k);
    }

    public SocketFactory i() {
        return this.c;
    }

    public SSLSocketFactory j() {
        return this.f1870i;
    }

    public z k() {
        return this.a;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Address{");
        sb.append(this.a.g());
        sb.append(":");
        sb.append(this.a.k());
        if (this.f1869h != null) {
            sb.append(", proxy=");
            sb.append(this.f1869h);
        } else {
            sb.append(", proxySelector=");
            sb.append(this.f1868g);
        }
        sb.append("}");
        return sb.toString();
    }

    /* access modifiers changed from: package-private */
    public boolean a(e eVar) {
        return this.b.equals(eVar.b) && this.d.equals(eVar.d) && this.e.equals(eVar.e) && this.f1867f.equals(eVar.f1867f) && this.f1868g.equals(eVar.f1868g) && c.a(this.f1869h, eVar.f1869h) && c.a(this.f1870i, eVar.f1870i) && c.a(this.f1871j, eVar.f1871j) && c.a(this.k, eVar.k) && k().k() == eVar.k().k();
    }
}
