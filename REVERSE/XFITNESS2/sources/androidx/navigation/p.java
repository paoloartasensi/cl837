package androidx.navigation;

import android.os.Bundle;
import android.os.Parcelable;
import java.io.Serializable;

/* compiled from: NavType */
public abstract class p<T> {
    public static final p<Integer> b = new c(false);
    public static final p<Integer> c = new d(false);
    public static final p<int[]> d = new e(true);
    public static final p<Long> e = new f(false);

    /* renamed from: f  reason: collision with root package name */
    public static final p<long[]> f746f = new g(true);

    /* renamed from: g  reason: collision with root package name */
    public static final p<Float> f747g = new h(false);

    /* renamed from: h  reason: collision with root package name */
    public static final p<float[]> f748h = new i(true);

    /* renamed from: i  reason: collision with root package name */
    public static final p<Boolean> f749i = new j(false);

    /* renamed from: j  reason: collision with root package name */
    public static final p<boolean[]> f750j = new k(true);
    public static final p<String> k = new a(true);
    public static final p<String[]> l = new b(true);
    private final boolean a;

    p(boolean z) {
        this.a = z;
    }

    public abstract T a(Bundle bundle, String str);

    /* access modifiers changed from: package-private */
    public T a(Bundle bundle, String str, String str2) {
        T a2 = a(str2);
        a(bundle, str, a2);
        return a2;
    }

    public abstract T a(String str);

    public abstract String a();

    public abstract void a(Bundle bundle, String str, T t);

    public boolean b() {
        return this.a;
    }

    public String toString() {
        return a();
    }

    /* compiled from: NavType */
    public static final class l<D extends Enum> extends C0041p<D> {
        private final Class<D> n;

        public l(Class<D> cls) {
            super(false, cls);
            if (cls.isEnum()) {
                this.n = cls;
                return;
            }
            throw new IllegalArgumentException(cls + " is not an Enum type.");
        }

        public D a(String str) {
            for (D d : (Enum[]) this.n.getEnumConstants()) {
                if (d.name().equals(str)) {
                    return d;
                }
            }
            throw new IllegalArgumentException("Enum value " + str + " not found for type " + this.n.getName() + ".");
        }

        public String a() {
            return this.n.getName();
        }
    }

