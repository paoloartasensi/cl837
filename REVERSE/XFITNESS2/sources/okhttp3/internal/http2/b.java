package okhttp3.internal.http2;

import com.jeremyliao.liveeventbus.BuildConfig;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import okio.ByteString;
import okio.c;
import okio.e;
import okio.k;
import okio.r;

/* compiled from: Hpack */
final class b {
    static final a[] a = {new a(a.f1938i, (String) BuildConfig.FLAVOR), new a(a.f1935f, "GET"), new a(a.f1935f, "POST"), new a(a.f1936g, "/"), new a(a.f1936g, "/index.html"), new a(a.f1937h, "http"), new a(a.f1937h, "https"), new a(a.e, "200"), new a(a.e, "204"), new a(a.e, "206"), new a(a.e, "304"), new a(a.e, "400"), new a(a.e, "404"), new a(a.e, "500"), new a("accept-charset", (String) BuildConfig.FLAVOR), new a("accept-encoding", "gzip, deflate"), new a("accept-language", (String) BuildConfig.FLAVOR), new a("accept-ranges", (String) BuildConfig.FLAVOR), new a("accept", (String) BuildConfig.FLAVOR), new a("access-control-allow-origin", (String) BuildConfig.FLAVOR), new a("age", (String) BuildConfig.FLAVOR), new a("allow", (String) BuildConfig.FLAVOR), new a("authorization", (String) BuildConfig.FLAVOR), new a("cache-control", (String) BuildConfig.FLAVOR), new a("content-disposition", (String) BuildConfig.FLAVOR), new a("content-encoding", (String) BuildConfig.FLAVOR), new a("content-language", (String) BuildConfig.FLAVOR), new a("content-length", (String) BuildConfig.FLAVOR), new a("content-location", (String) BuildConfig.FLAVOR), new a("content-range", (String) BuildConfig.FLAVOR), new a("content-type", (String) BuildConfig.FLAVOR), new a("cookie", (String) BuildConfig.FLAVOR), new a("date", (String) BuildConfig.FLAVOR), new a("etag", (String) BuildConfig.FLAVOR), new a("expect", (String) BuildConfig.FLAVOR), new a("expires", (String) BuildConfig.FLAVOR), new a("from", (String) BuildConfig.FLAVOR), new a("host", (String) BuildConfig.FLAVOR), new a("if-match", (String) BuildConfig.FLAVOR), new a("if-modified-since", (String) BuildConfig.FLAVOR), new a("if-none-match", (String) BuildConfig.FLAVOR), new a("if-range", (String) BuildConfig.FLAVOR), new a("if-unmodified-since", (String) BuildConfig.FLAVOR), new a("last-modified", (String) BuildConfig.FLAVOR), new a("link", (String) BuildConfig.FLAVOR), new a("location", (String) BuildConfig.FLAVOR), new a("max-forwards", (String) BuildConfig.FLAVOR), new a("proxy-authenticate", (String) BuildConfig.FLAVOR), new a("proxy-authorization", (String) BuildConfig.FLAVOR), new a("range", (String) BuildConfig.FLAVOR), new a("referer", (String) BuildConfig.FLAVOR), new a("refresh", (String) BuildConfig.FLAVOR), new a("retry-after", (String) BuildConfig.FLAVOR), new a("server", (String) BuildConfig.FLAVOR), new a("set-cookie", (String) BuildConfig.FLAVOR), new a("strict-transport-security", (String) BuildConfig.FLAVOR), new a("transfer-encoding", (String) BuildConfig.FLAVOR), new a("user-agent", (String) BuildConfig.FLAVOR), new a("vary", (String) BuildConfig.FLAVOR), new a("via", (String) BuildConfig.FLAVOR), new a("www-authenticate", (String) BuildConfig.FLAVOR)};
    static final Map<ByteString, Integer> b = a();

    /* compiled from: Hpack */
    static final class a {
        private final List<a> a;
        private final e b;
        private final int c;
        private int d;
        a[] e;

        /* renamed from: f  reason: collision with root package name */
        int f1939f;

        /* renamed from: g  reason: collision with root package name */
        int f1940g;

        /* renamed from: h  reason: collision with root package name */
        int f1941h;

        a(int i2, r rVar) {
            this(i2, i2, rVar);
        }

