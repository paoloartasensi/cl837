package com.github.mikephil.charting.data;

import android.graphics.DashPathEffect;
import com.github.mikephil.charting.data.Entry;
import h.a.a.a.e.b.h;
import h.a.a.a.i.i;
import java.util.List;

/* compiled from: LineScatterCandleRadarDataSet */
public abstract class l<T extends Entry> extends c<T> implements h<T> {
    protected float A;
    protected DashPathEffect B;
    protected boolean y;
    protected boolean z;

    public l(List<T> list, String str) {
        super(list, str);
        this.y = true;
        this.z = true;
        this.A = 0.5f;
        this.B = null;
        this.A = i.a(0.5f);
    }

    public boolean f0() {
        return this.y;
    }

    public DashPathEffect h0() {
        return this.B;
    }

    public boolean v0() {
        return this.z;
    }

    public float z() {
        return this.A;
    }
}
