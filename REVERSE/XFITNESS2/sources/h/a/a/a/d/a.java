package h.a.a.a.d;

import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.b;
import h.a.a.a.i.d;

/* compiled from: BarHighlighter */
public class a extends b<h.a.a.a.e.a.a> {
    public a(h.a.a.a.e.a.a aVar) {
        super(aVar);
    }

    public d a(float f2, float f3) {
        d a = super.a(f2, f3);
        if (a == null) {
            return null;
        }
        d b = b(f2, f3);
        h.a.a.a.e.b.a aVar = (h.a.a.a.e.b.a) ((h.a.a.a.e.a.a) this.a).getBarData().a(a.c());
        if (aVar.D()) {
            return a(a, aVar, (float) b.f1724g, (float) b.f1725h);
        }
        d.a(b);
        return a;
    }

    public d a(d dVar, h.a.a.a.e.b.a aVar, float f2, float f3) {
        BarEntry barEntry = (BarEntry) aVar.a(f2, f3);
        if (barEntry == null) {
            return null;
        }
        if (barEntry.h() == null) {
            return dVar;
        }
        j[] g2 = barEntry.g();
        if (g2.length <= 0) {
            return null;
        }
        int a = a(g2, f3);
        d a2 = ((h.a.a.a.e.a.a) this.a).b(aVar.S()).a(dVar.g(), g2[a].b);
        d dVar2 = new d(barEntry.d(), barEntry.c(), (float) a2.f1724g, (float) a2.f1725h, dVar.c(), a, dVar.a());
        d.a(a2);
        return dVar2;
    }

    /* access modifiers changed from: protected */
    public int a(j[] jVarArr, float f2) {
        if (jVarArr == null || jVarArr.length == 0) {
            return 0;
        }
        if (jVarArr.length <= 0) {
            int max = Math.max(jVarArr.length - 1, 0);
            if (f2 > jVarArr[max].b) {
                return max;
            }
            return 0;
        }
        jVarArr[0].a(f2);
        throw null;
    }

    /* access modifiers changed from: protected */
    public float a(float f2, float f3, float f4, float f5) {
        return Math.abs(f2 - f4);
    }

    /* access modifiers changed from: protected */
    public b a() {
        return ((h.a.a.a.e.a.a) this.a).getBarData();
    }
}
