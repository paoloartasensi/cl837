package i.a.c;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import kotlin.jvm.internal.i;
import kotlin.reflect.c;

/* compiled from: KClassExt.kt */
public final class a {
    private static final Map<c<?>, String> a = new ConcurrentHashMap();

    public static final String a(c<?> cVar) {
        i.b(cVar, "$this$getFullName");
        String str = a.get(cVar);
        return str != null ? str : b(cVar);
    }

    private static final String b(c<?> cVar) {
        String name = kotlin.jvm.a.a(cVar).getName();
        Map<c<?>, String> map = a;
        i.a((Object) name, "name");
        map.put(cVar, name);
        return name;
    }
}
