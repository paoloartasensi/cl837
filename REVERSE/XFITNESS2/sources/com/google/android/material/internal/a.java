package com.google.android.material.internal;

import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;

/* compiled from: CircularBorderDrawable */
public class a extends Drawable {
    final Paint a;
    final Rect b = new Rect();
    final RectF c = new RectF();
    final b d = new b();
    float e;

    /* renamed from: f  reason: collision with root package name */
    private int f1510f;

    /* renamed from: g  reason: collision with root package name */
    private int f1511g;

    /* renamed from: h  reason: collision with root package name */
    private int f1512h;

    /* renamed from: i  reason: collision with root package name */
    private int f1513i;

    /* renamed from: j  reason: collision with root package name */
    private ColorStateList f1514j;
    private int k;
    private boolean l = true;
    private float m;

    /* compiled from: CircularBorderDrawable */
    private class b extends Drawable.ConstantState {
        private b() {
        }

        public int getChangingConfigurations() {
            return 0;
        }

        public Drawable newDrawable() {
            return a.this;
        }
    }

    public a() {
        Paint paint = new Paint(1);
        this.a = paint;
        paint.setStyle(Paint.Style.STROKE);
    }

    public void a(int i2, int i3, int i4, int i5) {
        this.f1510f = i2;
        this.f1511g = i3;
        this.f1512h = i4;
        this.f1513i = i5;
    }

    public final void b(float f2) {
        if (f2 != this.m) {
            this.m = f2;
            invalidateSelf();
        }
    }

    public void draw(Canvas canvas) {
        if (this.l) {
            this.a.setShader(a());
            this.l = false;
        }
        float strokeWidth = this.a.getStrokeWidth() / 2.0f;
        RectF rectF = this.c;
        copyBounds(this.b);
        rectF.set(this.b);
        rectF.left += strokeWidth;
        rectF.top += strokeWidth;
        rectF.right -= strokeWidth;
        rectF.bottom -= strokeWidth;
        canvas.save();
        canvas.rotate(this.m, rectF.centerX(), rectF.centerY());
        canvas.drawOval(rectF, this.a);
        canvas.restore();
    }

    public Drawable.ConstantState getConstantState() {
        return this.d;
    }

    public int getOpacity() {
        return this.e > 0.0f ? -3 : -2;
    }

    public boolean getPadding(Rect rect) {
        int round = Math.round(this.e);
        rect.set(round, round, round, round);
        return true;
    }

    public boolean isStateful() {
        ColorStateList colorStateList = this.f1514j;
        return (colorStateList != null && colorStateList.isStateful()) || super.isStateful();
    }

    /* access modifiers changed from: protected */
    public void onBoundsChange(Rect rect) {
        this.l = true;
    }

    /* access modifiers changed from: protected */
    public boolean onStateChange(int[] iArr) {
        int colorForState;
        ColorStateList colorStateList = this.f1514j;
        if (!(colorStateList == null || (colorForState = colorStateList.getColorForState(iArr, this.k)) == this.k)) {
            this.l = true;
            this.k = colorForState;
        }
        if (this.l) {
            invalidateSelf();
        }
        return this.l;
    }

    public void setAlpha(int i2) {
        this.a.setAlpha(i2);
        invalidateSelf();
    }

    public void setColorFilter(ColorFilter colorFilter) {
        this.a.setColorFilter(colorFilter);
        invalidateSelf();
    }

    public void a(float f2) {
        if (this.e != f2) {
            this.e = f2;
            this.a.setStrokeWidth(f2 * 1.3333f);
            this.l = true;
            invalidateSelf();
        }
    }

    public void a(ColorStateList colorStateList) {
        if (colorStateList != null) {
            this.k = colorStateList.getColorForState(getState(), this.k);
        }
        this.f1514j = colorStateList;
        this.l = true;
        invalidateSelf();
    }

    private Shader a() {
        Rect rect = this.b;
        copyBounds(rect);
        float height = this.e / ((float) rect.height());
        return new LinearGradient(0.0f, (float) rect.top, 0.0f, (float) rect.bottom, new int[]{androidx.core.a.a.b(this.f1510f, this.k), androidx.core.a.a.b(this.f1511g, this.k), androidx.core.a.a.b(androidx.core.a.a.c(this.f1511g, 0), this.k), androidx.core.a.a.b(androidx.core.a.a.c(this.f1513i, 0), this.k), androidx.core.a.a.b(this.f1513i, this.k), androidx.core.a.a.b(this.f1512h, this.k)}, new float[]{0.0f, height, 0.5f, 0.5f, 1.0f - height, 1.0f}, Shader.TileMode.CLAMP);
    }
}
