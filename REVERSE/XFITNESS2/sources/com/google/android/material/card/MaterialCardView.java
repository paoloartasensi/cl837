package com.google.android.material.card;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import androidx.cardview.widget.CardView;
import com.google.android.material.R$attr;
import com.google.android.material.R$style;
import com.google.android.material.R$styleable;
import com.google.android.material.internal.k;

public class MaterialCardView extends CardView {
    private final a n;

    public MaterialCardView(Context context) {
        this(context, (AttributeSet) null);
    }

    public int getStrokeColor() {
        return this.n.a();
    }

    public int getStrokeWidth() {
        return this.n.b();
    }

    public void setRadius(float f2) {
        super.setRadius(f2);
        this.n.c();
    }

    public void setStrokeColor(int i2) {
        this.n.a(i2);
    }

    public void setStrokeWidth(int i2) {
        this.n.b(i2);
    }

    public MaterialCardView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R$attr.materialCardViewStyle);
    }

    public MaterialCardView(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        TypedArray c = k.c(context, attributeSet, R$styleable.MaterialCardView, i2, R$style.Widget_MaterialComponents_CardView, new int[0]);
        a aVar = new a(this);
        this.n = aVar;
        aVar.a(c);
        c.recycle();
    }
}
