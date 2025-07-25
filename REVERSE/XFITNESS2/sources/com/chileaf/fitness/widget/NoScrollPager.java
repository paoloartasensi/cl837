package com.chileaf.fitness.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import androidx.viewpager.widget.ViewPager;

public class NoScrollPager extends ViewPager {
    public NoScrollPager(Context context) {
        super(context);
    }

    /* access modifiers changed from: protected */
    public boolean a(View view, boolean z, int i2, int i3, int i4) {
        return false;
    }

    public NoScrollPager(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }
}
