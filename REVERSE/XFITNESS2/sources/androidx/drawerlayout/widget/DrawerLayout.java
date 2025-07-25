package androidx.drawerlayout.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.WindowInsets;
import android.view.accessibility.AccessibilityEvent;
import androidx.core.h.e0.d;
import androidx.core.h.v;
import androidx.customview.a.c;
import androidx.customview.view.AbsSavedState;
import java.util.ArrayList;
import java.util.List;

public class DrawerLayout extends ViewGroup {
    private static final int[] O = {16843828};
    static final int[] P = {16842931};
    static final boolean Q = (Build.VERSION.SDK_INT >= 19);
    private static final boolean R;
    private Drawable A;
    private Drawable B;
    private Drawable C;
    private CharSequence D;
    private CharSequence E;
    private Object F;
    private boolean G;
    private Drawable H;
    private Drawable I;
    private Drawable J;
    private Drawable K;
    private final ArrayList<View> L;
    private Rect M;
    private Matrix N;
    private final c e;

    /* renamed from: f  reason: collision with root package name */
    private float f571f;

    /* renamed from: g  reason: collision with root package name */
    private int f572g;

    /* renamed from: h  reason: collision with root package name */
    private int f573h;

    /* renamed from: i  reason: collision with root package name */
    private float f574i;

    /* renamed from: j  reason: collision with root package name */
    private Paint f575j;
    private final androidx.customview.a.c k;
    private final androidx.customview.a.c l;
    private final f m;
    private final f n;
    private int o;
    private boolean p;
    private boolean q;
    private int r;
    private int s;
    private int t;
    private int u;
    private boolean v;
    private d w;
    private List<d> x;
    private float y;
    private float z;

    class a implements View.OnApplyWindowInsetsListener {
        a(DrawerLayout drawerLayout) {
        }

        public WindowInsets onApplyWindowInsets(View view, WindowInsets windowInsets) {
            ((DrawerLayout) view).a((Object) windowInsets, windowInsets.getSystemWindowInsetTop() > 0);
            return windowInsets.consumeSystemWindowInsets();
        }
    }

    static final class c extends androidx.core.h.a {
        c() {
        }

        public void a(View view, androidx.core.h.e0.d dVar) {
            super.a(view, dVar);
            if (!DrawerLayout.m(view)) {
                dVar.b((View) null);
            }
        }
    }

    public interface d {
        void a(int i2);

        void a(View view);

        void a(View view, float f2);

        void b(View view);
    }

    private class f extends c.C0030c {
        private final int a;
        private androidx.customview.a.c b;
        private final Runnable c = new a();

        class a implements Runnable {
            a() {
            }

            public void run() {
                f.this.a();
            }
        }

        f(int i2) {
            this.a = i2;
        }

        public void a(androidx.customview.a.c cVar) {
            this.b = cVar;
        }

        public void b() {
            DrawerLayout.this.removeCallbacks(this.c);
        }

        public boolean b(int i2) {
            return false;
        }

        public void c(int i2) {
            DrawerLayout.this.a(this.a, i2, this.b.c());
        }

        private void c() {
            int i2 = 3;
            if (this.a == 3) {
                i2 = 5;
            }
            View b2 = DrawerLayout.this.b(i2);
            if (b2 != null) {
                DrawerLayout.this.a(b2);
            }
        }

        public void a(View view, int i2, int i3, int i4, int i5) {
            float f2;
            int width = view.getWidth();
            if (DrawerLayout.this.a(view, 3)) {
                f2 = (float) (i2 + width);
            } else {
                f2 = (float) (DrawerLayout.this.getWidth() - i2);
            }
            float f3 = f2 / ((float) width);
            DrawerLayout.this.c(view, f3);
            view.setVisibility(f3 == 0.0f ? 4 : 0);
            DrawerLayout.this.invalidate();
        }

        public boolean b(View view, int i2) {
            return DrawerLayout.this.i(view) && DrawerLayout.this.a(view, this.a) && DrawerLayout.this.d(view) == 0;
        }

        public void b(int i2, int i3) {
            DrawerLayout.this.postDelayed(this.c, 160);
        }

        public int b(View view, int i2, int i3) {
            return view.getTop();
        }

        public void a(View view, int i2) {
            ((e) view.getLayoutParams()).c = false;
            c();
        }

        public void a(View view, float f2, float f3) {
            int i2;
            float f4 = DrawerLayout.this.f(view);
            int width = view.getWidth();
            if (DrawerLayout.this.a(view, 3)) {
                i2 = (f2 > 0.0f || (f2 == 0.0f && f4 > 0.5f)) ? 0 : -width;
            } else {
                int width2 = DrawerLayout.this.getWidth();
                if (f2 < 0.0f || (f2 == 0.0f && f4 > 0.5f)) {
                    width2 -= width;
                }
                i2 = width2;
            }
            this.b.d(i2, view.getTop());
            DrawerLayout.this.invalidate();
        }

        /* access modifiers changed from: package-private */
        public void a() {
            View view;
            int i2;
            int d2 = this.b.d();
            int i3 = 0;
            boolean z = this.a == 3;
            if (z) {
                view = DrawerLayout.this.b(3);
                if (view != null) {
                    i3 = -view.getWidth();
                }
                i2 = i3 + d2;
            } else {
                view = DrawerLayout.this.b(5);
                i2 = DrawerLayout.this.getWidth() - d2;
            }
            if (view == null) {
                return;
            }
            if (((z && view.getLeft() < i2) || (!z && view.getLeft() > i2)) && DrawerLayout.this.d(view) == 0) {
                this.b.b(view, i2, view.getTop());
                ((e) view.getLayoutParams()).c = true;
                DrawerLayout.this.invalidate();
                c();
                DrawerLayout.this.a();
            }
        }

        public void a(int i2, int i3) {
            View view;
            if ((i2 & 1) == 1) {
                view = DrawerLayout.this.b(3);
            } else {
                view = DrawerLayout.this.b(5);
            }
            if (view != null && DrawerLayout.this.d(view) == 0) {
                this.b.a(view, i3);
            }
        }

        public int a(View view) {
            if (DrawerLayout.this.i(view)) {
                return view.getWidth();
            }
            return 0;
        }

