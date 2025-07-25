package com.github.mikephil.charting.charts;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.b;
import com.github.mikephil.charting.data.h;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.d;
import h.a.a.a.h.q;
import h.a.a.a.h.t;
import h.a.a.a.i.g;
import h.a.a.a.i.i;
import h.a.a.a.i.j;

@SuppressLint({"RtlHardcoded"})
public abstract class BarLineChartBase<T extends b<? extends h.a.a.a.e.b.b<? extends Entry>>> extends Chart<T> implements h.a.a.a.e.a.b {
    protected int K = 100;
    protected boolean L = false;
    protected boolean M = false;
    protected boolean N = true;
    protected boolean O = true;
    private boolean P = true;
    private boolean Q = true;
    private boolean R = true;
    private boolean S = true;
    protected Paint T;
    protected Paint U;
    protected boolean V = false;
    protected boolean W = false;
    protected boolean a0 = false;
    protected float b0 = 15.0f;
    protected boolean c0 = false;
    protected d d0;
    protected YAxis e0;
    protected YAxis f0;
    protected t g0;
    protected t h0;
    protected g i0;
    protected g j0;
    protected q k0;
    private long l0 = 0;
    private long m0 = 0;
    private RectF n0 = new RectF();
    protected Matrix o0 = new Matrix();
    private boolean p0;
    protected h.a.a.a.i.d q0;
    protected h.a.a.a.i.d r0;
    protected float[] s0;

