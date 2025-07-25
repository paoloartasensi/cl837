package h.a.a.a.h;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import com.github.mikephil.charting.data.BarEntry;
import h.a.a.a.b.b;
import h.a.a.a.b.c;
import h.a.a.a.d.d;
import h.a.a.a.e.a.a;
import h.a.a.a.i.e;
import h.a.a.a.i.g;
import h.a.a.a.i.i;
import h.a.a.a.i.j;
import java.util.List;

/* compiled from: HorizontalBarChartRenderer */
public class h extends b {
    private RectF m = new RectF();

    public h(a aVar, h.a.a.a.a.a aVar2, j jVar) {
        super(aVar, aVar2, jVar);
        this.e.setTextAlign(Paint.Align.LEFT);
    }

    public void a() {
        com.github.mikephil.charting.data.a barData = this.f1683g.getBarData();
        this.f1685i = new c[barData.b()];
        for (int i2 = 0; i2 < this.f1685i.length; i2++) {
            h.a.a.a.e.b.a aVar = (h.a.a.a.e.b.a) barData.a(i2);
            this.f1685i[i2] = new c(aVar.X() * 4 * (aVar.D() ? aVar.t0() : 1), barData.b(), aVar.D());
        }
    }

    public void c(Canvas canvas) {
        int i2;
        List list;
        e eVar;
        int i3;
        float[] fArr;
        float[] fArr2;
        int i4;
        float f2;
        float f3;
        float f4;
        BarEntry barEntry;
        int i5;
        float f5;
        int i6;
        List list2;
        h.a.a.a.c.e eVar2;
        b bVar;
        e eVar3;
        if (a(this.f1683g)) {
            List c = this.f1683g.getBarData().c();
            float a = i.a(5.0f);
            boolean b = this.f1683g.b();
            int i7 = 0;
            while (i7 < this.f1683g.getBarData().b()) {
                h.a.a.a.e.b.a aVar = (h.a.a.a.e.b.a) c.get(i7);
                if (!b(aVar)) {
                    list = c;
                    i2 = i7;
                } else {
                    boolean a2 = this.f1683g.a(aVar.S());
                    a((h.a.a.a.e.b.e) aVar);
                    float f6 = 2.0f;
                    float a3 = ((float) i.a(this.e, "10")) / 2.0f;
                    h.a.a.a.c.e W = aVar.W();
                    b bVar2 = this.f1685i[i7];
                    float b2 = this.b.b();
                    e a4 = e.a(aVar.Y());
                    a4.f1727g = i.a(a4.f1727g);
                    a4.f1728h = i.a(a4.f1728h);
                    if (!aVar.D()) {
                        int i8 = 0;
                        while (((float) i8) < ((float) bVar2.b.length) * this.b.a()) {
                            float[] fArr3 = bVar2.b;
                            int i9 = i8 + 1;
                            float f7 = (fArr3[i9] + fArr3[i8 + 3]) / f6;
                            if (!this.a.d(fArr3[i9])) {
                                break;
                            }
                            if (this.a.e(bVar2.b[i8]) && this.a.a(bVar2.b[i9])) {
                                BarEntry barEntry2 = (BarEntry) aVar.c(i8 / 4);
                                float c2 = barEntry2.c();
                                String a5 = W.a(barEntry2);
                                float c3 = (float) i.c(this.e, a5);
                                String str = a5;
                                float f8 = b ? a : -(c3 + a);
                                e eVar4 = a4;
                                float f9 = b ? -(c3 + a) : a;
                                if (a2) {
                                    f8 = (-f8) - c3;
                                    f9 = (-f9) - c3;
                                }
                                float f10 = f8;
                                float f11 = f9;
                                if (aVar.E()) {
                                    float f12 = bVar2.b[i8 + 2];
                                    float f13 = c2 >= 0.0f ? f10 : f11;
                                    int b3 = aVar.b(i8 / 2);
                                    i5 = i8;
                                    String str2 = str;
                                    list2 = c;
                                    eVar3 = eVar4;
                                    i6 = i7;
                                    bVar = bVar2;
                                    float f14 = f7 + a3;
                                    f5 = a3;
                                    eVar2 = W;
                                    a(canvas, str2, f12 + f13, f14, b3);
                                } else {
                                    i5 = i8;
                                    list2 = c;
                                    f5 = a3;
                                    eVar3 = eVar4;
                                    eVar2 = W;
                                    i6 = i7;
                                    bVar = bVar2;
                                }
                                if (barEntry2.b() != null && aVar.G0()) {
                                    Drawable b4 = barEntry2.b();
                                    float f15 = bVar.b[i5 + 2];
                                    if (c2 < 0.0f) {
                                        f10 = f11;
                                    }
                                    i.a(canvas, b4, (int) (f15 + f10 + eVar3.f1727g), (int) (f7 + eVar3.f1728h), b4.getIntrinsicWidth(), b4.getIntrinsicHeight());
                                }
                            } else {
                                i5 = i8;
                                list2 = c;
                                i6 = i7;
                                f5 = a3;
                                eVar3 = a4;
                                bVar = bVar2;
                                eVar2 = W;
                            }
                            i8 = i5 + 4;
                            a4 = eVar3;
                            bVar2 = bVar;
                            W = eVar2;
                            c = list2;
                            i7 = i6;
                            a3 = f5;
                            f6 = 2.0f;
                        }
                        list = c;
                        i2 = i7;
                        eVar = a4;
                    } else {
                        list = c;
                        i2 = i7;
                        float f16 = a3;
                        eVar = a4;
                        b bVar3 = bVar2;
                        h.a.a.a.c.e eVar5 = W;
                        g b5 = this.f1683g.b(aVar.S());
                        int i10 = 0;
                        int i11 = 0;
                        while (((float) i10) < ((float) aVar.X()) * this.b.a()) {
                            BarEntry barEntry3 = (BarEntry) aVar.c(i10);
                            int b6 = aVar.b(i10);
                            float[] h2 = barEntry3.h();
                            if (h2 != null) {
                                BarEntry barEntry4 = barEntry3;
                                i3 = i10;
                                fArr = h2;
                                int length = fArr.length * 2;
                                float[] fArr4 = new float[length];
                                float f17 = -barEntry4.e();
                                int i12 = 0;
                                int i13 = 0;
                                float f18 = 0.0f;
                                while (i12 < length) {
                                    float f19 = fArr[i13];
                                    if (f19 == 0.0f && (f18 == 0.0f || f17 == 0.0f)) {
                                        float f20 = f17;
                                        f17 = f19;
                                        f4 = f20;
                                    } else if (f19 >= 0.0f) {
                                        f18 += f19;
                                        f4 = f17;
                                        f17 = f18;
                                    } else {
                                        f4 = f17 - f19;
                                    }
                                    fArr4[i12] = f17 * b2;
                                    i12 += 2;
                                    i13++;
                                    f17 = f4;
                                }
                                b5.b(fArr4);
                                int i14 = 0;
                                while (true) {
                                    if (i14 >= length) {
                                        break;
                                    }
                                    float f21 = fArr[i14 / 2];
                                    String a6 = eVar5.a(f21, barEntry4);
                                    float c4 = (float) i.c(this.e, a6);
                                    String str3 = a6;
                                    float f22 = b ? a : -(c4 + a);
                                    int i15 = length;
                                    float f23 = b ? -(c4 + a) : a;
                                    if (a2) {
                                        f22 = (-f22) - c4;
                                        f23 = (-f23) - c4;
                                    }
                                    boolean z = (f21 == 0.0f && f17 == 0.0f && f18 > 0.0f) || f21 < 0.0f;
                                    float f24 = fArr4[i14];
                                    if (z) {
                                        f22 = f23;
                                    }
                                    float f25 = f24 + f22;
                                    float[] fArr5 = bVar3.b;
                                    float f26 = (fArr5[i11 + 1] + fArr5[i11 + 3]) / 2.0f;
                                    if (!this.a.d(f26)) {
                                        break;
                                    }
                                    if (this.a.e(f25) && this.a.a(f26)) {
                                        if (aVar.E()) {
                                            float f27 = f26 + f16;
                                            f2 = f26;
                                            String str4 = str3;
                                            i4 = i14;
                                            fArr2 = fArr4;
                                            float f28 = f27;
                                            f3 = f25;
                                            a(canvas, str4, f25, f28, b6);
                                        } else {
                                            f2 = f26;
                                            i4 = i14;
                                            fArr2 = fArr4;
                                            f3 = f25;
                                        }
                                        if (barEntry4.b() != null && aVar.G0()) {
                                            Drawable b7 = barEntry4.b();
                                            i.a(canvas, b7, (int) (f3 + eVar.f1727g), (int) (f2 + eVar.f1728h), b7.getIntrinsicWidth(), b7.getIntrinsicHeight());
                                        }
                                    } else {
                                        i4 = i14;
                                        fArr2 = fArr4;
                                    }
                                    i14 = i4 + 2;
                                    length = i15;
                                    fArr4 = fArr2;
                                }
                            } else {
                                int i16 = i11 + 1;
                                if (!this.a.d(bVar3.b[i16])) {
                                    break;
                                } else if (this.a.e(bVar3.b[i11]) && this.a.a(bVar3.b[i16])) {
                                    String a7 = eVar5.a(barEntry3);
                                    float c5 = (float) i.c(this.e, a7);
                                    float f29 = b ? a : -(c5 + a);
                                    float f30 = b ? -(c5 + a) : a;
                                    if (a2) {
                                        f29 = (-f29) - c5;
                                        f30 = (-f30) - c5;
                                    }
                                    float f31 = f29;
                                    float f32 = f30;
                                    if (aVar.E()) {
                                        i3 = i10;
                                        fArr = h2;
                                        barEntry = barEntry3;
                                        a(canvas, a7, bVar3.b[i11 + 2] + (barEntry3.c() >= 0.0f ? f31 : f32), bVar3.b[i16] + f16, b6);
                                    } else {
                                        barEntry = barEntry3;
                                        i3 = i10;
                                        fArr = h2;
                                    }
                                    if (barEntry.b() != null && aVar.G0()) {
                                        Drawable b8 = barEntry.b();
                                        float f33 = bVar3.b[i11 + 2];
                                        if (barEntry.c() < 0.0f) {
                                            f31 = f32;
                                        }
                                        i.a(canvas, b8, (int) (f33 + f31 + eVar.f1727g), (int) (bVar3.b[i16] + eVar.f1728h), b8.getIntrinsicWidth(), b8.getIntrinsicHeight());
                                    }
                                }
                            }
                            if (fArr == null) {
                                i11 += 4;
                            } else {
                                i11 += fArr.length * 4;
                            }
                            i10 = i3 + 1;
                        }
                    }
                    e.b(eVar);
                }
                i7 = i2 + 1;
                c = list;
            }
        }
    }

