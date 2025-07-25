package h.a.a.a.d;

import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.n;
import h.a.a.a.i.e;
import java.util.List;

/* compiled from: RadarHighlighter */
public class i extends h<RadarChart> {
    public i(RadarChart radarChart) {
        super(radarChart);
    }

    /* access modifiers changed from: protected */
    public d a(int i2, float f2, float f3) {
        List<d> a = a(i2);
        float c = ((RadarChart) this.a).c(f2, f3) / ((RadarChart) this.a).getFactor();
        d dVar = null;
        float f4 = Float.MAX_VALUE;
        for (int i3 = 0; i3 < a.size(); i3++) {
            d dVar2 = a.get(i3);
            float abs = Math.abs(dVar2.i() - c);
            if (abs < f4) {
                dVar = dVar2;
                f4 = abs;
            }
        }
        return dVar;
    }

    /* access modifiers changed from: protected */
    public List<d> a(int i2) {
        int i3 = i2;
        this.b.clear();
        float a = ((RadarChart) this.a).getAnimator().a();
        float b = ((RadarChart) this.a).getAnimator().b();
        float sliceAngle = ((RadarChart) this.a).getSliceAngle();
        float factor = ((RadarChart) this.a).getFactor();
        e a2 = e.a(0.0f, 0.0f);
        int i4 = 0;
        while (i4 < ((n) ((RadarChart) this.a).getData()).b()) {
            h.a.a.a.e.b.e a3 = ((n) ((RadarChart) this.a).getData()).a(i4);
            Entry c = a3.c(i3);
            float f2 = (float) i3;
            h.a.a.a.i.i.a(((RadarChart) this.a).getCenterOffsets(), (c.c() - ((RadarChart) this.a).getYChartMin()) * factor * b, (sliceAngle * f2 * a) + ((RadarChart) this.a).getRotationAngle(), a2);
            List<d> list = this.b;
            d dVar = r8;
            d dVar2 = new d(f2, c.c(), a2.f1727g, a2.f1728h, i4, a3.S());
            list.add(dVar);
            i4++;
            i3 = i2;
        }
        return this.b;
    }
}
