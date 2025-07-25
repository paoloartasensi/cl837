package kotlinx.coroutines.android;

import kotlin.jvm.b.l;
import kotlin.jvm.internal.Lambda;

/* compiled from: HandlerDispatcher.kt */
final class HandlerContext$scheduleResumeAfterDelay$1 extends Lambda implements l<Throwable, kotlin.l> {
    final /* synthetic */ Runnable $block;
    final /* synthetic */ HandlerContext this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    HandlerContext$scheduleResumeAfterDelay$1(HandlerContext handlerContext, Runnable runnable) {
        super(1);
        this.this$0 = handlerContext;
        this.$block = runnable;
    }

    public /* bridge */ /* synthetic */ Object invoke(Object obj) {
        invoke((Throwable) obj);
        return kotlin.l.a;
    }

    public final void invoke(Throwable th) {
        this.this$0.f1786f.removeCallbacks(this.$block);
    }
}
