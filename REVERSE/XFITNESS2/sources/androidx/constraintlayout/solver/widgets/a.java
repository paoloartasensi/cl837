package androidx.constraintlayout.solver.widgets;

import androidx.constraintlayout.solver.widgets.ConstraintAnchor;
import androidx.constraintlayout.solver.widgets.ConstraintWidget;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: Analyzer */
public class a {
    public static void a(e eVar) {
        if ((eVar.M() & 32) != 32) {
            b(eVar);
            return;
        }
        eVar.D0 = true;
        eVar.x0 = false;
        eVar.y0 = false;
        eVar.z0 = false;
        ArrayList<ConstraintWidget> arrayList = eVar.k0;
        List<f> list = eVar.w0;
        boolean z = eVar.j() == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
        boolean z2 = eVar.q() == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
        boolean z3 = z || z2;
        list.clear();
        for (ConstraintWidget next : arrayList) {
            next.p = null;
            next.d0 = false;
            next.F();
        }
        for (ConstraintWidget next2 : arrayList) {
            if (next2.p == null && !a(next2, list, z3)) {
                b(eVar);
                eVar.D0 = false;
                return;
            }
        }
        int i2 = 0;
        int i3 = 0;
        for (f next3 : list) {
            i2 = Math.max(i2, a(next3, 0));
            i3 = Math.max(i3, a(next3, 1));
        }
        if (z) {
            eVar.a(ConstraintWidget.DimensionBehaviour.FIXED);
            eVar.o(i2);
            eVar.x0 = true;
            eVar.y0 = true;
            eVar.A0 = i2;
        }
        if (z2) {
            eVar.b(ConstraintWidget.DimensionBehaviour.FIXED);
            eVar.g(i3);
            eVar.x0 = true;
            eVar.z0 = true;
            eVar.B0 = i3;
        }
        a(list, 0, eVar.s());
        a(list, 1, eVar.i());
    }

    private static void b(e eVar) {
        eVar.w0.clear();
        eVar.w0.add(0, new f(eVar.k0));
    }

    private static boolean a(ConstraintWidget constraintWidget, List<f> list, boolean z) {
        f fVar = new f(new ArrayList(), true);
        list.add(fVar);
        return a(constraintWidget, fVar, list, z);
    }

