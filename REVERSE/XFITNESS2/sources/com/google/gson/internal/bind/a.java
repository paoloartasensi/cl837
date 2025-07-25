package com.google.gson.internal.bind;

import com.google.gson.f;
import com.google.gson.j;
import com.google.gson.k;
import com.google.gson.l;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import java.io.Reader;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;

/* compiled from: JsonTreeReader */
public final class a extends JsonReader {

    /* renamed from: i  reason: collision with root package name */
    private static final Object f1618i = new Object();
    private Object[] e;

    /* renamed from: f  reason: collision with root package name */
    private int f1619f;

    /* renamed from: g  reason: collision with root package name */
    private String[] f1620g;

    /* renamed from: h  reason: collision with root package name */
    private int[] f1621h;

    /* renamed from: com.google.gson.internal.bind.a$a  reason: collision with other inner class name */
    /* compiled from: JsonTreeReader */
    class C0084a extends Reader {
        C0084a() {
        }

        public void close() {
            throw new AssertionError();
        }

        public int read(char[] cArr, int i2, int i3) {
            throw new AssertionError();
        }
    }

    static {
        new C0084a();
    }

    private void a(JsonToken jsonToken) {
        if (peek() != jsonToken) {
            throw new IllegalStateException("Expected " + jsonToken + " but was " + peek() + locationString());
        }
    }

    private Object c() {
        return this.e[this.f1619f - 1];
    }

    private Object j() {
        Object[] objArr = this.e;
        int i2 = this.f1619f - 1;
        this.f1619f = i2;
        Object obj = objArr[i2];
        objArr[i2] = null;
        return obj;
    }

    private String locationString() {
        return " at path " + getPath();
    }

    public void beginArray() {
        a(JsonToken.BEGIN_ARRAY);
        a((Object) ((f) c()).iterator());
        this.f1621h[this.f1619f - 1] = 0;
    }

    public void beginObject() {
        a(JsonToken.BEGIN_OBJECT);
        a((Object) ((k) c()).h().iterator());
    }

    public void close() {
        this.e = new Object[]{f1618i};
        this.f1619f = 1;
    }

    public void endArray() {
        a(JsonToken.END_ARRAY);
        j();
        j();
        int i2 = this.f1619f;
        if (i2 > 0) {
            int[] iArr = this.f1621h;
            int i3 = i2 - 1;
            iArr[i3] = iArr[i3] + 1;
        }
    }

    public void endObject() {
        a(JsonToken.END_OBJECT);
        j();
        j();
        int i2 = this.f1619f;
        if (i2 > 0) {
            int[] iArr = this.f1621h;
            int i3 = i2 - 1;
            iArr[i3] = iArr[i3] + 1;
        }
    }

    public String getPath() {
        StringBuilder sb = new StringBuilder();
        sb.append('$');
        int i2 = 0;
        while (i2 < this.f1619f) {
            Object[] objArr = this.e;
            if (objArr[i2] instanceof f) {
                i2++;
                if (objArr[i2] instanceof Iterator) {
                    sb.append('[');
                    sb.append(this.f1621h[i2]);
                    sb.append(']');
                }
            } else if (objArr[i2] instanceof k) {
                i2++;
                if (objArr[i2] instanceof Iterator) {
                    sb.append('.');
                    String[] strArr = this.f1620g;
                    if (strArr[i2] != null) {
                        sb.append(strArr[i2]);
                    }
                }
            }
            i2++;
        }
        return sb.toString();
    }

    public boolean hasNext() {
        JsonToken peek = peek();
        return (peek == JsonToken.END_OBJECT || peek == JsonToken.END_ARRAY) ? false : true;
    }

    public boolean nextBoolean() {
        a(JsonToken.BOOLEAN);
        boolean h2 = ((l) j()).h();
        int i2 = this.f1619f;
        if (i2 > 0) {
            int[] iArr = this.f1621h;
            int i3 = i2 - 1;
            iArr[i3] = iArr[i3] + 1;
        }
        return h2;
    }

    public double nextDouble() {
        JsonToken peek = peek();
        if (peek == JsonToken.NUMBER || peek == JsonToken.STRING) {
            double i2 = ((l) c()).i();
            if (isLenient() || (!Double.isNaN(i2) && !Double.isInfinite(i2))) {
                j();
                int i3 = this.f1619f;
                if (i3 > 0) {
                    int[] iArr = this.f1621h;
                    int i4 = i3 - 1;
                    iArr[i4] = iArr[i4] + 1;
                }
                return i2;
            }
            throw new NumberFormatException("JSON forbids NaN and infinities: " + i2);
        }
        throw new IllegalStateException("Expected " + JsonToken.NUMBER + " but was " + peek + locationString());
    }

