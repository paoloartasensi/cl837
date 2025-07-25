package androidx.recyclerview.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.PointF;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.j;
import java.util.List;

public class LinearLayoutManager extends RecyclerView.o implements j.c, RecyclerView.y.b {
    int A;
    int B;
    private boolean C;
    SavedState D;
    final a E;
    private final b F;
    private int G;
    private int[] H;
    int s;
    private c t;
    q u;
    private boolean v;
    private boolean w;
    boolean x;
    private boolean y;
    private boolean z;

    @SuppressLint({"BanParcelableUsage"})
    public static class SavedState implements Parcelable {
        public static final Parcelable.Creator<SavedState> CREATOR = new a();
        int e;

        /* renamed from: f  reason: collision with root package name */
        int f771f;

        /* renamed from: g  reason: collision with root package name */
        boolean f772g;

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
        public boolean a() {
            return this.e >= 0;
        }

        /* access modifiers changed from: package-private */
        public void b() {
            this.e = -1;
        }

        public int describeContents() {
            return 0;
        }

        public void writeToParcel(Parcel parcel, int i2) {
            parcel.writeInt(this.e);
            parcel.writeInt(this.f771f);
            parcel.writeInt(this.f772g ? 1 : 0);
        }

        SavedState(Parcel parcel) {
            this.e = parcel.readInt();
            this.f771f = parcel.readInt();
            this.f772g = parcel.readInt() != 1 ? false : true;
        }

        public SavedState(SavedState savedState) {
            this.e = savedState.e;
            this.f771f = savedState.f771f;
            this.f772g = savedState.f772g;
        }
    }

    protected static class b {
        public int a;
        public boolean b;
        public boolean c;
        public boolean d;

        protected b() {
        }

        /* access modifiers changed from: package-private */
        public void a() {
            this.a = 0;
            this.b = false;
            this.c = false;
            this.d = false;
        }
    }

    static class c {
        boolean a = true;
        int b;
        int c;
        int d;
        int e;

        /* renamed from: f  reason: collision with root package name */
        int f773f;

        /* renamed from: g  reason: collision with root package name */
        int f774g;

        /* renamed from: h  reason: collision with root package name */
        int f775h = 0;

        /* renamed from: i  reason: collision with root package name */
        int f776i = 0;

        /* renamed from: j  reason: collision with root package name */
        boolean f777j;
        int k;
        List<RecyclerView.c0> l = null;
        boolean m;

        c() {
        }

        private View b() {
            int size = this.l.size();
            for (int i2 = 0; i2 < size; i2++) {
                View view = this.l.get(i2).itemView;
                RecyclerView.p pVar = (RecyclerView.p) view.getLayoutParams();
                if (!pVar.c() && this.d == pVar.a()) {
                    a(view);
                    return view;
                }
            }
            return null;
        }

        /* access modifiers changed from: package-private */
        public boolean a(RecyclerView.z zVar) {
            int i2 = this.d;
            return i2 >= 0 && i2 < zVar.a();
        }

        /* access modifiers changed from: package-private */
        public View a(RecyclerView.v vVar) {
            if (this.l != null) {
                return b();
            }
            View d2 = vVar.d(this.d);
            this.d += this.e;
            return d2;
        }

        public void a() {
            a((View) null);
        }

        public void a(View view) {
            View b2 = b(view);
            if (b2 == null) {
                this.d = -1;
            } else {
                this.d = ((RecyclerView.p) b2.getLayoutParams()).a();
            }
        }

        public View b(View view) {
            int a2;
            int size = this.l.size();
            View view2 = null;
            int i2 = Integer.MAX_VALUE;
            for (int i3 = 0; i3 < size; i3++) {
                View view3 = this.l.get(i3).itemView;
                RecyclerView.p pVar = (RecyclerView.p) view3.getLayoutParams();
                if (view3 != view && !pVar.c() && (a2 = (pVar.a() - this.d) * this.e) >= 0 && a2 < i2) {
                    view2 = view3;
                    if (a2 == 0) {
                        break;
                    }
                    i2 = a2;
                }
            }
            return view2;
        }
    }

    public LinearLayoutManager(Context context) {
        this(context, 1, false);
    }

    private View O() {
        return e(0, e());
    }

    private View P() {
        return e(e() - 1, -1);
    }

    private View Q() {
        if (this.x) {
            return O();
        }
        return P();
    }

    private View R() {
        if (this.x) {
            return P();
        }
        return O();
    }

    private View S() {
        return d(this.x ? 0 : e() - 1);
    }

    private View T() {
        return d(this.x ? e() - 1 : 0);
    }

    private void U() {
        if (this.s == 1 || !L()) {
            this.x = this.w;
        } else {
            this.x = !this.w;
        }
    }

    private int j(RecyclerView.z zVar) {
        if (e() == 0) {
            return 0;
        }
        F();
        q qVar = this.u;
        View b2 = b(!this.z, true);
        return t.a(zVar, qVar, b2, a(!this.z, true), this, this.z, this.x);
    }

