package androidx.appcompat.app;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import androidx.appcompat.app.a;
import androidx.appcompat.d.i;
import androidx.appcompat.view.menu.g;
import androidx.appcompat.view.menu.m;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.widget.h0;
import androidx.appcompat.widget.p;
import androidx.core.h.v;
import java.util.ArrayList;

/* compiled from: ToolbarActionBar */
class h extends a {
    p a;
    boolean b;
    Window.Callback c;
    private boolean d;
    private boolean e;

    /* renamed from: f  reason: collision with root package name */
    private ArrayList<a.b> f68f = new ArrayList<>();

    /* renamed from: g  reason: collision with root package name */
    private final Runnable f69g = new a();

    /* renamed from: h  reason: collision with root package name */
    private final Toolbar.f f70h = new b();

    /* compiled from: ToolbarActionBar */
    class a implements Runnable {
        a() {
        }

        public void run() {
            h.this.m();
        }
    }

    /* compiled from: ToolbarActionBar */
    class b implements Toolbar.f {
        b() {
        }

        public boolean onMenuItemClick(MenuItem menuItem) {
            return h.this.c.onMenuItemSelected(0, menuItem);
        }
    }

    /* compiled from: ToolbarActionBar */
    private final class d implements g.a {
        d() {
        }

        public void a(g gVar) {
            h hVar = h.this;
            if (hVar.c == null) {
                return;
            }
            if (hVar.a.b()) {
                h.this.c.onPanelClosed(108, gVar);
            } else if (h.this.c.onPreparePanel(0, (View) null, gVar)) {
                h.this.c.onMenuOpened(108, gVar);
            }
        }

        public boolean a(g gVar, MenuItem menuItem) {
            return false;
        }
    }

    /* compiled from: ToolbarActionBar */
    private class e extends i {
        public e(Window.Callback callback) {
            super(callback);
        }

        public View onCreatePanelView(int i2) {
            if (i2 == 0) {
                return new View(h.this.a.getContext());
            }
            return super.onCreatePanelView(i2);
        }

        public boolean onPreparePanel(int i2, View view, Menu menu) {
            boolean onPreparePanel = super.onPreparePanel(i2, view, menu);
            if (onPreparePanel) {
                h hVar = h.this;
                if (!hVar.b) {
                    hVar.a.e();
                    h.this.b = true;
                }
            }
            return onPreparePanel;
        }
    }

    h(Toolbar toolbar, CharSequence charSequence, Window.Callback callback) {
        this.a = new h0(toolbar, false);
        e eVar = new e(callback);
        this.c = eVar;
        this.a.setWindowCallback(eVar);
        toolbar.setOnMenuItemClickListener(this.f70h);
        this.a.setWindowTitle(charSequence);
    }

    private Menu n() {
        if (!this.d) {
            this.a.a((m.a) new c(), (g.a) new d());
            this.d = true;
        }
        return this.a.i();
    }

    public void a(Drawable drawable) {
        this.a.a(drawable);
    }

    public void addOnMenuVisibilityListener(a.b bVar) {
        this.f68f.add(bVar);
    }

    public void b(int i2) {
        this.a.c(i2);
    }

    public void c(boolean z) {
    }

    public void d(boolean z) {
        a(z ? 4 : 0, 4);
    }

    public void e(boolean z) {
    }

    public boolean e() {
        return this.a.c();
    }

    public boolean f() {
        if (!this.a.m()) {
            return false;
        }
        this.a.collapseActionView();
        return true;
    }

    public int g() {
        return this.a.h();
    }

    public Context h() {
        return this.a.getContext();
    }

    public boolean i() {
        this.a.j().removeCallbacks(this.f69g);
        v.a((View) this.a.j(), this.f69g);
        return true;
    }

    /* access modifiers changed from: package-private */
    public void j() {
        this.a.j().removeCallbacks(this.f69g);
    }

    public boolean k() {
        return this.a.d();
    }

    public Window.Callback l() {
        return this.c;
    }

    /* access modifiers changed from: package-private */
    public void m() {
        Menu n = n();
        g gVar = n instanceof g ? (g) n : null;
        if (gVar != null) {
            gVar.s();
        }
        try {
            n.clear();
            if (!this.c.onCreatePanelMenu(0, n) || !this.c.onPreparePanel(0, (View) null, n)) {
                n.clear();
            }
        } finally {
            if (gVar != null) {
                gVar.r();
            }
        }
    }

    public void removeOnMenuVisibilityListener(a.b bVar) {
        this.f68f.remove(bVar);
    }

    /* compiled from: ToolbarActionBar */
    private final class c implements m.a {
        private boolean e;

        c() {
        }

        public boolean a(g gVar) {
            Window.Callback callback = h.this.c;
            if (callback == null) {
                return false;
            }
            callback.onMenuOpened(108, gVar);
            return true;
        }

        public void a(g gVar, boolean z) {
            if (!this.e) {
                this.e = true;
                h.this.a.g();
                Window.Callback callback = h.this.c;
                if (callback != null) {
                    callback.onPanelClosed(108, gVar);
                }
                this.e = false;
            }
        }
    }

    public void a(Configuration configuration) {
        super.a(configuration);
    }

    public void b(CharSequence charSequence) {
        this.a.setWindowTitle(charSequence);
    }

    public void a(CharSequence charSequence) {
        this.a.setTitle(charSequence);
    }

    public void b(boolean z) {
        if (z != this.e) {
            this.e = z;
            int size = this.f68f.size();
            for (int i2 = 0; i2 < size; i2++) {
                this.f68f.get(i2).a(z);
            }
        }
    }

    public void a(int i2, int i3) {
        this.a.d((i2 & i3) | ((i3 ^ -1) & this.a.h()));
    }

    public boolean a(KeyEvent keyEvent) {
        if (keyEvent.getAction() == 1) {
            k();
        }
        return true;
    }

    public boolean a(int i2, KeyEvent keyEvent) {
        Menu n = n();
        if (n == null) {
            return false;
        }
        boolean z = true;
        if (KeyCharacterMap.load(keyEvent != null ? keyEvent.getDeviceId() : -1).getKeyboardType() == 1) {
            z = false;
        }
        n.setQwertyMode(z);
        return n.performShortcut(i2, keyEvent, 0);
    }
}
