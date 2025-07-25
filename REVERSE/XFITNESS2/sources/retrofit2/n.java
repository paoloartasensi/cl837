package retrofit2;

import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.Map;
import okhttp3.c0;
import okhttp3.g0;
import okhttp3.y;

/* compiled from: ParameterHandler */
abstract class n<T> {

    /* compiled from: ParameterHandler */
    class a extends n<Iterable<T>> {
        a() {
        }

        /* access modifiers changed from: package-private */
        public void a(p pVar, Iterable<T> iterable) {
            if (iterable != null) {
                for (T a2 : iterable) {
                    n.this.a(pVar, a2);
                }
            }
        }
    }

    /* compiled from: ParameterHandler */
    class b extends n<Object> {
        b() {
        }

        /* access modifiers changed from: package-private */
        public void a(p pVar, Object obj) {
            if (obj != null) {
                int length = Array.getLength(obj);
                for (int i2 = 0; i2 < length; i2++) {
                    n.this.a(pVar, Array.get(obj, i2));
                }
            }
        }
    }

    /* compiled from: ParameterHandler */
    static final class c<T> extends n<T> {
        private final String a;
        private final h<T, String> b;
        private final boolean c;

        c(String str, h<T, String> hVar, boolean z) {
            this.a = (String) defpackage.d.a(str, "name == null");
            this.b = hVar;
            this.c = z;
        }

        /* access modifiers changed from: package-private */
        public void a(p pVar, T t) {
            String a2;
            if (t != null && (a2 = this.b.a(t)) != null) {
                pVar.a(this.a, a2, this.c);
            }
        }
    }

    /* compiled from: ParameterHandler */
    static final class d<T> extends n<Map<String, T>> {
        private final Method a;
        private final int b;
        private final h<T, String> c;
        private final boolean d;

        d(Method method, int i2, h<T, String> hVar, boolean z) {
            this.a = method;
            this.b = i2;
            this.c = hVar;
            this.d = z;
        }

        /* access modifiers changed from: package-private */
        public void a(p pVar, Map<String, T> map) {
            if (map != null) {
                for (Map.Entry next : map.entrySet()) {
                    String str = (String) next.getKey();
                    if (str != null) {
                        Object value = next.getValue();
                        if (value != null) {
                            String a2 = this.c.a(value);
                            if (a2 != null) {
                                pVar.a(str, a2, this.d);
                            } else {
                                Method method = this.a;
                                int i2 = this.b;
                                throw w.a(method, i2, "Field map value '" + value + "' converted to null by " + this.c.getClass().getName() + " for key '" + str + "'.", new Object[0]);
                            }
                        } else {
                            Method method2 = this.a;
                            int i3 = this.b;
                            throw w.a(method2, i3, "Field map contained null value for key '" + str + "'.", new Object[0]);
                        }
                    } else {
                        throw w.a(this.a, this.b, "Field map contained null key.", new Object[0]);
                    }
                }
                return;
            }
            throw w.a(this.a, this.b, "Field map was null.", new Object[0]);
        }
    }

    /* compiled from: ParameterHandler */
    static final class e<T> extends n<T> {
        private final String a;
        private final h<T, String> b;

        e(String str, h<T, String> hVar) {
            this.a = (String) defpackage.d.a(str, "name == null");
            this.b = hVar;
        }

        /* access modifiers changed from: package-private */
        public void a(p pVar, T t) {
            String a2;
            if (t != null && (a2 = this.b.a(t)) != null) {
                pVar.a(this.a, a2);
            }
        }
    }

    /* compiled from: ParameterHandler */
    static final class f<T> extends n<T> {
        private final Method a;
        private final int b;
        private final y c;
        private final h<T, g0> d;

        f(Method method, int i2, y yVar, h<T, g0> hVar) {
            this.a = method;
            this.b = i2;
            this.c = yVar;
            this.d = hVar;
        }

        /* access modifiers changed from: package-private */
        public void a(p pVar, T t) {
            if (t != null) {
                try {
                    pVar.a(this.c, this.d.a(t));
                } catch (IOException e) {
                    Method method = this.a;
                    int i2 = this.b;
                    throw w.a(method, i2, "Unable to convert " + t + " to RequestBody", e);
                }
            }
        }
    }

    /* compiled from: ParameterHandler */
    static final class g<T> extends n<Map<String, T>> {
        private final Method a;
        private final int b;
        private final h<T, g0> c;
        private final String d;

        g(Method method, int i2, h<T, g0> hVar, String str) {
            this.a = method;
            this.b = i2;
            this.c = hVar;
            this.d = str;
        }

