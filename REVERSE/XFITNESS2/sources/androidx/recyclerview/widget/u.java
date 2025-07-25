package androidx.recyclerview.widget;

import android.view.View;
import androidx.recyclerview.widget.RecyclerView;

/* compiled from: SimpleItemAnimator */
public abstract class u extends RecyclerView.l {

    /* renamed from: g  reason: collision with root package name */
    boolean f855g = true;

    public void a(boolean z) {
        this.f855g = z;
    }

    public abstract boolean a(RecyclerView.c0 c0Var, int i2, int i3, int i4, int i5);

    public abstract boolean a(RecyclerView.c0 c0Var, RecyclerView.c0 c0Var2, int i2, int i3, int i4, int i5);

    public boolean b(RecyclerView.c0 c0Var, RecyclerView.l.c cVar, RecyclerView.l.c cVar2) {
        int i2 = cVar.a;
        int i3 = cVar.b;
        View view = c0Var.itemView;
        int left = cVar2 == null ? view.getLeft() : cVar2.a;
        int top = cVar2 == null ? view.getTop() : cVar2.b;
        if (c0Var.isRemoved() || (i2 == left && i3 == top)) {
            return g(c0Var);
        }
        view.layout(left, top, view.getWidth() + left, view.getHeight() + top);
        return a(c0Var, i2, i3, left, top);
    }

    public void c(RecyclerView.c0 c0Var, boolean z) {
    }

    public boolean c(RecyclerView.c0 c0Var, RecyclerView.l.c cVar, RecyclerView.l.c cVar2) {
        if (cVar.a == cVar2.a && cVar.b == cVar2.b) {
            j(c0Var);
            return false;
        }
        return a(c0Var, cVar.a, cVar.b, cVar2.a, cVar2.b);
    }

    public void d(RecyclerView.c0 c0Var, boolean z) {
    }

    public abstract boolean f(RecyclerView.c0 c0Var);

    public abstract boolean g(RecyclerView.c0 c0Var);

    public final void h(RecyclerView.c0 c0Var) {
        n(c0Var);
        b(c0Var);
    }

    public final void i(RecyclerView.c0 c0Var) {
        o(c0Var);
    }

    public final void j(RecyclerView.c0 c0Var) {
        p(c0Var);
        b(c0Var);
    }

    public final void k(RecyclerView.c0 c0Var) {
        q(c0Var);
    }

    public final void l(RecyclerView.c0 c0Var) {
        r(c0Var);
        b(c0Var);
    }

    public final void m(RecyclerView.c0 c0Var) {
        s(c0Var);
    }

    public void n(RecyclerView.c0 c0Var) {
    }

    public void o(RecyclerView.c0 c0Var) {
    }

    public void p(RecyclerView.c0 c0Var) {
    }

    public void q(RecyclerView.c0 c0Var) {
    }

    public void r(RecyclerView.c0 c0Var) {
    }

    public void s(RecyclerView.c0 c0Var) {
    }

    public boolean a(RecyclerView.c0 c0Var) {
        return !this.f855g || c0Var.isInvalid();
    }

    public boolean a(RecyclerView.c0 c0Var, RecyclerView.l.c cVar, RecyclerView.l.c cVar2) {
        if (cVar == null || (cVar.a == cVar2.a && cVar.b == cVar2.b)) {
            return f(c0Var);
        }
        return a(c0Var, cVar.a, cVar.b, cVar2.a, cVar2.b);
    }

    public boolean a(RecyclerView.c0 c0Var, RecyclerView.c0 c0Var2, RecyclerView.l.c cVar, RecyclerView.l.c cVar2) {
        int i2;
        int i3;
        int i4 = cVar.a;
        int i5 = cVar.b;
        if (c0Var2.shouldIgnore()) {
            int i6 = cVar.a;
            i2 = cVar.b;
            i3 = i6;
        } else {
            i3 = cVar2.a;
            i2 = cVar2.b;
        }
        return a(c0Var, c0Var2, i4, i5, i3, i2);
    }

    public final void b(RecyclerView.c0 c0Var, boolean z) {
        d(c0Var, z);
    }

    public final void a(RecyclerView.c0 c0Var, boolean z) {
        c(c0Var, z);
        b(c0Var);
    }
}
