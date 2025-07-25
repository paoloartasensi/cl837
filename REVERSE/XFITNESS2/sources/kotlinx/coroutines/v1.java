package kotlinx.coroutines;

import kotlin.jvm.internal.i;

/* compiled from: Job.kt */
public final class v1 implements v0, m {
    public static final v1 e = new v1();

    private v1() {
    }

    public void a() {
    }

    public boolean a(Throwable th) {
        i.b(th, "cause");
        return false;
    }

    public String toString() {
        return "NonDisposableHandle";
    }
}
