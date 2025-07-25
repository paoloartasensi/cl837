package com.google.android.material.internal;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageButton;

public class VisibilityAwareImageButton extends ImageButton {
    private int e;

    public VisibilityAwareImageButton(Context context) {
        this(context, (AttributeSet) null);
    }

    public final void a(int i2, boolean z) {
        super.setVisibility(i2);
        if (z) {
            this.e = i2;
        }
    }

    public final int getUserSetVisibility() {
        return this.e;
    }

    public void setVisibility(int i2) {
        a(i2, true);
    }

    public VisibilityAwareImageButton(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public VisibilityAwareImageButton(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.e = getVisibility();
    }
}
