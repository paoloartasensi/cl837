package androidx.core.e;

import android.util.Base64;
import androidx.core.g.h;
import java.util.List;

/* compiled from: FontRequest */
public final class a {
    private final String a;
    private final String b;
    private final String c;
    private final List<List<byte[]>> d;
    private final int e = 0;

    /* renamed from: f  reason: collision with root package name */
    private final String f478f = (this.a + "-" + this.b + "-" + this.c);

    public a(String str, String str2, String str3, List<List<byte[]>> list) {
        h.a(str);
        this.a = str;
        h.a(str2);
        this.b = str2;
        h.a(str3);
        this.c = str3;
        h.a(list);
        this.d = list;
    }

    public List<List<byte[]>> a() {
        return this.d;
    }

    public int b() {
        return this.e;
    }

    public String c() {
        return this.f478f;
    }

    public String d() {
        return this.a;
    }

    public String e() {
        return this.b;
    }

    public String f() {
        return this.c;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("FontRequest {mProviderAuthority: " + this.a + ", mProviderPackage: " + this.b + ", mQuery: " + this.c + ", mCertificates:");
        for (int i2 = 0; i2 < this.d.size(); i2++) {
            sb.append(" [");
            List list = this.d.get(i2);
            for (int i3 = 0; i3 < list.size(); i3++) {
                sb.append(" \"");
                sb.append(Base64.encodeToString((byte[]) list.get(i3), 0));
                sb.append("\"");
            }
            sb.append(" ]");
        }
        sb.append("}");
        sb.append("mCertificatesArray: " + this.e);
        return sb.toString();
    }
}
