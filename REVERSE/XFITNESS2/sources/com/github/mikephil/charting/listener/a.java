package com.github.mikephil.charting.listener;

import android.annotation.SuppressLint;
import android.graphics.Matrix;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.animation.AnimationUtils;
import com.github.mikephil.charting.charts.BarLineChartBase;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.b;
import com.github.mikephil.charting.listener.ChartTouchListener;
import h.a.a.a.d.d;
import h.a.a.a.i.e;
import h.a.a.a.i.i;
import h.a.a.a.i.j;

/* compiled from: BarLineChartTouchListener */
public class a extends ChartTouchListener<BarLineChartBase<? extends b<? extends h.a.a.a.e.b.b<? extends Entry>>>> {

    /* renamed from: j  reason: collision with root package name */
    private Matrix f1374j = new Matrix();
    private Matrix k = new Matrix();
    private e l = e.a(0.0f, 0.0f);
    private e m = e.a(0.0f, 0.0f);
    private float n = 1.0f;
    private float o = 1.0f;
    private float p = 1.0f;
    private h.a.a.a.e.b.e q;
    private VelocityTracker r;
    private long s = 0;
    private e t = e.a(0.0f, 0.0f);
    private e u = e.a(0.0f, 0.0f);
    private float v;
    private float w;

    public a(BarLineChartBase<? extends b<? extends h.a.a.a.e.b.b<? extends Entry>>> barLineChartBase, Matrix matrix, float f2) {
        super(barLineChartBase);
        this.f1374j = matrix;
        this.v = i.a(f2);
        this.w = i.a(3.5f);
    }

    private void a(MotionEvent motionEvent, float f2, float f3) {
        this.e = ChartTouchListener.ChartGesture.DRAG;
        this.f1374j.set(this.k);
        b onChartGestureListener = ((BarLineChartBase) this.f1373i).getOnChartGestureListener();
        if (c()) {
            if (this.f1373i instanceof HorizontalBarChart) {
                f2 = -f2;
            } else {
                f3 = -f3;
            }
        }
        this.f1374j.postTranslate(f2, f3);
        if (onChartGestureListener != null) {
            onChartGestureListener.b(motionEvent, f2, f3);
        }
    }

    private static float c(MotionEvent motionEvent) {
        return Math.abs(motionEvent.getX(0) - motionEvent.getX(1));
    }

    private static float d(MotionEvent motionEvent) {
        return Math.abs(motionEvent.getY(0) - motionEvent.getY(1));
    }

    private void e(MotionEvent motionEvent) {
        d a = ((BarLineChartBase) this.f1373i).a(motionEvent.getX(), motionEvent.getY());
        if (a != null && !a.a(this.f1371g)) {
            this.f1371g = a;
            ((BarLineChartBase) this.f1373i).a(a, true);
        }
    }

    private void f(MotionEvent motionEvent) {
        boolean z;
        boolean z2;
        boolean z3;
        boolean z4;
        if (motionEvent.getPointerCount() >= 2) {
            b onChartGestureListener = ((BarLineChartBase) this.f1373i).getOnChartGestureListener();
            float h2 = h(motionEvent);
            if (h2 > this.w) {
                e eVar = this.m;
                e a = a(eVar.f1727g, eVar.f1728h);
                j viewPortHandler = ((BarLineChartBase) this.f1373i).getViewPortHandler();
                int i2 = this.f1370f;
                boolean z5 = true;
                float f2 = 1.0f;
                if (i2 == 4) {
                    this.e = ChartTouchListener.ChartGesture.PINCH_ZOOM;
                    float f3 = h2 / this.p;
                    if (f3 >= 1.0f) {
                        z5 = false;
                    }
                    if (z5) {
                        z3 = viewPortHandler.c();
                    } else {
                        z3 = viewPortHandler.a();
                    }
                    if (z5) {
                        z4 = viewPortHandler.d();
                    } else {
                        z4 = viewPortHandler.b();
                    }
                    float f4 = ((BarLineChartBase) this.f1373i).z() ? f3 : 1.0f;
                    if (((BarLineChartBase) this.f1373i).A()) {
                        f2 = f3;
                    }
                    if (z4 || z3) {
                        this.f1374j.set(this.k);
                        this.f1374j.postScale(f4, f2, a.f1727g, a.f1728h);
                        if (onChartGestureListener != null) {
                            onChartGestureListener.a(motionEvent, f4, f2);
                        }
                    }
                } else if (i2 == 2 && ((BarLineChartBase) this.f1373i).z()) {
                    this.e = ChartTouchListener.ChartGesture.X_ZOOM;
                    float c = c(motionEvent) / this.n;
                    if (c >= 1.0f) {
                        z5 = false;
                    }
                    if (z5) {
                        z2 = viewPortHandler.c();
                    } else {
                        z2 = viewPortHandler.a();
                    }
                    if (z2) {
                        this.f1374j.set(this.k);
                        this.f1374j.postScale(c, 1.0f, a.f1727g, a.f1728h);
                        if (onChartGestureListener != null) {
                            onChartGestureListener.a(motionEvent, c, 1.0f);
                        }
                    }
                } else if (this.f1370f == 3 && ((BarLineChartBase) this.f1373i).A()) {
                    this.e = ChartTouchListener.ChartGesture.Y_ZOOM;
                    float d = d(motionEvent) / this.o;
                    if (d >= 1.0f) {
                        z5 = false;
                    }
                    if (z5) {
                        z = viewPortHandler.d();
                    } else {
                        z = viewPortHandler.b();
                    }
                    if (z) {
                        this.f1374j.set(this.k);
                        this.f1374j.postScale(1.0f, d, a.f1727g, a.f1728h);
                        if (onChartGestureListener != null) {
                            onChartGestureListener.a(motionEvent, 1.0f, d);
                        }
                    }
                }
                e.b(a);
            }
        }
    }

