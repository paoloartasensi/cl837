package com.google.android.material.appbar;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;
import androidx.appcompat.widget.Toolbar;
import androidx.core.h.d0;
import androidx.core.h.r;
import androidx.core.h.v;
import com.google.android.material.R$id;
import com.google.android.material.R$style;
import com.google.android.material.R$styleable;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.internal.k;

public class CollapsingToolbarLayout extends FrameLayout {
    d0 A;
    private boolean e;

    /* renamed from: f  reason: collision with root package name */
    private int f1384f;

    /* renamed from: g  reason: collision with root package name */
    private Toolbar f1385g;

    /* renamed from: h  reason: collision with root package name */
    private View f1386h;

    /* renamed from: i  reason: collision with root package name */
    private View f1387i;

    /* renamed from: j  reason: collision with root package name */
    private int f1388j;
    private int k;
    private int l;
    private int m;
    private final Rect n;
    final com.google.android.material.internal.c o;
    private boolean p;
    private boolean q;
    private Drawable r;
    Drawable s;
    private int t;
    private boolean u;
    private ValueAnimator v;
    private long w;
    private int x;
    private AppBarLayout.d y;
    int z;

    class a implements r {
        a() {
        }

        public d0 a(View view, d0 d0Var) {
            return CollapsingToolbarLayout.this.a(d0Var);
        }
    }

    class b implements ValueAnimator.AnimatorUpdateListener {
        b() {
        }

        public void onAnimationUpdate(ValueAnimator valueAnimator) {
            CollapsingToolbarLayout.this.setScrimAlpha(((Integer) valueAnimator.getAnimatedValue()).intValue());
        }
    }

    private class d implements AppBarLayout.d {
        d() {
        }

        public void a(AppBarLayout appBarLayout, int i2) {
            CollapsingToolbarLayout collapsingToolbarLayout = CollapsingToolbarLayout.this;
            collapsingToolbarLayout.z = i2;
            d0 d0Var = collapsingToolbarLayout.A;
            int e = d0Var != null ? d0Var.e() : 0;
            int childCount = CollapsingToolbarLayout.this.getChildCount();
            for (int i3 = 0; i3 < childCount; i3++) {
                View childAt = CollapsingToolbarLayout.this.getChildAt(i3);
                c cVar = (c) childAt.getLayoutParams();
                d d = CollapsingToolbarLayout.d(childAt);
                int i4 = cVar.a;
                if (i4 == 1) {
                    d.b(androidx.core.c.a.a(-i2, 0, CollapsingToolbarLayout.this.a(childAt)));
                } else if (i4 == 2) {
                    d.b(Math.round(((float) (-i2)) * cVar.b));
                }
            }
            CollapsingToolbarLayout.this.a();
            CollapsingToolbarLayout collapsingToolbarLayout2 = CollapsingToolbarLayout.this;
            if (collapsingToolbarLayout2.s != null && e > 0) {
                v.H(collapsingToolbarLayout2);
            }
            CollapsingToolbarLayout.this.o.b(((float) Math.abs(i2)) / ((float) ((CollapsingToolbarLayout.this.getHeight() - v.p(CollapsingToolbarLayout.this)) - e)));
        }
    }

    public CollapsingToolbarLayout(Context context) {
        this(context, (AttributeSet) null);
    }

