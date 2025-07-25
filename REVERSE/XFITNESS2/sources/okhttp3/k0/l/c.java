package okhttp3.k0.l;

import java.security.cert.Certificate;
import java.util.List;
import javax.net.ssl.X509TrustManager;
import okhttp3.k0.j.f;

/* compiled from: CertificateChainCleaner */
public abstract class c {
    public static c a(X509TrustManager x509TrustManager) {
        return f.c().a(x509TrustManager);
    }

    public abstract List<Certificate> a(List<Certificate> list, String str);
}
