package com.google.gson.internal;

import com.google.gson.JsonIOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;

/* compiled from: ConstructorConstructor */
public final class b {
    private final Map<Type, com.google.gson.e<?>> a;
    private final com.google.gson.internal.j.b b = com.google.gson.internal.j.b.a();

    /* compiled from: ConstructorConstructor */
    class a implements e<T> {
        a(b bVar) {
        }

        public T a() {
            return new ConcurrentHashMap();
        }
    }

    /* renamed from: com.google.gson.internal.b$b  reason: collision with other inner class name */
    /* compiled from: ConstructorConstructor */
    class C0083b implements e<T> {
        C0083b(b bVar) {
        }

        public T a() {
            return new TreeMap();
        }
    }

    /* compiled from: ConstructorConstructor */
    class c implements e<T> {
        c(b bVar) {
        }

        public T a() {
            return new LinkedHashMap();
        }
    }

    /* compiled from: ConstructorConstructor */
    class d implements e<T> {
        d(b bVar) {
        }

        public T a() {
            return new LinkedTreeMap();
        }
    }

    /* compiled from: ConstructorConstructor */
    class e implements e<T> {
        private final i a = i.a();
        final /* synthetic */ Class b;
        final /* synthetic */ Type c;

        e(b bVar, Class cls, Type type) {
            this.b = cls;
            this.c = type;
        }

        public T a() {
            try {
                return this.a.a(this.b);
            } catch (Exception e) {
                throw new RuntimeException("Unable to invoke no-args constructor for " + this.c + ". Registering an InstanceCreator with Gson for this type may fix this problem.", e);
            }
        }
    }

    /* compiled from: ConstructorConstructor */
    class f implements e<T> {
        final /* synthetic */ com.google.gson.e a;
        final /* synthetic */ Type b;

        f(b bVar, com.google.gson.e eVar, Type type) {
            this.a = eVar;
            this.b = type;
        }

        public T a() {
            return this.a.a(this.b);
        }
    }

    /* compiled from: ConstructorConstructor */
    class g implements e<T> {
        final /* synthetic */ com.google.gson.e a;
        final /* synthetic */ Type b;

        g(b bVar, com.google.gson.e eVar, Type type) {
            this.a = eVar;
            this.b = type;
        }

        public T a() {
            return this.a.a(this.b);
        }
    }

    /* compiled from: ConstructorConstructor */
    class h implements e<T> {
        final /* synthetic */ Constructor a;

        h(b bVar, Constructor constructor) {
            this.a = constructor;
        }

        public T a() {
            try {
                return this.a.newInstance((Object[]) null);
            } catch (InstantiationException e) {
                throw new RuntimeException("Failed to invoke " + this.a + " with no args", e);
            } catch (InvocationTargetException e2) {
                throw new RuntimeException("Failed to invoke " + this.a + " with no args", e2.getTargetException());
            } catch (IllegalAccessException e3) {
                throw new AssertionError(e3);
            }
        }
    }

    /* compiled from: ConstructorConstructor */
    class i implements e<T> {
        i(b bVar) {
        }

        public T a() {
            return new TreeSet();
        }
    }

    /* compiled from: ConstructorConstructor */
    class j implements e<T> {
        final /* synthetic */ Type a;

        j(b bVar, Type type) {
            this.a = type;
        }

        public T a() {
            Type type = this.a;
            if (type instanceof ParameterizedType) {
                Type type2 = ((ParameterizedType) type).getActualTypeArguments()[0];
                if (type2 instanceof Class) {
                    return EnumSet.noneOf((Class) type2);
                }
                throw new JsonIOException("Invalid EnumSet type: " + this.a.toString());
            }
            throw new JsonIOException("Invalid EnumSet type: " + this.a.toString());
        }
    }

    /* compiled from: ConstructorConstructor */
    class k implements e<T> {
        k(b bVar) {
        }

        public T a() {
            return new LinkedHashSet();
        }
    }

    /* compiled from: ConstructorConstructor */
    class l implements e<T> {
        l(b bVar) {
        }

        public T a() {
            return new ArrayDeque();
        }
    }

    /* compiled from: ConstructorConstructor */
    class m implements e<T> {
        m(b bVar) {
        }

        public T a() {
            return new ArrayList();
        }
    }

    /* compiled from: ConstructorConstructor */
    class n implements e<T> {
        n(b bVar) {
        }

        public T a() {
            return new ConcurrentSkipListMap();
        }
    }

    public b(Map<Type, com.google.gson.e<?>> map) {
        this.a = map;
    }

    private <T> e<T> b(Type type, Class<? super T> cls) {
        return new e(this, cls, type);
    }

    public <T> e<T> a(com.google.gson.r.a<T> aVar) {
        Type b2 = aVar.b();
        Class<? super T> a2 = aVar.a();
        com.google.gson.e eVar = this.a.get(b2);
        if (eVar != null) {
            return new f(this, eVar, b2);
        }
        com.google.gson.e eVar2 = this.a.get(a2);
        if (eVar2 != null) {
            return new g(this, eVar2, b2);
        }
        e<T> a3 = a(a2);
        if (a3 != null) {
            return a3;
        }
        e<T> a4 = a(b2, a2);
        if (a4 != null) {
            return a4;
        }
        return b(b2, a2);
    }

    public String toString() {
        return this.a.toString();
    }

    private <T> e<T> a(Class<? super T> cls) {
        try {
            Constructor<? super T> declaredConstructor = cls.getDeclaredConstructor(new Class[0]);
            if (!declaredConstructor.isAccessible()) {
                this.b.a(declaredConstructor);
            }
            return new h(this, declaredConstructor);
        } catch (NoSuchMethodException unused) {
            return null;
        }
    }

    private <T> e<T> a(Type type, Class<? super T> cls) {
        if (Collection.class.isAssignableFrom(cls)) {
            if (SortedSet.class.isAssignableFrom(cls)) {
                return new i(this);
            }
            if (EnumSet.class.isAssignableFrom(cls)) {
                return new j(this, type);
            }
            if (Set.class.isAssignableFrom(cls)) {
                return new k(this);
            }
            if (Queue.class.isAssignableFrom(cls)) {
                return new l(this);
            }
            return new m(this);
        } else if (!Map.class.isAssignableFrom(cls)) {
            return null;
        } else {
            if (ConcurrentNavigableMap.class.isAssignableFrom(cls)) {
                return new n(this);
            }
            if (ConcurrentMap.class.isAssignableFrom(cls)) {
                return new a(this);
            }
            if (SortedMap.class.isAssignableFrom(cls)) {
                return new C0083b(this);
            }
            if (!(type instanceof ParameterizedType) || String.class.isAssignableFrom(com.google.gson.r.a.a(((ParameterizedType) type).getActualTypeArguments()[0]).a())) {
                return new d(this);
            }
            return new c(this);
        }
    }
}
