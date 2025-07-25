package okhttp3.k0.h;

import java.net.Proxy;
import okhttp3.f0;
import okhttp3.z;

/* compiled from: RequestLine */
public final class i {
    public static String a(f0 f0Var, Proxy.Type type) {
        StringBuilder sb = new StringBuilder();
        sb.append(f0Var.e());
        sb.append(' ');
        if (b(f0Var, type)) {
            sb.append(f0Var.g());
        } else {
            sb.append(a(f0Var.g()));
        }
        sb.append(" HTTP/1.1");
        return sb.toString();
    }

    private static boolean b(f0 f0Var, Proxy.Type type) {
        return !f0Var.d() && type == Proxy.Type.HTTP;
    }

    public static String a(z zVar) {
        String c = zVar.c();
        String e = zVar.e();
        if (e == null) {
            return c;
        }
        return c + '?' + e;
    }
}
