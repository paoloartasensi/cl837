package androidx.coordinatorlayout.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.ViewTreeObserver;
import androidx.coordinatorlayout.R$attr;
import androidx.coordinatorlayout.R$style;
import androidx.coordinatorlayout.R$styleable;
import androidx.core.h.d0;
import androidx.core.h.n;
import androidx.core.h.q;
import androidx.core.h.r;
import androidx.core.h.v;
import androidx.customview.view.AbsSavedState;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CoordinatorLayout extends ViewGroup implements n {
    static final Comparator<View> A;
    private static final androidx.core.g.e<Rect> B = new androidx.core.g.g(12);
    static final String x;
    static final Class<?>[] y = {Context.class, AttributeSet.class};
    static final ThreadLocal<Map<String, Constructor<c>>> z = new ThreadLocal<>();
    private final List<View> e;

    /* renamed from: f  reason: collision with root package name */
    private final a<View> f421f;

    /* renamed from: g  reason: collision with root package name */
    private final List<View> f422g;

    /* renamed from: h  reason: collision with root package name */
    private final List<View> f423h;

    /* renamed from: i  reason: collision with root package name */
    private final int[] f424i;

    /* renamed from: j  reason: collision with root package name */
    private Paint f425j;
    private boolean k;
    private boolean l;
    private int[] m;
    private View n;
    private View o;
    private g p;
    private boolean q;
    private d0 r;
    private boolean s;
    private Drawable t;
    ViewGroup.OnHierarchyChangeListener u;
    private r v;
    private final q w;

    class a implements r {
        a() {
        }

        public d0 a(View view, d0 d0Var) {
            CoordinatorLayout.this.a(d0Var);
            return d0Var;
        }
    }

    public interface b {
        c getBehavior();
    }

    public static abstract class c<V extends View> {
        public c() {
        }

        public d0 a(CoordinatorLayout coordinatorLayout, V v, d0 d0Var) {
            return d0Var;
        }

        public void a() {
        }

        public void a(f fVar) {
        }

        public void a(CoordinatorLayout coordinatorLayout, V v, Parcelable parcelable) {
        }

        @Deprecated
        public void a(CoordinatorLayout coordinatorLayout, V v, View view, int i2, int i3, int i4, int i5) {
        }

        @Deprecated
        public void a(CoordinatorLayout coordinatorLayout, V v, View view, int i2, int i3, int[] iArr) {
        }

        @Deprecated
        public void a(CoordinatorLayout coordinatorLayout, V v, View view, View view2, int i2) {
        }

        public boolean a(CoordinatorLayout coordinatorLayout, V v) {
            return c(coordinatorLayout, v) > 0.0f;
        }

        public boolean a(CoordinatorLayout coordinatorLayout, V v, int i2) {
            return false;
        }

        public boolean a(CoordinatorLayout coordinatorLayout, V v, int i2, int i3, int i4, int i5) {
            return false;
        }

        public boolean a(CoordinatorLayout coordinatorLayout, V v, Rect rect) {
            return false;
        }

        public boolean a(CoordinatorLayout coordinatorLayout, V v, Rect rect, boolean z) {
            return false;
        }

        public boolean a(CoordinatorLayout coordinatorLayout, V v, MotionEvent motionEvent) {
            return false;
        }

        public boolean a(CoordinatorLayout coordinatorLayout, V v, View view) {
            return false;
        }

        public boolean a(CoordinatorLayout coordinatorLayout, V v, View view, float f2, float f3) {
            return false;
        }

        public boolean a(CoordinatorLayout coordinatorLayout, V v, View view, float f2, float f3, boolean z) {
            return false;
        }

        public int b(CoordinatorLayout coordinatorLayout, V v) {
            return -16777216;
        }

        public boolean b(CoordinatorLayout coordinatorLayout, V v, MotionEvent motionEvent) {
            return false;
        }

        public boolean b(CoordinatorLayout coordinatorLayout, V v, View view) {
            return false;
        }

        @Deprecated
        public boolean b(CoordinatorLayout coordinatorLayout, V v, View view, View view2, int i2) {
            return false;
        }

        public boolean b(CoordinatorLayout coordinatorLayout, V v, View view, View view2, int i2, int i3) {
            if (i3 == 0) {
                return b(coordinatorLayout, v, view, view2, i2);
            }
            return false;
        }

        public float c(CoordinatorLayout coordinatorLayout, V v) {
            return 0.0f;
        }

        public void c(CoordinatorLayout coordinatorLayout, V v, View view) {
        }

        public Parcelable d(CoordinatorLayout coordinatorLayout, V v) {
            return View.BaseSavedState.EMPTY_STATE;
        }

        @Deprecated
        public void d(CoordinatorLayout coordinatorLayout, V v, View view) {
        }

        public c(Context context, AttributeSet attributeSet) {
        }

        public void a(CoordinatorLayout coordinatorLayout, V v, View view, View view2, int i2, int i3) {
            if (i3 == 0) {
                a(coordinatorLayout, v, view, view2, i2);
            }
        }

        public void a(CoordinatorLayout coordinatorLayout, V v, View view, int i2) {
            if (i2 == 0) {
                d(coordinatorLayout, v, view);
            }
        }

        public void a(CoordinatorLayout coordinatorLayout, V v, View view, int i2, int i3, int i4, int i5, int i6) {
            if (i6 == 0) {
                a(coordinatorLayout, v, view, i2, i3, i4, i5);
            }
        }

        public void a(CoordinatorLayout coordinatorLayout, V v, View view, int i2, int i3, int[] iArr, int i4) {
            if (i4 == 0) {
                a(coordinatorLayout, v, view, i2, i3, iArr);
            }
        }
    }

    @Deprecated
    @Retention(RetentionPolicy.RUNTIME)
    public @interface d {
        Class<? extends c> value();
    }

    private class e implements ViewGroup.OnHierarchyChangeListener {
        e() {
        }

        public void onChildViewAdded(View view, View view2) {
            ViewGroup.OnHierarchyChangeListener onHierarchyChangeListener = CoordinatorLayout.this.u;
            if (onHierarchyChangeListener != null) {
                onHierarchyChangeListener.onChildViewAdded(view, view2);
            }
        }

        public void onChildViewRemoved(View view, View view2) {
            CoordinatorLayout.this.a(2);
            ViewGroup.OnHierarchyChangeListener onHierarchyChangeListener = CoordinatorLayout.this.u;
            if (onHierarchyChangeListener != null) {
                onHierarchyChangeListener.onChildViewRemoved(view, view2);
            }
        }
    }

    class g implements ViewTreeObserver.OnPreDrawListener {
        g() {
        }

        public boolean onPreDraw() {
            CoordinatorLayout.this.a(0);
            return true;
        }
    }

    static class h implements Comparator<View> {
        h() {
        }

        /* renamed from: a */
        public int compare(View view, View view2) {
            float x = v.x(view);
            float x2 = v.x(view2);
            if (x > x2) {
                return -1;
            }
            return x < x2 ? 1 : 0;
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v5, resolved type: java.lang.Class<?>[]} */
    /* JADX WARNING: Multi-variable type inference failed */
    static {
        /*
            java.lang.Class<androidx.coordinatorlayout.widget.CoordinatorLayout> r0 = androidx.coordinatorlayout.widget.CoordinatorLayout.class
            java.lang.Package r0 = r0.getPackage()
            r1 = 0
            if (r0 == 0) goto L_0x000e
            java.lang.String r0 = r0.getName()
            goto L_0x000f
        L_0x000e:
            r0 = r1
        L_0x000f:
            x = r0
            int r0 = android.os.Build.VERSION.SDK_INT
            r2 = 21
            if (r0 < r2) goto L_0x001f
            androidx.coordinatorlayout.widget.CoordinatorLayout$h r0 = new androidx.coordinatorlayout.widget.CoordinatorLayout$h
            r0.<init>()
            A = r0
            goto L_0x0021
        L_0x001f:
            A = r1
        L_0x0021:
            r0 = 2
            java.lang.Class[] r0 = new java.lang.Class[r0]
            r1 = 0
            java.lang.Class<android.content.Context> r2 = android.content.Context.class
            r0[r1] = r2
            r1 = 1
            java.lang.Class<android.util.AttributeSet> r2 = android.util.AttributeSet.class
            r0[r1] = r2
            y = r0
            java.lang.ThreadLocal r0 = new java.lang.ThreadLocal
            r0.<init>()
            z = r0
            androidx.core.g.g r0 = new androidx.core.g.g
            r1 = 12
            r0.<init>(r1)
            B = r0
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.coordinatorlayout.widget.CoordinatorLayout.<clinit>():void");
    }

    public CoordinatorLayout(Context context) {
        this(context, (AttributeSet) null);
    }

    private static int a(int i2, int i3, int i4) {
        return i2 < i3 ? i3 : i2 > i4 ? i4 : i2;
    }

    private static void a(Rect rect) {
        rect.setEmpty();
        B.a(rect);
    }

    private int b(int i2) {
        int[] iArr = this.m;
        if (iArr == null) {
            Log.e("CoordinatorLayout", "No keylines defined for " + this + " - attempted index lookup " + i2);
            return 0;
        } else if (i2 >= 0 && i2 < iArr.length) {
            return iArr[i2];
        } else {
            Log.e("CoordinatorLayout", "Keyline index " + i2 + " out of range for " + this);
            return 0;
        }
    }

    private static int c(int i2) {
        if (i2 == 0) {
            return 17;
        }
        return i2;
    }

    private static int d(int i2) {
        if ((i2 & 7) == 0) {
            i2 |= 8388611;
        }
        return (i2 & 112) == 0 ? i2 | 48 : i2;
    }

    private static int e(int i2) {
        if (i2 == 0) {
            return 8388661;
        }
        return i2;
    }

    private void e(View view, int i2) {
        f fVar = (f) view.getLayoutParams();
        int i3 = fVar.f430i;
        if (i3 != i2) {
            v.d(view, i2 - i3);
            fVar.f430i = i2;
        }
    }

    private void f(View view, int i2) {
        f fVar = (f) view.getLayoutParams();
        int i3 = fVar.f431j;
        if (i3 != i2) {
            v.e(view, i2 - i3);
            fVar.f431j = i2;
        }
    }

    private static Rect g() {
        Rect a2 = B.a();
        return a2 == null ? new Rect() : a2;
    }

    private void h() {
        this.e.clear();
        this.f421f.a();
        int childCount = getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            View childAt = getChildAt(i2);
            f d2 = d(childAt);
            d2.a(this, childAt);
            this.f421f.a(childAt);
            for (int i3 = 0; i3 < childCount; i3++) {
                if (i3 != i2) {
                    View childAt2 = getChildAt(i3);
                    if (d2.a(this, childAt, childAt2)) {
                        if (!this.f421f.b(childAt2)) {
                            this.f421f.a(childAt2);
                        }
                        this.f421f.a(childAt2, childAt);
                    }
                }
            }
        }
        this.e.addAll(this.f421f.b());
        Collections.reverse(this.e);
    }

    private void i() {
        if (Build.VERSION.SDK_INT >= 21) {
            if (v.l(this)) {
                if (this.v == null) {
                    this.v = new a();
                }
                v.a((View) this, this.v);
                setSystemUiVisibility(1280);
                return;
            }
            v.a((View) this, (r) null);
        }
    }

    public void c(View view, int i2) {
        f fVar = (f) view.getLayoutParams();
        if (!fVar.a()) {
            View view2 = fVar.k;
            if (view2 != null) {
                a(view, view2, i2);
                return;
            }
            int i3 = fVar.e;
            if (i3 >= 0) {
                b(view, i3, i2);
            } else {
                d(view, i2);
            }
        } else {
            throw new IllegalStateException("An anchor may not be changed after CoordinatorLayout measurement begins before layout is complete.");
        }
    }

    /* access modifiers changed from: protected */
    public boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return (layoutParams instanceof f) && super.checkLayoutParams(layoutParams);
    }

    /* access modifiers changed from: package-private */
    public f d(View view) {
        f fVar = (f) view.getLayoutParams();
        if (!fVar.b) {
            if (view instanceof b) {
                c behavior = ((b) view).getBehavior();
                if (behavior == null) {
                    Log.e("CoordinatorLayout", "Attached behavior class is null");
                }
                fVar.a(behavior);
                fVar.b = true;
            } else {
                d dVar = null;
                for (Class cls = view.getClass(); cls != null; cls = cls.getSuperclass()) {
                    dVar = (d) cls.getAnnotation(d.class);
                    if (dVar != null) {
                        break;
                    }
                }
                if (dVar != null) {
                    try {
                        fVar.a((c) dVar.value().getDeclaredConstructor(new Class[0]).newInstance(new Object[0]));
                    } catch (Exception e2) {
                        Log.e("CoordinatorLayout", "Default behavior class " + dVar.value().getName() + " could not be instantiated. Did you forget" + " a default constructor?", e2);
                    }
                }
                fVar.b = true;
            }
        }
        return fVar;
    }

    /* access modifiers changed from: protected */
    public boolean drawChild(Canvas canvas, View view, long j2) {
        f fVar = (f) view.getLayoutParams();
        c cVar = fVar.a;
        if (cVar != null) {
            float c2 = cVar.c(this, view);
            if (c2 > 0.0f) {
                if (this.f425j == null) {
                    this.f425j = new Paint();
                }
                this.f425j.setColor(fVar.a.b(this, view));
                this.f425j.setAlpha(a(Math.round(c2 * 255.0f), 0, 255));
                int save = canvas.save();
                if (view.isOpaque()) {
                    canvas.clipRect((float) view.getLeft(), (float) view.getTop(), (float) view.getRight(), (float) view.getBottom(), Region.Op.DIFFERENCE);
                }
                canvas.drawRect((float) getPaddingLeft(), (float) getPaddingTop(), (float) (getWidth() - getPaddingRight()), (float) (getHeight() - getPaddingBottom()), this.f425j);
                canvas.restoreToCount(save);
            }
        }
        return super.drawChild(canvas, view, j2);
    }

    /* access modifiers changed from: protected */
    public void drawableStateChanged() {
        super.drawableStateChanged();
        int[] drawableState = getDrawableState();
        Drawable drawable = this.t;
        boolean z2 = false;
        if (drawable != null && drawable.isStateful()) {
            z2 = false | drawable.setState(drawableState);
        }
        if (z2) {
            invalidate();
        }
    }

    /* access modifiers changed from: package-private */
    public final List<View> getDependencySortedChildren() {
        h();
        return Collections.unmodifiableList(this.e);
    }

    public final d0 getLastWindowInsets() {
        return this.r;
    }

    public int getNestedScrollAxes() {
        return this.w.a();
    }

    public Drawable getStatusBarBackground() {
        return this.t;
    }

    /* access modifiers changed from: protected */
    public int getSuggestedMinimumHeight() {
        return Math.max(super.getSuggestedMinimumHeight(), getPaddingTop() + getPaddingBottom());
    }

    /* access modifiers changed from: protected */
    public int getSuggestedMinimumWidth() {
        return Math.max(super.getSuggestedMinimumWidth(), getPaddingLeft() + getPaddingRight());
    }

    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        a(false);
        if (this.q) {
            if (this.p == null) {
                this.p = new g();
            }
            getViewTreeObserver().addOnPreDrawListener(this.p);
        }
        if (this.r == null && v.l(this)) {
            v.I(this);
        }
        this.l = true;
    }

    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        a(false);
        if (this.q && this.p != null) {
            getViewTreeObserver().removeOnPreDrawListener(this.p);
        }
        View view = this.o;
        if (view != null) {
            onStopNestedScroll(view);
        }
        this.l = false;
    }

    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.s && this.t != null) {
            d0 d0Var = this.r;
            int e2 = d0Var != null ? d0Var.e() : 0;
            if (e2 > 0) {
                this.t.setBounds(0, 0, getWidth(), e2);
                this.t.draw(canvas);
            }
        }
    }

    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 0) {
            a(true);
        }
        boolean a2 = a(motionEvent, 0);
        if (actionMasked == 1 || actionMasked == 3) {
            a(true);
        }
        return a2;
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z2, int i2, int i3, int i4, int i5) {
        c d2;
        int o2 = v.o(this);
        int size = this.e.size();
        for (int i6 = 0; i6 < size; i6++) {
            View view = this.e.get(i6);
            if (view.getVisibility() != 8 && ((d2 = ((f) view.getLayoutParams()).d()) == null || !d2.a(this, view, o2))) {
                c(view, o2);
            }
        }
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x011a, code lost:
        if (r0.a(r30, r20, r11, r21, r23, 0) == false) goto L_0x012a;
     */
    /* JADX WARNING: Removed duplicated region for block: B:36:0x00c7  */
    /* JADX WARNING: Removed duplicated region for block: B:37:0x00f1  */
    /* JADX WARNING: Removed duplicated region for block: B:40:0x00fb  */
    /* JADX WARNING: Removed duplicated region for block: B:42:0x011d  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onMeasure(int r31, int r32) {
        /*
            r30 = this;
            r7 = r30
            r30.h()
            r30.e()
            int r8 = r30.getPaddingLeft()
            int r0 = r30.getPaddingTop()
            int r9 = r30.getPaddingRight()
            int r1 = r30.getPaddingBottom()
            int r10 = androidx.core.h.v.o(r30)
            r2 = 1
            if (r10 != r2) goto L_0x0021
            r12 = 1
            goto L_0x0022
        L_0x0021:
            r12 = 0
        L_0x0022:
            int r13 = android.view.View.MeasureSpec.getMode(r31)
            int r14 = android.view.View.MeasureSpec.getSize(r31)
            int r15 = android.view.View.MeasureSpec.getMode(r32)
            int r16 = android.view.View.MeasureSpec.getSize(r32)
            int r17 = r8 + r9
            int r18 = r0 + r1
            int r0 = r30.getSuggestedMinimumWidth()
            int r1 = r30.getSuggestedMinimumHeight()
            androidx.core.h.d0 r3 = r7.r
            if (r3 == 0) goto L_0x004b
            boolean r3 = androidx.core.h.v.l(r30)
            if (r3 == 0) goto L_0x004b
            r19 = 1
            goto L_0x004d
        L_0x004b:
            r19 = 0
        L_0x004d:
            java.util.List<android.view.View> r2 = r7.e
            int r6 = r2.size()
            r5 = r0
            r4 = r1
            r2 = 0
            r3 = 0
        L_0x0057:
            if (r3 >= r6) goto L_0x016f
            java.util.List<android.view.View> r0 = r7.e
            java.lang.Object r0 = r0.get(r3)
            r20 = r0
            android.view.View r20 = (android.view.View) r20
            int r0 = r20.getVisibility()
            r1 = 8
            if (r0 != r1) goto L_0x0073
            r22 = r3
            r29 = r6
            r28 = r8
            goto L_0x0167
        L_0x0073:
            android.view.ViewGroup$LayoutParams r0 = r20.getLayoutParams()
            r1 = r0
            androidx.coordinatorlayout.widget.CoordinatorLayout$f r1 = (androidx.coordinatorlayout.widget.CoordinatorLayout.f) r1
            int r0 = r1.e
            if (r0 < 0) goto L_0x00ba
            if (r13 == 0) goto L_0x00ba
            int r0 = r7.b((int) r0)
            int r11 = r1.c
            int r11 = e((int) r11)
            int r11 = androidx.core.h.d.a(r11, r10)
            r11 = r11 & 7
            r22 = r2
            r2 = 3
            if (r11 != r2) goto L_0x0097
            if (r12 == 0) goto L_0x009c
        L_0x0097:
            r2 = 5
            if (r11 != r2) goto L_0x00a8
            if (r12 == 0) goto L_0x00a8
        L_0x009c:
            int r2 = r14 - r9
            int r2 = r2 - r0
            r0 = 0
            int r2 = java.lang.Math.max(r0, r2)
            r21 = r2
            r11 = 0
            goto L_0x00bf
        L_0x00a8:
            if (r11 != r2) goto L_0x00ac
            if (r12 == 0) goto L_0x00b1
        L_0x00ac:
            r2 = 3
            if (r11 != r2) goto L_0x00bc
            if (r12 == 0) goto L_0x00bc
        L_0x00b1:
            int r0 = r0 - r8
            r11 = 0
            int r0 = java.lang.Math.max(r11, r0)
            r21 = r0
            goto L_0x00bf
        L_0x00ba:
            r22 = r2
        L_0x00bc:
            r11 = 0
            r21 = 0
        L_0x00bf:
            if (r19 == 0) goto L_0x00f1
            boolean r0 = androidx.core.h.v.l(r20)
            if (r0 != 0) goto L_0x00f1
            androidx.core.h.d0 r0 = r7.r
            int r0 = r0.c()
            androidx.core.h.d0 r2 = r7.r
            int r2 = r2.d()
            int r0 = r0 + r2
            androidx.core.h.d0 r2 = r7.r
            int r2 = r2.e()
            androidx.core.h.d0 r11 = r7.r
            int r11 = r11.b()
            int r2 = r2 + r11
            int r0 = r14 - r0
            int r0 = android.view.View.MeasureSpec.makeMeasureSpec(r0, r13)
            int r2 = r16 - r2
            int r2 = android.view.View.MeasureSpec.makeMeasureSpec(r2, r15)
            r11 = r0
            r23 = r2
            goto L_0x00f5
        L_0x00f1:
            r11 = r31
            r23 = r32
        L_0x00f5:
            androidx.coordinatorlayout.widget.CoordinatorLayout$c r0 = r1.d()
            if (r0 == 0) goto L_0x011d
            r24 = 0
            r2 = r1
            r1 = r30
            r26 = r2
            r25 = r22
            r2 = r20
            r22 = r3
            r3 = r11
            r27 = r4
            r4 = r21
            r28 = r8
            r8 = r5
            r5 = r23
            r29 = r6
            r6 = r24
            boolean r0 = r0.a((androidx.coordinatorlayout.widget.CoordinatorLayout) r1, r2, (int) r3, (int) r4, (int) r5, (int) r6)
            if (r0 != 0) goto L_0x0137
            goto L_0x012a
        L_0x011d:
            r26 = r1
            r27 = r4
            r29 = r6
            r28 = r8
            r25 = r22
            r22 = r3
            r8 = r5
        L_0x012a:
            r5 = 0
            r0 = r30
            r1 = r20
            r2 = r11
            r3 = r21
            r4 = r23
            r0.a((android.view.View) r1, (int) r2, (int) r3, (int) r4, (int) r5)
        L_0x0137:
            int r0 = r20.getMeasuredWidth()
            int r0 = r17 + r0
            r1 = r26
            int r2 = r1.leftMargin
            int r0 = r0 + r2
            int r2 = r1.rightMargin
            int r0 = r0 + r2
            int r0 = java.lang.Math.max(r8, r0)
            int r2 = r20.getMeasuredHeight()
            int r2 = r18 + r2
            int r3 = r1.topMargin
            int r2 = r2 + r3
            int r1 = r1.bottomMargin
            int r2 = r2 + r1
            r1 = r27
            int r1 = java.lang.Math.max(r1, r2)
            int r2 = r20.getMeasuredState()
            r11 = r25
            int r2 = android.view.View.combineMeasuredStates(r11, r2)
            r5 = r0
            r4 = r1
        L_0x0167:
            int r3 = r22 + 1
            r8 = r28
            r6 = r29
            goto L_0x0057
        L_0x016f:
            r11 = r2
            r1 = r4
            r8 = r5
            r0 = -16777216(0xffffffffff000000, float:-1.7014118E38)
            r0 = r0 & r11
            r2 = r31
            int r0 = android.view.View.resolveSizeAndState(r8, r2, r0)
            int r2 = r11 << 16
            r3 = r32
            int r1 = android.view.View.resolveSizeAndState(r1, r3, r2)
            r7.setMeasuredDimension(r0, r1)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.coordinatorlayout.widget.CoordinatorLayout.onMeasure(int, int):void");
    }

    public boolean onNestedFling(View view, float f2, float f3, boolean z2) {
        c d2;
        int childCount = getChildCount();
        boolean z3 = false;
        for (int i2 = 0; i2 < childCount; i2++) {
            View childAt = getChildAt(i2);
            if (childAt.getVisibility() != 8) {
                f fVar = (f) childAt.getLayoutParams();
                if (fVar.a(0) && (d2 = fVar.d()) != null) {
                    z3 |= d2.a(this, childAt, view, f2, f3, z2);
                }
            }
        }
        if (z3) {
            a(1);
        }
        return z3;
    }

    public boolean onNestedPreFling(View view, float f2, float f3) {
        c d2;
        int childCount = getChildCount();
        boolean z2 = false;
        for (int i2 = 0; i2 < childCount; i2++) {
            View childAt = getChildAt(i2);
            if (childAt.getVisibility() != 8) {
                f fVar = (f) childAt.getLayoutParams();
                if (fVar.a(0) && (d2 = fVar.d()) != null) {
                    z2 |= d2.a(this, childAt, view, f2, f3);
                }
            }
        }
        return z2;
    }

    public void onNestedPreScroll(View view, int i2, int i3, int[] iArr) {
        a(view, i2, i3, iArr, 0);
    }

    public void onNestedScroll(View view, int i2, int i3, int i4, int i5) {
        a(view, i2, i3, i4, i5, 0);
    }

    public void onNestedScrollAccepted(View view, View view2, int i2) {
        a(view, view2, i2, 0);
    }

    /* access modifiers changed from: protected */
    public void onRestoreInstanceState(Parcelable parcelable) {
        Parcelable parcelable2;
        if (!(parcelable instanceof SavedState)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        SavedState savedState = (SavedState) parcelable;
        super.onRestoreInstanceState(savedState.a());
        SparseArray<Parcelable> sparseArray = savedState.f426g;
        int childCount = getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            View childAt = getChildAt(i2);
            int id = childAt.getId();
            c d2 = d(childAt).d();
            if (!(id == -1 || d2 == null || (parcelable2 = sparseArray.get(id)) == null)) {
                d2.a(this, childAt, parcelable2);
            }
        }
    }

    /* access modifiers changed from: protected */
    public Parcelable onSaveInstanceState() {
        Parcelable d2;
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        SparseArray<Parcelable> sparseArray = new SparseArray<>();
        int childCount = getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            View childAt = getChildAt(i2);
            int id = childAt.getId();
            c d3 = ((f) childAt.getLayoutParams()).d();
            if (!(id == -1 || d3 == null || (d2 = d3.d(this, childAt)) == null)) {
                sparseArray.append(id, d2);
            }
        }
        savedState.f426g = sparseArray;
        return savedState;
    }

    public boolean onStartNestedScroll(View view, View view2, int i2) {
        return b(view, view2, i2, 0);
    }

    public void onStopNestedScroll(View view) {
        a(view, 0);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:3:0x0012, code lost:
        if (r3 != false) goto L_0x0016;
     */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x0031  */
    /* JADX WARNING: Removed duplicated region for block: B:12:0x0037  */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x004c  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean onTouchEvent(android.view.MotionEvent r18) {
        /*
            r17 = this;
            r0 = r17
            r1 = r18
            int r2 = r18.getActionMasked()
            android.view.View r3 = r0.n
            r4 = 1
            r5 = 0
            if (r3 != 0) goto L_0x0015
            boolean r3 = r0.a((android.view.MotionEvent) r1, (int) r4)
            if (r3 == 0) goto L_0x002b
            goto L_0x0016
        L_0x0015:
            r3 = 0
        L_0x0016:
            android.view.View r6 = r0.n
            android.view.ViewGroup$LayoutParams r6 = r6.getLayoutParams()
            androidx.coordinatorlayout.widget.CoordinatorLayout$f r6 = (androidx.coordinatorlayout.widget.CoordinatorLayout.f) r6
            androidx.coordinatorlayout.widget.CoordinatorLayout$c r6 = r6.d()
            if (r6 == 0) goto L_0x002b
            android.view.View r7 = r0.n
            boolean r6 = r6.b((androidx.coordinatorlayout.widget.CoordinatorLayout) r0, r7, (android.view.MotionEvent) r1)
            goto L_0x002c
        L_0x002b:
            r6 = 0
        L_0x002c:
            android.view.View r7 = r0.n
            r8 = 0
            if (r7 != 0) goto L_0x0037
            boolean r1 = super.onTouchEvent(r18)
            r6 = r6 | r1
            goto L_0x004a
        L_0x0037:
            if (r3 == 0) goto L_0x004a
            long r11 = android.os.SystemClock.uptimeMillis()
            r13 = 3
            r14 = 0
            r15 = 0
            r16 = 0
            r9 = r11
            android.view.MotionEvent r8 = android.view.MotionEvent.obtain(r9, r11, r13, r14, r15, r16)
            super.onTouchEvent(r8)
        L_0x004a:
            if (r8 == 0) goto L_0x004f
            r8.recycle()
        L_0x004f:
            if (r2 == r4) goto L_0x0054
            r1 = 3
            if (r2 != r1) goto L_0x0057
        L_0x0054:
            r0.a((boolean) r5)
        L_0x0057:
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.coordinatorlayout.widget.CoordinatorLayout.onTouchEvent(android.view.MotionEvent):boolean");
    }

    public boolean requestChildRectangleOnScreen(View view, Rect rect, boolean z2) {
        c d2 = ((f) view.getLayoutParams()).d();
        if (d2 == null || !d2.a(this, view, rect, z2)) {
            return super.requestChildRectangleOnScreen(view, rect, z2);
        }
        return true;
    }

    public void requestDisallowInterceptTouchEvent(boolean z2) {
        super.requestDisallowInterceptTouchEvent(z2);
        if (z2 && !this.k) {
            a(false);
            this.k = true;
        }
    }

    public void setFitsSystemWindows(boolean z2) {
        super.setFitsSystemWindows(z2);
        i();
    }

    public void setOnHierarchyChangeListener(ViewGroup.OnHierarchyChangeListener onHierarchyChangeListener) {
        this.u = onHierarchyChangeListener;
    }

    public void setStatusBarBackground(Drawable drawable) {
        Drawable drawable2 = this.t;
        if (drawable2 != drawable) {
            Drawable drawable3 = null;
            if (drawable2 != null) {
                drawable2.setCallback((Drawable.Callback) null);
            }
            if (drawable != null) {
                drawable3 = drawable.mutate();
            }
            this.t = drawable3;
            if (drawable3 != null) {
                if (drawable3.isStateful()) {
                    this.t.setState(getDrawableState());
                }
                androidx.core.graphics.drawable.a.a(this.t, v.o(this));
                this.t.setVisible(getVisibility() == 0, false);
                this.t.setCallback(this);
            }
            v.H(this);
        }
    }

    public void setStatusBarBackgroundColor(int i2) {
        setStatusBarBackground(new ColorDrawable(i2));
    }

    public void setStatusBarBackgroundResource(int i2) {
        setStatusBarBackground(i2 != 0 ? androidx.core.content.a.c(getContext(), i2) : null);
    }

    public void setVisibility(int i2) {
        super.setVisibility(i2);
        boolean z2 = i2 == 0;
        Drawable drawable = this.t;
        if (drawable != null && drawable.isVisible() != z2) {
            this.t.setVisible(z2, false);
        }
    }

    /* access modifiers changed from: protected */
    public boolean verifyDrawable(Drawable drawable) {
        return super.verifyDrawable(drawable) || drawable == this.t;
    }

    public CoordinatorLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R$attr.coordinatorLayoutStyle);
    }

    /* access modifiers changed from: protected */
    public f generateDefaultLayoutParams() {
        return new f(-2, -2);
    }

    public static class f extends ViewGroup.MarginLayoutParams {
        c a;
        boolean b = false;
        public int c = 0;
        public int d = 0;
        public int e = -1;

        /* renamed from: f  reason: collision with root package name */
        int f427f = -1;

        /* renamed from: g  reason: collision with root package name */
        public int f428g = 0;

        /* renamed from: h  reason: collision with root package name */
        public int f429h = 0;

        /* renamed from: i  reason: collision with root package name */
        int f430i;

        /* renamed from: j  reason: collision with root package name */
        int f431j;
        View k;
        View l;
        private boolean m;
        private boolean n;
        private boolean o;
        private boolean p;
        final Rect q = new Rect();

        public f(int i2, int i3) {
            super(i2, i3);
        }

        public void a(c cVar) {
            c cVar2 = this.a;
            if (cVar2 != cVar) {
                if (cVar2 != null) {
                    cVar2.a();
                }
                this.a = cVar;
                this.b = true;
                if (cVar != null) {
                    cVar.a(this);
                }
            }
        }

        /* access modifiers changed from: package-private */
        public boolean b() {
            if (this.a == null) {
                this.m = false;
            }
            return this.m;
        }

        public int c() {
            return this.f427f;
        }

        public c d() {
            return this.a;
        }

        /* access modifiers changed from: package-private */
        public boolean e() {
            return this.p;
        }

        /* access modifiers changed from: package-private */
        public Rect f() {
            return this.q;
        }

        /* access modifiers changed from: package-private */
        public void g() {
            this.p = false;
        }

        /* access modifiers changed from: package-private */
        public void h() {
            this.m = false;
        }

        /* access modifiers changed from: package-private */
        public boolean b(CoordinatorLayout coordinatorLayout, View view) {
            boolean z = this.m;
            if (z) {
                return true;
            }
            c cVar = this.a;
            boolean a2 = (cVar != null ? cVar.a(coordinatorLayout, view) : false) | z;
            this.m = a2;
            return a2;
        }

        /* access modifiers changed from: package-private */
        public void a(Rect rect) {
            this.q.set(rect);
        }

        /* access modifiers changed from: package-private */
        public boolean a() {
            return this.k == null && this.f427f != -1;
        }

        /* access modifiers changed from: package-private */
        public void b(int i2) {
            a(i2, false);
        }

        private boolean b(View view, CoordinatorLayout coordinatorLayout) {
            if (this.k.getId() != this.f427f) {
                return false;
            }
            View view2 = this.k;
            for (ViewParent parent = view2.getParent(); parent != coordinatorLayout; parent = parent.getParent()) {
                if (parent == null || parent == view) {
                    this.l = null;
                    this.k = null;
                    return false;
                }
                if (parent instanceof View) {
                    view2 = (View) parent;
                }
            }
            this.l = view2;
            return true;
        }

        /* access modifiers changed from: package-private */
        public void a(int i2, boolean z) {
            if (i2 == 0) {
                this.n = z;
            } else if (i2 == 1) {
                this.o = z;
            }
        }

        f(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.CoordinatorLayout_Layout);
            this.c = obtainStyledAttributes.getInteger(R$styleable.CoordinatorLayout_Layout_android_layout_gravity, 0);
            this.f427f = obtainStyledAttributes.getResourceId(R$styleable.CoordinatorLayout_Layout_layout_anchor, -1);
            this.d = obtainStyledAttributes.getInteger(R$styleable.CoordinatorLayout_Layout_layout_anchorGravity, 0);
            this.e = obtainStyledAttributes.getInteger(R$styleable.CoordinatorLayout_Layout_layout_keyline, -1);
            this.f428g = obtainStyledAttributes.getInt(R$styleable.CoordinatorLayout_Layout_layout_insetEdge, 0);
            this.f429h = obtainStyledAttributes.getInt(R$styleable.CoordinatorLayout_Layout_layout_dodgeInsetEdges, 0);
            boolean hasValue = obtainStyledAttributes.hasValue(R$styleable.CoordinatorLayout_Layout_layout_behavior);
            this.b = hasValue;
            if (hasValue) {
                this.a = CoordinatorLayout.a(context, attributeSet, obtainStyledAttributes.getString(R$styleable.CoordinatorLayout_Layout_layout_behavior));
            }
            obtainStyledAttributes.recycle();
            c cVar = this.a;
            if (cVar != null) {
                cVar.a(this);
            }
        }

        /* access modifiers changed from: package-private */
        public boolean a(int i2) {
            if (i2 == 0) {
                return this.n;
            }
            if (i2 != 1) {
                return false;
            }
            return this.o;
        }

        /* access modifiers changed from: package-private */
        public void a(boolean z) {
            this.p = z;
        }

        /* access modifiers changed from: package-private */
        /* JADX WARNING: Code restructure failed: missing block: B:4:0x000e, code lost:
            r0 = r1.a;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean a(androidx.coordinatorlayout.widget.CoordinatorLayout r2, android.view.View r3, android.view.View r4) {
            /*
                r1 = this;
                android.view.View r0 = r1.l
                if (r4 == r0) goto L_0x001b
                int r0 = androidx.core.h.v.o(r2)
                boolean r0 = r1.a((android.view.View) r4, (int) r0)
                if (r0 != 0) goto L_0x001b
                androidx.coordinatorlayout.widget.CoordinatorLayout$c r0 = r1.a
                if (r0 == 0) goto L_0x0019
                boolean r2 = r0.a((androidx.coordinatorlayout.widget.CoordinatorLayout) r2, r3, (android.view.View) r4)
                if (r2 == 0) goto L_0x0019
                goto L_0x001b
            L_0x0019:
                r2 = 0
                goto L_0x001c
            L_0x001b:
                r2 = 1
            L_0x001c:
                return r2
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.coordinatorlayout.widget.CoordinatorLayout.f.a(androidx.coordinatorlayout.widget.CoordinatorLayout, android.view.View, android.view.View):boolean");
        }

        /* access modifiers changed from: package-private */
        public View a(CoordinatorLayout coordinatorLayout, View view) {
            if (this.f427f == -1) {
                this.l = null;
                this.k = null;
                return null;
            }
            if (this.k == null || !b(view, coordinatorLayout)) {
                a(view, coordinatorLayout);
            }
            return this.k;
        }

        private void a(View view, CoordinatorLayout coordinatorLayout) {
            View findViewById = coordinatorLayout.findViewById(this.f427f);
            this.k = findViewById;
            if (findViewById != null) {
                if (findViewById != coordinatorLayout) {
                    ViewParent parent = findViewById.getParent();
                    while (parent != coordinatorLayout && parent != null) {
                        if (parent != view) {
                            if (parent instanceof View) {
                                findViewById = (View) parent;
                            }
                            parent = parent.getParent();
                        } else if (coordinatorLayout.isInEditMode()) {
                            this.l = null;
                            this.k = null;
                            return;
                        } else {
                            throw new IllegalStateException("Anchor must not be a descendant of the anchored view");
                        }
                    }
                    this.l = findViewById;
                } else if (coordinatorLayout.isInEditMode()) {
                    this.l = null;
                    this.k = null;
                } else {
                    throw new IllegalStateException("View can not be anchored to the the parent CoordinatorLayout");
                }
            } else if (coordinatorLayout.isInEditMode()) {
                this.l = null;
                this.k = null;
            } else {
                throw new IllegalStateException("Could not find CoordinatorLayout descendant view with id " + coordinatorLayout.getResources().getResourceName(this.f427f) + " to anchor view " + view);
            }
        }

        public f(f fVar) {
            super(fVar);
        }

        private boolean a(View view, int i2) {
            int a2 = androidx.core.h.d.a(((f) view.getLayoutParams()).f428g, i2);
            return a2 != 0 && (androidx.core.h.d.a(this.f429h, i2) & a2) == a2;
        }

        public f(ViewGroup.MarginLayoutParams marginLayoutParams) {
            super(marginLayoutParams);
        }

        public f(ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
        }
    }

    public CoordinatorLayout(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        TypedArray typedArray;
        this.e = new ArrayList();
        this.f421f = new a<>();
        this.f422g = new ArrayList();
        this.f423h = new ArrayList();
        this.f424i = new int[2];
        this.w = new q(this);
        if (i2 == 0) {
            typedArray = context.obtainStyledAttributes(attributeSet, R$styleable.CoordinatorLayout, 0, R$style.Widget_Support_CoordinatorLayout);
        } else {
            typedArray = context.obtainStyledAttributes(attributeSet, R$styleable.CoordinatorLayout, i2, 0);
        }
        int resourceId = typedArray.getResourceId(R$styleable.CoordinatorLayout_keylines, 0);
        if (resourceId != 0) {
            Resources resources = context.getResources();
            this.m = resources.getIntArray(resourceId);
            float f2 = resources.getDisplayMetrics().density;
            int length = this.m.length;
            for (int i3 = 0; i3 < length; i3++) {
                int[] iArr = this.m;
                iArr[i3] = (int) (((float) iArr[i3]) * f2);
            }
        }
        this.t = typedArray.getDrawable(R$styleable.CoordinatorLayout_statusBarBackground);
        typedArray.recycle();
        i();
        super.setOnHierarchyChangeListener(new e());
    }

    /* access modifiers changed from: package-private */
    public final d0 a(d0 d0Var) {
        if (!androidx.core.g.c.a(this.r, d0Var)) {
            this.r = d0Var;
            boolean z2 = true;
            boolean z3 = d0Var != null && d0Var.e() > 0;
            this.s = z3;
            if (z3 || getBackground() != null) {
                z2 = false;
            }
            setWillNotDraw(z2);
            b(d0Var);
            requestLayout();
        }
        return d0Var;
    }

    public f generateLayoutParams(AttributeSet attributeSet) {
        return new f(getContext(), attributeSet);
    }

    /* access modifiers changed from: protected */
    public f generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        if (layoutParams instanceof f) {
            return new f((f) layoutParams);
        }
        if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
            return new f((ViewGroup.MarginLayoutParams) layoutParams);
        }
        return new f(layoutParams);
    }

    /* access modifiers changed from: package-private */
    public void e() {
        int childCount = getChildCount();
        boolean z2 = false;
        int i2 = 0;
        while (true) {
            if (i2 >= childCount) {
                break;
            } else if (e(getChildAt(i2))) {
                z2 = true;
                break;
            } else {
                i2++;
            }
        }
        if (z2 == this.q) {
            return;
        }
        if (z2) {
            d();
        } else {
            f();
        }
    }

    /* access modifiers changed from: package-private */
    public void f() {
        if (this.l && this.p != null) {
            getViewTreeObserver().removeOnPreDrawListener(this.p);
        }
        this.q = false;
    }

    private d0 b(d0 d0Var) {
        c d2;
        if (d0Var.g()) {
            return d0Var;
        }
        int childCount = getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            View childAt = getChildAt(i2);
            if (v.l(childAt) && (d2 = ((f) childAt.getLayoutParams()).d()) != null) {
                d2.a(this, childAt, d0Var);
                if (d0Var.g()) {
                    break;
                }
            }
        }
        return d0Var;
    }

    protected static class SavedState extends AbsSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new a();

        /* renamed from: g  reason: collision with root package name */
        SparseArray<Parcelable> f426g;

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
            int readInt = parcel.readInt();
            int[] iArr = new int[readInt];
            parcel.readIntArray(iArr);
            Parcelable[] readParcelableArray = parcel.readParcelableArray(classLoader);
            this.f426g = new SparseArray<>(readInt);
            for (int i2 = 0; i2 < readInt; i2++) {
                this.f426g.append(iArr[i2], readParcelableArray[i2]);
            }
        }

        public void writeToParcel(Parcel parcel, int i2) {
            super.writeToParcel(parcel, i2);
            SparseArray<Parcelable> sparseArray = this.f426g;
            int size = sparseArray != null ? sparseArray.size() : 0;
            parcel.writeInt(size);
            int[] iArr = new int[size];
            Parcelable[] parcelableArr = new Parcelable[size];
            for (int i3 = 0; i3 < size; i3++) {
                iArr[i3] = this.f426g.keyAt(i3);
                parcelableArr[i3] = this.f426g.valueAt(i3);
            }
            parcel.writeIntArray(iArr);
            parcel.writeParcelableArray(parcelableArr, i2);
        }

        public SavedState(Parcelable parcelable) {
            super(parcelable);
        }
    }

    private void a(boolean z2) {
        int childCount = getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            View childAt = getChildAt(i2);
            c d2 = ((f) childAt.getLayoutParams()).d();
            if (d2 != null) {
                long uptimeMillis = SystemClock.uptimeMillis();
                MotionEvent obtain = MotionEvent.obtain(uptimeMillis, uptimeMillis, 3, 0.0f, 0.0f, 0);
                if (z2) {
                    d2.a(this, childAt, obtain);
                } else {
                    d2.b(this, childAt, obtain);
                }
                obtain.recycle();
            }
        }
        for (int i3 = 0; i3 < childCount; i3++) {
            ((f) getChildAt(i3).getLayoutParams()).h();
        }
        this.n = null;
        this.k = false;
    }

    /* access modifiers changed from: package-private */
    public void c(View view, Rect rect) {
        ((f) view.getLayoutParams()).a(rect);
    }

    private boolean e(View view) {
        return this.f421f.e(view);
    }

    public List<View> c(View view) {
        List c2 = this.f421f.c(view);
        this.f423h.clear();
        if (c2 != null) {
            this.f423h.addAll(c2);
        }
        return this.f423h;
    }

    /* access modifiers changed from: package-private */
    public void b(View view, Rect rect) {
        rect.set(((f) view.getLayoutParams()).f());
    }

    private void b(View view, int i2, int i3) {
        f fVar = (f) view.getLayoutParams();
        int a2 = androidx.core.h.d.a(e(fVar.c), i3);
        int i4 = a2 & 7;
        int i5 = a2 & 112;
        int width = getWidth();
        int height = getHeight();
        int measuredWidth = view.getMeasuredWidth();
        int measuredHeight = view.getMeasuredHeight();
        if (i3 == 1) {
            i2 = width - i2;
        }
        int b2 = b(i2) - measuredWidth;
        int i6 = 0;
        if (i4 == 1) {
            b2 += measuredWidth / 2;
        } else if (i4 == 5) {
            b2 += measuredWidth;
        }
        if (i5 == 16) {
            i6 = 0 + (measuredHeight / 2);
        } else if (i5 == 80) {
            i6 = measuredHeight + 0;
        }
        int max = Math.max(getPaddingLeft() + fVar.leftMargin, Math.min(b2, ((width - getPaddingRight()) - measuredWidth) - fVar.rightMargin));
        int max2 = Math.max(getPaddingTop() + fVar.topMargin, Math.min(i6, ((height - getPaddingBottom()) - measuredHeight) - fVar.bottomMargin));
        view.layout(max, max2, measuredWidth + max, measuredHeight + max2);
    }

    private void d(View view, int i2) {
        f fVar = (f) view.getLayoutParams();
        Rect g2 = g();
        g2.set(getPaddingLeft() + fVar.leftMargin, getPaddingTop() + fVar.topMargin, (getWidth() - getPaddingRight()) - fVar.rightMargin, (getHeight() - getPaddingBottom()) - fVar.bottomMargin);
        if (this.r != null && v.l(this) && !v.l(view)) {
            g2.left += this.r.c();
            g2.top += this.r.e();
            g2.right -= this.r.d();
            g2.bottom -= this.r.b();
        }
        Rect g3 = g();
        androidx.core.h.d.a(d(fVar.c), view.getMeasuredWidth(), view.getMeasuredHeight(), g2, g3, i2);
        view.layout(g3.left, g3.top, g3.right, g3.bottom);
        a(g2);
        a(g3);
    }

    private void a(List<View> list) {
        list.clear();
        boolean isChildrenDrawingOrderEnabled = isChildrenDrawingOrderEnabled();
        int childCount = getChildCount();
        for (int i2 = childCount - 1; i2 >= 0; i2--) {
            list.add(getChildAt(isChildrenDrawingOrderEnabled ? getChildDrawingOrder(childCount, i2) : i2));
        }
        Comparator<View> comparator = A;
        if (comparator != null) {
            Collections.sort(list, comparator);
        }
    }

    private boolean a(MotionEvent motionEvent, int i2) {
        MotionEvent motionEvent2 = motionEvent;
        int i3 = i2;
        int actionMasked = motionEvent.getActionMasked();
        List<View> list = this.f422g;
        a(list);
        int size = list.size();
        MotionEvent motionEvent3 = null;
        boolean z2 = false;
        boolean z3 = false;
        for (int i4 = 0; i4 < size; i4++) {
            View view = list.get(i4);
            f fVar = (f) view.getLayoutParams();
            c d2 = fVar.d();
            if ((!z2 && !z3) || actionMasked == 0) {
                if (!z2 && d2 != null) {
                    if (i3 == 0) {
                        z2 = d2.a(this, view, motionEvent2);
                    } else if (i3 == 1) {
                        z2 = d2.b(this, view, motionEvent2);
                    }
                    if (z2) {
                        this.n = view;
                    }
                }
                boolean b2 = fVar.b();
                boolean b3 = fVar.b(this, view);
                z3 = b3 && !b2;
                if (b3 && !z3) {
                    break;
                }
            } else if (d2 != null) {
                if (motionEvent3 == null) {
                    long uptimeMillis = SystemClock.uptimeMillis();
                    motionEvent3 = MotionEvent.obtain(uptimeMillis, uptimeMillis, 3, 0.0f, 0.0f, 0);
                }
                if (i3 == 0) {
                    d2.a(this, view, motionEvent3);
                } else if (i3 == 1) {
                    d2.b(this, view, motionEvent3);
                }
            }
        }
        list.clear();
        return z2;
    }

    public List<View> b(View view) {
        List<View> d2 = this.f421f.d(view);
        this.f423h.clear();
        if (d2 != null) {
            this.f423h.addAll(d2);
        }
        return this.f423h;
    }

    /* access modifiers changed from: package-private */
    public void d() {
        if (this.l) {
            if (this.p == null) {
                this.p = new g();
            }
            getViewTreeObserver().addOnPreDrawListener(this.p);
        }
        this.q = true;
    }

    /* access modifiers changed from: package-private */
    public void b(View view, int i2) {
        c d2;
        View view2 = view;
        f fVar = (f) view.getLayoutParams();
        if (fVar.k != null) {
            Rect g2 = g();
            Rect g3 = g();
            Rect g4 = g();
            a(fVar.k, g2);
            boolean z2 = false;
            a(view2, false, g3);
            int measuredWidth = view.getMeasuredWidth();
            int measuredHeight = view.getMeasuredHeight();
            int i3 = measuredHeight;
            a(view, i2, g2, g4, fVar, measuredWidth, measuredHeight);
            if (!(g4.left == g3.left && g4.top == g3.top)) {
                z2 = true;
            }
            a(fVar, g4, measuredWidth, i3);
            int i4 = g4.left - g3.left;
            int i5 = g4.top - g3.top;
            if (i4 != 0) {
                v.d(view2, i4);
            }
            if (i5 != 0) {
                v.e(view2, i5);
            }
            if (z2 && (d2 = fVar.d()) != null) {
                d2.b(this, view2, fVar.k);
            }
            a(g2);
            a(g3);
            a(g4);
        }
    }

    static c a(Context context, AttributeSet attributeSet, String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        if (str.startsWith(".")) {
            str = context.getPackageName() + str;
        } else if (str.indexOf(46) < 0 && !TextUtils.isEmpty(x)) {
            str = x + '.' + str;
        }
        try {
            Map map = z.get();
            if (map == null) {
                map = new HashMap();
                z.set(map);
            }
            Constructor<?> constructor = (Constructor) map.get(str);
            if (constructor == null) {
                constructor = context.getClassLoader().loadClass(str).getConstructor(y);
                constructor.setAccessible(true);
                map.put(str, constructor);
            }
            return (c) constructor.newInstance(new Object[]{context, attributeSet});
        } catch (Exception e2) {
            throw new RuntimeException("Could not inflate Behavior subclass " + str, e2);
        }
    }

    public boolean b(View view, View view2, int i2, int i3) {
        int i4 = i3;
        int childCount = getChildCount();
        boolean z2 = false;
        for (int i5 = 0; i5 < childCount; i5++) {
            View childAt = getChildAt(i5);
            if (childAt.getVisibility() != 8) {
                f fVar = (f) childAt.getLayoutParams();
                c d2 = fVar.d();
                if (d2 != null) {
                    boolean b2 = d2.b(this, childAt, view, view2, i2, i3);
                    z2 |= b2;
                    fVar.a(i4, b2);
                } else {
                    fVar.a(i4, false);
                }
            }
        }
        return z2;
    }

    /* access modifiers changed from: package-private */
    public void a(View view, Rect rect) {
        b.a((ViewGroup) this, view, rect);
    }

    public void a(View view, int i2, int i3, int i4, int i5) {
        measureChildWithMargins(view, i2, i3, i4, i5);
    }

    /* access modifiers changed from: package-private */
    public void a(View view, boolean z2, Rect rect) {
        if (view.isLayoutRequested() || view.getVisibility() == 8) {
            rect.setEmpty();
        } else if (z2) {
            a(view, rect);
        } else {
            rect.set(view.getLeft(), view.getTop(), view.getRight(), view.getBottom());
        }
    }

    private void a(View view, int i2, Rect rect, Rect rect2, f fVar, int i3, int i4) {
        int i5;
        int i6;
        int a2 = androidx.core.h.d.a(c(fVar.c), i2);
        int a3 = androidx.core.h.d.a(d(fVar.d), i2);
        int i7 = a2 & 7;
        int i8 = a2 & 112;
        int i9 = a3 & 7;
        int i10 = a3 & 112;
        if (i9 == 1) {
            i5 = rect.left + (rect.width() / 2);
        } else if (i9 != 5) {
            i5 = rect.left;
        } else {
            i5 = rect.right;
        }
        if (i10 == 16) {
            i6 = rect.top + (rect.height() / 2);
        } else if (i10 != 80) {
            i6 = rect.top;
        } else {
            i6 = rect.bottom;
        }
        if (i7 == 1) {
            i5 -= i3 / 2;
        } else if (i7 != 5) {
            i5 -= i3;
        }
        if (i8 == 16) {
            i6 -= i4 / 2;
        } else if (i8 != 80) {
            i6 -= i4;
        }
        rect2.set(i5, i6, i3 + i5, i4 + i6);
    }

    private void a(f fVar, Rect rect, int i2, int i3) {
        int width = getWidth();
        int height = getHeight();
        int max = Math.max(getPaddingLeft() + fVar.leftMargin, Math.min(rect.left, ((width - getPaddingRight()) - i2) - fVar.rightMargin));
        int max2 = Math.max(getPaddingTop() + fVar.topMargin, Math.min(rect.top, ((height - getPaddingBottom()) - i3) - fVar.bottomMargin));
        rect.set(max, max2, i2 + max, i3 + max2);
    }

    /* access modifiers changed from: package-private */
    public void a(View view, int i2, Rect rect, Rect rect2) {
        f fVar = (f) view.getLayoutParams();
        int measuredWidth = view.getMeasuredWidth();
        int measuredHeight = view.getMeasuredHeight();
        a(view, i2, rect, rect2, fVar, measuredWidth, measuredHeight);
        a(fVar, rect2, measuredWidth, measuredHeight);
    }

    private void a(View view, View view2, int i2) {
        Rect g2 = g();
        Rect g3 = g();
        try {
            a(view2, g2);
            a(view, i2, g2, g3);
            view.layout(g3.left, g3.top, g3.right, g3.bottom);
        } finally {
            a(g2);
            a(g3);
        }
    }

    /* access modifiers changed from: package-private */
    public final void a(int i2) {
        boolean z2;
        int i3 = i2;
        int o2 = v.o(this);
        int size = this.e.size();
        Rect g2 = g();
        Rect g3 = g();
        Rect g4 = g();
        for (int i4 = 0; i4 < size; i4++) {
            View view = this.e.get(i4);
            f fVar = (f) view.getLayoutParams();
            if (i3 != 0 || view.getVisibility() != 8) {
                for (int i5 = 0; i5 < i4; i5++) {
                    if (fVar.l == this.e.get(i5)) {
                        b(view, o2);
                    }
                }
                a(view, true, g3);
                if (fVar.f428g != 0 && !g3.isEmpty()) {
                    int a2 = androidx.core.h.d.a(fVar.f428g, o2);
                    int i6 = a2 & 112;
                    if (i6 == 48) {
                        g2.top = Math.max(g2.top, g3.bottom);
                    } else if (i6 == 80) {
                        g2.bottom = Math.max(g2.bottom, getHeight() - g3.top);
                    }
                    int i7 = a2 & 7;
                    if (i7 == 3) {
                        g2.left = Math.max(g2.left, g3.right);
                    } else if (i7 == 5) {
                        g2.right = Math.max(g2.right, getWidth() - g3.left);
                    }
                }
                if (fVar.f429h != 0 && view.getVisibility() == 0) {
                    a(view, g2, o2);
                }
                if (i3 != 2) {
                    b(view, g4);
                    if (!g4.equals(g3)) {
                        c(view, g3);
                    }
                }
                for (int i8 = i4 + 1; i8 < size; i8++) {
                    View view2 = this.e.get(i8);
                    f fVar2 = (f) view2.getLayoutParams();
                    c d2 = fVar2.d();
                    if (d2 != null && d2.a(this, view2, view)) {
                        if (i3 != 0 || !fVar2.e()) {
                            if (i3 != 2) {
                                z2 = d2.b(this, view2, view);
                            } else {
                                d2.c(this, view2, view);
                                z2 = true;
                            }
                            if (i3 == 1) {
                                fVar2.a(z2);
                            }
                        } else {
                            fVar2.g();
                        }
                    }
                }
            }
        }
        a(g2);
        a(g3);
        a(g4);
    }

    private void a(View view, Rect rect, int i2) {
        boolean z2;
        boolean z3;
        int width;
        int i3;
        int i4;
        int i5;
        int height;
        int i6;
        int i7;
        int i8;
        if (v.D(view) && view.getWidth() > 0 && view.getHeight() > 0) {
            f fVar = (f) view.getLayoutParams();
            c d2 = fVar.d();
            Rect g2 = g();
            Rect g3 = g();
            g3.set(view.getLeft(), view.getTop(), view.getRight(), view.getBottom());
            if (d2 == null || !d2.a(this, view, g2)) {
                g2.set(g3);
            } else if (!g3.contains(g2)) {
                throw new IllegalArgumentException("Rect should be within the child's bounds. Rect:" + g2.toShortString() + " | Bounds:" + g3.toShortString());
            }
            a(g3);
            if (g2.isEmpty()) {
                a(g2);
                return;
            }
            int a2 = androidx.core.h.d.a(fVar.f429h, i2);
            boolean z4 = true;
            if ((a2 & 48) != 48 || (i7 = (g2.top - fVar.topMargin) - fVar.f431j) >= (i8 = rect.top)) {
                z2 = false;
            } else {
                f(view, i8 - i7);
                z2 = true;
            }
            if ((a2 & 80) == 80 && (height = ((getHeight() - g2.bottom) - fVar.bottomMargin) + fVar.f431j) < (i6 = rect.bottom)) {
                f(view, height - i6);
                z2 = true;
            }
            if (!z2) {
                f(view, 0);
            }
            if ((a2 & 3) != 3 || (i4 = (g2.left - fVar.leftMargin) - fVar.f430i) >= (i5 = rect.left)) {
                z3 = false;
            } else {
                e(view, i5 - i4);
                z3 = true;
            }
            if ((a2 & 5) != 5 || (width = ((getWidth() - g2.right) - fVar.rightMargin) + fVar.f430i) >= (i3 = rect.right)) {
                z4 = z3;
            } else {
                e(view, width - i3);
            }
            if (!z4) {
                e(view, 0);
            }
            a(g2);
        }
    }

    public void a(View view) {
        List c2 = this.f421f.c(view);
        if (c2 != null && !c2.isEmpty()) {
            for (int i2 = 0; i2 < c2.size(); i2++) {
                View view2 = (View) c2.get(i2);
                c d2 = ((f) view2.getLayoutParams()).d();
                if (d2 != null) {
                    d2.b(this, view2, view);
                }
            }
        }
    }

    public boolean a(View view, int i2, int i3) {
        Rect g2 = g();
        a(view, g2);
        try {
            return g2.contains(i2, i3);
        } finally {
            a(g2);
        }
    }

    public void a(View view, View view2, int i2, int i3) {
        c d2;
        this.w.a(view, view2, i2, i3);
        this.o = view2;
        int childCount = getChildCount();
        for (int i4 = 0; i4 < childCount; i4++) {
            View childAt = getChildAt(i4);
            f fVar = (f) childAt.getLayoutParams();
            if (fVar.a(i3) && (d2 = fVar.d()) != null) {
                d2.a(this, childAt, view, view2, i2, i3);
            }
        }
    }

    public void a(View view, int i2) {
        this.w.a(view, i2);
        int childCount = getChildCount();
        for (int i3 = 0; i3 < childCount; i3++) {
            View childAt = getChildAt(i3);
            f fVar = (f) childAt.getLayoutParams();
            if (fVar.a(i2)) {
                c d2 = fVar.d();
                if (d2 != null) {
                    d2.a(this, childAt, view, i2);
                }
                fVar.b(i2);
                fVar.g();
            }
        }
        this.o = null;
    }

    public void a(View view, int i2, int i3, int i4, int i5, int i6) {
        c d2;
        int childCount = getChildCount();
        boolean z2 = false;
        for (int i7 = 0; i7 < childCount; i7++) {
            View childAt = getChildAt(i7);
            if (childAt.getVisibility() == 8) {
                int i8 = i6;
            } else {
                f fVar = (f) childAt.getLayoutParams();
                if (fVar.a(i6) && (d2 = fVar.d()) != null) {
                    d2.a(this, childAt, view, i2, i3, i4, i5, i6);
                    z2 = true;
                }
            }
        }
        if (z2) {
            a(1);
        }
    }

    public void a(View view, int i2, int i3, int[] iArr, int i4) {
        c d2;
        int i5;
        int i6;
        int childCount = getChildCount();
        boolean z2 = false;
        int i7 = 0;
        int i8 = 0;
        for (int i9 = 0; i9 < childCount; i9++) {
            View childAt = getChildAt(i9);
            if (childAt.getVisibility() == 8) {
                int i10 = i4;
            } else {
                f fVar = (f) childAt.getLayoutParams();
                if (fVar.a(i4) && (d2 = fVar.d()) != null) {
                    int[] iArr2 = this.f424i;
                    iArr2[1] = 0;
                    iArr2[0] = 0;
                    d2.a(this, childAt, view, i2, i3, iArr2, i4);
                    int[] iArr3 = this.f424i;
                    if (i2 > 0) {
                        i5 = Math.max(i7, iArr3[0]);
                    } else {
                        i5 = Math.min(i7, iArr3[0]);
                    }
                    i7 = i5;
                    int[] iArr4 = this.f424i;
                    if (i3 > 0) {
                        i6 = Math.max(i8, iArr4[1]);
                    } else {
                        i6 = Math.min(i8, iArr4[1]);
                    }
                    i8 = i6;
                    z2 = true;
                }
            }
        }
        iArr[0] = i7;
        iArr[1] = i8;
        if (z2) {
            a(1);
        }
    }
}
