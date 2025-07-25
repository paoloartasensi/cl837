package com.chileaf.fitness.ui.activity;

import android.os.Bundle;
import android.view.View;
import com.android.chileaf.adapt.h.a;
import com.chileaf.fitness.R$layout;
import com.chileaf.fitness.R$style;
import com.chileaf.fitness.b.g0;
import com.chileaf.fitness.base.BaseActivity;
import com.chileaf.fitness.base.BaseViewModel;
import java.util.HashMap;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.c;
import kotlinx.coroutines.CoroutineStart;
import kotlinx.coroutines.k1;

/* compiled from: SplashActivity.kt */
public final class SplashActivity extends BaseActivity<g0> implements a {
    private HashMap E;

    /* access modifiers changed from: protected */
    public void a(Bundle bundle) {
        k1 unused = e.a(this, (CoroutineContext) null, (CoroutineStart) null, new SplashActivity$initData$1(this, (c) null), 3, (Object) null);
    }

    public View d(int i2) {
        if (this.E == null) {
            this.E = new HashMap();
        }
        View view = (View) this.E.get(Integer.valueOf(i2));
        if (view != null) {
            return view;
        }
        View findViewById = findViewById(i2);
        this.E.put(Integer.valueOf(i2), findViewById);
        return findViewById;
    }

    /* access modifiers changed from: protected */
    public BaseViewModel o() {
        return null;
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setTheme(R$style.AppTheme);
    }

    /* access modifiers changed from: protected */
    public int p() {
        return R$layout.activity_splash;
    }
}