    /* access modifiers changed from: package-private */
    public boolean B() {
        return (i() == 1073741824 || s() == 1073741824 || !t()) ? false : true;
    }

    public boolean D() {
        return this.D == null && this.v == this.y;
    }

    /* access modifiers changed from: package-private */
    public c E() {
        return new c();
    }

    /* access modifiers changed from: package-private */
    public void F() {
        if (this.t == null) {
            this.t = E();
        }
    }

    public int G() {
        View a2 = a(0, e(), true, false);
        if (a2 == null) {
            return -1;
        }
        return l(a2);
    }

    public int H() {
        View a2 = a(0, e(), false, true);
        if (a2 == null) {
            return -1;
        }
        return l(a2);
    }

    public int I() {
        View a2 = a(e() - 1, -1, true, false);
        if (a2 == null) {
            return -1;
        }
        return l(a2);
    }

    public int J() {
        View a2 = a(e() - 1, -1, false, true);
        if (a2 == null) {
            return -1;
        }
        return l(a2);
    }

    public int K() {
        return this.s;
    }

    /* access modifiers changed from: protected */
    public boolean L() {
        return k() == 1;
    }

    public boolean M() {
        return this.z;
    }

    /* access modifiers changed from: package-private */
    public boolean N() {
        return this.u.d() == 0 && this.u.a() == 0;
    }

    public void a(AccessibilityEvent accessibilityEvent) {
        super.a(accessibilityEvent);
        if (e() > 0) {
            accessibilityEvent.setFromIndex(H());
            accessibilityEvent.setToIndex(J());
        }
    }

    /* access modifiers changed from: package-private */
    public void a(RecyclerView.v vVar, RecyclerView.z zVar, a aVar, int i2) {
    }

    public void b(RecyclerView recyclerView, RecyclerView.v vVar) {
        super.b(recyclerView, vVar);
        if (this.C) {
            b(vVar);
            vVar.a();
        }
    }

    public RecyclerView.p c() {
        return new RecyclerView.p(-2, -2);
    }

    public int d(RecyclerView.z zVar) {
        return i(zVar);
    }

    public void e(RecyclerView.v vVar, RecyclerView.z zVar) {
        int i2;
        int i3;
        int i4;
        int i5;
        int i6;
        int i7;
        View c2;
        int i8;
        int i9;
        int i10 = -1;
        if (!(this.D == null && this.A == -1) && zVar.a() == 0) {
            b(vVar);
            return;
        }
        SavedState savedState = this.D;
        if (savedState != null && savedState.a()) {
            this.A = this.D.e;
        }
        F();
        this.t.a = false;
        U();
        View g2 = g();
        if (!this.E.e || this.A != -1 || this.D != null) {
            this.E.b();
            a aVar = this.E;
            aVar.d = this.x ^ this.y;
            b(vVar, zVar, aVar);
            this.E.e = true;
        } else if (g2 != null && (this.u.d(g2) >= this.u.b() || this.u.a(g2) <= this.u.f())) {
            this.E.b(g2, l(g2));
        }
        c cVar = this.t;
        cVar.f773f = cVar.k >= 0 ? 1 : -1;
        int[] iArr = this.H;
        iArr[0] = 0;
        iArr[1] = 0;
        a(zVar, iArr);
        int max = Math.max(0, this.H[0]) + this.u.f();
        int max2 = Math.max(0, this.H[1]) + this.u.c();
        if (!(!zVar.d() || (i7 = this.A) == -1 || this.B == Integer.MIN_VALUE || (c2 = c(i7)) == null)) {
            if (this.x) {
                i8 = this.u.b() - this.u.a(c2);
                i9 = this.B;
            } else {
                i9 = this.u.d(c2) - this.u.f();
                i8 = this.B;
            }
            int i11 = i8 - i9;
            if (i11 > 0) {
                max += i11;
            } else {
                max2 -= i11;
            }
        }
        if (!this.E.d ? !this.x : this.x) {
            i10 = 1;
        }
        a(vVar, zVar, this.E, i10);
        a(vVar);
        this.t.m = N();
        this.t.f777j = zVar.d();
        this.t.f776i = 0;
        a aVar2 = this.E;
        if (aVar2.d) {
            b(aVar2);
            c cVar2 = this.t;
            cVar2.f775h = max;
            a(vVar, cVar2, zVar, false);
            c cVar3 = this.t;
            i3 = cVar3.b;
            int i12 = cVar3.d;
            int i13 = cVar3.c;
            if (i13 > 0) {
                max2 += i13;
            }
            a(this.E);
            c cVar4 = this.t;
            cVar4.f775h = max2;
            cVar4.d += cVar4.e;
            a(vVar, cVar4, zVar, false);
            c cVar5 = this.t;
            i2 = cVar5.b;
            int i14 = cVar5.c;
            if (i14 > 0) {
                h(i12, i3);
                c cVar6 = this.t;
                cVar6.f775h = i14;
                a(vVar, cVar6, zVar, false);
                i3 = this.t.b;
            }
        } else {
            a(aVar2);
            c cVar7 = this.t;
            cVar7.f775h = max2;
            a(vVar, cVar7, zVar, false);
            c cVar8 = this.t;
            i2 = cVar8.b;
            int i15 = cVar8.d;
            int i16 = cVar8.c;
            if (i16 > 0) {
                max += i16;
            }
            b(this.E);
            c cVar9 = this.t;
            cVar9.f775h = max;
            cVar9.d += cVar9.e;
            a(vVar, cVar9, zVar, false);
            c cVar10 = this.t;
            i3 = cVar10.b;
            int i17 = cVar10.c;
            if (i17 > 0) {
                g(i15, i2);
                c cVar11 = this.t;
                cVar11.f775h = i17;
                a(vVar, cVar11, zVar, false);
                i2 = this.t.b;
            }
        }
        if (e() > 0) {
            if (this.x ^ this.y) {
                int a2 = a(i2, vVar, zVar, true);
                i5 = i3 + a2;
                i4 = i2 + a2;
                i6 = b(i5, vVar, zVar, false);
            } else {
                int b2 = b(i3, vVar, zVar, true);
                i5 = i3 + b2;
                i4 = i2 + b2;
                i6 = a(i4, vVar, zVar, false);
            }
            i3 = i5 + i6;
            i2 = i4 + i6;
        }
        b(vVar, zVar, i3, i2);
        if (!zVar.d()) {
            this.u.i();
        } else {
            this.E.b();
        }
        this.v = this.y;
    }

