package androidx.constraintlayout.solver;

import androidx.constraintlayout.solver.SolverVariable;
import com.jeremyliao.liveeventbus.BuildConfig;
import java.util.Arrays;

/* compiled from: ArrayLinkedVariables */
public class a {
    int a = 0;
    private final b b;
    private final c c;
    private int d = 8;
    private SolverVariable e = null;

    /* renamed from: f  reason: collision with root package name */
    private int[] f360f = new int[8];

    /* renamed from: g  reason: collision with root package name */
    private int[] f361g = new int[8];

    /* renamed from: h  reason: collision with root package name */
    private float[] f362h = new float[8];

    /* renamed from: i  reason: collision with root package name */
    private int f363i = -1;

    /* renamed from: j  reason: collision with root package name */
    private int f364j = -1;
    private boolean k = false;

    a(b bVar, c cVar) {
        this.b = bVar;
        this.c = cVar;
    }

    public final void a(SolverVariable solverVariable, float f2) {
        if (f2 == 0.0f) {
            a(solverVariable, true);
            return;
        }
        int i2 = this.f363i;
        if (i2 == -1) {
            this.f363i = 0;
            this.f362h[0] = f2;
            this.f360f[0] = solverVariable.b;
            this.f361g[0] = -1;
            solverVariable.f359j++;
            solverVariable.a(this.b);
            this.a++;
            if (!this.k) {
                int i3 = this.f364j + 1;
                this.f364j = i3;
                int[] iArr = this.f360f;
                if (i3 >= iArr.length) {
                    this.k = true;
                    this.f364j = iArr.length - 1;
                    return;
                }
                return;
            }
            return;
        }
        int i4 = 0;
        int i5 = -1;
        while (i2 != -1 && i4 < this.a) {
            int[] iArr2 = this.f360f;
            int i6 = iArr2[i2];
            int i7 = solverVariable.b;
            if (i6 == i7) {
                this.f362h[i2] = f2;
                return;
            }
            if (iArr2[i2] < i7) {
                i5 = i2;
            }
            i2 = this.f361g[i2];
            i4++;
        }
        int i8 = this.f364j;
        int i9 = i8 + 1;
        if (this.k) {
            int[] iArr3 = this.f360f;
            if (iArr3[i8] != -1) {
                i8 = iArr3.length;
            }
        } else {
            i8 = i9;
        }
        int[] iArr4 = this.f360f;
        if (i8 >= iArr4.length && this.a < iArr4.length) {
            int i10 = 0;
            while (true) {
                int[] iArr5 = this.f360f;
                if (i10 >= iArr5.length) {
                    break;
                } else if (iArr5[i10] == -1) {
                    i8 = i10;
                    break;
                } else {
                    i10++;
                }
            }
        }
        int[] iArr6 = this.f360f;
        if (i8 >= iArr6.length) {
            i8 = iArr6.length;
            int i11 = this.d * 2;
            this.d = i11;
            this.k = false;
            this.f364j = i8 - 1;
            this.f362h = Arrays.copyOf(this.f362h, i11);
            this.f360f = Arrays.copyOf(this.f360f, this.d);
            this.f361g = Arrays.copyOf(this.f361g, this.d);
        }
        this.f360f[i8] = solverVariable.b;
        this.f362h[i8] = f2;
        if (i5 != -1) {
            int[] iArr7 = this.f361g;
            iArr7[i8] = iArr7[i5];
            iArr7[i5] = i8;
        } else {
            this.f361g[i8] = this.f363i;
            this.f363i = i8;
        }
        solverVariable.f359j++;
        solverVariable.a(this.b);
        this.a++;
        if (!this.k) {
            this.f364j++;
        }
        if (this.a >= this.f360f.length) {
            this.k = true;
        }
        int i12 = this.f364j;
        int[] iArr8 = this.f360f;
        if (i12 >= iArr8.length) {
            this.k = true;
            this.f364j = iArr8.length - 1;
        }
    }

