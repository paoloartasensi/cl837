package androidx.cardview.widget;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import androidx.cardview.R$color;
import androidx.cardview.R$dimen;

/* compiled from: RoundRectDrawableWithShadow */
class g extends Drawable {
    private static final double q = Math.cos(Math.toRadians(45.0d));
    static a r;
    private final int a;
    private Paint b;
    private Paint c;
    private Paint d;
    private final RectF e;

    /* renamed from: f  reason: collision with root package name */
    private float f350f;

    /* renamed from: g  reason: collision with root package name */
    private Path f351g;

    /* renamed from: h  reason: collision with root package name */
    private float f352h;

    /* renamed from: i  reason: collision with root package name */
    private float f353i;

    /* renamed from: j  reason: collision with root package name */
    private float f354j;
    private ColorStateList k;
    private boolean l = true;
    private final int m;
    private final int n;
    private boolean o = true;
    private boolean p = false;

    /* compiled from: RoundRectDrawableWithShadow */
    interface a {
        void a(Canvas canvas, RectF rectF, float f2, Paint paint);
    }

    g(Resources resources, ColorStateList colorStateList, float f2, float f3, float f4) {
        this.m = resources.getColor(R$color.cardview_shadow_start_color);
        this.n = resources.getColor(R$color.cardview_shadow_end_color);
        this.a = resources.getDimensionPixelSize(R$dimen.cardview_compat_inset_shadow);
        this.b = new Paint(5);
        b(colorStateList);
        Paint paint = new Paint(5);
        this.c = paint;
        paint.setStyle(Paint.Style.FILL);
        this.f350f = (float) ((int) (f2 + 0.5f));
        this.e = new RectF();
        Paint paint2 = new Paint(this.c);
        this.d = paint2;
        paint2.setAntiAlias(false);
        a(f3, f4);
    }

    private void b(ColorStateList colorStateList) {
        if (colorStateList == null) {
            colorStateList = ColorStateList.valueOf(0);
        }
        this.k = colorStateList;
        this.b.setColor(colorStateList.getColorForState(getState(), this.k.getDefaultColor()));
    }

    private int d(float f2) {
        int i2 = (int) (f2 + 0.5f);
        return i2 % 2 == 1 ? i2 - 1 : i2;
    }

    private void g() {
        float f2 = this.f350f;
        RectF rectF = new RectF(-f2, -f2, f2, f2);
        RectF rectF2 = new RectF(rectF);
        float f3 = this.f353i;
        rectF2.inset(-f3, -f3);
        Path path = this.f351g;
        if (path == null) {
            this.f351g = new Path();
        } else {
            path.reset();
        }
        this.f351g.setFillType(Path.FillType.EVEN_ODD);
        this.f351g.moveTo(-this.f350f, 0.0f);
        this.f351g.rLineTo(-this.f353i, 0.0f);
        this.f351g.arcTo(rectF2, 180.0f, 90.0f, false);
        this.f351g.arcTo(rectF, 270.0f, -90.0f, false);
        this.f351g.close();
        float f4 = this.f350f;
        float f5 = f4 / (this.f353i + f4);
        Paint paint = this.c;
        float f6 = this.f350f + this.f353i;
        int i2 = this.m;
        paint.setShader(new RadialGradient(0.0f, 0.0f, f6, new int[]{i2, i2, this.n}, new float[]{0.0f, f5, 1.0f}, Shader.TileMode.CLAMP));
        Paint paint2 = this.d;
        float f7 = this.f350f;
        float f8 = this.f353i;
        int i3 = this.m;
        paint2.setShader(new LinearGradient(0.0f, (-f7) + f8, 0.0f, (-f7) - f8, new int[]{i3, i3, this.n}, new float[]{0.0f, 0.5f, 1.0f}, Shader.TileMode.CLAMP));
        this.d.setAntiAlias(false);
    }

    /* access modifiers changed from: package-private */
    public void a(boolean z) {
        this.o = z;
        invalidateSelf();
    }

    /* access modifiers changed from: package-private */
    public void c(float f2) {
        a(f2, this.f352h);
    }

    public void draw(Canvas canvas) {
        if (this.l) {
            b(getBounds());
            this.l = false;
        }
        canvas.translate(0.0f, this.f354j / 2.0f);
        a(canvas);
        canvas.translate(0.0f, (-this.f354j) / 2.0f);
        r.a(canvas, this.e, this.f350f, this.b);
    }

    /* access modifiers changed from: package-private */
    public float e() {
        float f2 = this.f352h;
        return (Math.max(f2, this.f350f + ((float) this.a) + (f2 / 2.0f)) * 2.0f) + ((this.f352h + ((float) this.a)) * 2.0f);
    }

    /* access modifiers changed from: package-private */
    public float f() {
        return this.f354j;
    }

    public int getOpacity() {
        return -3;
    }

    public boolean getPadding(Rect rect) {
        int ceil = (int) Math.ceil((double) b(this.f352h, this.f350f, this.o));
        int ceil2 = (int) Math.ceil((double) a(this.f352h, this.f350f, this.o));
        rect.set(ceil2, ceil, ceil2, ceil);
        return true;
    }

    public boolean isStateful() {
        ColorStateList colorStateList = this.k;
        return (colorStateList != null && colorStateList.isStateful()) || super.isStateful();
    }

    /* access modifiers changed from: protected */
    public void onBoundsChange(Rect rect) {
        super.onBoundsChange(rect);
        this.l = true;
    }

