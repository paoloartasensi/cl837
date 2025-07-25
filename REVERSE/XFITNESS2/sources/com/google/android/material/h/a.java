package com.google.android.material.h;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import androidx.appcompat.b.a.c;
import com.google.android.material.R$color;

/* compiled from: ShadowDrawableWrapper */
public class a extends c {
    static final double u = Math.cos(Math.toRadians(45.0d));

    /* renamed from: f  reason: collision with root package name */
    final Paint f1486f;

    /* renamed from: g  reason: collision with root package name */
    final Paint f1487g;

    /* renamed from: h  reason: collision with root package name */
    final RectF f1488h;

    /* renamed from: i  reason: collision with root package name */
    float f1489i;

    /* renamed from: j  reason: collision with root package name */
    Path f1490j;
    float k;
    float l;
    float m;
    private boolean n = true;
    private final int o;
    private final int p;
    private final int q;
    private boolean r = true;
    private float s;
    private boolean t = false;

    public a(Context context, Drawable drawable, float f2, float f3, float f4) {
        super(drawable);
        this.o = androidx.core.content.a.a(context, R$color.design_fab_shadow_start_color);
        this.p = androidx.core.content.a.a(context, R$color.design_fab_shadow_mid_color);
        this.q = androidx.core.content.a.a(context, R$color.design_fab_shadow_end_color);
        Paint paint = new Paint(5);
        this.f1486f = paint;
        paint.setStyle(Paint.Style.FILL);
        this.f1489i = (float) Math.round(f2);
        this.f1488h = new RectF();
        Paint paint2 = new Paint(this.f1486f);
        this.f1487g = paint2;
        paint2.setAntiAlias(false);
        a(f3, f4);
    }

    private static int c(float f2) {
        int round = Math.round(f2);
        return round % 2 == 1 ? round - 1 : round;
    }

    public void a(boolean z) {
        this.r = z;
        invalidateSelf();
    }

    public void b(float f2) {
        a(f2, this.k);
    }

    public void draw(Canvas canvas) {
        if (this.n) {
            a(getBounds());
            this.n = false;
        }
        a(canvas);
        super.draw(canvas);
    }

    public int getOpacity() {
        return -3;
    }

    public boolean getPadding(Rect rect) {
        int ceil = (int) Math.ceil((double) b(this.k, this.f1489i, this.r));
        int ceil2 = (int) Math.ceil((double) a(this.k, this.f1489i, this.r));
        rect.set(ceil2, ceil, ceil2, ceil);
        return true;
    }

    /* access modifiers changed from: protected */
    public void onBoundsChange(Rect rect) {
        this.n = true;
    }

    public void setAlpha(int i2) {
        super.setAlpha(i2);
        this.f1486f.setAlpha(i2);
        this.f1487g.setAlpha(i2);
    }

    public float b() {
        return this.m;
    }

    public static float b(float f2, float f3, boolean z) {
        if (!z) {
            return f2 * 1.5f;
        }
        double d = (double) (f2 * 1.5f);
        double d2 = (double) f3;
        Double.isNaN(d2);
        Double.isNaN(d);
        return (float) (d + ((1.0d - u) * d2));
    }

    private void c() {
        float f2 = this.f1489i;
        RectF rectF = new RectF(-f2, -f2, f2, f2);
        RectF rectF2 = new RectF(rectF);
        float f3 = this.l;
        rectF2.inset(-f3, -f3);
        Path path = this.f1490j;
        if (path == null) {
            this.f1490j = new Path();
        } else {
            path.reset();
        }
        this.f1490j.setFillType(Path.FillType.EVEN_ODD);
        this.f1490j.moveTo(-this.f1489i, 0.0f);
        this.f1490j.rLineTo(-this.l, 0.0f);
        this.f1490j.arcTo(rectF2, 180.0f, 90.0f, false);
        this.f1490j.arcTo(rectF, 270.0f, -90.0f, false);
        this.f1490j.close();
        float f4 = -rectF2.top;
        if (f4 > 0.0f) {
            float f5 = this.f1489i / f4;
            Paint paint = this.f1486f;
            RadialGradient radialGradient = r8;
            RadialGradient radialGradient2 = new RadialGradient(0.0f, 0.0f, f4, new int[]{0, this.o, this.p, this.q}, new float[]{0.0f, f5, ((1.0f - f5) / 2.0f) + f5, 1.0f}, Shader.TileMode.CLAMP);
            paint.setShader(radialGradient);
        }
        this.f1487g.setShader(new LinearGradient(0.0f, rectF.top, 0.0f, rectF2.top, new int[]{this.o, this.p, this.q}, new float[]{0.0f, 0.5f, 1.0f}, Shader.TileMode.CLAMP));
        this.f1487g.setAntiAlias(false);
    }

