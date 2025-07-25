package kotlin.coroutines;

import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.b.p;
import kotlin.jvm.internal.Lambda;
import kotlin.jvm.internal.i;

/* compiled from: CoroutineContext.kt */
final class CoroutineContext$plus$1 extends Lambda implements p<CoroutineContext, CoroutineContext.a, CoroutineContext> {
    public static final CoroutineContext$plus$1 INSTANCE = new CoroutineContext$plus$1();

    CoroutineContext$plus$1() {
        super(2);
    }

    public final CoroutineContext invoke(CoroutineContext coroutineContext, CoroutineContext.a aVar) {
        i.b(coroutineContext, "acc");
        i.b(aVar, "element");
        CoroutineContext minusKey = coroutineContext.minusKey(aVar.getKey());
        if (minusKey == EmptyCoroutineContext.INSTANCE) {
            return aVar;
        }
        d dVar = (d) minusKey.get(d.b);
        if (dVar == null) {
            return new CombinedContext(minusKey, aVar);
        }
        CoroutineContext minusKey2 = minusKey.minusKey(d.b);
        if (minusKey2 == EmptyCoroutineContext.INSTANCE) {
            return new CombinedContext(aVar, dVar);
        }
        return new CombinedContext(new CombinedContext(minusKey2, aVar), dVar);
    }
}
