package com.chileaf.fitness.b;

import android.view.View;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.ViewDataBinding;
import com.chileaf.fitness.viewmodel.CL820ViewModel;

/* compiled from: ActivityCl820Binding */
public abstract class k extends ViewDataBinding {
    public final AppCompatTextView A;
    public final AppCompatTextView B;
    public final AppCompatTextView C;
    public final AppCompatTextView D;
    public final AppCompatTextView E;
    public final AppCompatTextView F;
    protected CL820ViewModel G;
    public final AppCompatTextView z;

    protected k(Object obj, View view, int i2, ConstraintLayout constraintLayout, AppCompatTextView appCompatTextView, AppCompatTextView appCompatTextView2, AppCompatTextView appCompatTextView3, AppCompatTextView appCompatTextView4, AppCompatTextView appCompatTextView5, AppCompatTextView appCompatTextView6, AppCompatTextView appCompatTextView7, AppCompatTextView appCompatTextView8, AppCompatTextView appCompatTextView9, AppCompatTextView appCompatTextView10) {
        super(obj, view, i2);
        this.z = appCompatTextView;
        this.A = appCompatTextView3;
        this.B = appCompatTextView6;
        this.C = appCompatTextView7;
        this.D = appCompatTextView8;
        this.E = appCompatTextView9;
        this.F = appCompatTextView10;
    }

    public abstract void a(CL820ViewModel cL820ViewModel);
}
