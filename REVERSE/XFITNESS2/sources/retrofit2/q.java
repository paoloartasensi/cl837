package retrofit2;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import kotlin.coroutines.c;
import okhttp3.b0;
import okhttp3.c0;
import okhttp3.f0;
import okhttp3.y;
import okhttp3.z;
import retrofit2.n;
import retrofit2.y.b;
import retrofit2.y.d;
import retrofit2.y.e;
import retrofit2.y.f;
import retrofit2.y.g;
import retrofit2.y.h;
import retrofit2.y.i;
import retrofit2.y.j;
import retrofit2.y.k;
import retrofit2.y.l;
import retrofit2.y.m;
import retrofit2.y.o;
import retrofit2.y.p;
import retrofit2.y.r;

/* compiled from: RequestFactory */
final class q {
    private final Method a;
    private final z b;
    final String c;
    private final String d;
    private final y e;

    /* renamed from: f  reason: collision with root package name */
    private final b0 f2136f;

    /* renamed from: g  reason: collision with root package name */
    private final boolean f2137g;

    /* renamed from: h  reason: collision with root package name */
    private final boolean f2138h;

    /* renamed from: i  reason: collision with root package name */
    private final boolean f2139i;

    /* renamed from: j  reason: collision with root package name */
    private final n<?>[] f2140j;
    final boolean k;

    q(a aVar) {
        this.a = aVar.b;
        this.b = aVar.a.c;
        this.c = aVar.n;
        this.d = aVar.r;
        this.e = aVar.s;
        this.f2136f = aVar.t;
        this.f2137g = aVar.o;
        this.f2138h = aVar.p;
        this.f2139i = aVar.q;
        this.f2140j = aVar.v;
        this.k = aVar.w;
    }

    static q a(s sVar, Method method) {
        return new a(sVar, method).a();
    }

    /* access modifiers changed from: package-private */
    public f0 a(Object[] objArr) {
        n<?>[] nVarArr = this.f2140j;
        int length = objArr.length;
        if (length == nVarArr.length) {
            p pVar = new p(this.c, this.b, this.d, this.e, this.f2136f, this.f2137g, this.f2138h, this.f2139i);
            if (this.k) {
                length--;
            }
            ArrayList arrayList = new ArrayList(length);
            for (int i2 = 0; i2 < length; i2++) {
                arrayList.add(objArr[i2]);
                nVarArr[i2].a(pVar, objArr[i2]);
            }
            f0.a a2 = pVar.a();
            a2.a(k.class, new k(this.a, arrayList));
            return a2.a();
        }
        throw new IllegalArgumentException("Argument count (" + length + ") doesn't match expected count (" + nVarArr.length + ")");
    }

    /* compiled from: RequestFactory */
    static final class a {
        private static final Pattern x = Pattern.compile("\\{([a-zA-Z][a-zA-Z0-9_-]*)\\}");
        private static final Pattern y = Pattern.compile("[a-zA-Z][a-zA-Z0-9_-]*");
        final s a;
        final Method b;
        final Annotation[] c;
        final Annotation[][] d;
        final Type[] e;

        /* renamed from: f  reason: collision with root package name */
        boolean f2141f;

        /* renamed from: g  reason: collision with root package name */
        boolean f2142g;

        /* renamed from: h  reason: collision with root package name */
        boolean f2143h;

        /* renamed from: i  reason: collision with root package name */
        boolean f2144i;

        /* renamed from: j  reason: collision with root package name */
        boolean f2145j;
        boolean k;
        boolean l;
        boolean m;
        String n;
        boolean o;
        boolean p;
        boolean q;
        String r;
        y s;
        b0 t;
        Set<String> u;
        n<?>[] v;
        boolean w;

        a(s sVar, Method method) {
            this.a = sVar;
            this.b = method;
            this.c = method.getAnnotations();
            this.e = method.getGenericParameterTypes();
            this.d = method.getParameterAnnotations();
        }

