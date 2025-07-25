package androidx.appcompat.d;

import android.content.Context;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import androidx.appcompat.d.b;
import androidx.appcompat.view.menu.j;
import androidx.appcompat.view.menu.o;
import g.a.g;
import java.util.ArrayList;

/* compiled from: SupportActionModeWrapper */
public class f extends ActionMode {
    final Context a;
    final b b;

    public f(Context context, b bVar) {
        this.a = context;
        this.b = bVar;
    }

    public void finish() {
        this.b.a();
    }

    public View getCustomView() {
        return this.b.b();
    }

    public Menu getMenu() {
        return new o(this.a, (androidx.core.b.a.a) this.b.c());
    }

    public MenuInflater getMenuInflater() {
        return this.b.d();
    }

    public CharSequence getSubtitle() {
        return this.b.e();
    }

    public Object getTag() {
        return this.b.f();
    }

    public CharSequence getTitle() {
        return this.b.g();
    }

    public boolean getTitleOptionalHint() {
        return this.b.h();
    }

    public void invalidate() {
        this.b.i();
    }

    public boolean isTitleOptional() {
        return this.b.j();
    }

    public void setCustomView(View view) {
        this.b.a(view);
    }

    public void setSubtitle(CharSequence charSequence) {
        this.b.a(charSequence);
    }

    public void setTag(Object obj) {
        this.b.a(obj);
    }

    public void setTitle(CharSequence charSequence) {
        this.b.b(charSequence);
    }

    public void setTitleOptionalHint(boolean z) {
        this.b.a(z);
    }

    public void setSubtitle(int i2) {
        this.b.a(i2);
    }

    public void setTitle(int i2) {
        this.b.b(i2);
    }

    /* compiled from: SupportActionModeWrapper */
    public static class a implements b.a {
        final ActionMode.Callback a;
        final Context b;
        final ArrayList<f> c = new ArrayList<>();
        final g<Menu, Menu> d = new g<>();

        public a(Context context, ActionMode.Callback callback) {
            this.b = context;
            this.a = callback;
        }

        public boolean a(b bVar, Menu menu) {
            return this.a.onPrepareActionMode(b(bVar), a(menu));
        }

        public boolean b(b bVar, Menu menu) {
            return this.a.onCreateActionMode(b(bVar), a(menu));
        }

        public boolean a(b bVar, MenuItem menuItem) {
            return this.a.onActionItemClicked(b(bVar), new j(this.b, (androidx.core.b.a.b) menuItem));
        }

        public ActionMode b(b bVar) {
            int size = this.c.size();
            for (int i2 = 0; i2 < size; i2++) {
                f fVar = this.c.get(i2);
                if (fVar != null && fVar.b == bVar) {
                    return fVar;
                }
            }
            f fVar2 = new f(this.b, bVar);
            this.c.add(fVar2);
            return fVar2;
        }

        public void a(b bVar) {
            this.a.onDestroyActionMode(b(bVar));
        }

        private Menu a(Menu menu) {
            Menu menu2 = this.d.get(menu);
            if (menu2 != null) {
                return menu2;
            }
            o oVar = new o(this.b, (androidx.core.b.a.a) menu);
            this.d.put(menu, oVar);
            return oVar;
        }
    }
}
