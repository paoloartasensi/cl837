package com.chileaf.fitness.device.wear.cl831;

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
import com.chileaf.fitness.device.wear.cl831.adapter.HistoryRecordAdapter;
import com.chileaf.fitness.device.wear.cl831.adapter.HistorySportAdapter;
import com.chileaf.fitness.device.wear.cl831.adapter.IntervalStepAdapter;
import com.chileaf.fitness.device.wear.cl831.model.HistoryOfSport;
import com.chileaf.fitness.device.wear.cl831.model.IntervalStep;
import java.util.HashMap;
import java.util.List;
import kotlin.TypeCastException;

/* compiled from: CL831HistoryActivity.kt */
public final class CL831HistoryActivity extends BaseActivity<a0> {
    private final kotlin.d E = g.a(new CL831HistoryActivity$mType$2(this));
    private final kotlin.d F = g.a(new CL831HistoryActivity$mManager$2(this));
    private boolean G;
    private HashMap H;

    /* compiled from: CL831HistoryActivity.kt */
    public static final class a {
        private a() {
        }

        public /* synthetic */ a(f fVar) {
            this();
        }
    }

    /* compiled from: CL831HistoryActivity.kt */
    static final class b implements View.OnClickListener {
        final /* synthetic */ CL831HistoryActivity e;

        b(CL831HistoryActivity cL831HistoryActivity) {
            this.e = cL831HistoryActivity;
        }

        public final void onClick(View view) {
            this.e.a(2000);
            this.e.q().B();
        }
    }

    /* compiled from: CL831HistoryActivity.kt */
    static final class c implements com.chileaf.fitness.device.wear.cl831.callback.b {
        final /* synthetic */ CL831HistoryActivity e;

        /* renamed from: f  reason: collision with root package name */
        final /* synthetic */ HistorySportAdapter f1177f;

        /* compiled from: CL831HistoryActivity.kt */
        static final class a implements Runnable {
            final /* synthetic */ c e;

            /* renamed from: f  reason: collision with root package name */
            final /* synthetic */ List f1178f;

            a(c cVar, List list) {
                this.e = cVar;
                this.f1178f = list;
            }

            public final void run() {
                this.e.f1177f.replaceData(this.f1178f);
                this.e.e.n();
            }
        }

        c(CL831HistoryActivity cL831HistoryActivity, HistorySportAdapter historySportAdapter) {
            this.e = cL831HistoryActivity;
            this.f1177f = historySportAdapter;
        }

        public final void b(BluetoothDevice bluetoothDevice, List<HistoryOfSport> list) {
            kotlin.jvm.internal.i.b(bluetoothDevice, "<anonymous parameter 0>");
            this.e.runOnUiThread(new a(this, list));
        }
    }

    /* compiled from: CL831HistoryActivity.kt */
    static final class d implements View.OnClickListener {
        final /* synthetic */ CL831HistoryActivity e;

        d(CL831HistoryActivity cL831HistoryActivity) {
            this.e = cL831HistoryActivity;
        }

        public final void onClick(View view) {
            this.e.a(2000);
            this.e.q().A();
        }
    }

    /* compiled from: CL831HistoryActivity.kt */
    static final class e implements BaseQuickAdapter.OnItemClickListener {
        final /* synthetic */ CL831HistoryActivity a;

        e(CL831HistoryActivity cL831HistoryActivity) {
            this.a = cL831HistoryActivity;
        }

        public final void onItemClick(BaseQuickAdapter<?, ?> baseQuickAdapter, View view, int i2) {
            kotlin.jvm.internal.i.b(baseQuickAdapter, "adapter1");
            Object obj = baseQuickAdapter.getData().get(i2);
            if (obj != null) {
                this.a.b(((HistoryOfRecord) obj).e);
                return;
            }
            throw new TypeCastException("null cannot be cast to non-null type com.android.chileaf.fitness.model.HistoryOfRecord");
        }
    }

    /* compiled from: CL831HistoryActivity.kt */
    static final class f implements com.android.chileaf.fitness.x.b {
        final /* synthetic */ CL831HistoryActivity e;

