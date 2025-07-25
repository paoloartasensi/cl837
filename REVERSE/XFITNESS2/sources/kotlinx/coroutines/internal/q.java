package kotlinx.coroutines.internal;

import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.c;
import kotlin.coroutines.jvm.internal.b;
import kotlin.jvm.internal.i;
import kotlinx.coroutines.a;
import kotlinx.coroutines.u;
import kotlinx.coroutines.y1;

/* compiled from: Scopes.kt */
public class q<T> extends a<T> implements b {

    /* renamed from: h  reason: collision with root package name */
    public final c<T> f1804h;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public q(CoroutineContext coroutineContext, c<? super T> cVar) {
        super(coroutineContext, true);
        i.b(coroutineContext, "context");
        i.b(cVar, "uCont");
        this.f1804h = cVar;
    }

    /* access modifiers changed from: protected */
    public void a(Object obj, int i2) {
        if (obj instanceof u) {
            Throwable th = ((u) obj).a;
            if (i2 != 4) {
                th = s.a(th, (c<?>) this.f1804h);
            }
            y1.a(this.f1804h, th, i2);
            return;
        }
        y1.b(this.f1804h, obj, i2);
    }

    /* access modifiers changed from: protected */
    public final boolean g() {
        return true;
    }

    public final b getCallerFrame() {
        return (b) this.f1804h;
    }

    public final StackTraceElement getStackTraceElement() {
        return null;
    }

    public int k() {
        return 2;
    }
}
