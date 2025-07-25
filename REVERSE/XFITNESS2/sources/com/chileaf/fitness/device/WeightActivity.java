package com.chileaf.fitness.device;

import aicare.net.cn.iweightlibrary.bleprofile.BleProfileServiceReadyActivity;
import aicare.net.cn.iweightlibrary.entity.AlgorithmInfo;
import aicare.net.cn.iweightlibrary.entity.BodyFatData;
import aicare.net.cn.iweightlibrary.entity.BroadData;
import aicare.net.cn.iweightlibrary.entity.DecimalInfo;
import aicare.net.cn.iweightlibrary.entity.WeightData;
import aicare.net.cn.iweightlibrary.wby.WBYService;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelLazy;
import com.chileaf.fitness.R$id;
import com.chileaf.fitness.R$layout;
import com.chileaf.fitness.R$string;
import com.chileaf.fitness.b.k0;
import com.chileaf.fitness.model.DiscoveredDevice;
import com.chileaf.fitness.viewmodel.WeightViewModel;
import com.jeremyliao.liveeventbus.LiveEventBus;
import java.util.HashMap;
import kotlin.TypeCastException;
import kotlin.jvm.internal.i;
import kotlin.jvm.internal.k;

/* compiled from: WeightActivity.kt */
public final class WeightActivity extends BleProfileServiceReadyActivity<WBYService.a> {
    private final kotlin.d H = g.a(new WeightActivity$mDevice$2(this));
    private final kotlin.d I = new ViewModelLazy(k.a(WeightViewModel.class), new WeightActivity$$special$$inlined$viewModels$2(this), new WeightActivity$$special$$inlined$viewModels$1(this));
    private k0 J;
    private HashMap K;

    /* compiled from: WeightActivity.kt */
    static final class a<T> implements Observer<String> {
        public static final a a = new a();

        a() {
        }

        /* renamed from: a */
        public final void onChanged(String str) {
            LiveEventBus.get("event_toast", String.class).post(str);
        }
    }

    /* compiled from: WeightActivity.kt */
    static final class b implements View.OnClickListener {
        final /* synthetic */ WeightActivity e;

        b(WeightActivity weightActivity) {
            this.e = weightActivity;
        }

        public final void onClick(View view) {
            this.e.onBackPressed();
        }
    }

    /* compiled from: WeightActivity.kt */
    public static final class c extends com.chileaf.fitness.widget.b {
        final /* synthetic */ WeightActivity e;

        c(WeightActivity weightActivity) {
            this.e = weightActivity;
        }

        public void afterTextChanged(Editable editable) {
            String obj;
            if (editable != null && (obj = editable.toString()) != null) {
                if (obj.length() > 0) {
                    this.e.t().c().a(obj);
                }
            }
        }
    }

    /* compiled from: WeightActivity.kt */
    public static final class d extends com.chileaf.fitness.widget.b {
        final /* synthetic */ WeightActivity e;

        d(WeightActivity weightActivity) {
            this.e = weightActivity;
        }

        public void afterTextChanged(Editable editable) {
            String obj;
            if (editable != null && (obj = editable.toString()) != null) {
                if (obj.length() > 0) {
                    this.e.t().h().a(obj);
                }
            }
        }
    }

    /* compiled from: WeightActivity.kt */
    static final class e implements RadioGroup.OnCheckedChangeListener {
        final /* synthetic */ k0 a;
        final /* synthetic */ WeightActivity b;

        e(k0 k0Var, WeightActivity weightActivity) {
            this.a = k0Var;
            this.b = weightActivity;
        }

        public final void onCheckedChanged(RadioGroup radioGroup, int i2) {
            AppCompatRadioButton appCompatRadioButton = this.a.G;
            i.a((Object) appCompatRadioButton, "rbMale");
            if (i2 == appCompatRadioButton.getId()) {
                this.b.t().a(1);
                return;
            }
            AppCompatRadioButton appCompatRadioButton2 = this.a.C;
            i.a((Object) appCompatRadioButton2, "rbFemale");
            if (i2 == appCompatRadioButton2.getId()) {
                this.b.t().a(2);
            }
        }
    }

    /* compiled from: WeightActivity.kt */
    static final class f implements RadioGroup.OnCheckedChangeListener {
        final /* synthetic */ k0 a;
        final /* synthetic */ WeightActivity b;

        f(k0 k0Var, WeightActivity weightActivity) {
            this.a = k0Var;
            this.b = weightActivity;
        }

        public final void onCheckedChanged(RadioGroup radioGroup, int i2) {
            AppCompatRadioButton appCompatRadioButton = this.a.E;
            i.a((Object) appCompatRadioButton, "rbKg");
            if (i2 == appCompatRadioButton.getId()) {
                this.b.t().b(1);
                return;
            }
            AppCompatRadioButton appCompatRadioButton2 = this.a.F;
            i.a((Object) appCompatRadioButton2, "rbLb");
            if (i2 == appCompatRadioButton2.getId()) {
                this.b.t().b(2);
                return;
            }
            AppCompatRadioButton appCompatRadioButton3 = this.a.H;
            i.a((Object) appCompatRadioButton3, "rbSt");
            if (i2 == appCompatRadioButton3.getId()) {
                this.b.t().b(3);
                return;
            }
            AppCompatRadioButton appCompatRadioButton4 = this.a.D;
            i.a((Object) appCompatRadioButton4, "rbJin");
            if (i2 == appCompatRadioButton4.getId()) {
                this.b.t().b(4);
            }
        }
    }

    /* compiled from: WeightActivity.kt */
    static final class g implements View.OnClickListener {
        final /* synthetic */ WeightActivity e;

        g(WeightActivity weightActivity) {
            this.e = weightActivity;
        }

        public final void onClick(View view) {
            this.e.t().v();
        }
    }

