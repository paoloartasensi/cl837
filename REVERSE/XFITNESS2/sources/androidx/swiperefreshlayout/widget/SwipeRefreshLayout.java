package androidx.swiperefreshlayout.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Transformation;
import android.widget.AbsListView;
import android.widget.ListView;
import androidx.core.h.l;
import androidx.core.h.m;
import androidx.core.h.p;
import androidx.core.h.q;
import androidx.core.h.v;

public class SwipeRefreshLayout extends ViewGroup implements p, l {
    private static final String S = SwipeRefreshLayout.class.getSimpleName();
    private static final int[] T = {16842766};
    protected int A;
    float B;
    protected int C;
    int D;
    int E;
    b F;
    private Animation G;
    private Animation H;
    private Animation I;
    private Animation J;
    private Animation K;
    boolean L;
    private int M;
    boolean N;
    private i O;
    private Animation.AnimationListener P;
    private final Animation Q;
    private final Animation R;
    private View e;

    /* renamed from: f  reason: collision with root package name */
    j f863f;

    /* renamed from: g  reason: collision with root package name */
    boolean f864g;

    /* renamed from: h  reason: collision with root package name */
    private int f865h;

    /* renamed from: i  reason: collision with root package name */
    private float f866i;

    /* renamed from: j  reason: collision with root package name */
    private float f867j;
    private final q k;
    private final m l;
    private final int[] m;
    private final int[] n;
    private boolean o;
    private int p;
    int q;
    private float r;
    private float s;
    private boolean t;
    private int u;
    boolean v;
    private boolean w;
    private final DecelerateInterpolator x;
    a y;
    private int z;

    class a implements Animation.AnimationListener {
        a() {
        }

        public void onAnimationEnd(Animation animation) {
            j jVar;
            SwipeRefreshLayout swipeRefreshLayout = SwipeRefreshLayout.this;
            if (swipeRefreshLayout.f864g) {
                swipeRefreshLayout.F.setAlpha(255);
                SwipeRefreshLayout.this.F.start();
                SwipeRefreshLayout swipeRefreshLayout2 = SwipeRefreshLayout.this;
                if (swipeRefreshLayout2.L && (jVar = swipeRefreshLayout2.f863f) != null) {
                    jVar.a();
                }
                SwipeRefreshLayout swipeRefreshLayout3 = SwipeRefreshLayout.this;
                swipeRefreshLayout3.q = swipeRefreshLayout3.y.getTop();
                return;
            }
            swipeRefreshLayout.b();
        }

        public void onAnimationRepeat(Animation animation) {
        }

        public void onAnimationStart(Animation animation) {
        }
    }

    class b extends Animation {
        b() {
        }

        public void applyTransformation(float f2, Transformation transformation) {
            SwipeRefreshLayout.this.setAnimationProgress(f2);
        }
    }

    class c extends Animation {
        c() {
        }

        public void applyTransformation(float f2, Transformation transformation) {
            SwipeRefreshLayout.this.setAnimationProgress(1.0f - f2);
        }
    }

    class d extends Animation {
        final /* synthetic */ int e;

        /* renamed from: f  reason: collision with root package name */
        final /* synthetic */ int f868f;

        d(int i2, int i3) {
            this.e = i2;
            this.f868f = i3;
        }

        public void applyTransformation(float f2, Transformation transformation) {
            b bVar = SwipeRefreshLayout.this.F;
            int i2 = this.e;
            bVar.setAlpha((int) (((float) i2) + (((float) (this.f868f - i2)) * f2)));
        }
    }

    class e implements Animation.AnimationListener {
        e() {
        }

        public void onAnimationEnd(Animation animation) {
            SwipeRefreshLayout swipeRefreshLayout = SwipeRefreshLayout.this;
            if (!swipeRefreshLayout.v) {
                swipeRefreshLayout.a((Animation.AnimationListener) null);
            }
        }

        public void onAnimationRepeat(Animation animation) {
        }

        public void onAnimationStart(Animation animation) {
        }
    }

    class f extends Animation {
        f() {
        }

