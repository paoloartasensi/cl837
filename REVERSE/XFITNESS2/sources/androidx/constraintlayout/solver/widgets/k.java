package androidx.constraintlayout.solver.widgets;

import androidx.constraintlayout.solver.SolverVariable;
import androidx.constraintlayout.solver.e;
import androidx.constraintlayout.solver.widgets.ConstraintAnchor;

/* compiled from: ResolutionAnchor */
public class k extends m {
    ConstraintAnchor c;
    k d;
    float e;

    /* renamed from: f  reason: collision with root package name */
    k f394f;

    /* renamed from: g  reason: collision with root package name */
    float f395g;

    /* renamed from: h  reason: collision with root package name */
    int f396h = 0;

    /* renamed from: i  reason: collision with root package name */
    private k f397i;

    /* renamed from: j  reason: collision with root package name */
    private l f398j = null;
    private int k = 1;
    private l l = null;
    private int m = 1;

    public k(ConstraintAnchor constraintAnchor) {
        this.c = constraintAnchor;
    }

    /* access modifiers changed from: package-private */
    public String a(int i2) {
        return i2 == 1 ? "DIRECT" : i2 == 2 ? "CENTER" : i2 == 3 ? "MATCH" : i2 == 4 ? "CHAIN" : i2 == 5 ? "BARRIER" : "UNCONNECTED";
    }

    public void a(k kVar, float f2) {
        if (this.b == 0 || !(this.f394f == kVar || this.f395g == f2)) {
            this.f394f = kVar;
            this.f395g = f2;
            if (this.b == 1) {
                b();
            }
            a();
        }
    }

    public void b(int i2) {
        this.f396h = i2;
    }

    public void d() {
        super.d();
        this.d = null;
        this.e = 0.0f;
        this.f398j = null;
        this.k = 1;
        this.l = null;
        this.m = 1;
        this.f394f = null;
        this.f395g = 0.0f;
        this.f397i = null;
        this.f396h = 0;
    }

    public void e() {
        k kVar;
        k kVar2;
        k kVar3;
        k kVar4;
        k kVar5;
        k kVar6;
        float f2;
        float f3;
        float f4;
        float f5;
        k kVar7;
        boolean z = true;
        if (this.b != 1 && this.f396h != 4) {
            l lVar = this.f398j;
            if (lVar != null) {
                if (lVar.b == 1) {
                    this.e = ((float) this.k) * lVar.c;
                } else {
                    return;
                }
            }
            l lVar2 = this.l;
            if (lVar2 != null) {
                if (lVar2.b == 1) {
                    float f6 = lVar2.c;
                } else {
                    return;
                }
            }
            if (this.f396h == 1 && ((kVar7 = this.d) == null || kVar7.b == 1)) {
                k kVar8 = this.d;
                if (kVar8 == null) {
                    this.f394f = this;
                    this.f395g = this.e;
                } else {
                    this.f394f = kVar8.f394f;
                    this.f395g = kVar8.f395g + this.e;
                }
                a();
            } else if (this.f396h == 2 && (kVar4 = this.d) != null && kVar4.b == 1 && (kVar5 = this.f397i) != null && (kVar6 = kVar5.d) != null && kVar6.b == 1) {
                if (e.h() != null) {
                    e.h().v++;
                }
                this.f394f = this.d.f394f;
                k kVar9 = this.f397i;
                kVar9.f394f = kVar9.d.f394f;
                ConstraintAnchor.Type type = this.c.c;
                int i2 = 0;
                if (!(type == ConstraintAnchor.Type.RIGHT || type == ConstraintAnchor.Type.BOTTOM)) {
                    z = false;
                }
                if (z) {
                    f3 = this.d.f395g;
                    f2 = this.f397i.d.f395g;
                } else {
                    f3 = this.f397i.d.f395g;
                    f2 = this.d.f395g;
                }
                float f7 = f3 - f2;
                ConstraintAnchor constraintAnchor = this.c;
                ConstraintAnchor.Type type2 = constraintAnchor.c;
                if (type2 == ConstraintAnchor.Type.LEFT || type2 == ConstraintAnchor.Type.RIGHT) {
                    f5 = f7 - ((float) this.c.b.s());
                    f4 = this.c.b.V;
                } else {
                    f5 = f7 - ((float) constraintAnchor.b.i());
                    f4 = this.c.b.W;
                }
                int b = this.c.b();
                int b2 = this.f397i.c.b();
                if (this.c.g() == this.f397i.c.g()) {
                    f4 = 0.5f;
                    b2 = 0;
                } else {
                    i2 = b;
                }
                float f8 = (float) i2;
                float f9 = (float) b2;
                float f10 = (f5 - f8) - f9;
                if (z) {
                    k kVar10 = this.f397i;
                    kVar10.f395g = kVar10.d.f395g + f9 + (f10 * f4);
                    this.f395g = (this.d.f395g - f8) - (f10 * (1.0f - f4));
                } else {
                    this.f395g = this.d.f395g + f8 + (f10 * f4);
                    k kVar11 = this.f397i;
                    kVar11.f395g = (kVar11.d.f395g - f9) - (f10 * (1.0f - f4));
                }
                a();
                this.f397i.a();
            } else if (this.f396h == 3 && (kVar = this.d) != null && kVar.b == 1 && (kVar2 = this.f397i) != null && (kVar3 = kVar2.d) != null && kVar3.b == 1) {
                if (e.h() != null) {
                    e.h().w++;
                }
                k kVar12 = this.d;
                this.f394f = kVar12.f394f;
                k kVar13 = this.f397i;
                k kVar14 = kVar13.d;
                kVar13.f394f = kVar14.f394f;
                this.f395g = kVar12.f395g + this.e;
                kVar13.f395g = kVar14.f395g + kVar13.e;
                a();
                this.f397i.a();
            } else if (this.f396h == 5) {
                this.c.b.G();
            }
        }
    }

