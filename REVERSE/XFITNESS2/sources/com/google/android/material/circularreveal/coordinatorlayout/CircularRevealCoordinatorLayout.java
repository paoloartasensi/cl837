package com.google.android.material.circularreveal.coordinatorlayout;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import com.google.android.material.circularreveal.b;
import com.google.android.material.circularreveal.c;

public class CircularRevealCoordinatorLayout extends CoordinatorLayout implements c {
    private final b C;

    public CircularRevealCoordinatorLayout(Context context) {
        this(context, (AttributeSet) null);
    }

    public void a() {
        this.C.a();
    }

    public void b() {
        this.C.b();
    }

    public boolean c() {
        return super.isOpaque();
    }

    public void draw(Canvas canvas) {
        b bVar = this.C;
        if (bVar != null) {
            bVar.a(canvas);
        } else {
            super.draw(canvas);
        }
    }

    public Drawable getCircularRevealOverlayDrawable() {
        return this.C.c();
    }

    public int getCircularRevealScrimColor() {
        return this.C.d();
    }

    public c.e getRevealInfo() {
        return this.C.e();
    }

    public boolean isOpaque() {
        b bVar = this.C;
        if (bVar != null) {
            return bVar.f();
        }
        return super.isOpaque();
    }

    public void setCircularRevealOverlayDrawable(Drawable drawable) {
        this.C.a(drawable);
    }

    public void setCircularRevealScrimColor(int i2) {
        this.C.a(i2);
    }

    public void setRevealInfo(c.e eVar) {
        this.C.a(eVar);
    }

    public CircularRevealCoordinatorLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.C = new b(this);
    }

    public void a(Canvas canvas) {
        super.draw(canvas);
    }
}