    public void f(int i2, int i3) {
        this.A = i2;
        this.B = i3;
        SavedState savedState = this.D;
        if (savedState != null) {
            savedState.b();
        }
        z();
    }

    public void g(RecyclerView.z zVar) {
        super.g(zVar);
        this.D = null;
        this.A = -1;
        this.B = Integer.MIN_VALUE;
        this.E.b();
    }

    /* access modifiers changed from: protected */
    @Deprecated
    public int h(RecyclerView.z zVar) {
        if (zVar.c()) {
            return this.u.g();
        }
        return 0;
    }

    public void i(int i2) {
        this.A = i2;
        this.B = Integer.MIN_VALUE;
        SavedState savedState = this.D;
        if (savedState != null) {
            savedState.b();
        }
        z();
    }

    public void k(int i2) {
        if (i2 == 0 || i2 == 1) {
            a((String) null);
            if (i2 != this.s || this.u == null) {
                q a2 = q.a(this, i2);
                this.u = a2;
                this.E.a = a2;
                this.s = i2;
                z();
                return;
            }
            return;
        }
        throw new IllegalArgumentException("invalid orientation:" + i2);
    }

    public boolean v() {
        return true;
    }

    public Parcelable y() {
        if (this.D != null) {
            return new SavedState(this.D);
        }
        SavedState savedState = new SavedState();
        if (e() > 0) {
            F();
            boolean z2 = this.v ^ this.x;
            savedState.f772g = z2;
            if (z2) {
                View S = S();
                savedState.f771f = this.u.b() - this.u.a(S);
                savedState.e = l(S);
            } else {
                View T = T();
                savedState.e = l(T);
                savedState.f771f = this.u.d(T) - this.u.f();
            }
        } else {
            savedState.b();
        }
        return savedState;
    }

    public LinearLayoutManager(Context context, int i2, boolean z2) {
        this.s = 1;
        this.w = false;
        this.x = false;
        this.y = false;
        this.z = true;
        this.A = -1;
        this.B = Integer.MIN_VALUE;
        this.D = null;
        this.E = new a();
        this.F = new b();
        this.G = 2;
        this.H = new int[2];
        k(i2);
        a(z2);
    }

    public View c(int i2) {
        int e = e();
        if (e == 0) {
            return null;
        }
        int l = i2 - l(d(0));
        if (l >= 0 && l < e) {
            View d = d(l);
            if (l(d) == i2) {
                return d;
            }
        }
        return super.c(i2);
    }

    static class a {
        q a;
        int b;
        int c;
        boolean d;
        boolean e;

        a() {
            b();
        }

        /* access modifiers changed from: package-private */
        public void a() {
            int i2;
            if (this.d) {
                i2 = this.a.b();
            } else {
                i2 = this.a.f();
            }
            this.c = i2;
        }

        /* access modifiers changed from: package-private */
        public void b() {
            this.b = -1;
            this.c = Integer.MIN_VALUE;
            this.d = false;
            this.e = false;
        }

        public String toString() {
            return "AnchorInfo{mPosition=" + this.b + ", mCoordinate=" + this.c + ", mLayoutFromEnd=" + this.d + ", mValid=" + this.e + '}';
        }

        /* access modifiers changed from: package-private */
        public boolean a(View view, RecyclerView.z zVar) {
            RecyclerView.p pVar = (RecyclerView.p) view.getLayoutParams();
            return !pVar.c() && pVar.a() >= 0 && pVar.a() < zVar.a();
        }

