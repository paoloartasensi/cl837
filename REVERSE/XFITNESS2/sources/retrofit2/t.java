package retrofit2;

import java.lang.reflect.Method;
import java.lang.reflect.Type;

/* compiled from: ServiceMethod */
abstract class t<T> {
    t() {
    }

    static <T> t<T> a(s sVar, Method method) {
        q a = q.a(sVar, method);
        Type genericReturnType = method.getGenericReturnType();
        if (w.c(genericReturnType)) {
            throw w.a(method, "Method return type must not include a type variable or wildcard: %s", genericReturnType);
        } else if (genericReturnType != Void.TYPE) {
            return j.a(sVar, method, a);
        } else {
            throw w.a(method, "Service methods cannot return void.", new Object[0]);
        }
    }

    /* access modifiers changed from: package-private */
    public abstract T a(Object[] objArr);
}
