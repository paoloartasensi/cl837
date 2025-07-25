package androidx.constraintlayout.solver.widgets;

import androidx.constraintlayout.solver.c;
import java.util.ArrayList;

/* compiled from: WidgetContainer */
public class o extends ConstraintWidget {
    protected ArrayList<ConstraintWidget> k0 = new ArrayList<>();

    public void D() {
        this.k0.clear();
        super.D();
    }

    public void H() {
        super.H();
        ArrayList<ConstraintWidget> arrayList = this.k0;
        if (arrayList != null) {
            int size = arrayList.size();
            for (int i2 = 0; i2 < size; i2++) {
                ConstraintWidget constraintWidget = this.k0.get(i2);
                constraintWidget.b(g(), h());
                if (!(constraintWidget instanceof e)) {
                    constraintWidget.H();
                }
            }
        }
    }

    public e J() {
        ConstraintWidget k = k();
        e eVar = this instanceof e ? (e) this : null;
        while (k != null) {
            ConstraintWidget k2 = k.k();
            if (k instanceof e) {
                eVar = (e) k;
            }
            k = k2;
        }
        return eVar;
    }

    public void K() {
        H();
        ArrayList<ConstraintWidget> arrayList = this.k0;
        if (arrayList != null) {
            int size = arrayList.size();
            for (int i2 = 0; i2 < size; i2++) {
                ConstraintWidget constraintWidget = this.k0.get(i2);
                if (constraintWidget instanceof o) {
                    ((o) constraintWidget).K();
                }
            }
        }
    }

    public void L() {
        this.k0.clear();
    }

    public void a(c cVar) {
        super.a(cVar);
        int size = this.k0.size();
        for (int i2 = 0; i2 < size; i2++) {
            this.k0.get(i2).a(cVar);
        }
    }

    public void b(ConstraintWidget constraintWidget) {
        this.k0.add(constraintWidget);
        if (constraintWidget.k() != null) {
            ((o) constraintWidget.k()).c(constraintWidget);
        }
        constraintWidget.a((ConstraintWidget) this);
    }

    public void c(ConstraintWidget constraintWidget) {
        this.k0.remove(constraintWidget);
        constraintWidget.a((ConstraintWidget) null);
    }

    public void b(int i2, int i3) {
        super.b(i2, i3);
        int size = this.k0.size();
        for (int i4 = 0; i4 < size; i4++) {
            this.k0.get(i4).b(o(), p());
        }
    }
}
