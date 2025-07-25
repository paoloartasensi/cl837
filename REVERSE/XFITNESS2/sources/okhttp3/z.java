package okhttp3;

import com.jeremyliao.liveeventbus.BuildConfig;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import okhttp3.k0.e;
import okio.c;

/* compiled from: HttpUrl */
public final class z {

    /* renamed from: j  reason: collision with root package name */
    private static final char[] f2078j = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
    final String a;
    private final String b;
    private final String c;
    final String d;
    final int e;

    /* renamed from: f  reason: collision with root package name */
    private final List<String> f2079f;

    /* renamed from: g  reason: collision with root package name */
    private final List<String> f2080g;

    /* renamed from: h  reason: collision with root package name */
    private final String f2081h;

    /* renamed from: i  reason: collision with root package name */
    private final String f2082i;

    /* compiled from: HttpUrl */
    public static final class a {
        String a;
        String b = BuildConfig.FLAVOR;
        String c = BuildConfig.FLAVOR;
        String d;
        int e = -1;

        /* renamed from: f  reason: collision with root package name */
        final List<String> f2083f;

        /* renamed from: g  reason: collision with root package name */
        List<String> f2084g;

        /* renamed from: h  reason: collision with root package name */
        String f2085h;

        public a() {
            ArrayList arrayList = new ArrayList();
            this.f2083f = arrayList;
            arrayList.add(BuildConfig.FLAVOR);
        }

        private boolean f(String str) {
            return str.equals(".") || str.equalsIgnoreCase("%2e");
        }

        private boolean g(String str) {
            return str.equals("..") || str.equalsIgnoreCase("%2e.") || str.equalsIgnoreCase(".%2e") || str.equalsIgnoreCase("%2e%2e");
        }

        public a a(int i2) {
            if (i2 <= 0 || i2 > 65535) {
                throw new IllegalArgumentException("unexpected port: " + i2);
            }
            this.e = i2;
            return this;
        }

        public a b(String str) {
            if (str != null) {
                String a2 = a(str, 0, str.length());
                if (a2 != null) {
                    this.d = a2;
                    return this;
                }
                throw new IllegalArgumentException("unexpected host: " + str);
            }
            throw new NullPointerException("host == null");
        }

        public a c(String str) {
            if (str != null) {
                this.c = z.a(str, " \"':;<=>@[]^`{}|/\\?#", false, false, false, true);
                return this;
            }
            throw new NullPointerException("password == null");
        }

        public a d(String str) {
            if (str != null) {
                if (str.equalsIgnoreCase("http")) {
                    this.a = "http";
                } else if (str.equalsIgnoreCase("https")) {
                    this.a = "https";
                } else {
                    throw new IllegalArgumentException("unexpected scheme: " + str);
                }
                return this;
            }
            throw new NullPointerException("scheme == null");
        }

        public a e(String str) {
            if (str != null) {
                this.b = z.a(str, " \"':;<=>@[]^`{}|/\\?#", false, false, false, true);
                return this;
            }
            throw new NullPointerException("username == null");
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            String str = this.a;
            if (str != null) {
                sb.append(str);
                sb.append("://");
            } else {
                sb.append("//");
            }
            if (!this.b.isEmpty() || !this.c.isEmpty()) {
                sb.append(this.b);
                if (!this.c.isEmpty()) {
                    sb.append(':');
                    sb.append(this.c);
                }
                sb.append('@');
            }
            String str2 = this.d;
            if (str2 != null) {
                if (str2.indexOf(58) != -1) {
                    sb.append('[');
                    sb.append(this.d);
                    sb.append(']');
                } else {
                    sb.append(this.d);
                }
            }
            if (!(this.e == -1 && this.a == null)) {
                int b2 = b();
                String str3 = this.a;
                if (str3 == null || b2 != z.c(str3)) {
                    sb.append(':');
                    sb.append(b2);
                }
            }
            z.b(sb, this.f2083f);
            if (this.f2084g != null) {
                sb.append('?');
                z.a(sb, this.f2084g);
            }
            if (this.f2085h != null) {
                sb.append('#');
                sb.append(this.f2085h);
            }
            return sb.toString();
        }

        private static int f(String str, int i2, int i3) {
            int i4 = 0;
            while (i2 < i3) {
                char charAt = str.charAt(i2);
                if (charAt != '\\' && charAt != '/') {
                    break;
                }
                i4++;
                i2++;
            }
            return i4;
        }