        public void b(View view, int i2) {
            int h2 = this.a.h();
            if (h2 >= 0) {
                a(view, i2);
                return;
            }
            this.b = i2;
            if (this.d) {
                int b2 = (this.a.b() - h2) - this.a.a(view);
                this.c = this.a.b() - b2;
                if (b2 > 0) {
                    int b3 = this.c - this.a.b(view);
                    int f2 = this.a.f();
                    int min = b3 - (f2 + Math.min(this.a.d(view) - f2, 0));
                    if (min < 0) {
                        this.c += Math.min(b2, -min);
                        return;
                    }
                    return;
                }
                return;
            }
            int d2 = this.a.d(view);
            int f3 = d2 - this.a.f();
            this.c = d2;
            if (f3 > 0) {
                int b4 = (this.a.b() - Math.min(0, (this.a.b() - h2) - this.a.a(view))) - (d2 + this.a.b(view));
                if (b4 < 0) {
                    this.c -= Math.min(f3, -b4);
                }
            }
        }

        public void a(View view, int i2) {
            if (this.d) {
                this.c = this.a.a(view) + this.a.h();
            } else {
                this.c = this.a.d(view);
            }
            this.b = i2;
        }
    }

    private void h(int i2, int i3) {
        this.t.c = i3 - this.u.f();
        c cVar = this.t;
        cVar.d = i2;
        cVar.e = this.x ? 1 : -1;
        c cVar2 = this.t;
        cVar2.f773f = -1;
        cVar2.b = i3;
        cVar2.f774g = Integer.MIN_VALUE;
    }

    public void a(Parcelable parcelable) {
        if (parcelable instanceof SavedState) {
            this.D = (SavedState) parcelable;
            z();
        }
    }

    public boolean b() {
        return this.s == 1;
    }

    private void g(int i2, int i3) {
        this.t.c = this.u.b() - i3;
        this.t.e = this.x ? -1 : 1;
        c cVar = this.t;
        cVar.d = i2;
        cVar.f773f = 1;
        cVar.b = i3;
        cVar.f774g = Integer.MIN_VALUE;
    }

    private int i(RecyclerView.z zVar) {
        if (e() == 0) {
            return 0;
        }
        F();
        q qVar = this.u;
        View b2 = b(!this.z, true);
        return t.a(zVar, qVar, b2, a(!this.z, true), this, this.z);
    }

    public void b(boolean z2) {
        a((String) null);
        if (this.y != z2) {
            this.y = z2;
            z();
        }
    }

    public int f(RecyclerView.z zVar) {
        return k(zVar);
    }

    private View f(RecyclerView.v vVar, RecyclerView.z zVar) {
        return a(vVar, zVar, 0, e(), zVar.a());
    }

    public int c(RecyclerView.z zVar) {
        return k(zVar);
    }

    /* access modifiers changed from: package-private */
    public int j(int i2) {
        if (i2 == 1) {
            return (this.s != 1 && L()) ? 1 : -1;
        }
        if (i2 == 2) {
            return (this.s != 1 && L()) ? -1 : 1;
        }
        if (i2 != 17) {
            if (i2 != 33) {
                if (i2 != 66) {
                    return (i2 == 130 && this.s == 1) ? 1 : Integer.MIN_VALUE;
                }
                if (this.s == 0) {
                    return 1;
                }
                return Integer.MIN_VALUE;
            } else if (this.s == 1) {
                return -1;
            } else {
                return Integer.MIN_VALUE;
            }
        } else if (this.s == 0) {
            return -1;
        } else {
            return Integer.MIN_VALUE;
        }
    }

    private int k(RecyclerView.z zVar) {
        if (e() == 0) {
            return 0;
        }
        F();
        q qVar = this.u;
        View b2 = b(!this.z, true);
        return t.b(zVar, qVar, b2, a(!this.z, true), this, this.z);
    }

    public boolean a() {
        return this.s == 0;
    }

    /* access modifiers changed from: package-private */
    public int c(int i2, RecyclerView.v vVar, RecyclerView.z zVar) {
        if (e() == 0 || i2 == 0) {
            return 0;
        }
        F();
        this.t.a = true;
        int i3 = i2 > 0 ? 1 : -1;
        int abs = Math.abs(i2);
        a(i3, abs, true, zVar);
        c cVar = this.t;
        int a2 = cVar.f774g + a(vVar, cVar, zVar, false);
        if (a2 < 0) {
            return 0;
        }
        if (abs > a2) {
            i2 = i3 * a2;
        }
        this.u.a(-i2);
        this.t.k = i2;
        return i2;
    }

    private View h(RecyclerView.v vVar, RecyclerView.z zVar) {
        if (this.x) {
            return f(vVar, zVar);
        }
        return g(vVar, zVar);
    }

    public void a(boolean z2) {
        a((String) null);
        if (z2 != this.w) {
            this.w = z2;
            z();
        }
    }

