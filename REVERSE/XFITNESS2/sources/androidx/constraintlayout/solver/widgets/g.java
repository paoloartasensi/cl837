package androidx.constraintlayout.solver.widgets;

import androidx.constraintlayout.solver.SolverVariable;
import androidx.constraintlayout.solver.e;
import androidx.constraintlayout.solver.widgets.ConstraintAnchor;
import androidx.constraintlayout.solver.widgets.ConstraintWidget;
import java.util.ArrayList;

/* compiled from: Guideline */
public class g extends ConstraintWidget {
    protected float k0 = -1.0f;
    protected int l0 = -1;
    protected int m0 = -1;
    private ConstraintAnchor n0 = this.t;
    private int o0;
    private boolean p0;

    /* compiled from: Guideline */
    static /* synthetic */ class a {
        static final /* synthetic */ int[] a;

        /* JADX WARNING: Can't wrap try/catch for region: R(18:0|1|2|3|4|5|6|7|8|9|10|11|12|13|14|15|16|(3:17|18|20)) */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:11:0x003e */
        /* JADX WARNING: Missing exception handler attribute for start block: B:13:0x0049 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:15:0x0054 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:17:0x0060 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0012 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x001d */
        /* JADX WARNING: Missing exception handler attribute for start block: B:7:0x0028 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:9:0x0033 */
        static {
            /*
                androidx.constraintlayout.solver.widgets.ConstraintAnchor$Type[] r0 = androidx.constraintlayout.solver.widgets.ConstraintAnchor.Type.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                a = r0
                androidx.constraintlayout.solver.widgets.ConstraintAnchor$Type r1 = androidx.constraintlayout.solver.widgets.ConstraintAnchor.Type.LEFT     // Catch:{ NoSuchFieldError -> 0x0012 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0012 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0012 }
            L_0x0012:
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x001d }
                androidx.constraintlayout.solver.widgets.ConstraintAnchor$Type r1 = androidx.constraintlayout.solver.widgets.ConstraintAnchor.Type.RIGHT     // Catch:{ NoSuchFieldError -> 0x001d }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001d }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001d }
            L_0x001d:
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x0028 }
                androidx.constraintlayout.solver.widgets.ConstraintAnchor$Type r1 = androidx.constraintlayout.solver.widgets.ConstraintAnchor.Type.TOP     // Catch:{ NoSuchFieldError -> 0x0028 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0028 }
                r2 = 3
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0028 }
            L_0x0028:
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x0033 }
                androidx.constraintlayout.solver.widgets.ConstraintAnchor$Type r1 = androidx.constraintlayout.solver.widgets.ConstraintAnchor.Type.BOTTOM     // Catch:{ NoSuchFieldError -> 0x0033 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0033 }
                r2 = 4
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0033 }
            L_0x0033:
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x003e }
                androidx.constraintlayout.solver.widgets.ConstraintAnchor$Type r1 = androidx.constraintlayout.solver.widgets.ConstraintAnchor.Type.BASELINE     // Catch:{ NoSuchFieldError -> 0x003e }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x003e }
                r2 = 5
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x003e }
            L_0x003e:
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x0049 }
                androidx.constraintlayout.solver.widgets.ConstraintAnchor$Type r1 = androidx.constraintlayout.solver.widgets.ConstraintAnchor.Type.CENTER     // Catch:{ NoSuchFieldError -> 0x0049 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0049 }
                r2 = 6
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0049 }
            L_0x0049:
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x0054 }
                androidx.constraintlayout.solver.widgets.ConstraintAnchor$Type r1 = androidx.constraintlayout.solver.widgets.ConstraintAnchor.Type.CENTER_X     // Catch:{ NoSuchFieldError -> 0x0054 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0054 }
                r2 = 7
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0054 }
            L_0x0054:
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x0060 }
                androidx.constraintlayout.solver.widgets.ConstraintAnchor$Type r1 = androidx.constraintlayout.solver.widgets.ConstraintAnchor.Type.CENTER_Y     // Catch:{ NoSuchFieldError -> 0x0060 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0060 }
                r2 = 8
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0060 }
            L_0x0060:
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x006c }
                androidx.constraintlayout.solver.widgets.ConstraintAnchor$Type r1 = androidx.constraintlayout.solver.widgets.ConstraintAnchor.Type.NONE     // Catch:{ NoSuchFieldError -> 0x006c }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x006c }
                r2 = 9
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x006c }
            L_0x006c:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.solver.widgets.g.a.<clinit>():void");
        }
    }

    public g() {
        this.o0 = 0;
        this.p0 = false;
        new j();
        this.B.clear();
        this.B.add(this.n0);
        int length = this.A.length;
        for (int i2 = 0; i2 < length; i2++) {
            this.A[i2] = this.n0;
        }
    }

    public int J() {
        return this.o0;
    }

    public ConstraintAnchor a(ConstraintAnchor.Type type) {
        switch (a.a[type.ordinal()]) {
            case 1:
            case 2:
                if (this.o0 == 1) {
                    return this.n0;
                }
                break;
            case 3:
            case 4:
                if (this.o0 == 0) {
                    return this.n0;
                }
                break;
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
                return null;
        }
        throw new AssertionError(type.name());
    }

    public boolean a() {
        return true;
    }

    public ArrayList<ConstraintAnchor> b() {
        return this.B;
    }

    public void c(e eVar) {
        if (k() != null) {
            int b = eVar.b((Object) this.n0);
            if (this.o0 == 1) {
                r(b);
                s(0);
                g(k().i());
                o(0);
                return;
            }
            r(0);
            s(b);
            o(k().s());
            g(0);
        }
    }

    public void e(float f2) {
        if (f2 > -1.0f) {
            this.k0 = f2;
            this.l0 = -1;
            this.m0 = -1;
        }
    }

    public void t(int i2) {
        if (i2 > -1) {
            this.k0 = -1.0f;
            this.l0 = i2;
            this.m0 = -1;
        }
    }

    public void u(int i2) {
        if (i2 > -1) {
            this.k0 = -1.0f;
            this.l0 = -1;
            this.m0 = i2;
        }
    }

    public void v(int i2) {
        if (this.o0 != i2) {
            this.o0 = i2;
            this.B.clear();
            if (this.o0 == 1) {
                this.n0 = this.s;
            } else {
                this.n0 = this.t;
            }
            this.B.add(this.n0);
            int length = this.A.length;
            for (int i3 = 0; i3 < length; i3++) {
                this.A[i3] = this.n0;
            }
        }
    }

    public void a(int i2) {
        ConstraintWidget k = k();
        if (k != null) {
            if (J() == 1) {
                this.t.d().a(1, k.t.d(), 0);
                this.v.d().a(1, k.t.d(), 0);
                if (this.l0 != -1) {
                    this.s.d().a(1, k.s.d(), this.l0);
                    this.u.d().a(1, k.s.d(), this.l0);
                } else if (this.m0 != -1) {
                    this.s.d().a(1, k.u.d(), -this.m0);
                    this.u.d().a(1, k.u.d(), -this.m0);
                } else if (this.k0 != -1.0f && k.j() == ConstraintWidget.DimensionBehaviour.FIXED) {
                    int i3 = (int) (((float) k.E) * this.k0);
                    this.s.d().a(1, k.s.d(), i3);
                    this.u.d().a(1, k.s.d(), i3);
                }
            } else {
                this.s.d().a(1, k.s.d(), 0);
                this.u.d().a(1, k.s.d(), 0);
                if (this.l0 != -1) {
                    this.t.d().a(1, k.t.d(), this.l0);
                    this.v.d().a(1, k.t.d(), this.l0);
                } else if (this.m0 != -1) {
                    this.t.d().a(1, k.v.d(), -this.m0);
                    this.v.d().a(1, k.v.d(), -this.m0);
                } else if (this.k0 != -1.0f && k.q() == ConstraintWidget.DimensionBehaviour.FIXED) {
                    int i4 = (int) (((float) k.F) * this.k0);
                    this.t.d().a(1, k.t.d(), i4);
                    this.v.d().a(1, k.t.d(), i4);
                }
            }
        }
    }

    public void a(e eVar) {
        e eVar2 = (e) k();
        if (eVar2 != null) {
            ConstraintAnchor a2 = eVar2.a(ConstraintAnchor.Type.LEFT);
            ConstraintAnchor a3 = eVar2.a(ConstraintAnchor.Type.RIGHT);
            ConstraintWidget constraintWidget = this.D;
            boolean z = true;
            boolean z2 = constraintWidget != null && constraintWidget.C[0] == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
            if (this.o0 == 0) {
                a2 = eVar2.a(ConstraintAnchor.Type.TOP);
                a3 = eVar2.a(ConstraintAnchor.Type.BOTTOM);
                ConstraintWidget constraintWidget2 = this.D;
                if (constraintWidget2 == null || constraintWidget2.C[1] != ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) {
                    z = false;
                }
                z2 = z;
            }
            if (this.l0 != -1) {
                SolverVariable a4 = eVar.a((Object) this.n0);
                eVar.a(a4, eVar.a((Object) a2), this.l0, 6);
                if (z2) {
                    eVar.b(eVar.a((Object) a3), a4, 0, 5);
                }
            } else if (this.m0 != -1) {
                SolverVariable a5 = eVar.a((Object) this.n0);
                SolverVariable a6 = eVar.a((Object) a3);
                eVar.a(a5, a6, -this.m0, 6);
                if (z2) {
                    eVar.b(a5, eVar.a((Object) a2), 0, 5);
                    eVar.b(a6, a5, 0, 5);
                }
            } else if (this.k0 != -1.0f) {
                eVar.a(e.a(eVar, eVar.a((Object) this.n0), eVar.a((Object) a2), eVar.a((Object) a3), this.k0, this.p0));
            }
        }
    }
}
