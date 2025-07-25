package androidx.appcompat.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.LocaleList;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.widget.TextView;
import androidx.appcompat.R$styleable;
import androidx.core.content.c.f;
import androidx.core.widget.b;
import androidx.core.widget.i;
import java.lang.ref.WeakReference;
import java.util.Locale;

/* compiled from: AppCompatTextHelper */
class m {
    private final TextView a;
    private e0 b;
    private e0 c;
    private e0 d;
    private e0 e;

    /* renamed from: f  reason: collision with root package name */
    private e0 f305f;

    /* renamed from: g  reason: collision with root package name */
    private e0 f306g;

    /* renamed from: h  reason: collision with root package name */
    private e0 f307h;

    /* renamed from: i  reason: collision with root package name */
    private final n f308i;

    /* renamed from: j  reason: collision with root package name */
    private int f309j = 0;
    private int k = -1;
    private Typeface l;
    private boolean m;

    /* compiled from: AppCompatTextHelper */
    private static class a extends f.a {
        private final WeakReference<m> a;
        private final int b;
        private final int c;

        /* renamed from: androidx.appcompat.widget.m$a$a  reason: collision with other inner class name */
        /* compiled from: AppCompatTextHelper */
        private class C0008a implements Runnable {
            private final WeakReference<m> e;

            /* renamed from: f  reason: collision with root package name */
            private final Typeface f310f;

            C0008a(a aVar, WeakReference<m> weakReference, Typeface typeface) {
                this.e = weakReference;
                this.f310f = typeface;
            }

            public void run() {
                m mVar = (m) this.e.get();
                if (mVar != null) {
                    mVar.a(this.f310f);
                }
            }
        }

        a(m mVar, int i2, int i3) {
            this.a = new WeakReference<>(mVar);
            this.b = i2;
            this.c = i3;
        }

        public void a(int i2) {
        }

        public void a(Typeface typeface) {
            int i2;
            m mVar = (m) this.a.get();
            if (mVar != null) {
                if (Build.VERSION.SDK_INT >= 28 && (i2 = this.b) != -1) {
                    typeface = Typeface.create(typeface, i2, (this.c & 2) != 0);
                }
                mVar.a((Runnable) new C0008a(this, this.a, typeface));
            }
        }
    }

    m(TextView textView) {
        this.a = textView;
        this.f308i = new n(this.a);
    }

    private void l() {
        e0 e0Var = this.f307h;
        this.b = e0Var;
        this.c = e0Var;
        this.d = e0Var;
        this.e = e0Var;
        this.f305f = e0Var;
        this.f306g = e0Var;
    }

