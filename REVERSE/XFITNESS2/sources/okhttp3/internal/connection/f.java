package okhttp3.internal.connection;

import java.io.IOException;
import java.lang.ref.Reference;
import java.net.ConnectException;
import java.net.Proxy;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.net.ssl.SSLPeerUnverifiedException;
import okhttp3.Protocol;
import okhttp3.a0;
import okhttp3.d0;
import okhttp3.f0;
import okhttp3.g0;
import okhttp3.h0;
import okhttp3.internal.http2.ConnectionShutdownException;
import okhttp3.internal.http2.ErrorCode;
import okhttp3.internal.http2.StreamResetException;
import okhttp3.internal.http2.d;
import okhttp3.internal.http2.g;
import okhttp3.j;
import okhttp3.j0;
import okhttp3.k0.c;
import okhttp3.k0.i.a;
import okhttp3.n;
import okhttp3.v;
import okhttp3.x;
import okhttp3.z;
import okio.e;
import okio.k;

/* compiled from: RealConnection */
public final class f extends d.j implements n {
    public final g b;
    private final j0 c;
    private Socket d;
    private Socket e;

    /* renamed from: f  reason: collision with root package name */
    private x f1920f;

    /* renamed from: g  reason: collision with root package name */
    private Protocol f1921g;

    /* renamed from: h  reason: collision with root package name */
    private d f1922h;

    /* renamed from: i  reason: collision with root package name */
    private e f1923i;

    /* renamed from: j  reason: collision with root package name */
    private okio.d f1924j;
    boolean k;
    int l;
    int m;
    private int n;
    private int o = 1;
    final List<Reference<j>> p = new ArrayList();
    long q = Long.MAX_VALUE;

    public f(g gVar, j0 j0Var) {
        this.b = gVar;
        this.c = j0Var;
    }

    private f0 g() {
        f0.a aVar = new f0.a();
        aVar.a(this.c.a().k());
        aVar.a("CONNECT", (g0) null);
        aVar.a("Host", okhttp3.k0.e.a(this.c.a().k(), true));
        aVar.a("Proxy-Connection", "Keep-Alive");
        aVar.a("User-Agent", okhttp3.k0.f.a());
        f0 a = aVar.a();
        h0.a aVar2 = new h0.a();
        aVar2.a(a);
        aVar2.a(Protocol.HTTP_1_1);
        aVar2.a(407);
        aVar2.a("Preemptive Authenticate");
        aVar2.a(okhttp3.k0.e.d);
        aVar2.b(-1);
        aVar2.a(-1);
        aVar2.b("Proxy-Authenticate", "OkHttp-Preemptive");
        f0 a2 = this.c.a().g().a(this.c, aVar2.a());
        return a2 != null ? a2 : a;
    }

