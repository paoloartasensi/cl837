package h.a.a.a.h;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import com.github.mikephil.charting.data.BarEntry;
import h.a.a.a.d.d;
import h.a.a.a.e.a.a;
import h.a.a.a.i.e;
import h.a.a.a.i.g;
import h.a.a.a.i.i;
import h.a.a.a.i.j;
import java.util.List;

/* compiled from: BarChartRenderer */
public class b extends c {

    /* renamed from: g  reason: collision with root package name */
    protected a f1683g;

    /* renamed from: h  reason: collision with root package name */
    protected RectF f1684h = new RectF();

    /* renamed from: i  reason: collision with root package name */
    protected h.a.a.a.b.b[] f1685i;

    /* renamed from: j  reason: collision with root package name */
    protected Paint f1686j;
    protected Paint k;
    private RectF l = new RectF();

    public b(a aVar, h.a.a.a.a.a aVar2, j jVar) {
        super(aVar2, jVar);
        this.f1683g = aVar;
        Paint paint = new Paint(1);
        this.d = paint;
        paint.setStyle(Paint.Style.FILL);
        this.d.setColor(Color.rgb(0, 0, 0));
        this.d.setAlpha(120);
        Paint paint2 = new Paint(1);
        this.f1686j = paint2;
        paint2.setStyle(Paint.Style.FILL);
        Paint paint3 = new Paint(1);
        this.k = paint3;
        paint3.setStyle(Paint.Style.STROKE);
    }

    public void a() {
        com.github.mikephil.charting.data.a barData = this.f1683g.getBarData();
        this.f1685i = new h.a.a.a.b.b[barData.b()];
        for (int i2 = 0; i2 < this.f1685i.length; i2++) {
            h.a.a.a.e.b.a aVar = (h.a.a.a.e.b.a) barData.a(i2);
            this.f1685i[i2] = new h.a.a.a.b.b(aVar.X() * 4 * (aVar.D() ? aVar.t0() : 1), barData.b(), aVar.D());
        }
    }

    public void b(Canvas canvas) {
    }

