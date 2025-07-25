package h.a.a.a.h;

import android.graphics.Paint;
import h.a.a.a.i.d;
import h.a.a.a.i.g;
import h.a.a.a.i.i;
import h.a.a.a.i.j;

/* compiled from: AxisRenderer */
public abstract class a extends o {
    protected com.github.mikephil.charting.components.a b;
    protected g c;
    protected Paint d;
    protected Paint e;

    /* renamed from: f  reason: collision with root package name */
    protected Paint f1681f;

    /* renamed from: g  reason: collision with root package name */
    protected Paint f1682g;

    public a(j jVar, g gVar, com.github.mikephil.charting.components.a aVar) {
        super(jVar);
        this.c = gVar;
        this.b = aVar;
        if (this.a != null) {
            this.e = new Paint(1);
            Paint paint = new Paint();
            this.d = paint;
            paint.setColor(-7829368);
            this.d.setStrokeWidth(1.0f);
            this.d.setStyle(Paint.Style.STROKE);
            this.d.setAlpha(90);
            Paint paint2 = new Paint();
            this.f1681f = paint2;
            paint2.setColor(-16777216);
            this.f1681f.setStrokeWidth(1.0f);
            this.f1681f.setStyle(Paint.Style.STROKE);
            Paint paint3 = new Paint(1);
            this.f1682g = paint3;
            paint3.setStyle(Paint.Style.STROKE);
        }
    }

    public Paint a() {
        return this.e;
    }

    public void a(float f2, float f3, boolean z) {
        float f4;
        double d2;
        j jVar = this.a;
        if (jVar != null && jVar.j() > 10.0f && !this.a.w()) {
            d b2 = this.c.b(this.a.g(), this.a.i());
            d b3 = this.c.b(this.a.g(), this.a.e());
            if (!z) {
                f4 = (float) b3.f1725h;
                d2 = b2.f1725h;
            } else {
                f4 = (float) b2.f1725h;
                d2 = b3.f1725h;
            }
            d.a(b2);
            d.a(b3);
            f2 = f4;
            f3 = (float) d2;
        }
        a(f2, f3);
    }

    /* access modifiers changed from: protected */
    public void a(float f2, float f3) {
        double d2;
        double d3;
        float f4 = f2;
        float f5 = f3;
        int n = this.b.n();
        double abs = (double) Math.abs(f5 - f4);
        if (n == 0 || abs <= 0.0d || Double.isInfinite(abs)) {
            com.github.mikephil.charting.components.a aVar = this.b;
            aVar.l = new float[0];
            aVar.m = new float[0];
            aVar.n = 0;
            return;
        }
        double d4 = (double) n;
        Double.isNaN(abs);
        Double.isNaN(d4);
        double b2 = (double) i.b(abs / d4);
        if (this.b.y() && b2 < ((double) this.b.j())) {
            b2 = (double) this.b.j();
        }
        double b3 = (double) i.b(Math.pow(10.0d, (double) ((int) Math.log10(b2))));
        Double.isNaN(b3);
        if (((int) (b2 / b3)) > 5) {
            Double.isNaN(b3);
            b2 = Math.floor(b3 * 10.0d);
        }
        int r = this.b.r();
        if (this.b.x()) {
            b2 = (double) (((float) abs) / ((float) (n - 1)));
            com.github.mikephil.charting.components.a aVar2 = this.b;
            aVar2.n = n;
            if (aVar2.l.length < n) {
                aVar2.l = new float[n];
            }
            for (int i2 = 0; i2 < n; i2++) {
                this.b.l[i2] = f4;
                double d5 = (double) f4;
                Double.isNaN(d5);
                Double.isNaN(b2);
                f4 = (float) (d5 + b2);
            }
        } else {
            if (b2 == 0.0d) {
                d2 = 0.0d;
            } else {
                double d6 = (double) f4;
                Double.isNaN(d6);
                d2 = Math.ceil(d6 / b2) * b2;
            }
            if (this.b.r()) {
                d2 -= b2;
            }
            if (b2 == 0.0d) {
                d3 = 0.0d;
            } else {
                double d7 = (double) f5;
                Double.isNaN(d7);
                d3 = i.a(Math.floor(d7 / b2) * b2);
            }
            if (b2 != 0.0d) {
                for (double d8 = d2; d8 <= d3; d8 += b2) {
                    r++;
                }
            }
            com.github.mikephil.charting.components.a aVar3 = this.b;
            aVar3.n = r;
            if (aVar3.l.length < r) {
                aVar3.l = new float[r];
            }
            for (int i3 = 0; i3 < r; i3++) {
                if (d2 == 0.0d) {
                    d2 = 0.0d;
                }
                this.b.l[i3] = (float) d2;
                d2 += b2;
            }
            n = r;
        }
        if (b2 < 1.0d) {
            this.b.o = (int) Math.ceil(-Math.log10(b2));
        } else {
            this.b.o = 0;
        }
        if (this.b.r()) {
            com.github.mikephil.charting.components.a aVar4 = this.b;
            if (aVar4.m.length < n) {
                aVar4.m = new float[n];
            }
            float f6 = ((float) b2) / 2.0f;
            for (int i4 = 0; i4 < n; i4++) {
                com.github.mikephil.charting.components.a aVar5 = this.b;
                aVar5.m[i4] = aVar5.l[i4] + f6;
            }
        }
    }
}
