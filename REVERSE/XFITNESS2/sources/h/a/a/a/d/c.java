package h.a.a.a.d;

import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.data.b;
import com.github.mikephil.charting.data.h;
import h.a.a.a.e.a.a;
import h.a.a.a.e.a.f;
import h.a.a.a.e.b.e;
import java.util.List;

/* compiled from: CombinedHighlighter */
public class c extends b<f> implements f {
    protected a c;

    public c(f fVar, a aVar) {
        super(fVar);
        this.c = aVar.getBarData() == null ? null : new a(aVar);
    }

    /* access modifiers changed from: protected */
    public List<d> b(float f2, float f3, float f4) {
        this.b.clear();
        List<b> k = ((f) this.a).getCombinedData().k();
        for (int i2 = 0; i2 < k.size(); i2++) {
            h hVar = k.get(i2);
            a aVar = this.c;
            if (aVar == null || !(hVar instanceof com.github.mikephil.charting.data.a)) {
                int b = hVar.b();
                for (int i3 = 0; i3 < b; i3++) {
                    e a = k.get(i2).a(i3);
                    if (a.e0()) {
                        for (d next : a(a, i3, f2, DataSet.Rounding.CLOSEST)) {
                            next.a(i2);
                            this.b.add(next);
                        }
                    }
                }
            } else {
                d a2 = aVar.a(f3, f4);
                if (a2 != null) {
                    a2.a(i2);
                    this.b.add(a2);
                }
            }
        }
        return this.b;
    }
}
