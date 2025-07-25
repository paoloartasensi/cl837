package okhttp3;

import com.jeremyliao.liveeventbus.BuildConfig;
import java.util.concurrent.TimeUnit;

/* compiled from: CacheControl */
public final class i {
    private final boolean a;
    private final boolean b;
    private final int c;
    private final int d;
    private final boolean e;

    /* renamed from: f  reason: collision with root package name */
    private final boolean f1890f;

    /* renamed from: g  reason: collision with root package name */
    private final boolean f1891g;

    /* renamed from: h  reason: collision with root package name */
    private final int f1892h;

    /* renamed from: i  reason: collision with root package name */
    private final int f1893i;

    /* renamed from: j  reason: collision with root package name */
    private final boolean f1894j;
    private final boolean k;
    private final boolean l;
    String m;

    static {
        a aVar = new a();
        aVar.b();
        aVar.a();
        a aVar2 = new a();
        aVar2.c();
        aVar2.a(Integer.MAX_VALUE, TimeUnit.SECONDS);
        aVar2.a();
    }

    private i(boolean z, boolean z2, int i2, int i3, boolean z3, boolean z4, boolean z5, int i4, int i5, boolean z6, boolean z7, boolean z8, String str) {
        this.a = z;
        this.b = z2;
        this.c = i2;
        this.d = i3;
        this.e = z3;
        this.f1890f = z4;
        this.f1891g = z5;
        this.f1892h = i4;
        this.f1893i = i5;
        this.f1894j = z6;
        this.k = z7;
        this.l = z8;
        this.m = str;
    }

    private String j() {
        StringBuilder sb = new StringBuilder();
        if (this.a) {
            sb.append("no-cache, ");
        }
        if (this.b) {
            sb.append("no-store, ");
        }
        if (this.c != -1) {
            sb.append("max-age=");
            sb.append(this.c);
            sb.append(", ");
        }
        if (this.d != -1) {
            sb.append("s-maxage=");
            sb.append(this.d);
            sb.append(", ");
        }
        if (this.e) {
            sb.append("private, ");
        }
        if (this.f1890f) {
            sb.append("public, ");
        }
        if (this.f1891g) {
            sb.append("must-revalidate, ");
        }
        if (this.f1892h != -1) {
            sb.append("max-stale=");
            sb.append(this.f1892h);
            sb.append(", ");
        }
        if (this.f1893i != -1) {
            sb.append("min-fresh=");
            sb.append(this.f1893i);
            sb.append(", ");
        }
        if (this.f1894j) {
            sb.append("only-if-cached, ");
        }
        if (this.k) {
            sb.append("no-transform, ");
        }
        if (this.l) {
            sb.append("immutable, ");
        }
        if (sb.length() == 0) {
            return BuildConfig.FLAVOR;
        }
        sb.delete(sb.length() - 2, sb.length());
        return sb.toString();
    }

    public boolean a() {
        return this.e;
    }

    public boolean b() {
        return this.f1890f;
    }

    public int c() {
        return this.c;
    }

    public int d() {
        return this.f1892h;
    }

    public int e() {
        return this.f1893i;
    }

    public boolean f() {
        return this.f1891g;
    }

    public boolean g() {
        return this.a;
    }

    public boolean h() {
        return this.b;
    }

    public boolean i() {
        return this.f1894j;
    }

    public String toString() {
        String str = this.m;
        if (str != null) {
            return str;
        }
        String j2 = j();
        this.m = j2;
        return j2;
    }

