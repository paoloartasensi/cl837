package j.a;

import android.os.Build;
import android.util.Log;
import com.jeremyliao.liveeventbus.BuildConfig;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* compiled from: Timber */
public final class a {
    private static final c[] a = new c[0];
    private static final List<c> b = new ArrayList();
    static volatile c[] c = a;
    private static final c d = new C0088a();

    public static void a(String str, Object... objArr) {
        d.a(str, objArr);
    }

    public static void b(String str, Object... objArr) {
        d.b(str, objArr);
    }

    public static void c(String str, Object... objArr) {
        d.d(str, objArr);
    }

    public static void d(String str, Object... objArr) {
        d.e(str, objArr);
    }

    /* compiled from: Timber */
    public static abstract class c {
        final ThreadLocal<String> a = new ThreadLocal<>();

        /* access modifiers changed from: package-private */
        public String a() {
            String str = this.a.get();
            if (str != null) {
                this.a.remove();
            }
            return str;
        }

        /* access modifiers changed from: protected */
        public abstract void a(int i2, String str, String str2, Throwable th);

        /* access modifiers changed from: protected */
        @Deprecated
        public boolean a(int i2) {
            return true;
        }

        public void b(String str, Object... objArr) {
            a(6, (Throwable) null, str, objArr);
        }

        /* access modifiers changed from: protected */
        public String c(String str, Object[] objArr) {
            return String.format(str, objArr);
        }

        public void d(String str, Object... objArr) {
            a(4, (Throwable) null, str, objArr);
        }

        public void e(String str, Object... objArr) {
            a(5, (Throwable) null, str, objArr);
        }

        public void a(String str, Object... objArr) {
            a(3, (Throwable) null, str, objArr);
        }

        /* access modifiers changed from: protected */
        public boolean a(String str, int i2) {
            return a(i2);
        }

        private void a(int i2, Throwable th, String str, Object... objArr) {
            String a2 = a();
            if (a(a2, i2)) {
                if (str != null && str.length() == 0) {
                    str = null;
                }
                if (str != null) {
                    if (objArr != null && objArr.length > 0) {
                        str = c(str, objArr);
                    }
                    if (th != null) {
                        str = str + "\n" + a(th);
                    }
                } else if (th != null) {
                    str = a(th);
                } else {
                    return;
                }
                a(i2, a2, str, th);
            }
        }

        private String a(Throwable th) {
            StringWriter stringWriter = new StringWriter(256);
            PrintWriter printWriter = new PrintWriter(stringWriter, false);
            th.printStackTrace(printWriter);
            printWriter.flush();
            return stringWriter.toString();
        }
    }

    public static c a(String str) {
        for (c cVar : c) {
            cVar.a.set(str);
        }
        return d;
    }

    /* renamed from: j.a.a$a  reason: collision with other inner class name */
    /* compiled from: Timber */
    static class C0088a extends c {
        C0088a() {
        }

        public void a(String str, Object... objArr) {
            for (c a : a.c) {
                a.a(str, objArr);
            }
        }

        public void b(String str, Object... objArr) {
            for (c b : a.c) {
                b.b(str, objArr);
            }
        }

        public void d(String str, Object... objArr) {
            for (c d : a.c) {
                d.d(str, objArr);
            }
        }

        public void e(String str, Object... objArr) {
            for (c e : a.c) {
                e.e(str, objArr);
            }
        }

        /* access modifiers changed from: protected */
        public void a(int i2, String str, String str2, Throwable th) {
            throw new AssertionError("Missing override for log method.");
        }
    }

    public static void a(c cVar) {
        if (cVar == null) {
            throw new NullPointerException("tree == null");
        } else if (cVar != d) {
            synchronized (b) {
                b.add(cVar);
                c = (c[]) b.toArray(new c[b.size()]);
            }
        } else {
            throw new IllegalArgumentException("Cannot plant Timber into itself.");
        }
    }

    /* compiled from: Timber */
    public static class b extends c {
        private static final Pattern b = Pattern.compile("(\\$\\d+)+$");

        /* access modifiers changed from: protected */
        public String a(StackTraceElement stackTraceElement) {
            String className = stackTraceElement.getClassName();
            Matcher matcher = b.matcher(className);
            if (matcher.find()) {
                className = matcher.replaceAll(BuildConfig.FLAVOR);
            }
            String substring = className.substring(className.lastIndexOf(46) + 1);
            return (substring.length() <= 23 || Build.VERSION.SDK_INT >= 24) ? substring : substring.substring(0, 23);
        }

        /* access modifiers changed from: package-private */
        public final String a() {
            String a = super.a();
            if (a != null) {
                return a;
            }
            StackTraceElement[] stackTrace = new Throwable().getStackTrace();
            if (stackTrace.length > 5) {
                return a(stackTrace[5]);
            }
            throw new IllegalStateException("Synthetic stacktrace didn't have enough elements: are you using proguard?");
        }

        /* access modifiers changed from: protected */
        public void a(int i2, String str, String str2, Throwable th) {
            int min;
            if (str2.length() >= 4000) {
                int i3 = 0;
                int length = str2.length();
                while (i3 < length) {
                    int indexOf = str2.indexOf(10, i3);
                    if (indexOf == -1) {
                        indexOf = length;
                    }
                    while (true) {
                        min = Math.min(indexOf, i3 + 4000);
                        String substring = str2.substring(i3, min);
                        if (i2 == 7) {
                            Log.wtf(str, substring);
                        } else {
                            Log.println(i2, str, substring);
                        }
                        if (min >= indexOf) {
                            break;
                        }
                        i3 = min;
                    }
                    i3 = min + 1;
                }
            } else if (i2 == 7) {
                Log.wtf(str, str2);
            } else {
                Log.println(i2, str, str2);
            }
        }
    }
}
