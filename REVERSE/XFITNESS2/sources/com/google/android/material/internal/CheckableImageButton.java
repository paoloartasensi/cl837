package com.google.android.material.internal;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import android.widget.Checkable;
import android.widget.ImageButton;
import androidx.appcompat.R$attr;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.core.h.e0.d;
import androidx.core.h.v;

public class CheckableImageButton extends AppCompatImageButton implements Checkable {

    /* renamed from: h  reason: collision with root package name */
    private static final int[] f1504h = {16842912};

    /* renamed from: g  reason: collision with root package name */
    private boolean f1505g;

    class a extends androidx.core.h.a {
        a() {
        }

        public void a(View view, d dVar) {
            super.a(view, dVar);
            dVar.c(true);
            dVar.d(CheckableImageButton.this.isChecked());
        }

        public void b(View view, AccessibilityEvent accessibilityEvent) {
            super.b(view, accessibilityEvent);
            accessibilityEvent.setChecked(CheckableImageButton.this.isChecked());
        }
    }

    public CheckableImageButton(Context context) {
        this(context, (AttributeSet) null);
    }

    public boolean isChecked() {
        return this.f1505g;
    }

    public int[] onCreateDrawableState(int i2) {
        if (this.f1505g) {
            return ImageButton.mergeDrawableStates(super.onCreateDrawableState(i2 + f1504h.length), f1504h);
        }
        return super.onCreateDrawableState(i2);
    }

    public void setChecked(boolean z) {
        if (this.f1505g != z) {
            this.f1505g = z;
            refreshDrawableState();
            sendAccessibilityEvent(2048);
        }
    }

    public void toggle() {
        setChecked(!this.f1505g);
    }

    public CheckableImageButton(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R$attr.imageButtonStyle);
    }

    public CheckableImageButton(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        v.a((View) this, (androidx.core.h.a) new a());
    }
}
