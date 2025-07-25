package com.google.android.material.appbar;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.LinearLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.h.d0;
import androidx.core.h.l;
import androidx.core.h.r;
import androidx.core.h.v;
import androidx.customview.view.AbsSavedState;
import com.google.android.material.R$attr;
import com.google.android.material.R$style;
import com.google.android.material.R$styleable;
import com.google.android.material.internal.k;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

@CoordinatorLayout.d(Behavior.class)
public class AppBarLayout extends LinearLayout {
    private int e;

    /* renamed from: f  reason: collision with root package name */
    private int f1376f;

    /* renamed from: g  reason: collision with root package name */
    private int f1377g;

    /* renamed from: h  reason: collision with root package name */
    private boolean f1378h;

    /* renamed from: i  reason: collision with root package name */
    private int f1379i;

    /* renamed from: j  reason: collision with root package name */
    private d0 f1380j;
    private List<b> k;
    private boolean l;
    private boolean m;
    private boolean n;
    private boolean o;
    private int[] p;

    protected static class BaseBehavior<T extends AppBarLayout> extends a<T> {
        /* access modifiers changed from: private */
        public int k;
        private int l;
        private ValueAnimator m;
        private int n = -1;
        private boolean o;
        private float p;
        private WeakReference<View> q;
        private b r;

        class a implements ValueAnimator.AnimatorUpdateListener {
            final /* synthetic */ CoordinatorLayout a;
            final /* synthetic */ AppBarLayout b;

            a(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout) {
                this.a = coordinatorLayout;
                this.b = appBarLayout;
            }

            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                BaseBehavior.this.c(this.a, this.b, ((Integer) valueAnimator.getAnimatedValue()).intValue());
            }
        }

        public static abstract class b<T extends AppBarLayout> {
            public abstract boolean a(T t);
        }

        public BaseBehavior() {
        }

        private static boolean a(int i2, int i3) {
            return (i2 & i3) == i3;
        }

        private void d(CoordinatorLayout coordinatorLayout, T t) {
            int c = c();
            int b2 = b(t, c);
            if (b2 >= 0) {
                View childAt = t.getChildAt(b2);
                c cVar = (c) childAt.getLayoutParams();
                int a2 = cVar.a();
                if ((a2 & 17) == 17) {
                    int i2 = -childAt.getTop();
                    int i3 = -childAt.getBottom();
                    if (b2 == t.getChildCount() - 1) {
                        i3 += t.getTopInset();
                    }
                    if (a(a2, 2)) {
                        i3 += v.p(childAt);
                    } else if (a(a2, 5)) {
                        int p2 = v.p(childAt) + i3;
                        if (c < p2) {
                            i2 = p2;
                        } else {
                            i3 = p2;
                        }
                    }
                    if (a(a2, 32)) {
                        i2 += cVar.topMargin;
                        i3 -= cVar.bottomMargin;
                    }
                    if (c < (i3 + i2) / 2) {
                        i2 = i3;
                    }
                    a(coordinatorLayout, t, androidx.core.c.a.a(i2, -t.getTotalScrollRange(), 0), 0.0f);
                }
            }
        }

        /* access modifiers changed from: package-private */
        public int c(T t) {
            return t.getTotalScrollRange();
        }

        public BaseBehavior(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
        }

        private int c(T t, int i2) {
            int abs = Math.abs(i2);
            int childCount = t.getChildCount();
            int i3 = 0;
            int i4 = 0;
            while (true) {
                if (i4 >= childCount) {
                    break;
                }
                View childAt = t.getChildAt(i4);
                c cVar = (c) childAt.getLayoutParams();
                Interpolator b2 = cVar.b();
                if (abs < childAt.getTop() || abs > childAt.getBottom()) {
                    i4++;
                } else if (b2 != null) {
                    int a2 = cVar.a();
                    if ((a2 & 1) != 0) {
                        i3 = 0 + childAt.getHeight() + cVar.topMargin + cVar.bottomMargin;
                        if ((a2 & 2) != 0) {
                            i3 -= v.p(childAt);
                        }
                    }
                    if (v.l(childAt)) {
                        i3 -= t.getTopInset();
                    }
                    if (i3 > 0) {
                        float f2 = (float) i3;
                        return Integer.signum(i2) * (childAt.getTop() + Math.round(f2 * b2.getInterpolation(((float) (abs - childAt.getTop())) / f2)));
                    }
                }
            }
            return i2;
        }

