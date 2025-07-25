package androidx.appcompat.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.view.Window;
import android.widget.OverScroller;
import androidx.appcompat.R$attr;
import androidx.appcompat.R$id;
import androidx.appcompat.view.menu.m;
import androidx.core.h.n;
import androidx.core.h.o;
import androidx.core.h.p;
import androidx.core.h.q;
import androidx.core.h.v;

public class ActionBarOverlayLayout extends ViewGroup implements o, p, n, o {
    static final int[] F = {R$attr.actionBarSize, 16842841};
    ViewPropertyAnimator A;
    final AnimatorListenerAdapter B;
    private final Runnable C;
    private final Runnable D;
    private final q E;
    private int e;

    /* renamed from: f  reason: collision with root package name */
    private int f179f;

    /* renamed from: g  reason: collision with root package name */
    private ContentFrameLayout f180g;

    /* renamed from: h  reason: collision with root package name */
    ActionBarContainer f181h;

    /* renamed from: i  reason: collision with root package name */
    private p f182i;

    /* renamed from: j  reason: collision with root package name */
    private Drawable f183j;
    private boolean k;
    private boolean l;
    private boolean m;
    private boolean n;
    boolean o;
    private int p;
    private int q;
    private final Rect r;
    private final Rect s;
    private final Rect t;
    private final Rect u;
    private final Rect v;
    private final Rect w;
    private final Rect x;
    private d y;
    private OverScroller z;

    class a extends AnimatorListenerAdapter {
        a() {
        }

        public void onAnimationCancel(Animator animator) {
            ActionBarOverlayLayout actionBarOverlayLayout = ActionBarOverlayLayout.this;
            actionBarOverlayLayout.A = null;
            actionBarOverlayLayout.o = false;
        }

        public void onAnimationEnd(Animator animator) {
            ActionBarOverlayLayout actionBarOverlayLayout = ActionBarOverlayLayout.this;
            actionBarOverlayLayout.A = null;
            actionBarOverlayLayout.o = false;
        }
    }

    class b implements Runnable {
        b() {
        }

        public void run() {
            ActionBarOverlayLayout.this.h();
            ActionBarOverlayLayout actionBarOverlayLayout = ActionBarOverlayLayout.this;
            actionBarOverlayLayout.A = actionBarOverlayLayout.f181h.animate().translationY(0.0f).setListener(ActionBarOverlayLayout.this.B);
        }
    }

    class c implements Runnable {
        c() {
        }

        public void run() {
            ActionBarOverlayLayout.this.h();
            ActionBarOverlayLayout actionBarOverlayLayout = ActionBarOverlayLayout.this;
            actionBarOverlayLayout.A = actionBarOverlayLayout.f181h.animate().translationY((float) (-ActionBarOverlayLayout.this.f181h.getHeight())).setListener(ActionBarOverlayLayout.this.B);
        }
    }

    public interface d {
        void a();

        void a(int i2);

        void a(boolean z);

        void b();

        void c();

        void d();
    }

    public static class e extends ViewGroup.MarginLayoutParams {
        public e(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
        }

        public e(int i2, int i3) {
            super(i2, i3);
        }

        public e(ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
        }
    }

    public ActionBarOverlayLayout(Context context) {
        this(context, (AttributeSet) null);
    }

    private void a(Context context) {
        TypedArray obtainStyledAttributes = getContext().getTheme().obtainStyledAttributes(F);
        boolean z2 = false;
        this.e = obtainStyledAttributes.getDimensionPixelSize(0, 0);
        Drawable drawable = obtainStyledAttributes.getDrawable(1);
        this.f183j = drawable;
        setWillNotDraw(drawable == null);
        obtainStyledAttributes.recycle();
        if (context.getApplicationInfo().targetSdkVersion < 19) {
            z2 = true;
        }
        this.k = z2;
        this.z = new OverScroller(context);
    }

    private void k() {
        h();
        this.D.run();
    }

    private void l() {
        h();
        postDelayed(this.D, 600);
    }

    private void m() {
        h();
        postDelayed(this.C, 600);
    }

    private void n() {
        h();
        this.C.run();
    }

    public boolean b(View view, View view2, int i2, int i3) {
        return i3 == 0 && onStartNestedScroll(view, view2, i2);
    }

    public boolean c() {
        j();
        return this.f182i.c();
    }

