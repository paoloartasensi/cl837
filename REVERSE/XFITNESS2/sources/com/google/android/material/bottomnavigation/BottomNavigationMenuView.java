package com.google.android.material.bottomnavigation;

import android.animation.TimeInterpolator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.R$attr;
import androidx.appcompat.view.menu.g;
import androidx.appcompat.view.menu.i;
import androidx.appcompat.view.menu.m;
import androidx.appcompat.view.menu.n;
import androidx.core.g.e;
import androidx.core.h.v;
import androidx.transition.b;
import androidx.transition.l;
import androidx.transition.p;
import com.google.android.material.R$dimen;
import com.google.android.material.internal.j;

public class BottomNavigationMenuView extends ViewGroup implements n {
    private static final int[] C = {16842912};
    private static final int[] D = {-16842910};
    /* access modifiers changed from: private */
    public BottomNavigationPresenter A;
    /* access modifiers changed from: private */
    public g B;
    private final p e;

    /* renamed from: f  reason: collision with root package name */
    private final int f1412f;

    /* renamed from: g  reason: collision with root package name */
    private final int f1413g;

    /* renamed from: h  reason: collision with root package name */
    private final int f1414h;

    /* renamed from: i  reason: collision with root package name */
    private final int f1415i;

    /* renamed from: j  reason: collision with root package name */
    private final int f1416j;
    private final View.OnClickListener k;
    private final e<BottomNavigationItemView> l;
    private boolean m;
    private int n;
    private BottomNavigationItemView[] o;
    private int p;
    private int q;
    private ColorStateList r;
    private int s;
    private ColorStateList t;
    private final ColorStateList u;
    private int v;
    private int w;
    private Drawable x;
    private int y;
    private int[] z;

    class a implements View.OnClickListener {
        a() {
        }

        public void onClick(View view) {
            i itemData = ((BottomNavigationItemView) view).getItemData();
            if (!BottomNavigationMenuView.this.B.a((MenuItem) itemData, (m) BottomNavigationMenuView.this.A, 0)) {
                itemData.setChecked(true);
            }
        }
    }

    public BottomNavigationMenuView(Context context) {
        this(context, (AttributeSet) null);
    }

    private boolean a(int i2, int i3) {
        if (i2 == -1) {
            if (i3 > 3) {
                return true;
            }
        } else if (i2 == 0) {
            return true;
        }
        return false;
    }

    private BottomNavigationItemView getNewItem() {
        BottomNavigationItemView a2 = this.l.a();
        return a2 == null ? new BottomNavigationItemView(getContext()) : a2;
    }

    public void c() {
        g gVar = this.B;
        if (gVar != null && this.o != null) {
            int size = gVar.size();
            if (size != this.o.length) {
                a();
                return;
            }
            int i2 = this.p;
            for (int i3 = 0; i3 < size; i3++) {
                MenuItem item = this.B.getItem(i3);
                if (item.isChecked()) {
                    this.p = item.getItemId();
                    this.q = i3;
                }
            }
            if (i2 != this.p) {
                androidx.transition.n.a(this, this.e);
            }
            boolean a2 = a(this.n, this.B.n().size());
            for (int i4 = 0; i4 < size; i4++) {
                this.A.b(true);
                this.o[i4].setLabelVisibilityMode(this.n);
                this.o[i4].setShifting(a2);
                this.o[i4].a((i) this.B.getItem(i4), 0);
                this.A.b(false);
            }
        }
    }

    public ColorStateList getIconTintList() {
        return this.r;
    }

    public Drawable getItemBackground() {
        BottomNavigationItemView[] bottomNavigationItemViewArr = this.o;
        if (bottomNavigationItemViewArr == null || bottomNavigationItemViewArr.length <= 0) {
            return this.x;
        }
        return bottomNavigationItemViewArr[0].getBackground();
    }

    @Deprecated
    public int getItemBackgroundRes() {
        return this.y;
    }

    public int getItemIconSize() {
        return this.s;
    }

    public int getItemTextAppearanceActive() {
        return this.w;
    }

    public int getItemTextAppearanceInactive() {
        return this.v;
    }

    public ColorStateList getItemTextColor() {
        return this.t;
    }

    public int getLabelVisibilityMode() {
        return this.n;
    }

    public int getSelectedItemId() {
        return this.p;
    }

