package com.google.android.material.internal;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import androidx.core.h.g;
import androidx.core.h.v;
import com.google.android.material.R$styleable;

public class FlowLayout extends ViewGroup {
    private int e;

    /* renamed from: f  reason: collision with root package name */
    private int f1506f;

    /* renamed from: g  reason: collision with root package name */
    private boolean f1507g;

    public FlowLayout(Context context) {
        this(context, (AttributeSet) null);
    }

    private void a(Context context, AttributeSet attributeSet) {
        TypedArray obtainStyledAttributes = context.getTheme().obtainStyledAttributes(attributeSet, R$styleable.FlowLayout, 0, 0);
        this.e = obtainStyledAttributes.getDimensionPixelSize(R$styleable.FlowLayout_lineSpacing, 0);
        this.f1506f = obtainStyledAttributes.getDimensionPixelSize(R$styleable.FlowLayout_itemSpacing, 0);
        obtainStyledAttributes.recycle();
    }

    /* access modifiers changed from: protected */
    public int getItemSpacing() {
        return this.f1506f;
    }

    /* access modifiers changed from: protected */
    public int getLineSpacing() {
        return this.e;
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i2, int i3, int i4, int i5) {
        int i6;
        int i7;
        if (getChildCount() != 0) {
            boolean z2 = true;
            if (v.o(this) != 1) {
                z2 = false;
            }
            int paddingRight = z2 ? getPaddingRight() : getPaddingLeft();
            int paddingLeft = z2 ? getPaddingLeft() : getPaddingRight();
            int paddingTop = getPaddingTop();
            int i8 = (i4 - i2) - paddingLeft;
            int i9 = paddingRight;
            int i10 = paddingTop;
            for (int i11 = 0; i11 < getChildCount(); i11++) {
                View childAt = getChildAt(i11);
                if (childAt.getVisibility() != 8) {
                    ViewGroup.LayoutParams layoutParams = childAt.getLayoutParams();
                    if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
                        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) layoutParams;
                        i6 = g.b(marginLayoutParams);
                        i7 = g.a(marginLayoutParams);
                    } else {
                        i7 = 0;
                        i6 = 0;
                    }
                    int measuredWidth = i9 + i6 + childAt.getMeasuredWidth();
                    if (!this.f1507g && measuredWidth > i8) {
                        i10 = this.e + paddingTop;
                        i9 = paddingRight;
                    }
                    int i12 = i9 + i6;
                    int measuredWidth2 = childAt.getMeasuredWidth() + i12;
                    int measuredHeight = childAt.getMeasuredHeight() + i10;
                    if (z2) {
                        childAt.layout(i8 - measuredWidth2, i10, (i8 - i9) - i6, measuredHeight);
                    } else {
                        childAt.layout(i12, i10, measuredWidth2, measuredHeight);
                    }
                    i9 += i6 + i7 + childAt.getMeasuredWidth() + this.f1506f;
                    paddingTop = measuredHeight;
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i2, int i3) {
        int i4;
        int i5;
        int i6;
        int size = View.MeasureSpec.getSize(i2);
        int mode = View.MeasureSpec.getMode(i2);
        int size2 = View.MeasureSpec.getSize(i3);
        int mode2 = View.MeasureSpec.getMode(i3);
        int i7 = (mode == Integer.MIN_VALUE || mode == 1073741824) ? size : Integer.MAX_VALUE;
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingRight = i7 - getPaddingRight();
        int i8 = paddingTop;
        int i9 = 0;
        for (int i10 = 0; i10 < getChildCount(); i10++) {
            View childAt = getChildAt(i10);
            if (childAt.getVisibility() == 8) {
                int i11 = i2;
                int i12 = i3;
            } else {
                measureChild(childAt, i2, i3);
                ViewGroup.LayoutParams layoutParams = childAt.getLayoutParams();
                if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
                    ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) layoutParams;
                    i5 = marginLayoutParams.leftMargin + 0;
                    i4 = marginLayoutParams.rightMargin + 0;
                } else {
                    i5 = 0;
                    i4 = 0;
                }
                int i13 = paddingLeft;
                if (paddingLeft + i5 + childAt.getMeasuredWidth() <= paddingRight || a()) {
                    i6 = i13;
                } else {
                    i6 = getPaddingLeft();
                    i8 = this.e + paddingTop;
                }
                int measuredWidth = i6 + i5 + childAt.getMeasuredWidth();
                int measuredHeight = i8 + childAt.getMeasuredHeight();
                if (measuredWidth > i9) {
                    i9 = measuredWidth;
                }
                paddingLeft = i6 + i5 + i4 + childAt.getMeasuredWidth() + this.f1506f;
                paddingTop = measuredHeight;
            }
        }
        setMeasuredDimension(a(size, mode, i9), a(size2, mode2, paddingTop));
    }

    /* access modifiers changed from: protected */
    public void setItemSpacing(int i2) {
        this.f1506f = i2;
    }

    /* access modifiers changed from: protected */
    public void setLineSpacing(int i2) {
        this.e = i2;
    }

    public void setSingleLine(boolean z) {
        this.f1507g = z;
    }

    public FlowLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public FlowLayout(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.f1507g = false;
        a(context, attributeSet);
    }

    /* access modifiers changed from: protected */
    public boolean a() {
        return this.f1507g;
    }

    private static int a(int i2, int i3, int i4) {
        if (i3 != Integer.MIN_VALUE) {
            return i3 != 1073741824 ? i4 : i2;
        }
        return Math.min(i4, i2);
    }
}
