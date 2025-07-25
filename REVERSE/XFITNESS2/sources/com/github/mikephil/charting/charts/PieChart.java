package com.github.mikephil.charting.charts;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.data.m;
import com.jeremyliao.liveeventbus.BuildConfig;
import h.a.a.a.d.d;
import h.a.a.a.d.g;
import h.a.a.a.e.b.i;
import h.a.a.a.i.e;
import java.util.List;

public class PieChart extends PieRadarChartBase<m> {
    private RectF O = new RectF();
    private boolean P = true;
    private float[] Q = new float[1];
    private float[] R = new float[1];
    private boolean S = true;
    private boolean T = false;
    private boolean U = false;
    private boolean V = false;
    private CharSequence W = BuildConfig.FLAVOR;
    private e a0 = e.a(0.0f, 0.0f);
    private float b0 = 50.0f;
    protected float c0 = 55.0f;
    private boolean d0 = true;
    private float e0 = 100.0f;
    protected float f0 = 360.0f;
    private float g0 = 0.0f;

    public PieChart(Context context) {
        super(context);
    }

    private float e(float f2, float f3) {
        return (f2 / f3) * this.f0;
    }

    private void v() {
        int d = ((m) this.f1326f).d();
        if (this.Q.length != d) {
            this.Q = new float[d];
        } else {
            for (int i2 = 0; i2 < d; i2++) {
                this.Q[i2] = 0.0f;
            }
        }
        if (this.R.length != d) {
            this.R = new float[d];
        } else {
            for (int i3 = 0; i3 < d; i3++) {
                this.R[i3] = 0.0f;
            }
        }
        float l = ((m) this.f1326f).l();
        List c = ((m) this.f1326f).c();
        float f2 = this.g0;
        boolean z = f2 != 0.0f && ((float) d) * f2 <= this.f0;
        float[] fArr = new float[d];
        float f3 = 0.0f;
        float f4 = 0.0f;
        int i4 = 0;
        for (int i5 = 0; i5 < ((m) this.f1326f).b(); i5++) {
            i iVar = (i) c.get(i5);
            for (int i6 = 0; i6 < iVar.X(); i6++) {
                float e = e(Math.abs(((PieEntry) iVar.c(i6)).c()), l);
                if (z) {
                    float f5 = this.g0;
                    float f6 = e - f5;
                    if (f6 <= 0.0f) {
                        fArr[i4] = f5;
                        f3 += -f6;
                    } else {
                        fArr[i4] = e;
                        f4 += f6;
                    }
                }
                float[] fArr2 = this.Q;
                fArr2[i4] = e;
                if (i4 == 0) {
                    this.R[i4] = fArr2[i4];
                } else {
                    float[] fArr3 = this.R;
                    fArr3[i4] = fArr3[i4 - 1] + fArr2[i4];
                }
                i4++;
            }
        }
        if (z) {
            for (int i7 = 0; i7 < d; i7++) {
                fArr[i7] = fArr[i7] - (((fArr[i7] - this.g0) / f4) * f3);
                if (i7 == 0) {
                    this.R[0] = fArr[0];
                } else {
                    float[] fArr4 = this.R;
                    fArr4[i7] = fArr4[i7 - 1] + fArr[i7];
                }
            }
            this.Q = fArr;
        }
    }

    /* access modifiers changed from: protected */
    public float[] a(d dVar) {
        e centerCircleBox = getCenterCircleBox();
        float radius = getRadius();
        float f2 = (radius / 10.0f) * 3.6f;
        if (r()) {
            f2 = (radius - ((radius / 100.0f) * getHoleRadius())) / 2.0f;
        }
        float f3 = radius - f2;
        float rotationAngle = getRotationAngle();
        int g2 = (int) dVar.g();
        float f4 = this.Q[g2] / 2.0f;
        double d = (double) f3;
        double cos = Math.cos(Math.toRadians((double) (((this.R[g2] + rotationAngle) - f4) * this.y.b())));
        Double.isNaN(d);
        double d2 = (double) centerCircleBox.f1727g;
        Double.isNaN(d2);
        float f5 = (float) ((cos * d) + d2);
        double sin = Math.sin(Math.toRadians((double) (((rotationAngle + this.R[g2]) - f4) * this.y.b())));
        Double.isNaN(d);
        double d3 = d * sin;
        double d4 = (double) centerCircleBox.f1728h;
        Double.isNaN(d4);
        e.b(centerCircleBox);
        return new float[]{f5, (float) (d3 + d4)};
    }

    public void d() {
        super.d();
        if (this.f1326f != null) {
            float diameter = getDiameter() / 2.0f;
            e centerOffsets = getCenterOffsets();
            float s = ((m) this.f1326f).k().s();
            RectF rectF = this.O;
            float f2 = centerOffsets.f1727g;
            float f3 = centerOffsets.f1728h;
            rectF.set((f2 - diameter) + s, (f3 - diameter) + s, (f2 + diameter) - s, (f3 + diameter) - s);
            e.b(centerOffsets);
        }
    }

    /* access modifiers changed from: protected */
    public void g() {
        super.g();
        this.v = new h.a.a.a.h.m(this, this.y, this.x);
        this.m = null;
        this.w = new g(this);
    }

    public float[] getAbsoluteAngles() {
        return this.R;
    }

    public e getCenterCircleBox() {
        return e.a(this.O.centerX(), this.O.centerY());
    }

    public CharSequence getCenterText() {
        return this.W;
    }

    public e getCenterTextOffset() {
        e eVar = this.a0;
        return e.a(eVar.f1727g, eVar.f1728h);
    }

    public float getCenterTextRadiusPercent() {
        return this.e0;
    }

