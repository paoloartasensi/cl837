package androidx.recyclerview.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.view.View;
import android.view.ViewPropertyAnimator;
import androidx.core.h.v;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: DefaultItemAnimator */
public class e extends u {
    private static TimeInterpolator s;

    /* renamed from: h  reason: collision with root package name */
    private ArrayList<RecyclerView.c0> f824h = new ArrayList<>();

    /* renamed from: i  reason: collision with root package name */
    private ArrayList<RecyclerView.c0> f825i = new ArrayList<>();

    /* renamed from: j  reason: collision with root package name */
    private ArrayList<j> f826j = new ArrayList<>();
    private ArrayList<i> k = new ArrayList<>();
    ArrayList<ArrayList<RecyclerView.c0>> l = new ArrayList<>();
    ArrayList<ArrayList<j>> m = new ArrayList<>();
    ArrayList<ArrayList<i>> n = new ArrayList<>();
    ArrayList<RecyclerView.c0> o = new ArrayList<>();
    ArrayList<RecyclerView.c0> p = new ArrayList<>();
    ArrayList<RecyclerView.c0> q = new ArrayList<>();
    ArrayList<RecyclerView.c0> r = new ArrayList<>();

    /* compiled from: DefaultItemAnimator */
    class a implements Runnable {
        final /* synthetic */ ArrayList e;

        a(ArrayList arrayList) {
            this.e = arrayList;
        }

        public void run() {
            Iterator it = this.e.iterator();
            while (it.hasNext()) {
                j jVar = (j) it.next();
                e.this.b(jVar.a, jVar.b, jVar.c, jVar.d, jVar.e);
            }
            this.e.clear();
            e.this.m.remove(this.e);
        }
    }

    /* compiled from: DefaultItemAnimator */
    class b implements Runnable {
        final /* synthetic */ ArrayList e;

        b(ArrayList arrayList) {
            this.e = arrayList;
        }

        public void run() {
            Iterator it = this.e.iterator();
            while (it.hasNext()) {
                e.this.a((i) it.next());
            }
            this.e.clear();
            e.this.n.remove(this.e);
        }
    }

    /* compiled from: DefaultItemAnimator */
    class c implements Runnable {
        final /* synthetic */ ArrayList e;

        c(ArrayList arrayList) {
            this.e = arrayList;
        }

        public void run() {
            Iterator it = this.e.iterator();
            while (it.hasNext()) {
                e.this.t((RecyclerView.c0) it.next());
            }
            this.e.clear();
            e.this.l.remove(this.e);
        }
    }

    /* compiled from: DefaultItemAnimator */
    class d extends AnimatorListenerAdapter {
        final /* synthetic */ RecyclerView.c0 a;
        final /* synthetic */ ViewPropertyAnimator b;
        final /* synthetic */ View c;

        d(RecyclerView.c0 c0Var, ViewPropertyAnimator viewPropertyAnimator, View view) {
            this.a = c0Var;
            this.b = viewPropertyAnimator;
            this.c = view;
        }

        public void onAnimationEnd(Animator animator) {
            this.b.setListener((Animator.AnimatorListener) null);
            this.c.setAlpha(1.0f);
            e.this.l(this.a);
            e.this.q.remove(this.a);
            e.this.j();
        }

        public void onAnimationStart(Animator animator) {
            e.this.m(this.a);
        }
    }

    /* renamed from: androidx.recyclerview.widget.e$e  reason: collision with other inner class name */
    /* compiled from: DefaultItemAnimator */
    class C0043e extends AnimatorListenerAdapter {
        final /* synthetic */ RecyclerView.c0 a;
        final /* synthetic */ View b;
        final /* synthetic */ ViewPropertyAnimator c;

        C0043e(RecyclerView.c0 c0Var, View view, ViewPropertyAnimator viewPropertyAnimator) {
            this.a = c0Var;
            this.b = view;
            this.c = viewPropertyAnimator;
        }

        public void onAnimationCancel(Animator animator) {
            this.b.setAlpha(1.0f);
        }

        public void onAnimationEnd(Animator animator) {
            this.c.setListener((Animator.AnimatorListener) null);
            e.this.h(this.a);
            e.this.o.remove(this.a);
            e.this.j();
        }

        public void onAnimationStart(Animator animator) {
            e.this.i(this.a);
        }
    }

