package okhttp3.k0.h;

import okhttp3.b0;
import okhttp3.i0;
import okio.e;

/* compiled from: RealResponseBody */
public final class h extends i0 {

    /* renamed from: f  reason: collision with root package name */
    private final String f2041f;

    /* renamed from: g  reason: collision with root package name */
    private final long f2042g;

    /* renamed from: h  reason: collision with root package name */
    private final e f2043h;

    public h(String str, long j2, e eVar) {
        this.f2041f = str;
        this.f2042g = j2;
        this.f2043h = eVar;
    }

    public long c() {
        return this.f2042g;
    }

    public b0 j() {
        String str = this.f2041f;
        if (str != null) {
            return b0.b(str);
        }
        return null;
    }

    public e m() {
        return this.f2043h;
    }
}