    /* access modifiers changed from: package-private */
    public void b() {
        int i2 = this.f363i;
        int i3 = 0;
        while (i2 != -1 && i3 < this.a) {
            float[] fArr = this.f362h;
            fArr[i2] = fArr[i2] * -1.0f;
            i2 = this.f361g[i2];
            i3++;
        }
    }

    public String toString() {
        int i2 = this.f363i;
        String str = BuildConfig.FLAVOR;
        int i3 = 0;
        while (i2 != -1 && i3 < this.a) {
            str = ((str + " -> ") + this.f362h[i2] + " : ") + this.c.c[this.f360f[i2]];
            i2 = this.f361g[i2];
            i3++;
        }
        return str;
    }

    /* access modifiers changed from: package-private */
    public final float b(int i2) {
        int i3 = this.f363i;
        int i4 = 0;
        while (i3 != -1 && i4 < this.a) {
            if (i4 == i2) {
                return this.f362h[i3];
            }
            i3 = this.f361g[i3];
            i4++;
        }
        return 0.0f;
    }

    public final float b(SolverVariable solverVariable) {
        int i2 = this.f363i;
        int i3 = 0;
        while (i2 != -1 && i3 < this.a) {
            if (this.f360f[i2] == solverVariable.b) {
                return this.f362h[i2];
            }
            i2 = this.f361g[i2];
            i3++;
        }
        return 0.0f;
    }

    /* access modifiers changed from: package-private */
    public final void a(SolverVariable solverVariable, float f2, boolean z) {
        if (f2 != 0.0f) {
            int i2 = this.f363i;
            if (i2 == -1) {
                this.f363i = 0;
                this.f362h[0] = f2;
                this.f360f[0] = solverVariable.b;
                this.f361g[0] = -1;
                solverVariable.f359j++;
                solverVariable.a(this.b);
                this.a++;
                if (!this.k) {
                    int i3 = this.f364j + 1;
                    this.f364j = i3;
                    int[] iArr = this.f360f;
                    if (i3 >= iArr.length) {
                        this.k = true;
                        this.f364j = iArr.length - 1;
                        return;
                    }
                    return;
                }
                return;
            }
            int i4 = 0;
            int i5 = -1;
            while (i2 != -1 && i4 < this.a) {
                int[] iArr2 = this.f360f;
                int i6 = iArr2[i2];
                int i7 = solverVariable.b;
                if (i6 == i7) {
                    float[] fArr = this.f362h;
                    fArr[i2] = fArr[i2] + f2;
                    if (fArr[i2] == 0.0f) {
                        if (i2 == this.f363i) {
                            this.f363i = this.f361g[i2];
                        } else {
                            int[] iArr3 = this.f361g;
                            iArr3[i5] = iArr3[i2];
                        }
                        if (z) {
                            solverVariable.b(this.b);
                        }
                        if (this.k) {
                            this.f364j = i2;
                        }
                        solverVariable.f359j--;
                        this.a--;
                        return;
                    }
                    return;
                }
                if (iArr2[i2] < i7) {
                    i5 = i2;
                }
                i2 = this.f361g[i2];
                i4++;
            }
            int i8 = this.f364j;
            int i9 = i8 + 1;
            if (this.k) {
                int[] iArr4 = this.f360f;
                if (iArr4[i8] != -1) {
                    i8 = iArr4.length;
                }
            } else {
                i8 = i9;
            }
            int[] iArr5 = this.f360f;
            if (i8 >= iArr5.length && this.a < iArr5.length) {
                int i10 = 0;
                while (true) {
                    int[] iArr6 = this.f360f;
                    if (i10 >= iArr6.length) {
                        break;
                    } else if (iArr6[i10] == -1) {
                        i8 = i10;
                        break;
                    } else {
                        i10++;
                    }
                }
            }
            int[] iArr7 = this.f360f;
            if (i8 >= iArr7.length) {
                i8 = iArr7.length;
                int i11 = this.d * 2;
                this.d = i11;
                this.k = false;
                this.f364j = i8 - 1;
                this.f362h = Arrays.copyOf(this.f362h, i11);
                this.f360f = Arrays.copyOf(this.f360f, this.d);
                this.f361g = Arrays.copyOf(this.f361g, this.d);
            }
            this.f360f[i8] = solverVariable.b;
            this.f362h[i8] = f2;
            if (i5 != -1) {
                int[] iArr8 = this.f361g;
                iArr8[i8] = iArr8[i5];
                iArr8[i5] = i8;
            } else {
                this.f361g[i8] = this.f363i;
                this.f363i = i8;
            }
            solverVariable.f359j++;
            solverVariable.a(this.b);
            this.a++;
            if (!this.k) {
                this.f364j++;
            }
            int i12 = this.f364j;
            int[] iArr9 = this.f360f;
            if (i12 >= iArr9.length) {
                this.k = true;
                this.f364j = iArr9.length - 1;
            }
        }
    }

