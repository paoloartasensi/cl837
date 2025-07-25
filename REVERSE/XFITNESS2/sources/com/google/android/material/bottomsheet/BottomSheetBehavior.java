package com.google.android.material.bottomsheet;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.h.v;
import androidx.customview.a.c;
import androidx.customview.view.AbsSavedState;
import com.google.android.material.R$dimen;
import com.google.android.material.R$styleable;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

public class BottomSheetBehavior<V extends View> extends CoordinatorLayout.c<V> {
    /* access modifiers changed from: private */
    public boolean a = true;
    private float b;
    private int c;
    private boolean d;
    private int e;

    /* renamed from: f  reason: collision with root package name */
    private int f1426f;

    /* renamed from: g  reason: collision with root package name */
    int f1427g;

    /* renamed from: h  reason: collision with root package name */
    int f1428h;

    /* renamed from: i  reason: collision with root package name */
    int f1429i;

    /* renamed from: j  reason: collision with root package name */
    boolean f1430j;
    private boolean k;
    int l = 4;
    androidx.customview.a.c m;
    private boolean n;
    private int o;
    private boolean p;
    int q;
    WeakReference<V> r;
    WeakReference<View> s;
    private c t;
    private VelocityTracker u;
    int v;
    private int w;
    boolean x;
    private Map<View, Integer> y;
    private final c.C0030c z = new b();

    class a implements Runnable {
        final /* synthetic */ View e;

        /* renamed from: f  reason: collision with root package name */
        final /* synthetic */ int f1432f;

        a(View view, int i2) {
            this.e = view;
            this.f1432f = i2;
        }

        public void run() {
            BottomSheetBehavior.this.a(this.e, this.f1432f);
        }
    }

    class b extends c.C0030c {
        b() {
        }

        public void a(View view, int i2, int i3, int i4, int i5) {
            BottomSheetBehavior.this.a(i3);
        }

        public boolean b(View view, int i2) {
            WeakReference<V> weakReference;
            View view2;
            BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.this;
            int i3 = bottomSheetBehavior.l;
            if (i3 == 1 || bottomSheetBehavior.x) {
                return false;
            }
            if ((i3 != 3 || bottomSheetBehavior.v != i2 || (view2 = (View) bottomSheetBehavior.s.get()) == null || !view2.canScrollVertically(-1)) && (weakReference = BottomSheetBehavior.this.r) != null && weakReference.get() == view) {
                return true;
            }
            return false;
        }

        public void c(int i2) {
            if (i2 == 1) {
                BottomSheetBehavior.this.d(1);
            }
        }

