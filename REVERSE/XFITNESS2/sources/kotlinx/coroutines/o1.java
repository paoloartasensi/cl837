package kotlinx.coroutines;

import java.util.concurrent.CancellationException;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.internal.i;

/* compiled from: Job.kt */
final /* synthetic */ class o1 {
    public static /* synthetic */ void a(CoroutineContext coroutineContext, CancellationException cancellationException, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            cancellationException = null;
        }
        n1.a(coroutineContext, cancellationException);
    }

    public static final void a(CoroutineContext coroutineContext, CancellationException cancellationException) {
        i.b(coroutineContext, "$this$cancel");
        k1 k1Var = (k1) coroutineContext.get(k1.d);
        if (k1Var != null) {
            k1Var.a(cancellationException);
        }
    }
}
