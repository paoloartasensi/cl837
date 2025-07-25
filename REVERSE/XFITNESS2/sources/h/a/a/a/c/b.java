package h.a.a.a.c;

import com.github.mikephil.charting.data.j;
import h.a.a.a.e.a.g;
import h.a.a.a.e.b.f;

/* compiled from: DefaultFillFormatter */
public class b implements d {
    public float a(f fVar, g gVar) {
        float yChartMax = gVar.getYChartMax();
        float yChartMin = gVar.getYChartMin();
        j lineData = gVar.getLineData();
        if (fVar.g0() > 0.0f && fVar.A() < 0.0f) {
            return 0.0f;
        }
        if (lineData.h() > 0.0f) {
            yChartMax = 0.0f;
        }
        if (lineData.i() < 0.0f) {
            yChartMin = 0.0f;
        }
        return fVar.A() >= 0.0f ? yChartMin : yChartMax;
    }
}
