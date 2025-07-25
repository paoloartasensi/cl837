package androidx.viewpager2.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import androidx.core.h.e0.d;
import androidx.core.h.e0.g;
import androidx.core.h.v;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.r;
import androidx.viewpager2.R$styleable;

public final class ViewPager2 extends ViewGroup {
    static boolean y = true;
    private final Rect e = new Rect();

    /* renamed from: f  reason: collision with root package name */
    private final Rect f965f = new Rect();

    /* renamed from: g  reason: collision with root package name */
    private b f966g = new b(3);

    /* renamed from: h  reason: collision with root package name */
    int f967h;

    /* renamed from: i  reason: collision with root package name */
    boolean f968i = false;

    /* renamed from: j  reason: collision with root package name */
    private RecyclerView.i f969j = new a();
    private LinearLayoutManager k;
    private int l = -1;
    private Parcelable m;
    RecyclerView n;
    private r o;
    e p;
    private b q;
    private c r;
    private d s;
    private RecyclerView.l t = null;
    private boolean u = false;
    private boolean v = true;
    private int w = -1;
    e x;

    class a extends g {
        a() {
            super((a) null);
        }

        public void a() {
            ViewPager2 viewPager2 = ViewPager2.this;
            viewPager2.f968i = true;
            viewPager2.p.e();
        }
    }

    class b extends i {
        b() {
        }

        public void a(int i2) {
            if (i2 == 0) {
                ViewPager2.this.e();
            }
        }

        public void b(int i2) {
            ViewPager2 viewPager2 = ViewPager2.this;
            if (viewPager2.f967h != i2) {
                viewPager2.f967h = i2;
                viewPager2.x.g();
            }
        }
    }

    class c extends i {
        c() {
        }

        public void b(int i2) {
            ViewPager2.this.clearFocus();
            if (ViewPager2.this.hasFocus()) {
                ViewPager2.this.n.requestFocus(2);
            }
        }
    }

    class d implements RecyclerView.q {
        d(ViewPager2 viewPager2) {
        }

        public void a(View view) {
            RecyclerView.p pVar = (RecyclerView.p) view.getLayoutParams();
            if (pVar.width != -1 || pVar.height != -1) {
                throw new IllegalStateException("Pages must fill the whole ViewPager2 (use match_parent)");
            }
        }

        public void b(View view) {
        }
    }

    private abstract class e {
        private e(ViewPager2 viewPager2) {
        }

        /* access modifiers changed from: package-private */
        public void a(AccessibilityEvent accessibilityEvent) {
        }

        /* access modifiers changed from: package-private */
        public void a(AccessibilityNodeInfo accessibilityNodeInfo) {
        }

        /* access modifiers changed from: package-private */
        public void a(androidx.core.h.e0.d dVar) {
        }

        /* access modifiers changed from: package-private */
        public void a(RecyclerView.g<?> gVar) {
        }

        /* access modifiers changed from: package-private */
        public void a(b bVar, RecyclerView recyclerView) {
        }

        /* access modifiers changed from: package-private */
        public boolean a() {
            return false;
        }

        /* access modifiers changed from: package-private */
        public boolean a(int i2) {
            return false;
        }

        /* access modifiers changed from: package-private */
        public boolean a(int i2, Bundle bundle) {
            return false;
        }

        /* access modifiers changed from: package-private */
        public void b(RecyclerView.g<?> gVar) {
        }

        /* access modifiers changed from: package-private */
        public boolean b() {
            return false;
        }

        /* access modifiers changed from: package-private */
        public boolean b(int i2, Bundle bundle) {
            throw new IllegalStateException("Not implemented.");
        }

        /* access modifiers changed from: package-private */
        public String c() {
            throw new IllegalStateException("Not implemented.");
        }

        /* access modifiers changed from: package-private */
        public void d() {
        }

        /* access modifiers changed from: package-private */
        public CharSequence e() {
            throw new IllegalStateException("Not implemented.");
        }

