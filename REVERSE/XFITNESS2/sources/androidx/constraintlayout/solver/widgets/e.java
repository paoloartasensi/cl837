package androidx.constraintlayout.solver.widgets;

import androidx.constraintlayout.solver.widgets.ConstraintAnchor;
import androidx.constraintlayout.solver.widgets.ConstraintWidget;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* compiled from: ConstraintWidgetContainer */
public class e extends o {
    public int A0 = 0;
    public int B0 = 0;
    private int C0 = 7;
    public boolean D0 = false;
    private boolean E0 = false;
    private boolean F0 = false;
    private boolean l0 = false;
    protected androidx.constraintlayout.solver.e m0 = new androidx.constraintlayout.solver.e();
    private n n0;
    int o0;
    int p0;
    int q0;
    int r0;
    int s0 = 0;
    int t0 = 0;
    d[] u0 = new d[4];
    d[] v0 = new d[4];
    public List<f> w0 = new ArrayList();
    public boolean x0 = false;
    public boolean y0 = false;
    public boolean z0 = false;

    private void V() {
        this.s0 = 0;
        this.t0 = 0;
    }

    private void e(ConstraintWidget constraintWidget) {
        int i2 = this.t0 + 1;
        d[] dVarArr = this.u0;
        if (i2 >= dVarArr.length) {
            this.u0 = (d[]) Arrays.copyOf(dVarArr, dVarArr.length * 2);
        }
        this.u0[this.t0] = new d(constraintWidget, 1, P());
        this.t0++;
    }

    public void D() {
        this.m0.f();
        this.o0 = 0;
        this.q0 = 0;
        this.p0 = 0;
        this.r0 = 0;
        this.w0.clear();
        this.D0 = false;
        super.D();
    }

