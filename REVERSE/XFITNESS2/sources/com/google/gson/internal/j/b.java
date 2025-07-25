package com.google.gson.internal.j;

import com.google.gson.internal.c;
import java.lang.reflect.AccessibleObject;

/* compiled from: ReflectionAccessor */
public abstract class b {
    private static final b a = (c.b() < 9 ? new a() : new c());

    public static b a() {
        return a;
    }

    public abstract void a(AccessibleObject accessibleObject);
}
