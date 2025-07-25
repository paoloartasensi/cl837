package com.chileaf.fitness.device.wear.cl800;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelLazy;
import com.chileaf.fitness.R$color;
import com.chileaf.fitness.R$layout;
import com.chileaf.fitness.R$string;
import com.chileaf.fitness.b.i;
import com.chileaf.fitness.base.BaseActivity;
import com.chileaf.fitness.model.DiscoveredDevice;
import com.chileaf.fitness.viewmodel.CL800ViewModel;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.j;
import com.jeremyliao.liveeventbus.BuildConfig;
import h.a.a.a.c.e;
import h.a.a.a.e.b.f;
import java.util.HashMap;
import java.util.List;
import kotlin.d;
import kotlin.jvm.internal.k;

/* compiled from: CL800Activity.kt */
public final class CL800Activity extends BaseActivity<i> {
    private final d E = g.a(new CL800Activity$mDevice$2(this));
    private LineChart F;
    private final d G = new ViewModelLazy(k.a(CL800ViewModel.class), new CL800Activity$$special$$inlined$viewModels$2(this), new CL800Activity$$special$$inlined$viewModels$1(this));
    private final d H = g.a(new CL800Activity$mHeartColor$2(this));
    private HashMap I;

    /* compiled from: CL800Activity.kt */
    public final class a extends e {
        public a(CL800Activity cL800Activity) {
        }

        public String a(float f2) {
            int i2 = (int) f2;
            return i2 == 0 ? BuildConfig.FLAVOR : String.valueOf(i2);
        }
    }

    /* compiled from: CL800Activity.kt */
    static final class b<T> implements Observer<String> {
        final /* synthetic */ CL800Activity a;

        b(CL800Activity cL800Activity) {
            this.a = cL800Activity;
        }

        /* renamed from: a */
        public final void onChanged(String str) {
            if (str != null) {
                BaseActivity.a((BaseActivity) this.a, (CharSequence) str, 0, 2, (Object) null);
            }
        }
    }

    /* compiled from: CL800Activity.kt */
    static final class c<T> implements Observer<Integer> {
        final /* synthetic */ CL800Activity a;

        c(CL800Activity cL800Activity) {
            this.a = cL800Activity;
        }

        /* renamed from: a */
        public final void onChanged(Integer num) {
            if (num != null) {
                this.a.e(num.intValue());
            }
        }
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
                    kotlin.jvm.internal.i.d("mChart");
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
            kotlin.jvm.internal.i.a((Object) fVar, "randomSet");
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
                        kotlin.jvm.internal.i.d("mChart");
                        throw null;
                    }
                } else {
                    kotlin.jvm.internal.i.d("mChart");
                    throw null;
                }
            } else {
                kotlin.jvm.internal.i.d("mChart");
                throw null;
            }
        } else {
            kotlin.jvm.internal.i.d("mChart");
            throw null;
        }
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
                    kotlin.jvm.internal.i.a((Object) description, "mChart.description");
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
                                kotlin.jvm.internal.i.a((Object) xAxis, "xAxis");
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
                                    kotlin.jvm.internal.i.a((Object) axisLeft, "leftAxis");
                                    axisLeft.b(0.0f);
                                    LineChart lineChart8 = this.F;
                                    if (lineChart8 != null) {
                                        YAxis axisRight = lineChart8.getAxisRight();
                                        axisRight.c(false);
                                        kotlin.jvm.internal.i.a((Object) axisRight, "rightAxis");
                                        axisRight.b(0.0f);
                                        return;
                                    }
                                    kotlin.jvm.internal.i.d("mChart");
                                    throw null;
                                }
                                kotlin.jvm.internal.i.d("mChart");
                                throw null;
                            }
                            kotlin.jvm.internal.i.d("mChart");
                            throw null;
                        }
                        kotlin.jvm.internal.i.d("mChart");
                        throw null;
                    }
                    kotlin.jvm.internal.i.d("mChart");
                    throw null;
                }
                kotlin.jvm.internal.i.d("mChart");
                throw null;
            }
            kotlin.jvm.internal.i.d("mChart");
            throw null;
        }
        kotlin.jvm.internal.i.d("mChart");
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
        lineDataSet.a((e) new a(this));
        return lineDataSet;
    }

    private final DiscoveredDevice s() {
        return (DiscoveredDevice) this.E.getValue();
    }

    private final int t() {
        return ((Number) this.H.getValue()).intValue();
    }

    private final CL800ViewModel u() {
        return (CL800ViewModel) this.G.getValue();
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
        return R$layout.activity_cl800;
    }

    /* access modifiers changed from: protected */
    public void a(View view) {
        kotlin.jvm.internal.i.b(view, "root");
        LineChart lineChart = ((i) m()).z;
        kotlin.jvm.internal.i.a((Object) lineChart, "mBinding.chartHeart");
        this.F = lineChart;
        q();
    }

    /* access modifiers changed from: protected */
    public CL800ViewModel o() {
        return u();
    }

    /* access modifiers changed from: protected */
    public void a(Bundle bundle) {
        String str;
        DiscoveredDevice s = s();
        if (s == null || (str = s.getName()) == null) {
            str = getString(R$string.text_wear);
            kotlin.jvm.internal.i.a((Object) str, "getString(R.string.text_wear)");
        }
        a(str);
        DiscoveredDevice s2 = s();
        if (s2 != null) {
            u().a(s2);
        }
        CL800ViewModel u = u();
        u.g().observe(this, new b(this));
        u.h().observe(this, new c(this));
        ((i) m()).a(u());
    }
}