    /* access modifiers changed from: package-private */
    @SuppressLint({"NewApi"})
    public void a(AttributeSet attributeSet, int i2) {
        ColorStateList colorStateList;
        boolean z;
        boolean z2;
        ColorStateList colorStateList2;
        String str;
        ColorStateList colorStateList3;
        String str2;
        f fVar;
        int i3;
        AttributeSet attributeSet2 = attributeSet;
        int i4 = i2;
        Context context = this.a.getContext();
        f b2 = f.b();
        g0 a2 = g0.a(context, attributeSet2, R$styleable.AppCompatTextHelper, i4, 0);
        int g2 = a2.g(R$styleable.AppCompatTextHelper_android_textAppearance, -1);
        if (a2.g(R$styleable.AppCompatTextHelper_android_drawableLeft)) {
            this.b = a(context, b2, a2.g(R$styleable.AppCompatTextHelper_android_drawableLeft, 0));
        }
        if (a2.g(R$styleable.AppCompatTextHelper_android_drawableTop)) {
            this.c = a(context, b2, a2.g(R$styleable.AppCompatTextHelper_android_drawableTop, 0));
        }
        if (a2.g(R$styleable.AppCompatTextHelper_android_drawableRight)) {
            this.d = a(context, b2, a2.g(R$styleable.AppCompatTextHelper_android_drawableRight, 0));
        }
        if (a2.g(R$styleable.AppCompatTextHelper_android_drawableBottom)) {
            this.e = a(context, b2, a2.g(R$styleable.AppCompatTextHelper_android_drawableBottom, 0));
        }
        if (Build.VERSION.SDK_INT >= 17) {
            if (a2.g(R$styleable.AppCompatTextHelper_android_drawableStart)) {
                this.f305f = a(context, b2, a2.g(R$styleable.AppCompatTextHelper_android_drawableStart, 0));
            }
            if (a2.g(R$styleable.AppCompatTextHelper_android_drawableEnd)) {
                this.f306g = a(context, b2, a2.g(R$styleable.AppCompatTextHelper_android_drawableEnd, 0));
            }
        }
        a2.a();
        boolean z3 = this.a.getTransformationMethod() instanceof PasswordTransformationMethod;
        if (g2 != -1) {
            g0 a3 = g0.a(context, g2, R$styleable.TextAppearance);
            if (z3 || !a3.g(R$styleable.TextAppearance_textAllCaps)) {
                z2 = false;
                z = false;
            } else {
                z2 = a3.a(R$styleable.TextAppearance_textAllCaps, false);
                z = true;
            }
            a(context, a3);
            if (Build.VERSION.SDK_INT < 23) {
                colorStateList = a3.g(R$styleable.TextAppearance_android_textColor) ? a3.a(R$styleable.TextAppearance_android_textColor) : null;
                colorStateList3 = a3.g(R$styleable.TextAppearance_android_textColorHint) ? a3.a(R$styleable.TextAppearance_android_textColorHint) : null;
                colorStateList2 = a3.g(R$styleable.TextAppearance_android_textColorLink) ? a3.a(R$styleable.TextAppearance_android_textColorLink) : null;
            } else {
                colorStateList3 = null;
                colorStateList2 = null;
                colorStateList = null;
            }
            str2 = a3.g(R$styleable.TextAppearance_textLocale) ? a3.d(R$styleable.TextAppearance_textLocale) : null;
            str = (Build.VERSION.SDK_INT < 26 || !a3.g(R$styleable.TextAppearance_fontVariationSettings)) ? null : a3.d(R$styleable.TextAppearance_fontVariationSettings);
            a3.a();
        } else {
            str2 = null;
            colorStateList3 = null;
            str = null;
            colorStateList2 = null;
            z2 = false;
            z = false;
            colorStateList = null;
        }
        g0 a4 = g0.a(context, attributeSet2, R$styleable.TextAppearance, i4, 0);
        if (!z3 && a4.g(R$styleable.TextAppearance_textAllCaps)) {
            z2 = a4.a(R$styleable.TextAppearance_textAllCaps, false);
            z = true;
        }
        if (Build.VERSION.SDK_INT < 23) {
            if (a4.g(R$styleable.TextAppearance_android_textColor)) {
                colorStateList = a4.a(R$styleable.TextAppearance_android_textColor);
            }
            if (a4.g(R$styleable.TextAppearance_android_textColorHint)) {
                colorStateList3 = a4.a(R$styleable.TextAppearance_android_textColorHint);
            }
            if (a4.g(R$styleable.TextAppearance_android_textColorLink)) {
                colorStateList2 = a4.a(R$styleable.TextAppearance_android_textColorLink);
            }
        }
        if (a4.g(R$styleable.TextAppearance_textLocale)) {
            str2 = a4.d(R$styleable.TextAppearance_textLocale);
        }
        if (Build.VERSION.SDK_INT >= 26 && a4.g(R$styleable.TextAppearance_fontVariationSettings)) {
            str = a4.d(R$styleable.TextAppearance_fontVariationSettings);
        }
        if (Build.VERSION.SDK_INT < 28 || !a4.g(R$styleable.TextAppearance_android_textSize) || a4.c(R$styleable.TextAppearance_android_textSize, -1) != 0) {
            fVar = b2;
        } else {
            fVar = b2;
            this.a.setTextSize(0, 0.0f);
        }
        a(context, a4);
        a4.a();
        if (colorStateList != null) {
            this.a.setTextColor(colorStateList);
        }
        if (colorStateList3 != null) {
            this.a.setHintTextColor(colorStateList3);
        }
        if (colorStateList2 != null) {
            this.a.setLinkTextColor(colorStateList2);
        }
        if (!z3 && z) {
            a(z2);
        }
        Typeface typeface = this.l;
        if (typeface != null) {
            if (this.k == -1) {
                this.a.setTypeface(typeface, this.f309j);
            } else {
                this.a.setTypeface(typeface);
            }
        }
        if (str != null) {
            this.a.setFontVariationSettings(str);
        }
        if (str2 != null) {
            int i5 = Build.VERSION.SDK_INT;
            if (i5 >= 24) {
                this.a.setTextLocales(LocaleList.forLanguageTags(str2));
            } else if (i5 >= 21) {
                this.a.setTextLocale(Locale.forLanguageTag(str2.substring(0, str2.indexOf(44))));
            }
        }
        this.f308i.a(attributeSet2, i4);
        if (b.a && this.f308i.f() != 0) {
            int[] e2 = this.f308i.e();
            if (e2.length > 0) {
                if (((float) this.a.getAutoSizeStepGranularity()) != -1.0f) {
                    this.a.setAutoSizeTextTypeUniformWithConfiguration(this.f308i.c(), this.f308i.b(), this.f308i.d(), 0);
                } else {
                    this.a.setAutoSizeTextTypeUniformWithPresetSizes(e2, 0);
                }
            }
        }
        g0 a5 = g0.a(context, attributeSet2, R$styleable.AppCompatTextView);
        int g3 = a5.g(R$styleable.AppCompatTextView_drawableLeftCompat, -1);
        f fVar2 = fVar;
        Drawable a6 = g3 != -1 ? fVar2.a(context, g3) : null;
        int g4 = a5.g(R$styleable.AppCompatTextView_drawableTopCompat, -1);
        Drawable a7 = g4 != -1 ? fVar2.a(context, g4) : null;
        int g5 = a5.g(R$styleable.AppCompatTextView_drawableRightCompat, -1);
        Drawable a8 = g5 != -1 ? fVar2.a(context, g5) : null;
        int g6 = a5.g(R$styleable.AppCompatTextView_drawableBottomCompat, -1);
        Drawable a9 = g6 != -1 ? fVar2.a(context, g6) : null;
        int g7 = a5.g(R$styleable.AppCompatTextView_drawableStartCompat, -1);
        Drawable a10 = g7 != -1 ? fVar2.a(context, g7) : null;
        int g8 = a5.g(R$styleable.AppCompatTextView_drawableEndCompat, -1);
        a(a6, a7, a8, a9, a10, g8 != -1 ? fVar2.a(context, g8) : null);
        if (a5.g(R$styleable.AppCompatTextView_drawableTint)) {
            i.a(this.a, a5.a(R$styleable.AppCompatTextView_drawableTint));
        }
        if (a5.g(R$styleable.AppCompatTextView_drawableTintMode)) {
            i3 = -1;
            i.a(this.a, q.a(a5.d(R$styleable.AppCompatTextView_drawableTintMode, -1), (PorterDuff.Mode) null));
        } else {
            i3 = -1;
        }
        int c2 = a5.c(R$styleable.AppCompatTextView_firstBaselineToTopHeight, i3);
        int c3 = a5.c(R$styleable.AppCompatTextView_lastBaselineToBottomHeight, i3);
        int c4 = a5.c(R$styleable.AppCompatTextView_lineHeight, i3);
        a5.a();
        if (c2 != i3) {
            i.a(this.a, c2);
        }
        if (c3 != i3) {
            i.b(this.a, c3);
        }
        if (c4 != i3) {
            i.c(this.a, c4);
        }
    }

