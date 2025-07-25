package okio;

import java.io.InterruptedIOException;
import java.util.concurrent.TimeUnit;

/* compiled from: Timeout */
public class s {
    public static final s d = new a();
    private boolean a;
    private long b;
    private long c;

    /* compiled from: Timeout */
    class a extends s {
        a() {
        }

        public s a(long j2) {
            return this;
        }

        public s a(long j2, TimeUnit timeUnit) {
            return this;
        }

        public void e() {
        }
    }

    public s a(long j2, TimeUnit timeUnit) {
        if (j2 < 0) {
            throw new IllegalArgumentException("timeout < 0: " + j2);
        } else if (timeUnit != null) {
            this.c = timeUnit.toNanos(j2);
            return this;
        } else {
            throw new IllegalArgumentException("unit == null");
        }
    }

    public s b() {
        this.c = 0;
        return this;
    }

    public long c() {
        if (this.a) {
            return this.b;
        }
        throw new IllegalStateException("No deadline");
    }

    public boolean d() {
        return this.a;
    }

    public void e() {
        if (Thread.interrupted()) {
            Thread.currentThread().interrupt();
            throw new InterruptedIOException("interrupted");
        } else if (this.a && this.b - System.nanoTime() <= 0) {
            throw new InterruptedIOException("deadline reached");
        }
    }

    public long f() {
        return this.c;
    }

    public s a(long j2) {
        this.a = true;
        this.b = j2;
        return this;
    }

    public s a() {
        this.a = false;
        return this;
    }
}