    /* access modifiers changed from: protected */
    public void a(Canvas canvas, h.a.a.a.e.b.a aVar, int i2) {
        h.a.a.a.e.b.a aVar2 = aVar;
        int i3 = i2;
        g b = this.f1683g.b(aVar.S());
        this.k.setColor(aVar.w0());
        this.k.setStrokeWidth(i.a(aVar.o()));
        int i4 = 0;
        boolean z = true;
        boolean z2 = aVar.o() > 0.0f;
        float a = this.b.a();
        float b2 = this.b.b();
        if (this.f1683g.a()) {
            this.f1686j.setColor(aVar.m());
            float k = this.f1683g.getBarData().k() / 2.0f;
            int min = Math.min((int) Math.ceil((double) (((float) aVar.X()) * a)), aVar.X());
            for (int i5 = 0; i5 < min; i5++) {
                float d = ((BarEntry) aVar2.c(i5)).d();
                RectF rectF = this.m;
                rectF.top = d - k;
                rectF.bottom = d + k;
                b.a(rectF);
                if (!this.a.d(this.m.bottom)) {
                    Canvas canvas2 = canvas;
                } else if (!this.a.a(this.m.top)) {
                    break;
                } else {
                    this.m.left = this.a.g();
                    this.m.right = this.a.h();
                    canvas.drawRect(this.m, this.f1686j);
                }
            }
        }
        Canvas canvas3 = canvas;
        b bVar = this.f1685i[i3];
        bVar.a(a, b2);
        bVar.a(i3);
        bVar.a(this.f1683g.a(aVar.S()));
        bVar.a(this.f1683g.getBarData().k());
        bVar.a(aVar2);
        b.b(bVar.b);
        if (aVar.p0().size() != 1) {
            z = false;
        }
        if (z) {
            this.c.setColor(aVar.b0());
        }
        while (i4 < bVar.b()) {
            int i6 = i4 + 3;
            if (this.a.d(bVar.b[i6])) {
                int i7 = i4 + 1;
                if (this.a.a(bVar.b[i7])) {
                    if (!z) {
                        this.c.setColor(aVar2.e(i4 / 4));
                    }
                    float[] fArr = bVar.b;
                    int i8 = i4 + 2;
                    canvas.drawRect(fArr[i4], fArr[i7], fArr[i8], fArr[i6], this.c);
                    if (z2) {
                        float[] fArr2 = bVar.b;
                        canvas.drawRect(fArr2[i4], fArr2[i7], fArr2[i8], fArr2[i6], this.k);
                    }
                }
                i4 += 4;
                Canvas canvas4 = canvas;
            } else {
                return;
            }
        }
    }

    public void a(Canvas canvas, String str, float f2, float f3, int i2) {
        this.e.setColor(i2);
        canvas.drawText(str, f2, f3, this.e);
    }

    /* access modifiers changed from: protected */
    public void a(float f2, float f3, float f4, float f5, g gVar) {
        this.f1684h.set(f3, f2 - f5, f4, f2 + f5);
        gVar.b(this.f1684h, this.b.b());
    }

    /* access modifiers changed from: protected */
    public void a(d dVar, RectF rectF) {
        dVar.a(rectF.centerY(), rectF.right);
    }

    /* access modifiers changed from: protected */
    public boolean a(h.a.a.a.e.a.e eVar) {
        return ((float) eVar.getData().d()) < ((float) eVar.getMaxVisibleCount()) * this.a.q();
    }
}