    /* JADX WARNING: type inference failed for: r1v6, types: [androidx.navigation.p, androidx.navigation.p<java.lang.Float>] */
    /* JADX WARNING: type inference failed for: r1v7, types: [androidx.navigation.p, androidx.navigation.p<java.lang.Long>] */
    /* JADX WARNING: type inference failed for: r1v8, types: [androidx.navigation.p, androidx.navigation.p<java.lang.Integer>] */
    /* JADX WARNING: Can't wrap try/catch for region: R(3:3|4|5) */
    /* JADX WARNING: Can't wrap try/catch for region: R(3:6|7|8) */
    /* JADX WARNING: Can't wrap try/catch for region: R(3:9|10|11) */
    /* JADX WARNING: Code restructure failed: missing block: B:10:?, code lost:
        f749i.a(r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x001f, code lost:
        return f749i;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0022, code lost:
        return k;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:3:0x0008, code lost:
        r1 = r1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:4:?, code lost:
        e.a(r1);
        r1 = e;
        r1 = r1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:5:0x000f, code lost:
        return r1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:6:0x0010, code lost:
        r1 = r1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:7:?, code lost:
        f747g.a(r1);
        r1 = f747g;
        r1 = r1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x0017, code lost:
        return r1;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0008 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:6:0x0010 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:9:0x0018 */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Unknown variable types count: 3 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static androidx.navigation.p b(java.lang.String r1) {
        /*
            androidx.navigation.p<java.lang.Integer> r0 = b     // Catch:{ IllegalArgumentException -> 0x0008 }
            r0.a((java.lang.String) r1)     // Catch:{ IllegalArgumentException -> 0x0008 }
            androidx.navigation.p<java.lang.Integer> r1 = b     // Catch:{ IllegalArgumentException -> 0x0008 }
            return r1
        L_0x0008:
            androidx.navigation.p<java.lang.Long> r0 = e     // Catch:{ IllegalArgumentException -> 0x0010 }
            r0.a((java.lang.String) r1)     // Catch:{ IllegalArgumentException -> 0x0010 }
            androidx.navigation.p<java.lang.Long> r1 = e     // Catch:{ IllegalArgumentException -> 0x0010 }
            return r1
        L_0x0010:
            androidx.navigation.p<java.lang.Float> r0 = f747g     // Catch:{ IllegalArgumentException -> 0x0018 }
            r0.a((java.lang.String) r1)     // Catch:{ IllegalArgumentException -> 0x0018 }
            androidx.navigation.p<java.lang.Float> r1 = f747g     // Catch:{ IllegalArgumentException -> 0x0018 }
            return r1
        L_0x0018:
            androidx.navigation.p<java.lang.Boolean> r0 = f749i     // Catch:{ IllegalArgumentException -> 0x0020 }
            r0.a((java.lang.String) r1)     // Catch:{ IllegalArgumentException -> 0x0020 }
            androidx.navigation.p<java.lang.Boolean> r1 = f749i     // Catch:{ IllegalArgumentException -> 0x0020 }
            return r1
        L_0x0020:
            androidx.navigation.p<java.lang.String> r1 = k
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.navigation.p.b(java.lang.String):androidx.navigation.p");
    }

    /* compiled from: NavType */
    static class a extends p<String> {
        a(boolean z) {
            super(z);
        }

        public String a() {
            return "string";
        }

        public String a(String str) {
            return str;
        }

        /* renamed from: b */
        public void a(Bundle bundle, String str, String str2) {
            bundle.putString(str, str2);
        }

        public String a(Bundle bundle, String str) {
            return (String) bundle.get(str);
        }
    }

    /* compiled from: NavType */
    static class b extends p<String[]> {
        b(boolean z) {
            super(z);
        }

        public String a() {
            return "string[]";
        }

        public void a(Bundle bundle, String str, String[] strArr) {
            bundle.putStringArray(str, strArr);
        }

        public String[] a(Bundle bundle, String str) {
            return (String[]) bundle.get(str);
        }

        public String[] a(String str) {
            throw new UnsupportedOperationException("Arrays don't support default values.");
        }
    }

    /* compiled from: NavType */
    static class c extends p<Integer> {
        c(boolean z) {
            super(z);
        }

        public String a() {
            return "integer";
        }

        public void a(Bundle bundle, String str, Integer num) {
            bundle.putInt(str, num.intValue());
        }

        public Integer a(Bundle bundle, String str) {
            return (Integer) bundle.get(str);
        }

        public Integer a(String str) {
            if (str.startsWith("0x")) {
                return Integer.valueOf(Integer.parseInt(str.substring(2), 16));
            }
            return Integer.valueOf(Integer.parseInt(str));
        }
    }

    /* compiled from: NavType */
    static class d extends p<Integer> {
        d(boolean z) {
            super(z);
        }

        public String a() {
            return "reference";
        }

        public void a(Bundle bundle, String str, Integer num) {
            bundle.putInt(str, num.intValue());
        }

        public Integer a(Bundle bundle, String str) {
            return (Integer) bundle.get(str);
        }

        public Integer a(String str) {
            throw new UnsupportedOperationException("References don't support parsing string values.");
        }
    }

    /* compiled from: NavType */
    static class e extends p<int[]> {
        e(boolean z) {
            super(z);
        }

        public String a() {
            return "integer[]";
        }

        public void a(Bundle bundle, String str, int[] iArr) {
            bundle.putIntArray(str, iArr);
        }

        public int[] a(Bundle bundle, String str) {
            return (int[]) bundle.get(str);
        }

        public int[] a(String str) {
            throw new UnsupportedOperationException("Arrays don't support default values.");
        }
    }

    /* compiled from: NavType */
    static class f extends p<Long> {
        f(boolean z) {
            super(z);
        }

        public String a() {
            return "long";
        }

        public void a(Bundle bundle, String str, Long l) {
            bundle.putLong(str, l.longValue());
        }

        public Long a(Bundle bundle, String str) {
            return (Long) bundle.get(str);
        }

        public Long a(String str) {
            if (str.endsWith("L")) {
                str = str.substring(0, str.length() - 1);
            }
            if (str.startsWith("0x")) {
                return Long.valueOf(Long.parseLong(str.substring(2), 16));
            }
            return Long.valueOf(Long.parseLong(str));
        }
    }

    /* compiled from: NavType */
    static class g extends p<long[]> {
        g(boolean z) {
            super(z);
        }

        public String a() {
            return "long[]";
        }

        public void a(Bundle bundle, String str, long[] jArr) {
            bundle.putLongArray(str, jArr);
        }

        public long[] a(Bundle bundle, String str) {
            return (long[]) bundle.get(str);
        }

        public long[] a(String str) {
            throw new UnsupportedOperationException("Arrays don't support default values.");
        }
    }

    /* compiled from: NavType */
    static class h extends p<Float> {
        h(boolean z) {
            super(z);
        }

        public String a() {
            return "float";
        }

        public void a(Bundle bundle, String str, Float f2) {
            bundle.putFloat(str, f2.floatValue());
        }

        public Float a(Bundle bundle, String str) {
            return (Float) bundle.get(str);
        }

        public Float a(String str) {
            return Float.valueOf(Float.parseFloat(str));
        }
    }

    /* compiled from: NavType */
    static class i extends p<float[]> {
        i(boolean z) {
            super(z);
        }

        public String a() {
            return "float[]";
        }

        public void a(Bundle bundle, String str, float[] fArr) {
            bundle.putFloatArray(str, fArr);
        }

        public float[] a(Bundle bundle, String str) {
            return (float[]) bundle.get(str);
        }

        public float[] a(String str) {
            throw new UnsupportedOperationException("Arrays don't support default values.");
        }
    }

    /* compiled from: NavType */
    static class j extends p<Boolean> {
        j(boolean z) {
            super(z);
        }

        public String a() {
            return "boolean";
        }

        public void a(Bundle bundle, String str, Boolean bool) {
            bundle.putBoolean(str, bool.booleanValue());
        }

        public Boolean a(Bundle bundle, String str) {
            return (Boolean) bundle.get(str);
        }

        public Boolean a(String str) {
            if ("true".equals(str)) {
                return true;
            }
            if ("false".equals(str)) {
                return false;
            }
            throw new IllegalArgumentException("A boolean NavType only accepts \"true\" or \"false\" values.");
        }
    }

    /* compiled from: NavType */
    static class k extends p<boolean[]> {
        k(boolean z) {
            super(z);
        }

        public String a() {
            return "boolean[]";
        }

        public void a(Bundle bundle, String str, boolean[] zArr) {
            bundle.putBooleanArray(str, zArr);
        }

        public boolean[] a(Bundle bundle, String str) {
            return (boolean[]) bundle.get(str);
        }

        public boolean[] a(String str) {
            throw new UnsupportedOperationException("Arrays don't support default values.");
        }
    }

    /* compiled from: NavType */
    public static final class m<D extends Parcelable> extends p<D[]> {
        private final Class<D[]> m;

        public m(Class<D> cls) {
            super(true);
            if (Parcelable.class.isAssignableFrom(cls)) {
                try {
                    this.m = Class.forName("[L" + cls.getName() + ";");
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            } else {
                throw new IllegalArgumentException(cls + " does not implement Parcelable.");
            }
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || m.class != obj.getClass()) {
                return false;
            }
            return this.m.equals(((m) obj).m);
        }

        public int hashCode() {
            return this.m.hashCode();
        }

        public void a(Bundle bundle, String str, D[] dArr) {
            this.m.cast(dArr);
            bundle.putParcelableArray(str, dArr);
        }

        public D[] a(Bundle bundle, String str) {
            return (Parcelable[]) bundle.get(str);
        }

        public D[] a(String str) {
            throw new UnsupportedOperationException("Arrays don't support default values.");
        }

        public String a() {
            return this.m.getName();
        }
    }

    /* compiled from: NavType */
    public static final class o<D extends Serializable> extends p<D[]> {
        private final Class<D[]> m;

        public o(Class<D> cls) {
            super(true);
            if (Serializable.class.isAssignableFrom(cls)) {
                try {
                    this.m = Class.forName("[L" + cls.getName() + ";");
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            } else {
                throw new IllegalArgumentException(cls + " does not implement Serializable.");
            }
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || o.class != obj.getClass()) {
                return false;
            }
            return this.m.equals(((o) obj).m);
        }

        public int hashCode() {
            return this.m.hashCode();
        }

        /* JADX WARNING: type inference failed for: r4v0, types: [D[], java.lang.Object, java.io.Serializable] */
        /* JADX WARNING: Unknown variable types count: 1 */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void a(android.os.Bundle r2, java.lang.String r3, D[] r4) {
            /*
                r1 = this;
                java.lang.Class<D[]> r0 = r1.m
                r0.cast(r4)
                r2.putSerializable(r3, r4)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.navigation.p.o.a(android.os.Bundle, java.lang.String, java.io.Serializable[]):void");
        }

        public D[] a(Bundle bundle, String str) {
            return (Serializable[]) bundle.get(str);
        }

        public D[] a(String str) {
            throw new UnsupportedOperationException("Arrays don't support default values.");
        }

        public String a() {
            return this.m.getName();
        }
    }

    /* renamed from: androidx.navigation.p$p  reason: collision with other inner class name */
    /* compiled from: NavType */
    public static class C0041p<D extends Serializable> extends p<D> {
        private final Class<D> m;

        public C0041p(Class<D> cls) {
            super(true);
            if (!Serializable.class.isAssignableFrom(cls)) {
                throw new IllegalArgumentException(cls + " does not implement Serializable.");
            } else if (!cls.isEnum()) {
                this.m = cls;
            } else {
                throw new IllegalArgumentException(cls + " is an Enum. You should use EnumType instead.");
            }
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof C0041p)) {
                return false;
            }
            return this.m.equals(((C0041p) obj).m);
        }

        public int hashCode() {
            return this.m.hashCode();
        }

        public void a(Bundle bundle, String str, D d) {
            this.m.cast(d);
            bundle.putSerializable(str, d);
        }

        public D a(Bundle bundle, String str) {
            return (Serializable) bundle.get(str);
        }

        C0041p(boolean z, Class<D> cls) {
            super(z);
            if (Serializable.class.isAssignableFrom(cls)) {
                this.m = cls;
                return;
            }
            throw new IllegalArgumentException(cls + " does not implement Serializable.");
        }

        public D a(String str) {
            throw new UnsupportedOperationException("Serializables don't support default values.");
        }

        public String a() {
            return this.m.getName();
        }
    }

    public static p<?> a(String str, String str2) {
        String str3;
        if (b.a().equals(str)) {
            return b;
        }
        if (d.a().equals(str)) {
            return d;
        }
        if (e.a().equals(str)) {
            return e;
        }
        if (f746f.a().equals(str)) {
            return f746f;
        }
        if (f749i.a().equals(str)) {
            return f749i;
        }
        if (f750j.a().equals(str)) {
            return f750j;
        }
        if (k.a().equals(str)) {
            return k;
        }
        if (l.a().equals(str)) {
            return l;
        }
        if (f747g.a().equals(str)) {
            return f747g;
        }
        if (f748h.a().equals(str)) {
            return f748h;
        }
        if (c.a().equals(str)) {
            return c;
        }
        if (str == null || str.isEmpty()) {
            return k;
        }
        try {
            if (!str.startsWith(".") || str2 == null) {
                str3 = str;
            } else {
                str3 = str2 + str;
            }
            if (str.endsWith("[]")) {
                str3 = str3.substring(0, str3.length() - 2);
                Class<?> cls = Class.forName(str3);
                if (Parcelable.class.isAssignableFrom(cls)) {
                    return new m(cls);
                }
                if (Serializable.class.isAssignableFrom(cls)) {
                    return new o(cls);
                }
            } else {
                Class<?> cls2 = Class.forName(str3);
                if (Parcelable.class.isAssignableFrom(cls2)) {
                    return new n(cls2);
                }
                if (Enum.class.isAssignableFrom(cls2)) {
                    return new l(cls2);
                }
                if (Serializable.class.isAssignableFrom(cls2)) {
                    return new C0041p(cls2);
                }
            }
            throw new IllegalArgumentException(str3 + " is not Serializable or Parcelable.");
        } catch (ClassNotFoundException e2) {
            throw new RuntimeException(e2);
        }
    }

    /* compiled from: NavType */
    public static final class n<D> extends p<D> {
        private final Class<D> m;

        public n(Class<D> cls) {
            super(true);
            if (Parcelable.class.isAssignableFrom(cls) || Serializable.class.isAssignableFrom(cls)) {
                this.m = cls;
                return;
            }
            throw new IllegalArgumentException(cls + " does not implement Parcelable or Serializable.");
        }

        public void a(Bundle bundle, String str, D d) {
            this.m.cast(d);
            if (d == null || (d instanceof Parcelable)) {
                bundle.putParcelable(str, (Parcelable) d);
            } else if (d instanceof Serializable) {
                bundle.putSerializable(str, (Serializable) d);
            }
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || n.class != obj.getClass()) {
                return false;
            }
            return this.m.equals(((n) obj).m);
        }

        public int hashCode() {
            return this.m.hashCode();
        }

        public D a(Bundle bundle, String str) {
            return bundle.get(str);
        }

        public D a(String str) {
            throw new UnsupportedOperationException("Parcelables don't support default values.");
        }

        public String a() {
            return this.m.getName();
        }
    }

