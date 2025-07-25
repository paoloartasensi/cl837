package com.chileaf.fitness.b;

import android.view.View;
import android.widget.ProgressBar;
import androidx.appcompat.widget.AppCompatSeekBar;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.chileaf.fitness.viewmodel.DevicesViewModel;

/* compiled from: ActivityDevicesBinding */
public abstract class y extends ViewDataBinding {
    public final q1 A;
    public final s1 B;
    public final ProgressBar C;
    public final RecyclerView D;
    public final AppCompatSeekBar E;
    public final SwipeRefreshLayout F;
    public final AppCompatTextView G;
    protected DevicesViewModel H;
    public final o1 z;

    protected y(Object obj, View view, int i2, o1 o1Var, q1 q1Var, s1 s1Var, ProgressBar progressBar, RecyclerView recyclerView, AppCompatSeekBar appCompatSeekBar, SwipeRefreshLayout swipeRefreshLayout, AppCompatTextView appCompatTextView) {
        super(obj, view, i2);
        this.z = o1Var;
        a((ViewDataBinding) o1Var);
        this.A = q1Var;
        a((ViewDataBinding) q1Var);
        this.B = s1Var;
        a((ViewDataBinding) s1Var);
        this.C = progressBar;
        this.D = recyclerView;
        this.E = appCompatSeekBar;
        this.F = swipeRefreshLayout;
        this.G = appCompatTextView;
    }

    public abstract void a(DevicesViewModel devicesViewModel);
}
