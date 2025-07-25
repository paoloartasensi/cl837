package androidx.appcompat.view.menu;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import androidx.appcompat.view.menu.g;

/* compiled from: SubMenuBuilder */
public class r extends g implements SubMenu {
    private g B;
    private i C;

    public r(Context context, g gVar, i iVar) {
        super(context);
        this.B = gVar;
        this.C = iVar;
    }

    public void a(g.a aVar) {
        this.B.a(aVar);
    }

    public boolean b(i iVar) {
        return this.B.b(iVar);
    }

    public String d() {
        i iVar = this.C;
        int itemId = iVar != null ? iVar.getItemId() : 0;
        if (itemId == 0) {
            return null;
        }
        return super.d() + ":" + itemId;
    }

    public MenuItem getItem() {
        return this.C;
    }

    public g m() {
        return this.B.m();
    }

    public boolean o() {
        return this.B.o();
    }

    public boolean p() {
        return this.B.p();
    }

    public boolean q() {
        return this.B.q();
    }

    public void setGroupDividerEnabled(boolean z) {
        this.B.setGroupDividerEnabled(z);
    }

    public SubMenu setHeaderIcon(Drawable drawable) {
        super.a(drawable);
        return this;
    }

    public SubMenu setHeaderTitle(CharSequence charSequence) {
        super.a(charSequence);
        return this;
    }

    public SubMenu setHeaderView(View view) {
        super.a(view);
        return this;
    }

    public SubMenu setIcon(Drawable drawable) {
        this.C.setIcon(drawable);
        return this;
    }

    public void setQwertyMode(boolean z) {
        this.B.setQwertyMode(z);
    }

    public Menu t() {
        return this.B;
    }

    /* access modifiers changed from: package-private */
    public boolean a(g gVar, MenuItem menuItem) {
        return super.a(gVar, menuItem) || this.B.a(gVar, menuItem);
    }

    public SubMenu setHeaderIcon(int i2) {
        super.d(i2);
        return this;
    }

    public SubMenu setHeaderTitle(int i2) {
        super.e(i2);
        return this;
    }

    public SubMenu setIcon(int i2) {
        this.C.setIcon(i2);
        return this;
    }

    public boolean a(i iVar) {
        return this.B.a(iVar);
    }
}
