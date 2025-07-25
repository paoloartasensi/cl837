package h.a.a.a.h;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.RectF;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.YAxis;
import com.jeremyliao.liveeventbus.BuildConfig;
import h.a.a.a.i.d;
import h.a.a.a.i.g;
import h.a.a.a.i.i;
import h.a.a.a.i.j;
import java.util.List;

/* compiled from: YAxisRendererHorizontalBarChart */
public class u extends t {
    protected Path r = new Path();
    protected Path s = new Path();
    protected float[] t = new float[4];

    public u(j jVar, YAxis yAxis, g gVar) {
        super(jVar, yAxis, gVar);
        this.f1682g.setTextAlign(Paint.Align.LEFT);
    }

    public void a(float f2, float f3, boolean z) {
        float f4;
        double d;
        if (this.a.f() > 10.0f && !this.a.v()) {
            d b = this.c.b(this.a.g(), this.a.i());
            d b2 = this.c.b(this.a.h(), this.a.i());
            if (!z) {
                f4 = (float) b.f1724g;
                d = b2.f1724g;
            } else {
                f4 = (float) b2.f1724g;
                d = b.f1724g;
            }
            d.a(b);
            d.a(b2);
            f2 = f4;
            f3 = (float) d;
        }
        a(f2, f3);
    }

    public void b(Canvas canvas) {
        float f2;
        float f3;
        float f4;
        if (this.f1717h.f() && this.f1717h.v()) {
            float[] c = c();
            this.e.setTypeface(this.f1717h.c());
            this.e.setTextSize(this.f1717h.b());
            this.e.setColor(this.f1717h.a());
            this.e.setTextAlign(Paint.Align.CENTER);
            float a = i.a(2.5f);
            float a2 = (float) i.a(this.e, "Q");
            YAxis.AxisDependency z = this.f1717h.z();
            YAxis.YAxisLabelPosition A = this.f1717h.A();
            if (z == YAxis.AxisDependency.LEFT) {
                if (A == YAxis.YAxisLabelPosition.OUTSIDE_CHART) {
                    f4 = this.a.i();
                } else {
                    f4 = this.a.i();
                }
                f2 = f4 - a;
            } else {
                if (A == YAxis.YAxisLabelPosition.OUTSIDE_CHART) {
                    f3 = this.a.e();
                } else {
                    f3 = this.a.e();
                }
                f2 = f3 + a2 + a;
            }
            a(canvas, f2, c, this.f1717h.e());
        }
    }

    public void c(Canvas canvas) {
        if (this.f1717h.f() && this.f1717h.s()) {
            this.f1681f.setColor(this.f1717h.g());
            this.f1681f.setStrokeWidth(this.f1717h.i());
            if (this.f1717h.z() == YAxis.AxisDependency.LEFT) {
                canvas.drawLine(this.a.g(), this.a.i(), this.a.h(), this.a.i(), this.f1681f);
                return;
            }
            canvas.drawLine(this.a.g(), this.a.e(), this.a.h(), this.a.e(), this.f1681f);
        }
    }

