package retrofit2.x.a;

import com.google.gson.d;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import okhttp3.g0;
import okhttp3.i0;
import retrofit2.h;
import retrofit2.s;

/* compiled from: GsonConverterFactory */
public final class a extends h.a {
    private final d a;

    private a(d dVar) {
        this.a = dVar;
    }

    public static a a() {
        return a(new d());
    }

    public static a a(d dVar) {
        if (dVar != null) {
            return new a(dVar);
        }
        throw new NullPointerException("gson == null");
    }

    public h<i0, ?> a(Type type, Annotation[] annotationArr, s sVar) {
        return new c(this.a, this.a.a(com.google.gson.r.a.a(type)));
    }

    public h<?, g0> a(Type type, Annotation[] annotationArr, Annotation[] annotationArr2, s sVar) {
        return new b(this.a, this.a.a(com.google.gson.r.a.a(type)));
    }
}
