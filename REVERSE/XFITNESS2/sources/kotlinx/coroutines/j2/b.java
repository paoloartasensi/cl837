package kotlinx.coroutines.j2;

import kotlin.Result;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.c;
import kotlin.coroutines.jvm.internal.e;
import kotlin.jvm.b.l;
import kotlin.jvm.b.p;
import kotlin.jvm.internal.i;
import kotlin.jvm.internal.n;
import kotlinx.coroutines.a;
import kotlinx.coroutines.internal.ThreadContextKt;
import kotlinx.coroutines.internal.r;
import kotlinx.coroutines.r1;
import kotlinx.coroutines.u;

/* compiled from: Undispatched.kt */
public final class b {
    public static final <T, R> Object a(a<? super T> aVar, R r, p<? super R, ? super c<? super T>, ? extends Object> pVar) {
        Object obj;
        i.b(aVar, "$this$startUndispatchedOrReturn");
        i.b(pVar, "block");
        aVar.l();
        try {
            n.a((Object) pVar, 2);
            obj = pVar.invoke(r, aVar);
        } catch (Throwable th) {
            obj = new u(th, false, 2, (f) null);
        }
        if (obj == b.a()) {
            return b.a();
        }
        if (!aVar.b(obj, 4)) {
            return b.a();
        }
        Object e = aVar.e();
        if (!(e instanceof u)) {
            return r1.b(e);
        }
        throw r.a(aVar, ((u) e).a);
    }

    public static final <T> void a(l<? super c<? super T>, ? extends Object> lVar, c<? super T> cVar) {
        CoroutineContext context;
        Object b;
        i.b(lVar, "$this$startCoroutineUndispatched");
        i.b(cVar, "completion");
        e.a(cVar);
        try {
            context = cVar.getContext();
            b = ThreadContextKt.b(context, (Object) null);
            n.a((Object) lVar, 1);
            Object invoke = lVar.invoke(cVar);
            ThreadContextKt.a(context, b);
            if (invoke != b.a()) {
                Result.a aVar = Result.Companion;
                cVar.resumeWith(Result.m1constructorimpl(invoke));
            }
        } catch (Throwable th) {
            Result.a aVar2 = Result.Companion;
            cVar.resumeWith(Result.m1constructorimpl(kotlin.i.a(th)));
        }
    }

    public static final <R, T> void a(p<? super R, ? super c<? super T>, ? extends Object> pVar, R r, c<? super T> cVar) {
        CoroutineContext context;
        Object b;
        i.b(pVar, "$this$startCoroutineUndispatched");
        i.b(cVar, "completion");
        e.a(cVar);
        try {
            context = cVar.getContext();
            b = ThreadContextKt.b(context, (Object) null);
            n.a((Object) pVar, 2);
            Object invoke = pVar.invoke(r, cVar);
            ThreadContextKt.a(context, b);
            if (invoke != b.a()) {
                Result.a aVar = Result.Companion;
                cVar.resumeWith(Result.m1constructorimpl(invoke));
            }
        } catch (Throwable th) {
            Result.a aVar2 = Result.Companion;
            cVar.resumeWith(Result.m1constructorimpl(kotlin.i.a(th)));
        }
    }
}
