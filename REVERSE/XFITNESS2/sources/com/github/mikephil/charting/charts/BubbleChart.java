package com.github.mikephil.charting.charts;

import android.content.Context;
import android.util.AttributeSet;
import com.github.mikephil.charting.data.f;
import h.a.a.a.e.a.c;
import h.a.a.a.h.d;

public class BubbleChart extends BarLineChartBase<f> implements c {
    public BubbleChart(Context context) {
        super(context);
    }

    /* access modifiers changed from: protected */
    public void g() {
        super.g();
        this.v = new d(this, this.y, this.x);
    }

    public f getBubbleData() {
        return (f) this.f1326f;
    }

    public BubbleChart(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public BubbleChart(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
    }
}
