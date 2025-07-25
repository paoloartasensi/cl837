package androidx.appcompat.widget;

import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.SeekBar;
import androidx.appcompat.R$styleable;
import androidx.core.graphics.drawable.a;
import androidx.core.h.v;

/* compiled from: AppCompatSeekBarHelper */
class k extends j {
    private final SeekBar d;
    private Drawable e;

    /* renamed from: f  reason: collision with root package name */
    private ColorStateList f299f = null;

    /* renamed from: g  reason: collision with root package name */
    private PorterDuff.Mode f300g = null;

    /* renamed from: h  reason: collision with root package name */
    private boolean f301h = false;

    /* renamed from: i  reason: collision with root package name */
    private boolean f302i = false;

    k(SeekBar seekBar) {
        super(seekBar);
        this.d = seekBar;
    }

    private void d() {
        if (this.e == null) {
            return;
        }
        if (this.f301h || this.f302i) {
            Drawable i2 = a.i(this.e.mutate());
            this.e = i2;
            if (this.f301h) {
                a.a(i2, this.f299f);
            }
            if (this.f302i) {
                a.a(this.e, this.f300g);
            }
            if (this.e.isStateful()) {
                this.e.setState(this.d.getDrawableState());
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void a(AttributeSet attributeSet, int i2) {
        super.a(attributeSet, i2);
        g0 a = g0.a(this.d.getContext(), attributeSet, R$styleable.AppCompatSeekBar, i2, 0);
        Drawable c = a.c(R$styleable.AppCompatSeekBar_android_thumb);
        if (c != null) {
            this.d.setThumb(c);
        }
        a(a.b(R$styleable.AppCompatSeekBar_tickMark));
        if (a.g(R$styleable.AppCompatSeekBar_tickMarkTintMode)) {
            this.f300g = q.a(a.d(R$styleable.AppCompatSeekBar_tickMarkTintMode, -1), this.f300g);
            this.f302i = true;
        }
        if (a.g(R$styleable.AppCompatSeekBar_tickMarkTint)) {
            this.f299f = a.a(R$styleable.AppCompatSeekBar_tickMarkTint);
            this.f301h = true;
        }
        a.a();
        d();
    }

    /* access modifiers changed from: package-private */
    public void b() {
        Drawable drawable = this.e;
        if (drawable != null && drawable.isStateful() && drawable.setState(this.d.getDrawableState())) {
            this.d.invalidateDrawable(drawable);
        }
    }

    /* access modifiers changed from: package-private */
    public void c() {
        Drawable drawable = this.e;
        if (drawable != null) {
            drawable.jumpToCurrentState();
        }
    }

    /* access modifiers changed from: package-private */
    public void a(Drawable drawable) {
        Drawable drawable2 = this.e;
        if (drawable2 != null) {
            drawable2.setCallback((Drawable.Callback) null);
        }
        this.e = drawable;
        if (drawable != null) {
            drawable.setCallback(this.d);
            a.a(drawable, v.o(this.d));
            if (drawable.isStateful()) {
                drawable.setState(this.d.getDrawableState());
            }
            d();
        }
        this.d.invalidate();
    }

    /* access modifiers changed from: package-private */
    public void a(Canvas canvas) {
        if (this.e != null) {
            int max = this.d.getMax();
            int i2 = 1;
            if (max > 1) {
                int intrinsicWidth = this.e.getIntrinsicWidth();
                int intrinsicHeight = this.e.getIntrinsicHeight();
                int i3 = intrinsicWidth >= 0 ? intrinsicWidth / 2 : 1;
                if (intrinsicHeight >= 0) {
                    i2 = intrinsicHeight / 2;
                }
                this.e.setBounds(-i3, -i2, i3, i2);
                float width = ((float) ((this.d.getWidth() - this.d.getPaddingLeft()) - this.d.getPaddingRight())) / ((float) max);
                int save = canvas.save();
                canvas.translate((float) this.d.getPaddingLeft(), (float) (this.d.getHeight() / 2));
                for (int i4 = 0; i4 <= max; i4++) {
                    this.e.draw(canvas);
                    canvas.translate(width, 0.0f);
                }
                canvas.restoreToCount(save);
            }
        }
    }
}
