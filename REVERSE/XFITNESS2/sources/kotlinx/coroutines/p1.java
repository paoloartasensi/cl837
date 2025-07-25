package kotlinx.coroutines;

import kotlin.TypeCastException;
import kotlin.jvm.internal.i;
import kotlinx.coroutines.k1;

/* compiled from: JobSupport.kt */
public abstract class p1<J extends k1> extends y implements v0, f1 {

    /* renamed from: h  reason: collision with root package name */
    public final J f1812h;

    public p1(J j2) {
        i.b(j2, "job");
        this.f1812h = j2;
    }

    public void a() {
        J j2 = this.f1812h;
        if (j2 != null) {
            ((q1) j2).a((p1<?>) this);
            return;
        }
        throw new TypeCastException("null cannot be cast to non-null type kotlinx.coroutines.JobSupport");
    }

    public u1 b() {
        return null;
    }

    public boolean isActive() {
        return true;
    }
}
