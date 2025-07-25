package okhttp3.k0.j;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;
import okhttp3.Protocol;
import okhttp3.d0;
import okhttp3.k0.e;
import okhttp3.k0.l.a;
import okhttp3.k0.l.b;
import okio.c;

/* compiled from: Platform */
public class f {
    private static final f a = b();
    private static final Logger b = Logger.getLogger(d0.class.getName());

    private static f b() {
        c b2;
        f b3 = a.b();
        if (b3 != null) {
            return b3;
        }
        f b4 = b.b();
        if (b4 != null) {
            return b4;
        }
        if (d() && (b2 = c.b()) != null) {
            return b2;
        }
        e b5 = e.b();
        if (b5 != null) {
            return b5;
        }
        f b6 = d.b();
        if (b6 != null) {
            return b6;
        }
        return new f();
    }

    public static f c() {
        return a;
    }

    public static boolean d() {
        if ("conscrypt".equals(e.a("okhttp.platform", (String) null))) {
            return true;
        }
        return "Conscrypt".equals(Security.getProviders()[0].getName());
    }

    public void a(Socket socket, InetSocketAddress inetSocketAddress, int i2) {
        socket.connect(inetSocketAddress, i2);
    }

    public void a(SSLSocket sSLSocket) {
    }

    public void a(SSLSocket sSLSocket, String str, List<Protocol> list) {
    }

    public void a(SSLSocketFactory sSLSocketFactory) {
    }

    public String b(SSLSocket sSLSocket) {
        return null;
    }

    public boolean b(String str) {
        return true;
    }

    public String toString() {
        return getClass().getSimpleName();
    }

    public void a(int i2, String str, Throwable th) {
        b.log(i2 == 5 ? Level.WARNING : Level.INFO, str, th);
    }

    public Object a(String str) {
        if (b.isLoggable(Level.FINE)) {
            return new Throwable(str);
        }
        return null;
    }

    public void a(String str, Object obj) {
        if (obj == null) {
            str = str + " To see where this was allocated, set the OkHttpClient logger level to FINE: Logger.getLogger(OkHttpClient.class.getName()).setLevel(Level.FINE);";
        }
        a(5, str, (Throwable) obj);
    }

    public static List<String> a(List<Protocol> list) {
        ArrayList arrayList = new ArrayList(list.size());
        int size = list.size();
        for (int i2 = 0; i2 < size; i2++) {
            Protocol protocol = list.get(i2);
            if (protocol != Protocol.HTTP_1_0) {
                arrayList.add(protocol.toString());
            }
        }
        return arrayList;
    }

    static byte[] b(List<Protocol> list) {
        c cVar = new c();
        int size = list.size();
        for (int i2 = 0; i2 < size; i2++) {
            Protocol protocol = list.get(i2);
            if (protocol != Protocol.HTTP_1_0) {
                cVar.writeByte(protocol.toString().length());
                cVar.a(protocol.toString());
            }
        }
        return cVar.o();
    }

    public okhttp3.k0.l.c a(X509TrustManager x509TrustManager) {
        return new a(b(x509TrustManager));
    }

    public SSLContext a() {
        try {
            return SSLContext.getInstance("TLS");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("No TLS provider", e);
        }
    }

    public okhttp3.k0.l.e b(X509TrustManager x509TrustManager) {
        return new b(x509TrustManager.getAcceptedIssuers());
    }
}
