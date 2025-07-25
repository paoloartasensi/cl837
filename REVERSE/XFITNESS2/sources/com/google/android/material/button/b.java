package com.google.android.material.button;

import android.annotation.TargetApi;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.InsetDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Build;
import androidx.core.graphics.drawable.a;
import androidx.core.h.v;
import com.google.android.material.R$styleable;
import com.google.android.material.internal.l;

/* compiled from: MaterialButtonHelper */
class b {
    private static final boolean w = (Build.VERSION.SDK_INT >= 21);
    private final MaterialButton a;
    private int b;
    private int c;
    private int d;
    private int e;

    /* renamed from: f  reason: collision with root package name */
    private int f1444f;

    /* renamed from: g  reason: collision with root package name */
    private int f1445g;

    /* renamed from: h  reason: collision with root package name */
    private PorterDuff.Mode f1446h;

    /* renamed from: i  reason: collision with root package name */
    private ColorStateList f1447i;

    /* renamed from: j  reason: collision with root package name */
    private ColorStateList f1448j;
    private ColorStateList k;
    private final Paint l = new Paint(1);
    private final Rect m = new Rect();
    private final RectF n = new RectF();
    private GradientDrawable o;
    private Drawable p;
    private GradientDrawable q;
    private Drawable r;
    private GradientDrawable s;
    private GradientDrawable t;
    private GradientDrawable u;
    private boolean v = false;

    public b(MaterialButton materialButton) {
        this.a = materialButton;
    }

    private Drawable i() {
        GradientDrawable gradientDrawable = new GradientDrawable();
        this.o = gradientDrawable;
        gradientDrawable.setCornerRadius(((float) this.f1444f) + 1.0E-5f);
        this.o.setColor(-1);
        Drawable i2 = a.i(this.o);
        this.p = i2;
        a.a(i2, this.f1447i);
        PorterDuff.Mode mode = this.f1446h;
        if (mode != null) {
            a.a(this.p, mode);
        }
        GradientDrawable gradientDrawable2 = new GradientDrawable();
        this.q = gradientDrawable2;
        gradientDrawable2.setCornerRadius(((float) this.f1444f) + 1.0E-5f);
        this.q.setColor(-1);
        Drawable i3 = a.i(this.q);
        this.r = i3;
        a.a(i3, this.k);
        return a((Drawable) new LayerDrawable(new Drawable[]{this.p, this.r}));
    }

    @TargetApi(21)
    private Drawable j() {
        GradientDrawable gradientDrawable = new GradientDrawable();
        this.s = gradientDrawable;
        gradientDrawable.setCornerRadius(((float) this.f1444f) + 1.0E-5f);
        this.s.setColor(-1);
        n();
        GradientDrawable gradientDrawable2 = new GradientDrawable();
        this.t = gradientDrawable2;
        gradientDrawable2.setCornerRadius(((float) this.f1444f) + 1.0E-5f);
        this.t.setColor(0);
        this.t.setStroke(this.f1445g, this.f1448j);
        InsetDrawable a2 = a((Drawable) new LayerDrawable(new Drawable[]{this.s, this.t}));
        GradientDrawable gradientDrawable3 = new GradientDrawable();
        this.u = gradientDrawable3;
        gradientDrawable3.setCornerRadius(((float) this.f1444f) + 1.0E-5f);
        this.u.setColor(-1);
        return new a(com.google.android.material.g.a.a(this.k), a2, this.u);
    }

    private GradientDrawable k() {
        if (!w || this.a.getBackground() == null) {
            return null;
        }
        return (GradientDrawable) ((LayerDrawable) ((InsetDrawable) ((RippleDrawable) this.a.getBackground()).getDrawable(0)).getDrawable()).getDrawable(0);
    }

