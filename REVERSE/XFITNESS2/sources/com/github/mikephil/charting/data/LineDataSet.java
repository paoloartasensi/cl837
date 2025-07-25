package com.github.mikephil.charting.data;

import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.util.Log;
import h.a.a.a.c.b;
import h.a.a.a.c.d;
import h.a.a.a.e.b.f;
import h.a.a.a.i.i;
import java.util.ArrayList;
import java.util.List;

public class LineDataSet extends k<Entry> implements f {
    private Mode H = Mode.LINEAR;
    private List<Integer> I = null;
    private int J = -1;
    private float K = 8.0f;
    private float L = 4.0f;
    private float M = 0.2f;
    private DashPathEffect N = null;
    private d O = new b();
    private boolean P = true;
    private boolean Q = true;

    public enum Mode {
        LINEAR,
        STEPPED,
        CUBIC_BEZIER,
        HORIZONTAL_BEZIER
    }

    public LineDataSet(List<Entry> list, String str) {
        super(list, str);
        if (this.I == null) {
            this.I = new ArrayList();
        }
        this.I.clear();
        this.I.add(Integer.valueOf(Color.rgb(140, 234, 255)));
    }

    public Mode C0() {
        return this.H;
    }

    public boolean E0() {
        return this.N != null;
    }

    public boolean F0() {
        return this.Q;
    }

    public float I() {
        return this.M;
    }

    public void L0() {
        if (this.I == null) {
            this.I = new ArrayList();
        }
        this.I.clear();
    }

    public DashPathEffect M() {
        return this.N;
    }

    public void a(Mode mode) {
        this.H = mode;
    }

    public d c0() {
        return this.O;
    }

    public void d(float f2) {
        if (f2 >= 1.0f) {
            this.K = i.a(f2);
        } else {
            Log.e("LineDataSet", "Circle radius cannot be < 1");
        }
    }

    public boolean d0() {
        return this.P;
    }

    public void j(int i2) {
        L0();
        this.I.add(Integer.valueOf(i2));
    }

    public float m0() {
        return this.L;
    }

    public int n() {
        return this.J;
    }

    public float r0() {
        return this.K;
    }

    public int u() {
        return this.I.size();
    }

    public int a(int i2) {
        return this.I.get(i2).intValue();
    }

    public void d(boolean z) {
        this.P = z;
    }
}
