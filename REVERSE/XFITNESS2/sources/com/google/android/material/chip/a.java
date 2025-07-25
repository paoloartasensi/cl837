package com.google.android.material.chip;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import androidx.core.content.c.f;
import com.google.android.material.R$styleable;
import com.google.android.material.a.h;
import com.google.android.material.internal.k;
import com.jeremyliao.liveeventbus.BuildConfig;
import java.lang.ref.WeakReference;
import java.util.Arrays;

/* compiled from: ChipDrawable */
public class a extends Drawable implements androidx.core.graphics.drawable.b, Drawable.Callback {
    private static final int[] l0 = {16842910};
    private h A;
    private h B;
    private float C;
    private float D;
    private float E;
    private float F;
    private float G;
    private float H;
    private float I;
    private float J;
    private final Context K;
    private final TextPaint L = new TextPaint(1);
    private final Paint M = new Paint(1);
    private final Paint N;
    private final Paint.FontMetrics O = new Paint.FontMetrics();
    private final RectF P = new RectF();
    private final PointF Q = new PointF();
    private int R;
    private int S;
    private int T;
    private int U;
    private boolean V;
    private int W;
    private int X = 255;
    private ColorFilter Y;
    private PorterDuffColorFilter Z;
    private ColorStateList a0;
    private PorterDuff.Mode b0 = PorterDuff.Mode.SRC_IN;
    private int[] c0;
    private boolean d0;
    private ColorStateList e;
    private ColorStateList e0;

    /* renamed from: f  reason: collision with root package name */
    private float f1456f;
    private WeakReference<b> f0;

    /* renamed from: g  reason: collision with root package name */
    private float f1457g;
    /* access modifiers changed from: private */
    public boolean g0;

    /* renamed from: h  reason: collision with root package name */
    private ColorStateList f1458h;
    private float h0;

    /* renamed from: i  reason: collision with root package name */
    private float f1459i;
    private TextUtils.TruncateAt i0;

    /* renamed from: j  reason: collision with root package name */
    private ColorStateList f1460j;
    private boolean j0;
    private CharSequence k;
    private int k0;
    private CharSequence l;
    private com.google.android.material.f.b m;
    private final f.a n = new C0076a();
    private boolean o;
    private Drawable p;
    private ColorStateList q;
    private float r;
    private boolean s;
    private Drawable t;
    private ColorStateList u;
    private float v;
    private CharSequence w;
    private boolean x;
    private boolean y;
    private Drawable z;

    /* renamed from: com.google.android.material.chip.a$a  reason: collision with other inner class name */
    /* compiled from: ChipDrawable */
    class C0076a extends f.a {
        C0076a() {
        }

        public void a(int i2) {
        }

        public void a(Typeface typeface) {
            boolean unused = a.this.g0 = true;
            a.this.I();
            a.this.invalidateSelf();
        }
    }

    /* compiled from: ChipDrawable */
    public interface b {
        void a();
    }

    private a(Context context) {
        Paint paint = null;
        this.f0 = new WeakReference<>(paint);
        this.g0 = true;
        this.K = context;
        this.k = BuildConfig.FLAVOR;
        this.L.density = context.getResources().getDisplayMetrics().density;
        this.N = paint;
        if (paint != null) {
            paint.setStyle(Paint.Style.STROKE);
        }
        setState(l0);
        a(l0);
        this.j0 = true;
    }

    private float K() {
        if (R()) {
            return this.H + this.v + this.I;
        }
        return 0.0f;
    }

    private float L() {
        this.L.getFontMetrics(this.O);
        Paint.FontMetrics fontMetrics = this.O;
        return (fontMetrics.descent + fontMetrics.ascent) / 2.0f;
    }

    private boolean M() {
        return this.y && this.z != null && this.x;
    }

    private float N() {
        if (!this.g0) {
            return this.h0;
        }
        float c = c(this.l);
        this.h0 = c;
        this.g0 = false;
        return c;
    }

    private ColorFilter O() {
        ColorFilter colorFilter = this.Y;
        return colorFilter != null ? colorFilter : this.Z;
    }

    private boolean P() {
        return this.y && this.z != null && this.V;
    }

    private boolean Q() {
        return this.o && this.p != null;
    }

    private boolean R() {
        return this.s && this.t != null;
    }

    private void S() {
        this.e0 = this.d0 ? com.google.android.material.g.a.a(this.f1460j) : null;
    }

    private void b(Canvas canvas, Rect rect) {
        this.M.setColor(this.R);
        this.M.setStyle(Paint.Style.FILL);
        this.M.setColorFilter(O());
        this.P.set(rect);
        RectF rectF = this.P;
        float f2 = this.f1457g;
        canvas.drawRoundRect(rectF, f2, f2, this.M);
    }

    private float c(CharSequence charSequence) {
        if (charSequence == null) {
            return 0.0f;
        }
        return this.L.measureText(charSequence, 0, charSequence.length());
    }

