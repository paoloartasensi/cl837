package androidx.appcompat.widget;

import android.content.Context;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupWindow;
import androidx.appcompat.R$attr;
import androidx.appcompat.view.menu.g;
import androidx.appcompat.view.menu.l;

/* compiled from: PopupMenu */
public class w {
    private final Context a;
    private final g b;
    final l c;
    d d;
    c e;

    /* compiled from: PopupMenu */
    class a implements g.a {
        a() {
        }

        public void a(g gVar) {
        }

        public boolean a(g gVar, MenuItem menuItem) {
            d dVar = w.this.d;
            if (dVar != null) {
                return dVar.onMenuItemClick(menuItem);
            }
            return false;
        }
    }

    /* compiled from: PopupMenu */
    class b implements PopupWindow.OnDismissListener {
        b() {
        }

        public void onDismiss() {
            w wVar = w.this;
            c cVar = wVar.e;
            if (cVar != null) {
                cVar.a(wVar);
            }
        }
    }

    /* compiled from: PopupMenu */
    public interface c {
        void a(w wVar);
    }

    /* compiled from: PopupMenu */
    public interface d {
        boolean onMenuItemClick(MenuItem menuItem);
    }

    public w(Context context, View view) {
        this(context, view, 0);
    }

    public Menu a() {
        return this.b;
    }

    public MenuInflater b() {
        return new androidx.appcompat.d.g(this.a);
    }

    public void c() {
        this.c.e();
    }

    public void setOnDismissListener(c cVar) {
        this.e = cVar;
    }

    public void setOnMenuItemClickListener(d dVar) {
        this.d = dVar;
    }

    public w(Context context, View view, int i2) {
        this(context, view, i2, R$attr.popupMenuStyle, 0);
    }

    public w(Context context, View view, int i2, int i3, int i4) {
        this.a = context;
        g gVar = new g(context);
        this.b = gVar;
        gVar.a((g.a) new a());
        l lVar = new l(context, this.b, view, false, i3, i4);
        this.c = lVar;
        lVar.a(i2);
        this.c.setOnDismissListener(new b());
    }
}
