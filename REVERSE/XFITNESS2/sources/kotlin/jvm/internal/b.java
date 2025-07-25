package kotlin.jvm.internal;

import java.util.Iterator;

/* compiled from: ArrayIterator.kt */
public final class b {
    public static final <T> Iterator<T> a(T[] tArr) {
        i.b(tArr, "array");
        return new a(tArr);
    }
}
