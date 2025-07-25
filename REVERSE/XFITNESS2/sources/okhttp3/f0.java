package okhttp3;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import okhttp3.k0.e;
import okhttp3.k0.h.f;
import okhttp3.y;

/* compiled from: Request */
public final class f0 {
    final z a;
    final String b;
    final y c;
    final g0 d;
    final Map<Class<?>, Object> e;

    /* renamed from: f  reason: collision with root package name */
    private volatile i f1879f;

    f0(a aVar) {
        this.a = aVar.a;
        this.b = aVar.b;
        this.c = aVar.c.a();
        this.d = aVar.d;
        this.e = e.a(aVar.e);
    }

    public String a(String str) {
        return this.c.a(str);
    }

    public i b() {
        i iVar = this.f1879f;
        if (iVar != null) {
            return iVar;
        }
        i a2 = i.a(this.c);
        this.f1879f = a2;
        return a2;
    }

    public y c() {
        return this.c;
    }

    public boolean d() {
        return this.a.h();
    }

    public String e() {
        return this.b;
    }

    public a f() {
        return new a(this);
    }

    public z g() {
        return this.a;
    }

    public String toString() {
        return "Request{method=" + this.b + ", url=" + this.a + ", tags=" + this.e + '}';
    }

    /* compiled from: Request */
    public static class a {
        z a;
        String b;
        y.a c;
        g0 d;
        Map<Class<?>, Object> e;

        public a() {
            this.e = Collections.emptyMap();
            this.b = "GET";
            this.c = new y.a();
        }

        public a a(z zVar) {
            if (zVar != null) {
                this.a = zVar;
                return this;
            }
            throw new NullPointerException("url == null");
        }

        public a a(String str, String str2) {
            this.c.c(str, str2);
            return this;
        }

        public a a(String str) {
            this.c.b(str);
            return this;
        }

        a(f0 f0Var) {
            Map<Class<?>, Object> map;
            this.e = Collections.emptyMap();
            this.a = f0Var.a;
            this.b = f0Var.b;
            this.d = f0Var.d;
            if (f0Var.e.isEmpty()) {
                map = Collections.emptyMap();
            } else {
                map = new LinkedHashMap<>(f0Var.e);
            }
            this.e = map;
            this.c = f0Var.c.a();
        }

        public a a(y yVar) {
            this.c = yVar.a();
            return this;
        }

        public a a(String str, g0 g0Var) {
            if (str == null) {
                throw new NullPointerException("method == null");
            } else if (str.length() == 0) {
                throw new IllegalArgumentException("method.length() == 0");
            } else if (g0Var != null && !f.b(str)) {
                throw new IllegalArgumentException("method " + str + " must not have a request body.");
            } else if (g0Var != null || !f.e(str)) {
                this.b = str;
                this.d = g0Var;
                return this;
            } else {
                throw new IllegalArgumentException("method " + str + " must have a request body.");
            }
        }

        public <T> a a(Class<? super T> cls, T t) {
            if (cls != null) {
                if (t == null) {
                    this.e.remove(cls);
                } else {
                    if (this.e.isEmpty()) {
                        this.e = new LinkedHashMap();
                    }
                    this.e.put(cls, cls.cast(t));
                }
                return this;
            }
            throw new NullPointerException("type == null");
        }

        public f0 a() {
            if (this.a != null) {
                return new f0(this);
            }
            throw new IllegalStateException("url == null");
        }
    }

    public g0 a() {
        return this.d;
    }

    public <T> T a(Class<? extends T> cls) {
        return cls.cast(this.e.get(cls));
    }
}
