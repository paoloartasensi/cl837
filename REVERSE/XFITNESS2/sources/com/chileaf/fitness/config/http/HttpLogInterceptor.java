package com.chileaf.fitness.config.http;

import android.text.TextUtils;
import com.jeremyliao.liveeventbus.BuildConfig;
import java.io.ByteArrayInputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.zip.DataFormatException;
import java.util.zip.GZIPInputStream;
import java.util.zip.Inflater;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import okhttp3.a0;
import okhttp3.b0;
import okhttp3.f0;
import okhttp3.g0;
import okhttp3.h0;
import okhttp3.i0;
import okio.e;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class HttpLogInterceptor implements a0 {
    private final Charset a = Charset.forName("UTF-8");
    private final LogLevel b = LogLevel.NONE;
    private final b c = new c();

    private enum LogLevel {
        NONE,
        REQUEST,
        RESPONSE,
        ALL
    }

    public interface b {
        void a(long j2, boolean z, int i2, String str, List<String> list, String str2, String str3);

        void a(long j2, boolean z, int i2, String str, b0 b0Var, String str2, List<String> list, String str3, String str4);

        void a(f0 f0Var);

        void a(f0 f0Var, String str);
    }

    private static class c implements b {
        private static final String a = System.getProperty("line.separator");
        private static final String b = (a + a);
        private static final String[] c;
        private static final String[] d;

        static {
            String str = a;
            c = new String[]{str, "Omitted response body"};
            d = new String[]{str, "Omitted request body"};
        }

        private c() {
        }

        private String a() {
            return BuildConfig.FLAVOR;
        }

        private void a(String str, String str2) {
            if (!TextUtils.isEmpty(str2)) {
                j.a.a.a(str).d(str2, new Object[0]);
            }
        }

        private boolean b(String str) {
            return TextUtils.isEmpty(str) || "\n".equals(str) || "\t".equals(str) || TextUtils.isEmpty(str.trim());
        }

        private String c(String str) {
            return a() + str;
        }

        private String d(String str) {
            if (TextUtils.isEmpty(str)) {
                return "Empty/Null xml content";
            }
            try {
                StreamSource streamSource = new StreamSource(new StringReader(str));
                StreamResult streamResult = new StreamResult(new StringWriter());
                Transformer newTransformer = TransformerFactory.newInstance().newTransformer();
                newTransformer.setOutputProperty("indent", "yes");
                newTransformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
                newTransformer.transform(streamSource, streamResult);
                return streamResult.getWriter().toString().replaceFirst(">", ">\n");
            } catch (TransformerException unused) {
                return str;
            }
        }

        private String[] b(f0 f0Var) {
            String str;
            String yVar = f0Var.c().toString();
            StringBuilder sb = new StringBuilder();
            sb.append("Method: @");
            sb.append(f0Var.e());
            sb.append(b);
            if (b(yVar)) {
                str = BuildConfig.FLAVOR;
            } else {
                str = "Headers:" + a + a(yVar);
            }
            sb.append(str);
            return sb.toString().split(a);
        }

        public void a(f0 f0Var, String str) {
            String a2 = a(true);
            a(a2, "┌────── Request ───────────────────────────────────────────────────────────────────────────────────────────────────────────");
            a(a2, new String[]{"URL: " + f0Var.g()}, false);
            a(a2, b(f0Var), true);
            a(a2, (a + "Body:" + a + str).split(a), true);
            a(a2, "└──────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────");
        }

        private String b() {
            String className = Thread.currentThread().getStackTrace()[4].getClassName();
            String substring = className.substring(className.lastIndexOf(46) + 1);
            int indexOf = substring.indexOf(36);
            return indexOf == -1 ? substring : substring.substring(0, indexOf);
        }

        public void a(f0 f0Var) {
            String a2 = a(true);
            a(a2, "┌────── Request ───────────────────────────────────────────────────────────────────────────────────────────────────────────");
            a(a2, new String[]{"URL: " + f0Var.g()}, false);
            a(a2, b(f0Var), true);
            a(a2, d, true);
            a(a2, "└──────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────");
        }

        public void a(long j2, boolean z, int i2, String str, b0 b0Var, String str2, List<String> list, String str3, String str4) {
            String str5;
            if (HttpLogInterceptor.e(b0Var)) {
                str5 = HttpLogInterceptor.c(str2);
            } else {
                str5 = HttpLogInterceptor.i(b0Var) ? d(str2) : str2;
            }
            String a2 = a(false);
            a(a2, "┌────── Response ──────────────────────────────────────────────────────────────────────────────────────────────────────────");
            a(a2, new String[]{"URL: " + str4, "\n"}, true);
            a(a2, a(str, j2, i2, z, list, str3), true);
            a(a2, (a + "Body:" + a + str5).split(a), true);
            a(a2, "└──────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────");
        }

        public void a(long j2, boolean z, int i2, String str, List<String> list, String str2, String str3) {
            String a2 = a(false);
            String[] strArr = {"URL: " + str3, "\n"};
            a(a2, "┌────── Response ──────────────────────────────────────────────────────────────────────────────────────────────────────────");
            a(a2, strArr, true);
            a(a2, a(str, j2, i2, z, list, str2), true);
            a(a2, c, true);
            a(a2, "└──────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────");
        }

        private void a(String str, String[] strArr, boolean z) {
            for (String str2 : strArr) {
                int length = str2.length();
                int i2 = z ? 120 : length;
                int i3 = 0;
                while (i3 <= length / i2) {
                    int i4 = i3 * i2;
                    i3++;
                    int i5 = i3 * i2;
                    if (i5 > str2.length()) {
                        i5 = str2.length();
                    }
                    a(c(str), "│ " + str2.substring(i4, i5));
                }
            }
        }

        private String[] a(String str, long j2, int i2, boolean z, List<String> list, String str2) {
            String str3;
            String a2 = a(list);
            StringBuilder sb = new StringBuilder();
            boolean isEmpty = TextUtils.isEmpty(a2);
            String str4 = BuildConfig.FLAVOR;
            if (!isEmpty) {
                str3 = a2 + " - ";
            } else {
                str3 = str4;
            }
            sb.append(str3);
            sb.append("is success : ");
            sb.append(z);
            sb.append(" - ");
            sb.append("Received in: ");
            sb.append(j2);
            sb.append("ms");
            sb.append(b);
            sb.append("Status Code: ");
            sb.append(i2);
            sb.append(" / ");
            sb.append(str2);
            sb.append(b);
            if (!b(str)) {
                str4 = "Headers:" + a + a(str);
            }
            sb.append(str4);
            return sb.toString().split(a);
        }

        private String a(List<String> list) {
            StringBuilder sb = new StringBuilder();
            for (String append : list) {
                sb.append("/");
                sb.append(append);
            }
            return sb.toString();
        }

        private String a(String str) {
            String str2;
            String[] split = str.split(a);
            StringBuilder sb = new StringBuilder();
            int i2 = 0;
            if (split.length > 1) {
                while (i2 < split.length) {
                    if (i2 == 0) {
                        str2 = "┌ ";
                    } else {
                        str2 = i2 == split.length - 1 ? "└ " : "├ ";
                    }
                    sb.append(str2);
                    sb.append(split[i2]);
                    sb.append("\n");
                    i2++;
                }
            } else {
                int length = split.length;
                while (i2 < length) {
                    String str3 = split[i2];
                    sb.append("─ ");
                    sb.append(str3);
                    sb.append("\n");
                    i2++;
                }
            }
            return sb.toString();
        }

        private String a(boolean z) {
            if (z) {
                return b() + "-Request";
            }
            return b() + "-Response";
        }
    }

    private boolean a(char c2) {
        return ('0' <= c2 && c2 <= '9') || ('a' <= c2 && c2 <= 'f') || ('A' <= c2 && c2 <= 'F');
    }

    /* access modifiers changed from: private */
    public static String c(String str) {
        if (TextUtils.isEmpty(str)) {
            return "Empty/Null json content";
        }
        try {
            String trim = str.trim();
            if (trim.startsWith("{")) {
                return new JSONObject(trim).toString(4);
            }
            return trim.startsWith("[") ? new JSONArray(trim).toString(4) : trim;
        } catch (JSONException unused) {
            return str;
        }
    }

    private static boolean d(b0 b0Var) {
        if (b0Var == null || TextUtils.isEmpty(b0Var.a())) {
            return false;
        }
        return b0Var.a().toLowerCase().contains("html");
    }

    /* access modifiers changed from: private */
    public static boolean e(b0 b0Var) {
        if (b0Var == null || TextUtils.isEmpty(b0Var.a())) {
            return false;
        }
        return b0Var.a().toLowerCase().contains("json");
    }

    private boolean f(b0 b0Var) {
        if (b0Var == null || TextUtils.isEmpty(b0Var.b())) {
            return false;
        }
        if (h(b0Var) || g(b0Var) || e(b0Var) || c(b0Var) || d(b0Var) || i(b0Var)) {
            return true;
        }
        return false;
    }

    private static boolean g(b0 b0Var) {
        if (b0Var == null || TextUtils.isEmpty(b0Var.a())) {
            return false;
        }
        return b0Var.a().toLowerCase().contains("plain");
    }

    private static boolean h(b0 b0Var) {
        if (b0Var == null || TextUtils.isEmpty(b0Var.b())) {
            return false;
        }
        return b0Var.b().equals("text");
    }

    /* access modifiers changed from: private */
    public static boolean i(b0 b0Var) {
        if (b0Var == null || TextUtils.isEmpty(b0Var.a())) {
            return false;
        }
        return b0Var.a().toLowerCase().contains("xml");
    }

    private boolean b(String str) {
        int i2;
        int i3 = 0;
        while (i3 < str.length()) {
            if (str.charAt(i3) != '%' || (i2 = i3 + 2) >= str.length()) {
                i3++;
            } else {
                char charAt = str.charAt(i3 + 1);
                char charAt2 = str.charAt(i2);
                if (!a(charAt) || !a(charAt2)) {
                    return false;
                }
                return true;
            }
        }
        return false;
    }

    public h0 a(a0.a aVar) {
        f0 a2 = aVar.a();
        LogLevel logLevel = this.b;
        boolean z = false;
        if (logLevel == LogLevel.ALL || (logLevel != LogLevel.NONE && logLevel == LogLevel.REQUEST)) {
            if (a2.a() == null || !f(a2.a().b())) {
                this.c.a(a2);
            } else {
                this.c.a(a2, a(a2));
            }
        }
        LogLevel logLevel2 = this.b;
        if (logLevel2 == LogLevel.ALL || (logLevel2 != LogLevel.NONE && logLevel2 == LogLevel.RESPONSE)) {
            z = true;
        }
        long j2 = 0;
        long nanoTime = z ? System.nanoTime() : 0;
        try {
            h0 a3 = aVar.a(a2);
            if (z) {
                j2 = System.nanoTime();
            }
            i0 a4 = a3.a();
            String str = null;
            if (a4 != null && f(a4.j())) {
                str = a(a2, a3, z);
            }
            String str2 = str;
            if (z) {
                List<String> d = a2.g().d();
                String yVar = a3.n().toString();
                int j3 = a3.j();
                boolean o = a3.o();
                String p = a3.p();
                String zVar = a3.t().g().toString();
                if (a4 == null || !f(a4.j())) {
                    this.c.a(TimeUnit.NANOSECONDS.toMillis(j2 - nanoTime), o, j3, yVar, d, p, zVar);
                } else {
                    this.c.a(TimeUnit.NANOSECONDS.toMillis(j2 - nanoTime), o, j3, yVar, a4.j(), str2, d, p, zVar);
                }
            }
            return a3;
        } catch (Exception e) {
            Exception exc = e;
            exc.printStackTrace();
            throw exc;
        }
    }

    private String b(byte[] bArr, String str) {
        byte[] a2 = a(bArr);
        try {
            return new String(a2, 0, a2.length, str);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static boolean c(b0 b0Var) {
        if (b0Var == null || TextUtils.isEmpty(b0Var.a())) {
            return false;
        }
        return b0Var.a().toLowerCase().contains("x-www-form-urlencoded");
    }

    private String a(f0 f0Var, h0 h0Var, boolean z) {
        try {
            i0 a2 = h0Var.q().a().a();
            e m = a2.m();
            m.d(Long.MAX_VALUE);
            return a(a2, h0Var.n().a("Content-Encoding"), m.b().clone());
        } catch (IOException e) {
            e.printStackTrace();
            return "{\"error\": \"" + e.getMessage() + "\"}";
        }
    }

    private String a(i0 i0Var, String str, okio.c cVar) {
        Charset charset = this.a;
        b0 j2 = i0Var.j();
        if (j2 != null) {
            charset = j2.a(this.a);
        }
        if (str != null && str.equalsIgnoreCase("gzip")) {
            return a(cVar.o(), a(charset));
        }
        if (str == null || !str.equalsIgnoreCase("zlib")) {
            return cVar.a(charset);
        }
        return b(cVar.o(), a(charset));
    }

    private String a(f0 f0Var) {
        try {
            g0 a2 = f0Var.f().a().a();
            if (a2 == null) {
                return BuildConfig.FLAVOR;
            }
            okio.c cVar = new okio.c();
            a2.a(cVar);
            Charset charset = this.a;
            b0 b2 = a2.b();
            if (b2 != null) {
                charset = b2.a(this.a);
            }
            String a3 = cVar.a(charset);
            if (b(a3)) {
                a3 = URLDecoder.decode(a3, a(charset));
            }
            return c(a3);
        } catch (IOException e) {
            e.printStackTrace();
            return "{\"error\": \"" + e.getMessage() + "\"}";
        }
    }

    private byte[] a(byte[] bArr) {
        byte[] bArr2;
        Inflater inflater = new Inflater();
        int length = bArr.length;
        inflater.setInput(bArr, 0, length);
        ArrayList arrayList = new ArrayList();
        while (true) {
            bArr2 = null;
            try {
                if (inflater.needsInput()) {
                    break;
                }
                byte[] bArr3 = new byte[length];
                int inflate = inflater.inflate(bArr3);
                for (int i2 = 0; i2 < inflate; i2++) {
                    arrayList.add(Byte.valueOf(bArr3[i2]));
                }
            } catch (DataFormatException e) {
                e.printStackTrace();
            }
        }
        int size = arrayList.size();
        bArr2 = new byte[size];
        for (int i3 = 0; i3 < size; i3++) {
            bArr2[i3] = ((Byte) arrayList.get(i3)).byteValue();
        }
        inflater.end();
        return bArr2;
    }

    private String a(byte[] bArr, String str) {
        ByteArrayInputStream byteArrayInputStream;
        GZIPInputStream gZIPInputStream;
        int length = bArr.length;
        GZIPInputStream gZIPInputStream2 = null;
        try {
            byteArrayInputStream = new ByteArrayInputStream(bArr);
            try {
                gZIPInputStream = new GZIPInputStream(byteArrayInputStream, length);
            } catch (IOException e) {
                e = e;
                gZIPInputStream = null;
                try {
                    e.printStackTrace();
                    a((Closeable) gZIPInputStream);
                    a((Closeable) byteArrayInputStream);
                    return null;
                } catch (Throwable th) {
                    th = th;
                    gZIPInputStream2 = gZIPInputStream;
                    a((Closeable) gZIPInputStream2);
                    a((Closeable) byteArrayInputStream);
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
                a((Closeable) gZIPInputStream2);
                a((Closeable) byteArrayInputStream);
                throw th;
            }
            try {
                StringBuilder sb = new StringBuilder();
                byte[] bArr2 = new byte[length];
                while (true) {
                    int read = gZIPInputStream.read(bArr2);
                    if (read != -1) {
                        sb.append(new String(bArr2, 0, read, str));
                    } else {
                        String sb2 = sb.toString();
                        a((Closeable) gZIPInputStream);
                        a((Closeable) byteArrayInputStream);
                        return sb2;
                    }
                }
            } catch (IOException e2) {
                e = e2;
                e.printStackTrace();
                a((Closeable) gZIPInputStream);
                a((Closeable) byteArrayInputStream);
                return null;
            }
        } catch (IOException e3) {
            e = e3;
            gZIPInputStream = null;
            byteArrayInputStream = null;
            e.printStackTrace();
            a((Closeable) gZIPInputStream);
            a((Closeable) byteArrayInputStream);
            return null;
        } catch (Throwable th3) {
            th = th3;
            byteArrayInputStream = null;
            a((Closeable) gZIPInputStream2);
            a((Closeable) byteArrayInputStream);
            throw th;
        }
    }

    private void a(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (RuntimeException e) {
                throw e;
            } catch (Exception unused) {
            }
        }
    }

    private String a(Charset charset) {
        String charset2 = charset.toString();
        int indexOf = charset2.indexOf(91);
        if (indexOf == -1) {
            return charset2;
        }
        return charset2.substring(indexOf + 1, charset2.length() - 1);
    }
}
