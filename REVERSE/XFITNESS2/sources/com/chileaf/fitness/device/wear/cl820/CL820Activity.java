package com.chileaf.fitness.device.wear.cl820;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelLazy;
import com.chileaf.fitness.R$layout;
import com.chileaf.fitness.R$string;
import com.chileaf.fitness.b.k;
import com.chileaf.fitness.base.BaseActivity;
import com.chileaf.fitness.model.DiscoveredDevice;
import com.chileaf.fitness.viewmodel.CL820ViewModel;
import java.util.HashMap;
import kotlin.jvm.internal.i;

/* compiled from: CL820Activity.kt */
public final class CL820Activity extends BaseActivity<k> {
    private final kotlin.d E = g.a(new CL820Activity$mDevice$2(this));
    private final kotlin.d F = new ViewModelLazy(kotlin.jvm.internal.k.a(CL820ViewModel.class), new CL820Activity$$special$$inlined$viewModels$2(this), new CL820Activity$$special$$inlined$viewModels$1(this));
    private HashMap G;

    /* compiled from: CL820Activity.kt */
    static final class a<T> implements Observer<String> {
        final /* synthetic */ CL820Activity a;

        a(CL820Activity cL820Activity) {
            this.a = cL820Activity;
        }

        /* renamed from: a */
        public final void onChanged(String str) {
            if (str != null) {
                BaseActivity.a((BaseActivity) this.a, (CharSequence) str, 0, 2, (Object) null);
            }
        }
    }

    /* compiled from: CL820Activity.kt */
    static final class b implements View.OnClickListener {
        final /* synthetic */ CL820Activity e;

        b(CL820Activity cL820Activity) {
            this.e = cL820Activity;
        }

        public final void onClick(View view) {
            this.e.e(2);
        }
    }

    /* compiled from: CL820Activity.kt */
    static final class c implements View.OnClickListener {
        final /* synthetic */ CL820Activity e;

        c(CL820Activity cL820Activity) {
            this.e = cL820Activity;
        }

        public final void onClick(View view) {
            this.e.e(4);
        }
    }

    /* compiled from: CL820Activity.kt */
    static final class d implements View.OnClickListener {
        final /* synthetic */ CL820Activity e;

        d(CL820Activity cL820Activity) {
            this.e = cL820Activity;
        }

        public final void onClick(View view) {
            this.e.e(6);
        }
    }

    /* compiled from: CL820Activity.kt */
    static final class e implements View.OnClickListener {
        public static final e e = new e();

        e() {
        }

        public final void onClick(View view) {
        }
    }

    /* access modifiers changed from: private */
    public final void e(int i2) {
        Intent intent = new Intent(this, CL820HistoryActivity.class);
        intent.putExtra("extra_history", i2);
        startActivity(intent);
    }

    private final DiscoveredDevice q() {
        return (DiscoveredDevice) this.E.getValue();
    }

    private final CL820ViewModel r() {
        return (CL820ViewModel) this.F.getValue();
    }

    public View d(int i2) {
        if (this.G == null) {
            this.G = new HashMap();
        }
        View view = (View) this.G.get(Integer.valueOf(i2));
        if (view != null) {
            return view;
        }
        View findViewById = findViewById(i2);
        this.G.put(Integer.valueOf(i2), findViewById);
        return findViewById;
    }

    /* access modifiers changed from: protected */
    public int p() {
        return R$layout.activity_cl820;
    }

    /* access modifiers changed from: protected */
    public void a(View view) {
        i.b(view, "root");
        k kVar = (k) m();
        kVar.F.setOnClickListener(new b(this));
        kVar.B.setOnClickListener(new c(this));
        kVar.D.setOnClickListener(new d(this));
        kVar.C.setOnClickListener(e.e);
    }

    /* access modifiers changed from: protected */
    public CL820ViewModel o() {
        return r();
    }

    /* access modifiers changed from: protected */
    public void a(Bundle bundle) {
        String str;
        DiscoveredDevice q = q();
        if (q == null || (str = q.getName()) == null) {
            str = getString(R$string.wireless_hr_belt);
            i.a((Object) str, "getString(R.string.wireless_hr_belt)");
        }
        a(str);
        DiscoveredDevice q2 = q();
        if (q2 != null) {
            r().a(q2);
        }
        r().g().observe(this, new a(this));
        ((k) m()).a(r());
    }
}
