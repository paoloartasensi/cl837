package androidx.core.b.a;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.view.MenuItem;
import android.view.View;

/* compiled from: SupportMenuItem */
public interface b extends MenuItem {
    b a(androidx.core.h.b bVar);

    androidx.core.h.b a();

    boolean collapseActionView();

    boolean expandActionView();

    View getActionView();

    int getAlphabeticModifiers();

    CharSequence getContentDescription();

    ColorStateList getIconTintList();

    PorterDuff.Mode getIconTintMode();

    int getNumericModifiers();

    CharSequence getTooltipText();

    boolean isActionViewExpanded();

    MenuItem setActionView(int i2);

    MenuItem setActionView(View view);

    MenuItem setAlphabeticShortcut(char c, int i2);

    b setContentDescription(CharSequence charSequence);

    MenuItem setIconTintList(ColorStateList colorStateList);

    MenuItem setIconTintMode(PorterDuff.Mode mode);

    MenuItem setNumericShortcut(char c, int i2);

    MenuItem setShortcut(char c, char c2, int i2, int i3);

    void setShowAsAction(int i2);

    MenuItem setShowAsActionFlags(int i2);

    b setTooltipText(CharSequence charSequence);
}
