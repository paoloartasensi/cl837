package com.github.mikephil.charting.charts;

import android.content.Context;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import h.a.a.a.d.d;
import h.a.a.a.d.e;
import h.a.a.a.h.r;
import h.a.a.a.h.u;
import h.a.a.a.i.c;
import h.a.a.a.i.g;
import h.a.a.a.i.h;
import h.a.a.a.i.i;

public class HorizontalBarChart extends BarChart {
    private RectF x0 = new RectF();

    public HorizontalBarChart(Context context) {
        super(context);
    }

    /* access modifiers changed from: protected */
    public void C() {
        g gVar = this.j0;
        YAxis yAxis = this.f0;
        float f2 = yAxis.H;
        float f3 = yAxis.I;
        XAxis xAxis = this.m;
        gVar.a(f2, f3, xAxis.I, xAxis.H);
        g gVar2 = this.i0;
        YAxis yAxis2 = this.e0;
        float f4 = yAxis2.H;
        float f5 = yAxis2.I;
        XAxis xAxis2 = this.m;
        gVar2.a(f4, f5, xAxis2.I, xAxis2.H);
    }

    /* access modifiers changed from: protected */
    public float[] a(d dVar) {
        return new float[]{dVar.e(), dVar.d()};
    }

    public void d() {
        a(this.x0);
        RectF rectF = this.x0;
        float f2 = rectF.left + 0.0f;
        float f3 = rectF.top + 0.0f;
        float f4 = rectF.right + 0.0f;
        float f5 = rectF.bottom + 0.0f;
        if (this.e0.L()) {
            f3 += this.e0.a(this.g0.a());
        }
        if (this.f0.L()) {
            f5 += this.f0.a(this.h0.a());
        }
        XAxis xAxis = this.m;
        float f6 = (float) xAxis.L;
        if (xAxis.f()) {
            if (this.m.A() == XAxis.XAxisPosition.BOTTOM) {
                f2 += f6;
            } else {
                if (this.m.A() != XAxis.XAxisPosition.TOP) {
                    if (this.m.A() == XAxis.XAxisPosition.BOTH_SIDED) {
                        f2 += f6;
                    }
                }
                f4 += f6;
            }
        }
        float extraTopOffset = f3 + getExtraTopOffset();
        float extraRightOffset = f4 + getExtraRightOffset();
        float extraBottomOffset = f5 + getExtraBottomOffset();
        float extraLeftOffset = f2 + getExtraLeftOffset();
        float a = i.a(this.b0);
        this.x.a(Math.max(a, extraLeftOffset), Math.max(a, extraTopOffset), Math.max(a, extraRightOffset), Math.max(a, extraBottomOffset));
        if (this.e) {
            Log.i("MPAndroidChart", "offsetLeft: " + extraLeftOffset + ", offsetTop: " + extraTopOffset + ", offsetRight: " + extraRightOffset + ", offsetBottom: " + extraBottomOffset);
            StringBuilder sb = new StringBuilder();
            sb.append("Content: ");
            sb.append(this.x.n().toString());
            Log.i("MPAndroidChart", sb.toString());
        }
        B();
        C();
    }

    /* access modifiers changed from: protected */
    public void g() {
        this.x = new c();
        super.g();
        this.i0 = new h(this.x);
        this.j0 = new h(this.x);
        this.v = new h.a.a.a.h.h(this, this.y, this.x);
        setHighlighter(new e(this));
        this.g0 = new u(this.x, this.e0, this.i0);
        this.h0 = new u(this.x, this.f0, this.j0);
        this.k0 = new r(this.x, this.m, this.i0, this);
    }

    public float getHighestVisibleX() {
        b(YAxis.AxisDependency.LEFT).a(this.x.g(), this.x.i(), this.r0);
        return (float) Math.min((double) this.m.G, this.r0.f1725h);
    }

    public float getLowestVisibleX() {
        b(YAxis.AxisDependency.LEFT).a(this.x.g(), this.x.e(), this.q0);
        return (float) Math.max((double) this.m.H, this.q0.f1725h);
    }

    public void setVisibleXRangeMaximum(float f2) {
        this.x.l(this.m.I / f2);
    }

    public void setVisibleXRangeMinimum(float f2) {
        this.x.j(this.m.I / f2);
    }

    public d a(float f2, float f3) {
        if (this.f1326f != null) {
            return getHighlighter().a(f3, f2);
        }
        if (!this.e) {
            return null;
        }
        Log.e("MPAndroidChart", "Can't select by touch. No data set.");
        return null;
    }

    public HorizontalBarChart(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public HorizontalBarChart(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
    }
}
