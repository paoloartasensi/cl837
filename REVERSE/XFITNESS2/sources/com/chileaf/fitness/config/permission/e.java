package com.chileaf.fitness.config.permission;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import kotlin.jvm.internal.i;

/* compiled from: PermissionsMap.kt */
public final class e {
    private static final AtomicInteger a = new AtomicInteger(100);
    private static final Map<Integer, d> b = new LinkedHashMap();
    public static final e c = new e();

    private e() {
    }

    public final int a(d dVar) {
        i.b(dVar, "callbacks");
        int andIncrement = a.getAndIncrement();
        b.put(Integer.valueOf(andIncrement), dVar);
        return andIncrement;
    }

    public final d a(int i2) {
        d dVar = b.get(Integer.valueOf(i2));
        b.remove(Integer.valueOf(i2));
        return dVar;
    }
}
