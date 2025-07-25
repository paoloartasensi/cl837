package androidx.core.f;

import android.text.SpannableStringBuilder;
import com.jeremyliao.liveeventbus.BuildConfig;
import java.util.Locale;

/* compiled from: BidiFormatter */
public final class a {
    static final d d = e.c;
    private static final String e = Character.toString(8206);

    /* renamed from: f  reason: collision with root package name */
    private static final String f489f = Character.toString(8207);

    /* renamed from: g  reason: collision with root package name */
    static final a f490g = new a(false, 2, d);

    /* renamed from: h  reason: collision with root package name */
    static final a f491h = new a(true, 2, d);
    private final boolean a;
    private final int b;
    private final d c;

    /* renamed from: androidx.core.f.a$a  reason: collision with other inner class name */
    /* compiled from: BidiFormatter */
    public static final class C0020a {
        private boolean a;
        private int b;
        private d c;

        public C0020a() {
            b(a.a(Locale.getDefault()));
        }

        private static a a(boolean z) {
            return z ? a.f491h : a.f490g;
        }

        private void b(boolean z) {
            this.a = z;
            this.c = a.d;
            this.b = 2;
        }

        public a a() {
            if (this.b == 2 && this.c == a.d) {
                return a(this.a);
            }
            return new a(this.a, this.b, this.c);
        }
    }

    /* compiled from: BidiFormatter */
    private static class b {

        /* renamed from: f  reason: collision with root package name */
        private static final byte[] f492f = new byte[1792];
        private final CharSequence a;
        private final boolean b;
        private final int c;
        private int d;
        private char e;

        static {
            for (int i2 = 0; i2 < 1792; i2++) {
                f492f[i2] = Character.getDirectionality(i2);
            }
        }

        b(CharSequence charSequence, boolean z) {
            this.a = charSequence;
            this.b = z;
            this.c = charSequence.length();
        }

        private static byte a(char c2) {
            return c2 < 1792 ? f492f[c2] : Character.getDirectionality(c2);
        }

        private byte e() {
            char charAt;
            int i2 = this.d;
            do {
                int i3 = this.d;
                if (i3 <= 0) {
                    break;
                }
                CharSequence charSequence = this.a;
                int i4 = i3 - 1;
                this.d = i4;
                charAt = charSequence.charAt(i4);
                this.e = charAt;
                if (charAt == '&') {
                    return 12;
                }
            } while (charAt != ';');
            this.d = i2;
            this.e = ';';
            return 13;
        }

        private byte f() {
            char charAt;
            do {
                int i2 = this.d;
                if (i2 >= this.c) {
                    return 12;
                }
                CharSequence charSequence = this.a;
                this.d = i2 + 1;
                charAt = charSequence.charAt(i2);
                this.e = charAt;
            } while (charAt != ';');
            return 12;
        }

        private byte g() {
            char charAt;
            int i2 = this.d;
            while (true) {
                int i3 = this.d;
                if (i3 <= 0) {
                    break;
                }
                CharSequence charSequence = this.a;
                int i4 = i3 - 1;
                this.d = i4;
                char charAt2 = charSequence.charAt(i4);
                this.e = charAt2;
                if (charAt2 == '<') {
                    return 12;
                }
                if (charAt2 == '>') {
                    break;
                } else if (charAt2 == '\"' || charAt2 == '\'') {
                    char c2 = this.e;
                    do {
                        int i5 = this.d;
                        if (i5 <= 0) {
                            break;
                        }
                        CharSequence charSequence2 = this.a;
                        int i6 = i5 - 1;
                        this.d = i6;
                        charAt = charSequence2.charAt(i6);
                        this.e = charAt;
                    } while (charAt != c2);
                }
            }
            this.d = i2;
            this.e = '>';
            return 13;
        }

        private byte h() {
            char charAt;
            int i2 = this.d;
            while (true) {
                int i3 = this.d;
                if (i3 < this.c) {
                    CharSequence charSequence = this.a;
                    this.d = i3 + 1;
                    char charAt2 = charSequence.charAt(i3);
                    this.e = charAt2;
                    if (charAt2 == '>') {
                        return 12;
                    }
                    if (charAt2 == '\"' || charAt2 == '\'') {
                        char c2 = this.e;
                        do {
                            int i4 = this.d;
                            if (i4 >= this.c) {
                                break;
                            }
                            CharSequence charSequence2 = this.a;
                            this.d = i4 + 1;
                            charAt = charSequence2.charAt(i4);
                            this.e = charAt;
                        } while (charAt != c2);
                    }
                } else {
                    this.d = i2;
                    this.e = '<';
                    return 13;
                }
            }
        }

        /* access modifiers changed from: package-private */
        public byte b() {
            char charAt = this.a.charAt(this.d);
            this.e = charAt;
            if (Character.isHighSurrogate(charAt)) {
                int codePointAt = Character.codePointAt(this.a, this.d);
                this.d += Character.charCount(codePointAt);
                return Character.getDirectionality(codePointAt);
            }
            this.d++;
            byte a2 = a(this.e);
            if (!this.b) {
                return a2;
            }
            char c2 = this.e;
            if (c2 == '<') {
                return h();
            }
            return c2 == '&' ? f() : a2;
        }

