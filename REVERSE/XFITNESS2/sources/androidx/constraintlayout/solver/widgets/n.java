package androidx.constraintlayout.solver.widgets;

import androidx.constraintlayout.solver.widgets.ConstraintAnchor;
import java.util.ArrayList;

/* compiled from: Snapshot */
public class n {
    private int a;
    private int b;
    private int c;
    private int d;
    private ArrayList<a> e = new ArrayList<>();

    /* compiled from: Snapshot */
    static class a {
        private ConstraintAnchor a;
        private ConstraintAnchor b;
        private int c;
        private ConstraintAnchor.Strength d;
        private int e;

        public a(ConstraintAnchor constraintAnchor) {
            this.a = constraintAnchor;
            this.b = constraintAnchor.g();
            this.c = constraintAnchor.b();
            this.d = constraintAnchor.f();
            this.e = constraintAnchor.a();
        }

        public void a(ConstraintWidget constraintWidget) {
            constraintWidget.a(this.a.h()).a(this.b, this.c, this.d, this.e);
        }

        public void b(ConstraintWidget constraintWidget) {
            ConstraintAnchor a2 = constraintWidget.a(this.a.h());
            this.a = a2;
            if (a2 != null) {
                this.b = a2.g();
                this.c = this.a.b();
                this.d = this.a.f();
                this.e = this.a.a();
                return;
            }
            this.b = null;
            this.c = 0;
            this.d = ConstraintAnchor.Strength.STRONG;
            this.e = 0;
        }
    }

    public n(ConstraintWidget constraintWidget) {
        this.a = constraintWidget.v();
        this.b = constraintWidget.w();
        this.c = constraintWidget.s();
        this.d = constraintWidget.i();
        ArrayList<ConstraintAnchor> b2 = constraintWidget.b();
        int size = b2.size();
        for (int i2 = 0; i2 < size; i2++) {
            this.e.add(new a(b2.get(i2)));
        }
    }

    public void a(ConstraintWidget constraintWidget) {
        constraintWidget.r(this.a);
        constraintWidget.s(this.b);
        constraintWidget.o(this.c);
        constraintWidget.g(this.d);
        int size = this.e.size();
        for (int i2 = 0; i2 < size; i2++) {
            this.e.get(i2).a(constraintWidget);
        }
    }

    public void b(ConstraintWidget constraintWidget) {
        this.a = constraintWidget.v();
        this.b = constraintWidget.w();
        this.c = constraintWidget.s();
        this.d = constraintWidget.i();
        int size = this.e.size();
        for (int i2 = 0; i2 < size; i2++) {
            this.e.get(i2).b(constraintWidget);
        }
    }
}
