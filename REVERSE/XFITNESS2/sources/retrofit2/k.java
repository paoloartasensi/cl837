package retrofit2;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;

/* compiled from: Invocation */
public final class k {
    private final Method a;
    private final List<?> b;

    k(Method method, List<?> list) {
        this.a = method;
        this.b = Collections.unmodifiableList(list);
    }

    public Method a() {
        return this.a;
    }

    public String toString() {
        return String.format("%s.%s() %s", new Object[]{this.a.getDeclaringClass().getName(), this.a.getName(), this.b});
    }
}
