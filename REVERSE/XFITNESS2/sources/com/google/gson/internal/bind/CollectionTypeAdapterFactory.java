package com.google.gson.internal.bind;

import com.google.gson.d;
import com.google.gson.internal.C$Gson$Types;
import com.google.gson.internal.b;
import com.google.gson.internal.e;
import com.google.gson.o;
import com.google.gson.p;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import java.lang.reflect.Type;
import java.util.Collection;

public final class CollectionTypeAdapterFactory implements p {
    private final b e;

    public CollectionTypeAdapterFactory(b bVar) {
        this.e = bVar;
    }

    public <T> o<T> a(d dVar, com.google.gson.r.a<T> aVar) {
        Type b = aVar.b();
        Class<? super T> a2 = aVar.a();
        if (!Collection.class.isAssignableFrom(a2)) {
            return null;
        }
        Type a3 = C$Gson$Types.a(b, (Class<?>) a2);
        return new a(dVar, a3, dVar.a(com.google.gson.r.a.a(a3)), this.e.a(aVar));
    }

    private static final class a<E> extends o<Collection<E>> {
        private final o<E> a;
        private final e<? extends Collection<E>> b;

        public a(d dVar, Type type, o<E> oVar, e<? extends Collection<E>> eVar) {
            this.a = new c(dVar, oVar, type);
            this.b = eVar;
        }

        public Collection<E> a(JsonReader jsonReader) {
            if (jsonReader.peek() == JsonToken.NULL) {
                jsonReader.nextNull();
                return null;
            }
            Collection<E> collection = (Collection) this.b.a();
            jsonReader.beginArray();
            while (jsonReader.hasNext()) {
                collection.add(this.a.a(jsonReader));
            }
            jsonReader.endArray();
            return collection;
        }

        public void a(JsonWriter jsonWriter, Collection<E> collection) {
            if (collection == null) {
                jsonWriter.nullValue();
                return;
            }
            jsonWriter.beginArray();
            for (E a2 : collection) {
                this.a.a(jsonWriter, a2);
            }
            jsonWriter.endArray();
        }
    }
}