    public void c(Canvas canvas) {
        boolean z;
        float f2;
        float f3;
        List list;
        boolean z2;
        e eVar;
        int i2;
        float f4;
        boolean z3;
        g gVar;
        float[] fArr;
        float f5;
        int i3;
        int i4;
        BarEntry barEntry;
        float[] fArr2;
        float f6;
        float f7;
        float f8;
        BarEntry barEntry2;
        List list2;
        int i5;
        h.a.a.a.c.e eVar2;
        e eVar3;
        float f9;
        BarEntry barEntry3;
        if (a((h.a.a.a.e.a.e) this.f1683g)) {
            List c = this.f1683g.getBarData().c();
            float a = i.a(4.5f);
            boolean b = this.f1683g.b();
            int i6 = 0;
            while (i6 < this.f1683g.getBarData().b()) {
                h.a.a.a.e.b.a aVar = (h.a.a.a.e.b.a) c.get(i6);
                if (!b(aVar)) {
                    list = c;
                    f3 = f2;
                    z2 = z;
                } else {
                    a((h.a.a.a.e.b.e) aVar);
                    boolean a2 = this.f1683g.a(aVar.S());
                    float a3 = (float) i.a(this.e, "8");
                    float f10 = z ? -f2 : a3 + f2;
                    float f11 = z ? a3 + f2 : -f2;
                    if (a2) {
                        f10 = (-f10) - a3;
                        f11 = (-f11) - a3;
                    }
                    float f12 = f10;
                    float f13 = f11;
                    h.a.a.a.b.b bVar = this.f1685i[i6];
                    float b2 = this.b.b();
                    h.a.a.a.c.e W = aVar.W();
                    e a4 = e.a(aVar.Y());
                    a4.f1727g = i.a(a4.f1727g);
                    a4.f1728h = i.a(a4.f1728h);
                    if (!aVar.D()) {
                        int i7 = 0;
                        while (((float) i7) < ((float) bVar.b.length) * this.b.a()) {
                            float[] fArr3 = bVar.b;
                            float f14 = (fArr3[i7] + fArr3[i7 + 2]) / 2.0f;
                            if (!this.a.c(f14)) {
                                break;
                            }
                            int i8 = i7 + 1;
                            if (!this.a.f(bVar.b[i8]) || !this.a.b(f14)) {
                                i5 = i7;
                                eVar2 = W;
                                list2 = c;
                                eVar3 = a4;
                            } else {
                                int i9 = i7 / 4;
                                BarEntry barEntry4 = (BarEntry) aVar.c(i9);
                                float c2 = barEntry4.c();
                                if (aVar.E()) {
                                    String a5 = W.a(barEntry4);
                                    int i10 = (c2 > 0.0f ? 1 : (c2 == 0.0f ? 0 : -1));
                                    float[] fArr4 = bVar.b;
                                    barEntry3 = barEntry4;
                                    f9 = f14;
                                    String str = a5;
                                    i5 = i7;
                                    list2 = c;
                                    eVar3 = a4;
                                    float f15 = i10 >= 0 ? fArr4[i8] + f12 : fArr4[i7 + 3] + f13;
                                    eVar2 = W;
                                    a(canvas, str, f9, f15, aVar.b(i9));
                                } else {
                                    barEntry3 = barEntry4;
                                    f9 = f14;
                                    i5 = i7;
                                    eVar2 = W;
                                    list2 = c;
                                    eVar3 = a4;
                                }
                                if (barEntry3.b() != null && aVar.G0()) {
                                    Drawable b3 = barEntry3.b();
                                    i.a(canvas, b3, (int) (f9 + eVar3.f1727g), (int) ((c2 >= 0.0f ? bVar.b[i8] + f12 : bVar.b[i5 + 3] + f13) + eVar3.f1728h), b3.getIntrinsicWidth(), b3.getIntrinsicHeight());
                                }
                            }
                            i7 = i5 + 4;
                            a4 = eVar3;
                            W = eVar2;
                            c = list2;
                        }
                        list = c;
                        eVar = a4;
                    } else {
                        h.a.a.a.c.e eVar4 = W;
                        list = c;
                        eVar = a4;
                        g b4 = this.f1683g.b(aVar.S());
                        int i11 = 0;
                        int i12 = 0;
                        while (((float) i11) < ((float) aVar.X()) * this.b.a()) {
                            BarEntry barEntry5 = (BarEntry) aVar.c(i11);
                            float[] h2 = barEntry5.h();
                            float[] fArr5 = bVar.b;
                            float f16 = (fArr5[i12] + fArr5[i12 + 2]) / 2.0f;
                            int b5 = aVar.b(i11);
                            if (h2 != null) {
                                BarEntry barEntry6 = barEntry5;
                                i2 = i11;
                                f4 = f2;
                                z3 = z;
                                fArr = h2;
                                gVar = b4;
                                float f17 = f16;
                                int length = fArr.length * 2;
                                float[] fArr6 = new float[length];
                                float f18 = -barEntry6.e();
                                int i13 = 0;
                                int i14 = 0;
                                float f19 = 0.0f;
                                while (i13 < length) {
                                    float f20 = fArr[i14];
                                    if (f20 == 0.0f && (f19 == 0.0f || f18 == 0.0f)) {
                                        float f21 = f18;
                                        f18 = f20;
                                        f7 = f21;
                                    } else if (f20 >= 0.0f) {
                                        f19 += f20;
                                        f7 = f18;
                                        f18 = f19;
                                    } else {
                                        f7 = f18 - f20;
                                    }
                                    fArr6[i13 + 1] = f18 * b2;
                                    i13 += 2;
                                    i14++;
                                    f18 = f7;
                                }
                                gVar.b(fArr6);
                                int i15 = 0;
                                while (i15 < length) {
                                    float f22 = fArr[i15 / 2];
                                    float f23 = fArr6[i15 + 1] + (((f22 > 0.0f ? 1 : (f22 == 0.0f ? 0 : -1)) == 0 && (f18 > 0.0f ? 1 : (f18 == 0.0f ? 0 : -1)) == 0 && (f19 > 0.0f ? 1 : (f19 == 0.0f ? 0 : -1)) > 0) || (f22 > 0.0f ? 1 : (f22 == 0.0f ? 0 : -1)) < 0 ? f13 : f12);
                                    int i16 = i15;
                                    if (!this.a.c(f17)) {
                                        break;
                                    }
                                    if (!this.a.f(f23) || !this.a.b(f17)) {
                                        i3 = length;
                                        f5 = f17;
                                        i4 = i16;
                                        barEntry = barEntry6;
                                        fArr2 = fArr6;
                                    } else {
                                        if (aVar.E()) {
                                            BarEntry barEntry7 = barEntry6;
                                            f6 = f23;
                                            i4 = i16;
                                            barEntry = barEntry7;
                                            fArr2 = fArr6;
                                            i3 = length;
                                            f5 = f17;
                                            a(canvas, eVar4.a(f22, barEntry7), f17, f6, b5);
                                        } else {
                                            f6 = f23;
                                            i3 = length;
                                            f5 = f17;
                                            i4 = i16;
                                            barEntry = barEntry6;
                                            fArr2 = fArr6;
                                        }
                                        if (barEntry.b() != null && aVar.G0()) {
                                            Drawable b6 = barEntry.b();
                                            i.a(canvas, b6, (int) (f5 + eVar.f1727g), (int) (f6 + eVar.f1728h), b6.getIntrinsicWidth(), b6.getIntrinsicHeight());
                                        }
                                    }
                                    i15 = i4 + 2;
                                    fArr6 = fArr2;
                                    barEntry6 = barEntry;
                                    length = i3;
                                    f17 = f5;
                                }
                            } else if (!this.a.c(f16)) {
                                break;
                            } else {
                                float[] fArr7 = h2;
                                int i17 = i12 + 1;
                                if (!this.a.f(bVar.b[i17]) || !this.a.b(f16)) {
                                    b4 = b4;
                                    z = z;
                                    f2 = f2;
                                    i11 = i11;
                                } else {
                                    if (aVar.E()) {
                                        f8 = f16;
                                        f4 = f2;
                                        fArr = fArr7;
                                        barEntry2 = barEntry5;
                                        i2 = i11;
                                        z3 = z;
                                        gVar = b4;
                                        a(canvas, eVar4.a(barEntry5), f8, bVar.b[i17] + (barEntry5.c() >= 0.0f ? f12 : f13), b5);
                                    } else {
                                        f8 = f16;
                                        i2 = i11;
                                        f4 = f2;
                                        z3 = z;
                                        fArr = fArr7;
                                        barEntry2 = barEntry5;
                                        gVar = b4;
                                    }
                                    if (barEntry2.b() != null && aVar.G0()) {
                                        Drawable b7 = barEntry2.b();
                                        i.a(canvas, b7, (int) (eVar.f1727g + f8), (int) (bVar.b[i17] + (barEntry2.c() >= 0.0f ? f12 : f13) + eVar.f1728h), b7.getIntrinsicWidth(), b7.getIntrinsicHeight());
                                    }
                                }
                            }
                            if (fArr == null) {
                                i12 += 4;
                            } else {
                                i12 += fArr.length * 4;
                            }
                            i11 = i2 + 1;
                            b4 = gVar;
                            z = z3;
                            f2 = f4;
                        }
                    }
                    f3 = f2;
                    z2 = z;
                    e.b(eVar);
                }
                i6++;
                b = z2;
                c = list;
                a = f3;
            }
        }
    }