        /* renamed from: f  reason: collision with root package name */
        final /* synthetic */ HistoryRecordAdapter f1179f;

        /* compiled from: CL831HistoryActivity.kt */
        static final class a implements Runnable {
            final /* synthetic */ f e;

            /* renamed from: f  reason: collision with root package name */
            final /* synthetic */ List f1180f;

            a(f fVar, List list) {
                this.e = fVar;
                this.f1180f = list;
            }

            public final void run() {
                this.e.f1179f.replaceData(this.f1180f);
                this.e.e.n();
            }
        }

        f(CL831HistoryActivity cL831HistoryActivity, HistoryRecordAdapter historyRecordAdapter) {
            this.e = cL831HistoryActivity;
            this.f1179f = historyRecordAdapter;
        }

        public final void a(BluetoothDevice bluetoothDevice, List<HistoryOfRecord> list) {
            kotlin.jvm.internal.i.b(bluetoothDevice, "<anonymous parameter 0>");
            this.e.runOnUiThread(new a(this, list));
        }
    }

    /* compiled from: CL831HistoryActivity.kt */
    static final class g implements View.OnClickListener {
        final /* synthetic */ CL831HistoryActivity e;

        g(CL831HistoryActivity cL831HistoryActivity) {
            this.e = cL831HistoryActivity;
        }

        public final void onClick(View view) {
            this.e.a(2000);
            this.e.q().C();
        }
    }

    /* compiled from: CL831HistoryActivity.kt */
    static final class h implements com.chileaf.fitness.device.wear.cl831.callback.c {
        final /* synthetic */ CL831HistoryActivity e;

        /* renamed from: f  reason: collision with root package name */
        final /* synthetic */ IntervalStepAdapter f1181f;

        /* compiled from: CL831HistoryActivity.kt */
        static final class a implements Runnable {
            final /* synthetic */ h e;

            /* renamed from: f  reason: collision with root package name */
            final /* synthetic */ List f1182f;

            a(h hVar, List list) {
                this.e = hVar;
                this.f1182f = list;
            }

            public final void run() {
                this.e.f1181f.replaceData(this.f1182f);
                this.e.e.n();
            }
        }

        h(CL831HistoryActivity cL831HistoryActivity, IntervalStepAdapter intervalStepAdapter) {
            this.e = cL831HistoryActivity;
            this.f1181f = intervalStepAdapter;
        }

        public final void e(BluetoothDevice bluetoothDevice, List<IntervalStep> list) {
            kotlin.jvm.internal.i.b(bluetoothDevice, "<anonymous parameter 0>");
            this.e.runOnUiThread(new a(this, list));
        }
    }

    /* compiled from: CL831HistoryActivity.kt */
    static final class i implements View.OnClickListener {
        final /* synthetic */ CL831HistoryActivity e;

        i(CL831HistoryActivity cL831HistoryActivity) {
            this.e = cL831HistoryActivity;
        }

        public final void onClick(View view) {
            this.e.a(2000);
            this.e.q().D();
        }
    }

    /* compiled from: CL831HistoryActivity.kt */
    static final class j implements com.chileaf.fitness.device.wear.cl831.callback.d {
        final /* synthetic */ CL831HistoryActivity e;

        /* renamed from: f  reason: collision with root package name */
        final /* synthetic */ HistoryRecordAdapter f1183f;

        /* compiled from: CL831HistoryActivity.kt */
        static final class a implements Runnable {
            final /* synthetic */ j e;

            /* renamed from: f  reason: collision with root package name */
            final /* synthetic */ List f1184f;

            a(j jVar, List list) {
                this.e = jVar;
                this.f1184f = list;
            }

            public final void run() {
                this.e.f1183f.replaceData(this.f1184f);
                this.e.e.n();
            }
        }

        j(CL831HistoryActivity cL831HistoryActivity, HistoryRecordAdapter historyRecordAdapter) {
            this.e = cL831HistoryActivity;
            this.f1183f = historyRecordAdapter;
        }