    private final DiscoveredDevice s() {
        return (DiscoveredDevice) this.H.getValue();
    }

    /* access modifiers changed from: private */
    public final WeightViewModel t() {
        return (WeightViewModel) this.I.getValue();
    }

    private final void u() {
        q();
        t().j().observe(this, a.a);
        k0 k0Var = this.J;
        if (k0Var != null) {
            k0Var.a(t());
            k0 k0Var2 = this.J;
            if (k0Var2 != null) {
                k0Var2.a((LifecycleOwner) this);
                k0 k0Var3 = this.J;
                if (k0Var3 != null) {
                    k0Var3.b();
                } else {
                    i.d("mBinding");
                    throw null;
                }
            } else {
                i.d("mBinding");
                throw null;
            }
        } else {
            i.d("mBinding");
            throw null;
        }
    }

    private final void v() {
        String str;
        TextView textView = (TextView) g(R$id.tv_toolbar_title);
        if (textView != null) {
            DiscoveredDevice s = s();
            if (s == null || (str = s.getName()) == null) {
                str = getString(R$string.text_weight);
            }
            textView.setText(str);
        }
        ImageView imageView = (ImageView) g(R$id.iv_toolbar_back);
        if (imageView != null) {
            imageView.setOnClickListener(new b(this));
        }
        k0 k0Var = this.J;
        if (k0Var != null) {
            k0Var.A.addTextChangedListener(new c(this));
            k0Var.B.addTextChangedListener(new d(this));
            k0Var.I.setOnCheckedChangeListener(new e(k0Var, this));
            k0Var.J.setOnCheckedChangeListener(new f(k0Var, this));
            k0Var.z.setOnClickListener(new g(this));
            return;
        }
        i.d("mBinding");
        throw null;
    }

    /* access modifiers changed from: protected */
    public void attachBaseContext(Context context) {
        super.attachBaseContext(com.chileaf.fitness.config.b.b.a(context));
    }

    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        View currentFocus;
        i.b(motionEvent, "event");
        if (motionEvent.getAction() != 0 || (currentFocus = getCurrentFocus()) == null) {
            return onTouchEvent(motionEvent) | getWindow().superDispatchTouchEvent(motionEvent);
        }
        int[] iArr = {0, 0};
        currentFocus.getLocationInWindow(iArr);
        int i2 = iArr[0];
        boolean z = true;
        int i3 = iArr[1];
        int height = currentFocus.getHeight() + i3;
        int width = currentFocus.getWidth() + i2;
        if (motionEvent.getX() > ((float) i2) && motionEvent.getX() < ((float) width) && motionEvent.getY() > ((float) i3) && motionEvent.getY() < ((float) height)) {
            z = false;
        }
        if ((currentFocus instanceof EditText) && z) {
            Object systemService = getSystemService("input_method");
            if (systemService != null) {
                ((InputMethodManager) systemService).hideSoftInputFromWindow(((EditText) currentFocus).getWindowToken(), 0);
                currentFocus.clearFocus();
            } else {
                throw new TypeCastException("null cannot be cast to non-null type android.view.inputmethod.InputMethodManager");
            }
        }
        return super.dispatchTouchEvent(motionEvent);
    }

    /* access modifiers changed from: protected */
    public void f(int i2) {
        t().c(i2);
    }

    public View g(int i2) {
        if (this.K == null) {
            this.K = new HashMap();
        }
        View view = (View) this.K.get(Integer.valueOf(i2));
        if (view != null) {
            return view;
        }
        View findViewById = findViewById(i2);
        this.K.put(Integer.valueOf(i2), findViewById);
        return findViewById;
    }

    /* access modifiers changed from: protected */
    public void o() {
        t().a((WBYService.a) null);
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        ViewDataBinding a2 = androidx.databinding.g.a(this, R$layout.activity_weight);
        i.a((Object) a2, "DataBindingUtil.setConte…R.layout.activity_weight)");
        this.J = (k0) a2;
        t().onCreate(this);
        v();
        u();
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        t().onDestroy(this);
        super.onDestroy();
    }

    /* access modifiers changed from: protected */
    public void a(int i2, String str) {
        t().a(i2, str);
    }

    /* access modifiers changed from: protected */
    public void a(DecimalInfo decimalInfo) {
        if (decimalInfo != null) {
            j.a.a.b("DecimalInfo" + decimalInfo, new Object[0]);
        }
    }

    /* access modifiers changed from: protected */
    public void a(AlgorithmInfo algorithmInfo) {
        if (algorithmInfo != null) {
            j.a.a.b("AlgorithmInfo" + algorithmInfo, new Object[0]);
        }
    }

    /* access modifiers changed from: protected */
    public void a(BroadData broadData) {
        if (broadData != null) {
            j.a.a.b("getAicareDevice:" + broadData, new Object[0]);
            String address = broadData.getAddress();
            DiscoveredDevice s = s();
            if (i.a((Object) address, (Object) s != null ? s.getAddress() : null)) {
                c(broadData.getAddress());
            }
            t().a(broadData);
        }
    }

    /* access modifiers changed from: protected */
    public void a(WeightData weightData) {
        if (weightData != null) {
            t().a(weightData);
        }
    }

    /* access modifiers changed from: protected */
    public void a(boolean z, BodyFatData bodyFatData) {
        j.a.a.b("BodyFatData" + bodyFatData, new Object[0]);
        if (!z && bodyFatData != null) {
            t().a(bodyFatData);
        }
    }

    /* access modifiers changed from: protected */
    public void a(WBYService.a aVar) {
        t().a(aVar);
    }

    /* access modifiers changed from: protected */
    public void a(String str, int i2) {
        j.a.a.b("message:" + str + " code" + i2, new Object[0]);
    }
}
