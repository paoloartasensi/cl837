package kotlin.collections;

import java.util.Collection;
import kotlin.jvm.internal.i;

/* compiled from: Iterables.kt */
class k extends j {
    public static <T> int a(Iterable<? extends T> iterable, int i2) {
        i.b(iterable, "$this$collectionSizeOrDefault");
        return iterable instanceof Collection ? ((Collection) iterable).size() : i2;
    }
}
