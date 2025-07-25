package com.google.android.material.circularreveal;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.GridLayout;
import com.google.android.material.circularreveal.c;

public class CircularRevealGridLayout extends GridLayout implements c {
    private final b e;

    public CircularRevealGridLayout(Context context) {
        this(context, (AttributeSet) null);
    }

    public void a() {
        this.e.a();
    }

    public void b() {
        this.e.b();
    }

    public boolean c() {
        return super.isOpaque();
    }

    public void draw(Canvas canvas) {
        b bVar = this.e;
        if (bVar != null) {
            bVar.a(canvas);
        } else {
            super.draw(canvas);
        }
    }

    public Drawable getCircularRevealOverlayDrawable() {
        return this.e.c();
    }

    public int getCircularRevealScrimColor() {
        return this.e.d();
    }

    public c.e getRevealInfo() {
        return this.e.e();
    }

    public boolean isOpaque() {
        b bVar = this.e;
        if (bVar != null) {
            return bVar.f();
        }
        return super.isOpaque();
    }

    public void setCircularRevealOverlayDrawable(Drawable drawable) {
        this.e.a(drawable);
    }

    public void setCircularRevealScrimColor(int i2) {
        this.e.a(i2);
    }

    public void setRevealInfo(c.e eVar) {
        this.e.a(eVar);
    }

    public CircularRevealGridLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.e = new b(this);
    }

    public void a(Canvas canvas) {
        super.draw(canvas);
    }
}
