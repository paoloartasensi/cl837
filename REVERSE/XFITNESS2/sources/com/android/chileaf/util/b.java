package com.android.chileaf.util;

import android.os.Build;
import android.util.Log;
import com.jeremyliao.liveeventbus.BuildConfig;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* compiled from: LogUtil */
public final class b {
    private static final C0061b a = new a();

    public static void a(String str, Object... objArr) {
        a.a(str, objArr);
    }

    public static void b(String str, Object... objArr) {
        a.c(str, objArr);
    }

    /* renamed from: com.android.chileaf.util.b$b  reason: collision with other inner class name */
    /* compiled from: LogUtil */
    public static abstract class C0061b {
        private boolean a;
        private final ThreadLocal<String> b = new ThreadLocal<>();

        /* access modifiers changed from: package-private */
        public String a() {
            String str = this.b.get();
            if (str != null) {
                this.b.remove();
            }
            return str;
        }

        /* access modifiers changed from: protected */
        public abstract void a(int i2, String str, String str2, Throwable th);

        /* access modifiers changed from: protected */
        public String b(String str, Object[] objArr) {
            return String.format(str, objArr);
        }

        public void c(String str, Object... objArr) {
            a(5, (Throwable) null, str, objArr);
        }

        public void a(String str, Object... objArr) {
            a(3, (Throwable) null, str, objArr);
        }

        private void a(int i2, Throwable th, String str, Object... objArr) {
            String a2 = a();
            if (this.a) {
                if (str != null && str.length() == 0) {
                    str = null;
                }
                if (str != null) {
                    if (objArr != null && objArr.length > 0) {
                        str = b(str, objArr);
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

    /* compiled from: LogUtil */
    public static class a extends C0061b {
        private static final Pattern c = Pattern.compile("(\\$\\d+)+$");

        /* access modifiers changed from: protected */
        public String a(StackTraceElement stackTraceElement) {
            String className = stackTraceElement.getClassName();
            Matcher matcher = c.matcher(className);
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