    public void a(float f2, float f3) {
        if (f2 < 0.0f || f3 < 0.0f) {
            throw new IllegalArgumentException("invalid shadow size");
        }
        float c = (float) c(f2);
        float c2 = (float) c(f3);
        if (c > c2) {
            if (!this.t) {
                this.t = true;
            }
            c = c2;
        }
        if (this.m != c || this.k != c2) {
            this.m = c;
            this.k = c2;
            this.l = (float) Math.round(c * 1.5f);
            this.n = true;
            invalidateSelf();
        }
    }

    public static float a(float f2, float f3, boolean z) {
        if (!z) {
            return f2;
        }
        double d = (double) f2;
        double d2 = (double) f3;
        Double.isNaN(d2);
        Double.isNaN(d);
        return (float) (d + ((1.0d - u) * d2));
    }

    public final void a(float f2) {
        if (this.s != f2) {
            this.s = f2;
            invalidateSelf();
        }
    }

    private void a(Canvas canvas) {
        float f2;
        int i2;
        int i3;
        float f3;
        float f4;
        float f5;
        Canvas canvas2 = canvas;
        int save = canvas.save();
        canvas2.rotate(this.s, this.f1488h.centerX(), this.f1488h.centerY());
        float f6 = this.f1489i;
        float f7 = (-f6) - this.l;
        float f8 = f6 * 2.0f;
        boolean z = this.f1488h.width() - f8 > 0.0f;
        boolean z2 = this.f1488h.height() - f8 > 0.0f;
        float f9 = this.m;
        float f10 = f6 / ((f9 - (0.5f * f9)) + f6);
        float f11 = f6 / ((f9 - (0.25f * f9)) + f6);
        float f12 = f6 / ((f9 - (f9 * 1.0f)) + f6);
        int save2 = canvas.save();
        RectF rectF = this.f1488h;
        canvas2.translate(rectF.left + f6, rectF.top + f6);
        canvas2.scale(f10, f11);
        canvas2.drawPath(this.f1490j, this.f1486f);
        if (z) {
            canvas2.scale(1.0f / f10, 1.0f);
            i3 = save2;
            f2 = f12;
            i2 = save;
            f3 = f11;
            canvas.drawRect(0.0f, f7, this.f1488h.width() - f8, -this.f1489i, this.f1487g);
        } else {
            i3 = save2;
            f2 = f12;
            i2 = save;
            f3 = f11;
        }
        canvas2.restoreToCount(i3);
        int save3 = canvas.save();
        RectF rectF2 = this.f1488h;
        canvas2.translate(rectF2.right - f6, rectF2.bottom - f6);
        float f13 = f2;
        canvas2.scale(f10, f13);
        canvas2.rotate(180.0f);
        canvas2.drawPath(this.f1490j, this.f1486f);
        if (z) {
            canvas2.scale(1.0f / f10, 1.0f);
            f4 = f3;
            f5 = f13;
            canvas.drawRect(0.0f, f7, this.f1488h.width() - f8, (-this.f1489i) + this.l, this.f1487g);
        } else {
            f4 = f3;
            f5 = f13;
        }
        canvas2.restoreToCount(save3);
        int save4 = canvas.save();
        RectF rectF3 = this.f1488h;
        canvas2.translate(rectF3.left + f6, rectF3.bottom - f6);
        canvas2.scale(f10, f5);
        canvas2.rotate(270.0f);
        canvas2.drawPath(this.f1490j, this.f1486f);
        if (z2) {
            canvas2.scale(1.0f / f5, 1.0f);
            canvas.drawRect(0.0f, f7, this.f1488h.height() - f8, -this.f1489i, this.f1487g);
        }
        canvas2.restoreToCount(save4);
        int save5 = canvas.save();
        RectF rectF4 = this.f1488h;
        canvas2.translate(rectF4.right - f6, rectF4.top + f6);
        float f14 = f4;
        canvas2.scale(f10, f14);
        canvas2.rotate(90.0f);
        canvas2.drawPath(this.f1490j, this.f1486f);
        if (z2) {
            canvas2.scale(1.0f / f14, 1.0f);
            canvas.drawRect(0.0f, f7, this.f1488h.height() - f8, -this.f1489i, this.f1487g);
        }
        canvas2.restoreToCount(save5);
        canvas2.restoreToCount(i2);
    }

    private void a(Rect rect) {
        float f2 = this.k;
        float f3 = 1.5f * f2;
        this.f1488h.set(((float) rect.left) + f2, ((float) rect.top) + f3, ((float) rect.right) - f2, ((float) rect.bottom) - f3);
        Drawable a = a();
        RectF rectF = this.f1488h;
        a.setBounds((int) rectF.left, (int) rectF.top, (int) rectF.right, (int) rectF.bottom);
        c();
    }
}
