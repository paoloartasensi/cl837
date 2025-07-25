package com.google.gson.internal.bind;

import com.google.gson.d;
import com.google.gson.g;
import com.google.gson.h;
import com.google.gson.i;
import com.google.gson.m;
import com.google.gson.n;
import com.google.gson.o;
import com.google.gson.p;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

public final class TreeTypeAdapter<T> extends o<T> {
    private final n<T> a;
    private final h<T> b;
    final d c;
    private final com.google.gson.r.a<T> d;
    private final p e;

    /* renamed from: f  reason: collision with root package name */
    private final TreeTypeAdapter<T>.defpackage.b f1600f = new b();

    /* renamed from: g  reason: collision with root package name */
    private o<T> f1601g;

    private static final class SingleTypeFactory implements p {
        private final com.google.gson.r.a<?> e;

        /* renamed from: f  reason: collision with root package name */
        private final boolean f1602f;

        /* renamed from: g  reason: collision with root package name */
        private final Class<?> f1603g;

        /* renamed from: h  reason: collision with root package name */
        private final n<?> f1604h;

        /* renamed from: i  reason: collision with root package name */
        private final h<?> f1605i;

        public <T> o<T> a(d dVar, com.google.gson.r.a<T> aVar) {
            boolean z;
            com.google.gson.r.a<?> aVar2 = this.e;
            if (aVar2 != null) {
                z = aVar2.equals(aVar) || (this.f1602f && this.e.b() == aVar.a());
            } else {
                z = this.f1603g.isAssignableFrom(aVar.a());
            }
            if (z) {
                return new TreeTypeAdapter(this.f1604h, this.f1605i, dVar, aVar, this);
            }
            return null;
        }
    }

    private final class b implements m, g {
        private b(TreeTypeAdapter treeTypeAdapter) {
        }
    }

    public TreeTypeAdapter(n<T> nVar, h<T> hVar, d dVar, com.google.gson.r.a<T> aVar, p pVar) {
        this.a = nVar;
        this.b = hVar;
        this.c = dVar;
        this.d = aVar;
        this.e = pVar;
    }

    private o<T> b() {
        o<T> oVar = this.f1601g;
        if (oVar != null) {
            return oVar;
        }
        o<T> a2 = this.c.a(this.e, this.d);
        this.f1601g = a2;
        return a2;
    }

    public T a(JsonReader jsonReader) {
        if (this.b == null) {
            return b().a(jsonReader);
        }
        i a2 = com.google.gson.internal.h.a(jsonReader);
        if (a2.e()) {
            return null;
        }
        return this.b.a(a2, this.d.b(), this.f1600f);
    }

    public void a(JsonWriter jsonWriter, T t) {
        n<T> nVar = this.a;
        if (nVar == null) {
            b().a(jsonWriter, t);
        } else if (t == null) {
            jsonWriter.nullValue();
        } else {
            com.google.gson.internal.h.a(nVar.a(t, this.d.b(), this.f1600f), jsonWriter);
        }
    }
}
