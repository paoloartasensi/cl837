package retrofit2;

import okhttp3.h0;
import okhttp3.i0;

/* compiled from: Response */
public final class r<T> {
    private final h0 a;
    private final T b;
    private final i0 c;

    private r(h0 h0Var, T t, i0 i0Var) {
        this.a = h0Var;
        this.b = t;
        this.c = i0Var;
    }

    public static <T> r<T> a(T t, h0 h0Var) {
        d.a(h0Var, "rawResponse == null");
        if (h0Var.o()) {
            return new r<>(h0Var, t, (i0) null);
        }
        throw new IllegalArgumentException("rawResponse must be successful response");
    }

    public int b() {
        return this.a.j();
    }

    public boolean c() {
        return this.a.o();
    }

    public String d() {
        return this.a.p();
    }

    public String toString() {
        return this.a.toString();
    }

    public static <T> r<T> a(i0 i0Var, h0 h0Var) {
        d.a(i0Var, "body == null");
        d.a(h0Var, "rawResponse == null");
        if (!h0Var.o()) {
            return new r<>(h0Var, (Object) null, i0Var);
        }
        throw new IllegalArgumentException("rawResponse should not be successful response");
    }

    public T a() {
        return this.b;
    }
}
