package okio;

import java.io.InputStream;
import java.nio.channels.ReadableByteChannel;

/* compiled from: BufferedSource */
public interface e extends r, ReadableByteChannel {
    int a(l lVar);

    long a(byte b);

    long a(q qVar);

    ByteString b(long j2);

    @Deprecated
    c b();

    String c(long j2);

    boolean d(long j2);

    short e();

    void e(long j2);

    String g();

    byte[] g(long j2);

    c getBuffer();

    int h();

    boolean i();

    long k();

    InputStream l();

    byte readByte();

    void readFully(byte[] bArr);

    int readInt();

    short readShort();

    void skip(long j2);
}
