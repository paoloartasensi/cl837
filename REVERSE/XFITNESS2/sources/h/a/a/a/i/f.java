package h.a.a.a.i;

import h.a.a.a.i.f.a;

/* compiled from: ObjectPool */
public class f<T extends a> {

    /* renamed from: g  reason: collision with root package name */
    private static int f1729g;
    private int a;
    private int b;
    private Object[] c;
    private int d;
    private T e;

    /* renamed from: f  reason: collision with root package name */
    private float f1730f;

    /* compiled from: ObjectPool */
    public static abstract class a {

        /* renamed from: f  reason: collision with root package name */
        public static int f1731f = -1;
        int e = f1731f;

        /* access modifiers changed from: protected */
        public abstract a a();
    }

    private f(int i2, T t) {
        if (i2 > 0) {
            this.b = i2;
            this.c = new Object[i2];
            this.d = 0;
            this.e = t;
            this.f1730f = 1.0f;
            b();
            return;
        }
        throw new IllegalArgumentException("Object Pool must be instantiated with a capacity greater than 0!");
    }

    public static synchronized f a(int i2, a aVar) {
        f fVar;
        synchronized (f.class) {
            fVar = new f(i2, aVar);
            fVar.a = f1729g;
            f1729g++;
        }
        return fVar;
    }

    private void b() {
        b(this.f1730f);
    }

    private void c() {
        int i2 = this.b;
        int i3 = i2 * 2;
        this.b = i3;
        Object[] objArr = new Object[i3];
        for (int i4 = 0; i4 < i2; i4++) {
            objArr[i4] = this.c[i4];
        }
        this.c = objArr;
    }

    private void b(float f2) {
        int i2 = this.b;
        int i3 = (int) (((float) i2) * f2);
        if (i3 < 1) {
            i2 = 1;
        } else if (i3 <= i2) {
            i2 = i3;
        }
        for (int i4 = 0; i4 < i2; i4++) {
            this.c[i4] = this.e.a();
        }
        this.d = i2 - 1;
    }

    public void a(float f2) {
        if (f2 > 1.0f) {
            f2 = 1.0f;
        } else if (f2 < 0.0f) {
            f2 = 0.0f;
        }
        this.f1730f = f2;
    }

    public synchronized T a() {
        T t;
        if (this.d == -1 && this.f1730f > 0.0f) {
            b();
        }
        t = (a) this.c[this.d];
        t.e = a.f1731f;
        this.d--;
        return t;
    }

    public synchronized void a(T t) {
        if (t.e == a.f1731f) {
            int i2 = this.d + 1;
            this.d = i2;
            if (i2 >= this.c.length) {
                c();
            }
            t.e = this.a;
            this.c[this.d] = t;
        } else if (t.e == this.a) {
            throw new IllegalArgumentException("The object passed is already stored in this pool!");
        } else {
            throw new IllegalArgumentException("The object to recycle already belongs to poolId " + t.e + ".  Object cannot belong to two different pool instances simultaneously!");
        }
    }
}
