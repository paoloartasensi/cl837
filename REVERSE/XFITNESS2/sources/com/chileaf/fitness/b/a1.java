package com.chileaf.fitness.b;

import android.view.View;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.databinding.ViewDataBinding;
import com.google.android.material.button.MaterialButton;

/* compiled from: FragmentRidingProductBinding */
public abstract class a1 extends ViewDataBinding {
    public final AppCompatImageView A;
    public final TextView B;
    public final MaterialButton z;

    protected a1(Object obj, View view, int i2, MaterialButton materialButton, AppCompatImageView appCompatImageView, TextView textView) {
        super(obj, view, i2);
        this.z = materialButton;
        this.A = appCompatImageView;
        this.B = textView;
    }
}
