package okhttp3;

import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import javax.net.ssl.SSLPeerUnverifiedException;
import okhttp3.k0.l.c;
import okio.ByteString;

/* compiled from: CertificatePinner */
public final class l {
    public static final l c = new a().a();
    private final Set<b> a;
    private final c b;

    /* compiled from: CertificatePinner */
    public static final class a {
        private final List<b> a = new ArrayList();

        public l a() {
            return new l(new LinkedHashSet(this.a), (c) null);
        }
    }

    /* compiled from: CertificatePinner */
    static final class b {
        final String a;
        final String b;
        final String c;
        final ByteString d;

        /* access modifiers changed from: package-private */
        public boolean a(String str) {
            if (!this.a.startsWith("*.")) {
                return str.equals(this.b);
            }
            int indexOf = str.indexOf(46);
            if ((str.length() - indexOf) - 1 == this.b.length()) {
                String str2 = this.b;
                if (str.regionMatches(false, indexOf + 1, str2, 0, str2.length())) {
                    return true;
                }
            }
            return false;
        }

        public boolean equals(Object obj) {
            if (obj instanceof b) {
                b bVar = (b) obj;
                return this.a.equals(bVar.a) && this.c.equals(bVar.c) && this.d.equals(bVar.d);
            }
        }

        public int hashCode() {
            return ((((527 + this.a.hashCode()) * 31) + this.c.hashCode()) * 31) + this.d.hashCode();
        }

        public String toString() {
            return this.c + this.d.base64();
        }
    }

    l(Set<b> set, c cVar) {
        this.a = set;
        this.b = cVar;
    }

    static ByteString b(X509Certificate x509Certificate) {
        return ByteString.of(x509Certificate.getPublicKey().getEncoded()).sha256();
    }

    public void a(String str, List<Certificate> list) {
        List<b> a2 = a(str);
        if (!a2.isEmpty()) {
            c cVar = this.b;
            if (cVar != null) {
                list = cVar.a(list, str);
            }
            int size = list.size();
            for (int i2 = 0; i2 < size; i2++) {
                X509Certificate x509Certificate = (X509Certificate) list.get(i2);
                int size2 = a2.size();
                ByteString byteString = null;
                ByteString byteString2 = null;
                for (int i3 = 0; i3 < size2; i3++) {
                    b bVar = a2.get(i3);
                    if (bVar.c.equals("sha256/")) {
                        if (byteString == null) {
                            byteString = b(x509Certificate);
                        }
                        if (bVar.d.equals(byteString)) {
                            return;
                        }
                    } else if (bVar.c.equals("sha1/")) {
                        if (byteString2 == null) {
                            byteString2 = a(x509Certificate);
                        }
                        if (bVar.d.equals(byteString2)) {
                            return;
                        }
                    } else {
                        throw new AssertionError("unsupported hashAlgorithm: " + bVar.c);
                    }
                }
            }
            StringBuilder sb = new StringBuilder();
            sb.append("Certificate pinning failure!");
            sb.append("\n  Peer certificate chain:");
            int size3 = list.size();
            for (int i4 = 0; i4 < size3; i4++) {
                X509Certificate x509Certificate2 = (X509Certificate) list.get(i4);
                sb.append("\n    ");
                sb.append(a((Certificate) x509Certificate2));
                sb.append(": ");
                sb.append(x509Certificate2.getSubjectDN().getName());
            }
            sb.append("\n  Pinned certificates for ");
            sb.append(str);
            sb.append(":");
            int size4 = a2.size();
            for (int i5 = 0; i5 < size4; i5++) {
                sb.append("\n    ");
                sb.append(a2.get(i5));
            }
            throw new SSLPeerUnverifiedException(sb.toString());
        }
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof l) {
            l lVar = (l) obj;
            if (!c.a(this.b, lVar.b) || !this.a.equals(lVar.a)) {
                return false;
            }
            return true;
        }
        return false;
    }

    public int hashCode() {
        return (defpackage.b.a(this.b) * 31) + this.a.hashCode();
    }

    /* access modifiers changed from: package-private */
    public List<b> a(String str) {
        List<b> emptyList = Collections.emptyList();
        for (b next : this.a) {
            if (next.a(str)) {
                if (emptyList.isEmpty()) {
                    emptyList = new ArrayList<>();
                }
                emptyList.add(next);
            }
        }
        return emptyList;
    }

    /* access modifiers changed from: package-private */
    public l a(c cVar) {
        if (c.a(this.b, cVar)) {
            return this;
        }
        return new l(this.a, cVar);
    }

    public static String a(Certificate certificate) {
        if (certificate instanceof X509Certificate) {
            return "sha256/" + b((X509Certificate) certificate).base64();
        }
        throw new IllegalArgumentException("Certificate pinning requires X509 certificates");
    }

    static ByteString a(X509Certificate x509Certificate) {
        return ByteString.of(x509Certificate.getPublicKey().getEncoded()).sha1();
    }
}
