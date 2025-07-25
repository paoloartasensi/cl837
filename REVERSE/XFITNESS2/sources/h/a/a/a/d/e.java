package h.a.a.a.d;

import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.data.Entry;
import h.a.a.a.e.a.a;
import h.a.a.a.i.d;
import java.util.ArrayList;
import java.util.List;

/* compiled from: HorizontalBarHighlighter */
public class e extends a {
    public e(a aVar) {
        super(aVar);
    }

    public d a(float f2, float f3) {
        com.github.mikephil.charting.data.a barData = ((a) this.a).getBarData();
        d b = b(f3, f2);
        d a = a((float) b.f1725h, f3, f2);
        if (a == null) {
            return null;
        }
        h.a.a.a.e.b.a aVar = (h.a.a.a.e.b.a) barData.a(a.c());
        if (aVar.D()) {
            return a(a, aVar, (float) b.f1725h, (float) b.f1724g);
        }
        d.a(b);
        return a;
    }

    /* access modifiers changed from: protected */
    public List<d> a(h.a.a.a.e.b.e eVar, int i2, float f2, DataSet.Rounding rounding) {
        Entry a;
        ArrayList arrayList = new ArrayList();
        List<Entry> a2 = eVar.a(f2);
        if (a2.size() == 0 && (a = eVar.a(f2, Float.NaN, rounding)) != null) {
            a2 = eVar.a(a.d());
        }
        if (a2.size() == 0) {
            return arrayList;
        }
        for (Entry entry : a2) {
            d a3 = ((a) this.a).b(eVar.S()).a(entry.c(), entry.d());
            arrayList.add(new d(entry.d(), entry.c(), (float) a3.f1724g, (float) a3.f1725h, i2, eVar.S()));
        }
        return arrayList;
    }

    /* access modifiers changed from: protected */
    public float a(float f2, float f3, float f4, float f5) {
        return Math.abs(f3 - f5);
    }
}
