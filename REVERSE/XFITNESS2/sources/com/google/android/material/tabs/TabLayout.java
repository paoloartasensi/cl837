package com.google.android.material.tabs;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.text.Layout;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.a;
import androidx.appcompat.widget.i0;
import androidx.core.h.t;
import androidx.core.h.v;
import androidx.viewpager.widget.ViewPager;
import com.google.android.material.R$attr;
import com.google.android.material.R$dimen;
import com.google.android.material.R$layout;
import com.google.android.material.R$style;
import com.google.android.material.R$styleable;
import com.google.android.material.internal.k;
import com.google.android.material.internal.l;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;

@ViewPager.e
public class TabLayout extends HorizontalScrollView {
    private static final androidx.core.g.e<g> S = new androidx.core.g.g(16);
    int A;
    int B;
    int C;
    int D;
    boolean E;
    boolean F;
    boolean G;
    private c H;
    private final ArrayList<c> I;
    private c J;
    private ValueAnimator K;
    ViewPager L;
    private androidx.viewpager.widget.a M;
    private DataSetObserver N;
    private h O;
    private b P;
    private boolean Q;
    private final androidx.core.g.e<i> R;
    private final ArrayList<g> e;

    /* renamed from: f  reason: collision with root package name */
    private g f1538f;
    /* access modifiers changed from: private */

    /* renamed from: g  reason: collision with root package name */
    public final RectF f1539g;

    /* renamed from: h  reason: collision with root package name */
    private final f f1540h;

    /* renamed from: i  reason: collision with root package name */
    int f1541i;

    /* renamed from: j  reason: collision with root package name */
    int f1542j;
    int k;
    int l;
    int m;
    ColorStateList n;
    ColorStateList o;
    ColorStateList p;
    Drawable q;
    PorterDuff.Mode r;
    float s;
    float t;
    final int u;
    int v;
    private final int w;
    private final int x;
    private final int y;
    private int z;

    class a implements ValueAnimator.AnimatorUpdateListener {
        a() {
        }

        public void onAnimationUpdate(ValueAnimator valueAnimator) {
            TabLayout.this.scrollTo(((Integer) valueAnimator.getAnimatedValue()).intValue(), 0);
        }
    }

    public interface c<T extends g> {
        void a(T t);

        void b(T t);

        void c(T t);
    }

    public interface d extends c<g> {
    }

    private class e extends DataSetObserver {
        e() {
        }

        public void onChanged() {
            TabLayout.this.c();
        }

        public void onInvalidated() {
            TabLayout.this.c();
        }
    }

    public static class g {
        private Drawable a;
        /* access modifiers changed from: private */
        public CharSequence b;
        /* access modifiers changed from: private */
        public CharSequence c;
        private int d = -1;
        private View e;

        /* renamed from: f  reason: collision with root package name */
        public TabLayout f1548f;

        /* renamed from: g  reason: collision with root package name */
        public i f1549g;

        public int c() {
            return this.d;
        }

        public CharSequence d() {
            return this.b;
        }

        public boolean e() {
            TabLayout tabLayout = this.f1548f;
            if (tabLayout != null) {
                return tabLayout.getSelectedTabPosition() == this.d;
            }
            throw new IllegalArgumentException("Tab not attached to a TabLayout");
        }

        /* access modifiers changed from: package-private */
        public void f() {
            this.f1548f = null;
            this.f1549g = null;
            this.a = null;
            this.b = null;
            this.c = null;
            this.d = -1;
            this.e = null;
        }

        public void g() {
            TabLayout tabLayout = this.f1548f;
            if (tabLayout != null) {
                tabLayout.c(this);
                return;
            }
            throw new IllegalArgumentException("Tab not attached to a TabLayout");
        }

        /* access modifiers changed from: package-private */
        public void h() {
            i iVar = this.f1549g;
            if (iVar != null) {
                iVar.b();
            }
        }

        public View a() {
            return this.e;
        }

        public Drawable b() {
            return this.a;
        }

        public g a(View view) {
            this.e = view;
            h();
            return this;
        }

        /* access modifiers changed from: package-private */
        public void b(int i2) {
            this.d = i2;
        }

        public g b(CharSequence charSequence) {
            if (TextUtils.isEmpty(this.c) && !TextUtils.isEmpty(charSequence)) {
                this.f1549g.setContentDescription(charSequence);
            }
            this.b = charSequence;
            h();
            return this;
        }

        public g a(int i2) {
            a(LayoutInflater.from(this.f1549g.getContext()).inflate(i2, this.f1549g, false));
            return this;
        }

        public g a(Drawable drawable) {
            this.a = drawable;
            h();
            return this;
        }

        public g a(CharSequence charSequence) {
            this.c = charSequence;
            h();
            return this;
        }
    }

    public static class j implements d {
        private final ViewPager e;

        public j(ViewPager viewPager) {
            this.e = viewPager;
        }

        public void a(g gVar) {
        }

        public void b(g gVar) {
            this.e.setCurrentItem(gVar.c());
        }

        public void c(g gVar) {
        }
    }

    public TabLayout(Context context) {
        this(context, (AttributeSet) null);
    }

    private i e(g gVar) {
        androidx.core.g.e<i> eVar = this.R;
        i a2 = eVar != null ? eVar.a() : null;
        if (a2 == null) {
            a2 = new i(getContext());
        }
        a2.a(gVar);
        a2.setFocusable(true);
        a2.setMinimumWidth(getTabMinWidth());
        if (TextUtils.isEmpty(gVar.c)) {
            a2.setContentDescription(gVar.b);
        } else {
            a2.setContentDescription(gVar.c);
        }
        return a2;
    }

