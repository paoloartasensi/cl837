package com.google.android.material.button;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.drawable.a;
import androidx.core.h.v;
import androidx.core.widget.i;
import com.google.android.material.R$attr;
import com.google.android.material.R$style;
import com.google.android.material.R$styleable;
import com.google.android.material.internal.k;
import com.google.android.material.internal.l;

public class MaterialButton extends AppCompatButton {

    /* renamed from: g  reason: collision with root package name */
    private final b f1440g;

    /* renamed from: h  reason: collision with root package name */
    private int f1441h;

    /* renamed from: i  reason: collision with root package name */
    private PorterDuff.Mode f1442i;

    /* renamed from: j  reason: collision with root package name */
    private ColorStateList f1443j;
    private Drawable k;
    private int l;
    private int m;
    private int n;

    public MaterialButton(Context context) {
        this(context, (AttributeSet) null);
    }

    private boolean a() {
        return v.o(this) == 1;
    }

    private boolean b() {
        b bVar = this.f1440g;
        return bVar != null && !bVar.g();
    }

    private void c() {
        Drawable drawable = this.k;
        if (drawable != null) {
            Drawable mutate = drawable.mutate();
            this.k = mutate;
            a.a(mutate, this.f1443j);
            PorterDuff.Mode mode = this.f1442i;
            if (mode != null) {
                a.a(this.k, mode);
            }
            int i2 = this.l;
            if (i2 == 0) {
                i2 = this.k.getIntrinsicWidth();
            }
            int i3 = this.l;
            if (i3 == 0) {
                i3 = this.k.getIntrinsicHeight();
            }
            Drawable drawable2 = this.k;
            int i4 = this.m;
            drawable2.setBounds(i4, 0, i2 + i4, i3);
        }
        i.a(this, this.k, (Drawable) null, (Drawable) null, (Drawable) null);
    }

    public ColorStateList getBackgroundTintList() {
        return getSupportBackgroundTintList();
    }

    public PorterDuff.Mode getBackgroundTintMode() {
        return getSupportBackgroundTintMode();
    }

    public int getCornerRadius() {
        if (b()) {
            return this.f1440g.a();
        }
        return 0;
    }

    public Drawable getIcon() {
        return this.k;
    }

    public int getIconGravity() {
        return this.n;
    }

    public int getIconPadding() {
        return this.f1441h;
    }

    public int getIconSize() {
        return this.l;
    }

    public ColorStateList getIconTint() {
        return this.f1443j;
    }

    public PorterDuff.Mode getIconTintMode() {
        return this.f1442i;
    }

    public ColorStateList getRippleColor() {
        if (b()) {
            return this.f1440g.b();
        }
        return null;
    }

    public ColorStateList getStrokeColor() {
        if (b()) {
            return this.f1440g.c();
        }
        return null;
    }

    public int getStrokeWidth() {
        if (b()) {
            return this.f1440g.d();
        }
        return 0;
    }

    public ColorStateList getSupportBackgroundTintList() {
        if (b()) {
            return this.f1440g.e();
        }
        return super.getSupportBackgroundTintList();
    }

    public PorterDuff.Mode getSupportBackgroundTintMode() {
        if (b()) {
            return this.f1440g.f();
        }
        return super.getSupportBackgroundTintMode();
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (Build.VERSION.SDK_INT < 21 && b()) {
            this.f1440g.a(canvas);
        }
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i2, int i3, int i4, int i5) {
        b bVar;
        super.onLayout(z, i2, i3, i4, i5);
        if (Build.VERSION.SDK_INT == 21 && (bVar = this.f1440g) != null) {
            bVar.a(i5 - i3, i4 - i2);
        }
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i2, int i3) {
        super.onMeasure(i2, i3);
        if (this.k != null && this.n == 2) {
            int measureText = (int) getPaint().measureText(getText().toString());
            int i4 = this.l;
            if (i4 == 0) {
                i4 = this.k.getIntrinsicWidth();
            }
            int measuredWidth = (((((getMeasuredWidth() - measureText) - v.s(this)) - i4) - this.f1441h) - v.t(this)) / 2;
            if (a()) {
                measuredWidth = -measuredWidth;
            }
            if (this.m != measuredWidth) {
                this.m = measuredWidth;
                c();
            }
        }
    }

    public void setBackground(Drawable drawable) {
        setBackgroundDrawable(drawable);
    }

    public void setBackgroundColor(int i2) {
        if (b()) {
            this.f1440g.a(i2);
        } else {
            super.setBackgroundColor(i2);
        }
    }

    public void setBackgroundDrawable(Drawable drawable) {
        if (!b()) {
            super.setBackgroundDrawable(drawable);
        } else if (drawable != getBackground()) {
            Log.i("MaterialButton", "Setting a custom background is not supported.");
            this.f1440g.h();
            super.setBackgroundDrawable(drawable);
        } else {
            getBackground().setState(drawable.getState());
        }
    }

