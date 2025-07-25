package com.chileaf.fitness.device.cdn;

import android.os.Bundle;
import android.view.View;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelLazy;
import com.chileaf.fitness.R$layout;
import com.chileaf.fitness.R$string;
import com.chileaf.fitness.b.g;
import com.chileaf.fitness.base.BaseActivity;
import com.chileaf.fitness.model.DiscoveredDevice;
import com.chileaf.fitness.viewmodel.CDNViewModel;
import java.util.HashMap;
import kotlin.d;
import kotlin.jvm.internal.i;
import kotlin.jvm.internal.k;

/* compiled from: CDNActivity.kt */
public final class CDNActivity extends BaseActivity<g> {
    private final d E = g.a(new CDNActivity$mDevice$2(this));
    private final d F = new ViewModelLazy(k.a(CDNViewModel.class), new CDNActivity$$special$$inlined$viewModels$2(this), new CDNActivity$$special$$inlined$viewModels$1(this));
    private HashMap G;

    /* compiled from: CDNActivity.kt */
    static final class a<T> implements Observer<String> {
        final /* synthetic */ CDNActivity a;

        a(CDNActivity cDNActivity) {
            this.a = cDNActivity;
        }

        /* renamed from: a */
        public final void onChanged(String str) {
            if (str != null) {
                BaseActivity.a((BaseActivity) this.a, (CharSequence) str, 0, 2, (Object) null);
            }
        }
    }

    /* compiled from: CDNActivity.kt */
    static final class b implements View.OnClickListener {
        final /* synthetic */ CDNActivity e;

        b(CDNActivity cDNActivity) {
            this.e = cDNActivity;
        }

        public final void onClick(View view) {
            CDNActivity cDNActivity = this.e;
            String string = cDNActivity.getString(R$string.setting);
            i.a((Object) string, "getString(R.string.setting)");
            cDNActivity.a(string, "setting_csc");
        }
    }

    private final DiscoveredDevice q() {
        return (DiscoveredDevice) this.E.getValue();
    }

    private final CDNViewModel r() {
        return (CDNViewModel) this.F.getValue();
    }

    /* access modifiers changed from: protected */
    public void a(Bundle bundle) {
        String str;
        DiscoveredDevice q = q();
        if (q == null || (str = q.getName()) == null) {
            str = getString(R$string.text_cdn);
            i.a((Object) str, "getString(R.string.text_cdn)");
        }
        a(str);
        String string = getString(R$string.setting);
        i.a((Object) string, "getString(R.string.setting)");
        a(string, (View.OnClickListener) new b(this));
        r().onCreate(this);
        DiscoveredDevice q2 = q();
        if (q2 != null) {
            r().a(q2);
        }
        r().g().observe(this, new a(this));
        ((g) m()).a(r());
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
        return R$layout.activity_cdn;
    }

    /* access modifiers changed from: protected */
    public CDNViewModel o() {
        return r();
    }
}
