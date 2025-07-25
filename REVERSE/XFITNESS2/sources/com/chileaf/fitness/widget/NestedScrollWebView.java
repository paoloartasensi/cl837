package com.chileaf.fitness.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.VelocityTracker;
import android.view.ViewConfiguration;
import android.webkit.WebView;
import android.widget.OverScroller;
import androidx.core.h.j;
import androidx.core.h.m;
import androidx.core.h.v;

public class NestedScrollWebView extends WebView implements j {
    private final int[] e = new int[2];

    /* renamed from: f  reason: collision with root package name */
    private final int[] f1304f = new int[2];

    /* renamed from: g  reason: collision with root package name */
    private int f1305g;

    /* renamed from: h  reason: collision with root package name */
    private VelocityTracker f1306h;

    /* renamed from: i  reason: collision with root package name */
    private int f1307i;

    /* renamed from: j  reason: collision with root package name */
    private int f1308j;
    private OverScroller k;
    private int l;
    private m m;

    public NestedScrollWebView(Context context) {
        super(context);
        a();
    }

    private void a() {
        this.m = new m(this);
        setNestedScrollingEnabled(true);
        this.k = new OverScroller(getContext());
        ViewConfiguration viewConfiguration = ViewConfiguration.get(getContext());
        this.f1307i = viewConfiguration.getScaledMinimumFlingVelocity();
        this.f1308j = viewConfiguration.getScaledMaximumFlingVelocity();
    }

    private void b() {
        if (this.f1306h == null) {
            this.f1306h = VelocityTracker.obtain();
        }
    }

    private void c() {
        VelocityTracker velocityTracker = this.f1306h;
        if (velocityTracker != null) {
            velocityTracker.recycle();
            this.f1306h = null;
        }
    }

    public void computeScroll() {
        int i2;
        int i3;
        super.computeScroll();
        if (this.k.computeScrollOffset()) {
            int currY = this.k.getCurrY();
            int i4 = currY - this.l;
            if (i4 != 0) {
                int scrollY = getScrollY();
                if (scrollY == 0) {
                    i2 = i4;
                    i3 = 0;
                } else {
                    int i5 = scrollY + i4;
                    if (i5 < 0) {
                        i3 = -scrollY;
                        i2 = i5;
                    } else {
                        i3 = i4;
                        i2 = 0;
                    }
                }
                a(0, i3, 0, i2, (int[]) null, 1);
            }
            this.l = currY;
            v.H(this);
            return;
        }
        if (c(1)) {
            a(1);
        }
        this.l = 0;
    }

    public boolean dispatchNestedFling(float f2, float f3, boolean z) {
        return this.m.a(f2, f3, z);
    }

    public boolean dispatchNestedPreFling(float f2, float f3) {
        return this.m.a(f2, f3);
    }

    public boolean dispatchNestedPreScroll(int i2, int i3, int[] iArr, int[] iArr2) {
        return this.m.a(i2, i3, iArr, iArr2);
    }

    public boolean dispatchNestedScroll(int i2, int i3, int i4, int i5, int[] iArr) {
        return this.m.a(i2, i3, i4, i5, iArr);
    }

    public boolean hasNestedScrollingParent() {
        return this.m.a();
    }

