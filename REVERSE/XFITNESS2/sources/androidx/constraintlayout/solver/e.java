package androidx.constraintlayout.solver;

import androidx.constraintlayout.solver.SolverVariable;
import androidx.constraintlayout.solver.widgets.ConstraintAnchor;
import androidx.constraintlayout.solver.widgets.ConstraintWidget;
import java.util.Arrays;
import java.util.HashMap;

/* compiled from: LinearSystem */
public class e {
    private static int p = 1000;
    public static f q;
    int a;
    private HashMap<String, SolverVariable> b;
    private a c;
    private int d;
    private int e;

    /* renamed from: f  reason: collision with root package name */
    b[] f365f;

    /* renamed from: g  reason: collision with root package name */
    public boolean f366g;

    /* renamed from: h  reason: collision with root package name */
    private boolean[] f367h;

    /* renamed from: i  reason: collision with root package name */
    int f368i;

    /* renamed from: j  reason: collision with root package name */
    int f369j;
    private int k;
    final c l;
    private SolverVariable[] m;
    private int n;
    private final a o;

    /* compiled from: LinearSystem */
    interface a {
        SolverVariable a(e eVar, boolean[] zArr);

        void a(SolverVariable solverVariable);

        void a(a aVar);

        void clear();

        SolverVariable getKey();
    }

    public e() {
        this.a = 0;
        this.b = null;
        this.d = 32;
        this.e = 32;
        this.f365f = null;
        this.f366g = false;
        this.f367h = new boolean[32];
        this.f368i = 1;
        this.f369j = 0;
        this.k = 32;
        this.m = new SolverVariable[p];
        this.n = 0;
        this.f365f = new b[32];
        j();
        c cVar = new c();
        this.l = cVar;
        this.c = new d(cVar);
        this.o = new b(this.l);
    }

    private final void d(b bVar) {
        if (this.f369j > 0) {
            bVar.d.a(bVar, this.f365f);
            if (bVar.d.a == 0) {
                bVar.e = true;
            }
        }
    }

    private void g() {
        for (int i2 = 0; i2 < this.f369j; i2++) {
            b bVar = this.f365f[i2];
            bVar.a.e = bVar.b;
        }
    }

    public static f h() {
        return q;
    }

    private void i() {
        int i2 = this.d * 2;
        this.d = i2;
        this.f365f = (b[]) Arrays.copyOf(this.f365f, i2);
        c cVar = this.l;
        cVar.c = (SolverVariable[]) Arrays.copyOf(cVar.c, this.d);
        int i3 = this.d;
        this.f367h = new boolean[i3];
        this.e = i3;
        this.k = i3;
        f fVar = q;
        if (fVar != null) {
            fVar.d++;
            fVar.o = Math.max(fVar.o, (long) i3);
            f fVar2 = q;
            fVar2.A = fVar2.o;
        }
    }

    private void j() {
        int i2 = 0;
        while (true) {
            b[] bVarArr = this.f365f;
            if (i2 < bVarArr.length) {
                b bVar = bVarArr[i2];
                if (bVar != null) {
                    this.l.a.a(bVar);
                }
                this.f365f[i2] = null;
                i2++;
            } else {
                return;
            }
        }
    }

    public SolverVariable a(Object obj) {
        SolverVariable solverVariable = null;
        if (obj == null) {
            return null;
        }
        if (this.f368i + 1 >= this.e) {
            i();
        }
        if (obj instanceof ConstraintAnchor) {
            ConstraintAnchor constraintAnchor = (ConstraintAnchor) obj;
            solverVariable = constraintAnchor.e();
            if (solverVariable == null) {
                constraintAnchor.a(this.l);
                solverVariable = constraintAnchor.e();
            }
            int i2 = solverVariable.b;
            if (i2 == -1 || i2 > this.a || this.l.c[i2] == null) {
                if (solverVariable.b != -1) {
                    solverVariable.a();
                }
                int i3 = this.a + 1;
                this.a = i3;
                this.f368i++;
                solverVariable.b = i3;
                solverVariable.f356g = SolverVariable.Type.UNRESTRICTED;
                this.l.c[i3] = solverVariable;
            }
        }
        return solverVariable;
    }

