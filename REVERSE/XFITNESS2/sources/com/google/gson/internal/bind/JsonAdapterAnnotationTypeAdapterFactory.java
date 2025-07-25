package com.google.gson.internal.bind;

import com.google.gson.d;
import com.google.gson.internal.b;
import com.google.gson.o;
import com.google.gson.p;
import com.google.gson.r.a;

public final class JsonAdapterAnnotationTypeAdapterFactory implements p {
    private final b e;

    public JsonAdapterAnnotationTypeAdapterFactory(b bVar) {
        this.e = bVar;
    }

    public <T> o<T> a(d dVar, a<T> aVar) {
        com.google.gson.q.b bVar = (com.google.gson.q.b) aVar.a().getAnnotation(com.google.gson.q.b.class);
        if (bVar == null) {
            return null;
        }
        return a(this.e, dVar, aVar, bVar);
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v13, resolved type: com.google.gson.o<?>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v14, resolved type: com.google.gson.o} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v1, resolved type: com.google.gson.internal.bind.TreeTypeAdapter} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v15, resolved type: com.google.gson.internal.bind.TreeTypeAdapter} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v16, resolved type: com.google.gson.internal.bind.TreeTypeAdapter} */
    /* JADX WARNING: type inference failed for: r9v3, types: [com.google.gson.o<?>, com.google.gson.o] */
    /* access modifiers changed from: package-private */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.google.gson.o<?> a(com.google.gson.internal.b r9, com.google.gson.d r10, com.google.gson.r.a<?> r11, com.google.gson.q.b r12) {
        /*
            r8 = this;
            java.lang.Class r0 = r12.value()
            com.google.gson.r.a r0 = com.google.gson.r.a.a(r0)
            com.google.gson.internal.e r9 = r9.a(r0)
            java.lang.Object r9 = r9.a()
            boolean r0 = r9 instanceof com.google.gson.o
            if (r0 == 0) goto L_0x0017
            com.google.gson.o r9 = (com.google.gson.o) r9
            goto L_0x0075
        L_0x0017:
            boolean r0 = r9 instanceof com.google.gson.p
            if (r0 == 0) goto L_0x0022
            com.google.gson.p r9 = (com.google.gson.p) r9
            com.google.gson.o r9 = r9.a(r10, r11)
            goto L_0x0075
        L_0x0022:
            boolean r0 = r9 instanceof com.google.gson.n
            if (r0 != 0) goto L_0x005b
            boolean r1 = r9 instanceof com.google.gson.h
            if (r1 == 0) goto L_0x002b
            goto L_0x005b
        L_0x002b:
            java.lang.IllegalArgumentException r10 = new java.lang.IllegalArgumentException
            java.lang.StringBuilder r12 = new java.lang.StringBuilder
            r12.<init>()
            java.lang.String r0 = "Invalid attempt to bind an instance of "
            r12.append(r0)
            java.lang.Class r9 = r9.getClass()
            java.lang.String r9 = r9.getName()
            r12.append(r9)
            java.lang.String r9 = " as a @JsonAdapter for "
            r12.append(r9)
            java.lang.String r9 = r11.toString()
            r12.append(r9)
            java.lang.String r9 = ". @JsonAdapter value must be a TypeAdapter, TypeAdapterFactory, JsonSerializer or JsonDeserializer."
            r12.append(r9)
            java.lang.String r9 = r12.toString()
            r10.<init>(r9)
            throw r10
        L_0x005b:
            r1 = 0
            if (r0 == 0) goto L_0x0063
            r0 = r9
            com.google.gson.n r0 = (com.google.gson.n) r0
            r3 = r0
            goto L_0x0064
        L_0x0063:
            r3 = r1
        L_0x0064:
            boolean r0 = r9 instanceof com.google.gson.h
            if (r0 == 0) goto L_0x006b
            r1 = r9
            com.google.gson.h r1 = (com.google.gson.h) r1
        L_0x006b:
            r4 = r1
            com.google.gson.internal.bind.TreeTypeAdapter r9 = new com.google.gson.internal.bind.TreeTypeAdapter
            r7 = 0
            r2 = r9
            r5 = r10
            r6 = r11
            r2.<init>(r3, r4, r5, r6, r7)
        L_0x0075:
            if (r9 == 0) goto L_0x0081
            boolean r10 = r12.nullSafe()
            if (r10 == 0) goto L_0x0081
            com.google.gson.o r9 = r9.a()
        L_0x0081:
            return r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.gson.internal.bind.JsonAdapterAnnotationTypeAdapterFactory.a(com.google.gson.internal.b, com.google.gson.d, com.google.gson.r.a, com.google.gson.q.b):com.google.gson.o");
    }
}
