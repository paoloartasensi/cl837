package androidx.appcompat.view.menu;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.Log;
import android.view.ActionProvider;
import android.view.CollapsibleActionView;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.FrameLayout;
import androidx.core.h.b;
import java.lang.reflect.Method;

/* compiled from: MenuItemWrapperICS */
public class j extends c implements MenuItem {
    private final androidx.core.b.a.b d;
    private Method e;

    /* compiled from: MenuItemWrapperICS */
    private class a extends androidx.core.h.b {
        final ActionProvider c;

        a(Context context, ActionProvider actionProvider) {
            super(context);
            this.c = actionProvider;
        }

        public boolean a() {
            return this.c.hasSubMenu();
        }

        public View c() {
            return this.c.onCreateActionView();
        }

        public boolean d() {
            return this.c.onPerformDefaultAction();
        }

        public void a(SubMenu subMenu) {
            this.c.onPrepareSubMenu(j.this.a(subMenu));
        }
    }

    /* compiled from: MenuItemWrapperICS */
    private class b extends a implements ActionProvider.VisibilityListener {
        private b.C0024b e;

        b(j jVar, Context context, ActionProvider actionProvider) {
            super(context, actionProvider);
        }

        public View a(MenuItem menuItem) {
            return this.c.onCreateActionView(menuItem);
        }

        public boolean b() {
            return this.c.isVisible();
        }

        public boolean e() {
            return this.c.overridesItemVisibility();
        }

        public void onActionProviderVisibilityChanged(boolean z) {
            b.C0024b bVar = this.e;
            if (bVar != null) {
                bVar.onActionProviderVisibilityChanged(z);
            }
        }

        public void a(b.C0024b bVar) {
            this.e = bVar;
            this.c.setVisibilityListener(bVar != null ? this : null);
        }
    }

    /* compiled from: MenuItemWrapperICS */
    static class c extends FrameLayout implements androidx.appcompat.d.c {
        final CollapsibleActionView e;

        c(View view) {
            super(view.getContext());
            this.e = (CollapsibleActionView) view;
            addView(view);
        }

        public void a() {
            this.e.onActionViewExpanded();
        }

        public void b() {
            this.e.onActionViewCollapsed();
        }

        /* access modifiers changed from: package-private */
        public View c() {
            return (View) this.e;
        }
    }

    /* compiled from: MenuItemWrapperICS */
    private class d implements MenuItem.OnActionExpandListener {
        private final MenuItem.OnActionExpandListener a;

        d(MenuItem.OnActionExpandListener onActionExpandListener) {
            this.a = onActionExpandListener;
        }

        public boolean onMenuItemActionCollapse(MenuItem menuItem) {
            return this.a.onMenuItemActionCollapse(j.this.a(menuItem));
        }

        public boolean onMenuItemActionExpand(MenuItem menuItem) {
            return this.a.onMenuItemActionExpand(j.this.a(menuItem));
        }
    }

    /* compiled from: MenuItemWrapperICS */
    private class e implements MenuItem.OnMenuItemClickListener {
        private final MenuItem.OnMenuItemClickListener e;

        e(MenuItem.OnMenuItemClickListener onMenuItemClickListener) {
            this.e = onMenuItemClickListener;
        }

        public boolean onMenuItemClick(MenuItem menuItem) {
            return this.e.onMenuItemClick(j.this.a(menuItem));
        }
    }

    public j(Context context, androidx.core.b.a.b bVar) {
        super(context);
        if (bVar != null) {
            this.d = bVar;
            return;
        }
        throw new IllegalArgumentException("Wrapped Object can not be null.");
    }

    public void a(boolean z) {
        try {
            if (this.e == null) {
                this.e = this.d.getClass().getDeclaredMethod("setExclusiveCheckable", new Class[]{Boolean.TYPE});
            }
            this.e.invoke(this.d, new Object[]{Boolean.valueOf(z)});
        } catch (Exception e2) {
            Log.w("MenuItemWrapper", "Error while calling setExclusiveCheckable", e2);
        }
    }

    public boolean collapseActionView() {
        return this.d.collapseActionView();
    }

    public boolean expandActionView() {
        return this.d.expandActionView();
    }

    public ActionProvider getActionProvider() {
        androidx.core.h.b a2 = this.d.a();
        if (a2 instanceof a) {
            return ((a) a2).c;
        }
        return null;
    }

    public View getActionView() {
        View actionView = this.d.getActionView();
        return actionView instanceof c ? ((c) actionView).c() : actionView;
    }

    public int getAlphabeticModifiers() {
        return this.d.getAlphabeticModifiers();
    }

    public char getAlphabeticShortcut() {
        return this.d.getAlphabeticShortcut();
    }

    public CharSequence getContentDescription() {
        return this.d.getContentDescription();
    }

    public int getGroupId() {
        return this.d.getGroupId();
    }

    public Drawable getIcon() {
        return this.d.getIcon();
    }

    public ColorStateList getIconTintList() {
        return this.d.getIconTintList();
    }

    public PorterDuff.Mode getIconTintMode() {
        return this.d.getIconTintMode();
    }

    public Intent getIntent() {
        return this.d.getIntent();
    }

    public int getItemId() {
        return this.d.getItemId();
    }