    private GradientDrawable l() {
        if (!w || this.a.getBackground() == null) {
            return null;
        }
        return (GradientDrawable) ((LayerDrawable) ((InsetDrawable) ((RippleDrawable) this.a.getBackground()).getDrawable(0)).getDrawable()).getDrawable(1);
    }

    private void m() {
        if (w && this.t != null) {
            this.a.setInternalBackground(j());
        } else if (!w) {
            this.a.invalidate();
        }
    }

    private void n() {
        GradientDrawable gradientDrawable = this.s;
        if (gradientDrawable != null) {
            a.a((Drawable) gradientDrawable, this.f1447i);
            PorterDuff.Mode mode = this.f1446h;
            if (mode != null) {
                a.a((Drawable) this.s, mode);
            }
        }
    }

    public void a(TypedArray typedArray) {
        int i2 = 0;
        this.b = typedArray.getDimensionPixelOffset(R$styleable.MaterialButton_android_insetLeft, 0);
        this.c = typedArray.getDimensionPixelOffset(R$styleable.MaterialButton_android_insetRight, 0);
        this.d = typedArray.getDimensionPixelOffset(R$styleable.MaterialButton_android_insetTop, 0);
        this.e = typedArray.getDimensionPixelOffset(R$styleable.MaterialButton_android_insetBottom, 0);
        this.f1444f = typedArray.getDimensionPixelSize(R$styleable.MaterialButton_cornerRadius, 0);
        this.f1445g = typedArray.getDimensionPixelSize(R$styleable.MaterialButton_strokeWidth, 0);
        this.f1446h = l.a(typedArray.getInt(R$styleable.MaterialButton_backgroundTintMode, -1), PorterDuff.Mode.SRC_IN);
        this.f1447i = com.google.android.material.f.a.a(this.a.getContext(), typedArray, R$styleable.MaterialButton_backgroundTint);
        this.f1448j = com.google.android.material.f.a.a(this.a.getContext(), typedArray, R$styleable.MaterialButton_strokeColor);
        this.k = com.google.android.material.f.a.a(this.a.getContext(), typedArray, R$styleable.MaterialButton_rippleColor);
        this.l.setStyle(Paint.Style.STROKE);
        this.l.setStrokeWidth((float) this.f1445g);
        Paint paint = this.l;
        ColorStateList colorStateList = this.f1448j;
        if (colorStateList != null) {
            i2 = colorStateList.getColorForState(this.a.getDrawableState(), 0);
        }
        paint.setColor(i2);
        int t2 = v.t(this.a);
        int paddingTop = this.a.getPaddingTop();
        int s2 = v.s(this.a);
        int paddingBottom = this.a.getPaddingBottom();
        this.a.setInternalBackground(w ? j() : i());
        v.b(this.a, t2 + this.b, paddingTop + this.d, s2 + this.c, paddingBottom + this.e);
    }

    /* access modifiers changed from: package-private */
    public ColorStateList b() {
        return this.k;
    }

