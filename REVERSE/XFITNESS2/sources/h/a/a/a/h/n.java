package h.a.a.a.h;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.Drawable;
import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.data.RadarEntry;
import h.a.a.a.a.a;
import h.a.a.a.c.e;
import h.a.a.a.d.d;
import h.a.a.a.i.i;
import h.a.a.a.i.j;

/* compiled from: RadarChartRenderer */
public class n extends k {

    /* renamed from: h  reason: collision with root package name */
    protected RadarChart f1709h;

    /* renamed from: i  reason: collision with root package name */
    protected Paint f1710i;

    /* renamed from: j  reason: collision with root package name */
    protected Paint f1711j;
    protected Path k = new Path();
    protected Path l = new Path();

    public n(RadarChart radarChart, a aVar, j jVar) {
        super(aVar, jVar);
        this.f1709h = radarChart;
        Paint paint = new Paint(1);
        this.d = paint;
        paint.setStyle(Paint.Style.STROKE);
        this.d.setStrokeWidth(2.0f);
        this.d.setColor(Color.rgb(255, 187, 115));
        Paint paint2 = new Paint(1);
        this.f1710i = paint2;
        paint2.setStyle(Paint.Style.STROKE);
        this.f1711j = new Paint(1);
    }

    public void a() {
    }

    public void a(Canvas canvas) {
        com.github.mikephil.charting.data.n nVar = (com.github.mikephil.charting.data.n) this.f1709h.getData();
        int X = ((h.a.a.a.e.b.j) nVar.e()).X();
        for (h.a.a.a.e.b.j jVar : nVar.c()) {
            if (jVar.isVisible()) {
                a(canvas, jVar, X);
            }
        }
    }

    public void b(Canvas canvas) {
        d(canvas);
    }

    public void c(Canvas canvas) {
        float f2;
        int i2;
        float f3;
        int i3;
        int i4;
        RadarEntry radarEntry;
        e eVar;
        h.a.a.a.e.b.j jVar;
        h.a.a.a.i.e eVar2;
        float a = this.b.a();
        float b = this.b.b();
        float sliceAngle = this.f1709h.getSliceAngle();
        float factor = this.f1709h.getFactor();
        h.a.a.a.i.e centerOffsets = this.f1709h.getCenterOffsets();
        h.a.a.a.i.e a2 = h.a.a.a.i.e.a(0.0f, 0.0f);
        h.a.a.a.i.e a3 = h.a.a.a.i.e.a(0.0f, 0.0f);
        float a4 = i.a(5.0f);
        int i5 = 0;
        while (i5 < ((com.github.mikephil.charting.data.n) this.f1709h.getData()).b()) {
            h.a.a.a.e.b.j jVar2 = (h.a.a.a.e.b.j) ((com.github.mikephil.charting.data.n) this.f1709h.getData()).a(i5);
            if (!b(jVar2)) {
                i2 = i5;
                f2 = a;
            } else {
                a((h.a.a.a.e.b.e) jVar2);
                e W = jVar2.W();
                h.a.a.a.i.e a5 = h.a.a.a.i.e.a(jVar2.Y());
                a5.f1727g = i.a(a5.f1727g);
                a5.f1728h = i.a(a5.f1728h);
                int i6 = 0;
                while (i6 < jVar2.X()) {
                    RadarEntry radarEntry2 = (RadarEntry) jVar2.c(i6);
                    h.a.a.a.i.e eVar3 = a5;
                    float f4 = ((float) i6) * sliceAngle * a;
                    i.a(centerOffsets, (radarEntry2.c() - this.f1709h.getYChartMin()) * factor * b, f4 + this.f1709h.getRotationAngle(), a2);
                    if (jVar2.E()) {
                        String a6 = W.a(radarEntry2);
                        float f5 = a2.f1727g;
                        radarEntry = radarEntry2;
                        float f6 = a2.f1728h - a4;
                        int b2 = jVar2.b(i6);
                        i3 = i6;
                        f3 = a;
                        eVar2 = eVar3;
                        eVar = W;
                        float f7 = f5;
                        jVar = jVar2;
                        float f8 = f6;
                        i4 = i5;
                        a(canvas, a6, f7, f8, b2);
                    } else {
                        radarEntry = radarEntry2;
                        i3 = i6;
                        jVar = jVar2;
                        i4 = i5;
                        f3 = a;
                        eVar2 = eVar3;
                        eVar = W;
                    }
                    if (radarEntry.b() != null && jVar.G0()) {
                        Drawable b3 = radarEntry.b();
                        i.a(centerOffsets, (radarEntry.c() * factor * b) + eVar2.f1728h, f4 + this.f1709h.getRotationAngle(), a3);
                        float f9 = a3.f1728h + eVar2.f1727g;
                        a3.f1728h = f9;
                        i.a(canvas, b3, (int) a3.f1727g, (int) f9, b3.getIntrinsicWidth(), b3.getIntrinsicHeight());
                    }
                    i6 = i3 + 1;
                    a5 = eVar2;
                    jVar2 = jVar;
                    W = eVar;
                    i5 = i4;
                    a = f3;
                }
                i2 = i5;
                f2 = a;
                h.a.a.a.i.e.b(a5);
            }
            i5 = i2 + 1;
            a = f2;
        }
        h.a.a.a.i.e.b(centerOffsets);
        h.a.a.a.i.e.b(a2);
        h.a.a.a.i.e.b(a3);
    }

