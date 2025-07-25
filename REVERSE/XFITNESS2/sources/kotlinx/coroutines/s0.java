package kotlinx.coroutines;

import java.util.concurrent.CancellationException;
import kotlin.Result;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.c;
import kotlin.jvm.internal.i;
import kotlin.l;
import kotlinx.coroutines.internal.ThreadContextKt;
import kotlinx.coroutines.internal.s;
import kotlinx.coroutines.internal.t;

/* compiled from: Dispatched.kt */
public final class s0 {
    /* access modifiers changed from: private */
    public static final t a = new t("UNDEFINED");

    public static final <T> void b(c<? super T> cVar, T t) {
        i.b(cVar, "$this$resumeDirect");
        if (cVar instanceof q0) {
            c<T> cVar2 = ((q0) cVar).l;
            Result.a aVar = Result.Companion;
            cVar2.resumeWith(Result.m1constructorimpl(t));
            return;
        }
        Result.a aVar2 = Result.Companion;
        cVar.resumeWith(Result.m1constructorimpl(t));
    }

    private static final void a(t0<?> t0Var) {
        y0 a2 = d2.b.a();
        if (a2.o()) {
            a2.a(t0Var);
            return;
        }
        a2.b(true);
        try {
            a(t0Var, t0Var.b(), 3);
            do {
            } while (a2.q());
        } catch (Throwable th) {
            a2.a(true);
            throw th;
        }
        a2.a(true);
    }

    public static final <T> void b(c<? super T> cVar, Throwable th) {
        i.b(cVar, "$this$resumeDirectWithException");
        i.b(th, "exception");
        if (cVar instanceof q0) {
            c<T> cVar2 = ((q0) cVar).l;
            Result.a aVar = Result.Companion;
            cVar2.resumeWith(Result.m1constructorimpl(kotlin.i.a(s.a(th, (c<?>) cVar2))));
            return;
        }
        Result.a aVar2 = Result.Companion;
        cVar.resumeWith(Result.m1constructorimpl(kotlin.i.a(s.a(th, (c<?>) cVar))));
    }

    public static final <T> void a(c<? super T> cVar, T t) {
        boolean z;
        CoroutineContext context;
        Object b;
        i.b(cVar, "$this$resumeCancellable");
        if (cVar instanceof q0) {
            q0 q0Var = (q0) cVar;
            if (q0Var.k.isDispatchNeeded(q0Var.getContext())) {
                q0Var.f1813h = t;
                q0Var.f1843g = 1;
                q0Var.k.dispatch(q0Var.getContext(), q0Var);
                return;
            }
            y0 a2 = d2.b.a();
            if (a2.o()) {
                q0Var.f1813h = t;
                q0Var.f1843g = 1;
                a2.a((t0<?>) q0Var);
                return;
            }
            a2.b(true);
            try {
                k1 k1Var = (k1) q0Var.getContext().get(k1.d);
                if (k1Var == null || k1Var.isActive()) {
                    z = false;
                } else {
                    CancellationException c = k1Var.c();
                    Result.a aVar = Result.Companion;
                    q0Var.resumeWith(Result.m1constructorimpl(kotlin.i.a((Throwable) c)));
                    z = true;
                }
                if (!z) {
                    context = q0Var.getContext();
                    b = ThreadContextKt.b(context, q0Var.f1815j);
                    c<T> cVar2 = q0Var.l;
                    Result.a aVar2 = Result.Companion;
                    cVar2.resumeWith(Result.m1constructorimpl(t));
                    l lVar = l.a;
                    ThreadContextKt.a(context, b);
                }
                do {
                } while (a2.q());
            } catch (Throwable th) {
                try {
                    q0Var.a(th, (Throwable) null);
                } catch (Throwable th2) {
                    a2.a(true);
                    throw th2;
                }
            }
            a2.a(true);
            return;
        }
        Result.a aVar3 = Result.Companion;
        cVar.resumeWith(Result.m1constructorimpl(t));
    }

    public static final <T> void a(c<? super T> cVar, Throwable th) {
        CoroutineContext context;
        Object b;
        i.b(cVar, "$this$resumeCancellableWithException");
        i.b(th, "exception");
        if (cVar instanceof q0) {
            q0 q0Var = (q0) cVar;
            CoroutineContext context2 = q0Var.l.getContext();
            boolean z = false;
            u uVar = new u(th, false, 2, (f) null);
            if (q0Var.k.isDispatchNeeded(context2)) {
                q0Var.f1813h = new u(th, false, 2, (f) null);
                q0Var.f1843g = 1;
                q0Var.k.dispatch(context2, q0Var);
                return;
            }
            y0 a2 = d2.b.a();
            if (a2.o()) {
                q0Var.f1813h = uVar;
                q0Var.f1843g = 1;
                a2.a((t0<?>) q0Var);
                return;
            }
            a2.b(true);
            try {
                k1 k1Var = (k1) q0Var.getContext().get(k1.d);
                if (k1Var != null && !k1Var.isActive()) {
                    CancellationException c = k1Var.c();
                    Result.a aVar = Result.Companion;
                    q0Var.resumeWith(Result.m1constructorimpl(kotlin.i.a((Throwable) c)));
                    z = true;
                }
                if (!z) {
                    context = q0Var.getContext();
                    b = ThreadContextKt.b(context, q0Var.f1815j);
                    c<T> cVar2 = q0Var.l;
                    Result.a aVar2 = Result.Companion;
                    cVar2.resumeWith(Result.m1constructorimpl(kotlin.i.a(s.a(th, (c<?>) cVar2))));
                    l lVar = l.a;
                    ThreadContextKt.a(context, b);
                }
                do {
                } while (a2.q());
            } catch (Throwable th2) {
                try {
                    q0Var.a(th2, (Throwable) null);
                } catch (Throwable th3) {
                    a2.a(true);
                    throw th3;
                }
            }
            a2.a(true);
            return;
        }
        Result.a aVar3 = Result.Companion;
        cVar.resumeWith(Result.m1constructorimpl(kotlin.i.a(s.a(th, (c<?>) cVar))));
    }

    public static final <T> void a(t0<? super T> t0Var, int i2) {
        i.b(t0Var, "$this$dispatch");
        c<? super T> b = t0Var.b();
        if (!y1.b(i2) || !(b instanceof q0) || y1.a(i2) != y1.a(t0Var.f1843g)) {
            a(t0Var, b, i2);
            return;
        }
        b0 b0Var = ((q0) b).k;
        CoroutineContext context = b.getContext();
        if (b0Var.isDispatchNeeded(context)) {
            b0Var.dispatch(context, t0Var);
        } else {
            a(t0Var);
        }
    }

    public static final <T> void a(t0<? super T> t0Var, c<? super T> cVar, int i2) {
        i.b(t0Var, "$this$resume");
        i.b(cVar, "delegate");
        Object c = t0Var.c();
        Throwable a2 = t0Var.a(c);
        if (a2 != null) {
            if (!(cVar instanceof t0)) {
                a2 = s.a(a2, (c<?>) cVar);
            }
            y1.b(cVar, a2, i2);
            return;
        }
        y1.a(cVar, t0Var.b(c), i2);
    }
}
