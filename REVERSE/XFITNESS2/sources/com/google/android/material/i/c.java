package com.google.android.material.i;

import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.drawable.Drawable;
import androidx.core.graphics.drawable.b;

/* compiled from: MaterialShapeDrawable */
public class c extends Drawable implements b {
    private Paint.Style A;
    private PorterDuffColorFilter B;
    private PorterDuff.Mode C;
    private ColorStateList D;
    private final Paint e = new Paint();

    /* renamed from: f  reason: collision with root package name */
    private final Matrix[] f1491f = new Matrix[4];

    /* renamed from: g  reason: collision with root package name */
    private final Matrix[] f1492g = new Matrix[4];

    /* renamed from: h  reason: collision with root package name */
    private final d[] f1493h = new d[4];

    /* renamed from: i  reason: collision with root package name */
    private final Matrix f1494i = new Matrix();

    /* renamed from: j  reason: collision with root package name */
    private final Path f1495j = new Path();
    private final PointF k = new PointF();
    private final d l = new d();
    private final Region m = new Region();
    private final Region n = new Region();
    private final float[] o = new float[2];
    private final float[] p = new float[2];
    private e q = null;
    private boolean r;
    private boolean s;
    private float t;
    private int u;
    private int v;
    private int w;
    private int x;
    private float y;
    private float z;

    public c(e eVar) {
        this.r = false;
        this.s = false;
        this.t = 1.0f;
        this.u = -16777216;
        this.v = 5;
        this.w = 10;
        this.x = 255;
        this.y = 1.0f;
        this.z = 0.0f;
        this.A = Paint.Style.FILL_AND_STROKE;
        this.C = PorterDuff.Mode.SRC_IN;
        this.D = null;
        this.q = eVar;
        for (int i2 = 0; i2 < 4; i2++) {
            this.f1491f[i2] = new Matrix();
            this.f1492g[i2] = new Matrix();
            this.f1493h[i2] = new d();
        }
    }

    private static int a(int i2, int i3) {
        return (i2 * (i3 + (i3 >>> 7))) >>> 8;
    }

    private void c(int i2, int i3, int i4) {
        a(i2, i3, i4, this.k);
        a(i2).a(a(i2, i3, i4), this.t, this.f1493h[i2]);
        this.f1491f[i2].reset();
        Matrix matrix = this.f1491f[i2];
        PointF pointF = this.k;
        matrix.setTranslate(pointF.x, pointF.y);
        this.f1491f[i2].preRotate((float) Math.toDegrees((double) (b(((i2 - 1) + 4) % 4, i3, i4) + 1.5707964f)));
    }

    private void d(int i2, int i3, int i4) {
        float[] fArr = this.o;
        d[] dVarArr = this.f1493h;
        fArr[0] = dVarArr[i2].c;
        fArr[1] = dVarArr[i2].d;
        this.f1491f[i2].mapPoints(fArr);
        float b = b(i2, i3, i4);
        this.f1492g[i2].reset();
        Matrix matrix = this.f1492g[i2];
        float[] fArr2 = this.o;
        matrix.setTranslate(fArr2[0], fArr2[1]);
        this.f1492g[i2].preRotate((float) Math.toDegrees((double) b));
    }

    public void a(boolean z2) {
        this.r = z2;
        invalidateSelf();
    }

    public ColorStateList b() {
        return this.D;
    }

    public void draw(Canvas canvas) {
        this.e.setColorFilter(this.B);
        int alpha = this.e.getAlpha();
        this.e.setAlpha(a(alpha, this.x));
        this.e.setStrokeWidth(this.z);
        this.e.setStyle(this.A);
        int i2 = this.v;
        if (i2 > 0 && this.r) {
            this.e.setShadowLayer((float) this.w, 0.0f, (float) i2, this.u);
        }
        if (this.q != null) {
            b(canvas.getWidth(), canvas.getHeight(), this.f1495j);
            canvas.drawPath(this.f1495j, this.e);
        } else {
            canvas.drawRect(0.0f, 0.0f, (float) canvas.getWidth(), (float) canvas.getHeight(), this.e);
        }
        this.e.setAlpha(alpha);
    }

    public int getOpacity() {
        return -3;
    }

    public Region getTransparentRegion() {
        Rect bounds = getBounds();
        this.m.set(bounds);
        b(bounds.width(), bounds.height(), this.f1495j);
        this.n.setPath(this.f1495j, this.m);
        this.m.op(this.n, Region.Op.DIFFERENCE);
        return this.m;
    }

    public void setAlpha(int i2) {
        this.x = i2;
        invalidateSelf();
    }

    public void setColorFilter(ColorFilter colorFilter) {
        this.e.setColorFilter(colorFilter);
        invalidateSelf();
    }

    public void setTint(int i2) {
        setTintList(ColorStateList.valueOf(i2));
    }

    public void setTintList(ColorStateList colorStateList) {
        this.D = colorStateList;
        c();
        invalidateSelf();
    }

    public void setTintMode(PorterDuff.Mode mode) {
        this.C = mode;
        c();
        invalidateSelf();
    }

