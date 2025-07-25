package kotlin.collections;

import java.util.Collections;
import java.util.List;
import kotlin.jvm.internal.i;

/* compiled from: _CollectionsJvm.kt */
class q extends p {
    public static final <T> void b(List<T> list) {
        i.b(list, "$this$reverse");
        Collections.reverse(list);
    }
}
