package androidx.appcompat.view.menu;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.view.ActionProvider;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import androidx.core.b.a.b;

/* compiled from: ActionMenuItem */
public class a implements b {
    private final int a;
    private final int b;
    private final int c;
    private CharSequence d;
    private CharSequence e;

    /* renamed from: f  reason: collision with root package name */
    private Intent f121f;

    /* renamed from: g  reason: collision with root package name */
    private char f122g;

    /* renamed from: h  reason: collision with root package name */
    private int f123h = 4096;

    /* renamed from: i  reason: collision with root package name */
    private char f124i;

    /* renamed from: j  reason: collision with root package name */
    private int f125j = 4096;
    private Drawable k;
    private Context l;
    private CharSequence m;
    private CharSequence n;
    private ColorStateList o = null;
    private PorterDuff.Mode p = null;
    private boolean q = false;
    private boolean r = false;
    private int s = 16;

    public a(Context context, int i2, int i3, int i4, int i5, CharSequence charSequence) {
        this.l = context;
        this.a = i3;
        this.b = i2;
        this.c = i5;
        this.d = charSequence;
    }

    private void b() {
        if (this.k == null) {
            return;
        }
        if (this.q || this.r) {
            Drawable i2 = androidx.core.graphics.drawable.a.i(this.k);
            this.k = i2;
            Drawable mutate = i2.mutate();
            this.k = mutate;
            if (this.q) {
                androidx.core.graphics.drawable.a.a(mutate, this.o);
            }
            if (this.r) {
                androidx.core.graphics.drawable.a.a(this.k, this.p);
            }
        }
    }

    public b a(androidx.core.h.b bVar) {
        throw new UnsupportedOperationException();
    }

    public androidx.core.h.b a() {
        return null;
    }

    public boolean collapseActionView() {
        return false;
    }

    public boolean expandActionView() {
        return false;
    }

    public ActionProvider getActionProvider() {
        throw new UnsupportedOperationException();
    }

    public View getActionView() {
        return null;
    }

    public int getAlphabeticModifiers() {
        return this.f125j;
    }

    public char getAlphabeticShortcut() {
        return this.f124i;
    }

    public CharSequence getContentDescription() {
        return this.m;
    }

    public int getGroupId() {
        return this.b;
    }

    public Drawable getIcon() {
        return this.k;
    }

    public ColorStateList getIconTintList() {
        return this.o;
    }

    public PorterDuff.Mode getIconTintMode() {
        return this.p;
    }

    public Intent getIntent() {
        return this.f121f;
    }

    public int getItemId() {
        return this.a;
    }

    public ContextMenu.ContextMenuInfo getMenuInfo() {
        return null;
    }

    public int getNumericModifiers() {
        return this.f123h;
    }

    public char getNumericShortcut() {
        return this.f122g;
    }

    public int getOrder() {
        return this.c;
    }

    public SubMenu getSubMenu() {
        return null;
    }

    public CharSequence getTitle() {
        return this.d;
    }

    public CharSequence getTitleCondensed() {
        CharSequence charSequence = this.e;
        return charSequence != null ? charSequence : this.d;
    }

    public CharSequence getTooltipText() {
        return this.n;
    }

    public boolean hasSubMenu() {
        return false;
    }

    public boolean isActionViewExpanded() {
        return false;
    }

    public boolean isCheckable() {
        return (this.s & 1) != 0;
    }

    public boolean isChecked() {
        return (this.s & 2) != 0;
    }

    public boolean isEnabled() {
        return (this.s & 16) != 0;
    }

    public boolean isVisible() {
        return (this.s & 8) == 0;
    }

    public MenuItem setActionProvider(ActionProvider actionProvider) {
        throw new UnsupportedOperationException();
    }

    public MenuItem setAlphabeticShortcut(char c2) {
        this.f124i = Character.toLowerCase(c2);
        return this;
    }

    public MenuItem setCheckable(boolean z) {
        this.s = z | (this.s & true) ? 1 : 0;
        return this;
    }

    public MenuItem setChecked(boolean z) {
        this.s = (z ? 2 : 0) | (this.s & -3);
        return this;
    }

    public MenuItem setEnabled(boolean z) {
        this.s = (z ? 16 : 0) | (this.s & -17);
        return this;
    }

    public MenuItem setIcon(Drawable drawable) {
        this.k = drawable;
        b();
        return this;
    }

    public MenuItem setIconTintList(ColorStateList colorStateList) {
        this.o = colorStateList;
        this.q = true;
        b();
        return this;
    }

    public MenuItem setIconTintMode(PorterDuff.Mode mode) {
        this.p = mode;
        this.r = true;
        b();
        return this;
    }

    public MenuItem setIntent(Intent intent) {
        this.f121f = intent;
        return this;
    }

    public MenuItem setNumericShortcut(char c2) {
        this.f122g = c2;
        return this;
    }

    public MenuItem setOnActionExpandListener(MenuItem.OnActionExpandListener onActionExpandListener) {
        throw new UnsupportedOperationException();
    }

    public MenuItem setOnMenuItemClickListener(MenuItem.OnMenuItemClickListener onMenuItemClickListener) {
        return this;
    }

    public MenuItem setShortcut(char c2, char c3) {
        this.f122g = c2;
        this.f124i = Character.toLowerCase(c3);
        return this;
    }

    public void setShowAsAction(int i2) {
    }

    public MenuItem setTitle(CharSequence charSequence) {
        this.d = charSequence;
        return this;
    }

    public MenuItem setTitleCondensed(CharSequence charSequence) {
        this.e = charSequence;
        return this;
    }

    public MenuItem setVisible(boolean z) {
        int i2 = 8;
        int i3 = this.s & 8;
        if (z) {
            i2 = 0;
        }
        this.s = i3 | i2;
        return this;
    }

    public MenuItem setAlphabeticShortcut(char c2, int i2) {
        this.f124i = Character.toLowerCase(c2);
        this.f125j = KeyEvent.normalizeMetaState(i2);
        return this;
    }

    public b setContentDescription(CharSequence charSequence) {
        this.m = charSequence;
        return this;
    }

    public MenuItem setNumericShortcut(char c2, int i2) {
        this.f122g = c2;
        this.f123h = KeyEvent.normalizeMetaState(i2);
        return this;
    }

    public b setShowAsActionFlags(int i2) {
        setShowAsAction(i2);
        return this;
    }

    public MenuItem setTitle(int i2) {
        this.d = this.l.getResources().getString(i2);
        return this;
    }

    public b setTooltipText(CharSequence charSequence) {
        this.n = charSequence;
        return this;
    }

    public b setActionView(View view) {
        throw new UnsupportedOperationException();
    }

    public MenuItem setIcon(int i2) {
        this.k = androidx.core.content.a.c(this.l, i2);
        b();
        return this;
    }

    public MenuItem setShortcut(char c2, char c3, int i2, int i3) {
        this.f122g = c2;
        this.f123h = KeyEvent.normalizeMetaState(i2);
        this.f124i = Character.toLowerCase(c3);
        this.f125j = KeyEvent.normalizeMetaState(i3);
        return this;
    }

    public b setActionView(int i2) {
        throw new UnsupportedOperationException();
    }
}
