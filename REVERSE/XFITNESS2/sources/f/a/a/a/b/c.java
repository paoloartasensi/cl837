package f.a.a.a.b;

import android.util.Log;

/* compiled from: L */
public class c {
    public static boolean a = true;

    public static void a(Class<?> cls, String str) {
        if (a) {
            Log.e(cls.getName(), str);
        }
    }

    public static void b(Class<?> cls, String str) {
        if (a) {
            Log.i(cls.getName(), str);
        }
    }

    public static void c(String str, String str2) {
        if (a) {
            Log.i(str, str2);
        }
    }

    public static void a(String str, String str2) {
        if (a) {
            Log.d(str, str2);
        }
    }

    public static void b(String str, String str2) {
        if (a) {
            Log.e(str, str2);
        }
    }
}
