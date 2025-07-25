package okio;

import java.io.Closeable;
import java.io.Flushable;

/* compiled from: Sink */
public interface q extends Closeable, Flushable {
    void a(c cVar, long j2);

    void close();

    s d();

    void flush();
}