        private int b(int i2) {
            int i3 = 0;
            if (i2 > 0) {
                int length = this.e.length;
                while (true) {
                    length--;
                    if (length < this.f1939f || i2 <= 0) {
                        a[] aVarArr = this.e;
                        int i4 = this.f1939f;
                        System.arraycopy(aVarArr, i4 + 1, aVarArr, i4 + 1 + i3, this.f1940g);
                        this.f1939f += i3;
                    } else {
                        a[] aVarArr2 = this.e;
                        i2 -= aVarArr2[length].c;
                        this.f1941h -= aVarArr2[length].c;
                        this.f1940g--;
                        i3++;
                    }
                }
                a[] aVarArr3 = this.e;
                int i42 = this.f1939f;
                System.arraycopy(aVarArr3, i42 + 1, aVarArr3, i42 + 1 + i3, this.f1940g);
                this.f1939f += i3;
            }
            return i3;
        }

        private void d() {
            int i2 = this.d;
            int i3 = this.f1941h;
            if (i2 >= i3) {
                return;
            }
            if (i2 == 0) {
                e();
            } else {
                b(i3 - i2);
            }
        }

        private void e() {
            Arrays.fill(this.e, (Object) null);
            this.f1939f = this.e.length - 1;
            this.f1940g = 0;
            this.f1941h = 0;
        }

        private void f(int i2) {
            a(-1, new a(c(i2), b()));
        }

        private void g(int i2) {
            this.a.add(new a(c(i2), b()));
        }

        private void h() {
            ByteString b2 = b();
            b.a(b2);
            this.a.add(new a(b2, b()));
        }

        public List<a> a() {
            ArrayList arrayList = new ArrayList(this.a);
            this.a.clear();
            return arrayList;
        }

        /* access modifiers changed from: package-private */
        public void c() {
            while (!this.b.i()) {
                byte readByte = this.b.readByte() & 255;
                if (readByte == 128) {
                    throw new IOException("index == 0");
                } else if ((readByte & 128) == 128) {
                    e(a((int) readByte, 127) - 1);
                } else if (readByte == 64) {
                    g();
                } else if ((readByte & 64) == 64) {
                    f(a((int) readByte, 63) - 1);
                } else if ((readByte & 32) == 32) {
                    int a2 = a((int) readByte, 31);
                    this.d = a2;
                    if (a2 < 0 || a2 > this.c) {
                        throw new IOException("Invalid dynamic table size update " + this.d);
                    }
                    d();
                } else if (readByte == 16 || readByte == 0) {
                    h();
                } else {
                    g(a((int) readByte, 15) - 1);
                }
            }
        }

        a(int i2, int i3, r rVar) {
            this.a = new ArrayList();
            a[] aVarArr = new a[8];
            this.e = aVarArr;
            this.f1939f = aVarArr.length - 1;
            this.f1940g = 0;
            this.f1941h = 0;
            this.c = i2;
            this.d = i3;
            this.b = k.a(rVar);
        }

        private int a(int i2) {
            return this.f1939f + 1 + i2;
        }

        private void a(int i2, a aVar) {
            this.a.add(aVar);
            int i3 = aVar.c;
            if (i2 != -1) {
                i3 -= this.e[a(i2)].c;
            }
            int i4 = this.d;
            if (i3 > i4) {
                e();
                return;
            }
            int b2 = b((this.f1941h + i3) - i4);
            if (i2 == -1) {
                int i5 = this.f1940g + 1;
                a[] aVarArr = this.e;
                if (i5 > aVarArr.length) {
                    a[] aVarArr2 = new a[(aVarArr.length * 2)];
                    System.arraycopy(aVarArr, 0, aVarArr2, aVarArr.length, aVarArr.length);
                    this.f1939f = this.e.length - 1;
                    this.e = aVarArr2;
                }
                int i6 = this.f1939f;
                this.f1939f = i6 - 1;
                this.e[i6] = aVar;
                this.f1940g++;
            } else {
                this.e[i2 + a(i2) + b2] = aVar;
            }
            this.f1941h += i3;
        }

        private boolean d(int i2) {
            return i2 >= 0 && i2 <= b.a.length - 1;
        }

        private int f() {
            return this.b.readByte() & 255;
        }

        private void g() {
            ByteString b2 = b();
            b.a(b2);
            a(-1, new a(b2, b()));
        }

        private void e(int i2) {
            if (d(i2)) {
                this.a.add(b.a[i2]);
                return;
            }
            int a2 = a(i2 - b.a.length);
            if (a2 >= 0) {
                a[] aVarArr = this.e;
                if (a2 < aVarArr.length) {
                    this.a.add(aVarArr[a2]);
                    return;
                }
            }
            throw new IOException("Header index too large " + (i2 + 1));
        }

