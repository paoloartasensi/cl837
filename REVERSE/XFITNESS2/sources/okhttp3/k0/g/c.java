package okhttp3.k0.g;

import java.util.Date;
import java.util.concurrent.TimeUnit;
import okhttp3.f0;
import okhttp3.h0;
import okhttp3.i;
import okhttp3.k0.h.d;
import okhttp3.k0.h.e;
import okhttp3.y;

/* compiled from: CacheStrategy */
public final class c {
    public final f0 a;
    public final h0 b;

    c(f0 f0Var, h0 h0Var) {
        this.a = f0Var;
        this.b = h0Var;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:28:0x0056, code lost:
        if (r3.c().a() == false) goto L_0x0059;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x0059, code lost:
        return false;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean a(okhttp3.h0 r3, okhttp3.f0 r4) {
        /*
            int r0 = r3.j()
            r1 = 200(0xc8, float:2.8E-43)
            r2 = 0
            if (r0 == r1) goto L_0x005a
            r1 = 410(0x19a, float:5.75E-43)
            if (r0 == r1) goto L_0x005a
            r1 = 414(0x19e, float:5.8E-43)
            if (r0 == r1) goto L_0x005a
            r1 = 501(0x1f5, float:7.02E-43)
            if (r0 == r1) goto L_0x005a
            r1 = 203(0xcb, float:2.84E-43)
            if (r0 == r1) goto L_0x005a
            r1 = 204(0xcc, float:2.86E-43)
            if (r0 == r1) goto L_0x005a
            r1 = 307(0x133, float:4.3E-43)
            if (r0 == r1) goto L_0x0031
            r1 = 308(0x134, float:4.32E-43)
            if (r0 == r1) goto L_0x005a
            r1 = 404(0x194, float:5.66E-43)
            if (r0 == r1) goto L_0x005a
            r1 = 405(0x195, float:5.68E-43)
            if (r0 == r1) goto L_0x005a
            switch(r0) {
                case 300: goto L_0x005a;
                case 301: goto L_0x005a;
                case 302: goto L_0x0031;
                default: goto L_0x0030;
            }
        L_0x0030:
            goto L_0x0059
        L_0x0031:
            java.lang.String r0 = "Expires"
            java.lang.String r0 = r3.b(r0)
            if (r0 != 0) goto L_0x005a
            okhttp3.i r0 = r3.c()
            int r0 = r0.c()
            r1 = -1
            if (r0 != r1) goto L_0x005a
            okhttp3.i r0 = r3.c()
            boolean r0 = r0.b()
            if (r0 != 0) goto L_0x005a
            okhttp3.i r0 = r3.c()
            boolean r0 = r0.a()
            if (r0 == 0) goto L_0x0059
            goto L_0x005a
        L_0x0059:
            return r2
        L_0x005a:
            okhttp3.i r3 = r3.c()
            boolean r3 = r3.h()
            if (r3 != 0) goto L_0x006f
            okhttp3.i r3 = r4.b()
            boolean r3 = r3.h()
            if (r3 != 0) goto L_0x006f
            r2 = 1
        L_0x006f:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: okhttp3.k0.g.c.a(okhttp3.h0, okhttp3.f0):boolean");
    }

    /* compiled from: CacheStrategy */
    public static class a {
        final long a;
        final f0 b;
        final h0 c;
        private Date d;
        private String e;

        /* renamed from: f  reason: collision with root package name */
        private Date f2031f;

        /* renamed from: g  reason: collision with root package name */
        private String f2032g;

        /* renamed from: h  reason: collision with root package name */
        private Date f2033h;

        /* renamed from: i  reason: collision with root package name */
        private long f2034i;

        /* renamed from: j  reason: collision with root package name */
        private long f2035j;
        private String k;
        private int l = -1;

        public a(long j2, f0 f0Var, h0 h0Var) {
            this.a = j2;
            this.b = f0Var;
            this.c = h0Var;
            if (h0Var != null) {
                this.f2034i = h0Var.u();
                this.f2035j = h0Var.s();
                y n = h0Var.n();
                int b2 = n.b();
                for (int i2 = 0; i2 < b2; i2++) {
                    String a2 = n.a(i2);
                    String b3 = n.b(i2);
                    if ("Date".equalsIgnoreCase(a2)) {
                        this.d = d.a(b3);
                        this.e = b3;
                    } else if ("Expires".equalsIgnoreCase(a2)) {
                        this.f2033h = d.a(b3);
                    } else if ("Last-Modified".equalsIgnoreCase(a2)) {
                        this.f2031f = d.a(b3);
                        this.f2032g = b3;
                    } else if ("ETag".equalsIgnoreCase(a2)) {
                        this.k = b3;
                    } else if ("Age".equalsIgnoreCase(a2)) {
                        this.l = e.a(b3, -1);
                    }
                }
            }
        }

        private long b() {
            Date date = this.d;
            long j2 = 0;
            if (date != null) {
                j2 = Math.max(0, this.f2035j - date.getTime());
            }
            int i2 = this.l;
            if (i2 != -1) {
                j2 = Math.max(j2, TimeUnit.SECONDS.toMillis((long) i2));
            }
            long j3 = this.f2035j;
            return j2 + (j3 - this.f2034i) + (this.a - j3);
        }

        private long c() {
            long j2;
            long j3;
            i c2 = this.c.c();
            if (c2.c() != -1) {
                return TimeUnit.SECONDS.toMillis((long) c2.c());
            }
            if (this.f2033h != null) {
                Date date = this.d;
                if (date != null) {
                    j3 = date.getTime();
                } else {
                    j3 = this.f2035j;
                }
                long time = this.f2033h.getTime() - j3;
                if (time > 0) {
                    return time;
                }
                return 0;
            } else if (this.f2031f == null || this.c.t().g().l() != null) {
                return 0;
            } else {
                Date date2 = this.d;
                if (date2 != null) {
                    j2 = date2.getTime();
                } else {
                    j2 = this.f2034i;
                }
                long time2 = j2 - this.f2031f.getTime();
                if (time2 > 0) {
                    return time2 / 10;
                }
                return 0;
            }
        }

        private c d() {
            if (this.c == null) {
                return new c(this.b, (h0) null);
            }
            if (this.b.d() && this.c.m() == null) {
                return new c(this.b, (h0) null);
            }
            if (!c.a(this.c, this.b)) {
                return new c(this.b, (h0) null);
            }
            i b2 = this.b.b();
            if (b2.g() || a(this.b)) {
                return new c(this.b, (h0) null);
            }
            i c2 = this.c.c();
            long b3 = b();
            long c3 = c();
            if (b2.c() != -1) {
                c3 = Math.min(c3, TimeUnit.SECONDS.toMillis((long) b2.c()));
            }
            long j2 = 0;
            long millis = b2.e() != -1 ? TimeUnit.SECONDS.toMillis((long) b2.e()) : 0;
            if (!c2.f() && b2.d() != -1) {
                j2 = TimeUnit.SECONDS.toMillis((long) b2.d());
            }
            if (!c2.g()) {
                long j3 = millis + b3;
                if (j3 < j2 + c3) {
                    h0.a q = this.c.q();
                    if (j3 >= c3) {
                        q.a("Warning", "110 HttpURLConnection \"Response is stale\"");
                    }
                    if (b3 > 86400000 && e()) {
                        q.a("Warning", "113 HttpURLConnection \"Heuristic expiration\"");
                    }
                    return new c((f0) null, q.a());
                }
            }
            String str = this.k;
            String str2 = "If-Modified-Since";
            if (str != null) {
                str2 = "If-None-Match";
            } else if (this.f2031f != null) {
                str = this.f2032g;
            } else if (this.d == null) {
                return new c(this.b, (h0) null);
            } else {
                str = this.e;
            }
            y.a a2 = this.b.c().a();
            okhttp3.k0.c.a.a(a2, str2, str);
            f0.a f2 = this.b.f();
            f2.a(a2.a());
            return new c(f2.a(), this.c);
        }

        private boolean e() {
            return this.c.c().c() == -1 && this.f2033h == null;
        }

        public c a() {
            c d2 = d();
            return (d2.a == null || !this.b.b().i()) ? d2 : new c((f0) null, (h0) null);
        }

        private static boolean a(f0 f0Var) {
            return (f0Var.a("If-Modified-Since") == null && f0Var.a("If-None-Match") == null) ? false : true;
        }
    }
}
