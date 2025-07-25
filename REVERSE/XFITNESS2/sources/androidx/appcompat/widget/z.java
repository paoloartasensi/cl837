package androidx.appcompat.widget;

/* compiled from: RtlSpacingHelper */
class z {
    private int a = 0;
    private int b = 0;
    private int c = Integer.MIN_VALUE;
    private int d = Integer.MIN_VALUE;
    private int e = 0;

    /* renamed from: f  reason: collision with root package name */
    private int f337f = 0;

    /* renamed from: g  reason: collision with root package name */
    private boolean f338g = false;

    /* renamed from: h  reason: collision with root package name */
    private boolean f339h = false;

    z() {
    }

    public int a() {
        return this.f338g ? this.a : this.b;
    }

    public int b() {
        return this.a;
    }

    public int c() {
        return this.b;
    }

    public int d() {
        return this.f338g ? this.b : this.a;
    }

    public void a(int i2, int i3) {
        this.f339h = false;
        if (i2 != Integer.MIN_VALUE) {
            this.e = i2;
            this.a = i2;
        }
        if (i3 != Integer.MIN_VALUE) {
            this.f337f = i3;
            this.b = i3;
        }
    }

    public void b(int i2, int i3) {
        this.c = i2;
        this.d = i3;
        this.f339h = true;
        if (this.f338g) {
            if (i3 != Integer.MIN_VALUE) {
                this.a = i3;
            }
            if (i2 != Integer.MIN_VALUE) {
                this.b = i2;
                return;
            }
            return;
        }
        if (i2 != Integer.MIN_VALUE) {
            this.a = i2;
        }
        if (i3 != Integer.MIN_VALUE) {
            this.b = i3;
        }
    }

    public void a(boolean z) {
        if (z != this.f338g) {
            this.f338g = z;
            if (!this.f339h) {
                this.a = this.e;
                this.b = this.f337f;
            } else if (z) {
                int i2 = this.d;
                if (i2 == Integer.MIN_VALUE) {
                    i2 = this.e;
                }
                this.a = i2;
                int i3 = this.c;
                if (i3 == Integer.MIN_VALUE) {
                    i3 = this.f337f;
                }
                this.b = i3;
            } else {
                int i4 = this.c;
                if (i4 == Integer.MIN_VALUE) {
                    i4 = this.e;
                }
                this.a = i4;
                int i5 = this.d;
                if (i5 == Integer.MIN_VALUE) {
                    i5 = this.f337f;
                }
                this.b = i5;
            }
        }
    }
}