    static /* synthetic */ class a {
        static final /* synthetic */ int[] a;
        static final /* synthetic */ int[] b;
        static final /* synthetic */ int[] c;

        /* JADX WARNING: Can't wrap try/catch for region: R(16:0|(2:1|2)|3|(2:5|6)|7|9|10|11|12|(2:13|14)|15|17|18|19|20|22) */
        /* JADX WARNING: Code restructure failed: missing block: B:23:?, code lost:
            return;
         */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:11:0x002e */
        /* JADX WARNING: Missing exception handler attribute for start block: B:13:0x0038 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:19:0x0054 */
        static {
            /*
                com.github.mikephil.charting.components.Legend$LegendOrientation[] r0 = com.github.mikephil.charting.components.Legend.LegendOrientation.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                c = r0
                r1 = 1
                com.github.mikephil.charting.components.Legend$LegendOrientation r2 = com.github.mikephil.charting.components.Legend.LegendOrientation.VERTICAL     // Catch:{ NoSuchFieldError -> 0x0012 }
                int r2 = r2.ordinal()     // Catch:{ NoSuchFieldError -> 0x0012 }
                r0[r2] = r1     // Catch:{ NoSuchFieldError -> 0x0012 }
            L_0x0012:
                r0 = 2
                int[] r2 = c     // Catch:{ NoSuchFieldError -> 0x001d }
                com.github.mikephil.charting.components.Legend$LegendOrientation r3 = com.github.mikephil.charting.components.Legend.LegendOrientation.HORIZONTAL     // Catch:{ NoSuchFieldError -> 0x001d }
                int r3 = r3.ordinal()     // Catch:{ NoSuchFieldError -> 0x001d }
                r2[r3] = r0     // Catch:{ NoSuchFieldError -> 0x001d }
            L_0x001d:
                com.github.mikephil.charting.components.Legend$LegendHorizontalAlignment[] r2 = com.github.mikephil.charting.components.Legend.LegendHorizontalAlignment.values()
                int r2 = r2.length
                int[] r2 = new int[r2]
                b = r2
                com.github.mikephil.charting.components.Legend$LegendHorizontalAlignment r3 = com.github.mikephil.charting.components.Legend.LegendHorizontalAlignment.LEFT     // Catch:{ NoSuchFieldError -> 0x002e }
                int r3 = r3.ordinal()     // Catch:{ NoSuchFieldError -> 0x002e }
                r2[r3] = r1     // Catch:{ NoSuchFieldError -> 0x002e }
            L_0x002e:
                int[] r2 = b     // Catch:{ NoSuchFieldError -> 0x0038 }
                com.github.mikephil.charting.components.Legend$LegendHorizontalAlignment r3 = com.github.mikephil.charting.components.Legend.LegendHorizontalAlignment.RIGHT     // Catch:{ NoSuchFieldError -> 0x0038 }
                int r3 = r3.ordinal()     // Catch:{ NoSuchFieldError -> 0x0038 }
                r2[r3] = r0     // Catch:{ NoSuchFieldError -> 0x0038 }
            L_0x0038:
                int[] r2 = b     // Catch:{ NoSuchFieldError -> 0x0043 }
                com.github.mikephil.charting.components.Legend$LegendHorizontalAlignment r3 = com.github.mikephil.charting.components.Legend.LegendHorizontalAlignment.CENTER     // Catch:{ NoSuchFieldError -> 0x0043 }
                int r3 = r3.ordinal()     // Catch:{ NoSuchFieldError -> 0x0043 }
                r4 = 3
                r2[r3] = r4     // Catch:{ NoSuchFieldError -> 0x0043 }
            L_0x0043:
                com.github.mikephil.charting.components.Legend$LegendVerticalAlignment[] r2 = com.github.mikephil.charting.components.Legend.LegendVerticalAlignment.values()
                int r2 = r2.length
                int[] r2 = new int[r2]
                a = r2
                com.github.mikephil.charting.components.Legend$LegendVerticalAlignment r3 = com.github.mikephil.charting.components.Legend.LegendVerticalAlignment.TOP     // Catch:{ NoSuchFieldError -> 0x0054 }
                int r3 = r3.ordinal()     // Catch:{ NoSuchFieldError -> 0x0054 }
                r2[r3] = r1     // Catch:{ NoSuchFieldError -> 0x0054 }
            L_0x0054:
                int[] r1 = a     // Catch:{ NoSuchFieldError -> 0x005e }
                com.github.mikephil.charting.components.Legend$LegendVerticalAlignment r2 = com.github.mikephil.charting.components.Legend.LegendVerticalAlignment.BOTTOM     // Catch:{ NoSuchFieldError -> 0x005e }
                int r2 = r2.ordinal()     // Catch:{ NoSuchFieldError -> 0x005e }
                r1[r2] = r0     // Catch:{ NoSuchFieldError -> 0x005e }
            L_0x005e:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.github.mikephil.charting.charts.BarLineChartBase.a.<clinit>():void");
        }
    }

    public BarLineChartBase(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        new Matrix();
        this.p0 = false;
        this.q0 = h.a.a.a.i.d.a(0.0d, 0.0d);
        this.r0 = h.a.a.a.i.d.a(0.0d, 0.0d);
        this.s0 = new float[2];
    }

    public boolean A() {
        return this.S;
    }

    /* access modifiers changed from: protected */
    public void B() {
        this.j0.a(this.f0.K());
        this.i0.a(this.e0.K());
    }

    /* access modifiers changed from: protected */
    public void C() {
        if (this.e) {
            Log.i("MPAndroidChart", "Preparing Value-Px Matrix, xmin: " + this.m.H + ", xmax: " + this.m.G + ", xdelta: " + this.m.I);
        }
        g gVar = this.j0;
        XAxis xAxis = this.m;
        float f2 = xAxis.H;
        float f3 = xAxis.I;
        YAxis yAxis = this.f0;
        gVar.a(f2, f3, yAxis.I, yAxis.H);
        g gVar2 = this.i0;
        XAxis xAxis2 = this.m;
        float f4 = xAxis2.H;
        float f5 = xAxis2.I;
        YAxis yAxis2 = this.e0;
        gVar2.a(f4, f5, yAxis2.I, yAxis2.H);
    }

    public void D() {
        this.l0 = 0;
        this.m0 = 0;
    }

    /* access modifiers changed from: protected */
    public void a(RectF rectF) {
        rectF.left = 0.0f;
        rectF.right = 0.0f;
        rectF.top = 0.0f;
        rectF.bottom = 0.0f;
        Legend legend = this.p;
        if (legend != null && legend.f() && !this.p.y()) {
            int i2 = a.c[this.p.t().ordinal()];
            if (i2 == 1) {
                int i3 = a.b[this.p.r().ordinal()];
                if (i3 == 1) {
                    rectF.left += Math.min(this.p.x, this.x.l() * this.p.s()) + this.p.d();
                } else if (i3 == 2) {
                    rectF.right += Math.min(this.p.x, this.x.l() * this.p.s()) + this.p.d();
                } else if (i3 == 3) {
                    int i4 = a.a[this.p.v().ordinal()];
                    if (i4 == 1) {
                        rectF.top += Math.min(this.p.y, this.x.k() * this.p.s()) + this.p.e();
                    } else if (i4 == 2) {
                        rectF.bottom += Math.min(this.p.y, this.x.k() * this.p.s()) + this.p.e();
                    }
                }
            } else if (i2 == 2) {
                int i5 = a.a[this.p.v().ordinal()];
                if (i5 == 1) {
                    rectF.top += Math.min(this.p.y, this.x.k() * this.p.s()) + this.p.e();
                } else if (i5 == 2) {
                    rectF.bottom += Math.min(this.p.y, this.x.k() * this.p.s()) + this.p.e();
                }
            }
        }
    }

    public g b(YAxis.AxisDependency axisDependency) {
        if (axisDependency == YAxis.AxisDependency.LEFT) {
            return this.i0;
        }
        return this.j0;
    }

    /* access modifiers changed from: protected */
    public void c(Canvas canvas) {
        if (this.V) {
            canvas.drawRect(this.x.n(), this.T);
        }
        if (this.W) {
            canvas.drawRect(this.x.n(), this.U);
        }
    }

    public void computeScroll() {
        ChartTouchListener chartTouchListener = this.r;
        if (chartTouchListener instanceof com.github.mikephil.charting.listener.a) {
            ((com.github.mikephil.charting.listener.a) chartTouchListener).a();
        }
    }

    public void d() {
        if (!this.p0) {
            a(this.n0);
            RectF rectF = this.n0;
            float f2 = rectF.left + 0.0f;
            float f3 = rectF.top + 0.0f;
            float f4 = rectF.right + 0.0f;
            float f5 = rectF.bottom + 0.0f;
            if (this.e0.L()) {
                f2 += this.e0.b(this.g0.a());
            }
            if (this.f0.L()) {
                f4 += this.f0.b(this.h0.a());
            }
            if (this.m.f() && this.m.v()) {
                XAxis xAxis = this.m;
                float e = ((float) xAxis.M) + xAxis.e();
                if (this.m.A() == XAxis.XAxisPosition.BOTTOM) {
                    f5 += e;
                } else {
                    if (this.m.A() != XAxis.XAxisPosition.TOP) {
                        if (this.m.A() == XAxis.XAxisPosition.BOTH_SIDED) {
                            f5 += e;
                        }
                    }
                    f3 += e;
                }
            }
            float extraTopOffset = f3 + getExtraTopOffset();
            float extraRightOffset = f4 + getExtraRightOffset();
            float extraBottomOffset = f5 + getExtraBottomOffset();
            float extraLeftOffset = f2 + getExtraLeftOffset();
            float a2 = i.a(this.b0);
            this.x.a(Math.max(a2, extraLeftOffset), Math.max(a2, extraTopOffset), Math.max(a2, extraRightOffset), Math.max(a2, extraBottomOffset));
            if (this.e) {
                Log.i("MPAndroidChart", "offsetLeft: " + extraLeftOffset + ", offsetTop: " + extraTopOffset + ", offsetRight: " + extraRightOffset + ", offsetBottom: " + extraBottomOffset);
                StringBuilder sb = new StringBuilder();
                sb.append("Content: ");
                sb.append(this.x.n().toString());
                Log.i("MPAndroidChart", sb.toString());
            }
        }
        B();
        C();
    }

    /* access modifiers changed from: protected */
    public void g() {
        super.g();
        this.e0 = new YAxis(YAxis.AxisDependency.LEFT);
        this.f0 = new YAxis(YAxis.AxisDependency.RIGHT);
        this.i0 = new g(this.x);
        this.j0 = new g(this.x);
        this.g0 = new t(this.x, this.e0, this.i0);
        this.h0 = new t(this.x, this.f0, this.j0);
        this.k0 = new q(this.x, this.m, this.i0);
        setHighlighter(new h.a.a.a.d.b(this));
        this.r = new com.github.mikephil.charting.listener.a(this, this.x.o(), 3.0f);
        Paint paint = new Paint();
        this.T = paint;
        paint.setStyle(Paint.Style.FILL);
        this.T.setColor(Color.rgb(240, 240, 240));
        Paint paint2 = new Paint();
        this.U = paint2;
        paint2.setStyle(Paint.Style.STROKE);
        this.U.setColor(-16777216);
        this.U.setStrokeWidth(i.a(1.0f));
    }

    public YAxis getAxisLeft() {
        return this.e0;
    }

    public YAxis getAxisRight() {
        return this.f0;
    }

    public /* bridge */ /* synthetic */ b getData() {
        return (b) super.getData();
    }

    public d getDrawListener() {
        return this.d0;
    }

    public float getHighestVisibleX() {
        b(YAxis.AxisDependency.LEFT).a(this.x.h(), this.x.e(), this.r0);
        return (float) Math.min((double) this.m.G, this.r0.f1724g);
    }

    public float getLowestVisibleX() {
        b(YAxis.AxisDependency.LEFT).a(this.x.g(), this.x.e(), this.q0);
        return (float) Math.max((double) this.m.H, this.q0.f1724g);
    }

    public int getMaxVisibleCount() {
        return this.K;
    }

    public float getMinOffset() {
        return this.b0;
    }

    public t getRendererLeftYAxis() {
        return this.g0;
    }

    public t getRendererRightYAxis() {
        return this.h0;
    }

    public q getRendererXAxis() {
        return this.k0;
    }

    public float getScaleX() {
        j jVar = this.x;
        if (jVar == null) {
            return 1.0f;
        }
        return jVar.p();
    }

    public float getScaleY() {
        j jVar = this.x;
        if (jVar == null) {
            return 1.0f;
        }
        return jVar.q();
    }

    public float getVisibleXRange() {
        return Math.abs(getHighestVisibleX() - getLowestVisibleX());
    }

    public float getYChartMax() {
        return Math.max(this.e0.G, this.f0.G);
    }

    public float getYChartMin() {
        return Math.min(this.e0.H, this.f0.H);
    }

    public void l() {
        if (this.f1326f != null) {
            if (this.e) {
                Log.i("MPAndroidChart", "Preparing...");
            }
            h.a.a.a.h.g gVar = this.v;
            if (gVar != null) {
                gVar.a();
            }
            o();
            t tVar = this.g0;
            YAxis yAxis = this.e0;
            tVar.a(yAxis.H, yAxis.G, yAxis.K());
            t tVar2 = this.h0;
            YAxis yAxis2 = this.f0;
            tVar2.a(yAxis2.H, yAxis2.G, yAxis2.K());
            q qVar = this.k0;
            XAxis xAxis = this.m;
            qVar.a(xAxis.H, xAxis.G, false);
            if (this.p != null) {
                this.u.a((h<?>) this.f1326f);
            }
            d();
        } else if (this.e) {
            Log.i("MPAndroidChart", "Preparing... DATA NOT SET.");
        }
    }

    /* access modifiers changed from: protected */
    public void n() {
        ((b) this.f1326f).a(getLowestVisibleX(), getHighestVisibleX());
        this.m.a(((b) this.f1326f).g(), ((b) this.f1326f).f());
        if (this.e0.f()) {
            this.e0.a(((b) this.f1326f).b(YAxis.AxisDependency.LEFT), ((b) this.f1326f).a(YAxis.AxisDependency.LEFT));
        }
        if (this.f0.f()) {
            this.f0.a(((b) this.f1326f).b(YAxis.AxisDependency.RIGHT), ((b) this.f1326f).a(YAxis.AxisDependency.RIGHT));
        }
        d();
    }

    /* access modifiers changed from: protected */
    public void o() {
        this.m.a(((b) this.f1326f).g(), ((b) this.f1326f).f());
        this.e0.a(((b) this.f1326f).b(YAxis.AxisDependency.LEFT), ((b) this.f1326f).a(YAxis.AxisDependency.LEFT));
        this.f0.a(((b) this.f1326f).b(YAxis.AxisDependency.RIGHT), ((b) this.f1326f).a(YAxis.AxisDependency.RIGHT));
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.f1326f != null) {
            long currentTimeMillis = System.currentTimeMillis();
            c(canvas);
            if (this.L) {
                n();
            }
            if (this.e0.f()) {
                t tVar = this.g0;
                YAxis yAxis = this.e0;
                tVar.a(yAxis.H, yAxis.G, yAxis.K());
            }
            if (this.f0.f()) {
                t tVar2 = this.h0;
                YAxis yAxis2 = this.f0;
                tVar2.a(yAxis2.H, yAxis2.G, yAxis2.K());
            }
            if (this.m.f()) {
                q qVar = this.k0;
                XAxis xAxis = this.m;
                qVar.a(xAxis.H, xAxis.G, false);
            }
            this.k0.b(canvas);
            this.g0.c(canvas);
            this.h0.c(canvas);
            if (this.m.t()) {
                this.k0.c(canvas);
            }
            if (this.e0.t()) {
                this.g0.d(canvas);
            }
            if (this.f0.t()) {
                this.h0.d(canvas);
            }
            if (this.m.f() && this.m.w()) {
                this.k0.d(canvas);
            }
            if (this.e0.f() && this.e0.w()) {
                this.g0.e(canvas);
            }
            if (this.f0.f() && this.f0.w()) {
                this.h0.e(canvas);
            }
            int save = canvas.save();
            canvas.clipRect(this.x.n());
            this.v.a(canvas);
            if (!this.m.t()) {
                this.k0.c(canvas);
            }
            if (!this.e0.t()) {
                this.g0.d(canvas);
            }
            if (!this.f0.t()) {
                this.h0.d(canvas);
            }
            if (m()) {
                this.v.a(canvas, this.E);
            }
            canvas.restoreToCount(save);
            this.v.b(canvas);
            if (this.m.f() && !this.m.w()) {
                this.k0.d(canvas);
            }
            if (this.e0.f() && !this.e0.w()) {
                this.g0.e(canvas);
            }
            if (this.f0.f() && !this.f0.w()) {
                this.h0.e(canvas);
            }
            this.k0.a(canvas);
            this.g0.b(canvas);
            this.h0.b(canvas);
            if (r()) {
                int save2 = canvas.save();
                canvas.clipRect(this.x.n());
                this.v.c(canvas);
                canvas.restoreToCount(save2);
            } else {
                this.v.c(canvas);
            }
            this.u.a(canvas);
            a(canvas);
            b(canvas);
            if (this.e) {
                long currentTimeMillis2 = System.currentTimeMillis() - currentTimeMillis;
                long j2 = this.l0 + currentTimeMillis2;
                this.l0 = j2;
                long j3 = this.m0 + 1;
                this.m0 = j3;
                Log.i("MPAndroidChart", "Drawtime: " + currentTimeMillis2 + " ms, average: " + (j2 / j3) + " ms, cycles: " + this.m0);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onSizeChanged(int i2, int i3, int i4, int i5) {
        float[] fArr = this.s0;
        fArr[1] = 0.0f;
        fArr[0] = 0.0f;
        if (this.c0) {
            fArr[0] = this.x.g();
            this.s0[1] = this.x.i();
            b(YAxis.AxisDependency.LEFT).a(this.s0);
        }
        super.onSizeChanged(i2, i3, i4, i5);
        if (this.c0) {
            b(YAxis.AxisDependency.LEFT).b(this.s0);
            this.x.a(this.s0, (View) this);
            return;
        }
        j jVar = this.x;
        jVar.a(jVar.o(), this, true);
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        super.onTouchEvent(motionEvent);
        ChartTouchListener chartTouchListener = this.r;
        if (chartTouchListener == null || this.f1326f == null || !this.n) {
            return false;
        }
        return chartTouchListener.onTouch(this, motionEvent);
    }

    public boolean p() {
        return this.x.t();
    }

    public boolean q() {
        if (!this.e0.K() && !this.f0.K()) {
            return false;
        }
        return true;
    }

    public boolean r() {
        return this.a0;
    }

    public boolean s() {
        return this.N;
    }

    public void setAutoScaleMinMaxEnabled(boolean z) {
        this.L = z;
    }

    public void setBorderColor(int i2) {
        this.U.setColor(i2);
    }

    public void setBorderWidth(float f2) {
        this.U.setStrokeWidth(i.a(f2));
    }

    public void setClipValuesToContent(boolean z) {
        this.a0 = z;
    }

    public void setDoubleTapToZoomEnabled(boolean z) {
        this.N = z;
    }

    public void setDragEnabled(boolean z) {
        this.P = z;
        this.Q = z;
    }

    public void setDragOffsetX(float f2) {
        this.x.g(f2);
    }

    public void setDragOffsetY(float f2) {
        this.x.h(f2);
    }

    public void setDragXEnabled(boolean z) {
        this.P = z;
    }

    public void setDragYEnabled(boolean z) {
        this.Q = z;
    }

    public void setDrawBorders(boolean z) {
        this.W = z;
    }

    public void setDrawGridBackground(boolean z) {
        this.V = z;
    }

    public void setGridBackgroundColor(int i2) {
        this.T.setColor(i2);
    }

    public void setHighlightPerDragEnabled(boolean z) {
        this.O = z;
    }

    public void setKeepPositionOnRotation(boolean z) {
        this.c0 = z;
    }

    public void setMaxVisibleValueCount(int i2) {
        this.K = i2;
    }

    public void setMinOffset(float f2) {
        this.b0 = f2;
    }

    public void setOnDrawListener(d dVar) {
        this.d0 = dVar;
    }

    public void setPinchZoom(boolean z) {
        this.M = z;
    }

    public void setRendererLeftYAxis(t tVar) {
        this.g0 = tVar;
    }

    public void setRendererRightYAxis(t tVar) {
        this.h0 = tVar;
    }

    public void setScaleEnabled(boolean z) {
        this.R = z;
        this.S = z;
    }

    public void setScaleXEnabled(boolean z) {
        this.R = z;
    }

    public void setScaleYEnabled(boolean z) {
        this.S = z;
    }

    public void setVisibleXRangeMaximum(float f2) {
        this.x.k(this.m.I / f2);
    }

    public void setVisibleXRangeMinimum(float f2) {
        this.x.i(this.m.I / f2);
    }

    public void setXAxisRenderer(q qVar) {
        this.k0 = qVar;
    }

    public boolean t() {
        return this.P || this.Q;
    }

    public boolean u() {
        return this.P;
    }

    public boolean v() {
        return this.Q;
    }

    public boolean w() {
        return this.x.u();
    }

    public boolean x() {
        return this.O;
    }

    public boolean y() {
        return this.M;
    }

    public boolean z() {
        return this.R;
    }

    public h.a.a.a.e.b.b c(float f2, float f3) {
        h.a.a.a.d.d a2 = a(f2, f3);
        if (a2 != null) {
            return (h.a.a.a.e.b.b) ((b) this.f1326f).a(a2.c());
        }
        return null;
    }

    public YAxis c(YAxis.AxisDependency axisDependency) {
        if (axisDependency == YAxis.AxisDependency.LEFT) {
            return this.e0;
        }
        return this.f0;
    }

    public BarLineChartBase(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        new Matrix();
        this.p0 = false;
        this.q0 = h.a.a.a.i.d.a(0.0d, 0.0d);
        this.r0 = h.a.a.a.i.d.a(0.0d, 0.0d);
        this.s0 = new float[2];
    }

    public void a(float f2, float f3, float f4, float f5) {
        this.x.a(f2, f3, f4, -f5, this.o0);
        this.x.a(this.o0, this, false);
        d();
        postInvalidate();
    }

    public void a(float f2) {
        a((Runnable) h.a.a.a.f.a.a(this.x, f2, 0.0f, b(YAxis.AxisDependency.LEFT), this));
    }

    public boolean a(YAxis.AxisDependency axisDependency) {
        return c(axisDependency).K();
    }

    public BarLineChartBase(Context context) {
        super(context);
        new Matrix();
        this.p0 = false;
        this.q0 = h.a.a.a.i.d.a(0.0d, 0.0d);
        this.r0 = h.a.a.a.i.d.a(0.0d, 0.0d);
        this.s0 = new float[2];
    }
}
