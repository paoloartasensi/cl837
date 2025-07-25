package com.chileaf.fitness.b;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.ViewDataBinding;
import com.chileaf.fitness.viewmodel.WeightViewModel;
import com.google.android.material.button.MaterialButton;

/* compiled from: ActivityWeightBinding */
public abstract class k0 extends ViewDataBinding {
    public final AppCompatEditText A;
    public final AppCompatEditText B;
    public final AppCompatRadioButton C;
    public final AppCompatRadioButton D;
    public final AppCompatRadioButton E;
    public final AppCompatRadioButton F;
    public final AppCompatRadioButton G;
    public final AppCompatRadioButton H;
    public final RadioGroup I;
    public final RadioGroup J;
    public final AppCompatTextView K;
    public final AppCompatTextView L;
    public final AppCompatTextView M;
    public final AppCompatTextView N;
    public final AppCompatTextView O;
    public final AppCompatTextView P;
    public final AppCompatTextView Q;
    public final AppCompatTextView R;
    public final AppCompatTextView S;
    public final AppCompatTextView T;
    public final AppCompatTextView U;
    public final AppCompatTextView V;
    public final AppCompatTextView W;
    public final AppCompatTextView X;
    protected WeightViewModel Y;
    public final MaterialButton z;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    protected k0(Object obj, View view, int i2, MaterialButton materialButton, AppCompatEditText appCompatEditText, AppCompatEditText appCompatEditText2, LinearLayout linearLayout, LinearLayout linearLayout2, LinearLayout linearLayout3, LinearLayout linearLayout4, LinearLayout linearLayout5, LinearLayout linearLayout6, LinearLayout linearLayout7, AppCompatRadioButton appCompatRadioButton, AppCompatRadioButton appCompatRadioButton2, AppCompatRadioButton appCompatRadioButton3, AppCompatRadioButton appCompatRadioButton4, AppCompatRadioButton appCompatRadioButton5, AppCompatRadioButton appCompatRadioButton6, RadioGroup radioGroup, RadioGroup radioGroup2, AppCompatTextView appCompatTextView, AppCompatTextView appCompatTextView2, AppCompatTextView appCompatTextView3, AppCompatTextView appCompatTextView4, AppCompatTextView appCompatTextView5, AppCompatTextView appCompatTextView6, AppCompatTextView appCompatTextView7, AppCompatTextView appCompatTextView8, AppCompatTextView appCompatTextView9, AppCompatTextView appCompatTextView10, AppCompatTextView appCompatTextView11, AppCompatTextView appCompatTextView12, AppCompatTextView appCompatTextView13, AppCompatTextView appCompatTextView14, AppCompatTextView appCompatTextView15, AppCompatTextView appCompatTextView16, AppCompatTextView appCompatTextView17, AppCompatTextView appCompatTextView18) {
        super(obj, view, i2);
        this.z = materialButton;
        this.A = appCompatEditText;
        this.B = appCompatEditText2;
        this.C = appCompatRadioButton;
        this.D = appCompatRadioButton2;
        this.E = appCompatRadioButton3;
        this.F = appCompatRadioButton4;
        this.G = appCompatRadioButton5;
        this.H = appCompatRadioButton6;
        this.I = radioGroup;
        this.J = radioGroup2;
        this.K = appCompatTextView2;
        this.L = appCompatTextView3;
        this.M = appCompatTextView4;
        this.N = appCompatTextView5;
        this.O = appCompatTextView7;
        this.P = appCompatTextView8;
        this.Q = appCompatTextView9;
        this.R = appCompatTextView11;
        this.S = appCompatTextView12;
        this.T = appCompatTextView13;
        this.U = appCompatTextView15;
        this.V = appCompatTextView16;
        this.W = appCompatTextView17;
        this.X = appCompatTextView18;
    }

    public abstract void a(WeightViewModel weightViewModel);
}
