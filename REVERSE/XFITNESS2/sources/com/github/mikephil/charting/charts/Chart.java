package com.github.mikephil.charting.charts;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.h;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.b;
import com.jeremyliao.liveeventbus.BuildConfig;
import h.a.a.a.c.c;
import h.a.a.a.d.d;
import h.a.a.a.d.f;
import h.a.a.a.e.b.e;
import h.a.a.a.h.g;
import h.a.a.a.h.i;
import h.a.a.a.i.j;
import java.util.ArrayList;
import java.util.Iterator;

public abstract class Chart<T extends h<? extends e<? extends Entry>>> extends ViewGroup implements h.a.a.a.e.a.e {
    private float A = 0.0f;
    private float B = 0.0f;
    private float C = 0.0f;
    private boolean D = false;
    protected d[] E;
    protected float F = 0.0f;
    protected boolean G = true;
    protected com.github.mikephil.charting.components.d H;
    protected ArrayList<Runnable> I = new ArrayList<>();
    private boolean J = false;
    protected boolean e = false;

    /* renamed from: f  reason: collision with root package name */
    protected T f1326f = null;

    /* renamed from: g  reason: collision with root package name */
    protected boolean f1327g = true;

    /* renamed from: h  reason: collision with root package name */
    private boolean f1328h = true;

    /* renamed from: i  reason: collision with root package name */
    private float f1329i = 0.9f;

    /* renamed from: j  reason: collision with root package name */
    protected c f1330j = new c(0);
    protected Paint k;
    protected Paint l;
    protected XAxis m;
    protected boolean n = true;
    protected com.github.mikephil.charting.components.c o;
    protected Legend p;
    protected com.github.mikephil.charting.listener.c q;
    protected ChartTouchListener r;
    private String s = "No chart data available.";
    private b t;
    protected i u;
    protected g v;
    protected f w;
    protected j x = new j();
    protected h.a.a.a.a.a y;
    private float z = 0.0f;

    class a implements ValueAnimator.AnimatorUpdateListener {
        a() {
        }

        public void onAnimationUpdate(ValueAnimator valueAnimator) {
            Chart.this.postInvalidate();
        }
    }

    public Chart(Context context) {
        super(context);
        g();
    }

    /* access modifiers changed from: protected */
    public void a(Canvas canvas) {
        float f2;
        float f3;
        com.github.mikephil.charting.components.c cVar = this.o;
        if (cVar != null && cVar.f()) {
            h.a.a.a.i.e g2 = this.o.g();
            this.k.setTypeface(this.o.c());
            this.k.setTextSize(this.o.b());
            this.k.setColor(this.o.a());
            this.k.setTextAlign(this.o.i());
            if (g2 == null) {
                f3 = (((float) getWidth()) - this.x.z()) - this.o.d();
                f2 = (((float) getHeight()) - this.x.x()) - this.o.e();
            } else {
                float f4 = g2.f1727g;
                f2 = g2.f1728h;
                f3 = f4;
            }
            canvas.drawText(this.o.h(), f3, f2, this.k);
        }
    }

    /* access modifiers changed from: protected */
    public void b(float f2, float f3) {
        float f4;
        T t2 = this.f1326f;
        if (t2 == null || t2.d() < 2) {
            f4 = Math.max(Math.abs(f2), Math.abs(f3));
        } else {
            f4 = Math.abs(f3 - f2);
        }
        this.f1330j.a(h.a.a.a.i.i.b(f4));
    }

    /* access modifiers changed from: protected */
    public abstract void d();

    public void e() {
        ViewParent parent = getParent();
        if (parent != null) {
            parent.requestDisallowInterceptTouchEvent(true);
        }
    }

    public void f() {
        ViewParent parent = getParent();
        if (parent != null) {
            parent.requestDisallowInterceptTouchEvent(false);
        }
    }

