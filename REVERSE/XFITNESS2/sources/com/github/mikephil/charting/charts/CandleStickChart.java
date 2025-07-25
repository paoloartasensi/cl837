package com.github.mikephil.charting.charts;

import android.content.Context;
import android.util.AttributeSet;
import com.github.mikephil.charting.data.g;
import h.a.a.a.e.a.d;
import h.a.a.a.h.e;

public class CandleStickChart extends BarLineChartBase<g> implements d {
    public CandleStickChart(Context context) {
        super(context);
    }

    /* access modifiers changed from: protected */
    public void g() {
        super.g();
        this.v = new e(this, this.y, this.x);
        getXAxis().e(0.5f);
        getXAxis().d(0.5f);
    }

    public g getCandleData() {
        return (g) this.f1326f;
    }

    public CandleStickChart(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public CandleStickChart(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
    }
}
