package kotlin.coroutines;

import kotlin.Result;
import kotlin.jvm.b.l;
import kotlin.jvm.b.p;
import kotlin.jvm.internal.i;

/* compiled from: Continuation.kt */
public final class e {
    public static final <T> void a(l<? super c<? super T>, ? extends Object> lVar, c<? super T> cVar) {
        i.b(lVar, "$this$startCoroutine");
        i.b(cVar, "completion");
        c<kotlin.l> a = IntrinsicsKt__IntrinsicsJvmKt.a(IntrinsicsKt__IntrinsicsJvmKt.a(lVar, cVar));
        kotlin.l lVar2 = kotlin.l.a;
        Result.a aVar = Result.Companion;
        a.resumeWith(Result.m1constructorimpl(lVar2));
    }

    public static final <R, T> void a(p<? super R, ? super c<? super T>, ? extends Object> pVar, R r, c<? super T> cVar) {
        i.b(pVar, "$this$startCoroutine");
        i.b(cVar, "completion");
        c<kotlin.l> a = IntrinsicsKt__IntrinsicsJvmKt.a(IntrinsicsKt__IntrinsicsJvmKt.a(pVar, r, cVar));
        kotlin.l lVar = kotlin.l.a;
        Result.a aVar = Result.Companion;
        a.resumeWith(Result.m1constructorimpl(lVar));
    }
}
