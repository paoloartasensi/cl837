package androidx.appcompat.widget;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.ImageView;
import androidx.appcompat.R$styleable;
import androidx.appcompat.a.a.a;
import androidx.core.widget.e;

/* compiled from: AppCompatImageHelper */
public class h {
    private final ImageView a;
    private e0 b;
    private e0 c;
    private e0 d;

    public h(ImageView imageView) {
        this.a = imageView;
    }

    private boolean e() {
        int i2 = Build.VERSION.SDK_INT;
        if (i2 <= 21) {
            return i2 == 21;
        }
        if (this.b != null) {
            return true;
        }
        return false;
    }

    public void a(AttributeSet attributeSet, int i2) {
        int g2;
        g0 a2 = g0.a(this.a.getContext(), attributeSet, R$styleable.AppCompatImageView, i2, 0);
        try {
            Drawable drawable = this.a.getDrawable();
            if (!(drawable != null || (g2 = a2.g(R$styleable.AppCompatImageView_srcCompat, -1)) == -1 || (drawable = a.c(this.a.getContext(), g2)) == null)) {
                this.a.setImageDrawable(drawable);
            }
            if (drawable != null) {
                q.b(drawable);
            }
            if (a2.g(R$styleable.AppCompatImageView_tint)) {
                e.a(this.a, a2.a(R$styleable.AppCompatImageView_tint));
            }
            if (a2.g(R$styleable.AppCompatImageView_tintMode)) {
                e.a(this.a, q.a(a2.d(R$styleable.AppCompatImageView_tintMode, -1), (PorterDuff.Mode) null));
            }
        } finally {
            a2.a();
        }
    }

    /* access modifiers changed from: package-private */
    public ColorStateList b() {
        e0 e0Var = this.c;
        if (e0Var != null) {
            return e0Var.a;
        }
        return null;
    }

    /* access modifiers changed from: package-private */
    public PorterDuff.Mode c() {
        e0 e0Var = this.c;
        if (e0Var != null) {
            return e0Var.b;
        }
        return null;
    }

    /* access modifiers changed from: package-private */
    public boolean d() {
        return Build.VERSION.SDK_INT < 21 || !(this.a.getBackground() instanceof RippleDrawable);
    }

    public void a(int i2) {
        if (i2 != 0) {
            Drawable c2 = a.c(this.a.getContext(), i2);
            if (c2 != null) {
                q.b(c2);
            }
            this.a.setImageDrawable(c2);
        } else {
            this.a.setImageDrawable((Drawable) null);
        }
        a();
    }

    /* access modifiers changed from: package-private */
    public void a(ColorStateList colorStateList) {
        if (this.c == null) {
            this.c = new e0();
        }
        e0 e0Var = this.c;
        e0Var.a = colorStateList;
        e0Var.d = true;
        a();
    }

    /* access modifiers changed from: package-private */
    public void a(PorterDuff.Mode mode) {
        if (this.c == null) {
            this.c = new e0();
        }
        e0 e0Var = this.c;
        e0Var.b = mode;
        e0Var.c = true;
        a();
    }

    /* access modifiers changed from: package-private */
    public void a() {
        Drawable drawable = this.a.getDrawable();
        if (drawable != null) {
            q.b(drawable);
        }
        if (drawable == null) {
            return;
        }
        if (!e() || !a(drawable)) {
            e0 e0Var = this.c;
            if (e0Var != null) {
                f.a(drawable, e0Var, this.a.getDrawableState());
                return;
            }
            e0 e0Var2 = this.b;
            if (e0Var2 != null) {
                f.a(drawable, e0Var2, this.a.getDrawableState());
            }
        }
    }

    private boolean a(Drawable drawable) {
        if (this.d == null) {
            this.d = new e0();
        }
        e0 e0Var = this.d;
        e0Var.a();
        ColorStateList a2 = e.a(this.a);
        if (a2 != null) {
            e0Var.d = true;
            e0Var.a = a2;
        }
        PorterDuff.Mode b2 = e.b(this.a);
        if (b2 != null) {
            e0Var.c = true;
            e0Var.b = b2;
        }
        if (!e0Var.d && !e0Var.c) {
            return false;
        }
        f.a(drawable, e0Var, this.a.getDrawableState());
        return true;
    }
}
