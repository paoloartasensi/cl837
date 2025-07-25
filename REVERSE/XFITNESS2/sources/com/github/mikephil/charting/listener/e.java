package com.github.mikephil.charting.listener;

import android.annotation.SuppressLint;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import com.github.mikephil.charting.charts.PieRadarChartBase;
import com.github.mikephil.charting.listener.ChartTouchListener;
import h.a.a.a.i.i;
import java.util.ArrayList;

/* compiled from: PieRadarChartTouchListener */
public class e extends ChartTouchListener<PieRadarChartBase<?>> {

    /* renamed from: j  reason: collision with root package name */
    private h.a.a.a.i.e f1375j = h.a.a.a.i.e.a(0.0f, 0.0f);
    private float k = 0.0f;
    private ArrayList<a> l = new ArrayList<>();
    private long m = 0;
    private float n = 0.0f;

    /* compiled from: PieRadarChartTouchListener */
    private class a {
        public long a;
        public float b;

        public a(e eVar, long j2, float f2) {
            this.a = j2;
            this.b = f2;
        }
    }

    public e(PieRadarChartBase<?> pieRadarChartBase) {
        super(pieRadarChartBase);
    }

    private void c(float f2, float f3) {
        long currentAnimationTimeMillis = AnimationUtils.currentAnimationTimeMillis();
        this.l.add(new a(this, currentAnimationTimeMillis, ((PieRadarChartBase) this.f1373i).d(f2, f3)));
        for (int size = this.l.size(); size - 2 > 0 && currentAnimationTimeMillis - this.l.get(0).a > 1000; size--) {
            this.l.remove(0);
        }
    }

    private void d() {
        this.l.clear();
    }

    public void a(float f2, float f3) {
        this.k = ((PieRadarChartBase) this.f1373i).d(f2, f3) - ((PieRadarChartBase) this.f1373i).getRawRotationAngle();
    }

    public void b(float f2, float f3) {
        T t = this.f1373i;
        ((PieRadarChartBase) t).setRotationAngle(((PieRadarChartBase) t).d(f2, f3) - this.k);
    }

    public void onLongPress(MotionEvent motionEvent) {
        this.e = ChartTouchListener.ChartGesture.LONG_PRESS;
        b onChartGestureListener = ((PieRadarChartBase) this.f1373i).getOnChartGestureListener();
        if (onChartGestureListener != null) {
            onChartGestureListener.c(motionEvent);
        }
    }

    public boolean onSingleTapConfirmed(MotionEvent motionEvent) {
        return true;
    }

    public boolean onSingleTapUp(MotionEvent motionEvent) {
        this.e = ChartTouchListener.ChartGesture.SINGLE_TAP;
        b onChartGestureListener = ((PieRadarChartBase) this.f1373i).getOnChartGestureListener();
        if (onChartGestureListener != null) {
            onChartGestureListener.b(motionEvent);
        }
        if (!((PieRadarChartBase) this.f1373i).j()) {
            return false;
        }
        a(((PieRadarChartBase) this.f1373i).a(motionEvent.getX(), motionEvent.getY()), motionEvent);
        return true;
    }

    @SuppressLint({"ClickableViewAccessibility"})
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (!this.f1372h.onTouchEvent(motionEvent) && ((PieRadarChartBase) this.f1373i).o()) {
            float x = motionEvent.getX();
            float y = motionEvent.getY();
            int action = motionEvent.getAction();
            if (action == 0) {
                b(motionEvent);
                b();
                d();
                if (((PieRadarChartBase) this.f1373i).h()) {
                    c(x, y);
                }
                a(x, y);
                h.a.a.a.i.e eVar = this.f1375j;
                eVar.f1727g = x;
                eVar.f1728h = y;
            } else if (action == 1) {
                if (((PieRadarChartBase) this.f1373i).h()) {
                    b();
                    c(x, y);
                    float c = c();
                    this.n = c;
                    if (c != 0.0f) {
                        this.m = AnimationUtils.currentAnimationTimeMillis();
                        i.a((View) this.f1373i);
                    }
                }
                ((PieRadarChartBase) this.f1373i).f();
                this.f1370f = 0;
                a(motionEvent);
            } else if (action == 2) {
                if (((PieRadarChartBase) this.f1373i).h()) {
                    c(x, y);
                }
                if (this.f1370f == 0) {
                    h.a.a.a.i.e eVar2 = this.f1375j;
                    if (ChartTouchListener.a(x, eVar2.f1727g, y, eVar2.f1728h) > i.a(8.0f)) {
                        this.e = ChartTouchListener.ChartGesture.ROTATE;
                        this.f1370f = 6;
                        ((PieRadarChartBase) this.f1373i).e();
                        a(motionEvent);
                    }
                }
                if (this.f1370f == 6) {
                    b(x, y);
                    ((PieRadarChartBase) this.f1373i).invalidate();
                }
                a(motionEvent);
            }
        }
        return true;
    }

    public void a() {
        if (this.n != 0.0f) {
            long currentAnimationTimeMillis = AnimationUtils.currentAnimationTimeMillis();
            this.n *= ((PieRadarChartBase) this.f1373i).getDragDecelerationFrictionCoef();
            T t = this.f1373i;
            ((PieRadarChartBase) t).setRotationAngle(((PieRadarChartBase) t).getRotationAngle() + (this.n * (((float) (currentAnimationTimeMillis - this.m)) / 1000.0f)));
            this.m = currentAnimationTimeMillis;
            if (((double) Math.abs(this.n)) >= 0.001d) {
                i.a((View) this.f1373i);
            } else {
                b();
            }
        }
    }

    public void b() {
        this.n = 0.0f;
    }

    private float c() {
        if (this.l.isEmpty()) {
            return 0.0f;
        }
        boolean z = false;
        a aVar = this.l.get(0);
        ArrayList<a> arrayList = this.l;
        a aVar2 = arrayList.get(arrayList.size() - 1);
        a aVar3 = aVar;
        for (int size = this.l.size() - 1; size >= 0; size--) {
            aVar3 = this.l.get(size);
            if (aVar3.b != aVar2.b) {
                break;
            }
        }
        float f2 = ((float) (aVar2.a - aVar.a)) / 1000.0f;
        if (f2 == 0.0f) {
            f2 = 0.1f;
        }
        if (aVar2.b >= aVar3.b) {
            z = true;
        }
        if (((double) Math.abs(aVar2.b - aVar3.b)) > 270.0d) {
            z = !z;
        }
        float f3 = aVar2.b;
        float f4 = aVar.b;
        if (((double) (f3 - f4)) > 180.0d) {
            double d = (double) f4;
            Double.isNaN(d);
            aVar.b = (float) (d + 360.0d);
        } else if (((double) (f4 - f3)) > 180.0d) {
            double d2 = (double) f3;
            Double.isNaN(d2);
            aVar2.b = (float) (d2 + 360.0d);
        }
        float abs = Math.abs((aVar2.b - aVar.b) / f2);
        return !z ? -abs : abs;
    }
}
