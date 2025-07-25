package androidx.recyclerview.widget;

import android.graphics.Rect;
import android.view.View;
import androidx.recyclerview.widget.RecyclerView;

/* compiled from: OrientationHelper */
public abstract class q {
    protected final RecyclerView.o a;
    private int b;
    final Rect c;

    /* compiled from: OrientationHelper */
    static class a extends q {
        a(RecyclerView.o oVar) {
            super(oVar, (a) null);
        }

        public int a() {
            return this.a.r();
        }

        public int b() {
            return this.a.r() - this.a.p();
        }

        public int c(View view) {
            RecyclerView.p pVar = (RecyclerView.p) view.getLayoutParams();
            return this.a.g(view) + pVar.topMargin + pVar.bottomMargin;
        }

        public int d(View view) {
            return this.a.f(view) - ((RecyclerView.p) view.getLayoutParams()).leftMargin;
        }

        public int e(View view) {
            this.a.a(view, true, this.c);
            return this.c.right;
        }

        public int f() {
            return this.a.o();
        }

        public int g() {
            return (this.a.r() - this.a.o()) - this.a.p();
        }

        public void a(int i2) {
            this.a.e(i2);
        }

        public int b(View view) {
            RecyclerView.p pVar = (RecyclerView.p) view.getLayoutParams();
            return this.a.h(view) + pVar.leftMargin + pVar.rightMargin;
        }

        public int f(View view) {
            this.a.a(view, true, this.c);
            return this.c.left;
        }

        public int a(View view) {
            return this.a.i(view) + ((RecyclerView.p) view.getLayoutParams()).rightMargin;
        }

        public int c() {
            return this.a.p();
        }

        public int d() {
            return this.a.s();
        }

        public int e() {
            return this.a.i();
        }
    }

    /* compiled from: OrientationHelper */
    static class b extends q {
        b(RecyclerView.o oVar) {
            super(oVar, (a) null);
        }

        public int a() {
            return this.a.h();
        }

        public int b() {
            return this.a.h() - this.a.n();
        }

        public int c(View view) {
            RecyclerView.p pVar = (RecyclerView.p) view.getLayoutParams();
            return this.a.h(view) + pVar.leftMargin + pVar.rightMargin;
        }

        public int d(View view) {
            return this.a.j(view) - ((RecyclerView.p) view.getLayoutParams()).topMargin;
        }

        public int e(View view) {
            this.a.a(view, true, this.c);
            return this.c.bottom;
        }

        public int f() {
            return this.a.q();
        }

        public int g() {
            return (this.a.h() - this.a.q()) - this.a.n();
        }

        public void a(int i2) {
            this.a.f(i2);
        }

        public int b(View view) {
            RecyclerView.p pVar = (RecyclerView.p) view.getLayoutParams();
            return this.a.g(view) + pVar.topMargin + pVar.bottomMargin;
        }

        public int f(View view) {
            this.a.a(view, true, this.c);
            return this.c.top;
        }

        public int a(View view) {
            return this.a.e(view) + ((RecyclerView.p) view.getLayoutParams()).bottomMargin;
        }

        public int c() {
            return this.a.n();
        }

        public int d() {
            return this.a.i();
        }

        public int e() {
            return this.a.s();
        }
    }

    /* synthetic */ q(RecyclerView.o oVar, a aVar) {
        this(oVar);
    }

    public static q a(RecyclerView.o oVar, int i2) {
        if (i2 == 0) {
            return a(oVar);
        }
        if (i2 == 1) {
            return b(oVar);
        }
        throw new IllegalArgumentException("invalid orientation");
    }

    public static q b(RecyclerView.o oVar) {
        return new b(oVar);
    }

    public abstract int a();

    public abstract int a(View view);

    public abstract void a(int i2);

    public abstract int b();

    public abstract int b(View view);

    public abstract int c();

    public abstract int c(View view);

    public abstract int d();

    public abstract int d(View view);

    public abstract int e();

    public abstract int e(View view);

    public abstract int f();

    public abstract int f(View view);

    public abstract int g();

    public int h() {
        if (Integer.MIN_VALUE == this.b) {
            return 0;
        }
        return g() - this.b;
    }

    public void i() {
        this.b = g();
    }

    private q(RecyclerView.o oVar) {
        this.b = Integer.MIN_VALUE;
        this.c = new Rect();
        this.a = oVar;
    }

    public static q a(RecyclerView.o oVar) {
        return new a(oVar);
    }
}
