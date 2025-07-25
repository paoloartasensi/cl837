package h.a.a.a.i;

import h.a.a.a.i.f;

/* compiled from: MPPointD */
public class d extends f.a {

    /* renamed from: i  reason: collision with root package name */
    private static f<d> f1723i;

    /* renamed from: g  reason: collision with root package name */
    public double f1724g;

    /* renamed from: h  reason: collision with root package name */
    public double f1725h;

    static {
        f<d> a = f.a(64, new d(0.0d, 0.0d));
        f1723i = a;
        a.a(0.5f);
    }

    private d(double d, double d2) {
        this.f1724g = d;
        this.f1725h = d2;
    }

    public static d a(double d, double d2) {
        d a = f1723i.a();
        a.f1724g = d;
        a.f1725h = d2;
        return a;
    }

    public String toString() {
        return "MPPointD, x: " + this.f1724g + ", y: " + this.f1725h;
    }

    public static void a(d dVar) {
        f1723i.a(dVar);
    }

    /* access modifiers changed from: protected */
    public f.a a() {
        return new d(0.0d, 0.0d);
    }
}