    private static boolean a(ConstraintWidget constraintWidget, f fVar, List<f> list, boolean z) {
        ConstraintAnchor constraintAnchor;
        ConstraintAnchor constraintAnchor2;
        ConstraintAnchor constraintAnchor3;
        ConstraintWidget constraintWidget2;
        ConstraintAnchor constraintAnchor4;
        ConstraintAnchor constraintAnchor5;
        ConstraintAnchor constraintAnchor6;
        ConstraintAnchor constraintAnchor7;
        ConstraintWidget constraintWidget3;
        ConstraintAnchor constraintAnchor8;
        if (constraintWidget == null) {
            return true;
        }
        constraintWidget.c0 = false;
        e eVar = (e) constraintWidget.k();
        f fVar2 = constraintWidget.p;
        if (fVar2 == null) {
            constraintWidget.b0 = true;
            fVar.a.add(constraintWidget);
            constraintWidget.p = fVar;
            if (constraintWidget.s.d == null && constraintWidget.u.d == null && constraintWidget.t.d == null && constraintWidget.v.d == null && constraintWidget.w.d == null && constraintWidget.z.d == null) {
                a(eVar, constraintWidget, fVar);
                if (z) {
                    return false;
                }
            }
            if (!(constraintWidget.t.d == null || constraintWidget.v.d == null)) {
                ConstraintWidget.DimensionBehaviour q = eVar.q();
                ConstraintWidget.DimensionBehaviour dimensionBehaviour = ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
                if (z) {
                    a(eVar, constraintWidget, fVar);
                    return false;
                } else if (!(constraintWidget.t.d.b == constraintWidget.k() && constraintWidget.v.d.b == constraintWidget.k())) {
                    a(eVar, constraintWidget, fVar);
                }
            }
            if (!(constraintWidget.s.d == null || constraintWidget.u.d == null)) {
                ConstraintWidget.DimensionBehaviour j2 = eVar.j();
                ConstraintWidget.DimensionBehaviour dimensionBehaviour2 = ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
                if (z) {
                    a(eVar, constraintWidget, fVar);
                    return false;
                } else if (!(constraintWidget.s.d.b == constraintWidget.k() && constraintWidget.u.d.b == constraintWidget.k())) {
                    a(eVar, constraintWidget, fVar);
                }
            }
            if (((constraintWidget.j() == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) ^ (constraintWidget.q() == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT)) && constraintWidget.G != 0.0f) {
                a(constraintWidget);
            } else if (constraintWidget.j() == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT || constraintWidget.q() == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                a(eVar, constraintWidget, fVar);
                if (z) {
                    return false;
                }
            }
            if (((constraintWidget.s.d == null && constraintWidget.u.d == null) || (((constraintAnchor5 = constraintWidget.s.d) != null && constraintAnchor5.b == constraintWidget.D && constraintWidget.u.d == null) || (((constraintAnchor6 = constraintWidget.u.d) != null && constraintAnchor6.b == constraintWidget.D && constraintWidget.s.d == null) || ((constraintAnchor7 = constraintWidget.s.d) != null && constraintAnchor7.b == (constraintWidget3 = constraintWidget.D) && (constraintAnchor8 = constraintWidget.u.d) != null && constraintAnchor8.b == constraintWidget3)))) && constraintWidget.z.d == null && !(constraintWidget instanceof g) && !(constraintWidget instanceof h)) {
                fVar.f389f.add(constraintWidget);
            }
            if (((constraintWidget.t.d == null && constraintWidget.v.d == null) || (((constraintAnchor = constraintWidget.t.d) != null && constraintAnchor.b == constraintWidget.D && constraintWidget.v.d == null) || (((constraintAnchor2 = constraintWidget.v.d) != null && constraintAnchor2.b == constraintWidget.D && constraintWidget.t.d == null) || ((constraintAnchor3 = constraintWidget.t.d) != null && constraintAnchor3.b == (constraintWidget2 = constraintWidget.D) && (constraintAnchor4 = constraintWidget.v.d) != null && constraintAnchor4.b == constraintWidget2)))) && constraintWidget.z.d == null && constraintWidget.w.d == null && !(constraintWidget instanceof g) && !(constraintWidget instanceof h)) {
                fVar.f390g.add(constraintWidget);
            }
            if (constraintWidget instanceof h) {
                a(eVar, constraintWidget, fVar);
                if (z) {
                    return false;
                }
                h hVar = (h) constraintWidget;
                for (int i2 = 0; i2 < hVar.l0; i2++) {
                    if (!a(hVar.k0[i2], fVar, list, z)) {
                        return false;
                    }
                }
            }
            for (ConstraintAnchor constraintAnchor9 : constraintWidget.A) {
                ConstraintAnchor constraintAnchor10 = constraintAnchor9.d;
                if (!(constraintAnchor10 == null || constraintAnchor10.b == constraintWidget.k())) {
                    if (constraintAnchor9.c == ConstraintAnchor.Type.CENTER) {
                        a(eVar, constraintWidget, fVar);
                        if (z) {
                            return false;
                        }
                    } else {
                        a(constraintAnchor9);
                    }
                    if (!a(constraintAnchor9.d.b, fVar, list, z)) {
                        return false;
                    }
                }
            }
            return true;
        }
        if (fVar2 != fVar) {
            fVar.a.addAll(fVar2.a);
            fVar.f389f.addAll(constraintWidget.p.f389f);
            fVar.f390g.addAll(constraintWidget.p.f390g);
            if (!constraintWidget.p.d) {
                fVar.d = false;
            }
            list.remove(constraintWidget.p);
            for (ConstraintWidget constraintWidget4 : constraintWidget.p.a) {
                constraintWidget4.p = fVar;
            }
        }
        return true;
    }

    private static void a(e eVar, ConstraintWidget constraintWidget, f fVar) {
        fVar.d = false;
        eVar.D0 = false;
        constraintWidget.b0 = false;
    }

