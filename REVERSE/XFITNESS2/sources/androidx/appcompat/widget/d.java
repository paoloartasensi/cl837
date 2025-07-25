package androidx.appcompat.widget;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import androidx.appcompat.R$styleable;
import androidx.core.h.v;

/* compiled from: AppCompatBackgroundHelper */
class d {
    private final View a;
    private final f b;
    private int c = -1;
    private e0 d;
    private e0 e;

    /* renamed from: f  reason: collision with root package name */
    private e0 f285f;

    d(View view) {
        this.a = view;
        this.b = f.b();
    }

    private boolean d() {
        int i2 = Build.VERSION.SDK_INT;
        if (i2 <= 21) {
            return i2 == 21;
        }
        if (this.d != null) {
            return true;
        }
        return false;
    }

    /* access modifiers changed from: package-private */
    public void a(AttributeSet attributeSet, int i2) {
        g0 a2 = g0.a(this.a.getContext(), attributeSet, R$styleable.ViewBackgroundHelper, i2, 0);
        try {
            if (a2.g(R$styleable.ViewBackgroundHelper_android_background)) {
                this.c = a2.g(R$styleable.ViewBackgroundHelper_android_background, -1);
                ColorStateList b2 = this.b.b(this.a.getContext(), this.c);
                if (b2 != null) {
                    a(b2);
                }
            }
            if (a2.g(R$styleable.ViewBackgroundHelper_backgroundTint)) {
                v.a(this.a, a2.a(R$styleable.ViewBackgroundHelper_backgroundTint));
            }
            if (a2.g(R$styleable.ViewBackgroundHelper_backgroundTintMode)) {
                v.a(this.a, q.a(a2.d(R$styleable.ViewBackgroundHelper_backgroundTintMode, -1), (PorterDuff.Mode) null));
            }
        } finally {
            a2.a();
        }
    }

    /* access modifiers changed from: package-private */
    public void b(ColorStateList colorStateList) {
        if (this.e == null) {
            this.e = new e0();
        }
        e0 e0Var = this.e;
        e0Var.a = colorStateList;
        e0Var.d = true;
        a();
    }

    /* access modifiers changed from: package-private */
    public PorterDuff.Mode c() {
        e0 e0Var = this.e;
        if (e0Var != null) {
            return e0Var.b;
        }
        return null;
    }

    /* access modifiers changed from: package-private */
    public ColorStateList b() {
        e0 e0Var = this.e;
        if (e0Var != null) {
            return e0Var.a;
        }
        return null;
    }

    private boolean b(Drawable drawable) {
        if (this.f285f == null) {
            this.f285f = new e0();
        }
        e0 e0Var = this.f285f;
        e0Var.a();
        ColorStateList g2 = v.g(this.a);
        if (g2 != null) {
            e0Var.d = true;
            e0Var.a = g2;
        }
        PorterDuff.Mode h2 = v.h(this.a);
        if (h2 != null) {
            e0Var.c = true;
            e0Var.b = h2;
        }
        if (!e0Var.d && !e0Var.c) {
            return false;
        }
        f.a(drawable, e0Var, this.a.getDrawableState());
        return true;
    }

    /* access modifiers changed from: package-private */
    public void a(int i2) {
        this.c = i2;
        f fVar = this.b;
        a(fVar != null ? fVar.b(this.a.getContext(), i2) : null);
        a();
    }

    /* access modifiers changed from: package-private */
    public void a(Drawable drawable) {
        this.c = -1;
        a((ColorStateList) null);
        a();
    }

    /* access modifiers changed from: package-private */
    public void a(PorterDuff.Mode mode) {
        if (this.e == null) {
            this.e = new e0();
        }
        e0 e0Var = this.e;
        e0Var.b = mode;
        e0Var.c = true;
        a();
    }

    /* access modifiers changed from: package-private */
    public void a() {
        Drawable background = this.a.getBackground();
        if (background == null) {
            return;
        }
        if (!d() || !b(background)) {
            e0 e0Var = this.e;
            if (e0Var != null) {
                f.a(background, e0Var, this.a.getDrawableState());
                return;
            }
            e0 e0Var2 = this.d;
            if (e0Var2 != null) {
                f.a(background, e0Var2, this.a.getDrawableState());
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void a(ColorStateList colorStateList) {
        if (colorStateList != null) {
            if (this.d == null) {
                this.d = new e0();
            }
            e0 e0Var = this.d;
            e0Var.a = colorStateList;
            e0Var.d = true;
        } else {
            this.d = null;
        }
        a();
    }
}
