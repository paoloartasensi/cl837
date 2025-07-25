package kotlinx.coroutines;

import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.coroutines.c;
import kotlin.coroutines.d;
import kotlin.jvm.b.p;
import kotlin.jvm.internal.i;
import kotlin.l;
import kotlinx.coroutines.internal.ThreadContextKt;
import kotlinx.coroutines.internal.q;
import kotlinx.coroutines.j2.a;
import kotlinx.coroutines.j2.b;

/* compiled from: Builders.common.kt */
final /* synthetic */ class e {
    public static /* synthetic */ k1 a(g0 g0Var, CoroutineContext coroutineContext, CoroutineStart coroutineStart, p pVar, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            coroutineContext = EmptyCoroutineContext.INSTANCE;
        }
        if ((i2 & 2) != 0) {
            coroutineStart = CoroutineStart.DEFAULT;
        }
        return d.a(g0Var, coroutineContext, coroutineStart, pVar);
    }

    public static final k1 a(g0 g0Var, CoroutineContext coroutineContext, CoroutineStart coroutineStart, p<? super g0, ? super c<? super l>, ? extends Object> pVar) {
        a aVar;
        i.b(g0Var, "$this$launch");
        i.b(coroutineContext, "context");
        i.b(coroutineStart, "start");
        i.b(pVar, "block");
        CoroutineContext a = a0.a(g0Var, coroutineContext);
        if (coroutineStart.isLazy()) {
            aVar = new s1(a, pVar);
        } else {
            aVar = new z1(a, true);
        }
        aVar.a(coroutineStart, aVar, pVar);
        return aVar;
    }

    /* JADX INFO: finally extract failed */
    public static final <T> Object a(CoroutineContext coroutineContext, p<? super g0, ? super c<? super T>, ? extends Object> pVar, c<? super T> cVar) {
        Object obj;
        CoroutineContext context = cVar.getContext();
        CoroutineContext plus = context.plus(coroutineContext);
        i2.a(plus);
        if (plus == context) {
            q qVar = new q(plus, cVar);
            obj = b.a(qVar, qVar, pVar);
        } else if (i.a((Object) (d) plus.get(d.b), (Object) (d) context.get(d.b))) {
            h2 h2Var = new h2(plus, cVar);
            Object b = ThreadContextKt.b(plus, (Object) null);
            try {
                Object a = b.a(h2Var, h2Var, pVar);
                ThreadContextKt.a(plus, b);
                obj = a;
            } catch (Throwable th) {
                ThreadContextKt.a(plus, b);
                throw th;
            }
        } else {
            r0 r0Var = new r0(plus, cVar);
            r0Var.l();
            a.a(pVar, r0Var, r0Var);
            obj = r0Var.n();
        }
        if (obj == b.a()) {
            kotlin.coroutines.jvm.internal.e.c(cVar);
        }
        return obj;
    }
}
