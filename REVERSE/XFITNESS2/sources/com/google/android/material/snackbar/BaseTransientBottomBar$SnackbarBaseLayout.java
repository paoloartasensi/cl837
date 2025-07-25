package com.google.android.material.snackbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.accessibility.AccessibilityManager;
import android.widget.FrameLayout;
import androidx.core.h.e0.c;
import androidx.core.h.v;
import com.google.android.material.R$styleable;

public class BaseTransientBottomBar$SnackbarBaseLayout extends FrameLayout {
    private final AccessibilityManager e;

    /* renamed from: f  reason: collision with root package name */
    private final c.a f1529f;

    /* renamed from: g  reason: collision with root package name */
    private c f1530g;

    /* renamed from: h  reason: collision with root package name */
    private b f1531h;

    class a implements c.a {
        a() {
        }

        public void onTouchExplorationStateChanged(boolean z) {
            BaseTransientBottomBar$SnackbarBaseLayout.this.setClickableOrFocusableBasedOnAccessibility(z);
        }
    }

    protected BaseTransientBottomBar$SnackbarBaseLayout(Context context) {
        this(context, (AttributeSet) null);
    }

    /* access modifiers changed from: private */
    public void setClickableOrFocusableBasedOnAccessibility(boolean z) {
        setClickable(!z);
        setFocusable(z);
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        b bVar = this.f1531h;
        if (bVar != null) {
            bVar.onViewAttachedToWindow(this);
        }
        v.I(this);
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        b bVar = this.f1531h;
        if (bVar != null) {
            bVar.onViewDetachedFromWindow(this);
        }
        c.b(this.e, this.f1529f);
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i2, int i3, int i4, int i5) {
        super.onLayout(z, i2, i3, i4, i5);
        c cVar = this.f1530g;
        if (cVar != null) {
            cVar.a(this, i2, i3, i4, i5);
        }
    }

    /* access modifiers changed from: package-private */
    public void setOnAttachStateChangeListener(b bVar) {
        this.f1531h = bVar;
    }

    /* access modifiers changed from: package-private */
    public void setOnLayoutChangeListener(c cVar) {
        this.f1530g = cVar;
    }

    protected BaseTransientBottomBar$SnackbarBaseLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.SnackbarLayout);
        if (obtainStyledAttributes.hasValue(R$styleable.SnackbarLayout_elevation)) {
            v.a((View) this, (float) obtainStyledAttributes.getDimensionPixelSize(R$styleable.SnackbarLayout_elevation, 0));
        }
        obtainStyledAttributes.recycle();
        this.e = (AccessibilityManager) context.getSystemService("accessibility");
        a aVar = new a();
        this.f1529f = aVar;
        c.a(this.e, aVar);
        setClickableOrFocusableBasedOnAccessibility(this.e.isTouchExplorationEnabled());
    }
}