    static p a(Object obj) {
        if (obj instanceof Integer) {
            return b;
        }
        if (obj instanceof int[]) {
            return d;
        }
        if (obj instanceof Long) {
            return e;
        }
        if (obj instanceof long[]) {
            return f746f;
        }
        if (obj instanceof Float) {
            return f747g;
        }
        if (obj instanceof float[]) {
            return f748h;
        }
        if (obj instanceof Boolean) {
            return f749i;
        }
        if (obj instanceof boolean[]) {
            return f750j;
        }
        if ((obj instanceof String) || obj == null) {
            return k;
        }
        if (obj instanceof String[]) {
            return l;
        }
        if (obj.getClass().isArray() && Parcelable.class.isAssignableFrom(obj.getClass().getComponentType())) {
            return new m(obj.getClass().getComponentType());
        }
        if (obj.getClass().isArray() && Serializable.class.isAssignableFrom(obj.getClass().getComponentType())) {
            return new o(obj.getClass().getComponentType());
        }
        if (obj instanceof Parcelable) {
            return new n(obj.getClass());
        }
        if (obj instanceof Enum) {
            return new l(obj.getClass());
        }
        if (obj instanceof Serializable) {
            return new C0041p(obj.getClass());
        }
        throw new IllegalArgumentException("Object of type " + obj.getClass().getName() + " is not supported for navigation arguments.");
    }
}
