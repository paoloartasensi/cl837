package com.github.mikephil.charting.data;

import h.a.a.a.d.d;
import h.a.a.a.e.b.b;
import java.util.ArrayList;
import java.util.List;

/* compiled from: CombinedData */
public class i extends b<b<? extends Entry>> {

    /* renamed from: j  reason: collision with root package name */
    private j f1369j;
    private a k;
    private o l;
    private g m;
    private f n;

    public void a() {
        if (this.f1368i == null) {
            this.f1368i = new ArrayList();
        }
        this.f1368i.clear();
        this.a = -3.4028235E38f;
        this.b = Float.MAX_VALUE;
        this.c = -3.4028235E38f;
        this.d = Float.MAX_VALUE;
        this.e = -3.4028235E38f;
        this.f1365f = Float.MAX_VALUE;
        this.f1366g = -3.4028235E38f;
        this.f1367h = Float.MAX_VALUE;
        for (h next : k()) {
            next.a();
            this.f1368i.addAll(next.c());
            if (next.h() > this.a) {
                this.a = next.h();
            }
            if (next.i() < this.b) {
                this.b = next.i();
            }
            if (next.f() > this.c) {
                this.c = next.f();
            }
            if (next.g() < this.d) {
                this.d = next.g();
            }
            float f2 = next.e;
            if (f2 > this.e) {
                this.e = f2;
            }
            float f3 = next.f1365f;
            if (f3 < this.f1365f) {
                this.f1365f = f3;
            }
            float f4 = next.f1366g;
            if (f4 > this.f1366g) {
                this.f1366g = f4;
            }
            float f5 = next.f1367h;
            if (f5 < this.f1367h) {
                this.f1367h = f5;
            }
        }
    }

    public b b(int i2) {
        return k().get(i2);
    }

    public void j() {
        j jVar = this.f1369j;
        if (jVar != null) {
            jVar.j();
        }
        a aVar = this.k;
        if (aVar != null) {
            aVar.j();
        }
        g gVar = this.m;
        if (gVar != null) {
            gVar.j();
        }
        o oVar = this.l;
        if (oVar != null) {
            oVar.j();
        }
        f fVar = this.n;
        if (fVar != null) {
            fVar.j();
        }
        a();
    }

    public List<b> k() {
        ArrayList arrayList = new ArrayList();
        j jVar = this.f1369j;
        if (jVar != null) {
            arrayList.add(jVar);
        }
        a aVar = this.k;
        if (aVar != null) {
            arrayList.add(aVar);
        }
        o oVar = this.l;
        if (oVar != null) {
            arrayList.add(oVar);
        }
        g gVar = this.m;
        if (gVar != null) {
            arrayList.add(gVar);
        }
        f fVar = this.n;
        if (fVar != null) {
            arrayList.add(fVar);
        }
        return arrayList;
    }

    public a l() {
        return this.k;
    }

    public f m() {
        return this.n;
    }

    public g n() {
        return this.m;
    }

    public j o() {
        return this.f1369j;
    }

    public o p() {
        return this.l;
    }

    public b<? extends Entry> b(d dVar) {
        if (dVar.b() >= k().size()) {
            return null;
        }
        b b = b(dVar.b());
        if (dVar.c() >= b.b()) {
            return null;
        }
        return (b) b.c().get(dVar.c());
    }

    /* JADX WARNING: Removed duplicated region for block: B:9:0x003d  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.github.mikephil.charting.data.Entry a(h.a.a.a.d.d r6) {
        /*
            r5 = this;
            int r0 = r6.b()
            java.util.List r1 = r5.k()
            int r1 = r1.size()
            r2 = 0
            if (r0 < r1) goto L_0x0010
            return r2
        L_0x0010:
            int r0 = r6.b()
            com.github.mikephil.charting.data.b r0 = r5.b((int) r0)
            int r1 = r6.c()
            int r3 = r0.b()
            if (r1 < r3) goto L_0x0023
            return r2
        L_0x0023:
            int r1 = r6.c()
            h.a.a.a.e.b.e r0 = r0.a((int) r1)
            float r1 = r6.g()
            java.util.List r0 = r0.a((float) r1)
            java.util.Iterator r0 = r0.iterator()
        L_0x0037:
            boolean r1 = r0.hasNext()
            if (r1 == 0) goto L_0x005a
            java.lang.Object r1 = r0.next()
            com.github.mikephil.charting.data.Entry r1 = (com.github.mikephil.charting.data.Entry) r1
            float r3 = r1.c()
            float r4 = r6.i()
            int r3 = (r3 > r4 ? 1 : (r3 == r4 ? 0 : -1))
            if (r3 == 0) goto L_0x0059
            float r3 = r6.i()
            boolean r3 = java.lang.Float.isNaN(r3)
            if (r3 == 0) goto L_0x0037
        L_0x0059:
            return r1
        L_0x005a:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.github.mikephil.charting.data.i.a(h.a.a.a.d.d):com.github.mikephil.charting.data.Entry");
    }
}
