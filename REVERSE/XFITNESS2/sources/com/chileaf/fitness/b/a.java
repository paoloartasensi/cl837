package com.chileaf.fitness.b;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TimePicker;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatCheckedTextView;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.core.widget.NestedScrollView;
import androidx.databinding.ViewDataBinding;

/* compiled from: ActivityAlarmEditBinding */
public abstract class a extends ViewDataBinding {
    public final AppCompatCheckedTextView A;
    public final AppCompatCheckedTextView B;
    public final AppCompatCheckedTextView C;
    public final AppCompatCheckedTextView D;
    public final AppCompatCheckedTextView E;
    public final AppCompatCheckedTextView F;
    public final AppCompatEditText G;
    public final AppCompatCheckedTextView H;
    public final AppCompatButton I;
    public final AppCompatButton J;
    public final TimePicker K;
    public final AppCompatCheckedTextView z;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    protected a(Object obj, View view, int i2, AppCompatCheckedTextView appCompatCheckedTextView, AppCompatCheckedTextView appCompatCheckedTextView2, AppCompatCheckedTextView appCompatCheckedTextView3, AppCompatCheckedTextView appCompatCheckedTextView4, AppCompatCheckedTextView appCompatCheckedTextView5, AppCompatCheckedTextView appCompatCheckedTextView6, AppCompatCheckedTextView appCompatCheckedTextView7, AppCompatEditText appCompatEditText, AppCompatCheckedTextView appCompatCheckedTextView8, AppCompatButton appCompatButton, AppCompatButton appCompatButton2, TimePicker timePicker, LinearLayout linearLayout, NestedScrollView nestedScrollView) {
        super(obj, view, i2);
        this.z = appCompatCheckedTextView;
        this.A = appCompatCheckedTextView2;
        this.B = appCompatCheckedTextView3;
        this.C = appCompatCheckedTextView4;
        this.D = appCompatCheckedTextView5;
        this.E = appCompatCheckedTextView6;
        this.F = appCompatCheckedTextView7;
        this.G = appCompatEditText;
        this.H = appCompatCheckedTextView8;
        this.I = appCompatButton;
        this.J = appCompatButton2;
        this.K = timePicker;
    }
}