    private void b(int i2, Path path) {
        int i3 = (i2 + 1) % 4;
        float[] fArr = this.o;
        d[] dVarArr = this.f1493h;
        fArr[0] = dVarArr[i2].c;
        fArr[1] = dVarArr[i2].d;
        this.f1491f[i2].mapPoints(fArr);
        float[] fArr2 = this.p;
        d[] dVarArr2 = this.f1493h;
        fArr2[0] = dVarArr2[i3].a;
        fArr2[1] = dVarArr2[i3].b;
        this.f1491f[i3].mapPoints(fArr2);
        float[] fArr3 = this.o;
        float f2 = fArr3[0];
        float[] fArr4 = this.p;
        this.l.b(0.0f, 0.0f);
        b(i2).a((float) Math.hypot((double) (f2 - fArr4[0]), (double) (fArr3[1] - fArr4[1])), this.t, this.l);
        this.l.a(this.f1492g[i2], path);
    }

    public float a() {
        return this.t;
    }

    public void a(float f2) {
        this.t = f2;
        invalidateSelf();
    }

    public void a(Paint.Style style) {
        this.A = style;
        invalidateSelf();
    }

    private void c() {
        ColorStateList colorStateList = this.D;
        if (colorStateList == null || this.C == null) {
            this.B = null;
            return;
        }
        int colorForState = colorStateList.getColorForState(getState(), 0);
        this.B = new PorterDuffColorFilter(colorForState, this.C);
        if (this.s) {
            this.u = colorForState;
        }
    }

    public void a(int i2, int i3, Path path) {
        path.rewind();
        if (this.q != null) {
            for (int i4 = 0; i4 < 4; i4++) {
                c(i4, i2, i3);
                d(i4, i2, i3);
            }
            for (int i5 = 0; i5 < 4; i5++) {
                a(i5, path);
                b(i5, path);
            }
            path.close();
        }
    }

    private b b(int i2) {
        if (i2 == 1) {
            return this.q.e();
        }
        if (i2 == 2) {
            return this.q.a();
        }
        if (i2 != 3) {
            return this.q.f();
        }
        return this.q.d();
    }

    private void a(int i2, Path path) {
        float[] fArr = this.o;
        d[] dVarArr = this.f1493h;
        fArr[0] = dVarArr[i2].a;
        fArr[1] = dVarArr[i2].b;
        this.f1491f[i2].mapPoints(fArr);
        if (i2 == 0) {
            float[] fArr2 = this.o;
            path.moveTo(fArr2[0], fArr2[1]);
        } else {
            float[] fArr3 = this.o;
            path.lineTo(fArr3[0], fArr3[1]);
        }
        this.f1493h[i2].a(this.f1491f[i2], path);
    }

    private float b(int i2, int i3, int i4) {
        a(i2, i3, i4, this.k);
        PointF pointF = this.k;
        float f2 = pointF.x;
        float f3 = pointF.y;
        a((i2 + 1) % 4, i3, i4, pointF);
        PointF pointF2 = this.k;
        return (float) Math.atan2((double) (pointF2.y - f3), (double) (pointF2.x - f2));
    }

    private a a(int i2) {
        if (i2 == 1) {
            return this.q.h();
        }
        if (i2 == 2) {
            return this.q.c();
        }
        if (i2 != 3) {
            return this.q.g();
        }
        return this.q.b();
    }

    private void a(int i2, int i3, int i4, PointF pointF) {
        if (i2 == 1) {
            pointF.set((float) i3, 0.0f);
        } else if (i2 == 2) {
            pointF.set((float) i3, (float) i4);
        } else if (i2 != 3) {
            pointF.set(0.0f, 0.0f);
        } else {
            pointF.set(0.0f, (float) i4);
        }
    }

    private void b(int i2, int i3, Path path) {
        a(i2, i3, path);
        if (this.y != 1.0f) {
            this.f1494i.reset();
            Matrix matrix = this.f1494i;
            float f2 = this.y;
            matrix.setScale(f2, f2, (float) (i2 / 2), (float) (i3 / 2));
            path.transform(this.f1494i);
        }
    }

    private float a(int i2, int i3, int i4) {
        a(((i2 - 1) + 4) % 4, i3, i4, this.k);
        PointF pointF = this.k;
        float f2 = pointF.x;
        float f3 = pointF.y;
        a((i2 + 1) % 4, i3, i4, pointF);
        PointF pointF2 = this.k;
        float f4 = pointF2.x;
        float f5 = pointF2.y;
        a(i2, i3, i4, pointF2);
        PointF pointF3 = this.k;
        float f6 = pointF3.x;
        float f7 = pointF3.y;
        float f8 = f5 - f7;
        float atan2 = ((float) Math.atan2((double) (f3 - f7), (double) (f2 - f6))) - ((float) Math.atan2((double) f8, (double) (f4 - f6)));
        if (atan2 >= 0.0f) {
            return atan2;
        }
        double d = (double) atan2;
        Double.isNaN(d);
        return (float) (d + 6.283185307179586d);
    }
}