    /* access modifiers changed from: protected */
    public boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return layoutParams instanceof e;
    }

    public boolean d() {
        j();
        return this.f182i.d();
    }

    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (this.f183j != null && !this.k) {
            int bottom = this.f181h.getVisibility() == 0 ? (int) (((float) this.f181h.getBottom()) + this.f181h.getTranslationY() + 0.5f) : 0;
            this.f183j.setBounds(0, bottom, getWidth(), this.f183j.getIntrinsicHeight() + bottom);
            this.f183j.draw(canvas);
        }
    }

    public void e() {
        j();
        this.f182i.e();
    }

    public boolean f() {
        j();
        return this.f182i.f();
    }

    /* access modifiers changed from: protected */
    public boolean fitSystemWindows(Rect rect) {
        j();
        int w2 = v.w(this) & 256;
        boolean a2 = a((View) this.f181h, rect, true, true, false, true);
        this.u.set(rect);
        m0.a(this, this.u, this.r);
        if (!this.v.equals(this.u)) {
            this.v.set(this.u);
            a2 = true;
        }
        if (!this.s.equals(this.r)) {
            this.s.set(this.r);
            a2 = true;
        }
        if (a2) {
            requestLayout();
        }
        return true;
    }

    public void g() {
        j();
        this.f182i.g();
    }

    public int getActionBarHideOffset() {
        ActionBarContainer actionBarContainer = this.f181h;
        if (actionBarContainer != null) {
            return -((int) actionBarContainer.getTranslationY());
        }
        return 0;
    }

    public int getNestedScrollAxes() {
        return this.E.a();
    }

    public CharSequence getTitle() {
        j();
        return this.f182i.getTitle();
    }

    /* access modifiers changed from: package-private */
    public void h() {
        removeCallbacks(this.C);
        removeCallbacks(this.D);
        ViewPropertyAnimator viewPropertyAnimator = this.A;
        if (viewPropertyAnimator != null) {
            viewPropertyAnimator.cancel();
        }
    }

    public boolean i() {
        return this.l;
    }

    /* access modifiers changed from: package-private */
    public void j() {
        if (this.f180g == null) {
            this.f180g = (ContentFrameLayout) findViewById(R$id.action_bar_activity_content);
            this.f181h = (ActionBarContainer) findViewById(R$id.action_bar_container);
            this.f182i = a(findViewById(R$id.action_bar));
        }
    }

    /* access modifiers changed from: protected */
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        a(getContext());
        v.I(this);
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        h();
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z2, int i2, int i3, int i4, int i5) {
        int childCount = getChildCount();
        int paddingLeft = getPaddingLeft();
        getPaddingRight();
        int paddingTop = getPaddingTop();
        getPaddingBottom();
        for (int i6 = 0; i6 < childCount; i6++) {
            View childAt = getChildAt(i6);
            if (childAt.getVisibility() != 8) {
                e eVar = (e) childAt.getLayoutParams();
                int measuredWidth = childAt.getMeasuredWidth();
                int measuredHeight = childAt.getMeasuredHeight();
                int i7 = eVar.leftMargin + paddingLeft;
                int i8 = eVar.topMargin + paddingTop;
                childAt.layout(i7, i8, measuredWidth + i7, measuredHeight + i8);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i2, int i3) {
        int i4;
        j();
        measureChildWithMargins(this.f181h, i2, 0, i3, 0);
        e eVar = (e) this.f181h.getLayoutParams();
        int max = Math.max(0, this.f181h.getMeasuredWidth() + eVar.leftMargin + eVar.rightMargin);
        int max2 = Math.max(0, this.f181h.getMeasuredHeight() + eVar.topMargin + eVar.bottomMargin);
        int combineMeasuredStates = View.combineMeasuredStates(0, this.f181h.getMeasuredState());
        boolean z2 = (v.w(this) & 256) != 0;
        if (z2) {
            i4 = this.e;
            if (this.m && this.f181h.getTabContainer() != null) {
                i4 += this.e;
            }
        } else {
            i4 = this.f181h.getVisibility() != 8 ? this.f181h.getMeasuredHeight() : 0;
        }
        this.t.set(this.r);
        this.w.set(this.u);
        if (this.l || z2) {
            Rect rect = this.w;
            rect.top += i4;
            rect.bottom += 0;
        } else {
            Rect rect2 = this.t;
            rect2.top += i4;
            rect2.bottom += 0;
        }
        a((View) this.f180g, this.t, true, true, true, true);
        if (!this.x.equals(this.w)) {
            this.x.set(this.w);
            this.f180g.a(this.w);
        }
        measureChildWithMargins(this.f180g, i2, 0, i3, 0);
        e eVar2 = (e) this.f180g.getLayoutParams();
        int max3 = Math.max(max, this.f180g.getMeasuredWidth() + eVar2.leftMargin + eVar2.rightMargin);
        int max4 = Math.max(max2, this.f180g.getMeasuredHeight() + eVar2.topMargin + eVar2.bottomMargin);
        int combineMeasuredStates2 = View.combineMeasuredStates(combineMeasuredStates, this.f180g.getMeasuredState());
        setMeasuredDimension(View.resolveSizeAndState(Math.max(max3 + getPaddingLeft() + getPaddingRight(), getSuggestedMinimumWidth()), i2, combineMeasuredStates2), View.resolveSizeAndState(Math.max(max4 + getPaddingTop() + getPaddingBottom(), getSuggestedMinimumHeight()), i3, combineMeasuredStates2 << 16));
    }

    public boolean onNestedFling(View view, float f2, float f3, boolean z2) {
        if (!this.n || !z2) {
            return false;
        }
        if (a(f2, f3)) {
            k();
        } else {
            n();
        }
        this.o = true;
        return true;
    }

    public boolean onNestedPreFling(View view, float f2, float f3) {
        return false;
    }

    public void onNestedPreScroll(View view, int i2, int i3, int[] iArr) {
    }

    public void onNestedScroll(View view, int i2, int i3, int i4, int i5) {
        int i6 = this.p + i3;
        this.p = i6;
        setActionBarHideOffset(i6);
    }

    public void onNestedScrollAccepted(View view, View view2, int i2) {
        this.E.a(view, view2, i2);
        this.p = getActionBarHideOffset();
        h();
        d dVar = this.y;
        if (dVar != null) {
            dVar.b();
        }
    }

    public boolean onStartNestedScroll(View view, View view2, int i2) {
        if ((i2 & 2) == 0 || this.f181h.getVisibility() != 0) {
            return false;
        }
        return this.n;
    }

    public void onStopNestedScroll(View view) {
        if (this.n && !this.o) {
            if (this.p <= this.f181h.getHeight()) {
                m();
            } else {
                l();
            }
        }
        d dVar = this.y;
        if (dVar != null) {
            dVar.d();
        }
    }

    public void onWindowSystemUiVisibilityChanged(int i2) {
        if (Build.VERSION.SDK_INT >= 16) {
            super.onWindowSystemUiVisibilityChanged(i2);
        }
        j();
        int i3 = this.q ^ i2;
        this.q = i2;
        boolean z2 = false;
        boolean z3 = (i2 & 4) == 0;
        if ((i2 & 256) != 0) {
            z2 = true;
        }
        d dVar = this.y;
        if (dVar != null) {
            dVar.a(!z2);
            if (z3 || !z2) {
                this.y.a();
            } else {
                this.y.c();
            }
        }
        if ((i3 & 256) != 0 && this.y != null) {
            v.I(this);
        }
    }

    /* access modifiers changed from: protected */
    public void onWindowVisibilityChanged(int i2) {
        super.onWindowVisibilityChanged(i2);
        this.f179f = i2;
        d dVar = this.y;
        if (dVar != null) {
            dVar.a(i2);
        }
    }

    public void setActionBarHideOffset(int i2) {
        h();
        this.f181h.setTranslationY((float) (-Math.max(0, Math.min(i2, this.f181h.getHeight()))));
    }

    public void setActionBarVisibilityCallback(d dVar) {
        this.y = dVar;
        if (getWindowToken() != null) {
            this.y.a(this.f179f);
            int i2 = this.q;
            if (i2 != 0) {
                onWindowSystemUiVisibilityChanged(i2);
                v.I(this);
            }
        }
    }

    public void setHasNonEmbeddedTabs(boolean z2) {
        this.m = z2;
    }

    public void setHideOnContentScrollEnabled(boolean z2) {
        if (z2 != this.n) {
            this.n = z2;
            if (!z2) {
                h();
                setActionBarHideOffset(0);
            }
        }
    }

    public void setIcon(int i2) {
        j();
        this.f182i.setIcon(i2);
    }

    public void setLogo(int i2) {
        j();
        this.f182i.b(i2);
    }

    public void setOverlayMode(boolean z2) {
        this.l = z2;
        this.k = z2 && getContext().getApplicationInfo().targetSdkVersion < 19;
    }

    public void setShowingForActionMode(boolean z2) {
    }

    public void setUiOptions(int i2) {
    }

    public void setWindowCallback(Window.Callback callback) {
        j();
        this.f182i.setWindowCallback(callback);
    }

    public void setWindowTitle(CharSequence charSequence) {
        j();
        this.f182i.setWindowTitle(charSequence);
    }

    public boolean shouldDelayChildPressedState() {
        return false;
    }

    public ActionBarOverlayLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.f179f = 0;
        this.r = new Rect();
        this.s = new Rect();
        this.t = new Rect();
        this.u = new Rect();
        this.v = new Rect();
        this.w = new Rect();
        this.x = new Rect();
        this.B = new a();
        this.C = new b();
        this.D = new c();
        a(context);
        this.E = new q(this);
    }

    public boolean b() {
        j();
        return this.f182i.b();
    }

    /* access modifiers changed from: protected */
    public e generateDefaultLayoutParams() {
        return new e(-1, -1);
    }

    public e generateLayoutParams(AttributeSet attributeSet) {
        return new e(getContext(), attributeSet);
    }

    /* access modifiers changed from: protected */
    public ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return new e(layoutParams);
    }

    public void setIcon(Drawable drawable) {
        j();
        this.f182i.setIcon(drawable);
    }

    private boolean a(View view, Rect rect, boolean z2, boolean z3, boolean z4, boolean z5) {
        boolean z6;
        int i2;
        int i3;
        int i4;
        int i5;
        e eVar = (e) view.getLayoutParams();
        if (!z2 || eVar.leftMargin == (i5 = rect.left)) {
            z6 = false;
        } else {
            eVar.leftMargin = i5;
            z6 = true;
        }
        if (z3 && eVar.topMargin != (i4 = rect.top)) {
            eVar.topMargin = i4;
            z6 = true;
        }
        if (z5 && eVar.rightMargin != (i3 = rect.right)) {
            eVar.rightMargin = i3;
            z6 = true;
        }
        if (!z4 || eVar.bottomMargin == (i2 = rect.bottom)) {
            return z6;
        }
        eVar.bottomMargin = i2;
        return true;
    }

    public void a(View view, int i2, int i3, int i4, int i5, int i6, int[] iArr) {
        a(view, i2, i3, i4, i5, i6);
    }

    public void a(View view, View view2, int i2, int i3) {
        if (i3 == 0) {
            onNestedScrollAccepted(view, view2, i2);
        }
    }

    public void a(View view, int i2) {
        if (i2 == 0) {
            onStopNestedScroll(view);
        }
    }

    public void a(View view, int i2, int i3, int i4, int i5, int i6) {
        if (i6 == 0) {
            onNestedScroll(view, i2, i3, i4, i5);
        }
    }

    public void a(View view, int i2, int i3, int[] iArr, int i4) {
        if (i4 == 0) {
            onNestedPreScroll(view, i2, i3, iArr);
        }
    }

    private p a(View view) {
        if (view instanceof p) {
            return (p) view;
        }
        if (view instanceof Toolbar) {
            return ((Toolbar) view).getWrapper();
        }
        throw new IllegalStateException("Can't make a decor toolbar out of " + view.getClass().getSimpleName());
    }

    private boolean a(float f2, float f3) {
        this.z.fling(0, 0, 0, (int) f3, 0, 0, Integer.MIN_VALUE, Integer.MAX_VALUE);
        return this.z.getFinalY() > this.f181h.getHeight();
    }

    public void a(int i2) {
        j();
        if (i2 == 2) {
            this.f182i.l();
        } else if (i2 == 5) {
            this.f182i.n();
        } else if (i2 == 109) {
            setOverlayMode(true);
        }
    }

    public boolean a() {
        j();
        return this.f182i.a();
    }

    public void a(Menu menu, m.a aVar) {
        j();
        this.f182i.a(menu, aVar);
    }
}
