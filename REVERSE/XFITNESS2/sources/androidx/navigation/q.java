package androidx.navigation;

import android.app.Activity;
import android.view.View;
import android.view.ViewParent;
import androidx.core.app.a;
import java.lang.ref.WeakReference;

/* compiled from: Navigation */
public final class q {
    public static NavController a(Activity activity, int i2) {
        NavController b = b(a.a(activity, i2));
        if (b != null) {
            return b;
        }
        throw new IllegalStateException("Activity " + activity + " does not have a NavController set on " + i2);
    }

    private static NavController b(View view) {
        while (view != null) {
            NavController c = c(view);
            if (c != null) {
                return c;
            }
            ViewParent parent = view.getParent();
            view = parent instanceof View ? (View) parent : null;
        }
        return null;
    }

    private static NavController c(View view) {
        Object tag = view.getTag(R$id.nav_controller_view_tag);
        if (tag instanceof WeakReference) {
            return (NavController) ((WeakReference) tag).get();
        }
        if (tag instanceof NavController) {
            return (NavController) tag;
        }
        return null;
    }

    public static NavController a(View view) {
        NavController b = b(view);
        if (b != null) {
            return b;
        }
        throw new IllegalStateException("View " + view + " does not have a NavController set");
    }

    public static void a(View view, NavController navController) {
        view.setTag(R$id.nav_controller_view_tag, navController);
    }
}
