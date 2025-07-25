package com.chileaf.fitness.device.wear.cl820;

import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.u;
import com.android.chileaf.fitness.model.HistoryOfRecord;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chileaf.fitness.R$layout;
import com.chileaf.fitness.R$string;
import com.chileaf.fitness.b.a0;
import com.chileaf.fitness.base.BaseActivity;
import com.chileaf.fitness.base.BaseViewModel;
import com.chileaf.fitness.device.wear.cl820.adapter.HistoryRecordAdapter;
import com.chileaf.fitness.device.wear.cl820.adapter.HistorySleepAdapter;
import com.chileaf.fitness.device.wear.cl820.adapter.HistorySportAdapter;
import com.chileaf.fitness.device.wear.cl820.model.HistoryOfSleep;
import com.chileaf.fitness.device.wear.cl820.model.HistoryOfSport;
import java.util.HashMap;
import java.util.List;
import kotlin.TypeCastException;

/* compiled from: CL820HistoryActivity.kt */
public final class CL820HistoryActivity extends BaseActivity<a0> {
    private final kotlin.d E = g.a(new CL820HistoryActivity$mType$2(this));
    private final kotlin.d F = g.a(new CL820HistoryActivity$mManager$2(this));
    private boolean G;
    private HashMap H;

    /* compiled from: CL820HistoryActivity.kt */
    public static final class a {
        private a() {
        }

        public /* synthetic */ a(f fVar) {
            this();
        }
    }

    /* compiled from: CL820HistoryActivity.kt */
    static final class b implements com.chileaf.fitness.device.wear.cl820.callback.e {
        final /* synthetic */ CL820HistoryActivity e;

        /* renamed from: f  reason: collision with root package name */
        final /* synthetic */ HistorySleepAdapter f1153f;

        b(CL820HistoryActivity cL820HistoryActivity, HistorySleepAdapter historySleepAdapter) {
            this.e = cL820HistoryActivity;
            this.f1153f = historySleepAdapter;
        }

        public final void j(BluetoothDevice bluetoothDevice, List<? extends HistoryOfSleep> list) {
            kotlin.jvm.internal.i.b(list, "sleeps");
            this.f1153f.addData(list);
            this.e.n();
        }
    }

    /* compiled from: CL820HistoryActivity.kt */
    static final class c implements View.OnClickListener {
        final /* synthetic */ CL820HistoryActivity e;

        /* renamed from: f  reason: collision with root package name */
        final /* synthetic */ HistorySportAdapter f1154f;

        c(CL820HistoryActivity cL820HistoryActivity, HistorySportAdapter historySportAdapter) {
            this.e = cL820HistoryActivity;
            this.f1154f = historySportAdapter;
        }

        public final void onClick(View view) {
            this.e.a(2000);
            this.f1154f.getData().clear();
            this.e.q().D();
        }
    }

    /* compiled from: CL820HistoryActivity.kt */
    static final class d implements com.chileaf.fitness.device.wear.cl820.callback.f {
        final /* synthetic */ CL820HistoryActivity e;

        /* renamed from: f  reason: collision with root package name */
        final /* synthetic */ HistorySportAdapter f1155f;

        d(CL820HistoryActivity cL820HistoryActivity, HistorySportAdapter historySportAdapter) {
            this.e = cL820HistoryActivity;
            this.f1155f = historySportAdapter;
        }

        public final void b(BluetoothDevice bluetoothDevice, List<? extends HistoryOfSport> list) {
            kotlin.jvm.internal.i.b(list, "sports");
            this.f1155f.addData(list);
            this.e.n();
        }
    }

    /* compiled from: CL820HistoryActivity.kt */
    static final class e implements BaseQuickAdapter.OnItemClickListener {
        final /* synthetic */ CL820HistoryActivity a;

        e(CL820HistoryActivity cL820HistoryActivity) {
            this.a = cL820HistoryActivity;
        }

