package kotlinx.coroutines;

import java.util.concurrent.CancellationException;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.c;
import kotlin.coroutines.jvm.internal.b;
import kotlin.jvm.b.l;
import kotlinx.coroutines.internal.s;
import kotlinx.coroutines.k1;

/* compiled from: CancellableContinuationImpl.kt */
public class i<T> extends t0<T> implements h<T>, b {

    /* renamed from: j  reason: collision with root package name */
    private static final AtomicIntegerFieldUpdater f1793j = AtomicIntegerFieldUpdater.newUpdater(i.class, "_decision");
    private static final AtomicReferenceFieldUpdater k = AtomicReferenceFieldUpdater.newUpdater(i.class, Object.class, "_state");
    private volatile int _decision = 0;
    private volatile Object _state = b.e;

    /* renamed from: h  reason: collision with root package name */
    private final CoroutineContext f1794h;

    /* renamed from: i  reason: collision with root package name */
    private final c<T> f1795i;
    private volatile v0 parentHandle;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public i(c<? super T> cVar, int i2) {
        super(i2);
        kotlin.jvm.internal.i.b(cVar, "delegate");
        this.f1795i = cVar;
        this.f1794h = cVar.getContext();
    }

    private final void h() {
        v0 v0Var = this.parentHandle;
        if (v0Var != null) {
            v0Var.a();
            this.parentHandle = v1.e;
        }
    }

    private final void i() {
        k1 k1Var;
        if (!f() && (k1Var = (k1) this.f1795i.getContext().get(k1.d)) != null) {
            k1Var.start();
            v0 a = k1.a.a(k1Var, true, false, new l(k1Var, this), 2, (Object) null);
            this.parentHandle = a;
            if (f()) {
                a.a();
                this.parentHandle = v1.e;
            }
        }
    }

    private final boolean j() {
        do {
            int i2 = this._decision;
            if (i2 != 0) {
                if (i2 == 1) {
                    return false;
                }
                throw new IllegalStateException("Already resumed".toString());
            }
        } while (!f1793j.compareAndSet(this, 0, 2));
        return true;
    }

    private final boolean k() {
        do {
            int i2 = this._decision;
            if (i2 != 0) {
                if (i2 == 2) {
                    return false;
                }
                throw new IllegalStateException("Already suspended".toString());
            }
        } while (!f1793j.compareAndSet(this, 0, 1));
        return true;
    }

    public void a(Object obj, Throwable th) {
        kotlin.jvm.internal.i.b(th, "cause");
        if (obj instanceof x) {
            try {
                ((x) obj).b.invoke(th);
            } catch (Throwable th2) {
                CoroutineContext context = getContext();
                d0.a(context, (Throwable) new CompletionHandlerException("Exception in cancellation handler for " + this, th2));
            }
        }
    }

    public final c<T> b() {
        return this.f1795i;
    }

    public Object c() {
        return e();
    }

    public final Object d() {
        k1 k1Var;
        i();
        if (k()) {
            return b.a();
        }
        Object e = e();
        if (e instanceof u) {
            throw s.a(((u) e).a, (c<?>) this);
        } else if (this.f1843g != 1 || (k1Var = (k1) getContext().get(k1.d)) == null || k1Var.isActive()) {
            return b(e);
        } else {
            CancellationException c = k1Var.c();
            a(e, (Throwable) c);
            throw s.a(c, (c<?>) this);
        }
    }

    public final Object e() {
        return this._state;
    }

    public boolean f() {
        return !(e() instanceof w1);
    }

    /* access modifiers changed from: protected */
    public String g() {
        return "CancellableContinuation";
    }

    public b getCallerFrame() {
        c<T> cVar = this.f1795i;
        if (!(cVar instanceof b)) {
            cVar = null;
        }
        return (b) cVar;
    }

    public CoroutineContext getContext() {
        return this.f1794h;
    }

    public StackTraceElement getStackTraceElement() {
        return null;
    }

    public void resumeWith(Object obj) {
        a(v.a(obj), this.f1843g);
    }

    public String toString() {
        return g() + '(' + k0.a((c<?>) this.f1795i) + "){" + e() + "}@" + k0.b(this);
    }

    private final void c(Object obj) {
        throw new IllegalStateException(("Already resumed, but proposed with update " + obj).toString());
    }

    public <T> T b(Object obj) {
        if (obj instanceof w) {
            return ((w) obj).a;
        }
        return obj instanceof x ? ((x) obj).a : obj;
    }

    public void b(l<? super Throwable, kotlin.l> lVar) {
        Object obj;
        kotlin.jvm.internal.i.b(lVar, "handler");
        Throwable th = null;
        f fVar = null;
        do {
            obj = this._state;
            if (obj instanceof b) {
                if (fVar == null) {
                    fVar = a(lVar);
                }
            } else if (obj instanceof f) {
                a(lVar, obj);
                throw null;
            } else if (!(obj instanceof k)) {
                return;
            } else {
                if (((k) obj).b()) {
                    try {
                        if (!(obj instanceof u)) {
                            obj = null;
                        }
                        u uVar = (u) obj;
                        if (uVar != null) {
                            th = uVar.a;
                        }
                        lVar.invoke(th);
                        return;
                    } catch (Throwable th2) {
                        d0.a(getContext(), (Throwable) new CompletionHandlerException("Exception in cancellation handler for " + this, th2));
                        return;
                    }
                } else {
                    a(lVar, obj);
                    throw null;
                }
            }
        } while (!k.compareAndSet(this, obj, fVar));
    }

    public Throwable a(k1 k1Var) {
        kotlin.jvm.internal.i.b(k1Var, "parent");
        return k1Var.c();
    }

    private final void a(l<? super Throwable, kotlin.l> lVar, Object obj) {
        throw new IllegalStateException(("It's prohibited to register multiple handlers, tried to register " + lVar + ", already has " + obj).toString());
    }

    private final f a(l<? super Throwable, kotlin.l> lVar) {
        return lVar instanceof f ? (f) lVar : new h1(lVar);
    }

    private final void a(int i2) {
        if (!j()) {
            s0.a(this, i2);
        }
    }

    public void a(b0 b0Var, T t) {
        kotlin.jvm.internal.i.b(b0Var, "$this$resumeUndispatched");
        c<T> cVar = this.f1795i;
        b0 b0Var2 = null;
        if (!(cVar instanceof q0)) {
            cVar = null;
        }
        q0 q0Var = (q0) cVar;
        if (q0Var != null) {
            b0Var2 = q0Var.k;
        }
        a((Object) t, b0Var2 == b0Var ? 3 : this.f1843g);
    }

    public boolean a(Throwable th) {
        Object obj;
        boolean z;
        do {
            obj = this._state;
            if (!(obj instanceof w1)) {
                return false;
            }
            z = obj instanceof f;
        } while (!k.compareAndSet(this, obj, new k(this, th, z)));
        if (z) {
            try {
                ((f) obj).a(th);
            } catch (Throwable th2) {
                CoroutineContext context = getContext();
                d0.a(context, (Throwable) new CompletionHandlerException("Exception in cancellation handler for " + this, th2));
            }
        }
        h();
        a(0);
        return true;
    }

    private final k a(Object obj, int i2) {
        Object obj2;
        do {
            obj2 = this._state;
            if (!(obj2 instanceof w1)) {
                if (obj2 instanceof k) {
                    k kVar = (k) obj2;
                    if (kVar.c()) {
                        return kVar;
                    }
                }
                c(obj);
                throw null;
            }
        } while (!k.compareAndSet(this, obj2, obj));
        h();
        a(i2);
        return null;
    }
}