        /* access modifiers changed from: package-private */
        public int c() {
            this.d = 0;
            int i2 = 0;
            int i3 = 0;
            int i4 = 0;
            while (this.d < this.c && i2 == 0) {
                byte b2 = b();
                if (b2 != 0) {
                    if (b2 == 1 || b2 == 2) {
                        if (i4 == 0) {
                            return 1;
                        }
                    } else if (b2 != 9) {
                        switch (b2) {
                            case 14:
                            case 15:
                                i4++;
                                i3 = -1;
                                continue;
                            case 16:
                            case 17:
                                i4++;
                                i3 = 1;
                                continue;
                            case 18:
                                i4--;
                                i3 = 0;
                                continue;
                        }
                    }
                } else if (i4 == 0) {
                    return -1;
                }
                i2 = i4;
            }
            if (i2 == 0) {
                return 0;
            }
            if (i3 != 0) {
                return i3;
            }
            while (this.d > 0) {
                switch (a()) {
                    case 14:
                    case 15:
                        if (i2 == i4) {
                            return -1;
                        }
                        break;
                    case 16:
                    case 17:
                        if (i2 == i4) {
                            return 1;
                        }
                        break;
                    case 18:
                        i4++;
                        continue;
                }
                i4--;
            }
            return 0;
        }

        /* access modifiers changed from: package-private */
        /* JADX WARNING: Code restructure failed: missing block: B:18:0x002b, code lost:
            r1 = r1 - 1;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public int d() {
            /*
                r7 = this;
                int r0 = r7.c
                r7.d = r0
                r0 = 0
                r1 = 0
                r2 = 0
            L_0x0007:
                int r3 = r7.d
                if (r3 <= 0) goto L_0x003b
                byte r3 = r7.a()
                r4 = -1
                if (r3 == 0) goto L_0x0034
                r5 = 1
                if (r3 == r5) goto L_0x002e
                r6 = 2
                if (r3 == r6) goto L_0x002e
                r6 = 9
                if (r3 == r6) goto L_0x0007
                switch(r3) {
                    case 14: goto L_0x0028;
                    case 15: goto L_0x0028;
                    case 16: goto L_0x0025;
                    case 17: goto L_0x0025;
                    case 18: goto L_0x0022;
                    default: goto L_0x001f;
                }
            L_0x001f:
                if (r2 != 0) goto L_0x0007
                goto L_0x0039
            L_0x0022:
                int r1 = r1 + 1
                goto L_0x0007
            L_0x0025:
                if (r2 != r1) goto L_0x002b
                return r5
            L_0x0028:
                if (r2 != r1) goto L_0x002b
                return r4
            L_0x002b:
                int r1 = r1 + -1
                goto L_0x0007
            L_0x002e:
                if (r1 != 0) goto L_0x0031
                return r5
            L_0x0031:
                if (r2 != 0) goto L_0x0007
                goto L_0x0039
            L_0x0034:
                if (r1 != 0) goto L_0x0037
                return r4
            L_0x0037:
                if (r2 != 0) goto L_0x0007
            L_0x0039:
                r2 = r1
                goto L_0x0007
            L_0x003b:
                return r0
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.core.f.a.b.d():int");
        }

        /* access modifiers changed from: package-private */
        public byte a() {
            char charAt = this.a.charAt(this.d - 1);
            this.e = charAt;
            if (Character.isLowSurrogate(charAt)) {
                int codePointBefore = Character.codePointBefore(this.a, this.d);
                this.d -= Character.charCount(codePointBefore);
                return Character.getDirectionality(codePointBefore);
            }
            this.d--;
            byte a2 = a(this.e);
            if (!this.b) {
                return a2;
            }
            char c2 = this.e;
            if (c2 == '>') {
                return g();
            }
            return c2 == ';' ? e() : a2;
        }
    }

    a(boolean z, int i2, d dVar) {
        this.a = z;
        this.b = i2;
        this.c = dVar;
    }

    public static a b() {
        return new C0020a().a();
    }

    private static int c(CharSequence charSequence) {
        return new b(charSequence, false).d();
    }

    public boolean a() {
        return (this.b & 2) != 0;
    }

    private String a(CharSequence charSequence, d dVar) {
        boolean a2 = dVar.a(charSequence, 0, charSequence.length());
        if (!this.a && (a2 || c(charSequence) == 1)) {
            return e;
        }
        if (this.a) {
            return (!a2 || c(charSequence) == -1) ? f489f : BuildConfig.FLAVOR;
        }
        return BuildConfig.FLAVOR;
    }

    private String b(CharSequence charSequence, d dVar) {
        boolean a2 = dVar.a(charSequence, 0, charSequence.length());
        if (!this.a && (a2 || b(charSequence) == 1)) {
            return e;
        }
        if (this.a) {
            return (!a2 || b(charSequence) == -1) ? f489f : BuildConfig.FLAVOR;
        }
        return BuildConfig.FLAVOR;
    }

    private static int b(CharSequence charSequence) {
        return new b(charSequence, false).c();
    }

    public CharSequence a(CharSequence charSequence, d dVar, boolean z) {
        if (charSequence == null) {
            return null;
        }
        boolean a2 = dVar.a(charSequence, 0, charSequence.length());
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
        if (a() && z) {
            spannableStringBuilder.append(b(charSequence, a2 ? e.b : e.a));
        }
        if (a2 != this.a) {
            spannableStringBuilder.append(a2 ? (char) 8235 : 8234);
            spannableStringBuilder.append(charSequence);
            spannableStringBuilder.append(8236);
        } else {
            spannableStringBuilder.append(charSequence);
        }
        if (z) {
            spannableStringBuilder.append(a(charSequence, a2 ? e.b : e.a));
        }
        return spannableStringBuilder;
    }

    public CharSequence a(CharSequence charSequence) {
        return a(charSequence, this.c, true);
    }

    static boolean a(Locale locale) {
        return f.b(locale) == 1;
    }
}