        public final void onItemClick(BaseQuickAdapter<?, ?> baseQuickAdapter, View view, int i2) {
            kotlin.jvm.internal.i.b(baseQuickAdapter, "adapter1");
            Object obj = baseQuickAdapter.getData().get(i2);
            if (obj != null) {
                this.a.a(3, ((HistoryOfRecord) obj).e);
                return;
            }
            throw new TypeCastException("null cannot be cast to non-null type com.android.chileaf.fitness.model.HistoryOfRecord");
        }
    }

    /* compiled from: CL820HistoryActivity.kt */
    static final class f implements View.OnClickListener {
        final /* synthetic */ CL820HistoryActivity e;

        /* renamed from: f  reason: collision with root package name */
        final /* synthetic */ HistoryRecordAdapter f1156f;

        f(CL820HistoryActivity cL820HistoryActivity, HistoryRecordAdapter historyRecordAdapter) {
            this.e = cL820HistoryActivity;
            this.f1156f = historyRecordAdapter;
        }

        public final void onClick(View view) {
            this.e.a(2000);
            this.f1156f.getData().clear();
            this.e.q().A();
        }
    }

    /* compiled from: CL820HistoryActivity.kt */
    static final class g implements com.chileaf.fitness.device.wear.cl820.callback.b {
        final /* synthetic */ CL820HistoryActivity e;

        /* renamed from: f  reason: collision with root package name */
        final /* synthetic */ HistoryRecordAdapter f1157f;

        /* compiled from: CL820HistoryActivity.kt */
        static final class a implements Runnable {
            final /* synthetic */ g e;

            /* renamed from: f  reason: collision with root package name */
            final /* synthetic */ List f1158f;

            a(g gVar, List list) {
                this.e = gVar;
                this.f1158f = list;
            }

            public final void run() {
                this.e.f1157f.addData(this.f1158f);
                this.e.e.n();
            }
        }

        g(CL820HistoryActivity cL820HistoryActivity, HistoryRecordAdapter historyRecordAdapter) {
            this.e = cL820HistoryActivity;
            this.f1157f = historyRecordAdapter;
        }

        public final void a(BluetoothDevice bluetoothDevice, List<? extends HistoryOfRecord> list) {
            kotlin.jvm.internal.i.b(list, "records");
            this.e.runOnUiThread(new a(this, list));
        }
    }

    /* compiled from: CL820HistoryActivity.kt */
    static final class h implements BaseQuickAdapter.OnItemClickListener {
        final /* synthetic */ CL820HistoryActivity a;

        h(CL820HistoryActivity cL820HistoryActivity) {
            this.a = cL820HistoryActivity;
        }

        public final void onItemClick(BaseQuickAdapter<?, ?> baseQuickAdapter, View view, int i2) {
            kotlin.jvm.internal.i.b(baseQuickAdapter, "adapter1");
            Object obj = baseQuickAdapter.getData().get(i2);
            if (obj != null) {
                this.a.a(5, ((HistoryOfRecord) obj).e);
                return;
            }
            throw new TypeCastException("null cannot be cast to non-null type com.android.chileaf.fitness.model.HistoryOfRecord");
        }
    }

    /* compiled from: CL820HistoryActivity.kt */
    static final class i implements View.OnClickListener {
        final /* synthetic */ CL820HistoryActivity e;

        /* renamed from: f  reason: collision with root package name */
        final /* synthetic */ HistoryRecordAdapter f1159f;

        i(CL820HistoryActivity cL820HistoryActivity, HistoryRecordAdapter historyRecordAdapter) {
            this.e = cL820HistoryActivity;
            this.f1159f = historyRecordAdapter;
        }

        public final void onClick(View view) {
            this.e.a(2000);
            this.f1159f.getData().clear();
            this.e.q().B();
        }
    }

    /* compiled from: CL820HistoryActivity.kt */
    static final class j implements com.chileaf.fitness.device.wear.cl820.callback.d {
        final /* synthetic */ CL820HistoryActivity e;