        protected static class SavedState extends AbsSavedState {
            public static final Parcelable.Creator<SavedState> CREATOR = new a();

            /* renamed from: g  reason: collision with root package name */
            int f1381g;

            /* renamed from: h  reason: collision with root package name */
            float f1382h;

            /* renamed from: i  reason: collision with root package name */
            boolean f1383i;

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
                this.f1381g = parcel.readInt();
                this.f1382h = parcel.readFloat();
                this.f1383i = parcel.readByte() != 0;
            }

            public void writeToParcel(Parcel parcel, int i2) {
                super.writeToParcel(parcel, i2);
                parcel.writeInt(this.f1381g);
                parcel.writeFloat(this.f1382h);
                parcel.writeByte(this.f1383i ? (byte) 1 : 0);
            }

            public SavedState(Parcelable parcelable) {
                super(parcelable);
            }
        }

        private int b(T t, int i2) {
            int childCount = t.getChildCount();
            for (int i3 = 0; i3 < childCount; i3++) {
                View childAt = t.getChildAt(i3);
                int top = childAt.getTop();
                int bottom = childAt.getBottom();
                c cVar = (c) childAt.getLayoutParams();
                if (a(cVar.a(), 32)) {
                    top -= cVar.topMargin;
                    bottom += cVar.bottomMargin;
                }
                int i4 = -i2;
                if (top <= i4 && bottom >= i4) {
                    return i3;
                }
            }
            return -1;
        }

        /* renamed from: a */
        public boolean b(CoordinatorLayout coordinatorLayout, T t, View view, View view2, int i2, int i3) {
            ValueAnimator valueAnimator;
            boolean z = (i2 & 2) != 0 && (t.c() || a(coordinatorLayout, t, view));
            if (z && (valueAnimator = this.m) != null) {
                valueAnimator.cancel();
            }
            this.q = null;
            this.l = i3;
            return z;
        }

        /* access modifiers changed from: package-private */
        public int b(T t) {
            return -t.getDownNestedScrollRange();
        }

        /* renamed from: b */
        public Parcelable d(CoordinatorLayout coordinatorLayout, T t) {
            Parcelable d = super.d(coordinatorLayout, t);
            int b2 = b();
            int childCount = t.getChildCount();
            boolean z = false;
            int i2 = 0;
            while (i2 < childCount) {
                View childAt = t.getChildAt(i2);
                int bottom = childAt.getBottom() + b2;
                if (childAt.getTop() + b2 > 0 || bottom < 0) {
                    i2++;
                } else {
                    SavedState savedState = new SavedState(d);
                    savedState.f1381g = i2;
                    if (bottom == v.p(childAt) + t.getTopInset()) {
                        z = true;
                    }
                    savedState.f1383i = z;
                    savedState.f1382h = ((float) bottom) / ((float) childAt.getHeight());
                    return savedState;
                }
            }
            return d;
        }

        private boolean a(CoordinatorLayout coordinatorLayout, T t, View view) {
            return t.b() && coordinatorLayout.getHeight() - view.getHeight() <= t.getHeight();
        }

        public void a(CoordinatorLayout coordinatorLayout, T t, View view, int i2, int i3, int[] iArr, int i4) {
            int i5;
            int i6;
            if (i3 != 0) {
                if (i3 < 0) {
                    int i7 = -t.getTotalScrollRange();
                    i6 = i7;
                    i5 = t.getDownNestedPreScrollRange() + i7;
                } else {
                    i6 = -t.getUpNestedPreScrollRange();
                    i5 = 0;
                }
                if (i6 != i5) {
                    iArr[1] = a(coordinatorLayout, t, i3, i6, i5);
                    a(i3, t, view, i4);
                }
            }
        }

        private boolean c(CoordinatorLayout coordinatorLayout, T t) {
            List<View> c = coordinatorLayout.c((View) t);
            int size = c.size();
            int i2 = 0;
            while (i2 < size) {
                CoordinatorLayout.c d = ((CoordinatorLayout.f) c.get(i2).getLayoutParams()).d();
                if (!(d instanceof ScrollingViewBehavior)) {
                    i2++;
                } else if (((ScrollingViewBehavior) d).c() != 0) {
                    return true;
                } else {
                    return false;
                }
            }
            return false;
        }

        public void a(CoordinatorLayout coordinatorLayout, T t, View view, int i2, int i3, int i4, int i5, int i6) {
            if (i5 < 0) {
                a(coordinatorLayout, t, i5, -t.getDownNestedScrollRange(), 0);
                a(i5, t, view, i6);
            }
            if (t.c()) {
                t.a(view.getScrollY() > 0);
            }
        }

        private void a(int i2, T t, View view, int i3) {
            if (i3 == 1) {
                int c = c();
                if ((i2 < 0 && c == 0) || (i2 > 0 && c == (-t.getDownNestedScrollRange()))) {
                    v.j(view, 1);
                }
            }
        }

        /* access modifiers changed from: package-private */
        public int c() {
            return b() + this.k;
        }

        public void a(CoordinatorLayout coordinatorLayout, T t, View view, int i2) {
            if (this.l == 0 || i2 == 1) {
                d(coordinatorLayout, t);
            }
            this.q = new WeakReference<>(view);
        }

        private void a(CoordinatorLayout coordinatorLayout, T t, int i2, float f2) {
            int i3;
            int abs = Math.abs(c() - i2);
            float abs2 = Math.abs(f2);
            if (abs2 > 0.0f) {
                i3 = Math.round((((float) abs) / abs2) * 1000.0f) * 3;
            } else {
                i3 = (int) (((((float) abs) / ((float) t.getHeight())) + 1.0f) * 150.0f);
            }
            a(coordinatorLayout, t, i2, i3);
        }

        private void a(CoordinatorLayout coordinatorLayout, T t, int i2, int i3) {
            int c = c();
            if (c == i2) {
                ValueAnimator valueAnimator = this.m;
                if (valueAnimator != null && valueAnimator.isRunning()) {
                    this.m.cancel();
                    return;
                }
                return;
            }
            ValueAnimator valueAnimator2 = this.m;
            if (valueAnimator2 == null) {
                ValueAnimator valueAnimator3 = new ValueAnimator();
                this.m = valueAnimator3;
                valueAnimator3.setInterpolator(com.google.android.material.a.a.e);
                this.m.addUpdateListener(new a(coordinatorLayout, t));
            } else {
                valueAnimator2.cancel();
            }
            this.m.setDuration((long) Math.min(i3, 600));
            this.m.setIntValues(new int[]{c, i2});
            this.m.start();
        }

        public boolean a(CoordinatorLayout coordinatorLayout, T t, int i2, int i3, int i4, int i5) {
            if (((CoordinatorLayout.f) t.getLayoutParams()).height != -2) {
                return super.a(coordinatorLayout, t, i2, i3, i4, i5);
            }
            coordinatorLayout.a((View) t, i2, i3, View.MeasureSpec.makeMeasureSpec(0, 0), i5);
            return true;
        }

        public boolean a(CoordinatorLayout coordinatorLayout, T t, int i2) {
            int i3;
            boolean a2 = super.a(coordinatorLayout, t, i2);
            int pendingAction = t.getPendingAction();
            int i4 = this.n;
            if (i4 >= 0 && (pendingAction & 8) == 0) {
                View childAt = t.getChildAt(i4);
                int i5 = -childAt.getBottom();
                if (this.o) {
                    i3 = v.p(childAt) + t.getTopInset();
                } else {
                    i3 = Math.round(((float) childAt.getHeight()) * this.p);
                }
                c(coordinatorLayout, t, i5 + i3);
            } else if (pendingAction != 0) {
                boolean z = (pendingAction & 4) != 0;
                if ((pendingAction & 2) != 0) {
                    int i6 = -t.getUpNestedPreScrollRange();
                    if (z) {
                        a(coordinatorLayout, t, i6, 0.0f);
                    } else {
                        c(coordinatorLayout, t, i6);
                    }
                } else if ((pendingAction & 1) != 0) {
                    if (z) {
                        a(coordinatorLayout, t, 0, 0.0f);
                    } else {
                        c(coordinatorLayout, t, 0);
                    }
                }
            }
            t.d();
            this.n = -1;
            a(androidx.core.c.a.a(b(), -t.getTotalScrollRange(), 0));
            a(coordinatorLayout, t, b(), 0, true);
            t.a(b());
            return a2;
        }

        /* access modifiers changed from: package-private */
        public boolean a(T t) {
            b bVar = this.r;
            if (bVar != null) {
                return bVar.a(t);
            }
            WeakReference<View> weakReference = this.q;
            if (weakReference == null) {
                return true;
            }
            View view = (View) weakReference.get();
            if (view == null || !view.isShown() || view.canScrollVertically(-1)) {
                return false;
            }
            return true;
        }

        /* access modifiers changed from: package-private */
        /* renamed from: a */
        public void e(CoordinatorLayout coordinatorLayout, T t) {
            d(coordinatorLayout, t);
        }

        /* access modifiers changed from: package-private */
        /* renamed from: a */
        public int b(CoordinatorLayout coordinatorLayout, T t, int i2, int i3, int i4) {
            int c = c();
            int i5 = 0;
            if (i3 == 0 || c < i3 || c > i4) {
                this.k = 0;
            } else {
                int a2 = androidx.core.c.a.a(i2, i3, i4);
                if (c != a2) {
                    int c2 = t.a() ? c(t, a2) : a2;
                    boolean a3 = a(c2);
                    i5 = c - a2;
                    this.k = a2 - c2;
                    if (!a3 && t.a()) {
                        coordinatorLayout.a((View) t);
                    }
                    t.a(b());
                    a(coordinatorLayout, t, a2, a2 < c ? -1 : 1, false);
                }
            }
            return i5;
        }

        /* JADX WARNING: Removed duplicated region for block: B:22:0x0055  */
        /* JADX WARNING: Removed duplicated region for block: B:26:0x0061  */
        /* JADX WARNING: Removed duplicated region for block: B:33:? A[RETURN, SYNTHETIC] */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        private void a(androidx.coordinatorlayout.widget.CoordinatorLayout r6, T r7, int r8, int r9, boolean r10) {
            /*
                r5 = this;
                android.view.View r0 = a((com.google.android.material.appbar.AppBarLayout) r7, (int) r8)
                if (r0 == 0) goto L_0x006e
                android.view.ViewGroup$LayoutParams r1 = r0.getLayoutParams()
                com.google.android.material.appbar.AppBarLayout$c r1 = (com.google.android.material.appbar.AppBarLayout.c) r1
                int r1 = r1.a()
                r2 = r1 & 1
                r3 = 1
                r4 = 0
                if (r2 == 0) goto L_0x0041
                int r2 = androidx.core.h.v.p(r0)
                if (r9 <= 0) goto L_0x002f
                r9 = r1 & 12
                if (r9 == 0) goto L_0x002f
                int r8 = -r8
                int r9 = r0.getBottom()
                int r9 = r9 - r2
                int r0 = r7.getTopInset()
                int r9 = r9 - r0
                if (r8 < r9) goto L_0x0041
            L_0x002d:
                r8 = 1
                goto L_0x0042
            L_0x002f:
                r9 = r1 & 2
                if (r9 == 0) goto L_0x0041
                int r8 = -r8
                int r9 = r0.getBottom()
                int r9 = r9 - r2
                int r0 = r7.getTopInset()
                int r9 = r9 - r0
                if (r8 < r9) goto L_0x0041
                goto L_0x002d
            L_0x0041:
                r8 = 0
            L_0x0042:
                boolean r9 = r7.c()
                if (r9 == 0) goto L_0x0057
                android.view.View r9 = r5.a((androidx.coordinatorlayout.widget.CoordinatorLayout) r6)
                if (r9 == 0) goto L_0x0057
                int r8 = r9.getScrollY()
                if (r8 <= 0) goto L_0x0055
                goto L_0x0056
            L_0x0055:
                r3 = 0
            L_0x0056:
                r8 = r3
            L_0x0057:
                boolean r8 = r7.a((boolean) r8)
                int r9 = android.os.Build.VERSION.SDK_INT
                r0 = 11
                if (r9 < r0) goto L_0x006e
                if (r10 != 0) goto L_0x006b
                if (r8 == 0) goto L_0x006e
                boolean r6 = r5.c((androidx.coordinatorlayout.widget.CoordinatorLayout) r6, r7)
                if (r6 == 0) goto L_0x006e
            L_0x006b:
                r7.jumpDrawablesToCurrentState()
            L_0x006e:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.material.appbar.AppBarLayout.BaseBehavior.a(androidx.coordinatorlayout.widget.CoordinatorLayout, com.google.android.material.appbar.AppBarLayout, int, int, boolean):void");
        }

        private static View a(AppBarLayout appBarLayout, int i2) {
            int abs = Math.abs(i2);
            int childCount = appBarLayout.getChildCount();
            for (int i3 = 0; i3 < childCount; i3++) {
                View childAt = appBarLayout.getChildAt(i3);
                if (abs >= childAt.getTop() && abs <= childAt.getBottom()) {
                    return childAt;
                }
            }
            return null;
        }

        private View a(CoordinatorLayout coordinatorLayout) {
            int childCount = coordinatorLayout.getChildCount();
            for (int i2 = 0; i2 < childCount; i2++) {
                View childAt = coordinatorLayout.getChildAt(i2);
                if (childAt instanceof l) {
                    return childAt;
                }
            }
            return null;
        }

        public void a(CoordinatorLayout coordinatorLayout, T t, Parcelable parcelable) {
            if (parcelable instanceof SavedState) {
                SavedState savedState = (SavedState) parcelable;
                super.a(coordinatorLayout, t, savedState.a());
                this.n = savedState.f1381g;
                this.p = savedState.f1382h;
                this.o = savedState.f1383i;
                return;
            }
            super.a(coordinatorLayout, t, parcelable);
            this.n = -1;
        }
    }

    public static class Behavior extends BaseBehavior<AppBarLayout> {
        public Behavior() {
        }

        public Behavior(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
        }
    }

    public static class ScrollingViewBehavior extends b {
        public ScrollingViewBehavior() {
        }

        /* access modifiers changed from: package-private */
        public int c(View view) {
            if (view instanceof AppBarLayout) {
                return ((AppBarLayout) view).getTotalScrollRange();
            }
            return super.c(view);
        }

        public ScrollingViewBehavior(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.ScrollingViewBehavior_Layout);
            b(obtainStyledAttributes.getDimensionPixelSize(R$styleable.ScrollingViewBehavior_Layout_behavior_overlapTop, 0));
            obtainStyledAttributes.recycle();
        }

        public boolean b(CoordinatorLayout coordinatorLayout, View view, View view2) {
            a(view, view2);
            b(view, view2);
            return false;
        }

        /* access modifiers changed from: package-private */
        public float b(View view) {
            int i2;
            if (view instanceof AppBarLayout) {
                AppBarLayout appBarLayout = (AppBarLayout) view;
                int totalScrollRange = appBarLayout.getTotalScrollRange();
                int downNestedPreScrollRange = appBarLayout.getDownNestedPreScrollRange();
                int a = a(appBarLayout);
                if ((downNestedPreScrollRange == 0 || totalScrollRange + a > downNestedPreScrollRange) && (i2 = totalScrollRange - downNestedPreScrollRange) != 0) {
                    return (((float) a) / ((float) i2)) + 1.0f;
                }
            }
            return 0.0f;
        }

        public boolean a(CoordinatorLayout coordinatorLayout, View view, View view2) {
            return view2 instanceof AppBarLayout;
        }

        public boolean a(CoordinatorLayout coordinatorLayout, View view, Rect rect, boolean z) {
            AppBarLayout a = a((List) coordinatorLayout.b(view));
            if (a != null) {
                rect.offset(view.getLeft(), view.getTop());
                Rect rect2 = this.d;
                rect2.set(0, 0, coordinatorLayout.getWidth(), coordinatorLayout.getHeight());
                if (!rect2.contains(rect)) {
                    a.a(false, !z);
                    return true;
                }
            }
            return false;
        }

        private void b(View view, View view2) {
            if (view2 instanceof AppBarLayout) {
                AppBarLayout appBarLayout = (AppBarLayout) view2;
                if (appBarLayout.c()) {
                    appBarLayout.a(view.getScrollY() > 0);
                }
            }
        }

        private void a(View view, View view2) {
            CoordinatorLayout.c d = ((CoordinatorLayout.f) view2.getLayoutParams()).d();
            if (d instanceof BaseBehavior) {
                v.e(view, (((view2.getBottom() - view.getTop()) + ((BaseBehavior) d).k) + d()) - a(view2));
            }
        }

        private static int a(AppBarLayout appBarLayout) {
            CoordinatorLayout.c d = ((CoordinatorLayout.f) appBarLayout.getLayoutParams()).d();
            if (d instanceof BaseBehavior) {
                return ((BaseBehavior) d).c();
            }
            return 0;
        }

        /* access modifiers changed from: package-private */
        public AppBarLayout a(List<View> list) {
            int size = list.size();
            for (int i2 = 0; i2 < size; i2++) {
                View view = list.get(i2);
                if (view instanceof AppBarLayout) {
                    return (AppBarLayout) view;
                }
            }
            return null;
        }
    }

    class a implements r {
        a() {
        }

        public d0 a(View view, d0 d0Var) {
            AppBarLayout.this.a(d0Var);
            return d0Var;
        }
    }

    public interface b<T extends AppBarLayout> {
        void a(T t, int i2);
    }

    public interface d extends b<AppBarLayout> {
    }

    public AppBarLayout(Context context) {
        this(context, (AttributeSet) null);
    }

    private boolean e() {
        int childCount = getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            if (((c) getChildAt(i2).getLayoutParams()).c()) {
                return true;
            }
        }
        return false;
    }

    private void f() {
        this.e = -1;
        this.f1376f = -1;
        this.f1377g = -1;
    }

    public void a(boolean z, boolean z2) {
        a(z, z2, true);
    }

    public void addOnOffsetChangedListener(b bVar) {
        if (this.k == null) {
            this.k = new ArrayList();
        }
        if (bVar != null && !this.k.contains(bVar)) {
            this.k.add(bVar);
        }
    }

    /* access modifiers changed from: package-private */
    public boolean b() {
        return getTotalScrollRange() != 0;
    }

    public boolean c() {
        return this.o;
    }

    /* access modifiers changed from: protected */
    public boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return layoutParams instanceof c;
    }

    /* access modifiers changed from: package-private */
    public void d() {
        this.f1379i = 0;
    }

    /* access modifiers changed from: package-private */
    public int getDownNestedPreScrollRange() {
        int i2;
        int i3 = this.f1376f;
        if (i3 != -1) {
            return i3;
        }
        int i4 = 0;
        for (int childCount = getChildCount() - 1; childCount >= 0; childCount--) {
            View childAt = getChildAt(childCount);
            c cVar = (c) childAt.getLayoutParams();
            int measuredHeight = childAt.getMeasuredHeight();
            int i5 = cVar.a;
            if ((i5 & 5) == 5) {
                int i6 = i4 + cVar.topMargin + cVar.bottomMargin;
                if ((i5 & 8) != 0) {
                    i4 = i6 + v.p(childAt);
                } else {
                    if ((i5 & 2) != 0) {
                        i2 = v.p(childAt);
                    } else {
                        i2 = getTopInset();
                    }
                    i4 = i6 + (measuredHeight - i2);
                }
            } else if (i4 > 0) {
                break;
            }
        }
        int max = Math.max(0, i4);
        this.f1376f = max;
        return max;
    }

    /* access modifiers changed from: package-private */
    public int getDownNestedScrollRange() {
        int i2 = this.f1377g;
        if (i2 != -1) {
            return i2;
        }
        int childCount = getChildCount();
        int i3 = 0;
        int i4 = 0;
        while (true) {
            if (i3 >= childCount) {
                break;
            }
            View childAt = getChildAt(i3);
            c cVar = (c) childAt.getLayoutParams();
            int measuredHeight = childAt.getMeasuredHeight() + cVar.topMargin + cVar.bottomMargin;
            int i5 = cVar.a;
            if ((i5 & 1) == 0) {
                break;
            }
            i4 += measuredHeight;
            if ((i5 & 2) != 0) {
                i4 -= v.p(childAt) + getTopInset();
                break;
            }
            i3++;
        }
        int max = Math.max(0, i4);
        this.f1377g = max;
        return max;
    }

    public final int getMinimumHeightForVisibleOverlappingContent() {
        int topInset = getTopInset();
        int p2 = v.p(this);
        if (p2 == 0) {
            int childCount = getChildCount();
            p2 = childCount >= 1 ? v.p(getChildAt(childCount - 1)) : 0;
            if (p2 == 0) {
                return getHeight() / 3;
            }
        }
        return (p2 * 2) + topInset;
    }

    /* access modifiers changed from: package-private */
    public int getPendingAction() {
        return this.f1379i;
    }

    @Deprecated
    public float getTargetElevation() {
        return 0.0f;
    }

    /* access modifiers changed from: package-private */
    public final int getTopInset() {
        d0 d0Var = this.f1380j;
        if (d0Var != null) {
            return d0Var.e();
        }
        return 0;
    }

    public final int getTotalScrollRange() {
        int i2 = this.e;
        if (i2 != -1) {
            return i2;
        }
        int childCount = getChildCount();
        int i3 = 0;
        int i4 = 0;
        while (true) {
            if (i3 >= childCount) {
                break;
            }
            View childAt = getChildAt(i3);
            c cVar = (c) childAt.getLayoutParams();
            int measuredHeight = childAt.getMeasuredHeight();
            int i5 = cVar.a;
            if ((i5 & 1) == 0) {
                break;
            }
            i4 += measuredHeight + cVar.topMargin + cVar.bottomMargin;
            if ((i5 & 2) != 0) {
                i4 -= v.p(childAt);
                break;
            }
            i3++;
        }
        int max = Math.max(0, i4 - getTopInset());
        this.e = max;
        return max;
    }

    /* access modifiers changed from: package-private */
    public int getUpNestedPreScrollRange() {
        return getTotalScrollRange();
    }

    /* access modifiers changed from: protected */
    public int[] onCreateDrawableState(int i2) {
        if (this.p == null) {
            this.p = new int[4];
        }
        int[] iArr = this.p;
        int[] onCreateDrawableState = super.onCreateDrawableState(i2 + iArr.length);
        iArr[0] = this.m ? R$attr.state_liftable : -R$attr.state_liftable;
        iArr[1] = (!this.m || !this.n) ? -R$attr.state_lifted : R$attr.state_lifted;
        iArr[2] = this.m ? R$attr.state_collapsible : -R$attr.state_collapsible;
        iArr[3] = (!this.m || !this.n) ? -R$attr.state_collapsed : R$attr.state_collapsed;
        return LinearLayout.mergeDrawableStates(onCreateDrawableState, iArr);
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i2, int i3, int i4, int i5) {
        super.onLayout(z, i2, i3, i4, i5);
        f();
        boolean z2 = false;
        this.f1378h = false;
        int childCount = getChildCount();
        int i6 = 0;
        while (true) {
            if (i6 >= childCount) {
                break;
            } else if (((c) getChildAt(i6).getLayoutParams()).b() != null) {
                this.f1378h = true;
                break;
            } else {
                i6++;
            }
        }
        if (!this.l) {
            if (this.o || e()) {
                z2 = true;
            }
            b(z2);
        }
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i2, int i3) {
        super.onMeasure(i2, i3);
        f();
    }

    public void removeOnOffsetChangedListener(b bVar) {
        List<b> list = this.k;
        if (list != null && bVar != null) {
            list.remove(bVar);
        }
    }

    public void setExpanded(boolean z) {
        a(z, v.D(this));
    }

    public void setLiftOnScroll(boolean z) {
        this.o = z;
    }

    public void setOrientation(int i2) {
        if (i2 == 1) {
            super.setOrientation(i2);
            return;
        }
        throw new IllegalArgumentException("AppBarLayout is always vertical and does not support horizontal orientation");
    }

    @Deprecated
    public void setTargetElevation(float f2) {
        if (Build.VERSION.SDK_INT >= 21) {
            e.a(this, f2);
        }
    }

    public AppBarLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.e = -1;
        this.f1376f = -1;
        this.f1377g = -1;
        this.f1379i = 0;
        setOrientation(1);
        if (Build.VERSION.SDK_INT >= 21) {
            e.a(this);
            e.a(this, attributeSet, 0, R$style.Widget_Design_AppBarLayout);
        }
        TypedArray c2 = k.c(context, attributeSet, R$styleable.AppBarLayout, 0, R$style.Widget_Design_AppBarLayout, new int[0]);
        v.a((View) this, c2.getDrawable(R$styleable.AppBarLayout_android_background));
        if (c2.hasValue(R$styleable.AppBarLayout_expanded)) {
            a(c2.getBoolean(R$styleable.AppBarLayout_expanded, false), false, false);
        }
        if (Build.VERSION.SDK_INT >= 21 && c2.hasValue(R$styleable.AppBarLayout_elevation)) {
            e.a(this, (float) c2.getDimensionPixelSize(R$styleable.AppBarLayout_elevation, 0));
        }
        if (Build.VERSION.SDK_INT >= 26) {
            if (c2.hasValue(R$styleable.AppBarLayout_android_keyboardNavigationCluster)) {
                setKeyboardNavigationCluster(c2.getBoolean(R$styleable.AppBarLayout_android_keyboardNavigationCluster, false));
            }
            if (c2.hasValue(R$styleable.AppBarLayout_android_touchscreenBlocksFocus)) {
                setTouchscreenBlocksFocus(c2.getBoolean(R$styleable.AppBarLayout_android_touchscreenBlocksFocus, false));
            }
        }
        this.o = c2.getBoolean(R$styleable.AppBarLayout_liftOnScroll, false);
        c2.recycle();
        v.a((View) this, (r) new a());
    }

    private void a(boolean z, boolean z2, boolean z3) {
        int i2 = 0;
        int i3 = (z ? 1 : 2) | (z2 ? 4 : 0);
        if (z3) {
            i2 = 8;
        }
        this.f1379i = i3 | i2;
        requestLayout();
    }

    private boolean b(boolean z) {
        if (this.m == z) {
            return false;
        }
        this.m = z;
        refreshDrawableState();
        return true;
    }

    /* access modifiers changed from: protected */
    public c generateDefaultLayoutParams() {
        return new c(-1, -2);
    }

    public void removeOnOffsetChangedListener(d dVar) {
        removeOnOffsetChangedListener((b) dVar);
    }

    /* access modifiers changed from: package-private */
    public boolean a() {
        return this.f1378h;
    }

    /* access modifiers changed from: package-private */
    public void a(int i2) {
        List<b> list = this.k;
        if (list != null) {
            int size = list.size();
            for (int i3 = 0; i3 < size; i3++) {
                b bVar = this.k.get(i3);
                if (bVar != null) {
                    bVar.a(this, i2);
                }
            }
        }
    }

    public void addOnOffsetChangedListener(d dVar) {
        addOnOffsetChangedListener((b) dVar);
    }

    public c generateLayoutParams(AttributeSet attributeSet) {
        return new c(getContext(), attributeSet);
    }

    /* access modifiers changed from: protected */
    public c generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        if (Build.VERSION.SDK_INT >= 19 && (layoutParams instanceof LinearLayout.LayoutParams)) {
            return new c((LinearLayout.LayoutParams) layoutParams);
        }
        if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
            return new c((ViewGroup.MarginLayoutParams) layoutParams);
        }
        return new c(layoutParams);
    }

    public static class c extends LinearLayout.LayoutParams {
        int a = 1;
        Interpolator b;

        public c(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.AppBarLayout_Layout);
            this.a = obtainStyledAttributes.getInt(R$styleable.AppBarLayout_Layout_layout_scrollFlags, 0);
            if (obtainStyledAttributes.hasValue(R$styleable.AppBarLayout_Layout_layout_scrollInterpolator)) {
                this.b = AnimationUtils.loadInterpolator(context, obtainStyledAttributes.getResourceId(R$styleable.AppBarLayout_Layout_layout_scrollInterpolator, 0));
            }
            obtainStyledAttributes.recycle();
        }

        public int a() {
            return this.a;
        }

        public Interpolator b() {
            return this.b;
        }

        /* access modifiers changed from: package-private */
        public boolean c() {
            int i2 = this.a;
            return (i2 & 1) == 1 && (i2 & 10) != 0;
        }

        public c(int i2, int i3) {
            super(i2, i3);
        }

        public c(ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
        }

        public c(ViewGroup.MarginLayoutParams marginLayoutParams) {
            super(marginLayoutParams);
        }

        public c(LinearLayout.LayoutParams layoutParams) {
            super(layoutParams);
        }
    }

    /* access modifiers changed from: package-private */
    public boolean a(boolean z) {
        if (this.n == z) {
            return false;
        }
        this.n = z;
        refreshDrawableState();
        return true;
    }

    /* access modifiers changed from: package-private */
    public d0 a(d0 d0Var) {
        d0 d0Var2 = v.l(this) ? d0Var : null;
        if (!androidx.core.g.c.a(this.f1380j, d0Var2)) {
            this.f1380j = d0Var2;
            f();
        }
        return d0Var;
    }
}