        private static int e(String str, int i2, int i3) {
            if (i3 - i2 < 2) {
                return -1;
            }
            char charAt = str.charAt(i2);
            if ((charAt >= 'a' && charAt <= 'z') || (charAt >= 'A' && charAt <= 'Z')) {
                while (true) {
                    i2++;
                    if (i2 >= i3) {
                        break;
                    }
                    char charAt2 = str.charAt(i2);
                    if ((charAt2 < 'a' || charAt2 > 'z') && ((charAt2 < 'A' || charAt2 > 'Z') && !((charAt2 >= '0' && charAt2 <= '9') || charAt2 == '+' || charAt2 == '-' || charAt2 == '.'))) {
                        if (charAt2 == ':') {
                            return i2;
                        }
                    }
                }
            }
            return -1;
        }

        public a a(String str) {
            this.f2084g = str != null ? z.e(z.a(str, " \"'<>#", true, false, true, true)) : null;
            return this;
        }

        /* access modifiers changed from: package-private */
        public a c() {
            int size = this.f2083f.size();
            for (int i2 = 0; i2 < size; i2++) {
                this.f2083f.set(i2, z.a(this.f2083f.get(i2), "[]", true, true, false, true));
            }
            List<String> list = this.f2084g;
            if (list != null) {
                int size2 = list.size();
                for (int i3 = 0; i3 < size2; i3++) {
                    String str = this.f2084g.get(i3);
                    if (str != null) {
                        this.f2084g.set(i3, z.a(str, "\\^`{|}", true, true, true, true));
                    }
                }
            }
            String str2 = this.f2085h;
            if (str2 != null) {
                this.f2085h = z.a(str2, " \"#<>\\^`{|}", true, true, false, false);
            }
            return this;
        }

        /* access modifiers changed from: package-private */
        public int b() {
            int i2 = this.e;
            return i2 != -1 ? i2 : z.c(this.a);
        }

        public a a(String str, String str2) {
            if (str != null) {
                if (this.f2084g == null) {
                    this.f2084g = new ArrayList();
                }
                this.f2084g.add(z.a(str, " \"'<>#&=", true, false, true, true));
                this.f2084g.add(str2 != null ? z.a(str2, " \"'<>#&=", true, false, true, true) : null);
                return this;
            }
            throw new NullPointerException("encodedName == null");
        }

        public a b(String str, String str2) {
            if (str != null) {
                if (this.f2084g == null) {
                    this.f2084g = new ArrayList();
                }
                this.f2084g.add(z.a(str, " !\"#$&'(),/:;<=>?@[]\\^`{|}~", false, false, true, true));
                this.f2084g.add(str2 != null ? z.a(str2, " !\"#$&'(),/:;<=>?@[]\\^`{|}~", false, false, true, true) : null);
                return this;
            }
            throw new NullPointerException("name == null");
        }

