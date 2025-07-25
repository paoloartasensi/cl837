package kotlinx.coroutines;

import kotlin.jvm.internal.i;
import kotlin.l;

/* compiled from: JobSupport.kt */
public final class n extends l1<q1> implements m {

    /* renamed from: i  reason: collision with root package name */
    public final o f1809i;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public n(q1 q1Var, o oVar) {
        super(q1Var);
        i.b(q1Var, "parent");
        i.b(oVar, "childJob");
        this.f1809i = oVar;
    }

    public boolean a(Throwable th) {
        i.b(th, "cause");
        return ((q1) this.f1812h).c(th);
    }

    public void b(Throwable th) {
        this.f1809i.a((x1) this.f1812h);
    }

    public /* bridge */ /* synthetic */ Object invoke(Object obj) {
        b((Throwable) obj);
        return l.a;
    }

    public String toString() {
        return "ChildHandle[" + this.f1809i + ']';
    }
}
