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

/* compiled from: YAxisRenderer */
public class t extends a {

    /* renamed from: h  reason: collision with root package name */
    protected YAxis f1717h;

    /* renamed from: i  reason: collision with root package name */
    protected Paint f1718i;

    /* renamed from: j  reason: collision with root package name */
    protected Path f1719j = new Path();
    protected RectF k = new RectF();
    protected float[] l = new float[2];
    protected Path m = new Path();
    protected RectF n = new RectF();
    protected Path o = new Path();
    protected float[] p = new float[2];
    protected RectF q = new RectF();

    public t(j jVar, YAxis yAxis, g gVar) {
        super(jVar, gVar, yAxis);
        this.f1717h = yAxis;
        if (this.a != null) {
            this.e.setColor(-16777216);
            this.e.setTextSize(i.a(10.0f));
            Paint paint = new Paint(1);
            this.f1718i = paint;
            paint.setColor(-7829368);
            this.f1718i.setStrokeWidth(1.0f);
            this.f1718i.setStyle(Paint.Style.STROKE);
        }
    }

    /* access modifiers changed from: protected */
    public void a(Canvas canvas, float f2, float[] fArr, float f3) {
        int i2 = this.f1717h.I() ? this.f1717h.n : this.f1717h.n - 1;
        for (int i3 = !this.f1717h.H(); i3 < i2; i3++) {
            canvas.drawText(this.f1717h.a(i3), f2, fArr[(i3 * 2) + 1] + f3, this.e);
        }
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
            float d = this.f1717h.d();
            float a = (((float) i.a(this.e, "A")) / 2.5f) + this.f1717h.e();
            YAxis.AxisDependency z = this.f1717h.z();
            YAxis.YAxisLabelPosition A = this.f1717h.A();
            if (z == YAxis.AxisDependency.LEFT) {
                if (A == YAxis.YAxisLabelPosition.OUTSIDE_CHART) {
                    this.e.setTextAlign(Paint.Align.RIGHT);
                    f3 = this.a.y();
                    f2 = f3 - d;
                    a(canvas, f2, c, a);
                }
                this.e.setTextAlign(Paint.Align.LEFT);
                f4 = this.a.y();
            } else if (A == YAxis.YAxisLabelPosition.OUTSIDE_CHART) {
                this.e.setTextAlign(Paint.Align.LEFT);
                f4 = this.a.h();
            } else {
                this.e.setTextAlign(Paint.Align.RIGHT);
                f3 = this.a.h();
                f2 = f3 - d;
                a(canvas, f2, c, a);
            }
            f2 = f4 + d;
            a(canvas, f2, c, a);
        }
    }

    public void c(Canvas canvas) {
        if (this.f1717h.f() && this.f1717h.s()) {
            this.f1681f.setColor(this.f1717h.g());
            this.f1681f.setStrokeWidth(this.f1717h.i());
            if (this.f1717h.z() == YAxis.AxisDependency.LEFT) {
                canvas.drawLine(this.a.g(), this.a.i(), this.a.g(), this.a.e(), this.f1681f);
                return;
            }
            canvas.drawLine(this.a.h(), this.a.i(), this.a.h(), this.a.e(), this.f1681f);
        }
    }

    public void d(Canvas canvas) {
        if (this.f1717h.f()) {
            if (this.f1717h.u()) {
                int save = canvas.save();
                canvas.clipRect(b());
                float[] c = c();
                this.d.setColor(this.f1717h.k());
                this.d.setStrokeWidth(this.f1717h.m());
                this.d.setPathEffect(this.f1717h.l());
                Path path = this.f1719j;
                path.reset();
                for (int i2 = 0; i2 < c.length; i2 += 2) {
                    canvas.drawPath(a(path, i2, c), this.d);
                    path.reset();
                }
                canvas.restoreToCount(save);
            }
            if (this.f1717h.J()) {
                a(canvas);
            }
        }
    }

    public void e(Canvas canvas) {
        List<LimitLine> o2 = this.f1717h.o();
        if (o2 != null && o2.size() > 0) {
            float[] fArr = this.p;
            fArr[0] = 0.0f;
            fArr[1] = 0.0f;
            Path path = this.o;
            path.reset();
            for (int i2 = 0; i2 < o2.size(); i2++) {
                LimitLine limitLine = o2.get(i2);
                if (limitLine.f()) {
                    int save = canvas.save();
                    this.q.set(this.a.n());
                    this.q.inset(0.0f, -limitLine.l());
                    canvas.clipRect(this.q);
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
                        this.f1682g.setTypeface(limitLine.c());
                        this.f1682g.setStrokeWidth(0.5f);
                        this.f1682g.setTextSize(limitLine.b());
                        float a = (float) i.a(this.f1682g, h2);
                        float a2 = i.a(4.0f) + limitLine.d();
                        float l2 = limitLine.l() + a + limitLine.e();
                        LimitLine.LimitLabelPosition i3 = limitLine.i();
                        if (i3 == LimitLine.LimitLabelPosition.RIGHT_TOP) {
                            this.f1682g.setTextAlign(Paint.Align.RIGHT);
                            canvas.drawText(h2, this.a.h() - a2, (fArr[1] - l2) + a, this.f1682g);
                        } else if (i3 == LimitLine.LimitLabelPosition.RIGHT_BOTTOM) {
                            this.f1682g.setTextAlign(Paint.Align.RIGHT);
                            canvas.drawText(h2, this.a.h() - a2, fArr[1] + l2, this.f1682g);
                        } else if (i3 == LimitLine.LimitLabelPosition.LEFT_TOP) {
                            this.f1682g.setTextAlign(Paint.Align.LEFT);
                            canvas.drawText(h2, this.a.g() + a2, (fArr[1] - l2) + a, this.f1682g);
                        } else {
                            this.f1682g.setTextAlign(Paint.Align.LEFT);
                            canvas.drawText(h2, this.a.y() + a2, fArr[1] + l2, this.f1682g);
                        }
                    }
                    canvas.restoreToCount(save);
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public Path a(Path path, int i2, float[] fArr) {
        int i3 = i2 + 1;
        path.moveTo(this.a.y(), fArr[i3]);
        path.lineTo(this.a.h(), fArr[i3]);
        return path;
    }

    /* access modifiers changed from: protected */
    public void a(Canvas canvas) {
        int save = canvas.save();
        this.n.set(this.a.n());
        this.n.inset(0.0f, -this.f1717h.G());
        canvas.clipRect(this.n);
        d a = this.c.a(0.0f, 0.0f);
        this.f1718i.setColor(this.f1717h.F());
        this.f1718i.setStrokeWidth(this.f1717h.G());
        Path path = this.m;
        path.reset();
        path.moveTo(this.a.g(), (float) a.f1725h);
        path.lineTo(this.a.h(), (float) a.f1725h);
        canvas.drawPath(path, this.f1718i);
        canvas.restoreToCount(save);
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
            fArr[i3 + 1] = this.f1717h.l[i3 / 2];
        }
        this.c.b(fArr);
        return fArr;
    }

    public RectF b() {
        this.k.set(this.a.n());
        this.k.inset(0.0f, -this.b.m());
        return this.k;
    }
}
