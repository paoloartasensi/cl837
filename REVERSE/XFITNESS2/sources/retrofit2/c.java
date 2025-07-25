package retrofit2;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import kotlin.l;
import okhttp3.g0;
import okhttp3.i0;
import retrofit2.h;
import retrofit2.y.s;

/* compiled from: BuiltInConverters */
final class c extends h.a {
    private boolean a = true;

    /* compiled from: BuiltInConverters */
    static final class a implements h<i0, i0> {
        static final a a = new a();

        a() {
        }

        public i0 a(i0 i0Var) {
            try {
                return w.a(i0Var);
            } finally {
                i0Var.close();
            }
        }
    }

    /* compiled from: BuiltInConverters */
    static final class b implements h<g0, g0> {
        static final b a = new b();

        b() {
        }

        public /* bridge */ /* synthetic */ Object a(Object obj) {
            g0 g0Var = (g0) obj;
            a(g0Var);
            return g0Var;
        }

        public g0 a(g0 g0Var) {
            return g0Var;
        }
    }

    /* renamed from: retrofit2.c$c  reason: collision with other inner class name */
    /* compiled from: BuiltInConverters */
    static final class C0100c implements h<i0, i0> {
        static final C0100c a = new C0100c();

        C0100c() {
        }

        public /* bridge */ /* synthetic */ Object a(Object obj) {
            i0 i0Var = (i0) obj;
            a(i0Var);
            return i0Var;
        }

        public i0 a(i0 i0Var) {
            return i0Var;
        }
    }

    /* compiled from: BuiltInConverters */
    static final class d implements h<Object, String> {
        static final d a = new d();

        d() {
        }

        public String a(Object obj) {
            return obj.toString();
        }
    }

    /* compiled from: BuiltInConverters */
    static final class e implements h<i0, l> {
        static final e a = new e();

        e() {
        }

        public l a(i0 i0Var) {
            i0Var.close();
            return l.a;
        }
    }

    /* compiled from: BuiltInConverters */
    static final class f implements h<i0, Void> {
        static final f a = new f();

        f() {
        }

        public Void a(i0 i0Var) {
            i0Var.close();
            return null;
        }
    }

    c() {
    }

    public h<i0, ?> a(Type type, Annotation[] annotationArr, s sVar) {
        if (type == i0.class) {
            if (w.a(annotationArr, (Class<? extends Annotation>) s.class)) {
                return C0100c.a;
            }
            return a.a;
        } else if (type == Void.class) {
            return f.a;
        } else {
            if (!this.a || type != l.class) {
                return null;
            }
            try {
                return e.a;
            } catch (NoClassDefFoundError unused) {
                this.a = false;
                return null;
            }
        }
    }

    public h<?, g0> a(Type type, Annotation[] annotationArr, Annotation[] annotationArr2, s sVar) {
        if (g0.class.isAssignableFrom(w.b(type))) {
            return b.a;
        }
        return null;
    }
}
