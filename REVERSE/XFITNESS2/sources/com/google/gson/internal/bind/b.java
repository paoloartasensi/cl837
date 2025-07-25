package com.google.gson.internal.bind;

import com.google.gson.f;
import com.google.gson.i;
import com.google.gson.j;
import com.google.gson.k;
import com.google.gson.l;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

/* compiled from: JsonTreeWriter */
public final class b extends JsonWriter {

    /* renamed from: h  reason: collision with root package name */
    private static final Writer f1622h = new a();

    /* renamed from: i  reason: collision with root package name */
    private static final l f1623i = new l("closed");
    private final List<i> e = new ArrayList();

    /* renamed from: f  reason: collision with root package name */
    private String f1624f;

    /* renamed from: g  reason: collision with root package name */
    private i f1625g = j.a;

    /* compiled from: JsonTreeWriter */
    class a extends Writer {
        a() {
        }

        public void close() {
            throw new AssertionError();
        }

        public void flush() {
            throw new AssertionError();
        }

        public void write(char[] cArr, int i2, int i3) {
            throw new AssertionError();
        }
    }

    public b() {
        super(f1622h);
    }

    private i peek() {
        List<i> list = this.e;
        return list.get(list.size() - 1);
    }

    public i a() {
        if (this.e.isEmpty()) {
            return this.f1625g;
        }
        throw new IllegalStateException("Expected one JSON element but was " + this.e);
    }

    public JsonWriter beginArray() {
        f fVar = new f();
        a(fVar);
        this.e.add(fVar);
        return this;
    }

    public JsonWriter beginObject() {
        k kVar = new k();
        a(kVar);
        this.e.add(kVar);
        return this;
    }

    public void close() {
        if (this.e.isEmpty()) {
            this.e.add(f1623i);
            return;
        }
        throw new IOException("Incomplete document");
    }

    public JsonWriter endArray() {
        if (this.e.isEmpty() || this.f1624f != null) {
            throw new IllegalStateException();
        } else if (peek() instanceof f) {
            List<i> list = this.e;
            list.remove(list.size() - 1);
            return this;
        } else {
            throw new IllegalStateException();
        }
    }

    public JsonWriter endObject() {
        if (this.e.isEmpty() || this.f1624f != null) {
            throw new IllegalStateException();
        } else if (peek() instanceof k) {
            List<i> list = this.e;
            list.remove(list.size() - 1);
            return this;
        } else {
            throw new IllegalStateException();
        }
    }

    public void flush() {
    }

    public JsonWriter name(String str) {
        if (this.e.isEmpty() || this.f1624f != null) {
            throw new IllegalStateException();
        } else if (peek() instanceof k) {
            this.f1624f = str;
            return this;
        } else {
            throw new IllegalStateException();
        }
    }

    public JsonWriter nullValue() {
        a(j.a);
        return this;
    }

    public JsonWriter value(String str) {
        if (str == null) {
            nullValue();
            return this;
        }
        a(new l(str));
        return this;
    }

    public JsonWriter value(boolean z) {
        a(new l(Boolean.valueOf(z)));
        return this;
    }

    private void a(i iVar) {
        if (this.f1624f != null) {
            if (!iVar.e() || getSerializeNulls()) {
                ((k) peek()).a(this.f1624f, iVar);
            }
            this.f1624f = null;
        } else if (this.e.isEmpty()) {
            this.f1625g = iVar;
        } else {
            i peek = peek();
            if (peek instanceof f) {
                ((f) peek).a(iVar);
                return;
            }
            throw new IllegalStateException();
        }
    }

    public JsonWriter value(Boolean bool) {
        if (bool == null) {
            nullValue();
            return this;
        }
        a(new l(bool));
        return this;
    }

    public JsonWriter value(double d) {
        if (isLenient() || (!Double.isNaN(d) && !Double.isInfinite(d))) {
            a(new l((Number) Double.valueOf(d)));
            return this;
        }
        throw new IllegalArgumentException("JSON forbids NaN and infinities: " + d);
    }

    public JsonWriter value(long j2) {
        a(new l((Number) Long.valueOf(j2)));
        return this;
    }

    public JsonWriter value(Number number) {
        if (number == null) {
            nullValue();
            return this;
        }
        if (!isLenient()) {
            double doubleValue = number.doubleValue();
            if (Double.isNaN(doubleValue) || Double.isInfinite(doubleValue)) {
                throw new IllegalArgumentException("JSON forbids NaN and infinities: " + number);
            }
        }
        a(new l(number));
        return this;
    }
}