    /* access modifiers changed from: protected */
    public void g() {
        setWillNotDraw(false);
        this.y = new h.a.a.a.a.a(new a());
        h.a.a.a.i.i.a(getContext());
        this.F = h.a.a.a.i.i.a(500.0f);
        this.o = new com.github.mikephil.charting.components.c();
        Legend legend = new Legend();
        this.p = legend;
        this.u = new i(this.x, legend);
        this.m = new XAxis();
        this.k = new Paint(1);
        Paint paint = new Paint(1);
        this.l = paint;
        paint.setColor(Color.rgb(247, 189, 51));
        this.l.setTextAlign(Paint.Align.CENTER);
        this.l.setTextSize(h.a.a.a.i.i.a(12.0f));
        if (this.e) {
            Log.i(BuildConfig.FLAVOR, "Chart.init()");
        }
    }

    public h.a.a.a.a.a getAnimator() {
        return this.y;
    }

    public h.a.a.a.i.e getCenter() {
        return h.a.a.a.i.e.a(((float) getWidth()) / 2.0f, ((float) getHeight()) / 2.0f);
    }

    public h.a.a.a.i.e getCenterOfView() {
        return getCenter();
    }

    public h.a.a.a.i.e getCenterOffsets() {
        return this.x.m();
    }

    public Bitmap getChartBitmap() {
        Bitmap createBitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(createBitmap);
        Drawable background = getBackground();
        if (background != null) {
            background.draw(canvas);
        } else {
            canvas.drawColor(-1);
        }
        draw(canvas);
        return createBitmap;
    }

    public RectF getContentRect() {
        return this.x.n();
    }

    public T getData() {
        return this.f1326f;
    }

    public h.a.a.a.c.e getDefaultValueFormatter() {
        return this.f1330j;
    }

    public com.github.mikephil.charting.components.c getDescription() {
        return this.o;
    }

    public float getDragDecelerationFrictionCoef() {
        return this.f1329i;
    }

    public float getExtraBottomOffset() {
        return this.B;
    }

    public float getExtraLeftOffset() {
        return this.C;
    }

    public float getExtraRightOffset() {
        return this.A;
    }

    public float getExtraTopOffset() {
        return this.z;
    }

    public d[] getHighlighted() {
        return this.E;
    }

    public f getHighlighter() {
        return this.w;
    }

    public ArrayList<Runnable> getJobs() {
        return this.I;
    }

    public Legend getLegend() {
        return this.p;
    }

    public i getLegendRenderer() {
        return this.u;
    }

    public com.github.mikephil.charting.components.d getMarker() {
        return this.H;
    }

    @Deprecated
    public com.github.mikephil.charting.components.d getMarkerView() {
        return getMarker();
    }

    public float getMaxHighlightDistance() {
        return this.F;
    }

    public b getOnChartGestureListener() {
        return this.t;
    }

    public ChartTouchListener getOnTouchListener() {
        return this.r;
    }

    public g getRenderer() {
        return this.v;
    }

    public j getViewPortHandler() {
        return this.x;
    }

    public XAxis getXAxis() {
        return this.m;
    }

    public float getXChartMax() {
        return this.m.G;
    }

    public float getXChartMin() {
        return this.m.H;
    }

    public float getXRange() {
        return this.m.I;
    }

    public float getYMax() {
        return this.f1326f.h();
    }

    public float getYMin() {
        return this.f1326f.i();
    }

    public boolean h() {
        return this.f1328h;
    }

    public boolean i() {
        return this.G;
    }

    public boolean j() {
        return this.f1327g;
    }

    public boolean k() {
        return this.e;
    }

    public abstract void l();