        public void applyTransformation(float f2, Transformation transformation) {
            int i2;
            SwipeRefreshLayout swipeRefreshLayout = SwipeRefreshLayout.this;
            if (!swipeRefreshLayout.N) {
                i2 = swipeRefreshLayout.D - Math.abs(swipeRefreshLayout.C);
            } else {
                i2 = swipeRefreshLayout.D;
            }
            SwipeRefreshLayout swipeRefreshLayout2 = SwipeRefreshLayout.this;
            int i3 = swipeRefreshLayout2.A;
            SwipeRefreshLayout.this.setTargetOffsetTopAndBottom((i3 + ((int) (((float) (i2 - i3)) * f2))) - swipeRefreshLayout2.y.getTop());
            SwipeRefreshLayout.this.F.a(1.0f - f2);
        }
    }

    class g extends Animation {
        g() {
        }

        public void applyTransformation(float f2, Transformation transformation) {
            SwipeRefreshLayout.this.a(f2);
        }
    }

    class h extends Animation {
        h() {
        }

        public void applyTransformation(float f2, Transformation transformation) {
            SwipeRefreshLayout swipeRefreshLayout = SwipeRefreshLayout.this;
            float f3 = swipeRefreshLayout.B;
            swipeRefreshLayout.setAnimationProgress(f3 + ((-f3) * f2));
            SwipeRefreshLayout.this.a(f2);
        }
    }

    public interface i {
        boolean a(SwipeRefreshLayout swipeRefreshLayout, View view);
    }

    public interface j {
        void a();
    }

    public SwipeRefreshLayout(Context context) {
        this(context, (AttributeSet) null);
    }

    private void a(boolean z2, boolean z3) {
        if (this.f864g != z2) {
            this.L = z3;
            d();
            this.f864g = z2;
            if (z2) {
                a(this.q, this.P);
            } else {
                a(this.P);
            }
        }
    }

    private void c() {
        this.y = new a(getContext(), -328966);
        b bVar = new b(getContext());
        this.F = bVar;
        bVar.a(1);
        this.y.setImageDrawable(this.F);
        this.y.setVisibility(8);
        addView(this.y);
    }

    private void d() {
        if (this.e == null) {
            for (int i2 = 0; i2 < getChildCount(); i2++) {
                View childAt = getChildAt(i2);
                if (!childAt.equals(this.y)) {
                    this.e = childAt;
                    return;
                }
            }
        }
    }

    private void e() {
        this.J = a(this.F.getAlpha(), 255);
    }

    private void f() {
        this.I = a(this.F.getAlpha(), 76);
    }

    private void setColorViewAlpha(int i2) {
        this.y.getBackground().setAlpha(i2);
        this.F.setAlpha(i2);
    }

    /* access modifiers changed from: package-private */
    public void b() {
        this.y.clearAnimation();
        this.F.stop();
        this.y.setVisibility(8);
        setColorViewAlpha(255);
        if (this.v) {
            setAnimationProgress(0.0f);
        } else {
            setTargetOffsetTopAndBottom(this.C - this.q);
        }
        this.q = this.y.getTop();
    }

    public boolean dispatchNestedFling(float f2, float f3, boolean z2) {
        return this.l.a(f2, f3, z2);
    }

    public boolean dispatchNestedPreFling(float f2, float f3) {
        return this.l.a(f2, f3);
    }

    public boolean dispatchNestedPreScroll(int i2, int i3, int[] iArr, int[] iArr2) {
        return this.l.a(i2, i3, iArr, iArr2);
    }

    public boolean dispatchNestedScroll(int i2, int i3, int i4, int i5, int[] iArr) {
        return this.l.a(i2, i3, i4, i5, iArr);
    }

    /* access modifiers changed from: protected */
    public int getChildDrawingOrder(int i2, int i3) {
        int i4 = this.z;
        if (i4 < 0) {
            return i3;
        }
        if (i3 == i2 - 1) {
            return i4;
        }
        return i3 >= i4 ? i3 + 1 : i3;
    }

    public int getNestedScrollAxes() {
        return this.k.a();
    }

    public int getProgressCircleDiameter() {
        return this.M;
    }

    public int getProgressViewEndOffset() {
        return this.D;
    }

    public int getProgressViewStartOffset() {
        return this.C;
    }

    public boolean hasNestedScrollingParent() {
        return this.l.a();
    }