    public boolean isNestedScrollingEnabled() {
        return this.m.b();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:6:0x0014, code lost:
        if (r1 != 3) goto L_0x00a0;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean onTouchEvent(android.view.MotionEvent r12) {
        /*
            r11 = this;
            r11.b()
            android.view.MotionEvent r0 = android.view.MotionEvent.obtain(r12)
            int r1 = r12.getAction()
            r2 = 2
            if (r1 == 0) goto L_0x007f
            r3 = 1
            if (r1 == r3) goto L_0x005d
            if (r1 == r2) goto L_0x0018
            r12 = 3
            if (r1 == r12) goto L_0x0078
            goto L_0x00a0
        L_0x0018:
            float r12 = r12.getRawY()
            int r12 = (int) r12
            int r1 = r11.f1305g
            int r1 = r1 - r12
            int[] r2 = r11.e
            int[] r4 = r11.f1304f
            r5 = 0
            boolean r2 = r11.dispatchNestedPreScroll(r5, r1, r2, r4)
            r4 = 0
            if (r2 == 0) goto L_0x0034
            int[] r2 = r11.e
            r2 = r2[r3]
            float r2 = (float) r2
            r0.offsetLocation(r4, r2)
        L_0x0034:
            r11.f1305g = r12
            int r12 = r11.getScrollY()
            if (r12 != 0) goto L_0x003e
            r9 = r1
            goto L_0x0049
        L_0x003e:
            int r12 = r12 + r1
            if (r12 >= 0) goto L_0x0048
            int r2 = -r12
            float r2 = (float) r2
            r0.offsetLocation(r4, r2)
            r9 = r12
            goto L_0x0049
        L_0x0048:
            r9 = 0
        L_0x0049:
            android.view.VelocityTracker r12 = r11.f1306h
            r12.addMovement(r0)
            boolean r12 = super.onTouchEvent(r0)
            r6 = 0
            int r7 = r1 - r9
            r8 = 0
            int[] r10 = r11.f1304f
            r5 = r11
            r5.dispatchNestedScroll(r6, r7, r8, r9, r10)
            return r12
        L_0x005d:
            android.view.VelocityTracker r12 = r11.f1306h
            r1 = 1000(0x3e8, float:1.401E-42)
            int r2 = r11.f1308j
            float r2 = (float) r2
            r12.computeCurrentVelocity(r1, r2)
            float r12 = r12.getYVelocity()
            int r12 = (int) r12
            int r1 = java.lang.Math.abs(r12)
            int r2 = r11.f1307i
            if (r1 <= r2) goto L_0x0078
            int r12 = -r12
            r11.b(r12)
        L_0x0078:
            r11.stopNestedScroll()
            r11.c()
            goto L_0x00a0
        L_0x007f:
            float r12 = r12.getRawY()
            int r12 = (int) r12
            r11.f1305g = r12
            r11.startNestedScroll(r2)
            android.view.VelocityTracker r12 = r11.f1306h
            r12.addMovement(r0)
            android.widget.OverScroller r12 = r11.k
            r12.computeScrollOffset()
            android.widget.OverScroller r12 = r11.k
            boolean r12 = r12.isFinished()
            if (r12 != 0) goto L_0x00a0
            android.widget.OverScroller r12 = r11.k
            r12.abortAnimation()
        L_0x00a0:
            boolean r12 = super.onTouchEvent(r0)
            return r12
        */
        throw new UnsupportedOperationException("Method not decompiled: com.chileaf.fitness.widget.NestedScrollWebView.onTouchEvent(android.view.MotionEvent):boolean");
    }

    public void setNestedScrollingEnabled(boolean z) {
        this.m.a(z);
    }

    public boolean startNestedScroll(int i2) {
        return this.m.b(i2);
    }

    public void stopNestedScroll() {
        this.m.c();
    }

    public void b(int i2) {
        a(2, 1);
        this.k.fling(getScrollX(), getScrollY(), 0, i2, 0, 0, Integer.MIN_VALUE, Integer.MAX_VALUE, 0, 0);
        this.l = getScrollY();
        v.H(this);
    }

    public boolean c(int i2) {
        return this.m.a(i2);
    }

    public NestedScrollWebView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        a();
    }

    public boolean a(int i2, int i3) {
        return this.m.a(i2, i3);
    }

    public void a(int i2) {
        this.m.c(i2);
    }

    public NestedScrollWebView(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        a();
    }

    public boolean a(int i2, int i3, int i4, int i5, int[] iArr, int i6) {
        return this.m.a(i2, i3, i4, i5, iArr, i6);
    }
}
