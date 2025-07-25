package kotlinx.coroutines.internal;

import kotlin.TypeCastException;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.b.p;
import kotlin.jvm.internal.i;
import kotlinx.coroutines.c2;

/* compiled from: ThreadContext.kt */
public final class ThreadContextKt {
    private static final t a = new t("ZERO");
    private static final p<Object, CoroutineContext.a, Object> b = ThreadContextKt$countAll$1.INSTANCE;
    private static final p<c2<?>, CoroutineContext.a, c2<?>> c = ThreadContextKt$findOne$1.INSTANCE;
    private static final p<z, CoroutineContext.a, z> d = ThreadContextKt$updateState$1.INSTANCE;
    private static final p<z, CoroutineContext.a, z> e = ThreadContextKt$restoreState$1.INSTANCE;

    public static final Object a(CoroutineContext coroutineContext) {
        i.b(coroutineContext, "context");
        Object fold = coroutineContext.fold(0, b);
        if (fold != null) {
            return fold;
        }
        i.a();
        throw null;
    }

    public static final Object b(CoroutineContext coroutineContext, Object obj) {
        i.b(coroutineContext, "context");
        if (obj == null) {
            obj = a(coroutineContext);
        }
        if (obj == 0) {
            return a;
        }
        if (obj instanceof Integer) {
            return coroutineContext.fold(new z(coroutineContext, ((Number) obj).intValue()), d);
        }
        if (obj != null) {
            return ((c2) obj).a(coroutineContext);
        }
        throw new TypeCastException("null cannot be cast to non-null type kotlinx.coroutines.ThreadContextElement<kotlin.Any?>");
    }

    public static final void a(CoroutineContext coroutineContext, Object obj) {
        i.b(coroutineContext, "context");
        if (obj != a) {
            if (obj instanceof z) {
                ((z) obj).b();
                coroutineContext.fold(obj, e);
                return;
            }
            Object fold = coroutineContext.fold(null, c);
            if (fold != null) {
                ((c2) fold).a(coroutineContext, obj);
                return;
            }
            throw new TypeCastException("null cannot be cast to non-null type kotlinx.coroutines.ThreadContextElement<kotlin.Any?>");
        }
    }
}
