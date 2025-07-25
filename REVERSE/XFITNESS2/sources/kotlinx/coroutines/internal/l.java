package kotlinx.coroutines.internal;

import java.util.Iterator;
import java.util.List;
import kotlin.jvm.internal.i;
import kotlinx.coroutines.t1;

/* compiled from: MainDispatchers.kt */
public final class l {
    private static final boolean a = u.a("kotlinx.coroutines.fast.service.loader", true);
    public static final t1 b;

    static {
        l lVar = new l();
        b = lVar.a();
    }

    private l() {
    }

    private final t1 a() {
        List<S> list;
        T t;
        t1 a2;
        try {
            if (a) {
                Class<MainDispatcherFactory> cls = MainDispatcherFactory.class;
                f fVar = f.a;
                ClassLoader classLoader = cls.getClassLoader();
                i.a((Object) classLoader, "clz.classLoader");
                list = fVar.a(cls, classLoader);
            } else {
                Iterator b2 = a.b();
                i.a((Object) b2, "ServiceLoader.load(\n    …             ).iterator()");
                list = h.b(f.a(b2));
            }
            Iterator<T> it = list.iterator();
            if (!it.hasNext()) {
                t = null;
            } else {
                t = it.next();
                if (it.hasNext()) {
                    int a3 = ((MainDispatcherFactory) t).a();
                    do {
                        T next = it.next();
                        int a4 = ((MainDispatcherFactory) next).a();
                        if (a3 < a4) {
                            t = next;
                            a3 = a4;
                        }
                    } while (it.hasNext());
                }
            }
            MainDispatcherFactory mainDispatcherFactory = (MainDispatcherFactory) t;
            if (mainDispatcherFactory == null || (a2 = m.a(mainDispatcherFactory, list)) == null) {
                return new n((Throwable) null, (String) null, 2, (f) null);
            }
            return a2;
        } catch (Throwable th) {
            return new n(th, (String) null, 2, (f) null);
        }
    }
}