    /* access modifiers changed from: package-private */
    public void b() {
        this.f308i.a();
    }

    /* access modifiers changed from: package-private */
    public int c() {
        return this.f308i.b();
    }

    /* access modifiers changed from: package-private */
    public int d() {
        return this.f308i.c();
    }

    /* access modifiers changed from: package-private */
    public int e() {
        return this.f308i.d();
    }

    /* access modifiers changed from: package-private */
    public int[] f() {
        return this.f308i.e();
    }

    /* access modifiers changed from: package-private */
    public int g() {
        return this.f308i.f();
    }

    /* access modifiers changed from: package-private */
    public ColorStateList h() {
        e0 e0Var = this.f307h;
        if (e0Var != null) {
            return e0Var.a;
        }
        return null;
    }

    /* access modifiers changed from: package-private */
    public PorterDuff.Mode i() {
        e0 e0Var = this.f307h;
        if (e0Var != null) {
            return e0Var.b;
        }
        return null;
    }

    /* access modifiers changed from: package-private */
    public boolean j() {
        return this.f308i.g();
    }

    /* access modifiers changed from: package-private */
    public void k() {
        a();
    }

    private void b(int i2, float f2) {
        this.f308i.a(i2, f2);
    }

    public void a(Typeface typeface) {
        if (this.m) {
            this.a.setTypeface(typeface);
            this.l = typeface;
        }
    }

