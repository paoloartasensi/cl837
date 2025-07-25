package androidx.constraintlayout.solver.widgets;

import androidx.constraintlayout.solver.widgets.ConstraintWidget;
import java.util.ArrayList;

/* compiled from: ChainHead */
public class d {
    protected ConstraintWidget a;
    protected ConstraintWidget b;
    protected ConstraintWidget c;
    protected ConstraintWidget d;
    protected ConstraintWidget e;

    /* renamed from: f  reason: collision with root package name */
    protected ConstraintWidget f384f;

    /* renamed from: g  reason: collision with root package name */
    protected ConstraintWidget f385g;

    /* renamed from: h  reason: collision with root package name */
    protected ArrayList<ConstraintWidget> f386h;

    /* renamed from: i  reason: collision with root package name */
    protected int f387i;

    /* renamed from: j  reason: collision with root package name */
    protected int f388j;
    protected float k = 0.0f;
    private int l;
    private boolean m = false;
    protected boolean n;
    protected boolean o;
    protected boolean p;
    private boolean q;

    public d(ConstraintWidget constraintWidget, int i2, boolean z) {
        this.a = constraintWidget;
        this.l = i2;
        this.m = z;
    }

    private static boolean a(ConstraintWidget constraintWidget, int i2) {
        if (constraintWidget.r() != 8 && constraintWidget.C[i2] == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
            int[] iArr = constraintWidget.f380g;
            if (iArr[i2] == 0 || iArr[i2] == 3) {
                return true;
            }
        }
        return false;
    }

    private void b() {
        int i2 = this.l * 2;
        ConstraintWidget constraintWidget = this.a;
        boolean z = false;
        ConstraintWidget constraintWidget2 = constraintWidget;
        boolean z2 = false;
        while (!z2) {
            this.f387i++;
            ConstraintWidget[] constraintWidgetArr = constraintWidget.i0;
            int i3 = this.l;
            ConstraintWidget constraintWidget3 = null;
            constraintWidgetArr[i3] = null;
            constraintWidget.h0[i3] = null;
            if (constraintWidget.r() != 8) {
                if (this.b == null) {
                    this.b = constraintWidget;
                }
                this.d = constraintWidget;
                ConstraintWidget.DimensionBehaviour[] dimensionBehaviourArr = constraintWidget.C;
                int i4 = this.l;
                if (dimensionBehaviourArr[i4] == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                    int[] iArr = constraintWidget.f380g;
                    if (iArr[i4] == 0 || iArr[i4] == 3 || iArr[i4] == 2) {
                        this.f388j++;
                        float[] fArr = constraintWidget.g0;
                        int i5 = this.l;
                        float f2 = fArr[i5];
                        if (f2 > 0.0f) {
                            this.k += fArr[i5];
                        }
                        if (a(constraintWidget, this.l)) {
                            if (f2 < 0.0f) {
                                this.n = true;
                            } else {
                                this.o = true;
                            }
                            if (this.f386h == null) {
                                this.f386h = new ArrayList<>();
                            }
                            this.f386h.add(constraintWidget);
                        }
                        if (this.f384f == null) {
                            this.f384f = constraintWidget;
                        }
                        ConstraintWidget constraintWidget4 = this.f385g;
                        if (constraintWidget4 != null) {
                            constraintWidget4.h0[this.l] = constraintWidget;
                        }
                        this.f385g = constraintWidget;
                    }
                }
            }
            if (constraintWidget2 != constraintWidget) {
                constraintWidget2.i0[this.l] = constraintWidget;
            }
            ConstraintAnchor constraintAnchor = constraintWidget.A[i2 + 1].d;
            if (constraintAnchor != null) {
                ConstraintWidget constraintWidget5 = constraintAnchor.b;
                ConstraintAnchor[] constraintAnchorArr = constraintWidget5.A;
                if (constraintAnchorArr[i2].d != null && constraintAnchorArr[i2].d.b == constraintWidget) {
                    constraintWidget3 = constraintWidget5;
                }
            }
            if (constraintWidget3 == null) {
                constraintWidget3 = constraintWidget;
                z2 = true;
            }
            constraintWidget2 = constraintWidget;
            constraintWidget = constraintWidget3;
        }
        this.c = constraintWidget;
        if (this.l != 0 || !this.m) {
            this.e = this.a;
        } else {
            this.e = constraintWidget;
        }
        if (this.o && this.n) {
            z = true;
        }
        this.p = z;
    }

    public void a() {
        if (!this.q) {
            b();
        }
        this.q = true;
    }
}
