package okhttp3;

import java.io.IOException;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSession;
import okhttp3.k0.e;

/* compiled from: Handshake */
public final class x {
    private final TlsVersion a;
    private final m b;
    private final List<Certificate> c;
    private final List<Certificate> d;

    private x(TlsVersion tlsVersion, m mVar, List<Certificate> list, List<Certificate> list2) {
        this.a = tlsVersion;
        this.b = mVar;
        this.c = list;
        this.d = list2;
    }

    public static x a(SSLSession sSLSession) {
        Certificate[] certificateArr;
        List list;
        List list2;
        String cipherSuite = sSLSession.getCipherSuite();
        if (cipherSuite == null) {
            throw new IllegalStateException("cipherSuite == null");
        } else if (!"SSL_NULL_WITH_NULL_NULL".equals(cipherSuite)) {
            m a2 = m.a(cipherSuite);
            String protocol = sSLSession.getProtocol();
            if (protocol == null) {
                throw new IllegalStateException("tlsVersion == null");
            } else if (!"NONE".equals(protocol)) {
                TlsVersion forJavaName = TlsVersion.forJavaName(protocol);
                try {
                    certificateArr = sSLSession.getPeerCertificates();
                } catch (SSLPeerUnverifiedException unused) {
                    certificateArr = null;
                }
                if (certificateArr != null) {
                    list = e.a((T[]) certificateArr);
                } else {
                    list = Collections.emptyList();
                }
                Certificate[] localCertificates = sSLSession.getLocalCertificates();
                if (localCertificates != null) {
                    list2 = e.a((T[]) localCertificates);
                } else {
                    list2 = Collections.emptyList();
                }
                return new x(forJavaName, a2, list, list2);
            } else {
                throw new IOException("tlsVersion == NONE");
            }
        } else {
            throw new IOException("cipherSuite == SSL_NULL_WITH_NULL_NULL");
        }
    }

    public List<Certificate> b() {
        return this.c;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof x)) {
            return false;
        }
        x xVar = (x) obj;
        if (!this.a.equals(xVar.a) || !this.b.equals(xVar.b) || !this.c.equals(xVar.c) || !this.d.equals(xVar.d)) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return ((((((527 + this.a.hashCode()) * 31) + this.b.hashCode()) * 31) + this.c.hashCode()) * 31) + this.d.hashCode();
    }

    public String toString() {
        return "Handshake{tlsVersion=" + this.a + " cipherSuite=" + this.b + " peerCertificates=" + a(this.c) + " localCertificates=" + a(this.d) + '}';
    }

    public m a() {
        return this.b;
    }

    private List<String> a(List<Certificate> list) {
        ArrayList arrayList = new ArrayList();
        for (Certificate next : list) {
            if (next instanceof X509Certificate) {
                arrayList.add(String.valueOf(((X509Certificate) next).getSubjectDN()));
            } else {
                arrayList.add(next.getType());
            }
        }
        return arrayList;
    }
}
