package kotlin;

import kotlin.jvm.b.a;
import kotlin.jvm.internal.i;

/* compiled from: LazyJVM.kt */
class g {
    public static <T> d<T> a(a<? extends T> aVar) {
        i.b(aVar, "initializer");
        return new SynchronizedLazyImpl(aVar, (Object) null, 2, (f) null);
    }

    public static <T> d<T> a(LazyThreadSafetyMode lazyThreadSafetyMode, a<? extends T> aVar) {
        i.b(lazyThreadSafetyMode, "mode");
        i.b(aVar, "initializer");
        int i2 = e.a[lazyThreadSafetyMode.ordinal()];
        if (i2 == 1) {
            return new SynchronizedLazyImpl(aVar, (Object) null, 2, (f) null);
        }
        if (i2 == 2) {
            return new SafePublicationLazyImpl(aVar);
        }
        if (i2 == 3) {
            return new UnsafeLazyImpl(aVar);
        }
        throw new NoWhenBranchMatchedException();
    }
}
