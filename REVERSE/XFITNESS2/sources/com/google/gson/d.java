package com.google.gson;

import com.google.gson.internal.Excluder;
import com.google.gson.internal.bind.ArrayTypeAdapter;
import com.google.gson.internal.bind.CollectionTypeAdapterFactory;
import com.google.gson.internal.bind.DateTypeAdapter;
import com.google.gson.internal.bind.JsonAdapterAnnotationTypeAdapterFactory;
import com.google.gson.internal.bind.MapTypeAdapterFactory;
import com.google.gson.internal.bind.ObjectTypeAdapter;
import com.google.gson.internal.bind.ReflectiveTypeAdapterFactory;
import com.google.gson.internal.bind.SqlDateTypeAdapter;
import com.google.gson.internal.bind.TimeTypeAdapter;
import com.google.gson.internal.bind.TypeAdapters;
import com.google.gson.internal.g;
import com.google.gson.internal.h;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import com.google.gson.stream.MalformedJsonException;
import java.io.EOFException;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicLongArray;

/* compiled from: Gson */
public final class d {
    private static final com.google.gson.r.a<?> k = com.google.gson.r.a.a(Object.class);
    private final ThreadLocal<Map<com.google.gson.r.a<?>, f<?>>> a;
    private final Map<com.google.gson.r.a<?>, o<?>> b;
    private final com.google.gson.internal.b c;
    private final JsonAdapterAnnotationTypeAdapterFactory d;
    final List<p> e;

    /* renamed from: f  reason: collision with root package name */
    final boolean f1572f;

    /* renamed from: g  reason: collision with root package name */
    final boolean f1573g;

    /* renamed from: h  reason: collision with root package name */
    final boolean f1574h;

    /* renamed from: i  reason: collision with root package name */
    final boolean f1575i;

    /* renamed from: j  reason: collision with root package name */
    final boolean f1576j;

    public d() {
        this(Excluder.k, FieldNamingPolicy.IDENTITY, Collections.emptyMap(), false, false, false, true, false, false, false, LongSerializationPolicy.DEFAULT, (String) null, 2, 2, Collections.emptyList(), Collections.emptyList(), Collections.emptyList());
    }

    private o<Number> a(boolean z) {
        if (z) {
            return TypeAdapters.v;
        }
        return new a(this);
    }

    private o<Number> b(boolean z) {
        if (z) {
            return TypeAdapters.u;
        }
        return new b(this);
    }

    public String toString() {
        return "{serializeNulls:" + this.f1572f + ",factories:" + this.e + ",instanceCreators:" + this.c + "}";
    }

    /* compiled from: Gson */
    class a extends o<Number> {
        a(d dVar) {
        }

        public Double a(JsonReader jsonReader) {
            if (jsonReader.peek() != JsonToken.NULL) {
                return Double.valueOf(jsonReader.nextDouble());
            }
            jsonReader.nextNull();
            return null;
        }

        public void a(JsonWriter jsonWriter, Number number) {
            if (number == null) {
                jsonWriter.nullValue();
                return;
            }
            d.a(number.doubleValue());
            jsonWriter.value(number);
        }
    }

    /* compiled from: Gson */
    class b extends o<Number> {
        b(d dVar) {
        }

        public Float a(JsonReader jsonReader) {
            if (jsonReader.peek() != JsonToken.NULL) {
                return Float.valueOf((float) jsonReader.nextDouble());
            }
            jsonReader.nextNull();
            return null;
        }

        public void a(JsonWriter jsonWriter, Number number) {
            if (number == null) {
                jsonWriter.nullValue();
                return;
            }
            d.a((double) number.floatValue());
            jsonWriter.value(number);
        }
    }

    /* compiled from: Gson */
    class c extends o<Number> {
        c() {
        }

        public Number a(JsonReader jsonReader) {
            if (jsonReader.peek() != JsonToken.NULL) {
                return Long.valueOf(jsonReader.nextLong());
            }
            jsonReader.nextNull();
            return null;
        }

        public void a(JsonWriter jsonWriter, Number number) {
            if (number == null) {
                jsonWriter.nullValue();
            } else {
                jsonWriter.value(number.toString());
            }
        }
    }

    /* renamed from: com.google.gson.d$d  reason: collision with other inner class name */
    /* compiled from: Gson */
    class C0082d extends o<AtomicLong> {
        final /* synthetic */ o a;

        C0082d(o oVar) {
            this.a = oVar;
        }

        public void a(JsonWriter jsonWriter, AtomicLong atomicLong) {
            this.a.a(jsonWriter, Long.valueOf(atomicLong.get()));
        }