    public float f() {
        return this.f395g;
    }

    public void g() {
        ConstraintAnchor g2 = this.c.g();
        if (g2 != null) {
            if (g2.g() == this.c) {
                this.f396h = 4;
                g2.d().f396h = 4;
            }
            int b = this.c.b();
            ConstraintAnchor.Type type = this.c.c;
            if (type == ConstraintAnchor.Type.RIGHT || type == ConstraintAnchor.Type.BOTTOM) {
                b = -b;
            }
            a(g2.d(), b);
        }
    }

    public String toString() {
        if (this.b != 1) {
            return "{ " + this.c + " UNRESOLVED} type: " + a(this.f396h);
        } else if (this.f394f == this) {
            return "[" + this.c + ", RESOLVED: " + this.f395g + "]  type: " + a(this.f396h);
        } else {
            return "[" + this.c + ", RESOLVED: " + this.f394f + ":" + this.f395g + "] type: " + a(this.f396h);
        }
    }

    public void b(k kVar, float f2) {
        this.f397i = kVar;
    }

    public void b(k kVar, int i2, l lVar) {
        this.f397i = kVar;
        this.l = lVar;
        this.m = i2;
    }

    public void a(int i2, k kVar, int i3) {
        this.f396h = i2;
        this.d = kVar;
        this.e = (float) i3;
        kVar.a(this);
    }

    public void a(k kVar, int i2) {
        this.d = kVar;
        this.e = (float) i2;
        kVar.a(this);
    }

    public void a(k kVar, int i2, l lVar) {
        this.d = kVar;
        kVar.a(this);
        this.f398j = lVar;
        this.k = i2;
        lVar.a(this);
    }

    /* access modifiers changed from: package-private */
    public void a(e eVar) {
        SolverVariable e2 = this.c.e();
        k kVar = this.f394f;
        if (kVar == null) {
            eVar.a(e2, (int) (this.f395g + 0.5f));
        } else {
            eVar.a(e2, eVar.a((Object) kVar.c), (int) (this.f395g + 0.5f), 6);
        }
    }
}
