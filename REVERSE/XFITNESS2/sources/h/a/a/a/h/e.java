package h.a.a.a.h;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import com.github.mikephil.charting.data.CandleEntry;
import h.a.a.a.a.a;
import h.a.a.a.e.a.d;
import h.a.a.a.h.c;
import h.a.a.a.i.g;
import h.a.a.a.i.i;
import h.a.a.a.i.j;
import java.util.List;

/* compiled from: CandleStickChartRenderer */
public class e extends l {

    /* renamed from: h  reason: collision with root package name */
    protected d f1692h;

    /* renamed from: i  reason: collision with root package name */
    private float[] f1693i = new float[8];

    /* renamed from: j  reason: collision with root package name */
    private float[] f1694j = new float[4];
    private float[] k = new float[4];
    private float[] l = new float[4];
    private float[] m = new float[4];

    public e(d dVar, a aVar, j jVar) {
        super(aVar, jVar);
        this.f1692h = dVar;
    }

    public void a() {
    }

    public void a(Canvas canvas) {
        for (h.a.a.a.e.b.d dVar : this.f1692h.getCandleData().c()) {
            if (dVar.isVisible()) {
                a(canvas, dVar);
            }
        }
    }

    public void b(Canvas canvas) {
    }

    public void c(Canvas canvas) {
        h.a.a.a.e.b.d dVar;
        float f2;
        CandleEntry candleEntry;
        if (a((h.a.a.a.e.a.e) this.f1692h)) {
            List c = this.f1692h.getCandleData().c();
            for (int i2 = 0; i2 < c.size(); i2++) {
                h.a.a.a.e.b.d dVar2 = (h.a.a.a.e.b.d) c.get(i2);
                if (b(dVar2) && dVar2.X() >= 1) {
                    a((h.a.a.a.e.b.e) dVar2);
                    g b = this.f1692h.b(dVar2.S());
                    this.f1687f.a(this.f1692h, dVar2);
                    float a = this.b.a();
                    float b2 = this.b.b();
                    c.a aVar = this.f1687f;
                    float[] a2 = b.a(dVar2, a, b2, aVar.a, aVar.b);
                    float a3 = i.a(5.0f);
                    h.a.a.a.c.e W = dVar2.W();
                    h.a.a.a.i.e a4 = h.a.a.a.i.e.a(dVar2.Y());
                    a4.f1727g = i.a(a4.f1727g);
                    a4.f1728h = i.a(a4.f1728h);
                    int i3 = 0;
                    while (i3 < a2.length) {
                        float f3 = a2[i3];
                        float f4 = a2[i3 + 1];
                        if (!this.a.c(f3)) {
                            break;
                        }
                        if (!this.a.b(f3) || !this.a.f(f4)) {
                            dVar = dVar2;
                        } else {
                            int i4 = i3 / 2;
                            CandleEntry candleEntry2 = (CandleEntry) dVar2.c(this.f1687f.a + i4);
                            if (dVar2.E()) {
                                candleEntry = candleEntry2;
                                f2 = f4;
                                float f5 = f4 - a3;
                                dVar = dVar2;
                                a(canvas, W.a(candleEntry2), f3, f5, dVar2.b(i4));
                            } else {
                                candleEntry = candleEntry2;
                                f2 = f4;
                                dVar = dVar2;
                            }
                            if (candleEntry.b() != null && dVar.G0()) {
                                Drawable b3 = candleEntry.b();
                                i.a(canvas, b3, (int) (f3 + a4.f1727g), (int) (f2 + a4.f1728h), b3.getIntrinsicWidth(), b3.getIntrinsicHeight());
                            }
                        }
                        i3 += 2;
                        dVar2 = dVar;
                    }
                    h.a.a.a.i.e.b(a4);
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public void a(Canvas canvas, h.a.a.a.e.b.d dVar) {
        int i2;
        int i3;
        int i4;
        int i5;
        int i6;
        h.a.a.a.e.b.d dVar2 = dVar;
        g b = this.f1692h.b(dVar.S());
        float b2 = this.b.b();
        float j0 = dVar.j0();
        boolean U = dVar.U();
        this.f1687f.a(this.f1692h, dVar2);
        this.c.setStrokeWidth(dVar.n0());
        int i7 = this.f1687f.a;
        while (true) {
            c.a aVar = this.f1687f;
            if (i7 <= aVar.c + aVar.a) {
                CandleEntry candleEntry = (CandleEntry) dVar2.c(i7);
                if (candleEntry == null) {
                    Canvas canvas2 = canvas;
                } else {
                    float d = candleEntry.d();
                    float h2 = candleEntry.h();
                    float e = candleEntry.e();
                    float f2 = candleEntry.f();
                    float g2 = candleEntry.g();
                    if (U) {
                        float[] fArr = this.f1693i;
                        fArr[0] = d;
                        fArr[2] = d;
                        fArr[4] = d;
                        fArr[6] = d;
                        if (h2 > e) {
                            fArr[1] = f2 * b2;
                            fArr[3] = h2 * b2;
                            fArr[5] = g2 * b2;
                            fArr[7] = e * b2;
                        } else if (h2 < e) {
                            fArr[1] = f2 * b2;
                            fArr[3] = e * b2;
                            fArr[5] = g2 * b2;
                            fArr[7] = h2 * b2;
                        } else {
                            fArr[1] = f2 * b2;
                            fArr[3] = h2 * b2;
                            fArr[5] = g2 * b2;
                            fArr[7] = fArr[3];
                        }
                        b.b(this.f1693i);
                        if (!dVar.k0()) {
                            Paint paint = this.c;
                            if (dVar.v() == 1122867) {
                                i3 = dVar2.e(i7);
                            } else {
                                i3 = dVar.v();
                            }
                            paint.setColor(i3);
                        } else if (h2 > e) {
                            Paint paint2 = this.c;
                            if (dVar.D0() == 1122867) {
                                i6 = dVar2.e(i7);
                            } else {
                                i6 = dVar.D0();
                            }
                            paint2.setColor(i6);
                        } else if (h2 < e) {
                            Paint paint3 = this.c;
                            if (dVar.J() == 1122867) {
                                i5 = dVar2.e(i7);
                            } else {
                                i5 = dVar.J();
                            }
                            paint3.setColor(i5);
                        } else {
                            Paint paint4 = this.c;
                            if (dVar.r() == 1122867) {
                                i4 = dVar2.e(i7);
                            } else {
                                i4 = dVar.r();
                            }
                            paint4.setColor(i4);
                        }
                        this.c.setStyle(Paint.Style.STROKE);
                        canvas.drawLines(this.f1693i, this.c);
                        float[] fArr2 = this.f1694j;
                        fArr2[0] = (d - 0.5f) + j0;
                        fArr2[1] = e * b2;
                        fArr2[2] = (d + 0.5f) - j0;
                        fArr2[3] = h2 * b2;
                        b.b(fArr2);
                        if (h2 > e) {
                            if (dVar.D0() == 1122867) {
                                this.c.setColor(dVar2.e(i7));
                            } else {
                                this.c.setColor(dVar.D0());
                            }
                            this.c.setStyle(dVar.R());
                            float[] fArr3 = this.f1694j;
                            canvas.drawRect(fArr3[0], fArr3[3], fArr3[2], fArr3[1], this.c);
                        } else if (h2 < e) {
                            if (dVar.J() == 1122867) {
                                this.c.setColor(dVar2.e(i7));
                            } else {
                                this.c.setColor(dVar.J());
                            }
                            this.c.setStyle(dVar.g());
                            float[] fArr4 = this.f1694j;
                            canvas.drawRect(fArr4[0], fArr4[1], fArr4[2], fArr4[3], this.c);
                        } else {
                            if (dVar.r() == 1122867) {
                                this.c.setColor(dVar2.e(i7));
                            } else {
                                this.c.setColor(dVar.r());
                            }
                            float[] fArr5 = this.f1694j;
                            canvas.drawLine(fArr5[0], fArr5[1], fArr5[2], fArr5[3], this.c);
                        }
                    } else {
                        Canvas canvas3 = canvas;
                        float[] fArr6 = this.k;
                        fArr6[0] = d;
                        fArr6[1] = f2 * b2;
                        fArr6[2] = d;
                        fArr6[3] = g2 * b2;
                        float[] fArr7 = this.l;
                        fArr7[0] = (d - 0.5f) + j0;
                        float f3 = h2 * b2;
                        fArr7[1] = f3;
                        fArr7[2] = d;
                        fArr7[3] = f3;
                        float[] fArr8 = this.m;
                        fArr8[0] = (0.5f + d) - j0;
                        float f4 = e * b2;
                        fArr8[1] = f4;
                        fArr8[2] = d;
                        fArr8[3] = f4;
                        b.b(fArr6);
                        b.b(this.l);
                        b.b(this.m);
                        if (h2 > e) {
                            if (dVar.D0() == 1122867) {
                                i2 = dVar2.e(i7);
                            } else {
                                i2 = dVar.D0();
                            }
                        } else if (h2 < e) {
                            if (dVar.J() == 1122867) {
                                i2 = dVar2.e(i7);
                            } else {
                                i2 = dVar.J();
                            }
                        } else if (dVar.r() == 1122867) {
                            i2 = dVar2.e(i7);
                        } else {
                            i2 = dVar.r();
                        }
                        this.c.setColor(i2);
                        float[] fArr9 = this.k;
                        Canvas canvas4 = canvas;
                        canvas4.drawLine(fArr9[0], fArr9[1], fArr9[2], fArr9[3], this.c);
                        float[] fArr10 = this.l;
                        canvas4.drawLine(fArr10[0], fArr10[1], fArr10[2], fArr10[3], this.c);
                        float[] fArr11 = this.m;
                        canvas4.drawLine(fArr11[0], fArr11[1], fArr11[2], fArr11[3], this.c);
                    }
                }
                i7++;
            } else {
                return;
            }
        }
    }

    public void a(Canvas canvas, String str, float f2, float f3, int i2) {
        this.e.setColor(i2);
        canvas.drawText(str, f2, f3, this.e);
    }

    public void a(Canvas canvas, h.a.a.a.d.d[] dVarArr) {
        com.github.mikephil.charting.data.g candleData = this.f1692h.getCandleData();
        for (h.a.a.a.d.d dVar : dVarArr) {
            h.a.a.a.e.b.d dVar2 = (h.a.a.a.e.b.d) candleData.a(dVar.c());
            if (dVar2 != null && dVar2.e0()) {
                CandleEntry candleEntry = (CandleEntry) dVar2.a(dVar.g(), dVar.i());
                if (a(candleEntry, dVar2)) {
                    h.a.a.a.i.d a = this.f1692h.b(dVar2.S()).a(candleEntry.d(), ((candleEntry.g() * this.b.b()) + (candleEntry.f() * this.b.b())) / 2.0f);
                    dVar.a((float) a.f1724g, (float) a.f1725h);
                    a(canvas, (float) a.f1724g, (float) a.f1725h, dVar2);
                }
            }
        }
    }
}
