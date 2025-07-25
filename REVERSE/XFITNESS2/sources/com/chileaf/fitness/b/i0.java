package com.chileaf.fitness.b;

import android.view.View;
import android.widget.ProgressBar;
import androidx.databinding.ViewDataBinding;
import com.chileaf.fitness.widget.NestedScrollWebView;

/* compiled from: ActivityWebBinding */
public abstract class i0 extends ViewDataBinding {
    public final NestedScrollWebView A;
    public final ProgressBar z;

    protected i0(Object obj, View view, int i2, ProgressBar progressBar, NestedScrollWebView nestedScrollWebView) {
        super(obj, view, i2);
        this.z = progressBar;
        this.A = nestedScrollWebView;
    }
}