    public int nextInt() {
        JsonToken peek = peek();
        if (peek == JsonToken.NUMBER || peek == JsonToken.STRING) {
            int j2 = ((l) c()).j();
            j();
            int i2 = this.f1619f;
            if (i2 > 0) {
                int[] iArr = this.f1621h;
                int i3 = i2 - 1;
                iArr[i3] = iArr[i3] + 1;
            }
            return j2;
        }
        throw new IllegalStateException("Expected " + JsonToken.NUMBER + " but was " + peek + locationString());
    }

    public long nextLong() {
        JsonToken peek = peek();
        if (peek == JsonToken.NUMBER || peek == JsonToken.STRING) {
            long k = ((l) c()).k();
            j();
            int i2 = this.f1619f;
            if (i2 > 0) {
                int[] iArr = this.f1621h;
                int i3 = i2 - 1;
                iArr[i3] = iArr[i3] + 1;
            }
            return k;
        }
        throw new IllegalStateException("Expected " + JsonToken.NUMBER + " but was " + peek + locationString());
    }

    public String nextName() {
        a(JsonToken.NAME);
        Map.Entry entry = (Map.Entry) ((Iterator) c()).next();
        String str = (String) entry.getKey();
        this.f1620g[this.f1619f - 1] = str;
        a(entry.getValue());
        return str;
    }

    public void nextNull() {
        a(JsonToken.NULL);
        j();
        int i2 = this.f1619f;
        if (i2 > 0) {
            int[] iArr = this.f1621h;
            int i3 = i2 - 1;
            iArr[i3] = iArr[i3] + 1;
        }
    }

    public String nextString() {
        JsonToken peek = peek();
        if (peek == JsonToken.STRING || peek == JsonToken.NUMBER) {
            String m = ((l) j()).m();
            int i2 = this.f1619f;
            if (i2 > 0) {
                int[] iArr = this.f1621h;
                int i3 = i2 - 1;
                iArr[i3] = iArr[i3] + 1;
            }
            return m;
        }
        throw new IllegalStateException("Expected " + JsonToken.STRING + " but was " + peek + locationString());
    }

    public JsonToken peek() {
        if (this.f1619f == 0) {
            return JsonToken.END_DOCUMENT;
        }
        Object c = c();
        if (c instanceof Iterator) {
            boolean z = this.e[this.f1619f - 2] instanceof k;
            Iterator it = (Iterator) c;
            if (!it.hasNext()) {
                return z ? JsonToken.END_OBJECT : JsonToken.END_ARRAY;
            }
            if (z) {
                return JsonToken.NAME;
            }
            a(it.next());
            return peek();
        } else if (c instanceof k) {
            return JsonToken.BEGIN_OBJECT;
        } else {
            if (c instanceof f) {
                return JsonToken.BEGIN_ARRAY;
            }
            if (c instanceof l) {
                l lVar = (l) c;
                if (lVar.p()) {
                    return JsonToken.STRING;
                }
                if (lVar.n()) {
                    return JsonToken.BOOLEAN;
                }
                if (lVar.o()) {
                    return JsonToken.NUMBER;
                }
                throw new AssertionError();
            } else if (c instanceof j) {
                return JsonToken.NULL;
            } else {
                if (c == f1618i) {
                    throw new IllegalStateException("JsonReader is closed");
                }
                throw new AssertionError();
            }
        }
    }

    public void skipValue() {
        if (peek() == JsonToken.NAME) {
            nextName();
            this.f1620g[this.f1619f - 2] = "null";
        } else {
            j();
            int i2 = this.f1619f;
            if (i2 > 0) {
                this.f1620g[i2 - 1] = "null";
            }
        }
        int i3 = this.f1619f;
        if (i3 > 0) {
            int[] iArr = this.f1621h;
            int i4 = i3 - 1;
            iArr[i4] = iArr[i4] + 1;
        }
    }

    public String toString() {
        return a.class.getSimpleName();
    }

    public void a() {
        a(JsonToken.NAME);
        Map.Entry entry = (Map.Entry) ((Iterator) c()).next();
        a(entry.getValue());
        a((Object) new l((String) entry.getKey()));
    }

    private void a(Object obj) {
        int i2 = this.f1619f;
        Object[] objArr = this.e;
        if (i2 == objArr.length) {
            int i3 = i2 * 2;
            this.e = Arrays.copyOf(objArr, i3);
            this.f1621h = Arrays.copyOf(this.f1621h, i3);
            this.f1620g = (String[]) Arrays.copyOf(this.f1620g, i3);
        }
        Object[] objArr2 = this.e;
        int i4 = this.f1619f;
        this.f1619f = i4 + 1;
        objArr2[i4] = obj;
    }
}
