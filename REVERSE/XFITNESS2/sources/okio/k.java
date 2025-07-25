package okio;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

/* compiled from: Okio */
public final class k {
    static final Logger a = Logger.getLogger(k.class.getName());

    /* compiled from: Okio */
    class a implements q {
        final /* synthetic */ s e;

        /* renamed from: f  reason: collision with root package name */
        final /* synthetic */ OutputStream f2102f;

        a(s sVar, OutputStream outputStream) {
            this.e = sVar;
            this.f2102f = outputStream;
        }

        public void a(c cVar, long j2) {
            t.a(cVar.f2094f, 0, j2);
            while (j2 > 0) {
                this.e.e();
                o oVar = cVar.e;
                int min = (int) Math.min(j2, (long) (oVar.c - oVar.b));
                this.f2102f.write(oVar.a, oVar.b, min);
                int i2 = oVar.b + min;
                oVar.b = i2;
                long j3 = (long) min;
                j2 -= j3;
                cVar.f2094f -= j3;
                if (i2 == oVar.c) {
                    cVar.e = oVar.b();
                    p.a(oVar);
                }
            }
        }

        public void close() {
            this.f2102f.close();
        }

        public s d() {
            return this.e;
        }

        public void flush() {
            this.f2102f.flush();
        }

        public String toString() {
            return "sink(" + this.f2102f + ")";
        }
    }

    /* compiled from: Okio */
    class b implements r {
        final /* synthetic */ s e;

        /* renamed from: f  reason: collision with root package name */
        final /* synthetic */ InputStream f2103f;

        b(s sVar, InputStream inputStream) {
            this.e = sVar;
            this.f2103f = inputStream;
        }

        public long b(c cVar, long j2) {
            if (j2 < 0) {
                throw new IllegalArgumentException("byteCount < 0: " + j2);
            } else if (j2 == 0) {
                return 0;
            } else {
                try {
                    this.e.e();
                    o b = cVar.b(1);
                    int read = this.f2103f.read(b.a, b.c, (int) Math.min(j2, (long) (8192 - b.c)));
                    if (read == -1) {
                        return -1;
                    }
                    b.c += read;
                    long j3 = (long) read;
                    cVar.f2094f += j3;
                    return j3;
                } catch (AssertionError e2) {
                    if (k.a(e2)) {
                        throw new IOException(e2);
                    }
                    throw e2;
                }
            }
        }

        public void close() {
            this.f2103f.close();
        }

        public s d() {
            return this.e;
        }

        public String toString() {
            return "source(" + this.f2103f + ")";
        }
    }

    /* compiled from: Okio */
    class c extends a {
        final /* synthetic */ Socket k;

        c(Socket socket) {
            this.k = socket;
        }

        /* access modifiers changed from: protected */
        public IOException b(IOException iOException) {
            SocketTimeoutException socketTimeoutException = new SocketTimeoutException("timeout");
            if (iOException != null) {
                socketTimeoutException.initCause(iOException);
            }
            return socketTimeoutException;
        }

        /* access modifiers changed from: protected */
        public void i() {
            try {
                this.k.close();
            } catch (Exception e) {
                Logger logger = k.a;
                Level level = Level.WARNING;
                logger.log(level, "Failed to close timed out socket " + this.k, e);
            } catch (AssertionError e2) {
                if (k.a(e2)) {
                    Logger logger2 = k.a;
                    Level level2 = Level.WARNING;
                    logger2.log(level2, "Failed to close timed out socket " + this.k, e2);
                    return;
                }
                throw e2;
            }
        }
    }

    private k() {
    }

    public static e a(r rVar) {
        return new n(rVar);
    }

    public static r b(Socket socket) {
        if (socket == null) {
            throw new IllegalArgumentException("socket == null");
        } else if (socket.getInputStream() != null) {
            a c2 = c(socket);
            return c2.a(a(socket.getInputStream(), (s) c2));
        } else {
            throw new IOException("socket's input stream == null");
        }
    }

    private static a c(Socket socket) {
        return new c(socket);
    }

    public static d a(q qVar) {
        return new m(qVar);
    }

    private static q a(OutputStream outputStream, s sVar) {
        if (outputStream == null) {
            throw new IllegalArgumentException("out == null");
        } else if (sVar != null) {
            return new a(sVar, outputStream);
        } else {
            throw new IllegalArgumentException("timeout == null");
        }
    }

    public static q a(Socket socket) {
        if (socket == null) {
            throw new IllegalArgumentException("socket == null");
        } else if (socket.getOutputStream() != null) {
            a c2 = c(socket);
            return c2.a(a(socket.getOutputStream(), (s) c2));
        } else {
            throw new IOException("socket's output stream == null");
        }
    }

    public static r a(InputStream inputStream) {
        return a(inputStream, new s());
    }

    private static r a(InputStream inputStream, s sVar) {
        if (inputStream == null) {
            throw new IllegalArgumentException("in == null");
        } else if (sVar != null) {
            return new b(sVar, inputStream);
        } else {
            throw new IllegalArgumentException("timeout == null");
        }
    }

    static boolean a(AssertionError assertionError) {
        return (assertionError.getCause() == null || assertionError.getMessage() == null || !assertionError.getMessage().contains("getsockname failed")) ? false : true;
    }
}
