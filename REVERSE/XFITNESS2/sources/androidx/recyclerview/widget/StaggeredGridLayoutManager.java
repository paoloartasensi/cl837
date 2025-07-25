package androidx.recyclerview.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.PointF;
import android.graphics.Rect;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import androidx.core.h.e0.d;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.List;

public class StaggeredGridLayoutManager extends RecyclerView.o implements RecyclerView.y.b {
    boolean A = false;
    private BitSet B;
    int C = -1;
    int D = Integer.MIN_VALUE;
    LazySpanLookup E = new LazySpanLookup();
    private int F = 2;
    private boolean G;
    private boolean H;
    private SavedState I;
    private int J;
    private final Rect K = new Rect();
    private final b L = new b();
    private boolean M = false;
    private boolean N = true;
    private int[] O;
    private final Runnable P = new a();
    private int s = -1;
    d[] t;
    q u;
    q v;
    private int w;
    private int x;
    private final m y;
    boolean z = false;

    @SuppressLint({"BanParcelableUsage"})
    public static class SavedState implements Parcelable {
        public static final Parcelable.Creator<SavedState> CREATOR = new a();
        int e;

        /* renamed from: f  reason: collision with root package name */
        int f812f;

        /* renamed from: g  reason: collision with root package name */
        int f813g;

        /* renamed from: h  reason: collision with root package name */
        int[] f814h;

        /* renamed from: i  reason: collision with root package name */
        int f815i;

        /* renamed from: j  reason: collision with root package name */
        int[] f816j;
        List<LazySpanLookup.FullSpanItem> k;
        boolean l;
        boolean m;
        boolean n;

        static class a implements Parcelable.Creator<SavedState> {
            a() {
            }

            public SavedState createFromParcel(Parcel parcel) {
                return new SavedState(parcel);
            }

            public SavedState[] newArray(int i2) {
                return new SavedState[i2];
            }
        }

        public SavedState() {
        }

        /* access modifiers changed from: package-private */
        public void a() {
            this.f814h = null;
            this.f813g = 0;
            this.e = -1;
            this.f812f = -1;
        }

        /* access modifiers changed from: package-private */
        public void b() {
            this.f814h = null;
            this.f813g = 0;
            this.f815i = 0;
            this.f816j = null;
            this.k = null;
        }

        public int describeContents() {
            return 0;
        }

        public void writeToParcel(Parcel parcel, int i2) {
            parcel.writeInt(this.e);
            parcel.writeInt(this.f812f);
            parcel.writeInt(this.f813g);
            if (this.f813g > 0) {
                parcel.writeIntArray(this.f814h);
            }
            parcel.writeInt(this.f815i);
            if (this.f815i > 0) {
                parcel.writeIntArray(this.f816j);
            }
            parcel.writeInt(this.l ? 1 : 0);
            parcel.writeInt(this.m ? 1 : 0);
            parcel.writeInt(this.n ? 1 : 0);
            parcel.writeList(this.k);
        }

        SavedState(Parcel parcel) {
            this.e = parcel.readInt();
            this.f812f = parcel.readInt();
            int readInt = parcel.readInt();
            this.f813g = readInt;
            if (readInt > 0) {
                int[] iArr = new int[readInt];
                this.f814h = iArr;
                parcel.readIntArray(iArr);
            }
            int readInt2 = parcel.readInt();
            this.f815i = readInt2;
            if (readInt2 > 0) {
                int[] iArr2 = new int[readInt2];
                this.f816j = iArr2;
                parcel.readIntArray(iArr2);
            }
            boolean z = false;
            this.l = parcel.readInt() == 1;
            this.m = parcel.readInt() == 1;
            this.n = parcel.readInt() == 1 ? true : z;
            this.k = parcel.readArrayList(LazySpanLookup.FullSpanItem.class.getClassLoader());
        }

        public SavedState(SavedState savedState) {
            this.f813g = savedState.f813g;
            this.e = savedState.e;
            this.f812f = savedState.f812f;
            this.f814h = savedState.f814h;
            this.f815i = savedState.f815i;
            this.f816j = savedState.f816j;
            this.l = savedState.l;
            this.m = savedState.m;
            this.n = savedState.n;
            this.k = savedState.k;
        }
    }

    class a implements Runnable {
        a() {
        }

        public void run() {
            StaggeredGridLayoutManager.this.G();
        }
    }

    public static class c extends RecyclerView.p {
        d e;

        /* renamed from: f  reason: collision with root package name */
        boolean f819f;

        public c(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
        }

        public void a(boolean z) {
            this.f819f = z;
        }

        public final int e() {
            d dVar = this.e;
            if (dVar == null) {
                return -1;
            }
            return dVar.e;
        }

        public boolean f() {
            return this.f819f;
        }

        public c(int i2, int i3) {
            super(i2, i3);
        }

        public c(ViewGroup.MarginLayoutParams marginLayoutParams) {
            super(marginLayoutParams);
        }

        public c(ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
        }
    }

    class d {
        ArrayList<View> a = new ArrayList<>();
        int b = Integer.MIN_VALUE;
        int c = Integer.MIN_VALUE;
        int d = 0;
        final int e;

        d(int i2) {
            this.e = i2;
        }

        /* access modifiers changed from: package-private */
        public int a(int i2) {
            int i3 = this.c;
            if (i3 != Integer.MIN_VALUE) {
                return i3;
            }
            if (this.a.size() == 0) {
                return i2;
            }
            a();
            return this.c;
        }

        /* access modifiers changed from: package-private */
        public int b(int i2) {
            int i3 = this.b;
            if (i3 != Integer.MIN_VALUE) {
                return i3;
            }
            if (this.a.size() == 0) {
                return i2;
            }
            b();
            return this.b;
        }

        /* access modifiers changed from: package-private */
        public void c(View view) {
            c b2 = b(view);
            b2.e = this;
            this.a.add(0, view);
            this.b = Integer.MIN_VALUE;
            if (this.a.size() == 1) {
                this.c = Integer.MIN_VALUE;
            }
            if (b2.c() || b2.b()) {
                this.d += StaggeredGridLayoutManager.this.u.b(view);
            }
        }

        /* access modifiers changed from: package-private */
        public void d(int i2) {
            this.b = i2;
            this.c = i2;
        }

        public int e() {
            if (StaggeredGridLayoutManager.this.z) {
                return b(0, this.a.size(), true);
            }
            return b(this.a.size() - 1, -1, true);
        }

        public int f() {
            if (StaggeredGridLayoutManager.this.z) {
                return a(0, this.a.size(), true);
            }
            return a(this.a.size() - 1, -1, true);
        }

        public int g() {
            return this.d;
        }

        /* access modifiers changed from: package-private */
        public int h() {
            int i2 = this.c;
            if (i2 != Integer.MIN_VALUE) {
                return i2;
            }
            a();
            return this.c;
        }

        /* access modifiers changed from: package-private */
        public int i() {
            int i2 = this.b;
            if (i2 != Integer.MIN_VALUE) {
                return i2;
            }
            b();
            return this.b;
        }

        /* access modifiers changed from: package-private */
        public void j() {
            this.b = Integer.MIN_VALUE;
            this.c = Integer.MIN_VALUE;
        }

        /* access modifiers changed from: package-private */
        public void k() {
            int size = this.a.size();
            View remove = this.a.remove(size - 1);
            c b2 = b(remove);
            b2.e = null;
            if (b2.c() || b2.b()) {
                this.d -= StaggeredGridLayoutManager.this.u.b(remove);
            }
            if (size == 1) {
                this.b = Integer.MIN_VALUE;
            }
            this.c = Integer.MIN_VALUE;
        }

        /* access modifiers changed from: package-private */
        public void l() {
            View remove = this.a.remove(0);
            c b2 = b(remove);
            b2.e = null;
            if (this.a.size() == 0) {
                this.c = Integer.MIN_VALUE;
            }
            if (b2.c() || b2.b()) {
                this.d -= StaggeredGridLayoutManager.this.u.b(remove);
            }
            this.b = Integer.MIN_VALUE;
        }

        public int d() {
            if (StaggeredGridLayoutManager.this.z) {
                return a(this.a.size() - 1, -1, true);
            }
            return a(0, this.a.size(), true);
        }

        /* access modifiers changed from: package-private */
        public void a() {
            LazySpanLookup.FullSpanItem c2;
            ArrayList<View> arrayList = this.a;
            View view = arrayList.get(arrayList.size() - 1);
            c b2 = b(view);
            this.c = StaggeredGridLayoutManager.this.u.a(view);
            if (b2.f819f && (c2 = StaggeredGridLayoutManager.this.E.c(b2.a())) != null && c2.f809f == 1) {
                this.c += c2.a(this.e);
            }
        }

        /* access modifiers changed from: package-private */
        public void b() {
            LazySpanLookup.FullSpanItem c2;
            View view = this.a.get(0);
            c b2 = b(view);
            this.b = StaggeredGridLayoutManager.this.u.d(view);
            if (b2.f819f && (c2 = StaggeredGridLayoutManager.this.E.c(b2.a())) != null && c2.f809f == -1) {
                this.b -= c2.a(this.e);
            }
        }

        /* access modifiers changed from: package-private */
        public void c() {
            this.a.clear();
            j();
            this.d = 0;
        }

        /* access modifiers changed from: package-private */
        public void c(int i2) {
            int i3 = this.b;
            if (i3 != Integer.MIN_VALUE) {
                this.b = i3 + i2;
            }
            int i4 = this.c;
            if (i4 != Integer.MIN_VALUE) {
                this.c = i4 + i2;
            }
        }

        /* access modifiers changed from: package-private */
        public void a(View view) {
            c b2 = b(view);
            b2.e = this;
            this.a.add(view);
            this.c = Integer.MIN_VALUE;
            if (this.a.size() == 1) {
                this.b = Integer.MIN_VALUE;
            }
            if (b2.c() || b2.b()) {
                this.d += StaggeredGridLayoutManager.this.u.b(view);
            }
        }

        /* access modifiers changed from: package-private */
        public c b(View view) {
            return (c) view.getLayoutParams();
        }

        /* access modifiers changed from: package-private */
        public int b(int i2, int i3, boolean z) {
            return a(i2, i3, z, true, false);
        }

