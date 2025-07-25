package kotlin.coroutines.jvm.internal;

import java.lang.reflect.Field;
import kotlin.jvm.internal.i;

/* compiled from: DebugMetadata.kt */
public final class d {
    private static final c a(BaseContinuationImpl baseContinuationImpl) {
        return (c) baseContinuationImpl.getClass().getAnnotation(c.class);
    }

    private static final int b(BaseContinuationImpl baseContinuationImpl) {
        try {
            Field declaredField = baseContinuationImpl.getClass().getDeclaredField("label");
            i.a((Object) declaredField, "field");
            declaredField.setAccessible(true);
            Object obj = declaredField.get(baseContinuationImpl);
            if (!(obj instanceof Integer)) {
                obj = null;
            }
            Integer num = (Integer) obj;
            return (num != null ? num.intValue() : 0) - 1;
        } catch (Exception unused) {
            return -1;
        }
    }

    public static final StackTraceElement c(BaseContinuationImpl baseContinuationImpl) {
        int i2;
        String str;
        i.b(baseContinuationImpl, "$this$getStackTraceElementImpl");
        c a = a(baseContinuationImpl);
        if (a == null) {
            return null;
        }
        a(1, a.v());
        int b = b(baseContinuationImpl);
        if (b < 0) {
            i2 = -1;
        } else {
            i2 = a.l()[b];
        }
        String a2 = f.c.a(baseContinuationImpl);
        if (a2 == null) {
            str = a.c();
        } else {
            str = a2 + '/' + a.c();
        }
        return new StackTraceElement(str, a.m(), a.f(), i2);
    }

    private static final void a(int i2, int i3) {
        if (i3 > i2) {
            throw new IllegalStateException(("Debug metadata version mismatch. Expected: " + i2 + ", got " + i3 + ". Please update the Kotlin standard library.").toString());
        }
    }
}
