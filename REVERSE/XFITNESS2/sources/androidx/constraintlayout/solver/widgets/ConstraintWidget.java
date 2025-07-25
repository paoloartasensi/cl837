package androidx.constraintlayout.solver.widgets;

import androidx.constraintlayout.solver.c;
import androidx.constraintlayout.solver.e;
import androidx.constraintlayout.solver.widgets.ConstraintAnchor;
import com.jeremyliao.liveeventbus.BuildConfig;
import java.util.ArrayList;

public class ConstraintWidget {
    public static float j0 = 0.5f;
    protected ConstraintAnchor[] A;
    protected ArrayList<ConstraintAnchor> B;
    protected DimensionBehaviour[] C;
    ConstraintWidget D;
    int E;
    int F;
    protected float G;
    protected int H;
    protected int I;
    protected int J;
    int K;
    int L;
    private int M;
    private int N;
    protected int O;
    protected int P;
    int Q;
    protected int R;
    protected int S;
    private int T;
    private int U;
    float V;
    float W;
    private Object X;
    private int Y;
    private String Z;
    public int a = -1;
    private String a0;
    public int b = -1;
    boolean b0;
    l c;
    boolean c0;
    l d;
    boolean d0;
    int e = 0;
    int e0;

    /* renamed from: f  reason: collision with root package name */
    int f379f = 0;
    int f0;

    /* renamed from: g  reason: collision with root package name */
    int[] f380g = new int[2];
    float[] g0;

    /* renamed from: h  reason: collision with root package name */
    int f381h = 0;
    protected ConstraintWidget[] h0;

    /* renamed from: i  reason: collision with root package name */
    int f382i = 0;
    protected ConstraintWidget[] i0;

    /* renamed from: j  reason: collision with root package name */
    float f383j = 1.0f;
    int k = 0;
    int l = 0;
    float m = 1.0f;
    int n = -1;
    float o = 1.0f;
    f p = null;
    private int[] q = {Integer.MAX_VALUE, Integer.MAX_VALUE};
    private float r = 0.0f;
    ConstraintAnchor s = new ConstraintAnchor(this, ConstraintAnchor.Type.LEFT);
    ConstraintAnchor t = new ConstraintAnchor(this, ConstraintAnchor.Type.TOP);
    ConstraintAnchor u = new ConstraintAnchor(this, ConstraintAnchor.Type.RIGHT);
    ConstraintAnchor v = new ConstraintAnchor(this, ConstraintAnchor.Type.BOTTOM);
    ConstraintAnchor w = new ConstraintAnchor(this, ConstraintAnchor.Type.BASELINE);
    ConstraintAnchor x = new ConstraintAnchor(this, ConstraintAnchor.Type.CENTER_X);
    ConstraintAnchor y = new ConstraintAnchor(this, ConstraintAnchor.Type.CENTER_Y);
    ConstraintAnchor z;

    public enum DimensionBehaviour {
        FIXED,
        WRAP_CONTENT,
        MATCH_CONSTRAINT,
        MATCH_PARENT
    }