        public AtomicLong a(JsonReader jsonReader) {
            return new AtomicLong(((Number) this.a.a(jsonReader)).longValue());
        }
    }

    /* compiled from: Gson */
    class e extends o<AtomicLongArray> {
        final /* synthetic */ o a;

        e(o oVar) {
            this.a = oVar;
        }

        public void a(JsonWriter jsonWriter, AtomicLongArray atomicLongArray) {
            jsonWriter.beginArray();
            int length = atomicLongArray.length();
            for (int i2 = 0; i2 < length; i2++) {
                this.a.a(jsonWriter, Long.valueOf(atomicLongArray.get(i2)));
            }
            jsonWriter.endArray();
        }

        public AtomicLongArray a(JsonReader jsonReader) {
            ArrayList arrayList = new ArrayList();
            jsonReader.beginArray();
            while (jsonReader.hasNext()) {
                arrayList.add(Long.valueOf(((Number) this.a.a(jsonReader)).longValue()));
            }
            jsonReader.endArray();
            int size = arrayList.size();
            AtomicLongArray atomicLongArray = new AtomicLongArray(size);
            for (int i2 = 0; i2 < size; i2++) {
                atomicLongArray.set(i2, ((Long) arrayList.get(i2)).longValue());
            }
            return atomicLongArray;
        }
    }

    /* compiled from: Gson */
    static class f<T> extends o<T> {
        private o<T> a;

        f() {
        }

        public void a(o<T> oVar) {
            if (this.a == null) {
                this.a = oVar;
                return;
            }
            throw new AssertionError();
        }

        public T a(JsonReader jsonReader) {
            o<T> oVar = this.a;
            if (oVar != null) {
                return oVar.a(jsonReader);
            }
            throw new IllegalStateException();
        }

        public void a(JsonWriter jsonWriter, T t) {
            o<T> oVar = this.a;
            if (oVar != null) {
                oVar.a(jsonWriter, t);
                return;
            }
            throw new IllegalStateException();
        }
    }

    static void a(double d2) {
        if (Double.isNaN(d2) || Double.isInfinite(d2)) {
            throw new IllegalArgumentException(d2 + " is not a valid double value as per JSON specification. To override this behavior, use GsonBuilder.serializeSpecialFloatingPointValues() method.");
        }
    }

    private static o<AtomicLongArray> b(o<Number> oVar) {
        return new e(oVar).a();
    }

    private static o<Number> a(LongSerializationPolicy longSerializationPolicy) {
        if (longSerializationPolicy == LongSerializationPolicy.DEFAULT) {
            return TypeAdapters.t;
        }
        return new c();
    }

    d(Excluder excluder, c cVar, Map<Type, e<?>> map, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, boolean z7, LongSerializationPolicy longSerializationPolicy, String str, int i2, int i3, List<p> list, List<p> list2, List<p> list3) {
        Excluder excluder2 = excluder;
        boolean z8 = z7;
        this.a = new ThreadLocal<>();
        this.b = new ConcurrentHashMap();
        Map<Type, e<?>> map2 = map;
        this.c = new com.google.gson.internal.b(map);
        this.f1572f = z;
        this.f1573g = z3;
        this.f1574h = z4;
        this.f1575i = z5;
        this.f1576j = z6;
        ArrayList arrayList = new ArrayList();
        arrayList.add(TypeAdapters.Y);
        arrayList.add(ObjectTypeAdapter.b);
        arrayList.add(excluder);
        arrayList.addAll(list3);
        arrayList.add(TypeAdapters.D);
        arrayList.add(TypeAdapters.m);
        arrayList.add(TypeAdapters.f1607g);
        arrayList.add(TypeAdapters.f1609i);
        arrayList.add(TypeAdapters.k);
        o<Number> a2 = a(longSerializationPolicy);
        arrayList.add(TypeAdapters.a(Long.TYPE, Long.class, a2));
        arrayList.add(TypeAdapters.a(Double.TYPE, Double.class, a(z8)));
        arrayList.add(TypeAdapters.a(Float.TYPE, Float.class, b(z8)));
        arrayList.add(TypeAdapters.x);
        arrayList.add(TypeAdapters.o);
        arrayList.add(TypeAdapters.q);
        arrayList.add(TypeAdapters.a(AtomicLong.class, a(a2)));
        arrayList.add(TypeAdapters.a(AtomicLongArray.class, b(a2)));
        arrayList.add(TypeAdapters.s);
        arrayList.add(TypeAdapters.z);
        arrayList.add(TypeAdapters.F);
        arrayList.add(TypeAdapters.H);
        arrayList.add(TypeAdapters.a(BigDecimal.class, TypeAdapters.B));
        arrayList.add(TypeAdapters.a(BigInteger.class, TypeAdapters.C));
        arrayList.add(TypeAdapters.J);
        arrayList.add(TypeAdapters.L);
        arrayList.add(TypeAdapters.P);
        arrayList.add(TypeAdapters.R);
        arrayList.add(TypeAdapters.W);
        arrayList.add(TypeAdapters.N);
        arrayList.add(TypeAdapters.d);
        arrayList.add(DateTypeAdapter.b);
        arrayList.add(TypeAdapters.U);
        arrayList.add(TimeTypeAdapter.b);
        arrayList.add(SqlDateTypeAdapter.b);
        arrayList.add(TypeAdapters.S);
        arrayList.add(ArrayTypeAdapter.c);
        arrayList.add(TypeAdapters.b);
        arrayList.add(new CollectionTypeAdapterFactory(this.c));
        boolean z9 = z2;
        arrayList.add(new MapTypeAdapterFactory(this.c, z2));
        JsonAdapterAnnotationTypeAdapterFactory jsonAdapterAnnotationTypeAdapterFactory = new JsonAdapterAnnotationTypeAdapterFactory(this.c);
        this.d = jsonAdapterAnnotationTypeAdapterFactory;
        arrayList.add(jsonAdapterAnnotationTypeAdapterFactory);
        arrayList.add(TypeAdapters.Z);
        c cVar2 = cVar;
        arrayList.add(new ReflectiveTypeAdapterFactory(this.c, cVar, excluder, this.d));
        this.e = Collections.unmodifiableList(arrayList);
    }

