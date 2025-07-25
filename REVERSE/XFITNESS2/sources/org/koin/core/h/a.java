package org.koin.core.h;

import kotlin.Pair;
import kotlin.jvm.internal.i;
import kotlin.l;

/* compiled from: Measure.kt */
public final class a {
    public static final <T> Pair<T, Double> a(kotlin.jvm.b.a<? extends T> aVar) {
        i.b(aVar, "code");
        long nanoTime = System.nanoTime();
        Object invoke = aVar.invoke();
        double nanoTime2 = (double) (System.nanoTime() - nanoTime);
        Double.isNaN(nanoTime2);
        return new Pair<>(invoke, Double.valueOf(nanoTime2 / 1000000.0d));
    }

    public static final double b(kotlin.jvm.b.a<l> aVar) {
        i.b(aVar, "code");
        long nanoTime = System.nanoTime();
        aVar.invoke();
        double nanoTime2 = (double) (System.nanoTime() - nanoTime);
        Double.isNaN(nanoTime2);
        return nanoTime2 / 1000000.0d;
    }
}