        /* access modifiers changed from: package-private */
        public q a() {
            for (Annotation a2 : this.c) {
                a(a2);
            }
            if (this.n != null) {
                if (!this.o) {
                    if (this.q) {
                        throw w.a(this.b, "Multipart can only be specified on HTTP methods with request body (e.g., @POST).", new Object[0]);
                    } else if (this.p) {
                        throw w.a(this.b, "FormUrlEncoded can only be specified on HTTP methods with request body (e.g., @POST).", new Object[0]);
                    }
                }
                int length = this.d.length;
                this.v = new n[length];
                int i2 = length - 1;
                int i3 = 0;
                while (true) {
                    boolean z = true;
                    if (i3 >= length) {
                        break;
                    }
                    n<?>[] nVarArr = this.v;
                    Type type = this.e[i3];
                    Annotation[] annotationArr = this.d[i3];
                    if (i3 != i2) {
                        z = false;
                    }
                    nVarArr[i3] = a(i3, type, annotationArr, z);
                    i3++;
                }
                if (this.r == null && !this.m) {
                    throw w.a(this.b, "Missing either @%s URL or @Url parameter.", this.n);
                } else if (!this.p && !this.q && !this.o && this.f2143h) {
                    throw w.a(this.b, "Non-body HTTP method cannot contain @Body.", new Object[0]);
                } else if (this.p && !this.f2141f) {
                    throw w.a(this.b, "Form-encoded method must contain at least one @Field.", new Object[0]);
                } else if (!this.q || this.f2142g) {
                    return new q(this);
                } else {
                    throw w.a(this.b, "Multipart method must contain at least one @Part.", new Object[0]);
                }
            } else {
                throw w.a(this.b, "HTTP method annotation is required (e.g., @GET, @POST, etc.).", new Object[0]);
            }
        }

        private void a(Annotation annotation) {
            if (annotation instanceof retrofit2.y.a) {
                a("DELETE", ((retrofit2.y.a) annotation).value(), false);
            } else if (annotation instanceof d) {
                a("GET", ((d) annotation).value(), false);
            } else if (annotation instanceof e) {
                a("HEAD", ((e) annotation).value(), false);
            } else if (annotation instanceof j) {
                a("PATCH", ((j) annotation).value(), true);
            } else if (annotation instanceof k) {
                a("POST", ((k) annotation).value(), true);
            } else if (annotation instanceof l) {
                a("PUT", ((l) annotation).value(), true);
            } else if (annotation instanceof i) {
                a("OPTIONS", ((i) annotation).value(), false);
            } else if (annotation instanceof f) {
                f fVar = (f) annotation;
                a(fVar.method(), fVar.path(), fVar.hasBody());
            } else if (annotation instanceof h) {
                String[] value = ((h) annotation).value();
                if (value.length != 0) {
                    this.s = a(value);
                    return;
                }
                throw w.a(this.b, "@Headers annotation is empty.", new Object[0]);
            }
        }

        private void a(String str, String str2, boolean z) {
            String str3 = this.n;
            if (str3 == null) {
                this.n = str;
                this.o = z;
                if (!str2.isEmpty()) {
                    int indexOf = str2.indexOf(63);
                    if (indexOf != -1 && indexOf < str2.length() - 1) {
                        String substring = str2.substring(indexOf + 1);
                        if (x.matcher(substring).find()) {
                            throw w.a(this.b, "URL query string \"%s\" must not have replace block. For dynamic query parameters use @Query.", substring);
                        }
                    }
                    this.r = str2;
                    this.u = a(str2);
                    return;
                }
                return;
            }
            throw w.a(this.b, "Only one HTTP method is allowed. Found: %s and %s.", str3, str);
        }

        private y a(String[] strArr) {
            y.a aVar = new y.a();
            for (String str : strArr) {
                int indexOf = str.indexOf(58);
                if (indexOf == -1 || indexOf == 0 || indexOf == str.length() - 1) {
                    throw w.a(this.b, "@Headers value must be in the form \"Name: Value\". Found: \"%s\"", str);
                }
                String substring = str.substring(0, indexOf);
                String trim = str.substring(indexOf + 1).trim();
                if ("Content-Type".equalsIgnoreCase(substring)) {
                    try {
                        this.t = b0.a(trim);
                    } catch (IllegalArgumentException e2) {
                        throw w.a(this.b, (Throwable) e2, "Malformed content type: %s", trim);
                    }
                } else {
                    aVar.a(substring, trim);
                }
            }
            return aVar.a();
        }

        private n<?> a(int i2, Type type, Annotation[] annotationArr, boolean z) {
            n<?> nVar;
            if (annotationArr != null) {
                nVar = null;
                for (Annotation a2 : annotationArr) {
                    n<?> a3 = a(i2, type, annotationArr, a2);
                    if (a3 != null) {
                        if (nVar == null) {
                            nVar = a3;
                        } else {
                            throw w.a(this.b, i2, "Multiple Retrofit annotations found, only one allowed.", new Object[0]);
                        }
                    }
                }
            } else {
                nVar = null;
            }
            if (nVar != null) {
                return nVar;
            }
            if (z) {
                try {
                    if (w.b(type) == c.class) {
                        this.w = true;
                        return null;
                    }
                } catch (NoClassDefFoundError unused) {
                }
            }
            throw w.a(this.b, i2, "No Retrofit annotation found.", new Object[0]);
        }

