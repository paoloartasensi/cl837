package com.chileaf.fitness.b;

import android.view.View;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.ViewDataBinding;
import com.chileaf.fitness.viewmodel.CL800ViewModel;
import com.github.mikephil.charting.charts.LineChart;

/* compiled from: ActivityCl800Binding */
public abstract class i extends ViewDataBinding {
    public final AppCompatTextView A;
    public final AppCompatTextView B;
    public final AppCompatTextView C;
    protected CL800ViewModel D;
    public final LineChart z;

    protected i(Object obj, View view, int i2, LineChart lineChart, ConstraintLayout constraintLayout, AppCompatTextView appCompatTextView, AppCompatTextView appCompatTextView2, AppCompatTextView appCompatTextView3, AppCompatTextView appCompatTextView4, AppCompatTextView appCompatTextView5, AppCompatTextView appCompatTextView6) {
        super(obj, view, i2);
        this.z = lineChart;
        this.A = appCompatTextView;
        this.B = appCompatTextView3;
        this.C = appCompatTextView6;
    }

    public abstract void a(CL800ViewModel cL800ViewModel);
}