    private static int a(f fVar, int i2) {
        int i3 = i2 * 2;
        List<ConstraintWidget> a = fVar.a(i2);
        int size = a.size();
        int i4 = 0;
        for (int i5 = 0; i5 < size; i5++) {
            ConstraintWidget constraintWidget = a.get(i5);
            ConstraintAnchor[] constraintAnchorArr = constraintWidget.A;
            int i6 = i3 + 1;
            i4 = Math.max(i4, a(constraintWidget, i2, constraintAnchorArr[i6].d == null || !(constraintAnchorArr[i3].d == null || constraintAnchorArr[i6].d == null), 0));
        }
        fVar.e[i2] = i4;
        return i4;
    }

    private static int a(ConstraintWidget constraintWidget, int i2, boolean z, int i3) {
        int i4;
        int i5;
        int i6;
        int i7;
        int i8;
        int s;
        int i9;
        int i10;
        int i11;
        ConstraintWidget constraintWidget2 = constraintWidget;
        int i12 = i2;
        boolean z2 = z;
        int i13 = 0;
        if (!constraintWidget2.b0) {
            return 0;
        }
        boolean z3 = constraintWidget2.w.d != null && i12 == 1;
        if (z2) {
            i7 = constraintWidget.c();
            i6 = constraintWidget.i() - constraintWidget.c();
            i5 = i12 * 2;
            i4 = i5 + 1;
        } else {
            i7 = constraintWidget.i() - constraintWidget.c();
            i6 = constraintWidget.c();
            i4 = i12 * 2;
            i5 = i4 + 1;
        }
        ConstraintAnchor[] constraintAnchorArr = constraintWidget2.A;
        if (constraintAnchorArr[i4].d == null || constraintAnchorArr[i5].d != null) {
            i8 = 1;
        } else {
            i8 = -1;
            int i14 = i4;
            i4 = i5;
            i5 = i14;
        }
        int i15 = z3 ? i3 - i7 : i3;
        int b = (constraintWidget2.A[i5].b() * i8) + a(constraintWidget, i2);
        int i16 = i15 + b;
        int s2 = (i12 == 0 ? constraintWidget.s() : constraintWidget.i()) * i8;
        Iterator<m> it = constraintWidget2.A[i5].d().a.iterator();
        while (it.hasNext()) {
            i13 = Math.max(i13, a(((k) it.next()).c.b, i12, z2, i16));
        }
        int i17 = 0;
        for (Iterator<m> it2 = constraintWidget2.A[i4].d().a.iterator(); it2.hasNext(); it2 = it2) {
            i17 = Math.max(i17, a(((k) it2.next()).c.b, i12, z2, s2 + i16));
        }
        if (z3) {
            i13 -= i7;
            s = i17 + i6;
        } else {
            s = i17 + ((i12 == 0 ? constraintWidget.s() : constraintWidget.i()) * i8);
        }
        int i18 = 1;
        if (i12 == 1) {
            Iterator<m> it3 = constraintWidget2.w.d().a.iterator();
            int i19 = 0;
            while (it3.hasNext()) {
                Iterator<m> it4 = it3;
                k kVar = (k) it3.next();
                if (i8 == i18) {
                    i19 = Math.max(i19, a(kVar.c.b, i12, z2, i7 + i16));
                    i11 = i4;
                } else {
                    i11 = i4;
                    i19 = Math.max(i19, a(kVar.c.b, i12, z2, (i6 * i8) + i16));
                }
                it3 = it4;
                i4 = i11;
                i18 = 1;
            }
            i9 = i4;
            int i20 = i19;
            i10 = (constraintWidget2.w.d().a.size() <= 0 || z3) ? i20 : i8 == 1 ? i20 + i7 : i20 - i6;
        } else {
            i9 = i4;
            i10 = 0;
        }
        int max = b + Math.max(i13, Math.max(s, i10));
        int i21 = s2 + i16;
        if (i8 == -1) {
            int i22 = i21;
            i21 = i16;
            i16 = i22;
        }
        if (z2) {
            i.a(constraintWidget2, i12, i16);
            constraintWidget2.a(i16, i21, i12);
        } else {
            constraintWidget2.p.a(constraintWidget2, i12);
            constraintWidget2.d(i16, i12);
        }
        if (constraintWidget.c(i2) == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && constraintWidget2.G != 0.0f) {
            constraintWidget2.p.a(constraintWidget2, i12);
        }
        ConstraintAnchor[] constraintAnchorArr2 = constraintWidget2.A;
        if (!(constraintAnchorArr2[i5].d == null || constraintAnchorArr2[i9].d == null)) {
            ConstraintWidget k = constraintWidget.k();
            ConstraintAnchor[] constraintAnchorArr3 = constraintWidget2.A;
            if (constraintAnchorArr3[i5].d.b == k && constraintAnchorArr3[i9].d.b == k) {
                constraintWidget2.p.a(constraintWidget2, i12);
            }
        }
        return max;
    }

