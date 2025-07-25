package com.google.android.material.appbar;

import android.content.Context;
import android.util.AttributeSet;
import android.view.VelocityTracker;
import android.view.View;
import android.widget.OverScroller;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.h.v;

/* compiled from: HeaderBehavior */
abstract class a<V extends View> extends c<V> {
    private Runnable d;
    OverScroller e;

    /* renamed from: f  reason: collision with root package name */
    private boolean f1389f;

    /* renamed from: g  reason: collision with root package name */
    private int f1390g = -1;

    /* renamed from: h  reason: collision with root package name */
    private int f1391h;

    /* renamed from: i  reason: collision with root package name */
    private int f1392i = -1;

    /* renamed from: j  reason: collision with root package name */
    private VelocityTracker f1393j;

    /* renamed from: com.google.android.material.appbar.a$a  reason: collision with other inner class name */
    /* compiled from: HeaderBehavior */
    private class C0074a implements Runnable {
        private final CoordinatorLayout e;

        /* renamed from: f  reason: collision with root package name */
        private final V f1394f;

        C0074a(CoordinatorLayout coordinatorLayout, V v) {
            this.e = coordinatorLayout;
            this.f1394f = v;
        }

        public void run() {
            OverScroller overScroller;
            if (this.f1394f != null && (overScroller = a.this.e) != null) {
                if (overScroller.computeScrollOffset()) {
                    a aVar = a.this;
                    aVar.c(this.e, this.f1394f, aVar.e.getCurrY());
                    v.a((View) this.f1394f, (Runnable) this);
                    return;
                }
                a.this.e(this.e, this.f1394f);
            }
        }
    }

    public a() {
    }

    private void d() {
        if (this.f1393j == null) {
            this.f1393j = VelocityTracker.obtain();
        }
    }

    /* access modifiers changed from: package-private */
    public abstract boolean a(V v);

    /* JADX WARNING: Code restructure failed: missing block: B:14:0x002c, code lost:
        if (r0 != 3) goto L_0x0083;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean a(androidx.coordinatorlayout.widget.CoordinatorLayout r5, V r6, android.view.MotionEvent r7) {
        /*
            r4 = this;
            int r0 = r4.f1392i
            if (r0 >= 0) goto L_0x0012
            android.content.Context r0 = r5.getContext()
            android.view.ViewConfiguration r0 = android.view.ViewConfiguration.get(r0)
            int r0 = r0.getScaledTouchSlop()
            r4.f1392i = r0
        L_0x0012:
            int r0 = r7.getAction()
            r1 = 2
            r2 = 1
            if (r0 != r1) goto L_0x001f
            boolean r0 = r4.f1389f
            if (r0 == 0) goto L_0x001f
            return r2
        L_0x001f:
            int r0 = r7.getActionMasked()
            r3 = 0
            if (r0 == 0) goto L_0x0060
            r5 = -1
            if (r0 == r2) goto L_0x0051
            if (r0 == r1) goto L_0x002f
            r6 = 3
            if (r0 == r6) goto L_0x0051
            goto L_0x0083
        L_0x002f:
            int r6 = r4.f1390g
            if (r6 != r5) goto L_0x0034
            goto L_0x0083
        L_0x0034:
            int r6 = r7.findPointerIndex(r6)
            if (r6 != r5) goto L_0x003b
            goto L_0x0083
        L_0x003b:
            float r5 = r7.getY(r6)
            int r5 = (int) r5
            int r6 = r4.f1391h
            int r6 = r5 - r6
            int r6 = java.lang.Math.abs(r6)
            int r0 = r4.f1392i
            if (r6 <= r0) goto L_0x0083
            r4.f1389f = r2
            r4.f1391h = r5
            goto L_0x0083
        L_0x0051:
            r4.f1389f = r3
            r4.f1390g = r5
            android.view.VelocityTracker r5 = r4.f1393j
            if (r5 == 0) goto L_0x0083
            r5.recycle()
            r5 = 0
            r4.f1393j = r5
            goto L_0x0083
        L_0x0060:
            r4.f1389f = r3
            float r0 = r7.getX()
            int r0 = (int) r0
            float r1 = r7.getY()
            int r1 = (int) r1
            boolean r2 = r4.a(r6)
            if (r2 == 0) goto L_0x0083
            boolean r5 = r5.a((android.view.View) r6, (int) r0, (int) r1)
            if (r5 == 0) goto L_0x0083
            r4.f1391h = r1
            int r5 = r7.getPointerId(r3)
            r4.f1390g = r5
            r4.d()
        L_0x0083:
            android.view.VelocityTracker r5 = r4.f1393j
            if (r5 == 0) goto L_0x008a
            r5.addMovement(r7)
        L_0x008a:
            boolean r5 = r4.f1389f
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.material.appbar.a.a(androidx.coordinatorlayout.widget.CoordinatorLayout, android.view.View, android.view.MotionEvent):boolean");
    }

    /* access modifiers changed from: package-private */
    public abstract int b(V v);