        /*  JADX ERROR: JadxOverflowException in pass: RegionMakerVisitor
            jadx.core.utils.exceptions.JadxOverflowException: Regions count limit reached
            	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:47)
            	at jadx.core.utils.ErrorsCounter.methodError(ErrorsCounter.java:81)
            */
        /* JADX WARNING: Removed duplicated region for block: B:10:0x002c  */
        /* JADX WARNING: Removed duplicated region for block: B:18:0x0044 A[SYNTHETIC] */
        private void d(java.lang.String r11, int r12, int r13) {
            /*
                r10 = this;
                if (r12 != r13) goto L_0x0003
                return
            L_0x0003:
                char r0 = r11.charAt(r12)
                r1 = 47
                java.lang.String r2 = ""
                r3 = 1
                if (r0 == r1) goto L_0x001e
                r1 = 92
                if (r0 != r1) goto L_0x0013
                goto L_0x001e
            L_0x0013:
                java.util.List<java.lang.String> r0 = r10.f2083f
                int r1 = r0.size()
                int r1 = r1 - r3
                r0.set(r1, r2)
                goto L_0x0029
            L_0x001e:
                java.util.List<java.lang.String> r0 = r10.f2083f
                r0.clear()
                java.util.List<java.lang.String> r0 = r10.f2083f
                r0.add(r2)
                goto L_0x0041
            L_0x0029:
                r6 = r12
                if (r6 >= r13) goto L_0x0044
                java.lang.String r12 = "/\\"
                int r12 = okhttp3.k0.e.a((java.lang.String) r11, (int) r6, (int) r13, (java.lang.String) r12)
                if (r12 >= r13) goto L_0x0036
                r0 = 1
                goto L_0x0037
            L_0x0036:
                r0 = 0
            L_0x0037:
                r9 = 1
                r4 = r10
                r5 = r11
                r7 = r12
                r8 = r0
                r4.a(r5, r6, r7, r8, r9)
                if (r0 == 0) goto L_0x0029
            L_0x0041:
                int r12 = r12 + 1
                goto L_0x0029
            L_0x0044:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: okhttp3.z.a.d(java.lang.String, int, int):void");
        }

        private void d() {
            List<String> list = this.f2083f;
            if (!list.remove(list.size() - 1).isEmpty() || this.f2083f.isEmpty()) {
                this.f2083f.add(BuildConfig.FLAVOR);
                return;
            }
            List<String> list2 = this.f2083f;
            list2.set(list2.size() - 1, BuildConfig.FLAVOR);
        }

        private static int b(String str, int i2, int i3) {
            try {
                int parseInt = Integer.parseInt(z.a(str, i2, i3, BuildConfig.FLAVOR, false, false, false, true, (Charset) null));
                if (parseInt <= 0 || parseInt > 65535) {
                    return -1;
                }
                return parseInt;
            } catch (NumberFormatException unused) {
            }
        }

        public z a() {
            if (this.a == null) {
                throw new IllegalStateException("scheme == null");
            } else if (this.d != null) {
                return new z(this);
            } else {
                throw new IllegalStateException("host == null");
            }
        }

        private static int c(String str, int i2, int i3) {
            while (i2 < i3) {
                char charAt = str.charAt(i2);
                if (charAt == ':') {
                    return i2;
                }
                if (charAt == '[') {
                    do {
                        i2++;
                        if (i2 >= i3) {
                            break;
                        }
                    } while (str.charAt(i2) == ']');
                }
                i2++;
            }
            return i3;
        }

        /* access modifiers changed from: package-private */
        public a a(z zVar, String str) {
            int a2;
            int i2;
            z zVar2 = zVar;
            String str2 = str;
            int b2 = e.b(str2, 0, str.length());
            int c2 = e.c(str2, b2, str.length());
            int e2 = e(str2, b2, c2);
            if (e2 != -1) {
                if (str.regionMatches(true, b2, "https:", 0, 6)) {
                    this.a = "https";
                    b2 += 6;
                } else if (str.regionMatches(true, b2, "http:", 0, 5)) {
                    this.a = "http";
                    b2 += 5;
                } else {
                    throw new IllegalArgumentException("Expected URL scheme 'http' or 'https' but was '" + str2.substring(0, e2) + "'");
                }
            } else if (zVar2 != null) {
                this.a = zVar2.a;
            } else {
                throw new IllegalArgumentException("Expected URL scheme 'http' or 'https' but no colon was found");
            }
            int f2 = f(str2, b2, c2);
            char c3 = '?';
            char c4 = '#';
            if (f2 >= 2 || zVar2 == null || !zVar2.a.equals(this.a)) {
                int i3 = b2 + f2;
                boolean z = false;
                boolean z2 = false;
                while (true) {
                    a2 = e.a(str2, i3, c2, "@/\\?#");
                    char charAt = a2 != c2 ? str2.charAt(a2) : 65535;
                    if (charAt == 65535 || charAt == c4 || charAt == '/' || charAt == '\\' || charAt == c3) {
                        int i4 = a2;
                        int c5 = c(str2, i3, i4);
                        int i5 = c5 + 1;
                    } else {
                        if (charAt == '@') {
                            if (!z) {
                                int a3 = e.a(str2, i3, a2, ':');
                                int i6 = a3;
                                String str3 = "%40";
                                i2 = a2;
                                String a4 = z.a(str, i3, a3, " \"':;<=>@[]^`{}|/\\?#", true, false, false, true, (Charset) null);
                                if (z2) {
                                    a4 = this.b + str3 + a4;
                                }
                                this.b = a4;
                                if (i6 != i2) {
                                    this.c = z.a(str, i6 + 1, i2, " \"':;<=>@[]^`{}|/\\?#", true, false, false, true, (Charset) null);
                                    z = true;
                                }
                                z2 = true;
                            } else {
                                i2 = a2;
                                this.c += "%40" + z.a(str, i3, i2, " \"':;<=>@[]^`{}|/\\?#", true, false, false, true, (Charset) null);
                            }
                            i3 = i2 + 1;
                        }
                        c3 = '?';
                        c4 = '#';
                    }
                }
                int i42 = a2;
                int c52 = c(str2, i3, i42);
                int i52 = c52 + 1;
                if (i52 < i42) {
                    this.d = a(str2, i3, c52);
                    int b3 = b(str2, i52, i42);
                    this.e = b3;
                    if (b3 == -1) {
                        throw new IllegalArgumentException("Invalid URL port: \"" + str2.substring(i52, i42) + '\"');
                    }
                } else {
                    this.d = a(str2, i3, c52);
                    this.e = z.c(this.a);
                }
                if (this.d != null) {
                    b2 = i42;
                } else {
                    throw new IllegalArgumentException("Invalid URL host: \"" + str2.substring(i3, c52) + '\"');
                }
            } else {
                this.b = zVar.f();
                this.c = zVar.b();
                this.d = zVar2.d;
                this.e = zVar2.e;
                this.f2083f.clear();
                this.f2083f.addAll(zVar.d());
                if (b2 == c2 || str2.charAt(b2) == '#') {
                    a(zVar.e());
                }
            }
            int a5 = e.a(str2, b2, c2, "?#");
            d(str2, b2, a5);
            if (a5 < c2 && str2.charAt(a5) == '?') {
                int a6 = e.a(str2, a5, c2, '#');
                this.f2084g = z.e(z.a(str, a5 + 1, a6, " \"'<>#", true, false, true, true, (Charset) null));
                a5 = a6;
            }
            if (a5 < c2 && str2.charAt(a5) == '#') {
                this.f2085h = z.a(str, 1 + a5, c2, BuildConfig.FLAVOR, true, false, false, false, (Charset) null);
            }
            return this;
        }

        private void a(String str, int i2, int i3, boolean z, boolean z2) {
            String a2 = z.a(str, i2, i3, " \"<>^`{}|/\\?#", z2, false, false, true, (Charset) null);
            if (!f(a2)) {
                if (g(a2)) {
                    d();
                    return;
                }
                List<String> list = this.f2083f;
                if (list.get(list.size() - 1).isEmpty()) {
                    List<String> list2 = this.f2083f;
                    list2.set(list2.size() - 1, a2);
                } else {
                    this.f2083f.add(a2);
                }
                if (z) {
                    this.f2083f.add(BuildConfig.FLAVOR);
                }
            }
        }

        private static String a(String str, int i2, int i3) {
            return e.a(z.a(str, i2, i3, false));
        }
    }

