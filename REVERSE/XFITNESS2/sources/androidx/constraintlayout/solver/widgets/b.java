package androidx.constraintlayout.solver.widgets;

import androidx.constraintlayout.solver.SolverVariable;
import androidx.constraintlayout.solver.e;
import androidx.constraintlayout.solver.widgets.ConstraintWidget;
import java.util.ArrayList;

/* compiled from: Barrier */
public class b extends h {
    private int m0 = 0;
    private ArrayList<k> n0 = new ArrayList<>(4);
    private boolean o0 = true;

    public void F() {
        super.F();
        this.n0.clear();
    }

    public void G() {
        k kVar;
        float f2;
        k kVar2;
        int i2 = this.m0;
        float f3 = Float.MAX_VALUE;
        if (i2 != 0) {
            if (i2 == 1) {
                kVar = this.u.d();
            } else if (i2 == 2) {
                kVar = this.t.d();
            } else if (i2 == 3) {
                kVar = this.v.d();
            } else {
                return;
            }
            f3 = 0.0f;
        } else {
            kVar = this.s.d();
        }
        int size = this.n0.size();
        k kVar3 = null;
        int i3 = 0;
        while (i3 < size) {
            k kVar4 = this.n0.get(i3);
            if (kVar4.b == 1) {
                int i4 = this.m0;
                if (i4 == 0 || i4 == 2) {
                    f2 = kVar4.f395g;
                    if (f2 < f3) {
                        kVar2 = kVar4.f394f;
                    } else {
                        i3++;
                    }
                } else {
                    f2 = kVar4.f395g;
                    if (f2 > f3) {
                        kVar2 = kVar4.f394f;
                    } else {
                        i3++;
                    }
                }
                kVar3 = kVar2;
                f3 = f2;
                i3++;
            } else {
                return;
            }
        }
        if (e.h() != null) {
            e.h().y++;
        }
        kVar.f394f = kVar3;
        kVar.f395g = f3;
        kVar.a();
        int i5 = this.m0;
        if (i5 == 0) {
            this.u.d().a(kVar3, f3);
        } else if (i5 == 1) {
            this.s.d().a(kVar3, f3);
        } else if (i5 == 2) {
            this.v.d().a(kVar3, f3);
        } else if (i5 == 3) {
            this.t.d().a(kVar3, f3);
        }
    }

    public void a(int i2) {
        k kVar;
        k kVar2;
        ConstraintWidget constraintWidget = this.D;
        if (constraintWidget != null && ((e) constraintWidget).t(2)) {
            int i3 = this.m0;
            if (i3 == 0) {
                kVar = this.s.d();
            } else if (i3 == 1) {
                kVar = this.u.d();
            } else if (i3 == 2) {
                kVar = this.t.d();
            } else if (i3 == 3) {
                kVar = this.v.d();
            } else {
                return;
            }
            kVar.b(5);
            int i4 = this.m0;
            if (i4 == 0 || i4 == 1) {
                this.t.d().a((k) null, 0.0f);
                this.v.d().a((k) null, 0.0f);
            } else {
                this.s.d().a((k) null, 0.0f);
                this.u.d().a((k) null, 0.0f);
            }
            this.n0.clear();
            for (int i5 = 0; i5 < this.l0; i5++) {
                ConstraintWidget constraintWidget2 = this.k0[i5];
                if (this.o0 || constraintWidget2.a()) {
                    int i6 = this.m0;
                    if (i6 == 0) {
                        kVar2 = constraintWidget2.s.d();
                    } else if (i6 == 1) {
                        kVar2 = constraintWidget2.u.d();
                    } else if (i6 == 2) {
                        kVar2 = constraintWidget2.t.d();
                    } else if (i6 != 3) {
                        kVar2 = null;
                    } else {
                        kVar2 = constraintWidget2.v.d();
                    }
                    if (kVar2 != null) {
                        this.n0.add(kVar2);
                        kVar2.a(kVar);
                    }
                }
            }
        }
    }

    public boolean a() {
        return true;
    }

    public void c(boolean z) {
        this.o0 = z;
    }

    public void t(int i2) {
        this.m0 = i2;
    }

    public void a(e eVar) {
        ConstraintAnchor[] constraintAnchorArr;
        boolean z;
        int i2;
        int i3;
        ConstraintAnchor[] constraintAnchorArr2 = this.A;
        constraintAnchorArr2[0] = this.s;
        constraintAnchorArr2[2] = this.t;
        constraintAnchorArr2[1] = this.u;
        constraintAnchorArr2[3] = this.v;
        int i4 = 0;
        while (true) {
            constraintAnchorArr = this.A;
            if (i4 >= constraintAnchorArr.length) {
                break;
            }
            constraintAnchorArr[i4].f378i = eVar.a((Object) constraintAnchorArr[i4]);
            i4++;
        }
        int i5 = this.m0;
        if (i5 >= 0 && i5 < 4) {
            ConstraintAnchor constraintAnchor = constraintAnchorArr[i5];
            int i6 = 0;
            while (true) {
                if (i6 >= this.l0) {
                    z = false;
                    break;
                }
                ConstraintWidget constraintWidget = this.k0[i6];
                if ((this.o0 || constraintWidget.a()) && ((((i2 = this.m0) == 0 || i2 == 1) && constraintWidget.j() == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) || (((i3 = this.m0) == 2 || i3 == 3) && constraintWidget.q() == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT))) {
                    z = true;
                } else {
                    i6++;
                }
            }
            int i7 = this.m0;
            if (i7 == 0 || i7 == 1 ? k().j() == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT : k().q() == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) {
                z = false;
            }
            for (int i8 = 0; i8 < this.l0; i8++) {
                ConstraintWidget constraintWidget2 = this.k0[i8];
                if (this.o0 || constraintWidget2.a()) {
                    SolverVariable a = eVar.a((Object) constraintWidget2.A[this.m0]);
                    ConstraintAnchor[] constraintAnchorArr3 = constraintWidget2.A;
                    int i9 = this.m0;
                    constraintAnchorArr3[i9].f378i = a;
                    if (i9 == 0 || i9 == 2) {
                        eVar.b(constraintAnchor.f378i, a, z);
                    } else {
                        eVar.a(constraintAnchor.f378i, a, z);
                    }
                }
            }
            int i10 = this.m0;
            if (i10 == 0) {
                eVar.a(this.u.f378i, this.s.f378i, 0, 6);
                if (!z) {
                    eVar.a(this.s.f378i, this.D.u.f378i, 0, 5);
                }
            } else if (i10 == 1) {
                eVar.a(this.s.f378i, this.u.f378i, 0, 6);
                if (!z) {
                    eVar.a(this.s.f378i, this.D.s.f378i, 0, 5);
                }
            } else if (i10 == 2) {
                eVar.a(this.v.f378i, this.t.f378i, 0, 6);
                if (!z) {
                    eVar.a(this.t.f378i, this.D.v.f378i, 0, 5);
                }
            } else if (i10 == 3) {
                eVar.a(this.t.f378i, this.v.f378i, 0, 6);
                if (!z) {
                    eVar.a(this.t.f378i, this.D.t.f378i, 0, 5);
                }
            }
        }
    }
}