    public final float a(SolverVariable solverVariable, boolean z) {
        if (this.e == solverVariable) {
            this.e = null;
        }
        int i2 = this.f363i;
        if (i2 == -1) {
            return 0.0f;
        }
        int i3 = 0;
        int i4 = -1;
        while (i2 != -1 && i3 < this.a) {
            if (this.f360f[i2] == solverVariable.b) {
                if (i2 == this.f363i) {
                    this.f363i = this.f361g[i2];
                } else {
                    int[] iArr = this.f361g;
                    iArr[i4] = iArr[i2];
                }
                if (z) {
                    solverVariable.b(this.b);
                }
                solverVariable.f359j--;
                this.a--;
                this.f360f[i2] = -1;
                if (this.k) {
                    this.f364j = i2;
                }
                return this.f362h[i2];
            }
            i3++;
            i4 = i2;
            i2 = this.f361g[i2];
        }
        return 0.0f;
    }

    public final void a() {
        int i2 = this.f363i;
        int i3 = 0;
        while (i2 != -1 && i3 < this.a) {
            SolverVariable solverVariable = this.c.c[this.f360f[i2]];
            if (solverVariable != null) {
                solverVariable.b(this.b);
            }
            i2 = this.f361g[i2];
            i3++;
        }
        this.f363i = -1;
        this.f364j = -1;
        this.k = false;
        this.a = 0;
    }

    /* access modifiers changed from: package-private */
    public final boolean a(SolverVariable solverVariable) {
        int i2 = this.f363i;
        if (i2 == -1) {
            return false;
        }
        int i3 = 0;
        while (i2 != -1 && i3 < this.a) {
            if (this.f360f[i2] == solverVariable.b) {
                return true;
            }
            i2 = this.f361g[i2];
            i3++;
        }
        return false;
    }

    /* access modifiers changed from: package-private */
    public void a(float f2) {
        int i2 = this.f363i;
        int i3 = 0;
        while (i2 != -1 && i3 < this.a) {
            float[] fArr = this.f362h;
            fArr[i2] = fArr[i2] / f2;
            i2 = this.f361g[i2];
            i3++;
        }
    }

