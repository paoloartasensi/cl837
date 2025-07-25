package androidx.appcompat.d;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

/* compiled from: ActionMode */
public abstract class b {
    private Object e;

    /* renamed from: f  reason: collision with root package name */
    private boolean f98f;

    /* compiled from: ActionMode */
    public interface a {
        void a(b bVar);

        boolean a(b bVar, Menu menu);

        boolean a(b bVar, MenuItem menuItem);

        boolean b(b bVar, Menu menu);
    }

    public abstract void a();

    public abstract void a(int i2);

    public abstract void a(View view);

    public abstract void a(CharSequence charSequence);

    public void a(Object obj) {
        this.e = obj;
    }

    public abstract View b();

    public abstract void b(int i2);

    public abstract void b(CharSequence charSequence);

    public abstract Menu c();

    public abstract MenuInflater d();

    public abstract CharSequence e();

    public Object f() {
        return this.e;
    }

    public abstract CharSequence g();

    public boolean h() {
        return this.f98f;
    }

    public abstract void i();

    public abstract boolean j();

    public void a(boolean z) {
        this.f98f = z;
    }
}
