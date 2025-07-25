package com.google.gson;

import com.google.gson.internal.bind.b;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;

/* compiled from: TypeAdapter */
public abstract class o<T> {
    public final o<T> a() {
        return new a();
    }

    public abstract T a(JsonReader jsonReader);

    public abstract void a(JsonWriter jsonWriter, T t);

    /* compiled from: TypeAdapter */
    class a extends o<T> {
        a() {
        }

        public void a(JsonWriter jsonWriter, T t) {
            if (t == null) {
                jsonWriter.nullValue();
            } else {
                o.this.a(jsonWriter, t);
            }
        }

        public T a(JsonReader jsonReader) {
            if (jsonReader.peek() != JsonToken.NULL) {
                return o.this.a(jsonReader);
            }
            jsonReader.nextNull();
            return null;
        }
    }

    public final i a(T t) {
        try {
            b bVar = new b();
            a(bVar, t);
            return bVar.a();
        } catch (IOException e) {
            throw new JsonIOException((Throwable) e);
        }
    }
}
