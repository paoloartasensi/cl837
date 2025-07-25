package androidx.slidingpanelayout.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityEvent;
import androidx.core.h.v;
import androidx.customview.a.c;
import androidx.customview.view.AbsSavedState;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class SlidingPaneLayout extends ViewGroup {
    private boolean A;
    private int e;

    /* renamed from: f  reason: collision with root package name */
    private int f856f;

    /* renamed from: g  reason: collision with root package name */
    private Drawable f857g;

    /* renamed from: h  reason: collision with root package name */
    private Drawable f858h;

    /* renamed from: i  reason: collision with root package name */
    private final int f859i;

    /* renamed from: j  reason: collision with root package name */
    private boolean f860j;
    View k;
    float l;
    private float m;
    int n;
    boolean o;
    private int p;
    private float q;
    private float r;
    private e s;
    final androidx.customview.a.c t;
    boolean u;
    private boolean v;
    private final Rect w;
    final ArrayList<b> x;
    private Method y;
    private Field z;

    static class SavedState extends AbsSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new a();

        /* renamed from: g  reason: collision with root package name */
        boolean f861g;

        static class a implements Parcelable.ClassLoaderCreator<SavedState> {
            a() {
            }

            public SavedState[] newArray(int i2) {
                return new SavedState[i2];
            }

            public SavedState createFromParcel(Parcel parcel, ClassLoader classLoader) {
                return new SavedState(parcel, (ClassLoader) null);
            }

            public SavedState createFromParcel(Parcel parcel) {
                return new SavedState(parcel, (ClassLoader) null);
            }
        }

        SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        public void writeToParcel(Parcel parcel, int i2) {
            super.writeToParcel(parcel, i2);
            parcel.writeInt(this.f861g ? 1 : 0);
        }

        SavedState(Parcel parcel, ClassLoader classLoader) {
            super(parcel, classLoader);
            this.f861g = parcel.readInt() != 0;
        }
    }

    private class b implements Runnable {
        final View e;

        b(View view) {
            this.e = view;
        }

        public void run() {
            if (this.e.getParent() == SlidingPaneLayout.this) {
                this.e.setLayerType(0, (Paint) null);
                SlidingPaneLayout.this.d(this.e);
            }
            SlidingPaneLayout.this.x.remove(this);
        }
    }

    private class c extends c.C0030c {
        c() {
        }

        public void a(View view, int i2) {
            SlidingPaneLayout.this.f();
        }

        public boolean b(View view, int i2) {
            if (SlidingPaneLayout.this.o) {
                return false;
            }
            return ((d) view.getLayoutParams()).b;
        }

        public void c(int i2) {
            if (SlidingPaneLayout.this.t.f() == 0) {
                SlidingPaneLayout slidingPaneLayout = SlidingPaneLayout.this;
                if (slidingPaneLayout.l == 0.0f) {
                    slidingPaneLayout.f(slidingPaneLayout.k);
                    SlidingPaneLayout slidingPaneLayout2 = SlidingPaneLayout.this;
                    slidingPaneLayout2.a(slidingPaneLayout2.k);
                    SlidingPaneLayout.this.u = false;
                    return;
                }
                slidingPaneLayout.b(slidingPaneLayout.k);
                SlidingPaneLayout.this.u = true;
            }
        }

        public void a(View view, int i2, int i3, int i4, int i5) {
            SlidingPaneLayout.this.a(i2);
            SlidingPaneLayout.this.invalidate();
        }

        public int b(View view, int i2, int i3) {
            return view.getTop();
        }

        public void a(View view, float f2, float f3) {
            int i2;
            d dVar = (d) view.getLayoutParams();
            if (SlidingPaneLayout.this.b()) {
                int paddingRight = SlidingPaneLayout.this.getPaddingRight() + dVar.rightMargin;
                if (f2 < 0.0f || (f2 == 0.0f && SlidingPaneLayout.this.l > 0.5f)) {
                    paddingRight += SlidingPaneLayout.this.n;
                }
                i2 = (SlidingPaneLayout.this.getWidth() - paddingRight) - SlidingPaneLayout.this.k.getWidth();
            } else {
                i2 = dVar.leftMargin + SlidingPaneLayout.this.getPaddingLeft();
                if (f2 > 0.0f || (f2 == 0.0f && SlidingPaneLayout.this.l > 0.5f)) {
                    i2 += SlidingPaneLayout.this.n;
                }
            }
            SlidingPaneLayout.this.t.d(i2, view.getTop());
            SlidingPaneLayout.this.invalidate();
        }

        public int a(View view) {
            return SlidingPaneLayout.this.n;
        }

        public int a(View view, int i2, int i3) {
            d dVar = (d) SlidingPaneLayout.this.k.getLayoutParams();
            if (SlidingPaneLayout.this.b()) {
                int width = SlidingPaneLayout.this.getWidth() - ((SlidingPaneLayout.this.getPaddingRight() + dVar.rightMargin) + SlidingPaneLayout.this.k.getWidth());
                return Math.max(Math.min(i2, width), width - SlidingPaneLayout.this.n);
            }
            int paddingLeft = SlidingPaneLayout.this.getPaddingLeft() + dVar.leftMargin;
            return Math.min(Math.max(i2, paddingLeft), SlidingPaneLayout.this.n + paddingLeft);
        }

        public void a(int i2, int i3) {
            SlidingPaneLayout slidingPaneLayout = SlidingPaneLayout.this;
            slidingPaneLayout.t.a(slidingPaneLayout.k, i3);
        }
    }

    public interface e {
        void a(View view);

        void a(View view, float f2);

        void b(View view);
    }

    public SlidingPaneLayout(Context context) {
        this(context, (AttributeSet) null);
    }

    private static boolean g(View view) {
        Drawable background;
        if (view.isOpaque()) {
            return true;
        }
        if (Build.VERSION.SDK_INT < 18 && (background = view.getBackground()) != null && background.getOpacity() == -1) {
            return true;
        }
        return false;
    }

    /* access modifiers changed from: package-private */
    public void a(View view) {
        e eVar = this.s;
        if (eVar != null) {
            eVar.b(view);
        }
        sendAccessibilityEvent(32);
    }

    /* access modifiers changed from: package-private */
    public void b(View view) {
        e eVar = this.s;
        if (eVar != null) {
            eVar.a(view);
        }
        sendAccessibilityEvent(32);
    }

    /* access modifiers changed from: package-private */
    public void c(View view) {
        e eVar = this.s;
        if (eVar != null) {
            eVar.a(view, this.l);
        }
    }

    /* access modifiers changed from: protected */
    public boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return (layoutParams instanceof d) && super.checkLayoutParams(layoutParams);
    }

    public void computeScroll() {
        if (!this.t.a(true)) {
            return;
        }
        if (!this.f860j) {
            this.t.a();
        } else {
            v.H(this);
        }
    }

    public boolean d() {
        return this.f860j;
    }

    public void draw(Canvas canvas) {
        Drawable drawable;
        int i2;
        int i3;
        super.draw(canvas);
        if (b()) {
            drawable = this.f858h;
        } else {
            drawable = this.f857g;
        }
        View childAt = getChildCount() > 1 ? getChildAt(1) : null;
        if (childAt != null && drawable != null) {
            int top = childAt.getTop();
            int bottom = childAt.getBottom();
            int intrinsicWidth = drawable.getIntrinsicWidth();
            if (b()) {
                i3 = childAt.getRight();
                i2 = intrinsicWidth + i3;
            } else {
                int left = childAt.getLeft();
                int i4 = left - intrinsicWidth;
                i2 = left;
                i3 = i4;
            }
            drawable.setBounds(i3, top, i2, bottom);
            drawable.draw(canvas);
        }
    }

    /* access modifiers changed from: protected */
    public boolean drawChild(Canvas canvas, View view, long j2) {
        d dVar = (d) view.getLayoutParams();
        int save = canvas.save();
        if (this.f860j && !dVar.b && this.k != null) {
            canvas.getClipBounds(this.w);
            if (b()) {
                Rect rect = this.w;
                rect.left = Math.max(rect.left, this.k.getRight());
            } else {
                Rect rect2 = this.w;
                rect2.right = Math.min(rect2.right, this.k.getLeft());
            }
            canvas.clipRect(this.w);
        }
        boolean drawChild = super.drawChild(canvas, view, j2);
        canvas.restoreToCount(save);
        return drawChild;
    }

    public boolean e() {
        return b(this.k, 0);
    }

    /* access modifiers changed from: package-private */
    public void f(View view) {
        int i2;
        int i3;
        int i4;
        int i5;
        View childAt;
        boolean z2;
        View view2 = view;
        boolean b2 = b();
        int width = b2 ? getWidth() - getPaddingRight() : getPaddingLeft();
        int paddingLeft = b2 ? getPaddingLeft() : getWidth() - getPaddingRight();
        int paddingTop = getPaddingTop();
        int height = getHeight() - getPaddingBottom();
        if (view2 == null || !g(view)) {
            i5 = 0;
            i4 = 0;
            i3 = 0;
            i2 = 0;
        } else {
            i5 = view.getLeft();
            i4 = view.getRight();
            i3 = view.getTop();
            i2 = view.getBottom();
        }
        int childCount = getChildCount();
        int i6 = 0;
        while (true) {
            if (i6 < childCount && (childAt = getChildAt(i6)) != view2) {
                if (childAt.getVisibility() == 8) {
                    z2 = b2;
                } else {
                    z2 = b2;
                    childAt.setVisibility((Math.max(b2 ? paddingLeft : width, childAt.getLeft()) < i5 || Math.max(paddingTop, childAt.getTop()) < i3 || Math.min(b2 ? width : paddingLeft, childAt.getRight()) > i4 || Math.min(height, childAt.getBottom()) > i2) ? 0 : 4);
                }
                i6++;
                view2 = view;
                b2 = z2;
            } else {
                return;
            }
        }
    }

    /* access modifiers changed from: protected */
    public ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new d();
    }

    /* access modifiers changed from: protected */
    public ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return layoutParams instanceof ViewGroup.MarginLayoutParams ? new d((ViewGroup.MarginLayoutParams) layoutParams) : new d(layoutParams);
    }

    public int getCoveredFadeColor() {
        return this.f856f;
    }

    public int getParallaxDistance() {
        return this.p;
    }

    public int getSliderFadeColor() {
        return this.e;
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.v = true;
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.v = true;
        int size = this.x.size();
        for (int i2 = 0; i2 < size; i2++) {
            this.x.get(i2).run();
        }
        this.x.clear();
    }

    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        boolean z2;
        View childAt;
        int actionMasked = motionEvent.getActionMasked();
        if (!this.f860j && actionMasked == 0 && getChildCount() > 1 && (childAt = getChildAt(1)) != null) {
            this.u = !this.t.a(childAt, (int) motionEvent.getX(), (int) motionEvent.getY());
        }
        if (!this.f860j || (this.o && actionMasked != 0)) {
            this.t.b();
            return super.onInterceptTouchEvent(motionEvent);
        } else if (actionMasked == 3 || actionMasked == 1) {
            this.t.b();
            return false;
        } else {
            if (actionMasked == 0) {
                this.o = false;
                float x2 = motionEvent.getX();
                float y2 = motionEvent.getY();
                this.q = x2;
                this.r = y2;
                if (this.t.a(this.k, (int) x2, (int) y2) && e(this.k)) {
                    z2 = true;
                    if (this.t.b(motionEvent) && !z2) {
                        return false;
                    }
                }
            } else if (actionMasked == 2) {
                float x3 = motionEvent.getX();
                float y3 = motionEvent.getY();
                float abs = Math.abs(x3 - this.q);
                float abs2 = Math.abs(y3 - this.r);
                if (abs > ((float) this.t.e()) && abs2 > abs) {
                    this.t.b();
                    this.o = true;
                    return false;
                }
            }
            z2 = false;
            return this.t.b(motionEvent) ? true : true;
        }
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z2, int i2, int i3, int i4, int i5) {
        int i6;
        int i7;
        int i8;
        int i9;
        boolean b2 = b();
        if (b2) {
            this.t.d(2);
        } else {
            this.t.d(1);
        }
        int i10 = i4 - i2;
        int paddingRight = b2 ? getPaddingRight() : getPaddingLeft();
        int paddingLeft = b2 ? getPaddingLeft() : getPaddingRight();
        int paddingTop = getPaddingTop();
        int childCount = getChildCount();
        if (this.v) {
            this.l = (!this.f860j || !this.u) ? 0.0f : 1.0f;
        }
        int i11 = paddingRight;
        for (int i12 = 0; i12 < childCount; i12++) {
            View childAt = getChildAt(i12);
            if (childAt.getVisibility() != 8) {
                d dVar = (d) childAt.getLayoutParams();
                int measuredWidth = childAt.getMeasuredWidth();
                if (dVar.b) {
                    int i13 = i10 - paddingLeft;
                    int min = (Math.min(paddingRight, i13 - this.f859i) - i11) - (dVar.leftMargin + dVar.rightMargin);
                    this.n = min;
                    int i14 = b2 ? dVar.rightMargin : dVar.leftMargin;
                    dVar.c = ((i11 + i14) + min) + (measuredWidth / 2) > i13;
                    int i15 = (int) (((float) min) * this.l);
                    i11 += i14 + i15;
                    this.l = ((float) i15) / ((float) this.n);
                    i6 = 0;
                } else if (!this.f860j || (i9 = this.p) == 0) {
                    i11 = paddingRight;
                    i6 = 0;
                } else {
                    i6 = (int) ((1.0f - this.l) * ((float) i9));
                    i11 = paddingRight;
                }
                if (b2) {
                    i7 = (i10 - i11) + i6;
                    i8 = i7 - measuredWidth;
                } else {
                    i8 = i11 - i6;
                    i7 = i8 + measuredWidth;
                }
                childAt.layout(i8, paddingTop, i7, childAt.getMeasuredHeight() + paddingTop);
                paddingRight += childAt.getWidth();
            }
        }
        if (this.v) {
            if (this.f860j) {
                if (this.p != 0) {
                    a(this.l);
                }
                if (((d) this.k.getLayoutParams()).c) {
                    a(this.k, this.l, this.e);
                }
            } else {
                for (int i16 = 0; i16 < childCount; i16++) {
                    a(getChildAt(i16), 0.0f, this.e);
                }
            }
            f(this.k);
        }
        this.v = false;
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i2, int i3) {
        int i4;
        int i5;
        int i6;
        int i7;
        int i8;
        int i9;
        int i10;
        int i11;
        float f2;
        int i12;
        int i13;
        int i14;
        int i15;
        int mode = View.MeasureSpec.getMode(i2);
        int size = View.MeasureSpec.getSize(i2);
        int mode2 = View.MeasureSpec.getMode(i3);
        int size2 = View.MeasureSpec.getSize(i3);
        if (mode != 1073741824) {
            if (!isInEditMode()) {
                throw new IllegalStateException("Width must have an exact value or MATCH_PARENT");
            } else if (mode != Integer.MIN_VALUE && mode == 0) {
                size = 300;
            }
        } else if (mode2 == 0) {
            if (!isInEditMode()) {
                throw new IllegalStateException("Height must not be UNSPECIFIED");
            } else if (mode2 == 0) {
                mode2 = Integer.MIN_VALUE;
                size2 = 300;
            }
        }
        boolean z2 = false;
        if (mode2 == Integer.MIN_VALUE) {
            i4 = (size2 - getPaddingTop()) - getPaddingBottom();
            i5 = 0;
        } else if (mode2 != 1073741824) {
            i5 = 0;
            i4 = 0;
        } else {
            i5 = (size2 - getPaddingTop()) - getPaddingBottom();
            i4 = i5;
        }
        int paddingLeft = (size - getPaddingLeft()) - getPaddingRight();
        int childCount = getChildCount();
        if (childCount > 2) {
            Log.e("SlidingPaneLayout", "onMeasure: More than two child views are not supported.");
        }
        this.k = null;
        int i16 = paddingLeft;
        int i17 = 0;
        boolean z3 = false;
        float f3 = 0.0f;
        while (true) {
            i6 = 8;
            if (i17 >= childCount) {
                break;
            }
            View childAt = getChildAt(i17);
            d dVar = (d) childAt.getLayoutParams();
            if (childAt.getVisibility() == 8) {
                dVar.c = z2;
            } else {
                float f4 = dVar.a;
                if (f4 > 0.0f) {
                    f3 += f4;
                    if (dVar.width == 0) {
                    }
                }
                int i18 = dVar.leftMargin + dVar.rightMargin;
                int i19 = dVar.width;
                if (i19 == -2) {
                    i12 = View.MeasureSpec.makeMeasureSpec(paddingLeft - i18, Integer.MIN_VALUE);
                    f2 = f3;
                    i13 = Integer.MIN_VALUE;
                } else {
                    f2 = f3;
                    i13 = Integer.MIN_VALUE;
                    if (i19 == -1) {
                        i12 = View.MeasureSpec.makeMeasureSpec(paddingLeft - i18, 1073741824);
                    } else {
                        i12 = View.MeasureSpec.makeMeasureSpec(i19, 1073741824);
                    }
                }
                int i20 = dVar.height;
                if (i20 == -2) {
                    i14 = View.MeasureSpec.makeMeasureSpec(i4, i13);
                } else {
                    if (i20 == -1) {
                        i15 = View.MeasureSpec.makeMeasureSpec(i4, 1073741824);
                    } else {
                        i15 = View.MeasureSpec.makeMeasureSpec(i20, 1073741824);
                    }
                    i14 = i15;
                }
                childAt.measure(i12, i14);
                int measuredWidth = childAt.getMeasuredWidth();
                int measuredHeight = childAt.getMeasuredHeight();
                if (mode2 == i13 && measuredHeight > i5) {
                    i5 = Math.min(measuredHeight, i4);
                }
                i16 -= measuredWidth;
                boolean z4 = i16 < 0;
                dVar.b = z4;
                z3 |= z4;
                if (z4) {
                    this.k = childAt;
                }
                f3 = f2;
            }
            i17++;
            z2 = false;
        }
        if (z3 || f3 > 0.0f) {
            int i21 = paddingLeft - this.f859i;
            int i22 = 0;
            while (i22 < childCount) {
                View childAt2 = getChildAt(i22);
                if (childAt2.getVisibility() != i6) {
                    d dVar2 = (d) childAt2.getLayoutParams();
                    if (childAt2.getVisibility() != i6) {
                        boolean z5 = dVar2.width == 0 && dVar2.a > 0.0f;
                        if (z5) {
                            i8 = 0;
                        } else {
                            i8 = childAt2.getMeasuredWidth();
                        }
                        if (!z3 || childAt2 == this.k) {
                            if (dVar2.a > 0.0f) {
                                if (dVar2.width == 0) {
                                    int i23 = dVar2.height;
                                    if (i23 == -2) {
                                        i9 = View.MeasureSpec.makeMeasureSpec(i4, Integer.MIN_VALUE);
                                    } else if (i23 == -1) {
                                        i9 = View.MeasureSpec.makeMeasureSpec(i4, 1073741824);
                                    } else {
                                        i9 = View.MeasureSpec.makeMeasureSpec(i23, 1073741824);
                                    }
                                } else {
                                    i9 = View.MeasureSpec.makeMeasureSpec(childAt2.getMeasuredHeight(), 1073741824);
                                }
                                if (z3) {
                                    int i24 = paddingLeft - (dVar2.leftMargin + dVar2.rightMargin);
                                    i7 = i21;
                                    int makeMeasureSpec = View.MeasureSpec.makeMeasureSpec(i24, 1073741824);
                                    if (i8 != i24) {
                                        childAt2.measure(makeMeasureSpec, i9);
                                    }
                                    i22++;
                                    i21 = i7;
                                    i6 = 8;
                                } else {
                                    i7 = i21;
                                    childAt2.measure(View.MeasureSpec.makeMeasureSpec(i8 + ((int) ((dVar2.a * ((float) Math.max(0, i16))) / f3)), 1073741824), i9);
                                    i22++;
                                    i21 = i7;
                                    i6 = 8;
                                }
                            }
                        } else if (dVar2.width < 0 && (i8 > i21 || dVar2.a > 0.0f)) {
                            if (z5) {
                                int i25 = dVar2.height;
                                if (i25 == -2) {
                                    i11 = View.MeasureSpec.makeMeasureSpec(i4, Integer.MIN_VALUE);
                                    i10 = 1073741824;
                                } else if (i25 == -1) {
                                    i10 = 1073741824;
                                    i11 = View.MeasureSpec.makeMeasureSpec(i4, 1073741824);
                                } else {
                                    i10 = 1073741824;
                                    i11 = View.MeasureSpec.makeMeasureSpec(i25, 1073741824);
                                }
                            } else {
                                i10 = 1073741824;
                                i11 = View.MeasureSpec.makeMeasureSpec(childAt2.getMeasuredHeight(), 1073741824);
                            }
                            childAt2.measure(View.MeasureSpec.makeMeasureSpec(i21, i10), i11);
                        }
                    }
                }
                i7 = i21;
                i22++;
                i21 = i7;
                i6 = 8;
            }
        }
        setMeasuredDimension(size, i5 + getPaddingTop() + getPaddingBottom());
        this.f860j = z3;
        if (this.t.f() != 0 && !z3) {
            this.t.a();
        }
    }

    /* access modifiers changed from: protected */
    public void onRestoreInstanceState(Parcelable parcelable) {
        if (!(parcelable instanceof SavedState)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        SavedState savedState = (SavedState) parcelable;
        super.onRestoreInstanceState(savedState.a());
        if (savedState.f861g) {
            e();
        } else {
            a();
        }
        this.u = savedState.f861g;
    }

    /* access modifiers changed from: protected */
    public Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        savedState.f861g = d() ? c() : this.u;
        return savedState;
    }

    /* access modifiers changed from: protected */
    public void onSizeChanged(int i2, int i3, int i4, int i5) {
        super.onSizeChanged(i2, i3, i4, i5);
        if (i2 != i4) {
            this.v = true;
        }
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (!this.f860j) {
            return super.onTouchEvent(motionEvent);
        }
        this.t.a(motionEvent);
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 0) {
            float x2 = motionEvent.getX();
            float y2 = motionEvent.getY();
            this.q = x2;
            this.r = y2;
        } else if (actionMasked == 1 && e(this.k)) {
            float x3 = motionEvent.getX();
            float y3 = motionEvent.getY();
            float f2 = x3 - this.q;
            float f3 = y3 - this.r;
            int e2 = this.t.e();
            if ((f2 * f2) + (f3 * f3) < ((float) (e2 * e2)) && this.t.a(this.k, (int) x3, (int) y3)) {
                a(this.k, 0);
            }
        }
        return true;
    }

    public void requestChildFocus(View view, View view2) {
        super.requestChildFocus(view, view2);
        if (!isInTouchMode() && !this.f860j) {
            this.u = view == this.k;
        }
    }

    public void setCoveredFadeColor(int i2) {
        this.f856f = i2;
    }

    public void setPanelSlideListener(e eVar) {
        this.s = eVar;
    }

    public void setParallaxDistance(int i2) {
        this.p = i2;
        requestLayout();
    }

    @Deprecated
    public void setShadowDrawable(Drawable drawable) {
        setShadowDrawableLeft(drawable);
    }

    public void setShadowDrawableLeft(Drawable drawable) {
        this.f857g = drawable;
    }

    public void setShadowDrawableRight(Drawable drawable) {
        this.f858h = drawable;
    }

    @Deprecated
    public void setShadowResource(int i2) {
        setShadowDrawable(getResources().getDrawable(i2));
    }

    public void setShadowResourceLeft(int i2) {
        setShadowDrawableLeft(androidx.core.content.a.c(getContext(), i2));
    }

    public void setShadowResourceRight(int i2) {
        setShadowDrawableRight(androidx.core.content.a.c(getContext(), i2));
    }

    public void setSliderFadeColor(int i2) {
        this.e = i2;
    }

    public static class d extends ViewGroup.MarginLayoutParams {
        private static final int[] e = {16843137};
        public float a = 0.0f;
        boolean b;
        boolean c;
        Paint d;

        public d() {
            super(-1, -1);
        }

        public d(ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
        }

        public d(ViewGroup.MarginLayoutParams marginLayoutParams) {
            super(marginLayoutParams);
        }

        public d(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, e);
            this.a = obtainStyledAttributes.getFloat(0, 0.0f);
            obtainStyledAttributes.recycle();
        }
    }

    public SlidingPaneLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    /* access modifiers changed from: package-private */
    public void d(View view) {
        Field field;
        int i2 = Build.VERSION.SDK_INT;
        if (i2 >= 17) {
            v.a(view, ((d) view.getLayoutParams()).d);
            return;
        }
        if (i2 >= 16) {
            if (!this.A) {
                try {
                    this.y = View.class.getDeclaredMethod("getDisplayList", (Class[]) null);
                } catch (NoSuchMethodException e2) {
                    Log.e("SlidingPaneLayout", "Couldn't fetch getDisplayList method; dimming won't work right.", e2);
                }
                try {
                    Field declaredField = View.class.getDeclaredField("mRecreateDisplayList");
                    this.z = declaredField;
                    declaredField.setAccessible(true);
                } catch (NoSuchFieldException e3) {
                    Log.e("SlidingPaneLayout", "Couldn't fetch mRecreateDisplayList field; dimming will be slow.", e3);
                }
                this.A = true;
            }
            if (this.y == null || (field = this.z) == null) {
                view.invalidate();
                return;
            }
            try {
                field.setBoolean(view, true);
                this.y.invoke(view, (Object[]) null);
            } catch (Exception e4) {
                Log.e("SlidingPaneLayout", "Error refreshing display list state", e4);
            }
        }
        v.a(this, view.getLeft(), view.getTop(), view.getRight(), view.getBottom());
    }

    /* access modifiers changed from: package-private */
    public boolean e(View view) {
        if (view == null) {
            return false;
        }
        d dVar = (d) view.getLayoutParams();
        if (!this.f860j || !dVar.c || this.l <= 0.0f) {
            return false;
        }
        return true;
    }

    public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new d(getContext(), attributeSet);
    }

    public SlidingPaneLayout(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.e = -858993460;
        this.v = true;
        this.w = new Rect();
        this.x = new ArrayList<>();
        float f2 = context.getResources().getDisplayMetrics().density;
        this.f859i = (int) ((32.0f * f2) + 0.5f);
        setWillNotDraw(false);
        v.a((View) this, (androidx.core.h.a) new a());
        v.h(this, 1);
        androidx.customview.a.c a2 = androidx.customview.a.c.a((ViewGroup) this, 0.5f, (c.C0030c) new c());
        this.t = a2;
        a2.a(f2 * 400.0f);
    }

    public boolean c() {
        return !this.f860j || this.l == 1.0f;
    }

    private boolean a(View view, int i2) {
        if (!this.v && !a(0.0f, i2)) {
            return false;
        }
        this.u = false;
        return true;
    }

    private boolean b(View view, int i2) {
        if (!this.v && !a(1.0f, i2)) {
            return false;
        }
        this.u = true;
        return true;
    }

    public boolean a() {
        return a(this.k, 0);
    }

    /* access modifiers changed from: package-private */
    public boolean b() {
        return v.o(this) == 1;
    }

    /* access modifiers changed from: package-private */
    public void a(int i2) {
        if (this.k == null) {
            this.l = 0.0f;
            return;
        }
        boolean b2 = b();
        d dVar = (d) this.k.getLayoutParams();
        int width = this.k.getWidth();
        if (b2) {
            i2 = (getWidth() - i2) - width;
        }
        float paddingRight = ((float) (i2 - ((b2 ? getPaddingRight() : getPaddingLeft()) + (b2 ? dVar.rightMargin : dVar.leftMargin)))) / ((float) this.n);
        this.l = paddingRight;
        if (this.p != 0) {
            a(paddingRight);
        }
        if (dVar.c) {
            a(this.k, this.l, this.e);
        }
        c(this.k);
    }

    class a extends androidx.core.h.a {
        private final Rect d = new Rect();

        a() {
        }

        public void a(View view, androidx.core.h.e0.d dVar) {
            androidx.core.h.e0.d a = androidx.core.h.e0.d.a(dVar);
            super.a(view, a);
            a(dVar, a);
            a.w();
            dVar.a((CharSequence) SlidingPaneLayout.class.getName());
            dVar.c(view);
            ViewParent u = v.u(view);
            if (u instanceof View) {
                dVar.b((View) u);
            }
            int childCount = SlidingPaneLayout.this.getChildCount();
            for (int i2 = 0; i2 < childCount; i2++) {
                View childAt = SlidingPaneLayout.this.getChildAt(i2);
                if (!c(childAt) && childAt.getVisibility() == 0) {
                    v.h(childAt, 1);
                    dVar.a(childAt);
                }
            }
        }

        public void b(View view, AccessibilityEvent accessibilityEvent) {
            super.b(view, accessibilityEvent);
            accessibilityEvent.setClassName(SlidingPaneLayout.class.getName());
        }

        public boolean c(View view) {
            return SlidingPaneLayout.this.e(view);
        }

        public boolean a(ViewGroup viewGroup, View view, AccessibilityEvent accessibilityEvent) {
            if (!c(view)) {
                return super.a(viewGroup, view, accessibilityEvent);
            }
            return false;
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
            dVar.b(dVar2.g());
        }
    }

    private void a(View view, float f2, int i2) {
        d dVar = (d) view.getLayoutParams();
        if (f2 > 0.0f && i2 != 0) {
            int i3 = (((int) (((float) ((-16777216 & i2) >>> 24)) * f2)) << 24) | (i2 & 16777215);
            if (dVar.d == null) {
                dVar.d = new Paint();
            }
            dVar.d.setColorFilter(new PorterDuffColorFilter(i3, PorterDuff.Mode.SRC_OVER));
            if (view.getLayerType() != 2) {
                view.setLayerType(2, dVar.d);
            }
            d(view);
        } else if (view.getLayerType() != 0) {
            Paint paint = dVar.d;
            if (paint != null) {
                paint.setColorFilter((ColorFilter) null);
            }
            b bVar = new b(view);
            this.x.add(bVar);
            v.a((View) this, (Runnable) bVar);
        }
    }

    /* access modifiers changed from: package-private */
    public void f() {
        int childCount = getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            View childAt = getChildAt(i2);
            if (childAt.getVisibility() == 4) {
                childAt.setVisibility(0);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public boolean a(float f2, int i2) {
        int i3;
        if (!this.f860j) {
            return false;
        }
        boolean b2 = b();
        d dVar = (d) this.k.getLayoutParams();
        if (b2) {
            i3 = (int) (((float) getWidth()) - ((((float) (getPaddingRight() + dVar.rightMargin)) + (f2 * ((float) this.n))) + ((float) this.k.getWidth())));
        } else {
            i3 = (int) (((float) (getPaddingLeft() + dVar.leftMargin)) + (f2 * ((float) this.n)));
        }
        androidx.customview.a.c cVar = this.t;
        View view = this.k;
        if (!cVar.b(view, i3, view.getTop())) {
            return false;
        }
        f();
        v.H(this);
        return true;
    }

    /* JADX WARNING: Removed duplicated region for block: B:10:0x0023  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void a(float r10) {
        /*
            r9 = this;
            boolean r0 = r9.b()
            android.view.View r1 = r9.k
            android.view.ViewGroup$LayoutParams r1 = r1.getLayoutParams()
            androidx.slidingpanelayout.widget.SlidingPaneLayout$d r1 = (androidx.slidingpanelayout.widget.SlidingPaneLayout.d) r1
            boolean r2 = r1.c
            r3 = 0
            if (r2 == 0) goto L_0x001c
            if (r0 == 0) goto L_0x0016
            int r1 = r1.rightMargin
            goto L_0x0018
        L_0x0016:
            int r1 = r1.leftMargin
        L_0x0018:
            if (r1 > 0) goto L_0x001c
            r1 = 1
            goto L_0x001d
        L_0x001c:
            r1 = 0
        L_0x001d:
            int r2 = r9.getChildCount()
        L_0x0021:
            if (r3 >= r2) goto L_0x0059
            android.view.View r4 = r9.getChildAt(r3)
            android.view.View r5 = r9.k
            if (r4 != r5) goto L_0x002c
            goto L_0x0056
        L_0x002c:
            float r5 = r9.m
            r6 = 1065353216(0x3f800000, float:1.0)
            float r5 = r6 - r5
            int r7 = r9.p
            float r8 = (float) r7
            float r5 = r5 * r8
            int r5 = (int) r5
            r9.m = r10
            float r8 = r6 - r10
            float r7 = (float) r7
            float r8 = r8 * r7
            int r7 = (int) r8
            int r5 = r5 - r7
            if (r0 == 0) goto L_0x0044
            int r5 = -r5
        L_0x0044:
            r4.offsetLeftAndRight(r5)
            if (r1 == 0) goto L_0x0056
            float r5 = r9.m
            if (r0 == 0) goto L_0x004f
            float r5 = r5 - r6
            goto L_0x0051
        L_0x004f:
            float r5 = r6 - r5
        L_0x0051:
            int r6 = r9.f856f
            r9.a(r4, r5, r6)
        L_0x0056:
            int r3 = r3 + 1
            goto L_0x0021
        L_0x0059:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.slidingpanelayout.widget.SlidingPaneLayout.a(float):void");
    }
}