    public RectF getCircleBox() {
        return this.O;
    }

    public float[] getDrawAngles() {
        return this.Q;
    }

    public float getHoleRadius() {
        return this.b0;
    }

    public float getMaxAngle() {
        return this.f0;
    }

    public float getMinAngleForSlices() {
        return this.g0;
    }

    public float getRadius() {
        RectF rectF = this.O;
        if (rectF == null) {
            return 0.0f;
        }
        return Math.min(rectF.width() / 2.0f, this.O.height() / 2.0f);
    }

    /* access modifiers changed from: protected */
    public float getRequiredBaseOffset() {
        return 0.0f;
    }

    /* access modifiers changed from: protected */
    public float getRequiredLegendOffset() {
        return this.u.a().getTextSize() * 2.0f;
    }

    public float getTransparentCircleRadius() {
        return this.c0;
    }

    @Deprecated
    public XAxis getXAxis() {
        throw new RuntimeException("PieChart has no XAxis");
    }

    /* access modifiers changed from: protected */
    public void n() {
        v();
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        h.a.a.a.h.g gVar = this.v;
        if (gVar != null && (gVar instanceof h.a.a.a.h.m)) {
            ((h.a.a.a.h.m) gVar).f();
        }
        super.onDetachedFromWindow();
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.f1326f != null) {
            this.v.a(canvas);
            if (m()) {
                this.v.a(canvas, this.E);
            }
            this.v.b(canvas);
            this.v.c(canvas);
            this.u.a(canvas);
            a(canvas);
            b(canvas);
        }
    }

    public boolean p() {
        return this.d0;
    }

    public boolean q() {
        return this.P;
    }

    public boolean r() {
        return this.S;
    }

    public boolean s() {
        return this.V;
    }

    public void setCenterText(CharSequence charSequence) {
        if (charSequence == null) {
            this.W = BuildConfig.FLAVOR;
        } else {
            this.W = charSequence;
        }
    }

    public void setCenterTextColor(int i2) {
        ((h.a.a.a.h.m) this.v).b().setColor(i2);
    }

    public void setCenterTextRadiusPercent(float f2) {
        this.e0 = f2;
    }

    public void setCenterTextSize(float f2) {
        ((h.a.a.a.h.m) this.v).b().setTextSize(h.a.a.a.i.i.a(f2));
    }

    public void setCenterTextSizePixels(float f2) {
        ((h.a.a.a.h.m) this.v).b().setTextSize(f2);
    }

    public void setCenterTextTypeface(Typeface typeface) {
        ((h.a.a.a.h.m) this.v).b().setTypeface(typeface);
    }

    public void setDrawCenterText(boolean z) {
        this.d0 = z;
    }

    public void setDrawEntryLabels(boolean z) {
        this.P = z;
    }

    public void setDrawHoleEnabled(boolean z) {
        this.S = z;
    }

    public void setDrawRoundedSlices(boolean z) {
        this.V = z;
    }

    @Deprecated
    public void setDrawSliceText(boolean z) {
        this.P = z;
    }

    public void setDrawSlicesUnderHole(boolean z) {
        this.T = z;
    }

    public void setEntryLabelColor(int i2) {
        ((h.a.a.a.h.m) this.v).c().setColor(i2);
    }

    public void setEntryLabelTextSize(float f2) {
        ((h.a.a.a.h.m) this.v).c().setTextSize(h.a.a.a.i.i.a(f2));
    }

    public void setEntryLabelTypeface(Typeface typeface) {
        ((h.a.a.a.h.m) this.v).c().setTypeface(typeface);
    }

    public void setHoleColor(int i2) {
        ((h.a.a.a.h.m) this.v).d().setColor(i2);
    }

    public void setHoleRadius(float f2) {
        this.b0 = f2;
    }

    public void setMaxAngle(float f2) {
        if (f2 > 360.0f) {
            f2 = 360.0f;
        }
        if (f2 < 90.0f) {
            f2 = 90.0f;
        }
        this.f0 = f2;
    }

    public void setMinAngleForSlices(float f2) {
        float f3 = this.f0;
        if (f2 > f3 / 2.0f) {
            f2 = f3 / 2.0f;
        } else if (f2 < 0.0f) {
            f2 = 0.0f;
        }
        this.g0 = f2;
    }

    public void setTransparentCircleAlpha(int i2) {
        ((h.a.a.a.h.m) this.v).e().setAlpha(i2);
    }

    public void setTransparentCircleColor(int i2) {
        Paint e = ((h.a.a.a.h.m) this.v).e();
        int alpha = e.getAlpha();
        e.setColor(i2);
        e.setAlpha(alpha);
    }

    public void setTransparentCircleRadius(float f2) {
        this.c0 = f2;
    }

    public void setUsePercentValues(boolean z) {
        this.U = z;
    }

    public boolean t() {
        return this.T;
    }

    public boolean u() {
        return this.U;
    }

    public boolean a(int i2) {
        if (!m()) {
            return false;
        }
        int i3 = 0;
        while (true) {
            d[] dVarArr = this.E;
            if (i3 >= dVarArr.length) {
                return false;
            }
            if (((int) dVarArr[i3].g()) == i2) {
                return true;
            }
            i3++;
        }
    }

    public PieChart(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public int a(float f2) {
        float c = h.a.a.a.i.i.c(f2 - getRotationAngle());
        int i2 = 0;
        while (true) {
            float[] fArr = this.R;
            if (i2 >= fArr.length) {
                return -1;
            }
            if (fArr[i2] > c) {
                return i2;
            }
            i2++;
        }
    }

    public PieChart(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
    }
}