    public boolean m() {
        d[] dVarArr = this.E;
        return (dVarArr == null || dVarArr.length <= 0 || dVarArr[0] == null) ? false : true;
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (this.J) {
            a((View) this);
        }
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        if (this.f1326f == null) {
            if (!TextUtils.isEmpty(this.s)) {
                h.a.a.a.i.e center = getCenter();
                canvas.drawText(this.s, center.f1727g, center.f1728h, this.l);
            }
        } else if (!this.D) {
            d();
            this.D = true;
        }
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z2, int i2, int i3, int i4, int i5) {
        for (int i6 = 0; i6 < getChildCount(); i6++) {
            getChildAt(i6).layout(i2, i3, i4, i5);
        }
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i2, int i3) {
        super.onMeasure(i2, i3);
        int a2 = (int) h.a.a.a.i.i.a(50.0f);
        setMeasuredDimension(Math.max(getSuggestedMinimumWidth(), ViewGroup.resolveSize(a2, i2)), Math.max(getSuggestedMinimumHeight(), ViewGroup.resolveSize(a2, i3)));
    }

    /* access modifiers changed from: protected */
    public void onSizeChanged(int i2, int i3, int i4, int i5) {
        if (this.e) {
            Log.i("MPAndroidChart", "OnSizeChanged()");
        }
        if (i2 > 0 && i3 > 0 && i2 < 10000 && i3 < 10000) {
            if (this.e) {
                Log.i("MPAndroidChart", "Setting chart dimens, width: " + i2 + ", height: " + i3);
            }
            this.x.b((float) i2, (float) i3);
        } else if (this.e) {
            Log.w("MPAndroidChart", "*Avoiding* setting chart dimens! width: " + i2 + ", height: " + i3);
        }
        l();
        Iterator<Runnable> it = this.I.iterator();
        while (it.hasNext()) {
            post(it.next());
        }
        this.I.clear();
        super.onSizeChanged(i2, i3, i4, i5);
    }

    public void setData(T t2) {
        this.f1326f = t2;
        this.D = false;
        if (t2 != null) {
            b(t2.i(), t2.h());
            for (e eVar : this.f1326f.c()) {
                if (eVar.p() || eVar.W() == this.f1330j) {
                    eVar.a((h.a.a.a.c.e) this.f1330j);
                }
            }
            l();
            if (this.e) {
                Log.i("MPAndroidChart", "Data is set.");
            }
        }
    }

    public void setDescription(com.github.mikephil.charting.components.c cVar) {
        this.o = cVar;
    }

    public void setDragDecelerationEnabled(boolean z2) {
        this.f1328h = z2;
    }

    public void setDragDecelerationFrictionCoef(float f2) {
        if (f2 < 0.0f) {
            f2 = 0.0f;
        }
        if (f2 >= 1.0f) {
            f2 = 0.999f;
        }
        this.f1329i = f2;
    }

    @Deprecated
    public void setDrawMarkerViews(boolean z2) {
        setDrawMarkers(z2);
    }

    public void setDrawMarkers(boolean z2) {
        this.G = z2;
    }

    public void setExtraBottomOffset(float f2) {
        this.B = h.a.a.a.i.i.a(f2);
    }

    public void setExtraLeftOffset(float f2) {
        this.C = h.a.a.a.i.i.a(f2);
    }

    public void setExtraRightOffset(float f2) {
        this.A = h.a.a.a.i.i.a(f2);
    }

    public void setExtraTopOffset(float f2) {
        this.z = h.a.a.a.i.i.a(f2);
    }

    public void setHardwareAccelerationEnabled(boolean z2) {
        if (z2) {
            setLayerType(2, (Paint) null);
        } else {
            setLayerType(1, (Paint) null);
        }
    }

    public void setHighlightPerTapEnabled(boolean z2) {
        this.f1327g = z2;
    }

    public void setHighlighter(h.a.a.a.d.b bVar) {
        this.w = bVar;
    }

    /* access modifiers changed from: protected */
    public void setLastHighlighted(d[] dVarArr) {
        if (dVarArr == null || dVarArr.length <= 0 || dVarArr[0] == null) {
            this.r.a((d) null);
        } else {
            this.r.a(dVarArr[0]);
        }
    }