    private void d(Canvas canvas, Rect rect) {
        if (this.f1459i > 0.0f) {
            this.M.setColor(this.S);
            this.M.setStyle(Paint.Style.STROKE);
            this.M.setColorFilter(O());
            RectF rectF = this.P;
            float f2 = this.f1459i;
            rectF.set(((float) rect.left) + (f2 / 2.0f), ((float) rect.top) + (f2 / 2.0f), ((float) rect.right) - (f2 / 2.0f), ((float) rect.bottom) - (f2 / 2.0f));
            float f3 = this.f1457g - (this.f1459i / 2.0f);
            canvas.drawRoundRect(this.P, f3, f3, this.M);
        }
    }

    private void e(Canvas canvas, Rect rect) {
        if (R()) {
            c(rect, this.P);
            RectF rectF = this.P;
            float f2 = rectF.left;
            float f3 = rectF.top;
            canvas.translate(f2, f3);
            this.t.setBounds(0, 0, (int) this.P.width(), (int) this.P.height());
            this.t.draw(canvas);
            canvas.translate(-f2, -f3);
        }
    }

    private void g(Canvas canvas, Rect rect) {
        Paint paint = this.N;
        if (paint != null) {
            paint.setColor(androidx.core.a.a.c(-16777216, 127));
            canvas.drawRect(rect, this.N);
            if (Q() || P()) {
                a(rect, this.P);
                canvas.drawRect(this.P, this.N);
            }
            if (this.l != null) {
                canvas.drawLine((float) rect.left, rect.exactCenterY(), (float) rect.right, rect.exactCenterY(), this.N);
            }
            if (R()) {
                c(rect, this.P);
                canvas.drawRect(this.P, this.N);
            }
            this.N.setColor(androidx.core.a.a.c(-65536, 127));
            b(rect, this.P);
            canvas.drawRect(this.P, this.N);
            this.N.setColor(androidx.core.a.a.c(-16711936, 127));
            d(rect, this.P);
            canvas.drawRect(this.P, this.N);
        }
    }

    private void h(Canvas canvas, Rect rect) {
        if (this.l != null) {
            Paint.Align a = a(rect, this.Q);
            e(rect, this.P);
            if (this.m != null) {
                this.L.drawableState = getState();
                this.m.b(this.K, this.L, this.n);
            }
            this.L.setTextAlign(a);
            int i2 = 0;
            boolean z2 = Math.round(N()) > Math.round(this.P.width());
            if (z2) {
                i2 = canvas.save();
                canvas.clipRect(this.P);
            }
            CharSequence charSequence = this.l;
            if (z2 && this.i0 != null) {
                charSequence = TextUtils.ellipsize(charSequence, this.L, this.P.width(), this.i0);
            }
            CharSequence charSequence2 = charSequence;
            int length = charSequence2.length();
            PointF pointF = this.Q;
            canvas.drawText(charSequence2, 0, length, pointF.x, pointF.y, this.L);
            if (z2) {
                canvas.restoreToCount(i2);
            }
        }
    }

    public com.google.android.material.f.b A() {
        return this.m;
    }

    public float B() {
        return this.G;
    }

    public float C() {
        return this.F;
    }

    public boolean D() {
        return this.x;
    }

    public boolean E() {
        return this.y;
    }

    public boolean F() {
        return this.o;
    }

    public boolean G() {
        return e(this.t);
    }

    public boolean H() {
        return this.s;
    }

    /* access modifiers changed from: protected */
    public void I() {
        b bVar = (b) this.f0.get();
        if (bVar != null) {
            bVar.a();
        }
    }

    /* access modifiers changed from: package-private */
    public boolean J() {
        return this.j0;
    }

    public void draw(Canvas canvas) {
        Rect bounds = getBounds();
        if (!bounds.isEmpty() && getAlpha() != 0) {
            int i2 = 0;
            int i3 = this.X;
            if (i3 < 255) {
                i2 = com.google.android.material.b.a.a(canvas, (float) bounds.left, (float) bounds.top, (float) bounds.right, (float) bounds.bottom, i3);
            }
            b(canvas, bounds);
            d(canvas, bounds);
            f(canvas, bounds);
            c(canvas, bounds);
            a(canvas, bounds);
            if (this.j0) {
                h(canvas, bounds);
            }
            e(canvas, bounds);
            g(canvas, bounds);
            if (this.X < 255) {
                canvas.restoreToCount(i2);
            }
        }
    }

    public void f(boolean z2) {
        if (this.d0 != z2) {
            this.d0 = z2;
            S();
            onStateChange(getState());
        }
    }

    public int getAlpha() {
        return this.X;
    }

    public ColorFilter getColorFilter() {
        return this.Y;
    }

    public int getIntrinsicHeight() {
        return (int) this.f1456f;
    }

    public int getIntrinsicWidth() {
        return Math.min(Math.round(this.C + a() + this.F + N() + this.G + K() + this.J), this.k0);
    }

    public int getOpacity() {
        return -3;
    }

    @TargetApi(21)
    public void getOutline(Outline outline) {
        Rect bounds = getBounds();
        if (!bounds.isEmpty()) {
            outline.setRoundRect(bounds, this.f1457g);
        } else {
            outline.setRoundRect(0, 0, getIntrinsicWidth(), getIntrinsicHeight(), this.f1457g);
        }
        outline.setAlpha(((float) getAlpha()) / 255.0f);
    }

