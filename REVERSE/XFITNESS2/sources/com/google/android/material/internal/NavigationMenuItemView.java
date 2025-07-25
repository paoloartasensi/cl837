package com.google.android.material.internal;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.CheckedTextView;
import android.widget.FrameLayout;
import androidx.appcompat.R$attr;
import androidx.appcompat.view.menu.i;
import androidx.appcompat.view.menu.n;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.appcompat.widget.i0;
import androidx.core.content.c.f;
import androidx.core.h.e0.d;
import androidx.core.h.v;
import com.google.android.material.R$dimen;
import com.google.android.material.R$drawable;
import com.google.android.material.R$id;
import com.google.android.material.R$layout;

public class NavigationMenuItemView extends ForegroundLinearLayout implements n.a {
    private static final int[] J = {16842912};
    private boolean A;
    boolean B;
    private final CheckedTextView C;
    private FrameLayout D;
    private i E;
    private ColorStateList F;
    private boolean G;
    private Drawable H;
    private final androidx.core.h.a I;
    private final int z;

    class a extends androidx.core.h.a {
        a() {
        }

        public void a(View view, d dVar) {
            super.a(view, dVar);
            dVar.c(NavigationMenuItemView.this.B);
        }
    }

    public NavigationMenuItemView(Context context) {
        this(context, (AttributeSet) null);
    }

    private void e() {
        if (g()) {
            this.C.setVisibility(8);
            FrameLayout frameLayout = this.D;
            if (frameLayout != null) {
                LinearLayoutCompat.a aVar = (LinearLayoutCompat.a) frameLayout.getLayoutParams();
                aVar.width = -1;
                this.D.setLayoutParams(aVar);
                return;
            }
            return;
        }
        this.C.setVisibility(0);
        FrameLayout frameLayout2 = this.D;
        if (frameLayout2 != null) {
            LinearLayoutCompat.a aVar2 = (LinearLayoutCompat.a) frameLayout2.getLayoutParams();
            aVar2.width = -2;
            this.D.setLayoutParams(aVar2);
        }
    }

    private StateListDrawable f() {
        TypedValue typedValue = new TypedValue();
        if (!getContext().getTheme().resolveAttribute(R$attr.colorControlHighlight, typedValue, true)) {
            return null;
        }
        StateListDrawable stateListDrawable = new StateListDrawable();
        stateListDrawable.addState(J, new ColorDrawable(typedValue.data));
        stateListDrawable.addState(ViewGroup.EMPTY_STATE_SET, new ColorDrawable(0));
        return stateListDrawable;
    }

    private boolean g() {
        return this.E.getTitle() == null && this.E.getIcon() == null && this.E.getActionView() != null;
    }

    private void setActionView(View view) {
        if (view != null) {
            if (this.D == null) {
                this.D = (FrameLayout) ((ViewStub) findViewById(R$id.design_menu_item_action_area_stub)).inflate();
            }
            this.D.removeAllViews();
            this.D.addView(view);
        }
    }

    public void a(i iVar, int i2) {
        this.E = iVar;
        setVisibility(iVar.isVisible() ? 0 : 8);
        if (getBackground() == null) {
            v.a((View) this, (Drawable) f());
        }
        setCheckable(iVar.isCheckable());
        setChecked(iVar.isChecked());
        setEnabled(iVar.isEnabled());
        setTitle(iVar.getTitle());
        setIcon(iVar.getIcon());
        setActionView(iVar.getActionView());
        setContentDescription(iVar.getContentDescription());
        i0.a(this, iVar.getTooltipText());
        e();
    }

    public boolean c() {
        return false;
    }

    public void d() {
        FrameLayout frameLayout = this.D;
        if (frameLayout != null) {
            frameLayout.removeAllViews();
        }
        this.C.setCompoundDrawables((Drawable) null, (Drawable) null, (Drawable) null, (Drawable) null);
    }

    public i getItemData() {
        return this.E;
    }

    /* access modifiers changed from: protected */
    public int[] onCreateDrawableState(int i2) {
        int[] onCreateDrawableState = super.onCreateDrawableState(i2 + 1);
        i iVar = this.E;
        if (iVar != null && iVar.isCheckable() && this.E.isChecked()) {
            ViewGroup.mergeDrawableStates(onCreateDrawableState, J);
        }
        return onCreateDrawableState;
    }

    public void setCheckable(boolean z2) {
        refreshDrawableState();
        if (this.B != z2) {
            this.B = z2;
            this.I.a((View) this.C, 2048);
        }
    }

    public void setChecked(boolean z2) {
        refreshDrawableState();
        this.C.setChecked(z2);
    }

    public void setHorizontalPadding(int i2) {
        setPadding(i2, 0, i2, 0);
    }

    public void setIcon(Drawable drawable) {
        if (drawable != null) {
            if (this.G) {
                Drawable.ConstantState constantState = drawable.getConstantState();
                if (constantState != null) {
                    drawable = constantState.newDrawable();
                }
                drawable = androidx.core.graphics.drawable.a.i(drawable).mutate();
                androidx.core.graphics.drawable.a.a(drawable, this.F);
            }
            int i2 = this.z;
            drawable.setBounds(0, 0, i2, i2);
        } else if (this.A) {
            if (this.H == null) {
                Drawable a2 = f.a(getResources(), R$drawable.navigation_empty_icon, getContext().getTheme());
                this.H = a2;
                if (a2 != null) {
                    int i3 = this.z;
                    a2.setBounds(0, 0, i3, i3);
                }
            }
            drawable = this.H;
        }
        androidx.core.widget.i.a(this.C, drawable, (Drawable) null, (Drawable) null, (Drawable) null);
    }

    public void setIconPadding(int i2) {
        this.C.setCompoundDrawablePadding(i2);
    }

    /* access modifiers changed from: package-private */
    public void setIconTintList(ColorStateList colorStateList) {
        this.F = colorStateList;
        this.G = colorStateList != null;
        i iVar = this.E;
        if (iVar != null) {
            setIcon(iVar.getIcon());
        }
    }

    public void setNeedsEmptyIcon(boolean z2) {
        this.A = z2;
    }

    public void setTextAppearance(int i2) {
        androidx.core.widget.i.d(this.C, i2);
    }

    public void setTextColor(ColorStateList colorStateList) {
        this.C.setTextColor(colorStateList);
    }

    public void setTitle(CharSequence charSequence) {
        this.C.setText(charSequence);
    }

    public NavigationMenuItemView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public NavigationMenuItemView(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.I = new a();
        setOrientation(0);
        LayoutInflater.from(context).inflate(R$layout.design_navigation_menu_item, this, true);
        this.z = context.getResources().getDimensionPixelSize(R$dimen.design_navigation_icon_size);
        CheckedTextView checkedTextView = (CheckedTextView) findViewById(R$id.design_menu_item_text);
        this.C = checkedTextView;
        checkedTextView.setDuplicateParentStateEnabled(true);
        v.a((View) this.C, this.I);
    }
}
