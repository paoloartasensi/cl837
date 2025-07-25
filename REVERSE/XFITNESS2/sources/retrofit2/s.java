package retrofit2;

import com.jeremyliao.liveeventbus.BuildConfig;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import okhttp3.d0;
import okhttp3.g0;
import okhttp3.i0;
import okhttp3.j;
import okhttp3.z;
import retrofit2.c;
import retrofit2.e;
import retrofit2.h;

/* compiled from: Retrofit */
public final class s {
    private final Map<Method, t<?>> a = new ConcurrentHashMap();
    final j.a b;
    final z c;
    final List<h.a> d;
    final List<e.a> e;

    /* renamed from: f  reason: collision with root package name */
    final boolean f2146f;

    /* compiled from: Retrofit */
    class a implements InvocationHandler {
        private final o a = o.e();
        private final Object[] b = new Object[0];
        final /* synthetic */ Class c;

        a(Class cls) {
            this.c = cls;
        }

        public Object invoke(Object obj, Method method, Object[] objArr) {
            if (method.getDeclaringClass() == Object.class) {
                return method.invoke(this, objArr);
            }
            if (this.a.a(method)) {
                return this.a.a(method, this.c, obj, objArr);
            }
            t<?> a2 = s.this.a(method);
            if (objArr == null) {
                objArr = this.b;
            }
            return a2.a(objArr);
        }
    }

    /* compiled from: Retrofit */
    public static final class b {
        private final o a;
        private j.a b;
        private z c;
        private final List<h.a> d;
        private final List<e.a> e;

        /* renamed from: f  reason: collision with root package name */
        private Executor f2147f;

        /* renamed from: g  reason: collision with root package name */
        private boolean f2148g;

        b(o oVar) {
            this.d = new ArrayList();
            this.e = new ArrayList();
            this.a = oVar;
        }

        public b a(d0 d0Var) {
            a((j.a) d.a(d0Var, "client == null"));
            return this;
        }

        public b a(j.a aVar) {
            this.b = (j.a) d.a(aVar, "factory == null");
            return this;
        }

        public b a(String str) {
            d.a(str, "baseUrl == null");
            a(z.d(str));
            return this;
        }

        public b() {
            this(o.e());
        }

        public b a(z zVar) {
            d.a(zVar, "baseUrl == null");
            List<String> j2 = zVar.j();
            if (BuildConfig.FLAVOR.equals(j2.get(j2.size() - 1))) {
                this.c = zVar;
                return this;
            }
            throw new IllegalArgumentException("baseUrl must end in /: " + zVar);
        }

        public b a(h.a aVar) {
            this.d.add((h.a) d.a(aVar, "factory == null"));
            return this;
        }

        public b a(e.a aVar) {
            this.e.add((e.a) d.a(aVar, "factory == null"));
            return this;
        }

        public s a() {
            if (this.c != null) {
                j.a aVar = this.b;
                if (aVar == null) {
                    aVar = new d0();
                }
                j.a aVar2 = aVar;
                Executor executor = this.f2147f;
                if (executor == null) {
                    executor = this.a.a();
                }
                Executor executor2 = executor;
                ArrayList arrayList = new ArrayList(this.e);
                arrayList.addAll(this.a.a(executor2));
                ArrayList arrayList2 = new ArrayList(this.d.size() + 1 + this.a.c());
                arrayList2.add(new c());
                arrayList2.addAll(this.d);
                arrayList2.addAll(this.a.b());
                return new s(aVar2, this.c, Collections.unmodifiableList(arrayList2), Collections.unmodifiableList(arrayList), executor2, this.f2148g);
            }
            throw new IllegalStateException("Base URL required.");
        }
    }

    s(j.a aVar, z zVar, List<h.a> list, List<e.a> list2, Executor executor, boolean z) {
        this.b = aVar;
        this.c = zVar;
        this.d = list;
        this.e = list2;
        this.f2146f = z;
    }

    private void b(Class<?> cls) {
        if (cls.isInterface()) {
            ArrayDeque arrayDeque = new ArrayDeque(1);
            arrayDeque.add(cls);
            while (!arrayDeque.isEmpty()) {
                Class<?> cls2 = (Class) arrayDeque.removeFirst();
                if (cls2.getTypeParameters().length != 0) {
                    StringBuilder sb = new StringBuilder("Type parameters are unsupported on ");
                    sb.append(cls2.getName());
                    if (cls2 != cls) {
                        sb.append(" which is an interface of ");
                        sb.append(cls.getName());
                    }
                    throw new IllegalArgumentException(sb.toString());
                }
                Collections.addAll(arrayDeque, cls2.getInterfaces());
            }
            if (this.f2146f) {
                o e2 = o.e();
                for (Method method : cls.getDeclaredMethods()) {
                    if (!e2.a(method) && !Modifier.isStatic(method.getModifiers())) {
                        a(method);
                    }
                }
                return;
            }
            return;
        }
        throw new IllegalArgumentException("API declarations must be interfaces.");
    }