    /* compiled from: DefaultItemAnimator */
    class f extends AnimatorListenerAdapter {
        final /* synthetic */ RecyclerView.c0 a;
        final /* synthetic */ int b;
        final /* synthetic */ View c;
        final /* synthetic */ int d;
        final /* synthetic */ ViewPropertyAnimator e;

        f(RecyclerView.c0 c0Var, int i2, View view, int i3, ViewPropertyAnimator viewPropertyAnimator) {
            this.a = c0Var;
            this.b = i2;
            this.c = view;
            this.d = i3;
            this.e = viewPropertyAnimator;
        }

        public void onAnimationCancel(Animator animator) {
            if (this.b != 0) {
                this.c.setTranslationX(0.0f);
            }
            if (this.d != 0) {
                this.c.setTranslationY(0.0f);
            }
        }

        public void onAnimationEnd(Animator animator) {
            this.e.setListener((Animator.AnimatorListener) null);
            e.this.j(this.a);
            e.this.p.remove(this.a);
            e.this.j();
        }

        public void onAnimationStart(Animator animator) {
            e.this.k(this.a);
        }
    }

    /* compiled from: DefaultItemAnimator */
    class g extends AnimatorListenerAdapter {
        final /* synthetic */ i a;
        final /* synthetic */ ViewPropertyAnimator b;
        final /* synthetic */ View c;

        g(i iVar, ViewPropertyAnimator viewPropertyAnimator, View view) {
            this.a = iVar;
            this.b = viewPropertyAnimator;
            this.c = view;
        }

        public void onAnimationEnd(Animator animator) {
            this.b.setListener((Animator.AnimatorListener) null);
            this.c.setAlpha(1.0f);
            this.c.setTranslationX(0.0f);
            this.c.setTranslationY(0.0f);
            e.this.a(this.a.a, true);
            e.this.r.remove(this.a.a);
            e.this.j();
        }

        public void onAnimationStart(Animator animator) {
            e.this.b(this.a.a, true);
        }
    }

    /* compiled from: DefaultItemAnimator */
    class h extends AnimatorListenerAdapter {
        final /* synthetic */ i a;
        final /* synthetic */ ViewPropertyAnimator b;
        final /* synthetic */ View c;

        h(i iVar, ViewPropertyAnimator viewPropertyAnimator, View view) {
            this.a = iVar;
            this.b = viewPropertyAnimator;
            this.c = view;
        }

        public void onAnimationEnd(Animator animator) {
            this.b.setListener((Animator.AnimatorListener) null);
            this.c.setAlpha(1.0f);
            this.c.setTranslationX(0.0f);
            this.c.setTranslationY(0.0f);
            e.this.a(this.a.b, false);
            e.this.r.remove(this.a.b);
            e.this.j();
        }

        public void onAnimationStart(Animator animator) {
            e.this.b(this.a.b, false);
        }
    }

    /* compiled from: DefaultItemAnimator */
    private static class j {
        public RecyclerView.c0 a;
        public int b;
        public int c;
        public int d;
        public int e;

        j(RecyclerView.c0 c0Var, int i2, int i3, int i4, int i5) {
            this.a = c0Var;
            this.b = i2;
            this.c = i3;
            this.d = i4;
            this.e = i5;
        }
    }

    private void u(RecyclerView.c0 c0Var) {
        View view = c0Var.itemView;
        ViewPropertyAnimator animate = view.animate();
        this.q.add(c0Var);
        animate.setDuration(f()).alpha(0.0f).setListener(new d(c0Var, animate, view)).start();
    }

    private void v(RecyclerView.c0 c0Var) {
        if (s == null) {
            s = new ValueAnimator().getInterpolator();
        }
        c0Var.itemView.animate().setInterpolator(s);
        c(c0Var);
    }

    public boolean a(RecyclerView.c0 c0Var, int i2, int i3, int i4, int i5) {
        View view = c0Var.itemView;
        int translationX = i2 + ((int) view.getTranslationX());
        int translationY = i3 + ((int) c0Var.itemView.getTranslationY());
        v(c0Var);
        int i6 = i4 - translationX;
        int i7 = i5 - translationY;
        if (i6 == 0 && i7 == 0) {
            j(c0Var);
            return false;
        }
        if (i6 != 0) {
            view.setTranslationX((float) (-i6));
        }
        if (i7 != 0) {
            view.setTranslationY((float) (-i7));
        }
        this.f826j.add(new j(c0Var, translationX, translationY, i4, i5));
        return true;
    }

