package kotlin.collections;

import java.util.Collection;
import java.util.List;
import kotlin.jvm.internal.i;

/* compiled from: Collections.kt */
class j extends i {
    public static final <T> Collection<T> a(T[] tArr) {
        i.b(tArr, "$this$asCollection");
        return new a(tArr, false);
    }

    public static <T> List<T> b(T... tArr) {
        i.b(tArr, "elements");
        return tArr.length > 0 ? e.a(tArr) : a();
    }

    public static final <T> List<T> a() {
        return EmptyList.INSTANCE;
    }

    public static void b() {
        throw new ArithmeticException("Index overflow has happened.");
    }

    public static <T> List<T> a(List<? extends T> list) {
        i.b(list, "$this$optimizeReadOnlyList");
        int size = list.size();
        if (size == 0) {
            return a();
        }
        if (size != 1) {
            return list;
        }
        return i.a(list.get(0));
    }
}
