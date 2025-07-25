package kotlinx.coroutines;

import kotlin.jvm.internal.i;

/* compiled from: JobSupport.kt */
public final class l extends l1<k1> {

    /* renamed from: i  reason: collision with root package name */
    public final i<?> f1806i;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public l(k1 k1Var, i<?> iVar) {
        super(k1Var);
        i.b(k1Var, "parent");
        i.b(iVar, "child");
        this.f1806i = iVar;
    }

    public void b(Throwable th) {
        i<?> iVar = this.f1806i;
        iVar.a(iVar.a((k1) this.f1812h));
    }

    public /* bridge */ /* synthetic */ Object invoke(Object obj) {
        b((Throwable) obj);
        return kotlin.l.a;
    }

    public String toString() {
        return "ChildContinuation[" + this.f1806i + ']';
    }
}