    /* access modifiers changed from: package-private */
    public void b(RecyclerView.c0 c0Var, int i2, int i3, int i4, int i5) {
        View view = c0Var.itemView;
        int i6 = i4 - i2;
        int i7 = i5 - i3;
        if (i6 != 0) {
            view.animate().translationX(0.0f);
        }
        if (i7 != 0) {
            view.animate().translationY(0.0f);
        }
        ViewPropertyAnimator animate = view.animate();
        this.p.add(c0Var);
        animate.setDuration(e()).setListener(new f(c0Var, i6, view, i7, animate)).start();
    }

    public void c(RecyclerView.c0 c0Var) {
        View view = c0Var.itemView;
        view.animate().cancel();
        int size = this.f826j.size();
        while (true) {
            size--;
            if (size < 0) {
                break;
            } else if (this.f826j.get(size).a == c0Var) {
                view.setTranslationY(0.0f);
                view.setTranslationX(0.0f);
                j(c0Var);
                this.f826j.remove(size);
            }
        }
        a((List<i>) this.k, c0Var);
        if (this.f824h.remove(c0Var)) {
            view.setAlpha(1.0f);
            l(c0Var);
        }
        if (this.f825i.remove(c0Var)) {
            view.setAlpha(1.0f);
            h(c0Var);
        }
        for (int size2 = this.n.size() - 1; size2 >= 0; size2--) {
            ArrayList arrayList = this.n.get(size2);
            a((List<i>) arrayList, c0Var);
            if (arrayList.isEmpty()) {
                this.n.remove(size2);
            }
        }
        for (int size3 = this.m.size() - 1; size3 >= 0; size3--) {
            ArrayList arrayList2 = this.m.get(size3);
            int size4 = arrayList2.size() - 1;
            while (true) {
                if (size4 < 0) {
                    break;
                } else if (((j) arrayList2.get(size4)).a == c0Var) {
                    view.setTranslationY(0.0f);
                    view.setTranslationX(0.0f);
                    j(c0Var);
                    arrayList2.remove(size4);
                    if (arrayList2.isEmpty()) {
                        this.m.remove(size3);
                    }
                } else {
                    size4--;
                }
            }
        }
        for (int size5 = this.l.size() - 1; size5 >= 0; size5--) {
            ArrayList arrayList3 = this.l.get(size5);
            if (arrayList3.remove(c0Var)) {
                view.setAlpha(1.0f);
                h(c0Var);
                if (arrayList3.isEmpty()) {
                    this.l.remove(size5);
                }
            }
        }
        this.q.remove(c0Var);
        this.o.remove(c0Var);
        this.r.remove(c0Var);
        this.p.remove(c0Var);
        j();
    }

    public boolean f(RecyclerView.c0 c0Var) {
        v(c0Var);
        c0Var.itemView.setAlpha(0.0f);
        this.f825i.add(c0Var);
        return true;
    }

    public boolean g(RecyclerView.c0 c0Var) {
        v(c0Var);
        this.f824h.add(c0Var);
        return true;
    }

