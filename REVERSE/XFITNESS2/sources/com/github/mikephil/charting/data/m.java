package com.github.mikephil.charting.data;

import h.a.a.a.d.d;
import h.a.a.a.e.b.i;

/* compiled from: PieData */
public class m extends h<i> {
    public i k() {
        return (i) this.f1368i.get(0);
    }

    public float l() {
        float f2 = 0.0f;
        for (int i2 = 0; i2 < k().X(); i2++) {
            f2 += ((PieEntry) k().c(i2)).c();
        }
        return f2;
    }

    public i a(int i2) {
        if (i2 == 0) {
            return k();
        }
        return null;
    }

    public Entry a(d dVar) {
        return k().c((int) dVar.g());
    }
}
