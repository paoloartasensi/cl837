package androidx.appcompat.view.menu;

import android.content.Context;
import android.view.MenuItem;
import android.view.SubMenu;
import androidx.core.b.a.b;
import g.a.a;
import java.util.Iterator;
import java.util.Map;

/* compiled from: BaseMenuWrapper */
abstract class c {
    final Context a;
    private Map<b, MenuItem> b;
    private Map<androidx.core.b.a.c, SubMenu> c;

    c(Context context) {
        this.a = context;
    }

    /* access modifiers changed from: package-private */
    public final MenuItem a(MenuItem menuItem) {
        if (!(menuItem instanceof b)) {
            return menuItem;
        }
        b bVar = (b) menuItem;
        if (this.b == null) {
            this.b = new a();
        }
        MenuItem menuItem2 = this.b.get(menuItem);
        if (menuItem2 != null) {
            return menuItem2;
        }
        j jVar = new j(this.a, bVar);
        this.b.put(bVar, jVar);
        return jVar;
    }

    /* access modifiers changed from: package-private */
    public final void b() {
        Map<b, MenuItem> map = this.b;
        if (map != null) {
            map.clear();
        }
        Map<androidx.core.b.a.c, SubMenu> map2 = this.c;
        if (map2 != null) {
            map2.clear();
        }
    }

    /* access modifiers changed from: package-private */
    public final void b(int i2) {
        Map<b, MenuItem> map = this.b;
        if (map != null) {
            Iterator<b> it = map.keySet().iterator();
            while (it.hasNext()) {
                if (i2 == it.next().getItemId()) {
                    it.remove();
                    return;
                }
            }
        }
    }

    /* access modifiers changed from: package-private */
    public final SubMenu a(SubMenu subMenu) {
        if (!(subMenu instanceof androidx.core.b.a.c)) {
            return subMenu;
        }
        androidx.core.b.a.c cVar = (androidx.core.b.a.c) subMenu;
        if (this.c == null) {
            this.c = new a();
        }
        SubMenu subMenu2 = this.c.get(cVar);
        if (subMenu2 != null) {
            return subMenu2;
        }
        s sVar = new s(this.a, cVar);
        this.c.put(cVar, sVar);
        return sVar;
    }

    /* access modifiers changed from: package-private */
    public final void a(int i2) {
        Map<b, MenuItem> map = this.b;
        if (map != null) {
            Iterator<b> it = map.keySet().iterator();
            while (it.hasNext()) {
                if (i2 == it.next().getGroupId()) {
                    it.remove();
                }
            }
        }
    }
}
