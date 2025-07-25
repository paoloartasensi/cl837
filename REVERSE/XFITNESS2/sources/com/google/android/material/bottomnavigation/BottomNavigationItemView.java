package com.google.android.material.bottomnavigation;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.view.menu.i;
import androidx.appcompat.view.menu.n;
import androidx.appcompat.widget.i0;
import androidx.core.graphics.drawable.a;
import androidx.core.h.t;
import androidx.core.h.v;
import com.google.android.material.R$dimen;
import com.google.android.material.R$drawable;
import com.google.android.material.R$id;
import com.google.android.material.R$layout;

public class BottomNavigationItemView extends FrameLayout implements n.a {
    private static final int[] q = {16842912};
    private final int e;

    /* renamed from: f  reason: collision with root package name */
    private float f1407f;

    /* renamed from: g  reason: collision with root package name */
    private float f1408g;

    /* renamed from: h  reason: collision with root package name */
    private float f1409h;

    /* renamed from: i  reason: collision with root package name */
    private int f1410i;

    /* renamed from: j  reason: collision with root package name */
    private boolean f1411j;
    private ImageView k;
    private final TextView l;
    private final TextView m;
    private int n;
    private i o;
    private ColorStateList p;

    public BottomNavigationItemView(Context context) {
        this(context, (AttributeSet) null);
    }

    public void a(i iVar, int i2) {
        this.o = iVar;
        setCheckable(iVar.isCheckable());
        setChecked(iVar.isChecked());
        setEnabled(iVar.isEnabled());
        setIcon(iVar.getIcon());
        setTitle(iVar.getTitle());
        setId(iVar.getItemId());
        if (!TextUtils.isEmpty(iVar.getContentDescription())) {
            setContentDescription(iVar.getContentDescription());
        }
        i0.a(this, iVar.getTooltipText());
        setVisibility(iVar.isVisible() ? 0 : 8);
    }

    public boolean c() {
        return false;
    }

    public i getItemData() {
        return this.o;
    }

    public int getItemPosition() {
        return this.n;
    }

    public int[] onCreateDrawableState(int i2) {
        int[] onCreateDrawableState = super.onCreateDrawableState(i2 + 1);
        i iVar = this.o;
        if (iVar != null && iVar.isCheckable() && this.o.isChecked()) {
            FrameLayout.mergeDrawableStates(onCreateDrawableState, q);
        }
        return onCreateDrawableState;
    }

    public void setCheckable(boolean z) {
        refreshDrawableState();
    }

    public void setChecked(boolean z) {
        TextView textView = this.m;
        textView.setPivotX((float) (textView.getWidth() / 2));
        TextView textView2 = this.m;
        textView2.setPivotY((float) textView2.getBaseline());
        TextView textView3 = this.l;
        textView3.setPivotX((float) (textView3.getWidth() / 2));
        TextView textView4 = this.l;
        textView4.setPivotY((float) textView4.getBaseline());
        int i2 = this.f1410i;
        if (i2 != -1) {
            if (i2 == 0) {
                if (z) {
                    a(this.k, this.e, 49);
                    a(this.m, 1.0f, 1.0f, 0);
                } else {
                    a(this.k, this.e, 17);
                    a(this.m, 0.5f, 0.5f, 4);
                }
                this.l.setVisibility(4);
            } else if (i2 != 1) {
                if (i2 == 2) {
                    a(this.k, this.e, 17);
                    this.m.setVisibility(8);
                    this.l.setVisibility(8);
                }
            } else if (z) {
                a(this.k, (int) (((float) this.e) + this.f1407f), 49);
                a(this.m, 1.0f, 1.0f, 0);
                TextView textView5 = this.l;
                float f2 = this.f1408g;
                a(textView5, f2, f2, 4);
            } else {
                a(this.k, this.e, 49);
                TextView textView6 = this.m;
                float f3 = this.f1409h;
                a(textView6, f3, f3, 4);
                a(this.l, 1.0f, 1.0f, 0);
            }
        } else if (this.f1411j) {
            if (z) {
                a(this.k, this.e, 49);
                a(this.m, 1.0f, 1.0f, 0);
            } else {
                a(this.k, this.e, 17);
                a(this.m, 0.5f, 0.5f, 4);
            }
            this.l.setVisibility(4);
        } else if (z) {
            a(this.k, (int) (((float) this.e) + this.f1407f), 49);
            a(this.m, 1.0f, 1.0f, 0);
            TextView textView7 = this.l;
            float f4 = this.f1408g;
            a(textView7, f4, f4, 4);
        } else {
            a(this.k, this.e, 49);
            TextView textView8 = this.m;
            float f5 = this.f1409h;
            a(textView8, f5, f5, 4);
            a(this.l, 1.0f, 1.0f, 0);
        }
        refreshDrawableState();
        setSelected(z);
    }

