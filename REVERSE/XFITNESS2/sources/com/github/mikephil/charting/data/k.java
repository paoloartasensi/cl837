package com.github.mikephil.charting.data;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import com.github.mikephil.charting.data.Entry;
import h.a.a.a.e.b.g;
import h.a.a.a.i.i;
import java.util.List;

/* compiled from: LineRadarDataSet */
public abstract class k<T extends Entry> extends l<T> implements g<T> {
    private int C = Color.rgb(140, 234, 255);
    protected Drawable D;
    private int E = 85;
    private float F = 2.5f;
    private boolean G = false;

    public k(List<T> list, String str) {
        super(list, str);
    }

    public Drawable P() {
        return this.D;
    }

    public int Q() {
        return this.E;
    }

    public void c(float f2) {
        if (f2 < 0.0f) {
            f2 = 0.0f;
        }
        if (f2 > 10.0f) {
            f2 = 10.0f;
        }
        this.F = i.a(f2);
    }

    public void i(int i2) {
        this.C = i2;
        this.D = null;
    }

    public boolean s0() {
        return this.G;
    }

    public float u0() {
        return this.F;
    }

    public int y() {
        return this.C;
    }

    public void c(boolean z) {
        this.G = z;
    }
}
