package retrofit2;

import kotlin.jvm.b.l;
import kotlin.jvm.internal.Lambda;

/* compiled from: KotlinExtensions.kt */
final class KotlinExtensions$awaitResponse$$inlined$suspendCancellableCoroutine$lambda$1 extends Lambda implements l<Throwable, kotlin.l> {
    final /* synthetic */ d $this_awaitResponse$inlined;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    KotlinExtensions$awaitResponse$$inlined$suspendCancellableCoroutine$lambda$1(d dVar) {
        super(1);
        this.$this_awaitResponse$inlined = dVar;
    }

    public /* bridge */ /* synthetic */ Object invoke(Object obj) {
        invoke((Throwable) obj);
        return kotlin.l.a;
    }

    public final void invoke(Throwable th) {
        this.$this_awaitResponse$inlined.cancel();
    }
}
