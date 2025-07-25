package kotlin.coroutines;

import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.b.p;
import kotlin.jvm.internal.Lambda;
import kotlin.jvm.internal.i;

/* compiled from: CoroutineContextImpl.kt */
final class CombinedContext$toString$1 extends Lambda implements p<String, CoroutineContext.a, String> {
    public static final CombinedContext$toString$1 INSTANCE = new CombinedContext$toString$1();

    CombinedContext$toString$1() {
        super(2);
    }

    public final String invoke(String str, CoroutineContext.a aVar) {
        i.b(str, "acc");
        i.b(aVar, "element");
        if (str.length() == 0) {
            return aVar.toString();
        }
        return str + ", " + aVar;
    }
}