    public b b() {
        b a2 = this.l.a.a();
        if (a2 == null) {
            a2 = new b(this.l);
        } else {
            a2.d();
        }
        SolverVariable.b();
        return a2;
    }

    public SolverVariable c() {
        f fVar = q;
        if (fVar != null) {
            fVar.m++;
        }
        if (this.f368i + 1 >= this.e) {
            i();
        }
        SolverVariable a2 = a(SolverVariable.Type.SLACK, (String) null);
        int i2 = this.a + 1;
        this.a = i2;
        this.f368i++;
        a2.b = i2;
        this.l.c[i2] = a2;
        return a2;
    }

    public void e() {
        f fVar = q;
        if (fVar != null) {
            fVar.e++;
        }
        if (this.f366g) {
            f fVar2 = q;
            if (fVar2 != null) {
                fVar2.q++;
            }
            boolean z = false;
            int i2 = 0;
            while (true) {
                if (i2 >= this.f369j) {
                    z = true;
                    break;
                } else if (!this.f365f[i2].e) {
                    break;
                } else {
                    i2++;
                }
            }
            if (!z) {
                a(this.c);
                return;
            }
            f fVar3 = q;
            if (fVar3 != null) {
                fVar3.p++;
            }
            g();
            return;
        }
        a(this.c);
    }

    public void f() {
        c cVar;
        int i2 = 0;
        while (true) {
            cVar = this.l;
            SolverVariable[] solverVariableArr = cVar.c;
            if (i2 >= solverVariableArr.length) {
                break;
            }
            SolverVariable solverVariable = solverVariableArr[i2];
            if (solverVariable != null) {
                solverVariable.a();
            }
            i2++;
        }
        cVar.b.a(this.m, this.n);
        this.n = 0;
        Arrays.fill(this.l.c, (Object) null);
        HashMap<String, SolverVariable> hashMap = this.b;
        if (hashMap != null) {
            hashMap.clear();
        }
        this.a = 0;
        this.c.clear();
        this.f368i = 1;
        for (int i3 = 0; i3 < this.f369j; i3++) {
            this.f365f[i3].c = false;
        }
        j();
        this.f369j = 0;
    }

    private void b(b bVar) {
        bVar.a(this, 0);
    }

    public c d() {
        return this.l;
    }

    public int b(Object obj) {
        SolverVariable e2 = ((ConstraintAnchor) obj).e();
        if (e2 != null) {
            return (int) (e2.e + 0.5f);
        }
        return 0;
    }

    private int b(a aVar) {
        float f2;
        boolean z;
        int i2 = 0;
        while (true) {
            f2 = 0.0f;
            if (i2 >= this.f369j) {
                z = false;
                break;
            }
            b[] bVarArr = this.f365f;
            if (bVarArr[i2].a.f356g != SolverVariable.Type.UNRESTRICTED && bVarArr[i2].b < 0.0f) {
                z = true;
                break;
            }
            i2++;
        }
        if (!z) {
            return 0;
        }
        boolean z2 = false;
        int i3 = 0;
        while (!z2) {
            f fVar = q;
            if (fVar != null) {
                fVar.k++;
            }
            i3++;
            float f3 = Float.MAX_VALUE;
            int i4 = 0;
            int i5 = -1;
            int i6 = -1;
            int i7 = 0;
            while (i4 < this.f369j) {
                b bVar = this.f365f[i4];
                if (bVar.a.f356g != SolverVariable.Type.UNRESTRICTED && !bVar.e && bVar.b < f2) {
                    int i8 = 1;
                    while (i8 < this.f368i) {
                        SolverVariable solverVariable = this.l.c[i8];
                        float b2 = bVar.d.b(solverVariable);
                        if (b2 > f2) {
                            for (int i9 = 0; i9 < 7; i9++) {
                                float f4 = solverVariable.f355f[i9] / b2;
                                if ((f4 < f3 && i9 == i7) || i9 > i7) {
                                    i6 = i8;
                                    i7 = i9;
                                    f3 = f4;
                                    i5 = i4;
                                }
                            }
                        }
                        i8++;
                        f2 = 0.0f;
                    }
                }
                i4++;
                f2 = 0.0f;
            }
            if (i5 != -1) {
                b bVar2 = this.f365f[i5];
                bVar2.a.c = -1;
                f fVar2 = q;
                if (fVar2 != null) {
                    fVar2.f374j++;
                }
                bVar2.d(this.l.c[i6]);
                SolverVariable solverVariable2 = bVar2.a;
                solverVariable2.c = i5;
                solverVariable2.c(bVar2);
            } else {
                z2 = true;
            }
            if (i3 > this.f368i / 2) {
                z2 = true;
            }
            f2 = 0.0f;
        }
        return i3;
    }

