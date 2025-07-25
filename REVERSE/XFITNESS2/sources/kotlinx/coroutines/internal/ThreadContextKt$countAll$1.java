package kotlinx.coroutines.internal;

import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.b.p;
import kotlin.jvm.internal.Lambda;
import kotlin.jvm.internal.i;
import kotlinx.coroutines.c2;

/* compiled from: ThreadContext.kt */
final class ThreadContextKt$countAll$1 extends Lambda implements p<Object, CoroutineContext.a, Object> {
    public static final ThreadContextKt$countAll$1 INSTANCE = new ThreadContextKt$countAll$1();

    ThreadContextKt$countAll$1() {
        super(2);
    }

    public final Object invoke(Object obj, CoroutineContext.a aVar) {
        i.b(aVar, "element");
        if (!(aVar instanceof c2)) {
            return obj;
        }
        if (!(obj instanceof Integer)) {
            obj = null;
        }
        Integer num = (Integer) obj;
        int intValue = num != null ? num.intValue() : 1;
        return intValue == 0 ? aVar : Integer.valueOf(intValue + 1);
    }
}