        /* access modifiers changed from: package-private */
        public ByteString b() {
            int f2 = f();
            boolean z = (f2 & 128) == 128;
            int a2 = a(f2, 127);
            if (z) {
                return ByteString.of(i.b().a(this.b.g((long) a2)));
            }
            return this.b.b((long) a2);
        }

        private ByteString c(int i2) {
            if (d(i2)) {
                return b.a[i2].a;
            }
            int a2 = a(i2 - b.a.length);
            if (a2 >= 0) {
                a[] aVarArr = this.e;
                if (a2 < aVarArr.length) {
                    return aVarArr[a2].a;
                }
            }
            throw new IOException("Header index too large " + (i2 + 1));
        }

        /* access modifiers changed from: package-private */
        public int a(int i2, int i3) {
            int i4 = i2 & i3;
            if (i4 < i3) {
                return i4;
            }
            int i5 = 0;
            while (true) {
                int f2 = f();
                if ((f2 & 128) == 0) {
                    return i3 + (f2 << i5);
                }
                i3 += (f2 & 127) << i5;
                i5 += 7;
            }
        }
    }

    /* renamed from: okhttp3.internal.http2.b$b  reason: collision with other inner class name */
    /* compiled from: Hpack */
    static final class C0092b {
        private final c a;
        private final boolean b;
        private int c;
        private boolean d;
        int e;

        /* renamed from: f  reason: collision with root package name */
        a[] f1942f;

        /* renamed from: g  reason: collision with root package name */
        int f1943g;

        /* renamed from: h  reason: collision with root package name */
        int f1944h;

        /* renamed from: i  reason: collision with root package name */
        int f1945i;

        C0092b(c cVar) {
            this(4096, true, cVar);
        }

        private void a(a aVar) {
            int i2 = aVar.c;
            int i3 = this.e;
            if (i2 > i3) {
                b();
                return;
            }
            b((this.f1945i + i2) - i3);
            int i4 = this.f1944h + 1;
            a[] aVarArr = this.f1942f;
            if (i4 > aVarArr.length) {
                a[] aVarArr2 = new a[(aVarArr.length * 2)];
                System.arraycopy(aVarArr, 0, aVarArr2, aVarArr.length, aVarArr.length);
                this.f1943g = this.f1942f.length - 1;
                this.f1942f = aVarArr2;
            }
            int i5 = this.f1943g;
            this.f1943g = i5 - 1;
            this.f1942f[i5] = aVar;
            this.f1944h++;
            this.f1945i += i2;
        }

        private void b() {
            Arrays.fill(this.f1942f, (Object) null);
            this.f1943g = this.f1942f.length - 1;
            this.f1944h = 0;
            this.f1945i = 0;
        }

        C0092b(int i2, boolean z, c cVar) {
            this.c = Integer.MAX_VALUE;
            a[] aVarArr = new a[8];
            this.f1942f = aVarArr;
            this.f1943g = aVarArr.length - 1;
            this.f1944h = 0;
            this.f1945i = 0;
            this.e = i2;
            this.b = z;
            this.a = cVar;
        }

        private int b(int i2) {
            int i3 = 0;
            if (i2 > 0) {
                int length = this.f1942f.length;
                while (true) {
                    length--;
                    if (length < this.f1943g || i2 <= 0) {
                        a[] aVarArr = this.f1942f;
                        int i4 = this.f1943g;
                        System.arraycopy(aVarArr, i4 + 1, aVarArr, i4 + 1 + i3, this.f1944h);
                        a[] aVarArr2 = this.f1942f;
                        int i5 = this.f1943g;
                        Arrays.fill(aVarArr2, i5 + 1, i5 + 1 + i3, (Object) null);
                        this.f1943g += i3;
                    } else {
                        a[] aVarArr3 = this.f1942f;
                        i2 -= aVarArr3[length].c;
                        this.f1945i -= aVarArr3[length].c;
                        this.f1944h--;
                        i3++;
                    }
                }
                a[] aVarArr4 = this.f1942f;
                int i42 = this.f1943g;
                System.arraycopy(aVarArr4, i42 + 1, aVarArr4, i42 + 1 + i3, this.f1944h);
                a[] aVarArr22 = this.f1942f;
                int i52 = this.f1943g;
                Arrays.fill(aVarArr22, i52 + 1, i52 + 1 + i3, (Object) null);
                this.f1943g += i3;
            }
            return i3;
        }