    public void a(Runnable runnable) {
        this.a.post(runnable);
    }

    private void a(Context context, g0 g0Var) {
        String d2;
        this.f309j = g0Var.d(R$styleable.TextAppearance_android_textStyle, this.f309j);
        boolean z = false;
        if (Build.VERSION.SDK_INT >= 28) {
            int d3 = g0Var.d(R$styleable.TextAppearance_android_textFontWeight, -1);
            this.k = d3;
            if (d3 != -1) {
                this.f309j = (this.f309j & 2) | 0;
            }
        }
        if (g0Var.g(R$styleable.TextAppearance_android_fontFamily) || g0Var.g(R$styleable.TextAppearance_fontFamily)) {
            this.l = null;
            int i2 = g0Var.g(R$styleable.TextAppearance_fontFamily) ? R$styleable.TextAppearance_fontFamily : R$styleable.TextAppearance_android_fontFamily;
            int i3 = this.k;
            int i4 = this.f309j;
            if (!context.isRestricted()) {
                try {
                    Typeface a2 = g0Var.a(i2, this.f309j, (f.a) new a(this, i3, i4));
                    if (a2 != null) {
                        if (Build.VERSION.SDK_INT < 28 || this.k == -1) {
                            this.l = a2;
                        } else {
                            this.l = Typeface.create(Typeface.create(a2, 0), this.k, (this.f309j & 2) != 0);
                        }
                    }
                    this.m = this.l == null;
                } catch (Resources.NotFoundException | UnsupportedOperationException unused) {
                }
            }
            if (this.l == null && (d2 = g0Var.d(i2)) != null) {
                if (Build.VERSION.SDK_INT < 28 || this.k == -1) {
                    this.l = Typeface.create(d2, this.f309j);
                    return;
                }
                Typeface create = Typeface.create(d2, 0);
                int i5 = this.k;
                if ((this.f309j & 2) != 0) {
                    z = true;
                }
                this.l = Typeface.create(create, i5, z);
            }
        } else if (g0Var.g(R$styleable.TextAppearance_android_typeface)) {
            this.m = false;
            int d4 = g0Var.d(R$styleable.TextAppearance_android_typeface, 1);
            if (d4 == 1) {
                this.l = Typeface.SANS_SERIF;
            } else if (d4 == 2) {
                this.l = Typeface.SERIF;
            } else if (d4 == 3) {
                this.l = Typeface.MONOSPACE;
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void a(Context context, int i2) {
        String d2;
        ColorStateList a2;
        g0 a3 = g0.a(context, i2, R$styleable.TextAppearance);
        if (a3.g(R$styleable.TextAppearance_textAllCaps)) {
            a(a3.a(R$styleable.TextAppearance_textAllCaps, false));
        }
        if (Build.VERSION.SDK_INT < 23 && a3.g(R$styleable.TextAppearance_android_textColor) && (a2 = a3.a(R$styleable.TextAppearance_android_textColor)) != null) {
            this.a.setTextColor(a2);
        }
        if (a3.g(R$styleable.TextAppearance_android_textSize) && a3.c(R$styleable.TextAppearance_android_textSize, -1) == 0) {
            this.a.setTextSize(0, 0.0f);
        }
        a(context, a3);
        if (Build.VERSION.SDK_INT >= 26 && a3.g(R$styleable.TextAppearance_fontVariationSettings) && (d2 = a3.d(R$styleable.TextAppearance_fontVariationSettings)) != null) {
            this.a.setFontVariationSettings(d2);
        }
        a3.a();
        Typeface typeface = this.l;
        if (typeface != null) {
            this.a.setTypeface(typeface, this.f309j);
        }
    }

    /* access modifiers changed from: package-private */
    public void a(boolean z) {
        this.a.setAllCaps(z);
    }

    /* access modifiers changed from: package-private */
    public void a() {
        if (!(this.b == null && this.c == null && this.d == null && this.e == null)) {
            Drawable[] compoundDrawables = this.a.getCompoundDrawables();
            a(compoundDrawables[0], this.b);
            a(compoundDrawables[1], this.c);
            a(compoundDrawables[2], this.d);
            a(compoundDrawables[3], this.e);
        }
        if (Build.VERSION.SDK_INT < 17) {
            return;
        }
        if (this.f305f != null || this.f306g != null) {
            Drawable[] compoundDrawablesRelative = this.a.getCompoundDrawablesRelative();
            a(compoundDrawablesRelative[0], this.f305f);
            a(compoundDrawablesRelative[2], this.f306g);
        }
    }

    private void a(Drawable drawable, e0 e0Var) {
        if (drawable != null && e0Var != null) {
            f.a(drawable, e0Var, this.a.getDrawableState());
        }
    }

    private static e0 a(Context context, f fVar, int i2) {
        ColorStateList b2 = fVar.b(context, i2);
        if (b2 == null) {
            return null;
        }
        e0 e0Var = new e0();
        e0Var.d = true;
        e0Var.a = b2;
        return e0Var;
    }

    /* access modifiers changed from: package-private */
    public void a(boolean z, int i2, int i3, int i4, int i5) {
        if (!b.a) {
            b();
        }
    }

    /* access modifiers changed from: package-private */
    public void a(int i2, float f2) {
        if (!b.a && !j()) {
            b(i2, f2);
        }
    }

    /* access modifiers changed from: package-private */
    public void a(int i2) {
        this.f308i.b(i2);
    }

    /* access modifiers changed from: package-private */
    public void a(int i2, int i3, int i4, int i5) {
        this.f308i.a(i2, i3, i4, i5);
    }

    /* access modifiers changed from: package-private */
    public void a(int[] iArr, int i2) {
        this.f308i.a(iArr, i2);
    }

    /* access modifiers changed from: package-private */
    public void a(ColorStateList colorStateList) {
        if (this.f307h == null) {
            this.f307h = new e0();
        }
        e0 e0Var = this.f307h;
        e0Var.a = colorStateList;
        e0Var.d = colorStateList != null;
        l();
    }

    /* access modifiers changed from: package-private */
    public void a(PorterDuff.Mode mode) {
        if (this.f307h == null) {
            this.f307h = new e0();
        }
        e0 e0Var = this.f307h;
        e0Var.b = mode;
        e0Var.c = mode != null;
        l();
    }

    private void a(Drawable drawable, Drawable drawable2, Drawable drawable3, Drawable drawable4, Drawable drawable5, Drawable drawable6) {
        if (Build.VERSION.SDK_INT >= 17 && (drawable5 != null || drawable6 != null)) {
            Drawable[] compoundDrawablesRelative = this.a.getCompoundDrawablesRelative();
            TextView textView = this.a;
            if (drawable5 == null) {
                drawable5 = compoundDrawablesRelative[0];
            }
            if (drawable2 == null) {
                drawable2 = compoundDrawablesRelative[1];
            }
            if (drawable6 == null) {
                drawable6 = compoundDrawablesRelative[2];
            }
            if (drawable4 == null) {
                drawable4 = compoundDrawablesRelative[3];
            }
            textView.setCompoundDrawablesRelativeWithIntrinsicBounds(drawable5, drawable2, drawable6, drawable4);
        } else if (drawable != null || drawable2 != null || drawable3 != null || drawable4 != null) {
            if (Build.VERSION.SDK_INT >= 17) {
                Drawable[] compoundDrawablesRelative2 = this.a.getCompoundDrawablesRelative();
                if (!(compoundDrawablesRelative2[0] == null && compoundDrawablesRelative2[2] == null)) {
                    TextView textView2 = this.a;
                    Drawable drawable7 = compoundDrawablesRelative2[0];
                    if (drawable2 == null) {
                        drawable2 = compoundDrawablesRelative2[1];
                    }
                    Drawable drawable8 = compoundDrawablesRelative2[2];
                    if (drawable4 == null) {
                        drawable4 = compoundDrawablesRelative2[3];
                    }
                    textView2.setCompoundDrawablesRelativeWithIntrinsicBounds(drawable7, drawable2, drawable8, drawable4);
                    return;
                }
            }
            Drawable[] compoundDrawables = this.a.getCompoundDrawables();
            TextView textView3 = this.a;
            if (drawable == null) {
                drawable = compoundDrawables[0];
            }
            if (drawable2 == null) {
                drawable2 = compoundDrawables[1];
            }
            if (drawable3 == null) {
                drawable3 = compoundDrawables[2];
            }
            if (drawable4 == null) {
                drawable4 = compoundDrawables[3];
            }
            textView3.setCompoundDrawablesWithIntrinsicBounds(drawable, drawable2, drawable3, drawable4);
        }
    }
}