    private void b(RecyclerView.v vVar, RecyclerView.z zVar, int i2, int i3) {
        RecyclerView.v vVar2 = vVar;
        RecyclerView.z zVar2 = zVar;
        if (zVar.e() && e() != 0 && !zVar.d() && D()) {
            List<RecyclerView.c0> f2 = vVar.f();
            int size = f2.size();
            int l = l(d(0));
            int i4 = 0;
            int i5 = 0;
            for (int i6 = 0; i6 < size; i6++) {
                RecyclerView.c0 c0Var = f2.get(i6);
                if (!c0Var.isRemoved()) {
                    char c2 = 1;
                    if ((c0Var.getLayoutPosition() < l) != this.x) {
                        c2 = 65535;
                    }
                    if (c2 == 65535) {
                        i4 += this.u.b(c0Var.itemView);
                    } else {
                        i5 += this.u.b(c0Var.itemView);
                    }
                }
            }
            this.t.l = f2;
            if (i4 > 0) {
                h(l(T()), i2);
                c cVar = this.t;
                cVar.f775h = i4;
                cVar.c = 0;
                cVar.a();
                a(vVar2, this.t, zVar2, false);
            }
            if (i5 > 0) {
                g(l(S()), i3);
                c cVar2 = this.t;
                cVar2.f775h = i5;
                cVar2.c = 0;
                cVar2.a();
                a(vVar2, this.t, zVar2, false);
            }
            this.t.l = null;
        }
    }

    private View g(RecyclerView.v vVar, RecyclerView.z zVar) {
        return a(vVar, zVar, e() - 1, -1, zVar.a());
    }

    private View i(RecyclerView.v vVar, RecyclerView.z zVar) {
        if (this.x) {
            return g(vVar, zVar);
        }
        return f(vVar, zVar);
    }

    /* access modifiers changed from: protected */
    public void a(RecyclerView.z zVar, int[] iArr) {
        int i2;
        int h2 = h(zVar);
        if (this.t.f773f == -1) {
            i2 = 0;
        } else {
            i2 = h2;
            h2 = 0;
        }
        iArr[0] = h2;
        iArr[1] = i2;
    }

    public LinearLayoutManager(Context context, AttributeSet attributeSet, int i2, int i3) {
        this.s = 1;
        this.w = false;
        this.x = false;
        this.y = false;
        this.z = true;
        this.A = -1;
        this.B = Integer.MIN_VALUE;
        this.D = null;
        this.E = new a();
        this.F = new b();
        this.G = 2;
        this.H = new int[2];
        RecyclerView.o.d a2 = RecyclerView.o.a(context, attributeSet, i2, i3);
        k(a2.a);
        a(a2.c);
        b(a2.d);
    }

    private void c(RecyclerView.v vVar, int i2, int i3) {
        if (i2 >= 0) {
            int i4 = i2 - i3;
            int e = e();
            if (this.x) {
                int i5 = e - 1;
                for (int i6 = i5; i6 >= 0; i6--) {
                    View d = d(i6);
                    if (this.u.a(d) > i4 || this.u.e(d) > i4) {
                        a(vVar, i5, i6);
                        return;
                    }
                }
                return;
            }
            for (int i7 = 0; i7 < e; i7++) {
                View d2 = d(i7);
                if (this.u.a(d2) > i4 || this.u.e(d2) > i4) {
                    a(vVar, 0, i7);
                    return;
                }
            }
        }
    }

    public void a(RecyclerView recyclerView, RecyclerView.z zVar, int i2) {
        n nVar = new n(recyclerView.getContext());
        nVar.c(i2);
        b((RecyclerView.y) nVar);
    }

    public PointF a(int i2) {
        if (e() == 0) {
            return null;
        }
        boolean z2 = false;
        int i3 = 1;
        if (i2 < l(d(0))) {
            z2 = true;
        }
        if (z2 != this.x) {
            i3 = -1;
        }
        if (this.s == 0) {
            return new PointF((float) i3, 0.0f);
        }
        return new PointF(0.0f, (float) i3);
    }

    private boolean a(RecyclerView.v vVar, RecyclerView.z zVar, a aVar) {
        View view;
        int i2;
        boolean z2 = false;
        if (e() == 0) {
            return false;
        }
        View g2 = g();
        if (g2 != null && aVar.a(g2, zVar)) {
            aVar.b(g2, l(g2));
            return true;
        } else if (this.v != this.y) {
            return false;
        } else {
            if (aVar.d) {
                view = h(vVar, zVar);
            } else {
                view = i(vVar, zVar);
            }
            if (view == null) {
                return false;
            }
            aVar.a(view, l(view));
            if (!zVar.d() && D()) {
                if (this.u.d(view) >= this.u.b() || this.u.a(view) < this.u.f()) {
                    z2 = true;
                }
                if (z2) {
                    if (aVar.d) {
                        i2 = this.u.b();
                    } else {
                        i2 = this.u.f();
                    }
                    aVar.c = i2;
                }
            }
            return true;
        }
    }

    private void b(RecyclerView.v vVar, RecyclerView.z zVar, a aVar) {
        if (!a(zVar, aVar) && !a(vVar, zVar, aVar)) {
            aVar.a();
            aVar.b = this.y ? zVar.a() - 1 : 0;
        }
    }

