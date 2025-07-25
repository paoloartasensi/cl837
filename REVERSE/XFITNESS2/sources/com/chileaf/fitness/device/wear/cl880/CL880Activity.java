package com.chileaf.fitness.device.wear.cl880;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import androidx.appcompat.view.menu.l;
import androidx.appcompat.widget.w;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelLazy;
import com.chileaf.fitness.R$color;
import com.chileaf.fitness.R$id;
import com.chileaf.fitness.R$layout;
import com.chileaf.fitness.R$menu;
import com.chileaf.fitness.R$string;
import com.chileaf.fitness.b.q;
import com.chileaf.fitness.base.BaseActivity;
import com.chileaf.fitness.model.DiscoveredDevice;
import com.chileaf.fitness.viewmodel.CL880ViewModel;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.j;
import com.jeremyliao.liveeventbus.BuildConfig;
import h.a.a.a.e.b.f;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import kotlin.jvm.internal.i;
import kotlin.jvm.internal.k;

/* compiled from: CL880Activity.kt */
public final class CL880Activity extends BaseActivity<q> {
    private final kotlin.d E = g.a(new CL880Activity$mDevice$2(this));
    private LineChart F;
    private final kotlin.d G = new ViewModelLazy(k.a(CL880ViewModel.class), new CL880Activity$$special$$inlined$viewModels$2(this), new CL880Activity$$special$$inlined$viewModels$1(this));
    private final kotlin.d H = g.a(new CL880Activity$mHeartColor$2(this));
    private HashMap I;

    /* compiled from: CL880Activity.kt */
    public final class a extends h.a.a.a.c.e {
        public a(CL880Activity cL880Activity) {
        }

        public String a(float f2) {
            int i2 = (int) f2;
            return i2 == 0 ? BuildConfig.FLAVOR : String.valueOf(i2);
        }
    }

    /* compiled from: CL880Activity.kt */
    static final class b<T> implements Observer<String> {
        final /* synthetic */ CL880Activity a;

        b(CL880Activity cL880Activity) {
            this.a = cL880Activity;
        }

        /* renamed from: a */
        public final void onChanged(String str) {
            if (str != null) {
                BaseActivity.a((BaseActivity) this.a, (CharSequence) str, 0, 2, (Object) null);
            }
        }
    }

    /* compiled from: CL880Activity.kt */
    static final class c<T> implements Observer<Integer> {
        final /* synthetic */ CL880Activity a;

        c(CL880Activity cL880Activity) {
            this.a = cL880Activity;
        }

        /* renamed from: a */
        public final void onChanged(Integer num) {
            if (num != null) {
                this.a.e(num.intValue());
            }
        }
    }

    /* compiled from: CL880Activity.kt */
    static final class d implements View.OnClickListener {
        final /* synthetic */ CL880Activity e;

        d(CL880Activity cL880Activity) {
            this.e = cL880Activity;
        }

        public final void onClick(View view) {
            CL880Activity cL880Activity = this.e;
            i.a((Object) view, "it");
            cL880Activity.b(view);
        }
    }

    /* compiled from: CL880Activity.kt */
    static final class e implements w.d {
        final /* synthetic */ CL880Activity a;

        e(CL880Activity cL880Activity) {
            this.a = cL880Activity;
        }

        public final boolean onMenuItemClick(MenuItem menuItem) {
            CL880Activity cL880Activity = this.a;
            i.a((Object) menuItem, "it");
            return cL880Activity.a(menuItem);
        }
    }

