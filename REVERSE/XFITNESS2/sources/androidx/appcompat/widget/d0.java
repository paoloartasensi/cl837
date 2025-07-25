package androidx.appcompat.widget;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.Build;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

/* compiled from: TintContextWrapper */
public class d0 extends ContextWrapper {
    private static final Object c = new Object();
    private static ArrayList<WeakReference<d0>> d;
    private final Resources a;
    private final Resources.Theme b;

    private d0(Context context) {
        super(context);
        if (l0.b()) {
            l0 l0Var = new l0(this, context.getResources());
            this.a = l0Var;
            Resources.Theme newTheme = l0Var.newTheme();
            this.b = newTheme;
            newTheme.setTo(context.getTheme());
            return;
        }
        this.a = new f0(this, context.getResources());
        this.b = null;
    }

    private static boolean a(Context context) {
        if ((context instanceof d0) || (context.getResources() instanceof f0) || (context.getResources() instanceof l0)) {
            return false;
        }
        if (Build.VERSION.SDK_INT < 21 || l0.b()) {
            return true;
        }
        return false;
    }

    public static Context b(Context context) {
        if (!a(context)) {
            return context;
        }
        synchronized (c) {
            if (d == null) {
                d = new ArrayList<>();
            } else {
                for (int size = d.size() - 1; size >= 0; size--) {
                    WeakReference weakReference = d.get(size);
                    if (weakReference == null || weakReference.get() == null) {
                        d.remove(size);
                    }
                }
                for (int size2 = d.size() - 1; size2 >= 0; size2--) {
                    WeakReference weakReference2 = d.get(size2);
                    d0 d0Var = weakReference2 != null ? (d0) weakReference2.get() : null;
                    if (d0Var != null && d0Var.getBaseContext() == context) {
                        return d0Var;
                    }
                }
            }
            d0 d0Var2 = new d0(context);
            d.add(new WeakReference(d0Var2));
            return d0Var2;
        }
    }

    public AssetManager getAssets() {
        return this.a.getAssets();
    }

    public Resources getResources() {
        return this.a;
    }

    public Resources.Theme getTheme() {
        Resources.Theme theme = this.b;
        return theme == null ? super.getTheme() : theme;
    }

    public void setTheme(int i2) {
        Resources.Theme theme = this.b;
        if (theme == null) {
            super.setTheme(i2);
        } else {
            theme.applyStyle(i2, true);
        }
    }
}
