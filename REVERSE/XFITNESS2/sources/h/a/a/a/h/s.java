package h.a.a.a.h;

import android.graphics.Canvas;
import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.a;
import com.github.mikephil.charting.data.n;
import h.a.a.a.i.e;
import h.a.a.a.i.g;
import h.a.a.a.i.i;
import h.a.a.a.i.j;

/* compiled from: XAxisRendererRadarChart */
public class s extends q {
    private RadarChart p;

    public s(j jVar, XAxis xAxis, RadarChart radarChart) {
        super(jVar, xAxis, (g) null);
        this.p = radarChart;
    }

    public void a(Canvas canvas) {
        if (this.f1714h.f() && this.f1714h.v()) {
            float z = this.f1714h.z();
            e a = e.a(0.5f, 0.25f);
            this.e.setTypeface(this.f1714h.c());
            this.e.setTextSize(this.f1714h.b());
            this.e.setColor(this.f1714h.a());
            float sliceAngle = this.p.getSliceAngle();
            float factor = this.p.getFactor();
            e centerOffsets = this.p.getCenterOffsets();
            e a2 = e.a(0.0f, 0.0f);
            for (int i2 = 0; i2 < ((h.a.a.a.e.b.j) ((n) this.p.getData()).e()).X(); i2++) {
                float f2 = (float) i2;
                String a3 = this.f1714h.q().a(f2, (a) this.f1714h);
                i.a(centerOffsets, (this.p.getYRange() * factor) + (((float) this.f1714h.L) / 2.0f), ((f2 * sliceAngle) + this.p.getRotationAngle()) % 360.0f, a2);
                a(canvas, a3, a2.f1727g, a2.f1728h - (((float) this.f1714h.M) / 2.0f), a, z);
            }
            e.b(centerOffsets);
            e.b(a2);
            e.b(a);
        }
    }

    public void d(Canvas canvas) {
    }
}
