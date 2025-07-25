package kotlin.r;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.jvm.internal.i;

/* compiled from: _Sequences.kt */
class h extends g {
    public static final <T, C extends Collection<? super T>> C a(b<? extends T> bVar, C c) {
        i.b(bVar, "$this$toCollection");
        i.b(c, "destination");
        for (Object add : bVar) {
            c.add(add);
        }
        return c;
    }

    public static <T> List<T> b(b<? extends T> bVar) {
        i.b(bVar, "$this$toList");
        return j.a(c(bVar));
    }

    public static final <T> List<T> c(b<? extends T> bVar) {
        i.b(bVar, "$this$toMutableList");
        ArrayList arrayList = new ArrayList();
        a(bVar, arrayList);
        return arrayList;
    }
}
