package kotlin.jvm.internal;

import java.util.Iterator;
import java.util.NoSuchElementException;

/* compiled from: ArrayIterator.kt */
final class a<T> implements Iterator<T> {
    private int e;

    /* renamed from: f  reason: collision with root package name */
    private final T[] f1773f;

    public a(T[] tArr) {
        i.b(tArr, "array");
        this.f1773f = tArr;
    }

    public boolean hasNext() {
        return this.e < this.f1773f.length;
    }

    public T next() {
        try {
            T[] tArr = this.f1773f;
            int i2 = this.e;
            this.e = i2 + 1;
            return tArr[i2];
        } catch (ArrayIndexOutOfBoundsException e2) {
            this.e--;
            throw new NoSuchElementException(e2.getMessage());
        }
    }

    public void remove() {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }
}
