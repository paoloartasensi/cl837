package okhttp3;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import javax.net.ssl.SSLSocket;
import okhttp3.k0.e;

/* compiled from: ConnectionSpec */
public final class p {
    private static final m[] e = {m.q, m.r, m.s, m.k, m.m, m.l, m.n, m.p, m.o};

    /* renamed from: f  reason: collision with root package name */
    private static final m[] f2068f = {m.q, m.r, m.s, m.k, m.m, m.l, m.n, m.p, m.o, m.f2066i, m.f2067j, m.f2064g, m.f2065h, m.e, m.f2063f, m.d};

    /* renamed from: g  reason: collision with root package name */
    public static final p f2069g;

    /* renamed from: h  reason: collision with root package name */
    public static final p f2070h = new a(false).a();
    final boolean a;
    final boolean b;
    final String[] c;
    final String[] d;

    static {
        a aVar = new a(true);
        aVar.a(e);
        aVar.a(TlsVersion.TLS_1_3, TlsVersion.TLS_1_2);
        aVar.a(true);
        aVar.a();
        a aVar2 = new a(true);
        aVar2.a(f2068f);
        aVar2.a(TlsVersion.TLS_1_3, TlsVersion.TLS_1_2);
        aVar2.a(true);
        f2069g = aVar2.a();
        a aVar3 = new a(true);
        aVar3.a(f2068f);
        aVar3.a(TlsVersion.TLS_1_3, TlsVersion.TLS_1_2, TlsVersion.TLS_1_1, TlsVersion.TLS_1_0);
        aVar3.a(true);
        aVar3.a();
    }

    p(a aVar) {
        this.a = aVar.a;
        this.c = aVar.b;
        this.d = aVar.c;
        this.b = aVar.d;
    }

    public List<m> a() {
        String[] strArr = this.c;
        if (strArr != null) {
            return m.a(strArr);
        }
        return null;
    }

    public boolean b() {
        return this.a;
    }

    public boolean c() {
        return this.b;
    }

    public List<TlsVersion> d() {
        String[] strArr = this.d;
        if (strArr != null) {
            return TlsVersion.forJavaNames(strArr);
        }
        return null;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof p)) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        p pVar = (p) obj;
        boolean z = this.a;
        if (z != pVar.a) {
            return false;
        }
        return !z || (Arrays.equals(this.c, pVar.c) && Arrays.equals(this.d, pVar.d) && this.b == pVar.b);
    }

    public int hashCode() {
        if (this.a) {
            return ((((527 + Arrays.hashCode(this.c)) * 31) + Arrays.hashCode(this.d)) * 31) + (this.b ^ true ? 1 : 0);
        }
        return 17;
    }

    public String toString() {
        if (!this.a) {
            return "ConnectionSpec()";
        }
        return "ConnectionSpec(cipherSuites=" + e.a(a(), "[all enabled]") + ", tlsVersions=" + e.a(d(), "[all enabled]") + ", supportsTlsExtensions=" + this.b + ")";
    }

    /* compiled from: ConnectionSpec */
    public static final class a {
        boolean a;
        String[] b;
        String[] c;
        boolean d;

        a(boolean z) {
            this.a = z;
        }

        public a a(m... mVarArr) {
            if (this.a) {
                String[] strArr = new String[mVarArr.length];
                for (int i2 = 0; i2 < mVarArr.length; i2++) {
                    strArr[i2] = mVarArr[i2].a;
                }
                a(strArr);
                return this;
            }
            throw new IllegalStateException("no cipher suites for cleartext connections");
        }

        public a b(String... strArr) {
            if (!this.a) {
                throw new IllegalStateException("no TLS versions for cleartext connections");
            } else if (strArr.length != 0) {
                this.c = (String[]) strArr.clone();
                return this;
            } else {
                throw new IllegalArgumentException("At least one TLS version is required");
            }
        }

        public a(p pVar) {
            this.a = pVar.a;
            this.b = pVar.c;
            this.c = pVar.d;
            this.d = pVar.b;
        }

        public a a(String... strArr) {
            if (!this.a) {
                throw new IllegalStateException("no cipher suites for cleartext connections");
            } else if (strArr.length != 0) {
                this.b = (String[]) strArr.clone();
                return this;
            } else {
                throw new IllegalArgumentException("At least one cipher suite is required");
            }
        }

        public a a(TlsVersion... tlsVersionArr) {
            if (this.a) {
                String[] strArr = new String[tlsVersionArr.length];
                for (int i2 = 0; i2 < tlsVersionArr.length; i2++) {
                    strArr[i2] = tlsVersionArr[i2].javaName;
                }
                b(strArr);
                return this;
            }
            throw new IllegalStateException("no TLS versions for cleartext connections");
        }

        public a a(boolean z) {
            if (this.a) {
                this.d = z;
                return this;
            }
            throw new IllegalStateException("no TLS extensions for cleartext connections");
        }

        public p a() {
            return new p(this);
        }
    }

    private p b(SSLSocket sSLSocket, boolean z) {
        String[] strArr;
        String[] strArr2;
        if (this.c != null) {
            strArr = e.a((Comparator<? super String>) m.b, sSLSocket.getEnabledCipherSuites(), this.c);
        } else {
            strArr = sSLSocket.getEnabledCipherSuites();
        }
        if (this.d != null) {
            strArr2 = e.a((Comparator<? super String>) e.f2026i, sSLSocket.getEnabledProtocols(), this.d);
        } else {
            strArr2 = sSLSocket.getEnabledProtocols();
        }
        String[] supportedCipherSuites = sSLSocket.getSupportedCipherSuites();
        int a2 = e.a(m.b, supportedCipherSuites, "TLS_FALLBACK_SCSV");
        if (z && a2 != -1) {
            strArr = e.a(strArr, supportedCipherSuites[a2]);
        }
        a aVar = new a(this);
        aVar.a(strArr);
        aVar.b(strArr2);
        return aVar.a();
    }

    /* access modifiers changed from: package-private */
    public void a(SSLSocket sSLSocket, boolean z) {
        p b2 = b(sSLSocket, z);
        String[] strArr = b2.d;
        if (strArr != null) {
            sSLSocket.setEnabledProtocols(strArr);
        }
        String[] strArr2 = b2.c;
        if (strArr2 != null) {
            sSLSocket.setEnabledCipherSuites(strArr2);
        }
    }

    public boolean a(SSLSocket sSLSocket) {
        if (!this.a) {
            return false;
        }
        String[] strArr = this.d;
        if (strArr != null && !e.b(e.f2026i, strArr, sSLSocket.getEnabledProtocols())) {
            return false;
        }
        String[] strArr2 = this.c;
        if (strArr2 == null || e.b(m.b, strArr2, sSLSocket.getEnabledCipherSuites())) {
            return true;
        }
        return false;
    }
}
