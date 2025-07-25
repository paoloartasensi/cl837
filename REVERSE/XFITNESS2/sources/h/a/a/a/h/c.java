package h.a.a.a.h;

import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.data.Entry;
import h.a.a.a.e.a.b;
import h.a.a.a.e.b.e;
import h.a.a.a.i.j;

/* compiled from: BarLineScatterCandleBubbleRenderer */
public abstract class c extends g {

    /* renamed from: f  reason: collision with root package name */
    protected a f1687f = new a();

    /* compiled from: BarLineScatterCandleBubbleRenderer */
    protected class a {
        public int a;
        public int b;
        public int c;

        protected a() {
        }

        public void a(b bVar, h.a.a.a.e.b.b bVar2) {
            int i2;
            float max = Math.max(0.0f, Math.min(1.0f, c.this.b.a()));
            float lowestVisibleX = bVar.getLowestVisibleX();
            float highestVisibleX = bVar.getHighestVisibleX();
            Entry a2 = bVar2.a(lowestVisibleX, Float.NaN, DataSet.Rounding.DOWN);
            Entry a3 = bVar2.a(highestVisibleX, Float.NaN, DataSet.Rounding.UP);
            int i3 = 0;
            if (a2 == null) {
                i2 = 0;
            } else {
                i2 = bVar2.b(a2);
            }
            this.a = i2;
            if (a3 != null) {
                i3 = bVar2.b(a3);
            }
            this.b = i3;
            this.c = (int) (((float) (i3 - this.a)) * max);
        }
    }

    public c(h.a.a.a.a.a aVar, j jVar) {
        super(aVar, jVar);
    }

    /* access modifiers changed from: protected */
    public boolean a(Entry entry, h.a.a.a.e.b.b bVar) {
        if (entry == null) {
            return false;
        }
        float b = (float) bVar.b(entry);
        if (entry == null || b >= ((float) bVar.X()) * this.b.a()) {
            return false;
        }
        return true;
    }

    /* access modifiers changed from: protected */
    public boolean b(e eVar) {
        return eVar.isVisible() && (eVar.E() || eVar.G0());
    }
}