    private int b(int i2, RecyclerView.v vVar, RecyclerView.z zVar, boolean z2) {
        int f2;
        int f3 = i2 - this.u.f();
        if (f3 <= 0) {
            return 0;
        }
        int i3 = -c(f3, vVar, zVar);
        int i4 = i2 + i3;
        if (!z2 || (f2 = i4 - this.u.f()) <= 0) {
            return i3;
        }
        this.u.a(-f2);
        return i3 - f2;
    }

    private void b(a aVar) {
        h(aVar.b, aVar.c);
    }

    public int b(int i2, RecyclerView.v vVar, RecyclerView.z zVar) {
        if (this.s == 0) {
            return 0;
        }
        return c(i2, vVar, zVar);
    }

    private boolean a(RecyclerView.z zVar, a aVar) {
        int i2;
        int i3;
        boolean z2 = false;
        if (!zVar.d() && (i2 = this.A) != -1) {
            if (i2 < 0 || i2 >= zVar.a()) {
                this.A = -1;
                this.B = Integer.MIN_VALUE;
            } else {
                aVar.b = this.A;
                SavedState savedState = this.D;
                if (savedState != null && savedState.a()) {
                    boolean z3 = this.D.f772g;
                    aVar.d = z3;
                    if (z3) {
                        aVar.c = this.u.b() - this.D.f771f;
                    } else {
                        aVar.c = this.u.f() + this.D.f771f;
                    }
                    return true;
                } else if (this.B == Integer.MIN_VALUE) {
                    View c2 = c(this.A);
                    if (c2 == null) {
                        if (e() > 0) {
                            if ((this.A < l(d(0))) == this.x) {
                                z2 = true;
                            }
                            aVar.d = z2;
                        }
                        aVar.a();
                    } else if (this.u.b(c2) > this.u.g()) {
                        aVar.a();
                        return true;
                    } else if (this.u.d(c2) - this.u.f() < 0) {
                        aVar.c = this.u.f();
                        aVar.d = false;
                        return true;
                    } else if (this.u.b() - this.u.a(c2) < 0) {
                        aVar.c = this.u.b();
                        aVar.d = true;
                        return true;
                    } else {
                        if (aVar.d) {
                            i3 = this.u.a(c2) + this.u.h();
                        } else {
                            i3 = this.u.d(c2);
                        }
                        aVar.c = i3;
                    }
                    return true;
                } else {
                    boolean z4 = this.x;
                    aVar.d = z4;
                    if (z4) {
                        aVar.c = this.u.b() - this.B;
                    } else {
                        aVar.c = this.u.f() + this.B;
                    }
                    return true;
                }
            }
        }
        return false;
    }

    public int b(RecyclerView.z zVar) {
        return j(zVar);
    }

    private void b(RecyclerView.v vVar, int i2, int i3) {
        int e = e();
        if (i2 >= 0) {
            int a2 = (this.u.a() - i2) + i3;
            if (this.x) {
                for (int i4 = 0; i4 < e; i4++) {
                    View d = d(i4);
                    if (this.u.d(d) < a2 || this.u.f(d) < a2) {
                        a(vVar, 0, i4);
                        return;
                    }
                }
                return;
            }
            int i5 = e - 1;
            for (int i6 = i5; i6 >= 0; i6--) {
                View d2 = d(i6);
                if (this.u.d(d2) < a2 || this.u.f(d2) < a2) {
                    a(vVar, i5, i6);
                    return;
                }
            }
        }
    }

    /* access modifiers changed from: package-private */
    public View b(boolean z2, boolean z3) {
        if (this.x) {
            return a(e() - 1, -1, z2, z3);
        }
        return a(0, e(), z2, z3);
    }

    private int a(int i2, RecyclerView.v vVar, RecyclerView.z zVar, boolean z2) {
        int b2;
        int b3 = this.u.b() - i2;
        if (b3 <= 0) {
            return 0;
        }
        int i3 = -c(-b3, vVar, zVar);
        int i4 = i2 + i3;
        if (!z2 || (b2 = this.u.b() - i4) <= 0) {
            return i3;
        }
        this.u.a(b2);
        return b2 + i3;
    }

    private void a(a aVar) {
        g(aVar.b, aVar.c);
    }

    public int a(int i2, RecyclerView.v vVar, RecyclerView.z zVar) {
        if (this.s == 1) {
            return 0;
        }
        return c(i2, vVar, zVar);
    }

    public int a(RecyclerView.z zVar) {
        return i(zVar);
    }

