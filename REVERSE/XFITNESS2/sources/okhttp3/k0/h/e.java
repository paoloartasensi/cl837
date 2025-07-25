package okhttp3.k0.h;

import java.util.List;
import okhttp3.h0;
import okhttp3.q;
import okhttp3.r;
import okhttp3.y;
import okhttp3.z;
import okio.ByteString;

/* compiled from: HttpHeaders */
public final class e {
    static {
        ByteString.encodeUtf8("\"\\");
        ByteString.encodeUtf8("\t ,=");
    }

    public static long a(h0 h0Var) {
        return a(h0Var.n());
    }

    public static boolean b(h0 h0Var) {
        if (h0Var.t().e().equals("HEAD")) {
            return false;
        }
        int j2 = h0Var.j();
        if (((j2 >= 100 && j2 < 200) || j2 == 204 || j2 == 304) && a(h0Var) == -1 && !"chunked".equalsIgnoreCase(h0Var.b("Transfer-Encoding"))) {
            return false;
        }
        return true;
    }

    public static long a(y yVar) {
        return a(yVar.a("Content-Length"));
    }

    private static long a(String str) {
        if (str == null) {
            return -1;
        }
        try {
            return Long.parseLong(str);
        } catch (NumberFormatException unused) {
            return -1;
        }
    }

    public static void a(r rVar, z zVar, y yVar) {
        if (rVar != r.a) {
            List<q> a = q.a(zVar, yVar);
            if (!a.isEmpty()) {
                rVar.a(zVar, a);
            }
        }
    }

    public static int b(String str, int i2) {
        while (i2 < str.length() && ((r0 = str.charAt(i2)) == ' ' || r0 == 9)) {
            i2++;
        }
        return i2;
    }

    public static int a(String str, int i2, String str2) {
        while (i2 < str.length() && str2.indexOf(str.charAt(i2)) == -1) {
            i2++;
        }
        return i2;
    }

    public static int a(String str, int i2) {
        try {
            long parseLong = Long.parseLong(str);
            if (parseLong > 2147483647L) {
                return Integer.MAX_VALUE;
            }
            if (parseLong < 0) {
                return 0;
            }
            return (int) parseLong;
        } catch (NumberFormatException unused) {
            return i2;
        }
    }
}
