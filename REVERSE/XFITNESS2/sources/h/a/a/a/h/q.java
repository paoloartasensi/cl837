package h.a.a.a.h;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.RectF;
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

/* compiled from: XAxisRenderer */
public class q extends a {

    /* renamed from: h  reason: collision with root package name */
    protected XAxis f1714h;

    /* renamed from: i  reason: collision with root package name */
    protected Path f1715i = new Path();

    /* renamed from: j  reason: collision with root package name */
    protected float[] f1716j = new float[2];
    protected RectF k = new RectF();
    protected float[] l = new float[2];
    protected RectF m = new RectF();
    float[] n = new float[4];
    private Path o = new Path();

    public q(j jVar, XAxis xAxis, g gVar) {
        super(jVar, gVar, xAxis);
        this.f1714h = xAxis;
        this.e.setColor(-16777216);
        this.e.setTextAlign(Paint.Align.CENTER);
        this.e.setTextSize(i.a(10.0f));
    }

    public void a(float f2, float f3, boolean z) {
        float f4;
        double d;
        if (this.a.j() > 10.0f && !this.a.v()) {
            d b = this.c.b(this.a.g(), this.a.i());
            d b2 = this.c.b(this.a.h(), this.a.i());
            if (z) {
                f4 = (float) b2.f1724g;
                d = b.f1724g;
            } else {
                f4 = (float) b.f1724g;
                d = b2.f1724g;
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
        String p = this.f1714h.p();
        this.e.setTypeface(this.f1714h.c());
        this.e.setTextSize(this.f1714h.b());
        b b = i.b(this.e, p);
        float f2 = b.f1721g;
        float a = (float) i.a(this.e, "Q");
        b a2 = i.a(f2, a, this.f1714h.z());
        this.f1714h.J = Math.round(f2);
        this.f1714h.K = Math.round(a);
        this.f1714h.L = Math.round(a2.f1721g);
        this.f1714h.M = Math.round(a2.f1722h);
        b.a(a2);
        b.a(b);
    }

    public void c(Canvas canvas) {
        if (this.f1714h.u() && this.f1714h.f()) {
            int save = canvas.save();
            canvas.clipRect(c());
            if (this.f1716j.length != this.b.n * 2) {
                this.f1716j = new float[(this.f1714h.n * 2)];
            }
            float[] fArr = this.f1716j;
            for (int i2 = 0; i2 < fArr.length; i2 += 2) {
                float[] fArr2 = this.f1714h.l;
                int i3 = i2 / 2;
                fArr[i2] = fArr2[i3];
                fArr[i2 + 1] = fArr2[i3];
            }
            this.c.b(fArr);
            d();
            Path path = this.f1715i;
            path.reset();
            for (int i4 = 0; i4 < fArr.length; i4 += 2) {
                a(canvas, fArr[i4], fArr[i4 + 1], path);
            }
            canvas.restoreToCount(save);
        }
    }

    /* access modifiers changed from: protected */
    public void d() {
        this.d.setColor(this.f1714h.k());
        this.d.setStrokeWidth(this.f1714h.m());
        this.d.setPathEffect(this.f1714h.l());
    }

    public void d(Canvas canvas) {
        List<LimitLine> o2 = this.f1714h.o();
        if (o2 != null && o2.size() > 0) {
            float[] fArr = this.l;
            fArr[0] = 0.0f;
            fArr[1] = 0.0f;
            for (int i2 = 0; i2 < o2.size(); i2++) {
                LimitLine limitLine = o2.get(i2);
                if (limitLine.f()) {
                    int save = canvas.save();
                    this.m.set(this.a.n());
                    this.m.inset(-limitLine.l(), 0.0f);
                    canvas.clipRect(this.m);
                    fArr[0] = limitLine.j();
                    fArr[1] = 0.0f;
                    this.c.b(fArr);
                    a(canvas, limitLine, fArr);
                    a(canvas, limitLine, fArr, limitLine.e() + 2.0f);
                    canvas.restoreToCount(save);
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public void a(float f2, float f3) {
        super.a(f2, f3);
        b();
    }

    public void a(Canvas canvas) {
        if (this.f1714h.f() && this.f1714h.v()) {
            float e = this.f1714h.e();
            this.e.setTypeface(this.f1714h.c());
            this.e.setTextSize(this.f1714h.b());
            this.e.setColor(this.f1714h.a());
            e a = e.a(0.0f, 0.0f);
            if (this.f1714h.A() == XAxis.XAxisPosition.TOP) {
                a.f1727g = 0.5f;
                a.f1728h = 1.0f;
                a(canvas, this.a.i() - e, a);
            } else if (this.f1714h.A() == XAxis.XAxisPosition.TOP_INSIDE) {
                a.f1727g = 0.5f;
                a.f1728h = 1.0f;
                a(canvas, this.a.i() + e + ((float) this.f1714h.M), a);
            } else if (this.f1714h.A() == XAxis.XAxisPosition.BOTTOM) {
                a.f1727g = 0.5f;
                a.f1728h = 0.0f;
                a(canvas, this.a.e() + e, a);
            } else if (this.f1714h.A() == XAxis.XAxisPosition.BOTTOM_INSIDE) {
                a.f1727g = 0.5f;
                a.f1728h = 0.0f;
                a(canvas, (this.a.e() - e) - ((float) this.f1714h.M), a);
            } else {
                a.f1727g = 0.5f;
                a.f1728h = 1.0f;
                a(canvas, this.a.i() - e, a);
                a.f1727g = 0.5f;
                a.f1728h = 0.0f;
                a(canvas, this.a.e() + e, a);
            }
            e.b(a);
        }
    }

    public void b(Canvas canvas) {
        if (this.f1714h.s() && this.f1714h.f()) {
            this.f1681f.setColor(this.f1714h.g());
            this.f1681f.setStrokeWidth(this.f1714h.i());
            this.f1681f.setPathEffect(this.f1714h.h());
            if (this.f1714h.A() == XAxis.XAxisPosition.TOP || this.f1714h.A() == XAxis.XAxisPosition.TOP_INSIDE || this.f1714h.A() == XAxis.XAxisPosition.BOTH_SIDED) {
                canvas.drawLine(this.a.g(), this.a.i(), this.a.h(), this.a.i(), this.f1681f);
            }
            if (this.f1714h.A() == XAxis.XAxisPosition.BOTTOM || this.f1714h.A() == XAxis.XAxisPosition.BOTTOM_INSIDE || this.f1714h.A() == XAxis.XAxisPosition.BOTH_SIDED) {
                canvas.drawLine(this.a.g(), this.a.e(), this.a.h(), this.a.e(), this.f1681f);
            }
        }
    }

    public RectF c() {
        this.k.set(this.a.n());
        this.k.inset(-this.b.m(), 0.0f);
        return this.k;
    }

    /* access modifiers changed from: protected */
    public void a(Canvas canvas, float f2, e eVar) {
        float z = this.f1714h.z();
        boolean r = this.f1714h.r();
        int i2 = this.f1714h.n * 2;
        float[] fArr = new float[i2];
        for (int i3 = 0; i3 < i2; i3 += 2) {
            if (r) {
                fArr[i3] = this.f1714h.m[i3 / 2];
            } else {
                fArr[i3] = this.f1714h.l[i3 / 2];
            }
        }
        this.c.b(fArr);
        for (int i4 = 0; i4 < i2; i4 += 2) {
            float f3 = fArr[i4];
            if (this.a.e(f3)) {
                h.a.a.a.c.e q = this.f1714h.q();
                XAxis xAxis = this.f1714h;
                int i5 = i4 / 2;
                String a = q.a(xAxis.l[i5], (a) xAxis);
                if (this.f1714h.B()) {
                    int i6 = this.f1714h.n;
                    if (i5 == i6 - 1 && i6 > 1) {
                        float c = (float) i.c(this.e, a);
                        if (c > this.a.z() * 2.0f && f3 + c > this.a.l()) {
                            f3 -= c / 2.0f;
                        }
                    } else if (i4 == 0) {
                        f3 += ((float) i.c(this.e, a)) / 2.0f;
                    }
                }
                a(canvas, a, f3, f2, eVar, z);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void a(Canvas canvas, String str, float f2, float f3, e eVar, float f4) {
        i.a(canvas, str, f2, f3, this.e, eVar, f4);
    }

    /* access modifiers changed from: protected */
    public void a(Canvas canvas, float f2, float f3, Path path) {
        path.moveTo(f2, this.a.e());
        path.lineTo(f2, this.a.i());
        canvas.drawPath(path, this.d);
        path.reset();
    }

    public void a(Canvas canvas, LimitLine limitLine, float[] fArr) {
        float[] fArr2 = this.n;
        fArr2[0] = fArr[0];
        fArr2[1] = this.a.i();
        float[] fArr3 = this.n;
        fArr3[2] = fArr[0];
        fArr3[3] = this.a.e();
        this.o.reset();
        Path path = this.o;
        float[] fArr4 = this.n;
        path.moveTo(fArr4[0], fArr4[1]);
        Path path2 = this.o;
        float[] fArr5 = this.n;
        path2.lineTo(fArr5[2], fArr5[3]);
        this.f1682g.setStyle(Paint.Style.STROKE);
        this.f1682g.setColor(limitLine.k());
        this.f1682g.setStrokeWidth(limitLine.l());
        this.f1682g.setPathEffect(limitLine.g());
        canvas.drawPath(this.o, this.f1682g);
    }

    public void a(Canvas canvas, LimitLine limitLine, float[] fArr, float f2) {
        String h2 = limitLine.h();
        if (h2 != null && !h2.equals(BuildConfig.FLAVOR)) {
            this.f1682g.setStyle(limitLine.m());
            this.f1682g.setPathEffect((PathEffect) null);
            this.f1682g.setColor(limitLine.a());
            this.f1682g.setStrokeWidth(0.5f);
            this.f1682g.setTextSize(limitLine.b());
            float l2 = limitLine.l() + limitLine.d();
            LimitLine.LimitLabelPosition i2 = limitLine.i();
            if (i2 == LimitLine.LimitLabelPosition.RIGHT_TOP) {
                this.f1682g.setTextAlign(Paint.Align.LEFT);
                canvas.drawText(h2, fArr[0] + l2, this.a.i() + f2 + ((float) i.a(this.f1682g, h2)), this.f1682g);
            } else if (i2 == LimitLine.LimitLabelPosition.RIGHT_BOTTOM) {
                this.f1682g.setTextAlign(Paint.Align.LEFT);
                canvas.drawText(h2, fArr[0] + l2, this.a.e() - f2, this.f1682g);
            } else if (i2 == LimitLine.LimitLabelPosition.LEFT_TOP) {
                this.f1682g.setTextAlign(Paint.Align.RIGHT);
                canvas.drawText(h2, fArr[0] - l2, this.a.i() + f2 + ((float) i.a(this.f1682g, h2)), this.f1682g);
            } else {
                this.f1682g.setTextAlign(Paint.Align.RIGHT);
                canvas.drawText(h2, fArr[0] - l2, this.a.e() - f2, this.f1682g);
            }
        }
    }
}