    /* JADX WARNING: Removed duplicated region for block: B:18:0x0090 A[Catch:{ IOException -> 0x00f9 }] */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x00a9  */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x00d3  */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x00e0  */
    /* JADX WARNING: Removed duplicated region for block: B:51:0x012f  */
    /* JADX WARNING: Removed duplicated region for block: B:52:0x0135  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void a(int r17, int r18, int r19, int r20, boolean r21, okhttp3.j r22, okhttp3.v r23) {
        /*
            r16 = this;
            r7 = r16
            r8 = r22
            r9 = r23
            okhttp3.Protocol r0 = r7.f1921g
            if (r0 != 0) goto L_0x0150
            okhttp3.j0 r0 = r7.c
            okhttp3.e r0 = r0.a()
            java.util.List r0 = r0.b()
            okhttp3.internal.connection.c r10 = new okhttp3.internal.connection.c
            r10.<init>(r0)
            okhttp3.j0 r1 = r7.c
            okhttp3.e r1 = r1.a()
            javax.net.ssl.SSLSocketFactory r1 = r1.j()
            if (r1 != 0) goto L_0x0074
            okhttp3.p r1 = okhttp3.p.f2070h
            boolean r0 = r0.contains(r1)
            if (r0 == 0) goto L_0x0067
            okhttp3.j0 r0 = r7.c
            okhttp3.e r0 = r0.a()
            okhttp3.z r0 = r0.k()
            java.lang.String r0 = r0.g()
            okhttp3.k0.j.f r1 = okhttp3.k0.j.f.c()
            boolean r1 = r1.b((java.lang.String) r0)
            if (r1 == 0) goto L_0x0046
            goto L_0x0086
        L_0x0046:
            okhttp3.internal.connection.RouteException r1 = new okhttp3.internal.connection.RouteException
            java.net.UnknownServiceException r2 = new java.net.UnknownServiceException
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = "CLEARTEXT communication to "
            r3.append(r4)
            r3.append(r0)
            java.lang.String r0 = " not permitted by network security policy"
            r3.append(r0)
            java.lang.String r0 = r3.toString()
            r2.<init>(r0)
            r1.<init>(r2)
            throw r1
        L_0x0067:
            okhttp3.internal.connection.RouteException r0 = new okhttp3.internal.connection.RouteException
            java.net.UnknownServiceException r1 = new java.net.UnknownServiceException
            java.lang.String r2 = "CLEARTEXT communication not enabled for client"
            r1.<init>(r2)
            r0.<init>(r1)
            throw r0
        L_0x0074:
            okhttp3.j0 r0 = r7.c
            okhttp3.e r0 = r0.a()
            java.util.List r0 = r0.e()
            okhttp3.Protocol r1 = okhttp3.Protocol.H2_PRIOR_KNOWLEDGE
            boolean r0 = r0.contains(r1)
            if (r0 != 0) goto L_0x0143
        L_0x0086:
            r11 = 0
            r12 = r11
        L_0x0088:
            okhttp3.j0 r0 = r7.c     // Catch:{ IOException -> 0x00f9 }
            boolean r0 = r0.c()     // Catch:{ IOException -> 0x00f9 }
            if (r0 == 0) goto L_0x00a9
            r1 = r16
            r2 = r17
            r3 = r18
            r4 = r19
            r5 = r22
            r6 = r23
            r1.a(r2, r3, r4, r5, r6)     // Catch:{ IOException -> 0x00f9 }
            java.net.Socket r0 = r7.d     // Catch:{ IOException -> 0x00f9 }
            if (r0 != 0) goto L_0x00a4
            goto L_0x00c6
        L_0x00a4:
            r13 = r17
            r14 = r18
            goto L_0x00b0
        L_0x00a9:
            r13 = r17
            r14 = r18
            r7.a((int) r13, (int) r14, (okhttp3.j) r8, (okhttp3.v) r9)     // Catch:{ IOException -> 0x00f7 }
        L_0x00b0:
            r15 = r20
            r7.a((okhttp3.internal.connection.c) r10, (int) r15, (okhttp3.j) r8, (okhttp3.v) r9)     // Catch:{ IOException -> 0x00f5 }
            okhttp3.j0 r0 = r7.c     // Catch:{ IOException -> 0x00f5 }
            java.net.InetSocketAddress r0 = r0.d()     // Catch:{ IOException -> 0x00f5 }
            okhttp3.j0 r1 = r7.c     // Catch:{ IOException -> 0x00f5 }
            java.net.Proxy r1 = r1.b()     // Catch:{ IOException -> 0x00f5 }
            okhttp3.Protocol r2 = r7.f1921g     // Catch:{ IOException -> 0x00f5 }
            r9.a(r8, r0, r1, r2)     // Catch:{ IOException -> 0x00f5 }
        L_0x00c6:
            okhttp3.j0 r0 = r7.c
            boolean r0 = r0.c()
            if (r0 == 0) goto L_0x00e0
            java.net.Socket r0 = r7.d
            if (r0 == 0) goto L_0x00d3
            goto L_0x00e0
        L_0x00d3:
            java.net.ProtocolException r0 = new java.net.ProtocolException
            java.lang.String r1 = "Too many tunnel connections attempted: 21"
            r0.<init>(r1)
            okhttp3.internal.connection.RouteException r1 = new okhttp3.internal.connection.RouteException
            r1.<init>(r0)
            throw r1
        L_0x00e0:
            okhttp3.internal.http2.d r0 = r7.f1922h
            if (r0 == 0) goto L_0x00f4
            okhttp3.internal.connection.g r1 = r7.b
            monitor-enter(r1)
            okhttp3.internal.http2.d r0 = r7.f1922h     // Catch:{ all -> 0x00f1 }
            int r0 = r0.a()     // Catch:{ all -> 0x00f1 }
            r7.o = r0     // Catch:{ all -> 0x00f1 }
            monitor-exit(r1)     // Catch:{ all -> 0x00f1 }
            goto L_0x00f4
        L_0x00f1:
            r0 = move-exception
            monitor-exit(r1)     // Catch:{ all -> 0x00f1 }
            throw r0
        L_0x00f4:
            return
        L_0x00f5:
            r0 = move-exception
            goto L_0x0100
        L_0x00f7:
            r0 = move-exception
            goto L_0x00fe
        L_0x00f9:
            r0 = move-exception
            r13 = r17
            r14 = r18
        L_0x00fe:
            r15 = r20
        L_0x0100:
            java.net.Socket r1 = r7.e
            okhttp3.k0.e.a((java.net.Socket) r1)
            java.net.Socket r1 = r7.d
            okhttp3.k0.e.a((java.net.Socket) r1)
            r7.e = r11
            r7.d = r11
            r7.f1923i = r11
            r7.f1924j = r11
            r7.f1920f = r11
            r7.f1921g = r11
            r7.f1922h = r11
            okhttp3.j0 r1 = r7.c
            java.net.InetSocketAddress r3 = r1.d()
            okhttp3.j0 r1 = r7.c
            java.net.Proxy r4 = r1.b()
            r5 = 0
            r1 = r23
            r2 = r22
            r6 = r0
            r1.a(r2, r3, r4, r5, r6)
            if (r12 != 0) goto L_0x0135
            okhttp3.internal.connection.RouteException r12 = new okhttp3.internal.connection.RouteException
            r12.<init>(r0)
            goto L_0x0138
        L_0x0135:
            r12.addConnectException(r0)
        L_0x0138:
            if (r21 == 0) goto L_0x0142
            boolean r0 = r10.a((java.io.IOException) r0)
            if (r0 == 0) goto L_0x0142
            goto L_0x0088
        L_0x0142:
            throw r12
        L_0x0143:
            okhttp3.internal.connection.RouteException r0 = new okhttp3.internal.connection.RouteException
            java.net.UnknownServiceException r1 = new java.net.UnknownServiceException
            java.lang.String r2 = "H2_PRIOR_KNOWLEDGE cannot be used with HTTPS"
            r1.<init>(r2)
            r0.<init>(r1)
            throw r0
        L_0x0150:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "already connected"
            r0.<init>(r1)
            goto L_0x0159
        L_0x0158:
            throw r0
        L_0x0159:
            goto L_0x0158
        */
        throw new UnsupportedOperationException("Method not decompiled: okhttp3.internal.connection.f.a(int, int, int, int, boolean, okhttp3.j, okhttp3.v):void");
    }

    public x b() {
        return this.f1920f;
    }

    public boolean c() {
        return this.f1922h != null;
    }

    public void d() {
        synchronized (this.b) {
            this.k = true;
        }
    }

    public j0 e() {
        return this.c;
    }

    public Socket f() {
        return this.e;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Connection{");
        sb.append(this.c.a().k().g());
        sb.append(":");
        sb.append(this.c.a().k().k());
        sb.append(", proxy=");
        sb.append(this.c.b());
        sb.append(" hostAddress=");
        sb.append(this.c.d());
        sb.append(" cipherSuite=");
        x xVar = this.f1920f;
        sb.append(xVar != null ? xVar.a() : "none");
        sb.append(" protocol=");
        sb.append(this.f1921g);
        sb.append('}');
        return sb.toString();
    }

    private void a(int i2, int i3, int i4, j jVar, v vVar) {
        f0 g2 = g();
        z g3 = g2.g();
        int i5 = 0;
        while (i5 < 21) {
            a(i2, i3, jVar, vVar);
            g2 = a(i3, i4, g2, g3);
            if (g2 != null) {
                okhttp3.k0.e.a(this.d);
                this.d = null;
                this.f1924j = null;
                this.f1923i = null;
                vVar.a(jVar, this.c.d(), this.c.b(), (Protocol) null);
                i5++;
            } else {
                return;
            }
        }
    }

    private void a(int i2, int i3, j jVar, v vVar) {
        Socket socket;
        Proxy b2 = this.c.b();
        okhttp3.e a = this.c.a();
        if (b2.type() == Proxy.Type.DIRECT || b2.type() == Proxy.Type.HTTP) {
            socket = a.i().createSocket();
        } else {
            socket = new Socket(b2);
        }
        this.d = socket;
        vVar.a(jVar, this.c.d(), b2);
        this.d.setSoTimeout(i3);
        try {
            okhttp3.k0.j.f.c().a(this.d, this.c.d(), i2);
            try {
                this.f1923i = k.a(k.b(this.d));
                this.f1924j = k.a(k.a(this.d));
            } catch (NullPointerException e2) {
                if ("throw with null exception".equals(e2.getMessage())) {
                    throw new IOException(e2);
                }
            }
        } catch (ConnectException e3) {
            ConnectException connectException = new ConnectException("Failed to connect to " + this.c.d());
            connectException.initCause(e3);
            throw connectException;
        }
    }

    private void a(c cVar, int i2, j jVar, v vVar) {
        if (this.c.a().j() != null) {
            vVar.g(jVar);
            a(cVar);
            vVar.a(jVar, this.f1920f);
            if (this.f1921g == Protocol.HTTP_2) {
                a(i2);
            }
        } else if (this.c.a().e().contains(Protocol.H2_PRIOR_KNOWLEDGE)) {
            this.e = this.d;
            this.f1921g = Protocol.H2_PRIOR_KNOWLEDGE;
            a(i2);
        } else {
            this.e = this.d;
            this.f1921g = Protocol.HTTP_1_1;
        }
    }

    private void a(int i2) {
        this.e.setSoTimeout(0);
        d.h hVar = new d.h(true);
        hVar.a(this.e, this.c.a().k().g(), this.f1923i, this.f1924j);
        hVar.a((d.j) this);
        hVar.a(i2);
        d a = hVar.a();
        this.f1922h = a;
        a.j();
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v0, resolved type: java.lang.String} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v1, resolved type: javax.net.ssl.SSLSocket} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v2, resolved type: java.lang.String} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v2, resolved type: javax.net.ssl.SSLSocket} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v3, resolved type: java.lang.String} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v4, resolved type: javax.net.ssl.SSLSocket} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v5, resolved type: java.lang.String} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v11, resolved type: javax.net.ssl.SSLSocket} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v12, resolved type: java.lang.String} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:37:0x0137 A[Catch:{ all -> 0x012e }] */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x013d A[Catch:{ all -> 0x012e }] */
    /* JADX WARNING: Removed duplicated region for block: B:41:0x0140  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void a(okhttp3.internal.connection.c r8) {
        /*
            r7 = this;
            okhttp3.j0 r0 = r7.c
            okhttp3.e r0 = r0.a()
            javax.net.ssl.SSLSocketFactory r1 = r0.j()
            r2 = 0
            java.net.Socket r3 = r7.d     // Catch:{ AssertionError -> 0x0130 }
            okhttp3.z r4 = r0.k()     // Catch:{ AssertionError -> 0x0130 }
            java.lang.String r4 = r4.g()     // Catch:{ AssertionError -> 0x0130 }
            okhttp3.z r5 = r0.k()     // Catch:{ AssertionError -> 0x0130 }
            int r5 = r5.k()     // Catch:{ AssertionError -> 0x0130 }
            r6 = 1
            java.net.Socket r1 = r1.createSocket(r3, r4, r5, r6)     // Catch:{ AssertionError -> 0x0130 }
            javax.net.ssl.SSLSocket r1 = (javax.net.ssl.SSLSocket) r1     // Catch:{ AssertionError -> 0x0130 }
            okhttp3.p r8 = r8.a((javax.net.ssl.SSLSocket) r1)     // Catch:{ AssertionError -> 0x012b, all -> 0x0128 }
            boolean r3 = r8.c()     // Catch:{ AssertionError -> 0x012b, all -> 0x0128 }
            if (r3 == 0) goto L_0x0041
            okhttp3.k0.j.f r3 = okhttp3.k0.j.f.c()     // Catch:{ AssertionError -> 0x012b, all -> 0x0128 }
            okhttp3.z r4 = r0.k()     // Catch:{ AssertionError -> 0x012b, all -> 0x0128 }
            java.lang.String r4 = r4.g()     // Catch:{ AssertionError -> 0x012b, all -> 0x0128 }
            java.util.List r5 = r0.e()     // Catch:{ AssertionError -> 0x012b, all -> 0x0128 }
            r3.a((javax.net.ssl.SSLSocket) r1, (java.lang.String) r4, (java.util.List<okhttp3.Protocol>) r5)     // Catch:{ AssertionError -> 0x012b, all -> 0x0128 }
        L_0x0041:
            r1.startHandshake()     // Catch:{ AssertionError -> 0x012b, all -> 0x0128 }
            javax.net.ssl.SSLSession r3 = r1.getSession()     // Catch:{ AssertionError -> 0x012b, all -> 0x0128 }
            okhttp3.x r4 = okhttp3.x.a((javax.net.ssl.SSLSession) r3)     // Catch:{ AssertionError -> 0x012b, all -> 0x0128 }
            javax.net.ssl.HostnameVerifier r5 = r0.d()     // Catch:{ AssertionError -> 0x012b, all -> 0x0128 }
            okhttp3.z r6 = r0.k()     // Catch:{ AssertionError -> 0x012b, all -> 0x0128 }
            java.lang.String r6 = r6.g()     // Catch:{ AssertionError -> 0x012b, all -> 0x0128 }
            boolean r3 = r5.verify(r6, r3)     // Catch:{ AssertionError -> 0x012b, all -> 0x0128 }
            if (r3 != 0) goto L_0x00d8
            java.util.List r8 = r4.b()     // Catch:{ AssertionError -> 0x012b, all -> 0x0128 }
            boolean r2 = r8.isEmpty()     // Catch:{ AssertionError -> 0x012b, all -> 0x0128 }
            java.lang.String r3 = "Hostname "
            if (r2 != 0) goto L_0x00b6
            r2 = 0
            java.lang.Object r8 = r8.get(r2)     // Catch:{ AssertionError -> 0x012b, all -> 0x0128 }
            java.security.cert.X509Certificate r8 = (java.security.cert.X509Certificate) r8     // Catch:{ AssertionError -> 0x012b, all -> 0x0128 }
            javax.net.ssl.SSLPeerUnverifiedException r2 = new javax.net.ssl.SSLPeerUnverifiedException     // Catch:{ AssertionError -> 0x012b, all -> 0x0128 }
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ AssertionError -> 0x012b, all -> 0x0128 }
            r4.<init>()     // Catch:{ AssertionError -> 0x012b, all -> 0x0128 }
            r4.append(r3)     // Catch:{ AssertionError -> 0x012b, all -> 0x0128 }
            okhttp3.z r0 = r0.k()     // Catch:{ AssertionError -> 0x012b, all -> 0x0128 }
            java.lang.String r0 = r0.g()     // Catch:{ AssertionError -> 0x012b, all -> 0x0128 }
            r4.append(r0)     // Catch:{ AssertionError -> 0x012b, all -> 0x0128 }
            java.lang.String r0 = " not verified:\n    certificate: "
            r4.append(r0)     // Catch:{ AssertionError -> 0x012b, all -> 0x0128 }
            java.lang.String r0 = okhttp3.l.a((java.security.cert.Certificate) r8)     // Catch:{ AssertionError -> 0x012b, all -> 0x0128 }
            r4.append(r0)     // Catch:{ AssertionError -> 0x012b, all -> 0x0128 }
            java.lang.String r0 = "\n    DN: "
            r4.append(r0)     // Catch:{ AssertionError -> 0x012b, all -> 0x0128 }
            java.security.Principal r0 = r8.getSubjectDN()     // Catch:{ AssertionError -> 0x012b, all -> 0x0128 }
            java.lang.String r0 = r0.getName()     // Catch:{ AssertionError -> 0x012b, all -> 0x0128 }
            r4.append(r0)     // Catch:{ AssertionError -> 0x012b, all -> 0x0128 }
            java.lang.String r0 = "\n    subjectAltNames: "
            r4.append(r0)     // Catch:{ AssertionError -> 0x012b, all -> 0x0128 }
            java.util.List r8 = okhttp3.k0.l.d.a(r8)     // Catch:{ AssertionError -> 0x012b, all -> 0x0128 }
            r4.append(r8)     // Catch:{ AssertionError -> 0x012b, all -> 0x0128 }
            java.lang.String r8 = r4.toString()     // Catch:{ AssertionError -> 0x012b, all -> 0x0128 }
            r2.<init>(r8)     // Catch:{ AssertionError -> 0x012b, all -> 0x0128 }
            throw r2     // Catch:{ AssertionError -> 0x012b, all -> 0x0128 }
        L_0x00b6:
            javax.net.ssl.SSLPeerUnverifiedException r8 = new javax.net.ssl.SSLPeerUnverifiedException     // Catch:{ AssertionError -> 0x012b, all -> 0x0128 }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ AssertionError -> 0x012b, all -> 0x0128 }
            r2.<init>()     // Catch:{ AssertionError -> 0x012b, all -> 0x0128 }
            r2.append(r3)     // Catch:{ AssertionError -> 0x012b, all -> 0x0128 }
            okhttp3.z r0 = r0.k()     // Catch:{ AssertionError -> 0x012b, all -> 0x0128 }
            java.lang.String r0 = r0.g()     // Catch:{ AssertionError -> 0x012b, all -> 0x0128 }
            r2.append(r0)     // Catch:{ AssertionError -> 0x012b, all -> 0x0128 }
            java.lang.String r0 = " not verified (no certificates)"
            r2.append(r0)     // Catch:{ AssertionError -> 0x012b, all -> 0x0128 }
            java.lang.String r0 = r2.toString()     // Catch:{ AssertionError -> 0x012b, all -> 0x0128 }
            r8.<init>(r0)     // Catch:{ AssertionError -> 0x012b, all -> 0x0128 }
            throw r8     // Catch:{ AssertionError -> 0x012b, all -> 0x0128 }
        L_0x00d8:
            okhttp3.l r3 = r0.a()     // Catch:{ AssertionError -> 0x012b, all -> 0x0128 }
            okhttp3.z r0 = r0.k()     // Catch:{ AssertionError -> 0x012b, all -> 0x0128 }
            java.lang.String r0 = r0.g()     // Catch:{ AssertionError -> 0x012b, all -> 0x0128 }
            java.util.List r5 = r4.b()     // Catch:{ AssertionError -> 0x012b, all -> 0x0128 }
            r3.a(r0, r5)     // Catch:{ AssertionError -> 0x012b, all -> 0x0128 }
            boolean r8 = r8.c()     // Catch:{ AssertionError -> 0x012b, all -> 0x0128 }
            if (r8 == 0) goto L_0x00f9
            okhttp3.k0.j.f r8 = okhttp3.k0.j.f.c()     // Catch:{ AssertionError -> 0x012b, all -> 0x0128 }
            java.lang.String r2 = r8.b((javax.net.ssl.SSLSocket) r1)     // Catch:{ AssertionError -> 0x012b, all -> 0x0128 }
        L_0x00f9:
            r7.e = r1     // Catch:{ AssertionError -> 0x012b, all -> 0x0128 }
            okio.r r8 = okio.k.b(r1)     // Catch:{ AssertionError -> 0x012b, all -> 0x0128 }
            okio.e r8 = okio.k.a((okio.r) r8)     // Catch:{ AssertionError -> 0x012b, all -> 0x0128 }
            r7.f1923i = r8     // Catch:{ AssertionError -> 0x012b, all -> 0x0128 }
            java.net.Socket r8 = r7.e     // Catch:{ AssertionError -> 0x012b, all -> 0x0128 }
            okio.q r8 = okio.k.a((java.net.Socket) r8)     // Catch:{ AssertionError -> 0x012b, all -> 0x0128 }
            okio.d r8 = okio.k.a((okio.q) r8)     // Catch:{ AssertionError -> 0x012b, all -> 0x0128 }
            r7.f1924j = r8     // Catch:{ AssertionError -> 0x012b, all -> 0x0128 }
            r7.f1920f = r4     // Catch:{ AssertionError -> 0x012b, all -> 0x0128 }
            if (r2 == 0) goto L_0x011a
            okhttp3.Protocol r8 = okhttp3.Protocol.get(r2)     // Catch:{ AssertionError -> 0x012b, all -> 0x0128 }
            goto L_0x011c
        L_0x011a:
            okhttp3.Protocol r8 = okhttp3.Protocol.HTTP_1_1     // Catch:{ AssertionError -> 0x012b, all -> 0x0128 }
        L_0x011c:
            r7.f1921g = r8     // Catch:{ AssertionError -> 0x012b, all -> 0x0128 }
            if (r1 == 0) goto L_0x0127
            okhttp3.k0.j.f r8 = okhttp3.k0.j.f.c()
            r8.a((javax.net.ssl.SSLSocket) r1)
        L_0x0127:
            return
        L_0x0128:
            r8 = move-exception
            r2 = r1
            goto L_0x013e
        L_0x012b:
            r8 = move-exception
            r2 = r1
            goto L_0x0131
        L_0x012e:
            r8 = move-exception
            goto L_0x013e
        L_0x0130:
            r8 = move-exception
        L_0x0131:
            boolean r0 = okhttp3.k0.e.a((java.lang.AssertionError) r8)     // Catch:{ all -> 0x012e }
            if (r0 == 0) goto L_0x013d
            java.io.IOException r0 = new java.io.IOException     // Catch:{ all -> 0x012e }
            r0.<init>(r8)     // Catch:{ all -> 0x012e }
            throw r0     // Catch:{ all -> 0x012e }
        L_0x013d:
            throw r8     // Catch:{ all -> 0x012e }
        L_0x013e:
            if (r2 == 0) goto L_0x0147
            okhttp3.k0.j.f r0 = okhttp3.k0.j.f.c()
            r0.a((javax.net.ssl.SSLSocket) r2)
        L_0x0147:
            okhttp3.k0.e.a((java.net.Socket) r2)
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: okhttp3.internal.connection.f.a(okhttp3.internal.connection.c):void");
    }

    private f0 a(int i2, int i3, f0 f0Var, z zVar) {
        String str = "CONNECT " + okhttp3.k0.e.a(zVar, true) + " HTTP/1.1";
        while (true) {
            a aVar = new a((d0) null, (f) null, this.f1923i, this.f1924j);
            this.f1923i.d().a((long) i2, TimeUnit.MILLISECONDS);
            this.f1924j.d().a((long) i3, TimeUnit.MILLISECONDS);
            aVar.a(f0Var.c(), str);
            aVar.a();
            h0.a a = aVar.a(false);
            a.a(f0Var);
            h0 a2 = a.a();
            aVar.c(a2);
            int j2 = a2.j();
            if (j2 != 200) {
                if (j2 == 407) {
                    f0 a3 = this.c.a().g().a(this.c, a2);
                    if (a3 == null) {
                        throw new IOException("Failed to authenticate with proxy");
                    } else if ("close".equalsIgnoreCase(a2.b("Connection"))) {
                        return a3;
                    } else {
                        f0Var = a3;
                    }
                } else {
                    throw new IOException("Unexpected response code for CONNECT: " + a2.j());
                }
            } else if (this.f1923i.getBuffer().i() && this.f1924j.b().i()) {
                return null;
            } else {
                throw new IOException("TLS tunnel buffered too many bytes!");
            }
        }
    }

    /* access modifiers changed from: package-private */
    public boolean a(okhttp3.e eVar, List<j0> list) {
        if (this.p.size() >= this.o || this.k || !c.a.a(this.c.a(), eVar)) {
            return false;
        }
        if (eVar.k().g().equals(e().a().k().g())) {
            return true;
        }
        if (this.f1922h == null || list == null || !a(list) || eVar.d() != okhttp3.k0.l.d.a || !a(eVar.k())) {
            return false;
        }
        try {
            eVar.a().a(eVar.k().g(), b().b());
            return true;
        } catch (SSLPeerUnverifiedException unused) {
            return false;
        }
    }

    private boolean a(List<j0> list) {
        int size = list.size();
        for (int i2 = 0; i2 < size; i2++) {
            j0 j0Var = list.get(i2);
            if (j0Var.b().type() == Proxy.Type.DIRECT && this.c.b().type() == Proxy.Type.DIRECT && this.c.d().equals(j0Var.d())) {
                return true;
            }
        }
        return false;
    }

    public boolean a(z zVar) {
        if (zVar.k() != this.c.a().k().k()) {
            return false;
        }
        if (zVar.g().equals(this.c.a().k().g())) {
            return true;
        }
        if (this.f1920f == null || !okhttp3.k0.l.d.a.a(zVar.g(), (X509Certificate) this.f1920f.b().get(0))) {
            return false;
        }
        return true;
    }

    /* access modifiers changed from: package-private */
    public okhttp3.k0.h.c a(d0 d0Var, a0.a aVar) {
        if (this.f1922h != null) {
            return new okhttp3.internal.http2.e(d0Var, this, aVar, this.f1922h);
        }
        this.e.setSoTimeout(aVar.d());
        this.f1923i.d().a((long) aVar.d(), TimeUnit.MILLISECONDS);
        this.f1924j.d().a((long) aVar.b(), TimeUnit.MILLISECONDS);
        return new a(d0Var, this, this.f1923i, this.f1924j);
    }

    public void a() {
        okhttp3.k0.e.a(this.d);
    }

    public boolean a(boolean z) {
        int soTimeout;
        if (this.e.isClosed() || this.e.isInputShutdown() || this.e.isOutputShutdown()) {
            return false;
        }
        d dVar = this.f1922h;
        if (dVar != null) {
            return dVar.h(System.nanoTime());
        }
        if (z) {
            try {
                soTimeout = this.e.getSoTimeout();
                this.e.setSoTimeout(1);
                if (this.f1923i.i()) {
                    this.e.setSoTimeout(soTimeout);
                    return false;
                }
                this.e.setSoTimeout(soTimeout);
                return true;
            } catch (SocketTimeoutException unused) {
            } catch (IOException unused2) {
                return false;
            } catch (Throwable th) {
                this.e.setSoTimeout(soTimeout);
                throw th;
            }
        }
        return true;
    }

    public void a(g gVar) {
        gVar.a(ErrorCode.REFUSED_STREAM, (IOException) null);
    }

    public void a(d dVar) {
        synchronized (this.b) {
            this.o = dVar.a();
        }
    }

    /* access modifiers changed from: package-private */
    public void a(IOException iOException) {
        synchronized (this.b) {
            if (iOException instanceof StreamResetException) {
                ErrorCode errorCode = ((StreamResetException) iOException).errorCode;
                if (errorCode == ErrorCode.REFUSED_STREAM) {
                    int i2 = this.n + 1;
                    this.n = i2;
                    if (i2 > 1) {
                        this.k = true;
                        this.l++;
                    }
                } else if (errorCode != ErrorCode.CANCEL) {
                    this.k = true;
                    this.l++;
                }
            } else if (!c() || (iOException instanceof ConnectionShutdownException)) {
                this.k = true;
                if (this.m == 0) {
                    if (iOException != null) {
                        this.b.a(this.c, iOException);
                    }
                    this.l++;
                }
            }
        }
    }
}