    private void a(int i2, int i3, boolean z2, RecyclerView.z zVar) {
        int i4;
        this.t.m = N();
        this.t.f773f = i2;
        int[] iArr = this.H;
        boolean z3 = false;
        iArr[0] = 0;
        int i5 = 1;
        iArr[1] = 0;
        a(zVar, iArr);
        int max = Math.max(0, this.H[0]);
        int max2 = Math.max(0, this.H[1]);
        if (i2 == 1) {
            z3 = true;
        }
        this.t.f775h = z3 ? max2 : max;
        c cVar = this.t;
        if (!z3) {
            max = max2;
        }
        cVar.f776i = max;
        if (z3) {
            this.t.f775h += this.u.c();
            View S = S();
            c cVar2 = this.t;
            if (this.x) {
                i5 = -1;
            }
            cVar2.e = i5;
            c cVar3 = this.t;
            int l = l(S);
            c cVar4 = this.t;
            cVar3.d = l + cVar4.e;
            cVar4.b = this.u.a(S);
            i4 = this.u.a(S) - this.u.b();
        } else {
            View T = T();
            this.t.f775h += this.u.f();
            c cVar5 = this.t;
            if (!this.x) {
                i5 = -1;
            }
            cVar5.e = i5;
            c cVar6 = this.t;
            int l2 = l(T);
            c cVar7 = this.t;
            cVar6.d = l2 + cVar7.e;
            cVar7.b = this.u.d(T);
            i4 = (-this.u.d(T)) + this.u.f();
        }
        c cVar8 = this.t;
        cVar8.c = i3;
        if (z2) {
            cVar8.c = i3 - i4;
        }
        this.t.f774g = i4;
    }

    public int e(RecyclerView.z zVar) {
        return j(zVar);
    }

    /* access modifiers changed from: package-private */
    public View e(int i2, int i3) {
        int i4;
        int i5;
        F();
        if ((i3 > i2 ? 1 : i3 < i2 ? (char) 65535 : 0) == 0) {
            return d(i2);
        }
        if (this.u.d(d(i2)) < this.u.f()) {
            i5 = 16644;
            i4 = 16388;
        } else {
            i5 = 4161;
            i4 = 4097;
        }
        if (this.s == 0) {
            return this.e.a(i2, i3, i5, i4);
        }
        return this.f790f.a(i2, i3, i5, i4);
    }

    /* access modifiers changed from: package-private */
    public void a(RecyclerView.z zVar, c cVar, RecyclerView.o.c cVar2) {
        int i2 = cVar.d;
        if (i2 >= 0 && i2 < zVar.a()) {
            cVar2.a(i2, Math.max(0, cVar.f774g));
        }
    }

    public void a(int i2, RecyclerView.o.c cVar) {
        boolean z2;
        int i3;
        SavedState savedState = this.D;
        int i4 = -1;
        if (savedState == null || !savedState.a()) {
            U();
            z2 = this.x;
            i3 = this.A;
            if (i3 == -1) {
                i3 = z2 ? i2 - 1 : 0;
            }
        } else {
            SavedState savedState2 = this.D;
            z2 = savedState2.f772g;
            i3 = savedState2.e;
        }
        if (!z2) {
            i4 = 1;
        }
        for (int i5 = 0; i5 < this.G && i3 >= 0 && i3 < i2; i5++) {
            cVar.a(i3, 0);
            i3 += i4;
        }
    }

    public void a(int i2, int i3, RecyclerView.z zVar, RecyclerView.o.c cVar) {
        if (this.s != 0) {
            i2 = i3;
        }
        if (e() != 0 && i2 != 0) {
            F();
            a(i2 > 0 ? 1 : -1, Math.abs(i2), true, zVar);
            a(zVar, this.t, cVar);
        }
    }

    public void a(String str) {
        if (this.D == null) {
            super.a(str);
        }
    }

    private void a(RecyclerView.v vVar, int i2, int i3) {
        if (i2 != i3) {
            if (i3 > i2) {
                for (int i4 = i3 - 1; i4 >= i2; i4--) {
                    a(i4, vVar);
                }
                return;
            }
            while (i2 > i3) {
                a(i2, vVar);
                i2--;
            }
        }
    }

