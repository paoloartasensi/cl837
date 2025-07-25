package com.google.android.material.internal;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import androidx.core.h.d0;
import androidx.core.h.r;
import androidx.core.h.v;
import com.google.android.material.R$style;
import com.google.android.material.R$styleable;

public class ScrimInsetsFrameLayout extends FrameLayout {
    Drawable e;

    /* renamed from: f  reason: collision with root package name */
    Rect f1508f;

    /* renamed from: g  reason: collision with root package name */
    private Rect f1509g;

    class a implements r {
        a() {
        }

        public d0 a(View view, d0 d0Var) {
            ScrimInsetsFrameLayout scrimInsetsFrameLayout = ScrimInsetsFrameLayout.this;
            if (scrimInsetsFrameLayout.f1508f == null) {
                scrimInsetsFrameLayout.f1508f = new Rect();
            }
            ScrimInsetsFrameLayout.this.f1508f.set(d0Var.c(), d0Var.e(), d0Var.d(), d0Var.b());
            ScrimInsetsFrameLayout.this.a(d0Var);
            ScrimInsetsFrameLayout.this.setWillNotDraw(!d0Var.f() || ScrimInsetsFrameLayout.this.e == null);
            v.H(ScrimInsetsFrameLayout.this);
            return d0Var.a();
        }
    }

    public ScrimInsetsFrameLayout(Context context) {
        this(context, (AttributeSet) null);
    }

    /* access modifiers changed from: protected */
    public void a(d0 d0Var) {
    }

    public void draw(Canvas canvas) {
        super.draw(canvas);
        int width = getWidth();
        int height = getHeight();
        if (this.f1508f != null && this.e != null) {
            int save = canvas.save();
            canvas.translate((float) getScrollX(), (float) getScrollY());
            this.f1509g.set(0, 0, width, this.f1508f.top);
            this.e.setBounds(this.f1509g);
            this.e.draw(canvas);
            this.f1509g.set(0, height - this.f1508f.bottom, width, height);
            this.e.setBounds(this.f1509g);
            this.e.draw(canvas);
            Rect rect = this.f1509g;
            Rect rect2 = this.f1508f;
            rect.set(0, rect2.top, rect2.left, height - rect2.bottom);
            this.e.setBounds(this.f1509g);
            this.e.draw(canvas);
            Rect rect3 = this.f1509g;
            Rect rect4 = this.f1508f;
            rect3.set(width - rect4.right, rect4.top, width, height - rect4.bottom);
            this.e.setBounds(this.f1509g);
            this.e.draw(canvas);
            canvas.restoreToCount(save);
        }
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        Drawable drawable = this.e;
        if (drawable != null) {
            drawable.setCallback(this);
        }
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Drawable drawable = this.e;
        if (drawable != null) {
            drawable.setCallback((Drawable.Callback) null);
        }
    }

    public ScrimInsetsFrameLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ScrimInsetsFrameLayout(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.f1509g = new Rect();
        TypedArray c = k.c(context, attributeSet, R$styleable.ScrimInsetsFrameLayout, i2, R$style.Widget_Design_ScrimInsetsFrameLayout, new int[0]);
        this.e = c.getDrawable(R$styleable.ScrimInsetsFrameLayout_insetForeground);
        c.recycle();
        setWillNotDraw(true);
        v.a((View) this, (r) new a());
    }
}
