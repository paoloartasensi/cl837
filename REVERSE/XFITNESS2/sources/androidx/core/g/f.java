package androidx.core.g;

/* compiled from: Pools */
public class f<T> implements e<T> {
    private final Object[] a;
    private int b;

    public f(int i2) {
        if (i2 > 0) {
            this.a = new Object[i2];
            return;
        }
        throw new IllegalArgumentException("The max pool size must be > 0");
    }

    private boolean b(T t) {
        for (int i2 = 0; i2 < this.b; i2++) {
            if (this.a[i2] == t) {
                return true;
            }
        }
        return false;
    }

    public T a() {
        int i2 = this.b;
        if (i2 <= 0) {
            return null;
        }
        int i3 = i2 - 1;
        T[] tArr = this.a;
        T t = tArr[i3];
        tArr[i3] = null;
        this.b = i2 - 1;
        return t;
    }

    public boolean a(T t) {
        if (!b(t)) {
            int i2 = this.b;
            Object[] objArr = this.a;
            if (i2 >= objArr.length) {
                return false;
            }
            objArr[i2] = t;
            this.b = i2 + 1;
            return true;
        }
        throw new IllegalStateException("Already in the pool!");
    }
}