    public <T> T a(Class<T> cls) {
        b(cls);
        return Proxy.newProxyInstance(cls.getClassLoader(), new Class[]{cls}, new a(cls));
    }

    public <T> h<T, String> c(Type type, Annotation[] annotationArr) {
        d.a(type, "type == null");
        d.a(annotationArr, "annotations == null");
        int size = this.d.size();
        for (int i2 = 0; i2 < size; i2++) {
            h<?, String> b2 = this.d.get(i2).b(type, annotationArr, this);
            if (b2 != null) {
                return b2;
            }
        }
        return c.d.a;
    }

    /* access modifiers changed from: package-private */
    public t<?> a(Method method) {
        t<?> tVar;
        t<?> tVar2 = this.a.get(method);
        if (tVar2 != null) {
            return tVar2;
        }
        synchronized (this.a) {
            tVar = this.a.get(method);
            if (tVar == null) {
                tVar = t.a(this, method);
                this.a.put(method, tVar);
            }
        }
        return tVar;
    }

    public e<?, ?> a(Type type, Annotation[] annotationArr) {
        return a((e.a) null, type, annotationArr);
    }

    public e<?, ?> a(e.a aVar, Type type, Annotation[] annotationArr) {
        d.a(type, "returnType == null");
        d.a(annotationArr, "annotations == null");
        int indexOf = this.e.indexOf(aVar) + 1;
        int size = this.e.size();
        for (int i2 = indexOf; i2 < size; i2++) {
            e<?, ?> a2 = this.e.get(i2).a(type, annotationArr, this);
            if (a2 != null) {
                return a2;
            }
        }
        StringBuilder sb = new StringBuilder("Could not locate call adapter for ");
        sb.append(type);
        sb.append(".\n");
        if (aVar != null) {
            sb.append("  Skipped:");
            for (int i3 = 0; i3 < indexOf; i3++) {
                sb.append("\n   * ");
                sb.append(this.e.get(i3).getClass().getName());
            }
            sb.append(10);
        }
        sb.append("  Tried:");
        int size2 = this.e.size();
        while (indexOf < size2) {
            sb.append("\n   * ");
            sb.append(this.e.get(indexOf).getClass().getName());
            indexOf++;
        }
        throw new IllegalArgumentException(sb.toString());
    }

    public <T> h<i0, T> b(Type type, Annotation[] annotationArr) {
        return a((h.a) null, type, annotationArr);
    }

    public <T> h<T, g0> a(Type type, Annotation[] annotationArr, Annotation[] annotationArr2) {
        return a((h.a) null, type, annotationArr, annotationArr2);
    }

    public <T> h<T, g0> a(h.a aVar, Type type, Annotation[] annotationArr, Annotation[] annotationArr2) {
        d.a(type, "type == null");
        d.a(annotationArr, "parameterAnnotations == null");
        d.a(annotationArr2, "methodAnnotations == null");
        int indexOf = this.d.indexOf(aVar) + 1;
        int size = this.d.size();
        for (int i2 = indexOf; i2 < size; i2++) {
            h<?, g0> a2 = this.d.get(i2).a(type, annotationArr, annotationArr2, this);
            if (a2 != null) {
                return a2;
            }
        }
        StringBuilder sb = new StringBuilder("Could not locate RequestBody converter for ");
        sb.append(type);
        sb.append(".\n");
        if (aVar != null) {
            sb.append("  Skipped:");
            for (int i3 = 0; i3 < indexOf; i3++) {
                sb.append("\n   * ");
                sb.append(this.d.get(i3).getClass().getName());
            }
            sb.append(10);
        }
        sb.append("  Tried:");
        int size2 = this.d.size();
        while (indexOf < size2) {
            sb.append("\n   * ");
            sb.append(this.d.get(indexOf).getClass().getName());
            indexOf++;
        }
        throw new IllegalArgumentException(sb.toString());
    }

    public <T> h<i0, T> a(h.a aVar, Type type, Annotation[] annotationArr) {
        d.a(type, "type == null");
        d.a(annotationArr, "annotations == null");
        int indexOf = this.d.indexOf(aVar) + 1;
        int size = this.d.size();
        for (int i2 = indexOf; i2 < size; i2++) {
            h<i0, ?> a2 = this.d.get(i2).a(type, annotationArr, this);
            if (a2 != null) {
                return a2;
            }
        }
        StringBuilder sb = new StringBuilder("Could not locate ResponseBody converter for ");
        sb.append(type);
        sb.append(".\n");
        if (aVar != null) {
            sb.append("  Skipped:");
            for (int i3 = 0; i3 < indexOf; i3++) {
                sb.append("\n   * ");
                sb.append(this.d.get(i3).getClass().getName());
            }
            sb.append(10);
        }
        sb.append("  Tried:");
        int size2 = this.d.size();
        while (indexOf < size2) {
            sb.append("\n   * ");
            sb.append(this.d.get(indexOf).getClass().getName());
            indexOf++;
        }
        throw new IllegalArgumentException(sb.toString());
    }
}