    public float i() {
        return this.f1456f;
    }

    public void invalidateDrawable(Drawable drawable) {
        Drawable.Callback callback = getCallback();
        if (callback != null) {
            callback.invalidateDrawable(this);
        }
    }

    public boolean isStateful() {
        return f(this.e) || f(this.f1458h) || (this.d0 && f(this.e0)) || b(this.m) || M() || e(this.p) || e(this.z) || f(this.a0);
    }

    public void j(int i2) {
        c(this.K.getResources().getBoolean(i2));
    }

    public void k(int i2) {
        d(this.K.getResources().getDimension(i2));
    }

    public float l() {
        return this.f1459i;
    }

    public void m(int i2) {
        c(androidx.appcompat.a.a.a.b(this.K, i2));
    }

    public void n(int i2) {
        f(this.K.getResources().getDimension(i2));
    }

    public float o() {
        return this.I;
    }

    @TargetApi(23)
    public boolean onLayoutDirectionChanged(int i2) {
        boolean onLayoutDirectionChanged = super.onLayoutDirectionChanged(i2);
        if (Q()) {
            onLayoutDirectionChanged |= this.p.setLayoutDirection(i2);
        }
        if (P()) {
            onLayoutDirectionChanged |= this.z.setLayoutDirection(i2);
        }
        if (R()) {
            onLayoutDirectionChanged |= this.t.setLayoutDirection(i2);
        }
        if (!onLayoutDirectionChanged) {
            return true;
        }
        invalidateSelf();
        return true;
    }

    /* access modifiers changed from: protected */
    public boolean onLevelChange(int i2) {
        boolean onLevelChange = super.onLevelChange(i2);
        if (Q()) {
            onLevelChange |= this.p.setLevel(i2);
        }
        if (P()) {
            onLevelChange |= this.z.setLevel(i2);
        }
        if (R()) {
            onLevelChange |= this.t.setLevel(i2);
        }
        if (onLevelChange) {
            invalidateSelf();
        }
        return onLevelChange;
    }

    /* access modifiers changed from: protected */
    public boolean onStateChange(int[] iArr) {
        return a(iArr, r());
    }

    public void p(int i2) {
        c(androidx.appcompat.a.a.a.c(this.K, i2));
    }

    public void q(int i2) {
        h(this.K.getResources().getDimension(i2));
    }

    public int[] r() {
        return this.c0;
    }

    public ColorStateList s() {
        return this.u;
    }

    public void scheduleDrawable(Drawable drawable, Runnable runnable, long j2) {
        Drawable.Callback callback = getCallback();
        if (callback != null) {
            callback.scheduleDrawable(this, runnable, j2);
        }
    }

    public void setAlpha(int i2) {
        if (this.X != i2) {
            this.X = i2;
            invalidateSelf();
        }
    }

    public void setColorFilter(ColorFilter colorFilter) {
        if (this.Y != colorFilter) {
            this.Y = colorFilter;
            invalidateSelf();
        }
    }

    public void setTintList(ColorStateList colorStateList) {
        if (this.a0 != colorStateList) {
            this.a0 = colorStateList;
            onStateChange(getState());
        }
    }

    public void setTintMode(PorterDuff.Mode mode) {
        if (this.b0 != mode) {
            this.b0 = mode;
            this.Z = com.google.android.material.c.a.a(this, this.a0, mode);
            invalidateSelf();
        }
    }

    public boolean setVisible(boolean z2, boolean z3) {
        boolean visible = super.setVisible(z2, z3);
        if (Q()) {
            visible |= this.p.setVisible(z2, z3);
        }
        if (P()) {
            visible |= this.z.setVisible(z2, z3);
        }
        if (R()) {
            visible |= this.t.setVisible(z2, z3);
        }
        if (visible) {
            invalidateSelf();
        }
        return visible;
    }

    public TextUtils.TruncateAt t() {
        return this.i0;
    }

    public h u() {
        return this.B;
    }

    public void unscheduleDrawable(Drawable drawable, Runnable runnable) {
        Drawable.Callback callback = getCallback();
        if (callback != null) {
            callback.unscheduleDrawable(this, runnable);
        }
    }

    public float v() {
        return this.E;
    }

    public float w() {
        return this.D;
    }

    public ColorStateList x() {
        return this.f1460j;
    }

    public void y(int i2) {
        e(androidx.appcompat.a.a.a.b(this.K, i2));
    }

    public CharSequence z() {
        return this.k;
    }

    public static a a(Context context, AttributeSet attributeSet, int i2, int i3) {
        a aVar = new a(context);
        aVar.a(attributeSet, i2, i3);
        return aVar;
    }

    private void c(Canvas canvas, Rect rect) {
        if (Q()) {
            a(rect, this.P);
            RectF rectF = this.P;
            float f2 = rectF.left;
            float f3 = rectF.top;
            canvas.translate(f2, f3);
            this.p.setBounds(0, 0, (int) this.P.width(), (int) this.P.height());
            this.p.draw(canvas);
            canvas.translate(-f2, -f3);
        }
    }

    public void A(int i2) {
        a(new com.google.android.material.f.b(this.K, i2));
    }

    public void B(int i2) {
        l(this.K.getResources().getDimension(i2));
    }