        public final void i(BluetoothDevice bluetoothDevice, List<HistoryOfRecord> list) {
            kotlin.jvm.internal.i.b(bluetoothDevice, "<anonymous parameter 0>");
            this.e.runOnUiThread(new a(this, list));
        }
    }

    static {
        new a((f) null);
    }

    /* access modifiers changed from: private */
    public final void b(long j2) {
        Intent intent = new Intent(this, CL831HistoryDetailActivity.class);
        intent.putExtra("extra_stamp", j2);
        startActivity(intent);
    }

    /* access modifiers changed from: private */
    public final CL831Manager q() {
        return (CL831Manager) this.F.getValue();
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
            String string = getString(R$string.sport_history);
            kotlin.jvm.internal.i.a((Object) string, "getString(R.string.sport_history)");
            a(string);
            String string2 = getString(R$string.refresh);
            kotlin.jvm.internal.i.a((Object) string2, "getString(R.string.refresh)");
            a(string2, (View.OnClickListener) new b(this));
            a(2000);
            HistorySportAdapter historySportAdapter = new HistorySportAdapter();
            RecyclerView recyclerView = ((a0) m()).z;
            kotlin.jvm.internal.i.a((Object) recyclerView, "mBinding.rvHistory");
            recyclerView.setAdapter(historySportAdapter);
            q().a((com.chileaf.fitness.device.wear.cl831.callback.b) new c(this, historySportAdapter));
            q().B();
        } else if (r != null && r.intValue() == 4) {
            String string3 = getString(R$string.hr_history_list);
            kotlin.jvm.internal.i.a((Object) string3, "getString(R.string.hr_history_list)");
            a(string3);
            String string4 = getString(R$string.refresh);
            kotlin.jvm.internal.i.a((Object) string4, "getString(R.string.refresh)");
            a(string4, (View.OnClickListener) new d(this));
            a(2000);
            HistoryRecordAdapter historyRecordAdapter = new HistoryRecordAdapter();
            historyRecordAdapter.setOnItemClickListener(new e(this));
            RecyclerView recyclerView2 = ((a0) m()).z;
            kotlin.jvm.internal.i.a((Object) recyclerView2, "mBinding.rvHistory");
            recyclerView2.setAdapter(historyRecordAdapter);
            q().a((com.android.chileaf.fitness.x.b) new f(this, historyRecordAdapter));
            q().A();
        } else if (r != null && r.intValue() == 6) {
            String string5 = getString(R$string.interval_steps_history);
            kotlin.jvm.internal.i.a((Object) string5, "getString(R.string.interval_steps_history)");
            a(string5);
            String string6 = getString(R$string.refresh);
            kotlin.jvm.internal.i.a((Object) string6, "getString(R.string.refresh)");
            a(string6, (View.OnClickListener) new g(this));
            a(2000);
            IntervalStepAdapter intervalStepAdapter = new IntervalStepAdapter();
            RecyclerView recyclerView3 = ((a0) m()).z;
            kotlin.jvm.internal.i.a((Object) recyclerView3, "mBinding.rvHistory");
            recyclerView3.setAdapter(intervalStepAdapter);
            q().a((com.chileaf.fitness.device.wear.cl831.callback.c) new h(this, intervalStepAdapter));
            q().C();
        } else if (r != null && r.intValue() == 8) {
            String string7 = getString(R$string.single_press_history);
            kotlin.jvm.internal.i.a((Object) string7, "getString(R.string.single_press_history)");
            a(string7);
            String string8 = getString(R$string.refresh);
            kotlin.jvm.internal.i.a((Object) string8, "getString(R.string.refresh)");
            a(string8, (View.OnClickListener) new i(this));
            a(2000);
            HistoryRecordAdapter historyRecordAdapter2 = new HistoryRecordAdapter();
            RecyclerView recyclerView4 = ((a0) m()).z;
            kotlin.jvm.internal.i.a((Object) recyclerView4, "mBinding.rvHistory");
            recyclerView4.setAdapter(historyRecordAdapter2);
            q().a((com.chileaf.fitness.device.wear.cl831.callback.d) new j(this, historyRecordAdapter2));
            q().D();
        }
    }
}
