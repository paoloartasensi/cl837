package h.a.a.a.i;

import h.a.a.a.i.f;

/* compiled from: FSize */
public final class b extends f.a {

    /* renamed from: i  reason: collision with root package name */
    private static f<b> f1720i;

    /* renamed from: g  reason: collision with root package name */
    public float f1721g;

    /* renamed from: h  reason: collision with root package name */
    public float f1722h;

    static {
        f<b> a = f.a(256, new b(0.0f, 0.0f));
        f1720i = a;
        a.a(0.5f);
    }

    public b() {
    }

    /* access modifiers changed from: protected */
    public f.a a() {
        return new b(0.0f, 0.0f);
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof b)) {
            return false;
        }
        b bVar = (b) obj;
        if (this.f1721g == bVar.f1721g && this.f1722h == bVar.f1722h) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return Float.floatToIntBits(this.f1721g) ^ Float.floatToIntBits(this.f1722h);
    }

    public String toString() {
        return this.f1721g + "x" + this.f1722h;
    }

    public b(float f2, float f3) {
        this.f1721g = f2;
        this.f1722h = f3;
    }

    public static b a(float f2, float f3) {
        b a = f1720i.a();
        a.f1721g = f2;
        a.f1722h = f3;
        return a;
    }

    public static void a(b bVar) {
        f1720i.a(bVar);
    }
}