    /* JADX WARNING: type inference failed for: r8v17, types: [boolean] */
    /* JADX WARNING: type inference failed for: r8v21 */
    /* JADX WARNING: type inference failed for: r8v22 */
    /* JADX WARNING: Removed duplicated region for block: B:103:0x0250  */
    /* JADX WARNING: Removed duplicated region for block: B:106:0x0263  */
    /* JADX WARNING: Removed duplicated region for block: B:109:0x0280  */
    /* JADX WARNING: Removed duplicated region for block: B:110:0x028d  */
    /* JADX WARNING: Removed duplicated region for block: B:112:0x0292  */
    /* JADX WARNING: Removed duplicated region for block: B:69:0x0186  */
    /* JADX WARNING: Removed duplicated region for block: B:71:0x018f  */
    /* JADX WARNING: Removed duplicated region for block: B:89:0x01e4  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void K() {
        /*
            r21 = this;
            r1 = r21
            int r2 = r1.I
            int r3 = r1.J
            int r0 = r21.s()
            r4 = 0
            int r5 = java.lang.Math.max(r4, r0)
            int r0 = r21.i()
            int r6 = java.lang.Math.max(r4, r0)
            r1.E0 = r4
            r1.F0 = r4
            androidx.constraintlayout.solver.widgets.ConstraintWidget r0 = r1.D
            if (r0 == 0) goto L_0x0046
            androidx.constraintlayout.solver.widgets.n r0 = r1.n0
            if (r0 != 0) goto L_0x002a
            androidx.constraintlayout.solver.widgets.n r0 = new androidx.constraintlayout.solver.widgets.n
            r0.<init>(r1)
            r1.n0 = r0
        L_0x002a:
            androidx.constraintlayout.solver.widgets.n r0 = r1.n0
            r0.b(r1)
            int r0 = r1.o0
            r1.r(r0)
            int r0 = r1.p0
            r1.s(r0)
            r21.E()
            androidx.constraintlayout.solver.e r0 = r1.m0
            androidx.constraintlayout.solver.c r0 = r0.d()
            r1.a(r0)
            goto L_0x004a
        L_0x0046:
            r1.I = r4
            r1.J = r4
        L_0x004a:
            int r0 = r1.C0
            r7 = 32
            r8 = 8
            r9 = 1
            if (r0 == 0) goto L_0x006a
            boolean r0 = r1.t(r8)
            if (r0 != 0) goto L_0x005c
            r21.S()
        L_0x005c:
            boolean r0 = r1.t(r7)
            if (r0 != 0) goto L_0x0065
            r21.R()
        L_0x0065:
            androidx.constraintlayout.solver.e r0 = r1.m0
            r0.f366g = r9
            goto L_0x006e
        L_0x006a:
            androidx.constraintlayout.solver.e r0 = r1.m0
            r0.f366g = r4
        L_0x006e:
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour[] r0 = r1.C
            r10 = r0[r9]
            r11 = r0[r4]
            r21.V()
            java.util.List<androidx.constraintlayout.solver.widgets.f> r0 = r1.w0
            int r0 = r0.size()
            if (r0 != 0) goto L_0x0090
            java.util.List<androidx.constraintlayout.solver.widgets.f> r0 = r1.w0
            r0.clear()
            java.util.List<androidx.constraintlayout.solver.widgets.f> r0 = r1.w0
            androidx.constraintlayout.solver.widgets.f r12 = new androidx.constraintlayout.solver.widgets.f
            java.util.ArrayList<androidx.constraintlayout.solver.widgets.ConstraintWidget> r13 = r1.k0
            r12.<init>(r13)
            r0.add(r4, r12)
        L_0x0090:
            java.util.List<androidx.constraintlayout.solver.widgets.f> r0 = r1.w0
            int r12 = r0.size()
            java.util.ArrayList<androidx.constraintlayout.solver.widgets.ConstraintWidget> r13 = r1.k0
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour r0 = r21.j()
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour r14 = androidx.constraintlayout.solver.widgets.ConstraintWidget.DimensionBehaviour.WRAP_CONTENT
            if (r0 == r14) goto L_0x00ab
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour r0 = r21.q()
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour r14 = androidx.constraintlayout.solver.widgets.ConstraintWidget.DimensionBehaviour.WRAP_CONTENT
            if (r0 != r14) goto L_0x00a9
            goto L_0x00ab
        L_0x00a9:
            r14 = 0
            goto L_0x00ac
        L_0x00ab:
            r14 = 1
        L_0x00ac:
            r0 = 0
            r15 = 0
        L_0x00ae:
            if (r15 >= r12) goto L_0x02f4
            boolean r8 = r1.D0
            if (r8 != 0) goto L_0x02f4
            java.util.List<androidx.constraintlayout.solver.widgets.f> r8 = r1.w0
            java.lang.Object r8 = r8.get(r15)
            androidx.constraintlayout.solver.widgets.f r8 = (androidx.constraintlayout.solver.widgets.f) r8
            boolean r8 = r8.d
            if (r8 == 0) goto L_0x00c4
            r19 = r12
            goto L_0x02e8
        L_0x00c4:
            boolean r8 = r1.t(r7)
            if (r8 == 0) goto L_0x00f9
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour r8 = r21.j()
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour r7 = androidx.constraintlayout.solver.widgets.ConstraintWidget.DimensionBehaviour.FIXED
            if (r8 != r7) goto L_0x00eb
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour r7 = r21.q()
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour r8 = androidx.constraintlayout.solver.widgets.ConstraintWidget.DimensionBehaviour.FIXED
            if (r7 != r8) goto L_0x00eb
            java.util.List<androidx.constraintlayout.solver.widgets.f> r7 = r1.w0
            java.lang.Object r7 = r7.get(r15)
            androidx.constraintlayout.solver.widgets.f r7 = (androidx.constraintlayout.solver.widgets.f) r7
            java.util.List r7 = r7.a()
            java.util.ArrayList r7 = (java.util.ArrayList) r7
            r1.k0 = r7
            goto L_0x00f9
        L_0x00eb:
            java.util.List<androidx.constraintlayout.solver.widgets.f> r7 = r1.w0
            java.lang.Object r7 = r7.get(r15)
            androidx.constraintlayout.solver.widgets.f r7 = (androidx.constraintlayout.solver.widgets.f) r7
            java.util.List<androidx.constraintlayout.solver.widgets.ConstraintWidget> r7 = r7.a
            java.util.ArrayList r7 = (java.util.ArrayList) r7
            r1.k0 = r7
        L_0x00f9:
            r21.V()
            java.util.ArrayList<androidx.constraintlayout.solver.widgets.ConstraintWidget> r7 = r1.k0
            int r7 = r7.size()
            r8 = 0
        L_0x0103:
            if (r8 >= r7) goto L_0x011b
            java.util.ArrayList<androidx.constraintlayout.solver.widgets.ConstraintWidget> r4 = r1.k0
            java.lang.Object r4 = r4.get(r8)
            androidx.constraintlayout.solver.widgets.ConstraintWidget r4 = (androidx.constraintlayout.solver.widgets.ConstraintWidget) r4
            boolean r9 = r4 instanceof androidx.constraintlayout.solver.widgets.o
            if (r9 == 0) goto L_0x0116
            androidx.constraintlayout.solver.widgets.o r4 = (androidx.constraintlayout.solver.widgets.o) r4
            r4.K()
        L_0x0116:
            int r8 = r8 + 1
            r4 = 0
            r9 = 1
            goto L_0x0103
        L_0x011b:
            r4 = r0
            r0 = 0
            r8 = 1
        L_0x011e:
            if (r8 == 0) goto L_0x02d7
            r17 = r4
            r9 = 1
            int r4 = r0 + 1
            androidx.constraintlayout.solver.e r0 = r1.m0     // Catch:{ Exception -> 0x0162 }
            r0.f()     // Catch:{ Exception -> 0x0162 }
            r21.V()     // Catch:{ Exception -> 0x0162 }
            androidx.constraintlayout.solver.e r0 = r1.m0     // Catch:{ Exception -> 0x0162 }
            r1.b((androidx.constraintlayout.solver.e) r0)     // Catch:{ Exception -> 0x0162 }
            r0 = 0
        L_0x0133:
            if (r0 >= r7) goto L_0x0149
            java.util.ArrayList<androidx.constraintlayout.solver.widgets.ConstraintWidget> r9 = r1.k0     // Catch:{ Exception -> 0x0162 }
            java.lang.Object r9 = r9.get(r0)     // Catch:{ Exception -> 0x0162 }
            androidx.constraintlayout.solver.widgets.ConstraintWidget r9 = (androidx.constraintlayout.solver.widgets.ConstraintWidget) r9     // Catch:{ Exception -> 0x0162 }
            r18 = r8
            androidx.constraintlayout.solver.e r8 = r1.m0     // Catch:{ Exception -> 0x015e }
            r9.b((androidx.constraintlayout.solver.e) r8)     // Catch:{ Exception -> 0x015e }
            int r0 = r0 + 1
            r8 = r18
            goto L_0x0133
        L_0x0149:
            r18 = r8
            androidx.constraintlayout.solver.e r0 = r1.m0     // Catch:{ Exception -> 0x015e }
            boolean r8 = r1.d((androidx.constraintlayout.solver.e) r0)     // Catch:{ Exception -> 0x015e }
            if (r8 == 0) goto L_0x015b
            androidx.constraintlayout.solver.e r0 = r1.m0     // Catch:{ Exception -> 0x0159 }
            r0.e()     // Catch:{ Exception -> 0x0159 }
            goto L_0x015b
        L_0x0159:
            r0 = move-exception
            goto L_0x0165
        L_0x015b:
            r19 = r12
            goto L_0x0184
        L_0x015e:
            r0 = move-exception
            r8 = r18
            goto L_0x0165
        L_0x0162:
            r0 = move-exception
            r18 = r8
        L_0x0165:
            r0.printStackTrace()
            java.io.PrintStream r9 = java.lang.System.out
            r18 = r8
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            r8.<init>()
            r19 = r12
            java.lang.String r12 = "EXCEPTION : "
            r8.append(r12)
            r8.append(r0)
            java.lang.String r0 = r8.toString()
            r9.println(r0)
            r8 = r18
        L_0x0184:
            if (r8 == 0) goto L_0x018f
            androidx.constraintlayout.solver.e r8 = r1.m0
            boolean[] r9 = androidx.constraintlayout.solver.widgets.i.a
            r1.a((androidx.constraintlayout.solver.e) r8, (boolean[]) r9)
        L_0x018d:
            r9 = 2
            goto L_0x01d8
        L_0x018f:
            androidx.constraintlayout.solver.e r8 = r1.m0
            r1.c((androidx.constraintlayout.solver.e) r8)
            r8 = 0
        L_0x0195:
            if (r8 >= r7) goto L_0x018d
            java.util.ArrayList<androidx.constraintlayout.solver.widgets.ConstraintWidget> r9 = r1.k0
            java.lang.Object r9 = r9.get(r8)
            androidx.constraintlayout.solver.widgets.ConstraintWidget r9 = (androidx.constraintlayout.solver.widgets.ConstraintWidget) r9
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour[] r12 = r9.C
            r16 = 0
            r12 = r12[r16]
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour r0 = androidx.constraintlayout.solver.widgets.ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT
            if (r12 != r0) goto L_0x01ba
            int r0 = r9.s()
            int r12 = r9.u()
            if (r0 >= r12) goto L_0x01ba
            boolean[] r0 = androidx.constraintlayout.solver.widgets.i.a
            r8 = 2
            r12 = 1
            r0[r8] = r12
            goto L_0x018d
        L_0x01ba:
            r12 = 1
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour[] r0 = r9.C
            r0 = r0[r12]
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour r12 = androidx.constraintlayout.solver.widgets.ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT
            if (r0 != r12) goto L_0x01d4
            int r0 = r9.i()
            int r9 = r9.t()
            if (r0 >= r9) goto L_0x01d4
            boolean[] r0 = androidx.constraintlayout.solver.widgets.i.a
            r8 = 1
            r9 = 2
            r0[r9] = r8
            goto L_0x01d8
        L_0x01d4:
            r9 = 2
            int r8 = r8 + 1
            goto L_0x0195
        L_0x01d8:
            if (r14 == 0) goto L_0x0250
            r8 = 8
            if (r4 >= r8) goto L_0x0250
            boolean[] r0 = androidx.constraintlayout.solver.widgets.i.a
            boolean r0 = r0[r9]
            if (r0 == 0) goto L_0x0250
            r0 = 0
            r9 = 0
            r12 = 0
        L_0x01e7:
            if (r0 >= r7) goto L_0x0211
            java.util.ArrayList<androidx.constraintlayout.solver.widgets.ConstraintWidget> r8 = r1.k0
            java.lang.Object r8 = r8.get(r0)
            androidx.constraintlayout.solver.widgets.ConstraintWidget r8 = (androidx.constraintlayout.solver.widgets.ConstraintWidget) r8
            r18 = r4
            int r4 = r8.I
            int r20 = r8.s()
            int r4 = r4 + r20
            int r9 = java.lang.Math.max(r9, r4)
            int r4 = r8.J
            int r8 = r8.i()
            int r4 = r4 + r8
            int r12 = java.lang.Math.max(r12, r4)
            int r0 = r0 + 1
            r4 = r18
            r8 = 8
            goto L_0x01e7
        L_0x0211:
            r18 = r4
            int r0 = r1.R
            int r0 = java.lang.Math.max(r0, r9)
            int r4 = r1.S
            int r4 = java.lang.Math.max(r4, r12)
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour r8 = androidx.constraintlayout.solver.widgets.ConstraintWidget.DimensionBehaviour.WRAP_CONTENT
            if (r11 != r8) goto L_0x0237
            int r8 = r21.s()
            if (r8 >= r0) goto L_0x0237
            r1.o(r0)
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour[] r0 = r1.C
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour r8 = androidx.constraintlayout.solver.widgets.ConstraintWidget.DimensionBehaviour.WRAP_CONTENT
            r9 = 0
            r0[r9] = r8
            r0 = 1
            r17 = 1
            goto L_0x0238
        L_0x0237:
            r0 = 0
        L_0x0238:
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour r8 = androidx.constraintlayout.solver.widgets.ConstraintWidget.DimensionBehaviour.WRAP_CONTENT
            if (r10 != r8) goto L_0x0253
            int r8 = r21.i()
            if (r8 >= r4) goto L_0x0253
            r1.g(r4)
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour[] r0 = r1.C
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour r4 = androidx.constraintlayout.solver.widgets.ConstraintWidget.DimensionBehaviour.WRAP_CONTENT
            r8 = 1
            r0[r8] = r4
            r0 = 1
            r17 = 1
            goto L_0x0253
        L_0x0250:
            r18 = r4
            r0 = 0
        L_0x0253:
            int r4 = r1.R
            int r8 = r21.s()
            int r4 = java.lang.Math.max(r4, r8)
            int r8 = r21.s()
            if (r4 <= r8) goto L_0x0270
            r1.o(r4)
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour[] r0 = r1.C
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour r4 = androidx.constraintlayout.solver.widgets.ConstraintWidget.DimensionBehaviour.FIXED
            r8 = 0
            r0[r8] = r4
            r0 = 1
            r17 = 1
        L_0x0270:
            int r4 = r1.S
            int r8 = r21.i()
            int r4 = java.lang.Math.max(r4, r8)
            int r8 = r21.i()
            if (r4 <= r8) goto L_0x028d
            r1.g(r4)
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour[] r0 = r1.C
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour r4 = androidx.constraintlayout.solver.widgets.ConstraintWidget.DimensionBehaviour.FIXED
            r8 = 1
            r0[r8] = r4
            r0 = 1
            r9 = 1
            goto L_0x0290
        L_0x028d:
            r8 = 1
            r9 = r17
        L_0x0290:
            if (r9 != 0) goto L_0x02cf
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour[] r4 = r1.C
            r12 = 0
            r4 = r4[r12]
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour r12 = androidx.constraintlayout.solver.widgets.ConstraintWidget.DimensionBehaviour.WRAP_CONTENT
            if (r4 != r12) goto L_0x02b1
            if (r5 <= 0) goto L_0x02b1
            int r4 = r21.s()
            if (r4 <= r5) goto L_0x02b1
            r1.E0 = r8
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour[] r0 = r1.C
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour r4 = androidx.constraintlayout.solver.widgets.ConstraintWidget.DimensionBehaviour.FIXED
            r9 = 0
            r0[r9] = r4
            r1.o(r5)
            r0 = 1
            r9 = 1
        L_0x02b1:
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour[] r4 = r1.C
            r4 = r4[r8]
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour r12 = androidx.constraintlayout.solver.widgets.ConstraintWidget.DimensionBehaviour.WRAP_CONTENT
            if (r4 != r12) goto L_0x02cf
            if (r6 <= 0) goto L_0x02cf
            int r4 = r21.i()
            if (r4 <= r6) goto L_0x02cf
            r1.F0 = r8
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour[] r0 = r1.C
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour r4 = androidx.constraintlayout.solver.widgets.ConstraintWidget.DimensionBehaviour.FIXED
            r0[r8] = r4
            r1.g(r6)
            r4 = 1
            r8 = 1
            goto L_0x02d1
        L_0x02cf:
            r8 = r0
            r4 = r9
        L_0x02d1:
            r0 = r18
            r12 = r19
            goto L_0x011e
        L_0x02d7:
            r17 = r4
            r19 = r12
            java.util.List<androidx.constraintlayout.solver.widgets.f> r0 = r1.w0
            java.lang.Object r0 = r0.get(r15)
            androidx.constraintlayout.solver.widgets.f r0 = (androidx.constraintlayout.solver.widgets.f) r0
            r0.b()
            r0 = r17
        L_0x02e8:
            int r15 = r15 + 1
            r12 = r19
            r4 = 0
            r7 = 32
            r8 = 8
            r9 = 1
            goto L_0x00ae
        L_0x02f4:
            r1.k0 = r13
            androidx.constraintlayout.solver.widgets.ConstraintWidget r4 = r1.D
            if (r4 == 0) goto L_0x0326
            int r2 = r1.R
            int r3 = r21.s()
            int r2 = java.lang.Math.max(r2, r3)
            int r3 = r1.S
            int r4 = r21.i()
            int r3 = java.lang.Math.max(r3, r4)
            androidx.constraintlayout.solver.widgets.n r4 = r1.n0
            r4.a(r1)
            int r4 = r1.o0
            int r2 = r2 + r4
            int r4 = r1.q0
            int r2 = r2 + r4
            r1.o(r2)
            int r2 = r1.p0
            int r3 = r3 + r2
            int r2 = r1.r0
            int r3 = r3 + r2
            r1.g(r3)
            goto L_0x032a
        L_0x0326:
            r1.I = r2
            r1.J = r3
        L_0x032a:
            if (r0 == 0) goto L_0x0334
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour[] r0 = r1.C
            r2 = 0
            r0[r2] = r11
            r2 = 1
            r0[r2] = r10
        L_0x0334:
            androidx.constraintlayout.solver.e r0 = r1.m0
            androidx.constraintlayout.solver.c r0 = r0.d()
            r1.a(r0)
            androidx.constraintlayout.solver.widgets.e r0 = r21.J()
            if (r1 != r0) goto L_0x0346
            r21.H()
        L_0x0346:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.solver.widgets.e.K():void");
    }

    public int M() {
        return this.C0;
    }

    public boolean N() {
        return false;
    }

    public boolean O() {
        return this.F0;
    }

    public boolean P() {
        return this.l0;
    }

    public boolean Q() {
        return this.E0;
    }

    public void R() {
        if (!t(8)) {
            a(this.C0);
        }
        U();
    }

    public void S() {
        int size = this.k0.size();
        F();
        for (int i2 = 0; i2 < size; i2++) {
            this.k0.get(i2).F();
        }
    }

    public void T() {
        S();
        a(this.C0);
    }

    public void U() {
        k d = a(ConstraintAnchor.Type.LEFT).d();
        k d2 = a(ConstraintAnchor.Type.TOP).d();
        d.a((k) null, 0.0f);
        d2.a((k) null, 0.0f);
    }

    public void a(androidx.constraintlayout.solver.e eVar, boolean[] zArr) {
        zArr[2] = false;
        c(eVar);
        int size = this.k0.size();
        for (int i2 = 0; i2 < size; i2++) {
            ConstraintWidget constraintWidget = this.k0.get(i2);
            constraintWidget.c(eVar);
            if (constraintWidget.C[0] == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && constraintWidget.s() < constraintWidget.u()) {
                zArr[2] = true;
            }
            if (constraintWidget.C[1] == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && constraintWidget.i() < constraintWidget.t()) {
                zArr[2] = true;
            }
        }
    }

    public void c(boolean z) {
        this.l0 = z;
    }

    public boolean d(androidx.constraintlayout.solver.e eVar) {
        a(eVar);
        int size = this.k0.size();
        for (int i2 = 0; i2 < size; i2++) {
            ConstraintWidget constraintWidget = this.k0.get(i2);
            if (constraintWidget instanceof e) {
                ConstraintWidget.DimensionBehaviour[] dimensionBehaviourArr = constraintWidget.C;
                ConstraintWidget.DimensionBehaviour dimensionBehaviour = dimensionBehaviourArr[0];
                ConstraintWidget.DimensionBehaviour dimensionBehaviour2 = dimensionBehaviourArr[1];
                if (dimensionBehaviour == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) {
                    constraintWidget.a(ConstraintWidget.DimensionBehaviour.FIXED);
                }
                if (dimensionBehaviour2 == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) {
                    constraintWidget.b(ConstraintWidget.DimensionBehaviour.FIXED);
                }
                constraintWidget.a(eVar);
                if (dimensionBehaviour == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) {
                    constraintWidget.a(dimensionBehaviour);
                }
                if (dimensionBehaviour2 == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) {
                    constraintWidget.b(dimensionBehaviour2);
                }
            } else {
                i.a(this, eVar, constraintWidget);
                constraintWidget.a(eVar);
            }
        }
        if (this.s0 > 0) {
            c.a(this, eVar, 0);
        }
        if (this.t0 > 0) {
            c.a(this, eVar, 1);
        }
        return true;
    }

    public void f(int i2, int i3) {
        l lVar;
        l lVar2;
        if (!(this.C[0] == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT || (lVar2 = this.c) == null)) {
            lVar2.a(i2);
        }
        if (this.C[1] != ConstraintWidget.DimensionBehaviour.WRAP_CONTENT && (lVar = this.d) != null) {
            lVar.a(i3);
        }
    }

    public boolean t(int i2) {
        return (this.C0 & i2) == i2;
    }

    public void u(int i2) {
        this.C0 = i2;
    }

    public void a(int i2) {
        super.a(i2);
        int size = this.k0.size();
        for (int i3 = 0; i3 < size; i3++) {
            this.k0.get(i3).a(i2);
        }
    }

    /* access modifiers changed from: package-private */
    public void a(ConstraintWidget constraintWidget, int i2) {
        if (i2 == 0) {
            d(constraintWidget);
        } else if (i2 == 1) {
            e(constraintWidget);
        }
    }

    private void d(ConstraintWidget constraintWidget) {
        int i2 = this.s0 + 1;
        d[] dVarArr = this.v0;
        if (i2 >= dVarArr.length) {
            this.v0 = (d[]) Arrays.copyOf(dVarArr, dVarArr.length * 2);
        }
        this.v0[this.s0] = new d(constraintWidget, 0, P());
        this.s0++;
    }
}
