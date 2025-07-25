package kotlinx.coroutines.scheduling;

import kotlin.jvm.internal.i;

/* compiled from: Tasks.kt */
public abstract class h implements Runnable {
    public long e;

    /* renamed from: f  reason: collision with root package name */
    public i f1840f;

    public h(long j2, i iVar) {
        i.b(iVar, "taskContext");
        this.e = j2;
        this.f1840f = iVar;
    }

    public final TaskMode a() {
        return this.f1840f.j();
    }

    public h() {
        this(0, g.f1839f);
    }
}