        /* access modifiers changed from: package-private */
        public void a(boolean z, int i2) {
            int i3;
            if (z) {
                i3 = a(Integer.MIN_VALUE);
            } else {
                i3 = b(Integer.MIN_VALUE);
            }
            c();
            if (i3 != Integer.MIN_VALUE) {
                if (z && i3 < StaggeredGridLayoutManager.this.u.b()) {
                    return;
                }
                if (z || i3 <= StaggeredGridLayoutManager.this.u.f()) {
                    if (i2 != Integer.MIN_VALUE) {
                        i3 += i2;
                    }
                    this.c = i3;
                    this.b = i3;
                }
            }
        }

        /* access modifiers changed from: package-private */
        public int a(int i2, int i3, boolean z, boolean z2, boolean z3) {
            int f2 = StaggeredGridLayoutManager.this.u.f();
            int b2 = StaggeredGridLayoutManager.this.u.b();
            int i4 = i3 > i2 ? 1 : -1;
            while (i2 != i3) {
                View view = this.a.get(i2);
                int d2 = StaggeredGridLayoutManager.this.u.d(view);
                int a2 = StaggeredGridLayoutManager.this.u.a(view);
                boolean z4 = false;
                boolean z5 = !z3 ? d2 < b2 : d2 <= b2;
                if (!z3 ? a2 > f2 : a2 >= f2) {
                    z4 = true;
                }
                if (z5 && z4) {
                    if (!z || !z2) {
                        if (z2) {
                            return StaggeredGridLayoutManager.this.l(view);
                        }
                        if (d2 < f2 || a2 > b2) {
                            return StaggeredGridLayoutManager.this.l(view);
                        }
                    } else if (d2 >= f2 && a2 <= b2) {
                        return StaggeredGridLayoutManager.this.l(view);
                    }
                }
                i2 += i4;
            }
            return -1;
        }

        /* access modifiers changed from: package-private */
        public int a(int i2, int i3, boolean z) {
            return a(i2, i3, false, false, z);
        }

        public View a(int i2, int i3) {
            View view = null;
            if (i3 != -1) {
                int size = this.a.size() - 1;
                while (size >= 0) {
                    View view2 = this.a.get(size);
                    StaggeredGridLayoutManager staggeredGridLayoutManager = StaggeredGridLayoutManager.this;
                    if (staggeredGridLayoutManager.z && staggeredGridLayoutManager.l(view2) >= i2) {
                        break;
                    }
                    StaggeredGridLayoutManager staggeredGridLayoutManager2 = StaggeredGridLayoutManager.this;
                    if ((!staggeredGridLayoutManager2.z && staggeredGridLayoutManager2.l(view2) <= i2) || !view2.hasFocusable()) {
                        break;
                    }
                    size--;
                    view = view2;
                }
            } else {
                int size2 = this.a.size();
                int i4 = 0;
                while (i4 < size2) {
                    View view3 = this.a.get(i4);
                    StaggeredGridLayoutManager staggeredGridLayoutManager3 = StaggeredGridLayoutManager.this;
                    if (staggeredGridLayoutManager3.z && staggeredGridLayoutManager3.l(view3) <= i2) {
                        break;
                    }
                    StaggeredGridLayoutManager staggeredGridLayoutManager4 = StaggeredGridLayoutManager.this;
                    if ((!staggeredGridLayoutManager4.z && staggeredGridLayoutManager4.l(view3) >= i2) || !view3.hasFocusable()) {
                        break;
                    }
                    i4++;
                    view = view3;
                }
            }
            return view;
        }
    }

    public StaggeredGridLayoutManager(Context context, AttributeSet attributeSet, int i2, int i3) {
        RecyclerView.o.d a2 = RecyclerView.o.a(context, attributeSet, i2, i3);
        j(a2.a);
        k(a2.b);
        c(a2.c);
        this.y = new m();
        O();
    }

    private void O() {
        this.u = q.a(this, this.w);
        this.v = q.a(this, 1 - this.w);
    }

    private void P() {
        if (this.v.d() != 1073741824) {
            float f2 = 0.0f;
            int e = e();
            for (int i2 = 0; i2 < e; i2++) {
                View d2 = d(i2);
                float b2 = (float) this.v.b(d2);
                if (b2 >= f2) {
                    if (((c) d2.getLayoutParams()).f()) {
                        b2 = (b2 * 1.0f) / ((float) this.s);
                    }
                    f2 = Math.max(f2, b2);
                }
            }
            int i3 = this.x;
            int round = Math.round(f2 * ((float) this.s));
            if (this.v.d() == Integer.MIN_VALUE) {
                round = Math.min(round, this.v.g());
            }
            l(round);
            if (this.x != i3) {
                for (int i4 = 0; i4 < e; i4++) {
                    View d3 = d(i4);
                    c cVar = (c) d3.getLayoutParams();
                    if (!cVar.f819f) {
                        if (!N() || this.w != 1) {
                            int i5 = cVar.e.e;
                            int i6 = this.x * i5;
                            int i7 = i5 * i3;
                            if (this.w == 1) {
                                d3.offsetLeftAndRight(i6 - i7);
                            } else {
                                d3.offsetTopAndBottom(i6 - i7);
                            }
                        } else {
                            int i8 = this.s;
                            int i9 = cVar.e.e;
                            d3.offsetLeftAndRight(((-((i8 - 1) - i9)) * this.x) - ((-((i8 - 1) - i9)) * i3));
                        }
                    }
                }
            }
        }
    }

    private void Q() {
        if (this.w == 1 || !N()) {
            this.A = this.z;
        } else {
            this.A = !this.z;
        }
    }

    private boolean a(d dVar) {
        if (this.A) {
            if (dVar.h() < this.u.b()) {
                ArrayList<View> arrayList = dVar.a;
                return !dVar.b(arrayList.get(arrayList.size() - 1)).f819f;
            }
        } else if (dVar.i() > this.u.f()) {
            return !dVar.b(dVar.a.get(0)).f819f;
        }
        return false;
    }

    private int h(RecyclerView.z zVar) {
        if (e() == 0) {
            return 0;
        }
        return t.a(zVar, this.u, b(!this.N), a(!this.N), this, this.N);
    }

    private int i(RecyclerView.z zVar) {
        if (e() == 0) {
            return 0;
        }
        return t.a(zVar, this.u, b(!this.N), a(!this.N), this, this.N, this.A);
    }

    private int m(int i2) {
        if (e() != 0) {
            if ((i2 < I()) != this.A) {
                return -1;
            }
            return 1;
        } else if (this.A) {
            return 1;
        } else {
            return -1;
        }
    }

    private int n(int i2) {
        if (i2 == 1) {
            return (this.w != 1 && N()) ? 1 : -1;
        }
        if (i2 == 2) {
            return (this.w != 1 && N()) ? -1 : 1;
        }
        if (i2 != 17) {
            if (i2 != 33) {
                if (i2 != 66) {
                    return (i2 == 130 && this.w == 1) ? 1 : Integer.MIN_VALUE;
                }
                if (this.w == 0) {
                    return 1;
                }
                return Integer.MIN_VALUE;
            } else if (this.w == 1) {
                return -1;
            } else {
                return Integer.MIN_VALUE;
            }
        } else if (this.w == 0) {
            return -1;
        } else {
            return Integer.MIN_VALUE;
        }
    }

    private LazySpanLookup.FullSpanItem o(int i2) {
        LazySpanLookup.FullSpanItem fullSpanItem = new LazySpanLookup.FullSpanItem();
        fullSpanItem.f810g = new int[this.s];
        for (int i3 = 0; i3 < this.s; i3++) {
            fullSpanItem.f810g[i3] = i2 - this.t[i3].a(i2);
        }
        return fullSpanItem;
    }

    private LazySpanLookup.FullSpanItem p(int i2) {
        LazySpanLookup.FullSpanItem fullSpanItem = new LazySpanLookup.FullSpanItem();
        fullSpanItem.f810g = new int[this.s];
        for (int i3 = 0; i3 < this.s; i3++) {
            fullSpanItem.f810g[i3] = this.t[i3].b(i2) - i2;
        }
        return fullSpanItem;
    }

    private void q(View view) {
        for (int i2 = this.s - 1; i2 >= 0; i2--) {
            this.t[i2].c(view);
        }
    }

    private int r(int i2) {
        for (int e = e() - 1; e >= 0; e--) {
            int l = l(d(e));
            if (l >= 0 && l < i2) {
                return l;
            }
        }
        return 0;
    }

    private int s(int i2) {
        int a2 = this.t[0].a(i2);
        for (int i3 = 1; i3 < this.s; i3++) {
            int a3 = this.t[i3].a(i2);
            if (a3 > a2) {
                a2 = a3;
            }
        }
        return a2;
    }

    private int t(int i2) {
        int b2 = this.t[0].b(i2);
        for (int i3 = 1; i3 < this.s; i3++) {
            int b3 = this.t[i3].b(i2);
            if (b3 > b2) {
                b2 = b3;
            }
        }
        return b2;
    }

    private int u(int i2) {
        int a2 = this.t[0].a(i2);
        for (int i3 = 1; i3 < this.s; i3++) {
            int a3 = this.t[i3].a(i2);
            if (a3 < a2) {
                a2 = a3;
            }
        }
        return a2;
    }

    private boolean w(int i2) {
        if (this.w == 0) {
            if ((i2 == -1) != this.A) {
                return true;
            }
            return false;
        }
        if (((i2 == -1) == this.A) == N()) {
            return true;
        }
        return false;
    }

    private void x(int i2) {
        m mVar = this.y;
        mVar.e = i2;
        int i3 = 1;
        if (this.A != (i2 == -1)) {
            i3 = -1;
        }
        mVar.d = i3;
    }

    public boolean D() {
        return this.I == null;
    }

    /* access modifiers changed from: package-private */
    public boolean E() {
        int a2 = this.t[0].a(Integer.MIN_VALUE);
        for (int i2 = 1; i2 < this.s; i2++) {
            if (this.t[i2].a(Integer.MIN_VALUE) != a2) {
                return false;
            }
        }
        return true;
    }

    /* access modifiers changed from: package-private */
    public boolean F() {
        int b2 = this.t[0].b(Integer.MIN_VALUE);
        for (int i2 = 1; i2 < this.s; i2++) {
            if (this.t[i2].b(Integer.MIN_VALUE) != b2) {
                return false;
            }
        }
        return true;
    }