    /* access modifiers changed from: private */
    @SuppressLint({"RestrictedApi"})
    public final void b(View view) {
        w wVar = new w(this, view);
        try {
            Field declaredField = wVar.getClass().getDeclaredField("mPopup");
            i.a((Object) declaredField, "popup.javaClass.getDeclaredField(\"mPopup\")");
            declaredField.setAccessible(true);
            l lVar = (l) declaredField.get(wVar);
            if (lVar != null) {
                lVar.a(true);
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        wVar.b().inflate(R$menu.menu_cl880, wVar.a());
        wVar.setOnMenuItemClickListener(new e(this));
        wVar.c();
    }

    /* access modifiers changed from: private */
    public final void e(int i2) {
        LineChart lineChart = this.F;
        if (lineChart != null) {
            j jVar = (j) lineChart.getData();
            if (jVar == null) {
                jVar = new j();
                LineChart lineChart2 = this.F;
                if (lineChart2 != null) {
                    lineChart2.setData(jVar);
                } else {
                    i.d("mChart");
                    throw null;
                }
            }
            if (((f) jVar.a(0)) == null) {
                jVar.a(r());
            }
            double random = Math.random();
            double b2 = (double) jVar.b();
            Double.isNaN(b2);
            int i3 = (int) (random * b2);
            f fVar = (f) jVar.a(i3);
            i.a((Object) fVar, "randomSet");
            jVar.a(new Entry((float) fVar.X(), (float) i2), i3);
            jVar.j();
            LineChart lineChart3 = this.F;
            if (lineChart3 != null) {
                lineChart3.l();
                LineChart lineChart4 = this.F;
                if (lineChart4 != null) {
                    lineChart4.setVisibleXRangeMaximum(15.0f);
                    LineChart lineChart5 = this.F;
                    if (lineChart5 != null) {
                        lineChart5.a((float) jVar.d());
                    } else {
                        i.d("mChart");
                        throw null;
                    }
                } else {
                    i.d("mChart");
                    throw null;
                }
            } else {
                i.d("mChart");
                throw null;
            }
        } else {
            i.d("mChart");
            throw null;
        }
    }

    private final void f(int i2) {
        Intent intent = new Intent(this, CL880HistoryActivity.class);
        intent.putExtra("extra_history", i2);
        startActivity(intent);
    }

    private final void q() {
        LineChart lineChart = this.F;
        if (lineChart != null) {
            lineChart.setNoDataText(getString(R$string.chart_heart_rate_no_data));
            LineChart lineChart2 = this.F;
            if (lineChart2 != null) {
                lineChart2.setNoDataTextColor(androidx.core.content.a.a((Context) this, (int) R$color.colorPrimary));
                LineChart lineChart3 = this.F;
                if (lineChart3 != null) {
                    com.github.mikephil.charting.components.c description = lineChart3.getDescription();
                    i.a((Object) description, "mChart.description");
                    description.a(getString(R$string.chart_heart_rate_unit));
                    LineChart lineChart4 = this.F;
                    if (lineChart4 != null) {
                        lineChart4.setDrawGridBackground(false);
                        LineChart lineChart5 = this.F;
                        if (lineChart5 != null) {
                            lineChart5.setTouchEnabled(false);
                            LineChart lineChart6 = this.F;
                            if (lineChart6 != null) {
                                XAxis xAxis = lineChart6.getXAxis();
                                i.a((Object) xAxis, "xAxis");
                                xAxis.a(XAxis.XAxisPosition.BOTTOM);
                                xAxis.c(false);
                                xAxis.b(true);
                                xAxis.b(0.0f);
                                xAxis.c(1.0f);
                                xAxis.d(true);
                                LineChart lineChart7 = this.F;
                                if (lineChart7 != null) {
                                    YAxis axisLeft = lineChart7.getAxisLeft();
                                    axisLeft.c(true);
                                    i.a((Object) axisLeft, "leftAxis");
                                    axisLeft.b(0.0f);
                                    LineChart lineChart8 = this.F;
                                    if (lineChart8 != null) {
                                        YAxis axisRight = lineChart8.getAxisRight();
                                        axisRight.c(false);
                                        i.a((Object) axisRight, "rightAxis");
                                        axisRight.b(0.0f);
                                        return;
                                    }
                                    i.d("mChart");
                                    throw null;
                                }
                                i.d("mChart");
                                throw null;
                            }
                            i.d("mChart");
                            throw null;
                        }
                        i.d("mChart");
                        throw null;
                    }
                    i.d("mChart");
                    throw null;
                }
                i.d("mChart");
                throw null;
            }
            i.d("mChart");
            throw null;
        }
        i.d("mChart");
        throw null;
    }

    private final LineDataSet r() {
        LineDataSet lineDataSet = new LineDataSet((List<Entry>) null, getString(R$string.text_heart_rate));
        lineDataSet.c(1.0f);
        lineDataSet.d(2.0f);
        lineDataSet.b(10.0f);
        lineDataSet.f(t());
        lineDataSet.j(t());
        lineDataSet.h(-65536);
        lineDataSet.g(t());
        lineDataSet.a(YAxis.AxisDependency.LEFT);
        lineDataSet.a((h.a.a.a.c.e) new a(this));
        return lineDataSet;
    }

    private final DiscoveredDevice s() {
        return (DiscoveredDevice) this.E.getValue();
    }

    private final int t() {
        return ((Number) this.H.getValue()).intValue();
    }

    private final CL880ViewModel u() {
        return (CL880ViewModel) this.G.getValue();
    }

    public View d(int i2) {
        if (this.I == null) {
            this.I = new HashMap();
        }
        View view = (View) this.I.get(Integer.valueOf(i2));
        if (view != null) {
            return view;
        }
        View findViewById = findViewById(i2);
        this.I.put(Integer.valueOf(i2), findViewById);
        return findViewById;
    }

    /* access modifiers changed from: protected */
    public int p() {
        return R$layout.activity_cl880;
    }

    /* access modifiers changed from: protected */
    public CL880ViewModel o() {
        return u();
    }

    /* access modifiers changed from: protected */
    public void a(View view) {
        i.b(view, "root");
        LineChart lineChart = ((q) m()).z;
        i.a((Object) lineChart, "mBinding.chartHeart");
        this.F = lineChart;
        q();
    }

    /* access modifiers changed from: protected */
    public void a(Bundle bundle) {
        String str;
        DiscoveredDevice s = s();
        if (s == null || (str = s.getName()) == null) {
            str = getString(R$string.watch_cl_series);
            i.a((Object) str, "getString(R.string.watch_cl_series)");
        }
        a(str);
        String string = getString(R$string.toolbox);
        i.a((Object) string, "getString(R.string.toolbox)");
        a(string, (View.OnClickListener) new d(this));
        DiscoveredDevice s2 = s();
        if (s2 != null) {
            u().a(s2);
        }
        CL880ViewModel u = u();
        u.g().observe(this, new b(this));
        u.h().observe(this, new c(this));
        ((q) m()).a(u());
    }

    /* access modifiers changed from: private */
    public final boolean a(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R$id.menu_cl880_history:
                f(2);
                break;
            case R$id.menu_cl880_setting:
                String string = getString(R$string.setting);
                i.a((Object) string, "getString(R.string.setting)");
                a(string, "setting_message_remind");
                break;
            case R$id.menu_cl880_sleep:
                BaseActivity.a((BaseActivity) this, CL880SleepActivity.class, false, 2, (Object) null);
                break;
            case R$id.menu_cl880_sport:
                BaseActivity.a((BaseActivity) this, CL880SportActivity.class, false, 2, (Object) null);
                break;
        }
        return false;
    }
}
