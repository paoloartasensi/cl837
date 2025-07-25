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
import com.chileaf.fitness.b.a0;
import com.chileaf.fitness.base.BaseActivity;
import com.chileaf.fitness.base.BaseViewModel;
import com.chileaf.fitness.device.wear.cl880.adapter.HistorySportAdapter;
import com.chileaf.fitness.device.wear.cl880.external.c;
import com.chileaf.fitness.device.wear.cl880.model.SportHistory;
import java.util.HashMap;
import java.util.List;
import kotlin.TypeCastException;
import kotlin.d;
import kotlin.jvm.internal.i;

/* compiled from: CL880HistoryActivity.kt */
public final class CL880HistoryActivity extends BaseActivity<a0> {
    private HashMap E;

    /* compiled from: CL880HistoryActivity.kt */
    public static final class a {
        private a() {
        }

        public /* synthetic */ a(f fVar) {
            this();
        }
    }

    static {
        new a((f) null);
    }

    public CL880HistoryActivity() {
        d<T> unused = g.a(new CL880HistoryActivity$mType$2(this));
    }

    /* access modifiers changed from: protected */
    public void a(View view) {
        i.b(view, "view");
        RecyclerView recyclerView = ((a0) m()).z;
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
        return R$layout.activity_history;
    }

    /* access modifiers changed from: protected */
    public void a(Bundle bundle) {
        String string = getString(R$string.history_record);
        i.a((Object) string, "getString(R.string.history_record)");
        a(string);
        c l = c.l();
        i.a((Object) l, "SpecManager.getInstance()");
        List<SportHistory> h2 = l.h();
        j.a.a.a("List<SportHistory>:%s", h2);
        HistorySportAdapter historySportAdapter = new HistorySportAdapter(h2);
        historySportAdapter.replaceData(h2);
        RecyclerView recyclerView = ((a0) m()).z;
        i.a((Object) recyclerView, "mBinding.rvHistory");
        recyclerView.setAdapter(historySportAdapter);
    }
}
