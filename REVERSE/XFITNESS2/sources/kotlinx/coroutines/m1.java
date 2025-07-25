package kotlinx.coroutines;

/* compiled from: JobSupport.kt */
public class m1 extends q1 implements t {

    /* renamed from: f  reason: collision with root package name */
    private final boolean f1808f = k();

    public m1(k1 k1Var) {
        super(true);
        a(k1Var);
    }

    private final boolean k() {
        q1 q1Var;
        m mVar = this.parentHandle;
        if (!(mVar instanceof n)) {
            mVar = null;
        }
        n nVar = (n) mVar;
        if (!(nVar == null || (q1Var = (q1) nVar.f1812h) == null)) {
            while (!q1Var.b()) {
                m mVar2 = q1Var.parentHandle;
                if (!(mVar2 instanceof n)) {
                    mVar2 = null;
                }
                n nVar2 = (n) mVar2;
                if (nVar2 != null) {
                    q1Var = (q1) nVar2.f1812h;
                    if (q1Var == null) {
                    }
                }
            }
            return true;
        }
        return false;
    }

    public boolean b() {
        return this.f1808f;
    }

    public boolean d() {
        return true;
    }
}
