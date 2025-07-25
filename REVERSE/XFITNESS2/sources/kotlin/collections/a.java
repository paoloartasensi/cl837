package kotlin.collections;

import java.util.Collection;
import java.util.Iterator;
import kotlin.jvm.internal.b;
import kotlin.jvm.internal.e;
import kotlin.jvm.internal.i;

/* compiled from: Collections.kt */
final class a<T> implements Collection<T> {
    private final T[] e;

    /* renamed from: f  reason: collision with root package name */
    private final boolean f1772f;

    public a(T[] tArr, boolean z) {
        i.b(tArr, "values");
        this.e = tArr;
        this.f1772f = z;
    }

    public int a() {
        return this.e.length;
    }

    public boolean add(T t) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    public boolean addAll(Collection<? extends T> collection) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    public void clear() {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    public boolean contains(Object obj) {
        return f.a(this.e, obj);
    }

    public boolean containsAll(Collection<? extends Object> collection) {
        i.b(collection, "elements");
        if (collection.isEmpty()) {
            return true;
        }
        Iterator<T> it = collection.iterator();
        while (it.hasNext()) {
            if (!contains(it.next())) {
                return false;
            }
        }
        return true;
    }

    public boolean isEmpty() {
        return this.e.length == 0;
    }

    public Iterator<T> iterator() {
        return b.a(this.e);
    }

    public boolean remove(Object obj) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    public boolean removeAll(Collection<? extends Object> collection) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    public boolean retainAll(Collection<? extends Object> collection) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    public final /* bridge */ int size() {
        return a();
    }

    public final Object[] toArray() {
        return i.a(this.e, this.f1772f);
    }

    public <T> T[] toArray(T[] tArr) {
        return e.a(this, tArr);
    }
}
