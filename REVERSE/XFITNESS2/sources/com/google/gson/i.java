package com.google.gson;

import com.google.gson.internal.h;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.io.StringWriter;

/* compiled from: JsonElement */
public abstract class i {
    public f a() {
        if (d()) {
            return (f) this;
        }
        throw new IllegalStateException("Not a JSON Array: " + this);
    }

    public k b() {
        if (f()) {
            return (k) this;
        }
        throw new IllegalStateException("Not a JSON Object: " + this);
    }

    public l c() {
        if (g()) {
            return (l) this;
        }
        throw new IllegalStateException("Not a JSON Primitive: " + this);
    }

    public boolean d() {
        return this instanceof f;
    }

    public boolean e() {
        return this instanceof j;
    }

    public boolean f() {
        return this instanceof k;
    }

    public boolean g() {
        return this instanceof l;
    }

    public String toString() {
        try {
            StringWriter stringWriter = new StringWriter();
            JsonWriter jsonWriter = new JsonWriter(stringWriter);
            jsonWriter.setLenient(true);
            h.a(this, jsonWriter);
            return stringWriter.toString();
        } catch (IOException e) {
            throw new AssertionError(e);
        }
    }
}
