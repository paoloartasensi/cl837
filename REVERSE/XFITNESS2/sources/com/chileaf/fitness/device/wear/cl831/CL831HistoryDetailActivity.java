package com.chileaf.fitness.device.wear.cl831;

import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.view.View;
import com.android.chileaf.fitness.model.HistoryOfHeartRate;
import com.chileaf.fitness.R$layout;
import com.chileaf.fitness.R$string;
import com.chileaf.fitness.b.c0;
import com.chileaf.fitness.base.BaseActivity;
import com.chileaf.fitness.base.BaseViewModel;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.j;
import com.jeremyliao.liveeventbus.BuildConfig;
import h.a.a.a.c.e;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import kotlin.d;
import kotlin.jvm.internal.i;

/* compiled from: CL831HistoryDetailActivity.kt */
public final class CL831HistoryDetailActivity extends BaseActivity<c0> {
    private LineChart E;
    private final d F = g.a(CL831HistoryDetailActivity$mDateFormat$2.INSTANCE);
    private final d G = g.a(new CL831HistoryDetailActivity$mManager$2(this));
    private final d H;
    private HashMap I;

    /* compiled from: CL831HistoryDetailActivity.kt */
    public static final class a {
        private a() {
        }

        public /* synthetic */ a(f fVar) {
            this();
        }
    }

    /* compiled from: CL831HistoryDetailActivity.kt */
    private static final class b extends e {
        private final List<String> a;

        public b(List<String> list) {
            i.b(list, "stamps");
            this.a = list;
        }

        public String a(float f2) {
            int i2 = (int) f2;
            return i2 <= this.a.size() ? this.a.get(i2) : BuildConfig.FLAVOR;
        }
    }

    /* compiled from: CL831HistoryDetailActivity.kt */
    static final class c implements com.android.chileaf.fitness.x.a {
        final /* synthetic */ CL831HistoryDetailActivity e;

        /* compiled from: CL831HistoryDetailActivity.kt */
        static final class a implements Runnable {
            final /* synthetic */ c e;

            /* renamed from: f  reason: collision with root package name */
            final /* synthetic */ List f1185f;

            a(c cVar, List list) {
                this.e = cVar;
                this.f1185f = list;
            }

            public final void run() {
                j.a.a.b("heartRates:%d %s", Integer.valueOf(this.f1185f.size()), this.f1185f.toString());
                this.e.e.a((List<? extends HistoryOfHeartRate>) this.f1185f);
                this.e.e.n();
            }
        }

        c(CL831HistoryDetailActivity cL831HistoryDetailActivity) {
            this.e = cL831HistoryDetailActivity;
        }

        public final void c(BluetoothDevice bluetoothDevice, List<? extends HistoryOfHeartRate> list) {
            i.b(list, "heartRates");
            this.e.runOnUiThread(new a(this, list));
        }
    }

    static {
        new a((f) null);
    }

    public CL831HistoryDetailActivity() {
        d<T> unused = g.a(new CL831HistoryDetailActivity$type$2(this));
        this.H = g.a(new CL831HistoryDetailActivity$stamp$2(this));
    }

    private final SimpleDateFormat q() {
        return (SimpleDateFormat) this.F.getValue();
    }

    private final CL831Manager r() {
        return (CL831Manager) this.G.getValue();
    }

    private final long s() {
        return ((Number) this.H.getValue()).longValue();
    }