    /* access modifiers changed from: package-private */
    public boolean G() {
        int i2;
        int i3;
        if (e() == 0 || this.F == 0 || !u()) {
            return false;
        }
        if (this.A) {
            i3 = J();
            i2 = I();
        } else {
            i3 = I();
            i2 = J();
        }
        if (i3 == 0 && L() != null) {
            this.E.a();
            A();
            z();
            return true;
        } else if (!this.M) {
            return false;
        } else {
            int i4 = this.A ? -1 : 1;
            int i5 = i2 + 1;
            LazySpanLookup.FullSpanItem a2 = this.E.a(i3, i5, i4, true);
            if (a2 == null) {
                this.M = false;
                this.E.b(i5);
                return false;
            }
            LazySpanLookup.FullSpanItem a3 = this.E.a(i3, a2.e, i4 * -1, true);
            if (a3 == null) {
                this.E.b(a2.e);
            } else {
                this.E.b(a3.e + 1);
            }
            A();
            z();
            return true;
        }
    }

    /* access modifiers changed from: package-private */
    public int H() {
        View view;
        if (this.A) {
            view = a(true);
        } else {
            view = b(true);
        }
        if (view == null) {
            return -1;
        }
        return l(view);
    }

    /* access modifiers changed from: package-private */
    public int I() {
        if (e() == 0) {
            return 0;
        }
        return l(d(0));
    }

    /* access modifiers changed from: package-private */
    public int J() {
        int e = e();
        if (e == 0) {
            return 0;
        }
        return l(d(e - 1));
    }

