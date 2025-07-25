package com.github.mikephil.charting.charts;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.a;
import com.github.mikephil.charting.data.g;
import com.github.mikephil.charting.data.i;
import com.github.mikephil.charting.data.j;
import com.github.mikephil.charting.data.o;
import h.a.a.a.d.c;
import h.a.a.a.d.d;
import h.a.a.a.e.a.f;
import h.a.a.a.e.b.b;

public class CombinedChart extends BarLineChartBase<i> implements f {
    private boolean t0 = true;
    protected boolean u0 = false;
    private boolean v0 = false;
    protected DrawOrder[] w0;

    public enum DrawOrder {
        BAR,
        BUBBLE,
        LINE,
        CANDLE,
        SCATTER
    }

    public CombinedChart(Context context) {
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
        return this.t0;
    }

    public boolean c() {
        return this.u0;
    }

    /* access modifiers changed from: protected */
    public void g() {
        super.g();
        this.w0 = new DrawOrder[]{DrawOrder.BAR, DrawOrder.BUBBLE, DrawOrder.LINE, DrawOrder.CANDLE, DrawOrder.SCATTER};
        setHighlighter(new c(this, this));
        setHighlightFullBarEnabled(true);
        this.v = new h.a.a.a.h.f(this, this.y, this.x);
    }

    public a getBarData() {
        T t = this.f1326f;
        if (t == null) {
            return null;
        }
        return ((i) t).l();
    }

    public com.github.mikephil.charting.data.f getBubbleData() {
        T t = this.f1326f;
        if (t == null) {
            return null;
        }
        return ((i) t).m();
    }

    public g getCandleData() {
        T t = this.f1326f;
        if (t == null) {
            return null;
        }
        return ((i) t).n();
    }

    public i getCombinedData() {
        return (i) this.f1326f;
    }

    public DrawOrder[] getDrawOrder() {
        return this.w0;
    }

    public j getLineData() {
        T t = this.f1326f;
        if (t == null) {
            return null;
        }
        return ((i) t).o();
    }

    public o getScatterData() {
        T t = this.f1326f;
        if (t == null) {
            return null;
        }
        return ((i) t).p();
    }

    public void setDrawBarShadow(boolean z) {
        this.v0 = z;
    }

    public void setDrawOrder(DrawOrder[] drawOrderArr) {
        if (drawOrderArr != null && drawOrderArr.length > 0) {
            this.w0 = drawOrderArr;
        }
    }

    public void setDrawValueAboveBar(boolean z) {
        this.t0 = z;
    }

    public void setHighlightFullBarEnabled(boolean z) {
        this.u0 = z;
    }

    /* access modifiers changed from: protected */
    public void b(Canvas canvas) {
        if (this.H != null && i() && m()) {
            int i2 = 0;
            while (true) {
                d[] dVarArr = this.E;
                if (i2 < dVarArr.length) {
                    d dVar = dVarArr[i2];
                    b<? extends Entry> b = ((i) this.f1326f).b(dVar);
                    Entry a = ((i) this.f1326f).a(dVar);
                    if (a != null && ((float) b.b(a)) <= ((float) b.X()) * this.y.a()) {
                        float[] a2 = a(dVar);
                        if (this.x.a(a2[0], a2[1])) {
                            this.H.a(a, dVar);
                            this.H.a(canvas, a2[0], a2[1]);
                        }
                    }
                    i2++;
                } else {
                    return;
                }
            }
        }
    }

    public void setData(i iVar) {
        super.setData(iVar);
        setHighlighter(new c(this, this));
        ((h.a.a.a.h.f) this.v).b();
        this.v.a();
    }

    public CombinedChart(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public boolean a() {
        return this.v0;
    }

    public CombinedChart(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
    }
}
