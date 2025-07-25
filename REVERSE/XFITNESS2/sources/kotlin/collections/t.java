package kotlin.collections;

import java.util.Iterator;

/* compiled from: Iterators.kt */
public abstract class t implements Iterator<Integer> {
    public abstract int a();

    public void remove() {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    public final Integer next() {
        return Integer.valueOf(a());
    }
}