    private static o<AtomicLong> a(o<Number> oVar) {
        return new C0082d(oVar).a();
    }

    public <T> o<T> a(com.google.gson.r.a<T> aVar) {
        o<T> oVar = this.b.get(aVar == null ? k : aVar);
        if (oVar != null) {
            return oVar;
        }
        Map map = this.a.get();
        boolean z = false;
        if (map == null) {
            map = new HashMap();
            this.a.set(map);
            z = true;
        }
        f fVar = (f) map.get(aVar);
        if (fVar != null) {
            return fVar;
        }
        try {
            f fVar2 = new f();
            map.put(aVar, fVar2);
            for (p a2 : this.e) {
                o<T> a3 = a2.a(this, aVar);
                if (a3 != null) {
                    fVar2.a(a3);
                    this.b.put(aVar, a3);
                    return a3;
                }
            }
            throw new IllegalArgumentException("GSON (2.8.6) cannot handle " + aVar);
        } finally {
            map.remove(aVar);
            if (z) {
                this.a.remove();
            }
        }
    }

    public <T> o<T> a(p pVar, com.google.gson.r.a<T> aVar) {
        if (!this.e.contains(pVar)) {
            pVar = this.d;
        }
        boolean z = false;
        for (p next : this.e) {
            if (z) {
                o<T> a2 = next.a(this, aVar);
                if (a2 != null) {
                    return a2;
                }
            } else if (next == pVar) {
                z = true;
            }
        }
        throw new IllegalArgumentException("GSON cannot serialize " + aVar);
    }

    public <T> o<T> a(Class<T> cls) {
        return a(com.google.gson.r.a.a(cls));
    }

    public String a(Object obj) {
        if (obj == null) {
            return a((i) j.a);
        }
        return a(obj, (Type) obj.getClass());
    }

    public String a(Object obj, Type type) {
        StringWriter stringWriter = new StringWriter();
        a(obj, type, (Appendable) stringWriter);
        return stringWriter.toString();
    }

    public void a(Object obj, Type type, Appendable appendable) {
        try {
            a(obj, type, a(h.a(appendable)));
        } catch (IOException e2) {
            throw new JsonIOException((Throwable) e2);
        }
    }

    public void a(Object obj, Type type, JsonWriter jsonWriter) {
        o<?> a2 = a(com.google.gson.r.a.a(type));
        boolean isLenient = jsonWriter.isLenient();
        jsonWriter.setLenient(true);
        boolean isHtmlSafe = jsonWriter.isHtmlSafe();
        jsonWriter.setHtmlSafe(this.f1574h);
        boolean serializeNulls = jsonWriter.getSerializeNulls();
        jsonWriter.setSerializeNulls(this.f1572f);
        try {
            a2.a(jsonWriter, obj);
            jsonWriter.setLenient(isLenient);
            jsonWriter.setHtmlSafe(isHtmlSafe);
            jsonWriter.setSerializeNulls(serializeNulls);
        } catch (IOException e2) {
            throw new JsonIOException((Throwable) e2);
        } catch (AssertionError e3) {
            AssertionError assertionError = new AssertionError("AssertionError (GSON 2.8.6): " + e3.getMessage());
            assertionError.initCause(e3);
            throw assertionError;
        } catch (Throwable th) {
            jsonWriter.setLenient(isLenient);
            jsonWriter.setHtmlSafe(isHtmlSafe);
            jsonWriter.setSerializeNulls(serializeNulls);
            throw th;
        }
    }