        /* renamed from: f  reason: collision with root package name */
        final /* synthetic */ HistoryRecordAdapter f1160f;

        /* compiled from: CL820HistoryActivity.kt */
        static final class a implements Runnable {
            final /* synthetic */ j e;

            /* renamed from: f  reason: collision with root package name */
            final /* synthetic */ List f1161f;

            a(j jVar, List list) {
                this.e = jVar;
                this.f1161f = list;
            }

            public final void run() {
                this.e.f1160f.addData(this.f1161f);
                this.e.e.n();
            }
        }

        j(CL820HistoryActivity cL820HistoryActivity, HistoryRecordAdapter historyRecordAdapter) {
            this.e = cL820HistoryActivity;
            this.f1160f = historyRecordAdapter;
        }

        public final void k(BluetoothDevice bluetoothDevice, List<? extends HistoryOfRecord> list) {
            kotlin.jvm.internal.i.b(list, "records");
            this.e.runOnUiThread(new a(this, list));
        }
    }

    /* compiled from: CL820HistoryActivity.kt */
    static final class k implements View.OnClickListener {
        final /* synthetic */ CL820HistoryActivity e;

        /* renamed from: f  reason: collision with root package name */
        final /* synthetic */ HistorySleepAdapter f1162f;

        k(CL820HistoryActivity cL820HistoryActivity, HistorySleepAdapter historySleepAdapter) {
            this.e = cL820HistoryActivity;
            this.f1162f = historySleepAdapter;
        }

        public final void onClick(View view) {
            this.e.a(20000);
            this.f1162f.getData().clear();
            this.e.q().C();
        }
    }

    static {
        new a((f) null);
    }

    /* access modifiers changed from: private */
    public final CL820Manager q() {
        return (CL820Manager) this.F.getValue();
    }

    private final Integer r() {
        return (Integer) this.E.getValue();
    }

    public View d(int i2) {
        if (this.H == null) {
            this.H = new HashMap();
        }
        View view = (View) this.H.get(Integer.valueOf(i2));
        if (view != null) {
            return view;
        }
        View findViewById = findViewById(i2);
        this.H.put(Integer.valueOf(i2), findViewById);
        return findViewById;
    }

    /* access modifiers changed from: protected */
    public BaseViewModel o() {
        return null;
    }

    /* access modifiers changed from: protected */
    public void onPause() {
        super.onPause();
        this.G = true;
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        if (this.G) {
            this.G = false;
            Integer r = r();
            if (r != null && r.intValue() == 4) {
                a(2000);
                q().A();
                return;
            }
            Integer r2 = r();
            if (r2 != null && r2.intValue() == 6) {
                a(2000);
                q().B();
            }
        }
    }

    /* access modifiers changed from: protected */
    public int p() {
        return R$layout.activity_history;
    }