    /* access modifiers changed from: package-private */
    public abstract int b(CoordinatorLayout coordinatorLayout, V v, int i2, int i3, int i4);

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0021, code lost:
        if (r0 != 3) goto L_0x00ae;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean b(androidx.coordinatorlayout.widget.CoordinatorLayout r12, V r13, android.view.MotionEvent r14) {
        /*
            r11 = this;
            int r0 = r11.f1392i
            if (r0 >= 0) goto L_0x0012
            android.content.Context r0 = r12.getContext()
            android.view.ViewConfiguration r0 = android.view.ViewConfiguration.get(r0)
            int r0 = r0.getScaledTouchSlop()
            r11.f1392i = r0
        L_0x0012:
            int r0 = r14.getActionMasked()
            r1 = 1
            r2 = 0
            if (r0 == 0) goto L_0x008d
            r3 = -1
            if (r0 == r1) goto L_0x005c
            r4 = 2
            if (r0 == r4) goto L_0x0025
            r12 = 3
            if (r0 == r12) goto L_0x007e
            goto L_0x00ae
        L_0x0025:
            int r0 = r11.f1390g
            int r0 = r14.findPointerIndex(r0)
            if (r0 != r3) goto L_0x002e
            return r2
        L_0x002e:
            float r0 = r14.getY(r0)
            int r0 = (int) r0
            int r2 = r11.f1391h
            int r2 = r2 - r0
            boolean r3 = r11.f1389f
            if (r3 != 0) goto L_0x0049
            int r3 = java.lang.Math.abs(r2)
            int r4 = r11.f1392i
            if (r3 <= r4) goto L_0x0049
            r11.f1389f = r1
            if (r2 <= 0) goto L_0x0048
            int r2 = r2 - r4
            goto L_0x0049
        L_0x0048:
            int r2 = r2 + r4
        L_0x0049:
            r6 = r2
            boolean r2 = r11.f1389f
            if (r2 == 0) goto L_0x00ae
            r11.f1391h = r0
            int r7 = r11.b(r13)
            r8 = 0
            r3 = r11
            r4 = r12
            r5 = r13
            r3.a((androidx.coordinatorlayout.widget.CoordinatorLayout) r4, r5, (int) r6, (int) r7, (int) r8)
            goto L_0x00ae
        L_0x005c:
            android.view.VelocityTracker r0 = r11.f1393j
            if (r0 == 0) goto L_0x007e
            r0.addMovement(r14)
            android.view.VelocityTracker r0 = r11.f1393j
            r4 = 1000(0x3e8, float:1.401E-42)
            r0.computeCurrentVelocity(r4)
            android.view.VelocityTracker r0 = r11.f1393j
            int r4 = r11.f1390g
            float r10 = r0.getYVelocity(r4)
            int r0 = r11.c(r13)
            int r8 = -r0
            r9 = 0
            r5 = r11
            r6 = r12
            r7 = r13
            r5.a((androidx.coordinatorlayout.widget.CoordinatorLayout) r6, r7, (int) r8, (int) r9, (float) r10)
        L_0x007e:
            r11.f1389f = r2
            r11.f1390g = r3
            android.view.VelocityTracker r12 = r11.f1393j
            if (r12 == 0) goto L_0x00ae
            r12.recycle()
            r12 = 0
            r11.f1393j = r12
            goto L_0x00ae
        L_0x008d:
            float r0 = r14.getX()
            int r0 = (int) r0
            float r3 = r14.getY()
            int r3 = (int) r3
            boolean r12 = r12.a((android.view.View) r13, (int) r0, (int) r3)
            if (r12 == 0) goto L_0x00b6
            boolean r12 = r11.a(r13)
            if (r12 == 0) goto L_0x00b6
            r11.f1391h = r3
            int r12 = r14.getPointerId(r2)
            r11.f1390g = r12
            r11.d()
        L_0x00ae:
            android.view.VelocityTracker r12 = r11.f1393j
            if (r12 == 0) goto L_0x00b5
            r12.addMovement(r14)
        L_0x00b5:
            return r1
        L_0x00b6:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.material.appbar.a.b(androidx.coordinatorlayout.widget.CoordinatorLayout, android.view.View, android.view.MotionEvent):boolean");
    }

    /* access modifiers changed from: package-private */
    public abstract int c();

    /* access modifiers changed from: package-private */
    public abstract int c(V v);

    /* access modifiers changed from: package-private */
    public int c(CoordinatorLayout coordinatorLayout, V v, int i2) {
        return b(coordinatorLayout, v, i2, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    /* access modifiers changed from: package-private */
    public abstract void e(CoordinatorLayout coordinatorLayout, V v);

    public a(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    /* access modifiers changed from: package-private */
    public final int a(CoordinatorLayout coordinatorLayout, V v, int i2, int i3, int i4) {
        return b(coordinatorLayout, v, c() - i2, i3, i4);
    }

    /* access modifiers changed from: package-private */
    public final boolean a(CoordinatorLayout coordinatorLayout, V v, int i2, int i3, float f2) {
        V v2 = v;
        Runnable runnable = this.d;
        if (runnable != null) {
            v.removeCallbacks(runnable);
            this.d = null;
        }
        if (this.e == null) {
            this.e = new OverScroller(v.getContext());
        }
        this.e.fling(0, b(), 0, Math.round(f2), 0, 0, i2, i3);
        if (this.e.computeScrollOffset()) {
            CoordinatorLayout coordinatorLayout2 = coordinatorLayout;
            C0074a aVar = new C0074a(coordinatorLayout, v);
            this.d = aVar;
            v.a((View) v, (Runnable) aVar);
            return true;
        }
        CoordinatorLayout coordinatorLayout3 = coordinatorLayout;
        e(coordinatorLayout, v);
        return false;
    }
}
