package kotlinx.coroutines;

import java.util.concurrent.CancellationException;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.b.l;
import kotlin.jvm.b.p;
import kotlin.jvm.internal.i;
import kotlinx.coroutines.CoroutineExceptionHandler;

/* compiled from: Job.kt */
public interface k1 extends CoroutineContext.a {
    public static final b d = b.a;

    /* compiled from: Job.kt */
    public static final class b implements CoroutineContext.b<k1> {
        static final /* synthetic */ b a = new b();

        static {
            CoroutineExceptionHandler.a aVar = CoroutineExceptionHandler.c;
        }

        private b() {
        }
    }

    m a(o oVar);

    v0 a(l<? super Throwable, kotlin.l> lVar);

    v0 a(boolean z, boolean z2, l<? super Throwable, kotlin.l> lVar);

    void a(CancellationException cancellationException);

    CancellationException c();

    boolean isActive();

    boolean isCancelled();

    boolean start();

    /* compiled from: Job.kt */
    public static final class a {
        public static <R> R a(k1 k1Var, R r, p<? super R, ? super CoroutineContext.a, ? extends R> pVar) {
            i.b(pVar, "operation");
            return CoroutineContext.a.C0089a.a(k1Var, r, pVar);
        }

        public static <E extends CoroutineContext.a> E a(k1 k1Var, CoroutineContext.b<E> bVar) {
            i.b(bVar, "key");
            return CoroutineContext.a.C0089a.a((CoroutineContext.a) k1Var, bVar);
        }

        public static CoroutineContext a(k1 k1Var, CoroutineContext coroutineContext) {
            i.b(coroutineContext, "context");
            return CoroutineContext.a.C0089a.a((CoroutineContext.a) k1Var, coroutineContext);
        }

        public static /* synthetic */ void a(k1 k1Var, CancellationException cancellationException, int i2, Object obj) {
            if (obj == null) {
                if ((i2 & 1) != 0) {
                    cancellationException = null;
                }
                k1Var.a(cancellationException);
                return;
            }
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: cancel");
        }

        public static CoroutineContext b(k1 k1Var, CoroutineContext.b<?> bVar) {
            i.b(bVar, "key");
            return CoroutineContext.a.C0089a.b(k1Var, bVar);
        }

        public static /* synthetic */ v0 a(k1 k1Var, boolean z, boolean z2, l lVar, int i2, Object obj) {
            if (obj == null) {
                if ((i2 & 1) != 0) {
                    z = false;
                }
                if ((i2 & 2) != 0) {
                    z2 = true;
                }
                return k1Var.a(z, z2, lVar);
            }
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: invokeOnCompletion");
        }
    }
}
