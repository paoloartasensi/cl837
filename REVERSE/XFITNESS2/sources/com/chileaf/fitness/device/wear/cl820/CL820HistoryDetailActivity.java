package com.chileaf.fitness.device.wear.cl820;

import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.view.View;
import com.android.chileaf.fitness.model.HistoryOfHeartRate;
import com.chileaf.fitness.R$layout;
import com.chileaf.fitness.R$string;
import com.chileaf.fitness.b.c0;
import com.chileaf.fitness.base.BaseActivity;
import com.chileaf.fitness.base.BaseViewModel;
import com.chileaf.fitness.device.wear.cl820.model.HistoryOfRespiratoryRate;
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
import kotlin.jvm.internal.i;

/* compiled from: CL820HistoryDetailActivity.kt */
public final class CL820HistoryDetailActivity extends BaseActivity<c0> {
    private LineChart E;
    private final kotlin.d F = g.a(CL820HistoryDetailActivity$mDateFormat$2.INSTANCE);
    private final kotlin.d G = g.a(new CL820HistoryDetailActivity$mManager$2(this));
    private final kotlin.d H = g.a(new CL820HistoryDetailActivity$type$2(this));
    private final kotlin.d I = g.a(new CL820HistoryDetailActivity$stamp$2(this));
    private HashMap J;

    /* compiled from: CL820HistoryDetailActivity.kt */
    public static final class a {
        private a() {
        }

        public /* synthetic */ a(f fVar) {
            this();
        }
    }

    /* compiled from: CL820HistoryDetailActivity.kt */
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

    /* compiled from: CL820HistoryDetailActivity.kt */
    static final class c implements com.chileaf.fitness.device.wear.cl820.callback.a {
        final /* synthetic */ CL820HistoryDetailActivity e;

        /* compiled from: CL820HistoryDetailActivity.kt */
        static final class a implements Runnable {
            final /* synthetic */ c e;

            /* renamed from: f  reason: collision with root package name */
            final /* synthetic */ List f1163f;

            a(c cVar, List list) {
                this.e = cVar;
                this.f1163f = list;
            }

            public final void run() {
                j.a.a.b("heartRates:%d %s", Integer.valueOf(this.f1163f.size()), this.f1163f.toString());
                this.e.e.a((List<? extends HistoryOfHeartRate>) this.f1163f);
                this.e.e.n();
            }
        }

        c(CL820HistoryDetailActivity cL820HistoryDetailActivity) {
            this.e = cL820HistoryDetailActivity;
        }

        public final void c(BluetoothDevice bluetoothDevice, List<? extends HistoryOfHeartRate> list) {
            i.b(list, "heartRates");
            this.e.runOnUiThread(new a(this, list));
        }
    }

    /* compiled from: CL820HistoryDetailActivity.kt */
    static final class d implements com.chileaf.fitness.device.wear.cl820.callback.c {
        final /* synthetic */ CL820HistoryDetailActivity e;

        /* compiled from: CL820HistoryDetailActivity.kt */
        static final class a implements Runnable {
            final /* synthetic */ d e;

            /* renamed from: f  reason: collision with root package name */
            final /* synthetic */ List f1164f;

            a(d dVar, List list) {
                this.e = dVar;
                this.f1164f = list;
            }

            public final void run() {
                j.a.a.b("respiratoryRates:%d %s", Integer.valueOf(this.f1164f.size()), this.f1164f.toString());
                this.e.e.b(this.f1164f);
                this.e.e.n();
            }
        }

        d(CL820HistoryDetailActivity cL820HistoryDetailActivity) {
            this.e = cL820HistoryDetailActivity;
        }

        public final void g(BluetoothDevice bluetoothDevice, List<? extends HistoryOfRespiratoryRate> list) {
            i.b(list, "respiratoryRates");
            this.e.runOnUiThread(new a(this, list));
        }
    }

    static {
        new a((f) null);
    }

    private final SimpleDateFormat q() {
        return (SimpleDateFormat) this.F.getValue();
    }

    private final CL820Manager r() {
        return (CL820Manager) this.G.getValue();
    }

    private final long s() {
        return ((Number) this.I.getValue()).longValue();
    }

    private final Integer t() {
        return (Integer) this.H.getValue();
    }

    private final void u() {
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
    public BaseViewModel o() {
        return null;
    }

    /* access modifiers changed from: protected */
    public int p() {
        return R$layout.activity_history_detail;
    }

    /* access modifiers changed from: private */
    public final void b(List<? extends HistoryOfRespiratoryRate> list) {
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        int size = list.size();
        for (int i2 = 0; i2 < size; i2++) {
            HistoryOfRespiratoryRate historyOfRespiratoryRate = (HistoryOfRespiratoryRate) list.get(i2);
            arrayList.add(new Entry((float) i2, (float) historyOfRespiratoryRate.heartRate));
            String format = q().format(new Date(historyOfRespiratoryRate.stamp));
            i.a((Object) format, "mDateFormat.format(Date(history.stamp))");
            arrayList2.add(format);
        }
        LineChart lineChart = this.E;
        if (lineChart != null) {
            lineChart.D();
            LineDataSet lineDataSet = new LineDataSet(arrayList, getString(R$string.text_rr));
            lineDataSet.b(8.0f);
            lineDataSet.d(1.5f);
            lineDataSet.f(-16776961);
            lineDataSet.i(-16776961);
            lineDataSet.j(-16776961);
            lineDataSet.g(-16776961);
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

    /* access modifiers changed from: protected */
    public void a(View view) {
        i.b(view, "root");
        LineChart lineChart = ((c0) m()).z;
        i.a((Object) lineChart, "mBinding.chartHistory");
        this.E = lineChart;
        u();
    }

    /* access modifiers changed from: protected */
    public void a(Bundle bundle) {
        a(30000);
        Integer t = t();
        if (t != null && t.intValue() == 3) {
            String string = getString(R$string.hr_history_detail);
            i.a((Object) string, "getString(R.string.hr_history_detail)");
            a(string);
            r().a((com.chileaf.fitness.device.wear.cl820.callback.a) new c(this));
            r().b(s());
            return;
        }
        Integer t2 = t();
        if (t2 != null && t2.intValue() == 5) {
            String string2 = getString(R$string.rr_history_detail);
            i.a((Object) string2, "getString(R.string.rr_history_detail)");
            a(string2);
            r().a((com.chileaf.fitness.device.wear.cl820.callback.c) new d(this));
            r().c(s());
        }
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
