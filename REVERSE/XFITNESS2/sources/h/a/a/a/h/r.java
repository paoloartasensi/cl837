package h.a.a.a.h;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.RectF;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.a;
import com.jeremyliao.liveeventbus.BuildConfig;
import h.a.a.a.i.b;
import h.a.a.a.i.d;
import h.a.a.a.i.e;
import h.a.a.a.i.g;
import h.a.a.a.i.i;
import h.a.a.a.i.j;
import java.util.List;

/* compiled from: XAxisRendererHorizontalBarChart */
public class r extends q {
    protected Path p = new Path();

    public r(j jVar, XAxis xAxis, g gVar, BarChart barChart) {
        super(jVar, xAxis, gVar);
    }

    public void a(float f2, float f3, boolean z) {
        float f4;
        double d;
        if (this.a.j() > 10.0f && !this.a.w()) {
            d b = this.c.b(this.a.g(), this.a.e());
            d b2 = this.c.b(this.a.g(), this.a.i());
            if (z) {
                f4 = (float) b2.f1725h;
                d = b.f1725h;
            } else {
                f4 = (float) b.f1725h;
                d = b2.f1725h;
            }
            d.a(b);
            d.a(b2);
            f2 = f4;
            f3 = (float) d;
        }
        a(f2, f3);
    }

    /* access modifiers changed from: protected */
    public void b() {
        this.e.setTypeface(this.f1714h.c());
        this.e.setTextSize(this.f1714h.b());
        b b = i.b(this.e, this.f1714h.p());
        float f2 = b.f1722h;
        b a = i.a(b.f1721g, f2, this.f1714h.z());
        this.f1714h.J = Math.round((float) ((int) (b.f1721g + (this.f1714h.d() * 3.5f))));
        this.f1714h.K = Math.round(f2);
        XAxis xAxis = this.f1714h;
        xAxis.L = (int) (a.f1721g + (xAxis.d() * 3.5f));
        this.f1714h.M = Math.round(a.f1722h);
        b.a(a);
    }

    public RectF c() {
        this.k.set(this.a.n());
        this.k.inset(0.0f, -this.b.m());
        return this.k;
    }

    public void d(Canvas canvas) {
        List<LimitLine> o = this.f1714h.o();
        if (o != null && o.size() > 0) {
            float[] fArr = this.l;
            fArr[0] = 0.0f;
            fArr[1] = 0.0f;
            Path path = this.p;
            path.reset();
            for (int i2 = 0; i2 < o.size(); i2++) {
                LimitLine limitLine = o.get(i2);
                if (limitLine.f()) {
                    int save = canvas.save();
                    this.m.set(this.a.n());
                    this.m.inset(0.0f, -limitLine.l());
                    canvas.clipRect(this.m);
                    this.f1682g.setStyle(Paint.Style.STROKE);
                    this.f1682g.setColor(limitLine.k());
                    this.f1682g.setStrokeWidth(limitLine.l());
                    this.f1682g.setPathEffect(limitLine.g());
                    fArr[1] = limitLine.j();
                    this.c.b(fArr);
                    path.moveTo(this.a.g(), fArr[1]);
                    path.lineTo(this.a.h(), fArr[1]);
                    canvas.drawPath(path, this.f1682g);
                    path.reset();
                    String h2 = limitLine.h();
                    if (h2 != null && !h2.equals(BuildConfig.FLAVOR)) {
                        this.f1682g.setStyle(limitLine.m());
                        this.f1682g.setPathEffect((PathEffect) null);
                        this.f1682g.setColor(limitLine.a());
                        this.f1682g.setStrokeWidth(0.5f);
                        this.f1682g.setTextSize(limitLine.b());
                        float a = (float) i.a(this.f1682g, h2);
                        float a2 = i.a(4.0f) + limitLine.d();
                        float l = limitLine.l() + a + limitLine.e();
                        LimitLine.LimitLabelPosition i3 = limitLine.i();
                        if (i3 == LimitLine.LimitLabelPosition.RIGHT_TOP) {
                            this.f1682g.setTextAlign(Paint.Align.RIGHT);
                            canvas.drawText(h2, this.a.h() - a2, (fArr[1] - l) + a, this.f1682g);
                        } else if (i3 == LimitLine.LimitLabelPosition.RIGHT_BOTTOM) {
                            this.f1682g.setTextAlign(Paint.Align.RIGHT);
                            canvas.drawText(h2, this.a.h() - a2, fArr[1] + l, this.f1682g);
                        } else if (i3 == LimitLine.LimitLabelPosition.LEFT_TOP) {
                            this.f1682g.setTextAlign(Paint.Align.LEFT);
                            canvas.drawText(h2, this.a.g() + a2, (fArr[1] - l) + a, this.f1682g);
                        } else {
                            this.f1682g.setTextAlign(Paint.Align.LEFT);
                            canvas.drawText(h2, this.a.y() + a2, fArr[1] + l, this.f1682g);
                        }
                    }
                    canvas.restoreToCount(save);
                }
            }
        }
    }

