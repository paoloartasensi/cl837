package com.chileaf.fitness.b;

import android.view.View;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.ViewDataBinding;

/* compiled from: ItemDeviceBinding */
public abstract class u1 extends ViewDataBinding {
    public final AppCompatImageView A;
    public final AppCompatTextView B;
    public final AppCompatTextView C;
    public final AppCompatImageView D;
    public final AppCompatTextView z;

    protected u1(Object obj, View view, int i2, AppCompatTextView appCompatTextView, AppCompatImageView appCompatImageView, AppCompatTextView appCompatTextView2, AppCompatTextView appCompatTextView3, AppCompatImageView appCompatImageView2, AppCompatImageView appCompatImageView3) {
        super(obj, view, i2);
        this.z = appCompatTextView;
        this.A = appCompatImageView;
        this.B = appCompatTextView2;
        this.C = appCompatTextView3;
        this.D = appCompatImageView2;
    }
}
