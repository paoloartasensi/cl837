package com.chileaf.fitness.b;

import android.view.View;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.ViewDataBinding;
import com.chileaf.fitness.viewmodel.BoxingViewModel;

/* compiled from: ActivityBoxingBinding */
public abstract class e extends ViewDataBinding {
    protected BoxingViewModel A;
    public final AppCompatTextView z;

    protected e(Object obj, View view, int i2, AppCompatTextView appCompatTextView) {
        super(obj, view, i2);
        this.z = appCompatTextView;
    }

    public abstract void a(BoxingViewModel boxingViewModel);
}
