package com.google.gson.internal.bind;

import com.google.gson.JsonSyntaxException;
import com.google.gson.d;
import com.google.gson.i;
import com.google.gson.internal.C$Gson$Types;
import com.google.gson.internal.b;
import com.google.gson.internal.e;
import com.google.gson.internal.h;
import com.google.gson.l;
import com.google.gson.o;
import com.google.gson.p;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Map;

public final class MapTypeAdapterFactory implements p {
    private final b e;

    /* renamed from: f  reason: collision with root package name */
    final boolean f1591f;

    public MapTypeAdapterFactory(b bVar, boolean z) {
        this.e = bVar;
        this.f1591f = z;
    }

    public <T> o<T> a(d dVar, com.google.gson.r.a<T> aVar) {
        Type b = aVar.b();
        if (!Map.class.isAssignableFrom(aVar.a())) {
            return null;
        }
        Type[] b2 = C$Gson$Types.b(b, C$Gson$Types.e(b));
        return new a(dVar, b2[0], a(dVar, b2[0]), b2[1], dVar.a(com.google.gson.r.a.a(b2[1])), this.e.a(aVar));
    }

    private final class a<K, V> extends o<Map<K, V>> {
        private final o<K> a;
        private final o<V> b;
        private final e<? extends Map<K, V>> c;

        public a(d dVar, Type type, o<K> oVar, Type type2, o<V> oVar2, e<? extends Map<K, V>> eVar) {
            this.a = new c(dVar, oVar, type);
            this.b = new c(dVar, oVar2, type2);
            this.c = eVar;
        }

        public Map<K, V> a(JsonReader jsonReader) {
            JsonToken peek = jsonReader.peek();
            if (peek == JsonToken.NULL) {
                jsonReader.nextNull();
                return null;
            }
            Map<K, V> map = (Map) this.c.a();
            if (peek == JsonToken.BEGIN_ARRAY) {
                jsonReader.beginArray();
                while (jsonReader.hasNext()) {
                    jsonReader.beginArray();
                    K a2 = this.a.a(jsonReader);
                    if (map.put(a2, this.b.a(jsonReader)) == null) {
                        jsonReader.endArray();
                    } else {
                        throw new JsonSyntaxException("duplicate key: " + a2);
                    }
                }
                jsonReader.endArray();
            } else {
                jsonReader.beginObject();
                while (jsonReader.hasNext()) {
                    com.google.gson.internal.d.INSTANCE.promoteNameToValue(jsonReader);
                    K a3 = this.a.a(jsonReader);
                    if (map.put(a3, this.b.a(jsonReader)) != null) {
                        throw new JsonSyntaxException("duplicate key: " + a3);
                    }
                }
                jsonReader.endObject();
            }
            return map;
        }

        public void a(JsonWriter jsonWriter, Map<K, V> map) {
            if (map == null) {
                jsonWriter.nullValue();
            } else if (!MapTypeAdapterFactory.this.f1591f) {
                jsonWriter.beginObject();
                for (Map.Entry next : map.entrySet()) {
                    jsonWriter.name(String.valueOf(next.getKey()));
                    this.b.a(jsonWriter, next.getValue());
                }
                jsonWriter.endObject();
            } else {
                ArrayList arrayList = new ArrayList(map.size());
                ArrayList arrayList2 = new ArrayList(map.size());
                int i2 = 0;
                boolean z = false;
                for (Map.Entry next2 : map.entrySet()) {
                    i a2 = this.a.a(next2.getKey());
                    arrayList.add(a2);
                    arrayList2.add(next2.getValue());
                    z |= a2.d() || a2.f();
                }
                if (z) {
                    jsonWriter.beginArray();
                    int size = arrayList.size();
                    while (i2 < size) {
                        jsonWriter.beginArray();
                        h.a((i) arrayList.get(i2), jsonWriter);
                        this.b.a(jsonWriter, arrayList2.get(i2));
                        jsonWriter.endArray();
                        i2++;
                    }
                    jsonWriter.endArray();
                    return;
                }
                jsonWriter.beginObject();
                int size2 = arrayList.size();
                while (i2 < size2) {
                    jsonWriter.name(a((i) arrayList.get(i2)));
                    this.b.a(jsonWriter, arrayList2.get(i2));
                    i2++;
                }
                jsonWriter.endObject();
            }
        }

        private String a(i iVar) {
            if (iVar.g()) {
                l c2 = iVar.c();
                if (c2.o()) {
                    return String.valueOf(c2.l());
                }
                if (c2.n()) {
                    return Boolean.toString(c2.h());
                }
                if (c2.p()) {
                    return c2.m();
                }
                throw new AssertionError();
            } else if (iVar.e()) {
                return "null";
            } else {
                throw new AssertionError();
            }
        }
    }

    private o<?> a(d dVar, Type type) {
        if (type == Boolean.TYPE || type == Boolean.class) {
            return TypeAdapters.f1606f;
        }
        return dVar.a(com.google.gson.r.a.a(type));
    }
}
