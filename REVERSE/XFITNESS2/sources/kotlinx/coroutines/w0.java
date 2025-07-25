package kotlinx.coroutines;

import kotlin.jvm.internal.i;
import kotlin.l;

/* compiled from: CancellableContinuation.kt */
final class w0 extends f {
    private final v0 e;

    public w0(v0 v0Var) {
        i.b(v0Var, "handle");
        this.e = v0Var;
    }

    public void a(Throwable th) {
        this.e.a();
    }

    public /* bridge */ /* synthetic */ Object invoke(Object obj) {
        a((Throwable) obj);
        return l.a;
    }

    public String toString() {
        return "DisposeOnCancel[" + this.e + ']';
    }
}
