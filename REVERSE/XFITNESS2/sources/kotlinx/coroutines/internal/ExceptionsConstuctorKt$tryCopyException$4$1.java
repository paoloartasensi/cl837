package kotlinx.coroutines.internal;

import kotlin.jvm.b.l;
import kotlin.jvm.internal.Lambda;
import kotlin.jvm.internal.i;

/* compiled from: ExceptionsConstuctor.kt */
final class ExceptionsConstuctorKt$tryCopyException$4$1 extends Lambda implements l {
    public static final ExceptionsConstuctorKt$tryCopyException$4$1 INSTANCE = new ExceptionsConstuctorKt$tryCopyException$4$1();

    ExceptionsConstuctorKt$tryCopyException$4$1() {
        super(1);
    }

    public final Void invoke(Throwable th) {
        i.b(th, "it");
        return null;
    }
}