    private void g(MotionEvent motionEvent) {
        this.k.set(this.f1374j);
        this.l.f1727g = motionEvent.getX();
        this.l.f1728h = motionEvent.getY();
        this.q = ((BarLineChartBase) this.f1373i).c(motionEvent.getX(), motionEvent.getY());
    }

    private static float h(MotionEvent motionEvent) {
        float x = motionEvent.getX(0) - motionEvent.getX(1);
        float y = motionEvent.getY(0) - motionEvent.getY(1);
        return (float) Math.sqrt((double) ((x * x) + (y * y)));
    }

    public void b() {
        e eVar = this.u;
        eVar.f1727g = 0.0f;
        eVar.f1728h = 0.0f;
    }

    public boolean onDoubleTap(MotionEvent motionEvent) {
        this.e = ChartTouchListener.ChartGesture.DOUBLE_TAP;
        b onChartGestureListener = ((BarLineChartBase) this.f1373i).getOnChartGestureListener();
        if (onChartGestureListener != null) {
            onChartGestureListener.a(motionEvent);
        }
        if (((BarLineChartBase) this.f1373i).s() && ((b) ((BarLineChartBase) this.f1373i).getData()).d() > 0) {
            e a = a(motionEvent.getX(), motionEvent.getY());
            T t2 = this.f1373i;
            BarLineChartBase barLineChartBase = (BarLineChartBase) t2;
            float f2 = 1.4f;
            float f3 = ((BarLineChartBase) t2).z() ? 1.4f : 1.0f;
            if (!((BarLineChartBase) this.f1373i).A()) {
                f2 = 1.0f;
            }
            barLineChartBase.a(f3, f2, a.f1727g, a.f1728h);
            if (((BarLineChartBase) this.f1373i).k()) {
                Log.i("BarlineChartTouch", "Double-Tap, Zooming In, x: " + a.f1727g + ", y: " + a.f1728h);
            }
            e.b(a);
        }
        return super.onDoubleTap(motionEvent);
    }

    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float f2, float f3) {
        this.e = ChartTouchListener.ChartGesture.FLING;
        b onChartGestureListener = ((BarLineChartBase) this.f1373i).getOnChartGestureListener();
        if (onChartGestureListener != null) {
            onChartGestureListener.a(motionEvent, motionEvent2, f2, f3);
        }
        return super.onFling(motionEvent, motionEvent2, f2, f3);
    }

    public void onLongPress(MotionEvent motionEvent) {
        this.e = ChartTouchListener.ChartGesture.LONG_PRESS;
        b onChartGestureListener = ((BarLineChartBase) this.f1373i).getOnChartGestureListener();
        if (onChartGestureListener != null) {
            onChartGestureListener.c(motionEvent);
        }
    }

    public boolean onSingleTapUp(MotionEvent motionEvent) {
        this.e = ChartTouchListener.ChartGesture.SINGLE_TAP;
        b onChartGestureListener = ((BarLineChartBase) this.f1373i).getOnChartGestureListener();
        if (onChartGestureListener != null) {
            onChartGestureListener.b(motionEvent);
        }
        if (!((BarLineChartBase) this.f1373i).j()) {
            return false;
        }
        a(((BarLineChartBase) this.f1373i).a(motionEvent.getX(), motionEvent.getY()), motionEvent);
        return super.onSingleTapUp(motionEvent);
    }

    @SuppressLint({"ClickableViewAccessibility"})
    public boolean onTouch(View view, MotionEvent motionEvent) {
        VelocityTracker velocityTracker;
        if (this.r == null) {
            this.r = VelocityTracker.obtain();
        }
        this.r.addMovement(motionEvent);
        int i2 = 3;
        if (motionEvent.getActionMasked() == 3 && (velocityTracker = this.r) != null) {
            velocityTracker.recycle();
            this.r = null;
        }
        if (this.f1370f == 0) {
            this.f1372h.onTouchEvent(motionEvent);
        }
        if (!((BarLineChartBase) this.f1373i).t() && !((BarLineChartBase) this.f1373i).z() && !((BarLineChartBase) this.f1373i).A()) {
            return true;
        }
        int action = motionEvent.getAction() & 255;
        if (action != 0) {
            boolean z = false;
            if (action == 1) {
                VelocityTracker velocityTracker2 = this.r;
                int pointerId = motionEvent.getPointerId(0);
                velocityTracker2.computeCurrentVelocity(1000, (float) i.c());
                float yVelocity = velocityTracker2.getYVelocity(pointerId);
                float xVelocity = velocityTracker2.getXVelocity(pointerId);
                if ((Math.abs(xVelocity) > ((float) i.d()) || Math.abs(yVelocity) > ((float) i.d())) && this.f1370f == 1 && ((BarLineChartBase) this.f1373i).h()) {
                    b();
                    this.s = AnimationUtils.currentAnimationTimeMillis();
                    this.t.f1727g = motionEvent.getX();
                    this.t.f1728h = motionEvent.getY();
                    e eVar = this.u;
                    eVar.f1727g = xVelocity;
                    eVar.f1728h = yVelocity;
                    i.a((View) this.f1373i);
                }
                int i3 = this.f1370f;
                if (i3 == 2 || i3 == 3 || i3 == 4 || i3 == 5) {
                    ((BarLineChartBase) this.f1373i).d();
                    ((BarLineChartBase) this.f1373i).postInvalidate();
                }
                this.f1370f = 0;
                ((BarLineChartBase) this.f1373i).f();
                VelocityTracker velocityTracker3 = this.r;
                if (velocityTracker3 != null) {
                    velocityTracker3.recycle();
                    this.r = null;
                }
                a(motionEvent);
            } else if (action == 2) {
                int i4 = this.f1370f;
                if (i4 == 1) {
                    ((BarLineChartBase) this.f1373i).e();
                    float f2 = 0.0f;
                    float x = ((BarLineChartBase) this.f1373i).u() ? motionEvent.getX() - this.l.f1727g : 0.0f;
                    if (((BarLineChartBase) this.f1373i).v()) {
                        f2 = motionEvent.getY() - this.l.f1728h;
                    }
                    a(motionEvent, x, f2);
                } else if (i4 == 2 || i4 == 3 || i4 == 4) {
                    ((BarLineChartBase) this.f1373i).e();
                    if (((BarLineChartBase) this.f1373i).z() || ((BarLineChartBase) this.f1373i).A()) {
                        f(motionEvent);
                    }
                } else if (i4 == 0 && Math.abs(ChartTouchListener.a(motionEvent.getX(), this.l.f1727g, motionEvent.getY(), this.l.f1728h)) > this.v && ((BarLineChartBase) this.f1373i).t()) {
                    if (!((BarLineChartBase) this.f1373i).w() || !((BarLineChartBase) this.f1373i).p()) {
                        z = true;
                    }
                    if (z) {
                        float abs = Math.abs(motionEvent.getX() - this.l.f1727g);
                        float abs2 = Math.abs(motionEvent.getY() - this.l.f1728h);
                        if ((((BarLineChartBase) this.f1373i).u() || abs2 >= abs) && (((BarLineChartBase) this.f1373i).v() || abs2 <= abs)) {
                            this.e = ChartTouchListener.ChartGesture.DRAG;
                            this.f1370f = 1;
                        }
                    } else if (((BarLineChartBase) this.f1373i).x()) {
                        this.e = ChartTouchListener.ChartGesture.DRAG;
                        if (((BarLineChartBase) this.f1373i).x()) {
                            e(motionEvent);
                        }
                    }
                }
            } else if (action == 3) {
                this.f1370f = 0;
                a(motionEvent);
            } else if (action != 5) {
                if (action == 6) {
                    i.a(motionEvent, this.r);
                    this.f1370f = 5;
                }
            } else if (motionEvent.getPointerCount() >= 2) {
                ((BarLineChartBase) this.f1373i).e();
                g(motionEvent);
                this.n = c(motionEvent);
                this.o = d(motionEvent);
                float h2 = h(motionEvent);
                this.p = h2;
                if (h2 > 10.0f) {
                    if (((BarLineChartBase) this.f1373i).y()) {
                        this.f1370f = 4;
                    } else if (((BarLineChartBase) this.f1373i).z() != ((BarLineChartBase) this.f1373i).A()) {
                        if (((BarLineChartBase) this.f1373i).z()) {
                            i2 = 2;
                        }
                        this.f1370f = i2;
                    } else {
                        if (this.n > this.o) {
                            i2 = 2;
                        }
                        this.f1370f = i2;
                    }
                }
                a(this.m, motionEvent);
            }
        } else {
            b(motionEvent);
            b();
            g(motionEvent);
        }
        j viewPortHandler = ((BarLineChartBase) this.f1373i).getViewPortHandler();
        Matrix matrix = this.f1374j;
        viewPortHandler.a(matrix, this.f1373i, true);
        this.f1374j = matrix;
        return true;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:4:0x000e, code lost:
        r0 = r2.q;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean c() {
        /*
            r2 = this;
            h.a.a.a.e.b.e r0 = r2.q
            if (r0 != 0) goto L_0x000e
            T r0 = r2.f1373i
            com.github.mikephil.charting.charts.BarLineChartBase r0 = (com.github.mikephil.charting.charts.BarLineChartBase) r0
            boolean r0 = r0.q()
            if (r0 != 0) goto L_0x0020
        L_0x000e:
            h.a.a.a.e.b.e r0 = r2.q
            if (r0 == 0) goto L_0x0022
            T r1 = r2.f1373i
            com.github.mikephil.charting.charts.BarLineChartBase r1 = (com.github.mikephil.charting.charts.BarLineChartBase) r1
            com.github.mikephil.charting.components.YAxis$AxisDependency r0 = r0.S()
            boolean r0 = r1.a((com.github.mikephil.charting.components.YAxis.AxisDependency) r0)
            if (r0 == 0) goto L_0x0022
        L_0x0020:
            r0 = 1
            goto L_0x0023
        L_0x0022:
            r0 = 0
        L_0x0023:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.github.mikephil.charting.listener.a.c():boolean");
    }

    private static void a(e eVar, MotionEvent motionEvent) {
        eVar.f1727g = (motionEvent.getX(0) + motionEvent.getX(1)) / 2.0f;
        eVar.f1728h = (motionEvent.getY(0) + motionEvent.getY(1)) / 2.0f;
    }

    public e a(float f2, float f3) {
        float f4;
        j viewPortHandler = ((BarLineChartBase) this.f1373i).getViewPortHandler();
        float y = f2 - viewPortHandler.y();
        if (c()) {
            f4 = -(f3 - viewPortHandler.A());
        } else {
            f4 = -((((float) ((BarLineChartBase) this.f1373i).getMeasuredHeight()) - f3) - viewPortHandler.x());
        }
        return e.a(y, f4);
    }

    public void a() {
        e eVar = this.u;
        float f2 = 0.0f;
        if (eVar.f1727g != 0.0f || eVar.f1728h != 0.0f) {
            long currentAnimationTimeMillis = AnimationUtils.currentAnimationTimeMillis();
            this.u.f1727g *= ((BarLineChartBase) this.f1373i).getDragDecelerationFrictionCoef();
            this.u.f1728h *= ((BarLineChartBase) this.f1373i).getDragDecelerationFrictionCoef();
            float f3 = ((float) (currentAnimationTimeMillis - this.s)) / 1000.0f;
            e eVar2 = this.u;
            float f4 = eVar2.f1727g * f3;
            float f5 = eVar2.f1728h * f3;
            e eVar3 = this.t;
            float f6 = eVar3.f1727g + f4;
            eVar3.f1727g = f6;
            float f7 = eVar3.f1728h + f5;
            eVar3.f1728h = f7;
            MotionEvent obtain = MotionEvent.obtain(currentAnimationTimeMillis, currentAnimationTimeMillis, 2, f6, f7, 0);
            float f8 = ((BarLineChartBase) this.f1373i).u() ? this.t.f1727g - this.l.f1727g : 0.0f;
            if (((BarLineChartBase) this.f1373i).v()) {
                f2 = this.t.f1728h - this.l.f1728h;
            }
            a(obtain, f8, f2);
            obtain.recycle();
            j viewPortHandler = ((BarLineChartBase) this.f1373i).getViewPortHandler();
            Matrix matrix = this.f1374j;
            viewPortHandler.a(matrix, this.f1373i, false);
            this.f1374j = matrix;
            this.s = currentAnimationTimeMillis;
            if (((double) Math.abs(this.u.f1727g)) >= 0.01d || ((double) Math.abs(this.u.f1728h)) >= 0.01d) {
                i.a((View) this.f1373i);
                return;
            }
            ((BarLineChartBase) this.f1373i).d();
            ((BarLineChartBase) this.f1373i).postInvalidate();
            b();
        }
    }
}