    private LinearLayout.LayoutParams f() {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-2, -1);
        a(layoutParams);
        return layoutParams;
    }

    private void g() {
        if (this.K == null) {
            ValueAnimator valueAnimator = new ValueAnimator();
            this.K = valueAnimator;
            valueAnimator.setInterpolator(com.google.android.material.a.a.b);
            this.K.setDuration((long) this.B);
            this.K.addUpdateListener(new a());
        }
    }

    private int getDefaultHeight() {
        int size = this.e.size();
        boolean z2 = false;
        int i2 = 0;
        while (true) {
            if (i2 < size) {
                g gVar = this.e.get(i2);
                if (gVar != null && gVar.b() != null && !TextUtils.isEmpty(gVar.d())) {
                    z2 = true;
                    break;
                }
                i2++;
            } else {
                break;
            }
        }
        return (!z2 || this.E) ? 48 : 72;
    }

    private int getTabMinWidth() {
        int i2 = this.w;
        if (i2 != -1) {
            return i2;
        }
        if (this.D == 0) {
            return this.y;
        }
        return 0;
    }

    private int getTabScrollRange() {
        return Math.max(0, ((this.f1540h.getWidth() - getWidth()) - getPaddingLeft()) - getPaddingRight());
    }

    private void h() {
        int size = this.e.size();
        for (int i2 = 0; i2 < size; i2++) {
            this.e.get(i2).h();
        }
    }

    private void setSelectedTabView(int i2) {
        int childCount = this.f1540h.getChildCount();
        if (i2 < childCount) {
            int i3 = 0;
            while (i3 < childCount) {
                View childAt = this.f1540h.getChildAt(i3);
                boolean z2 = true;
                childAt.setSelected(i3 == i2);
                if (i3 != i2) {
                    z2 = false;
                }
                childAt.setActivated(z2);
                i3++;
            }
        }
    }

    public void addOnTabSelectedListener(c cVar) {
        if (!this.I.contains(cVar)) {
            this.I.add(cVar);
        }
    }

    public void addView(View view) {
        a(view);
    }

    public g b() {
        g a2 = a();
        a2.f1548f = this;
        a2.f1549g = e(a2);
        return a2;
    }

    /* access modifiers changed from: package-private */
    public void c() {
        int currentItem;
        d();
        androidx.viewpager.widget.a aVar = this.M;
        if (aVar != null) {
            int a2 = aVar.a();
            for (int i2 = 0; i2 < a2; i2++) {
                g b2 = b();
                b2.b(this.M.a(i2));
                a(b2, false);
            }
            ViewPager viewPager = this.L;
            if (viewPager != null && a2 > 0 && (currentItem = viewPager.getCurrentItem()) != getSelectedTabPosition() && currentItem < getTabCount()) {
                c(b(currentItem));
            }
        }
    }

    public void d() {
        for (int childCount = this.f1540h.getChildCount() - 1; childCount >= 0; childCount--) {
            d(childCount);
        }
        Iterator<g> it = this.e.iterator();
        while (it.hasNext()) {
            g next = it.next();
            it.remove();
            next.f();
            b(next);
        }
        this.f1538f = null;
    }

    public int getSelectedTabPosition() {
        g gVar = this.f1538f;
        if (gVar != null) {
            return gVar.c();
        }
        return -1;
    }

    public int getTabCount() {
        return this.e.size();
    }

    public int getTabGravity() {
        return this.A;
    }

    public ColorStateList getTabIconTint() {
        return this.o;
    }

    public int getTabIndicatorGravity() {
        return this.C;
    }

    /* access modifiers changed from: package-private */
    public int getTabMaxWidth() {
        return this.v;
    }

    public int getTabMode() {
        return this.D;
    }

    public ColorStateList getTabRippleColor() {
        return this.p;
    }

    public Drawable getTabSelectedIndicator() {
        return this.q;
    }

    public ColorStateList getTabTextColors() {
        return this.n;
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (this.L == null) {
            ViewParent parent = getParent();
            if (parent instanceof ViewPager) {
                a((ViewPager) parent, true, true);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (this.Q) {
            setupWithViewPager((ViewPager) null);
            this.Q = false;
        }
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        for (int i2 = 0; i2 < this.f1540h.getChildCount(); i2++) {
            View childAt = this.f1540h.getChildAt(i2);
            if (childAt instanceof i) {
                ((i) childAt).a(canvas);
            }
        }
        super.onDraw(canvas);
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0067, code lost:
        if (r1.getMeasuredWidth() != getMeasuredWidth()) goto L_0x0076;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0072, code lost:
        if (r1.getMeasuredWidth() < getMeasuredWidth()) goto L_0x0076;
     */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x0079  */
    /* JADX WARNING: Removed duplicated region for block: B:26:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onMeasure(int r6, int r7) {
        /*
            r5 = this;
            int r0 = r5.getDefaultHeight()
            int r0 = r5.a((int) r0)
            int r1 = r5.getPaddingTop()
            int r0 = r0 + r1
            int r1 = r5.getPaddingBottom()
            int r0 = r0 + r1
            int r1 = android.view.View.MeasureSpec.getMode(r7)
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = 1073741824(0x40000000, float:2.0)
            if (r1 == r2) goto L_0x0024
            if (r1 == 0) goto L_0x001f
            goto L_0x0030
        L_0x001f:
            int r7 = android.view.View.MeasureSpec.makeMeasureSpec(r0, r3)
            goto L_0x0030
        L_0x0024:
            int r7 = android.view.View.MeasureSpec.getSize(r7)
            int r7 = java.lang.Math.min(r0, r7)
            int r7 = android.view.View.MeasureSpec.makeMeasureSpec(r7, r3)
        L_0x0030:
            int r0 = android.view.View.MeasureSpec.getSize(r6)
            int r1 = android.view.View.MeasureSpec.getMode(r6)
            if (r1 == 0) goto L_0x0049
            int r1 = r5.x
            if (r1 <= 0) goto L_0x003f
            goto L_0x0047
        L_0x003f:
            r1 = 56
            int r1 = r5.a((int) r1)
            int r1 = r0 - r1
        L_0x0047:
            r5.v = r1
        L_0x0049:
            super.onMeasure(r6, r7)
            int r6 = r5.getChildCount()
            r0 = 1
            if (r6 != r0) goto L_0x0097
            r6 = 0
            android.view.View r1 = r5.getChildAt(r6)
            int r2 = r5.D
            if (r2 == 0) goto L_0x006a
            if (r2 == r0) goto L_0x005f
            goto L_0x0077
        L_0x005f:
            int r2 = r1.getMeasuredWidth()
            int r4 = r5.getMeasuredWidth()
            if (r2 == r4) goto L_0x0075
            goto L_0x0076
        L_0x006a:
            int r2 = r1.getMeasuredWidth()
            int r4 = r5.getMeasuredWidth()
            if (r2 >= r4) goto L_0x0075
            goto L_0x0076
        L_0x0075:
            r0 = 0
        L_0x0076:
            r6 = r0
        L_0x0077:
            if (r6 == 0) goto L_0x0097
            int r6 = r5.getPaddingTop()
            int r0 = r5.getPaddingBottom()
            int r6 = r6 + r0
            android.view.ViewGroup$LayoutParams r0 = r1.getLayoutParams()
            int r0 = r0.height
            int r6 = android.widget.HorizontalScrollView.getChildMeasureSpec(r7, r6, r0)
            int r7 = r5.getMeasuredWidth()
            int r7 = android.view.View.MeasureSpec.makeMeasureSpec(r7, r3)
            r1.measure(r7, r6)
        L_0x0097:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.material.tabs.TabLayout.onMeasure(int, int):void");
    }

    public void removeOnTabSelectedListener(c cVar) {
        this.I.remove(cVar);
    }

    public void setInlineLabel(boolean z2) {
        if (this.E != z2) {
            this.E = z2;
            for (int i2 = 0; i2 < this.f1540h.getChildCount(); i2++) {
                View childAt = this.f1540h.getChildAt(i2);
                if (childAt instanceof i) {
                    ((i) childAt).c();
                }
            }
            e();
        }
    }

    public void setInlineLabelResource(int i2) {
        setInlineLabel(getResources().getBoolean(i2));
    }

    @Deprecated
    public void setOnTabSelectedListener(c cVar) {
        c cVar2 = this.H;
        if (cVar2 != null) {
            removeOnTabSelectedListener(cVar2);
        }
        this.H = cVar;
        if (cVar != null) {
            addOnTabSelectedListener(cVar);
        }
    }

    /* access modifiers changed from: package-private */
    public void setScrollAnimatorListener(Animator.AnimatorListener animatorListener) {
        g();
        this.K.addListener(animatorListener);
    }

    public void setSelectedTabIndicator(Drawable drawable) {
        if (this.q != drawable) {
            this.q = drawable;
            v.H(this.f1540h);
        }
    }

    public void setSelectedTabIndicatorColor(int i2) {
        this.f1540h.a(i2);
    }

    public void setSelectedTabIndicatorGravity(int i2) {
        if (this.C != i2) {
            this.C = i2;
            v.H(this.f1540h);
        }
    }

    @Deprecated
    public void setSelectedTabIndicatorHeight(int i2) {
        this.f1540h.b(i2);
    }

    public void setTabGravity(int i2) {
        if (this.A != i2) {
            this.A = i2;
            e();
        }
    }

    public void setTabIconTint(ColorStateList colorStateList) {
        if (this.o != colorStateList) {
            this.o = colorStateList;
            h();
        }
    }

    public void setTabIconTintResource(int i2) {
        setTabIconTint(androidx.appcompat.a.a.a.b(getContext(), i2));
    }

    public void setTabIndicatorFullWidth(boolean z2) {
        this.F = z2;
        v.H(this.f1540h);
    }

    public void setTabMode(int i2) {
        if (i2 != this.D) {
            this.D = i2;
            e();
        }
    }

    public void setTabRippleColor(ColorStateList colorStateList) {
        if (this.p != colorStateList) {
            this.p = colorStateList;
            for (int i2 = 0; i2 < this.f1540h.getChildCount(); i2++) {
                View childAt = this.f1540h.getChildAt(i2);
                if (childAt instanceof i) {
                    ((i) childAt).a(getContext());
                }
            }
        }
    }

    public void setTabRippleColorResource(int i2) {
        setTabRippleColor(androidx.appcompat.a.a.a.b(getContext(), i2));
    }

    public void setTabTextColors(ColorStateList colorStateList) {
        if (this.n != colorStateList) {
            this.n = colorStateList;
            h();
        }
    }

    @Deprecated
    public void setTabsFromPagerAdapter(androidx.viewpager.widget.a aVar) {
        a(aVar, false);
    }

    public void setUnboundedRipple(boolean z2) {
        if (this.G != z2) {
            this.G = z2;
            for (int i2 = 0; i2 < this.f1540h.getChildCount(); i2++) {
                View childAt = this.f1540h.getChildAt(i2);
                if (childAt instanceof i) {
                    ((i) childAt).a(getContext());
                }
            }
        }
    }

    public void setUnboundedRippleResource(int i2) {
        setUnboundedRipple(getResources().getBoolean(i2));
    }

    public void setupWithViewPager(ViewPager viewPager) {
        a(viewPager, true);
    }

    public boolean shouldDelayChildPressedState() {
        return getTabScrollRange() > 0;
    }

    private class b implements ViewPager.i {
        private boolean a;

        b() {
        }

        public void a(ViewPager viewPager, androidx.viewpager.widget.a aVar, androidx.viewpager.widget.a aVar2) {
            TabLayout tabLayout = TabLayout.this;
            if (tabLayout.L == viewPager) {
                tabLayout.a(aVar2, this.a);
            }
        }

        /* access modifiers changed from: package-private */
        public void a(boolean z) {
            this.a = z;
        }
    }

    public static class h implements ViewPager.j {
        private final WeakReference<TabLayout> a;
        private int b;
        private int c;

        public h(TabLayout tabLayout) {
            this.a = new WeakReference<>(tabLayout);
        }

        public void a(int i2) {
            this.b = this.c;
            this.c = i2;
        }

        public void b(int i2) {
            TabLayout tabLayout = (TabLayout) this.a.get();
            if (tabLayout != null && tabLayout.getSelectedTabPosition() != i2 && i2 < tabLayout.getTabCount()) {
                int i3 = this.c;
                tabLayout.b(tabLayout.b(i2), i3 == 0 || (i3 == 2 && this.b == 0));
            }
        }

        public void a(int i2, float f2, int i3) {
            TabLayout tabLayout = (TabLayout) this.a.get();
            if (tabLayout != null) {
                boolean z = false;
                boolean z2 = this.c != 2 || this.b == 1;
                if (!(this.c == 2 && this.b == 0)) {
                    z = true;
                }
                tabLayout.a(i2, f2, z2, z);
            }
        }

        /* access modifiers changed from: package-private */
        public void a() {
            this.c = 0;
            this.b = 0;
        }
    }

    public TabLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R$attr.tabStyle);
    }

    public void a(int i2, float f2, boolean z2) {
        a(i2, f2, z2, true);
    }

    public void addView(View view, int i2) {
        a(view);
    }

    public FrameLayout.LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return generateDefaultLayoutParams();
    }

    private class f extends LinearLayout {
        private int e;

        /* renamed from: f  reason: collision with root package name */
        private final Paint f1543f;

        /* renamed from: g  reason: collision with root package name */
        private final GradientDrawable f1544g;

        /* renamed from: h  reason: collision with root package name */
        int f1545h = -1;

        /* renamed from: i  reason: collision with root package name */
        float f1546i;

        /* renamed from: j  reason: collision with root package name */
        private int f1547j = -1;
        private int k = -1;
        private int l = -1;
        private ValueAnimator m;

        class a implements ValueAnimator.AnimatorUpdateListener {
            final /* synthetic */ int a;
            final /* synthetic */ int b;
            final /* synthetic */ int c;
            final /* synthetic */ int d;

            a(int i2, int i3, int i4, int i5) {
                this.a = i2;
                this.b = i3;
                this.c = i4;
                this.d = i5;
            }

            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float animatedFraction = valueAnimator.getAnimatedFraction();
                f.this.b(com.google.android.material.a.a.a(this.a, this.b, animatedFraction), com.google.android.material.a.a.a(this.c, this.d, animatedFraction));
            }
        }

        class b extends AnimatorListenerAdapter {
            final /* synthetic */ int a;

            b(int i2) {
                this.a = i2;
            }

            public void onAnimationEnd(Animator animator) {
                f fVar = f.this;
                fVar.f1545h = this.a;
                fVar.f1546i = 0.0f;
            }
        }

        f(Context context) {
            super(context);
            setWillNotDraw(false);
            this.f1543f = new Paint();
            this.f1544g = new GradientDrawable();
        }

        /* access modifiers changed from: package-private */
        public void a(int i2) {
            if (this.f1543f.getColor() != i2) {
                this.f1543f.setColor(i2);
                v.H(this);
            }
        }

        /* access modifiers changed from: package-private */
        public void b(int i2) {
            if (this.e != i2) {
                this.e = i2;
                v.H(this);
            }
        }

        public void draw(Canvas canvas) {
            Drawable drawable = TabLayout.this.q;
            int i2 = 0;
            int intrinsicHeight = drawable != null ? drawable.getIntrinsicHeight() : 0;
            int i3 = this.e;
            if (i3 >= 0) {
                intrinsicHeight = i3;
            }
            int i4 = TabLayout.this.C;
            if (i4 == 0) {
                i2 = getHeight() - intrinsicHeight;
                intrinsicHeight = getHeight();
            } else if (i4 == 1) {
                i2 = (getHeight() - intrinsicHeight) / 2;
                intrinsicHeight = (getHeight() + intrinsicHeight) / 2;
            } else if (i4 != 2) {
                if (i4 != 3) {
                    intrinsicHeight = 0;
                } else {
                    intrinsicHeight = getHeight();
                }
            }
            int i5 = this.k;
            if (i5 >= 0 && this.l > i5) {
                Drawable drawable2 = TabLayout.this.q;
                if (drawable2 == null) {
                    drawable2 = this.f1544g;
                }
                Drawable i6 = androidx.core.graphics.drawable.a.i(drawable2);
                i6.setBounds(this.k, i2, this.l, intrinsicHeight);
                Paint paint = this.f1543f;
                if (paint != null) {
                    if (Build.VERSION.SDK_INT == 21) {
                        i6.setColorFilter(paint.getColor(), PorterDuff.Mode.SRC_IN);
                    } else {
                        androidx.core.graphics.drawable.a.b(i6, paint.getColor());
                    }
                }
                i6.draw(canvas);
            }
            super.draw(canvas);
        }

        /* access modifiers changed from: protected */
        public void onLayout(boolean z, int i2, int i3, int i4, int i5) {
            super.onLayout(z, i2, i3, i4, i5);
            ValueAnimator valueAnimator = this.m;
            if (valueAnimator == null || !valueAnimator.isRunning()) {
                b();
                return;
            }
            this.m.cancel();
            a(this.f1545h, Math.round((1.0f - this.m.getAnimatedFraction()) * ((float) this.m.getDuration())));
        }

        /* access modifiers changed from: protected */
        public void onMeasure(int i2, int i3) {
            super.onMeasure(i2, i3);
            if (View.MeasureSpec.getMode(i2) == 1073741824) {
                TabLayout tabLayout = TabLayout.this;
                boolean z = true;
                if (tabLayout.D == 1 && tabLayout.A == 1) {
                    int childCount = getChildCount();
                    int i4 = 0;
                    for (int i5 = 0; i5 < childCount; i5++) {
                        View childAt = getChildAt(i5);
                        if (childAt.getVisibility() == 0) {
                            i4 = Math.max(i4, childAt.getMeasuredWidth());
                        }
                    }
                    if (i4 > 0) {
                        if (i4 * childCount <= getMeasuredWidth() - (TabLayout.this.a(16) * 2)) {
                            boolean z2 = false;
                            for (int i6 = 0; i6 < childCount; i6++) {
                                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) getChildAt(i6).getLayoutParams();
                                if (layoutParams.width != i4 || layoutParams.weight != 0.0f) {
                                    layoutParams.width = i4;
                                    layoutParams.weight = 0.0f;
                                    z2 = true;
                                }
                            }
                            z = z2;
                        } else {
                            TabLayout tabLayout2 = TabLayout.this;
                            tabLayout2.A = 0;
                            tabLayout2.a(false);
                        }
                        if (z) {
                            super.onMeasure(i2, i3);
                        }
                    }
                }
            }
        }

        public void onRtlPropertiesChanged(int i2) {
            super.onRtlPropertiesChanged(i2);
            if (Build.VERSION.SDK_INT < 23 && this.f1547j != i2) {
                requestLayout();
                this.f1547j = i2;
            }
        }

        private void b() {
            int i2;
            int i3;
            View childAt = getChildAt(this.f1545h);
            if (childAt == null || childAt.getWidth() <= 0) {
                i3 = -1;
                i2 = -1;
            } else {
                i3 = childAt.getLeft();
                i2 = childAt.getRight();
                TabLayout tabLayout = TabLayout.this;
                if (!tabLayout.F && (childAt instanceof i)) {
                    a((i) childAt, tabLayout.f1539g);
                    i3 = (int) TabLayout.this.f1539g.left;
                    i2 = (int) TabLayout.this.f1539g.right;
                }
                if (this.f1546i > 0.0f && this.f1545h < getChildCount() - 1) {
                    View childAt2 = getChildAt(this.f1545h + 1);
                    int left = childAt2.getLeft();
                    int right = childAt2.getRight();
                    TabLayout tabLayout2 = TabLayout.this;
                    if (!tabLayout2.F && (childAt2 instanceof i)) {
                        a((i) childAt2, tabLayout2.f1539g);
                        left = (int) TabLayout.this.f1539g.left;
                        right = (int) TabLayout.this.f1539g.right;
                    }
                    float f2 = this.f1546i;
                    i3 = (int) ((((float) left) * f2) + ((1.0f - f2) * ((float) i3)));
                    i2 = (int) ((((float) right) * f2) + ((1.0f - f2) * ((float) i2)));
                }
            }
            b(i3, i2);
        }

        /* access modifiers changed from: package-private */
        public boolean a() {
            int childCount = getChildCount();
            for (int i2 = 0; i2 < childCount; i2++) {
                if (getChildAt(i2).getWidth() <= 0) {
                    return true;
                }
            }
            return false;
        }

        /* access modifiers changed from: package-private */
        public void a(int i2, float f2) {
            ValueAnimator valueAnimator = this.m;
            if (valueAnimator != null && valueAnimator.isRunning()) {
                this.m.cancel();
            }
            this.f1545h = i2;
            this.f1546i = f2;
            b();
        }

        /* access modifiers changed from: package-private */
        public void a(int i2, int i3) {
            ValueAnimator valueAnimator = this.m;
            if (valueAnimator != null && valueAnimator.isRunning()) {
                this.m.cancel();
            }
            View childAt = getChildAt(i2);
            if (childAt == null) {
                b();
                return;
            }
            int left = childAt.getLeft();
            int right = childAt.getRight();
            TabLayout tabLayout = TabLayout.this;
            if (!tabLayout.F && (childAt instanceof i)) {
                a((i) childAt, tabLayout.f1539g);
                left = (int) TabLayout.this.f1539g.left;
                right = (int) TabLayout.this.f1539g.right;
            }
            int i4 = left;
            int i5 = right;
            int i6 = this.k;
            int i7 = this.l;
            if (i6 != i4 || i7 != i5) {
                ValueAnimator valueAnimator2 = new ValueAnimator();
                this.m = valueAnimator2;
                valueAnimator2.setInterpolator(com.google.android.material.a.a.b);
                valueAnimator2.setDuration((long) i3);
                valueAnimator2.setFloatValues(new float[]{0.0f, 1.0f});
                valueAnimator2.addUpdateListener(new a(i6, i4, i7, i5));
                valueAnimator2.addListener(new b(i2));
                valueAnimator2.start();
            }
        }

        /* access modifiers changed from: package-private */
        public void b(int i2, int i3) {
            if (i2 != this.k || i3 != this.l) {
                this.k = i2;
                this.l = i3;
                v.H(this);
            }
        }

        private void a(i iVar, RectF rectF) {
            int a2 = iVar.d();
            if (a2 < TabLayout.this.a(24)) {
                a2 = TabLayout.this.a(24);
            }
            int left = (iVar.getLeft() + iVar.getRight()) / 2;
            int i2 = a2 / 2;
            rectF.set((float) (left - i2), 0.0f, (float) (left + i2), 0.0f);
        }
    }

    class i extends LinearLayout {
        private g e;

        /* renamed from: f  reason: collision with root package name */
        private TextView f1550f;

        /* renamed from: g  reason: collision with root package name */
        private ImageView f1551g;

        /* renamed from: h  reason: collision with root package name */
        private View f1552h;

        /* renamed from: i  reason: collision with root package name */
        private TextView f1553i;

        /* renamed from: j  reason: collision with root package name */
        private ImageView f1554j;
        private Drawable k;
        private int l = 2;

        public i(Context context) {
            super(context);
            a(context);
            v.b(this, TabLayout.this.f1541i, TabLayout.this.f1542j, TabLayout.this.k, TabLayout.this.l);
            setGravity(17);
            setOrientation(TabLayout.this.E ^ true ? 1 : 0);
            setClickable(true);
            v.a((View) this, t.a(getContext(), 1002));
        }

        /* access modifiers changed from: private */
        public int d() {
            View[] viewArr = {this.f1550f, this.f1551g, this.f1552h};
            int i2 = 0;
            int i3 = 0;
            boolean z = false;
            for (int i4 = 0; i4 < 3; i4++) {
                View view = viewArr[i4];
                if (view != null && view.getVisibility() == 0) {
                    i3 = z ? Math.min(i3, view.getLeft()) : view.getLeft();
                    i2 = z ? Math.max(i2, view.getRight()) : view.getRight();
                    z = true;
                }
            }
            return i2 - i3;
        }

        /* access modifiers changed from: package-private */
        public final void b() {
            g gVar = this.e;
            Drawable drawable = null;
            View a = gVar != null ? gVar.a() : null;
            if (a != null) {
                ViewParent parent = a.getParent();
                if (parent != this) {
                    if (parent != null) {
                        ((ViewGroup) parent).removeView(a);
                    }
                    addView(a);
                }
                this.f1552h = a;
                TextView textView = this.f1550f;
                if (textView != null) {
                    textView.setVisibility(8);
                }
                ImageView imageView = this.f1551g;
                if (imageView != null) {
                    imageView.setVisibility(8);
                    this.f1551g.setImageDrawable((Drawable) null);
                }
                TextView textView2 = (TextView) a.findViewById(16908308);
                this.f1553i = textView2;
                if (textView2 != null) {
                    this.l = androidx.core.widget.i.d(textView2);
                }
                this.f1554j = (ImageView) a.findViewById(16908294);
            } else {
                View view = this.f1552h;
                if (view != null) {
                    removeView(view);
                    this.f1552h = null;
                }
                this.f1553i = null;
                this.f1554j = null;
            }
            boolean z = false;
            if (this.f1552h == null) {
                if (this.f1551g == null) {
                    ImageView imageView2 = (ImageView) LayoutInflater.from(getContext()).inflate(R$layout.design_layout_tab_icon, this, false);
                    addView(imageView2, 0);
                    this.f1551g = imageView2;
                }
                if (!(gVar == null || gVar.b() == null)) {
                    drawable = androidx.core.graphics.drawable.a.i(gVar.b()).mutate();
                }
                if (drawable != null) {
                    androidx.core.graphics.drawable.a.a(drawable, TabLayout.this.o);
                    PorterDuff.Mode mode = TabLayout.this.r;
                    if (mode != null) {
                        androidx.core.graphics.drawable.a.a(drawable, mode);
                    }
                }
                if (this.f1550f == null) {
                    TextView textView3 = (TextView) LayoutInflater.from(getContext()).inflate(R$layout.design_layout_tab_text, this, false);
                    addView(textView3);
                    this.f1550f = textView3;
                    this.l = androidx.core.widget.i.d(textView3);
                }
                androidx.core.widget.i.d(this.f1550f, TabLayout.this.m);
                ColorStateList colorStateList = TabLayout.this.n;
                if (colorStateList != null) {
                    this.f1550f.setTextColor(colorStateList);
                }
                a(this.f1550f, this.f1551g);
            } else if (!(this.f1553i == null && this.f1554j == null)) {
                a(this.f1553i, this.f1554j);
            }
            if (gVar != null && !TextUtils.isEmpty(gVar.c)) {
                setContentDescription(gVar.c);
            }
            if (gVar != null && gVar.e()) {
                z = true;
            }
            setSelected(z);
        }

        /* access modifiers changed from: package-private */
        public final void c() {
            setOrientation(TabLayout.this.E ^ true ? 1 : 0);
            if (this.f1553i == null && this.f1554j == null) {
                a(this.f1550f, this.f1551g);
            } else {
                a(this.f1553i, this.f1554j);
            }
        }

        /* access modifiers changed from: protected */
        public void drawableStateChanged() {
            super.drawableStateChanged();
            int[] drawableState = getDrawableState();
            Drawable drawable = this.k;
            boolean z = false;
            if (drawable != null && drawable.isStateful()) {
                z = false | this.k.setState(drawableState);
            }
            if (z) {
                invalidate();
                TabLayout.this.invalidate();
            }
        }

        public void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
            super.onInitializeAccessibilityEvent(accessibilityEvent);
            accessibilityEvent.setClassName(a.c.class.getName());
        }

        @TargetApi(14)
        public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
            super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
            accessibilityNodeInfo.setClassName(a.c.class.getName());
        }

        public void onMeasure(int i2, int i3) {
            Layout layout;
            int size = View.MeasureSpec.getSize(i2);
            int mode = View.MeasureSpec.getMode(i2);
            int tabMaxWidth = TabLayout.this.getTabMaxWidth();
            if (tabMaxWidth > 0 && (mode == 0 || size > tabMaxWidth)) {
                i2 = View.MeasureSpec.makeMeasureSpec(TabLayout.this.v, Integer.MIN_VALUE);
            }
            super.onMeasure(i2, i3);
            if (this.f1550f != null) {
                float f2 = TabLayout.this.s;
                int i4 = this.l;
                ImageView imageView = this.f1551g;
                boolean z = true;
                if (imageView == null || imageView.getVisibility() != 0) {
                    TextView textView = this.f1550f;
                    if (textView != null && textView.getLineCount() > 1) {
                        f2 = TabLayout.this.t;
                    }
                } else {
                    i4 = 1;
                }
                float textSize = this.f1550f.getTextSize();
                int lineCount = this.f1550f.getLineCount();
                int d = androidx.core.widget.i.d(this.f1550f);
                if (f2 != textSize || (d >= 0 && i4 != d)) {
                    if (TabLayout.this.D == 1 && f2 > textSize && lineCount == 1 && ((layout = this.f1550f.getLayout()) == null || a(layout, 0, f2) > ((float) ((getMeasuredWidth() - getPaddingLeft()) - getPaddingRight())))) {
                        z = false;
                    }
                    if (z) {
                        this.f1550f.setTextSize(0, f2);
                        this.f1550f.setMaxLines(i4);
                        super.onMeasure(i2, i3);
                    }
                }
            }
        }

        public boolean performClick() {
            boolean performClick = super.performClick();
            if (this.e == null) {
                return performClick;
            }
            if (!performClick) {
                playSoundEffect(0);
            }
            this.e.g();
            return true;
        }

        public void setSelected(boolean z) {
            boolean z2 = isSelected() != z;
            super.setSelected(z);
            if (z2 && z && Build.VERSION.SDK_INT < 16) {
                sendAccessibilityEvent(4);
            }
            TextView textView = this.f1550f;
            if (textView != null) {
                textView.setSelected(z);
            }
            ImageView imageView = this.f1551g;
            if (imageView != null) {
                imageView.setSelected(z);
            }
            View view = this.f1552h;
            if (view != null) {
                view.setSelected(z);
            }
        }

        /* JADX WARNING: type inference failed for: r2v3, types: [android.graphics.drawable.LayerDrawable] */
        /* JADX WARNING: type inference failed for: r0v3, types: [android.graphics.drawable.RippleDrawable] */
        /* access modifiers changed from: private */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void a(android.content.Context r7) {
            /*
                r6 = this;
                com.google.android.material.tabs.TabLayout r0 = com.google.android.material.tabs.TabLayout.this
                int r0 = r0.u
                r1 = 0
                if (r0 == 0) goto L_0x001f
                android.graphics.drawable.Drawable r7 = androidx.appcompat.a.a.a.c(r7, r0)
                r6.k = r7
                if (r7 == 0) goto L_0x0021
                boolean r7 = r7.isStateful()
                if (r7 == 0) goto L_0x0021
                android.graphics.drawable.Drawable r7 = r6.k
                int[] r0 = r6.getDrawableState()
                r7.setState(r0)
                goto L_0x0021
            L_0x001f:
                r6.k = r1
            L_0x0021:
                android.graphics.drawable.GradientDrawable r7 = new android.graphics.drawable.GradientDrawable
                r7.<init>()
                r0 = 0
                r7.setColor(r0)
                com.google.android.material.tabs.TabLayout r2 = com.google.android.material.tabs.TabLayout.this
                android.content.res.ColorStateList r2 = r2.p
                if (r2 == 0) goto L_0x0078
                android.graphics.drawable.GradientDrawable r2 = new android.graphics.drawable.GradientDrawable
                r2.<init>()
                r3 = 925353388(0x3727c5ac, float:1.0E-5)
                r2.setCornerRadius(r3)
                r3 = -1
                r2.setColor(r3)
                com.google.android.material.tabs.TabLayout r3 = com.google.android.material.tabs.TabLayout.this
                android.content.res.ColorStateList r3 = r3.p
                android.content.res.ColorStateList r3 = com.google.android.material.g.a.a((android.content.res.ColorStateList) r3)
                int r4 = android.os.Build.VERSION.SDK_INT
                r5 = 21
                if (r4 < r5) goto L_0x0063
                android.graphics.drawable.RippleDrawable r0 = new android.graphics.drawable.RippleDrawable
                com.google.android.material.tabs.TabLayout r4 = com.google.android.material.tabs.TabLayout.this
                boolean r4 = r4.G
                if (r4 == 0) goto L_0x0056
                r7 = r1
            L_0x0056:
                com.google.android.material.tabs.TabLayout r4 = com.google.android.material.tabs.TabLayout.this
                boolean r4 = r4.G
                if (r4 == 0) goto L_0x005d
                goto L_0x005e
            L_0x005d:
                r1 = r2
            L_0x005e:
                r0.<init>(r3, r7, r1)
                r7 = r0
                goto L_0x0078
            L_0x0063:
                android.graphics.drawable.Drawable r1 = androidx.core.graphics.drawable.a.i(r2)
                androidx.core.graphics.drawable.a.a((android.graphics.drawable.Drawable) r1, (android.content.res.ColorStateList) r3)
                android.graphics.drawable.LayerDrawable r2 = new android.graphics.drawable.LayerDrawable
                r3 = 2
                android.graphics.drawable.Drawable[] r3 = new android.graphics.drawable.Drawable[r3]
                r3[r0] = r7
                r7 = 1
                r3[r7] = r1
                r2.<init>(r3)
                r7 = r2
            L_0x0078:
                androidx.core.h.v.a((android.view.View) r6, (android.graphics.drawable.Drawable) r7)
                com.google.android.material.tabs.TabLayout r7 = com.google.android.material.tabs.TabLayout.this
                r7.invalidate()
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.material.tabs.TabLayout.i.a(android.content.Context):void");
        }

        /* access modifiers changed from: private */
        public void a(Canvas canvas) {
            Drawable drawable = this.k;
            if (drawable != null) {
                drawable.setBounds(getLeft(), getTop(), getRight(), getBottom());
                this.k.draw(canvas);
            }
        }

        /* access modifiers changed from: package-private */
        public void a(g gVar) {
            if (gVar != this.e) {
                this.e = gVar;
                b();
            }
        }

        /* access modifiers changed from: package-private */
        public void a() {
            a((g) null);
            setSelected(false);
        }

        private void a(TextView textView, ImageView imageView) {
            g gVar = this.e;
            CharSequence charSequence = null;
            Drawable mutate = (gVar == null || gVar.b() == null) ? null : androidx.core.graphics.drawable.a.i(this.e.b()).mutate();
            g gVar2 = this.e;
            CharSequence d = gVar2 != null ? gVar2.d() : null;
            if (imageView != null) {
                if (mutate != null) {
                    imageView.setImageDrawable(mutate);
                    imageView.setVisibility(0);
                    setVisibility(0);
                } else {
                    imageView.setVisibility(8);
                    imageView.setImageDrawable((Drawable) null);
                }
            }
            boolean z = !TextUtils.isEmpty(d);
            if (textView != null) {
                if (z) {
                    textView.setText(d);
                    textView.setVisibility(0);
                    setVisibility(0);
                } else {
                    textView.setVisibility(8);
                    textView.setText((CharSequence) null);
                }
            }
            if (imageView != null) {
                ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) imageView.getLayoutParams();
                int a = (!z || imageView.getVisibility() != 0) ? 0 : TabLayout.this.a(8);
                if (TabLayout.this.E) {
                    if (a != androidx.core.h.g.a(marginLayoutParams)) {
                        androidx.core.h.g.a(marginLayoutParams, a);
                        marginLayoutParams.bottomMargin = 0;
                        imageView.setLayoutParams(marginLayoutParams);
                        imageView.requestLayout();
                    }
                } else if (a != marginLayoutParams.bottomMargin) {
                    marginLayoutParams.bottomMargin = a;
                    androidx.core.h.g.a(marginLayoutParams, 0);
                    imageView.setLayoutParams(marginLayoutParams);
                    imageView.requestLayout();
                }
            }
            g gVar3 = this.e;
            CharSequence a2 = gVar3 != null ? gVar3.c : null;
            if (!z) {
                charSequence = a2;
            }
            i0.a(this, charSequence);
        }

        private float a(Layout layout, int i2, float f2) {
            return layout.getLineWidth(i2) * (f2 / layout.getPaint().getTextSize());
        }
    }

    /* JADX INFO: finally extract failed */
    public TabLayout(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.e = new ArrayList<>();
        this.f1539g = new RectF();
        this.v = Integer.MAX_VALUE;
        this.I = new ArrayList<>();
        this.R = new androidx.core.g.f(12);
        setHorizontalScrollBarEnabled(false);
        f fVar = new f(context);
        this.f1540h = fVar;
        super.addView(fVar, 0, new FrameLayout.LayoutParams(-2, -1));
        TypedArray c2 = k.c(context, attributeSet, R$styleable.TabLayout, i2, R$style.Widget_Design_TabLayout, R$styleable.TabLayout_tabTextAppearance);
        this.f1540h.b(c2.getDimensionPixelSize(R$styleable.TabLayout_tabIndicatorHeight, -1));
        this.f1540h.a(c2.getColor(R$styleable.TabLayout_tabIndicatorColor, 0));
        setSelectedTabIndicator(com.google.android.material.f.a.b(context, c2, R$styleable.TabLayout_tabIndicator));
        setSelectedTabIndicatorGravity(c2.getInt(R$styleable.TabLayout_tabIndicatorGravity, 0));
        setTabIndicatorFullWidth(c2.getBoolean(R$styleable.TabLayout_tabIndicatorFullWidth, true));
        int dimensionPixelSize = c2.getDimensionPixelSize(R$styleable.TabLayout_tabPadding, 0);
        this.l = dimensionPixelSize;
        this.k = dimensionPixelSize;
        this.f1542j = dimensionPixelSize;
        this.f1541i = dimensionPixelSize;
        this.f1541i = c2.getDimensionPixelSize(R$styleable.TabLayout_tabPaddingStart, dimensionPixelSize);
        this.f1542j = c2.getDimensionPixelSize(R$styleable.TabLayout_tabPaddingTop, this.f1542j);
        this.k = c2.getDimensionPixelSize(R$styleable.TabLayout_tabPaddingEnd, this.k);
        this.l = c2.getDimensionPixelSize(R$styleable.TabLayout_tabPaddingBottom, this.l);
        int resourceId = c2.getResourceId(R$styleable.TabLayout_tabTextAppearance, R$style.TextAppearance_Design_Tab);
        this.m = resourceId;
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(resourceId, androidx.appcompat.R$styleable.TextAppearance);
        try {
            this.s = (float) obtainStyledAttributes.getDimensionPixelSize(androidx.appcompat.R$styleable.TextAppearance_android_textSize, 0);
            this.n = com.google.android.material.f.a.a(context, obtainStyledAttributes, androidx.appcompat.R$styleable.TextAppearance_android_textColor);
            obtainStyledAttributes.recycle();
            if (c2.hasValue(R$styleable.TabLayout_tabTextColor)) {
                this.n = com.google.android.material.f.a.a(context, c2, R$styleable.TabLayout_tabTextColor);
            }
            if (c2.hasValue(R$styleable.TabLayout_tabSelectedTextColor)) {
                this.n = a(this.n.getDefaultColor(), c2.getColor(R$styleable.TabLayout_tabSelectedTextColor, 0));
            }
            this.o = com.google.android.material.f.a.a(context, c2, R$styleable.TabLayout_tabIconTint);
            this.r = l.a(c2.getInt(R$styleable.TabLayout_tabIconTintMode, -1), (PorterDuff.Mode) null);
            this.p = com.google.android.material.f.a.a(context, c2, R$styleable.TabLayout_tabRippleColor);
            this.B = c2.getInt(R$styleable.TabLayout_tabIndicatorAnimationDuration, 300);
            this.w = c2.getDimensionPixelSize(R$styleable.TabLayout_tabMinWidth, -1);
            this.x = c2.getDimensionPixelSize(R$styleable.TabLayout_tabMaxWidth, -1);
            this.u = c2.getResourceId(R$styleable.TabLayout_tabBackground, 0);
            this.z = c2.getDimensionPixelSize(R$styleable.TabLayout_tabContentStart, 0);
            this.D = c2.getInt(R$styleable.TabLayout_tabMode, 1);
            this.A = c2.getInt(R$styleable.TabLayout_tabGravity, 0);
            this.E = c2.getBoolean(R$styleable.TabLayout_tabInlineLabel, false);
            this.G = c2.getBoolean(R$styleable.TabLayout_tabUnboundedRipple, false);
            c2.recycle();
            Resources resources = getResources();
            this.t = (float) resources.getDimensionPixelSize(R$dimen.design_tab_text_size_2line);
            this.y = resources.getDimensionPixelSize(R$dimen.design_tab_scrollable_min_width);
            e();
        } catch (Throwable th) {
            obtainStyledAttributes.recycle();
            throw th;
        }
    }

    private void f(g gVar) {
        for (int size = this.I.size() - 1; size >= 0; size--) {
            this.I.get(size).c(gVar);
        }
    }

    private void h(g gVar) {
        for (int size = this.I.size() - 1; size >= 0; size--) {
            this.I.get(size).a(gVar);
        }
    }

    /* access modifiers changed from: package-private */
    public void a(int i2, float f2, boolean z2, boolean z3) {
        int round = Math.round(((float) i2) + f2);
        if (round >= 0 && round < this.f1540h.getChildCount()) {
            if (z3) {
                this.f1540h.a(i2, f2);
            }
            ValueAnimator valueAnimator = this.K;
            if (valueAnimator != null && valueAnimator.isRunning()) {
                this.K.cancel();
            }
            scrollTo(a(i2, f2), 0);
            if (z2) {
                setSelectedTabView(round);
            }
        }
    }

    public void addView(View view, ViewGroup.LayoutParams layoutParams) {
        a(view);
    }

    public void addView(View view, int i2, ViewGroup.LayoutParams layoutParams) {
        a(view);
    }

    /* access modifiers changed from: protected */
    public boolean b(g gVar) {
        return S.a(gVar);
    }

    public void setSelectedTabIndicator(int i2) {
        if (i2 != 0) {
            setSelectedTabIndicator(androidx.appcompat.a.a.a.c(getContext(), i2));
        } else {
            setSelectedTabIndicator((Drawable) null);
        }
    }

    public g b(int i2) {
        if (i2 < 0 || i2 >= getTabCount()) {
            return null;
        }
        return this.e.get(i2);
    }

    private void g(g gVar) {
        for (int size = this.I.size() - 1; size >= 0; size--) {
            this.I.get(size).b(gVar);
        }
    }

    /* access modifiers changed from: package-private */
    public void b(g gVar, boolean z2) {
        g gVar2 = this.f1538f;
        if (gVar2 != gVar) {
            int c2 = gVar != null ? gVar.c() : -1;
            if (z2) {
                if ((gVar2 == null || gVar2.c() == -1) && c2 != -1) {
                    a(c2, 0.0f, true);
                } else {
                    c(c2);
                }
                if (c2 != -1) {
                    setSelectedTabView(c2);
                }
            }
            this.f1538f = gVar;
            if (gVar2 != null) {
                h(gVar2);
            }
            if (gVar != null) {
                g(gVar);
            }
        } else if (gVar2 != null) {
            f(gVar);
            c(gVar.c());
        }
    }

    private void c(int i2) {
        if (i2 != -1) {
            if (getWindowToken() == null || !v.D(this) || this.f1540h.a()) {
                a(i2, 0.0f, true);
                return;
            }
            int scrollX = getScrollX();
            int a2 = a(i2, 0.0f);
            if (scrollX != a2) {
                g();
                this.K.setIntValues(new int[]{scrollX, a2});
                this.K.start();
            }
            this.f1540h.a(i2, this.B);
        }
    }

    private void d(g gVar) {
        this.f1540h.addView(gVar.f1549g, gVar.c(), f());
    }

    private void e() {
        v.b(this.f1540h, this.D == 0 ? Math.max(0, this.z - this.f1541i) : 0, 0, 0, 0);
        int i2 = this.D;
        if (i2 == 0) {
            this.f1540h.setGravity(8388611);
        } else if (i2 == 1) {
            this.f1540h.setGravity(1);
        }
        a(true);
    }

    public void a(g gVar) {
        a(gVar, this.e.isEmpty());
    }

    private void d(int i2) {
        i iVar = (i) this.f1540h.getChildAt(i2);
        this.f1540h.removeViewAt(i2);
        if (iVar != null) {
            iVar.a();
            this.R.a(iVar);
        }
        requestLayout();
    }

    public void a(g gVar, boolean z2) {
        a(gVar, this.e.size(), z2);
    }

    public void a(g gVar, int i2, boolean z2) {
        if (gVar.f1548f == this) {
            a(gVar, i2);
            d(gVar);
            if (z2) {
                gVar.g();
                return;
            }
            return;
        }
        throw new IllegalArgumentException("Tab belongs to a different TabLayout.");
    }

    private void a(TabItem tabItem) {
        g b2 = b();
        CharSequence charSequence = tabItem.e;
        if (charSequence != null) {
            b2.b(charSequence);
        }
        Drawable drawable = tabItem.f1536f;
        if (drawable != null) {
            b2.a(drawable);
        }
        int i2 = tabItem.f1537g;
        if (i2 != 0) {
            b2.a(i2);
        }
        if (!TextUtils.isEmpty(tabItem.getContentDescription())) {
            b2.a(tabItem.getContentDescription());
        }
        a(b2);
    }

    /* access modifiers changed from: package-private */
    public void c(g gVar) {
        b(gVar, true);
    }

    /* access modifiers changed from: protected */
    public g a() {
        g a2 = S.a();
        return a2 == null ? new g() : a2;
    }

    public void a(ViewPager viewPager, boolean z2) {
        a(viewPager, z2, false);
    }

    private void a(ViewPager viewPager, boolean z2, boolean z3) {
        ViewPager viewPager2 = this.L;
        if (viewPager2 != null) {
            h hVar = this.O;
            if (hVar != null) {
                viewPager2.removeOnPageChangeListener(hVar);
            }
            b bVar = this.P;
            if (bVar != null) {
                this.L.removeOnAdapterChangeListener(bVar);
            }
        }
        c cVar = this.J;
        if (cVar != null) {
            removeOnTabSelectedListener(cVar);
            this.J = null;
        }
        if (viewPager != null) {
            this.L = viewPager;
            if (this.O == null) {
                this.O = new h(this);
            }
            this.O.a();
            viewPager.addOnPageChangeListener(this.O);
            j jVar = new j(viewPager);
            this.J = jVar;
            addOnTabSelectedListener(jVar);
            androidx.viewpager.widget.a adapter = viewPager.getAdapter();
            if (adapter != null) {
                a(adapter, z2);
            }
            if (this.P == null) {
                this.P = new b();
            }
            this.P.a(z2);
            viewPager.addOnAdapterChangeListener(this.P);
            a(viewPager.getCurrentItem(), 0.0f, true);
        } else {
            this.L = null;
            a((androidx.viewpager.widget.a) null, false);
        }
        this.Q = z3;
    }

    /* access modifiers changed from: package-private */
    public void a(androidx.viewpager.widget.a aVar, boolean z2) {
        DataSetObserver dataSetObserver;
        androidx.viewpager.widget.a aVar2 = this.M;
        if (!(aVar2 == null || (dataSetObserver = this.N) == null)) {
            aVar2.c(dataSetObserver);
        }
        this.M = aVar;
        if (z2 && aVar != null) {
            if (this.N == null) {
                this.N = new e();
            }
            aVar.a(this.N);
        }
        c();
    }

    private void a(g gVar, int i2) {
        gVar.b(i2);
        this.e.add(i2, gVar);
        int size = this.e.size();
        while (true) {
            i2++;
            if (i2 < size) {
                this.e.get(i2).b(i2);
            } else {
                return;
            }
        }
    }

    private void a(View view) {
        if (view instanceof TabItem) {
            a((TabItem) view);
            return;
        }
        throw new IllegalArgumentException("Only TabItem instances can be added to TabLayout");
    }

    private void a(LinearLayout.LayoutParams layoutParams) {
        if (this.D == 1 && this.A == 0) {
            layoutParams.width = 0;
            layoutParams.weight = 1.0f;
            return;
        }
        layoutParams.width = -2;
        layoutParams.weight = 0.0f;
    }

    /* access modifiers changed from: package-private */
    public int a(int i2) {
        return Math.round(getResources().getDisplayMetrics().density * ((float) i2));
    }

    private int a(int i2, float f2) {
        int i3 = 0;
        if (this.D != 0) {
            return 0;
        }
        View childAt = this.f1540h.getChildAt(i2);
        int i4 = i2 + 1;
        View childAt2 = i4 < this.f1540h.getChildCount() ? this.f1540h.getChildAt(i4) : null;
        int width = childAt != null ? childAt.getWidth() : 0;
        if (childAt2 != null) {
            i3 = childAt2.getWidth();
        }
        int left = (childAt.getLeft() + (width / 2)) - (getWidth() / 2);
        int i5 = (int) (((float) (width + i3)) * 0.5f * f2);
        return v.o(this) == 0 ? left + i5 : left - i5;
    }

    /* access modifiers changed from: package-private */
    public void a(boolean z2) {
        for (int i2 = 0; i2 < this.f1540h.getChildCount(); i2++) {
            View childAt = this.f1540h.getChildAt(i2);
            childAt.setMinimumWidth(getTabMinWidth());
            a((LinearLayout.LayoutParams) childAt.getLayoutParams());
            if (z2) {
                childAt.requestLayout();
            }
        }
    }

    private static ColorStateList a(int i2, int i3) {
        return new ColorStateList(new int[][]{HorizontalScrollView.SELECTED_STATE_SET, HorizontalScrollView.EMPTY_STATE_SET}, new int[]{i3, i2});
    }
}