    public int getWindowAnimations() {
        return 0;
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z2, int i2, int i3, int i4, int i5) {
        int childCount = getChildCount();
        int i6 = i4 - i2;
        int i7 = i5 - i3;
        int i8 = 0;
        for (int i9 = 0; i9 < childCount; i9++) {
            View childAt = getChildAt(i9);
            if (childAt.getVisibility() != 8) {
                if (v.o(this) == 1) {
                    int i10 = i6 - i8;
                    childAt.layout(i10 - childAt.getMeasuredWidth(), 0, i10, i7);
                } else {
                    childAt.layout(i8, 0, childAt.getMeasuredWidth() + i8, i7);
                }
                i8 += childAt.getMeasuredWidth();
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i2, int i3) {
        int size = View.MeasureSpec.getSize(i2);
        int size2 = this.B.n().size();
        int childCount = getChildCount();
        int makeMeasureSpec = View.MeasureSpec.makeMeasureSpec(this.f1416j, 1073741824);
        if (!a(this.n, size2) || !this.m) {
            int min = Math.min(size / (size2 == 0 ? 1 : size2), this.f1414h);
            int i4 = size - (size2 * min);
            for (int i5 = 0; i5 < childCount; i5++) {
                if (getChildAt(i5).getVisibility() != 8) {
                    int[] iArr = this.z;
                    iArr[i5] = min;
                    if (i4 > 0) {
                        iArr[i5] = iArr[i5] + 1;
                        i4--;
                    }
                } else {
                    this.z[i5] = 0;
                }
            }
        } else {
            View childAt = getChildAt(this.q);
            int i6 = this.f1415i;
            if (childAt.getVisibility() != 8) {
                childAt.measure(View.MeasureSpec.makeMeasureSpec(this.f1414h, Integer.MIN_VALUE), makeMeasureSpec);
                i6 = Math.max(i6, childAt.getMeasuredWidth());
            }
            int i7 = size2 - (childAt.getVisibility() != 8 ? 1 : 0);
            int min2 = Math.min(size - (this.f1413g * i7), Math.min(i6, this.f1414h));
            int i8 = size - min2;
            int min3 = Math.min(i8 / (i7 == 0 ? 1 : i7), this.f1412f);
            int i9 = i8 - (i7 * min3);
            int i10 = 0;
            while (i10 < childCount) {
                if (getChildAt(i10).getVisibility() != 8) {
                    this.z[i10] = i10 == this.q ? min2 : min3;
                    if (i9 > 0) {
                        int[] iArr2 = this.z;
                        iArr2[i10] = iArr2[i10] + 1;
                        i9--;
                    }
                } else {
                    this.z[i10] = 0;
                }
                i10++;
            }
        }
        int i11 = 0;
        for (int i12 = 0; i12 < childCount; i12++) {
            View childAt2 = getChildAt(i12);
            if (childAt2.getVisibility() != 8) {
                childAt2.measure(View.MeasureSpec.makeMeasureSpec(this.z[i12], 1073741824), makeMeasureSpec);
                childAt2.getLayoutParams().width = childAt2.getMeasuredWidth();
                i11 += childAt2.getMeasuredWidth();
            }
        }
        setMeasuredDimension(View.resolveSizeAndState(i11, View.MeasureSpec.makeMeasureSpec(i11, 1073741824), 0), View.resolveSizeAndState(this.f1416j, makeMeasureSpec, 0));
    }

    public void setIconTintList(ColorStateList colorStateList) {
        this.r = colorStateList;
        BottomNavigationItemView[] bottomNavigationItemViewArr = this.o;
        if (bottomNavigationItemViewArr != null) {
            for (BottomNavigationItemView iconTintList : bottomNavigationItemViewArr) {
                iconTintList.setIconTintList(colorStateList);
            }
        }
    }

    public void setItemBackground(Drawable drawable) {
        this.x = drawable;
        BottomNavigationItemView[] bottomNavigationItemViewArr = this.o;
        if (bottomNavigationItemViewArr != null) {
            for (BottomNavigationItemView itemBackground : bottomNavigationItemViewArr) {
                itemBackground.setItemBackground(drawable);
            }
        }
    }

    public void setItemBackgroundRes(int i2) {
        this.y = i2;
        BottomNavigationItemView[] bottomNavigationItemViewArr = this.o;
        if (bottomNavigationItemViewArr != null) {
            for (BottomNavigationItemView itemBackground : bottomNavigationItemViewArr) {
                itemBackground.setItemBackground(i2);
            }
        }
    }

    public void setItemHorizontalTranslationEnabled(boolean z2) {
        this.m = z2;
    }

    public void setItemIconSize(int i2) {
        this.s = i2;
        BottomNavigationItemView[] bottomNavigationItemViewArr = this.o;
        if (bottomNavigationItemViewArr != null) {
            for (BottomNavigationItemView iconSize : bottomNavigationItemViewArr) {
                iconSize.setIconSize(i2);
            }
        }
    }

    public void setItemTextAppearanceActive(int i2) {
        this.w = i2;
        BottomNavigationItemView[] bottomNavigationItemViewArr = this.o;
        if (bottomNavigationItemViewArr != null) {
            for (BottomNavigationItemView bottomNavigationItemView : bottomNavigationItemViewArr) {
                bottomNavigationItemView.setTextAppearanceActive(i2);
                ColorStateList colorStateList = this.t;
                if (colorStateList != null) {
                    bottomNavigationItemView.setTextColor(colorStateList);
                }
            }
        }
    }

    public void setItemTextAppearanceInactive(int i2) {
        this.v = i2;
        BottomNavigationItemView[] bottomNavigationItemViewArr = this.o;
        if (bottomNavigationItemViewArr != null) {
            for (BottomNavigationItemView bottomNavigationItemView : bottomNavigationItemViewArr) {
                bottomNavigationItemView.setTextAppearanceInactive(i2);
                ColorStateList colorStateList = this.t;
                if (colorStateList != null) {
                    bottomNavigationItemView.setTextColor(colorStateList);
                }
            }
        }
    }

    public void setItemTextColor(ColorStateList colorStateList) {
        this.t = colorStateList;
        BottomNavigationItemView[] bottomNavigationItemViewArr = this.o;
        if (bottomNavigationItemViewArr != null) {
            for (BottomNavigationItemView textColor : bottomNavigationItemViewArr) {
                textColor.setTextColor(colorStateList);
            }
        }
    }

    public void setLabelVisibilityMode(int i2) {
        this.n = i2;
    }

    public void setPresenter(BottomNavigationPresenter bottomNavigationPresenter) {
        this.A = bottomNavigationPresenter;
    }

    public BottomNavigationMenuView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.l = new androidx.core.g.g(5);
        this.p = 0;
        this.q = 0;
        Resources resources = getResources();
        this.f1412f = resources.getDimensionPixelSize(R$dimen.design_bottom_navigation_item_max_width);
        this.f1413g = resources.getDimensionPixelSize(R$dimen.design_bottom_navigation_item_min_width);
        this.f1414h = resources.getDimensionPixelSize(R$dimen.design_bottom_navigation_active_item_max_width);
        this.f1415i = resources.getDimensionPixelSize(R$dimen.design_bottom_navigation_active_item_min_width);
        this.f1416j = resources.getDimensionPixelSize(R$dimen.design_bottom_navigation_height);
        this.u = a(16842808);
        b bVar = new b();
        this.e = bVar;
        bVar.b(0);
        this.e.a(115);
        this.e.a((TimeInterpolator) new g.c.a.a.b());
        this.e.a((l) new j());
        this.k = new a();
        this.z = new int[5];
    }

    public void a(g gVar) {
        this.B = gVar;
    }

    public boolean b() {
        return this.m;
    }

    public ColorStateList a(int i2) {
        TypedValue typedValue = new TypedValue();
        if (!getContext().getTheme().resolveAttribute(i2, typedValue, true)) {
            return null;
        }
        ColorStateList b = androidx.appcompat.a.a.a.b(getContext(), typedValue.resourceId);
        if (!getContext().getTheme().resolveAttribute(R$attr.colorPrimary, typedValue, true)) {
            return null;
        }
        int i3 = typedValue.data;
        int defaultColor = b.getDefaultColor();
        return new ColorStateList(new int[][]{D, C, ViewGroup.EMPTY_STATE_SET}, new int[]{b.getColorForState(D, defaultColor), i3, defaultColor});
    }

    /* access modifiers changed from: package-private */
    public void b(int i2) {
        int size = this.B.size();
        for (int i3 = 0; i3 < size; i3++) {
            MenuItem item = this.B.getItem(i3);
            if (i2 == item.getItemId()) {
                this.p = i2;
                this.q = i3;
                item.setChecked(true);
                return;
            }
        }
    }

    public void a() {
        removeAllViews();
        BottomNavigationItemView[] bottomNavigationItemViewArr = this.o;
        if (bottomNavigationItemViewArr != null) {
            for (BottomNavigationItemView bottomNavigationItemView : bottomNavigationItemViewArr) {
                if (bottomNavigationItemView != null) {
                    this.l.a(bottomNavigationItemView);
                }
            }
        }
        if (this.B.size() == 0) {
            this.p = 0;
            this.q = 0;
            this.o = null;
            return;
        }
        this.o = new BottomNavigationItemView[this.B.size()];
        boolean a2 = a(this.n, this.B.n().size());
        for (int i2 = 0; i2 < this.B.size(); i2++) {
            this.A.b(true);
            this.B.getItem(i2).setCheckable(true);
            this.A.b(false);
            BottomNavigationItemView newItem = getNewItem();
            this.o[i2] = newItem;
            newItem.setIconTintList(this.r);
            newItem.setIconSize(this.s);
            newItem.setTextColor(this.u);
            newItem.setTextAppearanceInactive(this.v);
            newItem.setTextAppearanceActive(this.w);
            newItem.setTextColor(this.t);
            Drawable drawable = this.x;
            if (drawable != null) {
                newItem.setItemBackground(drawable);
            } else {
                newItem.setItemBackground(this.y);
            }
            newItem.setShifting(a2);
            newItem.setLabelVisibilityMode(this.n);
            newItem.a((i) this.B.getItem(i2), 0);
            newItem.setItemPosition(i2);
            newItem.setOnClickListener(this.k);
            addView(newItem);
        }
        int min = Math.min(this.B.size() - 1, this.q);
        this.q = min;
        this.B.getItem(min).setChecked(true);
    }
}
