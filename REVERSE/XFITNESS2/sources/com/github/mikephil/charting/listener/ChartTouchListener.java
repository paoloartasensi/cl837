package com.github.mikephil.charting.listener;

import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import com.github.mikephil.charting.charts.Chart;
import h.a.a.a.d.d;

public abstract class ChartTouchListener<T extends Chart<?>> extends GestureDetector.SimpleOnGestureListener implements View.OnTouchListener {
    protected ChartGesture e = ChartGesture.NONE;

    /* renamed from: f  reason: collision with root package name */
    protected int f1370f = 0;

    /* renamed from: g  reason: collision with root package name */
    protected d f1371g;

    /* renamed from: h  reason: collision with root package name */
    protected GestureDetector f1372h;

    /* renamed from: i  reason: collision with root package name */
    protected T f1373i;

    public enum ChartGesture {
        NONE,
        DRAG,
        X_ZOOM,
        Y_ZOOM,
        PINCH_ZOOM,
        ROTATE,
        SINGLE_TAP,
        DOUBLE_TAP,
        LONG_PRESS,
        FLING
    }

    public ChartTouchListener(T t) {
        this.f1373i = t;
        this.f1372h = new GestureDetector(t.getContext(), this);
    }

    public void a(MotionEvent motionEvent) {
        b onChartGestureListener = this.f1373i.getOnChartGestureListener();
        if (onChartGestureListener != null) {
            onChartGestureListener.b(motionEvent, this.e);
        }
    }

    public void b(MotionEvent motionEvent) {
        b onChartGestureListener = this.f1373i.getOnChartGestureListener();
        if (onChartGestureListener != null) {
            onChartGestureListener.a(motionEvent, this.e);
        }
    }

    public void a(d dVar) {
        this.f1371g = dVar;
    }

    /* access modifiers changed from: protected */
    public void a(d dVar, MotionEvent motionEvent) {
        if (dVar == null || dVar.a(this.f1371g)) {
            this.f1373i.a((d) null, true);
            this.f1371g = null;
            return;
        }
        this.f1373i.a(dVar, true);
        this.f1371g = dVar;
    }

    protected static float a(float f2, float f3, float f4, float f5) {
        float f6 = f2 - f3;
        float f7 = f4 - f5;
        return (float) Math.sqrt((double) ((f6 * f6) + (f7 * f7)));
    }
}
