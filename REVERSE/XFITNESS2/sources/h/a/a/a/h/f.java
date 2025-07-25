package h.a.a.a.h;

import android.graphics.Canvas;
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.data.i;
import h.a.a.a.d.d;
import h.a.a.a.i.j;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/* compiled from: CombinedChartRenderer */
public class f extends g {

    /* renamed from: f  reason: collision with root package name */
    protected List<g> f1695f = new ArrayList(5);

    /* renamed from: g  reason: collision with root package name */
    protected WeakReference<Chart> f1696g;

    /* renamed from: h  reason: collision with root package name */
    protected List<d> f1697h = new ArrayList();

    /* compiled from: CombinedChartRenderer */
    static /* synthetic */ class a {
        static final /* synthetic */ int[] a;

        /* JADX WARNING: Can't wrap try/catch for region: R(12:0|1|2|3|4|5|6|7|8|9|10|12) */
        /* JADX WARNING: Code restructure failed: missing block: B:13:?, code lost:
            return;
         */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0012 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x001d */
        /* JADX WARNING: Missing exception handler attribute for start block: B:7:0x0028 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:9:0x0033 */
        static {
            /*
                com.github.mikephil.charting.charts.CombinedChart$DrawOrder[] r0 = com.github.mikephil.charting.charts.CombinedChart.DrawOrder.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                a = r0
                com.github.mikephil.charting.charts.CombinedChart$DrawOrder r1 = com.github.mikephil.charting.charts.CombinedChart.DrawOrder.BAR     // Catch:{ NoSuchFieldError -> 0x0012 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0012 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0012 }
            L_0x0012:
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x001d }
                com.github.mikephil.charting.charts.CombinedChart$DrawOrder r1 = com.github.mikephil.charting.charts.CombinedChart.DrawOrder.BUBBLE     // Catch:{ NoSuchFieldError -> 0x001d }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001d }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001d }
            L_0x001d:
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x0028 }
                com.github.mikephil.charting.charts.CombinedChart$DrawOrder r1 = com.github.mikephil.charting.charts.CombinedChart.DrawOrder.LINE     // Catch:{ NoSuchFieldError -> 0x0028 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0028 }
                r2 = 3
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0028 }
            L_0x0028:
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x0033 }
                com.github.mikephil.charting.charts.CombinedChart$DrawOrder r1 = com.github.mikephil.charting.charts.CombinedChart.DrawOrder.CANDLE     // Catch:{ NoSuchFieldError -> 0x0033 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0033 }
                r2 = 4
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0033 }
            L_0x0033:
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x003e }
                com.github.mikephil.charting.charts.CombinedChart$DrawOrder r1 = com.github.mikephil.charting.charts.CombinedChart.DrawOrder.SCATTER     // Catch:{ NoSuchFieldError -> 0x003e }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x003e }
                r2 = 5
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x003e }
            L_0x003e:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: h.a.a.a.h.f.a.<clinit>():void");
        }
    }

    public f(CombinedChart combinedChart, h.a.a.a.a.a aVar, j jVar) {
        super(aVar, jVar);
        this.f1696g = new WeakReference<>(combinedChart);
        b();
    }

    public void a() {
        for (g a2 : this.f1695f) {
            a2.a();
        }
    }

    public void b() {
        this.f1695f.clear();
        CombinedChart combinedChart = (CombinedChart) this.f1696g.get();
        if (combinedChart != null) {
            for (CombinedChart.DrawOrder ordinal : combinedChart.getDrawOrder()) {
                int i2 = a.a[ordinal.ordinal()];
                if (i2 != 1) {
                    if (i2 != 2) {
                        if (i2 != 3) {
                            if (i2 != 4) {
                                if (i2 == 5 && combinedChart.getScatterData() != null) {
                                    this.f1695f.add(new p(combinedChart, this.b, this.a));
                                }
                            } else if (combinedChart.getCandleData() != null) {
                                this.f1695f.add(new e(combinedChart, this.b, this.a));
                            }
                        } else if (combinedChart.getLineData() != null) {
                            this.f1695f.add(new j(combinedChart, this.b, this.a));
                        }
                    } else if (combinedChart.getBubbleData() != null) {
                        this.f1695f.add(new d(combinedChart, this.b, this.a));
                    }
                } else if (combinedChart.getBarData() != null) {
                    this.f1695f.add(new b(combinedChart, this.b, this.a));
                }
            }
        }
    }

    public void c(Canvas canvas) {
        for (g c : this.f1695f) {
            c.c(canvas);
        }
    }

    public void a(Canvas canvas) {
        for (g a2 : this.f1695f) {
            a2.a(canvas);
        }
    }

    public void a(Canvas canvas, d[] dVarArr) {
        int i2;
        Chart chart = (Chart) this.f1696g.get();
        if (chart != null) {
            for (g next : this.f1695f) {
                Object obj = null;
                if (next instanceof b) {
                    obj = ((b) next).f1683g.getBarData();
                } else if (next instanceof j) {
                    obj = ((j) next).f1700h.getLineData();
                } else if (next instanceof e) {
                    obj = ((e) next).f1692h.getCandleData();
                } else if (next instanceof p) {
                    obj = ((p) next).f1712h.getScatterData();
                } else if (next instanceof d) {
                    obj = ((d) next).f1688g.getBubbleData();
                }
                if (obj == null) {
                    i2 = -1;
                } else {
                    i2 = ((i) chart.getData()).k().indexOf(obj);
                }
                this.f1697h.clear();
                for (d dVar : dVarArr) {
                    if (dVar.b() == i2 || dVar.b() == -1) {
                        this.f1697h.add(dVar);
                    }
                }
                List<d> list = this.f1697h;
                next.a(canvas, (d[]) list.toArray(new d[list.size()]));
            }
        }
    }

    public void b(Canvas canvas) {
        for (g b : this.f1695f) {
            b.b(canvas);
        }
    }
}
