package kotlin.collections;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/* compiled from: CollectionsJVM.kt */
class i {
    public static final <T> List<T> a(T t) {
        List<T> singletonList = Collections.singletonList(t);
        kotlin.jvm.internal.i.a((Object) singletonList, "java.util.Collections.singletonList(element)");
        return singletonList;
    }

    public static final <T> Object[] a(T[] tArr, boolean z) {
        Class<Object[]> cls = Object[].class;
        kotlin.jvm.internal.i.b(tArr, "$this$copyToArrayOfAny");
        if (z && kotlin.jvm.internal.i.a((Object) tArr.getClass(), (Object) cls)) {
            return tArr;
        }
        Object[] copyOf = Arrays.copyOf(tArr, tArr.length, cls);
        kotlin.jvm.internal.i.a((Object) copyOf, "java.util.Arrays.copyOf(… Array<Any?>::class.java)");
        return copyOf;
    }
}
