package h.a.a.a.h;

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.Log;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.o;
import h.a.a.a.a.a;
import h.a.a.a.d.d;
import h.a.a.a.e.a.e;
import h.a.a.a.e.a.h;
import h.a.a.a.e.b.k;
import h.a.a.a.h.c;
import h.a.a.a.i.g;
import h.a.a.a.i.i;
import h.a.a.a.i.j;
import java.util.List;

/* compiled from: ScatterChartRenderer */
public class p extends l {

    /* renamed from: h  reason: collision with root package name */
    protected h f1712h;

    /* renamed from: i  reason: collision with root package name */
    float[] f1713i = new float[2];

    public p(h hVar, a aVar, j jVar) {
        super(aVar, jVar);
        this.f1712h = hVar;
    }

    public void a() {
    }

    public void a(Canvas canvas) {
        for (k kVar : this.f1712h.getScatterData().c()) {
            if (kVar.isVisible()) {
                a(canvas, kVar);
            }
        }
    }

    public void b(Canvas canvas) {
    }

    public void c(Canvas canvas) {
        k kVar;
        Entry entry;
        if (a((e) this.f1712h)) {
            List c = this.f1712h.getScatterData().c();
            for (int i2 = 0; i2 < this.f1712h.getScatterData().b(); i2++) {
                k kVar2 = (k) c.get(i2);
                if (b(kVar2) && kVar2.X() >= 1) {
                    a((h.a.a.a.e.b.e) kVar2);
                    this.f1687f.a(this.f1712h, kVar2);
                    g b = this.f1712h.b(kVar2.S());
                    float a = this.b.a();
                    float b2 = this.b.b();
                    c.a aVar = this.f1687f;
                    float[] a2 = b.a(kVar2, a, b2, aVar.a, aVar.b);
                    float a3 = i.a(kVar2.G());
                    h.a.a.a.c.e W = kVar2.W();
                    h.a.a.a.i.e a4 = h.a.a.a.i.e.a(kVar2.Y());
                    a4.f1727g = i.a(a4.f1727g);
                    a4.f1728h = i.a(a4.f1728h);
                    int i3 = 0;
                    while (i3 < a2.length && this.a.c(a2[i3])) {
                        if (this.a.b(a2[i3])) {
                            int i4 = i3 + 1;
                            if (this.a.f(a2[i4])) {
                                int i5 = i3 / 2;
                                Entry c2 = kVar2.c(this.f1687f.a + i5);
                                if (kVar2.E()) {
                                    String a5 = W.a(c2);
                                    float f2 = a2[i3];
                                    float f3 = a2[i4] - a3;
                                    entry = c2;
                                    float f4 = f3;
                                    kVar = kVar2;
                                    a(canvas, a5, f2, f4, kVar2.b(i5 + this.f1687f.a));
                                } else {
                                    entry = c2;
                                    kVar = kVar2;
                                }
                                if (entry.b() != null && kVar.G0()) {
                                    Drawable b3 = entry.b();
                                    i.a(canvas, b3, (int) (a2[i3] + a4.f1727g), (int) (a2[i4] + a4.f1728h), b3.getIntrinsicWidth(), b3.getIntrinsicHeight());
                                }
                                i3 += 2;
                                kVar2 = kVar;
                            }
                        }
                        kVar = kVar2;
                        i3 += 2;
                        kVar2 = kVar;
                    }
                    h.a.a.a.i.e.b(a4);
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public void a(Canvas canvas, k kVar) {
        int i2;
        k kVar2 = kVar;
        if (kVar.X() >= 1) {
            j jVar = this.a;
            g b = this.f1712h.b(kVar.S());
            float b2 = this.b.b();
            h.a.a.a.h.w.a k = kVar.k();
            if (k == null) {
                Log.i("MISSING", "There's no IShapeRenderer specified for ScatterDataSet");
                return;
            }
            int min = (int) Math.min(Math.ceil((double) (((float) kVar.X()) * this.b.a())), (double) ((float) kVar.X()));
            int i3 = 0;
            while (i3 < min) {
                Entry c = kVar2.c(i3);
                this.f1713i[0] = c.d();
                this.f1713i[1] = c.c() * b2;
                b.b(this.f1713i);
                if (jVar.c(this.f1713i[0])) {
                    if (!jVar.b(this.f1713i[0]) || !jVar.f(this.f1713i[1])) {
                        i2 = i3;
                    } else {
                        this.c.setColor(kVar2.e(i3 / 2));
                        j jVar2 = this.a;
                        float[] fArr = this.f1713i;
                        i2 = i3;
                        k.a(canvas, kVar, jVar2, fArr[0], fArr[1], this.c);
                    }
                    i3 = i2 + 1;
                } else {
                    return;
                }
            }
        }
    }

    public void a(Canvas canvas, String str, float f2, float f3, int i2) {
        this.e.setColor(i2);
        canvas.drawText(str, f2, f3, this.e);
    }

    public void a(Canvas canvas, d[] dVarArr) {
        o scatterData = this.f1712h.getScatterData();
        for (d dVar : dVarArr) {
            k kVar = (k) scatterData.a(dVar.c());
            if (kVar != null && kVar.e0()) {
                Entry a = kVar.a(dVar.g(), dVar.i());
                if (a(a, kVar)) {
                    h.a.a.a.i.d a2 = this.f1712h.b(kVar.S()).a(a.d(), a.c() * this.b.b());
                    dVar.a((float) a2.f1724g, (float) a2.f1725h);
                    a(canvas, (float) a2.f1724g, (float) a2.f1725h, kVar);
                }
            }
        }
    }
}
