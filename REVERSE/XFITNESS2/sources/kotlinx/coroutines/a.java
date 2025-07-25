package kotlinx.coroutines;

import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.c;
import kotlin.jvm.b.p;
import kotlin.jvm.internal.i;

/* compiled from: AbstractCoroutine.kt */
public abstract class a<T> extends q1 implements k1, c<T>, g0 {

    /* renamed from: f  reason: collision with root package name */
    private final CoroutineContext f1784f;

    /* renamed from: g  reason: collision with root package name */
    protected final CoroutineContext f1785g;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public a(CoroutineContext coroutineContext, boolean z) {
        super(z);
        i.b(coroutineContext, "parentContext");
        this.f1785g = coroutineContext;
        this.f1784f = coroutineContext.plus(this);
    }

    /* access modifiers changed from: protected */
    public void a(Throwable th, boolean z) {
        i.b(th, "cause");
    }

    public final <R> void a(CoroutineStart coroutineStart, R r, p<? super R, ? super c<? super T>, ? extends Object> pVar) {
        i.b(coroutineStart, "start");
        i.b(pVar, "block");
        l();
        coroutineStart.invoke(pVar, r, this);
    }

    /* access modifiers changed from: protected */
    public final void d(Object obj) {
        if (obj instanceof u) {
            u uVar = (u) obj;
            a(uVar.a, uVar.a());
            return;
        }
        e(obj);
    }

    /* access modifiers changed from: protected */
    public void e(T t) {
    }

    public final void e(Throwable th) {
        i.b(th, "exception");
        d0.a(this.f1784f, th);
    }

    public final CoroutineContext getContext() {
        return this.f1784f;
    }

    public CoroutineContext getCoroutineContext() {
        return this.f1784f;
    }

    public String h() {
        String a = a0.a(this.f1784f);
        if (a == null) {
            return super.h();
        }
        return '\"' + a + "\":" + super.h();
    }

    public final void i() {
        m();
    }

    public boolean isActive() {
        return super.isActive();
    }

    public int k() {
        return 0;
    }

    public final void l() {
        a((k1) this.f1785g.get(k1.d));
    }

    /* access modifiers changed from: protected */
    public void m() {
    }

    public final void resumeWith(Object obj) {
        b(v.a(obj), k());
    }
}