    public void i() {
        boolean z = !this.f824h.isEmpty();
        boolean z2 = !this.f826j.isEmpty();
        boolean z3 = !this.k.isEmpty();
        boolean z4 = !this.f825i.isEmpty();
        if (z || z2 || z4 || z3) {
            Iterator<RecyclerView.c0> it = this.f824h.iterator();
            while (it.hasNext()) {
                u(it.next());
            }
            this.f824h.clear();
            if (z2) {
                ArrayList arrayList = new ArrayList();
                arrayList.addAll(this.f826j);
                this.m.add(arrayList);
                this.f826j.clear();
                a aVar = new a(arrayList);
                if (z) {
                    v.a(((j) arrayList.get(0)).a.itemView, (Runnable) aVar, f());
                } else {
                    aVar.run();
                }
            }
            if (z3) {
                ArrayList arrayList2 = new ArrayList();
                arrayList2.addAll(this.k);
                this.n.add(arrayList2);
                this.k.clear();
                b bVar = new b(arrayList2);
                if (z) {
                    v.a(((i) arrayList2.get(0)).a.itemView, (Runnable) bVar, f());
                } else {
                    bVar.run();
                }
            }
            if (z4) {
                ArrayList arrayList3 = new ArrayList();
                arrayList3.addAll(this.f825i);
                this.l.add(arrayList3);
                this.f825i.clear();
                c cVar = new c(arrayList3);
                if (z || z2 || z3) {
                    long j2 = 0;
                    long f2 = z ? f() : 0;
                    long e = z2 ? e() : 0;
                    if (z3) {
                        j2 = d();
                    }
                    v.a(((RecyclerView.c0) arrayList3.get(0)).itemView, (Runnable) cVar, f2 + Math.max(e, j2));
                    return;
                }
                cVar.run();
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void j() {
        if (!g()) {
            a();
        }
    }

    /* access modifiers changed from: package-private */
    public void t(RecyclerView.c0 c0Var) {
        View view = c0Var.itemView;
        ViewPropertyAnimator animate = view.animate();
        this.o.add(c0Var);
        animate.alpha(1.0f).setDuration(c()).setListener(new C0043e(c0Var, view, animate)).start();
    }

    /* compiled from: DefaultItemAnimator */
    private static class i {
        public RecyclerView.c0 a;
        public RecyclerView.c0 b;
        public int c;
        public int d;
        public int e;

        /* renamed from: f  reason: collision with root package name */
        public int f831f;

        private i(RecyclerView.c0 c0Var, RecyclerView.c0 c0Var2) {
            this.a = c0Var;
            this.b = c0Var2;
        }

        public String toString() {
            return "ChangeInfo{oldHolder=" + this.a + ", newHolder=" + this.b + ", fromX=" + this.c + ", fromY=" + this.d + ", toX=" + this.e + ", toY=" + this.f831f + '}';
        }

        i(RecyclerView.c0 c0Var, RecyclerView.c0 c0Var2, int i2, int i3, int i4, int i5) {
            this(c0Var, c0Var2);
            this.c = i2;
            this.d = i3;
            this.e = i4;
            this.f831f = i5;
        }
    }

    public boolean g() {
        return !this.f825i.isEmpty() || !this.k.isEmpty() || !this.f826j.isEmpty() || !this.f824h.isEmpty() || !this.p.isEmpty() || !this.q.isEmpty() || !this.o.isEmpty() || !this.r.isEmpty() || !this.m.isEmpty() || !this.l.isEmpty() || !this.n.isEmpty();
    }

    private void b(i iVar) {
        RecyclerView.c0 c0Var = iVar.a;
        if (c0Var != null) {
            a(iVar, c0Var);
        }
        RecyclerView.c0 c0Var2 = iVar.b;
        if (c0Var2 != null) {
            a(iVar, c0Var2);
        }
    }

    public boolean a(RecyclerView.c0 c0Var, RecyclerView.c0 c0Var2, int i2, int i3, int i4, int i5) {
        if (c0Var == c0Var2) {
            return a(c0Var, i2, i3, i4, i5);
        }
        float translationX = c0Var.itemView.getTranslationX();
        float translationY = c0Var.itemView.getTranslationY();
        float alpha = c0Var.itemView.getAlpha();
        v(c0Var);
        int i6 = (int) (((float) (i4 - i2)) - translationX);
        int i7 = (int) (((float) (i5 - i3)) - translationY);
        c0Var.itemView.setTranslationX(translationX);
        c0Var.itemView.setTranslationY(translationY);
        c0Var.itemView.setAlpha(alpha);
        if (c0Var2 != null) {
            v(c0Var2);
            c0Var2.itemView.setTranslationX((float) (-i6));
            c0Var2.itemView.setTranslationY((float) (-i7));
            c0Var2.itemView.setAlpha(0.0f);
        }
        this.k.add(new i(c0Var, c0Var2, i2, i3, i4, i5));
        return true;
    }

    public void b() {
        int size = this.f826j.size();
        while (true) {
            size--;
            if (size < 0) {
                break;
            }
            j jVar = this.f826j.get(size);
            View view = jVar.a.itemView;
            view.setTranslationY(0.0f);
            view.setTranslationX(0.0f);
            j(jVar.a);
            this.f826j.remove(size);
        }
        for (int size2 = this.f824h.size() - 1; size2 >= 0; size2--) {
            l(this.f824h.get(size2));
            this.f824h.remove(size2);
        }
        int size3 = this.f825i.size();
        while (true) {
            size3--;
            if (size3 < 0) {
                break;
            }
            RecyclerView.c0 c0Var = this.f825i.get(size3);
            c0Var.itemView.setAlpha(1.0f);
            h(c0Var);
            this.f825i.remove(size3);
        }
        for (int size4 = this.k.size() - 1; size4 >= 0; size4--) {
            b(this.k.get(size4));
        }
        this.k.clear();
        if (g()) {
            for (int size5 = this.m.size() - 1; size5 >= 0; size5--) {
                ArrayList arrayList = this.m.get(size5);
                for (int size6 = arrayList.size() - 1; size6 >= 0; size6--) {
                    j jVar2 = (j) arrayList.get(size6);
                    View view2 = jVar2.a.itemView;
                    view2.setTranslationY(0.0f);
                    view2.setTranslationX(0.0f);
                    j(jVar2.a);
                    arrayList.remove(size6);
                    if (arrayList.isEmpty()) {
                        this.m.remove(arrayList);
                    }
                }
            }
            for (int size7 = this.l.size() - 1; size7 >= 0; size7--) {
                ArrayList arrayList2 = this.l.get(size7);
                for (int size8 = arrayList2.size() - 1; size8 >= 0; size8--) {
                    RecyclerView.c0 c0Var2 = (RecyclerView.c0) arrayList2.get(size8);
                    c0Var2.itemView.setAlpha(1.0f);
                    h(c0Var2);
                    arrayList2.remove(size8);
                    if (arrayList2.isEmpty()) {
                        this.l.remove(arrayList2);
                    }
                }
            }
            for (int size9 = this.n.size() - 1; size9 >= 0; size9--) {
                ArrayList arrayList3 = this.n.get(size9);
                for (int size10 = arrayList3.size() - 1; size10 >= 0; size10--) {
                    b((i) arrayList3.get(size10));
                    if (arrayList3.isEmpty()) {
                        this.n.remove(arrayList3);
                    }
                }
            }
            a((List<RecyclerView.c0>) this.q);
            a((List<RecyclerView.c0>) this.p);
            a((List<RecyclerView.c0>) this.o);
            a((List<RecyclerView.c0>) this.r);
            a();
        }
    }

    /* access modifiers changed from: package-private */
    public void a(i iVar) {
        View view;
        RecyclerView.c0 c0Var = iVar.a;
        View view2 = null;
        if (c0Var == null) {
            view = null;
        } else {
            view = c0Var.itemView;
        }
        RecyclerView.c0 c0Var2 = iVar.b;
        if (c0Var2 != null) {
            view2 = c0Var2.itemView;
        }
        if (view != null) {
            ViewPropertyAnimator duration = view.animate().setDuration(d());
            this.r.add(iVar.a);
            duration.translationX((float) (iVar.e - iVar.c));
            duration.translationY((float) (iVar.f831f - iVar.d));
            duration.alpha(0.0f).setListener(new g(iVar, duration, view)).start();
        }
        if (view2 != null) {
            ViewPropertyAnimator animate = view2.animate();
            this.r.add(iVar.b);
            animate.translationX(0.0f).translationY(0.0f).setDuration(d()).alpha(1.0f).setListener(new h(iVar, animate, view2)).start();
        }
    }

    private void a(List<i> list, RecyclerView.c0 c0Var) {
        for (int size = list.size() - 1; size >= 0; size--) {
            i iVar = list.get(size);
            if (a(iVar, c0Var) && iVar.a == null && iVar.b == null) {
                list.remove(iVar);
            }
        }
    }

    private boolean a(i iVar, RecyclerView.c0 c0Var) {
        boolean z = false;
        if (iVar.b == c0Var) {
            iVar.b = null;
        } else if (iVar.a != c0Var) {
            return false;
        } else {
            iVar.a = null;
            z = true;
        }
        c0Var.itemView.setAlpha(1.0f);
        c0Var.itemView.setTranslationX(0.0f);
        c0Var.itemView.setTranslationY(0.0f);
        a(c0Var, z);
        return true;
    }

    /* access modifiers changed from: package-private */
    public void a(List<RecyclerView.c0> list) {
        for (int size = list.size() - 1; size >= 0; size--) {
            list.get(size).itemView.animate().cancel();
        }
    }

    public boolean a(RecyclerView.c0 c0Var, List<Object> list) {
        return !list.isEmpty() || super.a(c0Var, list);
    }
}
