package com.github.mikephil.charting.charts;

import android.content.Context;
import android.util.AttributeSet;
import com.github.mikephil.charting.data.o;
import h.a.a.a.e.a.h;
import h.a.a.a.h.p;

public class ScatterChart extends BarLineChartBase<o> implements h {
    public ScatterChart(Context context) {
        super(context);
    }

    /* access modifiers changed from: protected */
    public void g() {
        super.g();
        this.v = new p(this, this.y, this.x);
        getXAxis().e(0.5f);
        getXAxis().d(0.5f);
    }

    public o getScatterData() {
        return (o) this.f1326f;
    }

    public ScatterChart(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public ScatterChart(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
    }
}