    /* access modifiers changed from: protected */
    public boolean onStateChange(int[] iArr) {
        ColorStateList colorStateList = this.k;
        int colorForState = colorStateList.getColorForState(iArr, colorStateList.getDefaultColor());
        if (this.b.getColor() == colorForState) {
            return false;
        }
        this.b.setColor(colorForState);
        this.l = true;
        invalidateSelf();
        return true;
    }

    public void setAlpha(int i2) {
        this.b.setAlpha(i2);
        this.c.setAlpha(i2);
        this.d.setAlpha(i2);
    }

    public void setColorFilter(ColorFilter colorFilter) {
        this.b.setColorFilter(colorFilter);
    }

    /* access modifiers changed from: package-private */
    public float c() {
        return this.f352h;
    }

    /* access modifiers changed from: package-private */
    public float d() {
        float f2 = this.f352h;
        return (Math.max(f2, this.f350f + ((float) this.a) + ((f2 * 1.5f) / 2.0f)) * 2.0f) + (((this.f352h * 1.5f) + ((float) this.a)) * 2.0f);
    }

    private void a(float f2, float f3) {
        if (f2 < 0.0f) {
            throw new IllegalArgumentException("Invalid shadow size " + f2 + ". Must be >= 0");
        } else if (f3 >= 0.0f) {
            float d2 = (float) d(f2);
            float d3 = (float) d(f3);
            if (d2 > d3) {
                if (!this.p) {
                    this.p = true;
                }
                d2 = d3;
            }
            if (this.f354j != d2 || this.f352h != d3) {
                this.f354j = d2;
                this.f352h = d3;
                this.f353i = (float) ((int) ((d2 * 1.5f) + ((float) this.a) + 0.5f));
                this.l = true;
                invalidateSelf();
            }
        } else {
            throw new IllegalArgumentException("Invalid max shadow size " + f3 + ". Must be >= 0");
        }
    }

    static float b(float f2, float f3, boolean z) {
        if (!z) {
            return f2 * 1.5f;
        }
        double d2 = (double) (f2 * 1.5f);
        double d3 = (double) f3;
        Double.isNaN(d3);
        Double.isNaN(d2);
        return (float) (d2 + ((1.0d - q) * d3));
    }

    private void b(Rect rect) {
        float f2 = this.f352h;
        float f3 = 1.5f * f2;
        this.e.set(((float) rect.left) + f2, ((float) rect.top) + f3, ((float) rect.right) - f2, ((float) rect.bottom) - f3);
        g();
    }

    /* access modifiers changed from: package-private */
    public float b() {
        return this.f350f;
    }

    /* access modifiers changed from: package-private */
    public void b(float f2) {
        a(this.f354j, f2);
    }

    static float a(float f2, float f3, boolean z) {
        if (!z) {
            return f2;
        }
        double d2 = (double) f2;
        double d3 = (double) f3;
        Double.isNaN(d3);
        Double.isNaN(d2);
        return (float) (d2 + ((1.0d - q) * d3));
    }

    /* access modifiers changed from: package-private */
    public void a(float f2) {
        if (f2 >= 0.0f) {
            float f3 = (float) ((int) (f2 + 0.5f));
            if (this.f350f != f3) {
                this.f350f = f3;
                this.l = true;
                invalidateSelf();
                return;
            }
            return;
        }
        throw new IllegalArgumentException("Invalid radius " + f2 + ". Must be >= 0");
    }

    private void a(Canvas canvas) {
        float f2 = this.f350f;
        float f3 = (-f2) - this.f353i;
        float f4 = f2 + ((float) this.a) + (this.f354j / 2.0f);
        float f5 = f4 * 2.0f;
        boolean z = this.e.width() - f5 > 0.0f;
        boolean z2 = this.e.height() - f5 > 0.0f;
        int save = canvas.save();
        RectF rectF = this.e;
        canvas.translate(rectF.left + f4, rectF.top + f4);
        canvas.drawPath(this.f351g, this.c);
        if (z) {
            canvas.drawRect(0.0f, f3, this.e.width() - f5, -this.f350f, this.d);
        }
        canvas.restoreToCount(save);
        int save2 = canvas.save();
        RectF rectF2 = this.e;
        canvas.translate(rectF2.right - f4, rectF2.bottom - f4);
        canvas.rotate(180.0f);
        canvas.drawPath(this.f351g, this.c);
        if (z) {
            canvas.drawRect(0.0f, f3, this.e.width() - f5, (-this.f350f) + this.f353i, this.d);
        }
        canvas.restoreToCount(save2);
        int save3 = canvas.save();
        RectF rectF3 = this.e;
        canvas.translate(rectF3.left + f4, rectF3.bottom - f4);
        canvas.rotate(270.0f);
        canvas.drawPath(this.f351g, this.c);
        if (z2) {
            canvas.drawRect(0.0f, f3, this.e.height() - f5, -this.f350f, this.d);
        }
        canvas.restoreToCount(save3);
        int save4 = canvas.save();
        RectF rectF4 = this.e;
        canvas.translate(rectF4.right - f4, rectF4.top + f4);
        canvas.rotate(90.0f);
        canvas.drawPath(this.f351g, this.c);
        if (z2) {
            canvas.drawRect(0.0f, f3, this.e.height() - f5, -this.f350f, this.d);
        }
        canvas.restoreToCount(save4);
    }

    /* access modifiers changed from: package-private */
    public void a(Rect rect) {
        getPadding(rect);
    }

    /* access modifiers changed from: package-private */
    public void a(ColorStateList colorStateList) {
        b(colorStateList);
        invalidateSelf();
    }

    /* access modifiers changed from: package-private */
    public ColorStateList a() {
        return this.k;
    }
}