        private n<?> a(int i2, Type type, Annotation[] annotationArr, Annotation annotation) {
            Class<String> cls = String.class;
            Class<c0.b> cls2 = c0.b.class;
            if (annotation instanceof o) {
                a(i2, type);
                if (this.f2145j) {
                    throw w.a(this.b, i2, "A @Path parameter must not come after a @Query.", new Object[0]);
                } else if (this.k) {
                    throw w.a(this.b, i2, "A @Path parameter must not come after a @QueryName.", new Object[0]);
                } else if (this.l) {
                    throw w.a(this.b, i2, "A @Path parameter must not come after a @QueryMap.", new Object[0]);
                } else if (this.m) {
                    throw w.a(this.b, i2, "@Path parameters may not be used with @Url.", new Object[0]);
                } else if (this.r != null) {
                    this.f2144i = true;
                    o oVar = (o) annotation;
                    String value = oVar.value();
                    a(i2, value);
                    return new n.h(this.b, i2, value, this.a.c(type, annotationArr), oVar.encoded());
                } else {
                    throw w.a(this.b, i2, "@Path can only be used with relative url on @%s", this.n);
                }
            } else if (annotation instanceof p) {
                a(i2, type);
                p pVar = (p) annotation;
                String value2 = pVar.value();
                boolean encoded = pVar.encoded();
                Class<?> b2 = w.b(type);
                this.f2145j = true;
                if (Iterable.class.isAssignableFrom(b2)) {
                    if (type instanceof ParameterizedType) {
                        return new n.i(value2, this.a.c(w.b(0, (ParameterizedType) type), annotationArr), encoded).b();
                    }
                    Method method = this.b;
                    throw w.a(method, i2, b2.getSimpleName() + " must include generic type (e.g., " + b2.getSimpleName() + "<String>)", new Object[0]);
                } else if (!b2.isArray()) {
                    return new n.i(value2, this.a.c(type, annotationArr), encoded);
                } else {
                    return new n.i(value2, this.a.c(a(b2.getComponentType()), annotationArr), encoded).a();
                }
            } else if (annotation instanceof r) {
                a(i2, type);
                boolean encoded2 = ((r) annotation).encoded();
                Class<?> b3 = w.b(type);
                this.k = true;
                if (Iterable.class.isAssignableFrom(b3)) {
                    if (type instanceof ParameterizedType) {
                        return new n.k(this.a.c(w.b(0, (ParameterizedType) type), annotationArr), encoded2).b();
                    }
                    Method method2 = this.b;
                    throw w.a(method2, i2, b3.getSimpleName() + " must include generic type (e.g., " + b3.getSimpleName() + "<String>)", new Object[0]);
                } else if (!b3.isArray()) {
                    return new n.k(this.a.c(type, annotationArr), encoded2);
                } else {
                    return new n.k(this.a.c(a(b3.getComponentType()), annotationArr), encoded2).a();
                }
            } else if (annotation instanceof retrofit2.y.q) {
                a(i2, type);
                Class<?> b4 = w.b(type);
                this.l = true;
                if (Map.class.isAssignableFrom(b4)) {
                    Type b5 = w.b(type, b4, Map.class);
                    if (b5 instanceof ParameterizedType) {
                        ParameterizedType parameterizedType = (ParameterizedType) b5;
                        Type b6 = w.b(0, parameterizedType);
                        if (cls == b6) {
                            return new n.j(this.b, i2, this.a.c(w.b(1, parameterizedType), annotationArr), ((retrofit2.y.q) annotation).encoded());
                        }
                        Method method3 = this.b;
                        throw w.a(method3, i2, "@QueryMap keys must be of type String: " + b6, new Object[0]);
                    }
                    throw w.a(this.b, i2, "Map must include generic types (e.g., Map<String, String>)", new Object[0]);
                }
                throw w.a(this.b, i2, "@QueryMap parameter type must be Map.", new Object[0]);
            } else if (annotation instanceof g) {
                a(i2, type);
                String value3 = ((g) annotation).value();
                Class<?> b7 = w.b(type);
                if (Iterable.class.isAssignableFrom(b7)) {
                    if (type instanceof ParameterizedType) {
                        return new n.e(value3, this.a.c(w.b(0, (ParameterizedType) type), annotationArr)).b();
                    }
                    Method method4 = this.b;
                    throw w.a(method4, i2, b7.getSimpleName() + " must include generic type (e.g., " + b7.getSimpleName() + "<String>)", new Object[0]);
                } else if (!b7.isArray()) {
                    return new n.e(value3, this.a.c(type, annotationArr));
                } else {
                    return new n.e(value3, this.a.c(a(b7.getComponentType()), annotationArr)).a();
                }
            } else if (annotation instanceof b) {
                a(i2, type);
                if (this.p) {
                    b bVar = (b) annotation;
                    String value4 = bVar.value();
                    boolean encoded3 = bVar.encoded();
                    this.f2141f = true;
                    Class<?> b8 = w.b(type);
                    if (Iterable.class.isAssignableFrom(b8)) {
                        if (type instanceof ParameterizedType) {
                            return new n.c(value4, this.a.c(w.b(0, (ParameterizedType) type), annotationArr), encoded3).b();
                        }
                        Method method5 = this.b;
                        throw w.a(method5, i2, b8.getSimpleName() + " must include generic type (e.g., " + b8.getSimpleName() + "<String>)", new Object[0]);
                    } else if (!b8.isArray()) {
                        return new n.c(value4, this.a.c(type, annotationArr), encoded3);
                    } else {
                        return new n.c(value4, this.a.c(a(b8.getComponentType()), annotationArr), encoded3).a();
                    }
                } else {
                    throw w.a(this.b, i2, "@Field parameters can only be used with form encoding.", new Object[0]);
                }
            } else if (annotation instanceof retrofit2.y.c) {
                a(i2, type);
                if (this.p) {
                    Class<?> b9 = w.b(type);
                    if (Map.class.isAssignableFrom(b9)) {
                        Type b10 = w.b(type, b9, Map.class);
                        if (b10 instanceof ParameterizedType) {
                            ParameterizedType parameterizedType2 = (ParameterizedType) b10;
                            Type b11 = w.b(0, parameterizedType2);
                            if (cls == b11) {
                                h c2 = this.a.c(w.b(1, parameterizedType2), annotationArr);
                                this.f2141f = true;
                                return new n.d(this.b, i2, c2, ((retrofit2.y.c) annotation).encoded());
                            }
                            Method method6 = this.b;
                            throw w.a(method6, i2, "@FieldMap keys must be of type String: " + b11, new Object[0]);
                        }
                        throw w.a(this.b, i2, "Map must include generic types (e.g., Map<String, String>)", new Object[0]);
                    }
                    throw w.a(this.b, i2, "@FieldMap parameter type must be Map.", new Object[0]);
                }
                throw w.a(this.b, i2, "@FieldMap parameters can only be used with form encoding.", new Object[0]);
            } else if (annotation instanceof m) {
                a(i2, type);
                if (this.q) {
                    m mVar = (m) annotation;
                    this.f2142g = true;
                    String value5 = mVar.value();
                    Class<?> b12 = w.b(type);
                    if (!value5.isEmpty()) {
                        y a2 = y.a("Content-Disposition", "form-data; name=\"" + value5 + "\"", "Content-Transfer-Encoding", mVar.encoding());
                        if (Iterable.class.isAssignableFrom(b12)) {
                            if (type instanceof ParameterizedType) {
                                Type b13 = w.b(0, (ParameterizedType) type);
                                if (!cls2.isAssignableFrom(w.b(b13))) {
                                    return new n.f(this.b, i2, a2, this.a.a(b13, annotationArr, this.c)).b();
                                }
                                throw w.a(this.b, i2, "@Part parameters using the MultipartBody.Part must not include a part name in the annotation.", new Object[0]);
                            }
                            Method method7 = this.b;
                            throw w.a(method7, i2, b12.getSimpleName() + " must include generic type (e.g., " + b12.getSimpleName() + "<String>)", new Object[0]);
                        } else if (b12.isArray()) {
                            Class<?> a3 = a(b12.getComponentType());
                            if (!cls2.isAssignableFrom(a3)) {
                                return new n.f(this.b, i2, a2, this.a.a((Type) a3, annotationArr, this.c)).a();
                            }
                            throw w.a(this.b, i2, "@Part parameters using the MultipartBody.Part must not include a part name in the annotation.", new Object[0]);
                        } else if (!cls2.isAssignableFrom(b12)) {
                            return new n.f(this.b, i2, a2, this.a.a(type, annotationArr, this.c));
                        } else {
                            throw w.a(this.b, i2, "@Part parameters using the MultipartBody.Part must not include a part name in the annotation.", new Object[0]);
                        }
                    } else if (Iterable.class.isAssignableFrom(b12)) {
                        if (!(type instanceof ParameterizedType)) {
                            Method method8 = this.b;
                            throw w.a(method8, i2, b12.getSimpleName() + " must include generic type (e.g., " + b12.getSimpleName() + "<String>)", new Object[0]);
                        } else if (cls2.isAssignableFrom(w.b(w.b(0, (ParameterizedType) type)))) {
                            return n.l.a.b();
                        } else {
                            throw w.a(this.b, i2, "@Part annotation must supply a name or use MultipartBody.Part parameter type.", new Object[0]);
                        }
                    } else if (b12.isArray()) {
                        if (cls2.isAssignableFrom(b12.getComponentType())) {
                            return n.l.a.a();
                        }
                        throw w.a(this.b, i2, "@Part annotation must supply a name or use MultipartBody.Part parameter type.", new Object[0]);
                    } else if (cls2.isAssignableFrom(b12)) {
                        return n.l.a;
                    } else {
                        throw w.a(this.b, i2, "@Part annotation must supply a name or use MultipartBody.Part parameter type.", new Object[0]);
                    }
                } else {
                    throw w.a(this.b, i2, "@Part parameters can only be used with multipart encoding.", new Object[0]);
                }
            } else if (!(annotation instanceof retrofit2.y.n)) {
                return null;
            } else {
                a(i2, type);
                if (this.q) {
                    this.f2142g = true;
                    Class<?> b14 = w.b(type);
                    if (Map.class.isAssignableFrom(b14)) {
                        Type b15 = w.b(type, b14, Map.class);
                        if (b15 instanceof ParameterizedType) {
                            ParameterizedType parameterizedType3 = (ParameterizedType) b15;
                            Type b16 = w.b(0, parameterizedType3);
                            if (cls == b16) {
                                Type b17 = w.b(1, parameterizedType3);
                                if (!cls2.isAssignableFrom(w.b(b17))) {
                                    return new n.g(this.b, i2, this.a.a(b17, annotationArr, this.c), ((retrofit2.y.n) annotation).encoding());
                                }
                                throw w.a(this.b, i2, "@PartMap values cannot be MultipartBody.Part. Use @Part List<Part> or a different value type instead.", new Object[0]);
                            }
                            Method method9 = this.b;
                            throw w.a(method9, i2, "@PartMap keys must be of type String: " + b16, new Object[0]);
                        }
                        throw w.a(this.b, i2, "Map must include generic types (e.g., Map<String, String>)", new Object[0]);
                    }
                    throw w.a(this.b, i2, "@PartMap parameter type must be Map.", new Object[0]);
                }
                throw w.a(this.b, i2, "@PartMap parameters can only be used with multipart encoding.", new Object[0]);
            }
        }

