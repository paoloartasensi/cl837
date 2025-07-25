package kotlinx.coroutines.j2;

import kotlin.Result;
import kotlin.coroutines.c;
import kotlin.jvm.b.l;
import kotlin.jvm.b.p;
import kotlin.jvm.internal.i;
import kotlinx.coroutines.s0;

/* compiled from: Cancellable.kt */
public final class a {
    public static final <T> void a(l<? super c<? super T>, ? extends Object> lVar, c<? super T> cVar) {
        i.b(lVar, "$this$startCoroutineCancellable");
        i.b(cVar, "completion");
        try {
            s0.a(IntrinsicsKt__IntrinsicsJvmKt.a(IntrinsicsKt__IntrinsicsJvmKt.a(lVar, cVar)), kotlin.l.a);
        } catch (Throwable th) {
            Result.a aVar = Result.Companion;
            cVar.resumeWith(Result.m1constructorimpl(kotlin.i.a(th)));
        }
    }

    public static final <R, T> void a(p<? super R, ? super c<? super T>, ? extends Object> pVar, R r, c<? super T> cVar) {
        i.b(pVar, "$this$startCoroutineCancellable");
        i.b(cVar, "completion");
        try {
            s0.a(IntrinsicsKt__IntrinsicsJvmKt.a(IntrinsicsKt__IntrinsicsJvmKt.a(pVar, r, cVar)), kotlin.l.a);
        } catch (Throwable th) {
            Result.a aVar = Result.Companion;
            cVar.resumeWith(Result.m1constructorimpl(kotlin.i.a(th)));
        }
    }
}
