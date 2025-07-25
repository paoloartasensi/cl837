package okhttp3.internal.connection;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.ProtocolException;
import java.net.UnknownServiceException;
import java.security.cert.CertificateException;
import java.util.Arrays;
import java.util.List;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLHandshakeException;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSocket;
import okhttp3.p;

/* compiled from: ConnectionSpecSelector */
final class c {
    private final List<p> a;
    private int b = 0;
    private boolean c;
    private boolean d;

    c(List<p> list) {
        this.a = list;
    }

    private boolean b(SSLSocket sSLSocket) {
        for (int i2 = this.b; i2 < this.a.size(); i2++) {
            if (this.a.get(i2).a(sSLSocket)) {
                return true;
            }
        }
        return false;
    }

    /* access modifiers changed from: package-private */
    public p a(SSLSocket sSLSocket) {
        p pVar;
        int i2 = this.b;
        int size = this.a.size();
        while (true) {
            if (i2 >= size) {
                pVar = null;
                break;
            }
            pVar = this.a.get(i2);
            if (pVar.a(sSLSocket)) {
                this.b = i2 + 1;
                break;
            }
            i2++;
        }
        if (pVar != null) {
            this.c = b(sSLSocket);
            okhttp3.k0.c.a.a(pVar, sSLSocket, this.d);
            return pVar;
        }
        throw new UnknownServiceException("Unable to find acceptable protocols. isFallback=" + this.d + ", modes=" + this.a + ", supported protocols=" + Arrays.toString(sSLSocket.getEnabledProtocols()));
    }

    /* access modifiers changed from: package-private */
    public boolean a(IOException iOException) {
        this.d = true;
        if (!this.c || (iOException instanceof ProtocolException) || (iOException instanceof InterruptedIOException)) {
            return false;
        }
        if ((!(iOException instanceof SSLHandshakeException) || !(iOException.getCause() instanceof CertificateException)) && !(iOException instanceof SSLPeerUnverifiedException)) {
            return iOException instanceof SSLException;
        }
        return false;
    }
}
