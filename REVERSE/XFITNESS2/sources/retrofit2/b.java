package retrofit2;

import retrofit2.i;

/* compiled from: lambda */
public final /* synthetic */ class b implements Runnable {
    private final /* synthetic */ i.b.a e;

    /* renamed from: f  reason: collision with root package name */
    private final /* synthetic */ f f2117f;

    /* renamed from: g  reason: collision with root package name */
    private final /* synthetic */ Throwable f2118g;

    public /* synthetic */ b(i.b.a aVar, f fVar, Throwable th) {
        this.e = aVar;
        this.f2117f = fVar;
        this.f2118g = th;
    }

    public final void run() {
        this.e.a(this.f2117f, this.f2118g);
    }
}
