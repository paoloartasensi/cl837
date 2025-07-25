package h.a.a.a.i;

import android.graphics.Matrix;
import android.graphics.Path;
import android.graphics.RectF;
import com.github.mikephil.charting.data.CandleEntry;
import com.github.mikephil.charting.data.Entry;
import h.a.a.a.e.b.c;
import h.a.a.a.e.b.d;
import h.a.a.a.e.b.f;
import h.a.a.a.e.b.k;

/* compiled from: Transformer */
public class g {
    protected Matrix a = new Matrix();
    protected Matrix b = new Matrix();
    protected j c;
    protected float[] d = new float[1];
    protected float[] e = new float[1];

    /* renamed from: f  reason: collision with root package name */
    protected float[] f1732f = new float[1];

    /* renamed from: g  reason: collision with root package name */
    protected float[] f1733g = new float[1];

    /* renamed from: h  reason: collision with root package name */
    protected Matrix f1734h = new Matrix();

    /* renamed from: i  reason: collision with root package name */
    float[] f1735i = new float[2];

    /* renamed from: j  reason: collision with root package name */
    private Matrix f1736j = new Matrix();

    public g(j jVar) {
        new Matrix();
        this.c = jVar;
    }

    public void a(float f2, float f3, float f4, float f5) {
        float j2 = this.c.j() / f3;
        float f6 = this.c.f() / f4;
        if (Float.isInfinite(j2)) {
            j2 = 0.0f;
        }
        if (Float.isInfinite(f6)) {
            f6 = 0.0f;
        }
        this.a.reset();
        this.a.postTranslate(-f2, -f5);
        this.a.postScale(j2, -f6);
    }

    public void b(float[] fArr) {
        this.a.mapPoints(fArr);
        this.c.o().mapPoints(fArr);
        this.b.mapPoints(fArr);
    }

    public void b(RectF rectF, float f2) {
        rectF.left *= f2;
        rectF.right *= f2;
        this.a.mapRect(rectF);
        this.c.o().mapRect(rectF);
        this.b.mapRect(rectF);
    }

    public void a(boolean z) {
        this.b.reset();
        if (!z) {
            this.b.postTranslate(this.c.y(), this.c.k() - this.c.x());
            return;
        }
        this.b.setTranslate(this.c.y(), -this.c.A());
        this.b.postScale(1.0f, -1.0f);
    }

    public d b(float f2, float f3) {
        d a2 = d.a(0.0d, 0.0d);
        a(f2, f3, a2);
        return a2;
    }

    public float[] a(k kVar, float f2, float f3, int i2, int i3) {
        int i4 = ((int) ((((float) (i3 - i2)) * f2) + 1.0f)) * 2;
        if (this.d.length != i4) {
            this.d = new float[i4];
        }
        float[] fArr = this.d;
        for (int i5 = 0; i5 < i4; i5 += 2) {
            Entry c2 = kVar.c((i5 / 2) + i2);
            if (c2 != null) {
                fArr[i5] = c2.d();
                fArr[i5 + 1] = c2.c() * f3;
            } else {
                fArr[i5] = 0.0f;
                fArr[i5 + 1] = 0.0f;
            }
        }
        a().mapPoints(fArr);
        return fArr;
    }

    public float[] a(c cVar, float f2, int i2, int i3) {
        int i4 = ((i3 - i2) + 1) * 2;
        if (this.e.length != i4) {
            this.e = new float[i4];
        }
        float[] fArr = this.e;
        for (int i5 = 0; i5 < i4; i5 += 2) {
            Entry c2 = cVar.c((i5 / 2) + i2);
            if (c2 != null) {
                fArr[i5] = c2.d();
                fArr[i5 + 1] = c2.c() * f2;
            } else {
                fArr[i5] = 0.0f;
                fArr[i5 + 1] = 0.0f;
            }
        }
        a().mapPoints(fArr);
        return fArr;
    }

    public float[] a(f fVar, float f2, float f3, int i2, int i3) {
        int i4 = (((int) (((float) (i3 - i2)) * f2)) + 1) * 2;
        if (this.f1732f.length != i4) {
            this.f1732f = new float[i4];
        }
        float[] fArr = this.f1732f;
        for (int i5 = 0; i5 < i4; i5 += 2) {
            Entry c2 = fVar.c((i5 / 2) + i2);
            if (c2 != null) {
                fArr[i5] = c2.d();
                fArr[i5 + 1] = c2.c() * f3;
            } else {
                fArr[i5] = 0.0f;
                fArr[i5 + 1] = 0.0f;
            }
        }
        a().mapPoints(fArr);
        return fArr;
    }

    public float[] a(d dVar, float f2, float f3, int i2, int i3) {
        int i4 = ((int) ((((float) (i3 - i2)) * f2) + 1.0f)) * 2;
        if (this.f1733g.length != i4) {
            this.f1733g = new float[i4];
        }
        float[] fArr = this.f1733g;
        for (int i5 = 0; i5 < i4; i5 += 2) {
            CandleEntry candleEntry = (CandleEntry) dVar.c((i5 / 2) + i2);
            if (candleEntry != null) {
                fArr[i5] = candleEntry.d();
                fArr[i5 + 1] = candleEntry.f() * f3;
            } else {
                fArr[i5] = 0.0f;
                fArr[i5 + 1] = 0.0f;
            }
        }
        a().mapPoints(fArr);
        return fArr;
    }

    public void a(Path path) {
        path.transform(this.a);
        path.transform(this.c.o());
        path.transform(this.b);
    }

    public void a(RectF rectF) {
        this.a.mapRect(rectF);
        this.c.o().mapRect(rectF);
        this.b.mapRect(rectF);
    }

    public void a(RectF rectF, float f2) {
        rectF.top *= f2;
        rectF.bottom *= f2;
        this.a.mapRect(rectF);
        this.c.o().mapRect(rectF);
        this.b.mapRect(rectF);
    }

    public void a(float[] fArr) {
        Matrix matrix = this.f1734h;
        matrix.reset();
        this.b.invert(matrix);
        matrix.mapPoints(fArr);
        this.c.o().invert(matrix);
        matrix.mapPoints(fArr);
        this.a.invert(matrix);
        matrix.mapPoints(fArr);
    }

    public void a(float f2, float f3, d dVar) {
        float[] fArr = this.f1735i;
        fArr[0] = f2;
        fArr[1] = f3;
        a(fArr);
        float[] fArr2 = this.f1735i;
        dVar.f1724g = (double) fArr2[0];
        dVar.f1725h = (double) fArr2[1];
    }

    public d a(float f2, float f3) {
        float[] fArr = this.f1735i;
        fArr[0] = f2;
        fArr[1] = f3;
        b(fArr);
        float[] fArr2 = this.f1735i;
        return d.a((double) fArr2[0], (double) fArr2[1]);
    }

    public Matrix a() {
        this.f1736j.set(this.a);
        this.f1736j.postConcat(this.c.a);
        this.f1736j.postConcat(this.b);
        return this.f1736j;
    }
}
