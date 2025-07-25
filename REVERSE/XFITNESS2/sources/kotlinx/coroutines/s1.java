package kotlinx.coroutines;

import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.c;
import kotlin.jvm.b.p;
import kotlin.jvm.internal.i;
import kotlin.l;
import kotlinx.coroutines.j2.a;

/* compiled from: Builders.common.kt */
final class s1 extends z1 {

    /* renamed from: h  reason: collision with root package name */
    private p<? super g0, ? super c<? super l>, ? extends Object> f1819h;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public s1(CoroutineContext coroutineContext, p<? super g0, ? super c<? super l>, ? extends Object> pVar) {
        super(coroutineContext, false);
        i.b(coroutineContext, "parentContext");
        i.b(pVar, "block");
        this.f1819h = pVar;
    }

    /* access modifiers changed from: protected */
    public void m() {
        p<? super g0, ? super c<? super l>, ? extends Object> pVar = this.f1819h;
        if (pVar != null) {
            this.f1819h = null;
            a.a(pVar, this, this);
            return;
        }
        throw new IllegalStateException("Already started".toString());
    }
}