    public ContextMenu.ContextMenuInfo getMenuInfo() {
        return this.d.getMenuInfo();
    }

    public int getNumericModifiers() {
        return this.d.getNumericModifiers();
    }

    public char getNumericShortcut() {
        return this.d.getNumericShortcut();
    }

    public int getOrder() {
        return this.d.getOrder();
    }

    public SubMenu getSubMenu() {
        return a(this.d.getSubMenu());
    }

    public CharSequence getTitle() {
        return this.d.getTitle();
    }

    public CharSequence getTitleCondensed() {
        return this.d.getTitleCondensed();
    }

    public CharSequence getTooltipText() {
        return this.d.getTooltipText();
    }

    public boolean hasSubMenu() {
        return this.d.hasSubMenu();
    }

    public boolean isActionViewExpanded() {
        return this.d.isActionViewExpanded();
    }

    public boolean isCheckable() {
        return this.d.isCheckable();
    }

    public boolean isChecked() {
        return this.d.isChecked();
    }

    public boolean isEnabled() {
        return this.d.isEnabled();
    }

    public boolean isVisible() {
        return this.d.isVisible();
    }

    public MenuItem setActionProvider(ActionProvider actionProvider) {
        androidx.core.h.b bVar;
        if (Build.VERSION.SDK_INT >= 16) {
            bVar = new b(this, this.a, actionProvider);
        } else {
            bVar = new a(this.a, actionProvider);
        }
        androidx.core.b.a.b bVar2 = this.d;
        if (actionProvider == null) {
            bVar = null;
        }
        bVar2.a(bVar);
        return this;
    }

    public MenuItem setActionView(View view) {
        if (view instanceof CollapsibleActionView) {
            view = new c(view);
        }
        this.d.setActionView(view);
        return this;
    }

    public MenuItem setAlphabeticShortcut(char c2) {
        this.d.setAlphabeticShortcut(c2);
        return this;
    }

    public MenuItem setCheckable(boolean z) {
        this.d.setCheckable(z);
        return this;
    }

    public MenuItem setChecked(boolean z) {
        this.d.setChecked(z);
        return this;
    }

    public MenuItem setContentDescription(CharSequence charSequence) {
        this.d.setContentDescription(charSequence);
        return this;
    }

    public MenuItem setEnabled(boolean z) {
        this.d.setEnabled(z);
        return this;
    }

    public MenuItem setIcon(Drawable drawable) {
        this.d.setIcon(drawable);
        return this;
    }

    public MenuItem setIconTintList(ColorStateList colorStateList) {
        this.d.setIconTintList(colorStateList);
        return this;
    }

    public MenuItem setIconTintMode(PorterDuff.Mode mode) {
        this.d.setIconTintMode(mode);
        return this;
    }

    public MenuItem setIntent(Intent intent) {
        this.d.setIntent(intent);
        return this;
    }

    public MenuItem setNumericShortcut(char c2) {
        this.d.setNumericShortcut(c2);
        return this;
    }

    public MenuItem setOnActionExpandListener(MenuItem.OnActionExpandListener onActionExpandListener) {
        this.d.setOnActionExpandListener(onActionExpandListener != null ? new d(onActionExpandListener) : null);
        return this;
    }

    public MenuItem setOnMenuItemClickListener(MenuItem.OnMenuItemClickListener onMenuItemClickListener) {
        this.d.setOnMenuItemClickListener(onMenuItemClickListener != null ? new e(onMenuItemClickListener) : null);
        return this;
    }

    public MenuItem setShortcut(char c2, char c3) {
        this.d.setShortcut(c2, c3);
        return this;
    }

    public void setShowAsAction(int i2) {
        this.d.setShowAsAction(i2);
    }

    public MenuItem setShowAsActionFlags(int i2) {
        this.d.setShowAsActionFlags(i2);
        return this;
    }

    public MenuItem setTitle(CharSequence charSequence) {
        this.d.setTitle(charSequence);
        return this;
    }

    public MenuItem setTitleCondensed(CharSequence charSequence) {
        this.d.setTitleCondensed(charSequence);
        return this;
    }

    public MenuItem setTooltipText(CharSequence charSequence) {
        this.d.setTooltipText(charSequence);
        return this;
    }

    public MenuItem setVisible(boolean z) {
        return this.d.setVisible(z);
    }

    public MenuItem setAlphabeticShortcut(char c2, int i2) {
        this.d.setAlphabeticShortcut(c2, i2);
        return this;
    }

    public MenuItem setIcon(int i2) {
        this.d.setIcon(i2);
        return this;
    }

    public MenuItem setNumericShortcut(char c2, int i2) {
        this.d.setNumericShortcut(c2, i2);
        return this;
    }

    public MenuItem setShortcut(char c2, char c3, int i2, int i3) {
        this.d.setShortcut(c2, c3, i2, i3);
        return this;
    }

    public MenuItem setTitle(int i2) {
        this.d.setTitle(i2);
        return this;
    }

    public MenuItem setActionView(int i2) {
        this.d.setActionView(i2);
        View actionView = this.d.getActionView();
        if (actionView instanceof CollapsibleActionView) {
            this.d.setActionView((View) new c(actionView));
        }
        return this;
    }
}
