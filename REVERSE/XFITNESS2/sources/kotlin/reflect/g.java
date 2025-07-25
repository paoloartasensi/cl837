package kotlin.reflect;

import kotlin.jvm.b.p;
import kotlin.l;
import kotlin.reflect.f;

/* compiled from: KProperty.kt */
public interface g<T, R> extends i<T, R>, f<R> {

    /* compiled from: KProperty.kt */
    public interface a<T, R> extends f.a<R>, p<T, R, l> {
    }

    a<T, R> getSetter();
}
