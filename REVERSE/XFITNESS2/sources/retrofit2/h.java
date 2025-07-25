package retrofit2;

import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import okhttp3.g0;
import okhttp3.i0;

/* compiled from: Converter */
public interface h<F, T> {

    /* compiled from: Converter */
    public static abstract class a {
        protected static Type a(int i2, ParameterizedType parameterizedType) {
            return w.b(i2, parameterizedType);
        }

        public h<i0, ?> a(Type type, Annotation[] annotationArr, s sVar) {
            return null;
        }

        public h<?, g0> a(Type type, Annotation[] annotationArr, Annotation[] annotationArr2, s sVar) {
            return null;
        }

        public h<?, String> b(Type type, Annotation[] annotationArr, s sVar) {
            return null;
        }

        protected static Class<?> a(Type type) {
            return w.b(type);
        }
    }

    T a(F f2);
}
