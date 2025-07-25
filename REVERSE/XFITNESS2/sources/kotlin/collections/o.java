package kotlin.collections;

import java.util.Collection;
import kotlin.jvm.internal.i;

/* compiled from: MutableCollections.kt */
class o extends n {
    public static <T> boolean a(Collection<? super T> collection, Iterable<? extends T> iterable) {
        i.b(collection, "$this$addAll");
        i.b(iterable, "elements");
        if (iterable instanceof Collection) {
            return collection.addAll((Collection) iterable);
        }
        boolean z = false;
        for (Object add : iterable) {
            if (collection.add(add)) {
                z = true;
            }
        }
        return z;
    }
}