    public void C(int i2) {
        m(this.K.getResources().getDimension(i2));
    }

    public void i(int i2) {
        b(androidx.appcompat.a.a.a.b(this.K, i2));
    }

    public float j() {
        return this.C;
    }

    public ColorStateList k() {
        return this.f1458h;
    }

    public void l(int i2) {
        e(this.K.getResources().getDimension(i2));
    }

    public Drawable m() {
        Drawable drawable = this.t;
        if (drawable != null) {
            return androidx.core.graphics.drawable.a.h(drawable);
        }
        return null;
    }

    public CharSequence n() {
        return this.w;
    }

    public void o(int i2) {
        g(this.K.getResources().getDimension(i2));
    }

    public float p() {
        return this.v;
    }

    public float q() {
        return this.H;
    }

    public void r(int i2) {
        i(this.K.getResources().getDimension(i2));
    }

    public void s(int i2) {
        d(androidx.appcompat.a.a.a.b(this.K, i2));
    }

    public void t(int i2) {
        d(this.K.getResources().getBoolean(i2));
    }

    public void u(int i2) {
        a(h.a(this.K, i2));
    }

    public void v(int i2) {
        j(this.K.getResources().getDimension(i2));
    }

    public void w(int i2) {
        k(this.K.getResources().getDimension(i2));
    }

    public void x(int i2) {
        this.k0 = i2;
    }

    public h y() {
        return this.A;
    }

    public void z(int i2) {
        b(h.a(this.K, i2));
    }

    public void i(float f2) {
        if (this.H != f2) {
            this.H = f2;
            invalidateSelf();
            if (R()) {
                I();
            }
        }
    }

    public void j(float f2) {
        if (this.E != f2) {
            float a = a();
            this.E = f2;
            float a2 = a();
            invalidateSelf();
            if (a != a2) {
                I();
            }
        }
    }

    public void k(float f2) {
        if (this.D != f2) {
            float a = a();
            this.D = f2;
            float a2 = a();
            invalidateSelf();
            if (a != a2) {
                I();
            }
        }
    }

    public void l(float f2) {
        if (this.G != f2) {
            this.G = f2;
            invalidateSelf();
            I();
        }
    }

    public void m(float f2) {
        if (this.F != f2) {
            this.F = f2;
            invalidateSelf();
            I();
        }
    }

    private void a(AttributeSet attributeSet, int i2, int i3) {
        TypedArray c = k.c(this.K, attributeSet, R$styleable.Chip, i2, i3, new int[0]);
        a(com.google.android.material.f.a.a(this.K, c, R$styleable.Chip_chipBackgroundColor));
        d(c.getDimension(R$styleable.Chip_chipMinHeight, 0.0f));
        a(c.getDimension(R$styleable.Chip_chipCornerRadius, 0.0f));
        c(com.google.android.material.f.a.a(this.K, c, R$styleable.Chip_chipStrokeColor));
        f(c.getDimension(R$styleable.Chip_chipStrokeWidth, 0.0f));
        e(com.google.android.material.f.a.a(this.K, c, R$styleable.Chip_rippleColor));
        b(c.getText(R$styleable.Chip_android_text));
        a(com.google.android.material.f.a.c(this.K, c, R$styleable.Chip_android_textAppearance));
        int i4 = c.getInt(R$styleable.Chip_android_ellipsize, 0);
        if (i4 == 1) {
            a(TextUtils.TruncateAt.START);
        } else if (i4 == 2) {
            a(TextUtils.TruncateAt.MIDDLE);
        } else if (i4 == 3) {
            a(TextUtils.TruncateAt.END);
        }
        c(c.getBoolean(R$styleable.Chip_chipIconVisible, false));
        if (!(attributeSet == null || attributeSet.getAttributeValue("http://schemas.android.com/apk/res-auto", "chipIconEnabled") == null || attributeSet.getAttributeValue("http://schemas.android.com/apk/res-auto", "chipIconVisible") != null)) {
            c(c.getBoolean(R$styleable.Chip_chipIconEnabled, false));
        }
        b(com.google.android.material.f.a.b(this.K, c, R$styleable.Chip_chipIcon));
        b(com.google.android.material.f.a.a(this.K, c, R$styleable.Chip_chipIconTint));
        c(c.getDimension(R$styleable.Chip_chipIconSize, 0.0f));
        d(c.getBoolean(R$styleable.Chip_closeIconVisible, false));
        if (!(attributeSet == null || attributeSet.getAttributeValue("http://schemas.android.com/apk/res-auto", "closeIconEnabled") == null || attributeSet.getAttributeValue("http://schemas.android.com/apk/res-auto", "closeIconVisible") != null)) {
            d(c.getBoolean(R$styleable.Chip_closeIconEnabled, false));
        }
        c(com.google.android.material.f.a.b(this.K, c, R$styleable.Chip_closeIcon));
        d(com.google.android.material.f.a.a(this.K, c, R$styleable.Chip_closeIconTint));
        h(c.getDimension(R$styleable.Chip_closeIconSize, 0.0f));
        a(c.getBoolean(R$styleable.Chip_android_checkable, false));
        b(c.getBoolean(R$styleable.Chip_checkedIconVisible, false));
        if (!(attributeSet == null || attributeSet.getAttributeValue("http://schemas.android.com/apk/res-auto", "checkedIconEnabled") == null || attributeSet.getAttributeValue("http://schemas.android.com/apk/res-auto", "checkedIconVisible") != null)) {
            b(c.getBoolean(R$styleable.Chip_checkedIconEnabled, false));
        }
        a(com.google.android.material.f.a.b(this.K, c, R$styleable.Chip_checkedIcon));
        b(h.a(this.K, c, R$styleable.Chip_showMotionSpec));
        a(h.a(this.K, c, R$styleable.Chip_hideMotionSpec));
        e(c.getDimension(R$styleable.Chip_chipStartPadding, 0.0f));
        k(c.getDimension(R$styleable.Chip_iconStartPadding, 0.0f));
        j(c.getDimension(R$styleable.Chip_iconEndPadding, 0.0f));
        m(c.getDimension(R$styleable.Chip_textStartPadding, 0.0f));
        l(c.getDimension(R$styleable.Chip_textEndPadding, 0.0f));
        i(c.getDimension(R$styleable.Chip_closeIconStartPadding, 0.0f));
        g(c.getDimension(R$styleable.Chip_closeIconEndPadding, 0.0f));
        b(c.getDimension(R$styleable.Chip_chipEndPadding, 0.0f));
        x(c.getDimensionPixelSize(R$styleable.Chip_android_maxWidth, Integer.MAX_VALUE));
        c.recycle();
    }

