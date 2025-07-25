package h.a.a.a.d;

import com.github.mikephil.charting.components.YAxis;

/* compiled from: Highlight */
public class d {
    private float a;
    private float b;
    private float c;
    private float d;
    private int e;

    /* renamed from: f  reason: collision with root package name */
    private int f1672f;

    /* renamed from: g  reason: collision with root package name */
    private int f1673g;

    /* renamed from: h  reason: collision with root package name */
    private YAxis.AxisDependency f1674h;

    /* renamed from: i  reason: collision with root package name */
    private float f1675i;

    /* renamed from: j  reason: collision with root package name */
    private float f1676j;

    public d(float f2, float f3, float f4, float f5, int i2, YAxis.AxisDependency axisDependency) {
        this.a = Float.NaN;
        this.b = Float.NaN;
        this.e = -1;
        this.f1673g = -1;
        this.a = f2;
        this.b = f3;
        this.c = f4;
        this.d = f5;
        this.f1672f = i2;
        this.f1674h = axisDependency;
    }

    public void a(int i2) {
        this.e = i2;
    }

    public int b() {
        return this.e;
    }

    public int c() {
        return this.f1672f;
    }

    public float d() {
        return this.f1675i;
    }

    public float e() {
        return this.f1676j;
    }

    public int f() {
        return this.f1673g;
    }

    public float g() {
        return this.a;
    }

    public float h() {
        return this.c;
    }

    public float i() {
        return this.b;
    }

    public float j() {
        return this.d;
    }

    public String toString() {
        return "Highlight, x: " + this.a + ", y: " + this.b + ", dataSetIndex: " + this.f1672f + ", stackIndex (only stacked barentry): " + this.f1673g;
    }

    public YAxis.AxisDependency a() {
        return this.f1674h;
    }

    public void a(float f2, float f3) {
        this.f1675i = f2;
        this.f1676j = f3;
    }

    public boolean a(d dVar) {
        return dVar != null && this.f1672f == dVar.f1672f && this.a == dVar.a && this.f1673g == dVar.f1673g && this.e == dVar.e;
    }

    public d(float f2, float f3, float f4, float f5, int i2, int i3, YAxis.AxisDependency axisDependency) {
        this(f2, f3, f4, f5, i2, axisDependency);
        this.f1673g = i3;
    }
}
