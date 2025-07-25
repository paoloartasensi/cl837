package com.google.gson.internal.bind;

import com.google.gson.JsonSyntaxException;
import com.google.gson.d;
import com.google.gson.internal.C$Gson$Types;
import com.google.gson.internal.Excluder;
import com.google.gson.internal.e;
import com.google.gson.internal.g;
import com.google.gson.o;
import com.google.gson.p;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class ReflectiveTypeAdapterFactory implements p {
    private final com.google.gson.internal.b e;

    /* renamed from: f  reason: collision with root package name */
    private final com.google.gson.c f1592f;

    /* renamed from: g  reason: collision with root package name */
    private final Excluder f1593g;

    /* renamed from: h  reason: collision with root package name */
    private final JsonAdapterAnnotationTypeAdapterFactory f1594h;

    /* renamed from: i  reason: collision with root package name */
    private final com.google.gson.internal.j.b f1595i = com.google.gson.internal.j.b.a();

    static abstract class c {
        final String a;
        final boolean b;
        final boolean c;

        protected c(String str, boolean z, boolean z2) {
            this.a = str;
            this.b = z;
            this.c = z2;
        }

        /* access modifiers changed from: package-private */
        public abstract void a(JsonReader jsonReader, Object obj);

        /* access modifiers changed from: package-private */
        public abstract void a(JsonWriter jsonWriter, Object obj);

        /* access modifiers changed from: package-private */
        public abstract boolean a(Object obj);
    }

    public ReflectiveTypeAdapterFactory(com.google.gson.internal.b bVar, com.google.gson.c cVar, Excluder excluder, JsonAdapterAnnotationTypeAdapterFactory jsonAdapterAnnotationTypeAdapterFactory) {
        this.e = bVar;
        this.f1592f = cVar;
        this.f1593g = excluder;
        this.f1594h = jsonAdapterAnnotationTypeAdapterFactory;
    }

    public boolean a(Field field, boolean z) {
        return a(field, z, this.f1593g);
    }

    static boolean a(Field field, boolean z, Excluder excluder) {
        return !excluder.a(field.getType(), z) && !excluder.a(field, z);
    }

    private List<String> a(Field field) {
        com.google.gson.q.c cVar = (com.google.gson.q.c) field.getAnnotation(com.google.gson.q.c.class);
        if (cVar == null) {
            return Collections.singletonList(this.f1592f.translateName(field));
        }
        String value = cVar.value();
        String[] alternate = cVar.alternate();
        if (alternate.length == 0) {
            return Collections.singletonList(value);
        }
        ArrayList arrayList = new ArrayList(alternate.length + 1);
        arrayList.add(value);
        for (String add : alternate) {
            arrayList.add(add);
        }
        return arrayList;
    }

    class a extends c {
        final /* synthetic */ Field d;
        final /* synthetic */ boolean e;

        /* renamed from: f  reason: collision with root package name */
        final /* synthetic */ o f1596f;

        /* renamed from: g  reason: collision with root package name */
        final /* synthetic */ d f1597g;

        /* renamed from: h  reason: collision with root package name */
        final /* synthetic */ com.google.gson.r.a f1598h;

        /* renamed from: i  reason: collision with root package name */
        final /* synthetic */ boolean f1599i;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        a(ReflectiveTypeAdapterFactory reflectiveTypeAdapterFactory, String str, boolean z, boolean z2, Field field, boolean z3, o oVar, d dVar, com.google.gson.r.a aVar, boolean z4) {
            super(str, z, z2);
            this.d = field;
            this.e = z3;
            this.f1596f = oVar;
            this.f1597g = dVar;
            this.f1598h = aVar;
            this.f1599i = z4;
        }

        /* access modifiers changed from: package-private */
        public void a(JsonWriter jsonWriter, Object obj) {
            o oVar;
            Object obj2 = this.d.get(obj);
            if (this.e) {
                oVar = this.f1596f;
            } else {
                oVar = new c(this.f1597g, this.f1596f, this.f1598h.b());
            }
            oVar.a(jsonWriter, obj2);
        }

        /* access modifiers changed from: package-private */
        public void a(JsonReader jsonReader, Object obj) {
            Object a = this.f1596f.a(jsonReader);
            if (a != null || !this.f1599i) {
                this.d.set(obj, a);
            }
        }

        public boolean a(Object obj) {
            if (this.b && this.d.get(obj) != obj) {
                return true;
            }
            return false;
        }
    }

    public static final class b<T> extends o<T> {
        private final e<T> a;
        private final Map<String, c> b;

        b(e<T> eVar, Map<String, c> map) {
            this.a = eVar;
            this.b = map;
        }

        public T a(JsonReader jsonReader) {
            if (jsonReader.peek() == JsonToken.NULL) {
                jsonReader.nextNull();
                return null;
            }
            T a2 = this.a.a();
            try {
                jsonReader.beginObject();
                while (jsonReader.hasNext()) {
                    c cVar = this.b.get(jsonReader.nextName());
                    if (cVar != null) {
                        if (cVar.c) {
                            cVar.a(jsonReader, (Object) a2);
                        }
                    }
                    jsonReader.skipValue();
                }
                jsonReader.endObject();
                return a2;
            } catch (IllegalStateException e) {
                throw new JsonSyntaxException((Throwable) e);
            } catch (IllegalAccessException e2) {
                throw new AssertionError(e2);
            }
        }

        public void a(JsonWriter jsonWriter, T t) {
            if (t == null) {
                jsonWriter.nullValue();
                return;
            }
            jsonWriter.beginObject();
            try {
                for (c next : this.b.values()) {
                    if (next.a(t)) {
                        jsonWriter.name(next.a);
                        next.a(jsonWriter, (Object) t);
                    }
                }
                jsonWriter.endObject();
            } catch (IllegalAccessException e) {
                throw new AssertionError(e);
            }
        }
    }

    public <T> o<T> a(d dVar, com.google.gson.r.a<T> aVar) {
        Class<? super T> a2 = aVar.a();
        if (!Object.class.isAssignableFrom(a2)) {
            return null;
        }
        return new b(this.e.a(aVar), a(dVar, (com.google.gson.r.a<?>) aVar, (Class<?>) a2));
    }

    private c a(d dVar, Field field, String str, com.google.gson.r.a<?> aVar, boolean z, boolean z2) {
        d dVar2 = dVar;
        com.google.gson.r.a<?> aVar2 = aVar;
        boolean a2 = g.a((Type) aVar.a());
        Field field2 = field;
        com.google.gson.q.b bVar = (com.google.gson.q.b) field.getAnnotation(com.google.gson.q.b.class);
        o<?> a3 = bVar != null ? this.f1594h.a(this.e, dVar, aVar2, bVar) : null;
        boolean z3 = a3 != null;
        if (a3 == null) {
            a3 = dVar.a(aVar2);
        }
        return new a(this, str, z, z2, field, z3, a3, dVar, aVar, a2);
    }

    private Map<String, c> a(d dVar, com.google.gson.r.a<?> aVar, Class<?> cls) {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        if (cls.isInterface()) {
            return linkedHashMap;
        }
        Type b2 = aVar.b();
        com.google.gson.r.a<?> aVar2 = aVar;
        Class<? super Object> cls2 = cls;
        while (cls2 != Object.class) {
            Field[] declaredFields = cls2.getDeclaredFields();
            int length = declaredFields.length;
            boolean z = false;
            int i2 = 0;
            while (i2 < length) {
                Field field = declaredFields[i2];
                boolean a2 = a(field, true);
                boolean a3 = a(field, z);
                if (a2 || a3) {
                    this.f1595i.a(field);
                    Type a4 = C$Gson$Types.a(aVar2.b(), (Class<?>) cls2, field.getGenericType());
                    List<String> a5 = a(field);
                    int size = a5.size();
                    c cVar = null;
                    int i3 = 0;
                    while (i3 < size) {
                        String str = a5.get(i3);
                        boolean z2 = i3 != 0 ? false : a2;
                        String str2 = str;
                        int i4 = i3;
                        c cVar2 = cVar;
                        int i5 = size;
                        List<String> list = a5;
                        Field field2 = field;
                        cVar = cVar2 == null ? (c) linkedHashMap.put(str2, a(dVar, field, str2, com.google.gson.r.a.a(a4), z2, a3)) : cVar2;
                        i3 = i4 + 1;
                        a2 = z2;
                        a5 = list;
                        size = i5;
                        field = field2;
                    }
                    c cVar3 = cVar;
                    if (cVar3 != null) {
                        throw new IllegalArgumentException(b2 + " declares multiple JSON fields named " + cVar3.a);
                    }
                }
                i2++;
                z = false;
            }
            aVar2 = com.google.gson.r.a.a(C$Gson$Types.a(aVar2.b(), (Class<?>) cls2, cls2.getGenericSuperclass()));
            cls2 = aVar2.a();
        }
        return linkedHashMap;
    }
}
