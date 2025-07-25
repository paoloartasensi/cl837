package retrofit2;

import kotlin.jvm.b.l;
import kotlin.jvm.internal.Lambda;

/* compiled from: KotlinExtensions.kt */
final class KotlinExtensions$await$$inlined$suspendCancellableCoroutine$lambda$2 extends Lambda implements l<Throwable, kotlin.l> {
    final /* synthetic */ d $this_await$inlined;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    KotlinExtensions$await$$inlined$suspendCancellableCoroutine$lambda$2(d dVar) {
        super(1);
        this.$this_await$inlined = dVar;
    }

    public /* bridge */ /* synthetic */ Object invoke(Object obj) {
        invoke((Throwable) obj);
        return kotlin.l.a;
    }

    public final void invoke(Throwable th) {
        this.$this_await$inlined.cancel();
    }
}
