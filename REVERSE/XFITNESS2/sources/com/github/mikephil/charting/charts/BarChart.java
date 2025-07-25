package com.github.mikephil.charting.charts;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.a;
import h.a.a.a.d.d;
import h.a.a.a.h.b;

public class BarChart extends BarLineChartBase<a> implements h.a.a.a.e.a.a {
    protected boolean t0 = false;
    private boolean u0 = true;
    private boolean v0 = false;
    private boolean w0 = false;

    public BarChart(Context context) {
        super(context);
    }

    public d a(float f2, float f3) {
        if (this.f1326f == null) {
            Log.e("MPAndroidChart", "Can't select by touch. No data set.");
            return null;
        }
        d a = getHighlighter().a(f2, f3);
        return (a == null || !c()) ? a : new d(a.g(), a.i(), a.h(), a.j(), a.c(), -1, a.a());
    }

    public boolean b() {
        return this.u0;
    }

    public boolean c() {
        return this.t0;
    }

    /* access modifiers changed from: protected */
    public void g() {
        super.g();
        this.v = new b(this, this.y, this.x);
        setHighlighter(new h.a.a.a.d.a(this));
        getXAxis().e(0.5f);
        getXAxis().d(0.5f);
    }

    public a getBarData() {
        return (a) this.f1326f;
    }

    /* access modifiers changed from: protected */
    public void o() {
        if (this.w0) {
            this.m.a(((a) this.f1326f).g() - (((a) this.f1326f).k() / 2.0f), ((a) this.f1326f).f() + (((a) this.f1326f).k() / 2.0f));
        } else {
            this.m.a(((a) this.f1326f).g(), ((a) this.f1326f).f());
        }
        this.e0.a(((a) this.f1326f).b(YAxis.AxisDependency.LEFT), ((a) this.f1326f).a(YAxis.AxisDependency.LEFT));
        this.f0.a(((a) this.f1326f).b(YAxis.AxisDependency.RIGHT), ((a) this.f1326f).a(YAxis.AxisDependency.RIGHT));
    }

    public void setDrawBarShadow(boolean z) {
        this.v0 = z;
    }

    public void setDrawValueAboveBar(boolean z) {
        this.u0 = z;
    }

    public void setFitBars(boolean z) {
        this.w0 = z;
    }

    public void setHighlightFullBarEnabled(boolean z) {
        this.t0 = z;
    }

    public BarChart(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public boolean a() {
        return this.v0;
    }

    public BarChart(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
    }
}
