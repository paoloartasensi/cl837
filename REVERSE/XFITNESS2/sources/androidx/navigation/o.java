package androidx.navigation;

/* compiled from: NavOptions */
public final class o {
    private boolean a;
    private int b;
    private boolean c;
    private int d;
    private int e;

    /* renamed from: f  reason: collision with root package name */
    private int f742f;

    /* renamed from: g  reason: collision with root package name */
    private int f743g;

    /* compiled from: NavOptions */
    public static final class a {
        boolean a;
        int b = -1;
        boolean c;
        int d = -1;
        int e = -1;

        /* renamed from: f  reason: collision with root package name */
        int f744f = -1;

        /* renamed from: g  reason: collision with root package name */
        int f745g = -1;

        public a a(boolean z) {
            this.a = z;
            return this;
        }

        public a b(int i2) {
            this.e = i2;
            return this;
        }

        public a c(int i2) {
            this.f744f = i2;
            return this;
        }

        public a d(int i2) {
            this.f745g = i2;
            return this;
        }

        public a a(int i2, boolean z) {
            this.b = i2;
            this.c = z;
            return this;
        }

        public a a(int i2) {
            this.d = i2;
            return this;
        }

        public o a() {
            return new o(this.a, this.b, this.c, this.d, this.e, this.f744f, this.f745g);
        }
    }

    o(boolean z, int i2, boolean z2, int i3, int i4, int i5, int i6) {
        this.a = z;
        this.b = i2;
        this.c = z2;
        this.d = i3;
        this.e = i4;
        this.f742f = i5;
        this.f743g = i6;
    }

    public int a() {
        return this.d;
    }

    public int b() {
        return this.e;
    }

    public int c() {
        return this.f742f;
    }

    public int d() {
        return this.f743g;
    }

    public int e() {
        return this.b;
    }

    public boolean f() {
        return this.c;
    }

    public boolean g() {
        return this.a;
    }
}
