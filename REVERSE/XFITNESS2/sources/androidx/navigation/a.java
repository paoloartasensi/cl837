package androidx.navigation;

import android.app.Activity;
import kotlin.jvm.internal.i;

/* compiled from: Activity.kt */
public final class a {
    public static final NavController a(Activity activity, int i2) {
        i.b(activity, "$this$findNavController");
        NavController a = q.a(activity, i2);
        i.a((Object) a, "Navigation.findNavController(this, viewId)");
        return a;
    }
}