    private static void a(ConstraintAnchor constraintAnchor) {
        k d = constraintAnchor.d();
        ConstraintAnchor constraintAnchor2 = constraintAnchor.d;
        if (constraintAnchor2 != null && constraintAnchor2.d != constraintAnchor) {
            constraintAnchor2.d().a(d);
        }
    }

    public static void a(List<f> list, int i2, int i3) {
        int size = list.size();
        for (int i4 = 0; i4 < size; i4++) {
            for (ConstraintWidget next : list.get(i4).b(i2)) {
                if (next.b0) {
                    a(next, i2, i3);
                }
            }
        }
    }

    private static void a(ConstraintWidget constraintWidget, int i2, int i3) {
        int i4 = i2 * 2;
        ConstraintAnchor[] constraintAnchorArr = constraintWidget.A;
        ConstraintAnchor constraintAnchor = constraintAnchorArr[i4];
        ConstraintAnchor constraintAnchor2 = constraintAnchorArr[i4 + 1];
        if ((constraintAnchor.d == null || constraintAnchor2.d == null) ? false : true) {
            i.a(constraintWidget, i2, a(constraintWidget, i2) + constraintAnchor.b());
        } else if (constraintWidget.G == 0.0f || constraintWidget.c(i2) != ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
            int e = i3 - constraintWidget.e(i2);
            int d = e - constraintWidget.d(i2);
            constraintWidget.a(d, e, i2);
            i.a(constraintWidget, i2, d);
        } else {
            int a = a(constraintWidget);
            int i5 = (int) constraintWidget.A[i4].d().f395g;
            constraintAnchor2.d().f394f = constraintAnchor.d();
            constraintAnchor2.d().f395g = (float) a;
            constraintAnchor2.d().b = 1;
            constraintWidget.a(i5, i5 + a, i2);
        }
    }

    private static int a(ConstraintWidget constraintWidget, int i2) {
        ConstraintWidget constraintWidget2;
        ConstraintAnchor constraintAnchor;
        int i3 = i2 * 2;
        ConstraintAnchor[] constraintAnchorArr = constraintWidget.A;
        ConstraintAnchor constraintAnchor2 = constraintAnchorArr[i3];
        ConstraintAnchor constraintAnchor3 = constraintAnchorArr[i3 + 1];
        ConstraintAnchor constraintAnchor4 = constraintAnchor2.d;
        if (constraintAnchor4 == null || constraintAnchor4.b != (constraintWidget2 = constraintWidget.D) || (constraintAnchor = constraintAnchor3.d) == null || constraintAnchor.b != constraintWidget2) {
            return 0;
        }
        return (int) (((float) (((constraintWidget2.d(i2) - constraintAnchor2.b()) - constraintAnchor3.b()) - constraintWidget.d(i2))) * (i2 == 0 ? constraintWidget.V : constraintWidget.W));
    }

    private static int a(ConstraintWidget constraintWidget) {
        float f2;
        float f3;
        if (constraintWidget.j() == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
            if (constraintWidget.H == 0) {
                f3 = ((float) constraintWidget.i()) * constraintWidget.G;
            } else {
                f3 = ((float) constraintWidget.i()) / constraintWidget.G;
            }
            int i2 = (int) f3;
            constraintWidget.o(i2);
            return i2;
        } else if (constraintWidget.q() != ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
            return -1;
        } else {
            if (constraintWidget.H == 1) {
                f2 = ((float) constraintWidget.s()) * constraintWidget.G;
            } else {
                f2 = ((float) constraintWidget.s()) / constraintWidget.G;
            }
            int i3 = (int) f2;
            constraintWidget.g(i3);
            return i3;
        }
    }
}