    /* JADX WARNING: type inference failed for: r4v0, types: [android.view.View] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void b() {
        /*
            r6 = this;
            boolean r0 = r6.e
            if (r0 != 0) goto L_0x0005
            return
        L_0x0005:
            r0 = 0
            r6.f1385g = r0
            r6.f1386h = r0
            int r1 = r6.f1384f
            r2 = -1
            if (r1 == r2) goto L_0x001f
            android.view.View r1 = r6.findViewById(r1)
            androidx.appcompat.widget.Toolbar r1 = (androidx.appcompat.widget.Toolbar) r1
            r6.f1385g = r1
            if (r1 == 0) goto L_0x001f
            android.view.View r1 = r6.b(r1)
            r6.f1386h = r1
        L_0x001f:
            androidx.appcompat.widget.Toolbar r1 = r6.f1385g
            r2 = 0
            if (r1 != 0) goto L_0x003c
            int r1 = r6.getChildCount()
            r3 = 0
        L_0x0029:
            if (r3 >= r1) goto L_0x003a
            android.view.View r4 = r6.getChildAt(r3)
            boolean r5 = r4 instanceof androidx.appcompat.widget.Toolbar
            if (r5 == 0) goto L_0x0037
            r0 = r4
            androidx.appcompat.widget.Toolbar r0 = (androidx.appcompat.widget.Toolbar) r0
            goto L_0x003a
        L_0x0037:
            int r3 = r3 + 1
            goto L_0x0029
        L_0x003a:
            r6.f1385g = r0
        L_0x003c:
            r6.d()
            r6.e = r2
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.material.appbar.CollapsingToolbarLayout.b():void");
    }

    private static int c(View view) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (!(layoutParams instanceof ViewGroup.MarginLayoutParams)) {
            return view.getHeight();
        }
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) layoutParams;
        return view.getHeight() + marginLayoutParams.topMargin + marginLayoutParams.bottomMargin;
    }

    private void d() {
        View view;
        if (!this.p && (view = this.f1387i) != null) {
            ViewParent parent = view.getParent();
            if (parent instanceof ViewGroup) {
                ((ViewGroup) parent).removeView(this.f1387i);
            }
        }
        if (this.p && this.f1385g != null) {
            if (this.f1387i == null) {
                this.f1387i = new View(getContext());
            }
            if (this.f1387i.getParent() == null) {
                this.f1385g.addView(this.f1387i, -1, -1);
            }
        }
    }

    private boolean e(View view) {
        View view2 = this.f1386h;
        if (view2 == null || view2 == this) {
            if (view == this.f1385g) {
                return true;
            }
        } else if (view == view2) {
            return true;
        }
        return false;
    }

    /* access modifiers changed from: package-private */
    public d0 a(d0 d0Var) {
        d0 d0Var2 = v.l(this) ? d0Var : null;
        if (!androidx.core.g.c.a(this.A, d0Var2)) {
            this.A = d0Var2;
            requestLayout();
        }
        return d0Var.a();
    }

