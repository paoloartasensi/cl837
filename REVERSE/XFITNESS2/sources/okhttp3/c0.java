package okhttp3;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import okhttp3.k0.e;
import okio.ByteString;
import okio.d;

/* compiled from: MultipartBody */
public final class c0 extends g0 {
    public static final b0 e = b0.a("multipart/mixed");

    /* renamed from: f  reason: collision with root package name */
    public static final b0 f1853f = b0.a("multipart/form-data");

    /* renamed from: g  reason: collision with root package name */
    private static final byte[] f1854g = {58, 32};

    /* renamed from: h  reason: collision with root package name */
    private static final byte[] f1855h = {13, 10};

    /* renamed from: i  reason: collision with root package name */
    private static final byte[] f1856i = {45, 45};
    private final ByteString a;
    private final b0 b;
    private final List<b> c;
    private long d = -1;

    /* compiled from: MultipartBody */
    public static final class a {
        private final ByteString a;
        private b0 b;
        private final List<b> c;

        public a() {
            this(UUID.randomUUID().toString());
        }

        public a a(b0 b0Var) {
            if (b0Var == null) {
                throw new NullPointerException("type == null");
            } else if (b0Var.b().equals("multipart")) {
                this.b = b0Var;
                return this;
            } else {
                throw new IllegalArgumentException("multipart != " + b0Var);
            }
        }

        public a(String str) {
            this.b = c0.e;
            this.c = new ArrayList();
            this.a = ByteString.encodeUtf8(str);
        }

        public a a(y yVar, g0 g0Var) {
            a(b.a(yVar, g0Var));
            return this;
        }

        public a a(b bVar) {
            if (bVar != null) {
                this.c.add(bVar);
                return this;
            }
            throw new NullPointerException("part == null");
        }

        public c0 a() {
            if (!this.c.isEmpty()) {
                return new c0(this.a, this.b, this.c);
            }
            throw new IllegalStateException("Multipart body must have at least one part.");
        }
    }

    /* compiled from: MultipartBody */
    public static final class b {
        final y a;
        final g0 b;

        private b(y yVar, g0 g0Var) {
            this.a = yVar;
            this.b = g0Var;
        }

        public static b a(y yVar, g0 g0Var) {
            if (g0Var == null) {
                throw new NullPointerException("body == null");
            } else if (yVar != null && yVar.a("Content-Type") != null) {
                throw new IllegalArgumentException("Unexpected header: Content-Type");
            } else if (yVar == null || yVar.a("Content-Length") == null) {
                return new b(yVar, g0Var);
            } else {
                throw new IllegalArgumentException("Unexpected header: Content-Length");
            }
        }
    }

    static {
        b0.a("multipart/alternative");
        b0.a("multipart/digest");
        b0.a("multipart/parallel");
    }

    c0(ByteString byteString, b0 b0Var, List<b> list) {
        this.a = byteString;
        this.b = b0.a(b0Var + "; boundary=" + byteString.utf8());
        this.c = e.a(list);
    }

    public long a() {
        long j2 = this.d;
        if (j2 != -1) {
            return j2;
        }
        long a2 = a((d) null, true);
        this.d = a2;
        return a2;
    }

    public b0 b() {
        return this.b;
    }

    public void a(d dVar) {
        a(dVar, false);
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r13v1, resolved type: okio.d} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v0, resolved type: okio.c} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v1, resolved type: okio.c} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r13v3, resolved type: okio.d} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v2, resolved type: okio.c} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private long a(okio.d r13, boolean r14) {
        /*
            r12 = this;
            if (r14 == 0) goto L_0x0009
            okio.c r13 = new okio.c
            r13.<init>()
            r0 = r13
            goto L_0x000a
        L_0x0009:
            r0 = 0
        L_0x000a:
            java.util.List<okhttp3.c0$b> r1 = r12.c
            int r1 = r1.size()
            r2 = 0
            r3 = 0
            r5 = 0
        L_0x0014:
            if (r5 >= r1) goto L_0x00a6
            java.util.List<okhttp3.c0$b> r6 = r12.c
            java.lang.Object r6 = r6.get(r5)
            okhttp3.c0$b r6 = (okhttp3.c0.b) r6
            okhttp3.y r7 = r6.a
            okhttp3.g0 r6 = r6.b
            byte[] r8 = f1856i
            r13.write(r8)
            okio.ByteString r8 = r12.a
            r13.a((okio.ByteString) r8)
            byte[] r8 = f1855h
            r13.write(r8)
            if (r7 == 0) goto L_0x0058
            int r8 = r7.b()
            r9 = 0
        L_0x0038:
            if (r9 >= r8) goto L_0x0058
            java.lang.String r10 = r7.a((int) r9)
            okio.d r10 = r13.a((java.lang.String) r10)
            byte[] r11 = f1854g
            okio.d r10 = r10.write(r11)
            java.lang.String r11 = r7.b((int) r9)
            okio.d r10 = r10.a((java.lang.String) r11)
            byte[] r11 = f1855h
            r10.write(r11)
            int r9 = r9 + 1
            goto L_0x0038
        L_0x0058:
            okhttp3.b0 r7 = r6.b()
            if (r7 == 0) goto L_0x0071
            java.lang.String r8 = "Content-Type: "
            okio.d r8 = r13.a((java.lang.String) r8)
            java.lang.String r7 = r7.toString()
            okio.d r7 = r8.a((java.lang.String) r7)
            byte[] r8 = f1855h
            r7.write(r8)
        L_0x0071:
            long r7 = r6.a()
            r9 = -1
            int r11 = (r7 > r9 ? 1 : (r7 == r9 ? 0 : -1))
            if (r11 == 0) goto L_0x008b
            java.lang.String r9 = "Content-Length: "
            okio.d r9 = r13.a((java.lang.String) r9)
            okio.d r9 = r9.f(r7)
            byte[] r10 = f1855h
            r9.write(r10)
            goto L_0x0091
        L_0x008b:
            if (r14 == 0) goto L_0x0091
            r0.j()
            return r9
        L_0x0091:
            byte[] r9 = f1855h
            r13.write(r9)
            if (r14 == 0) goto L_0x009a
            long r3 = r3 + r7
            goto L_0x009d
        L_0x009a:
            r6.a(r13)
        L_0x009d:
            byte[] r6 = f1855h
            r13.write(r6)
            int r5 = r5 + 1
            goto L_0x0014
        L_0x00a6:
            byte[] r1 = f1856i
            r13.write(r1)
            okio.ByteString r1 = r12.a
            r13.a((okio.ByteString) r1)
            byte[] r1 = f1856i
            r13.write(r1)
            byte[] r1 = f1855h
            r13.write(r1)
            if (r14 == 0) goto L_0x00c4
            long r13 = r0.r()
            long r3 = r3 + r13
            r0.j()
        L_0x00c4:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: okhttp3.c0.a(okio.d, boolean):long");
    }
}
