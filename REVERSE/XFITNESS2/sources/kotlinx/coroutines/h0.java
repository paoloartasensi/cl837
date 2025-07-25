package kotlinx.coroutines;

import java.util.concurrent.CancellationException;
import kotlin.jvm.internal.i;
import kotlinx.coroutines.internal.e;

/* compiled from: CoroutineScope.kt */
public final class h0 {
    public static final g0 a() {
        return new e(b2.a((k1) null, 1, (Object) null).plus(u0.b()));
    }

    public static /* synthetic */ void a(g0 g0Var, CancellationException cancellationException, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            cancellationException = null;
        }
        a(g0Var, cancellationException);
    }

    public static final void a(g0 g0Var, CancellationException cancellationException) {
        i.b(g0Var, "$this$cancel");
        k1 k1Var = (k1) g0Var.getCoroutineContext().get(k1.d);
        if (k1Var != null) {
            k1Var.a(cancellationException);
            return;
        }
        throw new IllegalStateException(("Scope cannot be cancelled because it does not have a job: " + g0Var).toString());
    }
}