    private void f(Canvas canvas, Rect rect) {
        this.M.setColor(this.T);
        this.M.setStyle(Paint.Style.FILL);
        this.P.set(rect);
        RectF rectF = this.P;
        float f2 = this.f1457g;
        canvas.drawRoundRect(rectF, f2, f2, this.M);
    }

    private void b(Rect rect, RectF rectF) {
        rectF.set(rect);
        if (R()) {
            float f2 = this.J + this.I + this.v + this.H + this.G;
            if (androidx.core.graphics.drawable.a.e(this) == 0) {
                rectF.right = ((float) rect.right) - f2;
            } else {
                rectF.left = ((float) rect.left) + f2;
            }
        }
    }

    private void d(Rect rect, RectF rectF) {
        rectF.setEmpty();
        if (R()) {
            float f2 = this.J + this.I + this.v + this.H + this.G;
            if (androidx.core.graphics.drawable.a.e(this) == 0) {
                float f3 = (float) rect.right;
                rectF.right = f3;
                rectF.left = f3 - f2;
            } else {
                int i2 = rect.left;
                rectF.left = (float) i2;
                rectF.right = ((float) i2) + f2;
            }
            rectF.top = (float) rect.top;
            rectF.bottom = (float) rect.bottom;
        }
    }

    private void e(Rect rect, RectF rectF) {
        rectF.setEmpty();
        if (this.l != null) {
            float a = this.C + a() + this.F;
            float K2 = this.J + K() + this.G;
            if (androidx.core.graphics.drawable.a.e(this) == 0) {
                rectF.left = ((float) rect.left) + a;
                rectF.right = ((float) rect.right) - K2;
            } else {
                rectF.left = ((float) rect.left) + K2;
                rectF.right = ((float) rect.right) - a;
            }
            rectF.top = (float) rect.top;
            rectF.bottom = (float) rect.bottom;
        }
    }

    private static boolean f(ColorStateList colorStateList) {
        return colorStateList != null && colorStateList.isStateful();
    }

    private void c(Rect rect, RectF rectF) {
        rectF.setEmpty();
        if (R()) {
            float f2 = this.J + this.I;
            if (androidx.core.graphics.drawable.a.e(this) == 0) {
                float f3 = ((float) rect.right) - f2;
                rectF.right = f3;
                rectF.left = f3 - this.v;
            } else {
                float f4 = ((float) rect.left) + f2;
                rectF.left = f4;
                rectF.right = f4 + this.v;
            }
            float exactCenterY = rect.exactCenterY();
            float f5 = this.v;
            float f6 = exactCenterY - (f5 / 2.0f);
            rectF.top = f6;
            rectF.bottom = f6 + f5;
        }
    }

    private void f(Drawable drawable) {
        if (drawable != null) {
            drawable.setCallback((Drawable.Callback) null);
        }
    }