        /* access modifiers changed from: package-private */
        public void f() {
        }

        /* access modifiers changed from: package-private */
        public void g() {
        }

        /* access modifiers changed from: package-private */
        public void h() {
        }

        /* access modifiers changed from: package-private */
        public void i() {
        }

        /* synthetic */ e(ViewPager2 viewPager2, a aVar) {
            this(viewPager2);
        }

        /* access modifiers changed from: package-private */
        public boolean b(int i2) {
            throw new IllegalStateException("Not implemented.");
        }
    }

    private static abstract class g extends RecyclerView.i {
        private g() {
        }

        public final void a(int i2, int i3) {
            a();
        }

        public final void b(int i2, int i3) {
            a();
        }

        public final void c(int i2, int i3) {
            a();
        }

        /* synthetic */ g(a aVar) {
            this();
        }

        public final void a(int i2, int i3, Object obj) {
            a();
        }

        public final void a(int i2, int i3, int i4) {
            a();
        }
    }

    public static abstract class i {
        public void a(int i2) {
        }

        public void a(int i2, float f2, int i3) {
        }

        public void b(int i2) {
        }
    }

    class j extends e {
        private final androidx.core.h.e0.g a = new a();
        private final androidx.core.h.e0.g b = new b();
        private RecyclerView.i c;

        class a implements androidx.core.h.e0.g {
            a() {
            }

            public boolean a(View view, g.a aVar) {
                j.this.c(((ViewPager2) view).getCurrentItem() + 1);
                return true;
            }
        }

        class b implements androidx.core.h.e0.g {
            b() {
            }

            public boolean a(View view, g.a aVar) {
                j.this.c(((ViewPager2) view).getCurrentItem() - 1);
                return true;
            }
        }

        class c extends g {
            c() {
                super((a) null);
            }

            public void a() {
                j.this.j();
            }
        }

        j() {
            super(ViewPager2.this, (a) null);
        }

        public void a(b bVar, RecyclerView recyclerView) {
            v.h(recyclerView, 2);
            this.c = new c();
            if (v.m(ViewPager2.this) == 0) {
                v.h(ViewPager2.this, 1);
            }
        }

        public boolean a() {
            return true;
        }

        public boolean a(int i2, Bundle bundle) {
            return i2 == 8192 || i2 == 4096;
        }

        public void b(RecyclerView.g<?> gVar) {
            if (gVar != null) {
                gVar.unregisterAdapterDataObserver(this.c);
            }
        }

        public String c() {
            if (a()) {
                return "androidx.viewpager.widget.ViewPager";
            }
            throw new IllegalStateException();
        }

        public void d() {
            j();
        }

        public void f() {
            j();
        }

        public void g() {
            j();
        }

        public void h() {
            j();
        }

        public void i() {
            j();
            if (Build.VERSION.SDK_INT < 21) {
                ViewPager2.this.sendAccessibilityEvent(2048);
            }
        }

        /* access modifiers changed from: package-private */
        public void j() {
            int itemCount;
            ViewPager2 viewPager2 = ViewPager2.this;
            int i2 = 16908360;
            v.f(viewPager2, 16908360);
            v.f(viewPager2, 16908361);
            v.f(viewPager2, 16908358);
            v.f(viewPager2, 16908359);
            if (ViewPager2.this.getAdapter() != null && (itemCount = ViewPager2.this.getAdapter().getItemCount()) != 0 && ViewPager2.this.c()) {
                if (ViewPager2.this.getOrientation() == 0) {
                    boolean b2 = ViewPager2.this.b();
                    int i3 = b2 ? 16908360 : 16908361;
                    if (b2) {
                        i2 = 16908361;
                    }
                    if (ViewPager2.this.f967h < itemCount - 1) {
                        v.a(viewPager2, new d.a(i3, (CharSequence) null), (CharSequence) null, this.a);
                    }
                    if (ViewPager2.this.f967h > 0) {
                        v.a(viewPager2, new d.a(i2, (CharSequence) null), (CharSequence) null, this.b);
                        return;
                    }
                    return;
                }
                if (ViewPager2.this.f967h < itemCount - 1) {
                    v.a(viewPager2, new d.a(16908359, (CharSequence) null), (CharSequence) null, this.a);
                }
                if (ViewPager2.this.f967h > 0) {
                    v.a(viewPager2, new d.a(16908358, (CharSequence) null), (CharSequence) null, this.b);
                }
            }
        }