        /* access modifiers changed from: package-private */
        public void a(List<a> list) {
            int i2;
            int i3;
            if (this.d) {
                int i4 = this.c;
                if (i4 < this.e) {
                    a(i4, 31, 32);
                }
                this.d = false;
                this.c = Integer.MAX_VALUE;
                a(this.e, 31, 32);
            }
            int size = list.size();
            for (int i5 = 0; i5 < size; i5++) {
                a aVar = list.get(i5);
                ByteString asciiLowercase = aVar.a.toAsciiLowercase();
                ByteString byteString = aVar.b;
                Integer num = b.b.get(asciiLowercase);
                if (num != null) {
                    i3 = num.intValue() + 1;
                    if (i3 > 1 && i3 < 8) {
                        if (c.a(b.a[i3 - 1].b, byteString)) {
                            i2 = i3;
                        } else if (c.a(b.a[i3].b, byteString)) {
                            i2 = i3;
                            i3++;
                        }
                    }
                    i2 = i3;
                    i3 = -1;
                } else {
                    i3 = -1;
                    i2 = -1;
                }
                if (i3 == -1) {
                    int i6 = this.f1943g + 1;
                    int length = this.f1942f.length;
                    while (true) {
                        if (i6 >= length) {
                            break;
                        }
                        if (c.a(this.f1942f[i6].a, asciiLowercase)) {
                            if (c.a(this.f1942f[i6].b, byteString)) {
                                i3 = b.a.length + (i6 - this.f1943g);
                                break;
                            } else if (i2 == -1) {
                                i2 = (i6 - this.f1943g) + b.a.length;
                            }
                        }
                        i6++;
                    }
                }
                if (i3 != -1) {
                    a(i3, 127, 128);
                } else if (i2 == -1) {
                    this.a.writeByte(64);
                    a(asciiLowercase);
                    a(byteString);
                    a(aVar);
                } else if (!asciiLowercase.startsWith(a.d) || a.f1938i.equals(asciiLowercase)) {
                    a(i2, 63, 64);
                    a(byteString);
                    a(aVar);
                } else {
                    a(i2, 15, 0);
                    a(byteString);
                }
            }
        }

        /* access modifiers changed from: package-private */
        public void a(int i2, int i3, int i4) {
            if (i2 < i3) {
                this.a.writeByte(i2 | i4);
                return;
            }
            this.a.writeByte(i4 | i3);
            int i5 = i2 - i3;
            while (i5 >= 128) {
                this.a.writeByte(128 | (i5 & 127));
                i5 >>>= 7;
            }
            this.a.writeByte(i5);
        }

        /* access modifiers changed from: package-private */
        public void a(ByteString byteString) {
            if (!this.b || i.b().a(byteString) >= byteString.size()) {
                a(byteString.size(), 127, 0);
                this.a.a(byteString);
                return;
            }
            c cVar = new c();
            i.b().a(byteString, cVar);
            ByteString p = cVar.p();
            a(p.size(), 127, 128);
            this.a.a(p);
        }

        /* access modifiers changed from: package-private */
        public void a(int i2) {
            int min = Math.min(i2, 16384);
            int i3 = this.e;
            if (i3 != min) {
                if (min < i3) {
                    this.c = Math.min(this.c, min);
                }
                this.d = true;
                this.e = min;
                a();
            }
        }

        private void a() {
            int i2 = this.e;
            int i3 = this.f1945i;
            if (i2 >= i3) {
                return;
            }
            if (i2 == 0) {
                b();
            } else {
                b(i3 - i2);
            }
        }
    }

    private static Map<ByteString, Integer> a() {
        LinkedHashMap linkedHashMap = new LinkedHashMap(a.length);
        int i2 = 0;
        while (true) {
            a[] aVarArr = a;
            if (i2 >= aVarArr.length) {
                return Collections.unmodifiableMap(linkedHashMap);
            }
            if (!linkedHashMap.containsKey(aVarArr[i2].a)) {
                linkedHashMap.put(a[i2].a, Integer.valueOf(i2));
            }
            i2++;
        }
    }

    static ByteString a(ByteString byteString) {
        int size = byteString.size();
        int i2 = 0;
        while (i2 < size) {
            byte b2 = byteString.getByte(i2);
            if (b2 < 65 || b2 > 90) {
                i2++;
            } else {
                throw new IOException("PROTOCOL_ERROR response malformed: mixed case name: " + byteString.utf8());
            }
        }
        return byteString;
    }
}