    public void a(Canvas canvas) {
        com.github.mikephil.charting.data.a barData = this.f1683g.getBarData();
        for (int i2 = 0; i2 < barData.b(); i2++) {
            h.a.a.a.e.b.a aVar = (h.a.a.a.e.b.a) barData.a(i2);
            if (aVar.isVisible()) {
                a(canvas, aVar, i2);
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
            float k2 = this.f1683g.getBarData().k() / 2.0f;
            int min = Math.min((int) Math.ceil((double) (((float) aVar.X()) * a)), aVar.X());
            for (int i5 = 0; i5 < min; i5++) {
                float d = ((BarEntry) aVar2.c(i5)).d();
                RectF rectF = this.l;
                rectF.left = d - k2;
                rectF.right = d + k2;
                b.a(rectF);
                if (!this.a.b(this.l.right)) {
                    Canvas canvas2 = canvas;
                } else if (!this.a.c(this.l.left)) {
                    break;
                } else {
                    this.l.top = this.a.i();
                    this.l.bottom = this.a.e();
                    canvas.drawRect(this.l, this.f1686j);
                }
            }
        }
        Canvas canvas3 = canvas;
        h.a.a.a.b.b bVar = this.f1685i[i3];
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
            int i6 = i4 + 2;
            if (this.a.b(bVar.b[i6])) {
                if (this.a.c(bVar.b[i4])) {
                    if (!z) {
                        this.c.setColor(aVar2.e(i4 / 4));
                    }
                    if (aVar.L() != null) {
                        h.a.a.a.g.a L = aVar.L();
                        Paint paint = this.c;
                        float[] fArr = bVar.b;
                        paint.setShader(new LinearGradient(fArr[i4], fArr[i4 + 3], fArr[i4], fArr[i4 + 1], L.b(), L.a(), Shader.TileMode.MIRROR));
                    }
                    if (aVar.j() != null) {
                        Paint paint2 = this.c;
                        float[] fArr2 = bVar.b;
                        float f2 = fArr2[i4];
                        float f3 = fArr2[i4 + 3];
                        float f4 = fArr2[i4];
                        float f5 = fArr2[i4 + 1];
                        int i7 = i4 / 4;
                        paint2.setShader(new LinearGradient(f2, f3, f4, f5, aVar2.d(i7).b(), aVar2.d(i7).a(), Shader.TileMode.MIRROR));
                    }
                    float[] fArr3 = bVar.b;
                    int i8 = i4 + 1;
                    int i9 = i4 + 3;
                    canvas.drawRect(fArr3[i4], fArr3[i8], fArr3[i6], fArr3[i9], this.c);
                    if (z2) {
                        float[] fArr4 = bVar.b;
                        canvas.drawRect(fArr4[i4], fArr4[i8], fArr4[i6], fArr4[i9], this.k);
                    }
                } else {
                    return;
                }
            }
            i4 += 4;
            Canvas canvas4 = canvas;
        }
    }