    public void setEnabled(boolean z) {
        super.setEnabled(z);
        this.l.setEnabled(z);
        this.m.setEnabled(z);
        this.k.setEnabled(z);
        if (z) {
            v.a((View) this, t.a(getContext(), 1002));
        } else {
            v.a((View) this, (t) null);
        }
    }

    public void setIcon(Drawable drawable) {
        if (drawable != null) {
            Drawable.ConstantState constantState = drawable.getConstantState();
            if (constantState != null) {
                drawable = constantState.newDrawable();
            }
            drawable = a.i(drawable).mutate();
            a.a(drawable, this.p);
        }
        this.k.setImageDrawable(drawable);
    }

    public void setIconSize(int i2) {
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) this.k.getLayoutParams();
        layoutParams.width = i2;
        layoutParams.height = i2;
        this.k.setLayoutParams(layoutParams);
    }

    public void setIconTintList(ColorStateList colorStateList) {
        this.p = colorStateList;
        i iVar = this.o;
        if (iVar != null) {
            setIcon(iVar.getIcon());
        }
    }

    public void setItemBackground(int i2) {
        setItemBackground(i2 == 0 ? null : androidx.core.content.a.c(getContext(), i2));
    }

    public void setItemPosition(int i2) {
        this.n = i2;
    }

    public void setLabelVisibilityMode(int i2) {
        if (this.f1410i != i2) {
            this.f1410i = i2;
            if (this.o != null) {
                setChecked(this.o.isChecked());
            }
        }
    }

    public void setShifting(boolean z) {
        if (this.f1411j != z) {
            this.f1411j = z;
            if (this.o != null) {
                setChecked(this.o.isChecked());
            }
        }
    }

    public void setTextAppearanceActive(int i2) {
        androidx.core.widget.i.d(this.m, i2);
        a(this.l.getTextSize(), this.m.getTextSize());
    }

    public void setTextAppearanceInactive(int i2) {
        androidx.core.widget.i.d(this.l, i2);
        a(this.l.getTextSize(), this.m.getTextSize());
    }

    public void setTextColor(ColorStateList colorStateList) {
        if (colorStateList != null) {
            this.l.setTextColor(colorStateList);
            this.m.setTextColor(colorStateList);
        }
    }

    public void setTitle(CharSequence charSequence) {
        this.l.setText(charSequence);
        this.m.setText(charSequence);
        i iVar = this.o;
        if (iVar == null || TextUtils.isEmpty(iVar.getContentDescription())) {
            setContentDescription(charSequence);
        }
    }

    public BottomNavigationItemView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public BottomNavigationItemView(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.n = -1;
        Resources resources = getResources();
        LayoutInflater.from(context).inflate(R$layout.design_bottom_navigation_item, this, true);
        setBackgroundResource(R$drawable.design_bottom_navigation_item_background);
        this.e = resources.getDimensionPixelSize(R$dimen.design_bottom_navigation_margin);
        this.k = (ImageView) findViewById(R$id.icon);
        this.l = (TextView) findViewById(R$id.smallLabel);
        this.m = (TextView) findViewById(R$id.largeLabel);
        v.h(this.l, 2);
        v.h(this.m, 2);
        setFocusable(true);
        a(this.l.getTextSize(), this.m.getTextSize());
    }

    public void setItemBackground(Drawable drawable) {
        v.a((View) this, drawable);
    }

    private void a(View view, int i2, int i3) {
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) view.getLayoutParams();
        layoutParams.topMargin = i2;
        layoutParams.gravity = i3;
        view.setLayoutParams(layoutParams);
    }

    private void a(View view, float f2, float f3, int i2) {
        view.setScaleX(f2);
        view.setScaleY(f3);
        view.setVisibility(i2);
    }

    private void a(float f2, float f3) {
        this.f1407f = f2 - f3;
        this.f1408g = (f3 * 1.0f) / f2;
        this.f1409h = (f2 * 1.0f) / f3;
    }
}
