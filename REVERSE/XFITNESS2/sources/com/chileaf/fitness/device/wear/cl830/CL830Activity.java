package com.chileaf.fitness.device.wear.cl830;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelLazy;
import com.chileaf.fitness.R$color;
import com.chileaf.fitness.R$layout;
import com.chileaf.fitness.R$string;
import com.chileaf.fitness.b.m;
import com.chileaf.fitness.base.BaseActivity;
import com.chileaf.fitness.model.DiscoveredDevice;
import com.chileaf.fitness.ui.c.c;
import com.chileaf.fitness.viewmodel.CL830ViewModel;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.j;
import com.jeremyliao.liveeventbus.BuildConfig;
import h.a.a.a.c.e;
import h.a.a.a.e.b.f;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import kotlin.jvm.internal.i;
import kotlin.jvm.internal.k;

/* compiled from: CL830Activity.kt */
public final class CL830Activity extends BaseActivity<m> implements c.d {
    private final kotlin.d E = g.a(new CL830Activity$mDevice$2(this));
    private LineChart F;
    private final kotlin.d G = new ViewModelLazy(k.a(CL830ViewModel.class), new CL830Activity$$special$$inlined$viewModels$2(this), new CL830Activity$$special$$inlined$viewModels$1(this));
    private final kotlin.d H = g.a(new CL830Activity$mHeartColor$2(this));
    private final kotlin.d I = g.a(new CL830Activity$mActionDialog$2(this));
    private HashMap J;

    /* compiled from: CL830Activity.kt */
    public final class a extends e {
        public a(CL830Activity cL830Activity) {
        }

        public String a(float f2) {
            int i2 = (int) f2;
            return i2 == 0 ? BuildConfig.FLAVOR : String.valueOf(i2);
        }
    }

    /* compiled from: CL830Activity.kt */
    static final class b<T> implements Observer<String> {
        final /* synthetic */ CL830Activity a;

        b(CL830Activity cL830Activity) {
            this.a = cL830Activity;
        }

        /* renamed from: a */
        public final void onChanged(String str) {
            if (str != null) {
                BaseActivity.a((BaseActivity) this.a, (CharSequence) str, 0, 2, (Object) null);
            }
        }
    }

    /* compiled from: CL830Activity.kt */
    static final class c<T> implements Observer<Integer> {
        final /* synthetic */ CL830Activity a;

        c(CL830Activity cL830Activity) {
            this.a = cL830Activity;
        }

        /* renamed from: a */
        public final void onChanged(Integer num) {
            if (num != null) {
                this.a.e(num.intValue());
            }
        }
    }

    /* compiled from: CL830Activity.kt */
    static final class d implements View.OnClickListener {
        final /* synthetic */ CL830Activity e;

        d(CL830Activity cL830Activity) {
            this.e = cL830Activity;
        }

        public final void onClick(View view) {
            ArrayList arrayList = new ArrayList();
            arrayList.add(new c.a(this.e.getString(R$string.vibrator_mode1)));
            arrayList.add(new c.a(this.e.getString(R$string.vibrator_mode2)));
            arrayList.add(new c.a(this.e.getString(R$string.vibrator_mode3)));
            this.e.s().a(arrayList, this.e);
            this.e.s().show();
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
        lineDataSet.f(u());
        lineDataSet.j(u());
        lineDataSet.h(-65536);
        lineDataSet.g(u());
        lineDataSet.a(YAxis.AxisDependency.LEFT);
        lineDataSet.a((e) new a(this));
        return lineDataSet;
    }

    /* access modifiers changed from: private */
    public final com.chileaf.fitness.ui.c.c s() {
        return (com.chileaf.fitness.ui.c.c) this.I.getValue();
    }

    private final DiscoveredDevice t() {
        return (DiscoveredDevice) this.E.getValue();
    }

    private final int u() {
        return ((Number) this.H.getValue()).intValue();
    }

    private final CL830ViewModel v() {
        return (CL830ViewModel) this.G.getValue();
    }

    public View d(int i2) {
        if (this.J == null) {
            this.J = new HashMap();
        }
        View view = (View) this.J.get(Integer.valueOf(i2));
        if (view != null) {
            return view;
        }
        View findViewById = findViewById(i2);
        this.J.put(Integer.valueOf(i2), findViewById);
        return findViewById;
    }

    /* access modifiers changed from: protected */
    public int p() {
        return R$layout.activity_cl830;
    }

    /* access modifiers changed from: protected */
    public CL830ViewModel o() {
        return v();
    }

    /* access modifiers changed from: protected */
    public void a(View view) {
        i.b(view, "root");
        LineChart lineChart = ((m) m()).z;
        i.a((Object) lineChart, "mBinding.chartHeart");
        this.F = lineChart;
        q();
    }

    /* access modifiers changed from: protected */
    public void a(Bundle bundle) {
        String str;
        DiscoveredDevice t = t();
        if (t == null || (str = t.getName()) == null) {
            str = getString(R$string.armband);
            i.a((Object) str, "getString(R.string.armband)");
        }
        a(str);
        String string = getString(R$string.setting);
        i.a((Object) string, "getString(R.string.setting)");
        a(string, (View.OnClickListener) new d(this));
        DiscoveredDevice t2 = t();
        if (t2 != null) {
            v().a(t2);
        }
        CL830ViewModel v = v();
        v.g().observe(this, new b(this));
        v.h().observe(this, new c(this));
        ((m) m()).a(v());
    }

    public void a(View view, int i2) {
        v().a(i2);
    }
}