    /* access modifiers changed from: protected */
    public void a(float f2, float f3, float f4, float f5, g gVar) {
        this.f1684h.set(f2 - f5, f3, f2 + f5, f4);
        gVar.a(this.f1684h, this.b.b());
    }

    public void a(Canvas canvas, String str, float f2, float f3, int i2) {
        this.e.setColor(i2);
        canvas.drawText(str, f2, f3, this.e);
    }

    public void a(Canvas canvas, d[] dVarArr) {
        float f2;
        float f3;
        com.github.mikephil.charting.data.a barData = this.f1683g.getBarData();
        for (d dVar : dVarArr) {
            h.a.a.a.e.b.a aVar = (h.a.a.a.e.b.a) barData.a(dVar.c());
            if (aVar != null && aVar.e0()) {
                BarEntry barEntry = (BarEntry) aVar.a(dVar.g(), dVar.i());
                if (a(barEntry, aVar)) {
                    g b = this.f1683g.b(aVar.S());
                    this.d.setColor(aVar.K());
                    this.d.setAlpha(aVar.i());
                    if (!(dVar.f() >= 0 && barEntry.i())) {
                        f3 = barEntry.c();
                        f2 = 0.0f;
                    } else if (this.f1683g.c()) {
                        float f4 = barEntry.f();
                        f2 = -barEntry.e();
                        f3 = f4;
                    } else {
                        h.a.a.a.d.j jVar = barEntry.g()[dVar.f()];
                        f3 = jVar.a;
                        f2 = jVar.b;
                    }
                    a(barEntry.d(), f3, f2, barData.k() / 2.0f, b);
                    a(dVar, this.f1684h);
                    canvas.drawRect(this.f1684h, this.d);
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public void a(d dVar, RectF rectF) {
        dVar.a(rectF.centerX(), rectF.top);
    }
}
