package androidx.navigation;

import android.net.Uri;
import android.os.Bundle;
import com.jeremyliao.liveeventbus.BuildConfig;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* compiled from: NavDeepLink */
class h {

    /* renamed from: f  reason: collision with root package name */
    private static final Pattern f732f = Pattern.compile("^[a-zA-Z]+[+\\w\\-.]*:");
    private final ArrayList<String> a = new ArrayList<>();
    private final Pattern b;
    private final boolean c;
    private final boolean d;
    private final Map<String, a> e = new HashMap();

    /* compiled from: NavDeepLink */
    private static class a {
        private String a;
        private ArrayList<String> b = new ArrayList<>();

        a() {
        }

        /* access modifiers changed from: package-private */
        public String a() {
            return this.a;
        }

        /* access modifiers changed from: package-private */
        public void b(String str) {
            this.a = str;
        }

        /* access modifiers changed from: package-private */
        public void a(String str) {
            this.b.add(str);
        }

        public int b() {
            return this.b.size();
        }

        /* access modifiers changed from: package-private */
        public String a(int i2) {
            return this.b.get(i2);
        }
    }

    h(String str) {
        Uri parse = Uri.parse(str);
        this.d = parse.getQuery() != null;
        StringBuilder sb = new StringBuilder("^");
        if (!f732f.matcher(str).find()) {
            sb.append("http[s]?://");
        }
        Pattern compile = Pattern.compile("\\{(.+?)\\}");
        if (this.d) {
            Matcher matcher = Pattern.compile("(\\?)").matcher(str);
            if (matcher.find()) {
                a(str.substring(0, matcher.start()), sb, compile);
            }
            this.c = false;
            for (String next : parse.getQueryParameterNames()) {
                StringBuilder sb2 = new StringBuilder();
                String queryParameter = parse.getQueryParameter(next);
                Matcher matcher2 = compile.matcher(queryParameter);
                a aVar = new a();
                int i2 = 0;
                while (matcher2.find()) {
                    aVar.a(matcher2.group(1));
                    sb2.append(Pattern.quote(queryParameter.substring(i2, matcher2.start())));
                    sb2.append("(.+?)?");
                    i2 = matcher2.end();
                }
                if (i2 < queryParameter.length()) {
                    sb2.append(Pattern.quote(queryParameter.substring(i2)));
                }
                aVar.b(sb2.toString().replace(".*", "\\E.*\\Q"));
                this.e.put(next, aVar);
            }
        } else {
            this.c = a(str, sb, compile);
        }
        this.b = Pattern.compile(sb.toString().replace(".*", "\\E.*\\Q"));
    }

    private boolean a(String str, StringBuilder sb, Pattern pattern) {
        Matcher matcher = pattern.matcher(str);
        boolean z = !str.contains(".*");
        int i2 = 0;
        while (matcher.find()) {
            this.a.add(matcher.group(1));
            sb.append(Pattern.quote(str.substring(i2, matcher.start())));
            sb.append("(.+?)");
            i2 = matcher.end();
            z = false;
        }
        if (i2 < str.length()) {
            sb.append(Pattern.quote(str.substring(i2)));
        }
        sb.append("($|(\\?(.)*))");
        return z;
    }

    /* access modifiers changed from: package-private */
    public boolean a() {
        return this.c;
    }

    /* access modifiers changed from: package-private */
    public Bundle a(Uri uri, Map<String, e> map) {
        Matcher matcher;
        Matcher matcher2 = this.b.matcher(uri.toString());
        if (!matcher2.matches()) {
            return null;
        }
        Bundle bundle = new Bundle();
        int size = this.a.size();
        int i2 = 0;
        while (i2 < size) {
            String str = this.a.get(i2);
            i2++;
            if (a(bundle, str, Uri.decode(matcher2.group(i2)), map.get(str))) {
                return null;
            }
        }
        if (this.d) {
            for (String next : this.e.keySet()) {
                a aVar = this.e.get(next);
                String queryParameter = uri.getQueryParameter(next);
                if (queryParameter != null) {
                    matcher = Pattern.compile(aVar.a()).matcher(queryParameter);
                    if (!matcher.matches()) {
                        return null;
                    }
                } else {
                    matcher = null;
                }
                int i3 = 0;
                while (true) {
                    if (i3 < aVar.b()) {
                        String decode = matcher != null ? Uri.decode(matcher.group(i3 + 1)) : null;
                        String a2 = aVar.a(i3);
                        e eVar = map.get(a2);
                        if (eVar != null && (decode == null || decode.replaceAll("[{}]", BuildConfig.FLAVOR).equals(a2))) {
                            if (eVar.a() != null) {
                                decode = eVar.a().toString();
                            } else if (eVar.d()) {
                                decode = null;
                            }
                        }
                        if (a(bundle, a2, decode, eVar)) {
                            return null;
                        }
                        i3++;
                    }
                }
            }
        }
        return bundle;
    }

    private boolean a(Bundle bundle, String str, String str2, e eVar) {
        if (eVar != null) {
            try {
                eVar.b().a(bundle, str, str2);
                return false;
            } catch (IllegalArgumentException unused) {
                return true;
            }
        } else {
            bundle.putString(str, str2);
            return false;
        }
    }
}
