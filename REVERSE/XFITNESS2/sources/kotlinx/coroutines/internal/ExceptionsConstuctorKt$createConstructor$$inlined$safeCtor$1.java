package kotlinx.coroutines.internal;

import java.lang.reflect.Constructor;
import kotlin.Result;
import kotlin.TypeCastException;
import kotlin.jvm.b.l;
import kotlin.jvm.internal.Lambda;
import kotlin.jvm.internal.i;

/* compiled from: ExceptionsConstuctor.kt */
public final class ExceptionsConstuctorKt$createConstructor$$inlined$safeCtor$1 extends Lambda implements l<Throwable, Throwable> {
    final /* synthetic */ Constructor $constructor$inlined;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public ExceptionsConstuctorKt$createConstructor$$inlined$safeCtor$1(Constructor constructor) {
        super(1);
        this.$constructor$inlined = constructor;
    }

    public final Throwable invoke(Throwable th) {
        Object obj;
        i.b(th, "e");
        try {
            Result.a aVar = Result.Companion;
            Object newInstance = this.$constructor$inlined.newInstance(new Object[]{th.getMessage(), th});
            if (newInstance != null) {
                obj = Result.m1constructorimpl((Throwable) newInstance);
                if (Result.m6isFailureimpl(obj)) {
                    obj = null;
                }
                return (Throwable) obj;
            }
            throw new TypeCastException("null cannot be cast to non-null type kotlin.Throwable");
        } catch (Throwable th2) {
            Result.a aVar2 = Result.Companion;
            obj = Result.m1constructorimpl(kotlin.i.a(th2));
        }
    }
}
