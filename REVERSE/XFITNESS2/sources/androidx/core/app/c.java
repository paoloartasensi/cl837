package androidx.core.app;

import android.app.Activity;
import android.app.Application;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

/* compiled from: ActivityRecreator */
final class c {
    protected static final Class<?> a = a();
    protected static final Field b = b();
    protected static final Field c = c();
    protected static final Method d = b(a);
    protected static final Method e = a(a);

    /* renamed from: f  reason: collision with root package name */
    protected static final Method f448f = c(a);

    /* renamed from: g  reason: collision with root package name */
    private static final Handler f449g = new Handler(Looper.getMainLooper());

    /* compiled from: ActivityRecreator */
    static class a implements Runnable {
        final /* synthetic */ d e;

        /* renamed from: f  reason: collision with root package name */
        final /* synthetic */ Object f450f;

        a(d dVar, Object obj) {
            this.e = dVar;
            this.f450f = obj;
        }

        public void run() {
            this.e.a = this.f450f;
        }
    }

    /* compiled from: ActivityRecreator */
    static class b implements Runnable {
        final /* synthetic */ Application e;

        /* renamed from: f  reason: collision with root package name */
        final /* synthetic */ d f451f;

        b(Application application, d dVar) {
            this.e = application;
            this.f451f = dVar;
        }

        public void run() {
            this.e.unregisterActivityLifecycleCallbacks(this.f451f);
        }
    }

    /* renamed from: androidx.core.app.c$c  reason: collision with other inner class name */
    /* compiled from: ActivityRecreator */
    static class C0014c implements Runnable {
        final /* synthetic */ Object e;

        /* renamed from: f  reason: collision with root package name */
        final /* synthetic */ Object f452f;

        C0014c(Object obj, Object obj2) {
            this.e = obj;
            this.f452f = obj2;
        }

        public void run() {
            try {
                if (c.d != null) {
                    c.d.invoke(this.e, new Object[]{this.f452f, false, "AppCompat recreation"});
                    return;
                }
                c.e.invoke(this.e, new Object[]{this.f452f, false});
            } catch (RuntimeException e2) {
                if (e2.getClass() == RuntimeException.class && e2.getMessage() != null && e2.getMessage().startsWith("Unable to stop")) {
                    throw e2;
                }
            } catch (Throwable th) {
                Log.e("ActivityRecreator", "Exception while invoking performStopActivity", th);
            }
        }
    }

    /* compiled from: ActivityRecreator */
    private static final class d implements Application.ActivityLifecycleCallbacks {
        Object a;
        private Activity b;
        private boolean c = false;
        private boolean d = false;
        private boolean e = false;

        d(Activity activity) {
            this.b = activity;
        }

        public void onActivityCreated(Activity activity, Bundle bundle) {
        }

        public void onActivityDestroyed(Activity activity) {
            if (this.b == activity) {
                this.b = null;
                this.d = true;
            }
        }

        public void onActivityPaused(Activity activity) {
            if (this.d && !this.e && !this.c && c.a(this.a, activity)) {
                this.e = true;
                this.a = null;
            }
        }

        public void onActivityResumed(Activity activity) {
        }

        public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
        }

        public void onActivityStarted(Activity activity) {
            if (this.b == activity) {
                this.c = true;
            }
        }

        public void onActivityStopped(Activity activity) {
        }
    }

    static boolean a(Activity activity) {
        Object obj;
        Application application;
        d dVar;
        if (Build.VERSION.SDK_INT >= 28) {
            activity.recreate();
            return true;
        } else if (d() && f448f == null) {
            return false;
        } else {
            if (e == null && d == null) {
                return false;
            }
            try {
                Object obj2 = c.get(activity);
                if (obj2 == null || (obj = b.get(activity)) == null) {
                    return false;
                }
                application = activity.getApplication();
                dVar = new d(activity);
                application.registerActivityLifecycleCallbacks(dVar);
                f449g.post(new a(dVar, obj2));
                if (d()) {
                    f448f.invoke(obj, new Object[]{obj2, null, null, 0, false, null, null, false, false});
                } else {
                    activity.recreate();
                }
                f449g.post(new b(application, dVar));
                return true;
            } catch (Throwable unused) {
                return false;
            }
        }
    }

    private static Method b(Class<?> cls) {
        if (cls == null) {
            return null;
        }
        try {
            Method declaredMethod = cls.getDeclaredMethod("performStopActivity", new Class[]{IBinder.class, Boolean.TYPE, String.class});
            declaredMethod.setAccessible(true);
            return declaredMethod;
        } catch (Throwable unused) {
            return null;
        }
    }

    private static Method c(Class<?> cls) {
        if (d() && cls != null) {
            try {
                Method declaredMethod = cls.getDeclaredMethod("requestRelaunchActivity", new Class[]{IBinder.class, List.class, List.class, Integer.TYPE, Boolean.TYPE, Configuration.class, Configuration.class, Boolean.TYPE, Boolean.TYPE});
                declaredMethod.setAccessible(true);
                return declaredMethod;
            } catch (Throwable unused) {
            }
        }
        return null;
    }

    private static boolean d() {
        int i2 = Build.VERSION.SDK_INT;
        return i2 == 26 || i2 == 27;
    }

    private static Field b() {
        try {
            Field declaredField = Activity.class.getDeclaredField("mMainThread");
            declaredField.setAccessible(true);
            return declaredField;
        } catch (Throwable unused) {
            return null;
        }
    }

    private static Field c() {
        try {
            Field declaredField = Activity.class.getDeclaredField("mToken");
            declaredField.setAccessible(true);
            return declaredField;
        } catch (Throwable unused) {
            return null;
        }
    }

    protected static boolean a(Object obj, Activity activity) {
        try {
            Object obj2 = c.get(activity);
            if (obj2 != obj) {
                return false;
            }
            f449g.postAtFrontOfQueue(new C0014c(b.get(activity), obj2));
            return true;
        } catch (Throwable th) {
            Log.e("ActivityRecreator", "Exception while fetching field values", th);
            return false;
        }
    }

    private static Method a(Class<?> cls) {
        if (cls == null) {
            return null;
        }
        try {
            Method declaredMethod = cls.getDeclaredMethod("performStopActivity", new Class[]{IBinder.class, Boolean.TYPE});
            declaredMethod.setAccessible(true);
            return declaredMethod;
        } catch (Throwable unused) {
            return null;
        }
    }

    private static Class<?> a() {
        try {
            return Class.forName("android.app.ActivityThread");
        } catch (Throwable unused) {
            return null;
        }
    }
}
