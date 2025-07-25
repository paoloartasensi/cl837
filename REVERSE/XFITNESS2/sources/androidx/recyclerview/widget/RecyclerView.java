package androidx.recyclerview.widget;

import android.animation.LayoutTransition;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.database.Observable;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.Display;
import android.view.FocusFinder;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import android.view.animation.Interpolator;
import android.widget.EdgeEffect;
import android.widget.OverScroller;
import androidx.core.h.e0.d;
import androidx.customview.view.AbsSavedState;
import androidx.recyclerview.R$attr;
import androidx.recyclerview.R$dimen;
import androidx.recyclerview.R$styleable;
import androidx.recyclerview.widget.a;
import androidx.recyclerview.widget.d;
import androidx.recyclerview.widget.i;
import androidx.recyclerview.widget.s;
import androidx.recyclerview.widget.w;
import androidx.recyclerview.widget.x;
import com.jeremyliao.liveeventbus.BuildConfig;
import java.lang.ref.WeakReference;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RecyclerView extends ViewGroup implements androidx.core.h.j, androidx.core.h.k {
    private static final int[] C0 = {16843830};
    static final boolean D0;
    static final boolean E0 = (Build.VERSION.SDK_INT >= 23);
    static final boolean F0 = (Build.VERSION.SDK_INT >= 16);
    static final boolean G0 = (Build.VERSION.SDK_INT >= 21);
    private static final boolean H0 = (Build.VERSION.SDK_INT <= 15);
    private static final boolean I0 = (Build.VERSION.SDK_INT <= 15);
    private static final Class<?>[] J0;
    static final Interpolator K0 = new c();
    boolean A;
    private Runnable A0;
    boolean B;
    private final x.b B0;
    private boolean C;
    private int D;
    boolean E;
    private final AccessibilityManager F;
    private List<q> G;
    boolean H;
    boolean I;
    private int J;
    private int K;
    private k L;
    private EdgeEffect M;
    private EdgeEffect N;
    private EdgeEffect O;
    private EdgeEffect P;
    l Q;
    private int R;
    private int S;
    private VelocityTracker T;
    private int U;
    private int V;
    private int W;
    private int a0;
    private int b0;
    private r c0;
    private final int d0;
    private final x e;
    private final int e0;

    /* renamed from: f  reason: collision with root package name */
    final v f778f;
    private float f0;

    /* renamed from: g  reason: collision with root package name */
    private SavedState f779g;
    private float g0;

    /* renamed from: h  reason: collision with root package name */
    a f780h;
    private boolean h0;

    /* renamed from: i  reason: collision with root package name */
    d f781i;
    final b0 i0;

    /* renamed from: j  reason: collision with root package name */
    final x f782j;
    i j0;
    boolean k;
    i.b k0;
    final Runnable l;
    final z l0;
    final Rect m;
    private t m0;
    private final Rect n;
    private List<t> n0;
    final RectF o;
    boolean o0;
    g p;
    boolean p0;
    o q;
    private l.b q0;
    w r;
    boolean r0;
    final ArrayList<n> s;
    s s0;
    private final ArrayList<s> t;
    private j t0;
    private s u;
    private final int[] u0;
    boolean v;
    private androidx.core.h.m v0;
    boolean w;
    private final int[] w0;
    boolean x;
    private final int[] x0;
    boolean y;
    final int[] y0;
    private int z;
    final List<c0> z0;

    class a implements Runnable {
        a() {
        }

        public void run() {
            RecyclerView recyclerView = RecyclerView.this;
            if (recyclerView.y && !recyclerView.isLayoutRequested()) {
                RecyclerView recyclerView2 = RecyclerView.this;
                if (!recyclerView2.v) {
                    recyclerView2.requestLayout();
                } else if (recyclerView2.B) {
                    recyclerView2.A = true;
                } else {
                    recyclerView2.b();
                }
            }
        }
    }

    public static abstract class a0 {
        public abstract View a(v vVar, int i2, int i3);
    }

    class b implements Runnable {
        b() {
        }

        public void run() {
            l lVar = RecyclerView.this.Q;
            if (lVar != null) {
                lVar.i();
            }
            RecyclerView.this.r0 = false;
        }
    }

    static class c implements Interpolator {
        c() {
        }

        public float getInterpolation(float f2) {
            float f3 = f2 - 1.0f;
            return (f3 * f3 * f3 * f3 * f3) + 1.0f;
        }
    }

    public static abstract class c0 {
        static final int FLAG_ADAPTER_FULLUPDATE = 1024;
        static final int FLAG_ADAPTER_POSITION_UNKNOWN = 512;
        static final int FLAG_APPEARED_IN_PRE_LAYOUT = 4096;
        static final int FLAG_BOUNCED_FROM_HIDDEN_LIST = 8192;
        static final int FLAG_BOUND = 1;
        static final int FLAG_IGNORE = 128;
        static final int FLAG_INVALID = 4;
        static final int FLAG_MOVED = 2048;
        static final int FLAG_NOT_RECYCLABLE = 16;
        static final int FLAG_REMOVED = 8;
        static final int FLAG_RETURNED_FROM_SCRAP = 32;
        static final int FLAG_TMP_DETACHED = 256;
        static final int FLAG_UPDATE = 2;
        private static final List<Object> FULLUPDATE_PAYLOADS = Collections.emptyList();
        static final int PENDING_ACCESSIBILITY_STATE_NOT_SET = -1;
        public final View itemView;
        int mFlags;
        boolean mInChangeScrap = false;
        private int mIsRecyclableCount = 0;
        long mItemId = -1;
        int mItemViewType = -1;
        WeakReference<RecyclerView> mNestedRecyclerView;
        int mOldPosition = -1;
        RecyclerView mOwnerRecyclerView;
        List<Object> mPayloads = null;
        int mPendingAccessibilityState = -1;
        int mPosition = -1;
        int mPreLayoutPosition = -1;
        v mScrapContainer = null;
        c0 mShadowedHolder = null;
        c0 mShadowingHolder = null;
        List<Object> mUnmodifiedPayloads = null;
        private int mWasImportantForAccessibilityBeforeHidden = 0;

        public c0(View view) {
            if (view != null) {
                this.itemView = view;
                return;
            }
            throw new IllegalArgumentException("itemView may not be null");
        }

        private void createPayloadsIfNeeded() {
            if (this.mPayloads == null) {
                ArrayList arrayList = new ArrayList();
                this.mPayloads = arrayList;
                this.mUnmodifiedPayloads = Collections.unmodifiableList(arrayList);
            }
        }

        /* access modifiers changed from: package-private */
        public void addChangePayload(Object obj) {
            if (obj == null) {
                addFlags(FLAG_ADAPTER_FULLUPDATE);
            } else if ((FLAG_ADAPTER_FULLUPDATE & this.mFlags) == 0) {
                createPayloadsIfNeeded();
                this.mPayloads.add(obj);
            }
        }

        /* access modifiers changed from: package-private */
        public void addFlags(int i2) {
            this.mFlags = i2 | this.mFlags;
        }

        /* access modifiers changed from: package-private */
        public void clearOldPosition() {
            this.mOldPosition = -1;
            this.mPreLayoutPosition = -1;
        }

        /* access modifiers changed from: package-private */
        public void clearPayload() {
            List<Object> list = this.mPayloads;
            if (list != null) {
                list.clear();
            }
            this.mFlags &= -1025;
        }

        /* access modifiers changed from: package-private */
        public void clearReturnedFromScrapFlag() {
            this.mFlags &= -33;
        }

        /* access modifiers changed from: package-private */
        public void clearTmpDetachFlag() {
            this.mFlags &= -257;
        }

        /* access modifiers changed from: package-private */
        public boolean doesTransientStatePreventRecycling() {
            return (this.mFlags & 16) == 0 && androidx.core.h.v.A(this.itemView);
        }

        /* access modifiers changed from: package-private */
        public void flagRemovedAndOffsetPosition(int i2, int i3, boolean z) {
            addFlags(8);
            offsetPosition(i3, z);
            this.mPosition = i2;
        }

        public final int getAdapterPosition() {
            RecyclerView recyclerView = this.mOwnerRecyclerView;
            if (recyclerView == null) {
                return -1;
            }
            return recyclerView.b(this);
        }

        public final long getItemId() {
            return this.mItemId;
        }

        public final int getItemViewType() {
            return this.mItemViewType;
        }

        public final int getLayoutPosition() {
            int i2 = this.mPreLayoutPosition;
            return i2 == -1 ? this.mPosition : i2;
        }

        public final int getOldPosition() {
            return this.mOldPosition;
        }

        @Deprecated
        public final int getPosition() {
            int i2 = this.mPreLayoutPosition;
            return i2 == -1 ? this.mPosition : i2;
        }

        /* access modifiers changed from: package-private */
        public List<Object> getUnmodifiedPayloads() {
            if ((this.mFlags & FLAG_ADAPTER_FULLUPDATE) != 0) {
                return FULLUPDATE_PAYLOADS;
            }
            List<Object> list = this.mPayloads;
            if (list == null || list.size() == 0) {
                return FULLUPDATE_PAYLOADS;
            }
            return this.mUnmodifiedPayloads;
        }

        /* access modifiers changed from: package-private */
        public boolean hasAnyOfTheFlags(int i2) {
            return (i2 & this.mFlags) != 0;
        }

        /* access modifiers changed from: package-private */
        public boolean isAdapterPositionUnknown() {
            return (this.mFlags & FLAG_ADAPTER_POSITION_UNKNOWN) != 0 || isInvalid();
        }

        /* access modifiers changed from: package-private */
        public boolean isAttachedToTransitionOverlay() {
            return (this.itemView.getParent() == null || this.itemView.getParent() == this.mOwnerRecyclerView) ? false : true;
        }

        /* access modifiers changed from: package-private */
        public boolean isBound() {
            return (this.mFlags & 1) != 0;
        }

        /* access modifiers changed from: package-private */
        public boolean isInvalid() {
            return (this.mFlags & 4) != 0;
        }

        public final boolean isRecyclable() {
            return (this.mFlags & 16) == 0 && !androidx.core.h.v.A(this.itemView);
        }

        /* access modifiers changed from: package-private */
        public boolean isRemoved() {
            return (this.mFlags & 8) != 0;
        }

        /* access modifiers changed from: package-private */
        public boolean isScrap() {
            return this.mScrapContainer != null;
        }

        /* access modifiers changed from: package-private */
        public boolean isTmpDetached() {
            return (this.mFlags & FLAG_TMP_DETACHED) != 0;
        }

        /* access modifiers changed from: package-private */
        public boolean isUpdated() {
            return (this.mFlags & 2) != 0;
        }

        /* access modifiers changed from: package-private */
        public boolean needsUpdate() {
            return (this.mFlags & 2) != 0;
        }

        /* access modifiers changed from: package-private */
        public void offsetPosition(int i2, boolean z) {
            if (this.mOldPosition == -1) {
                this.mOldPosition = this.mPosition;
            }
            if (this.mPreLayoutPosition == -1) {
                this.mPreLayoutPosition = this.mPosition;
            }
            if (z) {
                this.mPreLayoutPosition += i2;
            }
            this.mPosition += i2;
            if (this.itemView.getLayoutParams() != null) {
                ((p) this.itemView.getLayoutParams()).c = true;
            }
        }

        /* access modifiers changed from: package-private */
        public void onEnteredHiddenState(RecyclerView recyclerView) {
            int i2 = this.mPendingAccessibilityState;
            if (i2 != -1) {
                this.mWasImportantForAccessibilityBeforeHidden = i2;
            } else {
                this.mWasImportantForAccessibilityBeforeHidden = androidx.core.h.v.m(this.itemView);
            }
            recyclerView.a(this, 4);
        }

        /* access modifiers changed from: package-private */
        public void onLeftHiddenState(RecyclerView recyclerView) {
            recyclerView.a(this, this.mWasImportantForAccessibilityBeforeHidden);
            this.mWasImportantForAccessibilityBeforeHidden = 0;
        }

        /* access modifiers changed from: package-private */
        public void resetInternal() {
            this.mFlags = 0;
            this.mPosition = -1;
            this.mOldPosition = -1;
            this.mItemId = -1;
            this.mPreLayoutPosition = -1;
            this.mIsRecyclableCount = 0;
            this.mShadowedHolder = null;
            this.mShadowingHolder = null;
            clearPayload();
            this.mWasImportantForAccessibilityBeforeHidden = 0;
            this.mPendingAccessibilityState = -1;
            RecyclerView.e(this);
        }

        /* access modifiers changed from: package-private */
        public void saveOldPosition() {
            if (this.mOldPosition == -1) {
                this.mOldPosition = this.mPosition;
            }
        }

        /* access modifiers changed from: package-private */
        public void setFlags(int i2, int i3) {
            this.mFlags = (i2 & i3) | (this.mFlags & (i3 ^ -1));
        }

        public final void setIsRecyclable(boolean z) {
            int i2 = this.mIsRecyclableCount;
            int i3 = z ? i2 - 1 : i2 + 1;
            this.mIsRecyclableCount = i3;
            if (i3 < 0) {
                this.mIsRecyclableCount = 0;
                Log.e("View", "isRecyclable decremented below 0: unmatched pair of setIsRecyable() calls for " + this);
            } else if (!z && i3 == 1) {
                this.mFlags |= 16;
            } else if (z && this.mIsRecyclableCount == 0) {
                this.mFlags &= -17;
            }
        }

        /* access modifiers changed from: package-private */
        public void setScrapContainer(v vVar, boolean z) {
            this.mScrapContainer = vVar;
            this.mInChangeScrap = z;
        }

        /* access modifiers changed from: package-private */
        public boolean shouldBeKeptAsChild() {
            return (this.mFlags & 16) != 0;
        }

        /* access modifiers changed from: package-private */
        public boolean shouldIgnore() {
            return (this.mFlags & FLAG_IGNORE) != 0;
        }

        /* access modifiers changed from: package-private */
        public void stopIgnoring() {
            this.mFlags &= -129;
        }

        public String toString() {
            String simpleName = getClass().isAnonymousClass() ? "ViewHolder" : getClass().getSimpleName();
            StringBuilder sb = new StringBuilder(simpleName + "{" + Integer.toHexString(hashCode()) + " position=" + this.mPosition + " id=" + this.mItemId + ", oldPos=" + this.mOldPosition + ", pLpos:" + this.mPreLayoutPosition);
            if (isScrap()) {
                sb.append(" scrap ");
                sb.append(this.mInChangeScrap ? "[changeScrap]" : "[attachedScrap]");
            }
            if (isInvalid()) {
                sb.append(" invalid");
            }
            if (!isBound()) {
                sb.append(" unbound");
            }
            if (needsUpdate()) {
                sb.append(" update");
            }
            if (isRemoved()) {
                sb.append(" removed");
            }
            if (shouldIgnore()) {
                sb.append(" ignored");
            }
            if (isTmpDetached()) {
                sb.append(" tmpDetached");
            }
            if (!isRecyclable()) {
                sb.append(" not recyclable(" + this.mIsRecyclableCount + ")");
            }
            if (isAdapterPositionUnknown()) {
                sb.append(" undefined adapter position");
            }
            if (this.itemView.getParent() == null) {
                sb.append(" no parent");
            }
            sb.append("}");
            return sb.toString();
        }

        /* access modifiers changed from: package-private */
        public void unScrap() {
            this.mScrapContainer.c(this);
        }

        /* access modifiers changed from: package-private */
        public boolean wasReturnedFromScrap() {
            return (this.mFlags & 32) != 0;
        }
    }

    class d implements x.b {
        d() {
        }

        public void a(c0 c0Var, l.c cVar, l.c cVar2) {
            RecyclerView.this.a(c0Var, cVar, cVar2);
        }

        public void b(c0 c0Var, l.c cVar, l.c cVar2) {
            RecyclerView.this.f778f.c(c0Var);
            RecyclerView.this.b(c0Var, cVar, cVar2);
        }

        public void c(c0 c0Var, l.c cVar, l.c cVar2) {
            c0Var.setIsRecyclable(false);
            RecyclerView recyclerView = RecyclerView.this;
            if (recyclerView.H) {
                if (recyclerView.Q.a(c0Var, c0Var, cVar, cVar2)) {
                    RecyclerView.this.t();
                }
            } else if (recyclerView.Q.c(c0Var, cVar, cVar2)) {
                RecyclerView.this.t();
            }
        }

        public void a(c0 c0Var) {
            RecyclerView recyclerView = RecyclerView.this;
            recyclerView.q.a(c0Var.itemView, recyclerView.f778f);
        }
    }

    class e implements d.b {
        e() {
        }

        public void a(View view, int i2) {
            RecyclerView.this.addView(view, i2);
            RecyclerView.this.a(view);
        }

        public int b() {
            return RecyclerView.this.getChildCount();
        }

        public void c(int i2) {
            c0 m;
            View a2 = a(i2);
            if (!(a2 == null || (m = RecyclerView.m(a2)) == null)) {
                if (!m.isTmpDetached() || m.shouldIgnore()) {
                    m.addFlags(256);
                } else {
                    throw new IllegalArgumentException("called detach on an already detached child " + m + RecyclerView.this.i());
                }
            }
            RecyclerView.this.detachViewFromParent(i2);
        }

        public int d(View view) {
            return RecyclerView.this.indexOfChild(view);
        }

        public void b(int i2) {
            View childAt = RecyclerView.this.getChildAt(i2);
            if (childAt != null) {
                RecyclerView.this.b(childAt);
                childAt.clearAnimation();
            }
            RecyclerView.this.removeViewAt(i2);
        }

        public View a(int i2) {
            return RecyclerView.this.getChildAt(i2);
        }

        public void a() {
            int b = b();
            for (int i2 = 0; i2 < b; i2++) {
                View a2 = a(i2);
                RecyclerView.this.b(a2);
                a2.clearAnimation();
            }
            RecyclerView.this.removeAllViews();
        }

        public c0 b(View view) {
            return RecyclerView.m(view);
        }

        public void c(View view) {
            c0 m = RecyclerView.m(view);
            if (m != null) {
                m.onLeftHiddenState(RecyclerView.this);
            }
        }

        public void a(View view, int i2, ViewGroup.LayoutParams layoutParams) {
            c0 m = RecyclerView.m(view);
            if (m != null) {
                if (m.isTmpDetached() || m.shouldIgnore()) {
                    m.clearTmpDetachFlag();
                } else {
                    throw new IllegalArgumentException("Called attach on a child which is not detached: " + m + RecyclerView.this.i());
                }
            }
            RecyclerView.this.attachViewToParent(view, i2, layoutParams);
        }

        public void a(View view) {
            c0 m = RecyclerView.m(view);
            if (m != null) {
                m.onEnteredHiddenState(RecyclerView.this);
            }
        }
    }

    public static abstract class g<VH extends c0> {
        private boolean mHasStableIds = false;
        private final h mObservable = new h();

        public final void bindViewHolder(VH vh, int i2) {
            vh.mPosition = i2;
            if (hasStableIds()) {
                vh.mItemId = getItemId(i2);
            }
            vh.setFlags(1, 519);
            androidx.core.d.b.a("RV OnBindView");
            onBindViewHolder(vh, i2, vh.getUnmodifiedPayloads());
            vh.clearPayload();
            ViewGroup.LayoutParams layoutParams = vh.itemView.getLayoutParams();
            if (layoutParams instanceof p) {
                ((p) layoutParams).c = true;
            }
            androidx.core.d.b.a();
        }

        public final VH createViewHolder(ViewGroup viewGroup, int i2) {
            try {
                androidx.core.d.b.a("RV CreateView");
                VH onCreateViewHolder = onCreateViewHolder(viewGroup, i2);
                if (onCreateViewHolder.itemView.getParent() == null) {
                    onCreateViewHolder.mItemViewType = i2;
                    return onCreateViewHolder;
                }
                throw new IllegalStateException("ViewHolder views must not be attached when created. Ensure that you are not passing 'true' to the attachToRoot parameter of LayoutInflater.inflate(..., boolean attachToRoot)");
            } finally {
                androidx.core.d.b.a();
            }
        }

        public abstract int getItemCount();

        public long getItemId(int i2) {
            return -1;
        }

        public int getItemViewType(int i2) {
            return 0;
        }

        public final boolean hasObservers() {
            return this.mObservable.a();
        }

        public final boolean hasStableIds() {
            return this.mHasStableIds;
        }

        public final void notifyDataSetChanged() {
            this.mObservable.b();
        }

        public final void notifyItemChanged(int i2) {
            this.mObservable.b(i2, 1);
        }

        public final void notifyItemInserted(int i2) {
            this.mObservable.c(i2, 1);
        }

        public final void notifyItemMoved(int i2, int i3) {
            this.mObservable.a(i2, i3);
        }

        public final void notifyItemRangeChanged(int i2, int i3) {
            this.mObservable.b(i2, i3);
        }

        public final void notifyItemRangeInserted(int i2, int i3) {
            this.mObservable.c(i2, i3);
        }

        public final void notifyItemRangeRemoved(int i2, int i3) {
            this.mObservable.d(i2, i3);
        }

        public final void notifyItemRemoved(int i2) {
            this.mObservable.d(i2, 1);
        }

        public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        }

        public abstract void onBindViewHolder(VH vh, int i2);

        public void onBindViewHolder(VH vh, int i2, List<Object> list) {
            onBindViewHolder(vh, i2);
        }

        public abstract VH onCreateViewHolder(ViewGroup viewGroup, int i2);

        public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        }

        public boolean onFailedToRecycleView(VH vh) {
            return false;
        }

        public void onViewAttachedToWindow(VH vh) {
        }

        public void onViewDetachedFromWindow(VH vh) {
        }

        public void onViewRecycled(VH vh) {
        }

        public void registerAdapterDataObserver(i iVar) {
            this.mObservable.registerObserver(iVar);
        }

        public void setHasStableIds(boolean z) {
            if (!hasObservers()) {
                this.mHasStableIds = z;
                return;
            }
            throw new IllegalStateException("Cannot change whether this adapter has stable IDs while the adapter has registered observers.");
        }

        public void unregisterAdapterDataObserver(i iVar) {
            this.mObservable.unregisterObserver(iVar);
        }

        public final void notifyItemChanged(int i2, Object obj) {
            this.mObservable.a(i2, 1, obj);
        }

        public final void notifyItemRangeChanged(int i2, int i3, Object obj) {
            this.mObservable.a(i2, i3, obj);
        }
    }

    static class h extends Observable<i> {
        h() {
        }

        public boolean a() {
            return !this.mObservers.isEmpty();
        }

        public void b() {
            for (int size = this.mObservers.size() - 1; size >= 0; size--) {
                ((i) this.mObservers.get(size)).a();
            }
        }

        public void c(int i2, int i3) {
            for (int size = this.mObservers.size() - 1; size >= 0; size--) {
                ((i) this.mObservers.get(size)).b(i2, i3);
            }
        }

        public void d(int i2, int i3) {
            for (int size = this.mObservers.size() - 1; size >= 0; size--) {
                ((i) this.mObservers.get(size)).c(i2, i3);
            }
        }

        public void a(int i2, int i3, Object obj) {
            for (int size = this.mObservers.size() - 1; size >= 0; size--) {
                ((i) this.mObservers.get(size)).a(i2, i3, obj);
            }
        }

        public void b(int i2, int i3) {
            a(i2, i3, (Object) null);
        }

        public void a(int i2, int i3) {
            for (int size = this.mObservers.size() - 1; size >= 0; size--) {
                ((i) this.mObservers.get(size)).a(i2, i3, 1);
            }
        }
    }

    public static abstract class i {
        public void a() {
        }

        public void a(int i2, int i3) {
        }

        public void a(int i2, int i3, int i4) {
        }

        public void a(int i2, int i3, Object obj) {
            a(i2, i3);
        }

        public void b(int i2, int i3) {
        }

        public void c(int i2, int i3) {
        }
    }

    public interface j {
        int a(int i2, int i3);
    }

    public static class k {
        /* access modifiers changed from: protected */
        public EdgeEffect a(RecyclerView recyclerView, int i2) {
            return new EdgeEffect(recyclerView.getContext());
        }
    }

    public static abstract class l {
        private b a = null;
        private ArrayList<a> b = new ArrayList<>();
        private long c = 120;
        private long d = 120;
        private long e = 250;

        /* renamed from: f  reason: collision with root package name */
        private long f789f = 250;

        public interface a {
            void a();
        }

        interface b {
            void a(c0 c0Var);
        }

        public static class c {
            public int a;
            public int b;

            public c a(c0 c0Var) {
                a(c0Var, 0);
                return this;
            }

            public c a(c0 c0Var, int i2) {
                View view = c0Var.itemView;
                this.a = view.getLeft();
                this.b = view.getTop();
                view.getRight();
                view.getBottom();
                return this;
            }
        }

        /* access modifiers changed from: package-private */
        public void a(b bVar) {
            this.a = bVar;
        }

        public abstract boolean a(c0 c0Var);

        public abstract boolean a(c0 c0Var, c0 c0Var2, c cVar, c cVar2);

        public abstract boolean a(c0 c0Var, c cVar, c cVar2);

        public abstract void b();

        public final void b(c0 c0Var) {
            d(c0Var);
            b bVar = this.a;
            if (bVar != null) {
                bVar.a(c0Var);
            }
        }

        public abstract boolean b(c0 c0Var, c cVar, c cVar2);

        public long c() {
            return this.c;
        }

        public abstract void c(c0 c0Var);

        public abstract boolean c(c0 c0Var, c cVar, c cVar2);

        public long d() {
            return this.f789f;
        }

        public void d(c0 c0Var) {
        }

        public long e() {
            return this.e;
        }

        public long f() {
            return this.d;
        }

        public abstract boolean g();

        public c h() {
            return new c();
        }

        public abstract void i();

        static int e(c0 c0Var) {
            int i2 = c0Var.mFlags & 14;
            if (c0Var.isInvalid()) {
                return 4;
            }
            if ((i2 & 4) != 0) {
                return i2;
            }
            int oldPosition = c0Var.getOldPosition();
            int adapterPosition = c0Var.getAdapterPosition();
            return (oldPosition == -1 || adapterPosition == -1 || oldPosition == adapterPosition) ? i2 : i2 | 2048;
        }

        public c a(z zVar, c0 c0Var, int i2, List<Object> list) {
            c h2 = h();
            h2.a(c0Var);
            return h2;
        }

        public c a(z zVar, c0 c0Var) {
            c h2 = h();
            h2.a(c0Var);
            return h2;
        }

        public boolean a(c0 c0Var, List<Object> list) {
            return a(c0Var);
        }

        public final void a() {
            int size = this.b.size();
            for (int i2 = 0; i2 < size; i2++) {
                this.b.get(i2).a();
            }
            this.b.clear();
        }
    }

    private class m implements l.b {
        m() {
        }

        public void a(c0 c0Var) {
            c0Var.setIsRecyclable(true);
            if (c0Var.mShadowedHolder != null && c0Var.mShadowingHolder == null) {
                c0Var.mShadowedHolder = null;
            }
            c0Var.mShadowingHolder = null;
            if (!c0Var.shouldBeKeptAsChild() && !RecyclerView.this.k(c0Var.itemView) && c0Var.isTmpDetached()) {
                RecyclerView.this.removeDetachedView(c0Var.itemView, false);
            }
        }
    }

    public static abstract class n {
        @Deprecated
        public void a(Canvas canvas, RecyclerView recyclerView) {
        }

        public void a(Canvas canvas, RecyclerView recyclerView, z zVar) {
            a(canvas, recyclerView);
        }

        @Deprecated
        public void b(Canvas canvas, RecyclerView recyclerView) {
        }

        public void b(Canvas canvas, RecyclerView recyclerView, z zVar) {
            b(canvas, recyclerView);
        }

        @Deprecated
        public void a(Rect rect, int i2, RecyclerView recyclerView) {
            rect.set(0, 0, 0, 0);
        }

        public void a(Rect rect, View view, RecyclerView recyclerView, z zVar) {
            a(rect, ((p) view.getLayoutParams()).a(), recyclerView);
        }
    }

    public static abstract class o {
        d a;
        RecyclerView b;
        private final w.b c = new a();
        private final w.b d = new b();
        w e = new w(this.c);

        /* renamed from: f  reason: collision with root package name */
        w f790f = new w(this.d);

        /* renamed from: g  reason: collision with root package name */
        y f791g;

        /* renamed from: h  reason: collision with root package name */
        boolean f792h = false;

        /* renamed from: i  reason: collision with root package name */
        boolean f793i = false;

        /* renamed from: j  reason: collision with root package name */
        boolean f794j = false;
        private boolean k = true;
        private boolean l = true;
        int m;
        boolean n;
        private int o;
        private int p;
        private int q;
        private int r;

        class a implements w.b {
            a() {
            }

            public View a(int i2) {
                return o.this.d(i2);
            }

            public int b() {
                return o.this.o();
            }

            public int a() {
                return o.this.r() - o.this.p();
            }

            public int b(View view) {
                return o.this.f(view) - ((p) view.getLayoutParams()).leftMargin;
            }

            public int a(View view) {
                return o.this.i(view) + ((p) view.getLayoutParams()).rightMargin;
            }
        }

        class b implements w.b {
            b() {
            }

            public View a(int i2) {
                return o.this.d(i2);
            }

            public int b() {
                return o.this.q();
            }

            public int a() {
                return o.this.h() - o.this.n();
            }

            public int b(View view) {
                return o.this.j(view) - ((p) view.getLayoutParams()).topMargin;
            }

            public int a(View view) {
                return o.this.e(view) + ((p) view.getLayoutParams()).bottomMargin;
            }
        }

        public interface c {
            void a(int i2, int i3);
        }

        public static class d {
            public int a;
            public int b;
            public boolean c;
            public boolean d;
        }

        public void A() {
            this.f792h = true;
        }

        /* access modifiers changed from: package-private */
        public boolean B() {
            return false;
        }

        /* access modifiers changed from: package-private */
        public void C() {
            y yVar = this.f791g;
            if (yVar != null) {
                yVar.h();
            }
        }

        public boolean D() {
            return false;
        }

        public int a(int i2, v vVar, z zVar) {
            return 0;
        }

        public int a(z zVar) {
            return 0;
        }

        public View a(View view, int i2, v vVar, z zVar) {
            return null;
        }

        public void a(int i2, int i3, z zVar, c cVar) {
        }

        public void a(int i2, c cVar) {
        }

        public void a(Rect rect, int i2, int i3) {
            c(a(i2, rect.width() + o() + p(), m()), a(i3, rect.height() + q() + n(), l()));
        }

        public void a(Parcelable parcelable) {
        }

        public void a(g gVar, g gVar2) {
        }

        public void a(RecyclerView recyclerView, int i2, int i3) {
        }

        public void a(RecyclerView recyclerView, int i2, int i3, int i4) {
        }

        public boolean a() {
            return false;
        }

        public boolean a(p pVar) {
            return pVar != null;
        }

        public boolean a(v vVar, z zVar, View view, int i2, Bundle bundle) {
            return false;
        }

        public boolean a(RecyclerView recyclerView, ArrayList<View> arrayList, int i2, int i3) {
            return false;
        }

        public int b(int i2, v vVar, z zVar) {
            return 0;
        }

        public int b(z zVar) {
            return 0;
        }

        /* access modifiers changed from: package-private */
        public void b(int i2, int i3) {
            this.q = View.MeasureSpec.getSize(i2);
            int mode = View.MeasureSpec.getMode(i2);
            this.o = mode;
            if (mode == 0 && !RecyclerView.E0) {
                this.q = 0;
            }
            this.r = View.MeasureSpec.getSize(i3);
            int mode2 = View.MeasureSpec.getMode(i3);
            this.p = mode2;
            if (mode2 == 0 && !RecyclerView.E0) {
                this.r = 0;
            }
        }

        public void b(RecyclerView recyclerView) {
        }

        public void b(RecyclerView recyclerView, int i2, int i3) {
        }

        public boolean b() {
            return false;
        }

        public int c(v vVar, z zVar) {
            return 0;
        }

        public int c(z zVar) {
            return 0;
        }

        public View c(View view) {
            View c2;
            RecyclerView recyclerView = this.b;
            if (recyclerView == null || (c2 = recyclerView.c(view)) == null || this.a.c(c2)) {
                return null;
            }
            return c2;
        }

        public abstract p c();

        @Deprecated
        public void c(RecyclerView recyclerView) {
        }

        public void c(RecyclerView recyclerView, int i2, int i3) {
        }

        public int d() {
            return -1;
        }

        public int d(z zVar) {
            return 0;
        }

        public View d(View view, int i2) {
            return null;
        }

        /* access modifiers changed from: package-private */
        public void d(int i2, int i3) {
            int e2 = e();
            if (e2 == 0) {
                this.b.c(i2, i3);
                return;
            }
            int i4 = Integer.MIN_VALUE;
            int i5 = Integer.MIN_VALUE;
            int i6 = Integer.MAX_VALUE;
            int i7 = Integer.MAX_VALUE;
            for (int i8 = 0; i8 < e2; i8++) {
                View d2 = d(i8);
                Rect rect = this.b.m;
                b(d2, rect);
                int i9 = rect.left;
                if (i9 < i6) {
                    i6 = i9;
                }
                int i10 = rect.right;
                if (i10 > i4) {
                    i4 = i10;
                }
                int i11 = rect.top;
                if (i11 < i7) {
                    i7 = i11;
                }
                int i12 = rect.bottom;
                if (i12 > i5) {
                    i5 = i12;
                }
            }
            this.b.m.set(i6, i7, i4, i5);
            a(this.b.m, i2, i3);
        }

        public void d(RecyclerView recyclerView) {
        }

        public boolean d(v vVar, z zVar) {
            return false;
        }

        public int e(z zVar) {
            return 0;
        }

        public void e(v vVar, z zVar) {
            Log.e("RecyclerView", "You must override onLayoutChildren(Recycler recycler, State state) ");
        }

        public int f(z zVar) {
            return 0;
        }

        /* access modifiers changed from: package-private */
        public void f(RecyclerView recyclerView) {
            if (recyclerView == null) {
                this.b = null;
                this.a = null;
                this.q = 0;
                this.r = 0;
            } else {
                this.b = recyclerView;
                this.a = recyclerView.f781i;
                this.q = recyclerView.getWidth();
                this.r = recyclerView.getHeight();
            }
            this.o = 1073741824;
            this.p = 1073741824;
        }

        public View g() {
            View focusedChild;
            RecyclerView recyclerView = this.b;
            if (recyclerView == null || (focusedChild = recyclerView.getFocusedChild()) == null || this.a.c(focusedChild)) {
                return null;
            }
            return focusedChild;
        }

        public void g(int i2) {
        }

        public void g(z zVar) {
        }

        public void h(int i2) {
            if (d(i2) != null) {
                this.a.e(i2);
            }
        }

        public int i() {
            return this.p;
        }

        public void i(int i2) {
        }

        public int j() {
            RecyclerView recyclerView = this.b;
            g adapter = recyclerView != null ? recyclerView.getAdapter() : null;
            if (adapter != null) {
                return adapter.getItemCount();
            }
            return 0;
        }

        public int k() {
            return androidx.core.h.v.o(this.b);
        }

        public int l(View view) {
            return ((p) view.getLayoutParams()).a();
        }

        public int m(View view) {
            return ((p) view.getLayoutParams()).b.right;
        }

        public int n() {
            RecyclerView recyclerView = this.b;
            if (recyclerView != null) {
                return recyclerView.getPaddingBottom();
            }
            return 0;
        }

        public void o(View view) {
            this.a.d(view);
        }

        public int p() {
            RecyclerView recyclerView = this.b;
            if (recyclerView != null) {
                return recyclerView.getPaddingRight();
            }
            return 0;
        }

        public int q() {
            RecyclerView recyclerView = this.b;
            if (recyclerView != null) {
                return recyclerView.getPaddingTop();
            }
            return 0;
        }

        public int r() {
            return this.q;
        }

        public int s() {
            return this.o;
        }

        /* access modifiers changed from: package-private */
        public boolean t() {
            int e2 = e();
            for (int i2 = 0; i2 < e2; i2++) {
                ViewGroup.LayoutParams layoutParams = d(i2).getLayoutParams();
                if (layoutParams.width < 0 && layoutParams.height < 0) {
                    return true;
                }
            }
            return false;
        }

        public boolean u() {
            return this.f793i;
        }

        public boolean v() {
            return this.f794j;
        }

        public final boolean w() {
            return this.l;
        }

        public boolean x() {
            y yVar = this.f791g;
            return yVar != null && yVar.e();
        }

        public Parcelable y() {
            return null;
        }

        public void z() {
            RecyclerView recyclerView = this.b;
            if (recyclerView != null) {
                recyclerView.requestLayout();
            }
        }

        public int e() {
            d dVar = this.a;
            if (dVar != null) {
                return dVar.a();
            }
            return 0;
        }

        public int i(View view) {
            return view.getRight() + m(view);
        }

        public int k(View view) {
            return ((p) view.getLayoutParams()).b.left;
        }

        public int l() {
            return androidx.core.h.v.p(this.b);
        }

        public int m() {
            return androidx.core.h.v.q(this.b);
        }

        public int n(View view) {
            return ((p) view.getLayoutParams()).b.top;
        }

        public int o() {
            RecyclerView recyclerView = this.b;
            if (recyclerView != null) {
                return recyclerView.getPaddingLeft();
            }
            return 0;
        }

        public void e(int i2) {
            RecyclerView recyclerView = this.b;
            if (recyclerView != null) {
                recyclerView.f(i2);
            }
        }

        public int h() {
            return this.r;
        }

        public int j(View view) {
            return view.getTop() - n(view);
        }

        public View c(int i2) {
            int e2 = e();
            for (int i3 = 0; i3 < e2; i3++) {
                View d2 = d(i3);
                c0 m2 = RecyclerView.m(d2);
                if (m2 != null && m2.getLayoutPosition() == i2 && !m2.shouldIgnore() && (this.b.l0.d() || !m2.isRemoved())) {
                    return d2;
                }
            }
            return null;
        }

        public int g(View view) {
            Rect rect = ((p) view.getLayoutParams()).b;
            return view.getMeasuredHeight() + rect.top + rect.bottom;
        }

        public int h(View view) {
            Rect rect = ((p) view.getLayoutParams()).b;
            return view.getMeasuredWidth() + rect.left + rect.right;
        }

        public int e(View view) {
            return view.getBottom() + d(view);
        }

        public static int a(int i2, int i3, int i4) {
            int mode = View.MeasureSpec.getMode(i2);
            int size = View.MeasureSpec.getSize(i2);
            if (mode != Integer.MIN_VALUE) {
                return mode != 1073741824 ? Math.max(i3, i4) : size;
            }
            return Math.min(size, Math.max(i3, i4));
        }

        /* access modifiers changed from: package-private */
        public void e(RecyclerView recyclerView) {
            b(View.MeasureSpec.makeMeasureSpec(recyclerView.getWidth(), 1073741824), View.MeasureSpec.makeMeasureSpec(recyclerView.getHeight(), 1073741824));
        }

        public void b(RecyclerView recyclerView, v vVar) {
            c(recyclerView);
        }

        public void c(View view, int i2) {
            a(view, i2, (p) view.getLayoutParams());
        }

        public void a(String str) {
            RecyclerView recyclerView = this.b;
            if (recyclerView != null) {
                recyclerView.a(str);
            }
        }

        public void b(y yVar) {
            y yVar2 = this.f791g;
            if (!(yVar2 == null || yVar == yVar2 || !yVar2.e())) {
                this.f791g.h();
            }
            this.f791g = yVar;
            yVar.a(this.b, this);
        }

        /* access modifiers changed from: package-private */
        public void c(v vVar) {
            int e2 = vVar.e();
            for (int i2 = e2 - 1; i2 >= 0; i2--) {
                View c2 = vVar.c(i2);
                c0 m2 = RecyclerView.m(c2);
                if (!m2.shouldIgnore()) {
                    m2.setIsRecyclable(false);
                    if (m2.isTmpDetached()) {
                        this.b.removeDetachedView(c2, false);
                    }
                    l lVar = this.b.Q;
                    if (lVar != null) {
                        lVar.c(m2);
                    }
                    m2.setIsRecyclable(true);
                    vVar.a(c2);
                }
            }
            vVar.c();
            if (e2 > 0) {
                this.b.invalidate();
            }
        }

        public boolean f() {
            RecyclerView recyclerView = this.b;
            return recyclerView != null && recyclerView.k;
        }

        /* access modifiers changed from: package-private */
        public void a(RecyclerView recyclerView) {
            this.f793i = true;
            b(recyclerView);
        }

        public View d(int i2) {
            d dVar = this.a;
            if (dVar != null) {
                return dVar.c(i2);
            }
            return null;
        }

        public void f(int i2) {
            RecyclerView recyclerView = this.b;
            if (recyclerView != null) {
                recyclerView.g(i2);
            }
        }

        public int d(View view) {
            return ((p) view.getLayoutParams()).b.bottom;
        }

        private boolean d(RecyclerView recyclerView, int i2, int i3) {
            View focusedChild = recyclerView.getFocusedChild();
            if (focusedChild == null) {
                return false;
            }
            int o2 = o();
            int q2 = q();
            int r2 = r() - p();
            int h2 = h() - n();
            Rect rect = this.b.m;
            b(focusedChild, rect);
            if (rect.left - i2 >= r2 || rect.right - i2 <= o2 || rect.top - i3 >= h2 || rect.bottom - i3 <= q2) {
                return false;
            }
            return true;
        }

        /* access modifiers changed from: package-private */
        public void a(RecyclerView recyclerView, v vVar) {
            this.f793i = false;
            b(recyclerView, vVar);
        }

        public int f(View view) {
            return view.getLeft() - k(view);
        }

        public void b(View view) {
            b(view, -1);
        }

        public boolean a(Runnable runnable) {
            RecyclerView recyclerView = this.b;
            if (recyclerView != null) {
                return recyclerView.removeCallbacks(runnable);
            }
            return false;
        }

        public void b(View view, int i2) {
            a(view, i2, false);
        }

        public void b(int i2) {
            a(i2, d(i2));
        }

        public p a(ViewGroup.LayoutParams layoutParams) {
            if (layoutParams instanceof p) {
                return new p((p) layoutParams);
            }
            if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
                return new p((ViewGroup.MarginLayoutParams) layoutParams);
            }
            return new p(layoutParams);
        }

        /* access modifiers changed from: package-private */
        public boolean b(View view, int i2, int i3, p pVar) {
            return !this.k || !b(view.getMeasuredWidth(), i2, pVar.width) || !b(view.getMeasuredHeight(), i3, pVar.height);
        }

        private static boolean b(int i2, int i3, int i4) {
            int mode = View.MeasureSpec.getMode(i3);
            int size = View.MeasureSpec.getSize(i3);
            if (i4 > 0 && i2 != i4) {
                return false;
            }
            if (mode == Integer.MIN_VALUE) {
                return size >= i2;
            }
            if (mode != 0) {
                return mode == 1073741824 && size == i2;
            }
            return true;
        }

        private int[] c(View view, Rect rect) {
            int[] iArr = new int[2];
            int o2 = o();
            int q2 = q();
            int r2 = r() - p();
            int h2 = h() - n();
            int left = (view.getLeft() + rect.left) - view.getScrollX();
            int top = (view.getTop() + rect.top) - view.getScrollY();
            int width = rect.width() + left;
            int height = rect.height() + top;
            int i2 = left - o2;
            int min = Math.min(0, i2);
            int i3 = top - q2;
            int min2 = Math.min(0, i3);
            int i4 = width - r2;
            int max = Math.max(0, i4);
            int max2 = Math.max(0, height - h2);
            if (k() != 1) {
                if (min == 0) {
                    min = Math.min(i2, max);
                }
                max = min;
            } else if (max == 0) {
                max = Math.max(min, i4);
            }
            if (min2 == 0) {
                min2 = Math.min(i3, max2);
            }
            iArr[0] = max;
            iArr[1] = min2;
            return iArr;
        }

        public p a(Context context, AttributeSet attributeSet) {
            return new p(context, attributeSet);
        }

        public void b(View view, Rect rect) {
            RecyclerView.b(view, rect);
        }

        public void a(RecyclerView recyclerView, z zVar, int i2) {
            Log.e("RecyclerView", "You must override smoothScrollToPosition to support smooth scrolling");
        }

        public void b(v vVar) {
            for (int e2 = e() - 1; e2 >= 0; e2--) {
                if (!RecyclerView.m(d(e2)).shouldIgnore()) {
                    a(e2, vVar);
                }
            }
        }

        public void a(View view) {
            a(view, -1);
        }

        public void a(View view, int i2) {
            a(view, i2, true);
        }

        private void a(View view, int i2, boolean z) {
            c0 m2 = RecyclerView.m(view);
            if (z || m2.isRemoved()) {
                this.b.f782j.a(m2);
            } else {
                this.b.f782j.g(m2);
            }
            p pVar = (p) view.getLayoutParams();
            if (m2.wasReturnedFromScrap() || m2.isScrap()) {
                if (m2.isScrap()) {
                    m2.unScrap();
                } else {
                    m2.clearReturnedFromScrapFlag();
                }
                this.a.a(view, i2, view.getLayoutParams(), false);
            } else if (view.getParent() == this.b) {
                int b2 = this.a.b(view);
                if (i2 == -1) {
                    i2 = this.a.a();
                }
                if (b2 == -1) {
                    throw new IllegalStateException("Added View has RecyclerView as parent but view is not a real child. Unfiltered index:" + this.b.indexOfChild(view) + this.b.i());
                } else if (b2 != i2) {
                    this.b.q.a(b2, i2);
                }
            } else {
                this.a.a(view, i2, false);
                pVar.c = true;
                y yVar = this.f791g;
                if (yVar != null && yVar.e()) {
                    this.f791g.b(view);
                }
            }
            if (pVar.d) {
                m2.itemView.invalidate();
                pVar.d = false;
            }
        }

        public int b(v vVar, z zVar) {
            RecyclerView recyclerView = this.b;
            if (recyclerView == null || recyclerView.p == null || !b()) {
                return 1;
            }
            return this.b.p.getItemCount();
        }

        public void c(int i2, int i3) {
            this.b.setMeasuredDimension(i2, i3);
        }

        private void a(int i2, View view) {
            this.a.a(i2);
        }

        public void a(View view, int i2, p pVar) {
            c0 m2 = RecyclerView.m(view);
            if (m2.isRemoved()) {
                this.b.f782j.a(m2);
            } else {
                this.b.f782j.g(m2);
            }
            this.a.a(view, i2, pVar, m2.isRemoved());
        }

        public void a(int i2, int i3) {
            View d2 = d(i2);
            if (d2 != null) {
                b(i2);
                c(d2, i3);
                return;
            }
            throw new IllegalArgumentException("Cannot move a child from non-existing index:" + i2 + this.b.toString());
        }

        public void a(View view, v vVar) {
            o(view);
            vVar.b(view);
        }

        public void a(int i2, v vVar) {
            View d2 = d(i2);
            h(i2);
            vVar.b(d2);
        }

        public void a(v vVar) {
            for (int e2 = e() - 1; e2 >= 0; e2--) {
                a(vVar, e2, d(e2));
            }
        }

        private void a(v vVar, int i2, View view) {
            c0 m2 = RecyclerView.m(view);
            if (!m2.shouldIgnore()) {
                if (!m2.isInvalid() || m2.isRemoved() || this.b.p.hasStableIds()) {
                    b(i2);
                    vVar.c(view);
                    this.b.f782j.d(m2);
                    return;
                }
                h(i2);
                vVar.b(m2);
            }
        }

        /* access modifiers changed from: package-private */
        public boolean a(View view, int i2, int i3, p pVar) {
            return view.isLayoutRequested() || !this.k || !b(view.getWidth(), i2, pVar.width) || !b(view.getHeight(), i3, pVar.height);
        }

        public void a(View view, int i2, int i3) {
            p pVar = (p) view.getLayoutParams();
            Rect h2 = this.b.h(view);
            int i4 = i2 + h2.left + h2.right;
            int i5 = i3 + h2.top + h2.bottom;
            int a2 = a(r(), s(), o() + p() + pVar.leftMargin + pVar.rightMargin + i4, pVar.width, a());
            int a3 = a(h(), i(), q() + n() + pVar.topMargin + pVar.bottomMargin + i5, pVar.height, b());
            if (a(view, a2, a3, pVar)) {
                view.measure(a2, a3);
            }
        }

        /* JADX WARNING: Code restructure failed: missing block: B:6:0x0017, code lost:
            if (r5 == 1073741824) goto L_0x0021;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public static int a(int r4, int r5, int r6, int r7, boolean r8) {
            /*
                int r4 = r4 - r6
                r6 = 0
                int r4 = java.lang.Math.max(r6, r4)
                r0 = -2
                r1 = -1
                r2 = -2147483648(0xffffffff80000000, float:-0.0)
                r3 = 1073741824(0x40000000, float:2.0)
                if (r8 == 0) goto L_0x001a
                if (r7 < 0) goto L_0x0011
                goto L_0x001c
            L_0x0011:
                if (r7 != r1) goto L_0x002f
                if (r5 == r2) goto L_0x0021
                if (r5 == 0) goto L_0x002f
                if (r5 == r3) goto L_0x0021
                goto L_0x002f
            L_0x001a:
                if (r7 < 0) goto L_0x001f
            L_0x001c:
                r5 = 1073741824(0x40000000, float:2.0)
                goto L_0x0031
            L_0x001f:
                if (r7 != r1) goto L_0x0023
            L_0x0021:
                r7 = r4
                goto L_0x0031
            L_0x0023:
                if (r7 != r0) goto L_0x002f
                if (r5 == r2) goto L_0x002c
                if (r5 != r3) goto L_0x002a
                goto L_0x002c
            L_0x002a:
                r5 = 0
                goto L_0x0021
            L_0x002c:
                r5 = -2147483648(0xffffffff80000000, float:-0.0)
                goto L_0x0021
            L_0x002f:
                r5 = 0
                r7 = 0
            L_0x0031:
                int r4 = android.view.View.MeasureSpec.makeMeasureSpec(r7, r5)
                return r4
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.recyclerview.widget.RecyclerView.o.a(int, int, int, int, boolean):int");
        }

        public void a(View view, int i2, int i3, int i4, int i5) {
            p pVar = (p) view.getLayoutParams();
            Rect rect = pVar.b;
            view.layout(i2 + rect.left + pVar.leftMargin, i3 + rect.top + pVar.topMargin, (i4 - rect.right) - pVar.rightMargin, (i5 - rect.bottom) - pVar.bottomMargin);
        }

        public void a(View view, boolean z, Rect rect) {
            Matrix matrix;
            if (z) {
                Rect rect2 = ((p) view.getLayoutParams()).b;
                rect.set(-rect2.left, -rect2.top, view.getWidth() + rect2.right, view.getHeight() + rect2.bottom);
            } else {
                rect.set(0, 0, view.getWidth(), view.getHeight());
            }
            if (!(this.b == null || (matrix = view.getMatrix()) == null || matrix.isIdentity())) {
                RectF rectF = this.b.o;
                rectF.set(rect);
                matrix.mapRect(rectF);
                rect.set((int) Math.floor((double) rectF.left), (int) Math.floor((double) rectF.top), (int) Math.ceil((double) rectF.right), (int) Math.ceil((double) rectF.bottom));
            }
            rect.offset(view.getLeft(), view.getTop());
        }

        public void a(View view, Rect rect) {
            RecyclerView recyclerView = this.b;
            if (recyclerView == null) {
                rect.set(0, 0, 0, 0);
            } else {
                rect.set(recyclerView.h(view));
            }
        }

        public boolean a(RecyclerView recyclerView, View view, Rect rect, boolean z) {
            return a(recyclerView, view, rect, z, false);
        }

        public boolean a(RecyclerView recyclerView, View view, Rect rect, boolean z, boolean z2) {
            int[] c2 = c(view, rect);
            int i2 = c2[0];
            int i3 = c2[1];
            if ((z2 && !d(recyclerView, i2, i3)) || (i2 == 0 && i3 == 0)) {
                return false;
            }
            if (z) {
                recyclerView.scrollBy(i2, i3);
            } else {
                recyclerView.i(i2, i3);
            }
            return true;
        }

        public boolean a(View view, boolean z, boolean z2) {
            boolean z3 = this.e.a(view, 24579) && this.f790f.a(view, 24579);
            return z ? z3 : !z3;
        }

        @Deprecated
        public boolean a(RecyclerView recyclerView, View view, View view2) {
            return x() || recyclerView.o();
        }

        public boolean a(RecyclerView recyclerView, z zVar, View view, View view2) {
            return a(recyclerView, view, view2);
        }

        public void a(RecyclerView recyclerView, int i2, int i3, Object obj) {
            c(recyclerView, i2, i3);
        }

        public void a(v vVar, z zVar, int i2, int i3) {
            this.b.c(i2, i3);
        }

        /* access modifiers changed from: package-private */
        public void a(y yVar) {
            if (this.f791g == yVar) {
                this.f791g = null;
            }
        }

        /* access modifiers changed from: package-private */
        public void a(androidx.core.h.e0.d dVar) {
            RecyclerView recyclerView = this.b;
            a(recyclerView.f778f, recyclerView.l0, dVar);
        }

        public void a(v vVar, z zVar, androidx.core.h.e0.d dVar) {
            if (this.b.canScrollVertically(-1) || this.b.canScrollHorizontally(-1)) {
                dVar.a(8192);
                dVar.n(true);
            }
            if (this.b.canScrollVertically(1) || this.b.canScrollHorizontally(1)) {
                dVar.a(4096);
                dVar.n(true);
            }
            dVar.a((Object) d.b.a(b(vVar, zVar), a(vVar, zVar), d(vVar, zVar), c(vVar, zVar)));
        }

        public void a(AccessibilityEvent accessibilityEvent) {
            RecyclerView recyclerView = this.b;
            a(recyclerView.f778f, recyclerView.l0, accessibilityEvent);
        }

        public void a(v vVar, z zVar, AccessibilityEvent accessibilityEvent) {
            RecyclerView recyclerView = this.b;
            if (recyclerView != null && accessibilityEvent != null) {
                boolean z = true;
                if (!recyclerView.canScrollVertically(1) && !this.b.canScrollVertically(-1) && !this.b.canScrollHorizontally(-1) && !this.b.canScrollHorizontally(1)) {
                    z = false;
                }
                accessibilityEvent.setScrollable(z);
                g gVar = this.b.p;
                if (gVar != null) {
                    accessibilityEvent.setItemCount(gVar.getItemCount());
                }
            }
        }

        /* access modifiers changed from: package-private */
        public void a(View view, androidx.core.h.e0.d dVar) {
            c0 m2 = RecyclerView.m(view);
            if (m2 != null && !m2.isRemoved() && !this.a.c(m2.itemView)) {
                RecyclerView recyclerView = this.b;
                a(recyclerView.f778f, recyclerView.l0, view, dVar);
            }
        }

        public void a(v vVar, z zVar, View view, androidx.core.h.e0.d dVar) {
            dVar.b((Object) d.c.a(b() ? l(view) : 0, 1, a() ? l(view) : 0, 1, false, false));
        }

        public int a(v vVar, z zVar) {
            RecyclerView recyclerView = this.b;
            if (recyclerView == null || recyclerView.p == null || !a()) {
                return 1;
            }
            return this.b.p.getItemCount();
        }

        /* access modifiers changed from: package-private */
        public boolean a(int i2, Bundle bundle) {
            RecyclerView recyclerView = this.b;
            return a(recyclerView.f778f, recyclerView.l0, i2, bundle);
        }

        /* JADX WARNING: Removed duplicated region for block: B:25:0x0075 A[ADDED_TO_REGION] */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean a(androidx.recyclerview.widget.RecyclerView.v r8, androidx.recyclerview.widget.RecyclerView.z r9, int r10, android.os.Bundle r11) {
            /*
                r7 = this;
                androidx.recyclerview.widget.RecyclerView r8 = r7.b
                r9 = 0
                if (r8 != 0) goto L_0x0006
                return r9
            L_0x0006:
                r11 = 4096(0x1000, float:5.74E-42)
                r0 = 1
                if (r10 == r11) goto L_0x0042
                r11 = 8192(0x2000, float:1.14794E-41)
                if (r10 == r11) goto L_0x0012
                r2 = 0
                r3 = 0
                goto L_0x0073
            L_0x0012:
                r10 = -1
                boolean r8 = r8.canScrollVertically(r10)
                if (r8 == 0) goto L_0x0029
                int r8 = r7.h()
                int r11 = r7.q()
                int r8 = r8 - r11
                int r11 = r7.n()
                int r8 = r8 - r11
                int r8 = -r8
                goto L_0x002a
            L_0x0029:
                r8 = 0
            L_0x002a:
                androidx.recyclerview.widget.RecyclerView r11 = r7.b
                boolean r10 = r11.canScrollHorizontally(r10)
                if (r10 == 0) goto L_0x0071
                int r10 = r7.r()
                int r11 = r7.o()
                int r10 = r10 - r11
                int r11 = r7.p()
                int r10 = r10 - r11
                int r10 = -r10
                goto L_0x006e
            L_0x0042:
                boolean r8 = r8.canScrollVertically(r0)
                if (r8 == 0) goto L_0x0057
                int r8 = r7.h()
                int r10 = r7.q()
                int r8 = r8 - r10
                int r10 = r7.n()
                int r8 = r8 - r10
                goto L_0x0058
            L_0x0057:
                r8 = 0
            L_0x0058:
                androidx.recyclerview.widget.RecyclerView r10 = r7.b
                boolean r10 = r10.canScrollHorizontally(r0)
                if (r10 == 0) goto L_0x0071
                int r10 = r7.r()
                int r11 = r7.o()
                int r10 = r10 - r11
                int r11 = r7.p()
                int r10 = r10 - r11
            L_0x006e:
                r3 = r8
                r2 = r10
                goto L_0x0073
            L_0x0071:
                r3 = r8
                r2 = 0
            L_0x0073:
                if (r3 != 0) goto L_0x0078
                if (r2 != 0) goto L_0x0078
                return r9
            L_0x0078:
                androidx.recyclerview.widget.RecyclerView r1 = r7.b
                r4 = 0
                r5 = -2147483648(0xffffffff80000000, float:-0.0)
                r6 = 1
                r1.a((int) r2, (int) r3, (android.view.animation.Interpolator) r4, (int) r5, (boolean) r6)
                return r0
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.recyclerview.widget.RecyclerView.o.a(androidx.recyclerview.widget.RecyclerView$v, androidx.recyclerview.widget.RecyclerView$z, int, android.os.Bundle):boolean");
        }

        /* access modifiers changed from: package-private */
        public boolean a(View view, int i2, Bundle bundle) {
            RecyclerView recyclerView = this.b;
            return a(recyclerView.f778f, recyclerView.l0, view, i2, bundle);
        }

        public static d a(Context context, AttributeSet attributeSet, int i2, int i3) {
            d dVar = new d();
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.RecyclerView, i2, i3);
            dVar.a = obtainStyledAttributes.getInt(R$styleable.RecyclerView_android_orientation, 1);
            dVar.b = obtainStyledAttributes.getInt(R$styleable.RecyclerView_spanCount, 1);
            dVar.c = obtainStyledAttributes.getBoolean(R$styleable.RecyclerView_reverseLayout, false);
            dVar.d = obtainStyledAttributes.getBoolean(R$styleable.RecyclerView_stackFromEnd, false);
            obtainStyledAttributes.recycle();
            return dVar;
        }
    }

    public interface q {
        void a(View view);

        void b(View view);
    }

    public static abstract class r {
        public abstract boolean a(int i2, int i3);
    }

    public interface s {
        boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent);

        void onRequestDisallowInterceptTouchEvent(boolean z);

        void onTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent);
    }

    public static abstract class t {
        public void a(RecyclerView recyclerView, int i2) {
        }

        public void a(RecyclerView recyclerView, int i2, int i3) {
        }
    }

    public final class v {
        final ArrayList<c0> a = new ArrayList<>();
        ArrayList<c0> b = null;
        final ArrayList<c0> c = new ArrayList<>();
        private final List<c0> d = Collections.unmodifiableList(this.a);
        private int e = 2;

        /* renamed from: f  reason: collision with root package name */
        int f795f = 2;

        /* renamed from: g  reason: collision with root package name */
        u f796g;

        /* renamed from: h  reason: collision with root package name */
        private a0 f797h;

        public v() {
        }

        private void e(c0 c0Var) {
            if (RecyclerView.this.n()) {
                View view = c0Var.itemView;
                if (androidx.core.h.v.m(view) == 0) {
                    androidx.core.h.v.h(view, 1);
                }
                s sVar = RecyclerView.this.s0;
                if (sVar != null) {
                    androidx.core.h.a b2 = sVar.b();
                    if (b2 instanceof s.a) {
                        ((s.a) b2).d(view);
                    }
                    androidx.core.h.v.a(view, b2);
                }
            }
        }

        public void a() {
            this.a.clear();
            i();
        }

        /* access modifiers changed from: package-private */
        public View b(int i2, boolean z) {
            return a(i2, z, Long.MAX_VALUE).itemView;
        }

        /* access modifiers changed from: package-private */
        public void c(View view) {
            c0 m = RecyclerView.m(view);
            if (!m.hasAnyOfTheFlags(12) && m.isUpdated() && !RecyclerView.this.a(m)) {
                if (this.b == null) {
                    this.b = new ArrayList<>();
                }
                m.setScrapContainer(this, true);
                this.b.add(m);
            } else if (!m.isInvalid() || m.isRemoved() || RecyclerView.this.p.hasStableIds()) {
                m.setScrapContainer(this, false);
                this.a.add(m);
            } else {
                throw new IllegalArgumentException("Called scrap view with an invalid view. Invalid views cannot be reused from scrap, they should rebound from recycler pool." + RecyclerView.this.i());
            }
        }

        /* access modifiers changed from: package-private */
        public boolean d(c0 c0Var) {
            if (c0Var.isRemoved()) {
                return RecyclerView.this.l0.d();
            }
            int i2 = c0Var.mPosition;
            if (i2 < 0 || i2 >= RecyclerView.this.p.getItemCount()) {
                throw new IndexOutOfBoundsException("Inconsistency detected. Invalid view holder adapter position" + c0Var + RecyclerView.this.i());
            } else if (!RecyclerView.this.l0.d() && RecyclerView.this.p.getItemViewType(c0Var.mPosition) != c0Var.getItemViewType()) {
                return false;
            } else {
                if (!RecyclerView.this.p.hasStableIds() || c0Var.getItemId() == RecyclerView.this.p.getItemId(c0Var.mPosition)) {
                    return true;
                }
                return false;
            }
        }

        public void f(int i2) {
            this.e = i2;
            j();
        }

        /* access modifiers changed from: package-private */
        public void g() {
            int size = this.c.size();
            for (int i2 = 0; i2 < size; i2++) {
                p pVar = (p) this.c.get(i2).itemView.getLayoutParams();
                if (pVar != null) {
                    pVar.c = true;
                }
            }
        }

        /* access modifiers changed from: package-private */
        public void h() {
            int size = this.c.size();
            for (int i2 = 0; i2 < size; i2++) {
                c0 c0Var = this.c.get(i2);
                if (c0Var != null) {
                    c0Var.addFlags(6);
                    c0Var.addChangePayload((Object) null);
                }
            }
            g gVar = RecyclerView.this.p;
            if (gVar == null || !gVar.hasStableIds()) {
                i();
            }
        }

        /* access modifiers changed from: package-private */
        public void i() {
            for (int size = this.c.size() - 1; size >= 0; size--) {
                e(size);
            }
            this.c.clear();
            if (RecyclerView.G0) {
                RecyclerView.this.k0.a();
            }
        }

        /* access modifiers changed from: package-private */
        public void j() {
            o oVar = RecyclerView.this.q;
            this.f795f = this.e + (oVar != null ? oVar.m : 0);
            for (int size = this.c.size() - 1; size >= 0 && this.c.size() > this.f795f; size--) {
                e(size);
            }
        }

        public void b(View view) {
            c0 m = RecyclerView.m(view);
            if (m.isTmpDetached()) {
                RecyclerView.this.removeDetachedView(view, false);
            }
            if (m.isScrap()) {
                m.unScrap();
            } else if (m.wasReturnedFromScrap()) {
                m.clearReturnedFromScrapFlag();
            }
            b(m);
            if (RecyclerView.this.Q != null && !m.isRecyclable()) {
                RecyclerView.this.Q.c(m);
            }
        }

        private boolean a(c0 c0Var, int i2, int i3, long j2) {
            c0Var.mOwnerRecyclerView = RecyclerView.this;
            int itemViewType = c0Var.getItemViewType();
            long nanoTime = RecyclerView.this.getNanoTime();
            if (j2 != Long.MAX_VALUE && !this.f796g.a(itemViewType, nanoTime, j2)) {
                return false;
            }
            RecyclerView.this.p.bindViewHolder(c0Var, i2);
            this.f796g.a(c0Var.getItemViewType(), RecyclerView.this.getNanoTime() - nanoTime);
            e(c0Var);
            if (!RecyclerView.this.l0.d()) {
                return true;
            }
            c0Var.mPreLayoutPosition = i3;
            return true;
        }

        public List<c0> f() {
            return this.d;
        }

        private void f(c0 c0Var) {
            View view = c0Var.itemView;
            if (view instanceof ViewGroup) {
                a((ViewGroup) view, false);
            }
        }

        public View d(int i2) {
            return b(i2, false);
        }

        /* access modifiers changed from: package-private */
        public void e(int i2) {
            a(this.c.get(i2), true);
            this.c.remove(i2);
        }

        /* access modifiers changed from: package-private */
        public void b(c0 c0Var) {
            boolean z;
            boolean z2 = false;
            boolean z3 = true;
            if (c0Var.isScrap() || c0Var.itemView.getParent() != null) {
                StringBuilder sb = new StringBuilder();
                sb.append("Scrapped or attached views may not be recycled. isScrap:");
                sb.append(c0Var.isScrap());
                sb.append(" isAttached:");
                if (c0Var.itemView.getParent() != null) {
                    z2 = true;
                }
                sb.append(z2);
                sb.append(RecyclerView.this.i());
                throw new IllegalArgumentException(sb.toString());
            } else if (c0Var.isTmpDetached()) {
                throw new IllegalArgumentException("Tmp detached view should be removed from RecyclerView before it can be recycled: " + c0Var + RecyclerView.this.i());
            } else if (!c0Var.shouldIgnore()) {
                boolean doesTransientStatePreventRecycling = c0Var.doesTransientStatePreventRecycling();
                g gVar = RecyclerView.this.p;
                if ((gVar != null && doesTransientStatePreventRecycling && gVar.onFailedToRecycleView(c0Var)) || c0Var.isRecyclable()) {
                    if (this.f795f <= 0 || c0Var.hasAnyOfTheFlags(526)) {
                        z = false;
                    } else {
                        int size = this.c.size();
                        if (size >= this.f795f && size > 0) {
                            e(0);
                            size--;
                        }
                        if (RecyclerView.G0 && size > 0 && !RecyclerView.this.k0.a(c0Var.mPosition)) {
                            int i2 = size - 1;
                            while (i2 >= 0) {
                                if (!RecyclerView.this.k0.a(this.c.get(i2).mPosition)) {
                                    break;
                                }
                                i2--;
                            }
                            size = i2 + 1;
                        }
                        this.c.add(size, c0Var);
                        z = true;
                    }
                    if (!z) {
                        a(c0Var, true);
                        z2 = z;
                        RecyclerView.this.f782j.h(c0Var);
                        if (!z2 && !z3 && doesTransientStatePreventRecycling) {
                            c0Var.mOwnerRecyclerView = null;
                            return;
                        }
                        return;
                    }
                    z2 = z;
                }
                z3 = false;
                RecyclerView.this.f782j.h(c0Var);
                if (!z2) {
                }
            } else {
                throw new IllegalArgumentException("Trying to recycle an ignored view holder. You should first call stopIgnoringView(view) before calling recycle." + RecyclerView.this.i());
            }
        }

        /* access modifiers changed from: package-private */
        public u d() {
            if (this.f796g == null) {
                this.f796g = new u();
            }
            return this.f796g;
        }

        /* access modifiers changed from: package-private */
        public void c(c0 c0Var) {
            if (c0Var.mInChangeScrap) {
                this.b.remove(c0Var);
            } else {
                this.a.remove(c0Var);
            }
            c0Var.mScrapContainer = null;
            c0Var.mInChangeScrap = false;
            c0Var.clearReturnedFromScrapFlag();
        }

        public int a(int i2) {
            if (i2 < 0 || i2 >= RecyclerView.this.l0.a()) {
                throw new IndexOutOfBoundsException("invalid position " + i2 + ". State item count is " + RecyclerView.this.l0.a() + RecyclerView.this.i());
            } else if (!RecyclerView.this.l0.d()) {
                return i2;
            } else {
                return RecyclerView.this.f780h.b(i2);
            }
        }

        /* access modifiers changed from: package-private */
        public int e() {
            return this.a.size();
        }

        /* access modifiers changed from: package-private */
        /* JADX WARNING: Removed duplicated region for block: B:16:0x0037  */
        /* JADX WARNING: Removed duplicated region for block: B:25:0x005c  */
        /* JADX WARNING: Removed duplicated region for block: B:27:0x005f  */
        /* JADX WARNING: Removed duplicated region for block: B:79:0x01a2  */
        /* JADX WARNING: Removed duplicated region for block: B:84:0x01cb  */
        /* JADX WARNING: Removed duplicated region for block: B:85:0x01ce  */
        /* JADX WARNING: Removed duplicated region for block: B:95:0x01fe  */
        /* JADX WARNING: Removed duplicated region for block: B:96:0x020c  */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public androidx.recyclerview.widget.RecyclerView.c0 a(int r17, boolean r18, long r19) {
            /*
                r16 = this;
                r6 = r16
                r3 = r17
                r0 = r18
                if (r3 < 0) goto L_0x022f
                androidx.recyclerview.widget.RecyclerView r1 = androidx.recyclerview.widget.RecyclerView.this
                androidx.recyclerview.widget.RecyclerView$z r1 = r1.l0
                int r1 = r1.a()
                if (r3 >= r1) goto L_0x022f
                androidx.recyclerview.widget.RecyclerView r1 = androidx.recyclerview.widget.RecyclerView.this
                androidx.recyclerview.widget.RecyclerView$z r1 = r1.l0
                boolean r1 = r1.d()
                r2 = 0
                r7 = 1
                r8 = 0
                if (r1 == 0) goto L_0x0027
                androidx.recyclerview.widget.RecyclerView$c0 r1 = r16.b((int) r17)
                if (r1 == 0) goto L_0x0028
                r4 = 1
                goto L_0x0029
            L_0x0027:
                r1 = r2
            L_0x0028:
                r4 = 0
            L_0x0029:
                if (r1 != 0) goto L_0x005d
                androidx.recyclerview.widget.RecyclerView$c0 r1 = r16.a((int) r17, (boolean) r18)
                if (r1 == 0) goto L_0x005d
                boolean r5 = r6.d((androidx.recyclerview.widget.RecyclerView.c0) r1)
                if (r5 != 0) goto L_0x005c
                if (r0 != 0) goto L_0x005a
                r5 = 4
                r1.addFlags(r5)
                boolean r5 = r1.isScrap()
                if (r5 == 0) goto L_0x004e
                androidx.recyclerview.widget.RecyclerView r5 = androidx.recyclerview.widget.RecyclerView.this
                android.view.View r9 = r1.itemView
                r5.removeDetachedView(r9, r8)
                r1.unScrap()
                goto L_0x0057
            L_0x004e:
                boolean r5 = r1.wasReturnedFromScrap()
                if (r5 == 0) goto L_0x0057
                r1.clearReturnedFromScrapFlag()
            L_0x0057:
                r6.b((androidx.recyclerview.widget.RecyclerView.c0) r1)
            L_0x005a:
                r1 = r2
                goto L_0x005d
            L_0x005c:
                r4 = 1
            L_0x005d:
                if (r1 != 0) goto L_0x0181
                androidx.recyclerview.widget.RecyclerView r5 = androidx.recyclerview.widget.RecyclerView.this
                androidx.recyclerview.widget.a r5 = r5.f780h
                int r5 = r5.b((int) r3)
                if (r5 < 0) goto L_0x0149
                androidx.recyclerview.widget.RecyclerView r9 = androidx.recyclerview.widget.RecyclerView.this
                androidx.recyclerview.widget.RecyclerView$g r9 = r9.p
                int r9 = r9.getItemCount()
                if (r5 >= r9) goto L_0x0149
                androidx.recyclerview.widget.RecyclerView r9 = androidx.recyclerview.widget.RecyclerView.this
                androidx.recyclerview.widget.RecyclerView$g r9 = r9.p
                int r9 = r9.getItemViewType(r5)
                androidx.recyclerview.widget.RecyclerView r10 = androidx.recyclerview.widget.RecyclerView.this
                androidx.recyclerview.widget.RecyclerView$g r10 = r10.p
                boolean r10 = r10.hasStableIds()
                if (r10 == 0) goto L_0x0096
                androidx.recyclerview.widget.RecyclerView r1 = androidx.recyclerview.widget.RecyclerView.this
                androidx.recyclerview.widget.RecyclerView$g r1 = r1.p
                long r10 = r1.getItemId(r5)
                androidx.recyclerview.widget.RecyclerView$c0 r1 = r6.a((long) r10, (int) r9, (boolean) r0)
                if (r1 == 0) goto L_0x0096
                r1.mPosition = r5
                r4 = 1
            L_0x0096:
                if (r1 != 0) goto L_0x00eb
                androidx.recyclerview.widget.RecyclerView$a0 r0 = r6.f797h
                if (r0 == 0) goto L_0x00eb
                android.view.View r0 = r0.a(r6, r3, r9)
                if (r0 == 0) goto L_0x00eb
                androidx.recyclerview.widget.RecyclerView r1 = androidx.recyclerview.widget.RecyclerView.this
                androidx.recyclerview.widget.RecyclerView$c0 r1 = r1.g((android.view.View) r0)
                if (r1 == 0) goto L_0x00ce
                boolean r0 = r1.shouldIgnore()
                if (r0 != 0) goto L_0x00b1
                goto L_0x00eb
            L_0x00b1:
                java.lang.IllegalArgumentException r0 = new java.lang.IllegalArgumentException
                java.lang.StringBuilder r1 = new java.lang.StringBuilder
                r1.<init>()
                java.lang.String r2 = "getViewForPositionAndType returned a view that is ignored. You must call stopIgnoring before returning this view."
                r1.append(r2)
                androidx.recyclerview.widget.RecyclerView r2 = androidx.recyclerview.widget.RecyclerView.this
                java.lang.String r2 = r2.i()
                r1.append(r2)
                java.lang.String r1 = r1.toString()
                r0.<init>(r1)
                throw r0
            L_0x00ce:
                java.lang.IllegalArgumentException r0 = new java.lang.IllegalArgumentException
                java.lang.StringBuilder r1 = new java.lang.StringBuilder
                r1.<init>()
                java.lang.String r2 = "getViewForPositionAndType returned a view which does not have a ViewHolder"
                r1.append(r2)
                androidx.recyclerview.widget.RecyclerView r2 = androidx.recyclerview.widget.RecyclerView.this
                java.lang.String r2 = r2.i()
                r1.append(r2)
                java.lang.String r1 = r1.toString()
                r0.<init>(r1)
                throw r0
            L_0x00eb:
                if (r1 != 0) goto L_0x0102
                androidx.recyclerview.widget.RecyclerView$u r0 = r16.d()
                androidx.recyclerview.widget.RecyclerView$c0 r0 = r0.a((int) r9)
                if (r0 == 0) goto L_0x0101
                r0.resetInternal()
                boolean r1 = androidx.recyclerview.widget.RecyclerView.D0
                if (r1 == 0) goto L_0x0101
                r6.f((androidx.recyclerview.widget.RecyclerView.c0) r0)
            L_0x0101:
                r1 = r0
            L_0x0102:
                if (r1 != 0) goto L_0x0181
                androidx.recyclerview.widget.RecyclerView r0 = androidx.recyclerview.widget.RecyclerView.this
                long r0 = r0.getNanoTime()
                r10 = 9223372036854775807(0x7fffffffffffffff, double:NaN)
                int r5 = (r19 > r10 ? 1 : (r19 == r10 ? 0 : -1))
                if (r5 == 0) goto L_0x0120
                androidx.recyclerview.widget.RecyclerView$u r10 = r6.f796g
                r11 = r9
                r12 = r0
                r14 = r19
                boolean r5 = r10.b(r11, r12, r14)
                if (r5 != 0) goto L_0x0120
                return r2
            L_0x0120:
                androidx.recyclerview.widget.RecyclerView r2 = androidx.recyclerview.widget.RecyclerView.this
                androidx.recyclerview.widget.RecyclerView$g r5 = r2.p
                androidx.recyclerview.widget.RecyclerView$c0 r2 = r5.createViewHolder(r2, r9)
                boolean r5 = androidx.recyclerview.widget.RecyclerView.G0
                if (r5 == 0) goto L_0x013b
                android.view.View r5 = r2.itemView
                androidx.recyclerview.widget.RecyclerView r5 = androidx.recyclerview.widget.RecyclerView.l(r5)
                if (r5 == 0) goto L_0x013b
                java.lang.ref.WeakReference r10 = new java.lang.ref.WeakReference
                r10.<init>(r5)
                r2.mNestedRecyclerView = r10
            L_0x013b:
                androidx.recyclerview.widget.RecyclerView r5 = androidx.recyclerview.widget.RecyclerView.this
                long r10 = r5.getNanoTime()
                androidx.recyclerview.widget.RecyclerView$u r5 = r6.f796g
                long r10 = r10 - r0
                r5.b(r9, r10)
                r9 = r2
                goto L_0x0182
            L_0x0149:
                java.lang.IndexOutOfBoundsException r0 = new java.lang.IndexOutOfBoundsException
                java.lang.StringBuilder r1 = new java.lang.StringBuilder
                r1.<init>()
                java.lang.String r2 = "Inconsistency detected. Invalid item position "
                r1.append(r2)
                r1.append(r3)
                java.lang.String r2 = "(offset:"
                r1.append(r2)
                r1.append(r5)
                java.lang.String r2 = ").state:"
                r1.append(r2)
                androidx.recyclerview.widget.RecyclerView r2 = androidx.recyclerview.widget.RecyclerView.this
                androidx.recyclerview.widget.RecyclerView$z r2 = r2.l0
                int r2 = r2.a()
                r1.append(r2)
                androidx.recyclerview.widget.RecyclerView r2 = androidx.recyclerview.widget.RecyclerView.this
                java.lang.String r2 = r2.i()
                r1.append(r2)
                java.lang.String r1 = r1.toString()
                r0.<init>(r1)
                throw r0
            L_0x0181:
                r9 = r1
            L_0x0182:
                r10 = r4
                if (r10 == 0) goto L_0x01bb
                androidx.recyclerview.widget.RecyclerView r0 = androidx.recyclerview.widget.RecyclerView.this
                androidx.recyclerview.widget.RecyclerView$z r0 = r0.l0
                boolean r0 = r0.d()
                if (r0 != 0) goto L_0x01bb
                r0 = 8192(0x2000, float:1.14794E-41)
                boolean r1 = r9.hasAnyOfTheFlags(r0)
                if (r1 == 0) goto L_0x01bb
                r9.setFlags(r8, r0)
                androidx.recyclerview.widget.RecyclerView r0 = androidx.recyclerview.widget.RecyclerView.this
                androidx.recyclerview.widget.RecyclerView$z r0 = r0.l0
                boolean r0 = r0.k
                if (r0 == 0) goto L_0x01bb
                int r0 = androidx.recyclerview.widget.RecyclerView.l.e(r9)
                r0 = r0 | 4096(0x1000, float:5.74E-42)
                androidx.recyclerview.widget.RecyclerView r1 = androidx.recyclerview.widget.RecyclerView.this
                androidx.recyclerview.widget.RecyclerView$l r2 = r1.Q
                androidx.recyclerview.widget.RecyclerView$z r1 = r1.l0
                java.util.List r4 = r9.getUnmodifiedPayloads()
                androidx.recyclerview.widget.RecyclerView$l$c r0 = r2.a((androidx.recyclerview.widget.RecyclerView.z) r1, (androidx.recyclerview.widget.RecyclerView.c0) r9, (int) r0, (java.util.List<java.lang.Object>) r4)
                androidx.recyclerview.widget.RecyclerView r1 = androidx.recyclerview.widget.RecyclerView.this
                r1.a((androidx.recyclerview.widget.RecyclerView.c0) r9, (androidx.recyclerview.widget.RecyclerView.l.c) r0)
            L_0x01bb:
                androidx.recyclerview.widget.RecyclerView r0 = androidx.recyclerview.widget.RecyclerView.this
                androidx.recyclerview.widget.RecyclerView$z r0 = r0.l0
                boolean r0 = r0.d()
                if (r0 == 0) goto L_0x01ce
                boolean r0 = r9.isBound()
                if (r0 == 0) goto L_0x01ce
                r9.mPreLayoutPosition = r3
                goto L_0x01e1
            L_0x01ce:
                boolean r0 = r9.isBound()
                if (r0 == 0) goto L_0x01e3
                boolean r0 = r9.needsUpdate()
                if (r0 != 0) goto L_0x01e3
                boolean r0 = r9.isInvalid()
                if (r0 == 0) goto L_0x01e1
                goto L_0x01e3
            L_0x01e1:
                r0 = 0
                goto L_0x01f6
            L_0x01e3:
                androidx.recyclerview.widget.RecyclerView r0 = androidx.recyclerview.widget.RecyclerView.this
                androidx.recyclerview.widget.a r0 = r0.f780h
                int r2 = r0.b((int) r3)
                r0 = r16
                r1 = r9
                r3 = r17
                r4 = r19
                boolean r0 = r0.a(r1, r2, r3, r4)
            L_0x01f6:
                android.view.View r1 = r9.itemView
                android.view.ViewGroup$LayoutParams r1 = r1.getLayoutParams()
                if (r1 != 0) goto L_0x020c
                androidx.recyclerview.widget.RecyclerView r1 = androidx.recyclerview.widget.RecyclerView.this
                android.view.ViewGroup$LayoutParams r1 = r1.generateDefaultLayoutParams()
                androidx.recyclerview.widget.RecyclerView$p r1 = (androidx.recyclerview.widget.RecyclerView.p) r1
                android.view.View r2 = r9.itemView
                r2.setLayoutParams(r1)
                goto L_0x0224
            L_0x020c:
                androidx.recyclerview.widget.RecyclerView r2 = androidx.recyclerview.widget.RecyclerView.this
                boolean r2 = r2.checkLayoutParams(r1)
                if (r2 != 0) goto L_0x0222
                androidx.recyclerview.widget.RecyclerView r2 = androidx.recyclerview.widget.RecyclerView.this
                android.view.ViewGroup$LayoutParams r1 = r2.generateLayoutParams((android.view.ViewGroup.LayoutParams) r1)
                androidx.recyclerview.widget.RecyclerView$p r1 = (androidx.recyclerview.widget.RecyclerView.p) r1
                android.view.View r2 = r9.itemView
                r2.setLayoutParams(r1)
                goto L_0x0224
            L_0x0222:
                androidx.recyclerview.widget.RecyclerView$p r1 = (androidx.recyclerview.widget.RecyclerView.p) r1
            L_0x0224:
                r1.a = r9
                if (r10 == 0) goto L_0x022b
                if (r0 == 0) goto L_0x022b
                goto L_0x022c
            L_0x022b:
                r7 = 0
            L_0x022c:
                r1.d = r7
                return r9
            L_0x022f:
                java.lang.IndexOutOfBoundsException r0 = new java.lang.IndexOutOfBoundsException
                java.lang.StringBuilder r1 = new java.lang.StringBuilder
                r1.<init>()
                java.lang.String r2 = "Invalid item position "
                r1.append(r2)
                r1.append(r3)
                java.lang.String r2 = "("
                r1.append(r2)
                r1.append(r3)
                java.lang.String r2 = "). Item count:"
                r1.append(r2)
                androidx.recyclerview.widget.RecyclerView r2 = androidx.recyclerview.widget.RecyclerView.this
                androidx.recyclerview.widget.RecyclerView$z r2 = r2.l0
                int r2 = r2.a()
                r1.append(r2)
                androidx.recyclerview.widget.RecyclerView r2 = androidx.recyclerview.widget.RecyclerView.this
                java.lang.String r2 = r2.i()
                r1.append(r2)
                java.lang.String r1 = r1.toString()
                r0.<init>(r1)
                throw r0
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.recyclerview.widget.RecyclerView.v.a(int, boolean, long):androidx.recyclerview.widget.RecyclerView$c0");
        }

        /* access modifiers changed from: package-private */
        public View c(int i2) {
            return this.a.get(i2).itemView;
        }

        /* access modifiers changed from: package-private */
        public void c() {
            this.a.clear();
            ArrayList<c0> arrayList = this.b;
            if (arrayList != null) {
                arrayList.clear();
            }
        }

        /* access modifiers changed from: package-private */
        public void c(int i2, int i3) {
            int i4;
            int i5 = i3 + i2;
            for (int size = this.c.size() - 1; size >= 0; size--) {
                c0 c0Var = this.c.get(size);
                if (c0Var != null && (i4 = c0Var.mPosition) >= i2 && i4 < i5) {
                    c0Var.addFlags(2);
                    e(size);
                }
            }
        }

        /* access modifiers changed from: package-private */
        public c0 b(int i2) {
            int size;
            int b2;
            ArrayList<c0> arrayList = this.b;
            if (!(arrayList == null || (size = arrayList.size()) == 0)) {
                int i3 = 0;
                int i4 = 0;
                while (i4 < size) {
                    c0 c0Var = this.b.get(i4);
                    if (c0Var.wasReturnedFromScrap() || c0Var.getLayoutPosition() != i2) {
                        i4++;
                    } else {
                        c0Var.addFlags(32);
                        return c0Var;
                    }
                }
                if (RecyclerView.this.p.hasStableIds() && (b2 = RecyclerView.this.f780h.b(i2)) > 0 && b2 < RecyclerView.this.p.getItemCount()) {
                    long itemId = RecyclerView.this.p.getItemId(b2);
                    while (i3 < size) {
                        c0 c0Var2 = this.b.get(i3);
                        if (c0Var2.wasReturnedFromScrap() || c0Var2.getItemId() != itemId) {
                            i3++;
                        } else {
                            c0Var2.addFlags(32);
                            return c0Var2;
                        }
                    }
                }
            }
            return null;
        }

        /* access modifiers changed from: package-private */
        public void b(int i2, int i3) {
            int i4;
            int i5;
            int i6;
            int i7;
            if (i2 < i3) {
                i6 = -1;
                i5 = i2;
                i4 = i3;
            } else {
                i6 = 1;
                i4 = i2;
                i5 = i3;
            }
            int size = this.c.size();
            for (int i8 = 0; i8 < size; i8++) {
                c0 c0Var = this.c.get(i8);
                if (c0Var != null && (i7 = c0Var.mPosition) >= i5 && i7 <= i4) {
                    if (i7 == i2) {
                        c0Var.offsetPosition(i3 - i2, false);
                    } else {
                        c0Var.offsetPosition(i6, false);
                    }
                }
            }
        }

        /* access modifiers changed from: package-private */
        public void b() {
            int size = this.c.size();
            for (int i2 = 0; i2 < size; i2++) {
                this.c.get(i2).clearOldPosition();
            }
            int size2 = this.a.size();
            for (int i3 = 0; i3 < size2; i3++) {
                this.a.get(i3).clearOldPosition();
            }
            ArrayList<c0> arrayList = this.b;
            if (arrayList != null) {
                int size3 = arrayList.size();
                for (int i4 = 0; i4 < size3; i4++) {
                    this.b.get(i4).clearOldPosition();
                }
            }
        }

        private void a(ViewGroup viewGroup, boolean z) {
            for (int childCount = viewGroup.getChildCount() - 1; childCount >= 0; childCount--) {
                View childAt = viewGroup.getChildAt(childCount);
                if (childAt instanceof ViewGroup) {
                    a((ViewGroup) childAt, true);
                }
            }
            if (z) {
                if (viewGroup.getVisibility() == 4) {
                    viewGroup.setVisibility(0);
                    viewGroup.setVisibility(4);
                    return;
                }
                int visibility = viewGroup.getVisibility();
                viewGroup.setVisibility(4);
                viewGroup.setVisibility(visibility);
            }
        }

        /* access modifiers changed from: package-private */
        public void a(c0 c0Var, boolean z) {
            RecyclerView.e(c0Var);
            View view = c0Var.itemView;
            s sVar = RecyclerView.this.s0;
            if (sVar != null) {
                androidx.core.h.a b2 = sVar.b();
                androidx.core.h.v.a(view, b2 instanceof s.a ? ((s.a) b2).c(view) : null);
            }
            if (z) {
                a(c0Var);
            }
            c0Var.mOwnerRecyclerView = null;
            d().a(c0Var);
        }

        /* access modifiers changed from: package-private */
        public void a(View view) {
            c0 m = RecyclerView.m(view);
            m.mScrapContainer = null;
            m.mInChangeScrap = false;
            m.clearReturnedFromScrapFlag();
            b(m);
        }

        /* access modifiers changed from: package-private */
        public c0 a(int i2, boolean z) {
            View b2;
            int size = this.a.size();
            int i3 = 0;
            int i4 = 0;
            while (i4 < size) {
                c0 c0Var = this.a.get(i4);
                if (c0Var.wasReturnedFromScrap() || c0Var.getLayoutPosition() != i2 || c0Var.isInvalid() || (!RecyclerView.this.l0.f806h && c0Var.isRemoved())) {
                    i4++;
                } else {
                    c0Var.addFlags(32);
                    return c0Var;
                }
            }
            if (z || (b2 = RecyclerView.this.f781i.b(i2)) == null) {
                int size2 = this.c.size();
                while (i3 < size2) {
                    c0 c0Var2 = this.c.get(i3);
                    if (c0Var2.isInvalid() || c0Var2.getLayoutPosition() != i2 || c0Var2.isAttachedToTransitionOverlay()) {
                        i3++;
                    } else {
                        if (!z) {
                            this.c.remove(i3);
                        }
                        return c0Var2;
                    }
                }
                return null;
            }
            c0 m = RecyclerView.m(b2);
            RecyclerView.this.f781i.f(b2);
            int b3 = RecyclerView.this.f781i.b(b2);
            if (b3 != -1) {
                RecyclerView.this.f781i.a(b3);
                c(b2);
                m.addFlags(8224);
                return m;
            }
            throw new IllegalStateException("layout index should not be -1 after unhiding a view:" + m + RecyclerView.this.i());
        }

        /* access modifiers changed from: package-private */
        public c0 a(long j2, int i2, boolean z) {
            for (int size = this.a.size() - 1; size >= 0; size--) {
                c0 c0Var = this.a.get(size);
                if (c0Var.getItemId() == j2 && !c0Var.wasReturnedFromScrap()) {
                    if (i2 == c0Var.getItemViewType()) {
                        c0Var.addFlags(32);
                        if (c0Var.isRemoved() && !RecyclerView.this.l0.d()) {
                            c0Var.setFlags(2, 14);
                        }
                        return c0Var;
                    } else if (!z) {
                        this.a.remove(size);
                        RecyclerView.this.removeDetachedView(c0Var.itemView, false);
                        a(c0Var.itemView);
                    }
                }
            }
            int size2 = this.c.size();
            while (true) {
                size2--;
                if (size2 < 0) {
                    return null;
                }
                c0 c0Var2 = this.c.get(size2);
                if (c0Var2.getItemId() == j2 && !c0Var2.isAttachedToTransitionOverlay()) {
                    if (i2 == c0Var2.getItemViewType()) {
                        if (!z) {
                            this.c.remove(size2);
                        }
                        return c0Var2;
                    } else if (!z) {
                        e(size2);
                        return null;
                    }
                }
            }
        }

        /* access modifiers changed from: package-private */
        public void a(c0 c0Var) {
            w wVar = RecyclerView.this.r;
            if (wVar != null) {
                wVar.a(c0Var);
            }
            g gVar = RecyclerView.this.p;
            if (gVar != null) {
                gVar.onViewRecycled(c0Var);
            }
            RecyclerView recyclerView = RecyclerView.this;
            if (recyclerView.l0 != null) {
                recyclerView.f782j.h(c0Var);
            }
        }

        /* access modifiers changed from: package-private */
        public void a(g gVar, g gVar2, boolean z) {
            a();
            d().a(gVar, gVar2, z);
        }

        /* access modifiers changed from: package-private */
        public void a(int i2, int i3) {
            int size = this.c.size();
            for (int i4 = 0; i4 < size; i4++) {
                c0 c0Var = this.c.get(i4);
                if (c0Var != null && c0Var.mPosition >= i2) {
                    c0Var.offsetPosition(i3, true);
                }
            }
        }

        /* access modifiers changed from: package-private */
        public void a(int i2, int i3, boolean z) {
            int i4 = i2 + i3;
            for (int size = this.c.size() - 1; size >= 0; size--) {
                c0 c0Var = this.c.get(size);
                if (c0Var != null) {
                    int i5 = c0Var.mPosition;
                    if (i5 >= i4) {
                        c0Var.offsetPosition(-i3, z);
                    } else if (i5 >= i2) {
                        c0Var.addFlags(8);
                        e(size);
                    }
                }
            }
        }

        /* access modifiers changed from: package-private */
        public void a(a0 a0Var) {
            this.f797h = a0Var;
        }

        /* access modifiers changed from: package-private */
        public void a(u uVar) {
            u uVar2 = this.f796g;
            if (uVar2 != null) {
                uVar2.c();
            }
            this.f796g = uVar;
            if (uVar != null && RecyclerView.this.getAdapter() != null) {
                this.f796g.a();
            }
        }
    }

    public interface w {
        void a(c0 c0Var);
    }

    public static abstract class y {
        private int a = -1;
        private RecyclerView b;
        private o c;
        private boolean d;
        private boolean e;

        /* renamed from: f  reason: collision with root package name */
        private View f799f;

        /* renamed from: g  reason: collision with root package name */
        private final a f800g = new a(0, 0);

        /* renamed from: h  reason: collision with root package name */
        private boolean f801h;

        public static class a {
            private int a;
            private int b;
            private int c;
            private int d;
            private Interpolator e;

            /* renamed from: f  reason: collision with root package name */
            private boolean f802f;

            /* renamed from: g  reason: collision with root package name */
            private int f803g;

            public a(int i2, int i3) {
                this(i2, i3, Integer.MIN_VALUE, (Interpolator) null);
            }

            private void b() {
                if (this.e != null && this.c < 1) {
                    throw new IllegalStateException("If you provide an interpolator, you must set a positive duration");
                } else if (this.c < 1) {
                    throw new IllegalStateException("Scroll duration must be a positive number");
                }
            }

            public void a(int i2) {
                this.d = i2;
            }

            public a(int i2, int i3, int i4, Interpolator interpolator) {
                this.d = -1;
                this.f802f = false;
                this.f803g = 0;
                this.a = i2;
                this.b = i3;
                this.c = i4;
                this.e = interpolator;
            }

            /* access modifiers changed from: package-private */
            public boolean a() {
                return this.d >= 0;
            }

            /* access modifiers changed from: package-private */
            public void a(RecyclerView recyclerView) {
                int i2 = this.d;
                if (i2 >= 0) {
                    this.d = -1;
                    recyclerView.e(i2);
                    this.f802f = false;
                } else if (this.f802f) {
                    b();
                    recyclerView.i0.a(this.a, this.b, this.c, this.e);
                    int i3 = this.f803g + 1;
                    this.f803g = i3;
                    if (i3 > 10) {
                        Log.e("RecyclerView", "Smooth Scroll action is being updated too frequently. Make sure you are not changing it unless necessary");
                    }
                    this.f802f = false;
                } else {
                    this.f803g = 0;
                }
            }

            public void a(int i2, int i3, int i4, Interpolator interpolator) {
                this.a = i2;
                this.b = i3;
                this.c = i4;
                this.e = interpolator;
                this.f802f = true;
            }
        }

        public interface b {
            PointF a(int i2);
        }

        /* access modifiers changed from: protected */
        public abstract void a(int i2, int i3, z zVar, a aVar);

        /* access modifiers changed from: protected */
        public abstract void a(View view, z zVar, a aVar);

        /* access modifiers changed from: package-private */
        public void a(RecyclerView recyclerView, o oVar) {
            recyclerView.i0.b();
            if (this.f801h) {
                Log.w("RecyclerView", "An instance of " + getClass().getSimpleName() + " was started more than once. Each instance of" + getClass().getSimpleName() + " is intended to only be used once. You should create a new instance for each use.");
            }
            this.b = recyclerView;
            this.c = oVar;
            int i2 = this.a;
            if (i2 != -1) {
                recyclerView.l0.a = i2;
                this.e = true;
                this.d = true;
                this.f799f = b(c());
                f();
                this.b.i0.a();
                this.f801h = true;
                return;
            }
            throw new IllegalArgumentException("Invalid target position");
        }

        public o b() {
            return this.c;
        }

        public void c(int i2) {
            this.a = i2;
        }

        public boolean d() {
            return this.d;
        }

        public boolean e() {
            return this.e;
        }

        /* access modifiers changed from: protected */
        public abstract void f();

        /* access modifiers changed from: protected */
        public abstract void g();

        /* access modifiers changed from: protected */
        public final void h() {
            if (this.e) {
                this.e = false;
                g();
                this.b.l0.a = -1;
                this.f799f = null;
                this.a = -1;
                this.d = false;
                this.c.a(this);
                this.c = null;
                this.b = null;
            }
        }

        public View b(int i2) {
            return this.b.q.c(i2);
        }

        public int c() {
            return this.a;
        }

        /* access modifiers changed from: protected */
        public void b(View view) {
            if (a(view) == c()) {
                this.f799f = view;
            }
        }

        public PointF a(int i2) {
            o b2 = b();
            if (b2 instanceof b) {
                return ((b) b2).a(i2);
            }
            Log.w("RecyclerView", "You should override computeScrollVectorForPosition when the LayoutManager does not implement " + b.class.getCanonicalName());
            return null;
        }

        /* access modifiers changed from: package-private */
        public void a(int i2, int i3) {
            PointF a2;
            RecyclerView recyclerView = this.b;
            if (this.a == -1 || recyclerView == null) {
                h();
            }
            if (!(!this.d || this.f799f != null || this.c == null || (a2 = a(this.a)) == null || (a2.x == 0.0f && a2.y == 0.0f))) {
                recyclerView.a((int) Math.signum(a2.x), (int) Math.signum(a2.y), (int[]) null);
            }
            this.d = false;
            View view = this.f799f;
            if (view != null) {
                if (a(view) == this.a) {
                    a(this.f799f, recyclerView.l0, this.f800g);
                    this.f800g.a(recyclerView);
                    h();
                } else {
                    Log.e("RecyclerView", "Passed over target position while smooth scrolling.");
                    this.f799f = null;
                }
            }
            if (this.e) {
                a(i2, i3, recyclerView.l0, this.f800g);
                boolean a3 = this.f800g.a();
                this.f800g.a(recyclerView);
                if (a3 && this.e) {
                    this.d = true;
                    recyclerView.i0.a();
                }
            }
        }

        public int a(View view) {
            return this.b.f(view);
        }

        public int a() {
            return this.b.q.e();
        }

        /* access modifiers changed from: protected */
        public void a(PointF pointF) {
            float f2 = pointF.x;
            float f3 = pointF.y;
            float sqrt = (float) Math.sqrt((double) ((f2 * f2) + (f3 * f3)));
            pointF.x /= sqrt;
            pointF.y /= sqrt;
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v14, resolved type: java.lang.Class<?>[]} */
    /* JADX WARNING: Multi-variable type inference failed */
    static {
        /*
            r0 = 1
            int[] r1 = new int[r0]
            r2 = 16843830(0x1010436, float:2.369658E-38)
            r3 = 0
            r1[r3] = r2
            C0 = r1
            int r1 = android.os.Build.VERSION.SDK_INT
            r2 = 18
            if (r1 == r2) goto L_0x001c
            r2 = 19
            if (r1 == r2) goto L_0x001c
            r2 = 20
            if (r1 != r2) goto L_0x001a
            goto L_0x001c
        L_0x001a:
            r1 = 0
            goto L_0x001d
        L_0x001c:
            r1 = 1
        L_0x001d:
            D0 = r1
            int r1 = android.os.Build.VERSION.SDK_INT
            r2 = 23
            if (r1 < r2) goto L_0x0027
            r1 = 1
            goto L_0x0028
        L_0x0027:
            r1 = 0
        L_0x0028:
            E0 = r1
            int r1 = android.os.Build.VERSION.SDK_INT
            r2 = 16
            if (r1 < r2) goto L_0x0032
            r1 = 1
            goto L_0x0033
        L_0x0032:
            r1 = 0
        L_0x0033:
            F0 = r1
            int r1 = android.os.Build.VERSION.SDK_INT
            r2 = 21
            if (r1 < r2) goto L_0x003d
            r1 = 1
            goto L_0x003e
        L_0x003d:
            r1 = 0
        L_0x003e:
            G0 = r1
            int r1 = android.os.Build.VERSION.SDK_INT
            r2 = 15
            if (r1 > r2) goto L_0x0048
            r1 = 1
            goto L_0x0049
        L_0x0048:
            r1 = 0
        L_0x0049:
            H0 = r1
            int r1 = android.os.Build.VERSION.SDK_INT
            if (r1 > r2) goto L_0x0051
            r1 = 1
            goto L_0x0052
        L_0x0051:
            r1 = 0
        L_0x0052:
            I0 = r1
            r1 = 4
            java.lang.Class[] r1 = new java.lang.Class[r1]
            java.lang.Class<android.content.Context> r2 = android.content.Context.class
            r1[r3] = r2
            java.lang.Class<android.util.AttributeSet> r2 = android.util.AttributeSet.class
            r1[r0] = r2
            r0 = 2
            java.lang.Class r2 = java.lang.Integer.TYPE
            r1[r0] = r2
            r0 = 3
            r1[r0] = r2
            J0 = r1
            androidx.recyclerview.widget.RecyclerView$c r0 = new androidx.recyclerview.widget.RecyclerView$c
            r0.<init>()
            K0 = r0
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.recyclerview.widget.RecyclerView.<clinit>():void");
    }

    public RecyclerView(Context context) {
        this(context, (AttributeSet) null);
    }

    private void A() {
        int i2 = this.D;
        this.D = 0;
        if (i2 != 0 && n()) {
            AccessibilityEvent obtain = AccessibilityEvent.obtain();
            obtain.setEventType(2048);
            androidx.core.h.e0.b.a(obtain, i2);
            sendAccessibilityEventUnchecked(obtain);
        }
    }

    private void B() {
        boolean z2 = true;
        this.l0.a(1);
        a(this.l0);
        this.l0.f808j = false;
        x();
        this.f782j.a();
        r();
        J();
        O();
        z zVar = this.l0;
        if (!zVar.k || !this.p0) {
            z2 = false;
        }
        zVar.f807i = z2;
        this.p0 = false;
        this.o0 = false;
        z zVar2 = this.l0;
        zVar2.f806h = zVar2.l;
        zVar2.f804f = this.p.getItemCount();
        a(this.u0);
        if (this.l0.k) {
            int a2 = this.f781i.a();
            for (int i2 = 0; i2 < a2; i2++) {
                c0 m2 = m(this.f781i.c(i2));
                if (!m2.shouldIgnore() && (!m2.isInvalid() || this.p.hasStableIds())) {
                    this.f782j.c(m2, this.Q.a(this.l0, m2, l.e(m2), m2.getUnmodifiedPayloads()));
                    if (this.l0.f807i && m2.isUpdated() && !m2.isRemoved() && !m2.shouldIgnore() && !m2.isInvalid()) {
                        this.f782j.a(c(m2), m2);
                    }
                }
            }
        }
        if (this.l0.l) {
            w();
            z zVar3 = this.l0;
            boolean z3 = zVar3.f805g;
            zVar3.f805g = false;
            this.q.e(this.f778f, zVar3);
            this.l0.f805g = z3;
            for (int i3 = 0; i3 < this.f781i.a(); i3++) {
                c0 m3 = m(this.f781i.c(i3));
                if (!m3.shouldIgnore() && !this.f782j.c(m3)) {
                    int e2 = l.e(m3);
                    boolean hasAnyOfTheFlags = m3.hasAnyOfTheFlags(8192);
                    if (!hasAnyOfTheFlags) {
                        e2 |= 4096;
                    }
                    l.c a3 = this.Q.a(this.l0, m3, e2, m3.getUnmodifiedPayloads());
                    if (hasAnyOfTheFlags) {
                        a(m3, a3);
                    } else {
                        this.f782j.a(m3, a3);
                    }
                }
            }
            a();
        } else {
            a();
        }
        s();
        c(false);
        this.l0.e = 2;
    }

    private void C() {
        x();
        r();
        this.l0.a(6);
        this.f780h.b();
        this.l0.f804f = this.p.getItemCount();
        z zVar = this.l0;
        zVar.d = 0;
        zVar.f806h = false;
        this.q.e(this.f778f, zVar);
        z zVar2 = this.l0;
        zVar2.f805g = false;
        this.f779g = null;
        zVar2.k = zVar2.k && this.Q != null;
        this.l0.e = 4;
        s();
        c(false);
    }

    private void D() {
        this.l0.a(4);
        x();
        r();
        z zVar = this.l0;
        zVar.e = 1;
        if (zVar.k) {
            for (int a2 = this.f781i.a() - 1; a2 >= 0; a2--) {
                c0 m2 = m(this.f781i.c(a2));
                if (!m2.shouldIgnore()) {
                    long c2 = c(m2);
                    l.c a3 = this.Q.a(this.l0, m2);
                    c0 a4 = this.f782j.a(c2);
                    if (a4 == null || a4.shouldIgnore()) {
                        this.f782j.b(m2, a3);
                    } else {
                        boolean b2 = this.f782j.b(a4);
                        boolean b3 = this.f782j.b(m2);
                        if (!b2 || a4 != m2) {
                            l.c f2 = this.f782j.f(a4);
                            this.f782j.b(m2, a3);
                            l.c e2 = this.f782j.e(m2);
                            if (f2 == null) {
                                a(c2, m2, a4);
                            } else {
                                a(a4, m2, f2, e2, b2, b3);
                            }
                        } else {
                            this.f782j.b(m2, a3);
                        }
                    }
                }
            }
            this.f782j.a(this.B0);
        }
        this.q.c(this.f778f);
        z zVar2 = this.l0;
        zVar2.c = zVar2.f804f;
        this.H = false;
        this.I = false;
        zVar2.k = false;
        zVar2.l = false;
        this.q.f792h = false;
        ArrayList<c0> arrayList = this.f778f.b;
        if (arrayList != null) {
            arrayList.clear();
        }
        o oVar = this.q;
        if (oVar.n) {
            oVar.m = 0;
            oVar.n = false;
            this.f778f.j();
        }
        this.q.g(this.l0);
        s();
        c(false);
        this.f782j.a();
        int[] iArr = this.u0;
        if (k(iArr[0], iArr[1])) {
            d(0, 0);
        }
        K();
        M();
    }

    private View E() {
        c0 c2;
        int i2 = this.l0.m;
        if (i2 == -1) {
            i2 = 0;
        }
        int a2 = this.l0.a();
        int i3 = i2;
        while (i3 < a2) {
            c0 c3 = c(i3);
            if (c3 == null) {
                break;
            } else if (c3.itemView.hasFocusable()) {
                return c3.itemView;
            } else {
                i3++;
            }
        }
        int min = Math.min(a2, i2);
        while (true) {
            min--;
            if (min < 0 || (c2 = c(min)) == null) {
                return null;
            }
            if (c2.itemView.hasFocusable()) {
                return c2.itemView;
            }
        }
    }

    private boolean F() {
        int a2 = this.f781i.a();
        for (int i2 = 0; i2 < a2; i2++) {
            c0 m2 = m(this.f781i.c(i2));
            if (m2 != null && !m2.shouldIgnore() && m2.isUpdated()) {
                return true;
            }
        }
        return false;
    }

    @SuppressLint({"InlinedApi"})
    private void G() {
        if (androidx.core.h.v.n(this) == 0) {
            androidx.core.h.v.i(this, 8);
        }
    }

    private void H() {
        this.f781i = new d(new e());
    }

    private boolean I() {
        return this.Q != null && this.q.D();
    }

    private void J() {
        if (this.H) {
            this.f780h.f();
            if (this.I) {
                this.q.d(this);
            }
        }
        if (I()) {
            this.f780h.e();
        } else {
            this.f780h.b();
        }
        boolean z2 = false;
        boolean z3 = this.o0 || this.p0;
        this.l0.k = this.y && this.Q != null && (this.H || z3 || this.q.f792h) && (!this.H || this.p.hasStableIds());
        z zVar = this.l0;
        if (zVar.k && z3 && !this.H && I()) {
            z2 = true;
        }
        zVar.l = z2;
    }

    private void K() {
        View findViewById;
        if (this.h0 && this.p != null && hasFocus() && getDescendantFocusability() != 393216) {
            if (getDescendantFocusability() != 131072 || !isFocused()) {
                if (!isFocused()) {
                    View focusedChild = getFocusedChild();
                    if (!I0 || (focusedChild.getParent() != null && focusedChild.hasFocus())) {
                        if (!this.f781i.c(focusedChild)) {
                            return;
                        }
                    } else if (this.f781i.a() == 0) {
                        requestFocus();
                        return;
                    }
                }
                View view = null;
                c0 a2 = (this.l0.n == -1 || !this.p.hasStableIds()) ? null : a(this.l0.n);
                if (a2 != null && !this.f781i.c(a2.itemView) && a2.itemView.hasFocusable()) {
                    view = a2.itemView;
                } else if (this.f781i.a() > 0) {
                    view = E();
                }
                if (view != null) {
                    int i2 = this.l0.o;
                    if (!(((long) i2) == -1 || (findViewById = view.findViewById(i2)) == null || !findViewById.isFocusable())) {
                        view = findViewById;
                    }
                    view.requestFocus();
                }
            }
        }
    }

    private void L() {
        boolean z2;
        EdgeEffect edgeEffect = this.M;
        if (edgeEffect != null) {
            edgeEffect.onRelease();
            z2 = this.M.isFinished();
        } else {
            z2 = false;
        }
        EdgeEffect edgeEffect2 = this.N;
        if (edgeEffect2 != null) {
            edgeEffect2.onRelease();
            z2 |= this.N.isFinished();
        }
        EdgeEffect edgeEffect3 = this.O;
        if (edgeEffect3 != null) {
            edgeEffect3.onRelease();
            z2 |= this.O.isFinished();
        }
        EdgeEffect edgeEffect4 = this.P;
        if (edgeEffect4 != null) {
            edgeEffect4.onRelease();
            z2 |= this.P.isFinished();
        }
        if (z2) {
            androidx.core.h.v.H(this);
        }
    }

    private void M() {
        z zVar = this.l0;
        zVar.n = -1;
        zVar.m = -1;
        zVar.o = -1;
    }

    private void N() {
        VelocityTracker velocityTracker = this.T;
        if (velocityTracker != null) {
            velocityTracker.clear();
        }
        a(0);
        L();
    }

    private void O() {
        int i2;
        c0 c0Var = null;
        View focusedChild = (!this.h0 || !hasFocus() || this.p == null) ? null : getFocusedChild();
        if (focusedChild != null) {
            c0Var = d(focusedChild);
        }
        if (c0Var == null) {
            M();
            return;
        }
        this.l0.n = this.p.hasStableIds() ? c0Var.getItemId() : -1;
        z zVar = this.l0;
        if (this.H) {
            i2 = -1;
        } else if (c0Var.isRemoved()) {
            i2 = c0Var.mOldPosition;
        } else {
            i2 = c0Var.getAdapterPosition();
        }
        zVar.m = i2;
        this.l0.o = n(c0Var.itemView);
    }

    private void P() {
        this.i0.b();
        o oVar = this.q;
        if (oVar != null) {
            oVar.C();
        }
    }

    private void d(c0 c0Var) {
        View view = c0Var.itemView;
        boolean z2 = view.getParent() == this;
        this.f778f.c(g(view));
        if (c0Var.isTmpDetached()) {
            this.f781i.a(view, -1, view.getLayoutParams(), true);
        } else if (!z2) {
            this.f781i.a(view, true);
        } else {
            this.f781i.a(view);
        }
    }

    private androidx.core.h.m getScrollingChildHelper() {
        if (this.v0 == null) {
            this.v0 = new androidx.core.h.m(this);
        }
        return this.v0;
    }

    private void z() {
        N();
        setScrollState(0);
    }

    public void addFocusables(ArrayList<View> arrayList, int i2, int i3) {
        o oVar = this.q;
        if (oVar == null || !oVar.a(this, arrayList, i2, i3)) {
            super.addFocusables(arrayList, i2, i3);
        }
    }

    public void addOnChildAttachStateChangeListener(q qVar) {
        if (this.G == null) {
            this.G = new ArrayList();
        }
        this.G.add(qVar);
    }

    public void addOnItemTouchListener(s sVar) {
        this.t.add(sVar);
    }

    public void addOnScrollListener(t tVar) {
        if (this.n0 == null) {
            this.n0 = new ArrayList();
        }
        this.n0.add(tVar);
    }

    public void b(n nVar) {
        o oVar = this.q;
        if (oVar != null) {
            oVar.a("Cannot remove item decoration during a scroll  or layout");
        }
        this.s.remove(nVar);
        if (this.s.isEmpty()) {
            setWillNotDraw(getOverScrollMode() == 2);
        }
        p();
        requestLayout();
    }

    /* access modifiers changed from: package-private */
    public void c(boolean z2) {
        if (this.z < 1) {
            this.z = 1;
        }
        if (!z2 && !this.B) {
            this.A = false;
        }
        if (this.z == 1) {
            if (z2 && this.A && !this.B && this.q != null && this.p != null) {
                c();
            }
            if (!this.B) {
                this.A = false;
            }
        }
        this.z--;
    }

    /* access modifiers changed from: protected */
    public boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return (layoutParams instanceof p) && this.q.a((p) layoutParams);
    }

    public int computeHorizontalScrollExtent() {
        o oVar = this.q;
        if (oVar != null && oVar.a()) {
            return this.q.a(this.l0);
        }
        return 0;
    }

    public int computeHorizontalScrollOffset() {
        o oVar = this.q;
        if (oVar != null && oVar.a()) {
            return this.q.b(this.l0);
        }
        return 0;
    }

    public int computeHorizontalScrollRange() {
        o oVar = this.q;
        if (oVar != null && oVar.a()) {
            return this.q.c(this.l0);
        }
        return 0;
    }

    public int computeVerticalScrollExtent() {
        o oVar = this.q;
        if (oVar != null && oVar.b()) {
            return this.q.d(this.l0);
        }
        return 0;
    }

    public int computeVerticalScrollOffset() {
        o oVar = this.q;
        if (oVar != null && oVar.b()) {
            return this.q.e(this.l0);
        }
        return 0;
    }

    public int computeVerticalScrollRange() {
        o oVar = this.q;
        if (oVar != null && oVar.b()) {
            return this.q.f(this.l0);
        }
        return 0;
    }

    public boolean dispatchNestedFling(float f2, float f3, boolean z2) {
        return getScrollingChildHelper().a(f2, f3, z2);
    }

    public boolean dispatchNestedPreFling(float f2, float f3) {
        return getScrollingChildHelper().a(f2, f3);
    }

    public boolean dispatchNestedPreScroll(int i2, int i3, int[] iArr, int[] iArr2) {
        return getScrollingChildHelper().a(i2, i3, iArr, iArr2);
    }

    public boolean dispatchNestedScroll(int i2, int i3, int i4, int i5, int[] iArr) {
        return getScrollingChildHelper().a(i2, i3, i4, i5, iArr);
    }

    public boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        onPopulateAccessibilityEvent(accessibilityEvent);
        return true;
    }

    /* access modifiers changed from: protected */
    public void dispatchRestoreInstanceState(SparseArray<Parcelable> sparseArray) {
        dispatchThawSelfOnly(sparseArray);
    }

    /* access modifiers changed from: protected */
    public void dispatchSaveInstanceState(SparseArray<Parcelable> sparseArray) {
        dispatchFreezeSelfOnly(sparseArray);
    }

    public void draw(Canvas canvas) {
        boolean z2;
        super.draw(canvas);
        int size = this.s.size();
        boolean z3 = false;
        for (int i2 = 0; i2 < size; i2++) {
            this.s.get(i2).b(canvas, this, this.l0);
        }
        EdgeEffect edgeEffect = this.M;
        boolean z4 = true;
        if (edgeEffect == null || edgeEffect.isFinished()) {
            z2 = false;
        } else {
            int save = canvas.save();
            int paddingBottom = this.k ? getPaddingBottom() : 0;
            canvas.rotate(270.0f);
            canvas.translate((float) ((-getHeight()) + paddingBottom), 0.0f);
            EdgeEffect edgeEffect2 = this.M;
            z2 = edgeEffect2 != null && edgeEffect2.draw(canvas);
            canvas.restoreToCount(save);
        }
        EdgeEffect edgeEffect3 = this.N;
        if (edgeEffect3 != null && !edgeEffect3.isFinished()) {
            int save2 = canvas.save();
            if (this.k) {
                canvas.translate((float) getPaddingLeft(), (float) getPaddingTop());
            }
            EdgeEffect edgeEffect4 = this.N;
            z2 |= edgeEffect4 != null && edgeEffect4.draw(canvas);
            canvas.restoreToCount(save2);
        }
        EdgeEffect edgeEffect5 = this.O;
        if (edgeEffect5 != null && !edgeEffect5.isFinished()) {
            int save3 = canvas.save();
            int width = getWidth();
            int paddingTop = this.k ? getPaddingTop() : 0;
            canvas.rotate(90.0f);
            canvas.translate((float) (-paddingTop), (float) (-width));
            EdgeEffect edgeEffect6 = this.O;
            z2 |= edgeEffect6 != null && edgeEffect6.draw(canvas);
            canvas.restoreToCount(save3);
        }
        EdgeEffect edgeEffect7 = this.P;
        if (edgeEffect7 != null && !edgeEffect7.isFinished()) {
            int save4 = canvas.save();
            canvas.rotate(180.0f);
            if (this.k) {
                canvas.translate((float) ((-getWidth()) + getPaddingRight()), (float) ((-getHeight()) + getPaddingBottom()));
            } else {
                canvas.translate((float) (-getWidth()), (float) (-getHeight()));
            }
            EdgeEffect edgeEffect8 = this.P;
            if (edgeEffect8 != null && edgeEffect8.draw(canvas)) {
                z3 = true;
            }
            z2 |= z3;
            canvas.restoreToCount(save4);
        }
        if (z2 || this.Q == null || this.s.size() <= 0 || !this.Q.g()) {
            z4 = z2;
        }
        if (z4) {
            androidx.core.h.v.H(this);
        }
    }

    public boolean drawChild(Canvas canvas, View view, long j2) {
        return super.drawChild(canvas, view, j2);
    }

    /* access modifiers changed from: package-private */
    public void e(int i2) {
        if (this.q != null) {
            setScrollState(2);
            this.q.i(i2);
            awakenScrollBars();
        }
    }

    /* access modifiers changed from: package-private */
    public void f() {
        if (this.M == null) {
            EdgeEffect a2 = this.L.a(this, 0);
            this.M = a2;
            if (this.k) {
                a2.setSize((getMeasuredHeight() - getPaddingTop()) - getPaddingBottom(), (getMeasuredWidth() - getPaddingLeft()) - getPaddingRight());
            } else {
                a2.setSize(getMeasuredHeight(), getMeasuredWidth());
            }
        }
    }

    public View focusSearch(View view, int i2) {
        View view2;
        boolean z2;
        View d2 = this.q.d(view, i2);
        if (d2 != null) {
            return d2;
        }
        boolean z3 = true;
        boolean z4 = this.p != null && this.q != null && !o() && !this.B;
        FocusFinder instance = FocusFinder.getInstance();
        if (!z4 || !(i2 == 2 || i2 == 1)) {
            View findNextFocus = instance.findNextFocus(this, view, i2);
            if (findNextFocus != null || !z4) {
                view2 = findNextFocus;
            } else {
                b();
                if (c(view) == null) {
                    return null;
                }
                x();
                view2 = this.q.a(view, i2, this.f778f, this.l0);
                c(false);
            }
        } else {
            if (this.q.b()) {
                int i3 = i2 == 2 ? 130 : 33;
                z2 = instance.findNextFocus(this, view, i3) == null;
                if (H0) {
                    i2 = i3;
                }
            } else {
                z2 = false;
            }
            if (!z2 && this.q.a()) {
                int i4 = (this.q.k() == 1) ^ (i2 == 2) ? 66 : 17;
                if (instance.findNextFocus(this, view, i4) != null) {
                    z3 = false;
                }
                if (H0) {
                    i2 = i4;
                }
                z2 = z3;
            }
            if (z2) {
                b();
                if (c(view) == null) {
                    return null;
                }
                x();
                this.q.a(view, i2, this.f778f, this.l0);
                c(false);
            }
            view2 = instance.findNextFocus(this, view, i2);
        }
        if (view2 == null || view2.hasFocusable()) {
            return a(view, view2, i2) ? view2 : super.focusSearch(view, i2);
        }
        if (getFocusedChild() == null) {
            return super.focusSearch(view, i2);
        }
        a(view2, (View) null);
        return view;
    }

    /* access modifiers changed from: package-private */
    public void g() {
        if (this.O == null) {
            EdgeEffect a2 = this.L.a(this, 2);
            this.O = a2;
            if (this.k) {
                a2.setSize((getMeasuredHeight() - getPaddingTop()) - getPaddingBottom(), (getMeasuredWidth() - getPaddingLeft()) - getPaddingRight());
            } else {
                a2.setSize(getMeasuredHeight(), getMeasuredWidth());
            }
        }
    }

    /* access modifiers changed from: protected */
    public ViewGroup.LayoutParams generateDefaultLayoutParams() {
        o oVar = this.q;
        if (oVar != null) {
            return oVar.c();
        }
        throw new IllegalStateException("RecyclerView has no LayoutManager" + i());
    }

    public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        o oVar = this.q;
        if (oVar != null) {
            return oVar.a(getContext(), attributeSet);
        }
        throw new IllegalStateException("RecyclerView has no LayoutManager" + i());
    }

    public CharSequence getAccessibilityClassName() {
        return "androidx.recyclerview.widget.RecyclerView";
    }

    public g getAdapter() {
        return this.p;
    }

    public int getBaseline() {
        o oVar = this.q;
        if (oVar != null) {
            return oVar.d();
        }
        return super.getBaseline();
    }

    /* access modifiers changed from: protected */
    public int getChildDrawingOrder(int i2, int i3) {
        j jVar = this.t0;
        if (jVar == null) {
            return super.getChildDrawingOrder(i2, i3);
        }
        return jVar.a(i2, i3);
    }

    public boolean getClipToPadding() {
        return this.k;
    }

    public s getCompatAccessibilityDelegate() {
        return this.s0;
    }

    public k getEdgeEffectFactory() {
        return this.L;
    }

    public l getItemAnimator() {
        return this.Q;
    }

    public int getItemDecorationCount() {
        return this.s.size();
    }

    public o getLayoutManager() {
        return this.q;
    }

    public int getMaxFlingVelocity() {
        return this.e0;
    }

    public int getMinFlingVelocity() {
        return this.d0;
    }

    /* access modifiers changed from: package-private */
    public long getNanoTime() {
        if (G0) {
            return System.nanoTime();
        }
        return 0;
    }

    public r getOnFlingListener() {
        return this.c0;
    }

    public boolean getPreserveFocusAfterLayout() {
        return this.h0;
    }

    public u getRecycledViewPool() {
        return this.f778f.d();
    }

    public int getScrollState() {
        return this.R;
    }

    /* access modifiers changed from: package-private */
    public void h() {
        if (this.N == null) {
            EdgeEffect a2 = this.L.a(this, 1);
            this.N = a2;
            if (this.k) {
                a2.setSize((getMeasuredWidth() - getPaddingLeft()) - getPaddingRight(), (getMeasuredHeight() - getPaddingTop()) - getPaddingBottom());
            } else {
                a2.setSize(getMeasuredWidth(), getMeasuredHeight());
            }
        }
    }

    public void h(int i2) {
    }

    public void h(int i2, int i3) {
    }

    public boolean hasNestedScrollingParent() {
        return getScrollingChildHelper().a();
    }

    /* access modifiers changed from: package-private */
    public String i() {
        return " " + super.toString() + ", adapter:" + this.p + ", layout:" + this.q + ", context:" + getContext();
    }

    public void i(View view) {
    }

    public boolean isAttachedToWindow() {
        return this.v;
    }

    public final boolean isLayoutSuppressed() {
        return this.B;
    }

    public boolean isNestedScrollingEnabled() {
        return getScrollingChildHelper().b();
    }

    public void j(int i2) {
        if (!this.B) {
            o oVar = this.q;
            if (oVar == null) {
                Log.e("RecyclerView", "Cannot smooth scroll without a LayoutManager set. Call setLayoutManager with a non-null argument.");
            } else {
                oVar.a(this, this.l0, i2);
            }
        }
    }

    public void j(View view) {
    }

    /* access modifiers changed from: package-private */
    public void k() {
        this.f780h = new a(new f());
    }

    /* access modifiers changed from: package-private */
    public void l() {
        this.P = null;
        this.N = null;
        this.O = null;
        this.M = null;
    }

    public void m() {
        if (this.s.size() != 0) {
            o oVar = this.q;
            if (oVar != null) {
                oVar.a("Cannot invalidate item decorations during a scroll or layout");
            }
            p();
            requestLayout();
        }
    }

    /* access modifiers changed from: package-private */
    public boolean n() {
        AccessibilityManager accessibilityManager = this.F;
        return accessibilityManager != null && accessibilityManager.isEnabled();
    }

    public boolean o() {
        return this.J > 0;
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.J = 0;
        boolean z2 = true;
        this.v = true;
        if (!this.y || isLayoutRequested()) {
            z2 = false;
        }
        this.y = z2;
        o oVar = this.q;
        if (oVar != null) {
            oVar.a(this);
        }
        this.r0 = false;
        if (G0) {
            i iVar = i.f839i.get();
            this.j0 = iVar;
            if (iVar == null) {
                this.j0 = new i();
                Display j2 = androidx.core.h.v.j(this);
                float f2 = 60.0f;
                if (!isInEditMode() && j2 != null) {
                    float refreshRate = j2.getRefreshRate();
                    if (refreshRate >= 30.0f) {
                        f2 = refreshRate;
                    }
                }
                i iVar2 = this.j0;
                iVar2.f842g = (long) (1.0E9f / f2);
                i.f839i.set(iVar2);
            }
            this.j0.a(this);
        }
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        i iVar;
        super.onDetachedFromWindow();
        l lVar = this.Q;
        if (lVar != null) {
            lVar.b();
        }
        y();
        this.v = false;
        o oVar = this.q;
        if (oVar != null) {
            oVar.a(this, this.f778f);
        }
        this.z0.clear();
        removeCallbacks(this.A0);
        this.f782j.b();
        if (G0 && (iVar = this.j0) != null) {
            iVar.b(this);
            this.j0 = null;
        }
    }

    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int size = this.s.size();
        for (int i2 = 0; i2 < size; i2++) {
            this.s.get(i2).a(canvas, this, this.l0);
        }
    }

    public boolean onGenericMotionEvent(MotionEvent motionEvent) {
        float f2;
        float f3;
        if (this.q != null && !this.B && motionEvent.getAction() == 8) {
            if ((motionEvent.getSource() & 2) != 0) {
                f3 = this.q.b() ? -motionEvent.getAxisValue(9) : 0.0f;
                if (this.q.a()) {
                    f2 = motionEvent.getAxisValue(10);
                    if (!(f3 == 0.0f && f2 == 0.0f)) {
                        a((int) (f2 * this.f0), (int) (f3 * this.g0), motionEvent);
                    }
                }
            } else {
                if ((motionEvent.getSource() & 4194304) != 0) {
                    float axisValue = motionEvent.getAxisValue(26);
                    if (this.q.b()) {
                        f3 = -axisValue;
                    } else if (this.q.a()) {
                        f2 = axisValue;
                        f3 = 0.0f;
                        a((int) (f2 * this.f0), (int) (f3 * this.g0), motionEvent);
                    }
                }
                f3 = 0.0f;
            }
            f2 = 0.0f;
            a((int) (f2 * this.f0), (int) (f3 * this.g0), motionEvent);
        }
        return false;
    }

    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        boolean z2;
        if (this.B) {
            return false;
        }
        this.u = null;
        if (b(motionEvent)) {
            z();
            return true;
        }
        o oVar = this.q;
        if (oVar == null) {
            return false;
        }
        boolean a2 = oVar.a();
        boolean b2 = this.q.b();
        if (this.T == null) {
            this.T = VelocityTracker.obtain();
        }
        this.T.addMovement(motionEvent);
        int actionMasked = motionEvent.getActionMasked();
        int actionIndex = motionEvent.getActionIndex();
        if (actionMasked == 0) {
            if (this.C) {
                this.C = false;
            }
            this.S = motionEvent.getPointerId(0);
            int x2 = (int) (motionEvent.getX() + 0.5f);
            this.W = x2;
            this.U = x2;
            int y2 = (int) (motionEvent.getY() + 0.5f);
            this.a0 = y2;
            this.V = y2;
            if (this.R == 2) {
                getParent().requestDisallowInterceptTouchEvent(true);
                setScrollState(1);
                a(1);
            }
            int[] iArr = this.x0;
            iArr[1] = 0;
            iArr[0] = 0;
            int i2 = a2 ? 1 : 0;
            if (b2) {
                i2 |= 2;
            }
            j(i2, 0);
        } else if (actionMasked == 1) {
            this.T.clear();
            a(0);
        } else if (actionMasked == 2) {
            int findPointerIndex = motionEvent.findPointerIndex(this.S);
            if (findPointerIndex < 0) {
                Log.e("RecyclerView", "Error processing scroll; pointer index for id " + this.S + " not found. Did any MotionEvents get skipped?");
                return false;
            }
            int x3 = (int) (motionEvent.getX(findPointerIndex) + 0.5f);
            int y3 = (int) (motionEvent.getY(findPointerIndex) + 0.5f);
            if (this.R != 1) {
                int i3 = x3 - this.U;
                int i4 = y3 - this.V;
                if (!a2 || Math.abs(i3) <= this.b0) {
                    z2 = false;
                } else {
                    this.W = x3;
                    z2 = true;
                }
                if (b2 && Math.abs(i4) > this.b0) {
                    this.a0 = y3;
                    z2 = true;
                }
                if (z2) {
                    setScrollState(1);
                }
            }
        } else if (actionMasked == 3) {
            z();
        } else if (actionMasked == 5) {
            this.S = motionEvent.getPointerId(actionIndex);
            int x4 = (int) (motionEvent.getX(actionIndex) + 0.5f);
            this.W = x4;
            this.U = x4;
            int y4 = (int) (motionEvent.getY(actionIndex) + 0.5f);
            this.a0 = y4;
            this.V = y4;
        } else if (actionMasked == 6) {
            c(motionEvent);
        }
        if (this.R == 1) {
            return true;
        }
        return false;
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z2, int i2, int i3, int i4, int i5) {
        androidx.core.d.b.a("RV OnLayout");
        c();
        androidx.core.d.b.a();
        this.y = true;
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i2, int i3) {
        o oVar = this.q;
        if (oVar == null) {
            c(i2, i3);
            return;
        }
        boolean z2 = false;
        if (oVar.v()) {
            int mode = View.MeasureSpec.getMode(i2);
            int mode2 = View.MeasureSpec.getMode(i3);
            this.q.a(this.f778f, this.l0, i2, i3);
            if (mode == 1073741824 && mode2 == 1073741824) {
                z2 = true;
            }
            if (!z2 && this.p != null) {
                if (this.l0.e == 1) {
                    B();
                }
                this.q.b(i2, i3);
                this.l0.f808j = true;
                C();
                this.q.d(i2, i3);
                if (this.q.B()) {
                    this.q.b(View.MeasureSpec.makeMeasureSpec(getMeasuredWidth(), 1073741824), View.MeasureSpec.makeMeasureSpec(getMeasuredHeight(), 1073741824));
                    this.l0.f808j = true;
                    C();
                    this.q.d(i2, i3);
                }
            }
        } else if (this.w) {
            this.q.a(this.f778f, this.l0, i2, i3);
        } else {
            if (this.E) {
                x();
                r();
                J();
                s();
                z zVar = this.l0;
                if (zVar.l) {
                    zVar.f806h = true;
                } else {
                    this.f780h.b();
                    this.l0.f806h = false;
                }
                this.E = false;
                c(false);
            } else if (this.l0.l) {
                setMeasuredDimension(getMeasuredWidth(), getMeasuredHeight());
                return;
            }
            g gVar = this.p;
            if (gVar != null) {
                this.l0.f804f = gVar.getItemCount();
            } else {
                this.l0.f804f = 0;
            }
            x();
            this.q.a(this.f778f, this.l0, i2, i3);
            c(false);
            this.l0.f806h = false;
        }
    }

    /* access modifiers changed from: protected */
    public boolean onRequestFocusInDescendants(int i2, Rect rect) {
        if (o()) {
            return false;
        }
        return super.onRequestFocusInDescendants(i2, rect);
    }

    /* access modifiers changed from: protected */
    public void onRestoreInstanceState(Parcelable parcelable) {
        Parcelable parcelable2;
        if (!(parcelable instanceof SavedState)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        SavedState savedState = (SavedState) parcelable;
        this.f779g = savedState;
        super.onRestoreInstanceState(savedState.a());
        o oVar = this.q;
        if (oVar != null && (parcelable2 = this.f779g.f783g) != null) {
            oVar.a(parcelable2);
        }
    }

    /* access modifiers changed from: protected */
    public Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        SavedState savedState2 = this.f779g;
        if (savedState2 != null) {
            savedState.a(savedState2);
        } else {
            o oVar = this.q;
            if (oVar != null) {
                savedState.f783g = oVar.y();
            } else {
                savedState.f783g = null;
            }
        }
        return savedState;
    }

    /* access modifiers changed from: protected */
    public void onSizeChanged(int i2, int i3, int i4, int i5) {
        super.onSizeChanged(i2, i3, i4, i5);
        if (i2 != i4 || i3 != i5) {
            l();
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:45:0x00e2  */
    /* JADX WARNING: Removed duplicated region for block: B:51:0x00f8  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean onTouchEvent(android.view.MotionEvent r18) {
        /*
            r17 = this;
            r6 = r17
            r7 = r18
            boolean r0 = r6.B
            r8 = 0
            if (r0 != 0) goto L_0x01e8
            boolean r0 = r6.C
            if (r0 == 0) goto L_0x000f
            goto L_0x01e8
        L_0x000f:
            boolean r0 = r17.a((android.view.MotionEvent) r18)
            r9 = 1
            if (r0 == 0) goto L_0x001a
            r17.z()
            return r9
        L_0x001a:
            androidx.recyclerview.widget.RecyclerView$o r0 = r6.q
            if (r0 != 0) goto L_0x001f
            return r8
        L_0x001f:
            boolean r10 = r0.a()
            androidx.recyclerview.widget.RecyclerView$o r0 = r6.q
            boolean r11 = r0.b()
            android.view.VelocityTracker r0 = r6.T
            if (r0 != 0) goto L_0x0033
            android.view.VelocityTracker r0 = android.view.VelocityTracker.obtain()
            r6.T = r0
        L_0x0033:
            int r0 = r18.getActionMasked()
            int r1 = r18.getActionIndex()
            if (r0 != 0) goto L_0x0043
            int[] r2 = r6.x0
            r2[r9] = r8
            r2[r8] = r8
        L_0x0043:
            android.view.MotionEvent r12 = android.view.MotionEvent.obtain(r18)
            int[] r2 = r6.x0
            r3 = r2[r8]
            float r3 = (float) r3
            r2 = r2[r9]
            float r2 = (float) r2
            r12.offsetLocation(r3, r2)
            r2 = 1056964608(0x3f000000, float:0.5)
            if (r0 == 0) goto L_0x01b7
            if (r0 == r9) goto L_0x0175
            r3 = 2
            if (r0 == r3) goto L_0x008c
            r3 = 3
            if (r0 == r3) goto L_0x0087
            r3 = 5
            if (r0 == r3) goto L_0x006b
            r1 = 6
            if (r0 == r1) goto L_0x0066
            goto L_0x01dd
        L_0x0066:
            r17.c((android.view.MotionEvent) r18)
            goto L_0x01dd
        L_0x006b:
            int r0 = r7.getPointerId(r1)
            r6.S = r0
            float r0 = r7.getX(r1)
            float r0 = r0 + r2
            int r0 = (int) r0
            r6.W = r0
            r6.U = r0
            float r0 = r7.getY(r1)
            float r0 = r0 + r2
            int r0 = (int) r0
            r6.a0 = r0
            r6.V = r0
            goto L_0x01dd
        L_0x0087:
            r17.z()
            goto L_0x01dd
        L_0x008c:
            int r0 = r6.S
            int r0 = r7.findPointerIndex(r0)
            if (r0 >= 0) goto L_0x00b2
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r1 = "Error processing scroll; pointer index for id "
            r0.append(r1)
            int r1 = r6.S
            r0.append(r1)
            java.lang.String r1 = " not found. Did any MotionEvents get skipped?"
            r0.append(r1)
            java.lang.String r0 = r0.toString()
            java.lang.String r1 = "RecyclerView"
            android.util.Log.e(r1, r0)
            return r8
        L_0x00b2:
            float r1 = r7.getX(r0)
            float r1 = r1 + r2
            int r13 = (int) r1
            float r0 = r7.getY(r0)
            float r0 = r0 + r2
            int r14 = (int) r0
            int r0 = r6.W
            int r0 = r0 - r13
            int r1 = r6.a0
            int r1 = r1 - r14
            int r2 = r6.R
            if (r2 == r9) goto L_0x00fb
            if (r10 == 0) goto L_0x00df
            if (r0 <= 0) goto L_0x00d4
            int r2 = r6.b0
            int r0 = r0 - r2
            int r0 = java.lang.Math.max(r8, r0)
            goto L_0x00db
        L_0x00d4:
            int r2 = r6.b0
            int r0 = r0 + r2
            int r0 = java.lang.Math.min(r8, r0)
        L_0x00db:
            if (r0 == 0) goto L_0x00df
            r2 = 1
            goto L_0x00e0
        L_0x00df:
            r2 = 0
        L_0x00e0:
            if (r11 == 0) goto L_0x00f6
            if (r1 <= 0) goto L_0x00ec
            int r3 = r6.b0
            int r1 = r1 - r3
            int r1 = java.lang.Math.max(r8, r1)
            goto L_0x00f3
        L_0x00ec:
            int r3 = r6.b0
            int r1 = r1 + r3
            int r1 = java.lang.Math.min(r8, r1)
        L_0x00f3:
            if (r1 == 0) goto L_0x00f6
            r2 = 1
        L_0x00f6:
            if (r2 == 0) goto L_0x00fb
            r6.setScrollState(r9)
        L_0x00fb:
            r15 = r0
            r16 = r1
            int r0 = r6.R
            if (r0 != r9) goto L_0x01dd
            int[] r0 = r6.y0
            r0[r8] = r8
            r0[r9] = r8
            if (r10 == 0) goto L_0x010c
            r1 = r15
            goto L_0x010d
        L_0x010c:
            r1 = 0
        L_0x010d:
            if (r11 == 0) goto L_0x0112
            r2 = r16
            goto L_0x0113
        L_0x0112:
            r2 = 0
        L_0x0113:
            int[] r3 = r6.y0
            int[] r4 = r6.w0
            r5 = 0
            r0 = r17
            boolean r0 = r0.a((int) r1, (int) r2, (int[]) r3, (int[]) r4, (int) r5)
            if (r0 == 0) goto L_0x0142
            int[] r0 = r6.y0
            r1 = r0[r8]
            int r15 = r15 - r1
            r0 = r0[r9]
            int r16 = r16 - r0
            int[] r0 = r6.x0
            r1 = r0[r8]
            int[] r2 = r6.w0
            r3 = r2[r8]
            int r1 = r1 + r3
            r0[r8] = r1
            r1 = r0[r9]
            r2 = r2[r9]
            int r1 = r1 + r2
            r0[r9] = r1
            android.view.ViewParent r0 = r17.getParent()
            r0.requestDisallowInterceptTouchEvent(r9)
        L_0x0142:
            r0 = r16
            int[] r1 = r6.w0
            r2 = r1[r8]
            int r13 = r13 - r2
            r6.W = r13
            r1 = r1[r9]
            int r14 = r14 - r1
            r6.a0 = r14
            if (r10 == 0) goto L_0x0154
            r1 = r15
            goto L_0x0155
        L_0x0154:
            r1 = 0
        L_0x0155:
            if (r11 == 0) goto L_0x0159
            r2 = r0
            goto L_0x015a
        L_0x0159:
            r2 = 0
        L_0x015a:
            boolean r1 = r6.a((int) r1, (int) r2, (android.view.MotionEvent) r7)
            if (r1 == 0) goto L_0x0167
            android.view.ViewParent r1 = r17.getParent()
            r1.requestDisallowInterceptTouchEvent(r9)
        L_0x0167:
            androidx.recyclerview.widget.i r1 = r6.j0
            if (r1 == 0) goto L_0x01dd
            if (r15 != 0) goto L_0x016f
            if (r0 == 0) goto L_0x01dd
        L_0x016f:
            androidx.recyclerview.widget.i r1 = r6.j0
            r1.a((androidx.recyclerview.widget.RecyclerView) r6, (int) r15, (int) r0)
            goto L_0x01dd
        L_0x0175:
            android.view.VelocityTracker r0 = r6.T
            r0.addMovement(r12)
            android.view.VelocityTracker r0 = r6.T
            r1 = 1000(0x3e8, float:1.401E-42)
            int r2 = r6.e0
            float r2 = (float) r2
            r0.computeCurrentVelocity(r1, r2)
            r0 = 0
            if (r10 == 0) goto L_0x0191
            android.view.VelocityTracker r1 = r6.T
            int r2 = r6.S
            float r1 = r1.getXVelocity(r2)
            float r1 = -r1
            goto L_0x0192
        L_0x0191:
            r1 = 0
        L_0x0192:
            if (r11 == 0) goto L_0x019e
            android.view.VelocityTracker r2 = r6.T
            int r3 = r6.S
            float r2 = r2.getYVelocity(r3)
            float r2 = -r2
            goto L_0x019f
        L_0x019e:
            r2 = 0
        L_0x019f:
            int r3 = (r1 > r0 ? 1 : (r1 == r0 ? 0 : -1))
            if (r3 != 0) goto L_0x01a7
            int r0 = (r2 > r0 ? 1 : (r2 == r0 ? 0 : -1))
            if (r0 == 0) goto L_0x01af
        L_0x01a7:
            int r0 = (int) r1
            int r1 = (int) r2
            boolean r0 = r6.e(r0, r1)
            if (r0 != 0) goto L_0x01b2
        L_0x01af:
            r6.setScrollState(r8)
        L_0x01b2:
            r17.N()
            r8 = 1
            goto L_0x01dd
        L_0x01b7:
            int r0 = r7.getPointerId(r8)
            r6.S = r0
            float r0 = r18.getX()
            float r0 = r0 + r2
            int r0 = (int) r0
            r6.W = r0
            r6.U = r0
            float r0 = r18.getY()
            float r0 = r0 + r2
            int r0 = (int) r0
            r6.a0 = r0
            r6.V = r0
            if (r10 == 0) goto L_0x01d5
            r0 = 1
            goto L_0x01d6
        L_0x01d5:
            r0 = 0
        L_0x01d6:
            if (r11 == 0) goto L_0x01da
            r0 = r0 | 2
        L_0x01da:
            r6.j(r0, r8)
        L_0x01dd:
            if (r8 != 0) goto L_0x01e4
            android.view.VelocityTracker r0 = r6.T
            r0.addMovement(r12)
        L_0x01e4:
            r12.recycle()
            return r9
        L_0x01e8:
            return r8
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.recyclerview.widget.RecyclerView.onTouchEvent(android.view.MotionEvent):boolean");
    }

    /* access modifiers changed from: package-private */
    public void p() {
        int b2 = this.f781i.b();
        for (int i2 = 0; i2 < b2; i2++) {
            ((p) this.f781i.d(i2).getLayoutParams()).c = true;
        }
        this.f778f.g();
    }

    /* access modifiers changed from: package-private */
    public void q() {
        int b2 = this.f781i.b();
        for (int i2 = 0; i2 < b2; i2++) {
            c0 m2 = m(this.f781i.d(i2));
            if (m2 != null && !m2.shouldIgnore()) {
                m2.addFlags(6);
            }
        }
        p();
        this.f778f.h();
    }

    /* access modifiers changed from: package-private */
    public void r() {
        this.J++;
    }

    /* access modifiers changed from: protected */
    public void removeDetachedView(View view, boolean z2) {
        c0 m2 = m(view);
        if (m2 != null) {
            if (m2.isTmpDetached()) {
                m2.clearTmpDetachFlag();
            } else if (!m2.shouldIgnore()) {
                throw new IllegalArgumentException("Called removeDetachedView with a view which is not flagged as tmp detached." + m2 + i());
            }
        }
        view.clearAnimation();
        b(view);
        super.removeDetachedView(view, z2);
    }

    public void removeOnChildAttachStateChangeListener(q qVar) {
        List<q> list = this.G;
        if (list != null) {
            list.remove(qVar);
        }
    }

    public void removeOnItemTouchListener(s sVar) {
        this.t.remove(sVar);
        if (this.u == sVar) {
            this.u = null;
        }
    }

    public void removeOnScrollListener(t tVar) {
        List<t> list = this.n0;
        if (list != null) {
            list.remove(tVar);
        }
    }

    public void requestChildFocus(View view, View view2) {
        if (!this.q.a(this, this.l0, view, view2) && view2 != null) {
            a(view, view2);
        }
        super.requestChildFocus(view, view2);
    }

    public boolean requestChildRectangleOnScreen(View view, Rect rect, boolean z2) {
        return this.q.a(this, view, rect, z2);
    }

    public void requestDisallowInterceptTouchEvent(boolean z2) {
        int size = this.t.size();
        for (int i2 = 0; i2 < size; i2++) {
            this.t.get(i2).onRequestDisallowInterceptTouchEvent(z2);
        }
        super.requestDisallowInterceptTouchEvent(z2);
    }

    public void requestLayout() {
        if (this.z != 0 || this.B) {
            this.A = true;
        } else {
            super.requestLayout();
        }
    }

    /* access modifiers changed from: package-private */
    public void s() {
        a(true);
    }

    public void scrollBy(int i2, int i3) {
        o oVar = this.q;
        if (oVar == null) {
            Log.e("RecyclerView", "Cannot scroll without a LayoutManager set. Call setLayoutManager with a non-null argument.");
        } else if (!this.B) {
            boolean a2 = oVar.a();
            boolean b2 = this.q.b();
            if (a2 || b2) {
                if (!a2) {
                    i2 = 0;
                }
                if (!b2) {
                    i3 = 0;
                }
                a(i2, i3, (MotionEvent) null);
            }
        }
    }

    public void scrollTo(int i2, int i3) {
        Log.w("RecyclerView", "RecyclerView does not support scrolling to an absolute position. Use scrollToPosition instead");
    }

    public void sendAccessibilityEventUnchecked(AccessibilityEvent accessibilityEvent) {
        if (!a(accessibilityEvent)) {
            super.sendAccessibilityEventUnchecked(accessibilityEvent);
        }
    }

    public void setAccessibilityDelegateCompat(s sVar) {
        this.s0 = sVar;
        androidx.core.h.v.a((View) this, (androidx.core.h.a) sVar);
    }

    public void setAdapter(g gVar) {
        setLayoutFrozen(false);
        a(gVar, false, true);
        b(false);
        requestLayout();
    }

    public void setChildDrawingOrderCallback(j jVar) {
        if (jVar != this.t0) {
            this.t0 = jVar;
            setChildrenDrawingOrderEnabled(jVar != null);
        }
    }

    public void setClipToPadding(boolean z2) {
        if (z2 != this.k) {
            l();
        }
        this.k = z2;
        super.setClipToPadding(z2);
        if (this.y) {
            requestLayout();
        }
    }

    public void setEdgeEffectFactory(k kVar) {
        androidx.core.g.h.a(kVar);
        this.L = kVar;
        l();
    }

    public void setHasFixedSize(boolean z2) {
        this.w = z2;
    }

    public void setItemAnimator(l lVar) {
        l lVar2 = this.Q;
        if (lVar2 != null) {
            lVar2.b();
            this.Q.a((l.b) null);
        }
        this.Q = lVar;
        if (lVar != null) {
            lVar.a(this.q0);
        }
    }

    public void setItemViewCacheSize(int i2) {
        this.f778f.f(i2);
    }

    @Deprecated
    public void setLayoutFrozen(boolean z2) {
        suppressLayout(z2);
    }

    public void setLayoutManager(o oVar) {
        if (oVar != this.q) {
            y();
            if (this.q != null) {
                l lVar = this.Q;
                if (lVar != null) {
                    lVar.b();
                }
                this.q.b(this.f778f);
                this.q.c(this.f778f);
                this.f778f.a();
                if (this.v) {
                    this.q.a(this, this.f778f);
                }
                this.q.f((RecyclerView) null);
                this.q = null;
            } else {
                this.f778f.a();
            }
            this.f781i.c();
            this.q = oVar;
            if (oVar != null) {
                if (oVar.b == null) {
                    oVar.f(this);
                    if (this.v) {
                        this.q.a(this);
                    }
                } else {
                    throw new IllegalArgumentException("LayoutManager " + oVar + " is already attached to a RecyclerView:" + oVar.b.i());
                }
            }
            this.f778f.j();
            requestLayout();
        }
    }

    @Deprecated
    public void setLayoutTransition(LayoutTransition layoutTransition) {
        if (Build.VERSION.SDK_INT < 18) {
            if (layoutTransition == null) {
                suppressLayout(false);
                return;
            } else if (layoutTransition.getAnimator(0) == null && layoutTransition.getAnimator(1) == null && layoutTransition.getAnimator(2) == null && layoutTransition.getAnimator(3) == null && layoutTransition.getAnimator(4) == null) {
                suppressLayout(true);
                return;
            }
        }
        if (layoutTransition == null) {
            super.setLayoutTransition((LayoutTransition) null);
            return;
        }
        throw new IllegalArgumentException("Providing a LayoutTransition into RecyclerView is not supported. Please use setItemAnimator() instead for animating changes to the items in this RecyclerView");
    }

    public void setNestedScrollingEnabled(boolean z2) {
        getScrollingChildHelper().a(z2);
    }

    public void setOnFlingListener(r rVar) {
        this.c0 = rVar;
    }

    @Deprecated
    public void setOnScrollListener(t tVar) {
        this.m0 = tVar;
    }

    public void setPreserveFocusAfterLayout(boolean z2) {
        this.h0 = z2;
    }

    public void setRecycledViewPool(u uVar) {
        this.f778f.a(uVar);
    }

    public void setRecyclerListener(w wVar) {
        this.r = wVar;
    }

    /* access modifiers changed from: package-private */
    public void setScrollState(int i2) {
        if (i2 != this.R) {
            this.R = i2;
            if (i2 != 2) {
                P();
            }
            b(i2);
        }
    }

    public void setScrollingTouchSlop(int i2) {
        ViewConfiguration viewConfiguration = ViewConfiguration.get(getContext());
        if (i2 != 0) {
            if (i2 != 1) {
                Log.w("RecyclerView", "setScrollingTouchSlop(): bad argument constant " + i2 + "; using default value");
            } else {
                this.b0 = viewConfiguration.getScaledPagingTouchSlop();
                return;
            }
        }
        this.b0 = viewConfiguration.getScaledTouchSlop();
    }

    public void setViewCacheExtension(a0 a0Var) {
        this.f778f.a(a0Var);
    }

    public boolean startNestedScroll(int i2) {
        return getScrollingChildHelper().b(i2);
    }

    public void stopNestedScroll() {
        getScrollingChildHelper().c();
    }

    public final void suppressLayout(boolean z2) {
        if (z2 != this.B) {
            a("Do not suppressLayout in layout or scroll");
            if (!z2) {
                this.B = false;
                if (!(!this.A || this.q == null || this.p == null)) {
                    requestLayout();
                }
                this.A = false;
                return;
            }
            long uptimeMillis = SystemClock.uptimeMillis();
            onTouchEvent(MotionEvent.obtain(uptimeMillis, uptimeMillis, 3, 0.0f, 0.0f, 0));
            this.B = true;
            this.C = true;
            y();
        }
    }

    /* access modifiers changed from: package-private */
    public void t() {
        if (!this.r0 && this.v) {
            androidx.core.h.v.a((View) this, this.A0);
            this.r0 = true;
        }
    }

    /* access modifiers changed from: package-private */
    public void u() {
        l lVar = this.Q;
        if (lVar != null) {
            lVar.b();
        }
        o oVar = this.q;
        if (oVar != null) {
            oVar.b(this.f778f);
            this.q.c(this.f778f);
        }
        this.f778f.a();
    }

    /* access modifiers changed from: package-private */
    public void v() {
        c0 c0Var;
        int a2 = this.f781i.a();
        for (int i2 = 0; i2 < a2; i2++) {
            View c2 = this.f781i.c(i2);
            c0 g2 = g(c2);
            if (!(g2 == null || (c0Var = g2.mShadowingHolder) == null)) {
                View view = c0Var.itemView;
                int left = c2.getLeft();
                int top = c2.getTop();
                if (left != view.getLeft() || top != view.getTop()) {
                    view.layout(left, top, view.getWidth() + left, view.getHeight() + top);
                }
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void w() {
        int b2 = this.f781i.b();
        for (int i2 = 0; i2 < b2; i2++) {
            c0 m2 = m(this.f781i.d(i2));
            if (!m2.shouldIgnore()) {
                m2.saveOldPosition();
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void x() {
        int i2 = this.z + 1;
        this.z = i2;
        if (i2 == 1 && !this.B) {
            this.A = false;
        }
    }

    public void y() {
        setScrollState(0);
        P();
    }

    class f implements a.C0042a {
        f() {
        }

        public c0 a(int i2) {
            c0 a2 = RecyclerView.this.a(i2, true);
            if (a2 != null && !RecyclerView.this.f781i.c(a2.itemView)) {
                return a2;
            }
            return null;
        }

        public void b(int i2, int i3) {
            RecyclerView.this.a(i2, i3, true);
            RecyclerView recyclerView = RecyclerView.this;
            recyclerView.o0 = true;
            recyclerView.l0.d += i3;
        }

        public void c(int i2, int i3) {
            RecyclerView.this.a(i2, i3, false);
            RecyclerView.this.o0 = true;
        }

        public void d(int i2, int i3) {
            RecyclerView.this.f(i2, i3);
            RecyclerView.this.o0 = true;
        }

        public void a(int i2, int i3, Object obj) {
            RecyclerView.this.a(i2, i3, obj);
            RecyclerView.this.p0 = true;
        }

        /* access modifiers changed from: package-private */
        public void c(a.b bVar) {
            int i2 = bVar.a;
            if (i2 == 1) {
                RecyclerView recyclerView = RecyclerView.this;
                recyclerView.q.a(recyclerView, bVar.b, bVar.d);
            } else if (i2 == 2) {
                RecyclerView recyclerView2 = RecyclerView.this;
                recyclerView2.q.b(recyclerView2, bVar.b, bVar.d);
            } else if (i2 == 4) {
                RecyclerView recyclerView3 = RecyclerView.this;
                recyclerView3.q.a(recyclerView3, bVar.b, bVar.d, bVar.c);
            } else if (i2 == 8) {
                RecyclerView recyclerView4 = RecyclerView.this;
                recyclerView4.q.a(recyclerView4, bVar.b, bVar.d, 1);
            }
        }

        public void b(a.b bVar) {
            c(bVar);
        }

        public void a(a.b bVar) {
            c(bVar);
        }

        public void a(int i2, int i3) {
            RecyclerView.this.g(i2, i3);
            RecyclerView.this.o0 = true;
        }
    }

    public RecyclerView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R$attr.recyclerViewStyle);
    }

    static RecyclerView l(View view) {
        if (!(view instanceof ViewGroup)) {
            return null;
        }
        if (view instanceof RecyclerView) {
            return (RecyclerView) view;
        }
        ViewGroup viewGroup = (ViewGroup) view;
        int childCount = viewGroup.getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            RecyclerView l2 = l(viewGroup.getChildAt(i2));
            if (l2 != null) {
                return l2;
            }
        }
        return null;
    }

    private int n(View view) {
        int id = view.getId();
        while (!view.isFocused() && (view instanceof ViewGroup) && view.hasFocus()) {
            view = ((ViewGroup) view).getFocusedChild();
            if (view.getId() != -1) {
                id = view.getId();
            }
        }
        return id;
    }

    /* access modifiers changed from: package-private */
    public boolean k(View view) {
        x();
        boolean e2 = this.f781i.e(view);
        if (e2) {
            c0 m2 = m(view);
            this.f778f.c(m2);
            this.f778f.b(m2);
        }
        c(!e2);
        return e2;
    }

    class b0 implements Runnable {
        private int e;

        /* renamed from: f  reason: collision with root package name */
        private int f784f;

        /* renamed from: g  reason: collision with root package name */
        OverScroller f785g;

        /* renamed from: h  reason: collision with root package name */
        Interpolator f786h = RecyclerView.K0;

        /* renamed from: i  reason: collision with root package name */
        private boolean f787i = false;

        /* renamed from: j  reason: collision with root package name */
        private boolean f788j = false;

        b0() {
            this.f785g = new OverScroller(RecyclerView.this.getContext(), RecyclerView.K0);
        }

        private void c() {
            RecyclerView.this.removeCallbacks(this);
            androidx.core.h.v.a((View) RecyclerView.this, (Runnable) this);
        }

        /* access modifiers changed from: package-private */
        public void a() {
            if (this.f787i) {
                this.f788j = true;
            } else {
                c();
            }
        }

        public void b() {
            RecyclerView.this.removeCallbacks(this);
            this.f785g.abortAnimation();
        }

        public void run() {
            int i2;
            int i3;
            RecyclerView recyclerView = RecyclerView.this;
            if (recyclerView.q == null) {
                b();
                return;
            }
            this.f788j = false;
            this.f787i = true;
            recyclerView.b();
            OverScroller overScroller = this.f785g;
            if (overScroller.computeScrollOffset()) {
                int currX = overScroller.getCurrX();
                int currY = overScroller.getCurrY();
                int i4 = currX - this.e;
                int i5 = currY - this.f784f;
                this.e = currX;
                this.f784f = currY;
                RecyclerView recyclerView2 = RecyclerView.this;
                int[] iArr = recyclerView2.y0;
                iArr[0] = 0;
                iArr[1] = 0;
                if (recyclerView2.a(i4, i5, iArr, (int[]) null, 1)) {
                    int[] iArr2 = RecyclerView.this.y0;
                    i4 -= iArr2[0];
                    i5 -= iArr2[1];
                }
                if (RecyclerView.this.getOverScrollMode() != 2) {
                    RecyclerView.this.b(i4, i5);
                }
                RecyclerView recyclerView3 = RecyclerView.this;
                if (recyclerView3.p != null) {
                    int[] iArr3 = recyclerView3.y0;
                    iArr3[0] = 0;
                    iArr3[1] = 0;
                    recyclerView3.a(i4, i5, iArr3);
                    RecyclerView recyclerView4 = RecyclerView.this;
                    int[] iArr4 = recyclerView4.y0;
                    i2 = iArr4[0];
                    i3 = iArr4[1];
                    i4 -= i2;
                    i5 -= i3;
                    y yVar = recyclerView4.q.f791g;
                    if (yVar != null && !yVar.d() && yVar.e()) {
                        int a = RecyclerView.this.l0.a();
                        if (a == 0) {
                            yVar.h();
                        } else if (yVar.c() >= a) {
                            yVar.c(a - 1);
                            yVar.a(i2, i3);
                        } else {
                            yVar.a(i2, i3);
                        }
                    }
                } else {
                    i3 = 0;
                    i2 = 0;
                }
                if (!RecyclerView.this.s.isEmpty()) {
                    RecyclerView.this.invalidate();
                }
                RecyclerView recyclerView5 = RecyclerView.this;
                int[] iArr5 = recyclerView5.y0;
                iArr5[0] = 0;
                iArr5[1] = 0;
                recyclerView5.a(i2, i3, i4, i5, (int[]) null, 1, iArr5);
                int[] iArr6 = RecyclerView.this.y0;
                int i6 = i4 - iArr6[0];
                int i7 = i5 - iArr6[1];
                if (!(i2 == 0 && i3 == 0)) {
                    RecyclerView.this.d(i2, i3);
                }
                if (!RecyclerView.this.awakenScrollBars()) {
                    RecyclerView.this.invalidate();
                }
                boolean z = overScroller.isFinished() || (((overScroller.getCurrX() == overScroller.getFinalX()) || i6 != 0) && ((overScroller.getCurrY() == overScroller.getFinalY()) || i7 != 0));
                y yVar2 = RecyclerView.this.q.f791g;
                if ((yVar2 != null && yVar2.d()) || !z) {
                    a();
                    RecyclerView recyclerView6 = RecyclerView.this;
                    i iVar = recyclerView6.j0;
                    if (iVar != null) {
                        iVar.a(recyclerView6, i2, i3);
                    }
                } else {
                    if (RecyclerView.this.getOverScrollMode() != 2) {
                        int currVelocity = (int) overScroller.getCurrVelocity();
                        int i8 = i6 < 0 ? -currVelocity : i6 > 0 ? currVelocity : 0;
                        if (i7 < 0) {
                            currVelocity = -currVelocity;
                        } else if (i7 <= 0) {
                            currVelocity = 0;
                        }
                        RecyclerView.this.a(i8, currVelocity);
                    }
                    if (RecyclerView.G0) {
                        RecyclerView.this.k0.a();
                    }
                }
            }
            y yVar3 = RecyclerView.this.q.f791g;
            if (yVar3 != null && yVar3.d()) {
                yVar3.a(0, 0);
            }
            this.f787i = false;
            if (this.f788j) {
                c();
                return;
            }
            RecyclerView.this.setScrollState(0);
            RecyclerView.this.a(1);
        }

        public void a(int i2, int i3) {
            RecyclerView.this.setScrollState(2);
            this.f784f = 0;
            this.e = 0;
            Interpolator interpolator = this.f786h;
            Interpolator interpolator2 = RecyclerView.K0;
            if (interpolator != interpolator2) {
                this.f786h = interpolator2;
                this.f785g = new OverScroller(RecyclerView.this.getContext(), RecyclerView.K0);
            }
            this.f785g.fling(0, 0, i2, i3, Integer.MIN_VALUE, Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.MAX_VALUE);
            a();
        }

        public void a(int i2, int i3, int i4, Interpolator interpolator) {
            if (i4 == Integer.MIN_VALUE) {
                i4 = a(i2, i3, 0, 0);
            }
            int i5 = i4;
            if (interpolator == null) {
                interpolator = RecyclerView.K0;
            }
            if (this.f786h != interpolator) {
                this.f786h = interpolator;
                this.f785g = new OverScroller(RecyclerView.this.getContext(), interpolator);
            }
            this.f784f = 0;
            this.e = 0;
            RecyclerView.this.setScrollState(2);
            this.f785g.startScroll(0, 0, i2, i3, i5);
            if (Build.VERSION.SDK_INT < 23) {
                this.f785g.computeScrollOffset();
            }
            a();
        }

        private float a(float f2) {
            return (float) Math.sin((double) ((f2 - 0.5f) * 0.47123894f));
        }

        private int a(int i2, int i3, int i4, int i5) {
            int i6;
            int abs = Math.abs(i2);
            int abs2 = Math.abs(i3);
            boolean z = abs > abs2;
            int sqrt = (int) Math.sqrt((double) ((i4 * i4) + (i5 * i5)));
            int sqrt2 = (int) Math.sqrt((double) ((i2 * i2) + (i3 * i3)));
            RecyclerView recyclerView = RecyclerView.this;
            int width = z ? recyclerView.getWidth() : recyclerView.getHeight();
            int i7 = width / 2;
            float f2 = (float) width;
            float f3 = (float) i7;
            float a = f3 + (a(Math.min(1.0f, (((float) sqrt2) * 1.0f) / f2)) * f3);
            if (sqrt > 0) {
                i6 = Math.round(Math.abs(a / ((float) sqrt)) * 1000.0f) * 4;
            } else {
                if (!z) {
                    abs = abs2;
                }
                i6 = (int) (((((float) abs) / f2) + 1.0f) * 300.0f);
            }
            return Math.min(i6, 2000);
        }
    }

    public static class u {
        SparseArray<a> a = new SparseArray<>();
        private int b = 0;

        static class a {
            final ArrayList<c0> a = new ArrayList<>();
            int b = 5;
            long c = 0;
            long d = 0;

            a() {
            }
        }

        public c0 a(int i2) {
            a aVar = this.a.get(i2);
            if (aVar == null || aVar.a.isEmpty()) {
                return null;
            }
            ArrayList<c0> arrayList = aVar.a;
            for (int size = arrayList.size() - 1; size >= 0; size--) {
                if (!arrayList.get(size).isAttachedToTransitionOverlay()) {
                    return arrayList.remove(size);
                }
            }
            return null;
        }

        public void b() {
            for (int i2 = 0; i2 < this.a.size(); i2++) {
                this.a.valueAt(i2).a.clear();
            }
        }

        /* access modifiers changed from: package-private */
        public void c() {
            this.b--;
        }

        /* access modifiers changed from: package-private */
        public void b(int i2, long j2) {
            a b2 = b(i2);
            b2.c = a(b2.c, j2);
        }

        /* access modifiers changed from: package-private */
        public boolean b(int i2, long j2, long j3) {
            long j4 = b(i2).c;
            return j4 == 0 || j2 + j4 < j3;
        }

        private a b(int i2) {
            a aVar = this.a.get(i2);
            if (aVar != null) {
                return aVar;
            }
            a aVar2 = new a();
            this.a.put(i2, aVar2);
            return aVar2;
        }

        public void a(c0 c0Var) {
            int itemViewType = c0Var.getItemViewType();
            ArrayList<c0> arrayList = b(itemViewType).a;
            if (this.a.get(itemViewType).b > arrayList.size()) {
                c0Var.resetInternal();
                arrayList.add(c0Var);
            }
        }

        /* access modifiers changed from: package-private */
        public long a(long j2, long j3) {
            return j2 == 0 ? j3 : ((j2 / 4) * 3) + (j3 / 4);
        }

        /* access modifiers changed from: package-private */
        public void a(int i2, long j2) {
            a b2 = b(i2);
            b2.d = a(b2.d, j2);
        }

        /* access modifiers changed from: package-private */
        public boolean a(int i2, long j2, long j3) {
            long j4 = b(i2).d;
            return j4 == 0 || j2 + j4 < j3;
        }

        /* access modifiers changed from: package-private */
        public void a() {
            this.b++;
        }

        /* access modifiers changed from: package-private */
        public void a(g gVar, g gVar2, boolean z) {
            if (gVar != null) {
                c();
            }
            if (!z && this.b == 0) {
                b();
            }
            if (gVar2 != null) {
                a();
            }
        }
    }

    private class x extends i {
        x() {
        }

        public void a() {
            RecyclerView.this.a((String) null);
            RecyclerView recyclerView = RecyclerView.this;
            recyclerView.l0.f805g = true;
            recyclerView.b(true);
            if (!RecyclerView.this.f780h.c()) {
                RecyclerView.this.requestLayout();
            }
        }

        public void b(int i2, int i3) {
            RecyclerView.this.a((String) null);
            if (RecyclerView.this.f780h.b(i2, i3)) {
                b();
            }
        }

        public void c(int i2, int i3) {
            RecyclerView.this.a((String) null);
            if (RecyclerView.this.f780h.c(i2, i3)) {
                b();
            }
        }

        /* access modifiers changed from: package-private */
        public void b() {
            if (RecyclerView.F0) {
                RecyclerView recyclerView = RecyclerView.this;
                if (recyclerView.w && recyclerView.v) {
                    androidx.core.h.v.a((View) recyclerView, recyclerView.l);
                    return;
                }
            }
            RecyclerView recyclerView2 = RecyclerView.this;
            recyclerView2.E = true;
            recyclerView2.requestLayout();
        }

        public void a(int i2, int i3, Object obj) {
            RecyclerView.this.a((String) null);
            if (RecyclerView.this.f780h.a(i2, i3, obj)) {
                b();
            }
        }

        public void a(int i2, int i3, int i4) {
            RecyclerView.this.a((String) null);
            if (RecyclerView.this.f780h.a(i2, i3, i4)) {
                b();
            }
        }
    }

    public RecyclerView(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.e = new x();
        this.f778f = new v();
        this.f782j = new x();
        this.l = new a();
        this.m = new Rect();
        this.n = new Rect();
        this.o = new RectF();
        this.s = new ArrayList<>();
        this.t = new ArrayList<>();
        this.z = 0;
        this.H = false;
        this.I = false;
        this.J = 0;
        this.K = 0;
        this.L = new k();
        this.Q = new e();
        this.R = 0;
        this.S = -1;
        this.f0 = Float.MIN_VALUE;
        this.g0 = Float.MIN_VALUE;
        boolean z2 = true;
        this.h0 = true;
        this.i0 = new b0();
        this.k0 = G0 ? new i.b() : null;
        this.l0 = new z();
        this.o0 = false;
        this.p0 = false;
        this.q0 = new m();
        this.r0 = false;
        this.u0 = new int[2];
        this.w0 = new int[2];
        this.x0 = new int[2];
        this.y0 = new int[2];
        this.z0 = new ArrayList();
        this.A0 = new b();
        this.B0 = new d();
        setScrollContainer(true);
        setFocusableInTouchMode(true);
        ViewConfiguration viewConfiguration = ViewConfiguration.get(context);
        this.b0 = viewConfiguration.getScaledTouchSlop();
        this.f0 = androidx.core.h.w.b(viewConfiguration, context);
        this.g0 = androidx.core.h.w.c(viewConfiguration, context);
        this.d0 = viewConfiguration.getScaledMinimumFlingVelocity();
        this.e0 = viewConfiguration.getScaledMaximumFlingVelocity();
        setWillNotDraw(getOverScrollMode() == 2);
        this.Q.a(this.q0);
        k();
        H();
        G();
        if (androidx.core.h.v.m(this) == 0) {
            androidx.core.h.v.h(this, 1);
        }
        this.F = (AccessibilityManager) getContext().getSystemService("accessibility");
        setAccessibilityDelegateCompat(new s(this));
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.RecyclerView, i2, 0);
        if (Build.VERSION.SDK_INT >= 29) {
            saveAttributeDataForStyleable(context, R$styleable.RecyclerView, attributeSet, obtainStyledAttributes, i2, 0);
        }
        String string = obtainStyledAttributes.getString(R$styleable.RecyclerView_layoutManager);
        if (obtainStyledAttributes.getInt(R$styleable.RecyclerView_android_descendantFocusability, -1) == -1) {
            setDescendantFocusability(262144);
        }
        this.k = obtainStyledAttributes.getBoolean(R$styleable.RecyclerView_android_clipToPadding, true);
        boolean z3 = obtainStyledAttributes.getBoolean(R$styleable.RecyclerView_fastScrollEnabled, false);
        this.x = z3;
        if (z3) {
            a((StateListDrawable) obtainStyledAttributes.getDrawable(R$styleable.RecyclerView_fastScrollVerticalThumbDrawable), obtainStyledAttributes.getDrawable(R$styleable.RecyclerView_fastScrollVerticalTrackDrawable), (StateListDrawable) obtainStyledAttributes.getDrawable(R$styleable.RecyclerView_fastScrollHorizontalThumbDrawable), obtainStyledAttributes.getDrawable(R$styleable.RecyclerView_fastScrollHorizontalTrackDrawable));
        }
        obtainStyledAttributes.recycle();
        a(context, string, attributeSet, i2, 0);
        if (Build.VERSION.SDK_INT >= 21) {
            TypedArray obtainStyledAttributes2 = context.obtainStyledAttributes(attributeSet, C0, i2, 0);
            if (Build.VERSION.SDK_INT >= 29) {
                saveAttributeDataForStyleable(context, C0, attributeSet, obtainStyledAttributes2, i2, 0);
            }
            z2 = obtainStyledAttributes2.getBoolean(0, true);
            obtainStyledAttributes2.recycle();
        }
        setNestedScrollingEnabled(z2);
    }

    public void i(int i2) {
        if (!this.B) {
            y();
            o oVar = this.q;
            if (oVar == null) {
                Log.e("RecyclerView", "Cannot scroll to position a LayoutManager set. Call setLayoutManager with a non-null argument.");
                return;
            }
            oVar.i(i2);
            awakenScrollBars();
        }
    }

    public static class SavedState extends AbsSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new a();

        /* renamed from: g  reason: collision with root package name */
        Parcelable f783g;

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

        SavedState(Parcel parcel, ClassLoader classLoader) {
            super(parcel, classLoader);
            this.f783g = parcel.readParcelable(classLoader == null ? o.class.getClassLoader() : classLoader);
        }

        /* access modifiers changed from: package-private */
        public void a(SavedState savedState) {
            this.f783g = savedState.f783g;
        }

        public void writeToParcel(Parcel parcel, int i2) {
            super.writeToParcel(parcel, i2);
            parcel.writeParcelable(this.f783g, 0);
        }

        SavedState(Parcelable parcelable) {
            super(parcelable);
        }
    }

    public static class p extends ViewGroup.MarginLayoutParams {
        c0 a;
        final Rect b = new Rect();
        boolean c = true;
        boolean d = false;

        public p(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
        }

        public int a() {
            return this.a.getLayoutPosition();
        }

        public boolean b() {
            return this.a.isUpdated();
        }

        public boolean c() {
            return this.a.isRemoved();
        }

        public boolean d() {
            return this.a.isInvalid();
        }

        public p(int i2, int i3) {
            super(i2, i3);
        }

        public p(ViewGroup.MarginLayoutParams marginLayoutParams) {
            super(marginLayoutParams);
        }

        public p(ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
        }

        public p(p pVar) {
            super(pVar);
        }
    }

    public static class z {
        int a = -1;
        private SparseArray<Object> b;
        int c = 0;
        int d = 0;
        int e = 1;

        /* renamed from: f  reason: collision with root package name */
        int f804f = 0;

        /* renamed from: g  reason: collision with root package name */
        boolean f805g = false;

        /* renamed from: h  reason: collision with root package name */
        boolean f806h = false;

        /* renamed from: i  reason: collision with root package name */
        boolean f807i = false;

        /* renamed from: j  reason: collision with root package name */
        boolean f808j = false;
        boolean k = false;
        boolean l = false;
        int m;
        long n;
        int o;
        int p;
        int q;

        /* access modifiers changed from: package-private */
        public void a(int i2) {
            if ((this.e & i2) == 0) {
                throw new IllegalStateException("Layout state should be one of " + Integer.toBinaryString(i2) + " but it is " + Integer.toBinaryString(this.e));
            }
        }

        public int b() {
            return this.a;
        }

        public boolean c() {
            return this.a != -1;
        }

        public boolean d() {
            return this.f806h;
        }

        public boolean e() {
            return this.l;
        }

        public String toString() {
            return "State{mTargetPosition=" + this.a + ", mData=" + this.b + ", mItemCount=" + this.f804f + ", mIsMeasuring=" + this.f808j + ", mPreviousLayoutItemCount=" + this.c + ", mDeletedInvisibleItemCountSincePreviousLayout=" + this.d + ", mStructureChanged=" + this.f805g + ", mInPreLayout=" + this.f806h + ", mRunSimpleAnimations=" + this.k + ", mRunPredictiveAnimations=" + this.l + '}';
        }

        /* access modifiers changed from: package-private */
        public void a(g gVar) {
            this.e = 1;
            this.f804f = gVar.getItemCount();
            this.f806h = false;
            this.f807i = false;
            this.f808j = false;
        }

        public int a() {
            return this.f806h ? this.c - this.d : this.f804f;
        }
    }

    /* access modifiers changed from: protected */
    public ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        o oVar = this.q;
        if (oVar != null) {
            return oVar.a(layoutParams);
        }
        throw new IllegalStateException("RecyclerView has no LayoutManager" + i());
    }

    private void a(Context context, String str, AttributeSet attributeSet, int i2, int i3) {
        ClassLoader classLoader;
        Constructor<? extends U> constructor;
        if (str != null) {
            String trim = str.trim();
            if (!trim.isEmpty()) {
                String a2 = a(context, trim);
                try {
                    if (isInEditMode()) {
                        classLoader = getClass().getClassLoader();
                    } else {
                        classLoader = context.getClassLoader();
                    }
                    Class<? extends U> asSubclass = Class.forName(a2, false, classLoader).asSubclass(o.class);
                    Object[] objArr = null;
                    try {
                        constructor = asSubclass.getConstructor(J0);
                        objArr = new Object[]{context, attributeSet, Integer.valueOf(i2), Integer.valueOf(i3)};
                    } catch (NoSuchMethodException e2) {
                        constructor = asSubclass.getConstructor(new Class[0]);
                    }
                    constructor.setAccessible(true);
                    setLayoutManager((o) constructor.newInstance(objArr));
                } catch (NoSuchMethodException e3) {
                    e3.initCause(e2);
                    throw new IllegalStateException(attributeSet.getPositionDescription() + ": Error creating LayoutManager " + a2, e3);
                } catch (ClassNotFoundException e4) {
                    throw new IllegalStateException(attributeSet.getPositionDescription() + ": Unable to find LayoutManager " + a2, e4);
                } catch (InvocationTargetException e5) {
                    throw new IllegalStateException(attributeSet.getPositionDescription() + ": Could not instantiate the LayoutManager: " + a2, e5);
                } catch (InstantiationException e6) {
                    throw new IllegalStateException(attributeSet.getPositionDescription() + ": Could not instantiate the LayoutManager: " + a2, e6);
                } catch (IllegalAccessException e7) {
                    throw new IllegalStateException(attributeSet.getPositionDescription() + ": Cannot access non-public constructor " + a2, e7);
                } catch (ClassCastException e8) {
                    throw new IllegalStateException(attributeSet.getPositionDescription() + ": Class is not a LayoutManager " + a2, e8);
                }
            }
        }
    }

    public boolean e(int i2, int i3) {
        o oVar = this.q;
        int i4 = 0;
        if (oVar == null) {
            Log.e("RecyclerView", "Cannot fling without a LayoutManager set. Call setLayoutManager with a non-null argument.");
            return false;
        } else if (this.B) {
            return false;
        } else {
            boolean a2 = oVar.a();
            boolean b2 = this.q.b();
            if (!a2 || Math.abs(i2) < this.d0) {
                i2 = 0;
            }
            if (!b2 || Math.abs(i3) < this.d0) {
                i3 = 0;
            }
            if (i2 == 0 && i3 == 0) {
                return false;
            }
            float f2 = (float) i2;
            float f3 = (float) i3;
            if (!dispatchNestedPreFling(f2, f3)) {
                boolean z2 = a2 || b2;
                dispatchNestedFling(f2, f3, z2);
                r rVar = this.c0;
                if (rVar != null && rVar.a(i2, i3)) {
                    return true;
                }
                if (z2) {
                    if (a2) {
                        i4 = 1;
                    }
                    if (b2) {
                        i4 |= 2;
                    }
                    j(i4, 1);
                    int i5 = this.e0;
                    int max = Math.max(-i5, Math.min(i2, i5));
                    int i6 = this.e0;
                    this.i0.a(max, Math.max(-i6, Math.min(i3, i6)));
                    return true;
                }
            }
            return false;
        }
    }

    public boolean j() {
        return !this.y || this.H || this.f780h.c();
    }

    static c0 m(View view) {
        if (view == null) {
            return null;
        }
        return ((p) view.getLayoutParams()).a;
    }

    public boolean j(int i2, int i3) {
        return getScrollingChildHelper().a(i2, i3);
    }

    private boolean k(int i2, int i3) {
        a(this.u0);
        int[] iArr = this.u0;
        return (iArr[0] == i2 && iArr[1] == i3) ? false : true;
    }

    /* access modifiers changed from: package-private */
    public void b() {
        if (!this.y || this.H) {
            androidx.core.d.b.a("RV FullInvalidate");
            c();
            androidx.core.d.b.a();
        } else if (this.f780h.c()) {
            if (this.f780h.c(4) && !this.f780h.c(11)) {
                androidx.core.d.b.a("RV PartialInvalidate");
                x();
                r();
                this.f780h.e();
                if (!this.A) {
                    if (F()) {
                        c();
                    } else {
                        this.f780h.a();
                    }
                }
                c(true);
                s();
                androidx.core.d.b.a();
            } else if (this.f780h.c()) {
                androidx.core.d.b.a("RV FullInvalidate");
                c();
                androidx.core.d.b.a();
            }
        }
    }

    public c0 d(View view) {
        View c2 = c(view);
        if (c2 == null) {
            return null;
        }
        return g(c2);
    }

    /* access modifiers changed from: package-private */
    public void f(int i2, int i3) {
        int b2 = this.f781i.b();
        for (int i4 = 0; i4 < b2; i4++) {
            c0 m2 = m(this.f781i.d(i4));
            if (m2 != null && !m2.shouldIgnore() && m2.mPosition >= i2) {
                m2.offsetPosition(i3, false);
                this.l0.f805g = true;
            }
        }
        this.f778f.a(i2, i3);
        requestLayout();
    }

    /* access modifiers changed from: package-private */
    public void g(int i2, int i3) {
        int i4;
        int i5;
        int i6;
        int i7;
        int b2 = this.f781i.b();
        if (i2 < i3) {
            i6 = -1;
            i5 = i2;
            i4 = i3;
        } else {
            i4 = i2;
            i5 = i3;
            i6 = 1;
        }
        for (int i8 = 0; i8 < b2; i8++) {
            c0 m2 = m(this.f781i.d(i8));
            if (m2 != null && (i7 = m2.mPosition) >= i5 && i7 <= i4) {
                if (i7 == i2) {
                    m2.offsetPosition(i3 - i2, false);
                } else {
                    m2.offsetPosition(i6, false);
                }
                this.l0.f805g = true;
            }
        }
        this.f778f.b(i2, i3);
        requestLayout();
    }

    /* access modifiers changed from: package-private */
    public Rect h(View view) {
        p pVar = (p) view.getLayoutParams();
        if (!pVar.c) {
            return pVar.b;
        }
        if (this.l0.d() && (pVar.b() || pVar.d())) {
            return pVar.b;
        }
        Rect rect = pVar.b;
        rect.set(0, 0, 0, 0);
        int size = this.s.size();
        for (int i2 = 0; i2 < size; i2++) {
            this.m.set(0, 0, 0, 0);
            this.s.get(i2).a(this.m, view, this, this.l0);
            int i3 = rect.left;
            Rect rect2 = this.m;
            rect.left = i3 + rect2.left;
            rect.top += rect2.top;
            rect.right += rect2.right;
            rect.bottom += rect2.bottom;
        }
        pVar.c = false;
        return rect;
    }

    public void i(int i2, int i3) {
        a(i2, i3, (Interpolator) null);
    }

    public c0 d(int i2) {
        return a(i2, false);
    }

    private void c(MotionEvent motionEvent) {
        int actionIndex = motionEvent.getActionIndex();
        if (motionEvent.getPointerId(actionIndex) == this.S) {
            int i2 = actionIndex == 0 ? 1 : 0;
            this.S = motionEvent.getPointerId(i2);
            int x2 = (int) (motionEvent.getX(i2) + 0.5f);
            this.W = x2;
            this.U = x2;
            int y2 = (int) (motionEvent.getY(i2) + 0.5f);
            this.a0 = y2;
            this.V = y2;
        }
    }

    /* access modifiers changed from: package-private */
    public void d(int i2, int i3) {
        this.K++;
        int scrollX = getScrollX();
        int scrollY = getScrollY();
        onScrollChanged(scrollX, scrollY, scrollX - i2, scrollY - i3);
        h(i2, i3);
        t tVar = this.m0;
        if (tVar != null) {
            tVar.a(this, i2, i3);
        }
        List<t> list = this.n0;
        if (list != null) {
            for (int size = list.size() - 1; size >= 0; size--) {
                this.n0.get(size).a(this, i2, i3);
            }
        }
        this.K--;
    }

    public int f(View view) {
        c0 m2 = m(view);
        if (m2 != null) {
            return m2.getLayoutPosition();
        }
        return -1;
    }

    /* access modifiers changed from: package-private */
    public void c(int i2, int i3) {
        setMeasuredDimension(o.a(i2, getPaddingLeft() + getPaddingRight(), androidx.core.h.v.q(this)), o.a(i3, getPaddingTop() + getPaddingBottom(), androidx.core.h.v.p(this)));
    }

    public c0 g(View view) {
        ViewParent parent = view.getParent();
        if (parent == null || parent == this) {
            return m(view);
        }
        throw new IllegalArgumentException("View " + view + " is not a direct child of " + this);
    }

    public void f(int i2) {
        int a2 = this.f781i.a();
        for (int i3 = 0; i3 < a2; i3++) {
            this.f781i.c(i3).offsetLeftAndRight(i2);
        }
    }

    /* access modifiers changed from: package-private */
    public void e() {
        if (this.P == null) {
            EdgeEffect a2 = this.L.a(this, 3);
            this.P = a2;
            if (this.k) {
                a2.setSize((getMeasuredWidth() - getPaddingLeft()) - getPaddingRight(), (getMeasuredHeight() - getPaddingTop()) - getPaddingBottom());
            } else {
                a2.setSize(getMeasuredWidth(), getMeasuredHeight());
            }
        }
    }

    public void g(int i2) {
        int a2 = this.f781i.a();
        for (int i3 = 0; i3 < a2; i3++) {
            this.f781i.c(i3).offsetTopAndBottom(i2);
        }
    }

    /* access modifiers changed from: package-private */
    public void d() {
        int i2;
        for (int size = this.z0.size() - 1; size >= 0; size--) {
            c0 c0Var = this.z0.get(size);
            if (c0Var.itemView.getParent() == this && !c0Var.shouldIgnore() && (i2 = c0Var.mPendingAccessibilityState) != -1) {
                androidx.core.h.v.h(c0Var.itemView, i2);
                c0Var.mPendingAccessibilityState = -1;
            }
        }
        this.z0.clear();
    }

    /* access modifiers changed from: package-private */
    public void c() {
        if (this.p == null) {
            Log.e("RecyclerView", "No adapter attached; skipping layout");
        } else if (this.q == null) {
            Log.e("RecyclerView", "No layout manager attached; skipping layout");
        } else {
            z zVar = this.l0;
            zVar.f808j = false;
            if (zVar.e == 1) {
                B();
                this.q.e(this);
                C();
            } else if (!this.f780h.d() && this.q.r() == getWidth() && this.q.h() == getHeight()) {
                this.q.e(this);
            } else {
                this.q.e(this);
                C();
            }
            D();
        }
    }

    private String a(Context context, String str) {
        if (str.charAt(0) == '.') {
            return context.getPackageName() + str;
        } else if (str.contains(".")) {
            return str;
        } else {
            return RecyclerView.class.getPackage().getName() + '.' + str;
        }
    }

    public int e(View view) {
        c0 m2 = m(view);
        if (m2 != null) {
            return m2.getAdapterPosition();
        }
        return -1;
    }

    static void e(c0 c0Var) {
        WeakReference<RecyclerView> weakReference = c0Var.mNestedRecyclerView;
        if (weakReference != null) {
            View view = (View) weakReference.get();
            while (view != null) {
                if (view != c0Var.itemView) {
                    ViewParent parent = view.getParent();
                    view = parent instanceof View ? (View) parent : null;
                } else {
                    return;
                }
            }
            c0Var.mNestedRecyclerView = null;
        }
    }

    private void a(g gVar, boolean z2, boolean z3) {
        g gVar2 = this.p;
        if (gVar2 != null) {
            gVar2.unregisterAdapterDataObserver(this.e);
            this.p.onDetachedFromRecyclerView(this);
        }
        if (!z2 || z3) {
            u();
        }
        this.f780h.f();
        g gVar3 = this.p;
        this.p = gVar;
        if (gVar != null) {
            gVar.registerAdapterDataObserver(this.e);
            gVar.onAttachedToRecyclerView(this);
        }
        o oVar = this.q;
        if (oVar != null) {
            oVar.a(gVar3, this.p);
        }
        this.f778f.a(gVar3, this.p, z2);
        this.l0.f805g = true;
    }

    /* access modifiers changed from: package-private */
    public void b(int i2, int i3) {
        boolean z2;
        EdgeEffect edgeEffect = this.M;
        if (edgeEffect == null || edgeEffect.isFinished() || i2 <= 0) {
            z2 = false;
        } else {
            this.M.onRelease();
            z2 = this.M.isFinished();
        }
        EdgeEffect edgeEffect2 = this.O;
        if (edgeEffect2 != null && !edgeEffect2.isFinished() && i2 < 0) {
            this.O.onRelease();
            z2 |= this.O.isFinished();
        }
        EdgeEffect edgeEffect3 = this.N;
        if (edgeEffect3 != null && !edgeEffect3.isFinished() && i3 > 0) {
            this.N.onRelease();
            z2 |= this.N.isFinished();
        }
        EdgeEffect edgeEffect4 = this.P;
        if (edgeEffect4 != null && !edgeEffect4.isFinished() && i3 < 0) {
            this.P.onRelease();
            z2 |= this.P.isFinished();
        }
        if (z2) {
            androidx.core.h.v.H(this);
        }
    }

    /* access modifiers changed from: package-private */
    public long c(c0 c0Var) {
        return this.p.hasStableIds() ? c0Var.getItemId() : (long) c0Var.mPosition;
    }

    public View c(View view) {
        ViewParent parent = view.getParent();
        while (parent != null && parent != this && (parent instanceof View)) {
            view = (View) parent;
            parent = view.getParent();
        }
        if (parent == this) {
            return view;
        }
        return null;
    }

    private boolean b(MotionEvent motionEvent) {
        int action = motionEvent.getAction();
        int size = this.t.size();
        int i2 = 0;
        while (i2 < size) {
            s sVar = this.t.get(i2);
            if (!sVar.onInterceptTouchEvent(this, motionEvent) || action == 3) {
                i2++;
            } else {
                this.u = sVar;
                return true;
            }
        }
        return false;
    }

    public void a(n nVar, int i2) {
        o oVar = this.q;
        if (oVar != null) {
            oVar.a("Cannot add item decoration during a scroll  or layout");
        }
        if (this.s.isEmpty()) {
            setWillNotDraw(false);
        }
        if (i2 < 0) {
            this.s.add(nVar);
        } else {
            this.s.add(i2, nVar);
        }
        p();
        requestLayout();
    }

    public c0 c(int i2) {
        c0 c0Var = null;
        if (this.H) {
            return null;
        }
        int b2 = this.f781i.b();
        for (int i3 = 0; i3 < b2; i3++) {
            c0 m2 = m(this.f781i.d(i3));
            if (m2 != null && !m2.isRemoved() && b(m2) == i2) {
                if (!this.f781i.c(m2.itemView)) {
                    return m2;
                }
                c0Var = m2;
            }
        }
        return c0Var;
    }

    /* access modifiers changed from: package-private */
    public void b(c0 c0Var, l.c cVar, l.c cVar2) {
        d(c0Var);
        c0Var.setIsRecyclable(false);
        if (this.Q.b(c0Var, cVar, cVar2)) {
            t();
        }
    }

    public void a(n nVar) {
        a(nVar, -1);
    }

    /* access modifiers changed from: package-private */
    public void a(int i2, int i3, int[] iArr) {
        x();
        r();
        androidx.core.d.b.a("RV Scroll");
        a(this.l0);
        int a2 = i2 != 0 ? this.q.a(i2, this.f778f, this.l0) : 0;
        int b2 = i3 != 0 ? this.q.b(i3, this.f778f, this.l0) : 0;
        androidx.core.d.b.a();
        v();
        s();
        c(false);
        if (iArr != null) {
            iArr[0] = a2;
            iArr[1] = b2;
        }
    }

    /* access modifiers changed from: package-private */
    public void b(boolean z2) {
        this.I = z2 | this.I;
        this.H = true;
        q();
    }

    static void b(View view, Rect rect) {
        p pVar = (p) view.getLayoutParams();
        Rect rect2 = pVar.b;
        rect.set((view.getLeft() - rect2.left) - pVar.leftMargin, (view.getTop() - rect2.top) - pVar.topMargin, view.getRight() + rect2.right + pVar.rightMargin, view.getBottom() + rect2.bottom + pVar.bottomMargin);
    }

    /* access modifiers changed from: package-private */
    public void b(int i2) {
        o oVar = this.q;
        if (oVar != null) {
            oVar.g(i2);
        }
        h(i2);
        t tVar = this.m0;
        if (tVar != null) {
            tVar.a(this, i2);
        }
        List<t> list = this.n0;
        if (list != null) {
            for (int size = list.size() - 1; size >= 0; size--) {
                this.n0.get(size).a(this, i2);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public boolean a(int i2, int i3, MotionEvent motionEvent) {
        int i4;
        int i5;
        int i6;
        int i7;
        int i8 = i2;
        int i9 = i3;
        MotionEvent motionEvent2 = motionEvent;
        b();
        if (this.p != null) {
            int[] iArr = this.y0;
            iArr[0] = 0;
            iArr[1] = 0;
            a(i8, i9, iArr);
            int[] iArr2 = this.y0;
            int i10 = iArr2[0];
            int i11 = iArr2[1];
            i7 = i11;
            i6 = i10;
            i5 = i8 - i10;
            i4 = i9 - i11;
        } else {
            i7 = 0;
            i6 = 0;
            i5 = 0;
            i4 = 0;
        }
        if (!this.s.isEmpty()) {
            invalidate();
        }
        int[] iArr3 = this.y0;
        iArr3[0] = 0;
        iArr3[1] = 0;
        a(i6, i7, i5, i4, this.w0, 0, iArr3);
        int[] iArr4 = this.y0;
        int i12 = i5 - iArr4[0];
        int i13 = i4 - iArr4[1];
        boolean z2 = (iArr4[0] == 0 && iArr4[1] == 0) ? false : true;
        int i14 = this.W;
        int[] iArr5 = this.w0;
        this.W = i14 - iArr5[0];
        this.a0 -= iArr5[1];
        int[] iArr6 = this.x0;
        iArr6[0] = iArr6[0] + iArr5[0];
        iArr6[1] = iArr6[1] + iArr5[1];
        if (getOverScrollMode() != 2) {
            if (motionEvent2 != null && !androidx.core.h.i.a(motionEvent2, 8194)) {
                a(motionEvent.getX(), (float) i12, motionEvent.getY(), (float) i13);
            }
            b(i2, i3);
        }
        if (!(i6 == 0 && i7 == 0)) {
            d(i6, i7);
        }
        if (!awakenScrollBars()) {
            invalidate();
        }
        if (!z2 && i6 == 0 && i7 == 0) {
            return false;
        }
        return true;
    }

    /* access modifiers changed from: package-private */
    public void b(View view) {
        c0 m2 = m(view);
        j(view);
        g gVar = this.p;
        if (!(gVar == null || m2 == null)) {
            gVar.onViewDetachedFromWindow(m2);
        }
        List<q> list = this.G;
        if (list != null) {
            for (int size = list.size() - 1; size >= 0; size--) {
                this.G.get(size).b(view);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public int b(c0 c0Var) {
        if (c0Var.hasAnyOfTheFlags(524) || !c0Var.isBound()) {
            return -1;
        }
        return this.f780h.a(c0Var.mPosition);
    }

    public void a(int i2, int i3, Interpolator interpolator) {
        a(i2, i3, interpolator, Integer.MIN_VALUE);
    }

    public void a(int i2, int i3, Interpolator interpolator, int i4) {
        a(i2, i3, interpolator, i4, false);
    }

    /* access modifiers changed from: package-private */
    public void a(int i2, int i3, Interpolator interpolator, int i4, boolean z2) {
        o oVar = this.q;
        if (oVar == null) {
            Log.e("RecyclerView", "Cannot smooth scroll without a LayoutManager set. Call setLayoutManager with a non-null argument.");
        } else if (!this.B) {
            int i5 = 0;
            if (!oVar.a()) {
                i2 = 0;
            }
            if (!this.q.b()) {
                i3 = 0;
            }
            if (i2 != 0 || i3 != 0) {
                if (i4 == Integer.MIN_VALUE || i4 > 0) {
                    if (z2) {
                        if (i2 != 0) {
                            i5 = 1;
                        }
                        if (i3 != 0) {
                            i5 |= 2;
                        }
                        j(i5, 1);
                    }
                    this.i0.a(i2, i3, i4, interpolator);
                    return;
                }
                scrollBy(i2, i3);
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:10:0x0040  */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x0056  */
    /* JADX WARNING: Removed duplicated region for block: B:20:0x007c  */
    /* JADX WARNING: Removed duplicated region for block: B:22:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void a(float r7, float r8, float r9, float r10) {
        /*
            r6 = this;
            r0 = 1065353216(0x3f800000, float:1.0)
            r1 = 1
            r2 = 0
            int r3 = (r8 > r2 ? 1 : (r8 == r2 ? 0 : -1))
            if (r3 >= 0) goto L_0x0021
            r6.f()
            android.widget.EdgeEffect r3 = r6.M
            float r4 = -r8
            int r5 = r6.getWidth()
            float r5 = (float) r5
            float r4 = r4 / r5
            int r5 = r6.getHeight()
            float r5 = (float) r5
            float r9 = r9 / r5
            float r9 = r0 - r9
            androidx.core.widget.d.a(r3, r4, r9)
        L_0x001f:
            r9 = 1
            goto L_0x003c
        L_0x0021:
            int r3 = (r8 > r2 ? 1 : (r8 == r2 ? 0 : -1))
            if (r3 <= 0) goto L_0x003b
            r6.g()
            android.widget.EdgeEffect r3 = r6.O
            int r4 = r6.getWidth()
            float r4 = (float) r4
            float r4 = r8 / r4
            int r5 = r6.getHeight()
            float r5 = (float) r5
            float r9 = r9 / r5
            androidx.core.widget.d.a(r3, r4, r9)
            goto L_0x001f
        L_0x003b:
            r9 = 0
        L_0x003c:
            int r3 = (r10 > r2 ? 1 : (r10 == r2 ? 0 : -1))
            if (r3 >= 0) goto L_0x0056
            r6.h()
            android.widget.EdgeEffect r9 = r6.N
            float r0 = -r10
            int r3 = r6.getHeight()
            float r3 = (float) r3
            float r0 = r0 / r3
            int r3 = r6.getWidth()
            float r3 = (float) r3
            float r7 = r7 / r3
            androidx.core.widget.d.a(r9, r0, r7)
            goto L_0x0072
        L_0x0056:
            int r3 = (r10 > r2 ? 1 : (r10 == r2 ? 0 : -1))
            if (r3 <= 0) goto L_0x0071
            r6.e()
            android.widget.EdgeEffect r9 = r6.P
            int r3 = r6.getHeight()
            float r3 = (float) r3
            float r3 = r10 / r3
            int r4 = r6.getWidth()
            float r4 = (float) r4
            float r7 = r7 / r4
            float r0 = r0 - r7
            androidx.core.widget.d.a(r9, r3, r0)
            goto L_0x0072
        L_0x0071:
            r1 = r9
        L_0x0072:
            if (r1 != 0) goto L_0x007c
            int r7 = (r8 > r2 ? 1 : (r8 == r2 ? 0 : -1))
            if (r7 != 0) goto L_0x007c
            int r7 = (r10 > r2 ? 1 : (r10 == r2 ? 0 : -1))
            if (r7 == 0) goto L_0x007f
        L_0x007c:
            androidx.core.h.v.H(r6)
        L_0x007f:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.recyclerview.widget.RecyclerView.a(float, float, float, float):void");
    }

    /* access modifiers changed from: package-private */
    public void a(int i2, int i3) {
        if (i2 < 0) {
            f();
            if (this.M.isFinished()) {
                this.M.onAbsorb(-i2);
            }
        } else if (i2 > 0) {
            g();
            if (this.O.isFinished()) {
                this.O.onAbsorb(i2);
            }
        }
        if (i3 < 0) {
            h();
            if (this.N.isFinished()) {
                this.N.onAbsorb(-i3);
            }
        } else if (i3 > 0) {
            e();
            if (this.P.isFinished()) {
                this.P.onAbsorb(i3);
            }
        }
        if (i2 != 0 || i3 != 0) {
            androidx.core.h.v.H(this);
        }
    }

    private boolean a(View view, View view2, int i2) {
        int i3;
        if (view2 == null || view2 == this || c(view2) == null) {
            return false;
        }
        if (view == null || c(view) == null) {
            return true;
        }
        this.m.set(0, 0, view.getWidth(), view.getHeight());
        this.n.set(0, 0, view2.getWidth(), view2.getHeight());
        offsetDescendantRectToMyCoords(view, this.m);
        offsetDescendantRectToMyCoords(view2, this.n);
        char c2 = 65535;
        int i4 = this.q.k() == 1 ? -1 : 1;
        Rect rect = this.m;
        int i5 = rect.left;
        int i6 = this.n.left;
        if ((i5 < i6 || rect.right <= i6) && this.m.right < this.n.right) {
            i3 = 1;
        } else {
            Rect rect2 = this.m;
            int i7 = rect2.right;
            int i8 = this.n.right;
            i3 = ((i7 > i8 || rect2.left >= i8) && this.m.left > this.n.left) ? -1 : 0;
        }
        Rect rect3 = this.m;
        int i9 = rect3.top;
        int i10 = this.n.top;
        if ((i9 < i10 || rect3.bottom <= i10) && this.m.bottom < this.n.bottom) {
            c2 = 1;
        } else {
            Rect rect4 = this.m;
            int i11 = rect4.bottom;
            int i12 = this.n.bottom;
            if ((i11 <= i12 && rect4.top < i12) || this.m.top <= this.n.top) {
                c2 = 0;
            }
        }
        if (i2 != 1) {
            if (i2 != 2) {
                if (i2 != 17) {
                    if (i2 != 33) {
                        if (i2 != 66) {
                            if (i2 != 130) {
                                throw new IllegalArgumentException("Invalid direction: " + i2 + i());
                            } else if (c2 > 0) {
                                return true;
                            } else {
                                return false;
                            }
                        } else if (i3 > 0) {
                            return true;
                        } else {
                            return false;
                        }
                    } else if (c2 < 0) {
                        return true;
                    } else {
                        return false;
                    }
                } else if (i3 < 0) {
                    return true;
                } else {
                    return false;
                }
            } else if (c2 > 0 || (c2 == 0 && i3 * i4 >= 0)) {
                return true;
            } else {
                return false;
            }
        } else if (c2 < 0 || (c2 == 0 && i3 * i4 <= 0)) {
            return true;
        } else {
            return false;
        }
    }

    private void a(View view, View view2) {
        View view3 = view2 != null ? view2 : view;
        this.m.set(0, 0, view3.getWidth(), view3.getHeight());
        ViewGroup.LayoutParams layoutParams = view3.getLayoutParams();
        if (layoutParams instanceof p) {
            p pVar = (p) layoutParams;
            if (!pVar.c) {
                Rect rect = pVar.b;
                Rect rect2 = this.m;
                rect2.left -= rect.left;
                rect2.right += rect.right;
                rect2.top -= rect.top;
                rect2.bottom += rect.bottom;
            }
        }
        if (view2 != null) {
            offsetDescendantRectToMyCoords(view2, this.m);
            offsetRectIntoDescendantCoords(view, this.m);
        }
        this.q.a(this, view, this.m, !this.y, view2 == null);
    }

    /* access modifiers changed from: package-private */
    public void a(String str) {
        if (o()) {
            if (str == null) {
                throw new IllegalStateException("Cannot call this method while RecyclerView is computing a layout or scrolling" + i());
            }
            throw new IllegalStateException(str);
        } else if (this.K > 0) {
            Log.w("RecyclerView", "Cannot call this method in a scroll callback. Scroll callbacks mightbe run during a measure & layout pass where you cannot change theRecyclerView data. Any method call that might change the structureof the RecyclerView or the adapter contents should be postponed tothe next frame.", new IllegalStateException(BuildConfig.FLAVOR + i()));
        }
    }

    private boolean a(MotionEvent motionEvent) {
        s sVar = this.u;
        if (sVar != null) {
            sVar.onTouchEvent(this, motionEvent);
            int action = motionEvent.getAction();
            if (action == 3 || action == 1) {
                this.u = null;
            }
            return true;
        } else if (motionEvent.getAction() == 0) {
            return false;
        } else {
            return b(motionEvent);
        }
    }

    /* access modifiers changed from: package-private */
    public void a(boolean z2) {
        int i2 = this.J - 1;
        this.J = i2;
        if (i2 < 1) {
            this.J = 0;
            if (z2) {
                A();
                d();
            }
        }
    }

    /* access modifiers changed from: package-private */
    public boolean a(AccessibilityEvent accessibilityEvent) {
        int i2 = 0;
        if (!o()) {
            return false;
        }
        int a2 = accessibilityEvent != null ? androidx.core.h.e0.b.a(accessibilityEvent) : 0;
        if (a2 != 0) {
            i2 = a2;
        }
        this.D |= i2;
        return true;
    }

    /* access modifiers changed from: package-private */
    public final void a(z zVar) {
        if (getScrollState() == 2) {
            OverScroller overScroller = this.i0.f785g;
            zVar.p = overScroller.getFinalX() - overScroller.getCurrX();
            zVar.q = overScroller.getFinalY() - overScroller.getCurrY();
            return;
        }
        zVar.p = 0;
        zVar.q = 0;
    }

    private void a(long j2, c0 c0Var, c0 c0Var2) {
        int a2 = this.f781i.a();
        for (int i2 = 0; i2 < a2; i2++) {
            c0 m2 = m(this.f781i.c(i2));
            if (m2 != c0Var && c(m2) == j2) {
                g gVar = this.p;
                if (gVar == null || !gVar.hasStableIds()) {
                    throw new IllegalStateException("Two different ViewHolders have the same change ID. This might happen due to inconsistent Adapter update events or if the LayoutManager lays out the same View multiple times.\n ViewHolder 1:" + m2 + " \n View Holder 2:" + c0Var + i());
                }
                throw new IllegalStateException("Two different ViewHolders have the same stable ID. Stable IDs in your adapter MUST BE unique and SHOULD NOT change.\n ViewHolder 1:" + m2 + " \n View Holder 2:" + c0Var + i());
            }
        }
        Log.e("RecyclerView", "Problem while matching changed view holders with the newones. The pre-layout information for the change holder " + c0Var2 + " cannot be found but it is necessary for " + c0Var + i());
    }

    /* access modifiers changed from: package-private */
    public void a(c0 c0Var, l.c cVar) {
        c0Var.setFlags(0, 8192);
        if (this.l0.f807i && c0Var.isUpdated() && !c0Var.isRemoved() && !c0Var.shouldIgnore()) {
            this.f782j.a(c(c0Var), c0Var);
        }
        this.f782j.c(c0Var, cVar);
    }

    private void a(int[] iArr) {
        int a2 = this.f781i.a();
        if (a2 == 0) {
            iArr[0] = -1;
            iArr[1] = -1;
            return;
        }
        int i2 = Integer.MAX_VALUE;
        int i3 = Integer.MIN_VALUE;
        for (int i4 = 0; i4 < a2; i4++) {
            c0 m2 = m(this.f781i.c(i4));
            if (!m2.shouldIgnore()) {
                int layoutPosition = m2.getLayoutPosition();
                if (layoutPosition < i2) {
                    i2 = layoutPosition;
                }
                if (layoutPosition > i3) {
                    i3 = layoutPosition;
                }
            }
        }
        iArr[0] = i2;
        iArr[1] = i3;
    }

    /* access modifiers changed from: package-private */
    public void a(c0 c0Var, l.c cVar, l.c cVar2) {
        c0Var.setIsRecyclable(false);
        if (this.Q.a(c0Var, cVar, cVar2)) {
            t();
        }
    }

    private void a(c0 c0Var, c0 c0Var2, l.c cVar, l.c cVar2, boolean z2, boolean z3) {
        c0Var.setIsRecyclable(false);
        if (z2) {
            d(c0Var);
        }
        if (c0Var != c0Var2) {
            if (z3) {
                d(c0Var2);
            }
            c0Var.mShadowedHolder = c0Var2;
            d(c0Var);
            this.f778f.c(c0Var);
            c0Var2.setIsRecyclable(false);
            c0Var2.mShadowingHolder = c0Var;
        }
        if (this.Q.a(c0Var, c0Var2, cVar, cVar2)) {
            t();
        }
    }

    /* access modifiers changed from: package-private */
    public void a() {
        int b2 = this.f781i.b();
        for (int i2 = 0; i2 < b2; i2++) {
            c0 m2 = m(this.f781i.d(i2));
            if (!m2.shouldIgnore()) {
                m2.clearOldPosition();
            }
        }
        this.f778f.b();
    }

    /* access modifiers changed from: package-private */
    public void a(int i2, int i3, boolean z2) {
        int i4 = i2 + i3;
        int b2 = this.f781i.b();
        for (int i5 = 0; i5 < b2; i5++) {
            c0 m2 = m(this.f781i.d(i5));
            if (m2 != null && !m2.shouldIgnore()) {
                int i6 = m2.mPosition;
                if (i6 >= i4) {
                    m2.offsetPosition(-i3, z2);
                    this.l0.f805g = true;
                } else if (i6 >= i2) {
                    m2.flagRemovedAndOffsetPosition(i2 - 1, -i3, z2);
                    this.l0.f805g = true;
                }
            }
        }
        this.f778f.a(i2, i3, z2);
        requestLayout();
    }

    /* access modifiers changed from: package-private */
    public void a(int i2, int i3, Object obj) {
        int i4;
        int b2 = this.f781i.b();
        int i5 = i2 + i3;
        for (int i6 = 0; i6 < b2; i6++) {
            View d2 = this.f781i.d(i6);
            c0 m2 = m(d2);
            if (m2 != null && !m2.shouldIgnore() && (i4 = m2.mPosition) >= i2 && i4 < i5) {
                m2.addFlags(2);
                m2.addChangePayload(obj);
                ((p) d2.getLayoutParams()).c = true;
            }
        }
        this.f778f.c(i2, i3);
    }

    /* access modifiers changed from: package-private */
    public boolean a(c0 c0Var) {
        l lVar = this.Q;
        return lVar == null || lVar.a(c0Var, c0Var.getUnmodifiedPayloads());
    }

    /* access modifiers changed from: package-private */
    public c0 a(int i2, boolean z2) {
        int b2 = this.f781i.b();
        c0 c0Var = null;
        for (int i3 = 0; i3 < b2; i3++) {
            c0 m2 = m(this.f781i.d(i3));
            if (m2 != null && !m2.isRemoved()) {
                if (z2) {
                    if (m2.mPosition != i2) {
                        continue;
                    }
                } else if (m2.getLayoutPosition() != i2) {
                    continue;
                }
                if (!this.f781i.c(m2.itemView)) {
                    return m2;
                }
                c0Var = m2;
            }
        }
        return c0Var;
    }

    public c0 a(long j2) {
        g gVar = this.p;
        c0 c0Var = null;
        if (gVar != null && gVar.hasStableIds()) {
            int b2 = this.f781i.b();
            for (int i2 = 0; i2 < b2; i2++) {
                c0 m2 = m(this.f781i.d(i2));
                if (m2 != null && !m2.isRemoved() && m2.getItemId() == j2) {
                    if (!this.f781i.c(m2.itemView)) {
                        return m2;
                    }
                    c0Var = m2;
                }
            }
        }
        return c0Var;
    }

    public View a(float f2, float f3) {
        for (int a2 = this.f781i.a() - 1; a2 >= 0; a2--) {
            View c2 = this.f781i.c(a2);
            float translationX = c2.getTranslationX();
            float translationY = c2.getTranslationY();
            if (f2 >= ((float) c2.getLeft()) + translationX && f2 <= ((float) c2.getRight()) + translationX && f3 >= ((float) c2.getTop()) + translationY && f3 <= ((float) c2.getBottom()) + translationY) {
                return c2;
            }
        }
        return null;
    }

    public void a(View view, Rect rect) {
        b(view, rect);
    }

    /* access modifiers changed from: package-private */
    public void a(View view) {
        c0 m2 = m(view);
        i(view);
        g gVar = this.p;
        if (!(gVar == null || m2 == null)) {
            gVar.onViewAttachedToWindow(m2);
        }
        List<q> list = this.G;
        if (list != null) {
            for (int size = list.size() - 1; size >= 0; size--) {
                this.G.get(size).a(view);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public boolean a(c0 c0Var, int i2) {
        if (o()) {
            c0Var.mPendingAccessibilityState = i2;
            this.z0.add(c0Var);
            return false;
        }
        androidx.core.h.v.h(c0Var.itemView, i2);
        return true;
    }

    /* access modifiers changed from: package-private */
    public void a(StateListDrawable stateListDrawable, Drawable drawable, StateListDrawable stateListDrawable2, Drawable drawable2) {
        if (stateListDrawable == null || drawable == null || stateListDrawable2 == null || drawable2 == null) {
            throw new IllegalArgumentException("Trying to set fast scroller without both required drawables." + i());
        }
        Resources resources = getContext().getResources();
        new h(this, stateListDrawable, drawable, stateListDrawable2, drawable2, resources.getDimensionPixelSize(R$dimen.fastscroll_default_thickness), resources.getDimensionPixelSize(R$dimen.fastscroll_minimum_range), resources.getDimensionPixelOffset(R$dimen.fastscroll_margin));
    }

    public void a(int i2) {
        getScrollingChildHelper().c(i2);
    }

    public final void a(int i2, int i3, int i4, int i5, int[] iArr, int i6, int[] iArr2) {
        getScrollingChildHelper().a(i2, i3, i4, i5, iArr, i6, iArr2);
    }

    public boolean a(int i2, int i3, int[] iArr, int[] iArr2, int i4) {
        return getScrollingChildHelper().a(i2, i3, iArr, iArr2, i4);
    }
}
