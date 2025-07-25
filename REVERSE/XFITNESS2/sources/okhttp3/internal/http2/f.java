package okhttp3.internal.http2;

import java.io.Closeable;
import java.io.EOFException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import okhttp3.internal.http2.b;
import okio.ByteString;
import okio.c;
import okio.e;
import okio.r;
import okio.s;

/* compiled from: Http2Reader */
final class f implements Closeable {

    /* renamed from: i  reason: collision with root package name */
    static final Logger f1992i = Logger.getLogger(c.class.getName());
    private final e e;

    /* renamed from: f  reason: collision with root package name */
    private final a f1993f;

    /* renamed from: g  reason: collision with root package name */
    private final boolean f1994g;

    /* renamed from: h  reason: collision with root package name */
    final b.a f1995h;

    /* compiled from: Http2Reader */
    static final class a implements r {
        private final e e;

        /* renamed from: f  reason: collision with root package name */
        int f1996f;

        /* renamed from: g  reason: collision with root package name */
        byte f1997g;

        /* renamed from: h  reason: collision with root package name */
        int f1998h;

        /* renamed from: i  reason: collision with root package name */
        int f1999i;

        /* renamed from: j  reason: collision with root package name */
        short f2000j;

        a(e eVar) {
            this.e = eVar;
        }

        private void a() {
            int i2 = this.f1998h;
            int a = f.a(this.e);
            this.f1999i = a;
            this.f1996f = a;
            byte readByte = (byte) (this.e.readByte() & 255);
            this.f1997g = (byte) (this.e.readByte() & 255);
            if (f.f1992i.isLoggable(Level.FINE)) {
                f.f1992i.fine(c.a(true, this.f1998h, this.f1996f, readByte, this.f1997g));
            }
            int readInt = this.e.readInt() & Integer.MAX_VALUE;
            this.f1998h = readInt;
            if (readByte != 9) {
                c.b("%s != TYPE_CONTINUATION", Byte.valueOf(readByte));
                throw null;
            } else if (readInt != i2) {
                c.b("TYPE_CONTINUATION streamId changed", new Object[0]);
                throw null;
            }
        }

        public long b(c cVar, long j2) {
            while (true) {
                int i2 = this.f1999i;
                if (i2 == 0) {
                    this.e.skip((long) this.f2000j);
                    this.f2000j = 0;
                    if ((this.f1997g & 4) != 0) {
                        return -1;
                    }
                    a();
                } else {
                    long b = this.e.b(cVar, Math.min(j2, (long) i2));
                    if (b == -1) {
                        return -1;
                    }
                    this.f1999i = (int) (((long) this.f1999i) - b);
                    return b;
                }
            }
        }

        public void close() {
        }

        public s d() {
            return this.e.d();
        }
    }

    /* compiled from: Http2Reader */
    interface b {
        void a();

        void a(int i2, int i3, int i4, boolean z);

        void a(int i2, int i3, List<a> list);

        void a(int i2, long j2);

        void a(int i2, ErrorCode errorCode);

        void a(int i2, ErrorCode errorCode, ByteString byteString);

        void a(boolean z, int i2, int i3);

        void a(boolean z, int i2, int i3, List<a> list);

        void a(boolean z, int i2, e eVar, int i3);

        void a(boolean z, k kVar);
    }

    f(e eVar, boolean z) {
        this.e = eVar;
        this.f1994g = z;
        a aVar = new a(eVar);
        this.f1993f = aVar;
        this.f1995h = new b.a(4096, aVar);
    }

    private void b(b bVar, int i2, byte b2, int i3) {
        if (i2 < 8) {
            c.b("TYPE_GOAWAY length < 8: %s", Integer.valueOf(i2));
            throw null;
        } else if (i3 == 0) {
            int readInt = this.e.readInt();
            int readInt2 = this.e.readInt();
            int i4 = i2 - 8;
            ErrorCode fromHttp2 = ErrorCode.fromHttp2(readInt2);
            if (fromHttp2 != null) {
                ByteString byteString = ByteString.EMPTY;
                if (i4 > 0) {
                    byteString = this.e.b((long) i4);
                }
                bVar.a(readInt, fromHttp2, byteString);
                return;
            }
            c.b("TYPE_GOAWAY unexpected error code: %d", Integer.valueOf(readInt2));
            throw null;
        } else {
            c.b("TYPE_GOAWAY streamId != 0", new Object[0]);
            throw null;
        }
    }