    private final void c(b bVar) {
        b[] bVarArr = this.f365f;
        int i2 = this.f369j;
        if (bVarArr[i2] != null) {
            this.l.a.a(bVarArr[i2]);
        }
        b[] bVarArr2 = this.f365f;
        int i3 = this.f369j;
        bVarArr2[i3] = bVar;
        SolverVariable solverVariable = bVar.a;
        solverVariable.c = i3;
        this.f369j = i3 + 1;
        solverVariable.c(bVar);
    }

    public SolverVariable a() {
        f fVar = q;
        if (fVar != null) {
            fVar.n++;
        }
        if (this.f368i + 1 >= this.e) {
            i();
        }
        SolverVariable a2 = a(SolverVariable.Type.SLACK, (String) null);
        int i2 = this.a + 1;
        this.a = i2;
        this.f368i++;
        a2.b = i2;
        this.l.c[i2] = a2;
        return a2;
    }

    public void c(SolverVariable solverVariable, SolverVariable solverVariable2, int i2, int i3) {
        b b2 = b();
        SolverVariable c2 = c();
        c2.d = 0;
        b2.b(solverVariable, solverVariable2, c2, i2);
        if (i3 != 6) {
            a(b2, (int) (b2.d.b(c2) * -1.0f), i3);
        }
        a(b2);
    }

    /* access modifiers changed from: package-private */
    public void a(b bVar, int i2, int i3) {
        bVar.a(a(i3, (String) null), i2);
    }

    public SolverVariable a(int i2, String str) {
        f fVar = q;
        if (fVar != null) {
            fVar.l++;
        }
        if (this.f368i + 1 >= this.e) {
            i();
        }
        SolverVariable a2 = a(SolverVariable.Type.ERROR, str);
        int i3 = this.a + 1;
        this.a = i3;
        this.f368i++;
        a2.b = i3;
        a2.d = i2;
        this.l.c[i3] = a2;
        this.c.a(a2);
        return a2;
    }

    public void b(SolverVariable solverVariable, SolverVariable solverVariable2, int i2, int i3) {
        b b2 = b();
        SolverVariable c2 = c();
        c2.d = 0;
        b2.a(solverVariable, solverVariable2, c2, i2);
        if (i3 != 6) {
            a(b2, (int) (b2.d.b(c2) * -1.0f), i3);
        }
        a(b2);
    }

    private SolverVariable a(SolverVariable.Type type, String str) {
        SolverVariable a2 = this.l.b.a();
        if (a2 == null) {
            a2 = new SolverVariable(type, str);
            a2.a(type, str);
        } else {
            a2.a();
            a2.a(type, str);
        }
        int i2 = this.n;
        int i3 = p;
        if (i2 >= i3) {
            int i4 = i3 * 2;
            p = i4;
            this.m = (SolverVariable[]) Arrays.copyOf(this.m, i4);
        }
        SolverVariable[] solverVariableArr = this.m;
        int i5 = this.n;
        this.n = i5 + 1;
        solverVariableArr[i5] = a2;
        return a2;
    }