    public void setBackgroundResource(int i2) {
        setBackgroundDrawable(i2 != 0 ? androidx.appcompat.a.a.a.c(getContext(), i2) : null);
    }

    public void setBackgroundTintList(ColorStateList colorStateList) {
        setSupportBackgroundTintList(colorStateList);
    }

    public void setBackgroundTintMode(PorterDuff.Mode mode) {
        setSupportBackgroundTintMode(mode);
    }

    public void setCornerRadius(int i2) {
        if (b()) {
            this.f1440g.b(i2);
        }
    }

    public void setCornerRadiusResource(int i2) {
        if (b()) {
            setCornerRadius(getResources().getDimensionPixelSize(i2));
        }
    }

    public void setIcon(Drawable drawable) {
        if (this.k != drawable) {
            this.k = drawable;
            c();
        }
    }

    public void setIconGravity(int i2) {
        this.n = i2;
    }

    public void setIconPadding(int i2) {
        if (this.f1441h != i2) {
            this.f1441h = i2;
            setCompoundDrawablePadding(i2);
        }
    }

    public void setIconResource(int i2) {
        setIcon(i2 != 0 ? androidx.appcompat.a.a.a.c(getContext(), i2) : null);
    }

    public void setIconSize(int i2) {
        if (i2 < 0) {
            throw new IllegalArgumentException("iconSize cannot be less than 0");
        } else if (this.l != i2) {
            this.l = i2;
            c();
        }
    }

    public void setIconTint(ColorStateList colorStateList) {
        if (this.f1443j != colorStateList) {
            this.f1443j = colorStateList;
            c();
        }
    }

    public void setIconTintMode(PorterDuff.Mode mode) {
        if (this.f1442i != mode) {
            this.f1442i = mode;
            c();
        }
    }

    public void setIconTintResource(int i2) {
        setIconTint(androidx.appcompat.a.a.a.b(getContext(), i2));
    }

    /* access modifiers changed from: package-private */
    public void setInternalBackground(Drawable drawable) {
        super.setBackgroundDrawable(drawable);
    }

    public void setRippleColor(ColorStateList colorStateList) {
        if (b()) {
            this.f1440g.a(colorStateList);
        }
    }

    public void setRippleColorResource(int i2) {
        if (b()) {
            setRippleColor(androidx.appcompat.a.a.a.b(getContext(), i2));
        }
    }

    public void setStrokeColor(ColorStateList colorStateList) {
        if (b()) {
            this.f1440g.b(colorStateList);
        }
    }

    public void setStrokeColorResource(int i2) {
        if (b()) {
            setStrokeColor(androidx.appcompat.a.a.a.b(getContext(), i2));
        }
    }

    public void setStrokeWidth(int i2) {
        if (b()) {
            this.f1440g.c(i2);
        }
    }

    public void setStrokeWidthResource(int i2) {
        if (b()) {
            setStrokeWidth(getResources().getDimensionPixelSize(i2));
        }
    }

    public void setSupportBackgroundTintList(ColorStateList colorStateList) {
        if (b()) {
            this.f1440g.c(colorStateList);
        } else if (this.f1440g != null) {
            super.setSupportBackgroundTintList(colorStateList);
        }
    }

    public void setSupportBackgroundTintMode(PorterDuff.Mode mode) {
        if (b()) {
            this.f1440g.a(mode);
        } else if (this.f1440g != null) {
            super.setSupportBackgroundTintMode(mode);
        }
    }

    public MaterialButton(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R$attr.materialButtonStyle);
    }

    public MaterialButton(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        TypedArray c = k.c(context, attributeSet, R$styleable.MaterialButton, i2, R$style.Widget_MaterialComponents_Button, new int[0]);
        this.f1441h = c.getDimensionPixelSize(R$styleable.MaterialButton_iconPadding, 0);
        this.f1442i = l.a(c.getInt(R$styleable.MaterialButton_iconTintMode, -1), PorterDuff.Mode.SRC_IN);
        this.f1443j = com.google.android.material.f.a.a(getContext(), c, R$styleable.MaterialButton_iconTint);
        this.k = com.google.android.material.f.a.b(getContext(), c, R$styleable.MaterialButton_icon);
        this.n = c.getInteger(R$styleable.MaterialButton_iconGravity, 1);
        this.l = c.getDimensionPixelSize(R$styleable.MaterialButton_iconSize, 0);
        b bVar = new b(this);
        this.f1440g = bVar;
        bVar.a(c);
        c.recycle();
        setCompoundDrawablePadding(this.f1441h);
        c();
    }
}
