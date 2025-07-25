package okio;

import java.nio.channels.WritableByteChannel;

/* compiled from: BufferedSink */
public interface d extends q, WritableByteChannel {
    d a(long j2);

    d a(String str);

    d a(ByteString byteString);

    c b();

    d f();

    d f(long j2);

    void flush();

    d write(byte[] bArr);

    d write(byte[] bArr, int i2, int i3);

    d writeByte(int i2);

    d writeInt(int i2);

    d writeShort(int i2);
}
