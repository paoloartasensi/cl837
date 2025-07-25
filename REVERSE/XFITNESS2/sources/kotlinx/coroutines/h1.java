package kotlinx.coroutines;

import kotlin.jvm.b.l;
import kotlin.jvm.internal.i;

/* compiled from: CancellableContinuationImpl.kt */
final class h1 extends f {
    private final l<Throwable, kotlin.l> e;

    public h1(l<? super Throwable, kotlin.l> lVar) {
        i.b(lVar, "handler");
        this.e = lVar;
    }

    public void a(Throwable th) {
        this.e.invoke(th);
    }

    public /* bridge */ /* synthetic */ Object invoke(Object obj) {
        a((Throwable) obj);
        return kotlin.l.a;
    }

    public String toString() {
        return "InvokeOnCancel[" + k0.a((Object) this.e) + '@' + k0.b(this) + ']';
    }
}