    public void a(Canvas canvas) {
        if (this.f1714h.f() && this.f1714h.v()) {
            float d = this.f1714h.d();
            this.e.setTypeface(this.f1714h.c());
            this.e.setTextSize(this.f1714h.b());
            this.e.setColor(this.f1714h.a());
            e a = e.a(0.0f, 0.0f);
            if (this.f1714h.A() == XAxis.XAxisPosition.TOP) {
                a.f1727g = 0.0f;
                a.f1728h = 0.5f;
                a(canvas, this.a.h() + d, a);
            } else if (this.f1714h.A() == XAxis.XAxisPosition.TOP_INSIDE) {
                a.f1727g = 1.0f;
                a.f1728h = 0.5f;
                a(canvas, this.a.h() - d, a);
            } else if (this.f1714h.A() == XAxis.XAxisPosition.BOTTOM) {
                a.f1727g = 1.0f;
                a.f1728h = 0.5f;
                a(canvas, this.a.g() - d, a);
            } else if (this.f1714h.A() == XAxis.XAxisPosition.BOTTOM_INSIDE) {
                a.f1727g = 1.0f;
                a.f1728h = 0.5f;
                a(canvas, this.a.g() + d, a);
            } else {
                a.f1727g = 0.0f;
                a.f1728h = 0.5f;
                a(canvas, this.a.h() + d, a);
                a.f1727g = 1.0f;
                a.f1728h = 0.5f;
                a(canvas, this.a.g() - d, a);
            }
            e.b(a);
        }
    }

    public void b(Canvas canvas) {
        if (this.f1714h.s() && this.f1714h.f()) {
            this.f1681f.setColor(this.f1714h.g());
            this.f1681f.setStrokeWidth(this.f1714h.i());
            if (this.f1714h.A() == XAxis.XAxisPosition.TOP || this.f1714h.A() == XAxis.XAxisPosition.TOP_INSIDE || this.f1714h.A() == XAxis.XAxisPosition.BOTH_SIDED) {
                canvas.drawLine(this.a.h(), this.a.i(), this.a.h(), this.a.e(), this.f1681f);
            }
            if (this.f1714h.A() == XAxis.XAxisPosition.BOTTOM || this.f1714h.A() == XAxis.XAxisPosition.BOTTOM_INSIDE || this.f1714h.A() == XAxis.XAxisPosition.BOTH_SIDED) {
                canvas.drawLine(this.a.g(), this.a.i(), this.a.g(), this.a.e(), this.f1681f);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void a(Canvas canvas, float f2, e eVar) {
        float z = this.f1714h.z();
        boolean r = this.f1714h.r();
        int i2 = this.f1714h.n * 2;
        float[] fArr = new float[i2];
        for (int i3 = 0; i3 < i2; i3 += 2) {
            if (r) {
                fArr[i3 + 1] = this.f1714h.m[i3 / 2];
            } else {
                fArr[i3 + 1] = this.f1714h.l[i3 / 2];
            }
        }
        this.c.b(fArr);
        for (int i4 = 0; i4 < i2; i4 += 2) {
            float f3 = fArr[i4 + 1];
            if (this.a.f(f3)) {
                h.a.a.a.c.e q = this.f1714h.q();
                XAxis xAxis = this.f1714h;
                a(canvas, q.a(xAxis.l[i4 / 2], (a) xAxis), f2, f3, eVar, z);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void a(Canvas canvas, float f2, float f3, Path path) {
        path.moveTo(this.a.h(), f3);
        path.lineTo(this.a.g(), f3);
        canvas.drawPath(path, this.d);
        path.reset();
    }
}