    private boolean a(SolverVariable solverVariable, e eVar) {
        return solverVariable.f359j <= 1;
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Removed duplicated region for block: B:16:0x0047  */
    /* JADX WARNING: Removed duplicated region for block: B:48:0x0090 A[SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public androidx.constraintlayout.solver.SolverVariable a(androidx.constraintlayout.solver.e r15) {
        /*
            r14 = this;
            int r0 = r14.f363i
            r1 = 0
            r2 = 0
            r3 = 0
            r2 = r1
            r4 = 0
            r5 = 0
            r6 = 0
            r7 = 0
            r8 = 0
        L_0x000b:
            r9 = -1
            if (r0 == r9) goto L_0x0098
            int r9 = r14.a
            if (r4 >= r9) goto L_0x0098
            float[] r9 = r14.f362h
            r10 = r9[r0]
            r11 = 981668463(0x3a83126f, float:0.001)
            androidx.constraintlayout.solver.c r12 = r14.c
            androidx.constraintlayout.solver.SolverVariable[] r12 = r12.c
            int[] r13 = r14.f360f
            r13 = r13[r0]
            r12 = r12[r13]
            int r13 = (r10 > r3 ? 1 : (r10 == r3 ? 0 : -1))
            if (r13 >= 0) goto L_0x0036
            r11 = -1165815185(0xffffffffba83126f, float:-0.001)
            int r11 = (r10 > r11 ? 1 : (r10 == r11 ? 0 : -1))
            if (r11 <= 0) goto L_0x0042
            r9[r0] = r3
            androidx.constraintlayout.solver.b r9 = r14.b
            r12.b(r9)
            goto L_0x0041
        L_0x0036:
            int r11 = (r10 > r11 ? 1 : (r10 == r11 ? 0 : -1))
            if (r11 >= 0) goto L_0x0042
            r9[r0] = r3
            androidx.constraintlayout.solver.b r9 = r14.b
            r12.b(r9)
        L_0x0041:
            r10 = 0
        L_0x0042:
            r9 = 1
            int r11 = (r10 > r3 ? 1 : (r10 == r3 ? 0 : -1))
            if (r11 == 0) goto L_0x0090
            androidx.constraintlayout.solver.SolverVariable$Type r11 = r12.f356g
            androidx.constraintlayout.solver.SolverVariable$Type r13 = androidx.constraintlayout.solver.SolverVariable.Type.UNRESTRICTED
            if (r11 != r13) goto L_0x006c
            if (r2 != 0) goto L_0x0057
            boolean r2 = r14.a((androidx.constraintlayout.solver.SolverVariable) r12, (androidx.constraintlayout.solver.e) r15)
        L_0x0053:
            r5 = r2
            r7 = r10
            r2 = r12
            goto L_0x0090
        L_0x0057:
            int r11 = (r7 > r10 ? 1 : (r7 == r10 ? 0 : -1))
            if (r11 <= 0) goto L_0x0060
            boolean r2 = r14.a((androidx.constraintlayout.solver.SolverVariable) r12, (androidx.constraintlayout.solver.e) r15)
            goto L_0x0053
        L_0x0060:
            if (r5 != 0) goto L_0x0090
            boolean r11 = r14.a((androidx.constraintlayout.solver.SolverVariable) r12, (androidx.constraintlayout.solver.e) r15)
            if (r11 == 0) goto L_0x0090
            r7 = r10
            r2 = r12
            r5 = 1
            goto L_0x0090
        L_0x006c:
            if (r2 != 0) goto L_0x0090
            int r11 = (r10 > r3 ? 1 : (r10 == r3 ? 0 : -1))
            if (r11 >= 0) goto L_0x0090
            if (r1 != 0) goto L_0x007c
            boolean r1 = r14.a((androidx.constraintlayout.solver.SolverVariable) r12, (androidx.constraintlayout.solver.e) r15)
        L_0x0078:
            r6 = r1
            r8 = r10
            r1 = r12
            goto L_0x0090
        L_0x007c:
            int r11 = (r8 > r10 ? 1 : (r8 == r10 ? 0 : -1))
            if (r11 <= 0) goto L_0x0085
            boolean r1 = r14.a((androidx.constraintlayout.solver.SolverVariable) r12, (androidx.constraintlayout.solver.e) r15)
            goto L_0x0078
        L_0x0085:
            if (r6 != 0) goto L_0x0090
            boolean r11 = r14.a((androidx.constraintlayout.solver.SolverVariable) r12, (androidx.constraintlayout.solver.e) r15)
            if (r11 == 0) goto L_0x0090
            r8 = r10
            r1 = r12
            r6 = 1
        L_0x0090:
            int[] r9 = r14.f361g
            r0 = r9[r0]
            int r4 = r4 + 1
            goto L_0x000b
        L_0x0098:
            if (r2 == 0) goto L_0x009b
            return r2
        L_0x009b:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.solver.a.a(androidx.constraintlayout.solver.e):androidx.constraintlayout.solver.SolverVariable");
    }

    /* access modifiers changed from: package-private */
    public final void a(b bVar, b bVar2, boolean z) {
        int i2 = this.f363i;
        while (true) {
            int i3 = 0;
            while (i2 != -1 && i3 < this.a) {
                int i4 = this.f360f[i2];
                SolverVariable solverVariable = bVar2.a;
                if (i4 == solverVariable.b) {
                    float f2 = this.f362h[i2];
                    a(solverVariable, z);
                    a aVar = bVar2.d;
                    int i5 = aVar.f363i;
                    int i6 = 0;
                    while (i5 != -1 && i6 < aVar.a) {
                        a(this.c.c[aVar.f360f[i5]], aVar.f362h[i5] * f2, z);
                        i5 = aVar.f361g[i5];
                        i6++;
                    }
                    bVar.b += bVar2.b * f2;
                    if (z) {
                        bVar2.a.b(bVar);
                    }
                    i2 = this.f363i;
                } else {
                    i2 = this.f361g[i2];
                    i3++;
                }
            }
            return;
        }
    }

    /* access modifiers changed from: package-private */
    public void a(b bVar, b[] bVarArr) {
        int i2 = this.f363i;
        while (true) {
            int i3 = 0;
            while (i2 != -1 && i3 < this.a) {
                SolverVariable solverVariable = this.c.c[this.f360f[i2]];
                if (solverVariable.c != -1) {
                    float f2 = this.f362h[i2];
                    a(solverVariable, true);
                    b bVar2 = bVarArr[solverVariable.c];
                    if (!bVar2.e) {
                        a aVar = bVar2.d;
                        int i4 = aVar.f363i;
                        int i5 = 0;
                        while (i4 != -1 && i5 < aVar.a) {
                            a(this.c.c[aVar.f360f[i4]], aVar.f362h[i4] * f2, true);
                            i4 = aVar.f361g[i4];
                            i5++;
                        }
                    }
                    bVar.b += bVar2.b * f2;
                    bVar2.a.b(bVar);
                    i2 = this.f363i;
                } else {
                    i2 = this.f361g[i2];
                    i3++;
                }
            }
            return;
        }
    }

    /* access modifiers changed from: package-private */
    public SolverVariable a(boolean[] zArr, SolverVariable solverVariable) {
        SolverVariable.Type type;
        int i2 = this.f363i;
        int i3 = 0;
        SolverVariable solverVariable2 = null;
        float f2 = 0.0f;
        while (i2 != -1 && i3 < this.a) {
            if (this.f362h[i2] < 0.0f) {
                SolverVariable solverVariable3 = this.c.c[this.f360f[i2]];
                if ((zArr == null || !zArr[solverVariable3.b]) && solverVariable3 != solverVariable && ((type = solverVariable3.f356g) == SolverVariable.Type.SLACK || type == SolverVariable.Type.ERROR)) {
                    float f3 = this.f362h[i2];
                    if (f3 < f2) {
                        solverVariable2 = solverVariable3;
                        f2 = f3;
                    }
                }
            }
            i2 = this.f361g[i2];
            i3++;
        }
        return solverVariable2;
    }

    /* access modifiers changed from: package-private */
    public final SolverVariable a(int i2) {
        int i3 = this.f363i;
        int i4 = 0;
        while (i3 != -1 && i4 < this.a) {
            if (i4 == i2) {
                return this.c.c[this.f360f[i3]];
            }
            i3 = this.f361g[i3];
            i4++;
        }
        return null;
    }
}
