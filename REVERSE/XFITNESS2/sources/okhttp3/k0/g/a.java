package okhttp3.k0.g;

import java.io.Closeable;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import okhttp3.Protocol;
import okhttp3.a0;
import okhttp3.f0;
import okhttp3.h0;
import okhttp3.i0;
import okhttp3.k0.g.c;
import okhttp3.k0.h.f;
import okhttp3.k0.h.h;
import okhttp3.y;
import okio.c;
import okio.d;
import okio.e;
import okio.k;
import okio.q;
import okio.r;
import okio.s;

/* compiled from: CacheInterceptor */
public final class a implements a0 {
    final d a;

    /* renamed from: okhttp3.k0.g.a$a  reason: collision with other inner class name */
    /* compiled from: CacheInterceptor */
    class C0094a implements r {
        boolean e;

        /* renamed from: f  reason: collision with root package name */
        final /* synthetic */ e f2028f;

        /* renamed from: g  reason: collision with root package name */
        final /* synthetic */ b f2029g;

        /* renamed from: h  reason: collision with root package name */
        final /* synthetic */ d f2030h;

        C0094a(a aVar, e eVar, b bVar, d dVar) {
            this.f2028f = eVar;
            this.f2029g = bVar;
            this.f2030h = dVar;
        }

        public long b(c cVar, long j2) {
            try {
                long b = this.f2028f.b(cVar, j2);
                if (b == -1) {
                    if (!this.e) {
                        this.e = true;
                        this.f2030h.close();
                    }
                    return -1;
                }
                cVar.a(this.f2030h.b(), cVar.r() - b, b);
                this.f2030h.f();
                return b;
            } catch (IOException e2) {
                if (!this.e) {
                    this.e = true;
                    this.f2029g.a();
                }
                throw e2;
            }
        }

        public void close() {
            if (!this.e && !okhttp3.k0.e.a((r) this, 100, TimeUnit.MILLISECONDS)) {
                this.e = true;
                this.f2029g.a();
            }
            this.f2028f.close();
        }

        public s d() {
            return this.f2028f.d();
        }
    }

    public a(d dVar) {
        this.a = dVar;
    }

    static boolean b(String str) {
        return !"Connection".equalsIgnoreCase(str) && !"Keep-Alive".equalsIgnoreCase(str) && !"Proxy-Authenticate".equalsIgnoreCase(str) && !"Proxy-Authorization".equalsIgnoreCase(str) && !"TE".equalsIgnoreCase(str) && !"Trailers".equalsIgnoreCase(str) && !"Transfer-Encoding".equalsIgnoreCase(str) && !"Upgrade".equalsIgnoreCase(str);
    }

    public h0 a(a0.a aVar) {
        d dVar = this.a;
        h0 a2 = dVar != null ? dVar.a(aVar.a()) : null;
        c a3 = new c.a(System.currentTimeMillis(), aVar.a(), a2).a();
        f0 f0Var = a3.a;
        h0 h0Var = a3.b;
        d dVar2 = this.a;
        if (dVar2 != null) {
            dVar2.a(a3);
        }
        if (a2 != null && h0Var == null) {
            okhttp3.k0.e.a((Closeable) a2.a());
        }
        if (f0Var == null && h0Var == null) {
            h0.a aVar2 = new h0.a();
            aVar2.a(aVar.a());
            aVar2.a(Protocol.HTTP_1_1);
            aVar2.a(504);
            aVar2.a("Unsatisfiable Request (only-if-cached)");
            aVar2.a(okhttp3.k0.e.d);
            aVar2.b(-1);
            aVar2.a(System.currentTimeMillis());
            return aVar2.a();
        } else if (f0Var == null) {
            h0.a q = h0Var.q();
            q.a(a(h0Var));
            return q.a();
        } else {
            try {
                h0 a4 = aVar.a(f0Var);
                if (a4 == null && a2 != null) {
                }
                if (h0Var != null) {
                    if (a4.j() == 304) {
                        h0.a q2 = h0Var.q();
                        q2.a(a(h0Var.n(), a4.n()));
                        q2.b(a4.u());
                        q2.a(a4.s());
                        q2.a(a(h0Var));
                        q2.b(a(a4));
                        h0 a5 = q2.a();
                        a4.a().close();
                        this.a.a();
                        this.a.a(h0Var, a5);
                        return a5;
                    }
                    okhttp3.k0.e.a((Closeable) h0Var.a());
                }
                h0.a q3 = a4.q();
                q3.a(a(h0Var));
                q3.b(a(a4));
                h0 a6 = q3.a();
                if (this.a != null) {
                    if (okhttp3.k0.h.e.b(a6) && c.a(a6, f0Var)) {
                        return a(this.a.a(a6), a6);
                    }
                    if (f.a(f0Var.e())) {
                        try {
                            this.a.b(f0Var);
                        } catch (IOException unused) {
                        }
                    }
                }
                return a6;
            } finally {
                if (a2 != null) {
                    okhttp3.k0.e.a((Closeable) a2.a());
                }
            }
        }
    }

    private static h0 a(h0 h0Var) {
        if (h0Var == null || h0Var.a() == null) {
            return h0Var;
        }
        h0.a q = h0Var.q();
        q.a((i0) null);
        return q.a();
    }

    private h0 a(b bVar, h0 h0Var) {
        q b;
        if (bVar == null || (b = bVar.b()) == null) {
            return h0Var;
        }
        C0094a aVar = new C0094a(this, h0Var.a().m(), bVar, k.a(b));
        String b2 = h0Var.b("Content-Type");
        long c = h0Var.a().c();
        h0.a q = h0Var.q();
        q.a((i0) new h(b2, c, k.a((r) aVar)));
        return q.a();
    }

    private static y a(y yVar, y yVar2) {
        y.a aVar = new y.a();
        int b = yVar.b();
        for (int i2 = 0; i2 < b; i2++) {
            String a2 = yVar.a(i2);
            String b2 = yVar.b(i2);
            if ((!"Warning".equalsIgnoreCase(a2) || !b2.startsWith("1")) && (a(a2) || !b(a2) || yVar2.a(a2) == null)) {
                okhttp3.k0.c.a.a(aVar, a2, b2);
            }
        }
        int b3 = yVar2.b();
        for (int i3 = 0; i3 < b3; i3++) {
            String a3 = yVar2.a(i3);
            if (!a(a3) && b(a3)) {
                okhttp3.k0.c.a.a(aVar, a3, yVar2.b(i3));
            }
        }
        return aVar.a();
    }

    static boolean a(String str) {
        return "Content-Length".equalsIgnoreCase(str) || "Content-Encoding".equalsIgnoreCase(str) || "Content-Type".equalsIgnoreCase(str);
    }
}
