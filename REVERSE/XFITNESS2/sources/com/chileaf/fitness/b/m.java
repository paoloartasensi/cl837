package com.chileaf.fitness.b;

import android.view.View;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.ViewDataBinding;
import com.chileaf.fitness.viewmodel.CL830ViewModel;
import com.chileaf.fitness.widget.SwitchButton;
import com.github.mikephil.charting.charts.LineChart;

/* compiled from: ActivityCl830Binding */
public abstract class m extends ViewDataBinding {
    public final SwitchButton A;
    public final AppCompatTextView B;
    public final AppCompatTextView C;
    public final AppCompatTextView D;
    protected CL830ViewModel E;
    public final LineChart z;

    protected m(Object obj, View view, int i2, LineChart lineChart, ConstraintLayout constraintLayout, SwitchButton switchButton, AppCompatTextView appCompatTextView, AppCompatTextView appCompatTextView2, AppCompatTextView appCompatTextView3, AppCompatTextView appCompatTextView4, AppCompatTextView appCompatTextView5, AppCompatTextView appCompatTextView6) {
        super(obj, view, i2);
        this.z = lineChart;
        this.A = switchButton;
        this.B = appCompatTextView;
        this.C = appCompatTextView3;
        this.D = appCompatTextView6;
    }

    public abstract void a(CL830ViewModel cL830ViewModel);
}