    private void c(b bVar, int i2, byte b2, int i3) {
        short s = 0;
        if (i3 != 0) {
            boolean z = (b2 & 1) != 0;
            if ((b2 & 8) != 0) {
                s = (short) (this.e.readByte() & 255);
            }
            if ((b2 & 32) != 0) {
                a(bVar, i3);
                i2 -= 5;
            }
            bVar.a(z, i3, -1, a(a(i2, b2, s), s, b2, i3));
            return;
        }
        c.b("PROTOCOL_ERROR: TYPE_HEADERS streamId == 0", new Object[0]);
        throw null;
    }

    private void d(b bVar, int i2, byte b2, int i3) {
        boolean z = false;
        if (i2 != 8) {
            c.b("TYPE_PING length != 8: %s", Integer.valueOf(i2));
            throw null;
        } else if (i3 == 0) {
            int readInt = this.e.readInt();
            int readInt2 = this.e.readInt();
            if ((b2 & 1) != 0) {
                z = true;
            }
            bVar.a(z, readInt, readInt2);
        } else {
            c.b("TYPE_PING streamId != 0", new Object[0]);
            throw null;
        }
    }

    private void e(b bVar, int i2, byte b2, int i3) {
        if (i2 != 5) {
            c.b("TYPE_PRIORITY length: %d != 5", Integer.valueOf(i2));
            throw null;
        } else if (i3 != 0) {
            a(bVar, i3);
        } else {
            c.b("TYPE_PRIORITY streamId == 0", new Object[0]);
            throw null;
        }
    }

    private void f(b bVar, int i2, byte b2, int i3) {
        short s = 0;
        if (i3 != 0) {
            if ((b2 & 8) != 0) {
                s = (short) (this.e.readByte() & 255);
            }
            bVar.a(i3, this.e.readInt() & Integer.MAX_VALUE, a(a(i2 - 4, b2, s), s, b2, i3));
            return;
        }
        c.b("PROTOCOL_ERROR: TYPE_PUSH_PROMISE streamId == 0", new Object[0]);
        throw null;
    }

    private void g(b bVar, int i2, byte b2, int i3) {
        if (i2 != 4) {
            c.b("TYPE_RST_STREAM length: %d != 4", Integer.valueOf(i2));
            throw null;
        } else if (i3 != 0) {
            int readInt = this.e.readInt();
            ErrorCode fromHttp2 = ErrorCode.fromHttp2(readInt);
            if (fromHttp2 != null) {
                bVar.a(i3, fromHttp2);
                return;
            }
            c.b("TYPE_RST_STREAM unexpected error code: %d", Integer.valueOf(readInt));
            throw null;
        } else {
            c.b("TYPE_RST_STREAM streamId == 0", new Object[0]);
            throw null;
        }
    }

    private void h(b bVar, int i2, byte b2, int i3) {
        if (i3 != 0) {
            c.b("TYPE_SETTINGS streamId != 0", new Object[0]);
            throw null;
        } else if ((b2 & 1) != 0) {
            if (i2 == 0) {
                bVar.a();
            } else {
                c.b("FRAME_SIZE_ERROR ack frame should be empty!", new Object[0]);
                throw null;
            }
        } else if (i2 % 6 == 0) {
            k kVar = new k();
            for (int i4 = 0; i4 < i2; i4 += 6) {
                short readShort = this.e.readShort() & 65535;
                int readInt = this.e.readInt();
                if (readShort != 2) {
                    if (readShort == 3) {
                        readShort = 4;
                    } else if (readShort == 4) {
                        readShort = 7;
                        if (readInt < 0) {
                            c.b("PROTOCOL_ERROR SETTINGS_INITIAL_WINDOW_SIZE > 2^31 - 1", new Object[0]);
                            throw null;
                        }
                    } else if (readShort == 5 && (readInt < 16384 || readInt > 16777215)) {
                        c.b("PROTOCOL_ERROR SETTINGS_MAX_FRAME_SIZE: %s", Integer.valueOf(readInt));
                        throw null;
                    }
                } else if (!(readInt == 0 || readInt == 1)) {
                    c.b("PROTOCOL_ERROR SETTINGS_ENABLE_PUSH != 0 or 1", new Object[0]);
                    throw null;
                }
                kVar.a(readShort, readInt);
            }
            bVar.a(false, kVar);
        } else {
            c.b("TYPE_SETTINGS length %% 6 != 0: %s", Integer.valueOf(i2));
            throw null;
        }
    }

