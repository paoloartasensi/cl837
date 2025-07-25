package androidx.core.g;

/* compiled from: Pools */
public class g<T> extends f<T> {
    private final Object c = new Object();

    public g(int i2) {
        super(i2);
    }

    public T a() {
        T a;
        synchronized (this.c) {
            a = super.a();
        }
        return a;
    }

    public boolean a(T t) {
        boolean a;
        synchronized (this.c) {
            a = super.a(t);
        }
        return a;
    }
}
