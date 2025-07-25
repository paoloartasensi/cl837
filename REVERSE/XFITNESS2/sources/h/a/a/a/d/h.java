package h.a.a.a.d;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.charts.PieRadarChartBase;
import java.util.ArrayList;
import java.util.List;

/* compiled from: PieRadarHighlighter */
public abstract class h<T extends PieRadarChartBase> implements f {
    protected T a;
    protected List<d> b = new ArrayList();

    public h(T t) {
        this.a = t;
    }

    public d a(float f2, float f3) {
        if (this.a.c(f2, f3) > this.a.getRadius()) {
            return null;
        }
        float d = this.a.d(f2, f3);
        T t = this.a;
        if (t instanceof PieChart) {
            d /= t.getAnimator().b();
        }
        int a2 = this.a.a(d);
        if (a2 < 0 || a2 >= this.a.getData().e().X()) {
            return null;
        }
        return a(a2, f2, f3);
    }

    /* access modifiers changed from: protected */
    public abstract d a(int i2, float f2, float f3);
}
