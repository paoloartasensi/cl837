package com.chileaf.fitness.b;

import android.view.View;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.ViewDataBinding;
import com.chileaf.fitness.widget.SwitchButton;

/* compiled from: FragmentTrainSettingBinding */
public abstract class g1 extends ViewDataBinding {
    public final ConstraintLayout A;
    public final SwitchButton B;
    public final TextView C;
    public final ConstraintLayout z;

    protected g1(Object obj, View view, int i2, ConstraintLayout constraintLayout, ConstraintLayout constraintLayout2, SwitchButton switchButton, TextView textView) {
        super(obj, view, i2);
        this.z = constraintLayout;
        this.A = constraintLayout2;
        this.B = switchButton;
        this.C = textView;
    }
}