    public String a(i iVar) {
        StringWriter stringWriter = new StringWriter();
        a(iVar, (Appendable) stringWriter);
        return stringWriter.toString();
    }

    public void a(i iVar, Appendable appendable) {
        try {
            a(iVar, a(h.a(appendable)));
        } catch (IOException e2) {
            throw new JsonIOException((Throwable) e2);
        }
    }

    public JsonWriter a(Writer writer) {
        if (this.f1573g) {
            writer.write(")]}'\n");
        }
        JsonWriter jsonWriter = new JsonWriter(writer);
        if (this.f1575i) {
            jsonWriter.setIndent("  ");
        }
        jsonWriter.setSerializeNulls(this.f1572f);
        return jsonWriter;
    }

    public JsonReader a(Reader reader) {
        JsonReader jsonReader = new JsonReader(reader);
        jsonReader.setLenient(this.f1576j);
        return jsonReader;
    }

    public void a(i iVar, JsonWriter jsonWriter) {
        boolean isLenient = jsonWriter.isLenient();
        jsonWriter.setLenient(true);
        boolean isHtmlSafe = jsonWriter.isHtmlSafe();
        jsonWriter.setHtmlSafe(this.f1574h);
        boolean serializeNulls = jsonWriter.getSerializeNulls();
        jsonWriter.setSerializeNulls(this.f1572f);
        try {
            h.a(iVar, jsonWriter);
            jsonWriter.setLenient(isLenient);
            jsonWriter.setHtmlSafe(isHtmlSafe);
            jsonWriter.setSerializeNulls(serializeNulls);
        } catch (IOException e2) {
            throw new JsonIOException((Throwable) e2);
        } catch (AssertionError e3) {
            AssertionError assertionError = new AssertionError("AssertionError (GSON 2.8.6): " + e3.getMessage());
            assertionError.initCause(e3);
            throw assertionError;
        } catch (Throwable th) {
            jsonWriter.setLenient(isLenient);
            jsonWriter.setHtmlSafe(isHtmlSafe);
            jsonWriter.setSerializeNulls(serializeNulls);
            throw th;
        }
    }

    public <T> T a(String str, Class<T> cls) {
        return g.a(cls).cast(a(str, (Type) cls));
    }

    public <T> T a(String str, Type type) {
        if (str == null) {
            return null;
        }
        return a((Reader) new StringReader(str), type);
    }

    public <T> T a(Reader reader, Type type) {
        JsonReader a2 = a(reader);
        T a3 = a(a2, type);
        a((Object) a3, a2);
        return a3;
    }

    private static void a(Object obj, JsonReader jsonReader) {
        if (obj != null) {
            try {
                if (jsonReader.peek() != JsonToken.END_DOCUMENT) {
                    throw new JsonIOException("JSON document was not fully consumed.");
                }
            } catch (MalformedJsonException e2) {
                throw new JsonSyntaxException((Throwable) e2);
            } catch (IOException e3) {
                throw new JsonIOException((Throwable) e3);
            }
        }
    }

    public <T> T a(JsonReader jsonReader, Type type) {
        boolean isLenient = jsonReader.isLenient();
        jsonReader.setLenient(true);
        try {
            jsonReader.peek();
            T a2 = a(com.google.gson.r.a.a(type)).a(jsonReader);
            jsonReader.setLenient(isLenient);
            return a2;
        } catch (EOFException e2) {
            if (1 != 0) {
                jsonReader.setLenient(isLenient);
                return null;
            }
            throw new JsonSyntaxException((Throwable) e2);
        } catch (IllegalStateException e3) {
            throw new JsonSyntaxException((Throwable) e3);
        } catch (IOException e4) {
            throw new JsonSyntaxException((Throwable) e4);
        } catch (AssertionError e5) {
            AssertionError assertionError = new AssertionError("AssertionError (GSON 2.8.6): " + e5.getMessage());
            assertionError.initCause(e5);
            throw assertionError;
        } catch (Throwable th) {
            jsonReader.setLenient(isLenient);
            throw th;
        }
    }
}
