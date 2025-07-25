package kotlinx.coroutines.internal;

import java.util.List;
import kotlin.jvm.internal.i;
import kotlinx.coroutines.t1;

/* compiled from: MainDispatchers.kt */
public final class m {
    public static final t1 a(MainDispatcherFactory mainDispatcherFactory, List<? extends MainDispatcherFactory> list) {
        i.b(mainDispatcherFactory, "$this$tryCreateDispatcher");
        i.b(list, "factories");
        try {
            return mainDispatcherFactory.a(list);
        } catch (Throwable th) {
            return new n(th, mainDispatcherFactory.b());
        }
    }
}
