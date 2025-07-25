package com.chileaf.fitness.device.wear.cl880;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.e;
import androidx.recyclerview.widget.u;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chileaf.fitness.R$layout;
import com.chileaf.fitness.R$string;
import com.chileaf.fitness.base.BaseActivity;
import com.chileaf.fitness.base.BaseViewModel;
import com.chileaf.fitness.device.wear.cl880.adapter.AlarmAdapter;
import com.chileaf.fitness.device.wear.cl880.model.AlarmConfig;
import java.util.HashMap;
import java.util.List;
import kotlin.TypeCastException;
import kotlin.jvm.internal.i;

/* compiled from: AlarmSettingActivity.kt */
public final class AlarmSettingActivity extends BaseActivity<com.chileaf.fitness.b.c> {
    private RecyclerView E;
    private AlarmAdapter F;
    private final kotlin.d G = g.a(AlarmSettingActivity$mManager$2.INSTANCE);
    private HashMap H;

    /* compiled from: AlarmSettingActivity.kt */
    public static final class a {
        private a() {
        }

        public /* synthetic */ a(f fVar) {
            this();
        }
    }

    /* compiled from: AlarmSettingActivity.kt */
    static final class b implements View.OnClickListener {
        final /* synthetic */ AlarmSettingActivity e;

        b(AlarmSettingActivity alarmSettingActivity) {
            this.e = alarmSettingActivity;
        }

        public final void onClick(View view) {
            this.e.startActivityForResult(new Intent(this.e, AlarmEditActivity.class), 3);
        }
    }

    /* compiled from: AlarmSettingActivity.kt */
    static final class c implements BaseQuickAdapter.OnItemClickListener {
        final /* synthetic */ AlarmSettingActivity a;

        c(AlarmSettingActivity alarmSettingActivity) {
            this.a = alarmSettingActivity;
        }

        public final void onItemClick(BaseQuickAdapter<?, ?> baseQuickAdapter, View view, int i2) {
            Intent intent = new Intent(this.a, AlarmEditActivity.class);
            intent.putExtra("EXTRA_ALARM", (AlarmConfig) AlarmSettingActivity.a(this.a).getData().get(i2));
            this.a.startActivityForResult(intent, 5);
        }
    }

    /* compiled from: AlarmSettingActivity.kt */
    static final class d implements AlarmAdapter.a {
        final /* synthetic */ AlarmSettingActivity a;

        d(AlarmSettingActivity alarmSettingActivity) {
            this.a = alarmSettingActivity;
        }

        public final void a() {
            this.a.s();
        }
    }

    static {
        new a((f) null);
    }

    public static final /* synthetic */ AlarmAdapter a(AlarmSettingActivity alarmSettingActivity) {
        AlarmAdapter alarmAdapter = alarmSettingActivity.F;
        if (alarmAdapter != null) {
            return alarmAdapter;
        }
        i.d("mAdapter");
        throw null;
    }

    private final void q() {
        AlarmAdapter alarmAdapter = this.F;
        if (alarmAdapter != null) {
            if (alarmAdapter.getData().size() < 8) {
                String string = getString(R$string.add);
                i.a((Object) string, "getString(R.string.add)");
                a(string, (View.OnClickListener) new b(this));
                return;
            }
            return;
        }
        i.d("mAdapter");
        throw null;
    }

    private final com.chileaf.fitness.device.wear.cl880.external.c r() {
        return (com.chileaf.fitness.device.wear.cl880.external.c) this.G.getValue();
    }

    /* access modifiers changed from: private */
    public final void s() {
        AlarmAdapter alarmAdapter = this.F;
        if (alarmAdapter != null) {
            List data = alarmAdapter.getData();
            i.a((Object) data, "mAdapter.data");
            r().a((List<AlarmConfig>) data);
            q();
            return;
        }
        i.d("mAdapter");
        throw null;
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
    public void onActivityResult(int i2, int i3, Intent intent) {
        super.onActivityResult(i2, i3, intent);
        if (intent != null) {
            AlarmConfig alarmConfig = (AlarmConfig) intent.getSerializableExtra("extra_result");
            j.a.a.a("onActivityResult:%s", alarmConfig);
            if (alarmConfig != null && i3 == -1) {
                if (i2 == 5) {
                    AlarmAdapter alarmAdapter = this.F;
                    if (alarmAdapter != null) {
                        alarmAdapter.a(alarmConfig);
                    } else {
                        i.d("mAdapter");
                        throw null;
                    }
                } else if (i2 == 3) {
                    AlarmAdapter alarmAdapter2 = this.F;
                    if (alarmAdapter2 != null) {
                        alarmAdapter2.addData(alarmConfig);
                    } else {
                        i.d("mAdapter");
                        throw null;
                    }
                }
                s();
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onPause() {
        s();
        super.onPause();
    }

    /* access modifiers changed from: protected */
    public int p() {
        return R$layout.activity_alarm_setting;
    }

    /* access modifiers changed from: protected */
    public void a(View view) {
        i.b(view, "root");
        RecyclerView recyclerView = ((com.chileaf.fitness.b.c) m()).z;
        i.a((Object) recyclerView, "mBinding.rvAlarm");
        this.E = recyclerView;
        if (recyclerView != null) {
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
        i.d("mRvAlarm");
        throw null;
    }

    /* access modifiers changed from: protected */
    public void a(Bundle bundle) {
        String string = getString(R$string.alarm_setting);
        i.a((Object) string, "getString(R.string.alarm_setting)");
        a(string);
        List<AlarmConfig> a2 = r().a();
        this.F = new AlarmAdapter();
        if (a2 != null) {
            j.a.a.a("List<AlarmConfig>:%s", a2.toString());
            AlarmAdapter alarmAdapter = this.F;
            if (alarmAdapter != null) {
                alarmAdapter.addData(a2);
            } else {
                i.d("mAdapter");
                throw null;
            }
        }
        RecyclerView recyclerView = this.E;
        if (recyclerView != null) {
            AlarmAdapter alarmAdapter2 = this.F;
            if (alarmAdapter2 != null) {
                recyclerView.setAdapter(alarmAdapter2);
                AlarmAdapter alarmAdapter3 = this.F;
                if (alarmAdapter3 != null) {
                    alarmAdapter3.setOnItemClickListener(new c(this));
                    AlarmAdapter alarmAdapter4 = this.F;
                    if (alarmAdapter4 != null) {
                        alarmAdapter4.setOnItemLongClickListener(new AlarmSettingActivity$initData$2(this));
                        AlarmAdapter alarmAdapter5 = this.F;
                        if (alarmAdapter5 != null) {
                            alarmAdapter5.a((AlarmAdapter.a) new d(this));
                            q();
                            return;
                        }
                        i.d("mAdapter");
                        throw null;
                    }
                    i.d("mAdapter");
                    throw null;
                }
                i.d("mAdapter");
                throw null;
            }
            i.d("mAdapter");
            throw null;
        }
        i.d("mRvAlarm");
        throw null;
    }
}
