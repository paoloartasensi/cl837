package kotlinx.coroutines.internal;

import com.jeremyliao.liveeventbus.BuildConfig;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.internal.i;
import kotlin.l;
import kotlinx.coroutines.h;
import kotlinx.coroutines.o0;
import kotlinx.coroutines.t1;

/* compiled from: MainDispatchers.kt */
final class n extends t1 implements o0 {
    private final Throwable e;

    /* renamed from: f  reason: collision with root package name */
    private final String f1803f;

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public /* synthetic */ n(Throwable th, String str, int i2, f fVar) {
        this(th, (i2 & 2) != 0 ? null : str);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:5:0x0023, code lost:
        if (r1 != null) goto L_0x0028;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final java.lang.Void o() {
        /*
            r4 = this;
            java.lang.Throwable r0 = r4.e
            if (r0 == 0) goto L_0x0037
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r1 = "Module with the Main dispatcher had failed to initialize"
            r0.append(r1)
            java.lang.String r1 = r4.f1803f
            if (r1 == 0) goto L_0x0026
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = ". "
            r2.append(r3)
            r2.append(r1)
            java.lang.String r1 = r2.toString()
            if (r1 == 0) goto L_0x0026
            goto L_0x0028
        L_0x0026:
            java.lang.String r1 = ""
        L_0x0028:
            r0.append(r1)
            java.lang.String r0 = r0.toString()
            java.lang.IllegalStateException r1 = new java.lang.IllegalStateException
            java.lang.Throwable r2 = r4.e
            r1.<init>(r0, r2)
            throw r1
        L_0x0037:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "Module with the Main dispatcher is missing. Add dependency providing the Main dispatcher, e.g. 'kotlinx-coroutines-android'"
            r0.<init>(r1)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.internal.n.o():java.lang.Void");
    }

    public boolean isDispatchNeeded(CoroutineContext coroutineContext) {
        i.b(coroutineContext, "context");
        o();
        throw null;
    }

    public t1 n() {
        return this;
    }

    public String toString() {
        String str;
        StringBuilder sb = new StringBuilder();
        sb.append("Main[missing");
        if (this.e != null) {
            str = ", cause=" + this.e;
        } else {
            str = BuildConfig.FLAVOR;
        }
        sb.append(str);
        sb.append(']');
        return sb.toString();
    }

    public n(Throwable th, String str) {
        this.e = th;
        this.f1803f = str;
    }

    public Void a(long j2, h<? super l> hVar) {
        i.b(hVar, "continuation");
        o();
        throw null;
    }

    public Void dispatch(CoroutineContext coroutineContext, Runnable runnable) {
        i.b(coroutineContext, "context");
        i.b(runnable, "block");
        o();
        throw null;
    }
}