    public int K() {
        return this.s;
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x0074, code lost:
        if (r10 == r11) goto L_0x0088;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x0086, code lost:
        if (r10 == r11) goto L_0x0088;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x008a, code lost:
        r10 = false;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public android.view.View L() {
        /*
            r12 = this;
            int r0 = r12.e()
            r1 = 1
            int r0 = r0 - r1
            java.util.BitSet r2 = new java.util.BitSet
            int r3 = r12.s
            r2.<init>(r3)
            int r3 = r12.s
            r4 = 0
            r2.set(r4, r3, r1)
            int r3 = r12.w
            r5 = -1
            if (r3 != r1) goto L_0x0020
            boolean r3 = r12.N()
            if (r3 == 0) goto L_0x0020
            r3 = 1
            goto L_0x0021
        L_0x0020:
            r3 = -1
        L_0x0021:
            boolean r6 = r12.A
            if (r6 == 0) goto L_0x0027
            r6 = -1
            goto L_0x002b
        L_0x0027:
            int r0 = r0 + 1
            r6 = r0
            r0 = 0
        L_0x002b:
            if (r0 >= r6) goto L_0x002e
            r5 = 1
        L_0x002e:
            if (r0 == r6) goto L_0x00ab
            android.view.View r7 = r12.d((int) r0)
            android.view.ViewGroup$LayoutParams r8 = r7.getLayoutParams()
            androidx.recyclerview.widget.StaggeredGridLayoutManager$c r8 = (androidx.recyclerview.widget.StaggeredGridLayoutManager.c) r8
            androidx.recyclerview.widget.StaggeredGridLayoutManager$d r9 = r8.e
            int r9 = r9.e
            boolean r9 = r2.get(r9)
            if (r9 == 0) goto L_0x0054
            androidx.recyclerview.widget.StaggeredGridLayoutManager$d r9 = r8.e
            boolean r9 = r12.a((androidx.recyclerview.widget.StaggeredGridLayoutManager.d) r9)
            if (r9 == 0) goto L_0x004d
            return r7
        L_0x004d:
            androidx.recyclerview.widget.StaggeredGridLayoutManager$d r9 = r8.e
            int r9 = r9.e
            r2.clear(r9)
        L_0x0054:
            boolean r9 = r8.f819f
            if (r9 == 0) goto L_0x0059
            goto L_0x00a9
        L_0x0059:
            int r9 = r0 + r5
            if (r9 == r6) goto L_0x00a9
            android.view.View r9 = r12.d((int) r9)
            boolean r10 = r12.A
            if (r10 == 0) goto L_0x0077
            androidx.recyclerview.widget.q r10 = r12.u
            int r10 = r10.a((android.view.View) r7)
            androidx.recyclerview.widget.q r11 = r12.u
            int r11 = r11.a((android.view.View) r9)
            if (r10 >= r11) goto L_0x0074
            return r7
        L_0x0074:
            if (r10 != r11) goto L_0x008a
            goto L_0x0088
        L_0x0077:
            androidx.recyclerview.widget.q r10 = r12.u
            int r10 = r10.d(r7)
            androidx.recyclerview.widget.q r11 = r12.u
            int r11 = r11.d(r9)
            if (r10 <= r11) goto L_0x0086
            return r7
        L_0x0086:
            if (r10 != r11) goto L_0x008a
        L_0x0088:
            r10 = 1
            goto L_0x008b
        L_0x008a:
            r10 = 0
        L_0x008b:
            if (r10 == 0) goto L_0x00a9
            android.view.ViewGroup$LayoutParams r9 = r9.getLayoutParams()
            androidx.recyclerview.widget.StaggeredGridLayoutManager$c r9 = (androidx.recyclerview.widget.StaggeredGridLayoutManager.c) r9
            androidx.recyclerview.widget.StaggeredGridLayoutManager$d r8 = r8.e
            int r8 = r8.e
            androidx.recyclerview.widget.StaggeredGridLayoutManager$d r9 = r9.e
            int r9 = r9.e
            int r8 = r8 - r9
            if (r8 >= 0) goto L_0x00a0
            r8 = 1
            goto L_0x00a1
        L_0x00a0:
            r8 = 0
        L_0x00a1:
            if (r3 >= 0) goto L_0x00a5
            r9 = 1
            goto L_0x00a6
        L_0x00a5:
            r9 = 0
        L_0x00a6:
            if (r8 == r9) goto L_0x00a9
            return r7
        L_0x00a9:
            int r0 = r0 + r5
            goto L_0x002e
        L_0x00ab:
            r0 = 0
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.recyclerview.widget.StaggeredGridLayoutManager.L():android.view.View");
    }

    public void M() {
        this.E.a();
        z();
    }

    /* access modifiers changed from: package-private */
    public boolean N() {
        return k() == 1;
    }

    public void b(RecyclerView recyclerView, RecyclerView.v vVar) {
        super.b(recyclerView, vVar);
        a(this.P);
        for (int i2 = 0; i2 < this.s; i2++) {
            this.t[i2].c();
        }
        recyclerView.requestLayout();
    }

    public void c(boolean z2) {
        a((String) null);
        SavedState savedState = this.I;
        if (!(savedState == null || savedState.l == z2)) {
            savedState.l = z2;
        }
        this.z = z2;
        z();
    }

    public int d(RecyclerView.z zVar) {
        return h(zVar);
    }

    public void e(RecyclerView.v vVar, RecyclerView.z zVar) {
        c(vVar, zVar, true);
    }

    public int f(RecyclerView.z zVar) {
        return j(zVar);
    }

    public void g(int i2) {
        if (i2 == 0) {
            G();
        }
    }

    public void j(int i2) {
        if (i2 == 0 || i2 == 1) {
            a((String) null);
            if (i2 != this.w) {
                this.w = i2;
                q qVar = this.u;
                this.u = this.v;
                this.v = qVar;
                z();
                return;
            }
            return;
        }
        throw new IllegalArgumentException("invalid orientation.");
    }

    public void k(int i2) {
        a((String) null);
        if (i2 != this.s) {
            M();
            this.s = i2;
            this.B = new BitSet(this.s);
            this.t = new d[this.s];
            for (int i3 = 0; i3 < this.s; i3++) {
                this.t[i3] = new d(i3);
            }
            z();
        }
    }

    /* access modifiers changed from: package-private */
    public void l(int i2) {
        this.x = i2 / this.s;
        this.J = View.MeasureSpec.makeMeasureSpec(i2, this.v.d());
    }

    public boolean v() {
        return this.F != 0;
    }

    public Parcelable y() {
        int i2;
        int i3;
        int i4;
        int[] iArr;
        if (this.I != null) {
            return new SavedState(this.I);
        }
        SavedState savedState = new SavedState();
        savedState.l = this.z;
        savedState.m = this.G;
        savedState.n = this.H;
        LazySpanLookup lazySpanLookup = this.E;
        if (lazySpanLookup == null || (iArr = lazySpanLookup.a) == null) {
            savedState.f815i = 0;
        } else {
            savedState.f816j = iArr;
            savedState.f815i = iArr.length;
            savedState.k = lazySpanLookup.b;
        }
        if (e() > 0) {
            if (this.G) {
                i2 = J();
            } else {
                i2 = I();
            }
            savedState.e = i2;
            savedState.f812f = H();
            int i5 = this.s;
            savedState.f813g = i5;
            savedState.f814h = new int[i5];
            for (int i6 = 0; i6 < this.s; i6++) {
                if (this.G) {
                    i3 = this.t[i6].a(Integer.MIN_VALUE);
                    if (i3 != Integer.MIN_VALUE) {
                        i4 = this.u.b();
                    } else {
                        savedState.f814h[i6] = i3;
                    }
                } else {
                    i3 = this.t[i6].b(Integer.MIN_VALUE);
                    if (i3 != Integer.MIN_VALUE) {
                        i4 = this.u.f();
                    } else {
                        savedState.f814h[i6] = i3;
                    }
                }
                i3 -= i4;
                savedState.f814h[i6] = i3;
            }
        } else {
            savedState.e = -1;
            savedState.f812f = -1;
            savedState.f813g = 0;
        }
        return savedState;
    }

    static class LazySpanLookup {
        int[] a;
        List<FullSpanItem> b;

        LazySpanLookup() {
        }

        private void c(int i2, int i3) {
            List<FullSpanItem> list = this.b;
            if (list != null) {
                for (int size = list.size() - 1; size >= 0; size--) {
                    FullSpanItem fullSpanItem = this.b.get(size);
                    int i4 = fullSpanItem.e;
                    if (i4 >= i2) {
                        fullSpanItem.e = i4 + i3;
                    }
                }
            }
        }

        private int g(int i2) {
            if (this.b == null) {
                return -1;
            }
            FullSpanItem c = c(i2);
            if (c != null) {
                this.b.remove(c);
            }
            int size = this.b.size();
            int i3 = 0;
            while (true) {
                if (i3 >= size) {
                    i3 = -1;
                    break;
                } else if (this.b.get(i3).e >= i2) {
                    break;
                } else {
                    i3++;
                }
            }
            if (i3 == -1) {
                return -1;
            }
            this.b.remove(i3);
            return this.b.get(i3).e;
        }

        /* access modifiers changed from: package-private */
        public void a(int i2, d dVar) {
            a(i2);
            this.a[i2] = dVar.e;
        }

        /* access modifiers changed from: package-private */
        public int b(int i2) {
            List<FullSpanItem> list = this.b;
            if (list != null) {
                for (int size = list.size() - 1; size >= 0; size--) {
                    if (this.b.get(size).e >= i2) {
                        this.b.remove(size);
                    }
                }
            }
            return e(i2);
        }

        /* access modifiers changed from: package-private */
        public int d(int i2) {
            int[] iArr = this.a;
            if (iArr == null || i2 >= iArr.length) {
                return -1;
            }
            return iArr[i2];
        }

        /* access modifiers changed from: package-private */
        public int e(int i2) {
            int[] iArr = this.a;
            if (iArr == null || i2 >= iArr.length) {
                return -1;
            }
            int g2 = g(i2);
            if (g2 == -1) {
                int[] iArr2 = this.a;
                Arrays.fill(iArr2, i2, iArr2.length, -1);
                return this.a.length;
            }
            int i3 = g2 + 1;
            Arrays.fill(this.a, i2, i3, -1);
            return i3;
        }

        /* access modifiers changed from: package-private */
        public int f(int i2) {
            int length = this.a.length;
            while (length <= i2) {
                length *= 2;
            }
            return length;
        }

        private void d(int i2, int i3) {
            List<FullSpanItem> list = this.b;
            if (list != null) {
                int i4 = i2 + i3;
                for (int size = list.size() - 1; size >= 0; size--) {
                    FullSpanItem fullSpanItem = this.b.get(size);
                    int i5 = fullSpanItem.e;
                    if (i5 >= i2) {
                        if (i5 < i4) {
                            this.b.remove(size);
                        } else {
                            fullSpanItem.e = i5 - i3;
                        }
                    }
                }
            }
        }

        /* access modifiers changed from: package-private */
        public void a(int i2) {
            int[] iArr = this.a;
            if (iArr == null) {
                int[] iArr2 = new int[(Math.max(i2, 10) + 1)];
                this.a = iArr2;
                Arrays.fill(iArr2, -1);
            } else if (i2 >= iArr.length) {
                int[] iArr3 = new int[f(i2)];
                this.a = iArr3;
                System.arraycopy(iArr, 0, iArr3, 0, iArr.length);
                int[] iArr4 = this.a;
                Arrays.fill(iArr4, iArr.length, iArr4.length, -1);
            }
        }

        public FullSpanItem c(int i2) {
            List<FullSpanItem> list = this.b;
            if (list == null) {
                return null;
            }
            for (int size = list.size() - 1; size >= 0; size--) {
                FullSpanItem fullSpanItem = this.b.get(size);
                if (fullSpanItem.e == i2) {
                    return fullSpanItem;
                }
            }
            return null;
        }

        @SuppressLint({"BanParcelableUsage"})
        static class FullSpanItem implements Parcelable {
            public static final Parcelable.Creator<FullSpanItem> CREATOR = new a();
            int e;

            /* renamed from: f  reason: collision with root package name */
            int f809f;

            /* renamed from: g  reason: collision with root package name */
            int[] f810g;

            /* renamed from: h  reason: collision with root package name */
            boolean f811h;

            static class a implements Parcelable.Creator<FullSpanItem> {
                a() {
                }

                public FullSpanItem createFromParcel(Parcel parcel) {
                    return new FullSpanItem(parcel);
                }

                public FullSpanItem[] newArray(int i2) {
                    return new FullSpanItem[i2];
                }
            }

            FullSpanItem(Parcel parcel) {
                this.e = parcel.readInt();
                this.f809f = parcel.readInt();
                this.f811h = parcel.readInt() != 1 ? false : true;
                int readInt = parcel.readInt();
                if (readInt > 0) {
                    int[] iArr = new int[readInt];
                    this.f810g = iArr;
                    parcel.readIntArray(iArr);
                }
            }

            /* access modifiers changed from: package-private */
            public int a(int i2) {
                int[] iArr = this.f810g;
                if (iArr == null) {
                    return 0;
                }
                return iArr[i2];
            }

            public int describeContents() {
                return 0;
            }

            public String toString() {
                return "FullSpanItem{mPosition=" + this.e + ", mGapDir=" + this.f809f + ", mHasUnwantedGapAfter=" + this.f811h + ", mGapPerSpan=" + Arrays.toString(this.f810g) + '}';
            }

            public void writeToParcel(Parcel parcel, int i2) {
                parcel.writeInt(this.e);
                parcel.writeInt(this.f809f);
                parcel.writeInt(this.f811h ? 1 : 0);
                int[] iArr = this.f810g;
                if (iArr == null || iArr.length <= 0) {
                    parcel.writeInt(0);
                    return;
                }
                parcel.writeInt(iArr.length);
                parcel.writeIntArray(this.f810g);
            }

            FullSpanItem() {
            }
        }

        /* access modifiers changed from: package-private */
        public void b(int i2, int i3) {
            int[] iArr = this.a;
            if (iArr != null && i2 < iArr.length) {
                int i4 = i2 + i3;
                a(i4);
                int[] iArr2 = this.a;
                System.arraycopy(iArr2, i4, iArr2, i2, (iArr2.length - i2) - i3);
                int[] iArr3 = this.a;
                Arrays.fill(iArr3, iArr3.length - i3, iArr3.length, -1);
                d(i2, i3);
            }
        }

        /* access modifiers changed from: package-private */
        public void a() {
            int[] iArr = this.a;
            if (iArr != null) {
                Arrays.fill(iArr, -1);
            }
            this.b = null;
        }

        /* access modifiers changed from: package-private */
        public void a(int i2, int i3) {
            int[] iArr = this.a;
            if (iArr != null && i2 < iArr.length) {
                int i4 = i2 + i3;
                a(i4);
                int[] iArr2 = this.a;
                System.arraycopy(iArr2, i2, iArr2, i4, (iArr2.length - i2) - i3);
                Arrays.fill(this.a, i2, i4, -1);
                c(i2, i3);
            }
        }

        public void a(FullSpanItem fullSpanItem) {
            if (this.b == null) {
                this.b = new ArrayList();
            }
            int size = this.b.size();
            for (int i2 = 0; i2 < size; i2++) {
                FullSpanItem fullSpanItem2 = this.b.get(i2);
                if (fullSpanItem2.e == fullSpanItem.e) {
                    this.b.remove(i2);
                }
                if (fullSpanItem2.e >= fullSpanItem.e) {
                    this.b.add(i2, fullSpanItem);
                    return;
                }
            }
            this.b.add(fullSpanItem);
        }

        public FullSpanItem a(int i2, int i3, int i4, boolean z) {
            List<FullSpanItem> list = this.b;
            if (list == null) {
                return null;
            }
            int size = list.size();
            for (int i5 = 0; i5 < size; i5++) {
                FullSpanItem fullSpanItem = this.b.get(i5);
                int i6 = fullSpanItem.e;
                if (i6 >= i3) {
                    return null;
                }
                if (i6 >= i2 && (i4 == 0 || fullSpanItem.f809f == i4 || (z && fullSpanItem.f811h))) {
                    return fullSpanItem;
                }
            }
            return null;
        }
    }

    private int v(int i2) {
        int b2 = this.t[0].b(i2);
        for (int i3 = 1; i3 < this.s; i3++) {
            int b3 = this.t[i3].b(i2);
            if (b3 < b2) {
                b2 = b3;
            }
        }
        return b2;
    }

    public void d(RecyclerView recyclerView) {
        this.E.a();
        z();
    }

    public int e(RecyclerView.z zVar) {
        return i(zVar);
    }

    public void f(int i2) {
        super.f(i2);
        for (int i3 = 0; i3 < this.s; i3++) {
            this.t[i3].c(i2);
        }
    }

    public void g(RecyclerView.z zVar) {
        super.g(zVar);
        this.C = -1;
        this.D = Integer.MIN_VALUE;
        this.I = null;
        this.L.b();
    }

    private int q(int i2) {
        int e = e();
        for (int i3 = 0; i3 < e; i3++) {
            int l = l(d(i3));
            if (l >= 0 && l < i2) {
                return l;
            }
        }
        return 0;
    }

    public void e(int i2) {
        super.e(i2);
        for (int i3 = 0; i3 < this.s; i3++) {
            this.t[i3].c(i2);
        }
    }

    class b {
        int a;
        int b;
        boolean c;
        boolean d;
        boolean e;

        /* renamed from: f  reason: collision with root package name */
        int[] f817f;

        b() {
            b();
        }

        /* access modifiers changed from: package-private */
        public void a(d[] dVarArr) {
            int length = dVarArr.length;
            int[] iArr = this.f817f;
            if (iArr == null || iArr.length < length) {
                this.f817f = new int[StaggeredGridLayoutManager.this.t.length];
            }
            for (int i2 = 0; i2 < length; i2++) {
                this.f817f[i2] = dVarArr[i2].b(Integer.MIN_VALUE);
            }
        }

        /* access modifiers changed from: package-private */
        public void b() {
            this.a = -1;
            this.b = Integer.MIN_VALUE;
            this.c = false;
            this.d = false;
            this.e = false;
            int[] iArr = this.f817f;
            if (iArr != null) {
                Arrays.fill(iArr, -1);
            }
        }

        /* access modifiers changed from: package-private */
        public void a() {
            int i2;
            if (this.c) {
                i2 = StaggeredGridLayoutManager.this.u.b();
            } else {
                i2 = StaggeredGridLayoutManager.this.u.f();
            }
            this.b = i2;
        }

        /* access modifiers changed from: package-private */
        public void a(int i2) {
            if (this.c) {
                this.b = StaggeredGridLayoutManager.this.u.b() - i2;
            } else {
                this.b = StaggeredGridLayoutManager.this.u.f() + i2;
            }
        }
    }

    private void p(View view) {
        for (int i2 = this.s - 1; i2 >= 0; i2--) {
            this.t[i2].a(view);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:83:0x0157, code lost:
        if (G() != false) goto L_0x015b;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void c(androidx.recyclerview.widget.RecyclerView.v r9, androidx.recyclerview.widget.RecyclerView.z r10, boolean r11) {
        /*
            r8 = this;
            androidx.recyclerview.widget.StaggeredGridLayoutManager$b r0 = r8.L
            androidx.recyclerview.widget.StaggeredGridLayoutManager$SavedState r1 = r8.I
            r2 = -1
            if (r1 != 0) goto L_0x000b
            int r1 = r8.C
            if (r1 == r2) goto L_0x0018
        L_0x000b:
            int r1 = r10.a()
            if (r1 != 0) goto L_0x0018
            r8.b((androidx.recyclerview.widget.RecyclerView.v) r9)
            r0.b()
            return
        L_0x0018:
            boolean r1 = r0.e
            r3 = 0
            r4 = 1
            if (r1 == 0) goto L_0x0029
            int r1 = r8.C
            if (r1 != r2) goto L_0x0029
            androidx.recyclerview.widget.StaggeredGridLayoutManager$SavedState r1 = r8.I
            if (r1 == 0) goto L_0x0027
            goto L_0x0029
        L_0x0027:
            r1 = 0
            goto L_0x002a
        L_0x0029:
            r1 = 1
        L_0x002a:
            if (r1 == 0) goto L_0x0043
            r0.b()
            androidx.recyclerview.widget.StaggeredGridLayoutManager$SavedState r5 = r8.I
            if (r5 == 0) goto L_0x0037
            r8.a((androidx.recyclerview.widget.StaggeredGridLayoutManager.b) r0)
            goto L_0x003e
        L_0x0037:
            r8.Q()
            boolean r5 = r8.A
            r0.c = r5
        L_0x003e:
            r8.b((androidx.recyclerview.widget.RecyclerView.z) r10, (androidx.recyclerview.widget.StaggeredGridLayoutManager.b) r0)
            r0.e = r4
        L_0x0043:
            androidx.recyclerview.widget.StaggeredGridLayoutManager$SavedState r5 = r8.I
            if (r5 != 0) goto L_0x0060
            int r5 = r8.C
            if (r5 != r2) goto L_0x0060
            boolean r5 = r0.c
            boolean r6 = r8.G
            if (r5 != r6) goto L_0x0059
            boolean r5 = r8.N()
            boolean r6 = r8.H
            if (r5 == r6) goto L_0x0060
        L_0x0059:
            androidx.recyclerview.widget.StaggeredGridLayoutManager$LazySpanLookup r5 = r8.E
            r5.a()
            r0.d = r4
        L_0x0060:
            int r5 = r8.e()
            if (r5 <= 0) goto L_0x00c9
            androidx.recyclerview.widget.StaggeredGridLayoutManager$SavedState r5 = r8.I
            if (r5 == 0) goto L_0x006e
            int r5 = r5.f813g
            if (r5 >= r4) goto L_0x00c9
        L_0x006e:
            boolean r5 = r0.d
            if (r5 == 0) goto L_0x008e
            r1 = 0
        L_0x0073:
            int r5 = r8.s
            if (r1 >= r5) goto L_0x00c9
            androidx.recyclerview.widget.StaggeredGridLayoutManager$d[] r5 = r8.t
            r5 = r5[r1]
            r5.c()
            int r5 = r0.b
            r6 = -2147483648(0xffffffff80000000, float:-0.0)
            if (r5 == r6) goto L_0x008b
            androidx.recyclerview.widget.StaggeredGridLayoutManager$d[] r6 = r8.t
            r6 = r6[r1]
            r6.d(r5)
        L_0x008b:
            int r1 = r1 + 1
            goto L_0x0073
        L_0x008e:
            if (r1 != 0) goto L_0x00af
            androidx.recyclerview.widget.StaggeredGridLayoutManager$b r1 = r8.L
            int[] r1 = r1.f817f
            if (r1 != 0) goto L_0x0097
            goto L_0x00af
        L_0x0097:
            r1 = 0
        L_0x0098:
            int r5 = r8.s
            if (r1 >= r5) goto L_0x00c9
            androidx.recyclerview.widget.StaggeredGridLayoutManager$d[] r5 = r8.t
            r5 = r5[r1]
            r5.c()
            androidx.recyclerview.widget.StaggeredGridLayoutManager$b r6 = r8.L
            int[] r6 = r6.f817f
            r6 = r6[r1]
            r5.d(r6)
            int r1 = r1 + 1
            goto L_0x0098
        L_0x00af:
            r1 = 0
        L_0x00b0:
            int r5 = r8.s
            if (r1 >= r5) goto L_0x00c2
            androidx.recyclerview.widget.StaggeredGridLayoutManager$d[] r5 = r8.t
            r5 = r5[r1]
            boolean r6 = r8.A
            int r7 = r0.b
            r5.a((boolean) r6, (int) r7)
            int r1 = r1 + 1
            goto L_0x00b0
        L_0x00c2:
            androidx.recyclerview.widget.StaggeredGridLayoutManager$b r1 = r8.L
            androidx.recyclerview.widget.StaggeredGridLayoutManager$d[] r5 = r8.t
            r1.a((androidx.recyclerview.widget.StaggeredGridLayoutManager.d[]) r5)
        L_0x00c9:
            r8.a((androidx.recyclerview.widget.RecyclerView.v) r9)
            androidx.recyclerview.widget.m r1 = r8.y
            r1.a = r3
            r8.M = r3
            androidx.recyclerview.widget.q r1 = r8.v
            int r1 = r1.g()
            r8.l(r1)
            int r1 = r0.a
            r8.b((int) r1, (androidx.recyclerview.widget.RecyclerView.z) r10)
            boolean r1 = r0.c
            if (r1 == 0) goto L_0x00fc
            r8.x(r2)
            androidx.recyclerview.widget.m r1 = r8.y
            r8.a((androidx.recyclerview.widget.RecyclerView.v) r9, (androidx.recyclerview.widget.m) r1, (androidx.recyclerview.widget.RecyclerView.z) r10)
            r8.x(r4)
            androidx.recyclerview.widget.m r1 = r8.y
            int r2 = r0.a
            int r5 = r1.d
            int r2 = r2 + r5
            r1.c = r2
            r8.a((androidx.recyclerview.widget.RecyclerView.v) r9, (androidx.recyclerview.widget.m) r1, (androidx.recyclerview.widget.RecyclerView.z) r10)
            goto L_0x0113
        L_0x00fc:
            r8.x(r4)
            androidx.recyclerview.widget.m r1 = r8.y
            r8.a((androidx.recyclerview.widget.RecyclerView.v) r9, (androidx.recyclerview.widget.m) r1, (androidx.recyclerview.widget.RecyclerView.z) r10)
            r8.x(r2)
            androidx.recyclerview.widget.m r1 = r8.y
            int r2 = r0.a
            int r5 = r1.d
            int r2 = r2 + r5
            r1.c = r2
            r8.a((androidx.recyclerview.widget.RecyclerView.v) r9, (androidx.recyclerview.widget.m) r1, (androidx.recyclerview.widget.RecyclerView.z) r10)
        L_0x0113:
            r8.P()
            int r1 = r8.e()
            if (r1 <= 0) goto L_0x012d
            boolean r1 = r8.A
            if (r1 == 0) goto L_0x0127
            r8.a((androidx.recyclerview.widget.RecyclerView.v) r9, (androidx.recyclerview.widget.RecyclerView.z) r10, (boolean) r4)
            r8.b((androidx.recyclerview.widget.RecyclerView.v) r9, (androidx.recyclerview.widget.RecyclerView.z) r10, (boolean) r3)
            goto L_0x012d
        L_0x0127:
            r8.b((androidx.recyclerview.widget.RecyclerView.v) r9, (androidx.recyclerview.widget.RecyclerView.z) r10, (boolean) r4)
            r8.a((androidx.recyclerview.widget.RecyclerView.v) r9, (androidx.recyclerview.widget.RecyclerView.z) r10, (boolean) r3)
        L_0x012d:
            if (r11 == 0) goto L_0x015a
            boolean r11 = r10.d()
            if (r11 != 0) goto L_0x015a
            int r11 = r8.F
            if (r11 == 0) goto L_0x014b
            int r11 = r8.e()
            if (r11 <= 0) goto L_0x014b
            boolean r11 = r8.M
            if (r11 != 0) goto L_0x0149
            android.view.View r11 = r8.L()
            if (r11 == 0) goto L_0x014b
        L_0x0149:
            r11 = 1
            goto L_0x014c
        L_0x014b:
            r11 = 0
        L_0x014c:
            if (r11 == 0) goto L_0x015a
            java.lang.Runnable r11 = r8.P
            r8.a((java.lang.Runnable) r11)
            boolean r11 = r8.G()
            if (r11 == 0) goto L_0x015a
            goto L_0x015b
        L_0x015a:
            r4 = 0
        L_0x015b:
            boolean r11 = r10.d()
            if (r11 == 0) goto L_0x0166
            androidx.recyclerview.widget.StaggeredGridLayoutManager$b r11 = r8.L
            r11.b()
        L_0x0166:
            boolean r11 = r0.c
            r8.G = r11
            boolean r11 = r8.N()
            r8.H = r11
            if (r4 == 0) goto L_0x017a
            androidx.recyclerview.widget.StaggeredGridLayoutManager$b r11 = r8.L
            r11.b()
            r8.c((androidx.recyclerview.widget.RecyclerView.v) r9, (androidx.recyclerview.widget.RecyclerView.z) r10, (boolean) r3)
        L_0x017a:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.recyclerview.widget.StaggeredGridLayoutManager.c(androidx.recyclerview.widget.RecyclerView$v, androidx.recyclerview.widget.RecyclerView$z, boolean):void");
    }

    private void e(int i2, int i3) {
        for (int i4 = 0; i4 < this.s; i4++) {
            if (!this.t[i4].a.isEmpty()) {
                a(this.t[i4], i2, i3);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void b(RecyclerView.z zVar, b bVar) {
        if (!a(zVar, bVar) && !c(zVar, bVar)) {
            bVar.a();
            bVar.a = 0;
        }
    }

    public void i(int i2) {
        SavedState savedState = this.I;
        if (!(savedState == null || savedState.e == i2)) {
            savedState.a();
        }
        this.C = i2;
        this.D = Integer.MIN_VALUE;
        z();
    }

    private int j(RecyclerView.z zVar) {
        if (e() == 0) {
            return 0;
        }
        return t.b(zVar, this.u, b(!this.N), a(!this.N), this, this.N);
    }

    public void a(String str) {
        if (this.I == null) {
            super.a(str);
        }
    }

    public int b(RecyclerView.z zVar) {
        return i(zVar);
    }

    public int b(RecyclerView.v vVar, RecyclerView.z zVar) {
        if (this.w == 0) {
            return this.s;
        }
        return super.b(vVar, zVar);
    }

    public void a(Rect rect, int i2, int i3) {
        int i4;
        int i5;
        int o = o() + p();
        int q = q() + n();
        if (this.w == 1) {
            i5 = RecyclerView.o.a(i3, rect.height() + q, l());
            i4 = RecyclerView.o.a(i2, (this.x * this.s) + o, m());
        } else {
            i4 = RecyclerView.o.a(i2, rect.width() + o, m());
            i5 = RecyclerView.o.a(i3, (this.x * this.s) + q, l());
        }
        c(i4, i5);
    }

    /* access modifiers changed from: package-private */
    public View b(boolean z2) {
        int f2 = this.u.f();
        int b2 = this.u.b();
        int e = e();
        View view = null;
        for (int i2 = 0; i2 < e; i2++) {
            View d2 = d(i2);
            int d3 = this.u.d(d2);
            if (this.u.a(d2) > f2 && d3 < b2) {
                if (d3 >= f2 || !z2) {
                    return d2;
                }
                if (view == null) {
                    view = d2;
                }
            }
        }
        return view;
    }

    private void b(RecyclerView.v vVar, RecyclerView.z zVar, boolean z2) {
        int f2;
        int v2 = v(Integer.MAX_VALUE);
        if (v2 != Integer.MAX_VALUE && (f2 = v2 - this.u.f()) > 0) {
            int c2 = f2 - c(f2, vVar, zVar);
            if (z2 && c2 > 0) {
                this.u.a(-c2);
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:15:0x0036  */
    /* JADX WARNING: Removed duplicated region for block: B:16:0x004d  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void b(int r5, androidx.recyclerview.widget.RecyclerView.z r6) {
        /*
            r4 = this;
            androidx.recyclerview.widget.m r0 = r4.y
            r1 = 0
            r0.b = r1
            r0.c = r5
            boolean r0 = r4.x()
            r2 = 1
            if (r0 == 0) goto L_0x002e
            int r6 = r6.b()
            r0 = -1
            if (r6 == r0) goto L_0x002e
            boolean r0 = r4.A
            if (r6 >= r5) goto L_0x001b
            r5 = 1
            goto L_0x001c
        L_0x001b:
            r5 = 0
        L_0x001c:
            if (r0 != r5) goto L_0x0025
            androidx.recyclerview.widget.q r5 = r4.u
            int r5 = r5.g()
            goto L_0x002f
        L_0x0025:
            androidx.recyclerview.widget.q r5 = r4.u
            int r5 = r5.g()
            r6 = r5
            r5 = 0
            goto L_0x0030
        L_0x002e:
            r5 = 0
        L_0x002f:
            r6 = 0
        L_0x0030:
            boolean r0 = r4.f()
            if (r0 == 0) goto L_0x004d
            androidx.recyclerview.widget.m r0 = r4.y
            androidx.recyclerview.widget.q r3 = r4.u
            int r3 = r3.f()
            int r3 = r3 - r6
            r0.f849f = r3
            androidx.recyclerview.widget.m r6 = r4.y
            androidx.recyclerview.widget.q r0 = r4.u
            int r0 = r0.b()
            int r0 = r0 + r5
            r6.f850g = r0
            goto L_0x005d
        L_0x004d:
            androidx.recyclerview.widget.m r0 = r4.y
            androidx.recyclerview.widget.q r3 = r4.u
            int r3 = r3.a()
            int r3 = r3 + r5
            r0.f850g = r3
            androidx.recyclerview.widget.m r5 = r4.y
            int r6 = -r6
            r5.f849f = r6
        L_0x005d:
            androidx.recyclerview.widget.m r5 = r4.y
            r5.f851h = r1
            r5.a = r2
            androidx.recyclerview.widget.q r6 = r4.u
            int r6 = r6.d()
            if (r6 != 0) goto L_0x0074
            androidx.recyclerview.widget.q r6 = r4.u
            int r6 = r6.a()
            if (r6 != 0) goto L_0x0074
            r1 = 1
        L_0x0074:
            r5.f852i = r1
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.recyclerview.widget.StaggeredGridLayoutManager.b(int, androidx.recyclerview.widget.RecyclerView$z):void");
    }

    private void a(b bVar) {
        int i2;
        SavedState savedState = this.I;
        int i3 = savedState.f813g;
        if (i3 > 0) {
            if (i3 == this.s) {
                for (int i4 = 0; i4 < this.s; i4++) {
                    this.t[i4].c();
                    SavedState savedState2 = this.I;
                    int i5 = savedState2.f814h[i4];
                    if (i5 != Integer.MIN_VALUE) {
                        if (savedState2.m) {
                            i2 = this.u.b();
                        } else {
                            i2 = this.u.f();
                        }
                        i5 += i2;
                    }
                    this.t[i4].d(i5);
                }
            } else {
                savedState.b();
                SavedState savedState3 = this.I;
                savedState3.e = savedState3.f812f;
            }
        }
        SavedState savedState4 = this.I;
        this.H = savedState4.n;
        c(savedState4.l);
        Q();
        SavedState savedState5 = this.I;
        int i6 = savedState5.e;
        if (i6 != -1) {
            this.C = i6;
            bVar.c = savedState5.m;
        } else {
            bVar.c = this.A;
        }
        SavedState savedState6 = this.I;
        if (savedState6.f815i > 1) {
            LazySpanLookup lazySpanLookup = this.E;
            lazySpanLookup.a = savedState6.f816j;
            lazySpanLookup.b = savedState6.k;
        }
    }

    public void b(RecyclerView recyclerView, int i2, int i3) {
        b(i2, i3, 2);
    }

    /* JADX WARNING: Removed duplicated region for block: B:13:0x0025  */
    /* JADX WARNING: Removed duplicated region for block: B:18:0x003c  */
    /* JADX WARNING: Removed duplicated region for block: B:20:0x0043 A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x0044  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void b(int r7, int r8, int r9) {
        /*
            r6 = this;
            boolean r0 = r6.A
            if (r0 == 0) goto L_0x0009
            int r0 = r6.J()
            goto L_0x000d
        L_0x0009:
            int r0 = r6.I()
        L_0x000d:
            r1 = 8
            if (r9 != r1) goto L_0x001a
            if (r7 >= r8) goto L_0x0016
            int r2 = r8 + 1
            goto L_0x001c
        L_0x0016:
            int r2 = r7 + 1
            r3 = r8
            goto L_0x001d
        L_0x001a:
            int r2 = r7 + r8
        L_0x001c:
            r3 = r7
        L_0x001d:
            androidx.recyclerview.widget.StaggeredGridLayoutManager$LazySpanLookup r4 = r6.E
            r4.e(r3)
            r4 = 1
            if (r9 == r4) goto L_0x003c
            r5 = 2
            if (r9 == r5) goto L_0x0036
            if (r9 == r1) goto L_0x002b
            goto L_0x0041
        L_0x002b:
            androidx.recyclerview.widget.StaggeredGridLayoutManager$LazySpanLookup r9 = r6.E
            r9.b(r7, r4)
            androidx.recyclerview.widget.StaggeredGridLayoutManager$LazySpanLookup r7 = r6.E
            r7.a((int) r8, (int) r4)
            goto L_0x0041
        L_0x0036:
            androidx.recyclerview.widget.StaggeredGridLayoutManager$LazySpanLookup r9 = r6.E
            r9.b(r7, r8)
            goto L_0x0041
        L_0x003c:
            androidx.recyclerview.widget.StaggeredGridLayoutManager$LazySpanLookup r9 = r6.E
            r9.a((int) r7, (int) r8)
        L_0x0041:
            if (r2 > r0) goto L_0x0044
            return
        L_0x0044:
            boolean r7 = r6.A
            if (r7 == 0) goto L_0x004d
            int r7 = r6.I()
            goto L_0x0051
        L_0x004d:
            int r7 = r6.J()
        L_0x0051:
            if (r3 > r7) goto L_0x0056
            r6.z()
        L_0x0056:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.recyclerview.widget.StaggeredGridLayoutManager.b(int, int, int):void");
    }

    /* access modifiers changed from: package-private */
    public boolean a(RecyclerView.z zVar, b bVar) {
        int i2;
        int i3;
        int i4;
        boolean z2 = false;
        if (!zVar.d() && (i2 = this.C) != -1) {
            if (i2 < 0 || i2 >= zVar.a()) {
                this.C = -1;
                this.D = Integer.MIN_VALUE;
            } else {
                SavedState savedState = this.I;
                if (savedState == null || savedState.e == -1 || savedState.f813g < 1) {
                    View c2 = c(this.C);
                    if (c2 != null) {
                        if (this.A) {
                            i3 = J();
                        } else {
                            i3 = I();
                        }
                        bVar.a = i3;
                        if (this.D != Integer.MIN_VALUE) {
                            if (bVar.c) {
                                bVar.b = (this.u.b() - this.D) - this.u.a(c2);
                            } else {
                                bVar.b = (this.u.f() + this.D) - this.u.d(c2);
                            }
                            return true;
                        } else if (this.u.b(c2) > this.u.g()) {
                            if (bVar.c) {
                                i4 = this.u.b();
                            } else {
                                i4 = this.u.f();
                            }
                            bVar.b = i4;
                            return true;
                        } else {
                            int d2 = this.u.d(c2) - this.u.f();
                            if (d2 < 0) {
                                bVar.b = -d2;
                                return true;
                            }
                            int b2 = this.u.b() - this.u.a(c2);
                            if (b2 < 0) {
                                bVar.b = b2;
                                return true;
                            }
                            bVar.b = Integer.MIN_VALUE;
                        }
                    } else {
                        int i5 = this.C;
                        bVar.a = i5;
                        int i6 = this.D;
                        if (i6 == Integer.MIN_VALUE) {
                            if (m(i5) == 1) {
                                z2 = true;
                            }
                            bVar.c = z2;
                            bVar.a();
                        } else {
                            bVar.a(i6);
                        }
                        bVar.d = true;
                    }
                } else {
                    bVar.b = Integer.MIN_VALUE;
                    bVar.a = this.C;
                }
                return true;
            }
        }
        return false;
    }

    private void b(RecyclerView.v vVar, int i2) {
        while (e() > 0) {
            View d2 = d(0);
            if (this.u.a(d2) <= i2 && this.u.e(d2) <= i2) {
                c cVar = (c) d2.getLayoutParams();
                if (cVar.f819f) {
                    int i3 = 0;
                    while (i3 < this.s) {
                        if (this.t[i3].a.size() != 1) {
                            i3++;
                        } else {
                            return;
                        }
                    }
                    for (int i4 = 0; i4 < this.s; i4++) {
                        this.t[i4].l();
                    }
                } else if (cVar.e.a.size() != 1) {
                    cVar.e.l();
                } else {
                    return;
                }
                a(d2, vVar);
            } else {
                return;
            }
        }
    }

    public boolean b() {
        return this.w == 1;
    }

    public int b(int i2, RecyclerView.v vVar, RecyclerView.z zVar) {
        return c(i2, vVar, zVar);
    }

    private boolean c(RecyclerView.z zVar, b bVar) {
        int i2;
        if (this.G) {
            i2 = r(zVar.a());
        } else {
            i2 = q(zVar.a());
        }
        bVar.a = i2;
        bVar.b = Integer.MIN_VALUE;
        return true;
    }

    public int c(RecyclerView.z zVar) {
        return j(zVar);
    }

    private int c(int i2, int i3, int i4) {
        if (i3 == 0 && i4 == 0) {
            return i2;
        }
        int mode = View.MeasureSpec.getMode(i2);
        if (mode == Integer.MIN_VALUE || mode == 1073741824) {
            return View.MeasureSpec.makeMeasureSpec(Math.max(0, (View.MeasureSpec.getSize(i2) - i3) - i4), mode);
        }
        return i2;
    }

    /* access modifiers changed from: package-private */
    public int c(int i2, RecyclerView.v vVar, RecyclerView.z zVar) {
        if (e() == 0 || i2 == 0) {
            return 0;
        }
        a(i2, zVar);
        int a2 = a(vVar, this.y, zVar);
        if (this.y.b >= a2) {
            i2 = i2 < 0 ? -a2 : a2;
        }
        this.u.a(-i2);
        this.G = this.A;
        m mVar = this.y;
        mVar.b = 0;
        a(vVar, mVar);
        return i2;
    }

    public int[] a(int[] iArr) {
        if (iArr == null) {
            iArr = new int[this.s];
        } else if (iArr.length < this.s) {
            throw new IllegalArgumentException("Provided int[]'s size must be more than or equal to span count. Expected:" + this.s + ", array size:" + iArr.length);
        }
        for (int i2 = 0; i2 < this.s; i2++) {
            iArr[i2] = this.t[i2].e();
        }
        return iArr;
    }

    public int a(RecyclerView.z zVar) {
        return h(zVar);
    }

    private void a(View view, c cVar, boolean z2) {
        if (cVar.f819f) {
            if (this.w == 1) {
                a(view, this.J, RecyclerView.o.a(h(), i(), q() + n(), cVar.height, true), z2);
            } else {
                a(view, RecyclerView.o.a(r(), s(), o() + p(), cVar.width, true), this.J, z2);
            }
        } else if (this.w == 1) {
            a(view, RecyclerView.o.a(this.x, s(), 0, cVar.width, false), RecyclerView.o.a(h(), i(), q() + n(), cVar.height, true), z2);
        } else {
            a(view, RecyclerView.o.a(r(), s(), o() + p(), cVar.width, true), RecyclerView.o.a(this.x, i(), 0, cVar.height, false), z2);
        }
    }

    public RecyclerView.p c() {
        if (this.w == 0) {
            return new c(-2, -1);
        }
        return new c(-1, -2);
    }

    private void a(View view, int i2, int i3, boolean z2) {
        boolean z3;
        a(view, this.K);
        c cVar = (c) view.getLayoutParams();
        int i4 = cVar.leftMargin;
        Rect rect = this.K;
        int c2 = c(i2, i4 + rect.left, cVar.rightMargin + rect.right);
        int i5 = cVar.topMargin;
        Rect rect2 = this.K;
        int c3 = c(i3, i5 + rect2.top, cVar.bottomMargin + rect2.bottom);
        if (z2) {
            z3 = b(view, c2, c3, cVar);
        } else {
            z3 = a(view, c2, c3, (RecyclerView.p) cVar);
        }
        if (z3) {
            view.measure(c2, c3);
        }
    }

    public void a(Parcelable parcelable) {
        if (parcelable instanceof SavedState) {
            this.I = (SavedState) parcelable;
            z();
        }
    }

    public void a(RecyclerView.v vVar, RecyclerView.z zVar, View view, androidx.core.h.e0.d dVar) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (!(layoutParams instanceof c)) {
            super.a(view, dVar);
            return;
        }
        c cVar = (c) layoutParams;
        if (this.w == 0) {
            dVar.b((Object) d.c.a(cVar.e(), cVar.f819f ? this.s : 1, -1, -1, false, false));
        } else {
            dVar.b((Object) d.c.a(-1, -1, cVar.e(), cVar.f819f ? this.s : 1, false, false));
        }
    }

    public void a(AccessibilityEvent accessibilityEvent) {
        super.a(accessibilityEvent);
        if (e() > 0) {
            View b2 = b(false);
            View a2 = a(false);
            if (b2 != null && a2 != null) {
                int l = l(b2);
                int l2 = l(a2);
                if (l < l2) {
                    accessibilityEvent.setFromIndex(l);
                    accessibilityEvent.setToIndex(l2);
                    return;
                }
                accessibilityEvent.setFromIndex(l2);
                accessibilityEvent.setToIndex(l);
            }
        }
    }

    public int a(RecyclerView.v vVar, RecyclerView.z zVar) {
        if (this.w == 1) {
            return this.s;
        }
        return super.a(vVar, zVar);
    }

    /* access modifiers changed from: package-private */
    public View a(boolean z2) {
        int f2 = this.u.f();
        int b2 = this.u.b();
        View view = null;
        for (int e = e() - 1; e >= 0; e--) {
            View d2 = d(e);
            int d3 = this.u.d(d2);
            int a2 = this.u.a(d2);
            if (a2 > f2 && d3 < b2) {
                if (a2 <= b2 || !z2) {
                    return d2;
                }
                if (view == null) {
                    view = d2;
                }
            }
        }
        return view;
    }

    private void a(RecyclerView.v vVar, RecyclerView.z zVar, boolean z2) {
        int b2;
        int s2 = s(Integer.MIN_VALUE);
        if (s2 != Integer.MIN_VALUE && (b2 = this.u.b() - s2) > 0) {
            int i2 = b2 - (-c(-b2, vVar, zVar));
            if (z2 && i2 > 0) {
                this.u.a(i2);
            }
        }
    }

    public void a(RecyclerView recyclerView, int i2, int i3) {
        b(i2, i3, 1);
    }

    public void a(RecyclerView recyclerView, int i2, int i3, int i4) {
        b(i2, i3, 8);
    }

    public void a(RecyclerView recyclerView, int i2, int i3, Object obj) {
        b(i2, i3, 4);
    }

    /* JADX WARNING: type inference failed for: r9v0 */
    /* JADX WARNING: type inference failed for: r9v1, types: [boolean, int] */
    /* JADX WARNING: type inference failed for: r9v5 */
    private int a(RecyclerView.v vVar, m mVar, RecyclerView.z zVar) {
        int i2;
        int i3;
        int i4;
        d dVar;
        int i5;
        int i6;
        int i7;
        int i8;
        int i9;
        int i10;
        boolean z2;
        int i11;
        int i12;
        int i13;
        RecyclerView.v vVar2 = vVar;
        m mVar2 = mVar;
        ? r9 = 0;
        this.B.set(0, this.s, true);
        if (this.y.f852i) {
            i2 = mVar2.e == 1 ? Integer.MAX_VALUE : Integer.MIN_VALUE;
        } else {
            if (mVar2.e == 1) {
                i13 = mVar2.f850g + mVar2.b;
            } else {
                i13 = mVar2.f849f - mVar2.b;
            }
            i2 = i13;
        }
        e(mVar2.e, i2);
        if (this.A) {
            i3 = this.u.b();
        } else {
            i3 = this.u.f();
        }
        int i14 = i3;
        boolean z3 = false;
        while (mVar.a(zVar) && (this.y.f852i || !this.B.isEmpty())) {
            View a2 = mVar2.a(vVar2);
            c cVar = (c) a2.getLayoutParams();
            int a3 = cVar.a();
            int d2 = this.E.d(a3);
            boolean z4 = d2 == -1;
            if (z4) {
                dVar = cVar.f819f ? this.t[r9] : a(mVar2);
                this.E.a(a3, dVar);
            } else {
                dVar = this.t[d2];
            }
            d dVar2 = dVar;
            cVar.e = dVar2;
            if (mVar2.e == 1) {
                b(a2);
            } else {
                b(a2, (int) r9);
            }
            a(a2, cVar, (boolean) r9);
            if (mVar2.e == 1) {
                if (cVar.f819f) {
                    i12 = s(i14);
                } else {
                    i12 = dVar2.a(i14);
                }
                int b2 = this.u.b(a2) + i12;
                if (z4 && cVar.f819f) {
                    LazySpanLookup.FullSpanItem o = o(i12);
                    o.f809f = -1;
                    o.e = a3;
                    this.E.a(o);
                }
                i5 = b2;
                i6 = i12;
            } else {
                if (cVar.f819f) {
                    i11 = v(i14);
                } else {
                    i11 = dVar2.b(i14);
                }
                i6 = i11 - this.u.b(a2);
                if (z4 && cVar.f819f) {
                    LazySpanLookup.FullSpanItem p = p(i11);
                    p.f809f = 1;
                    p.e = a3;
                    this.E.a(p);
                }
                i5 = i11;
            }
            if (cVar.f819f && mVar2.d == -1) {
                if (z4) {
                    this.M = true;
                } else {
                    if (mVar2.e == 1) {
                        z2 = E();
                    } else {
                        z2 = F();
                    }
                    if (!z2) {
                        LazySpanLookup.FullSpanItem c2 = this.E.c(a3);
                        if (c2 != null) {
                            c2.f811h = true;
                        }
                        this.M = true;
                    }
                }
            }
            a(a2, cVar, mVar2);
            if (!N() || this.w != 1) {
                if (cVar.f819f) {
                    i9 = this.v.f();
                } else {
                    i9 = (dVar2.e * this.x) + this.v.f();
                }
                i8 = i9;
                i7 = this.v.b(a2) + i9;
            } else {
                if (cVar.f819f) {
                    i10 = this.v.b();
                } else {
                    i10 = this.v.b() - (((this.s - 1) - dVar2.e) * this.x);
                }
                i7 = i10;
                i8 = i10 - this.v.b(a2);
            }
            if (this.w == 1) {
                a(a2, i8, i6, i7, i5);
            } else {
                a(a2, i6, i8, i5, i7);
            }
            if (cVar.f819f) {
                e(this.y.e, i2);
            } else {
                a(dVar2, this.y.e, i2);
            }
            a(vVar2, this.y);
            if (this.y.f851h && a2.hasFocusable()) {
                if (cVar.f819f) {
                    this.B.clear();
                } else {
                    this.B.set(dVar2.e, false);
                    z3 = true;
                    r9 = 0;
                }
            }
            z3 = true;
            r9 = 0;
        }
        if (!z3) {
            a(vVar2, this.y);
        }
        if (this.y.e == -1) {
            i4 = this.u.f() - v(this.u.f());
        } else {
            i4 = s(this.u.b()) - this.u.b();
        }
        if (i4 > 0) {
            return Math.min(mVar2.b, i4);
        }
        return 0;
    }

    private void a(View view, c cVar, m mVar) {
        if (mVar.e == 1) {
            if (cVar.f819f) {
                p(view);
            } else {
                cVar.e.a(view);
            }
        } else if (cVar.f819f) {
            q(view);
        } else {
            cVar.e.c(view);
        }
    }

    private void a(RecyclerView.v vVar, m mVar) {
        int i2;
        int i3;
        if (mVar.a && !mVar.f852i) {
            if (mVar.b == 0) {
                if (mVar.e == -1) {
                    a(vVar, mVar.f850g);
                } else {
                    b(vVar, mVar.f849f);
                }
            } else if (mVar.e == -1) {
                int i4 = mVar.f849f;
                int t2 = i4 - t(i4);
                if (t2 < 0) {
                    i3 = mVar.f850g;
                } else {
                    i3 = mVar.f850g - Math.min(t2, mVar.b);
                }
                a(vVar, i3);
            } else {
                int u2 = u(mVar.f850g) - mVar.f850g;
                if (u2 < 0) {
                    i2 = mVar.f849f;
                } else {
                    i2 = Math.min(u2, mVar.b) + mVar.f849f;
                }
                b(vVar, i2);
            }
        }
    }

    private void a(d dVar, int i2, int i3) {
        int g2 = dVar.g();
        if (i2 == -1) {
            if (dVar.i() + g2 <= i3) {
                this.B.set(dVar.e, false);
            }
        } else if (dVar.h() - g2 >= i3) {
            this.B.set(dVar.e, false);
        }
    }

    private void a(RecyclerView.v vVar, int i2) {
        int e = e() - 1;
        while (e >= 0) {
            View d2 = d(e);
            if (this.u.d(d2) >= i2 && this.u.f(d2) >= i2) {
                c cVar = (c) d2.getLayoutParams();
                if (cVar.f819f) {
                    int i3 = 0;
                    while (i3 < this.s) {
                        if (this.t[i3].a.size() != 1) {
                            i3++;
                        } else {
                            return;
                        }
                    }
                    for (int i4 = 0; i4 < this.s; i4++) {
                        this.t[i4].k();
                    }
                } else if (cVar.e.a.size() != 1) {
                    cVar.e.k();
                } else {
                    return;
                }
                a(d2, vVar);
                e--;
            } else {
                return;
            }
        }
    }

    private d a(m mVar) {
        int i2;
        int i3;
        int i4 = -1;
        if (w(mVar.e)) {
            i3 = this.s - 1;
            i2 = -1;
        } else {
            i3 = 0;
            i4 = this.s;
            i2 = 1;
        }
        d dVar = null;
        if (mVar.e == 1) {
            int i5 = Integer.MAX_VALUE;
            int f2 = this.u.f();
            while (i3 != i4) {
                d dVar2 = this.t[i3];
                int a2 = dVar2.a(f2);
                if (a2 < i5) {
                    dVar = dVar2;
                    i5 = a2;
                }
                i3 += i2;
            }
            return dVar;
        }
        int i6 = Integer.MIN_VALUE;
        int b2 = this.u.b();
        while (i3 != i4) {
            d dVar3 = this.t[i3];
            int b3 = dVar3.b(b2);
            if (b3 > i6) {
                dVar = dVar3;
                i6 = b3;
            }
            i3 += i2;
        }
        return dVar;
    }

    public boolean a() {
        return this.w == 0;
    }

    public int a(int i2, RecyclerView.v vVar, RecyclerView.z zVar) {
        return c(i2, vVar, zVar);
    }

    public PointF a(int i2) {
        int m = m(i2);
        PointF pointF = new PointF();
        if (m == 0) {
            return null;
        }
        if (this.w == 0) {
            pointF.x = (float) m;
            pointF.y = 0.0f;
        } else {
            pointF.x = 0.0f;
            pointF.y = (float) m;
        }
        return pointF;
    }

    public void a(RecyclerView recyclerView, RecyclerView.z zVar, int i2) {
        n nVar = new n(recyclerView.getContext());
        nVar.c(i2);
        b((RecyclerView.y) nVar);
    }

    public void a(int i2, int i3, RecyclerView.z zVar, RecyclerView.o.c cVar) {
        int i4;
        int i5;
        if (this.w != 0) {
            i2 = i3;
        }
        if (e() != 0 && i2 != 0) {
            a(i2, zVar);
            int[] iArr = this.O;
            if (iArr == null || iArr.length < this.s) {
                this.O = new int[this.s];
            }
            int i6 = 0;
            for (int i7 = 0; i7 < this.s; i7++) {
                m mVar = this.y;
                if (mVar.d == -1) {
                    i5 = mVar.f849f;
                    i4 = this.t[i7].b(i5);
                } else {
                    i5 = this.t[i7].a(mVar.f850g);
                    i4 = this.y.f850g;
                }
                int i8 = i5 - i4;
                if (i8 >= 0) {
                    this.O[i6] = i8;
                    i6++;
                }
            }
            Arrays.sort(this.O, 0, i6);
            for (int i9 = 0; i9 < i6 && this.y.a(zVar); i9++) {
                cVar.a(this.y.c, this.O[i9]);
                m mVar2 = this.y;
                mVar2.c += mVar2.d;
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void a(int i2, RecyclerView.z zVar) {
        int i3;
        int i4;
        if (i2 > 0) {
            i4 = J();
            i3 = 1;
        } else {
            i4 = I();
            i3 = -1;
        }
        this.y.a = true;
        b(i4, zVar);
        x(i3);
        m mVar = this.y;
        mVar.c = i4 + mVar.d;
        mVar.b = Math.abs(i2);
    }

    public RecyclerView.p a(Context context, AttributeSet attributeSet) {
        return new c(context, attributeSet);
    }

    public RecyclerView.p a(ViewGroup.LayoutParams layoutParams) {
        if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
            return new c((ViewGroup.MarginLayoutParams) layoutParams);
        }
        return new c(layoutParams);
    }

    public boolean a(RecyclerView.p pVar) {
        return pVar instanceof c;
    }

    public View a(View view, int i2, RecyclerView.v vVar, RecyclerView.z zVar) {
        View c2;
        int i3;
        int i4;
        int i5;
        int i6;
        View a2;
        if (e() == 0 || (c2 = c(view)) == null) {
            return null;
        }
        Q();
        int n = n(i2);
        if (n == Integer.MIN_VALUE) {
            return null;
        }
        c cVar = (c) c2.getLayoutParams();
        boolean z2 = cVar.f819f;
        d dVar = cVar.e;
        if (n == 1) {
            i3 = J();
        } else {
            i3 = I();
        }
        b(i3, zVar);
        x(n);
        m mVar = this.y;
        mVar.c = mVar.d + i3;
        mVar.b = (int) (((float) this.u.g()) * 0.33333334f);
        m mVar2 = this.y;
        mVar2.f851h = true;
        mVar2.a = false;
        a(vVar, mVar2, zVar);
        this.G = this.A;
        if (!z2 && (a2 = dVar.a(i3, n)) != null && a2 != c2) {
            return a2;
        }
        if (w(n)) {
            for (int i7 = this.s - 1; i7 >= 0; i7--) {
                View a3 = this.t[i7].a(i3, n);
                if (a3 != null && a3 != c2) {
                    return a3;
                }
            }
        } else {
            for (int i8 = 0; i8 < this.s; i8++) {
                View a4 = this.t[i8].a(i3, n);
                if (a4 != null && a4 != c2) {
                    return a4;
                }
            }
        }
        boolean z3 = (this.z ^ true) == (n == -1);
        if (!z2) {
            if (z3) {
                i6 = dVar.d();
            } else {
                i6 = dVar.f();
            }
            View c3 = c(i6);
            if (!(c3 == null || c3 == c2)) {
                return c3;
            }
        }
        if (w(n)) {
            for (int i9 = this.s - 1; i9 >= 0; i9--) {
                if (i9 != dVar.e) {
                    if (z3) {
                        i5 = this.t[i9].d();
                    } else {
                        i5 = this.t[i9].f();
                    }
                    View c4 = c(i5);
                    if (!(c4 == null || c4 == c2)) {
                        return c4;
                    }
                }
            }
        } else {
            for (int i10 = 0; i10 < this.s; i10++) {
                if (z3) {
                    i4 = this.t[i10].d();
                } else {
                    i4 = this.t[i10].f();
                }
                View c5 = c(i4);
                if (c5 != null && c5 != c2) {
                    return c5;
                }
            }
        }
        return null;
    }
}
