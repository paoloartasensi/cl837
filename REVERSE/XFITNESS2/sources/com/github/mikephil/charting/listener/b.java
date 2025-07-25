package com.github.mikephil.charting.listener;

import android.view.MotionEvent;
import com.github.mikephil.charting.listener.ChartTouchListener;

/* compiled from: OnChartGestureListener */
public interface b {
    void a(MotionEvent motionEvent);

    void a(MotionEvent motionEvent, float f2, float f3);

    void a(MotionEvent motionEvent, MotionEvent motionEvent2, float f2, float f3);

    void a(MotionEvent motionEvent, ChartTouchListener.ChartGesture chartGesture);

    void b(MotionEvent motionEvent);

    void b(MotionEvent motionEvent, float f2, float f3);

    void b(MotionEvent motionEvent, ChartTouchListener.ChartGesture chartGesture);

    void c(MotionEvent motionEvent);
}
