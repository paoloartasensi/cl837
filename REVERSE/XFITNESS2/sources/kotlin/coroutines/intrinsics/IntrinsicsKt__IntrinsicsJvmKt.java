package kotlin.coroutines.intrinsics;

import kotlin.TypeCastException;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.coroutines.c;
import kotlin.coroutines.jvm.internal.BaseContinuationImpl;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.e;
import kotlin.jvm.b.p;
import kotlin.jvm.internal.i;
import kotlin.l;

/* compiled from: IntrinsicsJvm.kt */
class IntrinsicsKt__IntrinsicsJvmKt {
    public static <T> c<l> a(kotlin.jvm.b.l<? super c<? super T>, ? extends Object> lVar, c<? super T> cVar) {
        i.b(lVar, "$this$createCoroutineUnintercepted");
        i.b(cVar, "completion");
        e.a(cVar);
        if (lVar instanceof BaseContinuationImpl) {
            return ((BaseContinuationImpl) lVar).create(cVar);
        }
        CoroutineContext context = cVar.getContext();
        if (context == EmptyCoroutineContext.INSTANCE) {
            if (cVar != null) {
                return new IntrinsicsKt__IntrinsicsJvmKt$createCoroutineUnintercepted$$inlined$createCoroutineFromSuspendFunction$IntrinsicsKt__IntrinsicsJvmKt$1(cVar, cVar, lVar);
            }
            throw new TypeCastException("null cannot be cast to non-null type kotlin.coroutines.Continuation<kotlin.Any?>");
        } else if (cVar != null) {
            return new IntrinsicsKt__IntrinsicsJvmKt$createCoroutineUnintercepted$$inlined$createCoroutineFromSuspendFunction$IntrinsicsKt__IntrinsicsJvmKt$2(cVar, context, cVar, context, lVar);
        } else {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.coroutines.Continuation<kotlin.Any?>");
        }
    }

    public static <R, T> c<l> a(p<? super R, ? super c<? super T>, ? extends Object> pVar, R r, c<? super T> cVar) {
        i.b(pVar, "$this$createCoroutineUnintercepted");
        i.b(cVar, "completion");
        e.a(cVar);
        if (pVar instanceof BaseContinuationImpl) {
            return ((BaseContinuationImpl) pVar).create(r, cVar);
        }
        CoroutineContext context = cVar.getContext();
        if (context == EmptyCoroutineContext.INSTANCE) {
            if (cVar != null) {
                return new IntrinsicsKt__IntrinsicsJvmKt$createCoroutineUnintercepted$$inlined$createCoroutineFromSuspendFunction$IntrinsicsKt__IntrinsicsJvmKt$3(cVar, cVar, pVar, r);
            }
            throw new TypeCastException("null cannot be cast to non-null type kotlin.coroutines.Continuation<kotlin.Any?>");
        } else if (cVar != null) {
            return new IntrinsicsKt__IntrinsicsJvmKt$createCoroutineUnintercepted$$inlined$createCoroutineFromSuspendFunction$IntrinsicsKt__IntrinsicsJvmKt$4(cVar, context, cVar, context, pVar, r);
        } else {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.coroutines.Continuation<kotlin.Any?>");
        }
    }

    public static <T> c<T> a(c<? super T> cVar) {
        c<Object> intercepted;
        i.b(cVar, "$this$intercepted");
        ContinuationImpl continuationImpl = (ContinuationImpl) (!(cVar instanceof ContinuationImpl) ? null : cVar);
        return (continuationImpl == null || (intercepted = continuationImpl.intercepted()) == null) ? cVar : intercepted;
    }
}
