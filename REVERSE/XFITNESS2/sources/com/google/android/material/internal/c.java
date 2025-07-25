package com.google.android.material.internal;

import android.animation.TimeInterpolator;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.os.Build;
import android.text.TextPaint;
import android.text.TextUtils;
import android.view.View;
import androidx.appcompat.R$styleable;
import androidx.appcompat.widget.g0;
import androidx.core.c.a;
import androidx.core.f.e;
import androidx.core.h.d;
import androidx.core.h.v;

/* compiled from: CollapsingTextHelper */
public final class c {
    private static final boolean T = (Build.VERSION.SDK_INT < 18);
    private static final Paint U;
    private Paint A;
    private float B;
    private float C;
    private float D;
    private float E;
    private int[] F;
    private boolean G;
    private final TextPaint H;
    private final TextPaint I;
    private TimeInterpolator J;
    private TimeInterpolator K;
    private float L;
    private float M;
    private float N;
    private int O;
    private float P;
    private float Q;
    private float R;
    private int S;
    private final View a;
    private boolean b;
    private float c;
    private final Rect d;
    private final Rect e;

    /* renamed from: f  reason: collision with root package name */
    private final RectF f1515f;

    /* renamed from: g  reason: collision with root package name */
    private int f1516g = 16;

    /* renamed from: h  reason: collision with root package name */
    private int f1517h = 16;

    /* renamed from: i  reason: collision with root package name */
    private float f1518i = 15.0f;

    /* renamed from: j  reason: collision with root package name */
    private float f1519j = 15.0f;
    private ColorStateList k;
    private ColorStateList l;
    private float m;
    private float n;
    private float o;
    private float p;
    private float q;
    private float r;
    private Typeface s;
    private Typeface t;
    private Typeface u;
    private CharSequence v;
    private CharSequence w;
    private boolean x;
    private boolean y;
    private Bitmap z;

    static {
        Paint paint = null;
        U = paint;
        if (paint != null) {
            paint.setAntiAlias(true);
            U.setColor(-65281);
        }
    }

    public c(View view) {
        this.a = view;
        this.H = new TextPaint(129);
        this.I = new TextPaint(this.H);
        this.e = new Rect();
        this.d = new Rect();
        this.f1515f = new RectF();
    }

    private Typeface e(int i2) {
        TypedArray obtainStyledAttributes = this.a.getContext().obtainStyledAttributes(i2, new int[]{16843692});
        try {
            String string = obtainStyledAttributes.getString(0);
            if (string != null) {
                return Typeface.create(string, 0);
            }
            obtainStyledAttributes.recycle();
            return null;
        } finally {
            obtainStyledAttributes.recycle();
        }
    }

    private void n() {
        float f2 = this.E;
        d(this.f1519j);
        CharSequence charSequence = this.w;
        float f3 = 0.0f;
        float measureText = charSequence != null ? this.H.measureText(charSequence, 0, charSequence.length()) : 0.0f;
        int a2 = d.a(this.f1517h, this.x ? 1 : 0);
        int i2 = a2 & 112;
        if (i2 == 48) {
            this.n = ((float) this.e.top) - this.H.ascent();
        } else if (i2 != 80) {
            this.n = ((float) this.e.centerY()) + (((this.H.descent() - this.H.ascent()) / 2.0f) - this.H.descent());
        } else {
            this.n = (float) this.e.bottom;
        }
        int i3 = a2 & 8388615;
        if (i3 == 1) {
            this.p = ((float) this.e.centerX()) - (measureText / 2.0f);
        } else if (i3 != 5) {
            this.p = (float) this.e.left;
        } else {
            this.p = ((float) this.e.right) - measureText;
        }
        d(this.f1518i);
        CharSequence charSequence2 = this.w;
        if (charSequence2 != null) {
            f3 = this.H.measureText(charSequence2, 0, charSequence2.length());
        }
        int a3 = d.a(this.f1516g, this.x ? 1 : 0);
        int i4 = a3 & 112;
        if (i4 == 48) {
            this.m = ((float) this.d.top) - this.H.ascent();
        } else if (i4 != 80) {
            this.m = ((float) this.d.centerY()) + (((this.H.descent() - this.H.ascent()) / 2.0f) - this.H.descent());
        } else {
            this.m = (float) this.d.bottom;
        }
        int i5 = a3 & 8388615;
        if (i5 == 1) {
            this.o = ((float) this.d.centerX()) - (f3 / 2.0f);
        } else if (i5 != 5) {
            this.o = (float) this.d.left;
        } else {
            this.o = ((float) this.d.right) - f3;
        }
        p();
        f(f2);
    }

