package com.chileaf.fitness.device.wear.cl880;

import android.os.Bundle;
import android.view.View;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.e;
import androidx.recyclerview.widget.g;
import com.chileaf.fitness.R$layout;
import com.chileaf.fitness.R$string;
import com.chileaf.fitness.b.u;
import com.chileaf.fitness.base.BaseActivity;
import com.chileaf.fitness.base.BaseViewModel;
import com.chileaf.fitness.device.wear.cl880.adapter.HistorySportEveryDayAdapter;
import com.chileaf.fitness.device.wear.cl880.external.c;
import com.chileaf.fitness.device.wear.cl880.model.SportDayHistory;
import java.util.HashMap;
import java.util.List;
import kotlin.TypeCastException;
import kotlin.jvm.internal.i;

/* compiled from: CL880SportActivity.kt */
public final class CL880SportActivity extends BaseActivity<u> {
    private HashMap E;

    /* compiled from: CL880SportActivity.kt */
    static final class a implements View.OnClickListener {
        final /* synthetic */ CL880SportActivity e;

        a(CL880SportActivity cL880SportActivity) {
            this.e = cL880SportActivity;
        }

        public final void onClick(View view) {
            CL880SportActivity cL880SportActivity = this.e;
            String string = cL880SportActivity.getString(R$string.train_setting);
            i.a((Object) string, "getString(R.string.train_setting)");
            cL880SportActivity.a(string, "setting_train_config");
        }
    }

    /* access modifiers changed from: protected */
    public void a(View view) {
        i.b(view, "root");
        RecyclerView recyclerView = ((u) m()).z;
        recyclerView.a((RecyclerView.n) new g(this, 1));
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new e());
        RecyclerView.l itemAnimator = recyclerView.getItemAnimator();
        if (itemAnimator != null) {
            ((androidx.recyclerview.widget.u) itemAnimator).a(false);
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
        return R$layout.activity_cl880_sport;
    }

    /* access modifiers changed from: protected */
    public void a(Bundle bundle) {
        String string = getString(R$string.sport_mode);
        i.a((Object) string, "getString(R.string.sport_mode)");
        a(string);
        String string2 = getString(R$string.setting);
        i.a((Object) string2, "getString(R.string.setting)");
        a(string2, (View.OnClickListener) new a(this));
        c l = c.l();
        i.a((Object) l, "SpecManager.getInstance()");
        List<SportDayHistory> g2 = l.g();
        j.a.a.a("List<SportDayHistory>:%s", g2);
        HistorySportEveryDayAdapter historySportEveryDayAdapter = new HistorySportEveryDayAdapter(g2);
        historySportEveryDayAdapter.replaceData(g2);
        RecyclerView recyclerView = ((u) m()).z;
        i.a((Object) recyclerView, "mBinding.rvHistory");
        recyclerView.setAdapter(historySportEveryDayAdapter);
    }
}
