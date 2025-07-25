package androidx.core.app;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;

/* compiled from: ActivityCompat */
public class a extends androidx.core.content.a {
    private static c c;

    /* renamed from: androidx.core.app.a$a  reason: collision with other inner class name */
    /* compiled from: ActivityCompat */
    static class C0013a implements Runnable {
        final /* synthetic */ String[] e;

        /* renamed from: f  reason: collision with root package name */
        final /* synthetic */ Activity f446f;

        /* renamed from: g  reason: collision with root package name */
        final /* synthetic */ int f447g;

        C0013a(String[] strArr, Activity activity, int i2) {
            this.e = strArr;
            this.f446f = activity;
            this.f447g = i2;
        }

        public void run() {
            int[] iArr = new int[this.e.length];
            PackageManager packageManager = this.f446f.getPackageManager();
            String packageName = this.f446f.getPackageName();
            int length = this.e.length;
            for (int i2 = 0; i2 < length; i2++) {
                iArr[i2] = packageManager.checkPermission(this.e[i2], packageName);
            }
            ((b) this.f446f).onRequestPermissionsResult(this.f447g, this.e, iArr);
        }
    }

    /* compiled from: ActivityCompat */
    public interface b {
        void onRequestPermissionsResult(int i2, String[] strArr, int[] iArr);
    }

    /* compiled from: ActivityCompat */
    public interface c {
        boolean a(Activity activity, int i2, int i3, Intent intent);

        boolean a(Activity activity, String[] strArr, int i2);
    }

    /* compiled from: ActivityCompat */
    public interface d {
        void a(int i2);
    }

    public static c a() {
        return c;
    }

    public static void b(Activity activity) {
        if (Build.VERSION.SDK_INT >= 28) {
            activity.recreate();
        } else if (!c.a(activity)) {
            activity.recreate();
        }
    }

    public static void a(Activity activity, Intent intent, int i2, Bundle bundle) {
        if (Build.VERSION.SDK_INT >= 16) {
            activity.startActivityForResult(intent, i2, bundle);
        } else {
            activity.startActivityForResult(intent, i2);
        }
    }

    public static void a(Activity activity) {
        if (Build.VERSION.SDK_INT >= 16) {
            activity.finishAffinity();
        } else {
            activity.finish();
        }
    }

    public static <T extends View> T a(Activity activity, int i2) {
        if (Build.VERSION.SDK_INT >= 28) {
            return activity.requireViewById(i2);
        }
        T findViewById = activity.findViewById(i2);
        if (findViewById != null) {
            return findViewById;
        }
        throw new IllegalArgumentException("ID does not reference a View inside this Activity");
    }

    public static void a(Activity activity, String[] strArr, int i2) {
        c cVar = c;
        if (cVar != null && cVar.a(activity, strArr, i2)) {
            return;
        }
        if (Build.VERSION.SDK_INT >= 23) {
            if (activity instanceof d) {
                ((d) activity).a(i2);
            }
            activity.requestPermissions(strArr, i2);
        } else if (activity instanceof b) {
            new Handler(Looper.getMainLooper()).post(new C0013a(strArr, activity, i2));
        }
    }

    public static boolean a(Activity activity, String str) {
        if (Build.VERSION.SDK_INT >= 23) {
            return activity.shouldShowRequestPermissionRationale(str);
        }
        return false;
    }
}
