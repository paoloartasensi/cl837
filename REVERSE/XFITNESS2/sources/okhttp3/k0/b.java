package okhttp3.k0;

import java.util.concurrent.ThreadFactory;

/* compiled from: lambda */
public final /* synthetic */ class b implements ThreadFactory {
    private final /* synthetic */ String a;
    private final /* synthetic */ boolean b;

    public /* synthetic */ b(String str, boolean z) {
        this.a = str;
        this.b = z;
    }

    public final Thread newThread(Runnable runnable) {
        return e.a(this.a, this.b, runnable);
    }
}