        /* JADX WARNING: Removed duplicated region for block: B:42:0x00d5  */
        /* JADX WARNING: Removed duplicated region for block: B:43:0x00e6  */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void a(android.view.View r8, float r9, float r10) {
            /*
                r7 = this;
                r0 = 0
                r1 = 0
                r2 = 4
                r3 = 6
                r4 = 3
                int r5 = (r10 > r1 ? 1 : (r10 == r1 ? 0 : -1))
                if (r5 >= 0) goto L_0x0029
                com.google.android.material.bottomsheet.BottomSheetBehavior r9 = com.google.android.material.bottomsheet.BottomSheetBehavior.this
                boolean r9 = r9.a
                if (r9 == 0) goto L_0x0018
                com.google.android.material.bottomsheet.BottomSheetBehavior r9 = com.google.android.material.bottomsheet.BottomSheetBehavior.this
                int r9 = r9.f1427g
                r2 = 3
                goto L_0x00c7
            L_0x0018:
                int r9 = r8.getTop()
                com.google.android.material.bottomsheet.BottomSheetBehavior r10 = com.google.android.material.bottomsheet.BottomSheetBehavior.this
                int r10 = r10.f1428h
                if (r9 <= r10) goto L_0x0025
                r0 = r10
            L_0x0023:
                r2 = 6
                goto L_0x0026
            L_0x0025:
                r2 = 3
            L_0x0026:
                r9 = r0
                goto L_0x00c7
            L_0x0029:
                com.google.android.material.bottomsheet.BottomSheetBehavior r5 = com.google.android.material.bottomsheet.BottomSheetBehavior.this
                boolean r6 = r5.f1430j
                if (r6 == 0) goto L_0x0052
                boolean r5 = r5.a((android.view.View) r8, (float) r10)
                if (r5 == 0) goto L_0x0052
                int r5 = r8.getTop()
                com.google.android.material.bottomsheet.BottomSheetBehavior r6 = com.google.android.material.bottomsheet.BottomSheetBehavior.this
                int r6 = r6.f1429i
                if (r5 > r6) goto L_0x004b
                float r5 = java.lang.Math.abs(r9)
                float r6 = java.lang.Math.abs(r10)
                int r5 = (r5 > r6 ? 1 : (r5 == r6 ? 0 : -1))
                if (r5 >= 0) goto L_0x0052
            L_0x004b:
                com.google.android.material.bottomsheet.BottomSheetBehavior r9 = com.google.android.material.bottomsheet.BottomSheetBehavior.this
                int r9 = r9.q
                r2 = 5
                goto L_0x00c7
            L_0x0052:
                int r1 = (r10 > r1 ? 1 : (r10 == r1 ? 0 : -1))
                if (r1 == 0) goto L_0x0068
                float r9 = java.lang.Math.abs(r9)
                float r10 = java.lang.Math.abs(r10)
                int r9 = (r9 > r10 ? 1 : (r9 == r10 ? 0 : -1))
                if (r9 <= 0) goto L_0x0063
                goto L_0x0068
            L_0x0063:
                com.google.android.material.bottomsheet.BottomSheetBehavior r9 = com.google.android.material.bottomsheet.BottomSheetBehavior.this
                int r9 = r9.f1429i
                goto L_0x00c7
            L_0x0068:
                int r9 = r8.getTop()
                com.google.android.material.bottomsheet.BottomSheetBehavior r10 = com.google.android.material.bottomsheet.BottomSheetBehavior.this
                boolean r10 = r10.a
                if (r10 == 0) goto L_0x0093
                com.google.android.material.bottomsheet.BottomSheetBehavior r10 = com.google.android.material.bottomsheet.BottomSheetBehavior.this
                int r10 = r10.f1427g
                int r10 = r9 - r10
                int r10 = java.lang.Math.abs(r10)
                com.google.android.material.bottomsheet.BottomSheetBehavior r0 = com.google.android.material.bottomsheet.BottomSheetBehavior.this
                int r0 = r0.f1429i
                int r9 = r9 - r0
                int r9 = java.lang.Math.abs(r9)
                if (r10 >= r9) goto L_0x008e
                com.google.android.material.bottomsheet.BottomSheetBehavior r9 = com.google.android.material.bottomsheet.BottomSheetBehavior.this
                int r0 = r9.f1427g
                goto L_0x0025
            L_0x008e:
                com.google.android.material.bottomsheet.BottomSheetBehavior r9 = com.google.android.material.bottomsheet.BottomSheetBehavior.this
                int r0 = r9.f1429i
                goto L_0x0026
            L_0x0093:
                com.google.android.material.bottomsheet.BottomSheetBehavior r10 = com.google.android.material.bottomsheet.BottomSheetBehavior.this
                int r1 = r10.f1428h
                if (r9 >= r1) goto L_0x00aa
                int r10 = r10.f1429i
                int r10 = r9 - r10
                int r10 = java.lang.Math.abs(r10)
                if (r9 >= r10) goto L_0x00a4
                goto L_0x0025
            L_0x00a4:
                com.google.android.material.bottomsheet.BottomSheetBehavior r9 = com.google.android.material.bottomsheet.BottomSheetBehavior.this
                int r0 = r9.f1428h
                goto L_0x0023
            L_0x00aa:
                int r10 = r9 - r1
                int r10 = java.lang.Math.abs(r10)
                com.google.android.material.bottomsheet.BottomSheetBehavior r0 = com.google.android.material.bottomsheet.BottomSheetBehavior.this
                int r0 = r0.f1429i
                int r9 = r9 - r0
                int r9 = java.lang.Math.abs(r9)
                if (r10 >= r9) goto L_0x00c1
                com.google.android.material.bottomsheet.BottomSheetBehavior r9 = com.google.android.material.bottomsheet.BottomSheetBehavior.this
                int r0 = r9.f1428h
                goto L_0x0023
            L_0x00c1:
                com.google.android.material.bottomsheet.BottomSheetBehavior r9 = com.google.android.material.bottomsheet.BottomSheetBehavior.this
                int r0 = r9.f1429i
                goto L_0x0026
            L_0x00c7:
                com.google.android.material.bottomsheet.BottomSheetBehavior r10 = com.google.android.material.bottomsheet.BottomSheetBehavior.this
                androidx.customview.a.c r10 = r10.m
                int r0 = r8.getLeft()
                boolean r9 = r10.d(r0, r9)
                if (r9 == 0) goto L_0x00e6
                com.google.android.material.bottomsheet.BottomSheetBehavior r9 = com.google.android.material.bottomsheet.BottomSheetBehavior.this
                r10 = 2
                r9.d((int) r10)
                com.google.android.material.bottomsheet.BottomSheetBehavior$d r9 = new com.google.android.material.bottomsheet.BottomSheetBehavior$d
                com.google.android.material.bottomsheet.BottomSheetBehavior r10 = com.google.android.material.bottomsheet.BottomSheetBehavior.this
                r9.<init>(r8, r2)
                androidx.core.h.v.a((android.view.View) r8, (java.lang.Runnable) r9)
                goto L_0x00eb
            L_0x00e6:
                com.google.android.material.bottomsheet.BottomSheetBehavior r8 = com.google.android.material.bottomsheet.BottomSheetBehavior.this
                r8.d((int) r2)
            L_0x00eb:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.material.bottomsheet.BottomSheetBehavior.b.a(android.view.View, float, float):void");
        }

        public int b(View view, int i2, int i3) {
            int b = BottomSheetBehavior.this.d();
            BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.this;
            return androidx.core.c.a.a(i2, b, bottomSheetBehavior.f1430j ? bottomSheetBehavior.q : bottomSheetBehavior.f1429i);
        }

        public int b(View view) {
            BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.this;
            if (bottomSheetBehavior.f1430j) {
                return bottomSheetBehavior.q;
            }
            return bottomSheetBehavior.f1429i;
        }

        public int a(View view, int i2, int i3) {
            return view.getLeft();
        }
    }

    public static abstract class c {
        public abstract void a(View view, float f2);

        public abstract void a(View view, int i2);
    }

    private class d implements Runnable {
        private final View e;

        /* renamed from: f  reason: collision with root package name */
        private final int f1434f;

        d(View view, int i2) {
            this.e = view;
            this.f1434f = i2;
        }

        public void run() {
            androidx.customview.a.c cVar = BottomSheetBehavior.this.m;
            if (cVar == null || !cVar.a(true)) {
                BottomSheetBehavior.this.d(this.f1434f);
            } else {
                v.a(this.e, (Runnable) this);
            }
        }
    }

