package h.a.a.a.i;

import android.graphics.Matrix;
import android.graphics.RectF;
import android.view.View;

/* compiled from: ViewPortHandler */
public class j {
    protected final Matrix a = new Matrix();
    protected RectF b = new RectF();
    protected float c = 0.0f;
    protected float d = 0.0f;
    private float e = 1.0f;

    /* renamed from: f  reason: collision with root package name */
    private float f1742f = Float.MAX_VALUE;

    /* renamed from: g  reason: collision with root package name */
    private float f1743g = 1.0f;

    /* renamed from: h  reason: collision with root package name */
    private float f1744h = Float.MAX_VALUE;

    /* renamed from: i  reason: collision with root package name */
    private float f1745i = 1.0f;

    /* renamed from: j  reason: collision with root package name */
    private float f1746j = 1.0f;
    private float k = 0.0f;
    private float l = 0.0f;
    private float m = 0.0f;
    private float n = 0.0f;
    protected Matrix o = new Matrix();
    protected final float[] p = new float[9];

    public float A() {
        return this.b.top;
    }

    public void a(float f2, float f3, float f4, float f5) {
        this.b.set(f2, f3, this.c - f4, this.d - f5);
    }

    public void b(float f2, float f3) {
        float y = y();
        float A = A();
        float z = z();
        float x = x();
        this.d = f3;
        this.c = f2;
        a(y, A, z, x);
    }

    public boolean c(float f2) {
        return this.b.right >= (((float) ((int) (f2 * 100.0f))) / 100.0f) - 1.0f;
    }

    public boolean d(float f2) {
        return this.b.top <= f2;
    }

    public float e() {
        return this.b.bottom;
    }

    public float f() {
        return this.b.height();
    }

    public float g() {
        return this.b.left;
    }

    public float h() {
        return this.b.right;
    }

    public float i() {
        return this.b.top;
    }

    public float j() {
        return this.b.width();
    }

    public float k() {
        return this.d;
    }

    public float l() {
        return this.c;
    }

    public e m() {
        return e.a(this.b.centerX(), this.b.centerY());
    }

    public RectF n() {
        return this.b;
    }

    public Matrix o() {
        return this.a;
    }

    public float p() {
        return this.f1745i;
    }

    public float q() {
        return this.f1746j;
    }

    public float r() {
        return Math.min(this.b.width(), this.b.height());
    }

    public boolean s() {
        return this.d > 0.0f && this.c > 0.0f;
    }

    public boolean t() {
        return this.m <= 0.0f && this.n <= 0.0f;
    }

    public boolean u() {
        return v() && w();
    }

    public boolean v() {
        float f2 = this.f1745i;
        float f3 = this.f1743g;
        return f2 <= f3 && f3 <= 1.0f;
    }

    public boolean w() {
        float f2 = this.f1746j;
        float f3 = this.e;
        return f2 <= f3 && f3 <= 1.0f;
    }

    public float x() {
        return this.d - this.b.bottom;
    }

    public float y() {
        return this.b.left;
    }

    public float z() {
        return this.c - this.b.right;
    }

    public void a(float f2, float f3, float f4, float f5, Matrix matrix) {
        matrix.reset();
        matrix.set(this.a);
        matrix.postScale(f2, f3, f4, f5);
    }

    public boolean c() {
        return this.f1745i > this.f1743g;
    }

    public boolean d() {
        return this.f1746j > this.e;
    }

    public boolean e(float f2) {
        return b(f2) && c(f2);
    }

    public boolean f(float f2) {
        return d(f2) && a(f2);
    }

    public void g(float f2) {
        this.m = i.a(f2);
    }

    public void h(float f2) {
        this.n = i.a(f2);
    }

    public void i(float f2) {
        if (f2 == 0.0f) {
            f2 = Float.MAX_VALUE;
        }
        this.f1744h = f2;
        a(this.a, this.b);
    }

    public void j(float f2) {
        if (f2 == 0.0f) {
            f2 = Float.MAX_VALUE;
        }
        this.f1742f = f2;
        a(this.a, this.b);
    }

    public void k(float f2) {
        if (f2 < 1.0f) {
            f2 = 1.0f;
        }
        this.f1743g = f2;
        a(this.a, this.b);
    }

    public void l(float f2) {
        if (f2 < 1.0f) {
            f2 = 1.0f;
        }
        this.e = f2;
        a(this.a, this.b);
    }

    public void a(float[] fArr, View view) {
        Matrix matrix = this.o;
        matrix.reset();
        matrix.set(this.a);
        matrix.postTranslate(-(fArr[0] - y()), -(fArr[1] - A()));
        a(matrix, view, true);
    }

    public boolean b(float f2) {
        return this.b.left <= f2 + 1.0f;
    }

    public boolean b() {
        return this.f1746j < this.f1742f;
    }

    public Matrix a(Matrix matrix, View view, boolean z) {
        this.a.set(matrix);
        a(this.a, this.b);
        if (z) {
            view.invalidate();
        }
        matrix.set(this.a);
        return matrix;
    }

    public void a(Matrix matrix, RectF rectF) {
        float f2;
        matrix.getValues(this.p);
        float[] fArr = this.p;
        float f3 = fArr[2];
        float f4 = fArr[0];
        float f5 = fArr[5];
        float f6 = fArr[4];
        this.f1745i = Math.min(Math.max(this.f1743g, f4), this.f1744h);
        this.f1746j = Math.min(Math.max(this.e, f6), this.f1742f);
        float f7 = 0.0f;
        if (rectF != null) {
            f7 = rectF.width();
            f2 = rectF.height();
        } else {
            f2 = 0.0f;
        }
        this.k = Math.min(Math.max(f3, ((-f7) * (this.f1745i - 1.0f)) - this.m), this.m);
        float max = Math.max(Math.min(f5, (f2 * (this.f1746j - 1.0f)) + this.n), -this.n);
        this.l = max;
        float[] fArr2 = this.p;
        fArr2[2] = this.k;
        fArr2[0] = this.f1745i;
        fArr2[5] = max;
        fArr2[4] = this.f1746j;
        matrix.setValues(fArr2);
    }

    public boolean a(float f2, float f3) {
        return e(f2) && f(f3);
    }

    public boolean a(float f2) {
        return this.b.bottom >= ((float) ((int) (f2 * 100.0f))) / 100.0f;
    }

    public boolean a() {
        return this.f1745i < this.f1744h;
    }
}
