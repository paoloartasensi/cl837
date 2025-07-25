package kotlin.p;

import kotlin.jvm.internal.i;
import kotlin.reflect.h;

/* compiled from: Delegates.kt */
final class b<T> implements c<Object, T> {
    private T a;

    public T a(Object obj, h<?> hVar) {
        i.b(hVar, "property");
        T t = this.a;
        if (t != null) {
            return t;
        }
        throw new IllegalStateException("Property " + hVar.getName() + " should be initialized before get.");
    }

    public void a(Object obj, h<?> hVar, T t) {
        i.b(hVar, "property");
        i.b(t, "value");
        this.a = t;
    }
}
