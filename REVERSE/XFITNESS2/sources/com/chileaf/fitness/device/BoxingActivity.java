package com.chileaf.fitness.device;

import android.os.Bundle;
import android.view.View;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelLazy;
import com.chileaf.fitness.R$layout;
import com.chileaf.fitness.R$string;
import com.chileaf.fitness.b.e;
import com.chileaf.fitness.base.BaseActivity;
import com.chileaf.fitness.model.DiscoveredDevice;
import com.chileaf.fitness.viewmodel.BoxingViewModel;
import java.util.HashMap;
import kotlin.d;
import kotlin.jvm.internal.i;
import kotlin.jvm.internal.k;

/* compiled from: BoxingActivity.kt */
public final class BoxingActivity extends BaseActivity<e> {
    private final d E = g.a(new BoxingActivity$mDevice$2(this));
    private final d F = new ViewModelLazy(k.a(BoxingViewModel.class), new BoxingActivity$$special$$inlined$viewModels$2(this), new BoxingActivity$$special$$inlined$viewModels$1(this));
    private HashMap G;

    /* compiled from: BoxingActivity.kt */
    static final class a<T> implements Observer<String> {
        final /* synthetic */ BoxingActivity a;

        a(BoxingActivity boxingActivity) {
            this.a = boxingActivity;
        }

        /* renamed from: a */
        public final void onChanged(String str) {
            if (str != null) {
                BaseActivity.a((BaseActivity) this.a, (CharSequence) str, 0, 2, (Object) null);
            }
        }
    }

    private final DiscoveredDevice q() {
        return (DiscoveredDevice) this.E.getValue();
    }

    private final BoxingViewModel r() {
        return (BoxingViewModel) this.F.getValue();
    }

    /* access modifiers changed from: protected */
    public void a(Bundle bundle) {
        String str;
        DiscoveredDevice q = q();
        if (q == null || (str = q.getName()) == null) {
            str = getString(R$string.text_boxing);
            i.a((Object) str, "getString(R.string.text_boxing)");
        }
        a(str);
        DiscoveredDevice q2 = q();
        if (q2 != null) {
            r().a(q2);
        }
        r().d().observe(this, new a(this));
        ((e) m()).a(r());
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
        return R$layout.activity_boxing;
    }

    /* access modifiers changed from: protected */
    public BoxingViewModel o() {
        return r();
    }
}
