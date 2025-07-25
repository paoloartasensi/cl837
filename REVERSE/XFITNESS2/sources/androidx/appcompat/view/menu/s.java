package androidx.appcompat.view.menu;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import androidx.core.b.a.c;

/* compiled from: SubMenuWrapperICS */
class s extends o implements SubMenu {
    private final c e;

    s(Context context, c cVar) {
        super(context, cVar);
        this.e = cVar;
    }

    public void clearHeader() {
        this.e.clearHeader();
    }

    public MenuItem getItem() {
        return a(this.e.getItem());
    }

    public SubMenu setHeaderIcon(int i2) {
        this.e.setHeaderIcon(i2);
        return this;
    }

    public SubMenu setHeaderTitle(int i2) {
        this.e.setHeaderTitle(i2);
        return this;
    }

    public SubMenu setHeaderView(View view) {
        this.e.setHeaderView(view);
        return this;
    }

    public SubMenu setIcon(int i2) {
        this.e.setIcon(i2);
        return this;
    }

    public SubMenu setHeaderIcon(Drawable drawable) {
        this.e.setHeaderIcon(drawable);
        return this;
    }

    public SubMenu setHeaderTitle(CharSequence charSequence) {
        this.e.setHeaderTitle(charSequence);
        return this;
    }

    public SubMenu setIcon(Drawable drawable) {
        this.e.setIcon(drawable);
        return this;
    }
}
