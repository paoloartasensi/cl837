package okhttp3;

import java.util.concurrent.TimeUnit;
import okhttp3.internal.connection.g;

/* compiled from: ConnectionPool */
public final class o {
    final g a;

    public o() {
        this(5, 5, TimeUnit.MINUTES);
    }

    public o(int i2, long j2, TimeUnit timeUnit) {
        this.a = new g(i2, j2, timeUnit);
    }
}
