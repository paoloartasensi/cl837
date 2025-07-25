package kotlinx.coroutines;

import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.internal.i;
import kotlin.l;

/* compiled from: Builders.common.kt */
class z1 extends a<l> {
    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public z1(CoroutineContext coroutineContext, boolean z) {
        super(coroutineContext, z);
        i.b(coroutineContext, "parentContext");
    }

    /* access modifiers changed from: protected */
    public boolean d(Throwable th) {
        i.b(th, "exception");
        d0.a(getContext(), th);
        return true;
    }
}
