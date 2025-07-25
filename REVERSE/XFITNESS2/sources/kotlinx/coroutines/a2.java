package kotlinx.coroutines;

import kotlin.jvm.internal.i;

/* compiled from: Supervisor.kt */
final class a2 extends m1 {
    public a2(k1 k1Var) {
        super(k1Var);
    }

    public boolean c(Throwable th) {
        i.b(th, "cause");
        return false;
    }
}
