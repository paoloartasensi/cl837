package kotlinx.coroutines.scheduling;

import kotlinx.coroutines.b0;
import kotlinx.coroutines.internal.u;

/* compiled from: Dispatcher.kt */
public final class b extends c {

    /* renamed from: j  reason: collision with root package name */
    private static final b0 f1830j;
    public static final b k;

    static {
        b bVar = new b();
        k = bVar;
        f1830j = bVar.a(w.a("kotlinx.coroutines.io.parallelism", f.a(64, u.a()), 0, 0, 12, (Object) null));
    }

    private b() {
        super(0, 0, (String) null, 7, (f) null);
    }

    public void close() {
        throw new UnsupportedOperationException("DefaultDispatcher cannot be closed");
    }

    public final b0 n() {
        return f1830j;
    }

    public String toString() {
        return "DefaultDispatcher";
    }
}