    /* access modifiers changed from: protected */
    public void d(Canvas canvas) {
        float sliceAngle = this.f1709h.getSliceAngle();
        float factor = this.f1709h.getFactor();
        float rotationAngle = this.f1709h.getRotationAngle();
        h.a.a.a.i.e centerOffsets = this.f1709h.getCenterOffsets();
        this.f1710i.setStrokeWidth(this.f1709h.getWebLineWidth());
        this.f1710i.setColor(this.f1709h.getWebColor());
        this.f1710i.setAlpha(this.f1709h.getWebAlpha());
        int skipWebLineCount = this.f1709h.getSkipWebLineCount() + 1;
        int X = ((h.a.a.a.e.b.j) ((com.github.mikephil.charting.data.n) this.f1709h.getData()).e()).X();
        h.a.a.a.i.e a = h.a.a.a.i.e.a(0.0f, 0.0f);
        for (int i2 = 0; i2 < X; i2 += skipWebLineCount) {
            i.a(centerOffsets, this.f1709h.getYRange() * factor, (((float) i2) * sliceAngle) + rotationAngle, a);
            canvas.drawLine(centerOffsets.f1727g, centerOffsets.f1728h, a.f1727g, a.f1728h, this.f1710i);
        }
        h.a.a.a.i.e.b(a);
        this.f1710i.setStrokeWidth(this.f1709h.getWebLineWidthInner());
        this.f1710i.setColor(this.f1709h.getWebColorInner());
        this.f1710i.setAlpha(this.f1709h.getWebAlpha());
        int i3 = this.f1709h.getYAxis().n;
        h.a.a.a.i.e a2 = h.a.a.a.i.e.a(0.0f, 0.0f);
        h.a.a.a.i.e a3 = h.a.a.a.i.e.a(0.0f, 0.0f);
        for (int i4 = 0; i4 < i3; i4++) {
            int i5 = 0;
            while (i5 < ((com.github.mikephil.charting.data.n) this.f1709h.getData()).d()) {
                float yChartMin = (this.f1709h.getYAxis().l[i4] - this.f1709h.getYChartMin()) * factor;
                i.a(centerOffsets, yChartMin, (((float) i5) * sliceAngle) + rotationAngle, a2);
                i5++;
                i.a(centerOffsets, yChartMin, (((float) i5) * sliceAngle) + rotationAngle, a3);
                canvas.drawLine(a2.f1727g, a2.f1728h, a3.f1727g, a3.f1728h, this.f1710i);
            }
        }
        h.a.a.a.i.e.b(a2);
        h.a.a.a.i.e.b(a3);
    }

