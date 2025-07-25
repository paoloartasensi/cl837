package kotlinx.coroutines;

import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.b.p;
import kotlin.jvm.internal.i;

/* compiled from: ThreadContextElement.kt */
public interface c2<S> extends CoroutineContext.a {

    /* compiled from: ThreadContextElement.kt */
    public static final class a {
        public static <S, R> R a(c2<S> c2Var, R r, p<? super R, ? super CoroutineContext.a, ? extends R> pVar) {
            i.b(pVar, "operation");
            return CoroutineContext.a.C0089a.a(c2Var, r, pVar);
        }

        public static <S, E extends CoroutineContext.a> E a(c2<S> c2Var, CoroutineContext.b<E> bVar) {
            i.b(bVar, "key");
            return CoroutineContext.a.C0089a.a((CoroutineContext.a) c2Var, bVar);
        }

        public static <S> CoroutineContext a(c2<S> c2Var, CoroutineContext coroutineContext) {
            i.b(coroutineContext, "context");
            return CoroutineContext.a.C0089a.a((CoroutineContext.a) c2Var, coroutineContext);
        }

        public static <S> CoroutineContext b(c2<S> c2Var, CoroutineContext.b<?> bVar) {
            i.b(bVar, "key");
            return CoroutineContext.a.C0089a.b(c2Var, bVar);
        }
    }

    S a(CoroutineContext coroutineContext);

    void a(CoroutineContext coroutineContext, S s);
}
