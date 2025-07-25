package kotlin.coroutines;

import kotlin.TypeCastException;
import kotlin.jvm.b.p;
import kotlin.jvm.internal.i;

/* compiled from: CoroutineContext.kt */
public interface CoroutineContext {

    /* compiled from: CoroutineContext.kt */
    public static final class DefaultImpls {
        public static CoroutineContext a(CoroutineContext coroutineContext, CoroutineContext coroutineContext2) {
            i.b(coroutineContext2, "context");
            return coroutineContext2 == EmptyCoroutineContext.INSTANCE ? coroutineContext : (CoroutineContext) coroutineContext2.fold(coroutineContext, CoroutineContext$plus$1.INSTANCE);
        }
    }

    /* compiled from: CoroutineContext.kt */
    public interface a extends CoroutineContext {

        /* renamed from: kotlin.coroutines.CoroutineContext$a$a  reason: collision with other inner class name */
        /* compiled from: CoroutineContext.kt */
        public static final class C0089a {
            public static <E extends a> E a(a aVar, b<E> bVar) {
                i.b(bVar, "key");
                if (!i.a((Object) aVar.getKey(), (Object) bVar)) {
                    return null;
                }
                if (aVar != null) {
                    return aVar;
                }
                throw new TypeCastException("null cannot be cast to non-null type E");
            }

            public static CoroutineContext a(a aVar, CoroutineContext coroutineContext) {
                i.b(coroutineContext, "context");
                return DefaultImpls.a(aVar, coroutineContext);
            }

            public static CoroutineContext b(a aVar, b<?> bVar) {
                i.b(bVar, "key");
                return i.a((Object) aVar.getKey(), (Object) bVar) ? EmptyCoroutineContext.INSTANCE : aVar;
            }

            public static <R> R a(a aVar, R r, p<? super R, ? super a, ? extends R> pVar) {
                i.b(pVar, "operation");
                return pVar.invoke(r, aVar);
            }
        }

        <E extends a> E get(b<E> bVar);

        b<?> getKey();
    }

    /* compiled from: CoroutineContext.kt */
    public interface b<E extends a> {
    }

    <R> R fold(R r, p<? super R, ? super a, ? extends R> pVar);

    <E extends a> E get(b<E> bVar);

    CoroutineContext minusKey(b<?> bVar);

    CoroutineContext plus(CoroutineContext coroutineContext);
}
