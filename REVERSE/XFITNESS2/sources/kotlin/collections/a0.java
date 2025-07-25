package kotlin.collections;

import java.util.Collections;
import java.util.Set;
import kotlin.jvm.internal.i;

/* compiled from: SetsJVM.kt */
class a0 {
    public static final <T> Set<T> a(T t) {
        Set<T> singleton = Collections.singleton(t);
        i.a((Object) singleton, "java.util.Collections.singleton(element)");
        return singleton;
    }
}
