package kotlinx.coroutines;

import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.c;
import kotlin.jvm.internal.i;
import kotlinx.coroutines.internal.q;

/* compiled from: Builders.common.kt */
final class h2<T> extends q<T> {
    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public h2(CoroutineContext coroutineContext, c<? super T> cVar) {
        super(coroutineContext, cVar);
        i.b(coroutineContext, "context");
        i.b(cVar, "uCont");
    }

    public int k() {
        return 3;
    }
}
