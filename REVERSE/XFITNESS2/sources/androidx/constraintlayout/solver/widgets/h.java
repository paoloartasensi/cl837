package androidx.constraintlayout.solver.widgets;

import java.util.Arrays;

/* compiled from: Helper */
public class h extends ConstraintWidget {
    protected ConstraintWidget[] k0 = new ConstraintWidget[4];
    protected int l0 = 0;

    public void J() {
        this.l0 = 0;
    }

    public void b(ConstraintWidget constraintWidget) {
        int i2 = this.l0 + 1;
        ConstraintWidget[] constraintWidgetArr = this.k0;
        if (i2 > constraintWidgetArr.length) {
            this.k0 = (ConstraintWidget[]) Arrays.copyOf(constraintWidgetArr, constraintWidgetArr.length * 2);
        }
        ConstraintWidget[] constraintWidgetArr2 = this.k0;
        int i3 = this.l0;
        constraintWidgetArr2[i3] = constraintWidget;
        this.l0 = i3 + 1;
    }
}