        public boolean b(int i2, Bundle bundle) {
            int i3;
            if (a(i2, bundle)) {
                if (i2 == 8192) {
                    i3 = ViewPager2.this.getCurrentItem() - 1;
                } else {
                    i3 = ViewPager2.this.getCurrentItem() + 1;
                }
                c(i3);
                return true;
            }
            throw new IllegalStateException();
        }

        /* access modifiers changed from: package-private */
        public void c(int i2) {
            if (ViewPager2.this.c()) {
                ViewPager2.this.b(i2, true);
            }
        }

        private void c(AccessibilityNodeInfo accessibilityNodeInfo) {
            int itemCount;
            RecyclerView.g adapter = ViewPager2.this.getAdapter();
            if (adapter != null && (itemCount = adapter.getItemCount()) != 0 && ViewPager2.this.c()) {
                if (ViewPager2.this.f967h > 0) {
                    accessibilityNodeInfo.addAction(8192);
                }
                if (ViewPager2.this.f967h < itemCount - 1) {
                    accessibilityNodeInfo.addAction(4096);
                }
                accessibilityNodeInfo.setScrollable(true);
            }
        }

        public void a(RecyclerView.g<?> gVar) {
            j();
            if (gVar != null) {
                gVar.registerAdapterDataObserver(this.c);
            }
        }

        public void a(AccessibilityNodeInfo accessibilityNodeInfo) {
            b(accessibilityNodeInfo);
            if (Build.VERSION.SDK_INT >= 16) {
                c(accessibilityNodeInfo);
            }
        }

        private void b(AccessibilityNodeInfo accessibilityNodeInfo) {
            int i2;
            int i3;
            if (ViewPager2.this.getAdapter() == null) {
                i3 = 0;
            } else if (ViewPager2.this.getOrientation() == 1) {
                i3 = ViewPager2.this.getAdapter().getItemCount();
            } else {
                i2 = ViewPager2.this.getAdapter().getItemCount();
                i3 = 0;
                androidx.core.h.e0.d.a(accessibilityNodeInfo).a((Object) d.b.a(i3, i2, false, 0));
            }
            i2 = 0;
            androidx.core.h.e0.d.a(accessibilityNodeInfo).a((Object) d.b.a(i3, i2, false, 0));
        }

        public void a(AccessibilityEvent accessibilityEvent) {
            accessibilityEvent.setSource(ViewPager2.this);
            accessibilityEvent.setClassName(c());
        }
    }

    public interface k {
        void a(View view, float f2);
    }

    private class l extends r {
        l() {
        }

        public View c(RecyclerView.o oVar) {
            if (ViewPager2.this.a()) {
                return null;
            }
            return super.c(oVar);
        }
    }

    private class m extends RecyclerView {
        m(Context context) {
            super(context);
        }

        public CharSequence getAccessibilityClassName() {
            if (ViewPager2.this.x.b()) {
                return ViewPager2.this.x.e();
            }
            return super.getAccessibilityClassName();
        }

        public void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
            super.onInitializeAccessibilityEvent(accessibilityEvent);
            accessibilityEvent.setFromIndex(ViewPager2.this.f967h);
            accessibilityEvent.setToIndex(ViewPager2.this.f967h);
            ViewPager2.this.x.a(accessibilityEvent);
        }

