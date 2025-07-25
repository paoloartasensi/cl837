package com.google.android.material.circularreveal.cardview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import androidx.cardview.widget.CardView;
import com.google.android.material.circularreveal.b;
import com.google.android.material.circularreveal.c;

public class CircularRevealCardView extends CardView implements c {
    private final b n;

    public CircularRevealCardView(Context context) {
        this(context, (AttributeSet) null);
    }

    public void a() {
        this.n.a();
    }

    public void b() {
        this.n.b();
    }

    public boolean c() {
        return super.isOpaque();
    }

    public void draw(Canvas canvas) {
        b bVar = this.n;
        if (bVar != null) {
            bVar.a(canvas);
        } else {
            super.draw(canvas);
        }
    }

    public Drawable getCircularRevealOverlayDrawable() {
        return this.n.c();
    }

    public int getCircularRevealScrimColor() {
        return this.n.d();
    }

    public c.e getRevealInfo() {
        return this.n.e();
    }

    public boolean isOpaque() {
        b bVar = this.n;
        if (bVar != null) {
            return bVar.f();
        }
        return super.isOpaque();
    }

    public void setCircularRevealOverlayDrawable(Drawable drawable) {
        this.n.a(drawable);
    }

    public void setCircularRevealScrimColor(int i2) {
        this.n.a(i2);
    }

    public void setRevealInfo(c.e eVar) {
        this.n.a(eVar);
    }

    public CircularRevealCardView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.n = new b(this);
    }

    public void a(Canvas canvas) {
        super.draw(canvas);
    }
}