    private void i(b bVar, int i2, byte b2, int i3) {
        if (i2 == 4) {
            long readInt = ((long) this.e.readInt()) & 2147483647L;
            if (readInt != 0) {
                bVar.a(i3, readInt);
                return;
            }
            c.b("windowSizeIncrement was 0", Long.valueOf(readInt));
            throw null;
        }
        c.b("TYPE_WINDOW_UPDATE length !=4: %s", Integer.valueOf(i2));
        throw null;
    }

    public void a(b bVar) {
        if (!this.f1994g) {
            ByteString b2 = this.e.b((long) c.a.size());
            if (f1992i.isLoggable(Level.FINE)) {
                f1992i.fine(okhttp3.k0.e.a("<< CONNECTION %s", b2.hex()));
            }
            if (!c.a.equals(b2)) {
                c.b("Expected a connection header but was %s", b2.utf8());
                throw null;
            }
        } else if (!a(true, bVar)) {
            c.b("Required SETTINGS preface not received", new Object[0]);
            throw null;
        }
    }

    public void close() {
        this.e.close();
    }

    public boolean a(boolean z, b bVar) {
        try {
            this.e.e(9);
            int a2 = a(this.e);
            if (a2 < 0 || a2 > 16384) {
                c.b("FRAME_SIZE_ERROR: %s", Integer.valueOf(a2));
                throw null;
            }
            byte readByte = (byte) (this.e.readByte() & 255);
            if (!z || readByte == 4) {
                byte readByte2 = (byte) (this.e.readByte() & 255);
                int readInt = this.e.readInt() & Integer.MAX_VALUE;
                if (f1992i.isLoggable(Level.FINE)) {
                    f1992i.fine(c.a(true, readInt, a2, readByte, readByte2));
                }
                switch (readByte) {
                    case 0:
                        a(bVar, a2, readByte2, readInt);
                        break;
                    case 1:
                        c(bVar, a2, readByte2, readInt);
                        break;
                    case 2:
                        e(bVar, a2, readByte2, readInt);
                        break;
                    case 3:
                        g(bVar, a2, readByte2, readInt);
                        break;
                    case 4:
                        h(bVar, a2, readByte2, readInt);
                        break;
                    case 5:
                        f(bVar, a2, readByte2, readInt);
                        break;
                    case 6:
                        d(bVar, a2, readByte2, readInt);
                        break;
                    case 7:
                        b(bVar, a2, readByte2, readInt);
                        break;
                    case 8:
                        i(bVar, a2, readByte2, readInt);
                        break;
                    default:
                        this.e.skip((long) a2);
                        break;
                }
                return true;
            }
            c.b("Expected a SETTINGS frame but was %s", Byte.valueOf(readByte));
            throw null;
        } catch (EOFException unused) {
            return false;
        }
    }

    private List<a> a(int i2, short s, byte b2, int i3) {
        a aVar = this.f1993f;
        aVar.f1999i = i2;
        aVar.f1996f = i2;
        aVar.f2000j = s;
        aVar.f1997g = b2;
        aVar.f1998h = i3;
        this.f1995h.c();
        return this.f1995h.a();
    }

    private void a(b bVar, int i2, byte b2, int i3) {
        short s = 0;
        if (i3 != 0) {
            boolean z = true;
            boolean z2 = (b2 & 1) != 0;
            if ((b2 & 32) == 0) {
                z = false;
            }
            if (!z) {
                if ((b2 & 8) != 0) {
                    s = (short) (this.e.readByte() & 255);
                }
                bVar.a(z2, i3, this.e, a(i2, b2, s));
                this.e.skip((long) s);
                return;
            }
            c.b("PROTOCOL_ERROR: FLAG_COMPRESSED without SETTINGS_COMPRESS_DATA", new Object[0]);
            throw null;
        }
        c.b("PROTOCOL_ERROR: TYPE_DATA streamId == 0", new Object[0]);
        throw null;
    }

    private void a(b bVar, int i2) {
        int readInt = this.e.readInt();
        bVar.a(i2, readInt & Integer.MAX_VALUE, (this.e.readByte() & 255) + 1, (Integer.MIN_VALUE & readInt) != 0);
    }

    static int a(e eVar) {
        return (eVar.readByte() & 255) | ((eVar.readByte() & 255) << 16) | ((eVar.readByte() & 255) << 8);
    }

    static int a(int i2, byte b2, short s) {
        if ((b2 & 8) != 0) {
            i2--;
        }
        if (s <= i2) {
            return (short) (i2 - s);
        }
        c.b("PROTOCOL_ERROR padding %s > remaining length %s", Short.valueOf(s), Integer.valueOf(i2));
        throw null;
    }
}
