package kotlinx.coroutines;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import kotlin.jvm.b.l;
import kotlin.jvm.internal.i;

/* compiled from: JobSupport.kt */
final class i1 extends l1<k1> {

    /* renamed from: j  reason: collision with root package name */
    private static final AtomicIntegerFieldUpdater f1796j = AtomicIntegerFieldUpdater.newUpdater(i1.class, "_invoked");
    private volatile int _invoked = 0;

    /* renamed from: i  reason: collision with root package name */
    private final l<Throwable, kotlin.l> f1797i;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public i1(k1 k1Var, l<? super Throwable, kotlin.l> lVar) {
        super(k1Var);
        i.b(k1Var, "job");
        i.b(lVar, "handler");
        this.f1797i = lVar;
    }

    public void b(Throwable th) {
        if (f1796j.compareAndSet(this, 0, 1)) {
            this.f1797i.invoke(th);
        }
    }

    public /* bridge */ /* synthetic */ Object invoke(Object obj) {
        b((Throwable) obj);
        return kotlin.l.a;
    }

    public String toString() {
        return "InvokeOnCancelling[" + k0.a((Object) this) + '@' + k0.b(this) + ']';
    }
}