    /* access modifiers changed from: protected */
    public void a(Canvas canvas, h.a.a.a.e.b.j jVar, int i2) {
        float a = this.b.a();
        float b = this.b.b();
        float sliceAngle = this.f1709h.getSliceAngle();
        float factor = this.f1709h.getFactor();
        h.a.a.a.i.e centerOffsets = this.f1709h.getCenterOffsets();
        h.a.a.a.i.e a2 = h.a.a.a.i.e.a(0.0f, 0.0f);
        Path path = this.k;
        path.reset();
        boolean z = false;
        for (int i3 = 0; i3 < jVar.X(); i3++) {
            this.c.setColor(jVar.e(i3));
            i.a(centerOffsets, (((RadarEntry) jVar.c(i3)).c() - this.f1709h.getYChartMin()) * factor * b, (((float) i3) * sliceAngle * a) + this.f1709h.getRotationAngle(), a2);
            if (!Float.isNaN(a2.f1727g)) {
                if (!z) {
                    path.moveTo(a2.f1727g, a2.f1728h);
                    z = true;
                } else {
                    path.lineTo(a2.f1727g, a2.f1728h);
                }
            }
        }
        if (jVar.X() > i2) {
            path.lineTo(centerOffsets.f1727g, centerOffsets.f1728h);
        }
        path.close();
        if (jVar.s0()) {
            Drawable P = jVar.P();
            if (P != null) {
                a(canvas, path, P);
            } else {
                a(canvas, path, jVar.y(), jVar.Q());
            }
        }
        this.c.setStrokeWidth(jVar.u0());
        this.c.setStyle(Paint.Style.STROKE);
        if (!jVar.s0() || jVar.Q() < 255) {
            canvas.drawPath(path, this.c);
        }
        h.a.a.a.i.e.b(centerOffsets);
        h.a.a.a.i.e.b(a2);
    }

    public void a(Canvas canvas, String str, float f2, float f3, int i2) {
        this.e.setColor(i2);
        canvas.drawText(str, f2, f3, this.e);
    }

    public void a(Canvas canvas, d[] dVarArr) {
        int i2;
        d[] dVarArr2 = dVarArr;
        float sliceAngle = this.f1709h.getSliceAngle();
        float factor = this.f1709h.getFactor();
        h.a.a.a.i.e centerOffsets = this.f1709h.getCenterOffsets();
        h.a.a.a.i.e a = h.a.a.a.i.e.a(0.0f, 0.0f);
        com.github.mikephil.charting.data.n nVar = (com.github.mikephil.charting.data.n) this.f1709h.getData();
        int length = dVarArr2.length;
        int i3 = 0;
        int i4 = 0;
        while (i4 < length) {
            d dVar = dVarArr2[i4];
            h.a.a.a.e.b.j jVar = (h.a.a.a.e.b.j) nVar.a(dVar.c());
            if (jVar != null && jVar.e0()) {
                RadarEntry radarEntry = (RadarEntry) jVar.c((int) dVar.g());
                if (a(radarEntry, jVar)) {
                    i.a(centerOffsets, (radarEntry.c() - this.f1709h.getYChartMin()) * factor * this.b.b(), (dVar.g() * sliceAngle * this.b.a()) + this.f1709h.getRotationAngle(), a);
                    dVar.a(a.f1727g, a.f1728h);
                    a(canvas, a.f1727g, a.f1728h, jVar);
                    if (jVar.B0() && !Float.isNaN(a.f1727g) && !Float.isNaN(a.f1728h)) {
                        int q0 = jVar.q0();
                        if (q0 == 1122867) {
                            q0 = jVar.e(i3);
                        }
                        if (jVar.V() < 255) {
                            q0 = h.a.a.a.i.a.a(q0, jVar.V());
                        }
                        float O = jVar.O();
                        float B = jVar.B();
                        int F = jVar.F();
                        int i5 = F;
                        i2 = i4;
                        a(canvas, a, O, B, i5, q0, jVar.h());
                        i4 = i2 + 1;
                        i3 = 0;
                    }
                }
            }
            i2 = i4;
            i4 = i2 + 1;
            i3 = 0;
        }
        h.a.a.a.i.e.b(centerOffsets);
        h.a.a.a.i.e.b(a);
    }

    public void a(Canvas canvas, h.a.a.a.i.e eVar, float f2, float f3, int i2, int i3, float f4) {
        canvas.save();
        float a = i.a(f3);
        float a2 = i.a(f2);
        if (i2 != 1122867) {
            Path path = this.l;
            path.reset();
            path.addCircle(eVar.f1727g, eVar.f1728h, a, Path.Direction.CW);
            if (a2 > 0.0f) {
                path.addCircle(eVar.f1727g, eVar.f1728h, a2, Path.Direction.CCW);
            }
            this.f1711j.setColor(i2);
            this.f1711j.setStyle(Paint.Style.FILL);
            canvas.drawPath(path, this.f1711j);
        }
        if (i3 != 1122867) {
            this.f1711j.setColor(i3);
            this.f1711j.setStyle(Paint.Style.STROKE);
            this.f1711j.setStrokeWidth(i.a(f4));
            canvas.drawCircle(eVar.f1727g, eVar.f1728h, a, this.f1711j);
        }
        canvas.restore();
    }
}