    public void b(SolverVariable solverVariable, SolverVariable solverVariable2, boolean z) {
        b b2 = b();
        SolverVariable c2 = c();
        c2.d = 0;
        b2.b(solverVariable, solverVariable2, c2, 0);
        if (z) {
            a(b2, (int) (b2.d.b(c2) * -1.0f), 1);
        }
        a(b2);
    }

    /* access modifiers changed from: package-private */
    public void a(a aVar) {
        f fVar = q;
        if (fVar != null) {
            fVar.s++;
            fVar.t = Math.max(fVar.t, (long) this.f368i);
            f fVar2 = q;
            fVar2.u = Math.max(fVar2.u, (long) this.f369j);
        }
        d((b) aVar);
        b(aVar);
        a(aVar, false);
        g();
    }

    public void a(b bVar) {
        SolverVariable c2;
        if (bVar != null) {
            f fVar = q;
            if (fVar != null) {
                fVar.f370f++;
                if (bVar.e) {
                    fVar.f371g++;
                }
            }
            boolean z = true;
            if (this.f369j + 1 >= this.k || this.f368i + 1 >= this.e) {
                i();
            }
            boolean z2 = false;
            if (!bVar.e) {
                d(bVar);
                if (!bVar.c()) {
                    bVar.a();
                    if (bVar.a(this)) {
                        SolverVariable a2 = a();
                        bVar.a = a2;
                        c(bVar);
                        this.o.a((a) bVar);
                        a(this.o, true);
                        if (a2.c == -1) {
                            if (bVar.a == a2 && (c2 = bVar.c(a2)) != null) {
                                f fVar2 = q;
                                if (fVar2 != null) {
                                    fVar2.f374j++;
                                }
                                bVar.d(c2);
                            }
                            if (!bVar.e) {
                                bVar.a.c(bVar);
                            }
                            this.f369j--;
                        }
                    } else {
                        z = false;
                    }
                    if (bVar.b()) {
                        z2 = z;
                    } else {
                        return;
                    }
                } else {
                    return;
                }
            }
            if (!z2) {
                c(bVar);
            }
        }
    }

    private final int a(a aVar, boolean z) {
        f fVar = q;
        if (fVar != null) {
            fVar.f372h++;
        }
        for (int i2 = 0; i2 < this.f368i; i2++) {
            this.f367h[i2] = false;
        }
        boolean z2 = false;
        int i3 = 0;
        while (!z2) {
            f fVar2 = q;
            if (fVar2 != null) {
                fVar2.f373i++;
            }
            i3++;
            if (i3 >= this.f368i * 2) {
                return i3;
            }
            if (aVar.getKey() != null) {
                this.f367h[aVar.getKey().b] = true;
            }
            SolverVariable a2 = aVar.a(this, this.f367h);
            if (a2 != null) {
                boolean[] zArr = this.f367h;
                int i4 = a2.b;
                if (zArr[i4]) {
                    return i3;
                }
                zArr[i4] = true;
            }
            if (a2 != null) {
                float f2 = Float.MAX_VALUE;
                int i5 = -1;
                for (int i6 = 0; i6 < this.f369j; i6++) {
                    b bVar = this.f365f[i6];
                    if (bVar.a.f356g != SolverVariable.Type.UNRESTRICTED && !bVar.e && bVar.b(a2)) {
                        float b2 = bVar.d.b(a2);
                        if (b2 < 0.0f) {
                            float f3 = (-bVar.b) / b2;
                            if (f3 < f2) {
                                i5 = i6;
                                f2 = f3;
                            }
                        }
                    }
                }
                if (i5 > -1) {
                    b bVar2 = this.f365f[i5];
                    bVar2.a.c = -1;
                    f fVar3 = q;
                    if (fVar3 != null) {
                        fVar3.f374j++;
                    }
                    bVar2.d(a2);
                    SolverVariable solverVariable = bVar2.a;
                    solverVariable.c = i5;
                    solverVariable.c(bVar2);
                }
            }
            z2 = true;
        }
        return i3;
    }