    z(a aVar) {
        this.a = aVar.a;
        this.b = a(aVar.b, false);
        this.c = a(aVar.c, false);
        this.d = aVar.d;
        this.e = aVar.b();
        this.f2079f = a(aVar.f2083f, false);
        List<String> list = aVar.f2084g;
        String str = null;
        this.f2080g = list != null ? a(list, true) : null;
        String str2 = aVar.f2085h;
        this.f2081h = str2 != null ? a(str2, false) : str;
        this.f2082i = aVar.toString();
    }

    static void a(StringBuilder sb, List<String> list) {
        int size = list.size();
        for (int i2 = 0; i2 < size; i2 += 2) {
            String str = list.get(i2);
            String str2 = list.get(i2 + 1);
            if (i2 > 0) {
                sb.append('&');
            }
            sb.append(str);
            if (str2 != null) {
                sb.append('=');
                sb.append(str2);
            }
        }
    }

    public static int c(String str) {
        if (str.equals("http")) {
            return 80;
        }
        return str.equals("https") ? 443 : -1;
    }

    public String b() {
        if (this.c.isEmpty()) {
            return BuildConfig.FLAVOR;
        }
        int indexOf = this.f2082i.indexOf(64);
        return this.f2082i.substring(this.f2082i.indexOf(58, this.a.length() + 3) + 1, indexOf);
    }

    public List<String> d() {
        int indexOf = this.f2082i.indexOf(47, this.a.length() + 3);
        String str = this.f2082i;
        int a2 = e.a(str, indexOf, str.length(), "?#");
        ArrayList arrayList = new ArrayList();
        while (indexOf < a2) {
            int i2 = indexOf + 1;
            int a3 = e.a(this.f2082i, i2, a2, '/');
            arrayList.add(this.f2082i.substring(i2, a3));
            indexOf = a3;
        }
        return arrayList;
    }

