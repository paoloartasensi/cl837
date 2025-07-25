package kotlinx.coroutines;

import kotlin.jvm.b.l;
import kotlin.jvm.internal.i;

/* compiled from: JobSupport.kt */
final class j1 extends p1<k1> {

    /* renamed from: i  reason: collision with root package name */
    private final l<Throwable, kotlin.l> f1805i;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public j1(k1 k1Var, l<? super Throwable, kotlin.l> lVar) {
        super(k1Var);
        i.b(k1Var, "job");
        i.b(lVar, "handler");
        this.f1805i = lVar;
    }

    public void b(Throwable th) {
        this.f1805i.invoke(th);
    }

    public /* bridge */ /* synthetic */ Object invoke(Object obj) {
        b((Throwable) obj);
        return kotlin.l.a;
    }

    public String toString() {
        return "InvokeOnCompletion[" + k0.a((Object) this) + '@' + k0.b(this) + ']';
    }
}