    public void a(SolverVariable solverVariable, SolverVariable solverVariable2, boolean z) {
        b b2 = b();
        SolverVariable c2 = c();
        c2.d = 0;
        b2.a(solverVariable, solverVariable2, c2, 0);
        if (z) {
            a(b2, (int) (b2.d.b(c2) * -1.0f), 1);
        }
        a(b2);
    }

    public void a(SolverVariable solverVariable, SolverVariable solverVariable2, int i2, float f2, SolverVariable solverVariable3, SolverVariable solverVariable4, int i3, int i4) {
        int i5 = i4;
        b b2 = b();
        b2.a(solverVariable, solverVariable2, i2, f2, solverVariable3, solverVariable4, i3);
        if (i5 != 6) {
            b2.a(this, i5);
        }
        a(b2);
    }

    public void a(SolverVariable solverVariable, SolverVariable solverVariable2, SolverVariable solverVariable3, SolverVariable solverVariable4, float f2, int i2) {
        b b2 = b();
        b2.a(solverVariable, solverVariable2, solverVariable3, solverVariable4, f2);
        if (i2 != 6) {
            b2.a(this, i2);
        }
        a(b2);
    }

    public b a(SolverVariable solverVariable, SolverVariable solverVariable2, int i2, int i3) {
        b b2 = b();
        b2.a(solverVariable, solverVariable2, i2);
        if (i3 != 6) {
            b2.a(this, i3);
        }
        a(b2);
        return b2;
    }

    public void a(SolverVariable solverVariable, int i2) {
        int i3 = solverVariable.c;
        if (i3 != -1) {
            b bVar = this.f365f[i3];
            if (bVar.e) {
                bVar.b = (float) i2;
            } else if (bVar.d.a == 0) {
                bVar.e = true;
                bVar.b = (float) i2;
            } else {
                b b2 = b();
                b2.c(solverVariable, i2);
                a(b2);
            }
        } else {
            b b3 = b();
            b3.b(solverVariable, i2);
            a(b3);
        }
    }

    public static b a(e eVar, SolverVariable solverVariable, SolverVariable solverVariable2, SolverVariable solverVariable3, float f2, boolean z) {
        b b2 = eVar.b();
        if (z) {
            eVar.b(b2);
        }
        b2.a(solverVariable, solverVariable2, solverVariable3, f2);
        return b2;
    }

    public void a(ConstraintWidget constraintWidget, ConstraintWidget constraintWidget2, float f2, int i2) {
        ConstraintWidget constraintWidget3 = constraintWidget;
        ConstraintWidget constraintWidget4 = constraintWidget2;
        SolverVariable a2 = a((Object) constraintWidget3.a(ConstraintAnchor.Type.LEFT));
        SolverVariable a3 = a((Object) constraintWidget3.a(ConstraintAnchor.Type.TOP));
        SolverVariable a4 = a((Object) constraintWidget3.a(ConstraintAnchor.Type.RIGHT));
        SolverVariable a5 = a((Object) constraintWidget3.a(ConstraintAnchor.Type.BOTTOM));
        SolverVariable a6 = a((Object) constraintWidget4.a(ConstraintAnchor.Type.LEFT));
        SolverVariable a7 = a((Object) constraintWidget4.a(ConstraintAnchor.Type.TOP));
        SolverVariable a8 = a((Object) constraintWidget4.a(ConstraintAnchor.Type.RIGHT));
        SolverVariable a9 = a((Object) constraintWidget4.a(ConstraintAnchor.Type.BOTTOM));
        b b2 = b();
        double d2 = (double) f2;
        double sin = Math.sin(d2);
        SolverVariable solverVariable = a4;
        double d3 = (double) i2;
        Double.isNaN(d3);
        b2.b(a3, a5, a7, a9, (float) (sin * d3));
        a(b2);
        b b3 = b();
        double cos = Math.cos(d2);
        Double.isNaN(d3);
        b3.b(a2, solverVariable, a6, a8, (float) (cos * d3));
        a(b3);
    }
}