    /* access modifiers changed from: package-private */
    public void c(ColorStateList colorStateList) {
        if (this.f1447i != colorStateList) {
            this.f1447i = colorStateList;
            if (w) {
                n();
                return;
            }
            Drawable drawable = this.p;
            if (drawable != null) {
                a.a(drawable, colorStateList);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public int d() {
        return this.f1445g;
    }

    /* access modifiers changed from: package-private */
    public ColorStateList e() {
        return this.f1447i;
    }

    /* access modifiers changed from: package-private */
    public PorterDuff.Mode f() {
        return this.f1446h;
    }

    /* access modifiers changed from: package-private */
    public boolean g() {
        return this.v;
    }

    /* access modifiers changed from: package-private */
    public void h() {
        this.v = true;
        this.a.setSupportBackgroundTintList(this.f1447i);
        this.a.setSupportBackgroundTintMode(this.f1446h);
    }

    /* access modifiers changed from: package-private */
    public void b(ColorStateList colorStateList) {
        if (this.f1448j != colorStateList) {
            this.f1448j = colorStateList;
            Paint paint = this.l;
            int i2 = 0;
            if (colorStateList != null) {
                i2 = colorStateList.getColorForState(this.a.getDrawableState(), 0);
            }
            paint.setColor(i2);
            m();
        }
    }

    /* access modifiers changed from: package-private */
    public ColorStateList c() {
        return this.f1448j;
    }

    /* access modifiers changed from: package-private */
    public void b(int i2) {
        GradientDrawable gradientDrawable;
        if (this.f1444f != i2) {
            this.f1444f = i2;
            if (w && this.s != null && this.t != null && this.u != null) {
                if (Build.VERSION.SDK_INT == 21) {
                    float f2 = ((float) i2) + 1.0E-5f;
                    k().setCornerRadius(f2);
                    l().setCornerRadius(f2);
                }
                float f3 = ((float) i2) + 1.0E-5f;
                this.s.setCornerRadius(f3);
                this.t.setCornerRadius(f3);
                this.u.setCornerRadius(f3);
            } else if (!w && (gradientDrawable = this.o) != null && this.q != null) {
                float f4 = ((float) i2) + 1.0E-5f;
                gradientDrawable.setCornerRadius(f4);
                this.q.setCornerRadius(f4);
                this.a.invalidate();
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void c(int i2) {
        if (this.f1445g != i2) {
            this.f1445g = i2;
            this.l.setStrokeWidth((float) i2);
            m();
        }
    }

    /* access modifiers changed from: package-private */
    public void a(Canvas canvas) {
        if (canvas != null && this.f1448j != null && this.f1445g > 0) {
            this.m.set(this.a.getBackground().getBounds());
            RectF rectF = this.n;
            Rect rect = this.m;
            int i2 = this.f1445g;
            rectF.set(((float) rect.left) + (((float) i2) / 2.0f) + ((float) this.b), ((float) rect.top) + (((float) i2) / 2.0f) + ((float) this.d), (((float) rect.right) - (((float) i2) / 2.0f)) - ((float) this.c), (((float) rect.bottom) - (((float) i2) / 2.0f)) - ((float) this.e));
            float f2 = ((float) this.f1444f) - (((float) this.f1445g) / 2.0f);
            canvas.drawRoundRect(this.n, f2, f2, this.l);
        }
    }

    private InsetDrawable a(Drawable drawable) {
        return new InsetDrawable(drawable, this.b, this.d, this.c, this.e);
    }

    /* access modifiers changed from: package-private */
    public void a(PorterDuff.Mode mode) {
        if (this.f1446h != mode) {
            this.f1446h = mode;
            if (w) {
                n();
                return;
            }
            Drawable drawable = this.p;
            if (drawable != null && mode != null) {
                a.a(drawable, mode);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void a(int i2, int i3) {
        GradientDrawable gradientDrawable = this.u;
        if (gradientDrawable != null) {
            gradientDrawable.setBounds(this.b, this.d, i3 - this.c, i2 - this.e);
        }
    }

    /* access modifiers changed from: package-private */
    public void a(int i2) {
        GradientDrawable gradientDrawable;
        GradientDrawable gradientDrawable2;
        if (w && (gradientDrawable2 = this.s) != null) {
            gradientDrawable2.setColor(i2);
        } else if (!w && (gradientDrawable = this.o) != null) {
            gradientDrawable.setColor(i2);
        }
    }

    /* access modifiers changed from: package-private */
    public void a(ColorStateList colorStateList) {
        Drawable drawable;
        if (this.k != colorStateList) {
            this.k = colorStateList;
            if (w && (this.a.getBackground() instanceof RippleDrawable)) {
                ((RippleDrawable) this.a.getBackground()).setColor(colorStateList);
            } else if (!w && (drawable = this.r) != null) {
                a.a(drawable, colorStateList);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public int a() {
        return this.f1444f;
    }
}
