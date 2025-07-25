package com.chileaf.fitness.device.wear.cl880;

import android.os.Bundle;
import android.view.View;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.e;
import androidx.recyclerview.widget.g;
import androidx.recyclerview.widget.u;
import com.chileaf.fitness.R$layout;
import com.chileaf.fitness.R$string;
import com.chileaf.fitness.b.s;
import com.chileaf.fitness.base.BaseActivity;
import com.chileaf.fitness.base.BaseViewModel;
import com.chileaf.fitness.device.wear.cl880.adapter.HistorySleepAdapter;
import com.chileaf.fitness.device.wear.cl880.external.c;
import com.chileaf.fitness.device.wear.cl880.model.SleepHistory;
import java.util.HashMap;
import java.util.List;
import kotlin.TypeCastException;
import kotlin.jvm.internal.i;

/* compiled from: CL880SleepActivity.kt */
public final class CL880SleepActivity extends BaseActivity<s> {
    private HashMap E;

    /* compiled from: CL880SleepActivity.kt */
    static final class a implements View.OnClickListener {
        final /* synthetic */ CL880SleepActivity e;

        a(CL880SleepActivity cL880SleepActivity) {
            this.e = cL880SleepActivity;
        }

        public final void onClick(View view) {
            CL880SleepActivity cL880SleepActivity = this.e;
            String string = cL880SleepActivity.getString(R$string.sleep_time);
            i.a((Object) string, "getString(R.string.sleep_time)");
            cL880SleepActivity.a(string, "setting_sleep_config");
        }
    }

    /* access modifiers changed from: protected */
    public void a(View view) {
        i.b(view, "root");
        RecyclerView recyclerView = ((s) m()).z;
        recyclerView.a((RecyclerView.n) new g(this, 1));
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new e());
        RecyclerView.l itemAnimator = recyclerView.getItemAnimator();
        if (itemAnimator != null) {
            ((u) itemAnimator).a(false);
            return;
        }
        throw new TypeCastException("null cannot be cast to non-null type androidx.recyclerview.widget.SimpleItemAnimator");
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
    public int p() {
        return R$layout.activity_cl880_sleep;
    }

    /* access modifiers changed from: protected */
    public void a(Bundle bundle) {
        String string = getString(R$string.sleep_record);
        i.a((Object) string, "getString(R.string.sleep_record)");
        a(string);
        String string2 = getString(R$string.setting);
        i.a((Object) string2, "getString(R.string.setting)");
        a(string2, (View.OnClickListener) new a(this));
        c l = c.l();
        i.a((Object) l, "SpecManager.getInstance()");
        List<SleepHistory> f2 = l.f();
        j.a.a.a("List<SleepHistory>:%s", f2);
        HistorySleepAdapter historySleepAdapter = new HistorySleepAdapter(f2);
        historySleepAdapter.replaceData(f2);
        RecyclerView recyclerView = ((s) m()).z;
        i.a((Object) recyclerView, "mBinding.rvHistory");
        recyclerView.setAdapter(historySleepAdapter);
    }
}
