package okhttp3.k0.j;

import com.jeremyliao.liveeventbus.BuildConfig;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import javax.net.ssl.SSLParameters;
import javax.net.ssl.SSLSocket;
import okhttp3.Protocol;

/* compiled from: Jdk9Platform */
final class e extends f {
    final Method c;
    final Method d;

    e(Method method, Method method2) {
        this.c = method;
        this.d = method2;
    }

    public void a(SSLSocket sSLSocket, String str, List<Protocol> list) {
        try {
            SSLParameters sSLParameters = sSLSocket.getSSLParameters();
            List<String> a = f.a(list);
            this.c.invoke(sSLParameters, new Object[]{a.toArray(new String[a.size()])});
            sSLSocket.setSSLParameters(sSLParameters);
        } catch (IllegalAccessException | InvocationTargetException e) {
            AssertionError assertionError = new AssertionError("failed to set SSL parameters");
            assertionError.initCause(e);
            throw assertionError;
        }
    }

    public String b(SSLSocket sSLSocket) {
        try {
            String str = (String) this.d.invoke(sSLSocket, new Object[0]);
            if (str == null || str.equals(BuildConfig.FLAVOR)) {
                return null;
            }
            return str;
        } catch (IllegalAccessException | InvocationTargetException e) {
            AssertionError assertionError = new AssertionError("failed to get ALPN selected protocol");
            assertionError.initCause(e);
            throw assertionError;
        }
    }

    public static e b() {
        try {
            return new e(SSLParameters.class.getMethod("setApplicationProtocols", new Class[]{String[].class}), SSLSocket.class.getMethod("getApplicationProtocol", new Class[0]));
        } catch (NoSuchMethodException unused) {
            return null;
        }
    }
}