        public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
            return ViewPager2.this.c() && super.onInterceptTouchEvent(motionEvent);
        }

        @SuppressLint({"ClickableViewAccessibility"})
        public boolean onTouchEvent(MotionEvent motionEvent) {
            return ViewPager2.this.c() && super.onTouchEvent(motionEvent);
        }
    }

    private static class n implements Runnable {
        private final int e;

        /* renamed from: f  reason: collision with root package name */
        private final RecyclerView f972f;

        n(int i2, RecyclerView recyclerView) {
            this.e = i2;
            this.f972f = recyclerView;
        }

        public void run() {
            this.f972f.j(this.e);
        }
    }

    public ViewPager2(Context context) {
        super(context);
        a(context, (AttributeSet) null);
    }

    private void a(Context context, AttributeSet attributeSet) {
        this.x = y ? new j() : new f();
        m mVar = new m(context);
        this.n = mVar;
        mVar.setId(v.b());
        this.n.setDescendantFocusability(131072);
        h hVar = new h(context);
        this.k = hVar;
        this.n.setLayoutManager(hVar);
        this.n.setScrollingTouchSlop(1);
        b(context, attributeSet);
        this.n.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
        this.n.addOnChildAttachStateChangeListener(f());
        e eVar = new e(this);
        this.p = eVar;
        this.r = new c(this, eVar, this.n);
        l lVar = new l();
        this.o = lVar;
        lVar.a(this.n);
        this.n.addOnScrollListener(this.p);
        b bVar = new b(3);
        this.q = bVar;
        this.p.a((i) bVar);
        b bVar2 = new b();
        c cVar = new c();
        this.q.a((i) bVar2);
        this.q.a((i) cVar);
        this.x.a(this.q, this.n);
        this.q.a((i) this.f966g);
        d dVar = new d(this.k);
        this.s = dVar;
        this.q.a((i) dVar);
        RecyclerView recyclerView = this.n;
        attachViewToParent(recyclerView, 0, recyclerView.getLayoutParams());
    }

    private void b(Context context, AttributeSet attributeSet) {
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.ViewPager2);
        if (Build.VERSION.SDK_INT >= 29) {
            saveAttributeDataForStyleable(context, R$styleable.ViewPager2, attributeSet, obtainStyledAttributes, 0, 0);
        }
        try {
            setOrientation(obtainStyledAttributes.getInt(R$styleable.ViewPager2_android_orientation, 0));
        } finally {
            obtainStyledAttributes.recycle();
        }
    }

    private RecyclerView.q f() {
        return new d(this);
    }

    private void g() {
        RecyclerView.g adapter;
        if (this.l != -1 && (adapter = getAdapter()) != null) {
            Parcelable parcelable = this.m;
            if (parcelable != null) {
                if (adapter instanceof androidx.viewpager2.adapter.b) {
                    ((androidx.viewpager2.adapter.b) adapter).a(parcelable);
                }
                this.m = null;
            }
            int max = Math.max(0, Math.min(this.l, adapter.getItemCount() - 1));
            this.f967h = max;
            this.l = -1;
            this.n.i(max);
            this.x.d();
        }
    }

    public boolean c() {
        return this.v;
    }

    public boolean canScrollHorizontally(int i2) {
        return this.n.canScrollHorizontally(i2);
    }

    public boolean canScrollVertically(int i2) {
        return this.n.canScrollVertically(i2);
    }

    public void d() {
        if (this.s.a() != null) {
            double a2 = this.p.a();
            int i2 = (int) a2;
            double d2 = (double) i2;
            Double.isNaN(d2);
            float f2 = (float) (a2 - d2);
            this.s.a(i2, f2, Math.round(((float) getPageSize()) * f2));
        }
    }

    /* access modifiers changed from: protected */
    public void dispatchRestoreInstanceState(SparseArray<Parcelable> sparseArray) {
        Parcelable parcelable = sparseArray.get(getId());
        if (parcelable instanceof SavedState) {
            int i2 = ((SavedState) parcelable).e;
            sparseArray.put(this.n.getId(), sparseArray.get(i2));
            sparseArray.remove(i2);
        }
        super.dispatchRestoreInstanceState(sparseArray);
        g();
    }

    /* access modifiers changed from: package-private */
    public void e() {
        r rVar = this.o;
        if (rVar != null) {
            View c2 = rVar.c(this.k);
            if (c2 != null) {
                int l2 = this.k.l(c2);
                if (l2 != this.f967h && getScrollState() == 0) {
                    this.q.b(l2);
                }
                this.f968i = false;
                return;
            }
            return;
        }
        throw new IllegalStateException("Design assumption violated.");
    }

    public CharSequence getAccessibilityClassName() {
        if (this.x.a()) {
            return this.x.c();
        }
        return super.getAccessibilityClassName();
    }

    public RecyclerView.g getAdapter() {
        return this.n.getAdapter();
    }

    public int getCurrentItem() {
        return this.f967h;
    }

    public int getItemDecorationCount() {
        return this.n.getItemDecorationCount();
    }

    public int getOffscreenPageLimit() {
        return this.w;
    }

    public int getOrientation() {
        return this.k.K();
    }

    /* access modifiers changed from: package-private */
    public int getPageSize() {
        int i2;
        int i3;
        RecyclerView recyclerView = this.n;
        if (getOrientation() == 0) {
            i2 = recyclerView.getWidth() - recyclerView.getPaddingLeft();
            i3 = recyclerView.getPaddingRight();
        } else {
            i2 = recyclerView.getHeight() - recyclerView.getPaddingTop();
            i3 = recyclerView.getPaddingBottom();
        }
        return i2 - i3;
    }

    public int getScrollState() {
        return this.p.b();
    }

    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        this.x.a(accessibilityNodeInfo);
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i2, int i3, int i4, int i5) {
        int measuredWidth = this.n.getMeasuredWidth();
        int measuredHeight = this.n.getMeasuredHeight();
        this.e.left = getPaddingLeft();
        this.e.right = (i4 - i2) - getPaddingRight();
        this.e.top = getPaddingTop();
        this.e.bottom = (i5 - i3) - getPaddingBottom();
        Gravity.apply(8388659, measuredWidth, measuredHeight, this.e, this.f965f);
        RecyclerView recyclerView = this.n;
        Rect rect = this.f965f;
        recyclerView.layout(rect.left, rect.top, rect.right, rect.bottom);
        if (this.f968i) {
            e();
        }
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i2, int i3) {
        measureChild(this.n, i2, i3);
        int measuredWidth = this.n.getMeasuredWidth();
        int measuredHeight = this.n.getMeasuredHeight();
        int measuredState = this.n.getMeasuredState();
        int paddingLeft = measuredWidth + getPaddingLeft() + getPaddingRight();
        int paddingTop = measuredHeight + getPaddingTop() + getPaddingBottom();
        setMeasuredDimension(ViewGroup.resolveSizeAndState(Math.max(paddingLeft, getSuggestedMinimumWidth()), i2, measuredState), ViewGroup.resolveSizeAndState(Math.max(paddingTop, getSuggestedMinimumHeight()), i3, measuredState << 16));
    }

    /* access modifiers changed from: protected */
    public void onRestoreInstanceState(Parcelable parcelable) {
        if (!(parcelable instanceof SavedState)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        SavedState savedState = (SavedState) parcelable;
        super.onRestoreInstanceState(savedState.getSuperState());
        this.l = savedState.f970f;
        this.m = savedState.f971g;
    }

    /* access modifiers changed from: protected */
    public Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        savedState.e = this.n.getId();
        int i2 = this.l;
        if (i2 == -1) {
            i2 = this.f967h;
        }
        savedState.f970f = i2;
        Parcelable parcelable = this.m;
        if (parcelable != null) {
            savedState.f971g = parcelable;
        } else {
            RecyclerView.g adapter = this.n.getAdapter();
            if (adapter instanceof androidx.viewpager2.adapter.b) {
                savedState.f971g = ((androidx.viewpager2.adapter.b) adapter).saveState();
            }
        }
        return savedState;
    }

    public void onViewAdded(View view) {
        throw new IllegalStateException(ViewPager2.class.getSimpleName() + " does not support direct child views");
    }

    public boolean performAccessibilityAction(int i2, Bundle bundle) {
        if (this.x.a(i2, bundle)) {
            return this.x.b(i2, bundle);
        }
        return super.performAccessibilityAction(i2, bundle);
    }

    public void setAdapter(RecyclerView.g gVar) {
        RecyclerView.g adapter = this.n.getAdapter();
        this.x.b((RecyclerView.g<?>) adapter);
        b((RecyclerView.g<?>) adapter);
        this.n.setAdapter(gVar);
        this.f967h = 0;
        g();
        this.x.a((RecyclerView.g<?>) gVar);
        a((RecyclerView.g<?>) gVar);
    }

    public void setCurrentItem(int i2) {
        a(i2, true);
    }

    public void setLayoutDirection(int i2) {
        super.setLayoutDirection(i2);
        this.x.f();
    }

    public void setOffscreenPageLimit(int i2) {
        if (i2 >= 1 || i2 == -1) {
            this.w = i2;
            this.n.requestLayout();
            return;
        }
        throw new IllegalArgumentException("Offscreen page limit must be OFFSCREEN_PAGE_LIMIT_DEFAULT or a number > 0");
    }

    public void setOrientation(int i2) {
        this.k.k(i2);
        this.x.h();
    }

    public void setPageTransformer(k kVar) {
        if (kVar != null) {
            if (!this.u) {
                this.t = this.n.getItemAnimator();
                this.u = true;
            }
            this.n.setItemAnimator((RecyclerView.l) null);
        } else if (this.u) {
            this.n.setItemAnimator(this.t);
            this.t = null;
            this.u = false;
        }
        if (kVar != this.s.a()) {
            this.s.a(kVar);
            d();
        }
    }

    public void setUserInputEnabled(boolean z) {
        this.v = z;
        this.x.i();
    }

    static class SavedState extends View.BaseSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new a();
        int e;

        /* renamed from: f  reason: collision with root package name */
        int f970f;

        /* renamed from: g  reason: collision with root package name */
        Parcelable f971g;

        static class a implements Parcelable.ClassLoaderCreator<SavedState> {
            a() {
            }

            public SavedState[] newArray(int i2) {
                return new SavedState[i2];
            }

            public SavedState createFromParcel(Parcel parcel, ClassLoader classLoader) {
                return Build.VERSION.SDK_INT >= 24 ? new SavedState(parcel, classLoader) : new SavedState(parcel);
            }

            public SavedState createFromParcel(Parcel parcel) {
                return createFromParcel(parcel, (ClassLoader) null);
            }
        }

        SavedState(Parcel parcel, ClassLoader classLoader) {
            super(parcel, classLoader);
            a(parcel, classLoader);
        }

        private void a(Parcel parcel, ClassLoader classLoader) {
            this.e = parcel.readInt();
            this.f970f = parcel.readInt();
            this.f971g = parcel.readParcelable(classLoader);
        }

        public void writeToParcel(Parcel parcel, int i2) {
            super.writeToParcel(parcel, i2);
            parcel.writeInt(this.e);
            parcel.writeInt(this.f970f);
            parcel.writeParcelable(this.f971g, i2);
        }

        SavedState(Parcel parcel) {
            super(parcel);
            a(parcel, (ClassLoader) null);
        }

        SavedState(Parcelable parcelable) {
            super(parcelable);
        }
    }

    class f extends e {
        f() {
            super(ViewPager2.this, (a) null);
        }

        public boolean a(int i2) {
            return (i2 == 8192 || i2 == 4096) && !ViewPager2.this.c();
        }

        public boolean b() {
            return true;
        }

        public boolean b(int i2) {
            if (a(i2)) {
                return false;
            }
            throw new IllegalStateException();
        }

        public CharSequence e() {
            if (b()) {
                return "androidx.viewpager.widget.ViewPager";
            }
            throw new IllegalStateException();
        }

        public void a(androidx.core.h.e0.d dVar) {
            if (!ViewPager2.this.c()) {
                dVar.b(d.a.f515i);
                dVar.b(d.a.f514h);
                dVar.n(false);
            }
        }
    }

    private class h extends LinearLayoutManager {
        h(Context context) {
            super(context);
        }

        public boolean a(RecyclerView.v vVar, RecyclerView.z zVar, int i2, Bundle bundle) {
            if (ViewPager2.this.x.a(i2)) {
                return ViewPager2.this.x.b(i2);
            }
            return super.a(vVar, zVar, i2, bundle);
        }

        public boolean a(RecyclerView recyclerView, View view, Rect rect, boolean z, boolean z2) {
            return false;
        }

        public void a(RecyclerView.v vVar, RecyclerView.z zVar, androidx.core.h.e0.d dVar) {
            super.a(vVar, zVar, dVar);
            ViewPager2.this.x.a(dVar);
        }

        /* access modifiers changed from: protected */
        public void a(RecyclerView.z zVar, int[] iArr) {
            int offscreenPageLimit = ViewPager2.this.getOffscreenPageLimit();
            if (offscreenPageLimit == -1) {
                super.a(zVar, iArr);
                return;
            }
            int pageSize = ViewPager2.this.getPageSize() * offscreenPageLimit;
            iArr[0] = pageSize;
            iArr[1] = pageSize;
        }
    }

    private void b(RecyclerView.g<?> gVar) {
        if (gVar != null) {
            gVar.unregisterAdapterDataObserver(this.f969j);
        }
    }

    /* access modifiers changed from: package-private */
    public boolean b() {
        return this.k.k() == 1;
    }

    /* access modifiers changed from: package-private */
    public void b(int i2, boolean z) {
        RecyclerView.g adapter = getAdapter();
        if (adapter == null) {
            if (this.l != -1) {
                this.l = Math.max(i2, 0);
            }
        } else if (adapter.getItemCount() > 0) {
            int min = Math.min(Math.max(i2, 0), adapter.getItemCount() - 1);
            if (min == this.f967h && this.p.d()) {
                return;
            }
            if (min != this.f967h || !z) {
                double d2 = (double) this.f967h;
                this.f967h = min;
                this.x.g();
                if (!this.p.d()) {
                    d2 = this.p.a();
                }
                this.p.a(min, z);
                if (!z) {
                    this.n.i(min);
                    return;
                }
                double d3 = (double) min;
                Double.isNaN(d3);
                if (Math.abs(d3 - d2) > 3.0d) {
                    this.n.i(d3 > d2 ? min - 3 : min + 3);
                    RecyclerView recyclerView = this.n;
                    recyclerView.post(new n(min, recyclerView));
                    return;
                }
                this.n.j(min);
            }
        }
    }

    public ViewPager2(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        a(context, attributeSet);
    }

    public ViewPager2(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        a(context, attributeSet);
    }

    private void a(RecyclerView.g<?> gVar) {
        if (gVar != null) {
            gVar.registerAdapterDataObserver(this.f969j);
        }
    }

    public void a(int i2, boolean z) {
        if (!a()) {
            b(i2, z);
            return;
        }
        throw new IllegalStateException("Cannot change current item when ViewPager2 is fake dragging");
    }

    public void b(i iVar) {
        this.f966g.b(iVar);
    }

    public boolean a() {
        return this.r.a();
    }

    public void a(i iVar) {
        this.f966g.a(iVar);
    }

    public ViewPager2(Context context, AttributeSet attributeSet, int i2, int i3) {
        super(context, attributeSet, i2, i3);
        a(context, attributeSet);
    }
}
