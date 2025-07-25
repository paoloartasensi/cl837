package kotlin.collections;

import java.util.Set;
import kotlin.jvm.internal.i;

/* compiled from: Sets.kt */
class b0 extends a0 {
    public static final <T> Set<T> a() {
        return EmptySet.INSTANCE;
    }

    public static <T> Set<T> a(T... tArr) {
        i.b(tArr, "elements");
        return tArr.length > 0 ? f.f(tArr) : a();
    }

    public static final <T> Set<T> a(Set<? extends T> set) {
        i.b(set, "$this$optimizeReadOnlySet");
        int size = set.size();
        if (size == 0) {
            return a();
        }
        if (size != 1) {
            return set;
        }
        return a0.a(set.iterator().next());
    }
}