    public BottomSheetBehavior() {
    }

    private float e() {
        VelocityTracker velocityTracker = this.u;
        if (velocityTracker == null) {
            return 0.0f;
        }
        velocityTracker.computeCurrentVelocity(1000, this.b);
        return this.u.getYVelocity(this.v);
    }

    private void f() {
        this.v = -1;
        VelocityTracker velocityTracker = this.u;
        if (velocityTracker != null) {
            velocityTracker.recycle();
            this.u = null;
        }
    }

    public void c(boolean z2) {
        this.k = z2;
    }

    public Parcelable d(CoordinatorLayout coordinatorLayout, V v2) {
        return new SavedState(super.d(coordinatorLayout, v2), this.l);
    }

    protected static class SavedState extends AbsSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new a();

        /* renamed from: g  reason: collision with root package name */
        final int f1431g;

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
            this.f1431g = parcel.readInt();
        }

        public void writeToParcel(Parcel parcel, int i2) {
            super.writeToParcel(parcel, i2);
            parcel.writeInt(this.f1431g);
        }

        public SavedState(Parcelable parcelable, int i2) {
            super(parcelable);
            this.f1431g = i2;
        }
    }

    public void a(CoordinatorLayout coordinatorLayout, V v2, Parcelable parcelable) {
        SavedState savedState = (SavedState) parcelable;
        super.a(coordinatorLayout, v2, savedState.a());
        int i2 = savedState.f1431g;
        if (i2 == 1 || i2 == 2) {
            this.l = 4;
        } else {
            this.l = i2;
        }
    }

    public boolean b(CoordinatorLayout coordinatorLayout, V v2, MotionEvent motionEvent) {
        if (!v2.isShown()) {
            return false;
        }
        int actionMasked = motionEvent.getActionMasked();
        if (this.l == 1 && actionMasked == 0) {
            return true;
        }
        androidx.customview.a.c cVar = this.m;
        if (cVar != null) {
            cVar.a(motionEvent);
        }
        if (actionMasked == 0) {
            f();
        }
        if (this.u == null) {
            this.u = VelocityTracker.obtain();
        }
        this.u.addMovement(motionEvent);
        if (actionMasked == 2 && !this.n && Math.abs(((float) this.w) - motionEvent.getY()) > ((float) this.m.e())) {
            this.m.a((View) v2, motionEvent.getPointerId(motionEvent.getActionIndex()));
        }
        return !this.n;
    }

    public final void c(int i2) {
        if (i2 != this.l) {
            WeakReference<V> weakReference = this.r;
            if (weakReference != null) {
                View view = (View) weakReference.get();
                if (view != null) {
                    ViewParent parent = view.getParent();
                    if (parent == null || !parent.isLayoutRequested() || !v.C(view)) {
                        a(view, i2);
                    } else {
                        view.post(new a(view, i2));
                    }
                }
            } else if (i2 == 4 || i2 == 3 || i2 == 6 || (this.f1430j && i2 == 5)) {
                this.l = i2;
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void d(int i2) {
        c cVar;
        if (this.l != i2) {
            this.l = i2;
            if (i2 == 6 || i2 == 3) {
                d(true);
            } else if (i2 == 5 || i2 == 4) {
                d(false);
            }
            View view = (View) this.r.get();
            if (view != null && (cVar = this.t) != null) {
                cVar.a(view, i2);
            }
        }
    }

    public BottomSheetBehavior(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        int i2;
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.BottomSheetBehavior_Layout);
        TypedValue peekValue = obtainStyledAttributes.peekValue(R$styleable.BottomSheetBehavior_Layout_behavior_peekHeight);
        if (peekValue == null || (i2 = peekValue.data) != -1) {
            b(obtainStyledAttributes.getDimensionPixelSize(R$styleable.BottomSheetBehavior_Layout_behavior_peekHeight, -1));
        } else {
            b(i2);
        }
        b(obtainStyledAttributes.getBoolean(R$styleable.BottomSheetBehavior_Layout_behavior_hideable, false));
        a(obtainStyledAttributes.getBoolean(R$styleable.BottomSheetBehavior_Layout_behavior_fitToContents, true));
        c(obtainStyledAttributes.getBoolean(R$styleable.BottomSheetBehavior_Layout_behavior_skipCollapsed, false));
        obtainStyledAttributes.recycle();
        this.b = (float) ViewConfiguration.get(context).getScaledMaximumFlingVelocity();
    }

    public boolean a(CoordinatorLayout coordinatorLayout, V v2, int i2) {
        if (v.l(coordinatorLayout) && !v.l(v2)) {
            v2.setFitsSystemWindows(true);
        }
        int top = v2.getTop();
        coordinatorLayout.c((View) v2, i2);
        this.q = coordinatorLayout.getHeight();
        if (this.d) {
            if (this.e == 0) {
                this.e = coordinatorLayout.getResources().getDimensionPixelSize(R$dimen.design_bottom_sheet_peek_height_min);
            }
            this.f1426f = Math.max(this.e, this.q - ((coordinatorLayout.getWidth() * 9) / 16));
        } else {
            this.f1426f = this.c;
        }
        this.f1427g = Math.max(0, this.q - v2.getHeight());
        this.f1428h = this.q / 2;
        c();
        int i3 = this.l;
        if (i3 == 3) {
            v.e(v2, d());
        } else if (i3 == 6) {
            v.e(v2, this.f1428h);
        } else if (!this.f1430j || i3 != 5) {
            int i4 = this.l;
            if (i4 == 4) {
                v.e(v2, this.f1429i);
            } else if (i4 == 1 || i4 == 2) {
                v.e(v2, top - v2.getTop());
            }
        } else {
            v.e(v2, this.q);
        }
        if (this.m == null) {
            this.m = androidx.customview.a.c.a((ViewGroup) coordinatorLayout, this.z);
        }
        this.r = new WeakReference<>(v2);
        this.s = new WeakReference<>(a((View) v2));
        return true;
    }

    /* access modifiers changed from: private */
    public int d() {
        if (this.a) {
            return this.f1427g;
        }
        return 0;
    }

    private void d(boolean z2) {
        WeakReference<V> weakReference = this.r;
        if (weakReference != null) {
            ViewParent parent = ((View) weakReference.get()).getParent();
            if (parent instanceof CoordinatorLayout) {
                CoordinatorLayout coordinatorLayout = (CoordinatorLayout) parent;
                int childCount = coordinatorLayout.getChildCount();
                if (Build.VERSION.SDK_INT >= 16 && z2) {
                    if (this.y == null) {
                        this.y = new HashMap(childCount);
                    } else {
                        return;
                    }
                }
                for (int i2 = 0; i2 < childCount; i2++) {
                    View childAt = coordinatorLayout.getChildAt(i2);
                    if (childAt != this.r.get()) {
                        if (!z2) {
                            Map<View, Integer> map = this.y;
                            if (map != null && map.containsKey(childAt)) {
                                v.h(childAt, this.y.get(childAt).intValue());
                            }
                        } else {
                            if (Build.VERSION.SDK_INT >= 16) {
                                this.y.put(childAt, Integer.valueOf(childAt.getImportantForAccessibility()));
                            }
                            v.h(childAt, 4);
                        }
                    }
                }
                if (!z2) {
                    this.y = null;
                }
            }
        }
    }

    private void c() {
        if (this.a) {
            this.f1429i = Math.max(this.q - this.f1426f, this.f1427g);
        } else {
            this.f1429i = this.q - this.f1426f;
        }
    }

    public boolean b(CoordinatorLayout coordinatorLayout, V v2, View view, View view2, int i2, int i3) {
        this.o = 0;
        this.p = false;
        if ((i2 & 2) != 0) {
            return true;
        }
        return false;
    }

    /* JADX WARNING: Removed duplicated region for block: B:18:0x0037  */
    /* JADX WARNING: Removed duplicated region for block: B:23:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void b(int r4) {
        /*
            r3 = this;
            r0 = 1
            r1 = 0
            r2 = -1
            if (r4 != r2) goto L_0x000c
            boolean r4 = r3.d
            if (r4 != 0) goto L_0x0015
            r3.d = r0
            goto L_0x0024
        L_0x000c:
            boolean r2 = r3.d
            if (r2 != 0) goto L_0x0017
            int r2 = r3.c
            if (r2 == r4) goto L_0x0015
            goto L_0x0017
        L_0x0015:
            r0 = 0
            goto L_0x0024
        L_0x0017:
            r3.d = r1
            int r1 = java.lang.Math.max(r1, r4)
            r3.c = r1
            int r1 = r3.q
            int r1 = r1 - r4
            r3.f1429i = r1
        L_0x0024:
            if (r0 == 0) goto L_0x003a
            int r4 = r3.l
            r0 = 4
            if (r4 != r0) goto L_0x003a
            java.lang.ref.WeakReference<V> r4 = r3.r
            if (r4 == 0) goto L_0x003a
            java.lang.Object r4 = r4.get()
            android.view.View r4 = (android.view.View) r4
            if (r4 == 0) goto L_0x003a
            r4.requestLayout()
        L_0x003a:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.material.bottomsheet.BottomSheetBehavior.b(int):void");
    }

    public void b(boolean z2) {
        this.f1430j = z2;
    }

    public final int b() {
        return this.l;
    }

    public static <V extends View> BottomSheetBehavior<V> b(V v2) {
        ViewGroup.LayoutParams layoutParams = v2.getLayoutParams();
        if (layoutParams instanceof CoordinatorLayout.f) {
            CoordinatorLayout.c d2 = ((CoordinatorLayout.f) layoutParams).d();
            if (d2 instanceof BottomSheetBehavior) {
                return (BottomSheetBehavior) d2;
            }
            throw new IllegalArgumentException("The view is not associated with BottomSheetBehavior");
        }
        throw new IllegalArgumentException("The view is not a child of CoordinatorLayout");
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r10v12, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v4, resolved type: android.view.View} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean a(androidx.coordinatorlayout.widget.CoordinatorLayout r9, V r10, android.view.MotionEvent r11) {
        /*
            r8 = this;
            boolean r0 = r10.isShown()
            r1 = 0
            r2 = 1
            if (r0 != 0) goto L_0x000b
            r8.n = r2
            return r1
        L_0x000b:
            int r0 = r11.getActionMasked()
            if (r0 != 0) goto L_0x0014
            r8.f()
        L_0x0014:
            android.view.VelocityTracker r3 = r8.u
            if (r3 != 0) goto L_0x001e
            android.view.VelocityTracker r3 = android.view.VelocityTracker.obtain()
            r8.u = r3
        L_0x001e:
            android.view.VelocityTracker r3 = r8.u
            r3.addMovement(r11)
            r3 = 0
            r4 = -1
            if (r0 == 0) goto L_0x0038
            if (r0 == r2) goto L_0x002d
            r10 = 3
            if (r0 == r10) goto L_0x002d
            goto L_0x0077
        L_0x002d:
            r8.x = r1
            r8.v = r4
            boolean r10 = r8.n
            if (r10 == 0) goto L_0x0077
            r8.n = r1
            return r1
        L_0x0038:
            float r5 = r11.getX()
            int r5 = (int) r5
            float r6 = r11.getY()
            int r6 = (int) r6
            r8.w = r6
            java.lang.ref.WeakReference<android.view.View> r6 = r8.s
            if (r6 == 0) goto L_0x004f
            java.lang.Object r6 = r6.get()
            android.view.View r6 = (android.view.View) r6
            goto L_0x0050
        L_0x004f:
            r6 = r3
        L_0x0050:
            if (r6 == 0) goto L_0x0066
            int r7 = r8.w
            boolean r6 = r9.a((android.view.View) r6, (int) r5, (int) r7)
            if (r6 == 0) goto L_0x0066
            int r6 = r11.getActionIndex()
            int r6 = r11.getPointerId(r6)
            r8.v = r6
            r8.x = r2
        L_0x0066:
            int r6 = r8.v
            if (r6 != r4) goto L_0x0074
            int r4 = r8.w
            boolean r10 = r9.a((android.view.View) r10, (int) r5, (int) r4)
            if (r10 != 0) goto L_0x0074
            r10 = 1
            goto L_0x0075
        L_0x0074:
            r10 = 0
        L_0x0075:
            r8.n = r10
        L_0x0077:
            boolean r10 = r8.n
            if (r10 != 0) goto L_0x0086
            androidx.customview.a.c r10 = r8.m
            if (r10 == 0) goto L_0x0086
            boolean r10 = r10.b((android.view.MotionEvent) r11)
            if (r10 == 0) goto L_0x0086
            return r2
        L_0x0086:
            java.lang.ref.WeakReference<android.view.View> r10 = r8.s
            if (r10 == 0) goto L_0x0091
            java.lang.Object r10 = r10.get()
            r3 = r10
            android.view.View r3 = (android.view.View) r3
        L_0x0091:
            r10 = 2
            if (r0 != r10) goto L_0x00ca
            if (r3 == 0) goto L_0x00ca
            boolean r10 = r8.n
            if (r10 != 0) goto L_0x00ca
            int r10 = r8.l
            if (r10 == r2) goto L_0x00ca
            float r10 = r11.getX()
            int r10 = (int) r10
            float r0 = r11.getY()
            int r0 = (int) r0
            boolean r9 = r9.a((android.view.View) r3, (int) r10, (int) r0)
            if (r9 != 0) goto L_0x00ca
            androidx.customview.a.c r9 = r8.m
            if (r9 == 0) goto L_0x00ca
            int r9 = r8.w
            float r9 = (float) r9
            float r10 = r11.getY()
            float r9 = r9 - r10
            float r9 = java.lang.Math.abs(r9)
            androidx.customview.a.c r10 = r8.m
            int r10 = r10.e()
            float r10 = (float) r10
            int r9 = (r9 > r10 ? 1 : (r9 == r10 ? 0 : -1))
            if (r9 <= 0) goto L_0x00ca
            r1 = 1
        L_0x00ca:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.material.bottomsheet.BottomSheetBehavior.a(androidx.coordinatorlayout.widget.CoordinatorLayout, android.view.View, android.view.MotionEvent):boolean");
    }

    public void a(CoordinatorLayout coordinatorLayout, V v2, View view, int i2, int i3, int[] iArr, int i4) {
        if (i4 != 1 && view == ((View) this.s.get())) {
            int top = v2.getTop();
            int i5 = top - i3;
            if (i3 > 0) {
                if (i5 < d()) {
                    iArr[1] = top - d();
                    v.e(v2, -iArr[1]);
                    d(3);
                } else {
                    iArr[1] = i3;
                    v.e(v2, -i3);
                    d(1);
                }
            } else if (i3 < 0 && !view.canScrollVertically(-1)) {
                int i6 = this.f1429i;
                if (i5 <= i6 || this.f1430j) {
                    iArr[1] = i3;
                    v.e(v2, -i3);
                    d(1);
                } else {
                    iArr[1] = top - i6;
                    v.e(v2, -iArr[1]);
                    d(4);
                }
            }
            a(v2.getTop());
            this.o = i3;
            this.p = true;
        }
    }

    public void a(CoordinatorLayout coordinatorLayout, V v2, View view, int i2) {
        int i3;
        int i4;
        int i5 = 3;
        if (v2.getTop() == d()) {
            d(3);
        } else if (view == this.s.get() && this.p) {
            if (this.o > 0) {
                i3 = d();
            } else if (!this.f1430j || !a((View) v2, e())) {
                if (this.o == 0) {
                    int top = v2.getTop();
                    if (!this.a) {
                        int i6 = this.f1428h;
                        if (top < i6) {
                            if (top < Math.abs(top - this.f1429i)) {
                                i3 = 0;
                            } else {
                                i3 = this.f1428h;
                            }
                        } else if (Math.abs(top - i6) < Math.abs(top - this.f1429i)) {
                            i3 = this.f1428h;
                        } else {
                            i4 = this.f1429i;
                        }
                        i5 = 6;
                    } else if (Math.abs(top - this.f1427g) < Math.abs(top - this.f1429i)) {
                        i3 = this.f1427g;
                    } else {
                        i4 = this.f1429i;
                    }
                } else {
                    i4 = this.f1429i;
                }
                i5 = 4;
            } else {
                i3 = this.q;
                i5 = 5;
            }
            if (this.m.b((View) v2, v2.getLeft(), i3)) {
                d(2);
                v.a((View) v2, (Runnable) new d(v2, i5));
            } else {
                d(i5);
            }
            this.p = false;
        }
    }

    public boolean a(CoordinatorLayout coordinatorLayout, V v2, View view, float f2, float f3) {
        return view == this.s.get() && (this.l != 3 || super.a(coordinatorLayout, v2, view, f2, f3));
    }

    public void a(boolean z2) {
        if (this.a != z2) {
            this.a = z2;
            if (this.r != null) {
                c();
            }
            d((!this.a || this.l != 6) ? this.l : 3);
        }
    }

    public void a(c cVar) {
        this.t = cVar;
    }

    /* access modifiers changed from: package-private */
    public boolean a(View view, float f2) {
        if (this.k) {
            return true;
        }
        if (view.getTop() >= this.f1429i && Math.abs((((float) view.getTop()) + (f2 * 0.1f)) - ((float) this.f1429i)) / ((float) this.c) > 0.5f) {
            return true;
        }
        return false;
    }

    /* access modifiers changed from: package-private */
    public View a(View view) {
        if (v.E(view)) {
            return view;
        }
        if (!(view instanceof ViewGroup)) {
            return null;
        }
        ViewGroup viewGroup = (ViewGroup) view;
        int childCount = viewGroup.getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            View a2 = a(viewGroup.getChildAt(i2));
            if (a2 != null) {
                return a2;
            }
        }
        return null;
    }

    /* access modifiers changed from: package-private */
    public void a(View view, int i2) {
        int i3;
        int i4;
        if (i2 == 4) {
            i3 = this.f1429i;
        } else if (i2 == 6) {
            int i5 = this.f1428h;
            if (!this.a || i5 > (i4 = this.f1427g)) {
                i3 = i5;
            } else {
                i3 = i4;
                i2 = 3;
            }
        } else if (i2 == 3) {
            i3 = d();
        } else if (!this.f1430j || i2 != 5) {
            throw new IllegalArgumentException("Illegal state argument: " + i2);
        } else {
            i3 = this.q;
        }
        if (this.m.b(view, view.getLeft(), i3)) {
            d(2);
            v.a(view, (Runnable) new d(view, i2));
            return;
        }
        d(i2);
    }

    /* access modifiers changed from: package-private */
    public void a(int i2) {
        c cVar;
        View view = (View) this.r.get();
        if (view != null && (cVar = this.t) != null) {
            int i3 = this.f1429i;
            if (i2 > i3) {
                cVar.a(view, ((float) (i3 - i2)) / ((float) (this.q - i3)));
            } else {
                cVar.a(view, ((float) (i3 - i2)) / ((float) (i3 - d())));
            }
        }
    }
}
