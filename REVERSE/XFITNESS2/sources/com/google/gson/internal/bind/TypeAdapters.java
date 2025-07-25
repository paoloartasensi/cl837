package com.google.gson.internal.bind;

import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.internal.LazilyParsedNumber;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Calendar;
import java.util.Currency;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerArray;

public final class TypeAdapters {
    public static final com.google.gson.o<String> A = new g();
    public static final com.google.gson.o<BigDecimal> B = new h();
    public static final com.google.gson.o<BigInteger> C = new i();
    public static final com.google.gson.p D = a(String.class, A);
    public static final com.google.gson.o<StringBuilder> E;
    public static final com.google.gson.p F;
    public static final com.google.gson.o<StringBuffer> G;
    public static final com.google.gson.p H;
    public static final com.google.gson.o<URL> I;
    public static final com.google.gson.p J;
    public static final com.google.gson.o<URI> K;
    public static final com.google.gson.p L;
    public static final com.google.gson.o<InetAddress> M;
    public static final com.google.gson.p N;
    public static final com.google.gson.o<UUID> O;
    public static final com.google.gson.p P;
    public static final com.google.gson.o<Currency> Q;
    public static final com.google.gson.p R;
    public static final com.google.gson.p S = new com.google.gson.p() {
        public <T> com.google.gson.o<T> a(com.google.gson.d dVar, com.google.gson.r.a<T> aVar) {
            if (aVar.a() != Timestamp.class) {
                return null;
            }
            return new a(this, dVar.a(Date.class));
        }

        /* renamed from: com.google.gson.internal.bind.TypeAdapters$26$a */
        class a extends com.google.gson.o<Timestamp> {
            final /* synthetic */ com.google.gson.o a;

            a(AnonymousClass26 r1, com.google.gson.o oVar) {
                this.a = oVar;
            }

            public Timestamp a(JsonReader jsonReader) {
                Date date = (Date) this.a.a(jsonReader);
                if (date != null) {
                    return new Timestamp(date.getTime());
                }
                return null;
            }

            public void a(JsonWriter jsonWriter, Timestamp timestamp) {
                this.a.a(jsonWriter, timestamp);
            }
        }
    };
    public static final com.google.gson.o<Calendar> T;
    public static final com.google.gson.p U;
    public static final com.google.gson.o<Locale> V;
    public static final com.google.gson.p W;
    public static final com.google.gson.o<com.google.gson.i> X;
    public static final com.google.gson.p Y;
    public static final com.google.gson.p Z = new com.google.gson.p() {
        /* JADX WARNING: type inference failed for: r2v0, types: [com.google.gson.r.a, com.google.gson.r.a<T>] */
        /* JADX WARNING: Unknown variable types count: 1 */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public <T> com.google.gson.o<T> a(com.google.gson.d r1, com.google.gson.r.a<T> r2) {
            /*
                r0 = this;
                java.lang.Class r1 = r2.a()
                java.lang.Class<java.lang.Enum> r2 = java.lang.Enum.class
                boolean r2 = r2.isAssignableFrom(r1)
                if (r2 == 0) goto L_0x0021
                java.lang.Class<java.lang.Enum> r2 = java.lang.Enum.class
                if (r1 != r2) goto L_0x0011
                goto L_0x0021
            L_0x0011:
                boolean r2 = r1.isEnum()
                if (r2 != 0) goto L_0x001b
                java.lang.Class r1 = r1.getSuperclass()
            L_0x001b:
                com.google.gson.internal.bind.TypeAdapters$d0 r2 = new com.google.gson.internal.bind.TypeAdapters$d0
                r2.<init>(r1)
                return r2
            L_0x0021:
                r1 = 0
                return r1
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.gson.internal.bind.TypeAdapters.AnonymousClass30.a(com.google.gson.d, com.google.gson.r.a):com.google.gson.o");
        }
    };
    public static final com.google.gson.o<Class> a;
    public static final com.google.gson.p b;
    public static final com.google.gson.o<BitSet> c;
    public static final com.google.gson.p d;
    public static final com.google.gson.o<Boolean> e = new w();

    /* renamed from: f  reason: collision with root package name */
    public static final com.google.gson.o<Boolean> f1606f = new x();

    /* renamed from: g  reason: collision with root package name */
    public static final com.google.gson.p f1607g = a(Boolean.TYPE, Boolean.class, e);

    /* renamed from: h  reason: collision with root package name */
    public static final com.google.gson.o<Number> f1608h = new y();

    /* renamed from: i  reason: collision with root package name */
    public static final com.google.gson.p f1609i = a(Byte.TYPE, Byte.class, f1608h);

    /* renamed from: j  reason: collision with root package name */
    public static final com.google.gson.o<Number> f1610j = new z();
    public static final com.google.gson.p k = a(Short.TYPE, Short.class, f1610j);
    public static final com.google.gson.o<Number> l = new a0();
    public static final com.google.gson.p m = a(Integer.TYPE, Integer.class, l);
    public static final com.google.gson.o<AtomicInteger> n;
    public static final com.google.gson.p o;
    public static final com.google.gson.o<AtomicBoolean> p;
    public static final com.google.gson.p q;
    public static final com.google.gson.o<AtomicIntegerArray> r;
    public static final com.google.gson.p s;
    public static final com.google.gson.o<Number> t = new b();
    public static final com.google.gson.o<Number> u = new c();
    public static final com.google.gson.o<Number> v = new d();
    public static final com.google.gson.o<Number> w;
    public static final com.google.gson.p x;
    public static final com.google.gson.o<Character> y = new f();
    public static final com.google.gson.p z = a(Character.TYPE, Character.class, y);

    /* renamed from: com.google.gson.internal.bind.TypeAdapters$31  reason: invalid class name */
    class AnonymousClass31 implements com.google.gson.p {
        final /* synthetic */ com.google.gson.r.a e;

        /* renamed from: f  reason: collision with root package name */
        final /* synthetic */ com.google.gson.o f1611f;

        public <T> com.google.gson.o<T> a(com.google.gson.d dVar, com.google.gson.r.a<T> aVar) {
            if (aVar.equals(this.e)) {
                return this.f1611f;
            }
            return null;
        }
    }

    class k extends com.google.gson.o<Class> {
        k() {
        }

        public /* bridge */ /* synthetic */ void a(JsonWriter jsonWriter, Object obj) {
            a(jsonWriter, (Class) obj);
            throw null;
        }

        public void a(JsonWriter jsonWriter, Class cls) {
            throw new UnsupportedOperationException("Attempted to serialize java.lang.Class: " + cls.getName() + ". Forgot to register a type adapter?");
        }

        public Class a(JsonReader jsonReader) {
            throw new UnsupportedOperationException("Attempted to deserialize a java.lang.Class. Forgot to register a type adapter?");
        }
    }

    static /* synthetic */ class v {
        static final /* synthetic */ int[] a;

        /* JADX WARNING: Can't wrap try/catch for region: R(20:0|1|2|3|4|5|6|7|8|9|10|11|12|13|14|15|16|17|18|(3:19|20|22)) */
        /* JADX WARNING: Can't wrap try/catch for region: R(22:0|1|2|3|4|5|6|7|8|9|10|11|12|13|14|15|16|17|18|19|20|22) */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:11:0x003e */
        /* JADX WARNING: Missing exception handler attribute for start block: B:13:0x0049 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:15:0x0054 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:17:0x0060 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:19:0x006c */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0012 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x001d */
        /* JADX WARNING: Missing exception handler attribute for start block: B:7:0x0028 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:9:0x0033 */
        static {
            /*
                com.google.gson.stream.JsonToken[] r0 = com.google.gson.stream.JsonToken.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                a = r0
                com.google.gson.stream.JsonToken r1 = com.google.gson.stream.JsonToken.NUMBER     // Catch:{ NoSuchFieldError -> 0x0012 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0012 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0012 }
            L_0x0012:
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x001d }
                com.google.gson.stream.JsonToken r1 = com.google.gson.stream.JsonToken.BOOLEAN     // Catch:{ NoSuchFieldError -> 0x001d }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001d }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001d }
            L_0x001d:
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x0028 }
                com.google.gson.stream.JsonToken r1 = com.google.gson.stream.JsonToken.STRING     // Catch:{ NoSuchFieldError -> 0x0028 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0028 }
                r2 = 3
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0028 }
            L_0x0028:
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x0033 }
                com.google.gson.stream.JsonToken r1 = com.google.gson.stream.JsonToken.NULL     // Catch:{ NoSuchFieldError -> 0x0033 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0033 }
                r2 = 4
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0033 }
            L_0x0033:
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x003e }
                com.google.gson.stream.JsonToken r1 = com.google.gson.stream.JsonToken.BEGIN_ARRAY     // Catch:{ NoSuchFieldError -> 0x003e }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x003e }
                r2 = 5
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x003e }
            L_0x003e:
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x0049 }
                com.google.gson.stream.JsonToken r1 = com.google.gson.stream.JsonToken.BEGIN_OBJECT     // Catch:{ NoSuchFieldError -> 0x0049 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0049 }
                r2 = 6
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0049 }
            L_0x0049:
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x0054 }
                com.google.gson.stream.JsonToken r1 = com.google.gson.stream.JsonToken.END_DOCUMENT     // Catch:{ NoSuchFieldError -> 0x0054 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0054 }
                r2 = 7
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0054 }
            L_0x0054:
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x0060 }
                com.google.gson.stream.JsonToken r1 = com.google.gson.stream.JsonToken.NAME     // Catch:{ NoSuchFieldError -> 0x0060 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0060 }
                r2 = 8
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0060 }
            L_0x0060:
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x006c }
                com.google.gson.stream.JsonToken r1 = com.google.gson.stream.JsonToken.END_OBJECT     // Catch:{ NoSuchFieldError -> 0x006c }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x006c }
                r2 = 9
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x006c }
            L_0x006c:
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x0078 }
                com.google.gson.stream.JsonToken r1 = com.google.gson.stream.JsonToken.END_ARRAY     // Catch:{ NoSuchFieldError -> 0x0078 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0078 }
                r2 = 10
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0078 }
            L_0x0078:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.gson.internal.bind.TypeAdapters.v.<clinit>():void");
        }
    }

    static {
        com.google.gson.o<Class> a2 = new k().a();
        a = a2;
        b = a(Class.class, a2);
        com.google.gson.o<BitSet> a3 = new u().a();
        c = a3;
        d = a(BitSet.class, a3);
        com.google.gson.o<AtomicInteger> a4 = new b0().a();
        n = a4;
        o = a(AtomicInteger.class, a4);
        com.google.gson.o<AtomicBoolean> a5 = new c0().a();
        p = a5;
        q = a(AtomicBoolean.class, a5);
        com.google.gson.o<AtomicIntegerArray> a6 = new a().a();
        r = a6;
        s = a(AtomicIntegerArray.class, a6);
        e eVar = new e();
        w = eVar;
        x = a(Number.class, eVar);
        j jVar = new j();
        E = jVar;
        F = a(StringBuilder.class, jVar);
        l lVar = new l();
        G = lVar;
        H = a(StringBuffer.class, lVar);
        m mVar = new m();
        I = mVar;
        J = a(URL.class, mVar);
        n nVar = new n();
        K = nVar;
        L = a(URI.class, nVar);
        o oVar = new o();
        M = oVar;
        N = b(InetAddress.class, oVar);
        p pVar = new p();
        O = pVar;
        P = a(UUID.class, pVar);
        com.google.gson.o<Currency> a7 = new q().a();
        Q = a7;
        R = a(Currency.class, a7);
        r rVar = new r();
        T = rVar;
        U = b(Calendar.class, GregorianCalendar.class, rVar);
        s sVar = new s();
        V = sVar;
        W = a(Locale.class, sVar);
        t tVar = new t();
        X = tVar;
        Y = b(com.google.gson.i.class, tVar);
    }

    public static <TT> com.google.gson.p a(final Class<TT> cls, final com.google.gson.o<TT> oVar) {
        return new com.google.gson.p() {
            public <T> com.google.gson.o<T> a(com.google.gson.d dVar, com.google.gson.r.a<T> aVar) {
                if (aVar.a() == cls) {
                    return oVar;
                }
                return null;
            }

            public String toString() {
                return "Factory[type=" + cls.getName() + ",adapter=" + oVar + "]";
            }
        };
    }

    public static <TT> com.google.gson.p b(final Class<TT> cls, final Class<? extends TT> cls2, final com.google.gson.o<? super TT> oVar) {
        return new com.google.gson.p() {
            public <T> com.google.gson.o<T> a(com.google.gson.d dVar, com.google.gson.r.a<T> aVar) {
                Class<? super T> a = aVar.a();
                if (a == cls || a == cls2) {
                    return oVar;
                }
                return null;
            }

            public String toString() {
                return "Factory[type=" + cls.getName() + "+" + cls2.getName() + ",adapter=" + oVar + "]";
            }
        };
    }

    class a extends com.google.gson.o<AtomicIntegerArray> {
        a() {
        }

        public AtomicIntegerArray a(JsonReader jsonReader) {
            ArrayList arrayList = new ArrayList();
            jsonReader.beginArray();
            while (jsonReader.hasNext()) {
                try {
                    arrayList.add(Integer.valueOf(jsonReader.nextInt()));
                } catch (NumberFormatException e) {
                    throw new JsonSyntaxException((Throwable) e);
                }
            }
            jsonReader.endArray();
            int size = arrayList.size();
            AtomicIntegerArray atomicIntegerArray = new AtomicIntegerArray(size);
            for (int i2 = 0; i2 < size; i2++) {
                atomicIntegerArray.set(i2, ((Integer) arrayList.get(i2)).intValue());
            }
            return atomicIntegerArray;
        }

        public void a(JsonWriter jsonWriter, AtomicIntegerArray atomicIntegerArray) {
            jsonWriter.beginArray();
            int length = atomicIntegerArray.length();
            for (int i2 = 0; i2 < length; i2++) {
                jsonWriter.value((long) atomicIntegerArray.get(i2));
            }
            jsonWriter.endArray();
        }
    }

    class a0 extends com.google.gson.o<Number> {
        a0() {
        }

        public Number a(JsonReader jsonReader) {
            if (jsonReader.peek() == JsonToken.NULL) {
                jsonReader.nextNull();
                return null;
            }
            try {
                return Integer.valueOf(jsonReader.nextInt());
            } catch (NumberFormatException e) {
                throw new JsonSyntaxException((Throwable) e);
            }
        }

        public void a(JsonWriter jsonWriter, Number number) {
            jsonWriter.value(number);
        }
    }

    class b extends com.google.gson.o<Number> {
        b() {
        }

        public Number a(JsonReader jsonReader) {
            if (jsonReader.peek() == JsonToken.NULL) {
                jsonReader.nextNull();
                return null;
            }
            try {
                return Long.valueOf(jsonReader.nextLong());
            } catch (NumberFormatException e) {
                throw new JsonSyntaxException((Throwable) e);
            }
        }

        public void a(JsonWriter jsonWriter, Number number) {
            jsonWriter.value(number);
        }
    }

    class b0 extends com.google.gson.o<AtomicInteger> {
        b0() {
        }

        public AtomicInteger a(JsonReader jsonReader) {
            try {
                return new AtomicInteger(jsonReader.nextInt());
            } catch (NumberFormatException e) {
                throw new JsonSyntaxException((Throwable) e);
            }
        }

        public void a(JsonWriter jsonWriter, AtomicInteger atomicInteger) {
            jsonWriter.value((long) atomicInteger.get());
        }
    }

    class c extends com.google.gson.o<Number> {
        c() {
        }

        public Number a(JsonReader jsonReader) {
            if (jsonReader.peek() != JsonToken.NULL) {
                return Float.valueOf((float) jsonReader.nextDouble());
            }
            jsonReader.nextNull();
            return null;
        }

        public void a(JsonWriter jsonWriter, Number number) {
            jsonWriter.value(number);
        }
    }

    class c0 extends com.google.gson.o<AtomicBoolean> {
        c0() {
        }

        public AtomicBoolean a(JsonReader jsonReader) {
            return new AtomicBoolean(jsonReader.nextBoolean());
        }

        public void a(JsonWriter jsonWriter, AtomicBoolean atomicBoolean) {
            jsonWriter.value(atomicBoolean.get());
        }
    }

    class d extends com.google.gson.o<Number> {
        d() {
        }

        public Number a(JsonReader jsonReader) {
            if (jsonReader.peek() != JsonToken.NULL) {
                return Double.valueOf(jsonReader.nextDouble());
            }
            jsonReader.nextNull();
            return null;
        }

        public void a(JsonWriter jsonWriter, Number number) {
            jsonWriter.value(number);
        }
    }

    private static final class d0<T extends Enum<T>> extends com.google.gson.o<T> {
        private final Map<String, T> a = new HashMap();
        private final Map<T, String> b = new HashMap();

        public d0(Class<T> cls) {
            try {
                for (Enum enumR : (Enum[]) cls.getEnumConstants()) {
                    String name = enumR.name();
                    com.google.gson.q.c cVar = (com.google.gson.q.c) cls.getField(name).getAnnotation(com.google.gson.q.c.class);
                    if (cVar != null) {
                        name = cVar.value();
                        for (String put : cVar.alternate()) {
                            this.a.put(put, enumR);
                        }
                    }
                    this.a.put(name, enumR);
                    this.b.put(enumR, name);
                }
            } catch (NoSuchFieldException e) {
                throw new AssertionError(e);
            }
        }

        public T a(JsonReader jsonReader) {
            if (jsonReader.peek() != JsonToken.NULL) {
                return (Enum) this.a.get(jsonReader.nextString());
            }
            jsonReader.nextNull();
            return null;
        }

        public void a(JsonWriter jsonWriter, T t) {
            jsonWriter.value(t == null ? null : this.b.get(t));
        }
    }

    class e extends com.google.gson.o<Number> {
        e() {
        }

        public Number a(JsonReader jsonReader) {
            JsonToken peek = jsonReader.peek();
            int i2 = v.a[peek.ordinal()];
            if (i2 == 1 || i2 == 3) {
                return new LazilyParsedNumber(jsonReader.nextString());
            }
            if (i2 == 4) {
                jsonReader.nextNull();
                return null;
            }
            throw new JsonSyntaxException("Expecting number, got: " + peek);
        }

        public void a(JsonWriter jsonWriter, Number number) {
            jsonWriter.value(number);
        }
    }

    class f extends com.google.gson.o<Character> {
        f() {
        }

        public Character a(JsonReader jsonReader) {
            if (jsonReader.peek() == JsonToken.NULL) {
                jsonReader.nextNull();
                return null;
            }
            String nextString = jsonReader.nextString();
            if (nextString.length() == 1) {
                return Character.valueOf(nextString.charAt(0));
            }
            throw new JsonSyntaxException("Expecting character, got: " + nextString);
        }

        public void a(JsonWriter jsonWriter, Character ch) {
            jsonWriter.value(ch == null ? null : String.valueOf(ch));
        }
    }

    class g extends com.google.gson.o<String> {
        g() {
        }

        public String a(JsonReader jsonReader) {
            JsonToken peek = jsonReader.peek();
            if (peek == JsonToken.NULL) {
                jsonReader.nextNull();
                return null;
            } else if (peek == JsonToken.BOOLEAN) {
                return Boolean.toString(jsonReader.nextBoolean());
            } else {
                return jsonReader.nextString();
            }
        }

        public void a(JsonWriter jsonWriter, String str) {
            jsonWriter.value(str);
        }
    }

    class h extends com.google.gson.o<BigDecimal> {
        h() {
        }

        public BigDecimal a(JsonReader jsonReader) {
            if (jsonReader.peek() == JsonToken.NULL) {
                jsonReader.nextNull();
                return null;
            }
            try {
                return new BigDecimal(jsonReader.nextString());
            } catch (NumberFormatException e) {
                throw new JsonSyntaxException((Throwable) e);
            }
        }

        public void a(JsonWriter jsonWriter, BigDecimal bigDecimal) {
            jsonWriter.value((Number) bigDecimal);
        }
    }

    class i extends com.google.gson.o<BigInteger> {
        i() {
        }

        public BigInteger a(JsonReader jsonReader) {
            if (jsonReader.peek() == JsonToken.NULL) {
                jsonReader.nextNull();
                return null;
            }
            try {
                return new BigInteger(jsonReader.nextString());
            } catch (NumberFormatException e) {
                throw new JsonSyntaxException((Throwable) e);
            }
        }

        public void a(JsonWriter jsonWriter, BigInteger bigInteger) {
            jsonWriter.value((Number) bigInteger);
        }
    }

    class j extends com.google.gson.o<StringBuilder> {
        j() {
        }

        public StringBuilder a(JsonReader jsonReader) {
            if (jsonReader.peek() != JsonToken.NULL) {
                return new StringBuilder(jsonReader.nextString());
            }
            jsonReader.nextNull();
            return null;
        }

        public void a(JsonWriter jsonWriter, StringBuilder sb) {
            jsonWriter.value(sb == null ? null : sb.toString());
        }
    }

    class l extends com.google.gson.o<StringBuffer> {
        l() {
        }

        public StringBuffer a(JsonReader jsonReader) {
            if (jsonReader.peek() != JsonToken.NULL) {
                return new StringBuffer(jsonReader.nextString());
            }
            jsonReader.nextNull();
            return null;
        }

        public void a(JsonWriter jsonWriter, StringBuffer stringBuffer) {
            jsonWriter.value(stringBuffer == null ? null : stringBuffer.toString());
        }
    }

    class m extends com.google.gson.o<URL> {
        m() {
        }

        public URL a(JsonReader jsonReader) {
            if (jsonReader.peek() == JsonToken.NULL) {
                jsonReader.nextNull();
                return null;
            }
            String nextString = jsonReader.nextString();
            if ("null".equals(nextString)) {
                return null;
            }
            return new URL(nextString);
        }

        public void a(JsonWriter jsonWriter, URL url) {
            jsonWriter.value(url == null ? null : url.toExternalForm());
        }
    }

    class n extends com.google.gson.o<URI> {
        n() {
        }

        public URI a(JsonReader jsonReader) {
            if (jsonReader.peek() == JsonToken.NULL) {
                jsonReader.nextNull();
                return null;
            }
            try {
                String nextString = jsonReader.nextString();
                if ("null".equals(nextString)) {
                    return null;
                }
                return new URI(nextString);
            } catch (URISyntaxException e) {
                throw new JsonIOException((Throwable) e);
            }
        }

        public void a(JsonWriter jsonWriter, URI uri) {
            jsonWriter.value(uri == null ? null : uri.toASCIIString());
        }
    }

    class o extends com.google.gson.o<InetAddress> {
        o() {
        }

        public InetAddress a(JsonReader jsonReader) {
            if (jsonReader.peek() != JsonToken.NULL) {
                return InetAddress.getByName(jsonReader.nextString());
            }
            jsonReader.nextNull();
            return null;
        }

        public void a(JsonWriter jsonWriter, InetAddress inetAddress) {
            jsonWriter.value(inetAddress == null ? null : inetAddress.getHostAddress());
        }
    }

    class p extends com.google.gson.o<UUID> {
        p() {
        }

        public UUID a(JsonReader jsonReader) {
            if (jsonReader.peek() != JsonToken.NULL) {
                return UUID.fromString(jsonReader.nextString());
            }
            jsonReader.nextNull();
            return null;
        }

        public void a(JsonWriter jsonWriter, UUID uuid) {
            jsonWriter.value(uuid == null ? null : uuid.toString());
        }
    }

    class q extends com.google.gson.o<Currency> {
        q() {
        }

        public Currency a(JsonReader jsonReader) {
            return Currency.getInstance(jsonReader.nextString());
        }

        public void a(JsonWriter jsonWriter, Currency currency) {
            jsonWriter.value(currency.getCurrencyCode());
        }
    }

    class r extends com.google.gson.o<Calendar> {
        r() {
        }

        public Calendar a(JsonReader jsonReader) {
            if (jsonReader.peek() == JsonToken.NULL) {
                jsonReader.nextNull();
                return null;
            }
            jsonReader.beginObject();
            int i2 = 0;
            int i3 = 0;
            int i4 = 0;
            int i5 = 0;
            int i6 = 0;
            int i7 = 0;
            while (jsonReader.peek() != JsonToken.END_OBJECT) {
                String nextName = jsonReader.nextName();
                int nextInt = jsonReader.nextInt();
                if ("year".equals(nextName)) {
                    i2 = nextInt;
                } else if ("month".equals(nextName)) {
                    i3 = nextInt;
                } else if ("dayOfMonth".equals(nextName)) {
                    i4 = nextInt;
                } else if ("hourOfDay".equals(nextName)) {
                    i5 = nextInt;
                } else if ("minute".equals(nextName)) {
                    i6 = nextInt;
                } else if ("second".equals(nextName)) {
                    i7 = nextInt;
                }
            }
            jsonReader.endObject();
            return new GregorianCalendar(i2, i3, i4, i5, i6, i7);
        }

        public void a(JsonWriter jsonWriter, Calendar calendar) {
            if (calendar == null) {
                jsonWriter.nullValue();
                return;
            }
            jsonWriter.beginObject();
            jsonWriter.name("year");
            jsonWriter.value((long) calendar.get(1));
            jsonWriter.name("month");
            jsonWriter.value((long) calendar.get(2));
            jsonWriter.name("dayOfMonth");
            jsonWriter.value((long) calendar.get(5));
            jsonWriter.name("hourOfDay");
            jsonWriter.value((long) calendar.get(11));
            jsonWriter.name("minute");
            jsonWriter.value((long) calendar.get(12));
            jsonWriter.name("second");
            jsonWriter.value((long) calendar.get(13));
            jsonWriter.endObject();
        }
    }

    class s extends com.google.gson.o<Locale> {
        s() {
        }

        public Locale a(JsonReader jsonReader) {
            String str = null;
            if (jsonReader.peek() == JsonToken.NULL) {
                jsonReader.nextNull();
                return null;
            }
            StringTokenizer stringTokenizer = new StringTokenizer(jsonReader.nextString(), "_");
            String nextToken = stringTokenizer.hasMoreElements() ? stringTokenizer.nextToken() : null;
            String nextToken2 = stringTokenizer.hasMoreElements() ? stringTokenizer.nextToken() : null;
            if (stringTokenizer.hasMoreElements()) {
                str = stringTokenizer.nextToken();
            }
            if (nextToken2 == null && str == null) {
                return new Locale(nextToken);
            }
            if (str == null) {
                return new Locale(nextToken, nextToken2);
            }
            return new Locale(nextToken, nextToken2, str);
        }

        public void a(JsonWriter jsonWriter, Locale locale) {
            jsonWriter.value(locale == null ? null : locale.toString());
        }
    }

    class t extends com.google.gson.o<com.google.gson.i> {
        t() {
        }

        public com.google.gson.i a(JsonReader jsonReader) {
            switch (v.a[jsonReader.peek().ordinal()]) {
                case 1:
                    return new com.google.gson.l((Number) new LazilyParsedNumber(jsonReader.nextString()));
                case 2:
                    return new com.google.gson.l(Boolean.valueOf(jsonReader.nextBoolean()));
                case 3:
                    return new com.google.gson.l(jsonReader.nextString());
                case 4:
                    jsonReader.nextNull();
                    return com.google.gson.j.a;
                case 5:
                    com.google.gson.f fVar = new com.google.gson.f();
                    jsonReader.beginArray();
                    while (jsonReader.hasNext()) {
                        fVar.a(a(jsonReader));
                    }
                    jsonReader.endArray();
                    return fVar;
                case 6:
                    com.google.gson.k kVar = new com.google.gson.k();
                    jsonReader.beginObject();
                    while (jsonReader.hasNext()) {
                        kVar.a(jsonReader.nextName(), a(jsonReader));
                    }
                    jsonReader.endObject();
                    return kVar;
                default:
                    throw new IllegalArgumentException();
            }
        }

        public void a(JsonWriter jsonWriter, com.google.gson.i iVar) {
            if (iVar == null || iVar.e()) {
                jsonWriter.nullValue();
            } else if (iVar.g()) {
                com.google.gson.l c = iVar.c();
                if (c.o()) {
                    jsonWriter.value(c.l());
                } else if (c.n()) {
                    jsonWriter.value(c.h());
                } else {
                    jsonWriter.value(c.m());
                }
            } else if (iVar.d()) {
                jsonWriter.beginArray();
                Iterator<com.google.gson.i> it = iVar.a().iterator();
                while (it.hasNext()) {
                    a(jsonWriter, it.next());
                }
                jsonWriter.endArray();
            } else if (iVar.f()) {
                jsonWriter.beginObject();
                for (Map.Entry next : iVar.b().h()) {
                    jsonWriter.name((String) next.getKey());
                    a(jsonWriter, (com.google.gson.i) next.getValue());
                }
                jsonWriter.endObject();
            } else {
                throw new IllegalArgumentException("Couldn't write " + iVar.getClass());
            }
        }
    }

    class u extends com.google.gson.o<BitSet> {
        u() {
        }

        /* JADX WARNING: Code restructure failed: missing block: B:12:0x002b, code lost:
            if (java.lang.Integer.parseInt(r1) != 0) goto L_0x0069;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:21:0x0067, code lost:
            if (r8.nextInt() != 0) goto L_0x0069;
         */
        /* JADX WARNING: Removed duplicated region for block: B:23:0x006b  */
        /* JADX WARNING: Removed duplicated region for block: B:30:0x006e A[SYNTHETIC] */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public java.util.BitSet a(com.google.gson.stream.JsonReader r8) {
            /*
                r7 = this;
                java.util.BitSet r0 = new java.util.BitSet
                r0.<init>()
                r8.beginArray()
                com.google.gson.stream.JsonToken r1 = r8.peek()
                r2 = 0
                r3 = 0
            L_0x000e:
                com.google.gson.stream.JsonToken r4 = com.google.gson.stream.JsonToken.END_ARRAY
                if (r1 == r4) goto L_0x0075
                int[] r4 = com.google.gson.internal.bind.TypeAdapters.v.a
                int r5 = r1.ordinal()
                r4 = r4[r5]
                r5 = 1
                if (r4 == r5) goto L_0x0063
                r6 = 2
                if (r4 == r6) goto L_0x005e
                r6 = 3
                if (r4 != r6) goto L_0x0047
                java.lang.String r1 = r8.nextString()
                int r1 = java.lang.Integer.parseInt(r1)     // Catch:{ NumberFormatException -> 0x0030 }
                if (r1 == 0) goto L_0x002e
                goto L_0x0069
            L_0x002e:
                r5 = 0
                goto L_0x0069
            L_0x0030:
                com.google.gson.JsonSyntaxException r8 = new com.google.gson.JsonSyntaxException
                java.lang.StringBuilder r0 = new java.lang.StringBuilder
                r0.<init>()
                java.lang.String r2 = "Error: Expecting: bitset number value (1, 0), Found: "
                r0.append(r2)
                r0.append(r1)
                java.lang.String r0 = r0.toString()
                r8.<init>((java.lang.String) r0)
                throw r8
            L_0x0047:
                com.google.gson.JsonSyntaxException r8 = new com.google.gson.JsonSyntaxException
                java.lang.StringBuilder r0 = new java.lang.StringBuilder
                r0.<init>()
                java.lang.String r2 = "Invalid bitset value type: "
                r0.append(r2)
                r0.append(r1)
                java.lang.String r0 = r0.toString()
                r8.<init>((java.lang.String) r0)
                throw r8
            L_0x005e:
                boolean r5 = r8.nextBoolean()
                goto L_0x0069
            L_0x0063:
                int r1 = r8.nextInt()
                if (r1 == 0) goto L_0x002e
            L_0x0069:
                if (r5 == 0) goto L_0x006e
                r0.set(r3)
            L_0x006e:
                int r3 = r3 + 1
                com.google.gson.stream.JsonToken r1 = r8.peek()
                goto L_0x000e
            L_0x0075:
                r8.endArray()
                return r0
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.gson.internal.bind.TypeAdapters.u.a(com.google.gson.stream.JsonReader):java.util.BitSet");
        }

        public void a(JsonWriter jsonWriter, BitSet bitSet) {
            jsonWriter.beginArray();
            int length = bitSet.length();
            for (int i2 = 0; i2 < length; i2++) {
                jsonWriter.value(bitSet.get(i2) ? 1 : 0);
            }
            jsonWriter.endArray();
        }
    }

    class w extends com.google.gson.o<Boolean> {
        w() {
        }

        public Boolean a(JsonReader jsonReader) {
            JsonToken peek = jsonReader.peek();
            if (peek == JsonToken.NULL) {
                jsonReader.nextNull();
                return null;
            } else if (peek == JsonToken.STRING) {
                return Boolean.valueOf(Boolean.parseBoolean(jsonReader.nextString()));
            } else {
                return Boolean.valueOf(jsonReader.nextBoolean());
            }
        }

        public void a(JsonWriter jsonWriter, Boolean bool) {
            jsonWriter.value(bool);
        }
    }

    class x extends com.google.gson.o<Boolean> {
        x() {
        }

        public Boolean a(JsonReader jsonReader) {
            if (jsonReader.peek() != JsonToken.NULL) {
                return Boolean.valueOf(jsonReader.nextString());
            }
            jsonReader.nextNull();
            return null;
        }

        public void a(JsonWriter jsonWriter, Boolean bool) {
            jsonWriter.value(bool == null ? "null" : bool.toString());
        }
    }

    class y extends com.google.gson.o<Number> {
        y() {
        }

        public Number a(JsonReader jsonReader) {
            if (jsonReader.peek() == JsonToken.NULL) {
                jsonReader.nextNull();
                return null;
            }
            try {
                return Byte.valueOf((byte) jsonReader.nextInt());
            } catch (NumberFormatException e) {
                throw new JsonSyntaxException((Throwable) e);
            }
        }

        public void a(JsonWriter jsonWriter, Number number) {
            jsonWriter.value(number);
        }
    }

    class z extends com.google.gson.o<Number> {
        z() {
        }

        public Number a(JsonReader jsonReader) {
            if (jsonReader.peek() == JsonToken.NULL) {
                jsonReader.nextNull();
                return null;
            }
            try {
                return Short.valueOf((short) jsonReader.nextInt());
            } catch (NumberFormatException e) {
                throw new JsonSyntaxException((Throwable) e);
            }
        }

        public void a(JsonWriter jsonWriter, Number number) {
            jsonWriter.value(number);
        }
    }

    public static <TT> com.google.gson.p a(final Class<TT> cls, final Class<TT> cls2, final com.google.gson.o<? super TT> oVar) {
        return new com.google.gson.p() {
            public <T> com.google.gson.o<T> a(com.google.gson.d dVar, com.google.gson.r.a<T> aVar) {
                Class<? super T> a = aVar.a();
                if (a == cls || a == cls2) {
                    return oVar;
                }
                return null;
            }

            public String toString() {
                return "Factory[type=" + cls2.getName() + "+" + cls.getName() + ",adapter=" + oVar + "]";
            }
        };
    }

    public static <T1> com.google.gson.p b(final Class<T1> cls, final com.google.gson.o<T1> oVar) {
        return new com.google.gson.p() {

            /* renamed from: com.google.gson.internal.bind.TypeAdapters$35$a */
            class a extends com.google.gson.o<T1> {
                final /* synthetic */ Class a;

                a(Class cls) {
                    this.a = cls;
                }

                public void a(JsonWriter jsonWriter, T1 t1) {
                    oVar.a(jsonWriter, t1);
                }

                public T1 a(JsonReader jsonReader) {
                    T1 a2 = oVar.a(jsonReader);
                    if (a2 == null || this.a.isInstance(a2)) {
                        return a2;
                    }
                    throw new JsonSyntaxException("Expected a " + this.a.getName() + " but was " + a2.getClass().getName());
                }
            }

            public <T2> com.google.gson.o<T2> a(com.google.gson.d dVar, com.google.gson.r.a<T2> aVar) {
                Class<? super T2> a2 = aVar.a();
                if (!cls.isAssignableFrom(a2)) {
                    return null;
                }
                return new a(a2);
            }

            public String toString() {
                return "Factory[typeHierarchy=" + cls.getName() + ",adapter=" + oVar + "]";
            }
        };
    }
}
