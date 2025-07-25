package com.google.gson.internal;

import com.google.gson.b;
import com.google.gson.d;
import com.google.gson.o;
import com.google.gson.p;
import com.google.gson.q.e;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;

public final class Excluder implements p, Cloneable {
    public static final Excluder k = new Excluder();
    private double e = -1.0d;

    /* renamed from: f  reason: collision with root package name */
    private int f1577f = 136;

    /* renamed from: g  reason: collision with root package name */
    private boolean f1578g = true;

    /* renamed from: h  reason: collision with root package name */
    private boolean f1579h;

    /* renamed from: i  reason: collision with root package name */
    private List<com.google.gson.a> f1580i = Collections.emptyList();

    /* renamed from: j  reason: collision with root package name */
    private List<com.google.gson.a> f1581j = Collections.emptyList();

    private boolean b(Class<?> cls, boolean z) {
        for (com.google.gson.a a2 : z ? this.f1580i : this.f1581j) {
            if (a2.a(cls)) {
                return true;
            }
        }
        return false;
    }

    private boolean c(Class<?> cls) {
        return cls.isMemberClass() && !d(cls);
    }

    private boolean d(Class<?> cls) {
        return (cls.getModifiers() & 8) != 0;
    }

    public <T> o<T> a(d dVar, com.google.gson.r.a<T> aVar) {
        Class<? super T> a2 = aVar.a();
        boolean a3 = a((Class<?>) a2);
        boolean z = a3 || b(a2, true);
        boolean z2 = a3 || b(a2, false);
        if (z || z2) {
            return new a(z2, z, dVar, aVar);
        }
        return null;
    }

    /* access modifiers changed from: protected */
    public Excluder clone() {
        try {
            return (Excluder) super.clone();
        } catch (CloneNotSupportedException e2) {
            throw new AssertionError(e2);
        }
    }

    class a extends o<T> {
        private o<T> a;
        final /* synthetic */ boolean b;
        final /* synthetic */ boolean c;
        final /* synthetic */ d d;
        final /* synthetic */ com.google.gson.r.a e;

        a(boolean z, boolean z2, d dVar, com.google.gson.r.a aVar) {
            this.b = z;
            this.c = z2;
            this.d = dVar;
            this.e = aVar;
        }

        private o<T> b() {
            o<T> oVar = this.a;
            if (oVar != null) {
                return oVar;
            }
            o<T> a2 = this.d.a((p) Excluder.this, this.e);
            this.a = a2;
            return a2;
        }

        public T a(JsonReader jsonReader) {
            if (!this.b) {
                return b().a(jsonReader);
            }
            jsonReader.skipValue();
            return null;
        }

        public void a(JsonWriter jsonWriter, T t) {
            if (this.c) {
                jsonWriter.nullValue();
            } else {
                b().a(jsonWriter, t);
            }
        }
    }

    private boolean b(Class<?> cls) {
        return !Enum.class.isAssignableFrom(cls) && (cls.isAnonymousClass() || cls.isLocalClass());
    }

    public boolean a(Field field, boolean z) {
        com.google.gson.q.a aVar;
        if ((this.f1577f & field.getModifiers()) != 0) {
            return true;
        }
        if ((this.e != -1.0d && !a((com.google.gson.q.d) field.getAnnotation(com.google.gson.q.d.class), (e) field.getAnnotation(e.class))) || field.isSynthetic()) {
            return true;
        }
        if (this.f1579h && ((aVar = (com.google.gson.q.a) field.getAnnotation(com.google.gson.q.a.class)) == null || (!z ? !aVar.deserialize() : !aVar.serialize()))) {
            return true;
        }
        if ((!this.f1578g && c(field.getType())) || b(field.getType())) {
            return true;
        }
        List<com.google.gson.a> list = z ? this.f1580i : this.f1581j;
        if (list.isEmpty()) {
            return false;
        }
        b bVar = new b(field);
        for (com.google.gson.a a2 : list) {
            if (a2.a(bVar)) {
                return true;
            }
        }
        return false;
    }

    private boolean a(Class<?> cls) {
        if (this.e != -1.0d && !a((com.google.gson.q.d) cls.getAnnotation(com.google.gson.q.d.class), (e) cls.getAnnotation(e.class))) {
            return true;
        }
        if ((this.f1578g || !c(cls)) && !b(cls)) {
            return false;
        }
        return true;
    }

    public boolean a(Class<?> cls, boolean z) {
        return a(cls) || b(cls, z);
    }

    private boolean a(com.google.gson.q.d dVar, e eVar) {
        return a(dVar) && a(eVar);
    }

    private boolean a(com.google.gson.q.d dVar) {
        return dVar == null || dVar.value() <= this.e;
    }

    private boolean a(e eVar) {
        return eVar == null || eVar.value() > this.e;
    }
}
