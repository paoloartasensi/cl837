package com.chileaf.fitness.b;

import android.view.View;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.ViewDataBinding;
import com.chileaf.fitness.viewmodel.CDNViewModel;

/* compiled from: ActivityCdnBinding */
public abstract class g extends ViewDataBinding {
    public final AppCompatTextView A;
    public final AppCompatTextView B;
    public final AppCompatTextView C;
    protected CDNViewModel D;
    public final AppCompatTextView z;

    protected g(Object obj, View view, int i2, AppCompatTextView appCompatTextView, AppCompatTextView appCompatTextView2, AppCompatTextView appCompatTextView3, AppCompatTextView appCompatTextView4, AppCompatTextView appCompatTextView5, AppCompatTextView appCompatTextView6, AppCompatTextView appCompatTextView7, AppCompatTextView appCompatTextView8, AppCompatTextView appCompatTextView9, AppCompatTextView appCompatTextView10) {
        super(obj, view, i2);
        this.z = appCompatTextView;
        this.A = appCompatTextView5;
        this.B = appCompatTextView7;
        this.C = appCompatTextView10;
    }

    public abstract void a(CDNViewModel cDNViewModel);
}
