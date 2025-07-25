package okio;

/* compiled from: Segment */
final class o {
    final byte[] a;
    int b;
    int c;
    boolean d;
    boolean e;

    /* renamed from: f  reason: collision with root package name */
    o f2109f;

    /* renamed from: g  reason: collision with root package name */
    o f2110g;

    o() {
        this.a = new byte[8192];
        this.e = true;
        this.d = false;
    }

    public final o a(o oVar) {
        oVar.f2110g = this;
        oVar.f2109f = this.f2109f;
        this.f2109f.f2110g = oVar;
        this.f2109f = oVar;
        return oVar;
    }

    public final o b() {
        o oVar = this.f2109f;
        if (oVar == this) {
            oVar = null;
        }
        o oVar2 = this.f2110g;
        oVar2.f2109f = this.f2109f;
        this.f2109f.f2110g = oVar2;
        this.f2109f = null;
        this.f2110g = null;
        return oVar;
    }

    /* access modifiers changed from: package-private */
    public final o c() {
        this.d = true;
        return new o(this.a, this.b, this.c, true, false);
    }

    o(byte[] bArr, int i2, int i3, boolean z, boolean z2) {
        this.a = bArr;
        this.b = i2;
        this.c = i3;
        this.d = z;
        this.e = z2;
    }

    public final o a(int i2) {
        o oVar;
        if (i2 <= 0 || i2 > this.c - this.b) {
            throw new IllegalArgumentException();
        }
        if (i2 >= 1024) {
            oVar = c();
        } else {
            oVar = p.a();
            System.arraycopy(this.a, this.b, oVar.a, 0, i2);
        }
        oVar.c = oVar.b + i2;
        this.b += i2;
        this.f2110g.a(oVar);
        return oVar;
    }

    public final void a() {
        o oVar = this.f2110g;
        if (oVar == this) {
            throw new IllegalStateException();
        } else if (oVar.e) {
            int i2 = this.c - this.b;
            if (i2 <= (8192 - oVar.c) + (oVar.d ? 0 : oVar.b)) {
                a(this.f2110g, i2);
                b();
                p.a(this);
            }
        }
    }

    public final void a(o oVar, int i2) {
        if (oVar.e) {
            int i3 = oVar.c;
            if (i3 + i2 > 8192) {
                if (!oVar.d) {
                    int i4 = oVar.b;
                    if ((i3 + i2) - i4 <= 8192) {
                        byte[] bArr = oVar.a;
                        System.arraycopy(bArr, i4, bArr, 0, i3 - i4);
                        oVar.c -= oVar.b;
                        oVar.b = 0;
                    } else {
                        throw new IllegalArgumentException();
                    }
                } else {
                    throw new IllegalArgumentException();
                }
            }
            System.arraycopy(this.a, this.b, oVar.a, oVar.c, i2);
            oVar.c += i2;
            this.b += i2;
            return;
        }
        throw new IllegalArgumentException();
    }
}