    public void setLogEnabled(boolean z2) {
        this.e = z2;
    }

    public void setMarker(com.github.mikephil.charting.components.d dVar) {
        this.H = dVar;
    }

    @Deprecated
    public void setMarkerView(com.github.mikephil.charting.components.d dVar) {
        setMarker(dVar);
    }

    public void setMaxHighlightDistance(float f2) {
        this.F = h.a.a.a.i.i.a(f2);
    }

    public void setNoDataText(String str) {
        this.s = str;
    }

    public void setNoDataTextColor(int i2) {
        this.l.setColor(i2);
    }

    public void setNoDataTextTypeface(Typeface typeface) {
        this.l.setTypeface(typeface);
    }

    public void setOnChartGestureListener(b bVar) {
        this.t = bVar;
    }

    public void setOnChartValueSelectedListener(com.github.mikephil.charting.listener.c cVar) {
        this.q = cVar;
    }

    public void setOnTouchListener(ChartTouchListener chartTouchListener) {
        this.r = chartTouchListener;
    }

    public void setRenderer(g gVar) {
        if (gVar != null) {
            this.v = gVar;
        }
    }

    public void setTouchEnabled(boolean z2) {
        this.n = z2;
    }

    public void setUnbindEnabled(boolean z2) {
        this.J = z2;
    }

    /* access modifiers changed from: protected */
    public void b(Canvas canvas) {
        if (this.H != null && i() && m()) {
            int i2 = 0;
            while (true) {
                d[] dVarArr = this.E;
                if (i2 < dVarArr.length) {
                    d dVar = dVarArr[i2];
                    e a2 = this.f1326f.a(dVar.c());
                    Entry a3 = this.f1326f.a(this.E[i2]);
                    int b = a2.b(a3);
                    if (a3 != null && ((float) b) <= ((float) a2.X()) * this.y.a()) {
                        float[] a4 = a(dVar);
                        if (this.x.a(a4[0], a4[1])) {
                            this.H.a(a3, dVar);
                            this.H.a(canvas, a4[0], a4[1]);
                        }
                    }
                    i2++;
                } else {
                    return;
                }
            }
        }
    }

    public void a(d dVar, boolean z2) {
        Entry entry = null;
        if (dVar == null) {
            this.E = null;
        } else {
            if (this.e) {
                Log.i("MPAndroidChart", "Highlighted: " + dVar.toString());
            }
            Entry a2 = this.f1326f.a(dVar);
            if (a2 == null) {
                this.E = null;
                dVar = null;
            } else {
                this.E = new d[]{dVar};
            }
            entry = a2;
        }
        setLastHighlighted(this.E);
        if (z2 && this.q != null) {
            if (!m()) {
                this.q.a();
            } else {
                this.q.a(entry, dVar);
            }
        }
        invalidate();
    }

    public Chart(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        g();
    }

    public d a(float f2, float f3) {
        if (this.f1326f != null) {
            return getHighlighter().a(f2, f3);
        }
        Log.e("MPAndroidChart", "Can't select by touch. No data set.");
        return null;
    }

    /* access modifiers changed from: protected */
    public float[] a(d dVar) {
        return new float[]{dVar.d(), dVar.e()};
    }

    public void a(Runnable runnable) {
        if (this.x.s()) {
            post(runnable);
        } else {
            this.I.add(runnable);
        }
    }

    private void a(View view) {
        if (view.getBackground() != null) {
            view.getBackground().setCallback((Drawable.Callback) null);
        }
        if (view instanceof ViewGroup) {
            int i2 = 0;
            while (true) {
                ViewGroup viewGroup = (ViewGroup) view;
                if (i2 < viewGroup.getChildCount()) {
                    a(viewGroup.getChildAt(i2));
                    i2++;
                } else {
                    viewGroup.removeAllViews();
                    return;
                }
            }
        }
    }

    public Chart(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        g();
    }
}
