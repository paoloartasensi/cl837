package com.github.mikephil.charting.charts;

import android.content.Context;
import android.util.AttributeSet;
import com.github.mikephil.charting.data.j;
import h.a.a.a.e.a.g;

public class LineChart extends BarLineChartBase<j> implements g {
    public LineChart(Context context) {
        super(context);
    }

    /* access modifiers changed from: protected */
    public void g() {
        super.g();
        this.v = new h.a.a.a.h.j(this, this.y, this.x);
    }

    public j getLineData() {
        return (j) this.f1326f;
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        h.a.a.a.h.g gVar = this.v;
        if (gVar != null && (gVar instanceof h.a.a.a.h.j)) {
            ((h.a.a.a.h.j) gVar).b();
        }
        super.onDetachedFromWindow();
    }

    public LineChart(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public LineChart(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
    }
}