    public String e() {
        if (this.f2080g == null) {
            return null;
        }
        int indexOf = this.f2082i.indexOf(63) + 1;
        String str = this.f2082i;
        return this.f2082i.substring(indexOf, e.a(str, indexOf, str.length(), '#'));
    }

    public boolean equals(Object obj) {
        return (obj instanceof z) && ((z) obj).f2082i.equals(this.f2082i);
    }

    public String f() {
        if (this.b.isEmpty()) {
            return BuildConfig.FLAVOR;
        }
        int length = this.a.length() + 3;
        String str = this.f2082i;
        return this.f2082i.substring(length, e.a(str, length, str.length(), ":@"));
    }

    public String g() {
        return this.d;
    }

    public boolean h() {
        return this.a.equals("https");
    }

    public int hashCode() {
        return this.f2082i.hashCode();
    }

    public a i() {
        a aVar = new a();
        aVar.a = this.a;
        aVar.b = f();
        aVar.c = b();
        aVar.d = this.d;
        aVar.e = this.e != c(this.a) ? this.e : -1;
        aVar.f2083f.clear();
        aVar.f2083f.addAll(d());
        aVar.a(e());
        aVar.f2085h = a();
        return aVar;
    }

    public List<String> j() {
        return this.f2079f;
    }

    public int k() {
        return this.e;
    }

