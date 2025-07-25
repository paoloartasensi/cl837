package kotlin.jvm.internal;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Pair;
import kotlin.jvm.KotlinReflectionNotSupportedError;
import kotlin.jvm.b.b;
import kotlin.jvm.b.e;
import kotlin.jvm.b.f;
import kotlin.jvm.b.g;
import kotlin.jvm.b.h;
import kotlin.jvm.b.i;
import kotlin.jvm.b.j;
import kotlin.jvm.b.k;
import kotlin.jvm.b.l;
import kotlin.jvm.b.m;
import kotlin.jvm.b.n;
import kotlin.jvm.b.o;
import kotlin.jvm.b.p;
import kotlin.jvm.b.q;
import kotlin.jvm.b.r;
import kotlin.jvm.b.s;
import kotlin.jvm.b.t;
import kotlin.jvm.b.u;
import kotlin.jvm.b.v;
import kotlin.jvm.b.w;
import kotlin.reflect.c;

/* compiled from: ClassReference.kt */
public final class d implements c<Object>, c {

    /* renamed from: f  reason: collision with root package name */
    private static final Map<Class<? extends kotlin.c<?>>, Integer> f1774f;

    /* renamed from: g  reason: collision with root package name */
    private static final HashMap<String, String> f1775g;

    /* renamed from: h  reason: collision with root package name */
    private static final HashMap<String, String> f1776h;

    /* renamed from: i  reason: collision with root package name */
    private static final HashMap<String, String> f1777i;
    private final Class<?> e;

    /* compiled from: ClassReference.kt */
    public static final class a {
        private a() {
        }

        public /* synthetic */ a(f fVar) {
            this();
        }
    }

    static {
        new a((f) null);
        int i2 = 0;
        List b = j.b(kotlin.jvm.b.a.class, l.class, p.class, q.class, r.class, s.class, t.class, u.class, v.class, w.class, b.class, kotlin.jvm.b.c.class, kotlin.jvm.b.d.class, e.class, f.class, g.class, h.class, i.class, j.class, k.class, m.class, n.class, o.class);
        ArrayList arrayList = new ArrayList(k.a(b, 10));
        for (Object next : b) {
            int i3 = i2 + 1;
            if (i2 >= 0) {
                arrayList.add(kotlin.j.a((Class) next, Integer.valueOf(i2)));
                i2 = i3;
            } else {
                kotlin.collections.h.b();
                throw null;
            }
        }
        f1774f = x.a(arrayList);
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("boolean", "kotlin.Boolean");
        hashMap.put("char", "kotlin.Char");
        hashMap.put("byte", "kotlin.Byte");
        hashMap.put("short", "kotlin.Short");
        hashMap.put("int", "kotlin.Int");
        hashMap.put("float", "kotlin.Float");
        hashMap.put("long", "kotlin.Long");
        hashMap.put("double", "kotlin.Double");
        f1775g = hashMap;
        HashMap<String, String> hashMap2 = new HashMap<>();
        hashMap2.put("java.lang.Boolean", "kotlin.Boolean");
        hashMap2.put("java.lang.Character", "kotlin.Char");
        hashMap2.put("java.lang.Byte", "kotlin.Byte");
        hashMap2.put("java.lang.Short", "kotlin.Short");
        hashMap2.put("java.lang.Integer", "kotlin.Int");
        hashMap2.put("java.lang.Float", "kotlin.Float");
        hashMap2.put("java.lang.Long", "kotlin.Long");
        hashMap2.put("java.lang.Double", "kotlin.Double");
        f1776h = hashMap2;
        HashMap<String, String> hashMap3 = new HashMap<>();
        hashMap3.put("java.lang.Object", "kotlin.Any");
        hashMap3.put("java.lang.String", "kotlin.String");
        hashMap3.put("java.lang.CharSequence", "kotlin.CharSequence");
        hashMap3.put("java.lang.Throwable", "kotlin.Throwable");
        hashMap3.put("java.lang.Cloneable", "kotlin.Cloneable");
        hashMap3.put("java.lang.Number", "kotlin.Number");
        hashMap3.put("java.lang.Comparable", "kotlin.Comparable");
        hashMap3.put("java.lang.Enum", "kotlin.Enum");
        hashMap3.put("java.lang.annotation.Annotation", "kotlin.Annotation");
        hashMap3.put("java.lang.Iterable", "kotlin.collections.Iterable");
        hashMap3.put("java.util.Iterator", "kotlin.collections.Iterator");
        hashMap3.put("java.util.Collection", "kotlin.collections.Collection");
        hashMap3.put("java.util.List", "kotlin.collections.List");
        hashMap3.put("java.util.Set", "kotlin.collections.Set");
        hashMap3.put("java.util.ListIterator", "kotlin.collections.ListIterator");
        hashMap3.put("java.util.Map", "kotlin.collections.Map");
        hashMap3.put("java.util.Map$Entry", "kotlin.collections.Map.Entry");
        hashMap3.put("kotlin.jvm.internal.StringCompanionObject", "kotlin.String.Companion");
        hashMap3.put("kotlin.jvm.internal.EnumCompanionObject", "kotlin.Enum.Companion");
        hashMap3.putAll(f1775g);
        hashMap3.putAll(f1776h);
        Collection<String> values = f1775g.values();
        i.a((Object) values, "primitiveFqNames.values");
        for (String str : values) {
            StringBuilder sb = new StringBuilder();
            sb.append("kotlin.jvm.internal.");
            i.a((Object) str, "kotlinName");
            sb.append(m.a(str, '.', (String) null, 2, (Object) null));
            sb.append("CompanionObject");
            String sb2 = sb.toString();
            Pair a2 = kotlin.j.a(sb2, str + ".Companion");
            hashMap3.put(a2.getFirst(), a2.getSecond());
        }
        for (Map.Entry next2 : f1774f.entrySet()) {
            int intValue = ((Number) next2.getValue()).intValue();
            String name = ((Class) next2.getKey()).getName();
            hashMap3.put(name, "kotlin.Function" + intValue);
        }
        f1777i = hashMap3;
        LinkedHashMap linkedHashMap = new LinkedHashMap(w.a(hashMap3.size()));
        for (Map.Entry entry : hashMap3.entrySet()) {
            linkedHashMap.put(entry.getKey(), m.a((String) entry.getValue(), '.', (String) null, 2, (Object) null));
        }
    }

    public d(Class<?> cls) {
        i.b(cls, "jClass");
        this.e = cls;
    }

    private final Void b() {
        throw new KotlinReflectionNotSupportedError();
    }

    public Class<?> a() {
        return this.e;
    }

    public boolean equals(Object obj) {
        return (obj instanceof d) && i.a((Object) kotlin.jvm.a.b(this), (Object) kotlin.jvm.a.b((c) obj));
    }

    public List<Annotation> getAnnotations() {
        b();
        throw null;
    }

    public int hashCode() {
        return kotlin.jvm.a.b(this).hashCode();
    }

    public String toString() {
        return a().toString() + " (Kotlin reflection is not available)";
    }
}
