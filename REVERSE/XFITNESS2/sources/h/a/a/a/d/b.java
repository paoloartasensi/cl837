package h.a.a.a.d;

import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.data.Entry;
import h.a.a.a.e.a.b;
import h.a.a.a.e.b.e;
import h.a.a.a.i.d;
import java.util.ArrayList;
import java.util.List;

/* compiled from: ChartHighlighter */
public class b<T extends h.a.a.a.e.a.b> implements f {
    protected T a;
    protected List<d> b = new ArrayList();

    public b(T t) {
        this.a = t;
    }

    public d a(float f2, float f3) {
        d b2 = b(f2, f3);
        d.a(b2);
        return a((float) b2.f1724g, f2, f3);
    }

    /* access modifiers changed from: protected */
    public d b(float f2, float f3) {
        return this.a.b(YAxis.AxisDependency.LEFT).b(f2, f3);
    }

    /* access modifiers changed from: protected */
    public List<d> b(float f2, float f3, float f4) {
        this.b.clear();
        com.github.mikephil.charting.data.b a2 = a();
        if (a2 == null) {
            return this.b;
        }
        int b2 = a2.b();
        for (int i2 = 0; i2 < b2; i2++) {
            e a3 = a2.a(i2);
            if (a3.e0()) {
                this.b.addAll(a(a3, i2, f2, DataSet.Rounding.CLOSEST));
            }
        }
        return this.b;
    }

    /* access modifiers changed from: protected */
    public d a(float f2, float f3, float f4) {
        List<d> b2 = b(f2, f3, f4);
        if (b2.isEmpty()) {
            return null;
        }
        return a(b2, f3, f4, a(b2, f4, YAxis.AxisDependency.LEFT) < a(b2, f4, YAxis.AxisDependency.RIGHT) ? YAxis.AxisDependency.LEFT : YAxis.AxisDependency.RIGHT, this.a.getMaxHighlightDistance());
    }

    /* access modifiers changed from: protected */
    public float a(List<d> list, float f2, YAxis.AxisDependency axisDependency) {
        float f3 = Float.MAX_VALUE;
        for (int i2 = 0; i2 < list.size(); i2++) {
            d dVar = list.get(i2);
            if (dVar.a() == axisDependency) {
                float abs = Math.abs(a(dVar) - f2);
                if (abs < f3) {
                    f3 = abs;
                }
            }
        }
        return f3;
    }

    /* access modifiers changed from: protected */
    public float a(d dVar) {
        return dVar.j();
    }

    /* access modifiers changed from: protected */
    public List<d> a(e eVar, int i2, float f2, DataSet.Rounding rounding) {
        Entry a2;
        ArrayList arrayList = new ArrayList();
        List<Entry> a3 = eVar.a(f2);
        if (a3.size() == 0 && (a2 = eVar.a(f2, Float.NaN, rounding)) != null) {
            a3 = eVar.a(a2.d());
        }
        if (a3.size() == 0) {
            return arrayList;
        }
        for (Entry entry : a3) {
            d a4 = this.a.b(eVar.S()).a(entry.d(), entry.c());
            arrayList.add(new d(entry.d(), entry.c(), (float) a4.f1724g, (float) a4.f1725h, i2, eVar.S()));
        }
        return arrayList;
    }

    public d a(List<d> list, float f2, float f3, YAxis.AxisDependency axisDependency, float f4) {
        d dVar = null;
        for (int i2 = 0; i2 < list.size(); i2++) {
            d dVar2 = list.get(i2);
            if (axisDependency == null || dVar2.a() == axisDependency) {
                float a2 = a(f2, f3, dVar2.h(), dVar2.j());
                if (a2 < f4) {
                    dVar = dVar2;
                    f4 = a2;
                }
            }
        }
        return dVar;
    }

    /* access modifiers changed from: protected */
    public float a(float f2, float f3, float f4, float f5) {
        return (float) Math.hypot((double) (f2 - f4), (double) (f3 - f5));
    }

    /* access modifiers changed from: protected */
    public com.github.mikephil.charting.data.b a() {
        return this.a.getData();
    }
}