    public void f(float f2) {
        if (this.f1459i != f2) {
            this.f1459i = f2;
            this.M.setStrokeWidth(f2);
            invalidateSelf();
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:1:0x0002, code lost:
        r0 = r0.b;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static boolean b(com.google.android.material.f.b r0) {
        /*
            if (r0 == 0) goto L_0x000e
            android.content.res.ColorStateList r0 = r0.b
            if (r0 == 0) goto L_0x000e
            boolean r0 = r0.isStateful()
            if (r0 == 0) goto L_0x000e
            r0 = 1
            goto L_0x000f
        L_0x000e:
            r0 = 0
        L_0x000f:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.material.chip.a.b(com.google.android.material.f.b):boolean");
    }

    public void b(CharSequence charSequence) {
        if (charSequence == null) {
            charSequence = BuildConfig.FLAVOR;
        }
        if (this.k != charSequence) {
            this.k = charSequence;
            this.l = androidx.core.f.a.b().a(charSequence);
            this.g0 = true;
            invalidateSelf();
            I();
        }
    }

    public Drawable f() {
        Drawable drawable = this.p;
        if (drawable != null) {
            return androidx.core.graphics.drawable.a.h(drawable);
        }
        return null;
    }

    public void f(int i2) {
        b(this.K.getResources().getDimension(i2));
    }

    public ColorStateList h() {
        return this.q;
    }

    public void h(int i2) {
        c(this.K.getResources().getDimension(i2));
    }

    private void d(Drawable drawable) {
        if (drawable != null) {
            drawable.setCallback(this);
            androidx.core.graphics.drawable.a.a(drawable, androidx.core.graphics.drawable.a.e(this));
            drawable.setLevel(getLevel());
            drawable.setVisible(isVisible(), false);
            if (drawable == this.t) {
                if (drawable.isStateful()) {
                    drawable.setState(r());
                }
                androidx.core.graphics.drawable.a.a(drawable, this.u);
            } else if (drawable.isStateful()) {
                drawable.setState(getState());
            }
        }
    }

    public void h(float f2) {
        if (this.v != f2) {
            this.v = f2;
            invalidateSelf();
            if (R()) {
                I();
            }
        }
    }

    private static boolean e(Drawable drawable) {
        return drawable != null && drawable.isStateful();
    }

    public void b(Drawable drawable) {
        Drawable f2 = f();
        if (f2 != drawable) {
            float a = a();
            this.p = drawable != null ? androidx.core.graphics.drawable.a.i(drawable).mutate() : null;
            float a2 = a();
            f(f2);
            if (Q()) {
                d(this.p);
            }
            invalidateSelf();
            if (a != a2) {
                I();
            }
        }
    }

    public ColorStateList c() {
        return this.e;
    }

    public void g(int i2) {
        b(androidx.appcompat.a.a.a.c(this.K, i2));
    }

    public void c(ColorStateList colorStateList) {
        if (this.f1458h != colorStateList) {
            this.f1458h = colorStateList;
            onStateChange(getState());
        }
    }

    public void e(int i2) {
        a(this.K.getResources().getDimension(i2));
    }

    public float g() {
        return this.r;
    }

    public void e(ColorStateList colorStateList) {
        if (this.f1460j != colorStateList) {
            this.f1460j = colorStateList;
            S();
            onStateChange(getState());
        }
    }

    public void g(float f2) {
        if (this.I != f2) {
            this.I = f2;
            invalidateSelf();
            if (R()) {
                I();
            }
        }
    }

    public void c(boolean z2) {
        if (this.o != z2) {
            boolean Q2 = Q();
            this.o = z2;
            boolean Q3 = Q();
            if (Q2 != Q3) {
                if (Q3) {
                    d(this.p);
                } else {
                    f(this.p);
                }
                invalidateSelf();
                I();
            }
        }
    }

    public void e(float f2) {
        if (this.C != f2) {
            this.C = f2;
            invalidateSelf();
            I();
        }
    }

    public void d(int i2) {
        a(androidx.appcompat.a.a.a.b(this.K, i2));
    }

    public void b(ColorStateList colorStateList) {
        if (this.q != colorStateList) {
            this.q = colorStateList;
            if (Q()) {
                androidx.core.graphics.drawable.a.a(this.p, colorStateList);
            }
            onStateChange(getState());
        }
    }

    public void d(float f2) {
        if (this.f1456f != f2) {
            this.f1456f = f2;
            invalidateSelf();
            I();
        }
    }

    public float e() {
        return this.J;
    }

    /* access modifiers changed from: package-private */
    public void e(boolean z2) {
        this.j0 = z2;
    }

    public void c(float f2) {
        if (this.r != f2) {
            float a = a();
            this.r = f2;
            float a2 = a();
            invalidateSelf();
            if (a != a2) {
                I();
            }
        }
    }

    public float d() {
        return this.f1457g;
    }

    public void b(boolean z2) {
        if (this.y != z2) {
            boolean P2 = P();
            this.y = z2;
            boolean P3 = P();
            if (P2 != P3) {
                if (P3) {
                    d(this.z);
                } else {
                    f(this.z);
                }
                invalidateSelf();
                I();
            }
        }
    }

    public void d(boolean z2) {
        if (this.s != z2) {
            boolean R2 = R();
            this.s = z2;
            boolean R3 = R();
            if (R2 != R3) {
                if (R3) {
                    d(this.t);
                } else {
                    f(this.t);
                }
                invalidateSelf();
                I();
            }
        }
    }

    public void c(Drawable drawable) {
        Drawable m2 = m();
        if (m2 != drawable) {
            float K2 = K();
            this.t = drawable != null ? androidx.core.graphics.drawable.a.i(drawable).mutate() : null;
            float K3 = K();
            f(m2);
            if (R()) {
                d(this.t);
            }
            invalidateSelf();
            if (K2 != K3) {
                I();
            }
        }
    }

    public Drawable b() {
        return this.z;
    }

    public void d(ColorStateList colorStateList) {
        if (this.u != colorStateList) {
            this.u = colorStateList;
            if (R()) {
                androidx.core.graphics.drawable.a.a(this.t, colorStateList);
            }
            onStateChange(getState());
        }
    }

    public void b(int i2) {
        a(androidx.appcompat.a.a.a.c(this.K, i2));
    }

    public void b(h hVar) {
        this.A = hVar;
    }

    public void b(float f2) {
        if (this.J != f2) {
            this.J = f2;
            invalidateSelf();
            I();
        }
    }

    public void c(int i2) {
        b(this.K.getResources().getBoolean(i2));
    }

    public void a(b bVar) {
        this.f0 = new WeakReference<>(bVar);
    }

    public void a(RectF rectF) {
        d(getBounds(), rectF);
    }

    /* access modifiers changed from: package-private */
    public float a() {
        if (Q() || P()) {
            return this.D + this.r + this.E;
        }
        return 0.0f;
    }

    private void a(Canvas canvas, Rect rect) {
        if (P()) {
            a(rect, this.P);
            RectF rectF = this.P;
            float f2 = rectF.left;
            float f3 = rectF.top;
            canvas.translate(f2, f3);
            this.z.setBounds(0, 0, (int) this.P.width(), (int) this.P.height());
            this.z.draw(canvas);
            canvas.translate(-f2, -f3);
        }
    }

    private void a(Rect rect, RectF rectF) {
        rectF.setEmpty();
        if (Q() || P()) {
            float f2 = this.C + this.D;
            if (androidx.core.graphics.drawable.a.e(this) == 0) {
                float f3 = ((float) rect.left) + f2;
                rectF.left = f3;
                rectF.right = f3 + this.r;
            } else {
                float f4 = ((float) rect.right) - f2;
                rectF.right = f4;
                rectF.left = f4 - this.r;
            }
            float exactCenterY = rect.exactCenterY();
            float f5 = this.r;
            float f6 = exactCenterY - (f5 / 2.0f);
            rectF.top = f6;
            rectF.bottom = f6 + f5;
        }
    }

    /* access modifiers changed from: package-private */
    public Paint.Align a(Rect rect, PointF pointF) {
        pointF.set(0.0f, 0.0f);
        Paint.Align align = Paint.Align.LEFT;
        if (this.l != null) {
            float a = this.C + a() + this.F;
            if (androidx.core.graphics.drawable.a.e(this) == 0) {
                pointF.x = ((float) rect.left) + a;
                align = Paint.Align.LEFT;
            } else {
                pointF.x = ((float) rect.right) - a;
                align = Paint.Align.RIGHT;
            }
            pointF.y = ((float) rect.centerY()) - L();
        }
        return align;
    }

    public boolean a(int[] iArr) {
        if (Arrays.equals(this.c0, iArr)) {
            return false;
        }
        this.c0 = iArr;
        if (R()) {
            return a(getState(), iArr);
        }
        return false;
    }

    /* JADX WARNING: Removed duplicated region for block: B:49:0x008d  */
    /* JADX WARNING: Removed duplicated region for block: B:52:0x0097  */
    /* JADX WARNING: Removed duplicated region for block: B:53:0x00a4  */
    /* JADX WARNING: Removed duplicated region for block: B:56:0x00ad  */
    /* JADX WARNING: Removed duplicated region for block: B:59:0x00bc  */
    /* JADX WARNING: Removed duplicated region for block: B:62:0x00cb  */
    /* JADX WARNING: Removed duplicated region for block: B:64:0x00d4  */
    /* JADX WARNING: Removed duplicated region for block: B:66:0x00d9  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean a(int[] r6, int[] r7) {
        /*
            r5 = this;
            boolean r0 = super.onStateChange(r6)
            android.content.res.ColorStateList r1 = r5.e
            r2 = 0
            if (r1 == 0) goto L_0x0010
            int r3 = r5.R
            int r1 = r1.getColorForState(r6, r3)
            goto L_0x0011
        L_0x0010:
            r1 = 0
        L_0x0011:
            int r3 = r5.R
            r4 = 1
            if (r3 == r1) goto L_0x0019
            r5.R = r1
            r0 = 1
        L_0x0019:
            android.content.res.ColorStateList r1 = r5.f1458h
            if (r1 == 0) goto L_0x0024
            int r3 = r5.S
            int r1 = r1.getColorForState(r6, r3)
            goto L_0x0025
        L_0x0024:
            r1 = 0
        L_0x0025:
            int r3 = r5.S
            if (r3 == r1) goto L_0x002c
            r5.S = r1
            r0 = 1
        L_0x002c:
            android.content.res.ColorStateList r1 = r5.e0
            if (r1 == 0) goto L_0x0037
            int r3 = r5.T
            int r1 = r1.getColorForState(r6, r3)
            goto L_0x0038
        L_0x0037:
            r1 = 0
        L_0x0038:
            int r3 = r5.T
            if (r3 == r1) goto L_0x0043
            r5.T = r1
            boolean r1 = r5.d0
            if (r1 == 0) goto L_0x0043
            r0 = 1
        L_0x0043:
            com.google.android.material.f.b r1 = r5.m
            if (r1 == 0) goto L_0x0052
            android.content.res.ColorStateList r1 = r1.b
            if (r1 == 0) goto L_0x0052
            int r3 = r5.U
            int r1 = r1.getColorForState(r6, r3)
            goto L_0x0053
        L_0x0052:
            r1 = 0
        L_0x0053:
            int r3 = r5.U
            if (r3 == r1) goto L_0x005a
            r5.U = r1
            r0 = 1
        L_0x005a:
            int[] r1 = r5.getState()
            r3 = 16842912(0x10100a0, float:2.3694006E-38)
            boolean r1 = a((int[]) r1, (int) r3)
            if (r1 == 0) goto L_0x006d
            boolean r1 = r5.x
            if (r1 == 0) goto L_0x006d
            r1 = 1
            goto L_0x006e
        L_0x006d:
            r1 = 0
        L_0x006e:
            boolean r3 = r5.V
            if (r3 == r1) goto L_0x0088
            android.graphics.drawable.Drawable r3 = r5.z
            if (r3 == 0) goto L_0x0088
            float r0 = r5.a()
            r5.V = r1
            float r1 = r5.a()
            int r0 = (r0 > r1 ? 1 : (r0 == r1 ? 0 : -1))
            if (r0 == 0) goto L_0x0087
            r0 = 1
            r1 = 1
            goto L_0x0089
        L_0x0087:
            r0 = 1
        L_0x0088:
            r1 = 0
        L_0x0089:
            android.content.res.ColorStateList r3 = r5.a0
            if (r3 == 0) goto L_0x0093
            int r2 = r5.W
            int r2 = r3.getColorForState(r6, r2)
        L_0x0093:
            int r3 = r5.W
            if (r3 == r2) goto L_0x00a4
            r5.W = r2
            android.content.res.ColorStateList r0 = r5.a0
            android.graphics.PorterDuff$Mode r2 = r5.b0
            android.graphics.PorterDuffColorFilter r0 = com.google.android.material.c.a.a(r5, r0, r2)
            r5.Z = r0
            goto L_0x00a5
        L_0x00a4:
            r4 = r0
        L_0x00a5:
            android.graphics.drawable.Drawable r0 = r5.p
            boolean r0 = e((android.graphics.drawable.Drawable) r0)
            if (r0 == 0) goto L_0x00b4
            android.graphics.drawable.Drawable r0 = r5.p
            boolean r0 = r0.setState(r6)
            r4 = r4 | r0
        L_0x00b4:
            android.graphics.drawable.Drawable r0 = r5.z
            boolean r0 = e((android.graphics.drawable.Drawable) r0)
            if (r0 == 0) goto L_0x00c3
            android.graphics.drawable.Drawable r0 = r5.z
            boolean r6 = r0.setState(r6)
            r4 = r4 | r6
        L_0x00c3:
            android.graphics.drawable.Drawable r6 = r5.t
            boolean r6 = e((android.graphics.drawable.Drawable) r6)
            if (r6 == 0) goto L_0x00d2
            android.graphics.drawable.Drawable r6 = r5.t
            boolean r6 = r6.setState(r7)
            r4 = r4 | r6
        L_0x00d2:
            if (r4 == 0) goto L_0x00d7
            r5.invalidateSelf()
        L_0x00d7:
            if (r1 == 0) goto L_0x00dc
            r5.I()
        L_0x00dc:
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.material.chip.a.a(int[], int[]):boolean");
    }

    private static boolean a(int[] iArr, int i2) {
        if (iArr == null) {
            return false;
        }
        for (int i3 : iArr) {
            if (i3 == i2) {
                return true;
            }
        }
        return false;
    }

    public void a(ColorStateList colorStateList) {
        if (this.e != colorStateList) {
            this.e = colorStateList;
            onStateChange(getState());
        }
    }

    public void a(float f2) {
        if (this.f1457g != f2) {
            this.f1457g = f2;
            invalidateSelf();
        }
    }

    public void a(com.google.android.material.f.b bVar) {
        if (this.m != bVar) {
            this.m = bVar;
            if (bVar != null) {
                bVar.c(this.K, this.L, this.n);
                this.g0 = true;
            }
            onStateChange(getState());
            I();
        }
    }

    public void a(TextUtils.TruncateAt truncateAt) {
        this.i0 = truncateAt;
    }

    public void a(CharSequence charSequence) {
        if (this.w != charSequence) {
            this.w = androidx.core.f.a.b().a(charSequence);
            invalidateSelf();
        }
    }

    public void a(int i2) {
        a(this.K.getResources().getBoolean(i2));
    }

    public void a(boolean z2) {
        if (this.x != z2) {
            this.x = z2;
            float a = a();
            if (!z2 && this.V) {
                this.V = false;
            }
            float a2 = a();
            invalidateSelf();
            if (a != a2) {
                I();
            }
        }
    }

    public void a(Drawable drawable) {
        if (this.z != drawable) {
            float a = a();
            this.z = drawable;
            float a2 = a();
            f(this.z);
            d(this.z);
            invalidateSelf();
            if (a != a2) {
                I();
            }
        }
    }

    public void a(h hVar) {
        this.B = hVar;
    }
}
