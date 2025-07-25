package retrofit2;

import java.util.regex.Pattern;
import okhttp3.b0;
import okhttp3.c0;
import okhttp3.f0;
import okhttp3.g0;
import okhttp3.w;
import okhttp3.y;
import okhttp3.z;
import okio.c;
import okio.d;

/* compiled from: RequestBuilder */
final class p {
    private static final char[] l = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
    private static final Pattern m = Pattern.compile("(.*/)?(\\.|%2e|%2E){1,2}(/.*)?");
    private final String a;
    private final z b;
    private String c;
    private z.a d;
    private final f0.a e = new f0.a();

    /* renamed from: f  reason: collision with root package name */
    private final y.a f2131f;

    /* renamed from: g  reason: collision with root package name */
    private b0 f2132g;

    /* renamed from: h  reason: collision with root package name */
    private final boolean f2133h;

    /* renamed from: i  reason: collision with root package name */
    private c0.a f2134i;

    /* renamed from: j  reason: collision with root package name */
    private w.a f2135j;
    private g0 k;

    /* compiled from: RequestBuilder */
    private static class a extends g0 {
        private final g0 a;
        private final b0 b;

        a(g0 g0Var, b0 b0Var) {
            this.a = g0Var;
            this.b = b0Var;
        }

        public long a() {
            return this.a.a();
        }

        public b0 b() {
            return this.b;
        }

        public void a(d dVar) {
            this.a.a(dVar);
        }
    }

    p(String str, z zVar, String str2, y yVar, b0 b0Var, boolean z, boolean z2, boolean z3) {
        this.a = str;
        this.b = zVar;
        this.c = str2;
        this.f2132g = b0Var;
        this.f2133h = z;
        if (yVar != null) {
            this.f2131f = yVar.a();
        } else {
            this.f2131f = new y.a();
        }
        if (z2) {
            this.f2135j = new w.a();
        } else if (z3) {
            c0.a aVar = new c0.a();
            this.f2134i = aVar;
            aVar.a(c0.f1853f);
        }
    }

    /* access modifiers changed from: package-private */
    public void a(String str, String str2) {
        if ("Content-Type".equalsIgnoreCase(str)) {
            try {
                this.f2132g = b0.a(str2);
            } catch (IllegalArgumentException e2) {
                throw new IllegalArgumentException("Malformed content type: " + str2, e2);
            }
        } else {
            this.f2131f.a(str, str2);
        }
    }

    /* access modifiers changed from: package-private */
    public void b(String str, String str2, boolean z) {
        if (this.c != null) {
            String a2 = a(str2, z);
            String str3 = this.c;
            String replace = str3.replace("{" + str + "}", a2);
            if (!m.matcher(replace).matches()) {
                this.c = replace;
                return;
            }
            throw new IllegalArgumentException("@Path parameters shouldn't perform path traversal ('.' or '..'): " + str2);
        }
        throw new AssertionError();
    }

    /* access modifiers changed from: package-private */
    public void c(String str, String str2, boolean z) {
        String str3 = this.c;
        if (str3 != null) {
            z.a a2 = this.b.a(str3);
            this.d = a2;
            if (a2 != null) {
                this.c = null;
            } else {
                throw new IllegalArgumentException("Malformed URL. Base: " + this.b + ", Relative: " + this.c);
            }
        }
        if (z) {
            this.d.a(str, str2);
        } else {
            this.d.b(str, str2);
        }
    }

    private static String a(String str, boolean z) {
        int length = str.length();
        int i2 = 0;
        while (i2 < length) {
            int codePointAt = str.codePointAt(i2);
            if (codePointAt < 32 || codePointAt >= 127 || " \"<>^`{}|\\?#".indexOf(codePointAt) != -1 || (!z && (codePointAt == 47 || codePointAt == 37))) {
                c cVar = new c();
                cVar.a(str, 0, i2);
                a(cVar, str, i2, length, z);
                return cVar.q();
            }
            i2 += Character.charCount(codePointAt);
        }
        return str;
    }

    private static void a(c cVar, String str, int i2, int i3, boolean z) {
        c cVar2 = null;
        while (i2 < i3) {
            int codePointAt = str.codePointAt(i2);
            if (!z || !(codePointAt == 9 || codePointAt == 10 || codePointAt == 12 || codePointAt == 13)) {
                if (codePointAt < 32 || codePointAt >= 127 || " \"<>^`{}|\\?#".indexOf(codePointAt) != -1 || (!z && (codePointAt == 47 || codePointAt == 37))) {
                    if (cVar2 == null) {
                        cVar2 = new c();
                    }
                    cVar2.c(codePointAt);
                    while (!cVar2.i()) {
                        byte readByte = cVar2.readByte() & 255;
                        cVar.writeByte(37);
                        cVar.writeByte((int) l[(readByte >> 4) & 15]);
                        cVar.writeByte((int) l[readByte & 15]);
                    }
                } else {
                    cVar.c(codePointAt);
                }
            }
            i2 += Character.charCount(codePointAt);
        }
    }

    /* access modifiers changed from: package-private */
    public void a(String str, String str2, boolean z) {
        if (z) {
            this.f2135j.b(str, str2);
        } else {
            this.f2135j.a(str, str2);
        }
    }

    /* access modifiers changed from: package-private */
    public void a(y yVar, g0 g0Var) {
        this.f2134i.a(yVar, g0Var);
    }

    /* access modifiers changed from: package-private */
    public void a(c0.b bVar) {
        this.f2134i.a(bVar);
    }

    /* access modifiers changed from: package-private */
    public f0.a a() {
        z zVar;
        z.a aVar = this.d;
        if (aVar != null) {
            zVar = aVar.a();
        } else {
            zVar = this.b.b(this.c);
            if (zVar == null) {
                throw new IllegalArgumentException("Malformed URL. Base: " + this.b + ", Relative: " + this.c);
            }
        }
        a aVar2 = this.k;
        if (aVar2 == null) {
            w.a aVar3 = this.f2135j;
            if (aVar3 != null) {
                aVar2 = aVar3.a();
            } else {
                c0.a aVar4 = this.f2134i;
                if (aVar4 != null) {
                    aVar2 = aVar4.a();
                } else if (this.f2133h) {
                    aVar2 = g0.a((b0) null, new byte[0]);
                }
            }
        }
        b0 b0Var = this.f2132g;
        if (b0Var != null) {
            if (aVar2 != null) {
                aVar2 = new a(aVar2, b0Var);
            } else {
                this.f2131f.a("Content-Type", b0Var.toString());
            }
        }
        f0.a aVar5 = this.e;
        aVar5.a(zVar);
        aVar5.a(this.f2131f.a());
        aVar5.a(this.a, aVar2);
        return aVar5;
    }
}
