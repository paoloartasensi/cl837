package com.google.android.material.tabs;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import androidx.appcompat.widget.g0;
import com.google.android.material.R$styleable;

public class TabItem extends View {
    public final CharSequence e;

    /* renamed from: f  reason: collision with root package name */
    public final Drawable f1536f;

    /* renamed from: g  reason: collision with root package name */
    public final int f1537g;

    public TabItem(Context context) {
        this(context, (AttributeSet) null);
    }

    public TabItem(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        g0 a = g0.a(context, attributeSet, R$styleable.TabItem);
        this.e = a.e(R$styleable.TabItem_android_text);
        this.f1536f = a.b(R$styleable.TabItem_android_icon);
        this.f1537g = a.g(R$styleable.TabItem_android_layout, 0);
        a.a();
    }
}
