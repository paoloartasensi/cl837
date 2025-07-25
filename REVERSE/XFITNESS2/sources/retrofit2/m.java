package retrofit2;

import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Optional;
import okhttp3.i0;
import org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement;
import retrofit2.h;

@IgnoreJRERequirement
/* compiled from: OptionalConverterFactory */
final class m extends h.a {
    static final h.a a = new m();

    @IgnoreJRERequirement
    /* compiled from: OptionalConverterFactory */
    static final class a<T> implements h<i0, Optional<T>> {
        final h<i0, T> a;

        a(h<i0, T> hVar) {
            this.a = hVar;
        }

        public Optional<T> a(i0 i0Var) {
            return Optional.ofNullable(this.a.a(i0Var));
        }
    }

    m() {
    }

    public h<i0, ?> a(Type type, Annotation[] annotationArr, s sVar) {
        if (h.a.a(type) != Optional.class) {
            return null;
        }
        return new a(sVar.b(h.a.a(0, (ParameterizedType) type), annotationArr));
    }
}