    static /* synthetic */ class a {
        static final /* synthetic */ int[] a;
        static final /* synthetic */ int[] b;

        /* JADX WARNING: Can't wrap try/catch for region: R(29:0|(2:1|2)|3|(2:5|6)|7|9|10|11|(2:13|14)|15|17|18|19|20|21|22|23|24|25|26|27|28|29|30|31|32|33|34|36) */
        /* JADX WARNING: Can't wrap try/catch for region: R(31:0|1|2|3|(2:5|6)|7|9|10|11|13|14|15|17|18|19|20|21|22|23|24|25|26|27|28|29|30|31|32|33|34|36) */
        /* JADX WARNING: Can't wrap try/catch for region: R(32:0|1|2|3|5|6|7|9|10|11|13|14|15|17|18|19|20|21|22|23|24|25|26|27|28|29|30|31|32|33|34|36) */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:19:0x0044 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:21:0x004e */
        /* JADX WARNING: Missing exception handler attribute for start block: B:23:0x0058 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:25:0x0062 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:27:0x006d */
        /* JADX WARNING: Missing exception handler attribute for start block: B:29:0x0078 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:31:0x0083 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:33:0x008f */
        static {
            /*
                androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour[] r0 = androidx.constraintlayout.solver.widgets.ConstraintWidget.DimensionBehaviour.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                b = r0
                r1 = 1
                androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour r2 = androidx.constraintlayout.solver.widgets.ConstraintWidget.DimensionBehaviour.FIXED     // Catch:{ NoSuchFieldError -> 0x0012 }
                int r2 = r2.ordinal()     // Catch:{ NoSuchFieldError -> 0x0012 }
                r0[r2] = r1     // Catch:{ NoSuchFieldError -> 0x0012 }
            L_0x0012:
                r0 = 2
                int[] r2 = b     // Catch:{ NoSuchFieldError -> 0x001d }
                androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour r3 = androidx.constraintlayout.solver.widgets.ConstraintWidget.DimensionBehaviour.WRAP_CONTENT     // Catch:{ NoSuchFieldError -> 0x001d }
                int r3 = r3.ordinal()     // Catch:{ NoSuchFieldError -> 0x001d }
                r2[r3] = r0     // Catch:{ NoSuchFieldError -> 0x001d }
            L_0x001d:
                r2 = 3
                int[] r3 = b     // Catch:{ NoSuchFieldError -> 0x0028 }
                androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour r4 = androidx.constraintlayout.solver.widgets.ConstraintWidget.DimensionBehaviour.MATCH_PARENT     // Catch:{ NoSuchFieldError -> 0x0028 }
                int r4 = r4.ordinal()     // Catch:{ NoSuchFieldError -> 0x0028 }
                r3[r4] = r2     // Catch:{ NoSuchFieldError -> 0x0028 }
            L_0x0028:
                r3 = 4
                int[] r4 = b     // Catch:{ NoSuchFieldError -> 0x0033 }
                androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour r5 = androidx.constraintlayout.solver.widgets.ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT     // Catch:{ NoSuchFieldError -> 0x0033 }
                int r5 = r5.ordinal()     // Catch:{ NoSuchFieldError -> 0x0033 }
                r4[r5] = r3     // Catch:{ NoSuchFieldError -> 0x0033 }
            L_0x0033:
                androidx.constraintlayout.solver.widgets.ConstraintAnchor$Type[] r4 = androidx.constraintlayout.solver.widgets.ConstraintAnchor.Type.values()
                int r4 = r4.length
                int[] r4 = new int[r4]
                a = r4
                androidx.constraintlayout.solver.widgets.ConstraintAnchor$Type r5 = androidx.constraintlayout.solver.widgets.ConstraintAnchor.Type.LEFT     // Catch:{ NoSuchFieldError -> 0x0044 }
                int r5 = r5.ordinal()     // Catch:{ NoSuchFieldError -> 0x0044 }
                r4[r5] = r1     // Catch:{ NoSuchFieldError -> 0x0044 }
            L_0x0044:
                int[] r1 = a     // Catch:{ NoSuchFieldError -> 0x004e }
                androidx.constraintlayout.solver.widgets.ConstraintAnchor$Type r4 = androidx.constraintlayout.solver.widgets.ConstraintAnchor.Type.TOP     // Catch:{ NoSuchFieldError -> 0x004e }
                int r4 = r4.ordinal()     // Catch:{ NoSuchFieldError -> 0x004e }
                r1[r4] = r0     // Catch:{ NoSuchFieldError -> 0x004e }
            L_0x004e:
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x0058 }
                androidx.constraintlayout.solver.widgets.ConstraintAnchor$Type r1 = androidx.constraintlayout.solver.widgets.ConstraintAnchor.Type.RIGHT     // Catch:{ NoSuchFieldError -> 0x0058 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0058 }
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0058 }
            L_0x0058:
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x0062 }
                androidx.constraintlayout.solver.widgets.ConstraintAnchor$Type r1 = androidx.constraintlayout.solver.widgets.ConstraintAnchor.Type.BOTTOM     // Catch:{ NoSuchFieldError -> 0x0062 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0062 }
                r0[r1] = r3     // Catch:{ NoSuchFieldError -> 0x0062 }
            L_0x0062:
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x006d }
                androidx.constraintlayout.solver.widgets.ConstraintAnchor$Type r1 = androidx.constraintlayout.solver.widgets.ConstraintAnchor.Type.BASELINE     // Catch:{ NoSuchFieldError -> 0x006d }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x006d }
                r2 = 5
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x006d }
            L_0x006d:
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x0078 }
                androidx.constraintlayout.solver.widgets.ConstraintAnchor$Type r1 = androidx.constraintlayout.solver.widgets.ConstraintAnchor.Type.CENTER     // Catch:{ NoSuchFieldError -> 0x0078 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0078 }
                r2 = 6
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0078 }
            L_0x0078:
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x0083 }
                androidx.constraintlayout.solver.widgets.ConstraintAnchor$Type r1 = androidx.constraintlayout.solver.widgets.ConstraintAnchor.Type.CENTER_X     // Catch:{ NoSuchFieldError -> 0x0083 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0083 }
                r2 = 7
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0083 }
            L_0x0083:
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x008f }
                androidx.constraintlayout.solver.widgets.ConstraintAnchor$Type r1 = androidx.constraintlayout.solver.widgets.ConstraintAnchor.Type.CENTER_Y     // Catch:{ NoSuchFieldError -> 0x008f }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x008f }
                r2 = 8
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x008f }
            L_0x008f:
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x009b }
                androidx.constraintlayout.solver.widgets.ConstraintAnchor$Type r1 = androidx.constraintlayout.solver.widgets.ConstraintAnchor.Type.NONE     // Catch:{ NoSuchFieldError -> 0x009b }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x009b }
                r2 = 9
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x009b }
            L_0x009b:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.solver.widgets.ConstraintWidget.a.<clinit>():void");
        }
    }

    public ConstraintWidget() {
        ConstraintAnchor constraintAnchor = new ConstraintAnchor(this, ConstraintAnchor.Type.CENTER);
        this.z = constraintAnchor;
        this.A = new ConstraintAnchor[]{this.s, this.u, this.t, this.v, this.w, constraintAnchor};
        this.B = new ArrayList<>();
        DimensionBehaviour dimensionBehaviour = DimensionBehaviour.FIXED;
        this.C = new DimensionBehaviour[]{dimensionBehaviour, dimensionBehaviour};
        this.D = null;
        this.E = 0;
        this.F = 0;
        this.G = 0.0f;
        this.H = -1;
        this.I = 0;
        this.J = 0;
        this.K = 0;
        this.L = 0;
        this.M = 0;
        this.N = 0;
        this.O = 0;
        this.P = 0;
        this.Q = 0;
        float f2 = j0;
        this.V = f2;
        this.W = f2;
        this.Y = 0;
        this.Z = null;
        this.a0 = null;
        this.b0 = false;
        this.c0 = false;
        this.d0 = false;
        this.e0 = 0;
        this.f0 = 0;
        this.g0 = new float[]{-1.0f, -1.0f};
        this.h0 = new ConstraintWidget[]{null, null};
        this.i0 = new ConstraintWidget[]{null, null};
        J();
    }

    private void J() {
        this.B.add(this.s);
        this.B.add(this.t);
        this.B.add(this.u);
        this.B.add(this.v);
        this.B.add(this.x);
        this.B.add(this.y);
        this.B.add(this.z);
        this.B.add(this.w);
    }

    public boolean A() {
        ConstraintAnchor constraintAnchor = this.t;
        ConstraintAnchor constraintAnchor2 = constraintAnchor.d;
        if (constraintAnchor2 != null && constraintAnchor2.d == constraintAnchor) {
            return true;
        }
        ConstraintAnchor constraintAnchor3 = this.v;
        ConstraintAnchor constraintAnchor4 = constraintAnchor3.d;
        return constraintAnchor4 != null && constraintAnchor4.d == constraintAnchor3;
    }

    public boolean B() {
        return this.f379f == 0 && this.G == 0.0f && this.k == 0 && this.l == 0 && this.C[1] == DimensionBehaviour.MATCH_CONSTRAINT;
    }

    public boolean C() {
        return this.e == 0 && this.G == 0.0f && this.f381h == 0 && this.f382i == 0 && this.C[0] == DimensionBehaviour.MATCH_CONSTRAINT;
    }

    public void D() {
        this.s.j();
        this.t.j();
        this.u.j();
        this.v.j();
        this.w.j();
        this.x.j();
        this.y.j();
        this.z.j();
        this.D = null;
        this.r = 0.0f;
        this.E = 0;
        this.F = 0;
        this.G = 0.0f;
        this.H = -1;
        this.I = 0;
        this.J = 0;
        this.M = 0;
        this.N = 0;
        this.O = 0;
        this.P = 0;
        this.Q = 0;
        this.R = 0;
        this.S = 0;
        this.T = 0;
        this.U = 0;
        float f2 = j0;
        this.V = f2;
        this.W = f2;
        DimensionBehaviour[] dimensionBehaviourArr = this.C;
        DimensionBehaviour dimensionBehaviour = DimensionBehaviour.FIXED;
        dimensionBehaviourArr[0] = dimensionBehaviour;
        dimensionBehaviourArr[1] = dimensionBehaviour;
        this.X = null;
        this.Y = 0;
        this.a0 = null;
        this.e0 = 0;
        this.f0 = 0;
        float[] fArr = this.g0;
        fArr[0] = -1.0f;
        fArr[1] = -1.0f;
        this.a = -1;
        this.b = -1;
        int[] iArr = this.q;
        iArr[0] = Integer.MAX_VALUE;
        iArr[1] = Integer.MAX_VALUE;
        this.e = 0;
        this.f379f = 0;
        this.f383j = 1.0f;
        this.m = 1.0f;
        this.f382i = Integer.MAX_VALUE;
        this.l = Integer.MAX_VALUE;
        this.f381h = 0;
        this.k = 0;
        this.n = -1;
        this.o = 1.0f;
        l lVar = this.c;
        if (lVar != null) {
            lVar.d();
        }
        l lVar2 = this.d;
        if (lVar2 != null) {
            lVar2.d();
        }
        this.p = null;
        this.b0 = false;
        this.c0 = false;
        this.d0 = false;
    }

    public void E() {
        ConstraintWidget k2 = k();
        if (k2 == null || !(k2 instanceof e) || !((e) k()).N()) {
            int size = this.B.size();
            for (int i2 = 0; i2 < size; i2++) {
                this.B.get(i2).j();
            }
        }
    }

    public void F() {
        for (int i2 = 0; i2 < 6; i2++) {
            this.A[i2].d().d();
        }
    }

    public void G() {
    }

    public void H() {
        int i2 = this.I;
        int i3 = this.J;
        this.M = i2;
        this.N = i3;
    }

    public void I() {
        for (int i2 = 0; i2 < 6; i2++) {
            this.A[i2].d().g();
        }
    }

    public void a(int i2) {
        i.a(i2, this);
    }

    public void a(boolean z2) {
    }

    public void b(e eVar) {
        eVar.a((Object) this.s);
        eVar.a((Object) this.t);
        eVar.a((Object) this.u);
        eVar.a((Object) this.v);
        if (this.Q > 0) {
            eVar.a((Object) this.w);
        }
    }

    public void b(boolean z2) {
    }

    public int c() {
        return this.Q;
    }

    public int d(int i2) {
        if (i2 == 0) {
            return s();
        }
        if (i2 == 1) {
            return i();
        }
        return 0;
    }

    public Object e() {
        return this.X;
    }

    public String f() {
        return this.Z;
    }

    public int g() {
        return this.M + this.O;
    }

    public int h() {
        return this.N + this.P;
    }

    public void i(int i2) {
        this.q[1] = i2;
    }

    public void j(int i2) {
        this.q[0] = i2;
    }

    public ConstraintWidget k() {
        return this.D;
    }

    public l l() {
        if (this.d == null) {
            this.d = new l();
        }
        return this.d;
    }

    public l m() {
        if (this.c == null) {
            this.c = new l();
        }
        return this.c;
    }

    public void n(int i2) {
        this.Y = i2;
    }

    /* access modifiers changed from: protected */
    public int o() {
        return this.I + this.O;
    }

    /* access modifiers changed from: protected */
    public int p() {
        return this.J + this.P;
    }

    public void q(int i2) {
        this.T = i2;
    }

    public int r() {
        return this.Y;
    }

    public int s() {
        if (this.Y == 8) {
            return 0;
        }
        return this.E;
    }

    public int t() {
        return this.U;
    }

    public String toString() {
        String str;
        StringBuilder sb = new StringBuilder();
        String str2 = this.a0;
        String str3 = BuildConfig.FLAVOR;
        if (str2 != null) {
            str = "type: " + this.a0 + " ";
        } else {
            str = str3;
        }
        sb.append(str);
        if (this.Z != null) {
            str3 = "id: " + this.Z + " ";
        }
        sb.append(str3);
        sb.append("(");
        sb.append(this.I);
        sb.append(", ");
        sb.append(this.J);
        sb.append(") - (");
        sb.append(this.E);
        sb.append(" x ");
        sb.append(this.F);
        sb.append(") wrap: (");
        sb.append(this.T);
        sb.append(" x ");
        sb.append(this.U);
        sb.append(")");
        return sb.toString();
    }

    public int u() {
        return this.T;
    }

    public int v() {
        return this.I;
    }

    public int w() {
        return this.J;
    }

    public boolean x() {
        return this.Q > 0;
    }

    public boolean y() {
        if (this.s.d().b == 1 && this.u.d().b == 1 && this.t.d().b == 1 && this.v.d().b == 1) {
            return true;
        }
        return false;
    }

    public boolean z() {
        ConstraintAnchor constraintAnchor = this.s;
        ConstraintAnchor constraintAnchor2 = constraintAnchor.d;
        if (constraintAnchor2 != null && constraintAnchor2.d == constraintAnchor) {
            return true;
        }
        ConstraintAnchor constraintAnchor3 = this.u;
        ConstraintAnchor constraintAnchor4 = constraintAnchor3.d;
        return constraintAnchor4 != null && constraintAnchor4.d == constraintAnchor3;
    }

    private boolean t(int i2) {
        int i3 = i2 * 2;
        ConstraintAnchor[] constraintAnchorArr = this.A;
        if (!(constraintAnchorArr[i3].d == null || constraintAnchorArr[i3].d.d == constraintAnchorArr[i3])) {
            int i4 = i3 + 1;
            return constraintAnchorArr[i4].d != null && constraintAnchorArr[i4].d.d == constraintAnchorArr[i4];
        }
    }

    public void a(c cVar) {
        this.s.a(cVar);
        this.t.a(cVar);
        this.u.a(cVar);
        this.v.a(cVar);
        this.w.a(cVar);
        this.z.a(cVar);
        this.x.a(cVar);
        this.y.a(cVar);
    }

    public void c(int i2, int i3) {
        this.I = i2;
        this.J = i3;
    }

    public void e(int i2, int i3) {
        this.J = i2;
        int i4 = i3 - i2;
        this.F = i4;
        int i5 = this.S;
        if (i4 < i5) {
            this.F = i5;
        }
    }

    public void f(int i2) {
        this.Q = i2;
    }

    public void g(int i2) {
        this.F = i2;
        int i3 = this.S;
        if (i2 < i3) {
            this.F = i3;
        }
    }

    public void h(int i2) {
        this.e0 = i2;
    }

    public int i() {
        if (this.Y == 8) {
            return 0;
        }
        return this.F;
    }

    public DimensionBehaviour j() {
        return this.C[0];
    }

    public void k(int i2) {
        if (i2 < 0) {
            this.S = 0;
        } else {
            this.S = i2;
        }
    }

    public int n() {
        return v() + this.E;
    }

    public void o(int i2) {
        this.E = i2;
        int i3 = this.R;
        if (i2 < i3) {
            this.E = i3;
        }
    }

    public void p(int i2) {
        this.U = i2;
    }

    public DimensionBehaviour q() {
        return this.C[1];
    }

    public void r(int i2) {
        this.I = i2;
    }

    public int d() {
        return w() + this.F;
    }

    public void s(int i2) {
        this.J = i2;
    }

    public void c(float f2) {
        this.W = f2;
    }

    /* access modifiers changed from: package-private */
    public void d(int i2, int i3) {
        if (i3 == 0) {
            this.K = i2;
        } else if (i3 == 1) {
            this.L = i2;
        }
    }

    public void l(int i2) {
        if (i2 < 0) {
            this.R = 0;
        } else {
            this.R = i2;
        }
    }

    public void m(int i2) {
        this.f0 = i2;
    }

    public DimensionBehaviour c(int i2) {
        if (i2 == 0) {
            return j();
        }
        if (i2 == 1) {
            return q();
        }
        return null;
    }

    public void d(float f2) {
        this.g0[1] = f2;
    }

    /* access modifiers changed from: package-private */
    public int e(int i2) {
        if (i2 == 0) {
            return this.K;
        }
        if (i2 == 1) {
            return this.L;
        }
        return 0;
    }

    public float b(int i2) {
        if (i2 == 0) {
            return this.V;
        }
        if (i2 == 1) {
            return this.W;
        }
        return -1.0f;
    }

    public void c(e eVar) {
        int b2 = eVar.b((Object) this.s);
        int b3 = eVar.b((Object) this.t);
        int b4 = eVar.b((Object) this.u);
        int b5 = eVar.b((Object) this.v);
        int i2 = b5 - b3;
        if (b4 - b2 < 0 || i2 < 0 || b2 == Integer.MIN_VALUE || b2 == Integer.MAX_VALUE || b3 == Integer.MIN_VALUE || b3 == Integer.MAX_VALUE || b4 == Integer.MIN_VALUE || b4 == Integer.MAX_VALUE || b5 == Integer.MIN_VALUE || b5 == Integer.MAX_VALUE) {
            b5 = 0;
            b2 = 0;
            b3 = 0;
            b4 = 0;
        }
        a(b2, b3, b4, b5);
    }

    public ArrayList<ConstraintAnchor> b() {
        return this.B;
    }

    public void a(ConstraintWidget constraintWidget) {
        this.D = constraintWidget;
    }

    public void b(int i2, int i3) {
        this.O = i2;
        this.P = i3;
    }

    public void a(ConstraintWidget constraintWidget, float f2, int i2) {
        ConstraintAnchor.Type type = ConstraintAnchor.Type.CENTER;
        a(type, constraintWidget, type, i2, 0);
        this.r = f2;
    }

    public void b(int i2, int i3, int i4, float f2) {
        this.f379f = i2;
        this.k = i3;
        this.l = i4;
        this.m = f2;
        if (f2 < 1.0f && i2 == 0) {
            this.f379f = 2;
        }
    }

    public void a(String str) {
        this.Z = str;
    }

    public void a(int i2, int i3, int i4, float f2) {
        this.e = i2;
        this.f381h = i3;
        this.f382i = i4;
        this.f383j = f2;
        if (f2 < 1.0f && i2 == 0) {
            this.e = 2;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:39:0x0089  */
    /* JADX WARNING: Removed duplicated region for block: B:43:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void b(java.lang.String r9) {
        /*
            r8 = this;
            r0 = 0
            if (r9 == 0) goto L_0x008e
            int r1 = r9.length()
            if (r1 != 0) goto L_0x000b
            goto L_0x008e
        L_0x000b:
            r1 = -1
            int r2 = r9.length()
            r3 = 44
            int r3 = r9.indexOf(r3)
            r4 = 0
            r5 = 1
            if (r3 <= 0) goto L_0x0037
            int r6 = r2 + -1
            if (r3 >= r6) goto L_0x0037
            java.lang.String r6 = r9.substring(r4, r3)
            java.lang.String r7 = "W"
            boolean r7 = r6.equalsIgnoreCase(r7)
            if (r7 == 0) goto L_0x002c
            r1 = 0
            goto L_0x0035
        L_0x002c:
            java.lang.String r4 = "H"
            boolean r4 = r6.equalsIgnoreCase(r4)
            if (r4 == 0) goto L_0x0035
            r1 = 1
        L_0x0035:
            int r4 = r3 + 1
        L_0x0037:
            r3 = 58
            int r3 = r9.indexOf(r3)
            if (r3 < 0) goto L_0x0075
            int r2 = r2 - r5
            if (r3 >= r2) goto L_0x0075
            java.lang.String r2 = r9.substring(r4, r3)
            int r3 = r3 + r5
            java.lang.String r9 = r9.substring(r3)
            int r3 = r2.length()
            if (r3 <= 0) goto L_0x0084
            int r3 = r9.length()
            if (r3 <= 0) goto L_0x0084
            float r2 = java.lang.Float.parseFloat(r2)     // Catch:{ NumberFormatException -> 0x0084 }
            float r9 = java.lang.Float.parseFloat(r9)     // Catch:{ NumberFormatException -> 0x0084 }
            int r3 = (r2 > r0 ? 1 : (r2 == r0 ? 0 : -1))
            if (r3 <= 0) goto L_0x0084
            int r3 = (r9 > r0 ? 1 : (r9 == r0 ? 0 : -1))
            if (r3 <= 0) goto L_0x0084
            if (r1 != r5) goto L_0x006f
            float r9 = r9 / r2
            float r9 = java.lang.Math.abs(r9)     // Catch:{ NumberFormatException -> 0x0084 }
            goto L_0x0085
        L_0x006f:
            float r2 = r2 / r9
            float r9 = java.lang.Math.abs(r2)     // Catch:{ NumberFormatException -> 0x0084 }
            goto L_0x0085
        L_0x0075:
            java.lang.String r9 = r9.substring(r4)
            int r2 = r9.length()
            if (r2 <= 0) goto L_0x0084
            float r9 = java.lang.Float.parseFloat(r9)     // Catch:{ NumberFormatException -> 0x0084 }
            goto L_0x0085
        L_0x0084:
            r9 = 0
        L_0x0085:
            int r0 = (r9 > r0 ? 1 : (r9 == r0 ? 0 : -1))
            if (r0 <= 0) goto L_0x008d
            r8.G = r9
            r8.H = r1
        L_0x008d:
            return
        L_0x008e:
            r8.G = r0
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.solver.widgets.ConstraintWidget.b(java.lang.String):void");
    }

    public void a(float f2) {
        this.V = f2;
    }

    public void a(int i2, int i3, int i4, int i5) {
        int i6;
        int i7;
        int i8 = i4 - i2;
        int i9 = i5 - i3;
        this.I = i2;
        this.J = i3;
        if (this.Y == 8) {
            this.E = 0;
            this.F = 0;
            return;
        }
        if (this.C[0] == DimensionBehaviour.FIXED && i8 < (i7 = this.E)) {
            i8 = i7;
        }
        if (this.C[1] == DimensionBehaviour.FIXED && i9 < (i6 = this.F)) {
            i9 = i6;
        }
        this.E = i8;
        this.F = i9;
        int i10 = this.S;
        if (i9 < i10) {
            this.F = i10;
        }
        int i11 = this.E;
        int i12 = this.R;
        if (i11 < i12) {
            this.E = i12;
        }
        this.c0 = true;
    }

    public void a(int i2, int i3, int i4) {
        if (i4 == 0) {
            a(i2, i3);
        } else if (i4 == 1) {
            e(i2, i3);
        }
        this.c0 = true;
    }

    public void a(int i2, int i3) {
        this.I = i2;
        int i4 = i3 - i2;
        this.E = i4;
        int i5 = this.R;
        if (i4 < i5) {
            this.E = i5;
        }
    }

    public void b(float f2) {
        this.g0[0] = f2;
    }

    public void b(DimensionBehaviour dimensionBehaviour) {
        this.C[1] = dimensionBehaviour;
        if (dimensionBehaviour == DimensionBehaviour.WRAP_CONTENT) {
            g(this.U);
        }
    }

    public void a(Object obj) {
        this.X = obj;
    }

    public boolean a() {
        return this.Y != 8;
    }

    public void a(ConstraintAnchor.Type type, ConstraintWidget constraintWidget, ConstraintAnchor.Type type2, int i2, int i3) {
        a(type).a(constraintWidget.a(type2), i2, i3, ConstraintAnchor.Strength.STRONG, 0, true);
    }

    public ConstraintAnchor a(ConstraintAnchor.Type type) {
        switch (a.a[type.ordinal()]) {
            case 1:
                return this.s;
            case 2:
                return this.t;
            case 3:
                return this.u;
            case 4:
                return this.v;
            case 5:
                return this.w;
            case 6:
                return this.z;
            case 7:
                return this.x;
            case 8:
                return this.y;
            case 9:
                return null;
            default:
                throw new AssertionError(type.name());
        }
    }

    public void a(DimensionBehaviour dimensionBehaviour) {
        this.C[0] = dimensionBehaviour;
        if (dimensionBehaviour == DimensionBehaviour.WRAP_CONTENT) {
            o(this.T);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:109:0x01bf  */
    /* JADX WARNING: Removed duplicated region for block: B:110:0x01c2  */
    /* JADX WARNING: Removed duplicated region for block: B:113:0x01d4  */
    /* JADX WARNING: Removed duplicated region for block: B:122:0x023b  */
    /* JADX WARNING: Removed duplicated region for block: B:125:0x024c A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:126:0x024d  */
    /* JADX WARNING: Removed duplicated region for block: B:152:0x02ae  */
    /* JADX WARNING: Removed duplicated region for block: B:153:0x02b7  */
    /* JADX WARNING: Removed duplicated region for block: B:156:0x02bd  */
    /* JADX WARNING: Removed duplicated region for block: B:157:0x02c5  */
    /* JADX WARNING: Removed duplicated region for block: B:160:0x02fc  */
    /* JADX WARNING: Removed duplicated region for block: B:164:0x0325  */
    /* JADX WARNING: Removed duplicated region for block: B:167:0x032f  */
    /* JADX WARNING: Removed duplicated region for block: B:169:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void a(androidx.constraintlayout.solver.e r39) {
        /*
            r38 = this;
            r15 = r38
            r14 = r39
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r0 = r15.s
            androidx.constraintlayout.solver.SolverVariable r21 = r14.a((java.lang.Object) r0)
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r0 = r15.u
            androidx.constraintlayout.solver.SolverVariable r10 = r14.a((java.lang.Object) r0)
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r0 = r15.t
            androidx.constraintlayout.solver.SolverVariable r6 = r14.a((java.lang.Object) r0)
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r0 = r15.v
            androidx.constraintlayout.solver.SolverVariable r4 = r14.a((java.lang.Object) r0)
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r0 = r15.w
            androidx.constraintlayout.solver.SolverVariable r3 = r14.a((java.lang.Object) r0)
            androidx.constraintlayout.solver.widgets.ConstraintWidget r0 = r15.D
            r1 = 8
            r2 = 1
            r13 = 0
            if (r0 == 0) goto L_0x00b0
            if (r0 == 0) goto L_0x0036
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour[] r0 = r0.C
            r0 = r0[r13]
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour r5 = androidx.constraintlayout.solver.widgets.ConstraintWidget.DimensionBehaviour.WRAP_CONTENT
            if (r0 != r5) goto L_0x0036
            r0 = 1
            goto L_0x0037
        L_0x0036:
            r0 = 0
        L_0x0037:
            androidx.constraintlayout.solver.widgets.ConstraintWidget r5 = r15.D
            if (r5 == 0) goto L_0x0045
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour[] r5 = r5.C
            r5 = r5[r2]
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour r7 = androidx.constraintlayout.solver.widgets.ConstraintWidget.DimensionBehaviour.WRAP_CONTENT
            if (r5 != r7) goto L_0x0045
            r5 = 1
            goto L_0x0046
        L_0x0045:
            r5 = 0
        L_0x0046:
            boolean r7 = r15.t(r13)
            if (r7 == 0) goto L_0x0055
            androidx.constraintlayout.solver.widgets.ConstraintWidget r7 = r15.D
            androidx.constraintlayout.solver.widgets.e r7 = (androidx.constraintlayout.solver.widgets.e) r7
            r7.a((androidx.constraintlayout.solver.widgets.ConstraintWidget) r15, (int) r13)
            r7 = 1
            goto L_0x0059
        L_0x0055:
            boolean r7 = r38.z()
        L_0x0059:
            boolean r8 = r15.t(r2)
            if (r8 == 0) goto L_0x0068
            androidx.constraintlayout.solver.widgets.ConstraintWidget r8 = r15.D
            androidx.constraintlayout.solver.widgets.e r8 = (androidx.constraintlayout.solver.widgets.e) r8
            r8.a((androidx.constraintlayout.solver.widgets.ConstraintWidget) r15, (int) r2)
            r8 = 1
            goto L_0x006c
        L_0x0068:
            boolean r8 = r38.A()
        L_0x006c:
            if (r0 == 0) goto L_0x0089
            int r9 = r15.Y
            if (r9 == r1) goto L_0x0089
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r9 = r15.s
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r9 = r9.d
            if (r9 != 0) goto L_0x0089
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r9 = r15.u
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r9 = r9.d
            if (r9 != 0) goto L_0x0089
            androidx.constraintlayout.solver.widgets.ConstraintWidget r9 = r15.D
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r9 = r9.u
            androidx.constraintlayout.solver.SolverVariable r9 = r14.a((java.lang.Object) r9)
            r14.b(r9, r10, r13, r2)
        L_0x0089:
            if (r5 == 0) goto L_0x00aa
            int r9 = r15.Y
            if (r9 == r1) goto L_0x00aa
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r9 = r15.t
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r9 = r9.d
            if (r9 != 0) goto L_0x00aa
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r9 = r15.v
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r9 = r9.d
            if (r9 != 0) goto L_0x00aa
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r9 = r15.w
            if (r9 != 0) goto L_0x00aa
            androidx.constraintlayout.solver.widgets.ConstraintWidget r9 = r15.D
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r9 = r9.v
            androidx.constraintlayout.solver.SolverVariable r9 = r14.a((java.lang.Object) r9)
            r14.b(r9, r4, r13, r2)
        L_0x00aa:
            r12 = r5
            r16 = r7
            r22 = r8
            goto L_0x00b6
        L_0x00b0:
            r0 = 0
            r12 = 0
            r16 = 0
            r22 = 0
        L_0x00b6:
            int r5 = r15.E
            int r7 = r15.R
            if (r5 >= r7) goto L_0x00bd
            r5 = r7
        L_0x00bd:
            int r7 = r15.F
            int r8 = r15.S
            if (r7 >= r8) goto L_0x00c4
            r7 = r8
        L_0x00c4:
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour[] r8 = r15.C
            r8 = r8[r13]
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour r9 = androidx.constraintlayout.solver.widgets.ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT
            if (r8 == r9) goto L_0x00ce
            r8 = 1
            goto L_0x00cf
        L_0x00ce:
            r8 = 0
        L_0x00cf:
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour[] r9 = r15.C
            r9 = r9[r2]
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour r11 = androidx.constraintlayout.solver.widgets.ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT
            if (r9 == r11) goto L_0x00d9
            r9 = 1
            goto L_0x00da
        L_0x00d9:
            r9 = 0
        L_0x00da:
            int r11 = r15.H
            r15.n = r11
            float r11 = r15.G
            r15.o = r11
            int r2 = r15.e
            int r13 = r15.f379f
            r18 = 0
            r19 = 4
            int r11 = (r11 > r18 ? 1 : (r11 == r18 ? 0 : -1))
            if (r11 <= 0) goto L_0x018f
            int r11 = r15.Y
            r1 = 8
            if (r11 == r1) goto L_0x018f
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour[] r1 = r15.C
            r11 = 0
            r1 = r1[r11]
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour r11 = androidx.constraintlayout.solver.widgets.ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT
            r23 = r3
            if (r1 != r11) goto L_0x0102
            if (r2 != 0) goto L_0x0102
            r2 = 3
        L_0x0102:
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour[] r1 = r15.C
            r11 = 1
            r1 = r1[r11]
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour r11 = androidx.constraintlayout.solver.widgets.ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT
            if (r1 != r11) goto L_0x010e
            if (r13 != 0) goto L_0x010e
            r13 = 3
        L_0x010e:
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour[] r1 = r15.C
            r11 = 0
            r3 = r1[r11]
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour r11 = androidx.constraintlayout.solver.widgets.ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT
            if (r3 != r11) goto L_0x0125
            r3 = 1
            r1 = r1[r3]
            if (r1 != r11) goto L_0x0125
            r1 = 3
            if (r2 != r1) goto L_0x0126
            if (r13 != r1) goto L_0x0126
            r15.a((boolean) r0, (boolean) r12, (boolean) r8, (boolean) r9)
            goto L_0x0184
        L_0x0125:
            r1 = 3
        L_0x0126:
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour[] r3 = r15.C
            r8 = 0
            r9 = r3[r8]
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour r11 = androidx.constraintlayout.solver.widgets.ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT
            if (r9 != r11) goto L_0x014e
            if (r2 != r1) goto L_0x014e
            r15.n = r8
            float r1 = r15.o
            int r5 = r15.F
            float r5 = (float) r5
            float r1 = r1 * r5
            int r1 = (int) r1
            r8 = 1
            r3 = r3[r8]
            r25 = r1
            if (r3 == r11) goto L_0x014b
            r26 = r7
            r29 = r13
            r27 = 0
            r28 = 4
            goto L_0x019b
        L_0x014b:
            r28 = r2
            goto L_0x0188
        L_0x014e:
            r8 = 1
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour[] r1 = r15.C
            r1 = r1[r8]
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour r3 = androidx.constraintlayout.solver.widgets.ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT
            if (r1 != r3) goto L_0x0184
            r1 = 3
            if (r13 != r1) goto L_0x0184
            r15.n = r8
            int r1 = r15.H
            r3 = -1
            if (r1 != r3) goto L_0x0168
            r1 = 1065353216(0x3f800000, float:1.0)
            float r3 = r15.o
            float r1 = r1 / r3
            r15.o = r1
        L_0x0168:
            float r1 = r15.o
            int r3 = r15.E
            float r3 = (float) r3
            float r1 = r1 * r3
            int r1 = (int) r1
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour[] r3 = r15.C
            r7 = 0
            r3 = r3[r7]
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour r7 = androidx.constraintlayout.solver.widgets.ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT
            r26 = r1
            r28 = r2
            r25 = r5
            if (r3 == r7) goto L_0x018a
            r27 = 0
            r29 = 4
            goto L_0x019b
        L_0x0184:
            r28 = r2
            r25 = r5
        L_0x0188:
            r26 = r7
        L_0x018a:
            r29 = r13
            r27 = 1
            goto L_0x019b
        L_0x018f:
            r23 = r3
            r28 = r2
            r25 = r5
            r26 = r7
            r29 = r13
            r27 = 0
        L_0x019b:
            int[] r1 = r15.f380g
            r2 = 0
            r1[r2] = r28
            r2 = 1
            r1[r2] = r29
            if (r27 == 0) goto L_0x01af
            int r1 = r15.n
            r2 = -1
            if (r1 == 0) goto L_0x01ac
            if (r1 != r2) goto L_0x01b0
        L_0x01ac:
            r24 = 1
            goto L_0x01b2
        L_0x01af:
            r2 = -1
        L_0x01b0:
            r24 = 0
        L_0x01b2:
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour[] r1 = r15.C
            r3 = 0
            r1 = r1[r3]
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour r3 = androidx.constraintlayout.solver.widgets.ConstraintWidget.DimensionBehaviour.WRAP_CONTENT
            if (r1 != r3) goto L_0x01c2
            boolean r1 = r15 instanceof androidx.constraintlayout.solver.widgets.e
            if (r1 == 0) goto L_0x01c2
            r30 = 1
            goto L_0x01c4
        L_0x01c2:
            r30 = 0
        L_0x01c4:
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r1 = r15.z
            boolean r1 = r1.i()
            r3 = 1
            r31 = r1 ^ 1
            int r1 = r15.a
            r13 = 2
            r32 = 0
            if (r1 == r13) goto L_0x023b
            androidx.constraintlayout.solver.widgets.ConstraintWidget r1 = r15.D
            if (r1 == 0) goto L_0x01e1
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r1 = r1.u
            androidx.constraintlayout.solver.SolverVariable r1 = r14.a((java.lang.Object) r1)
            r20 = r1
            goto L_0x01e3
        L_0x01e1:
            r20 = r32
        L_0x01e3:
            androidx.constraintlayout.solver.widgets.ConstraintWidget r1 = r15.D
            if (r1 == 0) goto L_0x01f0
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r1 = r1.s
            androidx.constraintlayout.solver.SolverVariable r1 = r14.a((java.lang.Object) r1)
            r33 = r1
            goto L_0x01f2
        L_0x01f0:
            r33 = r32
        L_0x01f2:
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour[] r1 = r15.C
            r17 = 0
            r5 = r1[r17]
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r7 = r15.s
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r8 = r15.u
            int r9 = r15.I
            int r11 = r15.R
            int[] r1 = r15.q
            r1 = r1[r17]
            r34 = r12
            r12 = r1
            float r1 = r15.V
            r13 = r1
            int r1 = r15.f381h
            r17 = r1
            int r1 = r15.f382i
            r18 = r1
            float r1 = r15.f383j
            r19 = r1
            r35 = r0
            r0 = r38
            r1 = r39
            r3 = -1
            r2 = r35
            r36 = r23
            r3 = r33
            r23 = r4
            r4 = r20
            r37 = r6
            r6 = r30
            r30 = r10
            r10 = r25
            r14 = r24
            r15 = r16
            r16 = r28
            r20 = r31
            r0.a(r1, r2, r3, r4, r5, r6, r7, r8, r9, r10, r11, r12, r13, r14, r15, r16, r17, r18, r19, r20)
            goto L_0x0245
        L_0x023b:
            r37 = r6
            r30 = r10
            r34 = r12
            r36 = r23
            r23 = r4
        L_0x0245:
            r15 = r38
            int r0 = r15.b
            r1 = 2
            if (r0 != r1) goto L_0x024d
            return
        L_0x024d:
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour[] r0 = r15.C
            r14 = 1
            r0 = r0[r14]
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour r1 = androidx.constraintlayout.solver.widgets.ConstraintWidget.DimensionBehaviour.WRAP_CONTENT
            if (r0 != r1) goto L_0x025c
            boolean r0 = r15 instanceof androidx.constraintlayout.solver.widgets.e
            if (r0 == 0) goto L_0x025c
            r6 = 1
            goto L_0x025d
        L_0x025c:
            r6 = 0
        L_0x025d:
            if (r27 == 0) goto L_0x0269
            int r0 = r15.n
            if (r0 == r14) goto L_0x0266
            r1 = -1
            if (r0 != r1) goto L_0x0269
        L_0x0266:
            r16 = 1
            goto L_0x026b
        L_0x0269:
            r16 = 0
        L_0x026b:
            int r0 = r15.Q
            if (r0 <= 0) goto L_0x02a4
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r0 = r15.w
            androidx.constraintlayout.solver.widgets.k r0 = r0.d()
            int r0 = r0.b
            if (r0 != r14) goto L_0x0285
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r0 = r15.w
            androidx.constraintlayout.solver.widgets.k r0 = r0.d()
            r10 = r39
            r0.a((androidx.constraintlayout.solver.e) r10)
            goto L_0x02a6
        L_0x0285:
            r10 = r39
            int r0 = r38.c()
            r1 = 6
            r2 = r36
            r4 = r37
            r10.a((androidx.constraintlayout.solver.SolverVariable) r2, (androidx.constraintlayout.solver.SolverVariable) r4, (int) r0, (int) r1)
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r0 = r15.w
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r0 = r0.d
            if (r0 == 0) goto L_0x02a8
            androidx.constraintlayout.solver.SolverVariable r0 = r10.a((java.lang.Object) r0)
            r3 = 0
            r10.a((androidx.constraintlayout.solver.SolverVariable) r2, (androidx.constraintlayout.solver.SolverVariable) r0, (int) r3, (int) r1)
            r20 = 0
            goto L_0x02aa
        L_0x02a4:
            r10 = r39
        L_0x02a6:
            r4 = r37
        L_0x02a8:
            r20 = r31
        L_0x02aa:
            androidx.constraintlayout.solver.widgets.ConstraintWidget r0 = r15.D
            if (r0 == 0) goto L_0x02b7
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r0 = r0.v
            androidx.constraintlayout.solver.SolverVariable r0 = r10.a((java.lang.Object) r0)
            r24 = r0
            goto L_0x02b9
        L_0x02b7:
            r24 = r32
        L_0x02b9:
            androidx.constraintlayout.solver.widgets.ConstraintWidget r0 = r15.D
            if (r0 == 0) goto L_0x02c5
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r0 = r0.t
            androidx.constraintlayout.solver.SolverVariable r0 = r10.a((java.lang.Object) r0)
            r3 = r0
            goto L_0x02c7
        L_0x02c5:
            r3 = r32
        L_0x02c7:
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour[] r0 = r15.C
            r5 = r0[r14]
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r7 = r15.t
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r8 = r15.v
            int r9 = r15.J
            int r11 = r15.S
            int[] r0 = r15.q
            r12 = r0[r14]
            float r13 = r15.W
            int r0 = r15.k
            r17 = r0
            int r0 = r15.l
            r18 = r0
            float r0 = r15.m
            r19 = r0
            r0 = r38
            r1 = r39
            r2 = r34
            r25 = r4
            r4 = r24
            r10 = r26
            r14 = r16
            r15 = r22
            r16 = r29
            r0.a(r1, r2, r3, r4, r5, r6, r7, r8, r9, r10, r11, r12, r13, r14, r15, r16, r17, r18, r19, r20)
            if (r27 == 0) goto L_0x0325
            r6 = 6
            r7 = r38
            int r0 = r7.n
            r1 = 1
            if (r0 != r1) goto L_0x0314
            float r5 = r7.o
            r0 = r39
            r1 = r23
            r2 = r25
            r3 = r30
            r4 = r21
            r0.a((androidx.constraintlayout.solver.SolverVariable) r1, (androidx.constraintlayout.solver.SolverVariable) r2, (androidx.constraintlayout.solver.SolverVariable) r3, (androidx.constraintlayout.solver.SolverVariable) r4, (float) r5, (int) r6)
            goto L_0x0327
        L_0x0314:
            float r5 = r7.o
            r6 = 6
            r0 = r39
            r1 = r30
            r2 = r21
            r3 = r23
            r4 = r25
            r0.a((androidx.constraintlayout.solver.SolverVariable) r1, (androidx.constraintlayout.solver.SolverVariable) r2, (androidx.constraintlayout.solver.SolverVariable) r3, (androidx.constraintlayout.solver.SolverVariable) r4, (float) r5, (int) r6)
            goto L_0x0327
        L_0x0325:
            r7 = r38
        L_0x0327:
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r0 = r7.z
            boolean r0 = r0.i()
            if (r0 == 0) goto L_0x034f
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r0 = r7.z
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r0 = r0.g()
            androidx.constraintlayout.solver.widgets.ConstraintWidget r0 = r0.c()
            float r1 = r7.r
            r2 = 1119092736(0x42b40000, float:90.0)
            float r1 = r1 + r2
            double r1 = (double) r1
            double r1 = java.lang.Math.toRadians(r1)
            float r1 = (float) r1
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r2 = r7.z
            int r2 = r2.b()
            r3 = r39
            r3.a((androidx.constraintlayout.solver.widgets.ConstraintWidget) r7, (androidx.constraintlayout.solver.widgets.ConstraintWidget) r0, (float) r1, (int) r2)
        L_0x034f:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.solver.widgets.ConstraintWidget.a(androidx.constraintlayout.solver.e):void");
    }

    public void a(boolean z2, boolean z3, boolean z4, boolean z5) {
        if (this.n == -1) {
            if (z4 && !z5) {
                this.n = 0;
            } else if (!z4 && z5) {
                this.n = 1;
                if (this.H == -1) {
                    this.o = 1.0f / this.o;
                }
            }
        }
        if (this.n == 0 && (!this.t.i() || !this.v.i())) {
            this.n = 1;
        } else if (this.n == 1 && (!this.s.i() || !this.u.i())) {
            this.n = 0;
        }
        if (this.n == -1 && (!this.t.i() || !this.v.i() || !this.s.i() || !this.u.i())) {
            if (this.t.i() && this.v.i()) {
                this.n = 0;
            } else if (this.s.i() && this.u.i()) {
                this.o = 1.0f / this.o;
                this.n = 1;
            }
        }
        if (this.n == -1) {
            if (z2 && !z3) {
                this.n = 0;
            } else if (!z2 && z3) {
                this.o = 1.0f / this.o;
                this.n = 1;
            }
        }
        if (this.n == -1) {
            if (this.f381h > 0 && this.k == 0) {
                this.n = 0;
            } else if (this.f381h == 0 && this.k > 0) {
                this.o = 1.0f / this.o;
                this.n = 1;
            }
        }
        if (this.n == -1 && z2 && z3) {
            this.o = 1.0f / this.o;
            this.n = 1;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:153:0x0292  */
    /* JADX WARNING: Removed duplicated region for block: B:160:0x02d7  */
    /* JADX WARNING: Removed duplicated region for block: B:163:0x02e6  */
    /* JADX WARNING: Removed duplicated region for block: B:174:0x0307  */
    /* JADX WARNING: Removed duplicated region for block: B:177:0x0310  */
    /* JADX WARNING: Removed duplicated region for block: B:186:? A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:51:0x00dd  */
    /* JADX WARNING: Removed duplicated region for block: B:61:0x0107  */
    /* JADX WARNING: Removed duplicated region for block: B:92:0x01cd A[ADDED_TO_REGION] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void a(androidx.constraintlayout.solver.e r24, boolean r25, androidx.constraintlayout.solver.SolverVariable r26, androidx.constraintlayout.solver.SolverVariable r27, androidx.constraintlayout.solver.widgets.ConstraintWidget.DimensionBehaviour r28, boolean r29, androidx.constraintlayout.solver.widgets.ConstraintAnchor r30, androidx.constraintlayout.solver.widgets.ConstraintAnchor r31, int r32, int r33, int r34, int r35, float r36, boolean r37, boolean r38, int r39, int r40, int r41, float r42, boolean r43) {
        /*
            r23 = this;
            r0 = r23
            r10 = r24
            r11 = r26
            r12 = r27
            r13 = r30
            r14 = r31
            r1 = r34
            r2 = r35
            androidx.constraintlayout.solver.SolverVariable r15 = r10.a((java.lang.Object) r13)
            androidx.constraintlayout.solver.SolverVariable r9 = r10.a((java.lang.Object) r14)
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r3 = r30.g()
            androidx.constraintlayout.solver.SolverVariable r8 = r10.a((java.lang.Object) r3)
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r3 = r31.g()
            androidx.constraintlayout.solver.SolverVariable r7 = r10.a((java.lang.Object) r3)
            boolean r3 = r10.f366g
            r6 = 1
            r4 = 6
            r5 = 0
            if (r3 == 0) goto L_0x0066
            androidx.constraintlayout.solver.widgets.k r3 = r30.d()
            int r3 = r3.b
            if (r3 != r6) goto L_0x0066
            androidx.constraintlayout.solver.widgets.k r3 = r31.d()
            int r3 = r3.b
            if (r3 != r6) goto L_0x0066
            androidx.constraintlayout.solver.f r1 = androidx.constraintlayout.solver.e.h()
            if (r1 == 0) goto L_0x0050
            androidx.constraintlayout.solver.f r1 = androidx.constraintlayout.solver.e.h()
            long r2 = r1.r
            r6 = 1
            long r2 = r2 + r6
            r1.r = r2
        L_0x0050:
            androidx.constraintlayout.solver.widgets.k r1 = r30.d()
            r1.a((androidx.constraintlayout.solver.e) r10)
            androidx.constraintlayout.solver.widgets.k r1 = r31.d()
            r1.a((androidx.constraintlayout.solver.e) r10)
            if (r38 != 0) goto L_0x0065
            if (r25 == 0) goto L_0x0065
            r10.b(r12, r9, r5, r4)
        L_0x0065:
            return
        L_0x0066:
            androidx.constraintlayout.solver.f r3 = androidx.constraintlayout.solver.e.h()
            if (r3 == 0) goto L_0x0078
            androidx.constraintlayout.solver.f r3 = androidx.constraintlayout.solver.e.h()
            long r4 = r3.z
            r16 = 1
            long r4 = r4 + r16
            r3.z = r4
        L_0x0078:
            boolean r16 = r30.i()
            boolean r17 = r31.i()
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r3 = r0.z
            boolean r19 = r3.i()
            if (r16 == 0) goto L_0x008a
            r3 = 1
            goto L_0x008b
        L_0x008a:
            r3 = 0
        L_0x008b:
            if (r17 == 0) goto L_0x008f
            int r3 = r3 + 1
        L_0x008f:
            if (r19 == 0) goto L_0x0093
            int r3 = r3 + 1
        L_0x0093:
            r5 = r3
            if (r37 == 0) goto L_0x0098
            r3 = 3
            goto L_0x009a
        L_0x0098:
            r3 = r39
        L_0x009a:
            int[] r20 = androidx.constraintlayout.solver.widgets.ConstraintWidget.a.b
            int r21 = r28.ordinal()
            r4 = r20[r21]
            r14 = 2
            r13 = 4
            if (r4 == r6) goto L_0x00ad
            if (r4 == r14) goto L_0x00ad
            r14 = 3
            if (r4 == r14) goto L_0x00ad
            if (r4 == r13) goto L_0x00af
        L_0x00ad:
            r4 = 0
            goto L_0x00b3
        L_0x00af:
            if (r3 != r13) goto L_0x00b2
            goto L_0x00ad
        L_0x00b2:
            r4 = 1
        L_0x00b3:
            int r14 = r0.Y
            r13 = 8
            if (r14 != r13) goto L_0x00bc
            r4 = 0
            r13 = 0
            goto L_0x00bf
        L_0x00bc:
            r13 = r4
            r4 = r33
        L_0x00bf:
            if (r43 == 0) goto L_0x00da
            if (r16 != 0) goto L_0x00cd
            if (r17 != 0) goto L_0x00cd
            if (r19 != 0) goto L_0x00cd
            r14 = r32
            r10.a((androidx.constraintlayout.solver.SolverVariable) r15, (int) r14)
            goto L_0x00da
        L_0x00cd:
            if (r16 == 0) goto L_0x00da
            if (r17 != 0) goto L_0x00da
            int r14 = r30.b()
            r6 = 6
            r10.a((androidx.constraintlayout.solver.SolverVariable) r15, (androidx.constraintlayout.solver.SolverVariable) r8, (int) r14, (int) r6)
            goto L_0x00db
        L_0x00da:
            r6 = 6
        L_0x00db:
            if (r13 != 0) goto L_0x0107
            if (r29 == 0) goto L_0x00f4
            r6 = 0
            r14 = 3
            r10.a((androidx.constraintlayout.solver.SolverVariable) r9, (androidx.constraintlayout.solver.SolverVariable) r15, (int) r6, (int) r14)
            r4 = 6
            if (r1 <= 0) goto L_0x00ea
            r10.b(r9, r15, r1, r4)
        L_0x00ea:
            r6 = 2147483647(0x7fffffff, float:NaN)
            if (r2 >= r6) goto L_0x00f2
            r10.c(r9, r15, r2, r4)
        L_0x00f2:
            r6 = 6
            goto L_0x00f8
        L_0x00f4:
            r14 = 3
            r10.a((androidx.constraintlayout.solver.SolverVariable) r9, (androidx.constraintlayout.solver.SolverVariable) r15, (int) r4, (int) r6)
        L_0x00f8:
            r14 = r40
            r32 = r3
            r0 = r5
            r1 = r7
            r22 = r8
            r21 = r13
            r2 = 2
            r13 = r41
            goto L_0x01e4
        L_0x0107:
            r14 = 3
            r2 = -2
            r14 = r40
            r6 = r41
            if (r14 != r2) goto L_0x0110
            r14 = r4
        L_0x0110:
            if (r6 != r2) goto L_0x0113
            r6 = r4
        L_0x0113:
            r2 = 6
            if (r14 <= 0) goto L_0x011d
            r10.b(r9, r15, r14, r2)
            int r4 = java.lang.Math.max(r4, r14)
        L_0x011d:
            if (r6 <= 0) goto L_0x0126
            r10.c(r9, r15, r6, r2)
            int r4 = java.lang.Math.min(r4, r6)
        L_0x0126:
            r2 = 1
            if (r3 != r2) goto L_0x014f
            if (r25 == 0) goto L_0x013b
            r2 = 6
            r10.a((androidx.constraintlayout.solver.SolverVariable) r9, (androidx.constraintlayout.solver.SolverVariable) r15, (int) r4, (int) r2)
            r32 = r3
            r0 = r5
            r1 = r7
            r22 = r8
            r33 = r13
            r8 = r4
            r13 = r6
            goto L_0x01c8
        L_0x013b:
            r2 = 6
            if (r38 == 0) goto L_0x0146
            r33 = r13
            r13 = 4
            r10.a((androidx.constraintlayout.solver.SolverVariable) r9, (androidx.constraintlayout.solver.SolverVariable) r15, (int) r4, (int) r13)
            goto L_0x01c0
        L_0x0146:
            r33 = r13
            r2 = 1
            r13 = 4
            r10.a((androidx.constraintlayout.solver.SolverVariable) r9, (androidx.constraintlayout.solver.SolverVariable) r15, (int) r4, (int) r2)
            goto L_0x01c0
        L_0x014f:
            r33 = r13
            r2 = 2
            r13 = 4
            if (r3 != r2) goto L_0x01c0
            androidx.constraintlayout.solver.widgets.ConstraintAnchor$Type r2 = r30.h()
            androidx.constraintlayout.solver.widgets.ConstraintAnchor$Type r13 = androidx.constraintlayout.solver.widgets.ConstraintAnchor.Type.TOP
            if (r2 == r13) goto L_0x0181
            androidx.constraintlayout.solver.widgets.ConstraintAnchor$Type r2 = r30.h()
            androidx.constraintlayout.solver.widgets.ConstraintAnchor$Type r13 = androidx.constraintlayout.solver.widgets.ConstraintAnchor.Type.BOTTOM
            if (r2 != r13) goto L_0x0166
            goto L_0x0181
        L_0x0166:
            androidx.constraintlayout.solver.widgets.ConstraintWidget r2 = r0.D
            androidx.constraintlayout.solver.widgets.ConstraintAnchor$Type r13 = androidx.constraintlayout.solver.widgets.ConstraintAnchor.Type.LEFT
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r2 = r2.a((androidx.constraintlayout.solver.widgets.ConstraintAnchor.Type) r13)
            androidx.constraintlayout.solver.SolverVariable r2 = r10.a((java.lang.Object) r2)
            androidx.constraintlayout.solver.widgets.ConstraintWidget r13 = r0.D
            r29 = r2
            androidx.constraintlayout.solver.widgets.ConstraintAnchor$Type r2 = androidx.constraintlayout.solver.widgets.ConstraintAnchor.Type.RIGHT
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r2 = r13.a((androidx.constraintlayout.solver.widgets.ConstraintAnchor.Type) r2)
            androidx.constraintlayout.solver.SolverVariable r2 = r10.a((java.lang.Object) r2)
            goto L_0x019b
        L_0x0181:
            androidx.constraintlayout.solver.widgets.ConstraintWidget r2 = r0.D
            androidx.constraintlayout.solver.widgets.ConstraintAnchor$Type r13 = androidx.constraintlayout.solver.widgets.ConstraintAnchor.Type.TOP
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r2 = r2.a((androidx.constraintlayout.solver.widgets.ConstraintAnchor.Type) r13)
            androidx.constraintlayout.solver.SolverVariable r2 = r10.a((java.lang.Object) r2)
            androidx.constraintlayout.solver.widgets.ConstraintWidget r13 = r0.D
            r29 = r2
            androidx.constraintlayout.solver.widgets.ConstraintAnchor$Type r2 = androidx.constraintlayout.solver.widgets.ConstraintAnchor.Type.BOTTOM
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r2 = r13.a((androidx.constraintlayout.solver.widgets.ConstraintAnchor.Type) r2)
            androidx.constraintlayout.solver.SolverVariable r2 = r10.a((java.lang.Object) r2)
        L_0x019b:
            r21 = r29
            r13 = r2
            androidx.constraintlayout.solver.b r2 = r24.b()
            r29 = r2
            r18 = 1
            r20 = 6
            r0 = r3
            r3 = r9
            r22 = r8
            r8 = r4
            r4 = r15
            r32 = r0
            r0 = r5
            r5 = r13
            r13 = r6
            r6 = r21
            r1 = r7
            r7 = r42
            r2.a(r3, r4, r5, r6, r7)
            r10.a((androidx.constraintlayout.solver.b) r2)
            r5 = 0
            goto L_0x01ca
        L_0x01c0:
            r32 = r3
            r0 = r5
            r13 = r6
            r1 = r7
            r22 = r8
            r8 = r4
        L_0x01c8:
            r5 = r33
        L_0x01ca:
            r2 = 2
            if (r5 == 0) goto L_0x01e2
            if (r0 == r2) goto L_0x01e2
            if (r37 != 0) goto L_0x01e2
            int r3 = java.lang.Math.max(r14, r8)
            if (r13 <= 0) goto L_0x01db
            int r3 = java.lang.Math.min(r13, r3)
        L_0x01db:
            r4 = 6
            r10.a((androidx.constraintlayout.solver.SolverVariable) r9, (androidx.constraintlayout.solver.SolverVariable) r15, (int) r3, (int) r4)
            r21 = 0
            goto L_0x01e4
        L_0x01e2:
            r21 = r5
        L_0x01e4:
            if (r43 == 0) goto L_0x0317
            if (r38 == 0) goto L_0x01ea
            goto L_0x0317
        L_0x01ea:
            r0 = 5
            if (r16 != 0) goto L_0x01f9
            if (r17 != 0) goto L_0x01f9
            if (r19 != 0) goto L_0x01f9
            if (r25 == 0) goto L_0x030b
            r2 = 0
            r10.b(r12, r9, r2, r0)
            goto L_0x030b
        L_0x01f9:
            r2 = 0
            if (r16 == 0) goto L_0x0205
            if (r17 != 0) goto L_0x0205
            if (r25 == 0) goto L_0x030b
            r10.b(r12, r9, r2, r0)
            goto L_0x030b
        L_0x0205:
            if (r16 != 0) goto L_0x0219
            if (r17 == 0) goto L_0x0219
            int r3 = r31.b()
            int r3 = -r3
            r4 = 6
            r10.a((androidx.constraintlayout.solver.SolverVariable) r9, (androidx.constraintlayout.solver.SolverVariable) r1, (int) r3, (int) r4)
            if (r25 == 0) goto L_0x030b
            r10.b(r15, r11, r2, r0)
            goto L_0x030b
        L_0x0219:
            if (r16 == 0) goto L_0x030b
            if (r17 == 0) goto L_0x030b
            if (r21 == 0) goto L_0x0285
            r8 = r1
            r7 = 6
            if (r25 == 0) goto L_0x0228
            if (r34 != 0) goto L_0x0228
            r10.b(r9, r15, r2, r7)
        L_0x0228:
            if (r32 != 0) goto L_0x0252
            if (r13 > 0) goto L_0x0232
            if (r14 <= 0) goto L_0x022f
            goto L_0x0232
        L_0x022f:
            r4 = 6
            r6 = 0
            goto L_0x0234
        L_0x0232:
            r4 = 4
            r6 = 1
        L_0x0234:
            int r1 = r30.b()
            r5 = r22
            r10.a((androidx.constraintlayout.solver.SolverVariable) r15, (androidx.constraintlayout.solver.SolverVariable) r5, (int) r1, (int) r4)
            int r1 = r31.b()
            int r1 = -r1
            r10.a((androidx.constraintlayout.solver.SolverVariable) r9, (androidx.constraintlayout.solver.SolverVariable) r8, (int) r1, (int) r4)
            if (r13 > 0) goto L_0x024c
            if (r14 <= 0) goto L_0x024a
            goto L_0x024c
        L_0x024a:
            r1 = 0
            goto L_0x024d
        L_0x024c:
            r1 = 1
        L_0x024d:
            r13 = r6
            r14 = 1
            r16 = 5
            goto L_0x025d
        L_0x0252:
            r4 = r32
            r5 = r22
            r14 = 1
            if (r4 != r14) goto L_0x0260
            r1 = 1
            r13 = 1
            r16 = 6
        L_0x025d:
            r6 = r23
            goto L_0x0290
        L_0x0260:
            r1 = 3
            r6 = r23
            if (r4 != r1) goto L_0x0283
            if (r37 != 0) goto L_0x0270
            int r1 = r6.n
            r2 = -1
            if (r1 == r2) goto L_0x0270
            if (r13 > 0) goto L_0x0270
            r4 = 6
            goto L_0x0271
        L_0x0270:
            r4 = 4
        L_0x0271:
            int r1 = r30.b()
            r10.a((androidx.constraintlayout.solver.SolverVariable) r15, (androidx.constraintlayout.solver.SolverVariable) r5, (int) r1, (int) r4)
            int r1 = r31.b()
            int r1 = -r1
            r10.a((androidx.constraintlayout.solver.SolverVariable) r9, (androidx.constraintlayout.solver.SolverVariable) r8, (int) r1, (int) r4)
            r1 = 1
            r13 = 1
            goto L_0x028e
        L_0x0283:
            r1 = 0
            goto L_0x028d
        L_0x0285:
            r8 = r1
            r5 = r22
            r7 = 6
            r14 = 1
            r6 = r23
            r1 = 1
        L_0x028d:
            r13 = 0
        L_0x028e:
            r16 = 5
        L_0x0290:
            if (r1 == 0) goto L_0x02d7
            int r4 = r30.b()
            int r17 = r31.b()
            r1 = r24
            r2 = r15
            r3 = r5
            r18 = r5
            r5 = r36
            r6 = r8
            r19 = 6
            r7 = r9
            r14 = r8
            r0 = r18
            r12 = 6
            r8 = r17
            r12 = r9
            r9 = r16
            r1.a(r2, r3, r4, r5, r6, r7, r8, r9)
            r1 = r30
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r2 = r1.d
            androidx.constraintlayout.solver.widgets.ConstraintWidget r2 = r2.b
            boolean r2 = r2 instanceof androidx.constraintlayout.solver.widgets.b
            r3 = r31
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r4 = r3.d
            androidx.constraintlayout.solver.widgets.ConstraintWidget r4 = r4.b
            boolean r4 = r4 instanceof androidx.constraintlayout.solver.widgets.b
            if (r2 == 0) goto L_0x02cd
            if (r4 != 0) goto L_0x02cd
            r6 = r25
            r2 = 6
            r4 = 5
            r18 = 1
            goto L_0x02e4
        L_0x02cd:
            if (r2 != 0) goto L_0x02de
            if (r4 == 0) goto L_0x02de
            r18 = r25
            r2 = 5
            r4 = 6
            r6 = 1
            goto L_0x02e4
        L_0x02d7:
            r1 = r30
            r3 = r31
            r0 = r5
            r14 = r8
            r12 = r9
        L_0x02de:
            r6 = r25
            r18 = r6
            r2 = 5
            r4 = 5
        L_0x02e4:
            if (r13 == 0) goto L_0x02e8
            r2 = 6
            r4 = 6
        L_0x02e8:
            if (r21 != 0) goto L_0x02ec
            if (r6 != 0) goto L_0x02ee
        L_0x02ec:
            if (r13 == 0) goto L_0x02f5
        L_0x02ee:
            int r1 = r30.b()
            r10.b(r15, r0, r1, r4)
        L_0x02f5:
            if (r21 != 0) goto L_0x02f9
            if (r18 != 0) goto L_0x02fb
        L_0x02f9:
            if (r13 == 0) goto L_0x0303
        L_0x02fb:
            int r0 = r31.b()
            int r0 = -r0
            r10.c(r12, r14, r0, r2)
        L_0x0303:
            r0 = 6
            r1 = 0
            if (r25 == 0) goto L_0x030e
            r10.b(r15, r11, r1, r0)
            goto L_0x030e
        L_0x030b:
            r12 = r9
            r0 = 6
            r1 = 0
        L_0x030e:
            if (r25 == 0) goto L_0x0316
            r2 = r27
            r3 = 6
            r10.b(r2, r12, r1, r3)
        L_0x0316:
            return
        L_0x0317:
            r2 = r12
            r1 = 0
            r3 = 6
            r4 = 2
            r12 = r9
            if (r0 >= r4) goto L_0x0326
            if (r25 == 0) goto L_0x0326
            r10.b(r15, r11, r1, r3)
            r10.b(r2, r12, r1, r3)
        L_0x0326:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.solver.widgets.ConstraintWidget.a(androidx.constraintlayout.solver.e, boolean, androidx.constraintlayout.solver.SolverVariable, androidx.constraintlayout.solver.SolverVariable, androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour, boolean, androidx.constraintlayout.solver.widgets.ConstraintAnchor, androidx.constraintlayout.solver.widgets.ConstraintAnchor, int, int, int, int, float, boolean, boolean, int, int, int, float, boolean):void");
    }
}
