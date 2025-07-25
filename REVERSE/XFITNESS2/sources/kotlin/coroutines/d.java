package kotlin.coroutines;

import kotlin.TypeCastException;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.internal.i;

/* compiled from: ContinuationInterceptor.kt */
public interface d extends CoroutineContext.a {
    public static final b b = b.a;

    /* compiled from: ContinuationInterceptor.kt */
    public static final class a {
        public static <E extends CoroutineContext.a> E a(d dVar, CoroutineContext.b<E> bVar) {
            i.b(bVar, "key");
            if (bVar instanceof b) {
                b bVar2 = (b) bVar;
                if (!bVar2.a(dVar.getKey())) {
                    return null;
                }
                E a = bVar2.a((CoroutineContext.a) dVar);
                if (!(a instanceof CoroutineContext.a)) {
                    return null;
                }
                return a;
            } else if (d.b != bVar) {
                return null;
            } else {
                if (dVar != null) {
                    return dVar;
                }
                throw new TypeCastException("null cannot be cast to non-null type E");
            }
        }

        public static void a(d dVar, c<?> cVar) {
            i.b(cVar, "continuation");
        }

        public static CoroutineContext b(d dVar, CoroutineContext.b<?> bVar) {
            i.b(bVar, "key");
            if (!(bVar instanceof b)) {
                return d.b == bVar ? EmptyCoroutineContext.INSTANCE : dVar;
            }
            b bVar2 = (b) bVar;
            return (!bVar2.a(dVar.getKey()) || bVar2.a((CoroutineContext.a) dVar) == null) ? dVar : EmptyCoroutineContext.INSTANCE;
        }
    }

    /* compiled from: ContinuationInterceptor.kt */
    public static final class b implements CoroutineContext.b<d> {
        static final /* synthetic */ b a = new b();

        private b() {
        }
    }

    <T> c<T> interceptContinuation(c<? super T> cVar);

    void releaseInterceptedContinuation(c<?> cVar);
}
