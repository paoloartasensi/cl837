package com.github.mikephil.charting.components;

import h.a.a.a.i.i;

public class XAxis extends a {
    public int J;
    public int K;
    public int L = 1;
    public int M = 1;
    protected float N = 0.0f;
    private boolean O = false;
    private XAxisPosition P = XAxisPosition.TOP;

    public enum XAxisPosition {
        TOP,
        BOTTOM,
        BOTH_SIDED,
        TOP_INSIDE,
        BOTTOM_INSIDE
    }

    public XAxis() {
        this.c = i.a(4.0f);
    }

    public XAxisPosition A() {
        return this.P;
    }

    public boolean B() {
        return this.O;
    }

    public void a(XAxisPosition xAxisPosition) {
        this.P = xAxisPosition;
    }

    public void d(boolean z) {
        this.O = z;
    }

    public float z() {
        return this.N;
    }
}