    private void a(RecyclerView.v vVar, c cVar) {
        if (cVar.a && !cVar.m) {
            int i2 = cVar.f774g;
            int i3 = cVar.f776i;
            if (cVar.f773f == -1) {
                b(vVar, i2, i3);
            } else {
                c(vVar, i2, i3);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public int a(RecyclerView.v vVar, c cVar, RecyclerView.z zVar, boolean z2) {
        int i2 = cVar.c;
        int i3 = cVar.f774g;
        if (i3 != Integer.MIN_VALUE) {
            if (i2 < 0) {
                cVar.f774g = i3 + i2;
            }
            a(vVar, cVar);
        }
        int i4 = cVar.c + cVar.f775h;
        b bVar = this.F;
        while (true) {
            if ((!cVar.m && i4 <= 0) || !cVar.a(zVar)) {
                break;
            }
            bVar.a();
            a(vVar, zVar, cVar, bVar);
            if (!bVar.b) {
                cVar.b += bVar.a * cVar.f773f;
                if (!bVar.c || cVar.l != null || !zVar.d()) {
                    int i5 = cVar.c;
                    int i6 = bVar.a;
                    cVar.c = i5 - i6;
                    i4 -= i6;
                }
                int i7 = cVar.f774g;
                if (i7 != Integer.MIN_VALUE) {
                    int i8 = i7 + bVar.a;
                    cVar.f774g = i8;
                    int i9 = cVar.c;
                    if (i9 < 0) {
                        cVar.f774g = i8 + i9;
                    }
                    a(vVar, cVar);
                }
                if (z2 && bVar.d) {
                    break;
                }
            } else {
                break;
            }
        }
        return i2 - cVar.c;
    }

    /* access modifiers changed from: package-private */
    public void a(RecyclerView.v vVar, RecyclerView.z zVar, c cVar, b bVar) {
        int i2;
        int i3;
        int i4;
        int i5;
        int i6;
        View a2 = cVar.a(vVar);
        if (a2 == null) {
            bVar.b = true;
            return;
        }
        RecyclerView.p pVar = (RecyclerView.p) a2.getLayoutParams();
        if (cVar.l == null) {
            if (this.x == (cVar.f773f == -1)) {
                b(a2);
            } else {
                b(a2, 0);
            }
        } else {
            if (this.x == (cVar.f773f == -1)) {
                a(a2);
            } else {
                a(a2, 0);
            }
        }
        a(a2, 0, 0);
        bVar.a = this.u.b(a2);
        if (this.s == 1) {
            if (L()) {
                i6 = r() - p();
                i5 = i6 - this.u.c(a2);
            } else {
                i5 = o();
                i6 = this.u.c(a2) + i5;
            }
            if (cVar.f773f == -1) {
                int i7 = cVar.b;
                i2 = i7;
                i3 = i6;
                i4 = i7 - bVar.a;
            } else {
                int i8 = cVar.b;
                i4 = i8;
                i3 = i6;
                i2 = bVar.a + i8;
            }
        } else {
            int q = q();
            int c2 = this.u.c(a2) + q;
            if (cVar.f773f == -1) {
                int i9 = cVar.b;
                i3 = i9;
                i4 = q;
                i2 = c2;
                i5 = i9 - bVar.a;
            } else {
                int i10 = cVar.b;
                i4 = q;
                i3 = bVar.a + i10;
                i2 = c2;
                i5 = i10;
            }
        }
        a(a2, i5, i4, i3, i2);
        if (pVar.c() || pVar.b()) {
            bVar.c = true;
        }
        bVar.d = a2.hasFocusable();
    }

    /* access modifiers changed from: package-private */
    public View a(boolean z2, boolean z3) {
        if (this.x) {
            return a(0, e(), z2, z3);
        }
        return a(e() - 1, -1, z2, z3);
    }

    /* access modifiers changed from: package-private */
    public View a(RecyclerView.v vVar, RecyclerView.z zVar, int i2, int i3, int i4) {
        F();
        int f2 = this.u.f();
        int b2 = this.u.b();
        int i5 = i3 > i2 ? 1 : -1;
        View view = null;
        View view2 = null;
        while (i2 != i3) {
            View d = d(i2);
            int l = l(d);
            if (l >= 0 && l < i4) {
                if (((RecyclerView.p) d.getLayoutParams()).c()) {
                    if (view2 == null) {
                        view2 = d;
                    }
                } else if (this.u.d(d) < b2 && this.u.a(d) >= f2) {
                    return d;
                } else {
                    if (view == null) {
                        view = d;
                    }
                }
            }
            i2 += i5;
        }
        return view != null ? view : view2;
    }

    /* access modifiers changed from: package-private */
    public View a(int i2, int i3, boolean z2, boolean z3) {
        F();
        int i4 = 320;
        int i5 = z2 ? 24579 : 320;
        if (!z3) {
            i4 = 0;
        }
        if (this.s == 0) {
            return this.e.a(i2, i3, i5, i4);
        }
        return this.f790f.a(i2, i3, i5, i4);
    }

    public View a(View view, int i2, RecyclerView.v vVar, RecyclerView.z zVar) {
        int j2;
        View view2;
        View view3;
        U();
        if (e() == 0 || (j2 = j(i2)) == Integer.MIN_VALUE) {
            return null;
        }
        F();
        a(j2, (int) (((float) this.u.g()) * 0.33333334f), false, zVar);
        c cVar = this.t;
        cVar.f774g = Integer.MIN_VALUE;
        cVar.a = false;
        a(vVar, cVar, zVar, true);
        if (j2 == -1) {
            view2 = R();
        } else {
            view2 = Q();
        }
        if (j2 == -1) {
            view3 = T();
        } else {
            view3 = S();
        }
        if (!view3.hasFocusable()) {
            return view2;
        }
        if (view2 == null) {
            return null;
        }
        return view3;
    }

    public void a(View view, View view2, int i2, int i3) {
        a("Cannot drop a view during a scroll or layout calculation");
        F();
        U();
        int l = l(view);
        int l2 = l(view2);
        char c2 = l < l2 ? (char) 1 : 65535;
        if (this.x) {
            if (c2 == 1) {
                f(l2, this.u.b() - (this.u.d(view2) + this.u.b(view)));
            } else {
                f(l2, this.u.b() - this.u.a(view2));
            }
        } else if (c2 == 65535) {
            f(l2, this.u.d(view2));
        } else {
            f(l2, this.u.a(view2) - this.u.b(view));
        }
    }
}