    /* access modifiers changed from: protected */
    public boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return layoutParams instanceof c;
    }

    public void draw(Canvas canvas) {
        Drawable drawable;
        super.draw(canvas);
        b();
        if (this.f1385g == null && (drawable = this.r) != null && this.t > 0) {
            drawable.mutate().setAlpha(this.t);
            this.r.draw(canvas);
        }
        if (this.p && this.q) {
            this.o.a(canvas);
        }
        if (this.s != null && this.t > 0) {
            d0 d0Var = this.A;
            int e2 = d0Var != null ? d0Var.e() : 0;
            if (e2 > 0) {
                this.s.setBounds(0, -this.z, getWidth(), e2 - this.z);
                this.s.mutate().setAlpha(this.t);
                this.s.draw(canvas);
            }
        }
    }

    /* access modifiers changed from: protected */
    public boolean drawChild(Canvas canvas, View view, long j2) {
        boolean z2;
        if (this.r == null || this.t <= 0 || !e(view)) {
            z2 = false;
        } else {
            this.r.mutate().setAlpha(this.t);
            this.r.draw(canvas);
            z2 = true;
        }
        if (super.drawChild(canvas, view, j2) || z2) {
            return true;
        }
        return false;
    }

    /* access modifiers changed from: protected */
    public void drawableStateChanged() {
        super.drawableStateChanged();
        int[] drawableState = getDrawableState();
        Drawable drawable = this.s;
        boolean z2 = false;
        if (drawable != null && drawable.isStateful()) {
            z2 = false | drawable.setState(drawableState);
        }
        Drawable drawable2 = this.r;
        if (drawable2 != null && drawable2.isStateful()) {
            z2 |= drawable2.setState(drawableState);
        }
        com.google.android.material.internal.c cVar = this.o;
        if (cVar != null) {
            z2 |= cVar.a(drawableState);
        }
        if (z2) {
            invalidate();
        }
    }

    public int getCollapsedTitleGravity() {
        return this.o.c();
    }

    public Typeface getCollapsedTitleTypeface() {
        return this.o.e();
    }

    public Drawable getContentScrim() {
        return this.r;
    }

    public int getExpandedTitleGravity() {
        return this.o.g();
    }

    public int getExpandedTitleMarginBottom() {
        return this.m;
    }

    public int getExpandedTitleMarginEnd() {
        return this.l;
    }

    public int getExpandedTitleMarginStart() {
        return this.f1388j;
    }

    public int getExpandedTitleMarginTop() {
        return this.k;
    }

    public Typeface getExpandedTitleTypeface() {
        return this.o.h();
    }

    /* access modifiers changed from: package-private */
    public int getScrimAlpha() {
        return this.t;
    }

    public long getScrimAnimationDuration() {
        return this.w;
    }

    public int getScrimVisibleHeightTrigger() {
        int i2 = this.x;
        if (i2 >= 0) {
            return i2;
        }
        d0 d0Var = this.A;
        int e2 = d0Var != null ? d0Var.e() : 0;
        int p2 = v.p(this);
        if (p2 > 0) {
            return Math.min((p2 * 2) + e2, getHeight());
        }
        return getHeight() / 3;
    }

    public Drawable getStatusBarScrim() {
        return this.s;
    }

    public CharSequence getTitle() {
        if (this.p) {
            return this.o.j();
        }
        return null;
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        ViewParent parent = getParent();
        if (parent instanceof AppBarLayout) {
            v.a((View) this, v.l((View) parent));
            if (this.y == null) {
                this.y = new d();
            }
            ((AppBarLayout) parent).addOnOffsetChangedListener(this.y);
            v.I(this);
        }
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        ViewParent parent = getParent();
        AppBarLayout.d dVar = this.y;
        if (dVar != null && (parent instanceof AppBarLayout)) {
            ((AppBarLayout) parent).removeOnOffsetChangedListener(dVar);
        }
        super.onDetachedFromWindow();
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z2, int i2, int i3, int i4, int i5) {
        View view;
        super.onLayout(z2, i2, i3, i4, i5);
        d0 d0Var = this.A;
        if (d0Var != null) {
            int e2 = d0Var.e();
            int childCount = getChildCount();
            for (int i6 = 0; i6 < childCount; i6++) {
                View childAt = getChildAt(i6);
                if (!v.l(childAt) && childAt.getTop() < e2) {
                    v.e(childAt, e2);
                }
            }
        }
        if (this.p && (view = this.f1387i) != null) {
            boolean z3 = true;
            boolean z4 = v.C(view) && this.f1387i.getVisibility() == 0;
            this.q = z4;
            if (z4) {
                if (v.o(this) != 1) {
                    z3 = false;
                }
                View view2 = this.f1386h;
                if (view2 == null) {
                    view2 = this.f1385g;
                }
                int a2 = a(view2);
                com.google.android.material.internal.d.a((ViewGroup) this, this.f1387i, this.n);
                this.o.a(this.n.left + (z3 ? this.f1385g.getTitleMarginEnd() : this.f1385g.getTitleMarginStart()), this.n.top + a2 + this.f1385g.getTitleMarginTop(), this.n.right + (z3 ? this.f1385g.getTitleMarginStart() : this.f1385g.getTitleMarginEnd()), (this.n.bottom + a2) - this.f1385g.getTitleMarginBottom());
                this.o.b(z3 ? this.l : this.f1388j, this.n.top + this.k, (i4 - i2) - (z3 ? this.f1388j : this.l), (i5 - i3) - this.m);
                this.o.m();
            }
        }
        int childCount2 = getChildCount();
        for (int i7 = 0; i7 < childCount2; i7++) {
            d(getChildAt(i7)).c();
        }
        if (this.f1385g != null) {
            if (this.p && TextUtils.isEmpty(this.o.j())) {
                setTitle(this.f1385g.getTitle());
            }
            View view3 = this.f1386h;
            if (view3 == null || view3 == this) {
                setMinimumHeight(c(this.f1385g));
            } else {
                setMinimumHeight(c(view3));
            }
        }
        a();
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i2, int i3) {
        b();
        super.onMeasure(i2, i3);
        int mode = View.MeasureSpec.getMode(i3);
        d0 d0Var = this.A;
        int e2 = d0Var != null ? d0Var.e() : 0;
        if (mode == 0 && e2 > 0) {
            super.onMeasure(i2, View.MeasureSpec.makeMeasureSpec(getMeasuredHeight() + e2, 1073741824));
        }
    }

    /* access modifiers changed from: protected */
    public void onSizeChanged(int i2, int i3, int i4, int i5) {
        super.onSizeChanged(i2, i3, i4, i5);
        Drawable drawable = this.r;
        if (drawable != null) {
            drawable.setBounds(0, 0, i2, i3);
        }
    }

    public void setCollapsedTitleGravity(int i2) {
        this.o.b(i2);
    }

    public void setCollapsedTitleTextAppearance(int i2) {
        this.o.a(i2);
    }

    public void setCollapsedTitleTextColor(int i2) {
        setCollapsedTitleTextColor(ColorStateList.valueOf(i2));
    }

    public void setCollapsedTitleTypeface(Typeface typeface) {
        this.o.a(typeface);
    }

    public void setContentScrim(Drawable drawable) {
        Drawable drawable2 = this.r;
        if (drawable2 != drawable) {
            Drawable drawable3 = null;
            if (drawable2 != null) {
                drawable2.setCallback((Drawable.Callback) null);
            }
            if (drawable != null) {
                drawable3 = drawable.mutate();
            }
            this.r = drawable3;
            if (drawable3 != null) {
                drawable3.setBounds(0, 0, getWidth(), getHeight());
                this.r.setCallback(this);
                this.r.setAlpha(this.t);
            }
            v.H(this);
        }
    }

    public void setContentScrimColor(int i2) {
        setContentScrim(new ColorDrawable(i2));
    }

    public void setContentScrimResource(int i2) {
        setContentScrim(androidx.core.content.a.c(getContext(), i2));
    }

    public void setExpandedTitleColor(int i2) {
        setExpandedTitleTextColor(ColorStateList.valueOf(i2));
    }

    public void setExpandedTitleGravity(int i2) {
        this.o.d(i2);
    }

    public void setExpandedTitleMarginBottom(int i2) {
        this.m = i2;
        requestLayout();
    }

    public void setExpandedTitleMarginEnd(int i2) {
        this.l = i2;
        requestLayout();
    }

    public void setExpandedTitleMarginStart(int i2) {
        this.f1388j = i2;
        requestLayout();
    }

    public void setExpandedTitleMarginTop(int i2) {
        this.k = i2;
        requestLayout();
    }

    public void setExpandedTitleTextAppearance(int i2) {
        this.o.c(i2);
    }

    public void setExpandedTitleTextColor(ColorStateList colorStateList) {
        this.o.b(colorStateList);
    }

    public void setExpandedTitleTypeface(Typeface typeface) {
        this.o.b(typeface);
    }

    /* access modifiers changed from: package-private */
    public void setScrimAlpha(int i2) {
        Toolbar toolbar;
        if (i2 != this.t) {
            if (!(this.r == null || (toolbar = this.f1385g) == null)) {
                v.H(toolbar);
            }
            this.t = i2;
            v.H(this);
        }
    }

    public void setScrimAnimationDuration(long j2) {
        this.w = j2;
    }

    public void setScrimVisibleHeightTrigger(int i2) {
        if (this.x != i2) {
            this.x = i2;
            a();
        }
    }

    public void setScrimsShown(boolean z2) {
        a(z2, v.D(this) && !isInEditMode());
    }

    public void setStatusBarScrim(Drawable drawable) {
        Drawable drawable2 = this.s;
        if (drawable2 != drawable) {
            Drawable drawable3 = null;
            if (drawable2 != null) {
                drawable2.setCallback((Drawable.Callback) null);
            }
            if (drawable != null) {
                drawable3 = drawable.mutate();
            }
            this.s = drawable3;
            if (drawable3 != null) {
                if (drawable3.isStateful()) {
                    this.s.setState(getDrawableState());
                }
                androidx.core.graphics.drawable.a.a(this.s, v.o(this));
                this.s.setVisible(getVisibility() == 0, false);
                this.s.setCallback(this);
                this.s.setAlpha(this.t);
            }
            v.H(this);
        }
    }

    public void setStatusBarScrimColor(int i2) {
        setStatusBarScrim(new ColorDrawable(i2));
    }

    public void setStatusBarScrimResource(int i2) {
        setStatusBarScrim(androidx.core.content.a.c(getContext(), i2));
    }

    public void setTitle(CharSequence charSequence) {
        this.o.a(charSequence);
        c();
    }

    public void setTitleEnabled(boolean z2) {
        if (z2 != this.p) {
            this.p = z2;
            c();
            d();
            requestLayout();
        }
    }

    public void setVisibility(int i2) {
        super.setVisibility(i2);
        boolean z2 = i2 == 0;
        Drawable drawable = this.s;
        if (!(drawable == null || drawable.isVisible() == z2)) {
            this.s.setVisible(z2, false);
        }
        Drawable drawable2 = this.r;
        if (drawable2 != null && drawable2.isVisible() != z2) {
            this.r.setVisible(z2, false);
        }
    }

    /* access modifiers changed from: protected */
    public boolean verifyDrawable(Drawable drawable) {
        return super.verifyDrawable(drawable) || drawable == this.r || drawable == this.s;
    }

    public CollapsingToolbarLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public void setCollapsedTitleTextColor(ColorStateList colorStateList) {
        this.o.a(colorStateList);
    }

    public CollapsingToolbarLayout(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.e = true;
        this.n = new Rect();
        this.x = -1;
        com.google.android.material.internal.c cVar = new com.google.android.material.internal.c(this);
        this.o = cVar;
        cVar.b(com.google.android.material.a.a.e);
        TypedArray c2 = k.c(context, attributeSet, R$styleable.CollapsingToolbarLayout, i2, R$style.Widget_Design_CollapsingToolbar, new int[0]);
        this.o.d(c2.getInt(R$styleable.CollapsingToolbarLayout_expandedTitleGravity, 8388691));
        this.o.b(c2.getInt(R$styleable.CollapsingToolbarLayout_collapsedTitleGravity, 8388627));
        int dimensionPixelSize = c2.getDimensionPixelSize(R$styleable.CollapsingToolbarLayout_expandedTitleMargin, 0);
        this.m = dimensionPixelSize;
        this.l = dimensionPixelSize;
        this.k = dimensionPixelSize;
        this.f1388j = dimensionPixelSize;
        if (c2.hasValue(R$styleable.CollapsingToolbarLayout_expandedTitleMarginStart)) {
            this.f1388j = c2.getDimensionPixelSize(R$styleable.CollapsingToolbarLayout_expandedTitleMarginStart, 0);
        }
        if (c2.hasValue(R$styleable.CollapsingToolbarLayout_expandedTitleMarginEnd)) {
            this.l = c2.getDimensionPixelSize(R$styleable.CollapsingToolbarLayout_expandedTitleMarginEnd, 0);
        }
        if (c2.hasValue(R$styleable.CollapsingToolbarLayout_expandedTitleMarginTop)) {
            this.k = c2.getDimensionPixelSize(R$styleable.CollapsingToolbarLayout_expandedTitleMarginTop, 0);
        }
        if (c2.hasValue(R$styleable.CollapsingToolbarLayout_expandedTitleMarginBottom)) {
            this.m = c2.getDimensionPixelSize(R$styleable.CollapsingToolbarLayout_expandedTitleMarginBottom, 0);
        }
        this.p = c2.getBoolean(R$styleable.CollapsingToolbarLayout_titleEnabled, true);
        setTitle(c2.getText(R$styleable.CollapsingToolbarLayout_title));
        this.o.c(R$style.TextAppearance_Design_CollapsingToolbar_Expanded);
        this.o.a(androidx.appcompat.R$style.TextAppearance_AppCompat_Widget_ActionBar_Title);
        if (c2.hasValue(R$styleable.CollapsingToolbarLayout_expandedTitleTextAppearance)) {
            this.o.c(c2.getResourceId(R$styleable.CollapsingToolbarLayout_expandedTitleTextAppearance, 0));
        }
        if (c2.hasValue(R$styleable.CollapsingToolbarLayout_collapsedTitleTextAppearance)) {
            this.o.a(c2.getResourceId(R$styleable.CollapsingToolbarLayout_collapsedTitleTextAppearance, 0));
        }
        this.x = c2.getDimensionPixelSize(R$styleable.CollapsingToolbarLayout_scrimVisibleHeightTrigger, -1);
        this.w = (long) c2.getInt(R$styleable.CollapsingToolbarLayout_scrimAnimationDuration, 600);
        setContentScrim(c2.getDrawable(R$styleable.CollapsingToolbarLayout_contentScrim));
        setStatusBarScrim(c2.getDrawable(R$styleable.CollapsingToolbarLayout_statusBarScrim));
        this.f1384f = c2.getResourceId(R$styleable.CollapsingToolbarLayout_toolbarId, -1);
        c2.recycle();
        setWillNotDraw(false);
        v.a((View) this, (r) new a());
    }

    /* access modifiers changed from: protected */
    public c generateDefaultLayoutParams() {
        return new c(-1, -1);
    }

    public FrameLayout.LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new c(getContext(), attributeSet);
    }

    /* access modifiers changed from: protected */
    public FrameLayout.LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return new c(layoutParams);
    }

    private void c() {
        setContentDescription(getTitle());
    }

    public void a(boolean z2, boolean z3) {
        if (this.u != z2) {
            int i2 = 255;
            if (z3) {
                if (!z2) {
                    i2 = 0;
                }
                a(i2);
            } else {
                if (!z2) {
                    i2 = 0;
                }
                setScrimAlpha(i2);
            }
            this.u = z2;
        }
    }

    public static class c extends FrameLayout.LayoutParams {
        int a = 0;
        float b = 0.5f;

        public c(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.CollapsingToolbarLayout_Layout);
            this.a = obtainStyledAttributes.getInt(R$styleable.CollapsingToolbarLayout_Layout_layout_collapseMode, 0);
            a(obtainStyledAttributes.getFloat(R$styleable.CollapsingToolbarLayout_Layout_layout_collapseParallaxMultiplier, 0.5f));
            obtainStyledAttributes.recycle();
        }

        public void a(float f2) {
            this.b = f2;
        }

        public c(int i2, int i3) {
            super(i2, i3);
        }

        public c(ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
        }
    }

    private void a(int i2) {
        b();
        ValueAnimator valueAnimator = this.v;
        if (valueAnimator == null) {
            ValueAnimator valueAnimator2 = new ValueAnimator();
            this.v = valueAnimator2;
            valueAnimator2.setDuration(this.w);
            this.v.setInterpolator(i2 > this.t ? com.google.android.material.a.a.c : com.google.android.material.a.a.d);
            this.v.addUpdateListener(new b());
        } else if (valueAnimator.isRunning()) {
            this.v.cancel();
        }
        this.v.setIntValues(new int[]{this.t, i2});
        this.v.start();
    }

    static d d(View view) {
        d dVar = (d) view.getTag(R$id.view_offset_helper);
        if (dVar != null) {
            return dVar;
        }
        d dVar2 = new d(view);
        view.setTag(R$id.view_offset_helper, dVar2);
        return dVar2;
    }

    private View b(View view) {
        ViewParent parent = view.getParent();
        while (parent != this && parent != null) {
            if (parent instanceof View) {
                view = (View) parent;
            }
            parent = parent.getParent();
        }
        return view;
    }

    /* access modifiers changed from: package-private */
    public final void a() {
        if (this.r != null || this.s != null) {
            setScrimsShown(getHeight() + this.z < getScrimVisibleHeightTrigger());
        }
    }

    /* access modifiers changed from: package-private */
    public final int a(View view) {
        return ((getHeight() - d(view).a()) - view.getHeight()) - ((c) view.getLayoutParams()).bottomMargin;
    }
}
