package com.google.gson.internal.bind;

import com.google.gson.d;
import com.google.gson.internal.C$Gson$Types;
import com.google.gson.o;
import com.google.gson.p;
import com.google.gson.r.a;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Type;
import java.util.ArrayList;

public final class ArrayTypeAdapter<E> extends o<Object> {
    public static final p c = new p() {
        public <T> o<T> a(d dVar, a<T> aVar) {
            Type b = aVar.b();
            if (!(b instanceof GenericArrayType) && (!(b instanceof Class) || !((Class) b).isArray())) {
                return null;
            }
            Type d = C$Gson$Types.d(b);
            return new ArrayTypeAdapter(dVar, dVar.a(a.a(d)), C$Gson$Types.e(d));
        }
    };
    private final Class<E> a;
    private final o<E> b;

    public ArrayTypeAdapter(d dVar, o<E> oVar, Class<E> cls) {
        this.b = new c(dVar, oVar, cls);
        this.a = cls;
    }

    public Object a(JsonReader jsonReader) {
        if (jsonReader.peek() == JsonToken.NULL) {
            jsonReader.nextNull();
            return null;
        }
        ArrayList arrayList = new ArrayList();
        jsonReader.beginArray();
        while (jsonReader.hasNext()) {
            arrayList.add(this.b.a(jsonReader));
        }
        jsonReader.endArray();
        int size = arrayList.size();
        Object newInstance = Array.newInstance(this.a, size);
        for (int i2 = 0; i2 < size; i2++) {
            Array.set(newInstance, i2, arrayList.get(i2));
        }
        return newInstance;
    }

    public void a(JsonWriter jsonWriter, Object obj) {
        if (obj == null) {
            jsonWriter.nullValue();
            return;
        }
        jsonWriter.beginArray();
        int length = Array.getLength(obj);
        for (int i2 = 0; i2 < length; i2++) {
            this.b.a(jsonWriter, Array.get(obj, i2));
        }
        jsonWriter.endArray();
    }
}
