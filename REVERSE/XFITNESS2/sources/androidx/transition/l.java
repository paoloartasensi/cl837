package androidx.transition;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.TimeInterpolator;
import android.graphics.Path;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import androidx.core.h.v;
import com.jeremyliao.liveeventbus.BuildConfig;
import g.a.g;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: Transition */
public abstract class l implements Cloneable {
    private static final int[] K = {2, 1, 3, 4};
    private static final g L = new a();
    private static ThreadLocal<g.a.a<Animator, d>> M = new ThreadLocal<>();
    ArrayList<Animator> A = new ArrayList<>();
    private int B = 0;
    private boolean C = false;
    private boolean D = false;
    private ArrayList<f> E = null;
    private ArrayList<Animator> F = new ArrayList<>();
    o G;
    private e H;
    private g.a.a<String, String> I;
    private g J = L;
    private String e = getClass().getName();

    /* renamed from: f  reason: collision with root package name */
    private long f897f = -1;

    /* renamed from: g  reason: collision with root package name */
    long f898g = -1;

    /* renamed from: h  reason: collision with root package name */
    private TimeInterpolator f899h = null;

    /* renamed from: i  reason: collision with root package name */
    ArrayList<Integer> f900i = new ArrayList<>();

    /* renamed from: j  reason: collision with root package name */
    ArrayList<View> f901j = new ArrayList<>();
    private ArrayList<String> k = null;
    private ArrayList<Class> l = null;
    private ArrayList<Integer> m = null;
    private ArrayList<View> n = null;
    private ArrayList<Class> o = null;
    private ArrayList<String> p = null;
    private ArrayList<Integer> q = null;
    private ArrayList<View> r = null;
    private ArrayList<Class> s = null;
    private s t = new s();
    private s u = new s();
    p v = null;
    private int[] w = K;
    private ArrayList<r> x;
    private ArrayList<r> y;
    boolean z = false;

    /* compiled from: Transition */
    static class a extends g {
        a() {
        }

        public Path a(float f2, float f3, float f4, float f5) {
            Path path = new Path();
            path.moveTo(f2, f3);
            path.lineTo(f4, f5);
            return path;
        }
    }

    /* compiled from: Transition */
    class b extends AnimatorListenerAdapter {
        final /* synthetic */ g.a.a a;

        b(g.a.a aVar) {
            this.a = aVar;
        }

        public void onAnimationEnd(Animator animator) {
            this.a.remove(animator);
            l.this.A.remove(animator);
        }

        public void onAnimationStart(Animator animator) {
            l.this.A.add(animator);
        }
    }

    /* compiled from: Transition */
    class c extends AnimatorListenerAdapter {
        c() {
        }

        public void onAnimationEnd(Animator animator) {
            l.this.b();
            animator.removeListener(this);
        }
    }

    /* compiled from: Transition */
    private static class d {
        View a;
        String b;
        r c;
        k0 d;
        l e;

        d(View view, String str, l lVar, k0 k0Var, r rVar) {
            this.a = view;
            this.b = str;
            this.c = rVar;
            this.d = k0Var;
            this.e = lVar;
        }
    }

    /* compiled from: Transition */
    public static abstract class e {
    }

    /* compiled from: Transition */
    public interface f {
        void a(l lVar);

        void b(l lVar);

        void c(l lVar);

        void d(l lVar);
    }