    public boolean isNestedScrollingEnabled() {
        return this.l.b();
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        b();
    }

    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        d();
        int actionMasked = motionEvent.getActionMasked();
        if (this.w && actionMasked == 0) {
            this.w = false;
        }
        if (!isEnabled() || this.w || a() || this.f864g || this.o) {
            return false;
        }
        if (actionMasked != 0) {
            if (actionMasked != 1) {
                if (actionMasked == 2) {
                    int i2 = this.u;
                    if (i2 == -1) {
                        Log.e(S, "Got ACTION_MOVE event but don't have an active pointer id.");
                        return false;
                    }
                    int findPointerIndex = motionEvent.findPointerIndex(i2);
                    if (findPointerIndex < 0) {
                        return false;
                    }
                    d(motionEvent.getY(findPointerIndex));
                } else if (actionMasked != 3) {
                    if (actionMasked == 6) {
                        a(motionEvent);
                    }
                }
            }
            this.t = false;
            this.u = -1;
        } else {
            setTargetOffsetTopAndBottom(this.C - this.y.getTop());
            int pointerId = motionEvent.getPointerId(0);
            this.u = pointerId;
            this.t = false;
            int findPointerIndex2 = motionEvent.findPointerIndex(pointerId);
            if (findPointerIndex2 < 0) {
                return false;
            }
            this.s = motionEvent.getY(findPointerIndex2);
        }
        return this.t;
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z2, int i2, int i3, int i4, int i5) {
        int measuredWidth = getMeasuredWidth();
        int measuredHeight = getMeasuredHeight();
        if (getChildCount() != 0) {
            if (this.e == null) {
                d();
            }
            View view = this.e;
            if (view != null) {
                int paddingLeft = getPaddingLeft();
                int paddingTop = getPaddingTop();
                view.layout(paddingLeft, paddingTop, ((measuredWidth - getPaddingLeft()) - getPaddingRight()) + paddingLeft, ((measuredHeight - getPaddingTop()) - getPaddingBottom()) + paddingTop);
                int measuredWidth2 = this.y.getMeasuredWidth();
                int measuredHeight2 = this.y.getMeasuredHeight();
                int i6 = measuredWidth / 2;
                int i7 = measuredWidth2 / 2;
                int i8 = this.q;
                this.y.layout(i6 - i7, i8, i6 + i7, measuredHeight2 + i8);
            }
        }
    }

    public void onMeasure(int i2, int i3) {
        super.onMeasure(i2, i3);
        if (this.e == null) {
            d();
        }
        View view = this.e;
        if (view != null) {
            view.measure(View.MeasureSpec.makeMeasureSpec((getMeasuredWidth() - getPaddingLeft()) - getPaddingRight(), 1073741824), View.MeasureSpec.makeMeasureSpec((getMeasuredHeight() - getPaddingTop()) - getPaddingBottom(), 1073741824));
            this.y.measure(View.MeasureSpec.makeMeasureSpec(this.M, 1073741824), View.MeasureSpec.makeMeasureSpec(this.M, 1073741824));
            this.z = -1;
            for (int i4 = 0; i4 < getChildCount(); i4++) {
                if (getChildAt(i4) == this.y) {
                    this.z = i4;
                    return;
                }
            }
        }
    }

    public boolean onNestedFling(View view, float f2, float f3, boolean z2) {
        return dispatchNestedFling(f2, f3, z2);
    }

    public boolean onNestedPreFling(View view, float f2, float f3) {
        return dispatchNestedPreFling(f2, f3);
    }

    public void onNestedPreScroll(View view, int i2, int i3, int[] iArr) {
        if (i3 > 0) {
            float f2 = this.f867j;
            if (f2 > 0.0f) {
                float f3 = (float) i3;
                if (f3 > f2) {
                    iArr[1] = i3 - ((int) f2);
                    this.f867j = 0.0f;
                } else {
                    this.f867j = f2 - f3;
                    iArr[1] = i3;
                }
                c(this.f867j);
            }
        }
        if (this.N && i3 > 0 && this.f867j == 0.0f && Math.abs(i3 - iArr[1]) > 0) {
            this.y.setVisibility(8);
        }
        int[] iArr2 = this.m;
        if (dispatchNestedPreScroll(i2 - iArr[0], i3 - iArr[1], iArr2, (int[]) null)) {
            iArr[0] = iArr[0] + iArr2[0];
            iArr[1] = iArr[1] + iArr2[1];
        }
    }

    public void onNestedScroll(View view, int i2, int i3, int i4, int i5) {
        dispatchNestedScroll(i2, i3, i4, i5, this.n);
        int i6 = i5 + this.n[1];
        if (i6 < 0 && !a()) {
            float abs = this.f867j + ((float) Math.abs(i6));
            this.f867j = abs;
            c(abs);
        }
    }

    public void onNestedScrollAccepted(View view, View view2, int i2) {
        this.k.a(view, view2, i2);
        startNestedScroll(i2 & 2);
        this.f867j = 0.0f;
        this.o = true;
    }

    public boolean onStartNestedScroll(View view, View view2, int i2) {
        return isEnabled() && !this.w && !this.f864g && (i2 & 2) != 0;
    }

    public void onStopNestedScroll(View view) {
        this.k.a(view);
        this.o = false;
        float f2 = this.f867j;
        if (f2 > 0.0f) {
            b(f2);
            this.f867j = 0.0f;
        }
        stopNestedScroll();
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        int actionMasked = motionEvent.getActionMasked();
        if (this.w && actionMasked == 0) {
            this.w = false;
        }
        if (!isEnabled() || this.w || a() || this.f864g || this.o) {
            return false;
        }
        if (actionMasked == 0) {
            this.u = motionEvent.getPointerId(0);
            this.t = false;
        } else if (actionMasked == 1) {
            int findPointerIndex = motionEvent.findPointerIndex(this.u);
            if (findPointerIndex < 0) {
                Log.e(S, "Got ACTION_UP event but don't have an active pointer id.");
                return false;
            }
            if (this.t) {
                this.t = false;
                b((motionEvent.getY(findPointerIndex) - this.r) * 0.5f);
            }
            this.u = -1;
            return false;
        } else if (actionMasked == 2) {
            int findPointerIndex2 = motionEvent.findPointerIndex(this.u);
            if (findPointerIndex2 < 0) {
                Log.e(S, "Got ACTION_MOVE event but have an invalid active pointer id.");
                return false;
            }
            float y2 = motionEvent.getY(findPointerIndex2);
            d(y2);
            if (this.t) {
                float f2 = (y2 - this.r) * 0.5f;
                if (f2 <= 0.0f) {
                    return false;
                }
                c(f2);
            }
        } else if (actionMasked == 3) {
            return false;
        } else {
            if (actionMasked == 5) {
                int actionIndex = motionEvent.getActionIndex();
                if (actionIndex < 0) {
                    Log.e(S, "Got ACTION_POINTER_DOWN event but have an invalid action index.");
                    return false;
                }
                this.u = motionEvent.getPointerId(actionIndex);
            } else if (actionMasked == 6) {
                a(motionEvent);
            }
        }
        return true;
    }

    public void requestDisallowInterceptTouchEvent(boolean z2) {
        if (Build.VERSION.SDK_INT >= 21 || !(this.e instanceof AbsListView)) {
            View view = this.e;
            if (view == null || v.E(view)) {
                super.requestDisallowInterceptTouchEvent(z2);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void setAnimationProgress(float f2) {
        this.y.setScaleX(f2);
        this.y.setScaleY(f2);
    }

    @Deprecated
    public void setColorScheme(int... iArr) {
        setColorSchemeResources(iArr);
    }

    public void setColorSchemeColors(int... iArr) {
        d();
        this.F.a(iArr);
    }

    public void setColorSchemeResources(int... iArr) {
        Context context = getContext();
        int[] iArr2 = new int[iArr.length];
        for (int i2 = 0; i2 < iArr.length; i2++) {
            iArr2[i2] = androidx.core.content.a.a(context, iArr[i2]);
        }
        setColorSchemeColors(iArr2);
    }

    public void setDistanceToTriggerSync(int i2) {
        this.f866i = (float) i2;
    }

    public void setEnabled(boolean z2) {
        super.setEnabled(z2);
        if (!z2) {
            b();
        }
    }

    public void setNestedScrollingEnabled(boolean z2) {
        this.l.a(z2);
    }

    public void setOnChildScrollUpCallback(i iVar) {
        this.O = iVar;
    }

    public void setOnRefreshListener(j jVar) {
        this.f863f = jVar;
    }

    @Deprecated
    public void setProgressBackgroundColor(int i2) {
        setProgressBackgroundColorSchemeResource(i2);
    }

    public void setProgressBackgroundColorSchemeColor(int i2) {
        this.y.setBackgroundColor(i2);
    }

    public void setProgressBackgroundColorSchemeResource(int i2) {
        setProgressBackgroundColorSchemeColor(androidx.core.content.a.a(getContext(), i2));
    }

    public void setRefreshing(boolean z2) {
        int i2;
        if (!z2 || this.f864g == z2) {
            a(z2, false);
            return;
        }
        this.f864g = z2;
        if (!this.N) {
            i2 = this.D + this.C;
        } else {
            i2 = this.D;
        }
        setTargetOffsetTopAndBottom(i2 - this.q);
        this.L = false;
        b(this.P);
    }

    public void setSize(int i2) {
        if (i2 == 0 || i2 == 1) {
            DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
            if (i2 == 0) {
                this.M = (int) (displayMetrics.density * 56.0f);
            } else {
                this.M = (int) (displayMetrics.density * 40.0f);
            }
            this.y.setImageDrawable((Drawable) null);
            this.F.a(i2);
            this.y.setImageDrawable(this.F);
        }
    }

    public void setSlingshotDistance(int i2) {
        this.E = i2;
    }

    /* access modifiers changed from: package-private */
    public void setTargetOffsetTopAndBottom(int i2) {
        this.y.bringToFront();
        v.e(this.y, i2);
        this.q = this.y.getTop();
    }

    public boolean startNestedScroll(int i2) {
        return this.l.b(i2);
    }

    public void stopNestedScroll() {
        this.l.c();
    }

    public SwipeRefreshLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.f864g = false;
        this.f866i = -1.0f;
        this.m = new int[2];
        this.n = new int[2];
        this.u = -1;
        this.z = -1;
        this.P = new a();
        this.Q = new f();
        this.R = new g();
        this.f865h = ViewConfiguration.get(context).getScaledTouchSlop();
        this.p = getResources().getInteger(17694721);
        setWillNotDraw(false);
        this.x = new DecelerateInterpolator(2.0f);
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        this.M = (int) (displayMetrics.density * 40.0f);
        c();
        setChildrenDrawingOrderEnabled(true);
        int i2 = (int) (displayMetrics.density * 64.0f);
        this.D = i2;
        this.f866i = (float) i2;
        this.k = new q(this);
        this.l = new m(this);
        setNestedScrollingEnabled(true);
        int i3 = -this.M;
        this.q = i3;
        this.C = i3;
        a(1.0f);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, T);
        setEnabled(obtainStyledAttributes.getBoolean(0, true));
        obtainStyledAttributes.recycle();
    }

    private void d(float f2) {
        float f3 = this.s;
        int i2 = this.f865h;
        if (f2 - f3 > ((float) i2) && !this.t) {
            this.r = f3 + ((float) i2);
            this.t = true;
            this.F.setAlpha(76);
        }
    }

    private void c(float f2) {
        this.F.a(true);
        float min = Math.min(1.0f, Math.abs(f2 / this.f866i));
        double d2 = (double) min;
        Double.isNaN(d2);
        float max = (((float) Math.max(d2 - 0.4d, 0.0d)) * 5.0f) / 3.0f;
        float abs = Math.abs(f2) - this.f866i;
        int i2 = this.E;
        if (i2 <= 0) {
            i2 = this.N ? this.D - this.C : this.D;
        }
        float f3 = (float) i2;
        double max2 = (double) (Math.max(0.0f, Math.min(abs, f3 * 2.0f) / f3) / 4.0f);
        double pow = Math.pow(max2, 2.0d);
        Double.isNaN(max2);
        float f4 = ((float) (max2 - pow)) * 2.0f;
        int i3 = this.C + ((int) ((f3 * min) + (f3 * f4 * 2.0f)));
        if (this.y.getVisibility() != 0) {
            this.y.setVisibility(0);
        }
        if (!this.v) {
            this.y.setScaleX(1.0f);
            this.y.setScaleY(1.0f);
        }
        if (this.v) {
            setAnimationProgress(Math.min(1.0f, f2 / this.f866i));
        }
        if (f2 < this.f866i) {
            if (this.F.getAlpha() > 76 && !a(this.I)) {
                f();
            }
        } else if (this.F.getAlpha() < 255 && !a(this.J)) {
            e();
        }
        this.F.a(0.0f, Math.min(0.8f, max * 0.8f));
        this.F.a(Math.min(1.0f, max));
        this.F.b((((max * 0.4f) - 16.0f) + (f4 * 2.0f)) * 0.5f);
        setTargetOffsetTopAndBottom(i3 - this.q);
    }

    /* access modifiers changed from: package-private */
    public void a(Animation.AnimationListener animationListener) {
        c cVar = new c();
        this.H = cVar;
        cVar.setDuration(150);
        this.y.a(animationListener);
        this.y.clearAnimation();
        this.y.startAnimation(this.H);
    }

    private void b(Animation.AnimationListener animationListener) {
        this.y.setVisibility(0);
        this.F.setAlpha(255);
        b bVar = new b();
        this.G = bVar;
        bVar.setDuration((long) this.p);
        if (animationListener != null) {
            this.y.a(animationListener);
        }
        this.y.clearAnimation();
        this.y.startAnimation(this.G);
    }

    private Animation a(int i2, int i3) {
        d dVar = new d(i2, i3);
        dVar.setDuration(300);
        this.y.a((Animation.AnimationListener) null);
        this.y.clearAnimation();
        this.y.startAnimation(dVar);
        return dVar;
    }

    private void b(float f2) {
        if (f2 > this.f866i) {
            a(true, true);
            return;
        }
        this.f864g = false;
        this.F.a(0.0f, 0.0f);
        e eVar = null;
        if (!this.v) {
            eVar = new e();
        }
        b(this.q, eVar);
        this.F.a(false);
    }

    public boolean a() {
        i iVar = this.O;
        if (iVar != null) {
            return iVar.a(this, this.e);
        }
        View view = this.e;
        if (view instanceof ListView) {
            return androidx.core.widget.g.a((ListView) view, -1);
        }
        return view.canScrollVertically(-1);
    }

    private boolean a(Animation animation) {
        return animation != null && animation.hasStarted() && !animation.hasEnded();
    }

    private void a(int i2, Animation.AnimationListener animationListener) {
        this.A = i2;
        this.Q.reset();
        this.Q.setDuration(200);
        this.Q.setInterpolator(this.x);
        if (animationListener != null) {
            this.y.a(animationListener);
        }
        this.y.clearAnimation();
        this.y.startAnimation(this.Q);
    }

    private void b(int i2, Animation.AnimationListener animationListener) {
        if (this.v) {
            c(i2, animationListener);
            return;
        }
        this.A = i2;
        this.R.reset();
        this.R.setDuration(200);
        this.R.setInterpolator(this.x);
        if (animationListener != null) {
            this.y.a(animationListener);
        }
        this.y.clearAnimation();
        this.y.startAnimation(this.R);
    }

    /* access modifiers changed from: package-private */
    public void a(float f2) {
        int i2 = this.A;
        setTargetOffsetTopAndBottom((i2 + ((int) (((float) (this.C - i2)) * f2))) - this.y.getTop());
    }

    private void a(MotionEvent motionEvent) {
        int actionIndex = motionEvent.getActionIndex();
        if (motionEvent.getPointerId(actionIndex) == this.u) {
            this.u = motionEvent.getPointerId(actionIndex == 0 ? 1 : 0);
        }
    }

    private void c(int i2, Animation.AnimationListener animationListener) {
        this.A = i2;
        this.B = this.y.getScaleX();
        h hVar = new h();
        this.K = hVar;
        hVar.setDuration(150);
        if (animationListener != null) {
            this.y.a(animationListener);
        }
        this.y.clearAnimation();
        this.y.startAnimation(this.K);
    }
}
