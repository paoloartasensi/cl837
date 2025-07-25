package androidx.core.h;

import android.content.Context;
import android.util.Log;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;

/* compiled from: ActionProvider */
public abstract class b {
    private a a;
    private C0024b b;

    /* compiled from: ActionProvider */
    public interface a {
        void b(boolean z);
    }

    /* renamed from: androidx.core.h.b$b  reason: collision with other inner class name */
    /* compiled from: ActionProvider */
    public interface C0024b {
        void onActionProviderVisibilityChanged(boolean z);
    }

    public b(Context context) {
    }

    public View a(MenuItem menuItem) {
        return c();
    }

    public void a(SubMenu subMenu) {
    }

    public boolean a() {
        return false;
    }

    public boolean b() {
        return true;
    }

    public abstract View c();

    public boolean d() {
        return false;
    }

    public boolean e() {
        return false;
    }

    public void f() {
        this.b = null;
        this.a = null;
    }

    public void a(boolean z) {
        a aVar = this.a;
        if (aVar != null) {
            aVar.b(z);
        }
    }

    public void a(a aVar) {
        this.a = aVar;
    }

    public void a(C0024b bVar) {
        if (!(this.b == null || bVar == null)) {
            Log.w("ActionProvider(support)", "setVisibilityListener: Setting a new ActionProvider.VisibilityListener when one is already set. Are you reusing this " + getClass().getSimpleName() + " instance while it is still in use somewhere else?");
        }
        this.b = bVar;
    }
}
