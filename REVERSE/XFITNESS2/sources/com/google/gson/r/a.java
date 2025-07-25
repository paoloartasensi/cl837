package com.google.gson.r;

import com.google.gson.internal.C$Gson$Types;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/* compiled from: TypeToken */
public class a<T> {
    final Class<? super T> a;
    final Type b;
    final int c = this.b.hashCode();

    protected a() {
        Type b2 = b(getClass());
        this.b = b2;
        this.a = C$Gson$Types.e(b2);
    }

    static Type b(Class<?> cls) {
        Type genericSuperclass = cls.getGenericSuperclass();
        if (!(genericSuperclass instanceof Class)) {
            return C$Gson$Types.b(((ParameterizedType) genericSuperclass).getActualTypeArguments()[0]);
        }
        throw new RuntimeException("Missing type parameter.");
    }

    public final Class<? super T> a() {
        return this.a;
    }

    public final boolean equals(Object obj) {
        return (obj instanceof a) && C$Gson$Types.a(this.b, ((a) obj).b);
    }

    public final int hashCode() {
        return this.c;
    }

    public final String toString() {
        return C$Gson$Types.h(this.b);
    }

    public static a<?> a(Type type) {
        return new a<>(type);
    }

    public static <T> a<T> a(Class<T> cls) {
        return new a<>(cls);
    }

    a(Type type) {
        com.google.gson.internal.a.a(type);
        Type b2 = C$Gson$Types.b(type);
        this.b = b2;
        this.a = C$Gson$Types.e(b2);
    }

    public final Type b() {
        return this.b;
    }
}