    /* access modifiers changed from: protected */
    public void a(View view) {
        kotlin.jvm.internal.i.b(view, "root");
        RecyclerView recyclerView = ((a0) m()).z;
        recyclerView.a((RecyclerView.n) new androidx.recyclerview.widget.g(this, 1));
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new androidx.recyclerview.widget.e());
        RecyclerView.l itemAnimator = recyclerView.getItemAnimator();
        if (itemAnimator != null) {
            ((u) itemAnimator).a(false);
            return;
        }
        throw new TypeCastException("null cannot be cast to non-null type androidx.recyclerview.widget.SimpleItemAnimator");
    }

    /* access modifiers changed from: protected */
    public void a(Bundle bundle) {
        Integer r = r();
        if (r != null && r.intValue() == 2) {
            a(2000);
            HistorySportAdapter historySportAdapter = new HistorySportAdapter();
            RecyclerView recyclerView = ((a0) m()).z;
            kotlin.jvm.internal.i.a((Object) recyclerView, "mBinding.rvHistory");
            recyclerView.setAdapter(historySportAdapter);
            String string = getString(R$string.sport_history);
            kotlin.jvm.internal.i.a((Object) string, "getString(R.string.sport_history)");
            a(string);
            String string2 = getString(R$string.refresh);
            kotlin.jvm.internal.i.a((Object) string2, "getString(R.string.refresh)");
            a(string2, (View.OnClickListener) new c(this, historySportAdapter));
            q().a((com.chileaf.fitness.device.wear.cl820.callback.f) new d(this, historySportAdapter));
            q().D();
            return;
        }
        Integer r2 = r();
        if (r2 != null && r2.intValue() == 4) {
            a(2000);
            HistoryRecordAdapter historyRecordAdapter = new HistoryRecordAdapter();
            historyRecordAdapter.setOnItemClickListener(new e(this));
            RecyclerView recyclerView2 = ((a0) m()).z;
            kotlin.jvm.internal.i.a((Object) recyclerView2, "mBinding.rvHistory");
            recyclerView2.setAdapter(historyRecordAdapter);
            String string3 = getString(R$string.hr_history_list);
            kotlin.jvm.internal.i.a((Object) string3, "getString(R.string.hr_history_list)");
            a(string3);
            String string4 = getString(R$string.refresh);
            kotlin.jvm.internal.i.a((Object) string4, "getString(R.string.refresh)");
            a(string4, (View.OnClickListener) new f(this, historyRecordAdapter));
            q().a((com.chileaf.fitness.device.wear.cl820.callback.b) new g(this, historyRecordAdapter));
            q().A();
            return;
        }
        Integer r3 = r();
        if (r3 != null && r3.intValue() == 6) {
            a(2000);
            HistoryRecordAdapter historyRecordAdapter2 = new HistoryRecordAdapter();
            historyRecordAdapter2.setOnItemClickListener(new h(this));
            RecyclerView recyclerView3 = ((a0) m()).z;
            kotlin.jvm.internal.i.a((Object) recyclerView3, "mBinding.rvHistory");
            recyclerView3.setAdapter(historyRecordAdapter2);
            String string5 = getString(R$string.rr_history_list);
            kotlin.jvm.internal.i.a((Object) string5, "getString(R.string.rr_history_list)");
            a(string5);
            String string6 = getString(R$string.refresh);
            kotlin.jvm.internal.i.a((Object) string6, "getString(R.string.refresh)");
            a(string6, (View.OnClickListener) new i(this, historyRecordAdapter2));
            q().a((com.chileaf.fitness.device.wear.cl820.callback.d) new j(this, historyRecordAdapter2));
            q().B();
            return;
        }
        Integer r4 = r();
        if (r4 != null && r4.intValue() == 8) {
            a(20000);
            HistorySleepAdapter historySleepAdapter = new HistorySleepAdapter();
            RecyclerView recyclerView4 = ((a0) m()).z;
            kotlin.jvm.internal.i.a((Object) recyclerView4, "mBinding.rvHistory");
            recyclerView4.setAdapter(historySleepAdapter);
            String string7 = getString(R$string.sleep_record);
            kotlin.jvm.internal.i.a((Object) string7, "getString(R.string.sleep_record)");
            a(string7);
            String string8 = getString(R$string.refresh);
            kotlin.jvm.internal.i.a((Object) string8, "getString(R.string.refresh)");
            a(string8, (View.OnClickListener) new k(this, historySleepAdapter));
            q().a((com.chileaf.fitness.device.wear.cl820.callback.e) new b(this, historySleepAdapter));
            q().C();
        }
    }

    /* access modifiers changed from: private */
    public final void a(int i2, long j2) {
        Intent intent = new Intent(this, CL820HistoryDetailActivity.class);
        intent.putExtra("extra_type", i2);
        intent.putExtra("extra_stamp", j2);
        startActivity(intent);
    }
}
