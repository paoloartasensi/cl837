package h.a.a.a.h;

import android.graphics.Canvas;
import android.graphics.Path;
import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.components.a;
import com.github.mikephil.charting.data.n;
import h.a.a.a.i.e;
import h.a.a.a.i.g;
import h.a.a.a.i.i;
import h.a.a.a.i.j;
import java.util.List;

/* compiled from: YAxisRendererRadarChart */
public class v extends t {
    private RadarChart r;
    private Path s = new Path();

    public v(j jVar, YAxis yAxis, RadarChart radarChart) {
        super(jVar, yAxis, (g) null);
        this.r = radarChart;
    }

    /* access modifiers changed from: protected */
    public void a(float f2, float f3) {
        double d;
        double d2;
        boolean z;
        float f4 = f2;
        float f5 = f3;
        int n = this.b.n();
        double abs = (double) Math.abs(f5 - f4);
        if (n == 0 || abs <= 0.0d || Double.isInfinite(abs)) {
            a aVar = this.b;
            aVar.l = new float[0];
            aVar.m = new float[0];
            aVar.n = 0;
            return;
        }
        double d3 = (double) n;
        Double.isNaN(abs);
        Double.isNaN(d3);
        double b = (double) i.b(abs / d3);
        if (this.b.y() && b < ((double) this.b.j())) {
            b = (double) this.b.j();
        }
        double b2 = (double) i.b(Math.pow(10.0d, (double) ((int) Math.log10(b))));
        Double.isNaN(b2);
        if (((int) (b / b2)) > 5) {
            Double.isNaN(b2);
            b = Math.floor(b2 * 10.0d);
        }
        boolean r2 = this.b.r();
        if (this.b.x()) {
            float f6 = ((float) abs) / ((float) (n - 1));
            a aVar2 = this.b;
            aVar2.n = n;
            if (aVar2.l.length < n) {
                aVar2.l = new float[n];
            }
            for (int i2 = 0; i2 < n; i2++) {
                this.b.l[i2] = f4;
                f4 += f6;
            }
        } else {
            if (b == 0.0d) {
                d = 0.0d;
            } else {
                double d4 = (double) f4;
                Double.isNaN(d4);
                d = Math.ceil(d4 / b) * b;
            }
            if (r2) {
                d -= b;
            }
            if (b == 0.0d) {
                d2 = 0.0d;
            } else {
                double d5 = (double) f5;
                Double.isNaN(d5);
                d2 = i.a(Math.floor(d5 / b) * b);
            }
            if (b != 0.0d) {
                z = r2;
                for (double d6 = d; d6 <= d2; d6 += b) {
                    z++;
                }
            } else {
                z = r2;
            }
            int i3 = ((int) z) + 1;
            a aVar3 = this.b;
            aVar3.n = i3;
            if (aVar3.l.length < i3) {
                aVar3.l = new float[i3];
            }
            for (int i4 = 0; i4 < i3; i4++) {
                if (d == 0.0d) {
                    d = 0.0d;
                }
                this.b.l[i4] = (float) d;
                d += b;
            }
            n = i3;
        }
        if (b < 1.0d) {
            this.b.o = (int) Math.ceil(-Math.log10(b));
        } else {
            this.b.o = 0;
        }
        if (r2) {
            a aVar4 = this.b;
            if (aVar4.m.length < n) {
                aVar4.m = new float[n];
            }
            float[] fArr = this.b.l;
            float f7 = (fArr[1] - fArr[0]) / 2.0f;
            for (int i5 = 0; i5 < n; i5++) {
                a aVar5 = this.b;
                aVar5.m[i5] = aVar5.l[i5] + f7;
            }
        }
        a aVar6 = this.b;
        float[] fArr2 = aVar6.l;
        float f8 = fArr2[0];
        aVar6.H = f8;
        float f9 = fArr2[n - 1];
        aVar6.G = f9;
        aVar6.I = Math.abs(f9 - f8);
    }

    public void b(Canvas canvas) {
        if (this.f1717h.f() && this.f1717h.v()) {
            this.e.setTypeface(this.f1717h.c());
            this.e.setTextSize(this.f1717h.b());
            this.e.setColor(this.f1717h.a());
            e centerOffsets = this.r.getCenterOffsets();
            e a = e.a(0.0f, 0.0f);
            float factor = this.r.getFactor();
            int i2 = this.f1717h.I() ? this.f1717h.n : this.f1717h.n - 1;
            for (int i3 = !this.f1717h.H(); i3 < i2; i3++) {
                YAxis yAxis = this.f1717h;
                i.a(centerOffsets, (yAxis.l[i3] - yAxis.H) * factor, this.r.getRotationAngle(), a);
                canvas.drawText(this.f1717h.a(i3), a.f1727g + 10.0f, a.f1728h, this.e);
            }
            e.b(centerOffsets);
            e.b(a);
        }
    }

    public void e(Canvas canvas) {
        List<LimitLine> o = this.f1717h.o();
        if (o != null) {
            float sliceAngle = this.r.getSliceAngle();
            float factor = this.r.getFactor();
            e centerOffsets = this.r.getCenterOffsets();
            e a = e.a(0.0f, 0.0f);
            for (int i2 = 0; i2 < o.size(); i2++) {
                LimitLine limitLine = o.get(i2);
                if (limitLine.f()) {
                    this.f1682g.setColor(limitLine.k());
                    this.f1682g.setPathEffect(limitLine.g());
                    this.f1682g.setStrokeWidth(limitLine.l());
                    float j2 = (limitLine.j() - this.r.getYChartMin()) * factor;
                    Path path = this.s;
                    path.reset();
                    for (int i3 = 0; i3 < ((h.a.a.a.e.b.j) ((n) this.r.getData()).e()).X(); i3++) {
                        i.a(centerOffsets, j2, (((float) i3) * sliceAngle) + this.r.getRotationAngle(), a);
                        if (i3 == 0) {
                            path.moveTo(a.f1727g, a.f1728h);
                        } else {
                            path.lineTo(a.f1727g, a.f1728h);
                        }
                    }
                    path.close();
                    canvas.drawPath(path, this.f1682g);
                }
            }
            e.b(centerOffsets);
            e.b(a);
        }
    }
}