    public String l() {
        if (this.f2080g == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        a(sb, this.f2080g);
        return sb.toString();
    }

    public String m() {
        a a2 = a("/...");
        a2.e(BuildConfig.FLAVOR);
        a2.c(BuildConfig.FLAVOR);
        return a2.a().toString();
    }

    public String n() {
        return this.a;
    }

    public URI o() {
        a i2 = i();
        i2.c();
        String aVar = i2.toString();
        try {
            return new URI(aVar);
        } catch (URISyntaxException e2) {
            try {
                return URI.create(aVar.replaceAll("[\\u0000-\\u001F\\u007F-\\u009F\\p{javaWhitespace}]", BuildConfig.FLAVOR));
            } catch (Exception unused) {
                throw new RuntimeException(e2);
            }
        }
    }

    public String toString() {
        return this.f2082i;
    }

    public String c() {
        int indexOf = this.f2082i.indexOf(47, this.a.length() + 3);
        String str = this.f2082i;
        return this.f2082i.substring(indexOf, e.a(str, indexOf, str.length(), "?#"));
    }

    static void b(StringBuilder sb, List<String> list) {
        int size = list.size();
        for (int i2 = 0; i2 < size; i2++) {
            sb.append('/');
            sb.append(list.get(i2));
        }
    }

    static List<String> e(String str) {
        ArrayList arrayList = new ArrayList();
        int i2 = 0;
        while (i2 <= str.length()) {
            int indexOf = str.indexOf(38, i2);
            if (indexOf == -1) {
                indexOf = str.length();
            }
            int indexOf2 = str.indexOf(61, i2);
            if (indexOf2 == -1 || indexOf2 > indexOf) {
                arrayList.add(str.substring(i2, indexOf));
                arrayList.add((Object) null);
            } else {
                arrayList.add(str.substring(i2, indexOf2));
                arrayList.add(str.substring(indexOf2 + 1, indexOf));
            }
            i2 = indexOf + 1;
        }
        return arrayList;
    }

    public static z d(String str) {
        a aVar = new a();
        aVar.a((z) null, str);
        return aVar.a();
    }

    public String a() {
        if (this.f2081h == null) {
            return null;
        }
        return this.f2082i.substring(this.f2082i.indexOf(35) + 1);
    }

    public z b(String str) {
        a a2 = a(str);
        if (a2 != null) {
            return a2.a();
        }
        return null;
    }

    public a a(String str) {
        try {
            a aVar = new a();
            aVar.a(this, str);
            return aVar;
        } catch (IllegalArgumentException unused) {
            return null;
        }
    }

    static String a(String str, boolean z) {
        return a(str, 0, str.length(), z);
    }

    private List<String> a(List<String> list, boolean z) {
        int size = list.size();
        ArrayList arrayList = new ArrayList(size);
        for (int i2 = 0; i2 < size; i2++) {
            String str = list.get(i2);
            arrayList.add(str != null ? a(str, z) : null);
        }
        return Collections.unmodifiableList(arrayList);
    }

    static String a(String str, int i2, int i3, boolean z) {
        for (int i4 = i2; i4 < i3; i4++) {
            char charAt = str.charAt(i4);
            if (charAt == '%' || (charAt == '+' && z)) {
                c cVar = new c();
                cVar.a(str, i2, i4);
                a(cVar, str, i4, i3, z);
                return cVar.q();
            }
        }
        return str.substring(i2, i3);
    }

    static void a(c cVar, String str, int i2, int i3, boolean z) {
        int i4;
        while (i2 < i3) {
            int codePointAt = str.codePointAt(i2);
            if (codePointAt == 37 && (i4 = i2 + 2) < i3) {
                int a2 = e.a(str.charAt(i2 + 1));
                int a3 = e.a(str.charAt(i4));
                if (!(a2 == -1 || a3 == -1)) {
                    cVar.writeByte((a2 << 4) + a3);
                    i2 = i4;
                    i2 += Character.charCount(codePointAt);
                }
            } else if (codePointAt == 43 && z) {
                cVar.writeByte(32);
                i2 += Character.charCount(codePointAt);
            }
            cVar.c(codePointAt);
            i2 += Character.charCount(codePointAt);
        }
    }

    static boolean a(String str, int i2, int i3) {
        int i4 = i2 + 2;
        if (i4 >= i3 || str.charAt(i2) != '%' || e.a(str.charAt(i2 + 1)) == -1 || e.a(str.charAt(i4)) == -1) {
            return false;
        }
        return true;
    }

    static String a(String str, int i2, int i3, String str2, boolean z, boolean z2, boolean z3, boolean z4, Charset charset) {
        String str3 = str;
        int i4 = i3;
        int i5 = i2;
        while (i5 < i4) {
            int codePointAt = str.codePointAt(i5);
            if (codePointAt < 32 || codePointAt == 127 || (codePointAt >= 128 && z4)) {
                String str4 = str2;
            } else {
                String str5 = str2;
                if (str2.indexOf(codePointAt) == -1 && ((codePointAt != 37 || (z && (!z2 || a(str, i5, i3)))) && (codePointAt != 43 || !z3))) {
                    i5 += Character.charCount(codePointAt);
                }
            }
            c cVar = new c();
            int i6 = i2;
            cVar.a(str, i2, i5);
            a(cVar, str, i5, i3, str2, z, z2, z3, z4, charset);
            return cVar.q();
        }
        int i7 = i2;
        return str.substring(i2, i3);
    }

    static void a(c cVar, String str, int i2, int i3, String str2, boolean z, boolean z2, boolean z3, boolean z4, Charset charset) {
        c cVar2 = null;
        while (i2 < i3) {
            int codePointAt = str.codePointAt(i2);
            if (!z || !(codePointAt == 9 || codePointAt == 10 || codePointAt == 12 || codePointAt == 13)) {
                if (codePointAt == 43 && z3) {
                    cVar.a(z ? "+" : "%2B");
                } else if (codePointAt < 32 || codePointAt == 127 || ((codePointAt >= 128 && z4) || str2.indexOf(codePointAt) != -1 || (codePointAt == 37 && (!z || (z2 && !a(str, i2, i3)))))) {
                    if (cVar2 == null) {
                        cVar2 = new c();
                    }
                    if (charset == null || charset.equals(StandardCharsets.UTF_8)) {
                        cVar2.c(codePointAt);
                    } else {
                        cVar2.a(str, i2, Character.charCount(codePointAt) + i2, charset);
                    }
                    while (!cVar2.i()) {
                        byte readByte = cVar2.readByte() & 255;
                        cVar.writeByte(37);
                        cVar.writeByte((int) f2078j[(readByte >> 4) & 15]);
                        cVar.writeByte((int) f2078j[readByte & 15]);
                    }
                } else {
                    cVar.c(codePointAt);
                }
            }
            i2 += Character.charCount(codePointAt);
        }
    }

    static String a(String str, String str2, boolean z, boolean z2, boolean z3, boolean z4, Charset charset) {
        return a(str, 0, str.length(), str2, z, z2, z3, z4, charset);
    }

    static String a(String str, String str2, boolean z, boolean z2, boolean z3, boolean z4) {
        return a(str, 0, str.length(), str2, z, z2, z3, z4, (Charset) null);
    }
}
