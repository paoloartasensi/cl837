package androidx.constraintlayout.solver;

/* compiled from: Pools */
class h<T> implements g<T> {
    private final Object[] a;
    private int b;

    h(int i2) {
        if (i2 > 0) {
            this.a = new Object[i2];
            return;
        }
        throw new IllegalArgumentException("The max pool size must be > 0");
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
        int i2 = this.b;
        Object[] objArr = this.a;
        if (i2 >= objArr.length) {
            return false;
        }
        objArr[i2] = t;
        this.b = i2 + 1;
        return true;
    }

    public void a(T[] tArr, int i2) {
        if (i2 > tArr.length) {
            i2 = tArr.length;
        }
        for (int i3 = 0; i3 < i2; i3++) {
            T t = tArr[i3];
            int i4 = this.b;
            Object[] objArr = this.a;
            if (i4 < objArr.length) {
                objArr[i4] = t;
                this.b = i4 + 1;
            }
        }
    }
}