    private void o() {
        c(this.c);
    }

    private void p() {
        Bitmap bitmap = this.z;
        if (bitmap != null) {
            bitmap.recycle();
            this.z = null;
        }
    }

    private void q() {
        if (this.z == null && !this.d.isEmpty() && !TextUtils.isEmpty(this.w)) {
            c(0.0f);
            this.B = this.H.ascent();
            this.C = this.H.descent();
            TextPaint textPaint = this.H;
            CharSequence charSequence = this.w;
            int round = Math.round(textPaint.measureText(charSequence, 0, charSequence.length()));
            int round2 = Math.round(this.C - this.B);
            if (round > 0 && round2 > 0) {
                this.z = Bitmap.createBitmap(round, round2, Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(this.z);
                CharSequence charSequence2 = this.w;
                canvas.drawText(charSequence2, 0, charSequence2.length(), 0.0f, ((float) round2) - this.H.descent(), this.H);
                if (this.A == null) {
                    this.A = new Paint(3);
                }
            }
        }
    }

    private int r() {
        int[] iArr = this.F;
        if (iArr != null) {
            return this.k.getColorForState(iArr, 0);
        }
        return this.k.getDefaultColor();
    }

    public void a(TimeInterpolator timeInterpolator) {
        this.J = timeInterpolator;
        m();
    }

    public void b(TimeInterpolator timeInterpolator) {
        this.K = timeInterpolator;
        m();
    }

    public int c() {
        return this.f1517h;
    }

    public float d() {
        a(this.I);
        return -this.I.ascent();
    }

    public int f() {
        int[] iArr = this.F;
        if (iArr != null) {
            return this.l.getColorForState(iArr, 0);
        }
        return this.l.getDefaultColor();
    }

    public int g() {
        return this.f1516g;
    }

    public Typeface h() {
        Typeface typeface = this.t;
        return typeface != null ? typeface : Typeface.DEFAULT;
    }

    public float i() {
        return this.c;
    }

    public CharSequence j() {
        return this.v;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:4:0x000a, code lost:
        r0 = r1.k;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean k() {
        /*
            r1 = this;
            android.content.res.ColorStateList r0 = r1.l
            if (r0 == 0) goto L_0x000a
            boolean r0 = r0.isStateful()
            if (r0 != 0) goto L_0x0014
        L_0x000a:
            android.content.res.ColorStateList r0 = r1.k
            if (r0 == 0) goto L_0x0016
            boolean r0 = r0.isStateful()
            if (r0 == 0) goto L_0x0016
        L_0x0014:
            r0 = 1
            goto L_0x0017
        L_0x0016:
            r0 = 0
        L_0x0017:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.material.internal.c.k():boolean");
    }

    /* access modifiers changed from: package-private */
    public void l() {
        this.b = this.e.width() > 0 && this.e.height() > 0 && this.d.width() > 0 && this.d.height() > 0;
    }

    public void m() {
        if (this.a.getHeight() > 0 && this.a.getWidth() > 0) {
            n();
            o();
        }
    }

    public void c(int i2) {
        g0 a2 = g0.a(this.a.getContext(), i2, R$styleable.TextAppearance);
        if (a2.g(R$styleable.TextAppearance_android_textColor)) {
            this.k = a2.a(R$styleable.TextAppearance_android_textColor);
        }
        if (a2.g(R$styleable.TextAppearance_android_textSize)) {
            this.f1518i = (float) a2.c(R$styleable.TextAppearance_android_textSize, (int) this.f1518i);
        }
        this.S = a2.d(R$styleable.TextAppearance_android_shadowColor, 0);
        this.Q = a2.b(R$styleable.TextAppearance_android_shadowDx, 0.0f);
        this.R = a2.b(R$styleable.TextAppearance_android_shadowDy, 0.0f);
        this.P = a2.b(R$styleable.TextAppearance_android_shadowRadius, 0.0f);
        a2.a();
        if (Build.VERSION.SDK_INT >= 16) {
            this.t = e(i2);
        }
        m();
    }

    public void a(float f2) {
        if (this.f1518i != f2) {
            this.f1518i = f2;
            m();
        }
    }

    public void b(ColorStateList colorStateList) {
        if (this.k != colorStateList) {
            this.k = colorStateList;
            m();
        }
    }

    public void d(int i2) {
        if (this.f1516g != i2) {
            this.f1516g = i2;
            m();
        }
    }

    private void f(float f2) {
        d(f2);
        boolean z2 = T && this.D != 1.0f;
        this.y = z2;
        if (z2) {
            q();
        }
        v.H(this.a);
    }

    private void d(float f2) {
        float f3;
        boolean z2;
        boolean z3;
        if (this.v != null) {
            float width = (float) this.e.width();
            float width2 = (float) this.d.width();
            boolean z4 = true;
            if (a(f2, this.f1519j)) {
                f3 = this.f1519j;
                this.D = 1.0f;
                Typeface typeface = this.u;
                Typeface typeface2 = this.s;
                if (typeface != typeface2) {
                    this.u = typeface2;
                    z2 = true;
                } else {
                    z2 = false;
                }
            } else {
                float f4 = this.f1518i;
                Typeface typeface3 = this.u;
                Typeface typeface4 = this.t;
                if (typeface3 != typeface4) {
                    this.u = typeface4;
                    z3 = true;
                } else {
                    z3 = false;
                }
                if (a(f2, this.f1518i)) {
                    this.D = 1.0f;
                } else {
                    this.D = f2 / this.f1518i;
                }
                float f5 = this.f1519j / this.f1518i;
                width = width2 * f5 > width ? Math.min(width / f5, width2) : width2;
                f3 = f4;
                z2 = z3;
            }
            if (width > 0.0f) {
                z2 = this.E != f3 || this.G || z2;
                this.E = f3;
                this.G = false;
            }
            if (this.w == null || z2) {
                this.H.setTextSize(this.E);
                this.H.setTypeface(this.u);
                TextPaint textPaint = this.H;
                if (this.D == 1.0f) {
                    z4 = false;
                }
                textPaint.setLinearText(z4);
                CharSequence ellipsize = TextUtils.ellipsize(this.v, this.H, width, TextUtils.TruncateAt.END);
                if (!TextUtils.equals(ellipsize, this.w)) {
                    this.w = ellipsize;
                    this.x = b(ellipsize);
                }
            }
        }
    }

    public void a(ColorStateList colorStateList) {
        if (this.l != colorStateList) {
            this.l = colorStateList;
            m();
        }
    }

    public void b(int i2, int i3, int i4, int i5) {
        if (!a(this.d, i2, i3, i4, i5)) {
            this.d.set(i2, i3, i4, i5);
            this.G = true;
            l();
        }
    }

    public Typeface e() {
        Typeface typeface = this.s;
        return typeface != null ? typeface : Typeface.DEFAULT;
    }

    private void e(float f2) {
        this.f1515f.left = a((float) this.d.left, (float) this.e.left, f2, this.J);
        this.f1515f.top = a(this.m, this.n, f2, this.J);
        this.f1515f.right = a((float) this.d.right, (float) this.e.right, f2, this.J);
        this.f1515f.bottom = a((float) this.d.bottom, (float) this.e.bottom, f2, this.J);
    }

    public void a(int i2, int i3, int i4, int i5) {
        if (!a(this.e, i2, i3, i4, i5)) {
            this.e.set(i2, i3, i4, i5);
            this.G = true;
            l();
        }
    }

    public void b(int i2) {
        if (this.f1517h != i2) {
            this.f1517h = i2;
            m();
        }
    }

    public float a() {
        if (this.v == null) {
            return 0.0f;
        }
        a(this.I);
        TextPaint textPaint = this.I;
        CharSequence charSequence = this.v;
        return textPaint.measureText(charSequence, 0, charSequence.length());
    }

    public void b(Typeface typeface) {
        if (this.t != typeface) {
            this.t = typeface;
            m();
        }
    }

    public void a(RectF rectF) {
        float f2;
        boolean b2 = b(this.v);
        Rect rect = this.e;
        if (!b2) {
            f2 = (float) rect.left;
        } else {
            f2 = ((float) rect.right) - a();
        }
        rectF.left = f2;
        Rect rect2 = this.e;
        rectF.top = (float) rect2.top;
        rectF.right = !b2 ? f2 + a() : (float) rect2.right;
        rectF.bottom = ((float) this.e.top) + d();
    }

    public void b(float f2) {
        float a2 = a.a(f2, 0.0f, 1.0f);
        if (a2 != this.c) {
            this.c = a2;
            o();
        }
    }

    private boolean b(CharSequence charSequence) {
        boolean z2 = true;
        if (v.o(this.a) != 1) {
            z2 = false;
        }
        return (z2 ? e.d : e.c).a(charSequence, 0, charSequence.length());
    }

    private void a(TextPaint textPaint) {
        textPaint.setTextSize(this.f1519j);
        textPaint.setTypeface(this.s);
    }

    public void c(Typeface typeface) {
        this.t = typeface;
        this.s = typeface;
        m();
    }

    public void a(int i2) {
        g0 a2 = g0.a(this.a.getContext(), i2, R$styleable.TextAppearance);
        if (a2.g(R$styleable.TextAppearance_android_textColor)) {
            this.l = a2.a(R$styleable.TextAppearance_android_textColor);
        }
        if (a2.g(R$styleable.TextAppearance_android_textSize)) {
            this.f1519j = (float) a2.c(R$styleable.TextAppearance_android_textSize, (int) this.f1519j);
        }
        this.O = a2.d(R$styleable.TextAppearance_android_shadowColor, 0);
        this.M = a2.b(R$styleable.TextAppearance_android_shadowDx, 0.0f);
        this.N = a2.b(R$styleable.TextAppearance_android_shadowDy, 0.0f);
        this.L = a2.b(R$styleable.TextAppearance_android_shadowRadius, 0.0f);
        a2.a();
        if (Build.VERSION.SDK_INT >= 16) {
            this.s = e(i2);
        }
        m();
    }

    public ColorStateList b() {
        return this.l;
    }

    private void c(float f2) {
        e(f2);
        this.q = a(this.o, this.p, f2, this.J);
        this.r = a(this.m, this.n, f2, this.J);
        f(a(this.f1518i, this.f1519j, f2, this.K));
        if (this.l != this.k) {
            this.H.setColor(a(r(), f(), f2));
        } else {
            this.H.setColor(f());
        }
        this.H.setShadowLayer(a(this.P, this.L, f2, (TimeInterpolator) null), a(this.Q, this.M, f2, (TimeInterpolator) null), a(this.R, this.N, f2, (TimeInterpolator) null), a(this.S, this.O, f2));
        v.H(this.a);
    }

    public void a(Typeface typeface) {
        if (this.s != typeface) {
            this.s = typeface;
            m();
        }
    }

    public final boolean a(int[] iArr) {
        this.F = iArr;
        if (!k()) {
            return false;
        }
        m();
        return true;
    }

    public void a(Canvas canvas) {
        float f2;
        int save = canvas.save();
        if (this.w != null && this.b) {
            float f3 = this.q;
            float f4 = this.r;
            boolean z2 = this.y && this.z != null;
            if (z2) {
                f2 = this.B * this.D;
            } else {
                f2 = this.H.ascent() * this.D;
                this.H.descent();
            }
            if (z2) {
                f4 += f2;
            }
            float f5 = f4;
            float f6 = this.D;
            if (f6 != 1.0f) {
                canvas.scale(f6, f6, f3, f5);
            }
            if (z2) {
                canvas.drawBitmap(this.z, f3, f5, this.A);
            } else {
                CharSequence charSequence = this.w;
                canvas.drawText(charSequence, 0, charSequence.length(), f3, f5, this.H);
            }
        }
        canvas.restoreToCount(save);
    }

    public void a(CharSequence charSequence) {
        if (charSequence == null || !charSequence.equals(this.v)) {
            this.v = charSequence;
            this.w = null;
            p();
            m();
        }
    }

    private static boolean a(float f2, float f3) {
        return Math.abs(f2 - f3) < 0.001f;
    }

    private static int a(int i2, int i3, float f2) {
        float f3 = 1.0f - f2;
        return Color.argb((int) ((((float) Color.alpha(i2)) * f3) + (((float) Color.alpha(i3)) * f2)), (int) ((((float) Color.red(i2)) * f3) + (((float) Color.red(i3)) * f2)), (int) ((((float) Color.green(i2)) * f3) + (((float) Color.green(i3)) * f2)), (int) ((((float) Color.blue(i2)) * f3) + (((float) Color.blue(i3)) * f2)));
    }

    private static float a(float f2, float f3, float f4, TimeInterpolator timeInterpolator) {
        if (timeInterpolator != null) {
            f4 = timeInterpolator.getInterpolation(f4);
        }
        return com.google.android.material.a.a.a(f2, f3, f4);
    }

    private static boolean a(Rect rect, int i2, int i3, int i4, int i5) {
        return rect.left == i2 && rect.top == i3 && rect.right == i4 && rect.bottom == i5;
    }
}