        public int a(View view, int i2, int i3) {
            if (DrawerLayout.this.a(view, 3)) {
                return Math.max(-view.getWidth(), Math.min(i2, 0));
            }
            int width = DrawerLayout.this.getWidth();
            return Math.max(width - view.getWidth(), Math.min(i2, width));
        }
    }

    static {
        boolean z2 = true;
        if (Build.VERSION.SDK_INT < 21) {
            z2 = false;
        }
        R = z2;
    }

    public DrawerLayout(Context context) {
        this(context, (AttributeSet) null);
    }

    static String g(int i2) {
        if ((i2 & 3) == 3) {
            return "LEFT";
        }
        return (i2 & 5) == 5 ? "RIGHT" : Integer.toHexString(i2);
    }

    private Drawable h() {
        int o2 = v.o(this);
        if (o2 == 0) {
            Drawable drawable = this.I;
            if (drawable != null) {
                a(drawable, o2);
                return this.I;
            }
        } else {
            Drawable drawable2 = this.H;
            if (drawable2 != null) {
                a(drawable2, o2);
                return this.H;
            }
        }
        return this.K;
    }

    private void i() {
        if (!R) {
            this.B = g();
            this.C = h();
        }
    }

    private static boolean l(View view) {
        Drawable background = view.getBackground();
        if (background == null || background.getOpacity() != -1) {
            return false;
        }
        return true;
    }

    static boolean m(View view) {
        return (v.m(view) == 4 || v.m(view) == 2) ? false : true;
    }

    public void a(Object obj, boolean z2) {
        this.F = obj;
        this.G = z2;
        setWillNotDraw(!z2 && getBackground() == null);
        requestLayout();
    }

    public void addFocusables(ArrayList<View> arrayList, int i2, int i3) {
        if (getDescendantFocusability() != 393216) {
            int childCount = getChildCount();
            boolean z2 = false;
            for (int i4 = 0; i4 < childCount; i4++) {
                View childAt = getChildAt(i4);
                if (!i(childAt)) {
                    this.L.add(childAt);
                } else if (h(childAt)) {
                    childAt.addFocusables(arrayList, i2, i3);
                    z2 = true;
                }
            }
            if (!z2) {
                int size = this.L.size();
                for (int i5 = 0; i5 < size; i5++) {
                    View view = this.L.get(i5);
                    if (view.getVisibility() == 0) {
                        view.addFocusables(arrayList, i2, i3);
                    }
                }
            }
            this.L.clear();
        }
    }

    public void addView(View view, int i2, ViewGroup.LayoutParams layoutParams) {
        super.addView(view, i2, layoutParams);
        if (c() != null || i(view)) {
            v.h(view, 4);
        } else {
            v.h(view, 1);
        }
        if (!Q) {
            v.a(view, (androidx.core.h.a) this.e);
        }
    }

    public void b(d dVar) {
        List<d> list;
        if (dVar != null && (list = this.x) != null) {
            list.remove(dVar);
        }
    }

    public int c(int i2) {
        int o2 = v.o(this);
        if (i2 == 3) {
            int i3 = this.r;
            if (i3 != 3) {
                return i3;
            }
            int i4 = o2 == 0 ? this.t : this.u;
            if (i4 != 3) {
                return i4;
            }
            return 0;
        } else if (i2 == 5) {
            int i5 = this.s;
            if (i5 != 3) {
                return i5;
            }
            int i6 = o2 == 0 ? this.u : this.t;
            if (i6 != 3) {
                return i6;
            }
            return 0;
        } else if (i2 == 8388611) {
            int i7 = this.t;
            if (i7 != 3) {
                return i7;
            }
            int i8 = o2 == 0 ? this.r : this.s;
            if (i8 != 3) {
                return i8;
            }
            return 0;
        } else if (i2 != 8388613) {
            return 0;
        } else {
            int i9 = this.u;
            if (i9 != 3) {
                return i9;
            }
            int i10 = o2 == 0 ? this.s : this.r;
            if (i10 != 3) {
                return i10;
            }
            return 0;
        }
    }

