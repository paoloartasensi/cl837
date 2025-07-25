package androidx.appcompat.app;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.widget.l0;
import g.a.b;
import java.lang.ref.WeakReference;
import java.util.Iterator;

/* compiled from: AppCompatDelegate */
public abstract class e {
    private static int e = -100;

    /* renamed from: f  reason: collision with root package name */
    private static final b<WeakReference<e>> f62f = new b<>();

    /* renamed from: g  reason: collision with root package name */
    private static final Object f63g = new Object();

    e() {
    }

    public static e a(Activity activity, d dVar) {
        return new AppCompatDelegateImpl(activity, dVar);
    }

    static void b(e eVar) {
        synchronized (f63g) {
            c(eVar);
        }
    }

    private static void c(e eVar) {
        synchronized (f63g) {
            Iterator<WeakReference<e>> it = f62f.iterator();
            while (it.hasNext()) {
                e eVar2 = (e) it.next().get();
                if (eVar2 == eVar || eVar2 == null) {
                    it.remove();
                }
            }
        }
    }

    public static int k() {
        return e;
    }

    public abstract <T extends View> T a(int i2);

    public abstract b a();

    public void a(Context context) {
    }

    public abstract void a(Configuration configuration);

    public abstract void a(Bundle bundle);

    public abstract void a(View view);

    public abstract void a(View view, ViewGroup.LayoutParams layoutParams);

    public abstract void a(Toolbar toolbar);

    public abstract void a(CharSequence charSequence);

    public int b() {
        return -100;
    }

    public abstract void b(Bundle bundle);

    public abstract void b(View view, ViewGroup.LayoutParams layoutParams);

    public abstract boolean b(int i2);

    public abstract MenuInflater c();

    public abstract void c(int i2);

    public abstract void c(Bundle bundle);

    public abstract a d();

    public void d(int i2) {
    }

    public abstract void e();

    public abstract void f();

    public abstract void g();

    public abstract void h();

    public abstract void i();

    public abstract void j();

    public static e a(Dialog dialog, d dVar) {
        return new AppCompatDelegateImpl(dialog, dVar);
    }

    public static void a(boolean z) {
        l0.a(z);
    }

    static void a(e eVar) {
        synchronized (f63g) {
            c(eVar);
            f62f.add(new WeakReference(eVar));
        }
    }
}
