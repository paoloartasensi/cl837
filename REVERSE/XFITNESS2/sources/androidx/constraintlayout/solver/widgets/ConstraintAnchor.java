package androidx.constraintlayout.solver.widgets;

import androidx.constraintlayout.solver.SolverVariable;
import androidx.constraintlayout.solver.c;

public class ConstraintAnchor {
    private k a = new k(this);
    final ConstraintWidget b;
    final Type c;
    ConstraintAnchor d;
    public int e = 0;

    /* renamed from: f  reason: collision with root package name */
    int f375f = -1;

    /* renamed from: g  reason: collision with root package name */
    private Strength f376g = Strength.NONE;

    /* renamed from: h  reason: collision with root package name */
    private int f377h;

    /* renamed from: i  reason: collision with root package name */
    SolverVariable f378i;

    public enum ConnectionType {
        RELAXED,
        STRICT
    }

    public enum Strength {
        NONE,
        STRONG,
        WEAK
    }

    public enum Type {
        NONE,
        LEFT,
        TOP,
        RIGHT,
        BOTTOM,
        BASELINE,
        CENTER,
        CENTER_X,
        CENTER_Y
    }

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
                androidx.constraintlayout.solver.widgets.ConstraintAnchor$Type r1 = androidx.constraintlayout.solver.widgets.ConstraintAnchor.Type.CENTER     // Catch:{ NoSuchFieldError -> 0x0012 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0012 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0012 }
            L_0x0012:
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x001d }
                androidx.constraintlayout.solver.widgets.ConstraintAnchor$Type r1 = androidx.constraintlayout.solver.widgets.ConstraintAnchor.Type.LEFT     // Catch:{ NoSuchFieldError -> 0x001d }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001d }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001d }
            L_0x001d:
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x0028 }
                androidx.constraintlayout.solver.widgets.ConstraintAnchor$Type r1 = androidx.constraintlayout.solver.widgets.ConstraintAnchor.Type.RIGHT     // Catch:{ NoSuchFieldError -> 0x0028 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0028 }
                r2 = 3
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0028 }
            L_0x0028:
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x0033 }
                androidx.constraintlayout.solver.widgets.ConstraintAnchor$Type r1 = androidx.constraintlayout.solver.widgets.ConstraintAnchor.Type.TOP     // Catch:{ NoSuchFieldError -> 0x0033 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0033 }
                r2 = 4
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0033 }
            L_0x0033:
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x003e }
                androidx.constraintlayout.solver.widgets.ConstraintAnchor$Type r1 = androidx.constraintlayout.solver.widgets.ConstraintAnchor.Type.BOTTOM     // Catch:{ NoSuchFieldError -> 0x003e }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x003e }
                r2 = 5
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x003e }
            L_0x003e:
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x0049 }
                androidx.constraintlayout.solver.widgets.ConstraintAnchor$Type r1 = androidx.constraintlayout.solver.widgets.ConstraintAnchor.Type.BASELINE     // Catch:{ NoSuchFieldError -> 0x0049 }
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
            throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.solver.widgets.ConstraintAnchor.a.<clinit>():void");
        }
    }

    public ConstraintAnchor(ConstraintWidget constraintWidget, Type type) {
        ConnectionType connectionType = ConnectionType.RELAXED;
        this.f377h = 0;
        this.b = constraintWidget;
        this.c = type;
    }

    public void a(c cVar) {
        SolverVariable solverVariable = this.f378i;
        if (solverVariable == null) {
            this.f378i = new SolverVariable(SolverVariable.Type.UNRESTRICTED, (String) null);
        } else {
            solverVariable.a();
        }
    }

    public int b() {
        ConstraintAnchor constraintAnchor;
        if (this.b.r() == 8) {
            return 0;
        }
        if (this.f375f <= -1 || (constraintAnchor = this.d) == null || constraintAnchor.b.r() != 8) {
            return this.e;
        }
        return this.f375f;
    }

    public ConstraintWidget c() {
        return this.b;
    }

    public k d() {
        return this.a;
    }

    public SolverVariable e() {
        return this.f378i;
    }

    public Strength f() {
        return this.f376g;
    }

    public ConstraintAnchor g() {
        return this.d;
    }

    public Type h() {
        return this.c;
    }

    public boolean i() {
        return this.d != null;
    }

    public void j() {
        this.d = null;
        this.e = 0;
        this.f375f = -1;
        this.f376g = Strength.STRONG;
        this.f377h = 0;
        ConnectionType connectionType = ConnectionType.RELAXED;
        this.a.d();
    }

    public String toString() {
        return this.b.f() + ":" + this.c.toString();
    }

    public int a() {
        return this.f377h;
    }

    public boolean a(ConstraintAnchor constraintAnchor, int i2, Strength strength, int i3) {
        return a(constraintAnchor, i2, -1, strength, i3, false);
    }

    public boolean a(ConstraintAnchor constraintAnchor, int i2, int i3, Strength strength, int i4, boolean z) {
        if (constraintAnchor == null) {
            this.d = null;
            this.e = 0;
            this.f375f = -1;
            this.f376g = Strength.NONE;
            this.f377h = 2;
            return true;
        } else if (!z && !a(constraintAnchor)) {
            return false;
        } else {
            this.d = constraintAnchor;
            if (i2 > 0) {
                this.e = i2;
            } else {
                this.e = 0;
            }
            this.f375f = i3;
            this.f376g = strength;
            this.f377h = i4;
            return true;
        }
    }

    public boolean a(ConstraintAnchor constraintAnchor) {
        boolean z = false;
        if (constraintAnchor == null) {
            return false;
        }
        Type h2 = constraintAnchor.h();
        Type type = this.c;
        if (h2 != type) {
            switch (a.a[type.ordinal()]) {
                case 1:
                    if (h2 == Type.BASELINE || h2 == Type.CENTER_X || h2 == Type.CENTER_Y) {
                        return false;
                    }
                    return true;
                case 2:
                case 3:
                    boolean z2 = h2 == Type.LEFT || h2 == Type.RIGHT;
                    if (!(constraintAnchor.c() instanceof g)) {
                        return z2;
                    }
                    if (z2 || h2 == Type.CENTER_X) {
                        z = true;
                    }
                    return z;
                case 4:
                case 5:
                    boolean z3 = h2 == Type.TOP || h2 == Type.BOTTOM;
                    if (!(constraintAnchor.c() instanceof g)) {
                        return z3;
                    }
                    if (z3 || h2 == Type.CENTER_Y) {
                        z = true;
                    }
                    return z;
                case 6:
                case 7:
                case 8:
                case 9:
                    return false;
                default:
                    throw new AssertionError(this.c.name());
            }
        } else if (type != Type.BASELINE || (constraintAnchor.c().x() && c().x())) {
            return true;
        } else {
            return false;
        }
    }
}
