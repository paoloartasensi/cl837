package retrofit2;

import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/* compiled from: CallAdapter */
public interface e<R, T> {

    /* compiled from: CallAdapter */
    public static abstract class a {
        /* access modifiers changed from: protected */
        public static Type a(int i2, ParameterizedType parameterizedType) {
            return w.b(i2, parameterizedType);
        }

        public abstract e<?, ?> a(Type type, Annotation[] annotationArr, s sVar);

        /* access modifiers changed from: protected */
        public static Class<?> a(Type type) {
            return w.b(type);
        }
    }

    T a(d<R> dVar);

    Type a();
}
