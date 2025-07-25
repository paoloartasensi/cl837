package kotlinx.coroutines;

import kotlin.jvm.internal.i;

/* compiled from: CompletableDeferred.kt */
final class r<T> extends q1 implements q<T> {
    public r(k1 k1Var) {
        super(true);
        a(k1Var);
    }

    public boolean a(T t) {
        return c((Object) t);
    }

    public boolean d() {
        return true;
    }

    public boolean a(Throwable th) {
        i.b(th, "exception");
        return c((Object) new u(th, false, 2, (f) null));
    }
}