    private final void t() {
        LineChart lineChart = this.E;
        if (lineChart != null) {
            lineChart.setNoDataText(BuildConfig.FLAVOR);
            LineChart lineChart2 = this.E;
            if (lineChart2 != null) {
                lineChart2.setTouchEnabled(true);
                LineChart lineChart3 = this.E;
                if (lineChart3 != null) {
                    lineChart3.setScaleEnabled(true);
                    LineChart lineChart4 = this.E;
                    if (lineChart4 != null) {
                        lineChart4.setPinchZoom(false);
                        LineChart lineChart5 = this.E;
                        if (lineChart5 != null) {
                            com.github.mikephil.charting.components.c description = lineChart5.getDescription();
                            i.a((Object) description, "mChart.description");
                            description.a(false);
                            LineChart lineChart6 = this.E;
                            if (lineChart6 != null) {
                                Legend legend = lineChart6.getLegend();
                                i.a((Object) legend, "mChart.legend");
                                legend.a(true);
                                LineChart lineChart7 = this.E;
                                if (lineChart7 != null) {
                                    lineChart7.setScaleYEnabled(false);
                                    LineChart lineChart8 = this.E;
                                    if (lineChart8 != null) {
                                        lineChart8.setScaleXEnabled(true);
                                        LineChart lineChart9 = this.E;
                                        if (lineChart9 != null) {
                                            lineChart9.setDragEnabled(true);
                                            LineChart lineChart10 = this.E;
                                            if (lineChart10 != null) {
                                                lineChart10.getAxisLeft().c(true);
                                                LineChart lineChart11 = this.E;
                                                if (lineChart11 != null) {
                                                    lineChart11.getAxisLeft().b(true);
                                                    LineChart lineChart12 = this.E;
                                                    if (lineChart12 != null) {
                                                        YAxis axisLeft = lineChart12.getAxisLeft();
                                                        i.a((Object) axisLeft, "mChart.axisLeft");
                                                        axisLeft.a(true);
                                                        LineChart lineChart13 = this.E;
                                                        if (lineChart13 != null) {
                                                            YAxis axisLeft2 = lineChart13.getAxisLeft();
                                                            i.a((Object) axisLeft2, "mChart.axisLeft");
                                                            axisLeft2.b(0.0f);
                                                            LineChart lineChart14 = this.E;
                                                            if (lineChart14 != null) {
                                                                YAxis axisRight = lineChart14.getAxisRight();
                                                                i.a((Object) axisRight, "mChart.axisRight");
                                                                axisRight.a(false);
                                                                LineChart lineChart15 = this.E;
                                                                if (lineChart15 != null) {
                                                                    XAxis xAxis = lineChart15.getXAxis();
                                                                    i.a((Object) xAxis, "mChart.xAxis");
                                                                    xAxis.a(8.0f);
                                                                    LineChart lineChart16 = this.E;
                                                                    if (lineChart16 != null) {
                                                                        XAxis xAxis2 = lineChart16.getXAxis();
                                                                        i.a((Object) xAxis2, "mChart.xAxis");
                                                                        xAxis2.c(1.0f);
                                                                        LineChart lineChart17 = this.E;
                                                                        if (lineChart17 != null) {
                                                                            lineChart17.getXAxis().b(true);
                                                                            LineChart lineChart18 = this.E;
                                                                            if (lineChart18 != null) {
                                                                                lineChart18.getXAxis().c(false);
                                                                                LineChart lineChart19 = this.E;
                                                                                if (lineChart19 != null) {
                                                                                    XAxis xAxis3 = lineChart19.getXAxis();
                                                                                    i.a((Object) xAxis3, "mChart.xAxis");
                                                                                    xAxis3.a(XAxis.XAxisPosition.BOTTOM);
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
                i.d("mChart");
                throw null;
            }
            i.d("mChart");
            throw null;
        }
        i.d("mChart");
        throw null;
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
    public BaseViewModel o() {
        return null;
    }

    /* access modifiers changed from: protected */
    public int p() {
        return R$layout.activity_history_detail;
    }

    /* access modifiers changed from: protected */
    public void a(View view) {
        i.b(view, "root");
        LineChart lineChart = ((c0) m()).z;
        i.a((Object) lineChart, "mBinding.chartHistory");
        this.E = lineChart;
        t();
    }

    /* access modifiers changed from: protected */
    public void a(Bundle bundle) {
        a(30000);
        String string = getString(R$string.hr_history_detail);
        i.a((Object) string, "getString(R.string.hr_history_detail)");
        a(string);
        r().a((com.android.chileaf.fitness.x.a) new c(this));
        r().b(s());
    }

    /* access modifiers changed from: private */
    public final void a(List<? extends HistoryOfHeartRate> list) {
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        int size = list.size();
        for (int i2 = 0; i2 < size; i2++) {
            HistoryOfHeartRate historyOfHeartRate = (HistoryOfHeartRate) list.get(i2);
            arrayList.add(new Entry((float) i2, (float) historyOfHeartRate.f1141f));
            String format = q().format(new Date(historyOfHeartRate.e));
            i.a((Object) format, "mDateFormat.format(Date(history.stamp))");
            arrayList2.add(format);
        }
        LineChart lineChart = this.E;
        if (lineChart != null) {
            lineChart.D();
            LineDataSet lineDataSet = new LineDataSet(arrayList, getString(R$string.text_heart_rate));
            lineDataSet.b(8.0f);
            lineDataSet.d(1.5f);
            lineDataSet.f(-65536);
            lineDataSet.i(-65536);
            lineDataSet.j(-65536);
            lineDataSet.g(-65536);
            lineDataSet.c(1.0f);
            lineDataSet.a(true);
            lineDataSet.d(true);
            lineDataSet.b(false);
            lineDataSet.a(LineDataSet.Mode.LINEAR);
            lineDataSet.c(false);
            LineChart lineChart2 = this.E;
            if (lineChart2 != null) {
                XAxis xAxis = lineChart2.getXAxis();
                i.a((Object) xAxis, "mChart.xAxis");
                xAxis.a((e) new b(arrayList2));
                j jVar = new j(lineDataSet);
                LineChart lineChart3 = this.E;
                if (lineChart3 != null) {
                    lineChart3.setData(jVar);
                    LineChart lineChart4 = this.E;
                    if (lineChart4 != null) {
                        lineChart4.invalidate();
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
}
