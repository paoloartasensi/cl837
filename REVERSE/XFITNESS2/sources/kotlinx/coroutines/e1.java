package kotlinx.coroutines;

import kotlin.jvm.internal.i;

/* compiled from: JobSupport.kt */
public final class e1 implements f1 {
    private final u1 e;

    public e1(u1 u1Var) {
        i.b(u1Var, "list");
        this.e = u1Var;
    }

    public u1 b() {
        return this.e;
    }

    public boolean isActive() {
        return false;
    }

    public String toString() {
        return j0.c() ? b().a("New") : super.toString();
    }
}
