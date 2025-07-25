package okhttp3.k0.j;

import android.annotation.SuppressLint;
import android.net.ssl.SSLSockets;
import java.lang.reflect.Method;
import java.util.List;
import javax.net.ssl.SSLParameters;
import javax.net.ssl.SSLSocket;
import okhttp3.Protocol;
import org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement;

@SuppressLint({"NewApi"})
/* compiled from: Android10Platform */
class a extends b {
    a(Class<?> cls) {
        super(cls, (Class<?>) null, (Method) null, (Method) null, (Method) null, (Method) null);
    }

    private void c(SSLSocket sSLSocket) {
        if (SSLSockets.isSupportedSocket(sSLSocket)) {
            SSLSockets.setUseSessionTickets(sSLSocket, true);
        }
    }

    @SuppressLint({"NewApi"})
    @IgnoreJRERequirement
    public void a(SSLSocket sSLSocket, String str, List<Protocol> list) {
        c(sSLSocket);
        SSLParameters sSLParameters = sSLSocket.getSSLParameters();
        sSLParameters.setApplicationProtocols((String[]) f.a(list).toArray(new String[0]));
        sSLSocket.setSSLParameters(sSLParameters);
    }

    @IgnoreJRERequirement
    public String b(SSLSocket sSLSocket) {
        String applicationProtocol = sSLSocket.getApplicationProtocol();
        if (applicationProtocol == null || applicationProtocol.isEmpty()) {
            return null;
        }
        return applicationProtocol;
    }

    public static f b() {
        try {
            if (b.e() >= 29) {
                return new a(Class.forName("com.android.org.conscrypt.SSLParametersImpl"));
            }
            return null;
        } catch (ReflectiveOperationException unused) {
            return null;
        }
    }
}
