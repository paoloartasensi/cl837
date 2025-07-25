package kotlinx.coroutines;

import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.c;
import kotlin.coroutines.jvm.internal.b;
import kotlin.jvm.internal.i;
import kotlin.l;
import kotlinx.coroutines.internal.ThreadContextKt;

/* compiled from: Dispatched.kt */
public final class q0<T> extends t0<T> implements b, c<T> {

    /* renamed from: h  reason: collision with root package name */
    public Object f1813h = s0.a;

    /* renamed from: i  reason: collision with root package name */
    private final b f1814i;

    /* renamed from: j  reason: collision with root package name */
    public final Object f1815j;
    public final b0 k;
    public final c<T> l;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public q0(b0 b0Var, c<? super T> cVar) {
        super(0);
        i.b(b0Var, "dispatcher");
        i.b(cVar, "continuation");
        this.k = b0Var;
        this.l = cVar;
        c<T> cVar2 = this.l;
        this.f1814i = (b) (!(cVar2 instanceof b) ? null : cVar2);
        this.f1815j = ThreadContextKt.a(getContext());
    }

    public c<T> b() {
        return this;
    }

    public Object c() {
        Object obj = this.f1813h;
        if (j0.a()) {
            if (!(obj != s0.a)) {
                throw new AssertionError();
            }
        }
        this.f1813h = s0.a;
        return obj;
    }

    public b getCallerFrame() {
        return this.f1814i;
    }

    public CoroutineContext getContext() {
        return this.l.getContext();
    }

    public StackTraceElement getStackTraceElement() {
        return null;
    }

    public void resumeWith(Object obj) {
        CoroutineContext context;
        Object b;
        CoroutineContext context2 = this.l.getContext();
        Object a = v.a(obj);
        if (this.k.isDispatchNeeded(context2)) {
            this.f1813h = a;
            this.f1843g = 0;
            this.k.dispatch(context2, this);
            return;
        }
        y0 a2 = d2.b.a();
        if (a2.o()) {
            this.f1813h = a;
            this.f1843g = 0;
            a2.a((t0<?>) this);
            return;
        }
        a2.b(true);
        try {
            context = getContext();
            b = ThreadContextKt.b(context, this.f1815j);
            this.l.resumeWith(obj);
            l lVar = l.a;
            ThreadContextKt.a(context, b);
            do {
            } while (a2.q());
        } catch (Throwable th) {
            try {
                a(th, (Throwable) null);
            } catch (Throwable th2) {
                a2.a(true);
                throw th2;
            }
        }
        a2.a(true);
    }

    public String toString() {
        return "DispatchedContinuation[" + this.k + ", " + k0.a((c<?>) this.l) + ']';
    }
}
