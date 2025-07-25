package kotlinx.coroutines;

import kotlin.jvm.internal.i;

/* compiled from: EventLoop.common.kt */
public final class d2 {
    private static final ThreadLocal<y0> a = new ThreadLocal<>();
    public static final d2 b = new d2();

    private d2() {
    }

    public final y0 a() {
        y0 y0Var = a.get();
        if (y0Var != null) {
            return y0Var;
        }
        y0 a2 = b1.a();
        a.set(a2);
        return a2;
    }

    public final void b() {
        a.set((Object) null);
    }

    public final void a(y0 y0Var) {
        i.b(y0Var, "eventLoop");
        a.set(y0Var);
    }
}