        /* access modifiers changed from: package-private */
        public void a(p pVar, Map<String, T> map) {
            if (map != null) {
                for (Map.Entry next : map.entrySet()) {
                    String str = (String) next.getKey();
                    if (str != null) {
                        Object value = next.getValue();
                        if (value != null) {
                            pVar.a(y.a("Content-Disposition", "form-data; name=\"" + str + "\"", "Content-Transfer-Encoding", this.d), this.c.a(value));
                        } else {
                            Method method = this.a;
                            int i2 = this.b;
                            throw w.a(method, i2, "Part map contained null value for key '" + str + "'.", new Object[0]);
                        }
                    } else {
                        throw w.a(this.a, this.b, "Part map contained null key.", new Object[0]);
                    }
                }
                return;
            }
            throw w.a(this.a, this.b, "Part map was null.", new Object[0]);
        }
    }

    /* compiled from: ParameterHandler */
    static final class h<T> extends n<T> {
        private final Method a;
        private final int b;
        private final String c;
        private final h<T, String> d;
        private final boolean e;

        h(Method method, int i2, String str, h<T, String> hVar, boolean z) {
            this.a = method;
            this.b = i2;
            this.c = (String) defpackage.d.a(str, "name == null");
            this.d = hVar;
            this.e = z;
        }

        /* access modifiers changed from: package-private */
        public void a(p pVar, T t) {
            if (t != null) {
                pVar.b(this.c, this.d.a(t), this.e);
                return;
            }
            Method method = this.a;
            int i2 = this.b;
            throw w.a(method, i2, "Path parameter \"" + this.c + "\" value must not be null.", new Object[0]);
        }
    }

    /* compiled from: ParameterHandler */
    static final class i<T> extends n<T> {
        private final String a;
        private final h<T, String> b;
        private final boolean c;

        i(String str, h<T, String> hVar, boolean z) {
            this.a = (String) defpackage.d.a(str, "name == null");
            this.b = hVar;
            this.c = z;
        }

        /* access modifiers changed from: package-private */
        public void a(p pVar, T t) {
            String a2;
            if (t != null && (a2 = this.b.a(t)) != null) {
                pVar.c(this.a, a2, this.c);
            }
        }
    }

    /* compiled from: ParameterHandler */
    static final class j<T> extends n<Map<String, T>> {
        private final Method a;
        private final int b;
        private final h<T, String> c;
        private final boolean d;

        j(Method method, int i2, h<T, String> hVar, boolean z) {
            this.a = method;
            this.b = i2;
            this.c = hVar;
            this.d = z;
        }

        /* access modifiers changed from: package-private */
        public void a(p pVar, Map<String, T> map) {
            if (map != null) {
                for (Map.Entry next : map.entrySet()) {
                    String str = (String) next.getKey();
                    if (str != null) {
                        Object value = next.getValue();
                        if (value != null) {
                            String a2 = this.c.a(value);
                            if (a2 != null) {
                                pVar.c(str, a2, this.d);
                            } else {
                                Method method = this.a;
                                int i2 = this.b;
                                throw w.a(method, i2, "Query map value '" + value + "' converted to null by " + this.c.getClass().getName() + " for key '" + str + "'.", new Object[0]);
                            }
                        } else {
                            Method method2 = this.a;
                            int i3 = this.b;
                            throw w.a(method2, i3, "Query map contained null value for key '" + str + "'.", new Object[0]);
                        }
                    } else {
                        throw w.a(this.a, this.b, "Query map contained null key.", new Object[0]);
                    }
                }
                return;
            }
            throw w.a(this.a, this.b, "Query map was null", new Object[0]);
        }
    }

    /* compiled from: ParameterHandler */
    static final class k<T> extends n<T> {
        private final h<T, String> a;
        private final boolean b;

        k(h<T, String> hVar, boolean z) {
            this.a = hVar;
            this.b = z;
        }

        /* access modifiers changed from: package-private */
        public void a(p pVar, T t) {
            if (t != null) {
                pVar.c(this.a.a(t), (String) null, this.b);
            }
        }
    }

    /* compiled from: ParameterHandler */
    static final class l extends n<c0.b> {
        static final l a = new l();

        private l() {
        }

        /* access modifiers changed from: package-private */
        public void a(p pVar, c0.b bVar) {
            if (bVar != null) {
                pVar.a(bVar);
            }
        }
    }

    n() {
    }

    /* access modifiers changed from: package-private */
    public final n<Object> a() {
        return new b();
    }

    /* access modifiers changed from: package-private */
    public abstract void a(p pVar, T t);

    /* access modifiers changed from: package-private */
    public final n<Iterable<T>> b() {
        return new a();
    }
}