    public void e(Canvas canvas) {
        Canvas canvas2 = canvas;
        List<LimitLine> o = this.f1717h.o();
        if (o != null && o.size() > 0) {
            float[] fArr = this.t;
            float f2 = 0.0f;
            fArr[0] = 0.0f;
            char c = 1;
            fArr[1] = 0.0f;
            fArr[2] = 0.0f;
            fArr[3] = 0.0f;
            Path path = this.s;
            path.reset();
            int i2 = 0;
            while (i2 < o.size()) {
                LimitLine limitLine = o.get(i2);
                if (limitLine.f()) {
                    int save = canvas.save();
                    this.q.set(this.a.n());
                    this.q.inset(-limitLine.l(), f2);
                    canvas2.clipRect(this.q);
                    fArr[0] = limitLine.j();
                    fArr[2] = limitLine.j();
                    this.c.b(fArr);
                    fArr[c] = this.a.i();
                    fArr[3] = this.a.e();
                    path.moveTo(fArr[0], fArr[c]);
                    path.lineTo(fArr[2], fArr[3]);
                    this.f1682g.setStyle(Paint.Style.STROKE);
                    this.f1682g.setColor(limitLine.k());
                    this.f1682g.setPathEffect(limitLine.g());
                    this.f1682g.setStrokeWidth(limitLine.l());
                    canvas2.drawPath(path, this.f1682g);
                    path.reset();
                    String h2 = limitLine.h();
                    if (h2 != null && !h2.equals(BuildConfig.FLAVOR)) {
                        this.f1682g.setStyle(limitLine.m());
                        this.f1682g.setPathEffect((PathEffect) null);
                        this.f1682g.setColor(limitLine.a());
                        this.f1682g.setTypeface(limitLine.c());
                        this.f1682g.setStrokeWidth(0.5f);
                        this.f1682g.setTextSize(limitLine.b());
                        float l = limitLine.l() + limitLine.d();
                        float a = i.a(2.0f) + limitLine.e();
                        LimitLine.LimitLabelPosition i3 = limitLine.i();
                        if (i3 == LimitLine.LimitLabelPosition.RIGHT_TOP) {
                            this.f1682g.setTextAlign(Paint.Align.LEFT);
                            canvas2.drawText(h2, fArr[0] + l, this.a.i() + a + ((float) i.a(this.f1682g, h2)), this.f1682g);
                        } else if (i3 == LimitLine.LimitLabelPosition.RIGHT_BOTTOM) {
                            this.f1682g.setTextAlign(Paint.Align.LEFT);
                            canvas2.drawText(h2, fArr[0] + l, this.a.e() - a, this.f1682g);
                        } else if (i3 == LimitLine.LimitLabelPosition.LEFT_TOP) {
                            this.f1682g.setTextAlign(Paint.Align.RIGHT);
                            canvas2.drawText(h2, fArr[0] - l, this.a.i() + a + ((float) i.a(this.f1682g, h2)), this.f1682g);
                        } else {
                            this.f1682g.setTextAlign(Paint.Align.RIGHT);
                            canvas2.drawText(h2, fArr[0] - l, this.a.e() - a, this.f1682g);
                        }
                    }
                    canvas2.restoreToCount(save);
                }
                i2++;
                f2 = 0.0f;
                c = 1;
            }
        }
    }

    /* access modifiers changed from: protected */
    public float[] c() {
        int length = this.l.length;
        int i2 = this.f1717h.n;
        if (length != i2 * 2) {
            this.l = new float[(i2 * 2)];
        }
        float[] fArr = this.l;
        for (int i3 = 0; i3 < fArr.length; i3 += 2) {
            fArr[i3] = this.f1717h.l[i3 / 2];
        }
        this.c.b(fArr);
        return fArr;
    }

    /* access modifiers changed from: protected */
    public void a(Canvas canvas, float f2, float[] fArr, float f3) {
        this.e.setTypeface(this.f1717h.c());
        this.e.setTextSize(this.f1717h.b());
        this.e.setColor(this.f1717h.a());
        int i2 = this.f1717h.I() ? this.f1717h.n : this.f1717h.n - 1;
        for (int i3 = !this.f1717h.H(); i3 < i2; i3++) {
            canvas.drawText(this.f1717h.a(i3), fArr[i3 * 2], f2 - f3, this.e);
        }
    }

    public RectF b() {
        this.k.set(this.a.n());
        this.k.inset(-this.b.m(), 0.0f);
        return this.k;
    }

    /* access modifiers changed from: protected */
    public Path a(Path path, int i2, float[] fArr) {
        path.moveTo(fArr[i2], this.a.i());
        path.lineTo(fArr[i2], this.a.e());
        return path;
    }

    /* access modifiers changed from: protected */
    public void a(Canvas canvas) {
        int save = canvas.save();
        this.n.set(this.a.n());
        this.n.inset(-this.f1717h.G(), 0.0f);
        canvas.clipRect(this.q);
        d a = this.c.a(0.0f, 0.0f);
        this.f1718i.setColor(this.f1717h.F());
        this.f1718i.setStrokeWidth(this.f1717h.G());
        Path path = this.r;
        path.reset();
        path.moveTo(((float) a.f1724g) - 1.0f, this.a.i());
        path.lineTo(((float) a.f1724g) - 1.0f, this.a.e());
        canvas.drawPath(path, this.f1718i);
        canvas.restoreToCount(save);
    }
}
