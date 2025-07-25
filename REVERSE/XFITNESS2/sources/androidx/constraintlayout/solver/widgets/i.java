package androidx.constraintlayout.solver.widgets;

import androidx.constraintlayout.solver.e;
import androidx.constraintlayout.solver.widgets.ConstraintWidget;

/* compiled from: Optimizer */
public class i {
    static boolean[] a = new boolean[3];

    static void a(e eVar, e eVar2, ConstraintWidget constraintWidget) {
        if (eVar.C[0] != ConstraintWidget.DimensionBehaviour.WRAP_CONTENT && constraintWidget.C[0] == ConstraintWidget.DimensionBehaviour.MATCH_PARENT) {
            int i2 = constraintWidget.s.e;
            int s = eVar.s() - constraintWidget.u.e;
            ConstraintAnchor constraintAnchor = constraintWidget.s;
            constraintAnchor.f378i = eVar2.a((Object) constraintAnchor);
            ConstraintAnchor constraintAnchor2 = constraintWidget.u;
            constraintAnchor2.f378i = eVar2.a((Object) constraintAnchor2);
            eVar2.a(constraintWidget.s.f378i, i2);
            eVar2.a(constraintWidget.u.f378i, s);
            constraintWidget.a = 2;
            constraintWidget.a(i2, s);
        }
        if (eVar.C[1] != ConstraintWidget.DimensionBehaviour.WRAP_CONTENT && constraintWidget.C[1] == ConstraintWidget.DimensionBehaviour.MATCH_PARENT) {
            int i3 = constraintWidget.t.e;
            int i4 = eVar.i() - constraintWidget.v.e;
            ConstraintAnchor constraintAnchor3 = constraintWidget.t;
            constraintAnchor3.f378i = eVar2.a((Object) constraintAnchor3);
            ConstraintAnchor constraintAnchor4 = constraintWidget.v;
            constraintAnchor4.f378i = eVar2.a((Object) constraintAnchor4);
            eVar2.a(constraintWidget.t.f378i, i3);
            eVar2.a(constraintWidget.v.f378i, i4);
            if (constraintWidget.Q > 0 || constraintWidget.r() == 8) {
                ConstraintAnchor constraintAnchor5 = constraintWidget.w;
                constraintAnchor5.f378i = eVar2.a((Object) constraintAnchor5);
                eVar2.a(constraintWidget.w.f378i, constraintWidget.Q + i3);
            }
            constraintWidget.b = 2;
            constraintWidget.e(i3, i4);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:26:0x003b A[RETURN] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static boolean a(androidx.constraintlayout.solver.widgets.ConstraintWidget r5, int r6) {
        /*
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour[] r0 = r5.C
            r1 = r0[r6]
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour r2 = androidx.constraintlayout.solver.widgets.ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT
            r3 = 0
            if (r1 == r2) goto L_0x000a
            return r3
        L_0x000a:
            float r1 = r5.G
            r2 = 0
            r4 = 1
            int r1 = (r1 > r2 ? 1 : (r1 == r2 ? 0 : -1))
            if (r1 == 0) goto L_0x001d
            if (r6 != 0) goto L_0x0015
            goto L_0x0016
        L_0x0015:
            r4 = 0
        L_0x0016:
            r5 = r0[r4]
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour r6 = androidx.constraintlayout.solver.widgets.ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT
            if (r5 != r6) goto L_0x001c
        L_0x001c:
            return r3
        L_0x001d:
            if (r6 != 0) goto L_0x002d
            int r6 = r5.e
            if (r6 == 0) goto L_0x0024
            return r3
        L_0x0024:
            int r6 = r5.f381h
            if (r6 != 0) goto L_0x002c
            int r5 = r5.f382i
            if (r5 == 0) goto L_0x003b
        L_0x002c:
            return r3
        L_0x002d:
            int r6 = r5.f379f
            if (r6 == 0) goto L_0x0032
            return r3
        L_0x0032:
            int r6 = r5.k
            if (r6 != 0) goto L_0x003c
            int r5 = r5.l
            if (r5 == 0) goto L_0x003b
            goto L_0x003c
        L_0x003b:
            return r4
        L_0x003c:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.solver.widgets.i.a(androidx.constraintlayout.solver.widgets.ConstraintWidget, int):boolean");
    }

    static void a(int i2, ConstraintWidget constraintWidget) {
        ConstraintWidget constraintWidget2 = constraintWidget;
        constraintWidget.I();
        k d = constraintWidget2.s.d();
        k d2 = constraintWidget2.t.d();
        k d3 = constraintWidget2.u.d();
        k d4 = constraintWidget2.v.d();
        boolean z = (i2 & 8) == 8;
        boolean z2 = constraintWidget2.C[0] == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && a(constraintWidget2, 0);
        if (!(d.f396h == 4 || d3.f396h == 4)) {
            if (constraintWidget2.C[0] == ConstraintWidget.DimensionBehaviour.FIXED || (z2 && constraintWidget.r() == 8)) {
                if (constraintWidget2.s.d == null && constraintWidget2.u.d == null) {
                    d.b(1);
                    d3.b(1);
                    if (z) {
                        d3.a(d, 1, constraintWidget.m());
                    } else {
                        d3.a(d, constraintWidget.s());
                    }
                } else if (constraintWidget2.s.d != null && constraintWidget2.u.d == null) {
                    d.b(1);
                    d3.b(1);
                    if (z) {
                        d3.a(d, 1, constraintWidget.m());
                    } else {
                        d3.a(d, constraintWidget.s());
                    }
                } else if (constraintWidget2.s.d == null && constraintWidget2.u.d != null) {
                    d.b(1);
                    d3.b(1);
                    d.a(d3, -constraintWidget.s());
                    if (z) {
                        d.a(d3, -1, constraintWidget.m());
                    } else {
                        d.a(d3, -constraintWidget.s());
                    }
                } else if (!(constraintWidget2.s.d == null || constraintWidget2.u.d == null)) {
                    d.b(2);
                    d3.b(2);
                    if (z) {
                        constraintWidget.m().a(d);
                        constraintWidget.m().a(d3);
                        d.b(d3, -1, constraintWidget.m());
                        d3.b(d, 1, constraintWidget.m());
                    } else {
                        d.b(d3, (float) (-constraintWidget.s()));
                        d3.b(d, (float) constraintWidget.s());
                    }
                }
            } else if (z2) {
                int s = constraintWidget.s();
                d.b(1);
                d3.b(1);
                if (constraintWidget2.s.d == null && constraintWidget2.u.d == null) {
                    if (z) {
                        d3.a(d, 1, constraintWidget.m());
                    } else {
                        d3.a(d, s);
                    }
                } else if (constraintWidget2.s.d == null || constraintWidget2.u.d != null) {
                    if (constraintWidget2.s.d != null || constraintWidget2.u.d == null) {
                        if (!(constraintWidget2.s.d == null || constraintWidget2.u.d == null)) {
                            if (z) {
                                constraintWidget.m().a(d);
                                constraintWidget.m().a(d3);
                            }
                            if (constraintWidget2.G == 0.0f) {
                                d.b(3);
                                d3.b(3);
                                d.b(d3, 0.0f);
                                d3.b(d, 0.0f);
                            } else {
                                d.b(2);
                                d3.b(2);
                                d.b(d3, (float) (-s));
                                d3.b(d, (float) s);
                                constraintWidget2.o(s);
                            }
                        }
                    } else if (z) {
                        d.a(d3, -1, constraintWidget.m());
                    } else {
                        d.a(d3, -s);
                    }
                } else if (z) {
                    d3.a(d, 1, constraintWidget.m());
                } else {
                    d3.a(d, s);
                }
            }
        }
        boolean z3 = constraintWidget2.C[1] == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && a(constraintWidget2, 1);
        if (d2.f396h != 4 && d4.f396h != 4) {
            if (constraintWidget2.C[1] == ConstraintWidget.DimensionBehaviour.FIXED || (z3 && constraintWidget.r() == 8)) {
                if (constraintWidget2.t.d == null && constraintWidget2.v.d == null) {
                    d2.b(1);
                    d4.b(1);
                    if (z) {
                        d4.a(d2, 1, constraintWidget.l());
                    } else {
                        d4.a(d2, constraintWidget.i());
                    }
                    ConstraintAnchor constraintAnchor = constraintWidget2.w;
                    if (constraintAnchor.d != null) {
                        constraintAnchor.d().b(1);
                        d2.a(1, constraintWidget2.w.d(), -constraintWidget2.Q);
                    }
                } else if (constraintWidget2.t.d != null && constraintWidget2.v.d == null) {
                    d2.b(1);
                    d4.b(1);
                    if (z) {
                        d4.a(d2, 1, constraintWidget.l());
                    } else {
                        d4.a(d2, constraintWidget.i());
                    }
                    if (constraintWidget2.Q > 0) {
                        constraintWidget2.w.d().a(1, d2, constraintWidget2.Q);
                    }
                } else if (constraintWidget2.t.d == null && constraintWidget2.v.d != null) {
                    d2.b(1);
                    d4.b(1);
                    if (z) {
                        d2.a(d4, -1, constraintWidget.l());
                    } else {
                        d2.a(d4, -constraintWidget.i());
                    }
                    if (constraintWidget2.Q > 0) {
                        constraintWidget2.w.d().a(1, d2, constraintWidget2.Q);
                    }
                } else if (constraintWidget2.t.d != null && constraintWidget2.v.d != null) {
                    d2.b(2);
                    d4.b(2);
                    if (z) {
                        d2.b(d4, -1, constraintWidget.l());
                        d4.b(d2, 1, constraintWidget.l());
                        constraintWidget.l().a(d2);
                        constraintWidget.m().a(d4);
                    } else {
                        d2.b(d4, (float) (-constraintWidget.i()));
                        d4.b(d2, (float) constraintWidget.i());
                    }
                    if (constraintWidget2.Q > 0) {
                        constraintWidget2.w.d().a(1, d2, constraintWidget2.Q);
                    }
                }
            } else if (z3) {
                int i3 = constraintWidget.i();
                d2.b(1);
                d4.b(1);
                if (constraintWidget2.t.d == null && constraintWidget2.v.d == null) {
                    if (z) {
                        d4.a(d2, 1, constraintWidget.l());
                    } else {
                        d4.a(d2, i3);
                    }
                } else if (constraintWidget2.t.d == null || constraintWidget2.v.d != null) {
                    if (constraintWidget2.t.d != null || constraintWidget2.v.d == null) {
                        if (constraintWidget2.t.d != null && constraintWidget2.v.d != null) {
                            if (z) {
                                constraintWidget.l().a(d2);
                                constraintWidget.m().a(d4);
                            }
                            if (constraintWidget2.G == 0.0f) {
                                d2.b(3);
                                d4.b(3);
                                d2.b(d4, 0.0f);
                                d4.b(d2, 0.0f);
                                return;
                            }
                            d2.b(2);
                            d4.b(2);
                            d2.b(d4, (float) (-i3));
                            d4.b(d2, (float) i3);
                            constraintWidget2.g(i3);
                            if (constraintWidget2.Q > 0) {
                                constraintWidget2.w.d().a(1, d2, constraintWidget2.Q);
                            }
                        }
                    } else if (z) {
                        d2.a(d4, -1, constraintWidget.l());
                    } else {
                        d2.a(d4, -i3);
                    }
                } else if (z) {
                    d4.a(d2, 1, constraintWidget.l());
                } else {
                    d4.a(d2, i3);
                }
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0032, code lost:
        if (r7.e0 == 2) goto L_0x0034;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0036, code lost:
        r2 = false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x0048, code lost:
        if (r7.f0 == 2) goto L_0x0034;
     */
    /* JADX WARNING: Removed duplicated region for block: B:124:0x01d6  */
    /* JADX WARNING: Removed duplicated region for block: B:72:0x0104  */
    /* JADX WARNING: Removed duplicated region for block: B:73:0x0107  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static boolean a(androidx.constraintlayout.solver.widgets.e r24, androidx.constraintlayout.solver.e r25, int r26, int r27, androidx.constraintlayout.solver.widgets.d r28) {
        /*
            r0 = r25
            r1 = r26
            r2 = r28
            androidx.constraintlayout.solver.widgets.ConstraintWidget r3 = r2.a
            androidx.constraintlayout.solver.widgets.ConstraintWidget r4 = r2.c
            androidx.constraintlayout.solver.widgets.ConstraintWidget r5 = r2.b
            androidx.constraintlayout.solver.widgets.ConstraintWidget r6 = r2.d
            androidx.constraintlayout.solver.widgets.ConstraintWidget r7 = r2.e
            float r8 = r2.k
            androidx.constraintlayout.solver.widgets.ConstraintWidget r9 = r2.f384f
            androidx.constraintlayout.solver.widgets.ConstraintWidget r2 = r2.f385g
            r9 = r24
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour[] r2 = r9.C
            r2 = r2[r1]
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour r9 = androidx.constraintlayout.solver.widgets.ConstraintWidget.DimensionBehaviour.WRAP_CONTENT
            r2 = 2
            r10 = 1
            if (r1 != 0) goto L_0x0038
            int r11 = r7.e0
            if (r11 != 0) goto L_0x0028
            r11 = 1
            goto L_0x0029
        L_0x0028:
            r11 = 0
        L_0x0029:
            int r12 = r7.e0
            if (r12 != r10) goto L_0x002f
            r12 = 1
            goto L_0x0030
        L_0x002f:
            r12 = 0
        L_0x0030:
            int r7 = r7.e0
            if (r7 != r2) goto L_0x0036
        L_0x0034:
            r2 = 1
            goto L_0x004b
        L_0x0036:
            r2 = 0
            goto L_0x004b
        L_0x0038:
            int r11 = r7.f0
            if (r11 != 0) goto L_0x003e
            r11 = 1
            goto L_0x003f
        L_0x003e:
            r11 = 0
        L_0x003f:
            int r12 = r7.f0
            if (r12 != r10) goto L_0x0045
            r12 = 1
            goto L_0x0046
        L_0x0045:
            r12 = 0
        L_0x0046:
            int r7 = r7.f0
            if (r7 != r2) goto L_0x0036
            goto L_0x0034
        L_0x004b:
            r14 = r3
            r10 = 0
            r13 = 0
            r15 = 0
            r16 = 0
            r17 = 0
        L_0x0053:
            r7 = 8
            if (r13 != 0) goto L_0x010a
            int r9 = r14.r()
            if (r9 == r7) goto L_0x00a1
            int r15 = r15 + 1
            if (r1 != 0) goto L_0x0066
            int r9 = r14.s()
            goto L_0x006a
        L_0x0066:
            int r9 = r14.i()
        L_0x006a:
            float r9 = (float) r9
            float r16 = r16 + r9
            if (r14 == r5) goto L_0x007a
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r9 = r14.A
            r9 = r9[r27]
            int r9 = r9.b()
            float r9 = (float) r9
            float r16 = r16 + r9
        L_0x007a:
            if (r14 == r6) goto L_0x0089
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r9 = r14.A
            int r19 = r27 + 1
            r9 = r9[r19]
            int r9 = r9.b()
            float r9 = (float) r9
            float r16 = r16 + r9
        L_0x0089:
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r9 = r14.A
            r9 = r9[r27]
            int r9 = r9.b()
            float r9 = (float) r9
            float r17 = r17 + r9
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r9 = r14.A
            int r19 = r27 + 1
            r9 = r9[r19]
            int r9 = r9.b()
            float r9 = (float) r9
            float r17 = r17 + r9
        L_0x00a1:
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r9 = r14.A
            r9 = r9[r27]
            int r9 = r14.r()
            if (r9 == r7) goto L_0x00df
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour[] r7 = r14.C
            r7 = r7[r1]
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour r9 = androidx.constraintlayout.solver.widgets.ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT
            if (r7 != r9) goto L_0x00df
            int r10 = r10 + 1
            if (r1 != 0) goto L_0x00c7
            int r7 = r14.e
            if (r7 == 0) goto L_0x00bd
            r7 = 0
            return r7
        L_0x00bd:
            r7 = 0
            int r9 = r14.f381h
            if (r9 != 0) goto L_0x00c6
            int r9 = r14.f382i
            if (r9 == 0) goto L_0x00d6
        L_0x00c6:
            return r7
        L_0x00c7:
            r7 = 0
            int r9 = r14.f379f
            if (r9 == 0) goto L_0x00cd
            return r7
        L_0x00cd:
            int r9 = r14.k
            if (r9 != 0) goto L_0x00de
            int r9 = r14.l
            if (r9 == 0) goto L_0x00d6
            goto L_0x00de
        L_0x00d6:
            float r9 = r14.G
            r18 = 0
            int r9 = (r9 > r18 ? 1 : (r9 == r18 ? 0 : -1))
            if (r9 == 0) goto L_0x00df
        L_0x00de:
            return r7
        L_0x00df:
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r7 = r14.A
            int r9 = r27 + 1
            r7 = r7[r9]
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r7 = r7.d
            if (r7 == 0) goto L_0x0101
            androidx.constraintlayout.solver.widgets.ConstraintWidget r7 = r7.b
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r9 = r7.A
            r20 = r7
            r7 = r9[r27]
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r7 = r7.d
            if (r7 == 0) goto L_0x0101
            r7 = r9[r27]
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r7 = r7.d
            androidx.constraintlayout.solver.widgets.ConstraintWidget r7 = r7.b
            if (r7 == r14) goto L_0x00fe
            goto L_0x0101
        L_0x00fe:
            r9 = r20
            goto L_0x0102
        L_0x0101:
            r9 = 0
        L_0x0102:
            if (r9 == 0) goto L_0x0107
            r14 = r9
            goto L_0x0053
        L_0x0107:
            r13 = 1
            goto L_0x0053
        L_0x010a:
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r9 = r3.A
            r9 = r9[r27]
            androidx.constraintlayout.solver.widgets.k r9 = r9.d()
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r13 = r4.A
            int r19 = r27 + 1
            r13 = r13[r19]
            androidx.constraintlayout.solver.widgets.k r13 = r13.d()
            androidx.constraintlayout.solver.widgets.k r7 = r9.d
            if (r7 == 0) goto L_0x0385
            r21 = r3
            androidx.constraintlayout.solver.widgets.k r3 = r13.d
            if (r3 != 0) goto L_0x0128
            goto L_0x0385
        L_0x0128:
            int r7 = r7.b
            r0 = 1
            if (r7 != r0) goto L_0x0383
            int r3 = r3.b
            if (r3 == r0) goto L_0x0133
            goto L_0x0383
        L_0x0133:
            if (r10 <= 0) goto L_0x0139
            if (r10 == r15) goto L_0x0139
            r0 = 0
            return r0
        L_0x0139:
            if (r2 != 0) goto L_0x0142
            if (r11 != 0) goto L_0x0142
            if (r12 == 0) goto L_0x0140
            goto L_0x0142
        L_0x0140:
            r0 = 0
            goto L_0x015b
        L_0x0142:
            if (r5 == 0) goto L_0x014e
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r0 = r5.A
            r0 = r0[r27]
            int r0 = r0.b()
            float r0 = (float) r0
            goto L_0x014f
        L_0x014e:
            r0 = 0
        L_0x014f:
            if (r6 == 0) goto L_0x015b
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r3 = r6.A
            r3 = r3[r19]
            int r3 = r3.b()
            float r3 = (float) r3
            float r0 = r0 + r3
        L_0x015b:
            androidx.constraintlayout.solver.widgets.k r3 = r9.d
            float r3 = r3.f395g
            androidx.constraintlayout.solver.widgets.k r6 = r13.d
            float r6 = r6.f395g
            int r7 = (r3 > r6 ? 1 : (r3 == r6 ? 0 : -1))
            if (r7 >= 0) goto L_0x0169
            float r6 = r6 - r3
            goto L_0x016b
        L_0x0169:
            float r6 = r3 - r6
        L_0x016b:
            float r6 = r6 - r16
            r22 = 1
            if (r10 <= 0) goto L_0x0225
            if (r10 != r15) goto L_0x0225
            androidx.constraintlayout.solver.widgets.ConstraintWidget r0 = r14.k()
            if (r0 == 0) goto L_0x0187
            androidx.constraintlayout.solver.widgets.ConstraintWidget r0 = r14.k()
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour[] r0 = r0.C
            r0 = r0[r1]
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour r2 = androidx.constraintlayout.solver.widgets.ConstraintWidget.DimensionBehaviour.WRAP_CONTENT
            if (r0 != r2) goto L_0x0187
            r0 = 0
            return r0
        L_0x0187:
            float r6 = r6 + r16
            float r6 = r6 - r17
            r0 = r3
            r3 = r21
        L_0x018e:
            if (r3 == 0) goto L_0x0223
            androidx.constraintlayout.solver.f r2 = androidx.constraintlayout.solver.e.q
            if (r2 == 0) goto L_0x01a6
            long r11 = r2.z
            long r11 = r11 - r22
            r2.z = r11
            long r11 = r2.r
            long r11 = r11 + r22
            r2.r = r11
            long r11 = r2.x
            long r11 = r11 + r22
            r2.x = r11
        L_0x01a6:
            androidx.constraintlayout.solver.widgets.ConstraintWidget[] r2 = r3.i0
            r2 = r2[r1]
            if (r2 != 0) goto L_0x01b2
            if (r3 != r4) goto L_0x01af
            goto L_0x01b2
        L_0x01af:
            r7 = r25
            goto L_0x0220
        L_0x01b2:
            float r5 = (float) r10
            float r5 = r6 / r5
            r7 = 0
            int r11 = (r8 > r7 ? 1 : (r8 == r7 ? 0 : -1))
            if (r11 <= 0) goto L_0x01cc
            float[] r5 = r3.g0
            r7 = r5[r1]
            r11 = -1082130432(0xffffffffbf800000, float:-1.0)
            int r7 = (r7 > r11 ? 1 : (r7 == r11 ? 0 : -1))
            if (r7 != 0) goto L_0x01c7
            r18 = 0
            goto L_0x01ce
        L_0x01c7:
            r5 = r5[r1]
            float r5 = r5 * r6
            float r5 = r5 / r8
        L_0x01cc:
            r18 = r5
        L_0x01ce:
            int r5 = r3.r()
            r7 = 8
            if (r5 != r7) goto L_0x01d8
            r18 = 0
        L_0x01d8:
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r5 = r3.A
            r5 = r5[r27]
            int r5 = r5.b()
            float r5 = (float) r5
            float r0 = r0 + r5
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r5 = r3.A
            r5 = r5[r27]
            androidx.constraintlayout.solver.widgets.k r5 = r5.d()
            androidx.constraintlayout.solver.widgets.k r7 = r9.f394f
            r5.a((androidx.constraintlayout.solver.widgets.k) r7, (float) r0)
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r5 = r3.A
            r5 = r5[r19]
            androidx.constraintlayout.solver.widgets.k r5 = r5.d()
            androidx.constraintlayout.solver.widgets.k r7 = r9.f394f
            float r0 = r0 + r18
            r5.a((androidx.constraintlayout.solver.widgets.k) r7, (float) r0)
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r5 = r3.A
            r5 = r5[r27]
            androidx.constraintlayout.solver.widgets.k r5 = r5.d()
            r7 = r25
            r5.a((androidx.constraintlayout.solver.e) r7)
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r5 = r3.A
            r5 = r5[r19]
            androidx.constraintlayout.solver.widgets.k r5 = r5.d()
            r5.a((androidx.constraintlayout.solver.e) r7)
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r3 = r3.A
            r3 = r3[r19]
            int r3 = r3.b()
            float r3 = (float) r3
            float r0 = r0 + r3
        L_0x0220:
            r3 = r2
            goto L_0x018e
        L_0x0223:
            r0 = 1
            return r0
        L_0x0225:
            r7 = r25
            r8 = 0
            int r8 = (r6 > r8 ? 1 : (r6 == r8 ? 0 : -1))
            if (r8 >= 0) goto L_0x022f
            r2 = 1
            r11 = 0
            r12 = 0
        L_0x022f:
            if (r2 == 0) goto L_0x02af
            float r6 = r6 - r0
            r2 = r21
            float r0 = r2.b((int) r1)
            float r6 = r6 * r0
            float r3 = r3 + r6
            r0 = r3
        L_0x023c:
            r3 = r2
            if (r3 == 0) goto L_0x02b6
            androidx.constraintlayout.solver.f r2 = androidx.constraintlayout.solver.e.q
            if (r2 == 0) goto L_0x0255
            long r5 = r2.z
            long r5 = r5 - r22
            r2.z = r5
            long r5 = r2.r
            long r5 = r5 + r22
            r2.r = r5
            long r5 = r2.x
            long r5 = r5 + r22
            r2.x = r5
        L_0x0255:
            androidx.constraintlayout.solver.widgets.ConstraintWidget[] r2 = r3.i0
            r2 = r2[r1]
            if (r2 != 0) goto L_0x025d
            if (r3 != r4) goto L_0x023c
        L_0x025d:
            if (r1 != 0) goto L_0x0264
            int r5 = r3.s()
            goto L_0x0268
        L_0x0264:
            int r5 = r3.i()
        L_0x0268:
            float r5 = (float) r5
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r6 = r3.A
            r6 = r6[r27]
            int r6 = r6.b()
            float r6 = (float) r6
            float r0 = r0 + r6
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r6 = r3.A
            r6 = r6[r27]
            androidx.constraintlayout.solver.widgets.k r6 = r6.d()
            androidx.constraintlayout.solver.widgets.k r8 = r9.f394f
            r6.a((androidx.constraintlayout.solver.widgets.k) r8, (float) r0)
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r6 = r3.A
            r6 = r6[r19]
            androidx.constraintlayout.solver.widgets.k r6 = r6.d()
            androidx.constraintlayout.solver.widgets.k r8 = r9.f394f
            float r0 = r0 + r5
            r6.a((androidx.constraintlayout.solver.widgets.k) r8, (float) r0)
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r5 = r3.A
            r5 = r5[r27]
            androidx.constraintlayout.solver.widgets.k r5 = r5.d()
            r5.a((androidx.constraintlayout.solver.e) r7)
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r5 = r3.A
            r5 = r5[r19]
            androidx.constraintlayout.solver.widgets.k r5 = r5.d()
            r5.a((androidx.constraintlayout.solver.e) r7)
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r3 = r3.A
            r3 = r3[r19]
            int r3 = r3.b()
            float r3 = (float) r3
            float r0 = r0 + r3
            goto L_0x023c
        L_0x02af:
            r2 = r21
            if (r11 != 0) goto L_0x02b9
            if (r12 == 0) goto L_0x02b6
            goto L_0x02b9
        L_0x02b6:
            r0 = 1
            goto L_0x0382
        L_0x02b9:
            if (r11 == 0) goto L_0x02bd
        L_0x02bb:
            float r6 = r6 - r0
            goto L_0x02c0
        L_0x02bd:
            if (r12 == 0) goto L_0x02c0
            goto L_0x02bb
        L_0x02c0:
            int r0 = r15 + 1
            float r0 = (float) r0
            float r0 = r6 / r0
            if (r12 == 0) goto L_0x02d2
            r8 = 1
            if (r15 <= r8) goto L_0x02ce
            int r0 = r15 + -1
            float r0 = (float) r0
            goto L_0x02d0
        L_0x02ce:
            r0 = 1073741824(0x40000000, float:2.0)
        L_0x02d0:
            float r0 = r6 / r0
        L_0x02d2:
            int r6 = r2.r()
            r8 = 8
            if (r6 == r8) goto L_0x02dd
            float r6 = r3 + r0
            goto L_0x02de
        L_0x02dd:
            r6 = r3
        L_0x02de:
            if (r12 == 0) goto L_0x02ed
            r8 = 1
            if (r15 <= r8) goto L_0x02ed
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r6 = r5.A
            r6 = r6[r27]
            int r6 = r6.b()
            float r6 = (float) r6
            float r6 = r6 + r3
        L_0x02ed:
            if (r11 == 0) goto L_0x02fb
            if (r5 == 0) goto L_0x02fb
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r3 = r5.A
            r3 = r3[r27]
            int r3 = r3.b()
            float r3 = (float) r3
            float r6 = r6 + r3
        L_0x02fb:
            r3 = r2
            if (r3 == 0) goto L_0x02b6
            androidx.constraintlayout.solver.f r2 = androidx.constraintlayout.solver.e.q
            if (r2 == 0) goto L_0x0314
            long r10 = r2.z
            long r10 = r10 - r22
            r2.z = r10
            long r10 = r2.r
            long r10 = r10 + r22
            r2.r = r10
            long r10 = r2.x
            long r10 = r10 + r22
            r2.x = r10
        L_0x0314:
            androidx.constraintlayout.solver.widgets.ConstraintWidget[] r2 = r3.i0
            r2 = r2[r1]
            if (r2 != 0) goto L_0x0320
            if (r3 != r4) goto L_0x031d
            goto L_0x0320
        L_0x031d:
            r8 = 8
            goto L_0x02fb
        L_0x0320:
            if (r1 != 0) goto L_0x0327
            int r8 = r3.s()
            goto L_0x032b
        L_0x0327:
            int r8 = r3.i()
        L_0x032b:
            float r8 = (float) r8
            if (r3 == r5) goto L_0x0338
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r10 = r3.A
            r10 = r10[r27]
            int r10 = r10.b()
            float r10 = (float) r10
            float r6 = r6 + r10
        L_0x0338:
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r10 = r3.A
            r10 = r10[r27]
            androidx.constraintlayout.solver.widgets.k r10 = r10.d()
            androidx.constraintlayout.solver.widgets.k r11 = r9.f394f
            r10.a((androidx.constraintlayout.solver.widgets.k) r11, (float) r6)
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r10 = r3.A
            r10 = r10[r19]
            androidx.constraintlayout.solver.widgets.k r10 = r10.d()
            androidx.constraintlayout.solver.widgets.k r11 = r9.f394f
            float r12 = r6 + r8
            r10.a((androidx.constraintlayout.solver.widgets.k) r11, (float) r12)
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r10 = r3.A
            r10 = r10[r27]
            androidx.constraintlayout.solver.widgets.k r10 = r10.d()
            r10.a((androidx.constraintlayout.solver.e) r7)
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r10 = r3.A
            r10 = r10[r19]
            androidx.constraintlayout.solver.widgets.k r10 = r10.d()
            r10.a((androidx.constraintlayout.solver.e) r7)
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r3 = r3.A
            r3 = r3[r19]
            int r3 = r3.b()
            float r3 = (float) r3
            float r8 = r8 + r3
            float r6 = r6 + r8
            if (r2 == 0) goto L_0x031d
            int r3 = r2.r()
            r8 = 8
            if (r3 == r8) goto L_0x02fb
            float r6 = r6 + r0
            goto L_0x02fb
        L_0x0382:
            return r0
        L_0x0383:
            r0 = 0
            return r0
        L_0x0385:
            r0 = 0
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.solver.widgets.i.a(androidx.constraintlayout.solver.widgets.e, androidx.constraintlayout.solver.e, int, int, androidx.constraintlayout.solver.widgets.d):boolean");
    }

    static void a(ConstraintWidget constraintWidget, int i2, int i3) {
        int i4 = i2 * 2;
        int i5 = i4 + 1;
        constraintWidget.A[i4].d().f394f = constraintWidget.k().s.d();
        constraintWidget.A[i4].d().f395g = (float) i3;
        constraintWidget.A[i4].d().b = 1;
        constraintWidget.A[i5].d().f394f = constraintWidget.A[i4].d();
        constraintWidget.A[i5].d().f395g = (float) constraintWidget.d(i2);
        constraintWidget.A[i5].d().b = 1;
    }
}
