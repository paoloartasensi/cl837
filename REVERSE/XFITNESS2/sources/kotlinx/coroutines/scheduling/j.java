package kotlinx.coroutines.scheduling;

import kotlin.jvm.internal.i;
import kotlinx.coroutines.k0;

/* compiled from: Tasks.kt */
public final class j extends h {

    /* renamed from: g  reason: collision with root package name */
    public final Runnable f1841g;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public j(Runnable runnable, long j2, i iVar) {
        super(j2, iVar);
        i.b(runnable, "block");
        i.b(iVar, "taskContext");
        this.f1841g = runnable;
    }

    public void run() {
        try {
            this.f1841g.run();
        } finally {
            this.f1840f.m();
        }
    }

    public String toString() {
        return "Task[" + k0.a((Object) this.f1841g) + '@' + k0.b(this.f1841g) + ", " + this.e + ", " + this.f1840f + ']';
    }
}
