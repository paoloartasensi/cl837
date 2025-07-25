package kotlinx.coroutines.android;

import android.os.Handler;
import android.os.Looper;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.internal.i;
import kotlin.l;
import kotlinx.coroutines.h;
import kotlinx.coroutines.o0;

/* compiled from: HandlerDispatcher.kt */
public final class HandlerContext extends a implements o0 {
    private volatile HandlerContext _immediate;
    private final HandlerContext e;
    /* access modifiers changed from: private */

    /* renamed from: f  reason: collision with root package name */
    public final Handler f1786f;

    /* renamed from: g  reason: collision with root package name */
    private final String f1787g;

    /* renamed from: h  reason: collision with root package name */
    private final boolean f1788h;

    /* compiled from: Runnable.kt */
    public static final class a implements Runnable {
        final /* synthetic */ HandlerContext e;

        /* renamed from: f  reason: collision with root package name */
        final /* synthetic */ h f1789f;

        public a(HandlerContext handlerContext, h hVar) {
            this.e = handlerContext;
            this.f1789f = hVar;
        }

        public final void run() {
            this.f1789f.a(this.e, l.a);
        }
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    private HandlerContext(Handler handler, String str, boolean z) {
        super((f) null);
        HandlerContext handlerContext = null;
        this.f1786f = handler;
        this.f1787g = str;
        this.f1788h = z;
        this._immediate = z ? this : handlerContext;
        HandlerContext handlerContext2 = this._immediate;
        if (handlerContext2 == null) {
            handlerContext2 = new HandlerContext(this.f1786f, this.f1787g, true);
            this._immediate = handlerContext2;
        }
        this.e = handlerContext2;
    }

    public void dispatch(CoroutineContext coroutineContext, Runnable runnable) {
        i.b(coroutineContext, "context");
        i.b(runnable, "block");
        this.f1786f.post(runnable);
    }

    public boolean equals(Object obj) {
        return (obj instanceof HandlerContext) && ((HandlerContext) obj).f1786f == this.f1786f;
    }

    public int hashCode() {
        return System.identityHashCode(this.f1786f);
    }

    public boolean isDispatchNeeded(CoroutineContext coroutineContext) {
        i.b(coroutineContext, "context");
        return !this.f1788h || (i.a((Object) Looper.myLooper(), (Object) this.f1786f.getLooper()) ^ true);
    }

    public String toString() {
        String str = this.f1787g;
        if (str == null) {
            String handler = this.f1786f.toString();
            i.a((Object) handler, "handler.toString()");
            return handler;
        } else if (!this.f1788h) {
            return str;
        } else {
            return this.f1787g + " [immediate]";
        }
    }

    public void a(long j2, h<? super l> hVar) {
        i.b(hVar, "continuation");
        a aVar = new a(this, hVar);
        this.f1786f.postDelayed(aVar, f.b(j2, 4611686018427387903L));
        hVar.b(new HandlerContext$scheduleResumeAfterDelay$1(this, aVar));
    }

    public HandlerContext n() {
        return this.e;
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public HandlerContext(Handler handler, String str) {
        this(handler, str, false);
        i.b(handler, "handler");
    }
}
