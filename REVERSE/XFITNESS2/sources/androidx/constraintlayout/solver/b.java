package androidx.constraintlayout.solver;

import androidx.constraintlayout.solver.SolverVariable;
import androidx.constraintlayout.solver.e;

/* compiled from: ArrayRow */
public class b implements e.a {
    SolverVariable a = null;
    float b = 0.0f;
    boolean c;
    public final a d;
    boolean e = false;

    public b(c cVar) {
        this.d = new a(this, cVar);
    }

    public b a(SolverVariable solverVariable, SolverVariable solverVariable2, int i2) {
        boolean z = false;
        if (i2 != 0) {
            if (i2 < 0) {
                i2 *= -1;
                z = true;
            }
            this.b = (float) i2;
        }
        if (!z) {
            this.d.a(solverVariable, -1.0f);
            this.d.a(solverVariable2, 1.0f);
        } else {
            this.d.a(solverVariable, 1.0f);
            this.d.a(solverVariable2, -1.0f);
        }
        return this;
    }

    /* access modifiers changed from: package-private */
    public boolean b() {
        SolverVariable solverVariable = this.a;
        return solverVariable != null && (solverVariable.f356g == SolverVariable.Type.UNRESTRICTED || this.b >= 0.0f);
    }

    public b c(SolverVariable solverVariable, int i2) {
        if (i2 < 0) {
            this.b = (float) (i2 * -1);
            this.d.a(solverVariable, 1.0f);
        } else {
            this.b = (float) i2;
            this.d.a(solverVariable, -1.0f);
        }
        return this;
    }

    public void clear() {
        this.d.a();
        this.a = null;
        this.b = 0.0f;
    }

