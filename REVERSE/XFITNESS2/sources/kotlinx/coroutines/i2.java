package kotlinx.coroutines;

import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.internal.i;

/* compiled from: Yield.kt */
public final class i2 {
    public static final void a(CoroutineContext coroutineContext) {
        i.b(coroutineContext, "$this$checkCompletion");
        k1 k1Var = (k1) coroutineContext.get(k1.d);
        if (k1Var != null && !k1Var.isActive()) {
            throw k1Var.c();
        }
    }
}