    /* access modifiers changed from: protected */
    public boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return (layoutParams instanceof e) && super.checkLayoutParams(layoutParams);
    }

    public void computeScroll() {
        int childCount = getChildCount();
        float f2 = 0.0f;
        for (int i2 = 0; i2 < childCount; i2++) {
            f2 = Math.max(f2, ((e) getChildAt(i2).getLayoutParams()).b);
        }
        this.f574i = f2;
        boolean a2 = this.k.a(true);
        boolean a3 = this.l.a(true);
        if (a2 || a3) {
            v.H(this);
        }
    }

    public int d(View view) {
        if (i(view)) {
            return c(((e) view.getLayoutParams()).a);
        }
        throw new IllegalArgumentException("View " + view + " is not a drawer");
    }

    public boolean dispatchGenericMotionEvent(MotionEvent motionEvent) {
        if ((motionEvent.getSource() & 2) == 0 || motionEvent.getAction() == 10 || this.f574i <= 0.0f) {
            return super.dispatchGenericMotionEvent(motionEvent);
        }
        int childCount = getChildCount();
        if (childCount == 0) {
            return false;
        }
        float x2 = motionEvent.getX();
        float y2 = motionEvent.getY();
        for (int i2 = childCount - 1; i2 >= 0; i2--) {
            View childAt = getChildAt(i2);
            if (a(x2, y2, childAt) && !g(childAt) && a(motionEvent, childAt)) {
                return true;
            }
        }
        return false;
    }

    /* access modifiers changed from: protected */
    public boolean drawChild(Canvas canvas, View view, long j2) {
        Canvas canvas2 = canvas;
        View view2 = view;
        int height = getHeight();
        boolean g2 = g(view2);
        int width = getWidth();
        int save = canvas.save();
        int i2 = 0;
        if (g2) {
            int childCount = getChildCount();
            int i3 = 0;
            for (int i4 = 0; i4 < childCount; i4++) {
                View childAt = getChildAt(i4);
                if (childAt != view2 && childAt.getVisibility() == 0 && l(childAt) && i(childAt) && childAt.getHeight() >= height) {
                    if (a(childAt, 3)) {
                        int right = childAt.getRight();
                        if (right > i3) {
                            i3 = right;
                        }
                    } else {
                        int left = childAt.getLeft();
                        if (left < width) {
                            width = left;
                        }
                    }
                }
            }
            canvas.clipRect(i3, 0, width, getHeight());
            i2 = i3;
        }
        boolean drawChild = super.drawChild(canvas, view, j2);
        canvas.restoreToCount(save);
        float f2 = this.f574i;
        if (f2 > 0.0f && g2) {
            int i5 = this.f573h;
            this.f575j.setColor((i5 & 16777215) | (((int) (((float) ((-16777216 & i5) >>> 24)) * f2)) << 24));
            canvas.drawRect((float) i2, 0.0f, (float) width, (float) getHeight(), this.f575j);
        } else if (this.B != null && a(view2, 3)) {
            int intrinsicWidth = this.B.getIntrinsicWidth();
            int right2 = view.getRight();
            float max = Math.max(0.0f, Math.min(((float) right2) / ((float) this.k.d()), 1.0f));
            this.B.setBounds(right2, view.getTop(), intrinsicWidth + right2, view.getBottom());
            this.B.setAlpha((int) (max * 255.0f));
            this.B.draw(canvas);
        } else if (this.C != null && a(view2, 5)) {
            int intrinsicWidth2 = this.C.getIntrinsicWidth();
            int left2 = view.getLeft();
            float max2 = Math.max(0.0f, Math.min(((float) (getWidth() - left2)) / ((float) this.l.d()), 1.0f));
            this.C.setBounds(left2 - intrinsicWidth2, view.getTop(), left2, view.getBottom());
            this.C.setAlpha((int) (max2 * 255.0f));
            this.C.draw(canvas);
        }
        return drawChild;
    }

    /* access modifiers changed from: package-private */
    public int e(View view) {
        return androidx.core.h.d.a(((e) view.getLayoutParams()).a, v.o(this));
    }

    /* access modifiers changed from: package-private */
    public float f(View view) {
        return ((e) view.getLayoutParams()).b;
    }

    /* access modifiers changed from: protected */
    public ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new e(-1, -1);
    }

    /* access modifiers changed from: protected */
    public ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        if (layoutParams instanceof e) {
            return new e((e) layoutParams);
        }
        return layoutParams instanceof ViewGroup.MarginLayoutParams ? new e((ViewGroup.MarginLayoutParams) layoutParams) : new e(layoutParams);
    }

    public float getDrawerElevation() {
        if (R) {
            return this.f571f;
        }
        return 0.0f;
    }

    public Drawable getStatusBarBackgroundDrawable() {
        return this.A;
    }

    public boolean j(View view) {
        if (i(view)) {
            return ((e) view.getLayoutParams()).b > 0.0f;
        }
        throw new IllegalArgumentException("View " + view + " is not a drawer");
    }

    public void k(View view) {
        b(view, true);
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.q = true;
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.q = true;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:6:0x0012, code lost:
        r0 = r4.F;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onDraw(android.graphics.Canvas r5) {
        /*
            r4 = this;
            super.onDraw(r5)
            boolean r0 = r4.G
            if (r0 == 0) goto L_0x002e
            android.graphics.drawable.Drawable r0 = r4.A
            if (r0 == 0) goto L_0x002e
            int r0 = android.os.Build.VERSION.SDK_INT
            r1 = 21
            r2 = 0
            if (r0 < r1) goto L_0x001d
            java.lang.Object r0 = r4.F
            if (r0 == 0) goto L_0x001d
            android.view.WindowInsets r0 = (android.view.WindowInsets) r0
            int r0 = r0.getSystemWindowInsetTop()
            goto L_0x001e
        L_0x001d:
            r0 = 0
        L_0x001e:
            if (r0 <= 0) goto L_0x002e
            android.graphics.drawable.Drawable r1 = r4.A
            int r3 = r4.getWidth()
            r1.setBounds(r2, r2, r3, r0)
            android.graphics.drawable.Drawable r0 = r4.A
            r0.draw(r5)
        L_0x002e:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.drawerlayout.widget.DrawerLayout.onDraw(android.graphics.Canvas):void");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:13:0x004b, code lost:
        r7 = r6.k.b((int) r0, (int) r7);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:5:0x001b, code lost:
        if (r0 != 3) goto L_0x0036;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean onInterceptTouchEvent(android.view.MotionEvent r7) {
        /*
            r6 = this;
            int r0 = r7.getActionMasked()
            androidx.customview.a.c r1 = r6.k
            boolean r1 = r1.b((android.view.MotionEvent) r7)
            androidx.customview.a.c r2 = r6.l
            boolean r2 = r2.b((android.view.MotionEvent) r7)
            r1 = r1 | r2
            r2 = 1
            r3 = 0
            if (r0 == 0) goto L_0x0038
            if (r0 == r2) goto L_0x0031
            r7 = 2
            r4 = 3
            if (r0 == r7) goto L_0x001e
            if (r0 == r4) goto L_0x0031
            goto L_0x0036
        L_0x001e:
            androidx.customview.a.c r7 = r6.k
            boolean r7 = r7.a((int) r4)
            if (r7 == 0) goto L_0x0036
            androidx.drawerlayout.widget.DrawerLayout$f r7 = r6.m
            r7.b()
            androidx.drawerlayout.widget.DrawerLayout$f r7 = r6.n
            r7.b()
            goto L_0x0036
        L_0x0031:
            r6.a((boolean) r2)
            r6.v = r3
        L_0x0036:
            r7 = 0
            goto L_0x0060
        L_0x0038:
            float r0 = r7.getX()
            float r7 = r7.getY()
            r6.y = r0
            r6.z = r7
            float r4 = r6.f574i
            r5 = 0
            int r4 = (r4 > r5 ? 1 : (r4 == r5 ? 0 : -1))
            if (r4 <= 0) goto L_0x005d
            androidx.customview.a.c r4 = r6.k
            int r0 = (int) r0
            int r7 = (int) r7
            android.view.View r7 = r4.b((int) r0, (int) r7)
            if (r7 == 0) goto L_0x005d
            boolean r7 = r6.g((android.view.View) r7)
            if (r7 == 0) goto L_0x005d
            r7 = 1
            goto L_0x005e
        L_0x005d:
            r7 = 0
        L_0x005e:
            r6.v = r3
        L_0x0060:
            if (r1 != 0) goto L_0x0070
            if (r7 != 0) goto L_0x0070
            boolean r7 = r6.e()
            if (r7 != 0) goto L_0x0070
            boolean r7 = r6.v
            if (r7 == 0) goto L_0x006f
            goto L_0x0070
        L_0x006f:
            r2 = 0
        L_0x0070:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.drawerlayout.widget.DrawerLayout.onInterceptTouchEvent(android.view.MotionEvent):boolean");
    }

    public boolean onKeyDown(int i2, KeyEvent keyEvent) {
        if (i2 != 4 || !f()) {
            return super.onKeyDown(i2, keyEvent);
        }
        keyEvent.startTracking();
        return true;
    }

    public boolean onKeyUp(int i2, KeyEvent keyEvent) {
        if (i2 != 4) {
            return super.onKeyUp(i2, keyEvent);
        }
        View d2 = d();
        if (d2 != null && d(d2) == 0) {
            b();
        }
        return d2 != null;
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z2, int i2, int i3, int i4, int i5) {
        float f2;
        int i6;
        this.p = true;
        int i7 = i4 - i2;
        int childCount = getChildCount();
        for (int i8 = 0; i8 < childCount; i8++) {
            View childAt = getChildAt(i8);
            if (childAt.getVisibility() != 8) {
                e eVar = (e) childAt.getLayoutParams();
                if (g(childAt)) {
                    int i9 = eVar.leftMargin;
                    childAt.layout(i9, eVar.topMargin, childAt.getMeasuredWidth() + i9, eVar.topMargin + childAt.getMeasuredHeight());
                } else {
                    int measuredWidth = childAt.getMeasuredWidth();
                    int measuredHeight = childAt.getMeasuredHeight();
                    if (a(childAt, 3)) {
                        float f3 = (float) measuredWidth;
                        i6 = (-measuredWidth) + ((int) (eVar.b * f3));
                        f2 = ((float) (measuredWidth + i6)) / f3;
                    } else {
                        float f4 = (float) measuredWidth;
                        int i10 = i7 - ((int) (eVar.b * f4));
                        f2 = ((float) (i7 - i10)) / f4;
                        i6 = i10;
                    }
                    boolean z3 = f2 != eVar.b;
                    int i11 = eVar.a & 112;
                    if (i11 == 16) {
                        int i12 = i5 - i3;
                        int i13 = (i12 - measuredHeight) / 2;
                        int i14 = eVar.topMargin;
                        if (i13 < i14) {
                            i13 = i14;
                        } else {
                            int i15 = i13 + measuredHeight;
                            int i16 = eVar.bottomMargin;
                            if (i15 > i12 - i16) {
                                i13 = (i12 - i16) - measuredHeight;
                            }
                        }
                        childAt.layout(i6, i13, measuredWidth + i6, measuredHeight + i13);
                    } else if (i11 != 80) {
                        int i17 = eVar.topMargin;
                        childAt.layout(i6, i17, measuredWidth + i6, measuredHeight + i17);
                    } else {
                        int i18 = i5 - i3;
                        childAt.layout(i6, (i18 - eVar.bottomMargin) - childAt.getMeasuredHeight(), measuredWidth + i6, i18 - eVar.bottomMargin);
                    }
                    if (z3) {
                        c(childAt, f2);
                    }
                    int i19 = eVar.b > 0.0f ? 0 : 4;
                    if (childAt.getVisibility() != i19) {
                        childAt.setVisibility(i19);
                    }
                }
            }
        }
        this.p = false;
        this.q = false;
    }

    /* access modifiers changed from: protected */
    @SuppressLint({"WrongConstant"})
    public void onMeasure(int i2, int i3) {
        int mode = View.MeasureSpec.getMode(i2);
        int mode2 = View.MeasureSpec.getMode(i3);
        int size = View.MeasureSpec.getSize(i2);
        int size2 = View.MeasureSpec.getSize(i3);
        if (!(mode == 1073741824 && mode2 == 1073741824)) {
            if (isInEditMode()) {
                if (mode != Integer.MIN_VALUE && mode == 0) {
                    size = 300;
                }
                if (mode2 != Integer.MIN_VALUE && mode2 == 0) {
                    size2 = 300;
                }
            } else {
                throw new IllegalArgumentException("DrawerLayout must be measured with MeasureSpec.EXACTLY.");
            }
        }
        setMeasuredDimension(size, size2);
        int i4 = 0;
        boolean z2 = this.F != null && v.l(this);
        int o2 = v.o(this);
        int childCount = getChildCount();
        int i5 = 0;
        boolean z3 = false;
        boolean z4 = false;
        while (i5 < childCount) {
            View childAt = getChildAt(i5);
            if (childAt.getVisibility() != 8) {
                e eVar = (e) childAt.getLayoutParams();
                if (z2) {
                    int a2 = androidx.core.h.d.a(eVar.a, o2);
                    if (v.l(childAt)) {
                        if (Build.VERSION.SDK_INT >= 21) {
                            WindowInsets windowInsets = (WindowInsets) this.F;
                            if (a2 == 3) {
                                windowInsets = windowInsets.replaceSystemWindowInsets(windowInsets.getSystemWindowInsetLeft(), windowInsets.getSystemWindowInsetTop(), i4, windowInsets.getSystemWindowInsetBottom());
                            } else if (a2 == 5) {
                                windowInsets = windowInsets.replaceSystemWindowInsets(i4, windowInsets.getSystemWindowInsetTop(), windowInsets.getSystemWindowInsetRight(), windowInsets.getSystemWindowInsetBottom());
                            }
                            childAt.dispatchApplyWindowInsets(windowInsets);
                        }
                    } else if (Build.VERSION.SDK_INT >= 21) {
                        WindowInsets windowInsets2 = (WindowInsets) this.F;
                        if (a2 == 3) {
                            windowInsets2 = windowInsets2.replaceSystemWindowInsets(windowInsets2.getSystemWindowInsetLeft(), windowInsets2.getSystemWindowInsetTop(), i4, windowInsets2.getSystemWindowInsetBottom());
                        } else if (a2 == 5) {
                            windowInsets2 = windowInsets2.replaceSystemWindowInsets(i4, windowInsets2.getSystemWindowInsetTop(), windowInsets2.getSystemWindowInsetRight(), windowInsets2.getSystemWindowInsetBottom());
                        }
                        eVar.leftMargin = windowInsets2.getSystemWindowInsetLeft();
                        eVar.topMargin = windowInsets2.getSystemWindowInsetTop();
                        eVar.rightMargin = windowInsets2.getSystemWindowInsetRight();
                        eVar.bottomMargin = windowInsets2.getSystemWindowInsetBottom();
                    }
                }
                if (g(childAt)) {
                    childAt.measure(View.MeasureSpec.makeMeasureSpec((size - eVar.leftMargin) - eVar.rightMargin, 1073741824), View.MeasureSpec.makeMeasureSpec((size2 - eVar.topMargin) - eVar.bottomMargin, 1073741824));
                } else if (i(childAt)) {
                    if (R) {
                        float k2 = v.k(childAt);
                        float f2 = this.f571f;
                        if (k2 != f2) {
                            v.a(childAt, f2);
                        }
                    }
                    int e2 = e(childAt) & 7;
                    boolean z5 = e2 == 3;
                    if ((!z5 || !z3) && (z5 || !z4)) {
                        if (z5) {
                            z3 = true;
                        } else {
                            z4 = true;
                        }
                        childAt.measure(ViewGroup.getChildMeasureSpec(i2, this.f572g + eVar.leftMargin + eVar.rightMargin, eVar.width), ViewGroup.getChildMeasureSpec(i3, eVar.topMargin + eVar.bottomMargin, eVar.height));
                        i5++;
                        i4 = 0;
                    } else {
                        throw new IllegalStateException("Child drawer has absolute gravity " + g(e2) + " but this " + "DrawerLayout" + " already has a " + "drawer view along that edge");
                    }
                } else {
                    throw new IllegalStateException("Child " + childAt + " at index " + i5 + " does not have a valid layout_gravity - must be Gravity.LEFT, " + "Gravity.RIGHT or Gravity.NO_GRAVITY");
                }
            }
            int i6 = i2;
            int i7 = i3;
            i5++;
            i4 = 0;
        }
    }

    /* access modifiers changed from: protected */
    public void onRestoreInstanceState(Parcelable parcelable) {
        View b2;
        if (!(parcelable instanceof SavedState)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        SavedState savedState = (SavedState) parcelable;
        super.onRestoreInstanceState(savedState.a());
        int i2 = savedState.f576g;
        if (!(i2 == 0 || (b2 = b(i2)) == null)) {
            k(b2);
        }
        int i3 = savedState.f577h;
        if (i3 != 3) {
            a(i3, 3);
        }
        int i4 = savedState.f578i;
        if (i4 != 3) {
            a(i4, 5);
        }
        int i5 = savedState.f579j;
        if (i5 != 3) {
            a(i5, 8388611);
        }
        int i6 = savedState.k;
        if (i6 != 3) {
            a(i6, 8388613);
        }
    }

    public void onRtlPropertiesChanged(int i2) {
        i();
    }

    /* access modifiers changed from: protected */
    public Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        int childCount = getChildCount();
        int i2 = 0;
        while (true) {
            if (i2 >= childCount) {
                break;
            }
            e eVar = (e) getChildAt(i2).getLayoutParams();
            boolean z2 = true;
            boolean z3 = eVar.d == 1;
            if (eVar.d != 2) {
                z2 = false;
            }
            if (z3 || z2) {
                savedState.f576g = eVar.a;
            } else {
                i2++;
            }
        }
        savedState.f577h = this.r;
        savedState.f578i = this.s;
        savedState.f579j = this.t;
        savedState.k = this.u;
        return savedState;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:15:0x005b, code lost:
        if (d(r7) != 2) goto L_0x005e;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean onTouchEvent(android.view.MotionEvent r7) {
        /*
            r6 = this;
            androidx.customview.a.c r0 = r6.k
            r0.a((android.view.MotionEvent) r7)
            androidx.customview.a.c r0 = r6.l
            r0.a((android.view.MotionEvent) r7)
            int r0 = r7.getAction()
            r0 = r0 & 255(0xff, float:3.57E-43)
            r1 = 0
            r2 = 1
            if (r0 == 0) goto L_0x0062
            if (r0 == r2) goto L_0x0020
            r7 = 3
            if (r0 == r7) goto L_0x001a
            goto L_0x0070
        L_0x001a:
            r6.a((boolean) r2)
            r6.v = r1
            goto L_0x0070
        L_0x0020:
            float r0 = r7.getX()
            float r7 = r7.getY()
            androidx.customview.a.c r3 = r6.k
            int r4 = (int) r0
            int r5 = (int) r7
            android.view.View r3 = r3.b((int) r4, (int) r5)
            if (r3 == 0) goto L_0x005d
            boolean r3 = r6.g((android.view.View) r3)
            if (r3 == 0) goto L_0x005d
            float r3 = r6.y
            float r0 = r0 - r3
            float r3 = r6.z
            float r7 = r7 - r3
            androidx.customview.a.c r3 = r6.k
            int r3 = r3.e()
            float r0 = r0 * r0
            float r7 = r7 * r7
            float r0 = r0 + r7
            int r3 = r3 * r3
            float r7 = (float) r3
            int r7 = (r0 > r7 ? 1 : (r0 == r7 ? 0 : -1))
            if (r7 >= 0) goto L_0x005d
            android.view.View r7 = r6.c()
            if (r7 == 0) goto L_0x005d
            int r7 = r6.d((android.view.View) r7)
            r0 = 2
            if (r7 != r0) goto L_0x005e
        L_0x005d:
            r1 = 1
        L_0x005e:
            r6.a((boolean) r1)
            goto L_0x0070
        L_0x0062:
            float r0 = r7.getX()
            float r7 = r7.getY()
            r6.y = r0
            r6.z = r7
            r6.v = r1
        L_0x0070:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.drawerlayout.widget.DrawerLayout.onTouchEvent(android.view.MotionEvent):boolean");
    }

    public void requestDisallowInterceptTouchEvent(boolean z2) {
        super.requestDisallowInterceptTouchEvent(z2);
        if (z2) {
            a(true);
        }
    }

    public void requestLayout() {
        if (!this.p) {
            super.requestLayout();
        }
    }

    public void setDrawerElevation(float f2) {
        this.f571f = f2;
        for (int i2 = 0; i2 < getChildCount(); i2++) {
            View childAt = getChildAt(i2);
            if (i(childAt)) {
                v.a(childAt, this.f571f);
            }
        }
    }

    @Deprecated
    public void setDrawerListener(d dVar) {
        d dVar2 = this.w;
        if (dVar2 != null) {
            b(dVar2);
        }
        if (dVar != null) {
            a(dVar);
        }
        this.w = dVar;
    }

    public void setDrawerLockMode(int i2) {
        a(i2, 3);
        a(i2, 5);
    }

    public void setScrimColor(int i2) {
        this.f573h = i2;
        invalidate();
    }

    public void setStatusBarBackground(Drawable drawable) {
        this.A = drawable;
        invalidate();
    }

    public void setStatusBarBackgroundColor(int i2) {
        this.A = new ColorDrawable(i2);
        invalidate();
    }

    public DrawerLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    private Drawable g() {
        int o2 = v.o(this);
        if (o2 == 0) {
            Drawable drawable = this.H;
            if (drawable != null) {
                a(drawable, o2);
                return this.H;
            }
        } else {
            Drawable drawable2 = this.I;
            if (drawable2 != null) {
                a(drawable2, o2);
                return this.I;
            }
        }
        return this.J;
    }

    public void f(int i2) {
        b(i2, true);
    }

    public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new e(getContext(), attributeSet);
    }

    public DrawerLayout(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.e = new c();
        this.f573h = -1728053248;
        this.f575j = new Paint();
        this.q = true;
        this.r = 3;
        this.s = 3;
        this.t = 3;
        this.u = 3;
        this.H = null;
        this.I = null;
        this.J = null;
        this.K = null;
        setDescendantFocusability(262144);
        float f2 = getResources().getDisplayMetrics().density;
        this.f572g = (int) ((64.0f * f2) + 0.5f);
        float f3 = 400.0f * f2;
        this.m = new f(3);
        this.n = new f(5);
        androidx.customview.a.c a2 = androidx.customview.a.c.a((ViewGroup) this, 1.0f, (c.C0030c) this.m);
        this.k = a2;
        a2.d(1);
        this.k.a(f3);
        this.m.a(this.k);
        androidx.customview.a.c a3 = androidx.customview.a.c.a((ViewGroup) this, 1.0f, (c.C0030c) this.n);
        this.l = a3;
        a3.d(2);
        this.l.a(f3);
        this.n.a(this.l);
        setFocusableInTouchMode(true);
        v.h(this, 1);
        v.a((View) this, (androidx.core.h.a) new b());
        setMotionEventSplittingEnabled(false);
        if (v.l(this)) {
            if (Build.VERSION.SDK_INT >= 21) {
                setOnApplyWindowInsetsListener(new a(this));
                setSystemUiVisibility(1280);
                TypedArray obtainStyledAttributes = context.obtainStyledAttributes(O);
                try {
                    this.A = obtainStyledAttributes.getDrawable(0);
                } finally {
                    obtainStyledAttributes.recycle();
                }
            } else {
                this.A = null;
            }
        }
        this.f571f = f2 * 10.0f;
        this.L = new ArrayList<>();
    }

    private MotionEvent b(MotionEvent motionEvent, View view) {
        MotionEvent obtain = MotionEvent.obtain(motionEvent);
        obtain.offsetLocation((float) (getScrollX() - view.getLeft()), (float) (getScrollY() - view.getTop()));
        Matrix matrix = view.getMatrix();
        if (!matrix.isIdentity()) {
            if (this.N == null) {
                this.N = new Matrix();
            }
            matrix.invert(this.N);
            obtain.transform(this.N);
        }
        return obtain;
    }

    private boolean f() {
        return d() != null;
    }

    public boolean e(int i2) {
        View b2 = b(i2);
        if (b2 != null) {
            return h(b2);
        }
        return false;
    }

    public void setStatusBarBackground(int i2) {
        this.A = i2 != 0 ? androidx.core.content.a.c(getContext(), i2) : null;
        invalidate();
    }

    /* access modifiers changed from: package-private */
    public boolean i(View view) {
        int a2 = androidx.core.h.d.a(((e) view.getLayoutParams()).a, v.o(view));
        return ((a2 & 3) == 0 && (a2 & 5) == 0) ? false : true;
    }

    public static class e extends ViewGroup.MarginLayoutParams {
        public int a = 0;
        float b;
        boolean c;
        int d;

        public e(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, DrawerLayout.P);
            this.a = obtainStyledAttributes.getInt(0, 0);
            obtainStyledAttributes.recycle();
        }

        public e(int i2, int i3) {
            super(i2, i3);
        }

        public e(e eVar) {
            super(eVar);
            this.a = eVar.a;
        }

        public e(ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
        }

        public e(ViewGroup.MarginLayoutParams marginLayoutParams) {
            super(marginLayoutParams);
        }
    }

    private boolean e() {
        int childCount = getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            if (((e) getChildAt(i2).getLayoutParams()).c) {
                return true;
            }
        }
        return false;
    }

    public void a(d dVar) {
        if (dVar != null) {
            if (this.x == null) {
                this.x = new ArrayList();
            }
            this.x.add(dVar);
        }
    }

    public CharSequence d(int i2) {
        int a2 = androidx.core.h.d.a(i2, v.o(this));
        if (a2 == 3) {
            return this.D;
        }
        if (a2 == 5) {
            return this.E;
        }
        return null;
    }

    protected static class SavedState extends AbsSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new a();

        /* renamed from: g  reason: collision with root package name */
        int f576g = 0;

        /* renamed from: h  reason: collision with root package name */
        int f577h;

        /* renamed from: i  reason: collision with root package name */
        int f578i;

        /* renamed from: j  reason: collision with root package name */
        int f579j;
        int k;

        static class a implements Parcelable.ClassLoaderCreator<SavedState> {
            a() {
            }

            public SavedState[] newArray(int i2) {
                return new SavedState[i2];
            }

            public SavedState createFromParcel(Parcel parcel, ClassLoader classLoader) {
                return new SavedState(parcel, classLoader);
            }

            public SavedState createFromParcel(Parcel parcel) {
                return new SavedState(parcel, (ClassLoader) null);
            }
        }

        public SavedState(Parcel parcel, ClassLoader classLoader) {
            super(parcel, classLoader);
            this.f576g = parcel.readInt();
            this.f577h = parcel.readInt();
            this.f578i = parcel.readInt();
            this.f579j = parcel.readInt();
            this.k = parcel.readInt();
        }

        public void writeToParcel(Parcel parcel, int i2) {
            super.writeToParcel(parcel, i2);
            parcel.writeInt(this.f576g);
            parcel.writeInt(this.f577h);
            parcel.writeInt(this.f578i);
            parcel.writeInt(this.f579j);
            parcel.writeInt(this.k);
        }

        public SavedState(Parcelable parcelable) {
            super(parcelable);
        }
    }

    public void a(int i2, int i3) {
        View b2;
        int a2 = androidx.core.h.d.a(i3, v.o(this));
        if (i3 == 3) {
            this.r = i2;
        } else if (i3 == 5) {
            this.s = i2;
        } else if (i3 == 8388611) {
            this.t = i2;
        } else if (i3 == 8388613) {
            this.u = i2;
        }
        if (i2 != 0) {
            (a2 == 3 ? this.k : this.l).b();
        }
        if (i2 == 1) {
            View b3 = b(a2);
            if (b3 != null) {
                a(b3);
            }
        } else if (i2 == 2 && (b2 = b(a2)) != null) {
            k(b2);
        }
    }

    /* access modifiers changed from: package-private */
    public View d() {
        int childCount = getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            View childAt = getChildAt(i2);
            if (i(childAt) && j(childAt)) {
                return childAt;
            }
        }
        return null;
    }

    public boolean h(View view) {
        if (i(view)) {
            return (((e) view.getLayoutParams()).d & 1) == 1;
        }
        throw new IllegalArgumentException("View " + view + " is not a drawer");
    }

    /* access modifiers changed from: package-private */
    public void c(View view) {
        e eVar = (e) view.getLayoutParams();
        if ((eVar.d & 1) == 0) {
            eVar.d = 1;
            List<d> list = this.x;
            if (list != null) {
                for (int size = list.size() - 1; size >= 0; size--) {
                    this.x.get(size).a(view);
                }
            }
            c(view, true);
            if (hasWindowFocus()) {
                sendAccessibilityEvent(32);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public boolean g(View view) {
        return ((e) view.getLayoutParams()).a == 0;
    }

    /* access modifiers changed from: package-private */
    public void b(View view) {
        View rootView;
        e eVar = (e) view.getLayoutParams();
        if ((eVar.d & 1) == 1) {
            eVar.d = 0;
            List<d> list = this.x;
            if (list != null) {
                for (int size = list.size() - 1; size >= 0; size--) {
                    this.x.get(size).b(view);
                }
            }
            c(view, false);
            if (hasWindowFocus() && (rootView = getRootView()) != null) {
                rootView.sendAccessibilityEvent(32);
            }
        }
    }

    class b extends androidx.core.h.a {
        private final Rect d = new Rect();

        b() {
        }

        public void a(View view, androidx.core.h.e0.d dVar) {
            if (DrawerLayout.Q) {
                super.a(view, dVar);
            } else {
                androidx.core.h.e0.d a = androidx.core.h.e0.d.a(dVar);
                super.a(view, a);
                dVar.c(view);
                ViewParent u = v.u(view);
                if (u instanceof View) {
                    dVar.b((View) u);
                }
                a(dVar, a);
                a.w();
                a(dVar, (ViewGroup) view);
            }
            dVar.a((CharSequence) DrawerLayout.class.getName());
            dVar.i(false);
            dVar.j(false);
            dVar.b(d.a.e);
            dVar.b(d.a.f512f);
        }

        public void b(View view, AccessibilityEvent accessibilityEvent) {
            super.b(view, accessibilityEvent);
            accessibilityEvent.setClassName(DrawerLayout.class.getName());
        }

        public boolean a(View view, AccessibilityEvent accessibilityEvent) {
            CharSequence d2;
            if (accessibilityEvent.getEventType() != 32) {
                return super.a(view, accessibilityEvent);
            }
            List text = accessibilityEvent.getText();
            View d3 = DrawerLayout.this.d();
            if (d3 == null || (d2 = DrawerLayout.this.d(DrawerLayout.this.e(d3))) == null) {
                return true;
            }
            text.add(d2);
            return true;
        }

        public boolean a(ViewGroup viewGroup, View view, AccessibilityEvent accessibilityEvent) {
            if (DrawerLayout.Q || DrawerLayout.m(view)) {
                return super.a(viewGroup, view, accessibilityEvent);
            }
            return false;
        }

        private void a(androidx.core.h.e0.d dVar, ViewGroup viewGroup) {
            int childCount = viewGroup.getChildCount();
            for (int i2 = 0; i2 < childCount; i2++) {
                View childAt = viewGroup.getChildAt(i2);
                if (DrawerLayout.m(childAt)) {
                    dVar.a(childAt);
                }
            }
        }

        private void a(androidx.core.h.e0.d dVar, androidx.core.h.e0.d dVar2) {
            Rect rect = this.d;
            dVar2.a(rect);
            dVar.c(rect);
            dVar2.b(rect);
            dVar.d(rect);
            dVar.q(dVar2.v());
            dVar.e(dVar2.h());
            dVar.a(dVar2.c());
            dVar.b(dVar2.e());
            dVar.h(dVar2.o());
            dVar.e(dVar2.n());
            dVar.i(dVar2.p());
            dVar.j(dVar2.q());
            dVar.a(dVar2.k());
            dVar.o(dVar2.u());
            dVar.l(dVar2.r());
            dVar.a(dVar2.a());
        }
    }

    private void c(View view, boolean z2) {
        int childCount = getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            View childAt = getChildAt(i2);
            if ((z2 || i(childAt)) && (!z2 || childAt != view)) {
                v.h(childAt, 4);
            } else {
                v.h(childAt, 1);
            }
        }
    }

    private boolean a(float f2, float f3, View view) {
        if (this.M == null) {
            this.M = new Rect();
        }
        view.getHitRect(this.M);
        return this.M.contains((int) f2, (int) f3);
    }

    /* access modifiers changed from: package-private */
    public void b(View view, float f2) {
        float f3 = f(view);
        float width = (float) view.getWidth();
        int i2 = ((int) (width * f2)) - ((int) (f3 * width));
        if (!a(view, 3)) {
            i2 = -i2;
        }
        view.offsetLeftAndRight(i2);
        c(view, f2);
    }

    private boolean a(MotionEvent motionEvent, View view) {
        if (!view.getMatrix().isIdentity()) {
            MotionEvent b2 = b(motionEvent, view);
            boolean dispatchGenericMotionEvent = view.dispatchGenericMotionEvent(b2);
            b2.recycle();
            return dispatchGenericMotionEvent;
        }
        float scrollX = (float) (getScrollX() - view.getLeft());
        float scrollY = (float) (getScrollY() - view.getTop());
        motionEvent.offsetLocation(scrollX, scrollY);
        boolean dispatchGenericMotionEvent2 = view.dispatchGenericMotionEvent(motionEvent);
        motionEvent.offsetLocation(-scrollX, -scrollY);
        return dispatchGenericMotionEvent2;
    }

    /* access modifiers changed from: package-private */
    public void c(View view, float f2) {
        e eVar = (e) view.getLayoutParams();
        if (f2 != eVar.b) {
            eVar.b = f2;
            a(view, f2);
        }
    }

    /* access modifiers changed from: package-private */
    public View b(int i2) {
        int a2 = androidx.core.h.d.a(i2, v.o(this)) & 7;
        int childCount = getChildCount();
        for (int i3 = 0; i3 < childCount; i3++) {
            View childAt = getChildAt(i3);
            if ((e(childAt) & 7) == a2) {
                return childAt;
            }
        }
        return null;
    }

    /* access modifiers changed from: package-private */
    public View c() {
        int childCount = getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            View childAt = getChildAt(i2);
            if ((((e) childAt.getLayoutParams()).d & 1) == 1) {
                return childAt;
            }
        }
        return null;
    }

    public void b() {
        a(false);
    }

    /* access modifiers changed from: package-private */
    public void a(int i2, int i3, View view) {
        int f2 = this.k.f();
        int f3 = this.l.f();
        int i4 = 2;
        if (f2 == 1 || f3 == 1) {
            i4 = 1;
        } else if (!(f2 == 2 || f3 == 2)) {
            i4 = 0;
        }
        if (view != null && i3 == 0) {
            float f4 = ((e) view.getLayoutParams()).b;
            if (f4 == 0.0f) {
                b(view);
            } else if (f4 == 1.0f) {
                c(view);
            }
        }
        if (i4 != this.o) {
            this.o = i4;
            List<d> list = this.x;
            if (list != null) {
                for (int size = list.size() - 1; size >= 0; size--) {
                    this.x.get(size).a(i4);
                }
            }
        }
    }

    public void b(View view, boolean z2) {
        if (i(view)) {
            e eVar = (e) view.getLayoutParams();
            if (this.q) {
                eVar.b = 1.0f;
                eVar.d = 1;
                c(view, true);
            } else if (z2) {
                eVar.d |= 2;
                if (a(view, 3)) {
                    this.k.b(view, 0, view.getTop());
                } else {
                    this.l.b(view, getWidth() - view.getWidth(), view.getTop());
                }
            } else {
                b(view, 1.0f);
                a(eVar.a, 0, view);
                view.setVisibility(0);
            }
            invalidate();
            return;
        }
        throw new IllegalArgumentException("View " + view + " is not a sliding drawer");
    }

    /* access modifiers changed from: package-private */
    public void a(View view, float f2) {
        List<d> list = this.x;
        if (list != null) {
            for (int size = list.size() - 1; size >= 0; size--) {
                this.x.get(size).a(view, f2);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public boolean a(View view, int i2) {
        return (e(view) & i2) == i2;
    }

    private boolean a(Drawable drawable, int i2) {
        if (drawable == null || !androidx.core.graphics.drawable.a.f(drawable)) {
            return false;
        }
        androidx.core.graphics.drawable.a.a(drawable, i2);
        return true;
    }

    /* access modifiers changed from: package-private */
    public void a(boolean z2) {
        boolean z3;
        int childCount = getChildCount();
        boolean z4 = false;
        for (int i2 = 0; i2 < childCount; i2++) {
            View childAt = getChildAt(i2);
            e eVar = (e) childAt.getLayoutParams();
            if (i(childAt) && (!z2 || eVar.c)) {
                int width = childAt.getWidth();
                if (a(childAt, 3)) {
                    z3 = this.k.b(childAt, -width, childAt.getTop());
                } else {
                    z3 = this.l.b(childAt, getWidth(), childAt.getTop());
                }
                z4 |= z3;
                eVar.c = false;
            }
        }
        this.m.b();
        this.n.b();
        if (z4) {
            invalidate();
        }
    }

    public void b(int i2, boolean z2) {
        View b2 = b(i2);
        if (b2 != null) {
            b(b2, z2);
            return;
        }
        throw new IllegalArgumentException("No drawer view found with gravity " + g(i2));
    }

    public void a(View view) {
        a(view, true);
    }

    public void a(View view, boolean z2) {
        if (i(view)) {
            e eVar = (e) view.getLayoutParams();
            if (this.q) {
                eVar.b = 0.0f;
                eVar.d = 0;
            } else if (z2) {
                eVar.d |= 4;
                if (a(view, 3)) {
                    this.k.b(view, -view.getWidth(), view.getTop());
                } else {
                    this.l.b(view, getWidth(), view.getTop());
                }
            } else {
                b(view, 0.0f);
                a(eVar.a, 0, view);
                view.setVisibility(4);
            }
            invalidate();
            return;
        }
        throw new IllegalArgumentException("View " + view + " is not a sliding drawer");
    }

    public void a(int i2) {
        a(i2, true);
    }

    public void a(int i2, boolean z2) {
        View b2 = b(i2);
        if (b2 != null) {
            a(b2, z2);
            return;
        }
        throw new IllegalArgumentException("No drawer view found with gravity " + g(i2));
    }

    /* access modifiers changed from: package-private */
    public void a() {
        if (!this.v) {
            long uptimeMillis = SystemClock.uptimeMillis();
            MotionEvent obtain = MotionEvent.obtain(uptimeMillis, uptimeMillis, 3, 0.0f, 0.0f, 0);
            int childCount = getChildCount();
            for (int i2 = 0; i2 < childCount; i2++) {
                getChildAt(i2).dispatchTouchEvent(obtain);
            }
            obtain.recycle();
            this.v = true;
        }
    }
}