    /* JADX WARNING: Removed duplicated region for block: B:12:0x0041  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static okhttp3.i a(okhttp3.y r22) {
        /*
            r0 = r22
            int r1 = r22.b()
            r6 = 0
            r7 = 1
            r8 = 0
            r9 = 0
            r10 = 0
            r11 = -1
            r12 = -1
            r13 = 0
            r14 = 0
            r15 = 0
            r16 = -1
            r17 = -1
            r18 = 0
            r19 = 0
            r20 = 0
        L_0x001a:
            if (r6 >= r1) goto L_0x013f
            java.lang.String r2 = r0.a((int) r6)
            java.lang.String r4 = r0.b((int) r6)
            java.lang.String r3 = "Cache-Control"
            boolean r3 = r2.equalsIgnoreCase(r3)
            if (r3 == 0) goto L_0x0031
            if (r8 == 0) goto L_0x002f
            goto L_0x0039
        L_0x002f:
            r8 = r4
            goto L_0x003a
        L_0x0031:
            java.lang.String r3 = "Pragma"
            boolean r2 = r2.equalsIgnoreCase(r3)
            if (r2 == 0) goto L_0x0138
        L_0x0039:
            r7 = 0
        L_0x003a:
            r2 = 0
        L_0x003b:
            int r3 = r4.length()
            if (r2 >= r3) goto L_0x0138
            java.lang.String r3 = "=,;"
            int r3 = okhttp3.k0.h.e.a((java.lang.String) r4, (int) r2, (java.lang.String) r3)
            java.lang.String r2 = r4.substring(r2, r3)
            java.lang.String r2 = r2.trim()
            int r5 = r4.length()
            if (r3 == r5) goto L_0x0099
            char r5 = r4.charAt(r3)
            r0 = 44
            if (r5 == r0) goto L_0x0099
            char r0 = r4.charAt(r3)
            r5 = 59
            if (r0 != r5) goto L_0x0066
            goto L_0x0099
        L_0x0066:
            int r3 = r3 + 1
            int r0 = okhttp3.k0.h.e.b(r4, r3)
            int r3 = r4.length()
            if (r0 >= r3) goto L_0x0089
            char r3 = r4.charAt(r0)
            r5 = 34
            if (r3 != r5) goto L_0x0089
            int r0 = r0 + 1
            java.lang.String r3 = "\""
            int r3 = okhttp3.k0.h.e.a((java.lang.String) r4, (int) r0, (java.lang.String) r3)
            java.lang.String r0 = r4.substring(r0, r3)
            r5 = 1
            int r3 = r3 + r5
            goto L_0x009d
        L_0x0089:
            r5 = 1
            java.lang.String r3 = ",;"
            int r3 = okhttp3.k0.h.e.a((java.lang.String) r4, (int) r0, (java.lang.String) r3)
            java.lang.String r0 = r4.substring(r0, r3)
            java.lang.String r0 = r0.trim()
            goto L_0x009d
        L_0x0099:
            r5 = 1
            int r3 = r3 + 1
            r0 = 0
        L_0x009d:
            java.lang.String r5 = "no-cache"
            boolean r5 = r5.equalsIgnoreCase(r2)
            if (r5 == 0) goto L_0x00a9
            r5 = -1
            r9 = 1
            goto L_0x0133
        L_0x00a9:
            java.lang.String r5 = "no-store"
            boolean r5 = r5.equalsIgnoreCase(r2)
            if (r5 == 0) goto L_0x00b5
            r5 = -1
            r10 = 1
            goto L_0x0133
        L_0x00b5:
            java.lang.String r5 = "max-age"
            boolean r5 = r5.equalsIgnoreCase(r2)
            if (r5 == 0) goto L_0x00c4
            r5 = -1
            int r11 = okhttp3.k0.h.e.a(r0, r5)
            goto L_0x0133
        L_0x00c4:
            java.lang.String r5 = "s-maxage"
            boolean r5 = r5.equalsIgnoreCase(r2)
            if (r5 == 0) goto L_0x00d2
            r5 = -1
            int r12 = okhttp3.k0.h.e.a(r0, r5)
            goto L_0x0133
        L_0x00d2:
            java.lang.String r5 = "private"
            boolean r5 = r5.equalsIgnoreCase(r2)
            if (r5 == 0) goto L_0x00dd
            r5 = -1
            r13 = 1
            goto L_0x0133
        L_0x00dd:
            java.lang.String r5 = "public"
            boolean r5 = r5.equalsIgnoreCase(r2)
            if (r5 == 0) goto L_0x00e8
            r5 = -1
            r14 = 1
            goto L_0x0133
        L_0x00e8:
            java.lang.String r5 = "must-revalidate"
            boolean r5 = r5.equalsIgnoreCase(r2)
            if (r5 == 0) goto L_0x00f3
            r5 = -1
            r15 = 1
            goto L_0x0133
        L_0x00f3:
            java.lang.String r5 = "max-stale"
            boolean r5 = r5.equalsIgnoreCase(r2)
            if (r5 == 0) goto L_0x0104
            r2 = 2147483647(0x7fffffff, float:NaN)
            int r16 = okhttp3.k0.h.e.a(r0, r2)
            r5 = -1
            goto L_0x0133
        L_0x0104:
            java.lang.String r5 = "min-fresh"
            boolean r5 = r5.equalsIgnoreCase(r2)
            if (r5 == 0) goto L_0x0112
            r5 = -1
            int r17 = okhttp3.k0.h.e.a(r0, r5)
            goto L_0x0133
        L_0x0112:
            r5 = -1
            java.lang.String r0 = "only-if-cached"
            boolean r0 = r0.equalsIgnoreCase(r2)
            if (r0 == 0) goto L_0x011e
            r18 = 1
            goto L_0x0133
        L_0x011e:
            java.lang.String r0 = "no-transform"
            boolean r0 = r0.equalsIgnoreCase(r2)
            if (r0 == 0) goto L_0x0129
            r19 = 1
            goto L_0x0133
        L_0x0129:
            java.lang.String r0 = "immutable"
            boolean r0 = r0.equalsIgnoreCase(r2)
            if (r0 == 0) goto L_0x0133
            r20 = 1
        L_0x0133:
            r0 = r22
            r2 = r3
            goto L_0x003b
        L_0x0138:
            r5 = -1
            int r6 = r6 + 1
            r0 = r22
            goto L_0x001a
        L_0x013f:
            if (r7 != 0) goto L_0x0144
            r21 = 0
            goto L_0x0146
        L_0x0144:
            r21 = r8
        L_0x0146:
            okhttp3.i r0 = new okhttp3.i
            r8 = r0
            r8.<init>(r9, r10, r11, r12, r13, r14, r15, r16, r17, r18, r19, r20, r21)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: okhttp3.i.a(okhttp3.y):okhttp3.i");
    }

    /* compiled from: CacheControl */
    public static final class a {
        boolean a;
        boolean b;
        int c = -1;
        int d = -1;
        int e = -1;

        /* renamed from: f  reason: collision with root package name */
        boolean f1895f;

        /* renamed from: g  reason: collision with root package name */
        boolean f1896g;

        /* renamed from: h  reason: collision with root package name */
        boolean f1897h;

        public a a(int i2, TimeUnit timeUnit) {
            if (i2 >= 0) {
                long seconds = timeUnit.toSeconds((long) i2);
                this.d = seconds > 2147483647L ? Integer.MAX_VALUE : (int) seconds;
                return this;
            }
            throw new IllegalArgumentException("maxStale < 0: " + i2);
        }

        public a b() {
            this.a = true;
            return this;
        }

        public a c() {
            this.f1895f = true;
            return this;
        }

        public i a() {
            return new i(this);
        }
    }

    i(a aVar) {
        this.a = aVar.a;
        this.b = aVar.b;
        this.c = aVar.c;
        this.d = -1;
        this.e = false;
        this.f1890f = false;
        this.f1891g = false;
        this.f1892h = aVar.d;
        this.f1893i = aVar.e;
        this.f1894j = aVar.f1895f;
        this.k = aVar.f1896g;
        this.l = aVar.f1897h;
    }
}
