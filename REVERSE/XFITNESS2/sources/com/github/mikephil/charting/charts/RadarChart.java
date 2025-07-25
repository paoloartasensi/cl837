package com.github.mikephil.charting.charts;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.RectF;
import android.util.AttributeSet;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.h;
import com.github.mikephil.charting.data.n;
import h.a.a.a.e.b.j;
import h.a.a.a.h.s;
import h.a.a.a.h.v;
import h.a.a.a.i.i;

public class RadarChart extends PieRadarChartBase<n> {
    private float O = 2.5f;
    private float P = 1.5f;
    private int Q = Color.rgb(122, 122, 122);
    private int R = Color.rgb(122, 122, 122);
    private int S = 150;
    private boolean T = true;
    private int U = 0;
    private YAxis V;
    protected v W;
    protected s a0;

    public RadarChart(Context context) {
        super(context);
    }

    public int a(float f2) {
        float c = i.c(f2 - getRotationAngle());
        float sliceAngle = getSliceAngle();
        int X = ((j) ((n) this.f1326f).e()).X();
        int i2 = 0;
        while (i2 < X) {
            int i3 = i2 + 1;
            if ((((float) i3) * sliceAngle) - (sliceAngle / 2.0f) > c) {
                return i2;
            }
            i2 = i3;
        }
        return 0;
    }

    /* access modifiers changed from: protected */
    public void g() {
        super.g();
        this.V = new YAxis(YAxis.AxisDependency.LEFT);
        this.O = i.a(1.5f);
        this.P = i.a(0.75f);
        this.v = new h.a.a.a.h.n(this, this.y, this.x);
        this.W = new v(this.x, this.V, this);
        this.a0 = new s(this.x, this.m, this);
        this.w = new h.a.a.a.d.i(this);
    }

    public float getFactor() {
        RectF n = this.x.n();
        return Math.min(n.width() / 2.0f, n.height() / 2.0f) / this.V.I;
    }

    public float getRadius() {
        RectF n = this.x.n();
        return Math.min(n.width() / 2.0f, n.height() / 2.0f);
    }

    /* access modifiers changed from: protected */
    public float getRequiredBaseOffset() {
        if (!this.m.f() || !this.m.v()) {
            return i.a(10.0f);
        }
        return (float) this.m.L;
    }

    /* access modifiers changed from: protected */
    public float getRequiredLegendOffset() {
        return this.u.a().getTextSize() * 4.0f;
    }

    public int getSkipWebLineCount() {
        return this.U;
    }

    public float getSliceAngle() {
        return 360.0f / ((float) ((j) ((n) this.f1326f).e()).X());
    }

    public int getWebAlpha() {
        return this.S;
    }

    public int getWebColor() {
        return this.Q;
    }

    public int getWebColorInner() {
        return this.R;
    }

    public float getWebLineWidth() {
        return this.O;
    }

    public float getWebLineWidthInner() {
        return this.P;
    }

    public YAxis getYAxis() {
        return this.V;
    }

    public float getYChartMax() {
        return this.V.G;
    }

    public float getYChartMin() {
        return this.V.H;
    }

    public float getYRange() {
        return this.V.I;
    }

    public void l() {
        if (this.f1326f != null) {
            n();
            v vVar = this.W;
            YAxis yAxis = this.V;
            vVar.a(yAxis.H, yAxis.G, yAxis.K());
            s sVar = this.a0;
            XAxis xAxis = this.m;
            sVar.a(xAxis.H, xAxis.G, false);
            Legend legend = this.p;
            if (legend != null && !legend.z()) {
                this.u.a((h<?>) this.f1326f);
            }
            d();
        }
    }

    /* access modifiers changed from: protected */
    public void n() {
        super.n();
        this.V.a(((n) this.f1326f).b(YAxis.AxisDependency.LEFT), ((n) this.f1326f).a(YAxis.AxisDependency.LEFT));
        this.m.a(0.0f, (float) ((j) ((n) this.f1326f).e()).X());
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.f1326f != null) {
            if (this.m.f()) {
                s sVar = this.a0;
                XAxis xAxis = this.m;
                sVar.a(xAxis.H, xAxis.G, false);
            }
            this.a0.a(canvas);
            if (this.T) {
                this.v.b(canvas);
            }
            if (this.V.f() && this.V.w()) {
                this.W.e(canvas);
            }
            this.v.a(canvas);
            if (m()) {
                this.v.a(canvas, this.E);
            }
            if (this.V.f() && !this.V.w()) {
                this.W.e(canvas);
            }
            this.W.b(canvas);
            this.v.c(canvas);
            this.u.a(canvas);
            a(canvas);
            b(canvas);
        }
    }

    public void setDrawWeb(boolean z) {
        this.T = z;
    }

    public void setSkipWebLineCount(int i2) {
        this.U = Math.max(0, i2);
    }

    public void setWebAlpha(int i2) {
        this.S = i2;
    }

    public void setWebColor(int i2) {
        this.Q = i2;
    }

    public void setWebColorInner(int i2) {
        this.R = i2;
    }

    public void setWebLineWidth(float f2) {
        this.O = i.a(f2);
    }

    public void setWebLineWidthInner(float f2) {
        this.P = i.a(f2);
    }

    public RadarChart(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public RadarChart(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
    }
}
