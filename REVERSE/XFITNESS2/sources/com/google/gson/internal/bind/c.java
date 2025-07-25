package com.google.gson.internal.bind;

import com.google.gson.d;
import com.google.gson.internal.bind.ReflectiveTypeAdapterFactory;
import com.google.gson.o;
import com.google.gson.r.a;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;

/* compiled from: TypeAdapterRuntimeTypeWrapper */
final class c<T> extends o<T> {
    private final d a;
    private final o<T> b;
    private final Type c;

    c(d dVar, o<T> oVar, Type type) {
        this.a = dVar;
        this.b = oVar;
        this.c = type;
    }

    public T a(JsonReader jsonReader) {
        return this.b.a(jsonReader);
    }

    public void a(JsonWriter jsonWriter, T t) {
        o<T> oVar = this.b;
        Type a2 = a(this.c, (Object) t);
        if (a2 != this.c) {
            oVar = this.a.a(a.a(a2));
            if (oVar instanceof ReflectiveTypeAdapterFactory.b) {
                o<T> oVar2 = this.b;
                if (!(oVar2 instanceof ReflectiveTypeAdapterFactory.b)) {
                    oVar = oVar2;
                }
            }
        }
        oVar.a(jsonWriter, t);
    }

    private Type a(Type type, Object obj) {
        if (obj != null) {
            return (type == Object.class || (type instanceof TypeVariable) || (type instanceof Class)) ? obj.getClass() : type;
        }
        return type;
    }
}
