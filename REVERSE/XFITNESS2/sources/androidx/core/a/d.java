package androidx.core.a;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.ParcelFileDescriptor;
import android.system.ErrnoException;
import android.system.Os;
import android.system.OsConstants;
import android.util.Log;
import androidx.core.content.c.c;
import java.io.File;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/* compiled from: TypefaceCompatApi21Impl */
class d extends h {
    private static Class b = null;
    private static Constructor c = null;
    private static Method d = null;
    private static Method e = null;

    /* renamed from: f  reason: collision with root package name */
    private static boolean f432f = false;

    d() {
    }

    private static void a() {
        Method method;
        Class<?> cls;
        Method method2;
        if (!f432f) {
            f432f = true;
            Constructor<?> constructor = null;
            try {
                cls = Class.forName("android.graphics.FontFamily");
                Constructor<?> constructor2 = cls.getConstructor(new Class[0]);
                method = cls.getMethod("addFontWeightStyle", new Class[]{String.class, Integer.TYPE, Boolean.TYPE});
                method2 = Typeface.class.getMethod("createFromFamiliesWithDefault", new Class[]{Array.newInstance(cls, 1).getClass()});
                constructor = constructor2;
            } catch (ClassNotFoundException | NoSuchMethodException e2) {
                Log.e("TypefaceCompatApi21Impl", e2.getClass().getName(), e2);
                method2 = null;
                cls = null;
                method = null;
            }
            c = constructor;
            b = cls;
            d = method;
            e = method2;
        }
    }

    private static Object b() {
        a();
        try {
            return c.newInstance(new Object[0]);
        } catch (IllegalAccessException | InstantiationException | InvocationTargetException e2) {
            throw new RuntimeException(e2);
        }
    }

    private File a(ParcelFileDescriptor parcelFileDescriptor) {
        try {
            String readlink = Os.readlink("/proc/self/fd/" + parcelFileDescriptor.getFd());
            if (OsConstants.S_ISREG(Os.stat(readlink).st_mode)) {
                return new File(readlink);
            }
        } catch (ErrnoException unused) {
        }
        return null;
    }

    private static Typeface a(Object obj) {
        a();
        try {
            Object newInstance = Array.newInstance(b, 1);
            Array.set(newInstance, 0, obj);
            return (Typeface) e.invoke((Object) null, new Object[]{newInstance});
        } catch (IllegalAccessException | InvocationTargetException e2) {
            throw new RuntimeException(e2);
        }
    }

    private static boolean a(Object obj, String str, int i2, boolean z) {
        a();
        try {
            return ((Boolean) d.invoke(obj, new Object[]{str, Integer.valueOf(i2), Boolean.valueOf(z)})).booleanValue();
        } catch (IllegalAccessException | InvocationTargetException e2) {
            throw new RuntimeException(e2);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:34:0x004f, code lost:
        r4 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:?, code lost:
        r6.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:?, code lost:
        throw r4;
     */
    /* JADX WARNING: Exception block dominator not found, dom blocks: [] */
    /* JADX WARNING: Missing exception handler attribute for start block: B:37:0x0053 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:46:0x005c */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public android.graphics.Typeface a(android.content.Context r4, android.os.CancellationSignal r5, androidx.core.e.b.f[] r6, int r7) {
        /*
            r3 = this;
            int r0 = r6.length
            r1 = 0
            r2 = 1
            if (r0 >= r2) goto L_0x0006
            return r1
        L_0x0006:
            androidx.core.e.b$f r6 = r3.a((androidx.core.e.b.f[]) r6, (int) r7)
            android.content.ContentResolver r7 = r4.getContentResolver()
            android.net.Uri r6 = r6.c()     // Catch:{ IOException -> 0x005d }
            java.lang.String r0 = "r"
            android.os.ParcelFileDescriptor r5 = r7.openFileDescriptor(r6, r0, r5)     // Catch:{ IOException -> 0x005d }
            if (r5 != 0) goto L_0x0020
            if (r5 == 0) goto L_0x001f
            r5.close()     // Catch:{ IOException -> 0x005d }
        L_0x001f:
            return r1
        L_0x0020:
            java.io.File r6 = r3.a((android.os.ParcelFileDescriptor) r5)     // Catch:{ all -> 0x0054 }
            if (r6 == 0) goto L_0x0037
            boolean r7 = r6.canRead()     // Catch:{ all -> 0x0054 }
            if (r7 != 0) goto L_0x002d
            goto L_0x0037
        L_0x002d:
            android.graphics.Typeface r4 = android.graphics.Typeface.createFromFile(r6)     // Catch:{ all -> 0x0054 }
            if (r5 == 0) goto L_0x0036
            r5.close()     // Catch:{ IOException -> 0x005d }
        L_0x0036:
            return r4
        L_0x0037:
            java.io.FileInputStream r6 = new java.io.FileInputStream     // Catch:{ all -> 0x0054 }
            java.io.FileDescriptor r7 = r5.getFileDescriptor()     // Catch:{ all -> 0x0054 }
            r6.<init>(r7)     // Catch:{ all -> 0x0054 }
            android.graphics.Typeface r4 = super.a((android.content.Context) r4, (java.io.InputStream) r6)     // Catch:{ all -> 0x004d }
            r6.close()     // Catch:{ all -> 0x0054 }
            if (r5 == 0) goto L_0x004c
            r5.close()     // Catch:{ IOException -> 0x005d }
        L_0x004c:
            return r4
        L_0x004d:
            r4 = move-exception
            throw r4     // Catch:{ all -> 0x004f }
        L_0x004f:
            r4 = move-exception
            r6.close()     // Catch:{ all -> 0x0053 }
        L_0x0053:
            throw r4     // Catch:{ all -> 0x0054 }
        L_0x0054:
            r4 = move-exception
            throw r4     // Catch:{ all -> 0x0056 }
        L_0x0056:
            r4 = move-exception
            if (r5 == 0) goto L_0x005c
            r5.close()     // Catch:{ all -> 0x005c }
        L_0x005c:
            throw r4     // Catch:{ IOException -> 0x005d }
        L_0x005d:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.core.a.d.a(android.content.Context, android.os.CancellationSignal, androidx.core.e.b$f[], int):android.graphics.Typeface");
    }

    public Typeface a(Context context, c.b bVar, Resources resources, int i2) {
        Object b2 = b();
        c.C0015c[] a = bVar.a();
        int length = a.length;
        int i3 = 0;
        while (i3 < length) {
            c.C0015c cVar = a[i3];
            File a2 = i.a(context);
            if (a2 == null) {
                return null;
            }
            try {
                if (!i.a(a2, resources, cVar.b())) {
                    a2.delete();
                    return null;
                } else if (!a(b2, a2.getPath(), cVar.e(), cVar.f())) {
                    return null;
                } else {
                    a2.delete();
                    i3++;
                }
            } catch (RuntimeException unused) {
                return null;
            } finally {
                a2.delete();
            }
        }
        return a(b2);
    }
}
