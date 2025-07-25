package kotlinx.coroutines.internal;

import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Set;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import kotlin.jvm.internal.i;

/* compiled from: Concurrent.kt */
public final class d {
    static {
        Class<ScheduledThreadPoolExecutor> cls = ScheduledThreadPoolExecutor.class;
        try {
            cls.getMethod("setRemoveOnCancelPolicy", new Class[]{Boolean.TYPE});
        } catch (Throwable unused) {
        }
    }

    public static final <E> Set<E> a(int i2) {
        Set<E> newSetFromMap = Collections.newSetFromMap(new IdentityHashMap(i2));
        i.a((Object) newSetFromMap, "Collections.newSetFromMa…ityHashMap(expectedSize))");
        return newSetFromMap;
    }
}
