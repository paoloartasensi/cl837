package com.github.mikephil.charting.components;

import android.graphics.Paint;
import h.a.a.a.i.i;

public class YAxis extends a {
    private boolean J = true;
    private boolean K = true;
    protected boolean L = false;
    protected boolean M = false;
    protected int N = -7829368;
    protected float O = 1.0f;
    protected float P = 10.0f;
    protected float Q = 10.0f;
    private YAxisLabelPosition R = YAxisLabelPosition.OUTSIDE_CHART;
    private AxisDependency S;
    protected float T = 0.0f;
    protected float U = Float.POSITIVE_INFINITY;

    public enum AxisDependency {
        LEFT,
        RIGHT
    }

    public enum YAxisLabelPosition {
        OUTSIDE_CHART,
        INSIDE_CHART
    }

    public YAxis(AxisDependency axisDependency) {
        this.S = axisDependency;
        this.c = 0.0f;
    }

    public YAxisLabelPosition A() {
        return this.R;
    }

    public float B() {
        return this.U;
    }

    public float C() {
        return this.T;
    }

    public float D() {
        return this.Q;
    }

    public float E() {
        return this.P;
    }

    public int F() {
        return this.N;
    }

    public float G() {
        return this.O;
    }

    public boolean H() {
        return this.J;
    }

    public boolean I() {
        return this.K;
    }

    public boolean J() {
        return this.M;
    }

    public boolean K() {
        return this.L;
    }

    public boolean L() {
        return f() && v() && A() == YAxisLabelPosition.OUTSIDE_CHART;
    }

    public float a(Paint paint) {
        paint.setTextSize(this.e);
        return ((float) i.a(paint, p())) + (e() * 2.0f);
    }

    public float b(Paint paint) {
        paint.setTextSize(this.e);
        float c = ((float) i.c(paint, p())) + (d() * 2.0f);
        float C = C();
        float B = B();
        if (C > 0.0f) {
            C = i.a(C);
        }
        if (B > 0.0f && B != Float.POSITIVE_INFINITY) {
            B = i.a(B);
        }
        if (((double) B) <= 0.0d) {
            B = c;
        }
        return Math.max(C, Math.min(c, B));
    }

    public AxisDependency z() {
        return this.S;
    }

    public void a(float f2, float f3) {
        if (Math.abs(f3 - f2) == 0.0f) {
            f3 += 1.0f;
            f2 -= 1.0f;
        }
        float abs = Math.abs(f3 - f2);
        this.H = this.E ? this.H : f2 - ((abs / 100.0f) * D());
        float E = this.F ? this.G : f3 + ((abs / 100.0f) * E());
        this.G = E;
        this.I = Math.abs(this.H - E);
    }
}
