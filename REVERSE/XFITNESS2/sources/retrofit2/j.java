package retrofit2;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import okhttp3.h0;
import okhttp3.i0;
import okhttp3.j;
import retrofit2.w;

/* compiled from: HttpServiceMethod */
abstract class j<ResponseT, ReturnT> extends t<ReturnT> {
    private final q a;
    private final j.a b;
    private final h<i0, ResponseT> c;

    /* compiled from: HttpServiceMethod */
    static final class a<ResponseT, ReturnT> extends j<ResponseT, ReturnT> {
        private final e<ResponseT, ReturnT> d;

        a(q qVar, j.a aVar, h<i0, ResponseT> hVar, e<ResponseT, ReturnT> eVar) {
            super(qVar, aVar, hVar);
            this.d = eVar;
        }

        /* JADX WARNING: type inference failed for: r1v0, types: [retrofit2.d, retrofit2.d<ResponseT>] */
        /* access modifiers changed from: protected */
        /* JADX WARNING: Unknown variable types count: 1 */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public ReturnT a(retrofit2.d<ResponseT> r1, java.lang.Object[] r2) {
            /*
                r0 = this;
                retrofit2.e<ResponseT, ReturnT> r2 = r0.d
                java.lang.Object r1 = r2.a(r1)
                return r1
            */
            throw new UnsupportedOperationException("Method not decompiled: retrofit2.j.a.a(retrofit2.d, java.lang.Object[]):java.lang.Object");
        }
    }

    /* compiled from: HttpServiceMethod */
    static final class b<ResponseT> extends j<ResponseT, Object> {
        private final e<ResponseT, d<ResponseT>> d;
        private final boolean e;

        b(q qVar, j.a aVar, h<i0, ResponseT> hVar, e<ResponseT, d<ResponseT>> eVar, boolean z) {
            super(qVar, aVar, hVar);
            this.d = eVar;
            this.e = z;
        }

        /* access modifiers changed from: protected */
        public Object a(d<ResponseT> dVar, Object[] objArr) {
            d dVar2 = (d) this.d.a(dVar);
            kotlin.coroutines.c cVar = objArr[objArr.length - 1];
            try {
                if (this.e) {
                    return KotlinExtensions.b(dVar2, cVar);
                }
                return KotlinExtensions.a(dVar2, cVar);
            } catch (Exception e2) {
                return KotlinExtensions.a(e2, (kotlin.coroutines.c<?>) cVar);
            }
        }
    }

    /* compiled from: HttpServiceMethod */
    static final class c<ResponseT> extends j<ResponseT, Object> {
        private final e<ResponseT, d<ResponseT>> d;

        c(q qVar, j.a aVar, h<i0, ResponseT> hVar, e<ResponseT, d<ResponseT>> eVar) {
            super(qVar, aVar, hVar);
            this.d = eVar;
        }

        /* access modifiers changed from: protected */
        public Object a(d<ResponseT> dVar, Object[] objArr) {
            d dVar2 = (d) this.d.a(dVar);
            kotlin.coroutines.c cVar = objArr[objArr.length - 1];
            try {
                return KotlinExtensions.c(dVar2, cVar);
            } catch (Exception e) {
                return KotlinExtensions.a(e, (kotlin.coroutines.c<?>) cVar);
            }
        }
    }

    j(q qVar, j.a aVar, h<i0, ResponseT> hVar) {
        this.a = qVar;
        this.b = aVar;
        this.c = hVar;
    }

    static <ResponseT, ReturnT> j<ResponseT, ReturnT> a(s sVar, Method method, q qVar) {
        Type type;
        boolean z;
        Class<r> cls = r.class;
        boolean z2 = qVar.k;
        Annotation[] annotations = method.getAnnotations();
        if (z2) {
            Type[] genericParameterTypes = method.getGenericParameterTypes();
            Type a2 = w.a(0, (ParameterizedType) genericParameterTypes[genericParameterTypes.length - 1]);
            if (w.b(a2) != cls || !(a2 instanceof ParameterizedType)) {
                z = false;
            } else {
                a2 = w.b(0, (ParameterizedType) a2);
                z = true;
            }
            type = new w.b((Type) null, d.class, a2);
            annotations = v.a(annotations);
        } else {
            type = method.getGenericReturnType();
            z = false;
        }
        e a3 = a(sVar, method, type, annotations);
        Type a4 = a3.a();
        if (a4 == h0.class) {
            throw w.a(method, "'" + w.b(a4).getName() + "' is not a valid response body type. Did you mean ResponseBody?", new Object[0]);
        } else if (a4 == cls) {
            throw w.a(method, "Response must include generic type (e.g., Response<String>)", new Object[0]);
        } else if (!qVar.c.equals("HEAD") || Void.class.equals(a4)) {
            h a5 = a(sVar, method, a4);
            j.a aVar = sVar.b;
            if (!z2) {
                return new a(qVar, aVar, a5, a3);
            }
            if (z) {
                return new c(qVar, aVar, a5, a3);
            }
            return new b(qVar, aVar, a5, a3, false);
        } else {
            throw w.a(method, "HEAD method must use Void as response type.", new Object[0]);
        }
    }

    /* access modifiers changed from: protected */
    public abstract ReturnT a(d<ResponseT> dVar, Object[] objArr);

    private static <ResponseT, ReturnT> e<ResponseT, ReturnT> a(s sVar, Method method, Type type, Annotation[] annotationArr) {
        try {
            return sVar.a(type, annotationArr);
        } catch (RuntimeException e) {
            throw w.a(method, (Throwable) e, "Unable to create call adapter for %s", type);
        }
    }

    private static <ResponseT> h<i0, ResponseT> a(s sVar, Method method, Type type) {
        try {
            return sVar.b(type, method.getAnnotations());
        } catch (RuntimeException e) {
            throw w.a(method, (Throwable) e, "Unable to create converter for %s", type);
        }
    }

    /* access modifiers changed from: package-private */
    public final ReturnT a(Object[] objArr) {
        return a(new l(this.a, objArr, this.b, this.c), objArr);
    }
}