        private void a(int i2, Type type) {
            if (w.c(type)) {
                throw w.a(this.b, i2, "Parameter type must not include a type variable or wildcard: %s", type);
            }
        }

        private void a(int i2, String str) {
            if (!y.matcher(str).matches()) {
                throw w.a(this.b, i2, "@Path parameter name must match %s. Found: %s", x.pattern(), str);
            } else if (!this.u.contains(str)) {
                throw w.a(this.b, i2, "URL \"%s\" does not contain \"{%s}\".", this.r, str);
            }
        }

        static Set<String> a(String str) {
            Matcher matcher = x.matcher(str);
            LinkedHashSet linkedHashSet = new LinkedHashSet();
            while (matcher.find()) {
                linkedHashSet.add(matcher.group(1));
            }
            return linkedHashSet;
        }

        private static Class<?> a(Class<?> cls) {
            if (Boolean.TYPE == cls) {
                return Boolean.class;
            }
            if (Byte.TYPE == cls) {
                return Byte.class;
            }
            if (Character.TYPE == cls) {
                return Character.class;
            }
            if (Double.TYPE == cls) {
                return Double.class;
            }
            if (Float.TYPE == cls) {
                return Float.class;
            }
            if (Integer.TYPE == cls) {
                return Integer.class;
            }
            if (Long.TYPE == cls) {
                return Long.class;
            }
            return Short.TYPE == cls ? Short.class : cls;
        }
    }
}