    public void d() {
        this.a = null;
        this.d.a();
        this.b = 0.0f;
        this.e = false;
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x00c0  */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x00d0  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.String e() {
        /*
            r9 = this;
            androidx.constraintlayout.solver.SolverVariable r0 = r9.a
            java.lang.String r1 = ""
            if (r0 != 0) goto L_0x0018
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            r0.append(r1)
            java.lang.String r1 = "0"
            r0.append(r1)
            java.lang.String r0 = r0.toString()
            goto L_0x0029
        L_0x0018:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            r0.append(r1)
            androidx.constraintlayout.solver.SolverVariable r1 = r9.a
            r0.append(r1)
            java.lang.String r0 = r0.toString()
        L_0x0029:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            r1.append(r0)
            java.lang.String r0 = " = "
            r1.append(r0)
            java.lang.String r0 = r1.toString()
            float r1 = r9.b
            r2 = 0
            r3 = 1
            r4 = 0
            int r1 = (r1 > r4 ? 1 : (r1 == r4 ? 0 : -1))
            if (r1 == 0) goto L_0x0056
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            r1.append(r0)
            float r0 = r9.b
            r1.append(r0)
            java.lang.String r0 = r1.toString()
            r1 = 1
            goto L_0x0057
        L_0x0056:
            r1 = 0
        L_0x0057:
            androidx.constraintlayout.solver.a r5 = r9.d
            int r5 = r5.a
        L_0x005b:
            if (r2 >= r5) goto L_0x00ec
            androidx.constraintlayout.solver.a r6 = r9.d
            androidx.constraintlayout.solver.SolverVariable r6 = r6.a((int) r2)
            if (r6 != 0) goto L_0x0067
            goto L_0x00e8
        L_0x0067:
            androidx.constraintlayout.solver.a r7 = r9.d
            float r7 = r7.b((int) r2)
            int r8 = (r7 > r4 ? 1 : (r7 == r4 ? 0 : -1))
            if (r8 != 0) goto L_0x0073
            goto L_0x00e8
        L_0x0073:
            java.lang.String r6 = r6.toString()
            r8 = -1082130432(0xffffffffbf800000, float:-1.0)
            if (r1 != 0) goto L_0x0091
            int r1 = (r7 > r4 ? 1 : (r7 == r4 ? 0 : -1))
            if (r1 >= 0) goto L_0x00ba
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            r1.append(r0)
            java.lang.String r0 = "- "
            r1.append(r0)
            java.lang.String r0 = r1.toString()
            goto L_0x00b8
        L_0x0091:
            int r1 = (r7 > r4 ? 1 : (r7 == r4 ? 0 : -1))
            if (r1 <= 0) goto L_0x00a7
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            r1.append(r0)
            java.lang.String r0 = " + "
            r1.append(r0)
            java.lang.String r0 = r1.toString()
            goto L_0x00ba
        L_0x00a7:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            r1.append(r0)
            java.lang.String r0 = " - "
            r1.append(r0)
            java.lang.String r0 = r1.toString()
        L_0x00b8:
            float r7 = r7 * r8
        L_0x00ba:
            r1 = 1065353216(0x3f800000, float:1.0)
            int r1 = (r7 > r1 ? 1 : (r7 == r1 ? 0 : -1))
            if (r1 != 0) goto L_0x00d0
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            r1.append(r0)
            r1.append(r6)
            java.lang.String r0 = r1.toString()
            goto L_0x00e7
        L_0x00d0:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            r1.append(r0)
            r1.append(r7)
            java.lang.String r0 = " "
            r1.append(r0)
            r1.append(r6)
            java.lang.String r0 = r1.toString()
        L_0x00e7:
            r1 = 1
        L_0x00e8:
            int r2 = r2 + 1
            goto L_0x005b
        L_0x00ec:
            if (r1 != 0) goto L_0x00ff
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            r1.append(r0)
            java.lang.String r0 = "0.0"
            r1.append(r0)
            java.lang.String r0 = r1.toString()
        L_0x00ff:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.solver.b.e():java.lang.String");
    }

    public SolverVariable getKey() {
        return this.a;
    }

    public String toString() {
        return e();
    }

    /* access modifiers changed from: package-private */
    public boolean b(SolverVariable solverVariable) {
        return this.d.a(solverVariable);
    }

    /* access modifiers changed from: package-private */
    public b b(SolverVariable solverVariable, int i2) {
        this.a = solverVariable;
        float f2 = (float) i2;
        solverVariable.e = f2;
        this.b = f2;
        this.e = true;
        return this;
    }

    /* access modifiers changed from: package-private */
    public SolverVariable c(SolverVariable solverVariable) {
        return this.d.a((boolean[]) null, solverVariable);
    }

    /* access modifiers changed from: package-private */
    public void d(SolverVariable solverVariable) {
        SolverVariable solverVariable2 = this.a;
        if (solverVariable2 != null) {
            this.d.a(solverVariable2, -1.0f);
            this.a = null;
        }
        float a2 = this.d.a(solverVariable, true) * -1.0f;
        this.a = solverVariable;
        if (a2 != 1.0f) {
            this.b /= a2;
            this.d.a(a2);
        }
    }

    /* access modifiers changed from: package-private */
    public b a(SolverVariable solverVariable, int i2) {
        this.d.a(solverVariable, (float) i2);
        return this;
    }

    public boolean c() {
        return this.a == null && this.b == 0.0f && this.d.a == 0;
    }

    public b a(SolverVariable solverVariable, SolverVariable solverVariable2, SolverVariable solverVariable3, int i2) {
        boolean z = false;
        if (i2 != 0) {
            if (i2 < 0) {
                i2 *= -1;
                z = true;
            }
            this.b = (float) i2;
        }
        if (!z) {
            this.d.a(solverVariable, -1.0f);
            this.d.a(solverVariable2, 1.0f);
            this.d.a(solverVariable3, 1.0f);
        } else {
            this.d.a(solverVariable, 1.0f);
            this.d.a(solverVariable2, -1.0f);
            this.d.a(solverVariable3, -1.0f);
        }
        return this;
    }

    public b b(SolverVariable solverVariable, SolverVariable solverVariable2, SolverVariable solverVariable3, int i2) {
        boolean z = false;
        if (i2 != 0) {
            if (i2 < 0) {
                i2 *= -1;
                z = true;
            }
            this.b = (float) i2;
        }
        if (!z) {
            this.d.a(solverVariable, -1.0f);
            this.d.a(solverVariable2, 1.0f);
            this.d.a(solverVariable3, -1.0f);
        } else {
            this.d.a(solverVariable, 1.0f);
            this.d.a(solverVariable2, -1.0f);
            this.d.a(solverVariable3, 1.0f);
        }
        return this;
    }

    public b a(float f2, float f3, float f4, SolverVariable solverVariable, SolverVariable solverVariable2, SolverVariable solverVariable3, SolverVariable solverVariable4) {
        this.b = 0.0f;
        if (f3 == 0.0f || f2 == f4) {
            this.d.a(solverVariable, 1.0f);
            this.d.a(solverVariable2, -1.0f);
            this.d.a(solverVariable4, 1.0f);
            this.d.a(solverVariable3, -1.0f);
        } else if (f2 == 0.0f) {
            this.d.a(solverVariable, 1.0f);
            this.d.a(solverVariable2, -1.0f);
        } else if (f4 == 0.0f) {
            this.d.a(solverVariable3, 1.0f);
            this.d.a(solverVariable4, -1.0f);
        } else {
            float f5 = (f2 / f3) / (f4 / f3);
            this.d.a(solverVariable, 1.0f);
            this.d.a(solverVariable2, -1.0f);
            this.d.a(solverVariable4, f5);
            this.d.a(solverVariable3, -f5);
        }
        return this;
    }

    public b b(SolverVariable solverVariable, SolverVariable solverVariable2, SolverVariable solverVariable3, SolverVariable solverVariable4, float f2) {
        this.d.a(solverVariable3, 0.5f);
        this.d.a(solverVariable4, 0.5f);
        this.d.a(solverVariable, -0.5f);
        this.d.a(solverVariable2, -0.5f);
        this.b = -f2;
        return this;
    }

    /* access modifiers changed from: package-private */
    public b a(SolverVariable solverVariable, SolverVariable solverVariable2, int i2, float f2, SolverVariable solverVariable3, SolverVariable solverVariable4, int i3) {
        if (solverVariable2 == solverVariable3) {
            this.d.a(solverVariable, 1.0f);
            this.d.a(solverVariable4, 1.0f);
            this.d.a(solverVariable2, -2.0f);
            return this;
        }
        if (f2 == 0.5f) {
            this.d.a(solverVariable, 1.0f);
            this.d.a(solverVariable2, -1.0f);
            this.d.a(solverVariable3, -1.0f);
            this.d.a(solverVariable4, 1.0f);
            if (i2 > 0 || i3 > 0) {
                this.b = (float) ((-i2) + i3);
            }
        } else if (f2 <= 0.0f) {
            this.d.a(solverVariable, -1.0f);
            this.d.a(solverVariable2, 1.0f);
            this.b = (float) i2;
        } else if (f2 >= 1.0f) {
            this.d.a(solverVariable3, -1.0f);
            this.d.a(solverVariable4, 1.0f);
            this.b = (float) i3;
        } else {
            float f3 = 1.0f - f2;
            this.d.a(solverVariable, f3 * 1.0f);
            this.d.a(solverVariable2, f3 * -1.0f);
            this.d.a(solverVariable3, -1.0f * f2);
            this.d.a(solverVariable4, 1.0f * f2);
            if (i2 > 0 || i3 > 0) {
                this.b = (((float) (-i2)) * f3) + (((float) i3) * f2);
            }
        }
        return this;
    }

    public b a(e eVar, int i2) {
        this.d.a(eVar.a(i2, "ep"), 1.0f);
        this.d.a(eVar.a(i2, "em"), -1.0f);
        return this;
    }

    /* access modifiers changed from: package-private */
    public b a(SolverVariable solverVariable, SolverVariable solverVariable2, SolverVariable solverVariable3, float f2) {
        this.d.a(solverVariable, -1.0f);
        this.d.a(solverVariable2, 1.0f - f2);
        this.d.a(solverVariable3, f2);
        return this;
    }

    public b a(SolverVariable solverVariable, SolverVariable solverVariable2, SolverVariable solverVariable3, SolverVariable solverVariable4, float f2) {
        this.d.a(solverVariable, -1.0f);
        this.d.a(solverVariable2, 1.0f);
        this.d.a(solverVariable3, f2);
        this.d.a(solverVariable4, -f2);
        return this;
    }

    /* access modifiers changed from: package-private */
    public void a() {
        float f2 = this.b;
        if (f2 < 0.0f) {
            this.b = f2 * -1.0f;
            this.d.b();
        }
    }

    /* access modifiers changed from: package-private */
    public boolean a(e eVar) {
        boolean z;
        SolverVariable a2 = this.d.a(eVar);
        if (a2 == null) {
            z = true;
        } else {
            d(a2);
            z = false;
        }
        if (this.d.a == 0) {
            this.e = true;
        }
        return z;
    }

    public SolverVariable a(e eVar, boolean[] zArr) {
        return this.d.a(zArr, (SolverVariable) null);
    }

    public void a(e.a aVar) {
        if (aVar instanceof b) {
            b bVar = (b) aVar;
            this.a = null;
            this.d.a();
            int i2 = 0;
            while (true) {
                a aVar2 = bVar.d;
                if (i2 < aVar2.a) {
                    this.d.a(aVar2.a(i2), bVar.d.b(i2), true);
                    i2++;
                } else {
                    return;
                }
            }
        }
    }

    public void a(SolverVariable solverVariable) {
        int i2 = solverVariable.d;
        float f2 = 1.0f;
        if (i2 != 1) {
            if (i2 == 2) {
                f2 = 1000.0f;
            } else if (i2 == 3) {
                f2 = 1000000.0f;
            } else if (i2 == 4) {
                f2 = 1.0E9f;
            } else if (i2 == 5) {
                f2 = 1.0E12f;
            }
        }
        this.d.a(solverVariable, f2);
    }
}
