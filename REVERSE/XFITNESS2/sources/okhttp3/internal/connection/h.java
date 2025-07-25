package okhttp3.internal.connection;

import java.util.LinkedHashSet;
import java.util.Set;
import okhttp3.j0;

/* compiled from: RouteDatabase */
final class h {
    private final Set<j0> a = new LinkedHashSet();

    h() {
    }

    public synchronized void a(j0 j0Var) {
        this.a.remove(j0Var);
    }

    public synchronized void b(j0 j0Var) {
        this.a.add(j0Var);
    }

    public synchronized boolean c(j0 j0Var) {
        return this.a.contains(j0Var);
    }
}
