package kotlinx.coroutines;

import java.util.concurrent.CancellationException;
import kotlin.Result;
import kotlin.TypeCastException;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.c;
import kotlin.jvm.internal.i;
import kotlin.l;
import kotlinx.coroutines.internal.ThreadContextKt;
import kotlinx.coroutines.internal.s;
import kotlinx.coroutines.scheduling.h;

/* compiled from: Dispatched.kt */
public abstract class t0<T> extends h {

    /* renamed from: g  reason: collision with root package name */
    public int f1843g;

    public t0(int i2) {
        this.f1843g = i2;
    }

    public final Throwable a(Object obj) {
        if (!(obj instanceof u)) {
            obj = null;
        }
        u uVar = (u) obj;
        if (uVar != null) {
            return uVar.a;
        }
        return null;
    }

    public void a(Object obj, Throwable th) {
        i.b(th, "cause");
    }

    public <T> T b(Object obj) {
        return obj;
    }

    public abstract c<T> b();

    public abstract Object c();

    public final void run() {
        Object obj;
        CoroutineContext context;
        Object b;
        Object obj2;
        kotlinx.coroutines.scheduling.i iVar = this.f1840f;
        try {
            c b2 = b();
            if (b2 != null) {
                q0 q0Var = (q0) b2;
                c<T> cVar = q0Var.l;
                context = cVar.getContext();
                Object c = c();
                b = ThreadContextKt.b(context, q0Var.f1815j);
                Throwable a = a(c);
                k1 k1Var = y1.a(this.f1843g) ? (k1) context.get(k1.d) : null;
                if (a == null && k1Var != null && !k1Var.isActive()) {
                    CancellationException c2 = k1Var.c();
                    a(c, (Throwable) c2);
                    Result.a aVar = Result.Companion;
                    cVar.resumeWith(Result.m1constructorimpl(kotlin.i.a(s.a(c2, (c<?>) cVar))));
                } else if (a != null) {
                    Result.a aVar2 = Result.Companion;
                    cVar.resumeWith(Result.m1constructorimpl(kotlin.i.a(s.a(a, (c<?>) cVar))));
                } else {
                    Object b3 = b(c);
                    Result.a aVar3 = Result.Companion;
                    cVar.resumeWith(Result.m1constructorimpl(b3));
                }
                l lVar = l.a;
                ThreadContextKt.a(context, b);
                try {
                    Result.a aVar4 = Result.Companion;
                    iVar.m();
                    obj2 = Result.m1constructorimpl(l.a);
                } catch (Throwable th) {
                    Result.a aVar5 = Result.Companion;
                    obj2 = Result.m1constructorimpl(kotlin.i.a(th));
                }
                a((Throwable) null, Result.m4exceptionOrNullimpl(obj2));
                return;
            }
            throw new TypeCastException("null cannot be cast to non-null type kotlinx.coroutines.DispatchedContinuation<T>");
        } catch (Throwable th2) {
            try {
                Result.a aVar6 = Result.Companion;
                iVar.m();
                obj = Result.m1constructorimpl(l.a);
            } catch (Throwable th3) {
                Result.a aVar7 = Result.Companion;
                obj = Result.m1constructorimpl(kotlin.i.a(th3));
            }
            a(th2, Result.m4exceptionOrNullimpl(obj));
        }
    }

    public final void a(Throwable th, Throwable th2) {
        if (th != null || th2 != null) {
            if (!(th == null || th2 == null)) {
                b.a(th, th2);
            }
            if (th == null) {
                th = th2;
            }
            String str = "Fatal exception in coroutines machinery for " + this + ". " + "Please read KDoc to 'handleFatalException' method and report this incident to maintainers";
            if (th != null) {
                d0.a(b().getContext(), (Throwable) new CoroutinesInternalError(str, th));
                return;
            }
            i.a();
            throw null;
        }
    }
}
