package com.google.android.material.card;

import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import com.google.android.material.R$styleable;

/* compiled from: MaterialCardViewHelper */
class a {
    private final MaterialCardView a;
    private int b;
    private int c;

    public a(MaterialCardView materialCardView) {
        this.a = materialCardView;
    }

    private void d() {
        this.a.a(this.a.getContentPaddingLeft() + this.c, this.a.getContentPaddingTop() + this.c, this.a.getContentPaddingRight() + this.c, this.a.getContentPaddingBottom() + this.c);
    }

    private Drawable e() {
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setCornerRadius(this.a.getRadius());
        int i2 = this.b;
        if (i2 != -1) {
            gradientDrawable.setStroke(this.c, i2);
        }
        return gradientDrawable;
    }

    public void a(TypedArray typedArray) {
        this.b = typedArray.getColor(R$styleable.MaterialCardView_strokeColor, -1);
        this.c = typedArray.getDimensionPixelSize(R$styleable.MaterialCardView_strokeWidth, 0);
        c();
        d();
    }

    /* access modifiers changed from: package-private */
    public void b(int i2) {
        this.c = i2;
        c();
        d();
    }

    /* access modifiers changed from: package-private */
    public void c() {
        this.a.setForeground(e());
    }

    /* access modifiers changed from: package-private */
    public int b() {
        return this.c;
    }

    /* access modifiers changed from: package-private */
    public void a(int i2) {
        this.b = i2;
        c();
    }

    /* access modifiers changed from: package-private */
    public int a() {
        return this.b;
    }
}
