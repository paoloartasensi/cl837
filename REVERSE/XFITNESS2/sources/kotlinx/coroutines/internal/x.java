package kotlinx.coroutines.internal;

import java.lang.Comparable;
import java.util.Arrays;
import kotlin.jvm.internal.i;
import kotlinx.coroutines.internal.y;
import kotlinx.coroutines.j0;

/* compiled from: ThreadSafeHeap.kt */
public class x<T extends y & Comparable<? super T>> {
    private volatile int _size = 0;
    private T[] a;

    private final void d(int i2) {
        while (i2 > 0) {
            T[] tArr = this.a;
            if (tArr != null) {
                int i3 = (i2 - 1) / 2;
                T t = tArr[i3];
                if (t != null) {
                    Comparable comparable = (Comparable) t;
                    T t2 = tArr[i2];
                    if (t2 == null) {
                        i.a();
                        throw null;
                    } else if (comparable.compareTo(t2) > 0) {
                        a(i2, i3);
                        i2 = i3;
                    } else {
                        return;
                    }
                } else {
                    i.a();
                    throw null;
                }
            } else {
                i.a();
                throw null;
            }
        }
    }

    private final T[] f() {
        T[] tArr = this.a;
        if (tArr == null) {
            T[] tArr2 = new y[4];
            this.a = tArr2;
            return tArr2;
        } else if (b() < tArr.length) {
            return tArr;
        } else {
            T[] copyOf = Arrays.copyOf(tArr, b() * 2);
            i.a((Object) copyOf, "java.util.Arrays.copyOf(this, newSize)");
            T[] tArr3 = (y[]) copyOf;
            this.a = tArr3;
            return tArr3;
        }
    }

    public final T a() {
        T[] tArr = this.a;
        if (tArr != null) {
            return tArr[0];
        }
        return null;
    }

    public final int b() {
        return this._size;
    }

    public final boolean c() {
        return b() == 0;
    }

    public final T e() {
        T a2;
        synchronized (this) {
            a2 = b() > 0 ? a(0) : null;
        }
        return a2;
    }

    private final void b(int i2) {
        this._size = i2;
    }

    private final void c(int i2) {
        while (true) {
            int i3 = (i2 * 2) + 1;
            if (i3 < b()) {
                T[] tArr = this.a;
                if (tArr != null) {
                    int i4 = i3 + 1;
                    if (i4 < b()) {
                        T t = tArr[i4];
                        if (t != null) {
                            Comparable comparable = (Comparable) t;
                            T t2 = tArr[i3];
                            if (t2 == null) {
                                i.a();
                                throw null;
                            } else if (comparable.compareTo(t2) < 0) {
                                i3 = i4;
                            }
                        } else {
                            i.a();
                            throw null;
                        }
                    }
                    T t3 = tArr[i2];
                    if (t3 != null) {
                        Comparable comparable2 = (Comparable) t3;
                        T t4 = tArr[i3];
                        if (t4 == null) {
                            i.a();
                            throw null;
                        } else if (comparable2.compareTo(t4) > 0) {
                            a(i2, i3);
                            i2 = i3;
                        } else {
                            return;
                        }
                    } else {
                        i.a();
                        throw null;
                    }
                } else {
                    i.a();
                    throw null;
                }
            } else {
                return;
            }
        }
    }

    public final T a(int i2) {
        boolean z = false;
        if (j0.a()) {
            if (!(b() > 0)) {
                throw new AssertionError();
            }
        }
        T[] tArr = this.a;
        if (tArr != null) {
            b(b() - 1);
            if (i2 < b()) {
                a(i2, b());
                int i3 = (i2 - 1) / 2;
                if (i2 > 0) {
                    T t = tArr[i2];
                    if (t != null) {
                        Comparable comparable = (Comparable) t;
                        T t2 = tArr[i3];
                        if (t2 == null) {
                            i.a();
                            throw null;
                        } else if (comparable.compareTo(t2) < 0) {
                            a(i2, i3);
                            d(i3);
                        }
                    } else {
                        i.a();
                        throw null;
                    }
                }
                c(i2);
            }
            T t3 = tArr[b()];
            if (t3 != null) {
                if (j0.a()) {
                    if (t3.b() == this) {
                        z = true;
                    }
                    if (!z) {
                        throw new AssertionError();
                    }
                }
                t3.a((x<?>) null);
                t3.a(-1);
                tArr[b()] = null;
                return t3;
            }
            i.a();
            throw null;
        }
        i.a();
        throw null;
    }

    public final boolean b(T t) {
        boolean z;
        i.b(t, "node");
        synchronized (this) {
            z = true;
            boolean z2 = false;
            if (t.b() == null) {
                z = false;
            } else {
                int c = t.c();
                if (j0.a()) {
                    if (c >= 0) {
                        z2 = true;
                    }
                    if (!z2) {
                        throw new AssertionError();
                    }
                }
                a(c);
            }
        }
        return z;
    }

    public final T d() {
        T a2;
        synchronized (this) {
            a2 = a();
        }
        return a2;
    }

    public final void a(T t) {
        i.b(t, "node");
        if (j0.a()) {
            if (!(t.b() == null)) {
                throw new AssertionError();
            }
        }
        t.a((x<?>) this);
        y[] f2 = f();
        int b = b();
        b(b + 1);
        f2[b] = t;
        t.a(b);
        d(b);
    }

    private final void a(int i2, int i3) {
        T[] tArr = this.a;
        if (tArr != null) {
            T t = tArr[i3];
            if (t != null) {
                T t2 = tArr[i2];
                if (t2 != null) {
                    tArr[i2] = t;
                    tArr[i3] = t2;
                    t.a(i2);
                    t2.a(i3);
                    return;
                }
                i.a();
                throw null;
            }
            i.a();
            throw null;
        }
        i.a();
        throw null;
    }
}