    private void c(View view, boolean z2) {
        if (view != null) {
            int id = view.getId();
            ArrayList<Integer> arrayList = this.m;
            if (arrayList == null || !arrayList.contains(Integer.valueOf(id))) {
                ArrayList<View> arrayList2 = this.n;
                if (arrayList2 == null || !arrayList2.contains(view)) {
                    ArrayList<Class> arrayList3 = this.o;
                    if (arrayList3 != null) {
                        int size = arrayList3.size();
                        int i2 = 0;
                        while (i2 < size) {
                            if (!this.o.get(i2).isInstance(view)) {
                                i2++;
                            } else {
                                return;
                            }
                        }
                    }
                    if (view.getParent() instanceof ViewGroup) {
                        r rVar = new r();
                        rVar.b = view;
                        if (z2) {
                            c(rVar);
                        } else {
                            a(rVar);
                        }
                        rVar.c.add(this);
                        b(rVar);
                        if (z2) {
                            a(this.t, view, rVar);
                        } else {
                            a(this.u, view, rVar);
                        }
                    }
                    if (view instanceof ViewGroup) {
                        ArrayList<Integer> arrayList4 = this.q;
                        if (arrayList4 == null || !arrayList4.contains(Integer.valueOf(id))) {
                            ArrayList<View> arrayList5 = this.r;
                            if (arrayList5 == null || !arrayList5.contains(view)) {
                                ArrayList<Class> arrayList6 = this.s;
                                if (arrayList6 != null) {
                                    int size2 = arrayList6.size();
                                    int i3 = 0;
                                    while (i3 < size2) {
                                        if (!this.s.get(i3).isInstance(view)) {
                                            i3++;
                                        } else {
                                            return;
                                        }
                                    }
                                }
                                ViewGroup viewGroup = (ViewGroup) view;
                                for (int i4 = 0; i4 < viewGroup.getChildCount(); i4++) {
                                    c(viewGroup.getChildAt(i4), z2);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private static g.a.a<Animator, d> r() {
        g.a.a<Animator, d> aVar = M.get();
        if (aVar != null) {
            return aVar;
        }
        g.a.a<Animator, d> aVar2 = new g.a.a<>();
        M.set(aVar2);
        return aVar2;
    }

    public Animator a(ViewGroup viewGroup, r rVar, r rVar2) {
        return null;
    }

    public l a(long j2) {
        this.f898g = j2;
        return this;
    }

    public abstract void a(r rVar);

    public l b(long j2) {
        this.f897f = j2;
        return this;
    }

    public abstract void c(r rVar);

    public long d() {
        return this.f898g;
    }

    public void e(View view) {
        if (this.C) {
            if (!this.D) {
                g.a.a<Animator, d> r2 = r();
                int size = r2.size();
                k0 d2 = c0.d(view);
                for (int i2 = size - 1; i2 >= 0; i2--) {
                    d d3 = r2.d(i2);
                    if (d3.a != null && d2.equals(d3.d)) {
                        a.b(r2.b(i2));
                    }
                }
                ArrayList<f> arrayList = this.E;
                if (arrayList != null && arrayList.size() > 0) {
                    ArrayList arrayList2 = (ArrayList) this.E.clone();
                    int size2 = arrayList2.size();
                    for (int i3 = 0; i3 < size2; i3++) {
                        ((f) arrayList2.get(i3)).b(this);
                    }
                }
            }
            this.C = false;
        }
    }

    public TimeInterpolator f() {
        return this.f899h;
    }

    public String g() {
        return this.e;
    }

    public g h() {
        return this.J;
    }

    public o i() {
        return this.G;
    }

    public long j() {
        return this.f897f;
    }

    public List<Integer> k() {
        return this.f900i;
    }

    public List<String> l() {
        return this.k;
    }

    public List<Class> m() {
        return this.l;
    }

    public List<View> n() {
        return this.f901j;
    }

    public String[] o() {
        return null;
    }

    /* access modifiers changed from: protected */
    public void p() {
        q();
        g.a.a<Animator, d> r2 = r();
        Iterator<Animator> it = this.F.iterator();
        while (it.hasNext()) {
            Animator next = it.next();
            if (r2.containsKey(next)) {
                q();
                a(next, r2);
            }
        }
        this.F.clear();
        b();
    }

    /* access modifiers changed from: protected */
    public void q() {
        if (this.B == 0) {
            ArrayList<f> arrayList = this.E;
            if (arrayList != null && arrayList.size() > 0) {
                ArrayList arrayList2 = (ArrayList) this.E.clone();
                int size = arrayList2.size();
                for (int i2 = 0; i2 < size; i2++) {
                    ((f) arrayList2.get(i2)).c(this);
                }
            }
            this.D = false;
        }
        this.B++;
    }

    public String toString() {
        return a(BuildConfig.FLAVOR);
    }

    private void b(g.a.a<View, r> aVar, g.a.a<View, r> aVar2) {
        r remove;
        View view;
        for (int size = aVar.size() - 1; size >= 0; size--) {
            View b2 = aVar.b(size);
            if (!(b2 == null || !b(b2) || (remove = aVar2.remove(b2)) == null || (view = remove.b) == null || !b(view))) {
                this.x.add(aVar.c(size));
                this.y.add(remove);
            }
        }
    }

    public l a(TimeInterpolator timeInterpolator) {
        this.f899h = timeInterpolator;
        return this;
    }

    public l clone() {
        try {
            l lVar = (l) super.clone();
            lVar.F = new ArrayList<>();
            lVar.t = new s();
            lVar.u = new s();
            lVar.x = null;
            lVar.y = null;
            return lVar;
        } catch (CloneNotSupportedException unused) {
            return null;
        }
    }

    public l d(View view) {
        this.f901j.remove(view);
        return this;
    }

    private void a(g.a.a<View, r> aVar, g.a.a<View, r> aVar2, g.a.d<View> dVar, g.a.d<View> dVar2) {
        View c2;
        int e2 = dVar.e();
        for (int i2 = 0; i2 < e2; i2++) {
            View c3 = dVar.c(i2);
            if (c3 != null && b(c3) && (c2 = dVar2.c(dVar.a(i2))) != null && b(c2)) {
                r rVar = aVar.get(c3);
                r rVar2 = aVar2.get(c2);
                if (!(rVar == null || rVar2 == null)) {
                    this.x.add(rVar);
                    this.y.add(rVar2);
                    aVar.remove(c3);
                    aVar2.remove(c2);
                }
            }
        }
    }

    /* access modifiers changed from: package-private */
    public boolean b(View view) {
        ArrayList<Class> arrayList;
        ArrayList<String> arrayList2;
        int id = view.getId();
        ArrayList<Integer> arrayList3 = this.m;
        if (arrayList3 != null && arrayList3.contains(Integer.valueOf(id))) {
            return false;
        }
        ArrayList<View> arrayList4 = this.n;
        if (arrayList4 != null && arrayList4.contains(view)) {
            return false;
        }
        ArrayList<Class> arrayList5 = this.o;
        if (arrayList5 != null) {
            int size = arrayList5.size();
            for (int i2 = 0; i2 < size; i2++) {
                if (this.o.get(i2).isInstance(view)) {
                    return false;
                }
            }
        }
        if (this.p != null && v.v(view) != null && this.p.contains(v.v(view))) {
            return false;
        }
        if ((this.f900i.size() == 0 && this.f901j.size() == 0 && (((arrayList = this.l) == null || arrayList.isEmpty()) && ((arrayList2 = this.k) == null || arrayList2.isEmpty()))) || this.f900i.contains(Integer.valueOf(id)) || this.f901j.contains(view)) {
            return true;
        }
        ArrayList<String> arrayList6 = this.k;
        if (arrayList6 != null && arrayList6.contains(v.v(view))) {
            return true;
        }
        if (this.l != null) {
            for (int i3 = 0; i3 < this.l.size(); i3++) {
                if (this.l.get(i3).isInstance(view)) {
                    return true;
                }
            }
        }
        return false;
    }

    private void a(g.a.a<View, r> aVar, g.a.a<View, r> aVar2, SparseArray<View> sparseArray, SparseArray<View> sparseArray2) {
        View view;
        int size = sparseArray.size();
        for (int i2 = 0; i2 < size; i2++) {
            View valueAt = sparseArray.valueAt(i2);
            if (valueAt != null && b(valueAt) && (view = sparseArray2.get(sparseArray.keyAt(i2))) != null && b(view)) {
                r rVar = aVar.get(valueAt);
                r rVar2 = aVar2.get(view);
                if (!(rVar == null || rVar2 == null)) {
                    this.x.add(rVar);
                    this.y.add(rVar2);
                    aVar.remove(valueAt);
                    aVar2.remove(view);
                }
            }
        }
    }

    public e e() {
        return this.H;
    }

    private void a(g.a.a<View, r> aVar, g.a.a<View, r> aVar2, g.a.a<String, View> aVar3, g.a.a<String, View> aVar4) {
        View view;
        int size = aVar3.size();
        for (int i2 = 0; i2 < size; i2++) {
            View d2 = aVar3.d(i2);
            if (d2 != null && b(d2) && (view = aVar4.get(aVar3.b(i2))) != null && b(view)) {
                r rVar = aVar.get(d2);
                r rVar2 = aVar2.get(view);
                if (!(rVar == null || rVar2 == null)) {
                    this.x.add(rVar);
                    this.y.add(rVar2);
                    aVar.remove(d2);
                    aVar2.remove(view);
                }
            }
        }
    }

    public void c(View view) {
        if (!this.D) {
            g.a.a<Animator, d> r2 = r();
            int size = r2.size();
            k0 d2 = c0.d(view);
            for (int i2 = size - 1; i2 >= 0; i2--) {
                d d3 = r2.d(i2);
                if (d3.a != null && d2.equals(d3.d)) {
                    a.a(r2.b(i2));
                }
            }
            ArrayList<f> arrayList = this.E;
            if (arrayList != null && arrayList.size() > 0) {
                ArrayList arrayList2 = (ArrayList) this.E.clone();
                int size2 = arrayList2.size();
                for (int i3 = 0; i3 < size2; i3++) {
                    ((f) arrayList2.get(i3)).a(this);
                }
            }
            this.C = true;
        }
    }

    public r b(View view, boolean z2) {
        p pVar = this.v;
        if (pVar != null) {
            return pVar.b(view, z2);
        }
        return (z2 ? this.t : this.u).a.get(view);
    }

    /* access modifiers changed from: protected */
    public void b() {
        int i2 = this.B - 1;
        this.B = i2;
        if (i2 == 0) {
            ArrayList<f> arrayList = this.E;
            if (arrayList != null && arrayList.size() > 0) {
                ArrayList arrayList2 = (ArrayList) this.E.clone();
                int size = arrayList2.size();
                for (int i3 = 0; i3 < size; i3++) {
                    ((f) arrayList2.get(i3)).d(this);
                }
            }
            for (int i4 = 0; i4 < this.t.c.e(); i4++) {
                View c2 = this.t.c.c(i4);
                if (c2 != null) {
                    v.b(c2, false);
                }
            }
            for (int i5 = 0; i5 < this.u.c.e(); i5++) {
                View c3 = this.u.c.c(i5);
                if (c3 != null) {
                    v.b(c3, false);
                }
            }
            this.D = true;
        }
    }

    private void a(g.a.a<View, r> aVar, g.a.a<View, r> aVar2) {
        for (int i2 = 0; i2 < aVar.size(); i2++) {
            r d2 = aVar.d(i2);
            if (b(d2.b)) {
                this.x.add(d2);
                this.y.add((Object) null);
            }
        }
        for (int i3 = 0; i3 < aVar2.size(); i3++) {
            r d3 = aVar2.d(i3);
            if (b(d3.b)) {
                this.y.add(d3);
                this.x.add((Object) null);
            }
        }
    }

    public l b(f fVar) {
        ArrayList<f> arrayList = this.E;
        if (arrayList == null) {
            return this;
        }
        arrayList.remove(fVar);
        if (this.E.size() == 0) {
            this.E = null;
        }
        return this;
    }

    private void a(s sVar, s sVar2) {
        g.a.a aVar = new g.a.a((g) sVar.a);
        g.a.a aVar2 = new g.a.a((g) sVar2.a);
        int i2 = 0;
        while (true) {
            int[] iArr = this.w;
            if (i2 < iArr.length) {
                int i3 = iArr[i2];
                if (i3 == 1) {
                    b((g.a.a<View, r>) aVar, (g.a.a<View, r>) aVar2);
                } else if (i3 == 2) {
                    a((g.a.a<View, r>) aVar, (g.a.a<View, r>) aVar2, sVar.d, sVar2.d);
                } else if (i3 == 3) {
                    a((g.a.a<View, r>) aVar, (g.a.a<View, r>) aVar2, sVar.b, sVar2.b);
                } else if (i3 == 4) {
                    a((g.a.a<View, r>) aVar, (g.a.a<View, r>) aVar2, sVar.c, sVar2.c);
                }
                i2++;
            } else {
                a((g.a.a<View, r>) aVar, (g.a.a<View, r>) aVar2);
                return;
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void b(r rVar) {
        String[] a2;
        if (this.G != null && !rVar.a.isEmpty() && (a2 = this.G.a()) != null) {
            boolean z2 = false;
            int i2 = 0;
            while (true) {
                if (i2 >= a2.length) {
                    z2 = true;
                    break;
                } else if (!rVar.a.containsKey(a2[i2])) {
                    break;
                } else {
                    i2++;
                }
            }
            if (!z2) {
                this.G.a(rVar);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void a(ViewGroup viewGroup, s sVar, s sVar2, ArrayList<r> arrayList, ArrayList<r> arrayList2) {
        int i2;
        int i3;
        Animator a2;
        View view;
        Animator animator;
        r rVar;
        r rVar2;
        Animator animator2;
        ViewGroup viewGroup2 = viewGroup;
        g.a.a<Animator, d> r2 = r();
        SparseIntArray sparseIntArray = new SparseIntArray();
        int size = arrayList.size();
        long j2 = Long.MAX_VALUE;
        int i4 = 0;
        while (i4 < size) {
            r rVar3 = arrayList.get(i4);
            r rVar4 = arrayList2.get(i4);
            if (rVar3 != null && !rVar3.c.contains(this)) {
                rVar3 = null;
            }
            if (rVar4 != null && !rVar4.c.contains(this)) {
                rVar4 = null;
            }
            if (!(rVar3 == null && rVar4 == null)) {
                if ((rVar3 == null || rVar4 == null || a(rVar3, rVar4)) && (a2 = a(viewGroup2, rVar3, rVar4)) != null) {
                    if (rVar4 != null) {
                        view = rVar4.b;
                        String[] o2 = o();
                        if (view != null && o2 != null && o2.length > 0) {
                            rVar2 = new r();
                            rVar2.b = view;
                            Animator animator3 = a2;
                            i3 = size;
                            r rVar5 = sVar2.a.get(view);
                            if (rVar5 != null) {
                                int i5 = 0;
                                while (i5 < o2.length) {
                                    rVar2.a.put(o2[i5], rVar5.a.get(o2[i5]));
                                    i5++;
                                    ArrayList<r> arrayList3 = arrayList2;
                                    i4 = i4;
                                    rVar5 = rVar5;
                                }
                            }
                            i2 = i4;
                            int size2 = r2.size();
                            int i6 = 0;
                            while (true) {
                                if (i6 >= size2) {
                                    animator2 = animator3;
                                    break;
                                }
                                d dVar = r2.get(r2.b(i6));
                                if (dVar.c != null && dVar.a == view && dVar.b.equals(g()) && dVar.c.equals(rVar2)) {
                                    animator2 = null;
                                    break;
                                }
                                i6++;
                            }
                        } else {
                            i3 = size;
                            i2 = i4;
                            animator2 = a2;
                            rVar2 = null;
                        }
                        animator = animator2;
                        rVar = rVar2;
                    } else {
                        i3 = size;
                        i2 = i4;
                        view = rVar3.b;
                        animator = a2;
                        rVar = null;
                    }
                    if (animator != null) {
                        o oVar = this.G;
                        if (oVar != null) {
                            long a3 = oVar.a(viewGroup2, this, rVar3, rVar4);
                            sparseIntArray.put(this.F.size(), (int) a3);
                            j2 = Math.min(a3, j2);
                        }
                        r2.put(animator, new d(view, g(), this, c0.d(viewGroup), rVar));
                        this.F.add(animator);
                        j2 = j2;
                    }
                    i4 = i2 + 1;
                    size = i3;
                }
            }
            i3 = size;
            i2 = i4;
            i4 = i2 + 1;
            size = i3;
        }
        if (sparseIntArray.size() != 0) {
            for (int i7 = 0; i7 < sparseIntArray.size(); i7++) {
                Animator animator4 = this.F.get(sparseIntArray.keyAt(i7));
                animator4.setStartDelay((((long) sparseIntArray.valueAt(i7)) - j2) + animator4.getStartDelay());
            }
        }
    }

    private void a(Animator animator, g.a.a<Animator, d> aVar) {
        if (animator != null) {
            animator.addListener(new b(aVar));
            a(animator);
        }
    }

    public l a(View view) {
        this.f901j.add(view);
        return this;
    }

    /* access modifiers changed from: package-private */
    public void a(ViewGroup viewGroup, boolean z2) {
        g.a.a<String, String> aVar;
        ArrayList<String> arrayList;
        ArrayList<Class> arrayList2;
        a(z2);
        if ((this.f900i.size() > 0 || this.f901j.size() > 0) && (((arrayList = this.k) == null || arrayList.isEmpty()) && ((arrayList2 = this.l) == null || arrayList2.isEmpty()))) {
            for (int i2 = 0; i2 < this.f900i.size(); i2++) {
                View findViewById = viewGroup.findViewById(this.f900i.get(i2).intValue());
                if (findViewById != null) {
                    r rVar = new r();
                    rVar.b = findViewById;
                    if (z2) {
                        c(rVar);
                    } else {
                        a(rVar);
                    }
                    rVar.c.add(this);
                    b(rVar);
                    if (z2) {
                        a(this.t, findViewById, rVar);
                    } else {
                        a(this.u, findViewById, rVar);
                    }
                }
            }
            for (int i3 = 0; i3 < this.f901j.size(); i3++) {
                View view = this.f901j.get(i3);
                r rVar2 = new r();
                rVar2.b = view;
                if (z2) {
                    c(rVar2);
                } else {
                    a(rVar2);
                }
                rVar2.c.add(this);
                b(rVar2);
                if (z2) {
                    a(this.t, view, rVar2);
                } else {
                    a(this.u, view, rVar2);
                }
            }
        } else {
            c(viewGroup, z2);
        }
        if (!z2 && (aVar = this.I) != null) {
            int size = aVar.size();
            ArrayList arrayList3 = new ArrayList(size);
            for (int i4 = 0; i4 < size; i4++) {
                arrayList3.add(this.t.d.remove(this.I.b(i4)));
            }
            for (int i5 = 0; i5 < size; i5++) {
                View view2 = (View) arrayList3.get(i5);
                if (view2 != null) {
                    this.t.d.put(this.I.d(i5), view2);
                }
            }
        }
    }

    private static void a(s sVar, View view, r rVar) {
        sVar.a.put(view, rVar);
        int id = view.getId();
        if (id >= 0) {
            if (sVar.b.indexOfKey(id) >= 0) {
                sVar.b.put(id, (Object) null);
            } else {
                sVar.b.put(id, view);
            }
        }
        String v2 = v.v(view);
        if (v2 != null) {
            if (sVar.d.containsKey(v2)) {
                sVar.d.put(v2, null);
            } else {
                sVar.d.put(v2, view);
            }
        }
        if (view.getParent() instanceof ListView) {
            ListView listView = (ListView) view.getParent();
            if (listView.getAdapter().hasStableIds()) {
                long itemIdAtPosition = listView.getItemIdAtPosition(listView.getPositionForView(view));
                if (sVar.c.d(itemIdAtPosition) >= 0) {
                    View c2 = sVar.c.c(itemIdAtPosition);
                    if (c2 != null) {
                        v.b(c2, false);
                        sVar.c.c(itemIdAtPosition, null);
                        return;
                    }
                    return;
                }
                v.b(view, true);
                sVar.c.c(itemIdAtPosition, view);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void a(boolean z2) {
        if (z2) {
            this.t.a.clear();
            this.t.b.clear();
            this.t.c.b();
            return;
        }
        this.u.a.clear();
        this.u.b.clear();
        this.u.c.b();
    }

    /* access modifiers changed from: package-private */
    public r a(View view, boolean z2) {
        p pVar = this.v;
        if (pVar != null) {
            return pVar.a(view, z2);
        }
        ArrayList<r> arrayList = z2 ? this.x : this.y;
        if (arrayList == null) {
            return null;
        }
        int size = arrayList.size();
        int i2 = -1;
        int i3 = 0;
        while (true) {
            if (i3 >= size) {
                break;
            }
            r rVar = arrayList.get(i3);
            if (rVar == null) {
                return null;
            }
            if (rVar.b == view) {
                i2 = i3;
                break;
            }
            i3++;
        }
        if (i2 < 0) {
            return null;
        }
        return (z2 ? this.y : this.x).get(i2);
    }

    /* access modifiers changed from: package-private */
    public void a(ViewGroup viewGroup) {
        d dVar;
        this.x = new ArrayList<>();
        this.y = new ArrayList<>();
        a(this.t, this.u);
        g.a.a<Animator, d> r2 = r();
        int size = r2.size();
        k0 d2 = c0.d(viewGroup);
        for (int i2 = size - 1; i2 >= 0; i2--) {
            Animator b2 = r2.b(i2);
            if (!(b2 == null || (dVar = r2.get(b2)) == null || dVar.a == null || !d2.equals(dVar.d))) {
                r rVar = dVar.c;
                View view = dVar.a;
                r b3 = b(view, true);
                r a2 = a(view, true);
                if (b3 == null && a2 == null) {
                    a2 = this.u.a.get(view);
                }
                if (!(b3 == null && a2 == null) && dVar.e.a(rVar, a2)) {
                    if (b2.isRunning() || b2.isStarted()) {
                        b2.cancel();
                    } else {
                        r2.remove(b2);
                    }
                }
            }
        }
        a(viewGroup, this.t, this.u, this.x, this.y);
        p();
    }

    public boolean a(r rVar, r rVar2) {
        if (rVar == null || rVar2 == null) {
            return false;
        }
        String[] o2 = o();
        if (o2 != null) {
            int length = o2.length;
            int i2 = 0;
            while (i2 < length) {
                if (!a(rVar, rVar2, o2[i2])) {
                    i2++;
                }
            }
            return false;
        }
        for (String a2 : rVar.a.keySet()) {
            if (a(rVar, rVar2, a2)) {
            }
        }
        return false;
        return true;
    }

    private static boolean a(r rVar, r rVar2, String str) {
        Object obj = rVar.a.get(str);
        Object obj2 = rVar2.a.get(str);
        if (obj == null && obj2 == null) {
            return false;
        }
        if (obj == null || obj2 == null) {
            return true;
        }
        return true ^ obj.equals(obj2);
    }

    /* access modifiers changed from: protected */
    public void a(Animator animator) {
        if (animator == null) {
            b();
            return;
        }
        if (d() >= 0) {
            animator.setDuration(d());
        }
        if (j() >= 0) {
            animator.setStartDelay(j());
        }
        if (f() != null) {
            animator.setInterpolator(f());
        }
        animator.addListener(new c());
        animator.start();
    }

    public l a(f fVar) {
        if (this.E == null) {
            this.E = new ArrayList<>();
        }
        this.E.add(fVar);
        return this;
    }

    public void a(g gVar) {
        if (gVar == null) {
            this.J = L;
        } else {
            this.J = gVar;
        }
    }

    public void a(e eVar) {
        this.H = eVar;
    }

    public void a(o oVar) {
        this.G = oVar;
    }

    /* access modifiers changed from: package-private */
    public String a(String str) {
        String str2 = str + getClass().getSimpleName() + "@" + Integer.toHexString(hashCode()) + ": ";
        if (this.f898g != -1) {
            str2 = str2 + "dur(" + this.f898g + ") ";
        }
        if (this.f897f != -1) {
            str2 = str2 + "dly(" + this.f897f + ") ";
        }
        if (this.f899h != null) {
            str2 = str2 + "interp(" + this.f899h + ") ";
        }
        if (this.f900i.size() <= 0 && this.f901j.size() <= 0) {
            return str2;
        }
        String str3 = str2 + "tgts(";
        if (this.f900i.size() > 0) {
            for (int i2 = 0; i2 < this.f900i.size(); i2++) {
                if (i2 > 0) {
                    str3 = str3 + ", ";
                }
                str3 = str3 + this.f900i.get(i2);
            }
        }
        if (this.f901j.size() > 0) {
            for (int i3 = 0; i3 < this.f901j.size(); i3++) {
                if (i3 > 0) {
                    str3 = str3 + ", ";
                }
                str3 = str3 + this.f901j.get(i3);
            }
        }
        return str3 + ")";
    }
}
