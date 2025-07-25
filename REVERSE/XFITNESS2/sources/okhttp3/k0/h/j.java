package okhttp3.k0.h;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.ProtocolException;
import java.net.Proxy;
import java.net.SocketTimeoutException;
import java.security.cert.CertificateException;
import javax.net.ssl.SSLHandshakeException;
import javax.net.ssl.SSLPeerUnverifiedException;
import okhttp3.a0;
import okhttp3.d0;
import okhttp3.f0;
import okhttp3.g0;
import okhttp3.h0;
import okhttp3.j0;
import okhttp3.k0.e;
import okhttp3.z;

/* compiled from: RetryAndFollowUpInterceptor */
public final class j implements a0 {
    private final d0 a;

    public j(d0 d0Var) {
        this.a = d0Var;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:45:0x00b1, code lost:
        r1.d();
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public okhttp3.h0 a(okhttp3.a0.a r9) {
        /*
            r8 = this;
            okhttp3.f0 r0 = r9.a()
            okhttp3.k0.h.g r9 = (okhttp3.k0.h.g) r9
            okhttp3.internal.connection.j r1 = r9.f()
            r2 = 0
            r3 = 0
            r4 = r3
            r5 = 0
        L_0x000e:
            r1.a((okhttp3.f0) r0)
            boolean r6 = r1.f()
            if (r6 != 0) goto L_0x00bf
            okhttp3.h0 r0 = r9.a(r0, r1, r3)     // Catch:{ RouteException -> 0x00a6, IOException -> 0x0096 }
            if (r4 == 0) goto L_0x0033
            okhttp3.h0$a r0 = r0.q()
            okhttp3.h0$a r4 = r4.q()
            r4.a((okhttp3.i0) r3)
            okhttp3.h0 r4 = r4.a()
            r0.c(r4)
            okhttp3.h0 r0 = r0.a()
        L_0x0033:
            r4 = r0
            okhttp3.k0.c r0 = okhttp3.k0.c.a
            okhttp3.internal.connection.d r0 = r0.a((okhttp3.h0) r4)
            if (r0 == 0) goto L_0x0045
            okhttp3.internal.connection.f r6 = r0.b()
            okhttp3.j0 r6 = r6.e()
            goto L_0x0046
        L_0x0045:
            r6 = r3
        L_0x0046:
            okhttp3.f0 r6 = r8.a((okhttp3.h0) r4, (okhttp3.j0) r6)
            if (r6 != 0) goto L_0x0058
            if (r0 == 0) goto L_0x0057
            boolean r9 = r0.f()
            if (r9 == 0) goto L_0x0057
            r1.h()
        L_0x0057:
            return r4
        L_0x0058:
            okhttp3.g0 r7 = r6.a()
            if (r7 == 0) goto L_0x0065
            boolean r7 = r7.d()
            if (r7 == 0) goto L_0x0065
            return r4
        L_0x0065:
            okhttp3.i0 r7 = r4.a()
            okhttp3.k0.e.a((java.io.Closeable) r7)
            boolean r7 = r1.e()
            if (r7 == 0) goto L_0x0075
            r0.c()
        L_0x0075:
            int r5 = r5 + 1
            r0 = 20
            if (r5 > r0) goto L_0x007d
            r0 = r6
            goto L_0x000e
        L_0x007d:
            java.net.ProtocolException r9 = new java.net.ProtocolException
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r1 = "Too many follow-up requests: "
            r0.append(r1)
            r0.append(r5)
            java.lang.String r0 = r0.toString()
            r9.<init>(r0)
            throw r9
        L_0x0094:
            r9 = move-exception
            goto L_0x00bb
        L_0x0096:
            r6 = move-exception
            boolean r7 = r6 instanceof okhttp3.internal.http2.ConnectionShutdownException     // Catch:{ all -> 0x0094 }
            if (r7 != 0) goto L_0x009d
            r7 = 1
            goto L_0x009e
        L_0x009d:
            r7 = 0
        L_0x009e:
            boolean r7 = r8.a(r6, r1, r7, r0)     // Catch:{ all -> 0x0094 }
            if (r7 == 0) goto L_0x00a5
            goto L_0x00b1
        L_0x00a5:
            throw r6     // Catch:{ all -> 0x0094 }
        L_0x00a6:
            r6 = move-exception
            java.io.IOException r7 = r6.getLastConnectException()     // Catch:{ all -> 0x0094 }
            boolean r7 = r8.a(r7, r1, r2, r0)     // Catch:{ all -> 0x0094 }
            if (r7 == 0) goto L_0x00b6
        L_0x00b1:
            r1.d()
            goto L_0x000e
        L_0x00b6:
            java.io.IOException r9 = r6.getFirstConnectException()     // Catch:{ all -> 0x0094 }
            throw r9     // Catch:{ all -> 0x0094 }
        L_0x00bb:
            r1.d()
            throw r9
        L_0x00bf:
            java.io.IOException r9 = new java.io.IOException
            java.lang.String r0 = "Canceled"
            r9.<init>(r0)
            goto L_0x00c8
        L_0x00c7:
            throw r9
        L_0x00c8:
            goto L_0x00c7
        */
        throw new UnsupportedOperationException("Method not decompiled: okhttp3.k0.h.j.a(okhttp3.a0$a):okhttp3.h0");
    }

    private boolean a(IOException iOException, okhttp3.internal.connection.j jVar, boolean z, f0 f0Var) {
        if (!this.a.y()) {
            return false;
        }
        if ((!z || !a(iOException, f0Var)) && a(iOException, z) && jVar.b()) {
            return true;
        }
        return false;
    }

    private boolean a(IOException iOException, f0 f0Var) {
        g0 a2 = f0Var.a();
        return (a2 != null && a2.d()) || (iOException instanceof FileNotFoundException);
    }

    private boolean a(IOException iOException, boolean z) {
        if (iOException instanceof ProtocolException) {
            return false;
        }
        if (iOException instanceof InterruptedIOException) {
            if (!(iOException instanceof SocketTimeoutException) || z) {
                return false;
            }
            return true;
        } else if ((!(iOException instanceof SSLHandshakeException) || !(iOException.getCause() instanceof CertificateException)) && !(iOException instanceof SSLPeerUnverifiedException)) {
            return true;
        } else {
            return false;
        }
    }

    private f0 a(h0 h0Var, j0 j0Var) {
        String b;
        z b2;
        Proxy proxy;
        if (h0Var != null) {
            int j2 = h0Var.j();
            String e = h0Var.t().e();
            g0 g0Var = null;
            if (j2 == 307 || j2 == 308) {
                if (!e.equals("GET") && !e.equals("HEAD")) {
                    return null;
                }
            } else if (j2 == 401) {
                return this.a.b().a(j0Var, h0Var);
            } else {
                if (j2 != 503) {
                    if (j2 == 407) {
                        if (j0Var != null) {
                            proxy = j0Var.b();
                        } else {
                            proxy = this.a.u();
                        }
                        if (proxy.type() == Proxy.Type.HTTP) {
                            return this.a.v().a(j0Var, h0Var);
                        }
                        throw new ProtocolException("Received HTTP_PROXY_AUTH (407) code while not using proxy");
                    } else if (j2 != 408) {
                        switch (j2) {
                            case 300:
                            case 301:
                            case 302:
                            case 303:
                                break;
                            default:
                                return null;
                        }
                    } else if (!this.a.y()) {
                        return null;
                    } else {
                        g0 a2 = h0Var.t().a();
                        if (a2 != null && a2.d()) {
                            return null;
                        }
                        if ((h0Var.r() == null || h0Var.r().j() != 408) && a(h0Var, 0) <= 0) {
                            return h0Var.t();
                        }
                        return null;
                    }
                } else if ((h0Var.r() == null || h0Var.r().j() != 503) && a(h0Var, Integer.MAX_VALUE) == 0) {
                    return h0Var.t();
                } else {
                    return null;
                }
            }
            if (!this.a.m() || (b = h0Var.b("Location")) == null || (b2 = h0Var.t().g().b(b)) == null) {
                return null;
            }
            if (!b2.n().equals(h0Var.t().g().n()) && !this.a.n()) {
                return null;
            }
            f0.a f2 = h0Var.t().f();
            if (f.b(e)) {
                boolean d = f.d(e);
                if (f.c(e)) {
                    f2.a("GET", (g0) null);
                } else {
                    if (d) {
                        g0Var = h0Var.t().a();
                    }
                    f2.a(e, g0Var);
                }
                if (!d) {
                    f2.a("Transfer-Encoding");
                    f2.a("Content-Length");
                    f2.a("Content-Type");
                }
            }
            if (!e.a(h0Var.t().g(), b2)) {
                f2.a("Authorization");
            }
            f2.a(b2);
            return f2.a();
        }
        throw new IllegalStateException();
    }

    private int a(h0 h0Var, int i2) {
        String b = h0Var.b("Retry-After");
        if (b == null) {
            return i2;
        }
        if (b.matches("\\d+")) {
            return Integer.valueOf(b).intValue();
        }
        return Integer.MAX_VALUE;
    }
}
